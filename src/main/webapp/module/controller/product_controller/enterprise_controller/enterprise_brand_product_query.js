App.controller("enterpriseBrandProductQuery",function($scope, $rootScope, $location, $http, $compile, path) {
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
								product_title:$("#product_title").val(),	//标题
								class_id:$("#classify").data("kendoComboBox").value(),//分类
								series_id:$("#series").data("kendoComboBox").value()//系列
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
				},{    
					title : "付款模式",   
					field : "sales_model",   
					values: [
					         { text: "全款", value: 1 },
					         { text: "先定金后尾款", value: 2 }
					       ]
				},{    
					title : "状态",   
					field : "state",   
					values: [
					         { text: "上架", value: 1 },
					         { text: "下架", value: 2 }
					       ]
				},{    
					title : "单价",   
					field : "",   
					template:function(e){
						return e.min_price+"元--"+e.max_price+"元";
					}
				},
				{ 
				title : "操作",
				template:function(e){
					template_html='<div class="operation k-state-default" ng-click="check(\''+e.id+'\')">查看详情</div>';
		    		return template_html;
	            }
			}
				
		]
};
///brand/queryclassifies 品牌分类
///brand/brandserieslistbyclassify/{classify_id} 品牌系列
//初始化分类
$scope.initProvince = function(){
	$("#classify").kendoComboBox({
	    dataTextField: "name",
	    dataValueField: "id",
	    autoBind: false,
	    placeholder: "请选择",
	    filter: "contains",
	    dataSource: {
	      serverFiltering: false,
          transport: {
              read: {
            	  type : 'GET',
                  url: path+"/server/brand/queryclassifies",
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
		    dataValueField: "id",
		    autoBind: false,
		    placeholder: "请选择",
		    filter: "contains",
		    dataSource: []
		});
	}
}
$scope.initProvince();//初始化分类
$scope.initSeries(); //初始化系列

$scope.check=function(id){
	var params = {method:"check",id:id};
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
//选中本页全部产品
$scope.checkAll=function(){
	var grid = $("#checkallgrid").data("kendoGrid");
	$.each(grid.tbody.find("input"), function(index, obj){
		obj.checked=true;
	});
}

});