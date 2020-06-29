App.controller("linkSkipCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, $sce, publicService) {
	var params = JSON.parse($stateParams.params);
	console.info("路由参数：",params);
	
	
	$scope.search1 = {};	//产品跳转搜索参数
	$scope.search3 = {};	//图文跳转搜索参数
	$scope.search4 = {};	//活动跳转搜索参数
	$scope.details = {};	//详情信息
	var columnData = null;	//栏目图标数据
	

	//选择列表
	$scope.chooseList = function(type){
		for(var i=1;i<=5;i++){
			if(i==type){
				$("#list"+i).addClass("choose");
				$("#container"+i).show();
			}else{
				$("#list"+i).removeClass("choose");
				$("#container"+i).hide();
			}
		}
	}
	
	//【产品跳转】列表
	$scope.mainGridOptions1 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querynewbrandproductforbranner",  
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
									materials_id: $("#industry").data("kendoComboBox").value(),	//行业
									class_id: $("#class").data("kendoComboBox").value(),		//品类
									b_ent_id: $("#brand").data("kendoComboBox").value(),		//品牌
									
									product_title: $scope.search1.title,	//标题
									partner: $scope.search1.partner,		//城市合伙人
									sales_model: $("#payModel").data("kendoDropDownList").value() //付款模式
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
			    mode: "single"     //排序模式：single，multiple
			  },
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
						template: function(e){
							if(params.linkType==8 && params.toId==e.id){
								return  "<input type='radio' name='radio_product' value='"+e.id+"' checked='true'/><span class='row-number subTitle'></span>";
							}else{
								return  "<input type='radio' name='radio_product' value='"+e.id+"' /><span class='row-number subTitle'></span>";
							}
						}
					},
					{
						field : "imgurl",         
						title : "产品图",     
						width : "100px",
						template:'<img style="width:100px;height:100px;" src="#: imgurl #"/>'
					},
					{
						field : "product_title",         
						title : "标题",     
						width : "100px"
					},
					{
						field : "class_name",         
						title : "品类",     
						width : "100px"
					},
					{
						field : "brand_name",         
						title : "品牌",     
						width : "100px"
					},
					{
						field : "principal",         
						title : "城市合伙人",     
						width : "100px"
					},
					{
						field : "sales_model",         
						title : "付款模式",     
						width : "100px",
						values: [
					         { text: "全款", value: 1 },
					         { text: "定金", value: 2 }
				        ]
					},
					{
						field : "min_price",         
						title : "单价",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += (e.min_price+"~"+e.max_price);
							
							return html;
						}
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += ('<div class="k-state-default operation" ng-click="showDetails(1,\''+e.id+'\')">查看详情</div>');
							
							return html;
						}

					}
			]
		};
	
	//【专辑跳转】列表
	$scope.mainGridOptions2 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryabum",  
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
									//name: $scope.search.cardName,         //名称
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
			    mode: "single"     //排序模式：single，multiple
			  },
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
						template: function(e){
							if((params.linkType==5 || params.linkType==6) && params.toId==e.id){
								return  "<input type='radio' name='radio_album' value='"+e.id+"' checked='true'/><span class='row-number subTitle'></span>";
							}else{
								return  "<input type='radio' name='radio_album' value='"+e.id+"' /><span class='row-number subTitle'></span>";
							}
						}
					},
					{
						field : "top_img_url",         
						title : "专辑头像",     
						width : "100px",
						template:'<img style="width: 185px;height: 69px;" src="#: top_img_url #"/>'
					},
					{
						field : "create_time",         
						title : "创建日期",     
						width : "100px"
					},
					{
						field : "update_time",         
						title : "更新日期",     
						width : "100px"
					},
					{
						field : "name",         
						title : "专辑名称",     
						width : "100px"
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += ('<div class="k-state-default operation" ng-click="showDetails(2,\''+e.id+'\')">查看详情</div>');
							
							return html;
						}

					}
			]
		};
	
	//【图文跳转】列表
	$scope.mainGridOptions3 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryheadcontent",  
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
									title: $scope.search3.title,      //标题
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
			    mode: "single"     //排序模式：single，multiple
			  },
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
						template: function(e){
							if(params.linkType==7 && params.toId==e.id){
								return  "<input type='radio' name='radio_content' value='"+e.id+"' checked='true'/><span class='row-number subTitle'></span>";
							}else{
								return  "<input type='radio' name='radio_content' value='"+e.id+"' /><span class='row-number subTitle'></span>";
							}
						}
					},
					{
						field : "title",         
						title : "文章标题",     
						width : "100px"
					},
					{
						field : "create_time",         
						title : "创建日期",     
						width : "100px"
					},
					{
						field : "abum_name",         
						title : "所属专辑",     
						width : "100px"
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += ('<div class="k-state-default operation" ng-click="showDetails(3,\''+e.id+'\')">查看详情</div>');
							
							return html;
						}
					}
			]
		};
	
	//【活动跳转】列表
	$scope.mainGridOptions4 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querypromotionforlink",  
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
									name: $scope.search4.title,    //标题
									type: $("#type_4").data("kendoDropDownList").value(),     //类型
									start_time: $("#startTime_4").val(),    //开始时间
									end_time: $("#endTime_4").val(),      	//结束时间
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
			    mode: "single"     //排序模式：single，multiple
			  },
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
						template: function(e){
							if(params.linkType==25 && e.type==1 && params.toId==e.id){
								return  "<input type='radio' name='radio_activity' value='"+e.id+"' activityType='"+e.type+"' checked='true'/><span class='row-number subTitle'></span>";
							}else if(params.linkType==29 && e.type==2 && params.toId==e.id){
								return  "<input type='radio' name='radio_activity' value='"+e.id+"' activityType='"+e.type+"' checked='true'/><span class='row-number subTitle'></span>";
							}else{
								return  "<input type='radio' name='radio_activity' value='"+e.id+"' activityType='"+e.type+"'/><span class='row-number subTitle'></span>";
							}
						}
					},
					{
						field : "name",         
						title : "活动标题",     
						width : "100px"
					},
					{
						field : "type",         
						title : "活动类型",     
						width : "100px",
						values: [
					         { text: "联盟", value: 1 },
					         { text: "秒杀", value: 2 }
				        ]
					},
					{
						field : "start_time",         
						title : "开始时间",     
						width : "100px"
					},
					{
						field : "end_time",         
						title : "结束时间",     
						width : "100px"
					},
					{
						field : "state",         
						title : "状态",     
						width : "100px",
						values: [
						         { text: "未开始", value: 0 },
						         { text: "进行中", value: 1 },
						         { text: "已结束", value: 2 }
					        ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							html += ('<div class="k-state-default operation" ng-click="showDetails(4,\''+e.id+'\',\''+e.type+'\')">查看详情</div>');
							
							return html;
						}
					}
			]
		};
	
	//获取栏目信息
	$scope.getColumnInfo = function(){
		$http({
			   url: path+'/server/querycolumnselection',
			   method: 'GET' 
		}).success(function(data){
			if(data.code==0){
				columnData = data.result;
				
				$scope.showColumnInfo(columnData,"columnContainer");
			}else if(data.code==1){
				alert("获取栏目图标信息失败！");
			}
		}).error(function(data){
			alert("获取栏目图标失败！");   
		})
	}
	
	//显示栏目图标
	$scope.showColumnInfo = function(dataArr,containerId){
		$.each(dataArr, function(index, OneObj){
			var html = '';
			var column_html = '';
			
			if(params.linkType==OneObj.type){
				column_html = '<input type="radio" name="radio_column" value="'+OneObj.type+'" title="'+OneObj.name+'" checked="true"/>'+OneObj.name;
			}else{
				column_html = '<input type="radio" name="radio_column" value="'+OneObj.type+'" title="'+OneObj.name+'"/>'+OneObj.name;
			}
	
			html += ('<div class="col-sm-2">'
						+'<div class="oneColumn">'
							+column_html
						+'</div>'
					+'</div>');
			
			html = $compile(html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).append(html);	
		});
	}
	
	//显示详情
	$scope.showDetails = function(type,id,dataType){
		var url = '';
		if(type==1){          //产品
			url = path+'/server/getnewbrandproductbrannerbyid/'+id;
		}else if(type==2){	  //专辑
			url = path+'/server/getabumbyid/'+id;
		}else if(type==3){	  //图文
			url = path+'/server/getheadcontentbyid/'+id;
		}else if(type==4){    //活动
			if(dataType==1){	//联盟活动
				url = path+'/server/queryunionpromotiondetail/'+id;
			}else if(dataType==2){	//秒杀活动
				url = path+'/server/getplatformpromotionbyid/'+id;
			}
		}
		
		$http({
			   url: url,
			   method: 'GET' 
		}).success(function(data){
			if(data.code==0 || data.code==undefined){  //成功
				if(type==1){          //产品
					$scope.showProductDetails(data.result);
				}else if(type==2){	  //专辑
					$scope.showAlbumDetails(data.result);
				}else if(type==3){	  //图文
					$scope.showContentDetails(data.result);
				}else if(type==4){    //活动
					if(dataType==1){	//联盟活动
						$scope.showUnionActivityDetails(data.result);
					}else if(dataType==2){	//秒杀活动
						$scope.showSeckillActivityDetails(data);
					}
				}
			}
		}).error(function(data){
			alert("获取信息失败！");   
		})
	}
	
	//显示图片
	$scope.showPicture = function(id,url){
		$("#"+id).css({
			"background": "url('"+url+"') no-repeat",
    	    "background-size":"contain",
    	    "background-position":"center",
    	    "margin":"auto",
    	});
	}
	
	//显示产品详情
	$scope.showProductDetails = function(details){
		$scope.details = {};
		
		//产品展示图
		$.each(details.baseBrandProdImgEntities, function(index, OneObj){
			if(OneObj.type==1){
				var id = "productImage_"+(index+1);
				$scope.showPicture(id,OneObj.imgurl);
			}
		});
		$scope.details.product_title = details.product_title;	//产品标题
		//是否新品
		if(details.isnew==1){
			$scope.details.isnew = "新品";
		}else if(details.isnew==2){
			$scope.details.isnew = "非新品";
		}
		$scope.details.product_unit = details.product_unit;		//计价单位
		//付款模式
		if(details.sales_model==1){
			$scope.details.sales_model = "全款";
		}else if(details.sales_model==2){
			$scope.details.sales_model = "定金";
		}
		//付款百分比
		if(details.percent){
			$scope.details.percent = details.percent+"%";
		}else{
			$scope.details.percent = "";
		}
		//所属分类
		$scope.details.seriesName = '';
		$.each(details.baseBrandProdSeriesEntities, function(index, OneObj){
			$scope.details.seriesName += (OneObj.brandSeriesEntity.name+" ");
		});	
		$.each(details.productViewAttrAndVaule, function(index, OneObj){
			if(OneObj.ca_name=="适用空间"){		//适用空间
				$scope.details.space = OneObj.attr_values;
			}else if(OneObj.ca_name=="风格"){	//风格
				$scope.details.style = OneObj.attr_values;
			}
		});

		$scope.productDetailsWindow.center().open();   //打开弹框
	}
	
	//显示专辑详情
	$scope.showAlbumDetails = function(details){
		$scope.details = {};
		
		$scope.details.name = details.name;					//专辑名称
		$scope.showPicture("albumImage",details.top_img_url);	//专辑图像
		
		$scope.albumDetailsWindow.center().open();   //打开弹框
	}
	
	//显示图文详情
	$scope.showContentDetails = function(details){
		$scope.details = {};

		$scope.showPicture("contentImage",details.image);	//缩略图
		$scope.details.abum_name = details.abum_name;	//所属专辑	
		$scope.details.title = details.title;	//文章标题
		$scope.details.digest = details.digest;	//文章概述
		$scope.details.author = details.author;	//文章作者
		$scope.details.create_time = details.create_time;	//发布日期
		$scope.details.details = $sce.trustAsHtml(details.details);	//图文详情
		
		$scope.contentDetailsWindow.center().open();   //打开弹框
	}
	
	//显示联盟活动详情
	$scope.showUnionActivityDetails = function(details){
		$scope.details = {};

		$scope.showPicture("unionActivityImage",details.image);	//封面图
		
		$scope.details.name = details.name;	//活动名称
		$scope.details.rule = details.rule;	//活动规则
		
		$scope.details.type = "订金券";	//活动类型
		$scope.details.title = details.title;	//订金劵名称
		$scope.details.cash_value = details.cash_value;	//额度
		$scope.details.price = details.price;	//价格
		$scope.details.count = details.count;	//发布数量
		$scope.details.discount = details.discount;	//优惠折扣
		
		$scope.details.start_time = details.start_time;	//开始时间
		$scope.details.end_time = details.end_time;	//结束时间
		$scope.details.apply_end_time = details.apply_end_time;	//报名截止时间
		//商品范围
		if(details.total_flag==0){
			$scope.details.productRange = "部分商品";
		}else if(details.total_flag==1){
			$scope.details.productRange = "全部商品";
		}
		//参与品牌	
		$scope.details.joinBrand = '';
		$.each(details.brandArray, function(index, OneObj){
			$scope.details.joinBrand += OneObj.brand_name+"&nbsp;&nbsp;&nbsp;&nbsp;";
		});
		$scope.details.joinBrand = $sce.trustAsHtml($scope.details.joinBrand);
		
		$scope.unionActivityDetailsWindow.center().open();   //打开弹框
	}
	
	//显示秒杀活动详情
	$scope.showSeckillActivityDetails = function(details){
		$scope.details = {};

		$scope.showPicture("seckillActivityImage",details.imgurl1);	//活动宣传图
		$scope.details.title = details.title;		//活动名称
		//优惠限购
		if(details.person_product_num>0 && details.person_product_num!=99999){
			$scope.details.isLimit = "每人限购"+details.person_product_num+"个";
		}else if(details.person_product_num==99999){
			$scope.details.isLimit = "不限购";
		}
		$scope.details.limit_time = details.limit_time;	//报名截止日期
		$scope.details.promotion_product_num = "每个品牌限"+details.promotion_product_num+"款产品";	//报名数量
		$scope.details.start_time = details.start_time;	//开始时间
		$scope.details.end_time = details.end_time;		//结束时间
		//时间段
		$scope.details.timeRange = '';
		$.each(details.timeEntity, function(index, OneObj){
			$scope.details.timeRange += OneObj.pstart_time_str+"~"+OneObj.pend_time_str+"&nbsp;&nbsp;&nbsp;&nbsp;";
			
		});
		$scope.details.timeRange = $sce.trustAsHtml($scope.details.timeRange);
		
		$scope.details.principal = details.principal;	//活动宣传图
		$scope.details.class_name = details.class_name;	//参与合伙人
		
		$scope.seckillActivityDetailsWindow.center().open();   //打开弹框
	}
	
	
	
	
	
	
	//搜索
	$scope.search = function(type){ 
		if(type==1){
			$scope.grid1.dataSource.page(1);
		}else if(type==2){
			
		}else if(type==3){
			$scope.grid3.dataSource.page(1);
		}else if(type==4){
			$scope.grid4.dataSource.page(1);
		}else if(type==5){
			
		}
	}
	
	//确定
	$scope.submit = function(type){ 
		var url = '';	//路径
		var toId = null;		//记录Id
		var jumpType = null;    //跳转类型

		if(type==1){		//产品
			toId = $('input[name="radio_product"]:checked').val();
			jumpType = 8;
		}else if(type==2){	//专辑
			toId = $('input[name="radio_album"]:checked').val();
			
			if(params.method=="albumPictureSkip"){	//专栏图（开发者）
				jumpType = 6;
			}else{
				jumpType = 5;
			}
		}else if(type==3){	//图文
			toId = $('input[name="radio_content"]:checked').val();
			jumpType = 7;
		}else if(type==4){	//活动
			toId = $('input[name="radio_activity"]:checked').val();
			var activityType = $('input[name="radio_activity"]:checked').attr("activityType");
			if(activityType==1){
				jumpType = 25;	//联盟活动
			}else if(activityType==2){
				jumpType = 29;	//秒杀活动
			}
		}else if(type==5){	//栏目
			jumpType = $('input[name="radio_column"]:checked').val();
		}
		
		var postData = {
				id: params.fromId,
				link_type: jumpType,
				data_param: toId
		};
		
		if(params.method=="bannerSkip1" || params.method=="bannerSkip3"){	//Banner跳转（开发者，合伙人）
			url = path+'/server/modfiybanner';
		}else if(params.method=="bannerSkip4"){	//Banner跳转（品牌）
			url = path+'/server/modfiybrandbanner';
		}else if(params.method=="headlineSkip"){	//头条跳转（开发者）
			url = path+'/server/modfiyheadline';
		}else if(params.method=="albumPictureSkip"){	//专栏图（开发者）
			url = path+'/server/modifyarticlecolumns';
		}
	
		if(confirm("确定提交？")){
			$http({
				   url: url,
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					history.go(-1);
				}else if(data.code==1){  //失败
					
				}
			}).error(function(data){
				alert("内容跳转设定失败！");   
			})
		}
	}
	
	//产品跳转
	publicService.initIndustry();	//初始化行业
	publicService.initClass();		//初始化品类
	publicService.initBrand();		//初始化品牌
	publicService.initpayModel("payModel");	//初始化付款模式
	//活动跳转
	publicService.initActivityClass("type_4");	//活动种类
	publicService.initDateTime("startTime_4");	//开始时间
	publicService.initDateTime("endTime_4");	//结束时间
	
	
	$scope.getColumnInfo();	//获取栏目信息
	

	
	
	/*权限分配*/
	//$scope.chooseList(3);   //图文跳转（屏蔽）
	
	if(userInfo.user_type==1){  //开发者
		if(params.method=="albumPictureSkip"){	//专栏图（开发者）
			$scope.chooseList(2);
			
			$("#list1").hide();
			$("#list4").hide();
			$("#list5").hide();
		}else{
			$scope.chooseList(1);   //选择列表
		}
	}else if(userInfo.user_type==2){  //企业
		
	}else if(userInfo.user_type==3){  //合伙人
		$scope.chooseList(2);   //选择列表
		
		$("#list1").hide();
		$("#list5").hide();
	}else if(userInfo.user_type==4){  //品牌
		$scope.chooseList(1);   //选择列表
		
		$("#list2").hide();
		$("#list3").hide();
		$("#list5").hide();
	}else if(userInfo.user_type==5){  //店铺
		
	}


});