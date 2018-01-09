<html>
<head>
<title>开心保-保单生效提醒及事项提醒</title>
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
			<td  colspan="10" style="padding-top:10px;height:100px;"><b style="font-size:14px;">${MemberRealName}</b> <br>
				<p style="color:#222222; font-size:12px;">您购买的 <span style="color:#ff7600; font-size:12px; ">${ProductName}</span> ，将在明日生效。 保障期限：${SvaliDate} 至 ${EvaliDate} <br>	</p>
			</td>
		</tr>
		<tr>
			<td colspan="10">
				<span style="font-size:14px; ">订单详情：<br></br></span> 
				<table border="0" class="table-fsf" cellpadding="0" cellspacing="0" style="width:600px;margin-top:10px; margin-bottom:10px; border-collapse:collapse;">
					<tr>
						<td colspan="4" bgcolor="#caf5f9" style="padding-left:8px; text-align:left;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; color: #222222; " >订单号&nbsp;${OrderSn}</td>
					</tr>
					<tr>
						<td bgcolor="#f1fdfe" style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">投保人</td>
						<td bgcolor="#f1fdfe" style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">产品名称</td>
						<td bgcolor="#f1fdfe" style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">份数</td>
						<td bgcolor="#f1fdfe" style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">已支付金额</td>
						
					</tr>
					<tr>
						<td style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">${ApplicantName}</td>
						<td style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">${ProductName}</td>
						<td style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">${MulNum}</td>
						<td style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">￥${payPrice}</td>
					
					</tr>
				</table>
			</td>
		</tr>	
		<tr>
			<td colspan="10" >
				<table border="0" cellpadding="0" cellspacing="0">
	
					<td style="height:40px;width:600px; font-size:12px; "><p style="line-height:1.6em; font-size:12px;">
					请牢记您的保单号，将报案电话存入手机，以备不时之需。<br>
					开心保报案电话: <b style="font-size:12px; color:#ED6D00 ">4009-789-789</b>&nbsp;&nbsp;		
						</p>
						<p style="line-height:1.6em; font-size:12px; color:#888888">
							旅行中请注意妥善保管护照、签证、身份证、信用卡、机船车票等。<br>
	祝您旅途愉快，如有问题可及时联系开心保客服4009789789。
						</p></td>
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