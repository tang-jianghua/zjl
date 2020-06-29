App.controller("enterpriseBackInformationCtrl",function($scope, $rootScope, $location, $http, $compile, path) {
	
	$scope.bankEntity = {};  		//银行信息
	$scope.commerceEntity = {};  	//工商注册信息
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

			$scope.enterprise_name = data.enterprise_name;
			$scope.addr = data.addr;
			
			$scope.principal = data.principal;
			$scope.phone_num = data.phone_num;
			
			$scope.class_name = data.class_name;
			$scope.userEntity.account = data.userEntity.account;
			$scope.userEntity.pwd = data.userEntity.pwd;
			$scope.brand_name = data.brand_name;
			
			$scope.imgurl = data.imgurl;
			$scope.showImg("logo_img",$scope.imgurl);
			
			$scope.bankEntity.name = data.bankEntity.name;
			$scope.bankEntity.account = data.bankEntity.account;
			$scope.bankEntity.addr = data.bankEntity.addr;
			
			$scope.bankEntity.pay_account = data.bankEntity.pay_account;
			$scope.bankEntity.pay_account = data.bankEntity.pay_account;
			
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
			   url:	path+'/server/modifybackenterprise',
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