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
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	if(arr.length>1){
		Dialog.alert("请选择一行进行删除！");
		return;
	}
	Dialog.confirm("确认删除选中的文档吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		
		dc.add("PageIndex",DataGrid.getParam("dg1",Constant.PageIndex));
		dc.add("PageSize",DataGrid.getParam("dg1",Constant.Size));
		
		Server.sendRequest("com.sinosoft.cms.document.ProductBaseMsgManage.del",dc,function(response){
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

function edit(id){
	var width  = (screen.availWidth-10)+"px";
	var height = (screen.availHeight-50)+"px";
	var leftm  = 0;
	var topm   = 0;
	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
	var url="ProductArticle.jsp?ArticleID=" + id;
	var w = window.open(url,"",args);
	if(!w){
		Dialog.alert("发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!" ) ;
		return ;
	}
}

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

function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","productCode",$V("productID"));
	DataGrid.setParam("dg1","productName",$V("productName"));
	DataGrid.setParam("dg1","comName",$V("comName"));
	DataGrid.setParam("dg1","comFlag",$V("comFlag"));
	DataGrid.loadData("dg1");
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
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
					               产品编码&nbsp;<input name="productID" type="text" id="productID"> &nbsp;
			    		 产品名称&nbsp;<input name="productName" type="text" id="productName"> &nbsp;
 						品牌名称&nbsp;<input name="comName" type="text" id="comName"> &nbsp;
			    		是否关联品牌&nbsp;
						 <z:select name='comFlag' id='comFlag' style="width:50px;">
						 	<option value=""></option>
						 	<option value="Y">Y</option>
							<option value="N">N</option>
						 </z:select>&nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查 询" onClick="search()">&nbsp;&nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="删 除" onClick="del()">
				    </div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.document.ProductBaseMsgManage.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="2%" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="8%" sortfield="orderflag" direction=""><b>产品编码</b></td>
							<td width="20%"><strong>名称</strong></td>
							<td width="8%"><strong>基本信息</strong></td>
							<td width="8%"><strong>公共头信息</strong></td>
							<td width="9%"><strong>公共尾部信息</strong></td>
							<td width="15%"><strong>是否可使用品牌信息</strong></td>
							<td width="15%"><strong>是否关联品牌</strong></td>
							<td width="10%"><strong>最新修改时间</strong></td>
						</tr>
						<tr onDblClick="edit('${ProductID}');">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${ProductID}</td> 
							<td>${ProductName}</td> 
							<td>${BaseInfo}</td>
							<td>${FrontInfo}</td>
							<td>${TailInfo}</td>
							<td>${IsFlag}</td>
							<td>${ComFlag}</td>
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
