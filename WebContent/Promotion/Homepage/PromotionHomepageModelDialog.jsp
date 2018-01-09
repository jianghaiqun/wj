<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
</head>
<body class="dialogBody">
	<input type="hidden" id="ModelDialogStatusFlag" value="<%=request.getParameter("ModelDialogStatusFlag")%>">
	<z:init method="com.sinosoft.cms.document.PromotionManage.initHomepageDialog">
		<form id="form2">
			<input type="hidden"  id="ID" name="ID" value="<%=request.getParameter("ID")%>">
			<table width="100%" height="100%" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">模块标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${ShortTitle}" type="text" id="ShortTitle" name="ShortTitle" ztype="String"  class="inputText" size="20"   maxlength=200   verify="模块标题|NotNull" ></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">模块名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${ModuleName}" type="text" id="ModuleName" name="ModuleName" ztype="String"  class="inputText" size="20"   maxlength=200   verify="模块名称|NotNull" ></td>
										</tr>
							     </table>
							      <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是否显示：</td>
										  <td>${isShow}</td>
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
