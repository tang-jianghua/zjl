App.controller("brandDoorProductCtrl",function($scope, $rootScope, $location, $http, $compile,path,$routeParams) {
	
	var params = JSON.parse($routeParams.params);
	console.log("路由参数：",params);
	$scope.form={};
	if(params.method=="check"){
		var buttonDelete=document.getElementById("addchangpin").parentNode;
        buttonDelete.removeChild(document.getElementById("addchangpin"));
        var buttonDelete1=document.getElementById("xiajia").parentNode;
        buttonDelete1.removeChild(document.getElementById("xiajia"));
        var buttonDelete2=document.getElementById("ppcpdelete").parentNode;
        buttonDelete2.removeChild(document.getElementById("ppcpdelete"));
	}
$scope.mainGridOptions = {
	dataSource : {
			transport : {
				read : {
					url : path+"/server/brand/doorlist",
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
								product_name: $("#wallpaper_title").val(),	//标题
								class_id:$("#classify").data("kendoComboBox").value(),//分类
								sales_model:$("#wallpaper_payment_mode").val(),//付款状态
								state:$("#wallpaper_state").val(),//产品状态
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
				template:"<input type='checkbox' class='ischeckbox' id='#: id #' value='#: id #' />"
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
				title : "材质分类",   
				field : "material",   
			},
			{    
				title : "饰面工艺",   
				field : "craft_id",   
			},
			{    
				title : "开合方式",   
				field : "open_mode",   
			},{    
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
				         { text: "定金", value: 2 }
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
					var template_html = ''; 
		    		if(params.method=="check"){
		    			template_html = '<div class="operation k-state-default" ng-click="check(\''+e.db_product_id+'\')">查看详情</div>';
		    		}else{
		    			template_html='<div class="operation k-state-default" ng-click="edit(\''+e.db_product_id+'\')">编辑</div>';
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
	$location.path("/addBrandDoorProduct/"+angular.toJson(params));
}
$scope.edit=function(id){
	var params = {method:"edit",id:id};
	$location.path("/addBrandDoorProduct/"+angular.toJson(params));
}
//修改上下架状态
$scope.setChecked=function(url){
	var idArray=[];
	$(".ischeckbox").each(function(){
		if($(this)[0].checked==true){
			idArray.push($(this)[0].value)
		}
	});
	$http({
		   url: path+"/server/"+url,
		   method: 'POST',
		   data:angular.toJson(idArray)
	}).success(function(data){
		if(data.code==0){
			alert("状态修改成功");
			 window.location.reload();
		}else{
			alert("状态修改失败");
		}
	}).error(function(data){
		console.log(data);   
	})
}
//上架修改
$scope.putawayData=function(){
	$scope.setChecked("brand/onsell");
}	                        	
//下架修改
$scope.soldOutData=function(){
	$scope.setChecked("brand/offsell");
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
	$location.path("/addBrandDoorProduct/"+angular.toJson(params));
}

});