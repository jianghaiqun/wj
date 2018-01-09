<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg2").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 		return;
    	}
		dr.set("OrderFlag",$V("OrderFlag"));
		return true;
	}
});
/**
 * 添加模块
 */
function add() {
	$S("ModelStatusFlag","add");
	var diag = new Dialog("Diag3");
	diag.Width =700;
	diag.Height = 500;
	diag.Title = "新增模块";
	diag.URL = "Promotion/Normal/PromotionMainDialog.jsp?ModelDialogStatusFlag=add&articleid="+$V("articleid");
	diag.onLoad = function() {
	};
	diag.OKEvent = saveModel;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新增模块";
	diag.show();
}
/**
 * 修改模块
 */
function modify(){
	$S("ModelStatusFlag","edit");
	var dt = DataGrid.getSelectedData("dg2");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的模块！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能修改一个模块！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height =500;
	diag.Title = "编辑模块";
	diag.URL = "Promotion/Normal/PromotionMainDialog.jsp?ID=" + dr.get("ID")+"&ModelDialogStatusFlag=edit&articleid="+$V("articleid");
	diag.onLoad = function() {
	};
	diag.OKEvent = saveModel;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑模块";
	diag.show();
}
/**
 * 保存模块信息
 */
function saveModel(){
	if($DW.Verify.hasError()){
		return;
	}
	var Info = $DW.Tab.getChildTab("BaseInfo").contentWindow;
	var dc_info = Info.Form.getData("form2");
	dc_info.add("ModelStatusFlag", $V("ModelStatusFlag"));
	dc_info.add("articleid", $V("articleid"));
	var ModuleType=Info.$V("ModuleType");
	if(ModuleType==null||ModuleType==''){
		Dialog.alert("还没有选择模块类型！");
		return;
	}
	var ModuleTheme=Info.$V("ModuleTheme");
	if(ModuleTheme==null||ModuleTheme==''){
		Dialog.alert("还没有选择模块主题！");
		return;
	}
	var MoreUrl=Info.$V("MoreUrl");
	if(MoreUrl!=null&&MoreUrl!=''){
		var MoreColor=Info.$V("MoreColor");
		if(MoreColor==null||MoreColor==''){
			Dialog.alert("'更多链接地址'不为空的情况下,字体颜色为必填项！");
			return;
		}
	}
	var ProductInfo = $DW.Tab.getChildTab("ProductInfo").contentWindow;
	var productnum=ProductInfo.$V("productnum");
	if(ModuleType=='02'||ModuleType=='04'){
		if(parseInt(productnum)>1){
			Dialog.alert("该类型的模块只能添加一个产品,请删除多余的产品或更改模块类型！");
			return;
		}
	}
	Server.sendRequest("com.sinosoft.cms.document.PromotionManage.saveModel",dc_info,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg2");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}
/**
 * 删除模块
 */
function dell(){
	var arr = DataGrid.getSelectedValue("dg2");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的模块！");
		return;
	}
	Dialog.confirm("删除后不可恢复，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.sinosoft.cms.document.PromotionManage.deleteModel", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg2");
						}
					});
				});
	});
}
/**
 * 保存顺序
 */
function saveIndex(){
	DataGrid.save("dg2","com.sinosoft.cms.document.PromotionManage.saveModelIndex",function(){DataGrid.loadData('dg2');});
}
/**
 * 查询
 */
function search(){
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","ModuleName",$V("ModuleName"));
	DataGrid.loadData("dg2");
}
</script>
</head>
<body>
<input type="hidden" id="ModelStatusFlag" value="">
<input type="hidden" id="articleid" value="<%=request.getParameter("articleid")%>">
<table width="230%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left">
					<z:tbutton id="BtnFAQId" priv="article_browse" onClick="add()"><img src="../../Icons/icon003a2.gif" width="20" height="20" />添加模块</z:tbutton></div>
					<z:tbutton id="BtnFAQId" priv="article_browse" onClick="modify()"><img src="../../Icons/icon003a2.gif" width="20" height="20" />修改模块</z:tbutton></div>
					<z:tbutton id="BtnFAQId" priv="article_browse" onClick="dell()"><img src="../../Icons/icon003a2.gif" width="20" height="20" />删除模块</z:tbutton></div>
					<z:tbutton id="BtnFAQId" priv="article_browse" onClick="saveIndex()"><img src="../../Icons/icon003a2.gif" width="20" height="20" />保存顺序</z:tbutton>
				</div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">
					模块名称&nbsp;: <input name="ModuleName" type="text" id="ModuleName"> 
					<input type="button"  value="查询" onClick="search()">
				</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<z:datagrid id="dg2" method="com.sinosoft.cms.document.PromotionManage.dg1ModelDataBind" size="8">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="10" height="30" ztype="RowNo" drag="true">
							<img src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="15" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="150"><b>模块名称</b></td>
							<td width="100" sortfield="OrderFlag" direction=""><b>排序</b></td>
							<td width="150"><b>模块类型</b></td>
							<td width="150"><b>模块背景色</b></td>
							<td width="150"><b>模块主题</b></td>
							<td width="150"><b>模块名称颜色</b></td>
							<td width="200"><b>更多URL地址</b></td>
							<td width="150"><b>更多字体颜色</b></td>
							<td width="150"><b>创建人</b></td>
							<td width="150"><b>创建日期</b></td>
							<td width="150"><b>修改人</b></td>
							<td width="150"><b>修改日期</b></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td title="${ModuleName}">${ModuleName}</td>
							<td title="${OrderFlag}">${OrderFlag}</td>
							<td title="${ModuleTypeName}">${ModuleTypeName}</td>
							<td title="${ModuleColor}">${ModuleColor}</td>
							<td title="${ModuleThemeName}">${ModuleThemeName}</td>
							<td title="${ModuleNameColor}">${ModuleNameColor}</td>
							<td title="${MoreUrl}">${MoreUrl}</td>
							<td title="${MoreColor}">${MoreColor}</td>
							<td title="${CreateUser}">${CreateUser}</td>
							<td title="${CreateDate}">${CreateDate}</td>
							<td title="${ModifyUser}">${ModifyUser}</td>
							<td title="${ModifyDate}">${ModifyDate}</td>
						</tr>
						<tr ztype="edit" bgcolor="#E1F3FF">
							<td bgcolor="#E1F3FF">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${ModuleName}</td>
							<td><input name="OrderFlag" type="text" id="OrderFlag" value="${OrderFlag}" verify="PositiveNumber" size="15"></td>
							<td>${ModuleTypeName}</td>
							<td>${ModuleColor}</td>
							<td>${ModuleThemeName}</td>
							<td>${ModuleNameColor}</td>
							<td>${MoreUrl}</td>
							<td>${MoreColor}</td>
							<td>${CreateUser}</td>
							<td>${CreateDate}</td>
							<td>${ModifyUser}</td>
							<td>${ModifyDate}</td>
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
