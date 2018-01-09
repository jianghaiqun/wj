<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script type="text/javascript" src="../../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
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
/**
 * 暂停
 */
function PauseStock(){
	var dt = DataGrid.getSelectedData("dg3");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要暂停的库存！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能暂停一个库存！");
		return;
	}
	var status=DataGrid.getSelectedValueByColumn("dg3","status");
	if(status!="待兑换"){
		Dialog.alert("只有状态为'待兑换'的库存才可以暂停！");
		return;
	}
	var dr=drs[0];
	var arr = DataGrid.getSelectedValue("dg3");
	var dc = new DataCollection();
	dc.add("ID", dr.get("ID"));
	Server.sendRequest("com.wangjin.pointsMall.GiftManage.PauseStock", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData("dg3");
						$D.close();
					}
				});
			});
}
/**
 * 恢复
 */
function RecoveryStock(){
	var dt = DataGrid.getSelectedData("dg3");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要恢复的库存！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能恢复一个库存！");
		return;
	}
	var status=DataGrid.getSelectedValueByColumn("dg3","status");
	if(status!="已暂停"){
		Dialog.alert("只有状态为'已暂停'的库存才可以恢复！");
		return;
	}
	var arr = DataGrid.getSelectedValue("dg3");
	var dc = new DataCollection();
	dc.add("ID", dr.get("ID"));
	Server.sendRequest("com.wangjin.pointsMall.GiftManage.RecoveryStock", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData("dg3");
						$D.close();
					}
				});
			});
}
/**
 * 删除
 */
function dell(){
	var dt = DataGrid.getSelectedData("dg3");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要删除的库存！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能删除一个库存！");
		return;
	}
	var arr = DataGrid.getSelectedValue("dg3");
	var dc = new DataCollection();
	dc.add("ID", dr.get("ID"));
	Dialog.confirm("库存删除后不可恢复，确认要删除？",function() { 
		Dialog.wait("正在删除库存......"); 
		Server.sendRequest("com.wangjin.pointsMall.GiftManage.dellStock", dc, function() {
			Dialog.endWait();
			var response = Server.getResponse();
			Dialog.alert(response.Message, function() {
				if (response.Status == 1) {
					DataGrid.loadData("dg3");
					$D.close();
				}
			});
		});
	});
}
/**
 * 查询
 */
function search() {
	DataGrid.setParam("dg3", Constant.PageIndex, 0);
	DataGrid.setParam("dg3", "email", $V("email"));
	DataGrid.setParam("dg3", "mobileNO", $V("mobileNO"));
	DataGrid.loadData("dg3");
}
//展示浮动框
function showMessage(t){
	var innertext=t.innerHTML;
	if(innertext.indexOf("&nbsp;")!=-1){
		innertext=innertext.replace(new RegExp("&nbsp;", 'g'),"");
	}
	if(innertext==""||innertext==null){
		t.title="";
	}else{
		t.title=t.innerHTML;
	}
}
/**
 * 编辑库存
 */
function editStock() {
	var dt = DataGrid.getSelectedData("dg3");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的礼品！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能修改一个礼品！");
		return;
	}
	var status=DataGrid.getSelectedValueByColumn("dg3","status");
	if(status!="待兑换"){
		Dialog.alert("只有状态为'待兑换'的库存才可以修改！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag2");
	diag.Width = 800;
	diag.Height = 100;
	diag.Title = "编辑库存";
	diag.URL = "PointsMall/StockDialog04.jsp?ID=" + dr.get("ID");
	diag.onLoad = function() {
	};
	diag.OKEvent = saveStock;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑库存";
	diag.show();
}
/**
 * 库存的保存
 */
function saveStock() {
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form3");
	Server.sendRequest("com.wangjin.pointsMall.GiftManage.saveStock",dc,function(response){
		if(response.Status==1){
			Dialog.alert(
						response.Message,function(){
							$D.close();DataGrid.loadData("dg3");
						}
					);
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}
</script>
</head>
<body class="dialogBody">
		<input type="hidden"  id="ID" name="ID" value="${ID}">
		<table width="100%" border='0' cellpadding='10' cellspacing='0'>
			<tr>
				<td width="50%" valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" id="ConfigTable">
						<tr>
							<td valign="top">
								<fieldset>
									<legend>
										<label>商品库存管理</label>
									</legend>
								</fieldset>
								<table>
									<tr>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">邮箱：</td>
										<td><input value="" type="text" id="email" name="email" ztype="String" class="inputText" size="20"></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">电话：</td>
										<td><input value="" type="text" id="mobileNO" name="mobileNO" ztype="String" class="inputText" size="20"></td>
										<td><input value="查 询" type="button" onclick="search()"></td>
									</tr>
								</table> 
								<z:tbutton onClick="editStock()"><img src="../../Icons/icon021a2.gif" width="20" height="20" />修改</z:tbutton> 
								<z:tbutton onClick="dell()"><img src="../../Icons/icon021a2.gif" width="20" height="20" />删除</z:tbutton> 
								<z:tbutton onClick="PauseStock()"><img src="../../Icons/icon021a2.gif" width="20" height="20" />停用</z:tbutton> 
								<z:tbutton onClick="RecoveryStock()"><img src="../../Icons/icon021a2.gif" width="20" height="20" />恢复</z:tbutton>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td style="padding: 0px 5px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
									<tr>
										<td
											style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
											<z:datagrid id="dg3" method="com.wangjin.pointsMall.GiftManage.dg1DataBindRecords" size="10" autoFill="true" scroll="true" lazy="false" multiSelect="false">
												<table width="930" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="afterRowDragEnd" style="table-layout: fixed" fixedHeight="120px">
													<tr ztype="head" class="dataTableHead">
														<td width="30px;" height="30" ztype="RowNo" drag="true"><img src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
														<td width="20px;" height="30" ztype="Selector" field="id">&nbsp;</td>
														<td width="260px;"><strong>商品名称</strong></td>
														<td width="220px;"><strong>卡号</strong></td>
														<td width="80px;"><strong>积分兑换值</strong></td>
														<td width="55px;"><strong>状态</strong></td>
														<td width="200px;"><strong>兑换会员邮箱</strong></td>
														<td width="200px;"><strong>兑换电话</strong></td>
														<td width="150px;"><strong>创建人</strong></td>
														<td width="150px;"><strong>上传时间</strong></td>
														<td width="150px;"><strong>有效期时间</strong></td>
														<td width="150px;"><strong>修改人</strong></td>
														<td width="150px;"><strong>修改时间</strong></td>
													</tr>
													<tr onDblClick="editStock(${id});" style="background-color: #F9FBFC">
														<td onmouseover="">&nbsp;</td>
														<td>&nbsp;</td>
														<td onmouseover="showMessage(this)">${GoodsName}</td>
														<td onmouseover="showMessage(this)">${CardNo}</td>
														<td onmouseover="showMessage(this)">${PayPoints}</td>
														<td onmouseover="showMessage(this)">${status}</td>
														<td onmouseover="showMessage(this)">${email}</td>
														<td onmouseover="showMessage(this)">${mobileNo}</td>
														<td onmouseover="showMessage(this)">${CreateUser}</td>
														<td onmouseover="showMessage(this)">${CreateDate}</td>
														<td onmouseover="showMessage(this)">${OutDate}</td>
														<td onmouseover="showMessage(this)">${ModifyUser}</td>
														<td onmouseover="showMessage(this)">${ModifyDate}</td>
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
					</table>
				</td>
			</tr>
		</table>
</body>
</html>
