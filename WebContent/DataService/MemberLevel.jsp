<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>会员等级项目表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 	 return;
    	}														
		dr.set("MconfigName",$V("Name"));
		dr.set("MconfigCode",$V("TreeLevel"));
		dr.set("MconfigValue",$V("Score"));
		return true;
	}
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 360;
	diag.Height = 130;
	diag.Title = "新建会员等级";
	diag.URL = "DataService/MemberLevelDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	if( $DW.$V("Name").indexOf(",")!=-1 || $DW.$V("Name").indexOf("^")!=-1){
		alert("类别和名称中含有不规则字符\",\^\"");
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.MemberLevel.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});
}

function save(){
	DataGrid.save("dg1","com.sinosoft.cms.dataservice.MemberLevel.save",function(){DataGrid.loadData('dg1');});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除该项吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.dataservice.MemberLevel.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				DataGrid.loadData("dg1");
			}
		});
	},function(){
		return;
	});
}

</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:8px 10px;">
                <z:tbutton onClick="add()"><img src="../Icons/icon401a2.gif" />新建</z:tbutton> 
                <z:tbutton onClick="save()"><img src="../Icons/icon401a1.gif" />保存</z:tbutton> 
                <z:tbutton onClick="del()"><img src="../Icons/icon401a3.gif" />删除</z:tbutton>
                </td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.MemberLevel.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo"><b>序号</b></td>
							<td width="4%" ztype="selector" field="MconfigCode">&nbsp;</td>
							<td width="36%"><b>会员配置名称</b></td>
							<td width="38%"><b>会员配置代码</b></td>
							<td width="17%"><b>会员配置值</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td height="22" align="center">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${MconfigName}</td>
							<td>${MconfigCode}</td>
							<td>${MconfigValue}</td>
						</tr>
                        <tr ztype="edit" bgcolor="#E1F3FF">
							<td height="22" align="center">&nbsp;</td>
							<td>&nbsp;</td>
							<td><input type="text" name="Name" id="Name" value="${MconfigName}"/></td>
							<td><input type="text" name="TreeLevel" id="TreeLevel" size="6" value="${MconfigCode}" verify="NotNull"/></td>
							<td><input type="text" name="Score" id="Score" size="6" value="${MconfigValue}" verify="NotNull&&Number"/></td>
						</tr>
						<tr ztype="pagebar">
							<td height="25" colspan="6">${PageBar}</td>
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
