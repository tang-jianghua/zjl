App.controller("feedbackContentCtrl",function($scope, $rootScope, $location, $http, $compile, path) {
	
	$scope.form = {};   //账户添加表单

	$scope.mainGridOptions = {
			dataSource : {
				transport : {
					read : {
						url : path+"/server/queryfeedback",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					},
					parameterMap : function(options, operation) {
						var sort = [{dir:"desc",field:"create_date"}];
						if(options.sort!=null && options.sort.length>0 ){
							sort=options.sort;
						}
						
						if (operation == "read") {
							var parameter = {
								page : options.page, //当前页
								pageSize : options.pageSize,
								param:{
									name: $scope.search.userName,	 		//姓名
									phone_num: $scope.search.phone_num,	 	//联系方式
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
		        
		        console.log("【Grid 数据绑定成功】");
		    	$('.fancybox').fancybox();
		    },
			columns : [
					{    
						title : "序号",
						field : "id", 
						width : "40px",
						template:"<input type='checkbox' id='#: id #' value='#: id #' />"+"<span class='row-number subTitle '></span>"
					},
					{
						field : "name",         
						title : "昵称",     
						width : "80px"    
					},
					{
						field : "phone_num",         
						title : "联系方式",     
						width : "80px"    
					},
					{
						field : "gender",         
						title : "性别",     
						width : "40px",
						values: [
						         { text: "女", value: true },
						         { text: "男", value: false }
						       ]
					},
					{
						field : "create_date",         
						title : "反馈时间",     
						width : "80px"    
					},
					{
						field : "content",         
						title : "反馈内容",     
						width : "150px"    
					},
					{
						field : "img_url",         
						title : "图片信息",     
						width : "150px",
						template:function(e){
							var html = '';
							
							if(e.img_url){
								var imgArr = e.img_url.split(",");
								
								html += '<p>';
								$.each(imgArr, function(index, OneObj){
									html += ('<a class="fancybox img_info" href="'+OneObj+'" data-fancybox-group="gallery_'+e.id+'" title="">'
											+'<img src="'+OneObj+'" class="imgInfo"/>'
										+'</a>');
								});
								html += '</p>';
							}
							
							return html;
						}
					},
					{
						field : "feedbacker_type",         
						title : "反馈类型",     
						width : "60px",
						values: [
						         { text: "消费者", value: 1 },
						         { text: "推广员", value: 2 },
						         { text: "施工员", value: 3 }
						       ]
					},
					/*{
						field : "id",         
						title : "操作",     
						width : "100px",
						template : "<div  value='消费记录' class='operation k-state-default' ng-click='consumeList(\"#: id #\")' />消费记录</div>"
					}*/
			]
		};
	
	//查询
	$scope.search = function(){
		$scope.grid.dataSource.page(1);
	}
	
	//导出
	$scope.dataExport = function(){
		alert("导出！");
	}
	
	//送优惠券
	$scope.sendCoupon = function(){
		alert("送优惠券！");
	}
	
	//送红包
	$scope.sendRedPackage = function(){
		alert("送红包！");
	}
	
	//消费记录
	$scope.consumeList = function(id){
		alert("消费记录！");
	}
	
	
	

	
	

});