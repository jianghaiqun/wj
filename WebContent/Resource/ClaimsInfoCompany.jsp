<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>保险公司分类配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("comClassifyName",$V("comClassifyName"));
		dr.set("orderFlag",$V("orderFlag"));
		dr.set("recommendFlag",$V("recommendFlag"));
		dr.set("recommFlagName",$("recommendFlag").getText());
		return true;
	}
});
function doSearch(){
	DataGrid.setParam("dg1", Constant.PageIndex, 0);
	DataGrid.setParam("dg1", "comCode", $V("supplierCode"));
	DataGrid.setParam("dg1", "recomFlag", $V("recomFlag"));
	DataGrid.loadData("dg1");
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 220;
	diag.Title = "新建保险公司分类";
	diag.URL = "Resource/CompanyClassifyDialog.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	
	Server.sendRequest("com.sinosoft.cms.resource.ClaimsInfo.addCompany",dc,function(response){
		if(response.Status==1){
			$D.close();
			doSearch();
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要设置二级分类的保险公司！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改1条信息！");
		return;
	}
	dr = drs[0]; 
  	editDialog(dr);
}


function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 320;
	diag.Title = "二级分类配置";
	diag.URL = "Resource/ClassifyDialog.jsp?comCode="+dr.get("comCode");
	diag.onLoad = function(){
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form4");
	if($DW.Verify.hasError()){
		return;
	}

	var index = $DW.$V("index");
	var index_array=index.split(",");
	var DetailNum = $DW.$V("DetailNum");
	for(var i=0;i<parseInt(DetailNum);i++){
		var Detail1 = $DW.$V("Detail_"+index_array[i]);
		if(Detail1==null||Detail1==''){
			Dialog.alert("第"+(i+1)+"行的二级分类名称没有填写！");
			return;
		}
	}
	
	Server.sendRequest("com.sinosoft.cms.resource.ClaimsInfo.setClassifys",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("删除该保险公司分类，也会删除该分类下的二级分类，确定要删除吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.resource.ClaimsInfo.delCompany",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("删除成功");
        		DataGrid.loadData("dg1");
			}
		});
	});
} 

function save() {
	
	DataGrid.save("dg1","com.sinosoft.cms.resource.ClaimsInfo.saveCompany",function(){			
		doSearch();
	});
}
</script>
</head>
<body>
<z:init method="com.sinosoft.cms.resource.ClaimsInfo.init">
<table width="100%" border="0" cellspacing="0" cellpadding="1">

	<tr>
		<td>
			<table width="50%" border="0" cellspacing="0" cellpadding="1">
				<tr>
					<td width="60px;">保险公司：</td>
					<td width="150px;"><z:select name="supplierCode" id="supplierCode" style="width:150px;">${supplier}</z:select></td>
					<td width="60px;">是否推荐：</td>
					<td width="150px;"><z:select name="recomFlag" id="recomFlag" style="width:150px;">${YesOrNo}</z:select></td>
					<td><z:tbutton onClick="doSearch();"><img src="../Icons/icon018a2.gif" />查询</z:tbutton> </td>
				</tr>
			</table>
			
		</td>
	</tr>
	<tr height="20px">
		<td></td>
	</tr>

	<tr>
		<td style="padding:0 0 6px;">
		<z:tbutton onClick="add();"><img src="../Icons/icon018a2.gif" />新建</z:tbutton> 
		<z:tbutton onClick="del();"><img src="../Icons/icon018a3.gif" />删除</z:tbutton>
		<z:tbutton onClick="save();"><img src="../Icons/icon018a4.gif" />保存</z:tbutton> 
		<z:tbutton onClick="edit();"><img src="../Icons/icon018a4.gif" />编辑二级分类</z:tbutton> 
		</td>
	</tr>
	<tr>
		<td>
		<z:datagrid id="dg1" method="com.sinosoft.cms.resource.ClaimsInfo.dg1DataBind">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" size="50">
				<tr ztype="head" class="dataTableHead">
					<td width="6%" ztype="RowNo">&nbsp;</td>
					<td width="6%" ztype="selector" field="comCode">&nbsp;</td>
					<td width="13%"><b>保险公司</b></td>
					<td width="20%"><b>保险公司分类名称</b></td>
					<td width="10%"><b>是否推荐</b></td>
					<td width="10%"><b>排序</b></td>
				</tr>
				<tr style="background-color: #F9FBFC">
					<td align="center">&nbsp;</td>
					<td>&nbsp;</td>
					<td>${comName}</td>
					<td>${comClassifyName}</td>
					<td>${recommFlagName}</td>
					<td>${orderFlag}</td>
				</tr>
				<tr ztype="edit" bgcolor="#E1F3FF">
					<td bgcolor="#E1F3FF">&nbsp;</td>
					<td>&nbsp;</td>
					<td>${comName}</td>
					<td><input name="comClassifyName" type="text" id="comClassifyName" value="${comClassifyName}" size="20" verify="保险公司分类名称|NotNull"></td>
					<td><z:select name="recommendFlag" id="recommendFlag" style="width:50px;" value="${recommendFlag}">${@YesOrNo}</z:select></td>
					<td><input name="orderFlag" type="text" id="orderFlag" value="${orderFlag}" size="15" verify="排序|Int"></td>
				</tr>
				<tr ztype="pagebar">
					<td colspan="7">${PageBar}</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</z:init>
</body>
</html>
