App.controller("pageManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {

	//初始化卡券类型
	$scope.initCardType = function(){
		$("#cardType").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "红包"
			             },
			             {
			            	 id: 2,
			 				text: "优惠券"
			             }
            ]
		});
	}
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryCustomerCoupons",
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
									name: $scope.search.cardName,	 				//卡券名称
									convert_code: $scope.search.convert_code,	//兑换码
									type: $("#cardType").data("kendoDropDownList").value(),	 //兑换类型
									customer_name: $scope.search.customer_name,	 		     //用户昵称

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
						field : "imgurl",         
						title : "卡券图",     
						width : "150px",
						template :function(e){
							var html = '';
							
							html = '<img class="productImage" src="'+e.imgurl+'"/>';
							
							return html;
						}
					},
					{
						field : "name",         
						title : "卡券名称",     
						width : "100px"
					},
					{
						field : "value",         
						title : "金额",     
						width : "100px"
					},
					{
						field : "use_time",         
						title : "兑换时间",     
						width : "100px"
					},
					{
						field : "convert_code",         
						title : "兑换码",     
						width : "100px"
					},
					{
						field : "type",         
						title : "兑换类型",     
						width : "100px",
						values: [
						         {value:1, text:"红包"},
						         {value:2, text:"优惠卷"}
				        ]
					},
					{
						field : "customer_name",         
						title : "用户昵称",     
						width : "100px"
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}

	//兑换
	$scope.exchange = function(){
		if(!$scope.code){
			alert("请填写兑换码！");
			return;
		}

		$http({
			   url: path+'/server/queryCustomerCouponsByCode/'+$scope.code,
			   method: 'GET'  
		}).success(function(data){
			if(data.code==0){  //成功
				if(confirm("确认兑换？")){
					$http({
						   url: path+'/server/modifyCustomerCouponsById/'+data.result,
						   method: 'GET'  
					}).success(function(data){
						if(data.code==0){  //成功
							$scope.grid.dataSource.page(1);
							alert("兑换成功");
						}else if(data.code==1){  //失败
							alert("兑换失败！");
						}
					}).error(function(data){
						alert("兑换失败！");   
					})
				}
			}else if(data.code==1){  //失败
				alert("兑换码无效！");
			}
		}).error(function(data){
			alert("系统异常！");   
		})
	}
	
	
	$scope.initCardType();			//初始化卡券类型
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业	
	
	}else if(userInfo.user_type==3){  //合伙人
		
	}else if(userInfo.user_type==4){  //品牌
		
	}else if(userInfo.user_type==5){  //店铺

	}
	

});