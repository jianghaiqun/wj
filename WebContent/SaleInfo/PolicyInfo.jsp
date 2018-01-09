<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.PolicyInfo.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>日报信息查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript">
function statStaff(cancelFlag){
	if(cancelFlag == 2){
		cancelFlag = 0;
		DataGrid.setParam("dg1","show",1);
	}else{
		DataGrid.setParam("dg1","show",0);
	}
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","allDate","0");
	DataGrid.setParam("dg1","Markingkg",$V("Markingkg"));
	DataGrid.setParam("dg1","cancelFlag",cancelFlag);
	DataGrid.setParam("dg1","contant",$NV("contant"));
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","productName",$V("productName"));
	DataGrid.setParam("dg1","company",$V("company"));
	DataGrid.setParam("dg1","applicantName",$V("applicantName"));
	DataGrid.setParam("dg1","applicantMobile",$V("applicantMobile"));
	DataGrid.setParam("dg1","applicantMail",$V("applicantMail"));
	DataGrid.setParam("dg1","applicantIdentityId",$V("applicantIdentityId"));
	DataGrid.setParam("dg1","insureDateStart",$V("insureDateStart"));
	DataGrid.setParam("dg1","insureDateEnd",$V("insureDateEnd"));
	DataGrid.setParam("dg1","firstAppTimeFlag",$V("firstAppTimeFlag"));
	DataGrid.setParam("dg1","nextToLastAppTimeFlag",$V("nextToLastAppTimeFlag"));
	DataGrid.setParam("dg1","lastAppTimeFlag",$V("lastAppTimeFlag"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function statAll(cancelFlag){
	DataGrid.setParam("dg1","startDate","");
	DataGrid.setParam("dg1","endDate","");
	DataGrid.setParam("dg1","allDate","1");
	DataGrid.setParam("dg1","Markingkg",$V("Markingkg"));
	DataGrid.setParam("dg1","cancelFlag",cancelFlag);
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","productName",$V("productName"));
	DataGrid.setParam("dg1","company",$V("company"));
	DataGrid.setParam("dg1","applicantName",$V("applicantName"));
	DataGrid.setParam("dg1","applicantMobile",$V("applicantMobile"));
	DataGrid.setParam("dg1","applicantMail",$V("applicantMail"));
	DataGrid.setParam("dg1","applicantIdentityId",$V("applicantIdentityId"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
</script>
</head>
<body>
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
		<td>
		<table width="20" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding: 6px;" class="blockTd"><z:tree id="tree1"
					style="height:440px;width:120px;"
					method="com.sinosoft.platform.Channel.treeDataBind"
					level="3" lazy="true" resizeable="true">
					<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
					<p cid='${ID}' level="${TreeLevel}"><input type="checkbox"
						name="contant" id='contant_${ID}' value='${ChannelCode}'
						onClick="onCheck(this);"><label for="contant_${ID}"><span id="span_${ID}">${Name}</span></label></p>
				</z:tree></td>
			</tr>
		</table>
		</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
				 	<td>
						<table width="30%" border="0" cellspacing="0" >
						   <tr>
								<td>订单号：</td>
								<td> <input name="orderSn" type="text" id="orderSn" value="" ></td>
								<td>产品名称：</td>
								<td> <input name="productName" type="text" id="productName" value=""></td>
								<td>保险公司：</td>
								<td><z:select id="company">${company}</z:select></td>
								<td>投保人姓名：</td>
					            <td><input name="applicantName" type="text" id="applicantName" value="" ></td>
					            <td></td>
							</tr>
							<tr>
								<td>投保人手机号：</td>
								<td><input name="applicantMobile" type="text" id="applicantMobile" value=""></td>
								<td>投保人E-mail：</td>
					            <td><input name=applicantMail type="text" id="applicantMail" value="" ></td>
								<td>投保人证件号码：</td>
								<td><input name="applicantIdentityId" type="text" id="applicantIdentityId" value="" ></td>
							</tr>
							<tr>
								<td>投保人投保时间：</td>
								<td><input value="" type="text" id="insureDateStart" 
										name="insureDateStart" ztype="Date" class="inputText" size="14" ></td>
								<td>到：</td>
								<td><input value="" type="text" id="insureDateEnd" 
										name="insureDateEnd" ztype="Date"  class="inputText" size="14" ></td>
								<td colspan="4">
									<input type="checkbox" name="firstAppTimeFlag" id="firstAppTimeFlag" value="Y" title="首次投保时间" />首次投保时间
									<input type="checkbox" name="nextToLastAppTimeFlag" id="nextToLastAppTimeFlag" value="Y" onclick="memberChannelOn()" title="倒数第二次投保时间" />倒数第二次投保时间
									<input type="checkbox" name="lastAppTimeFlag" id="lastAppTimeFlag" value="Y" onclick="memberChannelOn()" title="最后一次投保时间" />最后一次投保时间
								</td>
							</tr>
							<tr>
								<td>统计时间：</td>
								<td><input value="${yesterday}" type="text" id="startDate" 
										name="startDate" ztype="Date" class="inputText" size="14" ></td>
								<td>到：</td>
								<td><input value="${yesterday}" type="text" id="endDate" 
										name="endDate" ztype="Date"  class="inputText" size="14" ></td>
								<td>订单所属险种：</td>
								<td><z:select id="Markingkg">${Markingkg}</z:select></td>
								<td colspan="2">
									<z:tbutton onClick="statStaff(0)"> <img src="../Icons/icon031a1.gif" />统计</z:tbutton>
									<z:tbutton onClick="statStaff(1)"> <img src="../Icons/icon031a1.gif" />撤单统计</z:tbutton>
									<z:tbutton onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
									<z:tButtonPurview>${_SaleInfo_PolicyInfo_Button}</z:tButtonPurview>
								</td>
							</tr>
				
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.PolicyInfo.dg1DataBind" autoFill="true"  size="20" lazy="true">
						<table width="100%" cellspacing="0" cellspacing="0" class="dataTable" >
						<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo"><b>序号</b></td>
								<td width="120">支付时间</td>
								<td width="100">会员ID</td>
								<td width="200" ><b>保单号</b></td>
								<td width="120" ><b>订单号</b></td>
								<td width="50" ><b>投保人</b></td>
								<td width="150" ><b>投保人证件号码</b></td>
								<td width="30" ><b>性别</b></td>
								<td width="100" ><b>出生日期</b></td>
								<td width="100" ><b>手机号码</b></td>
								<td width="150" ><b>电子邮箱</b></td>
								<td width="150" ><b>投保人投保时间</b></td>
								<td width="150" ><b>首次投保时间</b></td>
								<td width="150" ><b>倒数第二次投保时间</b></td>
								<td width="150" ><b>最后一次投保时间</b></td>
								<td width="80" ><b>与投保人关系</b></td>
								<td width="60" ><b>被保险人</b></td>
								<td width="150" ><b>被保人证件号码</b></td>
								<td width="30" ><b>性别</b></td>
								<td width="100" ><b>出生日期</b></td>
								<td width="100" ><b>手机号码</b></td>
								<td width="150" ><b>电子邮箱</b></td>
								<td width="100" ><b>职业</b></td>
								<td width="200" ><b>旅游目的地</b></td>
								<td width="200" ><b>产品</b></td>
								<td width="60" ><b>保险期间</b></td>
								<td width="60" ><b>缴费年期</b></td>
								<td width="120" ><b>撤单时间</b></td>
								<td width="90" ><b>起保日期</b></td>
								<td width="90" ><b>终止日期</b></td>
								<td width="50" ><b>保费</b></td>
								<td width="80" ><b>实际支付保费</b></td>
								<td width="70" ><b>保单状态</b></td>
								<td width="60" ><b>订单状态</b></td>
								<td width="150" ><b>交易流水号</b></td>
								<td width="100" ><b>来源渠道</b></td>
								<td width="80" ><b>所属公司</b></td>
								<td width="70" ><b>所属险种</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td>${modifyDate}</td>   
								<td title="${MemberID}">${MemberID}</td>
								<td>${policyNo}</td>
								<td>${orderSn}</td>
								<td>${applicantName}</td>
								<td>${applicantIdentityId}</td>
								<td>${applicantSexName}</td>
								<td>${applicantBirthday}</td>
								<td>${applicantMobile}</td>
								<td title="${applicantMail}">${applicantMail}</td>
								<td>${insureDate}</td>
								<td>${firstAppTime}</td>
								<td>${nextToLastAppTime}</td>
								<td>${lastAppTime}</td>
								<td>${recognizeeAppntRelationName}</td>
								<td>${recognizeeName}</td>
								<td>${recognizeeIdentityId}</td>
								<td>${recognizeeSexName}</td>
								<td>${recognizeeBirthday}</td>
								<td>${recognizeeMobile}</td>
								<td title="${recognizeeMail}">${recognizeeMail}</td>
								<td>${Occu}</td>
								<td>${destinationCountryText}</td>
								<td title="${productName}">${productName}</td>
								<td>${ensureDisplay}</td>
								<td>${chargeYearName}</td>
								<td>${cancelDate}</td>
								<td>${startDate}</td>
								<td>${endDate}</td>
								<td>${totalAmount}</td>
								<td>${payPrice}</td>
								<td title="${appStatusName}">${appStatusName}</td>
								<td>${orderStatusName}</td>
								<td title="${paySn}">${paySn}</td>
								<td>${ChannelSn}</td>
								<td>${company}</td>
								<td>${risktype}</td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0" scrolling="auto"
		style="width:100%;height: 100%;display: none;"></iframe>
</body>
</html>
</z:init>