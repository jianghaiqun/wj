<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function menuSave(){
	Server.sendRequest("com.sinosoft.framework.utility.weixin.common.MenuManager.createMenu",null,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				
			}
		});
	});
}
function menuDelete(){
	Server.sendRequest("com.sinosoft.framework.utility.weixin.common.MenuManager.deleteMenu",null,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				
			}
		});
	});
}

</script>
</head>
<body>
<input type="button" name="Submitbutton" id="Submitbutton" value="生产菜单" onClick="menuSave()"><b></b>
<input type="button" name="Submitbutton" id="Submitbutton" value="删除菜单" onClick="menuDelete()">
</body>
</html>
