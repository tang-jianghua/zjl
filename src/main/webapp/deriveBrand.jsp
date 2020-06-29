<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%  
String path=application.getRealPath(request.getRequestURI());  
String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);   
%> 
<html>
<head>
<meta charset="UTF-8" >
<title>产品导入</title>
<script type="text/javascript" src="plugins/jquery/jquery.min.js"></script>
</head>
<body style="width:850px;height:1000px;margin: auto;">
	<div style="width:500px;height: 300px;border:2px solid #CCCCCC;margin-top: 100px;">
		<table style="width:100%;">
			<tr style="height:50px;">
				<td width="30%">导出品牌</td>
				<td width="70%">
					<select id="daochupinpai" style="height:50px;width:100%;">
					</select>
				</td>
			</tr>
			<tr style="height:50px;">
				<td width="30%">导入品牌</td>
				<td width="70%">
					<select id="daorupinpai" style="height:50px;width:100%;">
						
					</select>
				</td>
			</tr>
			<tr style="height:50px;">
				<td width="30%">品类</td>
				<td width="70%">
					<select id="pinlei" style="height:50px;width:100%;">
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="button"  style="height:40px;margin-left:150px;margin-top:30px;" value="确定导入" onclick="tijiao()"></td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	var path='<%=realPath1%>';
	$(function(){
		getAllBrandName();
		getBuildclassentities();
	});
	
	function getAllBrandName(){
		$.ajax({
			url : path+"/server/getAllBrandName",
			dataType : "json",
			contentType: "application/json; charset=utf-8",
			type : "GET",
			success : function(data) {
				console.log(data);
				for (var i = 0; i < data.result.length; i++) {
					var html='<option value="'+data.result[i].id+'">'+data.result[i].principal+'</option>';
					$("#daochupinpai").append(html);
					$("#daorupinpai").append(html);
				}
				
			}
		});
	}
	function getBuildclassentities(){
		$.ajax({
			url : path+"/server/buildclassentities",
			dataType : "json",
			contentType: "application/json; charset=utf-8",
			type : "GET",
			success : function(data) {
				console.log(data);
				for (var i = 0; i < data.length; i++) {
					var html='<option value="'+data[i].id+'">'+data[i].name+'</option>';
					$("#pinlei").append(html);
				}
				
			}
		});
	}
	function tijiao(){
		var from_brandId=$("#daochupinpai").val();
		var to_brandId=$("#daorupinpai").val();
		var class_id=$("#pinlei").val();
		if(from_brandId==null||from_brandId==""){
			alert("选择导出品牌 ");
		}else if(to_brandId==null||to_brandId==""){
			alert("选择导入品牌 ");
		}else if(from_brandId==null||from_brandId==""){
			alert("选择品类 ");
		}else{
			$.ajax({
				url : path+"/server/brandtobrand",
				dataType : "json",
				contentType: "application/json; charset=utf-8",
				type : "POST",
				data:JSON.stringify({from_brandId:from_brandId,to_brandId:to_brandId,class_id:class_id}),
				success : function(data) {
					console.log(data);
				}
			});
		}
		
	}
	
</script>
</html>