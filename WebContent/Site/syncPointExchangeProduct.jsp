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
	if ($V("ProductID") == null || $V("ProductID") == '') {
		alert("请填写产品编码！");
		return;
	}
	Dialog.confirm("您确认要同步产品信息吗，同步时间可能较长？", function() {
		Dialog.wait("正在同步...");
		var dc = new DataCollection();
		dc.add("ProductID", $V("ProductID"));
		Server.sendRequest("com.sinosoft.product.ProductSearch.syncPointProduct", dc,
				function(response) {
					Dialog.closeEx();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							
							
						}
					});
				});
	});
}
function syncAllProductArea() {
	if ($V("ProductID") == null || $V("ProductID") == '') {
		alert("请填写产品编码！");
		return;
	}
	Dialog.confirm("您确认要同步产品销售地区信息吗，同步时间可能较长？", function() {
		Dialog.wait("正在同步...");
		var dc = new DataCollection();
		dc.add("ProductID", $V("ProductID"));
		Server.sendRequest("com.sinosoft.product.ProductSearch.syncPointProductArea", dc, 
				function(response) {
					Dialog.closeEx();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
								
							
						}
					});
				});
	});
}
function syncAllProductHI() {
	if ($V("ProductID") == null || $V("ProductID") == '') {
		alert("请填写产品编码！");
		return;
	}
	Dialog.confirm("您确认要同步产品健康告知信息吗，同步时间可能较长？", function() {
		Dialog.wait("正在同步...");
		var dc = new DataCollection();
		dc.add("ProductID", $V("ProductID"));
		Server.sendRequest("com.sinosoft.product.ProductSearch.syncPointProductHI", dc, 
				function(response) {
					Dialog.closeEx();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
								
							
						}
					});
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
	                <td width="40%" type="Data" field="count"><b>同步条件</b></td>
	                <td width="24%" type="Data" field="count"><b>操作按钮</b></td>
	              </tr>
              	<tr>
              		<td>产品信息同步：</td>
					<td rowspan="3">产品编码：<input name="ProductID" type="text" id="ProductID" value="" style="width:150px"></td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProduct();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr>
					<td>产品销售地区同步：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProductArea();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr>
					<td>产品健康告知同步：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProductHI();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
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