App.controller("classRecommendForPartnerCtrl",function($scope, $rootScope, $location, $http, $compile,path, publicService) {
	
	
	
	//获取品类和产品
	$scope.getClassAndProduct = function(){
		$http({
			   url: path+'/server/queryIntroduceProduct',
			   method: 'GET'
		}).success(function(data){
			$scope.classArr = data;
		}).error(function(data){
			alert("系统异常！");   
		})
	}
	
	//清除品类
	$scope.removeClass = function(class_id){
		if(confirm("确认清空此品类下的所有产品？")){
			$http({
				   url: path+'/server/deleteIntroduceClass/'+class_id,
				   method: 'GET'
			}).success(function(data){
				alert(data.message);
				
				if(data.code==0){
					$scope.getClassAndProduct();	//获取品类和产品
				}else if(data.code==1){
					
				}
			}).error(function(data){
				alert("系统异常！");   
			})
		}
	}
	
	//清除产品
	$scope.removeProduct = function(introduce_id,introduce_product_id){
		if(confirm("确认清除此款产品？")){
			$http({
				   url: path+'/server/deleteIntroduceProduce/'+introduce_id+'/'+introduce_product_id,
				   method: 'POST'
			}).success(function(data){
				alert(data.message);
				
				if(data.code==0){
					$scope.getClassAndProduct();	//获取品类和产品
				}else if(data.code==1){
					
				}
			}).error(function(data){
				alert("系统异常！");   
			})
		}
	}
	
	//位置移动
	$scope.locationMove = function(introduce_id,type){
		var another = '';
		if(type==1){	//下移
			another = $("#class_"+introduce_id).next().attr("classId");
		}else if(type==-1){	//上移
			another = $("#class_"+introduce_id).prev().attr("classId");
		}
		
		$http({
			   url: path+'/server/modifyIntroduceClass/'+introduce_id+'/'+another,
			   method: 'GET'
		}).success(function(data){
			alert(data.message);
			
			if(data.code==0){
				$scope.getClassAndProduct();	//获取品类和产品
			}else if(data.code==1){
				
			}
		}).error(function(data){
			alert("系统异常！");   
		})
	}
	
	
	$scope.getClassAndProduct();	//获取品类和产品
	
});