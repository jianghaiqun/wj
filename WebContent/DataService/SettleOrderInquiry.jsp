<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
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
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
var KID = "<%=KID%>";
//订单查询
function doSearch(){ 
	var sd = $V("createDate");
	var ed = $V("endCreateDate");
	var od = $V("orderSn");
	var pd = $V("productName");
	var os = $V("OrderStatus");
	var po = $V("policyNo");
	var rc = $V("remarkContent");
	var rs = $V("remarkSele");
	var ps = $V("paySn");
	var cs = $NV("contant");

	var all=sd+ed+od+pd+os+po+rc+rs+ps+cs;
	if(all == null || all == ''){
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
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","OrderStatus",$V("OrderStatus"));
	DataGrid.setParam("dg1","policyNo",$V("policyNo"));
	DataGrid.setParam("dg1","remarkContent",$V("remarkContent"));
	DataGrid.setParam("dg1","remarkSele",$V("remarkSele"));
	DataGrid.setParam("dg1","paySn",$V("paySn"));
	DataGrid.setParam("dg1","channelsn",$NV("contant"));
	DataGrid.setParam("dg1","memberid",$V("memberid"));
	DataGrid.setParam("dg1","description",$V("description"));
	DataGrid.loadData("dg1");
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

function sendRenewalEmail() {
	var dc = $DW.Form.getData("form2");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.cms.orders.QueryOrders.sendRenewalEmail", dc,
			function() {
				var response = Server.getResponse();
				Dialog.alert(response.Message);
				if (response.Status == 1) {
					$D.close();
					DataGrid.loadData('dg1');
				}
			});
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
			//alert(response.get("KID"));
			KKID = response.get("KID");
			var win = window.open(
					'../shop/order_config_new!linkOrderDetails.action?orderSn='
							+ queryID + "&KID=" + KKID, '_blank');
			win.focus();
		});

	}

	function showchannel(){
		var check_flag=jQuery('#showchannel').is(':checked');
		if(check_flag==true){
			jQuery("#channeltree").show();
		}else{
			jQuery("#channeltree").hide();
		}
	}
	

	//保全批量导入
	function batchImport(){
		var d = new Dialog("Diag9");
		d.Width = 450;
		d.Height = 150;
		d.Title = "保全批量导入";
		d.URL = "DataService/BaoquanBatchImport.jsp";
//		d.ShowMessageRow = true;
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
										<td>订单号(用半角逗号分割)：</td>
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
										<td>保单号(用半角逗号分割)：</td>
										<td><input name="policyNo" type="text" id="policyNo"
											value="" style="width: 100px" /></td>
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
										<%-- <td>渠道：</td>
							<td><z:select id="channelsn" name="channelsn" style="width:100px">${channelsn}</z:select></td> --%>
										<td><input type="checkbox" onclick="showchannel()"
											id="showchannel" />订单渠道</td>
										<td>自定义活动：</td>
										<td><input name="description" type="text"
											id="description" value="" style="width: 100px" />
									</tr>
									<tr>
										<td colspan="12" nowrap><z:tButtonPurview>${_DataService_SettleOrderInquiry_Button}</z:tButtonPurview>
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
					<z:datagrid id="dg1" method="com.wangjin.cms.orders.QueryOrders.settleOrderInquery" size="20" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="120" style="text-align:center;"><b>订单编号</b></td>
						        <td width="145" style="text-align:center;"><b>保单号</b></td>
						        <td width="55" style="text-align:center;"><b>保全记录</b></td>
						        <td width="70" style="text-align:center;"><b>被保险人数</b></td>
						        <td width="120" style="text-align:center;"><b>起保日期</b></td>
						        <td width="120" style="text-align:center;"><b>修改时间</b></td>
								<td width="150" style="text-align:center;"><b>产品名称</b></td>
								<td width="50" style="text-align:center;"><b>保费</b></td>
								<td width="50" style="text-align:center;"><b>已付费</b></td>
								<td width="55" style="text-align:center;"><b>订单状态</b></td>
								<td width="100" style="text-align:center;"><b>优惠券优惠金额</b></td>
								<td width="80" style="text-align:center;"><b>活动优惠金额</b></td>
								<td width="90" style="text-align:center;"><b>积分抵值金额</b></td>
								<td width="80" style="text-align:center;"><b>渠道</b></td>
								<td width="150" style="text-align:center;"><b>自定义活动描述</b></td>
								
								
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td style="text-align:center;">&nbsp;</td>
								<td style="text-align:center;" title="${orderSn}"><a  style="cursor: hand;" onClick="showOrderDetail('${OrderSn}')">${orderSn}</a></td>
								<td title="${policyNo}">${policyNo}</td>
								<td style="text-align:center;" title="${remark}">${remark}</td>
								<td style="text-align:center;" title="${recognizeeNu}">${recognizeeNu}</td>
								<td style="text-align:center;" title="${svalidate}">${svalidate}</td>
								<td style="text-align:center;" title="${ModifyDate}">${ModifyDate}</td>
								<td title="${ProductName}">${ProductName}</td>
								<td style="text-align:right;" title="${totalAmount}">${totalAmount}</td>
								<td style="text-align:right;" title="${PayPrice}">${PayPrice}</td>
								<td style="text-align:center;" title="${orderstatusname}">${orderstatusname}</td>
								<td style="text-align:right;" title="${parValue}">${parValue}</td>
								<td style="text-align:right;" title="${orderActivity}">${orderActivity}</td>
								<td style="text-align:right;" title="${offsetPoint}">${offsetPoint}</td>
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
