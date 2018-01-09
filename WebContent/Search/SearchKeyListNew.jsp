<!DOCTYPE html>
<%@ page session="false" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	import="com.sinosoft.search.*"
	import="com.sinosoft.cms.api.*"
	import="com.sinosoft.framework.*"
	import="com.sinosoft.framework.utility.*"
	import="com.sinosoft.framework.data.*"
	import="java.util.*"
	import="com.wangjin.search.*"
	import="cn.com.sinosoft.action.shop.SalesVolumeAction"
%> 
<%@ taglib uri="controls" prefix="z"%>
<%	
	String query1 = new String();
	request.setCharacterEncoding(Constant.GlobalCharset);
	response.setContentType("text/html;charset=utf-8"); 

	String keyword = request.getParameter("keyword");
	String query = request.getParameter("query");
	query1 = query;
//	query="健康";
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
	
	if(StringUtil.isNotEmpty(query) && query.length()>=6){
		query = query.trim().substring(0,6);
		if(StringUtil.isDigit(query)){
			query = ArticleSearcher.getKeyWord(query);		
		} else {
			 %>
				<script type="text/javascript">
					window.location.href="<%=Config.getServerContext()%>/error.jsp?errormsg=数据格式非法!";
				</script>
			<%
			return;
		}
	}

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
	if (!strpage.matches("\\d+")||("0".equals(strpage)))
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
	System.out.println("query:"+query);
	System.out.println("query1:"+query1);
	map.put("query",query);
	map.put("query1",query1);
	map.put("page", strpage);
	map.put("id", id);
	map.put("site", site);
	map.put("order", order);
	
	Mapx map1 = ServletUtil.getParameterMap(request,false);	//相关问答搜索
	map1.put("query",query);
	map1.put("page", "1");									//防止问答部分随页数改变而改变
	map1.put("id", "52");
	map1.put("site", site);
	map1.put("order", "time");
	
	Mapx map2 = ServletUtil.getParameterMap(request,false);//相关知识搜索
	map2.put("query",query);
	map2.put("page", "1");									
	map2.put("id", "51");
	map2.put("site", site);
	map2.put("order", "time");
	
	Mapx map3 = ServletUtil.getParameterMap(request,false);//相关资讯搜索
	map3.put("query",query);
	map3.put("page", "1");
	map3.put("id", "53");
	map3.put("site", site);
	map3.put("order", "time");
	// 2012.11.19 add by meijunfeng <<20121108018-需求系统开发需求申请单-搜索页面>> start
	AddKeyword.addkeyword(query.replaceAll("'", "\\\\'"));
	// 2012.11.19 add by meijunfeng <<20121108018-需求系统开发需求申请单-搜索页面>> end
	SearchResult r = ArticleSearcher.proSearch(map);
	SearchResult r1 = ArticleSearcher.leftSearch(map1);
	SearchResult r2 = ArticleSearcher.leftSearch(map2);
	SearchResult r3 = ArticleSearcher.leftSearch(map3);
	if(r==null){
		out.println("<br><br><br><b><font color='red'>全文检索索引未建立。</font></b>");
		out.println("<br><br>请在“站点管理”-“站点列表”中选择相应站点，开启“自动建立索引”选项。");
		return;
	}
	DataTable dt = null;
	if(User.getValue("dt")!=null){
		dt = (DataTable)User.getValue("dt");
	}else{
		dt = r.Data;
	}
	int total = 0;
	if (dt != null) {
		total = dt.getRowCount();
	}
	int riskcodesint = dt.getRowCount();
	String riskcodes="";
	double usedTime = r.UsedTime;
	int NoResult = r.NoResult;		//用于控制当未找到产品时，显示未找到相关产品
	
	DataTable dt1 = r1.Data;
	int total1 = r1.Total;
	double usedTime1 = r1.UsedTime;
	
	DataTable dt2 = r2.Data;
	int total2 = r2.Total;
	double usedTime2 = r2.UsedTime;
	
	DataTable dt3 = r3.Data;
	int total3 = r3.Total;
	double usedTime3 = r3.UsedTime;
	
	DataTable dtKeyWord1 = ArticleSearcher.getKeyWord1();
	DataTable dtKeyWord2 = ArticleSearcher.getKeyWord2();
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<title><%=query%>_搜索结果第<%=((total == 0) ? 0: (pageIndex + 1))  %>页-开心保保险网</title>
<!--new_list.css产品列表;搜索结果页样式 -->
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css"/>
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_detail.css" />
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_list.css" />
<script type="">
var complicatedFlag = 'N';
</script>
<%@include file="../wwwroot/kxb/block/kxb_custom_header.shtml" %>
</head>
<body class="re-bg">
<!-- 顶部 开始 -->
<input type="hidden" name="NoResult" value="<%=query1%>" id="query1" />
<input type="hidden" name="NoResult" value="<%=NoResult%>" id="NoResultid" />
<input type="hidden" name="oldquery" value="<%=oldquery%>" id="oldqueryid" />
<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml"%>
<script type="text/javascript">document.getElementById("NoResultid").value = document.getElementById("oldqueryid").value;</script>
<div class="weaper g-list-tree search-tree">
    <div class="daohang">
        <span class="daohang_home"></span>
        <a target="_self" href="<%=Config.getFrontServerContextPath()%>/"><span class="orange">您的现在位置：首页</span></a><span class="separator">&gt;</span><span class="separator1"><%=query%></span>
    </div>
