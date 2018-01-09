<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>按钮</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 250;
	diag.Title = "新建" + this.document.title;
	diag.URL = "Platform/ButtonDialog.jsp?OperType=SAVE";
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加菜单按钮" ;
	diag.Message = "设置菜单页面、tab页签按钮等";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.platform.Button.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1_button");
			}
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1_button");
	if(!dt||dt.getRowCount()==0){
		Dialog.alert("请先选择要编辑的菜单按钮！");
		return;
	}
	if(dt.getRowCount() != 1){
		Dialog.alert("请先选择一条要编辑的菜单按钮！");
		return;
	}
	
	var dr = dt.getDataRow(0);
	if(dr.get("TT") != '1'){
		Dialog.alert("请先选择要编辑的菜单按钮(菜单数据不做编辑处理)！");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 250;
	diag.Title = "修改" + this.document.title;
	diag.URL = "Platform/ButtonDialog.jsp?OperType=MODIFY&ID=" + dr.get("ID");
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改菜单按钮" ;
	diag.Message = "修改菜单页面、tab页签按钮等";
	diag.show();
}

function del(){
	var dt = DataGrid.getSelectedData("dg1_button");
	if(!dt||dt.getRowCount()==0){
		Dialog.alert("请先选择要删除的菜单按钮！");
		return;
	}
	var ids = "";
	for(var i=0;i<dt.getRowCount();i++){
		var dr = dt.getDataRow(i);
		if(dr.get("TT") == '1'){
			ids += dr.get("ID") + ",";
		}
	}
	if(ids == null || ids == '' ){
		Dialog.alert("请先选择要删除的菜单按钮(菜单数据不做删除处理)！");
		return;
	}
	
	Dialog.confirm("您确认要删除这些按钮吗？<br/>(菜单数据不做删除处理)",function(){
		var dc = new DataCollection();
		dc.add("IDs", ids );
		Server.sendRequest("com.sinosoft.platform.Button.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1_button');
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
      <td>
	        <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
	          <tr>
	            <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />按钮列表</td>
	          </tr>
			  <tr>
				  <td style="padding:0 8px 4px;">
		              <z:tbutton onClick="add()"><img src="../Icons/icon025a2.gif"/>新建</z:tbutton>
	  				  <z:tbutton onClick="edit()"><img src="../Icons/icon025a4.gif"/>修改</z:tbutton>
                      <z:tbutton onClick="del()"><img src="../Icons/icon025a3.gif"/>删除</z:tbutton>
	            </td>
	          </tr>
	          <tr>   
	           <td style="padding:0px 5px;">
		            <z:datagrid id="dg1_button" method="com.sinosoft.platform.Button.dg1DataBind" page="false">
							<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
								<tr ztype="head" class="dataTableHead">
				                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
				                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
				                  <td width="18%" ztype="tree" level="treelevel" ><b>菜单名称</b></td>
				                  <td width="20%"><strong>路径</strong></td>
				                  <td width="20%"><strong>编码</strong></td>
				                  <td width="20%"><strong>事件</strong></td>
				                  <td width="17%"><strong>添加时间</strong></td>
				                  <td width="7%"><strong>备注</strong></td>
				                </tr>
				                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
				                  <td>&nbsp;</td>
				                  <td>&nbsp;</td>
				                  <td><img src="../${Icon}" align="absmiddle"/>&nbsp;${Name}</td>
				                  <td>${URL}</td>
				                  <td>${Code}</td>
				                  <td>${OnClick}</td>
				                  <td>${AddTime}</td>
				                  <td>${memo}</td>
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
