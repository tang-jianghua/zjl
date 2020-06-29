App.controller("brandActivityCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {

	$scope.publishPromotionActivity_show = false;
	$scope.enterprise_show = true;
	$scope.partner_show = true;
	$scope.brand_show = true;
	
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryunionpromotion",
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
									name: $scope.search.activity_name,	 		//活动名称
									//property: $scope.search.enterprise_name,	 		//活动性质
									state: $("#activity_state").data("kendoDropDownList").value(),	//活动状态
									
									province: $("#province").data("kendoComboBox").value(),	 	//省
									city: $("#city").data("kendoComboBox").value(),	 			//市
									county: $("#area").data("kendoComboBox").value(),	 		//县
									
									enterprise_name: $("#enterprise_name").val(),	 		//企业名称
									partner_principal: $("#partner_principal").val(),	 	//合伙人
									brand_name: $("#brand_name").val(),	 					//品牌名称
									
									start_time: $("#start_time").val(),	 	//发布日期（始）
									end_time: $("#end_time").val(),	 		//发布日期（止）
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
						field : "name",         
						title : "活动名称",     
						width : "100px"
					},
					{
						field : "brand_name",         
						title : "参与品牌",     
						width : "100px"
					},
					{
						field : "create_time",         
						title : "活动创建日期",     
						width : "100px"
					},
					{
						field : "start_time",         
						title : "活动发布日期",     
						width : "100px"
					},
					{
						field : "partner_principal",         
						title : "所属城市合伙人",     
						width : "100px"
					},
					{
						field : "total_flag",         
						title : "商品范围",     
						width : "100px",
						values: [
						         { text: "部分商品", value: 0 },
						         { text: "全部商品", value: 1 }
				       ],
					},
					{
						field : "onOffFlag",         
						title : "上下架",     
						width : "100px",
						values: [
						         { text: "下架", value: 0 },
						         { text: "上架", value: 1 }
				       ],
					},
					{    
						title : "状态",   
						field : "state",   
						width : "80px",
						values: [
						         { text: "未开始", value: 0 },
						         { text: "进行中", value: 1 },
						         { text: "已结束", value: 2 }
				       ],
				       template: function(e){
					    	var html = '';
					    	if(e.state==0 || !e.state){  //未开始
					    		if(userInfo.user_type==4 && e.total_flag==0){  //品牌,部分商品
					    			html = '<div>未开始</div>'+'<div class="canEdit" ng-click="productSet(\''+e.id+'\')">活动产品设定</div>';
					    		}else if(userInfo.user_type==5 && e.total_flag==0){  //店铺,部分商品
					    			html = '<div>未开始</div>'+'<div class="canEdit">待设定</div>';
					    		}else{
					    			html = '<div>未开始</div>';
					    		}
					    	}else if(e.state==1){  //进行中
					    		html = '<div>进行中</div>';
					    	}else if(e.state==2){  //已结束
					    		html = '<div>已结束</div>';
					    	}
					    	
					    	return html;
					    },
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
					    	if(userInfo.user_type==1){  //开发者
					    		html = ('<div class="operation k-state-default" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
					    	}else if(userInfo.user_type==2){  //企业
					    		html = ('<div class="operation k-state-default" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
					    	}else if(userInfo.user_type==3){  //合伙人
					    		if(e.state==0 || !e.state){  //未开始
					    			html += ('<div class="operation k-state-default splitButtona" ng-click="deleteData(\''+e.id+'\')">删除</div>'
					    					+'<div class="operation k-state-default splitButtona" ng-click="editData(\''+e.id+'\')">编辑</div>'
					    					+'<div class="operation k-state-default splitButtona" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
					    		}else if(e.state==1){	//进行中
					    			if(e.onOffFlag==0){	//下架
					    				html += ('<div class="operation k-state-default splitButtonTwoc" ng-click="upOrDown(\''+e.id+'\',1)">上架</div>'
						    					+'<div class="operation k-state-default splitButtonTwoc" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
					    			}else if(e.onOffFlag==1){	//上架
					    				html += ('<div class="operation k-state-default splitButtonTwoc" ng-click="upOrDown(\''+e.id+'\',0)">下架</div>'
						    					+'<div class="operation k-state-default splitButtonTwoc" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
					    			}
					    		}else{
					    			html += ('<div class="operation k-state-default splitButtonRight" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
					    		}
					    	}else if(userInfo.user_type==4){  //品牌
					    		html = ('<div class="operation k-state-default" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
					    	}else if(userInfo.user_type==5){  //店铺
					    		html = ('<div class="operation k-state-default" ng-click="showDetails(\''+e.id+'\')">查看详情</div>');
					    	}

							return html;
						}
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}

	//初始化活动性质
	$scope.initActivityProperty = function(){
		$("#activity_property").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "满减"
			             },
			             {
			            	 id: 2,
			 				text: "秒杀"
			             },
			             {
			            	 id: 3,
			 				text: "折扣"
			             },
			             {
			            	 id: 4,
			 				text: "一口价"
			             }
            ]
		});
	}
	
	//初始化活动状态
	$scope.initActivityState = function(){
		$("#activity_state").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 0,
			 				text: "未开始"
			             },
			             {
			            	 id: 1,
			 				text: "进行中"
			             },
			             {
			            	 id: 2,
			 				text: "已结束"
			             }
            ]
		});
	}

	//删除
	$scope.deleteData = function(id){
		if(confirm("确认删除？")){
			var postData = {
					id: id
			};
			
			$http({
				   url: path+'/server/deleteunionpromotion',
				   method: 'POST',   
				   data: angular.toJson(postData),
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert("删除失败！");   
			})
		}
	}
	
	//上下架
	$scope.upOrDown = function(id,state){
		var postData = {
				id: id,
				onOffFlag: state
		};
		
		$http({
			   url: path+'/server/modifyonoffflag',
			   method: 'POST',   
			   data: angular.toJson(postData),
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				$scope.grid.dataSource.page(1);
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("失败！");   
		})
	}
	
	//发布促销活动
	$scope.publishPromotionActivity = function(){
		var params = {method:"add"};
		$location.path("/addBrandActivity/"+angular.toJson(params));
	}
	
	//显示详情
	$scope.showDetails = function(id){
		var params = {method:"look", activityId:id};
		$location.path("/addBrandActivity/"+angular.toJson(params));
	}
	
	//编辑
	$scope.editData = function(id){
		var params = {method:"edit", activityId:id};
		$location.path("/addBrandActivity/"+angular.toJson(params));
	}
	
	//活动产品设定
	$scope.productSet = function(id){
		var params = {method:"productSet", activityId:id};
		$location.path("/addBrandActivity/"+angular.toJson(params));
	}
	
	
	//$scope.initActivityProperty();	//初始化活动性质
	$scope.initActivityState();		//初始化活动状态

	publicService.initProvince(1);		//初始化省
	publicService.initCity(1);			//初始化市
	publicService.initArea(1);			//初始化区
	publicService.initEnterpriseName("enterprise_name");	//初始化【企业名称】
	publicService.initPartnerName("partner_principal");		//初始化【合伙人名称】
	publicService.initBrandName("brand_name");				//初始化【品牌名称】

	publicService.initDateTime("start_time");		//发布日期（始）
	publicService.initDateTime("end_time");			//发布日期（止）
	
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业
		
		$scope.enterprise_show = false;
	}else if(userInfo.user_type==3){  //合伙人
		$scope.publishPromotionActivity_show = true;
		
		$scope.enterprise_show = false;
		$scope.partner_show = false;
	}else if(userInfo.user_type==4){  //品牌
		$scope.enterprise_show = false;
		$scope.partner_show = false;
		$scope.brand_show = false;
	}else if(userInfo.user_type==5){  //店铺
		$scope.enterprise_show = false;
		$scope.partner_show = false;
		$scope.brand_show = false;
	}
	

});