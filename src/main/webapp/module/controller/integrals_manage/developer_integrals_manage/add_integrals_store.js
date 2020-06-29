App.controller("addIntegralsStore",function($scope, $rootScope, $location, $http, $compile, $stateParams, path,$timeout) {
	
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	
	$scope.items=[];            //运营商
	$scope.cmcc={flow:0,price:0} //移动
	$scope.ctcc={flow:0,price:0} //电信
	$scope.cucc={flow:0,price:0} //联通
	$scope.flowpackage=[];      //流量包
	$scope.flowpackageSelect=[]; //选择移动流量包
	$scope.flowpackageSelect1=[]; //选择电信流量包
	$scope.flowpackageSelect2=[]; //选择联通流量包
	$scope.partner=[];           //城市合伙人
	$scope.selectPartner=[];     //选择城市合伙人
	$scope.brand=[];             //品牌
	$scope.selectBrand=[];       //选择品牌
	$scope.shops=[];             //店铺
	$scope.from={};              //添加对象
	
	//上传图片
	$scope.Upload = function(containerId,imgId,imgUrlId,siz,format){
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
		    },
	        error: function(e){
	        	//alert('失败！');
	        },
	        remove: function(e){
	        	//alert('移除！');
	        },
	        select: function(e){	//选择
	        	var image = e.files[0];
        		if(image.size>(siz*1024)){
	        		alert("上传图片的大小请限制在【"+siz+"Kb】以内！");
	        		e.preventDefault();
	        		return;
	        	}
	        	if(image.extension.indexOf(format)==-1){
	        		alert("上传图片仅支持【"+format+"】格式！");
	        		e.preventDefault();
	        		return;
	        	}
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
	$scope.Upload("integrals","integrals_img","integrals_url",80,"jpg");
	
	//查询运营商
	$scope.loadOperator=function(){
		$http({
			   url: path+"/server/queryoperator",
			   method: 'GET',
		}).success(function(data){
			console.log(data);
			$scope.items=data.result;
		}).error(function(data){
			console.log(data);   
		})
	}
	//查询流量包
	$scope.operatorClick=function(obj){
		$("#"+obj.x.operator_code).parent().parent().css({
			"border":"1px solid #00BC81",
			"color":"#00BC81"
		});
		if(obj.x.operator_code=="CMCC"){
			$("#flowpackageSelect").css("display","block");
			$("#flowpackageSelect1").css("display","none");
			$("#flowpackageSelect2").css("display","none");
			$("#CTCC").parent().parent().removeAttr("style");
			$("#CUCC").parent().parent().removeAttr("style");
		}else if(obj.x.operator_code=="CTCC"){
			$("#flowpackageSelect").css("display","none");
			$("#flowpackageSelect1").css("display","block");
			$("#flowpackageSelect2").css("display","none");
			$("#CMCC").parent().parent().removeAttr("style");
			$("#CUCC").parent().parent().removeAttr("style");
		}else if(obj.x.operator_code=="CUCC"){
			$("#flowpackageSelect").css("display","none");
			$("#flowpackageSelect1").css("display","none");
			$("#flowpackageSelect2").css("display","block");
			$("#CMCC").parent().parent().removeAttr("style");
			$("#CTCC").parent().parent().removeAttr("style");
		}
		$http({
			   url: path+"/server/queryflowpackage/"+obj.x.operator_code,
			   method: 'GET',
		}).success(function(data){
			console.log(data);
			$scope.flowpackage=data.result;
			$("#flowpackageDiv").css("display","block");
		}).error(function(data){
			console.log(data);   
		})
	}
	//点击选择流量包
	$scope.selectFlowpackage=function(obj){
		console.log(obj);
		if($scope.flowpackageSelect.length<4 && obj.f.operator_code=="CMCC"){
			var object={};
			object.id=obj.f.id;
			object.flow=obj.f.flow;
			object.price=obj.f.price;
			object.operator_code=obj.f.operator_code;
			if(obj.f.operator_code=="CMCC"){
				//判断移动叠加流量大于总流量
				if((parseInt(obj.f.flow)+$scope.cmcc.flow)<=$("#traffic").val()){ 
					$scope.cmcc.flow=parseInt(obj.f.flow)+$scope.cmcc.flow;
					$scope.cmcc.price=parseInt(obj.f.price)+$scope.cmcc.price;
					$("#CMCC").html($scope.cmcc.flow+"M(¥"+$scope.cmcc.price+")");
					$scope.flowpackageSelect.push(object);
				}else{
					alert("移动叠加流量大于总流量");
				}
			}
		}else if($scope.flowpackageSelect1.length<4 && obj.f.operator_code=="CTCC"){
			var object={};
			object.id=obj.f.id;
			object.flow=obj.f.flow;
			object.price=obj.f.price;
			object.operator_code=obj.f.operator_code;
			if(obj.f.operator_code=="CTCC"){
				//判断移动叠加流量大于总流量
				if((parseInt(obj.f.flow)+$scope.ctcc.flow)<=$("#traffic").val()){ 
					$scope.ctcc.flow=parseInt(obj.f.flow)+$scope.ctcc.flow;
					$scope.ctcc.price=parseInt(obj.f.price)+$scope.ctcc.price;
					$("#CTCC").html($scope.ctcc.flow+"M(¥"+$scope.ctcc.price+")");
					$scope.flowpackageSelect1.push(object);
				}else{
					alert("电信叠加流量大于总流量");
				}
			}
		}else if($scope.flowpackageSelect2.length<4 && obj.f.operator_code=="CUCC"){
			var object={};
			object.id=obj.f.id;
			object.flow=obj.f.flow;
			object.price=obj.f.price;
			object.operator_code=obj.f.operator_code;
			if(obj.f.operator_code=="CUCC"){
				//判断移动叠加流量大于总流量
				if((parseInt(obj.f.flow)+$scope.cucc.flow)<=$("#traffic").val()){ 
					$scope.cucc.flow=parseInt(obj.f.flow)+$scope.cucc.flow;
					$scope.cucc.price=parseInt(obj.f.price)+$scope.cucc.price;
					$("#CUCC").html($scope.cucc.flow+"M(¥"+$scope.cucc.price+")");
					$scope.flowpackageSelect2.push(object);
				}else{
					alert("联通叠加流量大于总流量");
				}
			}
		}
	}
	//删除已选择的流量包组合
	$scope.deleteflowpackage=function(obj,code){
		if(code=="CMCC"){
			$scope.cmcc.flow=$scope.cmcc.flow-parseInt(obj.fs.flow);
			$scope.cmcc.price=$scope.cmcc.price-parseInt(obj.fs.price);
			$scope.flowpackageSelect.splice(obj.$index, 1);
			$("#CMCC").html($scope.cmcc.flow+"M(¥"+$scope.cmcc.price+")");
		}else if(code=="CTCC"){
			$scope.ctcc.flow=$scope.ctcc.flow-parseInt(obj.fs.flow);
			$scope.ctcc.price=$scope.ctcc.price-parseInt(obj.fs.price);
			$scope.flowpackageSelect1.splice(obj.$index, 1);
			$("#CTCC").html($scope.ctcc.flow+"M(¥"+$scope.ctcc.price+")");
		}else if(code=="CUCC"){
			$scope.cucc.flow=$scope.cucc.flow-parseInt(obj.fs.flow);
			$scope.cucc.price=$scope.cucc.price-parseInt(obj.fs.price);
			$scope.flowpackageSelect2.splice(obj.$index, 1);
			$("#CUCC").html($scope.cucc.flow+"M(¥"+$scope.cucc.price+")");
		}
		
	}
	//查询合伙人
	$scope.getPartner=function(){
		$http({
			   url: path+"/server/queryPartner/",
			   method: 'GET',
		}).success(function(data){
			console.log(data);
			$scope.partner=data.result;
		}).error(function(data){
			console.log(data);   
		})
	}
	//选择城市合伙人
	$scope.clickPartner=function(obj,index){
		console.log(obj);
		if(obj.target.checked==true){
			$scope.selectPartner.splice(index,0,$scope.partner[index].id);
			$scope.getBrand($scope.selectPartner);
		}else if(obj.target.checked==false){
			for (var i = 0; i < $scope.selectPartner.length; i++) {
				if($scope.selectPartner[i]==obj.target.id){
					$scope.selectPartner.splice(i,1);
				}
			}
			$scope.getBrand($scope.selectPartner);
		}
	}
	//根据id查询品牌
	$scope.getBrand=function(selectPartner){
		if(selectPartner.length!=0){
			$http({
				url: path+"/server/querybrands",
				method: 'POST',
				data:angular.toJson(selectPartner)
			}).success(function(data){
				$scope.brand=[];
				$scope.brand=data.result;
				$scope.shops=[];
				console.log($scope.brand);
			}).error(function(data){
				console.log(data);   
			})
		}else{
			$scope.brand=[];
			$scope.shops=[];
		}
		
	}
	//选择品牌
	$scope.clickBrand=function(obj,index){
		console.log(obj);
		if(obj.target.checked==true){
			$scope.selectBrand.splice(index,0,$scope.brand[index].id);
			$scope.getShops($scope.selectBrand);
		}else if(obj.target.checked==false){
			for (var i = 0; i < $scope.selectBrand.length; i++) {
				if($scope.selectBrand[i]==obj.target.id){
					$scope.selectBrand.splice(i,1);
				}
			}
			$scope.getShops($scope.selectBrand);
		}
	}
	//根据品牌id查询店铺
	$scope.getShops=function(selectBrand){
		console.log(selectBrand);
		if(selectBrand!=0){
			$http({
				url: path+"/server/queryshops",
				method: 'POST',
				data:angular.toJson(selectBrand)
			}).success(function(data){
				$scope.shops=[];
				$scope.shops=data.result;
				console.log($scope.shops);
			}).error(function(data){
				console.log(data);   
			})
		}else{
			$scope.shops=[];
		}
	}
	//初始化兑换类型
    $scope.initExchangeType=function(){    
    	$("#exchangeType").kendoComboBox({
    		autoBind: false,
    		dataTextField: "name",
    		dataValueField: "id",
    		placeholder: "请选择",
    		filter: "contains",
    		dataSource: [{id:1,name:"流量包"},{id:2,name:"购物卡"},{id:3,name:"实物商品"},{id:4,name:"实物礼品"},{id:5,name:"话费"}],
    		change: function(e) {
	        	var value = this.value();
	        	if(value==1){//流量包
	        		$("#trafficDiv").css("display","block");
	        		$("#operatorDiv").css("display","block");
	        		//隐藏合伙人，品牌和店铺
	        		$("#partnerDiv").css("display","none");
	        		$("#brandDiv").css("display","none");
	        		$("#shopsDiv").css("display","none");
	        		$("#priceDiv").css("display","none");
	        	}else{//其他
	        		$("#trafficDiv").css("display","none");
	        		$("#operatorDiv").css("display","none");
	        		//显示合伙人，品牌和店铺
	        		$("#partnerDiv").css("display","block");
	        		$("#brandDiv").css("display","block");
	        		$("#shopsDiv").css("display","block");
	        		$("#priceDiv").css("display","block");
	        	}
	        }
    	});
    }
    //初始化有效期单位
    $scope.initValidUnit=function(){    
    	$("#valid_unit").kendoComboBox({
    		autoBind: false,
    		dataTextField: "name",
    		dataValueField: "id",
    		placeholder: "请选择",
    		filter: "contains",
    		dataSource: [{id:"year",name:"年"},{id:"month",name:"月"},{id:"day",name:"日"}],
    	});
    }
  //初始化时间控件
	$scope.initDateTime = function(containerId){
		$("#"+containerId).kendoDateTimePicker({
		     value: "",
		     culture: "de-DE",
		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
		     interval: 30,   				  //时间间隔
		});
	}
	
	function getFlowIdString(arr){
		
		var str='';
		for (var j = 0; j < arr.length; j++) {
			if(arr.length-1==j){
				str=str+arr[j].id
			}else{
				
				str=str+arr[j].id+",";
			}
		}
		
		return str;
		
	}
	
	$scope.loadOperator();//查询运营商
	$scope.getPartner();//查询城市合伙人
	$scope.initExchangeType(); //初始化兑换类型
	$scope.initValidUnit(); //初始化日期单位
	$scope.initDateTime("start_time"); //初始化开始时间
	$scope.initDateTime("end_time"); //初始化结束时间
	$timeout(function(){
		//判断是否是编辑
		if(params.method=="edit"){
			$http({
				url: path+"/server/getpoinmapbyid/"+params.id,
				method:'GET', 
				data:angular.toJson($scope.from),
				headers:  {
					"X-Object-Header" : "123456789 ",
		            "Content-Type": "application/json"
		        }
			}).success(function(data){
				console.log(data.result);
				if(data.code==0){
					$scope.id=data.result.id;
					$("#product_title").val(data.result.product_title);
					$("#integrals_img").css({
			    		"background": "url('"+data.result.product_img+"') no-repeat",
			    	    "background-size":"100% 100%",
			    	    "background-color":"white"
			    	});
					//$("#integrals_img").val(data.result.product_img);//图片回显
					$("#integrals_url").val(data.result.product_img);
					$("#exchangeType").data("kendoComboBox").value(data.result.convert_type);//兑换类型回显
					if(data.result.convert_type==1){//流量包
		        		$("#trafficDiv").css("display","block");
		        		$("#operatorDiv").css("display","block");
		        		//隐藏合伙人，品牌和店铺
		        		$("#partnerDiv").css("display","none");
		        		$("#brandDiv").css("display","none");
		        		$("#shopsDiv").css("display","none");
		        		$("#priceDiv").css("display","none");
		        		$("#traffic").val(data.result.price);
		        		var flow=data.result.baseMallFlowEntities;
		        		for (var i = 0; i < flow.length; i++) {
							var fEntit=flow[i].flowPackageEntities
							for (var j = 0; j < fEntit.length; j++) {
								if(fEntit[j].operator_code=="CMCC"){
									$scope.cmccid=flow[i].id;
									$scope.flowpackageSelect.push(fEntit[j]);
									$scope.cmcc.flow=parseInt(fEntit[j].flow)+$scope.cmcc.flow;
									$scope.cmcc.price=parseInt(fEntit[j].price)+$scope.cmcc.price;
									$("#CMCC").html($scope.cmcc.flow+"M(¥"+$scope.cmcc.price+")");
								}else if(fEntit[j].operator_code=="CTCC"){
									$scope.ctccid=flow[i].id;
									$scope.flowpackageSelect1.push(fEntit[j]);
									$scope.ctcc.flow=parseInt(fEntit[j].flow)+$scope.ctcc.flow;
									$scope.ctcc.price=parseInt(fEntit[j].price)+$scope.ctcc.price;
									$("#CTCC").html($scope.ctcc.flow+"M(¥"+$scope.ctcc.price+")");
								}else if(fEntit[j].operator_code=="CUCC"){
									$scope.cuccid=flow[i].id;
									$scope.flowpackageSelect2.push(fEntit[j]);
									$scope.cucc.flow=parseInt(fEntit[j].flow)+$scope.cucc.flow;
									$scope.cucc.price=parseInt(fEntit[j].price)+$scope.cucc.price;
									$("#CUCC").html($scope.cucc.flow+"M(¥"+$scope.cucc.price+")");
								}
							}
						}
		        		
		        	}else{//其他
		        		$("#trafficDiv").css("display","none");
		        		$("#operatorDiv").css("display","none");
		        		//显示合伙人，品牌和店铺
		        		$("#partnerDiv").css("display","block");
		        		$("#brandDiv").css("display","block");
		        		$("#shopsDiv").css("display","block");
		        		$("#priceDiv").css("display","block");
		        		var partner=data.result.partner_ids.split(",");
		        		$scope.getBrand(partner);//根据合伙人id查询品牌
		        		
		        		var partnerInp = $("#partnerDiv").find("input");
		        		for (var j = 0; j < partner.length; j++) {
		        			for (var l = 0; l < partnerInp.length; l++) {
								if(partner[j]==partnerInp[l].id){
									partnerInp[l].checked=true;
								}
							}
		    			}
		        		
		        		var brand=data.result.brand_ids.split(",");
		        		$scope.getShops(brand);
		        		
		        		$timeout(function(){
		        			var brandInp = $("#brandDiv").find("input");
		        			for (var j = 0; j < brand.length; j++) {
			        			for (var l = 0; l < brandInp.length; l++) {
			        				if(brand[j]==brandInp[l].id){
			        					brandInp[l].checked=true;
			        				}
			        			}
			        		}
			        		var shop=data.result.shop_ids.split(",");
			        		var shopInp = $("#shopsDiv").find("input");
			        		for (var j = 0; j < shop.length; j++) {
			        			for (var l = 0; l < shopInp.length; l++) {
			        				if(shop[j]==shopInp[l].id){
			        					shopInp[l].checked=true;
			        				}
			        			}
			        		}
		        		},3000);
		        	}
					$("#point").val(data.result.point);//所需积分
					$("#product_num").val(data.result.product_num);//库存数量
					$("#price").val(data.result.price);//价格
					$("#start_time").data("kendoDateTimePicker").value(data.result.start_time);
					$("#end_time").data("kendoDateTimePicker").value(data.result.end_time);
					$("#valid_time").val(data.result.valid_time);
					$("#valid_unit").data("kendoComboBox").value(data.result.valid_unit);
					//百度图文编辑器回显数据data.result.description
					// UE.getEditor('container2').execCommand('insertHtml', data.result.description);
					$("#textareaValue").val(data.result.description);
					
				}
				
			}).error(function(data){
				alert(data);   
			}) 
		}
		
	},1000);
	//判断是否为空
	$scope.isNull=function(valueIsNull){
		if(valueIsNull==null||valueIsNull==""||valueIsNull==undefined){
			return true;
		}else{
			return false;
		}
	}
	//保存积分产品
	$scope.insertProduct=function(){
		$scope.from.product_title=$("#product_title").val();
		$scope.from.product_img=$("#integrals_url").val();
		var type=$("#exchangeType").val();
		if(type==1){//当兑换类型是流量包
			//总流量
			$scope.from.price=$("#traffic").val();
			$scope.from.baseMallFlowEntities=[];
			if(params.method=="edit"){
				$scope.from.baseMallFlowEntities.push({"id":$scope.cmccid,"flow_group_id":getFlowIdString($scope.flowpackageSelect)})
				$scope.from.baseMallFlowEntities.push({"id":$scope.ctccid,"flow_group_id":getFlowIdString($scope.flowpackageSelect1)});
				$scope.from.baseMallFlowEntities.push({"id":$scope.cuccid,"flow_group_id":getFlowIdString($scope.flowpackageSelect2)});
			}else{
				$scope.from.baseMallFlowEntities.push({"flow_group_id":getFlowIdString($scope.flowpackageSelect)});
				$scope.from.baseMallFlowEntities.push({"flow_group_id":getFlowIdString($scope.flowpackageSelect1)});
				$scope.from.baseMallFlowEntities.push({"flow_group_id":getFlowIdString($scope.flowpackageSelect2)});
			}
		}else{
			$scope.from.price=$("#price").val();//参考价格
			var partner = $("#partnerDiv").find("input");
			 $scope.from.partner_ids=[];
			for (var j = 0; j < partner.length; j++) {
				if(partner[j].checked==true){
					$scope.from.partner_ids.push(partner[j].id);
				}
			}
			var brand = $("#brandDiv").find("input");
			$scope.from.brand_ids=[];
			for (var j = 0; j < brand.length; j++) {
				if(brand[j].checked==true){
					$scope.from.brand_ids.push(brand[j].id);
				}
			}
			var shops = $("#shopsDiv").find("input");
			$scope.from.shop_ids=[];
			for (var j = 0; j < shops.length; j++) {
				if(shops[j].checked==true){
					$scope.from.shop_ids.push(shops[j].id);
				}
			}
			$scope.from.shop_ids=$scope.from.shop_ids.join(',');
			$scope.from.brand_ids=$scope.from.brand_ids.join(',');
			$scope.from.partner_ids=$scope.from.partner_ids.join(',');
		}
		$scope.from.convert_type=$("#exchangeType").val();//兑换类型
		$scope.from.point=$("#point").val();//所需积分
		$scope.from.product_num=$("#product_num").val();//库存数量
		
		$scope.from.start_time = $("#start_time").val();//开始时间
		$scope.from.end_time=$("#end_time").val();//结束时间
		$scope.from.valid_time=$("#valid_time").val();//有效期限
		$scope.from.valid_unit=$("#valid_unit").val();//有效期限单位
		
		//$scope.from.description=UE.getEditor('container2').getContent();
		$scope.from.description=$("#textareaValue").val();
		if($scope.from.product_title==null||$scope.from.product_title==undefined){
			alert("请填写商品名称");
		}else if($scope.isNull($scope.from.point)){
			alert("请填写所需积分");
		}else if($scope.isNull($scope.from.product_num)){
			alert("请填写库存数量");
		}else if($scope.isNull($scope.from.start_time)){
			alert("请填写开始时间");
		}else if($scope.isNull($scope.from.end_time)){
			alert("请填写结束时间");
		}else  if($scope.isNull($scope.from.valid_time)){
			alert("请填写有效期限");
		}else if($scope.isNull($scope.from.valid_unit)){
			alert("请填写有效期限单位");
		}else if($("#traffic").val()!=$scope.cmcc.flow&&type==1){
			alert("移动流量组合与总流量不相等");
		}else if($("#traffic").val()!=$scope.ctcc.flow&&type==1){
			alert("电信流量组合与总流量不相等");
		}else if($("#traffic").val()!=$scope.cucc.flow&&type==1){
			alert("联通流量组合与总流量不相等");
		}else{
			if(params.method=="add"){
				console.log(angular.toJson($scope.from));
				$http({
					url: path+"/server/createpoinmall",
					method:'POST', 
					data:angular.toJson($scope.from),
					headers:  {
						"X-Object-Header" : "123456789 ",
			            "Content-Type": "application/json"
			        }
				}).success(function(data){
					if(data.code==0){
						alert(data.message);
						$location.path("/developerIntegralsStore");
					}else{
						alert(data.message);
					}
				}).error(function(data){
					alert(data);   
				}) 
			}else if(params.method=="edit"){
				$scope.from.id=$scope.id;
				console.log(angular.toJson($scope.from));
				$http({
					url: path+"/server/modifypoinmall",
					method:'POST', 
					data:angular.toJson($scope.from),
					headers:  {
						"X-Object-Header" : "123456789 ",
			            "Content-Type": "application/json"
			        }
				}).success(function(data){
					if(data.code==0){
						alert(data.message);
						$location.path("/developerIntegralsStore");
					}else{
						alert(data.message);
					}
				}).error(function(data){
					alert(data);   
				}) 
			}
		}
		
	}
})