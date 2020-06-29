App.controller("brandBackInformationCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, $sce) {
	
	$scope.bankEntity = {};  			//银行信息
	$scope.commerceEntity = {};  		//工商注册信息
	$scope.enterpriseEntity = {};  		//企业信息
	$scope.userEntity = {};  			//用户信息
	$scope.partnerEntity = {};  		//合伙人信息
	
	var backInfo = null;   //后台信息
	
	//获取当前用户信息
	$scope.getCurrentUserInfo = function(){
		$http({
			   url: path+'/server/getCurrentUserInfo',
			   method: 'GET'
		}).success(function(data){  
			console.info(data);
			backInfo = data;
			
			$scope.brand_name = data.buildEnterpriseEntity.name;
			
			$scope.principal = data.principal;
			$scope.phone_num = data.phone_num;
			
			$scope.imgurl = data.buildEnterpriseEntity.imgurl;
			$scope.showImg("logo_img",$scope.imgurl);
			
			$scope.enterprise_name = data.partnerEntity.enterpriseEntity.enterprise_name;
			$scope.partner_name = data.partnerEntity.principal;
			
			$scope.region_code = data.partnerEntity.region_id;  //服务区域
			$scope.showArea($scope.region_code);
			
			$scope.class_name = data.buildEnterpriseEntity.buildClassEntity.name;
			
			$scope.userEntity.account = data.userEntity.account;
			$scope.userEntity.pwd = data.userEntity.pwd;
			
			$scope.commerceEntity.biz_num = data.commerceEntity.biz_num;
			$scope.commerceEntity.biz_url = data.commerceEntity.biz_url;
			$scope.showImg("biz_img",$scope.commerceEntity.biz_url);
			$scope.commerceEntity.open_acount = data.commerceEntity.open_acount;
			$scope.commerceEntity.open_acount_url = data.commerceEntity.open_acount_url;
			$scope.showImg("open_img",$scope.commerceEntity.open_acount_url);
			$scope.commerceEntity.tax_num = data.commerceEntity.tax_num;
			$scope.commerceEntity.tax_url = data.commerceEntity.tax_url;
			$scope.showImg("tax_img",$scope.commerceEntity.tax_url);

			$scope.bankEntity.name = data.bankEntity.name;
			$scope.bankEntity.account = data.bankEntity.account;
			$scope.bankEntity.addr = data.bankEntity.addr;
			
			$scope.bankEntity.pay_account = data.bankEntity.pay_account;
			$scope.bankEntity.pay_account = data.bankEntity.pay_account;
			
		}).error(function(data){
			alert(data);   
		})
	}
	
	//显示服务区域名称
	$scope.showArea = function(areaIds){
		$http({
			   url: path+'/server/getregion/'+areaIds,
			   method: 'GET'
		}).success(function(data){ 
			var areaArr = data.result.split(",");
			
			var province = areaArr[0];
			var city = areaArr[1];
			var area = "";
			$.each(areaArr, function(index, OneObj){
				if(index>=2){
					area += (OneObj+",");
				}
			});
			area = area.substring(0,area.length-1);

			$scope.region_str = $sce.trustAsHtml("省-----"+province+";市-----"+city+";<br>区-----"+area);
			
		}).error(function(data){
			alert(data);   
		})
	}
	
	//反显图片
	$scope.showImg = function(imgId,url){
		$("#"+imgId).css({
    		"background": "url('"+url+"') no-repeat",
    	    "background-size":"100% 100%"
    	});
	}

	//提交
	$scope.formSubmit = function(){   
		var postObj = {
				id: backInfo.id,
				principal: $scope.principal,
				phone_num: $scope.phone_num
		};    
		
		$http({
			   url:	path+'/server/modifybackbrand',
			   method: 'POST',   
			   data: angular.toJson(postObj),  
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				//window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	
	$scope.getCurrentUserInfo();    //获取当前用户信息

	
});


