<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","productId",$V("productId"));
	DataGrid.loadData("dg1");
}
function createFile() {
	
	var dc = new DataCollection();
	dc.add("productId", $V("productId"));
	Dialog.wait("正在生成文件......"); 
	Server.sendRequest("com.sinosoft.cms.dataservice.ExportProductInfo.createFile",
		dc, function() {
			Dialog.endWait();
			var response = Server.getResponse();
			Dialog.alert(response.Message, function() {
				if (response.Status == 0) {
					DataGrid.loadData("dg1");
					$D.close();
				}
			});
		});
}

// 删除
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.dataservice.ExportProductInfo.delFileInfo",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}
</script>
</head>
<body onContextMenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<fieldset>
				<legend><label>对接辅助产品信息文件管理</label></legend> 
		<table class="blockTable">
			<tr>
				<td style="padding:4px 10px;">
				产品ID <input type="text" id="productId" style="width:100px; ">
				<input type="button" name="createFile" value="生成辅助文件" onClick="createFile()"></td>
			</tr>
		</table>
		</fieldset>
		<table class="blockTable">
			<tr>
				<td><input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton">
				<input type="button" name="delbutton" value="删除" onClick="del()" id="delbutton"></td></tr>
		</table>
	<br />
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			
			<tr>
				<td style="padding:0px 5px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.ExportProductInfo.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterDrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<!-- <td width="3%" ztype="selector" field="id">&nbsp;</td> -->
							<td width="18%"><b>产品ID</b></td>
							<td width="10%"><b>创建人</b></td>
							<td width="10%"><b>创建时间</b></td>
							<td width="10%"><b>修改人</b></td>
							<td width="10%"><b>修改时间</b></td>
							<td width="5%"><b>下载</b></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<!-- <td>&nbsp;</td> -->
							<td>${ProductId}</td>
							<td>${AddUser}</td>
							<td>${AddTime}</td>
							<td>${ModifyUser}</td>
							<td>${ModifyTime}</td>
							<td><a href="<%=Config.getValue("ContextPath") %>/EFile/${FilePath}" target="_blank"><img src="../Framework/Images/icon_down.gif" width="15" height="15" style="cursor:pointer;"/></a></td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="10" align="center">${PageBar}</td>
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
