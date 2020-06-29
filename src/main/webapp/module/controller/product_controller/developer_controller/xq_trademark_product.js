/*添加页面主controller*/
App.controller("xqTrademarkProduct",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, $timeout) {
	$scope.data=[];
	
	$scope.addColourClassify=null;
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	var editor = UE.getEditor('containerxq',{
		toolbars:[['FullScreen', 'Source']],
	});
	if(editor.container!=undefined){
		editor.destroy();
		editor=UE.getEditor('containerxq',{
			toolbars:[['FullScreen', 'Source']],
		});
	}
	console.log(editor.container);
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
        		console.log('成功：',e.response);
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
	//添加图片
	$scope.appendImg=function(e){
		html='<div class="col-sm-2 caseImg">'+
				'<div  class="unloadIcon" >'+
					'<span class="glyphicon glyphicon-plus"></span>'+
				'</div>'+
				'<input type="file" name="file" id="caseUpload'+e+'" class="k-upload-button" />'+
				'<div class="col-sm-12 divImg" id="caseUpload_img'+e+'"></div>'+	
				'<input type="hidden" id="caseUpload_url'+e+'"/>'+
			'</div>';
		$("#uplodingImgController1").append(html);
		$scope.Upload("caseUpload"+e,"caseUpload_img"+e,"caseUpload_url"+e,150,150);
		
	}
	$scope.addColourClassify = function(){
		var len=$("#ColourClassifyId").find(".Colour").length;
		html='<div class="col-sm-12 Colour form-horizontal form-widgets outPadding" id="classifClass'+len+'">'+
				'<div class="form-group">'+
				'<div class="col-sm-10 outPadding">'+
					'<div class="col-sm-11 outPadding fix classifClass" id="classifClass'+len+'">'+
						  '<div class="col-sm-9 businessl">'+
						  		'<input name="autonomouslyName" class="k-textbox retract" placeholder="颜色名称" style="width:100%">'+
						  '</div>'+
						  '<div class="col-sm-2" class="market_img">'+
		 			 		'<div  class="unloadIcon1">'+
							 	  '<span class="glyphicon glyphicon-plus "></span>'+
							'</div>'+
	       		    		'<input type="file" name="file" id="uploadColourClassify'+len+'" class="k-upload-button"/>'+
	       		    		'<div class="col-sm-9 divImg1" id="ColourClassify_img'+len+'"></div>'+	
	       		    		'<input type="hidden" id="ColourClassify_url'+len+'"/>'+
			 			 '</div>'+
					'</div>'+
					'<div class="col-sm-8 outPadding row" id="standard'+len+'">'+
						'<div class="col-sm-12 Standard" id="standard'+len+'0">'+
							'<div class="col-xs-3 businessl fix" >'+
								'<input name="addStandard" class="k-textbox retract" placeholder="添加规格" style="width:80%;;">'+
							'</div>'+
							'<div class="col-xs-3 businessl fix">'+
								'<input name="addModel" class="k-textbox retract" placeholder="添加型号" style="width:80%;">'+
							'</div>'+
							'<div class="col-xs-3 businessl businessr fix" >'+
								'<input name="addPrice" class="k-textbox retract" placeholder="添加价格" style="width:80%;">'+
								'<span style="inline-block;">元</span>'+
							'</div>'+
							'<div class="col-xs-3 businessl businessr fix">'+
								'<input name="addRepertory" class="k-textbox retract" placeholder="添加库存数量" style="width:80%;">'+
								'<span style="inline-block;width:20%">件</span>'+
							'</div>'+
						'</div>'+
					'</div>'+
				'</div>'+
				'</div>'+
			'</div>';
		$("#ColourClassifyId").append($compile(html)($scope));
		$scope.Upload("uploadColourClassify"+len,"ColourClassify_img"+len,"ColourClassify_url"+len,100,100);	
	}
	//添加一条规格框
	$scope.addStandareData = function(id){
		var len=$("#standard"+id).find(".Standard").length;
		var html='<div class="col-sm-12 Standard" id="standard'+id+len+'">'+
					'<div class="col-xs-3 businessl fix" >'+
					'<input name="addStandard" class="k-textbox retract" placeholder="添加规格" style="width:80%;;">'+
				'</div>'+
				'<div class="col-xs-3 businessl fix">'+
					'<input name="addModel" class="k-textbox retract" placeholder="添加型号" style="width:80%;">'+
				'</div>'+
				'<div class="col-xs-3 businessl businessr fix" >'+
					'<input name="addPrice" class="k-textbox retract" placeholder="添加价格" style="width:80%;">'+
					'<span style="inline-block;">元</span>'+
				'</div>'+
				'<div class="col-xs-3 businessl businessr fix">'+
					'<input name="addRepertory" class="k-textbox retract" placeholder="添加库存数量" style="width:80%;">'+
					'<span style="inline-block;width:20%">件</span>'+
				'</div>'+
			'</div>';
		$("#standard"+id).append($compile(html)($scope));
	}
	
	
	$scope.Upload("upload1","upload1_img","upload1_url",150,150);				//上传展示图片
	$scope.Upload("upload2","upload2_img","upload2_url",150,150);				//上传展示图片
	$scope.Upload("upload3","upload3_img","upload3_url",150,150);				//上传展示图片
	$scope.Upload("upload4","upload4_img","upload4_url",150,150);				//上传展示图片
	$scope.Upload("upload5","upload5_img","upload5_url",150,150);				//上传展示图片
	$scope.Upload("uploadColourClassify0","ColourClassify_img0","ColourClassify_url0",100,100);				//上传展示图片	
	$scope.Upload("caseUpload0","caseUpload_img0","caseUpload_url0",150,150);				//上传展示图片
	$scope.Upload("logo1","logo1_img","logo1_url");		//上传图片
	//$scope.initEditor("editor");
	
	//加载回显数据
	$scope.geteditData=function(){
		$scope.titleName="品牌产品";
		$http({
			url: path+"/server/querybrandproductbyid/"+params.id,
			method:'GET', 
			headers:  {
				"X-Object-Header" : "123456789 ",
	            "Content-Type": "application/json"
	        }
		}).success(function(data){
			if(data!=null&&data!=""&&data!=undefined){
				console.log(data);
				var data=data.result;
				var typeImg=[],typeImg1=[];
				//案例图回显
				for (var k = 0; k < data.baseBrandProdImgEntities.length; k++) {
					if(data.baseBrandProdImgEntities[k].type==1){
						typeImg.push(data.baseBrandProdImgEntities[k])
					}else if(data.baseBrandProdImgEntities[k].type==2){
						typeImg1.push(data.baseBrandProdImgEntities[k])
					}
				}
				for (var n = 0; n < typeImg.length; n++) {
					$("#upload"+(n+1)+"_img").attr("imgId",typeImg[n].id);
					$("#upload"+(n+1)+"_img").css({
						"width":"150px",
						"height":"150px",
						"background": "url('"+typeImg[n].imgurl+"') no-repeat",
						"background-size":"100% 100%"
					});
					$("#upload"+(n+1)+"_url").val(typeImg[n].imgurl);
				}
				console.log(typeImg1);
				for (var s = 0; s < typeImg1.length; s++) {
					if(s!=0){
						$scope.appendImg(s);
					}
					$("#caseUpload_img"+s).attr("imgId",typeImg1[s].id);
					$("#caseUpload_img"+s).css({
						"width":"150px",
						"height":"150px",
						"background": "url('"+typeImg1[s].imgurl+"') no-repeat",
						"background-size":"100% 100%"
					});
					$("#caseUpload_url"+s).val(typeImg1[s].imgurl);
				}
				//筛选条件回显
				for (var d = 0; d < data.productViewAttrAndVaule.length; d++) {
					html='<div class="col-xs-6" style="height:50px;">'+
							'<div class="col-xs-4">'+
								'<label class="control-label col-sm-12" style="text-align: right;"><font class="f">*</font>'+data.productViewAttrAndVaule[d].ca_name+'</label>'+
							'</div>'+
							'<div class="col-xs-8">'+
								'<input style="width:100%" class="k-textbox" value="'+data.productViewAttrAndVaule[d].attr_values+'"/>'+
							'</div>'+
						'</div>';
					$("#productAttributeController").append(html);
				}
				var sehtml="";
				for (var i = 0; i < data.baseBrandProdSeriesEntities.length; i++) {
					if(i%5==0&&i!=0){
						sehtml+="<span class='category_big_span'><span>"+data.baseBrandProdSeriesEntities[i].brandSeriesEntity.name+"</span></span></br>";
					}else{
						sehtml+="<span class='category_big_span'><span>"+data.baseBrandProdSeriesEntities[i].brandSeriesEntity.name+"</span></span>";
					}
				}
				$("#tree_line").html("");
				$("#tree_line").html(sehtml);
				
				//颜色分类
				var colorArray=data.prodSalesAttrEntity;
				for (var j = 0; j < colorArray.length; j++) {
					if(j!=0){
						$scope.addColourClassify();
					}
					$("#classifClass"+j).find("input[name='autonomouslyName']").val(colorArray[j].color); //颜色名称
					$("#ColourClassify_img"+j).css({
						"width":"100px",
			    		"height":"100px",
			    		"background": "url('"+colorArray[j].color_url+"') no-repeat",
			    	    "background-size":"100% 100%"
			    	});
					$("#ColourClassify_url"+j).val(colorArray[j].color_url); 
					var standardArray=colorArray[j].standardArray;
					for (var k = 0; k < standardArray.length; k++) {
						if(k!=0){
							$scope.addStandareData(j);
						}
						$("#standard"+j+k).attr("colorid",colorArray[j].id);
						 $("#standard"+j+k).find("input[name='addStandard']").val(standardArray[k].standard);//规格
						 $("#standard"+j+k).find("input[name='addModel']").val(standardArray[k].model);//型号
						 $("#standard"+j+k).find("input[name='addPrice']").val(standardArray[k].price); //价格
						 $("#standard"+j+k).find("input[name='addRepertory']").val(standardArray[k].stock);//库存
					}
					
				}
				
				$("#product_title").val(data.product_title);//标题
				$("#product_sort").val(data.product_sort);//排序
				$("#percent").val(data.percent);//付款百分比
				$("#product_unit").val(data.product_unit)//计价单位
				if(data.isnew==1){
					$("#newProduct").val("新品");//是否新品
				}else{
					$("#newProduct").val("非新品");//是否新品
				}
				$("#start_time").val(data.new_time);//上新时间
				if(data.sales_model==1){
					$("#sales_model").val("全款");//付款模式
				}else{
					$("#sales_model").val("定金");//付款模式
				}
				//$("#editor").data("kendoEditor").value(data.description);//图文编辑器回显
				editor.execCommand('insertHtml', data.description);
				editor.setHeight(550);
				editor.setDisabled('fullscreen');
				$("#xqTrademarkProduct").find("input").attr("readonly","readonly");
				$("#xqTrademarkProduct").find("input").attr("disabled","disabled");
				$("#xqTrademarkProduct").find("select").attr("readonly","readonly");
			}else{
				alert("数据错误");
			}
		}).error(function(data){
			alert(data);   
		}) 
	};
	$scope.geteditData1=function(){
		$scope.titleName="仓库产品";
		$http({
			url: path+"/server/queryentproductbyid/"+params.id,
			method:'GET', 
			headers:  {
				"X-Object-Header" : "123456789 ",
				"Content-Type": "application/json"
			}
		}).success(function(data){
			if(data!=null&&data!=""&&data!=undefined){
				//console.log(angular.toJson(data));
				var data=data.result;
				var typeImg=[],typeImg1=[];
				//案例图回显
				for (var k = 0; k < data.baseBrandProdImgEntities.length; k++) {
					if(data.baseBrandProdImgEntities[k].type==1){
						typeImg.push(data.baseBrandProdImgEntities[k])
					}else if(data.baseBrandProdImgEntities[k].type==2){
						typeImg1.push(data.baseBrandProdImgEntities[k])
					}
				}
				for (var n = 0; n < typeImg.length; n++) {
					$("#upload"+(n+1)+"_img").attr("imgId",typeImg[n].id);
					$("#upload"+(n+1)+"_img").css({
						"width":"150px",
						"height":"150px",
						"background": "url('"+typeImg[n].imgurl+"') no-repeat",
						"background-size":"100% 100%"
					});
					$("#upload"+(n+1)+"_url").val(typeImg[n].imgurl);
				}
				//console.log(typeImg1);
				for (var s = 0; s < typeImg1.length; s++) {
					$scope.appendImg((s+1));
					$("#caseUpload_img"+s).attr("imgId",typeImg1[s].id);
					$("#caseUpload_img"+s).css({
						"width":"150px",
						"height":"150px",
						"background": "url('"+typeImg1[s].imgurl+"') no-repeat",
						"background-size":"100% 100%"
					});
					$("#caseUpload_url"+s).val(typeImg1[s].imgurl);
				}
				//筛选条件回显
				for (var d = 0; d < data.productViewAttrAndVaule.length; d++) {
					html='<div class="col-xs-6" style="height:50px;">'+
					'<div class="col-xs-4">'+
					'<label class="control-label col-sm-12" style="text-align: right;"><font class="f">*</font>'+data.productViewAttrAndVaule[d].ca_name+'</label>'+
					'</div>'+
					'<div class="col-xs-8">'+
					'<input class="k-textbox" style="width:100%" value="'+data.productViewAttrAndVaule[d].attr_values+'"/>'+
					'</div>'+
					'</div>';
					$("#productAttributeController").append(html);
				}
				var sehtml="";
				for (var i = 0; i < data.baseBrandProdSeriesEntities.length; i++) {
					if(i%5==0&&i!=0){
						sehtml+="<span class='category_big_span'><span>"+data.baseBrandProdSeriesEntities[i].entSeriesEntity.name+"</span></span></br>";
					}else{
						sehtml+="<span class='category_big_span'><span>"+data.baseBrandProdSeriesEntities[i].entSeriesEntity.name+"</span></span>";
					}
				}
				$("#tree_line").html("");
				$("#tree_line").html(sehtml);
				
				//颜色分类
				var colorArray=data.prodSalesAttrEntity;
				for (var j = 0; j < colorArray.length; j++) {
					if(j!=0){
						$scope.addColourClassify();
					}
					$("#classifClass"+j).find("input[name='autonomouslyName']").val(colorArray[j].color); //颜色名称
					$("#ColourClassify_img"+j).css({
						"width":"100px",
						"height":"100px",
						"background": "url('"+colorArray[j].color_url+"') no-repeat",
						"background-size":"100% 100%"
					});
					$("#ColourClassify_url"+j).val(colorArray[j].color_url); 
					var standardArray=colorArray[j].standardArray;
					for (var k = 0; k < standardArray.length; k++) {
						if(k!=0){
							$scope.addStandareData(j);
						}
						$("#standard"+j+k).attr("colorid",colorArray[j].id);
						$("#standard"+j+k).find("input[name='addStandard']").val(standardArray[k].standard);//规格
						$("#standard"+j+k).find("input[name='addModel']").val(standardArray[k].model);//型号
						$("#standard"+j+k).find("input[name='addPrice']").val(standardArray[k].price); //价格
						$("#standard"+j+k).find("input[name='addRepertory']").val(standardArray[k].stock);//库存
					}
					
				}
				
				$("#product_title").val(data.product_title);//标题
				$("#product_sort").val(data.product_sort);//排序
				$("#percent").val(data.percent);//付款百分比
				$("#product_unit").val(data.product_unit)//计价单位
				if(data.isnew==1){
					$("#newProduct").val("新品");//是否新品
				}else{
					$("#newProduct").val("非新品");//是否新品
				}
				$("#start_time").val(data.new_time);//上新时间
				if(data.sales_model==1){
					$("#sales_model").val("全款");//付款模式
				}else{
					$("#sales_model").val("定金");//付款模式
				}
				//$("#editor").data("kendoEditor").value(data.description);//图文编辑器回显
				editor.execCommand('insertHtml', data.description);
				editor.setHeight(550);
				editor.setDisabled('fullscreen');
				$("#xqTrademarkProduct").find("input").attr("readonly","readonly");
				$("#xqTrademarkProduct").find("input").attr("disabled","disabled");
				$("#xqTrademarkProduct").find("select").attr("readonly","readonly");
				
			}else{
				alert("数据错误");
			}
		}).error(function(data){
			alert(data);   
		}) 
	
	}
	//判断是否是编辑
	editor.ready( function( editor ) {
		if(params.method=="check"){
			$timeout(function(){
				$scope.geteditData();
				},1000);
			
		}else if(params.method=="check1"){
			 $timeout(function(){$scope.geteditData1()},1000);
		}
    });
	
	
});

