/*添加页面主controller*/
App.controller("addEnterpriseProduct",function($scope, $rootScope, $location, $http, $compile, $stateParams, path,$timeout) {
	$scope.data=[];
	$scope.from={};
	$scope.prodSalesAttrEntity=[]; //颜色分类
	$scope.baseBrandProdImgEntities=[]; //上传案例和轮播图
	$scope.baseBrandProdSeriesEntities=[]; // 所属分类
	$scope.brandProdAttrValueEntities=[]; //筛选属性
	$scope.addColourClassify=null;
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	var editor = UE.getEditor('container');
	if(editor.container!=undefined){
		editor.destroy();
		editor = UE.getEditor('container');
	}
	$scope.from.product_sort=0;
	//上传图片
	$scope.Upload = function(containerId,imgId,imgUrlId,w,h,siz,format){
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
	        select: function(e){	//选择
	        	var image = e.files[0];
        		if(image.size>(siz*1024)){
	        		alert("上传图片的大小请限制在【"+siz+"Kb】以内！");
	        		e.preventDefault();
	        		return;
	        	}
	        	if(image.extension.indexOf(format)==-1){
	        		alert("上传图片仅支持【"+format+"】格式！");
	        		e.preventDefault();
	        		return;
	        	}
	        }
	      
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
	//上传图片
	$scope.Upload1 = function(containerId,imgId,imgUrlId,w,h,siz,format){
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
	        	if(parseInt(e.response.code)==0){ 
	        		var length=$("#uplodingImgController1").find(".caseImg").length;
	        		if((length+1)%6==0){
	        			$("#uplodingImgHeight").height($("#uplodingImgHeight").height()+170);
	        		}
	        		html='<div class="col-sm-2 caseImg">'+
								'<div  class="unloadIcon" >'+
									'<span class="glyphicon glyphicon-plus"></span>'+
								'</div>'+
								'<input type="file" name="file" id="caseUpload'+length+'" class="k-upload-button" />'+
								'<div class="col-sm-12 divImg" id="caseUpload_img'+length+'"></div>'+	
								'<input type="hidden" id="caseUpload_url'+length+'"/>'+
							'</div>';
	        		if($("#"+imgUrlId).val()==""){
	        			$("#uplodingImgController1").append(html);
		        		$scope.Upload1("caseUpload"+length,"caseUpload_img"+length,"caseUpload_url"+length,150,150,150,"jpg");
	        		}
	        	}
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
	        select: function(e){	//选择
	        	var image = e.files[0];
        		if(image.siz>(siz*1024)){
	        		alert("上传图片的大小请限制在【"+siz+"Kb】以内！");
	        		e.preventDefault();
	        		return;
	        	}
	        	if(image.extension.indexOf(format)==-1){
	        		alert("上传图片仅支持【"+format+"】格式！");
	        		e.preventDefault();
	        		return;
	        	}
	        }
	    });
	}
	//添加图片
	$scope.appendImg=function(e){
		html='<div class="col-sm-2 caseImg" style="height: 150px">'+
				'<div  class="unloadIcon" >'+
					'<span class="glyphicon glyphicon-plus"></span>'+
				'</div>'+
				'<input type="file" name="file" id="caseUpload'+e+'" class="k-upload-button" />'+
				'<div class="col-sm-12 divImg" id="caseUpload_img'+e+'"></div>'+	
				'<input type="hidden" id="caseUpload_url'+e+'"/>'+
			'</div>';
		$("#uplodingImgController1").append(html);
		$scope.Upload1("caseUpload"+e,"caseUpload_img"+e,"caseUpload_url"+e,150,150);
		
	}
	$scope.addColourClassify = function(){
		var len=$("#ColourClassifyId").find(".Colour").length;
		html='<div class="col-sm-12 Colour" id="classifClass'+len+'">'+
				'<div class="col-sm-2"></div>'+
				'<div class="col-sm-10">'+
					'<div class="col-sm-11 outPadding fix classifClass">'+
						  '<div class="col-sm-9">'+
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
			 			'<div class="col-sm-1">'+
			 			 	'<input type="button" class="k-button" value="删除" ng-click="deleteColour('+len+')"/>'+
			 			 '</div>'+
					'</div>'+
					'<div class="col-sm-8 outPadding row" id="standard'+len+'">'+
						'<div class="col-sm-12 Standard" id="standard'+len+'0">'+
							'<div class="col-xs-3 businessl fix" >'+
								'<i class="fa fa-plus" ng-click="addStandareData('+len+')" aria-hidden="true" style="width:12px;"></i>'+
								'<input name="addStandard" class="k-textbox retract" placeholder="添加规格" style="width:80%;;">'+
							'</div>'+
							'<div class="col-xs-3 businessl fix">'+
								'<input name="addModel" class="k-textbox retract" placeholder="添加型号" style="width:80%;">'+
							'</div>'+
							'<div class="col-xs-3 businessl businessr fix" >'+
								'<input type="number" name="addPrice" class="k-textbox retract" placeholder="添加价格" style="width:80%;">'+
								'<span style="inline-block;">元</span>'+
							'</div>'+
							'<div class="col-xs-3 businessl businessr fix">'+
								'<input type="number" name="addRepertory" class="k-textbox retract" placeholder="添加库存数量" style="width:80%;">'+
								'<span style="inline-block;width:20%">件</span>'+
							'</div>'+
						'</div>'+
					'</div>'+
				'</div>'+
			'</div>';
		$("#ColourClassifyId").append($compile(html)($scope));
		$scope.Upload("uploadColourClassify"+len,"ColourClassify_img"+len,"ColourClassify_url"+len,100,100,100,"jpg");	
	}
	//添加一条规格框
	$scope.addStandareData = function(id){
		var len=$("#standard"+id).find(".Standard").length;
		var html='<div class="col-sm-12 Standard" id="standard'+id+len+'">'+
					'<div class="col-xs-3 businessl fix" >'+
					'<i class="fa fa-minus" ng-click="deleteStandareData('+id+','+len+')" aria-hidden="true" style="width:12px;"></i>'+
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
	//删除规格
	$scope.deleteStandareData=function(id,len){
		$("#standard"+id+len).remove();
	}
	$scope.Upload("upload1","upload1_img","upload1_url",150,150,150,"jpg");				//上传展示图片
	$scope.Upload("upload2","upload2_img","upload2_url",150,150,150,"jpg");				//上传展示图片
	$scope.Upload("upload3","upload3_img","upload3_url",150,150,150,"jpg");				//上传展示图片
	$scope.Upload("upload4","upload4_img","upload4_url",150,150,150,"jpg");				//上传展示图片
	$scope.Upload("upload5","upload5_img","upload5_url",150,150,150,"jpg");				//上传展示图片
	$scope.Upload("uploadColourClassify0","ColourClassify_img0","ColourClassify_url0",100,100,20,"jpg");				//上传展示图片	
	$scope.Upload1("caseUpload0","caseUpload_img0","caseUpload_url0",150,150,150,"jpg");				//上传展示图片
	
	//加载回显数据
	$scope.geteditData=function(){
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
				$scope.from.id=data.id;
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
				for (var s = 0; s < typeImg1.length; s++) {
					$scope.appendImg((s+1));
					if(s%6==0){
						$("#uplodingImgHeight").height($("#uplodingImgHeight").height()+160);
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
				var value=new Array();
				for (var d = 0; d < data.brandProdAttrValueEntities.length; d++) {
					if(data.brandProdAttrValueEntities[d].attrEntity.ismultiple==1&&$("#attribute"+data.brandProdAttrValueEntities[d].attr_id).length>0){
						$("#attribute"+data.brandProdAttrValueEntities[d].attr_id).data("kendoMultiSelect").value(data.brandProdAttrValueEntities[d].attr_value.split(","));
					}else{
						var num=data.brandProdAttrValueEntities[d].attr_id
						if($("#attribute"+num).attr("title")=="z"){
							$("#attribute"+num).data("kendoComboBox").setDataSource([{id:data.brandProdAttrValueEntities[d].attr_value,value:data.brandProdAttrValueEntities[d].value_name}]);
							$("#attribute"+num).data("kendoComboBox").value(data.brandProdAttrValueEntities[d].attr_value); 
						}else{
							$("#attribute"+num).data("kendoComboBox").value(data.brandProdAttrValueEntities[d].attr_value);
						}
					}
					
					/*if(data.brandProdAttrValueEntities[d].attr_id==1){
						value.push(data.brandProdAttrValueEntities[d].attr_value)
						if(value!=null&&value!=undefined){
							if($("#attribute"+data.brandProdAttrValueEntities[d].attr_id).length>0){
								$("#attribute"+data.brandProdAttrValueEntities[d].attr_id).data("kendoMultiSelect").value(value);
							}else{
								console.log("本条数据不正确，这个id不存在");
							}
						}
					}else if(data.brandProdAttrValueEntities[d].attr_id!=1){
						var num=data.brandProdAttrValueEntities[d].attr_id;
						if($("#attribute"+num).length>0){
							if($("#attribute"+num).attr("title")=="z"){
								$("#attribute"+num).data("kendoComboBox").setDataSource([{id:data.brandProdAttrValueEntities[d].attr_value,value:data.brandProdAttrValueEntities[d].value_name}]);
								$("#attribute"+num).data("kendoComboBox").value(data.brandProdAttrValueEntities[d].attr_value); 
							}else{
								$("#attribute"+num).data("kendoComboBox").value(data.brandProdAttrValueEntities[d].attr_value);
							}
						}else{
							console.log("本条数据不正确，这个id不存在");
						}
					}*/
				}
				
				//回显赋值所属分类
				var treeView = $("#tree_line").data("kendoTreeView");
				for (var i = 0; i < data.baseBrandProdSeriesEntities.length; i++) {
					var id=data.baseBrandProdSeriesEntities[i].series_id
					$("#tree_line :checkbox").filter(function(){
						var text=$("#tree_line").data("kendoTreeView").dataItem($(this).parent().parent());
						parseInt(id);
						if(text.id==id){
							treeView.expand(treeView.findByText(text.parent().parent().text));
						}
						return text.id==id;
					}).prop("checked",true);
				}
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
						$("#standard"+j+k).attr("colorid",standardArray[k].id);
						 $("#standard"+j+k).find("input[name='addStandard']").val(standardArray[k].standard);//规格
						 $("#standard"+j+k).find("input[name='addModel']").val(standardArray[k].model);//型号
						 $("#standard"+j+k).find("input[name='addPrice']").val(standardArray[k].price); //价格
						 $("#standard"+j+k).find("input[name='addRepertory']").val(standardArray[k].stock);//库存
					}
					
				}
				
				$scope.from.product_title=data.product_title;//标题
				$scope.from.product_sort=data.product_sort;//排序
				$scope.from.percent=data.percent;//付款百分比
				$("#product_unit").data("kendoComboBox").value(data.product_unit)//计价单位
				$("#newProduct").data("kendoComboBox").value(data.isnew);//是否新品
				//$("#editor").data("kendoEditor").value(data.description);//图文编辑器回显
				//百度图文编辑器回显数据
				editor.execCommand('insertHtml', data.description)
				$("#sales_model").data("kendoComboBox").value(data.sales_model);//付款模式
				$("#start_time").data("kendoDatePicker").value(data.new_time);//上新时间
				
			}else{
				alert("数据错误");
			}
		}).error(function(data){
			alert(data);   
		}) 
	}
	
	//判断是否是编辑
	if(params.method=="edit"){
		editor.ready( function( editor ) {
			$timeout(function(){$scope.geteditData()},1000);
		});
	}
	//判断是否为空
	$scope.isNull=function(valueIsNull){
		if(valueIsNull==null||valueIsNull==""||valueIsNull==undefined){
			return true;
		}else{
			return false;
		}
	}
	$scope.verification=function(){
		for (var i = 0; i < $scope.data.length; i++) {
			if($scope.data[i].ismultiple==1){ //多选框
				if($scope.isNull($("#attribute"+$scope.data[i].id).data("kendoMultiSelect").value())){
					alert($scope.data[i].name+"是必填项");
					return false;
				}
			}else if($scope.data[i].isenum==1){ //是下拉框
				if($scope.isNull($("#attribute"+$scope.data[i].id).data("kendoComboBox").value().toString())){
					alert($scope.data[i].name+"是必填项");
					return false;
				}
			}else{
				if($scope.isNull($("#attribute"+$scope.data[i].id).val())){
					alert($scope.data[i].name+"是必填项");
					return false;
				}
			}
		}
		$("#tree_line :checkbox").filter(function(){
			if($(this)[0].checked==true){
				var obj={};
				obj.series_id=$("#tree_line").data("kendoTreeView").dataItem($(this).parent().parent()).id;
				if(obj.series_id!=""&&obj.series_id!=null&&obj.series_id!=undefined){
					$scope.baseBrandProdSeriesEntities.push(obj);
				}
			}
		});
		if($scope.isNull($scope.baseBrandProdSeriesEntities)){
			alert("所属分类是必填项");
			return false;
		}
		var findAutonomouslyName=$("input[name='autonomouslyName']");
		for (var i = 0; i < findAutonomouslyName.length; i++) {
			if($scope.isNull(findAutonomouslyName[i].value)){
				alert("请检查颜色分类中的“颜色名称”是否填写完整");
				return false;
			}
		}
		for (var i = 0; i < findAutonomouslyName.length; i++) {
			if($scope.isNull($("#ColourClassify_url"+i).val())){
				alert("请检查颜色分类中的“颜色图片”是否填写完整");
				return false;
			}
		}
		var findAddStandard=$("input[name='addStandard']");
		for (var i = 0; i < findAddStandard.length; i++) {
			if($scope.isNull(findAddStandard[i].value)){
				alert("请检查颜色分类中的“规格”是否填写完整");
				return false;
			}
		}
		var findAddModel=$("input[name='addModel']");
		for (var i = 0; i < findAddModel.length; i++) {
			if($scope.isNull(findAddModel[i].value)){
				alert("请检查颜色分类中的“型号”是否填写完整");
				return false;
			}
		}
		var findAddPrice=$("input[name='addPrice']");
		for (var i = 0; i < findAddPrice.length; i++) {
			if($scope.isNull(findAddPrice[i].value)){
				alert("请检查颜色分类中的“价格”是否填写完整");
				return false;
			}
		}
		var findAddRepertory=$("input[name='addRepertory']");
		for (var i = 0; i < findAddRepertory.length; i++) {
			if($scope.isNull(findAddRepertory[i].value)){
				alert("请检查颜色分类中的“库存数量”是否填写完整");
				return false;
			}
		}
		return true;
	}
	
	//保存添加产品信息
	$scope.saveWallpaper=function(){
		//产品展示图
		for (var i = 1; i <= 5; i++) {
			var obj={};
			obj.id=$("#upload"+i+"_img").attr("imgId");
			obj.imgurl=$("#upload"+i+"_url").val();
			obj.type="1";
			if(obj.imgurl!=undefined&&obj.imgurl!=null&&obj.imgurl!=""){
				$scope.baseBrandProdImgEntities.push(obj);
			}
		}
		if($scope.baseBrandProdImgEntities.length==0){
			alert('请上传产品图，至少一张！');
			return;
		}
		
		if($scope.isNull($scope.from.product_title)){
			alert("产品标题是必填项");
		}else if($scope.isNull($("#sales_model").data("kendoComboBox").value().toString())){
			alert("请选择付款模式");
		}else if($scope.isNull($("#product_unit").data("kendoComboBox").value().toString())){
			alert("请选择计价单位");
		}else if($scope.verification()){
			$("#addBrandProductCtrlButton").attr("disabled","disabled");
			var caseImgLength=$("#uplodingImgController1").find(".caseImg");
			for (var j = 0; j < caseImgLength.length-1; j++) {
				var objImg={};
				objImg.id=$("#caseUpload_img"+j).attr("imgId");
				objImg.imgurl=$("#caseUpload_url"+j).val();
				objImg.type="2";
				if(objImg.imgurl!=undefined&&objImg.imgurl!=null&&objImg.imgurl!=""){
					$scope.baseBrandProdImgEntities.push(objImg);
				}
			}
			//筛选条件
			for (var i = 0; i < $scope.data.length; i++) {
				var json={};
				if($scope.data[i].ismultiple==1){ //多选框
					var MultiSelectValue=$("#attribute"+$scope.data[i].id).data("kendoMultiSelect").value().toString();
					var valArray=MultiSelectValue.split(",");
					for (var l = 0; l < valArray.length; l++) {
						var jsonm={};
						if(params.method=="edit"){
							jsonm.id=$scope.data[i].id;
						}
						jsonm.attr_id=$scope.data[i].id;
						jsonm.attr_value=valArray[l];
						$scope.brandProdAttrValueEntities.push(jsonm);
					}
				}else if($scope.data[i].isenum==1){ //是下拉框
					if(params.method=="edit"){
						json.id=$scope.data[i].id;
					}
					json.attr_id=$scope.data[i].id;
					json.attr_value=$("#attribute"+$scope.data[i].id).data("kendoComboBox").value().toString();
					$scope.brandProdAttrValueEntities.push(json);
				}else{
					if(params.method=="edit"){
						json.id=$scope.data[i].id;
					}
					json.attr_id=$scope.data[i].id;
					json.attr_value=$("#attribute"+$scope.data[i].id).val();
					$scope.brandProdAttrValueEntities.push(json);
				}
			}
			//所属分类
	       /* var treeView = $("#tree_line").data("kendoTreeView");
			$("#tree_line :checkbox").filter(function(){
				if($(this)[0].checked==true){
					var obj={};
					obj.series_id=$("#tree_line").data("kendoTreeView").dataItem($(this).parent().parent()).id;
					if(obj.series_id!=""&&obj.series_id!=null&&obj.series_id!=undefined){
						$scope.baseBrandProdSeriesEntities.push(obj);
					}
				}
			});*/
			//颜色分类
			var colurLen=$("#ColourClassifyId").find(".Colour").length;
			for (var y = 0; y < colurLen; y++) {
				var json={};
				var standardArray=[];
				json.color=$("#classifClass"+y).find("input[name='autonomouslyName']").val(); //颜色名称
				json.color_url=$("#ColourClassify_url"+y).val();//图片路径
				 var standardLen=$("#standard"+y).find(".Standard").length;
				 for (var t = 0; t < standardLen; t++) {
					 var jsons={};
					 jsons.id=$("#standard"+y+t).attr("colorid");
					 jsons.standard=$("#standard"+y+t).find("input[name='addStandard']").val();//规格
					 jsons.model=$("#standard"+y+t).find("input[name='addModel']").val();//型号
					 jsons.price=$("#standard"+y+t).find("input[name='addPrice']").val(); //价格
					 jsons.stock=$("#standard"+y+t).find("input[name='addRepertory']").val();//库存
					 standardArray.push(jsons);
				}
				 json.standardArray=standardArray;
				 $scope.prodSalesAttrEntity.push(json);
			}
			//console.log($scope.prodSalesAttrEntity);
			/*var editor=$("#container").data("kendoEditor");
			$scope.from.description = editor.value();//产品展示图
*/			//百度图文编辑器数据获取
			$scope.from.description=editor.getContent()
			$scope.from.product_unit = $("#product_unit").data("kendoComboBox").value().toString();//计价单位
			$scope.from.isnew = $("#newProduct").val();//是否新品
			$scope.from.new_time=$("#start_time").val();//设置上新时间
			$scope.from.sales_model = $("#sales_model").data("kendoComboBox").value().toString();//付款模式
			
			$scope.from.prodSalesAttrEntity=$scope.prodSalesAttrEntity;
			$scope.from.baseBrandProdImgEntities=$scope.baseBrandProdImgEntities;
			$scope.from.baseBrandProdSeriesEntities=$scope.baseBrandProdSeriesEntities;
			$scope.from.brandProdAttrValueEntities=$scope.brandProdAttrValueEntities;
			
			console.log(angular.toJson($scope.from));
			if(params.method=="edit"){
				if(confirm("您确定修改该产品吗？")){
					$http({
						url: path+"/server/modfiynewentproduct",
						method:'POST', 
						data:angular.toJson($scope.from),
						headers:  {
							"X-Object-Header" : "123456789 ",
				            "Content-Type": "application/json"
				        }
					}).success(function(data){
						console.log(data);
						if(data.code==0){
							alert("修改成功");
							$location.path("/enterpriseBrandProduct");
						}else{
							$scope.prodSalesAttrEntity=[];
							$scope.baseBrandProdImgEntities=[];
							$scope.baseBrandProdSeriesEntities=[];
							$scope.brandProdAttrValueEntities=[];
							alert("修改失败");
							window.location.reload();
						}
					}).error(function(data){
						alert(data);   
					}) 
				}else{
					$scope.prodSalesAttrEntity=[];
					$scope.baseBrandProdImgEntities=[];
					$scope.baseBrandProdSeriesEntities=[];
					$scope.brandProdAttrValueEntities=[];
					$("#addBrandProductCtrlButton").removeAttr("disabled");
				}
			}else{
				if(confirm("您确定保存该产品吗？")){
					$http({
						url: path+"/server/addnewentproduct",
						method:'POST', 
						data:angular.toJson($scope.from),
						headers:  {
							"X-Object-Header" : "123456789 ",
				            "Content-Type": "application/json"
				        }
					}).success(function(data){
						console.log(data);
						if(data.code==0){
							alert("保存成功");
							$location.path("/enterpriseBrandProduct");
						}else{
							$scope.prodSalesAttrEntity=[];
							$scope.baseBrandProdImgEntities=[];
							$scope.baseBrandProdSeriesEntities=[];
							$scope.brandProdAttrValueEntities=[];
							alert("保存失败");
							window.location.reload();
						}
					}).error(function(data){
						alert(data);   
					})
				}else{
					$scope.prodSalesAttrEntity=[];
					$scope.baseBrandProdImgEntities=[];
					$scope.baseBrandProdSeriesEntities=[];
					$scope.brandProdAttrValueEntities=[];
					$("#addBrandProductCtrlButton").removeAttr("disabled");
				}
			}
		}
	}
	//删除颜色分类removebrandprodsales
	$scope.deleteColour=function(len){
		var _parentElement=document.getElementById("classifClass"+len).parentNode;
        if(_parentElement){
        	var StandardArray=$("#classifClass"+len).find(".Standard");//.attr("colorid")
        	console.log(StandardArray);
        	for (var s = 0; s < StandardArray.length; s++) {
        		var color_id=$("#"+StandardArray[s].id).attr("colorid");
        		//删除
                 if(color_id!=null&&color_id!=""&&color_id!=undefined){
      		        $http({
      					url: path+"/server/removeentsaleattr/"+color_id,
      					method:'GET', 
      					headers:  {
      						"X-Object-Header" : "123456789 ",
      			            "Content-Type": "application/json"
      			        }
      				}).success(function(data){
      					console.log(data);
      				}).error(function(data){
      					alert(data);   
      				}) 
                  }	
			}
            _parentElement.removeChild(document.getElementById("classifClass"+len));
	     
        }
	}
});

