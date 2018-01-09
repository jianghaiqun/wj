<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function upload(){
	var diag = new Dialog("Diag1");
	diag.Width = 330;
	diag.Height = 200;
	diag.Title = "导入退款结果数据";
	diag.URL = "DataService/RefundResultImport.jsp";
    diag.OKEvent=OK;
	diag.show();
}

function OK(){
	$DW.upload();
}

function onFileUploadCompleted(){
	DataGrid.setParam("dg3",Constant.PageIndex,0);
	DataGrid.loadData("dg3");
	if($D){
		setTimeout(function(){$D.close()}, 100);
	}
}

function doSearch(){
	DataGrid.setParam("dg3",Constant.PageIndex,0);
	DataList.setParam("dg3","StartDate",$V("StartDate"));
	DataList.setParam("dg3","EndDate",$V("EndDate"));
	DataList.setParam("dg3","OrderSn",$V("OrderSn"));
	DataList.setParam("dg3","PaySn",$V("PaySn"));
	DataList.setParam("dg3","PayType",$V("PayType"));
	DataGrid.loadData("dg3");
}

function edit(){
	var dt = DataGrid.getSelectedData("dg3");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要编辑的退款信息！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 300;
	diag.Title = "修改退款信息";
	diag.URL = "DataService/RefundResultDialog.jsp?Id="+dr.get("Id");
	diag.onLoad = function(){
		//$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑退款信息";
	diag.Message = "设置退款信息";
	diag.show();
}

function editSave() {
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg3');
			}
		});
	});
}
//删除
function del(){
	var arr = DataGrid.getSelectedValue("dg3");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.delRefundResultInfo",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg3');
				}
			});
		});
	});
}
</script>
</head>
<body onContextMenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<fieldset>
		<legend><label>退款结果管理</label></legend> 
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:4px 10px;">回盘时间： 从 <input type="text" id="StartDate" style="width:100px; " ztype='date'> 至 <input type="text" id="EndDate" style="width:100px; " ztype='date'>
				&nbsp;订单号: <input name="OrderSn" type="text" id="OrderSn" style="width:150px"> 
				商户订单号： <input type="text" id="PaySn" name="PaySn" style="width:150px; ">
				支付方式： <input type="text" id="PayType" name="PayType" style="width:150px; ">
				<input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton"></td>
			</tr>
			<tr>
				<td style="padding:8px 10px;" class="blockTd">
				<div style="float: left">
					<!--<z:tbutton id="uploadButton" priv="attach_modify" onClick="upload()">
						<img src="../Icons/icon003a8.gif" />退款结果导入</z:tbutton>
					<z:tbutton id="delButton" priv="attach_modify" onClick="edit()">
						<img src="../Icons/icon003a4.gif" />编辑</z:tbutton>
					<z:tbutton id="delButton" priv="attach_modify" onClick="del()">
						<img src="../Icons/icon003a3.gif" />删除</z:tbutton>-->
						<z:tButtonPurview>${_DataService_RefundResultInfo_Button}</z:tButtonPurview>
				</div>
				</td>
			</tr>
		</table>
		</fieldset>
		<br />
		<table>
			<tr>
				<td style="padding:0px 5px;">
				<z:datagrid id="dg3" method="com.sinosoft.cms.dataservice.InitiateRefundManage.dg3DataBind" size="15" lazy="true">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterDrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" ztype="selector" field="id">&nbsp;</td>
							<td width="12%"><b>订单号</b></td>
							<td width="12%"><b>商户订单号</b></td>
							<td width="8%"><b>退款金额</b></td>
							<td width="8%"><b>退款状态</b></td>
							<td width="10%"><b>退款日期</b></td>
							<td width="10%"><b>变更日期</b></td>
							<td width="10%"><b>回盘日期</b></td>
							<td width="7%"><b>退款平台</b></td>
							<td width="10%"><b>备注</b></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${osn}</td>
							<td>${PaySn}</td>
							<td>${RefundAmount}</td>
							<td title="${status}">${status}</td>
							<td>${RefundDate}</td>
							<td>${ModifyTime}</td>
							<td>${AddTime}</td>
							<td>${description}</td>
							<td title="${Remark}">${Remark}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="10" align="center">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
