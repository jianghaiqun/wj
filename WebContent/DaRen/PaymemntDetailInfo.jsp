<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>稿费明细</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../wwwroot/kxb/js/jquery-1.4.2.min.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function add() {
	var diag = new Dialog("Diag11");
	diag.Width = 600;
	diag.Height = 100;
	diag.Title = "添加稿费支付信息";
	diag.URL = "DaRen/PaymemntDetailInfoDialog.jsp?type=Add&authorDetailInfo_id="+$V("authorDetailInfo_id");
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加稿费支付信息";
	diag.show();
}
function addSave() {

	var dc = $DW.Form.getData("form4");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.daren.PaymemntDetailInfo.add", dc, function() {
		Dialog.endWait();
		var response = Server.getResponse();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("pubInfo_dginput");
				$D.close();
			}
		});
	});	
}

function edit() {
	var dt = DataGrid.getSelectedData("pubInfo_dginput");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的数据！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择1条信息修改！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag11");
	diag.Width = 600;
	diag.Height = 100;
	diag.Title = "修改稿费支付信息";
	diag.URL = "DaRen/PaymemntDetailInfoDialog.jsp?type=Modify&ID=" + dr.get("id")+"&authorDetailInfo_id="+$V("authorDetailInfo_id");
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改稿费支付信息";
	diag.show();
}

function editSave() {

	var dc = $DW.Form.getData("form4");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.daren.PaymemntDetailInfo.edit", dc, function() {
		Dialog.endWait();
		var response = Server.getResponse();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("pubInfo_dginput");
				$D.close();
			}
		});
	});	
}

function del() {
	var arr = DataGrid.getSelectedValue("pubInfo_dginput");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的数据！");
		return;
	}
	
	Dialog.confirm("删除后不可恢复，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.wangjin.daren.PaymemntDetailInfo.delete", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("pubInfo_dginput");
						}
					});
				});
	});
}

</script>
</head>
<body class="dialogBody" >
	<z:init method="com.wangjin.daren.PaymemntDetailInfo.init">
		<form id="form2">
			<table width="90%"  border="0">
				<tr>
					<td valign="middle">
						<table width="100%" height="50" align="center" cellpadding="2"
							cellspacing="0" border="0">
							<tr>
								<td align="center">贴子链接：${articleLink}</td>
							</tr>
							<tr>
								<td align="center">作者：${authorName}</td>
							</tr>
							<tr>
								<td align="center"><input type="hidden"
									id="authorDetailInfo_id" name="authorDetailInfo_id" value="${authorDetailInfo_id}" />
								</td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td>
						<z:tbutton onClick="add();"><img src="../Icons/icon005a2.gif" width="20" height="20" />添加</z:tbutton>
						<z:tbutton onClick="edit();"> <img src="../Icons/icon005a4.gif" width="20" height="20" />修改</z:tbutton>
						<z:tbutton onClick="del();"><img src="../Icons/icon005a4.gif" width="20" height="20" />删除</z:tbutton>
					</td>
				</tr>
			</table>
			<z:datagrid id="pubInfo_dginput"
				method="com.wangjin.daren.PaymemntDetailInfo.dg1DataBind" size="10" scroll="true">
				<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
					<tr ztype="head" class="dataTableHead">
						<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
						<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						<td width="90px"><strong>支付时间</strong>
						</td>
						<td width="70px"><strong>支付金额</strong>
						</td>
						<td width="70px"><strong>是否已支付</strong>
						</td>
						<td width="90px"><strong>创建时间</strong>
						</td>
						<td width="60px"><strong>创建者</strong>
						</td>
						<td width="90px"><strong>修改时间</strong>
						</td>
						<td width="60px"><strong>修改者</strong>
						</td>
					</tr>
					<tr  style="background-color:#F9FBFC">
						<td width="3%">&nbsp;</td>
						<td style="text-align:center;">&nbsp;<input name="id" type="hidden" id="id" value="${id}" /></td>
						<td>${payTime}</td>
						<td>${payPrice}</td>
						<td>${isPay}</td>
						<td>${CreateDate}</td>
						<td>${CreateUser}</td>
						<td>${ModifyDate}</td>
						<td>${ModifyUser}</td>
					</tr>
					<tr ztype="pagebar">
						<td colspan="9">${PageBar}</td>
					</tr>
				</table>
			</z:datagrid>
			
		</form>
	</z:init>
</body>
</html>
