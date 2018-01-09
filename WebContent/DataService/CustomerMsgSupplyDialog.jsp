<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.CustomerMsgSupply.initDialog">
<form id="form2">
<table width="100%" height="100%" align="center" cellpadding="2" cellspacing="0" border="0">
    <tr>
      <td height="10"><input name="id" type="hidden" value="${id}" id="id" /></td>
      <td></td>
    </tr> 
    <tr>
      <td width="48%" valign="top" >
		   <fieldset>
		    	<legend><label>投保人信息</label></legend>
			      <table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
			        <tr>
			          <td width="28%" height="30" align="right" >姓名：</td>
			          <td ><input name="applicantName"  type="text" value="${applicantName}" id="applicantName" verify="姓名由2位以上中文汉字组成|Regex=^([\u4e00-\u9fa5]){2,}$&&NotNull" /></td>
			        </tr>
			        <tr>
						<td height="30" align="right">性别：</td>
						<td><z:select id="applicantSex" name="applicantSex" value="${applicantSex}" style="width:140px"  >${sexList}</z:select></td>
					</tr>
			        <tr>
			          <td height="30" align="right" >证件类型：</td>
			          <td><z:select id="applicantIdentityType" name="applicantIdentityType" value="${applicantIdentityType}" style="width:140px">${identityTypeList}</z:select></td>
			        </tr>
			        <tr>
			          <td height="30" align="right" >证件号：</td>
			          <td><input id="applicantIdentityId" name="applicantIdentityId" value="${applicantIdentityId}"  type="text"   verify="证件号由18位数字或者17位数字加X组成|Regex=^[0-9]{17}[0-9Xx]$&&NotNull" /></td>
			        </tr>
			        <tr>
			          <td height="30" align="right" >证件有效期：</td>
			          <td>
			          		<input name="applicantStartID" type="text" id="applicantStartID" value="${applicantStartID}" style="width:100px " ztype="date"> 至 
				         	<input name="applicantEndID" type="text" id="applicantEndID" value="${applicantEndID}" style="width:100px"  ztype="date">
				         	<input type="checkbox" id="applicantEndID_long" onclick="applicantEndIDChanel()"/>长期
			          </td>
			        </tr>
			        <tr>
			          <td height="30" align="right" >邮箱(选填)：</td>
			          <td><input id="applicantMail"  name="applicantMail"  type="text" value="${applicantMail}"  verify="电子邮件|Email"/></td>
			        </tr>
			        <tr>
			          <td height="30" align="right" >开户银行：</td>
			          <td><z:select id="bankCode" name="bankCode" value="${bankCode}" style="width:140px">${bankList}</z:select></td>
			        </tr>
			        <tr>
			          <td height="30" align="right" >账户名：</td>
			          <td >默认为投保人</td>
			        </tr>
			        <tr>
			          <td height="30" align="right" >卡号：</td>
			          <td ><input  id="bankNo" name="bankNo"  type="text" value="${bankNo}"   verify="卡号必须为数字|Number&&NotNull"/></td>
			        </tr>
			          <tr>
			          		<td height="30" align="right" >渠道：</td>
			         	 	<td ><z:select id="tbCustomerChannel" name="tbCustomerChannel" value="${tbCustomerChannel}" style="width:140px" code="tbCustomerChannel" lazy="false"  ></z:select></td>
			        </tr>
			      </table>
			   </fieldset>
      	</td>
	      <td  width="48%"  valign="top">
			  <fieldset>
				<legend><label>被保人信息</label></legend>
				  <table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
				  	  <tr>
				          <td width="28%" height="30" align="right" >与投保人关系：</td>
				          <td><z:select id="insuredRelation" name="insuredRelation" value="${insuredRelation}" onChange="insuredRelationInit()" style="width:140px">${relationList}</z:select></td>
			        </tr>
			        <tr>
			        	<td rowspan="1" colspan="2">
			        		<div id="recognizeeInfo" style="display:none">
			        			<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
							        <tr>
								          <td width="28%" height="30" align="right" >姓名：</td>
								          <td ><input name="recognizeeName"  type="text"  id="recognizeeName" value="${recognizeeName}"  verify="姓名由2位以上中文汉字组成|Regex=^([\u4e00-\u9fa5]){2,}$&&NotNull" /></td>
							        </tr>
							        <tr>
										<td height="30" align="right">性别：</td>
										<td><z:select id="recognizeeSex" name="recognizeeSex" value="${recognizeeSex}" style="width:140px"> ${sexList}</z:select></td>
									</tr>
								        <tr>
								          <td height="30" align="right" >证件类型：</td>
								          <td><z:select id="recognizeeIdentityType" name="recognizeeIdentityType" value="${recognizeeIdentityType}" style="width:140px">${identityTypeList}</z:select></td>
								        </tr>
								        <tr>
								          <td height="30" align="right" >证件号：</td>
								          <td><input name="recognizeeIdentityId"  type="text"  id="recognizeeIdentityId" value="${recognizeeIdentityId}" verify="证件号由18位数字或者17位数字加X组成|Regex=^[0-9]{17}[0-9Xx]$&&NotNull" /></td>
								        </tr>
								        <tr>
								          <td height="30" align="right" >证件有效期：</td>
								          <td >
								          	<input name="recognizeeStartID" type="text" id="recognizeeStartID" value="${recognizeeStartID}" style="width:100px"  ztype="date"> 至 
								         	<input name="recognizeeEndID" type="text" id="recognizeeEndID" value="${recognizeeEndID}" style="width:100px"  ztype="date">
								         	<input type="checkbox" id="recognizeeEndID_long" onclick="recognizeeEndIDChanel()"/>长期
								         </td>
								        </tr>
								        <tr>
								          <td height="30" align="right" >邮箱(选填)：</td>
								          <td><input name="recognizeeMail"  type="text"  id="recognizeeMail" value="${recognizeeMail}"  verify="电子邮件|Email"/></td>
								        </tr>
			        			</table>
			        		</div>
			        	</td>
			        </tr>
					  
				        <tr>
				          <td height="30" align="right" >手机号：</td>
				          <td><input name="recognizeeMobile"  type="text"  id="recognizeeMobile" value="${recognizeeMobile}" verify="手机号为11位数字|PHONE&&NotNull" /></td>
				        </tr>
				        <tr>
				          <td height="30" align="right" >地址：</td>
				          <td><input name="recognizeeAddress"  type="text"  id="recognizeeAddress" value="${recognizeeAddress}"  style="width: 250px;" verify="地址|NotNull"/></td>
				        </tr>
		        </table></fieldset>
		  </td>
    </tr>
	</table>
