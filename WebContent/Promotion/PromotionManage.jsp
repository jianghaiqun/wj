<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
/**
 * 编辑模块信息
 */
function editModel() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要编辑的促销页！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择一个促销页进行编辑！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 850;
	diag.Height = 350;
	diag.Title = "促销页模块信息";
	diag.URL = "Promotion/Normal/PromotionModelList.jsp?articleid="+drs[0].get("ID");
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "促销页模块信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "确定";
}
/*查询*/
function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","title",$V("title"));
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}
document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'title'||ele.id == 'Submitbutton'){
			search();
		}
	}
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left">
					<z:tbutton  onClick="editModel()"><img src="../Icons/icon003a2.gif" width="20" height="20" />编辑模块信息</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">
					开始时间：<input type="text" id="StartDate" style="width:90px; " ztype='date'> 
					结束时间 ：<input type="text" id="EndDate" style="width:90px; " ztype='date'> &nbsp;
					标题: <input name="title" type="text" id="title"> 
					<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.document.PromotionManage.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="20" height="30" ztype="RowNo" drag="true">
							<img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="15" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="200" ><b>标题</b></td>
							<td width="100">预览</td>
							<td width="100"><strong>创建者</strong></td>
							<td width="100"><strong>状态</strong></td>
							<td width="150" sortfield="publishdate" direction=""><strong>发布时间</strong></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td ><span title="${Title}">${Title}</span></td>
							<td><a href="${HeadURL}${URL}" target="_blank" title="${HeadURL}${URL}"><img src="../Icons/icon403a3.gif" width="20" height="20" /></a></td>
							<td>${Author}</td>
							<td>${StatusName}</td>
							<td title="${PublishDate}">${PublishDate}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="9" align="left">${PageBar}</td>
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
