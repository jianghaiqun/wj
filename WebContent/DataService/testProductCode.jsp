<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品编码列表接口测试</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function autoA() {
	Dialog.wait("正在同步......"); 
	fm.submit();
}
</script>

</head>
<body>
<iframe src="javascript:void(0);" name="targetFrame" width="0"
		height="0" frameborder="0"></iframe>
	<form name="fm" target="targetFrame" action="testProductCodeSave.jsp"
		method="post">
		<div id="StaffStat">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"
				style="border-collapse: separate; border-spacing: 6px;">
				<tr valign="top">
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="blockTable">
							<tr>
								<td width="100px">渠道：</td>
								<td width="350px"><input type="text" name="RiskProp"
									id="RiskProp" />（个险(I)、团险(G)、代理人(F)）</td>
							</tr>
							<tr>
								<td>保险公司编码：</td>
								<td><input type="text" name="SupplierCode"
									id="SupplierCode" />
								</td>
							</tr>
							<tr>
								<td>产品编码：</td>
								<td><input type="text" name="RiskCode" id="RiskCode"
									style="width: 300px" />(多个用半角逗号分隔)</td>

							</tr>
							<tr>
								<td><input name="importBtn" type="button"
									class="inputButton" id="importBtn"
									onClick="return autoA();" value="同步" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0"
			scrolling="auto" style="width: 100%; height: 100%; display: none;"></iframe>
	</form>
</body>
</html>