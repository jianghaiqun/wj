<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>合作商-新增</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<z:init method="com.sinosoft.cms.document.SuperPartnersManage.initAddDialog">
	<form id="form2">
		<table width="800" height="300" align="center" cellpadding="2"
			cellspacing="0">
			<tr>
				<td valign="top">
					<fieldset>
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">合作商编码：</td>
								<td>
									<input value="" type="text" id="partnerId" name="partnerId" class="inputText" size="20" verify="编码|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">合作商名称：</td>
								<td>
									<input value="" type="text" id="partnerName" name="partnerName" class="inputText" size="20" verify="名称|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">合作方私钥：</td>
								<td>
									<input value="" type="text" id="partnerKey" name="partnerKey" class="inputText" size="20" verify="私钥|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">合作状态：</td>
								<td>
									启用 <input type="radio" name="partnerStatus" value="0" checked="checked"> 停用 <input type="radio" name="partnerStatus" value="1">
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">支付方式：</td>
								<td>
									直付 <input type="radio" name="payType" value="0" checked="checked"> 预付 <input type="radio" name="payType" value="1">
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">联系电话：</td>
								<td>
									<input value="" type="text" id="telphone" name="telphone" class="inputText" size="11" />
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">渠道编号：</td>
								<td>
									<input value="" type="text" id="channelSn" name="channelSn" class="inputText" size="20" verify="渠道编号|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">前台成功回调URL：</td>
								<td>
									<input type="text" id="returnUrl" name="returnUrl" class="inputText" size="100" verify="前台成功回调URL|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">前台失败回调URL：</td>
								<td>
									<input type="text" id="returnErrorUrl" name="returnErrorUrl" class="inputText" size="100" verify="前台失败回调URL|NotNull"/>
								</td>
							</tr>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">后台回调URL：</td>
								<td>
									<input type="text" id="bgReturnUrl" name="bgReturnUrl" class="bgReturnUrl" size="100" verify="后台回调URL|NotNull"/>
								</td>
							</tr> 
						</table>
					</fieldset></td>
			</tr>
		</table>
	</form>
	</z:init>
</body>
</html>