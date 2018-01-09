<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广告位管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 460;
	diag.Title = "新建广告位";
	diag.URL = "DataService/AdvertiseAddDialog.jsp";
	diag.onLoad = function(){
		$DW.$("AdSpaceName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}
function addSave(){
	var dc = $DW.Form.getData("form2");
	var prop1=$DW.$('prop1').checked;
	if(prop1==false){
		var AdvertiseWidth=$DW.$("AdvertiseWidth").value;
		var AdvertiseHeight=$DW.$("AdvertiseHeight").value;
		//if(AdvertiseWidth==""||AdvertiseWidth==null){
		//	Dialog.alert("非弹窗广告需要填写广告宽度！");
		//	return;
		//}
		if(AdvertiseHeight==""||AdvertiseHeight==null){
			Dialog.alert("非弹窗广告需要填写广告高度！");
			return;
		}
	}
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.AdvertiseManage.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert("新增广告位成功",function(){
				$D.close();
				DataGrid.loadData("dg1");	
			});
		}
		else{
			Dialog.alert(response.Message);
		}
	});
}
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的广告位！");
		return;
	}
	
	Dialog.confirm("确定要删除广告位吗？",function(){
		var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.sinosoft.cms.dataservice.AdvertiseManage.del",dc,function(response){
				if(response.Status==1){
					Dialog.alert(response.Message,function(){
					   DataGrid.loadData("dg1");
					});
				}
				else{
					Dialog.alert(response.Message);
				}
			});
	})
}

function editDialog(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要编辑的广告位！");
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr.length > 1){
		Dialog.alert("请先选择一条要修改的记录!");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 460;
	diag.Title = "编辑广告位基本信息";
	diag.URL = "DataService/AdvertiseEditDialog.jsp?ID="+dr.get("ID");
	diag.onLoad = function(){
    	$DW.$S("ID",dr.get("ID"));
		$DW.$("AdSpaceName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	var prop1=$DW.$('prop1').checked;
	if(prop1==false){
		var AdvertiseWidth=$DW.$("AdvertiseWidth").value;
		var AdvertiseHeight=$DW.$("AdvertiseHeight").value;
		//if(AdvertiseWidth==""||AdvertiseWidth==null){
		//	Dialog.alert("非弹窗广告需要填写广告宽度！");
		//	return;
		//}
		if(AdvertiseHeight==""||AdvertiseHeight==null){
			Dialog.alert("非弹窗广告需要填写广告高度！");
			return;
		}
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.AdvertiseManage.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert("修改广告位成功",function(){
				$D.close();
				DataGrid.loadData("dg1");
			});
		}
		else{
			Dialog.alert(response.Message);
		}
	});
}

function doSearch(){
	var name = "";
	if($V("SearchContent").trim()!= "请输入要查询的广告位名称"){
		name = $V("SearchContent");
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchContent",name);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchContent'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	var keyWord = $V("SearchContent");
	if (keyWord == "请输入要查询的广告位名称") {
		$S("SearchContent","");
	}
}

function showAdList(ID,AdSpaceName){
	var arr;
	if(ID==""||ID==null){
		arr = DataGrid.getSelectedValue("dg1");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择广告位！");
			return;
		}
		if(arr.length > 1){
			Dialog.alert("请选择一条要查看的广告列表!");
			return;
		}
		ID = arr[0];
	}
	var diag = new Dialog("Diag1");
	diag.Width = 720;
	diag.Height = 380;
	diag.Title = "广告列表";
	diag.URL = "DataService/AdvertiseManageDialog.jsp?RelaID="+ID+"&RelaName="+AdSpaceName;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function loadAdData(){
	DataGrid.loadData("dg1");	
}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
<tr valign="top">
    <td>
	<table width="100%" border="0" cellpadding="4" cellspacing="0" class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon010a1.gif"/> 广告位列表
      </tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add();"><img src="../Icons/icon010a2.gif" />新建</z:tbutton> 
        <z:tbutton onClick="editDialog();"><img src="../Icons/icon010a16.gif" />修改</z:tbutton>
        <z:tbutton onClick="del();"><img src="../Icons/icon010a3.gif" />删除</z:tbutton> 
        <z:tbutton onClick="showAdList();"><img src="../Icons/icon010a12.gif" />广告列表</z:tbutton>
		<div float: right; white-space: nowrap;">
		<input name="SearchContent" type="text" id="SearchContent" value="请输入要查询的广告位名称" onFocus="delKeyWord();" style="width:150px">
		<input type="button" name="Submitbutton" id="Submitbutton" value="搜索" onClick="doSearch()">
		</div>
        </td>
      </tr>
      <tr>
        <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
		<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.AdvertiseManage.dg1DataBind"	size="15">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr ztype="head" class="dataTableHead">
				<td width="3%" ztype="RowNo">序号</td>
				<td width="3%" ztype="selector" field="id">&nbsp;</td>
				<td width="17%"><b>广告位名称</b></td>
				<td width="15%"><b>广告位类型</b></td>
                <td width="10%"><b>广告位描述</b></td>
                <td width="10%"><b>宽*高</b></td>
			</tr>
			<tr onDblClick="showAdList(${ID},'${AdSpaceName}')" style1="background-color:#FFFFFF"
				style2="background-color:#F9FBFC">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>${AdSpaceName}</td>
				<td>${AdSpaceTypeNs}</td>
                <td>${AdSpaceDesc}</td>
                <td>${AdvertiseWidth}*${AdvertiseHeight}</td>
			</tr>
			<tr ztype="pagebar">
				<td colspan="9" align="center">${PageBar}</td>
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