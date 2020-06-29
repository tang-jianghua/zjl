App.controller("addBrandInformationCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, path, $sce, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	

	$scope.form = {};                  //表单
	$scope.form.bankEntity = {};  		 //银行信息
	$scope.form.userEntity = {};   		 //用户信息
	$scope.form.commerceEntity = {};   	 //工商信息

	var fatherInfo = null;  //父级信息
	var brandInfo = null;   //品牌信息
	
	var limitParam1 = {imageSize:500};		//证件图


	//初始化品牌名称  
	$scope.initBrandName = function(){
		$("#brandName").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    placeholder: "请选择品牌名称",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type: 'GET',
	                  url: path+"/server/buildenterpriselist",
	                  dataType: "json"
	              }
	          }
		    },
		    change: function(e) {
		    	if(this.dataItem()){
		    		var imgUrl = this.dataItem().imgurl;
		    		publicService.showUploadImage("logo_img","logo_url",imgUrl);
		    	    $scope.productClass = this.dataItem().buildClassEntity.name;
		    	    //$scope.$digest();
		    	    $scope.$apply();
		    	}
		    }
		});
	}

	//初始化品牌类型
	$scope.initBrandType = function(){
		$("#brand_type").kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
			             {
				              name:"线下实体品牌",
				              value:"0"
		            	  },
		            	  {
				              name:"线上品牌",
				              value:"1"
		            	  }
			             ]
		});
	}
	
	//初始化审核状态
	$scope.initCheckStatus = function(){
		$("#check_state").kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
			             {
				              name:"审核中",
				              value:"1"
		            	  },
		            	  {
				              name:"已通过",
				              value:"2"
		            	  },
		            	  {
				              name:"未通过",
				              value:"3"
		            	  }
			             ]
		});
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
			   url:	path+'/server/resetpassword/'+brandInfo.sys_user_id,
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
			   url: path+'/server/initbrand',
			   method: 'GET' 
		}).success(function(data){  
			fatherInfo = data;	
			
			$scope.enterpriseName = fatherInfo.enterprise.enterprise_name+"(企业名称)";   //品牌属性(企业名称)
			$scope.partnerName = fatherInfo.partner.principal+"(合伙人姓名)";			   //品牌属性(合伙人姓名)
			//$scope.productClass = fatherInfo.enterprise.class_name;		    //产品品类
			$scope.showArea(fatherInfo.partner.region_id);

		}).error(function(data){
			alert(data);   
		})
	}
	
	//显示服务区域名称
	$scope.showArea = function(areaIds){
		$http({
			   url: path+'/server/getregion/'+areaIds,
			   method: 'GET'
		}).success(function(data){ 
			var areaArr = data.result.split(",");
			
			var province = areaArr[0];
			var city = areaArr[1];
			var area = "";
			$.each(areaArr, function(index, OneObj){
				if(index>=2){
					area += (OneObj+",");
				}
			});
			area = area.substring(0,area.length-1);

			$scope.serveArea = $sce.trustAsHtml("省-----"+province+";市-----"+city+";<br>区-----"+area);
			
		}).error(function(data){
			alert(data);   
		})
	}
	
	//提交
	$scope.formSubmit = function(){ 
		$scope.form.enterprise_id= $("#brandName").data("kendoComboBox").value();     //品牌名称（企业Id）
		if( isNaN(Number($scope.form.enterprise_id)) ){
			alert("此品牌不存在，请联系管理人员");
			return;
		}
		
		$scope.form.commerceEntity.biz_url = $("#upload1_url").val();
    	$scope.form.commerceEntity.open_acount_url = $("#upload2_url").val();
    	$scope.form.commerceEntity.tax_url = $("#upload3_url").val();
    	
    	$scope.form.brand_idintify_type = $("#brand_type").data("kendoDropDownList").value();
    	$scope.form.state = $("#check_state").data("kendoDropDownList").value();
    	
    	//参数验证
		if(!$scope.form.enterprise_id){
			alert("请填写【代理品牌】！");
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
		if(!$scope.form.hot_line){
			alert("请填写【服务热线】！");
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
		
		if(!$scope.form.brand_idintify_type){
			alert("请选择【品牌类型】！");
			return;
		}
		if(!$scope.form.state){
			alert("请选择【审核状态】！");
			return;
		}
    	
		
		var postUrl = '';
		if(params.method=="add"){
			postUrl = path+'/server/createbrand';
		}else if(params.method=="edit"){
			postUrl = path+'/server/modifybrand';
			$scope.form.id = brandInfo.id;
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
	
	//获取品牌信息
	$scope.getBrandInformation = function(id){
		$http({
			   url:path+'/server/getbrandbyid/'+id,
			   method: 'GET',   
			   data: angular.toJson($scope.form)
		}).success(function(data){
			brandInfo = data.result;
			
			$("#brandName").data("kendoComboBox").value(brandInfo.enterprise_id);   //品牌名称
			$scope.form.principal = brandInfo.principal;
			$scope.form.phone_num = brandInfo.phone_num;
			publicService.showUploadImage("logo_img","logo_url",brandInfo.buildEnterpriseEntity.imgurl);
			
			$scope.form.hot_line = brandInfo.hot_line;   //服务热线

			//用户信息
			$scope.form.userEntity.account = brandInfo.userEntity.account;
			$scope.form.userEntity.pwd = "******";
			$("#pwd").attr("readonly","readonly");
			//工商注册信息
			if(params.method=="look"){
				var commerceInfoArr = new Array();
				commerceInfoArr.push({
					id: "commerceInfo_1",
					url: brandInfo.commerceEntity.biz_url
				});
				commerceInfoArr.push({
					id: "commerceInfo_2",
					url: brandInfo.commerceEntity.open_acount_url
				});
				commerceInfoArr.push({
					id: "commerceInfo_3",
					url: brandInfo.commerceEntity.tax_url
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
				publicService.showUploadImage("upload1_img","upload1_url",brandInfo.commerceEntity.biz_url);
				publicService.showUploadImage("upload2_img","upload2_url",brandInfo.commerceEntity.open_acount_url);
				publicService.showUploadImage("upload3_img","upload3_url",brandInfo.commerceEntity.tax_url);
			}
			

			$scope.form.commerceEntity.biz_num = brandInfo.commerceEntity.biz_num;
			$scope.form.commerceEntity.open_acount = brandInfo.commerceEntity.open_acount;
			$scope.form.commerceEntity.tax_num = brandInfo.commerceEntity.tax_num;
			//银行信息
			$scope.form.bankEntity.name = brandInfo.bankEntity.name;
			$scope.form.bankEntity.account = brandInfo.bankEntity.account;
			$scope.form.bankEntity.addr = brandInfo.bankEntity.addr;
			//第三方账户
			$scope.form.bankEntity.pay_account = brandInfo.bankEntity.pay_account;
			$scope.form.bankEntity.webkit_account = brandInfo.bankEntity.webkit_account;

			$("#brand_type").data("kendoDropDownList").value(brandInfo.brand_idintify_type);   //品牌类型
			$("#check_state").data("kendoDropDownList").value(brandInfo.state);   //审核状态
			
			
			if(params.method=="edit" || params.method=="look"){
				$scope.enterpriseName = brandInfo.partnerEntity.enterpriseEntity.enterprise_name+"(企业名称)";   //品牌属性(企业名称)
				$scope.partnerName = brandInfo.partnerEntity.principal+"(合伙人姓名)";			   //品牌属性(合伙人姓名)
				$scope.productClass = brandInfo.buildEnterpriseEntity.buildClassEntity.name;		    //产品品类
				$scope.showArea(brandInfo.partnerEntity.region_id);
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
			$scope.getAreaData(result.city.code,brandInfo.region_code.split(","));

		}).error(function(data){
			alert(data);   
		})
	}
	

	
	$scope.initBrandName();    //初始化品牌名称
	publicService.uploadImage("upload1","upload1_img","upload1_url",limitParam1);				//上传图片
	publicService.uploadImage("upload2","upload2_img","upload2_url",limitParam1);				//上传图片
	publicService.uploadImage("upload3","upload3_img","upload3_url",limitParam1);				//上传图片
	$scope.initBrandType();			//初始化品牌类型
	$scope.initCheckStatus();		//初始化审核状态
	
	if(params.method=="add"){
		$scope.getFatherInfo();		//获取父级信息
	}else if(params.method=="edit"){
		$scope.getBrandInformation(params.brandId);
	}else if(params.method=="look"){
		$scope.getBrandInformation(params.brandId);
		
		$("#formSubmitButton").remove();
		$("input").attr("readonly","readonly");
		$("#brandName").data("kendoComboBox").readonly(true);
		$("#check_state").data("kendoDropDownList").readonly(true);
	}
	
	/*权限分配*/
	if(userInfo.user_type==3 && params.method=="edit"){  //合伙人
		
	}else{  
		$("#resetPasswordBtn").remove();
	}
});


