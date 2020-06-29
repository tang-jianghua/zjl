App.controller("roleJurisdiction",function($scope, $rootScope, $location, $http, $compile, path){
	
	$scope.treeNodeId="";
	
	var curMenu = null, zTree_Menu = null; 
	var setting = {
		view: {
			showLine: false,
			showIcon: false,
			selectedMulti: false,
			dblClickExpand: false,
			addDiyDom : addDiyDom
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
		},
		data: {
			keep: {
				parent:true,
				leaf:true
			},
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parent_id",
				rootPId: 0
			}
		},
		callback: {
			beforeClick: beforeClick,
			beforeRename: zTreeBeforeRename,
			onClick: zTreeOnClick
		}
	};

	var newCount = 1;
	//添加部门
	$scope.addSection=function(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		isParent = $("#treeDemo").data.isParent,
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		console.log($("#treeDemo").data);
		
		treeNode = zTree.addNodes(null, {id:"", pId:0, isParent:true, name:"部门名称" + (newCount++)});
		zTree.editName(treeNode[0]);
		
		if (treeNode) {
			zTree.editName(treeNode[newCount]);
		} else {
			alert("叶子节点被锁定，无法增加子节点");
		}
	}
	function addDiyDom(treeId, treeNode) {
		var switchObj = $("#" + treeNode.tId + "_span");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		$("#" + treeNode.tId + "_span").bind("dblclick" , function(){
			zTree.editName(treeNode);
		});
		var html="<i class='fa fa-times' id='popover_" +treeNode.id+ "_i1'></i>" +
				"<i class='fa fa-pencil-square-o' id='popover_" +treeNode.id+ "_i2'></i>"+
				"<i class='fa fa-plus-square' id='popover_" +treeNode.id+ "_i3'></i>";
		if(treeNode.level == 0){
			switchObj.after(html);
		}else{
			switchObj.after("<i class='fa fa-times' title=" +treeNode.id+ " id='popover_" +treeNode.id+ "_i1'></i>" +
					"<i class='fa fa-pencil-square-o' title=" +treeNode.id+ " id='popover_" +treeNode.id+ "_i5'></i>");
		}
		//删除部门和删除岗位
		$("#popover_" +treeNode.id+ "_i1").bind("click", function(){
			console.log("删除部门");
			if(confirm("是否删除 " + treeNode.name + "？")){
				$http({
				   url: path+"/server/removedep/"+treeNode.id,
				   method: 'GET',
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						alert("删除成功");
						loadTree();//加载树
					}else{
						alert("不可删除");
					}
				}).error(function(data){
					console.log(data);   
				})
			}
		});
		//编辑部门
		$("#popover_" +treeNode.id+ "_i2").bind("click", function(){
			console.log("编辑部门");
			zTree.editName(treeNode);
		});
		//添加岗位
		$("#popover_" +treeNode.id+ "_i3").bind("click", function(){
			treeNode.isParent=true;
			var newNodes = zTree.addNodes(treeNode, {id:"", pId:treeNode.id, name:"岗位名称" + (newCount++)});
			if(newNodes){
				zTree.editName(newNodes[0]);
			}
		});
		//编辑岗位
		$("#popover_" +treeNode.id+ "_i5").bind("click", function(){
			console.log("编辑岗位");
			zTree.editName(treeNode);
		});
		
	}
	//用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
	function zTreeBeforeRename(treeId, treeNode, newName, isCancel){
		//添加节点
		if(treeNode.id==""){
			//添加部门
			if(treeNode.parent_id==0){
				$http({
				   url: path+"/server/createdep",
				   method: 'POST',
				   data:angular.toJson({name:newName,parent_id:treeNode.pId}),
				}).success(function(data){
					if(data.code==0){
						alert("成功");
						loadTree();//加载树
					}else{
						alert("失败");
					}
				}).error(function(data){
					console.log(data);   
				})
			}else{//添加岗位
				$http({
				   url: path+"/server/createposition",
				   method: 'POST',
				   data:angular.toJson({name:newName,parent_id:treeNode.pId}),
				}).success(function(data){
					if(data.code==0){
						alert("成功");
						loadTree();//加载树
					}else{
						alert("失败");
					}
				}).error(function(data){
					console.log(data);   
				})
			}
		}else{//编辑节点
			//编辑部门名称
			if(treeNode.parent_id==0){
				$http({
					url: path+"/server/modifydep",
					method: 'POST',
					data:angular.toJson({id:treeNode.id,name:newName,parent_id:treeNode.pId,is_position:1}),
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						alert("成功");
					}else{
						alert("失败");
					}
				}).error(function(data){
					console.log(data);   
				})
			}else{//编辑岗位名称
				$http({
					url: path+"/server/modifydep",
					method: 'POST',
					data:angular.toJson({id:treeNode.id,name:newName,parent_id:treeNode.pId,is_position:2}),
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						alert("成功");
					}else{
						alert("失败");
					}
				}).error(function(data){
					console.log(data);   
				})
			}
		}
	}
	//单击节点事件
	function zTreeOnClick(event, treeId, treeNode){
		$("#addRole").css("display","block");
		$scope.treeNodeId=treeNode.id;
		//$scope.loadKendoGrid();
		$scope.grid.dataSource.page(1);
	}
	
	function beforeClick(treeId, treeNode) {
		$scope.treeNodeId=null;
		$("#addRole").css("display","none");
		if (treeNode.level == 0 ) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			return false;
		}
		return true;
	}
	//初始化加载数据
	function loadTree(){ 
		var treeObj = $("#treeDemo");
		$http({
			   url: path+"/server/querydeporpositionList",
			   method: 'POST',
		}).success(function(data){
			console.log(data);
			$.fn.zTree.init(treeObj, setting, data);
			zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
		}).error(function(data){
			console.log(data);   
		})
	}
	//首次加载树
	$(document).ready(function(){
		loadTree();
	});
	/*====================================================================================================*/
	/*$scope.loadKendoGrid=function(){
		$scope.grid=$("#RoleGridOptions").kendoGrid({
			dataSource:{
				transport: {
					read : {
						url : path+"/server/queryrole",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
	                parameterMap: function(options, operation) {
	                    if (operation !== "read" && options.models) {
	                        return kendo.stringify(options.models);
	                    }
	                    if (operation == "read") {
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									position_id:$scope.treeNodeId
								}
							};
							return kendo.stringify(parameter);
						}
	                },
	                requestEnd:function(e){
	                }
	            },
	            batch: true,
	            pageSize: 10,
	            serverPaging : true,
	            schema: {
	                model: {
	                    id: "id",
	                    fields: {
	                    	id:{ editable: false, nullable: true },
	                        role_name: {editable: true, validation: { required: true } }
	                    }
	                },
	                data : function(d) {
						return d.data;
					},
					total : function(d) {
						return d.total; //总条数
					}
	            }
			},
		    pageable: true,
		    toolbar: [{name:"create",click:function(e){
		    	alert(11111);
		    }}],
		    toolbar: "<button style='display:none' id='addRole' ng-click='addRole()'>+新建角色</button>",
		    columns: [
			        { field: "role_name", title: "角色名称", width: 120 },
			        { 
						title : "操作",
						width: "200px",
						template:function(e){
							template_html='<div class="operation k-state-default" ng-click="deleteqx(\''+e.id+'\')">删除</div>'+
							'<div class="operation k-state-default" ng-click="update(\''+e.id+'\')">设置权限</div>';
				    		return template_html;
						}
			        },
		    ],
		     editable:true,
		     save: function(e) {
			   if (e.values.role_name) {
				   //编辑
				   if(e.model.id){
				     //值改变了
				     if (e.values.role_name != e.model.role_name) {
				    	 $http({
						   url: path+"/server/modifyrole",
						   method: 'POST',
						   data:angular.toJson({id:e.model.id,position_id:$scope.treeNodeId,roles:{role_name:e.values.role_name}}),
						}).success(function(data){
							console.log(data);
						}).error(function(data){
							console.log(data);   
						})
				     }
				   }else{//添加
					   if($scope.treeNodeId!=null){
						   $http({
							   url: path+"/server/createrole",
							   method: 'POST',
							   data:angular.toJson({position_id:$scope.treeNodeId,roles:{role_name:e.values.role_name}}),
							}).success(function(data){
								console.log(data);
								$scope.grid.dataSource.page(1);
							}).error(function(data){
								console.log(data);   
							})
					   }else{
						   alert("请选择岗位");
					   }
				   }
			   }
			 }		

		}).data("kendoGrid");
	}*/
	
	$scope.RoleGridOptions={
    		dataSource:{
    			transport: {
    				read : {
    					url : path+"/server/queryrole",
    					type : 'POST',
    					dataType : "json",
    					contentType : "application/json"
    				},
                    parameterMap: function(options, operation) {
                        if (operation !== "read" && options.models) {
                            return kendo.stringify(options.models);
                        }
                        if (operation == "read") {
    						var parameter = {
    							page : options.page, //当前页
    							pageSize : options.pageSize,
    							param:{
    								position_id:$scope.treeNodeId
								}
    						};
    						return kendo.stringify(parameter);
    					}
                    },
                    requestEnd:function(e){
                    }
                },
                batch: true,
                pageSize: 10,
                serverPaging : true,
                schema: {
                    model: {
                        id: "id",
                        fields: {
                        	id:{ editable: false, nullable: true },
                            role_name: {editable: true, validation: { required: true } }
                        }
                    },
                    data : function(d) {
    					return d.data;
    				},
    				total : function(d) {
    					return d.total; //总条数
    				}
                }
    		},
		pageable : {  //分页
			pageSizes: [10, 20, 50, 100],  //每页显示记录数
		  },
	    toolbar: [{name:"create",click:function(e){
	    	alert(11111);
	    }}],
	    toolbar: "<button style='display:none' id='addRole' ng-click='addRole()'>+新建角色</button>",
	    columns: [
		        { field: "role_name", title: "角色名称", width: 120 },
		        { 
					title : "操作",
					width: "200px",
					template:function(e){
						template_html='<div class="operation k-state-default" ng-click="deleteqx(\''+e.id+'\')">删除</div>'+
						'<div class="operation k-state-default" ng-click="update(\''+e.id+'\')">设置权限</div>';
			    		return template_html;
					}
		        },
	    ],
	     editable:true,
	     save: function(e) {
		   if (e.values.role_name) {
			   //编辑
			   if(e.model.id){
			     //值改变了
			     if (e.values.role_name != e.model.role_name) {
			    	 $http({
					   url: path+"/server/modifyrole",
					   method: 'POST',
					   data:angular.toJson({id:e.model.id,position_id:$scope.treeNodeId,roles:{role_name:e.values.role_name}}),
					}).success(function(data){
						console.log(data);
					}).error(function(data){
						console.log(data);   
					})
			     }
			   }else{//添加
				   if($scope.treeNodeId!=null){
					   $http({
						   url: path+"/server/createrole",
						   method: 'POST',
						   data:angular.toJson({position_id:$scope.treeNodeId,roles:{role_name:e.values.role_name}}),
						}).success(function(data){
							console.log(data);
							$scope.grid.dataSource.page(1);
						}).error(function(data){
							console.log(data);   
						})
				   }else{
					   alert("请选择岗位");
				   }
			   }
		   }
		 }		
   }
	//新建角色
	$scope.addRole=function(){
		var grid = $("#RoleGridOptions").data("kendoGrid");
		grid.addRow();
	}
	//删除角色
	$scope.deleteqx=function(id){
		if(confirm("是否确认删除？")){
			$http({
			   url: path+"/server/removerole/"+id,
			   method: 'GET',
			  // data:angular.toJson({id:id}),
			}).success(function(data){
				console.log(data);
				if(data.code==0){/*RoleGridOptions*/
					alert("删除成功");
					$scope.grid.dataSource.page(1);
				}else{
					alert("此角色不可删除");
				}
			}).error(function(data){
				console.log(data);   
			})
		}
	}
	//修改权限
	$scope.update=function(id){
		console.log(id);
		var params = {method:"check",id:id};
    	$location.path("/updateJurisdiction/"+angular.toJson(params));
	}
})
