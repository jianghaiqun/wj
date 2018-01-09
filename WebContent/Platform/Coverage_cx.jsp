<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "新建渠道";
	diag.URL = "Platform/CoverageDialog.jsp?Type=coverage_cx";
	diag.onLoad = function(){
		$DW.$("Code").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.platform.Coverage.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("删除后不可恢复，确认要删除？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.platform.Coverage.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1");
				}
			});
		});
	});
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
          <tr>
            <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />列表</td>
          </tr>
			<tr>
				<td style="padding:0 8px 4px;">
				  <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建</z:tbutton>
	              <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
               </td>
          </tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="dg1" method="com.sinosoft.platform.Coverage.dg1DataBind_cx" page="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="40%"  ztype="tree" level="treelevel"><b>名称</b></td>
                  <td width="10%"><b>编码</b></td>
                  <td width="10%" ><b>父节点名称</b></td>
                  <td width="10%" ><b>展示层级</b></td>
                  <td width="10%" ><b>文件录入层级</b></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;${Name}</td>
                  <td>&nbsp;${Code}</td>
                  <td>&nbsp;${ParentName}</td>
                  <td>&nbsp;${TreeLevel}</td>
                  <td>&nbsp;${Prop2}</td>
                </tr>
              </table>
            </z:datagrid></td>
          </tr>
      </table></td>
    </tr>
  </table>
</body>
</html>
