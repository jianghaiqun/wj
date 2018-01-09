<!DOCTYPE html>
<%@ page session="false" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  
import="com.sinosoft.framework.*" import="com.sinosoft.framework.data.*" 
import="com.sinosoft.search.*" import="com.sinosoft.framework.utility.*"%>
<%@ taglib uri="controls" prefix="z"%>
<%
	request.setCharacterEncoding(Constant.GlobalCharset);
	response.setContentType("text/html;charset=utf-8");
	String riskcodes = "";
	String comCode = request.getParameter("comCode");
	String claType = request.getParameter("claType");
	String query = request.getParameter("query");
	String oldquery = request.getParameter("oldquery");
	if (StringUtil.isNotEmpty(query)) {
		query = java.net.URLDecoder.decode(query, "utf-8");
		if (null != oldquery) {
			oldquery = java.net.URLDecoder.decode(oldquery, "utf-8");
		}
	}
	if (query != null) {
		query = query.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
		if ("请输入保险公司名称、关键词搜索".equals(query)) {
			query = "";
		}
	}
	query = StringUtil.isEmpty(query) ? "" : StringUtil
			.escapeSpecialLetter(query);
	if (StringUtil.isEmpty(oldquery)) {
		oldquery = query;
	} else {
		oldquery = StringUtil.escapeSpecialLetter(oldquery);
	}
	
	StringBuffer LetterSb = new StringBuffer();
	String regex = "[+\\-&|!(){}\\[\\]^\"~*?:(\\)]";
	java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
	java.util.regex.Matcher matcher = pattern.matcher(query);
	while(matcher.find()){
	    matcher.appendReplacement(LetterSb, "\\\\"+matcher.group());
	}
	matcher.appendTail(LetterSb);
	query = LetterSb.toString();
	int pageSize = 15;
	int pageIndex = 0;
	DataTable dt = ClaimsInfoSearch.claimsInfo(comCode, claType, query);
	int total = 0;
	if (dt != null && dt.getRowCount() > 0) {
		total = dt.getRowCount();
	}
	int pageCount = new Double(Math.ceil(total * 1.0 / pageSize)).intValue();
	DataTable comDt = ClaimsInfoSearch.comSearch();
	
	DataTable hotDt = ClaimsInfoSearch.hotInfo();
	
	DataTable claDt = ClaimsInfoSearch.ClassityInfo(comCode); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保险理赔【流程_查询_材料_报案】-开心保保险网</title>
<meta name="keywords" content="理赔流程，理赔查询，理赔材料，理赔案例，理赔报案"/>
<meta name="description" content="买保险理赔难？开心保网理赔频道为您解忧，提供理赔流程、理赔查询、快速报案、理赔材料等一站式协助理赔服务，免费热线：4009-789-789！"/>
  <link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css"/>
  <link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css"/>
  <link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_sales.css"/>
  <link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_help.css"/>
  <link rel="shortcut icon" href="favicon.ico"/>
