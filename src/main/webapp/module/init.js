/*
 * AngularJS页面应用程序
 */
//var App = angular.module("app",['ngRoute','ngResource',"kendo.directives",'pascalprecht.translate','ngSanitize']);
var App = angular.module("app",['ui.router','oc.lazyLoad','ngResource','pascalprecht.translate','ngSanitize',"kendo.directives"]);

//国际化
App.config(['$translateProvider', function ($translateProvider) {	
    $translateProvider.useStaticFilesLoader({
        prefix: '../../i18n/',
        suffix: '.json'
    });
    
    $translateProvider.preferredLanguage('zh_CN');
    $translateProvider.usePostCompiling(true);
}]);

//模块化加载
App.config(["$provide","$compileProvider","$controllerProvider","$filterProvider",
    function($provide,$compileProvider,$controllerProvider,$filterProvider){	
        App.controller = $controllerProvider.register;
        App.directive = $compileProvider.directive;
        App.filter = $filterProvider.register;
        App.factory = $provide.factory;
        App.service  =$provide.service;
        App.constant = $provide.constant;
    }
]);

//angular-route(清除缓存)
/*App.run(function($rootScope, $templateCache) {  
    $rootScope.$on('$routeChangeStart', function(event, next, current) {  
        if (typeof(current) !== 'undefined'){  
            $templateCache.remove(current.templateUrl);  
        }  
    });  
});*/ 

//angular-ui-route(清除缓存)
App.run(['$rootScope', '$window', '$location', '$log','$templateCache',function ($rootScope, $window, $location, $log, $templateCache) { 
	  var stateChangeSuccess = $rootScope.$on('$stateChangeSuccess', stateChangeSuccess);  
	  function stateChangeSuccess($rootScope) {  
		  $templateCache.removeAll();    
	  }	
}]);

/***********************************************项目全局信息(begin)**********************************************/ 
var path = null;		//项目地址信息
var userInfo = null;	//登录用户信息

//获取项目根路径
(function getRootPath(){
	var curPath=window.document.location.href;
	var pathName=window.document.location.pathname;
	
	var pos=curPath.indexOf(pathName);
	var localhostPath=curPath.substring(0,pos);
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	path = localhostPath + projectName;
	App.constant('path', path);  
}());

//获取当前用户信息
(function getCurrentUserInfo(){
	$.ajax({
		url : path+"/server/getCurrentUser",
		type : "GET",
		success : function(data) {  
			userInfo = data;
			App.constant('userInfo', userInfo); 
		}
	});  
}());
/***********************************************项目全局信息(end)**********************************************/ 

//头部
App.controller("headerCtrl", function($scope,$rootScope,$location,$http,$compile,path){
	$scope.changeRightPage = function(caidModel) {
    	$location.path(caidModel);
}
});

//左侧菜单栏
App.controller("leftCtrl", function($scope,$rootScope,$location,$http,$compile,path){
	var menuData = null;
	
	//初始化左边菜单栏
	$scope.initLeftMenu = function(containerId) {
		$http({
			   url: path+'/server/getPermissionByUserId',
			   method: 'GET' 
		}).success(function(data){
			menuData = data;
	 		//console.info(menuData);
	 		$scope.showLeftMenu(menuData,containerId);
		}).error(function(data){
			alert(data);   
		})
	}

	//显示左边菜单栏
	$scope.showLeftMenu = function(menuData,containerId) {
		var html = '';
		var parentCount = 0;  //一级菜单数量
		
		html += '<ul id="main-nav" class="open-active">';
		$.each(menuData, function(index, OneObj){
			var sonCount = 0;     //二级菜单数量
			var haveChildren = false;    //是否有子菜单
			var id = OneObj.id;
			var parent_id = OneObj.parent_id;
			
			if(!parent_id){
				parentCount ++;
				var children_html = '';
				$.each(menuData, function(index1, OneObj1){
					if(OneObj1.parent_id==id){  //子菜单
						sonCount ++;
						if(parentCount==1 && sonCount==1){
							var routeUrl = $location.url();
							if(routeUrl=="/"){
								$location.path(OneObj1.url);
							}else{
								
							}
						}
						
						haveChildren = true;
						children_html += ('<li>'
											 +'<a href="javascript:void(0);" ng-click="changeRightPage(\''+OneObj1.url+'\')">'
											 	+'<i class="'+OneObj1.icon_url+'"></i>'
											 	+OneObj1.name.split("_")[0]
											 +'</a>'
										 +'</li>');
					}
				});
				if(haveChildren==true){
					children_html = ('<ul class="sub-nav">'
										+children_html
									+'</ul>');
				}
				
				html += ('<li class="dropdown">'
							+'<a href="javascript:;">'
								+'<i class="'+OneObj.icon_url+'"></i>'
									+OneObj.name
								+'<span class="caret"></span>'
							+'</a>'
							+children_html
						+'</li>');
			}
		});
		html += '</ul>';
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#"+containerId).html(html);
		Nav.init ();  //收缩，展开事件
	}

	 $scope.changeRightPage = function(caidModel) {
	    	$location.path(caidModel);
	}
	 
	 
	 $scope.initLeftMenu("sidebar");		//初始化左边菜单栏
});


