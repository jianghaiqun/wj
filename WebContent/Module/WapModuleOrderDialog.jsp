<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>元素详细信息</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
Page.onLoad(function(){
	doSearch();
});
function doSearch(){
	DataGrid.setParam("module_dginput",Constant.PageIndex,0);
	DataGrid.setParam("module_dginput","ProductID",$V("ProductID"));
	DataGrid.loadData("module_dginput");
}

function upOrder(){
	
	Dialog.confirm("更新后会影响到WAP端显示的顺序，是否确认修改？",function(){
		var productid = $V("ProductID");
		var ms = document.getElementsByTagName("INPUT") ;
		
		var ids = "";
		var vals = "";
		for(i = 0;i <ms.length;i++) { 
			if(ms[i].name.indexOf('of_') != -1){ 
				var id =ms[i].id;
			  	if(ids == ""){
			  		ids =id+",";
			  	}else{
			  		ids = ids+id+","
			  	}
			  	var val = $V(id).trim();
			  	if(vals == ""){
			  		vals =val+",";
			  	}else{
			  		if(val == ""){
			  			val = " ";
			  		}
			  		vals = vals+val+","
			  	}
			} 
		}
		
		var dc = new DataCollection();
		dc.add("productid",productid)
		dc.add("IDs",ids);
		dc.add("IDv",vals);
		Server.sendRequest("com.sinosoft.module.Module.updateWapOrder",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dginput");
				}
			});
		}); 
	});
}
</script>
</head>
<body class="dialogBody">
	<form id="form2">
		<input name="ProductID" type="hidden" id="ProductID" value="<%=request.getParameter("pid")%>" />
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td>产品编码：<%=request.getParameter("pid")%>
					<z:tbutton onClick="upOrder()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>更新排序</z:tbutton>
				</td>
			</tr>
		</table>
		<z:datagrid id="module_dginput" method="com.sinosoft.module.Module.dg2DataBindShowWAP">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr ztype="head" class="dataTableHead">
					<td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
					<td align="center" width="10%"><strong>元素编码</strong></td>
					<td align="center" width="15%"><strong>元素名称</strong></td>
					<td align="center" width="10%"><strong>元素排序</strong></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="center">${inputcode}</td>
					<td align="center">${inputname}</td>
					<td align="center" style="width:150px"><input id="${id}" name="of_${id}" type="text" value ="${orderflag}"/></td>
				</tr>
			</table>
		</z:datagrid>
	</form>
</body>
</html>

