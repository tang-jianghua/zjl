App.controller("openCityCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	var cityId = '';	//城市id
	
	$scope.mainGridOptions = {
			dataSource : {
					transport : {
						read : {
							url : path+"/server/querypartner",
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
										//check_state: "1",  //审核状态
										open_city: "100",  //开通城市							
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
						width : "80px",
						template:"<span class='row-number subTitle'></span>"
					},
					{         
						title : "企业名称",    
						field : "enterprise_name",
						width : "100px"
					},
					{    
						title : "合伙人",   
						field : "principal",   
						width : "100px"    
					},
					{    
						title : "后台账号",   
						field : "account",   
						width : "100px"    
					},
					{    
						title : "联系方式",   
						field : "phone_num",   
						width : "100px"
					},
					{    
						title : "管辖区域",   
						field : "region_name",   
						width : "100px"    
					},
					{    
						title : "创建时间",   
						field : "create_time",   
						width : "100px",
						format: "{0: yyyy-MM-dd HH:mm:ss}"
					},{    
						title : "修改时间",   
						field : "update_time",   
						width : "100px",
						format: "{0: yyyy-MM-dd HH:mm:ss}"
					},
					{    
						title : "账号状态",   
						field : "state",   
						width : "80px",
						values: [
						         { text: "正常", value: 1 },
						         { text: "关闭", value: 2 }
						       ]
					},
					{    
						title : "开通状态",   
						field : "check_state",   
						width : "80px",
						template: function(e){
							var html = '';
							
							if(e.check_state==1){	//未开通
								html = '<div >未开通</div>';
							}else if(e.check_state==2){	//已开通
								html = '<div style="color: green;font-weight: bold;">已开通</div>';
							}
							
							return html;
						}
					},
					{    
						title : "操作",
						field : "", 
						width : "100px",
						template: function(e){
							var html = '';
							
							if(e.check_state==1){	//未开通
								html = '<div class="operation k-state-default" ng-click="openCity(\''+e.id+'\')">开通</div>';
							}else if(e.check_state==2){	//已开通
								html = '<div class="operation k-state-default splitButtonTwoc" ng-click="close(\''+e.id+'\')">关闭</div>'
									  +'<div class="operation k-state-default splitButtonTwoc" ng-click="forceClose(\''+e.id+'\')">强制关闭</div>';
							}
							
							return html;
						}
					}
			]
		};
	
	//开通城市
	$scope.close = function(id){
		cityId = id;
		$("#closeTime").val(publicService.getCurrentTime("date"));
		
		$scope.closeCityWindow.center().open();   //打开弹框
	}
	
	//开通城市
	$scope.openCity = function(id){
		if(confirm("您确定开通该城市吗？")){
			$http({
				   url: path+'/server/addopencity/'+id,
				   method: 'GET'
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$scope.closeCityWindow.close();
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert("开通该城市失败！");  
			})
		}
	}
	
	//关闭城市
	$scope.closeCity = function(){
		if(confirm("您确定关闭该城市吗？")){
			var postData = {
					id: cityId,
					close_time: $("#closeTime").val()
					};
			
			$http({
				   url: path+'/server/closecity',
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$scope.closeCityWindow.close();
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert("关闭该城市失败！");  
			})
		}
	}
	
	//强制关闭
	$scope.forceClose = function(id){
		if(confirm("您确定强制关闭该城市吗？")){
			$http({
				   url: path+'/server/closecity/'+id,
				   method: 'GET'
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert("强制关闭该城市失败！");   
			})
		}
	}
	

	
	publicService.initDate("closeTime");

});