<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单数据daoru</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="${Site.URL}/js/premcalculate.js"></script>
<script>
function doSearch(){
	DataGrid.loadData("dg2");
}
function add(){
	var diag = new Dialog("Diag");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "设置公告";
	diag.URL = "DataService/MemberPubInfoDialog.jsp";
	diag.OKEvent=save;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "设置公告页面 ";
	diag.CancelEvent=function(){$D.close();doSearch();};
	diag.show();
}
function edit(id){
	var arr = DataGrid.getSelectedValue("dg2");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	
	if(!arr||arr.length==0){
		Dialog.alert("请先选择需要编辑的公告！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "设置公告";
	diag.URL = "DataService/MemberPubInfoDialog.jsp?id="+arr[0];
	diag.OKEvent=save;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "设置公告页面 ";
	diag.CancelEvent=function(){$D.close();doSearch();};
	diag.show();
}
function del(){
	var dc = new DataCollection();	 
	var arr = DataGrid.getSelectedValue("dg2");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择需要删除的公告！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	dc.add("id",arr[0]);
	Dialog.confirm("确认删除？", function() {
		Server.sendRequest("com.sinosoft.cms.dataservice.PublicInform.memberCenterInfoDel",
				dc, function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg2");
						}
					});
				});
	});
}

function save(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		  return;
	     }
	Server.sendRequest("com.sinosoft.cms.dataservice.PublicInform.memberCenterInfoSave",
			dc, function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData("dg2");
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
		            <z:tbutton onClick="add();">
				<img src="../Icons/icon005a2.gif" width="20" height="20" />添加</z:tbutton>
				<z:tbutton onClick="edit();">
				<img src="../Icons/icon005a2.gif" width="20" height="20" />修改</z:tbutton>
				<z:tbutton onClick="del();">
				<img src="../Icons/icon005a2.gif" width="20" height="20" />删除</z:tbutton>
		        </tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.PublicInform.memberCenterInfodg1DataBind" size="20" scroll="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
								<td width="5%" style="text-align:center;"><b>公告ID</b></td>
								<td width="10%" style="text-align:center;"><b>开始时间</b></td>
								<td width="25%" style="text-align:center;"><b>结束时间</b></td>
								<td width="10%" style="text-align:center;"><b>是否启用</b></td>
								<td width="10%" style="text-align:center;"><b>字体颜色</b></td>
								<td width="25%" style="text-align:center;"><b>公告内容</b></td>
							</tr>
							<tr onDblClick="edit('${id}');" style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td >${id}</td>
								<td >${StartDate}</td>
								<td >${EndDate}</td>
								<td >${ViewFlag}</td>
								<td >${Color}</td>
								<td >${Info}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="6">${PageBar}</td>
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