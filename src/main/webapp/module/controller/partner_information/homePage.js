App.controller("homePageCtrl",function($scope, $rootScope, $location, $http, $compile, path) {
	
	var imgInfo = new Object();   //图片信息
	$scope.banner = {};    			//Banner
	
	var limitParam1 = {imageSize:150, format:"jpg"};	//Banner格式
	
	
	//获取Banner图片信息  
	$scope.getBannerInfo = function(){
		$http({
			   url: path+'/server/getbannerlist',
			   method: 'GET' 
		}).success(function(data){
			imgInfo.banner = data;
			$scope.showBannerInfo(imgInfo.banner,"bannerContainer");
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
			
			html += ('<div class="col-sm-4">'
					   +'<div class="imgContainer1">'
						   +'<div class="imgBackground1">'
						   	   +'<span class="glyphicon glyphicon-plus"></span>'
						   +'</div>'
						   +'<div class="imgBackground2" id="'+bannerIndex_img+'"></div>'
						   +'<input type="file" name="file" id="'+bannerIndex+'"/>'
						   +'<input type="hidden" id="'+bannerIndex_url+'" imgId="'+OneObj.id+'"/>'
						   +'<span class="bannerJump">'
							   //+'<input type="text" class="col-sm-12 k-textbox" ng-model="banner.'+linkName+'"  placeholder="请输入连接地址"/>'
							   +'<button class="col-sm-6 btn k-button" ng-click="contentJump(1,\''+OneObj.id+'\',\''+OneObj.link_type+'\',\''+OneObj.data_param+'\')">内容跳转</button>'
							   +'<button class="col-sm-6 btn k-button" ng-click="deletePicture(1,\''+OneObj.id+'\')">删除</button>'
						   +'</span>'
					   +'</div>'
				   +'</div>');
			
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
			
			$scope.banner[linkName] = OneObj.image_link;

			$scope.Upload(bannerIndex,bannerIndex_img,bannerIndex_url,limitParam1);            //实例化文件上传控件
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
			
			$scope.Upload(bannerIndex,bannerIndex_img,bannerIndex_url,limitParam1);            //实例化文件上传控件
		}
	}

	//上传图片
	$scope.Upload = function(containerId,imgId,imgUrlId,limitParam){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/updoad"
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

	        	$scope.showUploadImg(imgId,imgUrlId,url);
	        	$scope.updateImgUrl(imgUrlId);
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
	        		if(image.size>(limitParam.imageSize*1024)){
		        		alert("上传图片的大小请限制在【"+limitParam.imageSize+"Kb】以内！");
		        		e.preventDefault();
		        		return;
		        	}
		        	if(image.extension.indexOf(limitParam.format)==-1){
		        		alert("上传图片仅支持【"+limitParam.format+"】格式！");
		        		e.preventDefault();
		        		return;
		        	}
	        	}
	        }
	      
	    });
	}
	
	//更改图片路径
	$scope.updateImgUrl = function(imgUrlId){
		var url = '';
		var imgData = {};
		
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
	
	//删除图片
	$scope.deletePicture = function(type,id){
		if(confirm("确认删除？")){
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
		}
	}


	$scope.getBannerInfo();  //Banner
	

	//内容跳转（1：Banner,4：广告位）
	$scope.contentJump = function(type,from_id,link_type,to_id){ 
		var params = {method:"bannerSkip3", fromId:from_id, linkType:link_type, toId:to_id};
		$location.path("/linkSkipManagement/"+angular.toJson(params));
	}

});
