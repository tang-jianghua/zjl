App.controller("pusherManagementCtrl",function($scope, $rootScope, $location, $http, $compile, path, publicService) {
	var curPromoterRole = 2//1：推广员页面2：推广导购员页面 3：施工人员页面
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
							var sort = [{dir:"desc",field:"s.create_time"}];
							if(options.sort!=null && options.sort.length>0 ){
								if(options.sort[0].field=="create_time"){
									options.sort[0].field="s.create_time"
								}
								
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
						width : "10%",
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
						width : "12%"    
					},
					{
						field : "phone",         
						title : "联系方式",     
						width : "20%"    
					},
					{
						field : "expand_name",         
						title : "推广员姓名",     
						width : "10%"    
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
						field : "state",         
						title : "账号状态",     
						width : "10%",
						values: [
						         { text: "正常", value: 1 },
						         { text: "关闭", value: 0 }
						       ]
					},
					{

						field : "state", 
						title : "操作",     
						width : "13%",
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
				            		var template_html = '<div class="operation k-state-default  splitButtonTwoc" ng-click="openEditAccount_win(\''+e.id+'\')">编辑</div>'
									    +'<div class="operation k-state-default text-center splitButtonTwoc" ng-click="toggleBtn('+e.id+","+e.state+')">'+toggleBtn_name+'</div>'
				            		return template_html;
				            	}else{  //如果是开发者或企业
				            		var template_html = '<div class="operation k-state-default" ng-click="openEditAccount_win(\''+e.id+'\')">查看</div>';
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
	//打开编辑弹出框
	$scope.openEditAccount_win=function(id){

		var postData = new Object();
		postData.id =id;
		curSelectUserID = id
		$http({
			   url: path+'/server/getoneseller',
			   method: 'POST',
			   data: angular.toJson(postData), 
		}).success(function(data){  
			if(data.code==0){  //成功
				var datails = data.result;
				console.log("显示详情页  ",datails.account);
				if(datails){   //有详情信息
					if(datails.head_img){
						$scope.showUploadImg("logo_img","logo_url",datails.head_img)//上传图片
					}else{
						$scope.showUploadImg("logo_img","logo_url",'../../images/icon/people.png')//上传图片
					}
					
					$scope.details.name = datails.name;//名字，
					$scope.details.account = datails.account;//账号名称
					$scope.details.phone = datails.phone;//联系方式
					$scope.details.ali_account = datails.ali_account; //密码
					$scope.editAccount_win.center().open();   //打开弹框
				}
				
			}else if(data.code==1){  //失败
				alert(data.message);
			}
		}).error(function(data){
			alert(data);   
		})
	}
	//上传图片，文件
	$scope.Upload = function(containerId,imgId,imgUrlId,type){
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
	
	//修改帐户
	$scope.editAccountOK = function(){
		var curUrl=""
		var postData = new Object();
		postData.head_img = $('#logo_url').val();
		postData.name = $scope.details.name;
		postData.ali_account = $scope.details.ali_account;
		postData.phone = $scope.details.phone;
		postData.seller_type = curPromoterRole;
		//postData.partner_id = userInfo.id;
		postData.id = curSelectUserID
		console.log("postData.head_img  ",angular.toJson(postData))
		curUrl = path+'/server/modifyseller'
		
			$http({
				   url: curUrl,
				   method: 'POST',
				   data: angular.toJson(postData)
			}).success(function(data){  
				if(data.code==0){  //成功
					$scope.editAccount_win.center().close();   //关闭弹框
					$scope.search();
					
				}else if(data.code==1){  //失败
					alert(data.message);
				}
			}).error(function(data){
				alert(data);   
			})	
	}
	
	//推广数据
	$scope.promoteDataBtn = function(id){
		console.log("sdfsdfdsffsdsf");
		var params = {curUserID:id,promotionOrInstall:1};//1.导购 2.施工
		$location.path("/promotionData/"+angular.toJson(params));
	}
	//上传展示图片
	$scope.Upload("logo","logo_img","logo_url",1);		//案例列表-首图			
	//初始化关闭状态
	$scope.initStateStatus()
	//初始化搜索条件input  如果是开发者或企业，不能点击上传图片按钮，必须最后运行，才能触发上传按钮不能用的
	$scope.initsearchIput();   
	
	//导出
	$scope.dataExport=function(){
		var sort = [{dir:"desc",field:"s.create_time"}];
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
	    my_input.attr('value', "daogou_module");  
	    
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
		var grid = $("#gridPromtion").data("kendoGrid");
		publicService.dataCheckboxExport(grid,"推广导购员列表");
	}
	//选中本页全部产品
	$scope.checkAll=function(is){
		var grid = $("#gridPromtion").data("kendoGrid");
		if(is==false){
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=false;});
		}else{
			$.each(grid.tbody.find("input"), function(index, obj){obj.checked=true;});
		}
	}
});