</form>
</z:init>
</body>
<script type="text/javascript">
function applicantEndIDChanel(){
		if($("applicantEndID_long") && $("applicantEndID_long").checked){
				$("applicantEndID").disable();
				$("applicantEndID").value="9999-12-31";
		} else {
				$("applicantEndID").enable();
				$("applicantEndID").value="";
		}
}
function recognizeeEndIDChanel(){
	if($("recognizeeEndID_long") && $("recognizeeEndID_long").checked){
			$("recognizeeEndID").disable();
			$("recognizeeEndID").value="9999-12-31";
	} else {
			$("recognizeeEndID").enable();
			$("recognizeeEndID").value="";
	}
}
function init(){
	var insuredRelationCode = jQuery("#insuredRelation").val();
	if(insuredRelationCode && "00"==insuredRelationCode){
		jQuery("#recognizeeName").removeAttr("verify");
		jQuery("#recognizeeIdentityId").removeAttr("verify");
		jQuery("#recognizeeMail").removeAttr("verify");
		jQuery("#recognizeeInfo").css("display","none");
	}else{
		jQuery("#recognizeeName").attr("verify","姓名由2位以上中文汉字组成|Regex=^([\u4e00-\u9fa5]){2,}$&&NotNull");
		jQuery("#recognizeeIdentityId").attr("verify","证件号由18位数字或者17位数字加X组成|Regex=^[0-9]{17}[0-9Xx]$&&NotNull");
		jQuery("#recognizeeMail").attr("verify","电子邮件|Email");
		jQuery("#recognizeeInfo").css("display","block");
	}
	//长期默认勾选框勾选
	var appEndID = jQuery("#applicantEndID").val();
	if("9999-12-31"==appEndID){
		jQuery("#applicantEndID_long").attr("checked",true);
	}
 	var recEndID = jQuery("#recognizeeEndID").val();
	if("9999-12-31"==recEndID){
		jQuery("#recognizeeEndID_long").attr("checked",true);
	}
}
function insuredRelationInit(){
	clear();
	init();
}
function clear(){
	jQuery("#recognizeeName").val("");
	jQuery("#recognizeeIdentityId").val("");
	jQuery("#recognizeeStartID").val("");
	jQuery("#recognizeeEndID").val("");
	jQuery("#recognizeeMail").val("");
}
</script>
</html>