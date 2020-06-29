<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%  
String path=application.getRealPath(request.getRequestURI());  
String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);   
%> 
<html>
<head>
<meta charset="UTF-8" >
<title>删除品牌产品</title>
<script type="text/javascript" src="plugins/jquery/jquery.min.js"></script>
</head>
<body style="width:850px;height:1000px;margin: auto;">
	<div style="width:800px;height:60px;">
		<table style="margin: auto;padding-top: 20px;">
			<tr>
				<td width="10%">标题名称</td>
				<td width="80%"><input type="text" id="deleteName" style="width:100%;height:30px;"></td>
				<td><input type="button" style="width:80px;height:30px;" value="查询" onclick="loadTable()"></td>
			</tr>
		</table>
	</div>
	<div style="width:800px;overflow-y:scroll;height:800px;">
		<table  style="border:3px solid #000033;">
			<thead>
				<tr>
					<th style="width:100px;">id</th>
					<th style="width:600px;">标题名称</th>
					<th style="width:100px;">操作</th>
				</tr>
			</thead>
			<tbody id="tbodyHtml">
				
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	var path='<%=realPath1%>';
	$(function(){
		loadTable();
	});
	function loadTable(){
		var product_title=$("#deleteName").val();
		var data={"product_title":product_title};
		$.ajax({
			url : path+"admin/allproducts",
			dataType : "json",
			contentType: "application/json; charset=utf-8",
			type : "POST",
			data:JSON.stringify(data),
			success : function(data) {
				$("#tbodyHtml").html("")
				for (var i = 0; i < data.length; i++) {
					html='<tr>'+
						'<td>'+data[i].id+'</td>'+
						'<td>'+data[i].product_title+'</td>'+
						'<td><input type="button" value="删除" style="width:80px;height:30px;" onclick="deletePrduct(\''+data[i].id+'\')"></td>'+
					'</tr>'
					$("#tbodyHtml").append(html);
				}
			}
		});
	}
	//admin/deleteproductbyid/{id}
	function deletePrduct(id){
		if(confirm("是否确认删除")){
			$.ajax({
				url : path+"admin/deleteproductbyid/"+id,
				dataType : "json",
				contentType: "application/json; charset=utf-8",
				type : "GET",
				success : function(data) {
					console.log(data);
					loadTable();
				}
			});
		}
	}
</script>
</html>