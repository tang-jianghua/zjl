App.controller("cardListCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	$scope.list3_show = true;	//特权现金券订单(开发者，企业，合伙人)
	$scope.list4_show = true;	//卡券列表(开发者，企业，合伙人)
	
	$scope.enterprise_show = true;
	$scope.partner_show = true;
	$scope.brand_show = true;
	
	//模块选择
	$scope.chooseList = function(type){
		var params={}
		if(type==1){
			$location.path("/orderList");
		}else if(type==2){
			params.orderType=1;
			$location.path("/orderElseList/"+angular.toJson(params));
		}else if(type==3){
			params.orderType=2;
			$location.path("/orderElseList/"+angular.toJson(params));
		}else if(type==4){
			$location.path("/cardList");
		}
	}
	
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryOrderBrandCoupons",
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
									enterprise_name:  $("#enterprise_name").data("kendoAutoComplete").value(),	 	//企业名称
									principal: $("#partner_name").data("kendoAutoComplete").value(),		 	    //合伙人名称	
									brand_name: $("#brand_name").data("kendoAutoComplete").value(),           		//品牌名称
									coupons_name: $scope.search.coupons_name,	//卡券名称
									customer_name: $scope.search.customer_name,	//买家名称
									order_code: $scope.search.order_code,		//订单编号
									start_time: $("#start_time").val(),	//成交时间（始）
									end_time: $("#end_time").val(),		//成交时间（止）
								}
							};
							console.log('查询参数',parameter);
							return kendo.stringify(parameter);
						}
					},
				},
				pageSize : 10,
				group: {
                    field: "code",
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
		            var index = ($(this).index() + 1)/2 + page * pagesize;
		            var rowLabel = $(this).find(".row-number");
		            $(rowLabel).html(index);
		        });
		    },
			columns : [
					{    
						title : "序号",
						field : "id", 
						width : "50px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{
						field : "code",         
						title : "订单号",     
						width : "100px",
						hidden:true,//隐藏当前订单号列
						groupHeaderTemplate:function(e) {	//当前code值为一个组合值，第一个为订单号，第二个为流水号，第三个为下单日期,第四个订单id，第五个订单状态,第六个总价
							var html = '';
							var codeArr = e.value.split("_");
							
							html += '<div style="float:left;padding-top:5px;">'
									+'订单号:'+codeArr[0]+'&nbsp;&nbsp;&nbsp;&nbsp;流水号:'+codeArr[1]
								  +'</div>';
							
							return html;
				        }	
					},
					{
						field : "imgurl",         
						title : "产品",     
						width : "150px",
						template :function(e){
							var html = '';
							
							html = '<img class="productImage" src="'+e.imgurl+'"/>';
							
							return html;
						}
					},
					{
						field : "cash_value",         
						title : "额度",     
						width : "80px"
					},
					{
						field : "order_price",         
						title : "金额",     
						width : "80px"
					},
					{
						field : "customer_name",         
						title : "买家",     
						width : "100px"
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "100px"
					},
					{
						field : "isused",         
						title : "使用状态",     
						width : "80px",
						template :function(e){
							var html = '';
							
							if(e.exp_date_state==0){	//过期
								html += '已过期';
							}else if(e.exp_date_state==1){	//未过期
								if(e.isused==0){
									html += '未使用';
								}else if(e.isused==1){
									html += '已使用';
								}else{
									html += '已删除';
								}
							}
							
							return html;
						}
					},
					{
						field : "use_time",         
						title : "使用时间",     
						width : "100px"
					},
					{
						field : "product_order_code",         
						title : "产品订单",     
						width : "100px"
					},
					{
						field : "customer_pay_id",         
						title : "支付渠道",     
						width : "100px",
						values: [
						         { text: "未支付", value: null },
						         { text: "支付宝", value: 1 },
						         { text: "微信", value: 2 },
						         { text: "平台", value: 3 }
				       ]
					},
					{
						field : "",         
						title : "关联信息",     
						width : "150px",
						template :function(e){
							var html = '';
							var relationInfo_html = '';
							
							if(userInfo.user_type==1){
								relationInfo_html += ('<div>企业：'+e.enterpise_name+'</div>'
										+'<div>合伙人：'+e.principal+'</div>'
										+'<div>品牌：'+e.brand_name+'</div>');
							}else if(userInfo.user_type==2){
								relationInfo_html += ('<div>合伙人：'+e.principal+'</div>'
										+'<div>品牌：'+e.brand_name+'</div>');
							}else if(userInfo.user_type==3){
								relationInfo_html += ('<div>品牌：'+e.brand_name+'</div>');
							}else if(userInfo.user_type==4){
								
							}
							
							html = '<div style="text-align: left;">'
										+relationInfo_html
								  +'</div>';
							
							return html;
						}
					},
					{
						field : "seller_name",         
						title : "推广导购",     
						width : "100px"
					},
					{
						field : "create_time",         
						title : "领取时间 ",     
						width : "100px"
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}

	publicService.initEnterpriseName("enterprise_name");	//企业名称
	publicService.initPartnerName("partner_name");			//合伙人名称
	publicService.initBrandName("brand_name");				//品牌名称
	publicService.initDateTime("start_time");			//成交时间（始）
	publicService.initDateTime("end_time");			//成交时间（止）
	

	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业
		$scope.enterprise_show = false;
	}else if(userInfo.user_type==3){  //合伙人
		$scope.enterprise_show = false;
		$scope.partner_show = false;
	}else if(userInfo.user_type==4){  //品牌
		$scope.list3_show = false;
		
		$scope.enterprise_show = false;
		$scope.partner_show = false;
		$scope.brand_show = false;
	}else if(userInfo.user_type==5){  //店铺
		$scope.list3_show = false;
		$scope.list4_show = false;
		
	}

});