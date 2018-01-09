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
</script>
</head>
<body class="dialogBody">
	<z:init method="com.sinosoft.cms.document.PromotionManage.initDialogSellingBrand">
		<form id="form2">
			<input type="hidden"  id="ID" name="ID" value="<%=request.getParameter("ID")%>">
			<input type="hidden" id="UploadFilePath" name="UploadFilePath" value="${UploadFilePath}" />
			<table width="100%" height="100%" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">品牌名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${BrandName}" type="text" id="BrandName" name="BrandName" ztype="String"  class="inputText" size="20"   maxlength=200   verify="品牌名称|NotNull" ></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">品牌数量：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td ><input value="${count}" type="text" id="count" name="count" ztype="String"  class="inputText" size="20"   maxlength=10   verify="参与人数|NotNull&&Number" ></td>
										</tr>
							     </table>
							     <table>
							     	<tr >
								   	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">公司编码：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								    <td ><input value="${LogoUrl}" type="text" id="LogoUrl" name="LogoUrl" ztype="String"  class="inputText" size="20" verify="公司编码|NotNull&&Number"   maxlength=10  ></td>
		                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		                            <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;&nbsp;&nbsp;&nbsp;跳转地址：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								    <td ><input value="${URL}" type="text" id="URL" name="URL" ztype="String"  class="inputText" size="20" verify="跳转地址|NotNull" ></td>
								    </tr>
							     </table>
							     <table>
								          <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是否显示：</td>
										  <td>${isShow}</td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;&nbsp;&nbsp;&nbsp;购买人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								    	  <td ><input value="${buynum}" type="text" id="buynum" name="buynum" ztype="String"  class="inputText" size="20"   maxlength=200   verify="购买人数|NotNull&&Number" ></td>
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
