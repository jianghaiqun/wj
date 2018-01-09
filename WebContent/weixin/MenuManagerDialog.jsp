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
jQuery(document).ready(function(){
	ctrl();
	var MenuLevel = jQuery("#MenuLevel").val();
	if ("0" == MenuLevel) {
		jQuery("#ParentMenuTR").hide();
	} 
});
function MenuResponseTypeChange(){
	jQuery("#MenuURL").val("");
	ctrl();
}
function ctrl(){
	var MenuResponseType = jQuery("#MenuResponseType").val();
	if (MenuResponseType == 'click') {
		jQuery("#MenuURLTR").hide();
	} else {
		jQuery("#MenuURLTR").show();
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.framework.utility.weixin.common.MenuManager.initDialog">
<form id="form2">
	<table width="500" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td width="114" height="10"></td>
			<td width="376">
			<input id="ID" name="ID" type="hidden" value="${ID}"/>
			<input id="MenuLevel" name="MenuLevel" type="hidden" value="${MenuLevel}"/>
			</td>
		</tr>
		<tr id="ParentMenuTR">
			<td align="right">父菜单：</td>
			<td height="30">
				<z:select style="width:100px;" name="ParentMenuID" id="ParentMenuID"
					value="${ParentMenuID}">${ParentMenu}</z:select>
			</td>
		</tr>
		<tr>
			<td align="right">菜单名称：</td>
			<td height="30">
				<input id="ExternalMenuName" name="ExternalMenuName" type="text" value="${ExternalMenuName}" class="input1" verify="菜单名称|NotNull"/>
			</td>
		</tr>
		<tr>
			<td align="right">菜单KEY：</td>
			<td height="30">
				<input id="MenuAttribute" name="MenuAttribute" type="text" value="${MenuAttribute}" class="input1" verify="菜单KEY|NotNull"/>
			</td>
		</tr>
		<tr>
			<td align="right">回复类型：</td>
			<td height="30">
				<z:select style="width:100px;" verify="回复类型|NotNull">
					<select name="MenuResponseType" id="MenuResponseType" value="${MenuResponseType}" onchange="MenuResponseTypeChange()">
	                	<option value="view" >view</option>
	                	<option value="click">click</option>
	              	</select>
	            </z:select>
	            <span style="color: red;">view是链接跳转，click是回复图文页。</span>
			</td>
		</tr>
	 	<tr id="MenuURLTR">
            <td align="right">跳转链接：</td>
            <td height="30">
                <textarea name="MenuURL" id="MenuURL" style="width:300px;height:100px">${MenuURL}</textarea>
            </td>
       	</tr>
	</table>
	</form>
</z:init>
</body>
</html>