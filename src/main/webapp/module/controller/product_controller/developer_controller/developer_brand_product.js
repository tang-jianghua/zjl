/**
 * 开发者权限品牌产品查询页控制器
 */
App.controller("developetrBrandProductCtrl",function($scope, $rootScope, $location, $http, $compile,path,publicService) {

	$scope.form={};

$scope.mainGridOptions = {
	dataSource : {
			transport : {
				read : {
					url : path+"/server/querynewbranddevproduct",
					type : 'POST',
					dataType : "json",
					contentType : "application/json"
			    },
				parameterMap : function(options, operation) {
					if (operation == "read") {
						var sort = [{dir:"desc",field:"sell.sel_count"},{dir:"desc",field:"col.col_count"}];
						if(options.sort!=null && options.sort.length>0 ){
							sort=options.sort;
						}
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
								product_title:$("#product_name").val(),//标题
								sales_model:mode,//付款模式
								b_ent_id:$("#brand_name").val(),// 品牌
								state:$("#payment_state").val(),//状态
								min_quality:$("#serveGradeMin").val(),
								max_quality:$("#serveGradeMax").val(),
								min_service:$("#describeGradeMin").val(),
								max_service:$("#describeGradeMax").val(),
								sort:sort
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
				sortable: false,
				width : "80px",
				template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
			},
			{    
				title : "缩略图",   
				field : "imgurl",
				sortable: false,
				width : "120px",
				template:"<img src='#: imgurl #' style='height:100px;width:100px'/>"
			},
			{    
				title : "标题",   
				field : "product_title", 
				sortable: false,
				width : "120px"    
			},
			{    
				title : "服务评分",   
				field : "service", 
				sortable: false,
				template:function(e){ 
					var template_html='';
					if(e.service==""||e.service==null){
						template_html = '<span class="ng-binding">5</span>'
					}else{
						template_html = '<span class="ng-binding">'+e.service+'</span>'
					}
		    		return template_html;
	            }
			},
			{    
				title : "描述评分",   
				field : "quality",
				sortable: false,
				template:function(e){ 
					var template_html='';
					if(e.quality==""||e.quality==null){
						template_html = '<span class="ng-binding">5</span>'
					}else{
						template_html = '<span class="ng-binding">'+e.quality+'</span>'
					}
		    		return template_html;
	            }
			},
			{    
				title : "已售数量",   
				field : "sel_count", 
				template:function(e){ 
					var template_html='';
					if(e.sel_count==""||e.sel_count==null){
						template_html = '<span class="ng-binding">0</span>'
					}else{
						template_html = '<span class="ng-binding">'+e.sel_count+'</span>'
					}
		    		return template_html;
	            }
			},{         
				title : "收藏量",    
				field : "col_count",
				template:function(e){ 
					var template_html='';
					if(e.col_count==""||e.col_count==null){
						template_html = '<span class="ng-binding">0</span>'
					}else{
						template_html = '<span class="ng-binding">'+e.col_count+'</span>'
					}
		    		return template_html;
	            }
			},{    
				title : "省",  
				sortable: false,
				field : "pv_name",   
			},{    
				title : "市",   
				sortable: false,
				field : "city",   
			},{    
				title : "品牌", 
				sortable: false,
				field : "brand_name",   
			},{    
				title : "城市合伙人",   
				sortable: false,
				field : "principal",   
			},{    
				title : "付款模式",  
				sortable: false,
				field : "sales_model",  
				values: [
				         { text: "全款", value: 1 },
				         { text: "定金", value: 2 }
				       ]
			},{    
				title : "单价", 
				sortable: false,
				field : "update_time",   
				template:function(e){
					if(e.min_price==null||e.min_price==""){
						e.min_price=0;
					}
					if(e.max_price==null||e.max_price==""){
						e.max_price=0;
					}
					return e.min_price+"元--"+e.max_price+"元";
				}
			},
			{ 
				title : "操作",
				sortable: false,
				width : "110px" ,
				template:function(e){
		    		var template_html = '<div class="operation k-state-default" ng-click="viewDetails(\''+e.id+'\')">查看详情</div>'
		    		+'<div class="operation k-state-default" ng-click="copyLink(8,\''+e.id+'\')">复制链接</div>';
		    		return template_html;
	            }
			}
			
	]
};
//复制链接
$scope.copyLink = function(type,id){
	var url = publicService.buildLinkUrl(type,id);
	$scope.linkUrl = url;
	$scope.linkUrlWindow.center().open();   //打开弹框
}
//查看详情
$scope.viewDetails = function(id){
	var params = {method:"check",id:id};
	$location.path("/xqTrademarkProduct/"+angular.toJson(params));
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
//初始化行业
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
		var plugObj = $("#brand_name").data("kendoComboBox");
		plugObj.value("");
		plugObj.setDataSource(brandData);
		if(defaultVal){
			plugObj.value(defaultVal);
		}
	}else{
		$("#brand_name").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    autoBind: false,
		    placeholder: "品牌",
		    filter: "contains",
		    dataSource: []
		});
	}
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

//搜索
$scope.search=function(){
	$scope.grid.dataSource.page(1);
	//$scope.grid.dataSource.read();
}

//导出
$scope.dataExport=function(){
	alert('导出！');
}
//加载行业
$scope.initIndustry();
//初始化品类
$scope.initEnterpriselist();
//初始化加载品牌
$scope.initBrandName();

});