</div>
<div class="weaper">
	<div class="grid-c2-s6">
		<div class="col-main">
			<div class="main-wrap">
				<div class="g-search-mod">
					<form action="<%=Config.getFrontServerContextPath()%>/Search/Search.jsp">
					<div class="gsearch-box"><input type="text" class="gsearch-txt clear-none-xtx" name="query" id="key_input" onfocus="if (this.value == '请输入关键词') {this.value = '';}" onblur="if (this.value == '') {this.value = '请输入关键词';  }" value="<%=query%>"><span class="clear-txt" id="clear-txt" onclick="document.getElementById('key_input').value=''"></span><input type="submit" class="gsearch-btn f-ib" value="搜索" onclick="jQuery.ajaxLoadImg.show('searchLoad1');">
                      	<div class="clear"></div>
                    </div>
                    </form>
					<p class="search-tip" id="search_key2_0" style="display: none">没有找到与“<b><%=query%></b>”相关的内容</p>
					<p class="search-tip" id="search_key1">成功为您找到<%=total%>款“<b><%=query%></b>”相关的产品</p>
                  	<div class="search-tab" id="search_key2_1" style="display: none">
                    	换个搜索关键词试试？例如：<br>
                    <%
						if (dtKeyWord2 != null && dtKeyWord2.getRowCount() > 0) {
							int dtKeyWord2Count = dtKeyWord2.getRowCount();
							
							for (int i = 0; i < dtKeyWord2Count; i++) {
								DataRow drKeyWord2 = dtKeyWord2.getDataRow(i);
					
					%>
						<a href="<%=drKeyWord2.getString("url")%>"><%=drKeyWord2.getString("KeyWord")%></a>
					<%
							}
						}
					%>
                    	<p>或者，让我们的客服来帮助您！<span class="g-online" id="qqwap2">在线客服&gt;&gt;</span></p>
                  	</div>
                  	<%
                  	
						int pageCount = new Double(Math.ceil(total * 1.0 / pageSize))
								.intValue();
						int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
						int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
					%> 
					<input type="hidden" name="pageIndex" value="<%=pageIndex + 1%>" id="pageIndex" />
                  	<div id="divSearchOrder" class="div_search_Order">
            			<span onclick="doOrder1(this);" class="onesel" id="order_default">综合排序</span><span onclick="doOrder1(this);" class="two" id="order_Popular">人气</span><span onclick="doOrder1(this);" class="two" id="order_SalesVolume">销量</span><span onclick="doOrder1(this);" class="two" id="order_InitPrem">价格</span>
          			</div>
          			<div class="clear h10"></div>
          			<div id="products" class="shop_img_list cf">
          			<%
	
						int Start = pageIndex * pageSize;
						for (int i = Start; i < Start + pageSize && i < dt.getRowCount(); i++) {
							DataRow dr = dt.getDataRow(i);
							if (StringUtil.isNotEmpty(riskcodes)) {
								riskcodes += ",";
							}
							riskcodes += dr.getString("RiskCode");
					%>
					
					<div class="nlist_con cf">
						<div class="nlist_des">
                            <div class="icon_C<%=dr.getString("SupplierCode2")%> m-list-logo"></div>
                            <div class="m-list-cn">
                                <a class="nlist_title" target="_blank" href="<%=dr.getString("URL")%>"><%=dr.getString("Title")%></a>
                                <div class="shop_tj_shrq cf">
                                       
                                        <p class="shop_shrd_con"> <span class="shop_shrq_bg">适合人群</span>
                                          <%= dr.getString("AdaptPeopleInfoListV3")%>
                                        </p>
                                </div>
                            </div>
                            <div class="clear"></div>
                            <ul class="recommend_list">
                                <%=dr.getString("DutyHTMLV3")%>
                            </ul>
                            <div class="price_tj">
                                <span class="recom_xl" id="SalesV_<%=dr.getString("RiskCode")%>">(累计销量：加载中...)</span>
                                <span class="recom_xl" id="CommentV_<%=dr.getString("RiskCode")%>"><i><a class="shop_tj_num" onclick="openCommnet('<%=dr.getString("URL")%>')" href="javascript:void(0)"><i><%=SearchAPI.getCommentCount(dr.getString("RiskCode")) %></i>人已评价</a></i></span>
								<%
									String complateDate = dr.getString("ClaimsComplateDate");
									if ("1".equals(complateDate)) {
								%>
								<span class="recom_xl">拍拍速赔</span>
								<%}%>
							</div>
                        </div>
                        <div class="nlist_price">
                        	<p class="list_Sale tribe-action" id="Activity_<%=dr.getString("RiskCode")%>">
								<span id="Diy_Title_Activity_<%=dr.getString("RiskCode")%>" style="display: none;"></span><em
									id="Diy_Activity_<%=dr.getString("RiskCode")%>" style="display: none;"></em>
							</p>
                            <span name="Ajax_Prict_<%=dr.getString("RiskCode")%>" class="nlist_pay moneys"><em>￥</em><%=dr.getString("InitPrem")%></span>
                            <em class="nlist_pay_t moneys"><span name="Clear_Ajax_Prict_<%=dr.getString("RiskCode")%>"><% if (StringUtil.isNotEmpty(dr.getString("BasePremV3"))) {%>原价<%=dr.getString("BasePremV3")%><% }%></span></em>
                             <div class="remcon_desmore">
                                 <a rel="nofollow" href="<%=dr.getString("URL")%>" target="_blank">去看看</a>
                             </div>
                        </div>
				    </div>
				    <%
						}
					%>
				</div>
				</div>
				<div class="plpage">
					<div class="page_area" id="productsPageBar">
				<%
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
					%>
					</div>
					<div class="clear"></div>
        		</div>
		</div>
	</div>
	<div class="col-sub">
		<div class="l_boxss l_boxss2">
			<div class="c1"><em class="l_icon"></em>大家都在搜</div>
			<div class="g-peosearch">
				<div class=" tag_yun">
					<ul>
					<%
						if (dtKeyWord1 != null && dtKeyWord1.getRowCount() > 0) {
							int dtKeyWord1Count = dtKeyWord1.getRowCount();
							
							for (int i = 0; i < dtKeyWord1Count; i++) {
								DataRow drKeyWord1 = dtKeyWord1.getDataRow(i);
					
					%>
					<a class="" href="<%=drKeyWord1.getString("url")%>"><%=drKeyWord1.getString("KeyWord")%></a>
					<%
							}
						}
						
					%>
					</ul>
				</div>
			</div>
		</div>
		<div class="l_boxss l_boxss2">
        	<div class="c1"><em class="l_icon"></em>相关问答<a href="<%=Config.getFrontServerContextPath()%>/wenda/" class="g-more">更多&gt;&gt;</a></div>
        	<ul class="search_l_list">
        	<%
				List<String> idList1 = new ArrayList<String>();
				for (int i = 0; i < (dt1.getRowCount() > 5 ? 5 : dt1.getRowCount()); i++) {
					DataRow dr1 = dt1.getDataRow(i);
			%>
			<li><a href="<%=dr1.getString("URL")%>"><%=dr1.getString("Title")%></a></li>
			<%
			    	idList1.add(dr1.getString("id"));
				}
				if (dt1.getRowCount() < 5 && dt1.getRowCount() > 0) {
		
					DataTable dt_ques = ArticleSearcher.getQues(idList1);
					for (int i = 0; i < 5 - dt1.getRowCount(); i++) {
						DataRow dr4 = dt_ques.getDataRow(i);
			%>
			<li><a href="<%=dr4.getString("URL")%>"><%=dr4.getString("Title")%></a></li>
			<%
				 	}
		
				}
			%>
        	</ul>
        </div>
        <div class="l_boxss l_boxss2">
        	<div class="c1"><em class="l_icon"></em>相关知识<a href="<%=Config.getFrontServerContextPath()%>/zhishi/" class="g-more">更多&gt;&gt;</a></div>
            <ul class="search_l_list">
            <%
				List<String> idList2 = new ArrayList();
				for (int i = 0; i < (dt2.getRowCount() > 5 ? 5 : dt2.getRowCount()); i++) {
					DataRow dr2 = dt2.getDataRow(i);
			%>
			<li><a href="<%=dr2.getString("URL")%>"><%=dr2.getString("Title")%></a></li>
			<%
					idList2.add(dr2.getString("id"));
			
				}
				
				if (dt2.getRowCount() < 5 && dt2.getRowCount() > 0) {
		
					DataTable dt_knwn = ArticleSearcher.getKnwn(idList2);
					for (int i = 0; i < 5 - dt2.getRowCount(); i++) {
						DataRow dr5 = dt_knwn.getDataRow(i);
			%>
			<li><a href="<%=dr5.getString("URL")%>"><%=dr5.getString("Title")%></a></li>
			<%
				 	}
				}
			%>
            </ul>
        </div>
        <div class="l_boxss l_boxss2">
        	<div class="c1"><em class="l_icon"></em>相关资讯<a href="<%=Config.getFrontServerContextPath()%>/zixun/" class="g-more">更多&gt;&gt;</a></div>
          	<ul class="search_l_list">
          	<%
				List<String> idList3 = new ArrayList();
				for (int i = 0; i < (dt3.getRowCount() > 5 ? 5 : dt3.getRowCount()); i++) {
					DataRow dr3 = dt3.getDataRow(i);
			%>
			<li><a href="<%=dr3.getString("URL")%>"><%=dr3.getString("Title")%></a></li>
			<%
				    idList3.add(dr3.getString("id"));
				}
				if (dt3.getRowCount() < 5 && dt3.getRowCount() > 0) {
					DataTable dt_news = ArticleSearcher.getNews(idList3);
					for (int i = 0; i < 5 - dt3.getRowCount(); i++) {
						DataRow dr6 = dt_news.getDataRow(i);
			%>
			<li><a href="<%=dr6.getString("URL")%>"><%=dr6.getString("Title")%></a></li>
			<%
					}
			
				}
			%>
          	</ul>
        </div>
	</div>
