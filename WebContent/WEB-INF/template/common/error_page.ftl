<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
</head>
<body class="error">
 <div class="wrapper">
	<#include "/wwwroot/kxb/block/kxb_header.shtml">
	<div class="body">
		<div class="errorBox">
			<div class="errorDetail">
				<div class="errorContent">
					${errorContent!"您的操作出现错误!"}
				</div>
				<div class="errorUrl">点击此处<a href="#" onclick="window.history.back(); return false;">返回</a>，或回到<a href="${front}/">首页</a></div>
			</div>
		</div>
	</div>
	 <#include "/wwwroot/kxb/block/kxb_footer.shtml">
</div>
</body>
</html>