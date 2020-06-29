/*路由配置*/
App.config(["$stateProvider","$urlRouterProvider",routeFunction]);

function routeFunction($stateProvider,$urlRouterProvider){
	//其他页面
    $urlRouterProvider.otherwise("/");
    
    $stateProvider
    /********************************-----【账户管理】-----***********************/
    .state("userAccount",{	//用户账户
        url:"/userAccount",
        templateUrl:"../../views/account_management/user_account.html",
        //【当前controller定义】与【页面controller定义】二取一，否则会加载两次
        /*controller:"userAccountCtrl",	
        controllerAs:"userAccount",*/
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/account_management/user_account.js");
            }]
        } 
    })
    /********************************-----【数据统计】-----***********************/
    .state("dataStatistics",{	//数据统计
        url:"/dataStatistics",
        templateUrl:"../../views/data_statistics/data_statistics.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/data_statistics/data_statistics.js");
            }]
        } 
    })
    /********************************-----【企业信息】-----***********************/
    .state("enterpriseBackInformation",{	//后台信息	
        url:"/enterpriseBackInformation",
        templateUrl:"../../views/enterprise_information/back_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/enterprise_information/back_information.js");
            }]
        } 
    })
    .state("enterpriseAbout",{	//企业关于
        url:"/enterpriseAbout",
        templateUrl:"../../views/enterprise_information/enterprise_about.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                //return $ocLazyLoad.load("../../module/controller/enterprise_information/enterprise_about.js");
                
                return $ocLazyLoad.load({
                	files:[
                           "../../plugins/timeAxis/css/component.css",
                           "../../plugins/timeAxis/js/modernizr.custom.js",
                           "../../module/controller/enterprise_information/enterprise_about.js"
                       ]
                });

                /*return $ocLazyLoad.load("TimeAxis").then(
	                function(){
	                	return $ocLazyLoad.load("../../module/controller/enterprise_information/enterprise_about.js");
	                }
	    		);*/ 
            	
            }]
        } 
    })
    /********************************-----【合伙人信息】-----***********************/
    .state("partnerHomePageManagement",{	//首页管理
        url:"/partnerHomePageManagement",
        templateUrl:"../../views/partner_information/homePage.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/partner_information/homePage.js");
            }]
        } 
    })
    .state("partnerContentManagement",{	//内容管理（同【栏目管理】：内容管理）
        url:"/partnerContentManagement",
        templateUrl:"../../views/column_management/content.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/column_management/content.js");
            }]
        } 
    })
    .state("partnerBackInformation",{	//后台信息
        url:"/partnerBackInformation",
        templateUrl:"../../views/partner_information/back_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/partner_information/back_information.js");
            }]
        } 
    })
    /********************************-----【品牌信息】-----***********************/
    .state("brandBackInformation",{	//后台信息
        url:"/brandBackInformation",
        templateUrl:"../../views/brand_information/back_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/brand_information/back_information.js");
            }]
        } 
    })
    .state("brandInterfaceDecoration",{	//界面装修
        url:"/brandInterfaceDecoration",
        templateUrl:"../../views/brand_information/interface_decoration.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/brand_information/interface_decoration.js");
            }]
        } 
    })
    /********************************-----【店铺信息】-----***********************/
    .state("storeBackInformation",{	//后台信息
        url:"/storeBackInformation",
        templateUrl:"../../views/store_information/back_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/store_information/back_information.js");
            }]
        } 
    })
    /********************************-----【栏目管理】-----***********************/
    .state("interfaceManagement",{	//界面管理
        url:"/interfaceManagement",
        templateUrl:"../../views/column_management/interface.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/column_management/interface.js");
            }]
        } 
    })
    .state("contentManagement",{	//内容管理
        url:"/contentManagement",
        templateUrl:"../../views/column_management/content.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/column_management/content.js");
            }]
        } 
    })
    .state("headlineManagement",{	//头条管理
        url:"/headlineManagement",
        templateUrl:"../../views/column_management/headline.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/column_management/headline.js");
            }]
        } 
    })
    .state("caseManagement",{	//案例管理
        url:"/caseManagement",
        templateUrl:"../../views/column_management/case.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/column_management/case.js");
            }]
        } 
    })
    .state("openCityManagement",{	//开通城市管理
        url:"/openCityManagement",
        templateUrl:"../../views/column_management/openCity.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/column_management/openCity.js");
            }]
        } 
    })
    
    .state("linkSkipManagement",{	//链接跳转管理
        url:"/linkSkipManagement/:params",
        templateUrl:"../../views/column_management/linkSkip.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/column_management/linkSkip.js");
            }]
        } 
    })
    
    /********************************-----【文章专辑】-----***********************/
    .state("albumManagement",{	//专辑管理
        url:"/albumManagement",
        templateUrl:"../../views/album_management/album.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/album_management/album.js");
            }]
        } 
    })
    .state("evaluationManagement",{	//评价管理
        url:"/evaluationManagement",
        templateUrl:"../../views/album_management/evaluation.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/album_management/evaluation.js");
            }]
        } 
    })
    
    /********************************-----【开发者权限产品管理】-----***********************/
    
    .state("productClassify",{	//开发者产品分类
        url:"/productClassify",
        templateUrl:"../../views/product_management/product_classify.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/product_management/product_classify.js");
            }]
        } 
    })
    .state("developetrBrandProduct",{	//开发者品牌产品
    	url:"/developetrBrandProduct",
    	templateUrl:"../../views/product_views/developer_views/developer_brand_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/developer_controller/developer_brand_product.js");
    		}]
    	} 
    })
    .state("developerRepotProductCtrl",{	//开发者仓库产品信息
    	url:"/developerRepotProductCtrl",
    	templateUrl:"../../views/product_views/developer_views/developer_entrepot_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/developer_controller/developer_entrepot_product.js");
    		}]
    	} 
    })
    .state("xqTrademarkProduct",{	//开发者查看详情
    	url:"/xqTrademarkProduct/:params",
    	templateUrl:"../../views/product_views/developer_views/xq_trademark_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/developer_controller/xq_trademark_product.js");
    		}]
    	} 
    })
    /********************************-----【企业权限产品管理】-----***********************/
    .state("enterpriseBrandProductQuery",{	//企业权限品牌产品
    	url:"/enterpriseBrandProductQuery",
    	templateUrl:"../../views/product_views/enterprise_views/enterprise_brand_product_query.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/enterprise_controller/enterprise_brand_product_query.js");
    		}]
    	} 
    })
    .state("enterpriseBrandProduct",{	//企业权限仓库产品管理
    	url:"/enterpriseBrandProduct",
    	templateUrl:"../../views/product_views/enterprise_views/enterprise_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/enterprise_controller/enterprise_product.js");
    		}]
    	} 
    })
    .state("addEnterpriseProduct",{	//企业权限仓库产品添加编辑页
    	url:"/addEnterpriseProduct/:params",
    	templateUrl:"../../views/product_views/enterprise_views/add_enterprise_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load({
    				files:[
    			             "../../module/controller/product_controller/enterprise_controller/add_enterprise_product.js",
    			             "../../module/controller/product_controller/enterprise_controller/enterpriseEditorController.js",
    			             "../../module/controller/product_controller/enterprise_controller/enterpriseFundamentalController.js",
    			             "../../module/controller/product_controller/enterprise_controller/enterpriseProductAttributeController.js"
    			         ]
    			});
    		}]
    	} 
    })
    .state("entproductClassifySeries",{	//企业权限分类
    	url:"/entproductClassifySeries",
    	templateUrl:"../../views/product_views/enterprise_views/ent_classify_series.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/enterprise_controller/ent_classify_series.js");
    		}]
    	} 
    	/*templateUrl:"../../views/enterprise_product/product_line.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/enterprise_product/product_line.js");
    		}]
    	} */
    })
    /********************************-----【品牌权限产品管理】-----***********************/
    .state("productClassifySeries",{	//品牌分类
    	url:"/productClassifySeries",
    	templateUrl:"../../views/product_views/trademark_views/product_classify_series.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/trademark_controller/product_classify_series.js");
    		}]
    	}
    	/*templateUrl:"../../views/product_management/product_line.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_management/product_line.js");
    		}]
    	} */
    })
    .state("trademarkUpshelfProduct",{	//品牌上架产品
    	url:"/trademarkUpshelfProduct",
    	templateUrl:"../../views/product_views/trademark_views/putaway_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/trademark_controller/putaway_product.js");
    		}]
    	} 
    })
    .state("trademarkSoldoutProduct",{	//品牌下架产品
    	url:"/trademarkSoldoutProduct",
    	templateUrl:"../../views/product_views/trademark_views/soldout_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/trademark_controller/soldout_product.js");
    		}]
    	} 
    })
    .state("trademarkNewProduct",{	//品牌新品管理
    	url:"/trademarkNewProduct",
    	templateUrl:"../../views/product_views/trademark_views/new_product_list.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/trademark_controller/new_product_list.js");
    		}]
    	} 
    })
    .state("brandProductEnterpriseQuery",{	//品牌仓库产品
    	url:"/brandProductEnterpriseQuery",
    	templateUrl:"../../views/product_views/trademark_views/brand_product_enterprise_query.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/trademark_controller/brand_product_enterprise_query.js");
    		}]
    	} 
    })
    .state("trademarkBrandProduct",{	//品牌产品数据
    	url:"/trademarkBrandProduct",
    	templateUrl:"../../views/product_views/trademark_views/trademark_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/product_controller/trademark_controller/trademark_product.js");
    		}]
    	} 
    })
    .state("addTrademarkBrandProduct",{	//品牌产品数据添加编辑
    	url:"/addTrademarkBrandProduct/:params",
    	templateUrl:"../../views/product_views/trademark_views/add_trademark_product.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load({
    				files:[
    			             "../../module/controller/product_controller/trademark_controller/add_trademark_product.js",
    			             "../../module/controller/product_controller/trademark_controller/fundamentalController.js",
    			             "../../module/controller/product_controller/trademark_controller/editorController.js",
    			             "../../module/controller/product_controller/trademark_controller/productAttributeController.js"
    			         ]
    			});
    		}]
    	} 
    })
    
    /********************************-----【产品管理（合伙人）】-----***********************/
    .state("classRecommendForPartner",{	//品类推荐
        url:"/classRecommendForPartner",
        templateUrl:"../../views/product_management_partner/class_recommend.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/product_management_partner/class_recommend.js");
            }]
        } 
    })
    .state("productDataForPartner",{	//产品数据
        url:"/productDataForPartner",
        templateUrl:"../../views/product_management_partner/product_data.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/product_management_partner/product_data.js");
            }]
        } 
    })
 
    /********************************-----【订单管理】-----***********************/
    .state("orderList",{	//订单列表
        url:"/orderList",
        templateUrl:"../../views/order_management/order_list.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/order_management/order_list.js");
            }]
        } 
    })
    .state("orderElseList",{	//优惠券或定金订单列表
        url:"/orderElseList/:params",
        templateUrl:"../../views/order_management/orderElse_list.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/order_management/orderElse_list.js");
            }]
        } 
    })
    .state("cardList",{	//卡券列表
        url:"/cardList",
        templateUrl:"../../views/order_management/card_list.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/order_management/card_list.js");
            }]
        } 
    })
   .state("evaluationCenter",{	//评价中心
        url:"/evaluationCenter",
        templateUrl:"../../views/order_management/evaluation_center.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/fancyBox/source/fancybox.css",
    	       			   "../../plugins/fancyBox/source/fancybox.js",
    	       			   "../../plugins/fancyBox/lib/mousewheel-3.0.6.pack.js",
                           "../../module/controller/order_management/evaluationListController.js"
                       ]
                });
            }]
        } 
    })
    .state("orderClear",{	//订单结算
        url:"/orderClear",
        templateUrl:"../../views/order_management/order_clear.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/order_management/order_clear.js");
            }]
        } 
    })
    .state("orderClearDetails",{	//订单结算详情
        url:"/orderClearDetails/:params",
        templateUrl:"../../views/order_management/order_clear_details.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/order_management/order_clear_details.js");
            }]
        } 
    })

    /********************************-----【预约管理】-----***********************/
    .state("brandBooking",{	//品牌预约（开发者，企业，合伙人）
        url:"/brandBooking",
        templateUrl:"../../views/booking_management/brand_booking.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/fancyBox/source/fancybox.css",
    	       			   "../../plugins/fancyBox/source/fancybox.js",
    	       			   "../../plugins/fancyBox/lib/mousewheel-3.0.6.pack.js",
                           "../../module/controller/booking_management/brand_booking.js"
                       ]
                });
            }]
        } 
    })
    .state("brandBookingForBrand",{	//品牌预约（品牌）
        url:"/brandBookingForBrand",
        templateUrl:"../../views/booking_management/brand_booking_brand.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/fancyBox/source/fancybox.css",
    	       			   "../../plugins/fancyBox/source/fancybox.js",
    	       			   "../../plugins/fancyBox/lib/mousewheel-3.0.6.pack.js",
                           "../../module/controller/booking_management/brand_booking_brand.js"
                       ]
                });
            }]
        } 
    })
    .state("brandBookingForStore",{	//品牌预约（店铺）
        url:"/brandBookingForStore",
        templateUrl:"../../views/booking_management/brand_booking_store.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/fancyBox/source/fancybox.css",
    	       			   "../../plugins/fancyBox/source/fancybox.js",
    	       			   "../../plugins/fancyBox/lib/mousewheel-3.0.6.pack.js",
                           "../../module/controller/booking_management/brand_booking_store.js"
                       ]
                });
            }]
        } 
    })
    /********************************-----【活动管理】-----***********************/
    .state("platformActivity",{	//平台活动
        url:"/platformActivity",
        templateUrl:"../../views/activity_management/platform_activity.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/platform_activity.js");
            }]
        } 
    })
    .state("addPlatformActivity",{	//发布平台活动
        url:"/addPlatformActivity/:params",
        templateUrl:"../../views/activity_management/add_platform_activity.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/add_platform_activity.js");
            }]
        } 
    })
    .state("addSeckillActivity",{	//发布秒杀活动
        url:"/addSeckillActivity/:params",
        templateUrl:"../../views/activity_management/add_seckill_activity.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
            	return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/jeDate/jedate/skin/jedate.css",
    	       			"../../module/controller/activity_management/add_seckill_activity.js",
    	       			   "../../plugins/jeDate/jedate/jedate.js",
                           
                       ]
                });
            }]
        } 
    })
    .state("addDiscountActivity",{	//发布折扣活动
        url:"/addDiscountActivity/:params",
        templateUrl:"../../views/activity_management/add_discount_activity.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/add_discount_activity.js");
            }]
        } 
    })
    .state("activityCheck",{	//活动审核	
        url:"/activityCheck/:params",
        templateUrl:"../../views/activity_management/activity_check.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/activity_check.js");
            }]
        } 
    })
    .state("activitySign",{	//活动报名
        url:"/activitySign/:params",
        templateUrl:"../../views/activity_management/activity_sign.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/activity_sign.js");
            }]
        } 
    })
    .state("productSet",{	//产品设置
        url:"/productSet/:params",
        templateUrl:"../../views/activity_management/product_set.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/product_set.js");
            }]
        } 
    })
    .state("signProductDetails",{	//报名产品详情	
        url:"/signProductDetails/:params",
        templateUrl:"../../views/activity_management/sign_product_details.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/sign_product_details.js");
            }]
        } 
    })
    .state("brandActivity",{	//品牌活动	
        url:"/brandActivity",
        templateUrl:"../../views/activity_management/brand_activity.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/brand_activity.js");
            }]
        } 
    })
    .state("addBrandActivity",{	//添加活动(发布促销活动)
        url:"/addBrandActivity/:params",
        templateUrl:"../../views/activity_management/add_brand_activity.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/add_brand_activity.js");
            }]
        } 
    })
    .state("discountCardManagement",{	//优惠卡券管理	
        url:"/discountCardManagement",
        templateUrl:"../../views/activity_management/discount_card_management.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/discount_card_management.js");
            }]
        } 
    })
    .state("addCardAndRedPackage",{	//设定卡券	
        url:"/addCardAndRedPackage/:params",
        templateUrl:"../../views/activity_management/add_card_redPackage.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/dist/zTreeStyle/zTreeStyle2.css",
    	       			   "../../js/tools/ztree.js",
    	       			   "../../module/controller/activity_management/add_card_redPackage.js"
                       ]
                });
            }]
        } 
    })
    .state("cardExchangeManagement",{	//卡券兑换管理	
        url:"/cardExchangeManagement",
        templateUrl:"../../views/activity_management/card_exchange_management.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/card_exchange_management.js");
            }]
        } 
    })
    .state("discountManagement",{	//折扣管理	
        url:"/discountManagement",
        templateUrl:"../../views/activity_management/discount_management.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/discount_management.js");
            }]
        } 
    })
    .state("discountSet",{	//折扣设置	
        url:"/discountSet/:params",
        templateUrl:"../../views/activity_management/discount_set.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/activity_management/discount_set.js");
            }]
        } 
    })
    /********************************-----【后台用户】-----***********************/
    .state("enterpriseInformation",{	//企业信息
        url:"/enterpriseInformation",
        templateUrl:"../../views/back_user/enterprise_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/back_user/enterprise_information.js");
            }]
        } 
    })
    .state("addEnterpriseInformation",{	//添加企业账号信息
        url:"/addEnterpriseInformation/:params",
        templateUrl:"../../views/back_user/add_enterprise_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/back_user/add_enterprise_information.js");
            }]
        } 
    })
    .state("cityPartnerInformation",{	//城市合伙人信息
        url:"/cityPartnerInformation",
        templateUrl:"../../views/back_user/city_partner_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/back_user/city_partner_information.js");
            }]
        } 
    })
    .state("addPartnerInformation",{	//新增合伙人信息
        url:"/addPartnerInformation/:params",
        templateUrl:"../../views/back_user/add_partner_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/fancyBox/source/fancybox.css",
    	       			   "../../plugins/fancyBox/source/fancybox.js",
    	       			   "../../plugins/fancyBox/lib/mousewheel-3.0.6.pack.js",
                           "../../module/controller/back_user/add_partner_information.js"
                       ]
                });
            }]
        } 
    })
    .state("brandInformation",{	//品牌信息
        url:"/brandInformation",
        templateUrl:"../../views/back_user/brand_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/back_user/brand_information.js");
            }]
        } 
    })
    .state("addBrandInformation",{	//新增品牌信息
        url:"/addBrandInformation/:params",
        templateUrl:"../../views/back_user/add_brand_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/fancyBox/source/fancybox.css",
    	       			   "../../plugins/fancyBox/source/fancybox.js",
    	       			   "../../plugins/fancyBox/lib/mousewheel-3.0.6.pack.js",
                           "../../module/controller/back_user/add_brand_information.js"
                       ]
                });
            }]
        } 
    })
    .state("storeInformation",{	//店铺信息
        url:"/storeInformation",
        templateUrl:"../../views/back_user/store_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/back_user/store_information.js");
            }]
        } 
    })
    .state("addStoreInformation",{	//新增店铺信息
        url:"/addStoreInformation/:params",
        templateUrl:"../../views/back_user/add_store_information.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
            	return $ocLazyLoad.load("../../module/controller/back_user/add_store_information.js");
            	
            	/*return $ocLazyLoad.load("Map").then(
	                        function(){
	                        	return $ocLazyLoad.load("../../module/controller/back_user/add_store_information.js");
	                        }
                		);*/ 
            }]
        } 
    })
    /********************************-----【内部员工管理】-----***********************/
    .state("rolePermission",{	//角色权限
        url:"/rolePermission",
       /* templateUrl:"../../views/inside_employee_management/role_permission.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/inside_employee_management/role_permission.js");
            }]
        } */
        templateUrl:"../../views/inside_employee_management/role_jurisdiction.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/inside_employee_management/role_jurisdiction.js");
    		}]
    	} 
    })
    .state("employeeAccount",{	//员工账号
        url:"/employeeAccount",
        templateUrl:"../../views/inside_employee_management/employee_account.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/inside_employee_management/employee_account.js");
            }]
        } 
    })
    .state("roleJurisdiction",{	//角色权限测试版
    	url:"/roleJurisdiction",
    	templateUrl:"../../views/inside_employee_management/role_jurisdiction.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/inside_employee_management/role_jurisdiction.js");
    		}]
    	} 
    })
    .state("updateJurisdiction",{	//修改权限测试版
    	url:"/updateJurisdiction/:params",  
    	templateUrl:"../../views/inside_employee_management/update_jurisdiction.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/inside_employee_management/update_jurisdiction.js");
    		}]
    	} 
    })
    /********************************-----【人员管理】-----***********************/
   .state("promoterManagement",{	//推广员管理
        url:"/promoterManagement",
        templateUrl:"../../views/person_management/promoter.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/person_management/promoter.js");
            }]
        } 
    })
    
   .state("pusherManagement",{	//地推员管理
        url:"/pusherManagement",
        templateUrl:"../../views/person_management/pusher.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/person_management/pusher.js");
            }]
        } 
    })
    
   .state("promotionData",{	//地推员管理-推广数据
        url:"/promotionData/:params",
        templateUrl:"../../views/person_management/promotionData.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/person_management/promotionData.js");
            }]
        } 
    })
    
    .state("builderManagement",{	//施工员管理
        url:"/builderManagement",
        templateUrl:"../../views/person_management/builder.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/person_management/builder.js");
            }]
        } 
    })
    
    .state("builderData",{	//施工员管理-施工数据
        url:"/builderData/:params",
        templateUrl:"../../views/person_management/builderData.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/person_management/builderData.js");
            }]
        } 
    })
    
    .state("builderDetails",{	//施工员详情
        url:"/builderDetails/:params",
        templateUrl:"../../views/person_management/builderDetails.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/fancyBox/source/fancybox.css",
    	       			   "../../plugins/fancyBox/source/fancybox.js",
    	       			   "../../plugins/fancyBox/lib/mousewheel-3.0.6.pack.js",
                           "../../module/controller/person_management/builderDetails.js"
                       ]
                });
            }]
        } 
    })
    .state("servicePersonManagement",{	//服务人员管理
        url:"/servicePersonManagement",
        templateUrl:"../../views/person_management/servicePerson.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/person_management/servicePerson.js");
            }]
        } 
    })
    .state("guiderManagement",{	//导购员管理
        url:"/guiderManagement",
        templateUrl:"../../views/person_management/guider.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/person_management/guider.js");
            }]
        } 
    })
    .state("guiderDataManagement",{	//导购数据管理
        url:"/guiderDataManagement/:params",
        templateUrl:"../../views/person_management/guiderData.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/person_management/guiderData.js");
            }]
        } 
    })
    
    /********************************-----【消费者管理】-----***********************/
    .state("consumerList",{	//消费者列表
        url:"/consumerList",
        templateUrl:"../../views/customer_management/consumer_list.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/customer_management/consumer_list.js");
            }]
        } 
    })
    /********************************-----【帮助与反馈】-----***********************/
    .state("problemContent",{	//问题内容
        url:"/problemContent",
        templateUrl:"../../views/help_feedback/problem_content.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/help_feedback/problem_content.js");
            }]
        } 
    })
    .state("feedbackContent",{	//反馈内容
        url:"/feedbackContent",
        templateUrl:"../../views/help_feedback/feedback_content.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load({
                	files:[
    	       			   "../../plugins/fancyBox/source/fancybox.css",
    	       			   "../../plugins/fancyBox/source/fancybox.js",
    	       			   "../../plugins/fancyBox/lib/mousewheel-3.0.6.pack.js",
                           "../../module/controller/help_feedback/feedback_content.js"
                       ]
                });
            }]
        } 
    })
    /********************************-----【积分管理开发者】-----***********************/
    .state("developerIntegralsStore",{	//积分商城
        url:"/developerIntegralsStore",
        templateUrl:"../../views/integrals_manage/developer_integrals_manage/developer_integrals_store.html",
        resolve:{
            deps:["$ocLazyLoad",function($ocLazyLoad){
                return $ocLazyLoad.load("../../module/controller/integrals_manage/developer_integrals_manage/developer_integrals_store.js");
            }]
        } 
    })
    .state("addIntegralsStore",{	//添加积分商品
    	url:"/addIntegralsStore/:params",
    	templateUrl:"../../views/integrals_manage/developer_integrals_manage/add_integrals_store.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/integrals_manage/developer_integrals_manage/add_integrals_store.js");
    		}]
    	} 
    })
    .state("developerExchangeManage",{	//兑换管理
    	url:"/developerExchangeManage",
    	templateUrl:"../../views/integrals_manage/developer_integrals_manage/developer_exchange_manage.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/integrals_manage/developer_integrals_manage/developer_exchange_manage.js");
    		}]
    	} 
    })
    /********************************-----【积分管理店铺】-----***********************/
    .state("exchangeManage",{	//兑换管理
    	url:"/exchangeManage",
    	templateUrl:"../../views/integrals_manage/shop_integrals_manage/exchange_manage.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/integrals_manage/shop_integrals_manage/exchange_manage.js");
    		}]
    	} 
    })
    .state("exchangeCommodity",{	//兑换管理
    	url:"/exchangeCommodity",
    	templateUrl:"../../views/integrals_manage/shop_integrals_manage/exchange_commodity.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/integrals_manage/shop_integrals_manage/exchange_commodity.js");
    		}]
    	} 
    })
    /********************************-----【微信管理】-----***********************/
    .state("storeManagement",{	//门店管理
    	url:"/storeManagement",
    	templateUrl:"../../views/WeChat_management/store_management.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/WeChat_management/store_management.js");
    		}]
    	} 
    })
    .state("addStore",{	//新增门店
    	url:"/addStore/:params",
    	templateUrl:"../../views/WeChat_management/add_store.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/WeChat_management/add_store.js");
    		}]
    	} 
    })
    .state("deviceManagement",{	//设备管理
    	url:"/deviceManagement",
    	templateUrl:"../../views/WeChat_management/device_management.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/WeChat_management/device_management.js");
    		}]
    	} 
    })
    .state("pageManagement",{	//页面管理
    	url:"/pageManagement",
    	templateUrl:"../../views/WeChat_management/page_management.html",
    	resolve:{
    		deps:["$ocLazyLoad",function($ocLazyLoad){
    			return $ocLazyLoad.load("../../module/controller/WeChat_management/page_management.js");
    		}]
    	} 
    })
    
    
};
