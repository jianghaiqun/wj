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
	function onTreeClick(ele) {
		var cid = ele.getAttribute("cid");
		var code = ele.getAttribute("innercode");
		$S("catalogID", cid);
		try {
			DataGrid.setParam("dg1", "catalogID", cid);
			DataGrid.setParam("dg1", Constant.PageIndex, 0);
			DataGrid.loadData("dg1");

		} catch (ex) {
			alert(ex.message);
		}
	}

	function pro(){
		var dt = DataGrid.getSelectedData("dg1");
		if(dt == null || dt.Rows==0){
			Dialog.alert("请先选择要发布的片段！");
			return;
		}

		var drs = dt.Rows;
		dr = drs[0]; 
		
		var dc = new DataCollection();
		dc.add("catalogID",$V("catalogID"));
		dc.add("ModuleCode",dr.get("ModuleCode"));
		
		Dialog.alert("请稍等...");
		Server.sendRequest("com.sinosoft.cms.document.ArticleCacheList.pro",dc,function(response){
			Dialog.closeAlert();
			
			Dialog.alert(response.Message);
		}); 
	}
		
</script>
</head>
<body>
<input type="hidden" id="catalogID" value="">

<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="260">
			<table width="260" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
						style="height:440px;width:240px;"
						method="com.sinosoft.cms.document.ArticleList.treeDataBind" level="2"
						lazy="true" resizeable="true">
						<p cid='${ID}' innercode='${InnerCode}'
							onClick="onTreeClick(this);" afterdrag='afterTreeDragEnd' >${Name}</p>
					</z:tree></td>
				</tr>
			</table>
		</td>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable">
				 
				<tr>
					<td style="padding:20px 10px;">
					 	 <input type="button" name="Submitbutton" id="Submitbutton" value="生成" onClick="pro()">
					</td>
				</tr>
				<tr>
					<td style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
						<z:datagrid id="dg1" method="com.sinosoft.cms.document.ArticleCacheList.dg1DataBind" size="15" lazy="true" multiSelect="false">
							<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="afterRowDragEnd">
								<tr ztype="head" class="dataTableHead">
									<td width="3%" height="30" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
									<td width="3%" height="30" ztype="Selector" field="id">&nbsp;</td>
									<td width="15%" >模块编码</td>
									<td width="15%" >数据库文件</td>
								</tr>
								<tr >
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>${ModuleCode}</td>
									<td>${DbFiles}</td>
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
