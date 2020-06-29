App.controller("orderClearDetailsCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, $sce, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.clearDetails = {};	//结算详情
	$scope.orderDetails = {};	//结算详情
	var orderClearInfo = null;	//订单结算信息
	var pay_type = {	//支付方式
			1: '全款',
			2: '定金'
	};
	
	
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryShellRealOrdersListById", 
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
									shell_id: params.orderId,		//结算订单id
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
		        $scope.showProductInfo("grid",dataList);	//显示产品信息
		        
		        console.info("开始表格合并......");
				publicService.tableTdRowspan("grid",[8,7,6,5,3,2]);	//表格合并
		    },
		    columns : [
						{    
							title : "序号",
							field : "id", 
							width : "50px",
							template:"<input type='checkbox' class='product_order' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
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
								if(e.money_platform){	//平台佣金
									html += '&nbsp;&nbsp;&nbsp;&nbsp;平台佣金：'+e.money_platform;
								}
								//导购提点
								html += '&nbsp;&nbsp;&nbsp;&nbsp;导购提点：'+e.amount;
								
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
									if(e.order_code=='1000' || e.order_code=='4000'){
										html += '&nbsp;&nbsp;&nbsp;&nbsp;实付：0';
									}else if(e.order_code=='5000'){	//已删除
										if(e.order_state_code=='4000'){
											html += '&nbsp;&nbsp;&nbsp;&nbsp;实付：0';
										}else{
											html += '&nbsp;&nbsp;&nbsp;&nbsp;实付：'+(e.order_price+e.deposit_price);
										}
									}else{
										html += '&nbsp;&nbsp;&nbsp;&nbsp;实付：'+(e.order_price+e.deposit_price);
									}
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
							title : "应付",     
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
			var orderText2 = $(OneObj).children('td:eq(2)').html();

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
							+'<td role="gridcell"><p>'+(OneObj1.price*OneObj1.product_num).toFixed(2)+'</p>'+cashInfo_html+'</td>'
							+'<td role="gridcell">'+pay_type[OneObj1.sales_model]+'</td>'
							+'<td role="gridcell">'
								+'<div style="text-align: left;">'
									+relationInfo_html
								+'</div>'
							+'</td>'
							+'<td role="gridcell">'+orderInfo.seller_name+'</td>'
							+'<td role="gridcell">'+orderInfo.order_price.toFixed(2)+'</td>'
							+'<td role="gridcell">'
								+'<div class="operation k-state-default" ng-click="showDetails('+OneObj1.product_id+')">查看详情</div>'
							+'</td>'
						+'</tr>');
			});
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$(OneObj).after(html);
		});
	}
	
	//显示详情
	$scope.showDetails = function(id){
		$http({
			   url: path+"/server/customerorderdetails/"+id,
			   method: 'GET'
		}).success(function(data){
			
			if(data.code==0){  //成功
				var datails = data.result;
				console.log("显示详情页  ",datails);
				if(datails){   //有详情信息
				
					$scope.orderDetails.imgurl = datails.imgurl;//图片，
					$scope.orderDetails.code = datails.code;//订单号
					$scope.orderDetails.create_time = datails.create_time;//交易时间
					$scope.orderDetails.address = datails.address; //收货地址
					$scope.orderDetails.product_name = datails.product_name;//产品名称
					$scope.orderDetails.sales_model = datails.sales_model;//产品型号
					$scope.orderDetails.color = datails.color;//颜色分类
				
					$scope.orderDetails.price = datails.price;//单价
					$scope.orderDetails.product_num = datails.product_num;//购买数量
					$scope.orderDetails.totalprice = datails.totalprice;//总价
				
					$scope.orderDetails.state = datails.state;//收货状态
					$scope.orderDetails.customerName = datails.customer_name;//买家昵称
					$scope.orderDetails.receiver_name = datails.receiver_name;//收货人姓名
					$scope.orderDetails.customer_phone = datails.customer_phone;//联系方式
				
					$scope.orderDetails.shop_name = datails.shop_name;//店铺名称
					$scope.orderDetails.principal = datails.principal;//合伙人名称
					$scope.orderDetails.brand_name = datails.brand_name;//品牌名称
					$scope.orderDetails.enterpise_name = datails.enterpise_name;//企业名称
					$scope.orderDetails.seller_name = datails.seller_name;//推广导购
					$scope.orderDetails.state_info = $sce.trustAsHtml(datails.state_info);//订单状态列表
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

	//显示结算进程
	$scope.showClearProcess = function(containerId,state){
		var html = '';
		var color1 = '#00cf61';
		var color2 = 'gray';
		var circleColorArr = [];
		var polygonColorArr = [];
		var shop_state = '商家已确认';
		
		if(state=="100000"){
			circleColorArr = [color1,color2,color2,color2];
			polygonColorArr = [color1,color2,color2];
		}else if(state=="200000"){	//商家已确认
			circleColorArr = [color1,color1,color2,color2];
			polygonColorArr = [color1,color1,color2];
		}else if(state=="300000"){
			circleColorArr = [color1,color1,color1,color2];
			polygonColorArr = [color1,color1,color1];
		}else if(state=="400000"){
			circleColorArr = [color1,color1,color1,color1];
			polygonColorArr = [color1,color1,color1];
		}else if(state=="600000"){	//拒绝
			circleColorArr = [color1,color1,color2,color2];
			polygonColorArr = [color1,color2,color2];
			shop_state = '商家已拒绝';
		}
		
		var containerWidth = Math.floor(parseInt($("#"+containerId).css('width')));
		var cx = containerWidth/8;
		var polygon_core = cx*2;
		var unit = cx/2;
		var unit_2 = unit/2;

		html += ($scope.getCircle_html($scope.initCircleParam(cx*1,75,cx*0.3,'black',1,circleColorArr[0]))
				+$scope.getCircle_html($scope.initCircleParam(cx*3,75,cx*0.3,'black',1,circleColorArr[1]))
				+$scope.getCircle_html($scope.initCircleParam(cx*5,75,cx*0.3,'black',1,circleColorArr[2]))
				+$scope.getCircle_html($scope.initCircleParam(cx*7,75,cx*0.3,'black',1,circleColorArr[3]))
				+$scope.getPolygon_html($scope.initPolygonParam([[cx*2-unit,65],[cx*2+unit_2,65],[cx*2+unit_2,50],[cx*2+unit,75],[cx*2+unit_2,100],[cx*2+unit_2,85],[cx*2-unit,85]],'purple',1,polygonColorArr[0]))
				+$scope.getPolygon_html($scope.initPolygonParam([[cx*4-unit,65],[cx*4+unit_2,65],[cx*4+unit_2,50],[cx*4+unit,75],[cx*4+unit_2,100],[cx*4+unit_2,85],[cx*4-unit,85]],'purple',1,polygonColorArr[1]))
				+$scope.getPolygon_html($scope.initPolygonParam([[cx*6-unit,65],[cx*6+unit_2,65],[cx*6+unit_2,50],[cx*6+unit,75],[cx*6+unit_2,100],[cx*6+unit_2,85],[cx*6-unit,85]],'purple',1,polygonColorArr[2]))
				+$scope.getText_html($scope.initTextParam(cx*1-unit_2*0.9,135,'black','订单生成'))
				+$scope.getText_html($scope.initTextParam(cx*3-unit_2,135,'black',shop_state))
				+$scope.getText_html($scope.initTextParam(cx*5-unit_2,135,'black','平台已确认'))
				+$scope.getText_html($scope.initTextParam(cx*7-unit_2,135,'black','已完成结算'))
				+$scope.getText_html($scope.initTextParam(cx*2-unit*0.8,120,'black','商家确认中'))
				+$scope.getText_html($scope.initTextParam(cx*4-unit*0.8,120,'black','平台确认中'))
				+$scope.getText_html($scope.initTextParam(cx*6-unit*0.8,120,'black','财务付款中'))
		);

		$("#"+containerId).html(html);
	}
	
	//初始化圆
	$scope.initCircleParam = function(cx,cy,r,stroke,stroke_width,fill){
		var paramObj = {
				cx: cx,
				cy: cy,
				r: r,
				stroke: stroke,
				stroke_width: stroke_width,
				fill: fill
		};
		
		return paramObj;
	}
	
	//获取圆html
	$scope.getCircle_html = function(paramObj){
		var html = ('<circle cx="'+paramObj.cx+'" cy="'+paramObj.cy+'" r="'+paramObj.r+'" stroke="'+paramObj.stroke+'" stroke-width="'+paramObj.stroke_width+'" fill="'+paramObj.fill+'"/>');

		return html;
	}
	
	//初始化多边形
	$scope.initPolygonParam = function(points,stroke,stroke_width,fill){
		var paramObj = {
				points: points.join(" "),
				stroke: stroke,
				stroke_width: stroke_width,
				fill: fill
		};
		
		return paramObj;
	}
	
	//获取多边形html
	$scope.getPolygon_html = function(paramObj){
		var html = ('<polygon points="'+paramObj.points+'" style="fill:'+paramObj.fill+';stroke:'+paramObj.stroke+';stroke-width:'+paramObj.stroke_width+'"/>');

		return html;
	}
	
	//初始化文本
	$scope.initTextParam = function(x,y,fill,text){
		var paramObj = {
				x: x,
				y: y,
				fill: fill,
				text: text
		};
		
		return paramObj;
	}
	
	//获取文本html
	$scope.getText_html = function(paramObj){
		var html = ('<text x="'+paramObj.x+'" y="'+paramObj.y+'" fill="'+paramObj.fill+'">'+paramObj.text+'</text>');

		return html;
	}
	
	//获取订单结算信息
	$scope.getOrderClearInfo = function(){
		$http({
			   url: path+'/server/queryOneShellOrderById/'+params.orderId,
			   method: 'GET' 
		}).success(function(data){
			if(data.code==0){  //成功
				orderClearInfo = data.result;
				
				$scope.clearDetails.shell_code = orderClearInfo.shell_code;
				$scope.clearDetails.create_time = orderClearInfo.create_time;
				$scope.clearDetails.enterprise_name = orderClearInfo.enterprise_name;
				$scope.clearDetails.principal = orderClearInfo.principal;
				$scope.clearDetails.brand_name = orderClearInfo.brand_name;
				$scope.clearDetails.shop_name = orderClearInfo.shop_name;
				$scope.clearDetails.money_pre = orderClearInfo.money_pre;
				$scope.clearDetails.money_platform = orderClearInfo.money_platform;
				$scope.clearDetails.seller_money = orderClearInfo.seller_money;
				$scope.clearDetails.money_trade = orderClearInfo.money_trade;
				
				$scope.showClearProcess("clearProcess",orderClearInfo.shell_state);	//显示结算进程
			}else{
				alert("获取信息失败！");
			}
		}).error(function(data){
			alert("获取信息失败！");   
		})
	}
	
	//显示订单详情
	$scope.showOrderDetails = function(id){
		return;
		$http({
			   url: path+"/server/customerorderdetails/"+id,
			   method: 'GET'
		}).success(function(data){
			
			if(data.code==0){  //成功
				
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	
	
	
	$scope.getOrderClearInfo();	//获取订单结算信息
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业	
	
	}else if(userInfo.user_type==3){  //合伙人
		
	}else if(userInfo.user_type==4){  //品牌
		
	}else if(userInfo.user_type==5){  //店铺

	}
	

});