App.controller("addEnterpriseInformationCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, $timeout, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	var enterpriseData = null;	//企业名称数据

	$scope.form = {};                  //表单
	$scope.form.bankEntity = {};  		 //银行信息
	$scope.form.userEntity = {};   		 //用户信息
	$scope.form.commerceEntity = {};   	 //工商信息

	$scope.enterpriseInfo = {};   //企业信息
	
	var limitParam1 = {imageSize:500};		//证件图


	//获得产品品类
	$scope.getProductClass = function(containerId){ 
		$http({
			   url:	path+"/server/getbuildclassentities",
			   method: 'GET' 
		}).success(function(data){
			var productClass_html = '';
			$(data).each(function (index,obj) {
				productClass_html += '<div    class="col-sm-3 businessr">'
						+'<input type="radio" name="product_class" value="'+obj.id+'" ng-click="chooseProductClass(\''+obj.id+'\')" class="col-sm-3"/>'+obj.name
					+'</div>';
			});
			
			productClass_html = $compile(productClass_html)($scope);   //angularJs代码需要动态编译
			$("#"+containerId).html(productClass_html);
		}).error(function(data){
			alert(data);   
		})
	}
	
	//选择产品品类
	$scope.chooseProductClass = function(classId,defaultVal){
		var showDataArr = new Array();   //显示的数据集
		if(enterpriseData && enterpriseData.length>0){
			$.each(enterpriseData, function(index, OneObj){
				if(OneObj.class_id==classId){
					showDataArr.push(OneObj);
				}
			});
		}else{
			$timeout(function(){
				$scope.chooseProductClass(classId,defaultVal);
		    }, 100);
		}
		
		$scope.initEnterpriseName(showDataArr,defaultVal);
	}
	
	//获取企业名称数据
	$scope.getEnterpriseData = function(){
		$http({
			   url: path+'/server/buildenterpriselist',
			   method: 'GET' 
		}).success(function(data){  
			enterpriseData = data;	
		}).error(function(data){
			alert(data);   
		})
	}
	
	//初始化品牌名称
	$scope.initEnterpriseName = function(showEnterpriseData,defaultVal){
		if(showEnterpriseData){
			var plugObj = $("#brandName").data("kendoComboBox");
			plugObj.value("");
			plugObj.setDataSource(showEnterpriseData);
			if(defaultVal){
				plugObj.value(defaultVal);
			}
		}else{
			$("#brandName").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "id",
			    placeholder: "请选择品牌名称",
			    filter: "contains",
			    dataSource: []
			});
		}
	}

	//检测账号
	$scope.checkAccount = function(){ 
		if($scope.form.userEntity.account){  //有值
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
		var value = $scope.form.bankEntity.pay_account;
		if(value){
			if(publicService.checkPhoneNumber(value) || publicService.checkMailbox(value)){
				
			}else{
				$scope.form.bankEntity.pay_account = "";
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
			   url:	path+'/server/resetpassword/'+$scope.enterpriseInfo.sys_user_id,
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

	//提交
	$scope.formSubmit = function(){ 
		$scope.form.class_id = $('input[name="product_class"]:checked').val();              //产品品类
		$scope.form.build_enterpise = $("#brandName").data("kendoComboBox").value();	//品牌名称Id
		
		$scope.form.imgurl = $("#logo_url").val();
		
		$scope.form.commerceEntity.biz_url = $("#upload1_url").val();
    	$scope.form.commerceEntity.open_acount_url = $("#upload2_url").val();
    	$scope.form.commerceEntity.tax_url = $("#upload3_url").val();

    	//参数验证
		if(!$scope.form.class_id){
			alert("请选择【产品品类】！");
			return;
		}
		if(!$scope.form.build_enterpise){
			alert("请选择【经营品牌】！");
			return;
		}
		if(!$scope.form.addr){
			alert("请填写【联系地址】！");
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
		if(!$scope.form.userEntity.account){
			alert("请填写【创建账号】！");
			return;
		}
		if(params.method=="add" && !$scope.form.userEntity.pwd){
			alert("请填写【创建密码】！");
			return;
		}
		if(!$scope.form.enterprise_name){
			alert("请填写【企业名称】！");
			return;
		}
		if(!$scope.form.imgurl){
			alert("请上传【LOGO】！");
			return;
		}
		if(!$scope.form.commerceEntity.biz_url){
			alert("请上传【营业执照】！");
			return;
		}
		if(!$scope.form.commerceEntity.biz_num){
			alert("请填写【营业执照注册号】！");
			return;
		}
		if(!$scope.form.commerceEntity.open_acount_url){
			alert("请上传【开户许可证】！");
			return;
		}
		if(!$scope.form.commerceEntity.open_acount){
			alert("请填写【开户许可证号】！");
			return;
		}
		if(!$scope.form.commerceEntity.tax_url){
			alert("请上传【税务登记证】！");
			return;
		}
		if(!$scope.form.commerceEntity.tax_num){
			alert("请填写【税务登记证号】！");
			return;
		}
	
		if(!$scope.form.bankEntity.name){
			alert("请填写【户名】！");
			return;
		}
		if(!$scope.form.bankEntity.account){
			alert("请填写【对公账号】！");
			return;
		}
		if(!$scope.form.bankEntity.addr){
			alert("请填写【开户行】！");
			return;
		}
		if(!$scope.form.bankEntity.pay_account && !$scope.form.bankEntity.webkit_account){
			alert("【支付宝】和【微信】至少填写一项！");
			return;
		}
		
    	
		var postUrl = '';
		if(params.method=="add"){
			postUrl = path+'/server/createenterprise';
		}else if(params.method=="edit"){
			postUrl = path+'/server/modifyenterprise';
			$scope.form.id = $scope.enterpriseInfo.id;
			$scope.form.userEntity.pwd = null;
		}
		
		$http({
			   url:	postUrl,
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
	
	//获取企业信息
	$scope.getEnterpriseInformation = function(id){
		$http({
			   url: path+'/server/getenterprisebyid/'+id,
			   method: 'GET',   
			   data: angular.toJson($scope.form)
		}).success(function(data){
			$scope.enterpriseInfo = data.result;

			$('input[name="product_class"][value="'+$scope.enterpriseInfo.class_id+'"]').attr("checked",true);  //产品品类
			$scope.chooseProductClass($scope.enterpriseInfo.class_id,$scope.enterpriseInfo.build_enterpise);	//品牌名称
			$scope.form.addr = $scope.enterpriseInfo.addr;
			
			$scope.form.principal = $scope.enterpriseInfo.principal;
			$scope.form.phone_num = $scope.enterpriseInfo.phone_num;
			
			$scope.form.userEntity.account = $scope.enterpriseInfo.userEntity.account;
			$scope.form.userEntity.pwd = "******";
			$("#pwd").attr("disabled",true);
			
			$scope.form.enterprise_name = $scope.enterpriseInfo.enterprise_name;
			publicService.showUploadImage("logo_img","logo_url",$scope.enterpriseInfo.imgurl);
			
			//工商注册信息
			publicService.showUploadImage("upload1_img","upload1_url",$scope.enterpriseInfo.commerceEntity.biz_url);
			publicService.showUploadImage("upload2_img","upload2_url",$scope.enterpriseInfo.commerceEntity.open_acount_url);
			publicService.showUploadImage("upload3_img","upload3_url",$scope.enterpriseInfo.commerceEntity.tax_url);
			$scope.form.commerceEntity.biz_num = $scope.enterpriseInfo.commerceEntity.biz_num;
			$scope.form.commerceEntity.open_acount = $scope.enterpriseInfo.commerceEntity.open_acount;
			$scope.form.commerceEntity.tax_num = $scope.enterpriseInfo.commerceEntity.tax_num;
			//银行信息
			$scope.form.bankEntity.name = $scope.enterpriseInfo.bankEntity.name;
			$scope.form.bankEntity.account = $scope.enterpriseInfo.bankEntity.account;
			$scope.form.bankEntity.addr = $scope.enterpriseInfo.bankEntity.addr;
			//第三方账户
			$scope.form.bankEntity.pay_account = $scope.enterpriseInfo.bankEntity.pay_account;
			$scope.form.bankEntity.webkit_account = $scope.enterpriseInfo.bankEntity.webkit_account;
			
		}).error(function(data){
			alert(data);   
		})
	}
	

	$scope.getProductClass("productClassContainer");		//获得产品品类
	$scope.getEnterpriseData();			//获取企业名称数据
	$scope.initEnterpriseName();		//初始化企业名称					

	publicService.uploadImage("logo","logo_img","logo_url");		//LOGO
	publicService.uploadImage("upload1","upload1_img","upload1_url",limitParam1);				//上传图片
	publicService.uploadImage("upload2","upload2_img","upload2_url",limitParam1);				//上传图片
	publicService.uploadImage("upload3","upload3_img","upload3_url",limitParam1);				//上传图片
	
	if(params.method=="add"){
		
	}else if(params.method=="edit"){
		$scope.getEnterpriseInformation(params.enterpriseId);
	}
	
	/*权限分配*/
	if(userInfo.user_type==1 && params.method=="edit"){  //开发者
		
	}else{  
		$("#resetPasswordBtn").remove();
	}
});


