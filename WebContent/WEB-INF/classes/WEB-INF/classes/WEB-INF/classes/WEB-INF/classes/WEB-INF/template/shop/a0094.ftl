<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>（订单编号）${orderSn}待支付提醒</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="kaixinbao" />
<style type="text/css">
img{ border:none;}
</style>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table id="__01" width="630" height="360" border="0" cellpadding="0" cellspacing="0" style="border:5px solid #e2e2e2;">
	<tr>
		<td colspan="5"></td>
	</tr>
	<tr>
		<td rowspan="6">&nbsp;</td>
		<td style="height:79px;">
			<a href="${front}"><img src="${front}/images/email_03.jpg" width="213" height="79" alt="开心保保险网，一站式服务，省钱更省心"></a>
		</td>
		<td style="">&nbsp;</td>
		<td style="padding-top:25px;text-align:right; padding-right:25px; ">
	  		<span style=" padding-left:45px;color:#ed6d00; font-size:18px; line-height:20px;  padding-left:25px;  font-weight:bold; font-family:"微软雅黑",Verdana, arial, helvetica, sans-serif;">亲爱的客服${name}，您好！</span>
	  	</td>
		<td rowspan="6">	</td>
	</tr>
	<tr>
		<td colspan="3">
			<img src="${front}/images/email_07.jpg" width="680" height="1" alt="">
		</td>
	</tr>
	<tr>
		<td colspan="3" style="padding-bottom:5px;">	
     		<p style="padding:0 90px; font-size:12px; color:#7d7d7d; text-align:left; line-height:1.9em;">        订单编号：<span style="font-weight:bold">${orderSn}</span>，产品名称：<span style="font-weight:bold">${productName}</span>
     			计划：<span style="font-weight:bold">${planName}</span>，保费：<span style="font-weight:bold">${productprice}</span>，订单创建时间：<span style="font-weight:bold">${createDate}</span>，投保人姓名：<span style="font-weight:bold">${applicantName}</span>，性别：<span style="font-weight:bold">${applicantSex}</span>，年龄：<span style="font-weight:bold">${applicantAge}</span>，被保险人数：<span style="font-weight:bold">${num}</span>，电话：<span style="font-weight:bold">${applicantMobile}</span>，邮箱：<span style="font-weight:bold">${applicantMail}</span>。
     			</br>
     			订单目前为待支付状态，请及时与客户取得联系！
			</p>
        </td>
	</tr>
	<tr>
		<td colspan="3">
			<img src="${front}/images/email_09.jpg" width="680" height="1" alt="">
		</td>
	</tr>
	<tr>
		<td colspan="3" style="height:75px;">
			<p style=" color:#666; font-size:12px; line-height:24px; padding:0 35px; text-align:center;">客服电话：4009-789-789  &nbsp;     客服邮箱：<a href="mailto:kf@kaixinbao.com">kf@kaixinbao.com </a>   &nbsp;   开心保官网：<a href="http://www.kaixinbao.com" target="_blank">www.kaixinbao.com</a><br>
				如有任何疑问或意见建议，欢迎您随时与我们联系
			</p>
		</td>
	</tr>
	<tr>
		<td colspan="3"></td>
	</tr>
</table>
</body>
</html>