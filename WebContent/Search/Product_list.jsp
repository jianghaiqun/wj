<!DOCTYPE html >
<%@ page session="false" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	import="com.sinosoft.framework.*"
	import="java.util.*"
	import="com.sinosoft.framework.utility.*"
	import="com.sinosoft.framework.data.*"
	import="com.sinosoft.schema.FEMSearchConditionInfoSchema"
	import="com.sinosoft.schema.FEMSearchConditionInfoSet"
	import="com.sinosoft.cms.api.SearchAPI"
%>
<%@ taglib uri="controls" prefix="z"%>
<%	
	
	String strpage = request.getParameter("page");
	strpage = (StringUtil.isEmpty(strpage))?"1":strpage;
	if (!strpage.matches("\\d+")||("0".equals(strpage)))
	{
		StringUtil.invalidInputCope(out, "页码不合法！");
		return;
		//strpage = "1";
	}
	
	String ID = request.getParameter("ID");
	String tERiskSubType = SearchAPI.getERiskSubType(ID);
	String nERiskSubType="";
	if(StringUtil.isNotEmpty(tERiskSubType)){
		nERiskSubType = tERiskSubType.substring(0, 1);
	}
	String SearchID = SearchAPI.getSearchID(tERiskSubType,ID);
	String SearchName = SearchAPI.getSearchName(SearchID);
	String SearchCode = SearchAPI.getSearchCode(SearchID);
	String[] adds = SearchCode.split("\\|");
	String ERiskSubTypeName = SearchAPI.getERiskSubTypeName(tERiskSubType);
		
	String SearchAddress = SearchAPI.getSearchAddress(SearchID);
	DataTable dt = new DataTable(); 
	dt = SearchAPI.getSearchProduct(SearchID,nERiskSubType);

	// 获取保险知识文章
	DataTable dt2 = new DataTable(); 
	dt2 = SearchAPI.getInsKnowledge();
	// 获取保险问答文章
	DataTable dt3 = new DataTable(); 
	dt3 = SearchAPI.getInsQuestion();
	// 获取推荐产品
	DataTable dt4 = new DataTable(); 
	dt4 = SearchAPI.getRecommendProduct(tERiskSubType);
	
	String size = request.getParameter("size");
	size  = StringUtil.isEmpty(size)?"10":size;
	
	int pageSize = Integer.parseInt(size);
	int pageIndex = Integer.parseInt(strpage) - 1;
	
	String CatalogUrl = "/Search/Product_list.jsp?ID="+ID;
	
	String summarize = SearchAPI.getSummarize(tERiskSubType);
	
	String summarizeImg = SearchAPI.getSummarizeImg(tERiskSubType);
	
	String hotERiskSubType = tERiskSubType;
