App.controller("activitySignCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.info("路由参数：",params);


	$scope.activityInfo = {};
	$scope.activityType = null;	//活动类型
	var activityInfo = null;	//活动信息

	
	//活动报名
	$scope.discountActivityJoin = function(id){
		var postData = {
				id: id
		};

		$http({
			   url: path+'/server/registerPlatformStepPromotion',
			   method: 'POST',   
			   data: angular.toJson(postData),  
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("报名失败！");   
		})
	}
	
	//活动取消
	$scope.discountActivityCancel = function(id){
		$http({
			   url: path+'/server/cancleRegisterPlatformStepPromotion/'+id,
			   method: 'GET',    
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("取消失败！");   
		})
	}
	
	//活动报名
	$scope.join = function(id){
		var postData = {
				id: id
		};

		$http({
			   url: path+'/server/registerPromotionforbrand',
			   method: 'POST',   
			   data: angular.toJson(postData),  
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("报名失败！");   
		})
	}
	
	//活动取消
	$scope.cancel = function(id){
		var postData = {
				id: id
		};

		$http({
			   url: path+'/server/cancleregisterPromotionforbrand',
			   method: 'POST',   
			   data: angular.toJson(postData),  
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("取消失败！");   
		})
	}
	
	//获取活动信息
	$scope.getActivityInfo = function(id,type){
		var url = '';
		if(type==0){
			url = path+'/server/getplatformpromotionbyid/'+id;
		}else if(type==1 || type==2 || type==3){
			url = path+'/server/getPlatformStepPromotionByid/'+id;
		}
		
		$http({
			   url: url,
			   method: 'GET'
		}).success(function(data){
			if(type==0){
				activityInfo = data;
			}else if(type==1 || type==2 || type==3){
				activityInfo = data.result;
			}
			
			$scope.activityInfo.title = activityInfo.title;		//活动名称
			$scope.activityType = activityInfo.type;	//活动类型
			var activityType = '';
			if(activityInfo.type==0){
				activityType = '秒杀活动';
			}else if(activityInfo.type==1){
				activityType = '满额折扣';
			}else if(activityInfo.type==2){
				activityType = '满减折扣';
			}else if(activityInfo.type==3){
				activityType = '数量折扣';
			}
			$scope.activityInfo.type = activityType;		//活动类型
			$scope.activityInfo.limit_time = activityInfo.limit_time;	//报名截止日期
			$scope.activityInfo.time = activityInfo.start_time+" ~ "+activityInfo.end_time;	//活动时间
			
			if(activityInfo.type==0){	//秒杀活动
				$("#activityType_1").show();
				$("#activityType_2").hide();
				
				$scope.activityInfo.promotion_product_num = "每个品牌最多可参与  "+activityInfo.promotion_product_num+" 款产品";	//参与数量

				var html = '';
				
				$.each(activityInfo.timeEntity, function(index, OneObj){
					if(OneObj.pstart_time_str && OneObj.pend_time_str){
						var time = OneObj.pstart_time_str+"~"+OneObj.pend_time_str;
						
						if(OneObj.has_registered_flag==0){	//没参加
							if(OneObj.brand_id_count && OneObj.brand_id_count>=OneObj.promotion_brand_num){
								html += ('<div class="form-horizontal form-widgets col-sm-12">'
											+'<div class="form-group">'
												+'<label class="control-label col-sm-4">活动时间：</label>'
												+'<div class="col-sm-1 info">'+time+'</div>'
												+'<div class="col-sm-1">'
												
												+'</div>'
												+'<div class="col-sm-3 tips">当前时间段报名数量已满！</div>'
											+'</div>'
										+'</div>');
							}else{
								html += ('<div class="form-horizontal form-widgets col-sm-12">'
											+'<div class="form-group">'
												+'<label class="control-label col-sm-4">活动时间：</label>'
												+'<div class="col-sm-1 info">'+time+'</div>'
												+'<div class="col-sm-1">'
													+'<button class="k-button k-state-default" ng-click="join('+OneObj.id+')">报名</button>'
												+'</div>'
											+'</div>'
										+'</div>');
							}
						}else if(OneObj.has_registered_flag==1){	//已参加
							if(OneObj.brand_id_count && OneObj.brand_id_count>=OneObj.promotion_brand_num){
								html += ('<div class="form-horizontal form-widgets col-sm-12">'
											+'<div class="form-group">'
												+'<label class="control-label col-sm-4">活动时间：</label>'
												+'<div class="col-sm-1 info">'+time+'</div>'
												+'<div class="col-sm-1">'
													+'<button class="k-button k-state-default" ng-click="cancel('+OneObj.id+')">取消</button>'
												+'</div>'
												+'<div class="col-sm-3 tips">当前时间段报名数量已满！</div>'
											+'</div>'
										+'</div>');
							}else{
								html += ('<div class="form-horizontal form-widgets col-sm-12">'
											+'<div class="form-group">'
												+'<label class="control-label col-sm-4">活动时间：</label>'
												+'<div class="col-sm-1 info">'+time+'</div>'
												+'<div class="col-sm-1">'
													+'<button class="k-button k-state-default" ng-click="cancel('+OneObj.id+')">取消</button>'
												+'</div>'
											+'</div>'
										+'</div>');
							}
						}
					}
				});
				
				html = $compile(html)($scope);   //angularJs代码需要动态编译
				$("#timeRange").html(html);
			}else if(activityInfo.type==1 || activityInfo.type==2 || activityInfo.type==3){	//折扣活动
				$("#activityType_1").hide();
				$("#activityType_2").show();
				
				$scope.activityInfo.content = activityInfo.content;	//活动说明
				$scope.discountParam = activityInfo.stepConditionEntityList; //折扣设置参数
				
				var html = '';
				
				if(activityInfo.has_registered_flag==0){	//没参加
					html += ('<button class="k-button k-state-default button_center" ng-click="discountActivityJoin(\''+activityInfo.id+'\')">报名</button>');
				}else if(activityInfo.has_registered_flag==1){	//已参加
					html += ('<button class="k-button k-state-default button_center" ng-click="discountActivityCancel(\''+activityInfo.id+'\')">取消</button>');
				}
				
				html = $compile(html)($scope);   //angularJs代码需要动态编译
				$("#discountOperate").html(html);
			}
			

		}).error(function(data){
			alert("获取活动信息失败！");   
		})
		
	}
	
	
	

	$scope.getActivityInfo(params.activityId,params.activityType);		//获取活动信息


});