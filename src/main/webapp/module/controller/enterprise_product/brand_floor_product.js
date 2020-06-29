App.controller("brandFloorProductCtrl1",function($scope, $rootScope, $location, $http, $compile,path,$routeParams) {
	
	var params = JSON.parse($routeParams.params);
	console.log("路由参数：",params);
	$scope.form={};
$scope.mainGridOptions = {
	dataSource : {
			transport : {
				read : {
					url : path+"/server/ent/floorlist",
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
								product_name:$("#title").val(),		 			//标题
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
				title : "材质",   
				field : "material",   
			},
			{    
				title : "面层工艺",   
				field : "craft_id",   
			},
			{    
				title : "厚度",   
				field : "thickness",   
			},{    
				title : "产地",   
				field : "madenin",   
			},{    
				title : "适用空间",   
				field : "space",   
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
					var template_html = ''; 
		    		if(params.method=="check"){
		    			template_html = '<div class="operation k-state-default" ng-click="check(\''+e.fb_product_id+'\')">查看详情</div>';
		    		}else{
		    			template_html='<div class="operation k-state-default" ng-click="edit(\''+e.fb_product_id+'\')">编辑</div>';
		    		}
		    		/*+'<div class="operation k-state-default" ng-click="copyUrl(\''+e.id+'\')">复制链接</div>';*/
		    		return template_html;
	            }
			}
			
	]
};
//初始化分类
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
                  url: path+"/server/ent/classifyseries",
                  dataType : "json"
              }
          },
          schema : {
				data : function(d) {
					return d;
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
$scope.initProvince();
$scope.initSeries();
$scope.check=function(id){
	var params = {method:"check",id:id};
	$location.path("/entaddBrandFloorProduct/"+angular.toJson(params));
}
$scope.edit=function(id){
	var params = {method:"edit",id:id};
	$location.path("/entaddBrandFloorProduct/"+angular.toJson(params));
}
//上架搜索
$scope.putawayData=function(num){
	$scope.form.state=num;
	$scope.search();
}	                        	
//下架搜索
$scope.soldOutData=function(num){
	$scope.form.state=num;
	$scope.search();
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

$scope.productInformation=function(){
	var params = {method:"add"};
	$location.path("/entaddBrandFloorProduct/"+angular.toJson(params));
}

});