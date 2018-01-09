<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>元素管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 620;
	diag.Height = 600;
	diag.Title = "新建元素";
	diag.URL = "Module/ModuleAddDialog.jsp";
	diag.onLoad = function(){
		$DW.$("ElementName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.module.Module.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		});
	});
}


function del(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("删除后可能影响购买流程，确认要删除？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.module.Module.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if( ele.id == 'ElementType' || ele.id == 'ElementName'||  ele.id == 'ElementContent'||ele.id == 'Submitbutton'){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("module_dg1",Constant.PageIndex,0);
	DataGrid.setParam("module_dg1","ElementType",$V("ElementType"));
	DataGrid.setParam("module_dg1","ElementName",$V("ElementName"));
	DataGrid.setParam("module_dg1","ElementContent",$V("ElementContent"));
	DataGrid.loadData("module_dg1");
}

function edit(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 500;
	diag.Title = "编辑元素" ;
	diag.URL = "Module/ModuleEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
		$DW.$("ElementName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	Server.sendRequest("com.sinosoft.module.Module.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		})
	});
}



function setUpWapInfo(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 300;
	diag.Title = "编辑元素" ;
	diag.URL = "Module/WapModuleEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
	};
	diag.ShowButtonRow = false;	
	diag.show();
}


</script>
</head>
<body>
<z:init method="com.sinosoft.module.Module.initModuleList">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:0 8px 4px;">
					    <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建</z:tbutton>
              		    <z:tbutton onClick="edit()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>编辑</z:tbutton>
             		    <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
             		    <z:tbutton onClick="setUpWapInfo()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>设置wap元素</z:tbutton>
	            	</td>
	           </tr>
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
			    		 元素类型&nbsp;<z:select id='ElementType'>${ElementTypeList}</z:select>&nbsp;
						 元素名称&nbsp;<input name="ElementName" type="text" id="ElementName"> &nbsp;
						 元素内容&nbsp;<input name="ElementContent" type="text" id="ElementContent"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="module_dg1" method="com.sinosoft.module.Module.dg1DataBind"  size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="15%"><strong>元素名称</strong></td>
                  <td width="15%"><strong>元素类型</strong></td>
                  <td width="17%"><strong>备注</strong></td>
                  <td width="20%"><strong>创建时间</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ElementName}</td>
                  <td>${ElementTypeName} </td>
                  <td>${Memo}</td>
                  <td> ${CreateDate}</td>
                </tr>
                <tr ztype="pagebar">
					<td colspan="6" align="left">${PageBar}</td>
				</tr>
              </table>
            </z:datagrid></td>
          </tr>
      </table></td>
    </tr>
  </table>
 </z:init>
</body>
</html>
