<!DOCTYPE HTML>
<%@ page session="false" contentType="text/html;charset=utf-8"
	import="com.sinosoft.search.*"
	import="com.sinosoft.cms.api.*"
	import="com.sinosoft.framework.*"
	import="com.sinosoft.framework.utility.*"
	import="com.sinosoft.framework.security.*"
	import="com.sinosoft.framework.data.*"
	import="java.util.*"
	import="com.sinosoft.schema.ZCTagSchema"
	import="com.sinosoft.schema.ZCTagSet"
	import="com.sinosoft.schema.ZCCatalogSchema"
	import="com.sinosoft.schema.ZCCatalogSet"
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
	
	if(StringUtil.isNotEmpty(query)){
		query = java.net.URLDecoder.decode(query,"utf-8");
		if (null != oldquery)
		{
			oldquery = java.net.URLDecoder.decode(oldquery,"utf-8");
		}
	}
	if (StringUtil.isEmpty(query)) {
		query = keyword;//和旧版本保持一致
	}
	query = StringUtil.isEmpty(query)?"":query;
	

	String strpage = request.getParameter("page");
	strpage = StringUtil.isEmpty(strpage)?"1":strpage;
	
	String order = request.getParameter("order");
	order = StringUtil.isEmpty(order)?"rela":order;

	String time = request.getParameter("time");
	time = StringUtil.isEmpty(time)?"all":time;
	
	String site = request.getParameter("site");
	site = StringUtil.isEmpty(site)?"":site;

	String id = request.getParameter("id");
	id  = StringUtil.isEmpty(id)?"":id; 
	String size = request.getParameter("size");
	size  = StringUtil.isEmpty(size)?"10":size; 
	
	int pageSize = Integer.parseInt(size);
	int pageIndex = Integer.parseInt(strpage) - 1;
	String tagRex = request.getParameter("tag");
	String tagId="";
	if(StringUtil.isNotEmpty(tagRex)){
		//修改tag搜索 ，用id做参数
		//query=EncryptUtil.decrypt3DES(query,EncryptUtil.DEFAULT_KEY);
		if(StringUtil.isNotEmpty(query)&&query.length()>9){
			query=query.trim().substring(0,6);
		}
		tagId=query;
		if(StringUtil.isDigit(tagId)){
			ZCTagSchema tZCTagSchema=new ZCTagSchema();
			tZCTagSchema.setID(tagId);
			if(!tZCTagSchema.fill()){
				%>
				<script type="text/javascript">
					window.location.href="<%=Config.getFrontServerContextPath()%>/yccl/404cwym/index.shtml!";
				</script>
				<%
			}
			query=tZCTagSchema.getTag();
			
		} else {
			 %>
				<script type="text/javascript">
					window.location.href="<%=Config.getServerContext()%>/error.jsp?errormsg=数据格式非法!";
				</script>
			<%
			return;
			
		}
		if (StringUtil.isEmpty(oldquery))
		{
			oldquery = query;
		}
	}
	
	Mapx map = ServletUtil.getParameterMap(request,false);
	map.put("query",query);
	DataTable r = ArticleSearcher.leftSearch9(map);
	DataTable dt1 = ArticleSearcher.leftSearch1(map);
	DataTable dt2 = ArticleSearcher.leftSearch2(map);
	DataTable dt3 = ArticleSearcher.leftSearch3(map);
	DataTable dt4 = ArticleSearcher.leftSearch4(map);
	DataTable dt5 = ArticleSearcher.leftSearch5(map);
	DataTable dt6 = ArticleSearcher.leftSearch6(map);
	DataTable dt7 = ArticleSearcher.leftSearch7(map);
	DataTable dt8 = ArticleSearcher.leftSearch8(map);
	map.put("query",tagId);
	if(r==null){
		out.println("<br><br><br><b><font color='red'>全文检索索引未建立。</font></b>");
		out.println("<br><br>请在“站点管理”-“站点列表”中选择相应站点，开启“自动建立索引”选项。");
		return;
	}
	
	int total = r.getRowCount();
	
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<title><%=query%>_第<%=strpage%>页-开心保保险网</title>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_list.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_news.css" />
<style type="text/css">
	.InsuQASearchArea .InsuQASearchBg{border:1px solid #f9b041; background:#fff url(<%=Config.getValue("StaticResourcePath")%>/images/Bg_1.gif) 0 -490px repeat-x; height:80px;}
	.InsuQASearchArea .InputTxtArea{width:530px; height:30px; line-height:30px; border:1px solid #CC6600; background:#fff url(<%=Config.getValue("StaticResourcePath")%>/images/Bg_1.gif) 0 -382px repeat-x; margin-top:20px; margin-left:125px; padding-left:8px; font-size:12px; color:#666666; margin-right:5px; float:left;}
	.InsuQASearchArea .Btn_1   {background:url('<%=Config.getValue("StaticResourcePath")%>/images/BtnImg1.jpg') ;width:100px; height:31px; border:none; cursor:pointer; margin-right:5px; margin-top:20px; }
	.lip2{padding-top: 2px ;padding-bottom: 2px;}
	.tag_box{ border:1px solid #CCCCCC; border-top:2px solid #ED6D00; width: 701px; padding:18px;  padding-top:0px; text-align:left;word-wrap: break-word; word-break: normal;}
	.tag_jg{ text-align:left; color:#555555; height:28px; line-height:24px;}
	.tag_ul ul,.tag_ul{ width:205px; height:auto; margin-top:0px;}
	.tag_ul{ padding:8px 10px 10px 12px; overflow:hidden;}
	.lip2  a.tag_titles:link, .lip2  a.tag_titles:visited, .lip2  a.tag_titles:active{font-weight: bold;color: #000000;font-size: 15px;}
	.lip2  a.tag_titles:hover{ text-decoration:underline;}
	.g1{border-bottom: 1px dashed #CDCAC6;padding-top: 10px ;padding-bottom: 10px;}
	.tag_tags,.tag_tags  a:link, .tag_tags  a:visited, .tag_tags  a:active{ color:#A3A3A3;}
</style>

</head>

<body >
	<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml" %>
	<div class="wrapper cf">
		<div class="content_left">
		   <div class="l_boxss">
            	 <div class="c1">常见问答</div>
		          <ul class="zx_list">
					<%
						for (int i = 0; i < (dt1.getRowCount()>=5?5:dt1.getRowCount()); i++) {
							DataRow dr1 = dt1.getDataRow(i);
					%>     
                    	<li><a href="<%=Config.getFrontServerContextPath()%>/<%=dr1.getString("URL")%>" target="_blank"><%=dr1.getString("Title")%></a></li>
	                <%
						}
					if (dt1.getRowCount() < 5 && dt1.getRowCount() >= 0&&dt2.getRowCount()>0) {
						for (int i = 0; i <(dt2.getRowCount() >(5 - dt1.getRowCount())?(5 - dt1.getRowCount()):dt2.getRowCount()); i++) {
							DataRow dr2 = dt2.getDataRow(i);
					%> 
					<li><a href="<%=Config.getFrontServerContextPath()%>/<%=dr2.getString("URL")%>" target="_blank"><%=dr2.getString("Title")%></a></li>
					<%
						 }
						}
					%>
		          </ul>
           </div>
           <div class="l_boxss">
            	 <div class="c1">保险百科</div>
		          <ul class="zx_list">
		          	<%
						for (int i = 0; i < (dt3.getRowCount()>=5?5:dt3.getRowCount()); i++) {
							DataRow dr3 = dt3.getDataRow(i);
					%>     
                    	<li><a href="<%=Config.getFrontServerContextPath()%>/<%=dr3.getString("URL")%>" target="_blank"><%=dr3.getString("Title")%></a></li>
	                <%
						}
					if (dt3.getRowCount() < 5 && dt3.getRowCount() >= 0&&dt4.getRowCount()>0) {
						for (int i = 0; i < (5 - dt3.getRowCount()>dt4.getRowCount()?dt4.getRowCount():(5 - dt3.getRowCount())); i++) {
							DataRow dr4 = dt4.getDataRow(i);
					%> 
					<li><a href="<%=Config.getFrontServerContextPath()%>/<%=dr4.getString("URL")%>" target="_blank"><%=dr4.getString("Title")%></a></li>
					<%
						 }
						}
					%>
		          </ul>
           </div>
           <div class="l_boxss">
            	 <div class="c1">推荐专题</div>
		          <ul class="zx_list">
		          	<%
						for (int i = 0; i < (dt5.getRowCount()>=5?5:dt5.getRowCount()); i++) {
							DataRow dr5 = dt5.getDataRow(i);
					%>     
                    	<li><a href="<%=Config.getFrontServerContextPath()%>/<%=dr5.getString("URL")%>" target="_blank"><%=dr5.getString("Title")%></a></li>
	                <%
						}
					if (dt5.getRowCount() < 5 && dt5.getRowCount() >= 0&&dt6.getRowCount()>0) {
						for (int i = 0; i < (5 - dt5.getRowCount()>dt6.getRowCount()?dt6.getRowCount():(5 - dt5.getRowCount())); i++) {
							DataRow dr6 = dt6.getDataRow(i);
					%> 
					<li><a href="<%=Config.getFrontServerContextPath()%>/<%=dr6.getString("URL")%>" target="_blank"><%=dr6.getString("Title")%></a></li>
					<%
						 }
						}
					%>
		          </ul>
           </div>
           <div class="l_boxss">
            	 <div class="c1">推荐标签</div>
		          <div class="news_rmbj tag_ul">
				<ul>
					<li>
						<!--循环：customtable count：20-->
					<%
						for (int i = 0; i < (dt7.getRowCount()>=20?20:dt7.getRowCount()); i++) {
							DataRow dr7 = dt7.getDataRow(i);
							int drint1=Integer.valueOf(dr7.getString("UsedCount")).intValue();
							if(drint1<=10){
					%>     
                    	<a href="<%=dr7.getString("LinkURL")%>" target="_blank" class="news_color13"><%=dr7.getString("Tag")%></a>
                    <% }else if(drint1>=11&&drint1<=50){%>
                    	<a href="<%=dr7.getString("LinkURL")%>" target="_blank" class="news_color14"><%=dr7.getString("Tag")%></a>
	                <%}else if(drint1>=51&&drint1<=100){ %>
	                <a href="<%=dr7.getString("LinkURL")%>" target="_blank" class="news_color12"><%=dr7.getString("Tag")%></a>
	                <%}else if(drint1>=101&&drint1<=200){ %>
	                <a href="<%=dr7.getString("LinkURL")%>" target="_blank" class="news_color15"><%=dr7.getString("Tag")%></a>
	                <%}else if(drint1>=201&&drint1<=500){ %>
	                <a href="<%=dr7.getString("LinkURL")%>" target="_blank" class="news_color16"><%=dr7.getString("Tag")%></a>
	                <%}else{ %>
	                <a href="<%=dr7.getString("LinkURL")%>" target="_blank" class="news_color19"><%=dr7.getString("Tag")%></a>
	                <%
						}
						}
					if (dt7.getRowCount() < 20 && dt7.getRowCount() >= 0) {
						for (int i = 0; i < 20 - dt7.getRowCount(); i++) {
							DataRow dr8 = dt8.getDataRow(i);
							int drint=Integer.valueOf(dr8.getString("UsedCount")).intValue();
							if(drint<=10){
					%> 
					<a href="<%=dr8.getString("LinkURL")%>" target="_blank" class="news_color13"><%=dr8.getString("Tag")%></a>
					<% }else if(drint>=11&&drint<=50){%>
                    	<a href="<%=dr8.getString("LinkURL")%>" target="_blank" class="news_color14"><%=dr8.getString("Tag")%></a>
	                <%}else if(drint>=51&&drint<=100){ %>
	               	<a href="<%=dr8.getString("LinkURL")%>" target="_blank" class="news_color12"><%=dr8.getString("Tag")%></a>
	                <%}else if(drint>=101&&drint<=200){ %>
	                <a href="<%=dr8.getString("LinkURL")%>" target="_blank" class="news_color15"><%=dr8.getString("Tag")%></a>
	                <%}else if(drint>=201&&drint<=500){ %>
	                <a href="<%=dr8.getString("LinkURL")%>" target="_blank" class="news_color16"><%=dr8.getString("Tag")%></a>
	                <%}else{ %>
	                <a href="<%=Config.getFrontServerContextPath()%><%=dr8.getString("LinkURL")%>" target="_blank" class="news_color19"><%=dr8.getString("Tag")%></a>
					<%
						}
						}
					    }
					%>
					</li>
				</ul>
			</div>
           </div>
		   <%@include file="../wwwroot/kxb/block/kxb_product_tag_recommend.shtml" %>
			</div>
			<div class="content_right">
		     <form id="search" action="<%=Config.getServerContext()%>/Search/ResultTag.jsp" style="display: none;">
			    <div class="InsuQASearchArea mb10">
						<div class="InsuQASearchBg">
							<input type="text" value="<%=query%>" class="InputTxtArea" name="query" id="queryid"/>
							<input type="hidden" name="oldquery" value="<%=oldquery%>" id="oldqueryid" />
							<input name="site" type="hidden" id="site" value="<%=site%>">
							<input name="id" type="hidden" id="id" value="<%=id%>">
							<input type="submit" value="" style="float:left;background:url('<%=Config.getValue("StaticResourcePath")%>/images/search.gif');width:42px;height:32px;cursor: pointer;border: none; margin-top:20px; " />
						</div>
			    </div>
		    </form>
			<script type="text/javascript">document.getElementById("queryid").value = document.getElementById("oldqueryid").value;</script>
		
		
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bi">
				<tr>
					<td width="81%" valign="bottom" nowrap>
					<div id=div1 class="tag_jg">
					<span>约有<b><%=total%></b>项符合搜索<b> <%=query%>
					</b>的查询结果，以下是第<b><%=(pageIndex * pageSize + 1)%></b>-<b><%=((pageIndex + 1) * pageSize)%></b>项。</span>
					</div>
					</td>
				</tr>
			</table>
		
		 
		
			<div id="mleft" class="tag_box">
				<ul>
					
							<%
								int strpage1 = Integer.parseInt(strpage);
								int k=0,n=0;
								int a=r.getRowCount()%10;
								int b=r.getRowCount()/10;
								int c=b+1;
								if(strpage1==1)
								{
									k=0;
									n=10;
								}
								else if(strpage1>1&&strpage1<c)
								{
									k=(strpage1-1)*10;
									n=strpage1*10;
								}
								else if(strpage1==c)
								{
									k=(strpage1-1)*10;
									n=(strpage1-1)*10 + a;
								}
								if(b==0)
								{
									k=0;
									n=a;
								}
								for (int i = k; i < n; i++) {
									DataRow dr10 = r.getDataRow(i);
							%>   
							<li class="g1"  >
							<ul>  
		                    	<li class="lip2"><a class="tag_titles" href="<%=Config.getFrontServerContextPath()%>/<%=dr10.getString("URL")%>" 
		                    	target="_blank"><%=dr10.getString("Title")%></a></li>
		                    	<li class="lip2">标签：<span style="font-size: 13px; ">
		                    	<%
										String[] tag=dr10.getString("Tag").split(" ");
										for (int j = 0; j < tag.length; j++) {
											String tag1=tag[j];
											String tagId1="";
											if(StringUtil.isNotEmpty(tag1)){
												ZCTagSchema tZCTagSchema=new ZCTagSchema();
												ZCTagSet tZCTagSet=tZCTagSchema.query(new QueryBuilder("where tag= '" + tag1 + "' "));
												if(tZCTagSet.get(0)!=null){
													tagId1=tZCTagSet.get(0).getID()+"";
													if(tagId1.length()<6){
														tagId1=StringUtil.leftPad(tagId1, '0', 6);
													}
									%>
		                    	<a href="<%=Config.getFrontServerContextPath()%>/tag/<%=tagId1%>000001-<%=id%>.html"><%=tag1%></a>&nbsp;&nbsp;
		                    	<%
												}
											}
										}
									%>
		                    	</span></li>
		                    	<%
		                    		String content1="";
		                    		if(dr10.getString("Content").length()<160)
		                    		{
		                    			 content1=dr10.getString("Content").replaceAll("<p>", "").replaceAll("</p>", "");
		                    		}
		                    		else{
		                    		 content1=dr10.getString("Content").substring(0,160).replaceAll("<p>", "").replaceAll("</p>", "");
		                    		}
		                    	%>
		                    	 <li class="lip2">摘要：<span><%=content1%>...</span></li>
		                    	
		                    	<li class="lip2"><span ><a href="<%=Config.getFrontServerContextPath()%>/<%=dr10.getString("URL")%>">
		                    	<%=Config.getFrontServerContextPath()%>/<%=dr10.getString("URL")%></a> - <%=dr10.getString("PublishDate")%></span></li>
		                    	<%
		                    		    ZCCatalogSchema tZCCatalogSchema=new ZCCatalogSchema();
				                    	ZCCatalogSet tZCCatalogSet=new ZCCatalogSet();
				                    	tZCCatalogSet.add(tZCCatalogSchema.query(new QueryBuilder("where InnerCode="+dr10.getString("CatalogInnerCode"))));
				                    	tZCCatalogSchema=tZCCatalogSet.get(0);
				                    	String CatalogName1=tZCCatalogSchema.getName();
		                    	%>
		                    	<li class="lip2">栏目：<%=CatalogName1%></li>
		                    	</ul>
			                <%
								}
							%>
								
				</li>
				</ul>
				<ul style=" text-align: center;padding-top: 40px;">
					<li ><span style="font-size: 13px;">结果页码:&nbsp;</span> 
					<%
						int pageCount = new Double(Math.ceil(total * 1.0 / pageSize)).intValue();
						int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
						int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
						if (pageIndex >= 1) {
							out.println(" <td nowrap><a href='"+ SearchAPI.getTagPageURL(map, pageIndex)+ "'>上一页</a></td>");
						}
						for (int i = start; i <= end; i++) {
							if (i == pageIndex + 1) {
								out.println("<td nowrap><span class=i>" + i	+ "</span></td>");
							} else {
								out.println("<td nowrap><a href='"+ SearchAPI.getTagPageURL(map, i) + "'>[" + i+ "]</a></td>");
							}
						}
						if (pageIndex < pageCount - 1) {
							out.println(" <td nowrap><a href='"+ SearchAPI.getTagPageURL(map, pageIndex + 2)+ "'>下一页</a></td>");
						}
					%></li>
				</ul>
			</div>
		
		</div>
	</div>
  		<%@include file="../wwwroot/kxb/block/kxb_footer_new_common.shtml" %>
	   <%@include file="../wwwroot/kxb/block/community_v1.shtml" %>
</body>
</html>
