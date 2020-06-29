App.controller("brandProductCtrl",function($scope, $rootScope, $location, $http, $compile,path) {

	$scope.form={};

$scope.mainGridOptions = {
	dataSource : {
			transport : {
				read : {
					url : path+"/server/brand/devproductlist",
					type : 'POST',
					dataType : "json",
					contentType : "application/json"
			    },
				parameterMap : function(options, operation) {
					if (operation == "read") {
						var mode="";
						if($("#category").data("kendoComboBox").value()>5){
							mode="";
						}else{
							mode=$("#payment_mode").val();
						}
						
						var parameter = {
							page : options.page, //当前页
							pageSize : options.pageSize,
							param:{
								materials_id:$("#ppcp_industry").data("kendoComboBox").value(),//行业id
								/*classify:$("#classify").data("kendoComboBox").value(),//分类
								series_id:$("#series").data("kendoComboBox").value(),//系列
*/								class_id:$("#category").data("kendoComboBox").value(),//品类
								partner:$("#partner").val(),//城市合伙人
								product_name:$("#product_name").val(),//标题
								sales_model:mode,//付款模式
								brand_name:$("#brand_name").val(),// 品牌
								state:$("#payment_state").val()//状态
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
	    mode: "multiple"     //排序模式：single，multiple
	  },
	/*editable: {   //编辑
	    mode: "inline"   //整行编辑
	  },*/
	editable: false,  //true：单个编辑；popup：弹出新页面编辑     
	columns : [
			{    
				title : "<input type='checkbox' />全选",
				field : "brand_id", 
				width : "60px",
				template:"<input type='checkbox' id='#: id #' value='#: id #' />"
			},
			{    
				title : "缩略图",   
				field : "imgurl",   
				width : "120px",
				template:"<img src='#: imgurl #' style='height: 100px;'/>"
			},
			{    
				title : "标题",   
				field : "product_name",   
				width : "120px"    
			},
			{    
				title : "服务评分",   
				field : "service",   
			},
			{    
				title : "描述评分",   
				field : "quality",   
			},
			{    
				title : "已售数量",   
				field : "sel_count",   
			},{         
				title : "收藏量",    
				field : "col_count",
			},{    
				title : "省",   
				field : "province",   
			},{    
				title : "市",   
				field : "city",   
			},{    
				title : "品牌",   
				field : "brand_name",   
			},{    
				title : "城市合伙人",   
				field : "principal",   
			},{    
				title : "付款模式",   
				field : "sales_model",  
				values: [
				         { text: "全款", value: 1 },
				         { text: "定金", value: 2 }
				       ]
			},{    
				title : "单价",   
				field : "update_time",   
				template:function(e){
					return e.min_price+"元--"+e.max_price+"元";
				}
			},
			{ 
				title : "操作",
				width : "110px" ,
				template:function(e){
		    		var template_html = '<div class="operation k-state-default" ng-click="viewDetails(\''+e.id+'\',\''+e.class_id+'\')">查看详情</div>'
		    		return template_html;
	            }
			}
			
	]
};
//查看详情
$scope.viewDetails=function(id,categoryid){
	var params = {method:"check",id:id};
	var num=parseInt(categoryid)
	switch (num) {
	case 1://壁纸
		$location.path("/addBrandProduct/"+angular.toJson(params));
		break;
	case 2://地板
		$location.path("/addBrandFloorProduct/"+angular.toJson(params));
		break;
	case 3://瓷砖
		$location.path("/addBrandTileProduct/"+angular.toJson(params));
		break;
	case 4://门
		$location.path("/addBrandDoorProduct/"+angular.toJson(params));
		break;
	case 5://涂料
		$location.path("/addBrandCoatingProduct/"+angular.toJson(params));
		break;
	case 6://卫浴及配件
		$location.path("/addBrandSwitchProduct/"+angular.toJson(params));
		break;
	case 7://橱柜及配件
		$location.path("/addBrandSwitchProduct/"+angular.toJson(params));
		break;
	case 8://开关
		$location.path("/addBrandSwitchProduct/"+angular.toJson(params));
		break;
	case 9://衣柜
		$location.path("/addBrandSwitchProduct/"+angular.toJson(params));
		break;
	}
}

/*//初始化分类
$scope.initProvince = function(){
	$("#classify").kendoComboBox({
	    dataTextField: "name",
	    dataValueField: "id",
	    autoBind: false,
	    placeholder: "分类",
	    filter: "contains",
	    dataSource: {
	      serverFiltering: true,
          transport: {
              read: {
            	  type : 'GET',
                  url: path+"/server/brand/devqueryclassifies",
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
    	    $scope.getSeries(this.value());
      }
	});
}
$scope.getSeries=function(provinceCode,defaultVal){
	$http({
		   url: path+"/server/brand/brandserieslistbyclassify/"+provinceCode,
		   method: 'GET'
	}).success(function(data){
		$scope.initSeries(data.result,defaultVal);
	}).error(function(data){
		console.log(data);   
	})
}
//初始化系列
$scope.initSeries = function(seriesData,defaultVal){
	console.log(seriesData);
	if(seriesData){
		var plugObj = $("#series").data("kendoComboBox");
		plugObj.value("");
		plugObj.setDataSource(seriesData);
		if(defaultVal){
			plugObj.value(defaultVal);
		}
	}else{
		$("#series").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "code",
		    autoBind: false,
		    placeholder: "系列",
		    filter: "contains",
		    dataSource: []
		});
	}
}*/
//初始化加载kendoComboBox
$scope.initIndustry = function(){
	$("#ppcp_industry").kendoComboBox({
		dataTextField:"name",
		dataValueField:"id",
		autoBind: false,
		placeholder: "请选择",
		filter: "contains",
		dataSource: {
			serverFiltering: true,
			transport: {
				read: {
					type : 'GET',
					url: path+"/server/buildmaterialslist",
					dataType : "json"
				}
			},
			schema : {
				data : function(d) {
					return d;
				}
			},
		}
	});
}	        
//初始化加载
$scope.initCategory = function(){
	$("#category").kendoComboBox({
		dataTextField:"name",
		dataValueField:"id",
		autoBind: false,
		placeholder: "请选择",
		filter: "contains",
		dataSource: {
			serverFiltering: true,
			transport: {
				read: {
					type : 'GET',
					url: path+"/server/buildclassentities",
					dataType : "json"
				}
			},
			schema : {
				data : function(d) {
					return d;
				}
			},
		}
	});
}	        
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
//获取品牌名称输入框提示列表
$("#brandname").kendoAutoComplete({
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
//搜索
$scope.search=function(){
	$scope.grid.dataSource.online(true);
	$scope.grid.dataSource.read();
}

//导出
$scope.dataExport=function(){
	alert('导出！');
}
//加载行业
$scope.initIndustry();
//$scope.initProvince(); 
//$scope.initSeries();
$scope.initCategory();


});