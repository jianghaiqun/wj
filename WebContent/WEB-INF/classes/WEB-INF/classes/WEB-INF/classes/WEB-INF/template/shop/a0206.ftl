<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>赠送积分告警邮件</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="kaixinbao" />
<style type="text/css">
p{font-size:14px;}
td{width:150px;height:20px;}
</style>
</head>
<body>
<p style="font-weight:bold">赠送积分告警邮件</p>
<table border="1" cellpadding="0" cellspacing="0">
	<tr>
		<td>积分赠送轨迹id</td> 	
		<td>订单号</td> 	
		<td>实际支付保费</td> 	
		<td>会员帐号</td> 	
		<td>积分</td> 	
		<td>订单创建时间</td> 	
 	</tr>
 	
 <#list content as list>
 	<tr>
		<td>${list.id}</td> 	
		<td>${list.ordersn}</td> 	
		<td>${list.payprice}</td> 	
		<td>${list.membercode}</td> 	
		<td>${list.point}</td> 	
		<td>${list.createdate}</td> 	
 	</tr>
 </#list>
 </table>
</body>
</html>