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
	$("dg3").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 		return;
    	}
		dr.set("ProductName",$V("ProductName"));
		dr.set("OrderFlag",$V("OrderFlag"));
		return true;
	}
});
/**
 * 查询
 */
function sear() {
	DataGrid.setParam("dg3", Constant.PageIndex, 0);
	DataGrid.setParam("dg3", "productname", $V("productname"));
	DataGrid.loadData("dg3");
}
/**
 * 添加产品
 */
function add() {
	if($V("ID")==''||$V("ID")==null){
		Dialog.alert("请先保存模块基本信息，再对应添加产品！");
		window.parent.chooseChild("BaseInfo");
		return;
	}
	var diag = new Dialog("Diag4");
	diag.Width =600;
	diag.Height = 360;
	diag.Title = "添加产品";
	diag.URL = "Promotion/Normal/PromotionProductSearchDialog.jsp";
	diag.onLoad = function() {
	};
	diag.OKEvent = addProduct;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加产品";
	diag.show();
}
/**
 * 保存产品信息
 */
function addProduct(){
	var arr = $DW.DataGrid.getSelectedValue("dg4");
	if (!arr || arr.length ==0) {
		Dialog.alert("请选择产品！");
		return;
	}
	var dc = new DataCollection();
	var flag=window.parent.getModelType();
	if(flag=="true"){
		if (!arr || arr.length > 1) {
			Dialog.alert("该类型的模块只能选择一个产品！");
			return;
		}
		var productnum=$V("productnum");
		if(parseInt(productnum)>=1){
			Dialog.alert("该类型的模块只能添加一个产品，删除多余的产品！");
			return;
		}
	}
	dc.add("productID", arr.join());
	if($V("ID")==''||$V("ID")==null){
		Dialog.alert("请先保存模块基本信息，再对应添加产品！");
		window.parent.chooseChild("BaseInfo");
		return;
	}
	dc.add("ModelID",$V("ID"));
	Server.sendRequest("com.sinosoft.cms.document.PromotionManage.addProduct",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				$D.close();DataGrid.loadData("dg3");
				$S("productnum",response.get("productnum"));
				});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}
/**
 * 删除产品
 */
function dell(){
	var arr = DataGrid.getSelectedValue("dg3");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的产品！");
		return;
	}
	Dialog.confirm("删除后不可恢复，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.sinosoft.cms.document.PromotionManage.deleteModelProduct", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg3");
							$S("productnum",response.get("productnum"));
						}
					});
				});
	});
}
/**
 * 设置亮点
 */
function setHighlights(){
	var dt = DataGrid.getSelectedData("dg3");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要设置的产品！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能设置的一个产品的亮点！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag4");
	diag.Width = 600;
	diag.Height =300;
	diag.Title = "设置亮点";
	diag.URL = "Promotion/Normal/PromotionProductHighlightsDialog.jsp?ID=" + dr.get("ID")+"&ModelID="+$V("ID");
	diag.onLoad = function() {
	};
	diag.OKEvent = saveProductHighlights;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "设置亮点";
	diag.show();
}
/**
 * 保存亮点
 */
function saveProductHighlights(){
	if($DW.Verify.hasError()){
		return;
	}
	var ModuleType = $DW.$V("ModuleType");
	var index = $DW.$V("index");
	var index_array=index.split(",");
	var DetailNum = $DW.$V("DetailNum");
	for(var i=0;i<parseInt(DetailNum);i++){
		var Detail1 = $DW.$V("Detail1_"+index_array[i]);
		if(Detail1==null||Detail1==''){
			Dialog.alert("第"+(i+1)+"行的亮点描述1没有填写！");
			return;
		}
		if(ModuleType=='03'||ModuleType=='04'){
			var Detail2 = $DW.$V("Detail2_"+index_array[i]);
			if(Detail2==null||Detail2==''){
				Dialog.alert("第"+(i+1)+"行的亮点描述2没有填写！");
				return;
			}
		}
	}
	var dc = $DW.Form.getData("form4");
	var dt = DataGrid.getSelectedData("dg3");
	var drs = dt.Rows;
	var dr = drs[0];
	dc.add("DetailId",dr.get("ID"));
	var productid=DataGrid.getSelectedValueByColumn("dg3","productid")
	dc.add("productId",productid);
	Server.sendRequest("com.sinosoft.cms.document.PromotionManage.setHighlights",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg3");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}
/**
 * 保存产品信息
 */
function saveProductInfo(){
	DataGrid.save("dg3","com.sinosoft.cms.document.PromotionManage.saveProductInfo",function(){DataGrid.loadData('dg3');});
}
//根据column名来取得value值
DataGrid.getSelectedValueByColumn = function(ele, column) {
	ele = $(ele);
	var ds = ele.DataSource;
	for ( var i = 0; i < ds.Columns.length; i++) {
		if (ds.Columns[i].Name == column.toLowerCase()) {
			for ( var k = 1; k < ele.rows.length; k++) {
				if (ele.rows[k].Selected) {
					return ds.Values[k - 1][i];
				}
			}
		}
	}
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
<z:init method="com.sinosoft.cms.document.PromotionManage.initProductDialog">
	<input type="hidden"  id="ID" name="ID" value="<%=request.getParameter("ID")%>">
	<input type="hidden"  id="productnum" name="productnum" value="${productnum}">
</z:init>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" height="100%" align="center" cellpadding="0" cellspacing="0" id="ConfigTable">
			<tr>
				产品名称：
				<input name="productname" type="text" id="productname" value="" style="width:90px">
                <input type="button"  value="查询" onClick="sear()">
                &nbsp;&nbsp;
                <input type="button"  value="添加" onClick="add()">
                &nbsp;&nbsp;
                <input type="button"  value="保存" onClick="saveProductInfo()">
                &nbsp;&nbsp;
                <input type="button"  value="删除" onClick="dell()">
                &nbsp;&nbsp;
                <input type="button"  value="设置产品亮点" onClick="setHighlights()">
			</tr>
			<tr>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" height="100%" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
					<tr>
						<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
							<z:datagrid id="dg3" method="com.sinosoft.cms.document.PromotionManage.dg1DataBindProduct"  autoFill="true" scroll="true" lazy="false" multiSelect="true">
								<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="afterRowDragEnd" style="table-layout: fixed" fixedHeight="270px">
									<tr ztype="head" class="dataTableHead">
										<td width="20" height="30" ztype="RowNo" drag="true"><img src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
										<td width="15" height="30" ztype="Selector" field="id">&nbsp;</td>
										<td width="100"><strong>产品代码</strong></td>
										<td width="150"><strong>产品名称</strong></td>
										<td width="100"><strong>顺序</strong></td>
									</tr>
									<tr style="background-color: #F9FBFC">
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>${productid}</td>
										<td>${ProductName}</td>
										<td>${OrderFlag}</td>
									</tr>
									<tr ztype="edit" bgcolor="#E1F3FF">
										<td bgcolor="#E1F3FF">&nbsp;</td>
										<td>&nbsp;</td>
										<td>${productid}</td>
										<td><input name="ProductName" type="text" id="ProductName" value="${ProductName}"  size="15"></td>
										<td><input name="OrderFlag" type="text" id="OrderFlag" value="${OrderFlag}" verify="PositiveNumber" size="15"></td>
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