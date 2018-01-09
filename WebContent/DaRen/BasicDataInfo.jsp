<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>达人录入统计基础数据</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script language="javascript">
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("CodeType",$V("CodeType"));
		dr.set("CodeName",$V("CodeName"));
		dr.set("Memo",$V("Memo"));
		return true;
	}
});

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchCodeType'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("SearchCodeType") == "请输入代码类别或名称") {
		$S("SearchCodeType","");
	}
}

function doSearch(){
	var searchName = "";
	if($V("SearchCodeType") != "请输入代码类别或名称"){
		searchName = $V("SearchCodeType").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchCodeType",searchName);
	DataGrid.loadData("dg1");
}

function codeList(codeType,codeName){
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 380;
	diag.Title = "代码";
	diag.URL = "Platform/CodeList.jsp?CodeType="+codeType+"&CodeName="+codeName;
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
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon023a6.gif" />基础数据</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
						<div style="float: left; white-space: nowrap;"><input type="text" name="SearchCodeType"
						id="SearchCodeType" value="请输入代码类别或名称" onFocus="delKeyWord();" style="width:150px"> 
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="doSearch()"></div></td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.platform.Code.dg2BindCode"
						size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo">序号</td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="18%"><b>代码类别</b></td>
								<td width="24%"><b>代码名称</b></td>
								<td width="28%">备注</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><span class="fc_bl3"><a href="javascript:void(0);" onClick="codeList('${CodeType}','${CodeName}');">${CodeType}</a></span></td>
								<td>${CodeName}</td>
								<td>${Memo}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="5">${PageBar}</td>
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
