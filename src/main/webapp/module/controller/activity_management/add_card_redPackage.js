App.controller("addCardAndRedPackageCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, $timeout, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.form1 = {};     //表单
	$scope.form2 = {};     //表单
	$scope.form3 = {};     //表单
	$scope.brandName = "品牌名称";
	var timeLimit = {};	//时间限制
	var cardMessage = null;	//卡券信息
	
	var ztreeObj_1 = null;	//使用范围（优惠券）
	var ztreeObj_2 = null;	//使用范围（红包）
	var ztreeObj_3 = null;	//使用范围（折扣券）
	
	$scope.brand_show = true;		//品牌名称
	$scope.useRange_show = true;	//使用范围
	$scope.coupons_price_show = true;	//购买金额


	
	//初始化领取条件
	$scope.initCondition = function(){
		$("#condition").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "直接领取"
			             },
			             {
			            	 id: 2,
			 				text: "关注品牌领取"
			             }
			             ]
		});
	}
	
	//获取当前用户信息
	$scope.getCurrentUserInfo = function(){
		$http({
			   url: path+'/server/getCurrentUserInfo',
			   method: 'GET'
		}).success(function(data){  
			console.info("后台信息：",data);
			var backInfo = data;
			
			if(data){
				$scope.brandName = backInfo.buildEnterpriseEntity.name;
			}
			
		}).error(function(data){
			alert("获取用户信息失败！");   
		})
	}
	
	//获取所有合伙人
	$scope.getAllPartner = function(){
		$http({
			   url: path+'/server/queryPartner',
			   method: 'GET'
		}).success(function(data){  
			if(data.code==0){  //成功
				var partnerArr = data.result;
				
				$scope.getAllBrand(partnerArr);
			}else if(data.code==1){  //失败
				alert("获取合伙人信息失败！"); 
			}
		}).error(function(data){
			alert("获取合伙人信息失败！");   
		})
	}
	
	//获取所有品牌
	$scope.getAllBrand = function(partnerArr){
		$http({
			   url: path+'/server/querybrands',
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				var brandArr = data.result;
				
				if(params.method=="add"){
					$scope.packPartnerAndBrandData(partnerArr,brandArr);
				}else if(params.method=="edit" || params.method=="look"){
					$scope.isHaveCardMessage(partnerArr,brandArr);
				}
			}else if(data.code==1){  //失败
				alert("获取品牌信息失败！");
			}
			
			
		}).error(function(data){
			alert("获取品牌信息失败！");   
		})
	}
	
	//是否获得合伙人信息
	$scope.isHaveCardMessage = function(partnerArr,brandArr){
		if(cardMessage){
			$scope.packPartnerAndBrandData(partnerArr,brandArr);
		}else{
			$timeout(function(){
				$scope.isHaveCardMessage(partnerArr,brandArr);
		    }, 100);
		}
	}
	
	//封装合伙人品牌数据
	$scope.packPartnerAndBrandData = function(partnerArr,brandArr){
		var dataArr = [];
		var index_sign = 1;		//伪id
		
		var allChoose = {
				id: -100,
				trueId: -100,
				name: "全部",
				pid: null,
				open: true
		};
		dataArr.push(allChoose);
		
		$.each(partnerArr, function(index, OneObj){
			var onePartner = {
					id: index_sign,
					trueId: OneObj.id,
					name: OneObj.principal,
					pid: -100
			};
			dataArr.push(onePartner);	//合伙人
			index_sign++;
			
			$.each(brandArr, function(index2, OneObj2){
				if(OneObj2.citypartner_id==OneObj.id){
					var oneBrand = {};
					
					if(params.method=="add"){
						oneBrand = {
								id:	index_sign,
								trueId: OneObj2.id,
								name: OneObj2.brand_name,
								pid: onePartner.id
							};
					}else if(params.method=="edit" || params.method=="look"){
						if(cardMessage.brand_id.indexOf(OneObj2.id)>-1){
							oneBrand = {
									id:	index_sign,
									trueId: OneObj2.id,
									name: OneObj2.brand_name,
									pid: onePartner.id,
									checked: true
								};
						}else{
							oneBrand = {
									id:	index_sign,
									trueId: OneObj2.id,
									name: OneObj2.brand_name,
									pid: onePartner.id,
									checked: false
								};
						}
					}
			
					dataArr.push(oneBrand);	//品牌
					index_sign++;
				}
			});
		});
		console.log(dataArr);
		
		if(params.method=="add"){
			ztreeObj_1 = publicService.initZtree("ztree1",dataArr);
			ztreeObj_2 = publicService.initZtree("ztree2",dataArr);
			ztreeObj_3 = publicService.initZtree("ztree3",dataArr);
		}else if(params.method=="edit" || params.method=="look"){
			if(params.type==2){	//优惠券
				ztreeObj_1 = publicService.initZtree("ztree1",dataArr);
			}else if(params.type==1){	//红包
				ztreeObj_2 = publicService.initZtree("ztree2",dataArr);
			}else if(params.type==3){	//折扣券
				ztreeObj_3 = publicService.initZtree("ztree3",dataArr);
			}
		}
	}
	
	//选择面板
	$scope.choosePannel = function(type){
		for(var i=1;i<=3;i++){
			if(i==type){
				$("#pannel_"+i).addClass("choose");
				$("#container_"+i).show();
			}else{
				$("#pannel_"+i).removeClass("choose");
				$("#container_"+i).hide();
			}
		}
	}
	
	//检测名称
	$scope.checkName = function(type){ 
		if(params.method=="look"){
			return;
		}
		
		var postData = {};
		
		//卡券类型
		if(type==1){	//优惠券	
			postData.type = 2;
		}else if(type==2){	//红包
			postData.type = 1;
		}else if(type==3){	//折扣券
			postData.type = 3;
		}
		
		//卡券id
		if(cardMessage && cardMessage.id){
			postData.id = cardMessage.id;	
		}
		//卡券面额
		if(type==1){	//优惠券	
			postData.value = $scope.form1.value;
			if(!postData.value){
				alert("优惠券面额不能为空！");
				return;
			}
		}else if(type==2){	//红包
			postData.value = $scope.form2.value;
			if(!postData.value){
				alert("红包面额不能为空！");
				return;
			}
		}else if(type==3){	//折扣券
			postData.value = $scope.form3.value;
			if(!postData.value){
				alert("折扣力度不能为空！");
				return;
			}
		}

		$http({
			   url:	path+'/server/checkname',
			   method: 'POST',   
			   data: angular.toJson(postData),  
		}).success(function(data){
			if(data.code==0){  //成功
				timeLimit = data.result;
				/*var minTime = timeLimit.min_start_time;	//min
				var maxTime = timeLimit.max_end_time;	//max
				
				if(minTime && maxTime){
					if(type==1){	//优惠券
						alert("同面额的优惠券在【"+minTime+"~"+maxTime+"】已经存在，请选择其他时间段！");
					}else if(type==2){	//红包
						alert("同面额的红包在【"+minTime+"~"+maxTime+"】已经存在，请选择其他时间段！");
					}
				}*/
			}else if(data.code==1){  //失败
				if(type==1){	//优惠券	
					alert("【优惠券】验证失败！");
				}else if(type==2){	//红包
					alert("【红包】验证失败！");
				}else if(type==3){	//红包
					alert("【折扣券】验证失败！");
				}
			}
		}).error(function(data){
			alert("系统异常！");   
		})
	}

	//表单提交
	$scope.formSubmit = function(type){
		if(type==1){   //优惠券设定
			$scope.form1.start_time = $("#startTime1").val();
			$scope.form1.end_time = $("#endTime1").val();
			$scope.form1.type = 2;

			$scope.form = $scope.form1;
			
			//参数验证
			if(!$scope.form.name){
				alert("请填写【优惠券名称】！");
				return;
			}
			if(!$scope.form.value){
				alert("请填写【优惠券面额】！");
				return;
			}
			if($scope.form.coupons_price==undefined || $scope.form.coupons_price<0){
				alert("请填写【购买金额】！");
				return;
			}
			if($scope.form.step_value==undefined || $scope.form.step_value<0){
				alert("请填写【使用条件】！");
				return;
			}
			if(!$scope.form.start_time){
				alert("请填写【使用日期】！");
				return;
			}
			if(!$scope.form.end_time){
				alert("请填写【到期日期】！");
				return;
			}
			if(!$scope.form.total_count){
				alert("请填写【发行数量】！");
				return;
			}
			/*if(!$scope.form.limit_count){
				alert("请填写【每人限领】！");
				return;
			}*/
			
			//使用范围
			if(userInfo.user_type==1){  //开发者
				$scope.form.brand_id = [];
				var checkedNodes = ztreeObj_1.ztree.getCheckedNodes(true);

				$.each(checkedNodes, function(index, OneObj){
					if(OneObj.level==2){
						$scope.form.brand_id.push(OneObj.trueId);
					}
				});
				
				if($scope.form.brand_id.length==0){  //开发者
					alert("请填写【使用范围】！");
					return;
				}
			}
		}else if(type==2){	//红包设定
			//$scope.form2.gain = $("#condition").data("kendoDropDownList").value();   //领取条件
			$scope.form2.start_time = $("#startTime2").val();
			$scope.form2.end_time = $("#endTime2").val();
			$scope.form2.type = 1;
			
			$scope.form = $scope.form2;
			
			//参数验证
			if(!$scope.form.name){
				alert("请填写【红包名称】！");
				return;
			}
			if(!$scope.form.value){
				alert("请填写【红包面额】！");
				return;
			}
			if($scope.form.coupons_price==undefined || $scope.form.coupons_price<0){
				alert("请填写【购买金额】！");
				return;
			}
			if(!$scope.form.start_time){
				alert("请填写【使用日期】！");
				return;
			}
			if(!$scope.form.end_time){
				alert("请填写【到期日期】！");
				return;
			}
			if(!$scope.form.total_count){
				alert("请填写【发行数量】！");
				return;
			}
			/*if(!$scope.form.limit_count){
				alert("请填写【每人限领】！");
				return;
			}*/
			//使用范围
			if(userInfo.user_type==1){  //开发者
				$scope.form.brand_id = [];
				var checkedNodes = ztreeObj_2.ztree.getCheckedNodes(true);

				$.each(checkedNodes, function(index, OneObj){
					if(OneObj.level==2){
						$scope.form.brand_id.push(OneObj.trueId);
					}
				});
				
				if($scope.form.brand_id.length==0){  //开发者
					alert("请填写【使用范围】！");
					return;
				}
			}
		}else if(type==3){   //折扣券设定
			$scope.form3.start_time = $("#startTime3").val();
			$scope.form3.end_time = $("#endTime3").val();
			$scope.form3.type = 3;

			$scope.form = $scope.form3;
			
			//参数验证
			if(!$scope.form.name){
				alert("请填写【折扣券名称】！");
				return;
			}
			if(!$scope.form.value){
				alert("请填写【折扣力度】！");
				return;
			}else if($scope.form.value<=0 || $scope.form.value>1){
				alert("请填写正确的【折扣力度】，范围为(0,1]！");
				return;
			}
			if($scope.form.coupons_price==undefined || $scope.form.coupons_price<0){
				alert("请填写【购买金额】！");
				return;
			}
			if($scope.form.step_value==undefined || $scope.form.step_value<0){
				alert("请填写【使用条件】！");
				return;
			}
			if(!$scope.form.start_time){
				alert("请填写【使用日期】！");
				return;
			}
			if(!$scope.form.end_time){
				alert("请填写【到期日期】！");
				return;
			}
			if(!$scope.form.total_count){
				alert("请填写【发行数量】！");
				return;
			}
			
			
			//使用范围
			if(userInfo.user_type==1){  //开发者
				$scope.form.brand_id = [];
				var checkedNodes = ztreeObj_3.ztree.getCheckedNodes(true);

				$.each(checkedNodes, function(index, OneObj){
					if(OneObj.level==2){
						$scope.form.brand_id.push(OneObj.trueId);
					}
				});
				
				if($scope.form.brand_id.length==0){  //开发者
					alert("请填写【使用范围】！");
					return;
				}
			}
		}
		
		//验证
		if($scope.form.value && $scope.form.start_time && $scope.form.end_time){
			if(timeLimit.min_start_time && timeLimit.max_end_time){	//时间段限制
				var paramObject = {
						start_1: timeLimit.min_start_time,
						end_1: timeLimit.max_end_time,
						start_2: $scope.form.start_time,
						end_2: $scope.form.end_time
				};
				
				if(publicService.checkTwoTime(paramObject.start_2,paramObject.end_2)){
					if(publicService.checkTwoTimeRangeIsCross(paramObject)){
						console.log("【时间验证】：true！");
					}else{
						alert("填写时间范围与【"+paramObject.start_1+"~"+paramObject.end_1+"】有重合，请重新选择！");
						$scope.form.start_time = "";
						$scope.form.end_time = "";
						return;
					}
				}else{
					alert("请填写正确的时间范围!");
					return;
				}
			}
		}else{
			alert("请填写完整信息！");
			return;
		}

		
		var url = '';
		if(params.method=="add"){
			url = path+'/server/createcoupons';
		}else if(params.method=="edit"){
			url = path+'/server/modifycoupons';
			$scope.form.id = cardMessage.id;
		}
		
		
		$http({
			   url: url,
			   method: 'POST',   
			   data: angular.toJson($scope.form),  
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("保存失败！");   
		})
	}
	
	//根据id获取卡券信息
	$scope.getCardMessageById = function(id){
		$http({
			   url: path+'/server/getcouponsbyid/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				cardMessage = data.result;
				
				if(params.type==2){	//优惠券
					$scope.form1.name = cardMessage.name;	//优惠券名称
					$scope.form1.value = cardMessage.value;	//优惠券面额
					$scope.form1.coupons_price = cardMessage.coupons_price;		//购买金额
					$scope.form1.step_value = cardMessage.step_value;			//使用条件
					$("#startTime1").val(cardMessage.start_time);	//使用日期
					$("#endTime1").val(cardMessage.end_time);		//到期日期
					$scope.form1.total_count = cardMessage.total_count;	//发行数量
					$scope.form1.details = cardMessage.details;			//使用说明
					//$scope.form1.limit_count = cardMessage.limit_count;	//每人限领
					if(cardMessage.brand_name){
						$scope.brandName = cardMessage.brand_name;	//品牌名称
					}
					

					if(params.method=="edit"){
						$scope.checkName(1);
					}
				}else if(params.type==1){	//红包
					$scope.form2.name = cardMessage.name;	//红包名称
					$scope.form2.value = cardMessage.value;	//红包面额
					$scope.form2.coupons_price = cardMessage.coupons_price;	//购买金额
					$("#startTime2").val(cardMessage.start_time);	//使用日期
					$("#endTime2").val(cardMessage.end_time);		//到期日期
					$scope.form2.total_count = cardMessage.total_count;	//发行数量
					$scope.form2.details = cardMessage.details;			//使用说明
					//$scope.form2.limit_count = .limit_count;	//每人限领
					if(cardMessage.brand_name){
						$scope.brandName = cardMessage.brand_name;	//品牌名称
					}
					

					if(params.method=="edit"){
						$scope.checkName(2);
					}
				}else if(params.type==3){	//折扣券
					$scope.form3.name = cardMessage.name;	//折扣券名称
					$scope.form3.value = cardMessage.value;	//折扣力度
					$scope.form3.coupons_price = cardMessage.coupons_price;		//购买金额
					$scope.form3.step_value = cardMessage.step_value;			//使用条件
					$("#startTime3").val(cardMessage.start_time);	//使用日期
					$("#endTime3").val(cardMessage.end_time);		//到期日期
					$scope.form3.total_count = cardMessage.total_count;	//发行数量
					$scope.form3.details = cardMessage.details;			//使用说明
					if(cardMessage.brand_name){
						$scope.brandName = cardMessage.brand_name;	//品牌名称
					}
					
					if(params.method=="edit"){
						$scope.checkName(3);
					}
				}
			}else if(data.code==1){  //失败
				alert("查询卡券信息失败！");
			}
		}).error(function(data){
			alert(data);   
		})
	}

	
	$scope.choosePannel(1);		//选择面板
	publicService.initDateTime("startTime1");
	publicService.initDateTime("endTime1");
	publicService.initDateTime("startTime2");
	publicService.initDateTime("endTime2");
	publicService.initDateTime("startTime3");
	publicService.initDateTime("endTime3");
	//$scope.initCondition();		//初始化领取条件
	
	
	/*权限分配*/
	if(userInfo.user_type==1){  //开发者
		$scope.brand_show = false;
		
		$scope.getAllPartner();		//获取所有合伙人
	}else if(userInfo.user_type==4){  //品牌
		$scope.useRange_show = false;
		
		$scope.form1.coupons_price = 0;
		$scope.form2.coupons_price = 0;
		$scope.form3.coupons_price = 0;
		$scope.coupons_price_show = false;
	}
	
	if(params.method=="add"){
		$scope.getCurrentUserInfo();    //获取当前用户信息
	}else if(params.method=="edit"){
		if(params.type==2){	//优惠券
			$scope.choosePannel(1);		//选择面板
			$("#pannel_2").hide();
			$("#pannel_3").hide();
		}else if(params.type==1){	//红包
			$scope.choosePannel(2);		//选择面板
			$("#pannel_1").hide();
			$("#pannel_3").hide();
		}else if(params.type==3){	//折扣券
			$scope.choosePannel(3);		//选择面板
			$("#pannel_1").hide();
			$("#pannel_2").hide();
		}
		$scope.getCardMessageById(params.cardId);
	}else if(params.method=="look"){
		if(params.type==2){	//优惠券
			$scope.choosePannel(1);		//选择面板
			$("#pannel_2").hide();
			$("#pannel_3").hide();
			$("#submitBtn1").hide();
		}else if(params.type==1){	//红包
			$scope.choosePannel(2);		//选择面板
			$("#pannel_1").hide();
			$("#pannel_3").hide();
			$("#submitBtn2").hide();
		}else if(params.type==3){	//折扣券
			$scope.choosePannel(3);		//选择面板
			$("#pannel_1").hide();
			$("#pannel_2").hide();
			$("#submitBtn3").hide();
		}
		$scope.getCardMessageById(params.cardId);
		
		$("input").attr("readonly","readonly");
	}
	
});