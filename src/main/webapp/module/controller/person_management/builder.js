App.controller("builderCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	var curPromoterRole = 3//1：推广员页面2：推广导购员页面 3：施工人员页面
	$scope.details = {};  //定点列表
	$scope.userRole = {}; //
	//初始化搜索条件input及grid显示的模块数据
	$scope.initsearchIput = function(){
		//userInfo.user_type = "2"
		if(userInfo.user_type=="0")
		{
		$scope.userRole.userEnterpris = "show";		
		}else if(userInfo.user_type=="1")
		{
			$scope.userRole.userEnterpris = "show";
			$("#editAccount").find("input").attr("disabled","disabled");
			$("#editAccount").find("input").attr("readonly","readonly");
		}
		else if(userInfo.user_type== "2") //企业用户
		{
			$("#editAccount").find("input").attr("disabled","disabled");
			$("#editAccount").find("input").attr("readonly","readonly");
			$scope.userRole.userPartner = "show";
		}
		else if(userInfo.user_type=="3")//城市合伙人用户
		{
			$scope.userRole.userPartner = "hidden";
		}
	}
	
	$scope.initsearchIput();            //初始化搜索条件input

	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/getSeller",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						if (operation == "read") {
							var sort = [{dir:"desc",field:"create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								sort=options.sort;
							}
							
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									seller_type: curPromoterRole,	 	//1：推广员2：推广导购员 3：施工人员
									name: $scope.search.name1,		   		//姓名
									account: $scope.search.account,           //账号名称
									principal: $scope.search.principal,	 	//合伙人名称	
									phone: $scope.search.phone,	 	//联系方式
									state: $scope.search.state,	 	//状态
									expand_name: $scope.search.expand_name,	 	//推广员
									sort:sort
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
						width : "90px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle'></span>"
					},
					{
						field : "name",         
						title : "姓名", 
						width : "10%" 
					},
					{
						field : "account",         
						title : "用户名",
						width : "15%" 
					},
					{
						field : "expand_name",         
						title : "推广员姓名",
						width : "15%" 
					},
					{
						field : "service_type",         
						title : "施工种类",
						width : "10%" 
					},
					{
						field : "unit",         
						title : "施工单价", 
						width : "10%" 
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "20%"    
					},

					{
						field : "invite_count",         
						title : "推广数量",     
						width : "10%",
						template:function(e)
						{
							if(e.invite_count == null)
							{
								return 0;
							}else
							{
								return e.invite_count;
							}
						}    
					},
					{
						field : "create_time",         
						title : "创建时间",     
						width : "20%"    
					},
					{
						field : "certification_state",         
						title : "账号状态",     
						width : "10%",
						template: function(e){
							var html = '';
							if(e.certification_state==null){
								html = '<div>未提交</div>';
							}else if(e.certification_state==0){
								html = '<div>未通过</div>';
							}else if(e.certification_state==1){	//通过
								if(e.state==0){
									html = '<div>关闭</div>';
								}else if(e.state==1){
									html = '<div>正常</div>';
								}
							}else if(e.certification_state==2){
								if(userInfo.user_type=="3"){  //如果是城市合伙人
									html = '<div class="canEdit" ng-click="waitingCheck(\''+e.id+'\')">待审核</div>';
								}else
								{
									html = '<div class="canEdit">待审核</div>';
								}
							}
							
							return html;
						}
					},
					{

						field : "state", 
						title : "操作",     
						width : "16%",
						 template:function(e){
				            	if(userInfo.user_type=="3"){  //如果是城市合伙人
				            		var toggleBtn_name = ""
				            		if(e.state == 1)
			            			{
			            				toggleBtn_name = "关闭"
			            			}else if(e.state == 0)
			            			{
			            				toggleBtn_name = "开启"
			            			}
				            		var template_html = '<div class="operation k-state-default splitButtona" ng-click="editData(\''+e.id+'\')">编辑</div>'
									    +'<div class="operation k-state-default splitButtona" ng-click="buildDataBtn('+e.id+')">推广数据</div>'+
									    '<div class="operation k-state-default splitButtona" ng-click="toggleBtn('+e.id+","+e.state+')">'+toggleBtn_name+'</div>'
				            		return template_html;
				            	}else{  //如果是开发者或企业
				            		var template_html = '<div class="operation k-state-default splitButtonTwoc" ng-click="lookData(\''+e.id+'\')">查看</div>'
									    +'<div class="operation k-state-default splitButtonTwoc" ng-click="buildDataBtn('+e.id+","+1+')">推广数据</siv>';
				            		return template_html;
				            	}
				            }
						
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//导出
	$scope.dataExport=function(){
		var sort = [{dir:"desc",field:"create_time"}];
		var param={
				seller_type: curPromoterRole,	 	//1：推广员2：推广导购员 3：施工人员
				name: $scope.search.name1,		   		//姓名
				account: $scope.search.account,           //账号名称
				principal: $scope.search.principal,	 	//合伙人名称	
				phone: $scope.search.phone,	 	//联系方式
				state: $scope.search.state,	 	//状态
				expand_name: $scope.search.expand_name,	 	//推广员
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "shigong_module");  
	    
	    var my_input_page = $('<input type="text" name="page" />');  
	    /*{page:1,pageSize:10,ispage:false,"param":param}*/
	    my_input_page.attr('value', angular.toJson({page:1,pageSize:10,ispage:false,"param":param}));  
	    // 附加到Form  
	    form.append(my_input);  
	    form.append(my_input_page);  
	    console.log(form.serializeArray());
	    form.submit(); 
	}
	
	//推广数据
	$scope.promotionData=function(){
		$location.path("/promotionData");
	}
	
	//初始化状态
	$scope.initStateStatus = function(){
		$("#state").kendoDropDownList({
			dataTextField: "name",
		    dataValueField: "value",
			dataSource: [
						{
						    name:"全部",
						    value:""
						},
			             {
				              name:"正常",
				              value:"1"
		            	  },
		            	  {
				              name:"关闭",
				              value:"0"
		            	  }
			             ],
             change: function(e) {
		    	    var value = this.value();
		    	    $scope.search.state = value;
		      }
		});
	}
	
	//启用和禁用
	$scope.toggleBtn=function(id,state){	
		var isOpen = true;
		
		if(state==1){	//关闭
			if(!confirm("您确定关闭该账号吗?")){
				isOpen = false;
			}
		}
		
		if(isOpen){
			var postData = new Object();
			if(state == 1){
				postData.state = 0;
			}else if(state == 0){
				postData.state = 1;
			}
			postData.id =id;
			console.log("aaaa",postData)
			$http({
				   url: path+'/server/modifyseller',
				   method: 'POST',
				   data: angular.toJson(postData), 
			}).success(function(data){  
				if(data.code==0){  //成功
					$scope.search();
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})
		}
	}

	
	//推广数据
	$scope.buildDataBtn = function(id){
		console.log("ssssss    "+id)
		var params = {curUserID:id,promotionOrInstall:2};//1，推广导购界面（推广数量，订单收入） 2，施工信息界面（推广数量，订单收入，施工预约）
		$location.path("/builderData/"+angular.toJson(params));
	}
	
	//待审核
	$scope.waitingCheck = function(id){
		var params = {method:"waitingCheck", buiderId:id};
		$location.path("/builderDetails/"+angular.toJson(params));
	}
	
	//编辑
	$scope.editData = function(id){
		var params = {method:"edit", buiderId:id};
		$location.path("/builderDetails/"+angular.toJson(params));
	}
	
	//查看
	$scope.lookData = function(id){
		var params = {method:"look", buiderId:id};
		$location.path("/builderDetails/"+angular.toJson(params));
	}
	
	
		
	//初始化关闭状态
	$scope.initStateStatus()
	//初始化搜索条件input  如果是开发者或企业，不能点击上传图片按钮，必须最后运行，才能触发上传按钮不能用的
	$scope.initsearchIput();        
	
	//选中导出
	$scope.dataCheckboxExport=function(){
		var grid = $("#gridBuilder").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"施工员列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridBuilder").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}
});