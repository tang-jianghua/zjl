App.controller("signProductDetailsCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.info("路由参数：",params);


	var activityInfo = null;	//活动信息
	var timeRangeCount = 0;		//时间段数量
	var showTimeId = 0;			//显示的时间段Id
	var brandInfo = [];			//品牌信息
	
	

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
				
				$scope.showTimeRange(type,activityInfo.timeEntity);	//显示时间段
				$scope.chooseJump(1);		//选择跳转
			}else if(type==1 || type==2 || type==3){
				activityInfo = data.result;
				
				$scope.showTimeRange(type);	//显示时间段
				$scope.chooseJump(1);		//选择跳转
			}
		}).error(function(data){
			alert("获取活动信息失败！");   
		})
	}
	
	//显示时间段
	$scope.showTimeRange = function(type,timeData){
		var html = '<ul>';
		
		if(type==0){
			$.each(timeData, function(index, OneObj){
				if(OneObj.has_registered_flag==1){
					timeRangeCount ++;
					var time_index = timeRangeCount;
					var time = OneObj.pstart_time_str+'-'+OneObj.pend_time_str;
					
					html += ('<li id="jump_'+time_index+'" timeId='+OneObj.id+' ng-click="chooseJump('+time_index+')">'+time+'</li>');
				}
			});
		}else if(type==1 || type==2 || type==3){
			timeRangeCount ++;
			var time_index = timeRangeCount;
			var time = activityInfo.start_time+'~'+activityInfo.end_time;
			
			html += ('<li id="jump_'+time_index+'" timeId='+activityInfo.id+' ng-click="chooseJump('+time_index+')">'+time+'</li>');
		}
		
		html += '</ul>';
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#timeRange").html(html);
	}
	
	//选择跳转
	$scope.chooseJump = function(type){
		for(var i=1;i<=timeRangeCount;i++){
			if(i==type){
				angular.element("#jump_"+i).addClass("timeChoose");
				
				showTimeId = $("#jump_"+i).attr("timeId");
				if(params.method=="productSet"){	//产品设置
					$scope.getProductFromDifferentState([0,1,2,5]);	
				}else if(params.method=="productLook_0"){	//未开始
					$scope.getProductFromDifferentState([1,5]);	
				}else if(params.method=="productLook_1"){	//进行中
					$scope.getProductFromDifferentState([1,3,5]);	
				}else if(params.method=="productLook_2"){	//已结束
					$scope.getProductFromDifferentState([1,3,5]);	
				}
			}else{
				angular.element("#jump_"+i).removeClass("timeChoose");
			}
		}
	}

	//获取不同状态下的产品
	$scope.getProductFromDifferentState = function(stateArr){
		if(params.activityType==0){
			var postData = {
					pid: params.activityId,
					time_id: showTimeId,
					stateList: stateArr	//0：未审核,1:审核通过,2:审核未通过,3:申请下架,4:下架,5:申请取消
			};
			
			$http({
				   url: path+'/server/getProductOfOnepromotionForPlatform',
				   method: 'POST',
				   data: angular.toJson(postData), 
			}).success(function(data){
				$scope.showDifferentStateProduct(data.brandProduct);	
			}).error(function(data){
				alert("系统异常！");   
			})
		}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){
			var postData = {
					id: params.activityId,
					stateList: stateArr	//0：未审核,1:审核通过,2:审核未通过,3:申请下架,4:下架,5:申请取消
			};
			
			$http({
				   url: path+'/server/getStepPromotionProductForPlatform',
				   method: 'POST',
				   data: angular.toJson(postData), 
			}).success(function(data){
				if(data.code==0){
					$scope.showDifferentStateProduct(data.result.brandProduct);
				}else if(data.code==1){
					alert("查询产品信息失败！");
				}	
			}).error(function(data){
				alert("系统异常！");   
			})
		}
	}
	
	//显示不同状态的产品
	$scope.showDifferentStateProduct = function(brandProductArr){
		if(params.method=="productSet"){	//产品设置
			if(brandProductArr && brandProductArr.length>0){
				brandInfo = brandProductArr[0];
				$("#productCheck").show();
				
				if(brandInfo.product_state0.length>0){
					$("#container_state0").show();
					$scope.showProduct(0,"product_state0",brandInfo.product_state0,"checkbox_state0");	//未审核
				}else{
					$("#container_state0").hide();
					$("#product_state0").html("");
				}
				if(brandInfo.product_state1.length>0){
					$("#container_state1").show();
					$scope.showProduct(1,"product_state1",brandInfo.product_state1,"checkbox_state1");	//审核通过
				}else{
					$("#container_state1").hide();
					$("#product_state1").html("");
				}
				if(brandInfo.product_state2.length>0){
					$("#container_state2").show();
					$scope.showProduct(2,"product_state2",brandInfo.product_state2,"checkbox_state2");	//审核未通过
				}else{
					$("#container_state2").hide();
					$("#product_state2").html("");
				}
				if(brandInfo.product_state5.length>0){
					$("#container_state5").show();
					$scope.showProduct(5,"product_state5",brandInfo.product_state5,"checkbox_state5");	//申请取消
				}else{
					$("#container_state5").hide();
					$("#product_state5").html("");
				}
			}else{
				$("#productCheck").hide();
			}
		}else if(params.method=="productLook_0"){	//未开始
			if(brandProductArr && brandProductArr.length>0){
				brandInfo = brandProductArr[0];
				
				var html = $scope.getProductHtml_cancel(brandInfo.product_state5,0)	//申请取消
						  +$scope.getProductHtml_cancel(brandInfo.product_state1,1)	//审核通过
				
				html = $compile(html)($scope);   //angularJs代码需要动态编译
				$("#productLook").html(html);
			}else{
				$("#productLook").html("");
			}
		}else if(params.method=="productLook_1"){	//进行中
			if(brandProductArr && brandProductArr.length>0){
				brandInfo = brandProductArr[0];
				
				var html = $scope.getProductHtml_down(brandInfo.product_state3,0)	//申请下架
						  +$scope.getProductHtml_down(brandInfo.product_state1,1)	//审核通过
						  +$scope.getProductHtml_down(brandInfo.product_state5,1)	//申请取消
				
				html = $compile(html)($scope);   //angularJs代码需要动态编译
				$("#productLook").html(html);
			}else{
				$("#productLook").html("");
			}
		}else if(params.method=="productLook_2"){	//已结束
			if(brandProductArr && brandProductArr.length>0){
				brandInfo = brandProductArr[0];
				
				var html = $scope.getProductHtml(brandInfo.product_state3)	//申请下架
						  +$scope.getProductHtml(brandInfo.product_state1)	//审核通过
						  +$scope.getProductHtml(brandInfo.product_state5)	//申请取消
				
				html = $compile(html)($scope);   //angularJs代码需要动态编译
				$("#productLook").html(html);
			}else{
				$("#productLook").html("");
			}
		}
		
	}
	

	//显示产品
	$scope.showProduct = function(type,containerId,productArr,checkboxClass){
		var html = '';

		$.each(productArr, function(index, OneObj){
			var mainId = '';
			
			if(params.activityType==0){
				mainId = OneObj.product_id;
			}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){
				mainId = OneObj.id;
			}
			//复选操作
			var checkbox_html = ('<div class="operate">'
									+'<div><input type="checkbox" class="'+checkboxClass+'" value="'+mainId+'"/></div>'
								+'</div>');
			//审核失败原因
			var reason_html = '';
			if(type==2){
				reason_html = ('<div class="info">原因：'+OneObj.info+'</div>');
			}
			
			html += ('<div class="col-sm-3 oneProduct push">'
						+$scope.packOneProductHtml(OneObj,checkbox_html)
						+reason_html
					+'</div>');
			
		});

		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#"+containerId).html(html);
	}
	
	//获取产品html(申请取消)
	$scope.getProductHtml_cancel = function(productData,type){
		var html = '';
		
		$.each(productData, function(index, OneObj){
			var operate_html = '';
			var mainId = '';
			
			if(params.activityType==0){
				mainId = OneObj.product_id;
			}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){
				mainId = OneObj.id;
			}
			
			if(type==0){
				operate_html = '<div><button class="k-button k-state-default button_center" disabled="disabled" style="background-color:gray !important;color:white;">申请取消</button></div>';
			}else if(type==1){
				operate_html = '<div><button class="k-button k-state-default button_center" ng-click="modifyProductState(null,5,\''+mainId+'\')">申请取消</button></div>'
			}
			
			html += ('<div class="col-sm-3 oneProduct push">'
						+$scope.packOneProductHtml(OneObj)
						+operate_html
					+'</div>');
		});
		
		return html;
	}
	
	//获取产品html(申请下架)
	$scope.getProductHtml_down = function(productData,type){
		var html = '';
	
		$.each(productData, function(index, OneObj){
			var operate_html = '';
			var mainId = '';
			
			if(params.activityType==0){
				mainId = OneObj.product_id;
			}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){
				mainId = OneObj.id;
			}
			
			if(type==0){
				operate_html = '<div><button class="k-button k-state-default button_center" disabled="disabled" style="background-color:gray !important;color:white;">申请下架</button></div>';
			}else if(type==1){
				operate_html = '<div><button class="k-button k-state-default button_center" ng-click="modifyProductState(null,3,\''+mainId+'\')">申请下架</button></div>'
			}
			
			html += ('<div class="col-sm-3 oneProduct push">'
						+$scope.packOneProductHtml(OneObj)
						+operate_html
					+'</div>');
		});
		
		return html;
	}
	
	//获取产品html
	$scope.getProductHtml = function(productData){
		var html = '';

		$.each(productData, function(index, OneObj){
			html += ('<div class="col-sm-3 oneProduct push">'
						+$scope.packOneProductHtml(OneObj)
					+'</div>');
		});
		
		return html;
	}
	
	//封装单个产品的html
	$scope.packOneProductHtml = function(productInfo,checkbox_html){
		var html = '';
		
		if(!checkbox_html){
			checkbox_html = '';
		}
		
		if(params.activityType==0){
			html += ('<div class="image" style="background:url(\''+productInfo.image_url+'\');background-size:100% 100%;">'
						+checkbox_html
					+'</div>'
					+'<div class="title" title="'+productInfo.product_name+'">'+productInfo.product_name+'</div>'
					+'<div class="text">限购数量：'+productInfo.person_product_num+'件 </div>'
					+'<div class="text">库存：'+productInfo.sale_num+'('+productInfo.promotion_num+')件  <span class="redText">'+productInfo.promotion_price+'</span>元/'+productInfo.max_price+'元</div>');
		}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){
			html += ('<div class="image" style="background:url(\''+productInfo.image_url+'\');background-size:100% 100%;">'
						+checkbox_html
					+'</div>'
					+'<div class="title" title="'+productInfo.product_name+'">'+productInfo.product_name+'</div>'
					+'<div class="text">库存：'+productInfo.stock+'件  &nbsp;<span class="redText">'+productInfo.new_price+'</span>元</div>');
		}

		return html;
	}

	//全选，取消
	$scope.allChoose = function(className,flag){
		publicService.chooseAllByClassName(className,flag);
	}
	
	//移除产品
	$scope.removeProduct = function(className){
		var postData = [];
		
		var productIdArr = publicService.getChooseValueByClassName(className);
		if(productIdArr.length>0){
			if(params.activityType==0){
				$.each(productIdArr, function(index, OneObj){
					var oneProduct = {};
					oneProduct.promotion_id = params.activityId;
					oneProduct.time_id = showTimeId;
					oneProduct.product_id = OneObj;
					
					postData.push(oneProduct);
				});

				$http({
					   url: path+'/server/removebrandpromotionproduct',
					   method: 'POST',
					   data: angular.toJson(postData), 
				}).success(function(data){
					if(data.code==0){  //成功
						window.location.reload();   //页面刷新
					}else if(data.code==1){  //失败
						alert(data.message);
					}
				}).error(function(data){
					alert(data);   
				})
			}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){				
				$http({
					   url: path+'/server/removeStepPromotionProductForBrand',
					   method: 'POST',
					   data: angular.toJson(productIdArr), 
				}).success(function(data){
					if(data.code==0){  //成功
						window.location.reload();   //页面刷新
					}else if(data.code==1){  //失败
						alert(data.message);
					}
				}).error(function(data){
					alert(data);   
				})
			}
		}else{
			alert("请选择产品！");
		}
	}

	//编辑产品状态
	$scope.modifyProductState = function(className,flag,productId){
		var postData = [];
		var productIdArr = [];
		
		if(className && !productId){
			productIdArr = publicService.getChooseValueByClassName(className);
		}else{
			productIdArr.push(productId);
		}

		if(productIdArr.length>0){
			if(params.activityType==0){
				$.each(productIdArr, function(index, OneObj){
					var oneProduct = {};
					oneProduct.promotion_id = params.activityId;
					oneProduct.time_id = showTimeId;
					oneProduct.product_id = OneObj;
					oneProduct.state = flag;
					
					postData.push(oneProduct);
				});

				$http({
					   url: path+'/server/modifyonepromotionproduct',
					   method: 'POST',
					   data: angular.toJson(postData), 
				}).success(function(data){
					alert(data.message);
					if(data.code==0){  //成功
						window.location.reload();   //页面刷新
					}else if(data.code==1){  //失败
						
					}
				}).error(function(data){
					alert(data);   
				})
			}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){
				$.each(productIdArr, function(index, OneObj){
					var oneProduct = {};
					oneProduct.id = OneObj;
					oneProduct.state = flag;
					
					postData.push(oneProduct);
				});

				$http({
					   url: path+'/server/modifyStepPromotionProductForBrand',
					   method: 'POST',
					   data: angular.toJson(postData), 
				}).success(function(data){
					alert(data.message);
					if(data.code==0){  //成功
						window.location.reload();   //页面刷新
					}else if(data.code==1){  //失败
						
					}
				}).error(function(data){
					alert(data);   
				})
			}
		}else{
			alert("请选择产品！");
		}
	}

	//产品设置
	$scope.productSet = function(){
		var params2 = {method:"productSet",activityId:params.activityId,activityType:params.activityType};
		$location.path("/productSet/"+angular.toJson(params2));
	}
	
	$scope.getActivityInfo(params.activityId,params.activityType);		//获取活动信息
	
	if(params.method=="productSet"){	//产品设置
		$("#productLook").hide();
	}else if(params.method=="productLook_0" || params.method=="productLook_1" || params.method=="productLook_2"){	//产品查看
		$("#productSetButton").hide();
		$("#productCheck").hide();	
	}

	
});