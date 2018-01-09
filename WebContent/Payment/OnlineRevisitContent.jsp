<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>在线回访内容配置列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
/*
* 添加
*/
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 270;
	diag.Title = "添加在线回访内容配置";
	diag.URL = "onlinerevisit/OnlineRevisitContentAdd.jsp";
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加在线回访内容配置";
	diag.show();
}
/**
* 保存添加数据
*/
function addSave(){
	if($DW.$V("productId") == ''){
		Dialog.alert("请填写产品id");
		return;
	}
	var dc = $DW.Form.getData("form1");
	if ($DW.Verify.hasError()) {
		return;
	}
	Dialog.wait("正在保存，请稍候......");
	Server.sendRequest("com.wangjin.payment.OnlineRevisitContent.add", dc, function(response) {
		Dialog.endWait();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});	
}

// 搜索
function searchdata(){
	var ProductName = $V("ProductName");
	var productId = $V("productId");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","ProductName",ProductName);
	DataGrid.setParam("dg1","productId",productId);
	DataGrid.loadData("dg1");
}

// 编辑在线回访内容
function doEdit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要编辑的信息！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能编辑一条信息！");
		return;
	}
	var dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 400;
	diag.Title = "编辑在线回访内容";
	diag.URL = "Payment/OnlineRevisitContentAdd.jsp?productId="+dr.get("ProductID");
	diag.OKEvent = doEditCallBack;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑在线回访内容";
	diag.show();
}

function doEditCallBack(){
	var dc = $DW.Form.getData("form1");

	var content = $DW.getContent();
	dc.add("content",content);

	Server.sendRequest("com.wangjin.payment.OnlineRevisitContent.saveContent",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
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
						<table  cellspacing="0" cellpadding="3">
							<tr>
								<td align="right" height="35">产品编码：</td>
								<td><input name="productId" type="text" id="productId"></td>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<td align="right" height="35">产品名称：</td>
								<td><input name="ProductName" type="text" id="ProductName"></td>
								<td colspan="8" nowrap><z:tButtonPurview>${_Payment_OnlineRevisitContent_Button}</z:tButtonPurview></td>
							</tr>
						</table>
					</td>
				</tr>
			  <tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.payment.OnlineRevisitContent.dg1DataBind" size="15" scroll="true" lazy="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="5" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
                                <td width="80" style="text-align:center;"><b>产品名称</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="5">&nbsp;</td>
								 <td style="text-align:left;" title="${ProductName}">${ProductName}</td>
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