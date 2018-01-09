<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>链接分组</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var selectedCID = "";
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Name",$V("Name"));
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.sinosoft.cms.site.LinkGroup.save",function(){DataGrid.loadData('dg1');});
}

function add(){
	var diag = new Dialog("DiagLinkAdd");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "新建"+this.document.title;
	diag.URL = "Site/LinkGroupDialog.jsp?ID="+selectedCID;
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("Type",$DW.$NV("Type"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.site.LinkGroup.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
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
	Dialog.confirm("您确认要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.site.LinkGroup.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function link(ID,type,name){
	if(!ID){
		var dt = $("dg1").getSelectedData();
		if(!dt||dt.Rows.length==0){
			Dialog.alert("请先选择记录!");
			return;
		}
		ID = dt.Rows[0].get("ID");
		type = dt.Rows[0].get("type");
		name = dt.Rows[0].get("name");
		
	}
	var diag = new Dialog("DiagLinkEdit");
	diag.Width = 800;
	diag.Height = 500;
	diag.Title = "链接分组 "+name;
	diag.URL = "Site/Link.jsp?LinkGroupID="+ID+"&Type="+type;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function onTreeClick(ele){
	var cid = ele.getAttribute("cid");
	selectedCID = cid;
	DataGrid.setParam("dg1","id",cid);
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	var cname = ele.getAttribute("cname");
	var cmsg=[cid,cname];
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cmsg);
	menu.addItem("新建",addTeam,"/Icons/icon018a2.gif");
	menu.addItem("修改",editTeam,"/Icons/icon018a4.gif");
	menu.addItem("删除",delTeam,"/Icons/icon018a3.gif");
	menu.show();
}

function loadTreeData(){
	Tree.loadData("tree1",function(){Tree.select("tree1","cid",selectedCID)});	
}

function addTeam(){
	var diag = new Dialog("Diag1");
	diag.Width = 360;
	diag.Height = 80;
	diag.Title = "新建友情链接包";
	diag.URL = "Site/LinkTeamDialog.jsp";
	diag.OKEvent = addTeamSave;
  	diag.show();
}

function addTeamSave(){
	var dc = new DataCollection();
	dc.add("TeamName",$DW.$V("TeamName"));
	
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.site.LinkTeam.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("新建链接包成功",function(){
			    $D.close();
				Tree.loadData("tree1",function(){
					Tree.select("tree1","cid",Cookie.get("DocList.LastCatalog"));
				});
			});
		}
	});
}

function editTeam(param) {
	var cmsg=param;
	var cid =cmsg[0];
	if(!cid){
		Dialog.alert("请先选择一个分类！");
		return ;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 360;
	diag.Height = 80;
	diag.Title = "修改链接名称";
	diag.URL = "Site/LinkTeamDialog.jsp?id="+cid;
	diag.OKEvent = editTeamSave;
  	diag.show();
}

function editTeamSave(){
	var dc = new DataCollection();
	dc.add("ID",$DW.$V("cid"));
	dc.add("TeamName",$DW.$V("TeamName"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.site.LinkTeam.edit",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("修改成功",function(){
			    $D.close();
				Tree.loadData("tree1",function(){
					//Tree.select("tree1","cid",Cookie.get("DocList.LastCatalog"));
				});
			});
		}
	});
}

function delTeam(param) {
	var cmsg = param;
	var cid = cmsg[0];
	var cname = cmsg[1];
	if(!cid) {
		Dialog.alert("请先选择一个链接包！");
		return ;
	}
	Dialog.confirm("确认删除 \""+cname+"\"</font> 链接包吗？<br><font style='color:#F00'>确认将移除该包下所有链接!</font>",function() {
		var dc = new DataCollection();
		dc.add("ID", cid);
		
		Server.sendRequest("com.sinosoft.cms.site.LinkTeam.del",dc,function(response){
			if(response.Status == 0) {
				Dialog.alert(response.Message);
			} else {
				Dialog.alert("删除分类成功",function() {
					Tree.loadData("tree1",function() {
						DataGrid.setParam("dg1",Constant.PageIndex,0);
						DataGrid.loadData("dg1");
					});
				});
			}
		});
	})
}


