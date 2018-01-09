<%
	/* 撤单信息导入画面
	 */
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>撤单信息导入</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
/**
 * 检查上传文件格式 
 */
function getFileSuff() {
	var file = document.getElementById("importCommentFile").value;
	var ss = file.split(".");
	if (ss.length == 2) {
		if (ss[1] == "xls") {
			document.getElementById("importBtn").disabled = false;
			return true;
		} else {
			Dialog.alert("请上传扩展名为xls的文件！");
			document.getElementById("importBtn").disabled = true;
		}
	} else {
		Dialog.alert("请上传扩展名为xls的文件！");
		document.getElementById("importBtn").disabled = true;
	}
	return false;
}

/**
 * 上传按钮的处理
 * @returns {Boolean}
 */
function ImportExcel() {
	var file = document.getElementById("importCommentFile").value;
	if (file == null || file == '') {
		Dialog.alert("请选择上传的文件！");
		return false;
	}
	
	Dialog.confirm("确认要导入？",function() { 
		Dialog.wait("正在生成......"); 
		fm.submit();
	});
}
</script>
</head>
<body>
	<iframe src="javascript:void(0);" name="targetFrame" width="0"
		height="0" frameborder="0"></iframe>
	<form name="fm" id="fm"  target="targetFrame" action="SurrenderDataFileUpload.jsp"
		method="post" enctype="multipart/form-data"
		onSubmit="return ImportExcel();">
		<div id="StaffStat">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"
				style="border-collapse: separate; border-spacing: 6px;">
				<tr valign="top">
					<td colspan=2>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="blockTable">
							<tr height="20px">
							</tr>
							<tr valign="middle">
								<td nowrap><input type="file" style="width: 300px"
									name="importCommentFile" id="importCommentFile"
									onchange="getFileSuff();" /> <input name="importBtn"
									type="button" class="inputButton" id="importBtn"
									onClick="return ImportExcel();" value="上  传" /></td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td><A href="surrender_importData_20171123.xls">撤单信息导入模板下载</A></td>
								<td></td>
							</tr>
							<tr>
							</tr> 
							<tr height='20px'>
								<td></td>
								<td></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td colspan=2 style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.SurrenderDataUpLoad.dg1DataBind" size="15" lazy="true" autoFill="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="50px" ztype="RowNo"><strong>序号</strong></td>
								<td width="120px"><b>撤单状态</b></td>
								<td width="200px"><b>保单号</b></td>
								<td width="500px"><b>备注</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
								<td>&nbsp;</td>
								<td title="${status}">${status}</td>
								<td title="${policyNo}">${policyNo}</td>
								<td title="${remark}">${remark}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>