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
function upload(){
	var diag = new Dialog("Diag1");
	diag.Width = 630;
	diag.Height = 250;
	diag.Title = "上传附件";
	diag.URL = "DataService/ServiceInquiryUpload.jsp";
    diag.OKEvent=OK;
	diag.show();
}

function OK(){
	$DW.upload();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的附件！");
		return;
	}
	Dialog.confirm("你确认要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.dataservice.CustomerServiceManage.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					if ($V("SearchName") != "请输入附件名称") {
						DataGrid.setParam("dg1","Name",$V("SearchName"));
					} else {
						DataGrid.setParam("dg1","Name","");
					}
					DataGrid.loadData("dg1");
				}
			});
		})
	});
}
function onFileUploadCompleted(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	if ($V("SearchName") != "请输入附件名称") {
		DataGrid.setParam("dg1","Name",$V("SearchName"));
	} else {
		DataGrid.setParam("dg1","Name","");
	}
	DataGrid.loadData("dg1");
	if($D){
		setTimeout(function(){$D.close()}, 100);
	}
}

function doSearch(){
	var name = "";
	if ($V("SearchName") != "请输入附件名称") {
		name = $V("SearchName");
	}
	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchName",name);
	//DataList.setParam("dg1","StartDate",$V("StartDate"));
	//DataList.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}

function deal(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要发布的附件！");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Dialog.wait("正在导入数据...");
	Server.sendRequest("com.sinosoft.cms.dataservice.CustomerServiceManage.deal",dc,function(response){
		Dialog.endWait();
		Dialog.alert(response.Message);
		DataGrid.loadData("dg1");
	});
}

function delKeyWord() {
	var keyWord = $V("SearchName");
	if (keyWord == "请输入附件名称") {
		$S("SearchName","");
	}
}
</script>
</head>
<body onContextMenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:8px 10px;" class="blockTd">
				<div style="float: left">
					<z:tbutton id="uploadButton" priv="attach_modify" onClick="upload()">
						<img src="../Icons/icon003a8.gif" />上传</z:tbutton>
					<z:tbutton id="delButton" priv="attach_modify" onClick="del()">
						<img src="../Icons/icon003a3.gif" />删除</z:tbutton>
					<z:tbutton id="publishButton" priv="attach_modify" onClick="deal()">
						<img src="../Icons/icon003a4.gif" />解析</z:tbutton>
				</div>
				</td>
			</tr>
			<tr>
				<!-- <td style="padding:4px 10px;">列出： 从 <input type="text" id="StartDate" style="width:100px; " ztype='date'> 至 <input type="text" id="EndDate" style="width:100px; " ztype='date'> --> 
				&nbsp;附件名称: <input name="SearchName" type="text" id="SearchName" onFocus="delKeyWord();" value="请输入附件名称" style="width:110px"> <input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton"></td>
			</tr>
			<tr>
				<td style="padding:0px 5px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.CustomerServiceManage.dg1DataBind" size="15" lazy="true">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterDrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" ztype="selector" field="id">&nbsp;</td>
							<td width="18%"><b>附件名称</b></td>
							<td width="18%"><b>附件描述</b></td>
							<td width="11%"><b>创建者</b></td>
							<td width="10%"><b>上传时间</b></td>
							<td width="15%"><b>解析状态</b></td>
							<!-- <td width="5%"><b>下载</b></td> -->
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${FileName}</td>
							<td>${FileDesc}</td>
							<td>${AddUser}</td>
							<td>${ADDTIME}</td>
							<td>${useFlag}</td>
							<!-- <td><a href="<%=Config.getValue("ServerContext") %>/temp/${FilePath}" target="_blank"><img src="../Framework/Images/icon_down.gif" width="15" height="15" style="cursor:pointer;"/></a></td> -->
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
