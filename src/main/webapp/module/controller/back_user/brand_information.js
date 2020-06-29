App.controller("brandInformationCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	$scope.search = {};	
	$scope.search2 = {};	
	var chooseBrandId = null;	//选择的品牌id
	$scope.enterprise_show = true;
	$scope.partner_show = true;
	
	
	//选择面板
	$scope.choosePannel = function(type){
		for(var i=1;i<=2;i++){
			if(i==type){
				$("#pannel_"+i).addClass("choose");
				$("#container_"+i).show();
			}else{
				$("#pannel_"+i).removeClass("choose");
				$("#container_"+i).hide();
			}
		}
	}
	
	//初始化账号状态
	$scope.initAccountState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "正常"
			             },
			             {
			            	 id: 2,
			 				text: "关闭"
			             }
            ]
		});
	}
	
	//初始化品牌状态
	$scope.initBrandState = function(containerId){
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
			             },
			             {
			            	 id: 2,
			 				text: "审核中"
			             }
            ]
		});
	}
	
	//品牌信息列表
	$scope.mainGridOptions = {
		dataSource : {
				transport : {
					read : {
						url : path+"/server/querybrand",
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
									enterprise_name: $scope.search.enterprise_name,		 	//企业名称		
									parter_principal: $scope.search.parter_principal,		//合伙人
									brand_name: $scope.search.brand_name,		   			//品牌名称
									account: $scope.search.account,		 				//后台账号
									user_name: $scope.search.user_name,		   			//负责人
									phone_num: $scope.search.phone_num,		 			//联系方式
									user_state: $("#accountState").data("kendoDropDownList").value(),	//账号状态
									brand_state: $("#brandState").data("kendoDropDownList").value(),	//品牌状态
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
					width : "90px"
				},
				{         
					title : "合伙人",    
					field : "partner_principal",
					width : "80px"
				},
				{         
					title : "品牌名称",    
					field : "brand_name",
					width : "100px"
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
					title : "服务区域",   
					field : "region_name",   
					width : "140px"    
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
					title : "品牌类型",   
					field : "brand_idintify_type",   
					width : "80px",
					values: [
					         { text: "线下实体品牌", value: 0 },
					         { text: "线上品牌", value: 1 }
					       ]
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
					title : "品牌状态",   
					field : "brand_state",   
					width : "80px",
					values: [
					         { text: "下架", value: 0 },
					         { text: "上架", value: 1 },
					         { text: "审核中", value: 2 }
					       ]
				},
				{    
					title : "操作",
					field : "", 
					width : "150px",
				    template:function(e){
				    	var template_html = '';
				    	
				    	if(userInfo.user_type==1){  //开发者
				    		template_html += '<div class="operation k-state-default" ng-click="lookDetails(\''+e.id+'\')">查看</div>';
				    	}else if(userInfo.user_type==2){  //企业
				    		template_html += '<div class="operation k-state-default" ng-click="lookDetails(\''+e.id+'\')">查看</div>';
				    	}else if(userInfo.user_type==3){  //合伙人
				    		template_html += ('<div class="operation k-state-default splitButtona" ng-click="edit(\''+e.id+'\')">编辑</div>');
				    		
				    		if(e.state==1){  //开启
				    			template_html += ('<div class="operation k-state-default splitButtona" ng-click="disable(\''+e.id+'\')">禁用</div>');
				    		}else if(e.state==2){  //关闭
				    			template_html += ('<div class="operation k-state-default splitButtona" ng-click="enable(\''+e.id+'\')">启用</div>');
				    		}
				    		
				    		if(e.brand_state==0){	//下架
				    			template_html += ('<div class="operation k-state-default splitButtona" ng-click="openBrandPutUpWindow(\''+e.id+'\')">开通</div>');
				    		}else if(e.brand_state==1){	//上架
				    			template_html += ('<div class="operation k-state-default splitButtona" ng-click="openBrandApplyPutDownWindow(\''+e.id+'\')">申请下架</div>');
				    		}else if(e.brand_state==2){	//审核中
				    			
				    		}
				    	}else if(userInfo.user_type==4){  //品牌
				    		
				    	}else if(userInfo.user_type==5){  //店铺
				    		
				    	}
				    	
				    	return template_html;
		            }
				}
		]
	};
	
	//下架申请列表
	$scope.applyPutDownGridOptions = {
		dataSource : {
				transport : {
					read : {
						url : path+"/server/getApplyOffStateBrands",
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
									partner: $scope.search2.partner,		//合伙人
									brand_name: $scope.search2.brand_name,	//品牌名称
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
					title : "城市合伙人",    
					field : "principal",
					width : "100px"
				},
				{         
					title : "品牌名称",    
					field : "brand_name",
					width : "100px"
				},
				{         
					title : "品类",    
					field : "name",
					width : "100px"
				},
				{         
					title : "申请时间",    
					field : "apply_time",
					width : "100px"
				},
				{         
					title : "下架原因",    
					field : "reason",
					width : "200px"
				},
				{    
					title : "操作",
					field : "brand_id", 
					width : "150px",
				    template:function(e){
				    	var template_html = '';
				    	
				    	template_html += ('<div class="operation k-state-default" ng-click="putDownBrand(\''+e.id+'\',\''+e.brand_id+'\')">下架</div>');
				    	
				    	return template_html;
		            }
				}
		]
	};
		                        	
	//查询
	$scope.search=function(type){
		if(type==1){
			$scope.grid1.dataSource.page(1);
		}else if(type==2){
			$scope.grid2.dataSource.page(1);
		}
	}
	
	//启用
	$scope.enable=function(id){
		if(confirm("您确定开启该账号吗?")){
			$http({
				   url: path+'/server/openbrand/'+id,
				   method: 'GET' 
			}).success(function(data){
				alert(data.message);
				$scope.search(1);
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//禁用
	$scope.disable=function(id){
		if(confirm("您确定禁用该账号吗?")){
			$http({
				   url: path+'/server/closebrand/'+id,
				   method: 'GET' 
			}).success(function(data){
				alert(data.message);
				$scope.search(1);
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//打开品牌上架窗口
	$scope.openBrandPutUpWindow = function(id){
		chooseBrandId = id;
		$scope.openBrandWindow.center().open();   //打开弹框
	}
	
	//开通品牌
	$scope.putUpBrand = function(){
		if(confirm("您确定开通该品牌吗?")){
			$http({
				   url: path+'/server/putBrandOnShelf',
				   method: 'POST',
				   data: angular.toJson({id: chooseBrandId}), 
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$scope.openBrandWindow.close();
					$scope.search(1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//打开品牌申请下架窗口
	$scope.openBrandApplyPutDownWindow = function(id){
		chooseBrandId = id;
		$scope.reason = "";
		$scope.applyPutDownWindow.center().open();   //打开弹框
	}

	//申请品牌下架
	$scope.applyBrandPutDown = function(){
		if(!$scope.reason){
			alert("请填写下架理由！");
			return;
		}
		
		var postData = {
				brand_id: chooseBrandId,
				reason: $scope.reason
		};
		
		$http({
			   url: path+'/server/applyBrandOffShelf',
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				$scope.applyPutDownWindow.close();
				$scope.search(1);
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//下架品牌
	$scope.putDownBrand = function(id,brand_id){
		if(confirm("您确定下架该品牌吗?")){
			var postData = {
					id: id,
					brand_id: brand_id,
					state: 0
			};
			
			$http({
				   url: path+'/server/putBrandOffShelf',
				   method: 'POST',
				   data: angular.toJson(postData), 
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$scope.search(2);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//添加账号信息
	$scope.addAccountInformation=function(){
		var params = {method:"add"};
		$location.path("/addBrandInformation/"+angular.toJson(params));
	}
	
	//编辑
	$scope.edit=function(id){
		var params = {method:"edit",brandId:id};
		$location.path("/addBrandInformation/"+angular.toJson(params));
	}
	
	//查看详情
	$scope.lookDetails=function(id){
		var params = {method:"look",brandId:id};
		$location.path("/addBrandInformation/"+angular.toJson(params));
	}
	
	
	/*页面初始化*/
	$scope.initAccountState("accountState");	//初始化账号状态
	$scope.initBrandState("brandState");		//初始化品牌状态
	$scope.choosePannel(1);
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		$("#tabContainer").show();
		$("#addAccount").hide();
	}else if(userInfo.user_type==2){  //企业
		$("#addAccount").hide();
		
		$scope.enterprise_show = false;
	}else if(userInfo.user_type==3){  //合伙人
		$scope.enterprise_show = false;
		$scope.partner_show = false;
	}else if(userInfo.user_type==4){  //品牌
		
	}else if(userInfo.user_type==5){  //店铺
		
	}

	//导出
	$scope.dataExport=function(){
		var sort = [{dir:"desc",field:"create_time"}];
		var param={
				enterprise_name: $scope.search.enterprise_name,		 	//企业名称		
				parter_principal: $scope.search.parter_principal,		//合伙人
				brand_name: $scope.search.brand_name,		   			//品牌名称
				account: $scope.search.account,		 				//后台账号
				user_name: $scope.search.user_name,		   			//负责人
				phone_num: $scope.search.phone_num,		 			//联系方式
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "brand_module");  
	    
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
		var grid = $("#gridBrand").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"品牌商列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridBrand").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}
});