<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>密码找回</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link href="${shopStaticPath}/template/shop/css/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/MemberCenterSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">




<div class="wrapnormal">
			<h3 class="tit01">找回密码</h3>
<div class="sheetrig2">
<ul>
<form id="passwordRecoverForm" action="${base}/shop/member!sendPasswordRecoverMail.action" method="post" onsubmit="return validateData();">
<li><label>手机号/E-mail：</label><input type="text" name="mobileNoOrEmail" id="mobileNOOrEmail" class="inputstyle02" onMouseOver="this.className='inputstyle02b'" onMouseOut="this.className='inputstyle02'" style="width:350px;"  /></li>
<li><label>&nbsp;</label><em><input type="submit" id="submitButton" class="btnorange02" value="保 存" /></em></li>
</form>
</ul>



</div>
</div>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</div>
<script type="text/javascript">
jQuery().ready(function() {
 jQuery("#mobileNOOrEmail").blur(function(){
    $mobileNOOrEmail=jQuery("#mobileNOOrEmail").val();
   if("undefined"==typeof $mobileNOOrEmail || $mobileNOOrEmail==""){
    jQuery("#empty").html="手机号/邮箱不能为空";
    jQuery("#submitButton").attr("disabled",true);
    }else  
    jQuery("#submitButton").attr("disabled",false);    
  });
});	

function validateData(){
var sy=jQuery("#mobileNOOrEmail").val();
if(sy==""||"undefined"==typeof(sy)){
jQuery.tip("手机号或邮箱不能为空");
return false;
}


if(/^[0-9]+$/.test(sy)){
if(!/^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[5-9][0-9]{8}|147[0-9]{8})$/.test(sy)){
jQuery.tip("您输入的手机格式不正确，请重新输入!");
return false;
}
}else{
if(!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(sy)){
jQuery.tip("您输入的邮箱格式不正确，请重新输入!");
return false;
}
}
return true;
}
</script>	
</body>
</html>