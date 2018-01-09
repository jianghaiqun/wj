<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.sinosoft.schema.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>创建百科</title>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script charset="utf-8" src="../editorn/kindeditor.js"></script>
<script charset="utf-8" src="../editorn/lang/zh_CN.js"></script>
<script language="JavaScript" type="text/javascript">
	jQuery(document).ready(function(){
		jQuery("#add").click(function(){			
			var f = window.location.host;
			var tContent=getHTML();
			var tTitle=document.getElementById("title").value;
			var tType=document.getElementById("selectlink").value;
			var now=new Date().getTime();
			var data = {Title:encodeURI(tTitle,"UTF-8"), Content:encodeURI(tContent,"UTF-8"),Type:tType , operate:"BkSave" ,method:"save"};	
			//jQuery.post('http://localhost:8080/wj/shop.jsp?now='+now, data, function(json){//now表示每次点击都会发送新的请求	
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
	jQuery(function(){
		var f = window.location.host;
		 var now=new Date().getTime();
			var data = {Title:"", operate:"BkSave" ,method:"getList"};
			jQuery.getJSON('http://'+f+'/shop.jsp?now='+now, data, function(json){
			if(json!=null){
			   for(var i=0;i<json.map.length;i++){			  
				   jQuery("#selectlink").append( "<option value=\""+json.map[i].key+"\">"+json.map[i].value+"</option>" );
			      } 
			}
			});    
	});
</script>
</head>
<body>
     <div style="display:none"><a href="javascript:void(0);" id="headerShowLoginWindow" class="showLoginWindow">登录</a></div>
	<input type="hidden" id="ID">
	<form action="#" id="f1">
		词条名称：<input type="text" id="title"><br/>
		词条分类：<select id="selectlink" name="selectlink">
				    <option></option>
				 </select>
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
	<button id="add">创建词条</button>
</body>
</html>
