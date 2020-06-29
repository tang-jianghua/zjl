App.controller("servicePersonCtrl",function($scope, $rootScope, $location, $http, $compile, path) {
	
	$scope.form = {};   //账户添加表单

	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryemployeepage",
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
									name: $scope.search.userName,	 		//姓名
									phone_num: $scope.search.phone_num,	 	//联系方式
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
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle'></span>"
					},
					{
						field : "name",         
						title : "姓名",     
						width : "100px"    
					},
					{
						field : "phone_num",         
						title : "联系方式",     
						width : "120px"    
					},
					{
						field : "create_time",         
						title : "创建时间",     
						width : "120px"    
					},
					{
						field : "update_time",         
						title : "更新时间",     
						width : "120px"    
					},
					{
						field : "id",         
						title : "操作",     
						width : "60px",
						template : "<div  value='编辑' class='operation k-state-default splitButtonTwoc' ng-click='editData(\"#: id #\")' />编辑</div>"
								  +"<div  value='删除' class='operation k-state-default splitButtonTwoc' ng-click='deleteData(\"#: id #\")' />删除</div>"
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//导出
	$scope.dataExport = function(){
		alert("导出");
	}
	
	//添加账号
	$scope.addAccount = function(){
		$("#method").val("add");
		$scope.form = {};
		$scope.addAccountWindow.center().open();   //打开弹框
	}
	
	//添加新的账号
	$scope.addNewAccount = function(){
		//参数验证
		if(!$scope.form.name){
			alert("请填写【姓名】！");
			return;
		}
		if(!$scope.form.phone_num){
			alert("请填写【联系方式】！");
			return;
		}

		var method = $("#method").val();
		
		var url = '';
		if(method=="add"){
			url = path+'/server/createemployee';
		}else if(method=="edit"){
			url = path+'/server/modifyemployee';
			$scope.form.id = $("#dataId").val();
		}
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.form)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.addAccountWindow.close();   //关闭
				$scope.grid.dataSource.page(1);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//编辑
	$scope.editData = function(id){
		$http({
			   url: path+'/server/getemployeebyid/'+id,
			   method: 'GET'
		}).success(function(data){
			var peopleData = data.result;
			
			$scope.form.name = peopleData.name;            //姓名
			$scope.form.phone_num = peopleData.phone_num;  //联系方式
			$("#dataId").val(peopleData.id);
			$("#method").val("edit");
			$scope.addAccountWindow.center().open();   //打开弹框
		}).error(function(data){
			alert(data);   
		})
	}
	
	//删除
	$scope.deleteData = function(id){
		if(confirm("您确定删除该账号吗?")){
			$http({
				   url: path+'/server/removeemployee/'+id,
				   method: 'GET'
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.search();
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert("删除失败！");   
			})
		}
	}
	

	
	

});