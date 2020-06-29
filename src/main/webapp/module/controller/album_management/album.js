App.controller("albumManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	
	$scope.search1 = {};	//专辑搜索表单
	$scope.search2 = {};	//文章搜索表单
	$scope.album = {};			//专辑
	$scope.article = {};		//图文
	var albumInfo = null;	//专辑信息
	var articleInfo = null;	//文章信息
	
	var limitParam1 = {imageSize:80, format:"jpg"};		//头图格式
	var limitParam2 = {imageSize:20, format:"jpg"};		//缩略图格式
	var limitParam3 = {imageSize:150, format:"jpg"};	//专栏图格式
	
	var editor = UE.getEditor('editor');
	if(editor.container!=undefined){
		editor.destroy();
		editor = UE.getEditor('editor');
	}
	
	
	
	//选择列表
	$scope.chooseList = function(type){
		for(var i=1;i<=3;i++){
			if(i==type){
				$("#list"+i).addClass("choose");
				$("#list"+i+"_add").show();
				$("#container"+i).show();
			}else{
				$("#list"+i).removeClass("choose");
				$("#list"+i+"_add").hide();
				$("#container"+i).hide();
			}
		}
		
		if(type==3){
			$scope.getImgInfo();	//获取图片信息  
		}
	}
	
	//专辑列表
	$scope.mainGridOptions1 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryabum",  
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									name: $scope.search1.name,	//专辑名称
									create_time: $("#publish_time1").val(),	//发布日期
								}
							};
							console.log('查询参数',parameter);
							return kendo.stringify(parameter);
						}
					},
				},
				pageSize : 10,
				serverPaging : true,
				serverSorting: true,
				schema : {
					data : function(d) {
						return d.data;
					},
					total : function(d) {
						return d.total; //总条数
					}
				},
			},
			pageable : {  //分页
				pageSizes: [10, 20, 50, 100],  //每页显示记录数
			  },   
			sortable: {   //排序
				//allowUnsort: false,  //允许无序
			    mode: "single"     //排序模式：single，multiple
			  },
			editable: false,  //true：单个编辑；popup：弹出新页面编辑
			columns : [
					{
						field : "top_img_url",         
						title : "专辑头图",     
						width : "100px",
						template:'<img style="width: 200px;height: 80px;" src="#: top_img_url #" />'
					},
					{
						field : "name",         
						title : "专辑名称",     
						width : "100px"    
					},
					{
						field : "create_time",         
						title : "发布日期",     
						width : "100px"
					},
					{
						field : "update_time",         
						title : "更新日期",     
						width : "100px"
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += ('<div class="k-state-default operation splitButtona" ng-click="editAlbum(\''+e.id+'\')">编辑</div>'
									+'<div class="k-state-default operation splitButtona" ng-click="deleteAlbum(\''+e.id+'\')">删除</div>'
									+'<div class="k-state-default operation splitButtona" ng-click="copyLink(5,\''+e.id+'\')">链接复制</div>');
							
							return html;
						}
					}
			]
		};
	
	//文章列表
	$scope.mainGridOptions2 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryheadcontent",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									
								}
							};
							console.log('查询参数',parameter);
							return kendo.stringify(parameter);
						}
					},
				},
				pageSize : 10,
				serverPaging : true,
				serverSorting: true,
				schema : {
					data : function(d) {
						return d.data;
					},
					total : function(d) {
						return d.total; //总条数
					}
				},
			},
			pageable : {  //分页
				pageSizes: [10, 20, 50, 100],  //每页显示记录数
			  },   
			sortable: {   //排序
				//allowUnsort: false,  //允许无序
			    mode: "single"     //排序模式：single，multiple
		    },
			editable: false,  //true：单个编辑；popup：弹出新页面编辑
			columns : [
					{
						field : "title",         
						title : "文章标题",     
						width : "120px"    
					},
					{
						field : "abum_name",         
						title : "所属专辑",     
						width : "100px"    
					},
					{
						field : "create_time",         
						title : "创建日期",     
						width : "120px",
						format: "{0: yyyy-MM-dd HH:mm:ss}"
					},
					{
						field : "likes_num",         
						title : "点赞量",     
						width : "100px"    
					},
					{
						field : "collection_num",         
						title : "收藏量",     
						width : "100px"    
					},
					{
						field : "evaluation_num",         
						title : "评价量",     
						width : "100px"    
					},
					{
						field : "share_num",         
						title : "分享量",     
						width : "100px"    
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += ('<div class="k-state-default operation splitButtona" ng-click="editArticle(\''+e.id+'\')">编辑</div>'
									+'<div class="k-state-default operation splitButtona" ng-click="deleteArticle(\''+e.id+'\')">删除</div>'
									+'<div class="k-state-default operation splitButtona" ng-click="copyLink(7,\''+e.id+'\')">链接复制</div>');
							
							return html;
						}
					}
			]
		};
	
	//查询
	$scope.search = function(type){
		if(type==1){
			$scope.grid1.dataSource.page(1);
		}else if(type==2){
			$scope.grid2.dataSource.page(1);
		}
	}
	
	//复制链接
	$scope.copyLink = function(type,id){
		var url = publicService.buildLinkUrl(type,id);
		$scope.linkUrl = url;
		$scope.linkUrlWindow.center().open();   //打开弹框
	}
	
	//新增
	$scope.add = function(type){
		if(type==1){
			$scope.album = {};
			publicService.removeUploadImage("logo1_img","logo1_url");
			
			$scope.addAlbumWindow.center().open();   //打开弹框
		}else if(type==2){
			$scope.article = {};
			$("#belongAlbum").data("kendoDropDownList").value(""); //专辑
			$("#publish_time2").val("");    //发布日期
			publicService.removeUploadImage("logo2_img","logo2_url");
			editor.setContent("");   	   //内容

			$("#belongAlbum").data("kendoDropDownList").dataSource.read();
			$scope.addArticleWindow.center().open();   //打开弹框
		}
	}
	
	//获取所有的专辑数据
	$scope.getAllAlbumData = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "id",
		    dataSource: {
			      serverFiltering: true,
		          transport: {
		              read: {
		            	  type : 'POST',
		                  url: path+"/server/queryabumlist",
		                  dataType : "json"
		              }
		          },
		          schema : {
						data : function(data) {
							data.unshift({id:"",name:"请选择"});
							return data;
						}
				  }
	       }
		});
	}

	//【专辑】添加
	$scope.addNewAlbum = function(){
		$scope.album.top_img_url = $("#logo1_url").val();  //头图
		
		//验证
		if(!$scope.album.name){
			alert("请填写【专辑名称】！");
			return;
		}
		if(!$scope.album.top_img_url){
			alert("请上传【头图】！");
			return;
		}
		
		var url = '';
		if($scope.album && $scope.album.id){
			url = path+'/server/modfiyabum';
		}else{
			url = path+'/server/addabum';
		}
		
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.album)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.addAlbumWindow.close();   //关闭
				$scope.grid1.dataSource.page(1);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//【专辑】编辑
	$scope.editAlbum = function(id){
		$http({
			   url: path+'/server/getabumbyid/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				albumInfo = data.result;

				$scope.album.id = albumInfo.id;
				$scope.album.name = albumInfo.name;			//专辑名称
				publicService.showUploadImage("logo1_img","logo1_url",albumInfo.top_img_url);			//文章标题
				
				$scope.addAlbumWindow.center().open();   //打开弹框
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//【专辑】删除
	$scope.deleteAlbum = function(id){
		if(confirm("确认删除本条专辑及专辑下的文章？")){
			$http({
				   url: path+'/server/removeabum/'+id,
				   method: 'GET'
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.grid1.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//【文章】添加
	$scope.addNewArticle = function(){
		$scope.article.abum_id = $("#belongAlbum").data("kendoDropDownList").value();  			//所属专辑
		$scope.article.create_time = $("#publish_time2").val();    //发布日期
		$scope.article.image = $("#logo2_url").val();  //缩略图
		$scope.article.details = editor.getContent();   //内容
		$scope.article.type = 1;    //文章类型（1：专辑，2：图文）
		$scope.article.state = 1;   //状态(0 过期 1 可用）

		//验证
		if(!$scope.article.abum_id){
			alert("请填写【所属专辑】！");
			return;
		}
		if(!$scope.article.title){
			alert("请填写【文章标题】！");
			return;
		}
		if(!$scope.article.digest){
			alert("请填写【文章概述】！");
			return;
		}
		if(!$scope.article.author){
			alert("请填写【文章作者】！");
			return;
		}
		if(!$scope.article.create_time){
			alert("请填写【发布日期】！");
			return;
		}
		if(!$scope.article.image){
			alert("请上传【缩略图】！");
			return;
		}
		if(!$scope.article.details){
			alert("请填写【图文详情】！");
			return;
		}
		
		var url = '';
		if($scope.article && $scope.article.id){  //编辑
			url = path+'/server/modfiyheadcontent';
		}else{//新增
			url = path+'/server/createheadcontent';
		}
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.article)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.addArticleWindow.close();   //关闭
				$scope.grid2.dataSource.page(1);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//【文章】编辑
	$scope.editArticle = function(id){
		$http({
			   url: path+'/server/getheadcontentbyid/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				articleInfo = data.result;
			
				$scope.article.id = articleInfo.id;
				$("#belongAlbum").data("kendoDropDownList").value(articleInfo.abum_id);  	//所属专辑
				$scope.article.title = articleInfo.title;			//文章标题
				$scope.article.digest = articleInfo.digest;			//文章概述
				$scope.article.author = articleInfo.author;			//文章作者
				$("#publish_time2").val(articleInfo.create_time);    //发布日期
				publicService.showUploadImage("logo2_img","logo2_url",articleInfo.image);   //缩略图上传
				editor.setContent(articleInfo.details);   	   //内容
				$scope.article.type = 1;    //文章类型（1：专辑，2：图文）
				$scope.article.state = 1;   //状态(0 过期 1 可用）
				
				$scope.addArticleWindow.center().open();   //打开弹框
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//【文章】删除
	$scope.deleteArticle = function(id){
		if(confirm("确认删除本条图文？")){
			$http({
				   url: path+'/server/removeheadcontent/'+id,
				   method: 'GET'
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.grid2.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	/*********************************************专辑设置*********************************************/
	var imgInfo = null;	//图片信息
	
	//获取图片信息  
	$scope.getImgInfo = function(){
		$http({
			   url: path+'/server/queryarticlecolumns',
			   method: 'POST' 
		}).success(function(data){
			if(data.code==0){  //成功
				imgInfo = data.result;
				$scope.showInfo(imgInfo,"imageContainer");
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert("获取图片信息失败！");   
		})
	}
	
	//显示专栏图
	$scope.showInfo = function(dataArr,containerId){  
		$("#"+containerId).html("");
		
		$.each(dataArr, function(index, OneObj){
			var html = '';
			var bannerIndex = "banner"+(index+1);
			var bannerIndex_img = bannerIndex+"_img";
			var bannerIndex_url = bannerIndex+"_url";
			
			html += ('<div class="col-sm-4 businessl">'
					   +'<div class="imgContainer3">'
						   +'<div class="imgBackground1">'
						   	   +'<span class="glyphicon glyphicon-plus"></span>'
						   +'</div>'
						   +'<div class="imgBackground2" id="'+bannerIndex_img+'"></div>'
						   +'<input type="file" name="file" id="'+bannerIndex+'"/>'
						   +'<input type="hidden" id="'+bannerIndex_url+'" imgId="'+OneObj.id+'"/>'
						   +'<span class="bannerJump">'
							   +'<button class="widths btn k-button" ng-click="contentJump(\''+OneObj.id+'\',\''+OneObj.link_type+'\',\''+OneObj.data_param+'\')">内容跳转</button>'
							   +'<button class="widths btn k-button" ng-click="deletePicture(\''+OneObj.id+'\')">删除</button>'
						   +'</span>'
					   +'</div>'
				   +'</div>');
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
			
			$scope.Upload(bannerIndex,bannerIndex_img,bannerIndex_url,limitParam3);            //实例化文件上传控件
			publicService.showUploadImage(bannerIndex_img,bannerIndex_url,OneObj.img_url);   //显示默认图片
		});
		
		var bannerCount = dataArr.length;	//Banner数量
		
		var html = '';
		var bannerIndex = "banner"+(bannerCount+1);
		var bannerIndex_img = bannerIndex+"_img";
		var bannerIndex_url = bannerIndex+"_url";
		
		html += ('<div class="col-sm-4">'
				   +'<div class="imgContainer3">'
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
		
		$scope.Upload(bannerIndex,bannerIndex_img,bannerIndex_url,limitParam3);            //实例化文件上传控件
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
	        	
	        	publicService.showUploadImage(imgId,imgUrlId,url);
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
	$scope.updateImgUrl = function(imgUrlId,type){
		var url = '';
		var imgData = {};
		
		var id = $('#'+imgUrlId).attr("imgId");
		var image_url = $('#'+imgUrlId).val();
		if(id){  //编辑
			url = path+'/server/modifyarticlecolumns';
			imgData.id = id;
			imgData.img_url = image_url;
		}else{	 //添加
			url = path+'/server/setarticlecolumn';
			imgData.img_url = image_url;
			imgData.sort = (imgInfo.length+1);
		}

		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson(imgData)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.chooseList(3);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert("图片保存失败！");   
		})
	}
	
	//内容跳转（专栏图）
	$scope.contentJump = function(from_id,link_type,to_id){
		var params = {method:"albumPictureSkip", fromId:from_id, linkType:link_type, toId:to_id};
		$location.path("/linkSkipManagement/"+angular.toJson(params));
	}
	
	//删除图片
	$scope.deletePicture = function(id){
		if(confirm("确认删除？")){
			$http({
				   url: path+'/server/deletearticlecolumns/'+id,
				   method: 'POST'
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.chooseList(3);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert("删除失败！");   
			})
		}
	}
	
	
	
	$scope.chooseList(1);   //选择列表
	
	publicService.initDate("publish_time1");
	publicService.uploadImage("logo1","logo1_img","logo1_url",limitParam1);		//头图
	
	$scope.getAllAlbumData("belongAlbum");  //获取所有的专辑数据
	publicService.initDateTime("publish_time2");
	publicService.uploadImage("logo2","logo2_img","logo2_url",limitParam2);		//缩略图
	
	
	


});