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
<z:init method="com.wisecode.test.Personnel.initEditPersonnel">

<table width="570" height="227" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="10"></td>
      <td></td>
    </tr>
    <tr><form id="form2">
      <td >
   <fieldset>
    <legend><label>员工信息</label></legend>
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
      <td width="40%" height="10" align="right" ></td>
      <td height="10"></td>
    </tr>
    <tr>
      <td align="right" >用户ID：</td>
      <td width="60%"><input name="user_id"  type="text" class="input1" id="user_id" size=20 verify="用户ID|NotNull" value="${user_id}" readonly="readonly"/></td>
    </tr>
    <tr id ="tr_CodeValue" style="display:">
      <td align="right" >用户名称：</td>
      <td><input name="user_name"  type="text" class="input1" id="user_name" size=20 verify="用户名称|NotNull" value="${user_name}"/></td>
    </tr>
    <tr>
      <td align="right" >性别：</td>
      <td><z:select id="user_sex"> ${user_sex} </z:select></td>
    <tr>
      <td align="right" >用户电话：</td>
      <td><input name="user_tel" type="text"  class="input1" id="user_tel" size=20 value="${user_tel}"/></td>
    </tr>
    <tr>
      <td align="right" >用户地址：</td>
      <td><input name="user_addr" type="text"  class="input1" id="user_addr" size=20 value="${user_addr}"/></td>
    </tr>
    <tr>
      <td align="right" >用户兴趣：</td>
      <td><input name="user_interest" type="text"  class="input1" id="user_interest" size=20 value="${user_interest}"/></td>
    </tr>
    <tr>
      <td align="right" >用户特长：</td>
      <td><input name="user_speciality" type="text"  class="input1" id="user_speciality" size=20 value="${user_speciality}"/></td>
    </tr>
    <tr>
      <td align="right" >用户座右铭：</td>
      <td><input name="user_motto" type="text"  class="input1" id="user_motto" size=20 value="${user_motto}"/></td>
    </tr>
</table></fieldset></td> 
      </form>
</z:init>
<td  width="240"  valign="top">
	  <fieldset>
		<legend><label>所属岗位</label></legend>
		  <table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr>
			<td>
	    <z:tree id="tree1" style="width:230px;height:300px" method="com.wisecode.test.Personnel.treeDataBind" level="3"
					lazy="true" resizeable="true">
	      <p cid='${ID}' >
	        <input type="checkbox" name="organ_code" value='${ID}' ${checked}>
	        ${ID}(${organ_name})</p>
	      </z:tree>
		</td>
		 </tr>
      </table></fieldset>
	  </td>
    </tr>
	</table>


</body>
</html>
