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
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
Page.onLoad(function(){
	$("gifttitle").disable();
	$("exchangeQuantity").disable();
	$("orderSn").disable();
	$("fuLuOrderSn").disable();
	$("fuLuGoodsID").disable();
	$("memberid").disable();
	$("cardNo").disable();
	$("cardKey").disable();

	$("wrongMassage").disable();
})
</script>
</head>
<body class="dialogBody">
	<input type="hidden" id="GiftDialogStatusFlag" value="<%=request.getParameter("GiftDialogStatusFlag")%>">
	<z:init method="com.wangjin.pointsMall.EXGiftManage.initExchangeDialog">
		<form id="form2">
			<input type="hidden"  id="ID" name="ID" value="${ID}">
			<table width="920" height="580" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">礼品标题：</td>
								          <td ><input value="${gifttitle}" type="text" id="gifttitle" name="gifttitle" ztype="String"  class="inputText" size="35" maxlength=50></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">兑换数量：</td>
								          <td ><input value="${exchangeQuantity}" type="text" id="exchangeQuantity" name="exchangeQuantity" ztype="String"  class="inputText" size="35" maxlength=50></td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">订单号：</td>
								          <td ><input value="${orderSn}" type="text" id="orderSn" name="orderSn" ztype="String"  class="inputText" size="35"  maxlength=50></td>
										 </tr>
										 <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">福禄订单号：</td>
								          <td ><input value="${fuLuOrderSn}" type="text" id="fuLuOrderSn" name="fuLuOrderSn" ztype="String"  class="inputText" size="35" maxlength=50></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">福禄商品号：</td>
								          <td ><input value="${fuLuGoodsID}" type="text" id="fuLuGoodsID" name="fuLuGoodsID" ztype="String"  class="inputText" size="35" maxlength=50></td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">会员id：</td>
								          <td ><input value="${memberid}" type="text" id="memberid" name="memberid" ztype="String"  class="inputText" size="35"  maxlength=50></td>
										</tr>
										<tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">卡号：</td>
								          <td ><input value="${cardNo}" type="text" id="cardNo" name="cardNo" ztype="String"  class="inputText" size="35" maxlength=50></td>
								          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">卡密：</td>
								          <td ><input value="${cardKey}" type="text" id="cardKey" name="cardKey" ztype="String"  class="inputText" size="35" maxlength=50></td>
										  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">充值号码：</td>
								          <td ><input value="${mobileNo}" type="text" id="mobileNo" name="mobileNo" ztype="String"  class="inputText" size="35"  maxlength=50></td>
										</tr>
										<tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">错误信息：</td>
								          <td ><input value="${wrongMassage}" type="text" id="wrongMassage" name="wrongMassage" ztype="String"  class="inputText" size="35" maxlength=50 ></td>
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