<%@include file="../wwwroot/kxb/block/kxb_custom_header.shtml" %>
<style type="text/css">
.g-nav-main li a.nav_5{ color:#f08225; }
</style>
</head>
<body class="re-bg"> 
<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml"%>
  <!-- 主体开始 -->
    <div class="material">
      <div class="main-wrap">
        <div class="daohang">
          <span class="daohang_home"></span> 
          <a target="_self" href="<%=Config.getFrontServerContextPath()%>/"><span class="orange">您的现在位置：首页</span></a>
          <span class="separator">&gt;</span>
           <a href="<%=Config.getValue("FrontServerContextPath")%>/lipei/" target="_self"><span class="separator1">理赔通道</span></a>
          <span class="separator">&gt;</span>
          <span class="separator1">理赔资料下载</span>
        </div>
        <div class="main">
          <div class="left-side fl-l">
          	<%
				if (comDt != null && comDt.getRowCount() > 0) {
			%>
				<div class="section download-company">
					<h1><i class="claim-icon fl-l"></i>按保险公司下载</h1>
					<ul class="clearfix">
			<%
					for (int i = 0; i < comDt.getRowCount(); i++) {
							DataRow dr = comDt.getDataRow(i);		
			%>	
					<li class="fl-l ellipsis"><a href="<%=Config.getServerContext()%>/Search/SearchClaimsInfo.jsp?comCode=<%= dr.getString("comCode")%>"><%= dr.getString("comClassifyName")%></a></li>	
			<%	
					}
			%>
					</ul>
				</div>
			<%
				} else {
			%>
				<div class="section download-company" style="display:none">
				</div>
			<%		
				}
          	%>	
          	
            <%
				if (hotDt != null && hotDt.getRowCount() > 0) {
			%>
				<div class="section hot-download">
			      <h1><i class="claim-icon fl-l"></i>热门资料下载</h1>
					<ul>
			<%
					for (int i = 0; i < hotDt.getRowCount(); i++) {
							DataRow dr = hotDt.getDataRow(i);		
			%>	
					  <li><a class="ellipsis" href="<%= dr.getString("Link")%>" target="_blank"><%= dr.getString("name")%></a></li>
			<%	
					}
			%>
					</ul>
				</div>
			<%
				} else {
			%>
				<div class="section hot-download" style="display:none">
				</div>
			<%		
				}
          	%>
          </div>
          <div class="content-wrap fl-r">
            <!-- 搜索 -->
            <div class="search-wrap clearfix">
              <input class="fl-l" type="text" id="searchText" value="<%=oldquery%>" placeholder="请输入保险公司名称、关键词搜索">
              <a class="btn fl-r" href="javascript:void(0);" onclick="searchClaims();">搜索</a>
            </div>
            <!-- 标签 -->
        <%
			if (claDt != null && claDt.getRowCount() > 0) {
		%>
			<div class="label-wrap" id="ClassityInfo">
			  <ul class="clearfix">
		<%
				for (int i = 0; i < claDt.getRowCount(); i++) {
						DataRow dr = claDt.getDataRow(i);	
					if (dr.getString("id").equals(claType)) {
		%>	
		
				<li class="fl-l"><a class="on" href="<%=Config.getServerContext()%>/Search/SearchClaimsInfo.jsp?comCode=<%= dr.getString("comCode")%>&claType=<%= dr.getString("id")%>"><%= dr.getString("name")%></a></li>
		<%
					} else {
		%>
				<li class="fl-l"><a href="<%=Config.getServerContext()%>/Search/SearchClaimsInfo.jsp?comCode=<%= dr.getString("comCode")%>&claType=<%= dr.getString("id")%>"><%= dr.getString("name")%></a></li>
		
		<%	
					}
		%>  
		<%	
				}
		%>  
			  </ul>
			</div>
		<%
			} else {
		%>
			<div class="label-wrap" style="display:none" id="ClassityInfo"></div>
		<%		
			}
        %>
        
        	<ul class="content" id="content">
        <%
			if (total > 0) {
		%>
        <%
				int Start = pageIndex * pageSize;
				for (int i = Start; i < Start + pageSize && i < total; i++) {
					DataRow dr = dt.getDataRow(i);
		%>
			   <li class="clearfix">
                 <span class="fl-l"><%= dr.getString("name")%></span>
                 <a class="fl-r" href="<%= dr.getString("Link")%>" rel="nofollow"><i class="claim-icon fl-l"></i>点击下载</a>
               </li>
		<%		
				}
        %>
        <%		
			}
        %>
        	</ul>
            <!-- 分页 -->
            <div class="page_area"  id="claimsPageBar">
        <%
        if (total > 0) { 
			StringBuffer sb2 = new StringBuffer();
			// 分页
			sb2.append("<div id='pagination'><ul>");
			if (pageIndex > 0) {
				sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageIndex + "\");'><span>上一页</span></a></li>");
				sb2.append("<li><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
			} else {
				sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span class='default'>上一页</span></a></li>");
				sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
			}
			int j = 2;
			for (j = 2;j< pageCount; j++) {
				if(pageCount>6){
					if (pageIndex >= pageCount - 4) {
						if (j >= pageCount - 3) {
							if (j == pageCount - 3) {
								sb2.append("<li class='omit'><span>...</span></li>");
							}
							if (j==(pageIndex+1)) {
								sb2.append("<li class='now'>");
							} else {
								sb2.append("<li>");
							}
							sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
										
						}
					} else if(pageIndex<3){
						if(j<5){
							if (j==(pageIndex+1)) {
								sb2.append("<li class='now'>");
							} else {
								sb2.append("<li>");
							}
							sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
							if(j==4){						
								sb2.append("<li class='omit'><span>...</span></li>");
							}
						}
					} else {
						if(pageIndex>2 && pageCount>(pageIndex+1)){
							if(j>(pageIndex-1)&&j<(pageIndex+3)){
								if (j==(pageIndex+1)) {
									sb2.append("<li class='now'>");
								} else {
									sb2.append("<li>");
								}
								sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
							}
							if(j==(pageIndex+2)&&j<pageCount-2){
								sb2.append("<li class='omit'><span>...</span></li>");
							}
						}
					}
				}else if(pageCount<7){
					if (j==(pageIndex+1)) {
						sb2.append("<li class='now'>");
					} else {
						sb2.append("<li>");
					}
					sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
				}
			}
						
			if (pageIndex + 1 == pageCount) {
				if (pageCount > 1) {
					sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span>"+pageCount+"</span></a></li>");
				}
				sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span class='default'>下一页</span></a></li>");
			} else {
				sb2.append("<li><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span>"+pageCount+"</span></a></li>");
				sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doOrder2(\"" + (pageIndex + 2) + "\");'><span>下一页</span></a></li>");
			}
						
			sb2.append("</ul></div>");
			JspWriter out1 = pageContext.getOut();
			out1.print(sb2);
        }
		%>
         
            </div>
          </div>
        </div>
      </div>
    </div>
  <!-- 主体结束 -->
  
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
<%@include file="../wwwroot/kxb/block/kxb_footer_new_common.shtml" %> 
<%@include file="../wwwroot/kxb/block/community_v1.shtml" %>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/redesign/re_help.js"></script>
<script type="text/javascript">

</script>

</body>
</html>