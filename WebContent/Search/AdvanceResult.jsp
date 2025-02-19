<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" 
	import="com.sinosoft.search.*"
	import="com.sinosoft.cms.api.*" 
	import="com.sinosoft.framework.*"
	import="com.sinosoft.framework.utility.*"
	import="com.sinosoft.framework.data.*"
%>
<%@ taglib uri="controls" prefix="z"%>
<%
	request.setCharacterEncoding(Constant.GlobalCharset);
    response.setContentType("text/html;charset=" + Constant.GlobalCharset);
	
	String query = SearchAPI.getParameter(request,"query");
	query = query==null?"":query;

	String title = SearchAPI.getParameter(request,"title");
	title = title==null?"":title;

	String content = SearchAPI.getParameter(request,"content");
	content = content==null?"":content;
	
	String catalog = request.getParameter("catalog");
	catalog = catalog==null?"":catalog;
	
	String startdate = request.getParameter("startdate");
	startdate = startdate==null?"":startdate;

	String enddate = request.getParameter("enddate");
	enddate = enddate==null?"":enddate;

	String author = SearchAPI.getParameter(request,"author");
	author = author==null?"":author;
	
	String strpage = request.getParameter("page");
	strpage = StringUtil.isEmpty(strpage)?"1":strpage;

	String site = request.getParameter("site");
	site = site==null?"0":site;
	
	String size = request.getParameter("size");
	size  = StringUtil.isEmpty(size)?"10":size; 

	String id = request.getParameter("id");
	id = id==null?"":id;

	int pageSize = Integer.parseInt(size);
	int pageIndex = Integer.parseInt(strpage) - 1;
	
	Mapx map = ServletUtil.getParameterMap(request,false);
	map.put("query",query);
	map.put("title",title);
	map.put("author",author);
	map.put("content",content);
	SearchResult r = ArticleSearcher.advanceSearch(map);
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
<title>搜索结果-高级检索</title>
<link href="gufei.css" rel="stylesheet" type="text/css" />
<style>
<!--
body {font-size: 12px; font-family: "宋体";	background: #FFFFFF; margin: 0px; padding: 0px;	color: #444;}
a:link {color: #0066CC;	text-decoration: underline;}
a:visited {color: #0066CC; text-decoration: underline;}
a:hover {color: #CC0000; text-decoration: none;}
td {font-size: 12px; line-height: 18px;	color: #444;}
.f14 {font-size: 14px}
.f10 {font-size: 10.5pt}
.f16 {font-size: 16px; font-family: Arial}
.c {color: #7777CC;}
.p1 {line-height: 120%;	margin-left: -12pt}
.p2 {width: 100%; line-height: 120%;	margin-left: -12pt}
.i {font-size: 16px}
.t {color: #0000cc;text-decoration: none}
a.t:hover {text-decoration: underline}
.p {padding-left: 18px;	font-size: 14px; word-spacing: 4px;}
.f {line-height: 120%; font-size: 100%;	width: 32em; padding-left: 15px; word-break: break-all;	word-wrap: break-word;}
.h {margin-left: 8px; width: 100%}
.s {width: 8%; padding-left: 10px; height: 25px;}
.m,a.m:link {color: #666666; font-size: 100%;}
a.m:visited {color: #660066;}
.g {color: #008000;	font-size: 12px;}
.r {word-break: break-all; cursor: hand; width: 225px;}
.bi {margin-bottom: 12px}
.pl {padding-left: 3px;	height: 8px; padding-right: 2px; font-size: 14px;}
.Tit {height: 21px;	font-size: 14px;}
.fB {font-weight: bold;}
.mo,a.mo:link,a.mo:visited {color: #666666;	font-size: 100%; line-height: 10px;}
.htb {margin-bottom: 5px;}
#ft {clear: both; background: #E6E6E6; text-align: center; color: #777;	font-size: 12px; font-family: Arial; line-height: 24px;	width: 100%;}
#ft span {color: #666}
.g1 {margin-top: 1em; margin-bottom: 0em; text-align:left}
.l {font-size: 16px; font-family: Verdana, Geneva, sans-serif;}
.j {line-height: 22px}
#mleft {float: left;}
.STYLE13 {color: #336600;}
-->
</style>
<script src="../Framework/Main.js"></script>
<script>
var query = "<%=query%>";
function searchInResult(){
	if(document.getElementById("query").value!=query){
		document.getElementById("query").value  = query+" "+document.getElementById("query").value;
	}
	document.getElementById("search").submit();
}
</script>
<script type="text/javascript">
 ///时间日期函数
function Ncdatetime()
{       
today=new Date();
function initArray(){
this.length=initArray.arguments.length
for(var i=0;i<this.length;i++)
this[i+1]=initArray.arguments[i]  }
var d=new initArray(
"星期日",
"星期一",
"星期二",
"星期三",
"星期四",
"星期五",
"星期六");
document.write(
"<font color=#111111 style='font-size:9pt;font-family: 宋体'> ",
today.getFullYear(),"年",
today.getMonth()+1,"月",
today.getDate(),"日",
"</font>",
"<font color=#111111 style='font-size:9pt;font-family: 宋体'> ",
d[today.getDay()+1],
"</font>" ); 
}


</script>
</head>
<body link="#261CDC">
  <div class="wap">
    <div class="header">
       <div class="header_top"><img src="er_toplip.jpg" /></div>
       <div class="logo">
         <p class="date">北京时间：<script type="text/javascript">Ncdatetime();</script></span></p>
       </div>

    </div>
    <br>
<div class="center" style="width:977px;">
<div class="bodyWidth">
<form id="search" method="get">
<table width="100%" height="71" align="center" cellpadding="0" cellspacing="0">
	<tr valign=middle>
		<td width="200" align="center" valign="middle" nowrap style="padding-left: 10px;">
		<img src="tit.gif" width="200" height="50" alt="搜索结果"></td>
		<td>&nbsp;</td>
		<td width="100%" valign="middle">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td height="30" valign="top" nowrap>
					关键字：
					<input name="query" class="i" id="query" value="<%=query%>" size="35" maxlength="100">
					<input name="site" type="hidden" id="site" value="<%=site%>">
					<input type="submit" value="搜索一下">
					<input type="button" value="结果中找" onClick="searchInResult()">&nbsp;&nbsp;&nbsp;				</td>
				<td valign="middle" nowrap>
					<a href=# target="_blank">帮助</a>&nbsp;|&nbsp;
					<a href="Result.jsp?query=<%=StringUtil.urlEncode(query)%>&site=<%=site%>">模糊搜索</a>				</td>
			</tr>
		</table>
		</td>
		<td></td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bi">
	<tr>
		<td width="81%" valign="bottom" nowrap>
		<div id=div1
			style="background-color: #eee; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; CLEAR: right; Z-INDEX: 20; FLOAT: left; WIDTH: 100%; HEIGHT: 24px; FONT-SIZE: 13px;">
		<div style="padding: 0 1em; border-top: 1px solid #fff;"><span
			style="float: right;">约有<b><%=total%></b>项符合搜索条件，耗时<%=usedTime%>秒，以下是第<b><%=(pageIndex * pageSize + 1)%></b>-<b><%=((pageIndex + 1) * pageSize)%></b>项。</span>
		</div>
		</div>
		</td>
	</tr>
</table>
<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"	align="right" width="24%">
	<tbody>
		<tr>
			<td>&nbsp;</td>
			<td bgcolor="#cccccc" width="1"><img height="1" width="1" alt="" /></td>
			<td>&nbsp;</td>
			<td>
			<div style="line-height: 25px; margin-right: 10px">
			<table cellspacing="0" cellpadding="0" width="100%">
				<tr>
				  <td width="26%" height="30" valign="middle" nowrap>标题：</td>
				  <td width="74%">
				  <input name=title id="title" value="<%=title%>" style="width:130px;height:18px"></td>
			  	</tr>
				<tr>
				  <td height="30" valign="middle" nowrap>内容：</td><td>
				  <input name=content id="content" value="<%=content%>" style="width:130px;height:18px"></td>
			 	</tr>
				<tr>
				  <td height="30" valign="middle" nowrap>作者：</td><td>
				  <input name=author id="author" value="<%=author%>" style="width:130px;height:18px"></td>
			 	</tr>
				<tr>
				  <td height="30" valign="middle" nowrap>起始日期：</td><td>
					<input name=startdate id="startdate" value="<%=startdate%>" ztype="Date" style="width:130px;height:18px"></td>
			 	</tr>
				<tr>
				  <td height="30" valign="middle" nowrap>结束日期：</td><td>
                  <input name=enddate id="enddate" value="<%=enddate%>" ztype="Date" style="width:130px;height:18px"></td>
			  	</tr>
				<tr>
				  <td height="30" valign="middle" nowrap>所在栏目：</td><td>
				  <select name="catalog" id="catalog" style="width:135px;height:18px">
				  	<%=SearchAPI.showCatalogList(site,catalog)%>
				  </select>
                  </td>
			  	</tr>
				<tr>
				  <td height="30" valign="middle" nowrap>&nbsp;</td>
				  <td><input type="submit" value="搜索一下"></td>
			  </tr>
			</table>
			</div>
			</td>
		</tr>
	</tbody>
</table>
<div id="mleft" style="width: 680px; padding: 20px;padding-top:0px;">
<div>
<%
	for (int i = 0; i < dt.getRowCount(); i++) {
		DataRow dr = dt.getDataRow(i);
%>
  <p class=g1><a class=l href="<%=dr.getString("URL")%>" target=_blank><%=dr.getString("Title")%></a></p>
	<table cellpadding=0 cellspacing=0 border=0>
		<tr>
			<td class=j><span style="font-size: 13px;"><%=dr.getString("Content")%>...<br>
			<span style="color: #875"><%=dr.getString("URL")%> - <%=dr.getString("PublishDate")%></span></span></td>
		</tr>
	</table>
<%
	}
%>
</div>
</div>
<br clear=all>
<table style="margin-left: 20px;">
	<tr>
		<td nowrap><span style="font-size: 13px;">结果页码:&nbsp;</span></td>
		<%
			int pageCount = new Double(Math.ceil(total * 1.0 / pageSize)).intValue();
			int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
			int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
			if (pageIndex > 1) {
				out.println(" <td nowrap><a href='"+ SearchAPI.getPageURL(map, pageIndex)+ "'>上一页</a></td>");
			}
			for (int i = start; i <= end; i++) {
				if (i == pageIndex + 1) {
					out.println("<td nowrap><span class=i>" + i	+ "</span></td>");
				} else {
					out.println("<td nowrap><a href='"+ SearchAPI.getPageURL(map, i) + "'>[" + i+ "]</a></td>");
				}
			}
			if (pageIndex < pageCount - 1) {
				out.println(" <td nowrap><a href='"+ SearchAPI.getPageURL(map, pageIndex + 2)+ "'>下一页</a></td>");
			}
		%>
	</tr>
</table>
</form>
</div>
<script src="<%=Config.getContextPath()%>Services/Stat.js" type="text/javascript"></script>
<script type="text/javascript">
_zcms_stat("SiteID=<%=site%>&Type=Article&Dest=<%=Config.getContextPath()%>Services/Stat.jsp");
</script>
</div>
</div>
</body>
</html>
