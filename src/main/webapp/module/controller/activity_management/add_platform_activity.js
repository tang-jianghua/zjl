App.controller("addPlatformActivityCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.form = {};     //表单
	
	
	
	//初始化活动类型
	$scope.initActivityType = function(){
		$("#activity_type").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
		    value: "1",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "满减"
			             },
			             {
			            	 id: 2,
			 				text: "秒杀"
			             },
			             {
			            	 id: 3,
			 				text: "折扣"
			             },
			             {
			            	 id: 4,
			 				text: "一口价"
			             }
            ]
		});
	}
	
	//优惠限购
	$scope.initIsLimit = function(){
		$("#isLimit").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: 1,
			 				text: "不限购"
			             },
			             {
			            	 id: 2,
			 				text: "限购"
			             }
            ],
            change: function(e) {
	    	    var value = this.value();
	    	    if(value==1){	//不限购
	    	    	$("#limit_count").hide();
	    	    }else if(value==2){	//限购
	    	    	$("#limit_count").show();
	    	    }
            }
		});
	}
	
	//参与品类
	$scope.initJoinClass = function(){
		$("#join_class").kendoMultiSelect({
		    dataTextField: "name",
		    dataValueField: "id",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/getbuildclassentities",
	                  dataType : "json"
	              }
	          }
	      }
		});
	}
	
	//表单提交
	$scope.formSubmit = function(){
		return;
		$http({
			   url: path+'/server/addunionpromotion',
			   method: 'POST',   
			   data: angular.toJson($scope.form),  
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	
	$scope.initActivityType();		//初始化活动类型
	$("#activity_type").data("kendoDropDownList").readonly(true);
	publicService.initDateTime("start_time");    //活动日期（始）
	publicService.initDateTime("end_time");   	 //活动日期（止）
	$scope.initIsLimit();			//优惠限购
	$("#limit_count").hide();
	publicService.initDateTime("endTime");   //报名截止日期
	
	$scope.initJoinClass();		//参与品类
	publicService.initProvince(2);		//初始化省
	publicService.initCity(2);			//初始化市
	publicService.initArea(2);			//初始化区
	
	publicService.uploadImage("introduce","introduce_img","introduce_url");		//活动宣传图



});