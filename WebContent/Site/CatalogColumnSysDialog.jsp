<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
function changeType(){
	if($V("CatalogType")!=""){
		var dc = new DataCollection();
		dc.add("CatalogType",$V("CatalogType"));
		Server.sendRequest("com.sinosoft.cms.site.CatalogColumn.getCatalogValue",dc,function(response){
		if(response.get(0)==null){
			document.getElementById("NameList").value ="";
		}else{
			document.getElementById("NameList").value =response.get(0);
		}
		});
	}
}
Page.onLoad(function() {
	changeType();
}); 
</script>
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.site.CatalogColumn.initDialog1">
	<form id="form2">
	<table width="100%" height="100" border="0" align="center"
		cellpadding="4" cellspacing="" bordercolor="#DEDEDC"
		style="border-collapse:collapse;">
	<%-- 	<tr>
			<td width="100" height="10"></td>
			<td><input name="ColumnID" id="ColumnID" type="hidden"
				value="${ID}" /></td>
		</tr> --%>
		<tr>
			<td height="29" align="right">栏目类型：</td>
			<td><z:select id="CatalogType" onChange="changeType()" style="width:100px;">
      		${CatalogType}
      </z:select></td>
		</tr>
		<tr>
			<td height="29" align="right">字段列表：</td>
			<td>
			  <textarea id="NameList" readonly="readonly" style="width: 200PX ;height: 260PX;overflow-y:hidden" ></textarea>
			</td>
		</tr>
		<tr>
			<td align="right" valign="top">字段沿用：</td>
			<td valign="top"><z:select id="Extend" style="width:150px;">
				<span value="1">仅本栏目</span>
				<span value="2">所有子栏目沿用相同设置</span>
				<span value="3">所有栏目沿用相同设置</span>
			</z:select></td>
		</tr>
	</table>
	</form>
</z:init>
</body>
</html>
