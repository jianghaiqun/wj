package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.BindInfoForLoginService;
import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.publish.AutomaticPublishArticle;
import com.sinosoft.cms.publish.PublishSearchCache;
import com.sinosoft.cms.webservice.CmsServiceImpl;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.product.SortList;
import com.sinosoft.schema.UserSearchOpinionSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.search.ArticleSearcher;
import com.sinosoft.search.SearchResult;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMSearchRelaList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class ProductSearchAjaxAction extends BaseShopAction {
	
	private static final long serialVersionUID = -7286089566677270798L;
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	/**
	 * 判断是否utf-8
	 */
	public static boolean isUTF8(byte[] rawtext) { 
		   int score = 0; 
		   int i, rawtextlen = 0; 
		   int goodbytes = 0, asciibytes = 0; 
		   // Maybe also use UTF8 Byte Order Mark: EF BB BF 
		   // Check to see if characters fit into acceptable ranges 
		   rawtextlen = rawtext.length; 
		   for (i = 0; i < rawtextlen; i++) { 
		    if ((rawtext[i] & (byte) 0x7F) == rawtext[i]) {  
		     // 最高位是0的ASCII字符 
		     asciibytes++; 
		     // Ignore ASCII, can throw off count 
		    } else if (-64 <= rawtext[i] && rawtext[i] <= -33 
		      //-0x40~-0x21 
		      && // Two bytes 
		      i + 1 < rawtextlen && -128 <= rawtext[i + 1] 
		      && rawtext[i + 1] <= -65) { 
		     goodbytes += 2; 
		     i++; 
		    } else if (-32 <= rawtext[i] 
		      && rawtext[i] <= -17 
		      && // Three bytes 
		      i + 2 < rawtextlen && -128 <= rawtext[i + 1] 
		      && rawtext[i + 1] <= -65 && -128 <= rawtext[i + 2] 
		      && rawtext[i + 2] <= -65) { 
		     goodbytes += 3; 
		     i += 2; 
		    } 
		   } 
		   if (asciibytes == rawtextlen) { 
		    return false; 
		   } 
		   score = 100 * goodbytes / (rawtextlen - asciibytes); 
		   // If not above 98, reduce to zero to prevent coincidental matches 
		   // Allows for some (few) bad formed sequences 
		   if (score > 98) { 
		    return true; 
		   } else if (score > 95 && goodbytes > 30) { 
		    return true; 
		   } else { 
		    return false; 
		   } 
		   }
		  
	
	/**
	 * 搜索结果排序调用方法
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public String SearchOrder() throws UnsupportedEncodingException{
	String productOrder = getParameter("ProductsOrder");//Request.getString("ProductsOrder");
	int pageindex = Integer.parseInt(getParameter("pageIndex"));
	if(pageindex!=0){
		pageindex = pageindex - 1;
	}
	int nextPage = pageindex + 2;
	int start = pageindex*10;
	List<DataRow> alist = new ArrayList<DataRow>();
	String query= getParameter("query");
	if (StringUtil.isNotEmpty(query)) {
		query = java.net.URLDecoder.decode(query, "utf-8");
	}
	logger.info("ProductSearchAjaxAction--SearchOrder的查询关键字:{}", query);
	Mapx mapns = new Mapx();
	mapns.put("query",  query);
	mapns.put("page", getParameter("pageIndex"));
	mapns.put("id",  getParameter("id"));
	mapns.put("site",  getParameter("site"));
	mapns.put("order", "rela");
	SearchResult r = ArticleSearcher.proSearch(mapns);
	DataTable dt1 = r.Data;//(DataTable)User.getValue("dt");
	int total = dt1.getRowCount();
	int pageCount = new Double(Math.ceil(total * 1.0 / 10)).intValue();
	DataTable dt = new DataTable();
	for(int i=0;i<dt1.getRowCount();i++){
		alist.add(dt1.get(i));
	}
	if(StringUtil.isNotEmpty(productOrder)){
		String[] str1 = productOrder.split(" ");
		SortList<DataRow> sortList = new SortList<DataRow>();
		sortList.Sort(alist,str1[0],str1[1]);   	
	}
	for(DataRow dt2:alist){
		dt.insertRow(dt2);	
	}
	StringBuffer sb = new StringBuffer();
	String sb1 = "";
	StringBuffer sb2 = new StringBuffer();
	String sb3 = "";
	Map<String, String> jsonMap = new HashMap<String, String>();
try{	
	for(int i=start;i<start+10&&i<dt.getRowCount();i++){
		
		sb.append("<div class=\"nlist_con cf\">");
		//活动
		sb.append("<div class=\"shop_nlist_img\">");
		//是否有满减活动
		FilterAction filteraction = new FilterAction();
		String productid=dt.get(i).getString("ProductID");
		Map<String,String> map=filteraction.searchProductListAvtivity(dt.get(i).getString("RiskCode"),"wj");
		String diyactivity="";
		if(map==null||map.size()==0){
			sb.append(""+dt.get(i).getString("ProductActive")+"");
		}else{
			for (String key : map.keySet()) {
				String value=map.get(key);
				String type=key.substring(key.indexOf("@")+1,key.length());
				if(!"8".equals(type)){//非自定义活动
					sb.append(value);
					break;
				}else{
					String title=value.substring(0,value.indexOf("@"));
					String description=value.substring(value.indexOf("@")+1,value.length());
					if(description.length()>20){
						description=description.substring(0, 20);
					}
					diyactivity="<em class=\"list_hddes\"  id=\"Diy_em_Activity_"+productid +"\" > <i class=\"list_hd_t\" id=\"Diy_Title_Activity_"+productid+"\">"+title+"</i><span id=\"Diy_Activity_"+productid+"\" >"+description+"</span></em>";
				}
			}
			
		}
		//title
		sb.append("<a rel=\"nofollow\" target=\"_blank\" href=\""+dt.get(i).getString("URL")+"\"><img width=\"190\" height=\"190\" alt=\""+dt.get(i).getString("Title")+"\" src=\""+dt.get(i).getString("LogoLink")+"\" class=\"lazy\" data-original=\""+dt.get(i).getString("LogoLink")+"\" style=\"display: inline;\"></a>");
		sb.append("</div>");
		sb.append("<div class=\"nlist_des\">");
		sb.append("<a href=\""+dt.get(i).getString("URL")+"\" target=\"_blank\" rel=\"nofollow\" class=\"shop_pos_a\"></a>");
		sb.append(diyactivity);
		//适合人群
		sb.append("<div class=\"shop_tj_shrq cf\"> <span class=\"shop_shrq_bg\">适合人群</span>");
		sb.append("<p class=\"shop_shrd_con\">"+dt.get(i).getString("AdaptPeopleInfoListV3")+"</p></div>");
        sb.append("<div class=\"clear\"></div>");
        //保险责任
        sb.append("<ul class=\"recommend_list\">");
        sb.append(""+dt.get(i).getString("DutyHTMLV3")+"</ul>");
        //价格
		sb.append("<div class=\"nlist_price\">");
		sb.append("<span class=\"nlist_pay moneys\">"+dt.get(i).getString("InitPrem")+"</span><em class='nlist_pay_t moneys'>"+dt.get(i).getString("BasePremV3")+"</em>");
		sb.append("<div class=\"price_tj\">");
		sb.append("<span class=\"recom_xl\">已有"+dt.get(i).getString("SalesVolume")+"人投保</span>");
		sb.append("<span class=\"recom_xl\"><i>（<a class=\"shop_tj_num\" href="+dt.get(i).getString("URL")+" target=\"_blank\">"+SearchAPI.getCommentCount(dt.get(i).getString("RiskCode"))+"</a>） </i> 条评论</span>");
		sb.append("</div>");
		
		
		sb.append("<span class=\"remcon_desmore\"><label class=\"nlist_add_db\">");
		sb.append("<input type=\"checkbox\" onclick=\"showcp('" + dt.get(i).getString("Title") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("RiskCode") + "','"+Config.getServerContext() + "','" + dt.get(i).getString("ProductType") + "','"+dt.get(i).getString("LogoLink")+"','"+dt.get(i).getString("InitPrem")+"');\">加入对比</label>");
		sb.append("<a target=\"_blank\" href="+dt.get(i).getString("URL")+">去看看</a></span>");
		
		sb.append("</div></div></div>");
	}
	sb1 = sb.toString();
	sb2.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
	sb2.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
	sb2.append("共" + total + "条记录，每页" + 10 + "条，当前第<span class='fc_ch1'>" + ((total == 0) ? 0: (pageindex + 1)) 
			+ "</span>/<span class='fc_ch1'>" + pageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
	if (pageindex > 0) {
			
		sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+1+"\");'><span class='fc_ch1'>第一页</span></a>|");
		if(1==pageindex){
			sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+pageindex+"\");'><span class='fc_ch1'>上一页</span></a>|");
		}else{
			sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+pageindex+"\");'><span class='fc_ch1'>上一页</span></a>|");
		}
		
	} else {
		sb2.append("<span class='fc_hui2'>第一页</span>|");
		sb2.append("<span class='fc_hui2'>上一页</span>|");
	}
	if (pageindex + 1 != pageCount && pageCount>0) {
		sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+nextPage+"\");'><span class='fc_ch1'>下一页</span></a>|");
		sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+pageCount+"\");'><span class='fc_ch1'>最末页</span></a>");
	} else {
		sb2.append("<span class='fc_hui2'>下一页</span>|");
		sb2.append("<span class='fc_hui2'>最末页</span>");
	}

	sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
	sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");
	
	sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>" + pageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){newJump();}else{newJump();}}\" style='' value='跳转'></td>");
	sb2.append("</tr></table>");
//	sb2.append("<input type='hidden' name='newPageIndex' value='"+pageindex+++"' id='newPageIndex' />");
	sb3 = sb2.toString();
	jsonMap.put("Status", "1");
	jsonMap.put("sb1",sb1);
	jsonMap.put("sb3",sb3);
	return ajaxJson(jsonMap);
}catch(Exception e){
	jsonMap.put("Status", "0");
	jsonMap.put("error","搜索排序方法出现异常："+e.getMessage());
	logger.error("搜索排序方法出现异常："+e.getMessage(), e);
	return ajaxJson(jsonMap);
	}
}
	
	
	/**
	 * 改版搜索结果排序调用方法
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	
	@SuppressWarnings("unchecked")
	public String searchOrderRe() throws UnsupportedEncodingException{
		String productOrder = getParameter("ProductsOrder");
		int pageindex = Integer.parseInt(getParameter("pageIndex"));
		String channelsn = getParameter("channelsn");
		if(pageindex!=0){
			pageindex = pageindex - 1;
		}
		int nextPage = pageindex + 2;
		int start = pageindex*10;
		List<DataRow> alist = new ArrayList<DataRow>();
		String query= getParameter("query");
		if (StringUtil.isNotEmpty(query)) {
			query = java.net.URLDecoder.decode(query, "utf-8");
		}
		logger.info("ProductSearchAjaxAction--searchOrderRe的查询关键字:{}", query);
		Mapx mapns = new Mapx();
		mapns.put("query",  query);
		mapns.put("page", getParameter("pageIndex"));
		mapns.put("id",  getParameter("id"));
		mapns.put("site",  getParameter("site"));
		mapns.put("order", "rela");
		SearchResult r = ArticleSearcher.proSearch(mapns);
		
		Member member = getLoginMember();
		String memberId = "";
		if (member != null) {
			memberId = member.getId();
		}
		DataTable dt1 = r.Data;//(DataTable)User.getValue("dt");
		int total = dt1.getRowCount();
		int pageCount = new Double(Math.ceil(total * 1.0 / 10)).intValue();
		DataTable dt = new DataTable();
		for(int i=0;i<dt1.getRowCount();i++){
			alist.add(dt1.get(i));
		}
		if(StringUtil.isNotEmpty(productOrder)){
			String[] str1 = productOrder.split(" ");
			SortList<DataRow> sortList = new SortList<DataRow>();
			sortList.Sort(alist,str1[0],str1[1]);   	
		}
		for(DataRow dt2:alist){
			dt.insertRow(dt2);	
		}
		StringBuffer sb = new StringBuffer();
		String sb1 = "";
		StringBuffer sb2 = new StringBuffer();
		String sb3 = "";
		Map<String, String> jsonMap = new HashMap<String, String>();
	try{	
		AjaxPriceAction ap = new AjaxPriceAction();
		for(int i=start;i<start+10&&i<dt.getRowCount();i++){
			
			//是否有满减活动
			FilterAction filteraction = new FilterAction();
			String productid=dt.get(i).getString("ProductID");
			String riskcode = dt.get(i).getString("ProductID");
			String newPrice = ap.queryAjaxPrice(riskcode,channelsn,memberId);
			String oPrice = dt.get(i).getString("Remark4");
			if (StringUtil.isEmpty(oPrice)) {
				oPrice = "0";
			}
			if (StringUtil.isEmpty(newPrice)) {
				newPrice = "0";
			}
			String discountRate = "";
			if (Double.parseDouble(newPrice) >= Double.parseDouble(oPrice)) {
				DataTable dt3 = new QueryBuilder(
						" select BackUp2 from femriskb where RiskCode=? and IsPublish='Y'  ", riskcode)
						.executeDataTable();
				if (dt3 != null && dt3.getRowCount() >= 1) {
					if (StringUtil.isNotEmpty(dt3.getString(0, "BackUp2"))) {
						discountRate = dt3.getString(0, "BackUp2");
					}
				}
				if (StringUtil.isNotEmpty(discountRate)) {
					oPrice = "原价"+dt.get(i).getString("BasePremV3");
				} else {
					oPrice = "";
				}
			} else {
				oPrice = "原价<em>￥</em>" + oPrice;
			}
			
			Map<String,String> map=filteraction.searchProductListAvtivityRe(dt.get(i).getString("RiskCode"),channelsn, discountRate);
			String diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\">";
			if(map==null||map.size()==0){
				diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\" style=\"display: none;\">";
				diyactivity += "</span><em id=\"Diy_Activity_"+productid+"\" style=\"display: none;\"></em></p>";
			}else{
				for (String key : map.keySet()) {
					// 为不同的活动类型添加不同的背景颜色
					String activity_type = key.substring(key.indexOf("@")+1);
					String active_class = "";
					if ("x".equals(activity_type)) {
						active_class = " active_07";
					} else if ("1".equals(activity_type)) {
						active_class = " active_06";
					} else if ("2".equals(activity_type)) {
						active_class = " active_05";
					} else if ("3".equals(activity_type)) {
						active_class = " active_04";
					} else if ("6".equals(activity_type)) {
						active_class = " active_02";
					} else if ("7".equals(activity_type)) {
						active_class = " active_08";
					} else if ("8".equals(activity_type)) {
						active_class = " active_03";
					} else if ("9".equals(activity_type)) {
						active_class = " active_09";
					}
					String value = map.get(key);
					String title = value.substring(0, value.indexOf("@"));
					String description = value.substring(value.indexOf("@") + 1, value.length());
					if (key.endsWith("@3") || key.endsWith("@6")) {
						String display="\" style=\"display: none;\">" ; 
						if(key.endsWith("@6") && "discount".equals(Config.getValue("discount")) ){
						     display="\">";
						}
						if(key.endsWith("@3")){
							 display="\">";
						}
						diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\" class=\""+active_class + display;
						diyactivity += (title+"</span><em id=\"Diy_Activity_"+productid+ display + description+"</em></p>");
					} else {
						diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\" class=\""+active_class+"\">";
						diyactivity += (title+"</span><em id=\"Diy_Activity_"+productid+"\">"+description+"</em></p>");
					}
				}
			}
			String url = dt.get(i).getString("URL");
			sb.append("<div class=\"nlist_con cf\"><div class=\"nlist_des\">");
			sb.append("<div class=\"icon_C"+dt.get(i).getString("SupplierCode2")+" m-list-logo\"></div>");
			
			sb.append("<div class=\"m-list-cn\"><a href=\""+url+"\" target=\"_blank\" rel=\"nofollow\" class=\"nlist_title\">");
			sb.append(dt.get(i).getString("Title") + "</a><div class=\"shop_tj_shrq cf\"><p class=\"shop_shrd_con\">");
			sb.append("<span class=\"shop_shrq_bg\">适合人群</span>" + dt.get(i).getString("AdaptPeopleInfo") + "</p></div></div>");
			sb.append("<div class=\"clear\"></div>" + dt.get(i).getString("FEMRiskBrightSpot"));
			sb.append("<ul class=\"recommend_list\">"+ dt.get(i).getString("DutyHTMLV3") + "</ul>");
			sb.append("<div class=\"price_tj\"><span class=\"recom_xl\" id=\"SalesV_"+productid+"\">"+SalesVolumeAction.dealSalvesVolumn(productid)+"人已投保</span>");
			sb.append("<span class=\"recom_xl\"><i><a href=\"javascript:void(0)\" onclick=\"openCommnet('"+url+"')\" ");
			sb.append("class=\"shop_tj_num\" id=\"CommentV_"+productid+"\"><i>"+SearchAPI.getCommentCount(productid)+"</i></a></i>人已评价</span>");
			String complateDate = dt.get(i).getString("ClaimsComplateDate");
			if ("1".equals(complateDate)) {
				sb.append("<span class=\"recom_xl\">拍拍速赔</span>");
			}
			sb.append("</div></div><div class=\"nlist_price\">" + diyactivity + "<span class=\"nlist_pay moneys\" name=\"Ajax_Prict_"+productid+"\">");
			sb.append("<em>￥</em>"+dt.get(i).getString("InitPrem")+"</span><em class=\"nlist_pay_t moneys\"><span style=\"display:none\" name=\"Clear_Ajax_Prict_"+productid+"\">");
			sb.append(oPrice+"</span></em><div class=\"remcon_desmore\"><a target=\"_blank\" href=\""+url+"\" rel=\"nofollow\">去看看</a>");
			sb.append("</div></div></div>");
		}
		
		sb1 = sb.toString();
		
		int onePageCount = 10;
		// 分页
		sb2.append("<div id='pagination'><input type='hidden' id='listProductCount' value='"+total+"'/><ul>");
		
		if (pageindex > 0) {
			sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageindex + "\");'><span>上一页</span></a></li>");
			sb2.append("<li><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
		} else {
			sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span class='default'>上一页</span></a></li>");
			sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
		}
		int j = 2;
		for (j = 2;j< (total % onePageCount == 0 ? total / onePageCount : (total / onePageCount + 1)); j++) {
			if(pageCount>6){
				if (pageindex >= pageCount - 4) {
					if (j >= pageCount - 3) {
						if (j == pageCount - 3) {
							sb2.append("<li class='omit'><span>...</span></li>");
						}
						if (j==(pageindex+1)) {
							sb2.append("<li class='now'>");
						} else {
							sb2.append("<li>");
						}
						sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
						
					}
				} else if(pageindex<3){
						if(j<5){
							if (j==(pageindex+1)) {
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
					if(pageindex>2 && pageCount>(pageindex+1)){
						if(j>(pageindex-1)&&j<(pageindex+3)){
							if (j==(pageindex+1)) {
								sb2.append("<li class='now'>");
							} else {
								sb2.append("<li>");
							}
							sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
						}
						if(j==(pageindex+2)&&j<pageCount-2){
							sb2.append("<li class='omit'><span>...</span></li>");
						}
					}
				}
			}else if(pageCount<7){
				if (j==(pageindex+1)) {
					sb2.append("<li class='now'>");
				} else {
					sb2.append("<li>");
				}
				sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
			}
		}

		if (pageindex + 1 == pageCount) {
			if (pageCount > 1) {
				sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span>"+pageCount+"</span></a></li>");
			}
			sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span class='default'>下一页</span></a></li>");
		} else {
			sb2.append("<li><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span>"+pageCount+"</span></a></li>");
			sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doOrder2(\"" + nextPage + "\");'><span>下一页</span></a></li>");
		}
		
		sb2.append("</ul></div>");

		sb3 = sb2.toString();
		
		jsonMap.put("Status", "1");
		jsonMap.put("sb1",sb1);
		jsonMap.put("sb3",sb3);
		return ajaxJson(jsonMap);
	}catch(Exception e){
		jsonMap.put("Status", "0");
		jsonMap.put("error","搜索排序方法出现异常："+e.getMessage());
		logger.error("搜索排序方法出现异常："+e.getMessage(), e);
		return ajaxJson(jsonMap);
		}
	}
	
	
	public String syncPointProduct() {
		// 产品编码
		String productID = getParameter("ProductID");
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(productID)) {
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "请填写产品编码！");
			//Response.setLogInfo(0, "请填写产品编码！");
			return ajaxJson(jsonMap);
		}
		// 积分兑换保险产品栏目ID
		String catalogID = Config.getValue("PointProductCatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "请在系统管理-配置项管理 设置积分兑换保险产品栏目ID！PointProductCatalogID");
			//Response.setLogInfo(0, "请在系统管理-配置项管理 设置积分兑换保险产品栏目ID！PointProductCatalogID");
			return ajaxJson(jsonMap);
		}
		
		CmsServiceImpl csi = new CmsServiceImpl();
		if (csi.publishArticle(productID, catalogID)) {
			jsonMap.put("Status", "1");
			jsonMap.put("LogInfo", "同步成功");
			return ajaxJson(jsonMap);
			//Response.setLogInfo(1, "同步成功");
		} else {
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "同步失败");
			return ajaxJson(jsonMap);
			//Response.setLogInfo(0, "同步失败");
		}
	}
	
	
	
	public String syncPointProductArea() {
		// 产品编码
		String productID = getParameter("ProductID");
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(productID)) {
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "请填写产品编码！");
			return ajaxJson(jsonMap);
			//Response.setLogInfo(0, "请填写产品编码！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RiskCode", new String[] { productID });
		try {
			ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
			FMRisk[] list = productInfo.getFMRisk();
			if (list != null && list.length > 0 && list[0] != null) {
				try {
					if (AutomaticPublishArticle.publishingArea(list[0])) {
						jsonMap.put("Status", "1");
						jsonMap.put("LogInfo", "同步成功！");
						return ajaxJson(jsonMap);
						//Response.setLogInfo(1, "同步成功");
					} else {
						jsonMap.put("Status", "0");
						jsonMap.put("LogInfo", "同步失败！");
						return ajaxJson(jsonMap);
						//Response.setLogInfo(0, "同步失败");
					}
				} catch (Exception e) {
					logger.error("同步产品销售地区失败：" + list[0].getRiskName() + e.getMessage(), e);
					jsonMap.put("Status", "0");
					jsonMap.put("LogInfo", "同步失败！");
					return ajaxJson(jsonMap);
				}
			} else  {
				jsonMap.put("Status", "0");
				jsonMap.put("LogInfo", "同步失败！");
				return ajaxJson(jsonMap);
				//Response.setLogInfo(0, "同步失败!");
			}
		} catch (Exception e) {
			logger.error("同步产品信息失败！" + e.getMessage(), e);
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "同步失败！");
			return ajaxJson(jsonMap);
			//Response.setLogInfo(0, "同步失败!");
		}
	}

	public String syncPointProductHI() {
		// 产品编码
		String productID = getParameter("ProductID");
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(productID)) {
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "请填写产品编码！");
			return ajaxJson(jsonMap);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RiskCode", new String[] { productID });
		try {
			ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
			FMRisk[] list = productInfo.getFMRisk();
			if (list != null && list.length > 0 && list[0] != null) {
				try {
					if (AutomaticPublishArticle.publishingHI(list[0])) {
						jsonMap.put("Status", "1");
						jsonMap.put("LogInfo", "同步成功");
						return ajaxJson(jsonMap);
					} else {
						jsonMap.put("Status", "0");
						jsonMap.put("LogInfo", "同步失败");
						return ajaxJson(jsonMap);
					}
				} catch (Exception e) {
					logger.error("同步产品健康告知失败：" + list[0].getRiskName() + e.getMessage(), e);
					jsonMap.put("Status", "0");
					jsonMap.put("LogInfo", "同步失败");
					return ajaxJson(jsonMap);
				}
			} else  {
				jsonMap.put("Status", "0");
				jsonMap.put("LogInfo", "同步失败");
				return ajaxJson(jsonMap);
			}
		} catch (Exception e) {
			logger.error("同步产品信息失败！" + e.getMessage(), e);
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "同步失败");
			return ajaxJson(jsonMap);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String quicksearch() {
		String productOrder = getParameter("ProductsOrder");
		String eRiskType = getParameter("ProductType");
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (eRiskType == null || "".equals(eRiskType)) {
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "清选择产品类型。");
			return ajaxJson(jsonMap);
		}
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setName("快速查询");
		catalog.setSiteID(getParameter("_CurrentSiteID"));
		ZCCatalogSet catalogs = catalog.query();
		if (catalogs == null || catalogs.size() <= 0) {
			jsonMap.put("Status", "0");
			jsonMap.put("LogInfo", "产品速查.失败:未找到速查栏目,需要建立。");
			logger.info("产品速查.失败:未找到速查栏目,需要建立。");
			return ajaxJson(jsonMap);
		}
		Map<String, String> map = new HashMap<String, String>();
		int iCount = 0;
		if (null!=getParameter("SupplierCode") 
				&& !"".equals(getParameter("SupplierCode"))
				&& !"0".equals(getParameter("SupplierCode"))) {
			iCount++;
			map.put("SupplierCode", getParameter("SupplierCode"));
		}
		if (getParameter("Age") != null
				&& !"".equals(getParameter("Age"))
				&& !"0".equals(getParameter("Age"))) {
			iCount++;
			map.put("Age", getParameter("Age"));
		}
		if (getParameter("Sex") != null
				&& !"".equals(getParameter("Sex"))
				&& !"0".equals(getParameter("Sex"))) {
			iCount++;
			map.put("Sex", getParameter("Sex"));
		}
		map.put("CatalogID", String.valueOf(catalogs.get(0).getID()));
		map.put("ProductType", eRiskType);
		map.put("ERiskType", eRiskType);
		map.put("SearchType", getParameter("SearchType"));
		map.put("ProductType", "quicksearch");
		String risks = "";
		String pageURL = "";
		try {
			GetDBdata db = new GetDBdata();
			String search = db
					.getOneValue("select value from zdconfig where type='quicksearch' ");
			if (StringUtil.isNotEmpty(search) && "Y".equals(search)) {
				pageURL = PublishSearchCache.getCacheHTMLPath(map);
				if (pageURL != null && !"".equals(pageURL)) {
					jsonMap.put("Status", "1");
					jsonMap.put("resultPageURL", pageURL);
					logger.info("产品筛选.缓存页面路径:{}", pageURL);
					return ajaxJson(jsonMap);
				}
			}
		} catch (Exception e) {
		}
		FEMSearchRelaList[] list = null;
		Map<String, Object> sMap = new HashMap<String, Object>();
		sMap.put("SubRiskTypeCode", eRiskType);
		sMap.put("BU1", "N");
		sMap.put("Sex", getParameter("Sex"));
		sMap.put("SupplierCode", getParameter("SupplierCode"));

		if (iCount - 1 > 0) {
			list = new FEMSearchRelaList[iCount - 1];
			Iterator<String> itr = map.keySet().iterator();
			int j = 0;
			String key = "";
			while (itr.hasNext()) {
				key = itr.next();
				if ("CatalogID".equals(key) || "ProductType".equals(key)
						|| "SupplierCode".equals(key)
						|| "SearchType".equals(key) || "ERiskType".equals(key)) {
					continue;
				}
				list[j] = new FEMSearchRelaList();
				list[j].setERiskSubType(eRiskType);
				list[j].setSearchCode(key);
				list[j].setSearchValue(map.get(key));
				j++;
			}
			// sMap.put("FEMSearchRelaList", list);
		}
		logger.info("产品速查.调用接口:{}:{}", eRiskType, productOrder);
		ProductInfoResponse productInfo = null;
		try {
			productInfo = ProductWebservice.ProductInfoSereviceImpl(sMap,
					productOrder);
		} catch (Exception e) {
			logger.error("产品速查失败：" + e.getMessage(), e);
		}
		if (productInfo != null) {
			FMRisk[] productList = productInfo.getFMRisk();
			if (productList != null && productList.length > 0
					&& productList[0] != null) {
				for (int i = 0; i < productList.length; i++) {
					risks += "," + productList[i].getRiskCode();
				}
				if (risks.startsWith(",")) {
					risks = risks.substring(1);
				}
			}
		}
		map.put("CatalogID2",
				new QueryBuilder(
						"select id from zccatalog where SiteID = ? and ProductType = ?",
						getParameter("_CurrentSiteID"), eRiskType)
						.executeString());
		logger.info("产品速查.查询结果:{}", risks);
		map.put("ProductType", "quicksearch");
		pageURL = PublishSearchCache.Publish(risks, map);
		if (pageURL != null && !"".equals(pageURL)) {
			jsonMap.put("Status", "1");
			jsonMap.put("resultPageURL", pageURL);
		}
		logger.info("产品筛选.缓存页面路径:{}", pageURL);
		return ajaxJson(jsonMap);
	}
	
	
	/**
	 * 搜索页满意按钮
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	
	@SuppressWarnings("unchecked")
	public String satisfyOk() throws UnsupportedEncodingException{//keyinput //getLoginMember()
		Map<String, String> jsonMap = new HashMap<String, String>();
		UserSearchOpinionSchema  userSearchOpinionSchema = new UserSearchOpinionSchema();
		String ip =  getParameter("ip");
		String keyinput=getParameter("keyinput");
		if(StringUtil.isNotEmpty(keyinput)){
			keyinput = java.net.URLDecoder.decode(keyinput, "utf-8");
		}
		if(getLoginMember()!=null){
			userSearchOpinionSchema.setmemberId(getLoginMember().getId());
		}else{
			if(StringUtil.isNotEmpty(ip)){
			     userSearchOpinionSchema.setuserIp(ip);
			}else{
				 userSearchOpinionSchema.setuserIp("AnonymousHideIpUser");
			}
		}
		userSearchOpinionSchema.setcreateTime(new Date());
		userSearchOpinionSchema.setinvestigationStatus("1");
		userSearchOpinionSchema.setid(NoUtil.getMaxID("UserSearchOpinion") + "");
		userSearchOpinionSchema.setchannel("wj");
		userSearchOpinionSchema.setsearchName(keyinput);
		try{
			userSearchOpinionSchema.insert();
			jsonMap.put("Status", "1");
	    } catch (Exception e) {
			jsonMap.put("Status","0");
			jsonMap.put("error","出现未知异常，请与管理员联系！"+e.getMessage());
		}
		
		return ajaxJson(jsonMap);
	}
	
	
	/**
	 * 搜索页不满意按钮
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	
	@SuppressWarnings("unchecked")
	public String satisfynNo() throws UnsupportedEncodingException{
		Map<String, String> jsonMap = new HashMap<String, String>();
		UserSearchOpinionSchema  userSearchOpinionSchema = new UserSearchOpinionSchema();
		String reason = getParameter("reason");
		if(StringUtil.isNotEmpty(reason)){
			reason = java.net.URLDecoder.decode(reason, "utf-8");
		}
		String ip =  getParameter("ip");
		String satisfyid ="";
		String keyinput=getParameter("keyinput");
		if(StringUtil.isNotEmpty(reason)){
			reason = java.net.URLDecoder.decode(reason, "utf-8");
		}
		if(getLoginMember()!=null){
			userSearchOpinionSchema.setmemberId(getLoginMember().getId());
		}else{
			if(StringUtil.isNotEmpty(ip)){
			     userSearchOpinionSchema.setuserIp(ip);
			}else{
				 userSearchOpinionSchema.setuserIp("AnonymousHideIpUser");
			}
		}
		userSearchOpinionSchema.setcreateTime(new Date());
		userSearchOpinionSchema.setinvestigationStatus("2");
		satisfyid=NoUtil.getMaxID("UserSearchOpinion") + "";
		userSearchOpinionSchema.setid(satisfyid);
		userSearchOpinionSchema.setchannel("wj");
		userSearchOpinionSchema.setsearchName(keyinput);
		userSearchOpinionSchema.setreason(reason);
		try{
			userSearchOpinionSchema.insert();
			jsonMap.put("Status", "1");
			jsonMap.put("satisfyid", satisfyid);
	    } catch (Exception e) {
			jsonMap.put("Status","0");
			jsonMap.put("error","出现未知异常，请与管理员联系！"+e.getMessage());
		}
		return ajaxJson(jsonMap);
	}
	
	
	
	/**
	 * 帮您找保险按钮
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	
	@SuppressWarnings("unchecked")
	public String helpSearch() throws UnsupportedEncodingException{
		Map<String, String> jsonMap = new HashMap<String, String>();
		UserSearchOpinionSchema  userSearchOpinionSchema = new UserSearchOpinionSchema();
		String ip =  getParameter("ip");
		String surveyName = getParameter("surveyName");
		String surveyTel = getParameter("surveyTel");
		String keyinput=getParameter("keyinput");
		if(StringUtil.isNotEmpty(surveyTel)){
			surveyTel = java.net.URLDecoder.decode(surveyTel, "utf-8");
		}
		if(StringUtil.isNotEmpty(surveyName)){
			surveyName = java.net.URLDecoder.decode(surveyName, "utf-8");
		}
		if(StringUtil.isNotEmpty(keyinput)){
			keyinput = java.net.URLDecoder.decode(keyinput, "utf-8");
		}
		if(getLoginMember()!=null){
			userSearchOpinionSchema.setmemberId(getLoginMember().getId());
		}else{
			if(StringUtil.isNotEmpty(ip)){
			     userSearchOpinionSchema.setuserIp(ip);
			}else{
				 userSearchOpinionSchema.setuserIp("AnonymousHideIpUser");
			}
		}
		userSearchOpinionSchema.setcreateTime(new Date());
		userSearchOpinionSchema.setinvestigationStatus("0");
		userSearchOpinionSchema.setid(NoUtil.getMaxID("UserSearchOpinion") + "");
		userSearchOpinionSchema.setinsuranceName(surveyName);
		userSearchOpinionSchema.settelephone(surveyTel);
		userSearchOpinionSchema.setchannel("wj");
		userSearchOpinionSchema.setsearchName(keyinput);
		try{
			userSearchOpinionSchema.insert();
			jsonMap.put("Status", "1");
	    } catch (Exception e) {
			jsonMap.put("Status","0");
			jsonMap.put("error","出现未知异常，请与管理员联系！"+e.getMessage());
		}
		return ajaxJson(jsonMap);
	}

	
	
	
}
