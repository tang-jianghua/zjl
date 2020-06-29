App.controller("orderListCtrl",function($scope, $rootScope, $location, $http, $compile, path, $sce, publicService) {
	
	
	$scope.list3_show = true;	//特权现金券订单(开发者，企业，合伙人)
	$scope.list4_show = true;	//卡券列表(开发者，企业，合伙人)
	
	var pay_type = {	//支付方式
			1: '全款',
			2: '定金'
	};
	
	$scope.exData;
	
	
	$scope.details = {};  //定点列表
	//如果当前权限开发者，则显示城市合伙人和企业，如果当前为企业，则显示城市合伙人，如果当前为城市合伙人，则不显示城市合伙人搜索框
	$scope.userRole = {}; //显示企业
	
		//选择列表
		$scope.chooseList = function(type){
			var params={}//1.产品订单 2.定金订单  3.现金券订单

			if(type==1){
				$location.path("/orderList");
			}else if(type==2){
				params.orderType=1;
				console.log(angular.toJson(params))
				$location.path("/orderElseList/"+angular.toJson(params));
			}else if(type==3){
				params.orderType=2;
				$location.path("/orderElseList/"+angular.toJson(params));
			}else if(type==4){
				$location.path("/cardList");
			}
		}
	//初始化搜索条件input及grid显示的模块数据
	$scope.initsearchIput = function(){
		//userInfo.user_type = "1"
		$scope.userRole.userEnterprisAndPartnerAndBrand_hidden_col = false;//品牌用户不显示关联信息列表
		$scope.userRole.userEnterprisAndPartnerAndBrandAndShop_hidden_col = false;//店铺用户不显示所属店面列表 
		if(userInfo.user_type=="0")
		{
		$scope.userRole.userPartner = "show";  //搜索框及关联信息显示
		$scope.userRole.userEnterpris = "show";
		$scope.userRole.userBrand = "show";
		$scope.userRole.userShop = "show";
		}else if(userInfo.user_type=="1")
		{
			$scope.userRole.userPartner = "show";  //搜索框及关联信息显示
			$scope.userRole.userEnterpris = "show";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
			}
		else if(userInfo.user_type== "2") //企业用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "show";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
		}
		else if(userInfo.user_type=="3")//城市合伙人用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
		}else if(userInfo.user_type=="4")//品牌用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "hidden";
			$scope.userRole.userEnterprisAndPartnerAndBrand_hidden_col= true;//grid关联信息整列不显示
			$scope.userRole.userShop = "show";
		}else if(userInfo.user_type=="5")//店铺用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "hidden";
			$scope.userRole.userEnterprisAndPartnerAndBrand_hidden_col= true;//grid关联信息整列不显示
			$scope.userRole.userShop = "hidden";
			$scope.userRole.userEnterprisAndPartnerAndBrandAndShop_hidden_col = true;//品牌用户不显示关联信息列表 	
		}
	}
	
	$scope.initsearchIput();            //初始化搜索条件input
	
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/orderlist",
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
									sales_model: $scope.search.sales_model,	 	//产品型号
									shop_name: $("#autoComShopName").data("kendoAutoComplete").value(),		   		//店面名称
									brand_name: $("#autoComBrandName").data("kendoAutoComplete").value(),	           //品牌名称
									principal: $("#autoComPartnerName").data("kendoAutoComplete").value(),	 	//合伙人名称	
									product_title: $scope.search.product_title,		 	//产品名称
									enterprise_name:  $("#autoComeEnterprisName").data("kendoAutoComplete").value(),			 	//企业名称
									province: $("#province").data("kendoComboBox").value(),		//省
									city: $("#city").data("kendoComboBox").value(),				//市
									county: $("#area").data("kendoComboBox").value(),			//区
									order_code: $scope.search.order_code,		 	//订单编号
									customer_name: $scope.search.customer_name,	    //买家名称
									start_time: $("#start_time").data("kendoDateTimePicker").value(),	//预约时间（始）
									end_time: $("#end_time").data("kendoDateTimePicker").value(),		//预约时间（止）
									order_state:$scope.search.state_name,             //订单状态
									deposit_price:$("#autoComeDepositPrice").data("kendoDropDownList").value(),//尾款支付
									sort:sort
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
						$scope.exData=d.data;
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
							if(userInfo.user_type==5){	//店铺, 1000待付款 1001待发货 2000待收货 2001待评价 3000交易完成 4000已取消 5000已删除  7000上门测量
								if(e.order_code=='1001'){	//待发货
									html += '<button class="k-button k-state-default" style="margin-left: 10px;" ng-click="sendGoods('+e.id+',2000)">发货</button>';
								}else if(e.order_code=='7000'){	//上门测量
									html += '<button class="k-button k-state-default" style="margin-left: 10px;" ng-click="sendGoods('+e.id+',1001)">完成测量</button>';
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
						title : "支付渠道",     
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
							+'<td colspan="10">'
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
				
				var pay_way_html = '未支付';		//支付渠道
				if(orderInfo.customer_pay_id==1){
					pay_way_html = '支付宝';
				}else if(orderInfo.customer_pay_id==2){
					pay_way_html = '微信';
				}else if(orderInfo.customer_pay_id==3){
					pay_way_html = '平台';
				}
				
				html += ('<tr>'
							+'<td role="gridcell">'+(index1+1)+'</td>'	//序号
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
							+'<td role="gridcell">'+orderInfo.receiver_name+'</td>'	//买家
							+'<td role="gridcell">'+orderInfo.state+'</td>'			//交易状态
							+'<td role="gridcell"><p>'+(OneObj1.price*OneObj1.product_num).toFixed(2)+'</p>'+cashInfo_html+'</td>'	//金额
							+'<td role="gridcell">'+pay_type[OneObj1.sales_model]+'</td>'	//支付方式
							+'<td role="gridcell">'+pay_way_html+'</td>'	//支付渠道
							+'<td role="gridcell">'	//关联信息
								+'<div style="text-align: left;">'
									+relationInfo_html
								+'</div>'
							+'</td>'
							+'<td role="gridcell">'+orderInfo.seller_name+'</td>'	//推广导购
							+'<td role="gridcell">'+orderInfo.order_price.toFixed(2)+'</td>'	//应付
							+'<td role="gridcell">'	//操作
								+'<div class="operation k-state-default" ng-click="showDetails('+OneObj1.id+')">查看详情</div>'	
							+'</td>'
						+'</tr>');
			});
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$(OneObj).after(html);
		});
	}

	//查询
	$scope.search=function(){
		$scope.grid.dataSource.page(1);
	}
	
	//初始化省
	$scope.initProvince = function(){
		$("#province").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "code",
		    placeholder: "省",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/province",
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
	    	    var value = this.value();
	    	    $scope.getCityData(value);
	      }
		});
	}
	
	//获取市数据
	$scope.getCityData = function(provinceCode,defaultVal){
		$http({
			   url: path+"/server/city/"+provinceCode,
			   method: 'GET'
		}).success(function(data){
			$scope.initCity(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化市
	$scope.initCity = function(cityData,defaultVal){
		if(cityData){
			var plugObj = $("#city").data("kendoComboBox");
			plugObj.value("");
			plugObj.setDataSource(cityData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#city").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "code",
			    placeholder: "市",
			    filter: "contains",
			    dataSource: [],
			    change: function(e) {
		    	    var value = this.value();
		    	    $scope.getAreaData(value);
		      }
			});
		}
	}
	
	//获取区数据
	$scope.getAreaData = function(cityCode,defaultVal){
		$http({
			   url: path+"/server/county/"+cityCode,
			   method: 'GET'
		}).success(function(data){
			$scope.initArea(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化区
	$scope.initArea = function(areaData,defaultVal){
		if(areaData){
			var plugObj = $("#area").data("kendoComboBox");
			plugObj.setDataSource(areaData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#area").kendoComboBox({
				  dataTextField: "name",
				  dataValueField: "code",
				  placeholder: "区",
				  filter: "contains",
				  dataSource: []
			});
		}
	}
	
	//初始化时间控件
	$scope.initDateTime = function(containerId){
		$("#"+containerId).kendoDateTimePicker({
		     value: "",
		     culture: "de-DE",
		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
		     interval: 30,   				  //时间间隔
		     timeFormat: "HH:mm" 			  //24 hours format
		});
	}
//	//初始化订单状态按钮
//	$scope.initStateBtnGroup = function(){
//		var mybtn = $('#stateBtnGroup :button');
//			mybtn.each(function(i){
//					$(this).click(function()
//					{
//						console.log("this  "+i)
//					}		
//				)
//			}		
//		);
//	}
	//当订单状态按钮进行点击
	//1000待付款 1001待发货 2000待收货 2001待评价 3000交易完成 4000已取消 5000已删除  7000上门测量
	$scope.setState = function($event,_btnState){
		$scope.valuename = _btnState;
		console.log($scope.valuename)
		//console.log("点击的订单状态：",_btnState);
		if(_btnState == "")
		{
			$scope.search.state_name =undefined;
		}else
		{
			$scope.search.state_name = _btnState;
//			console.log($event)
//			$event.target.className+=' oneList';
		}
		
		$scope.search();
	}
	//获取订单状态列表
	$scope.getOrderStateClassData = function(){
		var url = path+'/server/orderstate';
		$http({
			   url: url,
			   method: 'GET' 
		}).success(function(data){  
			console.log("订单状态列表：",data);
			$scope.btnStateNames = data;

		}).error(function(data){
			alert(data);   
		})
	}
	
	//当用户为商铺时，并且买家已经付款，待发货状态
	$scope.sendGoods = function(_codeNum,_codeState){
		console.log("_codeNum   "+_codeNum+"   "+_codeState);
		$scope.customerorder={}
		$scope.customerorder.order_id = _codeNum;
		$scope.customerorder.order_state_code = _codeState;
		var url = path+'/server/customerorder';
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.customerorder)
		}).success(function(data){  
			if(_codeState == 2000)
			{
				alert("确认发货")
			}else if(_codeState == 1001)
			{
				alert("确认上门测量")
			}
			$("#sendGoodsBtn").hide();
			
			$scope.search();
		}).error(function(data){
			alert(data);   
		})
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
				
					$scope.details.imgurl = datails.imgurl;//图片，
					$scope.details.code = datails.code;//订单号
					$scope.details.create_time = datails.create_time;//交易时间
					$scope.details.address = datails.address; //收货地址
					$scope.details.product_name = datails.product_name;//产品名称
					$scope.details.sales_model = datails.sales_model;//产品型号
					$scope.details.color = datails.color;//颜色分类
				
					$scope.details.price = datails.price;//单价
					$scope.details.product_num = datails.product_num;//购买数量
					$scope.details.totalprice = datails.totalprice;//总价
				
					$scope.details.state = datails.state;//收货状态
					$scope.details.customerName = datails.customer_name;//买家昵称
					$scope.details.receiver_name = datails.receiver_name;//收货人姓名
					$scope.details.customer_phone = datails.customer_phone;//联系方式
				
					$scope.details.shop_name = datails.shop_name;//店铺名称
					$scope.details.principal = datails.principal;//合伙人名称
					$scope.details.brand_name = datails.brand_name;//品牌名称
					$scope.details.enterpise_name = datails.enterpise_name;//企业名称
					$scope.details.seller_name = datails.seller_name;//推广导购
					//$scope.details.state_info = datails.state_info;
					$scope.details.state_info = $sce.trustAsHtml(datails.state_info);//订单状态列表
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
	
	
//	//获取店面名称输入框提示列表
//	$scope.autoComShopNameloadData = new kendo.data.DataSource({
//		transport : {
//			read : {
//				url : path+"/server/shopname",
//				type : 'POST',
//				dataType : "json",
//				contentType : "application/json"
//			}
//		},
//		requestEnd: function(e) {
//		}
//	});
	$("#autoComShopName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/shopname",
				type : 'POST',
				dataType : "json",
				contentType : "application/json"
			}
		},
		requestEnd: function(e) {
		}},
        filter: "startswith",
        placeholder: "输入名称／首字母搜索",
  
    });
	
	//获取品牌名称输入框提示列表
	$("#autoComBrandName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/brandname",
				type : 'POST',
				dataType : "json",
				contentType : "application/json"
			}
		},
		requestEnd: function(e) {
		}},
        filter: "startswith",
        placeholder: "输入名称／首字母搜索",
  
    });
	//获取合伙人名称输入框提示列表
	$("#autoComPartnerName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/partnername",
				type : 'POST',
				dataType : "json",
				contentType : "application/json"
			}
		},
		requestEnd: function(e) {
		}},
        filter: "startswith",
        placeholder: "输入名称／首字母搜索",
  
    });
	//获取企业名称输入框提示列表
	$("#autoComeEnterprisName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/enterprisename",
				type : 'POST',
				dataType : "json",
				contentType : "application/json"
			}
		},
		requestEnd: function(e) {
		}},
        filter: "startswith",
        placeholder: "输入名称／首字母搜索",
  
    });
	
	//获取订单状态列表
	$scope.initTotalData = function(){
		var url = path+'/server/selectordertotalprice';
		$http({
			   url: url,
			   method: 'GET' 
		}).success(function(data){  
			if(data.code==0){  //成功
				var datails = data.result;
				console.log("订单总额和订单数量：",datails);
				if(datails){   //有详情信息
					$scope.order_totalprice = datails.order_totalprice;
					$scope.order_count = datails.order_count;
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
	
	//初始化尾款支付状态
		$("#autoComeDepositPrice").kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
						{
						    name:"全部",
						    value:""
						},
			             {
				              name:"支付",
				              value:"1"
		            	  },
		            	  {
				              name:"未支付",
				              value:"0"
		            	  }
			             ],
             change: function(e) {
		    	    var value = this.value();
		    	    $scope.search.deposit_price = value;
		      }
		});

	//$scope.initStateBtnGroup();		//初始订单状态按钮
	$scope.getOrderStateClassData();		//初始订单状态按钮
	$scope.initProvince();			//初始化省
	$scope.initCity();				//初始化市
	$scope.initArea();				//初始化区
	$scope.initDateTime("start_time");   //预约时间（始）
	$scope.initDateTime("end_time");   	 //预约时间（止）
	$scope.initTotalData();   	 //底部订单总额和订单数量的接口
	//导出
	$scope.dataExport=function(){
		var sort = [{dir:"desc",field:"o.create_time"}];
		var param={
				sales_model: $scope.search.sales_model,	 	//产品型号
				shop_name: $("#autoComShopName").data("kendoAutoComplete").value(),		   		//店面名称
				brand_name: $("#autoComBrandName").data("kendoAutoComplete").value(),	           //品牌名称
				principal: $("#autoComPartnerName").data("kendoAutoComplete").value(),	 	//合伙人名称	
				product_name: $scope.search.product_name,		 	//产品名称
				enterprise_name:  $("#autoComeEnterprisName").data("kendoAutoComplete").value(),			 	//企业名称
				province: $("#province").data("kendoComboBox").value(),		//省
				city: $("#city").data("kendoComboBox").value(),				//市
				county: $("#area").data("kendoComboBox").value(),			//区
				order_code: $scope.search.order_code,		 	//订单编号
				start_time: $("#start_time").data("kendoDateTimePicker").value(),	//预约时间（始）
				end_time: $("#end_time").data("kendoDateTimePicker").value(),		//预约时间（止）
				order_state:$scope.search.state_name,             //订单状态
				deposit_price:$("#autoComeDepositPrice").data("kendoDropDownList").value(),//尾款支付
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "order_module");  
	    
	    var my_input_page = $('<input type="text" name="page" />');  
	    /*{page:1,pageSize:10,ispage:false,"param":param}*/
	    my_input_page.attr('value', angular.toJson({page:1,pageSize:10,ispage:false,"param":param}));  
	    // 附加到Form  
	    form.append(my_input);  
	    form.append(my_input_page);  
	    console.log(form.serializeArray());
	    form.submit();  
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridOrder").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){
				obj.checked=false;
			});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){
				obj.checked=true;
			});
		}
	}
	//选中导出
	$scope.dataCheckboxExport=function(){
		var grid = $("#gridOrder").data("kendoGrid");
		var idData = publicService.getChooseValueByClassName('product_order');
		
		if(idData.length==0){
			alert('请选择要导出的订单！');
			return;
		}
		
		var sort = [{dir:"desc",field:"o.create_time"}];
		var param={
				ids:idData.toString(),
				sales_model: $scope.search.sales_model,	 	//产品型号
				shop_name: $("#autoComShopName").data("kendoAutoComplete").value(),		   		//店面名称
				brand_name: $("#autoComBrandName").data("kendoAutoComplete").value(),	           //品牌名称
				principal: $("#autoComPartnerName").data("kendoAutoComplete").value(),	 	//合伙人名称	
				product_title: $scope.search.product_title,		 	//产品名称
				enterprise_name:  $("#autoComeEnterprisName").data("kendoAutoComplete").value(),			 	//企业名称
				province: $("#province").data("kendoComboBox").value(),		//省
				city: $("#city").data("kendoComboBox").value(),				//市
				county: $("#area").data("kendoComboBox").value(),			//区
				order_code: $scope.search.order_code,		 	//订单编号
				start_time: $("#start_time").data("kendoDateTimePicker").value(),	//预约时间（始）
				end_time: $("#end_time").data("kendoDateTimePicker").value(),		//预约时间（止）
				order_state:$scope.search.state_name,             //订单状态
				deposit_price:$("#autoComeDepositPrice").data("kendoDropDownList").value(),//尾款支付
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "order_module");  
	    
	    var my_input_page = $('<input type="text" name="page" />');  
	    /*{page:1,pageSize:10,ispage:false,"param":param}*/
	    my_input_page.attr('value', angular.toJson({page:1,pageSize:10,ispage:false,"param":param}));  
	    // 附加到Form  
	    form.append(my_input);  
	    form.append(my_input_page);  
	    form.submit(); 
	    
	}
	
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业
		
	}else if(userInfo.user_type==3){  //合伙人
		
	}else if(userInfo.user_type==4){  //品牌
		$scope.list3_show = false;
	}else if(userInfo.user_type==5){  //店铺
		$scope.list3_show = false;
		$scope.list4_show = false;
	}
	
	
	
});