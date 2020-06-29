App.controller("entaddBrandDoorProductCtrl",function($scope, $rootScope, $location, $http, $compile, $routeParams, path) {
	
	$scope.form = {};                  //表单
	$scope.doorBaseEntity  = {}        
	$scope.doorSalesEntities  = []          
	$scope.brandProdSeriesEntities = []             
	$scope.brandProdImgEntities = []  
	
	$scope.formEditor={};
	var params = JSON.parse($routeParams.params);
	console.log("路由参数：",params);
	
	$("#category").kendoMultiSelect().data("kendoMultiSelect"); //多选所属分类
    $("#applicable_space").kendoMultiSelect().data("kendoMultiSelect");//多选适用空间
    $("#accessories").kendoMultiSelect().data("kendoMultiSelect");//多选辅料套餐
    
  //加载所属分类
	$scope.initTreeLine = function(){
		$http({
			   url: path+"/server/ent/getclassifyseriesfortree",
			   method:'GET',   
		}).success(function(data){
			console.log("加载所属分类");
			$("#tree_line").kendoTreeView({
				   checkboxes: {
		                checkChildren: true,
		            },
		            dataSource:data
				});
			
		}).error(function(data){
			console.log("所属分类数据错误");   
		}) 
	}
	$scope.initTreeLine();        //初始化所属分类
    
  //初始化图文编辑器
	$scope.initEditor = function(editorId){
		curEditor = "#"+editorId
		$("#"+editorId).kendoEditor({
			  value: "",
			  encoded: false,
			  tools: [
                      "formatting",
                      "bold", "italic", "underline",
                      "justifyLeft", "justifyCenter", "justifyRight",
                      "insertUnorderedList", "insertOrderedList", "indent", 
                
                      "createTable",
                      {
                          name: "custom",                       
                          exec:function(e)
                          {
                        	  $scope.addImageWindow.center().open();   //打开弹框
                          }
                      }
                 ],
                
                keydown: function(e) {
                    $(".k-editable-area").tooltip('destroy');
                }
		});
		
	}
	//确定上传
	$scope.addImgToEditor = function(){
		var editor = $(curEditor).data("kendoEditor");
		
		var para=document.createElement("p");
		var result = '<img src="'+$("#logo1_url").val()+'" alt="" width='+$scope.formEditor.imgWidth+' height='+$scope.formEditor.imgHeight+'/>';   
		var div = document.createElement('div');
		div.innerHTML = result;  
		editor.body.appendChild(div);
		$scope.addImageWindow.center().close();   //关闭弹框
	}
	//关闭弹出框
	$scope.closeImgToEditor = function(){
		$scope.addImageWindow.center().close();   //关闭弹框
	}
    //上传图片
	$scope.Upload = function(containerId,imgId,imgUrlId,w,h){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/updoad",
	            removeUrl: path+"/remove",
	           // autoUpload: false
	        },
	     	multiple: false,      //多选
	      	showFileList: false,  //显示文件列表
	        localization: {
	              select: "请上传图片",
	              cancel: "取消",
	              remove: "移除",  //autoUpload: false
	              retry: "刷新",
	              headerStatusUploading: "上传...",
	              headerStatusUploaded: "上传成功",
	              statusUploading: "loading",
	              statusUploaded: "success",
	              statusFailed: "failed",
	              uploadSelectedFiles: "上传选中的文件",   //autoUpload: false
	        },
	        success: function(e){
	        	var url = e.response.result;   //图片路径
	        	$scope.showUploadImg(imgId,imgUrlId,url,w,h);
		    },
	        error: function(e){
	        	//alert('失败！');
	        },
	        remove: function(e){
	        	//alert('移除！');
	        },
	      
	    });
	}
	//反显图片并赋值
	$scope.showUploadImg = function(imgId,imgUrlId,url,w,h){
		$("#"+imgId).css({
			"width":w,
    		"height":h,
    		"background": "url('"+url+"') no-repeat",
    	    "background-size":"100% 100%"
    	});
		$('#'+imgUrlId).val(url);
	}
	//初始化加载kendoComboBox
	$scope.init = function(id,url,name){
		$("#"+id).kendoComboBox({
			dataTextField: "name",
			dataValueField: "id",
			autoBind: false,
			placeholder: name,
			filter: "contains",
			dataSource: {
				serverFiltering: true,
				transport: {
					read: {
						type : 'GET',
						url: path+"/server/"+url,
						dataType : "json"
					}
				},
				schema : {
					data : function(d) {
						return d.result;
					}
				},
			}
		});
	}
	
	$scope.initEditor("editor");//初始化图文编辑器
	
	$scope.init("unit","doorunit","计价单位");//初始化计量单位
	$scope.init("craft_id","doorcraft","饰面工艺");//初始化饰面工艺
	$scope.init("material","doormaterial","材质分类");//初始化材质分类
	$scope.init("ropen","dooropen","开合方式");//初始化开合方式
	
	$scope.Upload("upload1","upload1_img","upload1_url",150,150);				//上传展示图片
	$scope.Upload("upload2","upload2_img","upload2_url",150,150);				//上传展示图片
	$scope.Upload("upload3","upload3_img","upload3_url",150,150);				//上传展示图片
	$scope.Upload("upload4","upload4_img","upload4_url",150,150);				//上传展示图片
	$scope.Upload("upload5","upload5_img","upload5_url",150,150);				//上传展示图片
	
	$scope.Upload("upload6","upload6_img","upload6_url",150,150);				//上传案例图片
	$scope.Upload("upload7","upload7_img","upload7_url",150,150);				//上传案例图片
	$scope.Upload("upload8","upload8_img","upload8_url",150,150);				//上传案例图片
	$scope.Upload("upload9","upload9_img","upload9_url",150,150);				//上传案例图片
	$scope.Upload("upload10","upload10_img","upload10_url",150,150);			//上传案例图片
	
	$scope.Upload("logo1","logo1_img","logo1_url");		//图文编辑器上传图片
	
	$scope.Upload("uploadColourClassify0","ColourClassify_img0","ColourClassify_url0",100,100);			//上传案例图片
	//判断是否是编辑
	if(params.method=="edit"||params.method=="check"){
		console.log("查看编辑回显");
		console.log($scope.initTreeLine);
		$http({
			url: path+"/server/ent/getdoorbyid/"+params.id,
			method:'GET', 
			headers:  {
				"X-Object-Header" : "123456789 ",
	            "Content-Type": "application/json"
	        }
		}).success(function(data){
			if(data!=null&&data!=""&&data!=undefined){
				console.log(angular.toJson(data));
				$scope.form.id=data.id;
				$scope.form.brand_id=data.brand_id;
				$scope.doorBaseEntity.id=data.doorBaseEntity.id;
				$scope.doorBaseEntity.product_id=data.id;
				
				$scope.form.product_name=data.product_name;
				$("#sort").val(data.sort);
				$("#unit").data("kendoComboBox").value(data.doorBaseEntity.unit);
				$("#material").data("kendoComboBox").value(data.doorBaseEntity.material);
				$("#craft_id").data("kendoComboBox").value(data.doorBaseEntity.craft_id);
				$("#ropen").data("kendoComboBox").value(data.doorBaseEntity.open_mode);
				//$("#comb_newProduct")是否新品
				if(params.method=="check"){
					html="";
					for (var i = 0; i < data.brandProdSeriesEntities.length; i++) {
						if(i%5==0&&i!=0){
							html+="<span class='category_big_span'><span>"+data.brandProdSeriesEntities[i].brandSeriesEntity.name+"</span></span></br>";
						}else{
							html+="<span class='category_big_span'><span>"+data.brandProdSeriesEntities[i].brandSeriesEntity.name+"</span></span>";
						}
					}
					$("#tree_line").html("");
					$("#tree_line").html(html);
				}else{
					//回显赋值所属分类
					var treeView = $("#tree_line").data("kendoTreeView");
					for (var i = 0; i < data.brandProdSeriesEntities.length; i++) {
						var id=data.brandProdSeriesEntities[i].series_id
						$("#tree_line :checkbox").filter(function(){
							var text=$("#tree_line").data("kendoTreeView").dataItem($(this).parent().parent());
							parseInt(id);
							if(text.node_id==id){
								treeView.expand(treeView.findByText(text.parent().parent().text));
							}
							return text.node_id==id;
						}).prop("checked",true);
					}
				}
				
				//颜色分类
				for (var l = 0; l < data.doorSalesEntities.length; l++) {
					if(l!=0){
						$scope.addColourClassify();
					}
					$("#deleteHtml"+l).find("input[name='productModel']").val(data.doorSalesEntities[l].model);
					$("#deleteHtml"+l).find("input[name='autonomouslyName']").val(data.doorSalesEntities[l].color);
					$("#deleteHtml"+l).find("input[name='addPrice']").val(data.doorSalesEntities[l].price);
					$("#deleteHtml"+l).find("input[name='addRepertory']").val(data.doorSalesEntities[l].stock);
					$("#ColourClassify_img"+l).css({
						"width":"100px",
			    		"height":"100px",
			    		"background": "url('"+data.doorSalesEntities[l].color_url+"') no-repeat",
			    	    "background-size":"100% 100%"
			    	});
					$("#ColourClassify_url"+l).val(data.doorSalesEntities[l].color_url); 
				}
				var typeImg=[],typeImg1=[];
				//案例图回显
				for (var k = 0; k < data.brandProdImgEntities.length; k++) {
					if(data.brandProdImgEntities[k].type==1){
						typeImg.push(data.brandProdImgEntities[k])
					}else{
						typeImg1.push(data.brandProdImgEntities[k])
					}
				}
				for (var n = 0; n < typeImg.length; n++) {
					$("#upload"+(n+1)+"_img").css({
						"width":"150px",
						"height":"150px",
						"background": "url('"+typeImg[n].imgurl+"') no-repeat",
						"background-size":"100% 100%"
					});
					$("#upload"+(n+1)+"_url").val(typeImg[n].imgurl);
				}
				for (var n = 0; n < typeImg1.length; n++) {
					$("#upload"+(n+6)+"_img").css({
						"width":"150px",
						"height":"150px",
						"background": "url('"+typeImg1[n].imgurl+"') no-repeat",
						"background-size":"100% 100%"
					});
					$("#upload"+(n+6)+"_url").val(typeImg1[n].imgurl);
				}
				
				$("#editor").data("kendoEditor").value(data.description);//图文编辑器回显
				//设置只读不可编辑
				if(params.method=="check"){
					$("#entaddBrandDoorProductCtrl").find("input").attr("readonly","readonly");
					$("#entaddBrandDoorProductCtrl").find("input").attr("disabled","disabled");
					$("#entaddBrandDoorProductCtrl").find("select").attr("readonly","readonly");
					$("#entaddBrandDoorProductCtrlButton").css("display","none");
					
					$("#unit").data("kendoComboBox").readonly(true);
					$("#material").data("kendoComboBox").readonly(true);
					$("#craft_id").data("kendoComboBox").readonly(true);
					$("#ropen").data("kendoComboBox").readonly(true);
					$("#start_time").data("kendoDateTimePicker").readonly(true);
				}
			}else{
				alert("数据错误");
			}
		}).error(function(data){
			alert(data);   
		}) 
	}
	//保存门产
	$scope.saveWallpaper=function(){
		
		$scope.form.sort = $("#sort").val();//产品排序
		var editor = $("#editor").data("kendoEditor");
		$scope.form.description = editor.value();//产品展示图
		
		$scope.doorBaseEntity.unit = $("#unit").data("kendoComboBox").value().toString();//计价单位
		$scope.doorBaseEntity.material = $("#material").data("kendoComboBox").value().toString();
		$scope.doorBaseEntity.craft_id = $("#craft_id").data("kendoComboBox").value().toString();
		$scope.doorBaseEntity.open_mode = $("#ropen").data("kendoComboBox").value().toString();
		$scope.doorBaseEntity.isnew = $("#newProduct").val();
		
		//所属分类
        treeView = $("#tree_line").data("kendoTreeView");
		$("#tree_line :checkbox").filter(function(){
			//console.log($(this)[0].checked);
			if($(this)[0].checked==true){
				var obj={};
				obj.series_id=$("#tree_line").data("kendoTreeView").dataItem($(this).parent().parent()).node_id;
				if(obj.series_id!=""&&obj.series_id!=null&&obj.series_id!=undefined){
					$scope.brandProdSeriesEntities.push(obj);
				}
			}
			//console.log($("#tree_line").data("kendoTreeView").dataItem($(this).parent().parent()));
		});
		//console.log($scope.brandProdSeriesEntities);
	    //产品展示图
		for (var i = 1; i <= 5; i++) {
			var obj={};
			obj.imgurl=$("#upload"+i+"_url").val();
			obj.type="1";
			if(obj.imgurl!=undefined&&obj.imgurl!=null&&obj.imgurl!=""){
				$scope.brandProdImgEntities.push(obj);
			}
		}
		for (var i = 6; i <= 11; i++) {
			var obj={};
			obj.imgurl=$("#upload"+i+"_url").val();
			obj.type="2";
			if(obj.imgurl!=undefined&&obj.imgurl!=null&&obj.imgurl!=""){
				$scope.brandProdImgEntities.push(obj);
			}
		}
		
		//销售属性颜色分类
		var colClass=$("#ColourClassifyId").find(".classifClass");
		var pm=$("#ColourClassifyId").find(".classifClass").find("input[name='productModel']");
		var an=$("#ColourClassifyId").find(".classifClass").find("input[name='autonomouslyName']");
		var ap=$("#ColourClassifyId").find(".classifClass").find("input[name='addPrice']");
		var ar=$("#ColourClassifyId").find(".classifClass").find("input[name='addRepertory']");
		for (var i = 0; i < colClass.length; i++) {
			var json={
					"model":pm[i].value,
					"color":an[i].value,
					"price":ap[i].value,
					"stock":ar[i].value,
					"color_url":$("#ColourClassify_url"+i).val()
			}
			$scope.doorSalesEntities.push(json);
		}
		$scope.form.doorBaseEntity=$scope.doorBaseEntity;
		$scope.form.brandProdSeriesEntities=$scope.brandProdSeriesEntities;
		$scope.form.brandProdImgEntities=$scope.brandProdImgEntities;
		$scope.form.doorSalesEntities=$scope.doorSalesEntities;
		
		console.log(angular.toJson($scope.form));
		if(params.method=="edit"){
			$http({
				url: path+"/server/ent/modifydoor",
				method:'POST', 
				data:angular.toJson($scope.form),
				headers:  {
					"X-Object-Header" : "123456789 ",
		            "Content-Type": "application/json"
		        }
			}).success(function(data){
				console.log(data);
				if(data.code=="0"){
					alert("修改成功");
					$location.path("/entbrandDoorProduct/{}");
				}else{
					alert("修改失败");
				}
			}).error(function(data){
				alert(data);   
			}) 
		}else{
			$http({
				url: path+"/server/ent/adddoor",
				method:'POST', 
				data:angular.toJson($scope.form),
				headers:  {
					"X-Object-Header" : "123456789 ",
		            "Content-Type": "application/json"
		        }
			}).success(function(data){
				console.log(data);
				if(data.code=="0"){
					alert("保存成功");
					$location.path("/entbrandDoorProduct/{}");
				}else{
					alert("保存失败");
				}
			}).error(function(data){
				alert(data);   
			}) 
		}
	}
	
	
	/*----------------------------------------------------------------------------------------------*/
	//点击添加按钮添加一个颜色分类
	$scope.addColourClassify=function(){
		var length=$("#ColourClassifyId").find(".classifClass").length;
		var object=$("#ColourClassifyId").find(".classifClass");
		var ti=object[length-1].title;
		ti=parseInt(ti)+1;
		html='<div class="col-sm-12 classifClass" style="height:110px;" id="deleteHtml'+ti+'" title="'+ti+'">'+
		    '<div class="col-md-12" style="padding: 0;">'+
			'<div class="col-md-12" style="padding: 0;">'+
				'<div class="col-xs-8" style="padding: 0;">'+
					'<div class="col-xs-6" style="height:50px;">'+
						'<input name="autonomouslyName" class="k-textbox" placeholder="名称可自主编辑"/>'+
					'</div>'+
					'<div class="col-xs-6" style="height:50px;">'+
						'<input name="productModel" class="k-textbox" placeholder="请输入型号"/>'+
					'</div>'+
					'<div class="col-xs-6" style="height:50px;">'+
						'<input name="addPrice" class="k-textbox" placeholder="添加价格" style="inline-block;width:80%;"/>'+
				        '<span style="inline-block;">元</span>'+
					'</div>'+
					'<div class="col-xs-6" style="height:50px;">'+
						'<input name="addRepertory" class="k-textbox" placeholder="添加库存数量" style="inline-block;width:80%;"/>'+
				        '<span style="inline-block;">件</span>'+
					'</div>'+
	 			 '</div>'+
	 			 '<div class="col-xs-2" class="market_img">'+
 			 		'<div  class="unloadIcon1">'+
					 	'<span class="glyphicon glyphicon-plus "></span>'+
					'</div>'+
   		    		'<input type="file" name="file" id="uploadColourClassify'+ti+'" class="k-upload-button"/>'+
   		    		'<div class="col-sm-9 divImg1" id="ColourClassify_img'+ti+'"></div>'+	
   		    		'<input type="hidden" id="ColourClassify_url'+ti+'"/>'+
	 			 '</div>'+
	 			 '<div class="col-xs-2">'+
	 			 	'<button class="k-button" style="margin-top: 40px;" ng-click="deleteColourClassify('+ti+')">删除</button>'+
	 			 '</div>'+
			'</div>'+
		'</div>';
		html = $compile(html)($scope);				
		$("#ColourClassifyId").append(html);
		$("#marketPropertyId").height($("#marketPropertyId").height()+110);
		
		$scope.Upload("uploadColourClassify"+ti,"ColourClassify_img"+ti,"ColourClassify_url"+ti,100,100);			//上传案例图片
	}
	//删除一个颜色分类
	$scope.deleteColourClassify=function(ti){
		
		var _parentElement=document.getElementById("deleteHtml"+ti).parentNode;
         if(_parentElement){
                _parentElement.removeChild(document.getElementById("deleteHtml"+ti));
                $("#marketPropertyId").height($("#marketPropertyId").height()-110);
         }
	}
});
