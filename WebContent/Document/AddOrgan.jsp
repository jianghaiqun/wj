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
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
      <td width="40%" height="10" align="right" ></td>
      <td height="10"></td>
    </tr>
    <tr>
      <td align="right" >机构代码：</td>
      <td width="60%"><input name="organ_code"  type="text" class="input1" id="organ_code" size=20 verify="机构代码|NotNull"/></td>
    </tr>
    <tr id ="tr_CodeValue" style="display:">
      <td align="right" >机构名称：</td>
      <td><input name="organ_name"  type="text" class="input1" id="organ_name" size=20 verify="机构名称|NotNull"/></td>
    </tr>
    <tr>
      <td align="right" >机构描述：</td>
      <td><input name="oragan_respons"  type="text" class="input1" id="oragan_respons" size=20/></td>
    </tr><!-- 
    <tr>
      <td align="right" >上级机构：</td>
      <td><input name="parent_organ" type="text"  class="input1" id="parent_organ" size=20 verify="上级机构|NotNull"/></td>
    </tr>
    <tr>
      <td align="right" >机构属性：</td>
      <td><select name="organ_level" id="organ_level">
      	<option value="1">机构</option>
      	<option value="2">团队</option>
      	<option value="3">岗位</option>
      </select> </td>
    </tr> -->
</table>
</form>
</body>
</html>
