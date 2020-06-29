App.controller("entaddBrandProductCtrl",function($scope, $rootScope, $location, $http, $compile, $routeParams, path) {
	$scope.form = {};                  //表单
	$scope.baseEntity = {}             
	$scope.brandProdSeriesEntities = []             
	$scope.brandProdImgEntities = [] 
	$scope.wallpaperSalesEntities=[];
	
	$scope.formEditor={}
	
	var params = JSON.parse($routeParams.params);
	console.log("路由参数：",params);
	
	var url="";
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
			console.log("加载所属分类错误");   
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
	//初始化时间控件
	$scope.initDateTime = function(containerId){
		$("#"+containerId).kendoDateTimePicker({
		     value: "",
		     culture: "de-DE",
		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
		     interval: 30,   				  //时间间隔
		     timeFormat: "HH:mm" 			  //24 hours format
		});
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
	//初始化加载适用空间
	$scope.initSpace = function(){
		$("#comb_space").kendoMultiSelect({
			dataTextField: "name",
			dataValueField: "id",
			placeholder: "适用空间",
			filter: "contains",
			dataSource: {
				serverFiltering: true,
				transport: {
					read: {
						type : 'GET',
						url: path+"/server/wallpaperspace",
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
	
	//加载辅料套餐
	$scope.initInputchk = function(){
		$http({
			url: path+"/server/wallpaperpackage",
			method:'GET',   
		}).success(function(data){
			var html="";
			$(".wallpaperSalesEntities").html("");
			for (var i = 0; i < data.result.length; i++) {
				if(i==0){
					html+='<li><input checked="checked" name="entities" type="radio"  value="'+data.result[i].id+'">'+
	          		'<label>'+data.result[i].name+'</label></li>';
				}else{
					html+='<li><input name="entities" type="radio"  value="'+data.result[i].id+'">'+
	          		'<label>'+data.result[i].name+'</label></li>';
				}
			}
			html = $compile(html)($scope);
	    	$(".wallpaperSalesEntities").html(html);
		}).error(function(data){
			alert(data);   
		}) 
	}
	
	
	$scope.initEditor("editor");
	$scope.init("comb_unit","wallpaperunit","单位");
	$scope.init("comb_surfaceProcess","wallpapercraft","面层工艺");
	$scope.init("comb_producing","wallpapermadein","产地");
	$scope.init("comb_manner","wallpaperstyle","风格");
	$scope.init("comb_principal","wallpapermainmaterial","主材");
	
	$scope.initSpace();           //初始化适用空间、
	$scope.initInputchk();        //初始化辅料套餐
	$scope.initDateTime("comb_start_time");        //初始化时间
	
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
	
	$scope.Upload("logo1","logo1_img","logo1_url");		//上传图片
	
	$scope.Upload("uploadColourClassify0","ColourClassify_img0","ColourClassify_url0",100,100);			//上传案例图片
	
	//判断是否是编辑
	if(params.method=="edit"||params.method=="check"){
		console.log("查看编辑回显");
		console.log($scope.initTreeLine);
		$http({
			url: path+"/server/ent/wallpaperbyid/"+params.id,
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
				$scope.baseEntity.id=data.baseEntity.id;
				$scope.baseEntity.product_id=data.id;
				
				$scope.form.product_name=data.product_name;
				//$("#comb_state").val(data.state);
				$("#comb_sort").val(data.sort);
				if(data.baseEntity.standard!=null&&data.baseEntity.standard!=""&&data.baseEntity.standard!=undefined){
					$scope.baseEntity.standard=data.baseEntity.standard;
				}
				$("#comb_unit").data("kendoComboBox").value(data.baseEntity.unit);
				$("#comb_surfaceProcess").data("kendoComboBox").value(data.baseEntity.craft_id);
				
				$("#comb_space").data("kendoMultiSelect").value(data.baseEntity.space.split(","));
				$("#comb_position").val(data.baseEntity.haspicture);
				$("#comb_producing").data("kendoComboBox").value(data.baseEntity.madein);
				$("#comb_manner").data("kendoComboBox").value(data.baseEntity.style);
				//$("#comb_newProduct")是否新品
				//$("#comb_start_time").data("kendoDateTimePicker").value(data.new_time);//时间
				
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
				//辅料套餐单选按钮回显赋值
				var inp=$(".wallpaperSalesEntities").find("input");
				for (var j = 0; j < inp.length; j++) {
					if(inp[j].value==parseInt(data.wallpaperSalesEntities[0].material_package)){
						inp[j].checked=true;
						if(params.method=="check"){
							inp[j].disabled=false;
						}
					}
				}
				//付款模式
				/*if(data.baseEntity.sales_model==2){
					$scope.baseEntity.sales_model=data.baseEntity.sales_model;
					$scope.baseEntity.percent=data.baseEntity.percent;
				}else{
					$scope.baseEntity.sales_model=data.baseEntity.sales_model;
				}*/
				//颜色分类
				for (var l = 0; l < data.wallpaperSalesEntities.length; l++) {
					if(l!=0){
						$scope.addColourClassify();
					}
					$("#deleteHtml"+l).find("input[name='productModel']").val(data.wallpaperSalesEntities[l].model);
					$("#deleteHtml"+l).find("input[name='autonomouslyName']").val(data.wallpaperSalesEntities[l].color);
					$("#deleteHtml"+l).find("input[name='addPrice']").val(data.wallpaperSalesEntities[l].price);
					$("#deleteHtml"+l).find("input[name='addRepertory']").val(data.wallpaperSalesEntities[l].stock);
					//$("#ColourClassify_img"+l).css("backgroundImage","url("+data.wallpaperSalesEntities[l].color_img+")"); 
					$("#ColourClassify_img"+l).css({
						"width":"100px",
			    		"height":"100px",
			    		"background": "url('"+data.wallpaperSalesEntities[l].color_img+"') no-repeat",
			    	    "background-size":"100% 100%"
			    	});
					$("#ColourClassify_url"+l).val(data.wallpaperSalesEntities[l].color_img); 
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
					$("#entaddBrandProductCtrl").find("input").attr("readonly","readonly");
					$("#entaddBrandProductCtrl").find("input").attr("disabled","disabled");
					$("#entaddBrandProductCtrl").find("select").attr("readonly","readonly");
					$("#entaddBrandProductCtrlButton").css("display","none");
					$("#comb_unit").data("kendoComboBox").readonly(true);
					$("#comb_surfaceProcess").data("kendoComboBox").readonly(true);
					$("#comb_space").data("kendoMultiSelect").readonly(true);
					$("#comb_producing").data("kendoComboBox").readonly(true);
					$("#comb_manner").data("kendoComboBox").readonly(true);
					$("#comb_start_time").data("kendoDateTimePicker").readonly(true);
				}
				
			}else{
				alert("数据错误");
			}
		}).error(function(data){
			alert(data);   
		}) 
	}
	
	//保存壁纸数据
	$scope.saveWallpaper=function(){
		
		//$scope.form.state = $("#comb_state").data("kendoComboBox").value();//产品状态
		//$scope.form.new_time = $("#comb_newProduct").val();//是否新品
		//$scope.form.new_time=$("#comb_start_time").data("kendoDateTimePicker").value();//设置上新时间
		var editor = $("#editor").data("kendoEditor");
		$scope.form.description = editor.value();//产品展示图
		$scope.form.sort = $("#comb_sort").val();//产品排序
		
		$scope.baseEntity.space = $("#comb_space").data("kendoMultiSelect").value().toString();//适用空间
		$scope.baseEntity.sales_model=$("input[name='marketClass']").val();// 销售类型
		$scope.baseEntity.craft_id = $("#comb_surfaceProcess").data("kendoComboBox").value().toString();//面层工艺
		$scope.baseEntity.unit = $("#comb_unit").data("kendoComboBox").value().toString();//产品单位
		$scope.baseEntity.style = $("#comb_manner").data("kendoComboBox").value().toString();//风格
		$scope.baseEntity.haspicture = $("#comb_position").val();//是否图案
		$scope.baseEntity.madein = $("#comb_producing").data("kendoComboBox").value().toString();//产地
		//销售方式暂无
		
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
		
		//销售属性辅料套餐
		var inp=$(".wallpaperSalesEntities").find("input");
		var check="";
		var inputArray=[];
		for (var i = 0; i < inp.length; i++) {
			if(inp[i].checked==true){
				check=inp[i].value;
			}
		}
		var array=[];
		//销售属性颜色分类
		var colClass=$("#ColourClassifyId").find(".classifClass");
		var pm=$("#ColourClassifyId").find(".classifClass").find("input[name='productModel']");
		var an=$("#ColourClassifyId").find(".classifClass").find("input[name='autonomouslyName']");
		var ap=$("#ColourClassifyId").find(".classifClass").find("input[name='addPrice']");
		var ar=$("#ColourClassifyId").find(".classifClass").find("input[name='addRepertory']");
		//console.log(check);
		for (var i = 0; i < colClass.length; i++) {
			var json={
					"material_package":check,
					"model":pm[i].value,
					"color":an[i].value,
					"price":ap[i].value,
					"stock":ar[i].value,
					"color_img":$("#ColourClassify_url"+i).val()
			}
			$scope.wallpaperSalesEntities.push(json);
		}
		$scope.form.baseEntity=$scope.baseEntity;
		$scope.form.brandProdSeriesEntities=$scope.brandProdSeriesEntities;
		$scope.form.brandProdImgEntities=$scope.brandProdImgEntities;
		$scope.form.wallpaperSalesEntities=$scope.wallpaperSalesEntities;
		console.log(angular.toJson($scope.form));
		if(params.method=="edit"){
			//console.log(angular.toJson($scope.form));
			$http({
				url: path+"/server/ent/modifywallpaper",
				method:'POST', 
				data:angular.toJson($scope.form),
				headers:  {
					"X-Object-Header" : "123456789 ",
		            "Content-Type": "application/json"
		        }
			}).success(function(data){
				console.log(data);
				if(data.code==0){
					alert("修改成功");
					$location.path("/entbrandWallpaperProduct/{}");
				}else{
					alert("修改失败");
				}
			}).error(function(data){
				alert(data);   
			}) 
		}else if(params.method=="add"){
			$http({
				url: path+"/server/ent/addwallpaper",
				method:'POST', 
				data:angular.toJson($scope.form),
				headers:  {
					"X-Object-Header" : "123456789 ",
		            "Content-Type": "application/json"
		        }
			}).success(function(data){
				console.log(data);
				if(data.code==0){
					alert("保存成功");
					$location.path("/entbrandWallpaperProduct/{}");
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
			'<div class="col-md-1"></div>'+
		    '<div class="col-md-11">'+
			'<div class="col-md-12">'+
				'<div class="col-xs-7">'+
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
	 			 '<div class="col-xs-3" class="market_img">'+
 			 		'<div  class="unloadIcon1">'+
					 	'<span class="glyphicon glyphicon-plus "></span>'+
					'</div>'+
   		    		'<input type="file" name="file" id="uploadColourClassify'+ti+'" class="k-upload-button"/>'+
   		    		'<div class="col-sm-9 divImg1" id="ColourClassify_img'+ti+'"></div>'+	
   		    		'<input type="hidden" id="ColourClassify_url'+ti+'"/>'+
	 			 '</div>'+
	 			 '<div class="col-xs-2">'+
	 			 	'<button ng-click="deleteColourClassify('+ti+')" class="k-button">删除</button>'+
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
