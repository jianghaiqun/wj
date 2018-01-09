package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ZDCode;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.IndexNavigationService;
import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.product.SortList;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * auther sinosoft
 * 用于首页快速导航查询
 */
@ParentPackage("shop")
public class IndexNavigationAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312353046197298683L;
	//首页快速查询-年龄
	private String ages;
	//旅行天数
	private String period;
	//旅行地点
	private String travelAddress;
	// 投保年龄
	private String age;
	// 保障类型
	private String features;
	// 保险金额
	private String prem;
	private String ProductsOrder;
	private String activityFlag;
	private String ProductType;
	private int pageIndex;
	private String searchProductFlag;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private IndexNavigationService indexNavigationService;
	
	
	/**
	 * 首页旅游险浮动层 查看计划
	 * @return
	 */
	public String indexQueryProduct(){
		
		Map<String, String> jsonMap = new HashMap<String, String>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		
		String period =(StringUtil.isNotEmpty(request.getParameter("period")))?request.getParameter("period"):"";
	    //本地使用代码	
		//String travelAddress = (StringUtil.isNotEmpty(new String(request.getParameter("travelAddress").getBytes("ISO-8859-1"),"UTF-8")))?new String(request.getParameter("travelAddress").getBytes("ISO-8859-1"),"UTF-8"):"";
		//35使用代码
		String travelAddress = (StringUtil.isNotEmpty(request.getParameter("travelAddress")))?request.getParameter("travelAddress"):"";
		String age_one = (StringUtil.isNotEmpty(request.getParameter("age_one")))?request.getParameter("age_one"):"";
		String age_two = (StringUtil.isNotEmpty(request.getParameter("age_two")))?request.getParameter("age_two"):"";
		String age_three = (StringUtil.isNotEmpty(request.getParameter("age_three")))?request.getParameter("age_three"):"";
		String[] ages = new String[]{age_one,age_two,age_three};
		
		String productinfohtml="";
		DataTable dt = new DataTable(); 
		dt = SearchAPI.getSearchProduct(ages,period,travelAddress);
		for(int t=0;t<dt.getRowCount();t++){
			if(t>=2) break;
			String url = dt.get(t).getString("URL");
			if (url.indexOf("://") < 0) {
				String siteUrl = SiteUtil.getURL(dt.get(t).getString("SITEID"));
				if (!siteUrl.endsWith("/")) {
					siteUrl = siteUrl + "/";
				}
				if (url.startsWith("/")) {
					url = url.substring(1);
				}
				url = siteUrl + url;
			}
			StringBuffer subhtml = new StringBuffer(" <div class=\"clear h20\"></div> ");
			if(t<=0){
				subhtml = new StringBuffer(" ");
			}
			subhtml.append(" <div class=\"m-recommend-list\">  <div class=\"clear\"></div> ");
			subhtml.append(" <a href=\""+url+"\" class=\"m-recommend-a\" target=\"_blank\">"+dt.get(t).getString("ProductName")+"</a> ");
			subhtml.append(" <ul>"+dt.get(t).getString("FEMRiskBrightSpotNew")+"</ul> ");//亮点
			subhtml.append("  <div class=\"clear h10\"></div> ");
			subhtml.append(" <div class=\"m-recommend-money\"><span class=\"m-re-money f-ib f_mi\">￥"+dt.get(t).getString("InitPrem")+"</span> ");
			subhtml.append(" <span class=\"m-xj-money f-ib f_mi\">￥"+dt.get(t).getString("BasePrem")+"</span></div> ");
			subhtml.append(" <a href=\""+url+"\" class=\"f-submit-btn m-recommend-go\" target=\"_blank\">查看详情</a> ");
			subhtml.append(" </div> ");
			
			productinfohtml += subhtml.toString();
		}
		jsonMap.put("lvyouhtml", productinfohtml);
		
		return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
	}
	
	/**
	 * 首页一键筛选
	 * @return 
	 */
	public String quicksearchplan(){
		
		Map<String, String> jsonMap = new HashMap<String, String>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		
		String param =(StringUtil.isNotEmpty(request.getParameter("param")))?request.getParameter("param"):"";
		
		try {
			param = java.net.URLDecoder.decode(param,"utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		String productType =(StringUtil.isNotEmpty(request.getParameter("productType")))?request.getParameter("productType"):"";
		if(StringUtil.isEmpty(productType)){
			productType="A";
		}
		String[] tParam = param.split(",");
		String ages = tParam[0];
		String travel = tParam[1];
		String period = tParam[2];
		//String features = tParam[3];
		//String prem = tParam[4];
		
		DataTable dt = new DataTable(); 
		if("A".equals(productType)){
			String[] agesarr = null;
			if(!"default".equals(ages)){
				agesarr = new String[]{ages};
			}else{agesarr = new String[]{ages};
				agesarr = new String[]{""};
			}
			if("default".equals(travel)){
				travel = "";
			}
			dt = SearchAPI.getSearchProduct(agesarr,period,travel);
		}else{
			dt = SearchAPI.getQuickSearchProduct(param);
		}
	    
		String productA = "";
		String productB = "";
		String productC = "";
		String productD = "";
		if(dt!=null && dt.getRowCount()>=1){
			if(StringUtil.isNotEmpty(dt.getString(0,0))){
				productA = dt.getString(0,0);
			}
		}
		if(dt!=null && dt.getRowCount()>=2){
			if(StringUtil.isNotEmpty(dt.getString(1,0))){
				productB = dt.getString(1,0);
			}
		}
		if(dt!=null && dt.getRowCount()>=3){
			if(StringUtil.isNotEmpty(String.valueOf(dt.get(2,0)))){
				productC = dt.getString(2,0);
			}
		}
		if(dt!=null && dt.getRowCount()>=4){
			if(StringUtil.isNotEmpty(dt.getString(3,0))){
				productD = dt.getString(3,0);
			}
		}
		String tflag = "";
		String compareurl = "eriskType="+productType+"&productA="+productA+"&productB="+productB+"&productC="+productC+"&productD="+productD;
		if(dt==null || dt.getRowCount()<=0){
			tflag = "01";
		}
		jsonMap.put("tflag", tflag);
		jsonMap.put("compareurl", compareurl);
		
		return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
		
	}
	
	public String quickQueryProductRe() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		String CpsUserId = request.getParameter("CpsUserId");
		// 拆封条件，去查询产品中心数据库，获取产品编码
		String channelSn = "wj";// 渠道编码，默认wj
		// 如果有cps用户的coolie信息，那么渠道编码为cps
		if (StringUtil.isNotEmpty(CpsUserId)) {
			channelSn = "cps";
		}
		Map<String, String> jsonMap = new HashMap<String, String>();
		FilterAction filterAction = new FilterAction();
		jsonMap.put("status", "0");
		String memberId = "";
		Member member = getLoginMember();
		if (member != null) {
			memberId = member.getId();
		}
		try {
			String proType = ProductType.substring(0, 1);
			if(StringUtil.isNotEmpty(travelAddress)){
				travelAddress = java.net.URLDecoder.decode(travelAddress, "UTF-8");
			}
			DataTable dt0 = SearchAPI.getSearchProduct(ProductType, age, period, travelAddress, features, prem);
			String tFlag = "";
			if (dt0.getRowCount() > 0) {
				setSession("dt0", dt0);
			} else {
				if (getSession("dt0") != null) {
					dt0 = (DataTable) getSession("dt0");
					tFlag = "02";
				}
			}
			int onePageCount=10;//每个页面显示的产品数量，根据列表页不同而不同
			if (dt0.getRowCount() > 0) {
				DataTable dt = new DataTable();
				if (pageIndex != 0) {
					pageIndex = pageIndex - 1;
				}
				int nextPage = pageIndex + 2;
				int start = pageIndex * onePageCount;
				List<DataRow> alist = new ArrayList<DataRow>();
				
				for (int i = 0; i < dt0.getRowCount(); i++) {
					alist.add(dt0.get(i));
				}
				if (StringUtil.isNotEmpty(ProductsOrder)) {
					String[] str1 = ProductsOrder.split(" ");
					SortList<DataRow> sortList = new SortList<DataRow>();
					sortList.Sort(alist, str1[0], str1[1]);
				}
				for (DataRow dt2 : alist) {
					dt.insertRow(dt2);
				}
				StringBuffer sb1 = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				int total = 0;
				if ("Y".equals(activityFlag)) {
					for (int i = 0; i < dt.getRowCount(); i++) {
						String url = dt.get(i).getString("URL");
						if (url.indexOf("://") < 0) {
							String siteUrl = SiteUtil.getURL(dt.get(i).getString("SITEID"));
							if (!siteUrl.endsWith("/")) {
								siteUrl = siteUrl + "/";
							}
							if (url.startsWith("/")) {
								url = url.substring(1);
							}
							url = siteUrl + url;
						}
						AjaxPriceAction ap = new AjaxPriceAction();
						String riskcode = dt.get(i).getString("ProductID");
						String newPrice = ap.queryAjaxPrice(riskcode, memberId);
						String oPrice = dt.get(i).getString("Remark4");
						if (StringUtil.isEmpty(oPrice)) {
							oPrice = "0";
						}
						if (StringUtil.isEmpty(newPrice)) {
							newPrice = "0";
						}
						boolean flag = true;
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
								// 筛选促销产品，没有打折优惠价格的产品不显示
								flag = false;
								
								oPrice = "";
							}
						} else {
							oPrice = "原价<em>￥</em>" + oPrice;
						}
						// 是否有满减活动
						String productid = dt.get(i).getString("ProductID");
						Map<String, String> map = filterAction.searchProductListAvtivityRe(productid, channelSn, discountRate);
						String diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\">";
						if (map == null || map.size() == 0) {
							// 筛选促销产品，没有参加活动的产品不显示
							if (!flag) {
								continue;
							}
							
							diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\" style=\"display: none;\">";
							diyactivity += "</span><em id=\"Diy_Activity_"+productid+"\" style=\"display: none;\"></em></p>";
						} else {
							for (String key : map.keySet()) {
								String value = map.get(key);
								String title = value.substring(0, value.indexOf("@"));
								String description = value.substring(value.indexOf("@") + 1, value.length());
								diyactivity += (title+"</span><em id=\"Diy_Activity_"+productid+"\">"+description+"</em></p>");
							}
						}
						total++;
						if (total > start + onePageCount) {
							continue;
						}
						
						if (total <= start) {
							continue;
						}
						sb1.append("<div class=\"nlist_con cf\"><div class=\"nlist_des\">");
						sb1.append("<div class=\"icon_C"+dt.get(i).getString("SupplierCode2")+" m-list-logo\"></div>");
						
						sb1.append("<div class=\"m-list-cn\"><a href=\""+url+"\" target=\"_blank\" class=\"nlist_title\">");
						sb1.append(dt.get(i).getString("ProductName") + "</a><div class=\"shop_tj_shrq cf\"><p class=\"shop_shrd_con\">");
						sb1.append("<span class=\"shop_shrq_bg\">适合人群</span>" + dt.get(i).getString("AdaptPeopleInfoListV3") + "</p></div></div>");
						sb1.append("<div class=\"clear\"></div><ul class=\"recommend_list\">"+ dt.get(i).getString("DutyHTMLV3") + "</ul>");
						sb1.append("<div class=\"price_tj\"><span class=\"recom_xl\" id=\"SalesV_"+productid+"\">"+SalesVolumeAction.dealSalvesVolumn(productid)+"人已投保</span>");
						sb1.append("<span class=\"recom_xl\"><i><a href=\"javascript:void(0)\" onclick=\"openCommnet('"+url+"')\" ");
						sb1.append("class=\"shop_tj_num\" id=\"CommentV_"+productid+"\"><i>"+SearchAPI.getCommentCount(productid)+"</i></a></i>人已评价</span></div></div>");
						sb1.append("<div class=\"nlist_price\">" + diyactivity + "<span class=\"nlist_pay moneys\" name=\"Ajax_Prict_"+productid+"\">");
						sb1.append("<em>￥</em>"+newPrice+"</span><em class=\"nlist_pay_t moneys\"><span name=\"Clear_Ajax_Prict_"+productid+"\">");
						sb1.append(oPrice+"</span></em><div class=\"remcon_desmore\"><a target=\"_blank\" href=\""+url+"\" rel=\"nofollow\">去看看</a>");
						sb1.append("<label class=\"nlist_add_db\" onclick=\"showcp('"+dt.get(i).getString("ProductName"));
						sb1.append("','"+dt.get(i).getString("logo")+"','"+productid+"','"+Config.getServerContext()+"','"+proType);
						sb1.append("','"+dt.get(i).getString("LogoLink")+"','"+dt.get(i).getString("InitPrem")+"');\">加入对比</label></div></div></div>");
						
					}
				} else {
					total = dt.getRowCount();
					for (int i = start; i < start + onePageCount && i < dt.getRowCount(); i++) {

						String url = dt.get(i).getString("URL");
						if (url.indexOf("://") < 0) {
							String siteUrl = SiteUtil.getURL(dt.get(i).getString("SITEID"));
							if (!siteUrl.endsWith("/")) {
								siteUrl = siteUrl + "/";
							}
							if (url.startsWith("/")) {
								url = url.substring(1);
							}
							url = siteUrl + url;
						}
						AjaxPriceAction ap = new AjaxPriceAction();
						String riskcode = dt.get(i).getString("ProductID");
						String newPrice = ap.queryAjaxPrice(riskcode, memberId);
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
									"  select BackUp2 from femriskb where RiskCode=? and IsPublish='Y'  ", riskcode )
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
						
						// 是否有满减活动
						String productid = dt.get(i).getString("ProductID");
						Map<String, String> map = filterAction.searchProductListAvtivityRe(productid, "wj", discountRate);
						String diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\">";
						if (map == null || map.size() == 0) {
							diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\" style=\"display: none;\">";
							diyactivity += "</span><em id=\"Diy_Activity_"+productid+"\" style=\"display: none;\"></em></p>";
						} else {
							for (String key : map.keySet()) {
								String value = map.get(key);
								String title = value.substring(0, value.indexOf("@"));
								String description = value.substring(value.indexOf("@") + 1, value.length());
								diyactivity += (title+"</span><em id=\"Diy_Activity_"+productid+"\">"+description+"</em></p>");
							}
						}
						sb1.append("<div class=\"nlist_con cf\"><div class=\"nlist_des\">");
						sb1.append("<div class=\"icon_C"+dt.get(i).getString("SupplierCode2")+" m-list-logo\"></div>");
						
						sb1.append("<div class=\"m-list-cn\"><a href=\""+url+"\" target=\"_blank\"  class=\"nlist_title\">");
						sb1.append(dt.get(i).getString("ProductName") + "</a><div class=\"shop_tj_shrq cf\"><p class=\"shop_shrd_con\">");
						sb1.append("<span class=\"shop_shrq_bg\">适合人群</span>" + dt.get(i).getString("AdaptPeopleInfoListV3") + "</p></div></div>");
						sb1.append("<div class=\"clear\"></div><ul class=\"recommend_list\">"+ dt.get(i).getString("DutyHTMLV3") + "</ul>");
						sb1.append("<div class=\"price_tj\"><span class=\"recom_xl\" id=\"SalesV_"+productid+"\">"+SalesVolumeAction.dealSalvesVolumn(productid)+"人已投保</span>");
						sb1.append("<span class=\"recom_xl\"><i><a href=\"javascript:void(0)\" onclick=\"openCommnet('"+url+"')\" ");
						sb1.append("class=\"shop_tj_num\" id=\"CommentV_"+productid+"\"><i>"+SearchAPI.getCommentCount(productid)+"</i></a></i>人已评价</span></div></div>");
						sb1.append("<div class=\"nlist_price\">" + diyactivity + "<span class=\"nlist_pay moneys\" name=\"Ajax_Prict_"+productid+"\">");
						sb1.append("<em>￥</em>"+newPrice+"</span><em class=\"nlist_pay_t moneys\"><span name=\"Clear_Ajax_Prict_"+productid+"\">");
						sb1.append(oPrice+"</span></em><div class=\"remcon_desmore\"><a target=\"_blank\" href=\""+url+"\" rel=\"nofollow\">去看看</a>");
						sb1.append("<label class=\"nlist_add_db\" onclick=\"showcp('"+dt.get(i).getString("ProductName"));
						sb1.append("','"+dt.get(i).getString("logo")+"','"+productid+"','"+Config.getServerContext()+"','"+proType);
						sb1.append("','"+dt.get(i).getString("LogoLink")+"','"+dt.get(i).getString("InitPrem")+"');\">加入对比</label></div></div></div>");
						
					}
				}
				
				
				int pageCount = new Double(Math.ceil(total * 1.0 / onePageCount)).intValue();
				// 分页
				sb2.append("<div id='pagination'><input type='hidden' id='listProductCount' value='"+total+"'/><ul>");
				
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
				
				sb2.append("</ul></div>");
				
				if ("02".equals(tFlag)) {
					jsonMap.put("status", "2");
				} else {
					jsonMap.put("status", "1");
				}
				if (total != 0) {
					jsonMap.put("sb1", sb1.toString());
					jsonMap.put("sb3", sb2.toString());
				}
				jsonMap.put("total", total+"");
			}
		} catch (Exception e) {
			jsonMap.put("status", "0");
			logger.error("搜索排序方法出现异常：" + e.getMessage(), e);
		}
		return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
	}
	
	/**
	 * 得到前台AJAX传递的字符串，并返回根据拆分后的字符得到的产品拼装后的HTML代码块
	 * 
	 * @return
	 */
	public String quickQueryProduct() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		
		String ProductType = "A";//旅游险默认
		// 拆封条件，去查询产品中心数据库，获取产品编码
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "0");
		try {
			//String travelAddress1 = new String(travelAddress.getBytes("ISO-8859-1"),"UTF-8");
			if(StringUtil.isNotEmpty(travelAddress)){
				travelAddress = java.net.URLDecoder.decode(travelAddress, "UTF-8");
			}
			String tSearchCode = SearchAPI.getSearchCodeByTravelAddress(travelAddress);
			String[] cAges = ages.split(",");
			String ageSearchCode = SearchAPI.getSearchCodeByAge(cAges);
			StringBuffer sbSerch = new StringBuffer();
			sbSerch.append(" SELECT DISTINCT e.ProductId,e.ProductActive,e.URL,e.ProductName,e.LogoLink,e.AdaptPeopleInfoListV3,e.DutyHTMLV3,e.InitPrem,e.BasePremV3,c.ProductType FROM sdproduct c,sdsearchrelaproduct e ");
			sbSerch.append(" WHERE c.productID=e.productID ");
			sbSerch.append(" AND  c.IsPublish='Y' ");
			if(StringUtil.isNotEmpty(period)){
				if(period.indexOf("-")!=-1){
					period = period.split("-")[1];
				} 
				sbSerch.append(" AND ("+period+" <= func_splitString(c.remark2,'-',1) or "+period+" <=func_splitString(c.remark2,'-',2)) ");//出行天数
			}
			if(StringUtil.isNotEmpty(tSearchCode)){
				sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+tSearchCode+"  AND c.productid = f.riskcode) ");//旅游地点
			}
	        if(StringUtil.isNotEmpty(ageSearchCode)){
				sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+ageSearchCode+" AND c.productid = f.riskcode) ");//年龄
			}
			sbSerch.append(" order by c.feerate desc");
			QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());
			DataTable dt0 = qb0.executeDataTable();
			String tFlag = "";
			if (dt0.getRowCount() > 0) {
				setSession("dt0", dt0);
			} else {
				if (getSession("dt0") != null) {
					dt0 = (DataTable) getSession("dt0");
					tFlag = "02";
				}
			}
			int onePageCount=15;//每个页面显示的产品数量，根据列表页不同而不同
			if("ltblist".equals(searchProductFlag)){
				onePageCount = 15;
			}else if("listv3".equals(searchProductFlag)){
				onePageCount = 10;
			}
			if (dt0.getRowCount() > 0) {
				DataTable dt = new DataTable();
				if (pageIndex != 0) {
					pageIndex = pageIndex - 1;
				}
				int nextPage = pageIndex + 2;
				int start = pageIndex * onePageCount;
				List<DataRow> alist = new ArrayList<DataRow>();
				int total = dt0.getRowCount();
				int pageCount = new Double(Math.ceil(total * 1.0 / onePageCount)).intValue();
				for (int i = 0; i < dt0.getRowCount(); i++) {
					alist.add(dt0.get(i));
				}
				for (DataRow dt2 : alist) {
					dt.insertRow(dt2);
				}
				StringBuffer sb1 = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();

				for (int i = start; i < start + onePageCount && i < dt.getRowCount(); i++) {

					String url = dt.get(i).getString("URL");
					if (url.indexOf("://") < 0) {
						String siteUrl = SiteUtil.getURL(dt.get(i).getString("SITEID"));
						if (!siteUrl.endsWith("/")) {
							siteUrl = siteUrl + "/";
						}
						if (url.startsWith("/")) {
							url = url.substring(1);
						}
						url = siteUrl + url;
					}
					
					if("ltblist".equals(searchProductFlag)){
						sb1.append("<dl class=\"shop_img_con\">");
						//活动
						sb1.append("<dt class=\"shop_img_head\">");
						//是否有满减活动
						FilterAction tFilterAction = new FilterAction();
						//是否有满减活动
						Map<String,String> map=tFilterAction.searchProductListAvtivity(dt.get(i).getString("ProductID"),"wj");
						if(map==null||map.size()==0){
							sb1.append(""+dt.get(i).getString("ProductActive")+"");
						}else{
							 for (String key : map.keySet()) {
								String value=map.get(key);
								String type=key.substring(key.indexOf("@")+1,key.length());
								if(!"8".equals(type)){//非自定义活动
									sb1.append(value);
									break;
								}
							}
						}
						//title
						sb1.append("<a rel=\"nofollow\" target=\"_blank\" href=\""+url+"\"><img width=\"190\" height=\"190\" alt=\""+dt.get(i).getString("ProductName")+"\" src=\""+dt.get(i).getString("LogoLink")+"\" class=\"lazy\" data-original=\""+dt.get(i).getString("LogoLink")+"\" style=\"display: inline;\"></a>");
						sb1.append("<div class=\"shop_box_des\" style=\"display:none;\">");
						sb1.append("<div class=\"shop_box_l\"><span></span></div>");
						sb1.append("<a href=\""+url+"\" target=\"_blank\" rel=\"nofollow\" class=\"shop_pos_a\"></a>");
						sb1.append("<div class=\"shop_box_con\"><div class=\"shop_xx\"> <b>适合人群</b>");
						sb1.append("<p class=\"shop_product\">"+dt.get(i).getString("AdaptPeopleInfo")+"</p><b>产品特色</b>");
						sb1.append("<ul class=\"shop_desafs\">"+dt.get(i).getString("FEMRiskBrightSpotNew")+"</ul>");
	                    sb1.append("</div></div>");
	                    sb1.append("<div class=\"shop_box_con2\"> <div class=\"shop_xx\">");
	                    sb1.append("<b>保险责任</b><ul class=\"recommend_list\">");
	                    sb1.append(""+dt.get(i).getString("DutyHTML2")+"</ul>");
	                    sb1.append("</div></div>");
	                    sb1.append("<div class=\"shop_box_r\"><span class=\"shop_btns_r\"></span> </div>");
						sb1.append("</div>");
						sb1.append("</dt>");
						sb1.append("<dd class=\"shop_img_titile\"><a target=\"_blank\" href=\""+url+"\">"+dt.get(i).getString("ProductName")+"</a></dd>");
						sb1.append("<dd class=\"img_price_con\"><b class=\"moneys img_box_price\">￥"+dt.get(i).getString("InitPrem")+"</b>"+dt.get(i).getString("BasePrem")+"</dd>");
						sb1.append("<dd class=\"shop_operating\">");
						sb1.append("<span class=\"shop_sales\">销量：<i class=\"img_xl_num\">"+ dt.get(i).getString("SalesVolume") +"</i></span>");
						sb1.append("<span class=\"add_collect\"  onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("ProductId") + "','"+Config.getServerContext() + "','" + ProductType + "','"+dt.get(i).getString("LogoLink")+"','"+dt.get(i).getString("InitPrem")+"');\"  title=\"加入对比\"></span>");
						sb1.append("<span id=\"contrast_"+dt.get(i).getString("ProductId")+"\" onclick=\"submitp('" + dt.get(i).getString("ProductId") + "');\"  class=\"add_contrast\" title=\"加入收藏\"></span>");
						sb1.append("</dd>");
						sb1.append("</dl>");
					}else if("listv3".equals(searchProductFlag)){
						sb1.append("<div class=\"nlist_con cf\">");
						//活动
						sb1.append("<div class=\"shop_nlist_img\">");
						FilterAction tFilterAction = new FilterAction();
						//是否有满减活动
						String productid=dt.get(i).getString("ProductID");
						Map<String,String> map=tFilterAction.searchProductListAvtivity(dt.get(i).getString("ProductID"),"wj");
						String diyactivity="";
						if(map==null||map.size()==0){
							sb1.append(""+dt.get(i).getString("ProductActive")+"");
						}else{
							 for (String key : map.keySet()) {
								String value=map.get(key);
								String type=key.substring(key.indexOf("@")+1,key.length());
								if(!"8".equals(type)){//非自定义活动
									sb1.append(value);
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
						sb1.append("<a rel=\"nofollow\" target=\"_blank\" href=\""+url+"\"><img width=\"190\" height=\"190\" alt=\""+dt.get(i).getString("ProductName")+"\" src=\""+dt.get(i).getString("LogoLink")+"\" class=\"lazy\" data-original=\""+dt.get(i).getString("LogoLink")+"\" style=\"display: inline;\"></a>");
						sb1.append("</div>");
						sb1.append("<div class=\"nlist_des\">");
						sb1.append("<a href=\""+url+"\" target=\"_blank\" rel=\"nofollow\" class=\"nlist_title\">"+dt.get(i).getString("ProductName")+"</a>");
						sb1.append(diyactivity);
						//适合人群
						sb1.append("<div class=\"shop_tj_shrq cf\"> <span class=\"shop_shrq_bg\">适合人群</span>");
						sb1.append("<p class=\"shop_shrd_con\">"+dt.get(i).getString("AdaptPeopleInfoListV3")+"</p></div>");
	                    sb1.append("<div class=\"clear\"></div>");
	                    //保险责任
	                    sb1.append("<ul class=\"recommend_list\">");
	                    sb1.append(""+dt.get(i).getString("DutyHTMLV3")+"</ul>");
	                    //价格
						sb1.append("<div class=\"nlist_price\">");
						sb1.append("<span class=\"nlist_pay moneys\">"+dt.get(i).getString("InitPrem")+"</span><em class='nlist_pay_t moneys'>"+dt.get(i).getString("BasePremV3")+"</em>");
						sb1.append("<div class=\"price_tj\">");
						sb1.append("<span class=\"recom_xl\">已有"+dt.get(i).getString("SalesVolume")+"人投保</span>");
						sb1.append("<span class=\"recom_xl\"><i>（<a class=\"shop_tj_num\" href=\"javascript:void(0)\" onclick=\"openCommnet('"+url+"')\">"+SearchAPI.getCommentCount(dt.get(i).getString("ProductId"))+"</a>） </i> 条评论</span>");
						sb1.append("</div>");
						
						
						sb1.append("<span class=\"remcon_desmore\"><label class=\"nlist_add_db\">");
						sb1.append("<input type=\"checkbox\" onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("ProductId") + "','"+Config.getServerContext() + "','" + ProductType + "','"+dt.get(i).getString("LogoLink")+"','"+dt.get(i).getString("InitPrem")+"');\">加入对比</label>");
						sb1.append("<a target=\"_blank\" href="+url+">去看看</a></span>");
						
						sb1.append("</div></div></div>");
					}
					
				}
				
				sb2.append("<div class='plpage'>");
				sb2.append("<div class='plpagecont'>");
				if (pageIndex > 0) {
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + 1 + "\");'>首页</a></span>");
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + pageIndex + "\");'><</a></span>");
				} else {
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + 1 + "\");'>首页</a></span>");
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + 1 + "\");'><</a></span>");
				}
				int j = 1;  
				for( j = 1;j<=(total%onePageCount==0?total/onePageCount:(total/onePageCount+1));j++){    
					if(j==(pageIndex+1)){
						sb2.append("<span class='plpage04'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'><font color = '#FFFFFF'> "+j+"</font></a></span>&nbsp;");
					}
					//如果总页面大于5
					else if(pageCount>5){
						if(pageIndex>3 && pageCount>(pageIndex+1)){
							if(j>(pageIndex-3)&&j<(pageIndex+3)){
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'> "+j+" </a></span>&nbsp;");
							}
							if(j==(pageIndex+2)&&j!=pageCount){
								sb2.append("...&nbsp;");
							}else if(j==(pageCount-1)&&(pageIndex+1)==(pageCount-2)){
								sb2.append("...&nbsp;");
							}
						}
						if(pageIndex>3&&pageCount<(pageIndex+2)){
							if(j>pageCount-5){					
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
							}
						}
						if(pageIndex<4){
							if(j<6){				
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
								if(j==5){						
									sb2.append("...&nbsp;");
								}
							}
						}
						
					}
					//如果总页面小于5 则全部显示
					else if(pageCount<6){
						sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
					}
				}
				if (pageIndex + 1 != pageCount && pageCount > 0) {
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + nextPage + "\");'>></a></span>");
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + pageCount + "\");'>尾页</a></span>");
				} else {
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + pageCount + "\");'>></a></span>");
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doQuickOrder2(\"" + pageCount + "\");'>尾页</a></span>");
				}

				sb2.append("&nbsp;&nbsp;共" + (total%onePageCount==0?total/onePageCount:(total/onePageCount+1)) + "页&nbsp;&nbsp;");
				sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
				sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

				sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>"
								+ pageCount
								+ "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){newQuickJump();}else{newQuickJump();}}\" style='' value='跳转'></td>");
				
				sb2.append("</div>");
				sb2.append("</div>");


				if("02".equals(tFlag)){
					jsonMap.put("status", "2");
				}else{
					jsonMap.put("status", "1");
				}
				if (dt.getRowCount() != 0) {
					jsonMap.put("sb1", sb1.toString());
					jsonMap.put("sb3", sb2.toString());
				}
				
			}
		} catch (Exception e) {
			jsonMap.put("status", "0");
			logger.error("搜索排序方法出现异常：" + e.getMessage(), e);
		}
		return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
	}
	
	public String queryAddress(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		QueryBuilder qb = new QueryBuilder(" Select CodeValue from ZDCode where CodeType='TravelAddress' ");
		DataTable dt = qb.executeDataTable();
		List<String> tList = new ArrayList<String>();
		for(int i=0;i<dt.getRowCount();i++){
			tList.add(dt.getString(i, 0));
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("address", tList);
		return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
	}
	public String queryAddressList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		String addrs = indexNavigationService.getAddressList();
		return ajaxHtml(jsonpname + "(" + addrs + ");");
	}
	public ZDCode dealZDCode(DataRow dr){
		ZDCode zdcode = new ZDCode();
		zdcode.setCodeValue(dr.getString("CodeValue"));//中文
		zdcode.setCodeName(dr.getString("CodeName"));
		zdcode.setProp1(dr.getString("Prop1"));//全拼
		zdcode.setProp3(dr.getString("Prop3"));//简写
		
		return zdcode;
		
	}
	/**
	 * 获取客户端IP
	 * @return
	 */
	public String queryIPAddress(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String ip = getRequest().getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getRemoteAddr();
			}
			jsonMap.put("ip", ip);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getTravelAddress() {
		return travelAddress;
	}
	public void setTravelAddress(String travelAddress) {
		this.travelAddress = travelAddress;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getSearchProductFlag() {
		return searchProductFlag;
	}
	public void setSearchProductFlag(String searchProductFlag) {
		this.searchProductFlag = searchProductFlag;
	}
	public String getAges() {
		return ages;
	}
	public void setAges(String ages) {
		this.ages = ages;
	}
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getPrem() {
		return prem;
	}

	public void setPrem(String prem) {
		this.prem = prem;
	}

	public String getProductsOrder() {
		return ProductsOrder;
	}

	public void setProductsOrder(String productsOrder) {
		ProductsOrder = productsOrder;
	}

	public String getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag) {
		this.activityFlag = activityFlag;
	}

	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		ProductType = productType;
	}

	public static void main(String args) {
		SearchAPI.getSearchCodeByTravelAddress("美国 英国");
	}
}
