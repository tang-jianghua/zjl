App.controller("addSeckillActivityCtrl",function($scope, $rootScope, $location, $http, $compile, $stateParams, $timeout, path, publicService) {
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.form = {};     			 //表单
	$scope.form.timeEntity = [];     //时间段
	var activityInfo = null;	//活动信息
	
	var limitParam1 = {imageSize:80, format:"jpg"};		//活动宣传图
	
	
	
	//初始化活动类型
	$scope.initActivityType = function(){
		$("#activity_type").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
		    value: "0",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 0,
			 				text: "秒杀"
			             },
			             {
			            	 id: 1,
			 				text: "满减"
			             }
            ]
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
	
	//参与合伙人
	$scope.initJoinPartner = function(){
		$("#join_partner").kendoMultiSelect({
		    dataTextField: "principal",
		    dataValueField: "id",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'POST',
	                  url: path+"/server/selectAllPartner",
	                  dataType : "json"
	              }
	          }
	      }
		});
	}
	
	//删除图片
	$scope.deletePicture = function(type){
		if(confirm("确认删除？")){
			if(params.method=="add"){
				if(type==1){
					publicService.removeUploadImage("introduce_img","introduce_url");		//活动宣传图
				}
			}else if(params.method=="edit"){
				var postData = {};
				postData.id = activityInfo.id;
					
				if(type==1){
					postData.imgurl1 = "";   //活动宣传图
				}

				$http({
					   url: path+'/server/modifyplatformpromotion',
					   method: 'POST',   
					   data: angular.toJson(postData)
				}).success(function(data){
					if(data.code==0){  //成功
						window.location.reload();   //页面刷新
					}else if(data.code==1){  //失败
						alert(data.message);
					}
				}).error(function(data){
					alert("删除失败！");   
				})
			}
		}
	}
	
	//表单提交
	$scope.formSubmit = function(){
		$scope.form.type = $("#activity_type").data("kendoDropDownList").value();	//活动类型

		$scope.form.limit_time = $("#endTime").val();		//报名截止日期
		$scope.form.start_time = $("#start_time").val();	//活动日期（始）
		$scope.form.end_time = $("#end_time").val();		//活动日期（止）

		//参数验证
		if(!$scope.form.title){
			alert("请填写【活动名称】！");
			return;
		}
		if(!$scope.form.type){
			alert("请填写【活动类型】！");
			return;
		}
		if(!$scope.form.limit_time){
			alert("请填写【报名截止日期】！");
			return;
		}
		if(!$scope.form.start_time){
			alert("请填写【活动开始日期】！");
			return;
		}else{
			$scope.form.start_time += ' 00:00:00';
		}
		if(!$scope.form.end_time){
			alert("请填写【活动结束日期】！");
			return;
		}else{
			$scope.form.end_time += ' 23:59:59';
		}
		
		//时间验证
		if($scope.form.limit_time>($scope.form.start_time+" 00:00:00")){
			alert("【报名截止日期】不得大于【活动开始日期】！");
			return;
		}
		if($scope.form.start_time>$scope.form.end_time){
			alert("【活动开始日期】不得大于【活动结束日期】！");
			return;
		}

		$scope.form.timeEntity = [];
		for(var i=1;i<=5;i++){
			var oneTime = {};
			oneTime.pstart_time = $("#start_time"+i).val();
			oneTime.pend_time = $("#end_time"+i).val();
			oneTime.promotion_brand_num = $("#brand_num_"+i).val();
			if(params.method=="edit"){
				oneTime.id = activityInfo.timeEntity[i-1].id;
			}

			if(oneTime.pstart_time && oneTime.pend_time && oneTime.promotion_brand_num){
				$scope.form.timeEntity.push(oneTime);
			}else if(!oneTime.pstart_time && !oneTime.pend_time && !oneTime.promotion_brand_num){
				
			}else{	//有信息
				alert("请填写完全第  "+i+" 个时间段的信息！");
				return;
			}
		}

		$scope.form.partner_id = $("#join_partner").data("kendoMultiSelect").value().toString();	//参与合伙人
		$scope.form.class_id = $("#join_class").data("kendoMultiSelect").value().toString();		//参与品类
		$scope.form.imgurl1 = $("#introduce_url").val();	//活动宣传图
		
		//参数验证
		if($scope.form.timeEntity.length==0){
			alert("请至少填写一个【活动段】信息！");
			return;
		}
		if(!$scope.form.promotion_product_num){
			alert("请填写【报名数量】！");
			return;
		}
		if(!$scope.form.partner_id){
			alert("请填写【参与合伙人】！");
			return;
		}
		if(!$scope.form.class_id){
			alert("请填写【参与品类】！");
			return;
		}
		if(!$scope.form.imgurl1){
			alert("请填写【活动宣传图】！");
			return;
		}
	
		//验证(时间段)
		var beginTime = "00:00";
		$.each($scope.form.timeEntity, function(index, OneObj){
			if(OneObj.pstart_time>OneObj.pend_time){
				alert("你填写的第"+(index+1)+"个时间段【开始时间】应该  <= 【结束时间】！");
				return;
			}
			if(beginTime>OneObj.pstart_time){
				alert("你填写的第"+(index+1)+"个时间段的【开始时间】应该  >= 上一个时间段的【结束时间】！");
				return;
			}
			beginTime = OneObj.pend_time;
		});
		
		
		var url = '';
		if(params.method=="add"){
			url = path+'/server/createplatformpromotion';
			$scope.form.state = 1;
		}else if(params.method=="edit"){
			url = path+'/server/modifyplatformpromotion';
			$scope.form.id = activityInfo.id;
			$scope.form.state = activityInfo.state;
		}

		$("#formSubmitButton").attr("disabled","true");

		if(confirm("您确定发布该秒杀活动吗？")){
			$http({
				   url: url,
				   method: 'POST',   
				   data: angular.toJson($scope.form),  
			}).success(function(data){
				alert(data.message);
				if(data.code==0){  //成功
					$location.path("platformActivity");
				}else if(data.code==1){  //失败
					
				}
				$("#formSubmitButton").removeAttr("disabled");
			}).error(function(data){
				alert("秒杀活动发布失败！");   
			})
		}
	}
	
	//获取活动信息
	$scope.getActivityInfo = function(id){
		$http({
			   url: path+'/server/getplatformpromotionbyid/'+id,
			   method: 'GET'
		}).success(function(data){
			activityInfo = data;
			
			$scope.form.title = activityInfo.title;		//活动名称
			$("#activity_type").data("kendoDropDownList").value(activityInfo.type);	//活动类型

			$("#endTime").val(activityInfo.limit_time);		//报名截止日期
			$("#start_time").val(activityInfo.start_time);	//活动日期（始）
			$("#end_time").val(activityInfo.end_time);		//活动日期（止）
			$.each(activityInfo.timeEntity, function(index, OneObj){
				$("#start_time"+(index+1)).val(OneObj.pstart_time_str);
				$("#end_time"+(index+1)).val(OneObj.pend_time_str);
				if(OneObj.promotion_brand_num>0){
					$("#brand_num_"+(index+1)).val(OneObj.promotion_brand_num);
				}
			});
			
			$scope.form.promotion_product_num = activityInfo.promotion_product_num;		//报名数量
			$("#join_class").data("kendoMultiSelect").value(activityInfo.class_id.split(","));		//参与品类
			$("#join_partner").data("kendoMultiSelect").value(activityInfo.partner_id.split(","));	//参与品合伙人
				
			publicService.showUploadImage("introduce_img","introduce_url",activityInfo.imgurl1);	//活动宣传图
			
			
		}).error(function(data){
			alert(data);   
		})
		
	}
	
	
	
	

	$scope.initActivityType();		//初始化活动类型
	$("#activity_type").data("kendoDropDownList").readonly(true);
	publicService.initDateTime("endTime");   //报名截止日期
	publicService.initDate("start_time");    //活动日期（始）
	publicService.initDate("end_time");   	 //活动日期（止）
	if(params.method=="add"){
		$timeout(function(){
			for(var i=1;i<=5;i++){
				jeDate({
			        dateCell: '#start_time'+i,
			        //skinCell:"jedategreen",
			        isTime:true,
			        isinitVal:false,
			        format: 'hh:mm'
			    });
				jeDate({
			        dateCell: '#end_time'+i,
			        //skinCell:"jedategreen",
			        isinitVal:false,
			        format: 'hh:mm'
			    });
			}
	    }, 1000);
	}
	
	$scope.initJoinClass();		//参与品类	
	$scope.initJoinPartner();	//参与合伙人
	publicService.uploadImage("introduce","introduce_img","introduce_url",limitParam1);		//活动宣传图

	
	
	if(params.method=="add"){
		
	}else if(params.method=="edit"){
		$scope.getActivityInfo(params.activityId);		//获取活动信息
		
		$("#endTime").data("kendoDateTimePicker").readonly(true);
		$("#start_time").data("kendoDatePicker").readonly(true);
		$("#end_time").data("kendoDatePicker").readonly(true);
		for(var i=1;i<=5;i++){
			$("#start_time"+i).attr("readonly","readonly");
			$("#end_time"+i).attr("readonly","readonly");
		}
	}else if(params.method=="look"){
		$scope.getActivityInfo(params.activityId);		//获取活动信息
		
		$("input").attr("readonly","readonly");
		$("#join_class").data("kendoMultiSelect").readonly(true);
		$("#join_partner").data("kendoMultiSelect").readonly(true);
		
		$("#formSubmitButton").hide();
		$(".operateContainer").hide();
	}
	

});