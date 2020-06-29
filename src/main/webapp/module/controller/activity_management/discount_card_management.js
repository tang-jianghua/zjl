App.controller("discountCardManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	

	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querycoupons",
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
									type: $("#cardType").data("kendoDropDownList").value(),	 	//卡券类型
									name: $scope.search.cardName,         //卡券名称
									brand_name: $("#brandName").val(),         //品牌名称
									start_time: $("#startTime").val(),    //发布日期
									end_time: $("#endTime").val(),    	  //结束日期
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
						field : "type",         
						title : "卡券类型",     
						width : "100px",
						values: [
						         {value:1, text:"红包"},
						         {value:2, text:"优惠卷"},
						         {value:3, text:"折扣券"}
						]
					},
					{
						field : "name",         
						title : "卡券名称",     
						width : "150px"
					},
					{
						field : "brand_name",         
						title : "品牌名称",     
						width : "150px"
					},
					{
						field : "value",         
						title : "面额",     
						width : "80px"
					},
					{
						field : "coupons_price",         
						title : "金额",     
						width : "80px"
					},
					{
						field : "publish_count",         
						title : "发行数量",     
						width : "80px"
					},
					{
						field : "start_time",         
						title : "发布日期",     
						width : "120px"
					},
					{
						field : "end_time",         
						title : "结束日期",     
						width : "120px"
					},
					{
						field : "re_count",         
						title : "领取数量",     
						width : "80px",
						template: function(e){
							if(e.re_count){
								return e.re_count;
							}else{
								return 0;
							}
						}
						
					},
					{
						field : "use_count",         
						title : "使用数量",     
						width : "80px",
						template: function(e){
							if(e.use_count){
								return e.use_count;
							}else{
								return 0;
							}
						}
					},
					{
						field : "scope_type",         
						title : "发布者",     
						width : "80px",
						values: [
						         {value:1, text:"开发者"},
						         {value:2, text:"品牌"}
						         ]
					},
					{
						field : "state",         
						title : "状态",     
						width : "80px",
						values: [
						         {value:1, text:"上架"},
						         {value:2, text:"下架"}
						         ]
					},
					{
						field : "name",         
						title : "操作",     
						width : "120px",
						template:function(e){
							var html = '';
							var nowDate = publicService.getCurrentTime();
							
					    	if(userInfo.user_type==1){  //开发者
					    		if(e.scope_type==1){
					    			if(nowDate<e.start_time){
					    				html = ('<div class="k-state-default canOperate splitButtonTwoc" ng-click="editData(\''+e.id+'\',\''+e.type+'\')">编辑</div>'
							    			   +'<div class="k-state-default canOperate splitButtonTwoc" ng-click="deleteData(\''+e.id+'\')">删除</div>');
					    			}else if(nowDate>=e.start_time && nowDate<=e.end_time){
					    				if(e.state==1){
					    					html = ('<div class="k-state-default canOperate splitButtonTwoc" ng-click="forceDown(\''+e.id+'\')">强制下架</div>'
										    	   +'<div class="k-state-default canOperate splitButtonTwoc" ng-click="showDetails(\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
					    				}else{
					    					html = ('<div class="k-state-default canOperate" ng-click="showDetails(\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
					    				}
					    			}else if(nowDate>e.end_time){
					    				html = ('<div class="k-state-default canOperate" ng-click="showDetails(\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
					    			}
					    		}else if(e.scope_type==2){
					    			html = ('<div class="k-state-default canOperate" ng-click="showDetails(\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
					    		}
					    	}else if(userInfo.user_type==4){	//品牌
					    		if(e.scope_type==1){
					    			html = ('<div class="k-state-default canOperate" ng-click="showDetails(\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
					    		}else if(e.scope_type==2){
					    			if(nowDate<e.start_time){
					    				html = ('<div class="k-state-default canOperate splitButtonTwoc" ng-click="editData(\''+e.id+'\',\''+e.type+'\')">编辑</div>'
							    			   +'<div class="k-state-default canOperate splitButtonTwoc" ng-click="deleteData(\''+e.id+'\')">删除</div>');
					    			}else if(nowDate>=e.start_time && nowDate<=e.end_time){
					    				if(e.state==1){	//上架
					    					html = ('<div class="k-state-default canOperate splitButtonTwoc" ng-click="forceDown(\''+e.id+'\',\''+e.type+'\')">强制下架</div>'
									    		   +'<div class="k-state-default canOperate splitButtonTwoc" ng-click="showDetails(\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
					    				}else if(e.state==2){	//下架
					    					html = ('<div class="k-state-default canOperate" ng-click="showDetails(\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
					    				}
					    			}else if(nowDate>e.end_time){
					    				html = ('<div class="k-state-default canOperate" ng-click="showDetails(\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
					    			}
					    		}
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
	
	//初始化卡券类型
	$scope.initCardType = function(){
		$("#cardType").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "红包"
			             },
			             {
			            	 id: 2,
			 				text: "优惠券"
			             },
			             {
			            	 id: 3,
			 				text: "折扣券"
			             }
            ]
		});
	}

	//设定卡券
	$scope.addCardAndRedPackage=function(){
		var params = {method:"add"};
		$location.path("/addCardAndRedPackage/"+angular.toJson(params));
	}
	
	//编辑
	$scope.editData=function(id,type){
		var params = {method:"edit", cardId:id, type:type};
		$location.path("/addCardAndRedPackage/"+angular.toJson(params));
	}
	
	//显示详情
	$scope.showDetails = function(id,type){
		var params = {method:"look", cardId:id, type:type};
		$location.path("/addCardAndRedPackage/"+angular.toJson(params));
	}

	//删除
	$scope.deleteData=function(id){
		if(confirm("确认删除？")){
			$http({
				   url: path+'/server/removeByname/'+id,
				   method: 'GET'
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
	}
	
	//强制下架
	$scope.forceDown = function(id){
		if(confirm("确认强制下架？")){
			var postData = {
					id: id,
					state: 2
			};
			
			$http({
				   url: path+'/server/updateCouponsState',
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
	}
	
	//显示到首页
	$scope.showFirstPage=function(id){
		return;
		$http({
			   url: path+'/server/createcoupons/'+id,
			   method: 'GET' 
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}

	
	$scope.initCardType();							//初始化卡券类型
	publicService.initBrandName("brandName");		//初始化【品牌名称】
	publicService.initDateTime("startTime");		//发布日期（始）
	publicService.initDateTime("endTime");			//发布日期（止）
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==4){  //品牌
		
	}
	

});