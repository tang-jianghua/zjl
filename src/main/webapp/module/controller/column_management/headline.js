App.controller("headlineCtrl",function($scope, $rootScope, $location, $http, $compile, path) {
	
	$scope.form = {};   //表单
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryheadline",
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
									title: $scope.search.title,	 	//姓名
									start_time: $("#start_time").val(),	//预约时间（始）
									end_time: $("#end_time").val(),		//预约时间（止）
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
						field : "release_time",         
						title : "发布日期",     
						width : "15%"    
					},
					{
						field : "end_time",         
						title : "到期日期",     
						width : "15%",
						format: "{0: yyyy-MM-dd HH:mm:ss}"
					},
					{
						field : "title",         
						title : "发布内容",     
						width : "30%"    
					},
					{
						field : "state",         
						title : "状态",     
						width : "10%", 
						values: [
						         { text: "准备发布", value: 0 },
						         { text: "当前显示", value: 1 },
						         { text: "已过期", value: 2 }
						       ]
					},
					{
						field : "tag",         
						title : "标识",     
						width : "10%",
						values: [
						         { text: "最新", value: 1 },
						         { text: "推荐", value: 2 }
						       ]
					},
					{
						field : "id",         
						title : "操作",     
						width : "20%",
						template: function(e){
							var html = ('<div class="k-state-default operation splitButtona" ng-click="edit('+e.id+')">编辑</div>'
									   +'<div class="k-state-default operation splitButtona" ng-click="deletett('+e.id+')">删除</div>'
									   +'<div class="k-state-default operation splitButtona" ng-click="contentJump(\''+e.id+'\',\''+e.link_type+'\',\''+e.data_param+'\')">内容跳转设定</div>');
							return html;
						}
						
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	//删除
	$scope.deletett=function(id){
		if(confirm("是否确认删除？")){
			$http({
				   url: path+'/server/removeheadline/'+id,
				   method: 'GET'
			}).success(function(data){
				alert(data.message);
				$scope.grid.dataSource.page(1);
			}).error(function(data){
				alert(data);   
			})
		}
	}
	//编辑
	$scope.edit = function(id){
		$http({
			   url: path+'/server/getheadline/'+id,
			   method: 'GET'
		}).success(function(data){
			if(data.code==0){  //成功
				var headLine = data.result;
				
				$scope.form.id = headLine.id;
				$("#start_time2").val(headLine.release_time);   //发布日期
				$("#end_time2").val(headLine.end_time);		  //到期日期
				$scope.form.title = headLine.title;  //发布内容
				$("#state").data("kendoDropDownList").value(headLine.state);		//状态
				$("#tag").data("kendoDropDownList").value(headLine.tag);			//标识
				
				$scope.addHeadlineWindow.center().open();   //打开弹框
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//初始化时间控件
	$scope.initDateTime = function(containerId){
		$("#"+containerId).kendoDateTimePicker({
		     value: "",
		     culture: "de-DE",
		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
		     interval: 30,   				  //时间间隔
		     timeFormat: "HH:mm" 			  //24 hours format
		});
	}
	
	//初始化状态
	$scope.initState = function(){
		$("#state").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 0,
			 				text: "准备发布"
			             },
			             {
			            	 id: 1,
			 				text: "当前显示"
			             },
			             {
			            	 id: 2,
			 				text: "已过期"
			             }
			             ]
		});
	}
	
	//初始化标识
	$scope.initTag = function(){
		$("#tag").kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "最新"
			             },
			             {
			            	 id: 2,
			 				text: "推荐"
			             }
			             ]
		});
	}
	
	//新增头条
	$scope.addHeadline = function(){
		$scope.form = {};
		$("#start_time2").val("");   //发布日期
		$("#end_time2").val("");		  //到期日期
		$("#state").data("kendoDropDownList").value("");		//状态
		$("#tag").data("kendoDropDownList").value("");			//标识
		
		$scope.addHeadlineWindow.center().open();   //打开弹框
	}
	
	//新增头条内容
	$scope.addNewHeadline = function(){
		$scope.form.release_time = $("#start_time2").val();   //发布日期
		$scope.form.end_time = $("#end_time2").val();		  //到期日期
		$scope.form.state = $("#state").data("kendoDropDownList").value();		//状态
		$scope.form.tag = $("#tag").data("kendoDropDownList").value();			//标识

		//验证
		if(!$scope.form.release_time){
			alert("请选择【发布日期】！");
			return;
		}
		if(!$scope.form.end_time){
			alert("请选择【到期日期】！");
			return;
		}
		if(!$scope.form.title){
			alert("请填写【发布内容】！");
			return;
		}
		if(!$scope.form.state){
			alert("请选择【状态】！");
			return;
		}
		if(!$scope.form.tag){
			alert("请选择【标识】！");
			return;
		}
		
		
		var url = '';
		if($scope.form.id){
			url = path+'/server/modfiyheadline';
		}else{
			url = path+'/server/createheadline';
		}
		$http({
			   url: url,
			   method: 'POST',
			   data: angular.toJson($scope.form)
		}).success(function(data){
			if(data.code==0){  //成功
				$scope.addHeadlineWindow.close();
				$scope.grid.dataSource.page(1);
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	

	
	$scope.initDateTime("start_time");   //起始时间
	$scope.initDateTime("end_time");   	 //终止时间
	
	$scope.initDateTime("start_time2");   //发布日期
	$scope.initDateTime("end_time2");     //到期日期
	$scope.initState();		//初始化状态
	$scope.initTag();		//初始化状态
	
	
	//内容跳转
	$scope.contentJump = function(from_id,link_type,to_id){
		var params = {method:"headlineSkip", fromId:from_id, linkType:link_type, toId:to_id};
		$location.path("/linkSkipManagement/"+angular.toJson(params));
	}
	

});