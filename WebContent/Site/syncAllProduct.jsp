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
function syncSearchCondition() {
	Dialog.confirm("您确认要重置筛选条件数据吗，重置时可能会影响用户的正常查询？", function() {
		Server.sendRequest(
				"com.sinosoft.product.ProductSearch.syncSearchCondition",
				null, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在同步...");
					p.show();
				});
	});
}
function syncAllProduct() {
	Dialog.confirm("您确认要同步全部产品信息吗，同步时间可能较长？", function() {
		Server.sendRequest(
				"com.sinosoft.product.ProductSearch.syncAllProduct",
				null, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在同步...");
					p.show();
				});
	});
}
function syncAllProductArea() {
	Dialog.confirm("您确认要同步全部产品销售地区信息吗，同步时间可能较长？", function() {
		//mod by wangej 20160107 地区同步增加产品和保险公司的范围选择
		var dc = new DataCollection();	
		dc.add("ProductID", $V("ProductIDArea"));
		dc.add("CompanyID", $V("CompanyIDArea"));
		
		Server.sendRequest(
				"com.sinosoft.product.ProductSearch.syncAllProductArea",
				dc, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在同步...");
					p.show();
				});
	});
}
function syncAllProductHI() {
	Dialog.confirm("您确认要同步全部产品健康告知信息吗，同步时间可能较长？", function() {
		//mod by wangej 20160107 地区同步增加产品和保险公司的范围选择
		var dc = new DataCollection();	
		dc.add("ProductID", $V("ProductIDHI"));
		dc.add("CompanyID", $V("CompanyIDHI"));
		
		Server.sendRequest(
				"com.sinosoft.product.ProductSearch.syncAllProductHI",
				dc, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在同步...");
					p.show();
				});
	});
}
function publishAllProduct(){
	Dialog.confirm("您确认要发布产品中心下全部产品信息吗，发布时间可能较长？", function() {
		Server.sendRequest(
				"com.sinosoft.product.ProductSearch.publishAllProduct",
				null, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在发布...");
					p.show();
				});
	});
}
</script>
</head>
<body>
<z:init method="com.sinosoft.cms.dataservice.Retransmit.init">
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
                <td width="20%" height="30" align="left" type="Tree"><b>系统信息项&nbsp;</b></td>
                <td width="60%" height="30" align="left" ><b>产品&保险公司&nbsp;</b></td>
                <td width="20%" type="Data" field="count"><b>操作按钮</b></td>
              </tr>
				<tr>
					<td>产品筛选条件重置：</td>
					<td></td>
					<td><z:tbutton id="BtnSyncSearchCondition" priv="site_browse" onClick="syncSearchCondition();">
							<img src="../Icons/icon002a9.gif" />重置</z:tbutton></td>
				</tr>					                
				<tr>
					<td>全部产品销售地区同步：</td>
					<td> 产品编码：<input name="ProductIDArea" type="text" id="ProductIDArea" value=""   style="width:150px">
						   保险公司：<z:select name="CompanyIDArea" id="CompanyIDArea" style="width:150px;">${CompanyIDList}</z:select>
					 </td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProductArea();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>		
				<tr>
					<td>全部产品健康告知同步：</td>
					<td> 产品编码：<input name="ProductIDHI" type="text" id="ProductIDHI" value=""   style="width:150px">
						   保险公司：<z:select name="CompanyIDHI" id="CompanyIDHI" style="width:150px;">${CompanyIDList}</z:select>
					 </td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProductHI();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr>
					<td>全部产品信息同步：</td>
					<td></td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProduct();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr>
					<td>全部产品信息发布：</td>
					<td></td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="publishAllProduct();">
							<img src="../Icons/icon002a9.gif" />发布</z:tbutton><span style="color:#ff0000 ">该发布不包含已下线的产品</span></td>
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
</z:init>
</body>
</html>