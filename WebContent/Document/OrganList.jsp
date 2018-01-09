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
var cid = "0000";
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 150;
	diag.Title = "新建机构";
	diag.URL = "Document/AddOrgan.jsp";
	diag.onLoad = function(){
		$DW.$("organ_code").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	dc.add("cid",cid);
	Server.sendRequest("com.wisecode.test.Organ.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
					$D.close();
					Tree.loadData("tree1");
					DataGrid.loadData('dg1');
			}	
		});
	});
}

function del(){
	var dt = DataGrid.getSelectedData("dg1");
	if(dt.Values.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("DT",dt);
	Dialog.confirm("您确认要删除吗？",function(){
		Server.sendRequest("com.wisecode.test.Organ.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					Tree.loadData("tree1");
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedValue("dg1");
	if(!dt||dt.length==0){
		Dialog.alert("请先选择要编辑的机构！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 380;
	diag.Title = "编辑机构";
	diag.URL = "Document/EditOrgan.jsp?Organ_code="+dt[0];
	diag.onLoad = function(){
		$DW.$("organ_name").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	Server.sendRequest("com.wisecode.test.Organ.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				Tree.loadData("tree1");
				DataGrid.loadData("dg1");
			}
		})
	});
}

function onTreeClick(ele){
	cid = ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("CatalogID",cid);
	try{
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.setParam("dg1","organlevel",code);
	DataGrid.setParam("dg1", Constant.PageIndex, 0);	
	DataGrid.loadData("dg1");
	
}catch(ex){alert(ex.message);}
}

function initButtons(ele){
	if(Tree.hasChild(ele)||Tree.isRoot(ele)){//有可能根节点下没有子节点
		if(Tree.isRoot(ele)){
			Misc.setButtonText("BtnPublish","发布站点首页");
			$("BtnPublish").onclick = publishIndex;
			$("BtnToPublish").disable();
			$("BtnAdd").disable();
			$("BtnEdit").disable();
			$("BtnPreview").enable();
			$("BtnMove").disable();
			$("BtnCopy").disable();
			$("BtnDel").disable();
			$("BtnOrder").disable();
			return false;
		}else{
			Misc.setButtonText("BtnPublish","发布");
			$("BtnPublish").onclick = publish;
			$("BtnToPublish").enable();
			$("BtnAdd").enable();
			$("BtnEdit").enable();
			$("BtnPreview").enable();
			$("BtnMove").enable();
			$("BtnCopy").enable();
			$("BtnDel").enable();
			$("BtnOrder").enable();	
			return true;
		}
	}else{
		Misc.setButtonText("BtnPublish","发布");
		$("BtnPublish").onclick = publish;
		if($V("Type")=="ARCHIVE"){
			$("BtnAdd").disable();
			$("BtnEdit").disable();
			$("BtnToPublish").disable();
			$("BtnPublish").disable();
			$("BtnCopy").disable();
			$("BtnMove").disable();
			$("BtnOrder").disable();
			$("BtnDel").disable();
		}else{
			$("BtnPublish").enable();
			$("BtnToPublish").enable();
			$("BtnAdd").enable();
			$("BtnEdit").enable();
			$("BtnPreview").enable();
			$("BtnMove").enable();
			$("BtnCopy").enable();
			$("BtnDel").enable();
			$("BtnOrder").enable();		
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
		$("BtnAdd").disable();
		$("BtnEdit").disable();
		$("BtnPreview").enable();
		$("BtnMove").disable();
		$("BtnCopy").disable();
		$("BtnDel").disable();
		$("BtnOrder").disable();
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

function afterTreeDragEnd(evt){
	var item = this;
	var catalogID = item.$A("cid");
	Dialog.alert(catalogID);
	var row = DragManager.DragSource.parentNode;
	var dc = new DataCollection();
	dc.add("ArticleIDs",$("dg1").DataSource.get(row.rowIndex-1,"ID"));
	dc.add("CatalogID",catalogID);
	Dialog.wait("请稍等...");
	Server.sendRequest("com.sinosoft.cms.document.ArticleList.move",dc,function(response){
		Dialog.endWait();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				item.onclick.apply(item);
			}
		});
	});
}

function showMenu(event,ele){
	$("dg1").onContextMenu = function(tr,evt){
		evt = getEvent(evt);
		var menu = new Menu();
		menu.Width = 150;
		menu.setEvent(evt);
		menu.setParam([]);
			
		menu.addItem("新建",add,"/Icons/icon022a2.gif");
		menu.addItem("保存",save,"/Icons/icon022a16.gif");
		menu.addItem("放弃",discard,"/Icons/icon400a8.gif");
		menu.addItem("删除",del,"/Icons/icon022a3.gif");
		
		menu.show();
	}
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
					method="com.wisecode.test.Organ.treeDataBind" level="3"
					lazy="true" resizeable="true">
					<p cid='${ID}' innercode='${ParentID}'
						onClick="onTreeClick(this);">${organ_name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left"><z:tbutton id="BtnAdd"
					priv="article_modify" onClick="add()">
					<img src="../Icons/icon003a2.gif" width="20" height="20" />新建</z:tbutton> <z:tbutton
					id="BtnEdit" priv="article_modify"
					onClick="edit();return false;">
					<img src="../Icons/icon003a4.gif" width="20" height="20" />编辑</z:tbutton> 
					<z:tbutton
					id="BtnToPublish" priv="article_modify" onClick="del()">
					<img src="../Icons/icon003a3.gif" width="20" height="20" />删除</z:tbutton></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
						<z:datagrid id="dg1" method="com.wisecode.test.Organ.datagrid" size="15">
				     <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" height="30" ztype="Selector" field="organ_code">&nbsp;</td>
							<td width="7%" sortfield="title" direction=""><b>机构代码</b></td>
							<td width="20%"><strong>机构名称</strong></td>
							<td width="45%"><strong>机构职责</strong></td>
							<td width="20%"><strong>上级机构</strong></td>
						</tr>
						<tr ondblclick="edit(this);" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td title="${organ_code}">${organ_code}</td>
							<td>${organ_name}</td>
							<td>${oragan_respons}</td>
							<td>${parent_name}</td>
						</tr><!-- 
						<tr ztype="edit" bgcolor="#E1F3FF">
								<td bgcolor="#E1F3FF">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${organ_code}</td>
								<td><input name="organ_name" type="text" id="organ_name"
									value="${organ_name}" size="20"></td>
								<td><input name="oragan_respons" type="text" id="oragan_respons"
									value="${oragan_respons}" size="20"></td>
								<td>${parent_name}</td>
							</tr> -->
						<tr ztype="pagebar">
							<td colspan="6" align="left">${PageBar}</td>
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
