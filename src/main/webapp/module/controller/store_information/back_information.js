App.controller("storeBackInformationCtrl",function($scope, $rootScope, $location, $http, $compile,$stateParams,path) {
	
	
	$scope.userEntity = {};  			//用户信息
	
	var backInfo = null;   //后台信息
	
	//获取当前用户信息
	$scope.getCurrentUserInfo = function(){
		$http({
			   url: path+'/server/getCurrentUserInfo',
			   method: 'GET'
		}).success(function(data){  
			console.info(data);
			backInfo = data;

			$scope.shop_name = data.shop_name;
			
			$scope.principal = data.principal;
			$scope.phone_num = data.phone_num;
			
			$scope.imgurl = data.imgurl;
			$scope.showImg("logo_img",$scope.imgurl);
			
			$scope.region_code = data.region_code;
			$scope.getNameByCode($scope.region_code);
			
			$scope.address = data.address;
			
			$scope.userEntity.account = data.userEntity.account;
			$scope.userEntity.pwd = data.userEntity.pwd;
			
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
	
	//根据code获取name
	$scope.getNameByCode = function(code){
		$http({
			   url: path+'/server/getname/'+code,
			   method: 'GET' 
		}).success(function(data){
			var result = data.result;
			
			var areaStr = (result.province.name+"-"+result.city.name+"-"+result.area.name);
			$scope.region_str = areaStr;
		}).error(function(data){
			alert(data);   
		})
	}

	//提交
	$scope.formSubmit = function(){   
		var postObj = {
				id: backInfo.id,
				principal: $scope.principal,
				phone_num: $scope.phone_num
		};    
		
		$http({
			   url:	path+'/server/modifybackshop',
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


