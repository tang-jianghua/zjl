App.controller("deviceManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {

	var ACCESS_TOKEN = 'Yck4K2XVWOo9MK31wjGiMtdZXCkQwmjq_c8h7QkfEho2qzyBXZ2gMw96FxGqgFsfgvHcV6dBpa3GorlT7dUZUCT2jno2ATkj90UWvsRQPBdVCdixVJwxuXh_KyCQGBEDZEOaAIAHMW';
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : 'https://api.weixin.qq.com/shakearound/device/search?access_token='+ACCESS_TOKEN,
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var parameter = {
									type: 2,
									begin: 0,
									count: 10
									
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

	
	
	
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业	
	
	}else if(userInfo.user_type==3){  //合伙人
		
	}else if(userInfo.user_type==4){  //品牌
		
	}else if(userInfo.user_type==5){  //店铺

	}
	

});