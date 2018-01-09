<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../template/common/js/jquery.js"></script>
<script src="../../Framework/Main.js"></script>
<script type="text/javascript">
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
 * 查询
 */
function sear() {
	DataGrid.setParam("dg2", Constant.PageIndex, 0);
	DataGrid.setParam("dg2", "BrandName", $V("BrandName"));
	DataGrid.loadData("dg2");
}
/**
 * 添加
 */
function add() {
	var diag = new Dialog("Diag3");
	diag.Width =600;
	diag.Height = 360;
	diag.Title = "添加热卖品牌";
	diag.URL = "Promotion/Homepage/PromotionHomepageSellingBrandDialog.jsp";
	diag.onLoad = function() {
	};
	diag.OKEvent = save;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加热卖品牌";
	diag.show();
}
/**
 * 修改
 */
function edit(){
	$S("SellingBrandStatusFlag","edit");
	var dt = DataGrid.getSelectedData("dg2");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的热卖品牌！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能修改一个热卖品牌！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height =500;
	diag.Title = "编辑热卖品牌";
	diag.URL = "Promotion/Homepage/PromotionHomepageSellingBrandDialog.jsp?ID=" + dr.get("ID")+"&SellingBrandStatusFlag=edit";
	diag.onLoad = function() {
	};
	diag.OKEvent = save;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑热卖品牌";
	diag.show();
}
/**
 * 保存信息
 */
function save(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	dc.add("SellingBrandStatusFlag",$V("SellingBrandStatusFlag"));
	Server.sendRequest("com.sinosoft.cms.document.PromotionManage.saveSellingBrand",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg2");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}
/**
 * 删除
 */
function dell(){
	var arr = DataGrid.getSelectedValue("dg2");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的热卖品牌！");
		return;
	}
	Dialog.confirm("删除后不可恢复，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.sinosoft.cms.document.PromotionManage.deleteSellintBrand", dc,
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
	DataGrid.save("dg2","com.sinosoft.cms.document.PromotionManage.saveHomepageSellingBrandIndex",function(){DataGrid.loadData('dg2');});
}
</script>
<style>
#hotarea {
	width: 160px;
	height: 120px;
	border: #147 1px solid;
	background: #ca6 url(../../Platform/Images/picture.jpg) no-repeat;
	position: relative
}

#hotarea  a {
	position: absolute;
	display: block;
	width: 35px;
	height: 25px;
	border: #fff 1px dashed;
	text-align: center;
	line-height: 24px;
	color: #fff;
}

#hotarea  a:hover {
	text-decoration: none;
	border: #fff 1px solid;
	color: #fff
}
</style>
</head>
<body>
<input type="hidden" value="" id="SellingBrandStatusFlag" name="SellingBrandStatusFlag">
<z:init method="com.sinosoft.cms.document.PromotionManage.initProductDialog">
	<input type="hidden"  id="ID" name="ID" value="<%=request.getParameter("ID")%>">
</z:init>
<table width="150%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" height="100%" align="center" cellpadding="0" cellspacing="0" id="ConfigTable">
			<tr>
				产品名称：
				<input name="BrandName" type="text" id="BrandName" value="" style="width:90px">
                <input type="button"  value="查询" onClick="sear()">
                &nbsp;&nbsp;
                <input type="button"  value="添加" onClick="add()">
                &nbsp;&nbsp;
                <input type="button"  value="修改" onClick="edit()">
                &nbsp;&nbsp;
                <input type="button"  value="删除" onClick="dell()">
                &nbsp;&nbsp;
                <input type="button"  value="保存顺序" onClick="saveIndex()">
			</tr>
			<tr>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" height="100%" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
					<tr>
						<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
							<z:datagrid id="dg2" method="com.sinosoft.cms.document.PromotionManage.dg1DataBindHomepageSellingBrand"  size="10">
								<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="afterRowDragEnd">
									<tr ztype="head" class="dataTableHead">
										<td width="20" height="30" ztype="RowNo" drag="true"><img src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
										<td width="15" height="30" ztype="Selector" field="id">&nbsp;</td>
										<td width="150"><strong>品牌名称</strong></td>
										<td width="100" sortfield="OrderFlag" direction="" ><strong>顺序</strong></td>
										<td width="100"><strong>数量</strong></td>
										<td width="100"><strong>购买人数</strong></td>
										<td width="150"><strong>跳转地址</strong></td>
										<td width="100"><strong>是否展示</strong></td>
										<td width="100"><strong>创建人</strong></td>
										<td width="150"><strong>创建时间</strong></td>
										<td width="100"><strong>修改人</strong></td>
										<td width="150"><strong>修改时间</strong></td>
									</tr>
									<tr style="background-color: #F9FBFC">
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>${BrandName}</td>
										<td>${OrderFlag}</td>
										<td>${count}</td>
										<td>${buynum}</td>
										<td>${URL}</td>
										<td>${isShowName}</td>
										<td>${CreateUser}</td>
										<td>${CreateDate}</td>
										<td>${ModifyUser}</td>
										<td>${ModifyDate}</td>
									</tr>
									<tr ztype="edit" bgcolor="#E1F3FF">
										<td bgcolor="#E1F3FF">&nbsp;</td>
										<td>&nbsp;</td>
										<td>${BrandName}</td>
										<td><input name="OrderFlag" type="text" id="OrderFlag" value="${OrderFlag}" verify="PositiveNumber" size="15"></td>
										<td>${count}</td>
										<td>${buynum}</td>
										<td>${URL}</td>
										<td>${isShowName}</td>
										<td>${CreateUser}</td>
										<td>${CreateDate}</td>
										<td>${ModifyUser}</td>
										<td>${ModifyDate}</td>
									</tr>
									<tr ztype="pagebar">
										<td colspan="9" align="left">${PageBar}</td>
									</tr>
								</table>
							</z:datagrid>
						</td>
					</tr>
				</table>
				</td>
			<tr>
		</table>
	</td>
	</tr>
</table>
</body>
</html>