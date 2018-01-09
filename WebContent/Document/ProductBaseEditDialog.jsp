<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%> 
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>证件类型配置</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function add(){
	DataGrid.insertRow("module_dginput");
}
function del(){
	var dg = $("module_dginput");
	var arr = dg.getSelectedRows();
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的列!");
		return;
	}
	Dialog.confirm("确认删除？",function(){
		for(var i=arr.length-1;i>=0;i--){
			dg.deleteRow(arr[i].rowIndex);
		}
	});
	
}
function save(){
	var dc = Form.getData("form2");
	var dt = getDataTable();
	dc.add("Id",$("Id").value); 
	dc.add("Data", dt);
	if(Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.product.ProductInfo.addBaseInfo",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				//暂时不做任何处理
			}
		});
	});
}
function getDataTable(){
	var dg = $("module_dginput");
	var dt = dg.DataSource;
	var names = ["CodeValue","CodeName","CodeEnName"];
	var map = {};
	for(var i=0;i<names.length;i++){
		var eles = $N(names[i]);
		var arr = [];
		if(eles!=null){
			for(var j=0;j<eles.length;j++){
				arr.push($V(eles[j]));
			}
		}
		map[names[i]] = arr;
	}
	for(var i=0;i<dt.getRowCount();i++){
		for(var j=0;j<names.length;j++){
			dt.Rows[i].set(names[j],map[names[j]][i]);
		}
	}
	return dt;
}
function search(){
	DataGrid.setParam("module_dginput","BaseInfoType",$V("BaseInfoType"));
	DataGrid.loadData("module_dginput");
}
</script>
	</head>
	<body class="dialogBody">
	<z:init method="com.sinosoft.product.ProductInfo.initbase">
	<form id="form2">
	<table width="100%" height="50%" border="0">
		<tr>
			<td valign="middle">
			<table width="600" height="30" align="center" cellpadding="2"
				cellspacing="0" border="0">
				<tr>
					<td width="300" height="25"></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">保险公司编码：</td>
					<td  >${Id}<input name="Id" type="hidden" id="Id" value="${Id}"/><input name="ComCode" type="hidden" id="ComCode" value="${ComCode}"/></td>
					
				</tr>
				<tr>
					<td align="right">保险公司名称名称：</td>
					<td  >${InsureName}</td>
				</tr>
				<tr>
					<td align="right">基础数据类型：</td>
					<td>
					<z:select id="BaseInfoType" style="width:140px;" onChange="search()" verify="元素类型|NotNull">${BaseTypeList}</z:select>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	
	<z:datagrid id="module_dginput" method="com.sinosoft.product.ProductInfo.dg6DataBind">
            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
              <tr ztype="head" class="dataTableHead">
                <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                <td width="3%" ztype="selector" field="id">&nbsp;</td>
                <td width="5%"><strong>编码</strong></td>
                <td width="10%"><strong>显示值</strong></td>
                <td width="10%"><strong>英文显示值</strong></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td><input name="CodeValue" type="text" id="CodeValue" value="${codeValue}"></td>
                <td><input name="CodeName" type="text" id="CodeName" value="${codeName}"></td>
                 <td><input name="CodeEnName" type="text" id="CodeEnName" value="${CodeEnName}"></td>
              </tr>
            </table>
          </z:datagrid>
     <z:tbutton onClick="add()"><img src="../Icons/icon005a2.gif" width="20" height="20"/>添加</z:tbutton>
     <z:tbutton onClick="del()"><img src="../Icons/icon005a4.gif" width="20" height="20"/>删除</z:tbutton>
     <z:tbutton onClick="save()"><img src="../Icons/icon005a4.gif" width="20" height="20"/>保存</z:tbutton>
    </form>
    </z:init>
	</body>
	</html>
