<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>淘宝SD订单生效日期过近通知</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="kaixinbao" />
<style type="text/css">
p{font-size:14px;}
td{width:150px;height:20px;}
</style>
</head>
<body>
<p style="font-weight:bold">淘宝SD订单生效日期过近！</p>
<br>
<table border="1" cellSpacing="0" cellPadding="0">
<tr>
<td style = "text-align:center;font-size:12px;">外部订单号</td>
<td style = "text-align:center;font-size:12px;">系统订单号</td>
<td style = "text-align:center;font-size:12px;">支付对账号</td>
<td style = "text-align:center;font-size:12px;">承保日期</td>
<td style = "text-align:center;font-size:12px;">起保日期</td>
<td style = "text-align:center;font-size:12px;">警告原因</td>
</tr>
<tr>
<td style = "font-size:12px;">${TBTradeSeriNo}</td>
<td style = "font-size:12px;">${OrderSn}</td>
<td style = "font-size:12px;">${PaySn}</td>
<td style = "font-size:12px;">${DealDate}</td>
<td style = "font-size:12px;">${SvaliDate}</td>
<td style = "font-size:12px;">${ErrMsg}</td>
</tr>
</table>
</body>
</html>