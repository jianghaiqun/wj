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
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","source",$V("source"));
	DataGrid.setParam("dg1","isExist",$V("isExist"));
	DataGrid.setParam("dg1","link",$V("link"));
	DataGrid.loadData("dg1");
}
function doCheck(){
	var dc = new DataCollection();
	dc.add("source",$V("source"));
	dc.add("isExist",$V("isExist"));
	dc.add("link",$V("link"));
	Dialog.wait("正在检查数据...");
	Server.sendRequest("com.wangjin.daren.DataMiningManage.linkCheck",dc,function(response){
		Dialog.endWait();
		Dialog.alert(response.Message);
		doSearch();
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
							<td>来源：<z:select style="width:80px;" name="source" id="source">${source}</z:select>
								存在关键字：<z:select id="isExist" name="isExist" style="width:50px">${YesOrNo}</z:select>
							
							帖子链接：<input name="link" type="text" id="link" value="" style="width:280px" />
							</td>
						</tr>
						<tr>
							<td><z:tbutton onClick="doCheck()" id="Submitbutton"><img src="../Icons/icon005a2.gif" width="20" height="20"/>检查</z:tbutton>
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
					<z:datagrid id="dg1" method="com.wangjin.daren.DataMiningManage.dg1DataBind" size="15" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="40" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
						        <td width="70" style="text-align:center;"><b>来源</b></td>
						        <td width="250" style="text-align:center;"><b>帖子链接</b></td>
						        <td width="70" style="text-align:center;"><b>检查时间</b></td>
						        <td width="70" style="text-align:center;"><b>是否存在关键字</b></td>
						        <td width="70" style="text-align:center;"><b>关键字</b></td>
						        <td width="70" style="text-align:center;"><b>第几页</b></td>
						        <td width="70" style="text-align:center;"><b>联系人</b></td>
						        <td width="70" style="text-align:center;"><b>作者</b></td>
							</tr>
							<tr style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td title="${Platform}">${Platform}</td>
								<td title="${Link}">${Link}</td>
								<td title="${CheckTime}">${CheckTime}</td>
								<td title="${isExistName}">${isExistName}</td>
								<td title="${Keyword}">${Keyword}</td>
								<td title="${PageNo}">${PageNo}</td>
								<td title="${linkman}">${linkman}</td>
								<td title="${Author}">${Author}</td>
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
