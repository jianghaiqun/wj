<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function menuSave(){
	var checkValue = $V("MenuType");
	var checkName = "";
	if(checkValue=="kxb"){
		checkName ="开心保保险微信平台";
	}else{
		checkName ="开心保车险微信平台";
	}
	var dc = new DataCollection();
	dc.add("MenuType",$V("MenuType"));
	Dialog.confirm("确定要生成【"+checkName+"】菜单,可能影响微信平台？",function(){
		Server.sendRequest("com.sinosoft.framework.utility.weixin.common.MenuManager.createMenu",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					
				}
			});
		});
	});
}
function menuDelete(){
	var checkValue = $V("MenuType");
	var checkName = "";
	if(checkValue=="kxb"){
		checkName ="开心保保险微信平台";
	}else{
		checkName ="开心保车险微信平台";
	}
	var dc = new DataCollection();
	dc.add("MenuType",$V("MenuType"));
	Dialog.confirm("确定要删除【"+checkName+"】菜单,可能影响微信平台？",function(){
		Server.sendRequest("com.sinosoft.framework.utility.weixin.common.MenuManager.deleteMenu",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					
				}
			});
		});
	});
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "新建菜单";
	diag.URL = "weixin/MenuManagerDialog.jsp";
	diag.onLoad = function(){
		$DW.$("ExternalMenuName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }

	var MenuResponseType = $DW.$V('MenuResponseType');
	var MenuURL = $DW.$V('MenuURL');
	if ('view' == MenuResponseType && MenuURL == '') {
		Dialog.alert("请输入跳转链接！");
		return;
	}
	
	Server.sendRequest("com.sinosoft.framework.utility.weixin.common.MenuManager.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要编辑的信息！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "修改菜单";
	diag.URL = "weixin/MenuManagerDialog.jsp?ID="+dr.get("id");
	diag.onLoad = function(){
		$DW.$("ExternalMenuName").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑菜单信息";
	diag.Message = "设置菜单信息";
	diag.show();
}

function editSave() {
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	var MenuLevel = $DW.$V('MenuLevel');
	var ParentMenuID = $DW.$V('ParentMenuID');
	if ('1' == MenuLevel && ParentMenuID == '') {
		Dialog.alert("请选择父菜单！");
		return;
	}
	var MenuResponseType = $DW.$V('MenuResponseType');
	var MenuURL = $DW.$V('MenuURL');
	if ('0' == MenuLevel && 'view' == MenuResponseType) {
		Dialog.alert("父菜单的回复类型必须是click！");
		return;
	}
	if ('view' == MenuResponseType && MenuURL == '') {
		Dialog.alert("请输入跳转链接！");
		return;
	}
	
	Server.sendRequest("com.sinosoft.framework.utility.weixin.common.MenuManager.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}
//删除
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.framework.utility.weixin.common.MenuManager.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}
function refresh(){
	DataGrid.loadData('dg1');
}
</script>
</head>
<body>
	<table>
		<tr>
			<td>
				<z:init method="com.sinosoft.framework.utility.weixin.common.MenuManager.init">
				<div style="float: left;">
				微信平台&nbsp;<z:select id='MenuType'>${menutype}</z:select>&nbsp;
				</div>
				<input type="button" name="Submitbutton" id="Submitbutton" value="生成菜单" onClick="menuSave()"><b></b>
				<input type="button" name="Submitbutton" id="Submitbutton" value="删除菜单" onClick="menuDelete()">
				</z:init>
			</td>
		</tr>
		<tr>
			<td style="padding:20px 0px 0px 0px;">
				<z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建</z:tbutton>
              	<z:tbutton onClick="edit()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>编辑</z:tbutton>
              	<z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
              	<z:tbutton onClick="refresh()"><img src="../Icons/icon022a1.gif" width="20" height="20"/>刷新</z:tbutton>
			</td>
		</tr>
		<tr>
			<td style="padding:5px 0px;">
			<z:datagrid id="dg1" method="com.sinosoft.framework.utility.weixin.common.MenuManager.dg1DataBind" page="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="sortMenu">
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="18%" ztype="tree" level="MenuLevel" ><b>菜单名称</b></td>
                  <%-- <td width="7%" ztype="checkbox" checkedvalue="1" field="MenuStatus"><b>是否显示</b></td> --%>
                  <td width="6%"><strong>菜单KEY</strong></td>
                  <td width="6%"><strong>菜单回复类型</strong></td>
                  <td width="30%"><strong>菜单URL</strong></td>
                  <td width="17%"><strong>创建时间</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ExternalMenuName}</td>
                  <%-- <td></td> --%>
                  <td>${MenuAttribute}</td>
                  <td>${MenuResponseType}</td>
                  <td>${MenuURL} </td>
                  <td>${CreateDate}</td>
                </tr>
                <%-- <tr ztype="edit" bgcolor="#E1F3FF">
                  <td>&nbsp;</td>
                  <td bgcolor="#E1F3FF">&nbsp;</td>
                  <td bgcolor="#E1F3FF"><input name="text" type="text" class="input1" id="ExternalMenuName" value="${ExternalMenuName}" size="5"></td>
                  <td></td>
                  <td bgcolor="#E1F3FF"><input name="text" type="text" class="input1" id="MenuAttribute" value="${MenuAttribute}" size="5"></td>
                  <td bgcolor="#E1F3FF"><input name="text" type="text" class="input1" id="MenuResponseType" value="${MenuResponseType}" size="8"></td>
                  <td><input name="text2" type="text" class="input1" id="URL" value="${MenuURL}" size="200"></td>
                  <td>${CreateDate}</td>
                </tr> --%>
              </table>
            </z:datagrid>
			</td>
		</tr>
	</table>
</body>
</html>
