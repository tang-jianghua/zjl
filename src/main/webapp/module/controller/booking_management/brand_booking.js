App.controller("brandBookingCtrl",function($scope, $rootScope, $location, $http, $compile, path, $sce, publicService) {
	
	$scope.details = {};  //预约详情
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querybrandappointment",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"desc",field:"s.create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								if(options.sort[0].field=="seller_num"){
									options.sort[0].field="temp2.seller_num"
								}else if(options.sort[0].field=="create_time"){
									options.sort[0].field="s.create_time"
								}
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									customer_name: $scope.search.customer_name,	 	//姓名
									shop_name: $("#shop_name").val(),		   	//预约店面
									brand_name: $("#brand_name").val(),		 	//预约品牌	
									province: $("#province").data("kendoComboBox").value(),		//省
									city: $("#city").data("kendoComboBox").value(),				//市
									county: $("#area").data("kendoComboBox").value(),			//区
									start_time: $("#start_time").val(),	//预约时间（始）
									end_time: $("#end_time").val(),		//预约时间（止）
									server_name: $scope.search.server_name,	 						//服务人员
								    state: $("#bookingState").data("kendoDropDownList").value(),	//预约状态
									//sort:sort
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
	        dataBound: function(e) {	//数据绑定成功
	        	var rows = this.items();
		        var page = this.pager.page() - 1;
		        var pagesize = this.pager.pageSize();
		        $(rows).each(function () {
		            var index = $(this).index() + 1 + page * pagesize;
		            var rowLabel = $(this).find(".row-number");
		            $(rowLabel).html(index);
		        });
	        	
		    	console.log("【Grid 数据绑定成功】");
		    	$('.fancybox').fancybox();
		    },	
			columns : [
					{    
						title : "序号",
						field : "brand_id", 
						width : "60px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{
						field : "door_model_img",         
						title : "户型图",     
						width : "160px",
						template: function(e){ 
							var html = ('<a class="fancybox" href="'+e.door_model_img+'" data-fancybox-group="gallery_'+e.id+'" title="户型图">'
											+'<img src="'+e.door_model_img+'" class="bafont"/>'
										+'</a>');
	
							return html;
						}
					},
					{
						field : "name",         
						title : "姓名",     
						width : "70px"    
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "100px"    
					},
					{
						field : "brand_name",         
						title : "预约品牌",     
						width : "100px"    
					},
					{
						field : "shop_name",         
						title : "预约店面",     
						width : "100px"    
					},
					{
						field : "create_time",         
						title : "提交时间",     
						width : "100px"
					},
					{
						field : "start_time",         
						title : "预约时间",     
						width : "100px"
					},
					{
						field : "server_name",         
						title : "服务人员",     
						width : "80px"    
					},
					{
						field : "state",         
						title : "预约状态",     
						width : "80px", 
						values: [
						         { text: "已提交", value: 1 },
						         { text: "已确认", value: 2 },
						         { text: "受理中", value: 3 },
						         { text: "已完成", value: 4 },
						         { text: "已取消", value: 5 },
						         { text: "无法受理", value: 6 }
				        ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div value='查看详情' class='operation k-state-default' ng-click='showDetails(\"#: id #\")' />查看详情</div>"
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
	
	//初始化时间控件
	$scope.initDateTime = function(containerId){
		$("#"+containerId).kendoDateTimePicker({
		     value: "",
		     culture: "de-DE",
		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
		     interval: 30,   				  //时间间隔
		     timeFormat: "HH:mm" 			  //24 hours format
		});
	}
	
	//显示详情
	$scope.showDetails = function(id){
		$http({
			   url: path+"/server/querybrandappdetail/"+id,
			   method: 'GET'
		}).success(function(data){
			console.log(data);
			if(data.code==0){  //成功
				var datails = data.result;
				
				if(datails){   //有详情信息
					//图片，预约姓名，预约品牌，联系方式，预约店面，预约地址
					$scope.details.door_model_img = datails.door_model_img;
					$scope.details.name = datails.name;
					$scope.details.brand_name = datails.brand_name;
					$scope.details.phone = datails.phone;
					$scope.details.shop_name = datails.shop_name;
					$scope.details.address_details = datails.address_details;
					//预约品类，预约时间，预约风格
					$scope.details.class_name = datails.class_name;
					$scope.details.start_time = datails.start_time+"~"+datails.end_time;
					$scope.details.app_style = datails.app_style;
					//预约空间，装修预算
					$scope.details.app_space = datails.app_space;
					$scope.details.min_price = datails.min_price;
					$scope.details.max_price = datails.max_price;
					//设计需求，预约进度
					$scope.details.needs = datails.needs;
					if(datails.ap_info){
						$scope.details.deal_info = $sce.trustAsHtml(datails.ap_info.replace(/;/g,"<br>").replace(/T/g,""));
					}else{
						$scope.details.deal_info = "";
					}

					$scope.booking_detail.center().open();   //打开弹框
				}else{
					alert("暂无详情信息！");
				}
				
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	
	publicService.initBrandName("brand_name");	//初始化【品牌名称】
	publicService.initShopName("shop_name");	//初始化【店铺名称】
	
	$scope.initProvince();			//初始化省
	$scope.initCity();				//初始化市
	$scope.initArea();				//初始化区
	$scope.initDateTime("start_time");   //预约时间（始）
	$scope.initDateTime("end_time");   	 //预约时间（止）
	
	publicService.initBookingState("bookingState");	//预约状态
	
	//导出
	$scope.dataExport=function(){
		var param={
				customer_name: $scope.search.customer_name,	 	//姓名
				shop_name: $("#shop_name").val(),		   	//预约店面
				brand_name: $("#brand_name").val(),		 	//预约品牌	
				province: $("#province").data("kendoComboBox").value(),		//省
				city: $("#city").data("kendoComboBox").value(),				//市
				county: $("#area").data("kendoComboBox").value(),			//区
				start_time: $("#start_time").val(),	//预约时间（始）
				end_time: $("#end_time").val(),		//预约时间（止）
				server_name: $scope.search.server_name,	 						//服务人员
			    state: $("#bookingState").data("kendoDropDownList").value(),	//预约状态
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "appointment_module");  
	    
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
		var grid = $("#gridBooking").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"品牌预约列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridBooking").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}

});