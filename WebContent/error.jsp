<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" 
	import="com.sinosoft.framework.*"
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>提示信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getServerContext()%>/wwwroot/kxb/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getServerContext()%>/template/shop/css/PayPromptSty.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getServerContext()%>/wwwroot/kxb/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getServerContext()%>/wwwroot/kxb/style/shop/css/new_shops.css"/>
</head>
<body class="error">
<%@ include file="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml" %>
<div class="wrapper">
	<div class="pay_error_box">
		<p class="error_heightling">出现错误!</p>
				<p class="error_fontdes">
			<%
				String result = request.getParameter("errormsg");
				if(com.sinosoft.framework.utility.StringUtil.isEmpty(result)){
					result = "未知异常!";
				}
				if(result.contains("carjs")){
					out.print("内容中含有非法字符!");
				}else{
				out.print(result);
				}
			%>
			</p>
		</div>
	<div class="bor_dsf"></div>
		    <div class="clear"></div>
</div>
<%@ include file="/wwwroot/kxb/block/community_v1.shtml" %>
<%@ include file="/wwwroot/kxb/block/kxb_footer_new_common.shtml" %>
</body>
</html>