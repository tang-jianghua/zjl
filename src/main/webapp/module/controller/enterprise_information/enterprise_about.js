App.controller("enterpriseAboutCtrl",function($scope, $rootScope, $location, $http, $compile, $timeout, path, publicService) {
	

	var editor1 = null;	//编辑器（公司简介）
	var editor2 = null;	//编辑器（发展历程）
	var showModule = 1;        //显示的模块
	var showClassId = 0;	   //显示的分类名称id
	
	var companyProfile = null;		//公司简介
	var developHistory = null;		//发展历程
	var limitParam1 = {imageSize:200, format:"jpg"};	//图片格式

	
	//选择模块
	$scope.chooseModule = function(type){
		showModule = type;
		for(var i=1;i<=5;i++){
			if(i==type){
				$("#module"+i).addClass("chooseModule");
				$("#container"+i).show();
			}else{
				$("#module"+i).removeClass("chooseModule");
				$("#container"+i).hide();
			}
		}
		
		if(type==1){		//公司简介
			$scope.getCompanyProfile();
		}else if(type==2){  //发展历程
			$scope.getDevelopHistory();
		}else if(type==3){  //工程案例
			$scope.getClassList(type,"class3");
		}else if(type==4){  //企业荣誉
			$scope.getClassList(type,"class4");
		}else if(type==5){  //店面风采
			$scope.getClassList(type,"class5");
		}
	}
	
	//初始化图文编辑器
	$scope.initEditor = function(editorId,type){
		var option = {initialFrameHeight:500};

		if(UE.getEditor(editorId,option).container){
			UE.getEditor(editorId).destroy();
			if(type==1){
				editor1 = UE.getEditor(editorId,option);
			}else if(type==2){
				editor2 = UE.getEditor(editorId,option);
			}
		}else{
			if(type==1){
				editor1 = UE.getEditor(editorId,option);
			}else if(type==2){
				editor2 = UE.getEditor(editorId,option);
			}
		}
	}

	/**************************公司简介**********************************/
	//获取公司简介
	$scope.getCompanyProfile = function(){
		$http({
			   url: path+"/server/getprofiles",
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				if(data.result){
					companyProfile = data.result;
					
					$timeout(function(){
						editor1.setContent(companyProfile.enterprise_html);
				    }, 1000);
				}
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert("获取公司简介信息失败！");   
		})
	}
	
	//编辑公司简介
	$scope.editCompanyProfile = function(){
		var url = '';
		if(companyProfile && companyProfile.id){
			url = path+"/server/modifyprofiles";
			companyProfile.enterprise_html = editor1.getContent();
		}else{
			url = path+"/server/addprofiles";
			companyProfile = {};
			companyProfile.enterprise_html = editor1.getContent();
		}
		
		//参数验证
		if(!companyProfile.enterprise_html){
			alert("请填写【公司简介】！");
			return;
		}
		
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson(companyProfile)
		}).success(function(data){
			alert(data.message);
			$scope.getCompanyProfile();
		}).error(function(data){
			alert(data);    
		})
	}
	
	/**************************发展历程**********************************/
	//获取发展历程
	$scope.getDevelopHistory = function(){
		$http({
			   url: path+"/server/getdevelopment",
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				if(data.result){
					developHistory = data.result;
					
					$timeout(function(){
						editor2.setContent(developHistory.enterprise_html);
				    }, 1000);
				}
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert("获取发展历程信息失败！");   
		})
	}
	
	//编辑发展历程
	$scope.editDevelopHistory = function(){
		var url = '';
		if(developHistory && developHistory.id){
			url = path+"/server/modifydevelopment";
			developHistory.enterprise_html = editor2.getContent();
		}else{
			url = path+"/server/adddevelopment";
			developHistory = {};
			developHistory.enterprise_html = editor2.getContent();
		}
		
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson(developHistory)
		}).success(function(data){
			alert(data.message);
			$scope.getDevelopHistory();
		}).error(function(data){
			alert(data);    
		})
	}
	
	/**************************工程案例，企业荣誉，店面风采**********************************/
	
	//获取分类数据列表
	$scope.getClassList = function(type,containerId){
		$http({
			   url : path+"/server/getEnterpriseInfoOne/"+(type-1),
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				var classData = data.result;
				
				if(classData.length>0){
					$scope.showData(type,containerId,classData);	//显示数据
					$scope.chooseClass(type,classData[0].id);		//选择
				}else{
					showClassId = null;
					$("#class"+type).html("");
					$("#imagesContainer"+type).html("");
				}
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert("获取信息失败！");   
		})
	}
	
	//显示数据
	$scope.showData = function(type,containerId,dataArr){
		var html = '';
		
		$.each(dataArr, function(index, OneObj){
			var id = type+"_"+OneObj.id;
			html += '<div class="col-sm-12 oneClass" style="padding:0">'
						+'<div class="decorateLeft" id="'+id+'" ng-click="chooseClass('+type+',\''+OneObj.id+'\')" >'+OneObj.name+'</div>'
						+'<div class="decorateRight outPadding" ng-click="editData(\''+OneObj.id+'\',\''+OneObj.name+'\')"><i class="fa fa-pencil-square-o" ></i></div>'
						+'<div class="decorateRight outPadding" ng-click="deleteData(\''+OneObj.id+'\')"><i class="fa fa-times"></i></div>'
					+'</div>';
			
		});
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#"+containerId).html(html);
	}
	
	//选择
	$scope.chooseClass = function(type,id){
		var divId = type+"_"+id;
		angular.element("#"+divId).parent("div").parent("div").children("div").removeClass("choose");
		angular.element("#"+divId).parent("div").addClass("choose");	
		
		showClassId = id;
		
		if(type==3){		//行业分类
			$scope.getImageInfoList(showClassId,type);
		}else if(type==4){	//品类分类
			$scope.getImageInfoList(showClassId,type);
		}else if(type==5){	//企业分类
			$scope.getImageInfoList(showClassId,type);
		}
	}
	
	//获取图片信息列表
	$scope.getImageInfoList = function(classId,type){
		$http({
			   url : path+"/server/getEnterpriseInfoTow/"+classId,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				var imageDataArr = data.result;
				
				$scope.showImageInfo(imageDataArr,type);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert("获取图片信息失败！");   
		})
	}
	
	//显示图片信息
	$scope.showImageInfo = function(dataArr,type){
		var html = '';
		
		$.each(dataArr, function(index, OneObj){
			html += ('<div class="col-sm-3" style="padding:5px;">'
						+'<div>'
							+'<div class="oneImage" style="background: url(\''+OneObj.img_url+'\');background-size:100% 100%;">'
								+'<div class="operate">'
									+'<div><input type="checkbox" class="checkbox_image_'+type+'" value="'+OneObj.id+'"></div>'
								+'</div>'
							+'</div>'
						+'</div>'
						+'<div class="text">'+OneObj.name+'</div>'
					+'</div>');
		});
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#imagesContainer"+type).html(html);
	}

	//删除
	$scope.deleteData = function(id,name){
		if(confirm("确认删除？")){
			$http({
				   url: path+"/server/removeEnterpriseInfoOne/"+id,
				   method: 'GET'
			}).success(function(data){
				if(data.code==0){  //成功
					if(showModule==3){
						$scope.getClassList(showModule,"class3");
					}else if(showModule==4){
						$scope.getClassList(showModule,"class4");
					}else if(showModule==5){
						$scope.getClassList(showModule,"class5");
					}
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);    
			})
		}
	}
	
	//编辑
	$scope.editData = function(id,name){
		$scope.newClass = {};
		$scope.newClass.id = id;
		$scope.newClass.name = name;
		$scope.addClassWindow.center().open();   //打开弹框
	}
	
	//新建
	$scope.addClass = function(){
		$scope.newClass = {};
		$scope.addClassWindow.center().open();   //打开弹框
	}
	
	//添加新的分类
	$scope.addNewClass = function(){
		var url = '';
		if($scope.newClass.id){
			url = path+"/server/modifyenterpriseinfoone";
		}else{
			url = path+"/server/addenterpriseinfoone";
		}
		if(showModule==3){
			$scope.newClass.type = 2;
		}else if(showModule==4){
			$scope.newClass.type = 3;
		}else if(showModule==5){
			$scope.newClass.type = 4;
		}
		
		//验证
		var name_length = $scope.newClass.name.length;
		if(name_length<2 || name_length>6){
			alert("【分类名称】字数在[2~6]范围内！");
			return;
		}
		
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.newClass)
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				$scope.addClassWindow.close();
				if(showModule==3){
					$scope.getClassList(showModule,"class3");
				}else if(showModule==4){
					$scope.getClassList(showModule,"class4");
				}else if(showModule==5){
					$scope.getClassList(showModule,"class5");
				}
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("添加失败！");    
		})
	}
	
	//全选
	$scope.chooseAll = function(className,type){
		publicService.chooseAllByClassName(className,type);
	}
	
	//批量上传
	$scope.multipleUpload = function(){
		if(showClassId){
			$("#uploadContainer").html("");
			$scope.multipleUploadWindow.center().open();   //打开弹框
		}else{
			alert('请选择对应的分类！');
		}
	}
	
	//批量删除
	$scope.multipleDelete = function(){
		var idArr = publicService.getChooseValueByClassName("checkbox_image_"+showModule);
		
		if(idArr.length==0){
			alert("请选择要删除的图片！");
			return;
		}
		
		if(confirm("确认删除？")){
			$http({
				   url: path+"/server/removeEnterpriseInfoTwo",
				   method: 'POST',
				   data: angular.toJson(idArr)
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.getImageInfoList(showClassId,showModule);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);    
			})
		}
	}
	
	//上传图片（多个）
	$scope.uploadMultipleImage = function(containerId,pannelId,limitParam){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/uploadimageswithsize",
	            batch: true
	        },
	     	multiple: true,      //多选
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
	        	if(e.response.code==0){
	        		var urlArr = e.response.result;   //图片路径
		        	$scope.showUploadImages(urlArr,pannelId);
	        	}else{
	        		alert('上传失败！');
	        	}
		    },
	        error: function(e){
	        	//alert('失败！');
	        },
	        remove: function(e){
	        	//alert('移除！');
	        },
	        select: function(e){	//选择
	        	var imageArr = $.extend(true, [], e.files);
	        	var length = imageArr.length;
	        	
	        	$.each(imageArr.reverse(), function(index, OneObj){
	        		var image = OneObj;
	        		if(limitParam){
		        		if(limitParam.imageSize && image.size>(limitParam.imageSize*1024)){
			        		alert("上传图片【“"+image.name+"”'】的大小请限制在【"+limitParam.imageSize+"Kb】以内！");
			        		e.files.splice(length-1-index,1);
			        		//e.preventDefault();
			        		return;
			        	}
			        	if(limitParam.format && image.extension.indexOf(limitParam.format)==-1){
			        		alert("上传图片仅支持【"+limitParam.format+"】格式！");
			        		e.files.splice(length-1-index,1);
			        		//e.preventDefault();
			        		return;
			        	}
		        	}
	        	});
	        	
	        	console.log(e.files);
	        }
	    });
	}
	
	//显示上传的图片
	$scope.showUploadImages = function(urlArr,pannelId){
		var html = '';
		
		$.each(urlArr, function(index, OneObj){
			html += ('<div class="col-sm-3 imageContainer" imageUrl="'+OneObj.url+'" imageWidth="'+OneObj.width+'" imageHeight="'+OneObj.height+'">'
						+'<div>'
							+'<div class="oneImage" style="background: url(\''+OneObj.url+'\');background-size:100% 100%;">'
								+'<div class="operate">'
									+'<div ng-click="removeImage(\''+OneObj.url+'\')"><i class="fa fa-times" aria-hidden="true"></i></div>'
								+'</div>'
							+'</div>'
						+'</div>'
						+'<div class="center">'
							+'<input type="text" class="k-textbox" placeholder="请输入标题名称"/>'
						+'</div>'
					+'</div>');
		});
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#"+pannelId).append(html);
	}
	
	//移除
	$scope.removeImage = function(imageUrl){
		$("#uploadContainer").find("div[imageUrl='"+imageUrl+"']").remove();
	}

	//添加多个图片
	$scope.addImages = function(){
		var postData = new Array();
		
		var imgArr = $("#uploadContainer").children("div");
		$.each(imgArr, function(index, OneObj){
			var oneImg = {};
			oneImg.one_id = showClassId;
			oneImg.img_url = $(this).attr("imageurl");
			oneImg.width = $(this).attr("imageWidth");
			oneImg.height = $(this).attr("imageHeight");
			oneImg.name = $(this).children("div:eq(1)").children("input:eq(0)").val();
			
			postData.push(oneImg);
		});
		
		if(postData.length==0){
			alert("请选择上传的图片！");
			return;
		}

		$http({
			   url: path+"/server/addenterpriseinfotwo",
			   method: 'POST',
			   data: angular.toJson(postData)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.multipleUploadWindow.close();
				$scope.getImageInfoList(showClassId,showModule);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);    
		})
	}
	
	
	

	$scope.initEditor("editor1",1);	//编辑器（公司简介）
	$scope.initEditor("editor2",2);	//编辑器（发展历程）
	$scope.uploadMultipleImage("multipleUpload","uploadContainer",limitParam1);
	
	$scope.chooseModule(1);   //选择模块

});