App.controller("exchangeManage",function($scope, $rootScope, $location, $http, $compile, path) {
	
	$scope.mainGridOptions = {
			dataSource : {
					transport : {
						read : {
							url : path+"/server/querypointconver",
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
										id:$scope.id,
										product_title:$("#product_title").val(),
										convert_type:$("#convert_type").data("kendoComboBox").value(),
										name:$("#userName").val(),
										convert_time:$("#convert_time").val()
										
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
							console.log(d);
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
			    mode: "multiple"     //排序模式：single，multiple
			  },
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
						field : "brand_id", 
						width : "80px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{    
						title : "商品图",   
						field : "product_img",   
						width : "120px",
						template:"<img src='#: product_img #' style='height:100px;width:100px'/>"
					},
					{    
						title : "商品名称",   
						field : "product_title",   
					},
					{    
						title : "兑换时间",   
						field : "convert_time",   
					},
					{    
						title : "兑换码",   
						field : "convert_code",   
					},
					{    
						title : "兑换类型",   
						field : "convert_type", 
						values: [
						         { text: "流量包", value: 1 },
						         { text: "购物卡", value: 2 },
						         { text: "实物商品", value: 3 },
						         { text: "实物礼品", value: 4 },
						         { text: "话费", value: 5 }
						       ]
					},
					{    
						title : "用户昵称",   
						field : "name",   
					},
					{    
						title : "消费积分",   
						field : "convert_point",   
					},
					{    
						title : "使用状态",   
						field : "convert_state",
						values: [
						         { text: "未使用", value: 1 },
						         { text: "已使用", value: 2 },
						         { text: "充值中", value:3}
						       ]
					}
			]
		};
	//初始化兑换类型
    $scope.initExchangeType=function(){    
    	$("#convert_type").kendoComboBox({
    		autoBind: false,
    		dataTextField: "name",
    		dataValueField: "id",
    		placeholder: "请选择",
    		filter: "contains",
    		dataSource: [{id:1,name:"流量包"},{id:2,name:"购物卡"},{id:3,name:"实物商品"},{id:4,name:"实物礼品"}],
    	});
    }
    //初始化时间控件
	$scope.initDateTime = function(containerId){
		$("#"+containerId).kendoDateTimePicker({
		     value: "",
		     culture: "de-DE",
		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
		     interval: 30,   				  //时间间隔
		});
	}
    $scope.initExchangeType();//初始化兑换类型
    $scope.initDateTime("convert_time");//初始化时间
	//跳转添加积分商品页
	$scope.addIntegralsStore=function(){
		var params = {method:"add"};
		$location.path("/addIntegralsStore/"+angular.toJson(params));
	}
	//搜索
	$scope.search=function(){
		$scope.grid.dataSource.page(1);
	}
	//点击兑换
	$scope.exchangeClick=function(){
		$http({
			   url: path+"/server/querypointconvertbyconvertcode/"+$("#exchangeCode").val(),
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){
				$scope.id=data.result.id;
				$scope.grid.dataSource.page(1);
				if(confirm("是否确认兑换？")){
					$http({
						   url: path+"/server/updatepoinconvert/"+data.result,
						   method: 'GET'
					}).success(function(data){
						if(data.code==0){
							$scope.grid.dataSource.page(1);
							alert("兑换成功");	
						}else{
							alert("兑换失败");
						}
					}).error(function(data){
						console.log(data);   
					})
				}		
			}else{
				alert("兑换码已使用或无效");
			}
		}).error(function(data){
			console.log(data);   
		})
	}
})