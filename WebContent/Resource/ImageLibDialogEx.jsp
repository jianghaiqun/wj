<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function onReturnBack(returnPaths){
	if(returnPaths){
		var dc = new DataCollection();
		dc.add("Time",$V("Time"));
		dc.add("Path",returnPaths);
		Server.sendRequest("com.sinosoft.cms.resource.Image.setTempPath",dc,function(response){
			window.close();
		});
	}
}
</script>
</head>
<body>
<z:init>
<input type="hidden" id="Time" name="Time" value="${t}"/>
	<z:tab>
		<z:childtab id="ImageBrowse" selected="true"
			src="ImageBrowseEx.jsp?_CatalogID=${CatalogID}&Search=${Search}&SelectType=${SelectType}">
			<img src="../Icons/icon030a11.gif" />
			<b>图片浏览</b>
		</z:childtab>
		<z:childtab id="ImageUpload" src="ImageUploadEx.jsp">
			<img src="../Icons/icon030a13.gif" />
			<b>上传图片</b>
		</z:childtab>
	</z:tab>
</z:init>
</body>
</html>
