App.controller("addPartnerInformationCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);

	$scope.form = {};                  //表单
	$scope.form.userEntity = {};		//用户信息
	$scope.form.bankInfo = {};			//银行信息
	$scope.form.commerceInfo = {};		//商业信息
	
	var fatherInfo = null;  //父级信息
	var partnerInfo = {};   //合伙人信息
	var allAreaStr = '';  //全城
	
	var limitParam1 = {imageSize:500};		//证件图

	//初始化省
	$scope.initProvince = function(){
		$("#province").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "code",
		    placeholder: "省",
		    filter: "contains",
		    dataSource: {
		      serverFiltering: true,
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
			var areaArr = data.result;
			allAreaStr = '';
			$.each(areaArr, function(index, OneObj){
				allAreaStr += (OneObj.code+",");
			});

			var allArea = {code:"all",name:"全城"};  //全城
			areaArr.unshift(allArea);

			$scope.initArea(areaArr,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	}
	
	//初始化区
	$scope.initArea = function(areaData,defaultVal){
		if(areaData){
			var plugObj = $("#area").data("kendoMultiSelect");
			plugObj.setDataSource(areaData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#area").kendoMultiSelect({
				  dataTextField: "name",
				  dataValueField: "code",
				  placeholder: "请选择区域",
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
	
	//检测支付宝
	$scope.checkAlipayNumber = function(){
		var value = $scope.form.bankInfo.pay_account;
		if(value){
			if(publicService.checkPhoneNumber(value) || publicService.checkMailbox(value)){
				
			}else{
				$scope.form.bankInfo.pay_account = "";
				alert("请输入有效的支付宝账号！");
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
			   url:	path+'/server/resetpassword/'+partnerInfo.sys_user_id,
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
			   url: path+'/server/initpartner',
			   method: 'GET' 
		}).success(function(data){  
			fatherInfo = data;	
			
			$scope.enterpriseName = fatherInfo.enterprise_name;	//企业属性
			
		}).error(function(data){
			alert(data);   
		})
	}
	
	//提交
	$scope.formSubmit = function(){ 
		$scope.form.region_id = $("#area").data("kendoMultiSelect").value().toString();  		//区
		if($scope.form.region_id.indexOf("all")>-1){   //全城
			$scope.form.region_id = allAreaStr;
			$scope.form.isglobal = 1;	//是否全城（是）
		}else{
			$scope.form.isglobal = 2;
		}
		
    	$scope.form.commerceInfo.biz_url = $("#upload1_url").val();
    	$scope.form.commerceInfo.open_acount_url = $("#upload2_url").val();
    	$scope.form.commerceInfo.tax_url = $("#upload3_url").val();

    	//参数验证
		if(!$scope.form.principal){
			alert("请填写【姓名】！");
			return;
		}
		if(!$scope.form.phone_num){
			alert("请填写【联系方式】！");
			return;
		}
		if(!$scope.form.region_id){
			alert("请填写【服务区域】！");
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
		
		if(!$scope.form.commerceInfo.biz_url){
			alert("请上传【营业执照】！");
			return;
		}
		if(!$scope.form.commerceInfo.biz_num){
			alert("请填写【营业执照注册号】！");
			return;
		}
		if(!$scope.form.commerceInfo.open_acount_url){
			alert("请上传【开户许可证】！");
			return;
		}
		if(!$scope.form.commerceInfo.open_acount){
			alert("请填写【开户许可证号】！");
			return;
		}
		if(!$scope.form.commerceInfo.tax_url){
			alert("请上传【税务登记证】！");
			return;
		}
		if(!$scope.form.commerceInfo.tax_num){
			alert("请填写【税务登记证号】！");
			return;
		}
	
		if(!$scope.form.bankInfo.name){
			alert("请填写【户名】！");
			return;
		}
		if(!$scope.form.bankInfo.account){
			alert("请填写【对公账号】！");
			return;
		}
		if(!$scope.form.bankInfo.addr){
			alert("请填写【开户行】！");
			return;
		}
		if(!$scope.form.bankInfo.pay_account && !$scope.form.bankInfo.webkit_account){
			alert("【支付宝】和【微信】至少填写一项！");
			return;
		}
		
		var postUrl = '';
		if(params.method=="add"){
			postUrl = path+'/server/createpartner';
			$scope.form.check_state = 1;  		//审核状态
		}else if(params.method=="edit"){
			postUrl = path+'/server/modifypartner';
			$scope.form.id = partnerInfo.id;
			$scope.form.check_state = partnerInfo.check_state;
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
			alert(data);   
		})
	}
	
	//获取合伙人信息
	$scope.getPartnerInformation = function(id){
		$http({
			   url: path+'/server/getpartnerbyid/'+id,
			   method: 'GET',   
			   data: angular.toJson($scope.form)
		}).success(function(data){
			partnerInfo = data.result;

			$scope.form.principal = partnerInfo.principal;
			$scope.form.phone_num = partnerInfo.phone_num;
			
			$scope.getNameByCode(partnerInfo.region_id.split(",")[2]);  //服务区域
			
			$scope.form.bankInfo.name = partnerInfo.bankInfo.name;
			$scope.form.bankInfo.account = partnerInfo.bankInfo.account;
			$scope.form.bankInfo.addr = partnerInfo.bankInfo.addr;
			
			$scope.form.userEntity.account = partnerInfo.userEntity.account;
			$scope.form.userEntity.pwd = "******";
			$("#pwd").attr("readonly","readonly");

			//工商注册信息
			if(params.method=="look"){
				var commerceInfoArr = new Array();
				commerceInfoArr.push({
					id: "commerceInfo_1",
					url: partnerInfo.commerceInfo.biz_url
				});
				commerceInfoArr.push({
					id: "commerceInfo_2",
					url: partnerInfo.commerceInfo.open_acount_url
				});
				commerceInfoArr.push({
					id: "commerceInfo_3",
					url: partnerInfo.commerceInfo.tax_url
				});

				$.each(commerceInfoArr, function(index, OneObj){
					if(OneObj.url){
						var html = ('<div class="col-sm-12">'
										+'<a class="fancybox" href="'+OneObj.url+'" data-fancybox-group="gallery_commerceInfo" title="">'
											+'<img src="'+OneObj.url+'" class="imgContainer" alt=""/>'
										+'</a>'
									+'</div>');
						$("#"+OneObj.id).html(html);
					}
				});
		
				$('.fancybox').fancybox();
			}else{
				publicService.showUploadImage("upload1_img","upload1_url",partnerInfo.commerceInfo.biz_url);
				publicService.showUploadImage("upload2_img","upload2_url",partnerInfo.commerceInfo.open_acount_url);
				publicService.showUploadImage("upload3_img","upload3_url",partnerInfo.commerceInfo.tax_url);
			}
			
			
			$scope.form.commerceInfo.biz_num = partnerInfo.commerceInfo.biz_num;
			$scope.form.commerceInfo.open_acount = partnerInfo.commerceInfo.open_acount;
			$scope.form.commerceInfo.tax_num = partnerInfo.commerceInfo.tax_num;
			//银行信息
			$scope.form.bankInfo.name = partnerInfo.bankInfo.name;
			$scope.form.bankInfo.account = partnerInfo.bankInfo.account;
			$scope.form.bankInfo.addr = partnerInfo.bankInfo.addr;
			//第三方账户
			$scope.form.bankInfo.pay_account = partnerInfo.bankInfo.pay_account;
			$scope.form.bankInfo.webkit_account = partnerInfo.bankInfo.webkit_account;

			if(partnerInfo.check_state==1){
				$scope.form.check_stateStr = "审核中";
			}else if(partnerInfo.check_state==2){
				$scope.form.check_stateStr = "已通过";
			}
			
			
			if(params.method=="edit" || params.method=="look"){
				$scope.enterpriseName = partnerInfo.enterpriseEntity.enterprise_name;	//企业属性
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
			$scope.getAreaData(result.city.code,partnerInfo.region_id.split(","));

		}).error(function(data){
			alert(data);   
		})
	}
	
	//检查合伙人的开通城市
	$scope.checkCityOfPartner = function(id){
		$http({
			   url: path+'/server/checkopenctiy/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				
			}else if(data.code==1){  //失败
				$("#province").data("kendoComboBox").readonly(true);
				$("#city").data("kendoComboBox").readonly(true);
				$("#area").data("kendoMultiSelect").readonly(true);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	
	
	$scope.initProvince();			//初始化省
	$scope.initCity();				//初始化市
	$scope.initArea();				//初始化区
	$scope.form.check_stateStr = "审核中";
	
	publicService.uploadImage("upload1","upload1_img","upload1_url",limitParam1);				//上传图片
	publicService.uploadImage("upload2","upload2_img","upload2_url",limitParam1);				//上传图片
	publicService.uploadImage("upload3","upload3_img","upload3_url",limitParam1);				//上传图片
	
	if(params.method=="add"){
		$scope.getFatherInfo();		//获取父级信息
		
		$("#checkState").hide();
	}else if(params.method=="edit"){
		$scope.getPartnerInformation(params.peopleId);
		$scope.checkCityOfPartner(params.peopleId);
	}else if(params.method=="look"){
		$scope.getPartnerInformation(params.peopleId);
		
		$("#formSubmitButton").remove();
		$("input").attr("readonly","readonly");
		$("#province").data("kendoComboBox").readonly(true);
		$("#city").data("kendoComboBox").readonly(true);
		$("#area").data("kendoMultiSelect").readonly(true);
	}
	
	/*权限分配*/
	if(userInfo.user_type==2 && params.method=="edit"){  //企业
		
	}else{  
		$("#resetPasswordBtn").remove();
	}
	
});


