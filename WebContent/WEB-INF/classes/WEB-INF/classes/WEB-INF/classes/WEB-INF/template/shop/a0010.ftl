<html>
<head>
<title>开心保-订单待支付提醒</title>
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
	
	<#if (OrdersInfoList?size > 1)>
		<tr>
			<td colspan="10" style="padding-top:10px;height:100px;"><b style="font-size:14px;">${MemberRealName}</b> <br>
				<p style="color:#222222; font-size:12px;">您于${StandardDate}购买的产品订单已经确认。您可以点击下方“继续支付”按钮完成支付。</p></td>
		</tr>
	<#else>
		<tr>
			<td  colspan="10" style="padding-top:20px;height:100px;"><b style="font-size:14px;">${MemberRealName}</b> <br>
				<p style="color:#222222; font-size:12px;">您于${StandardDate}购买的 ${ProductName}，订单号（${OrderSn}）已经确认。<br>
	您可以点击下方“继续支付”按钮完成支付。</p></td>
		</tr>
	</#if>
		<tr>
			<td colspan="10" >
				<table border="0"  cellpadding="0" cellspacing="0" style="margin-bottom:20px; margin-top:10px; width:600px;">
					<tr>
						<td style="width:232px;"></td>
						<td style="width:118px;"><a href="${PayPath}"><img src="http://resource.kaixinbao.com/active_file/images/email/zf_03.gif" alt=""></a> </td>
						<td style="font-size:12px; width:250px;" >&nbsp;支付成功得积分，积分可抵值或换购商品</td>
					</tr>
					<tr>
						<td colspan="3" style="height:40px;font-size:12px;">若点击“继续支付”无效，您可复制下面网页地址到浏览器地址栏中打开：</td>
					</tr>
					<tr>
						<td colspan="3" style="padding-left:8px; width:600px; padding-top:8px;padding-right:8px;word-break:break-all; padding-bottom:8px; color:#888888; border-top:1px solid #e8e8e8; border-left:1px solid #e8e8e8; border-right:1px solid #e8e8e8; border-bottom:1px solid #e8e8e8; font-size:12px;" bgcolor="#fafafa">
							${PayPath}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="10" style="height:20px; width:600px;" bgcolor="#ffffff"></td>
		</tr>
		<tr>
			<td colspan="10">
				<span style="font-size:14px;">订单详情：<br></br></span>
				<#list OrdersInfoList as list>
				<table border="0" class="table-fsf"  cellpadding="0" cellspacing="0" style="width:600px;margin-top:10px; margin-bottom:10px; border-collapse:collapse;">
					<tr>
						<td colspan="3" bgcolor="#caf5f9" style="padding-left:8px; text-align:left;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; color: #222222; " >订单号&nbsp;${list.OrderSn}</td>
					</tr>
					<tr>
						<td bgcolor="#f1fdfe" style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222;">投保人</td>
						<td bgcolor="#f1fdfe" style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222;">产品名称</td>
						<td bgcolor="#f1fdfe" style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222;">份数</td>
						
					</tr>
					
					<tr>
						<td style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">${list.ApplicantName}</td>
						<td style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">${list.ProductName}</td>
						<td style="padding-left:8px;font-size:12px; border-top:1px solid #99d1d6;border-left:1px solid #99d1d6;border-right:1px solid #99d1d6;border-bottom:1px solid #99d1d6; height: 30px; text-align: center ; color: #222222; ">${list.MulNum}</td>
						
					</tr>
				</table>
				<br>
				</#list>
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