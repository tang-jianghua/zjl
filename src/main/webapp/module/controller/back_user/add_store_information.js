App.controller("addStoreInformationCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);


	$scope.form = {};                  //表单
	$scope.form.bankEntity = {};  		 //银行信息
	$scope.form.userEntity = {};   		 //用户信息
	$scope.form.commerceEntity = {};   	 //工商信息

	var fatherInfo = null;  //父级信息
	var storeInfo = null;   //店铺信息
	var mapObj = null;  //地图对象



	//上传图片
	$scope.Upload = function(containerId,imgId,imgUrlId){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/updoad",
	            removeUrl: path+"/remove",
	           // autoUpload: false
	        },
	     	multiple: false,      //多选
	      	showFileList: false,  //显示文件列表
	        localization: {
	              select: "请上传图片",
	              cancel: "取消",
	              remove: "移除",  //autoUpload: false
	              retry: "刷新",
	              headerStatusUploading: "上传...",
	              headerStatusUploaded: "上传成功",
	              statusUploading: "loading",
	              statusUploaded: "success",
	              statusFailed: "failed",
	              uploadSelectedFiles: "上传选中的文件",   //autoUpload: false
	        },
	        success: function(e){
	        	//console.log('成功：',e.response);
	        	var url = e.response.result;   //图片路径
	        	$scope.showUploadImg(imgId,imgUrlId,url);
		    }
	      
	    });
	}
	
	//反显图片并赋值
	$scope.showUploadImg = function(imgId,imgUrlId,url){
		$("#"+imgId).css({
			"background": "url('"+url+"') no-repeat",
    	    "background-size":"100% 100%",
    	    "background-color":"white"
    	});
		$('#'+imgUrlId).val(url);
	}
	
	//初始化省
	$scope.initProvince = function(){
		$("#province").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "code",
		    placeholder: "省",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/province",
	                  dataType : "json"
	              }
	          },
	          schema : {
					data : function(d) {
						return d.result;
					}
				},
	      },
	      change: function(e) {
	    	    var value = this.value();
	    	    $scope.getCityData(value);
	      }
		});
	}
	
	//获取市数据
	$scope.getCityData = function(provinceCode,defaultVal){
		$http({
			   url: path+"/server/city/"+provinceCode,
			   method: 'GET'
		}).success(function(data){
			$scope.initCity(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化市
	$scope.initCity = function(cityData,defaultVal){
		if(cityData){
			var plugObj = $("#city").data("kendoComboBox");
			plugObj.value("");
			plugObj.setDataSource(cityData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#city").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "code",
			    placeholder: "市",
			    filter: "contains",
			    dataSource: [],
			    change: function(e) {
		    	    var value = this.value();
		    	    $scope.getAreaData(value);
		      }
			});
		}
	}
	
	//获取区数据
	$scope.getAreaData = function(cityCode,defaultVal){
		$http({
			   url: path+"/server/county/"+cityCode,
			   method: 'GET'
		}).success(function(data){
			$scope.initArea(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化区
	$scope.initArea = function(areaData,defaultVal){
		if(areaData){
			var plugObj = $("#area").data("kendoComboBox");
			plugObj.setDataSource(areaData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#area").kendoComboBox({
				  dataTextField: "name",
				  dataValueField: "code",
				  placeholder: "区",
				  filter: "contains",
				  dataSource: []
			});
		}
	}
	
	//检测账号
	$scope.checkAccount = function(){ 
		if($scope.form.userEntity.account && params.method!="look"){  //有值
			var postData = {
					account: $scope.form.userEntity.account
			};
			
			$http({
				   url:	path+'/server/checkuser',
				   method: 'POST',   
				   data: angular.toJson(postData),  
			}).success(function(data){
				if(data.code==0){  //成功
					
				}else if(data.code==1){  //失败
					alert("用户已存在，请重新录入！");
					$scope.form.userEntity.account = "";
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//检测联系方式
	$scope.checkPhoneNumber = function(){
		var value = $scope.form.phone_num;
		if(value){
			if(publicService.checkPhoneNumber(value) || publicService.checkTellNumber(value)){
				
			}else{
				$scope.form.phone_num = "";
				alert("请输入有效的联系方式！");
			}
		}
	}
	
	//检测不包含中文
	$scope.checkNotContainChinese = function(type){
		var value = '';
		if(type==1){
			value = $scope.form.userEntity.account;
		}else if(type==2){
			value = $scope.form.userEntity.pwd;
		}
		
		if(value){
			if(publicService.checkIsContainChinese(value)){
				alert("不应该包含中文！");
				if(type==1){
					$scope.form.userEntity.account = '';
				}else if(type==2){
					$scope.form.userEntity.pwd = '';
				}
			}else{
				
			}
		}
	}
	
	//重置密码
	$scope.resetPassword = function(){ 
		$http({
			   url:	path+'/server/resetpassword/'+storeInfo.sys_user_id,
			   method: 'GET'   
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				$scope.form.userEntity.pwd = "888888";
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//获取父级信息  
	$scope.getFatherInfo = function(){
		$http({
			   url: path+'/server/initshop',
			   method: 'GET' 
		}).success(function(data){  
			fatherInfo = data;	
			
			$scope.brandName = fatherInfo.buildEnterpriseEntity.name;	//店铺属性(品牌名称)
			
		}).error(function(data){
			alert(data);   
		})
	}
	
	//提交
	$scope.formSubmit = function(){ 
		$scope.form.imgurl = $("#logo_url").val();    //LOGO 
		$scope.form.region_code = $("#area").data("kendoComboBox").value();  //服务区域
		$scope.form.lbs = $("#search_coordinate").val();
		
		//参数验证
		if(!$scope.form.shop_name){
			alert("请填写【店面名称】！");
			return;
		}
		if(!$scope.form.principal){
			alert("请填写【负责人】！");
			return;
		}
		if(!$scope.form.phone_num){
			alert("请填写【联系方式】！");
			return;
		}
		if(!$scope.form.imgurl){
			alert("请上传【LOGO】！");
			return;
		}
		if(!$scope.form.region_code){
			alert("请填写【店铺区域】！");
			return;
		}
		if(!$scope.form.address){
			alert("请填写【详细地址】！");
			return;
		}
		if(!$scope.form.lbs){
			alert("请选择【地址坐标】！");
			return;
		}
		if(!$scope.form.userEntity.account){
			alert("请填写【创建账号】！");
			return;
		}
		if(params.method=="add" && !$scope.form.userEntity.pwd){
			alert("请填写【创建密码】！");
			return;
		}
		
		
		var postUrl = '';
		if(params.method=="add"){
			postUrl = path+'/server/createshop';
		}else if(params.method=="edit"){
			postUrl = path+'/server/modifyshop';
			$scope.form.id = storeInfo.id;
			$scope.form.userEntity.pwd = null;
		}
		
		$http({
			   url: postUrl,
			   method: 'POST',   
			   data: angular.toJson($scope.form),  
		}).success(function(data){
			alert(data.message);
			if(data.code==0){  //成功
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert("保存失败！");   
		})
	}
	
	//获取店铺信息
	$scope.getStoreInformation = function(id){
		$http({
			   url: path+'/server/getshopbyid/'+id,
			   method: 'GET',   
			   data: angular.toJson($scope.form)
		}).success(function(data){
			storeInfo = data.result;
			
			$scope.form.shop_name = storeInfo.shop_name;
			$scope.form.principal = storeInfo.principal;
			$scope.form.phone_num = storeInfo.phone_num;
			$scope.showUploadImg("logo_img","logo_url",storeInfo.imgurl);
			$scope.getNameByCode(storeInfo.region_code);  //店铺区域
			$scope.form.address = storeInfo.address;
			//设置地图中心位置
			var coordinateArr = storeInfo.lbs.split(",");
			var coordinateArr_1 = [Number(coordinateArr[1]),Number(coordinateArr[0])];
			mapObj.setCenter(coordinateArr_1);
			// 在新中心点添加 marker 
	        var marker = new AMap.Marker({
	            map: mapObj,
	            position: coordinateArr_1
	        });

			$("#search_coordinate").val(storeInfo.lbs);
			//用户信息
			$scope.form.userEntity.account = storeInfo.userEntity.account;
			$scope.form.userEntity.pwd = "******";
			$("#pwd").attr("readonly","readonly");

			
			if(params.method=="edit" || params.method=="look"){
				$scope.brandName = storeInfo.brandEntity.buildEnterpriseEntity.name;	//店铺属性(品牌名称)
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//根据code获取name
	$scope.getNameByCode = function(code){
		$http({
			   url: path+'/server/getname/'+code,
			   method: 'GET' 
		}).success(function(data){
			var result = data.result;
			$("#province").data("kendoComboBox").value(result.province.code);
			$scope.getCityData(result.province.code,result.city.code);
			$scope.getAreaData(result.city.code,storeInfo.region_code);

		}).error(function(data){
			alert(data);   
		})
	}
	
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

	

	$scope.Upload("logo","logo_img","logo_url");		//LOGO
	$scope.initProvince();			//初始化省
	$scope.initCity();				//初始化市
	$scope.initArea();				//初始化区
	
	$scope.initMap("map_container","search_location","search_coordinate");
	
	
	if(params.method=="add"){
		$scope.getFatherInfo();		//获取父级信息  
	}else if(params.method=="edit"){
		$scope.getStoreInformation(params.storeId);
	}else if(params.method=="look"){
		$scope.getStoreInformation(params.storeId);
		
		$("#formSubmitButton").remove();
		$("input").attr("readonly","readonly");
		$("#province").data("kendoComboBox").readonly(true);
		$("#city").data("kendoComboBox").readonly(true);
		$("#area").data("kendoComboBox").readonly(true);
	}
	
	/*权限分配*/
	if(userInfo.user_type==4 && params.method=="edit"){  //品牌
		
	}else{  
		$("#resetPasswordBtn").remove();
	}
	
});


