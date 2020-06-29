App.controller("productSetCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.info("路由参数：",params);
	
	$scope.productForm = {};	//产品设置表单
	
	var activityInfo = null;	//活动信息
	var timeRangeCount = 0;		//时间段数量
	var showTimeId = 0;			//显示的时间段Id
	var productId = 0;			//设置的产品Id
	var productStock = 0;		//产品库存
	var productMinPrice = 0;	//产品最小价
	
	
	
	//选择跳转
	$scope.chooseJump = function(type){
		for(var i=1;i<=timeRangeCount;i++){
			if(i==type){
				angular.element("#jump_"+i).addClass("timeChoose");
				
				showTimeId = $("#jump_"+i).attr("timeId");
				$scope.grid.dataSource.page(1);
			}else{
				angular.element("#jump_"+i).removeClass("timeChoose");
			}
		}
	}
	
	if(params.activityType==0){
		$("#operateContainer").hide();
		//产品设置
		$scope.mainGridOptions = {
				dataSource : {
					transport : {
						read : {
							url : path+"/server/getbrandpromotionproductListByPage",
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
											pid: params.activityId,	 	//活动Id
											time_id: showTimeId,	 	//时间段Id
											
											title: $scope.search.title,	 	//标题
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
							field : "product_id", 
							width : "50px",
							template:"<input type='checkbox' id='#: product_id #' value='#: product_id #' />"+"<span class='row-number subTitle '></span>"
						},
						{
							field : "image_url",         
							title : "缩略图",     
							width : "100px",
							template:'<img style="width: 100px;height:100px;margin"auto;" src="#: image_url #" />'
						},
						{
							field : "product_name",         
							title : "标题",     
							width : "100px"
						},
						{
							field : "service",         
							title : "服务评分",     
							width : "100px"
						},
						{
							field : "quality",         
							title : "描述评分",     
							width : "100px"
						},
						{
							field : "sell_num",         
							title : "已售数量",     
							width : "100px",
							template: function(e){
						    	if(e.sell_num){
						    		return e.sell_num;
						    	}else{
						    		return 0;
						    	}
						    }
						},
						{
							field : "COLLECTION_COUNT",         
							title : "收藏量",     
							width : "100px",
							template: function(e){
						    	if(e.COLLECTION_COUNT){
						    		return e.COLLECTION_COUNT;
						    	}else{
						    		return 0;
						    	}
						    }
						},
						{
							field : "min_price",         
							title : "单价",     
							width : "100px",
							template: function(e){
						    	var html = e.min_price+"-"+e.max_price;
						    	
						    	return html;
						    }
						},
						{
							field : "stock",         
							title : "库存",     
							width : "100px"
						},
						{
							field : "promotion_price",         
							title : "秒杀价",     
							width : "100px"
						},
						{
							field : "promotion_num",         
							title : "秒杀件数",     
							width : "100px"
						},
						{
							field : "product_id",         
							title : "操作",     
							width : "100px",
							template: function(e){
						    	var html = '<div class="operation k-state-default" ng-click="setProduct('+e.product_id+','+e.stock+','+e.min_price+','+e.promotion_price+','+e.promotion_num+','+e.person_product_num+',\''+e.title+'\')">设置</div>';
						    	
						    	return html;
						    }
						}
				]
			};
	}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){
		$("#operateContainer").show();
		//产品设置
		$scope.mainGridOptions = {
				dataSource : {
					transport : {
						read : {
							url : path+"/server/selectAllProductForBrandToAddByPage",
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
											id: params.activityId,	 	//活动Id
											product_name: $scope.search.title,	 	//标题
											classify_id: $("#classify").data("kendoComboBox").value(), //分类
											series_id: $("#series").data("kendoComboBox").value(), 		//系列
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
							template:"<input type='checkbox' value='#: id #' class='productSign'/>"+"<span class='row-number subTitle'></span>"
						},
						{
							field : "imgurl",         
							title : "缩略图",     
							width : "100px",
							template:'<img style="width: 100px;height:100px;margin"auto;" src="#: imgurl #" />'
						},
						{
							field : "product_name",         
							title : "标题",     
							width : "100px"
						},
						{
							field : "classify",         
							title : "分类",     
							width : "100px"
						},
						{
							field : "series",         
							title : "系列",     
							width : "100px"
						},
						{
							field : "min_price",         
							title : "单价",     
							width : "100px",
							template: function(e){
						    	var html = e.min_price+"-"+e.max_price;
						    	
						    	return html;
						    }
						},
						{
							field : "stock",         
							title : "库存",     
							width : "100px"
						},
						
				]
			};
	}
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
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
	
	//全选
	$scope.chooseAll = function(className,type){
		publicService.chooseAllByClassName(className,type);
	}

	//设置产品
	$scope.setProduct = function(id,stock,min_price,price,number,person_product_num,title){
		$scope.productForm = {};
		if(price){
			$scope.productForm.price = price;
		}
		if(number){
			$scope.productForm.limit_num = number;
		}
		if(person_product_num){
			$scope.productForm.person_product_num = person_product_num;
		}
		if(title && title!='null'){
			$scope.productForm.title = title;
		}

		productId = id;
		productStock = stock;
		productMinPrice = min_price;
		$scope.productSetWindow.center().open();
	}
	
	//产品设置提交
	$scope.productSubmit = function(){
		$scope.productForm.promotion_id = params.activityId;	//活动Id
		$scope.productForm.time_id = showTimeId;	//显示的时间段Id
		$scope.productForm.product_id = productId;	//设置的产品Id

		if($scope.productForm.price>productMinPrice){	//秒杀单价
			alert("【秒杀单价】设置要<=【产品最小价】！");
			return;
		}
		
		if(productStock>=$scope.productForm.limit_num && $scope.productForm.limit_num>=$scope.productForm.person_product_num){
			if($scope.productForm.title.length<=15){
				$("#productSubmitButton").attr("disabled","true");
				
				$http({
					   url: path+'/server/addmodifybrandpromotionproductList',
					   method: 'POST',
					   data: angular.toJson($scope.productForm)
				}).success(function(data){
					alert(data.message);
					if(data.code==0){  //成功
						$scope.productSetWindow.close();
						$scope.grid.dataSource.page(1);
					}else if(data.code==1){  //失败
						
					}
					
					$("#productSubmitButton").removeAttr("disabled");
				}).error(function(data){
					alert(data);   
				})
			}else{
				alert("附加说明最多15字！");
				return;
			}

			
		}else{
			alert("【限购数量】要<=【秒杀件数】，【秒杀件数】要<=【库存】！");
			return;
		}
	}
	
	//产品参加 
	$scope.productJoin = function(){
		var productIdArr = publicService.getChooseValueByClassName("productSign");
		
		if(productIdArr.length>0){
			var postData = {
					id: params.activityId,
					product_id_list: productIdArr
			};
			
			$http({
				   url: path+'/server/addStepPromotionProducts',
				   method: 'POST',
				   data: angular.toJson(postData), 
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert(data);   
			})
		}else{
			alert("请选择要参加的产品！");
		}
	}
	
	//获取活动信息
	$scope.getActivityInfo = function(id,type){
		var url = '';
		if(type==0){
			url = path+'/server/getplatformpromotionbyid/'+id;
		}else if(type==1 || type==2 || type==3){
			url = path+'/server/getPlatformStepPromotionByid/'+id;
		}
		
		$http({
			   url: url,
			   method: 'GET'
		}).success(function(data){
			if(type==0){
				activityInfo = data;
				
				$scope.showTimeRange(type,activityInfo.timeEntity);	//显示时间段
				$scope.chooseJump(1);		//选择跳转
			}else if(type==1 || type==2 || type==3){
				activityInfo = data.result;
				
				$scope.showTimeRange(type);	//显示时间段
			}
		}).error(function(data){
			alert("获取活动信息失败！");   
		})
	}
	
	//显示时间段
	$scope.showTimeRange = function(type,timeData){
		var html = '<ul>';
		
		if(type==0){
			$.each(timeData, function(index, OneObj){
				if(OneObj.has_registered_flag==1){
					timeRangeCount ++;
					var time_index = timeRangeCount;
					var time = OneObj.pstart_time_str+'-'+OneObj.pend_time_str;
					
					html += ('<li id="jump_'+time_index+'" timeId='+OneObj.id+' ng-click="chooseJump('+time_index+')">'+time+'</li>');
				}
			});
		}else if(type==1 || type==2 || type==3){
			timeRangeCount ++;
			var time_index = timeRangeCount;
			var time = activityInfo.start_time+'~'+activityInfo.end_time;
			
			html += ('<li id="jump_'+time_index+'" timeId='+activityInfo.id+' ng-click="chooseJump('+time_index+')">'+time+'</li>');
		}
		
		html += '</ul>';
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#timeRange").html(html);
	}
	
	
	
	$scope.initClassify();		//初始化分类
	$scope.initSeries();		//初始化系列
	
	$scope.getActivityInfo(params.activityId,params.activityType);		//获取活动信息
	

});