<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>支付后台回调超时！</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="kaixinbao" />
<style type="text/css">
p{font-size:14px;}
td{width:150px;height:20px;}
</style>
</head>
<body>
<p style="font-weight:bold">支付后台回调超时！</p>
<br>
<table border="1" cellSpacing="0" cellPadding="0">
<tr>
<td style = "text-align:center;font-size:12px;">订单号</td>
<td style = "text-align:center;font-size:12px;">起保日期</td>
<td style = "text-align:center;font-size:12px;">产品名称</td>
<td style = "text-align:center;font-size:12px;">保费</td>
<td style = "text-align:center;font-size:12px;">失败原因</td>
<td style = "text-align:center;font-size:12px;">被保人编号</td>
<td style = "text-align:center;font-size:12px;">支付对账号</td>
<td style = "text-align:center;font-size:12px;">支付方式</td>
</tr>
<tr>
<td style = "font-size:12px;">${OrderSn}</td>
<td style = "font-size:12px;">${Effective}</td>
<td style = "font-size:12px;">${ProductName}</td>
<td style = "font-size:12px;">${TotalAmount}</td>
<td style = "font-size:12px;">${ErrMsg}</td>
<td style = "font-size:12px;">${InsuredSn}</td>
<td style = "font-size:12px;">${PaySn}</td>
<td style = "font-size:12px;">${payType}</td>
</tr>
</table>
</body>
</html>