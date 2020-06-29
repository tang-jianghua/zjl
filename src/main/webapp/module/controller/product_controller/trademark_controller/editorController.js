App.controller("editorController",function($scope, $rootScope, $location, $http, $compile, $stateParams, path) {
	$scope.formEditor={};
	var url="";
	
	//上传图片
	$scope.Upload = function(containerId,imgId,imgUrlId,w,h){
		$("#"+containerId).kendoUpload({
	        async: {
	            saveUrl: path+"/common/updoad",
	            removeUrl: path+"/remove",
	           // autoUpload: false
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
	        	//console.log('成功：',e.response);
	        	url = e.response.result;   //图片路径
	        	$scope.showUploadImg(imgId,imgUrlId,url,w,h);
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
	$scope.showUploadImg = function(imgId,imgUrlId,url,w,h){
		$("#"+imgId).css({
			"width":w,
    		"height":h,
    		"background": "url('"+url+"') no-repeat",
    	    "background-size":"100% 100%"
    	});
		$('#'+imgUrlId).val(url);
	}
	//初始化图文编辑器
	$scope.initEditor = function(editorId){
		curEditor = "#"+editorId
		$("#"+editorId).kendoEditor({
			  value: "",
			  encoded: false,
			  tools: [
					"formatting",
					"bold", "italic", "underline",
					"justifyLeft", "justifyCenter", "justifyRight",
					"insertUnorderedList", "insertOrderedList", "indent", 
					"createTable",
					{
						name: "custom",                       
                          exec:function(e)
                          {
                        	  $scope.addImageWindow.center().open();   //打开弹框
                          }
                      }
                 ],
                
                keydown: function(e) {
                    $(".k-editable-area").tooltip('destroy');
                }
		});
		
	}
	//确定上传
	$scope.addImgToEditor = function(){
		var editor = $(curEditor).data("kendoEditor");
		var para=document.createElement("p");
		var result = '<img src="'+url+'" alt="" width='+$scope.formEditor.imgWidth+' height='+$scope.formEditor.imgHeight+'/>';   
		var div = document.createElement('div');
		div.innerHTML = result;  
		editor.body.appendChild(div);
		$scope.addImageWindow.center().close();   //关闭弹框
	}
	//关闭弹出框
	$scope.closeImgToEditor = function(){
		$scope.addImageWindow.center().close();   //关闭弹框
	}
	$scope.Upload("logo1","logo1_img","logo1_url");		//上传图片
	$scope.initEditor("editor");
});