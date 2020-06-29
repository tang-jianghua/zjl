App.controller("problemContentCtrl",function($scope, $rootScope, $location, $http, $compile, path) {
	
	$scope.form = {};
	var user_type = 1;//1代表买家端问题，2代表卖家端问题
	var problemList = null;		//问题列表
	var showProblemClass = 0;   //显示的问题分类
	
	var addInput_index = 0;   //增加分类下标
	var focusInput = '';      //获得焦点的文本框
	var editor = UE.getEditor('editor');
	if(editor.container!=undefined){
		editor.destroy();
		editor = UE.getEditor('editor');
	}
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryquestion",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									question_type_id:showProblemClass,
									type:user_type
								}
							};
							console.log('查询参数',parameter);
							return kendo.stringify(parameter);
						}
					},
				},
				pageSize : 10,
				serverPaging : true,
				serverSorting: true,
				schema : {
					data : function(d) {
						return d.data;
					},
					total : function(d) {
						return d.total; //总条数
					}
				},
			},
			pageable : {  //分页
				pageSizes: [10, 20, 50, 100],  //每页显示记录数
			  },   
			sortable: {   //排序
				//allowUnsort: false,  //允许无序
			    mode: "multiple"     //排序模式：single，multiple
			  },
			/*editable: {   //编辑
			    mode: "inline"   //整行编辑
			  },*/
			editable: false,  //true：单个编辑；popup：弹出新页面编辑
			dataBound: function () {   //序号
		        var rows = this.items();
		        var page = this.pager.page() - 1;
		        var pagesize = this.pager.pageSize();
		        $(rows).each(function () {
		            var index = $(this).index() + 1 + page * pagesize;
		            var rowLabel = $(this).find(".row-number");
		            $(rowLabel).html(index);
		        });
		    },
			columns : [
					{    
						title : "序号",
						field : "id", 
						width : "80px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{
						field : "title",         
						title : "问题列表",     
						width : "400px"    
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div  value='查看答案' class='operation k-state-default splitButtonTwob ' ng-click='lookAnswer(\"#: id #\")' />查看答案</div>"
							      +"<div  value='删除' class='operation k-state-default splitButtonTwo' ng-click='deleteProblem(\"#: id #\")' />删除</div>"
					}
			]
		};
	
	//查看答案
	$scope.lookAnswer = function(id){
		alert("查看答案！");
	}
	
	//删除
	$scope.dataDelete = function(id){
		alert("删除！");
	}
	
	//新增问题
	$scope.addProblem = function(id){
		$scope.form = {};
		$scope.form.question_type_id = showProblemClass;
		editor.setContent("");
		$scope.addProblemWindow.center().open();   //打开弹框
	}
	
	//添加新的问题
	$scope.addNewProblem = function(id){
		$scope.form.details = editor.getContent;   //内容
		
		$http({
			   url: path+'/server/aa',
			   method: 'POST',
			   data: angular.toJson($scope.form)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.addProblemWindow.close();   //关闭
				$scope.grid.dataSource.page(1);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
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
		    },
	        error: function(e){
	        	//alert('失败！');
	        },
	        remove: function(e){
	        	//alert('移除！');
	        },
	      
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
	
	//移除图片
	$scope.removeUploadImg = function(imgId,imgUrlId){
		$("#"+imgId).removeAttr("style");
		$('#'+imgUrlId).val("");
	}
	
	
	/*******************************图文编辑器(end)*******************************/
	
	//获取问题分类数据
	$scope.getProblemClassifyData = function(){
		var postData = new Object();
		postData.type = user_type;
		$http({
			   url: path+'/server/queryquestiontype',
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){  
			//console.log("问题分类数据：",data);
			problemList = data;
			
			$scope.showData(problemList);
		}).error(function(data){
			alert(data);   
		})
	}
	
	//显示数据
	$scope.showData = function(dataArr){
		var html = '';
		
		$.each(dataArr, function(index, OneObj){
			var id = 'problem_'+OneObj.id;
			html += ('<div class="col-sm-12 oneClass">'
						+'<div class="content" id="'+id+'" ng-click="chooseClass(\''+OneObj.id+'\')" ng-dblclick="editClass(\''+OneObj.id+'\')">'+OneObj.question_type+'</div>'
						+'<div class="delete" ng-click="deleteClassData(\''+OneObj.id+'\')">×</div>'
					+'</div>');
		});
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#problemClassify").html(html);
		if(dataArr.length>0){
			$scope.chooseClass(dataArr[0].id);
		}
	}
	
	//选择
	$scope.chooseClass = function(id){
		//刷新问题列表
		showProblemClass = id;
		$('.oneClass ').removeClass('active');
		$('#problem_'+id).parent().addClass('active');
		$scope.grid.dataSource.page(1);
	}

	//添加分类
	$scope.addClass = function(){
		addInput_index ++;
		var inputId = "add_"+addInput_index;
		
		var html = '';
		html += ('<div class="col-sm-12 oneClass">'
					+'<input type="text" class="k-textbox" id="'+inputId+'" ng-focus="getFocusInput(\''+inputId+'\')" ng-blur="addClassData(\''+inputId+'\')"/>'
				+'</div>');
		
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		$("#problemClassify").append(html);
	}
	
	//编辑分类
	$scope.editClass = function(id){
		var divId = "problem_"+id;
		var text = angular.element("#"+divId).text();

		addInput_index ++;
		var inputId = "add_"+addInput_index;
		var html = '<input type="text" class="k-textbox" id="'+inputId+'" value="'+text+'" ng-focus="getFocusInput(\''+inputId+'\')" ng-blur="editClassData(\''+inputId+'\',\''+id+'\')"/>';
		html = $compile(html)($scope);   //angularJs代码需要动态编译
		var text = angular.element("#"+divId).parent("div").html(html);	
		console.log("1");
	}
	
	//获得焦点的文本框
	$scope.getFocusInput = function(inputId){
		focusInput = inputId;	
	}
	
	//添加分类数据
	$scope.addClassData = function(inputId){
		var text = angular.element("#"+inputId).val();
		
		if(!text){
			alert("请添加分类数据！");
			return;
		}

		var postData = new Object();
		postData.question_type = text;
		postData.type = user_type;
		$http({
			   url: path+'/server/createquestiontype',
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){  
			if(data.code==0){  //成功
				$scope.getProblemClassifyData();
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//编辑分类数据
	$scope.editClassData = function(inputId,id){
		var text = angular.element("#"+inputId).val();
		
		if(!text){
			alert("数据不能为空！");
			return;
		}

		var postData = new Object();
		postData.id = id;
		postData.question_type = text;

		$http({
			   url: path+'/server/modifyquestiontype',
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){  
			if(data.code==0){  //成功
				$scope.getProblemClassifyData();
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//删除分类
	$scope.deleteClassData = function(id){
		if(confirm("确认删除？")){
			$http({
				   url: path+'/server/removequestiontype/'+id,
				   method: 'GET'
			}).success(function(data){  
				if(data.code==0){  //成功
					$scope.getProblemClassifyData();
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//添加新的问题，编辑
	$scope.addNewProblem = function(){
		var url = '';
		if($scope.form.id){  //编辑
			url = path+'/server/modifyquestion';
		}else{  //添加
			url = path+'/server/createquestion';
		}

		$scope.form.details = editor.getContent();
		$scope.form.type = user_type;
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.form),
		}).success(function(data){  
			if(data.code==0){  //成功
				$scope.addProblemWindow.close();
				$scope.grid.dataSource.page(1);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//查看答案
	$scope.lookAnswer = function(id){
		$http({
			   url: path+'/server/getquestionbyid/'+id,
			   method: 'GET'
		}).success(function(data){  
			if(data.code==0){
				var problemObj = data.result;
				
				$scope.form.question_type_id = problemObj.question_type_id;
				$scope.form.id = problemObj.id;
				$scope.form.title = problemObj.title;
				editor.setContent(problemObj.details);
				$scope.addProblemWindow.center().open();   //打开弹框
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//删除问题
	$scope.deleteProblem = function(id){
		if(confirm("您确定删除该问题吗?")){
			$http({
				   url: path+'/server/removequestion/'+id,
				   method: 'GET'
			}).success(function(data){  
				if(data.code==0){  //成功
					$scope.grid.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}else{
			
		}
	}
	
	 //切换到买家端问题列表
	 $scope.changeToBuyLayer=function(obj){
		 user_type = 1;
		 $scope.getProblemClassifyData();  //获取问题分类数据
		 $scope.grid.dataSource.page(1);
		 $scope.chooseProblem(1);
	 }
	 //切换到卖家端问题列表
	 $scope.changeToSailLayer=function(obj){
		 user_type = 2;
		 $scope.getProblemClassifyData();  //获取问题分类数据
		 $scope.grid.dataSource.page(1);
		 $scope.chooseProblem(2);
	 }
	 
	 //问题选择
	 $scope.chooseProblem = function(type){
		 for(var i=1; i<=2;i++){
			 if(i==type){
				 $("#problemType_"+i).addClass("choose");
			 }else{
				 $("#problemType_"+i).removeClass("choose");
			 }
		 } 
	 }
	 
	 
	$scope.getProblemClassifyData();  //获取问题分类数据
	
	$scope.Upload("localPicture","localPicture_img","localPicture_url");		//上传本地图片
	
	
	

});