<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.wap.ProductManage.initLimitProduct">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title></title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body class="dialogBody">
	
	
	<form id="form2">
<table width="100%" height="123" cellpadding="3" cellspacing="0">

	<tr>
		<td>&nbsp;&nbsp;&nbsp;产品名称 : </td>
		<td>${productName}</td>  
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td >&nbsp;&nbsp;&nbsp;限时优惠 : </td>
		<td> 		
			 <z:select name='Preferential' id='Preferential' style="width:50px;">
			 	<option value="Y">- Y -</option>
				<option value="N">- N -</option>
			 </z:select>&nbsp;
		</td>
		<td></td>
		<td></td>
		
	</tr>
	<tr>
		<td >优惠日期从 : </td>
		<td ><input type="text" id="StartDate" style="width:100px; " ztype='date' value = "${StartDate}"/> 
		</td>
		<td >至 : </td>
		<td >
		 <input type="text" id="EndDate" style="width:100px; " ztype='date'  value = "${EndDate}"/> 
		</td>
	</tr>
	<tr>
		<td >优惠时间从 : </td>
		<td >
		<input type="text" id="StartTime" style="width:100px; " ztype='time' value = "${StartTime}"/> 
		</td>
		<td >至 : </td>
		<td >
		<input type="text" id="EndTime" style="width:100px; " ztype='time'  value = "${EndTime}"/> 
		</td>
	</tr>
	<input type="hidden" id="productId"  name ="productId"  value = "${productId}"/> 
</table>
</form>
	</body>
	</html>
</z:init>
