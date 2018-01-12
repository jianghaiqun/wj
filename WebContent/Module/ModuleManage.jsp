<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模版元素管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if( ele.id == 'ModuleType' || ele.id == 'ModuleName'||  ele.id == 'ProduceFlag'||ele.id == 'Submitbutton'){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("module_dg3",Constant.PageIndex,0);
	DataGrid.setParam("module_dg3","ModuleType",$V("ModuleType"));
	DataGrid.setParam("module_dg3","ModuleName",$V("ModuleName"));
	DataGrid.setParam("module_dg3","ProduceFlag",$V("ProduceFlag"));
	DataGrid.loadData("module_dg3");
}


function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "生成模版" ;
	diag.URL = "Module/ModuleManageAddDialog.jsp" ;
	diag.onLoad = function(){
		$DW.$("ModuleName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	var dt = $DW.DataGrid.getSelectedData("module_dg2");
	if(!dt || dt.getRowCount() == 0){
		Dialog.alert("请先要生成模版的数据！");
		return;
	}
	dc.add("DT",dt);
	Server.sendRequest("com.sinosoft.module.ModuleManage.addSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("module_dg3");
			}
		})
	});

}

function del(){
	var arr = DataGrid.getSelectedValue("module_dg3");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("删除后可能影响购买流程，确认要删除？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.module.ModuleManage.delModule",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg3");
				}
			});
		});
	});
}

function edit(){
	var arr = DataGrid.getSelectedValue("module_dg3");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 350;
	diag.Title = "编辑模版" ;
	diag.URL = "Module/ModuleManageEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
		$DW.$("ModuleName").focus();
	};
	diag.CancelEvent = CancelClose;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
}

function CancelClose(){
	$D.close();
	DataGrid.loadData("module_dg3");
}

function pro(){
	var arr = DataGrid.getSelectedValue("module_dg3");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要生成的行！");
		return;
	}
	Dialog.confirm("确认重新生成？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.module.ModuleManage.proModule",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg3");
				}
			});
		});
	});
}

function copy(){
    var arr = DataGrid.getSelectedValue("module_dg3");
    if(!arr||arr.length==0||arr.length>1){
        Dialog.alert("请先选择一行要复制的模块！");
        return;
    }
    var diag = new Dialog("Diag1");
    diag.Width = 350;
    diag.Height = 150;
    diag.Title = "复制模块" ;
    diag.URL = "Module/ModuleManageCopyDialog.jsp";
    diag.onLoad = function(){
        $DW.$("ModuleName").focus();
    };
    diag.CancelEvent = CancelClose;
    diag.OKEvent = copyEvent;
    diag.show();
    diag.CancelButton.value = "关  闭";
}

function copyEvent() {
    var arr = DataGrid.getSelectedValue("module_dg3");
    var dc = $DW.Form.getData("form2");
    dc.add("id",arr[0]);
    if($DW.Verify.hasError()){
        return;
    }
    Server.sendRequest("com.sinosoft.module.ModuleManage.moduleCopy",dc,function(response){
        Dialog.alert(response.Message,function(){
            if(response.Status==1){
                $D.close();
                DataGrid.loadData("module_dg3");
            }
        })
    });
}

</script>
</head>
<body>
<z:init method="com.sinosoft.module.ModuleManage.initModuleElement">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:0 8px 4px;">
					    <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建</z:tbutton>
              		    <z:tbutton onClick="edit()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>编辑</z:tbutton>
             		    <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
             		    <z:tbutton onClick="pro()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>生成模块</z:tbutton>
             		    <z:tbutton onClick="copy()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>复制模块</z:tbutton>
	            	</td>
	           </tr>
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;">
			    		 模块类型&nbsp;<z:select id='ModuleType'>${ModuleTypeList}</z:select>&nbsp;
						 模块名称&nbsp;<input name="ModuleName" type="text" id="ModuleName"> &nbsp;
						 生成标记&nbsp;
						 <z:select id='ProduceFlag' style="width:50px;">
						 	<option value=""></option>
						 	<option value="Y">Y</option>
							<option value="N">N</option>
						 </z:select>&nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="module_dg3" method="com.sinosoft.module.ModuleManage.dg2DataBind" page="true" size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="15%"><strong>模块名称</strong></td>
                  <td width="15%"><strong>模块类型</strong></td>
                  <td width="30%"><strong>模块路径</strong></td>
                  <td width="5%"><strong>发布标记</strong></td>
                  <td width="20%"><strong>备注</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ModuleName}</td>
                  <td>${ModuleTypeName} </td>
                  <td>${ModuleURL}</td>
                  <td>${ProduceFlag}</td>
                  <td>${Memo}</td>
                </tr>
                <tr ztype="pagebar">
					<td colspan="7" align="left">${PageBar}</td>
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
