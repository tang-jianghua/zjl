App.controller("entProductLineCtrl",function($scope, $rootScope, $location, $http, $compile, path) {

	$scope.mainGridOptions = {
			dataSource:{
				transport:{
					read:{
						url : path+"/server/ent/classifyseries",
						type : 'GET',
						dataType : "json",
						contentType : "application/json"
					},
					update: {
	                    url:  path+"/server/ent/modifyclassify",
	                    type : 'POST',
						dataType : "json",
						contentType : "application/json"
	                },
					 parameterMap: function(options, operation) {
						 if (operation !== "read" && options.models) {
							 return  kendo.stringify(options.models);
						 }
		             }
				
				},
				batch: true,
				schema : {
					model: {
	                    id: "id",
	                    name: "name",
	                    parentId: "parent_id",
	                    fields: {
	                        s_id: { type: "number", editable: false, nullable: false },
	                        parent_id: {type: "number", editable: false, nullable: true,},
	                        name: { validation: { required: true } },
	                    }
	                },
	                expanded: true
				}
				
			},
			editable: true,  //true：单个编辑；popup：弹出新页面编辑    
			columns : [
						{         
							title : "分类名称",    
							field : "name",
							width:"320px",
							  attributes: {
					    	      "class": "table-cell",
					    	      style: "text-align:left;" +
					    	      		"padding-left:20px"
					    	    }
						},
						{         
							title : "添加子节点", 
							width:"105px",
							template:function(e){
								if(e.parent_id==null||e.parent_id==""){
									var template_html = '<div ng-click="addChildNode(\''+e.uid+'\')" class="operation">添加</div>'
									return template_html;
								}
							}
						},
						/*{    
							title : "移动",
							width:"60px",
							template:function(e){
								console.log(e);
								if(e.parent_id!=null&&e.parent_id!=""&&e.parent_id!=undefined){
									var template_html='<div class="operation  getLine splitButtonLeft" ng-click="moveUp(\''+e.id+'\',\''+e.parent_id+'\' )">向上</div>&nbsp;<div class="operation getLine splitButtonRight" ng-click="moveDowm(\''+e.id+'\',\''+e.parent_id+'\')">向下</div>'
									return template_html;
								}
							}
						},*/
						{   title: "操作", 
							width:"105px",
							template:function(e){
								var template_html = '<div id=\''+e.uid+'\' class="operation k-state-default " ng-click="edit(\''+e.uid+'\')" >编辑</div>'
								return template_html;
							}
							
						}
						
                        
						
				],
				 edit: function(e) {
			        console.log(e);
			      }
			
	};
	//添加大分类
	 $scope.add=function(){
    	//var row = $scope.dep_tree_list.select();
		var data =  $scope.dep_tree_list.dataItem($scope.dep_tree_list.table);
		 console.log(data);
		 $scope.dep_tree_list.addRow(data);
	    	
	   }
	 //保存分类
	 $scope.saveChanges=function(e){
		 console.log($scope.dep_tree_list.editor.model);
		 var model=$scope.dep_tree_list.editor.model;
		 var data=null;
	 	var postUrl = '';
		if(model.parent_id!=null&&model.parent_id!=""&&model.parent_id!=undefined){
			postUrl = path+'/server/ent/createseries';
			data={classify_id:model.parent_id,name:model.name};
		}else{
			postUrl = path+'/server/ent/createclassify';
			data={name:model.name};
		}
		 $http({
			   url: postUrl,
			   method: 'POST',   
			   data: angular.toJson(data),  
		}).success(function(data){
			if(data.code==0){  //成功
				alert(data.message);
				window.location.reload();   //页面刷新
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
		    
	 }
	 //添加子节点
	 $scope.addChildNode=function(obj){
		 var row=$("#"+obj);
		 $scope.dep_tree_list.addRow(row);
	 }
	//编辑产品分类
	$scope.edit=function(obj){
		var row=$("#"+obj);
    	$scope.dep_tree_list.editRow(row);
    	var html='<div class=" k-state-default operation splitButtonTwoc" ng-click="save()">确定</div><div class="operation k-state-default splitButtonTwoc" ng-click="cancel()">取消</div>';
    	html = $compile(html)($scope);
    	$("#"+obj).parent().html(html);
	}
	//确定修改
	$scope.save=function(){
		//console.log($scope.dep_tree_list.editor.model);
		var model=$scope.dep_tree_list.editor.model;
		var data={id:model.id,name:model.name};
		$http({
			   url: path+"/server/ent/modifyclassify",
			   method: 'POST',   
			   data: angular.toJson(data),  
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
	//取消编辑
	$scope.cancel=function(e){
		$scope.dep_tree_list.refresh();
	}
	$scope.tree_data_bound=function(e){
		console.log($scope.dep_tree_list.dataSource);
		var dataItem = $scope.dep_tree_list.dataSource.at(0);
    	var row = $scope.dep_tree_list.content.find("tr[data-uid="+ dataItem.uid+"]")
    	$scope.dep_tree_list.expand(row);
    }
});
