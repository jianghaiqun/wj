<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信活动管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>
<script>
	function doSearch() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "name", $V("name"));
		DataGrid.loadData("dg1");
	}

	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 800;
		diag.Height = 500;
		diag.Title = "新增微信活动";
		diag.URL = "weixin/WXSetNewActivity.jsp";
		diag.onLoad = function() {
			//$DW.$("WXSetNewActivity").focus();
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "新建微信活动信息";
		diag.show();
	}

	function addSave() {
		if ($DW.Verify.hasError()) {
			return;
		}
		var dc = $DW.Form.getData("form2");
		var dt = getDataTable();
		//$DW.DataGrid.getAllData("dg2");
		dc.add("Data", dt);
		Server.sendRequest("com.sinosoft.cms.document.WXActivity.save", dc,
				function() {
					var response = Server.getResponse();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							$D.close();
							DataGrid.loadData('dg1');
						}
					});
				});
	}
	
	function getDataTable() {
		var dg = $DW.$("dg2");
		var dt = dg.DataSource;
		var names = [ "id", "productImage", "productUrl"];
		var map = {};
		for ( var i = 0; i < names.length; i++) {
			var eles = $DW.$N(names[i]);
			var arr = [];
			if (eles != null) {
				for ( var j = 0; j < eles.length; j++) {
					arr.push($DW.$V(eles[j]));
				}
			}
			map[names[i]] = arr;
		}
		for ( var i = 0; i < dt.getRowCount(); i++) {
			for ( var j = 0; j < names.length; j++) {
				dt.Rows[i].set(names[j], map[names[j]][i]);
			}
		}
		return dt;
	}
	
	function edit() {
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要编辑的行！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("只能修改1条信息！");
			return;
		}
		dr = drs[0];
		var diag = new Dialog("Diag1");
		diag.Width = 800;
		diag.Height = 500;
		diag.Title = "微信活动修改";
		diag.URL = "weixin/WXActivityEdit.jsp?id=" + dr.get("id");
		diag.onLoad = function() {
			//$DW.$("WXactivityEdit").focus();
		};
		diag.OKEvent = editSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "修改微信活动信息";
		diag.show();
	}
	function editSave() {
		if ($DW.Verify.hasError()) {
			return;
		}
		var dc = $DW.Form.getData("form3");
		var dt = getDataTable();
		//$DW.DataGrid.getAllData("dg2");
		dc.add("Data", dt);
		Server.sendRequest("com.sinosoft.cms.document.WXActivity.edit", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							$D.close();
							DataGrid.loadData("dg1");
						}
					});
				});
	}

	function stop() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		Dialog.confirm("确定要删除该活动吗？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.sinosoft.cms.document.WXActivity.stop", dc,
					function(response) {
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData('dg1');
							}
						});
					});
		});
	}
	function del() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		Dialog.confirm("确定要删除该活动吗？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.sinosoft.cms.document.WXActivity.del", dc,
					function(response) {
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData('dg1');
							}
						});
					});
		});
	}
	
	function docopy(obj){
		var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		var isOpera = userAgent.indexOf("Opera") > -1;
		if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
       		obj.select();
 			js=obj.createTextRange();
 			js.execCommand("Copy")
 			alert("复制成功!");
   		}else{
    		alert("请在IE浏览器下使用复制功能。");
    	}	
	} 
	
	
</script>

</head>
<body>
	<z:init>
		<table width="100%" border="0" cellspacing="6" cellpadding="0"
			style="border-collapse: separate; border-spacing: 6px;">
			<tr valign="top">
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="6"
						class="blockTable">
						<tr>
							<td valign="middle" class="blockTd"><img
								src="../Icons/icon018a6.gif" />微信活动列表</td>
						</tr>

						<tr>
							<td style="padding: 8px 10px;">
								<div style="float: right">
									活动名称：<input name="name" type="text" id="name" value=""
										style="width: 90px"> <input type="button"
										name="Submit" value="查询" onClick="doSearch()">
								</div> 
								<z:tbutton onClick="add()"> <img src="../Icons/icon021a2.gif" /> 新建</z:tbutton> 
								<z:tbutton onClick="edit()"> <img src="../Icons/icon021a4.gif" />修改</z:tbutton> 
								<z:tbutton onClick="stop()"> <img src="../Icons/icon021a4.gif" />停用</z:tbutton> 
								<z:tbutton onClick="del()"> <img src="../Icons/icon021a4.gif" />删除</z:tbutton>
							</td>
						</tr>
						<tr>
							<td
								style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
								<z:datagrid id="dg1"
									method="com.sinosoft.cms.document.WXActivity.dg1DataBind"
									size="15" lazy="true">
									<table width="100%" cellpadding="2" cellspacing="0"
										class="dataTable">
										<tr>
											<td></td>
										</tr>
										<tr ztype="head" class="dataTableHead">
											<td width="4%" ztype="RowNo">序号</td>
											<td width="4%" ztype="selector" field="ID">&nbsp;</td>
											<td><b>活动id</b></td>
											<td><b>活动名称</b></td>
											<td><b>活动状态</b></td>
											<td><b>活动开始时间</b></td>
											<td><b>活动截止时间</b></td>
											<td><b>活动类型</b></td>
											<td><b>创建时间</b></td>
											<td><b>创建人</b></td>
											<td><b>活动页面</b></td>

										</tr>
										<tr ondblclick="edit();">
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>${id}</td>
											<td>${name}</td>
											<td>${status}</td>
											<td>${startTime}</td>
											<td>${stopTime}</td>
											<td>${type}</td>
											<td>${createDate}</td>
											<td>${createStaff}</td>
											<td><a href="${prop1}" target="_blank" title="${prop1}"><img src="../Icons/icon403a3.gif"/></a>
											<input type="button" onclick="docopy(this)" value="${prop1}"></td>
											
										</tr>
										<tr ztype="pagebar">
											<td colspan="10" align="center">${PageBar}</td>
										</tr>
									</table>
								</z:datagrid>
							</td>
						</tr>
					</table> </z:init>
</body>
</html>