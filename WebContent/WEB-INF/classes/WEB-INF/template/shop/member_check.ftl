<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>校验信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link href="${shopStaticPath}/template/shop/css/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/MemberCenterSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css" />
<script type="text/javascript">
function validateData(){
	var name=jQuery("#name").val();
	var NO=jQuery("#phoneno").val();
	if(name==""||name==null||typeof(name)=="undefined"){
		jQuery("#check").html("投保人信息不能为空");
		return false;
	}else{
		jQuery("#check").html("");
	}
	if(NO==""||typeof(NO)=="undefined"||NO==null){
		jQuery("#check2").html("手机号不能为空");
		return false;
	}else{
		var regu1 = /^[1][3-8][0-9]{9}$/;
		if (!regu1.test(NO)) {
			 jQuery("#check2").html("请输入正确的手机号");
			 return false;
		}else{
		     jQuery("#check").html("");
		}
	}
	return true;
}
 
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>

<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

	<div class="wrapnormal" style="margin-top:20px;">
		<h3 class="tit01">校验信息</h3>
		<div class="sheetrig2">
		
			<form action="member_center!validateData.action" method="get" onsubmit="return validateData();">
				<ul>
				<li><label style="font-size:13px;">投保人姓名：</label>
					<input type="text"  name="appName" id="name" class="inputstyle02" /> <span id="check" style="color:red;font-size:12px;"></span> 
				</li>
				
				<li><label style="font-size:13px;">手机号：</label>
					<input type="text"  name="mobileNO" id="phoneno" class="inputstyle02" /><span id="check2" style="color:red;font-size:12px;"></span>
				</li>
					<input type="hidden" name="codeType" value="${codeType}"/>
					<input type="hidden" name="serialNO" value="${serialNO}"/>
				<li><label>&nbsp;</label><em><input type="submit" id="sButton" class="btnorange02" value="确认" hidefocus="true"/></em></li>
				</ul>
			</form>
		</div>
	</div>
</div>		
		
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</body>
</html>