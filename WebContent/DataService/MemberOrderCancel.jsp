<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单信息撤销</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var KID = "<%=KID%>";
function doSearch(){
	var sd = $V("createDate");
	var ed = $V("endCreateDate");
	var pn = $V("productName");
	var od = $V("ordersn"); 
	var pn = $V("policyNo");
	var as = $V("appStatus");
	var ins = $V("insuredname");
	var em = $V("email");
	var mo = $V("mobile");
	var io = $V("idNO");
	var md = $V("mid");
	var all=sd+ed+pn+od+pn+as+ins+em+mo+io+md;
	if(all == null || all == '' || all == '-1'){
		Dialog.alert("查询条件不能为空,至少要有一个查询条件！");
		return;
	}
	if(ed < sd){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","createDate",$V("createDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));
	DataGrid.setParam("dg1","productName",$V("productName"));
	DataGrid.setParam("dg1","mobile",$V("mobile"));
	DataGrid.setParam("dg1","email",$V("email"));
	DataGrid.setParam("dg1","ordersn",$V("ordersn"));
	DataGrid.setParam("dg1","policyNo",$V("policyNo"));
	DataGrid.setParam("dg1","appStatus",$V("appStatus"));
	DataGrid.setParam("dg1","insuredname",$V("insuredname"))
	DataGrid.setParam("dg1","mid",$V("mid"));
	DataGrid.setParam("dg1","idNO",$V("idNO"))
	DataGrid.loadData("dg1");
}
function doblank(){
	createDate.value="";
	endCreateDate.value="";
	productName.value="";
	mobile.value="";
	email.value="";
	document.getElementById("appStatus").value="-1";
	insuredname.value="";
	appStatus.value="-1";
	mid.value="";
	idNO.value="";
	
}
function cancel(){
	Dialog.confirm("您确认要申请退保吗?",function(){
		if(!checked()){
			return ;
		}
		var dc = new DataCollection();
		var arr = DataGrid.getSelectedValue("dg1");
		dc.add("IDs", arr.join());
		Server.sendRequest("com.sinosoft.orders.CancelCont.dealCancel", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
						} 
							
					});
				});
	});
}
function checked(){
	var flag=true;
	var arr1 = DataGrid.getSelectedData("dg1");
	var drs = arr1.Rows;
	if(arr1.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return false;
	}
	dr = drs[0];
	if(dr.get("appStatus")=="2"){
		Dialog.alert("订单已撤单！请选择正确的订单！");
		return false;
	}
	var channelsnEn = dr.get("channelsn");
	var mxbChannelSns = $V("mxbChannelSns");
	if(mxbChannelSns.indexOf(channelsnEn) !=-1  && channelsnEn!='b2b_qyer'){
		Dialog.alert("美行保订单需在美行保后台操作!");
		return;
	}
	//if(dr.get("appStatus")=="4"){
	//	Dialog.alert("订单在保险公司已撤单！请联系运维人员进行撤单！");
	//	flag =false;
	//	return flag;
	//}
	var nowDate = new Date();//获取当前系统时间
	var startDate = dr.get("startDate");
	var startDateTemp = startDate.split(" ");
	var arrStartDate = startDateTemp[0].split("-");
	var allStartDate = new Date(arrStartDate[0], arrStartDate[1] - 1,
			arrStartDate[2]);
	var allNowDate = new Date(nowDate.getFullYear(), nowDate.getMonth(),
			nowDate.getDate());

	var productId = dr.get("productId");
	if (dr.get("productId") == '224801001' || dr.get("productId") == '225701001'|| dr.get("productId") == '225701002'|| dr.get("productId") == '225701003') {
		if (allNowDate.getTime() < allStartDate.getTime()) {
			Dialog.alert("该订单需起保后，才可退保！");
			return false;
		}
		var Hesitation = dr.get("Hesitation");
		if (Hesitation != null && Hesitation != '') {
			var day = allStartDate.getDate() + parseInt(Hesitation);
			allStartDate.setDate(day);
			if (allNowDate.getTime() >= allStartDate.getTime()) {
				Dialog.alert("该订单犹豫期已过，不可撤单！");
				return false;
			} else {
				return true;
			}
		}
	}
	
	if ((allNowDate.getTime() >= allStartDate.getTime())) {
		//added by yuzj @20171103 for  昆仑特殊产品 未承保撤单
		if(productId=='106501039'||productId=='106501040'||productId=='106501041'){
		  //Dialog.alert("起保日早于当前时间，必须联系昆仑清除保额数据后再通知用户投保！");
		  return true;
		}else{
		  Dialog.alert("该订单已起保，不可撤单！");
		}
		return false;
	}
	
	return flag ;
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 订单撤销</td>
		          </tr>
				<tr>
				<td>
				  <z:init method="com.sinosoft.orders.CancelCont.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>订单号：</td>
							<td> <input name="ordersn" type="text" id="ordersn" value="" style="width:100px"></td>
							<td>保单号：</td>
							<td> <input name="policyNo" type="text" id="policyNo" value="" style="width:100px"></td>
							<td>产品名称：</td>
							<td> <input name="productName" type="text" id="productName" value="" style="width:100px"></td>
							<td>订单状态</td>
							<td>
							<z:select style="width:100px;"><select name="appStatus" id="appStatus"  > 
							<option value="-1">请选择订单状态</option> 
							<option value="1">已承保 </option>
		                 	<option value="2">已撤单 </option>
		                 	<option value="3">撤单失败(保险公司) </option>
		                 	<option value="4">撤单失败(网金公司) </option>
		                 	</select></z:select></td>
							<td>订单时间 从:</td>
							<td> <input name="createDate" type="text" id="createDate" value="${createDate}" style="width:100px" ztype="date"></td>
							<td>到</td>
							<td><input name="endCreateDate" type="text" id="endCreateDate" value="${endCreateDate}" style="width:100px" ztype="date"></td>
						</tr>
						<tr>
						<tr>
							<td>被保人：</td>
							<td> <input name="insuredname" type="text" id="insuredname" value="" style="width:100px" /></td>
							<td>证件号码：</td>
							<td> <input name="idNO" type="text" id="idNO" value="" style="width:100px" /></td>
							<td>手机号：</td>
							<td> <input name="mobile" type="text" id="mobile" value="" style="width:100px"></td>
							<td>E-mail：</td>
							<td> <input name="email" type="text" id="email" value="" style="width:100px" /></td>
							<td>会员ID：</td>
							<td> <input name="mid" type="text" id="mid" value="" style="width:100px" /></td>
		                	
						</tr>
						<tr>
						   <td  colspan="4">
			            	   <z:tbutton onClick="doSearch()"   id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton>
			            
			            	 <z:tbutton onClick="doblank()"><img src="../Icons/icon401a3.gif" width="20" height="20" />清空查询条件</z:tbutton>
			            	   <z:tbutton onClick="cancel()"  id="Submitbutton">
			            	     <img src="../Icons/icon021a4.gif" width="20" height="20"/>撤单
			            	   </z:tbutton>
			            	   
			            	</td>
		            	</tr>
		            	<input name="mxbChannelSns" type="hidden" id="mxbChannelSns" value="${mxbChannelSns}" >
		            	
		             </table>
		           </z:init>
				</td>
			</tr>
				<tr>
						  <td style="padding: 0px 5px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="6"
									class="blockTable" style="table-layout: fixed;" >
										<tr>
												<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
													<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.orderCancel" scroll="true" lazy="true" size="20" >
														<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" style="table-layout : fixed"  fixedHeight="480px"  afterdrag="afterRowDragEnd">
															<tr ztype="head" class="dataTableHead">
																<td  width="30" ztype="RowNo"><strong>序号</strong></td>
																<td  width="15"  ztype="selector" field="insuredSn">&nbsp;</td>
																<td  width="120"><b>订单号</b></td>
																<td  width="130"><b>被保人编号</b></td>
																<td  width="200"><b>产品名称</b></td>
																<td  width="120"><b>保单号</b></td>
																<td  width="80"><b>已付费用</b></td>
																<td  width="80"><b>订单状态</b></td>
																<td  width="80"><b>投保人姓名</b></td>
																<td  width="80"><b>被保人姓名</b></td>
																<td  width="100"><b>被保人证件号码</b></td>
																<td  width="120"><b>起保时间</b></td>
																<td  width="120"><b>止保时间</b></td>
																<td  width="100"><b>撤单日期</b></td>
																<td  width="200"><b>会员ID</b></td>
															</tr>
															<tr  style="background-color:#F9FBFC">
																<td width="10%">&nbsp;</td>
																<td>&nbsp;</td>
																<td>${ordersn}</td>
																<td>${insuredSn}</td>
																<td>${productName}</td>
																<td>${policyNo}</td>
																<td>${totalAmount}</td>
																<td>${appStatusName}</td>
																<td>${applicantName}</td>
																<td>${recognizeeName}</td>
																<td>${recognizeeIdentityId}</td>
																<td> ${startDate}</td>
																<td>${endDate} </td>
																<td>${cancelDate} </td>
																<td>${mid}</td>
																<td style="display:none"  >${channelsn}</td>
																
															</tr>
															<tr ztype="pagebar">
																<td height="25" colspan="11">${PageBar}</td>
															</tr>
														</table>
													</z:datagrid>
												</td>
										</tr>
								</table>
							</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
