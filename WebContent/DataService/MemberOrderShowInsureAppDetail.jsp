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

</script>
</head>
<body class="dialogBody">
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
						id="applicantName"   disabled="true"/></td>
						
					<td height="25" align="right" class="tdgrey1">投保人性别：</td>
					<td height="25"><z:select id="applicantSex"
						name="applicantSex" style="width:150px" listWidth="150" disabled="true"> ${applicantSex}</z:select>
					</td>
					
					<td height="25" align="right" class="tdgrey1">投保人电话：</td>
					<td height="25"><input name="applicantMobile" type="text"
						value="${applicantMobile}" style="width: 150px" class="inputText"
						id="applicantMobile"  disabled="true" /></td><!-- td>
						<input id="id_appnt" name="id_appnt" value="${id_appnt}" type="hidden"/></td-->
						

				</tr>
				<tr>
				
					<td height="25" align="right" class="tdgrey1">投保人出生日期：</td>
					<td height="25"><input name="applicantBirthday" type="text"
						value="${applicantBirthday}" style="width: 150px"
						id="applicantBirthday" ztype="date"  disabled="true" /></td>
						
					<td height="25" align="right" class="tdgrey1">投保人证件类型：</td>
					<td height="25"><z:select id="applicantIdentityType"
						name="applicantIdentityType" style="width:150px" listWidth="150" disabled="true"> ${applicantIdentityType}</z:select>
					</td>
					
					<td height="25" align="right" class="tdgrey1">投保人证件号码：</td>
					<td height="25"><input name="applicantIdentityId" type="text"
						value="${applicantIdentityId}" style="width: 150px"
						class="inputText" id="applicantIdentityId" disabled="true" /></td>
				</tr>
				<tr>


					<td height="25" align="right" class="tdgrey1">投保人电子邮箱：</td>
					<td height="25"><input name="applicantMail" type="text"
						value="${applicantMail}" style="width: 150px" class="inputText"
						id="applicantMail"  disabled="true" /></td>
						
                  	<td height="25" align="right" class="tdgrey1">投保人所在省/直辖市：</td>
						<td height="25"><z:select id="applicantArea1"
						name="applicantArea1" style="width:150px" listWidth="150"  disabled="true"> ${applicantArea1}</z:select>
					</td>
					
					<td height="25" align="right" class="tdgrey1">投保人所在地级市/区：</td>
						<td height="25"><z:select id="applicantArea2"
						name="applicantArea2" style="width:150px" listWidth="150" disabled="true"> ${applicantArea2}</z:select>
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
						id="recognizeeName"  disabled="true" /></td>
						
					<td height="25" align="right" class="tdgrey1">被保人性别：</td>
					<td height="25"><z:select id="recognizeeSex"
						name="recognizeeSex" style="width:150px" listWidth="150" disabled="true"> ${recognizeeSex}</z:select>
					</td>
					
					<td height="25" align="right" class="tdgrey1">被保人电话：</td>
					<td height="25"><input name="recognizeeMobile" type="text"
						value="${recognizeeMobile}" style="width: 150px" class="inputText"
						id="recognizeeMobile"  disabled="true"/></td>
						<td><input id="id_insured" name="id_insured" value="${id_insured}" type="hidden"/></td>
						

				</tr>

				<tr>
				
					<td height="25" align="right" class="tdgrey1">被保人出生日期：</td>
					<td height="25"><input name="recognizeeBirthday" type="text"
						value="${recognizeeBirthday}" style="width: 150px"
						id="recognizeeBirthday" ztype="date"  disabled="true" /></td>
						
					<td height="25" align="right" class="tdgrey1">被保人证件类型：</td>
					<td height="25"><z:select id="recognizeeIdentityType"
						name="recognizeeIdentityType" style="width:150px" listWidth="150" disabled="true"> ${recognizeeIdentityType}</z:select>

					</td>
					<td height="25" align="right" class="tdgrey1">被保人证件号码：</td>
					<td height="25"><input name="recognizeeIdentityId" type="text"
						value="${recognizeeIdentityId}" style="width: 150px"
						class="inputText" id="recognizeeIdentityId" disabled="true" /></td>
				</tr>
				<tr>
					<td height="25" align="right" class="tdgrey1">被保人电子邮箱：</td>
					<td height="25"><input name="recognizeeMail" type="text"
						value="${recognizeeMail}" style="width: 150px" class="inputText"
						id="recognizeeMail"  disabled="true" /></td>
						
					<td height="25" align="right" class="tdgrey1">被保人所在省/直辖市：</td>
						<td height="25"><z:select id="recognizeeArea1"
						name="recognizeeArea1" style="width:150px" listWidth="150" disabled="true"> ${recognizeeArea1}</z:select>
					</td>
					
					<td height="25" align="right" class="tdgrey1">被保人所在地级市/区：</td>
						<td height="25"><z:select id="recognizeeArea2"
						name="recognizeeArea2" style="width:150px" listWidth="150" disabled="true"> ${recognizeeArea2}</z:select>
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
