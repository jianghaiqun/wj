<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品信息同步</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function syncAllProduct() {
	Dialog.confirm("您确认要同步全部产品信息吗，同步时间可能较长？", function() {
		Server.sendRequest(
				"com.sinosoft.product.DlrSynAllProduct.syncAllProduct",
				null, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在同步...");
					p.show();
				});
	});
}
function syncAllProductArea() {
	Dialog.confirm("您确认要同步全部产品销售地区信息吗，同步时间可能较长？", function() {
		Server.sendRequest(
				"com.sinosoft.product.DlrSynAllProduct.syncAllProductArea",
				null, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在同步...");
					p.show();
				});
	});
}
function syncAllProductHI() {
	Dialog.confirm("您确认要同步全部产品健康告知信息吗，同步时间可能较长？", function() {
		Server.sendRequest(
				"com.sinosoft.product.DlrSynAllProduct.syncAllProductHI",
				null, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在同步...");
					p.show();
				});
	});
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon018a1.gif" />产品信息同步管理</td>
      </tr>
      <tr>
        <td style="padding:0"><table width="60%" border="0" cellspacing="6" style="border-collapse: separate; border-spacing: 6px;">
          <tr>
            <td width="55%" valign="top"><table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
              <tr class="dataTableHead">
                <td width="36%" height="30" align="left" type="Tree"><b>系统信息项&nbsp;</b></td>
                <td width="64%" type="Data" field="count"><b>操作按钮</b></td>
              </tr>
               
				<tr>
					<td>全部产品信息同步：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProduct();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr>
					<td>全部产品销售地区同步：</td>
					<td><z:tbutton id="BtnSyncAllProductArea" priv="site_browse" onClick="syncAllProductArea();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr>
					<td>全部产品健康告知同步：</td>
					<td><z:tbutton id="BtnSyncAllProductHI" priv="site_browse" onClick="syncAllProductHI();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
            </table></td>
          </tr>
        </table>
          <br>
          <p>&nbsp;</p>
          </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>