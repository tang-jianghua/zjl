App.controller("builderDetailsCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, $timeout, path, publicService) {

	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	var buiderInfo = null;		//施工信息
	$scope.details = {};		//详情
	$scope.form = {};			//表单
	$scope.checkForm = {};	//审核
	

	//初始化审核状态
	$scope.initCheckState = function(){
		$("#checkState").kendoDropDownList({
			dataTextField: "value",
		    dataValueField: "key",
			dataSource: [
			             {
			            	 key: "",
			 			   value: "请选择"
			             },
			             {
			            	 key: 1,
		            	   value: "确认通过"
			             },
			             {
			            	 key: 0,
			               value: "未通过"
			             }
            ],
            change: function(e) {
	    	    var value = this.value();
	    	    if(value!='' && value==0){	//未通过
	    	    	$("#unCheckedParam").show();
	    	    }else{	
	    	    	$("#unCheckedParam").hide();
	    	    }
            }
		});
	}
	
	//初始化服务类型
	$scope.initServerType = function(){
		$("#serverType").kendoDropDownList({
			dataTextField: "service_type",
		    dataValueField: "service_code",
		    dataSource: {
			      serverFiltering: true,
		          transport: {
		              read: {
		            	  type : 'POST',
		                  url: path+"/server/queryservicetypeback",
		                  dataType : "json"
		              }
		          },
		          schema : {
						data : function(d) {
							d.result.unshift({"service_code":"","service_type":"请选择"});
							return d.result;
						}
					},
		      }
		});
	}
	
	//获取施工信息
	$scope.getBuilderInfo = function(id){
		var postData = {id: id};
		
		$http({
			   url: path+'/server/queryconstructdetailbackbyid',
			   method: 'POST',   
			   data: angular.toJson(postData),  
		}).success(function(data){
			if(data.code==0){  //成功
				buiderInfo = data.result;
				
				if(buiderInfo.head_img){
					publicService.showUploadImage("head_img",null,buiderInfo.head_img);  //头像
				}else{
					publicService.showUploadImage("head_img",null,'../../images/icon/people.png');  //头像
				}
				
				$scope.details.score = buiderInfo.score;			//评分等级
				$scope.details.construct_about = buiderInfo.construct_about;	//施工简介
				
				if(params.method=="waitingCheck" || params.method=="look"){   //待审核,查看
					$scope.details.team_name = buiderInfo.team_name;			//施工名称
					$scope.details.region_name = buiderInfo.region_name;		//服务区域
					
					$scope.details.service_type = buiderInfo.service_type;		//服务类型
					$scope.details.unit = buiderInfo.unit;						//施工单价
					
					$scope.details.name = buiderInfo.name;		    //认证姓名
					$scope.details.phone = buiderInfo.phone;		//联系方式
					$scope.details.ali_account = buiderInfo.ali_account;		//支付宝账号

					//身份认证
					var idcardArr = new Array();
					idcardArr.push(buiderInfo.idcard_front);
					idcardArr.push(buiderInfo.idcard_back);
					idcardArr.push(buiderInfo.idcard_hold);
					$scope.showImageInfo("idcardContainer",idcardArr);
					
					//资质认证
					var qualificationArr = new Array();
					qualificationArr.push(buiderInfo.qualification_one);
					qualificationArr.push(buiderInfo.qualification_two);
					qualificationArr.push(buiderInfo.qualification_three);
					$scope.showImageInfo("qualificationContainer",qualificationArr);
					
					$('.fancybox').fancybox();
					
					//审核详情
					if(params.method=="look"){
						if(buiderInfo.certification_state==0){
							$scope.details.certification_state = "未通过";
						}else if(buiderInfo.certification_state==1){
							$scope.details.certification_state = "通过";
						}else if(buiderInfo.certification_state==2){
							$scope.details.certification_state = "未审核";
						}
						$scope.details.updatetime = buiderInfo.updatetime;	//审核时间
						$scope.details.info = buiderInfo.info;				//审核跟踪
					}else if(params.method=="waitingCheck"){
						$scope.checkForm.phone_num = buiderInfo.phone_num;	//人工热线(合伙人电话)
					}
				}else if(params.method=="edit"){	//编辑	
					$scope.form.team_name = buiderInfo.team_name;			//施工名称
					if(buiderInfo.region_code){
						//$scope.getNameByCode(buiderInfo.region_code.split(",")[1]);  //服务区域
						var areaArr = buiderInfo.region_code.split(",");
						var province_code = areaArr[0];
						var city_code = areaArr[1];
						$("#province").data("kendoComboBox").value(province_code);
						publicService.getCityData(2,province_code,city_code);

						$timeout(function(){
							areaArr.shift();
							areaArr.shift();
							publicService.getAreaData(2,city_code,areaArr);
					    }, 100);
					}
					
					$("#serverType").data("kendoDropDownList").value(buiderInfo.service_code);	//服务类型
					
					$scope.form.unit_price = buiderInfo.unit_price;						//施工单价
					$scope.form.unit_name = buiderInfo.unit_name;						//单位
					
					$scope.form.name = buiderInfo.name;		    //认证姓名
					$scope.form.phone = buiderInfo.phone;		//联系方式
					$scope.form.ali_account = buiderInfo.ali_account;		//支付宝账号

					//身份认证
					var idcardArr = new Array();
					idcardArr.push(buiderInfo.idcard_front);
					idcardArr.push(buiderInfo.idcard_back);
					idcardArr.push(buiderInfo.idcard_hold);
					$scope.showImageInfo("idcardContainer_edit",idcardArr);
					
					//资质认证
					var qualificationArr = new Array();
					qualificationArr.push(buiderInfo.qualification_one);
					qualificationArr.push(buiderInfo.qualification_two);
					qualificationArr.push(buiderInfo.qualification_three);
					$scope.showImageInfo("qualificationContainer_edit",qualificationArr);
					
					$('.fancybox').fancybox();
					
					//审核详情
					if(buiderInfo.certification_state==0){
						$scope.details.certification_state = "未通过";
					}else if(buiderInfo.certification_state==1){
						$scope.details.certification_state = "通过";
					}else if(buiderInfo.certification_state==2){
						$scope.details.certification_state = "未审核";
					}
					$scope.details.updatetime = buiderInfo.updatetime;	//审核时间
					$scope.details.info = buiderInfo.info;				//审核跟踪
				}
			}else if(data.code==1){  //失败
				alert("获取信息失败！");
			}
		}).error(function(data){
			alert("获取信息失败！");  
		})
	}
	
	//显示图片信息
	$scope.showImageInfo = function(containerId,imageArr){
		var html = '';
		
		$.each(imageArr, function(index, OneObj){
			if(OneObj){
				html += ('<div class="col-sm-2">'
							+'<a class="fancybox" href="'+OneObj+'" data-fancybox-group="gallery_'+containerId+'" title="">'
								+'<img src="'+OneObj+'" class="imgContainer" alt=""/>'
							+'</a>'
						+'</div>');
			}
		});
		
		$("#"+containerId).append(html);
	}
	
	//根据code获取name
	$scope.getNameByCode = function(code){
		$http({
			   url: path+'/server/getname/'+code,
			   method: 'GET' 
		}).success(function(data){
			var result = data.result;
			$("#province").data("kendoComboBox").value(result.province.code);
			publicService.getCityData(2,result.province.code,result.city.code);
			
			var areaArr = buiderInfo.region_code.split(",");
			areaArr.shift();
			areaArr.shift();
			publicService.getAreaData(2,result.city.code,areaArr);

		}).error(function(data){
			alert(data);   
		})
	}
	
	//审核状态更改
	$scope.stateSubmit = function(){
		var postData = {};
		postData.id = buiderInfo.id;
		postData.certification_state = $("#checkState").data("kendoDropDownList").value();
		if(postData.certification_state==0){
			if($scope.checkForm.info){
				postData.info = "施工认证因："+$scope.checkForm.info+"；未通过审核，请重新填写；如有疑问请致电："+$scope.checkForm.phone_num+"。";
			}else{
				alert("请填写审核不通过理由！");
				return;
			}
		}
		
		$http({
			   url: path+'/server/changeverifystate',
			   method: 'POST',   
			   data: angular.toJson(postData),  
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
	
	//表单提交
	$scope.formSubmit = function(){
		$scope.form.id = buiderInfo.id;
		$scope.form.region_code = $("#area").data("kendoMultiSelect").value().toString();
		$scope.form.service_code = $("#serverType").data("kendoDropDownList").value();

		//身份认证
		$scope.form.idcard_front = $("#idcard1_url").val();
		$scope.form.idcard_back = $("#idcard2_url").val();
		$scope.form.idcard_hold = $("#idcard3_url").val();
		//资质认证
		$scope.form.qualification_one = $("#qualification1_url").val();
		$scope.form.qualification_two = $("#qualification2_url").val();
		$scope.form.qualification_three = $("#qualification3_url").val();

		$http({
			   url: path+'/server/saveconstructdetailback',
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
	
	$scope.initCheckState();		//初始化审核状态
	$("#unCheckedParam").hide();
	
	publicService.initProvince(2);		//初始化省
	publicService.initCity(2);			//初始化市
	publicService.initArea(2);			//初始化区
	$scope.initServerType();		//初始化服务类型

	
	$scope.getBuilderInfo(params.buiderId);
	
	if(params.method=="waitingCheck"){   //待审核
		$("#editContainer").hide();
		$("#checkDetailsContainer").hide();
		$("#editButton").hide();
	}else if(params.method=="edit"){	//编辑	
		$("#detailsContainer").hide();
		$("#checkStateContainer").hide();
		$("#checkButton").hide();
	}else if(params.method=="look"){	//查看
		$("#editContainer").hide();
		$("#checkStateContainer").hide();
		$("#editButton").hide();
		$("#checkButton").hide();
	}
	
	
});