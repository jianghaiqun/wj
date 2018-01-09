<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>  
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();
String serverContext = Config.getServerContext();%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>订单信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>

<script>
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
</script>
</head>
<body>	
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
					<tr>
                    <td>
                    <z:tbutton onClick="exportStaff()"><img src="../Icons/icon021a3.gif" />导出EXCEL</z:tbutton>
                    </td>
				</tr>
						<tr>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" />回购订单信息</td>
		          </tr>
				<tr>
				<td>

				</td>
			</tr>
			  <tr>
				<td style="padding: 0px 5px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.infoseeker.UserTotalByDayUI.dg1DataBind" size="20" scroll="true" >
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
						        <td width="120" style="text-align:center;"><b>订单编号</b></td>
						        <td width="50" style="text-align:center;"><b>投保人</b></td>
						        <td width="70" style="text-align:center;"><b>被保险人数</b></td>
						        <td width="120" style="text-align:center;"><b>起保日期</b></td>
						        <td width="120" style="text-align:center;"><b>修改时间</b></td>
								<td width="150" style="text-align:center;"><b>产品名称</b></td>
								<td width="55" style="text-align:center;"><b>产品计划</b></td>
								<td width="50" style="text-align:center;"><b>保费</b></td>
								<td width="50" style="text-align:center;"><b>已付费</b></td>
								<td width="110" style="text-align:center;"><b>会员ID</b></td>
								<td width="55" style="text-align:center;"><b>订单状态</b></td>
								<td width="145" style="text-align:center;"><b>优惠券号</b></td>
								<td width="100" style="text-align:center;"><b>优惠券优惠金额</b></td>
								<td width="145" style="text-align:center;"><b>活动号</b></td>
								<td width="80" style="text-align:center;"><b>活动优惠金额</b></td>
								<td width="90" style="text-align:center;"><b>积分抵值金额</b></td>
								<td width="80" style="text-align:center;"><b>订单来源渠道</b></td>
								<td width="150" style="text-align:center;"><b>会员来源渠道</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td style="text-align:center;" title="${orderSn}">${orderSn}</td>
								<td style="text-align:center;" title="${ApplicantName}">${ApplicantName}</td>
								<td style="text-align:center;" title="${recognizeeNu}">${recognizeeNu}</td>
								<td style="text-align:center;" title="${svalidate}">${svalidate}</td>
								<td style="text-align:center;" title="${ModifyDate}">${ModifyDate}</td>
								<td title="${ProductName}">${ProductName}</td>
								<td title="${planName}">${planName}</td>
								<td style="text-align:right;" title="${totalAmount}">${totalAmount}</td>
								<td style="text-align:right;" title="${PayPrice}">${PayPrice}</td>
								<td style="text-align:right;" title="${mid}">${mid}</td>
								<td style="text-align:center;" title="${orderstatusname}">${orderstatusname}</td>
								<td style="text-align:right;" title="${couponSn}">${couponSn}</td>
								<td style="text-align:right;" title="${parValue}">${parValue}</td>
								<td style="text-align:right;" title="${ActivitySn}">${ActivitySn}</td>
								<td style="text-align:right;" title="${orderActivity}">${orderActivity}</td>
								<td style="text-align:right;" title="${offsetPoint}">${offsetPoint}</td>
								<td style="text-align:center;" title="${channelsn}">${channelsn}</td>
								<td style="text-align:center;" title="${fromWap}">${fromWap}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
								<input name="id" type="hidden" id="id" value="${id}" style="width:150px">
								<input name="insuredid" type="hidden" id="insuredid" value="${insuredid}" style="width:150px">
								<input name="OrderSn" type="hidden" id="OrderSn" value="${OrderSn}" style="width:150px">
								<input name="insuranceCompany" type="hidden" id="insuranceCompany" value="${insuranceCompany}" />
								
							</tr>
						</table>
					</z:datagrid></td>
					</tr>
				  </table>
				</td>
				</tr>
			</table>
	 </tr>
	</table>
	 <input name="id" type="hidden" value="${registDate}" id="registDate" />
	</form>
</body>
</html>
