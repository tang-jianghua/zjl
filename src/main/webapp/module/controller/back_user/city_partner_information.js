App.controller("cityPartnerInformationCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
			
	$scope.enterprise_show = true;
	
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
							var sort = [{dir:"desc",field:"create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								sort=options.sort;
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									no_open_city: "100",
									enterprise_name: $scope.search.enterprise_name,	 	//企业名称
									user_name: $scope.search.user_name,		   			//合伙人
									account: $scope.search.account,		 				//账号		
									phone_num: $scope.search.phone_num,		 			//联系方式
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
					width : "80px",
					template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle'></span>"
				},
				{         
					title : "企业名称",    
					field : "enterprise_name",
					width : "100px"
				},
				{    
					title : "合伙人",   
					field : "principal",   
					width : "60px"    
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
					title : "账号状态",   
					field : "state",   
					width : "80px",
					values: [
					         { text: "正常", value: 1 },
					         { text: "关闭", value: 2 }
					       ]
				},
				{    
					title : "操作",
					field : "", 
					width : "120px",
				    template:function(e){
				    	var template_html = '';
				    	if(userInfo.user_type==1){  //开发者
				    		return '<div class="operation k-state-default " ng-click="lookDetails(\''+e.id+'\')">查看</div>';
				    	}else if(userInfo.user_type==2){  //企业
				    		if(e.state==1){  //开启
			            		template_html = '<div class="operation k-state-default splitButtonTwoc" ng-click="edit(\''+e.id+'\')">编辑</div>'
								    +'<div class="operation k-state-default splitButtonTwoc" ng-click="disable(\''+e.id+'\')">禁用</div>';
			            		return template_html;
			            	}else if(e.state==2){  //关闭
			            		template_html = '<div class="operation k-state-default splitButtonTwoc" ng-click="edit(\''+e.id+'\')">编辑</div>'
								    +'<div class="operation k-state-default splitButtonTwoc" ng-click="enable(\''+e.id+'\')">启用</div>';
			            		return template_html;
			            	}
				    	}else if(userInfo.user_type==3){  //合伙人
				    		
				    	}else if(userInfo.user_type==4){  //品牌
				    		
				    	}else if(userInfo.user_type==5){  //店铺
				    		
				    	}
				    	
		            	
		            }
				}
				
		]
	};
		                        	
	//查询
	$scope.search=function(){
		$scope.grid.dataSource.page(1);
	}
	
	//初始化省
	$scope.initProvince = function(){
		$("#province").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "code",
		    placeholder: "省",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/province",
	                  dataType : "json"
	              }
	          },
	          schema : {
					data : function(d) {
						return d.result;
					}
				},
	      },
	      change: function(e) {
	    	    var value = this.value();
	    	    $scope.getCityData(value);
	      }
		});
	}
	
	//获取市数据
	$scope.getCityData = function(provinceCode,defaultVal){
		$http({
			   url: path+"/server/city/"+provinceCode,
			   method: 'GET'
		}).success(function(data){
			$scope.initCity(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化市
	$scope.initCity = function(cityData,defaultVal){
		if(cityData){
			var plugObj = $("#city").data("kendoComboBox");
			plugObj.value("");
			plugObj.setDataSource(cityData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#city").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "code",
			    placeholder: "市",
			    filter: "contains",
			    dataSource: [],
			    change: function(e) {
		    	    var value = this.value();
		    	    $scope.getAreaData(value);
		      }
			});
		}
	}
	
	//获取区数据
	$scope.getAreaData = function(cityCode,defaultVal){
		$http({
			   url: path+"/server/county/"+cityCode,
			   method: 'GET'
		}).success(function(data){
			$scope.initArea(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化区
	$scope.initArea = function(areaData,defaultVal){
		if(areaData){
			var plugObj = $("#area").data("kendoComboBox");
			plugObj.setDataSource(areaData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#area").kendoComboBox({
				  dataTextField: "name",
				  dataValueField: "code",
				  placeholder: "区",
				  filter: "contains",
				  dataSource: []
			});
		}
	}
	
	//合伙人启用
	$scope.enable=function(id){
		$http({
			   url: path+'/server/openpartner/'+id,
			   method: 'GET' 
		}).success(function(data){
			alert(data.message);
			$scope.search();
		}).error(function(data){
			alert(data);   
		})
	}
	
	//合伙人禁用
	$scope.disable=function(id){
		$http({
			   url: path+'/server/closepartner/'+id,
			   method: 'GET' 
		}).success(function(data){
			alert(data.message);
			$scope.search();
		}).error(function(data){
			alert(data);   
		})
	}
	
	//添加账号信息
	$scope.addPartnerInformation=function(){
		var params = {method:"add"};
		$location.path("/addPartnerInformation/"+angular.toJson(params));
	}
	
	//合伙人编辑
	$scope.edit=function(id){
		var params = {method:"edit",peopleId:id};
		$location.path("/addPartnerInformation/"+angular.toJson(params));
	}
	
	//查看详情
	$scope.lookDetails=function(id){
		var params = {method:"look",peopleId:id};
		$location.path("/addPartnerInformation/"+angular.toJson(params));
	}
	
	/*页面初始化*/
	$scope.initProvince();			//初始化省
	$scope.initCity();				//初始化市
	$scope.initArea();				//初始化区
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		$("#addAccount").hide();
	}else if(userInfo.user_type==2){  //企业
		$scope.enterprise_show = false;
	}else if(userInfo.user_type==3){  //合伙人
		
	}else if(userInfo.user_type==4){  //品牌
		
	}else if(userInfo.user_type==5){  //店铺
		
	}
	//导出
	$scope.dataExport=function(){
		var sort = [{dir:"desc",field:"create_time"}];
		var param={
				no_open_city: "100",
				enterprise_name: $scope.search.enterprise_name,	 	//企业名称
				user_name: $scope.search.user_name,		   			//合伙人
				account: $scope.search.account,		 				//账号		
				phone_num: $scope.search.phone_num,		 			//联系方式
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "partner_module");  
	    
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
		var grid = $("#gridCity").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"城市合伙人列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridCity").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}
});