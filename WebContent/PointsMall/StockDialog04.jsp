<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

</script>
</head>
<body class="dialogBody">
	<z:init method="com.wangjin.pointsMall.GiftManage.initStockDialog">
		<form id="form3">
			<input type="hidden"  id="ID" name="ID" value="${ID}">
			<table width="800" height="100" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">商品名称：</td>
								          <td ><input value="${GoodsName}" type="text" id="GoodsName" name="GoodsName" ztype="String"  class="inputText" size="25" verify="商品名称|NotNull" maxlength=50></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td  height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">卡号：</td>
								          <td ><input value="${CardNo}" type="text" id="CardNo" name="CardNo" ztype="String"  class="inputText"  size="25"   maxlength=50 verify="卡号|NotNull"></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										</tr>
							     </table>
							     <table>
								          <tr>
										        <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">有效期时间：</td>
												<td><input name="OutDate" id="OutDate" value="${OutDate}" type="text" size="14" ztype="Date"  verify="有效期时间|NotNull" /></td>
												<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
