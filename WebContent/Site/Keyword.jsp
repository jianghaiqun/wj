<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.cms.site.Keyword.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var selectedCID = "";//被选中关键词分类的ID

/*Page.onLoad(function(){
	$("dg1").beforeEdit = function(tr,dr){
	    $S("LinkTarget",$V("hLinkTarget"));
	}
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Keyword",$V("Keyword"));
		dr.set("LinkURL",$V("LinkURL"));
		dr.set("LinkAlt",$V("LinkAlt"));
		dr.set("Prop1",$V("Prop1"));
		dr.set("LinkTarget",$V("LinkTarget"));
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.sinosoft.cms.site.Keyword.dg1Edit",function(){		
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
	});
}*/

function addType(){
	var diag = new Dialog("Diag1");
	diag.Width = 320;
	diag.Height = 70;
	diag.Title = "新建关键词分类";
	diag.URL = "Site/KeywordTypeDialog.jsp";
	diag.OKEvent = addTypeSave;
  	diag.show();
}

function addTypeSave(){
	var dc = new DataCollection();
	dc.add("TypeName",$DW.$V("TypeName"));
	
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.site.KeywordType.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("新建分类成功",function(){
			    $D.close();
				Tree.loadData("tree1",function(){
					Tree.select("tree1","cid",Cookie.get("DocList.LastCatalog"));
				});
			});
		}
	});
}

function editType(param) {
	var cmsg=param;
	var cid =cmsg[0];
	if(!cid){
		Dialog.alert("请先选择一个分类！");
		return ;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 320;
	diag.Height = 70;
	diag.Title = "修改关键词分类";
	diag.URL = "Site/KeywordTypeDialog.jsp?id="+cid;
	diag.OKEvent = editTypeSave;
  	diag.show();
}

function editTypeSave(){
	var dc = new DataCollection();
	dc.add("ID",$DW.$V("cid"));
	dc.add("TypeName",$DW.$V("TypeName"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.site.KeywordType.edit",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("修改成功",function(){
			    $D.close();
				Tree.loadData("tree1",function(){
					//Tree.select("tree1","cid",Cookie.get("DocList.LastCatalog"));
				});
			});
		}
	});
}

function delType(param) {
	var cmsg = param;
	var cid = cmsg[0];
	var cname = cmsg[1];
	if(!cid) {
		Dialog.alert("请先选择一个分类！");
		return ;
	}
	Dialog.confirm("确认删除 \""+cname+"\"</font> 分类吗？<br><font style='color:#F00'>确认将移除该分类下所有关键词!</font>",function() {
		var dc = new DataCollection();
		dc.add("ID", cid);
		
		Server.sendRequest("com.sinosoft.cms.site.KeywordType.del",dc,function(response){
			if(response.Status == 0) {
				Dialog.alert(response.Message);
			} else {
				Dialog.alert("删除分类成功",function() {
					Tree.loadData("tree1",function() {
						DataGrid.setParam("dg1",Constant.PageIndex,0);
						DataGrid.loadData("dg1");
					});
				});
			}
		});
	})
}

function onTreeClick(ele){
	var cid = ele.getAttribute("cid");
	selectedCID = cid;
	DataGrid.setParam("dg1","id",cid);
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	var cname = ele.getAttribute("cname");
	var cmsg=[cid,cname];
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cmsg);
	menu.addItem("新建",addType,"/Icons/icon018a2.gif");
	menu.addItem("修改",editType,"/Icons/icon018a4.gif");
	menu.addItem("删除",delType,"/Icons/icon018a3.gif");
	menu.show();
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 850;
	diag.Height = 300;
	diag.Title = "新建关键词";
	diag.URL = "Site/KeywordDialog.jsp?selectedCID="+selectedCID;
	diag.onLoad = function(){
		$DW.$("Keyword").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建关键词";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	var arr = $DW.$NV("keywordType");
	if(arr == null || arr == "") {
		Dialog.alert("请选择内链分类");
		return;
	}
	dc.add("selectdTypes",arr);
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.site.Keyword.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function edit(){
  	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的信息！");
		return;
	}else if(drs.length>1){
		Dialog.alert("一次只能修改一条信息！");
		return;
	}
	dr = drs[0]; 
  editDialog(dr);
}