%>
<html >
<head>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/mobile.js"></script>
<script type="text/javascript">
var pageID = '<%= ID%>';
var WapServerContext = '<%=Config.getValue("WapServerContext") %>';
if (pageID != null && pageID != '') {
  uaredirect(WapServerContext + "/mobile/blankpage.ashx?PageType=B&PageID=" + pageID , "list");
	
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=SearchName%>_<%=ERiskSubTypeName %>-开心保保险网</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<%@include file="../wwwroot/kxb/block/kxb_header_new_css.shtml" %>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_listv2.css?v=1225" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<%@include file="../wwwroot/kxb/block/kxb_custom_header.shtml" %>
</head>
<body>
	<%@include file="../wwwroot/kxb/block/kxb_header_index_new_v2.shtml" %>
	<div class="wrapper">
	<!-- 旅游地点 -->
	<div id='suggest' class="ac_results"> </div> 
	<div class="daohang">
		<span class="daohang_home"></span>
		<a target="_self" href="<%=Config.getFrontServerContextPath()%>/">您现在的位置：首页</a><span class="separator">&gt;</span><p><%=SearchAPI.getCatalogName(tERiskSubType)%></p>
		</div>
		<div class="content">
		
			<div class="grid-c2-s6f">
			<div class="col-main">
				<div class="main-wrap">
				<div class="top_b">
				<div id="load_con" style="display:none;"></div>
					<div class="top_aa">
						<h1 class="shaixuan" ><%=SearchAPI.getCatalogName(tERiskSubType)%></h1>-筛选<a href="">重置条件</a>
					</div>
					<%
					
					String BNCompany = Config.getValue("BNCompany");
					
					StringBuffer tHTML = new StringBuffer();
					FEMSearchConditionInfoSchema SCschema = new FEMSearchConditionInfoSchema(); 
					FEMSearchConditionInfoSet SCset = new FEMSearchConditionInfoSet();
					if(StringUtil.isNotEmpty(tERiskSubType)){
					SCset = SCschema.query(new QueryBuilder("where SearchLevel='1' and ERiskType=? order by extra3 DESC,-SearchOrder" , nERiskSubType));

					tHTML.append("<div id=\"radiolist\" class=\"radiolist CsearchConditions\">") ;
					for (int i = 0; i < SCset.size(); i++) 
					{	
						//拼装不限和全部
						SCschema = SCset.get(i);
						FEMSearchConditionInfoSchema SCschema1 = new FEMSearchConditionInfoSchema();
						FEMSearchConditionInfoSet SCset1 = new FEMSearchConditionInfoSet();
						FEMSearchConditionInfoSchema SCschema2 = new FEMSearchConditionInfoSchema();
						FEMSearchConditionInfoSet SCset2 = new FEMSearchConditionInfoSet();
						FEMSearchConditionInfoSet SCset3 = new FEMSearchConditionInfoSet();
						int[] subNode = new int[10];
						int k = 0;
						//查询出一级查询条件对应的子条件
						if(StringUtil.isEmpty(BNCompany)){
							SCset1 = SCschema1.query(new QueryBuilder("where UpperId=? order by -SearchOrder",SCschema.getId()));
							
						} else {
							SCset1 = SCschema1.query(new QueryBuilder("where UpperId=? and SearchCode <> ? order by -SearchOrder", SCschema.getId(), BNCompany));
						}
						
						
						if("Y".equals(SCschema.getIsMultipleChoice())){
							//如果本条件为多选在外成多套一个“chklist”
							tHTML.append("<div class=\"chklist\" >");
						}
						if("Y".equals(SCschema.getExtra3())){
							tHTML.append("<div class=\"bznx-z\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"\">");
						}else{
							tHTML.append( "<div class=\"bznx-no\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"\">");
						}
						tHTML.append("<span class=\"CConditionName\">" + SCschema.getSearchName() + "：</span>");
						tHTML.append("<div class=\"select_nav\"><ul>");
						//IF判断是多选而且是并集时 02—Y为全部
						if ("02".equals(SCschema.getIntersection()) && "Y".equals(SCschema.getIsMultipleChoice())) 
						{ 
							tHTML.append(" <li class=\"jiange\"><input type=\"radio\" id=\""+ SCschema.getSearchCode() +"_all\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\""+ SCschema.getSearchCode()); 
							tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>");
						} 
						//02以外-Y为不限
						else if ("Y".equals(SCschema.getIsMultipleChoice())&&!"02".equals(SCschema.getIntersection()))
						{
							tHTML.append("<li  class=\"jiange\"><input type=\"radio\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\"" + SCschema.getSearchCode()); 
							tHTML.append( "\" id=\""+ SCschema.getSearchCode() +"_all\"  style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>");
						}
						else
						{
							tHTML.append("<li  class=\"jiange\"><input type=\"radio\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\"" + SCschema.getSearchCode());
							
							if("".equals(SCschema.getSubColumnCategory()) || SCschema.getSubColumnCategory()==null){
								tHTML.append( "\" style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>");
							}else{
								tHTML.append( "\" style=\"display: none;\"><label onclick=\"queryRecommendProduct('"+SCschema.getSubColumnCategory()+"','999')\" class=\"hRadio\" >不限</label></li>");
							}
							
							
						}
						//拼装除不限和全部以外的子条件
						for (int j = 0; j < SCset1.size(); j++) 
						{	
							int m = j+1;
							SCschema1 = SCset1.get(j);
							if ("Y".equals(SCschema.getIsMultipleChoice())) 
							//IF判断是多选条件，多选条件单独样式
							{
								tHTML.append("<li><input type=\"checkbox\" name=\""+ SCschema.getSearchCode() +"\" value=\""+ SCschema1.getId() +"\" style=\"display: none;\"><label class=\"checkbox\" >" + SCschema1.getSearchName());
								tHTML.append("</label></li>");
							} 
							else 
							{	//境内、外旅游有子菜单，需要单独拼装样式
								if("02".equals(SCschema.getIntersection()) && "N".equals(SCschema.getIsMultipleChoice())){
									tHTML.append("<li><input type=\"radio\" id=\"first"+m+"\" value=\"" + SCschema1.getId() + "\" name=\"" + SCschema.getSearchCode());
									tHTML.append( "\" style=\"display: none;\"><label class=\"hRadio\" >" + SCschema1.getSearchName() + "</label></li>");
								}
								else 
								{	
									tHTML.append("<li><input type=\"radio\"  value=\"" + SCschema1.getId() + "\" name=\"" + SCschema.getSearchCode());
									tHTML.append("\" style=\"display: none;\">");
									if("".equals(SCschema1.getSubColumnCategory()) || SCschema1.getSubColumnCategory()==null){
										tHTML.append("<label class=\"hRadio\" >" + SCschema1.getSearchName() + "</label></li>");
									}else{

										if (StringUtil.isNotEmpty(SCschema1.getId())) {
											String addsValue;
											for(int kk=0;kk<adds.length;kk++){
												if(adds[kk].indexOf(":") >= 0){
													if (SCschema.getSearchCode().equals(adds[kk].split(":")[0])) {
														addsValue = adds[kk].split(":")[1];
														String[] vals = addsValue.split(",");
														for (int v = 0; v < vals.length; v++) {
															if (SCschema1.getId().equals(vals[v])) {
																hotERiskSubType = SCschema1.getSubColumnCategory();
															}
														}
													}
												}
											}
										}
										tHTML.append("<label onclick=\"queryRecommendProduct('"+SCschema1.getSubColumnCategory()+"','"+SCschema1.getSearchName()+"')\" class=\"hRadio\" >" + SCschema1.getSearchName() + "</label></li>");
									}
								}
								if(StringUtil.isNotEmpty(SCschema1.getSubNodeNum()) && Integer.parseInt(SCschema1.getSubNodeNum())>0){
									SCset3.add(SCschema1);
									SCset2.add(SCschema2.query(new QueryBuilder("where Upperid=? order by -SearchOrder",SCschema1.getId())));
									subNode[k] = SCset2.size();
									k++;
								}
								
							}
							if(j!=0 && (j+1)%4==0){
								tHTML.append(" <li class=\"jiange\"></li>");
							}
						}
						tHTML.append("</ul></div>");
						tHTML.append("</div>");
						if("Y".equals(SCschema.getIsMultipleChoice())){
							tHTML.append("</div>");
						}
						//拼装境内境外旅游的子菜单
						if (SCset2.size()>0) 
						{	
							int m = 0;
							tHTML.append("<div class=\"jn-k\" style=\"display: none;\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"_\">");
							tHTML.append("<div id=\"CsearchConditions_"+SCschema.getSearchCode()+"_jn-sj\" class=\"jn-sj\" style=\"margin: 0px 0px -1px 60px;\"><img alt=\"\" src=\""+Config.getValue("StaticResourcePath")+"/images/sj.gif\"></div><div class=\"jn-qbbg\" >");
							for (int n = 0 ; n < SCset3.size(); n++) 
							{	
								tHTML.append("<ul style=\"display: block;width: 430px;\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"_"+SCset3.get(n).getId()+"\">");
								tHTML.append("<li style=\"padding-right:20px\"><input type=\"radio\" id=\""+SCschema.getSearchCode()+"_sub_"+""+SCset3.get(n).getId()+"\" value=\""+SCset3.get(n).getId()+"\" name=\""+SCschema.getSearchCode()+"_sub\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>");
								for(; m < subNode[n]; m++){
									SCschema2 = SCset2.get(m);
									tHTML.append("<li style=\"padding-right:20px\"><input type=\"radio\" value=\"" + SCschema2.getId() + "\" name=\""+SCschema.getSearchCode()+"_sub\" style=\"display: none;\"><label class=\"hRadio\" >" + SCschema2.getSearchName() + "</label></li>");
								}
								tHTML.append("</ul>");
							}
							tHTML.append("</div></div>");
						}
						
						}
					for(int i=0;i<adds.length;i++){
						if(adds[i].indexOf(":")==-1){
							tHTML.append("<input id=\"hdn_"+adds[i]+"\" type=\"hidden\" value=\"default_"+adds[i]+"_1\" name=\"hdn_"+adds[i]+"\" />"); 
						}else{
							String[] nadds = adds[i].split(":");
								tHTML.append("<input id=\"hdn_"+nadds[0]+"\" type=\"hidden\" value=\""+nadds[1]+"\" name=\"hdn_"+nadds[0]+"\" />"); 
						}
					}
					
					tHTML.append("</div>");
					JspWriter out1 = pageContext.getOut();		
					out1.print(tHTML.toString());
					}	
					%>
				<input id="hdn_page" type="hidden"  value="-1" name="hdn_page" />
				<input id="prop6" type="hidden" value="<%=Config.getServerContext()%>" name="prop6" />
				<input id="ProductType" type="hidden" value="<%=tERiskSubType%>" name="ProductType" />
				<input id="ChiProductType" type="hidden" value="<%=tERiskSubType%>" name="ChiProductType" />
				<input id="FrontServer" type="hidden" value="<%=Config.getFrontServerContextPath()%>" name="FrontServer" />
				<input id="searchProductFlag" type="hidden" value="listv3" name="searchProductFlag" />
				</div>
				<div class="zd_sx_list" id="zd_click" title="展开筛选条件"></div>
				<div class="recommend_shop cf">
					<input type="hidden" id="catalogName" value="<%=SearchAPI.getCatalogName(tERiskSubType)%>"/>
                	<div class="recommend_titile recommend_titile_up"><div class="reconmend_img_up"></div><b><span id="recommendTitle"><%=SearchAPI.getCatalogName(tERiskSubType)%></span>推荐产品</b><div class="recommend_kong">收起&nbsp;>></div></div>
                    <div id="shop_bosf">
		              <%
		              	int count=0;
		                if(dt4.getRowCount()==0){
		                	count=0;
		                }else
		                {if(dt4.getRowCount()==1){
		                	count=1;
		                }else{
		                 count = (dt4.getRowCount()+dt4.getRowCount())/dt4.getRowCount();
		                }
		                }
					  	for(int i = 0; i < count; i++){
					  %>
					  	<% 
					  		if(count==1){
					  			%>
					  			<div class="recommend_con floatl bor_leftsf">
					  			<%
					  		}else{
					  	%>
					  
					  	<% 
					  		if(i == (count-1)){
					  			%>
					  			<div class="recommend_con floatl">
					  			<%
					  		}else{
					  			%>
					  			<div class="recommend_con floatl bor_leftsf">
					  			<%
					  		}
					  	}
					  	 %>
					  	<div class="hot_tj_img">
					  	<% if (dt4.get(i).getString("RecommendImg") != null && !"".equals(dt4.get(i).getString("RecommendImg"))
					  			&& dt4.get(i).getString("RecommendImg").indexOf("nopicture.jpg")==-1 && !dt4.get(i).getString("RecommendImg").endsWith("/")) {%>
					  		<img src="<%=Config.getValue("StaticResourcePath")+dt4.get(i).getString("RecommendImg")%>" width="74px" height="74px" alt="" />
					  	<%}%>
					  	</div>
	                    <div class="recommend_box ">
	                       <div class="recommend_top cf">
	                      <span class="list_zd_img floatl"  >
	                         <a target="_blank" class="cp_tj_logo_a" href="<%=dt4.get(i).getString("URL")%>?link_id=recom" onclick="javascript:void(0);return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/recommend" vlpageid="recommend">
                                 <div class="icon_C<%=dt4.get(i).getString("SupplierCode2")%> tj_cp_logos"></div>
                              </a>
		                     </span>
	                     <div class="floatl shop_sfs "> 
	                     <span class="shop_titile"><a href="<%=dt4.get(i).getString("URL")%>?link_id=recom"  target="_blank" onclick="javascript:void(0);return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/recommend" vlpageid="recommend"><%=dt4.get(i).getString("ProductName")%></a></span>
	                      <p class="shop_tj_shrq">
                                 <span class="shop_shrq_bg">推荐理由</span>
                                 <%=dt4.get(i).getString("AdaptPeopleInfoV3")%>
                             </p>
	                     </div>
	                       </div>
	                       <div class="recommend_cen cf">
	                           <div class="tj_cp_dess">
                                   <p><%=dt4.get(i).getString("RecommendInfo")%></p>
                               </div>
	                       <div class="recom_price cf">
	                       
	                           <div class="price_tj">
		                        <span id="SalesU_<%=dt4.get(i).getString("ProductID")%>" class="recom_xl">(累计销量：加载中...)</span>
                                <span id="CommentU_<%=dt4.get(i).getString("ProductID")%>" class="recom_xl"> <i>(
                                        <a href="javascript:void(0)" class="shop_tj_num" onclick="openCommnet('<%=dt4.get(i).getString("URL")%>','link_id=recom');return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/recommend" vlpageid="recommend"><%=SearchAPI.getCommentCount(dt4.get(i).getString("ProductID")) %></a>
                                        )</i>条评论
                                </span>
                                </div>
	                            <ul class="crcom_pric">
								<li class="new_priceA"><span name="Ajax_Prict_<%=dt4.get(i).getString("ProductID")%>"><%=dt4.get(i).getString("InitPrem")%></span></li>
								<!-- 
								<li class="new_priceB"><span name="Clear_Ajax_Prict_<%=dt4.get(i).getString("ProductID")%>"><%=dt4.get(i).getString("BasePremV3")%></span></li>
								 -->
							   </ul>
		                       <span class="remcon_desmore">
		                       <a href="<%=dt4.get(i).getString("URL")%>?link_id=recom" target="_blank" onclick="javascript:void(0);return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/recommend" vlpageid="recommend">去看看>></a></span>
		                       </div>
	                       </div>
	                    </div>
	                    </div>
	                    
	                    
	           			<%
							}
						%>
	                 
	                 
                    </div>
                    <div class="clear"></div>
                </div>
				
				
				<div id="divSearchOrder">
					<span id="order_default" class="one" onclick="doOrder1(this);">默认排序</span><span id="order_Popular" class="two" onclick="doOrder1(this);">人气</span><span id="order_SalesVolume" class="two" onclick="doOrder1(this);">销量</span><span id="order_InitPrem"
						class="two" onclick="doOrder1(this);">价格</span>
				</div>
				<%
					if(dt==null || dt.getRowCount()<=0){
						dt = SearchAPI.getDefaltProduct();
						%>	
						<div class="s_noshop cf"  id="s_noshop" style="display:block;">
					    <div class="noshop_img"><img src="<%=Config.getValue("StaticResourcePath") %>/images/searchfs_03.jpg" width="128" height="90" /></div>
					    <div class="noshop_des"> <b>很抱歉，您需要的产品没有找到。</b>建议您更换筛选条件试一试</div>
					    <div class="clear"></div>
					    <p class="shop_titles">我们为您精心挑选了以下产品：</p>
						<%
					}else{
					%>
					<div class="s_noshop cf"  id="s_noshop">
					<%} %>
					</div>
				<div id="products" class="shop_img_list cf">
				<%
					int k = 0;
					int Start = pageIndex*pageSize;
					for (int i = Start; i < Start+pageSize && i<dt.getRowCount(); i++) {						
						DataRow dr = dt.getDataRow(i);						
					%>
					
					
											
					 <div class="nlist_con cf">
                   	    <div class="shop_nlist_img">
	                       <p id="Activity_<%=dr.getString("ProductId")%>">
	                        <%=dr.getString("ProductActive")%>
	                       </p>
                        	<a rel="nofollow" target="_blank" href="<%=dr.getString("URL")%>"><img width="190" height="190" alt="<%=dr.getString("ProductName")%>" src="<%=dr.getString("LogoLink")%>" class="lazy" data-original="<%=dr.getString("LogoLink")%>" style="display: inline;"></a>
                        </div>
                        <div class="nlist_des">
                        	<a href="<%=dr.getString("URL")%>" target="_blank" class="nlist_title"><%=dr.getString("ProductName")%></a>
                        	<div class="shop_tj_shrq cf">
                                       <span class="shop_shrq_bg">适合人群</span>
                                       <p class="shop_shrd_con">
                                          <%=dr.getString("AdaptPeopleInfoListV3")%>
                                       </p>
                               </div>
                               <div class="clear"></div>
                              <ul class="recommend_list">
	                            <%=dr.getString("DutyHTMLV3")%>
	                       </ul>
                               <div class="nlist_price">
                                       <span class="nlist_pay moneys" name="Ajax_Prict_<%=dr.getString("ProductId")%>">￥<%=dr.getString("InitPrem")%></span> 
                                       <em class="nlist_pay_t moneys"><span name="Clear_Ajax_Prict_<%=dr.getString("ProductId")%>"><%=dr.getString("BasePremV3")%></span></em>
                                        
                                       <div class="price_tj">
                                           <span class="recom_xl" id="SalesV_<%=dr.getString("ProductId")%>">(累计销量：加载中...)</span>
                                           <span class="recom_xl" id="CommentV_<%=dr.getString("ProductId")%>">
                                           <i>
                                               	（<a class="shop_tj_num" href="javascript:void(0)" onclick="openCommnet('<%=dr.getString("URL")%>')"><%=SearchAPI.getCommentCount(dr.getString("ProductID")) %></a>）
                                           </i> 条评论
                                           </span>
                                       </div>

                                       <span class="remcon_desmore">
                                           <label class="nlist_add_db">
                                               <input type="checkbox" onclick="showcp('<%=dr.getString("ProductName")%>','<%=dr.getString("LogoLink")%>','<%=dr.getString("ProductId")%>','<%=dr.getString("prop6")%>','<%=dr.getString("ProductType")%>','<%=dr.getString("LogoLink")%>','<%=dr.getString("InitPrem")%>');">加入对比</label>
                                           <a target="_blank" href="<%=dr.getString("URL")%>">去看看</a>
                                       </span>
                                   </div>
                     </div>
                   </div>
						
                  <%
						}
					%>
					</div>
				<div class="plpage">
					<!--    翻页    -->
					
				<div id="productsPageBar"><%
				int nextPage = pageIndex + 2;
				int total = dt.getRowCount();
				int pageCount = new Double(Math.ceil(total * 1.0 / 10)).intValue();
				StringBuffer sb2 = new StringBuffer();
				sb2.append("<div class='plpage'>");
				sb2.append("<div class='plpagecont'>");
				if (pageIndex > 0) {
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'>首页</a></span>");
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageIndex + "\");'><</a></span>");
				} else {
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'>首页</a></span>");
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><</a></span>");
				}
				int j = 1;  
				for( j = 1;j<=(total%10==0?total/10:(total/10+1));j++){    
					if(j==(pageIndex+1)){
						sb2.append("<span class='plpage04'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><font color = '#FFFFFF'> "+j+"</font></a></span>&nbsp;");
					}
					//如果总页面大于5
					else if(pageCount>5){
						if(pageIndex>3 && pageCount>(pageIndex+1)){
							if(j>(pageIndex-3)&&j<(pageIndex+3)){
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'> "+j+" </a></span>&nbsp;");
							}
							if(j==(pageIndex+2)&&j!=pageCount){
								sb2.append("...&nbsp;");
							}else if(j==(pageCount-1)&&(pageIndex+1)==(pageCount-2)){
								sb2.append("...&nbsp;");
							}
						}
						if(pageIndex>3&&pageCount<(pageIndex+2)){
							if(j>pageCount-5){					
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
							}
						}
						if(pageIndex<4){
							if(j<6){				
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
								if(j==5){						
									sb2.append("...&nbsp;");
								}
							}
						}
						
					}
					//如果总页面小于5 则全部显示
					else if(pageCount<6){
						sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
					}
				}
				if (pageIndex + 1 != pageCount && pageCount > 0) {
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + nextPage + "\");'>></a></span>");
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>尾页</a></span>");
				} else {
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>></a></span>");
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>尾页</a></span>");
				}

				sb2.append("&nbsp;&nbsp;共" + (total%10==0?total/10:(total/10+1)) + "页&nbsp;&nbsp;");
				sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
				sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

				sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>"
								+ pageCount
								+ "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){newJump();}else{newJump();}}\" style='' value='跳转'></td>");
				
				sb2.append("</div>");
				sb2.append("</div>");
				JspWriter out2 = pageContext.getOut();		
				out2.print(sb2.toString());
				%>
				</div>
				<div class="clear"></div>
				</div>
			
			</div>
			</div>
		  <div class="col-sub">

				
				  <div class="l_boxss">
        			  <div class="c1"><%=SearchAPI.getCatalogName(tERiskSubType)%>概述</div>
         			  <img src="<%=Config.getValue("StaticResourcePath")%>/<%=summarizeImg%>" width="228" height="60"/>
         			 
         	          <div class="l_box_p"><p><%=summarize%></p></div>
         		  </div>
         		  
         		  
				
				<div class="l_boxss">
					<div class="c1" id="c1"><%=SearchAPI.getCatalogName(hotERiskSubType)%>热销产品</div>
					<ul class="usershop_box" id="usershop_box">
						<%
							// 获取热销产品
							DataTable dt1 = new DataTable(); 
							dt1 = SearchAPI.getHotProduct(hotERiskSubType);
							for(int i=0;i<dt1.getRowCount();i++){
						%>
	           			       <li class="usershop_list">
								  <h4><a href="<%=dt1.get(i).getString("URL")%>" target="_blank"><img width="70" height="70" alt="<%=dt1.get(i).getString("Title")%>" src="<%=dt1.get(i).getString("LogoLink")%>"></a></h4>
								 	 <div class="user_shop">
								 	 	<a href="<%=dt1.get(i).getString("URL")%>" target="_blank"><%=dt1.get(i).getString("Title")%></a> <span class="red shop_m moneys" name="Ajax_Prict_<%=dt1.get(i).getString("ProductID") %>">￥<%=dt1.get(i).getString("InitPrem")%><span name="Clear_Ajax_Prict_<%=dt1.get(i).getString("ProductID") %>" class="moneys price_ymoneys"><%=dt1.get(i).getString("BasePremV3")%></span></span>
					 			 	 </div>
					 		    </li>
						
						<%
							}
						%>
					</ul>
				</div>
				
				 <div class="l_boxss kf_box">
         		  <a href="javascript:void(0);" vlpageid="xiaoneng" exturl="http://www.kaixinbao.com/xiaoneng" id="qqwap2" onclick="return(VL_FileDL(this));return false;" rel="nofollow"><img width="228" height="48" title="投保遇到问题？马上咨询在线客服" alt="投保遇到问题？马上咨询在线客服" src="<%=Config.getValue("StaticResourcePath")%>/images/group_11.jpg"></a>
           		</div>
				
				<div class="l_boxss">
            	 <div class="c1">保险知识</div>
		          <ul class="zx_list">
		          			<%
							for(int i=0;i<dt2.getRowCount();i++){
							%>
								<li><a target="_blank" href="<%=Config.getFrontServerContextPath()%><%=dt2.get(i).getString("URL")%>"><%=dt2.get(i).getString("Title")%></a></li>
							<%
								}
							%>	
		          </ul>
          	 </div>
				
				
				<div class="l_boxss">
             <div class="c1">保险问答</div>
             	<ul class="zx_list">
					<%
					for(int i=0;i<dt3.getRowCount();i++){
					%>
							<li><a target="_blank" href="<%=Config.getFrontServerContextPath()%><%=dt3.get(i).getString("URL")%>"><%=dt3.get(i).getString("Title")%></a></li>
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
	</div>
	
	<%@include file="../wwwroot/kxb/block/kxb_product_part6.shtml" %>
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
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-hcheckbox.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.qtip.js"></script>
	<script	type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/iframeTools.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/premcalculate.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/productCompare.js"></script>
	<script language="JavaScript" src="<%=Config.getValue("JsResourcePath")%>/js/tabchange.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.soChange-min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/productlistv3.js"></script>
	<script type="text/javascript">    

</script>
	<script type="text/javascript">
(function(){
	 //焦点图切换
	jQuery('#change_33 div.changeDiv').soChange({
		thumbObj:'#change_33 .ul_change_a2 span',
		thumbNowClass:'on',
		changeTime:4000//自定义切换时间为4000ms
	});
})();
</script>
</body>
</html>
