App.controller("userAccountCtrl",function($scope, $rootScope, $location, $http, $compile, path, userInfo) {
	
	$scope.user = {};  //用户信息
	$scope.form = {};  //密码
	var backInfo = null;   //后台信息
	

	$scope.user.role_name = userInfo.role_name;
	$scope.user.account = userInfo.account;

	
	//获取当前用户信息
	$scope.getCurrentUserInfo = function(){
		$http({
			   url: path+'/server/getCurrentUserInfo',
			   method: 'GET'
		}).success(function(data){  
			console.info("后台信息：",data);
			backInfo = data;

			$scope.imgurl = data.imgurl;
			if($scope.imgurl){
				$scope.showImg("logo_img",$scope.imgurl);
			}
			
			if(userInfo.user_type==1){  //开发者
				$scope.user.enterprise_name = "开发者后台";
	    	}else if(userInfo.user_type==2){  //企业
	    		$scope.user.enterprise_name = backInfo.enterprise_name;
	    	}else if(userInfo.user_type==3){  //合伙人
	    		$scope.user.enterprise_name = backInfo.enterprise_name;
	    	}else if(userInfo.user_type==4){  //品牌
	    		$scope.user.enterprise_name = backInfo.enterpriseEntity.buildEnterpriseEntity.name;
	    	}else if(userInfo.user_type==5){  //店铺
	    		$scope.user.enterprise_name = backInfo.shop_name;
	    	}
			
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
		if($scope.form.new_pw!=$scope.form.new_pw2){
			alert("两次输入密码不一致！");
			$scope.form.new_pw = "";
			$scope.form.new_pw2 = "";
			return;
		}
		$scope.form.id = userInfo.id;
		
		$http({
			   url: path+'/server/modifypassword',
			   method: 'POST',
			   data: angular.toJson($scope.form)
		}).success(function(data){  
			alert(data.message);
			if(data.code==0){  //成功
				$scope.form = {};
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//重置
	$scope.formReset = function(){
		$scope.form = {};
	}
	
	
	$scope.getCurrentUserInfo();    //获取当前用户信息
	
});