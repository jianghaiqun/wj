<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>优惠券审批信息表列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
/**
* 查询
*/
function searchdata(){
	DataGrid.setParam("dg1", Constant.PageIndex, 0); 
	DataGrid.setParam("dg1","couponBatch",$V("couponBatch"));
	DataGrid.setParam("dg1","approveStatus",$V("approveStatus"));
	DataGrid.loadData("dg1");
}

/**
* 审核
*/
function approve(){
	var arr = DataGrid.getSelectedValue("dg1");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要审核的数据！");
		return;
	}
	var selDatas = DataGrid.getSelectedData("dg1");
	var rows = selDatas.Rows;
	for(var i=0;i<rows.length;i++){
		if(rows[i].get("approveStatus") != '0'){
			Dialog.alert("所选数据有已审核数据，请重新选择！");
			return;
		}
	}
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 200;
	diag.Title = "审核";
	diag.URL = "Couponapprove/CouponApprove.jsp?ids="+arr.join();
	diag.OKEvent = approveSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "审核";
	diag.show();
}

function approveSave(){
	var dc = $DW.Form.getData("form1");
	if ($DW.Verify.hasError()) {
		return;
	}
	Dialog.wait("正在保存，请稍候......");
	Server.sendRequest("com.wangjin.couponapprove.CouponApproveInfo.approve", dc, function(response) {
		Dialog.endWait();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});	
}

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
					<td>
						<z:init method="com.wangjin.couponapprove.CouponApproveInfo.init">
							<table  cellspacing="0" cellpadding="3">
								<tr>
									<td>优惠券批次号：</td>
									<td>
										<input name="couponBatch" type="text" id="couponBatch" value="" style="width:200px" />
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td>审核状态：</td>
									<td>
										<z:select id="approveStatus" name="approveStatus">${approveStatusOpts}</z:select>
									</td>
								</tr>
								<tr>
								   <td colspan="8" nowrap><z:tButtonPurview>${_Couponapprove_CouponApproveInfo_Button}</z:tButtonPurview></td>
				            	</tr>
				            </table>
				        </z:init>
					</td>
				</tr>
			  <tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.couponapprove.CouponApproveInfo.dg1DataBind" size="15" scroll="true" lazy="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="4" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="4" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
                                 <td width="30" style="text-align:center;"><b>优惠券批次</b></td>
                                 <td width="20" style="text-align:center;"><b>优惠券申请人</b></td>
                                 <td width="20" style="text-align:center;"><b>审批状态</b></td>
                                 <td width="80" style="text-align:center;"><b>审批时间</b></td>
                                 <td width="80" style="text-align:center;"><b>审批人</b></td>
                                 <td width="80" style="text-align:center;"><b>审批意见</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td width="15">&nbsp;<input name="id" type="hidden" id="id" value="${id}" /></td>
								 <td style="text-align:left;" title="${couponBatch}">${couponBatch}</td>
								 <td style="text-align:left;" title="${couponApplicant}">${couponApplicant}</td>
								 <td style="text-align:left;" title="${approveStatusName}">${approveStatusName}</td>
								 <td style="text-align:left;" title="${createDate}">${createDate}</td>
								 <td style="text-align:left;" title="${createUser}">${createUser}</td>
								 <td style="text-align:left;" title="${createUser}">${remark1}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
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