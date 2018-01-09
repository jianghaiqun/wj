<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.InitiateRefundManage.initDialog">
<form id="form2">
	<table width="500" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td width="114" height="10"></td>
			<td width="376"><input id="Id" name="Id" type="hidden" value="${id}"/></td>
		</tr>
		<!-- <tr>
			<td align="right">订单号：</td>
			<td height="30">${OrderSn}</td>
		</tr> -->
		<tr>
			<td align="right">商户流水号：</td>
			<td height="30">${PaySn}</td>
		</tr>
        <tr>
            <td align="right">退款状态：</td>
            <td height="30">
                <input id="status" name="status" type="text" value="${status}" class="input1"/>
            </td>
        </tr>
        <tr>
            <td align="right">退款日期：</td>
            <td height="30">
                <input id="tRefundDate" name="tRefundDate" type="text" value="${tRefundDate}" class="input1" ztype="Date" />
                <input name="RefundTime" type="text" class="inputText" id="RefundTime" ztype="Time" value="${RefundTime}" size=14 />
            </td>
        </tr>
        <tr>
            <td align="right">退款金额：</td>
            <td height="30">
                <input id="RefundAmount" name="RefundAmount" type="text" value="${RefundAmount}" class="input1"/>
            </td>
        </tr>
        <tr>
            <td align="right">备注：</td>
           <td align="left">
                   <textarea name="Remark" id="Remark" style="width:300px;height:100px">${Remark}</textarea>
               </td>
        </tr>
	</table>
	</form>
</z:init>
</body>
</html>