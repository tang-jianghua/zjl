App.controller("addBrandActivityCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);

	$scope.form = {};     //表单
	$scope.productForm = {};
	var activityInfo = null;	//活动信息
	var productArr = [];		//产品列表信息
	var maxCount = 20000;	//订金券发布数量
	var productIdArr = new Array();	//产品数组
	
	var limitParam1 = {imageSize:100, format:"jpg"};		//活动宣传图
	

	
	//初始化活动类型
	$scope.initActivityType = function(){
		$("#activity_type").kendoDropDownList({
			value: "现金劵",
			dataSource: ["满减","秒杀","折扣","一口价","现金劵"],
			change: function(e) {
			    var value = this.value();
			   
			}
		});
	}
	
	//限时优惠
	$scope.initLimitDiscount = function(){
		$("#limitDiscount").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "value",
			dataSource: [
			             {
			            	value: 0,
			 				text: "无"
			             },
			             {
			            	value: 1,
			 				text: "有"
			             }
            ],
            change: function(e) {
	    	    var value = this.value();
	    	    if(value==0){	//不限购
	    	    	$("#discountParam").hide();
	    	    }else if(value==1){	//限购
	    	    	$("#discountParam").show();
	    	    }
            }
		});
	}
	
	//初始化参与品牌  
	$scope.initJoinBrand = function(dataArr,defaultVal){
		if(dataArr){
			var plugObj = $("#joinBrand").data("kendoMultiSelect");
			plugObj.value("");
			plugObj.setDataSource(dataArr);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			if(params.method=="add" || params.method=="edit"){
				$("#joinBrand").kendoMultiSelect({
				    dataTextField: "brand_name",
				    dataValueField: "id",
				    placeholder: "请选择品牌名称",
				    filter: "contains",
				    dataSource: {
			          transport: {
			              read: {
			            	  type: 'GET',
			                  url: path+"/server/querybrandname",
			                  dataType: "json"
			              }
			          }
				    }
				});
			}else{
				$("#joinBrand").kendoMultiSelect({
				    dataTextField: "brand_name",
				    dataValueField: "id",
				    placeholder: "请选择品牌名称",
				    filter: "contains",
				    dataSource: []
				});
			}
			
		}
	}
	
	//初始化商品范围
	$scope.initProductRange = function(){
		$("#productRange").kendoDropDownList({
		    dataTextField: "value",
		    dataValueField: "key",
		    dataSource: [
		                 {key:"0",value:"部分商品"},
		                 {key:"1",value:"全部商品"}
             ]
		   
		});
	}
	
	//检验发布数量
	$scope.checkPublishCount = function(){
		if($scope.form.count){  //有值
			if($.isNumeric($scope.form.count)){
				if($scope.form.count<=0){
					alert("请输入正整数！");
				}else if($scope.form.count>0 && $scope.form.count<=maxCount){
					return;
				}else if($scope.form.count>maxCount){
					alert("发布数量最大值为"+maxCount+"！");
				}
			}else{
				alert("请输入正确的数字！");
			}
			$scope.form.count = "";
		}else{  //无值
			alert("请输入发布数量！");
		}
	}
	
	//删除图片
	$scope.deletePicture = function(type,which){
		if(confirm("确认删除？")){
			if(params.method=="add"){
				if(type==1){
					publicService.removeUploadImage("cover_img","cover_url");		//封面图
				}else if(type==2){
					if(which==1){
						publicService.removeUploadImage("activity1_img","activity1_url");		//活动图
					}else if(which==2){
						publicService.removeUploadImage("activity2_img","activity2_url");		//活动图
					}else if(which==3){
						publicService.removeUploadImage("activity3_img","activity3_url");		//活动图
					}
				}
			}else if(params.method=="edit"){
				var postData = {};
				postData.id = activityInfo.id;
					
				if(type==1){
					postData.image = "";   //封面图
				}else if(type==2){
					if(which==1){
						postData.images = $("#activity2_url").val()+","+$("#activity3_url").val();   //活动图
					}else if(which==2){
						postData.images = $("#activity1_url").val()+","+$("#activity3_url").val();   //活动图
					}else if(which==3){
						postData.images = $("#activity1_url").val()+","+$("#activity2_url").val();   //活动图
					}
				}
				
				$http({
					   url: path+'/server/modifyunionpromotionimage',
					   method: 'POST',   
					   data: angular.toJson(postData)
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
	
	
	//表单提交
	$scope.formSubmit = function(){
		$scope.form.image = $("#cover_url").val();   //封面图
		$scope.form.images = $("#activity1_url").val()+","+$("#activity2_url").val()+","+$("#activity3_url").val();   //活动图
		//活动类型（默认现金券）
		
		$scope.form.cash_promotion_flag = $("#limitDiscount").data("kendoDropDownList").value(); //限时优惠
		if($scope.form.cash_promotion_flag==1){
			$scope.form.cash_start_time = $("#discount_start_time").val();   //开始时间
			$scope.form.cash_end_time = $("#discount_end_time").val();       //结束时间
			
			if($scope.form.cash_start_time){
				$scope.form.cash_start_time += " 00:00:00";
			}else{
				alert("请填写【限时优惠】的开始时间！");
				return;
			}
			if($scope.form.cash_end_time){
				$scope.form.cash_end_time += " 23:59:59";
			}else{
				alert("请填写【限时优惠】的结束时间！");
				return;
			}
			if(!$scope.form.cash_prom_price){
				alert("请填写【限时优惠】的价格！");
				return;
			}
			if(!$scope.form.cash_prom_num){
				alert("请填写【限时优惠】的发布数量！");
				return;
			}
		}

		$scope.form.apply_end_time = $("#limit_time").val();   //报名截止时间
		$scope.form.start_time = $("#start_time").val();   //活动日期（始）
		$scope.form.end_time = $("#end_time").val();       //活动日期（止）
		if($scope.form.apply_end_time){
			$scope.form.apply_end_time += " 23:59:59";
		}
		if($scope.form.start_time){
			$scope.form.start_time += " 00:00:00";
		}
		if($scope.form.end_time){
			$scope.form.end_time += " 23:59:59";
		}
		
		$scope.form.brand_ids = $("#joinBrand").data("kendoMultiSelect").value().toString();   //参与品牌
		$scope.form.total_flag = $("#productRange").data("kendoDropDownList").value();  //商品范围
		
		//参数验证
		if(!$scope.form.name){
			alert("请填写【活动名称】！");
			return;
		}
		if(!$scope.form.image){
			alert("请上传【封面图】！");
			return;
		}
		if($scope.form.images==",,"){
			alert("请上传【活动图】！");
			return;
		}
		if(!$scope.form.rule){
			alert("请填写【活动规则】！");
			return;
		}
		
		if(!$scope.form.title){
			alert("请填写【现金券名称】！");
			return;
		}
		if(!$scope.form.cash_value){
			alert("请填写【额度】！");
			return;
		}
		if(!$scope.form.price){
			alert("请填写【价格】！");
			return;
		}
		if(!$scope.form.count){
			alert("请填写【发布数量】！");
			return;
		}
		
		if(!$scope.form.discount){
			alert("请填写【优惠折扣】！");
			return;
		}
		
		if(!$scope.form.details){
			alert("请填写【特权现金券使用说明】！");
			return;
		}
		if(!$scope.form.apply_end_time){
			alert("请填写【报名截止时间】！");
			return;
		}
		if(!$scope.form.start_time){
			alert("请填写【活动开始日期】！");
			return;
		}
		if(!$scope.form.end_time){
			alert("请填写【活动结束日期】！");
			return;
		}
		if(!$scope.form.brand_ids){
			alert("请选择【参与品牌】！");
			return;
		}
		if(!$scope.form.total_flag){
			alert("请选择【商品范围】！");
			return;
		}
		
		//整体验证
		if($scope.form.cash_promotion_flag==1){	//限时限购
			if($scope.form.start_time<=$scope.form.cash_start_time 
				&& $scope.form.cash_start_time<=$scope.form.cash_end_time
				&& $scope.form.cash_end_time<=$scope.form.end_time){	
			
			}else{
				alert("【限时限购】的时间范围应该在活动时间范围内！");
				return;
			}
		}else{
			if($scope.form.start_time>$scope.form.end_time){	
				alert("活动的开始时间应该小于结束时间！");
				return;
			}
		}
		
		if($scope.form.discount){
			if($scope.form.discount<0 || $scope.form.discount>10){
				alert("优惠折扣范围应该在[0~10]间！");
				return;
			}
		}else{
			$scope.form.discount = 10;
		}

		var url = '';
		if(params.method=="add"){
			url = path+'/server/addunionpromotion';
		}else if(params.method=="edit"){
			url = path+'/server/modifyunionpromotion';
			$scope.form.id = activityInfo.id;
		}
		
		$("#formSubmitButton").attr("disabled","true");

		$http({
			   url: url,
			   method: 'POST',   
			   data: angular.toJson($scope.form),  
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				$location.path("brandActivity");
			}else if(data.code==1){  //失败
				
			}
			$("#formSubmitButton").removeAttr("disabled");
		}).error(function(data){
			alert("保存失败！");   
		})
	}
	
	//获取活动信息
	$scope.getActivityInfo = function(id){
		$http({
			   url: path+'/server/queryunionpromotiondetail/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){
				activityInfo = data.result;
				
				$scope.form.name = activityInfo.name;	//活动名称
				publicService.showUploadImage("cover_img","cover_url",activityInfo.image);	//封面图
				
				var imageArr = activityInfo.images.split(",");	//活动图
				$.each(imageArr, function(index, OneObj){
					publicService.showUploadImage("activity"+(index+1)+"_img","activity"+(index+1)+"_url",OneObj);
				});
				
				$scope.form.rule = activityInfo.rule;	//活动规则
				
				$scope.form.title = activityInfo.title;				//订金劵名称
				$scope.form.cash_value = activityInfo.cash_value;	//额度
				$scope.form.price = activityInfo.price;				//价格
				$scope.form.count = activityInfo.count;				//发布数量
				
				if(activityInfo.cash_promotion_flag){
					$("#limitDiscount").data("kendoDropDownList").value(activityInfo.cash_promotion_flag);
				}
				if(activityInfo.cash_promotion_flag==1){	//限时优惠
					$("#discountParam").show();
					
					$("#discount_start_time").val(activityInfo.cash_start_time);   //开始时间
					$("#discount_end_time").val(activityInfo.cash_end_time);       //结束时间
					$scope.form.cash_prom_price = activityInfo.cash_prom_price;	   //价格	
					$scope.form.cash_prom_num = activityInfo.cash_prom_num;	   	   //发布数量	
				}
				
				$scope.form.discount = activityInfo.discount;	//优惠折扣
				$scope.form.details = activityInfo.details;		//特权现金券使用说明
				
				$("#limit_time").val(activityInfo.apply_end_time);   //报名截止时间
				$("#start_time").val(activityInfo.start_time);	//活动日期（始）
				$("#end_time").val(activityInfo.end_time);		//活动日期（止）

				if(userInfo.user_type==3 && (params.method=="add" || params.method=="edit")){	//合伙人
					$("#joinBrand").data("kendoMultiSelect").value(activityInfo.brand_ids.split(","));
				}else{
					$scope.initJoinBrand(activityInfo.brandArray,activityInfo.brand_ids.split(","));	//参与品牌
				}
				
				$("#productRange").data("kendoDropDownList").value(activityInfo.total_flag);  //商品范围
				
				if(params.method=="look"){
					$scope.initSignBrand(activityInfo.brandArray);		//初始化【报名品牌】
					$scope.getProductList();	//获取产品列表
				}

			}else{
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	

	//活动产品
	$scope.mainGridOptions1 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/unionproductpage",
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
									promotion_id: params.activityId
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
			editable: {   //编辑
			    mode: "inline"   //整行编辑
			  },
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
						width : "60px",
						template:"<input type='checkbox' class='selectProduct' value='#: id #'/>"+"<span class='row-number subTitle'></span>"
					},
					{
						field : "model",         
						title : "型号",     
						width : "100px"
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
						template:'<img style="width: 110px;margin"auto;" src="#: imgurl #" />'
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
						field : "city",         
						title : "所属城市",     
						width : "100px"    
					},
					{
						field : "brand_name",         
						title : "所属品牌",     
						width : "100px"    
					},
					{
						field : "principal",         
						title : "城市合伙人",     
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
						template:function(e){
							var html = e.min_price+'-'+e.min_price;
							
							return html;
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
		if(confirm("确认删除？")){
			var productIdArr = publicService.getChooseValueByClassName("selectProduct");
			console.info(productIdArr);
	
			if(productIdArr.length>0){
				$http({
					   url: path+'/server/deleteunionproduct',
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
			}else{
				alert("请选择要删除的产品！");
			}
			
		}
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
	
	//产品跳转
	$scope.optionsForProduct = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/selectunionproduct",
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
									product_name: $scope.productForm.product_name,	//标题
									classify_id : $("#classify").data("kendoComboBox").value(),	 	//分类
									series_id: $("#series").data("kendoComboBox").value(),	 		//系列
									id: params.activityId,	//活动Id
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
						template:"<input type='checkbox' class='unSelectProduct' value='#: id #' />"
						
					},
					{
						field : "product_name",         
						title : "标题",     
						width : "100px",
					},
					{
						field : "imgurl",         
						title : "缩略图",     
						width : "100px",
						template:function(e){
							var html = '<img style="width:100px;height:100px;" src="'+e.imgurl+'" />';
							
							return html;
						}
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
		$scope.gridForProduct.dataSource.page(1);
	}
	
	//产品设定提交
	$scope.productSubmit = function(type){
		var postData = [];
		var productIdArr = publicService.getChooseValueByClassName("unSelectProduct");
		$.each(productIdArr, function(index, OneObj){
			var oneProduct = {
					promotion_id: params.activityId,	//活动Id
					product_id: OneObj					//产品Id
					};
			postData.push(oneProduct);
		});
		
		$http({
			   url: path+'/server/addunionproduct',
			   method: 'POST',
			   data: angular.toJson(postData)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.contentJumpWindow.close();
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
		
	}

	$scope.setActivityProduct = function(){
		$scope.contentJumpWindow.center().open();
	}
	
	//初始化【报名品牌】
	$scope.initSignBrand = function(dataArr){
		if(dataArr){
			var plugObj = $("#sign_brand").data("kendoDropDownList");
			plugObj.setDataSource(dataArr);
			plugObj.value(dataArr[0].id);
			$scope.showBrandInfo(dataArr[0]);	//显示品牌信息
		}else{
			$("#sign_brand").kendoDropDownList({
				dataTextField: "brand_name",
			    dataValueField: "id",
			    dataSource: [],
			    change: function(e) {
		    	    var brandObj = this.dataItem();

		    		$scope.showBrandInfo(brandObj);	//显示品牌信息
		    		$scope.getProductList();	//获取产品列表
		        }
			});
		}
	}
	
	//显示品牌信息
	$scope.showBrandInfo = function(brandInfo){
		var html = '';
		
		html += ('<div class="col-sm-2 text-center">品牌信息</div>'
				+'<div class="col-sm-3 text-center brandInfo">联系人：'+brandInfo.principal+'</div>'
				+'<div class="col-sm-4 text-center brandInfo">联系电话：'+brandInfo.phone_num+'</div>');

		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#brandInfo").html(html);
	}
	
	//获取产品列表
	$scope.getProductList = function(){
		var postData = {
				page : 0, //当前页
				pageSize : 0,
				param:{
					promotion_id: params.activityId,
					brand_id: $("#sign_brand").data("kendoDropDownList").value()
				}
				
		};

		$http({
			   url: path+'/server/unionproductlist',
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){
			productArr = data;

			if(productArr.length>0){
				$scope.showBrandAllProduct(productArr);	//显示品牌下的所有产品
			}else{
				alert("当前品牌暂无产品参与！");
				$("#allProduct").html("");
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//显示品牌下的所有产品
	$scope.showBrandAllProduct = function(productArr){
		var html = '';
		
		$.each(productArr, function(index, OneObj){
			var stock = "0";
			if(OneObj.stock){
				stock = OneObj.stock;
			}

			html += ('<div class="col-sm-3 oneProduct push">'
						+'<div class="image" style="background:url(\''+OneObj.imgurl+'\');background-size:100% 100%;">'
						+'</div>'
						+'<div>'
							+'<div class="title" title="'+OneObj.product_name+'">'+OneObj.product_name+'</div>'
							//+'<div class="text">型号：FTPL09389</div>'
							+'<div class="text">库存：'+stock+'件  <span class="redText">'+OneObj.min_price+'</span>元/'+OneObj.max_price+'元</div>'
						+'</div>'
					+'</div>');
		});
		

		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#allProduct").html(html);
	}
	
	
	publicService.uploadImage("cover","cover_img","cover_url",limitParam1);				//封面图
	publicService.uploadImage("activity1","activity1_img","activity1_url",limitParam1);		//活动图
	publicService.uploadImage("activity2","activity2_img","activity2_url",limitParam1);		//活动图
	publicService.uploadImage("activity3","activity3_img","activity3_url",limitParam1);		//活动图
	
	$scope.initActivityType();		//初始化活动类型
	
	$scope.initLimitDiscount();		//限时优惠
	$("#discountParam").hide();
	publicService.initDate("discount_start_time");   //开始时间
	publicService.initDate("discount_end_time");   	 //结束时间
	
	publicService.initDate("limit_time");   //报名截止时间
	publicService.initDate("start_time");   //活动日期（始）
	publicService.initDate("end_time");   	 //活动日期（止）

	$scope.initJoinBrand();		    //初始化参与品牌  
	$scope.initProductRange();		//初始化商品范围
	
	
	
	if(params.method=="add"){
		$scope.form.details = $("#useDetails").text();	//特权现金券使用说明(默认值)

		$("#productSetContainer").hide();
		$("#brandProduct").hide();
	}else if(params.method=="edit"){
		$scope.getActivityInfo(params.activityId);	//获取活动信息
		
		$("#productSetContainer").hide();
		$("#brandProduct").hide();
		
		$("#activity_type").data("kendoDropDownList").readonly(true);
		$("#cashPannel").find("input").attr("readonly","readonly");
		$("#limitDiscount").data("kendoDropDownList").readonly(true);
		$("#discount_start_time").data("kendoDatePicker").readonly(true);
		$("#discount_end_time").data("kendoDatePicker").readonly(true);
		
		$("#limit_time").data("kendoDatePicker").readonly(true);
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		
		$("#productRange").data("kendoDropDownList").readonly(true);
	}else if(params.method=="look"){
		$scope.initSignBrand();		//初始化【报名品牌】
		$scope.getActivityInfo(params.activityId);	//获取活动信息
		
		$(".idMessage").find("input").attr("readonly","readonly");
		$("textarea").attr("readonly","readonly");
		
		$("#activity_type").data("kendoDropDownList").readonly(true);
		$("#limitDiscount").data("kendoDropDownList").readonly(true);
		$("#limit_time").data("kendoDatePicker").readonly(true);
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		$("#joinBrand").data("kendoMultiSelect").readonly(true);
		$("#productRange").data("kendoDropDownList").readonly(true);

		$("#formSubmitButton").hide();
		$("#productSetContainer").hide();
		$(".operateContainer").hide();
	}else if(params.method=="productSet"){
		$scope.getActivityInfo(params.activityId);	//获取活动信息
		
		$(".idMessage").find("input").attr("readonly","readonly");
		$("textarea").attr("readonly","readonly");
		
		$("#activity_type").data("kendoDropDownList").readonly(true);
		$("#limitDiscount").data("kendoDropDownList").readonly(true);
		$("#limit_time").data("kendoDatePicker").readonly(true);
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		$("#joinBrand").data("kendoMultiSelect").readonly(true);
		$("#productRange").data("kendoDropDownList").readonly(true);
		
		
		$("#formSubmitButton").hide();
		$("#brandProduct").hide();
		$(".operateContainer").hide();
	}


});