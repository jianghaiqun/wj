<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>电子发票下载</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery('#Invoice_HeadType').change(function(){
		
		var invoice_HeadType= jQuery('#Invoice_HeadType').val();
		if(invoice_HeadType!="01"){
			jQuery('#Invoice_TaxpayerId').attr("readonly",false);
		}else{
			jQuery('#Invoice_TaxpayerId').attr("readonly",true);
		}
	});
});
</script>

	</head>
	<body class="dialogBody">
	<z:init method="com.wangjin.cms.orders.QueryOrders.electronicInvoiceInit">
	<form id="form2">
 	<fieldset style="margin: 3px 5px;">
    <legend><label>发票信息</label></legend>
    <input type="hidden" name="policyNo" id="policyNo" value="${policyNo}"/>
    <input type="hidden" name="timePrem" id="timePrem" value="${timePrem}"/>
    <input type="hidden" name="riskCode" id="riskCode" value="${riskCode}"/>
    <input type="hidden" name="orderSn" id="orderSn" value="${orderSn}"/>
    
		<table width="100%" height="100%" border="0">
			<tr>
				<td >
						<table width="460" height="180" align="center" cellpadding="2"cellspacing="0" border="0">
							<tr>
								<td align="right">发票抬头<font color="red">*</font>：</td>
								<td><input name="applicantName" type="text" value="${applicantName}" style="width:150px" class="inputText" id="applicantName" /></td>
							</tr>
							<tr>
								<td align="right">发票抬头类型<font color="red">*</font>：</td>
								<td><select class="inputText" id="Invoice_HeadType" name="Invoice_HeadType">
									<option value="01" selected="selected">个人</option>
									<option value="02">公司/企业</option>
									<option value="03">政府机构、非企业性事业单位、国外企业</option>
								</select></td>
							</tr>
							<tr>
								<td align="right">纳税识别号/统一社会信用代码<font color="red">*</font>：</td>
								<td><input name="Invoice_TaxpayerId" type="text" style="width:150px" class="inputText" id="Invoice_TaxpayerId" readonly="readonly"/><font color="red">*</font><font color="red">非个人必填</font></td>
							</tr>
							<tr>
								<td align="right">邮箱：</td>
								<td><input name="applicantMail" type="text" value="${applicantMail}" style="width:150px" class="inputText" id="applicantMail" /></td>
							</tr>
							<tr>
								<td align="right">手机号：</td>
								<td><input name="applicantMobile" type="text" value="${applicantMobile}" style="width:150px" class="inputText" id="applicantMobile" /></td>
							</tr>
						</table>
				</td>
			</tr>
		</table>
	 </fieldset>
</form>
</z:init>
</body>
</html>
