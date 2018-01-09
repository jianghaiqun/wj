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
});
function ResponseTypeChange(){
	jQuery("#TextContent").val("");
	jQuery("#PicTitle").val("");
	jQuery("#PicDesc").val("");
	jQuery("#PicURL").val("");
	jQuery("#piclinkurl").val("");
	jQuery("#TextOrder").val("");
	ctrl();
}
function ctrl(){
	var ResponseType = jQuery("#ResponseType").val();
	if (ResponseType == 'text') {
		jQuery("#TextContentTR").show();
		jQuery("#PicTitleTR").hide();
		jQuery("#PicDescTR").hide();
		jQuery("#PicURLTR").hide();
		jQuery("#piclinkurlTR").hide("");
	} else if (ResponseType == 'img') {
		jQuery("#TextContentTR").hide();
		jQuery("#PicTitleTR").hide();
		jQuery("#PicDescTR").hide();
		jQuery("#PicURLTR").show();
		jQuery("#piclinkurlTR").hide("");
	} else if (ResponseType == 'pic') {
		jQuery("#TextContentTR").hide();
		jQuery("#PicTitleTR").show();
		jQuery("#PicDescTR").show();
		jQuery("#PicURLTR").show();
		jQuery("#piclinkurlTR").show("");
	} else {
		jQuery("#TextContentTR").hide();
		jQuery("#PicTitleTR").hide();
		jQuery("#PicDescTR").hide();
		jQuery("#PicURLTR").hide();
		jQuery("#piclinkurlTR").hide("");
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.document.WxAutoReply.initDialog">
<form id="form2">
	<table width="500" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td width="114" height="10"></td>
			<td width="376">
			<input id="ID" name="ID" type="hidden" value="${ID}"/>
			</td>
		</tr>
		<tr>
			<td align="right">关键字或菜单KEY：</td>
			<td height="30">
				<input id="MenuAttribute" name="MenuAttribute" type="text" value="${MenuAttribute}" class="input1" verify="关键字|NotNull"/>
			</td>
		</tr>
		<tr>
			<td align="right">回复类型：</td>
			<td height="30">
				<z:select style="width:100px;" name="ResponseType" id="ResponseType" onChange="ResponseTypeChange()"
					value="${ResponseType}" verify="回复类型|NotNull">${ResponseTypeList}</z:select>
			</td>
		</tr>
	 	<tr id="TextContentTR">
            <td align="right">文本内容：</td>
            <td height="30">
                <textarea name="TextContent" id="TextContent" style="width:300px;height:100px">${TextContent}</textarea>
            </td>
       	</tr>
		<tr id="PicTitleTR" style="display:none;">
            <td align="right">图片标题：</td>
            <td height="30">
                <textarea name="PicTitle" id="PicTitle" style="width:300px;height:50px">${PicTitle}</textarea>
            </td>
        </tr>
        <tr id="PicDescTR" style="display:none;">
            <td align="right">图片描述：</td>
            <td height="30">
                <textarea name="PicDesc" id="PicDesc" style="width:300px;height:50px">${PicDesc}</textarea>
            </td>
        </tr>
        <tr id="PicURLTR" style="display:none;">
            <td align="right">图片链接：</td>
            <td height="30">
                <textarea name="PicURL" id="PicURL" style="width:300px;height:60px">${PicURL}</textarea>
            </td>
        </tr>
        <tr id="piclinkurlTR" style="display:none;">
            <td align="right">图文素材链接：</td>
            <td height="30">
            	<textarea name="piclinkurl" id="piclinkurl" style="width:300px;height:60px">${piclinkurl}</textarea>
            </td>
        </tr>
        <tr>
            <td align="right">多个图文排序：</td>
            <td height="30">
                <input id="TextOrder" name="TextOrder" type="text" value="${TextOrder}" class="input1" size="10" verify="多个图文排序|Number"/>
            </td>
        </tr>
	</table>
	</form>
</z:init>
</body>
</html>