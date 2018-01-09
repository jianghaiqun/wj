<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	String type = request.getParameter("type");
	String strpage = request.getParameter("page");
	strpage = (StringUtil.isEmpty(strpage))?"1":strpage;
	if (!strpage.matches("\\d+")||("0".equals(strpage)))
	{
		StringUtil.invalidInputCope(out, "页码不合法！");
		return;
		//strpage = "1";
	}
	String detailUrl = "";
	DataTable toDetalUrl = new QueryBuilder("select prop3 from ZCSite where id='221'").executeDataTable();
	if(toDetalUrl.getRowCount()>0){
		detailUrl = toDetalUrl.getString(0,0);
	}
	DataTable dtType = new QueryBuilder("select prop1,prop2,prop3 from zdcode where codetype = 'BXSCSearchPage' and parentcode='BXSCSearchPage' and codevalue=? ",type).executeDataTable();
	String ID = request.getParameter("ID");
	String SearchID = "";
	String SearchCode = "";
	String tERiskSubType = "";
	if(dtType.getRowCount()>0){
		SearchID = dtType.getString(0, 0);
		SearchCode = dtType.getString(0, 1);
		tERiskSubType = dtType.getString(0, 2);
	}
	String nERiskSubType="";
	if(StringUtil.isNotEmpty(tERiskSubType)){
		nERiskSubType = tERiskSubType.substring(0, 1);
	}
	String[] adds = SearchCode.split("\\|");
	String ERiskSubTypeName = SearchAPI.getERiskSubTypeName(tERiskSubType);
		
	DataTable dt = new DataTable(); 
	dt = SearchAPI.getSearchProduct(SearchID,nERiskSubType);
	
	String size = request.getParameter("size");
	size  = StringUtil.isEmpty(size)?"10":size;
	
	int pageSize = Integer.parseInt(size);
	int pageIndex = Integer.parseInt(strpage) - 1;
	String summarize = SearchAPI.getSummarize(tERiskSubType);
	String summarizeImg = SearchAPI.getSummarizeImg(tERiskSubType);
%>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=ERiskSubTypeName %>-开心保保险网</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<%@include file="../wwwroot/kxb/block/kxb_header_new_css.shtml" %>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_list.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<style type="text/css">
	.wrapper {
		width:100%;
		margin: 0 auto;
	}
	.content_right{
		height:auto;
		float:left;
		margin-left:10px;
		_margin-left:0px;
	}
</style>
</head>
<body >
	<div class="wrapper">
			<div class="content_right">
				<div class="top_b">
				<div id="load_con" style="display:none;"></div>
					<div class="top_aa">
						<h1 class="shaixuan" ><%=SearchAPI.getCatalogName(tERiskSubType)%></h1>-筛选
					</div>
					<%
					StringBuffer tHTML = new StringBuffer();
					FEMSearchConditionInfoSchema SCschema = new FEMSearchConditionInfoSchema(); 
					FEMSearchConditionInfoSet SCset = new FEMSearchConditionInfoSet();
					if(StringUtil.isNotEmpty(tERiskSubType)){
					SCset = SCschema.query(new QueryBuilder("where SearchLevel='1' and ERiskType=? order by -SearchOrder" , nERiskSubType));

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
						SCset1 = SCschema1.query(new QueryBuilder("where UpperId=? order by -SearchOrder",SCschema.getId()));
						if("Y".equals(SCschema.getIsMultipleChoice())){
							//如果本条件为多选在外成多套一个“chklist”
							tHTML.append("<div class=\"chklist\" >");
						}
						tHTML.append("<div class=\"bznx-z\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"\">");
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
				<input id="ProductType" type="hidden" value="<%=nERiskSubType%>" name="ProductType" />
				<input id="FrontServer" type="hidden" value="<%=Config.getFrontServerContextPath()%>" name="FrontServer" />
				</div>
				
				
				<div id="divSearchOrder">
					<span id="order_default" class="one" onclick="doOrder1(this);">默认排序</span><span id="order_Popular" class="two" onclick="doOrder1(this);">人气</span><span id="order_SalesVolume" class="two" onclick="doOrder1(this);">销量</span><span id="order_InitPrem"
						class="two" onclick="doOrder1(this);">价格</span>
				</div>
				<div class="s_noshop cf"  id="s_noshop" ></div>
				<div id="products">
					<%
					int k = 0;
					int Start = pageIndex*pageSize;
					for (int i = Start; i < Start+pageSize && i<dt.getRowCount(); i++) {						
						DataRow dr = dt.getDataRow(i);						
					%>      
						<div class="product_title">
							<span class="CInsuranceCompany icon_C<%=dr.getString("SupplierCode2")%>"></span><span class="productName"> <a target="_blank" onclick="chakan('<%=detailUrl%>productId=<%=dr.getString("ProductId")%>')"><h2 class="ziti"><%=dr.getString("ProductName")%></h2></a>
							</span> <span class="SalesVolume">(累计销量：<%=dr.getString("SalesVolume")%>)</span><span id="productIntegral_<%=dr.getString("ProductId")%>" style="display: none;"></span>
						</div>
                        <div class="product_condition">
							<%=dr.getString("CalHTML2")%>
						</div>
						
						<div class="product_info">
						<div class="product_info_bor">
							<div class="prodcutMark">
								<ul class="price"><%=dr.getString("prodcutMarkPrice")%></ul>
								<ul class="btn">
									<li class="btn1"><span onClick="chakan('<%=detailUrl%>productId=<%=dr.getString("ProductId")%>')">查看详情</span></li>
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
	<%@include file="../wwwroot/kxb/block/community.shtml" %>
	<div class="clear"></div>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-bxhcheckbox.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.qtip.js"></script>
	<script	type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/iframeTools.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/premcalculate.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/productCompare.js"></script>
	<script language="JavaScript" src="<%=Config.getValue("JsResourcePath")%>/js/tabchange.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.soChange-min.js"></script>
	<script type="text/javascript">
    var bxhzUrl = '<%=request.getParameter("channel") %>';  
	function chakan(str)
		{
			str = str + "&channel="+bxhzUrl;
			window.open(str);
		}
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F2d7e032a5f8d4e609feb9e22c0cb83f8' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>