function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 850;
	diag.Height = 300;
	diag.Title = "修改关键词";
	diag.URL = "Site/KeywordDialog.jsp?ID="+dr.get("ID");
	diag.onLoad = function(){
		$DW.$("Keyword").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改关键词";
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	var arr = $DW.$NV("keywordType");
	if(arr == null || arr == "") {
		Dialog.alert("请选择内链分类");
		return;
	}
	dc.add("selectdTypes",arr);
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.site.Keyword.edit",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var message = "";
	if(selectedCID == "" || selectedCID == null) {
		message = "<font color='red'>注：此操作将彻底删除您所选中的关键词！（从所有分类）</font><br/>&nbsp;确认要从所有分类中移除吗？";
	} else {
		message = "您确认要从该分类中移除吗？";
	}
	Dialog.confirm(message,function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			dc.add("selectedCID",selectedCID);
			Server.sendRequest("com.sinosoft.cms.site.Keyword.del",dc,function(response){
				if(response.Status==0){
					Dialog.alert(response.Message);
				}else{
					Dialog.alert("删除关键词成功");
					DataGrid.setParam("dg1",Constant.PageIndex,0);
					DataGrid.loadData("dg1");
				}
			});
	});
}

function importWord(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 480;
	diag.Title = "批量导入关键词";
	diag.URL = "Site/KeyWordImport.jsp";
	diag.OKEvent = importSave;
	diag.show();
}

function importSearchCode(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 480;
	diag.Title = "批量导入搜索量";
	diag.URL = "Site/SearchCodeImport.jsp";
	diag.OKEvent = importSave;
	diag.show();
}

function importSave(){
	$DW.importWords();
}

function exportWord(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要导出的行！");
		return;
	}
	var ids;
	if(!arr||arr.length==0){
		ids = "";
	}else{
		ids = arr.join(",");	
	}
	window.location = "KeyWordExport.jsp?ids="+ids+"&selectedCID="+selectedCID;
}

function loadWordData(){
	DataGrid.loadData("dg1");	
}

function loadTreeData(){
	Tree.loadData("tree1",function(){Tree.select("tree1","cid",selectedCID)});	
}

