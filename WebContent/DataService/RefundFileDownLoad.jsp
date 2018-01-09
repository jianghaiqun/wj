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
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.loadData("dg1");
}
function RefundFileCreate(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 280;
	diag.Title = "批量退款文件生成";
	diag.URL = "DataService/RefundFileDialog.jsp";
	diag.onLoad = function() {
	};
	diag.OKEvent = callback;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "退款文件生成";
	diag.show();
}
function callback() {
	
	var dc = $DW.Form.getData("AddForm");
	if ($DW.Verify.hasError()) {
		return;
	}

	Dialog.wait("正在生成批量退款文件......"); 
	Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.refundFileCreate",
		dc, function() {
			Dialog.endWait();
			var response = Server.getResponse();
			Dialog.alert(response.Message, function() {
				if (response.Status == 0) {
					DataGrid.loadData("dg1");
					$D.close();
				}
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
		Server.sendRequest("com.sinosoft.cms.dataservice.InitiateRefundManage.delFileInfo",dc,function(response){
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
<body onContextMenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<fieldset>
				<legend><label>退款文件管理</label></legend> 
		<table class="blockTable">
			<tr>
				<td style="padding:4px 10px;">
				文件生成时间： 从 <input type="text" id="startDate" style="width:100px; " ztype='date'> 至 <input type="text" id="endDate" style="width:100px; " ztype='date'>
				<input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton"></td>
			</tr>
			<tr style="padding-top: 10px;"></tr>
			<tr>
				<td style="padding:4px 10px;">
				 <!--<z:tbutton onClick="RefundFileCreate()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>批量退款文件生成</z:tbutton>
	    		 <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>-->
	    			<z:tButtonPurview>${_DataService_RefundFileDownLoad_Button}</z:tButtonPurview>
	    		</td>
			</tr>
		</table>
		</fieldset>
	<br />
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			
			<tr>
				<td style="padding:0px 5px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.RefundFileManage.dg1DataBind" size="15" lazy="true">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterDrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<!-- <td width="3%" ztype="selector" field="id">&nbsp;</td> -->
							<td width="18%"><b>文件名称</b></td>
							<td width="10%"><b>操作人</b></td>
							<td width="10%"><b>创建时间</b></td>
							<td width="10%"><b>起始时间</b></td>
							<td width="10%"><b>截止时间</b></td>
							<td width="5%"><b>下载</b></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<!-- <td>&nbsp;</td> -->
							<td>${FileName}</td>
							<td>${CreateUser}</td>
							<td>${CreateDate}</td>
							<td>${Prop1}</td>
							<td>${Prop2}</td>
							<td><a href="<%=Config.getValue("ContextPath") %>/EFile/${FilePath}" target="_blank"><img src="../Framework/Images/icon_down.gif" width="15" height="15" style="cursor:pointer;"/></a></td>
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
