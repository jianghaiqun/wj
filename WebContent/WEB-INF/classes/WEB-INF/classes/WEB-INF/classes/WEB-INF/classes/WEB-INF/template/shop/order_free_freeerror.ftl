<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${shopStaticPath}/template/shop/css/PayPromptSty.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/new_shops.css"/>
</head>
<body class="error">
  <div class="wrapper">
				<div class="pay_error_box">
				<p class="error_heightling">出现错误!</p>
				<p class="error_fontdes">
					<#if (errorMessages?size > 0)!>
						<#list errorMessages as list>${list}<br></#list>
					<#elseif (actionMessages?size > 0)!>
						<#list actionMessages as list>${list}<br></#list>
					<#elseif (fieldErrors?size > 0)!>
						<#list (fieldErrors?keys)! as key>
							${fieldErrors[key]?replace('^\\[', '', 'r')?replace('\\]$', '', 'r')}<br>
						</#list>
					<#else>
						出错啦～～人太多，系统太繁忙，我们努力加载中，请您稍等!
					</#if>
					<br/>
					<#if (typeFlag !="02")!>
						<#if redirectionUrl??>
							点击<a href="${redirectionUrl}"><font class="error_font">确定</font></a>，或<a href="javascript:void(0);" onclick="window.history.back(); return false;"><font class="error_font">返回</font></a>
						<#else>
							点击此处<a href="javascript:void(0);" onclick="window.history.back(); return false;"><font class="error_font">返回</font></a>或回到<a href="${front}/"><font class="error_font">首页</font></a>
						</#if>
					</#if> 
				</p>
				</div>				
				<div class="bor_dsf"></div>
		    <div class="clear"></div>
</div>
</body>
</html>