</div>
</div>
<%@include file="../wwwroot/kxb/block/kxb_footer_new_common.shtml" %>
<%@include file="../wwwroot/kxb/block/community_v1.shtml" %>
<script type="text/javascript">
	var noresult = document.getElementById("NoResultid").value;
	if(noresult==1){
		document.getElementById("search_key1").style.display = "none";
		document.getElementById("search_key2_0").style.display = "block";
		document.getElementById("search_key2_1").style.display = "block";
	}else{
		document.getElementById("search_key1").style.display = "block";
		document.getElementById("search_key2_0").style.display = "none";
		document.getElementById("search_key2_1").style.display = "none";
	}	
function doOrder1(ele){
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	changeOrderInfoStyle(ele);
	var arr = new Array();
	var dc = new DataCollection();
	var pageIndex = 0;
	var ProductsOrder="";
	if (document.getElementById("order_Popular").className == "twoasc") {
		ProductsOrder= "Popular asc";
	} else if (document.getElementById("order_Popular").className == "twodesc") {
		ProductsOrder= "Popular desc";
	} else if (document.getElementById("order_SalesVolume").className == "twoasc") {
		ProductsOrder= "SalesVolume asc";
	} else if (document.getElementById("order_SalesVolume").className == "twodesc") {
		ProductsOrder = "SalesVolume desc";
	} else if (document.getElementById("order_InitPrem").className == "twoasc") {
		ProductsOrder = "InitPrem asc";
	} else if (document.getElementById("order_InitPrem").className == "twodesc") {
		ProductsOrder = "InitPrem desc";
	}
	var Request = new Object();
	Request = GetRequest();
	var query_0,id_0,site_0;
	query_0 = encodeURIComponent('<%=query%>');
	if(id_0==null||id_0==""){
		id_0="50";
	}
	site_0 = Request['site'];
	if(site_0==""||site_0==null){
		site_0="221";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/product_search_ajax!searchOrderRe.action?pageIndex='+pageIndex+'&channelsn='+channelsn+'&ProductsOrder='+ProductsOrder+'&query='+query_0+'&id='+id_0+'&site='+site_0,
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.status == "0"){
				alert(data.error);
			}else{
				document.getElementById("products").innerHTML = data.sb1;
				document.getElementById("productsPageBar").innerHTML = data.sb3;
			}
			
		}
	})
	

		
}

