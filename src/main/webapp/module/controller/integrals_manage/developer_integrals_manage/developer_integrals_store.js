App.controller("developerIntegralsStore",function($scope, $rootScope, $location, $http, $compile, path,publicService) {
	
	var limitParam1 = {imageSize:100, format:"jpg"};	//宣传图
	
	//上传图片
	$scope.Upload = function(containerId,imgId,imgUrlId,limitParam){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/server/updloadpointmallimg",
	            removeUrl: path+"/remove",
	           // autoUpload: false
	        },
	     	multiple: false,      //多选
	      	showFileList: false,  //显示文件列表
	        localization: {
	              select: "请上传图片",
	              cancel: "取消",
	              remove: "移除",  //autoUpload: false
	              retry: "刷新",
	              headerStatusUploading: "上传...",
	              headerStatusUploaded: "上传成功",
	              statusUploading: "loading",
	              statusUploaded: "success",
	              statusFailed: "failed",
	              uploadSelectedFiles: "上传选中的文件",   //autoUpload: false
	        },
	        success: function(e){
	        	console.log('成功：',e.response);
	        	var url = e.response.result;   //图片路径

	        	$scope.showUploadImg(imgId,imgUrlId,url);
		    },
	        error: function(e){
	        	//alert('失败！');
	        },
	        remove: function(e){
	        	//alert('移除！');
	        },
	        select: function(e){	//选择
	        	var image = e.files[0];
	        	if(limitParam){
	        		if(limitParam.imageSize && image.size>(limitParam.imageSize*1024)){
		        		alert("上传图片的大小请限制在【"+limitParam.imageSize+"Kb】以内！");
		        		e.preventDefault();
		        		return;
		        	}
		        	if(limitParam.format && image.extension.toLowerCase().indexOf(limitParam.format)==-1){
		        		alert("上传图片仅支持【"+limitParam.format+"】格式！");
		        		e.preventDefault();
		        		return;
		        	}
	        	}
	        }
	    });
	}
	//反显图片并赋值
	$scope.showUploadImg = function(imgId,imgUrlId,url){
		$("#"+imgId).css({
    		"background": "url('http://www.kaolaj.com/file/upload/image/pointmallimg.jpg?a="+Math.random()+"') no-repeat",
    		//"background": "url('http://localhost:8080/file/upload/image/pointmallimg.jpg?a="+Math.random()+"') no-repeat",
    	    "background-size":"100% 100%",
    	    "background-color":"white"
    	});
		$('#'+imgUrlId).val(url);
	}
	$scope.Upload("integrals","integrals_img","integrals_url",limitParam1);	//宣传图
	
	publicService.showUploadImage("integrals_img","integrals_url",'http://www.zjial.com/file/upload/image/pointmallimg.jpg');
	
	$scope.mainGridOptions = {
			dataSource : {
					transport : {
						read : {
							url : path+"/server/querypoinmall",
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
										product_title:$("#product_title").val(),
										convert_type:$("#exchangeType").data("kendoComboBox").value(),
										partner_name:$("#partner").data("kendoComboBox").value()
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
			    mode: "multiple"     //排序模式：single，multiple
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
						width : "60px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{    
						title : "商品图",   
						field : "product_img",   
						width : "120px",
						template:"<img src='#: product_img #' style='height:100px;width:100px'/>"
					},
					{    
						title : "商品名称",   
						field : "product_title",   
					},
					{    
						title : "库存",   
						field : "product_num",   
					},
					{    
						title : "商品兑换起止日期",   
						field : "start_time",  
						width:"240px",
						template:function(e){
							return e.start_time+"~"+e.end_time;
						}
					},
					{    
						title : "已兑换",   
						field : "convert_count",   
					},
					{    
						title : "兑换积分",   
						field : "point",   
					},
					{    
						title : "兑换类型",   
						field : "convert_type",
						values: [
						         { text: "流量包", value: 1 },
						         { text: "购物卡", value: 2 },
						         { text: "实物商品", value: 3 },
						         { text: "实物礼品", value: 4 },
						         { text: "话费", value: 5 }
						       ]
					},
					{    
						title : "城市合伙人",   
						field : "partner_name",   
					},
					{    
						title : "操作",   
						sortable: false,
						template:function(e){
							var template_html='';
							if(e.state==1){
								template_html = '<div class="operation k-state-default splitButtonTwoc" ng-click="update(\''+e.id+'\')">编辑</div>'+
					    		'<div class="operation k-state-default splitButtonTwoc" ng-click="soldOut(\''+e.id+'\')">下架</div>';
							}else{
								template_html = '<div class="operation k-state-default splitButtonTwoc" ng-click="update(\''+e.id+'\')">编辑</div>'+
					    		'<div class="operation k-state-default splitButtonTwoc" ng-click="putaway(\''+e.id+'\')">上架</div>';
							}
				    		return template_html;
			            }
					},
					
					
			]
		};
	
	//初始化兑换类型
    $scope.initExchangeType=function(){    
    	$("#exchangeType").kendoComboBox({
    		autoBind: false,
    		dataTextField: "name",
    		dataValueField: "id",
    		placeholder: "请选择",
    		filter: "contains",
    		dataSource: [{id:1,name:"流量包"},{id:2,name:"购物卡"},{id:3,name:"实物商品"},{id:4,name:"实物礼品"}],
    		change: function(e) {
	        	var value = this.value();
	        	if(value==1){//流量包
	        		$("#trafficDiv").css("display","block");
	        		$("#operatorDiv").css("display","block");
	        	}else{//其他
	        		$("#trafficDiv").css("display","none");
	        		$("#operatorDiv").css("display","none");
	        	}
	        }
    	});
    }
   
  //查询合伙人
	$scope.getPartner=function(){
		$("#partner").kendoComboBox({
    		autoBind: false,
    		dataTextField: "principal",
    		dataValueField: "principal",
    		placeholder: "请选择",
    		filter: "contains",
    		 dataSource: {
    				serverFiltering: false,
    				transport: {
    					read: {
    						type : 'GET',
    						url: path+"/server/queryPartner",
    						dataType : "json",
    						contentType: "application/json",
    					},
    				},
    				schema : {
    					data : function(d) {
    						return d.result;
    					}
    				},
    			}
    	});
	}
    $scope.initExchangeType();//初始化兑换类型
    $scope.getPartner();//初始化城市合伙人
	//跳转添加积分商品页
	$scope.addIntegralsStore=function(){
		var params = {method:"add"};
		$location.path("/addIntegralsStore/"+angular.toJson(params));
	}
	//编辑产品
	$scope.update=function(id){
		var params = {method:"edit",id:id};
		$location.path("/addIntegralsStore/"+angular.toJson(params));
	}
	//修改上下架状态
	$scope.setChecked=function(url,id){
		$http({
			   url: path+"/server/"+url,
			   method: 'GET',
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
	//下架产品
	$scope.soldOut=function(id){
		$scope.setChecked("offpointproduct/"+id);//下架产品
	}
	//上架产品
	$scope.putaway=function(id){
		$scope.setChecked("onpointproduct/"+id);//上架产品
	}
	//搜索
	$scope.search=function(){
		$scope.grid.dataSource.page(1);
	}
})