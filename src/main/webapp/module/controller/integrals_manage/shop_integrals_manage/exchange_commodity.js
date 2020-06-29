App.controller("exchangeCommodity",function($scope, $rootScope, $location, $http, $compile, path) {
	
	$scope.mainGridOptions = {
			dataSource : {
					transport : {
						read : {
							url : path+"/server/querypoinmall",
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
							title : "库存",   
							field : "product_num",   
						},
						{    
							title : "商品兑换起止日期",   
							field : "start_time",  
							width:"240px",
							template:function(e){
								return e.start_time+"~"+e.end_time;
							}
						},
						{    
							title : "已兑换",   
							field : "convert_count",   
						},
						{    
							title : "兑换积分",   
							field : "point",   
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
						}
						
				]
		};
	
})