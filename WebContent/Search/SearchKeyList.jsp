<!DOCTYPE html >
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
	int riskcodesint = dt.getRowCount();
	String riskcodes="";
	int total = r.Total;
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
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<title><%=query%>_搜索结果第<%=((total == 0) ? 0: (pageIndex + 1))  %>页-开心保保险网</title>
<!--new_list.css产品列表;搜索结果页样式 -->
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_list.css">
<!--new_search.css搜索页面唯一调用样式-->
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_search.css">

<%@include file="../wwwroot/kxb/block/kxb_header_new_css.shtml" %>
<script type="">
var complicatedFlag = 'N';
</script>
<%@include file="../wwwroot/kxb/block/kxb_custom_header.shtml" %>
</head>
<body onload="salesVolumeLoad();">
<!-- 顶部 开始 -->
<input type="hidden" name="NoResult" value="<%=query1%>" id="query1" />
<input type="hidden" name="NoResult" value="<%=NoResult%>" id="NoResultid" />
<input type="hidden" name="oldquery" value="<%=oldquery%>" id="oldqueryid" />
<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml" %>
<script type="text/javascript">document.getElementById("NoResultid").value = document.getElementById("oldqueryid").value;</script>
<div class="wrapper">
<div class="daohang">
			<span class="daohang_home"></span>
		<a target="_self" href="<%=Config.getFrontServerContextPath()%>/">您现在的位置：首页</a><span class="separator">&gt;</span><p><%=query%></p>
		</div>
        <div class="content">
			<div class="content_left">
				<div class="search_l_box">
                	<h3 class="search_l_title">相关问答</h3>
                    <ul class="search_l_list">
                    <%
						for (int i = 0; i < (dt1.getRowCount()>5?5:dt1.getRowCount()); i++) {
							DataRow dr1 = dt1.getDataRow(i);
					%>     
                    	<li><a href="<%=dr1.getString("URL")%>"><%=dr1.getString("Title")%></a></li>
	                <%
						}
					%>        
                    </ul>
                </div>
                <div class="search_l_box">
               	  <h3 class="search_l_title">相关知识</h3>
                    <ul class="search_l_list">
                    <%
						for (int i = 0; i < (dt2.getRowCount()>5?5:dt2.getRowCount()); i++) {
							DataRow dr2 = dt2.getDataRow(i);
					%>    
                    	<li><a href="<%=dr2.getString("URL")%>"><%=dr2.getString("Title")%></a></li>
	                <%
						}
					%>       
                    </ul>
                </div>
                <div class="search_l_box">
               	  <h3 class="search_l_title">相关资讯</h3>
                    <ul class="search_l_list">
                    <%
						for (int i = 0; i < (dt3.getRowCount()>5?5:dt3.getRowCount()); i++) {
							DataRow dr3 = dt3.getDataRow(i);
					%>    
                    	<li><a href="<%=dr3.getString("URL")%>"><%=dr3.getString("Title")%></a></li>
	                <%
						}
					%>       
                    </ul>
                </div>
                  
            </div>
            <div class="content_right">
		
			  <div class="search_key" id="search_key1">相关<span><%=query%></span>保险产品</div>
              <div class="search_key" id="search_key2" style="display:none">未找到<span><%=query%></span>保险产品，为您精心挑选了一些产品</div>  
					
					<%
						int pageCount = new Double(Math.ceil(total * 1.0 / pageSize)).intValue();
						int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
						int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
