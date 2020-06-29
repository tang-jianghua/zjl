App.controller("evaluationListCtrl",function($scope, $rootScope, $location, $http, $compile, path,$sce) {
	
	$scope.details = {};  //定点列表
	//如果当前权限开发者，则显示城市合伙人和企业，如果当前为企业，则显示城市合伙人，如果当前为城市合伙人，则不显示城市合伙人搜索框
	$scope.userRole = {}; //显示企业
	$scope.curItem_id = null;
	var delItemID = null;
	//初始化搜索条件input及grid显示的模块数据
	$scope.initsearchIput = function(){
		//userInfo.user_type = "5"
		$scope.show_role_info_col = false;//用户 店铺 不显示对应信息列
		$scope.show_operation = true;     //用户 品牌／店铺 显示操作列 	
		if(userInfo.user_type=="0")
		{
			$scope.userRole.userPartner = "show";  //搜索框及关联信息显示
			$scope.userRole.userEnterpris = "show";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
		
		}else if(userInfo.user_type=="1") //开发者账号
		{
			$scope.userRole.userEnterpris = "show";
			$scope.userRole.userPartner = "show";  //搜索框及关联信息显
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
			
			}
		else if(userInfo.user_type== "2") //企业用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "show";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
		}
		else if(userInfo.user_type=="3")//城市合伙人用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "show";
			$scope.userRole.userShop = "show";
		}else if(userInfo.user_type=="4")//品牌用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "hidden";
			$scope.show_operation = false;     //用户 品牌／店铺 显示操作列 
			$scope.userRole.userShop = "show";
		}else if(userInfo.user_type=="5")//店铺用户
		{
			$scope.userRole.userEnterpris = "hidden";
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.userBrand = "hidden";
			$scope.show_role_info_col= true;//用户 店铺 不显示对应信息列
			$scope.userRole.userShop = "hidden";
			$scope.show_operation = false;     //用户 品牌／店铺 显示操作列 		
		}
	}
	
	$scope.initsearchIput();            //初始化搜索条件input
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryproductcomment",
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
									product_name: $scope.search.product_name,	 	//产品标题
									
									shop_name: $("#autoComShopName").data("kendoAutoComplete").value(),		   		//店面名称
									brand_name: $("#autoComBrandName").data("kendoAutoComplete").value(),	           //品牌名称
									principal: $("#autoComPartnerName").data("kendoAutoComplete").value(),	 	//合伙人名称
									enterprise_name:  $("#autoComeEnterprisName").data("kendoAutoComplete").value(),	
		
									customer_name: $scope.search.customer_name,		 	//买家昵称
									province: $("#province").data("kendoComboBox").value(),		//省
									city: $("#city").data("kendoComboBox").value(),				//市
									county: $("#area").data("kendoComboBox").value(),			//区
									start_time: $("#start_time").data("kendoDateTimePicker").value(),	//预约时间（始）
									end_time: $("#end_time").data("kendoDateTimePicker").value(),		//预约时间（止）
									isreply:$scope.search.isreply,		 	//无回复评价null  已回复1
									state:$scope.search.delReply,			//已删除的评价
									evaluation_level:$scope.search.evaluation_level,			//评价内容状态
									hasimg:$scope.search.hasimg,		 	//无图null  有图1
									noimg:$scope.search.noimg,		 		//无图 1  有图 null
									hascontent:$scope.search.hascontent,		 	//有内容的评价 1 无内容的评价 null
									nocontent:$scope.search.nocontent,		 	//有内容的评价 null 无内容的评价 1
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
						console.log('返回数据评价',d);
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
			dataBound: function(e) {	//数据绑定成功
		    	  console.log("【Grid 数据绑定成功】");
		    	  $('.fancybox').fancybox();
		    },
			columns : [
					{    
						title : "内容状态",     
						width : "120px",
						template:function(e)
						{
							var hearder = '<div class="row"><div class="form-group" style="overflow: hidden;margin-bottom: 0px;"><div class="col-md-2 text-center" style="position: absolute;top: 50%;transform: translateY(-50%);">';
							var ender = '</div><div class="col-md-8" style="padding-left: 25%;"><ul style="float:left;" class="list-unstyled">'+e.reply_content +'</ul></div></div></div>'
							if(e.evaluation_level ==3 )
								{
								return hearder+"好评"+ender;
								}
							else if(e.evaluation_level ==2 )
								{
								return hearder+"中评"+ender;
								}
							else if(e.evaluation_level ==1 )
								{
								return hearder+"差评"+ender;
								}
						}
							
						
					},
					{     
						title : "买家信息",     
						width : "80px",
						template:function(e)
						{
							var hearder ='<ul style="float:left;" class="list-unstyled"><li>昵称:'+e.customer_name+'</li><li>用户级别：'
							var ender =	'</li></ul>'
							if(e.customer_level ==null )
							{
								return hearder+"普通用户"+ender;
							}
							else if(e.customer_level ==0 )
							{
								return hearder+"普通用户"+ender;
							}
							else if(e.customer_level ==1 )
							{
								return hearder+"会员"+ender;
							}
							else if(e.customer_level ==2 )
							{
								return hearder+"高级用户"+ender;
							}
						}
					},
					{      
						title : "产品信息",     
						width : "100px",
						template:
	                        '<ul style="float:left;" class="list-unstyled">'+
							'<li>名称：#= product_name #</li>'+
							'<li>价格：#= price #</li>'+
							'</ul>'
					},
					{
						field : "img_info",         
						title : "图片信息",     
						width : "60px",
						template:function(e)
						{
							var html = '';
							if(e.img_info){
								var imgArr = e.img_info.split(",");
								
								html += '<p>';
								$.each(imgArr, function(index, OneObj){
									html += ('<a class="fancybox img_info" href="'+OneObj+'" data-fancybox-group="gallery_'+e.id+'" title="">'
											+'<img src="'+OneObj+'" class="bafont"/>'
										+'</a>');
								});
								html += '</p>';
							}
							
							return html;
						}
					},
					{
						hidden: $scope.show_role_info_col,//用户 品牌／店铺 显示对应信息列 
						field : "shop_name",         
						title : "对应信息",     
						width : "120px",
						template:
	                        '<ul style="margin-bottom: 0px;" class="list-unstyled text-center">'+
	                        '<li class = "{{userRole.userEnterpris}}" >企业:#= enterprise_name #</li>'+
							'<li class = "{{userRole.userPartner}}" >合伙人:#= partner_principal #</li>'+
							'<li class = "{{userRole.userBrand}}">品牌：#= brand_name #</li>'+
							'<li class = "{{userRole.shop_name}} ">店面信息:#= shop_name #</li>'+
							'</ul>'
					},
					{
						hidden: $scope.show_operation,//用户 品牌／店铺 显示操作列   
						//field : "op_id", 
						title : "操作",     
						width : "40px",
						template:function(e){
//							if($scope.show_role_info_col&&e.isreply=="1" &&userInfo.user_type=="5"){//如果是不显示对应信息列，则显示 回复，删除按钮 同时品牌不显示回复
//								return "<div  value='回复' class='operation k-state-default splitButtonLeft' ng-click='showReply("+e.id+")' />回复</div>"+
//								"<div  value='删除' class='operation k-state-default splitButtonRight' ng-click='delItemReplay("+e.id+")' />删除</div>"
//							}else//如果是显示对应信息列，则显示 删除按钮
//							{
//								return "<div  value='删除' class='operation k-state-default' ng-click='delItemReplay("+e.id+")' /> 删除</div>"
//							}
							
							if($scope.show_role_info_col&&e.isreply=="1" &&userInfo.user_type=="5"){//如果是不显示对应信息列，则显示 回复，删除按钮 同时品牌不显示回复
								return "<div  value='回复' class='operation k-state-default splitButtonLeft' ng-click='showReply("+e.id+")' />回复</div>"
							}else//如果是显示对应信息列，则显示 删除按钮
							{
								return ""
							}
							
						}
						
					}
			]
		};
	
	//弹出回复窗口
	$scope.showReply=function(_id)
	{
		$scope.details.evaluation_id = _id
		$scope.replyWin.center().open();   //打开弹框
	}
	//提交回复
	$scope.setReply=function()
	{
		$http({
			   url: path+"/server/reply",
			   method: 'POST',
			   data: angular.toJson($scope.details)
		}).success(function(data){
			
			if(data.code==0){  //成功

				$scope.search();
				$scope.replyWin.center().close();   //关闭弹框
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
		
	}
	//弹出删除窗口
	$scope.delItemReplay=function(_id)
	{
		delItemID = _id
		$scope.delReplyWin.center().open();   //打开弹框
	}

	//删除当前行回复
	$scope.setDelReplyItem=function()
	{
		$http({
			   url: path+"/server/modifyproductcommentstate/"+delItemID,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.delReplyWin.center().close();   //打开弹框
				$scope.search()
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
		
		
		
	}

	//查询
	$scope.search=function(){
		$scope.grid.dataSource.online(true);
		$scope.grid.dataSource.page(1);
	}
	
	//初始化省
	$scope.initProvince = function(){
		$("#province").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "code",
		    placeholder: "省",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/province",
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
	    	    var value = this.value();
	    	    $scope.getCityData(value);
	      }
		});
	}
	
	//获取市数据
	$scope.getCityData = function(provinceCode,defaultVal){
		$http({
			   url: path+"/server/city/"+provinceCode,
			   method: 'GET'
		}).success(function(data){
			$scope.initCity(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化市
	$scope.initCity = function(cityData,defaultVal){
		if(cityData){
			var plugObj = $("#city").data("kendoComboBox");
			plugObj.value("");
			plugObj.setDataSource(cityData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#city").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "code",
			    placeholder: "市",
			    filter: "contains",
			    dataSource: [],
			    change: function(e) {
		    	    var value = this.value();
		    	    $scope.getAreaData(value);
		      }
			});
		}
	}
	
	//获取区数据
	$scope.getAreaData = function(cityCode,defaultVal){
		$http({
			   url: path+"/server/county/"+cityCode,
			   method: 'GET'
		}).success(function(data){
			$scope.initArea(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化区
	$scope.initArea = function(areaData,defaultVal){
		if(areaData){
			var plugObj = $("#area").data("kendoComboBox");
			plugObj.setDataSource(areaData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#area").kendoComboBox({
				  dataTextField: "name",
				  dataValueField: "code",
				  placeholder: "区",
				  filter: "contains",
				  dataSource: []
			});
		}
	}
	
	//初始化时间控件
	$scope.initDateTime = function(containerId){
		$("#"+containerId).kendoDateTimePicker({
		     value: "",
		     culture: "de-DE",
		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
		     interval: 30,   				  //时间间隔
		     timeFormat: "HH:mm" 			  //24 hours format
		});
	}
//	//初始化订单状态按钮
//	$scope.initStateBtnGroup = function(){
//		var mybtn = $('#stateBtnGroup :button');
//			mybtn.each(function(i){
//					$(this).click(function()
//					{
//						console.log("this  "+i)
//					}		
//				)
//			}		
//		);
//	}
	//当订单状态按钮进行点击
	$scope.setState = function(_btnState){
		//console.log("点击的订单状态：",_btnState);
		if(_btnState == "")
		{
			$scope.search.state_name =undefined;
		}else
		{$scope.search.state_name = _btnState;}
		
		$scope.search();
	}
	//获取订单状态列表
	$scope.getOrderStateClassData = function(){
		var url = path+'/server/orderstate';
		$http({
			   url: url,
			   method: 'GET' 
		}).success(function(data){  
			console.log("订单状态列表：",data);
			$scope.btnStateNames = data;

		}).error(function(data){
			alert(data);   
		})
	}
	

	
	$("#autoComShopName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/shopname",
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
	
	//获取品牌名称输入框提示列表
	$("#autoComBrandName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/brandname",
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
	//获取企业名称输入框提示列表
	$("#autoComeEnterprisName").kendoAutoComplete({
        dataSource: {
        	transport : {
			read : {
				url : path+"/server/enterprisename",
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
	
	//来自买家的评价
	$scope.customerListReply = function(){		
		$scope.search.isreply = null;
		$scope.search.delReply = null;
 	    $scope.search();
	}
	//已回复的评价
	$scope.shopListReply = function(){
		$scope.search.isreply = "1";
		$scope.search.delReply = null;
 	    $scope.search();
	}
	//已删除的评价
	$scope.delListReplay = function(){
		$scope.search.isreply = null;
		$scope.search.delReply = "1";
 	    $scope.search();
	}
	//初始化评价状态
	$scope.initEvaluationStatus = function(){
		$("#evaluation_state").kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
						{
						    name:"全部",
						    value:""
						},
			             {
				              name:"好评",
				              value:"3"
		            	  },
		            	  {
				              name:"中评",
				              value:"2"
		            	  },
		            	  {
				              name:"差评",
				              value:"1"
		            	  }
			             ],
             change: function(e) {
		    	    var value = this.value();
		    	    $scope.search.evaluation_level = value;
		    	    $scope.search();
		      }
		});
	}
	//初始化有内容的评价状态
	$scope.initEvaluationContentStatus = function(){
		$("#evaluation_content_state").kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
						{
						    name:"全部",
						    value:""
						},
			             {
				              name:"有内容的评价",
				              value:"1"
		            	  },
		            	  {
				              name:"无内容的评价",
				              value:"2"
		            	  }
			             ],
             change: function(e) {
		    	    var value = this.value();
		    	    if(value == 1){
		    	    	$scope.search.hascontent = "1"
		    	    	$scope.search.nocontent = null
		    	    }else if(value == 2)
	    	    	{
		    	    	$scope.search.hascontent = null
			    	    $scope.search.nocontent = "1"
	    	    	}else if(value == "")
	    	    	{
		    	    	$scope.search.hascontent = null
			    	    $scope.search.nocontent = null
	    	    	}
		    	    
		    	    $scope.search();
		      }
		});
	}
	//初始化有图片的评价状态
	$scope.initEvaluationImgStatus = function(){
		$("#evaluation_img_state").kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
			             {
				              name:"全部",
				              value:""
		            	  },
			             {
				              name:"有图",
				              value:"1"
		            	  },
		            	  {
				              name:"无图",
				              value:"2"
		            	  }
			             ],
         change: function(e) {
	    	    var value = this.value();
	    	    if(value == 1){
	    	    	$scope.search.hasimg = "1"
	    	    	$scope.search.noimg = null
	    	    }else if(value == 2)
    	    	{
	    	    	$scope.search.hasimg = null
		    	    $scope.search.noimg = "1"
    	    	}else if(value == "")
    	    	{
	    	    	$scope.search.hasimg = null
		    	    $scope.search.noimg = null
    	    	}
	    	    
	    	    $scope.search();
	      }
		});
	}
	

	//$scope.initStateBtnGroup();		//初始评价状态按钮
	$scope.initEvaluationStatus();//初始化评价状态
	$scope.initEvaluationContentStatus();//初始化有内容的评价状态
	$scope.initEvaluationImgStatus();//初始化有图片的评价状态
	$scope.getOrderStateClassData();		//初始订单状态按钮
	$scope.initProvince();			//初始化省
	$scope.initCity();				//初始化市
	$scope.initArea();				//初始化区
	$scope.initDateTime("start_time");   //预约时间（始）
	$scope.initDateTime("end_time");   	 //预约时间（止）
	
});