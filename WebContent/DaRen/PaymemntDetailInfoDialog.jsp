<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.wangjin.daren.PaymemntDetailInfo.initDialog">
<form id="form4">
		<table width="600" height="100" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
	    			<fieldset>
	    				 <table>
						     <tr>
						          <td>支付时间：</td>
						          <td ><input value="${payTime}" type="text" id="payTime" name="payTime" style="width:100px" ztype="date" verify="支付时间|NotNull"></td>
						          <td>支付金额：</td>
						          <td ><input value="${payPrice}" type="text" id="payPrice" name="payPrice" ztype="String"  class="inputText" size="20" verify="支付金额|NotNull"></td>
						          <td>是否已支付：</td>
						          <td ><z:select id="isPay" name="isPay" value="${isPay}" style="width:50px">${YesOrNo}</z:select></td>
						          
						     </tr>
					     </table>
					     <input value="${ID}" type="hidden" id="ID" name="ID" />
					     <input value="${authorDetailInfo_id}" type="hidden" id="authorDetailInfo_id" name="authorDetailInfo_id" />
	               </fieldset>
			</td>
	    </tr>
	    </table>
	</form>
</z:init>
</body>
</html>
