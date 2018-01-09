<!DOCTYPE html>
<%@ page session="false" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.search.*"
	import="com.sinosoft.cms.api.*" import="com.sinosoft.framework.*"
	import="com.sinosoft.framework.utility.*"
	import="com.sinosoft.framework.data.*" import="java.util.*"
	import="com.sinosoft.cms.pub.SiteUtil" import="com.wangjin.search.*"
	import="cn.com.sinosoft.action.shop.SalesVolumeAction"%>
<%@ taglib uri="controls" prefix="z"%>
<%
	HttpSession session = request.getSession();
	request.setCharacterEncoding(Constant.GlobalCharset);
	response.setContentType("text/html;charset=utf-8");
	String riskcodes = "";
	String keyword = request.getParameter("keyword");
	String query = request.getParameter("query");
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

	if (StringUtil.isNotEmpty(query)) {
		utf8query = java.net.URLEncoder.encode(query, "utf-8");
		query = java.net.URLDecoder.decode(query, "utf-8");
		if (null != oldquery) {
			utf8Oldquery = java.net.URLEncoder
					.encode(oldquery, "utf-8");
			oldquery = java.net.URLDecoder.decode(oldquery, "utf-8");
		}
	}

	if (StringUtil.isEmpty(query)) {
		query = keyword;//和旧版本保持一致
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

	String strpage = request.getParameter("page");
	strpage = (StringUtil.isEmpty(strpage)) ? "1" : strpage;
	if (!strpage.matches("\\d+") || ("0".equals(strpage))) {
		StringUtil.invalidInputCope(out, "页码不合法！");
		return;
		//strpage = "1";
	}

	String order = request.getParameter("order");
	order = StringUtil.isEmpty(order) ? "rela" : order;
	if (!order.matches("(rela)|(time)")) {
		StringUtil.invalidInputCope(out, "排序方式不合法！");
		return;
		//order = "time";
	}

	String time = request.getParameter("time");
	time = StringUtil.isEmpty(time) ? "all" : time;
	if (!time.matches("(all)|(week)|(month)|(quarter)")) {
		StringUtil.invalidInputCope(out, "时间类型不合法！");
		return;
		//time = "all";
	}

     String site = request.getParameter("site");
	site = StringUtil.isEmpty(site) ? "" : site;
	if (!site.matches("\\d+")) {
		StringUtil.invalidInputCope(out, "站点id不合法！");
		return;
		//site = "221";
	}

	String id = request.getParameter("id");
	id = StringUtil.isEmpty(id) ? "" : id;
	if (!id.matches("\\d+")) {
		StringUtil.invalidInputCope(out, "索引id不合法！");
		return;
		//id = "20";
	}
	String size = request.getParameter("size");
	size = StringUtil.isEmpty(size) ? "10" : size;

	int pageSize = Integer.parseInt(size);
	int pageIndex = Integer.parseInt(strpage) - 1;

	Mapx map = ServletUtil.getParameterMap(request, false);
	map.put("query", query);
	map.put("page", strpage);
	map.put("id", id);
	map.put("site", site);
	map.put("order", order);

	Mapx map1 = ServletUtil.getParameterMap(request, false); //相关问答搜索
	map1.put("query", query);
	map1.put("page", "1"); //防止问答部分随页数改变而改变
	map1.put("id", "52");
	map1.put("site", site);
	map1.put("order", "time");

	Mapx map2 = ServletUtil.getParameterMap(request, false);//相关知识搜索
	map2.put("query", query);
	map2.put("page", "1");
	map2.put("id", "51");
	map2.put("site", site);
	map2.put("order", "time");

	Mapx map3 = ServletUtil.getParameterMap(request, false);//相关资讯搜索
	map3.put("query", query);
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
	if (r == null) {
		out
				.println("<br><br><br><b><font color='red'>全文检索索引未建立。</font></b>");
		out.println("<br><br>请在“站点管理”-“站点列表”中选择相应站点，开启“自动建立索引”选项。");
		return;
	}
	DataTable dt = null;
	if (User.getValue("dt") != null) {
		dt = (DataTable) User.getValue("dt");
	} else {
		dt = r.Data;
	}
	int total = r.Total;
	double usedTime = r.UsedTime;
	int NoResult = r.NoResult; //用于控制当未找到产品时，显示未找到相关产品
	//	User.setValue("dt",dt);

	DataTable dt1 = r1.Data;
	int total1 = r1.Total;
	double usedTime1 = r1.UsedTime;

	DataTable dt2 = r2.Data;
	int total2 = r2.Total;
	double usedTime2 = r2.UsedTime;

	DataTable dt3 = r3.Data;
	int total3 = r3.Total;
	double usedTime3 = r3.UsedTime;
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<title><%=oldquery%>_搜索结果第<%=((total == 0) ? 0 : (pageIndex + 1))%>页-开心保保险网</title>
<!--new_list.css产品列表;搜索结果页样式 -->
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_listv2.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<!--new_search.css搜索页面唯一调用样式-->
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_search.css">
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css">

<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml"%>
<style>
.remcon_desmore {
    padding-right: 88px;
}
</style>
<%@include file="../wwwroot/kxb/block/kxb_custom_header.shtml" %>
</head>
<body onload="salesVolumeLoad();">
<!-- 顶部 开始 -->
<input type="hidden" name="NoResult" value="<%=NoResult%>"
	id="NoResultid" />
<input type="hidden" name="oldquery" value="<%=oldquery%>"
	id="oldqueryid" />
<input type="hidden" name="page" value="<%=strpage%>" id="pageid" />
<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml"%>
<div class="wrapper">
<div class="daohang"><span class="daohang_home"></span> <a
	target="_self" href="<%=Config.getFrontServerContextPath()%>/">您现在的位置：首页</a><span
	class="separator">&gt;</span>
<p><%=oldquery%></p>
</div>
<div class="content">
<div class="grid-c2-s6f">

<div class="col-main">
<div class="main-wrap">
<div class="search_key" id="search_key1">相关<span><%=oldquery%></span>保险产品</div>
<div class="search_key" id="search_key2" style="display: none">未找到<span><%=oldquery%></span>保险产品，为您精心挑选了一些产品</div>

<%
	int pageCount = new Double(Math.ceil(total * 1.0 / pageSize))
			.intValue();
	int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
	int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
	String queryStr = "/Search/Search.jsp?query=" + utf8query;
	//						String queryStr = "/wj/Search/Search.jsp?id=50&site=221&query=" + utf8query ;		//测试用的
%> <input type="hidden" name="queryStr" value="<%=queryStr%>"
	id="queryStr" /> <input type="hidden" name="pageIndex"
	value="<%=pageIndex + 1%>" id="pageIndex" />
<div id="divSearchOrder"><span onClick="defaultOrder(this);"
	class="onesel" id="order_default">默认排序</span><span
	onClick="doOrder1(this);" class="two" id="order_Popular">人气</span><span
	onClick="doOrder1(this);" class="two" id="order_SalesVolume">销量</span><span
	onClick="doOrder1(this);" class="two" id="order_InitPrem">价格</span></div>

<div id="products">
<%
	//					int k = 0;
	int Start = pageIndex * pageSize;
	for (int i = Start; i < Start + pageSize && i < dt.getRowCount(); i++) {
		DataRow dr = dt.getDataRow(i);
		if (StringUtil.isNotEmpty(riskcodes)) {
			riskcodes += ",";
		}
		riskcodes += dr.getString("RiskCode");
%>
<div class="nlist_con cf">
             	    <div class="shop_nlist_img">
                  <p id="Activity_<%=dr.getString("RiskCode")%>">
               <%=dr.getString("ProductActive")%>
              </p>
              	<a rel="nofollow" target="_blank" href="<%=dr.getString("URL")%>"><img width="190" height="190" alt="<%=dr.getString("Title")%>" src="<%=dr.getString("LogoLink")%>" class="lazy" data-original="<%=dr.getString("LogoLink")%>" style="display: inline;"></a>
              </div>
              <div class="nlist_des">
              	<a href="<%=dr.getString("URL")%>" target="_blank" class="nlist_title"><%=dr.getString("Title")%></a>
              	<div class="shop_tj_shrq cf">
                             <span class="shop_shrq_bg">适合人群</span>
                             <%
                                
                                String adap = dr.getString("AdaptPeopleInfoListV3");
                                if(adap.length()>76){
                                	adap = adap.substring(0,75)+"...";
                                }
                                
                                
                                %>
                             <p class="shop_shrd_con">
                                <%=adap%>
                             </p>
                     </div>
                     <div class="clear"></div>
                    <ul class="recommend_list">
                   <%=dr.getString("DutyHTMLV3")%>
              </ul>
                     <div class="nlist_price">
                             
                              <span class="nlist_pay moneys" name="Ajax_Prict_<%=dr.getString("RiskCode")%>">￥<%=dr.getString("InitPrem")%></span> 
                              <em class="nlist_pay_t moneys"><span style="display:none" name="Clear_Ajax_Prict_<%=dr.getString("RiskCode")%>"><%=dr.getString("BasePremV3")%></span></em>
                             <div class="price_tj">
                                 <span class="recom_xl" id="SalesV_<%=dr.getString("RiskCode")%>">(累计销量：加载中...)</span>
                                 <span class="recom_xl" id="CommentV_<%=dr.getString("RiskCode")%>">
                                 <i>
                                     	（<a class="shop_tj_num" href="javascript:void(0)" onclick="openCommnet('<%=dr.getString("URL")%>')"><%=SearchAPI.getCommentCount(dr.getString("RiskCode")) %></a>）
                                 </i> 条评论
                                 </span>
                             </div>

                             <span class="remcon_desmore">
                                 <label class="nlist_add_db" style="display:none">
                                     <input type="checkbox" onclick="showcp('<%=dr.getString("Title")%>','<%=dr.getString("LogoLink")%>','<%=dr.getString("RiskCode")%>','<%=dr.getString("prop6")%>','<%=dr.getString("ProductType")%>','<%=dr.getString("LogoLink")%>','<%=dr.getString("InitPrem")%>');">加入对比</label>
                                 <a target="_blank" href="<%=dr.getString("URL")%>">去看看</a>
                             </span>
                         </div>
           </div>
</div>



<%
	}
%>

</div>
</div>
</div>
<div class="col-sub">
<div class="search_l_box">
<h3 class="search_l_title">相关问答</h3>
<ul class="search_l_list">
	<%
	List<String> idList1 = new ArrayList();
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
<div class="search_l_box">
<h3 class="search_l_title">相关知识</h3>
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
<div class="search_l_box">
<h3 class="search_l_title">相关资讯</h3>
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
<div id="productsPageBar">
<%
	StringBuffer sb = new StringBuffer();
	sb
			.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
	sb
			.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
	sb.append("共" + total + "条记录，每页" + pageSize
			+ "条，当前第<span class='fc_ch1'>"
			+ ((total == 0) ? 0 : (pageIndex + 1))
			+ "</span>/<span class='fc_ch1'>" + pageCount
			+ "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
	if (pageIndex > 0) {

		sb.append("<a href='" + queryStr
				+ "'><span class='fc_ch1'>第一页</span></a>|");
		if (1 == pageIndex) {
			sb.append("<a href='" + queryStr
					+ "'><span class='fc_ch1'>上一页</span></a>|");
		} else {
			sb.append("<a href='" + queryStr + "&page=" + pageIndex
					+ "'><span class='fc_ch1'>上一页</span></a>|");
		}

	} else {
		sb.append("<span class='fc_hui2'>第一页</span>|");
		sb.append("<span class='fc_hui2'>上一页</span>|");
	}
	if (pageIndex + 1 != pageCount && pageCount > 0) {
		sb.append("<a href='" + queryStr + "&page=" + (pageIndex + 2)
				+ "'><span class='fc_ch1'>下一页</span></a>|");
		sb.append("<a href='" + queryStr + "&page=" + pageCount
				+ "'><span class='fc_ch1'>最末页</span></a>");
	} else {
		sb.append("<span class='fc_hui2'>下一页</span>|");
		sb.append("<span class='fc_hui2'>最末页</span>");
	}

	sb
			.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
	sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

	sb
			.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>"
					+ pageCount
					+ "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){window.location='"
					+ queryStr
					+ "'}else{jump('"
					+ queryStr
					+ "');}}\" style='' value='跳转'></td>");
	sb.append("</tr></table>");
	JspWriter out1 = pageContext.getOut();
	out1.print(sb);
%>
</div>

</div>
</div>
<script type="text/javascript">
	var noresult = document.getElementById("NoResultid").value;
	if(noresult==1){
		document.getElementById("search_key1").style.display = "none";
		document.getElementById("search_key2").style.display = "block";
	}else{
		document.getElementById("search_key1").style.display = "block";
		document.getElementById("search_key2").style.display = "none";
	}	
</script>
<script type="text/javascript">
function salesVolumeLoad() {
	
	<%SalesVolumeAction salesVolumeAction = new SalesVolumeAction();
			String salesV = salesVolumeAction.search1(riskcodes);
			;%>
	var salesVolumes = "<%=salesV%>";
	if (salesVolumes != null && salesVolumes != "") {
		var salesVolume = salesVolumes.split(",");
		for (var i = 0; i < salesVolume.length; i++) {
			if (document.getElementById(salesVolume[i].split(":")[0]) != null) {
				document.getElementById(salesVolume[i].split(":")[0]).innerHTML="(累计销量："+salesVolume[i].split(":")[1]+")";
			}
		}
	}
}

function chakan(str)
{
	window.open(str);
}

function jump(queryStr){
	var JumpPage = document.getElementById('_PageBar_Index_').value;
	window.location = queryStr+"&page="+JumpPage;
}

function newJump(){
	var JumpPage = document.getElementById('_PageBar_Index_').value;
	doOrder2(JumpPage);
}


function defaultOrder(ele){
	var queryStr = document.getElementById("queryStr").value;
	var pageIndex = document.getElementById("pageIndex").value;
	changeOrderInfoStyle(ele);
	window.location = queryStr+"&page="+(pageIndex);
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
	
function doOrder1(ele){
	
	changeOrderInfoStyle(ele);
	var arr = new Array();
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
	var channelsn="";
	var Request = new Object();
	Request = GetRequest();
	var query_0,id_0,site_0;
	query_0 = Request['query'];
	if(id_0==null||id_0==""){
		id_0="50";
	}
	site_0 = Request['site'];
	if(site_0==""||site_0==null){
		site_0="";
	}else{
		site_0="site";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/product_search_ajax!SearchOrderRe.action?pageIndex='+pageIndex+'&channelsn='+channelsn+'&ProductsOrder='+ProductsOrder+'&query='+query_0+'&id='+id_0+'&site='+site_0,
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


function doOrder2(num){
	
	var arr = new Array();
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
	var channelsn="";
	var Request = new Object();
	Request = GetRequest();
	var query_0,id_0,site_0;
	query_0 = Request['query'];
	if(id_0==null||id_0==""){
		id_0="50";
	}
	site_0 = Request['site'];
	if(site_0==""||site_0==null){
		site_0="";
	}else{
		site_0="site";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/product_search_ajax!SearchOrderRe.action?pageIndex='+pageIndex+'&channelsn='+channelsn+'&ProductsOrder='+ProductsOrder+'&query='+query_0+'&id='+id_0+'&site='+site_0,
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
<%@include file="../wwwroot/kxb/block/kxb_footer_new_common.shtml" %>
<%@include file="../wwwroot/kxb/block/community_v1.shtml" %>
<div class="clear"></div>
<div class="clear"></div>
    <div id="cpdiv" class="cpdiv">
            <div class="comparediv">
                <h3>产品比较</h3>
                <div class="closebtn">
                    <span onclick="close_h()" class="close_compare"></span>
                </div>
                <div class="comparedivcont">
                    <div class="comparedetail" style="display: none;" id="cpprodu1">
                           <img id="productLogoA" width="73" height="73" class="compare_img" alt="" src="<%=Config.getValue("StaticResourcePath") %>/images/list/zd_img_03.jpg" alt="">
                        <input type="hidden" name="productCodeA" id="productCodeA" />
                        <p>
                            <span id="productnameA" class="compare_name"></span>
                            <span id="productPremA" class="compare_pric" id=""></span>
                            <span class="dblue cpdelico" onclick="closecp1()"></span>
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div class="comparedetail" style="display: none;" id="cpprodu2">
                         <img id="productLogoB" width="73" height="73" class="compare_img" alt="" src="<%=Config.getValue("StaticResourcePath") %>/images/list/zd_img_03.jpg" alt="">
                        <input type="hidden" name="productCodeB" id="productCodeB" />
                       
                        <p>
                            <span id="productnameB" class="compare_name"></span>
                            <span id="productPremB" class="compare_pric"></span>
                            <span class="dblue cpdelico" onclick="closecp2()"></span>
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div class="comparedetail" style="display: none;" id="cpprodu3">
                        <input type="hidden" name="productCodeC" id="productCodeC" />
                        <img id="productLogoC" width="73" height="73" class="compare_img" alt="" src="<%=Config.getValue("StaticResourcePath") %>/images/list/zd_img_03.jpg" alt="">
                        <p>
                            <span id="productnameC"  class="compare_name"></span>
                            <span id="productPremC" class="compare_pric"></span>
                            <span class="dblue cpdelico" onclick="closecp3()"></span>
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div class="comparedetail" style="display: none;" id="cpprodu4">
                        <input type="hidden" name="productCodeD" id="productCodeD" />
                        <img id="productLogoD" width="73" height="73" class="compare_img" alt="" src="<%=Config.getValue("StaticResourcePath") %>/images/list/zd_img_03.jpg" alt="">
                        <p>
                            <span id="productnameD" class="compare_name"></span>
                            <span id="productPremD" class="compare_pric"></span>
                            <span class="dblue cpdelico" onclick="closecp4()"></span>
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div class="comparebtn">

                        <span class="compare_submit" onclick="javascript:begincompare('<%=Config.getServerContext()%>');subclosecp();" titile="提交对比">提交对比</span>
                        <span class="compare_none" onclick="close_all();">清空</span>
                    </div>
                    <div class="clear"></div>
                </div>
                <span class="cpdiv_jao"></span>
            </div>
            
        </div>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.qtip.js"></script>
	<script	type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/iframeTools.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/premcalculate.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/productCompare.js"></script>
	<script language="JavaScript" src="<%=Config.getValue("JsResourcePath")%>/js/tabchange.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.soChange-min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/productlistv3.js"></script>
</body>
</html>