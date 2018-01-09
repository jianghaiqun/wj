<!DOCTYPE html>
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />

<link href="http://localhost:8080/style/new_header.css" rel="stylesheet" type="text/css" />
<link href="http://localhost:8080/style/new_main.css" rel="stylesheet" type="text/css" />


<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>

<z:init method="com.sinosoft.cms.site.ImagePlayerPreview.init">
	<input name="ImagePlayerID" type="hidden" id="ImagePlayerID"
		value="${ImagePlayerID}" />

${_SWFObject}

</z:init>
 
<script type="text/javascript" src="http://localhost:8080/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/js/jquery.soChange-min.js"></script>
<script type="text/javascript" >
jQuery(function(){
	 //焦点图切换
	jQuery('#change_33 div.changeDiv').soChange({
		thumbObj:'#change_33 .ul_change_a2 span',
		thumbNowClass:'on',
		changeTime:4000//自定义切换时间为4000ms
	});
})
</script>




</body>
</html>
