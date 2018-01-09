<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分兑换说明详情页</title>
<!--redesign/re_header.css头部脚部全站样式-->
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body>
  <s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span> <a
				href='<%=Config.getFrontServerContextPath()%>' target='_self'><span
				class="orange">您现在的位置：首页</span>
			</a><span class="separator">> </span><a href='member_center!index.action'
				target='_self'>会员中心</a> <span class="separator">></span><a href='member_center!index.action'
				target='_self'>会员中心首页</a> <span class="separator">></span><span
				class="separator1">常见问题</span>
		</div>
		<div class="member_con">
			<s:include value="/wwwroot/kxb/block/kxb_member_center_left.shtml"></s:include>
			<div class="member_right bor_sild  "  id="right_height">
				<h2 class="member_t_h2" id="showTitle"><s:property value="#request.showTitle"/></h2>
				<div class="qa_consf" id="showContent">
					<s:property value="#request.showContent" escape="false"/>
				</div>
				<input type="hidden" id="fullDegree"
						value="<%=session.getAttribute("fullDegree")%>">
				<div class="qa_list  bor_sild ">
					<h3 class="jf_hottitle">其他常见问题
						<ul class="fl_list">
							<s:iterator id="list1" value="#request.fenlei">
								<li><span><s:property value="#list1.name"/></span></li>
							</s:iterator>
							<li class="select"><span>全部</span></li>
						</ul>
						<input type="hidden" id="pointDescFenlei" value="全部"/>
					</h3>
					<ul class="member_qa_list" id="pointDescInfo">
						<s:iterator id="list" value="#request.info">
						<li class="member_qa_li"><a href="javascript:void(0);" onclick="setPointContent('<s:property value="#list.Title"/>','<s:property value="#list.Content"/>')"><s:property value="#list.Title"/></a>
						</li>
						</s:iterator>
					</ul>
					<div class="vip_page" id="pageInfo">
					<s:iterator id="list1" value="#request.pageList">
					<span class="<s:property value="#list1.choose"/>" id="page<s:property value="#list1.no"/>"><a href="javascript:void(0);" onclick="loadPointDesc(<s:property value="#list1.no"/>);"><s:property value="#list1.no"/></a></span>
					</s:iterator>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
		
		
<script type="text/javascript">
jQuery(document)
		.ready(function() {
	pointDescChange();	
});
</script>
</body>
</html>