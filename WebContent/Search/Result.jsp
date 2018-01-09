<!DOCTYPE HTML>
<%@ page session="false" contentType="text/html;charset=utf-8"
	import="com.sinosoft.search.*"
	import="com.sinosoft.cms.api.*"
	import="com.sinosoft.framework.*"
	import="com.sinosoft.framework.utility.*"
	import="com.sinosoft.framework.data.*"
	import="java.util.*"
	import="com.wangjin.search.*"
%>
<%@ taglib uri="controls" prefix="z"%>
<%
	request.setCharacterEncoding(Constant.GlobalCharset);
	response.setContentType("text/html;charset=utf-8"); 
	
	String keyword = request.getParameter("keyword");
	String query = request.getParameter("query");
	String oldquery = request.getParameter("oldquery");
	//query = new String(query.getBytes("ISO8859_1"), "utf-8");
	
	//java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^\\?+$");
	//java.util.regex.Matcher matcher = pattern.matcher(query);
	//if (matcher.find())
	//{
	//	query = request.getParameter("query");
	//}
	//String keyword = SearchAPI.getParameter(request,"keyword");
	//String query = SearchAPI.getParameter(request,"query");
	
	String utf8query = "";
	String utf8Oldquery = "";
	
	if(StringUtil.isNotEmpty(query)){
		utf8query = java.net.URLEncoder.encode(query, "utf-8");
		query = java.net.URLDecoder.decode(query,"utf-8");
		if (null != oldquery)
		{
			utf8Oldquery = java.net.URLEncoder.encode(oldquery, "utf-8");
			oldquery = java.net.URLDecoder.decode(oldquery,"utf-8");
		}
	}
	
	if (StringUtil.isEmpty(query)) {
		query = keyword;//和旧版本保持一致
	}
	query = StringUtil.isEmpty(query)?"":StringUtil.escapeSpecialLetter(query);
	if (StringUtil.isEmpty(oldquery))
	{
		oldquery = query;
	}
	else
	{
		oldquery = StringUtil.escapeSpecialLetter(oldquery);
	}
	
	String strpage = request.getParameter("page");
	strpage = StringUtil.isEmpty(strpage)?"1":strpage;
	if (!strpage.matches("\\d+"))
	{
		StringUtil.invalidInputCope(out, "页码不合法！");
		return;
		//strpage = "1";
	}
	
	String order = request.getParameter("order");
	order = StringUtil.isEmpty(order)?"rela":order;
	if (!order.matches("(rela)|(time)"))
	{
		StringUtil.invalidInputCope(out, "排序方式不合法！");
		return;
		//order = "time";
	}
	
	String time = request.getParameter("time");
	time = StringUtil.isEmpty(time)?"all":time;
	if (!time.matches("(all)|(week)|(month)|(quarter)"))
	{
		StringUtil.invalidInputCope(out, "时间类型不合法！");
		return;
		//time = "all";
	}
	
	String site = request.getParameter("site");
	site = StringUtil.isEmpty(site)?"":site;
	if (!site.matches("\\d+"))
	{
		StringUtil.invalidInputCope(out, "站点id不合法！");
		return;
		//site = "221";
	}
	
	String id = request.getParameter("id");
	id  = StringUtil.isEmpty(id)?"":id;
	if (!id.matches("\\d+"))
	{
		StringUtil.invalidInputCope(out, "索引id不合法！");
		return;
		//id = "20";
	}
	
	String size = request.getParameter("size");
	size  = StringUtil.isEmpty(size)?"10":size;
	
	int pageSize = Integer.parseInt(size);
	int pageIndex = Integer.parseInt(strpage) - 1;
	
	Mapx map = ServletUtil.getParameterMap(request,false);
	map.put("query",query);
	map.put("page", strpage);
	map.put("id", id);
	map.put("site", site);
	map.put("order", order);
	// 2012.11.19 add by meijunfeng <<20121108018-需求系统开发需求申请单-搜索页面>> start
	AddKeyword.addkeyword(query.replaceAll("'", "\\\\'"));
	// 2012.11.19 add by meijunfeng <<20121108018-需求系统开发需求申请单-搜索页面>> end
	SearchResult r = ArticleSearcher.search(map);
	if(r==null){
		out.println("<br><br><br><b><font color='red'>全文检索索引未建立。</font></b>");
		out.println("<br><br>请在“站点管理”-“站点列表”中选择相应站点，开启“自动建立索引”选项。");
		return;
	}
	DataTable dt = r.Data;
	int total = r.Total;
	double usedTime = r.UsedTime;
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<title>搜索结果_<%=query%></title>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<style type="text/css">
	.InsuQASearchArea{}
	.InsuQASearchArea .InsuQASearchBg{border:1px solid #f9b041; width:980px;background:#fff url(<%=Config.getValue("StaticResourcePath")%>/images/Bg_1.gif) 0 -490px repeat-x; height:80px;}
	.InsuQASearchArea .InputTxtArea{width:530px; height:30px; line-height:30px; border:1px solid #CC6600; background:#fff url(<%=Config.getValue("StaticResourcePath")%>/images/Bg_1.gif) 0 -382px repeat-x; margin-top:20px; margin-left:125px; padding-left:8px; font-size:12px; color:#666666; margin-right:5px; float:left;}
	.InsuQASearchArea .Btn_1   {background:url('<%=Config.getValue("StaticResourcePath")%>/images/BtnImg1.jpg') ;width:100px; height:31px; border:none; cursor:pointer; margin-right:5px; margin-top:20px; }
	.lip2{padding-top: 2px ;padding-bottom: 2px;}
</style>

</head>

<body >
	<div class="wrapper">
	<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml" %>
		<div class="center" style="width:980px;">
		     <form id="search" action="/Search/Result.jsp">
			    <div class="InsuQASearchArea mb10">
						<div class="InsuQASearchBg">
							<input type="text" value="<%=query%>" class="InputTxtArea" name="query" id="queryid"/>
							<input type="hidden" name="oldquery" value="<%=oldquery%>" id="oldqueryid" />
							<!-- input name="site" type="hidden" id="site" value="<%=site%>">
							<input name="id" type="hidden" id="id" value="<%=id%>" -->
							<input type="submit" value="" style="float:left;background:url('<%=Config.getValue("StaticResourcePath")%>/images/search.gif');width:42px;height:32px;cursor: pointer;border: none; margin-top:20px; " />
						</div>
			    </div>
		    </form>
			<script type="text/javascript">document.getElementById("queryid").value = document.getElementById("oldqueryid").value;</script>
		
		
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bi">
				<tr>
					<td width="81%" valign="bottom" nowrap>
					<div id=div1
						style="background-color: #eee; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; CLEAR: right; Z-INDEX: 20; FLOAT: left; WIDTH: 100%; HEIGHT: 24px; FONT-SIZE: 13px;">
					<div style="padding: 0 1em; border-top: 1px solid #fff;"><span
						style="float: right;">约有<b><%=total%></b>项符合搜索<b> <%=query%>
					</b>的查询结果，耗时<%=usedTime%>秒，以下是第<b><%=(pageIndex * pageSize + 1)%></b>-<b><%=((pageIndex + 1) * pageSize)%></b>项。</span>
					</div>
					</div>
					</td>
				</tr>
			</table>
		
		 
		
			<div id="mleft" style="width: 900px;  padding-top:0px; text-align:left;word-wrap: break-word; word-break: normal;">
				<ul>
					<%
						for (int i = 0; i < dt.getRowCount(); i++) {
							DataRow dr = dt.getDataRow(i);
							if(i != dt.getRowCount()-1){
					%>     
						   <li class="g1" style="border-bottom: 1px dashed #CDCAC6;padding-top: 10px ;padding-bottom: 10px;">
						   <%  } else {%>
						   <li class="g1"  >
						   <% } %>
						        <ul>
							       	<li class="lip2"> <a class=l href="<%=dr.getString("URL")%>" target=_blank style="font-weight: bold;color: #000000;font-size: 15px;">
							        <%=dr.getString("Title")%></a></li>
									
								    <li class="lip2"><span style="font-size: 13px; "><%=dr.getString("Content")%>...</span></li>
								    <li class="lip2"><span style="color: #875"><a href="<%=dr.getString("URL")%>"><%=dr.getString("URL")%></a> - <%=dr.getString("PublishDate")%></span></li>
							   		<li class="lip2">栏目：<%=dr.getString("CatalogName")%></li>
							    </ul>
						   </li>
					<%
						}
					%>
					
				</ul>
				<ul style=" text-align: center;padding-top: 40px;">
					<li ><span style="font-size: 13px;">结果页码:&nbsp;</span> 
					<%
						int pageCount = new Double(Math.ceil(total * 1.0 / pageSize)).intValue();
						int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
						int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
						String queryStr = "/Search/Result.jsp?query=" + utf8query + (query.equals(oldquery)?"":("&oldquery=" + utf8Oldquery));
						if (pageIndex >= 1) {
							//out.println(" <td nowrap><a href='"+ SearchAPI.getPageURL(map, pageIndex)+ "'>上一页</a></td>");
							out.println(" <td nowrap><a href='"+queryStr+"&page="+pageIndex+"'>上一页</a></td>");
						}
						for (int i = start; i <= end; i++) {
							if (i == pageIndex + 1) {
								out.println("<td nowrap><span class=i>" + i	+ "</span></td>");
							} else {
								//out.println("<td nowrap><a href='"+ SearchAPI.getPageURL(map, i) + "'>[" + i+ "]</a></td>");
								out.println("<td nowrap><a href='"+queryStr+"&page=" + i + "'>[" + i+ "]</a></td>");
							}
						}
						if (pageIndex < pageCount - 1) {
							//out.println(" <td nowrap><a href='"+ SearchAPI.getPageURL(map, pageIndex + 2)+ "'>下一页</a></td>");
							out.println(" <td nowrap><a href='"+queryStr+"&page=" + (pageIndex + 2) + "'>下一页</a></td>");
						}
					%></li>
				</ul>
			</div>
		
		</div>
	   <%@include file="../wwwroot/kxb/block/kxb_footer_new_common.shtml" %>
	</div>
	   <%@include file="../wwwroot/kxb/block/community_v1.shtml" %>
</body>
</html>
