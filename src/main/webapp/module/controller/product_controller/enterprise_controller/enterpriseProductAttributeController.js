/*产品属性controller*/
App.controller("enterpriseProductAttributeController",function($scope, $rootScope, $location, $http, $compile, $stateParams, path) {
	/**
	 * 初始化加载kendoComboBox
	 * 参数 下拉框id 名称 属性id 是否可填写 
	 */
	$scope.init = function(id,name,attrId,iswrite){
		var data={attr_id:attrId,iswrite:iswrite};
		console.log(data);
		$("#"+id).kendoComboBox({
			dataTextField: "value",
			dataValueField: "id",
			autoBind: true,
			placeholder: name,
			filter: "contains",
			dataSource: {
				serverFiltering: false,
				transport: {
					read: {
						type : 'POST',
						url: path+"/server/attrvaluebyattrid",
						dataType : "json",
						contentType: "application/json",
						//data:angular.toJson(data)
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
								var param={
									attr_id: attrId,
									iswrite:iswrite
								}	
							//console.log('查询参数',param);
							return kendo.stringify(param);
						}
					}
				},
				schema : {
					data : function(d) {
						//console.log(d);
						return d.result;
					}
				},
			}
		});
	}
	//初始化加载多选下拉框
	$scope.initSpace = function(id,name,attrId,iswrite){
		var dataJson={attr_id:attrId,iswrite:iswrite};
		$("#"+id).kendoMultiSelect({
			dataTextField: "value",
			dataValueField: "id",
			placeholder: name,
			filter: "contains",
			dataSource: {
				serverFiltering: false,
				transport: {
					read: {
						type : 'POST',
						url: path+"/server/attrvaluebyattrid",
						dataType : "json",
						contentType: "application/json",
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
								var param={
									attr_id: attrId,
									iswrite:iswrite
								}	
							//console.log('查询参数',param);
							return kendo.stringify(param);
						}
					}
				},
				schema : {
					data : function(d) {
						return d.result;
					}
				},
			}
		});
	} 
	
	/**
	 * 初始化级联父级
	 * 参数 文本框id 名称  id 是否可填写
	 */
	$scope.initParent = function(id,name,attrId,iswrite,nodeId){
		$("#"+id).kendoComboBox({
		    dataTextField: "value",
		    dataValueField: "id",
		    autoBind: true,
		    placeholder: name,
		    filter: "contains",
		    dataSource: {
		      serverFiltering: false,
	          transport: {
	              read: {
	            	  type : 'POST',
	                  url: path+"/server/attrvaluebyattrid",
	                  dataType : "json",
	                  contentType: "application/json",
	              },
				  parameterMap : function(options, operation) {
						if (operation == "read") {
								var param={
									attr_id: attrId,
									iswrite:iswrite
								}	
							//console.log('查询参数',param);
							return kendo.stringify(param);
						}
					}
	          },
	          schema : {
					data : function(d) {
						//console.log(d);
						return d.result;
					}
				},
	      },
	      change: function(e) {
	    	    $scope.getSeries(this._prev,this._old,nodeId);
	         }
		});
	}
	$scope.getSeries=function(defaultVal,id,nodeId){
		$http({
			   url: path+"/server/attrvaluebyparentId",
			   method: 'POST',
			   data:angular.toJson({id:id,attr_id:nodeId}),
		}).success(function(data){
			$scope.initSeries(data.result,defaultVal,nodeId);
		}).error(function(data){
			console.log(data);   
		})
	}
	//初始化子级
	$scope.initSeries = function(seriesData,defaultVal,nodeId){
		console.log(seriesData);
		if(seriesData){
			var plugObj = $("#attribute"+nodeId).data("kendoComboBox");
			plugObj.value("");
			plugObj.setDataSource(seriesData);
			
		}else{
			$("#attribute"+nodeId).kendoComboBox({
			    dataTextField: "value",
			    dataValueField: "id",
			    autoBind: true,
			    placeholder: "子级",
			    filter: "contains",
			   dataSource: []
			});
		}
	}
	//初始化动态加载产品属性
	$scope.initProductAttribute=function(){
		$http({
			url: path+"/server/attrbyclassid",
			method:'GET',   
		}).success(function(data){
			//console.log(data.result);
			for (var i = 0; i < data.result.length; i++) {
				$scope.data.push(data.result[i]);
				html='<div class="col-xs-6" style="height:50px;">'+
						'<div class="col-xs-4">'+
							'<label class="control-label col-sm-12" style="text-align: right;"><font class="f">*</font>'+data.result[i].name+'</label>'+
						'</div>'+
						'<div class="col-xs-8">'+
							'<input id="attribute'+data.result[i].id+'" style="width:99%"/>'+
						'</div>'+
					'</div>';
				$("#productAttributeController").append(html);
				//判断是否 是下拉框 并且 不可级联 并且 不是多选下拉框
				if(data.result[i].isenum==1&&data.result[i].iscascading==2&&data.result[i].ismultiple==2){
					$scope.init("attribute"+data.result[i].id,data.result[i].name,data.result[i].id,data.result[i].iswrite);
				}else if(data.result[i].ismultiple==1){//当是多选时
					$scope.initSpace("attribute"+data.result[i].id,data.result[i].name,data.result[i].id,data.result[i].iswrite);
				}else if(data.result[i].iscascading==1&&data.result[i].cascading_attr_id!=-1){//当是级联时父节点
					$scope.initParent("attribute"+data.result[i].id,data.result[i].name,data.result[i].id,data.result[i].iswrite,data.result[i].cascading_attr_id);
					$("#attribute"+data.result[i].id).attr("title","f");
					$("#attribute"+data.result[i].id).attr("cascading",data.result[i].cascading_attr_id);
				}else if(data.result[i].iscascading==1&&data.result[i].cascading_attr_id==-1){//是级联的子节点
					$scope.initSeries("","",data.result[i].id);
					$("#attribute"+data.result[i].id).attr("title","z");
				}
			}
		}).error(function(data){
			alert(data);   
		}) 
	}
	//加载所属分类
	$scope.initTreeLine = function(){
		$http({
			   url: path+"/server/ent/getclassifyseriesfortree",
			   method:'GET',   
		}).success(function(data){
			console.log(data);
			$("#tree_line").kendoTreeView({
				   checkboxes: {
		                checkChildren: true,
		                template: function(e){
		                	if(e.items){
		                		return "<span id='checkedFiles#= item.id #'><span>";
		                	}else{
		                		return "<input type='checkbox' id='checkedFiles#= item.id #' value='true' />";
		                	}
		                }
		            },
		            dataSource:data
				});
			
		}).error(function(data){
			cnsole.log("加载所属分类错误");   
		}) 
	}
	$scope.initTreeLine();        //初始化所属分类
	$scope.initProductAttribute();
});