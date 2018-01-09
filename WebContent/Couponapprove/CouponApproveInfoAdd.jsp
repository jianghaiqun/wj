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
<z:init method="com.wangjin.couponapprove.CouponApproveInfo.initDialog">
<form id="form1">
		<table width="750" height="200" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
						 <table>
						 	 <tr>
						 	 	<td align="right" height="35">id：</td>
						 	 	<td><input id="id" name="id"  value="" type="text" size="24" verify="id|NotNull"/></td>
						 	 	<td align="right" height="35">优惠券批次：</td>
						 	 	<td><input id="couponBatch" name="couponBatch"  value="" type="text" size="24" verify="优惠券批次"/></td>
						 	 	<td align="right" height="35">优惠券申请人：</td>
						 	 	<td><input id="couponApplicant" name="couponApplicant"  value="" type="text" size="24" verify="优惠券申请人"/></td>
						 	 	<td align="right" height="35">审批状态：</td>
						 	 	<td><input id="approveStatus" name="approveStatus"  value="" type="text" size="24" verify="审批状态"/></td>
						 	 	<td align="right" height="35">备用字段1：</td>
						 	 	<td><input id="remark1" name="remark1"  value="" type="text" size="24" verify="备用字段1"/></td>
						 	 	<td align="right" height="35">备用字段2：</td>
						 	 	<td><input id="remark2" name="remark2"  value="" type="text" size="24" verify="备用字段2"/></td>
						 	 	<td align="right" height="35">备用字段3：</td>
						 	 	<td><input id="remark3" name="remark3"  value="" type="text" size="24" verify="备用字段3"/></td>
						 	 	<td align="right" height="35">审批时间：</td>
						 	 	<td><input id="createDate" name="createDate"  value="" type="text" size="24" verify="审批时间"/></td>
						 	 	<td align="right" height="35">审批人：</td>
						 	 	<td><input id="createUser" name="createUser"  value="" type="text" size="24" verify="审批人"/></td>
						 	 	<td align="right" height="35">修改时间：</td>
						 	 	<td><input id="modifyDate" name="modifyDate"  value="" type="text" size="24" verify="修改时间"/></td>
						 	 	<td align="right" height="35">修改人：</td>
						 	 	<td><input id="modifyUser" name="modifyUser"  value="" type="text" size="24" verify="修改人"/></td>
						 	 </tr>
					     </table>	     
			</td>
	    </tr>
	    </table>
	</form>
</z:init>
</body>
</html>