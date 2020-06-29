App.controller("enterpriseInformationCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	$scope.mainGridOptions = {
		dataSource : {
				transport : {
					read : {
						url : path+"/server/queryenterprise",
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
									user_name: $scope.search.user_name,		   			//姓名
									account: $scope.search.account,		 				//用户名			
									phone_num: $scope.search.phone_num,		 			//联系方式
									class_id: $("#buildClass").data("kendoComboBox").value(),	//生产品类
									sort:sort
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
		    mode: "single"     //排序模式：single，multiple
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
					width : "60px",
					template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle'></span>"
				},
				{         
					title : "企业名称",    
					field : "enterprise_name",
					width : "180px"
				},
				{    
					title : "后台账号",   
					field : "account",   
					width : "100px"    
				},
				{    
					title : "负责人",   
					field : "principal",   
					width : "60px"    
				},
				{    
					title : "联系方式",   
					field : "phone_num",   
					width : "100px"
				},
				{    
					title : "生产品类",   
					field : "class_name",   
					width : "90px"    
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
					width : "60px",
					values: [
					         { text: "正常", value: 1 },
					         { text: "关闭", value: 2 }
					       ]
				},
				{    
					title : "操作",
					field : "", 
					width : "80px",
				    template:function(e){
		            	if(e.state==1){  //开启
		            		var template_html = '<div class="operation k-state-default splitButtonTwoc" ng-click="edit(\''+e.id+'\')">编辑</div>'
							    +'<div class="operation k-state-default splitButtonTwoc" ng-click="disable(\''+e.id+'\')">禁用</div>';
		            		return template_html;
		            	}else if(e.state==2){  //关闭
		            		var template_html = '<div class="operation k-state-default splitButtonTwoc" ng-click="enable(\''+e.id+'\')">编辑</div>'
							    +'<div class="operation k-state-default splitButtonTwoc" ng-click="enable(\''+e.id+'\')">启用</div>';
		            		return template_html;
		            	}
		            }
				}
				
		]
	};
		                        	
	//查询
	$scope.search=function(){
		$scope.grid.dataSource.page(1);
	}
	
	//启用
	$scope.enable=function(id){
		$http({
			   url: path+'/server/openenterprise/'+id,
			   method: 'GET' 
		}).success(function(data){
			alert(data.message);
			$scope.search();
		}).error(function(data){
			alert(data);   
		})
	}
	
	//禁用
	$scope.disable=function(id){
		if(confirm("您确定禁用该账号吗?")){
			$http({
				   url: path+'/server/closeenterprise/'+id,
				   method: 'GET' 
			}).success(function(data){
				alert(data.message);
				$scope.search();
			}).error(function(data){
				alert(data);   
			})
		}
	}

	//添加账号信息
	$scope.addAccountInformation=function(){
		var params = {method:"add"};
		$location.path("/addEnterpriseInformation/"+angular.toJson(params));
	}
	
	//编辑
	$scope.edit=function(id){
		var params = {method:"edit",enterpriseId:id};
		$location.path("/addEnterpriseInformation/"+angular.toJson(params));
	}
	
	
	publicService.initAllClass("buildClass");	//生产品类
	
	//导出
	$scope.dataExport=function(){
		var sort = [{dir:"desc",field:"create_time"}];
		var param={
				user_name: $scope.search.user_name,		   			//姓名
				account: $scope.search.account,		 				//用户名			
				phone_num: $scope.search.phone_num,		 			//联系方式
				class_id: $("#buildClass").data("kendoComboBox").value(),	//生产品类
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "enterprise_module");  
	    
	    var my_input_page = $('<input type="text" name="page" />');  
	    /*{page:1,pageSize:10,ispage:false,"param":param}*/
	    my_input_page.attr('value', angular.toJson({page:1,pageSize:10,ispage:false,"param":param}));  
	    // 附加到Form  
	    form.append(my_input);  
	    form.append(my_input_page);  
	    console.log(form.serializeArray());
	    form.submit(); 
	}
	//选中导出
	$scope.dataCheckboxExport=function(){
		var grid = $("#gridInformation").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"企业列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridInformation").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}
});