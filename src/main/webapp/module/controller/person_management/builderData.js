App.controller("builderDataCtrl",function($scope, $rootScope, $location, $http, $compile, path, $stateParams, $sce, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);

	var pay_type = {	//支付方式
			1: '全款',
			2: '定金'
	};
	
	var curUserId = params.curUserID;

	//----------------------
	$scope.userRole = {}; //
	$scope.search = {}//输入框存储对象
	$scope.details = {};  //弹出框存储对象
	$scope.buildDetails = {};  //施工弹出框存储对象
	//初始化搜索条件input及grid显示的模块数据
	$scope.initsearchIput = function(){
		//userInfo.user_type = "3"
		if(userInfo.user_type=="0")
		{
			$scope.userRole.userPartner = "show";		
		}else if(userInfo.user_type=="1")
		{
			$scope.userRole.userPartner = "show";
		}
		else if(userInfo.user_type== "2") //企业用户
		{
			$scope.userRole.userPartner = "show";
		}
		else if(userInfo.user_type=="3")//城市合伙人用户
		{
			$scope.userRole.userPartner = "hidden";
		}
	}
	
	$scope.initsearchIput();            //初始化搜索条件input
	//选择列表
	$scope.chooseList = function(type){
		for(var i=1;i<=4;i++){
			$scope.search = {}//搜索条件清空
			if(i==type){
				$("#list_"+i).addClass("choose");
				$("#list_"+i+"_container").show();
			}else{
				$("#list_"+i).removeClass("choose");
				$("#list_"+i+"_container").hide();
			}
		}
	}
	

	//--------------	推广消费者数据---------------------
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
										id: curUserId,
										customer_name:$scope.search.customer_name,
										customer_phone:$scope.search.customer_phone
									}	
								};
								console.log('查询参数',parameter);
								return kendo.stringify(parameter);
							}
						}
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
					}
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
						width : "100px",
						//template:"<input type='checkbox'  />"+"<span class='row-number'></span>"
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number'></span>"
					},
					{         
						title : "消费者姓名",    
						field : "customer_name",
						width : "100px"
					},
					{         
						title : "联系方式",    
						field : "phone",
						width : "100px"
					},
					{         
						title : "会员属性",    
						field : "level",
						width : "100px",
						values: [
						         { text: "普通用户", value: 0 },
						         { text: "会员", value: 1 }
							    ],
					},
					{         
						title : "注册时间",    
						field : "create_time",
						width : "100px"
					},
					{         
						title : "导购姓名",    
						field : "seller_name",
						width : "100px"
					}
					
					
			]
		};
	
	//查询
	$scope.search1=function(){
		$scope.grid1.dataSource.page(1);
	}
	//--------------	订单收入---------------------
	/*$scope.mainGridOptions2 = {

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
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									id: curUserId,
									order_code: $scope.search.order_code,		 	//订单编号
									seller_name: $scope.search.seller_name,		 	//订单编号
									order_state:$("#order_state").data("kendoDropDownList").value()             //订单状态	
								}
							};
							console.log('查询参数',parameter);
							return kendo.stringify(parameter);
						}
					},
				},
				pageSize : 10,
			     group: {
                     field: "CODE",
                     dir: "desc"  //倒序排列
                   },
				serverPaging : true,
				serverSorting: true,
				schema : {
					data : function(d) {
						return d.data;
					},
					total : function(d) {
						return d.total; //总条数
					}
				}
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
			
			columns : [
			       	{
						field : "CODE",         
						title : "订单号",     
						width : "100px",
						hidden:true,//隐藏当前订单号列
						groupHeaderTemplate:function(e) {
							var payOrder = "" //当前code值为一个组合值，第零个为订单号，第一个为流水号，第二个为下单日期,第三个总额，第四个已支付＋为支付   //之前code值为一个组合值，第零个为订单号，第一个为流水号，第二个为下单日期,第三个总额，第四个定金,第五个预计收入,第六个实际收入
							if(e.value.split("_")[1] != "%"){
								payOrder = "&nbsp&nbsp&nbsp&nbsp 流水号:"+e.value.split("_")[1]
							}

							var curTotalPrice_arr = e.value.split("_")[3].split("+")
							if(curTotalPrice_arr.length>1)
							{
								var totalPrice = '<div  style="float:right;padding:5px 15px 0px 0px;font-size:16px;">已付定金:'+curTotalPrice_arr[0]+"&nbsp&nbsp&nbsp&nbsp剩余尾款:"+curTotalPrice_arr[1]+'</div>'
							}else
							{
								var totalPrice = '<div  style="float:right;padding:5px 15px 0px 0px;font-size:16px;">实付:'+e.value.split("_")[3]+'</div>'
							}
							return '<div style="float:left;padding-top:5px;">'+
							"订单号: "+e.value.split("_")[0] +payOrder+ "&nbsp&nbsp&nbsp&nbsp 日期:"+e.value.split("_")[2]+'</div>'+ totalPrice
							
					      }	
							//"订单号: #= value # 成交日期: #= count #"
					},
					{
						field : "imgurl",         
						title : "产品",     
						width : "250px",
						template:'<div class="row" ><div class="form-group" style="overflow: hidden;margin-bottom: 0px;"><div class="col-md-4" style="position: absolute;top: 50%;transform: translateY(-50%);">'+
						'<img  style="width: 120px;height: 80px;" src="#: imgurl #" /></div>'+
						'<div class="col-md-5" style="padding-left: 150px;">'+
                        '<ul style="float:left;margin-bottom: 0px;" class="list-unstyled">'+
						'<li>#= product_name #</li>'+
						'<li>型号：#= sales_model #</li>'+
						'<li>颜色分类:#= color #</li>'+
						'<li><span>单价：#= price #</span><span style="float:right;"><i class="fa fa-times" aria-hidden="true"></i>#= product_num #</span></li>'+
						'</ul></div></div></div>'
	
					},
					{
						
						field : "customer_name",         
						title : "买家",     
						width : "80px",
						template : "<p class='text-center'>#=customer_name#</p>"
					},
					{
						field : "state",         
						title : "交易状态",     
						width : "80px",  
						template :"<p class='text-center'>#=state#</p>"
					},
					{
						field : "state",         
						title : "收益",     
						width : "80px",  
						template :function(e)
						{
							return "<p class='text-center'>预计收入:"+e.pre_money+"</p></n>"+
							   "<p class='text-center'>实际收入:"+e.fixed_money+"</p>"
						}
					},
					{
						field :"totalprice",    
						title : "金额",     
						width : "80px",  
						template :function(e)
						{
							var order_info = ""
							if(e.info!=null)
							{
								order_info = "<p class='text-center' style='color:#fff;background-color:#01bc81;'>"+e.info+"</p>";
								
							}
							return  "<p class='text-center'>"+e.price*e.product_num+"</p>"+order_info;
							
						}
					},
					{
						field : "pay_type",         
						title : "支付方式",     
						width : "80px",
						values: [
						         { text: "全款", value: 1 },
						         { text: "定金", value: 2 }
							    ],
				       attributes: {
				    	      "class": "table-cell",
				    	      style: "text-align: center;"
				    	    }
//						template : "<p class='text-center'>#=payState#</p>"    
					},
					{
						hidden:$scope.userRole.userEnterprisAndPartnerAndBrandAndShop_hidden_col,////如果是店铺用户，不显示所属店面 
						field : "shop_name",         
						title : "所属店面",     
						width : "80px",  
						template : "<p class='text-center'>#=shop_name#</p>"    
					},
					{
						hidden: $scope.userRole.userEnterprisAndPartnerAndBrand_hidden_col,//如果是品牌用户，不显示关联信息   
						field : "shop_name",     
						title : "关联信息",     
						width : "120px",
						template:
                        '<ul style="float:left;margin-bottom: 0px;" class="list-unstyled">'+
						'<li class = "{{userRole.userPartner}}" >合伙人:#= principal #</li>'+
						'<li class = "{{userRole.userBrand}}" >品牌：#= brand_name #</li>'+
						'</ul>'
						
					},
					{
						field : "seller_name",  //目前没有       
						title : "推广导购",     
						width : "80px",  
						template : "<p class='text-center'>#=seller_name#</p>"
					},
					{
						field : "op_id",         
						title : "操作",     
						width : "80px",
						template : "<div value='查看详情' class='k operation k-state-default' ng-click='showDetails(\"#: op_id #\")' />查看详情</div>"
					},
					
			]
		};*/
	
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
										id: curUserId,
										order_code: $scope.search.order_code,		 	//订单编号
										seller_name: $scope.search.seller_name,		 	//订单编号
										order_state:$("#order_state").data("kendoDropDownList").value(),             //订单状态	
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
								+'<div class="operation k-state-default" ng-click="showDetails('+OneObj1.product_id+')">查看详情</div>'
							+'</td>'
						+'</tr>');
			});
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$(OneObj).after(html);
		});
	}
	
	
	//查询
	$scope.search2=function(){
		$scope.grid2.dataSource.page(1);
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
	
	$("#order_state").kendoDropDownList({
		dataTextField: "name",
	    dataValueField: "value",
		dataSource: [
					{
					    name:"全部",
					    value:""
					},
		             {
			              name:"已发货",
			              value:"1"
	            	  },
	            	  {
			              name:"已收货",
			              value:"2"
	            	  }
		             ],
         change: function(e) {
	    	    var value = this.value();
	    	  //  $scope.search.evaluation_level = value;
	    	  // $scope.search();
	      }
	});
//--------------	施工预约---------------------
	$scope.mainGridOptions3 = {
			dataSource : {
					transport : {
						read : {
							url : path+"/server/selectConstructAppointmentBackForPage",
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
										id: curUserId,
										customer_name: $scope.search.customer_name,		 	//消费者姓名
										account: $scope.search.account,		 	//施工员
										state_code: $("#build_state").data("kendoComboBox").value(),//施工状态
									}	
								};
								console.log('查询参数',parameter);
								return kendo.stringify(parameter);
							}
						}
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
					}
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
						width : "100px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number'></span>"
					},
					{         
						title : "消费者姓名",    
						field : "customer_name",
						width : "100px"
					},
					{         
						title : "联系方式",    
						field : "phone",
						width : "100px"
					},
					{         
						title : "结算费用",    
						field : "construct_data",
						width : "80px"
					},
					{         
						title : "预约时间",    
						field : "start_time",
						width : "100px"
					},
					{         
						title : "结算时间",    
						field : "pay_time",
						width : "100px"
					},
					{         
						title : "预约需求",    
						field : "needs",
						width : "100px"
					},
					{         
						title : "施工员",    
						field : "name",
						width : "80px"
					},
					{         
						title : "预约状态",    
						field : "state_code",
						width : "80px",
						values: [
						         { text: "待接受", value: 1001 },
						         { text: "上门测量", value: 1002 },
						         { text: "开始施工", value: 2001 },
						         { text: "待支付", value: 3001 },
						         { text: "待评价", value: 3002 },
						         { text: "已完成", value: 4001 },
						         { text: "已取消", value: 5001 }
							    ],
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div  value='查看详情' class='operation k-state-default' ng-click='showBuildDetail(\"#: id #\")' />查看详情</div>"
					}
					
					
			]
		};
	//查询
	$scope.search3=function(){
		$scope.grid3.dataSource.page(1);
	}
	
	//初始化施工预约
		$("#build_state").kendoComboBox({
		    dataTextField: "state",
		    dataValueField: "state_code",
		    placeholder: "",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'POST',
	                  url: path+"/server/getconstructstate",
	                  dataType : "json"
	              }
	          },
	          
	      },
	      change: function(e) {
	    	    var value = this.value();
	    	   // $scope.getCityData(value);
	      }
		});
	
	//显示详情
	$scope.showBuildDetail = function(id){
		var postData = new Object();
		postData.id =id;
		$http({
			   url: path+"/server/selectConstructerAppointmentAndTraceingBackByAppopintId",
			   method: 'POST', 
			   data: angular.toJson(postData)
		}).success(function(data){	
			if(data.code==0){  //成功
				var datails = data.result;
				console.log("显示详情页  ",datails);
				if(datails){   //有详情信息
				
					$scope.buildDetails.customer_name = datails.customer_name;//预约姓名
					$scope.buildDetails.customer_phone = datails.customer_phone;//预约电话
					$scope.buildDetails.address_details = datails.address_details;//预约地址，
					$scope.buildDetails.service_type = datails.service_type;//预约施工种类，
					$scope.buildDetails.start_time = datails.start_time;//预约时间，
					$scope.buildDetails.construct_data = datails.construct_data;//施工数据，
					$scope.buildDetails.create_time = datails.create_time;	//创建时间
					$scope.buildDetails.needs = datails.needs;				//预约需求，
					$scope.buildDetails.construct_name = datails.construct_name;//预约施工员，
					
					var html = '<ul style="float:left;" class="list-unstyled">';
					$.each(datails.constructStateRelationList, function(index, OneObj){
						html += ('<li>'
								   +OneObj.state
								   +'</li>'
								   +'<li>' 
								   +OneObj.create_time
								   +'</li>'
								   +'</br>'
							   );
						//html = $compile(html)($scope);   //angularJs代码需要动态编译
						//$("#"+containerId).append(html);	
					});
					html = html+"</ul>"
					console.log(html)
					//$scope.details.state_info = $sce.trustAsHtml(datails.state_info);//订单状态列表
					$scope.buildDetails.state_info = $sce.trustAsHtml(html);//订单状态列表
					$scope.builder_detail.center().open();   //打开弹框
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
	

	//--------------	服务评价 ---------------------
	$scope.mainGridOptions4 = {
			dataSource : {
					transport : {
						read : {
							url : path+"/server/getEvaluationsForPage",
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
										id: curUserId,
										customer_name: $scope.search.customer_name,		 	//消费者姓名
										phone: $scope.search.phone,		 	//联系方式
										seller_name: $scope.search.seller_name		 	//施工员
										
									}	
								};
								console.log('查询参数',parameter);
								return kendo.stringify(parameter);
							}
						}
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
					}
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
						title : "消费者姓名",    
						field : "customer_name",
						width : "100px"
					},
					{         
						title : "联系方式",    
						field : "customer_phone",
						width : "100px"
					},
					{         
						title : "施工员",    
						field : "seller_name",
						width : "80px"
					},
					{         
						title : "评价星级",    
						field : "score",
						width : "80px"
					},
					{         
						title : "评价内容",    
						field : "evaluate_content",
						width : "80px"
					},
					{         
						title : "图片",    
						field : "imgurl",
						width : "80px",
						template:function(e){
							var tempHtml = ""
							var tempurl_arr = e.imgurl.split(",")
							for(var i=0;i<tempurl_arr.length;i++)
								{
									tempHtml = tempHtml+'<img  style="width: 50px;height: 50px;padding:2px;" src='+tempurl_arr[i]+' />'
								}
							
							return tempHtml;
						}
//						template:
//						'<img  style="width: 50px;height: 50px;" src="#: imgurl #" />'+
//						'<img  style="width: 50px;height: 50px;" src="#: imgurl #" />'
					},
					{         
						title : "评价时间",    
						field : "evaluate_time",
						width : "100px"
					}
					
			]
		};
	//查询
	$scope.search4=function(){
		$scope.grid4.dataSource.page(1);
	}
	
	
	$scope.chooseList(1);   //选择列表


});