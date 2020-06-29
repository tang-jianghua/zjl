App.controller("entbrandProductCtrl",function($scope, $rootScope, $location, $http, $compile,path) {
	
	$scope.initpinlei=function(){
		$http({
			   url: path+'/server/getCurrentUserInfo',
			   method: 'GET'
		}).success(function(data){  
			console.info(data.class_id);
			var params = {method:"check"};
			var num=parseInt(data.class_id)
			switch (num) {
			case 1://壁纸
				$location.path("/brandWallpaperProduct/"+angular.toJson(params));
				break;
			case 2://地板
				$location.path("/brandFloorProduct/"+angular.toJson(params));
				break;
			case 3://瓷砖
				$location.path("/brandTileProduct/"+angular.toJson(params));
				break;
			case 4://门
				$location.path("/brandDoorProduct/"+angular.toJson(params));
				break;
			case 5://涂料
				$location.path("/brandCoatingProduct/"+angular.toJson(params));
				break;
			case 6://卫浴及配件
				$location.path("/brandSwitchProduct/"+angular.toJson(params));
				break;
			case 7://橱柜及配件
				$location.path("/brandSwitchProduct/"+angular.toJson(params));
				break;
			case 8://开关
				$location.path("/brandSwitchProduct/"+angular.toJson(params));
				break;
			case 9://衣柜
				$location.path("/brandSwitchProduct/"+angular.toJson(params));
				break;
			}
		}).error(function(data){
			alert(data);   
		})
	}
	$scope.initpinlei();
});