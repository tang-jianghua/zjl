App.controller("activityCheckCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.info("路由参数：",params);


	var activityInfo = null;	//活动信息
	var brandArr = null;		//品牌列表信息
	var timeRangeCount = 0;		//时间段数量
	var showTimeId = 0;			//显示的时间段Id
	var chooseCheckBox = '';	//选择的产品复选框
	

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
				if(OneObj.pstart_time_str && OneObj.pend_time_str){
					timeRangeCount ++;
					var time_index = index+1;
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
				if(params.method=="activityCheck"){	//活动审核（未开始）
					$scope.getAllBrandProduct([0,1,2,5]);		
				}else if(params.method=="productManagement_1"){	//产品管理（进行中）
					$scope.getAllBrandProduct([1,3,5]);		
				}else if(params.method=="productManagement_2"){	//产品管理（已结束）
					$scope.getAllBrandProduct([1,3,5]);		
				}
			}else{
				angular.element("#jump_"+i).removeClass("timeChoose");
			}
		}
	}

	//获取所有品牌下的产品
	$scope.getAllBrandProduct = function(stateArr){
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
				brandArr = data;
				
				if(brandArr.brandProduct){
					if(data.brandProduct.length>0){
						$scope.showAllBrandProduct(brandArr.brandProduct);	//显示所有品牌下的产品
					}else{
						$("#brandProductInfo").html("");
					}
				}else{
					$("#brandProductInfo").html("");
					alert("当前时间段下暂无品牌报名产品！");
				}
			}).error(function(data){
				alert("查询产品信息失败！");   
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
					brandArr = data.result;
					
					if(brandArr.brandProduct){
						if(brandArr.brandProduct.length>0){
							$scope.showAllBrandProduct(brandArr.brandProduct);	//显示所有品牌下的产品
						}else{
							$("#brandProductInfo").html("");
						}
					}else{
						$("#brandProductInfo").html("");
						alert("当前活动下暂无品牌报名产品！");
					}
				}else if(data.code==1){
					alert("查询产品信息失败！");
				}
			}).error(function(data){
				alert("查询产品信息失败！");   
			})
		}
		
		
	}
	
	//显示所有品牌下的产品
	$scope.showAllBrandProduct = function(brandProductData){
		var html = '';
		
		$.each(brandProductData, function(index, OneObj){
			var checkboxClass = "brand_"+(index+1)+"_product";
			//品牌信息
			html += ('<div class="col-sm-12 bigTitle2">'
						+'<div class="col-sm-1">品牌</div>'
						+'<div class="col-sm-2">'+OneObj.brand_name+'</div>'
						+'<div class="col-sm-2">品牌信息</div>'
						+'<div class="col-sm-2">联系人：'+OneObj.principal+'</div>'
						+'<div class="col-sm-3">联系电话：'+OneObj.phone_num+'</div>'
						+'<div class="col-sm-2">展开查看详细商品</div>'
					+'</div>');
			if(params.method=="activityCheck"){	//活动审核
				//操作
				html += ('<div class="operateContainer">'
							+'<button class="k-button k-state-default" ng-click="isChooseAll(\''+checkboxClass+'\',true)">全选</button>'
							+'<button class="k-button k-state-default" ng-click="isChooseAll(\''+checkboxClass+'\',false)">取消</button>'
						+'</div>');
				//产品信息
				html += ('<div class="col-sm-12 seckillBig">'
							+$scope.getProductHtml_cancel(OneObj.product_state5,checkboxClass,1)	//申请取消
							+$scope.getProductHtml_cancel(OneObj.product_state0,checkboxClass,0)	//未审核
							+$scope.getProductHtml_pass(OneObj.product_state1,checkboxClass,1)		//审核通过
							+$scope.getProductHtml_pass(OneObj.product_state2,checkboxClass,0)		//审核未通过
							
							
						+'</div>');
				//审核
				html += ('<div class="buttons-wrap buttonContainer">'
							+'<button class="k-button k-state-default " ng-click="productCheck(\''+checkboxClass+'\',1)">审核通过</button>'
							+'<button class="k-button k-state-default " ng-click="unPassCheck(\''+checkboxClass+'\')">未通过审核</button>'
						+'</div>');
			}else if(params.method=="productManagement_1"){	//产品管理（进行中）
				//产品信息
				html += ('<div class="col-sm-12 seckillBig">'
							+$scope.getProductHtml_down(OneObj.product_state3,1)	//申请下架
							+$scope.getProductHtml_down(OneObj.product_state1,0)	//审核通过
							+$scope.getProductHtml_down(OneObj.product_state5,0)	//申请取消
						+'</div>');
			}else if(params.method=="productManagement_2"){	//产品管理（已结束）
				//产品信息
				html += ('<div class="col-sm-12 seckillBig">'
							+$scope.getProductHtml(OneObj.product_state3)	//申请下架
							+$scope.getProductHtml(OneObj.product_state1)	//审核通过
							+$scope.getProductHtml(OneObj.product_state5)	//申请取消
						+'</div>');
			}
		});
		
		html += ('<div style="clear: both;"></div>');
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#brandProductInfo").html(html);
	}
	
	//获取产品html(申请取消)
	$scope.getProductHtml_cancel = function(productData,checkboxClass,type){
		var html = '';

		$.each(productData, function(index, OneObj){
			var operate_html = '';		//按钮操作
			var checkbox_html = '';		//复选操作
			var mainId = '';
			
			if(params.activityType==0){
				mainId = OneObj.product_id;
			}else if(params.activityType==1 || params.activityType==2 || params.activityType==3){
				mainId = OneObj.id;
			}
			if(type==0){
				operate_html = '<div><button class="k-button k-state-default button_center" disabled="disabled" style="background-color:gray !important;color:white;">同意取消</button></div>';
				checkbox_html = ('<div class="operate">'
									+'<div><input type="checkbox" class="'+checkboxClass+'" value="'+mainId+'"/></div>'
								+'</div>');
			}else if(type==1){
				operate_html = '<div><button class="k-button k-state-default button_center" ng-click="removeProduct(\''+mainId+'\')">同意取消</button></div>';
			}
			
			html += ('<div class="col-sm-3 oneProduct push">'
						+$scope.packOneProductHtml(OneObj,checkbox_html)
						+operate_html
					+'</div>');
		});
		
		return html;
	}
	
	//获取产品html(审核通过)
	$scope.getProductHtml_pass = function(productData,checkboxClass,type){
		var html = '';

		$.each(productData, function(index, OneObj){
			var state_html = '';		//状态
			
			if(type==0){
				state_html = '<span style="color:red;cursor:pointer;" title="'+OneObj.info+'">审核未通过<span>';
			}else if(type==1){
				state_html = '<span style="color:green;">审核通过<span>';
			}
			
			html += ('<div class="col-sm-3 oneProduct push">'
						+$scope.packOneProductHtml(OneObj)
						+'<div class="text">'
							+'<span>状态：'+state_html+'</span>'
						+'</div>'
					+'</div>');
		});
		
		return html;
	}
	
	//获取产品html(强制下架)
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
				operate_html = '<div><button class="k-button k-state-default button_center" disabled="disabled" style="background-color:gray !important;color:white;">强制下架</button></div>';
			}else if(type==1){
				operate_html = '<div><button class="k-button k-state-default button_center" ng-click="productCheck(null,4,\''+mainId+'\')">强制下架</button></div>';
			}
			
			html += ('<div class="col-sm-3 oneProduct push">'
						+$scope.packOneProductHtml(OneObj)
						+operate_html
					+'</div>');
		});
		
		return html;
	}
	
	//获取产品html
	$scope.getProductHtml = function(productData,type){
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
	$scope.isChooseAll = function(className,flag){
		publicService.chooseAllByClassName(className,flag);
	}
	
	//未通过审核
	$scope.unPassCheck = function(className){
		chooseCheckBox = className;
		$scope.info = '';
		$scope.unCheckWindow.center().open();   //打开弹框
	}
	
	//产品审核
	$scope.productCheck = function(className,flag,productId){
		var postData = [];
		var productIdArr = [];
		
		if(flag==1){	//通过
			productIdArr = publicService.getChooseValueByClassName(className);
		}else if(flag==2){	//不通过
			productIdArr = publicService.getChooseValueByClassName(chooseCheckBox);
		}else if(flag==4){	//下架
			productIdArr.push(productId);
		}
		
		if(productIdArr.length>0){
			if(flag==2 && !$scope.info){
				alert("请填写审核不通过的理由！");
				return;
			}
			
			if(params.activityType==0){
				$.each(productIdArr, function(index, OneObj){
					var oneProduct = {};
					oneProduct.promotion_id = params.activityId;
					oneProduct.time_id = showTimeId;
					oneProduct.product_id = OneObj;
					oneProduct.state = flag;
					if(flag==2){	//不通过
						oneProduct.info = $scope.info;
					}
					
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
					if(flag==2){	//不通过
						oneProduct.info = $scope.info;
					}
					
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
			alert("请选择要审核的产品！");
		}
	}
	
	//移除产品
	$scope.removeProduct = function(id){
		if(params.activityType==0){
			var postData = [{
				promotion_id: params.activityId,
				time_id: showTimeId,
				product_id: id
			}];
			
			$http({
				   url: path+'/server/removebrandpromotionproduct',
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
			$http({
				   url: path+'/server/removeStepPromotionProductForBrand',
				   method: 'POST',
				   data: angular.toJson([id]), 
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
	}

	
	$scope.getActivityInfo(params.activityId,params.activityType);		//获取活动信息

	
});