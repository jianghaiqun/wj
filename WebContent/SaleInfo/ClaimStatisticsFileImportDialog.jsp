<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<z:init
	method="com.sinosoft.cms.dataservice.CustomerServiceManage.uploadInit">
</z:init>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	function upload() {
		if (Verify.hasError()) {
			return;
		}
		$E.disable(window.Parent.$D.CancelButton);
		$E.disable(window.Parent.$D.OKButton);
		msg();
		$("divMsg").className = "ErrorMsg";
		$F("form2").submit();
	}
	var counter = 1;
	function msg() {
		var txt = "正在上传处理中，请不要关闭对话框...耗时";
		setInterval(function() {
			$("divMsg").innerHTML = "<font color=red>" + txt + counter
					+ "秒</font>";
			counter++
		}, 1000);
	}
	function onUploadCompleted(returnValue, errorMessage) {
		switch (returnValue) {
		case 1:
			//success
			$("divMsg").hide();
			$E.disable(window.Parent.$D.CancelButton);
			$E.disable(window.Parent.$D.OKButton);
			Dialog.alert(errorMessage, function() {
				try {
					window.Dialog.close();
				} catch (ex) {
				}
			});
			break;
		default:
			Dialog.alert(errorMessage, function() {
				try {
					//window.Dialog.close();
				} catch (ex) {

				}
			});
			break;
		}
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div style="display: none">
		<iframe name="formTarget" src="javascript:void(0)"></iframe>
	</div>
	<form id="form2"
		action="../Editor/filemanager/upload/ClaimStatisticsImportServlet"
		method="post" target="formTarget" enctype="multipart/form-data">
		<fieldset>
			<legend>
				<strong>导入数据</strong>
			</legend>
			<table id="Videotable0" width="100%" cellpadding="2">
				<tr>
					<td width="10%" align="right"><label></label></td>
					<td width="50%"><label>请选择Excel文件,上限20M</label></td>
				</tr>
				<tr>
					<td align="right"><label>1:</label></td>
					<td><input name='File' id='File' type='file'
						accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
						value='' size='30'></td>
				</tr>
				<TR>
					<TD colSpan=2 align=center><A
						href="../DataService/ADTemplate/claimStatisticsTemplate.xls">导入模板下载</A> </TD>
				</TR>
			</table>
		</fieldset>
	</form>
	<div id="divMsg" align="center"></div>
</body>
</html>
