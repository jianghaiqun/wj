<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	var ModuleColor=$V("ModuleColor");
	if(ModuleColor==null||ModuleColor==''){
		$S("ModuleColor",'#FFFFFF');
	}
	var ModuleNameColor=$V("ModuleNameColor");
	if(ModuleNameColor==null||ModuleNameColor==''){
		$S("ModuleNameColor",'#4D4D4D');
	}
	var MoreColor=$V("MoreColor");
	if(MoreColor==null||MoreColor==''){
		$S("MoreColor",'#4D4D4D');
	}
});
</script>
</head>
<body class="dialogBody">
	<input type="hidden" id="ModelDialogStatusFlag" value="<%=request.getParameter("ModelDialogStatusFlag")%>">
	<z:init method="com.sinosoft.cms.document.PromotionManage.initDialog">
		<form id="form2">
			<input type="hidden"  id="ID" name="ID" value="<%=request.getParameter("ID")%>">
			<table width="100%" height="100%" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">模块名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${ModuleName}" type="text" id="ModuleName" name="ModuleName" ztype="String"  class="inputText" size="20"   maxlength=200  verify="模块名称|NotNull" ></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">模块类型：</td>
										  <td><z:select style="width:120px;" id="ModuleType"  verify="模块类型|NotNull"  value="${ModuleTypeName}">${ModuleType}</z:select></td>
										</tr>
							     </table>
							      <table>
								         <tr>
								          <td  height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">模块背景色：&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${ModuleColor}" type="text" id="ModuleColor" name="ModuleColor" ztype="String"  class="inputText"  maxlength=30    size="20" verify="模块背景色|NotNull"  ></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">模块主题：</td>
								          <td><z:select style="width:120px;" id="ModuleTheme"  verify="模块主题|NotNull"      value="${ModuleThemeName}">${ModuleTheme}</z:select></td>
										</tr>
							     </table>
							      <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">模块名称颜色：</td>
								          <td ><input value="${ModuleNameColor}" type="text" id="ModuleNameColor" name="ModuleNameColor" ztype="String"   maxlength=30   class="inputText" verify="模块名称颜色|NotNull" size="20"  ></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td  height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;&nbsp;更多链接地址：</td>
								          <td ><input value="${MoreUrl}" type="text" id="MoreUrl" name="MoreUrl" ztype="String"  class="inputText"  size="20"    maxlength=200  ></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										</tr>
							     </table>
							      <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">更多字体颜色：</td>
								          <td ><input value="${MoreColor}" type="text" id="MoreColor" name="MoreColor" ztype="String"  class="inputText" size="20"   maxlength=30   ></td>
										</tr>
							     </table>
			               </fieldset>
					</td>
			    </tr>
			  </table>
		</form>
	</z:init>
</body>
</html>
