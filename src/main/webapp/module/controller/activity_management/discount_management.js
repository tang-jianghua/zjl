App.controller("discountManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	

	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryZhekouPromotionForPage",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"desc",field:"create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								sort=options.sort;
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									type: $("#discountType").data("kendoDropDownList").value(),	 	//折扣类型
									state_t: $("#discountState").data("kendoDropDownList").value(),	//折扣状态
									start_time: $("#startTime").val(),    //折扣日期（始）
									end_time: $("#endTime").val(),    	  //折扣日期（止）
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
						field : "type",         
						title : "折扣类型",     
						width : "100px",
						values: [
						         {value:1, text:"统一折扣"}
						]
					},
					{
						field : "create_time",         
						title : "创建日期",     
						width : "100px"
					},
					{
						field : "start_time",         
						title : "开始日期",     
						width : "100px"
					},
					{
						field : "end_time",         
						title : "结束日期",     
						width : "100px"
					},
					{
						field : "state",         
						title : "上下架",     
						width : "100px",
						values: [
						         {value:0, text:"下架"},
						         {value:1, text:"上架"}
						]
					},
					{
						field : "state_t",         
						title : "折扣状态",     
						width : "80px",
						values: [
						         {value:0, text:"未开始"},
						         {value:1, text:"进行中"},
						         {value:2, text:"已结束"}
						]
					},
					{
						field : "id",         
						title : "操作",     
						width : "150px",
						template:function(e){
							var html = '';
							
							if(e.state_t==0){
								if(e.state==0){
									html += ('<div class="k-state-default canOperate splitButtona" ng-click="upOrDown(\''+e.id+'\',1)">上架</div>'
											+'<div class="k-state-default canOperate splitButtona" ng-click="modifyDiscount(\''+e.id+'\')">编辑</div>'
											+'<div class="k-state-default canOperate splitButtona" ng-click="productSet(\''+e.id+'\')">产品设置</div>');
								}else if(e.state==1){
									html += ('<div class="k-state-default canOperate splitButtona" ng-click="upOrDown(\''+e.id+'\',0)">下架</div>'
											+'<div class="k-state-default canOperate splitButtona" ng-click="modifyDiscount(\''+e.id+'\')">编辑</div>'
											+'<div class="k-state-default canOperate splitButtona" ng-click="productSet(\''+e.id+'\')">产品设置</div>');
								}
							}else if(e.state_t==1){
								if(e.state==0){
									html += ('<div class="k-state-default canOperate splitButtonTwoc" ng-click="upOrDown(\''+e.id+'\',1)">上架</div>'
											+'<div class="k-state-default canOperate splitButtonTwoc" ng-click="productManagement(\''+e.id+'\')">产品管理</div>');
								}else if(e.state==1){
									html += ('<div class="k-state-default canOperate splitButtonTwoc" ng-click="upOrDown(\''+e.id+'\',0)">下架</div>'
											+'<div class="k-state-default canOperate splitButtonTwoc" ng-click="productManagement(\''+e.id+'\')">产品管理</div>');
								}
							}else if(e.state_t==2){
								html += ('<div class="k-state-default canOperate" ng-click="productManagement(\''+e.id+'\')">产品管理</div>');
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
	
	//添加折扣
	$scope.addDiscount = function(){
		var params = {method:"add"};
		$location.path("/discountSet/"+angular.toJson(params));
	}
	
	//上下架
	$scope.upOrDown = function(id,state){
		if(confirm("确认更改？")){
			var postData = {
					id: id,
					state: state
			};
			
			$http({
				   url: path+'/server/modifyZhekouPromotion',
				   method: 'POST',   
				   data: angular.toJson(postData),
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert("系统异常！");   
			})
		}
	}
	
	//编辑
	$scope.modifyDiscount = function(id){
		var params = {method:"edit",discountId:id};
		$location.path("/discountSet/"+angular.toJson(params));
	}
	
	//产品设置
	$scope.productSet = function(id){
		var params = {method:"productSet",discountId:id};
		$location.path("/discountSet/"+angular.toJson(params));
	}
	
	//产品管理
	$scope.productManagement = function(id){
		var params = {method:"productManagement",discountId:id};
		$location.path("/discountSet/"+angular.toJson(params));
	}
	
	


	publicService.initDiscountType("discountType");		//折扣类型
	publicService.initDiscountState("discountState");	//折扣状态
	publicService.initDateTime("startTime");			//折扣日期（始）
	publicService.initDateTime("endTime");				//折扣日期（止）
	

});