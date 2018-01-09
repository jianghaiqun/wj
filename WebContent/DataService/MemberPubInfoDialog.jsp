<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../wwwroot/kxb/js/jquery-1.4.2.min.js""></script>
<script src="../Framework/Main.js"></script>
<script src="../template/shop/js/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function getcolor(ele) {
	var color = jQuery(ele).val();
	jQuery("#Info").css("color",color);
}
</script>
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.PublicInform.MemberInfoinit">
<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0" align="left">
	<tr>
      <td width="25%" height="10" align="right" ></td>
      <td height="10"></td>
    </tr>
    <tr id ="tr_CodeValue" style="display:">
      <td align="right" height="25">开始时间：</td>
      <td height="25">
      	<input name="StartDate" type="text" id="StartDate" value="${StartDate}" verify="开始时间|NotNull" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 120px;"/>
      </td>
    </tr>
    <tr>
      <td align="right" height="25">结束时间：</td>
      <td height="25">
      	<input name="EndDate" type="text" id="EndDate" value="${EndDate}" verify="结束时间|NotNull" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})" style="width: 120px;"/>
      </td>
    </tr>
    <tr>
      <td align="right" height="25">是否启用：</td>
      <td height="25">
      	<z:select name="ViewFlag" style="width:48px;" id="ViewFlag" value="${ViewFlag}" verify="是否启用|NotNull">${ViewFlags}</z:select>
      </td>
    </tr>
    <tr>
      <td align="right" height="25">字体颜色：</td>
      <td height="25">
      	<z:select name="Color" onChange="getcolor(this)" id="Color" style="width:48px;" value="${Color}" verify="字体颜色|NotNull">${Colors}</z:select>
	  </td>
    </tr>
    <tr>
      <td align="right" height="25">公告内容：</td>
      <td height="25">
      	<textarea name="Info" type="text" id="Info" verify="公告内容|NotNull" maxlength="150" style="width:300px;">${Info}</textarea>
      </td>
    </tr>
    <input name="id" id="id" type="hidden" value="${id}" />
</table>
</form>
</z:init>
</body>
</html>