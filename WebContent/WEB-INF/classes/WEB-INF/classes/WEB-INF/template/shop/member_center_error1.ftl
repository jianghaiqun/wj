<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>提示信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="error">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
  <div class="wrapper">
	
	<div class="body">
		<div class="errorBox">
			<div class="errorDetail">
				<div class="errorContent">
					投保人姓名或者手机号不正确！
				</div>
				<div class="errorUrl">
					点击此处<a href="${base}/shop/member!operationRegister.action?serialNO=${serialNO}" >返回</a>或回到<a href="${front}/">首页</a>
				</div>
			</div>
		</div>
	</div>
	
	 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</div>
</body>
</html>