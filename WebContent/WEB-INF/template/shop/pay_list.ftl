<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付方式请单</title>
</head>
<body>
<div>
<form action="pay!show.action" target="_blank">
订单号：<input type="text" name="OrdId">	
<input type="radio" value="hftx" name="payType">汇付天下
<input type="radio" value="cft" name="payType">财付通
<input type="radio" value="zfb" name="payType">支付宝
<input type="submit" value="支付">
</form>
</div>
</body>
</html>