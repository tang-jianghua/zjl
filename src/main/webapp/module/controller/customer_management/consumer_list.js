App.controller("consumerListCtrl",function($scope, $rootScope, $location, $http, $compile, path) {
	
	$scope.details = {};   //账户添加表单

	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querycustomer",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"desc",field:"t.create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								if(options.sort[0].field=="points"){
									options.sort[0].field="po.points"
								}else if(options.sort[0].field=="inviter_count"){
									options.sort[0].field="inv.inviter_count"
								}
								
								sort=options.sort;
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									name: $scope.search.userName,	 		            //姓名
									phone_num: $scope.search.phone_num,	 	        //联系方式
									seller_name: $scope.search.seller_name,	 		//推广导购
									property: $("#userProperty").data("kendoDropDownList").value(),	 //用户属性
									province: $("#province").data("kendoComboBox").value(),  //省
									city: $("#city").data("kendoComboBox").value(),  		 //市
									county: $("#area").data("kendoComboBox").value(),  		 //区
									sort:sort
								}
							};
							
							if(parameter.param.seller_name && "消费者".indexOf(parameter.param.seller_name)>-1){	//消费者
								parameter.param.seller_name = "";
								parameter.param.special_name = "消费者";
								parameter.param.property = 2;
							}
							
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
						width : "80px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle'></span>"
					},
					{
						field : "customer_name",         
						title : "昵称",     
						width : "100px"
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "100px"
					},
					{
						field : "gender",         
						title : "性别",     
						width : "80px",
						values: [
						         { text: "女", value: true },
						         { text: "男", value: false }
						       ]
					},
					{
						field : "customer_account",         
						title : "消费者账号",     
						width : "100px"    
					},
					{
						field : "points",         
						title : "积分",     
						width : "100px",
						template: function(e){
							if(e.points){
								return e.points;
							}else{
								return "0";
							}
						}
					},
					{
						field : "property",         
						title : "用户属性",     
						width : "100px",
						values: [
						         { text: "自主注册", value: 1 },
						         { text: "邀请注册", value: 2 }
						       ]
					},
					{
						field : "sell_name",         
						title : "推广人",     
						width : "100px",
						template: function(e){
							if(e.sell_name){
								return e.sell_name;
							}else if(e.property==2 && e.invieter_name && !e.sell_name){	//邀请注册，有邀请人账号，无推广人
								return "消费者";
							}else{
								return "";
							}
						}
					},
					{
						field : "inviter_count",         
						title : "推广数量",     
						width : "100px"    
					},
					{
						field : "invieter_name",         
						title : "邀请人账号",     
						width : "100px"    
					},
					{
						field : "create_time",         
						title : "创建时间",     
						width : "100px"    
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div ' value='查看详情' class='operation k-state-default' ng-click='lookDetails(\"#: id #\")' />查看详情</div>"
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//初始化用户属性
	$scope.initUserProperty = function(){
		$("#userProperty").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "自主注册"
			             },
			             {
			            	 id: 2,
			 				text: "邀请注册"
			             }
			             ]
		});
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
	
	//送优惠券
	$scope.sendCoupon = function(){
		alert("送优惠券！");
	}
	
	//送红包
	$scope.sendRedPackage = function(){
		alert("送红包！");
	}
	
	//查看详情
	$scope.lookDetails = function(id){
		$scope.consumerInfoWindow.center().open();   //打开弹框
		
		$http({
			   url: path+'/server/getcustomerbyid/'+id,
			   method: 'GET'
		}).success(function(data){
			var datails = data;
			
			if(datails){
				
				$scope.details.head_url = datails.head_url;
				
				$scope.details.customer_name = datails.customer_name;
				if(datails.gender==true){
					$scope.details.gender = '女';
				}else if(datails.gender==false){
					$scope.details.gender = '男';
				}
				
				//$scope.details.name = datails.name;
				$scope.details.birthday = datails.birthday;
				
				if(datails.property==1){
					$scope.details.property = '自主注册';
				}else if(datails.property==2){
					$scope.details.property = '邀请注册';
				}
				if(datails.points){
					$scope.details.points = datails.points;
				}else{
					$scope.details.points = 0;
				}
				
				$scope.details.phone = datails.phone;
				$scope.details.email = datails.email;
				
				$scope.details.address = datails.address;
				
				$scope.details.space = datails.space;
				$scope.details.style = datails.style;
				$scope.details.class_name = datails.class_name;
				
				$scope.details.order_count = datails.order_count;
				$scope.details.order_t = datails.order_t;
				
			}else{
				alert("暂无详情信息！");
			}
			
			
			
		}).error(function(data){
			alert(data);   
		})
	}
	
	//消费记录
	$scope.consumeRecord = function(id){
		alert("消费记录！");
	}
	
	
	
	
	
	$scope.initUserProperty();		//初始化用户属性
	
	$scope.initProvince();			//初始化省
	$scope.initCity();				//初始化市
	$scope.initArea();				//初始化区
	

	//导出
	$scope.dataExport = function(){
		var sort = [{dir:"desc",field:"t.create_time"}];
		var param={
				name: $scope.search.userName,	 		            //姓名
				phone_num: $scope.search.phone_num,	 	        //联系方式
				seller_name: $scope.search.seller_name,	 		//推广导购
				property: $("#userProperty").data("kendoDropDownList").value(),	 //用户属性
				province: $("#province").data("kendoComboBox").value(),  //省
				city: $("#city").data("kendoComboBox").value(),  		 //市
				county: $("#area").data("kendoComboBox").value(),  		 //区
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "consumer_module");  
	    
	    var my_input_page = $('<input type="text" name="page" />');  
	    /*{page:1,pageSize:10,ispage:false,"param":param}*/
	    my_input_page.attr('value', angular.toJson({page:1,pageSize:10,ispage:false,"param":param}));  
	    // 附加到Form  
	    form.append(my_input);  
	    form.append(my_input_page);  
	    console.log(form.serializeArray());
	    form.submit(); 
	}
	

});