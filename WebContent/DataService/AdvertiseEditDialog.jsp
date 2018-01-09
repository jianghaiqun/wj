<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=Config.getValue("App.Name")%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
 Page.onLoad(function(){
	if($V("hPositionType")!=""){
		$S("AdSpaceType",$V("hPositionType"));
	}
	if($("prop").value=="popup"){
		$("prop1").click();
	}
}); 

function  ChangePositionType(){
	var AdSpaceType = $V("AdSpaceType");
	if($V("ID")!=""){
		if(AdSpaceType!=$V("hPositionType")){
			$("alertTr").style.display="";
		}else{
			$("alertTr").style.display="none";
		}
	}
}
/*弹窗无需输入长宽*/
function choosePopup(){
	if($("prop1").checked==true){
		$("trSize").style.display="none";
		$("AdvertiseWidth").value="";
		$("AdvertiseHeight").value="";
	}else{
		$("trSize").style.display="";
	}
}
</script>
</head>
<body>
<z:init method="com.sinosoft.cms.dataservice.AdvertiseManage.DialogInit">
<form id="form2">
<table width="100%" border="0" cellpadding="4" cellspacing="" bordercolor="#DEDEDC" style="border-collapse:collapse;">
	<tr>
		<td width="25%" height="12"></td>
		<td colspan="2">
        <input type="hidden" id="ID" name="ID" value="${ID}"/>
        <input type="hidden" id="hPositionType" name="hPositionType" value="${AdSpaceType}"/>
        </td>
	</tr>
    <tr>
      <td  align="center" valign="top">广告位名称：</td>
      <td width="30%">
      <input name="AdSpaceName" type="text" id="AdSpaceName" value="${AdSpaceName}" size="20" verify="广告位名称|NotNull"/>
      &nbsp;&nbsp;&nbsp;
      	是否是弹窗广告
      <input name="prop1" type="checkbox" id="prop1" value="popup" size="20" onclick="choosePopup()"/>
      <input name="prop" id="prop" value="${prop1}" type="hidden" />
      </td>
    </tr>
    <tr>
      <td  align="center" valign="top">广告位类型：</td>
      <td colspan="2">
		  <z:tree id="tree1" style="width:280px;height:300px" method="com.sinosoft.cms.dataservice.AdvertiseManage.editPosTypes">
	      	<p cid='${CodeValue}' >
	        <input type="checkbox" name="AdSpaceType" value='${CodeValue}' ${checked}>
	        ${CodeValue}(${CodeName})</p>
	      </z:tree>
      </td>
    </tr>
    <tr id="trSize">
      <td align="center" valign="top">广告位尺寸：</td>
      <td colspan="2">&nbsp;&nbsp;&nbsp;宽度：<input name="AdvertiseWidth" type="text" id="AdvertiseWidth"  value="${AdvertiseWidth}" size="5"  /> px&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      高度：<input name="AdvertiseHeight" type="text" id="AdvertiseHeight" value="${AdvertiseHeight}" size="5"  /> px</td>
    </tr>
    <tr>
      <td  align="center" valign="top">广告位描述：</td>
      <td colspan="2"><textarea id="AdSpaceDesc" name="AdSpaceDesc" cols="52" rows="4">${AdSpaceDesc}</textarea></td>
    </tr>
    <tr id="alertTr" style="display:none;">
      <td>&nbsp;</td>
      <td align="left" colspan="2"><font color="#FF0000">注意：变更广告位类型将导致广告位下已有的广告内容清空</font></td>
    </tr>
</table>
</form>
</z:init>
</body>
</html>