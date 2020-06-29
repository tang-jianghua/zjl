App.controller("addDiscountActivityCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, $timeout, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.form = {};     			 //表单
	var discountParamCount_1 = 0;	//参数数量（满额折扣）
	var discountParamCount_2 = 0;	//参数数量（满减折扣）
	var discountParamCount_3 = 0;	//参数数量（数量折扣）
	var activityInfo = null;	//活动信息
	
	var limitParam1 = {imageSize:100, format:"jpg"};		//活动宣传图
	
	
	
	
	
	//参与合伙人
	$scope.initJoinPartner = function(){
		$("#join_partner").kendoMultiSelect({
		    dataTextField: "principal",
		    dataValueField: "id",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'POST',
	                  url: path+"/server/selectAllPartner",
	                  dataType : "json"
	              }
	          }
	      }
		});
	}
	
	//参与品类
	$scope.initJoinClass = function(){
		$("#join_class").kendoMultiSelect({
		    dataTextField: "name",
		    dataValueField: "id",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/getbuildclassentities",
	                  dataType : "json"
	              }
	          }
	      }
		});
	}
	
	//活动类型
	$scope.initDiscountActivityType = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: 1,
			 				text: "满额折扣"
			             },
			             {
			            	 id: 2,
			 				text: "满减折扣"
			             },
			             {
			            	 id: 3,
			 				text: "数量折扣"
			             }
            ],
            change: function(e) {
	    	    var value = this.value();
	    	    
	    	    $scope.chooseDiscountParam(value);
            }
		});
	}
	
	//添加折扣参数
	$scope.chooseDiscountParam = function(type){
		for(var i=1;i<=3;i++){
	    	if(i==type){
	    		$("#discountType_"+i).show();
	    	}else{
	    		$("#discountType_"+i).hide();
	    	}
	    }
	}
	
	//添加折扣参数
	$scope.addDiscountParam = function(containerId,type){
		var html = '';
		var deleteButtonClass = "";
		
		if(params.method=="add" || params.method=="edit"){
			deleteButtonClass = "show";
		}else if(params.method=="look"){
			deleteButtonClass = "hide";
		}
		
		if(type==1){
			discountParamCount_1 ++;
			var paramId = 'discountParam_'+discountParamCount_1;
			
			html += ('<div class="form-horizontal form-widgets col-sm-12" id="'+paramId+'">'
						+'<div class="form-group">'
							+'<label class="control-label col-sm-2">满折'+discountParamCount_1+'：</label>'
							+'<div class="col-sm-8 discountContent">'
								+'<span class="col-sm-1 text_right">满</span>'
								+'<span class="col-sm-2"><input type="number" class="k-textbox"/></span>'
								+'<span class="col-sm-1">元</span>'
								+'<span class="col-sm-2 text_right">折扣比例</span>'
								+'<span class="col-sm-2"><input type="number" class="k-textbox"/></span>'
								+'<span class="col-sm-1">%</span>'
								+'<span class="col-sm-2 '+deleteButtonClass+'">'
									+'<button class="k-button k-state-default" ng-click="deleteParam(\''+paramId+'\')">删除</button>'
								+'</span>'
							+'</div>'
						+'</div>'
					+'</div>');
		}else if(type==2){
			discountParamCount_2 ++;
			var paramId = 'discountParam_'+discountParamCount_2;
			
			html += ('<div class="form-horizontal form-widgets col-sm-12" id="'+paramId+'">'
						+'<div class="form-group">'
							+'<label class="control-label col-sm-2">满减'+discountParamCount_2+'：</label>'
							+'<div class="col-sm-8 discountContent">'
								+'<span class="col-sm-1 text_right">满</span>'
								+'<span class="col-sm-2"><input type="number" class="k-textbox"/></span>'
								+'<span class="col-sm-1">元</span>'
								+'<span class="col-sm-2 text_right">减</span>'
								+'<span class="col-sm-2"><input type="number" class="k-textbox"/></span>'
								+'<span class="col-sm-1">元</span>'
								+'<span class="col-sm-2 '+deleteButtonClass+'">'
									+'<button class="k-button k-state-default" ng-click="deleteParam(\''+paramId+'\')">删除</button>'
								+'</span>'
							+'</div>'
						+'</div>'
					+'</div>');			
		}else if(type==3){
			discountParamCount_3 ++;
			var paramId = 'discountParam_'+discountParamCount_3;
			
			html += ('<div class="form-horizontal form-widgets col-sm-12" id="'+paramId+'">'
						+'<div class="form-group">'
							+'<label class="control-label col-sm-2">折扣'+discountParamCount_3+'：</label>'
							+'<div class="col-sm-8 discountContent">'
								+'<span class="col-sm-1 text_right">第</span>'
								+'<span class="col-sm-2"><input type="number" class="k-textbox"/></span>'
								+'<span class="col-sm-1">件起</span>'
								+'<span class="col-sm-2 text_right">折扣比例</span>'
								+'<span class="col-sm-2"><input type="number" class="k-textbox"/></span>'
								+'<span class="col-sm-1">%</span>'
								+'<span class="col-sm-2 '+deleteButtonClass+'">'
									+'<button class="k-button k-state-default" ng-click="deleteParam(\''+paramId+'\')">删除</button>'
								+'</span>'
							+'</div>'
						+'</div>'
					+'</div>');			
		}
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#"+containerId).append(html);
	}
	
	//删除参数
	$scope.deleteParam = function(paramId){
		if(confirm("确认删除？")){
			if(params.method=="add"){
				$("#"+paramId).remove();
			}else if(params.method=="edit"){
				var postData = {
						id: $($("#"+paramId).find(".discountContent")[0]).attr("discountParamId")
				};
				$http({
					   url: path+'/server/deletePlatformStepPromotion',
					   method: 'POST',   
					   data: angular.toJson(postData)
				}).success(function(data){
					if(data.code==0){  //成功
						$("#"+paramId).remove();
					}else if(data.code==1){  //失败
						alert(data.message);
					}
				}).error(function(data){
					alert("系统异常！");   
				})
			}
		}
	}
	
	//删除图片
	$scope.deletePicture = function(type,which){
		if(confirm("确认删除？")){
			if(params.method=="add"){
				if(type==1){
					publicService.removeUploadImage("cover_img","cover_url");		//封面图
				}else if(type==2){
					if(which==1){
						publicService.removeUploadImage("activity1_img","activity1_url");		//活动图
					}else if(which==2){
						publicService.removeUploadImage("activity2_img","activity2_url");		//活动图
					}else if(which==3){
						publicService.removeUploadImage("activity3_img","activity3_url");		//活动图
					}
				}
			}else if(params.method=="edit"){
				var postData = {};
				postData.id = activityInfo.id;
					
				if(type==1){
					postData.var3  = "";   //封面图
				}else if(type==2){
					if(which==1){
						postData.imgurl1 = '';   //活动图
					}else if(which==2){
						postData.imgurl2 = '';   //活动图
					}else if(which==3){
						postData.imgurl3 = '';   //活动图
					}
				}
				
				$http({
					   url: path+'/server/modifyPlatformStepPromotion',
					   method: 'POST',   
					   data: angular.toJson(postData)
				}).success(function(data){
					if(data.code==0){  //成功
						window.location.reload();   //页面刷新
					}else if(data.code==1){  //失败
						alert(data.message);
					}
				}).error(function(data){
					alert("系统异常！");   
				})
			}
		}
	}
	
	//折扣活动添加
	$scope.formSubmit = function(){
		$scope.form.var3 = $("#cover_url").val();   //封面图 
		$scope.form.imgurl1 = $("#activity1_url").val();   //活动图
		$scope.form.imgurl2 = $("#activity2_url").val();   //活动图 
		$scope.form.imgurl3 = $("#activity3_url").val();   //活动图
		$scope.form.limit_time = $("#limit_time").val();   //报名截止时间
		$scope.form.start_time = $("#start_time").val();   //活动日期（始）
		$scope.form.end_time = $("#end_time").val();       //活动日期（止）
		$scope.form.partner_id = $("#join_partner").data("kendoMultiSelect").value().toString();	//参与合伙人
		$scope.form.class_id = $("#join_class").data("kendoMultiSelect").value().toString();		//参与品类
		$scope.form.type = $("#activity_type").data("kendoDropDownList").value();  //活动类型
		
		if(!$scope.form.title){
			alert("请填写【活动名称】！");
			return;
		}
		if(!$scope.form.var3){
			alert("请上传【封面图】！");
			return;
		}
		if(!$scope.form.imgurl1 && !$scope.form.imgurl2 && !$scope.form.imgurl3){
			alert("请至少上传一张【活动图】！");
			return;
		}
		if(!$scope.form.limit_time){
			alert("请选择【报名截止时间】！");
			return;
		}else{
			$scope.form.limit_time += " 23:59:59";
		}
		if(!$scope.form.start_time){
			alert("请填写【活动开始日期】！");
			return;
		}else{
			$scope.form.start_time += " 00:00:00";
		}
		if(!$scope.form.end_time){
			alert("请填写【活动结束日期】！");
			return;
		}else{
			$scope.form.end_time += " 23:59:59";
		}
		if(!$scope.form.partner_id){
			alert("请选择【参与合伙人】！");
			return;
		}
		if(!$scope.form.class_id){
			alert("请选择【参与品类】！");
			return;
		}
		
		//折扣设置
		var isQuit = false;
		$scope.form.stepConditionEntityList = [];	//折扣设置
		var discountParamArr = $("#discountType_"+$scope.form.type).find(".discountContent");
		var discountParamArr_length = discountParamArr.length;
		$.each(discountParamArr, function(index, OneObj){
			var oneParam = {
					value1: Number($(OneObj).find("input:eq(0)").val()),
					discount: Number($(OneObj).find("input:eq(1)").val())
			};
			
			if($scope.form.type==1){	//满额折扣
				if(oneParam.value1 && oneParam.discount){
					if(oneParam.discount<=0 || oneParam.discount>=100){
						alert("第"+(index+1)+"个满折信息的【折扣比例】范围为(0,100)！");
						isQuit = true;
						return false;
					}
					
					oneParam.discount = oneParam.discount/100;
				}else if(!oneParam.value1 && !oneParam.discount){
					return true;
				}else{
					alert("请填写完整第"+(index+1)+"个满折信息！");
					isQuit = true;
					return false;
				}
			}else if($scope.form.type==2){	//满减折扣
				if(oneParam.value1 && oneParam.discount){
					if(oneParam.value1<=oneParam.discount){
						alert("第"+(index+1)+"个满减信息的【折扣金额】应该小于【总金额】！");
						isQuit = true;
						return false;
					}
				}else if(!oneParam.value1 && !oneParam.discount){
					return true;
				}else{
					alert("请填写完整第"+(index+1)+"个满减信息！");
					isQuit = true;
					return false;
				}
			}else if($scope.form.type==3){	//数量折扣
				if(oneParam.value1 && oneParam.discount){
					if(!publicService.checkIsUpNumber(oneParam.value1)){
						alert("第"+(index+1)+"个折扣信息的【商品件数】应该为正整数！");
						isQuit = true;
						return false;
					}
					if(oneParam.discount<=0 || oneParam.discount>=100){
						alert("第"+(index+1)+"个折扣信息的【折扣比例】范围为(0,100)！");
						isQuit = true;
						return false;
					}
					
					oneParam.discount = oneParam.discount/100;
				}else if(!oneParam.value1 && !oneParam.discount){
					return true;
				}else{
					alert("请填写完整第"+(index+1)+"个折扣信息！");
					isQuit = true;
					return false;
				}
				
				if(index==0){
					oneParam.value2 = 999999;
				}else{
					var lastParam = $scope.form.stepConditionEntityList[$scope.form.stepConditionEntityList.length-1];
					if(oneParam.value1<=lastParam.value1){
						alert("第"+(index+1)+"个折扣信息的【商品件数】应该大于上一个折扣信息的【商品件数】！");
						isQuit = true;
						return false;
					}
					if(oneParam.discount>=lastParam.discount){
						alert("第"+(index+1)+"个折扣信息的【折扣比例】应该小于上一个折扣信息的【折扣比例】！");
						isQuit = true;
						return false;
					}
					
					lastParam.value2 = oneParam.value1-1;
					oneParam.value2 = 999999;
				}
			}
			
			var discountParamId = $(OneObj).attr("discountParamId");
			if(discountParamId){
				oneParam.id = discountParamId;
			}
			
			$scope.form.stepConditionEntityList.push(oneParam);
		});
		if(isQuit){
			return;
		}
		
		if($scope.form.stepConditionEntityList.length==0){
			alert("请至少填写一组【折扣设置】信息");
			return;
		}
		
		
		var url = '';
		if($scope.form.id){
			url = path+'/server/modifyPlatformStepPromotion';
		}else{
			url = path+'/server/addPlatformStepPromotion';
		}
		
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.form)
		}).success(function(data){
			if(data.code==0){  //成功
				$location.path("platformActivity");
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert("系统异常！");   
		})	
	}
	
	//获取活动信息
	$scope.getActivityInfo = function(id){
		$http({
			   url: path+'/server/getPlatformStepPromotionByid/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				activityInfo = data.result;
				
				$scope.form.id = activityInfo.id;
				$scope.form.title = activityInfo.title;		//活动名称
				publicService.showUploadImage("cover_img","cover_url",activityInfo.var3);	//封面图
				publicService.showUploadImage("activity1_img","activity1_url",activityInfo.imgurl1);	//活动图
				publicService.showUploadImage("activity2_img","activity2_url",activityInfo.imgurl2);	//活动图
				publicService.showUploadImage("activity3_img","activity3_url",activityInfo.imgurl3);	//活动图
				$("#limit_time").val(activityInfo.limit_time);		//报名截止日期
				$("#start_time").val(activityInfo.start_time);	//活动日期（始）
				$("#end_time").val(activityInfo.end_time);		//活动日期（止）
				$("#join_partner").data("kendoMultiSelect").value(activityInfo.partner_id.split(","));	//参与品合伙人
				$("#join_class").data("kendoMultiSelect").value(activityInfo.class_id.split(","));		//参与品类
				$("#activity_type").data("kendoDropDownList").value(activityInfo.type);  //活动类型
				$scope.form.content = activityInfo.content;		//活动说明
				
				var discountType = activityInfo.type; //折扣类型
				$scope.chooseDiscountParam(discountType);
				$.each(activityInfo.stepConditionEntityList, function(index, OneObj){
					if(index>0){
						$scope.addDiscountParam('discountType_'+discountType,discountType);
					}
					
					$("#discountType_"+discountType).find(".discountContent:eq("+index+")").attr("discountParamId",OneObj.id);
					$("#discountType_"+discountType).find(".discountContent:eq("+index+")").find("input:eq(0)").val(OneObj.value1);
					if(discountType==1 || discountType==3){
						$("#discountType_"+discountType).find(".discountContent:eq("+index+")").find("input:eq(1)").val(OneObj.discount*100);
					}else{
						$("#discountType_"+discountType).find(".discountContent:eq("+index+")").find("input:eq(1)").val(OneObj.discount);
					}
				});
				
				$("#discountType_"+discountType).find("input").attr("readonly","readonly");
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert("系统异常！");   
		})
	}
	
	
	
	
	publicService.uploadImage("cover","cover_img","cover_url",limitParam1);				//封面图
	publicService.uploadImage("activity1","activity1_img","activity1_url",limitParam1);		//活动图
	publicService.uploadImage("activity2","activity2_img","activity2_url",limitParam1);		//活动图
	publicService.uploadImage("activity3","activity3_img","activity3_url",limitParam1);		//活动图
	publicService.initDate("limit_time");   //报名截止时间
	publicService.initDate("start_time");   //活动日期（始）
	publicService.initDate("end_time");   	 //活动日期（止）
	$scope.initJoinPartner();	//参与合伙人
	$scope.initJoinClass();		//参与品类
	$scope.initDiscountActivityType("activity_type");	//活动类型
	
	$scope.addDiscountParam('discountType_1',1);
	$scope.addDiscountParam('discountType_2',2);
	$scope.addDiscountParam('discountType_3',3);
	
	if(params.method=="add"){
		
	}else if(params.method=="edit"){
		$scope.getActivityInfo(params.activityId);		//获取活动信息
		
		$("#limit_time").data("kendoDatePicker").readonly(true);
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		$("#join_partner").data("kendoMultiSelect").readonly(true);
		$("#join_class").data("kendoMultiSelect").readonly(true);
		$("#activity_type").data("kendoDropDownList").readonly(true);
		
	}else if(params.method=="look"){
		$scope.getActivityInfo(params.activityId);		//获取活动信息
		
		$(".idMessage").find("input").attr("readonly","readonly");
		$(".idMessage").find("textarea").attr("readonly","readonly");
		$("#limit_time").data("kendoDatePicker").readonly(true);
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		$("#join_partner").data("kendoMultiSelect").readonly(true);
		$("#join_class").data("kendoMultiSelect").readonly(true);
		$("#activity_type").data("kendoDropDownList").readonly(true);
		
		$("#formSubmitButton").hide();
		$(".operateContainer").hide();	
		$(".addNewDiscountParam").hide();
	}
	

});