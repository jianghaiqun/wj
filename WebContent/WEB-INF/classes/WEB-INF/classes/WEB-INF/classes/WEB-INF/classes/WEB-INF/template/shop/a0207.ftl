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
<p style="font-weight:bold">礼品兑换告警邮件</p>
<table border="1" cellpadding="0" cellspacing="0">
	<tr>
		<td>会员名</td> 	
		<td>礼品名称</td> 	
		<td>积分数</td> 	
		<td>礼品编码</td> 	
 	</tr>
 	
 	<tr>
		<td>${membername}</td> 	
		<td>${giftname}</td> 	
		<td>${point}</td> 	
		<td>${giftid}</td> 	
 	</tr>
 	
 </table>
</body>
</html>