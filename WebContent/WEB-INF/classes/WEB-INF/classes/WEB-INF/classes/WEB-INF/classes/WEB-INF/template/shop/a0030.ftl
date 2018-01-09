<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>开心保-保单成功登陆</title>
<meta name="Author" content="kaixinbao Team" />
<meta name="Copyright" content="kaixinbao" />
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
								<td style="padding-bottom: 15px; line-height:28px; padding-left:25px; padding-right: 0px; font-size: 12px; padding-top: 15px; color:#7D7D7D; "><br />
										您的保单(保单号如下)现已生效，您已经开始享有最专业的保险保障。<br />
								</td>
							</tr>
							
							<tr>
								<td>
									<table border="0" cellspacing="0" cellpadding="0" width="600px" align="center" style="border-top:1px solid  #BCCDE7; border-bottom:0px solid  #BCCDE7; border-left:1px solid  #BCCDE7; border-right:1px solid  #BCCDE7;">
										<tr>                                                                               
											<td  bgcolor="#D4E3F6" style="color:#666666; text-align: center;   border-bottom:1px solid #BCCDE7; font-size:12px;" align="center" height="35px;">订单号</td>
											<td  bgcolor="#D4E3F6" style="color:#666666;  text-align: center;  border-bottom:1px solid #BCCDE7; font-size:12px;" align="center">产品名称</td>
											<td  bgcolor="#D4E3F6" style="color:#666666; text-align: center;   border-bottom:1px solid #BCCDE7; font-size:12px;" align="center">被保人姓名</td>
											<td  bgcolor="#D4E3F6" style="color:#666666; text-align: center;   border-bottom:1px solid #BCCDE7; font-size:12px;" align="center">保单号</td>
										</tr>
										${detail}
									</table>
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
</body>
</html>