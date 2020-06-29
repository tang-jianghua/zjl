App.controller("promoterCtrl",function($scope, $rootScope, $location, $http, $compile, path) {
	

	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryheadline",
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
									title: $scope.search.title,	 	//姓名
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
						width : "100px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number'></span>"
					},
					{
						field : "",         
						title : "姓名",     
						width : "100px"
					},
					{
						field : "",         
						title : "用户名",     
						width : "100px"
					},
					{
						field : "",         
						title : "密码",     
						width : "100px"
					},
					{
						field : "",         
						title : "联系方式",     
						width : "100px"
					},
					{
						field : "",         
						title : "所属合伙人",     
						width : "100px"
					},
					{
						field : "",         
						title : "推广卖家端数量",     
						width : "100px"
					},
					{
						field : "",         
						title : "创建时间",     
						width : "100px"
					},
					{
						field : "",         
						title : "账号状态",     
						width : "100px",
						values: [
						         { text: "正常", value: 1 },
						         { text: "关闭", value: 2 }
						       ]
					},
					{    
						title : "操作",
						field : "", 
						width : "100px",
					    template:'<button class="k-button k-state-default splitButtonLeft" ng-click="edit()">编辑</button>'
					    	    +'<button class="k-button k-state-default splitButtonRight" ng-click="edit()">删除</button>'
			            
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//导出
	$scope.dataExport=function(){
		alert('导出！');
	}
	
	//添加账号
	$scope.addAccount = function(){
		$("#method").val("add");
		$scope.form = {};
		$scope.addAccountWindow.center().open();   //打开弹框
	}

	
	

});