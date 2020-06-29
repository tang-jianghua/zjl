App.controller("guiderDataManagementCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, $sce, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.search1 = {};	//消费者数据表单
	$scope.search2 = {};	//订单收入表单
	$scope.search3 = {};	//预约订单表单
	$scope.productDetails = {};	//产品详情
	$scope.bookingDetails = {};	//预约详情
	var pay_type = {	//支付方式
			1: '全款',
			2: '定金'
	};

	
	//选择列表
	$scope.chooseList = function(type){
		for(var i=1;i<=3;i++){
			if(i==type){
				$("#list"+i).addClass("choose");
				$("#container"+i).show();
			}else{
				$("#list"+i).removeClass("choose");
				$("#container"+i).hide();
			}
		}
	}
	
	//消费者数据
	$scope.mainGridOptions1 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querycustomerback",  
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
									id: params.guiderId,
									customer_name:$scope.search1.customer_name,		//姓名
									customer_phone:$scope.search1.customer_phone,	//联系方式
									seller_name:$scope.search1.seller_name,			//导购员
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
						template:"<input type='checkbox' value='#: id #' />"+"<span class='row-number subTitle'></span>"
					},
					{
						field : "customer_name",         
						title : "消费者姓名",     
						width : "100px"
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "100px"
					},
					{
						field : "level",         
						title : "会员属性",     
						width : "100px",
						values: [
					         { text: "普通用户", value: 0 },
					         { text: "会员", value: 1 }
					    ],
					},
					{
						field : "create_time",         
						title : "注册时间",     
						width : "100px"
					},
					{
						field : "seller_name",         
						title : "导购姓名",     
						width : "100px"
					}
			]
		};
	
	//订单收入
	$scope.mainGridOptions2 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/selectOrderListBackForPage",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"desc",field:"o.create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								if(options.sort[0].field=="create_time"){
									options.sort[0].field="o.create_time"
								}else if(options.sort[0].field=="order_price"){
									options.sort[0].field="o.order_price"
								}
								
								sort=options.sort;
							}
							
							var parameter = {
									page : options.page, //当前页
									pageSize : options.pageSize,
									param:{
										id: params.guiderId,
										/*id: curUserId,
										order_code: $scope.search.order_code,		 	//订单编号
										seller_name: $scope.search.seller_name,		 	//订单编号
										order_state:$("#order_state").data("kendoDropDownList").value(),             //订单状态	
										sort:sort*/
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
			dataBound: function (e) {   //序号
		        var rows = this.items();
		        var page = this.pager.page() - 1;
		        var pagesize = this.pager.pageSize();
		        $(rows).each(function () {
		            var index = $(this).index() + 1 + page * pagesize;
		            var rowLabel = $(this).find(".row-number");
		            $(rowLabel).html(index);
		        });
		        
		        //显示产品信息
		        var dataList = e.sender._data;
		        $scope.showProductInfo("grid2",dataList);	//显示产品信息
		        
		        console.info("开始表格合并......");
				publicService.tableTdRowspan("grid2",[8,7,6,5,3,2]);	//表格合并
		    },
			columns : [
					{    
						title : "序号",
						field : "id", 
						width : "50px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{
						field : "",         
						title : "产品",     
						width : "250px",
						template :function(e){
							var html = '';
							
							if(e.code){	//订单号
								html += '订单号：'+e.code;
							}
							if(e.trade_data){	//流水号
								html += '&nbsp;&nbsp;&nbsp;&nbsp;流水号：'+e.trade_data;
							}
							if(e.create_time){	//日期
								html += '&nbsp;&nbsp;&nbsp;&nbsp;日期：'+e.create_time;
							}
							
							return html;
						}
					},
					{
						field : "",         
						title : "买家",     
						width : "80px",
						template :function(e){
							var html = '';
							
							if(e.cash_info){	//现金券
								html += '&nbsp;&nbsp;&nbsp;&nbsp;现金券：'+e.cash_info;
							}
							if(e.shifu_type==0){	//已付定金，剩余尾款
								html += '&nbsp;&nbsp;&nbsp;&nbsp;已付定金：'+e.order_price;
								html += '&nbsp;&nbsp;&nbsp;&nbsp;剩余尾款：'+e.deposit_price;
							}else if(e.shifu_type==1){	//实付
								html += '&nbsp;&nbsp;&nbsp;&nbsp;实付：'+(e.order_price+e.deposit_price);
							}
							
							return html;
						}
					},
					{
						field : "",         
						title : "交易状态",     
						width : "60px"    
					},
					{
						field : "",         
						title : "金额",     
						width : "80px"    
					},
					{
						field : "",         
						title : "支付方式",     
						width : "60px"    
					},
					{
						field : "",         
						title : "关联信息",     
						width : "160px"    
					},
					{
						field : "",         
						title : "推广导购",     
						width : "80px"    
					},
					{
						field : "order_price",         
						title : "实付",     
						width : "80px"    
					},
					{
						field : "",         
						title : "操作",     
						width : "100px"    
					}
			]
		};
	
	//显示产品信息
	$scope.showProductInfo = function(gridName,dataList){
		var tableTrObj = $("div[kendo-grid='"+gridName+"']").children("div:eq(1)").children("table").children("tbody").children("tr");
		
		$.each(tableTrObj, function(index, OneObj){
			//订单信息
			var orderIndex = $(OneObj).children('td:eq(0)').html();
			var orderText1 = $(OneObj).children('td:eq(1)').text();
			var orderText2 = $(OneObj).children('td:eq(2)').text();

			var order_html = '<td>'+orderIndex+'</td>'
							+'<td colspan="9">'
								+'<span style="float: left;">'+orderText1+'</span>'
								+'<span style="float: right;">'+orderText2+'</span>'
							+'</td>';
			order_html = $compile(order_html)($scope);   //angularJs代码需要动态编译
			$(OneObj).html(order_html);
			
			
			//产品信息
			var html = '';
			
			var orderInfo = dataList[index];
			var productList = dataList[index].order_products;
			$.each(productList, function(index1, OneObj1){
				var relationInfo_html = '';	//关联信息
				if(userInfo.user_type==1){
					relationInfo_html += ('<div>企业：'+orderInfo.enterprise_name+'</div>'
										 +'<div>合伙人：'+orderInfo.principal+'</div>'
										 +'<div>品牌：'+orderInfo.brand_name+'</div>'
										 +'<div>所属店铺：'+orderInfo.shop_name+'</div>');
				}else if(userInfo.user_type==2){
					relationInfo_html += ('<div>合伙人：'+orderInfo.principal+'</div>'
										 +'<div>品牌：'+orderInfo.brand_name+'</div>'
										 +'<div>所属店铺：'+orderInfo.shop_name+'</div>');
				}else if(userInfo.user_type==3){
					relationInfo_html += ('<div>品牌：'+orderInfo.brand_name+'</div>'
										 +'<div>所属店铺：'+orderInfo.shop_name+'</div>');
				}else if(userInfo.user_type==4){
					relationInfo_html += ('<div>所属店铺：'+orderInfo.shop_name+'</div>');
				}
				
				var cashInfo_html = '';	//金额（秒杀，联盟信息）
				if(OneObj1.info){
					cashInfo_html += ('<p style="color:#fff;background-color:#caa359;">'+OneObj1.info+'</p>');
				}
				
				html += ('<tr>'
							+'<td role="gridcell">'+(index1+1)+'</td>'
							+'<td role="gridcell">'
								+'<div style="padding: 1% 5%;">'
									+'<div class="leftImage">'
										+'<img class="productImage" src="'+OneObj1.imgurl+'"/>'
									+'</div>'
									+'<div class="rightInfo">'
										+'<div>'+OneObj1.product_title+'</div>'
										+'<div>型号：'+OneObj1.model+'</div>'
										+'<div>颜色：'+OneObj1.color+'</div>'
										+'<div>'
											+'<span>单价：'+OneObj1.price+'</span>'
											+'<span style="float: right;"><i class="fa fa-times" aria-hidden="true"></i>'+OneObj1.product_num+'</span>'
										+'</div>'
									+'</div>'
								+'</div>'
								
							+'</td>'
							+'<td role="gridcell">'+orderInfo.receiver_name+'</td>'
							+'<td role="gridcell">'+orderInfo.state+'</td>'
							+'<td role="gridcell"><p>'+(OneObj1.price*OneObj1.product_num)+'</p>'+cashInfo_html+'</td>'
							+'<td role="gridcell">'+pay_type[OneObj1.sales_model]+'</td>'
							+'<td role="gridcell">'
								+'<div style="text-align: left;">'
									+relationInfo_html
								+'</div>'
							+'</td>'
							+'<td role="gridcell">'+orderInfo.seller_name+'</td>'
							+'<td role="gridcell">'+orderInfo.order_price+'</td>'
							+'<td role="gridcell">'
								+'<div class="operation k-state-default" ng-click="showOrderDetails('+OneObj1.product_id+')">查看详情</div>'
							+'</td>'
						+'</tr>');
			});
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$(OneObj).after(html);
		});
	}
	
	//显示订单详情
	$scope.showOrderDetails = function(id){
		$http({
			   url: path+"/server/customerorderdetails/"+id,
			   method: 'GET'
		}).success(function(data){
			
			if(data.code==0){  //成功
				var details = data.result;
				console.log("显示详情页  ",details);
				if(details){   //有详情信息
					$scope.productDetails.imgurl = details.imgurl;//图片，
					$scope.productDetails.code = details.code;//订单号
					$scope.productDetails.create_time = details.create_time;//交易时间
					$scope.productDetails.address = details.address; //收货地址
					$scope.productDetails.product_name = details.product_name;//产品名称
					$scope.productDetails.sales_model = details.sales_model;//产品型号
					$scope.productDetails.color = details.color;//颜色分类
				
					$scope.productDetails.price = details.price;//单价
					$scope.productDetails.product_num = details.product_num;//购买数量
					$scope.productDetails.totalprice = details.totalprice;//总价
				
					$scope.productDetails.state = details.state;//收货状态
					$scope.productDetails.customerName = details.customer_name;//买家昵称
					$scope.productDetails.receiver_name = details.receiver_name;//收货人姓名
					$scope.productDetails.customer_phone = details.customer_phone;//联系方式
				
					$scope.productDetails.shop_name = details.shop_name;//店铺名称
					$scope.productDetails.principal = details.principal;//合伙人名称
					$scope.productDetails.brand_name = details.brand_name;//品牌名称
					$scope.productDetails.enterpise_name = details.enterpise_name;//企业名称
					$scope.productDetails.seller_name = details.seller_name;//推广导购
					//$scope.productDetails.state_info = details.state_info;
					$scope.productDetails.state_info = $sce.trustAsHtml(details.state_info);//订单状态列表
					$scope.product_detail.center().open();   //打开弹框
				}else{
					alert("暂无详情信息！");
				}
				
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//预约订单
	$scope.mainGridOptions3 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querybrandappointment",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"desc",field:"s.create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								if(options.sort[0].field=="seller_num"){
									options.sort[0].field="temp2.seller_num"
								}else if(options.sort[0].field=="create_time"){
									options.sort[0].field="s.create_time"
								}
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									server_id: params.guiderId,	//导购员Id
									customer_name: $scope.search3.customer_name,	 	//姓名
									shop_name: $("#shop_name").val(),		   	//预约店面
									brand_name: $("#brand_name").val(),		 	//预约品牌	
									province: $("#province").data("kendoComboBox").value(),		//省
									city: $("#city").data("kendoComboBox").value(),				//市
									county: $("#area").data("kendoComboBox").value(),			//区
									start_time: $("#start_time").val(),	//预约时间（始）
									end_time: $("#end_time").val(),		//预约时间（止）
									server_name: $scope.search3.server_name,	 					//服务人员
								    state: $("#bookingState").data("kendoDropDownList").value(),	//预约状态
									//sort:sort
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
	        dataBound: function(e) {	//数据绑定成功
	        	var rows = this.items();
		        var page = this.pager.page() - 1;
		        var pagesize = this.pager.pageSize();
		        $(rows).each(function () {
		            var index = $(this).index() + 1 + page * pagesize;
		            var rowLabel = $(this).find(".row-number");
		            $(rowLabel).html(index);
		        });
	        	
		    	console.log("【Grid 数据绑定成功】");
		    	//$('.fancybox').fancybox();
		    },	
			columns : [
					{    
						title : "序号",
						field : "brand_id", 
						width : "60px",
						template:"<input type='checkbox' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{
						field : "door_model_img",         
						title : "户型图",     
						width : "160px",
						template: function(e){ 
							var html = ('<a class="fancybox" href="'+e.door_model_img+'" data-fancybox-group="gallery_'+e.id+'" title="户型图">'
											+'<img src="'+e.door_model_img+'" class="bafont"/>'
										+'</a>');
	
							return html;
						}
					},
					{
						field : "name",         
						title : "姓名",     
						width : "70px"    
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "100px"    
					},
					{
						field : "brand_name",         
						title : "预约品牌",     
						width : "100px"    
					},
					{
						field : "shop_name",         
						title : "预约店面",     
						width : "100px"    
					},
					{
						field : "create_time",         
						title : "提交时间",     
						width : "100px"
					},
					{
						field : "start_time",         
						title : "预约时间",     
						width : "100px"
					},
					{
						field : "server_name",         
						title : "服务人员",     
						width : "80px"    
					},
					{
						field : "state",         
						title : "预约状态",     
						width : "80px", 
						values: [
						         { text: "已提交", value: 1 },
						         { text: "已确认", value: 2 },
						         { text: "受理中", value: 3 },
						         { text: "已完成", value: 4 },
						         { text: "已取消", value: 5 },
						         { text: "无法受理", value: 6 }
				        ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div value='查看详情' class='operation k-state-default' ng-click='showBookingDetails(\"#: id #\")' />查看详情</div>"
					}
			]
		};

	//查询
	$scope.search = function(type){
		if(type==1){
			$scope.grid1.dataSource.page(1);
		}else if(type==2){
			$scope.grid2.dataSource.page(1);
		}else if(type==3){
			$scope.grid3.dataSource.page(1);
		}
	}
	
	//显示预约详情
	$scope.showBookingDetails = function(id){
		$http({
			   url: path+"/server/querybrandappdetail/"+id,
			   method: 'GET'
		}).success(function(data){
			console.log(data);
			if(data.code==0){  //成功
				var details = data.result;
				
				if(details){   //有详情信息
					//图片，预约姓名，预约品牌，联系方式，预约店面，预约地址
					$scope.bookingDetails.door_model_img = details.door_model_img;
					$scope.bookingDetails.name = details.name;
					$scope.bookingDetails.brand_name = details.brand_name;
					$scope.bookingDetails.phone = details.phone;
					$scope.bookingDetails.shop_name = details.shop_name;
					$scope.bookingDetails.address_details = details.address_details;
					//预约品类，预约时间，预约风格
					$scope.bookingDetails.class_name = details.class_name;
					$scope.bookingDetails.start_time = details.start_time+"~"+details.end_time;
					$scope.bookingDetails.app_style = details.app_style;
					//预约空间，装修预算
					$scope.bookingDetails.app_space = details.app_space;
					$scope.bookingDetails.min_price = details.min_price;
					$scope.bookingDetails.max_price = details.max_price;
					//设计需求，预约进度
					$scope.bookingDetails.needs = details.needs;
					if(details.ap_info){
						$scope.bookingDetails.deal_info = $sce.trustAsHtml(details.ap_info.replace(/;/g,"<br>").replace(/T/g,""));
					}else{
						$scope.bookingDetails.deal_info = "";
					}

					$scope.booking_detail.center().open();   //打开弹框
				}else{
					alert("暂无详情信息！");
				}
				
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	
	
	/*订单收入*/
	publicService.initOrderState("orderState");
	
	/*预约订单*/
	publicService.initBrandName("brand_name");	//初始化【品牌名称】
	publicService.initShopName("shop_name");	//初始化【店铺名称】
	
	publicService.initProvince(1);			//初始化省
	publicService.initCity(1);				//初始化市
	publicService.initArea(1);				//初始化区
	publicService.initDateTime("start_time");   //预约时间（始）
	publicService.initDateTime("end_time");   	 //预约时间（止）
	
	publicService.initBookingState("bookingState");	//预约状态
	
	$scope.chooseList(1);

});