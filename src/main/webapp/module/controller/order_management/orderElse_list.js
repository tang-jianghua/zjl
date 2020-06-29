App.controller("orderElseListCtrl",function($scope, $rootScope, $location, $http, $compile, path,$stateParams,$sce, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.list3_show = true;	//特权现金券订单(开发者，企业，合伙人)
	$scope.list4_show = true;	//卡券列表(开发者，企业，合伙人)
	
	var curSelectOrderType_url= ""
	if(params.orderType == 1)//定金接口
	{
		$("#list_"+2).addClass("choose");
		curSelectOrderType_url = path+"/server/selectOrdercashcouponNewList"
		//$("#list_"+1+"_container").show();
	}else if(params.orderType == 2)//现金券接口
	{
		$("#list_"+3).addClass("choose");
		
		curSelectOrderType_url = path+"/server/selectOrdercashcouponList"
		//$("#list_"+2+"_container").show();
	}
	
	
	$scope.details = {};  //定点列表
	//如果当前权限开发者，则显示城市合伙人和企业，如果当前为企业，则显示城市合伙人，如果当前为城市合伙人，则不显示城市合伙人搜索框
	$scope.userRole = {}; //显示企业
	
	//顶部3个按钮事件
	$scope.chooseList = function(type){
		var params={}
		if(type==1){
			$location.path("/orderList");
		}else if(type==2){
			params.orderType=1;
			$location.path("/orderElseList/"+angular.toJson(params));
		}else if(type==3){
			params.orderType=2;
			$location.path("/orderElseList/"+angular.toJson(params));
		}else if(type==4){
			$location.path("/cardList");
		}
	}
	//初始化搜索条件input及grid显示的模块数据
	$scope.initsearchIput = function(){
		//userInfo.user_type = "1"

		if(params.orderType == 2) //如果是现金券界面，不显示店铺信息
		{
			$scope.userRole.userEnterprisAndPartnerAndBrandAndShop_hidden_col = true;//店铺用户不显示所属店面列表 	
			$scope.userRole.isShowPromotionSearchInput = "show"    //不显示定金订单－活动名称输入框
		}else
		{
			$scope.userRole.userEnterprisAndPartnerAndBrandAndShop_hidden_col = false;//店铺用户不显示所属店面列表
			$scope.userRole.isShowPromotionSearchInput = "hidden" //不显示定金订单－活动名称输入框
		}
		
		if(userInfo.user_type=="0")
		{
			$scope.userRole.userPartner = "show";  //搜索框及关联信息显示
			$scope.userRole.userEnterpris = "show";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
		}else if(userInfo.user_type=="1")
		{
			$scope.userRole.userPartner = "show";  //搜索框及关联信息显示
			$scope.userRole.userEnterpris = "show";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
			}
		else if(userInfo.user_type== "2") //企业用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "show";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
		}
		else if(userInfo.user_type=="3")//城市合伙人用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
			if(params.orderType==2)//现金券订单 用户权限是城市合伙人 不显示关联信息
			{
				$scope.userRole.userEnterprisAndPartnerAndBrand_hidden_col= true;//grid关联信息整列不显示
			}
		}else if(userInfo.user_type=="4")//品牌用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "hidden";
			$scope.userRole.userEnterprisAndPartnerAndBrand_hidden_col= true;//grid关联信息整列不显示
			$scope.userRole.userShop = "show";
		}else if(userInfo.user_type=="5")//店铺用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "hidden";
			$scope.userRole.userEnterprisAndPartnerAndBrand_hidden_col= true;//grid关联信息整列不显示
			$scope.userRole.userShop = "hidden";
			$scope.userRole.userEnterprisAndPartnerAndBrandAndShop_hidden_col = true;//品牌用户不显示关联信息列表 	
		}
		if(params.orderType==2)//现金券订单 搜索框 不显示店面名称和品牌
		{
			$scope.userRole.userBrand = "hidden";
			$scope.userRole.userShop = "hidden";
		}
	}
	
	$scope.initsearchIput();            //初始化搜索条件input
	
	
	$scope.mainGridOptions = {

			dataSource : {
				transport : {
					read : {
						url : curSelectOrderType_url,
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
									promotion_name: $scope.search.promotion_name,	 	//活动名称
									enterprise_name:  $("#autoComeEnterprisName").data("kendoAutoComplete").value(),	 	//企业名称
									principal: $("#autoComPartnerName").data("kendoAutoComplete").value(),		 	//合伙人名称	
									brand_name: $("#autoComBrandName").data("kendoAutoComplete").value(),           //品牌名称
									shop_name:$("#autoComShopName").data("kendoAutoComplete").value(),		   		//店面名称
									customer_name:$scope.search.customer_name,	//买家名称
									order_code: $scope.search.order_code,		 	//订单编号
									start_time: $("#start_time").data("kendoDateTimePicker").value(),	//预约时间（始）
									end_time: $("#end_time").data("kendoDateTimePicker").value(),		//预约时间（止）
								}
							};
							console.log('查询参数',parameter);
							return kendo.stringify(parameter);
						}
					},
				},
				pageSize : 10,
		     	group: {
                     field: "code",
                     dir: "desc"  //倒序排列
                },
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
			
			columns : [
					{    
						title : "序号",
						field : "id", 
						width : "40px",
						template:"<input type='checkbox' class='store_order' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
			       	{
						field : "code",         
						title : "订单号",     
						width : "100px",
						hidden:true,//隐藏当前订单号列
						groupHeaderTemplate:function(e) {
							var html = '';
							var codeArr = e.value.split("_");	//当前code值为一个组合值，第一个为订单号，第二个为流水号，第三个为下单日期,第四个订单id，第五个订单状态,第六个总价
							
							if(codeArr[1]!= "%"){
								html += '<div style="float:left;padding-top:5px;">'
										 	+'订单号:'+codeArr[0]+'&nbsp;&nbsp;&nbsp;&nbsp;流水号:'+codeArr[1]
									   +'</div>';
							}else{
								html += '<div style="float:left;padding-top:5px;">'
										 	+'订单号:'+codeArr[0]
									   +'</div>';
							}
							
							return html;
					      }	
							
					},
					{
						field : "imgurl",         
						title : "产品",     
						width : "230px",
						template:function(e)
						{
							var temp = ""
							if(params.orderType == 2)
							{
								temp = '<li style="text-align:left;">活动名称:'+e.promotion_name+'</li>'
							}
							return '<div class="row" ><div class="form-group" style="overflow: hidden;margin-bottom: 0px;"><div class="col-md-4" style="">'+
							'<img  style="width: 120px;height: 120px;" src='+e.imgurl +' /></div>'+
							'<div class="col-md-5" style="padding-left: 60%;position: absolute;top: 50%;transform: translateY(-50%);">'+
	                        '<ul style="float:left;margin-bottom: 0px;" class="list-unstyled">'+
							temp+
							'<li style="text-align:left;">额度：'+e.cash_value +'</li>'+
							'</ul></div></div></div>'
						}
							
					
	
					},
					{
						
						field : "customer_name",         
						title : "买家",     
						width : "80px",
						template : "<p class='text-center'>#=customer_name#</p>"
					},
					{
						field :"order_price",    
						title : "金额",     
						width : "80px",  
						template : "<p class='text-center'>#=order_price#</p>"
					},
					{
						hidden:$scope.userRole.userEnterprisAndPartnerAndBrandAndShop_hidden_col,////如果是店铺用户，不显示所属店面 
						field : "shop_name",         
						title : "所属店面",     
						width : "80px",  
						template : "<p class='text-center'>#=shop_name#</p>"    
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "80px"
					},
					{
						field : "use_state",         
						title : "使用状态",     
						width : "80px",
						values: [
						         { text: "未支付", value: null },
						         { text: "未使用", value: 0 },
						         { text: "已使用", value: 1 }
				       ]
					},
					{
						field : "use_time",         
						title : "使用时间",     
						width : "80px"
					},
					{
						field : "product_order_code",         
						title : "产品订单",     
						width : "100px"
					},
					{
						field : "customer_pay_id",         
						title : "支付渠道",     
						width : "100px",
						values: [
						         { text: "未支付", value: null },
						         { text: "支付宝", value: 1 },
						         { text: "微信", value: 2 },
						         { text: "平台", value: 3 }
				       ]
					},
					{
						hidden: $scope.userRole.userEnterprisAndPartnerAndBrand_hidden_col,//如果是品牌用户，不显示关联信息   
						field : "shop_name",     
						title : "关联信息",     
						width : "140px",
						template:
                        '<ul style="float:left;margin-bottom: 0px;" class="list-unstyled">'+
						'<li class = "{{userRole.userPartner}}" >合伙人:#= principal #</li>'+		
						'<li class = "{{userRole.userEnterpris}} ">企业:#= enterpise_name #</li>'+
						'<li class = "{{userRole.userBrand}}" >品牌：#= brand_name #</li>'+
						'</ul>'
						
					},
					{
						field : "sell_name",  //目前没有       
						title : "推广导购",     
						width : "80px",  
						template :function(e)
						{
							 if(e.seller_name!=null){
								 return "<p class='text-center'>"+e.seller_name+"</p>"
								 }else{return ""}
						}		
					},
					{
						field : "trade_data",         
						title : "下单日期",     
						width : "80px",
						template : "<p class='text-center'>#=create_time#</p>"
					},
					
			]
		};
	
	
	
	//查询
	$scope.search=function(){
		$scope.grid.dataSource.page(1);
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
//	//初始化订单状态按钮
//	$scope.initStateBtnGroup = function(){
//		var mybtn = $('#stateBtnGroup :button');
//			mybtn.each(function(i){
//					$(this).click(function()
//					{
//						console.log("this  "+i)
//					}		
//				)
//			}		
//		);
//	}
	

	
	
//	//获取店面名称输入框提示列表
//	$scope.autoComShopNameloadData = new kendo.data.DataSource({
//		transport : {
//			read : {
//				url : path+"/server/shopname",
//				type : 'POST',
//				dataType : "json",
//				contentType : "application/json"
//			}
//		},
//		requestEnd: function(e) {
//		}
//	});
	//获取商铺名称输入框提示列表
	$("#autoComShopName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/shopname",
				type : 'POST',
				dataType : "json",
				contentType : "application/json"
			}
		},
		requestEnd: function(e) {
		}},
        filter: "startswith",
        placeholder: "输入名称／首字母搜索",
  
    });
	
	//获取品牌名称输入框提示列表
	$("#autoComBrandName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/brandname",
				type : 'POST',
				dataType : "json",
				contentType : "application/json"
			}
		},
		requestEnd: function(e) {
		}},
        filter: "startswith",
        placeholder: "输入名称／首字母搜索",
  
    });
	//获取合伙人名称输入框提示列表
	$("#autoComPartnerName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/partnername",
				type : 'POST',
				dataType : "json",
				contentType : "application/json"
			}
		},
		requestEnd: function(e) {
		}},
        filter: "startswith",
        placeholder: "输入名称／首字母搜索",
  
    });
	//获取企业名称输入框提示列表
	$("#autoComeEnterprisName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/enterprisename",
				type : 'POST',
				dataType : "json",
				contentType : "application/json"
			}
		},
		requestEnd: function(e) {
		}},
        filter: "startswith",
        placeholder: "输入名称／首字母搜索",
  
    });
	



	//$scope.initStateBtnGroup();		//初始订单状态按钮
	$scope.initDateTime("start_time");   //预约时间（始）
	$scope.initDateTime("end_time");   	 //预约时间（止）
	
	//导出
	$scope.dataExport=function(){
		var param={
				promotion_name: $scope.search.promotion_name,	 	//活动名称
				enterprise_name:  $("#autoComeEnterprisName").data("kendoAutoComplete").value(),	 	//企业名称
				principal: $("#autoComPartnerName").data("kendoAutoComplete").value(),		 	//合伙人名称	
				brand_name: $("#autoComBrandName").data("kendoAutoComplete").value(),           //品牌名称
				shop_name:$("#autoComShopName").data("kendoAutoComplete").value(),		   		//店面名称
				customer_name:$scope.search.customer_name,	//买家名称
				order_code: $scope.search.order_code,		 	//订单编号
				start_time: $("#start_time").data("kendoDateTimePicker").value(),	//预约时间（始）
				end_time: $("#end_time").data("kendoDateTimePicker").value(),		//预约时间（止）
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    if(params.orderType==1){
	    	my_input.attr('value', "shop_deposit_module"); 
	    }else if(params.orderType==2){
	    	my_input.attr('value', "privilege_coupon_module"); 
	    }
	    var my_input_page = $('<input type="text" name="page" />');  
	    my_input_page.attr('value', angular.toJson({page:1,pageSize:10,ispage:false,"param":param}));  
	    // 附加到Form  
	    form.append(my_input);  
	    form.append(my_input_page);  
	    console.log(form.serializeArray());
	    form.submit();  
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridOrderElse").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){
				obj.checked=false;
			});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){
				obj.checked=true;
			});
		}
	}
	//选中导出
	$scope.dataCheckboxExport=function(){
		var grid = $("#gridOrderElse").data("kendoGrid");
		var idData = publicService.getChooseValueByClassName('store_order');
		
		if(idData.length==0){
			alert('请选择要导出的订单！');
			return;
		}
		
		var param={
				ids:idData.toString(),
				promotion_name: $scope.search.promotion_name,	 	//活动名称
				enterprise_name:  $("#autoComeEnterprisName").data("kendoAutoComplete").value(),	 	//企业名称
				principal: $("#autoComPartnerName").data("kendoAutoComplete").value(),		 	//合伙人名称	
				brand_name: $("#autoComBrandName").data("kendoAutoComplete").value(),           //品牌名称
				shop_name:$("#autoComShopName").data("kendoAutoComplete").value(),		   		//店面名称
				customer_name:$scope.search.customer_name,	//买家名称
				order_code: $scope.search.order_code,		 	//订单编号
				start_time: $("#start_time").data("kendoDateTimePicker").value(),	//预约时间（始）
				end_time: $("#end_time").data("kendoDateTimePicker").value(),		//预约时间（止）
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    if(params.orderType==1){
	    	my_input.attr('value', "shop_deposit_module"); 
	    }else if(params.orderType==2){
	    	my_input.attr('value', "privilege_coupon_module"); 
	    }
	    var my_input_page = $('<input type="text" name="page" />');  
	    my_input_page.attr('value', angular.toJson({page:1,pageSize:10,ispage:false,"param":param}));  
	    // 附加到Form  
	    form.append(my_input);  
	    form.append(my_input_page);  
	    form.submit(); 
	    
	}
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		
	}else if(userInfo.user_type==2){  //企业
		
	}else if(userInfo.user_type==3){  //合伙人
		
	}else if(userInfo.user_type==4){  //品牌
		$scope.list3_show = false;
	}else if(userInfo.user_type==5){  //店铺
		$scope.list3_show = false;
		$scope.list4_show = false;
	}

});