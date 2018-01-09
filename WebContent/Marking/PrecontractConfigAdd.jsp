<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<form id="form2">
<table width="420" height="227" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td width="90" height="10" ></td>
      <td></td>
    </tr>
    <tr>
      <td align="right" >预约栏目URL：</td>
      <td><input name="StencilUrl" type="text" class="input1" id="StencilUrl" verify="预约栏目URL|NotNull" /></td>
    </tr>
    <tr>
      <td align="right" >优惠券编号：</td>
      <td><input name="CouponBatch" type="text" class="input1" id="CouponBatch" verify="优惠券编号|NotNull" /></td>
    </tr>
    <tr>
      <td align="right" >初始预约人数：</td>
      <td><input name="Prop1" type="text" class="input1" id="Prop1" verify="初始预约人数|Number" /></td>
    </tr>
    <tr>
      <td align="right" >备注：</td>
      <td><textarea rows="4" name="Remark" type="text" class="input1" id="Remark" ></textarea></td>
    </tr>
    <tr>
      <td height="10" colspan="2" align="center"></td>
    </tr>
</table>
</form>
</body>
</html>