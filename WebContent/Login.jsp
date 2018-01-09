<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%> 
<%@page import="com.sinosoft.framework.*"%>
<%@page import="com.sinosoft.framework.utility.*"%>
<%@page import="com.sinosoft.platform.*"%>
<%@page import="com.sinosoft.framework.license.*"%>
<%
//if(session.getAttribute(com.sinosoft.zas.Constant.UserSessionAttrName)!=null){
//	Login.ssoLogin(request,response,((UserData)session.getAttribute(com.sinosoft.zas.Constant.UserSessionAttrName)).getUserName());
//}
%>
<%@page import="com.sinosoft.zas.UserData"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Config.getAppCode()%><%=Config.getAppName()%></title>
<link rel="shortcut icon" href="Include/favicon.ico" type="image/x-icon" />
<link href="Include/Default.css" rel="stylesheet" type="text/css">
<style>
.input1{ border:1px solid #84A1BD; width:100px; height:20px; line-height:23px;}
.input2{ border:1px solid #84A1BD; width:68px; height:20px; line-height:23px;}
.button1{
	border:none;
	width:70px;
	height:27px;
	line-height:23px;
	color:#525252;
	font-size:12px;
	font-weight:bold;
	background-image: url(images/bt001.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.button2{
	border:none;
	width:70px;
	height:27px;
	line-height:23px;
	color:#525252;
	font-size:12px;
	font-weight:bold;
	background-image: url(images/bt002.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.STYLE3 {
	color: #FF0000;
	font-weight: bold;
}
</style>
<script src="Framework/Main.js"></script>
<script>
window.onload =function() {
	if($("spanVerifyCode").innerText.trim()==""){
		var sb = [];
		sb.push("&nbsp;验证码：");
		sb.push("<img src=\"AuthCode.jsp?Height=18&Width=60\" alt=\"点击刷新验证码\" height=\"18\"  id=\"yzPic\"/");
		sb.push("align=\"absmiddle\" style=\"cursor:pointer;\" ");
		sb.push("onClick=\"this.src='AuthCode.jsp?Height=18&Width=60&'+new Date().getTime()\" />&nbsp; <input ");
		sb.push("name=\"VerifyCode\" type=\"text\" style=\"width:60px\" id=\"VerifyCode\" ");
		sb.push("class=\"inputText\" onfocus=\"this.select();\" />");
		$("spanVerifyCode").innerHTML = sb.join('');
		$("UserName").style.width = "80px";
		$("Password").style.width = "80px";
	}
}

function login(){
	if($V("UserName").trim()==""||$V("Password").trim()==""){
		alert("请输入用户名和密码");
		return false;
	}

	if(document.getElementById("VerifyCode")){
	if($V("VerifyCode").trim()==""||$V("VerifyCode").trim()==null){
		alert("验证码不能为空");
		document.getElementById("yzPic").click();//刷新验证码
		return false;
	}
	}
	var dc = Form.getData("form1");
	
	Server.sendRequest("com.sinosoft.platform.Login.submit",dc,function(response){
		if(response&&response.Status==0){
			alert(response.Message);
			//if($("spanVerifyCode").innerText.trim()==""){
			//	var sb = [];
			//	sb.push("&nbsp;验证码：");
			//	sb.push("<img src=\"AuthCode.jsp?Height=18&Width=60\" alt=\"点击刷新验证码\" height=\"18\"  id=\"yzPic\"/");
			//	sb.push("align=\"absmiddle\" style=\"cursor:pointer;\" ");
			//	sb.push("onClick=\"this.src='AuthCode.jsp?Height=18&Width=60&'+new Date().getTime()\" />&nbsp; <input ");
			//	sb.push("name=\"VerifyCode\" type=\"text\" style=\"width:60px\" id=\"VerifyCode\" ");
			//	sb.push("class=\"inputText\" onfocus=\"this.select();\" />");
			//	$("spanVerifyCode").innerHTML = sb.join('');
			//}
			//$("UserName").style.width = "80px";
			//$("Password").style.width = "80px";
			document.getElementById("yzPic").click();//刷新验证码
			
		}
	});
}
document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){ //摁下的是回车键
		login();  //调用点击登录按钮后的方法
	}
}
Page.onLoad(function(){
	if(window.top.location != window.self.location){
		window.top.location = window.self.location;
	}else{
		$("UserName").focusEx();
		//$S("UserName","admin");
		//$S("Password","admin");
	}
});
</script>
</head>
<body>
<form id="form1" method="post" style=" display:block;height:100%;">
<table width="100%" height="100%">
	<tr>
		<td align="center" valign="middle">
		<table style=" height:283px; width:620px; background:url(Platform/Images/loginbg_nozving.jpg) no-repeat 0px 0px;">
			<tr>
				<td>
				<div style="height:213px; width:620px;"></div>
				<div style="height:70px; width:620px;margin:0px auto 0px auto;">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top:8px;">
					<tr>
						<td align="center">用户名：
					    <input name="UserName" type="text" style="width:120px"
							id="UserName" class="inputText" onfocus="this.select();"/>
					    &nbsp;密码：
					    <input name="Password" type="password" style="width:120px"
							id="Password" class="inputText" onfocus="this.select();"/>
					    <span id='spanVerifyCode'></span>	
						&nbsp;<img src="Platform/Images/loginbutton.jpg" name="LoginImg" align="absmiddle" id="LoginImg" style="cursor:pointer"
							onClick="login();" /></td>
					</tr>
					<tr>
						<td height="23" align="center" valign="bottom">Copyright © 2007-2012 SinoSoft.com Inc. All rights reserved.</td>
					</tr>
				</table>
				</div>
				</td>
			</tr>
		</table>
		<br>
		<%if(License.needWarning()){%> 请注意，当前许可证将于 <span class="STYLE3"><%=DateUtil.toString(LicenseInfo.getEndDate())%></span>
		失效！ <a href="LicenseRequest.jsp">点击此处获得新的许可证。</a> <%}%>
		</td>
	</tr>
</table>
</form>
</body>
</html>