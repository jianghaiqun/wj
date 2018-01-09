<%@page language="java" contentType="text/html;charset=gbk"  pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title></title>
</head>
<body onload=javascript:document.forms[0].submit();>
<div style="display:none;">
<form  method="post" action="pay_call_back!payCallback.action" >
	  <!-- 订单号 -->
	  订单号:<input type="text" name="paySn" value="<%=request.getAttribute("paySn") %>"/><br/>
	  <!-- 支付金额 -->
	 支付金额: <input type="text" name="payAmount" value="<%=request.getAttribute("OrdAmt") %>"><br/>
	  <!-- 支付类型 -->
	 支付类型: <input type="text" name="payType" value="<%=request.getAttribute("payType") %>"><br/>
	  <!-- 支付流水号 -->
	 支付流水号: <input type="text" name="TrxId" value="<%=request.getAttribute("TrxId") %>"><br/>
	  <!-- 签名数据 -->
	 签名数据: <input type="text" name="ChkValue" value="<%=request.getAttribute("ChkValue") %>"><br/>
	  <!-- 回调方式 -->
	 回调方式: <input type="text" name="Btype" value="<%=request.getAttribute("Btype") %>"><br/>
	  <input type="submit" value="提交">
</form>
</div>
</body>
</html>