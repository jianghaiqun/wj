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
/*查询*/
function search(){
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","title",$V("title"));
	DataGrid.loadData("dg2");
}

/**
 * 添加活动
 */
function addActivity() {
	$S("ActivityStatusFlag","add");
	var diag = new Dialog("Diag3");
	diag.Width =700;
	diag.Height = 500;
	diag.Title = "新增模块";
	diag.URL = "Promotion/Homepage/PromotionHomepageActivityDialog.jsp?ActivityStatusFlag=add";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveActivity;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新增模块";
	diag.show();
}
/**
 * 修改活动
 */
function editActivity(){
	$S("ActivityStatusFlag","edit");
	var dt = DataGrid.getSelectedData("dg2");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的活动！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能修改一个活动！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height =500;
	diag.Title = "编辑模块";
	diag.URL = "Promotion/Homepage/PromotionHomepageActivityDialog.jsp?ID=" + dr.get("ID")+"&ActivityStatusFlag=edit";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveActivity;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑模块";
	diag.show();
}
/**
 * 保存活动信息
 */
function saveActivity(){
	if($DW.Verify.hasError()){
		return;
	}
	if(!$DW.checkImgPath()){
		Dialog.alert("请为活动选择展示图片");
		return;
	}
	var dc = $DW.Form.getData("form2");
	dc.add("ActivityStatusFlag",$V("ActivityStatusFlag"));
	dc.add("ModuleID",$V("ModuleID"));
	Server.sendRequest("com.sinosoft.cms.document.PromotionManage.saveActivity",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg2");});
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
	DataGrid.save("dg2","com.sinosoft.cms.document.PromotionManage.saveActivityIndex",function(){DataGrid.loadData('dg2');});
}
/**
 * 删除活动
 */
function dellActivity(){
	var arr = DataGrid.getSelectedValue("dg2");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的模块！");
		return;
	}
	Dialog.confirm("删除后不可恢复，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.sinosoft.cms.document.PromotionManage.deleteActivity", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg2");
						}
					});
				});
	});
}
</script>
</head>
<body>
<input type="hidden" value="" id="ActivityStatusFlag" name="ActivityStatusFlag">
<input type="hidden" value="<%=request.getParameter("ModuleID") %>" id="ModuleID" name="ModuleID">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left">
					<z:tbutton  onClick="addActivity()"><img src="../../Icons/icon003a2.gif" width="20" height="20" />添加活动信息</z:tbutton></div>
					<z:tbutton  onClick="editActivity()"><img src="../../Icons/icon003a2.gif" width="20" height="20" />编辑活动信息</z:tbutton></div>
					<z:tbutton  onClick="dellActivity()"><img src="../../Icons/icon003a2.gif" width="20" height="20" />删除活动信息</z:tbutton></div>
					<z:tbutton  onClick="saveIndex()"><img src="../../Icons/icon003a2.gif" width="20" height="20" />保存活动顺序</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">
					活动名称: <input name="title" type="text" id="title"> 
					<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<z:datagrid id="dg2" method="com.sinosoft.cms.document.PromotionManage.dg1DataBindHomepageActivity" size="10">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="20" height="30" ztype="RowNo" drag="true">
							<img src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="15" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="150" ><b>活动名称</b></td>
							<td width="100"><strong>参与人数</strong></td>
							<td width="100" sortfield="OrderFlag" direction="" ><strong>顺序</strong></td>
							<td width="150"><strong>结束时间</strong></td>
							<td width="100"><strong>创建人</strong></td>
							<td width="150"><strong>创建时间</strong></td>
							<td width="100"><strong>修改人</strong></td>
							<td width="150"><strong>修改时间</strong></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td title="${ActivityName}">${ActivityName}</td>
							<td title="${peopleNum}">${peopleNum}</td>
							<td title="${OrderFlag}">${OrderFlag}</td>
							<td title="${endtime}">${endtime}</td>
							<td title="${CreateUser}">${CreateUser}</td>
							<td title="${CreateDate}">${CreateDate}</td>
							<td title="${ModifyUser}">${ModifyUser}</td>
							<td title="${ModifyDate}">${ModifyDate}</td>
						</tr>
						<tr ztype="edit" bgcolor="#E1F3FF">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td ><span title="${ActivityName}">${ActivityName}</span></td>
							<td>${peopleNum}</td>
							<td><input name="OrderFlag" type="text" id="OrderFlag" value="${OrderFlag}" verify="PositiveNumber" size="15"></td>
							<td title="${endtime}">${endtime}</td>
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
