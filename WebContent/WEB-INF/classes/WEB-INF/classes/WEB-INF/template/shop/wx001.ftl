<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>微信支付维权邮件提醒</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="kaixinbao" />
<style type="text/css">
p{font-size:14px;}
td{width:150px;height:20px;}
</style>
</head>
<body>
<p style="font-weight:bold">微信支付维权邮件提醒</p>
<br>
微信维权通知：${Msg}
<table border="1" cellSpacing="0" cellPadding="0">
	<tr>
		<td style = "text-align:center;font-size:12px;">订单号</td>
		<td style = "text-align:center;font-size:12px;">申请原因</td>
		<td style = "text-align:center;font-size:12px;">用户期待解决方案</td>
		<td style = "text-align:center;font-size:12px;">备注信息（电话）</td>
	</tr>
	<tr>
	    <td style = "font-size:12px;">&nbsp ${orderSn}</td>
		<td style = "font-size:12px;">&nbsp ${Reason}</td>
		<td style = "font-size:12px;">&nbsp ${Solution}</td>
		<td style = "font-size:12px;">&nbsp ${ExtInfo}</td>
	</tr>
</table>
</body>
</html>