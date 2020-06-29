/*添加页面基本信息controller*/
App.controller("fundamentalController",function($scope, $rootScope, $location, $http, $compile, $stateParams, path) {
	//初始化加载计价单位
    $scope.initValuationUnit=function(){    
    	$("#product_unit").kendoComboBox({
    		autoBind: false,
    		placeholder: "请选择",
    		filter: "contains",
    		dataSource: ["元/片","元/米","元/平米","元/件","元/延米","元/个","元/套","元/L","元/桶","元/卷","元/块","元/樘","元/组","元/片","元/立方米","元/立方厘米","元/盒","元/袋","元/g","元/kg","元/毫米","元/厘米","元/分米","元/毫升","元/扇","元/张"]
    	});
    }
  //初始化加载是否图案
    $scope.initSalesModel=function(){    
    	$("#sales_model").kendoComboBox({
    		dataTextField: "text",
    		dataValueField: "id",
    		autoBind: false,
    		placeholder: "请选择",
    		filter: "contains",
    		dataSource: [
		         {id: 1,text: "全款"},
		         {id: 2,text: "先定金后尾款"}
	         ],
	    	change: function(e) {
	        	var value = this.value();
	        	if(value==1){//全款
	        		$("#percent").val("");
	        		$("#percent").attr("readonly","readonly");
	        	}else if(value==2){//先定金后尾款
	        		$("#percent").val("");
	        		$("#percent").removeAttr("readonly");
	        	}
	        }
    	});
    },
    //初始化加载是否新品
	$scope.initNewProduct=function(){    
		$("#newProduct").kendoComboBox({
		    dataTextField: "text",
		    dataValueField: "id",
		    autoBind: false,
		    placeholder: "请选择",
		    filter: "contains",
		    dataSource: [
	             {id: 1,text: "是"},
	             {id: 2,text: "否"}
            ],
            change: function(e) {
            	var value = this.value();
            	if(value==1){//是新品
            		$("#start_time").data("kendoDatePicker").readonly(false);
            	}else if(value==2){//非新品
            		$("#start_time").data("kendoDatePicker").value("");
            		$("#start_time").data("kendoDatePicker").readonly(true);
            	}
	        }
		});
    },
     //初始化时间控件
	$scope.initDateTime = function(){
		$("#start_time").kendoDatePicker({
		     value: "",
		     culture: "de-DE",
		     format: "yyyy-MM-dd",   //事件格式
		     interval: 30,   				  //时间间隔
		     timeFormat: "HH:mm" 			  //24 hours format
		});
	}
    $scope.initNewProduct(); //初始加载是否新品
    $scope.initDateTime();   //初始化加载上新时间
    $scope.initSalesModel(); //初始化加载付款模式
    $scope.initValuationUnit(); //初始化加载计价单位
});