function update(){

	var dt = $("dg1").getSelectedData();
	if(!dt||dt.Rows.length==0){
		Dialog.alert("请先选择记录!");
		return;
	}
	ID = dt.Rows[0].get("ID");
	type = dt.Rows[0].get("type");
	name = dt.Rows[0].get("name");
	
	//var arr = DataGrid.getSelectedValue("dg1");
	//if(!arr||arr.length==0){
	//	Dialog.alert("请先选择要编辑的链接分组名称！");
	//	return;
	//}
	//alert(arr[0]);
	//var diag = new Dialog("Diag1");
	//diag.Width = 700;
	//diag.Height = 400;
	//diag.Title = "设置用户"+arr[0]+"权限";
	
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 380;
	diag.Title = "新建"+this.document.title;
	diag.URL = "Site/LinkGroupUpdateDialog.jsp";
	//diag.URL = "Site/LinkGroupUpdateDialog.jsp?LinkName="+name;
	diag.onLoad = function(){
		$DW.$NS("LinkName",name);
		
		//$DW.$S("Password","");
		$DW.$("UserName").focus();
	};
	diag.OKEvent = updateSave;
	diag.show();
}

function updateSave(){

	var dt = $("dg1").getSelectedData();
	if(!dt||dt.Rows.length==0){
		Dialog.alert("请先选择记录!");
		return;
	}
	ID = dt.Rows[0].get("ID");
	name = dt.Rows[0].get("name");
	var dc = $DW.Form.getData("form3");
	dc.add("NAME",name);
	dc.add("OLDID",ID);
	
	if($DW.Verify.hasError()){
	  return;
     }

	Server.sendRequest("com.sinosoft.cms.site.LinkGroup.update",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});	
	});

}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="140">
			<table width="140" oncontextmenu="return false;" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:6px;" class="blockTd"><z:tree id="tree1"
						style="height:443px;width:130px;"
						method="com.sinosoft.cms.site.LinkGroup.treeDataBind" level="2"
						lazy="true">
						<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}'
						   cname='${Name}' onClick="onTreeClick(this);" afterdrag='afterTreeDragEnd'
						   oncontextmenu="showMenu(event,this);">${Name}</p>
					</z:tree></td>
				</tr>
			</table>
			</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon028a1.gif" />友情链接分组列表</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
					<z:tbutton onClick="add();"><img src="../Icons/icon028a2.gif" />新建链接分组</z:tbutton> 
					<z:tbutton onClick="link();"><img src="../Icons/icon028a10.gif"/>链接管理</z:tbutton> 
					<z:tbutton onClick="update();"><img src="../Icons/icon028a4.gif" />修改</z:tbutton> 
					<!-- <z:tbutton onClick="save();"><img src="../Icons/icon028a4.gif" />保存</z:tbutton> -->
					<z:tbutton onClick="del();"><img src="../Icons/icon028a3.gif" />删除</z:tbutton></td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<z:datagrid id="dg1"
					method="com.sinosoft.cms.site.LinkGroup.dg1DataBind" size="12" multiSelect="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo"><strong>序号</strong></td>
							<td width="5%" ztype="selector" field="id">&nbsp;</td>
							<td width="50%"><strong>链接分组名称</strong></td>
							<td width="30%"><strong>类型</strong></td>
						</tr>
						<tr>
							<td width="5%">&nbsp;</td>
							<td width="5%">&nbsp;</td>
							<td><a href="javascript:void(0)"
								onClick="link('${ID}','${Type}','${Name}');">${Name}</a></td>
							<td>${Type}</td>
						</tr>
						<tr ztype="edit">
							<td width="5%">&nbsp;</td>
							<td width="5%">&nbsp;</td>
							<td><input type="text" class="input1" id="Name"
								value="${Name}" maxlength="20" style="width:200px"></td>
							<td>${Type}</td>
						</tr>
						<tr ztype="pagebar">
								<td colspan="8" align="center">${PageBar}</td>
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
