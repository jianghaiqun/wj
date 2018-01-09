<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
function getContent(){
	var content = UE.getEditor('content').getContent()
	if (content == '' || content == null) {
		return '';
	}
	return content;
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.wangjin.payment.OnlineRevisitContent.initDialog">
<form id="form1">
		<table width="750" height="200" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
				<table>
				 	<tr>
				 	 	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">在线回访内容：</td>
						<td>
							<input type="hidden" id="productId" name="productId" value="${productId}" />
							<textarea id="content" name="content" style="height:300px;width:600px;">${content}</textarea>
							<script type="text/javascript">var ue = UE.getEditor("content",{  
				                //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  
				                toolbars:[['source','forecolor','link']]});
							</script>
						</td>
				 	</tr>
				</table>	     
			</td>
	    </tr>
	    </table>
	</form>
</z:init>
</body>
</html>