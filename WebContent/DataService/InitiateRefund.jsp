<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>发起退款管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
	// 查询
	function doSearch() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1","createStartDate",$V("createStartDate"));
		DataGrid.setParam("dg1","createEndDate",$V("createEndDate"));
		DataGrid.setParam("dg1","approvalStartDate",$V("approvalStartDate"));
		DataGrid.setParam("dg1","approvalEndDate",$V("approvalEndDate"));
		DataGrid.setParam("dg1","orderSn",$V("orderSn"));
		DataGrid.setParam("dg1","policyNo",$V("policyNo"));
		DataGrid.setParam("dg1","refundType",$V("refundType"));
		DataGrid.setParam("dg1","approval",$V("approval"));
		DataGrid.setParam("dg1","remark",$V("remark"));
		DataGrid.loadData("dg1");
	}

	// 新建
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 500;
		diag.Height = 280;
		diag.Title = "发起退款-新增";
		diag.URL = "DataService/InitiateRefundAddDialog.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "发起退款";
		diag.show();
	}

	// 新建保存
	function addSave() {
		var dc = $DW.Form.getData("AddForm");
		if ($DW.Verify.hasError()) {
			return;
		}
		// 退款类型
		var RefundType = $DW.$V("RefundType");
		if ("2" == RefundType) {
			// 支付宝名称
			var Prop1 = $DW.$V("Prop1");
			if (Prop1 == null || Prop1 == '') {
				Dialog.alert("支付宝名称不能为空");
				return;
			}
			// 支付宝账号
			var Prop2 = $DW.$V("Prop2");
			if (Prop2 == null || Prop2 == '') {
				Dialog.alert("支付宝账号不能为空");
				return;
			}
		}
		
		Dialog.confirm("确认要发起本次退款？",function() { 
			Dialog.wait("正在生成退款信息......"); 
			Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.add",
				dc, function() {
					Dialog.closeEx();
					var response = Server.getResponse();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
		});
	}

	// 删除
	function del(){
		var arr = DataGrid.getSelectedValue("dg1");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		Dialog.confirm("确定要删除吗？",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.del",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataGrid.loadData('dg1');
					}
				});
			});
		});
	}
	function upload(){
		var diag = new Dialog("Diag1");
		diag.Width = 330;
		diag.Height = 100;
		diag.Title = "导入返现数据";
		diag.URL = "DataService/TBCouponImport.jsp";
	    diag.OKEvent=OK;
		diag.show();
	}

	function OK(){
		$DW.upload();
	}

	function onFileUploadCompleted(){
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
		if($D){
			setTimeout(function(){$D.close()}, 100);
		}
	}

	// 审批通过
	function approvalYes(){
		var arr = DataGrid.getSelectedValue("dg1");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择要审批的行！");
			return;
		}
		Dialog.confirm("确定要审批通过吗？",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.approvalYes",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataGrid.loadData('dg1');
					}
				});
			});
		});
	}

	// 审批不通过
	function approvalNo(){
		var arr = DataGrid.getSelectedValue("dg1");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择要审批的行！");
			return;
		}
		Dialog.confirm("确定要审批不通过吗？",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.approvalNo",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataGrid.loadData('dg1');
					}
				});
			});
		});
	}
</script>
</head>
<body>

			<fieldset>
				<legend><label>手动发起退款管理</label></legend> 
	<table class="blockTable">
		<tr>
			<td style="padding:4px 10px;">
				创建时间： 从 <input type="text" id="createStartDate" style="width:100px; " ztype='date'> 至 <input type="text" id="createEndDate" style="width:100px; " ztype='date'>
				&nbsp;&nbsp;审批时间： 从 <input type="text" id="approvalStartDate" style="width:100px; " ztype='date'> 至 <input type="text" id="approvalEndDate" style="width:100px; " ztype='date'>
				<br/><br/>
				订单号： <input type="text" id="orderSn" style="width:150px; ">
				保单号： <input type="text" id="policyNo" style="width:150px; ">
				返现类型：<z:select style="width:100px;">
	               		<select name="refundType" id="refundType"  > 
		                	<option value="" ></option>
		                	<option value="0">优惠返现</option>
		                	<option value="1">代客支付</option>
		                	<option value="2">淘宝测试</option>
		                	<option value="3">生效后退保</option>
	               		</select>
	               	</z:select>
               	审批状态：<z:select style="width:80px;">
	               		<select name="approval" id="approval"  > 
		                	<option value="" ></option>
		                	<option value="0">未审批</option>
		                	<option value="1">已通过</option>
		                	<option value="2">不通过</option>
	               		</select>
               		</z:select>
				备注： <input type="text" id="remark" style="width:200px; ">
				<input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton">
			</td>
		</tr>
		<tr style="padding-top: 10px;"></tr>
		<tr>
			<td style="padding:4px 10px;">
			 <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建</z:tbutton>
			 <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
   			 <!--<z:tbutton id="uploadButton" priv="attach_modify" onClick="upload()"><img src="../Icons/icon003a8.gif" />淘宝返现导入</z:tbutton>
    		 <z:tbutton onClick="approvalYes()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>审批通过</z:tbutton>
    		 <z:tbutton onClick="approvalNo()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>审批不通过</z:tbutton>-->
    		 
    		 <z:tButtonPurview>${_DataService_InitiateRefund_Button}</z:tButtonPurview>
    		</td>
		</tr>
	</table>
			</fieldset>
	<br />
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<div style="width:1980px;  overflow: auto;">
				<z:datagrid id="dg1"
					method="com.sinosoft.cms.dataservice.InitiateRefundManage.dg1DataBind" size="50"
					scroll="true" lazy="true">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="20" ztype="RowNo"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16">
							</td>
							<td width="15" ztype="selector" field="id">&nbsp;</td>
							<td width="80"><strong>订单号</strong>
							</td>
							<td width="90"><strong>保单号</strong>
							</td>
							<td width="60"><strong>返现类型</strong>
							</td>
							<td width="60"><strong>保险公司</strong></td>
							<td width="60"><strong>保费</strong></td>
							<td width="60"><strong>返现金额</strong>
							</td>
							<td width="50"><strong>审批状态</strong>
							</td>
							<td width="80"><strong>审批时间</strong>
							</td>
							<td width="80"><strong>创建日期</strong>
							</td>
							<td width="80"><strong>承保日期</strong></td>
							<td width="80"><strong>生效日期</strong></td>
							<td width="80"><strong>支付宝名称</strong></td>
							<td width="80"><strong>支付宝账号</strong></td>
							<td width="120"><strong>备注</strong>
							</td>
						</tr>
						<tr style="background-color: #F9FBFC">
							<td width="3%">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${OrderSn}</td>
							<td>${PolicyNo}</td>
							<td>${RefundTypeName}</td>
							<td title="${InsureName}">${InsureName}</td>
							<td>${productprice}</td>
							<td>${RefundAmount}</td>
							<td>${ApprovalName}</td>
							<td>${ApprovalTime}</td>
							<td>${AddTime}</td>
							<td>${insureDate}</td>
							<td>${svaliDate}</td>
							<td>${Prop1}</td>
							<td>${Prop2}</td>
							<td title="${Remark}">${Remark}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="10" align="left">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>