<html>
<head>
<title>开心保-保单查看通知</title>
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
			<td  colspan="10" style="padding-top:20px;" height="80px;"><b style="font-size:14px;">${MemberRealName}</b> <br>
				<p style="color:#222222; font-size:12px;">感谢您选择“${ProductName}”<br>附件为您的电子保单，上面记载了详细的保障信息，请您仔细阅读并妥善保存。</p><p>
					如遇到问题，可致电开心保24小时客服电话：4009789789，立即报案，开心保协助理赔将指 <br>导流程、所需资料，竭诚为您服务。<br><br>
				</p></td>
		</tr>
		<tr>
			<td colspan="10" >
				<table border="0"  cellpadding="0" cellspacing="0" style="width:600px;margin-bottom:10px; margin-top:10px; border-top:1px dashed #e5e5e5; padding-top:20px; ">
					
					<tr>
						<td><b style="font-size:14px;">协助理赔流程</b> </td>
					</tr>
					
					<tr>
						<td>
							<table border="0"  cellpadding="0" cellspacing="0" style="width:600px;margin-top:10px; margin-bottom:10px;">
								<tr>
									<td bgcolor="#ffa457" style="width:144px;height:54px;color:#ffffff; font-size:14px; text-align: center;">致电开心保报案 <br>4009-789-789</td>
									<td bgcolor="#ffffff" style="width:9px;height:54px;"></td>
									<td bgcolor="#ffa457" style="width:144px;height:54px;color:#ffffff; font-size:14px; text-align: center;">领取理赔申请表 <br>按指导准备资料</td>
									<td bgcolor="#ffffff" style="width:9px;height:54px;"></td>
									<td bgcolor="#ffa457" style="width:144px;height:54px;color:#ffffff; font-size:14px; text-align: center;">提交资料至保险公司</td>
									<td bgcolor="#ffffff" style="width:9px;height:54px;"></td>
									<td bgcolor="#ffa457" style="width:144px;height:54px;color:#ffffff; font-size:14px; text-align: center;">等待保险公司审核</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td  style=" font-size:12px;" >• 审核通过，理赔款直接赔付到您的银行卡，<br>
	• 若审核不通过，将会短信或书面通知拒赔及理由。</td>
					</tr>
				</table>
			</td>
		</tr>
		<#include "/wwwroot/kxb/block/MailTemplateMiddleNew.shtml">

	<#if (MailProductList?size > 0)>	
		<tr>
			<td colspan="10" style="height:22px; width:600px;bgcolor:#ffffff;"></td>
		</tr>
		<tr>
			<td colspan="10">
				<table border="0"  cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="6" style="height:32px; width:166px;bgcolor:#ffffff;font-size:14px; color:#222222;">
							您可能感兴趣的商品：</td>
						<td colspan="16" style="height:32px; width:356px;bgcolor:#ffffff;"></td>
						<td style="height:32px; width:78px;">
							<a href="${ProductCategory}" style="font-size:14px; color:#555555;">查看更多&gt;</a></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="10" style="border-top:1px solid #e8e8e8;border-left:1px solid #e8e8e8;border-right:1px solid #e8e8e8; width:600px; height:24px; "></td>
		</tr>
		<tr>
			<td colspan="10" style="border-right:1px solid #e8e8e8;">
				<table border="0"  cellpadding="0" cellspacing="0" >
					<tr>
					<#list MailProductList as list>
						<td style="border-left:1px solid #e8e8e8; width:198px; height:116px;">
							<table border="0"  cellpadding="0" cellspacing="0" width="198px;">
								<tr><td style="width:20px;"></td><td colspan="2" style="height:48px;">
								<table border="0"  cellpadding="0" cellspacing="0">
									<tr>
										<td style="width:103px; height:46px; border:1px solid #e8e8e8;">
										<a href="${list.Url}" target="_blank"><img src="${list.ComanyPic}" width="103px" style="width:103px; " alt="${list.ProductName}"></a>
										</td>
									</tr>
								</table>
								
								</td><td style="width:20px;"></td></tr>
								<tr><td style="width:20px;"></td><td colspan="2" style="height:40px;"><a href="${list.Url}" target="_blank" style="color:#888888; font-size:12px;">${list.ProductName}</a></td><td style="width:20px;"></td></tr>
								<tr><td style="width:20px;"></td><td style="width:97px;font-size:18px;color:#ef8642"><em style="font-family:'Microsoft YaHei';font-size:15px; font-style: normal;">￥</em>${list.InitPrem}</td><td style="width:62px;"><a href="${list.Url}"><img src="http://resource.kaixinbao.com/active_file/images/email/btn_17.gif" alt="去看看"></a></td> <td style="width:20px;"></td></tr>
							</table>
						</td>
					</#list>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="10" style="height:25px; width:600px;border-bottom:1px solid #e8e8e8;border-left:1px solid #e8e8e8;border-right:1px solid #e8e8e8"></td>
		</tr>
	</#if>

	<#if (MailActivityList?size > 0)>
		<tr>
			<td colspan="10" style="height:30px; width:600px;bgcolor:#ffffff;"></td>
		</tr>
		<#list MailActivityList as list>
		<tr>
			<td colspan="10">
			<a href="${list.ImageLink}" target="_blank"><img width="600" height="276" style="width:600px; height:276px; " alt="${list.ImageDesc}" src="${list.ImagePath}"></a></td>
		</tr>
		</#list>
	</#if>
		<#include "/wwwroot/kxb/block/MailTemplateFooterNew.shtml">
	</table>
</body>
</html>