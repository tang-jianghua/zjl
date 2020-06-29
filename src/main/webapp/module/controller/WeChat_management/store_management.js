App.controller("storeManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {

	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/app/weixin/selectWeixinStore", 
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
									business_name: $scope.search.shop_name,		//店铺名称
									business_name: $scope.search.business_name,		//门店名称
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
						field : "",         
						title : "店铺名称",     
						width : "100px"
					},
					{
						field : "business_name",         
						title : "门店名称",     
						width : "100px"
					},
					{
						field : "categories",         
						title : "门店类型",     
						width : "100px"
					},
					{
						field : "address",         
						title : "所在地区",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += ('<div>'+e.province+'-'+e.city+'-'+e.district+'</div>'
									+'<div>'+e.address+'</div>');
							
							return html;
						}
					},
					{
						field : "telephone",         
						title : "联系方式",     
						width : "100px"
					},
					{
						field : "available_state",         
						title : "状态",     
						width : "100px",
						values: [
					         { text: "系统错误", value: 1 },
					         { text: "审核中", value: 2 },
					         { text: "审核通过", value: 3 },
					         { text: "审核驳回", value: 4 }
					    ]
					},
					{
						field : "sid",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += ('<div class="k-state-default operation" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
							
							return html;
						}

					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//新增门店
	$scope.addStore = function(){
		var params = {method:"add"};
		$location.path("/addStore/"+angular.toJson(params));
	}

	//显示详情
	$scope.showDetails = function(id){
		$http({
			   url: path+'/server/getnewbrandproductbrannerbyid/'+id,
			   method: 'GET' 
		}).success(function(data){
			if(data.code==0 || data.code==undefined){  //成功
				
			}else{
				alert("获取门店信息失败！");
			}
		}).error(function(data){
			alert("获取门店信息失败！");   
		})
	}
	
	//显示门店详情
	$scope.showStoreDetails = function(details){
		$scope.details = {};
		
		$scope.details.name = details.name;			//专辑名称

		$scope.storeDetailsWindow.center().open();   //打开弹框
	}
	
	
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业	
	
	}else if(userInfo.user_type==3){  //合伙人
		
	}else if(userInfo.user_type==4){  //品牌
		
	}else if(userInfo.user_type==5){  //店铺

	}
	

});