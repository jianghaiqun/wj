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
<table width="370" height="227" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td width="113" height="10" ></td>
      <td></td>
    </tr>
    <tr>
      <td align="right" >姓名：</td>
      <td><input name="Name"  type="text"  class="input1" id="Name" verify="姓名|NotNull" /></td>
    </tr>
    <tr>
      <td align="right" >护照：</td>
      <td><input name="PassportId"  type="text"  class="input1" id="PassportId" verify="签证号|NotNull" /></td>
    </tr>
    <tr>
      <td align="right" >手机：</td>
      <td><input name="Mobile"  type="text"  class="input1" id="Mobile" /></td>
    </tr>
    <tr>
      <td align="right" >Email：</td>
      <td><input name="Email"  type="text"  class="input1" id="Email" /></td>
    </tr>
    <tr>
      <td height="10" colspan="2" align="center"></td>
    </tr>
</table>
</form>
</body>
</html>