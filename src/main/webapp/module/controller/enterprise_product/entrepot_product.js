App.controller("entrepotProductCtrl1",function($scope, $rootScope, $location, $http, $compile) {
	
$scope.mainGridOptions = {
	dataSource : {
			transport : {
				read : {
					url : "server/brand/wallpaperlist",
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
								user_name: $scope.search.user_name,		   			//姓名
								account: $scope.search.account,		 				//用户名			
								phone_num: $scope.search.phone_num,		 			//联系方式
								enterprise_name: $scope.search.enterprise_name,	 	//企业
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
	columns : [
			{    
				title : "<input type='checkbox' />全选",
				field : "brand_id", 
				width : "100px",
				template:"<input type='checkbox' id='#: id #' value='#: id #' />"
			},
			{         
				title : "型号",    
				field : "model",
				width : "100px"    
			},
			{    
				title : "缩略图",   
				field : "region_name",   
				width : "100px"    
			},
			{    
				title : "产品规格",   
				field : "product_name",   
				width : "100px"    
			},
			{    
				title : "所属城市",   
				field : "update_time",   
				width : "100px",
				format: "{0: yyyy-MM-dd HH:mm:ss}"
			},
			{    
				title : "品牌",   
				field : "update_time",   
				width : "100px",
				format: "{0: yyyy-MM-dd HH:mm:ss}"
			},
			{    
				title : "系列",   
				field : "update_time",   
				width : "100px",
				format: "{0: yyyy-MM-dd HH:mm:ss}"
			},
			{    
				title : "出厂价",   
				field : "update_time",   
				width : "100px",
				format: "{0: yyyy-MM-dd HH:mm:ss}"
			},
			{ title : "操作",
				command: [
		            
		           { name: "details",
		              text:'查看详情',
		              /*click: function(e) {
		            	  var tr = $(e.target).closest("tr"); 
		                  var data = this.dataItem(tr);
		                  console.log("Details for: " + data.phone_num);
		                return;
		              }*/
		              /*click:function(){
		            		 $location.path("/addBrandProduct");
		              }*/
		            }    
			   ]
			}
			
	]
};
	                        	
//搜索
/*$scope.search=function(){
	$scope.grid.dataSource.online(true);
	$scope.grid.dataSource.read();
}*/

//导出
$scope.dataExport=function(){
	alert('导出！');
}

/*$scope.productInformation=function(){
	$location.path("/addBrandProduct");
}*/

});