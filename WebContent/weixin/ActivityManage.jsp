<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文档列表sadfasf</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var width  = (screen.availWidth-10)+"px";
	var height = (screen.availHeight-50)+"px";
	var leftm  = 0;
	var topm   = 0;
	
	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
	var url="Article.jsp?";
	var w = window.open(url,"",args);
	if ( !w ){
		Dialog.alert( "发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!" ) ;
		return ;
	}
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	
	Dialog.confirm("确认删除选中的文档吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		
		dc.add("PageIndex",DataGrid.getParam("dg1",Constant.PageIndex));
		dc.add("PageSize",DataGrid.getParam("dg1",Constant.Size));
		
		Server.sendRequest("com.sinosoft.cms.document.WxActive.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("成功删除文档");
				DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	      DataGrid.loadData("dg1");
			}
		});
	});
}
function online(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要上线的活动！");
		return;
	}
	Dialog.confirm("确认要上线活动吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		
		dc.add("PageIndex",DataGrid.getParam("dg1",Constant.PageIndex));
		dc.add("PageSize",DataGrid.getParam("dg1",Constant.Size));
		
		Server.sendRequest("com.sinosoft.cms.document.WxActive.onLine",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("活动上线成功");
				DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	      DataGrid.loadData("dg1");
			}
		});
	});
}
function unline(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要下线的活动！");
		return;
	}
	
	Dialog.confirm("确认要下线活动吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		
		dc.add("PageIndex",DataGrid.getParam("dg1",Constant.PageIndex));
		dc.add("PageSize",DataGrid.getParam("dg1",Constant.Size));
		
		Server.sendRequest("com.sinosoft.cms.document.WxActive.unLine",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("活动下线成功");
				DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	      DataGrid.loadData("dg1");
			}
		});
	});
}

function edit(id){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要编辑的文章！");
		return;
	}
	var id = arr[0];
	var width  = (screen.availWidth-10)+"px";
	var height = (screen.availHeight-50)+"px";
	var leftm  = 0;
	var topm   = 0;
	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
	var url="Article.jsp?ArticleID=" + id;
	var w = window.open(url,"",args);
	if(!w){
		Dialog.alert("发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!" ) ;
		return ;
	}
}


Page.onLoad(function(){
$("dg1").onContextMenu = function(tr,evt){
	evt = getEvent(evt);
	var menu = new Menu();
	menu.Width = 150;
	menu.setEvent(evt);
	menu.setParam([]);
	
	var arr = DataGrid.getSelectedValue("dg1");
	var flag = false;
	if(arr && arr.length>1){
		flag = true;
	}

	menu.addItem("新建",add,"Icons/icon003a2.gif");
	menu.addItem("编辑",edit,"Icons/icon003a4.gif",flag);
	menu.addItem("删除",del,"Icons/icon003a3.gif");

	menu.addItem("导出成Excel",function(){DataGrid.toExcel("dg1")},"Framework/Images/FileType/xls.gif");
	
	if(!flag){
		var dr = $("dg1").DataSource.Rows[tr.rowIndex-1];
		if(dr.get("Status")=="40"){
			menu.addItem("-");
			menu.addItem("上线",up,"Icons/icon026a7.gif");
		}else if(dr.get("Status")=="30"){
			menu.addItem("-");
			menu.addItem("下线",down,"Icons/icon026a5.gif");
		}
	}
	menu.show();
}
});

function changeType(){
   	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Type",$V("Type"));
	DataGrid.loadData("dg1");
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length<1){
		if($V("CatalogID")){
			window.open("../Site/Preview.jsp?Type=1&SiteID="+Cookie.get("SiteID")+"&CatalogID="+$V("CatalogID"));
		}else{
			window.open("../Site/Preview.jsp?Type=0&SiteID="+Cookie.get("SiteID"));
		}
	}else{
		window.open("Preview.jsp?ArticleID="+arr[0]);
	}	
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'Keyword'||ele.id == 'Submitbutton'){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}
function show(){
	var dt = DataGrid.getSelectedData("dg1");
	if(dt.getRowCount()==0 ){
		Dialog.alert("请先选择要预览的页面!");
		return;
	}
	dr = dt.getDataRow(0);
	var diag = new Dialog("Diag1");
	diag.Width = 1005;
	diag.Height = 700;
	diag.Title = "微信活动-预览" ;
	diag.URL = "/shop/wx_active!getActivePage.action?activeID="+dr.get("ID");
	diag.onLoad = function(){
		$DW.$("FactorName").focus();
	};
	diag.CancelEvent = CancelClose;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
}
function CancelClose(){
	$D.close();
	DataGrid.loadData("dg1");
}
</script>
</head>
<body oncontextmenu="return false;">
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left"><z:tbutton id="BtnAdd"
					priv="article_modify" onClick="add()">
					<img src="../Icons/icon003a2.gif" width="20" height="20" />新建</z:tbutton> <z:tbutton
					id="BtnEdit" priv="article_modify"
					onClick="DataGrid.edit(event,'dg1')">
					<img src="../Icons/icon003a4.gif" width="20" height="20" />编辑</z:tbutton> <z:tbutton
					id="BtnDel" priv="article_modify" onClick="del()">
					<img src="../Icons/icon003a3.gif" width="20" height="20" />删除</z:tbutton><z:tbutton
					id="BtnDel" priv="article_modify" onClick="online()">
					<img src="../Icons/icon003a3.gif" width="20" height="20" />上线</z:tbutton>
					<z:tbutton
					id="BtnDel" priv="article_modify" onClick="unline()">
					<img src="../Icons/icon003a3.gif" width="20" height="20" />下线</z:tbutton>
					<z:tbutton
					id="BtnDel" priv="article_modify" onClick="show()">
					<img src="../Icons/icon003a3.gif" width="20" height="20" />预览</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">列出: <z:select id="Type"
					onChange="changeType()" value="ALL" style="width:90px;">
					<select>
					<option value="ALL" selected="true">所有活动</option>
					<option value="1">已上线活动</option>
					<option value="0">已下线活动</option>
					</select>
		  </z:select> &nbsp;从 <input type="text" id="StartDate" style="width:90px; "
					ztype='date'> 至 <input type="text" id="EndDate"
					style="width:90px; " ztype='date'> &nbsp;<input
					type="button" name="Submitbutton" id="Submitbutton" value="查询"
					onClick="search()"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.document.WxActive.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="4%" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="56%" sortfield="orderflag" direction=""><b>标题</b></td>
							<td width="7%"><strong>创建者</strong></td>
							<td width="7%"><strong>状态</strong></td>
							<td width="15%" sortfield="publishdate" direction=""><strong>上线时间</strong></td>
							<td width="15%" sortfield="publishdate" direction=""><strong>下线时间</strong></td>
						</tr>
						<tr onDblClick="edit(${ID});">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Title}</td> 
							<td>${Author}</td>
							<td>${ActiveStatus}</td>
							<td>${CreateDate}</td>
							<td>${ModifyDate}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="9" align="left">${PageBar}</td>
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
