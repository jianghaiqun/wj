<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单数据daoru</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="${Site.URL}/js/premcalculate.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","comName",$V("comName"));
	DataGrid.setParam("dg2","comCode",$V("comCode"));
	DataGrid.setParam("dg2","hasInfo",$V("hasInfo"));
	DataGrid.loadData("dg2");
}

function setPubInfo(comCode,comName){
    var arr = DataGrid.getSelectedValue("dg2");
	if(comCode && comCode != ""){
		arr = new Array();
		arr[0] = comCode;
	}
	if(!arr || arr.length == 0){
		Dialog.alert("请先选择要编辑的条目！");
		return;
	}
	if(!arr||arr.length>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条！");
		return;
	}

	var diag = new Dialog("Diag");
	diag.Width = 1200;
	diag.Height = 300;
	diag.Title = "设置公告";
	var url = "DataService/PubInfoSetDialog.jsp?comCode="+arr[0]+"&comName="+comName;
	diag.URL = encodeURI(url);
	diag.ShowMessageRow = true;
	diag.MessageTitle = "设置公告页面 ";
	diag.CancelEvent=function(){$D.close();doSearch();};
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
	
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			    <tr>
		            <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 购买流程</td>
		        </tr>
				<tr>
				<td>
					<table  cellspacing="0" cellpadding="3">
						<tr>
							<td>保险公司名称：</td>
							<td> <input name="comName" type="text" id="comName" value="" style="width:200px"></td>
							<td width="100px" align="right">保险公司编码：</td>
							<td> <input name="comCode" type="text" id="comCode" value="" style="width:120px"></td>
							<td width="100px" align="right">是否有公告：</td>
							<td><z:select style="width:100px;"><select name="hasInfo" id="hasInfo" > 
							<option value=""></option> 
		                 	<option value="0">否</option>
		                 	<option value="1">是 </option>
		                	</select></z:select></td>
						    <td>
			            	   <z:tbutton onClick="doSearch();"   id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton>
			            	</td>
		            	</tr>
		            </table>
				</td>
			</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.PublicInform.dg2DataBind" size="20" scroll="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
								<td width="10%" style="text-align:center;"><b>保险公司编码</b></td>
								<td width="25%" style="text-align:center;"><b>保险公司名称</b></td>
								<td width="10%" style="text-align:center;"><b>是否有公告</b></td>
								<td width="10%" style="text-align:center;"><b>操作</b></td>
							</tr>
							<tr onDblClick="setPubInfo('${comCode}','${comName}');" style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td >&nbsp;</td>
								<td >${comCode}</td>
								<td >${comName}</td>
								<td >${HasInfo}</td>
								<td ><a style="cursor: hand;" onClick="setPubInfo('${comCode}','${comName}');">设置公告</a></td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="6">${PageBar}</td>
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