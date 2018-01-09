<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运算符查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
// 刷新

function queryClick(){
	DataGrid.clear("contgrid");
	var dc = Form.getData($F("fm"));
	for(var i = 0;i<dc.size();i++){
		DataGrid.setParam("contgrid",dc.getKey(i),dc.get(i));
	}
	DataGrid.setParam("contgrid",Constant.PageIndex,0);
	DataGrid.loadData("contgrid");
}


</script>
</head>
<body>
<form name="fm" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="6"
	class="blockTable" style="table-layout: fixed;">
	<tr>
		<td>
		<fieldset><legend> <label> 查询条件</label> </legend>
		<table width="100%">
			<tr>
				<td width="180">运算符名称</td>
				<td width="311"><input id="LRName" style="width: 150px" /></td>
				<td width="180">数据类型</td>
				<td width="311"><z:select id="CommandType" style="width:150px"
					code="IbrmsCommandType" listWidth="260" input="true" showValue="true"
					lazy="true"></z:select></td>
				<td width="180">有效性</td>
				<td width="311"><z:select id="Valid" name="Valid"  >
				 <span value="" selected></span>
                      <span value="1">是</span>
                      <span value="0" >否</span> 
		   </z:select></td>
			</tr>
			
			<tr>
				<td colspan="2"><z:tbutton onClick="queryClick()">
					<img src="../Icons/icon403a5.gif" />查询运算符</z:tbutton></td>
			</tr>
		</table>
		</fieldset>
		</td>
	</tr>
	<tr>
		<td style="padding: 0px 5px;"><z:datagrid id="contgrid"
			method="com.sinosoft.ibrms.BOMDataQueryUI.dgDataBind"
			autoFill="false" size="12" lazy="true" scroll="true">
			<table cellpadding="2" cellspacing="0" class="dataTable"
				afterdrag="afterRowDragEnd" fixedHeight="276px" fixedWidth="99.9%">
				<tr ztype="head" class="dataTableHead">
					<td width="3%" ztype="RowNo" drag="true"><img
						src="../Framework/Images/icon_drag.gif"></td>
					
					<td width="10%">运算符名称</td>
					<td width="10%">界面展示</td>
					<td width="10%">技术实现</td>
					<td width="10%">有效性</td>
					<td width="10%">运算数据类型</td>
					<td width="10%">运算结果类型</td>
					<td width="10%">连接参数的类型</td>
					<td width="10%">参数个数</td>
					<td width="17%">描述信息</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>${name}</td>
					<td>${display}</td>
					<td>${implenmation}</td>
					<td>${valid}</td>
					<td>${commandtype}</td>
					<td>${resulttype}</td>
					<td>${paratype}</td>
					<td>${paranum}</td>
					<td>${description}</td>	
					
				</tr>
					<tr>
					<td>&nbsp;</td>
					<td>${name}</td>
					<td>${display}</td>
					<td>${implenmation}</td>
					<td>${valid}</td>
					<td>${commandtype}</td>
					<td>${resulttype}</td>
					<td>${paratype}</td>
					<td>${paranum}</td>
					<td>${description}</td>	
					
				</tr>
				
				<tr ztype="PageBar">
					<td colspan="14">${PageBar}</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>

</form>
</body>
</html>
