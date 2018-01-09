<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>员工列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var cid = "0000";
var code = "1";

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 240;
	diag.Title = "新建员工";
	diag.URL = "Document/AddPersonnel.jsp";
	diag.onLoad = function(){
		$DW.$("user_id").focus();
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
	dc.add("level",code);
	Server.sendRequest("com.wisecode.test.Personnel.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
					$D.close();
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
		Server.sendRequest("com.wisecode.test.Personnel.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedValue("dg1");
	if(!dt||dt.length==0){
		Dialog.alert("请先选择要编辑的员工！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "编辑用户";
	diag.URL = "Document/EditPersonnel.jsp?user_id="+dt[0];
	diag.onLoad = function(){
		$DW.$("user_name").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	dc.add("organ_code",$DW.$NV("organ_code"))
	Server.sendRequest("com.wisecode.test.Personnel.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		})
	});
}

function onTreeClick(ele){
	cid = ele.getAttribute("cid");
	code = ele.getAttribute("innercode");
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

function doSearch(){
	var name = "";
	if ($V("SearchUserName") != "请输入用户名称") {
		name = $V("SearchUserName").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchUserName",name);
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
<input type="hidden" id="organ_id" value='<%=request.getParameter("organ_id") %>'>
<table width="100%" height="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="200">
		<table width="200" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
					style="height:500px;width:180px;"
					method="com.wisecode.test.Personnel.treeDataBind" level="3"
					lazy="true" resizeable="true">
					<p cid='${ID}' innercode='${organ_level}'
						onClick="onTreeClick(this);">${organ_name}</p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
						<z:datagrid id="dg1" method="com.wisecode.test.Personnel.datagrid" size="20">
				     <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
						
							<td width="10%"><strong>用户名称</strong></td>
							<td width="10%"><strong>所属机构</strong></td>
							<td width="15%"><strong>所属团队</strong></td>
							<td width="20%"><strong>用户岗位</strong></td>
							<td width="5%"><strong>性别</strong></td>
						   <td width="12%"><strong>用户电话</strong></td>	
							<td ><strong>用户地址</strong></td>
					
						</tr>
						<tr ondblclick="void(0)" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							
							<td>${user_name}</td>
							<td>${organ}</td>
							<td>${team}</td>
							<td>${post}</td>
							<td>${user_sex}</td>
							<td>${user_tel}</td>
							<td>${user_addr}</td>
							
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
