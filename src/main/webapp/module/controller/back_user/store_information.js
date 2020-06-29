App.controller("storeInformationCtrl",function($scope, $rootScope, $location, $http, $compile, $state, path, publicService) {
	
	$scope.enterprise_show = true;
	$scope.partner_show = true;
	$scope.brand_show = true;
	
	//初始化店铺状态
	$scope.initShopState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: "0",
			 				text: "下架"
			             },
			             {
			            	 id: 1,
			 				text: "上架"
			             }
            ]
		});
	}
	
	$scope.mainGridOptions = {
		dataSource : {
				transport : {
					read : {
						url : path+"/server/queryshop",
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
									user_name: $scope.search.user_name,		   			//负责人
									account: $scope.search.account,		 				//后台账号		
									phone_num: $scope.search.phone_num,		 			//联系方式
									enterprise_name: $scope.search.enterprise_name,		 	//企业名称		
									partner_principal: $scope.search.partner_principal,		//合伙人
									brand_name: $scope.search.brand_name,		   			//品牌名称
									shop_name: $scope.search.shop_name,		   			//店铺名称
									shop_state: $("#shopState").data("kendoDropDownList").value(),	//店铺状态
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
					width : "60px",
					template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle'></span>"
				},
				{         
					title : "企业名称",    
					field : "enterprise_name",
					width : "100px"
				},
				{         
					title : "合伙人",    
					field : "partner_name",
					width : "60px"
				},
				{         
					title : "品牌名称",    
					field : "brand_name",
					width : "100px"
				},
				{         
					title : "店铺名称",    
					field : "shop_name",
					width : "100px"
				},
				{    
					title : "后台账号",   
					field : "account",   
					width : "110px"    
				},
				{    
					title : "负责人",   
					field : "principal",   
					width : "60px"    
				},
				{    
					title : "联系方式",   
					field : "phone_num",   
					width : "90px"
				},
				{    
					title : "创建时间",   
					field : "create_time",   
					width : "100px"
				},{    
					title : "修改时间",   
					field : "update_time",   
					width : "100px"
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
					title : "店铺状态",   
					field : "shop_state",   
					width : "80px",
					values: [
					         { text: "下架", value: 0 },
					         { text: "上架", value: 1 }
					       ]
				},
				{    
					title : "操作",
					field : "", 
					width : "120px",
				    template:function(e){
				    	var template_html = '';
				    	if(userInfo.user_type==1){  //开发者
				    		return '<div class="operation k-state-default" ng-click="lookDetails(\''+e.id+'\')">查看</div>';
				    	}else if(userInfo.user_type==2){  //企业
				    		return '<div class="operation k-state-default" ng-click="lookDetails(\''+e.id+'\')">查看</div>';
				    	}else if(userInfo.user_type==3){  //合伙人
				    		return '<div class="operation k-state-default" ng-click="lookDetails(\''+e.id+'\')">查看</div>';
				    	}else if(userInfo.user_type==4){  //品牌
				    		template_html += ('<div class="operation k-state-default splitButtona" ng-click="edit(\''+e.id+'\')">编辑</div>');
				    		
				    		if(e.state==1){  //开启
				    			template_html += ('<div class="operation k-state-default splitButtona" ng-click="disable(\''+e.id+'\')">禁用</div>');
				    		}else if(e.state==2){  //关闭
				    			template_html += ('<div class="operation k-state-default splitButtona" ng-click="enable(\''+e.id+'\')">启用</div>');
				    		}
				    		
				    		if(e.shop_state==0){	//下架
				    			template_html += ('<div class="operation k-state-default splitButtona" ng-click="modifyShopState(\''+e.id+'\',1)">上架</div>');
				    		}else if(e.shop_state==1){	//上架
				    			template_html += ('<div class="operation k-state-default splitButtona" ng-click="modifyShopState(\''+e.id+'\',0)">下架</div>');
				    		}
				    		
				    	}else if(userInfo.user_type==5){  //店铺
				    		
				    	}
				    	
				    	return template_html;
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
			   url: path+'/server/openshop/'+id,
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
				   url: path+'/server/closeshop/'+id,
				   method: 'GET' 
			}).success(function(data){
				alert(data.message);
				$scope.search();
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//禁用
	$scope.modifyShopState = function(id,state){
		var postData = {
				id: id,
				state: state
		};
		
		$http({
			   url: path+'/server/modifyShopState',
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				$scope.grid.dataSource.page(1);
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	
	
	//添加账号信息
	$scope.addAccountInformation=function(){
		var params = {method:"add"};
		$location.path("/addStoreInformation/"+angular.toJson(params));
	}
	
	//编辑
	$scope.edit=function(id){
		var params = {method:"edit",storeId:id};
		$location.path("/addStoreInformation/"+angular.toJson(params));
	}
	
	//查看详情
	$scope.lookDetails=function(id){
		var params = {method:"look",storeId:id};
		//$location.path("/addStoreInformation/"+angular.toJson(params));
		$state.go('addStoreInformation', {params:angular.toJson(params)});
	}
	
	/*页面初始化*/
	$scope.initShopState("shopState");	//初始化店铺状态
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		$("#addAccount").hide();
	}else if(userInfo.user_type==2){  //企业
		$("#addAccount").hide();
		
		$scope.enterprise_show = false;
	}else if(userInfo.user_type==3){  //合伙人
		$("#addAccount").hide();
		
		$scope.enterprise_show = false;
		$scope.partner_show = false;
	}else if(userInfo.user_type==4){  //品牌
		$scope.enterprise_show = false;
		$scope.partner_show = false;
		$scope.brand_show = false;
	}else if(userInfo.user_type==5){  //店铺
		
	}

	//导出
	$scope.dataExport=function(){
		var sort = [{dir:"desc",field:"create_time"}];
		var param={
				user_name: $scope.search.user_name,		   			//负责人
				account: $scope.search.account,		 				//后台账号		
				phone_num: $scope.search.phone_num,		 			//联系方式
				enterprise_name: $scope.search.enterprise_name,		 	//企业名称		
				partner_principal: $scope.search.partner_principal,		//合伙人
				brand_name: $scope.search.brand_name,		   			//品牌名称
				shop_name: $scope.search.shop_name,		   			//店铺名称
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "shop_module");  
	    
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
		var grid = $("#gridStore").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"店铺列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridStore").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}
});