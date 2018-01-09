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
<body>
<z:init method="com.sinosoft.cms.site.Keyword.initDialog">
<form id="form2">
<table width="800" height="224" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="10"></td>
      <td></td>
    </tr>
    <tr>
      <td >
   <fieldset>
    <legend><label>基本信息</label></legend>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" height="200">
    <tr>
    <td align="left" valign = "top">
      <table>
        <tr>
          <td width="39%" height="30" align="right" >关键字：</td>
          <td width="61%"><input name="Keyword" type="text" value="${Keyword}" style="width:100px" class="input1" id="Keyword" verify="关键字|NotNull" size=15 /> <input name="ID" type="hidden" id="ID" /></td>
        </tr>
        <tr>
          <td height="30" align="right" >着陆页：</td>
          <td><input name="LinkURL" type="text" value="${LinkUrl}" class="input1" id="LinkURL" size=33 /></td>
        </tr>
        <tr id ="tr_Password2">
          <td height="30" align="right" >链接提示：</td>
          <td><input name="LinkAlt" type="text" value="${LinkAlt}" class="input1" id="LinkAlt" size="33" /></td>
        </tr>
        <tr>
          <td width="39%" height="30" align="right" >搜索值：</td>
          <td width="61%"><input name="SearchCode" type="text" value="${SearchCode}" class="input1" id="SearchCode" verify="Int"/></td>
        </tr>
        <tr>
          <td width="39%" height="30" align="right" >优先级：</td>
          <td width="61%"><input name="PriorityLevel" type="text" value="${PriorityLevel}" class="input1" id="PriorityLevel" verify="Int"/></td>
        </tr>
      </table>
    </td>
    <td align="left" valign = "top">
      <table>
      	<tr id ="tr_ConfirmPassword2">
          <td height="30" align="right" >&nbsp;&nbsp;&nbsp;&nbsp;打开方式：</td>
          <td><z:select name="LinkTarget" id="LinkTarget" value="${LinkTarget}" verify="打开方式|NotNull"><span value="_self">原窗口</span> <span value="_blank">新窗口</span> <span value="_parent">父窗口</span></z:select></td>
        </tr>
        <tr id ="tr_ConfirmPassword2">
          <td height="30">&nbsp;&nbsp;&nbsp;&nbsp;是否收录：</td>
          <td><z:select id="EmployFlag" name="EmployFlag" style="width:50px" value="${EmployFlag}">"${employFlagInit}"</z:select></td>
        </tr>
        <tr id ="tr_ConfirmPassword2">
          <td height="30">&nbsp;&nbsp;&nbsp;&nbsp;所属分类：</td>
          <td><z:select id="BelongCategory" name="BelongCategory" style="width:100px" value="${BelongCategory}">${belongCategoryInit}</z:select></td>
        </tr>
      </table>
    </td>
    </tr>
    </table>
   </fieldset></td>
      <td  width="240"  valign="top">
	  <fieldset>
		<legend><label>内链分类</label></legend>
		  <table width="100%" border="0" cellpadding="2" cellspacing="0" height="200">
			<tr>
			<td>
	    <z:tree id="tree1" style="width:230px;height:200px" method="com.sinosoft.cms.site.KeywordType.loadTypeTree">
	      <p cid='${ID}' >
	        <input type="checkbox" name="keywordType" value='${ID}' ${checked}>
	        ${TypeName}</p>
	      </z:tree>
	      <input type="hidden" id="ID" value="${ID}" />
		</td>
		 </tr>
      </table></fieldset>
	  </td>
    </tr>
</table>
</form>
</z:init>
</body>
</html>