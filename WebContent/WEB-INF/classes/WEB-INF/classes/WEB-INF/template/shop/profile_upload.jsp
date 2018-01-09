<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员中心-头像上传</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<link href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>


<style type="text/css">
#member_commantable1 table,#member_commantable1 table td,#member_commantable1 table th
	{
	border: 1px solid #cccccc;
	border-collapse: collapse;
}
</style>
<script type="text/javascript">

	function loadImg(){
		var img = $('#jiazaidonghua');
		var img1 = $('#preview');
		img.attr('style', 'display:block');
    	img1.attr('style', 'display:none');
		
	}
	
	function onUploadImg(sender) {
		
		if (!sender.value.match(/.jpeg|.jpg|.gif|.png|.bmp/i)) {
			alert('图片格式无效！');
			return false;
		}
		var img = $('#jiazaidonghua');
		var img1 = $('#preview');
		img.attr('style', 'display:block');
		img1.attr('style', 'display:none');
		$('#uploadImgFrom').submit();
	}

	function loadImg(){
		var mes = $("#mes").val();
		if(mes != ""){
			window.parent.uploadImgMessage(mes);
		}
	}
</script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body>

<input id="mes" type="hidden" value="<s:property value="message"/>"/>
<s:iterator id="mList" value="#request.memberList">
<div id="preview_wrapper" class="landing_head">
<div id="preview_fake" >
<img id="jiazaidonghua" src="<%=Config.getValue("StaticResourcePath")%>/style/images/loading2.gif" width="70" height="70" style="display:none"/> 

<s:if test="#mList.headPicPath == null || #mList.headPicPath ==''">
<img src="<%=Config.getValue("StaticResourcePath")%>/images/header_mo_03.jpg" width="100" height="100"/> 
</s:if>
<s:else>
<img id="preview" src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#mList.headPicPath"/>" alt="头像" onload="loadImg()"/> 
</s:else>

</div>
</div>
</s:iterator>
<s:form action="profile!uploadImg.action" method="post" name="uploadImgFrom" id="uploadImgFrom" enctype="multipart/form-data" theme="simple" target="touxiang">
	<a class="btn_addPic" href="javascript:void(0);"><span>设置头像</span>
	<input  class="filePrew" id="upload_img" type="file" 
		onchange="onUploadImg(this)" tabindex="3" name="uploads" size="3" />
	</a>
</s:form>

</body>
</html>