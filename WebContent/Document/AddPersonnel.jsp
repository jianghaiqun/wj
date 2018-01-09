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
      <td align="right" >用户ID：</td>
      <td width="60%"><input name="user_id"  type="text" class="input1" id="user_id" size=40 verify="用户ID|NotNull"/></td>
    </tr>
    <tr id ="tr_CodeValue" style="display:">
      <td align="right" >用户名称：</td>
      <td><input name="user_name"  type="text" class="input1" id="user_name" size=40 verify="用户名称|NotNull"/></td>
    </tr>
    <tr>
      <td align="right" >性别：</td>
      <td><select name="user_sex" id="user_sex">
      	<option value="M">男</option>
      	<option value="F">女</option>
      </select> </td>
    <tr>
      <td align="right" >用户电话：</td>
      <td><input name="user_tel" type="text"  class="input1" id="user_tel" size=20/></td>
    </tr>
    <tr>
      <td align="right" >用户地址：</td>
      <td><input name="user_addr" type="text"  class="input1" id="user_addr" size=40/></td>
    </tr>
    <tr>
      <td align="right" >用户兴趣：</td>
      <td><input name="user_interest" type="text"  class="input1" id="user_interest" size=40/></td>
    </tr>
    <tr>
      <td align="right" >用户特长：</td>
      <td><input name="user_speciality" type="text"  class="input1" id="user_speciality" size=40/></td>
    </tr>
    <tr>
      <td align="right" >用户座右铭：</td>
      <td><input name="user_motto" type="text"  class="input1" id="user_motto" size=40/></td>
    </tr>
</table>
</form>
</body>
</html>
