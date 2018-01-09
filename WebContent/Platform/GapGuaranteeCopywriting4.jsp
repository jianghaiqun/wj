<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>险种维护</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<z:init method="com.sinosoft.platform.WXFuLi.initRiskTypeManageList">
<script language="javascript">
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("CodeValue",$V("CodeValue"));
		dr.set("CodeName",$V("CodeName"));
		dr.set("Memo",$V("Memo"));
		return true;
	}
});

function update(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	var dr = drs[0]
	if(dt.getRowCount()==0){
		Dialog.alert("请先选择要编辑那行！");
		return;
	} 
	if (drs.length > 1) {
		Dialog.alert("只能选择1条信息编辑！");
		return;
	}
	
	var diag = new Dialog("Diag2");
	diag.Width = 350;
	diag.Height = 150;
	diag.Title = "文案修改";
	diag.URL = "Platform/GapGuaranteeCopywriting4Edit.jsp?codeValue="+dr.get("codeValue");
	diag.onLoad = function(){
		$DW.$("CodeType").value="${CodeType}";
		$DW.$("CodeName").value=dr.get("CodeName");
		$DW.$("CodeValue").value=dr.get("CodeValue");
		
		$DW.$("CodeNameOld").value=dr.get("CodeName");
		$DW.$("CodeValueOld").value=dr.get("CodeValue");
		
		$DW.$("CodeType").disabled=true;
		$DW.$("CodeValue").focus();
	};
	diag.OKEvent = updateThis;
	diag.show();
}

function updateThis(){
	var dc = $DW.Form.getData("form4");
	dc.add("ParentCode","${CodeType}");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.platform.GapGuarantee.update4",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}


function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 350;
	diag.Height = 150;
	diag.Title = "新建文案";
	diag.URL = "Platform/GapGuaranteeCopywriting4Edit.jsp";
	diag.onLoad = function(){
		$DW.$("CodeType").value="${CodeType}";
		$DW.$("CodeType").disabled=true;
		$DW.$("CodeValue").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form4");
	dc.add("ParentCode","${CodeType}");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.platform.GapGuarantee.add4",dc,function(response){
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
	if(dt.getRowCount()==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	if(dt.getRowCount()>=2){
		Dialog.alert("一次只能删除一条数据！");
		return;
	}
	var dc = new DataCollection();
	dc.add("DT",dt);
	Dialog.confirm("您确认要删除吗？",function(){
		Server.sendRequest("com.sinosoft.platform.GapGuarantee.del4",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}		
			});
		});
	});
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	var order = $("dg1").DataSource.get(rowIndex-1,"CodeOrder");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg1").DataSource;
	var i = rowIndex-1;
	if(i!=0){
		target = ds.get(i-1,"CodeOrder");
		dc.add("Type","Before");		
	}else{
		dc.add("Type","After");
		target = $("dg1").DataSource.get(1,"CodeOrder");
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	dc.add("ParentCode",ds.get(0,"ParentCode"));
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.sinosoft.platform.WXFuLi.sortColumn",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd">
					缺口保障测评结果页标签文案
					</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()"><img src="../Icons/icon018a2.gif" />新建</z:tbutton> 
					<z:tbutton onClick="update()"><img src="../Icons/icon018a4.gif" />编辑</z:tbutton> 
					<z:tbutton onClick="del()"><img src="../Icons/icon018a3.gif" />删除</z:tbutton>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1"
						method="com.sinosoft.platform.GapGuarantee.dg4HomePage4">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" afterdrag="afterRowDragEnd">
							<tr ztype="head" class="dataTableHead">
								<td  width="5%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
								<td width="5%" ztype="selector" field="id">&nbsp;</td>
								<td width="15%"><b>有无社保</b></td>
								<td width="15%"><strong>保障缺口社保情况文案</strong></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td >&nbsp;</td>
								<td>&nbsp;</td>
								<td>${CodeName}</td>
								<td>${CodeValue}</td>
								<td><input name="CodeValue"  type="hidden"  id="CodeValue" value ='${CodeValue}' /></td>
							</tr>
						</table></z:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</z:init>
</html>
