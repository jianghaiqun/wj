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
<z:init method="com.wangjin.couponapprove.CouponApproveInfo.init">
<form id="form1">
<input type="hidden" value="${ids}" id="ids" name="ids"/>
		<table width="380" height="150" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
	    			<fieldset>
	    				 <table>
	    				     <tr>
						 	     <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">状态：</td>
						 	     <td>
						 	     	<z:select id="approveStatus" name="approveStatus" verify="状态|NotNull">${approveStatus}</z:select>
						 	     </td>
						 	 </tr>
						 	 <tr>
						 	     <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">描述：</td>
						         <td >
						         	<textarea id="remark1" name="remark1" style="width:260px"></textarea>
						         </td>
						 	 </tr>
						 	 <tr>
						 	     <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">审批抄送邮箱：</td>
						         <td >
						         	<input id="ccMail" name="ccMail" style="width:260px" value="yangxiaoqing@kaixinbao.com"/>
						         </td>
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
