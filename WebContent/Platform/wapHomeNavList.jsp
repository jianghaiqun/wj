<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分设置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){

	$("dg1").afterEdit = function(tr,dr){
		dr.set("CodeValue",$V("CodeValue"));
		dr.set("CodeName",$V("CodeName"));
		dr.set("prop1",$V("prop1"));
		return true;
	}
});
function edit() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的选项！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择1条信息修改！");
		return;
	}
	if(Verify.hasError()){
		return;
	}
	var dr = drs[0];
	var dc = new DataCollection();
	dc.add("CodeType", dr.get("CodeType"));
	dc.add("ParentCode", dr.get("ParentCode"));
	dc.add("CodeValue", dr.get("CodeValue"));
	dc.add("CodeName", dr.get("CodeName"));
	dc.add("prop1", dr.get("prop1"));
	Server.sendRequest("com.sinosoft.platform.WapHomeNavList.edit", dc,
		function(response) {
			Dialog.alert(response.Message);
			if(response.Status==1){
				DataGrid.loadData('dg1');
			}
		});
}
function clearCache(){
	var dc = new DataCollection();
	Server.sendRequest("com.sinosoft.platform.WapHomeNavList.clearCache", dc,
		function(response) {
			Dialog.alert(response.Message);
		});
	
}
function clearCacheForWapCampaigns(){
	var dc = new DataCollection();
	Server.sendRequest("com.sinosoft.platform.WapHomeNavList.clearCacheForWapCampaigns", dc,
		function(response) {
			Dialog.alert(response.Message);
		});
	
}
</script>
</head>
<body>
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td width="50%" valign="top">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" id="ConfigTable">
					<tr>
						<td style="padding: 0px 5px;"><z:tbutton onClick="edit()"><img src="../Icons/icon021a4.gif" />保存</z:tbutton> 
						<z:tbutton onClick="clearCache()"><img src="../Icons/icon021a4.gif" />清理缓存</z:tbutton>
						<z:tbutton onClick="clearCacheForWapCampaigns()"><img src="../Icons/icon021a4.gif" />清理wap站广告缓存</z:tbutton>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="padding: 0px 5px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="6"
								class="blockTable">
								<tr>
									<td
										style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
										<z:datagrid id="dg1"
											method="com.sinosoft.platform.WapHomeNavList.dg1DataBind"
											size="10" >
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable">
												<tr ztype="head" class="dataTableHead">
													<td width="20px" height="30" ztype="RowNo" ><img
														src="../Framework/Images/icon_drag.gif" width="16"
														height="16">
													</td>
													<td width="20px" height="30" ztype="Selector" field="id">&nbsp;</td>
													<td width="40px"><b>代码类型</b>
													</td>
													<td width="40px"><b>父节点</b>
													</td>
													<td width="80px"><b>链接地址</b>
													</td>
													<td width="40px"><b>显示名称</b>
													</td>
													<td width="80px"><b>图片地址</b>
													</td>
												</tr>
												<tr style="background-color: #F9FBFC">
													<td width="3%">&nbsp; 
													</td>
													<td>&nbsp;</td>
													<td>${CodeType}</td>
													<td>${ParentCode}</td>
													<td>${CodeValue}</td>
													<td>${CodeName}</td>
													<td>${prop1}</td>
												</tr>
												<tr ztype="edit" bgcolor="#E1F3FF">
													<td bgcolor="#E1F3FF">&nbsp;</td>
													<td>&nbsp;</td>
													<td>${CodeType}</td>
													<td>${ParentCode}</td>
													<td><input name="CodeValue" type="text" id="CodeValue"
														value="${CodeValue}" size="80"></td>
													<td><input name="CodeName" type="text" id="CodeName"
														value="${CodeName}" size="40"></td>
													<td><input name="prop1" type="text" id="prop1"
														value="${prop1}" size="80"></td>
													<td type="hidden">${CodeType}</td>
												</tr>
												<tr ztype="pagebar">
													<td colspan="6" align="left">${PageBar}</td>
												</tr>
											</table>
										</z:datagrid>
									</td>
								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>