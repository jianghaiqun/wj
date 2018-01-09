<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gbk" />
<title>待过期活动邮件提醒</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="kaixinbao" />
<style type="text/css">
p{font-size:14px;}
td{width:150px;height:20px;}
</style>
</head>
<body>
<p style="font-weight:bold">待过期活动邮件提醒</p>
<br>
以下活动马上过期，请注意处理相关产品，
注：操作步骤
       1、产品中心对该产品上架处理
       2、发布产品详细页
       3、发布相关产品所在栏目列表页
       4、同步筛选条件
       5、生成产品检索结果
<table border="1" cellSpacing="0" cellPadding="0">
	<tr>
		<td style = "text-align:center;font-size:12px;">活动ID</td>
		<td style = "text-align:center;font-size:12px;">活动描述</td>
		<td style = "text-align:center;font-size:12px;">活动起始时间</td>
		<td style = "text-align:center;font-size:12px;">活动结束时间</td>
	</tr>
	<#list ActiveInfoList as list>
		<tr>
			<td style = "font-size:12px;">&nbsp ${list.activitysn}</td>
			<td style = "font-size:12px;">&nbsp ${list.description}</td>
			<td style = "font-size:12px;">&nbsp ${list.starttime}</td>
			<td style = "font-size:12px;">&nbsp ${list.endtime}</td>
		</tr>
	</#list>
</table>
</body>
</html>