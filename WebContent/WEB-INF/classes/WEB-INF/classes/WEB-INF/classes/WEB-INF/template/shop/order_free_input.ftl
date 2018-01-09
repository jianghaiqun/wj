<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="viewport" content="width=device-width,initial-scale=0.5,minimum-scale=0.5,maximum-scale=0.5,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="format-detection" content="telephone=no" />
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>赠险录入页</title>
<style>
/* =================================
			Common Style
   ================================= */
* {
	margin: 0; 
	padding: 0; 
}
a, a:hover {
	text-decoration:none;
}
html{
	height: 100%;
	border:0;
}
body{
	position: relative;
	height: 100%;
	background-color: #fff2ea;
	border:0;
	font-family: "Microsoft\ YaHei";
}
img{
	display: block;
	width: 100%;
	height: auto;
	border:0;
}
ul, ol { 
	list-style: outside none none; 
}

form {
	margin-top: 70px;
}
form ul {
	padding: 0 20px;
	text-align: center;
}
form ul li {
	margin-bottom: 25px;
	overflow: hidden;
}
form ul li label {
	display: inline-block;
	width: 160px;
	font-size: 32px;
	line-height: 70px;
	overflow: hidden;
	text-align: right;
}
form ul li input {
	width: 69%;
	height: 70px;
	padding: 0 10px;
	line-height: 70px;
	font-size: 32px;
	vertical-align: top;
}
form ul li #code { width: 26%; }
form ul li #verify { display: inline-block; width: 20%; height: 70px; vertical-align: top; line-height: 70px; background-color: red; }
form ul li span { display: inline-block; width: 22%; }
form .btn {
	margin: 40px 10% 0;
	border: 1px solid #d65601;
    border-radius: 5px;
    box-shadow: 0 2px 0 0 #d65601;
    color: #fff;
    cursor: pointer;
    font-size: 32px;
    text-align: center;
}
form .btn a {
	display: block;
	background: -moz-linear-gradient(center top , #fa7d14, #ed6c00) repeat scroll 0 0 rgba(0, 0, 0, 0);
	border: 1px solid #fcac6a;
	border-radius: 4px;
	height: 84px;
	line-height: 84px;
	color: #fff;
}
</style>
</head>
<body>
<div>
	<form id="freeForm" action="" method="post">
		<ul>
			<li><label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label><input id="applicantName" name="sdinformationAppnt.applicantName" type="text" value="${(sdinformationAppnt.applicantName)!}" /></li>
			<li><label>身份证号：</label><input id="applicantId" name="sdinformationAppnt.applicantIdentityId" type="text" value="" /></li>
			<li><label>手机号码：</label><input id="applicantMobile" name="sdinformationAppnt.applicantMobile" type="text" value="" /></li>
			<li><label>电子邮箱：</label><input id="applicantMail" name="sdinformationAppnt.applicantMail" type="text" value="" /></li>
			<li><label>验证码：</label><input id="verifycode" type="text" value="" name="j_captcha"/><img id="verify" src=""  style="cursor:hand;" alt="看不清，换一张"/><span></span></li>
		</ul>
		<p class="btn"><a href="#" onclick="toSubmit()" >提&#12288;&#12288;交</a></p>
	</form>
</div>
<script type="text/javascript" src="${shopStaticPath}/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.form.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/base.js"></script>
<script>
jQuery(function(){
	// 刷新验证码图片
	$("#verify").click(function(){
		WindowCaptchaImageRefresh();
	});	
});
// 点击刷新验证码图片
function WindowCaptchaImageRefresh() {
	document.getElementById("verify").setAttribute("src", "${base}/captcha.jpg?timestamp" + (new Date()).valueOf()); 
}
//先加载验证码
WindowCaptchaImageRefresh();
function toSubmit(){

     //提交前校验
     
     //校验验证码
	 jQuery.ajax({
	 url: sinosoft.base + "/shop/order_free!checkVerifyCode.action?verifyCode="+jQuery("#verifycode").val(),
	 dataType: "json",
	 async: false,
	 success: function(data) {
	 		var tFlag = data.VerifyFlag;
	 		if("Y"==tFlag){
		 		 //提交form
			     jQuery("#freeForm").attr("action",sinosoft.base + "/shop/order_free!save.action");
			     jQuery("#freeForm").submit();
	 		}else if("N"==tFlag){
	 			 var tMsg = data.Msg;
	 			 alert(tMsg);
	 			 //刷新验证码
	 			 WindowCaptchaImageRefresh();
	 		}
		}
	 });
}
</script>
</body>
</html>