function GetRequest() {   
	   var url = location.search; //获取url中"?"符后的字串   
	   var theRequest = new Object();   
	   if (url.indexOf("?") != -1) {   
	      var str = url.substr(1);   
	      strs = str.split("&");   
	      for(var i = 0; i < strs.length; i ++) {   
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
	      }   
	   }   
	   return theRequest;   
	}

function doOrder2(num){
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	var arr = new Array();
	var dc = new DataCollection();
	var pageIndex = num;
	var ProductsOrder="";
	if (document.getElementById("order_Popular").className == "twoasc") {
		ProductsOrder= "Popular asc";
	} else if (document.getElementById("order_Popular").className == "twodesc") {
		ProductsOrder= "Popular desc";
	} else if (document.getElementById("order_SalesVolume").className == "twoasc") {
		ProductsOrder= "SalesVolume asc";
	} else if (document.getElementById("order_SalesVolume").className == "twodesc") {
		ProductsOrder = "SalesVolume desc";
	} else if (document.getElementById("order_InitPrem").className == "twoasc") {
		ProductsOrder = "InitPrem asc";
	} else if (document.getElementById("order_InitPrem").className == "twodesc") {
		ProductsOrder = "InitPrem desc";
	}
	var Request = new Object();
	Request = GetRequest();
	var query_0,id_0,site_0;
	query_0 = encodeURIComponent('<%=query%>');
	if(id_0==null||id_0==""){
		id_0="50";
	}
	site_0 = Request['site'];
	if(site_0==""||site_0==null){
		site_0="221";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/product_search_ajax!searchOrderRe.action?pageIndex='+pageIndex+'&channelsn='+channelsn+'&ProductsOrder='+ProductsOrder+'&query='+query_0+'&id='+id_0+'&site='+site_0,
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.status == "0"){
				alert(data.error);
			}else{
				document.getElementById("products").innerHTML = data.sb1;
				document.getElementById("productsPageBar").innerHTML = data.sb3;
			}
			
		}
	})
		
}
</script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/premcalculate.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/redesign/re_productCompare.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.soChange-min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/redesign/re_productlistv3.js"></script>
</body>
</html>