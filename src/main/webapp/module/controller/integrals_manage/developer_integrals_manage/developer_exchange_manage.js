App.controller("developerExchangeManage",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
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
										product_title:$("#product_title").val(),
										convert_type:$("#convert_type").data("kendoComboBox").value(),
										state:$("#state").data("kendoComboBox").value(),
										name:$("#userName").val(),
										partner_name:$("#partner_name").val(),
										shop_name:$("#shop_name").val(),
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
						field : "id", 
						width : "50px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle'></span>"
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
						title : "兑换类型",   
						field : "convert_type",  
						width : "80px",
						values: [
						         { text: "流量包", value: 1 },
						         { text: "购物卡", value: 2 },
						         { text: "实物商品", value: 3 },
						         { text: "实物礼品", value: 4 },
						         { text: "话费", value: 5 }
						       ]
					},
					{    
						title : "兑换码",   
						field : "convert_code", 
						width : "150px",
					},
					{    
						title : "兑换时间",   
						field : "crater_time", 
						width : "110px",
					},
					{    
						title : "城市合伙人",   
						field : "principal",   
					},
					{    
						title : "店铺",   
						field : "shop_name",   
					},
					{    
						title : "消费者账号",   
						field : "name",
						width: "100px"
					},
					{    
						title : "消费积分",   
						field : "convert_point",
						width : "100px",
					},
					{    
						title : "使用时间",   
						field : "convert_time",   
						width : "110px",
					},
					{    
						title : "使用状态",   
						field : "convert_state",  
						width : "100px",
						values: [
						         { text: "未使用", value:1},
						         { text: "已使用", value:2},
						         { text: "充值中", value:3}
						       ]
					},
					
					
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
    //初始化状态
    $scope.initState=function(){    
    	$("#state").kendoComboBox({
    		autoBind: false,
    		dataTextField: "name",
    		dataValueField: "id",
    		placeholder: "请选择",
    		filter: "contains",
    		dataSource: [{id:0,name:"全部"},{id:1,name:"未使用"},{id:2,name:"已使用"},{id:3,name:"充值中"}],
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
	$scope.initDateTime("convert_time");//初始化时间控件
    $scope.initExchangeType();//初始化兑换类型
    $scope.initState();//初始化加载状态
	//跳转添加积分商品页
	$scope.addIntegralsStore=function(){
		var params = {method:"add"};
		$location.path("/addIntegralsStore/"+angular.toJson(params));
	}
	//搜索
	$scope.search=function(){
		$scope.grid.dataSource.page(1);
	}
	//导出
	$scope.dataExport=function(){
		var param={
				product_title:$("#product_title").val(),
				convert_type:$("#convert_type").data("kendoComboBox").value(),
				state:$("#state").data("kendoComboBox").value(),
				name:$("#userName").val(),
				partner_name:$("#partner_name").val(),
				shop_name:$("#shop_name").val(),
				convert_time:$("#convert_time").val()
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "convert_module");  
	    
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
		var grid = $("#gridExhange").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"兑换管理列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridExhange").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}
})