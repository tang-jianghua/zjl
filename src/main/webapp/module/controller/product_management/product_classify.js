App.controller("productClassifyCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	var dataArr1 = null;		//行业分类数据
	var dataArr2 = null;		//品类分类数据
	var dataArr3 = null;		//企业名称数据
	
	var addInput_index = 0;   //增加分类下标
	var focusInput = '';      //获得焦点的文本框
	
	var limitParam1 = {imageSize:20, format:"jpg"};		//品类图
	var limitParam2 = {imageSize:80, format:"jpg/png"};		//品类示意图
	
	$scope.classSpecial_show = false;	//品类特殊参数（示意图，色值）
	
	
	//获取三种分类数据
	$scope.getClassData = function(type,showWhich){
		var url = '';
		if(type==1){		//行业分类
			url = path+'/server/buildmaterialslist';
		}else if(type==2){	//品类分类
			url = path+'/server/buildclassentities';
		}else if(type==3){	//企业分类
			url = path+'/server/buildenterpriselist';
		}

		$http({
			   url: url,
			   method: 'GET' 
		}).success(function(data){  
			console.log("分类数据：",data);
			if(type==1){		//行业分类
				dataArr1 = data;
				$scope.getClassData(2,showWhich);
			}else if(type==2){	//品类分类
				dataArr2 = data;
				$scope.getClassData(3,showWhich);
			}else if(type==3){	//企业分类
				dataArr3 = data;
				if(showWhich==1){		//行业分类
					$scope.showData(1,dataArr1);
				}else if(showWhich==2){	//品类分类
					$scope.showData(2,dataArr2,$("#secondClass").attr("classid"));
				}else if(showWhich==3){	//企业分类
					$scope.showData(3,dataArr3,$("#thirdClass").attr("classid"));
				}
			}
		}).error(function(data){
			alert(data);   
		})
	},
	
	//显示数据
	$scope.showData = function(type,dataArr,parentId){
		var html = '';
		var showDataArr = new Array();   //显示的数据集
		
		$.each(dataArr, function(index, OneObj){
			if(type==1 && parentId==null){
				showDataArr.push(OneObj);
				var id = type+"_"+OneObj.id;
				html += '<div class="col-sm-12 oneClass" style="padding:0">'
							+'<div class=" decorate decorateLeft" id="'+id+'" ng-click="chooseClass('+type+',\''+OneObj.id+'\')" >'+OneObj.name+'</div>'
							+'<div class="delete decorateRight outPadding" ng-click="updateClass('+type+',\''+OneObj.id+'\',\''+OneObj.name+'\')"><i class="fa fa-pencil-square-o"></i></div>'
						+'</div>';
			}else if((type==3 && OneObj.class_id==parentId)||(type==2 && OneObj.build_Id==parentId)){
				showDataArr.push(OneObj);
				var id = type+"_"+OneObj.id;
				html += '<div class="col-sm-12 oneClass fabric">'
							+'<img src='+OneObj.imgurl+' class="beffeact"/>'
							+'<div class="content decorateTop " id="'+id+'" ng-click="chooseClass('+type+',\''+OneObj.id+'\')" >'+OneObj.name+'</div>'
							+'<div class="delete decorateBotton "  ng-click="deleteClass('+type+',\''+OneObj.id+'\',\''+OneObj.name+'\',\''+OneObj.imgurl+'\')"><i class="fa fa-times"></i></div>'
							+'<div class="delete decorateBotton " ng-click="updateClass('+type+',\''+OneObj.id+'\',\''+OneObj.name+'\',\''+OneObj.imgurl+'\',\''+OneObj.appshow_imgurl+'\',\''+OneObj.rgb+'\')"><i class="fa fa-pencil-square-o"></i></div>'
						+'</div>';
			}
		});
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		if(type==1){		//行业分类
			$("#firstClass").html(html);
			$scope.chooseClass(type,showDataArr[0].id);
		}else if(type==2){	//品类分类
			$("#secondClass").html(html);
			$("#secondClass").attr("classid",parentId);
			if(showDataArr.length>0){
				$scope.chooseClass(type,showDataArr[0].id);
			}else{
				$("#secondClass").html("");
				$("#thirdClass").html("");
			}
		}else if(type==3){	//企业分类
			$("#thirdClass").html(html);
			$("#thirdClass").attr("classid",parentId);
			if(showDataArr.length>0){
				$scope.chooseClass(type,showDataArr[0].id);
			}else{
				$("#thirdClass").html("");
			}
		}
	}
	
	//修改分类
	$scope.updateClass = function(type,id,name,imgurl,showImage,rgb){
		$("#addClassifyType").val(type);
		$("#addClassifyType1").val("update");
		$("#addClassifyId").val(id);
		
		var html='';
		if(type==1){
			html='<label class="control-label col-sm-2" >行业分类</label>'
				 +'<div class="col-sm-8">'
					+'<input id="industryName" class="k-textbox" placeholder="行业名称"/>'
				+'</div>';
			$("#addClassifyImg").css("display","none");
			
			$scope.classSpecial_show = false;
		}else if(type==2){
			html='<label class="control-label col-sm-2" >品类分类</label>'
				 +'<div class="col-sm-8">'
					+'<input id="categoryName" class="k-textbox" placeholder="品类名称"/>'
				+'</div>';
			$("#addClassifyImg").css("display","block");
			
			$scope.classSpecial_show = true;
		}else if(type==3){
			html='<label class="control-label col-sm-2" >品牌分类</label>'
				 +'<div class="col-sm-8">'
					+'<input id="brandName" class="k-textbox" placeholder="品牌名称"/>'
				+'</div>';
			$("#addClassifyImg").css("display","block");
			
			$scope.classSpecial_show = false;
		}
		$("#addClassifyName").html($compile(html)($scope));
		
		$("#addClassify").kendoWindow({
			  position: { top:"30%",left:"30%"}
		});
		var dialog = $("#addClassify").data("kendoWindow");
		dialog.open();
		
		//赋值
		if(type==1){
			$("#industryName").val(name);
		}else if(type==2){
			$("#categoryName").val(name);
			publicService.showUploadImage("addClass_img","addClass_url",imgurl);
			publicService.showUploadImage("showClass_img","showClass_url",showImage);
			$("#classRGB").val(rgb);
		}else if(type==3){
			$("#brandName").val(name);
			publicService.showUploadImage("addClass_img","addClass_url",imgurl);
		}
	}
	//删除分类
	$scope.deleteClass = function(type,id,name,imgurl){
		if(confirm("是否确认删除")){
			if(type==2){
				$http({
				   url: path+"/server/removebuildclass/"+id,
				   method: 'GET',
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						window.location.reload();
					}else{
						alert("此品类的品牌下已有代理商，请勿删除！");
					}
				}).error(function(data){
					console.log(data);   
				})
			}else if(type==3){
				$http({
				   url: path+"/server/removebuildenterpise/"+id,
				   method: 'GET',
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						window.location.reload();
					}else{
						alert("此品牌下已有代理商，请勿删除！");
					}
				}).error(function(data){
					console.log(data);   
				})
			}
		}
	}
	//添加分类
	$scope.addClass = function(type){
		$("#addClassifyType").val(type);
		$("#addClassifyType1").val("add");
		var html='';
		if(type==1){
			html='<label class="control-label col-sm-2" >行业分类</label>'
				 +'<div class="col-sm-8">'
					+'<input id="industryName" class="k-textbox" placeholder="行业名称"/>'
				+'</div>';
			$("#addClassifyImg").css("display","none");
			
			$scope.classSpecial_show = false;
		}else if(type==2){
			html='<label class="control-label col-sm-2" >品类分类</label>'
				 +'<div class="col-sm-8">'
					+'<input id="categoryName" class="k-textbox" placeholder="品类名称"/>'
				+'</div>';
			$("#addClassifyImg").css("display","block");
			
			$scope.classSpecial_show = true;
		}else if(type==3){
			html='<label class="control-label col-sm-2" >品牌分类</label>'
				 +'<div class="col-sm-8">'
					+'<input id="brandName" class="k-textbox" placeholder="品牌名称"/>'
				+'</div>';
			$("#addClassifyImg").css("display","block");
			
			$scope.classSpecial_show = false;
		}
		$("#addClassifyName").html($compile(html)($scope));
		$("#addClassify").kendoWindow({
			  position: { top:"30%",left:"30%"}
		});
		var dialog = $("#addClassify").data("kendoWindow");
		dialog.open();
		
		publicService.removeUploadImage("addClass_img","addClass_url");
		publicService.removeUploadImage("showClass_img","showClass_url");
		$("#classRGB").val("");
	}
	$scope.yesAddClassify=function(){
		var type=$("#addClassifyType").val();
		var type1=$("#addClassifyType1").val();
		var url = '';
		var postData = new Object();
		if(type1=="update"){
			if(type==1){		//行业分类
				url = path+'/server/modifybuildmaterials';
				postData.id = $("#addClassifyId").val();
				postData.name = $("#industryName").val();
			}else if(type==2){	//品类分类
				url = path+'/server/modifybuildclass';
				postData.id = $("#addClassifyId").val();
				postData.name = $("#categoryName").val();
				postData.imgurl = $("#addClass_url").val();
				postData.appshow_imgurl = $("#showClass_url").val();	//示意图
				postData.rgb = $("#classRGB").val();					//色值
				postData.build_Id = $("#secondClass").attr("classid");
			}else if(type==3){	//企业分类
				url = path+'/server/modifybuildenterpise';
				postData.id = $("#addClassifyId").val();
				postData.name = $("#brandName").val();
				postData.imgurl = $("#addClass_url").val();
				postData.class_id = $("#thirdClass").attr("classid");
			}
		}else{
			if(type==1){		//行业分类
				url = path+'/server/addbuildmaterials';
				postData.name = $("#industryName").val();
			}else if(type==2){	//品类分类
				url = path+'/server/addbuildclass';
				postData.name = $("#categoryName").val();
				postData.imgurl = $("#addClass_url").val();
				postData.appshow_imgurl = $("#showClass_url").val();	//示意图
				postData.rgb = $("#classRGB").val();					//色值
				postData.build_Id = $("#secondClass").attr("classid");
			}else if(type==3){	//企业分类
				url = path+'/server/addbuildenterpise';
				postData.name = $("#brandName").val();
				postData.imgurl = $("#addClass_url").val();
				postData.class_id = $("#thirdClass").attr("classid");
			}
		}
		
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){  
			if(data.code==0){  //成功
				alert("成功");
				var dialog = $("#addClassify").data("kendoWindow");
				dialog.close();
				window.location.reload();
			}else if(data.code==1){  //失败
				alert("失败");
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//选择
	$scope.chooseClass = function(type,id){
		var divId = type+"_"+id;
		angular.element("#"+divId).parent("div").parent("div").children("div").removeClass("choose");
		angular.element("#"+divId).parent("div").addClass("choose");	
		
		if(type==1){		//行业分类
			$scope.showData(type+1,dataArr2,id);
		}else if(type==2){	//品类分类
			$scope.showData(type+1,dataArr3,id);
		}else if(type==3){	//企业分类
			
		}
	}
	
	
	publicService.uploadImage("addClass","addClass_img","addClass_url",limitParam1);
	publicService.uploadImage("showClass","showClass_img","showClass_url",limitParam2);
	
	$scope.getClassData(1,1);
	
	
	

});