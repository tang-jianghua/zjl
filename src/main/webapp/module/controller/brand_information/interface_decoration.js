App.controller("brandInterfaceDecorationCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	$scope.banner = {};    			//Banner
	var backInfo = null;	//后台信息
	var brandId = null;     //品牌Id
	$scope.backgroundImg = "http://www.kaolaj.com/file/upload/image/brand_bg_image_";    	//背景图
	$scope.productAllForm = {};
	
	var limitParam1 = {imageSize:150, format:"jpg"};		//Banner
	var limitParam2 = {imageSize:150, format:"png"};		//背景图
	
	//获取当前用户信息
	$scope.getCurrentUserInfo = function(){
		$http({
			   url: path+'/server/getCurrentUserInfo',
			   method: 'GET'
		}).success(function(data){  
			backInfo = data;
			
			brandId = backInfo.id;
			//背景图设置
			$scope.UploadAdvertisementImg("background","background_img","background_url",limitParam2);     //实例化文件上传控件
			$scope.showUploadImg("background_img","background_url",$scope.backgroundImg+brandId+".png?aa="+Math.random());    //显示默认图片
		}).error(function(data){
			alert(data);   
		})
	}
	
	//获取Banner图片信息  
	$scope.getBannerImgInfo = function(){
		$http({
			   url: path+'/server/querybrandbanner',
			   method: 'GET' 
		}).success(function(data){
			$scope.banner = data.result;
			$scope.showBannerInfo($scope.banner,"bannerContainer");
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
							   //+'<input type="text" class="col-sm-12" ng-model="banner.'+linkName+'" placeholder="请输入连接地址"/>'
						   		//+'<button class="widths btn k-button" style="margin-left: 25%;" ng-click="deletePicture(1,\''+OneObj.id+'\')">删除</button>'

							   +'<button class="widths btn k-button" ng-click="contentJump(\''+OneObj.id+'\',\''+OneObj.link_type+'\',\''+OneObj.data_param+'\')">内容跳转</button>'
							   +'<button class="widths btn k-button" ng-click="deletePicture(1,\''+OneObj.id+'\')">删除</button>'
						   +'</span>'
					   +'</div>'
				   +'</div>');
			
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
			
			$scope.banner[linkName] = OneObj.link;

			$scope.Upload(bannerIndex,bannerIndex_img,bannerIndex_url,limitParam1);            //实例化文件上传控件
			$scope.showUploadImg(bannerIndex_img,bannerIndex_url,OneObj.imgurl);   //显示默认图片
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
		var imageurl = $('#'+imgUrlId).val();
		if(id){  //编辑
			url = path+'/server/modfiybrandbanner';
			imgData.id = id;
			imgData.imgurl = imageurl;
		}else{	 //添加
			url = path+'/server/addbrandbanner';
			imgData.imgurl = imageurl;
			imgData.sort = ($scope.banner.length+1);
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
	
	//上传---背景图
	$scope.UploadAdvertisementImg = function(containerId,imgId,imgUrlId,limitParam){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/server/updloadbrandbg/"+brandId
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
	        	if(url.indexOf("png")>-1){
	        		$scope.showUploadImg(imgId,imgUrlId,$scope.backgroundImg+brandId+".png?aa="+Math.random());
	        	}else{
	        		alert("背景图片上传暂时只支持(*.PNG)格式！");
	        	}
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

	//反显图片并赋值
	$scope.showUploadImg = function(imgId,imgUrlId,url){
		$("#"+imgId).css({
    		"background": "url('"+url+"') no-repeat",
    	    "background-size":"100% 100%",
    	    "background-color":"white"
    	});
		$('#'+imgUrlId).val(url);
	}
	
	//移除图片
	$scope.removeUploadImg = function(imgId,imgUrlId){
		$("#"+imgId).removeAttr("style");
		$('#'+imgUrlId).val("");
	}
	
	//删除图片
	$scope.deletePicture = function(type,id){
		if(confirm("确认删除？")){
			$http({
				   url: path+'/server/removebrandbanner/'+id,
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


	$scope.getBannerImgInfo();  	//Banner
	$scope.getCurrentUserInfo();	//获取当前用户信息

	
	//首页单品设定
	$scope.optionsForHomeProduct = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/brandmainproductpage",
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
			/*editable: {   //编辑
			    mode: "inline"   //整行编辑
			  },*/
			editable: false,  //true：单个编辑；popup：弹出新页面编辑
			dataBound: function () {   //序号
		        var rows = this.items();
		        var page = this.pager.page() - 1;
		        var pagesize = this.pager.pageSize();
		        $(rows).each(function () {
		            var index = $(this).index() + 1 + page * pagesize;
		            var rowLabel = $(this).find(".row-number");
		            $(rowLabel).html(index);
		        });
		    },
			columns : [
					{    
						title : "序号",
						field : "id", 
						width : "80px",
						template:"<input type='checkbox' class='checkbox_main_product' value='#: id #'/>"+"<span class='row-number subTitle'></span>"
					},
					{
						field : "product_name",         
						title : "标题",     
						width : "100px",
					},
					{
						field : "service",         
						title : "服务评分",     
						width : "100px",
						template: function(e){
							if(e.service){
								return e.service;
							}else{
								return 5;
							}
						}
					},
					{
						field : "quality",         
						title : "描述评分",     
						width : "100px",
						template: function(e){
							if(e.quality){
								return e.quality;
							}else{
								return 5;
							}
						}
					},
					{
						field : "imgurl",         
						title : "缩略图",     
						width : "100px",
						template:'<img style="width: 100px;height:100px;margin"auto;" src="#: imgurl #" />'
					},
					{
						field : "product_num",         
						title : "已售数量",     
						width : "100px",
						template: function(e){
							if(e.product_num){
								return e.product_num;
							}else{
								return 0;
							}
						}
					},
					{
						field : "classify",         
						title : "所属分类",     
						width : "100px"    
					},
					{
						field : "series",         
						title : "所属系列",     
						width : "100px"    
					},
					{
						field : "state",         
						title : "状态",     
						width : "100px"    
					},
					{
						field : "min_price",         
						title : "单价",     
						width : "100px",
						template: function(e){
							return e.min_price+"-"+e.max_price;
						}
					}
					
			]
		};
	
	//全选
	$scope.chooseAll = function(className,type){
		publicService.chooseAllByClassName(className,type);
	}
	
	//删除选择的产品 
	$scope.deleteData = function(){
		var productIdArr = publicService.getChooseValueByClassName("checkbox_main_product");
		
		if(productIdArr.length>0){
			if(confirm("您确定删除这些单品吗？")){
				var postData = {brandMainProductIDList: productIdArr};
				
				$http({
					   url: path+'/server/batchdeletebrandmainproduct',
					   method: 'POST',
					   data: angular.toJson(productIdArr), 
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
		}else{
			alert("请选择要删除的产品！");
		}
	}
	
	//选择产品 
	$scope.setProduct = function(){
		$scope.setProductWindow.center().open();
	}
	
	//初始化分类
	$scope.initClassify = function(){
		$("#classify").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    placeholder: "分类",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/brand/queryclassifies",
	                  dataType : "json"
	              }
	          },
	          schema : {
					data : function(d) {
						return d.result;
					}
				},
	      },
	      change: function(e) {
	    	    $scope.getSeries(this.value());
	      }
		});
	}
	
	//获取系列数据
	$scope.getSeries=function(classifyId,defaultVal){
		$http({
			   url: path+"/server/brand/brandserieslistbyclassify/"+classifyId,
			   method: 'GET'
		}).success(function(data){
			$scope.initSeries(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化系列
	$scope.initSeries = function(seriesData,defaultVal){
		if(seriesData){
			var plugObj = $("#series").data("kendoComboBox");
			plugObj.value("");
			plugObj.setDataSource(seriesData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#series").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "code",
			    placeholder: "系列",
			    filter: "contains",
			    dataSource: []
			});
		}
	}
	
	$scope.initClassify();		//初始化分类
	$scope.initSeries();		//初始化系列

	
	//产品参与
	$scope.optionsForAllProduct = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/bannerproductlist",
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
									product_name: $scope.productAllForm.product_name,	//标题
									classify_id : $("#classify").data("kendoComboBox").value(),	 	//分类
									series_id: $("#series").data("kendoComboBox").value(),	 		//系列
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
			dataBound: function () {   //序号
		        var rows = this.items();
		        var page = this.pager.page() - 1;
		        var pagesize = this.pager.pageSize();
		        $(rows).each(function () {
		            var index = $(this).index() + 1 + page * pagesize;
		            var rowLabel = $(this).find(".row-number");
		            $(rowLabel).html(index);
		        });
		    },
			columns : [
					{    
						title : "序号",
						field : "id", 
						width : "50px",
						template:"<input type='checkbox' class='checkbox_product' value='#: id #'/>"+"<span class='row-number subTitle'></span>"
					},
					{
						field : "imgurl",         
						title : "缩略图",     
						width : "80px",
						template:'<img style="width: 100px;height:100px;margin"auto;" src="#: imgurl #" />'
					},
					{
						field : "product_name",         
						title : "标题",     
						width : "100px",
					},
					{
						field : "classify",         
						title : "所属分类",     
						width : "100px",
					},
					{
						field : "series",         
						title : "所属系列",     
						width : "100px",
					}
					
					
			]
		};
	
	//产品搜索
	$scope.productSearch = function(){
		$scope.gridForAllProduct.dataSource.page(1);
	}
	
	//产品设定提交
	$scope.productSubmit = function(type){
		var postData = [];
		var productIdArr = publicService.getChooseValueByClassName("checkbox_product");

		$.each(productIdArr, function(index, OneObj){
			var oneProduct = {
					product_id: OneObj
					};
			postData.push(oneProduct); 
		});

		if(postData.length>0){
			$http({
				   url: path+'/server/addbrandmainproduct',
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.setProductWindow.close();
					window.location.reload();   //页面刷新
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}else{
			alert("请选择要设定的产品！");
		}
	}
	
	/*****************************内容跳转设定（begin）**********************************/
	$scope.activityForm = {};        //活动跳转表单
	$scope.productForm = {};         //产品跳转表单
	
	var fromId = null;     //源内容Id
	var linkType = null;   //链接类型
	var toId = null;       //目标内容Id

	
	//内容跳转（1：Banner,4：广告位）
	$scope.contentJump = function(from_id,link_type,to_id){
		var params = {method:"bannerSkip4", fromId:from_id, linkType:link_type, toId:to_id};
		$location.path("/linkSkipManagement/"+angular.toJson(params));
		
		/*fromId = from_id;
		linkType = link_type;
		toId = to_id;
		console.log("源内容Id:"+fromId,"---链接类型:"+linkType,"---目标内容Id:"+toId);

		$scope.chooseJump(2);
		$scope.contentJumpWindow.center().open();*/	
	}
	
	//选择跳转
	$scope.chooseJump = function(type){
		for(var i=1;i<=5;i++){
			if(i==type){
				angular.element("#jump_"+i).addClass("choose");
				angular.element("#jump_"+i+"_container").show();
			}else{
				angular.element("#jump_"+i).removeClass("choose");
				angular.element("#jump_"+i+"_container").hide();
			}
		}
		
		if(type==1){
			$scope.gridForActivity.dataSource.page(1);
		}else if(type==2){
			$scope.gridForProduct.dataSource.page(1);
		}else if(type==3){
			$scope.gridForImageAndText.dataSource.page(1);
		}else if(type==4){
			$scope.gridForAlbum.dataSource.page(1);
		}else if(type==5){
			
		}
	}
	
	//活动跳转
	$scope.optionsForActivity = {
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
								/*param:{
									content: $scope.search.content,	 	//姓名
									start_time: $("#start_time").data("kendoDateTimePicker").value(),	//预约时间（始）
									end_time: $("#end_time").data("kendoDateTimePicker").value(),		//预约时间（止）
								}*/
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
						field : "id",         
						title : "选择",     
						width : "100px",
						template:"<input type='radio' name='radio_activity' value='#: id #' />"
						
					},
					{
						field : "title",         
						title : "活动名称",     
						width : "100px",
					},
					{
						field : "",         
						title : "属性",     
						width : "100px"    
					},
					{
						field : "",         
						title : "类型",     
						width : "100px"    
					},
					{
						field : "",         
						title : "活动日期",     
						width : "100px"    
					},
					{
						field : "",         
						title : "所属合伙人",     
						width : "100px"    
					},
					{
						field : "",         
						title : "活动地区",     
						width : "100px"    
					},
					
			]
		};
	
	
	//初始化行业
	$scope.initHangYe = function(){
		$("#hangYe").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    value:"",
		    dataSource: {
		      serverFiltering: true,
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/buildmaterialslist",
	                  dataType : "json"
	              }
	          },
	          schema : {
					data : function(d) {
						return d;
					}
				},
	      },
	      change: function(e) {
	    	    var value = this.value();
	    	    $scope.getPinLeiData(value);
	      }
		});
	}
	
	//获取品类数据
	$scope.getPinLeiData = function(parentId){
		$http({
			   url: path+"/server/buildclassentities",
			   method: 'GET'
		}).success(function(data){
			var showDataArr = new Array();   //显示的数据集
			$.each(data, function(index, OneObj){
				if(OneObj.build_Id==parentId){
					showDataArr.push(OneObj);
				}
			});

			$scope.initPinLei(showDataArr);		//初始化品类
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化品类
	$scope.initPinLei = function(cityData){
		if(cityData){
			var plugObj = $("#pinLei").data("kendoComboBox");
			plugObj.value("");
			plugObj.setDataSource(cityData);
		}else{
			$("#pinLei").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "id",
			    dataSource: []
			});
		}
	}

	$scope.initHangYe();		//初始化行业
	$scope.initPinLei();		//初始化品类

	//产品跳转
	$scope.optionsForProduct = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/brand/productcolumn",
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
									materials_id: $("#hangYe").data("kendoComboBox").value(),	//行业
									class_id: $("#pinLei").data("kendoComboBox").value(),	 	//品类
									product_name: $scope.productForm.product_name,	 	//标题
									partner: $scope.productForm.partner,	 			//合伙人
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
						field : "id",         
						title : "选择",     
						width : "100px",
						template:"<input type='radio' name='radio_product' value='#: id #' />"
						
					},
					{
						field : "product_name",         
						title : "标题",     
						width : "100px",
					},
					{
						field : "",         
						title : "地区",     
						width : "100px"    
					},
					{
						field : "partner_principal",         
						title : "城市合伙人",     
						width : "100px"    
					},
					{
						field : "sel_count",         
						title : "销量",     
						width : "100px"    
					}
					
			]
		};
	
	
	
	//内容跳转设定（查询）
	$scope.jumpSearch = function(type){
		if(type==1){
			$scope.gridForActivity.dataSource.page(1);
		}else if(type==2){
			$scope.gridForProduct.dataSource.page(1);
		}else if(type==3){
			$scope.gridForImageAndText.dataSource.page(1);
		}else if(type==4){
			$scope.gridForAlbum.dataSource.page(1);
		}else if(type==5){
			
		}
	}
	
	//内容跳转设定（确定）
	$scope.jumpSubmit = function(type){
		var id = null;			//记录Id
		var jumpType = null;    //跳转类型
		
		if(type==1){		//活动
			id = $('input[name="radio_activity"]:checked').val();
			jumpType = 6;	
		}else if(type==2){	//产品		
			id = $('input[name="radio_product"]:checked').val();
			jumpType = 8;	
		}

		var postData = {
				id: fromId,
				link_type:jumpType,
				data_param: id
		};
		

		$http({
			   url: path+'/server/modfiybrandbanner',
			   method: 'POST',
			   data: angular.toJson(postData)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.contentJumpWindow.close();
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
		
	}
	
	
	/*****************************内容跳转设定（end）**********************************/
	
	



});
