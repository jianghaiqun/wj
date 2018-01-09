<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">
function checkPWD(){
	var password=$V("NewPassword");
	var confirmPassword=$V("ConfirmPassword");
	if(password==confirmPassword){
		return true;
	} else{
		return false;
	}
}

//投保人二级地区下拉
function appAreaLink(){
	var parentNode = $V("applicantArea1");
	if(parentNode != null){
	    document.getElementById("applicantArea2").conditionValue=parentNode;
	    Selector.loadData(document.getElementById("applicantArea2"));
	}
}
//被保人地区二级地区下拉
function recAreaLink(){
	var parentNode = $V("recognizeeArea1");
	if(parentNode != null){
	    document.getElementById("recognizeeArea2").conditionValue=parentNode;
	    Selector.loadData(document.getElementById("recognizeeArea2"));
	}
}

function init(){
	var parentNode = $V("applicantArea1");
	var parentRecNode = $V("recognizeeArea1");
	if(parentNode == ''){
		document.getElementById("applicantArea0").style.display = 'none';
		document.getElementById("applicantArea1").style.display = 'none';
		document.getElementById("applicantArea2").style.display = 'none';
	}
	if(parentRecNode==''){
		document.getElementById("recognizeeArea0").style.display = 'none';
		document.getElementById("recognizeeArea1").style.display = 'none';
		document.getElementById("recognizeeArea2").style.display = 'none';
	}
}



</script>
</head> 
<body class="dialogBody" onload="init()">
<z:init method="com.wangjin.cms.orders.QueryMember.initDialog5">
	<form id="form2">
	<fieldset>
	<table>
		<tr>
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon021a1.gif" /> 投保人基本信息：</td>
				</tr>
				<tr>
					<td height="25" align="right" class="tdgrey1">投保人姓名：</td>
					<td height="25"><input name="applicantName" type="text"
						value="${applicantName}" style="width: 150px" class="inputText"
						id="applicantName" /></td>
					<td height="25" align="right" class="tdgrey1">投保人性别：</td>
					<td height="25"><z:select id="applicantSex"
						name="applicantSex" style="width:150px" listWidth="150"> ${applicantSex}</z:select>
					</td>
					
					<td height="25" align="right" class="tdgrey1">投保人电话：</td>
					<td height="25"><input name="applicantMobile" type="text"
						value="${applicantMobile}" style="width: 150px" class="inputText"
						id="applicantMobile" /></td><td>
						<input id="id_appnt" name="id_appnt" value="${id_appnt}" type="hidden"/></td>
				</tr>
				<tr>
				
					<td height="25" align="right" class="tdgrey1">投保人出生日期：</td>
					<td height="25"><input name="applicantBirthday" type="text"
						value="${applicantBirthday}" style="width: 150px"
						id="applicantBirthday" ztype="date" /></td>
						
					<td height="25" align="right" class="tdgrey1">投保人证件类型：</td>
					<td height="25"><z:select id="applicantIdentityType"
						name="applicantIdentityType" style="width:150px" listWidth="150"> ${applicantIdentityType}</z:select>
					</td>
					
					<td height="25" align="right" class="tdgrey1">投保人证件号码：</td>
					<td height="25"><input name="applicantIdentityId" type="text"
						value="${applicantIdentityId}" style="width: 150px"
						class="inputText" id="applicantIdentityId" /></td>


				</tr>
				<tr>


					<td height="25" align="right" class="tdgrey1">投保人电子邮箱：</td>
					<td height="25"><input name="applicantMail" type="text"
						value="${applicantMail}" style="width: 150px" class="inputText"
						id="applicantMail" /></td>
						
			<td height="25" align="right" class="tdgrey1" id="applicantArea0">投保人所在地：</td>
			<td height="25">
            <z:select id="applicantArea1"  name="applicantArea1"   code="areaHZ"  conditionField="parent_id" conditionValue="${orderSn},${informationSn},${productId}"
            	listWidth="80" style="width:70px" onChange="appAreaLink()"  lazy="true">${applicantArea1} </z:select>
            <z:select id="applicantArea2" name="applicantArea2" code="areaHZS" conditionField="parent_id" conditionValue="${recParentArea}" 
            	listWidth="80" style="width:80px" defaultblank="" lazy="true">${applicantArea2}</z:select>
            </td>
				</tr>
			</table>
		</tr>
		<tr>
			<table width="100%" cellpadding="2" cellspacing="0">
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon021a1.gif" /> 被保人基本信息：</td>
				<tr>
				
					<td height="25" align="right" class="tdgrey1">被保人姓名：</td>
					<td height="25"><input name="recognizeeName" type="text"
						value="${recognizeeName}" style="width: 150px" class="inputText"
						id="recognizeeName" /></td>
						
					<td height="25" align="right" class="tdgrey1">被保人性别：</td>
					<td height="25"><z:select id="recognizeeSex"
						name="recognizeeSex" style="width:150px" listWidth="150"> ${recognizeeSex}</z:select>
					</td>
					
					<td height="25" align="right" class="tdgrey1">被保人电话：</td>
					<td height="25"><input name="recognizeeMobile" type="text"
						value="${recognizeeMobile}" style="width: 150px" class="inputText"
						id="recognizeeMobile" /></td>
						<td><input id="id_insured" name="id_insured" value="${id_insured}" type="hidden"/></td>
						

				</tr>
				<tr>
					<td height="25" align="right" class="tdgrey1">被保人出生日期：</td>
					<td height="25"><input name="recognizeeBirthday" type="text"
						value="${recognizeeBirthday}" style="width: 150px"
						id="recognizeeBirthday" ztype="date" /></td>
					<td height="25" align="right" class="tdgrey1">被保人证件类型：</td>
					<td height="25"><z:select id="recognizeeIdentityType"
						name="recognizeeIdentityType" style="width:150px" listWidth="150"> ${recognizeeIdentityType}</z:select>
					</td>
					<td height="25" align="right" class="tdgrey1">被保人证件号码：</td>
					<td height="25"><input name="recognizeeIdentityId" type="text"
						value="${recognizeeIdentityId}" style="width: 150px"
						class="inputText" id="recognizeeIdentityId" /></td>
				</tr>

				<tr>
					<td height="25" align="right" class="tdgrey1">被保人电子邮箱：</td>
					<td height="25"><input name="recognizeeMail" type="text" value="${recognizeeMail}" style="width: 150px" class="inputText"
						id="recognizeeMail" /></td>
			        <td height="25" align="right" class="tdgrey1" id="recognizeeArea0">被保人所在地：</td>
		            <td height="25">
                    <z:select id="recognizeeArea1"  name="recognizeeArea1"   code="areaHZ1"  conditionField="parent_id"  conditionValue="${orderSn},${informationSn},${productId}"
            	    listWidth="80" style="width:70px" onChange="recAreaLink()" lazy="true">${recognizeeArea1} </z:select>
                    <z:select id="recognizeeArea2" name="recognizeeArea2" code="areaHZS1" conditionField="parent_id"  conditionValue="${recParentArea}" 
              	    listWidth="80" style="width:80px"   defaultblank=""  lazy="true">${recognizeeArea2}</z:select>
                    </td>
				</tr>
			</table>
		</tr>
	</table>
	</fieldset>
	</form>
</z:init>
</body>
</html>
