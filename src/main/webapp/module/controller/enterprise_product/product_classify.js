App.controller("productClassifyCtrl1",function($scope, $rootScope, $location, $http, $compile, path) {
	
	var dataArr1 = null;		//行业分类数据
	var dataArr2 = null;		//品类分类数据
	var dataArr3 = null;		//企业名称数据
	
	var addInput_index = 0;   //增加分类下标
	var focusInput = '';      //获得焦点的文本框
	
	
	//获取三种分类数据
	$scope.getClassData = function(type,showWhich){
		var url = '';
		if(type==1){		//行业分类
			url = path+'/server/buildmaterialslist';
		}else if(type==2){	//品类分类
			url = path+'/server/buildclassentities ';
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
	}
	
	//显示数据
	$scope.showData = function(type,dataArr,parentId){
		var html = '';
		var showDataArr = new Array();   //显示的数据集
		
		$.each(dataArr, function(index, OneObj){
			if((type==1 && parentId==null) || (type==2 && OneObj.build_Id==parentId) || (type==3 && OneObj.class_id==parentId)){
				showDataArr.push(OneObj);
				var id = type+"_"+OneObj.id;
				html += ('<div class="col-sm-12 oneClass">'
							+'<div class="content col-sm-8" id="'+id+'" ng-click="chooseClass('+type+',\''+OneObj.id+'\')" ng-dblclick="editClass('+type+',\''+OneObj.id+'\')">'+OneObj.name+'</div>'
							+'<div class="delete col-sm-4" ng-click="deleteClass('+type+',\''+OneObj.id+'\')">×</div>'
						+'</div>');
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
	
	
	//添加分类
	$scope.addClass = function(type){
		addInput_index ++;
		var inputId = "add_"+addInput_index;
		
		var html = '';
		html += ('<div class="col-sm-12 oneClass">'
				+'<input type="text" class="k-textbox" id="'+inputId+'" ng-focus="getFocusInput(\''+inputId+'\')" ng-blur="addClassData('+type+',\''+inputId+'\')"/>'
			+'</div>');
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		if(type==1){		//行业分类
			$("#firstClass").append(html);
		}else if(type==2){	//品类分类
			$("#secondClass").append(html);
		}else if(type==3){	//企业分类
			$("#thirdClass").append(html);
		}
	}
	
	//编辑分类
	$scope.editClass = function(type,id){
		var divId = type+"_"+id;
		var text = angular.element("#"+divId).text();

		addInput_index ++;
		var inputId = "add_"+addInput_index;
		var html = '<input type="text" class="k-textbox" id="'+inputId+'" value="'+text+'" ng-focus="getFocusInput(\''+inputId+'\')" ng-blur="editClassData('+type+',\''+inputId+'\',\''+id+'\')"/>';
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		var text = angular.element("#"+divId).parent("div").html(html);	
	}
	
	//获得焦点的文本框
	$scope.getFocusInput = function(inputId){
		focusInput = inputId;	
	}
	
	//添加分类数据
	$scope.addClassData = function(type,inputId){
		var text = angular.element("#"+inputId).val();
		
		if(!text){
			alert("请添加分类数据！");
			return;
		}
		
		var url = '';
		var postData = new Object();
		if(type==1){		//行业分类
			url = path+'/server/addbuildmaterials';
			postData.name = text;
		}else if(type==2){	//品类分类
			url = path+'/server/addbuildclass';
			postData.name = text;
			postData.build_Id = $("#secondClass").attr("classid");
		}else if(type==3){	//企业分类
			url = path+'/server/addbuildenterpise';
			postData.name = text;
			postData.class_id = $("#thirdClass").attr("classid");
		}

		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){  
			if(data.code==0){  //成功
				if(type==1){		//行业分类
					$scope.getClassData(1,1);
				}else if(type==2){	//品类分类
					$scope.getClassData(2,2);
				}else if(type==3){	//企业分类
					$scope.getClassData(3,3);
				}
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//编辑分类数据
	$scope.editClassData = function(type,inputId,id){
		var text = angular.element("#"+inputId).val();
		
		if(!text){
			alert("数据不能为空！");
			return;
		}
		
		var url = '';
		var postData = new Object();
		if(type==1){		//行业分类
			url = path+'/server/modifybuildmaterials';
			postData.id = id;
			postData.name = text;
		}else if(type==2){	//品类分类
			url = path+'/server/modifybuildclass';
			postData.id = id;
			postData.name = text;
			postData.build_Id = $("#secondClass").attr("classid");
		}else if(type==3){	//企业分类
			url = path+'/server/modifybuildclass';
			postData.id = id;
			postData.name = text;
			postData.class_id = $("#thirdClass").attr("classid");
		}

		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){  
			if(data.code==0){  //成功
				if(type==1){		//行业分类
					$scope.getClassData(1,1);
				}else if(type==2){	//品类分类
					$scope.getClassData(2,2);
				}else if(type==3){	//企业分类
					$scope.getClassData(3,3);
				}
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//删除分类
	$scope.deleteClass = function(type,id){
		alert("删除操作暂不支持！");
	}
	
	
	
	$scope.getClassData(1,1);
	
	
	

});