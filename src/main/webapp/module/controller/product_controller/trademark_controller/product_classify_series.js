App.controller("productClassifySeries",function($scope, $rootScope, $location, $http, $compile, path){
	
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
			drag: {
				prev: true,
				next: true,
				inner: false
			}
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
			onClick: zTreeOnClick,
			beforeDrop: beforeDrop
		}
	};
	function beforeDrop(treeId, treeNodes, targetNode, moveType){
		if(treeNodes[0].level!=0){
			var treeNodesChildren=treeNodes[0].getPath()[0].children;
			for (var i = 0; i < treeNodesChildren.length; i++) {
				$http({
					   url: path+"/server/brand/modifyseries",
					   method: 'POST',   
					   data: angular.toJson({id:treeNodesChildren[i].id,sort:i}),  
		   		}).success(function(data){
		   		}).error(function(data){
		   			alert(data);   
		   		})
			}
		}else{
			var ztree=$.fn.zTree.getZTreeObj("treeDemo"),
			nodes=ztree.getNodes();
			for (var l = 0; l < nodes.length; l++) {
				$http({
					   url: path+"/server/brand/modifyclassify",
					   method: 'POST',   
					   data: angular.toJson({id:nodes[l].id,sort:l}),  
		   		}).success(function(data){
		   		}).error(function(data){
		   			alert(data);   
		   		})
			}
		}
	}
	var newCount = 1;
	//添加部门
	$scope.addSection=function(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		isParent = $("#treeDemo").data.isParent,
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		console.log($("#treeDemo").data);
		if (!treeNode) {
			treeNode = zTree.addNodes(null, {id:"", pId:0, isParent:true, name:"未命名" + (newCount++)});
			zTree.editName(treeNode[0]);
		}else{
			treeNode = zTree.addNodes(null, {id:"", pId:0, isParent:true, name:"未命名" + (newCount++)});
			zTree.editName(treeNode[0]);
		} 
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
			console.log(treeNode);
			if(confirm("是否删除 " + treeNode.name + "？")){
				if(treeNode.parent_id==0){
					$http({
					   url: path+"/server/brand/removeclassify/"+treeNode.id,
					   method: 'GET',
					}).success(function(data){
						console.log(data);
						if(data.code==0){
							alert(data.message);
							loadTree();//加载树
						}else{
							alert("此分类下有产品使用，不可删除！");
						}
					}).error(function(data){
						console.log(data);   
					})
				}else{
					$http({
						   url: path+"/server/brand/removeseries/"+treeNode.id,
						   method: 'GET',
						}).success(function(data){
							console.log(data);
							if(data.code==0){
								alert(data.message);
								loadTree();//加载树
							}else{
								alert("此系列下有产品使用，不可删除！");
							}
						}).error(function(data){
							console.log(data);   
						})
				}
			}
		});
		//编辑分类
		$("#popover_" +treeNode.id+ "_i2").bind("click", function(){
			console.log("编辑分类");
			zTree.editName(treeNode);
		});
		//添加系列
		$("#popover_" +treeNode.id+ "_i3").bind("click", function(){
			treeNode.isParent=true;
			var newNodes = zTree.addNodes(treeNode, {id:"", pId:treeNode.id, name:"未命名" + (newCount++)});
			if(newNodes){
				zTree.editName(newNodes[0]);
			}
		});
		//编辑系列
		$("#popover_" +treeNode.id+ "_i5").bind("click", function(){
			console.log("编辑系列");
			zTree.editName(treeNode);
		});
		
	}
	//用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
	function zTreeBeforeRename(treeId, treeNode, newName, isCancel){
		//添加节点
		if(treeNode.id==""){
			//添加分类
			if(treeNode.parent_id==0){
				$http({
				   url: path+"/server/brand/createclassify",
				   method: 'POST',
				   data:angular.toJson({name:newName}),
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						loadTree();//加载树
					}
				}).error(function(data){
					console.log(data);   
				})
			}else{//添加系列
				$http({
				   url: path+"/server/brand/createseries",
				   method: 'POST',
				   data:angular.toJson({name:newName,classify_id:treeNode.pId}),
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						loadTree();//加载树
					}
				}).error(function(data){
					console.log(data);   
				})
			}
		}else{//编辑节点
			if(treeNode.parent_id==0){
				//编辑部门名称
				$http({
					url: path+"/server/brand/modifyclassify",
					method: 'POST',
					data:angular.toJson({id:treeNode.id,name:newName}),
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						loadTree();//加载树
					}
				}).error(function(data){
					console.log(data);   
				})
			}else{
				$http({
					url: path+"/server/brand/modifyseries",
					method: 'POST',
					data:angular.toJson({id:treeNode.id,name:newName}),
				}).success(function(data){
					console.log(data);
					if(data.code==0){
						loadTree();//加载树
					}
				}).error(function(data){
					console.log(data);   
				})
			}
		}
	}
	//单击节点事件
	function zTreeOnClick(event, treeId, treeNode){
		
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
			   url: path+"/server/brand/classifyseries",
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
})
