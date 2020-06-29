App.controller("promoterCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	var curPromoterRole = 1//1：推广员页面2：推广导购员页面 3：施工人员页面
	$scope.details = {};  //定点列表
	//如果当前权限开发者，则显示城市合伙人和企业，如果当前为企业，则显示城市合伙人
	$scope.userRole = {}; //显示企业
	var isCreateOrEdit = false;
	var curSelectUserID = 0; //当前选择的列表行id
	
	
	//初始化搜索条件input及grid显示的模块数据
	$scope.initsearchIput = function(){
		//userInfo.user_type = "2"
		$scope.userRole.show_operation = true;//城市合伙人显示操作列表项
		if(userInfo.user_type=="0")
		{
		$scope.userRole.userEnterpris = "show";
		$scope.userRole.isShowCreateAccountBtn = "false";
		isShowCreateAccountBtn.isShowCreateAccountBtn = "hidden";
		}else if(userInfo.user_type=="1")
		{
			$scope.userRole.userEnterpris = "show";	
			$scope.userRole.isShowCreateAccountBtn = "false";
		}
		else if(userInfo.user_type== "2") //企业用户
		{
			$scope.userRole.userPartner = "show";
			$scope.userRole.isShowCreateAccountBtn = "false";
		}
		else if(userInfo.user_type=="3")//城市合伙人用户
		{
			$scope.userRole.userPartner = "hidden";
			$scope.userRole.isShowCreateAccountBtn = "true";
			$scope.userRole.show_operation = false;
		}
	}
	
	$scope.initsearchIput();            //初始化搜索条件input
	
	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/getTuiguangSeller",
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
									sort:sort
	
									}
							};
							console.log('查询参数11',parameter);
							return kendo.stringify(parameter);
						}
					},
				},
				pageSize : 10,
				serverPaging : true,
				serverSorting: true,
				schema : {
					data : function(d) {
						console.log("推广员管理返回数据",d.data)
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
			    mode: "single"     //排序模式：single，multiple
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
						width : "100px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle'></span>"
					},
					{
						field : "name",         
						title : "姓名",     
						width : "100px"
					},
					{
						field : "account",         
						title : "账号名称",     
						width : "100px"
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "100px"
					},
					{
						field : "principal",         
						title : "所属合伙人",     
						width : "100px"
					},
					{
						field : "seller_num",         
						title : "推广数量",     
						width : "100px",
						template:function(e)
						{
							if(e.seller_num == null)
							{
								return 0;
							}else
							{
								return e.seller_num;
							}
						}
					},
					{
						field : "create_time",         
						title : "创建时间",     
						width : "100px"
					},
					{
						field : "state",         
						title : "账号状态",     
						width : "100px",
						values: [
						         { text: "正常", value:1 },
						         { text: "关闭", value: 0 }
						       ]
					},
					{
						hidden: $scope.show_operation,//用户 品牌／店铺 显示操作列   
						//field : "state", 
						
						title : "操作",     
						width : "80px",
						 template:function(e){
							 	var template_html = '';
							 	
						    	if(userInfo.user_type==1){  //开发者
						    		template_html += '<div class="operation k-state-default " ng-click="lookOrEdit(\''+e.id+'\',1)">查看</div>';	
						    	}else if(userInfo.user_type==2){  //企业
						    		template_html += '<div class="operation k-state-default " ng-click="lookOrEdit(\''+e.id+'\',1)">查看</div>';
						    	}else if(userInfo.user_type==3){  //合伙人
						    		if(e.state==1){	//开启
						    			template_html += ('<div class="operation k-state-default splitButtonTwoc" ng-click="lookOrEdit(\''+e.id+'\',2)">编辑</div>'
											    		 +'<div class="operation k-state-default splitButtonTwoc" ng-click="toggleBtn('+e.id+","+0+')">禁用</div>');
						    		}else if(e.state==0){	//关闭
						    			template_html += ('<div class="operation k-state-default splitButtonTwoc" ng-click="lookOrEdit(\''+e.id+'\',2)">编辑</div>'
									    		 		 +'<div class="operation k-state-default splitButtonTwoc" ng-click="toggleBtn('+e.id+","+1+')">启用</div>');
						    		}
						    	}else if(userInfo.user_type==4){  //品牌
						    		
						    	}else if(userInfo.user_type==5){  //店铺
						    		
						    	}
						    	
						    	return template_html;
				            }
						
					}
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//打开创建帐户弹出框
	$scope.openCreateAccount_win=function(){
		isCreateOrEdit = true;
		$("#resetPasswordBtn").remove();
		$scope.creatAccount_win.center().open();   //打开弹框
		console.log($scope.creatAccount_win);
		$scope.creatAccount_win.bind("close", $scope.window_close);
	}
	
	//当关闭弹出窗口时，把回显数据清空
	$scope.window_close=function(e){
		  // close animation has finished playing
		$scope.details = {};
	}
	//创建帐户
	$scope.createAccountOK = function(){
		var curUrl=""
		var postData = new Object();
		postData.account = $scope.details.phone; ///目前增加帐户 联系方式和账号名称一样
		postData.password = $scope.details.password;
		postData.seller_type = curPromoterRole;
		postData.phone = $scope.details.phone;
		postData.name = $scope.details.name;
		
		//参数验证
		if(!postData.name){
			alert("请填写【姓名】！");
			return;
		}
		if(!postData.account){
			alert("请填写【联系方式】！");
			return;
		}
		if(!postData.password){
			alert("请填写【密码】！");
			return;
		}

		if(isCreateOrEdit)
		{
			postData.partner_id = userInfo.id;
			curUrl = path+'/server/addseller'
		}else
		{
			postData.id = curSelectUserID
			curUrl = path+'/server/modifyseller'
		}
		
	
			$http({
				   url: curUrl,
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){
				console.log("dd   "+angular.toJson(postData))
				if(data.code==0){  //成功
					$scope.creatAccount_win.center().close();   //关闭弹框
					$scope.search();
					
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})		
	}
	
	//查看或编辑
	$scope.lookOrEdit = function(id,type){
		var postData = new Object();
		postData.id =id;
		
		$http({
			   url: path+'/server/getoneseller',
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){  
			if(data.code==0){  //成功
				var datails = data.result;
				
				if(datails){   //有详情信息
					$scope.details.name = datails.name;//名字，
					$scope.details.account =datails.phone// datails.account;//账号名称
					$scope.details.phone = datails.phone;//联系方式
					$scope.details.password = "******"; //密码
					$("#pwd").attr("readonly","readonly");
					$scope.creatAccount_win.center().open();   //打开弹框
					
					if(type==1){	//查看
						$("#submitButton").remove();
						$("#resetPasswordBtn").remove();
						$("#promoterForm").find("input").attr("readonly","readonly");
					}else if(type==2){	//编辑
						isCreateOrEdit = false;
						curSelectUserID = id
					}
				}else{
					alert("暂无推广员详细信息！");
				}
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	
	//重置密码
	$scope.resetPassword = function(){ 
		var postData = new Object();
		postData.id = curSelectUserID;
		postData.password = "888888";
		
		$http({
			   url: path+'/server/modifyseller',
			   method: 'POST',
			   data: angular.toJson(postData),  
		}).success(function(data){
			if(data.code==0){  //成功
				alert("密码重置成功！");
				$scope.details.password = "888888";
			}else if(data.code==1){  //失败
				
			}
		}).error(function(data){
			alert(data);   
		})
	}

	//启用和禁用
	$scope.toggleBtn=function(id,state){	
		var isOpen = true;
		
		if(state==0){	//禁用
			if(!confirm("您确定禁用该账号吗?")){
				isOpen = false;
			}
		}

		if(isOpen){
			var postData = new Object();
			postData.state = state;
			postData.id =id;
			
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
	
	//导出
	$scope.dataExport=function(){
		var sort = [{dir:"desc",field:"create_time"}];
		var param={
				seller_type: curPromoterRole,	 	//1：推广员2：推广导购员 3：施工人员
				name: $scope.search.name1,		   	//姓名
				account: $scope.search.account,     //账号名称
				principal: $scope.search.principal,	//合伙人名称	
				sort:sort
			 }	
	    var form = $('<form></form>');  
	    form.attr('action', path+"/server/export");  
	    form.attr('method', 'post');  
	    
	    form.attr('target', '_self');  
	    var my_input = $('<input type="text" name="module_code" />');  
	    my_input.attr('value', "tuiguangyuan_module");  
	    
	    var my_input_page = $('<input type="text" name="page" />');  
	    /*{page:1,pageSize:10,ispage:false,"param":param}*/
	    my_input_page.attr('value', angular.toJson({page:1,pageSize:10,ispage:false,"param":param}));  
	    // 附加到Form  
	    form.append(my_input);  
	    form.append(my_input_page);  
	    console.log(form.serializeArray());
	    form.submit(); 
	}
	//选中导出
	$scope.dataCheckboxExport=function(){
		var grid = $("#gridPromoter").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"推广员列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridPromoter").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}
});