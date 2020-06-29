App.controller("entrepotProductCtrl",function($scope, $rootScope, $location, $http, $compile,path) {
	$scope.form={}
$scope.mainGridOptions = {
	dataSource : {
			transport : {
				read : {
					url : path+"/server/ent/devproductlist",
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
								materials_id:$("#ppcpIndustry").data("kendoComboBox").value(),//行业id
								class_id :$("#category").data("kendoComboBox").value(),//品类
								build_enterprise :$("#enterprise").data("kendoComboBox").value(),//企业
								/*classify_id :$("#classify").data("kendoComboBox").value(),//分类
								series_id :$("#series").data("kendoComboBox").value(),//系列
*/								product_name:$scope.form.product_name,
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
				width : "100px",
				template:"<input type='checkbox' id='#: id #' value='#: id #' />"
			},
			{         
				title : "标题",    
				field : "product_name",
				width : "200px"    
			},
			{    
				title : "缩略图",   
				field : "imgurl",   
				width : "200px", 
				template:'<img style="width: 120px;height: 100px;" src="#: imgurl #" />'
			},
			{    
				title : "企业",   
				field : "enterprise_name",   
			},
			{    
				title : "价格",   
				field : "",
				template:function(e){
					return e.min_price+"元--"+e.max_price+"元";
				}
			},
			{ 
				title : "操作",
				template:function(e){
		    		var template_html = '<button class="k-button k-state-default" ng-click="viewDetails(\''+e.id+'\',\''+e.class_id+'\')">查看详情</button>'
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
		$location.path("/entaddBrandProduct/"+angular.toJson(params));
		break;
	case 2://地板
		$location.path("/entaddBrandFloorProduct/"+angular.toJson(params));
		break;
	case 3://瓷砖
		$location.path("/entaddBrandTileProduct/"+angular.toJson(params));
		break;
	case 4://门
		$location.path("/entaddBrandDoorProduct/"+angular.toJson(params));
		break;
	case 5://涂料
		$location.path("/entaddBrandCoatingProduct/"+angular.toJson(params));
		break;
	case 6://卫浴及配件
		$location.path("/entaddBrandSwitchProduct/"+angular.toJson(params));
		break;
	case 7://橱柜及配件
		$location.path("/entaddBrandSwitchProduct/"+angular.toJson(params));
		break;
	case 8://开关
		$location.path("/addBrandSwitchProduct/"+angular.toJson(params));
		break;
	case 9://衣柜
		$location.path("/entaddBrandSwitchProduct/"+angular.toJson(params));
		break;
	}
}	                        	
//搜索
$scope.search=function(){
	$scope.grid.dataSource.online(true);
	$scope.grid.dataSource.read();
}

//导出
$scope.dataExport=function(){
	alert('导出！');
}

/*$scope.productInformation=function(){
	$location.path("/addBrandProduct");
}*/
//初始化分类
$scope.initProvince = function(){
	$("#classify").kendoComboBox({
	    dataTextField: "name",
	    dataValueField: "id",
	    autoBind: false,
	    placeholder: "分类",
	    filter: "contains",
	    dataSource: {
	      serverFiltering: false,
          transport: {
              read: {
            	  type : 'GET',
                  url: path+"/server/ent/queryclassifies",
                  dataType : "json"
              }
          },
          schema : {
				data : function(d) {
					console.log(d);
					return d.result;
				}
			},
      },
      change: function(e) {
    	    $scope.getSeries(this.value());
      }
	});
}
//获取系列
$scope.getSeries=function(provinceCode,defaultVal){
	$http({
		   url: path+"/server/ent/serieslistbyclassify/"+provinceCode,
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
}
//初始化行业
$scope.initIndustry = function(){
	$("#ppcpIndustry").kendoComboBox({
	    dataTextField: "name",
	    dataValueField: "id",
	    autoBind: false,
	    placeholder: "行业",
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
		 
      },
      change: function(e) {
		 $scope.getCategory(this.value());
			 
	    }
	});
}
//获取品类分类
$scope.getCategory = function(provinceCode,defaultVal){
	$http({
		   url: path+"/server/buildclassentitiesByBuildId/"+provinceCode,
		   method: 'GET'
	}).success(function(data){
		$scope.initCategory(data,defaultVal);
	}).error(function(data){
		console.log(data);   
	})
}

//初始品类分类
$scope.initCategory = function(cityData,defaultVal){
	if(cityData){
		var plugObj = $("#category").data("kendoComboBox");
		plugObj.value("");
		plugObj.setDataSource(cityData);
		if(defaultVal){
			plugObj.value(defaultVal);
		}
	}else{
		$("#category").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    autoBind: false,
		    placeholder: "品类",
		    filter: "contains",
		    dataSource: [],
		    change: function(e) {
		    	console.log(e);
	    	    var value = this.value();
	    	    console.log(value);
	    	    $scope.getEnterprise(value);
	      }
		});
	}
}

//获取企业数据
$scope.getEnterprise = function(cityCode,defaultVal){
	$http({
		   url: path+"/server/buildenterpriselistByClassId/"+cityCode,
		   method: 'GET'
	}).success(function(data){
		$scope.initEnterprise(data,defaultVal);
	}).error(function(data){
		console.log(data);   
	})
}

//初始化企业
$scope.initEnterprise = function(areaData,defaultVal){
	if(areaData){
		var plugObj = $("#enterprise").data("kendoComboBox");
		plugObj.setDataSource(areaData);
		if(defaultVal){
			plugObj.value(defaultVal);
		}
	}else{
		$("#enterprise").kendoComboBox({
			  dataTextField: "name",
			  dataValueField: "code",
			  placeholder: "企业",
			  filter: "contains",
			  dataSource: []
		});
	}
}

$scope.initProvince();//分类
$scope.initSeries();//系列

$scope.initIndustry();//行业
$scope.initCategory();//品类分类
$scope.initEnterprise();//企业
});