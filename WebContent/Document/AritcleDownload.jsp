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
	function changeType() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "Type", $V("Type"));
		DataGrid.loadData("dg1");
	}

	function search() {
		if (!$NV("Catalog")) {
			Dialog.alert("没有选择任何栏目!");
			return;
		}
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		var arr = $NV("Catalog");
		for ( var i = 0; i < arr.length; i++) {
			var id = arr[i];
		}
		DataGrid.setParam("dg1", "Catalogs", $NV("Catalog"));
		DataGrid.setParam("dg1", "StartDate", $V("StartDate"));
		DataGrid.setParam("dg1", "EndDate", $V("EndDate"));
		DataGrid.setParam("dg1", "aothor", $V("aothor"));
		DataGrid.loadData("dg1");
	}
	function onTypeChange() {
		loadTreeData();
	}
	function onCheck(ele) {
		ele = $(ele);
		var checked = ele.checked;
		var p = ele.getParent("P");
		var level = p.$A("level");
		var arr = $("tree1").$T("P");
		var flag = true;
		for ( var i = 0; i < arr.length; i++) {
			var c = arr[i];
			var cid = c.$A("cid");
			if (cid) {
				if (cid != "-1" && ele.value == "-1") {
					if (checked) {
						$("Catalog_" + cid).disable();
					} else {
						$("Catalog_" + cid).enable();
					}
				} else {
					if (c != p && flag) {
						continue;
					}
					if (c == p) {
						flag = false;
						continue;
					}
					if (c.$A("level") > level) {
						$("Catalog_" + cid).checked = checked;
					} else {
						break;
					}
				}
			}
		}
	}

	function loadTreeData() {
		Tree.setParam("tree1", "Type", $NV("Type"));
		Tree.loadData("tree1", function() {
			if (window.RelaStr) {
				var rela = "," + window.RelaStr + ",";
				var arr = $N("Catalog");
				;
				for ( var i = 0; i < arr.length; i++) {
					if (rela.indexOf("," + arr[i].value + ",") >= 0) {
						arr[i].checked = true;
					}
				}
				if (rela.indexOf(",-1,") >= 0) {
					onCheck(arr[0]);
				}
			}
		});
	}
	
	function edit(id){
		 
	}
	
	function Export() {
		DataGrid.selectedRowToExcel("dg1");

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
				<td style="padding: 6px;" class="blockTd"><z:tree id="tree1"
					style="height:440px;width:160px;"
					method="com.sinosoft.cms.document.ArticleList.treeDataBind"
					level="2" lazy="true" resizeable="true">
					<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
					<p cid='${ID}' level="${Level}"><input type="checkbox"
						name="Catalog" id='Catalog_${ID}' value='${ID}'
						onClick="onCheck(this);"><label for="Catalog_${ID}"><span id="span_${ID}">${Name}</span></label></p>
				</z:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding: 6px 10px;" class="blockTd">
				</td>
			</tr>
			<tr>
				<td style="padding: 2px 10px;">
				<div style="float: left;">列出: <z:select id="Type"
					onChange="changeType()" value="ALL" style="width:90px;">
					<select>
						<option value="PUBLISHED" selected="true">已发布的文档</option>
						<option value="TOPUBLISH">待发布的文档</option>
						<option value="ADD">全部</option>
						<option value="OFFLINE">已下线的文档</option>
						<option value="ARCHIVE">已归档的文档</option>
					</select>
				</z:select> &nbsp;从 <input type="text" id="StartDate" style="width: 90px;"
					ztype='date'> 至 <input type="text" id="EndDate"
					style="width: 90px;" ztype='date'>
					创建者<input type="text" id="aothor"/>
					 <input type="button"
					name="Submitbutton" id="Submitbutton" value="查询" onClick="search();">
				<input type="button" name="Submitbutton" id="Submitbutton"
					value="导出" onClick="Export();"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
				<z:datagrid id="dg1"
					method="com.sinosoft.cms.document.ArticleList.dg2DataBind"
					size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="15%" sortfield="title" direction=""><b>标题</b></td>
							<td width="25%"><strong>URL</strong></td>
							<td width="10%"><strong>顺序值</strong></td>
							<td width="5%"><strong>状态</strong></td>
							<td width="10%"><strong>栏目</strong></td>
							<td width="8%"><strong>关键词</strong></td>
							<td width="7%"><strong>创建者</strong></td>
							<td width="15%" sortfield="publishdate" direction=""><strong>发布时间</strong></td>
						</tr>
						<tr onDblClick="edit(${ID});">
							<td algin="center">${Title}</td>
							<td style="${TitleStyle}"><span title="${Title}">${Title}</span></td>
							<td>${url}</td>
							<td>${OrderFlag}</td>
							<td>${StatusName}</td>
							<td>${name}</td>
							<td>${MetaKeywords}</td>
							<td>${author}</td>
							<td title="${firstpublishdate}">${firstpublishdate}</td>
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
