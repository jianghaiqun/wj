<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评论点赞管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","userIP",$V("userIP"));
	DataGrid.setParam("dg2","productName",$V("productName"));
	DataGrid.setParam("dg2","relaID",$V("relaID"));
	DataGrid.loadData("dg2");
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
					<tr>
						<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />评论点赞管理</td>
					</tr>
				
					<tr>
						<td>用户IP：<input name="userIP" type="text" id="userIP" value="" style="width:180px"/>
							&nbsp;关联ID：<input name="relaID" type="text" id="relaID" value="" style="width:90px"/>
							&nbsp;产品名：<input name="productName" type="text" id="productName" value="" style="width:180px"/>
							&nbsp;&nbsp;
		                    <input type="button" name="Submit" value="查询" onClick="doSearch();">                     
						</td>
					</tr>
				
					<tr>
						<td style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
							<z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.Comment.dg2DataBind" size="15">
								<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
									<tr ztype="head" class="dataTableHead">
										<td width="4%" ztype="RowNo"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
										<td width="18%"><b>点赞用户IP</b></td>
										<td width="10%"><b>点赞时间</b></td>
										<td width="6%"><b>关联ID</b></td>
										<td width="15%"><b>产品名</b></td>
										<td width="10%"><b>评论订单号</b></td>
										<td width="37%"><b>评论内容</b></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td title="${userIP}">${userIP}</td>
										<td>${praisedDate}</td>
										<td>${RelaID}</td>
										<td title="${productName}">${productName}</td>
										<td>${orderSn}</td>
										<td title="${Content}">${Content}</td>
									</tr>
									<tr ztype="pagebar">
									  <td colspan="7" align="center">${PageBar}</td>
									</tr>
								</table>
							</z:datagrid>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>