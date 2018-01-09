<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>帖子链接管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
	// 查询
	function doSearch() {
		DataGrid.setParam("dg3", Constant.PageIndex, 0);
		DataGrid.setParam("dg3","source",$V("source"));
		DataGrid.setParam("dg3","author",$V("author"));
		DataGrid.setParam("dg3","linkman",$V("linkman"));
		DataGrid.loadData("dg3");
	}

	// 新建
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 500;
		diag.Height = 280;
		diag.Title = "帖子信息-新增";
		diag.URL = "DaRen/LinkInfoAddDialog.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "帖子信息";
		diag.show();
	}

	// 新建保存
	function addSave() {
		var dc = $DW.Form.getData("AddForm");
		if ($DW.Verify.hasError()) {
			return;
		}
		// 退款类型
		var Platform = $DW.$V("Platform");
		if ("" == Platform) {
			Dialog.alert("来源不能为空");
			return;
		}
		
		Dialog.confirm("确认增加帖子信息？",function() { 
			Dialog.wait("正在生成退款信息......"); 
			Server.sendRequest("com.wangjin.daren.DataMiningManage.add",
				dc, function() {
					Dialog.closeEx();
					var response = Server.getResponse();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg3");
							$D.close();
						}
					});
				});
		});
	}

	// 删除
	function del(){
		var arr = DataGrid.getSelectedValue("dg3");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		Dialog.confirm("确定要删除吗？",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.wangjin.daren.DataMiningManage.del",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataGrid.loadData('dg3');
					}
				});
			});
		});
	}
	function upload(){
		var diag = new Dialog("Diag1");
		diag.Width = 330;
		diag.Height = 200;
		diag.Title = "导入帖子信息";
		diag.URL = "DaRen/LinkInfoImport.jsp";
	    diag.OKEvent=OK;
		diag.show();
	}

	function OK(){
		$DW.upload();
	}

	function onFileUploadCompleted(){
		DataGrid.setParam("dg3",Constant.PageIndex,0);
		DataGrid.loadData("dg3");
		if($D){
			setTimeout(function(){$D.close()}, 100);
		}
	}
</script>
</head>
<body>

			<fieldset>
				<legend><label>帖子信息管理</label></legend> 
	<table class="blockTable">
		<tr>
			<z:init method="com.wangjin.daren.AuthorDetailInfo.init">
			<td style="padding:4px 10px;">
				来源：<z:select style="width:80px;" name="source" id="source">${source}</z:select>
				联系人：<input name="linkman" type="text" id="linkman" value="" style="width:100px" />
				作者：<input name="author" type="text" id="author" value="" style="width:100px" />
				<input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton">
			</td>
			</z:init>
		</tr>
		<tr style="padding-top: 10px;"></tr>
		<tr>
			<td style="padding:4px 10px;">
			 <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建</z:tbutton>
			 <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
			 <z:tbutton onClick="upload()"><img src="../Icons/icon003a8.gif" width="20" height="20"/>导入</z:tbutton>
		</tr>
	</table>
			</fieldset>
	<br />
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<div style="overflow: auto;">
				<z:datagrid id="dg3"
					method="com.wangjin.daren.DataMiningManage.dg3DataBind" size="50"
					scroll="true" lazy="true">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="20" ztype="RowNo"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16">
							</td>
							<td width="15" ztype="selector" field="id">&nbsp;</td>
							<td width="80"><strong>来源</strong>
							</td>
							<td width="220"><strong>链接</strong>
							</td>
							<td width="60"><strong>作者</strong>
							</td>
							<td width="60"><strong>联系人</strong></td>
							</td>
							<td width="80"><strong>创建日期</strong>
							</td>
							<td width="80"><strong>创建人</strong>
							</td>
							<td width="120"><strong>备注</strong>
							</td>
						</tr>
						<tr style="background-color: #F9FBFC">
							<td width="3%">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Platform}</td>
							<td>${Link}</td>
							<td>${Author}</td>
							<td>${linkman}</td>
							<td>${AddTime}</td>
							<td>${AddUser}</td>
							<td title="${Remark}">${Remark}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="10" align="left">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>