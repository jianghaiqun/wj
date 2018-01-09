<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自动回复</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function editSub(){
	var diag = new Dialog("Diag2");
	diag.Width = 800;
	diag.Height = 200;
	diag.Title = "首次关注回复内容";
	diag.URL = "weixin/WXSubscribeinfoDialog.jsp";
	diag.OKEvent = addSaveSub;
	diag.show();
}

function addSaveSub(){
	var dc = $DW.Form.getData("form2");
	var TextContent = $DW.$V("TextContent");
	if (TextContent == '') {
		Dialog.alert("首次关注回复内容不能为空！");
		return;
	}
	Server.sendRequest("com.sinosoft.cms.document.WxAutoReply.subSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
			}
		});
	});
}
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "新建微信图文";
	diag.URL = "weixin/WXAutoReplyDialog.jsp";
	diag.onLoad = function(){
		$DW.$("MenuAttribute").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }

	if (!check()) {
		return;
	}
	Server.sendRequest("com.sinosoft.cms.document.WxAutoReply.save",dc,function(response){
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
		Dialog.alert("请先选择要编辑的退款信息！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "修改微信图文";
	diag.URL = "weixin/WXAutoReplyDialog.jsp?ID="+dr.get("ID");
	diag.onLoad = function(){
		$DW.$("MenuAttribute").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑微信图文";
	diag.Message = "设置微信图文";
	diag.show();
}

function editSave() {
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	if (!check()) {
		return;
	}
	Server.sendRequest("com.sinosoft.cms.document.WxAutoReply.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function check(){

	var ResponseType = $DW.$V('ResponseType');
	
	if (ResponseType == 'text') {
		var TextContent = $DW.$V('TextContent');
		if (TextContent == '') {
			Dialog.alert("文本内容不能为空！");
			return false;
		}
	} else if (ResponseType == 'img') {
		var PicURL = $DW.$V('PicURL');
		if (PicURL == '') {
			Dialog.alert("图片链接不能为空！");
			return;
		} else {
			if (PicURL.length > 100) {
				Dialog.alert("图片链接过长！");
				return false;
			}
		}
	} else if (ResponseType == 'pic') {
		var PicTitle = $DW.$V('PicTitle');
		if (PicTitle == '') {
			Dialog.alert("图片标题不能为空！");
			return;
		} else {
			if (PicTitle.length > 225) {
				Dialog.alert("图片标题过长！");
				return false;
			}
		}
		var piclinkurl = $DW.$V('piclinkurl');
		if (piclinkurl == '') {
			Dialog.alert("图文素材链接不能为空！");
			return false;
		} else {
			if (PicTitle.length > 200) {
				Dialog.alert("图文素材链接过长！");
				return false;
			}
		}
	} else {
		Dialog.alert("请选择回复类型");
		return false;
	}
	return true;
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除该图文吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.document.WxAutoReply.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function doSearch(){
	var MenuAttribute = "";
	if($V("MenuAttribute") != "请输入关键字"){
		MenuAttribute = $V("MenuAttribute").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","MenuAttribute",MenuAttribute);
	DataGrid.setParam("dg1","PicTitle",$V("PicTitle").trim());
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'MenuAttribute'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("MenuAttribute") == "请输入关键字") {
		$S("MenuAttribute","");
	}
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />微信公众号图文列表</td>
            </tr>
            <tr>
            	<td valign="middle" class="blockTd">
            		<div style="float: left; white-space: nowrap;">
            		关键字(菜单KEY)：<input type="text" name="MenuAttribute"
						id="MenuAttribute" value="请输入关键字" onFocus="delKeyWord();" style="width:150px"> 
					图文标题(菜单名称)：<input type="text" name="PicTitle" id="PicTitle" style="width:150px"> 
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="doSearch()"></div></td>
            </tr>
			<tr>
				<td style="padding:0 8px 4px;">
					<z:tbutton onClick="add()"><img src="../Icons/icon018a2.gif" />新建</z:tbutton>
					<z:tbutton onClick="edit()"><img src="../Icons/icon018a4.gif" />编辑</z:tbutton>
                	<z:tbutton onClick="del()"> <img src="../Icons/icon018a3.gif" />删除</z:tbutton>
                	<z:tbutton onClick="editSub()"> <img src="../Icons/icon018a4.gif" />编辑首次关注自动回复</z:tbutton>
				</td>
            </tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <z:datagrid id="dg1" method="com.sinosoft.cms.document.WxAutoReply.dg1DataBind" size="15" lazy="true">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                  <tr ztype="head" class="dataTableHead">
                    <td  width="4%" ztype="RowNo"><b>序号</b></td>
                    <td width="3%" ztype="selector" field="ID">&nbsp;</td>
                    <td width="5%"><b>关键字KEY</b></td>
                    <td width="5%"><b>回复类型</b></td>
                    <td width="20%"><b>回复内容</b></td>
                    <td width="15%"><b>图文标题(菜单名称)</b></td>
                    <td width="15%"><b>图文图片地址</b></td>
                    <td width="15%"><b>图文地址</b></td>
                  </tr>
                  <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td title="${MenuAttribute}">${MenuAttribute}</td>
                    <td>${ResponseTypeName}</td>
                    <td title="${TextContent}">${TextContent}</td>
                    <td title="${PicTitle}">${PicTitle}</td>
                    <td title="${PicURL}">${PicURL}</td>
                    <td title="${PicLinkURL}">${PicLinkURL}</td>
                  </tr>
                  <tr ztype="pagebar">
                    <td colspan="5">${PageBar}</td>
                  </tr>
                </table>
              </z:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>
	</body>
</html>
