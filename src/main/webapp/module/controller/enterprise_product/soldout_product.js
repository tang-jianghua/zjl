App.controller("soldoutProduct1",function($scope, $rootScope, $location, $http, $compile,path) {
	$scope.form={};
	var num=0;
	var brand_id={title : "<input type='checkbox' />全选",field : "brand_id",width : "60px",
				template:"<input type='checkbox' id='#: id #' value='#: id #' />"};
	
	var imgurl={title : "缩略图",field : "imgurl",width : "120px",
				template:"<img src='#: imgurl #' style='height: 100px;'/>"};
	
	var product_name={title : "标题",field : "product_name",width : "120px"};
	
	var standard={title : "产品规格",field : "standard"};
	var craft_id={title : "面层工艺",field : "craft_id",};
	var haspicture={title : "有无图案",  field : "haspicture",values: [{ text: "有图", value: 1 },{ text: "无图", value: 2 }]};
	var style={title : "风格",field : "style"};
	var madenin={title : "产地",field : "madenin"};
	var space={title : "适用空间", field : "space"};
	
	var material={ title : "材质", field : "material"};
	var thickness={title : "厚度",field : "thickness"}
	
	var price={ title : "单价",template:function(e){return e.min_price+"元--"+e.max_price+"元";}};
	
	var main_material={titie:"材质",field : "main_material"};//瓷砖材质
	var size={ title : "大小", field : "size"};//瓷砖大小
	var texture={title : "纹理",field : "texture"};//瓷砖纹理
	
	var material={title : "材质分类",field : "material"};//门材质分类
	var door_craft_id={title : "饰面工艺",field : "craft_id"};//门饰面工艺
	var open_mode={title : "开合方式",field : "open_mode"}//门开合方式
	
	var gloss={ title : "涂料光泽", field : "gloss"};//涂料 涂料光泽
	var coating={title : "涂层类别", field : "coating"};//图层类别
	var ispalette={ title:"是否可调色",field:"ispalette",values:[{text: "不可调色", value:0 },{ text: "可调色", value: 1 }]};//是否可调色
	var spray_type={ title : "容量", field : "spray_type", values: [{ text: "手扫", value: 1 },{ text: "手摇", value: 2 }]};//容量
	
	var operation={ title : "操作",
					template:function(e){
						var template_html = '<div class="opration k-state-default" ng-click="edit('+e.product_id+')">编辑</div>'
						    +'<div class="opration k-state-default" ng-click="copyUrl(\''+e.id+'\')">复制链接</div>';
						return template_html;}
				    };
	var operation1={ title : "操作",
			template:function(e){
				var template_html = '<div class="opration k-state-default" ng-click="edit(\''+e.fb_product_id+'\')">编辑</div>'
				+'<div class="opration k-state-default" ng-click="copyUrl(\''+e.id+'\')">复制链接</div>';
				return template_html;}
	};
	var operation2={ title : "操作",
		template:function(e){
    		var template_html = '<div class="opration k-state-default" ng-click="edit(\''+e.db_product_id+'\')">编辑</div>'
			    +'<div class="opration k-state-default" ng-click="copyUrl(\''+e.id+'\')">复制链接</div>';
    		return template_html;
        }
	};
	var operation3={ title : "操作",
			template:function(e){
				var template_html = '<div class="opration k-state-default" ng-click="edit(\''+e.id+'\')">编辑</div>'
				+'<div class="opration k-state-default" ng-click="copyUrl(\''+e.id+'\')">复制链接</div>';
				return template_html;
			}
	};
	
	

$scope.getCurrentUserInfo = function(){
	$http({
		   url: path+'/server/getCurrentUserInfo',
		   method: 'GET'
	}).success(function(data){  
		console.info(data.enterpriseEntity.class_id);
		num=data.enterpriseEntity.class_id;
		var comm;
		if(data.enterpriseEntity.class_id==1){//壁纸
			comm=[brand_id,imgurl,product_name,standard,craft_id,haspicture,style,madenin,space,price,operation];
			$scope.initGrid(comm,"wallpaperlist");
		}else if(data.enterpriseEntity.class_id==2){//地板
			comm=[brand_id,imgurl,product_name,material,craft_id,thickness,madenin,space,price,operation1];
			$scope.initGrid(comm,"floorlist");
		}else if(data.enterpriseEntity.class_id==3){//瓷砖
			comm=[brand_id,imgurl,product_name,main_material,size,texture,style,space,price,operation1];
			$scope.initGrid(comm,"tilelist");
		}else if(data.enterpriseEntity.class_id==4){//门
			comm=[brand_id,imgurl,product_name,material,door_craft_id,open_mode,price,operation];
			$scope.initGrid(comm,"doorlist");
		}else if(data.enterpriseEntity.class_id==5){//涂料
			comm=[brand_id,imgurl,product_name,gloss,coating,ispalette,spray_type,price,operation2];
			$scope.initGrid(comm,"paintlist");
		}else if(data.enterpriseEntity.class_id==6){//开关
			comm=[brand_id,imgurl,product_name,price,operation3];
			$scope.initGrid(comm,"saleattrlist");
		}
		
	}).error(function(data){
		alert(data);   
	})
};

$scope.initGrid=function(comm,url){
	$("#grid").kendoGrid({
				dataSource : {
					transport : {
						read : {
							url : path+"/server/brand/"+url,
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
									state:"2",//产品状态
									/*sales_model: $("#wallpaper_payment_mode").data("kendoComboBox").value(),//付款模式
									enterprise_name: $scope.search.enterprise_name,	 	//企业
		*/							}	
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
		    mode: "multiple"     //排序模式：single，multiple
		  },
		
		editable: false,  //true：单个编辑；popup：弹出新页面编辑     
		columns : $compile(comm)($scope)
	});
}
/*
$scope.mainGridOptions = {
		
};*/
$scope.edit=function(id){
	var params = {method:"edit",id:id};
	if(num==1){
		$location.path("/addBrandProduct/"+angular.toJson(params));
	}else if(num==2){
		$location.path("/addBrandFloorProduct/"+angular.toJson(params));
	}else if(num==3){
		$location.path("/addBrandTileProduct/"+angular.toJson(params));
	}else if(num==4){
		$location.path("/addBrandDoorProduct/"+angular.toJson(params));
	}else if(num==5){
		$location.path("/addBrandCoatingProduct/"+angular.toJson(params));
	}else if(num==6){
		$location.path("/addBrandSwitchProduct/"+angular.toJson(params));
	}
	
}
$scope.getCurrentUserInfo();//区分品类
//搜索
$scope.search=function(){
	$scope.grid.dataSource.online(true);
	$scope.grid.dataSource.read();
}

//导出
$scope.dataExport=function(){
	alert('导出！');
}


});