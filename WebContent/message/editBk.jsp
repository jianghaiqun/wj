<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.sinosoft.schema.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>编辑百科</title>
<%
	ZCArticleSchema tZCArticleSchema = (ZCArticleSchema) session.getAttribute("ZCArticleSchema");
	String tTitle = tZCArticleSchema.getTitle();
	String tContent = tZCArticleSchema.getContent();
	String tId = tZCArticleSchema.getID() + "";
	System.out.print(tContent);
%>
<script type="text/javascript" src="../json/jquery.js"></script>
<script charset="utf-8" src="../editorn/kindeditor.js"></script>
<script charset="utf-8" src="../editorn/lang/zh_CN.js"></script>
<script language="JavaScript" type="text/javascript">
var tContent='<%=tContent%>';
var tTitle='<%=tTitle%>';
var tId='<%=tId%>';
jQuery(document).ready(function() {
	jQuery("#add").click(function() {
		var f = window.location.host;
		 	var now=new Date().getTime();
		 	var html=getHTML();
			var data = {Content: encodeURI(html,"UTF8") , ID:document.getElementById("ID").value,operate:"BkEdit" ,method:"save"};
			jQuery.getJSON('http://'+f+'/shop.jsp?now='+now, data, function(json){				
				if(json.test=='nologin'){
					alert("请先登陆用户")
					window.location('http://'+f+'/html/login.html').href;
					//document.getElementById("headerShowLoginWindow").click();
				}else{
					//jQuery('#f1')[0].reset();
					window.history.go(-1);
				}
			//jQuery('#f1')[0].reset(); 
		});     
	});
});

function getHTML() {
	return editor.html();
}
$(function(){
	editor.html(tContent);
	document.getElementById("title").value=tTitle;
	document.getElementById("ID").value=tId;
	
});
</script>
</head>
<body>
	<input type="hidden" id="ID">
	<form action="#">
		<input type="text" id="title" disabled="disabled">
		<textarea name="content" id="editor_id"
			style="width: 650px; height: 300px">
				  </textarea>
		<script type="text/javascript">
			var options = {
				filterMode : true
			};
			var editor = KindEditor.create('textarea[name="content"]', options);
		</script>
	</form>
	<button id="add">提交</button>
</body>
</html>
