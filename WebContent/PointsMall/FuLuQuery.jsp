<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>福禄商品查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
</head>
<body>

<z:init method="com.wangjin.infoseeker.OrderInfo.initStaff">
		<table width="200" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding: 6px;" class="blockTd">
				
				<z:tree id="tree1" style="height:600px;width:400px;" method="com.wangjin.pointsMall.GiftManage.treeDataBind" level="3" lazy="true" resizeable="true">
					<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
					<p cid='${ID}' level="${TreeLevel}">
					<input type="checkbox" name="contant" id='contant_${ID}' value='${ChannelCode}' onClick="onCheck(this);">
					<label for="contant_${ID}">
					<span id="span_${ID}">${Name}</span>
					</label>
					</p>
				</z:tree></td>
				
			</tr>
		</table>
</z:init>
</body>
</html>
