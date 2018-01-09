<html>
<head>
<title>开心保-用户激活提醒</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
	body{ background:#ffffff;}
	a { outline:none; color: #63a426; text-decoration: none; }
	a{blr:expression_r(this.onFocus=this.blur());outline:none;}
a:hover {color: #e35b00; text-decoration: underline}
td {
	font: 12px/1.5 宋体; color: #4e4e4e
}
font {
	font: 12px/1.5 宋体; color: #4e4e4e
}
a:focus {
	outline-style: none; outline-color: invert; outline-width: medium
}
img {
	border-bottom: medium none; border-left: medium none; border-top: medium none; border-right: medium none
}
.table-fsf{ border-collapse:collapse;}
.table-fsf td{ border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 40px; text-align: center ; color: #222222;  border-collapse:collapse;}
</style>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table id="__01" width="600"  border="0"  align="center" cellpadding="0" cellspacing="0">
	<#include "/wwwroot/kxb/block/MailTemplateHeaderNew.shtml">
		<tr>
			 <td colspan="10" style="padding-top:10px;height:100px;"><b style="font-size:14px;">亲爱的会员:</b> <br>
			 <br>${massage}</br> 
		         <br> 若点击“${sendWay}”无效，您可复制下面网页地址到浏览器地址栏中打开：</br>
		</tr>
		<td colspan="3" style="padding-left:8px; width:600px; padding-top:8px;padding-right:8px;word-break:break-all; padding-bottom:8px; color:#888888; border-top:1px solid #e8e8e8; border-left:1px solid #e8e8e8; border-right:1px solid #e8e8e8; border-bottom:1px solid #e8e8e8; font-size:12px;" bgcolor="#fafafa">
			${productUrl}
		</td>
		<#include "/wwwroot/kxb/block/MailTemplateMiddleNew.shtml">
		<#include "/wwwroot/kxb/block/MailTemplateFooterNew.shtml">
	</table>
</body>
</html>