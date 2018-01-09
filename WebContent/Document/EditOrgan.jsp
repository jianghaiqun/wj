<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.wisecode.test.Organ.initEditOrgan">
<form id="form2">
<table width="570" height="227" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="10"></td>
      <td></td>
    </tr>
    <tr>
      <td >
   <fieldset>
    <legend><label>机构信息</label></legend>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="39%" height="30" align="right" >机构编号：</td>
          <td width="61%"><input name="organ_code"  type="text" value="${organ_code}" id="organ_code" width="70" readonly="readonly"/></td>
        </tr>
        <tr>
          <td height="30" align="right" >机构名称：</td>
          <td><input name="organ_name"  type="text" value="${organ_name}" id="organ_name" width="70" /></td>
        </tr>
        <tr>
          <td height="30" align="right" >机构职责：</td>
          <td >
          <textarea rows="4" cols="40" id="oragan_respons" name="oragan_respons">${oragan_respons}</textarea>
          </td>
        </tr>
      </table></fieldset></td><!-- 
      <td  width="240"  valign="top">
	  <fieldset>
		<legend><label>所属机构</label></legend>
		  <table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr>
			<td>
	    <z:tree id="tree1" style="width:230px;height:300px" method="com.wisecode.test.Organ.treeDataBind">
	      <p cid='${organ_code}' >
	        <input type="checkbox" name="parent_organ" value='${organ_code}' ${checked}>
	        ${organ_code}(${organ_name})</p>
	      </z:tree>
		</td>
		 </tr>
      </table></fieldset>
	  </td> -->
    </tr>
	</table>
</form>
</z:init>
</body>
</html>