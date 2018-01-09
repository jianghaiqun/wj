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
	<script type="text/javascript">
		/**
		 * 检查上传文件格式
		 */
		function getFileSuff() {
			var file = document.getElementById("importBaoquanInfo").value;
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
			var file = document.getElementById("importBaoquanInfo").value;
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
<form name="fm" target="targetFrame" action="BaoquanInfoUpLoadSave.jsp"
	  method="post" enctype="multipart/form-data"
	  onSubmit="return ImportExcel();">
	<div id="StaffStat">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			   class="blockTable">
			<tr height="50px">
			</tr>
			<tr valign="middle">
				<td width="10%"></td>
				<td nowrap><input type="file" style="width: 300px"
								  name="importBaoquanInfo" id="importBaoquanInfo"
								  onchange="getFileSuff();" />
					<input name="importBtn" type="button" class="inputButton" id="importBtn"
								onClick="return ImportExcel();" value="上  传" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td><A href="./ADTemplate/Import_baoquan.xls">模板下载</A>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>1，请按照模板要求填写评论相关信息</td>
			</tr>
			<tr>
				<td></td>
				<td>2，每次导入的数据最多为300条</td>
			</tr>
		</table>
	</div>
	<iframe name="InputorStat" id="InputorStat" frameborder="0"
			scrolling="auto" style="width: 100%; height: 100%; display: none;"></iframe>
</form>
</body>
</html>