<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页面请求参数</title>
</head>
<div style="display:none;">
<form name="payment" action="<%=request.getAttribute("GateUrl") %>" method="post">
  <table>
	  <tr><input type="text" name="paySn" value="<%=request.getAttribute("paySn") %>"></tr>
	  <tr><input type="text" name="TransAmt" value="<%=request.getAttribute("total_fee") %>"></tr>
	  <tr><input type="text" name="TransDate" value="<%=request.getAttribute("TransDate") %>"></tr>
	  <tr><input type="text" name="Version" value="<%=request.getAttribute("Version") %>"></tr>
	  <tr><input type="text" name="BgRetUrl" value="<%=request.getAttribute("BgRetUrl") %>"></tr>
	  <tr><input type="text" name="PageRetUrl" value="<%=request.getAttribute("RetUrl") %>"></tr>
  </table>
</form>
</div>
<script language=JavaScript>
	document.payment.submit();
</script>	

<body>

</body>
</html>