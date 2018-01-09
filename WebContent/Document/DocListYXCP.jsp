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
function FAQ(){
	var diag = new Dialog("Diag1");
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要编辑的文章！");
		return;
	}if(arr.length>1){
		Dialog.alert("只能设置一条常见问题！");
		return;
	}
	var id = arr[0];
	var diag = new Dialog("Diag1");
	diag.Width = 680;
	diag.Height = 350;
	diag.Title = "产品列表";
	diag.URL = "Document/DocListDialogYXCP.jsp";
	diag.onLoad = function(){
		$DW.setArticleId(id);
	};
	//$E.hide(diag.CancelButton);
	//diag.CancelButton.value = "确 定";
	diag.OKEvent = FaqSave;
	diag.show();
}

function FaqSave(){
	$D.close();
	DataGrid.loadData("dg1");
}

function publishCatalog(){
	var diag = new Dialog("Diag1");
	var nodeType = $V("CatalogID")=="" ? "0":"1";
	diag.Width = 340;
	diag.Height = 150;
	diag.Title = "发布";
	diag.URL = "Site/CatalogGenerateDialog.jsp";
	diag.onLoad = function(){
		if(nodeType == "0"){
			$DW.$("tr_Flag").style.display="none";
			
		}
	};
	diag.OKEvent = publishCatalogSave;
	diag.show();
}

function publishCatalogSave(){
	$E.disable($D.OKButton);
	$E.disable($D.CancelButton);
	$E.show($DW.$("Message"));
	$DW.msg();
	var dc = $DW.Form.getData("form2");
	var nodeType = $V("CatalogID")=="" ? "0":"1";
	dc.add("type",nodeType);
	dc.add("CatalogID",Tree.CurrentItem.getAttribute("cid"));
	Server.sendRequest("com.sinosoft.cms.site.CatalogCJWT.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
			$D.close();
		}else{
			$D.close();
			var p = new Progress(response.get("TaskID"),"正在生成静态文件...");
			p.show();
		}
	});
}

function publishIndex(){
	var diag = new Dialog("Diag1");
	diag.Width = 340;
	diag.Height = 150;
	diag.Title = "发布";
	diag.URL = "Site/CatalogGenerateDialog.jsp";
	diag.onLoad = function(){
		$DW.$("tr_Flag").style.display="none";
	};
	diag.OKEvent = publishIndexSave;
	diag.show();
}

function publishIndexSave(){
	$E.disable($D.OKButton);
	$E.disable($D.CancelButton);
	$E.show($DW.$("Message"));
	$DW.msg();
  var dc = new DataCollection();
	Server.sendRequest("com.sinosoft.cms.site.CatalogSiteCJWT.publishIndex",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
			$D.close();
		}else{
			$D.close();
			var taskID = response.get("TaskID");
			var p = new Progress(taskID,"发布首页...");
			p.show();
		}
	});
}
function down(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择文档！");
		return;
	}
	var dc = new DataCollection();
	dc.add("ArticleIDs",arr.join(","));
	Server.sendRequest("com.sinosoft.cms.document.ArticleListCJWT.down",dc,function(response){
		if(response.Status==1){
			Dialog.alert("操作成功!",function(){
				DataGrid.loadData("dg1");
			});
		} else {
			Dialog.alert(response.Message);
		}																			
	});
}
function onlyPreview(){
	if($V("Type")=="ARCHIVE"){
		$("BtnPublish").disable();
	}else{
		initButtons(Tree.CurrentItem);
	}
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length<1){
		if($V("CatalogID")&&$V("CatalogID")!="0"){
			window.open("../Site/Preview.jsp?Type=1&SiteID="+Cookie.get("SiteID")+"&CatalogID="+$V("CatalogID"));
		}else{
			window.open("../Site/Preview.jsp?Type=0&SiteID="+Cookie.get("SiteID"));
		}
	}else{
		window.open("Preview.jsp?ArticleID="+arr[0]);
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
			$("BtnPublish").onclick = publishIndex;
			$("BtnPreview").enable();
			return false;
		}else{
			Misc.setButtonText("BtnPublish","发布");
			$("BtnPublish").onclick = publish;
			$("BtnPreview").enable();
			return true;
		}
	}else{
		Misc.setButtonText("BtnPublish","发布");
		$("BtnPublish").onclick = publish;
		if($V("Type")=="ARCHIVE"){
			$("BtnPublish").disable();
		}else{
			$("BtnPublish").enable();
			$("BtnPreview").enable();
		}
		return true;
	}
}

