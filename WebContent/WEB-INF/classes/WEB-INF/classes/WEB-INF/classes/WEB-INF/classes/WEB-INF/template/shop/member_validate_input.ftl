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
<div class="wrapper" >

<div class="wrapnormal"  style="height:320px">
<h3 class="tit01">找回密码</h3>
<div class="sheetrig2">
<ul >
<form id="passwordRecoverForm2" action="${base}/shop/member!modifyPassword.action" method="post" name="fm" onsubmit="return validateData();">
<input type="hidden" name="mobileOrEmail" value="${mobileNoOrEmail}" id="emailormailno">
<input type="hidden" name="oldtime" id="olddate"/>
<input type="hidden" name="times" id="timess" value="0"/>
<li><label>新密码：</label><input type="password" name="member.password" id="fpassword" class="inputstyle02" onMouseOver="this.className='inputstyle02b'" onMouseOut="this.className='inputstyle02'" style="width:350px;"  /><span id="check1" style="color:red;"></span></li>
<li><label>确认密码：</label><input type="password" name="rePassword" id="refpassword" class="inputstyle02" onMouseOver="this.className='inputstyle02b'" onMouseOut="this.className='inputstyle02'" style="width:350px;"  /><span id="check2" style="color:red;"></span></li>
<li><label>验证码：</label><input type="type" name="validateCode" id="revalidateCode" class="inputstyle02" onMouseOver="this.className='inputstyle02b'" onMouseOut="this.className='inputstyle02'" style="width:350px;"  /><span id="check3" style="color:red;"></span></li>
<li><label>&nbsp;&nbsp;</label><input type="button" id="reSendCode" class="btnorange02" value="重新发送验证码"/><span id="isOften" style="color:red;"></span></li>
<li><label>&nbsp;</label><em><input type="submit" id="sButton" class="btnorange02" value="保 存" hidefocus="true"/></em></li>
</ul>
</form>
</div>
</div>
</div> 
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<#include "/wwwroot/kxb/block/community_v1.shtml" />
<script type="text/javascript">
jQuery().ready(function() {


jQuery("#fpassword").blur(function(){
  var  fpassword=jQuery("#fpassword").val();
    if(fpassword==null || fpassword=="" || typeof(fpassword)=="undefined"){
   jQuery("#check1").html("密码不能为空");
   jQuery("#sButton").attr("disabled",true);
    return false;
    }
    
   if(/^[0-9]+$/.test(fpassword)){
	jQuery("#check1").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#sButton").attr("disabled",true);
	return false;
	}
	
 if(/^[a-zA-Z]+$/.test(fpassword)){
	jQuery("#check1").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#sButton").attr("disabled",true);
	return false;
	}
 if(/^[_]+$/.test(fpassword)){
	jQuery("#check1").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#sButton").attr("disabled",true);
	return false;
	}	
	
   if(!/^[a-zA-Z0-9_]{6,16}$/.test(fpassword)){
	jQuery("#check1").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#sButton").attr("disabled",true);
	return false;
	}
	jQuery("#check1").html("");
   jQuery("#sButton").attr("disabled",false);
  });

jQuery("#refpassword").blur(function(){
var refpassword=jQuery("#refpassword").val();
    if("undefined"==typeof(refpassword) || refpassword=="" ||refpassword==null){
    jQuery("#check2").html("重复密码不能为空");
    jQuery("#sButton").attr("disabled",true);
    return false;
    }
    else if(jQuery("#refpassword").val()!=jQuery("#fpassword").val()){
    jQuery("#check2").html("两次密码输入不一致");
    jQuery("#refpassword").attr("value","");
   jQuery("#fpassword").attr("value","");
    jQuery("#sButton").attr("disabled",true);
    return false;
    }else{
     jQuery("#check2").html("");
     jQuery("#sButton").attr("disabled",false);
    }
   });
   
 jQuery("#revalidateCode").blur(function(){
  var  revalidateCode=jQuery("#revalidateCode").val();
   if("undefined"==typeof(revalidateCode) || revalidateCode==""||revalidateCode==null){
   jQuery("#check3").html("验证码不能为空");
   jQuery("#sButton").attr("disabled",true);
   return false;
   }else{
    jQuery("#check3").html("");
     jQuery("#sButton").attr("disabled",false); 
   }
   });
   
 jQuery("#reSendCode").click(function(){
 var emailormailno = jQuery("#emailormailno").val();
 var times1= jQuery("#timess").val();
 if(times1==0){
 var olddate1 =new Date().getTime();
  jQuery("#olddate").attr("value",olddate1);
  jQuery("#timess").attr("value","1");
  jQuery.ajax({
		url: sinosoft.base+"/shop/member!sendPasswordRecoverMail.action?mobileNoOrEmail="+emailormailno,
		type: "post",
		dataType: "json"		
	});
  } else {
  var news = new Date().getTime();
  var old = document.getElementById("olddate").value;
  if((news-old)<(1000*60*2)){
jQuery("#isOften").html("您的操作频繁,请稍后再试!");
  }
  else{
  var olddate2 =new Date().getTime();
  jQuery("#olddate").attr("value",olddate2)
  var emailormailno = jQuery("#emailormailno").val();
  jQuery.ajax({
		url: sinosoft.base+"/shop/member!sendPasswordRecoverMail.action?mobileNoOrEmail="+emailormailno,
		type: "post",
		dataType: "json"		
	});
  }
  }
 });
});


  
function validateData(){
var fpassword=jQuery("#fpassword").val();
var refpassword=jQuery("#refpassword").val();
if(fpassword==""||"undefined"==typeof(fpassword)){
jQuery("#check1").html("密码不能为空");
return false;
}
if(/^[a-zA-Z]+$/.test(fpassword)){
	jQuery("#check1").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#sButton").attr("disabled",true);
	return false;
	}
if(/^[0-9]+$/.test(fpassword)){
	jQuery("#check1").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#sButton").attr("disabled",true);
	return false;
	}
if(/^[_]+$/.test(fpassword)){
	jQuery("#check1").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#sButton").attr("disabled",true);
	return false;
	}

if(!/^[0-9a-zA-Z_]{6,16}$/.test(fpassword)){
	jQuery("#check1").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#sButton").attr("disabled",true);
	return false;
	}

if(refpassword==""||"undefined"==typeof(refpassword)){
jQuery("#check2").html("确认密码不能为空");
return false;
}
if(jQuery("#refpassword").val()!=jQuery("#fpassword").val()){
    jQuery("#check2").html("两次密码输入不一致");
    jQuery("#refpassword").attr("value","");
    jQuery("#fpassword").attr("value","");
   return false;
    }
var  revalidateCode=jQuery("#revalidateCode").val();
   if("undefined"==typeof(revalidateCode) || revalidateCode==""||revalidateCode==null){
   jQuery("#check3").html("验证码不能为空");
   return false;}
   
return true;
} 
  
</script>
 			
</body>
</html>