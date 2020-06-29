App.controller("discountSetCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, $timeout, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.form = {};     			//表单
	$scope.productForm = {};		//产品设置表单
	
	var discountInfo = null;	//折扣信息
	
	
	//设置的产品列表
	$scope.setProductOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryZhekouPromotionPorduct",
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
									zhekou_id: params.discountId	 	//折扣工具id
								}
							};
							console.log('查询参数',parameter);
							return kendo.stringify(parameter);
						}
					},
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
				},
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
						field : "id", 
						width : "80px",
						template:"<input type='checkbox' class='selectProduct' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{
						field : "imgurl",         
						title : "产品图",     
						width : "100px",
						template:function(e){
							var html = '<img style="width:100px;height:100px;" src="'+e.imgurl+'" />';
							
							return html;
						}
					},
					{
						field : "product_title",         
						title : "标题",     
						width : "120px"
					},
					{
						field : "product_unit",         
						title : "计价单位",     
						width : "100px"
					},
					{
						field : "space",         
						title : "适用空间",     
						width : "100px"
					},
					{
						field : "style",         
						title : "风格",     
						width : "100px"
					},
					{
						field : "isnew",         
						title : "是否为新品",     
						width : "100px",
						values: [
						         {value:0, text:"否"},
						         {value:1, text:"是"}
						]
					},
					{
						field : "sales_model",         
						title : "付款模式",     
						width : "80px",
						values: [
						         {value:1, text:"全款"},
						         {value:2, text:"定金"}
						]
					},
					{
						field : "min_price",         
						title : "单价",     
						width : "100px",
						template:function(e){
							var html = e.min_price+'-'+e.min_price;
							
							return html;
						}
					}
			]
		};
	
	//未设置的产品列表
	$scope.unSetProductOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryAllZhekouPorductBrandToChooseByPage",
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
									product_name: $scope.productForm.product_name,	//标题
									classify_id : $("#classify").data("kendoComboBox").value(),	 	//分类
									series_id: $("#series").data("kendoComboBox").value(),	 		//系列
									id: params.discountId,	//折扣工具Id
								}
							};
							console.log('查询参数',parameter);
							return kendo.stringify(parameter);
						}
					},
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
				},
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
						field : "id", 
						title : "序号",
						width : "80px",
						template:"<input type='checkbox' class='unSelectProduct' value='#: id #' />"+"<span class='row-number subTitle '></span>"
						
					},
					{
						field : "product_name",         
						title : "标题",     
						width : "100px",
					},
					{
						field : "imgurl",         
						title : "缩略图",     
						width : "100px",
						template:function(e){
							var html = '<img style="width:100px;height:100px;" src="'+e.imgurl+'" />';
							
							return html;
						}
					},
					{
						field : "classify",         
						title : "所属分类",     
						width : "100px",
					},
					{
						field : "series",         
						title : "所属系列",     
						width : "100px",
					}
					
			]
		};
	
	//查询
	$scope.productSearch = function(){
		$scope.grid2.dataSource.page(1);
	}
	
	//初始化【参加产品】
	$scope.initProductRange = function(){
		$("#productRange").kendoDropDownList({
		    dataTextField: "value",
		    dataValueField: "key",
		    dataSource: [
		                 {key:"0",value:"部分商品"},
		                 {key:"1",value:"全部商品"}
             ]
		   
		});
	}
	
	//全选
	$scope.chooseAll = function(className,type){
		publicService.chooseAllByClassName(className,type);
	}
	
	//添加产品
	$scope.addProduct = function(){
		$scope.grid2.dataSource.page(1);
		$scope.productSetWindow.center().open();
	}
	
	//折扣工具添加
	$scope.formSubmit = function(){
		$scope.form.type = $("#discountType").data("kendoDropDownList").value();	//折扣类型
		$scope.form.start_time = $("#start_time").val();	//开始日期
		$scope.form.end_time = $("#end_time").val();		//结束日期
		$scope.form.isAllProduct = $("#productRange").data("kendoDropDownList").value();	//参加产品
		
		//表单验证
		if(!$scope.form.start_time){
			alert("请填写【开始日期】！");
			return;
		}else{
			$scope.form.start_time += " 00:00:00";
		}
		if(!$scope.form.end_time){
			alert("请填写【结束日期】！");
			return;
		}else{
			$scope.form.end_time += " 23:59:59";
		}
		if(!$scope.form.discount){
			alert("请填写【折扣比例】！");
			return;
		}else if($scope.form.discount<0 || $scope.form.discount>100){
			alert("【折扣比例】的范围应该在(0,100)之间！");
			return;
		}else{
			$scope.form.discount = $scope.form.discount/100;
		}
		if($scope.form.start_time>$scope.form.end_time){
			alert("【开始日期】应该小于等于【结束日期】！");
			return;
		}
		
		var url = '';
		if($scope.form.id){
			url = path+'/server/modifyZhekouPromotion';
		}else{
			url = path+'/server/addZhekouPromotion';
		}
	
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.form)
		}).success(function(data){
			alert(data.message);
			
			if(data.code==0){  //成功
				$location.path("discountManagement");
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("系统异常！");   
		})
	}
	
	//获取折扣信息
	$scope.getDiscountInfo = function(id){
		$http({
			   url: path+'/server/queryOneZhekouPromotion/'+id,
			   method: 'GET'
		}).success(function(data){
			discountInfo = data;
			
			if(discountInfo){
				$scope.form.id = discountInfo.id;
				$("#start_time").val(discountInfo.start_time);	//开始日期
				$("#end_time").val(discountInfo.end_time);		//结束日期
				$scope.form.discount = (discountInfo.discount*100);
				$("#productRange").data("kendoDropDownList").value(discountInfo.isAllProduct);	//参加产品
				$scope.form.content = discountInfo.content;	//使用说明
			}
			
		}).error(function(data){
			alert("系统异常！");   
		})
	}
	
	//产品提交
	$scope.productSubmit = function(){
		var postData = [];
		var productIdArr = publicService.getChooseValueByClassName("unSelectProduct");
		
		if(productIdArr.length<=0){
			alert("请选择要添加的产品！");
			return;
		}else{
			$.each(productIdArr, function(index, OneObj){
				var oneProduct = {
						zhekou_id: params.discountId,	//折扣工具Id
						product_id: OneObj				//产品Id
						};
				postData.push(oneProduct);
			});
			
			$http({
				   url: path+'/server/addZhekouPromotionPorduct',
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.productSetWindow.close();
					$scope.grid1.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert("系统异常！");   
			})
		}
	}
	
	//删除产品
	$scope.deleteProduct = function(){
		var productIdArr = publicService.getChooseValueByClassName("selectProduct");
		
		if(productIdArr.length<=0){
			alert("请选择要删除的产品！");
			return;
		}else{
			if(confirm("确认删除选中的产品？")){
				$http({
					   url: path+'/server/deleteZhekouPromotionPorduct',
					   method: 'POST',
					   data: angular.toJson(productIdArr)
				}).success(function(data){
					if(data.code==0){  //成功
						$scope.grid1.dataSource.page(1);
					}else if(data.code==1){  //失败
						alert(data.message);
					}
				}).error(function(data){
					alert("系统异常！");   
				})
			}
		}
	}
	
	
	
	
	
	publicService.initDiscountType("discountType");		//折扣类型
	publicService.initDate("start_time");   //活动日期（始）
	publicService.initDate("end_time");   	//活动日期（止）
	$scope.initProductRange();				//初始化【参加产品】
	
	publicService.initClassify();	//初始化【分类】
	publicService.initSeries();		//初始化【系列】
	
	$("#discountType").data("kendoDropDownList").readonly(true);
	
	if(params.method=="add"){
		
	}else if(params.method=="edit"){
		$scope.getDiscountInfo(params.discountId);
		
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		$("#productRange").data("kendoDropDownList").readonly(true);
	}else if(params.method=="productSet"){
		$scope.getDiscountInfo(params.discountId);
		
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		$("#discountContainer").find("input").attr("readonly","readonly");
		$("#productRange").data("kendoDropDownList").readonly(true);
		$("#discountContainer").find("textarea").attr("readonly","readonly");
		$("#formSubmitButton").hide();
		$("#productSetContainer").show();
	}else if(params.method=="productManagement"){
		$scope.getDiscountInfo(params.discountId);
		
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		$("#discountContainer").find("input").attr("readonly","readonly");
		$("#productRange").data("kendoDropDownList").readonly(true);
		$("#discountContainer").find("textarea").attr("readonly","readonly");
		$("#formSubmitButton").hide();
		$("#productSetContainer").show();
		$("#productSetOperate").hide();
	}
	
	

});