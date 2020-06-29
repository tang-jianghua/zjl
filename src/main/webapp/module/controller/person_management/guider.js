App.controller("guiderManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	$scope.guiderInfo = {};	//导购信息
	var guiderInfo = null;	//导购员信息
	$scope.addGuider_show = false;		//新增导购
	$scope.passwordReset_show = false;	//密码重置显示
	
	$scope.checkForm = {};	//审核
	
	
	//初始化账号状态
	$scope.initAccountState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
					{
					    name:"请选择",
					    value:""
					},
		            {
			              name:"关闭",
			              value:"0"
	            	},
	            	{
			              name:"正常",
			              value:"1"
	            	}
             ]
		});
	}
	
	//初始化审核状态
	$scope.initCheckState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
					{
					    name:"请选择",
					    value:""
					},
		            {
			              name:"未提交",
			              value:"0"
	            	},
	            	{
			              name:"待审核",
			              value:"1"
	            	},
	            	{
			              name:"审核通过",
			              value:"2"
	            	},
	            	{
			              name:"审核未通过",
			              value:"3"
	            	}
             ]
		});
	}

	//初始化导购审核状态
	$scope.initGuiderCheckState = function(){
		$("#checkState").kendoDropDownList({
			dataTextField: "value",
		    dataValueField: "key",
			dataSource: [
			             {
			            	 key: "",
			 			   value: "请选择"
			             },
			             {
			            	 key: 2,
		            	   value: "审核通过"
			             },
			             {
			            	 key: 3,
			               value: "审核未通过"
			             }
            ],
            change: function(e) {
	    	    var value = this.value();
	    	    if(value!='' && value==3){	//未通过
	    	    	$("#unCheckedParam").show();
	    	    }else{	
	    	    	$("#unCheckedParam").hide();
	    	    }
            }
		});
	}
	
	//导购员列表
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryShopGuideres",  
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"desc",field:"s.create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								sort=options.sort;
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									name: $scope.search.name1,		//姓名
									account: $scope.search.account, //账号名称（用户名）
									phone: $scope.search.phone,	 	//联系方式
									expand_name: $scope.search.expand_name,	 	//推广员
									state: $("#state").data("kendoDropDownList").value(),	 //账号状态
									certification_state: $("#check_state").data("kendoDropDownList").value(),	//审核状态
									principal: $scope.search.principal,	 	//合伙人名称	
									brand_name: $scope.search.brand_name,	 	//合伙人名称	
									shop_name: $scope.search.shop_name,	 	//合伙人名称	
									sort:sort
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
							template:"<input type='checkbox' value='#: id #' />"+"<span class='row-number subTitle'></span>"
						},
						{
							field : "name",         
							title : "姓名", 
							width : "100px" 
						},
						{
							field : "account",         
							title : "用户名", 
							width : "100px" 
						},
						{
							field : "expand_name",         
							title : "推广员姓名", 
							width : "100px",
							template:function(e){
								if(e.expand_name){
									return e.expand_name;
								}else{
									return "自主入驻";
								}
				            }
						},
						{
							field : "phone",         
							title : "联系方式", 
							width : "80px" 
						},
						{
							field : "invite_count",         
							title : "推广数量", 
							width : "80px" 
						},
						{
							field : "brand_name",         
							title : "所属品牌", 
							width : "100px" 
						},
						{
							field : "shop_name",         
							title : "所属店铺", 
							width : "120px" 
						},
						{
							field : "create_time",         
							title : "创建时间", 
							width : "100px" 
						},
						{
							field : "state",         
							title : "账号状态", 
							width : "80px",
							values: [
							         { text: "关闭", value: 0 },
							         { text: "正常", value: 1 }
					        ]
						},
						{
							field : "certification_state",         
							title : "审核状态", 
							width : "80px",
							template: function(e){
								var html = '';
								
								if(e.certification_state==0){
									html += '<div>未提交</div>';
								}else if(e.certification_state==1){
									if(userInfo.user_type=="3"){  //如果是城市合伙人
										html += '<div class="canEdit" ng-click="lookOrEdit(\''+e.id+'\',3)">待审核</div>';
									}else{
										html += '<div>待审核</div>';
									}
								}else if(e.certification_state==2){
									html += '<div>审核通过</div>';
								}else if(e.certification_state==3){
									html += '<div>审核未通过</div>';
								}
								
								return html;
							}
						},
						{

							field : "id", 
							title : "操作",     
							width : "150px",
							template:function(e){
								 var html = '';
								 
								 if(userInfo.user_type==5){  //店铺
									 if(e.isgenerate==1){	//店铺创建
										 html += ('<div class="operation k-state-default splitButtonTwoc" ng-click="lookOrEdit(\''+e.id+'\',1)">编辑</div>'
												 +'<div class="operation k-state-default splitButtonTwoc" ng-click="guiderData('+e.id+')">推广数据</div>');
									 }else{
										 html += ('<div class="operation k-state-default splitButtonTwoc" ng-click="lookOrEdit(\''+e.id+'\',2)">查看</div>'
												 +'<div class="operation k-state-default splitButtonTwoc" ng-click="guiderData('+e.id+')">推广数据</div>');
									 }
								 }else{
									 html += ('<div class="operation k-state-default splitButtonTwoc" ng-click="lookOrEdit(\''+e.id+'\',2)">查看</div>'
											 +'<div class="operation k-state-default splitButtonTwoc" ng-click="guiderData('+e.id+')">推广数据</div>');
								 }
								 
								 return html;
				            }
						}
				]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//新增导购
	$scope.addGuider = function(){
		$("#method").val("add");
		$("#idcardContainer").hide();
		$("#checkDetailsContainer").hide();
		$("#checkStateContainer").hide();
		
		$scope.passwordReset_show = false;
		$("#password").removeAttr("disabled");
		
		$scope.guiderInfo = {};
		$scope.guiderInfoWindow.center().open();   //打开弹框
	}
	
	//导购信息提交
	$scope.guiderInfoSubmit = function(){
		var method = $("#method").val();
		
		if(method=="add" || method=="edit"){
			var postData = {};
			if(!$scope.guiderInfo.name){
				alert("请填写【姓名】！");
				return;
			}
			if(!$scope.guiderInfo.phone){
				alert("请填写【联系方式】！");
				return;
			}else{
				if(!publicService.checkPhoneNumber($scope.guiderInfo.phone)){
					alert("请填写正确的【联系方式】！");
					return;
				}
			}
			if(!$scope.guiderInfo.password){
				alert("请填写【密码】！");
				return;
			}
			$scope.guiderInfo.account = $scope.guiderInfo.phone;	//账号和联系方式一致

			
			var url = '';
			if($scope.guiderInfo.id){
				url = path+'/server/modifyShopGuider';
				postData = {
						id: $scope.guiderInfo.id,  
						name: $scope.guiderInfo.name,
						phone: $scope.guiderInfo.phone,
						account: $scope.guiderInfo.phone
				};
			}else{
				url = path+'/server/addShopGuider';
				postData = {
						name: $scope.guiderInfo.name,
						phone: $scope.guiderInfo.phone,
						account: $scope.guiderInfo.phone,
						password: $scope.guiderInfo.password
				};
			}
			
			$http({
				   url: url,
				   method: 'POST',   
				   data: angular.toJson(postData),  
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.guiderInfoWindow.close();
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}else if(method=="check"){
			var postData = {
					seller_id: $scope.guiderInfo.id,
					certification_state: $("#checkState").data("kendoDropDownList").value()
			};
			if(postData.certification_state==3){
				if($scope.checkForm.info){
					postData.no_pass_reason = "认证因："+$scope.checkForm.info+"；未通过审核，请重新填写；如有疑问请致电："+$scope.checkForm.phone_num+"。";
				}else{
					alert("请填写审核不通过理由！");
					return;
				}
			}
			
			$http({
				   url: path+'/server/modifyShopGuiderState',
				   method: 'POST',   
				   data: angular.toJson(postData),  
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.guiderInfoWindow.close();
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//重置密码
	$scope.resetPassword = function(){ 
		var postData = new Object();
		postData.id = $scope.guiderInfo.id;
		postData.password = "888888";
		
		$http({
			   url: path+'/server/modifyShopGuider',
			   method: 'POST',
			   data: angular.toJson(postData),  
		}).success(function(data){
			if(data.code==0){  //成功
				alert("密码重置成功！");
				$scope.guiderInfo.password = "888888";
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//查看或编辑
	$scope.lookOrEdit = function(id,type){
		$http({
			   url: path+'/server/queryShopGuider/'+id,
			   method: 'GET'
		}).success(function(data){  
			if(data.code==0){  //成功
				guiderInfo =  data.result;
				
				if(guiderInfo){
					$scope.guiderInfo.id = guiderInfo.id;
					$scope.guiderInfo.name = guiderInfo.name;	//姓名
					$scope.guiderInfo.phone = guiderInfo.phone;	//联系方式、账号名称
					$scope.guiderInfo.password = "******";		//密码
					
					//显示图片信息
					var idcardArr = new Array();
					idcardArr.push({name:"正面照",url:guiderInfo.idcard_front});
					idcardArr.push({name:"背面照",url:guiderInfo.idcard_back});
					idcardArr.push({name:"持件照",url:guiderInfo.idcard_hold});
					$scope.showImageInfo("idcardContainer",idcardArr);
					
					//审核详情
					if(guiderInfo.certification_state==0){
						$scope.guiderInfo.certification_state = "未提交";
					}else if(guiderInfo.certification_state==1){
						$scope.guiderInfo.certification_state = "待审核";
					}else if(guiderInfo.certification_state==2){
						$scope.guiderInfo.certification_state = "审核通过";
					}else if(guiderInfo.certification_state==3){
						$scope.guiderInfo.certification_state = "审核未通过";
					}
					$scope.guiderInfo.certificate_time = guiderInfo.certificate_time;	//审核时间
					$scope.guiderInfo.no_pass_reason = guiderInfo.no_pass_reason;				//审核跟踪
					
					$scope.checkForm.phone_num = guiderInfo.phone_num;	////人工热线(合伙人电话)
				
					$scope.guiderInfoWindow.center().open();   //打开弹框
					
					if(type==1){	//编辑
						$("#method").val("edit");
						
						$scope.passwordReset_show = true;
						$("#submitButton").show();
						$("#guiderForm").find("input").removeAttr("readonly");
						$("#password").attr("disabled","disabled");
						
						$("#checkDetailsContainer").hide();
						$("#checkStateContainer").hide();
					}else if(type==2){	//查看
						$("#method").val("look");
						
						$scope.passwordReset_show = false;
						$("#submitButton").hide();
						$("#guiderForm").find("input").attr("readonly","readonly");
						
						$("#checkDetailsContainer").show();
						$("#checkStateContainer").hide();
					}else if(type==3){	//审核
						$("#method").val("check");
						
						$scope.passwordReset_show = false;
						$("#submitButton").show();
						$("#guiderForm").find("input").attr("readonly","readonly");
						$("#unCheckedParam").hide();
						
						$("#checkDetailsContainer").hide();
						$("#checkStateContainer").show ();
					}
				}else{
					alert("暂无导购员详细信息！");
				}
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//显示图片信息
	$scope.showImageInfo = function(containerId,imageArr){
		var html = '';
		
		$.each(imageArr, function(index, OneObj){
			if(OneObj.url){
				html += ('<div class="col-md-3">'
							+'<div>'
								+'<img class="imgContainer" src="'+OneObj.url+'">'
							+'</div>'
							+'<div class="text">'+OneObj.name+'</div>'
						+'</div>');
			}
		});
		
		$("#"+containerId).html(html);
	}
	
	
	
	//导购数据
	$scope.guiderData = function(id){
		var params = {guiderId:id};
		$location.path("/guiderDataManagement/"+angular.toJson(params));
	}
	

	
	$scope.initAccountState("state");		//初始化账号状态
	$scope.initCheckState("check_state");	//初始化审核状态
	
	$scope.initGuiderCheckState();	//初始化导购审核状态
	
	/*权限分配*/
	if(userInfo.user_type==5){  //店铺
		$scope.addGuider_show = true;
	}else{
		$scope.addGuider_show = false;
	}
	
});