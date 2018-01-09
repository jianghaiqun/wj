<html>
<head>
<title>开心保-会员等级升级到K1会员</title>
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
			<td  colspan="10" style="padding-top:10px;height:60px;"><b style="font-size:14px;">${MemberRealName}</b></td>
		</tr>
		<tr>
			<td colspan="10" >
				<table border="0" cellpadding="0" cellspacing="0">
	
					<td bgcolor="#fafafa" style="height:132px; width:600px;border:1px solid #e8e8e8; padding-left:20px;"><p style="line-height:1.8em; font-size:12px;">
						<span style="color:#222222; font-size:12px">您的会员等级已升级为 K1会员
						<#if (K1BirPoints)??>
						，生日当月您将获得<b style="color:#FF7400; font-size:12px">${K1BirPoints}</b>倍积分，您可以通过积分商城进行礼品兑换。
						</#if>
						</span> <br>
						<span>您距离K2等级会员还差
					<#if (BuyCount)??>
						<b style="color:#289DD5; font-size:12px">${BuyCount}</b>单<#if (BuyAmount)??>、</#if>
					</#if>
					<#if (BuyAmount)??><b style="color:#FF0000; font-size:12px">${BuyAmount}</b>元</#if>升级，
					升级为K2会员<#if (K2BirPoints)??>除生日<b style="color:#FF7400; font-size:12px">${K2BirPoints}</b>倍积分外，还</#if>将获得更多积分返还，可兑换更多礼品。
						</span>
					<br>
						<a href="${MemCenterUrl}" target="_blank" style="font-size:12px; color:#ed6d00;  border-bottom: 1px solid #ed6d00;">立即查看会员等级</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${JFUrl}" target="_blank" style="font-size:12px; color:#1670d1;  border-bottom: 1px solid #1670d1;">兑换礼品</a> 
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