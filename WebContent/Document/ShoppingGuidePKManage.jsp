<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
/**
 * 编辑PK产品
 */
function editPKProduct() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要编辑的PK文章！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择一个PK文章进行编辑！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 350;
	diag.Title = "PK产品";
	diag.URL = "Document/PKProductDialog.jsp?articleid="+drs[0].get("ID") ;
	diag.onLoad = function() {
	};
	diag.OKEvent = savePKProduct;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "PK产品";
	diag.show();
}

function savePKProduct() {
	var productId1 = $DW.$V("ProductId1");
	var productId2 = $DW.$V("ProductId2");
	if (productId1 == null || productId1 == '' || productId2 == null || productId2 == '') {
		Dialog.alert("请选择PK产品！");
		return;
	}
	var dc = new DataCollection();
	dc.add("articleId",$DW.$V("ArticleId"));
	dc.add("productId1",productId1);
	dc.add("productId2",productId2);
	Dialog.wait("正在保存，请稍后......");
	Server.sendRequest("com.sinosoft.cms.document.ShoppingGuidePKManage.savePKProduct",dc,function(response){
		Dialog.closeEx();
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg1");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function editPKDuty() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要编辑的PK文章！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择一个PK文章进行编辑！");
		return;
	}
	var diag = new Dialog("Diag2");
	diag.Width = 750;
	diag.Height = 450;
	diag.Title = "编辑PK保障信息";
	diag.URL = "Document/PKDutyDialog.jsp?articleid="+drs[0].get("ID")+"&&textvalue="+drs[0].get("textvalue");
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑PK保障信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "取消";
}

function editPKAdvantage() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要编辑的PK文章！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择一个PK文章进行编辑！");
		return;
	}
	var diag = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 300;
	diag.Title = "优势PK信息";
	diag.URL = "Document/PKAdvantageDialog.jsp?articleid="+drs[0].get("ID");
	diag.onLoad = function() {
	};
	diag.OKEvent = savePKAdvantage;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "优势PK信息";
	diag.show();
}

function savePKAdvantage() {
	var index = $DW.$V("index");
	var index_array=index.split(",");
	var DetailNum = $DW.$V("DetailNum");
	for(var i=0;i<parseInt(DetailNum);i++){
		var Info1 = $DW.$V("Info1_"+index_array[i]);
		var Info2 = $DW.$V("Info2_"+index_array[i]);
		if((Info1==null||Info1=='') && (Info2==null||Info2=='')){
			Dialog.alert("第"+(i+1)+"行的优势信息必须至少填写一个！");
			return;
		}

	}
	var dc = $DW.Form.getData("form4");
	Server.sendRequest("com.sinosoft.cms.document.ShoppingGuidePKManage.savePKAdvantage",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg1");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
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
					<z:tbutton  onClick="editPKProduct()"><img src="../Icons/icon003a2.gif" width="20" height="20" />编辑PK产品</z:tbutton>
					<z:tbutton  onClick="editPKDuty()"><img src="../Icons/icon003a2.gif" width="20" height="20" />编辑PK保障信息</z:tbutton>
					<z:tbutton  onClick="editPKAdvantage()"><img src="../Icons/icon003a2.gif" width="20" height="20" />编辑PK优势信息</z:tbutton></div>
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
				<z:datagrid id="dg1" method="com.sinosoft.cms.document.ShoppingGuidePKManage.dg1DataBind" size="15">
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
						   <td style="display:none" >${textvalue}</td>
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