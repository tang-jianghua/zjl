App.controller("addStoreCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);



	
	//初始化地图
	$scope.initMap = function(mapContainerId,searchLocationId,searchCoordinateId){
		mapObj = new AMap.Map(mapContainerId, {    //地图加载
	        resizeEnable: true,
	        zoom:11,
	        center: [116.397428, 39.90923]
	    });
	    var autoOptions = {    //输入提示
	        input: searchLocationId
	    };
	    var auto = new AMap.Autocomplete(autoOptions);
	    var placeSearch = new AMap.PlaceSearch({   //构造地点查询类
	        map: mapObj
	    });  
	    
	    AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
	    function select(e) {
	        placeSearch.setCity(e.poi.adcode);
	        placeSearch.search(e.poi.name);  //关键字查询查询
	    }
	    mapObj.plugin(["AMap.ToolBar"], function() {  //地图工具条
	    	mapObj.addControl(new AMap.ToolBar());
		});

	    mapObj.on('click', function(e) {	//维度，经度
			$("#"+searchCoordinateId).val(e.lnglat.getLat()+","+e.lnglat.getLng());
	    });
	}

	

	publicService.initProvince(1);	//省份
	publicService.initCity(1);		//市
	publicService.initArea(1);		//区
	$scope.initMap("map_container","search_location","search_coordinate");	//初始化地图
	
	publicService.uploadImage("logo","logo_img","logo_url");
	
	publicService.initTime2("open_time");
	publicService.initTime2("close_time");
	
	
	
	
	
	
	
});


