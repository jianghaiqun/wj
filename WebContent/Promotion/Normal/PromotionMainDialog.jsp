<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分管理</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="../../Framework/Controls/Tabpage.js"></script>
<script>
var currentTab = "Config";
var grantType ;
var currentTreeItem,lastTreeItem;
Page.onLoad(function(){
});
function getModelType(){
	var Info = Tab.getChildTab("BaseInfo").contentWindow;
	var ModuleType=Info.$V("ModuleType");
	if(ModuleType=='02'||ModuleType=='04'){
		return "true";
	}else{
		return "false";
	}
}
function chooseChild(id){
	Tab.onChildTabClick(id);
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
			<z:init method="com.sinosoft.cms.document.PromotionManage.initDialog">
				<z:tab>
					<z:childtab id="BaseInfo" selected="true"  src="PromotionBaseInfoDialog.jsp?ModelDialogStatusFlag=${ModelDialogStatusFlag}&ID=${ID}"><img src="../../Icons/icon003a12.gif" /><b>基本信息</b></z:childtab>
					<z:childtab id="ProductInfo" src="PromotionProductDialog.jsp?ModelDialogStatusFlag=${ModelDialogStatusFlag}&ID=${ID}" > <img src="../../Icons/icon003a12.gif" /><b>产品列表</b></z:childtab>
				</z:tab>
			</z:init>
		</td>
	</tr>
</table>
</body>
</html>
