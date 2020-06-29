App.controller("updateJurisdiction",function($scope, $rootScope, $location, $http, $compile, $stateParams, path){
	var params = JSON.parse($stateParams.params);
	console.log("路由参数：",params);
	var setting = {
			view: {
				showIcon: false,
				selectedMulti: false,
				dblClickExpand: false,
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: 0
				}
			}
		};
		//初始化加载数据
		function loadTree(){ 
			var treeObj = $("#treeDemo");
			$http({
				   url: path+"/server/getpermissionZtree",
				   method: 'GET',
			}).success(function(data){
				$.fn.zTree.init(treeObj, setting, data);
				zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
				$http({
					   url: path+"/server/permissionbyroleid/"+params.id,
					   method: 'GET',
				}).success(function(data){
					var nodes = zTree_Menu.transformToArray(zTree_Menu.getNodes());
					for (var i = 0; i < nodes.length; i++) {
						for (var l = 0; l < data.length; l++) {
							if(data[l]==nodes[i].id){
								zTree_Menu.checkNode(nodes[i], true, true);
							}
						}
					}
				}).error(function(data){
					console.log(data);   
				})
			}).error(function(data){
				console.log(data);   
			})
		}
	
		$(document).ready(function(){
			//$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			loadTree();
		});
		
		//保存权限设置
		$scope.addqx=function(){
			var ztreeArray=zTree_Menu.getCheckedNodes(true);
			var dateArray=[];
			for (var i = 0; i < ztreeArray.length; i++) {
				dateArray.push(ztreeArray[i].id+'');
			}
			console.log(dateArray);
			 $http({
	     		method : 'POST',
	     		url : path+'/server/updatepermission2role',
	     		data : angular.toJson({role_id:params.id,permission:dateArray}),
	     		headers : {
	     			'Content-Type' : 'application/json'
	     		}
	     	}).success(function(data) {
	     		 if(data.code==0){
	     			 alert("成功");
	     			$location.path("/rolePermission");
	     		 }
	     	});
		}
})
