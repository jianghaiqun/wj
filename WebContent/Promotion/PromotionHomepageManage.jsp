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
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 		return;
    	}
		dr.set("OrderFlag",$V("OrderFlag"));
		return true;
	}
});
/*查询*/
function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","title",$V("title"));
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}

/**
 * 添加模块
 */
function addModel() {
	$S("ModelStatusFlag","add");
	var diag = new Dialog("Diag2");
	diag.Width =600;
	diag.Height = 150;
	diag.Title = "新增模块";
	diag.URL = "Promotion/Homepage/PromotionHomepageModelDialog.jsp?ModelStatusFlag=add";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveHomepageModel;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新增模块";
	diag.show();
}
/**
 * 修改模块
 */
function editModel(){
	$S("ModelStatusFlag","edit");
	var dt = DataGrid.getSelectedData("dg1");
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
	var diag = new Dialog("Diag2");
	diag.Width = 600;
	diag.Height =150;
	diag.Title = "编辑模块";
	diag.URL = "Promotion/Homepage/PromotionHomepageModelDialog.jsp?ID=" + dr.get("ID")+"&ModelStatusFlag=edit";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveHomepageModel;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑模块";
	diag.show();
}
/**
 * 保存模块信息
 */
function saveHomepageModel(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	dc.add("ModelStatusFlag",$V("ModelStatusFlag"));
	Server.sendRequest("com.sinosoft.cms.document.PromotionManage.saveHomepageModel",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg1");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}
/**
 * 保存顺序
 */
function saveIndex(){
	DataGrid.save("dg1","com.sinosoft.cms.document.PromotionManage.saveHomepageModelIndex",function(){DataGrid.loadData('dg1');});
}
/**
 * 删除模块
 */
function dellModel(){
	var arr = DataGrid.getSelectedValue("dg1");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的模块！");
		return;
	}
	Dialog.confirm("删除后不可恢复，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.sinosoft.cms.document.PromotionManage.deleteHomepageModel", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
						}
					});
				});
	});
}
/**
 * 查询活动信息
 */
function showActivityInfo(ModuleID){
	var diag = new Dialog("Diag2");
	diag.Width =1000;
	diag.Height = 400;
	diag.Title = "活动信息";
	diag.URL = "Promotion/Homepage/PromotionHomepageActivity.jsp?ModuleID="+ModuleID;
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "活动信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
/**
 * 设置热卖品牌
 */
function  setSellingBrand(){
	var diag = new Dialog("Diag2");
	diag.Width =800;
	diag.Height = 400;
	diag.Title = "热点品牌";
	diag.URL = "Promotion/Homepage/PromotionHomepageSellingBrandList.jsp";
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "热点品牌";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关闭";
}
/**
 * 设置倒计时产品
 */
function setCountdownProduct(){
	var diag = new Dialog("Diag2");
	diag.Width =700;
	diag.Height = 400;
	diag.Title = "倒计时产品";
	diag.URL = "Promotion/Homepage/PromotionHomepageSpecialProductDialog.jsp";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveSpecialProduct;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "倒计时产品";
	diag.show();
}
/**
 * 保存特殊促销产品
 */
function saveSpecialProduct(){
	if($DW.Verify.hasError()){
		return;
	}
	if(!$DW.checkImgPath()){
		Dialog.alert("请为倒计时产品选择展示图片");
		return;
	}
	var productid = $DW.$V("productid");
	if(productid==null||productid==''){
		Dialog.alert("请选择倒计时产品！");
		return;
	}
	var dc = $DW.Form.getData("form2");
	Server.sendRequest("com.sinosoft.cms.document.PromotionManage.saveSpecialProduct",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg1");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}
</script>
</head>
<body>
<input type="hidden" value="" id="ModelStatusFlag" name="ModelStatusFlag">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left">
					<z:tbutton  onClick="addModel()"><img src="../Icons/icon003a2.gif" width="20" height="20" />添加模块信息</z:tbutton></div>
					<z:tbutton  onClick="editModel()"><img src="../Icons/icon003a2.gif" width="20" height="20" />编辑模块信息</z:tbutton></div>
					<z:tbutton  onClick="dellModel()"><img src="../Icons/icon003a2.gif" width="20" height="20" />删除模块信息</z:tbutton></div>
					<z:tbutton  onClick="saveIndex()"><img src="../Icons/icon003a2.gif" width="20" height="20" />保存模块顺序</z:tbutton></div>
					<z:tbutton  onClick="setSellingBrand()"><img src="../Icons/icon003a2.gif" width="20" height="20" />设置热卖品牌</z:tbutton></div>
					<z:tbutton  onClick="setCountdownProduct()"><img src="../Icons/icon003a2.gif" width="20" height="20" />设置倒计时产品</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">
					开始时间：<input type="text" id="StartDate" style="width:90px; " ztype='date'> 
					结束时间 ：<input type="text" id="EndDate" style="width:90px; " ztype='date'> &nbsp;
					关键词: <input name="title" type="text" id="title"> 
					<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.document.PromotionManage.dg1DataBindHomepage" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="20" height="30" ztype="RowNo" drag="true">
							<img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="15" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="150" ><b>模块标题</b></td>
							<td width="100"><strong>显示状态</strong></td>
							<td width="100" sortfield="OrderFlag" direction="" ><strong>顺序</strong></td>
							<td width="100"><strong>创建人</strong></td>
							<td width="150"><strong>创建时间</strong></td>
							<td width="100"><strong>修改人</strong></td>
							<td width="150"><strong>修改时间</strong></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td ><a href="###" onclick="showActivityInfo('${ID}')" title="${ModuleName}">${ModuleName}</a></td>
							<td title="${isShowName}">${isShowName}</td>
							<td title="${OrderFlag}">${OrderFlag}</td>
							<td title="${CreateUser}">${CreateUser}</td>
							<td title="${CreateDate}">${CreateDate}</td>
							<td title="${ModifyUser}">${ModifyUser}</td>
							<td title="${ModifyDate}">${ModifyDate}</td>
						</tr>
						<tr ztype="edit" bgcolor="#E1F3FF">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td ><span title="${ModuleName}">${ModuleName}</span></td>
							<td>${isShowName}</td>
							<td><input name="OrderFlag" type="text" id="OrderFlag" value="${OrderFlag}" verify="PositiveNumber" size="15"></td>
							<td title="${CreateUser}">${CreateUser}</td>
							<td title="${CreateDate}">${CreateDate}</td>
							<td title="${ModifyUser}">${ModifyUser}</td>
							<td title="${ModifyDate}">${ModifyDate}</td>
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
