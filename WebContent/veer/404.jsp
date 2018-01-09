<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.sinosoft.framework.*,com.sinosoft.framework.utility.StringUtil,com.sinosoft.cms.pub.PublicHead"%>
<%
	String frontServerContextPath = Config.getFrontServerContextPath();
%>
<jsp:include page="/wwwroot/kxb/block/kxb_custom_header.shtml"></jsp:include>
<div class="wrapper"></div>
<!--  主体 开始 -->
<div class="wrap10">
<div class="error_404box">
<div class="error_img"></div>
<div class="error_des">
<p>很抱歉，您访问的页面没有找到，您可以：</p>
<ul class="error_list">
	<li>1.请检查您的网址是否有误</li>
	<li>2.返回开心保<a href="<%=frontServerContextPath %>">首页</a>；</li>
	<li>3.联系<a
		href="javascript:void(0);" vlpageid="xiaoneng" exturl="http://www.kaixinbao.com/xiaoneng" id="qqwap2" onclick="return(VL_FileDL(this));return false;">在线客服</a>或者致电<em>4009-789-789</em></li>
</ul>

</div>
</div>
</div>
<div class="clear"></div>
<!--底部 开始-->
<jsp:include page="/wwwroot/kxb/block/community_v1.shtml"></jsp:include>