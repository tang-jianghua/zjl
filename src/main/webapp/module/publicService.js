/*
 * angularjs公共服务
 * */

App.factory('publicService', function($rootScope, $http, $compile, $timeout, path) {
	var FUNC_Object = {};    //函数对象
	
	
	//获取当前时间
	FUNC_Object.getCurrentTime = function(type){    
		var myDate = new Date();
		
		var year = myDate.getFullYear();
		var month = myDate.getMonth();
		month += 1;
		if(month<10){
			month="0"+month;
		}
		
		var day = myDate.getDate();
		if(day<10){
			day="0"+day;
		}
		
		var hour = myDate.getHours();
		if(hour<10){
			hour="0"+hour;
		}
		
		var minute = myDate.getMinutes();
		if(minute<10){
			minute="0"+minute;
		}
		
		var second = myDate.getSeconds();
		if(second<10){
			second="0"+second;
		}
		
		var time = '';
		if(type=="date"){
			time = year+"-"+month+"-"+day;
		}else if(type=="time"){
			time = hour+":"+minute+":"+second;
		}else if(type=="dateTime"){
			time = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
		}else{
			time = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
		}
		
		return time;
	};
   
   	//获取时间间隔
	FUNC_Object.getTimeRange = function(startTime,endTime,returnType){ 
		var timeRange = null;	//时间间隔
		var startDate = new Date(startTime);	//开始日期
		var endDate = new Date(endTime);		//结束日期
		
		if(returnType=="day"){
			timeRange = (startDate.getTime()-endDate.getTime())/1000/60/60/24;
		}else if(returnType=="hour"){
			timeRange = (startDate.getTime()-endDate.getTime())/1000/60/60;
		}else if(returnType=="minute"){
			timeRange = (startDate.getTime()-endDate.getTime())/1000/60;
		}else if(returnType=="second"){
			timeRange = (startDate.getTime()-endDate.getTime())/1000;
		}
		
		return Math.abs(timeRange);
	}
   
   
   	//初始化时间控件(年月日，时分秒)
	FUNC_Object.initDateTime = function(containerId){    
		$("#"+containerId).kendoDateTimePicker({
 		     value: "",
 		     culture: "de-DE",
 		     format: "yyyy-MM-dd HH:mm:ss",   //事件格式
 		     interval: 30,   				  //时间间隔
 		     timeFormat: "HH:mm" 			  //24 hours format
 		});
	};
   
   	//初始化时间控件(年月日)
	FUNC_Object.initDate = function(containerId){    
		$("#"+containerId).kendoDatePicker({
 		     value: "",
 		     culture: "de-DE",
 		     format: "yyyy-MM-dd"    //事件格式
 		});
	};
	
	//初始化时间控件(时分秒)
	FUNC_Object.initTime1 = function(containerId){    
		$("#"+containerId).kendoTimePicker({
  		     value: "",
  		     culture: "de-DE",
  		     format: "HH:mm:ss",   //事件格式
  		     interval: 30,   				  //时间间隔
  		     timeFormat: "HH:mm" 			  //24 hours format
  		});
   };
   
   //初始化时间控件(时分)
	FUNC_Object.initTime2 = function(containerId){    
		$("#"+containerId).kendoTimePicker({
 		     value: "",
 		     culture: "de-DE",
 		     format: "HH:mm",   //时间格式
 		     interval: 30,   				  //时间间隔
 		     timeFormat: "HH:mm" 			  //24 hours format
 		});
	};
	
   /******************************************省---市---区（三级联动）【begin】******************************************/
	//type:1	 区单选
	//type:2	 区多选
	
   //初始化省
   FUNC_Object.initProvince = function(type){
		$("#province").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "code",
		    placeholder: "省",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/province",
	                  dataType : "json"
	              }
	          },
	          schema : {
					data : function(d) {
						return d.result;
					}
				},
	      },
	      change: function(e) {
	    	    var value = this.value();
	    	    if(!isNaN(Number(value))){
	    	    	FUNC_Object.getCityData(type,value);
	    	    }
	      }
		});
	};
   
	//获取市数据
	FUNC_Object.getCityData = function(type,provinceCode,defaultVal){
		$http({
			   url: path+"/server/city/"+provinceCode,
			   method: 'GET'
		}).success(function(data){
			FUNC_Object.initCity(type,data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	};
	
	//初始化市
	FUNC_Object.initCity = function(type,cityData,defaultVal){
		if(cityData){
			var plugObj_city = $("#city").data("kendoComboBox");
			plugObj_city.value("");
			plugObj_city.setDataSource(cityData);
			if(defaultVal){
				plugObj_city.value(defaultVal);
			}
			
			//区（值清空）
			var plugObj_area = null;
			if(type==1){		//区（单选）
				plugObj_area = $("#area").data("kendoComboBox");
			}else if(type==2){	//区（多选）
				plugObj_area = $("#area").data("kendoMultiSelect");
			}
			plugObj_area.value("");
			plugObj_area.setDataSource([]);
		}else{
			$("#city").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "code",
			    placeholder: "市",
			    filter: "contains",
			    dataSource: [],
			    change: function(e) {
		    	    var value = this.value();
		    	    if(!isNaN(Number(value))){
		    	    	FUNC_Object.getAreaData(type,value);
		    	    }
		      }
			});
		}
	}
	
	//获取区数据
	FUNC_Object.getAreaData = function(type,cityCode,defaultVal){
		$http({
			   url: path+"/server/county/"+cityCode,
			   method: 'GET'
		}).success(function(data){
			FUNC_Object.initArea(type,data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	};

	//初始化区
	FUNC_Object.initArea = function(type,areaData,defaultVal){
		if(areaData){
			var plugObj_area = null;
			if(type==1){		//区（单选）
				plugObj_area = $("#area").data("kendoComboBox");
			}else if(type==2){	//区（多选）
				plugObj_area = $("#area").data("kendoMultiSelect");
			}
			
			plugObj_area.value("");
			plugObj_area.setDataSource(areaData);
			if(defaultVal){
				plugObj_area.value(defaultVal);
			}
		}else{
			if(type==1){		//区（单选）
				$("#area").kendoComboBox({
      				  dataTextField: "name",
      				  dataValueField: "code",
      				  placeholder: "区",
      				  filter: "contains",
      				  dataSource: []
      			});
			}else if(type==2){	//区（多选）
				$("#area").kendoMultiSelect({
      				  dataTextField: "name",
      				  dataValueField: "code",
      				  placeholder: "区",
      				  filter: "contains",
      				  dataSource: []
      			});
			}
		}
	};
	/******************************************省---市---区（三级联动）【end】******************************************/
	
	
	/******************************************行业---品类---品牌（三级联动）【begin】******************************************/
	//初始化【行业】
   FUNC_Object.initIndustry = function(){
		$("#industry").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    placeholder: "请选择",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/buildmaterialslist",
	                  dataType : "json"
	              }
	          }
	      },
	      change: function(e) {
	    	    var value = this.value();
	    	    if(!isNaN(Number(value))){
	    	    	FUNC_Object.getClassData(value);
	    	    }
	      }
		});
	};
   
	//获取【品类】数据
	FUNC_Object.getClassData = function(industryValue,defaultVal){
		$http({
			   url: path+"/server/buildclassentitiesByBuildId/"+industryValue,
			   method: 'GET'
		}).success(function(data){
			FUNC_Object.initClass(data,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	};
	
	//初始化【品类】
	FUNC_Object.initClass = function(classData,defaultVal){
		if(classData){
			var plugObj_class = $("#class").data("kendoComboBox");
			plugObj_class.value("");
			plugObj_class.setDataSource(classData);
			if(defaultVal){
				plugObj_class.value(defaultVal);
			}
			
			//品牌（值清空）
			var plugObj_brand = $("#brand").data("kendoComboBox");
			plugObj_brand.value("");
			plugObj_brand.setDataSource([]);
		}else{
			$("#class").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "id",
			    placeholder: "请选择",
			    filter: "contains",
			    dataSource: [],
			    change: function(e) {
		    	    var value = this.value();
		    	    if(!isNaN(Number(value))){
		    	    	FUNC_Object.getBrandData(value);
		    	    }
		      }
			});
		}
	}
	
	//获取【品牌】数据
	FUNC_Object.getBrandData = function(brandCode,defaultVal){
		$http({
			   url: path+"/server/buildenterpriselistByClassId/"+brandCode,
			   method: 'GET'
		}).success(function(data){
			FUNC_Object.initBrand(data,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	};

	//初始化【品牌】
	FUNC_Object.initBrand = function(brandData,defaultVal){
		if(brandData){
			var plugObj_brand = $("#brand").data("kendoComboBox");
			plugObj_brand.value("");
			plugObj_brand.setDataSource(brandData);
			if(defaultVal){
				plugObj_brand.value(defaultVal);
			}
		}else{
			$("#brand").kendoComboBox({
				  dataTextField: "name",
				  dataValueField: "id",
				  placeholder: "请选择",
				  filter: "contains",
				  dataSource: []
			});
		}
	};
	/******************************************行业---品类---品牌（三级联动）【end】******************************************/
	

	/******************************************分类---系列（二级联动）【begin】******************************************/
	//初始化【分类】
   FUNC_Object.initClassify = function(){
		$("#classify").kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    placeholder: "请选择",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/brand/queryclassifies",
	                  dataType : "json"
	              }
	          },
	          schema : {
					data : function(d) {
						return d.result;
					}
	          },
	      },
	      change: function(e) {
	    	    var value = this.value();
	    	    if(!isNaN(Number(value))){
	    	    	FUNC_Object.getSeriesData(value);
	    	    }
	      }
		});
	};
	
	//获取【系列】数据
	FUNC_Object.getSeriesData = function(classifyValue,defaultVal){
		$http({
			   url: path+"/server/brand/brandserieslistbyclassify/"+classifyValue,
			   method: 'GET'
		}).success(function(data){
			FUNC_Object.initSeries(data.result,defaultVal);
		}).error(function(data){
			console.log(data);   
		})
	};
	
	//初始化【系列】
	FUNC_Object.initSeries = function(classifyData,defaultVal){
		if(classifyData){
			var plugObj_class = $("#series").data("kendoComboBox");
			plugObj_class.value("");
			plugObj_class.setDataSource(classifyData);
			if(defaultVal){
				plugObj_class.value(defaultVal);
			}
		}else{
			$("#series").kendoComboBox({
			    dataTextField: "name",
			    dataValueField: "id",
			    placeholder: "请选择",
			    filter: "contains",
			    dataSource: []
			});
		}
	}
	
	/******************************************分类---系列（二级联动）【end】******************************************/
	
	//初始化【生产品类】
   FUNC_Object.initAllClass = function(containerId){
		$("#"+containerId).kendoComboBox({
		    dataTextField: "name",
		    dataValueField: "id",
		    placeholder: "请选择",
		    filter: "contains",
		    dataSource: {
	          transport: {
	              read: {
	            	  type : 'GET',
	                  url: path+"/server/buildclassentities",
	                  dataType : "json"
	              }
	          }
	      }
		});
	};

	//上传图片（单个）
	FUNC_Object.uploadImage = function(containerId,imgId,imgUrlId,limitParam){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/updoad"
	        },
	     	multiple: false,      //多选
	      	showFileList: false,  //显示文件列表
	        localization: {
	              select: "请上传图片",
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
	        	var url = e.response.result;   //图片路径
	        	FUNC_Object.showUploadImage(imgId,imgUrlId,url);
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
	        		if(limitParam.imageSize && image.size>(limitParam.imageSize*1024)){
		        		alert("上传图片的大小请限制在【"+limitParam.imageSize+"Kb】以内！");
		        		e.preventDefault();
		        		return;
		        	}
	        		if(limitParam.format && limitParam.format.indexOf(image.extension.substr(1,image.extension.length).toLowerCase())==-1){
		        		alert("上传图片仅支持【"+limitParam.format+"】格式！");
		        		e.preventDefault();
		        		return;
		        	}
	        	}
	        }
	    });
	};
	
	//上传图片（多个）
	FUNC_Object.uploadMultipleImage = function(containerId,imgId,imgUrlId){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/uploads",
	            batch: true
	        },
	     	multiple: true,      //多选
	      	showFileList: false,  //显示文件列表
	        localization: {
	              select: "请上传图片",
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
	        	var url = e.response.result;   //图片路径
	        	//FUNC_Object.showUploadImage(imgId,imgUrlId,url);
		    },
	        error: function(e){
	        	//alert('失败！');
	        },
	        remove: function(e){
	        	//alert('移除！');
	        }
	    });
	};
	
	//反显图片并赋值
	FUNC_Object.showUploadImage = function(imgId,imgUrlId,url){
		if(url && url!="undefined"){
			$("#"+imgId).css({
				"background": "url('"+url+"') no-repeat",
	    	    "background-size":"100% 100%",
	    	    "background-color":"white"
	    	});
			if(imgUrlId){
				$('#'+imgUrlId).val(url);
			}
		}else{
			$("#"+imgId).css({
				"background": "url('') no-repeat"
	    	});
			$('#'+imgUrlId).val('');
		}
	};
	
	//移除图片
	FUNC_Object.removeUploadImage = function(imgId,imgUrlId){
		$("#"+imgId).removeAttr("style");
		$('#'+imgUrlId).val("");
	};
	
	
	/*******************************图文编辑器---支持本地图片上传(begin)*******************************/
	//上传图片Id:localPicture; 反显图片Id：localPicture_img;  图片路径Id：localPicture_url  (保证唯一)
	//图片宽度Id:localPicture_width 
	//var showEditor = "#editor";  //显示的编辑器
	
	//初始化图文编辑器
	FUNC_Object.initEditor = function(editorId){
		showEditor = "#"+editorId;  //显示的编辑器
		$("#"+editorId).kendoEditor({
			  value: "",
			  encoded: false,
			  resizable: {
				   min: 500
			  },
			  tools: [
                      "formatting",
                      "fontName", "fontSize", "foreColor", "backColor",
                      "bold", "italic", "underline",
                      "justifyLeft", "justifyCenter", "justifyRight",
                      "insertUnorderedList", "insertOrderedList", "indent", 
                      "createLink", "unlink", 
                      "createTable",
                      {
                          name: "custom",  
                          tooltip:"上传本地图片",
                          exec:function(e)
                          {
                        	  FUNC_Object.removeUploadImage("localPicture_img","localPicture_url");
                        	  $("#localPicture_width").val("");    //图片宽度
                        	  //$scope.addImageWindow.center().open();   //打开弹框
                          }
                      }
                ],
                keydown: function(e) {
                    $(".k-editable-area").tooltip('destroy');
                }
		});
	};

	//添加图片到编辑器
	FUNC_Object.addImgToEditor = function(){
		var imgUrl = $("#localPicture_url").val();    		//图片路径
		var widthValue = $("#localPicture_width").val();    //图片宽度
		
		if(imgUrl){   //有图片
			var width = "100%";
			if(widthValue && !isNaN(widthValue)){
				width = widthValue+"%";
			}

			var insertHTML = '<img src="'+imgUrl+'" width="'+width+'"  height:"initial";/>';  //高度默认值
			$(showEditor).data("kendoEditor").exec("insertHTML",{value:insertHTML});
		}
	
		//$scope.addImageWindow.close();   //关闭弹框
	};
	
	//关闭弹出框
	FUNC_Object.closeImgToEditor = function(){
		//$scope.addImageWindow.close();   //关闭弹框
	};
	
	/*******************************图文编辑器---支持本地图片上传(end)*******************************/
	
	//初始化【订单结算状态】
	FUNC_Object.initOrderClearState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: "100000",
			 				text: "生成"
			             },
			             {
			            	 id: "200000",
			 				text: "商家已确认"
			             },
			             {
			            	 id: "300000",
			 				text: "平台已确认"
			             },
			             {
			            	 id: "400000",
			 				text: "已支付结算"
			             },
			             {
			            	 id: "500000",
			 				text: "完成"
			             },
			             {
			            	 id: "600000",
			 				text: "拒绝"
			             }
            ]
		});
	}
	
	//初始化【订单状态】
	FUNC_Object.initOrderState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "value",
			dataSource: [
			             {
			            	text: "请选择",
			 				value: ""
			             },
			             {
			            	text: "已发货",
			 				value: "1"
			             },
			             {
			            	text: "已收货",
			 				value: "2"
			             }
            ]
		});
	}
	
	//初始化【活动状态】
	FUNC_Object.initActivityState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 0,
			 				text: "未开始"
			             },
			             {
			            	 id: 1,
			 				text: "进行中"
			             },
			             {
			            	 id: 2,
			 				text: "已结束"
			             }
            ]
		});
	}
	
	//初始化【活动性质】
	FUNC_Object.initActivityProperty = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "免费"
			             },
			             {
			            	 id: 2,
			 				text: "收费"
			             }
            ]
		});
	}
	
	//初始化【活动类型】
	FUNC_Object.initActivityType = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 0,
			 				text: "秒杀活动"
			             },
			             {
			            	 id: 1,
			 				text: "满额折扣"
			             },
			             {
			            	 id: 2,
			 				text: "满减折扣"
			             },
			             {
			            	 id: 3,
			 				text: "数量折扣"
			             }
            ]
		});
	}
	
	//初始化【折扣活动类型】
	FUNC_Object.initDiscountActivityType = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "满额折扣"
			             },
			             {
			            	 id: 2,
			 				text: "满减折扣"
			             },
			             {
			            	 id: 3,
			 				text: "数量折扣"
			             }
            ]
		});
	}
	
	//初始化【折扣类型】
	FUNC_Object.initDiscountType = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
		    value: 1,
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "统一折扣"
			             }
            ]
		});
	}
	
	//初始化【折扣状态】
	FUNC_Object.initDiscountState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 0,
			 				text: "未开始"
			             },
			             {
			            	 id: 1,
			 				text: "进行中"
			             },
			             {
			            	 id: 2,
			 				text: "已结束"
			             }
            ]
		});
	}
	
	//初始化【活动种类】
	FUNC_Object.initActivityClass = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "联盟"
			             },
			             {
			            	 id: 2,
			 				text: "秒杀"
			             }
            ]
		});
	}
	
	//初始化【报名状态】
	FUNC_Object.initSignState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: "",
			 				text: "请选择"
			             },
			             {
			            	 id: 0,
			 				text: "未报名"
			             },
			             {
			            	 id: 1,
			 				text: "已报名"
			             }
            ]
		});
	}
	
	//初始化【付款模式】
	FUNC_Object.initpayModel = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "id",
			dataSource: [
			             {
			            	 id: null,
			 				text: "请选择"
			             },
			             {
			            	 id: 1,
			 				text: "全款"
			             },
			             {
			            	 id: 2,
			 				text: "定金"
			             }
            ]
		});
	}
	
	//初始化【预约状态】
	FUNC_Object.initBookingState = function(containerId){
		$("#"+containerId).kendoDropDownList({
			dataTextField: "text",
		    dataValueField: "value",
			dataSource: [
			             {
			            	value: null,
			 				text: "请选择"
			             },
			             {
			            	value: 1,
			 				text: "已提交"
			             },
			             {
			            	value: 2,
			 				text: "已确认"
			             },
			             {
			            	value: 3,
			 				text: "受理中"
			             },
			             {
			            	value: 4,
			 				text: "已完成"
			             },
			             {
			            	value: 5,
			 				text: "已取消"
			             },
			             {
			            	value: 6,
			 				text: "无法受理"
			             }
			             
			             
            ]
		});
	}
	
	//初始化【企业名称】
	FUNC_Object.initEnterpriseName = function(containerId){
		$("#"+containerId).kendoAutoComplete({
			placeholder: "输入名称／首字母搜索",
		    filter: "contains",
	        dataSource: {
	        	transport : {
					read : {
						url : path+"/server/enterprisename",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					}
	        	}
        	}
	    });
	}
	
	//初始化【合伙人名称】
	FUNC_Object.initPartnerName = function(containerId){
		$("#"+containerId).kendoAutoComplete({
			placeholder: "输入名称／首字母搜索",
		    filter: "contains",
	        dataSource: {
	        	transport : {
					read : {
						url : path+"/server/partnername",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					}
	        	}
        	}
	    });
	}
	
	//初始化【品牌名称】
	FUNC_Object.initBrandName = function(containerId){
		$("#"+containerId).kendoAutoComplete({
			placeholder: "输入名称／首字母搜索",
		    filter: "contains",
	        dataSource: {
	        	transport : {
					read : {
						url : path+"/server/brandname",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					}
	        	}
        	}
	    });
	}
	
	//初始化【店铺名称】
	FUNC_Object.initShopName = function(containerId){
		$("#"+containerId).kendoAutoComplete({
			placeholder: "输入名称／首字母搜索",
		    filter: "contains",
	        dataSource: {
	        	transport : {
					read : {
						url : path+"/server/shopname",
						type : 'POST',
						dataType : "json",
						contentType : "application/json"
					}
	        	}
        	}
	    });
	}

	//验证手机号
	FUNC_Object.checkPhoneNumber = function(value){
		var phoneStr = /^1[3|4|5|7|8]\d{9}$/; 
		if(phoneStr.test(value)){
			return true;
		}else{
			return false;
		}
	}
	
	//验证固定电话
	FUNC_Object.checkTellNumber = function(value){
		var tellStr = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/; 
		if(tellStr.test(value)){
			return true;
		}else{
			return false;
		}
	}
	
	//验证邮箱
	FUNC_Object.checkMailbox = function(value){
		var mailboxStr = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/; 
		//var mailboxStr = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;  
		if(mailboxStr.test(value)){
			return true;
		}else{
			return false;
		}
	}
	
	//验证是否包含汉字
	FUNC_Object.checkIsContainChinese = function(value){
		var str = /.*[\u4e00-\u9fa5]+.*$/;  
		if(str.test(value)){
			return true;
		}else{
			return false;
		}
	}
	
	//验证是否是正整数
	FUNC_Object.checkIsUpNumber = function(value){
		var str = /^[0-9]*[1-9][0-9]*$/;  
		if(str.test(value)){
			return true;
		}else{
			return false;
		}
	}
	
	//验证是否是浮点数
	FUNC_Object.checkIsFloat = function(value){
		var str = /^(- \\d+)(\\.\\d+) $/;  
		if(str.test(value)){
			return true;
		}else{
			return false;
		}
	}
	
	//验证两个时间
	FUNC_Object.checkTwoTime = function(startTime,endTime){
		if(startTime<=endTime){
			return true;
		}else{
			return false;
		}
	}
	
	//验证两个时间段是否有交集
	FUNC_Object.checkTwoTimeRangeIsCross = function(params){
		if((params.start_1<=params.end_1 && params.start_2<=params.end_2 && (params.end_1<=params.start_2 || params.end_2<=params.start_1))){
			return true;
		}else{
			return false;
		}
	}
	
	//根据样式全选
	FUNC_Object.chooseAllByClassName = function(className,isAll){
		var checkBoxArr = $("."+className);
		
		$.each(checkBoxArr, function(index, OneObj){
			if(isAll){
				OneObj.checked = true;
			}else{
				OneObj.checked = false;
			}
		});
	}
	
	//根据样式获取选中的值
	FUNC_Object.getChooseValueByClassName = function(className){
		var idArr = new Array();
		var checkBoxArr = $("."+className);
		
		$.each(checkBoxArr, function(index, OneObj){
			var isChoose = OneObj.checked;
			if(isChoose){
				var value = OneObj.value;
				if(value){
					idArr.push(value);
				}
			}
		});
		
		return idArr;
	}
	
	//表格合并
	FUNC_Object.tableTdRowspan = function(gridName,mergeTdArr){
		var tableTrObj = $("div[kendo-grid='"+gridName+"']").children("div:eq(1)").children("table").children("tbody").children("tr");
		var trLength = $(tableTrObj).length;
		
		$.each(mergeTdArr,function(index_td,obj_td){
			var whichTd = obj_td;	//合并列
			
			var rowspanCount = 1;
			var groupTrIndex = 0;	//分组行下标
			var tdText = "";  		//合并表格内容
			var trIndex = 0;		//合并行
			
			$.each(tableTrObj,function(index,obj){
				var roleName = $(this).attr("role");
				if(roleName=="row"){	//分组标题行
					if(rowspanCount>1){
						$(tableTrObj[trIndex]).children("td:eq("+whichTd+")").attr("rowspan",rowspanCount);
						rowspanCount = 1;
						tdText = nowTdText;
						trIndex = index;
					}
					
					groupTrIndex = index;
					rowspanCount = 1;
				}else{
					var nowTdText = $(this).children("td:eq("+whichTd+")").text();   //当前行td的文本值
					
					if(index==(groupTrIndex+1)){	//分组数据【第一条记录】
						tdText = nowTdText;
						trIndex = index;
					}else{
						if(nowTdText==tdText){   
							$(this).children("td:eq("+whichTd+")").remove();
							rowspanCount ++;
							if(index==(trLength-1)){   //最后一行
								$(tableTrObj[trIndex]).children("td:eq("+whichTd+")").attr("rowspan",rowspanCount);
							}
						}else{
							$(tableTrObj[trIndex]).children("td:eq("+whichTd+")").attr("rowspan",rowspanCount);
							
							rowspanCount = 1;
							tdText = nowTdText;
							trIndex = index;
						}
					}
				}
			});
		});	
	}

	//生成链接地址
	FUNC_Object.buildLinkUrl = function(type,id){
		var url = 'http://www.kaolaj.com/index.html?link_type='+type+'&data_param='+id;
		return url;
	}
	
	//实例化树形控件
	FUNC_Object.initZtree = function(ztreeId,dataArr){
		var zTree = new ZtreeTool(ztreeId);
		zTree.setParam();
		zTree.showTree(dataArr);
		
		return zTree;
	}
	
	//根据样式获取选中的值
	FUNC_Object.dataCheckboxExport = function(grid,fileName){
		var selectedThead = grid.thead.find("tr");
		var selectedTbody = grid.tbody.find("tr");
	    var bookData = new Array();
		for (var i = 0; i < selectedThead.length; i++) {
			var theadJson={};
			theadJson.type = "header";
			theadJson.cells = new Array();
			for (var l = 0; l < selectedThead[i].childNodes.length; l++) {
				var thJson={};
				thJson.background="#7a7a7a";
				thJson.color="#fff";
				thJson.value=selectedThead[i].childNodes[l].innerText;
				thJson.colSpan=1;
				thJson.rowSpan=1;
				theadJson.cells.push(thJson);
			}
			bookData.push(theadJson);
		}
		for (var i = 0; i < selectedTbody.length; i++) {
			var tbodyJson={};
			tbodyJson.type = "data";
			tbodyJson.cells = new Array();
			if(grid.tbody.find("input")[i].checked==true){
				for (var l = 0; l < selectedTbody[i].childNodes.length; l++) {
					tbodyJson.cells.push({value:selectedTbody[i].childNodes[l].innerText});
				}
				bookData.push(tbodyJson);
			}
		}
		
		
		
	    var excel = grid.options.excel || {};
	    var exporter = new kendo.ExcelExporter({
	        columns: grid.columns,
	        dataSource: grid.dataSource,
	        allPages: excel.allPages,
	        filterable: excel.filterable,
	        hierarchy: excel.hierarchy
	    });
	    exporter.workbook().then($.proxy(function (book, data) {
	        if (!grid.trigger('excelExport', {
	                workbook: book,
	                data: data
	            })) 
	        {
	        	book.sheets[0].rows=bookData;
	        	book.fileName=fileName;
	            var workbook = new kendo.ooxml.Workbook(book);
	            kendo.saveAs({
	                dataURI: workbook.toDataURL(),
	                fileName: book.fileName || excel.fileName,
	                proxyURL: excel.proxyURL,
	                forceProxy: excel.forceProxy
	            });
	        }
	    }, grid));
		
	}
	
	
	
	return FUNC_Object;
});