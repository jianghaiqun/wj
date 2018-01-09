<!DOCTYPE html >
<%@ page session="false" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	import="com.sinosoft.framework.*"
	import="java.util.*"
	import="com.sinosoft.framework.utility.*"
	import="com.sinosoft.framework.data.*"
	import="com.sinosoft.schema.FEMSearchConditionInfoSchema"
	import="com.sinosoft.schema.FEMSearchConditionInfoSet"
	import="com.sinosoft.cms.api.SearchAPI"
	import="java.io.UnsupportedEncodingException"
%>
<%@ taglib uri="controls" prefix="z"%>
<%	
	JspWriter out1 = pageContext.getOut();
	String strpage = request.getParameter("page");
	strpage = (StringUtil.isEmpty(strpage))?"1":strpage;
	if (!strpage.matches("\\d+")||("0".equals(strpage)))
	{
		StringUtil.invalidInputCope(out, "页码不合法！");
		return;
		//strpage = "1";
	}
	String tERiskSubType = (StringUtil.isNotEmpty(request.getParameter("productType")))?request.getParameter("productType"):"";
	String age = (StringUtil.isNotEmpty(request.getParameter("age")))?request.getParameter("age"):"";
	String period =(StringUtil.isNotEmpty(request.getParameter("period")))?request.getParameter("period"):"";
    //本地使用代码	
	//String travelAddress = (StringUtil.isNotEmpty(new String(request.getParameter("travelAddress").getBytes("ISO-8859-1"),"UTF-8")))?new String(request.getParameter("travelAddress").getBytes("ISO-8859-1"),"UTF-8"):"";
	//35使用代码
	String travelAddress = request.getParameter("travelAddress");
	if(StringUtil.isNotEmpty(travelAddress)){
		try {
			travelAddress = java.net.URLDecoder.decode(travelAddress, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	} else {
		travelAddress = "";
	}
	String features = (StringUtil.isNotEmpty(request.getParameter("features")))?request.getParameter("features"):"";
	String prem = (StringUtil.isNotEmpty(request.getParameter("prem")))?request.getParameter("prem"):"";
	String nERiskSubType="";
	if(StringUtil.isNotEmpty(tERiskSubType)){
		nERiskSubType = tERiskSubType.substring(0, 1);
	}
	
	String ERiskSubTypeName = SearchAPI.getERiskSubTypeName(tERiskSubType);
	
	DataTable dt = new DataTable(); 
	String travelSearchCode = SearchAPI.getSearchCodeByTravelAddress(travelAddress);
	String ageSearchCode = SearchAPI.getSearchIDBySearchCode(nERiskSubType,age);
	String featuresSearchCode = SearchAPI.getSearchIDBySearchCode(nERiskSubType,features);
	String premSearchCode = SearchAPI.getSearchIDBySearchCode(nERiskSubType,prem);
	
	dt = SearchAPI.getSearchProduct(tERiskSubType,ageSearchCode,period,travelSearchCode,featuresSearchCode,premSearchCode);
	
	Map<String,String> searchValueMap = new HashMap<String, String>();
	if (StringUtil.isNotEmpty(travelSearchCode)) {
		String searCode = SearchAPI.getSearchValue(travelSearchCode);
		if (StringUtil.isNotEmpty(searCode)) {
			searchValueMap.put(searCode.split("@")[0], searCode.split("@")[1]);
		}
	}
	if (StringUtil.isNotEmpty(ageSearchCode)) {
		String searCode = SearchAPI.getSearchValue(ageSearchCode);
		if (StringUtil.isNotEmpty(searCode)) {
			searchValueMap.put(searCode.split("@")[0], searCode.split("@")[1]);
		}
	}
	if (StringUtil.isNotEmpty(featuresSearchCode)) {
		String searCode = SearchAPI.getSearchValue(featuresSearchCode);
		if (StringUtil.isNotEmpty(searCode)) {
			searchValueMap.put(searCode.split("@")[0], searCode.split("@")[1]);
		}
	}
	if (StringUtil.isNotEmpty(premSearchCode)) {
		String searCode = SearchAPI.getSearchValue(premSearchCode);
		if (StringUtil.isNotEmpty(searCode)) {
			searchValueMap.put(searCode.split("@")[0], searCode.split("@")[1]);
		}
	}
	boolean noProduct = false;
	if(dt==null || dt.getRowCount()<=0){
		noProduct = true;
		dt = SearchAPI.getDefaltProduct();
	}
	int total = dt.getRowCount();
	String size = request.getParameter("size");
	size  = StringUtil.isEmpty(size)?"10":size;
	int pageSize = Integer.parseInt(size);
	int pageIndex = Integer.parseInt(strpage) - 1;
	
	String hotERiskSubType = tERiskSubType;
	Map<String,String> searchConMap = SearchAPI.getNewlistSearchCondition(nERiskSubType,tERiskSubType,hotERiskSubType,searchValueMap);
	if (searchConMap != null && !searchConMap.isEmpty()) {
		hotERiskSubType = searchConMap.get("hotERiskSubType");
	}
	String catalogName = SearchAPI.getCatalogName(hotERiskSubType);
	// 获取推荐产品
	DataTable dt4 = new DataTable(); 
	dt4 = SearchAPI.getRecommendProduct(hotERiskSubType);
	String summarize = SearchAPI.getSummarize(hotERiskSubType);
%>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=ERiskSubTypeName %>-开心保保险网</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css"/>
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css"/>
<link rel="stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_list.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_detail.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<%@include file="../wwwroot/kxb/block/kxb_custom_header.shtml" %>
</head>
<body class="re-bg">
	<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml" %>
	<div class="re-list-banner <%=SearchAPI.getListColor(hotERiskSubType)%>">
		<div class="weaper g-list-tree">
			<div class="daohang">
				<span class="daohang_home"></span>
				<a target="_self" href="<%=Config.getFrontServerContextPath()%>/">您现在的位置：首页</a><span class="separator">&gt;</span><span class="separator1"><%=catalogName%></span>
			</div>
		</div>
		<div class="weaper">
			<!-- 栏目介绍 -->
			<div class="m-list-desc f_mi">
				<p>
					<em><%=catalogName%></em><%=summarize%>
				</p>
			</div>
			<!-- 栏目推荐 -->
			<div class="g-list-banner">
				<span class="m-list-tip"></span>
				<div class="changeBox_a2" id="change_34">
			 <% if (dt4 != null && dt4.getRowCount() > 0) {
				 int dt4Count = dt4.getRowCount();
				 for (int i = 0; i < dt4Count; i++) {
					if (i==0) {
			 %>
					 <div class="changeDiv" style="display: block">
			 <%			
					} else {
			 %>
					<div class="changeDiv">
			 <%
					}
			 %>
					<div class="m-banner-box">
						<div class="m-ban-left">
							<a href="<%=dt4.get(i).getString("URL")%>?link_id=recom" class="f_mi m-ban-tit"
								target="_blank"
								onclick="javascript:void(0);return(VL_FileDL(this));return false;"
								exturl="http://www.kaixinbao.com/recommend"
								vlpageid="recommend"><%=dt4.get(i).getString("ProductName")%></a>
							<p class="m-ban-p"><%=dt4.get(i).getString("AdaptPeopleInfoV3")%></p>
							<p class="m-tj-list"><%=dt4.get(i).getString("RecommendInfo")%></p>
						</div>
						<div class="m-abn-right">
							<dl class="m-bar-style">
								<dd class="m-bar-num">
									<span id="SalesU_<%=dt4.get(i).getString("ProductID")%>">(累计销量：加载中...)</span>
								</dd>
								<dd class="m-bar-money">
									<i>￥</i><span name="R_Ajax_Prict_<%=dt4.get(i).getString("ProductID")%>"><%=dt4.get(i).getString("InitPrem")%></span>
								</dd>
								<dd class="m-bar-rmoney">
									<span name="Clear_Ajax_Prict_<%=dt4.get(i).getString("ProductID")%>">
										<% String initPrem = "￥"+dt4.get(i).getString("InitPrem");
										String basePrem = dt4.get(i).getString("BasePremV3");
										if(!initPrem.equals(basePrem) && basePrem!=null &&!"".equals(basePrem)){ out1.print("原价："+basePrem); } %></span>
								</dd>
								<dd>
									<a href="<%=dt4.get(i).getString("URL")%>?link_id=recom" target="_blank"
										class="m-bar-go"
										onclick="javascript:void(0);return(VL_FileDL(this));return false;"
										exturl="http://www.kaixinbao.com/recommend"
										vlpageid="recommend" rel="nofollow">去看看&gt;</a>
								</dd>
							</dl>
						</div>
					</div>
					</div>
			 <%		   
				 }
			 %>
					<ul class="ul_change_a3">
						<% for(int i = 0; i < dt4Count; i++) {%>
						<li><span>&nbsp;</span></li>
						<% }%>
					</ul>
			<%		   
				}
			 %>
					
				</div>
			</div>
		</div>
	</div>
	<div class="weaper m-list-pos">
		<div class="content">
			<div class="grid-c2-s6f">
				<div class="col-main">
					<div class="main-wrap">
						<div class="top_b">
							<div id="load_con" style="display: none;"></div>
							<div class="m-jxw"></div>
							<%		
							out1.print(searchConMap.get("SearchCondition"));
							%>
							<input id="hdn_page" type="hidden" value="-1" name="hdn_page" /> 
							<input id="prop6" type="hidden" value="<%=Config.getServerContext()%>" name="prop6" /> 
							<input id="ProductType" type="hidden" value="<%=tERiskSubType%>" name="ProductType" /> 
							<input id="ChiProductType" type="hidden" value="<%=tERiskSubType%>" name="ChiProductType" />
							<input id="age" type="hidden" value="<%=age%>" name="age" /> 
							<input id="period" type="hidden" value="<%=period%>" name="period" /> 
							<input id="travelAddress" type="hidden" value="<%=travelAddress%>" name="travelAddress" /> 
							<input id="features" type="hidden" value="<%=features%>" name="features" /> 
							<input id="searchPrem" type="hidden" value="<%=prem%>" name="searchPrem" /> 
							<input id="FrontServer" type="hidden" value="<%=Config.getFrontServerContextPath()%>" name="FrontServer" /> 
							
						</div>
						
						<div class="g-zd-sx">
							<div class="zd_sx_list" id="zd_click" title="展开筛选条件"></div>
						</div>
						
						<div id="divSearchOrder">
							<span id="order_default" class="one" onclick="doOrder1(this);">综合排序</span><span
								id="order_Popular" class="two" onclick="doOrder1(this);">人气</span><span 
								id="order_SalesVolume" class="two" onclick="doOrder1(this);">销量</span><span
								id="order_InitPrem" class="two" onclick="doOrder1(this);">价格</span>
							<em class="order-sale" id="order_sale" onclick="doOrder1(this);">促销商品</em>
							<p class="order-num">
								共<i id="searchProductCount"><%=total%></i>款保险满足条件
							</p>
						</div>
				<%
					if(noProduct){
				%>	
						<div class="s_noshop cf"  id="s_noshop" style="display:block;">
							<div class="noshop_img"><img src="<%=Config.getValue("StaticResourcePath") %>/images/redesign/noshop_47.gif" width="72" height="93" /></div>
				     		<div class="noshop_des"> 抱歉，没有找到您需要的产品<br>建议换筛选条件试试<br>或者看看相似产品</div>
				     		<div class="clear"></div>
				     		<p class="shop_titles">相似产品推荐：</p>
						</div>
				<%
					}else{
				%>
						<div id="s_noshop" class="s_noshop cf"></div>
				<%} %>
					
						<div id="products" class="shop_img_list cf">
				<%
					int k = 0;
					int Start = pageIndex*pageSize;
					for (int i = Start; i < Start+pageSize && i<dt.getRowCount(); i++) {						
						DataRow dr = dt.getDataRow(i);						
				%>
							<div class="nlist_con cf">
								<div class="nlist_des">
									<div class="icon_C<%=dr.getString("SupplierCode2")%> m-list-logo"></div>
									<div class="m-list-cn">
										<a href="<%=dr.getString("URL")%>" target="_blank" class="nlist_title"><%=dr.getString("ProductName")%></a>
										<div class="shop_tj_shrq cf">
											<p class="shop_shrd_con">
												<span class="shop_shrq_bg">适合人群</span>
												<%=dr.getString("AdaptPeopleInfoListV3")%>
											</p>
										</div>
									</div>
									<div class="clear"></div>
									<ul class="recommend_list"><%=dr.getString("DutyHTMLV3")%></ul>
									<div class="price_tj">
										<span class="recom_xl" id="SalesV_<%=dr.getString("ProductId")%>">(累计销量：加载中...)</span>
										<span class="recom_xl"><i><a
												href="javascript:void(0)"
												onclick="openCommnet('<%=dr.getString("URL")%>')"
												class="shop_tj_num" id="CommentV_<%=dr.getString("ProductId")%>">评论加载中...</a></i></span>
									</div>
								</div>
								<div class="nlist_price">
									<p class="list_Sale tribe-action" id="Activity_<%=dr.getString("ProductId")%>">
										<span id="Diy_Title_Activity_<%=dr.getString("ProductId")%>" style="display: none;"></span><em
											id="Diy_Activity_<%=dr.getString("ProductId")%>" style="display: none;"></em>
									</p>
									<span class="nlist_pay moneys" name="Ajax_Prict_<%=dr.getString("ProductId")%>"><em>￥</em><%=dr.getString("InitPrem")%></span>
									<em class="nlist_pay_t moneys"> 
										<span name="Clear_Ajax_Prict_<%=dr.getString("ProductId")%>">
										</span>
									</em>
									<div class="remcon_desmore">
										<a target="_blank" href="<%=dr.getString("URL")%>" rel="nofollow">去看看</a>
										<label class="nlist_add_db" onclick="showcp('<%=dr.getString("ProductName")%>','<%=dr.getString("LogoLink")%>','<%=dr.getString("ProductId")%>','<%=dr.getString("prop6")%>','<%=dr.getString("ProductType")%>','<%=dr.getString("LogoLink")%>','<%=dr.getString("InitPrem")%>');">加入对比</label>
									</div>
								</div>
							</div>
				<%
					}
				%>
						</div>
						<div class="plpage">
							<!--    翻页    -->
							<div class="page_area" id="productsPageBar">
								<div id='pagination'>
								<%	
									StringBuffer sb2 = new StringBuffer();
									int nextPage = pageIndex + 2;
									int onePageCount = 10;
									int pageCount = new Double(Math.ceil(total * 1.0 / onePageCount)).intValue();
									sb2.append("<ul>");
									if (pageIndex > 0) {
										sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + pageIndex + "\");'><span>上一页</span></a></li>");
										sb2.append("<li><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
									} else {
										sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + 1 + "\");'><span class='default'>上一页</span></a></li>");
										sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
									}
									int j = 2;
									for (j = 2;j< (total % onePageCount == 0 ? total / onePageCount : (total / onePageCount + 1)); j++) {
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
													sb2.append("<a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
													
												}
											} else if(pageIndex<3){
													if(j<5){
														if (j==(pageIndex+1)) {
															sb2.append("<li class='now'>");
														} else {
															sb2.append("<li>");
														}
														sb2.append("<a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
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
														sb2.append("<a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
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
											sb2.append("<a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
										}
									}
		
									if (pageIndex + 1 == pageCount) {
										if (pageCount > 1) {
											sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + pageCount + "\");'><span>"+pageCount+"</span></a></li>");
										}
										sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + pageCount + "\");'><span class='default'>下一页</span></a></li>");
									} else {
										sb2.append("<li><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + pageCount + "\");'><span>"+pageCount+"</span></a></li>");
										sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + nextPage + "\");'><span>下一页</span></a></li>");
									}
									
									sb2.append("</ul>");
									out1.print(sb2.toString());
								%>
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<div class="col-sub">
					<div class="l_boxss">
						<div class="c1">
							<em class="l_icon"></em><%=catalogName%>热销产品
						</div>
						<ul id="products" class="usershop_box">
						<%
							// 获取热销产品
							DataTable dt1 = new DataTable(); 
							dt1 = SearchAPI.getHotProduct(hotERiskSubType);
							for(int i=0;i<dt1.getRowCount();i++){
						%>
								<li class="usershop_list">
									<p>
										<a href="<%=dt1.get(i).getString("URL")%>" target="_blank">
										    <span class="us_title"><%=dt1.get(i).getString("Title")%></span>
											<span class="us_text"><%=dt1.get(i).getString("AdaptPeopleInfoListV3")%></span>
										</a>
									</p>
									<p>
										<span class="shop_m" name="Ajax_Prict_<%=dt1.get(i).getString("ProductID") %>"><em>￥</em><%=dt1.get(i).getString("InitPrem")%></span><span
											class="sale_num" id="SalesI_<%=dt1.get(i).getString("ProductID") %>"></span>
									</p>
								</li>
						<%
							}
						%>
						</ul>
					</div>
					<%@include file="../wwwroot/kxb/block/kxb_product_list.shtml" %>
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<%@include file="../wwwroot/kxb/block/kxb_footer_new_common.shtml" %>
	<!-- 底部结束 -->
	<%@include file="../wwwroot/kxb/block/kxb_product_compare_v1.shtml" %>

	<!-- js加载 -->
	<%@include file="../wwwroot/kxb/block/community_v1.shtml" %>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/redesign/jquery-hcheckbox.js"></script>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/premcalculate.js"></script>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/redesign/re_productCompare.js"></script>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/jquery.soChange-min.js"></script>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/redesign/re_productlistv3.js"></script>
		
</body>
</html>
