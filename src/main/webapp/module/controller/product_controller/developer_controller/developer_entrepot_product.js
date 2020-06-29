App.controller("developerRepotProductCtrl",function($scope, $rootScope, $location, $http, $compile,path) {
	$scope.form={}
$scope.mainGridOptions = {
	dataSource : {
			transport : {
				read : {
					url : path+"/server/querynewentdevproduct",
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
								b_ent_id :$("#enterprise").data("kendoComboBox").value(),//品牌
								/*classify_id :$("#classify").data("kendoComboBox").value(),//分类
								series_id :$("#series").data("kendoComboBox").value(),//系列
*/								product_title:$scope.form.product_name,
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
					console.log(angular.toJson(d));
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
				field : "brand_id", 
				width : "80px",
				template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
			},
			{         
				title : "标题",    
				field : "product_title",
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
		    		var template_html = '<div class="operation k-state-default" ng-click="viewDetails(\''+e.id+'\')">查看详情</div>'
		    		return template_html;
	            }
			}
			
	]
};
//查看详情
$scope.viewDetails=function(id,categoryid){
	var params = {method:"check1",id:id};
	$location.path("/xqTrademarkProduct/"+angular.toJson(params));
}	                        	
//搜索
$scope.search=function(){
	$scope.grid.dataSource.page(1);
	//$scope.grid.dataSource.read();
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
		},
		change:function(e){
			 $scope.getEnterpriselist(this.value());
		}
	});
}
//查询品类
$scope.getEnterpriselist=function(provinceCode,defaultVal){
	$http({
		   url: path+"/server/buildclassentitiesByBuildId/"+provinceCode,
		   method: 'GET'
	}).success(function(data){
		$scope.initEnterpriselist(data,defaultVal);
	}).error(function(data){
		console.log(data);   
	})
}
//初始化品类
$scope.initEnterpriselist=function(classData,defaultVal){
	if(classData){
		console.log(defaultVal);
		var categoryObj = $("#category").data("kendoComboBox");
		categoryObj.value("");
		categoryObj.setDataSource(classData);
		if(defaultVal){
			categoryObj.value(defaultVal);
		}
	}else{
		$("#category").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    autoBind: false,
		    placeholder: "品类",
		    filter: "contains",
		    dataSource: [],
		    change:function(e){
		    	$scope.getBrandName(this.value());
		    }
		});
	}
}
//查询品牌
$scope.getBrandName=function(provinceCode,defaultVal){
	$http({
		   url: path+"/server/buildenterpriselistByClassId/"+provinceCode,
		   method: 'GET'
	}).success(function(data){
		console.log(data);
		$scope.initBrandName(data,defaultVal);
	}).error(function(data){
		console.log(data);   
	})
}
//初始化加载品牌
$scope.initBrandName=function(brandData,defaultVal){
	if(brandData){
		var plugObj = $("#enterprise").data("kendoComboBox");
		plugObj.value("");
		plugObj.setDataSource(brandData);
		if(defaultVal){
			plugObj.value(defaultVal);
		}
	}else{
		$("#enterprise").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    autoBind: false,
		    placeholder: "品牌",
		    filter: "contains",
		    dataSource: []
		});
	}
}

$scope.initProvince();//分类
$scope.initSeries();//系列

$scope.initIndustry();//行业
//初始化品类
$scope.initEnterpriselist();
//初始化加载品牌
$scope.initBrandName();

});