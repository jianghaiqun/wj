<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>开心保-订单支付成功通知(未登录)</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="kaixinbao" />
<style type="text/css">
p{font-size:14px;}
td{width:150px;height:20px;}
</style>
</head>
<body>
<p style="font-weight:bold">亲爱的会员${MemberName}，您好！</p>
<p>        您已经完成了如下订单的支付，稍后您将收到保单信息。恭喜您选到了合适的保险</p>
<p>保障，感谢您选择开心保！</p>
<br>
<table border="1" cellSpacing="0" cellPadding="0">
<tr>
<td style = "text-align:center;font-size:12px;">订单号</td>
<td style = "text-align:center;font-size:12px;">产品名称</td>
<td style = "text-align:center;font-size:12px;">投保人姓名</td>
<td style = "text-align:center;font-size:12px;">已支付金额</td>
</tr>
<tr>
<td style = "font-size:12px;">${OrderSn}</td>
<td style = "font-size:12px;">${ProductName}</td>
<td style = "font-size:12px;">${ApplicantName}</td>
<td style = "font-size:12px;">${paidOrdAmt}</td>
</tr>
</table>
<p>本次交易您获得了奖励积分<span style="font-weight:bold;">${Points}</span>个开心果，请及时点击<span style="font-weight:bold;"><a href="${path}">登录/注册</a></span>领取。</p>
<p>---------------------------------------------------------------------------------------------------------------</p>
<p>如有任何疑问或意见建议，欢迎您随时与我们联系：</p>
<p>*客服电话：4009-789-789</p>
<p>*客服邮箱:kf@kaixinbao.com：</p>
<p>*开心保官网：<a href="http://www.kaixinbao.com">www.kaixinbao.com</a></p>
</body>
</html>