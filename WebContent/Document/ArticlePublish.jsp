<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文档列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function publish(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.confirm("您没有选择要发布的文章，确认要发布全部查询结果吗？",function(){
			toPublish();
		});	
		return;
	} else {
		toPublish();
	}
}

function cacheClear(){
 
	Server.sendRequest("com.sinosoft.cms.document.ArticlePublish.clear",null,function(response){
		Dialog.alert(response.Message);
	});
	
}


function toPublish(){
	var dc = new DataCollection();
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr != null && arr.length != 0){
		dc.add("IDs",arr.join());
	}
	dc.add("Type",$V("Type"));
	dc.add("CatalogID",$V("CatalogID"));
	dc.add("Keyword",$V("Keyword"));
	dc.add("StartDate",$V("StartDate"));
	dc.add("EndDate",$V("EndDate"));

	Server.sendRequest("com.sinosoft.cms.document.ArticlePublish.syncPublish",dc,function(response){
		var taskID = response.get("SignArticleTaskID");
		var p = new Progress(taskID, "正在发布...");
		p.show();
	});
	
}

function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Type",$V("Type"));
	DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
	DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}

function onTreeClick(ele){
	var cid = ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("CatalogID",cid);
}
 
</script>
</head>
<body>
<input type="hidden" id="CatalogID">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px; ">
	<tr valign="top" >
		<td width="180">
			<table width="180" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
						style="height:440px;width:160px;"
						method="com.sinosoft.cms.document.ArticleList.treeDataBind" level="2"
						lazy="true" resizeable="true">
						<p cid='${ID}' innercode='${InnerCode}'
							onClick="onTreeClick(this);" afterdrag='afterTreeDragEnd'
							oncontextmenu="showMenu(event,this);stopEvent(event);">${Name}</p>
					</z:tree></td>
				</tr>
			</table>
		</td>
		<td>
		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable" >
		 	<tr>
		 		<td width="65%"   >
		 			&nbsp;
				</td>
		 	</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">
					列出: 
					<z:select id="Type"  value="ALL" style="width:90px;">
						<select>
							<option value="ALL" selected="true">所有文档</option>
							<option value="ONPUBLISH">发布中</option>
							<option value="TOPUBLISH">待发布</option>
							<option value="PUBLISHED">已发布</option>
							<option value="EDITING">重新编辑</option>
						</select>
		  			</z:select> 
		  			&nbsp;从 <input type="text" id="StartDate" style="width:90px; " ztype='date'> 
		  			              至 <input type="text" id="EndDate" style="width:90px; " ztype='date'> 
		  		    &nbsp;关键词: <input name="Keyword" type="text" id="Keyword">
						 <input type="button" name="searchButton" id="searchButton" value="查询" onClick="search()">
						 <input type="button" name="publishButton" id="publishButton" value="发布" onClick="publish()">
						 <input type="button" name="ClearButton" id="ClearButton" value="模版缓存清空" onClick="cacheClear()">
					</div>
				</td>
			</tr>
			<tr>
				<td style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.document.ArticleList.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" afterdrag="afterRowDragEnd">
					    <tr ztype="head" class="dataTableHead">
							<td width="3%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="3%"><strong>属性</strong></td>
							<td width="40%" sortfield="title" direction=""><b>标题</b></td>
							<td width="4%"> 地址</td>
							<td width="7%"><strong>创建者</strong></td>
							<td width="6%"><strong>状态</strong></td>
							<td width="22%"><strong>所属栏目</strong></td>
							<td width="13%" sortfield="publishdate" direction=""><strong>发布时间</strong></td>
						</tr>
						<tr  >
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td algin="center">${Icon}</td>
							<td style="${TitleStyle}"><span title="${Title}">${Title}</span></td>
							<td><a href="${HeadURL}${URL}" target="_blank" title="${HeadURL}${URL}"><img src="../Icons/icon403a3.gif" width="20" height="20" /></a></td>
							<td>${Author}</td>
							<td>${StatusName}</td>
							<td title="${LanMu}">${LanMu}</td>
							<td title="${PublishDate}">${PublishDate}</td>
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
</body>
</html>
