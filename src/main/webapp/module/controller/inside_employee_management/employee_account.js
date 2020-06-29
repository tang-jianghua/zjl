App.controller("employeeAccountCtrl",function($scope, $rootScope, $location, $http, $compile,path) {
	
	
	$scope.com_update={};
	
	$scope.mainGridOptions = {
		dataSource : {
			
		transport : {
				read : {
					url : path+"/server/queryuserlist",
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
								
								user : $scope.search.user,
							account : $scope.search.account,
							phone_num: $scope.search.phone_num,
							sort:options.sort
							
							
							}
							
						//每页显示个数
							
						};
						console.log(options);
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
		sortable: {
		    mode: "multiple"
		  },
		columns : [
				{
					field : "id",
					title : "序号",
					width : "120px",
					template:"<input type='checkbox'  class='' id='#: id #' value='#: id #' />"
					
						
				},
				{
					field : "user",
					title : "姓名",
					width : "120px",
					sortable:true
				},
				{
					field : "account",
					title : "用户名",
					width : "120px"
				},
				{
					field : "phone_num",
					title : "联系方式",
					width : "120px"
				},
				{
					field : "role_name",
					title : "用户角色",
					width : "120px"
				},
				{
					field : "position_name",
					title : "岗位",
					width : "120px"
				},
				{
					field : "department_name",
					title : "部门",
					width : "120px"
				},
				{
					field : "create_time",
					title : "创建时间",
					width : "120px",
					format: "{0: yyyy-MM-dd HH:mm:ss}"
				},
				{
					field : "state",
					title : "状态",
					width : "120px",
					values: [
					         { text: "正常", value: 1 },
					         { text: "关闭", value: 2 }
					       ]
				},
				
				{
					field : "",
					title : "操作",
					width : "120px",
					//template : "<input type='button' value='编辑' class='k-button k-state-default' ng-click='edit(\"#: id #\")' /> <input type='button' value='启用' class='k-button k-state-default'/>",
					template :function(e){
						var template_html='';
						if(e.state!=1){
							template_html = "<input type='button' value='编辑' class='k-button k-state-default' ng-click='edit("+e.id+")' /> <input type='button' value='启用' ng-click='updateState("+e.id+",1)' class='k-button k-state-default'/>"
						}else{
							template_html = "<input type='button' value='编辑' class='k-button k-state-default' ng-click='edit("+e.id+")' /> <input type='button' value='禁用' ng-click='updateState("+e.id+",2)' class='k-button k-state-default'/>"
						}
			    		return template_html;
					}
				}

		]
	};
$scope.depUpdateDataSource = new kendo.data.DataSource({
	transport : {
		read : {
			url : path+"/server/querydepCombox",
			type : 'POST',
			dataType : "json",
			contentType : "application/json"
		}
	},
	requestEnd: function(e) {
	}
});
	
$scope.positionUpdateDataSource = {
			serverFiltering : true,
			transport : {
				read : {
					url : path+"/server/queryPositionCombox",
					type : 'POST',
					dataType : "json",
					contentType : "application/json"
				},
				parameterMap : function(data, type) {
					if (type == "read") {
						var parameter = {
							id : data.filter.filters[0].value, //当前页

						};
						return kendo.stringify(parameter)
					}
				}
			},
			requestEnd: function(e) {
				
				
			}
		};

$scope.roleUpdateDataSource = {
			serverFiltering : true,
			transport : {
				read : {
					url : path+"/server/rolepositioncombox",
					type : 'POST',
					dataType : "json",
					contentType : "application/json"
				},
				parameterMap : function(data, type) {
					if (type == "read") {
						console.log(data.filter.filters[0].value);
						var parameter = {
							id : data.filter.filters[0].value, //当前页

						};
						return kendo.stringify(parameter)
					}
				}
			},
	requestEnd: function(e) {
		
	
		
	}
	
};
/**
 * 查询
 */
$scope.search=function(){
	$scope.grid.dataSource.page(1);
}
	
//弹出文新增框
$scope.winOpen = function() {
		$scope.user = {};
		$scope.create_win.center().open();
		comboxloadData();
}
//关闭弹出框
$scope.cancel = function() {
	$scope.create_win.close();

}
var comboxloadData=function(){
	$scope.depDataSource = {
			transport : {
				read : {
					url : path+"/server/querydepCombox",
					type : 'POST',
					dataType : "json",
					contentType : "application/json"
				}
			}
	};
$scope.positionDataSource = {
			serverFiltering : true,
			transport : {
				read : {
					url : path+"/server/queryPositionCombox",
					type : 'POST',
					dataType : "json",
					contentType : "application/json"
				},
				parameterMap : function(data, type) {
					if (type == "read") {
						var parameter = {
							id : data.filter.filters[0].value, //当前页

						};
						return kendo.stringify(parameter)
					}
				}
			}
		};

$scope.roleDataSource = {
			serverFiltering : true,
			transport : {
				read : {
					url : path+"/server/rolepositioncombox",
					type : 'POST',
					dataType : "json",
					contentType : "application/json"
				},
				parameterMap : function(data, type) {
					if (type == "read") {
						var parameter = {
							id : data.filter.filters[0].value, //当前页

						};
						return kendo.stringify(parameter)
					}
				}
			},
		};
	
}


//确认账号生成
$scope.save = function() {
console.log($scope.user);
	$http({
		method : 'POST',
		url : path+'/server/createuser',
		data : angular.toJson($scope.user),
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {

		if (data.code == 0) {
			$scope.create_win.close();
			$scope.grid.dataSource.online(true);
			$scope.grid.dataSource.page(1);
		}
	});
	
}
/**
 * 弹出编辑框
 */
$scope.edit=function(id){
	//$scope.update_dep.value("");
	$http({
		method : 'GET',
		url : path+'/server/userbyid/'+id,
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		if (data.code == 0) {
			$scope.update_dep.value("");
			$scope.com_update_position.value("");
			$scope.update_role.value("");
				$scope.updateuser={};
				$scope.updateuser.role={};
				$scope.updateuser.user=data.result.user;
				$scope.updateuser.account=data.result.account;	
				$scope.updateuser.phone_num=data.result.phone_num;	
				$scope.update_dep.value(data.result.department_id);
				$scope.com_update_position.value(data.result.position_id);
				$scope.update_role.value(data.result.roleEntity.id);
				
				$scope.update_win.center();
				$scope.update_win.open();
				
			
				
		}
	});
	
}


$scope.update_close=function(){
	
//	alert(1);
//	$scope.updateuser={};
//	
//	console.log($scope.updateuser);
	
}


/**
 * 更新数据
 */
$scope.update=function(){
	
	$scope.updateuser.department_id=$scope.update_dep.value();
	$scope.updateuser.position_id=$scope.com_update_position.value();
	$scope.updateuser.role={};
	$scope.updateuser.role.role_id=$scope.update_role.value();
	$http({
		method : 'POST',
		url : path+'/server/modifyuser',
		data : angular.toJson($scope.updateuser),
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {

		if (data.code == 0) {
			$scope.update_win.close();
			$scope.grid.dataSource.online(true);
			$scope.grid.dataSource.page(1);
		}
	});
	
}
$scope.update_cancel=function(){
	
	$scope.update_win.close();
	
}

$scope.role_data_bound=function(){
	//alert(1);
 
}

//修改账号状态
$scope.updateState=function(id,state){
	console.log(state);
	if(state==1){
		$http({
			method : 'GET',
			url : path+'/server/openuser/'+id,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
				console.log(data);
			if (data.code == 0) {
				$scope.update_win.close();
				$scope.grid.dataSource.online(true);
				$scope.grid.dataSource.page(1);
			}
		});
	}else{
		$http({
			method : 'GET',
			url : path+'/server/closeuser/'+id,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data) {
			console.log(data);
			if (data.code == 0) {
				$scope.update_win.close();
				$scope.grid.dataSource.online(true);
				$scope.grid.dataSource.page(1);
			}
		});
	}
}

})