Page.onReady(init);

function init(){
	if(Cookie.get("DocList.LastCatalog")=="0"){
		Tree.CurrentItem = $("tree1").$T("p")[0];
		Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
		Misc.setButtonText("BtnPublish","发布站点首页");
		$("BtnPublish").onclick = publishIndex;
		$("BtnPreview").enable();
	}else{
		var node = Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
		Tree.selectNode(node,true);
		Tree.scrollToNode(node);	
		$S("CatalogID",Cookie.get("DocList.LastCatalog"));
		initButtons(Tree.CurrentItem);
		Application.setAllPriv("article",Tree.CurrentItem.getAttribute("innerCode"));
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
	DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	if(DataGrid.getParam("dg1",Constant.SortString)){
		Dialog.confirm("默认排序下才可能调整顺序，是否要先切换到默认排序？",function(){
			DataGrid.setParam("dg1",Constant.SortString,"");
			DataGrid.loadData("dg1",function(){
				Dialog.alert("切换成功!");
			});
		});
		return;
	}
	var order = sourceDr.get("OrderFlag");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg1").DataSource;
	dc.add("TopFlag","false");
	var i = rowIndex-1;
	if(i!=0){
		target = targetDr.get("OrderFlag");
		dc.add("Type","After");
		if(rowIndex<ds.getRowCount()){
			var topFlag = targetDr.get("TopFlag");
			if(topFlag=="1"){
				dc.add("TopFlag","true");
			}
		}		
	}else{
		dc.add("Type","Before");
		target = $("dg1").DataSource.get(1,"OrderFlag");
		var topFlag = ds.get(1,"TopFlag");
		if(topFlag=="1"){
			dc.add("TopFlag","true");
		}
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	dc.add("CatalogID",$V("CatalogID"));
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.sinosoft.cms.document.ArticleListCJWT.sortArticle",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});	
}

function afterTreeDragEnd(evt){
	var item = this;
	var catalogID = item.$A("cid");
	var row = DragManager.DragSource.parentNode;
	var dc = new DataCollection();
	dc.add("ArticleIDs",$("dg1").DataSource.get(row.rowIndex-1,"ID"));
	dc.add("CatalogID",catalogID);
	Dialog.wait("请稍等...");
	Server.sendRequest("com.sinosoft.cms.document.ArticleListCJWT.move",dc,function(response){
		Dialog.endWait();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				item.onclick.apply(item);
			}
		});
	});
}

function changeType(){
   	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	DataGrid.setParam("dg1","Type",$V("Type"));
	onlyPreview();
	DataGrid.loadData("dg1");
}

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	if(!cid){
		return ;
	}
	Tree.selectNode(ele,true);
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cid);
	menu.addItem("预览栏目",function(){window.open("../Site/Preview.jsp?Type=1&SiteID="+Cookie.get("SiteID")+"&CatalogID="+$V("CatalogID"));},"Icons/icon403a3.gif");
	if(Application.getPriv("article",ele.getAttribute("innercode"),"article_modify")) {
		menu.addItem("发布栏目",publishCatalog,"/Icons/icon003a13.gif");
		menu.addItem("导出成Excel",function(){DataGrid.toExcel("dg1")},"Framework/Images/FileType/xls.gif");
	}
	menu.show();
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
					method="com.sinosoft.cms.document.ArticleListYXCP.treeDataBind" level="2"
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
					<img src="../Icons/icon003a13.gif" width="20" height="20" />发布</z:tbutton> <z:tbutton
					id="BtnPreview" priv="article_browse" onClick="preview()">
					<img src="../Icons/icon403a3.gif" width="20" height="20" />预览</z:tbutton> <z:tbutton
					id="BtnFAQId" priv="article_browse" onClick="FAQ()">
					<img src="../Icons/icon003a2.gif" width="20" height="20" />设置产品顺序</z:tbutton></div>
					
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
							<td width="7%"><strong>创建者</strong></td>
							<td width="17%"><strong>状态</strong></td>
							<td width="15%" sortfield="publishdate" direction=""><strong>发布时间</strong></td>
						</tr>
						<tr onDblClick="FAQ(${ID});">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td algin="center">${Icon}</td>
							<td style="${TitleStyle}"><span title="${Title}">${Title}</span></td>
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
