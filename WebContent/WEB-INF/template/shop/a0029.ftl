<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>SMTP - Powered By SINOSOFT</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<style type="text/css">
img{ border:none;}
a {
	color: #63a426; text-decoration: none
}
a:hover {
	color: #e35b00; text-decoration: underline
}
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
<body>


<table  style="border-bottom: #D9D9D9 5px solid; border-left: #D9D9D9 5px solid; border-top: #D9D9D9 5px solid; border-right: #D9D9D9 5px solid"   border="0" cellspacing="10" cellpadding="0" width="640" align="center">
		<tbody>
			<#include "/wwwroot/kxb/block/MailTemplateHeader.shtml">
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" width="600" align="center">
						<tbody>
							<tr>
								<td style="padding-bottom: 0px; padding-left: 0px; padding-right: 0px; font-size: 12px; padding-top: 5px">
									${MemberRealName}
								</td>
							</tr>
							<tr>
								<td style="padding-bottom: 15px; line-height:28px; padding-left:25px; padding-right: 0px; font-size: 12px; padding-top: 15px; color:#7D7D7D;">您的订单<a href="${QueryOrdersUrl}" style="color:#3399CC;text-decoration: underline;font-size:12px; ">${OrderSn}</a>已支付成功！稍后您将收到保单信息。<br />恭喜您选到了合适的保险保障，感谢您选择开心保！<br />
								</td>
							</tr>
							<tr>
								<td style="border-top:1px solid #EAEAEA;" height="40px">
									<b style="font-size:12px;">&nbsp;&nbsp;&nbsp;&nbsp;订单信息：</b>
								</td>
							</tr>
							<tr>
								<td>
									<table border="0" cellspacing="0" cellpadding="0" width="590px;" align="center" style="border-top:1px solid  #BCCDE7; border-bottom:0px solid  #BCCDE7; border-left:1px solid  #BCCDE7; border-right:1px solid  #BCCDE7; font-size:12px;">
										<tr>                                                                               
											<td  bgcolor="#D4E3F6" style="color:#666666; text-align: center;   border-bottom:1px solid #BCCDE7; font-size:12px;" align="center" height="35px;">订单号</td>
											<td  bgcolor="#D4E3F6" style="color:#666666;  text-align: center;  border-bottom:1px solid #BCCDE7; font-size:12px;" align="center">产品名称</td>
											<td  bgcolor="#D4E3F6" style="color:#666666; text-align: center;   border-bottom:1px solid #BCCDE7; font-size:12px;" align="center">投保人姓名</td>
											<td  bgcolor="#D4E3F6" style="color:#666666; text-align: center;   border-bottom:1px solid #BCCDE7; font-size:12px;" align="center">保费</td>
										</tr>
										<tr>                                                       
											<td align="center" style="color:#999999;  border-bottom:1px solid  #BCCDE7;  text-align: center; font-size:12px;" height="48px;"><font style="color:#3399CC; ">${OrderSn}</font></td>
											<td align="center" style="color:#999999; border-bottom:1px solid  #BCCDE7;  text-align: center; font-size:12px; ">${ProductName}</td>
											<td align="center" style="color:#999999; border-bottom:1px solid  #BCCDE7;   text-align: center; font-size:12px;">${ApplicantName}</td>
											<td align="center" style="color:#F15717; border-bottom:1px solid  #BCCDE7;  text-align: center; font-size:12px; font-family:'Microsoft YaHei' ">￥${paidOrdAmt}</td>
										</tr>

									</table>
								</td>
							</tr>
							<tr>
								<td height="35px" style="color:#666666; font-size:12px;">
									&nbsp;&nbsp;&nbsp;&nbsp;本次交易您获得了 <a  href="${QueryPointUrl}" style="color:#3399CC;    text-decoration: underline;" target="_blank">${Points}</a> 个积分，可登陆开心保网进入会员中心查看。
								</td>
							</tr>
						    
						    <#list MailActivityList as list>
							
							<tr>
								<td height="280px" align="center">
									<a href="${list.ImageLink}" target="_blank">
										<img src="${list.ImagePath}" alt="${list.ImageDesc}" width="590px;" height="260px" />
									</a>
								</td>
							</tr>

						    </#list>
						    
							  <#if (MailProductList?size > 0)>
							  <tr>
								<td>
									<a href="${ProductCategory}" target="_blank">
										<img src="${front}/image/mored_03.gif" width="590px;" height="26px;" alt="查看更多" border="0" />
									</a>
								</td>
							</tr>
						    <tr><td height="250px;">
									<table border="0" cellspacing="0" cellpadding="0" width="100%;">
										<tr>
											<#list MailProductList as list>
											<td align="center"><a href="${list.Url}" target="_blank"><img width="190" height="190" alt="${list.ProductName}" src="${list.LogoLink}" style=" vertical-align: middle; border:0px; "></a>
											<table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td colspan="2" height="24px;"><a href="${list.Url}" target="_blank" style="color:#666666;font-size:12px; text-decoration: none; ">${list.ProductName}</a></td>
											
												</tr>
												<tr>
													<td><font style="color:#FF0000;font-family:'Microsoft YaHei' ;font-size:12px;">￥${list.InitPrem}</font></td>
													<td align="right"><a href="${list.Url}" style="color:red;font-size:12px; text-decoration: none; ">去看看></a></td>
												</tr>
											</table>
											</td>
											</#list>
										</tr>
									</table>
							</td></tr>
								</#if>
							<#include "/wwwroot/kxb/block/MailTemplateFooter.shtml">
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<div></div>
</body>
</html>