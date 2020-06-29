App.controller("platformActivityCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	
	$scope.search1 = {};	//平台活动查询表单
	$scope.search2 = {};	//秒杀活动查询表单
	$scope.search3 = {};	//参与活动查询表单
	$scope.search4 = {};	//活动报名查询表单
	$scope.search5 = {};	//折扣活动查询表单
	
	
	//选择面板
	$scope.choosePannel = function(type){
		for(var i=1;i<=5;i++){
			if(i==type){
				$("#pannel_"+i).addClass("choose");
				$("#container_"+i).show();
			}else{
				$("#pannel_"+i).removeClass("choose");
				$("#container_"+i).hide();
			}
		}
	}
	
	//平台活动
	$scope.mainGridOptions1_ = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryheadline",
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
									//title: $scope.search.title,	 	//姓名
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
			columns : [
					{
						field : "title",         
						title : "活动名称",     
						width : "120px"
					},
					{
						field : "",         
						title : "活动创建日期",     
						width : "100px"
					},
					{
						field : "",         
						title : "活动发布日期",     
						width : "80px"
					},
					{
						field : "",         
						title : "成功报名数据",     
						width : "100px"
					},
					{
						field : "",         
						title : "状态",     
						width : "80px"
					},
					{
						field : "id",         
						title : "操作",     
						width : "60px"
					}
			]
		};
	
	//秒杀活动
	$scope.mainGridOptions2 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryplatformpromotionListByPage",
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
									title: $scope.search2.activity_name,	 	//活动名称
									//property: $("#activity_property2").data("kendoDropDownList").value(),	//活动性质
									state: $("#activity_state2").data("kendoDropDownList").value(),			//活动状态
									create_time1: $("#create_time2_1").val(),	//创建日期
									create_time2: $("#create_time2_2").val(),	//创建日期
									start_time1: $("#start_time2_1").val(),	//发布日期
									start_time2: $("#start_time2_2").val(),	//发布日期
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
			columns : [
					{
						field : "title",         
						title : "活动名称",     
						width : "100px"
					},
					{
						field : "create_time",         
						title : "活动创建日期",     
						width : "80px"
					},
					{
						field : "start_time",         
						title : "活动发布日期",     
						width : "80px"
					},
					{
						field : "brand_id_count",         
						title : "成功报名品牌",     
						width : "80px"
					},
					{
						field : "state",         
						title : "上下架",     
						width : "80px",
						values: [
						         { text: "下架", value: 0 },
						         { text: "上架", value: 1 },
						         { text: "已删除", value: 2 }
				        ],
					},
					{
						field : "state_t",         
						title : "状态",     
						width : "80px",
						values: [
						         { text: "未开始", value: 0 },
						         { text: "进行中", value: 1 },
						         { text: "已结束", value: 2 }
				        ],
					},
					{
						field : "id",         
						title : "操作",     
						width : "90px",
						template: function(e){
					    	var html = '';
					    	
					    	if(userInfo.user_type==1){  //开发者
					    		if(e.state_t==0){	//未开始
					    			html = '<div class="operation k-state-default splitButtona" ng-click="deleteActivity('+e.type+',\''+e.id+'\',2)">取消活动</div>'
		    					  		  +'<div class="operation k-state-default splitButtona" ng-click="editData('+e.type+',\''+e.id+'\')">编辑</div>'
		    					  		  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'activityCheck\',\''+e.id+'\','+e.type+')">审核活动</div>';
					    		}else if(e.state_t==1){	//进行中
					    			if(e.state==0){	//下架
					    				html = '<div class="operation k-state-default splitButtona noOperate">强行停止</div>'
		    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
		    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'productManagement_1\',\''+e.id+'\','+e.type+')">产品管理</div>';
					    			}else if(e.state==1){	//上架
					    				html = '<div class="operation k-state-default splitButtona" ng-click="upOrDownActivity('+e.type+',\''+e.id+'\',0)">强行停止</div>'
		    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
		    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'productManagement_1\',\''+e.id+'\','+e.type+')">产品管理</div>';
					    			}
					    		}else if(e.state_t==2){	//已结束
					    			html = '<div class="operation k-state-default splitButtona noOperate">删除活动</div>'
	    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
	    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'productManagement_2\',\''+e.id+'\','+e.type+')">产品管理</div>';
					    		}
					    	}else if(userInfo.user_type==2){  //企业
					    		html = '<div class="operation k-state-default text-center"  ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>';
					    	}else if(userInfo.user_type==3){  //合伙人
					    		html = '<div class="operation k-state-default text-center" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>';
					    	}else if(userInfo.user_type==4){  //品牌
					    		
					    	}else if(userInfo.user_type==5){  //店铺
					    		
					    	}

					    	return html;
					    },
					}
			]
		};
	
	//参与的活动
	$scope.mainGridOptions3 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/getbrandpromotionListByPage1",
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
									title: $scope.search3.activity_name,	 	//活动名称
									type: $("#activity_type3").data("kendoDropDownList").value(),	//活动类型
									state: $("#activity_state3").data("kendoDropDownList").value(),			//活动状态
									start_time: $("#start_time3").val(),	//活动日期（始）
									end_time: $("#end_time3").val(),		//活动日期（止）
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
			columns : [
					{
						field : "title",         
						title : "活动名称",     
						width : "100px"
					},
					{
						field : "type",         
						title : "活动类型",     
						width : "100px",
						values: [
						         { text: "秒杀活动", value: 0 },
						         { text: "满额折扣", value: 1 },
						         { text: "满减折扣", value: 2 },
						         { text: "数量折扣", value: 3 }
				        ]
					},
					{
						field : "create_time",         
						title : "活动创建日期",     
						width : "80px"
					},
					{
						field : "start_time",         
						title : "活动开始日期",     
						width : "80px"
					},
					{
						field : "end_time",         
						title : "活动结束日期",     
						width : "80px"
					},
					{
						field : "brand_id_count",         
						title : "成功报名品牌",     
						width : "80px"
					},
					{
						field : "state",         
						title : "状态",     
						width : "80px",
						values: [
						         { text: "未开始", value: 0 },
						         { text: "进行中", value: 1 },
						         { text: "已结束", value: 2 }
				        ],
					},
					{
						field : "id",         
						title : "操作",     
						width : "60px",
						template: function(e){
					    	var html = '';
					    	
					    	if(e.state==0){	//未开始
				    			html = '<div class="operation k-state-default splitButtonTwoc" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
	    					  		  +'<div class="operation k-state-default splitButtonTwoc" ng-click="productDetails(\'productLook_0\',\''+e.id+'\','+e.type+')">报名管理</div>';
				    		}else if(e.state==1){	//进行中
				    			html = '<div class="operation k-state-default splitButtonTwoc" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
    					  		  	  +'<div class="operation k-state-default splitButtonTwoc" ng-click="productDetails(\'productLook_1\',\''+e.id+'\','+e.type+')">产品管理</div>';
				    		}else if(e.state==2){	//已结束
				    			html = '<div class="operation k-state-default splitButtonTwoc" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
    					  		  	  +'<div class="operation k-state-default splitButtonTwoc" ng-click="productDetails(\'productLook_2\',\''+e.id+'\','+e.type+')">产品管理</div>';
				    		}
					    	
					    	return html;
					    },
					}
			]
		};
	
	//活动报名
	$scope.mainGridOptions4 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/getbrandpromotionListByPage2",
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
									title: $scope.search4.activity_name,	 	//活动名称
									type: $("#activity_type4").data("kendoDropDownList").value(),	//活动类型
									state: $("#sign_state4").data("kendoDropDownList").value(),		//报名状态
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
			columns : [
					{
						field : "title",         
						title : "活动名称",     
						width : "100px"
					},
					{
						field : "type",         
						title : "活动类型",     
						width : "100px",
						values: [
						         { text: "秒杀活动", value: 0 },
						         { text: "满额折扣", value: 1 },
						         { text: "满减折扣", value: 2 },
						         { text: "数量折扣", value: 3 }
				        ],
					},
					{
						field : "limit_time",         
						title : "报名截止日期",     
						width : "80px"
					},
					{
						field : "start_time",         
						title : "活动时间",     
						width : "80px",
						template: function(e){
							return e.start_time+"~"+e.end_time;
						}
					},
					{
						field : "state",         
						title : "状态",     
						width : "80px",
						values: [
						         { text: "未报名", value: 0 },
						         { text: "已报名", value: 1 }
				        ],
					},
					{
						field : "register_num",         
						title : "报名数量",     
						width : "80px"
					},
					{
						field : "pass_num",         
						title : "通过数量",     
						width : "80px"
					},
					{
						field : "id",         
						title : "操作",     
						width : "60px",
						template: function(e){
					    	var html = '';
					    	
					    	if(e.state==0){	//未报名
				    			html = '<div class="operation k-state-default splitButtonTwoc" ng-click="activitySign(\''+e.id+'\','+e.type+')">活动报名</div>'
	    					  		  +'<div class="operation k-state-default splitButtonTwoc noOperate">产品管理</div>';
				    		}else if(e.state==1){	//已报名
				    			html = '<div class="operation k-state-default splitButtonTwoc" ng-click="activitySign(\''+e.id+'\','+e.type+')">报名信息</div>'
    					  		  	  +'<div class="operation k-state-default splitButtonTwoc" ng-click="productDetails(\'productSet\',\''+e.id+'\','+e.type+')">产品管理</div>';
				    		}
					    	
					    	return html;
					    },
					}
			]
		};
	
	//折扣活动
	$scope.mainGridOptions5 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryPlatformStepPromotionListByPage",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"asc",field:"state_t"}];
							if(options.sort!=null && options.sort.length>0 ){
								if(options.sort[0].field=="create_time"){
									options.sort[0].field="p.create_time"
								}
								
								sort=options.sort;
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									title: $scope.search5.activity_name,	 	//活动名称
									type: $("#activity_type5").data("kendoDropDownList").value(),	//活动类型
									state: $("#activity_state5").data("kendoDropDownList").value(),			//活动状态
									create_time1: $("#create_time5_1").val(),	//创建日期
									create_time2: $("#create_time5_2").val(),	//创建日期
									start_time1: $("#start_time5_1").val(),	//发布日期
									start_time2: $("#start_time5_2").val(),	//发布日期
									sort:sort
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
			columns : [
					{
						field : "title",         
						title : "活动名称",     
						width : "100px"
					},
					{
						field : "type",         
						title : "活动类型",     
						width : "80px",
						values: [
						         { text: "满额折扣", value: 1 },
						         { text: "满减折扣", value: 2 },
						         { text: "数量折扣    ", value: 3 }
				        ],
					},
					{
						field : "create_time",         
						title : "活动创建日期",     
						width : "80px"
					},
					{
						field : "start_time",         
						title : "活动发布日期",     
						width : "80px"
					},
					{
						field : "brand_id_count",         
						title : "成功报名品牌",     
						width : "80px"
					},
					{
						field : "state",         
						title : "上下架",     
						width : "80px",
						values: [
						         { text: "下架", value: 0 },
						         { text: "上架", value: 1 },
						         { text: "已删除", value: 2 }
				        ],
					},
					{
						field : "state_t",         
						title : "状态",     
						width : "80px",
						values: [
						         { text: "未开始", value: 0 },
						         { text: "进行中", value: 1 },
						         { text: "已结束", value: 2 }
				        ],
					},
					{
						field : "id",         
						title : "操作",     
						width : "90px",
						template: function(e){
					    	var html = '';
					    	
					    	if(userInfo.user_type==1){  //开发者
					    		if(e.state_t==0){	//未开始
					    			if(e.state==0){	//下架
					    				html = '<div class="operation k-state-default splitButtona noOperate">取消活动</div>'
			    					  		  +'<div class="operation k-state-default splitButtona" ng-click="editData('+e.type+',\''+e.id+'\')">编辑</div>'
			    					  		  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'activityCheck\',\''+e.id+'\','+e.type+')">审核活动</div>';
					    			}else if(e.state==1){	//上架
					    				html = '<div class="operation k-state-default splitButtona" ng-click="deleteActivity('+e.type+',\''+e.id+'\',2)">取消活动</div>'
			    					  		  +'<div class="operation k-state-default splitButtona" ng-click="editData('+e.type+',\''+e.id+'\')">编辑</div>'
			    					  		  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'activityCheck\',\''+e.id+'\','+e.type+')">审核活动</div>';
					    			}
					    		}else if(e.state_t==1){	//进行中
					    			if(e.state==0){	//下架
					    				html = '<div class="operation k-state-default splitButtona noOperate">强行停止</div>'
		    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
		    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'productManagement_1\',\''+e.id+'\','+e.type+')">产品管理</div>';
					    			}else if(e.state==1){	//上架
					    				html = '<div class="operation k-state-default splitButtona" ng-click="upOrDownActivity('+e.type+',\''+e.id+'\',0)">强行停止</div>'
		    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
		    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'productManagement_1\',\''+e.id+'\','+e.type+')">产品管理</div>';
					    			}
					    		}else if(e.state_t==2){	//已结束
					    			html = '<div class="operation k-state-default splitButtona noOperate">删除活动</div>'
	    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>'
	    					  		  	  +'<div class="operation k-state-default splitButtona" ng-click="checkActivity(\'productManagement_2\',\''+e.id+'\','+e.type+')">产品管理</div>';
					    		}
					    	}else if(userInfo.user_type==2){  //企业
					    		html = '<div class="operation k-state-default text-center"  ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>';
					    	}else if(userInfo.user_type==3){  //合伙人
					    		html = '<div class="operation k-state-default text-center" ng-click="lookDetails('+e.type+',\''+e.id+'\')">查看详情</div>';
					    	}else if(userInfo.user_type==4){  //品牌
					    		
					    	}else if(userInfo.user_type==5){  //店铺
					    		
					    	}

					    	return html;
					    },
					}
			]
		};
	
	//查询
	$scope.search = function(type){
		if(type==1){
			$scope.grid1.dataSource.page(1);
		}else if(type==2){
			$scope.grid2.dataSource.page(1);
		}else if(type==3){
			$scope.grid3.dataSource.page(1);
		}else if(type==4){
			$scope.grid4.dataSource.page(1);
		}else if(type==5){
			$scope.grid5.dataSource.page(1);
		}
	}
	
	//发布活动
	$scope.publishActivity = function(type){
		if(type==1){		//平台活动
			var params = {method:"add"};
			$location.path("/addPlatformActivity/"+angular.toJson(params));
		}else if(type==2){	//秒杀活动
			var params = {method:"add"};
			$location.path("/addSeckillActivity/"+angular.toJson(params));
		}else if(type==5){	//折扣活动
			var params = {method:"add"};
			$location.path("/addDiscountActivity/"+angular.toJson(params));
		}
	}
	
	//删除
	$scope.deleteActivity = function(type,id,state){
		if(confirm("确认取消此活动？")){
			if(type==0){	//秒杀活动
				$scope.modifySeckillActivityState(id,state);
			}else if(type==1 || type==2 || type==3){	//折扣活动
				$scope.modifyDiscountActivityState(id,state);
			}
		}
	}
	
	//上下架
	$scope.upOrDownActivity = function(type,id,state){
		if(confirm("确认强行停止此活动？")){
			if(type==0){	//秒杀活动
				$scope.modifySeckillActivityState(id,state);
			}else if(type==1 || type==2 || type==3){	//折扣活动
				$scope.modifyDiscountActivityState(id,state);
			}
		}
	}
	
	//编辑秒杀活动状态
	$scope.modifySeckillActivityState = function(id,state){
		var postData = {
				id: id,
				state: state
		};
		
		$http({
			   url: path+'/server/modifyplatformpromotion',
			   method: 'POST',   
			   data: angular.toJson(postData),
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				$scope.grid2.dataSource.page(1);
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("系统异常！");   
		})
	}
	
	//编辑折扣活动状态
	$scope.modifyDiscountActivityState = function(id,state){
		var postData = {
				id: id,
				state: state
		};
		
		$http({
			   url: path+'/server/modifyPlatformStepPromotion',
			   method: 'POST',   
			   data: angular.toJson(postData),
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				$scope.grid5.dataSource.page(1);
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("系统异常！");   
		})
	}
	
	
	//活动审核
	$scope.checkActivity = function(method,id,type){
		var params = {method:method,activityId:id,activityType:type};
		$location.path("/activityCheck/"+angular.toJson(params));
	}
	
	//编辑
	$scope.editData = function(type,id){
		/*if(type==1){		//平台活动
			var params = {method:"edit",activityId:id};
			$location.path("/addPlatformActivity/"+angular.toJson(params));
		}*/
		if(type==0){	//秒杀活动
			var params = {method:"edit",activityId:id};
			$location.path("/addSeckillActivity/"+angular.toJson(params));
		}else if(type==1 || type==2 || type==3){	//折扣活动
			var params = {method:"edit",activityId:id};
			$location.path("/addDiscountActivity/"+angular.toJson(params));
		}
	}
	
	//查看详情
	$scope.lookDetails = function(type,id){
		/*if(type==1){		//平台活动
			var params = {method:"look",activityId:id};
			$location.path("/addPlatformActivity/"+angular.toJson(params));
		}*/
		if(type==0){	//秒杀活动
			var params = {method:"look",activityId:id};
			$location.path("/addSeckillActivity/"+angular.toJson(params));
		}else if(type==1 || type==2 || type==3){	//折扣活动
			var params = {method:"look",activityId:id};
			$location.path("/addDiscountActivity/"+angular.toJson(params));
		}
	}
	
	//活动报名
	$scope.activitySign = function(id,type){
		var params = {method:"sign",activityId:id,activityType:type};
		$location.path("/activitySign/"+angular.toJson(params));
	}

	//产品详情
	$scope.productDetails = function(method,id,type){
		var params = {method:method,activityId:id,activityType:type};
		$location.path("/signProductDetails/"+angular.toJson(params));
	}

	/*平台活动*/
	//publicService.initActivityProperty("activity_property1");	//活动性质
	publicService.initActivityState("activity_state1");			//活动状态
	publicService.initDateTime("create_time1");					//创建日期
	publicService.initDateTime("start_time1");					//发布日期
	/*秒杀活动*/
	//publicService.initActivityProperty("activity_property2");	//活动性质
	publicService.initActivityState("activity_state2");			//活动状态
	publicService.initDateTime("create_time2_1");					//创建日期
	publicService.initDateTime("create_time2_2");					//创建日期
	publicService.initDateTime("start_time2_1");					//发布日期
	publicService.initDateTime("start_time2_2");					//发布日期
	/*参与的活动*/
	publicService.initActivityType("activity_type3");			//活动类型
	publicService.initActivityState("activity_state3");			//活动状态
	publicService.initDateTime("start_time3");					//活动日期（始）
	publicService.initDateTime("end_time3");					//活动日期（止）
	/*活动报名*/
	publicService.initActivityType("activity_type4");			//活动类型
	publicService.initSignState("sign_state4");					//报名状态
	/*折扣活动*/
	publicService.initDiscountActivityType("activity_type5");	//活动类型
	publicService.initActivityState("activity_state5");			//活动状态
	publicService.initDateTime("create_time5_1");					//创建日期
	publicService.initDateTime("create_time5_2");					//创建日期
	publicService.initDateTime("start_time5_1");					//发布日期
	publicService.initDateTime("start_time5_2");					//发布日期

	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		$scope.choosePannel(2);		//选择面板
		
		$("#pannel_3").hide();
		$("#pannel_4").hide();
		
	}else if(userInfo.user_type==2){  //企业
		$scope.choosePannel(2);		//选择面板
		
		$("#pannel_3").hide();
		$("#pannel_4").hide();
		
		$("#operateContainer1").hide();
		$("#operateContainer2").hide();
	}else if(userInfo.user_type==3){  //合伙人
		$scope.choosePannel(2);		//选择面板
		
		$("#pannel_3").hide();
		$("#pannel_4").hide();
		
		$("#operateContainer1").hide();
		$("#operateContainer2").hide();
	}else if(userInfo.user_type==4){  //品牌
		$scope.choosePannel(3);		//选择面板
		
		$("#pannel_1").hide();
		$("#pannel_2").hide();
		$("#pannel_5").hide();
		
	}else if(userInfo.user_type==5){  //店铺
		$scope.choosePannel(3);		//选择面板
		
		$("#pannel_1").hide();
		$("#pannel_2").hide();
		$("#pannel_4").hide();
		$("#pannel_5").hide();
	}

});