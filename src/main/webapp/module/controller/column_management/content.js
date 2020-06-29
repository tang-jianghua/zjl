App.controller("contentCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	$scope.album = {};			//专辑
	$scope.article = {};		//图文
	
	var limitParam1 = {imageSize:80, format:"jpg"};		//头图格式
	var limitParam2 = {imageSize:20, format:"jpg"};		//缩略图格式
	
	var editor = UE.getEditor('editor');
	if(editor.container!=undefined){
		editor.destroy();
		editor = UE.getEditor('editor');
	}
	
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
			    mode: "multiple"     //排序模式：single，multiple
			  },
			/*editable: {   //编辑
			    mode: "inline"   //整行编辑
			  },*/
			editable: false,  //true：单个编辑；popup：弹出新页面编辑
			columns : [
					{
						field : "top_img_url",         
						title : "头图显示",     
						width : "100px",
						template:'<img style="width: 185px;height: 69px;" src="#: top_img_url #" />'
					},
					{
						field : "create_time",         
						title : "创建日期",     
						width : "100px",
						format: "{0: yyyy-MM-dd HH:mm:ss}"
					},
					{
						field : "update_time",         
						title : "更新日期",     
						width : "100px",
						format: "{0: yyyy-MM-dd HH:mm:ss}"
					},
					{
						field : "name",         
						title : "专辑名称",     
						width : "100px"    
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div  value='编辑' class=' k-state-default operation splitButtona' ng-click='specialEditData(\"#: id #\")' />编辑</div>" +
								"<div  value='删除' class=' k-state-default operation splitButtona' ng-click='specialDeleteData(\"#: id #\")' />删除</div>"+
								"<div value='链接复制' class=' k-state-default operation splitButtona' ng-click='copyLink(5,\"#: id #\")'/>链接复制</div>" 

					}
			]
		};
	

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
			    mode: "multiple"     //排序模式：single，multiple
			  },
			/*editable: {   //编辑
			    mode: "inline"   //整行编辑
			  },*/
			editable: false,  //true：单个编辑；popup：弹出新页面编辑
			columns : [
					{
						field : "title",         
						title : "文章标题",     
						width : "120px"    
					},
					{
						field : "create_time",         
						title : "创建日期",     
						width : "120px",
						format: "{0: yyyy-MM-dd HH:mm:ss}"
					},
					{
						field : "abum_name",         
						title : "所属专辑",     
						width : "100px"    
					},
					{
						field : "seen_time",         
						title : "点击量",     
						width : "100px"    
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div  value='编辑' class=' k-state-default operation splitButtona' ng-click='editData(\"#: id #\")' />编辑</div>"
							      +"<div  value='删除' class=' k-state-default operation splitButtona' ng-click='imgTextDeleteData(\"#: id #\")' />删除</div>"
							      +"<div value='链接复制' class=' k-state-default operation splitButtona' ng-click='copyLink(7,\"#: id #\")' />链接复制</div>" 
					}
			]
		};
	
	//内容管理专辑列表删除
	$scope.specialDeleteData=function(id){
		if(confirm("是否确认删除本条专辑及专辑下的图文？")){
			$http({
				   url: path+'/server/removeabum/'+id,
				   method: 'GET'
			}).success(function(data){
				alert(data.message);
				$scope.grid1.dataSource.page(1);
			}).error(function(data){
				alert(data);   
			})
		}
	}
	//内容管理图文列表删除
	$scope.imgTextDeleteData=function(id){
		if(confirm("是否确认删除本条图文？")){
			$http({
				   url: path+'/server/removeheadcontent/'+id,
				   method: 'GET'
			}).success(function(data){
				alert(data.message);
				$scope.grid2.dataSource.page(1);
			}).error(function(data){
				alert(data);   
			})
		}
	}
	//复制链接
	$scope.copyLink = function(type,id){
		var url = publicService.buildLinkUrl(type,id);
		$scope.linkUrl = url;
		$scope.linkUrlWindow.center().open();   //打开弹框
	}
	//专辑编辑
	$scope.specialEditData=function(id){
		$http({
			   url: path+'/server/getabumbyid/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				var dataObj = data.result;
				$scope.album.id = dataObj.id;
				$scope.album.name = dataObj.name;			//专辑名称
				publicService.showUploadImage("logo1_img","logo1_url",dataObj.top_img_url);			//文章标题
				$scope.addAlbumWindow.center().open();   //打开弹框
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//图文编辑
	$scope.editData = function(id){
		$http({
			   url: path+'/server/getheadcontentbyid/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				var dataObj = data.result;
				
				$scope.article.id = dataObj.id;
				$("#belongAlbum").data("kendoDropDownList").value(dataObj.abum_id);  	//所属专辑
				$scope.article.title = dataObj.title;			//文章标题
				$scope.article.digest = dataObj.digest;			//文章概述
				$scope.article.author = dataObj.author;			//文章作者
				$("#publish_time").val(dataObj.create_time);    //发布日期
				publicService.showUploadImage("logo2_img","logo2_url",dataObj.image);   //缩略图上传
				editor.setContent(dataObj.details);   	   //内容
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
	
	
	//选择列表
	$scope.chooseList = function(type){
		for(var i=1;i<=2;i++){
			if(i==type){
				$("#list"+i).addClass("choose");
				$("#list"+i+"_add").show();
				$("#list"+i+"_grid").show();
			}else{
				$("#list"+i).removeClass("choose");
				$("#list"+i+"_add").hide();
				$("#list"+i+"_grid").hide();
				
			}
		}
		
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
			$("#publish_time").val("");    //发布日期
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
	
	//初始化时间控件
	$scope.initDateTime = function(containerId){
		$("#"+containerId).kendoDateTimePicker({
		     value: "",
		     culture: "zh-CN",
		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
		     interval: 30,   				  //时间间隔
		     timeFormat: "HH:mm" 			  //24 hours format
		});
	}
	
	
	/*******************************图文编辑器(end)*******************************/
	
	//添加专辑
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
		if($scope.album.id){
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
	
	//添加图文
	$scope.addNewArticle = function(){
		$scope.article.abum_id = $("#belongAlbum").data("kendoDropDownList").value();  			//所属专辑
		$scope.article.create_time = $("#publish_time").val();    //发布日期
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
		if($scope.article.id){  //编辑
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
	
	
	
	$scope.chooseList(1);   //选择列表
	
	publicService.uploadImage("logo1","logo1_img","logo1_url",limitParam1);		//头图
	
	$scope.getAllAlbumData("belongAlbum");  //获取所有的专辑数据
	$scope.initDateTime("publish_time");
	publicService.uploadImage("logo2","logo2_img","logo2_url",limitParam2);		//缩略图
	
	publicService.uploadImage("localPicture","localPicture_img","localPicture_url");		//上传本地图片
	


});