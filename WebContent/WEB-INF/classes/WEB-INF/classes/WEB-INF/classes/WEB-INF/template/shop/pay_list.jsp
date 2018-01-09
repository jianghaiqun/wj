<!DOCTYPE HTML >
<%@ page contentType="text/html; charset=utf-8"%> 
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>第三方支付</title>
</head>
<body>
	<form id="paylist" method="post" target="_blank" action="pay!show.action"> 
	    <ul >
   			<li><input  type="radio" name="payType" id="payType" value="hftx" />汇付天下</li>
   			<li><input  type="radio" name="payType" id="payType" value="cft" />财付通</li>
   			<li><input  type="radio" name="payType" id="payType" value="zfb" />支付宝</li>
	    </ul>
	    
	    <ul >
   			<li><input  type="radio" name="payType" id="payType" value="ms" />民生银行</li>
	    </ul>
	   
	   <button type="submit"  >同意以下协议并支付</button>
	</form>
</body>
</html>