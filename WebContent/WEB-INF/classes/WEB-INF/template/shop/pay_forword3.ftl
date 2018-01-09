<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body onload=javascript:document.forms[0].submit();>
<div style="display:none;">	
<form  method="post" action="http://test.chinapnr.com/gar/entry.do">
  <tr><input type="text" name="Version" value="${version}"></tr>
  <tr><input type="text" name="CmdId" value="${cmdId}"></tr>
  <tr><input type="text" name="MerId" value="${merId}"></tr>
  <tr><input type="text" name="OrdId" value="${ordId}"></tr>
  <tr><input type="text" name="OldOrdId" value="${OldOrdId}"></tr>
  <tr><input type="text" name="RefAmt" value="${RefAmt}"></tr>
  <tr><input type="text" name="BgRetUrl" value="${bgRetUrl}"></tr>
  <tr><input type="text" name="ChkValue" value="${chkValue}"></tr>
   <tr><input type="text" name="DivDetails" value="${DivDetails}"></tr>
</form>
</div>
</body>
</html>