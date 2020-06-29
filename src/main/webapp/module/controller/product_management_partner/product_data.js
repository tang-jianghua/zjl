App.controller("productDataForPartnerCtrl",function($scope, $rootScope, $location, $http, $compile,path, publicService) {
	
	
	//初始化品类
	$scope.initClassParent = function(containerId){
		$("#"+containerId).kendoDropDownList({
		    dataTextField: "class_name",
		    dataValueField: "class_id",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/getAllPartnerClassWithIntroState",
	                  dataType : "json"
	              }
	          },
	          schema : {
					data : function(d) {
						console.log(d);
						return d.result;
					}
	          }
	      }
		});
	}
	

	$scope.initClassParent("classParent");	//初始化品类
	
	
	$scope.mainGridOptions = {
			dataSource : {
					transport : {
						read : {
							url : path+"/server/querynewbrandproduct",
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
										state: "1",	//上架产品
										product_title: $scope.search.product_title,	//产品标题
										class_id: $("#classify").data("kendoComboBox").value(),//分类
										series_id: $("#series").data("kendoComboBox").value(), //系列
										class_parent_id: $("#classParent").data("kendoDropDownList").value(), //品类
										sales_model: $("#payModel").val(),//付款状态
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
				//allowUnsort: false,  //允许无序
			    mode: "single"     //排序模式：single，multiple
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
						template:"<input type='checkbox' id='#: id #' value='#: id #' class='productSign' />"+"<span class='row-number subTitle '></span>"
					},
					{    
						title : "缩略图",   
						field : "imgurl",   
						width : "120px",
						template:"<img src='#: imgurl #' style='height: 100px;'/>"
					},
					{    
						title : "标题",   
						field : "product_title",   
						width : "120px"    
					},
					{    
						title : "计价单位",   
						field : "product_unit",   
					},
					{    
						title : "适用空间",   
						field : "space",   
					},
					{    
						title : "风格",   
						field : "style",   
					},
					{    
						title : "是否为新品",   
						field : "isnew",   
						values: [
						         { text: "新品", value: 1 },
						         { text: "非新品", value: 2 }
						       ]
					},
					{    
						title : "付款模式",   
						field : "sales_model",   
						values: [
						         { text: "全款", value: 1 },
						         { text: "先定金后尾款", value: 2 }
						       ]
					},
					{    
						title : "单价",   
						field : "",   
						template:function(e){
							var html = '';
							
							html += e.min_price+'元---'+e.max_price+'元';
							
							return html;
						}
					},
					{    
						title : "分类",   
						field : "class_name"
					},
					{    
						title : "系列",   
						field : "series_name"
					}
			]
		};
	

	//搜索
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	
	
	//全选
	$scope.chooseAll = function(className,type){
		publicService.chooseAllByClassName(className,type);
	}
	
	//推荐到首页
	$scope.recommendToHome = function(){
		var productIdArr = publicService.getChooseValueByClassName("productSign");

		if(productIdArr.length==0){
			alert("请选择要推荐到首页的产品！");
			return;
		}
		
		var postData = {
				class_id: $("#classParent").data("kendoDropDownList").value(),
				product_id_array: productIdArr
			};

		if(confirm("是否确认添加到【品类推荐】？")){
			$http({
				   url: path+'/server/addIntroduceProduct',
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				alert(data.message);
				
				if(data.code==0){  //成功
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert("系统异常！");   
			})
		}
	}
	
		                        	
	
	publicService.initClassify();	//初始化【分类】
	publicService.initSeries();		//初始化【系列】
	publicService.initpayModel("payModel");	//初始化【付款模式】

});