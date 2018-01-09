<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function sendMail(){
	var mail = $V("mail");
    if (mail == null || mail == '') {
    	Dialog.alert("邮箱不能为空！");
		return;
    }
    var content = $V("content");
    if (content == null || content == '') {
    	Dialog.alert("邮件内容不能为空！");
		return;
    }
	
	var dc = new DataCollection();
	dc.add("mail", mail);
	dc.add("content", content);
	dc.add("comCode", $V("comCode"));
	Dialog.wait("正在发送......"); 
	Server.sendRequest("com.wangjin.cms.orders.QueryOrders.sendClaimsData",dc,function(response){
		Dialog.closeEx();
		Dialog.alert(response.Message);
	});
}
</script>
</head>
<z:init>
<body class="dialogBody">
	<table width="100%" border="0" cellspacing="0" cellpadding="1"
		class="blockTable">
		<tr>
			<td valign="middle" class="blockTd"><img
				src="../Icons/icon018a6.gif" width="20" height="20" />发送理赔资料邮件信息</td>
		</tr>
		<tr>
			<td>
					<form id="form2">
						<table width="100%" cellpadding="0" cellspacing="0" align="center">
							<tr>

								<td height="25" align="right" class="tdgrey1" width="100px">邮箱：</td>
								<td height="25"><input name="mail" type="text"
									value="${mail}" id="mail" style="width: 150px" />
									<input name="comCode" type="hidden" value="${comCode}" id="comCode" />
								</td>
							</tr>
							<tr>
								<td height="25" align="right" class="tdgrey1">邮件内容：</td>
								<td height="25">
								  <textarea name="content" id="content" style="width:700px;height:100px"></textarea>
								</td>
								
							</tr>
							<tr>
							    <td style="padding-top: 3px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
							    	<z:tbutton onClick="sendMail();"><img src="../Icons/icon018a4.gif" />发送</z:tbutton> 
							    </td>
							</tr>
						</table>
						
					</form>
				</td>
		</tr>

	</table>
</body>
</z:init>
</html>