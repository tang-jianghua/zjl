App.controller("partnerBackInformationCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, $sce) {

	$scope.bankInfo = {};  			//银行信息
	$scope.commerceInfo = {};  		//工商注册信息
	$scope.enterpriseEntity = {};  	//企业信息
	$scope.userEntity = {};  		//用户信息
	var backInfo = null;   //后台信息
	
	//获取当前用户信息
	$scope.getCurrentUserInfo = function(){
		$http({
			   url: path+'/server/getCurrentUserInfo',
			   method: 'GET'
		}).success(function(data){  
			//console.info(data);
			backInfo = data;
			
			$scope.principal = data.principal;
			$scope.phone_num = data.phone_num;
			
			$scope.enterprise_name = data.enterprise_name;
			$scope.region_id = data.region_id;
			$scope.showArea($scope.region_id);
			
			$scope.userEntity.account = data.userEntity.account;
			$scope.userEntity.pwd = data.userEntity.pwd;
			
			$scope.commerceInfo.biz_num = data.commerceInfo.biz_num;
			$scope.commerceInfo.biz_url = data.commerceInfo.biz_url;
			$scope.showImg("biz_img",$scope.commerceInfo.biz_url);
			$scope.commerceInfo.open_acount = data.commerceInfo.open_acount;
			$scope.commerceInfo.open_acount_url = data.commerceInfo.open_acount_url;
			$scope.showImg("open_img",$scope.commerceInfo.open_acount_url);
			$scope.commerceInfo.tax_num = data.commerceInfo.tax_num;
			$scope.commerceInfo.tax_url = data.commerceInfo.tax_url;
			$scope.showImg("tax_img",$scope.commerceInfo.tax_url);

			$scope.bankInfo.name = data.bankInfo.name;
			$scope.bankInfo.account = data.bankInfo.account;
			$scope.bankInfo.addr = data.bankInfo.addr;
			
			$scope.bankInfo.pay_account = data.bankInfo.pay_account;
			$scope.bankInfo.pay_account = data.bankInfo.pay_account;
			
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
			   url:	path+'/server/modifybackpartner',
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


