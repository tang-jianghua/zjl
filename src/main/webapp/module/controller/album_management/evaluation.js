App.controller("evaluationManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	
	$scope.search1 = {};	//评价管理表单
	$scope.search2 = {};	//敏感词汇库表单
	$scope.evaluationDetails = {};	//评论详情
	$scope.word = {};		//敏感词库表单
	var wordInfo = null;	//敏感词信息
	
	
	//选择列表
	$scope.chooseList = function(type){
		for(var i=1;i<=2;i++){
			if(i==type){
				$("#list"+i).addClass("choose");
				$("#container"+i).show();
			}else{
				$("#list"+i).removeClass("choose");
				$("#container"+i).hide();
			}
		}
	}
	
	//评价列表
	$scope.mainGridOptions1 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryarticleevaluations",  
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
									album_title: $scope.search1.album_title,		//专辑名称
									article_title: $scope.search1.article_title,	//文章名称
									customer_name: $scope.search1.customer_name,	//用户昵称
									create_time: $("#evaluate_time").val(),			//评价时间
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
			columns : [
					{
						field : "article_title",         
						title : "文章标题",     
						width : "100px"
					},
					{
						field : "album_title",         
						title : "所属专辑",     
						width : "100px"
					},
					{
						field : "customer_name",         
						title : "用户昵称",     
						width : "100px"
					},
					{
						field : "create_time",         
						title : "评价时间",     
						width : "100px"    
					},
					{
						field : "content",         
						title : "评价内容",     
						width : "200px"    
					},
					{
						field : "reply_num",         
						title : "回复量",     
						width : "80px"    
					},
					{
						field : "state",         
						title : "状态",     
						width : "80px",
						values: [
					         { text: "违规", value: 0 },
					         { text: "正常", value: 1 }
					    ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							if(e.state==0){
								html += ('<div class="k-state-default operation splitButtonTwoc" ng-click="lookEvaluationDetails(\''+e.id+'\',true)">查看详情</div>'
										+'<div class="k-state-default operation splitButtonTwoc" ng-click="modifyEvaluationState(\''+e.id+'\',1,\''+e.id+'\',false)">恢复</div>');
							}else if(e.state==1){
								html += ('<div class="k-state-default operation splitButtonTwoc" ng-click="lookEvaluationDetails(\''+e.id+'\',true)">查看详情</div>'
										+'<div class="k-state-default operation splitButtonTwoc" ng-click="modifyEvaluationState(\''+e.id+'\',0,\''+e.id+'\',false)">违规屏蔽</div>');
							}
							
							return html;
						}
					}
			]
		};
	
	//敏感词列表
	$scope.mainGridOptions2 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querysensitivewords",
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
									name: $scope.search2.name,			//词库名
									content: $scope.search2.content,	//词库内容
									public_time: $("#publish_time").val(),	//发布时间
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
			columns : [
					{
						field : "name",         
						title : "词库名",     
						width : "80px"    
					},
					{
						field : "content",         
						title : "词库内容",     
						width : "200px"    
					},
					{
						field : "create_time",         
						title : "创建时间",     
						width : "100px"    
					},
					{
						field : "public_time",         
						title : "发布时间",     
						width : "100px"    
					},
					{
						field : "state",         
						title : "状态",     
						width : "80px",
						values: [
					         { text: "未发布", value: 0 },
					         { text: "已发布", value: 1 }
					    ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template:function(e){
							var html = '';
							
							if(e.state==1){
								html += ('<div class="k-state-default operation splitButtona" ng-click="modifyWord(\''+e.id+'\')">编辑</div>'
										+'<div class="k-state-default operation splitButtona" ng-click="deleteWord(\''+e.id+'\')">删除</div>'
										+'<div class="k-state-default operation splitButtona" ng-click="changeWord(\''+e.id+'\',0)">撤销</div>');
							}else if(e.state==0){
								html += ('<div class="k-state-default operation splitButtona" ng-click="modifyWord(\''+e.id+'\')">编辑</div>'
										+'<div class="k-state-default operation splitButtona" ng-click="deleteWord(\''+e.id+'\')">删除</div>'
										+'<div class="k-state-default operation splitButtona" ng-click="changeWord(\''+e.id+'\',1)">发布</div>');
							}
							
							return html;
						}
					}
			]
		};
	
	//查询
	$scope.search = function(type){
		if(type==1){
			$scope.grid1.dataSource.page(1);
		}else if(type==2){
			$scope.grid2.dataSource.page(1);
		}
	}

	//评价状态编辑
	$scope.modifyEvaluationState = function(id,state,parentId,ifGet){
		if(confirm("确认更改？")){
			var postData = {
					id: id,
					state: state
			};
			
			$http({
				   url: path+'/server/modifyarticleevaluation',
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.grid1.dataSource.page(1);
					
					if(ifGet && parentId){
						$scope.lookEvaluationDetails(parentId,false);
					}
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//查看评价详情
	$scope.lookEvaluationDetails = function(id,ifOpen){
		$http({
			   url: path+'/server/queryarticleevaluation/'+id,
			   method: 'POST'
		}).success(function(data){
			if(data.code==0){  //成功
				var evaluationDetails = data.result;
				
				$scope.evaluationDetails = {};
				$scope.evaluationDetails.album_title = evaluationDetails.album_title;		//专辑名称
				$scope.evaluationDetails.article_title = evaluationDetails.article_title;	//文章名称
				
				$scope.evaluation_one = evaluationDetails.articleEvaluationModels[0];
				evaluationDetails.articleEvaluationModels.shift();
				
				$scope.evaluation_two = evaluationDetails.articleEvaluationModels;
				
				if(ifOpen){
					$scope.evaluationDetailsWindow.center().open();   //打开弹框
				}
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//新增词库
	$scope.addWord = function(){
		$scope.word = {};
		$scope.addWordWindow.center().open();   //打开弹框	
	}
	
	//添加新的词库
	$scope.addNewWord = function(){
		if(!$scope.word.name){
			alert('请填写【词库名称】！');
			return;
		}
		if(!$scope.word.content){
			alert('请填写【词库内容】！');
			return;
		}
		
		var url = '';
		if($scope.word.id){
			url = path+'/server/modifysensitivewords';
		}else{
			url = path+'/server/addsensitivewords';
		}

		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.word)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.addWordWindow.close();   //关闭
				$scope.grid2.dataSource.page(1);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//编辑词库
	$scope.modifyWord = function(id){
		$http({
			   url: path+'/server/querysensitiveword/'+id,
			   method: 'POST'
		}).success(function(data){
			if(data.code==0){  //成功
				wordInfo = data.result;
				
				$scope.word.id = wordInfo.id;
				$scope.word.name = wordInfo.name;
				$scope.word.content = wordInfo.content;
				$scope.addWordWindow.center().open();   //打开弹框	
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//删除词库
	$scope.deleteWord = function(id){
		if(confirm('确认删除该条词库？')){
			$http({
				   url: path+'/server/deletesensitiveword/'+id,
				   method: 'POST'
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.grid2.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//更改词库状态（发布，撤销）
	$scope.changeWord = function(id,state){
		if(confirm('确认更改？')){
			var postData = {
					id: id,
					state: state
			};
			
			$http({
				   url: path+'/server/publishsensitivewords',
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.grid2.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	
	$scope.chooseList(1);   //选择列表
	publicService.initDate("evaluate_time");
	publicService.initDate("publish_time");

});