App.controller("caseCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	
	$scope.caseForm = {};
	
	var limitParam1 = {imageSize:80, format:"jpg"};		//首图
	var limitParam2 = {imageSize:80, format:"jpg"};		//案例图
	
	$scope.mainGridOptions1 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querycase",
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
									type: 1	 	//案例
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
						field : "list_img",         
						title : "头图显示",     
						width : "100px",
						template:'<img style="width: 185px;height: 69px;" " src="#: list_img #" />'
					},
					{
						field : "name",         
						title : "案例名称",     
						width : "120px"
					},
					{
						field : "create_time",         
						title : "发布日期",     
						width : "100px"    
					},
					{
						field : "state",         
						title : "状态",     
						width : "120px",
						values: [
						         { text: "显示", value: 1 },
						         { text: "关闭", value: 2 }
						       ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template: function(e){
							var html = '';
							
							if(e.state==1){
								html += ('<div class="operation k-state-default splitButtona" ng-click="openOrClose(\''+e.id+'\',2)">关闭</div>'
										+'<div class="operation k-state-default splitButtona" ng-click="editData(\''+e.id+'\')">编辑</div>'
										+'<div class="operation k-state-default splitButtona" ng-click="deleteData(\''+e.id+'\')">删除</div>');
							}else if(e.state==2){
								html += ('<div class="operation k-state-default splitButtona" ng-click="openOrClose(\''+e.id+'\',1)">显示</div>'
										+'<div class="operation k-state-default splitButtona" ng-click="editData(\''+e.id+'\')">编辑</div>'
										+'<div class="operation k-state-default splitButtona" ng-click="deleteData(\''+e.id+'\')">删除</div>');
							}
							
							return html;
						}
					}
			]
		};
	
	$scope.mainGridOptions2 = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/querycase",
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
									type: 2	 	//案例资源
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
						title : "案例名称",     
						width : "100px",
						format: "{0: yyyy-MM-dd HH:mm:ss}"
					},
					{
						field : "create_time",         
						title : "发布日期",     
						width : "100px"    
					},
					{
						field : "state",         
						title : "显示",     
						width : "100px",
						values: [
						         { text: "显示", value: 1 },
						         { text: "关闭", value: 2 }
						       ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div value='显示' class='k-button k-state-default ' ng-click='showDetails(\"#: id #\")' />显示</div>"
								  +"<div  value='查看' class='k-button k-state-default ' ng-click='showDetails(\"#: id #\")' />查看</div>"
					}
			]
		};
	
	//参与合伙人
	$scope.initJoinPartner = function(containerId){
		$("#"+containerId).kendoMultiSelect({
		    dataTextField: "principal",
		    dataValueField: "id",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'POST',
	                  url: path+"/server/selectAllPartner",
	                  dataType : "json"
	              }
	          }
	      }
		});
	}
	
	//获取——案例列表首图
	$scope.getCaseListTopImg = function(){
		$http({
			   url: path+'/server/querycasetopimg',
			   method: 'GET'
		}).success(function(data){
			$scope.caseListTopImg = data.result[0];   //案例列表首图
			
			$scope.showUploadImg("logo_img","logo_url",$scope.caseListTopImg.imgurl);
		}).error(function(data){
			alert(data);   
		})
	}
	
	//编辑——案例列表首图
	$scope.editCaseListTopImg = function(imgUrl){
		$scope.caseListTopImg.imgurl = imgUrl;
		
		$http({
			   url: path+'/server/modfiycasetopimg',
			   method: 'POST',
			   data: angular.toJson($scope.caseListTopImg)
		}).success(function(data){
			if(data.code==0){  //成功
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//选择列表
	$scope.chooseList = function(type){
		for(var i=1;i<=2;i++){
			if(i==type){
				$("#list"+i).addClass("choose");
				$("#list"+i+"_container").show();
			}else{
				$("#list"+i).removeClass("choose");
				$("#list"+i+"_container").hide();
			}
		}
	}
	
	//上传图片，文件
	$scope.Upload = function(containerId,imgId,imgUrlId,type,limitParam){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/updoad",
	            removeUrl: path+"/remove",
	           // autoUpload: false
	        },
	     	multiple: false,      //多选
	      	showFileList: false,  //显示文件列表
	        localization: {
	              select: "请上传",
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
	        	var url = e.response.result;   //路径
	        	if(type==1){       //上传图片
	        		$scope.showUploadImg(imgId,imgUrlId,url);
	        		$scope.editCaseListTopImg(url);		//编辑——案例列表首图
	        	}else if(type==2){ //上传文件
	        		$scope.showUploadImg(imgId,imgUrlId,url);
	        	}else if(type==3){
	        		$('#'+imgUrlId).val(url);
	        	}
		    },
	        error: function(e){
	        	//alert('失败！');
	        },
	        remove: function(e){
	        	//alert('移除！');
	        },
	        select: function(e){	//选择
	        	var image = e.files[0];
	        	if(limitParam){
	        		if(image.size>(limitParam.imageSize*1024)){
		        		alert("上传图片的大小请限制在【"+limitParam.imageSize+"Kb】以内！");
		        		e.preventDefault();
		        		return;
		        	}
		        	if(image.extension.indexOf(limitParam.format)==-1){
		        		alert("上传图片仅支持【"+limitParam.format+"】格式！");
		        		e.preventDefault();
		        		return;
		        	}
	        	}
	        }
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
	
	//新增案例
	$scope.addCase = function(){
		$scope.caseForm = {};
		publicService.removeUploadImage("casePicture_img","casePicture_url");
		$("#join_partner").data("kendoMultiSelect").value("");	//合伙人
		
		$scope.addCaseWindow.center().open();   //打开弹框
	}
	
	//新增资源
	$scope.addCaseResource = function(){
		$scope.addCaseResourceWindow.center().open();   //打开弹框
	}
	
	//添加（案例）
	$scope.addNewCase = function(){
		$scope.caseForm.list_img = $("#casePicture_url").val();  //图片
		$scope.caseForm.partner_ids = $("#join_partner").data("kendoMultiSelect").value().toString();	//合伙人
		$scope.caseForm.type = 1;  //1：案例 2 案例资源
		
		//验证
		if(!$scope.caseForm.list_img){
			alert("请上传【案例图片】！");
			return;
		}
		if(!$scope.caseForm.name){
			alert("请填写【案例名称】！");
			return;
		}
		if(!$scope.caseForm.partner_ids){
			alert("请选择【合伙人】！");
			return;
		}
		
		var url = '';
		if($scope.caseForm.id){
			url = path+'/server/modfiycase';
			$scope.caseForm.state = 1;
		}else{
			url = path+'/server/createcase';
		}
		
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.caseForm)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.addCaseWindow.close();   //关闭
				$scope.grid1.dataSource.page(1);
				
				$scope.caseForm = {};
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//编辑（案例）
	$scope.editData = function(id){
		$http({
			   url: path+'/server/getcaseid/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				var caseData = data.result;
				
				$scope.caseForm.id = caseData.id;
				publicService.showUploadImage("casePicture_img","casePicture_url",caseData.list_img);
				$scope.caseForm.name = caseData.name;			//案例名称
				$scope.caseForm.link_url = caseData.link_url;	//案例链接
				$("#join_partner").data("kendoMultiSelect").value(caseData.partner_ids.split(","));	//合伙人

				$scope.addCaseWindow.center().open();   //打开弹框
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//显示，关闭
	$scope.openOrClose = function(id,state){
		var isOpen = true;
		
		if(state==2){	//关闭
			if(!confirm("您确定关闭该案例吗?")){
				isOpen = false;
			}
		}

		if(isOpen){
			var postData = {
					id: id,
					state: state
			};

			$http({
				   url: path+'/server/modfiycase',
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				if(data.code==0){  //成功
					$scope.grid1.dataSource.page(1);
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}
	
	//删除（案例）
	$scope.deleteData = function(id){
		if(confirm("是否确认删除？")){
			$http({
				   url: path+'/server/removecase/'+id,
				   method: 'GET'
			}).success(function(data){
				alert(data.message);
				$scope.grid1.dataSource.page(1);
			}).error(function(data){
				alert("删除案例失败！");   
			})
		}
	}
	
	//添加新的案例资源
	$scope.addNewCaseResource = function(){
		$scope.caseResourceForm.case_resource_url = $("#caseResourceUrl_url").val();  //案例文件
		$scope.caseResourceForm.type = 2;  //1：案例 2 案例资源
		
		$http({
			   url: path+'/server/createcase',
			   method: 'POST',
			   data: angular.toJson($scope.caseResourceForm)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.addCaseResourceWindow.close();   //关闭
				$scope.grid2.dataSource.page(1);
				
				$scope.caseResourceForm = {};
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	
	$scope.getCaseListTopImg();		//获取案例列表首图

	$scope.chooseList(1);   //选择案例列表
	
	$scope.Upload("logo","logo_img","logo_url",1,limitParam1);		//案例列表-首图
	$scope.Upload("casePicture","casePicture_img","casePicture_url",2,limitParam2);		//新增案例-图片
	$scope.initJoinPartner("join_partner");
	$scope.Upload("caseResourceUrl",null,"caseResourceUrl_url",3);		    //案例资源-文件

});