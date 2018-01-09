<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function changeStatus(){
	var status = $V("status");
	if (status == '06') {
		$("finishTR").show();
		$("returnTR").hide();
	} else if (status == '03' || status == '07') {
		$("returnTR").show();
		$("finishTR").hide();
	} else {
		$("finishTR").hide();
		$("returnTR").hide();
	}
}

function lookMessage(){
	if ($V("status") == '03') {
		Dialog.alert("尊敬的开心保用户，您好！您的"+$V("claimsItemsName")+"理赔申请经审核需要补充："+$V("returnDesc")+" 如有疑问可拨打4009-789-789咨询。理赔单号："+$V("claimsNo"));
	} else {
		Dialog.alert("尊敬的开心保用户，您好！您的"+$V("claimsItemsName")+"理赔申请，被拒绝！ 拒赔原因："+$V("returnDesc")+" 如有疑问可拨打4009-789-789咨询。理赔单号："+$V("claimsNo"));
	}
}



</script>
</head>
<body class="dialogBody">
	<table width="100%" border="0" cellspacing="0" cellpadding="1"
		class="blockTable">
		<tr>
			<td valign="middle" class="blockTd"><img
				src="../Icons/icon018a6.gif" width="20" height="20" />理赔申请信息</td>
		</tr>
		<tr>
			<td><z:init method="com.sinosoft.cms.payment.PaymentManage.initDialog">
					<form id="form2">
						<table width="100%" cellpadding="0" cellspacing="0" align="center">
							<tr>

								<td height="25" align="right" class="tdgrey1" width="100px">理赔单号：</td>
								<td height="25"><input name="claimsNo" type="text"
									value="${claimsNo}" id="claimsNo" style="width: 150px" disabled /></td>
								<td height="25" align="right" class="tdgrey1" width="80px">理赔项目：</td>
								<td height="25"><input type="text" id="claimsItemsName"
									name="claimsItemsName" value="${claimsItemsName}" disabled /></td>
								<td height="25" align="right" class="tdgrey1">订单号：</td>
								<td height="25"><input type="text" id="orderSn"
									name="orderSn" value="${orderSn}" disabled /></td>
								<td height="25" align="right" class="tdgrey1">保单号：</td>
								<td height="25"><input name="policyNo" type="text"
									value="${policyNo}" id="policyNo" style="width: 150px" disabled /></td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">理赔状态：</td>
								<td height="25">
								  <z:select name="status" id="status" style="width:80px;" value="${status}" onChange="changeStatus();">${ClaimsStatus}</z:select>
								</td>
								<td height="25" align="right" class="tdgrey1">案件类型：</td>
								<td height="25"> <z:select name="caseType" id="caseType" style="width:80px;" value="${caseType}">${ClaimsCaseType}</z:select></td>
								<td height="25" align="right" class="tdgrey1">申请日期：</td>
								<td height="25"><input name="applicationDate" type="text"
									value="${applicationDate}" id="applicationDate" disabled /></td>
								<td height="25" align="right" class="tdgrey1">更新日期：</td>
								<td height="25"><input name="ModifyDate" type="text"
									value="${ModifyDate}" id="ModifyDate" disabled /></td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">会员ID：</td>
								<td height="25" width="230px"><input name="memberId" type="text"
									value="${memberId}" id="memberId" style="width: 230px" disabled /></td>
								<td height="25" align="right" class="tdgrey1">被保人姓名：</td>
								<td height="25" width="90px"><input type="text" id="insureName"
									name="insureName" value="${insureName}" disabled/></td>
								<td height="25" align="right" class="tdgrey1" width="90px">被保人证件号：</td>
								<td height="25" width="90px"><input type="text" id="insureIdentityId"
									name="insureIdentityId" value="${insureIdentityId}" style="width: 150px" disabled/></td>
								<td height="25" align="right" class="tdgrey1" width="80px">申请人与被保人关系：</td>
								<td height="25" width="90px"><input type="text" id="insureRelation"
									name="insureRelation" value="${insureRelation}" /></td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">联系人姓名：</td>
								<td height="25"><input name="contactName" type="text"
									value="${contactName}" id="contactName" /></td>
								<td height="25" align="right" class="tdgrey1">联系人电话：</td>
								<td height="25"><input name="contactMobile" type="text"
									value="${contactMobile}" id="contactMobile" maxlength="11" verify="联系人电话|NotNull&&PHONE"/></td>
								<td height="25" align="right" class="tdgrey1">联系人邮箱：</td>
								<td height="25"><input name="contactMail" type="text"
									value="${contactMail}" id="contactMail" verify="联系人邮箱|NotNull&&Email"/></td>
							</tr>
							<tr>
								<td height="35" align="right" class="tdgrey1">银行卡开户行：</td>
								<td height="25" colspan="3"><input name="bankName" type="text"
									value="${bankName}" id="bankName" style="width: 430px" disabled /></td>
								<td height="25" align="right" class="tdgrey1">持卡人姓名：</td>
								<td height="25" width="90px"><input type="text" id="bankUserName"
									name="bankUserName" value="${bankUserName}" disabled/></td>
							</tr>
							<tr id="courierTR" style="display:;">
								<td height="25" align="right" class="tdgrey1">客户邮寄快递：</td>
								<td height="25"><input name="courierFirm" type="hidden"
									value="${courierFirm}" id="courierFirm" /><input name="courierFirmName" type="text"
									value="${courierFirmName}" id="courierFirmName" disabled/></td>
								<td height="25" align="right" class="tdgrey1">客户邮寄单号：</td>
								<td height="25"><input name="courierNumber" type="text"
									value="${courierNumber}" id="courierNumber" disabled /></td>
								<td height="25" align="right" class="tdgrey1">签收时间(填写发短信通知客户)：</td>
								<td height="25"><input name="receiveDate" id="receiveDate" type="text" 
									value="${receiveDate}" ztype="Date" class="inputText" size="14" /></td>
								<td height="25" align="right" class="tdgrey1">理赔项目分类：</td>
								<td height="25"><input name="claimsItemsTypeName" type="text"
									value="${claimsItemsTypeName}" id="claimsItemsTypeName" disabled/></td>
							</tr>
							<tr id="" style=";">
								<td height="25" align="right" class="tdgrey1">客户邮寄地址：</td>
								<td height="35" colspan="7"><input name="sendAddress" type="text" value="${sendAddress}" id="sendAddress" style="width: 650px" /> </td>
							</tr>
							<tr id="returnTR" style="display:none;">
								<td height="25" align="right" class="tdgrey1">退回原因：</td>
								<td height="25" colspan="7"><input name="returnDesc" type="text"
									value="${returnDesc}" id="returnDesc" style="width: 650px" />例如：请在微信理赔立即补充XXXX相关资料，以便顺利理赔。&nbsp;&nbsp;<div style="padding-top: 3px; padding-bottom: 6px; "><z:tbutton onClick="lookMessage();"><img src="../Icons/icon018a4.gif" />预览短信</z:tbutton></div> </td>
							</tr>
							<tr id="finishTR" style="display:none;">
								<td height="25" align="right" class="tdgrey1" width="80px">理赔款转账时间：</td>
								<td height="25"><input value="${giroDate}" type="text" id="giroDate" name="giroDate" ztype="Date"  class="inputText" size="14" ></td>
								<td height="25" align="right" class="tdgrey1">理赔金额：</td>
								<td height="25" ><input name="claimsMoney" type="text" value="${claimsMoney}" id="claimsMoney" /></td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1" width="80px">处理周期(单位：天)：</td>
								<td height="25"><input value="${cycle}" type="text" id="cycle" name="cycle"  class="inputText" size="14" ></td>
								<td height="25" align="right" class="tdgrey1">是否展示：</td>
								<td height="25" ><z:select name="isShowFlag" id="isShowFlag" style="width:80px;" value="${isShowFlag}">${showFlag}</z:select></td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">案件描述：</td>
								<td height="25" colspan="7"><textarea name="caseDesc" id="caseDesc" style="width:700px;height:100px">${caseDesc}</textarea></td>
							</tr>
						</table>
						<input name="id" type="hidden" value="${id}" id="id" />
						<input name="claimsItemsId" type="hidden" value="${claimsItemsId}" id="claimsItemsId" />
						<input name="productId" type="hidden" value="${productId}" id="productId" />
					</form>
				</z:init></td>
		</tr>

	</table>
</body>
</html>