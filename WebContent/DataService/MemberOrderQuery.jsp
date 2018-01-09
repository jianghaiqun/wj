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
<title>订单信息查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
var KID = "<%=KID%>";
//订单查询
function doSearch(){ 
	var sd = $V("createDate");
	var ed = $V("endCreateDate");
	var tT = $V("tbTradeSeriNo");
	var od = $V("orderSn");
	var pd = $V("productName");
	var os = $V("OrderStatus");
	var ap = $V("applicantName");
	var em = $V("email");
	var sm = $V("shipMobile");
	var io = $V("idNO");
	var md = $V("mid");
	var po = $V("policyNo");
	var rc = $V("remarkContent");
	var rs = $V("remarkSele");
	var ps = $V("paySn");
	var cs = $NV("contant");

	var all=sd+ed+tT+od+pd+os+ap+em+sm+io+md+po+rc+rs+ps+cs;
	if(all == null || all == '' || all == 'null'){
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
	DataGrid.setParam("dg1","shipMobile",$V("shipMobile"));
	DataGrid.setParam("dg1","email",$V("email"));
	DataGrid.setParam("dg1","tbTradeSeriNo",$V("tbTradeSeriNo"));
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","OrderStatus",$V("OrderStatus"));
	DataGrid.setParam("dg1","applicantName",$V("applicantName"));
	DataGrid.setParam("dg1","mid",$V("mid"));
	DataGrid.setParam("dg1","idNO",$V("idNO"));
	DataGrid.setParam("dg1","policyNo",$V("policyNo"));
	DataGrid.setParam("dg1","remarkContent",$V("remarkContent"));
	DataGrid.setParam("dg1","remarkSele",$V("remarkSele"));
	DataGrid.setParam("dg1","paySn",$V("paySn"));
	DataGrid.setParam("dg1","channelsn",$NV("contant"));
	DataGrid.setParam("dg1","memberid",$V("memberid"));
	DataGrid.setParam("dg1","description",$V("description"));
	DataGrid.loadData("dg1");
}
//订单变更
function orderChange(id,rid,orderSn,recognizeeSn,productName,parValue,channelsn) {
	var arr = DataGrid.getSelectedData("dg1");
	if(!arr || arr.getRowCount() == 0){
		Dialog.alert("请先选择要变更的条目！");
		return;
	}
	if(!arr||arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	if(id ==null){
		id=arr.get(0,"id");
	}
	if(rid ==null){
		rid=arr.get(0,"rid");
	}
	if(orderSn ==null){
		orderSn=arr.get(0,"orderSn");
	}
	if(recognizeeSn ==null){
		recognizeeSn=arr.get(0,"recognizeeSn");
	}
	if(productName ==null){
		productName=arr.get(0,"productName");
	}
	if(parValue ==null){
		parValue=arr.get(0,"parValue");
	}
	if(channelsn ==null){
		channelsn=arr.get(0,"channelsn");
	}
	
	if (channelsn == '积分商城') {
		Dialog.alert("积分兑换的订单不能变更！");
		return;
	}
	var svalidate = arr.get(0,"svalidate");
	var company = arr.get(0,"insuranceCompany");
	
	var orderstatusname = arr.get(0,"orderstatusname");
	
	if (orderstatusname.indexOf("已支付") == -1 
			&& orderstatusname.indexOf("有撤单") == -1 
			&& orderstatusname.indexOf("有作废") == -1
			&& orderstatusname.indexOf("有退保") == -1) {
		Dialog.alert("只有已支付或有撤单或有退保或有作废的订单才可变更！");
		return;
	}
	
	var nowDate = new Date();//获取当前系统时间
	var startDate = arr.get(0,"svalidate");
	var arrStartDate = startDate.split("-");
	var allStartDate = new Date(arrStartDate[0], arrStartDate[1] - 1,
			arrStartDate[2]);
	var allNowDate = new Date(nowDate.getFullYear(), nowDate.getMonth(),
			nowDate.getDate());
	if (allNowDate.getTime() >= allStartDate.getTime()) {
		Dialog.alert("已生效的订单不能变更！");
		return;
	}
	
	// 昆仑、合众、泰康、阳光、大都会、平安养老、新华 不支持变更
	if (company == '1065' || company == '1061' || company == '1015'
			|| company == '1087' || company == '1048'
			|| company == '1010' || company == '1014') {
		Dialog.alert("昆仑、合众、泰康、阳光、大都会、平安养老、新华的订单不支持变更！");
		return;
	}
	
	var diag = new Dialog("Diag7");
	diag.Width = 1200;
	diag.Height = 550;
	diag.Title = "订单变更";
	diag.URL = "DataService/MemberOrderChangeDialog.jsp?id=" + id
			+ "&rid=" + rid + "&orderSn=" + orderSn + "&recognizeeSn="
			+ recognizeeSn + "&productName=" + productName + "&parValue="
			+ parValue + "&company=" + company;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "订单变更";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
//保全记录
function baoquanedit(orderSn,id){
	 var arr = DataGrid.getSelectedData("dg1");
		if(!arr || arr.getRowCount() == 0){
			Dialog.alert("请先选择要查看的条目！");
			return;
		}
		if(!arr||arr.getRowCount()>=2){
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}
		if(orderSn ==null){
			orderSn=arr.get(0,"OrderSn");
		}
		if(id ==null){
			id=arr.get(0,"id");
		}
		
	var d = new Dialog("Diag1");
	d.Width = 550;
	d.Height = 450;
	d.Title = "保全记录";
	d.URL = "DataService/BaoquanEdit.jsp?orderSn="+orderSn+"&id="+id;
	d.ShowMessageRow = true;
	d.MessageTitle = "保全记录 ";
	d.show();
	d.OKButton.hide();
	d.CancelButton.value="关闭";
}

//订单查看
function showOrder(id,rid,orderSn,recognizeeSn,productName,parValue){
	 var arr = DataGrid.getSelectedData("dg1");
		if(!arr || arr.getRowCount() == 0){
			Dialog.alert("请先选择要查看的条目！");
			return;
		}
		if(!arr||arr.getRowCount()>=2){
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}
		if(id ==null){
			id=arr.get(0,"id");
		}
		if(rid ==null){
			rid=arr.get(0,"rid");
		}
		if(orderSn ==null){
			orderSn=arr.get(0,"orderSn");
		}
		if(recognizeeSn ==null){
			recognizeeSn=arr.get(0,"recognizeeSn");
		}
		if(productName ==null){
			productName=arr.get(0,"productName");
		}
		if(parValue ==null){
			parValue=arr.get(0,"parValue");
		}
		
		var diag = new Dialog("Diag6");
		diag.Width = 1200;
		diag.Height = 550;
		diag.Title = "订单详细信息查询";
		diag.URL = "DataService/MemberOrderDisplayDialog.jsp?id=" + id
				+ "&rid=" + rid + "&orderSn=" + orderSn + "&recognizeeSn="
				+ recognizeeSn + "&productName=" + productName + "&parValue="
				+ parValue;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "查看订单信息 ";
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value = "关闭";

	}

	//显示订单详细信息
	function showOrderDetail(orderSn) {
		var queryID = orderSn;
		//alert(KID);
		var KKID = '';
		var dc = new DataCollection();
		dc.add("KID", KID + queryID);
		var method = "cn.com.sinosoft.util.StringUtilC.md5Hex";

		Server.sendRequest(method, dc, function(response) {
			KKID = response.get("KID");
			var win = window.open(
					'../shop/order_config_new!linkOrderDetails.action?orderSn='
							+ queryID + "&KID=" + KKID, '_blank');
			win.focus();
		});

	}

	//订单关联会员
	function editMemderId() {

		var arr = DataGrid.getSelectedData("dg1");
		if (!arr || arr.getRowCount() == 0) {
			Dialog.alert("请先选择要编辑的条目！");
			return;
		}
		if (!arr || arr.getRowCount() >= 2) {
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}
		var diag = new Dialog("Diag3");
		diag.Width = 1200;
		diag.Height = 500;
		diag.Title = "订单信息修改";
		diag.URL = "DataService/MemberOrderEditMemberId.jsp";
		diag.OKEvent = editMemderIdUpdate;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "会员信息 ";
		diag.Message = "会员信息";
		diag.show();
		diag.CancelButton.value = "关闭";

	}
	//订单关联会员
	function editMemderIdUpdate() {
		var dt = $DW.DataGrid.getSelectedData("dg1");
		var memberEmail = dt.get(0, "email");
		var orderSn = DataGrid.getSelectedData("dg1").get(0, "orderSn");
		var dc = new DataCollection();
		var memberMobileNo = dt.get(0, "mobileno");
		dc.add("memberEmail", memberEmail);
		dc.add("orderSn", orderSn);
		dc.add("memberMobileNo", memberMobileNo);
		var memberid = $DW.DataGrid.getSelectedValue("dg1");
		dc.add("memberid", memberid);
		Server.sendRequest("com.wangjin.cms.orders.QueryOrders.updateMemberId",
				dc, function() {
					var response = Server.getResponse();
					Dialog.alert(response.Message);
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData('dg1');
					}
				});
	}

	//清空查询条件
	function doblank() {
		createDate.value = "";
		endCreateDate.value = "";
		productName.value = "";
		shipMobile.value = "";
		email.value = "";
		tbTradeSeriNo.value = "";
		orderSn.value = "";
		document.getElementById("OrderStatus").value = "";
		applicantName.value = "";
		mid.value = "";
		idNO.value = "";
		policyNo.value = "";
		remarkContent.value = "";
		document.getElementById("remarkSele").value = "";
	}
	function showchannel(){
		var check_flag=jQuery('#showchannel').is(':checked');
		if(check_flag==true){
			jQuery("#channeltree").show();
		}else{
			jQuery("#channeltree").hide();
		}
	}

	//订单修改
	function edit(id, rid, orderSn, recognizeeSn) {
		var arr = DataGrid.getSelectedData("dg1");
		if (!arr || arr.getRowCount() == 0) {
			Dialog.alert("请先选择要编辑的条目！");
			return;
		}
		if (!arr || arr.getRowCount() >= 2) {
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}
		if (id == null) {
			id = arr.get(0, "id");
		}
		if (rid == null) {
			rid = arr.get(0, "rid");
		}
		if (orderSn == null) {
			orderSn = arr.get(0, "orderSn");
		}
		if (recognizeeSn == null) {
			recognizeeSn = arr.get(0, "recognizeeSn");
		}
		var diag = new Dialog("Diag4");
		diag.Width = 1200;
		diag.Height = 500;
		diag.Title = "订单信息修改";
		diag.URL = "DataService/MemberOrderEditDialogtest.jsp?id=" + id
				+ "&rid=" + rid + "&orderSn=" + orderSn + "&recognizeeSn="
				+ recognizeeSn;
		diag.OKEvent = editSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "修改订单信息 ";
		diag.Message = "修改订单信息，修改投/被保人信息";
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value = "关闭";
	}

	function editSave() {
		var dc = $DW.Form.getData("form2");
		if ($DW.Verify.hasError()) {
			return;
		}
		if ($DW.Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.sinosoft.cms.dataservice.Member.dg1Edit3", dc,
				function() {
					var response = Server.getResponse();
					Dialog.alert(response.Message);
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData('dg1');
					}
				});
	}

	//保全批量导入
	function batchImport(){
		var d = new Dialog("Diag9");
		d.Width = 450;
		d.Height = 150;
		d.Title = "保全批量导入";
		d.URL = "DataService/BaoquanBatchImport.jsp";
		d.show();
		d.OKButton.hide();
		d.CancelButton.value="关闭";
	}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
		     <td id="channeltree" style="display: none">
				<table width="50" border="0" cellspacing="0" cellpadding="6"
					class="blockTable">
					<tr>
						<td style="padding: 6px;"  width="50" class="blockTd"><z:tree id="tree1"
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
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 订单信息</td>
		          </tr>
				<tr>
				<td>
				<z:init method="com.wangjin.cms.orders.QueryOrders.init">
								<table cellspacing="0" cellpadding="3">
									<tr>
										<td>渠道订单号：</td>
										<td><input name="tbTradeSeriNo" type="text"
											id="tbTradeSeriNo" value="" style="width: 100px"></td>
										<td>订单号：</td>
										<td><input name="orderSn" type="text" id="orderSn"
											value="" style="width: 100px"></td>
										<td>产品名称：</td>
										<td><input name="productName" type="text"
											id="productName" value="" style="width: 100px"></td>
										<td>订单状态</td>
										<td><z:select style="width:100px;" name="OrderStatus"
												id="OrderStatus">${OrderStatus}</z:select></td>
										<td>订单时间 从：</td>
										<td><input name="createDate" type="text" id="createDate"
											value="${createDate}" style="width: 100px" ztype="date"></td>
										<td>至：</td>
										<td><input name="endCreateDate" type="text"
											id="endCreateDate" value="${endCreateDate}"
											style="width: 100px" ztype="date"></td>
									</tr>
									<tr>
										<td>投/被保人：</td>
										<td><input name="applicantName" type="text"
											id="applicantName" value="" style="width: 100px" /></td>
										<td>E-mail：</td>
										<td><input name="email" type="text" id="email" value=""
											style="width: 100px" /></td>
										<td>手机号：</td>
										<td><input name="shipMobile" type="text" id="shipMobile"
											value="" style="width: 100px"></td>
										<td>证件号码：</td>
										<td><input name="idNO" type="text" id="idNO" value=""
											style="width: 100px" /></td>
										<td>会员ID：</td>
										<td><input name="mid" type="text" id="mid" value=""
											style="width: 100px" /></td>
										<td>保单号：</td>
										<td><input name="policyNo" type="text" id="policyNo"
											value="" style="width: 100px" /></td>
									</tr>
									<tr>
										<td>保全记录：</td>
										<td><input name="remarkContent" type="text"
											id="remarkContent" value="" style="width: 100px" /></td>
										<td>保全记录状态</td>
										<td><z:select style="width:100px;">
												<select name="remarkSele" id="remarkSele">
													<option value=""></option>
													<option value="1">有</option>
													<option value="2">无</option>
												</select>
											</z:select></td>
										<td>商户订单号：</td>
										<td><input name="paySn" type="text" id="paySn" value=""
											style="width: 100px" /> <input name="PolicyInfoChange"
											type="hidden" id="PolicyInfoChange"
											value="${PolicyInfoChange}" /></td>
										<td><input type="checkbox" onclick="showchannel()"
											id="showchannel" />订单渠道</td>
										<td>自定义活动：</td>
										<td><input name="description" type="text"
											id="description" value="" style="width: 100px" />
									</tr>
									<tr>
										<td colspan="8">
										<z:tbutton onClick="doSearch()"> <img src="../Icons/icon001a15.gif" />订单查询</z:tbutton>
										<z:tbutton onClick="edit()"> <img src="../Icons/icon001a4.gif" />订单修改</z:tbutton>
										<z:tbutton onClick="editMemderId()"> <img src="../Icons/icon001a10.gif" />关联会员</z:tbutton>
										<z:tbutton onClick="baoquanedit()"> <img src="../Icons/icon1.gif" />保全记录</z:tbutton>
										<z:tbutton onClick="showOrder()"> <img src="../Icons/icon001a12.gif" />订单查看</z:tbutton>
										<z:tbutton onClick="orderChange()"> <img src="../Icons/icon001a4.gif" />订单变更</z:tbutton>
										<z:tbutton onClick="batchImport()"> <img src="../Icons/icon001a13.gif" />批量导入</z:tbutton>
										<z:tbutton onClick="doblank()"> <img src="../Icons/icon001a3.gif" />清空查询条件</z:tbutton>
										</td>
				            		</tr>
									<input name="memberid" type="hidden" id="memberid"
										value="${memberid}" style="width: 150px">
								</table>
							</z:init>
						</td>
			</tr>
			  <tr>
				<td style="padding: 0px 5px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.cms.orders.QueryOrders.orderQuery" size="20" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="120" style="text-align:center;"><b>订单编号</b></td>
						        <td width="120" style="text-align:center;"><b>渠道订单号</b></td>
						        <td width="145" style="text-align:center;"><b>保单号</b></td>
						        <td width="50" style="text-align:center;"><b>投保人</b></td>
						        <td width="120" style="text-align:center;"><b>起保日期</b></td>
						        <td width="120" style="text-align:center;"><b>修改时间</b></td>
								<td width="150" style="text-align:center;"><b>产品名称</b></td>
								<td width="55" style="text-align:center;"><b>产品计划</b></td>
								<td width="50" style="text-align:center;"><b>保费</b></td>
								<td width="50" style="text-align:center;"><b>已付费</b></td>
								<td width="110" style="text-align:center;"><b>会员ID</b></td>
								<td width="55" style="text-align:center;"><b>订单状态</b></td>
								<td width="100" style="text-align:center;"><b>优惠券优惠金额</b></td>
								<td width="80" style="text-align:center;"><b>活动优惠金额</b></td>
								<td width="90" style="text-align:center;"><b>积分抵值金额</b></td>
								<td width="80" style="text-align:center;"><b>是否报案</b></td>
								<td width="80" style="text-align:center;"><b>渠道</b></td>
								<td width="150" style="text-align:center;"><b>自定义活动描述</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td style="text-align:center;">&nbsp;</td>
								<td style="text-align:center;" title="${orderSn}"><a  style="cursor: hand;" onClick="showOrderDetail('${OrderSn}')">${orderSn}</a></td>
								<td title="${tbTradeSeriNo}">${tbTradeSeriNo}</td>
								<td title="${policyNo}">${policyNo}</td>
								<td style="text-align:center;" title="${ApplicantName}">${ApplicantName}</td>
								<td style="text-align:center;" title="${svalidate}">${svalidate}</td>
								<td style="text-align:center;" title="${ModifyDate}">${ModifyDate}</td>
								<td title="${ProductName}">${ProductName}</td>
								<td title="${planName}">${planName}</td>
								<td style="text-align:right;" title="${totalAmount}">${totalAmount}</td>
								<td style="text-align:right;" title="${PayPrice}">${PayPrice}</td>
								<td style="text-align:right;" title="${mid}">${mid}</td>
								<td style="text-align:center;" title="${orderStatusName}">${orderStatusName}</td>
								<td style="text-align:right;" title="${parValue}">${parValue}</td>
								<td style="text-align:right;" title="${orderActivity}">${orderActivity}</td>
								<td style="text-align:right;" title="${offsetPoint}">${offsetPoint}</td>
								<td style="text-align:center;" title="${paymentReport}">${paymentReport}</td>
								<td style="text-align:center;" title="${channelsn}">${channelsn}</td>
								<td style="text-align:center;" title="${diyActivityDescription}">${diyActivityDescription}</td>
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
			</td>
		</tr>
	</table>
</body>
</html>
