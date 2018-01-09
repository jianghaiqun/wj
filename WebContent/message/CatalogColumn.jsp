<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义表单</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>
<script>
Page.onLoad(function(){
	Application.setAllPriv("article",$V("InnerCode"));
});

function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 450;
	diag.Height = 350;
	diag.Title = "新建自定义字段";
	diag.URL = "message/CatalogColumnDialog.jsp?CatalogType="+$V("CatalogType");
	diag.onLoad = function(){
		$DW.$("ValueName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}


function addSave(){
	var dc = $DW.Form.getData("form2");
	var noCheckArr = [];
	var inputType = $DW.$V("InputType");
	if($DW.input==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.text==inputType){
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.select==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.radio==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.checkbox==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.dateInput==inputType||$DW.timeInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.imageInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.htmlInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
		var HTML = $DW.$V("HTML");
		if(HTML==""||HTML=="<br/>"){
			Dialog.alert("HTML内容不能为空");
			return;
		}else{
			dc.add("HTML",HTML);
		}
	}
	if($DW.Verify.hasError(noCheckArr,"form2")){
		return;
	}
	if(!$DW.$NV("IsMandatory")){
		dc.add("IsMandatory","N");
	}
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.sinosoft.message.CatalogColumn.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});
}
function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的行！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改1条信息！");
		return;
	}
	dr = drs[0];
  editDialog(dr.get("ID"));
}

function editDialog(ID){
	var diag = new Dialog("Diag2");
	diag.Width = 450;
	diag.Height = 350;
	diag.Title = "编辑自定义字段";
	diag.URL = "message/CatalogColumnEditDialog.jsp?ColumnID="+ID;
	diag.onLoad = function(){
		$DW.$("ValueName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	var noCheckArr = [];
	var inputType = $DW.$V("InputType");
	if($DW.input==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.text==inputType){
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.select==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.radio==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.checkbox==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
	}else if($DW.dateInput==inputType||$DW.timeInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.imageInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
	}else if($DW.htmlInput==inputType){
		noCheckArr.push($DW.$("ColSize"));
		noCheckArr.push($DW.$("RowSize"));
		noCheckArr.push($DW.$("MaxLength"));
		noCheckArr.push($DW.$("ListOption"));
		var HTML = $DW.$V("HTML");
		if(HTML==""||HTML=="<br/>"){
			Dialog.alert("HTML内容不能为空");
			return;
		}else{
			dc.add("HTML",HTML);
		}
	}
	if($DW.Verify.hasError(noCheckArr,"form2")){
		return;
	}
	if(!$DW.$NV("IsMandatory")){
		dc.add("IsMandatory","N");
	}
	dc.add("CatalogID",$V("CatalogID"));
	Server.sendRequest("com.sinosoft.message.CatalogColumn.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});
}

function del() {
	Dialog.confirm("您确认要删除评论吗?",function(){
		var dc = new DataCollection();
		var arr = DataGrid.getSelectedValue("dg1");
		dc.add("IDs", arr.join());
		Server.sendRequest("com.sinosoft.message.CatalogColumn.del", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
	});
}
function changeStatus(id){
	document.getElementById("CatalogType").value=id;
	DataGrid.setParam("dg1","CatalogType",id);
	DataGrid.loadData("dg1");
}
/* Page.onLoad(function() {
	changeStatus();
});  */
</script>
</head>
<body>
<z:init>
	<div style="padding:2px;">
	<table width="100%" cellpadding="0" cellspacing="0"
		style="margin-bottom:4px;">
		<tr>
			<td>
			<input name="CatalogType" id="CatalogType" type="hidden"  />
			<input name="CatalogID" id="CatalogID" value="${CatalogID}"type="hidden" />
			<input name="InnerCode" type="hidden" id="InnerCode"  value="${InnerCode}" />
			<z:tbutton id="BtnAdd" priv="article_manage"
				onClick="add()">
				<img src="../Icons/icon024a2.gif" />新建</z:tbutton> <z:tbutton id="BtnEdit"
				priv="article_manage" onClick="edit()">
				<img src="../Icons/icon024a4.gif" />编辑</z:tbutton> <z:tbutton id="BtnDel"
				priv="article_manage" onClick="del()">
				<img src="../Icons/icon024a3.gif" />删除</z:tbutton>
				<%-- &nbsp;<font color="#999999">栏目类型</font>
                <z:select id="CatalogType" style="width:100px;" onChange="changeStatus();">
                   ${CatalogType}
                </z:select> --%>
				</td>
		</tr>
	</table>
	<z:datagrid id="dg1"
		method="com.sinosoft.message.CatalogColumn.dg1DataBind" page="false">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr ztype="head" class="dataTableHead">
				<td width="4%" ztype="RowNo">&nbsp;</td>
				<td width="5%" ztype="selector" field="id">&nbsp;</td>
				<td width="15%"><b>字段ID</b></td>
				<td width="15%"><b>名称</b></td>
				<td width="14%"><b>代码</b></td>
				<td width="15%"><b>表现形式</b></td>
				<td width="12%"><b>校验类型</b></td>
				<td width="10%" align="center" ztype="Checkbox" field="IsMandatory"
					checkedvalue="Y"><b>是否必填</b></td>
				<td width="10%"><b>最大字数</b></td>
			</tr>
			<tr onDblClick="editDialog(${ID})">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>${ID}</td>
				<td>${ValueName}</td>
				<td>${ValueCode}</td>
				<td>${InputTypeName}</td>
				<td>${VerifyTypeName}</td>
				<td align="center"></td>
				<td>${MaxLength}</td>
				<td>${OrderFlag}</td>
			</tr>
		</table>
	</z:datagrid></div>

</z:init>
</body>
</html>