//						String queryStr = "/Search/Search.jsp?query=" + utf8query ;
//						String queryStr = "/wj/Search/Search.jsp?id=50&site=221&query=" + utf8query ;		//测试用的
					%>
				<input type="hidden" name="pageIndex" value="<%=pageIndex+1%>" id="pageIndex" />
				<div id="divSearchOrder">
					<span onClick="defaultOrder(this);" class="onesel" id="order_default">默认排序</span><span onClick="doOrder1(this);" class="two" id="order_Popular">人气</span><span onClick="doOrder1(this);" class="two" id="order_SalesVolume">销量</span><span onClick="doOrder1(this);" class="two" id="order_InitPrem">价格</span>
				</div>
				
				<div id="products">
					<%
					int k = 0;
					int Start = pageIndex*pageSize;
					for (int i = Start; i < Start+pageSize && i<dt.getRowCount(); i++) {						
						DataRow dr = dt.getDataRow(i);	
						if (StringUtil.isNotEmpty(riskcodes)) {
							riskcodes += ",";
						}
						 riskcodes += dr.getString("RiskCode");
					%>      
						<div class="product_title">
							<span class="CInsuranceCompany icon_C<%=dr.getString("SupplierCode2")%>"></span><span class="productName"> <a target="_blank" href="<%=dr.getString("URL")%>"><h2 class="ziti"><%=dr.getString("Title")%></h2></a>
							</span> <span class="SalesVolume" id="SalesV_<%=dr.getString("RiskCode")%>">(累计销量：加载中...)</span><span id="productIntegral_<%=dr.getString("RiskCode")%>" style="display: none;"></span>
						</div>
                        <div class="product_condition">
							<%=dr.getString("CalHTML2")%>
						</div>
						
						<div class="product_info">
						<div class="product_info_bor">
							<div class="prodcutMark">
								<ul class="price"><%=dr.getString("prodcutMarkPrice")%></ul>
								<ul class="btn">
									<li class="btn1"><span onClick="chakan('<%=dr.getString("URL")%>')">查看详情</span></li>
								</ul>
							</div>
						  </div>
							<div class="AdaptPeopleInfo"><%=dr.getString("AdaptPeopleInfo")%></div>
							
							<div class="productFeature"><%=dr.getString("FEMRiskBrightSpot")%></div>
							<%=dr.getString("DutyHTML")%>
						</div>	
					<%	
						}
					%>		
					</div>
					
				<div id="productsPageBar" >
				<%
				StringBuffer sb = new StringBuffer();
				sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
				sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
				sb.append("共" + total + "条记录，每页" + pageSize + "条，当前第<span class='fc_ch1'>" + ((total == 0) ? 0: (pageIndex + 1)) 
						+ "</span>/<span class='fc_ch1'>" + pageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
				if (pageIndex > 0) {
				
						
					sb.append("<a href='"+SearchAPI.getNewTagPageURL(map, 1)+"'><span class='fc_ch1'>第一页</span></a>|");
					if(1==pageIndex){
						sb.append("<a href='"+SearchAPI.getNewTagPageURL(map, pageIndex)+"'><span class='fc_ch1'>上一页</span></a>|");
					}else{
						sb.append("<a href='"+SearchAPI.getNewTagPageURL(map, pageIndex)+"'><span class='fc_ch1'>上一页</span></a>|");
					}
					
				} else {
					sb.append("<span class='fc_hui2'>第一页</span>|");
					sb.append("<span class='fc_hui2'>上一页</span>|");
				}
				if (pageIndex + 1 != pageCount && pageCount>0) {
					sb.append("<a href='"+SearchAPI.getNewTagPageURL(map, pageIndex+2)+"'><span class='fc_ch1'>下一页</span></a>|");
					sb.append("<a href='"+SearchAPI.getNewTagPageURL(map, pageCount)+"'><span class='fc_ch1'>最末页</span></a>");
				} else {
					sb.append("<span class='fc_hui2'>下一页</span>|");
					sb.append("<span class='fc_hui2'>最末页</span>");
				}

				sb.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
				sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");
				
				sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>" + pageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){window.location='"+SearchAPI.getNewTagPageURL(map, 1)+"'}else{jump();}}\" style='' value='跳转'></td>");
				sb.append("</tr></table>");
				JspWriter out1 = pageContext.getOut();		
				out1.print(sb);
				%>

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
		document.getElementById("search_key2").style.display = "block";
	}else{
		document.getElementById("search_key1").style.display = "block";
		document.getElementById("search_key2").style.display = "none";
	}	
</script>
<script type="text/javascript">
function salesVolumeLoad() {
	
	<%
		SalesVolumeAction salesVolumeAction = new SalesVolumeAction();
		String salesV = salesVolumeAction.search1(riskcodes);;
	%>
	var salesVolumes = "<%=salesV%>";
	if (salesVolumes != null && salesVolumes != "") {
		var salesVolume = salesVolumes.split(",");
		for (var i = 0; i < salesVolume.length; i++) {
			document.getElementById(salesVolume[i].split(":")[0]).innerHTML="(累计销量："+salesVolume[i].split(":")[1]+")";
		}
	}
}
function chakan(str)
{
	window.open(str);
}

function jump(){
	var JumpPage = document.getElementById('_PageBar_Index_').value;
	var query = document.getElementById('query1').value;
	
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/product_search_ajax!Jump.action?JumpPage='+JumpPage+'&query='+query,
		dataType: "json",
		async: false,
		success: function(data) {
			if (data.Status == "1") {
				window.location = data.tagHtml;
			} else {
				 alert(data.error);
			}
		}
	})
//	return JumpPage;
}

function newJump(){
	var JumpPage = document.getElementById('_PageBar_Index_').value;
	doOrder2(JumpPage);
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
	
function defaultOrder(ele){
//	var queryStr = document.getElementById("queryStr").value;
	var pageIndex = document.getElementById("pageIndex").value;
	var query = document.getElementById('query1').value;
	changeOrderInfoStyle(ele);
	var Request = new Object();
	Request = GetRequest();
	var query_0,id_0,site_0;
	query_0 = Request['query'];
	id_0 = Request['id'];
	site_0 = Request['site'];
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/product_search_ajax!SearchOrderRe.action?pageIndex='+pageIndex+'&query='+query+'&query='+query_0+'&id='+id_0+'&site='+site_0,
		dataType: "json",
		async: false,
		success: function(data) {
			if (data.Status == "1") {
				window.location = data.tagHtml;
			} else {
				 alert(data.error);
			}
			
		}
	})
	

}

function doOrder1(ele){
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
	var channelsn="";
	var channelsn="";
	var Request = new Object();
	Request = GetRequest();
	var query_0,id_0,site_0;
	query_0 = Request['query'];
	id_0 = Request['id'];
	site_0 = Request['site'];
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/product_search_ajax!SearchOrder.action?pageIndex='+pageIndex+'&channelsn='+channelsn+'&ProductsOrder='+ProductsOrder+'&query='+query_0+'&id='+id_0+'&site='+site_0,,
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
	query_0 = Request['query'];
	id_0 = Request['id'];
	site_0 = Request['site'];
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/product_search_ajax!SearchOrder.action?pageIndex='+pageIndex+'&channelsn='+channelsn+'&ProductsOrder='+ProductsOrder+'&query='+query_0+'&id='+id_0+'&site='+site_0,,
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
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/template/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/template/common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/template/shop/js/header1.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/productCompare.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/premcalculate.js"></script>
<%@include file="../wwwroot/kxb/block/community_v1.shtml" %>
</body>
</html>