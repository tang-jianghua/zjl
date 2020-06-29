App.controller("rolePermissionCtrl",function($scope, $rootScope, $location, $http, $compile,path){
	
	 $scope.requestData;
	
    $scope.treelistOptions = {
        dataSource: {
            transport: {
                read:  {
                    url: path+"/server/querydeporpositionList",
                    type : 'POST',
					dataType : "json",
					contentType : "application/json"
                },
                update: {
                    url:  path+"/server/modifydep",
                    type : 'POST',
					dataType : "json",
					contentType : "application/json"
                },
                destroy: {
                    url:   path+"/server/removedep",
                    type : 'POST',
					dataType : "json",
					contentType : "application/json"
                },
                create: {
                    url: path+"/server/createdep",
                    type : 'POST',
					dataType : "json",
					contentType : "application/json"
                },
                parameterMap: function(options, operation) {
                    if (operation !== "read" && options.models) {
                    	
                    	if(options.models[0].is_position){
                    		
                    		options.models[0].is_position=1
                    	}else{
                    		options.models[0].is_position=0
                    	}
                    	
                        return  kendo.stringify(options.models);
                    }
                }
            },
            batch: true,
            schema: {
                model: {
                    id: "id",
                    parentId: "parent_id",
                    fields: {
                        id: { type: "number", editable: false, nullable: false },
                        parent_id: { nullable: true, type: "number", editable: false},
                        name: { validation: { required: true } },
                        is_position:{type:"boolean" }
                        
                    }
                },
                expanded: true
            }
        },
        editable: true,
        toolbar: [ "create" ],
        columns: [
            { field: "name", title: "名称", width: "100px" },
            { field: "is_position", title: "是否是岗位", width: "100px",template:function(e){
            	
            	if(e.is_position){
            		return "是";
            	}else 
            		{
            		return "否";
            		}
            	
            }}
            
           
        ]
    };

    
    

    
    $scope.add=function(){
    	
    	var row = $scope.dep_tree_list.select();
    
    	if(row.length>0){
    		var data =  $scope.dep_tree_list.dataItem(row);
    		 $scope.dep_tree_list.addRow(data);
    	 
    	}else{
    		alert("请选择")
    	}
    }
    
    
    /**
     * 
     */
    $scope.save=function(){
  
    	$scope.dep_tree_list.saveRow();
    	//$scope.dep_tree_list.dataSource.read();   
      	//$scope.dep_tree_list._render();
    	
    }
    
    $scope.edit=function(){
    	
    	var row = $scope.dep_tree_list.select();
    	
    	$scope.dep_tree_list.editRow(row);
    }
    
    $scope.cancelEdit=function(){
    	
    	$scope.dep_tree_list.cancelRow();
    }
    
    $scope.remove=function(){
    	
    	
    	var row = $scope.dep_tree_list.select();
    	$scope.dep_tree_list.removeRow(row);
    	
    }
    
    $scope.tree_data_bound=function(e){
    	var dataItem =$scope.dep_tree_list.dataSource.get(1);
    	var row = $scope.dep_tree_list.content.find("tr[data-uid=" + dataItem.uid + "]")
    	$scope.dep_tree_list.expand(row);
    }
    
    $scope.RoleGridOptions={
    		
    		dataSource:{
    			
    			transport: {
    				read : {
    					url : path+"/server/queryrole",
    					type : 'POST',
    					dataType : "json",
    					contentType : "application/json"
    				},
                    update: {
                    	 url: path+"/server/modifyrole",
                         type : 'POST',
     					dataType : "json",
     					contentType : "application/json"
                    },
                    destroy: {
                    	url: path+"/server/removerole",
                        type : 'POST',
    					dataType : "json",
    					contentType : "application/json"
                    },
                    create: {
                        url: path+"/server/createrole",
                        type : 'POST',
    					dataType : "json",
    					contentType : "application/json"
                    },
                    parameterMap: function(options, operation) {
                        if (operation !== "read" && options.models) {
                        	
                        	console.log(options.models)
                            return kendo.stringify(options.models);
                        }
                        if (operation == "read") {
    						var parameter = {
    							page : options.page, //当前页
    							pageSize : options.pageSize
    							
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
                            role_name: { validation: { required: true } }
                           
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
    toolbar: ["create", "save", "cancel"],
    columns: [
        { field: "role_name", title: "角色名称", width: 120 },
        { command: ["destroy",{name:"设置权限",click:function(e){
        	
        	var tr = $(e.target).closest("tr"); 
           
            var data = this.dataItem(tr);
            $scope.requestData={};
            $scope.requestData.role_id=data.id;
        	
            
            $http({
        		method : 'GET',
        		url : path+'/server/permissionbyroleid/'+$scope.requestData.role_id,
        		data : angular.toJson($scope.user),
        		headers : {
        			'Content-Type' : 'application/json'
        		}
        	}).success(function(data) {
        		
        		console.log(data);
        		 for(var i=0;i<data.length;i++){
        			 
        			 var name =data[i];
        			 $(":checkbox").filter(function() {
     					
     				
     					var d=$scope.permission_tree.dataItem($(this).parent().parent());
     				       var id=parseInt(d.id);
     				     
     	      	  		  return name==id;
     	      	  		}).prop("checked", true);
        		 }
    				
    				
    			
      		      $scope.permission_tree.updateIndeterminate();
        	});
            
            $scope.permission_win.center().open();
        	return false;
        	
        }}], width: 120 }
        
        
        ],
    editable:true
    }
    
    
    
    
    
    $http({
		method : 'POST',
		url : path+'/server/getpermissiontree',
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data) {
		
			var  dataSource=  new kendo.data.HierarchicalDataSource({data:data});
			$scope.permission_options={
				dataSource:dataSource,
				checkboxes: {
				    checkChildren: true,
				    template: "<input type='checkbox' id='checkedFiles#= item.id #' value='true' />"
				  }
			}
			
			
			
	});
    
    $scope.save=function(){
    	$scope.permissionTreeNodeIds=[];
    	 checkedNodeIds($scope.permission_tree.dataSource.view(), $scope.permissionTreeNodeIds);
    	 $scope.requestData.permission=$scope.permissionTreeNodeIds;
    	 
    	 alert($scope.permissionTreeNodeIds.length);
    	 $http({
     		method : 'POST',
     		url : path+'/server/updatepermission2role',
     		data : angular.toJson($scope.requestData),
     		headers : {
     			'Content-Type' : 'application/json'
     		}
     	}).success(function(data) {
     		
     		 $scope.permission_win.close();
     	});
    }
   
    function checkedNodeIds(nodes, checkedNodes) {
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].checked) {
                checkedNodes.push(nodes[i].id);
                console.info(nodes[i].text);
            }
            if (nodes[i].hasChildren) {
                checkedNodeIds(nodes[i].children.view(), checkedNodes);
            }
        }
    }
    
    $scope.permission_win_close=function(){
    	$(":checkbox").filter(function() {
	  		  return true
	  		}).prop("checked", false);
		
		$scope.permission_tree.updateIndeterminate();
    }
    
})
