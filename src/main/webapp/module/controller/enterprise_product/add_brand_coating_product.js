App.controller("entaddbrandCoatingProductCtrl",function($scope, $rootScope, $location, $http, $compile, $routeParams, path) {
	
	$scope.form = {};                  //表单
	$scope.paintBaseEntity = {} 
	$scope.paintSalesEntities = []
	$scope.brandProdSeriesEntities = [] 
	$scope.brandProdImgEntities=[];
	
	$scope.formEditor={};
	
	var params = JSON.parse($routeParams.params);
	console.log("路由参数：",params);
	
	var url="";
	$("#category").kendoMultiSelect().data("kendoMultiSelect"); //多选所属分类
    $("#applicable_space").kendoMultiSelect().data("kendoMultiSelect");//多选适用空间
    
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
			console.log("所属分类数据");   
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
		var result = '<img src="'+url+'" alt="" width='+$scope.formEditor.imgWidth+' height='+$scope.formEditor.imgHeight+'/>';   
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
	        	//console.log('成功：',e.response);
	        	url = e.response.result;   //图片路径
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
	
	$scope.initEditor("editor");
	$scope.init("unit","paintunit","单位");
	$scope.init("coating","paintcoating","涂料光泽");
	$scope.init("gloss","paintgloss","图层类别");
	
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
	
	$scope.Upload("uploadColourClassify0","ColourClassify_img0","ColourClassify_url0",100,100);			//颜色分类图片
	//判断是否是编辑
	if(params.method=="edit"||params.method=="check"){
		console.log("查看编辑回显");
		console.log($scope.initTreeLine);
		$http({
			url: path+"/server/ent/getpaintbyid/"+params.id,
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
				$scope.paintBaseEntity.id=data.paintBaseEntity.id;
				$scope.paintBaseEntity.product_id=data.id;
				
				$scope.form.product_name=data.product_name;
				$("#sort").val(data.sort);
				$("#unit").data("kendoComboBox").value(data.paintBaseEntity.unit);
				$("#coating").data("kendoComboBox").value(data.paintBaseEntity.coating);
				$("#gloss").data("kendoComboBox").value(data.paintBaseEntity.gloss);
				$scope.paintBaseEntity.ispalette=data.paintBaseEntity.ispalette;
				$scope.paintBaseEntity.capacity=data.paintBaseEntity.spray_type;
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
				for (var j = 0; j < data.paintSalesEntities.length; j++) {
					if(j!=0){
						$scope.addColourClassify();
					}
					$("#deleteHtml"+j).find("input[name='standard']").val(data.paintSalesEntities[j].capacity);
					$("#deleteHtml"+j).find("input[name='addPrice']").val(data.paintSalesEntities[j].price);
					$("#deleteHtml"+j).find("input[name='addRepertory']").val(data.paintSalesEntities[j].stock);
					$("#deleteHtml"+j).find("input[name='productModel']").val(data.paintSalesEntities[j].model);
					$("#deleteHtml"+j).find("input[name='autonomouslyName']").val(data.paintSalesEntities[j].color);
					$("#ColourClassify_img"+j).css({
						"width":"100px",
			    		"height":"100px",
			    		"background": "url('"+data.paintSalesEntities[j].color_url+"') no-repeat",
			    	    "background-size":"100% 100%"
			    	});
					$("#ColourClassify_url"+j).val(data.paintSalesEntities[j].color_url); 
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
					$("#entaddbrandCoatingProductCtrl").find("input").attr("readonly","readonly");
					$("#entaddbrandCoatingProductCtrl").find("input").attr("disabled","disabled");
					$("#entaddbrandCoatingProductCtrl").find("select").attr("readonly","readonly");
					$("#entaddbrandCoatingProductCtrlButton").css("display","none");
					
					$("#unit").data("kendoComboBox").readonly(true);
					$("#coating").data("kendoComboBox").readonly(true);
					$("#gloss").data("kendoComboBox").readonly(true);
					$("#start_time").data("kendoDateTimePicker").readonly(true);
				}
				
			}else{
				alert("数据错误");
			}
		}).error(function(data){
			alert(data);   
		}) 
	}
	//保存壁纸数据
	$scope.saveTlie=function(){
		
		$scope.form.sort = $("#sort").val();//产品排序
		var editor = $("#editor").data("kendoEditor");
		$scope.form.description = editor.value();//产品展示图
		
		$scope.paintBaseEntity.coating = $("#coating").data("kendoComboBox").value().toString();
		$scope.paintBaseEntity.gloss = $("#gloss").data("kendoComboBox").value().toString();
		$scope.paintBaseEntity.isnew = $("#newProduct").val();
		$scope.paintBaseEntity.unit = $("#unit").data("kendoComboBox").value().toString();//计价单位
		$scope.paintBaseEntity.ispalette=$scope.paintBaseEntity.ispalette;							 
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
		for (var i = 6; i <= 10; i++) {
			var obj={};
			obj.imgurl=$("#upload"+i+"_url").val();
			obj.type="2";
			if(obj.imgurl!=undefined&&obj.imgurl!=null&&obj.imgurl!=""){
				$scope.brandProdImgEntities.push(obj);
			}
		}
		//规则分类inp
		var colour=$("#ColourClassifyId").find(".classifClass");//颜色分类长度
		var st=$("#ColourClassifyId").find(".classifClass").find("input[name='standard']");//规则分类数组
		var ap=$("#ColourClassifyId").find(".classifClass").find("input[name='addPrice']");//规则分类价格数组
		var ar=$("#ColourClassifyId").find(".classifClass").find("input[name='addRepertory']");//规则分类库存数组
		var an=$("#ColourClassifyId").find(".classifClass").find("input[name='autonomouslyName']");//颜色分类颜色名称
		var pm=$("#ColourClassifyId").find(".classifClass").find("input[name='productModel']");//颜色分类型号
		
		for (var i = 0; i < colour.length; i++) {
			var json={
				"capacity":st[i].value,
				"price":ap[i].value,
				"stock":ar[i].value,
				"color":an[i].value,
				"model":pm[i].value,
				"color_url":$("#ColourClassify_url"+i).val()
			}
			$scope.paintSalesEntities.push(json);
		}
		
		
		$scope.form.paintBaseEntity=$scope.paintBaseEntity;
		$scope.form.brandProdSeriesEntities=$scope.brandProdSeriesEntities;
		$scope.form.brandProdImgEntities=$scope.brandProdImgEntities;
		$scope.form.paintSalesEntities=$scope.paintSalesEntities;

		console.log(angular.toJson($scope.form));
		if(params.method=="edit"){
			$http({
				url: path+"/server/ent/modifypaint",
				method:'POST', 
				data:angular.toJson($scope.form),
				headers:  {
					"X-Object-Header" : "123456789 ",
		            "Content-Type": "application/json"
		        }
			}).success(function(data){
				if(data.code==0){
					alert("修改成功");
					$location.path("/entbrandCoatingProduct/{}");
				}else{
					alert("修改失败");
				}
			}).error(function(data){
				alert(data);   
			}) 
		}else{
			$http({
				url: path+"/server/ent/addpaint",
				method:'POST', 
				data:angular.toJson($scope.form),
				headers:  {
					"X-Object-Header" : "123456789 ",
		            "Content-Type": "application/json"
		        }
			}).success(function(data){
				if(data.code==0){
					alert("保存成功");
					$location.path("/entbrandCoatingProduct/{}");
				}else{
					alert("保存失败");
				}
			}).error(function(data){
				alert(data);   
			}) 
		}
	}
	
	
	/*----------------------------------------------------------------------------------------------*/
	//确定选择规则"
	$scope.yesSpecifications=function(){
		var inp=$("#Specifications").find("input[name='inp']:checked");
		var id=$("#Specifications").find("button").val();
		if(inp.length>1){
			alert("只能选择一个规格");
		}else if(inp.length<1){
			alert("请选择一个规则分类");
		}else{
			$("#"+id).val(inp[0].value);
			$("#Specifications").find("input[name='inp']:checked").attr("checked",false);
		}
		var dialog = $("#Specifications").data("kendoWindow");
		dialog.close();
	}
	//显示规格分类disabled="true"
	$scope.showSpecifications=function(e){
		console.log(e);
		console.log(e.target);
		$("#Specifications").kendoWindow({
			  position: { top: e.pageY,left:e.pageX}
		});
		var dialog = $("#Specifications").data("kendoWindow");
		dialog.open();
		$("#Specifications").find("button").val(e.target.id);
	}
	//点击添加按钮添加一个颜色分类
	$scope.addColourClassify=function(){
		var length=$("#ColourClassifyId").find(".classifClass").length;
		var object=$("#ColourClassifyId").find(".classifClass");
		var ti=object[length-1].title;
		ti=parseInt(ti)+1;
		html='<div class="classifClass" style="height:110px;" id="deleteHtml'+ti+'" title="'+ti+'">'+								
			'<div class="col-xs-8">'+
				'<div class="col-xs-6" style="height:50px;">'+
					'<input style="margin-top: 10px;" name="autonomouslyName" class="k-textbox" placeholder="名称可自主编辑"/>'+
				'</div>'+
				'<div class="col-xs-6" style="height:50px;">'+
					'<input style="margin-top: 10px;" name="productModel" class="k-textbox" placeholder="请输入型号"/>'+
				'</div>'+
				'<div class="col-xs-4" style="height:50px;">'+
					'<input name="standard" id="standard'+ti+'" ng-click="showSpecifications($event)" class="k-textbox" placeholder="选择规格分类" style="inline-block;"/>'+
				'</div>'+
				'<div class="col-xs-4" style="height:50px;">'+
					'<input name="addPrice" class="k-textbox" placeholder="添加价格" style="inline-block;width:80%;"/>'+
			        '<span style="inline-block;">元</span>'+
				'</div>'+
				'<div class="col-xs-4" style="height:50px;">'+
					'<input name="addRepertory" class="k-textbox" placeholder="添加库存数量" style="inline-block;width:80%;"/>'+
			        '<span style="inline-block;">件</span>'+
				'</div>'+
			'</div>'+
			'<div class="col-xs-2">'+
				'<div  class="unloadIcon1">'+
				 	    '<span class="glyphicon glyphicon-plus "></span>'+
				'</div>'+
		   		'<input type="file" name="file" id="uploadColourClassify'+ti+'" class="k-upload-button"/>'+
		   		'<div class="col-sm-9" id="ColourClassify_img'+ti+'"></div>'+	
		   		'<input type="hidden" id="ColourClassify_url'+ti+'"/>'+
			'</div>'+
			'<div class="col-xs-2">'+
				'<button class="k-button" style="margin-top: 40px;" ng-click="deleteColourClassify('+ti+')">删除</button>'+
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
