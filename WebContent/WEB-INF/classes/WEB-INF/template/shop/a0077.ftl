<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>普通预约通知</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="kaixinbao" />
<style type="text/css">
p{font-size:14px;}
td{width:150px;height:20px;}
</style>
</head>
<body>
<p style="font-weight:bold">普通预约通知！</p>
<br>
<table border="1" cellSpacing="0" cellPadding="0">
<tr>
								<td width="15%"><b>客户姓名</b></td>
								<td width="15%"><b>联系方式</b></td>
								<td width="20%"><b>创建日期</b></td>
								<td width="25%"><b>所在地区</b></td>
								<td width="25%"><b>预约产品</b></td>
</tr>
<tr>
								<td>${customerName}</td>
								<td>${customerTel}</td>
								<td>${createDate}</td>
								<td>${areaShow}</td>
								<td>${productName}</td>
</tr>
</table>
</body>
</html>