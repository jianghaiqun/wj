<%
	/* WAP站竞价页画面
	 ************************************************************** 
	 *  程序名 : WapSiteBidPage.jsp
	 *  建立日期:2015/06/02
	 *  作者   :  wangwenying
	 *  模块   :  CMS
	 *  描述   :  批量导入问答
	 *  备注   :  
	 * ------------------------------------------------------------ 
	 *  修改历史 
	 *  序号   日期   修改人     修改原因 
	 * 1 
	 * 2 
	 ************************************************************** 
	 */
%>
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
Page.onReady(init);

function init(){
	if(Cookie.get("DocList.LastCatalog")=="0"){
		Tree.CurrentItem = $("tree1").$T("p")[0];
		Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
		Misc.setButtonText("BtnPublish","发布站点首页");
		//$("BtnPublish").onclick = publishIndex;
	}else{
		var node = Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
		Tree.selectNode(node,true);
		Tree.scrollToNode(node);	
		$S("CatalogID",Cookie.get("DocList.LastCatalog"));
		initButtons(Tree.CurrentItem);
		Application.setAllPriv("article",Tree.CurrentItem.getAttribute("innerCode"));
	}
}

function onTreeClick(ele){
	var cid = ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("CatalogID",cid);
	try{
	if(!Tree.isRoot(ele)){
		Cookie.set("DocList.LastCatalog",cid,"2100-01-01");
		Cookie.set("DocList.LastCatalogCode",code,"2100-01-01");
	}else{
		Cookie.set("DocList.LastCatalog","0","2100-01-01");
	}
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.setParam("dg1","StartDate","");//栏目切换时需清掉时间和关键字选项
	DataGrid.setParam("dg1","EndDate","");
	DataGrid.setParam("dg1","Keyword","");
	DataGrid.setParam("dg1", Constant.PageIndex, 0);	
	$S("StartDate","");
	$S("EndDate","");
	$S("Keyword","");
	DataGrid.loadData("dg1");
	if(!initButtons(ele)){
		return;
	}
	Application.setAllPriv("article",code);
	if($V("Type")=="ARCHIVE"){
		$("BtnPublish").disable();
	}
}catch(ex){alert(ex.message);}
}

function initButtons(ele){
	if(Tree.hasChild(ele)||Tree.isRoot(ele)){//有可能根节点下没有子节点
		if(Tree.isRoot(ele)){
			Misc.setButtonText("BtnPublish","发布站点首页");
			//$("BtnPublish").onclick = publishIndex;
			//$("BtnPreview").enable();
			return false;
		}else{
			Misc.setButtonText("BtnPublish","发布");
			$("BtnPublish").onclick = publish;
			//$("BtnPreview").enable();
			return true;
		}
	}else{
		Misc.setButtonText("BtnPublish","发布");
		$("BtnPublish").onclick = publish;
		if($V("Type")=="ARCHIVE"){
			$("BtnPublish").disable();
		}else{
			$("BtnPublish").enable();
			//$("BtnPreview").enable();
		}
		return true;
	}
}

// 产品编辑
function productEditor() {
	var arrs = DataGrid.getSelectedData("dg1");
	var drs = arrs.Rows;
	var dr = drs[0];
	if (!drs || drs.length == 0) {
		Dialog.alert("请选择一条进行编辑 ！");
		return;
	}
	if (!drs || drs.length >= 2) {
		Dialog.alert("只能选择一条进行编辑！");
		return;
	}

	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 500;
	diag.Title = "标题菜单设置";
	diag.URL = "Document/WapBidProductMenu.jsp?DocumentId=" + dr.get("id");
	diag.CancelEvent = CancelClose;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "取  消";
}

function CancelClose(){
	$D.close();
}

function publish(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要发布的文章！");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.sinosoft.cms.document.ArticleListCJWT.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			var p = new Progress(response.get("TaskID"),"正在发布...");
			p.show(function(){
				$("dg1").loadData();
			});
		}
	});
}

</script>
</head>
<body>
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
					style="height:440px;width:160px;"
					method="com.sinosoft.cms.document.WapSiteBidPageManage.treeDataBind" level="2"
					lazy="true" resizeable="true">
					<p cid='${ID}' innercode='${InnerCode}'
						onClick="onTreeClick(this);" afterdrag='afterTreeDragEnd'
						oncontextmenu="showMenu(event,this);stopEvent(event);">${Name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left"><z:tbutton
					id="BtnPublish" priv="article_modify" onClick="publish()">
					<img src="../Icons/icon003a13.gif" width="20" height="20" />发布</z:tbutton><z:tbutton
					id="BtnFAQId" priv="article_browse" onClick="productEditor()">
					<img src="../Icons/icon003a2.gif" width="20" height="20" />产品内容编辑</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">列出: <z:select id="Type"
					onChange="changeType()" value="ALL" style="width:90px;">
					<select>
					<option value="ALL" selected="true">所有文档</option>
					<option value="ADD">我创建的文档</option>
					<option value="WORKFLOW">流转中的文档</option>
					<option value="TOPUBLISH">待发布的文档</option>
					<option value="PUBLISHED">已发布的文档</option>
					<option value="OFFLINE">已下线的文档</option>
					<option value="ARCHIVE">已归档的文档</option>
                    <option value="Editer">所有编辑投稿</option>
                    <option value="Member">所有会员投稿</option>
					</select>
		  </z:select> &nbsp;从 <input type="text" id="StartDate" style="width:90px; "
					ztype='date'> 至 <input type="text" id="EndDate"
					style="width:90px; " ztype='date'> &nbsp;关键词: <input
					name="Keyword" type="text" id="Keyword"> <input
					type="button" name="Submitbutton" id="Submitbutton" value="查询"
					onClick="search()"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.document.ArticleListCJWT.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="5%"><strong>属性</strong></td>
							<td width="48%" sortfield="title" direction=""><b>标题</b></td>
							<td width="4%"> 地址</td>
							<td width="7%"><strong>创建者</strong></td>
							<td width="17%"><strong>状态</strong></td>
							<td width="15%" sortfield="publishdate" direction=""><strong>发布时间</strong></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td algin="center">${Icon}</td>
							<td style="${TitleStyle}"><span title="${Title}">${Title}</span></td>
							<td><a href="${HeadURL}${URL}" target="_blank" title="${HeadURL}${URL}"><img src="../Icons/icon403a3.gif" width="20" height="20" /></a></td>
							 <td>${Author}</td>
							 
							<td>${StatusName}</td>
							<td title="${PublishDate}">${PublishDate}</td>
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
