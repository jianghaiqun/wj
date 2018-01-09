<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提交支付请求参数</title>
</head>
<body onload=javascript:document.forms[0].submit();>
<div style="display:none;">	
<form  method="post" action="${GateUrl}">
  <tr><input type="text" name="Version" value="${version}"></tr>
  <tr><input type="text" name="CmdId" value="${cmdId}"></tr>
  <tr><input type="text" name="MerId" value="${merId}"></tr>
  <tr><input type="text" name="OrdId" value="${paySn}"></tr>
  <tr><input type="text" name="OrdAmt" value="${ordAmt}"></tr>
  <tr><input type="text" name="CurCode" value="${curCode}"></tr>
  <tr><input type="text" name="Pid" value="${pid}"></tr>
  <tr><input type="text" name="RetUrl" value="${retUrl}"></tr>
  <tr><input type="text" name="BgRetUrl" value="${bgRetUrl}"></tr>
  <tr><input type="text" name="MerPriv" value="${merPriv}"></tr>
  <tr><input type="text" name="GateId" value="${gateId}"></tr>
  <tr><input type="text" name="UsrMp" value="${usrMp}"></tr>
  <tr><input type="text" name="DivDetails" value="${divDetails}"></tr>
  <tr><input type="text" name="PayUsrId" value="${payUsrId}"></tr>
  <tr><input type="text" name="ChkValue" value="${chkValue}"></tr>
</form>
</div>
</body>
</html>