function doSearch(){
	if(Verify.hasError()){
		return;
	}
	
	DataGrid.setParam("dg1","Word",$V(Word));
	DataGrid.setParam("dg1","Link",$V(Link));
	DataGrid.setParam("dg1","Category",$V("Category"));
	DataGrid.setParam("dg1","Employ",$V("Employ"));
	DataGrid.setParam("dg1","LinkFlag",$V("LinkFlag"));
	DataGrid.setParam("dg1","SearchCodeStart",$V(SearchCodeStart));
	DataGrid.setParam("dg1","SearchCodeEnd",$V(SearchCodeEnd));
	DataGrid.setParam("dg1","PriorityLevelStart",$V(PriorityLevelStart));
	DataGrid.setParam("dg1","PriorityLevelEnd",$V(PriorityLevelEnd));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

function delKeyWord() {
	var keyWord = $V("Word");
	if (keyWord == "请输入关键词") {
		$S("Word","");
	}
}

function addKeyWord() {
	var keyWord = $V("Word");
	if (keyWord == "") {
		$S("Word","请输入关键词");
	}
}

function afterTreeDragEnd(evt){
	var item = this;
	var typeID = item.$A("cid");
	var row = DragManager.DragSource.parentNode;
	var dc = new DataCollection();
	dc.add("KeywordIDs",$("dg1").DataSource.get(row.rowIndex-1,"ID"));
	dc.add("TypeID",typeID);
	Server.sendRequest("com.sinosoft.cms.site.Keyword.move",dc,function(response){
		Dialog.alert(response.Message,function(){
			DataGrid.setParam("dg1",Constant.PageIndex,0);
			DataGrid.loadData("dg1");
		});
	});
}

</script>
	</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td width="140">
			<table width="140" oncontextmenu="return false;" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
						style="height:443px;width:130px;"
						method="com.sinosoft.cms.site.Keyword.treeDataBind" level="2"
						lazy="true">
						<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}'
						   cname='${TypeName}' onClick="onTreeClick(this);" afterdrag='afterTreeDragEnd'
						   oncontextmenu="showMenu(event,this);">${TypeName}</p>
					</z:tree></td>
				</tr>
			</table>
			</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd">
						<img src="../Icons/icon048a1.gif" /> 词库列表
					</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
						<z:tbutton onClick="addType()"><img src="../Icons/icon048a2.gif" />新建分类</z:tbutton>
						<z:tbutton onClick="add()"><img src="../Icons/icon048a2.gif" />新建</z:tbutton>
						<!--<z:tbutton onClick="save()"><img src="../Icons/icon048a16.gif" />保存</z:tbutton>-->
						<z:tbutton onClick="edit()"><img src="../Icons/icon048a16.gif" />编辑</z:tbutton>
						<z:tbutton onClick="del()"><img src="../Icons/icon048a3.gif" />删除</z:tbutton>
						<z:tbutton onClick="importWord()"><img src="../Icons/icon048a8.gif" />关键词导入</z:tbutton>
						<z:tbutton onClick="importSearchCode()"><img src="../Icons/icon048a8.gif" />搜索量导入</z:tbutton>
					</td>
				</tr>
				
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float">
						<div style="float: left white-space: nowrap;">
						关键词：<input name="Word" type="text" id="Word" style="width:110px"> 
						&nbsp;&nbsp;着陆页：<input name="Link" type="text" id="Link" style="width:110px">
						&nbsp;&nbsp;所属分类：<z:select id="Category" name="Category" style="width:100px">${belongCategoryInit}</z:select>
						&nbsp;&nbsp;是否收录：<z:select id="Employ" name="Employ" style="width:50px">${employFlagInit}</z:select>
						&nbsp;&nbsp;是否有着陆页：<z:select id="LinkFlag" name="LinkFlag" style="width:50px">${employFlagInit}</z:select>
						</div>
						</span>
					</td>
				</tr>
				
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float">
						<div style="float: left white-space: nowrap;">
						搜索量：从 
						<input value="" type="text" id="SearchCodeStart" 
							name="SearchCodeStart" ztype="String"  class="inputText" size="6" Verify="Int">
						到 <input value="" type="text" id="SearchCodeEnd" 
							name="SearchCodeEnd" ztype="String"  class="inputText" size="6" Verify="Int">
						&nbsp;&nbsp;优先级：从 
						<input value="" type="text" id="PriorityLevelStart" 
							name="PriorityLevelStart" ztype="String"  class="inputText" size="6" Verify="Int">
						到 <input value="" type="text" id="PriorityLevelEnd" 
							name="PriorityLevelEnd" ztype="String"  class="inputText" size="6" Verify="Int">
						&nbsp;&nbsp;<input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton">
						</div>
						</span>
					</td>
				</tr>
				
				<tr>
					<td
						style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.site.Keyword.dg1DataBind" size="12">
						<table width="100%" cellpadding="2" cellspacing="0"
							   class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
								<td width="5%" ztype="selector" field="id">&nbsp;</td>
								<td width="20%" onclick="DataGrid.onSort(this);" onmouseout="DataGrid.onSortHeadMouseOut(this);" onmouseover="DataGrid.onSortHeadMouseOver(this);" style="cursor:pointer" sortfield="Keyword">关键词</td>
								<td width="5%" onclick="DataGrid.onSort(this);" onmouseout="DataGrid.onSortHeadMouseOut(this);" onmouseover="DataGrid.onSortHeadMouseOver(this);" style="cursor:pointer" sortfield="SearchCode">搜索值</td>
								<td width="30%" >着陆页</td>
								<td width="5%" onmouseout="DataGrid.onSortHeadMouseOut(this);" onmouseover="DataGrid.onSortHeadMouseOver(this);" style="cursor:pointer" sortfield="PriorityLevel">优先级</td>
								<td width="6%" onmouseout="DataGrid.onSortHeadMouseOut(this);" onmouseover="DataGrid.onSortHeadMouseOver(this);" style="cursor:pointer" sortfield="EmployFlag">是否收录</td>
								<td width="10%" onmouseout="DataGrid.onSortHeadMouseOut(this);" onmouseover="DataGrid.onSortHeadMouseOver(this);" style="cursor:pointer" sortfield="BelongCategory">所属分类</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="5%">&nbsp;</td>
								<td width="5%">&nbsp;</td>
								<td>${Keyword}</td>
								<td>${SearchCode}</td>
								<td>${LinkURL}</td>
								<td>${PriorityLevel}</td>
								<td style="width:50px;">${EmployFlag}</td>
								<td style="width:100px;">${BelongCategory}</td>
								<td>
							</tr>

							<tr ztype="pagebar">
								<td colspan="8" align="center">${PageBar}</td>
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
</z:init>
