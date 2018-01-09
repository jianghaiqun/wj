<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>作者详细统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
//订单查询
function doSearch(){ 
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","source",$V("source"));
	DataGrid.setParam("dg2","destination",$V("destination"));
	DataGrid.setParam("dg2","email",$V("email"));
	DataGrid.loadData("dg2");
}
function doExtract(){
	var dc = new DataCollection();
	dc.add("link",$V("link"));
	Dialog.wait("正在抓取数据...");
	Server.sendRequest("com.wangjin.daren.DataMiningManage.emailExtract",dc,function(response){
		Dialog.endWait();
		Dialog.alert(response.Message);
		DataGrid.loadData("dg2");
	});
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
				<td>
				<z:init method="com.wangjin.daren.AuthorDetailInfo.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>
							链接：<input name="link" type="text" id="link" value="" style="width:320px" />
							来源：<z:select style="width:80px;" name="source" id="source">${source}</z:select>
							目的地：<input name="destination" type="text" id="destination" value="" style="width:120px" />
							邮箱：<input name="email" type="text" id="email" value="" style="width:120px" />
							</td>
						</tr>
						<tr>
							<td style="color:red;">暂只支持http://www.mafengwo.cn/（马蜂窝）, http://www.qyer.com/(穷游)</td>
						</tr>
						<tr>
							<td><z:tbutton onClick="doExtract()" id="Submitbutton"><img src="../Icons/icon005a2.gif" width="20" height="20"/>抓取</z:tbutton>
							<z:tbutton onClick="doSearch()" id="Submitbutton"><img src="../Icons/icon018a6.gif" width="20" height="20"/>查询</z:tbutton>
							</td>
		            	</tr>
		             </table>
		        </z:init>
				</td>
			</tr>
			  <tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.wangjin.daren.DataMiningManage.dg2DataBind" size="15" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="40" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
						        <td width="70" style="text-align:center;"><b>电子邮箱</b></td>
						        <td width="70" style="text-align:center;"><b>目的地</b></td>
						        <td width="70" style="text-align:center;"><b>来源</b></td>
						        <td width="200" style="text-align:center;"><b>帖子链接</b></td>
						        <td width="50" style="text-align:center;"><b>评论页码</b></td>
						        <td width="70" style="text-align:center;"><b>创建时间</b></td>
						        <td width="70" style="text-align:center;"><b>创建人</b></td>
							</tr>
							<tr style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td title="${Email}">${Email}</td>
								<td title="${destination}">${destination}</td>
								<td title="${Prop1}">${Prop1}</td>
								<td title="${Link}">${Link}</td>
								<td title="${PageNo}">${PageNo}</td>
								<td title="${AddTime}">${AddTime}</td>
								<td title="${AddUser}">${AddUser}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
					</tr>
				  </table>
				</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>
