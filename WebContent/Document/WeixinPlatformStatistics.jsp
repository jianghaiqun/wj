<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
/**
 * 查询
 */
function doSearch() {
	DataGrid.setParam("dg2", Constant.PageIndex, 0);
	DataGrid.setParam("dg2", "sceneId", $V("sceneId"));
	DataGrid.setParam("dg2", "Status", $V("Status"));
	DataGrid.setParam("dg2", "openid", $V("openid"));
	DataGrid.setParam("dg2", "nickname", $V("nickname"));
	DataGrid.setParam("dg2", "sex", $V("sex"));
	DataGrid.setParam("dg2", "city", $V("city"));
	DataGrid.setParam("dg2", "province", $V("province"));
	DataGrid.setParam("dg2", "createStartDate", $V("createStartDate"));
	DataGrid.setParam("dg2", "createEndDate", $V("createEndDate"));
	DataGrid.loadData("dg2");
}

function openWeixinUserInfo(openid){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 300;
	diag.Title = "微信关注用户信息";
	diag.URL = "Document/WeixinPlatformUserInfo.jsp?openid=" + openid;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "用户信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
}
</script>
</head>
<body>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
				<tr>
					<td valign="top">
						<fieldset>
							<legend><label>公众平台统计</label></legend> 
							<form id="form1" >
								<table>
									<tr>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">关注场景值：</td>
										<td><input value="" type="text" id="sceneId" name="sceneId" ztype="String" class="inputText" size="10"></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">关注时状态：</td>
										<td><z:select style="width:90px;" >
											<select name="Status" id="Status"> 
											<option value=""></option> 
							                  <option value="0">未关注用户扫描</option>
							                  <option value="1">已关注用户扫描</option>
							                </select>               
										</z:select></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">关注人昵称：</td>
										<td><input value="" type="text" id="nickname" name="nickname" ztype="String" class="inputText" size="14"></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">性别：</td>
										<td><z:select style="width:50px;" >
											<select name="sex" id="sex"> 
											<option value=""></option> 
							                  <option value="1">男</option>
							                  <option value="2">女</option>
							                </select>               
										</z:select></td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">关注人openid：</td>
										<td><input value="" type="text" id="openid" name="openid" ztype="String" class="inputText" size="30"></td>
										<td></td>
									</tr>
									<tr>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">城市：</td>
										<td><input value="" type="text" id="city" name="city" ztype="String" class="inputText" size="10"></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">省份：</td>
										<td><input value="" type="text" id="province" name="province" ztype="String" class="inputText" size="12"></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">关注开始时间：</td>
										<td><input name="createStartDate" id="createStartDate" value="" type="text" size="14" ztype="Date" /></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">关注结束时间：</td>
										<td><input name="createEndDate" id="createEndDate" value="" type="text" size="14" ztype="Date" /></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="doSearch()"></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</table>
							</form>
						</fieldset>
						<table>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td style="padding: 0px 5px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
							<tr>
								<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
									<z:datagrid id="dg2" method="com.sinosoft.cms.document.WeixinPlatform.dg2DataBind" size="15" autoFill="true"  scroll="true" lazy="true" multiSelect="true">
										<table width="100%" cellpadding="2" cellspacing="0"
											class="dataTable" afterdrag="afterRowDragEnd" 
											style="table-layout: fixed" fixedHeight="343px">
											<tr ztype="head" class="dataTableHead">
												<td width="30" height="30" ztype="RowNo" drag="true">
												<img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
												<td width="110px"><strong>ID</strong></td>
												<td width="50px"><strong>场景值</strong></td>
												<td width="80px;"><strong>关注时状态</strong></td>
												<td width="80px;"><strong>关注时间</strong></td>
												<td width="160px;"><strong>关注人openid</strong></td>
												<td width="100px;"><strong>关注人昵称</strong></td>
												<td width="50px;"><strong>性别</strong></td>
												<td width="80px;"><strong>城市</strong></td>
												<td width="80px;"><strong>省份</strong></td>
											</tr>
											<tr style="background-color: #F9FBFC">
                    							<td align="center">&nbsp;</td>
												<td>${id}</td>
												<td>${sceneId}</td>
												<td>${STATUS}</td>
												<td>${CreateTime}</td>
												<td>${FromUserName}</td>
												<td><a onclick="openWeixinUserInfo('${FromUserName}');" href="###" title="点击查看详细">${nickname}</a></td>
												<td>${sex}</td>
												<td>${city}</td>
												<td>${province}</td>
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
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>