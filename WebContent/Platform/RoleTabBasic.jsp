<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function save(){
	DataGrid.save("dg1","com.sinosoft.platform.Role.dg1Edit",function(){DataGrid.loadData('dg1');});
}


var topFrame = window.parent;

function add(){
   topFrame.add();	
}

function del(){
	var param = $V("RoleCode");
	if(!param||param==""||param=="null"||param==null){
		return;
	}
	var paramname = $V("RoleName");
	topFrame.del(param,paramname);
}

function showEditDialog(){
    var param = $V("RoleCode");
	if(!param||param==""||param=="null"||param==null){
		return;
	}
	topFrame.showEditDialog(param);
}

function addUserToRole(){
	if(!$V("RoleCode")||$V("RoleCode")=="null"){
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 680;
	diag.Height = 350;
	diag.Title = "用户列表";
	diag.URL = "Platform/RoleTabUserListDialog.jsp?RoleCode="+$V("RoleCode");
	diag.OKEvent = addUserToRoleSave;
	diag.show();
}

function addUserToRoleSave(){
	var arr = $DW.DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选中用户！");
		return;
	}
	var dc = new DataCollection();
	dc.add("RoleCode",$V("RoleCode"));
	dc.add("UserNames",arr.join());
	Server.sendRequest("com.sinosoft.platform.RoleTabBasic.addUserToRole",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}		
		});
	});
}

function delUserFromRole(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确认删除吗？",function(){
		var dc = new DataCollection();
		dc.add("RoleCode",$V("RoleCode"));
		dc.add("UserNames",arr.join());
		Server.sendRequest("com.sinosoft.platform.RoleTabBasic.delUserFromRole",dc,function(response){
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
<z:init method="com.sinosoft.platform.RoleTabBasic.init">
<input type="hidden" id="RoleCode" value="${RoleCode}" />
<input type="hidden" id="RoleName" value="${RoleName}" />
<table  width="100%" border='0' cellpadding='0' cellspacing='0'>
<%if("admin".equals(User.getUserName())){%>
  <tr >
    <td style="padding:4px 5px;"><z:tbutton onClick="add()"><img src="../Icons/icon025a2.gif"/>新建</z:tbutton>
	  <z:tbutton onClick="showEditDialog()"><img src="../Icons/icon025a4.gif"/>修改</z:tbutton>
    <z:tbutton onClick="del()"><img src="../Icons/icon025a3.gif"/>删除</z:tbutton></td>
</tr>
 <%} %>
<tr>   <td style="padding:0px 5px;">
<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
		<tr class="dataTableHead">
		  <td  width="5%">&nbsp;</td>
		  <td width="10%" ><b>名称</b></td>
		  <td width="35%" ><b>值</b></td>
          <td width="12%" ><b>名称</b></td>
		  <td width="38%" ><b>值</b></td>
	    </tr>
		<tr>
		  <td >&nbsp;</td>
		  <td ><strong>角色名：</strong></td>
		  <td>${RoleName}</td>
            <td ><strong>所属机构：</strong></td>
		  <td>${BranchName}</td>
	    </tr>
	    <tr>
		  <td >&nbsp;</td>
          <td ><strong>备注：</strong></td>
		  <td>${Memo}</td>
		  <td ><strong></strong></td>
		  <td></td>
	    </tr>
	  </table>
	  </td>
 </tr>

				<tr>
					<td style="padding:4px 5px;"><z:tbutton onClick="addUserToRole()"><img src="../Icons/icon021a2.gif"/>添加用户到角色</z:tbutton>
    		  <z:tbutton onClick="delUserFromRole()"><img src="../Icons/icon021a3.gif"/>从角色中删除用户</z:tbutton>
  			</td>
		</tr>
		<tr>   <td style="padding:0px 5px;">
<z:datagrid id="dg1" method="com.sinosoft.platform.RoleTabBasic.dg1DataBind" size="10">
     <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
		<tr ztype="head" class="dataTableHead">
   	        <td width="6%" ztype="RowNo">序号</td>
	        <td width="8%" ztype="selector" field="UserName">&nbsp;</td>
            <td width="13%" ><b>用户名</b></td>
			<td width="12%" ><b>真实姓名</b></td>
			<td width="21%" ><b>电子邮件</b></td>
			<td width="40%" ><b>所属角色</b></td>
	    </tr>
	    <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
	        <td >&nbsp;</td>
			      <td>&nbsp;</td>
				  <td title="${UserName}">${UserName}</td>
				  <td title="${RealName}">${RealName}</td>
				  <td title="${Email}">${Email}</td>
				  <td title="${RoleNames}">${RoleNames}</td>
			    </tr>
				<tr ztype="pagebar">
				  <td colspan="6" align="center">${PageBar}</td>
				</tr>
	</table>
</z:datagrid>
</td></tr></table>
</z:init>
</body>
</html>