<html>
<head>
<title>您已获得${parValueShow}优惠券,请查收！</title>
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
</style>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table id="__01" width="600"  border="0"  align="center" cellpadding="0" cellspacing="0">
		<#include "/wwwroot/kxb/block/MailTemplateHeaderNew.shtml">
		<tr>
			<td colspan="10" style="height:30px; width:600px;bgcolor:#ffffff;" bgcolor="#ffffff"></td>
		</tr>
		<tr>
			<td colspan="10" width="600px;" height="20px" bgcolor="#ffffff"><p style="font-size:20px; font-weight: bold;text-align: center;color:#ED6D00;">恭喜您！预约成功</p></td>
		</tr>
		<tr>
			<td colspan="10" width="600px;" height="75px" bgcolor="#ffffff">
				<p style="text-align: center; font-size:12px; color:#222222" >
 					稍候会有开心保保险专家和您联系，为您订制专属保险方案，我们不会泄露您的隐私 <br>
					为了表达您对我们信任的谢意，特赠送优惠券一张
				</p>
			</td>
		</tr>
		<tr>
			<td colspan="10" style="height:30px; width:600px;bgcolor:#ffffff;" bgcolor="#ffffff"></td>
		</tr>
		<tr>
			<td  colspan="10">
				<table border="0" cellpadding="0" cellspacing="0" style="border-top:1px solid #e8e8e8;border-left:1px solid #e8e8e8;border-right:1px solid #e8e8e8;border-bottom:1px solid #e8e8e8;">
					<tr>
						<td colspan="3" style="height:29px;bgcolor:#fafafa;" bgcolor="#fafafa"></td>
					</tr>
					<tr>
						<td style="width:150px;height:150px;bgcolor:#fafafa;" bgcolor="#fafafa"></td>
					<td style="width:320px;height:150px;bgcolor:#fafafa;" bgcolor="#fafafa">
								<table border="0"  style="width:320px;"  cellpadding="0" cellspacing="0">
									<tr><td colspan="3"><img src="http://resource.kaixinbao.com/active_file/images/email/yhjsf_04.gif" alt=""></td></tr>
									<tr>
										<td style="width:33px;height:85px;"><img src="http://resource.kaixinbao.com/active_file/images/email/yhjsf_07.gif" alt=""></td>
										<td bgcolor="#ffffff" style="width:209px;font-size:49px; font-weight: bold; color:#fe5555;text-align: center;  font-family: 'Microsoft YaHei'   " >${parValueShow}</td>
										<td style="width:78px;height:85px;"><img src="http://resource.kaixinbao.com/active_file/images/email/yhjsf_09to.gif" alt=""></td>
									</tr>
								</table>
								<table border="0" style="width:320px;" cellpadding="0" cellspacing="0">
									<tr>
										<td style="width:19px;height:30px;"><img src="http://resource.kaixinbao.com/active_file/images/email/yhjsf_10.gif" width="19px;" alt=""> </td>
										<td bgcolor="#ffac3c" style="width:140px;height:30px;font-size:12px; color:#ffffff;  text-align:left;">有效日期 </td>
										<td bgcolor="#ffac3c" style="width:167px;height:30px;font-size:12px; color:#ffffff; text-align:right;"><div style="width:167px; "  >${starttime} 至 ${endtime}</div></td>
										<td style="width:13px;height:30px;"><img src="http://resource.kaixinbao.com/active_file/images/email/yhjsf_13.gif" alt=""></td>
									</tr>
								</table>
					</td>
					<td style="width:130px;height:150px;" bgcolor="#fafafa"></td>
					</tr>
					<tr>
						<td colspan="3" style="height:20px;" bgcolor="#fafafa"></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center; font-size:12px;" bgcolor="#fafafa"><b style="font-size:14px; color:#fe5555; padding-bottom:8px;">亲爱的用户，您好！</b><p>恭喜您获得<span style="color:#fe5555;">${parValueShow}优惠劵</span>一张		 <br>
	有效时间：<span style="color:#fe5555;">${starttime}</span> 至 <span style="color:#fe5555;">${endtime}</span> <br>
	优惠劵号：<span style="color:#fe5555;">${couponsn}</span> <br>
	该优惠劵可在支付时使用，您也可以在会员中心查询您的优惠劵使用情况，谢谢！<br> </p> </td>
					</tr>
					<tr>
						<td colspan="3" style="height:29px;" bgcolor="#fafafa"></td>
					</tr>
				</table>
			</td>
		</tr>
		<#include "/wwwroot/kxb/block/MailTemplateMiddleNew.shtml">

	<#if (MailProductList?size > 0)>	
		<tr>
			<td colspan="10" style="height:22px; width:600px;" bgcolor="#ffffff"></td>
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