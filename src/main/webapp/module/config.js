App.config(['$routeProvider',function($routeProvider) {
	$routeProvider

	/********************************-----【账户管理】-----***********************/
	.when('/userAccount',
			{templateUrl:'../../views/account_management/user_account.html'})   //用户账户
			
	/********************************-----【数据统计】-----***********************/
	.when('/dataStatistics',
			{templateUrl:'../../views/data_statistics/data_statistics.html'})  //数据统计
			
	/********************************-----【企业信息】-----***********************/
	.when('/enterpriseBackInformation',
			{templateUrl:'../../views/enterprise_information/back_information.html'})   //后台信息	
	.when('/enterpriseAbout',
			{templateUrl:'../../views/enterprise_information/enterprise_about.html'})   //企业关于		
			
	/********************************-----【合伙人信息】-----***********************/
	.when('/partnerHomePageManagement',
			{templateUrl:'../../views/partner_information/homePage.html'})    		  //首页管理
	.when('/partnerContentManagement',
			{templateUrl:'../../views/column_management/content.html'})    		      //内容管理（同【栏目管理】：内容管理）																					  
	.when('/partnerBackInformation',
			{templateUrl:'../../views/partner_information/back_information.html'})    //后台信息		
			
			
	/********************************-----【品牌信息】-----***********************/
	.when('/brandBackInformation',
			{templateUrl:'../../views/brand_information/back_information.html'})        //后台信息
	.when('/brandInterfaceDecoration',
			{templateUrl:'../../views/brand_information/interface_decoration.html'})    //界面装修		
			
	/********************************-----【店铺信息】-----***********************/
	.when('/storeBackInformation',
			{templateUrl:'../../views/store_information/back_information.html'})      //后台信息		
			
	/********************************-----【栏目管理】-----***********************/
	.when('/interfaceManagement',
			{templateUrl:'../../views/column_management/interface.html'})  //界面管理
	.when('/contentManagement',
			{templateUrl:'../../views/column_management/content.html'})    //内容管理	
	.when('/headlineManagement',
			{templateUrl:'../../views/column_management/headline.html'})   //头条管理
	.when('/caseManagement',
			{templateUrl:'../../views/column_management/case.html'})       //案例管理
	.when('/openCityManagement',
			{templateUrl:'../../views/column_management/openCity.html'})   //开通城市管理
			
	/********************************-----【产品管理】-----***********************/
	.when('/brandProduct',
			{templateUrl:'../../views/product_management/brand_product.html'})      //开发者权限品牌产品
			
	.when('/brandWallpaperProduct/:params',
			{templateUrl:'../../views/product_management/brand_wallpaper_product.html'})      //壁纸产品数据
	.when('/addBrandProduct/:params',
			{templateUrl:'../../views/product_management/add_brand_product.html'})  	      //壁纸产品（详情）
	.when('/brandTileProduct/:params',
			{templateUrl:'../../views/product_management/brand_tile_product.html'})  		  //瓷砖产品数据
	.when('/addBrandTileProduct/:params',
			{templateUrl:'../../views/product_management/add_brand_tile_product.html'})  	  //新增瓷砖产品(详情)
	.when('/brandFloorProduct/:params',
			{templateUrl:'../../views/product_management/brand_floor_product.html'})  		  //地板产品数据
	.when('/addBrandFloorProduct/:params',
			{templateUrl:'../../views/product_management/add_brand_floor_product.html'})      //新增地板产品(详情)
	.when('/brandDoorProduct/:params',
			{templateUrl:'../../views/product_management/brand_door_product.html'})           //门产产品数据
	.when('/addBrandDoorProduct/:params',
			{templateUrl:'../../views/product_management/add_brand_door_product.html'})       //新增门产产品(详情)		
	.when('/brandCoatingProduct/:params',
			{templateUrl:'../../views/product_management/brand_coating_product.html'})              //涂料产品数据
	.when('/addBrandCoatingProduct/:params',
			{templateUrl:'../../views/product_management/add_brand_coating_product.html'})          //新增涂料产品(详情)		
	.when('/brandSanitaryFittingsProduct',
			{templateUrl:'../../views/product_management/brand_sanitary_fittings_product.html'})     //洁具产品数据
	.when('/addBrandSanitaryFittingsProduct',
			{templateUrl:'../../views/product_management/add_brand_sanitary_fittings_product.html'}) //新增洁具产品(详情)
	.when('/brandSwitchProduct/:params',
			{templateUrl:'../../views/product_management/brand_switch_product.html'})  //开关产品数据
	.when('/addBrandSwitchProduct/:params',
			{templateUrl:'../../views/product_management/add_brand_switch_product.html'}) //新增开关产品(详情)		
	.when('/brandCabinetProduct',
			{templateUrl:'../../views/product_management/brand_cabinet_product.html'})  //橱柜产品数据
	.when('/addBrandCabinetProduct',
			{templateUrl:'../../views/product_management/add_brand_cabinet_product.html'}) //新增橱柜产品(详情)		
			
	.when('/warehouseProduct',
			{templateUrl:'../../views/product_management/warehouse_product.html'})  //仓库产品		
	.when('/upshelfProduct',
			{templateUrl:'../../views/product_management/putaway_product.html'})  //上架产品	
	.when('/downshelfProduct',
			{templateUrl:'../../views/product_management/soldout_product.html'})  //下架产品
	.when('/newProductManagement',
			{templateUrl:'../../views/product_management/new_product_list.html'})  //新品管理	
			
	.when('/warehouseProductInformation',
			{templateUrl:'../../views/product_management/entrepot_product.html'})   //开发者仓库产品信息	
	.when('/productClassifySeries',
			{templateUrl:'../../views/product_management/product_line.html'})       //产品分类系列	
			
	.when('/warehouseProductClassify',
			{templateUrl:'../../views/product_management/product_classify.html'})   //仓库产品分类	
	.when('/productData',
			{templateUrl:'../../views/product_management/product_classify.html'})   //产品数据	
			
		/********************************-----【产品管理-企业】-----***********************/
	.when('/entbrandProduct',
			{templateUrl:'../../views/enterprise_product/brand_product.html'})      //品牌产品
	.when('/entproductClassify',
			{templateUrl:'../../views/enterprise_product/product_classify.html'})   //产品分类	
	.when('/entbrandWallpaperProduct/:params',
			{templateUrl:'../../views/enterprise_product/brand_wallpaper_product.html'})      //壁纸产品数据
	.when('/entaddBrandProduct/:params',
			{templateUrl:'../../views/enterprise_product/add_brand_product.html'})  	      //壁纸产品（详情）
	.when('/entbrandTileProduct/:params',
			{templateUrl:'../../views/enterprise_product/brand_tile_product.html'})  		  //瓷砖产品数据
	.when('/entaddBrandTileProduct/:params',
			{templateUrl:'../../views/enterprise_product/add_brand_tile_product.html'})  	  //新增瓷砖产品(详情)
	.when('/entbrandFloorProduct/:params',
			{templateUrl:'../../views/enterprise_product/brand_floor_product.html'})  		  //地板产品数据
	.when('/entaddBrandFloorProduct/:params',
			{templateUrl:'../../views/enterprise_product/add_brand_floor_product.html'})      //新增地板产品(详情)
	.when('/entbrandDoorProduct/:params',
			{templateUrl:'../../views/enterprise_product/brand_door_product.html'})           //门产产品数据
	.when('/entaddBrandDoorProduct/:params',
			{templateUrl:'../../views/enterprise_product/add_brand_door_product.html'})       //新增门产产品(详情)		
	.when('/entbrandCoatingProduct/:params',
			{templateUrl:'../../views/enterprise_product/brand_coating_product.html'})              //涂料产品数据
	.when('/entaddBrandCoatingProduct/:params',
			{templateUrl:'../../views/enterprise_product/add_brand_coating_product.html'})          //新增涂料产品(详情)		
	.when('/entbrandSwitchProduct/:params',
			{templateUrl:'../../views/enterprise_product/brand_switch_product.html'})  //开关产品数据
	.when('/entaddBrandSwitchProduct/:params',
			{templateUrl:'../../views/enterprise_product/add_brand_switch_product.html'}) //新增开关产品(详情)		
		
			
	.when('/entwarehouseProduct',
			{templateUrl:'../../views/enterprise_product/warehouse_product.html'})  //仓库产品		
	.when('/entupshelfProduct',
			{templateUrl:'../../views/enterprise_product/putaway_product.html'})  //上架产品	
	.when('/entdownshelfProduct',
			{templateUrl:'../../views/enterprise_product/soldout_product.html'})  //下架产品
	.when('/entnewProductManagement',
			{templateUrl:'../../views/enterprise_product/new_product_list.html'})  //新品管理	
			
	.when('/entwarehouseProductInformation',
			{templateUrl:'../../views/enterprise_product/entrepot_product.html'})   //仓库产品信息	
	.when('/entproductClassifySeries',
			{templateUrl:'../../views/enterprise_product/product_line.html'})       //产品分类系列	
			
	.when('/entwarehouseProductClassify',
			{templateUrl:'../../views/enterprise_product/product_classify.html'})   //仓库产品分类	
	.when('/entproductData',
			{templateUrl:'../../views/enterprise_product/product_classify.html'})   //产品数据
			
	/****************************-----[开发者产品管理]-------*************************/
	.when('/productClassify',
			{templateUrl:'../../views/product_management/product_classify.html'})   //开发者权限产品分类	
	.when('/developetrBrandProduct',
		{templateUrl:'../../views/product_views/developer_views/developer_brand_product.html'})//开发者权限品牌产品	
	.when('/xqTrademarkProduct/:params',
			{templateUrl:'../../views/product_views/developer_views/xq_trademark_product.html'})//开发者权限查看详情
		
	.when('/developerRepotProductCtrl',
			{templateUrl:'../../views/product_views/developer_views/developer_entrepot_product.html'})//开发者权限仓库产品信息
	/****************************-----[企业产品管理]-------*************************/	
	.when('/enterpriseBrandProductQuery',
			{templateUrl:'../../views/product_views/enterprise_views/enterprise_brand_product_query.html'})  //企业权限品牌产品数据		
	.when('/enterpriseBrandProduct',
		{templateUrl:'../../views/product_views/enterprise_views/enterprise_product.html'})  //企业权限仓库产品数据		
	.when('/addEnterpriseProduct/:params',
			{templateUrl:'../../views/product_views/enterprise_views/add_enterprise_product.html'})  //企业权限添加查看编辑页		
			
	/****************************-----[品牌产品管理]-------*************************/			
	.when('/trademarkBrandProduct',
		{templateUrl:'../../views/product_views/trademark_views/trademark_product.html'})  //品牌权限品牌产品数据		
	.when('/addTrademarkBrandProduct/:params',
			{templateUrl:'../../views/product_views/trademark_views/add_trademark_product.html'})  //品牌权限添加查看编辑页	
	.when('/trademarkUpshelfProduct',
			{templateUrl:'../../views/product_views/trademark_views/putaway_product.html'})  //上架产品	
	.when('/trademarkSoldoutProduct',
			{templateUrl:'../../views/product_views/trademark_views/soldout_product.html'})  //下架产品
	.when('/trademarkNewProduct',
			{templateUrl:'../../views/product_views/trademark_views/new_product_list.html'})  //新品管理	
	.when('/brandProductEnterpriseQuery',
			{templateUrl:'../../views/product_views/trademark_views/brand_product_enterprise_query.html'})  //仓库产品	
		
					
	/********************************-----【订单管理】-----***********************/
	.when('/orderList',
			{templateUrl:'../../views/order_management/order_list.html'})        //订单列表
	.when('/orderElseList/:params',
			{templateUrl:'../../views/order_management/orderElse_list.html'})   //优惠券或定金订单列表	
	.when('/evaluationCenter',
			{templateUrl:'../../views/order_management/evaluation_center.html'})         //评价中心
	.when('/brand/evaluationCenter',
			{templateUrl:'../../views/order_management/evaluation_center_brand.html'})   //评价中心(品牌)
	.when('/shop/evaluationCenter',
			{templateUrl:'../../views/order_management/evaluation_center_shop.html'})    //评价中心(店铺)
	
	/********************************-----【预约管理】-----***********************/
	.when('/brandBooking',
			{templateUrl:'../../views/booking_management/brand_booking.html'})         //品牌预约（开发者，企业，合伙人）	
	.when('/brandBookingForBrand',
			{templateUrl:'../../views/booking_management/brand_booking_brand.html'})   //品牌预约（品牌）		
	.when('/brandBookingForStore',
			{templateUrl:'../../views/booking_management/brand_booking_store.html'})   //品牌预约（店铺）		
	
	/********************************-----【活动管理】-----***********************/		
	.when('/platformActivity',
			{templateUrl:'../../views/activity_management/platform_activity.html'})         //平台活动
	.when('/addPlatformActivity/:params',
			{templateUrl:'../../views/activity_management/add_platform_activity.html'})   	//发布平台活动
	.when('/addSeckillActivity/:params',
			{templateUrl:'../../views/activity_management/add_seckill_activity.html'})   	//发布秒杀活动	
	.when('/activityCheck/:params',
			{templateUrl:'../../views/activity_management/activity_check.html'})   	//活动审核		
	.when('/productSet/:params',
			{templateUrl:'../../views/activity_management/product_set.html'})   		//产品设置	
	.when('/signProductDetails/:params',
			{templateUrl:'../../views/activity_management/sign_product_details.html'})  //报名产品详情					
	.when('/brandActivity',
			{templateUrl:'../../views/activity_management/brand_activity.html'})   			//品牌活动	
	.when('/addBrandActivity/:params',
			{templateUrl:'../../views/activity_management/add_brand_activity.html'})   		//添加活动(发布促销活动)			
	.when('/discountCardManagement',
			{templateUrl:'../../views/activity_management/discount_card_management.html'})  //优惠卡券管理
	.when('/addCardAndRedPackage/:params',
			{templateUrl:'../../views/activity_management/add_card_redPackage.html'})  		//设定卡券
			
	/********************************-----【后台用户】-----***********************/
	.when('/enterpriseInformation',
			{templateUrl:'../../views/back_user/enterprise_information.html'})   	   //企业信息
	.when('/addEnterpriseInformation/:params',
			{templateUrl:'../../views/back_user/add_enterprise_information.html'})     //添加企业账号信息
	.when('/cityPartnerInformation',
			{templateUrl:'../../views/back_user/city_partner_information.html'})   	   //城市合伙人信息
	.when('/addPartnerInformation/:params',
			{templateUrl:'../../views/back_user/add_partner_information.html'})        //新增合伙人信息
	.when('/brandInformation',
			{templateUrl:'../../views/back_user/brand_information.html'})   		   //品牌信息
	.when('/addBrandInformation/:params',
			{templateUrl:'../../views/back_user/add_brand_information.html'})      	   //新增品牌信息
	.when('/storeInformation',
			{templateUrl:'../../views/back_user/store_information.html'})   		   //店铺信息
	.when('/addStoreInformation/:params',
			{templateUrl:'../../views/back_user/add_store_information.html'})     	   //新增店铺信息		
			
	/********************************-----【内部员工管理】-----***********************/
	.when('/rolePermission',
			{templateUrl:'../../views/inside_employee_management/role_permission.html'})    //角色权限		
	.when('/employeeAccount',
			{templateUrl:'../../views/inside_employee_management/employee_account.html'})   //员工账号	
			 
	/********************************-----【人员管理】-----***********************/		
	.when('/promoterManagement12',
			{templateUrl:'../../views/person_management/promoter.html'})   	     //推广员管理（开发者，企业）	
	.when('/promotionGuiderManagement',
			{templateUrl:'../../views/person_management/promotionGuider.html'}) 	 //推广导购管理	
	.when('/promotionData/:params',
			{templateUrl:'../../views/person_management/promotionData.html'})    	 //(推广数据)	
	.when('/builderManagement',
			{templateUrl:'../../views/person_management/builder.html'})   	         //施工员管理	
	.when('/builderData/:params',
			{templateUrl:'../../views/person_management/builderData.html'})   	     //(施工数据)	
	.when('/builderDetails/:params',
			{templateUrl:'../../views/person_management/builderDetails.html'})   	     //(施工数据)		
			
			
	.when('/servicePersonManagement',
		{templateUrl:'../../views/person_management/servicePerson.html'})    		 //服务人员管理			
				
	/********************************-----【消费者管理】-----***********************/
	.when('/consumerList',
			{templateUrl:'../../views/customer_management/consumer_list.html'})   //消费者列表
			
	/********************************-----【帮助与反馈】-----***********************/
	.when('/problemContent',
			{templateUrl:'../../views/help_feedback/problem_content.html'})    //问题内容		
	.when('/feedbackContent',
			{templateUrl:'../../views/help_feedback/feedback_content.html'})   //反馈内容	
			

	.otherwise({redirectTo: '/'});
	
    //console.log($routeProvider,222);
}]);


