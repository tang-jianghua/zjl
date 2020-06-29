App.controller("orderClearCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {

	$scope.clearSetForm = {};	//结算设置信息
	
	$scope.clearSet_show = true;
	$scope.enterprise_show = true;
	$scope.partner_show = true;
	$scope.brand_show = true;
	$scope.shop_show = true;
	
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryShellOrderListBypage", 
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"desc",field:"sl.create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								if(options.sort[0].field=="create_time"){
									options.sort[0].field="sl.create_time"
								}
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									shell_code: $scope.search.shell_code,	//结算编号
									shell_state: $("#clear_state").data("kendoDropDownList").value(),	//结算状态
									start_time: $("#start_time").val(),	//结算日期（始）
									end_time: $("#end_time").val(),		//结算日期（止）
									enterprise_name: $("#enterprise_name").val(),	//企业名称
									principal: $("#partner_name").val(),			//合伙人
									brand_name: $("#brand_name").val(),				//品牌名称
									shop_name: $("#shop_name").val(),				//店铺名称
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
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{
						field : "shell_code",         
						title : "结算编号",     
						width : "100px"
					},
					{
						field : "enterprise_name",         
						title : "企业",     
						width : "100px"
					},
					{
						field : "principal",         
						title : "合伙人",     
						width : "80px"
					},
					{
						field : "brand_name",         
						title : "品牌",     
						width : "100px"
					},
					{
						field : "shop_name",         
						title : "店铺",     
						width : "100px"
					},
					{
						field : "create_time",         
						title : "结算时间",     
						width : "100px"
					},
					{
						field : "money_pre",         
						title : "结算金额",     
						width : "100px"
					},
					{
						field : "money_platform",         
						title : "平台佣金",     
						width : "100px"
					},
					{
						field : "seller_money",         
						title : "导购提点",     
						width : "100px"
					},
					{
						field : "shell_state",         
						title : "结算状态",     
						width : "80px",
						values: [
					         { text: "订单生成", value: "100000" },
					         { text: "商家已确认", value: "200000" },
					         { text: "平台已确认", value: "300000" },
					         { text: "已完成结算", value: "400000" },
					         //{ text: "完成", value: "500000" },
					         { text: "拒绝", value: "600000" }
					    ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "120px",
						template:function(e){
							var html = '';
							
							if(userInfo.user_type==1){  //开发者
								if(e.shell_state=="200000"){
									html += ('<div class="k-state-default operation splitButtonTwoc" ng-click="showOrderDetails(\''+e.id+'\')">查看</div>'
											+'<div class="k-state-default operation splitButtonTwoc" ng-click="orderConfirm(\''+e.id+'\',\'300000\')">平台确认</div>');
								}else if(e.shell_state=="300000"){
									html += ('<div class="k-state-default operation splitButtonTwoc" ng-click="showOrderDetails(\''+e.id+'\')">查看</div>'
											+'<div class="k-state-default operation splitButtonTwoc" ng-click="orderConfirm(\''+e.id+'\',\'400000\')">完成结算</div>');
								}else{
									html += ('<div class="k-state-default operation" ng-click="showOrderDetails(\''+e.id+'\')">查看</div>');
								}
							}else if(userInfo.user_type==2){  //企业	
								html += ('<div class="k-state-default operation" ng-click="showOrderDetails(\''+e.id+'\')">查看</div>');
							}else if(userInfo.user_type==3){  //合伙人
								html += ('<div class="k-state-default operation" ng-click="showOrderDetails(\''+e.id+'\')">查看</div>');
							}else if(userInfo.user_type==4){  //品牌
								html += ('<div class="k-state-default operation" ng-click="showOrderDetails(\''+e.id+'\')">查看</div>');
							}else if(userInfo.user_type==5){  //店铺
								if(e.shell_state=="100000"){
									html += ('<div class="k-state-default operation splitButtona" ng-click="showOrderDetails(\''+e.id+'\')">查看</div>'
											+'<div class="k-state-default operation splitButtona" ng-click="orderConfirm(\''+e.id+'\',\'200000\')">确认</div>'
											+'<div class="k-state-default operation splitButtona" ng-click="orderConfirm(\''+e.id+'\',\'600000\')">拒绝</div>');
								}else{
									html += ('<div class="k-state-default operation" ng-click="showOrderDetails(\''+e.id+'\')">查看</div>');
								}
							}
							
							return html;
						}
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//结算设置
	$scope.clearSet = function(){
		$scope.getClearSetInfo();
		$scope.clearSetWindow.center().open();   //打开弹框
	}
	
	//获取结算设置信息
	$scope.getClearSetInfo = function(){
		$http({
			   url: path+'/server/selectShellOrderInfo',
			   method: 'GET' 
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.clearSetForm = data.result;
			}else{
				alert("获取门店信息失败！");
			}
		}).error(function(data){
			alert("获取信息失败！");   
		})
	}

	//结算设置提交
	$scope.clearSetSubmit = function(){
		if(!$scope.clearSetForm.days && $scope.clearSetForm.days!=0){
			alert("请填写【结算周期】！");
			return;
		}
		if(!$scope.clearSetForm.rate && $scope.clearSetForm.rate!=0){
			alert("请填写【佣金比例】！");
			return;
		}
		
		if(publicService.checkIsUpNumber($scope.clearSetForm.days)){
			if($scope.clearSetForm.days<15){
				alert("【结算周期】最短为15天！");
				return;
			}
		}else{
			alert("【结算周期】请输入正整数！");
			return;
		}
		if($scope.clearSetForm.rate<0 || $scope.clearSetForm.rate>1){
			alert("【佣金比例】在[0~1]之间！");
			return;
		}
		
		$http({
			   url: path+'/server/setShellOrderDay',
			   method: 'POST',
			   data: angular.toJson($scope.clearSetForm)
		}).success(function(data){
			if(data.code==0){  //成功
				alert("结算设置成功！");
				$scope.clearSetWindow.close();   //关闭弹框
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}

	//订单确认
	$scope.orderConfirm = function(id,state){
		var postData = {
				shell_id: id,
				shell_state: state
		};
		
		$http({
			   url: path+'/server/updateShellRealOrdersState',
			   method: 'POST',
			   data: angular.toJson(postData)
		}).success(function(data){
			if(data.code==0){  //成功
				alert("成功！");
				$scope.grid.dataSource.page(1);
			}else{
				alert("更改失败！");
			}
		}).error(function(data){
			alert("服务器异常！");   
		})
	}
	
	//显示门店详情
	$scope.showOrderDetails = function(id){
		var params = {method:"look",orderId:id};
		$location.path("/orderClearDetails/"+angular.toJson(params));
	}
	
	
	
	
	
	publicService.initOrderClearState("clear_state");
	publicService.initDateTime("start_time");
	publicService.initDateTime("end_time");
	publicService.initEnterpriseName("enterprise_name");	//初始化【企业名称】
	publicService.initPartnerName("partner_name");			//初始化【合伙人名称】
	publicService.initBrandName("brand_name");				//初始化【品牌名称】
	publicService.initShopName("shop_name");				//初始化【店铺名称】

	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业	
		$scope.clearSet_show = false;
		$scope.enterprise_show = false;
	
	}else if(userInfo.user_type==3){  //合伙人
		$scope.clearSet_show = false;
		$scope.enterprise_show = false;
		$scope.partner_show = false;
		
	}else if(userInfo.user_type==4){  //品牌
		$scope.clearSet_show = false;
		$scope.enterprise_show = false;
		$scope.partner_show = false;
		$scope.brand_show = false;
		
	}else if(userInfo.user_type==5){  //店铺
		$scope.clearSet_show = false;
		$scope.enterprise_show = false;
		$scope.partner_show = false;
		$scope.brand_show = false;
		$scope.shop_show = false;

	}
	

});