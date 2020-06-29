App.controller("interfaceCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	var imgInfo = new Object();   //图片信息
	$scope.banner = {};    			//Banner
	$scope.advertisement = {};    	//广告位
	
	var limitParam1 = {imageSize:150, format:"jpg"};	//Banner格式
	var limitParam2 = {imageSize:20, format:"png"};		//栏目图标格式
	var limitParam3 = {imageSize:20, format:"png"};		//标签栏图标格式
	var limitParam4 = {imageSize:150, format:"jpg/png"};	//广告位格式
	var limitParam5 = {imageSize:200, format:"jpg/png/gif"};	//启动广告图格式
	
	
	//获取图片信息  
	$scope.getImgInfo = function(type){
		var url = '';
		if(type==1){          //Banner
			url = path+'/server/getbannerlist';
		}else if(type==2){	  //栏目图标
			url = path+'/server/querycolumn';
		}else if(type==3){	  //标签栏图标
			url = path+'/server/querytagimg';
		}else if(type==4){    //广告位
			url = path+'/server/getbanneradlist';
		}else if(type==5){    //启动广告图
			url = path+'/server/getAdvertisements          ';
		}
		
		$http({
			   url: url,
			   method: 'GET' 
		}).success(function(data){
			if(type==1){          //Banner
				imgInfo.banner = data;
				$scope.showBannerInfo(imgInfo.banner,"bannerContainer");
			}else if(type==2){	  //栏目图标
				imgInfo.column = data.result;
				$scope.showColumnInfo(imgInfo.column,"columnContainer");
			}else if(type==3){	  //标签栏图标
				imgInfo.label = data.result;
				$scope.showLabelInfo(imgInfo.label,"labelContainer");
			}else if(type==4){    //广告位
				imgInfo.advertisement = data;
				$scope.showAdvertisementInfo(imgInfo.advertisement,"advertisementContainer");
			}else if(type==5){    //启动广告图
				imgInfo.startAdvertisement = data.result;
				$scope.showStartAdvertisementInfo(imgInfo.startAdvertisement,"startAdvertisementContainer");
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//显示Banner
	$scope.showBannerInfo = function(dataArr,containerId){  
		$.each(dataArr, function(index, OneObj){
			var html = '';
			var bannerIndex = "banner"+(index+1);
			var bannerIndex_img = bannerIndex+"_img";
			var bannerIndex_url = bannerIndex+"_url";
			var linkName = "link"+(index+1);   //链接
			
			html += ('<div class="col-sm-4 businessl">'
					   +'<div class="imgContainer1">'
						   +'<div class="imgBackground1">'
						   	   +'<span class="glyphicon glyphicon-plus"></span>'
						   +'</div>'
						   +'<div class="imgBackground2" id="'+bannerIndex_img+'"></div>'
						   +'<input type="file" name="file" id="'+bannerIndex+'"/>'
						   +'<input type="hidden" id="'+bannerIndex_url+'" imgId="'+OneObj.id+'"/>'
						   +'<span class="bannerJump">'
							   //+'<input type="text" class="col-sm-12 k-textbox" ng-model="banner.'+linkName+'"  placeholder="请输入连接地址"/>'
							   +'<button class="widths btn k-button" ng-click="contentJump(1,\''+OneObj.id+'\',\''+OneObj.link_type+'\',\''+OneObj.data_param+'\')">内容跳转</button>'
							   +'<button class="widths btn k-button" ng-click="deletePicture(1,\''+OneObj.id+'\')">删除</button>'
						   +'</span>'
						   
					   +'</div>'
				   +'</div>');
			
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
			
			$scope.banner[linkName] = OneObj.image_link;

			$scope.Upload(bannerIndex,bannerIndex_img,bannerIndex_url,1,limitParam1);            //实例化文件上传控件
			$scope.showUploadImg(bannerIndex_img,bannerIndex_url,OneObj.image_url);   //显示默认图片
		});
		
		var bannerCount = dataArr.length;	//Banner数量
		if(bannerCount<5){
			var html = '';
			var bannerIndex = "banner"+(bannerCount+1);
			var bannerIndex_img = bannerIndex+"_img";
			var bannerIndex_url = bannerIndex+"_url";
			var linkName = "link"+(bannerCount+1);   //链接
			
			html += ('<div class="col-sm-4">'
					   +'<div class="imgContainer1">'
						   +'<div class="imgBackground1">'
						   	   +'<span class="glyphicon glyphicon-plus"></span>'
						   +'</div>'
						   +'<div class="imgBackground2" id="'+bannerIndex_img+'"></div>'
						   +'<input type="file" name="file" id="'+bannerIndex+'"/>'
						   +'<input type="hidden" id="'+bannerIndex_url+'" imgId=""/>'
					   +'</div>'
				   +'</div>');
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);
			
			$scope.Upload(bannerIndex,bannerIndex_img,bannerIndex_url,1,limitParam1);            //实例化文件上传控件
		}
	}
	
	//显示栏目图标
	$scope.showColumnInfo = function(dataArr,containerId){
		$.each(dataArr, function(index, OneObj){
			var html = '';
			var columnIndex = "column"+(index+1);
			var columnIndex_img = columnIndex+"_img";
			var columnIndex_url = columnIndex+"_url";

			html += ('<div class="col-sm-3 businessl" style="margin:15px 0 0 0;">'
						+'<div class="imgContainer2">'
							+'<div class="imgBackground1">'
								+'<span class="glyphicon glyphicon-plus"></span>'
							+'</div>'
							+'<div class="imgBackground2" id="'+columnIndex_img+'"></div>'
							+'<input type="file" name="file" id="'+columnIndex+'"/>'
							+'<input type="hidden" id="'+columnIndex_url+'" imgId="'+OneObj.id+'"/>'
						+'</div>'
						+'<div class="text">'+OneObj.column_name+'</div>'
					+'</div>');
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
			
			$scope.Upload(columnIndex,columnIndex_img,columnIndex_url,2,limitParam2);            //实例化文件上传控件
			$scope.showUploadImg(columnIndex_img,columnIndex_url,OneObj.imgurl);   //显示默认图片
		});
	}
	
	//显示标签栏图标
	$scope.showLabelInfo = function(dataArr,containerId){
		$.each(dataArr, function(index, OneObj){
			var html = '';
			
			var labelIndex_first = "label"+(index+1)+"_first";
			var labelIndex_first_img = labelIndex_first+"_img";
			var labelIndex_first_url = labelIndex_first+"_url";
			
			var labelIndex_second = "label"+(index+1)+"_second";
			var labelIndex_second_img = labelIndex_second+"_img";
			var labelIndex_second_url = labelIndex_second+"_url";

			var imgContainerClassName = "";
			var imgFirstClassName="";
			var style="";
			if(OneObj.column_name=="DIY设计"){  //中间“DIY设计”
				imgContainerClassName = "imgContainer3_mid";
				imgFirstClassName="col-sm-2 businessl";
				imgFistName="width:16%";
				texts="width:120px;text-align:center";
			}else{ //其他四个
				imgContainerClassName = "imgContainer3";
				imgFirstClassName="col-sm-2 businessl";
				imgFistName="width:14%";
				texts="width:92px;text-align:center";
			}
			html += ('<div class="'+imgFirstClassName+'" style="'+imgFistName+'" >'
						+'<div class="'+imgContainerClassName+'">'
								+'<div class="imgBackground1">'
									+'<span class="glyphicon glyphicon-plus"></span>'
								+'</div>'
								+'<div class="imgBackground2" id="'+labelIndex_first_img+'"></div>'
								+'<input type="file" name="file" id="'+labelIndex_first+'"/>'
								+'<input type="hidden" id="'+labelIndex_first_url+'" imgId="'+OneObj.id+'"/>'
						+'</div>'
						+'<div class="'+imgContainerClassName+'" >'
								+'<div class="imgBackground1">'
									+'<span class="glyphicon glyphicon-plus"></span>'
								+'</div>'
								+'<div class="imgBackground2" id="'+labelIndex_second_img+'"></div>'
								+'<input type="file" name="file" id="'+labelIndex_second+'"/>'
								+'<input type="hidden" id="'+labelIndex_second_url+'" imgId="'+OneObj.id+'"/>'
						+'</div>'
						+'<div class="text" style="'+texts+'">'+OneObj.column_name+'</div>'
					+'</div>');
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
			
			var imgurlArr = OneObj.imgurl.split(",");
			$scope.Upload(labelIndex_first,labelIndex_first_img,labelIndex_first_url,3,limitParam3);    //实例化文件上传控件
			$scope.showUploadImgForColumn(labelIndex_first_img,labelIndex_first_url,imgurlArr[0]);   //显示默认图片
			
			$scope.Upload(labelIndex_second,labelIndex_second_img,labelIndex_second_url,3,limitParam3);    //实例化文件上传控件
			$scope.showUploadImgForColumn(labelIndex_second_img,labelIndex_second_url,imgurlArr[1]);   //显示默认图片
		});
	}
	
	//显示广告图
	$scope.showAdvertisementInfo = function(dataArr,containerId){
		$.each(dataArr, function(index, OneObj){
			var html = '';
			var advertisementIndex = "advertisement"+(index+1);
			var advertisementIndex_img = advertisementIndex+"_img";
			var advertisementIndex_url = advertisementIndex+"_url";
			var linkName = "link"+(index+1);   //链接
			
			html += ('<div class="col-sm-7 businessl">'
					   +'<div class="imgContainer4">'
						   +'<div class="imgBackground1">'
						   	   +'<span class="glyphicon glyphicon-plus"></span>'
						   +'</div>'
						   +'<div class="imgBackground2" id="'+advertisementIndex_img+'"></div>'
						   +'<input type="file" name="file" id="'+advertisementIndex+'"/>'
						   +'<input type="hidden" id="'+advertisementIndex_url+'" imgId="'+OneObj.id+'"/>'
					   	   +'<span class="bannerJump">'
						   	   //+'<input type="text" class="col-sm-12 k-textbox" ng-model="advertisement.'+linkName+'" placeholder="请输入连接地址"/>'
							   +'<button class="col-sm-12 btn k-button whollys" ng-click="contentJump(4,\''+OneObj.id+'\',\''+OneObj.link_type+'\',\''+OneObj.data_param+'\')">内容跳转</button>'
						   +'</span>'
						  
					   +'</div>'
				   +'</div>');
			
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
			
			$scope.advertisement[linkName] = OneObj.image_link;

			$scope.Upload(advertisementIndex,advertisementIndex_img,advertisementIndex_url,4,limitParam4);            //实例化文件上传控件
			$scope.showUploadImg(advertisementIndex_img,advertisementIndex_url,OneObj.image_url);         //显示默认图片
		});
	}
	
	//显示启动广告图
	$scope.showStartAdvertisementInfo = function(dataArr,containerId){  
		$.each(dataArr, function(index, OneObj){
			var html = '';
			var startAdvertisementIndex = "startAdvertisement"+(index+1);
			var startAdvertisementIndex_img = startAdvertisementIndex+"_img";
			var startAdvertisementIndex_url = startAdvertisementIndex+"_url";
			
			html += ('<div class="col-sm-2 businessl">'
					   +'<div class="imgContainer5">'
						   +'<div class="imgBackground1">'
						   	   +'<span class="glyphicon glyphicon-plus"></span>'
						   +'</div>'
						   +'<div class="imgBackground2" id="'+startAdvertisementIndex_img+'"></div>'
						   +'<input type="file" name="file" id="'+startAdvertisementIndex+'"/>'
						   +'<input type="hidden" id="'+startAdvertisementIndex_url+'" imgId="'+OneObj.id+'"/>'
						   +'<span class="bannerJump">'
							   +'<button class="widths btn k-button" ng-click="deletePicture(5,\''+OneObj.id+'\')">删除</button>'
						   +'</span>'
					   +'</div>'
				   +'</div>');
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
			
			$scope.Upload(startAdvertisementIndex,startAdvertisementIndex_img,startAdvertisementIndex_url,5,limitParam5);            //实例化文件上传控件
			$scope.showUploadImg(startAdvertisementIndex_img,startAdvertisementIndex_url,OneObj.url);   //显示默认图片
		});
		
		var startAdvertisementCount = dataArr.length;	//启动广告图数量
		if(startAdvertisementCount<5){
			var html = '';
			var startAdvertisementIndex = "startAdvertisement"+(startAdvertisementCount+1);
			var startAdvertisementIndex_img = startAdvertisementIndex+"_img";
			var startAdvertisementIndex_url = startAdvertisementIndex+"_url";
			
			html += ('<div class="col-sm-2">'
					   +'<div class="imgContainer5">'
						   +'<div class="imgBackground1">'
						   	   +'<span class="glyphicon glyphicon-plus"></span>'
						   +'</div>'
						   +'<div class="imgBackground2" id="'+startAdvertisementIndex_img+'"></div>'
						   +'<input type="file" name="file" id="'+startAdvertisementIndex+'"/>'
						   +'<input type="hidden" id="'+startAdvertisementIndex_url+'" imgId=""/>'
					   +'</div>'
				   +'</div>');
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);
			
			$scope.Upload(startAdvertisementIndex,startAdvertisementIndex_img,startAdvertisementIndex_url,5,limitParam5);            //实例化文件上传控件
		}
	}
	
	//上传图片
	$scope.Upload = function(containerId,imgId,imgUrlId,type,limitParam){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/updoad"
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
	        	var url = e.response.result;   //图片路径
	        	
	        	$scope.showUploadImg(imgId,imgUrlId,url);
	        	$scope.updateImgUrl(imgUrlId,type);
		    },
	        error: function(e){
	        	//alert('失败！');
	        },
	        remove: function(e){
	        	//alert('移除！');
	        },
	        select: function(e){	//选择
	        	var image = e.files[0];
	        	if(limitParam){
	        		if(limitParam.imageSize && image.size>(limitParam.imageSize*1024)){
		        		alert("上传图片的大小请限制在【"+limitParam.imageSize+"Kb】以内！");
		        		e.preventDefault();
		        		return;
		        	}
	        		if(limitParam.format && limitParam.format.indexOf(image.extension.substr(1,image.extension.length).toLowerCase())==-1){
		        		alert("上传图片仅支持【"+limitParam.format+"】格式！");
		        		e.preventDefault();
		        		return;
		        	}
	        	}
	        }
	      
	    });
	}
	
	//更改图片路径
	$scope.updateImgUrl = function(imgUrlId,type){
		var url = '';
		var imgData = {};
		
		if(type==1 || type==4){          //Banner，广告位
			var id = $('#'+imgUrlId).attr("imgId");
			var image_url = $('#'+imgUrlId).val();
			if(id){  //编辑
				url = path+'/server/modfiybanner';
				imgData.id = id;
				imgData.image_url = image_url;
			}else{	 //添加
				url = path+'/server/setbanner';
				imgData.image_url = image_url;
				imgData.sort = (imgInfo.banner.length+1);
			}
		}else if(type==2){	  //栏目图标
			url = path+'/server/modfiycolumn';
			imgData.id = $('#'+imgUrlId).attr("imgId");
			imgData.imgurl = $('#'+imgUrlId).val();
		}else if(type==3){	  //标签栏图标
			url = path+'/server/modfiytagimg';
			imgData.id = $('#'+imgUrlId).attr("imgId");
			
			var idArr = imgUrlId.split("_");
			var id_first = idArr[0]+"_first_"+idArr[2];
			var id_second = idArr[0]+"_second_"+idArr[2];
			imgData.imgurl = $('#'+id_first).val()+","+$('#'+id_second).val();
		}else if(type==5){          //启动广告图
			var id = $('#'+imgUrlId).attr("imgId");
			var image_url = $('#'+imgUrlId).val();
			if(id){  //编辑
				url = path+'/server/modifyAdvertisement';
				imgData.id = id;
				imgData.url = image_url;
			}else{	 //添加
				url = path+'/server/addAdvertisement';
				imgData.url = image_url;
			}
		}

		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson(imgData)
		}).success(function(data){
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//反显图片并赋值
	$scope.showUploadImg = function(imgId,imgUrlId,url){
		$("#"+imgId).css({
    		"background": "url('"+url+"') no-repeat",
    	    "background-size":"100% 100%",
    	    "background-color":"white"
    	});
		$('#'+imgUrlId).val(url);
	}
	
	//反显图片并赋值(标签栏图标)
	$scope.showUploadImgForColumn = function(imgId,imgUrlId,url){
		$("#"+imgId).css({
    		"background": "url('"+url+"') no-repeat",
    	    "background-size":"60%",
    	    "background-position":"center",
    	    "background-color":"white"
    	});
		$('#'+imgUrlId).val(url);
	}
	
	//删除图片
	$scope.deletePicture = function(type,id){
		if(confirm("确认删除？")){
			if(type==1){	//Banner
				$http({
					   url: path+'/server/removemainbanner/'+id,
					   method: 'GET'
				}).success(function(data){
					if(data.code==0){  //成功
						window.location.reload();   //页面刷新
					}else if(data.code==1){  //失败
						alert(data.message);
					}
				}).error(function(data){
					alert("删除失败！");   
				})
			}else if(type==5){	//启动广告图
				$http({
					   url: path+'/server/deleteAdvertisement',
					   method: 'POST',
					   data: angular.toJson({id:id})
				}).success(function(data){
					if(data.code==0){  //成功
						window.location.reload();   //页面刷新
					}else if(data.code==1){  //失败
						alert(data.message);
					}
				}).error(function(data){
					alert("删除失败！");   
				})
			}
		}
	}


	$scope.getImgInfo(1);  //Banner
	$scope.getImgInfo(2);  //栏目图标 
	$scope.getImgInfo(3);  //标签栏图标
	$scope.getImgInfo(4);  //广告位
	$scope.getImgInfo(5);  //启动广告图
	

	//内容跳转（1：Banner,4：广告位）
	$scope.contentJump = function(type,from_id,link_type,to_id){
		var params = {method:"bannerSkip1", fromId:from_id, linkType:link_type, toId:to_id};
		$location.path("/linkSkipManagement/"+angular.toJson(params));
	}
	

});
