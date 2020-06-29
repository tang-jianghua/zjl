/*
*  模块化懒加载
*/
App.constant("Modules_Config",[
    {	//高德地图
        name:"Map",
        module:true,
        files:[
            "../../plugins/map/main1119.css",
            "http://webapi.amap.com/maps?v=1.3&key=3705a223b2286f192d34ec178ca43747&plugin=AMap.Autocomplete,AMap.PlaceSearch"
        ]
    },
    {	//时间轴
        name:"TimeAxis",
        module:true,
        files:[
            "../../plugins/timeAxis/css/component.css",
            "../../plugins/timeAxis/js/modernizr.custom.js"
        ]
    },
    {//企业仓库产品管理
    	 name:"insertEnterpriseProduct",
         module:true,
         files:[
             "../../module/controller/product_controller/enterprise_controller/add_enterprise_product.js",
             "../../module/controller/product_controller/enterprise_controller/enterpriseEditorController.js",
             "../../module/controller/product_controller/enterprise_controller/enterpriseFundamentalController.js",
             "../../module/controller/product_controller/enterprise_controller/enterpriseProductAttributeController.js"
         ]
    }
    
    
    
    
])
.config(["$ocLazyLoadProvider","Modules_Config",moduleFunction]);

function moduleFunction($ocLazyLoadProvider,Modules_Config){
    $ocLazyLoadProvider.config({
        debug:false,
        events:false,
        modules:Modules_Config
    });
};