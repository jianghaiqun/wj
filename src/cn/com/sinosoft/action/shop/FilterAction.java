package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.common.JCaptchaEngine;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.impl.OrderConfigNewServiceImpl;
import com.octo.captcha.service.CaptchaService;
import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FreemarkerUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.inter.IntegralAction;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.product.Filtrate;
import com.sinosoft.product.PremCalculate;
import com.sinosoft.product.SortList;
import com.sinosoft.schema.SDInformationDutyTempSchema;
import com.sinosoft.schema.SDInformationDutyTempSet;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.ProductPremResponse;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class FilterAction extends BaseShopAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private CaptchaService captchaService;
	private String condtion;
	private int pageIndex;
	private String ProductsOrder;
	private String ProductType;
	private String searchProductFlag;// 产品列表页标志ltblist:类淘宝列表页
	private String ChiProductType; // 子栏目险种类型，用于筛选
	private String activityFlag;// 筛选促销标识产品
	
	public String getSearchSQL(String catalogId, String[] conditionsplit) {
		
		StringBuffer sbSerch = new StringBuffer();
		sbSerch.append("select distinct e.*,c.Remark4,f.ComplateDate from zcarticle a,sdsearchrelaproduct e,sdproduct c left join fmrisk f on c.productID = f.RiskCode ");
		sbSerch.append("where a.catalogid='" + catalogId + "' and a.status='30' AND (a.id = e.prop1 or e.Prop1=a.ReferSourceID) and c.productID=e.productID ");

		for (int j = 0; j < conditionsplit.length; j++) {
			if ((conditionsplit[j]).startsWith("default_")) {
				continue;
			} else {
				if (conditionsplit[j].indexOf(",") != 0) {
					String[] conditionsplit2 = conditionsplit[j].split(",");
					for (int k = 0; k < conditionsplit2.length; k++) {
						if ("Y".equals(Filtrate.ValueCheck(conditionsplit2[k])) && "02".equals(Filtrate.ValueCheck1(conditionsplit2[k]))) {
							sbSerch.append(" and exists (select 1 from FEMProductRelaCondition where searchcode in (" + conditionsplit[j] + ") and riskcode = e.productID) ");
							break;
						} else {
							sbSerch.append("and exists (select 1 from FEMProductRelaCondition where searchcode ='" + conditionsplit2[k] + "' and riskcode = e.productID)  ");
						}
					}
				} else {
					sbSerch.append(" and exists (select 1 from FEMProductRelaCondition where searchcode='" + conditionsplit[j] + "' and riskcode = e.productID)  ");
				}
			}
		}
		sbSerch.append(" order by a.topflag desc ,a.orderflag desc");

		return sbSerch.toString();

	}

	/**
	 * 新改版筛选
	 * @return
	 */
	public String searchRe() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		String CpsUserId = request.getParameter("CpsUserId");
		// 拆封条件，去查询产品中心数据库，获取产品编码
		String channelSn = "wj";// 渠道编码，默认wj
		// 如果有cps用户的coolie信息，那么渠道编码为cps
		if (StringUtil.isNotEmpty(CpsUserId)) {
			channelSn = "cps";
		}
		String condtion1 = "";
		String a = ProductType;// 将原始数据保存
		String b = ChiProductType;

		// 拆封条件，去查询产品中心数据库，获取产品编码
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "0");
		
		Member member = getLoginMember();
		String memberId = "";
		if (member != null) {
			memberId = member.getId();
		}
		
		try {
			ProductType = "B";
			condtion = condtion.substring(1);
			String[] str3 = condtion.split("\\|");
			for (int j = 0; j < str3.length; j++) {
				if (str3[j].indexOf(",") != -1) {
					String[] str4 = str3[j].split(",");
					for (int k = 0; k < str4.length; k++) {
						if ("N".equals(Filtrate.ValueCheck(str4[k])) && "01".equals(Filtrate.ValueCheck1(str4[k]))) {
							str3[j] = str4[k + 1];
							break;
						}
					}
				}
			}
			for (int j = 0; j < str3.length; j++) {
				condtion1 += str3[j];
				if (j != str3.length - 1) {
					condtion1 += "|";
				}
			}
			String catalogidSql = "select id from zccatalog where productType=?";
			DataTable catalogDt = new QueryBuilder(catalogidSql, b).executeDataTable();
			String catalogId = "";
			if (catalogDt.getRowCount() > 0) {
				catalogId = catalogDt.getString(0, 0);
			}
			condtion = condtion1;
			String[] conditionsplit = condtion.split("\\|");
			String sbSerch = getSearchSQL(catalogId, conditionsplit);
			QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());
			DataTable dt0 = qb0.executeDataTable();
			if (dt0 == null || dt0.getRowCount() <= 0) {
				catalogidSql = "select id from zccatalog where productType=?";
				catalogDt = new QueryBuilder(catalogidSql, a).executeDataTable();
				if (catalogDt.getRowCount() > 0) {
					catalogId = catalogDt.getString(0, 0);
				}
				sbSerch = getSearchSQL(catalogId, conditionsplit);
				qb0 = new QueryBuilder(sbSerch.toString());
				dt0 = qb0.executeDataTable();
			}
			String tFlag = "";
			if (dt0.getRowCount() > 0) {
				setSession("dt0", dt0);
			} else {
				if (getSession("dt0") != null) {
					dt0 = (DataTable) getSession("dt0");
					tFlag = "02";
				}
			}
			int onePageCount = 10;// 每个页面显示的产品数量，根据列表页不同而不同
			
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
						Map<String, String> map = searchProductListAvtivityRe(productid, channelSn, discountRate);
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
						total++;
						if (total > start + onePageCount) {
							continue;
						}
						
						if (total <= start) {
							continue;
						}

						// 销售数量
						String tSalesVolume = "0";
						tSalesVolume = String.valueOf(SalesVolumeAction.dealSalvesVolumn(productid));

						// 如果大于1万，以“万”为单位显示
						if (StringUtil.isNotEmpty(tSalesVolume) && !"0".equals(tSalesVolume)) {
							double dSalesVolume = Double.valueOf(tSalesVolume);
							if (dSalesVolume >= 10000) {
								BigDecimal bigDecimal = new BigDecimal(dSalesVolume/10000);
								tSalesVolume = String.valueOf(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)) + "万";
							}
						}

						sb1.append("<div class=\"nlist_con cf\"><div class=\"nlist_des\">");
						sb1.append("<div class=\"icon_C"+dt.get(i).getString("SupplierCode2")+" m-list-logo\"></div>");
						
						sb1.append("<div class=\"m-list-cn\"><a href=\""+url+"\" target=\"_blank\" class=\"nlist_title\">");
						sb1.append(dt.get(i).getString("ProductName") + "</a><div class=\"shop_tj_shrq cf\"><p class=\"shop_shrd_con\">");
						sb1.append("<span class=\"shop_shrq_bg\">适合人群</span>" + dt.get(i).getString("AdaptPeopleInfoListV3") + "</p></div></div>");
						sb1.append("<div class=\"clear\"></div>" + dt.get(i).getString("FEMRiskBrightSpot"));
						sb1.append("<ul class=\"recommend_list\">"+ dt.get(i).getString("DutyHTMLV3") + "</ul>");
						sb1.append("<div class=\"price_tj\"><span class=\"recom_xl\" id=\"SalesV_"+productid+"\">"+tSalesVolume+"人已投保</span>");
						sb1.append("<span class=\"recom_xl\"><i><a href=\"javascript:void(0)\" onclick=\"openCommnet('"+url+"')\" ");
						sb1.append("class=\"shop_tj_num\" id=\"CommentV_"+productid+"\"><i>"+SearchAPI.getCommentCount(productid)+"</i></a></i>人已评价</span>");
						String complateDate = dt.get(i).getString("ComplateDate");
						if ("1".equals(complateDate)) {
							sb1.append("<span class=\"recom_xl\">拍拍速赔</span>");
						}
						sb1.append("</div></div><div class=\"nlist_price\">" + diyactivity + "<span class=\"nlist_pay moneys\" name=\"Ajax_Prict_"+productid+"\">");
						sb1.append("<em>￥</em>"+newPrice+"</span><em class=\"nlist_pay_t moneys\"><span style=\"display:none\" name=\"Clear_Ajax_Prict_"+productid+"\">");
						sb1.append(oPrice+"</span></em><div class=\"remcon_desmore\"><a target=\"_blank\" href=\""+url+"\" rel=\"nofollow\">去看看</a>");
						sb1.append("<label class=\"nlist_add_db\" onclick=\"showcp('"+dt.get(i).getString("ProductName"));
						sb1.append("','"+dt.get(i).getString("logo")+"','"+productid+"','"+Config.getServerContext()+"','"+ProductType);
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
						boolean flag = true;
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
						Map<String, String> map = searchProductListAvtivityRe(productid, "wj", discountRate);
						String diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\">";
						if (map == null || map.size() == 0) {
							diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\" style=\"display: none;\">";
							diyactivity += "</span><em id=\"Diy_Activity_"+productid+"\" style=\"display: none;\"></em></p>";
						} else {
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

						// 销售数量
						String tSalesVolume = "0";
						tSalesVolume = String.valueOf(SalesVolumeAction.dealSalvesVolumn(productid));

						// 如果大于1万，以“万”为单位显示
						if (StringUtil.isNotEmpty(tSalesVolume) && !"0".equals(tSalesVolume)) {
							double dSalesVolume = Double.valueOf(tSalesVolume);
							if (dSalesVolume >= 10000) {
								BigDecimal bigDecimal = new BigDecimal(dSalesVolume/10000);
								tSalesVolume = String.valueOf(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)) + "万";
							}
						}

						sb1.append("<div class=\"nlist_con cf\"><div class=\"nlist_des\">");
						sb1.append("<div class=\"icon_C"+dt.get(i).getString("SupplierCode2")+" m-list-logo\"></div>");
						
						sb1.append("<div class=\"m-list-cn\"><a href=\""+url+"\" target=\"_blank\"  class=\"nlist_title\">");
						sb1.append(dt.get(i).getString("ProductName") + "</a><div class=\"shop_tj_shrq cf\"><p class=\"shop_shrd_con\">");
						sb1.append("<span class=\"shop_shrq_bg\">适合人群</span>" + dt.get(i).getString("AdaptPeopleInfoListV3") + "</p></div></div>");
						sb1.append("<div class=\"clear\"></div>" + dt.get(i).getString("FEMRiskBrightSpot"));
						sb1.append("<ul class=\"recommend_list\">"+ dt.get(i).getString("DutyHTMLV3") + "</ul>");
						sb1.append("<div class=\"price_tj\"><span class=\"recom_xl\" id=\"SalesV_"+productid+"\">"+tSalesVolume+"人已投保</span>");
						sb1.append("<span class=\"recom_xl\"><i><a href=\"javascript:void(0)\" onclick=\"openCommnet('"+url+"')\" ");
						sb1.append("class=\"shop_tj_num\" id=\"CommentV_"+productid+"\"><i>"+SearchAPI.getCommentCount(productid)+"</i></a></i>人已评价</span>");
						String complateDate = dt.get(i).getString("ComplateDate");
						if ("1".equals(complateDate)) {
							sb1.append("<span class=\"recom_xl\">拍拍速赔</span>");
						}
						sb1.append("</div></div><div class=\"nlist_price\">" + diyactivity + "<span class=\"nlist_pay moneys\" name=\"Ajax_Prict_"+productid+"\">");
						sb1.append("<em>￥</em>"+newPrice+"</span><em class=\"nlist_pay_t moneys\"><span style=\"display:none\" name=\"Clear_Ajax_Prict_"+productid+"\">");
						sb1.append(oPrice+"</span></em><div class=\"remcon_desmore\"><a target=\"_blank\" href=\""+url+"\" rel=\"nofollow\">去看看</a>");
						sb1.append("<label class=\"nlist_add_db\" onclick=\"showcp('"+dt.get(i).getString("ProductName"));
						sb1.append("','"+dt.get(i).getString("logo")+"','"+productid+"','"+Config.getServerContext()+"','"+ProductType);
						sb1.append("','"+dt.get(i).getString("LogoLink")+"','"+dt.get(i).getString("InitPrem")+"');\">加入对比</label></div></div></div>");
						
					}
				}
				
				
				int pageCount = new Double(Math.ceil(total * 1.0 / onePageCount)).intValue();
				// 分页
				sb2.append("<div id='pagination'><input type='hidden' id='listProductCount' value='"+total+"'/><ul>");
				
				if (pageIndex > 0) {
					sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageIndex + "\");'><span>上一页</span></a></li>");
					sb2.append("<li><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
				} else {
					sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span class='default'>上一页</span></a></li>");
					sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
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
					sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doOrder2(\"" + nextPage + "\");'><span>下一页</span></a></li>");
				}
				
				sb2.append("</ul></div>");
				
				if ("02".equals(tFlag)||total==0) {
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

	public String search() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		String condtion1 = "";
		String a = ProductType;// 将原始数据保存
		String b = ChiProductType;

		// 拆封条件，去查询产品中心数据库，获取产品编码
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "0");
		
		Member member = getLoginMember();
		String memberId = "";
		if (member != null) {
			memberId = member.getId();
		}
		try {
			ProductType = ProductType.substring(0, 1);
			condtion = condtion.substring(1);
			String[] str3 = condtion.split("\\|");
			for (int j = 0; j < str3.length; j++) {
				if (str3[j].indexOf(",") != -1) {
					String[] str4 = str3[j].split(",");
					for (int k = 0; k < str4.length; k++) {
						if ("N".equals(Filtrate.ValueCheck(str4[k])) && "01".equals(Filtrate.ValueCheck1(str4[k]))) {
							str3[j] = str4[k + 1];
							break;
						}
					}
				}
			}
			for (int j = 0; j < str3.length; j++) {
				condtion1 += str3[j];
				if (j != str3.length - 1) {
					condtion1 += "|";
				}
			}
			String catalogidSql = "select id from zccatalog where productType=?";
			DataTable catalogDt = new QueryBuilder(catalogidSql, b).executeDataTable();
			String catalogId = "";
			if (catalogDt.getRowCount() > 0) {
				catalogId = catalogDt.getString(0, 0);
			}
			condtion = condtion1;
			String[] conditionsplit = condtion.split("\\|");
			String sbSerch = getSearchSQL(catalogId, conditionsplit);
			QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());
			DataTable dt0 = qb0.executeDataTable();
			if (dt0 == null || dt0.getRowCount() <= 0) {
				catalogidSql = "select id from zccatalog where productType=?";
				catalogDt = new QueryBuilder(catalogidSql, a).executeDataTable();
				if (catalogDt.getRowCount() > 0) {
					catalogId = catalogDt.getString(0, 0);
				}
				sbSerch = getSearchSQL(catalogId, conditionsplit);
				qb0 = new QueryBuilder(sbSerch.toString());
				dt0 = qb0.executeDataTable();
			}
			String tFlag = "";
			if (dt0.getRowCount() > 0) {
				setSession("dt0", dt0);
			} else {
				if (getSession("dt0") != null) {
					dt0 = (DataTable) getSession("dt0");
					tFlag = "02";
				}
			}
			int onePageCount = 10;// 每个页面显示的产品数量，根据列表页不同而不同
			if ("ltblist".equals(searchProductFlag)) {
				onePageCount = 15;
			} else if ("listv3".equals(searchProductFlag)) {
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
					;
					if ("ltblist".equals(searchProductFlag)) {
						sb1.append("<dl class=\"shop_img_con\">");
						// 活动
						sb1.append("<dt class=\"shop_img_head\">");
						// 是否有满减活动
						String productid = dt.get(i).getString("ProductID");
						Map<String, String> map = searchProductListAvtivity(productid, "wj");
						if (map == null || map.size() == 0) {
							sb1.append("" + dt.get(i).getString("ProductActive") + "");
						} else {
							for (String key : map.keySet()) {
								String value = map.get(key);
								String type = key.substring(key.indexOf("@") + 1, key.length());
								if (!"8".equals(type)) {// 非自定义活动
									sb1.append(value);
									break;
								}
							}
						}
						// title
						sb1.append("<a rel=\"nofollow\" target=\"_blank\" href=\"" + url + "\"><img width=\"190\" height=\"190\" alt=\"" + dt.get(i).getString("Title") + "\" src=\""
								+ dt.get(i).getString("LogoLink") + "\" class=\"lazy\" data-original=\"" + dt.get(i).getString("LogoLink") + "\" style=\"display: inline;\"></a>");
						sb1.append("<div class=\"shop_box_des\" style=\"display:none;\">");
						sb1.append("<div class=\"shop_box_l\"><span></span></div>");
						sb1.append("<a href=\"" + url + "\" target=\"_blank\" rel=\"nofollow\" class=\"shop_pos_a\"></a>");
						sb1.append("<div class=\"shop_box_con\"><div class=\"shop_xx\"> <b>适合人群</b>");
						sb1.append("<p class=\"shop_product\">" + dt.get(i).getString("AdaptPeopleInfo") + "</p><b>产品特色</b>");
						sb1.append("<ul class=\"shop_desafs\">" + dt.get(i).getString("FEMRiskBrightSpotNew") + "</ul>");
						sb1.append("</div></div>");
						sb1.append("<div class=\"shop_box_con2\"> <div class=\"shop_xx\">");
						sb1.append("<b>保险责任</b><ul class=\"recommend_list\">");
						sb1.append("" + dt.get(i).getString("DutyHTML2") + "</ul>");
						sb1.append("</div></div>");
						sb1.append("<div class=\"shop_box_r\"><span class=\"shop_btns_r\"></span> </div>");
						sb1.append("</div>");
						sb1.append("</dt>");
						sb1.append("<dd class=\"shop_img_titile\"><a target=\"_blank\" href=\"" + url + "\">" + dt.get(i).getString("ProductName") + "</a></dd>");
						sb1.append("<dd class=\"img_price_con\"><b class=\"moneys img_box_price\">￥" + dt.get(i).getString("InitPrem") + "</b>" + dt.get(i).getString("BasePrem") + "</dd>");
						sb1.append("<dd class=\"shop_operating\">");
						sb1.append("<span class=\"shop_sales\">销量：<i class=\"img_xl_num\">" + dt.get(i).getString("SalesVolume") + "</i></span>");
						sb1.append("<span class=\"add_collect\"  onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','"
								+ dt.get(i).getString("ProductId") + "','" + Config.getServerContext() + "','" + ProductType + "','" + dt.get(i).getString("LogoLink") + "','"
								+ dt.get(i).getString("InitPrem") + "');\"  title=\"加入对比\"></span>");
						sb1.append("<span id=\"contrast_" + dt.get(i).getString("ProductId") + "\" onclick=\"submitp('" + dt.get(i).getString("ProductId")
								+ "');\"  class=\"add_contrast\" title=\"加入收藏\"></span>");
						sb1.append("</dd>");
						sb1.append("</dl>");
					} else if ("listv3".equals(searchProductFlag)) {
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
						if (Double.parseDouble(newPrice) >= Double.parseDouble(oPrice)) {
							DataTable dt3 = new QueryBuilder(
									" SELECT z2.TextValue FROM zcarticle z1,zdcolumnvalue z2 WHERE z1.id = z2.relaid AND z1.prop4 = ? AND ColumnCode = 'DiscountRate' LIMIT 1 ", riskcode)
									.executeDataTable();
							String discountRate = "";
							if (dt3 != null && dt3.getRowCount() >= 1) {
								if (StringUtil.isNotEmpty(dt3.getString(0, "TextValue"))) {
									discountRate = dt3.getString(0, "TextValue");
								}
							}
							if (StringUtil.isNotEmpty(discountRate)) {
								oPrice = dt.get(i).getString("BasePremV3");
							} else {
								oPrice = "";
							}
						} else {
							oPrice = "<em>￥</em>" + oPrice;
						}
						sb1.append("<div class=\"nlist_con cf\">");
						// 活动
						sb1.append("<div class=\"shop_nlist_img\">");
						// 是否有满减活动
						String productid = dt.get(i).getString("ProductID");
						Map<String, String> map = searchProductListAvtivity(productid, "wj");
						String diyactivity = "";
						if (map == null || map.size() == 0) {
							sb1.append("" + dt.get(i).getString("ProductActive") + "");
						} else {
							for (String key : map.keySet()) {
								String value = map.get(key);
								String type = key.substring(key.indexOf("@") + 1, key.length());
								if (!"8".equals(type)) {// 非自定义活动
									sb1.append(value);
									break;
								} else {
									String title = value.substring(0, value.indexOf("@"));
									String description = value.substring(value.indexOf("@") + 1, value.length());
									if (description.length() > 20) {
										description = description.substring(0, 20);
									}
									diyactivity = "<em class=\"list_hddes\"  id=\"Diy_em_Activity_" + productid + "\" > <i class=\"list_hd_t\" id=\"Diy_Title_Activity_" + productid + "\">" + title
											+ "</i><span id=\"Diy_Activity_" + productid + "\" >" + description + "</span></em>";
								}
							}
						}
						// title
						sb1.append("<a rel=\"nofollow\" target=\"_blank\" href=\"" + url + "\"><img width=\"190\" height=\"190\" alt=\"" + dt.get(i).getString("ProductName") + "\" src=\""
								+ dt.get(i).getString("LogoLink") + "\" class=\"lazy\" data-original=\"" + dt.get(i).getString("LogoLink") + "\" style=\"display: inline;\"></a>");
						sb1.append("</div>");
						sb1.append("<div class=\"nlist_des\">");
						sb1.append("<a href=\"" + url + "\" target=\"_blank\" rel=\"nofollow\" class=\"nlist_title\">" + dt.get(i).getString("ProductName") + "</a>");
						sb1.append(diyactivity);
						// 适合人群
						sb1.append("<div class=\"shop_tj_shrq cf\"> <span class=\"shop_shrq_bg\">适合人群</span>");
						sb1.append("<p class=\"shop_shrd_con\">" + dt.get(i).getString("AdaptPeopleInfoListV3") + "</p></div>");
						sb1.append("<div class=\"clear\"></div>");
						// 保险责任
						sb1.append("<ul class=\"recommend_list\">");
						sb1.append("" + dt.get(i).getString("DutyHTMLV3") + "</ul>");
						// 价格
						sb1.append("<div class=\"nlist_price\">");
						sb1.append("<span class=\"nlist_pay moneys\" name=\"Ajax_Prict_" + dt.get(i).getString("ProductID") + "\">￥" + newPrice
								+ "</span><em class='nlist_pay_t moneys'><span name=\"Clear_Ajax_Prict_" + dt.get(i).getString("ProductID") + "\">" + oPrice + "</span></em>");
						sb1.append("<div class=\"price_tj\">");
						sb1.append("<span class=\"recom_xl\">已有" + SalesVolumeAction.dealSalvesVolumn(dt.get(i).getString("ProductID")) + "人投保</span>");
						sb1.append("<span class=\"recom_xl\"><i>（<a class=\"shop_tj_num\" href=\"javascript:void(0)\" onclick=\"openCommnet('" + url + "')\">"
								+ SearchAPI.getCommentCount(dt.get(i).getString("ProductId")) + "</a>） </i> 条评论</span>");
						sb1.append("</div>");

						sb1.append("<span class=\"remcon_desmore\"><label class=\"nlist_add_db\">");
						sb1.append("<input type=\"checkbox\" onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("ProductId")
								+ "','" + Config.getServerContext() + "','" + ProductType + "','" + dt.get(i).getString("LogoLink") + "','" + dt.get(i).getString("InitPrem") + "');\">加入对比</label>");
						sb1.append("<a target=\"_blank\" href=" + url + ">去看看</a></span>");

						sb1.append("</div></div></div>");
					} else {
						sb1.append("<div class=\"product_title\">");
						sb1.append("<span class=\"CInsuranceCompany icon_C" + dt.get(i).getString("SupplierCode2") + "\"  ></span> <span class=\"productName\"> ");
						sb1.append("<a href=\"" + url + "\" target=\"_blank\"><h2 class=\"ziti\">" + dt.get(i).getString("ProductName") + "</h2></a></span>");
						sb1.append("<span class=\"SalesVolume\">(累计销量：" + dt.get(i).getString("SalesVolume") + ")</span>");
						sb1.append("<span style=\"display: none;\" id=\"productIntegral_" + dt.get(i).getString("ProductId") + "}\"></span>");
						sb1.append("</div><div class=\"product_condition\">");
						sb1.append(dt.get(i).getString("CalHTML2"));
						sb1.append("</div><div class=\"product_info\"><div class=\"product_info_bor\"><div class=\"prodcutMark\">");
						sb1.append("<ul class=\"price\" >" + dt.get(i).getString("prodcutMarkPrice") + "</ul><ul class=\"btn\">");
						sb1.append("<li class=\"btn1\"><span onclick=\"chakan('" + url + "')\">查看详情</span></li>");
						sb1.append("<li class=\"btn2\"><span onclick=\"submitp('" + dt.get(i).getString("ProductId") + "');\">加入收藏</span><span class=\"add_cp_list\" style=\"margin-left: 6px;\"");
						sb1.append("onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("ProductId") + "','"
								+ Config.getServerContext() + "','" + ProductType + "','" + dt.get(i).getString("LogoLink") + "','" + dt.get(i).getString("InitPrem") + "');\">加入对比</span></li>");
						sb1.append("</ul></div></div>");
						sb1.append("<div class=\"AdaptPeopleInfo\">" + dt.get(i).getString("AdaptPeopleInfo") + "</div>");
						sb1.append("<div class=\"productFeature\">" + dt.get(i).getString("FEMRiskBrightSpot") + "</div>");
						sb1.append(dt.get(i).getString("DutyHTML"));
						sb1.append("</div>");
					}

				}

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
				for (j = 1; j <= (total % onePageCount == 0 ? total / onePageCount : (total / onePageCount + 1)); j++) {
					if (j == (pageIndex + 1)) {
						sb2.append("<span class='plpage04'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><font color = '#FFFFFF'> " + j + "</font></a></span>&nbsp;");
					}
					// 如果总页面大于5
					else if (pageCount > 5) {
						if (pageIndex > 3 && pageCount > (pageIndex + 1)) {
							if (j > (pageIndex - 3) && j < (pageIndex + 3)) {
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'> " + j + " </a></span>&nbsp;");
							}
							if (j == (pageIndex + 2) && j != pageCount) {
								sb2.append("...&nbsp;");
							} else if (j == (pageCount - 1) && (pageIndex + 1) == (pageCount - 2)) {
								sb2.append("...&nbsp;");
							}
						}
						if (pageIndex > 3 && pageCount < (pageIndex + 2)) {
							if (j > pageCount - 5) {
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>" + j + "</a></span>&nbsp;");
							}
						}
						if (pageIndex < 4) {
							if (j < 6) {
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>" + j + "</a></span>&nbsp;");
								if (j == 5) {
									sb2.append("...&nbsp;");
								}
							}
						}

					}
					// 如果总页面小于5 则全部显示
					else if (pageCount < 6) {
						sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>" + j + "</a></span>&nbsp;");
					}
				}
				if (pageIndex + 1 != pageCount && pageCount > 0) {
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + nextPage + "\");'>></a></span>");
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>尾页</a></span>");
				} else {
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>></a></span>");
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>尾页</a></span>");
				}

				sb2.append("&nbsp;&nbsp;共" + (total % onePageCount == 0 ? total / onePageCount : (total / onePageCount + 1)) + "页&nbsp;&nbsp;");
				sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
				sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

				sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>"
						+ pageCount
						+ "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){newJump();}else{newJump();}}\" style='' value='跳转'></td>");

				sb2.append("</div>");
				sb2.append("</div>");

				if ("02".equals(tFlag)) {
					jsonMap.put("status", "2");
				} else {
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

	/**
	 * 得到前台AJAX传递的字符串，并返回根据拆分后的字符得到的产品拼装后的HTML代码块
	 * 
	 * @return
	 */
	public String searchBXSC() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String CpsUserId = request.getParameter("CpsUserId");
		String condtion1 = "";
		// 拆封条件，去查询产品中心数据库，获取产品编码
		String channelSn = "wj";// 渠道编码，默认wj
		// 如果有cps用户的coolie信息，那么渠道编码为cps
		if (StringUtil.isNotEmpty(CpsUserId)) {
			channelSn = "cps";
		}
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "0");
		try {
			if ("Z".equals(ProductType.substring(0, 1))) {
				ProductType = ProductType.substring(1, 2);
			} else {
				ProductType = ProductType.substring(0, 1);
			}
			condtion = condtion.substring(1);
			String[] str3 = condtion.split("\\|");
			for (int j = 0; j < str3.length; j++) {
				if (str3[j].indexOf(",") != -1) {
					String[] str4 = str3[j].split(",");
					for (int k = 0; k < str4.length; k++) {
						if ("N".equals(Filtrate.ValueCheck(str4[k])) && "01".equals(Filtrate.ValueCheck1(str4[k]))) {
							str3[j] = str4[k + 1];
							break;
						}
					}
				}
			}
			for (int j = 0; j < str3.length; j++) {
				condtion1 += str3[j];
				if (j != str3.length - 1) {
					condtion1 += "|";
				}
			}
			condtion = condtion1;
			String[] conditionsplit = condtion.split("\\|");
			String sql0 = "select a.productID from sdproduct a where 1=1 ";
			boolean balanceFlag = false;
			for (int j = 0; j < conditionsplit.length; j++) {
				if ((conditionsplit[j]).startsWith("default_")) {
					continue;
				} else {
					balanceFlag = true;
					if (conditionsplit[j].indexOf(",") != 0) {
						String[] conditionsplit2 = conditionsplit[j].split(",");
						for (int k = 0; k < conditionsplit2.length; k++) {
							if ("Y".equals(Filtrate.ValueCheck(conditionsplit2[k])) && "02".equals(Filtrate.ValueCheck1(conditionsplit2[k]))) {
								sql0 += " and exists (select 1 from FEMProductRelaCondition where searchcode in (" + conditionsplit[j] + ") and riskcode = a.productID )";
								break;
							} else {
								sql0 += " and exists (select 1 from FEMProductRelaCondition where searchcode ='" + conditionsplit2[k] + "' and riskcode = a.productID )";
							}
						}
					} else {
						sql0 += " and exists (select 1 from FEMProductRelaCondition where searchcode='" + conditionsplit[j] + "' and riskcode = a.productID )";
					}
				}
			}
			if (!balanceFlag) {
				sql0 += " and a.productType = '" + ProductType + "'";
			}
			QueryBuilder qb0 = new QueryBuilder(sql0);
			DataTable dt0 = qb0.executeDataTable();
			if (dt0.getRowCount() > 0) {
				String productIDs = "";
				for (int i = 0; i < dt0.getRowCount(); i++) {
					productIDs += dt0.get(i).getString("productID");
					if (i != dt0.getRowCount() - 1) {
						productIDs += ",";
					}
				}
				String sql = "select * from SDSearchRelaProduct where productId in (" + productIDs + ") ";
				QueryBuilder qb1 = new QueryBuilder(sql);
				DataTable dt1 = qb1.executeDataTable();
				if (dt1.getRowCount() > 0) {
					setSession("dt1", dt1);
				} else {
					if (getSession("dt1") != null) {
						dt1 = (DataTable) getSession("dt1");
					}
				}
				DataTable dt = new DataTable();
				if (pageIndex != 0) {
					pageIndex = pageIndex - 1;
				}
				int nextPage = pageIndex + 2;
				int start = pageIndex * 10;
				List<DataRow> alist = new ArrayList<DataRow>();
				int total = dt1.getRowCount();
				int pageCount = new Double(Math.ceil(total * 1.0 / 10)).intValue();
				for (int i = 0; i < total; i++) {
					alist.add(dt1.get(i));
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
				String urlSql = "select prop3 from ZCSite where id='221'";
				DataTable dtUrl = new QueryBuilder(urlSql).executeDataTable();
				for (int i = start; i < start + 10 && i < dt.getRowCount(); i++) {
					String url = "";
					if (dtUrl.getRowCount() > 0) {
						url = dtUrl.getString(0, 0);
					}
					String productId = dt.get(i).getString("ProductId");
					url = url + "productId=" + productId;
					
					// 是否有满减活动
					String productid = dt.get(i).getString("ProductID");
					String discountRate = "";
					DataTable dt3 = new QueryBuilder(
							" select BackUp2 from femriskb where RiskCode=? and IsPublish='Y'  ", productid)
							.executeDataTable();
					
					if (dt3 != null && dt3.getRowCount() >= 1) {
						if (StringUtil.isNotEmpty(dt3.getString(0, "BackUp2"))) {
							discountRate = dt3.getString(0, "BackUp2");
						}
					}
					Map<String, String> map = searchProductListAvtivityRe(productid, "wj", discountRate);
					String diyactivity = "<p class=\"list_Sale tribe-action\"><span id=\"Diy_Title_Activity_"+productid+"\">";
					if (map == null || map.size() == 0) {
						// 筛选促销产品，没有参加活动的产品不显示
						if ("Y".equals(activityFlag)) {
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
					
					sb1.append("<div class=\"nlist_con cf\"><div class=\"nlist_des\">");
					sb1.append("<div class=\"icon_C"+dt.get(i).getString("SupplierCode2")+" m-list-logo\"></div>");
					
					sb1.append("<div class=\"m-list-cn\"><a href='###' onclick=\"chakan('" + url + "')\" rel=\"nofollow\" class=\"nlist_title\">");
					sb1.append(dt.get(i).getString("ProductName") + "</a><div class=\"shop_tj_shrq cf\"><p class=\"shop_shrd_con\">");
					sb1.append("<span class=\"shop_shrq_bg\">适合人群</span>" + dt.get(i).getString("AdaptPeopleInfoListV3") + "</p></div></div>");
					sb1.append("<div class=\"clear\"></div><ul class=\"recommend_list\">"+ dt.get(i).getString("DutyHTMLV3") + "</ul>");
					sb1.append("<div class=\"price_tj\"></div></div>");
					sb1.append("<div class=\"nlist_price\">" + diyactivity + "<span class=\"nlist_pay moneys\" name=\"Ajax_Prict_"+productid+"\">");
					if ("wj".equals(channelSn)) {
						sb1.append("<em>￥</em>" + dt.get(i).getString("InitPrem") + "</span>");
					} else {
						sb1.append("<em>￥</em>" + dt.get(i).getString("InitPremCPS") + "</span>");
					}
					sb1.append("<em class=\"nlist_pay_t moneys\"><span name=\"Clear_Ajax_Prict_"+productid+"\">");
					if (StringUtil.isNotEmpty(dt.get(i).getString("BasePremV3"))) {
						sb1.append("原价");
					}
					sb1.append(dt.get(i).getString("BasePremV3")+"</span></em><div class=\"remcon_desmore\">");
					sb1.append("<a href='###' onclick=\"chakan('" + url + "')\" rel=\"nofollow\">去看看</a></div></div></div>");
				}
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
				for (j = 1; j <= (total % 10 == 0 ? total / 10 : (total / 10 + 1)); j++) {
					if (j == (pageIndex + 1)) {
						sb2.append("<span class='plpage04'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><font color = '#FFFFFF'> " + j + "</font></a></span>&nbsp;");
					}
					// 如果总页面大于5
					else if (pageCount > 5) {
						if (pageIndex > 3 && pageCount > (pageIndex + 1)) {
							if (j > (pageIndex - 3) && j < (pageIndex + 3)) {
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'> " + j + " </a></span>&nbsp;");
							}
							if (j == (pageIndex + 2) && j != pageCount) {
								sb2.append("...&nbsp;");
							} else if (j == (pageCount - 1) && (pageIndex + 1) == (pageCount - 2)) {
								sb2.append("...&nbsp;");
							}
						}
						if (pageIndex > 3 && pageCount < (pageIndex + 2)) {
							if (j > pageCount - 5) {
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>" + j + "</a></span>&nbsp;");
							}
						}
						if (pageIndex < 4) {
							if (j < 6) {
								sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>" + j + "</a></span>&nbsp;");
								if (j == 5) {
									sb2.append("...&nbsp;");
								}
							}
						}

					}
					// 如果总页面小于5 则全部显示
					else if (pageCount < 6) {
						sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'>" + j + "</a></span>&nbsp;");
					}
				}
				if (pageIndex + 1 != pageCount && pageCount > 0) {
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + nextPage + "\");'>></a></span>");
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>尾页</a></span>");
				} else {
					sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>></a></span>");
					sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'>尾页</a></span>");
				}

				sb2.append("&nbsp;&nbsp;共" + (total % 10 == 0 ? total / 10 : (total / 10 + 1)) + "页&nbsp;&nbsp;");
				sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
				sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

				sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>"
						+ pageCount
						+ "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){newJump();}else{newJump();}}\" style='' value='跳转'></td>");

				sb2.append("</div>");
				sb2.append("</div>");

				jsonMap.put("status", "1");
				if (dt.getRowCount() != 0) {
					jsonMap.put("sb1", sb1.toString());
					jsonMap.put("sb3", sb2.toString());
				}

			}
		} catch (Exception e) {
			jsonMap.put("status", "0");
			logger.error("搜索排序方法出现异常：" + e.getMessage(), e);
		}
		return ajaxJson(jsonMap);
	}

	/**
	 * 根据险种查询热销产品.
	 * 
	 * @return
	 */
	public String ajaxGetHotRecommendProduct() {
		DataTable dt = new DataTable();

		String memberId = "";
		Member member = getLoginMember();
		if (member != null) {
			memberId = member.getId();
		}
		JSONObject jsonObject = new JSONObject();
		try {
			QueryBuilder qb = new QueryBuilder("select innercode from zccatalog where producttype = ?", ProductType);
			StringBuffer result = new StringBuffer();
			String innercode = (String) qb.executeOneValue();

			qb = new QueryBuilder(
					"select a.title,b.LogoLink,a.URL,b.InitPrem,b.BasePremV3,b.BasePremValue,b.ProductID "
							+ "from zcarticle a,sdsearchrelaproduct b where b.ProductID=a.prop4 and a.attribute like '%hot%' and a.status = '30' and a.cataloginnercode = ? order by topflag desc,orderflag desc,publishdate desc, id desc limit 5",
					innercode);

			dt = qb.executeDataTable();

			AjaxPriceAction ap = new AjaxPriceAction();

			if (dt != null && dt.getRowCount() > 0) {
				for (int i = 0; i < dt.getRowCount(); i++) {

					String newPrice = ap.queryAjaxPrice(dt.get(i).getString("ProductId"), memberId);
					String basePremValue = dt.get(i).getString("BasePremValue");

					String v3 = "";
					if (StringUtil.isNotEmpty(basePremValue)) {
						if (Double.parseDouble(basePremValue) > Double.parseDouble(newPrice)) {
							v3 = "￥" + basePremValue;
						}
					} else {
						v3 = dt.get(i).getString("BasePremV3");
					}

					result.append(" <li class=\"usershop_list\">");
					result.append(" <h4><a href=\"" + Config.getFrontServerContextPath() + "/" + dt.get(i).getString("URL") + "\" target=\"_blank\"><img width=\"70\" height=\"70\" alt=\""
							+ dt.get(i).getString("title") + "\" src=\"" + dt.get(i).getString("LogoLink") + "\"></a></h4>");
					result.append(" <div class=\"user_shop\"><a href=\"" + Config.getFrontServerContextPath() + "/" + dt.get(i).getString("URL") + "\" target=\"_blank\">"
							+ dt.get(i).getString("title") + "</a>");
					result.append(" <span class=\"red shop_m moneys\">￥" + newPrice + "<span class=\"moneys price_ymoneys\">" + v3 + "</span></span>");
					result.append(" </div></li>");
				}
			}
			qb = new QueryBuilder("select CodeName from zdcode where CodeType='ProductType' and CodeValue=?", ProductType);

			Map<String, Object> price = new HashMap<String, Object>();
			price.put("products", result.toString());
			price.put("productTypeName", qb.executeString().trim());
			jsonObject = JSONObject.fromObject(price);

		} catch (Exception e) {
			logger.error("查询推荐产品异常：" + e.getMessage(), e);
		}

		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 根据险种查询推荐产品.
	 * 
	 * @return
	 */
	public String ajaxGetRecommendProduct() {

		DataTable dt = new DataTable();
		String memberId = "";
		Member member = getLoginMember();
		if (member != null) {
			memberId = member.getId();
		}
		JSONObject jsonObject = new JSONObject();
		try {
			QueryBuilder qb = new QueryBuilder("select innercode from zccatalog where producttype = ?", ProductType);
			StringBuffer result = new StringBuffer();
			String innercode = (String) qb.executeOneValue();

			qb = new QueryBuilder(
					"select a.ID,a.title,b.LogoLink,a.URL,b.RecomDutyV3,b.SupplierCode2,b.AdaptPeopleInfoV3,b.SalesVolume,b.InitPrem,b.BasePremV3,b.ProductID,'' as RecommendImg, '' as RecommendInfo "
							+ "from zcarticle a,sdsearchrelaproduct b where b.ProductID=a.prop4 and a.attribute like '%newRecommend%' and a.status = '30' and a.cataloginnercode = ? order by topflag desc,orderflag desc,publishdate desc, id desc limit 2",
					innercode);

			dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				String sql = "select TextValue from zdcolumnvalue where ColumnCode = ? and RelaID = ?";
				String strOnclick = "onclick=\"javascript:void(0);return(VL_FileDL(this));return false;\" exturl=\"http://www.kaixinbao.com/recommend\" vlpageid=\"recommend\"";
				for (int i = 0; i < dt.getRowCount(); i++) {
					AjaxPriceAction ap = new AjaxPriceAction();
					String newrice = ap.queryAjaxPrice(dt.get(i).getString("ProductID"), memberId);
					String baseprem = dt.get(i).getString("InitPrem");
					if (Double.parseDouble(newrice) >= Double.parseDouble(baseprem)) {
						DataTable dt3 = new QueryBuilder(" SELECT z2.TextValue FROM zcarticle z1,zdcolumnvalue z2 WHERE z1.id = z2.relaid AND z1.prop4 = ? AND ColumnCode = 'DiscountRate' LIMIT 1 ",
								dt.get(i).getString("ProductID")).executeDataTable();
						String discountRate = "";
						if (dt3 != null && dt3.getRowCount() >= 1) {
							if (StringUtil.isNotEmpty(dt3.getString(0, "TextValue"))) {
								discountRate = dt3.getString(0, "TextValue");
							}
						}
						if (StringUtil.isNotEmpty(discountRate)) {
							baseprem = dt.get(i).getString("BasePremV3");
						} else {
							baseprem = "";
						}
					} else {
						baseprem = "￥" + baseprem;
					}
					qb = new QueryBuilder(sql, "RecommendImg", dt.get(i, 0));
					String imgInfo = qb.executeString();
					if (StringUtil.isNotEmpty(imgInfo) && imgInfo.indexOf("nopicture.jpg") == -1) {
						dt.set(i, "RecommendImg", imgInfo);
					} else {
						dt.set(i, "RecommendImg", "");
					}

					qb = new QueryBuilder(sql, "RecommendInfo", dt.get(i, 0));
					dt.set(i, "RecommendInfo", qb.executeString());
					if (i + 1 == dt.getRowCount()) {
						result.append(" <div class=\"recommend_con floatl\">");
					} else {
						result.append(" <div class=\"recommend_con floatl bor_leftsf\">");
					}
					result.append(" <div class=\"hot_tj_img\">");
					if (StringUtil.isNotEmpty(dt.get(i).getString("RecommendImg")) && !dt.get(i).getString("RecommendImg").endsWith("/")) {
						result.append("<img src=\"" + Config.getValue("StaticResourcePath") + dt.get(i).getString("RecommendImg") + "\" width=\"74px\" height=\"74px\" alt=\"\" />");
					}
					result.append(" </div><div class=\"recommend_box \"> ");
					result.append(" <div class=\"recommend_top cf\"> ");
					result.append(" <span class=\"list_zd_img floatl\" > ");
					result.append("  <a class=\"cp_tj_logo_a\" href=\"" + Config.getFrontServerContextPath() + "/" + dt.get(i).getString("URL") + "?link_id=recom\" target=\"_blank\" " + strOnclick
							+ "><div class=\"icon_C" + dt.get(i).getString("SupplierCode2") + " tj_cp_logos\"></div></a> </span>");
					result.append(" <div class=\"floatl shop_sfs \"> ");
					result.append(" <span class=\"shop_titile\"><a href=\"" + Config.getFrontServerContextPath() + "/" + dt.get(i).getString("URL") + "?link_id=recom\" target=\"_blank\" "
							+ strOnclick + ">" + dt.get(i).getString("title") + "</a></span>");
					result.append(" <p class=\"shop_tj_shrq\"><span class=\"shop_shrq_bg\">推荐理由</span>" + dt.get(i).getString("AdaptPeopleInfoV3") + "</p>");
					result.append(" </div></div>");
					result.append(" <div class=\"recommend_cen cf\">");
					result.append(" <div class=\"tj_cp_dess\"><p>" + dt.get(i).getString("RecommendInfo") + "</p></div>");
					result.append(" <div class=\"recom_price cf\"><div class=\"price_tj\">");
					result.append(" <span class=\"recom_xl\">已有" + dt.get(i).getString("SalesVolume") + "人投保</span>");
					result.append(" <div class=\"clear\" ></div>");
					result.append("<span class=\"recom_xl\"><i>（<a class=\"shop_tj_num\" href=\"javascript:void(0)\" onclick=\"openCommnet('" + Config.getFrontServerContextPath() + "/"
							+ dt.get(i).getString("URL") + "','link_id=recom');return(VL_FileDL(this));return false;\" exturl=\"http://www.kaixinbao.com/recommend\" vlpageid=\"recommend\">"
							+ SearchAPI.getCommentCount(dt.get(i).getString("ProductID")) + "</a>） </i> 条评论</span> </div>");
//					result.append(" <ul class=\"crcom_pric\"><li class=\"new_priceA\"><span>" + newrice + "</span></li><li class=\"new_priceB\"><span >" + baseprem + "</span></li></ul>");
					result.append(" <ul class=\"crcom_pric\"><li class=\"new_priceA\"><span>" + newrice + "</span></li></ul>");
					result.append(" <span class=\"remcon_desmore\"> <a href=\"" + Config.getFrontServerContextPath() + "/" + dt.get(i).getString("URL") + "?link_id=recom\" target=\"_blank\" "
							+ strOnclick + ">去看看>></a></span>");
					result.append(" </div></div></div>");
					result.append(" </div>");
				}
			}

			Map<String, Object> price = new HashMap<String, Object>();
			price.put("products", result.toString());
			jsonObject = JSONObject.fromObject(price);

		} catch (Exception e) {
			logger.error("查询推荐产品异常：" + e.getMessage(), e);
		}

		return ajax(jsonObject.toString(), "text/html");
	}

	public String getCondtion() {
		return condtion;
	}

	public void setCondtion(String condtion) {
		this.condtion = condtion;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getProductsOrder() {
		return ProductsOrder;
	}

	public void setProductsOrder(String productsOrder) {
		ProductsOrder = productsOrder;
	}

	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		ProductType = productType;
	}

	public String getSearchProductFlag() {
		return searchProductFlag;
	}

	public void setSearchProductFlag(String searchProductFlag) {
		this.searchProductFlag = searchProductFlag;
	}

	public String getChiProductType() {
		return ChiProductType;
	}

	public void setChiProductType(String chiProductType) {
		ChiProductType = chiProductType;
	}

	// public static void main(String args[])
	// {
	// ValueCheck("InsuranceCompany");
	// StringBuffer ww1=new StringBuffer();
	// ww1.append("and exists (select 1 from FEMProductRelacondtion where searchcode in (\"");
	// ww1.append(1);
	// ww1.append("\") and riskcode = a.riskcode)");
	// System.out.println(ww1);
	// }

	public String getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag) {
		this.activityFlag = activityFlag;
	}

	public String searchYXHD() {
		String name = getRequest().getParameter("condition");
		String articleid = name.replace("'", "");
		String[] articleid1 = articleid.split(",");
		String productidsum = "";
		int k, a,j;
		String recommend1 = "";
		String recommend2 = "";
		String recommend3 = "";
		for(j =0;j<articleid1.length;j++){
			if(j==0){
				QueryBuilder qb4 = new QueryBuilder("select * from ZDColumnValue where relaid= ? and columncode='RiskCode' ");
				qb4.add(articleid1[0]);
				DataTable dt4 = qb4.executeDataTable();
				recommend1 = dt4.get(0).getString("textvalue");
			}else if(j==1){
				QueryBuilder qb5 = new QueryBuilder("select * from ZDColumnValue where relaid= ? and columncode='RiskCode' ");
				qb5.add(articleid1[1]);
				DataTable dt5 = qb5.executeDataTable();
				recommend2 = dt5.get(0).getString("textvalue");
			}else if(j==2){
				QueryBuilder qb6 = new QueryBuilder("select * from ZDColumnValue where relaid= ? and columncode='RiskCode' ");
				qb6.add(articleid1[2]);
				DataTable dt6 = qb6.executeDataTable();
				recommend3 = dt6.get(0).getString("textvalue");
			}
		}
		StringBuffer sql=new StringBuffer(" select * from ZDColumnValue where relaid in (");
		QueryBuilder qb1 = new QueryBuilder();
		for (int i = 0; i < articleid1.length; i++) {
			sql.append("?,");
			qb1.add(articleid1[i]);
		}
		sql.deleteCharAt(sql.length()-1).append(") and columncode='RiskCode' ");
		qb1.setSQL(sql.toString());
		qb1.executeDataTable();
		DataTable dt2 = qb1.executeDataTable();
		if (dt2.getRowCount() > 0) {

			for (k = 0; k < dt2.getRowCount(); k++) {

				productidsum = productidsum + "'" + dt2.get(k).getString("textvalue").toString();
				if (k == dt2.getRowCount() - 1) {
					productidsum = productidsum + "'";
				} else {
					productidsum = productidsum + "',";
				}
			}
		}

		// qb1.add(articleid[i]);
		// DataTable dt = qb1.executeDataTable();
		// articlelogo=dt.get(i).getString("Logo");
		// articlelogo1=articlelogo.split("/");
		// a=articlelogo1.length-1;
		// articlelogo2=articlelogo1[a];
		// articlelogo3=articlelogo2.replace(".jpg", "");

		String ProductsOrder = getRequest().getParameter("ProductsOrder");
		QueryBuilder qb = new QueryBuilder("select * from sdsearchrelaproduct where ProductID in (");
		qb.append(productidsum);
		qb.append(")");
		if ("order_SalesVolumedesc".equals(ProductsOrder)) {
			qb.append("order by SalesVolume +0 desc");
		} else if ("order_SalesVolumeasc".equals(ProductsOrder)) {
			qb.append("order by SalesVolume +0 asc");
		} else if ("order_InitPremdesc".equals(ProductsOrder)) {
			qb.append("order by InitPrem +0 desc");
		} else if ("order_InitPremasc".equals(ProductsOrder)) {
			qb.append("order by InitPrem +0 asc");
		}

		DataTable dt = qb.executeDataTable();
		StringBuffer wow = new StringBuffer();

		for (int i = 0; i < dt.getRowCount(); i++) {

			if (recommend1.equals(dt.get(i).getString("ProductID"))) {
				wow.append(" <div class=\"cf\"><div class=\"stc_shopboxs stc_tj\"><div class=\"product_title\">");
				wow.append("<span class=\"CInsuranceCompany icon_C" + dt.get(i).getString("SupplierCode2") + "\"  ></span> <span class=\"productName\"> ");
				wow.append("<a href=\"" + dt.get(i).getString("URL") + "\" target=\"_blank\"><h2 class=\"ziti\">" + dt.get(i).getString("ProductName") + "</h2></a></span>");
				wow.append("<span class=\"SalesVolume\">(累计销量：" + dt.get(i).getString("SalesVolume") + ")</span>");
				wow.append("<span id=\"productIntegral_209601001\" style=\"display: none;\"></span>");
				wow.append("</div>");
				wow.append("<div class=\"product_condition\">");
				wow.append(dt.get(i).getString("CalHTML2"));
				wow.append("</div>");
				wow.append("<div class=\"product_info\"><div class=\"product_info_bor\"><div class=\"prodcutMark\">");
				wow.append("<ul class=\"price\" >" + dt.get(i).getString("prodcutMarkPrice") + "</ul><ul class=\"btn\">");
				wow.append("<li class=\"btn1\"><span onclick=\"chakan('" + dt.get(i).getString("URL") + "')\">查看详情</span></li>");
				wow.append("<li class=\"btn2\"><span onclick=\"submitp('" + dt.get(i).getString("ProductId") + "');\">加入收藏</span><span class=\"add_cp_list\" style=\"margin-left: 6px;\"");
				wow.append("onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("ProductId") + "','"
						+ Config.getServerContext() + "','A00');\">加入对比</span></li>");
				wow.append("</ul></div></div>");
				wow.append("<div class=\"AdaptPeopleInfo\">" + dt.get(i).getString("AdaptPeopleInfo") + "</div>");
				wow.append("<div class=\"productFeature\">" + dt.get(i).getString("FEMRiskBrightSpot") + "</div>");
				wow.append(dt.get(i).getString("DutyHTML"));
				wow.append(" </div></div>");
				wow.append(" <div class=\"stc_pinglun\">");
				wow.append(" <p class=\"stc_djzmk\">大家怎么看：</p><ul class=\"sct_zan\">");

				String urllink = dt.get(i).getString("URL");
				String[] urllink1 = urllink.split("/");
				int m = urllink1.length;
				String urllink2 = urllink1[m - 1];
				String urllink3 = urllink2.replace(".shtml", "");

				QueryBuilder qb2 = new QueryBuilder("select * from zccomment where VerifyFlag='Y' and RelaID =?");
				qb2.add(urllink3);
				qb2.append("order by rand() limit 3");
				DataTable dt1 = qb2.executeDataTable();
				int h;
				for (h = 0; h < dt1.getRowCount(); h++) {
					wow.append("<li><span class=\"zan_s\"></span>");
					wow.append(dt1.get(h).getString("Content"));
					wow.append("</li>");
				}

				wow.append("</ul></div>");
				wow.append(" </div>");

			} else if (recommend2.equals(dt.get(i).getString("ProductID"))) {
				wow.append(" <div class=\"cf\"><div class=\"stc_shopboxs stc_tj\"><div class=\"product_title\">");
				wow.append("<span class=\"CInsuranceCompany icon_C" + dt.get(i).getString("SupplierCode2") + "\"  ></span> <span class=\"productName\"> ");
				wow.append("<a href=\"" + dt.get(i).getString("URL") + "\" target=\"_blank\"><h2 class=\"ziti\">" + dt.get(i).getString("ProductName") + "</h2></a></span>");
				wow.append("<span class=\"SalesVolume\">(累计销量：" + dt.get(i).getString("SalesVolume") + ")</span>");
				wow.append("<span id=\"productIntegral_209601001\" style=\"display: none;\"></span>");
				wow.append("</div>");
				wow.append("<div class=\"product_condition\">");
				wow.append(dt.get(i).getString("CalHTML2"));
				wow.append("</div>");
				wow.append("<div class=\"product_info\"><div class=\"product_info_bor\"><div class=\"prodcutMark\">");
				wow.append("<ul class=\"price\" >" + dt.get(i).getString("prodcutMarkPrice") + "</ul><ul class=\"btn\">");
				wow.append("<li class=\"btn1\"><span onclick=\"chakan('" + dt.get(i).getString("URL") + "')\">查看详情</span></li>");
				wow.append("<li class=\"btn2\"><span onclick=\"submitp('" + dt.get(i).getString("ProductId") + "');\">加入收藏</span><span class=\"add_cp_list\" style=\"margin-left: 6px;\"");
				wow.append("onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("ProductId") + "','"
						+ Config.getServerContext() + "','A00');\">加入对比</span></li>");
				wow.append("</ul></div></div>");
				wow.append("<div class=\"AdaptPeopleInfo\">" + dt.get(i).getString("AdaptPeopleInfo") + "</div>");
				wow.append("<div class=\"productFeature\">" + dt.get(i).getString("FEMRiskBrightSpot") + "</div>");
				wow.append(dt.get(i).getString("DutyHTML"));
				wow.append(" </div></div>");
				wow.append(" <div class=\"stc_pinglun\">");
				wow.append(" <p class=\"stc_djzmk\">大家怎么看：</p><ul class=\"sct_zan\">");
				String urllink = dt.get(i).getString("URL");
				String[] urllink1 = urllink.split("/");
				int m = urllink1.length;
				String urllink2 = urllink1[m - 1];
				String urllink3 = urllink2.replace(".shtml", "");

				QueryBuilder qb2 = new QueryBuilder("select * from zccomment where VerifyFlag='Y' and RelaID =?");
				qb2.add(urllink3);
				qb2.append("order by rand() limit 3");
				DataTable dt1 = qb2.executeDataTable();
				int h;
				for (h = 0; h < dt1.getRowCount(); h++) {
					wow.append("<li><span class=\"zan_s\"></span>");
					wow.append(dt1.get(h).getString("Content"));
					wow.append("</li>");
				}

				wow.append("</li>");
				wow.append("</ul></div>");
				wow.append(" </div>");
			} else if (recommend3.equals(dt.get(i).getString("ProductID"))) {
				wow.append(" <div class=\"cf\"><div class=\"stc_shopboxs stc_tj\"><div class=\"product_title\">");
				wow.append("<span class=\"CInsuranceCompany icon_C" + dt.get(i).getString("SupplierCode2") + "\"  ></span> <span class=\"productName\"> ");
				wow.append("<a href=\"" + dt.get(i).getString("URL") + "\" target=\"_blank\"><h2 class=\"ziti\">" + dt.get(i).getString("ProductName") + "</h2></a></span>");
				wow.append("<span class=\"SalesVolume\">(累计销量：" + dt.get(i).getString("SalesVolume") + ")</span>");
				wow.append("<span id=\"productIntegral_209601001\" style=\"display: none;\"></span>");
				wow.append("</div>");
				wow.append("<div class=\"product_condition\">");
				wow.append(dt.get(i).getString("CalHTML2"));
				wow.append("</div>");
				wow.append("<div class=\"product_info\"><div class=\"product_info_bor\"><div class=\"prodcutMark\">");
				wow.append("<ul class=\"price\" >" + dt.get(i).getString("prodcutMarkPrice") + "</ul><ul class=\"btn\">");
				wow.append("<li class=\"btn1\"><span onclick=\"chakan('" + dt.get(i).getString("URL") + "')\">查看详情</span></li>");
				wow.append("<li class=\"btn2\"><span onclick=\"submitp('" + dt.get(i).getString("ProductId") + "');\">加入收藏</span><span class=\"add_cp_list\" style=\"margin-left: 6px;\"");
				wow.append("onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("ProductId") + "','"
						+ Config.getServerContext() + "','A00');\">加入对比</span></li>");
				wow.append("</ul></div></div>");
				wow.append("<div class=\"AdaptPeopleInfo\">" + dt.get(i).getString("AdaptPeopleInfo") + "</div>");
				wow.append("<div class=\"productFeature\">" + dt.get(i).getString("FEMRiskBrightSpot") + "</div>");
				wow.append(dt.get(i).getString("DutyHTML"));
				wow.append(" </div></div>");
				wow.append(" <div class=\"stc_pinglun\">");
				wow.append(" <p class=\"stc_djzmk\">大家怎么看：</p><ul class=\"sct_zan\">");
				String urllink = dt.get(i).getString("URL");
				String[] urllink1 = urllink.split("/");
				int m = urllink1.length;
				String urllink2 = urllink1[m - 1];
				String urllink3 = urllink2.replace(".shtml", "");

				QueryBuilder qb2 = new QueryBuilder("select * from zccomment where VerifyFlag='Y' and RelaID =?");
				qb2.add(urllink3);
				qb2.append("order by rand() limit 3");
				DataTable dt1 = qb2.executeDataTable();
				int h;
				for (h = 0; h < dt1.getRowCount(); h++) {
					wow.append("<li><span class=\"zan_s\"></span>");
					wow.append(dt1.get(h).getString("Content"));
					wow.append("</li>");
				}

				wow.append("</li>");
				wow.append("</ul></div>");
				wow.append(" </div>");
			} else {
				wow.append(" <div class=\"cf\"><div class=\"stc_shopboxs\"><div class=\"product_title\">");
				wow.append("<span class=\"CInsuranceCompany icon_C" + dt.get(i).getString("SupplierCode2") + "\"  ></span> <span class=\"productName\"> ");
				wow.append("<a href=\"" + dt.get(i).getString("URL") + "\" target=\"_blank\"><h2 class=\"ziti\">" + dt.get(i).getString("ProductName") + "</h2></a></span>");
				wow.append("<span class=\"SalesVolume\">(累计销量：" + dt.get(i).getString("SalesVolume") + ")</span>");
				wow.append("<span id=\"productIntegral_209601001\" style=\"display: none;\"></span>");
				wow.append("</div>");
				wow.append("<div class=\"product_condition\">");
				wow.append(dt.get(i).getString("CalHTML2"));
				wow.append("</div>");
				wow.append("<div class=\"product_info\"><div class=\"product_info_bor\"><div class=\"prodcutMark\">");
				wow.append("<ul class=\"price\" >" + dt.get(i).getString("prodcutMarkPrice") + "</ul><ul class=\"btn\">");
				wow.append("<li class=\"btn1\"><span onclick=\"chakan('" + dt.get(i).getString("URL") + "')\">查看详情</span></li>");
				wow.append("<li class=\"btn2\"><span onclick=\"submitp('" + dt.get(i).getString("ProductId") + "');\">加入收藏</span><span class=\"add_cp_list\" style=\"margin-left: 6px;\"");
				wow.append("onclick=\"showcp('" + dt.get(i).getString("ProductName") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("ProductId") + "','"
						+ Config.getServerContext() + "','A00');\">加入对比</span></li>");
				wow.append("</ul></div></div>");
				wow.append("<div class=\"AdaptPeopleInfo\">" + dt.get(i).getString("AdaptPeopleInfo") + "</div>");
				wow.append("<div class=\"productFeature\">" + dt.get(i).getString("FEMRiskBrightSpot") + "</div>");
				wow.append(dt.get(i).getString("DutyHTML"));
				wow.append(" </div></div>");
				wow.append(" <div class=\"stc_pinglun\">");
				wow.append(" <p class=\"stc_djzmk\">大家怎么看：</p><ul class=\"sct_zan\">");
				String urllink = dt.get(i).getString("URL");
				String[] urllink1 = urllink.split("/");
				int m = urllink1.length;
				String urllink2 = urllink1[m - 1];
				String urllink3 = urllink2.replace(".shtml", "");

				QueryBuilder qb2 = new QueryBuilder("select * from zccomment where VerifyFlag='Y' and RelaID =?");
				qb2.add(urllink3);
				qb2.append("order by rand() limit 3");
				DataTable dt1 = qb2.executeDataTable();
				int h;
				for (h = 0; h < dt1.getRowCount(); h++) {
					wow.append("<li><span class=\"zan_s\"></span>");
					wow.append(dt1.get(h).getString("Content"));
					wow.append("</li>");
				}

				wow.append("</li>");
				wow.append("</ul></div>");
				wow.append(" </div>");
			}

		}

		String kk = wow.toString();
		Map<String, String> jsonMap = new HashMap<String, String>();
		// 根据产品编码查询zcarticle表，拼装列表模版，拼装分页函数
		jsonMap.put("message", kk);
		jsonMap.put("status", "0");
		return ajaxJson(jsonMap);

		// 拆封条件，去查询产品中心数据库，获取产品编码
		// Map<String, String> jsonMap = new HashMap<String, String>();
		// jsonMap.put("status", "0");
		// try {
		// ProductType = ProductType.substring(0, 1);
		// condtion = condtion.substring(1);
		// String[] str3 = condtion.split("\\|");
		// for (int j = 0; j < str3.length; j++) {
		// if (str3[j].indexOf(",") != -1) {
		// String[] str4 = str3[j].split(",");
		// for (int k = 0; k < str4.length; k++) {
		// if ("N".equals(Filtrate.ValueCheck(str4[k])) &&
		// "01".equals(Filtrate.ValueCheck1(str4[k]))) {
		// str3[j] = str4[k + 1];
		// break;
		// }
		// }
		// }
		// }
		// for (int j = 0; j < str3.length; j++) {
		// condtion1 += str3[j];
		// if (j != str3.length - 1) {
		// condtion1 += "|";
		// }
		// }
		// condtion = condtion1;
		// String[] conditionsplit = condtion.split("\\|");
		// String sql0 = "select  a.productID from sdproduct a where 1=1 ";
		// boolean balanceFlag = false;
		// for (int j = 0; j < conditionsplit.length; j++) {
		// if ((conditionsplit[j]).startsWith("default_")) {
		// continue;
		// } else {
		// balanceFlag = true;
		// if (conditionsplit[j].indexOf(",") != 0) {
		// String[] conditionsplit2 = conditionsplit[j].split(",");
		// for (int k = 0; k < conditionsplit2.length; k++) {
		// if ("Y".equals(Filtrate.ValueCheck(conditionsplit2[k])) &&
		// "02".equals(Filtrate.ValueCheck1(conditionsplit2[k]))) {
		// sql0 +=
		// " and exists (select 1 from FEMProductRelaCondition where searchcode in ("
		// + conditionsplit[j] + ") and riskcode = a.productID )";
		// break;
		// } else {
		// sql0 +=
		// " and exists (select 1 from FEMProductRelaCondition where searchcode ='"
		// + conditionsplit2[k] + "' and riskcode = a.productID )";
		// }
		// }
		// } else {
		// sql0 +=
		// " and exists (select 1 from FEMProductRelaCondition where searchcode='"
		// + conditionsplit[j] + "' and riskcode = a.productID )";
		// }
		// }
		// }
		// if(!balanceFlag){
		// sql0 += " and a.productType = '" + ProductType + "'" ;
		// }
		// LogUtil.info("筛选sql " + sql0);
		// QueryBuilder qb0 = new QueryBuilder(sql0);
		// DataTable dt0 = qb0.executeDataTable();
		// if (dt0.getRowCount() > 0) {
		// String productIDs = "";
		// for (int i = 0; i < dt0.getRowCount(); i++) {
		// productIDs += dt0.get(i).getString("productID");
		// if (i != dt0.getRowCount() - 1) {
		// productIDs += ",";
		// }
		// }
		// String sql = "select * from SDSearchRelaProduct where productId in ("
		// + productIDs + ") ";
		// QueryBuilder qb1 = new QueryBuilder(sql);
		// DataTable dt1 = qb1.executeDataTable();
		// if (dt1.getRowCount() > 0) {
		// setSession("dt1", dt1);
		// } else {
		// if (getSession("dt1") != null) {
		// dt1 = (DataTable) getSession("dt1");
		// }
		// }
		// DataTable dt = new DataTable();
		// if (pageIndex != 0) {
		// pageIndex = pageIndex - 1;
		// }
		// int nextPage = pageIndex + 2;
		// int start = pageIndex * 10;
		// List<DataRow> alist = new ArrayList<DataRow>();
		// int total = dt1.getRowCount();
		// int pageCount = new Double(Math.ceil(total * 1.0 / 10)).intValue();
		// for (int i = 0; i < dt1.getRowCount(); i++) {
		// alist.add(dt1.get(i));
		// }
		// if (StringUtil.isNotEmpty(ProductsOrder)) {
		// String[] str1 = ProductsOrder.split(" ");
		// SortList<DataRow> sortList = new SortList<DataRow>();
		// sortList.Sort(alist, str1[0], str1[1]);
		// }
		// for (DataRow dt2 : alist) {
		// dt.insertRow(dt2);
		// }
		// StringBuffer sb1 = new StringBuffer();
		// StringBuffer sb2 = new StringBuffer();
		// String urlSql =
		// "select value from zdconfig where type='BXSCProductURL'";
		// DataTable dtUrl = new QueryBuilder(urlSql).executeDataTable();
		// String url = "";
		// if(dtUrl.getRowCount()>0){
		// url = dt.getString(0, 0);
		// }
		//
		// for (int i = start; i < start + 10 && i < dt.getRowCount(); i++) {
		// String productId = dt.get(i).getString("ProductId");
		// url = url+"productId = "+productId;
		// if (url.indexOf("://") < 0) {
		// String siteUrl = SiteUtil.getURL(dt.get(i).getString("SITEID"));
		// if (!siteUrl.endsWith("/")) {
		// siteUrl = siteUrl + "/";
		// }
		// if (url.startsWith("/")) {
		// url = url.substring(1);
		// }
		// url = siteUrl + url;
		// }
		// ;
		// sb1.append("<div class=\"product_title\">");
		// sb1.append("<span class=\"CInsuranceCompany icon_C" +
		// dt.get(i).getString("SupplierCode2") +
		// "\"  ></span> <span class=\"productName\"> ");
		// sb1.append("<a href=\"" + url +
		// "\" target=\"_blank\"><h2 class=\"ziti\">" +
		// dt.get(i).getString("ProductName") + "</h2></a></span>");
		// sb1.append("<span class=\"SalesVolume\">(累计销量：" +
		// dt.get(i).getString("SalesVolume") + ")</span>");
		// sb1.append("<span style=\"display: none;\" id=\"productIntegral_" +
		// dt.get(i).getString("ProductId") + "}\"></span>");
		// sb1.append("</div><div class=\"product_condition\">");
		// sb1.append(dt.get(i).getString("CalHTML2"));
		// sb1.append("</div><div class=\"product_info\"><div class=\"product_info_bor\"><div class=\"prodcutMark\">");
		// sb1.append("<ul class=\"price\" >" +
		// dt.get(i).getString("prodcutMarkPrice") + "</ul><ul class=\"btn\">");
		// sb1.append("<li class=\"btn1\"><span onclick=\"chakan('" + url +
		// "')\">查看详情</span></li>");
		// sb1.append("<li class=\"btn2\"><span onclick=\"submitp('" +
		// dt.get(i).getString("ProductId") +
		// "');\">加入收藏</span><span class=\"add_cp_list\" style=\"margin-left: 6px;\"");
		// sb1.append("onclick=\"showcp('" + dt.get(i).getString("ProductName")
		// + "','" + dt.get(i).getString("logo") + "','" +
		// dt.get(i).getString("ProductId") + "','"
		// + Config.getServerContext() + "','" + ProductType +
		// "');\">加入对比</span></li>");
		// sb1.append("</ul></div></div>");
		// sb1.append("<div class=\"AdaptPeopleInfo\">" +
		// dt.get(i).getString("AdaptPeopleInfo") + "</div>");
		// sb1.append("<div class=\"productFeature\">" +
		// dt.get(i).getString("FEMRiskBrightSpot") + "</div>");
		// sb1.append(dt.get(i).getString("DutyHTML"));
		// sb1.append("</div>");
		// }
		// sb2.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		// sb2.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		// sb2.append("共" + total + "条记录，每页" + 10 + "条，当前第<span class='fc_ch1'>"
		// + ((total == 0) ? 0 : (pageIndex + 1)) +
		// "</span>/<span class='fc_ch1'>" + pageCount
		// + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		// if (pageIndex > 0) {
		//
		// sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + 1 +
		// "\");'><span class='fc_ch1'>第一页</span></a>|");
		// if (1 == pageIndex) {
		// sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" +
		// pageIndex + "\");'><span class='fc_ch1'>上一页</span></a>|");
		// } else {
		// sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" +
		// pageIndex + "\");'><span class='fc_ch1'>上一页</span></a>|");
		// }
		//
		// } else {
		// sb2.append("<span class='fc_hui2'>第一页</span>|");
		// sb2.append("<span class='fc_hui2'>上一页</span>|");
		// }
		// if (pageIndex + 1 != pageCount && pageCount > 0) {
		// sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" +
		// nextPage + "\");'><span class='fc_ch1'>下一页</span></a>|");
		// sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" +
		// pageCount + "\");'><span class='fc_ch1'>最末页</span></a>");
		// } else {
		// sb2.append("<span class='fc_hui2'>下一页</span>|");
		// sb2.append("<span class='fc_hui2'>最末页</span>");
		// }
		//
		// sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
		// sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");
		//
		// sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>"
		// + pageCount
		// +
		// "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){newJump();}else{newJump();}}\" style='' value='跳转'></td>");
		// sb2.append("</tr></table>");
		//
		// jsonMap.put("status", "1");
		// if (dt.getRowCount() != 0) {
		// jsonMap.put("sb1", sb1.toString());
		// jsonMap.put("sb3", sb2.toString());
		// }
		//
		// }
		// } catch (Exception e) {
		// jsonMap.put("status", "0");
		// LogUtil.error("搜索排序方法出现异常：" + e.getMessage());
		// e.printStackTrace();
		// }
		// // return ajaxJson(jsonpname+"("+jsonMap+")");
		// return ajaxJson(jsonMap);

	}

	public String searchPL() {
		String name = getRequest().getParameter("condition");
		String[] name1 = name.split(",");
		String name2, name4, name5;
		int m, k;
		String wow1 = "";
		for (m = 0; m < name1.length; m++) {
			name2 = name1[m];
			String[] name3 = name2.split("/");
			k = name3.length;
			name4 = name3[k - 1];
			name5 = name4.replace(".shtml", "");
			QueryBuilder qb1 = new QueryBuilder("select * from zccomment where VerifyFlag='Y' and RelaID =?");
			qb1.add(name5);
			qb1.append("order by rand() limit 3");
			DataTable dt = qb1.executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				wow1 = wow1 + "<li><span class=\"zan_s\"></span>" + dt.get(i).getString("Content") + "</li>";
			}
			wow1 = wow1 + "-";
		}

		// String[] name1=name.split("/");
		// int k=name1.length;
		// String name2=name1[k-1];
		// String name3=name2.replace(".shtml", "");
		// QueryBuilder qb1 = new
		// QueryBuilder("select * from zccomment where VerifyFlag='Y' and RelaID =?");
		// qb1.add(name3);
		// qb1.append("order by rand() limit 3");
		// DataTable dt = qb1.executeDataTable();
		// StringBuffer wow1 = new StringBuffer();
		// System.out.println(dt.getRowCount());
		// for(int i=0;i<dt.getRowCount();i++)
		// {
		// wow1.append("<li><span class=\"zan_s\"></span>");
		// wow1.append(dt.get(i).getString("Content"));
		// wow1.append("</li>");
		// }
		//
		//
		String kk = wow1;
		Map<String, String> jsonMap = new HashMap<String, String>();
		// 根据产品编码查询zcarticle表，拼装列表模版，拼装分页函数
		jsonMap.put("message", kk);
		jsonMap.put("status", "0");
		return ajaxJson(jsonMap);
	}

	public String quickPageList() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		int pageIndex = 0;
		String applicantIdentityId = request.getParameter("hidApplicantIdentityId");
		if (StringUtil.isNotEmpty(request.getParameter("pageIndex"))) {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex")) - 1;
		}
		String applicantName = request.getParameter("hidApplicantName");
		try {
			applicantName = java.net.URLDecoder.decode(applicantName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		String applicantIdentityType = request.getParameter("hidApplicantIdentityType");
		jsonMap = quickOrderList(pageIndex, applicantName, applicantIdentityType, applicantIdentityId);
		jsonMap.put("status", "0");
		
		
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 订单快速查询
	 * 
	 * @author jiaomengying
	 * @throws UnsupportedEncodingException
	 */
	public String quickList() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = StringUtils.upperCase(request
					.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {
				jsonMap.put("status", "1");
				jsonMap.put("message", "验证码没有写对哟");
				return ajaxJson(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("status", "1");
			jsonMap.put("message", "验证码没有写对哟");
			return ajaxJson(jsonMap);
		}
		
		int pageIndex = 0;
		String applicantIdentityId = request.getParameter("applicantIdentityId");
		if (StringUtil.isNotEmpty(request.getParameter("pageIndex"))) {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex")) - 1;
		}
		String applicantName = request.getParameter("applicantName");
		try {
			applicantName = java.net.URLDecoder.decode(applicantName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		String applicantIdentityType = request.getParameter("applicantIdentityType");
		
		jsonMap = quickOrderList(pageIndex, applicantName, applicantIdentityType, applicantIdentityId);
		jsonMap.put("status", "0");
		
		return ajaxJson(jsonMap);

	}

	private Map<String, String> quickOrderList(int pageIndex, String applicantName, String applicantIdentityType, String applicantIdentityId) {
		Map<String, String> result = new HashMap<String, String>();
		int pageSize = 10;
		
		String appIdentityTypeNames = new QueryBuilder("select Memo from zdcode where CodeType='Certificate' and ParentCode='Certificate' and CodeValue = ?", applicantIdentityType).executeString();
		if (appIdentityTypeNames == null) {
			appIdentityTypeNames = "";
		}
		appIdentityTypeNames = appIdentityTypeNames.replace(",", "','");
		// 查询订单号、投保人姓名、险种名称、支付日期、生效日期
		StringBuffer resuSql = new StringBuffer();
		resuSql.append("SELECT o.orderSn, a.applicantName, i.productName, DATE_FORMAT(t.sendDate,'%Y-%m-%d') payDate, ");
		resuSql.append("DATE_FORMAT(i.startDate,'%Y-%m-%d') svaliDate, IF(DATE_FORMAT(i.startDate,'%Y-%m-%d %H:%i:%s') ");
		resuSql.append("< DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), '0', '1') valiStatus ");
		StringBuffer whereSql = new StringBuffer();
		whereSql.append("FROM sdinformationappnt a, sdinformation i, sdorders o ");
		whereSql.append("LEFT JOIN tradeinformation t ON o.orderSN=t.ordID AND t.payStatus='1' ");
		whereSql.append("WHERE a.applicantName=? AND a.applicantIdentityTypeName in ('"+appIdentityTypeNames+"') AND a.applicantIdentityId=? AND a.informationSn=i.informationSn AND i.orderSn=o.orderSn ");
		
		QueryBuilder qb = new QueryBuilder("SELECT count(1) " + whereSql.toString());
		
		qb.add(applicantName);
		qb.add(applicantIdentityId);
		// 总行数
		int totalNum = qb.executeInt();
		List<Map<String, String>> orderInfo = new ArrayList<Map<String, String>>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (totalNum > 0) {
			qb = new QueryBuilder(resuSql.toString() + whereSql.toString() + " ORDER BY o.createdate DESC");
			
			qb.add(applicantName);
			qb.add(applicantIdentityId);
			DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
			if (dt != null && dt.getRowCount() > 0) {
				int rowcount = dt.getRowCount();
				int i = 0;
				Map<String, String> orderMap;
				int colCount = dt.getColCount();
				int j = 0;
				for (; i < rowcount; i++) {
					orderMap = new HashMap<String, String>();
					j = 0;
					for (; j < colCount; j++) {
						orderMap.put(dt.getColumnName(j), dt.getString(i, j));
					}
					if (orderMap.get("payDate") == null) {
						orderMap.put("payDate", "");
					}
					orderInfo.add(orderMap);
				}
				
			}
			
			// 总页数
			int lastpage = ((totalNum + pageSize-1) / (pageSize));
			map.put("lastpage", lastpage);
			if (lastpage > 1) {
				map.put("pageFootList",PageDataList(lastpage, pageIndex));
			}
		} 
		map.put("orderInfo", orderInfo);
		map.put("totalNum", totalNum);
		map.put("pageIndex", pageIndex+1);
		map.put("base", Config.getServerContext());
		result.put("message", FreemarkerUtil.templateManage(Config.getContextRealPath()
				+ "WEB-INF/template/admin", "qscx_result", map));
		result.put("totalNum", totalNum + "");
		return result;
	}
	
 

	public String dutySave() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, String> jsonMap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		String riskcode = "";
		// 责任信息
		String dutyinfo = "";
		Iterator iter = request.getParameterMap().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			if ("prem_callback".equalsIgnoreCase(key)) {
				continue;
			}
			if ("RiskCode".equalsIgnoreCase(key)) {
				riskcode = ((String[]) entry.getValue())[0];
				continue;
			}
			if ("duty".equalsIgnoreCase(key)) {
				dutyinfo = ((String[]) entry.getValue())[0];
				continue;
			}
			Object val = entry.getValue();
			String value = "";
			if (val instanceof String[]) {
				String[] strs = (String[]) val;
				for (int i = 0; i < strs.length; i++) {
					// 如果是多个参数直接累加，保费试算应该没有出现多个的可能
					value += strs[i];
				}
			} else {
				value = val.toString();
			}
			map.put(key, value);
		}

		if (StringUtil.isEmpty(dutyinfo)) {
			jsonMap.put("status", "0");
			jsonMap.put("msg", "网络有点慢，请再点次立即购买！");
			logger.warn("未接收到责任信息！");
		} else {
			if (StringUtil.isNotEmpty(riskcode)) {
				Iterator<String> itr = map.keySet().iterator();
				FEMRiskFactorList[] fEMRiskFactorList = new FEMRiskFactorList[map.size()];
				int iCount = 0;
				String key = "";
				while (itr.hasNext()) {
					key = itr.next();
					fEMRiskFactorList[iCount] = new FEMRiskFactorList();
					fEMRiskFactorList[iCount].setAppFactorCode(key.split("_")[0]);
					fEMRiskFactorList[iCount].setFactorType(key.split("_")[1]);
					fEMRiskFactorList[iCount].setFactorValue(map.get(key));
					if ("TextAge".equals(fEMRiskFactorList[iCount].getFactorType())) {
						String age = map.get(key);
						if (StringUtil.isNotEmpty(age)) {
							String birthday = new OrderConfigNewServiceImpl().getBrithdayByFactor(null, age);
							fEMRiskFactorList[iCount].setFactorValue(birthday);
						}
					}
					fEMRiskFactorList[iCount].setIsPremCalFacotor("Y");
					iCount++;
				}
				CalProductPrem[] calProductPrem;
				// @前部分责任为用户选择的责任保额，后部分是默认且不展示的责任信息用于传给保险公司
				String[] duty = dutyinfo.split("@");
				String[] dutys = duty[0].split(",");
				calProductPrem = new CalProductPrem[dutys.length];
				for (int i = 0; i < dutys.length; i++) {
					calProductPrem[i] = new CalProductPrem();
					calProductPrem[i].setDutyCode(dutys[i].split("-")[0]);
					calProductPrem[i].setAmnt(dutys[i].split("-")[1]);
					calProductPrem[i].setRiskCode(riskcode);
					calProductPrem[i].setFEMRiskFactorList(fEMRiskFactorList);
				}
				Map<String, Object> mp = new HashMap<String, Object>();
				String[] discountPrice = null;
				String[] prem = null;
				try {
					mp.put("CalProductPrem", changeAotuPeriod(calProductPrem));
					ProductPremResponse ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
					if ("2".equals(ProductPrem.getResultDTO().getResultCode())) {
						jsonMap.put("status", "0");
						String msg = ProductPrem.getResultDTO().getResultInfoDesc();
						if (StringUtil.isEmpty(msg)) {
							msg = "责任保额组合错误！";
						}
						jsonMap.put("msg", msg);
						return ajaxHtml(JSONObject.fromObject(jsonMap).toString());
					}
					discountPrice = ProductPrem.getDiscountPrice();
					prem = ProductPrem.getPrem();
				} catch (Exception e) {
					logger.error("复杂产品点击立即购买 方法dutySave报错！" + e.getMessage(), e);
				}

				int len = dutys.length;
				String serials = NoUtil.getMaxNo("DutyTempID", "ID", 20);
				SDInformationDutyTempSet set = new SDInformationDutyTempSet();
				SDInformationDutyTempSchema dutyTemp;
				int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ riskcode +"', CodeValue);").executeInt();
				for (int i = 0; i < len; i++) {
					dutyTemp = new SDInformationDutyTempSchema();
					dutyTemp.setId(NoUtil.getMaxNo("DutyTempID", "ID", 20));
					dutyTemp.setDutySerials(serials);
					dutyTemp.setCreateDate(new Date());
					dutyTemp.setModifyDate(dutyTemp.getCreateDate());
					dutyTemp.setDutySn(dutys[i].split("-")[0]);

					// 合众年金保险 保额与保费对调 将来如果这种情况比较多的话，可以把riskcode写成配置
					if (count > 0) {
						dutyTemp.setAmnt(prem[i]);
						if (discountPrice != null && discountPrice.length > 0 && StringUtil.isNotEmpty(discountPrice[i])) {
							dutyTemp.setDiscountPrice(dutys[i].split("-")[1]);
						}
						if (prem != null && prem.length > 0) {
							dutyTemp.setPremium(dutys[i].split("-")[1]);
						}
					} else {
						dutyTemp.setAmnt(dutys[i].split("-")[1]);
						if (discountPrice != null && discountPrice.length > 0 && StringUtil.isNotEmpty(discountPrice[i])) {
							dutyTemp.setDiscountPrice(discountPrice[i]);
						}
						if (prem != null && prem.length > 0) {
							dutyTemp.setPremium(prem[i]);
						}
					}

					set.add(dutyTemp);
				}

				if (duty.length == 2) {
					String[] defalutDuty = duty[1].split(",");
					int leng = defalutDuty.length;
					for (int i = 0; i < leng; i++) {
						dutyTemp = new SDInformationDutyTempSchema();
						dutyTemp.setId(NoUtil.getMaxNo("DutyTempID", "ID", 20));
						dutyTemp.setDutySerials(serials);
						dutyTemp.setCreateDate(new Date());
						dutyTemp.setModifyDate(dutyTemp.getCreateDate());
						dutyTemp.setDutySn(defalutDuty[i].split("-")[0]);
						dutyTemp.setAmnt(defalutDuty[i].split("-")[1]);
						dutyTemp.setRemark2("N");
						set.add(dutyTemp);
					}
				}
				if (!set.insert()) {
					jsonMap.put("status", "0");
					jsonMap.put("msg", "抱歉，数据异常，请联系客服mm~");
					logger.warn("责任信息保存失败！");
				} else {
					jsonMap.put("status", "1");
					jsonMap.put("serials", serials);
				}
			} else {
				logger.warn("复杂产品riskcode未传值！");
			}
		}

		return ajaxHtml(JSONObject.fromObject(jsonMap).toString());
	}

	/**
	 * 保费试算
	 * 
	 * @return
	 */
	public String premDoCal() {

		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, String> jsonMap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		String channelSn = "wj";// 渠道编码，默认wj
		// 是否是复杂产品标识
		String complicatedFlag = "N";
		String warnMessage = "";
		String pointproducttype = "";
		String riskcode = "";
		//4139bug0003034-bug:录入时保费试算与支付页保费试算不一致
		String insureDate = "";
		try {
			// 责任信息
			String dutyinfo = "";
			// 保费试算的来源，产品详细页面为空，积分商城为point
			Iterator iter = request.getParameterMap().entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = entry.getKey().toString();
				if ("prem_callback".equalsIgnoreCase(key)) {
					continue;
				}
				if ("_".equals(key)) {
					continue;
				}
				// 如果有cps用户的coolie信息，那么渠道编码为cps
				if ("channelSn".equalsIgnoreCase(key)) {
					if (StringUtil.isNotEmpty(String.valueOf(entry.getValue()))) {
						channelSn = String.valueOf(request.getParameter("channelSn"));
					}
					continue;
				}
				if ("RiskCode".equalsIgnoreCase(key)) {
					riskcode = ((String[]) entry.getValue())[0];
					continue;
				}
				if ("complicatedFlag".equalsIgnoreCase(key)) {
					complicatedFlag = ((String[]) entry.getValue())[0];
					continue;
				}
				if ("duty".equalsIgnoreCase(key)) {
					dutyinfo = ((String[]) entry.getValue())[0];
					continue;
				}
				if ("pointproducttype".equalsIgnoreCase(key)) {
					pointproducttype =  ((String[]) entry.getValue())[0];
					continue;
				}
				
				if ("insureDate".equalsIgnoreCase(key)) {
					insureDate =  ((String[]) entry.getValue())[0] + " 00:00:00";
					continue;
				}

				Object val = entry.getValue();
				String value = "";
				if (val instanceof String[]) {
					String[] strs = (String[]) val;
					for (int i = 0; i < strs.length; i++) {
						// 如果是多个参数直接累加，保费试算应该没有出现多个的可能
						value += strs[i];
					}
				} else {
					value = val.toString();
				}
				map.put(key, value);
			}

			if (riskcode != null && !"".equals(riskcode.trim())) {
				Iterator<String> itr = map.keySet().iterator();
				FEMRiskFactorList[] fEMRiskFactorList = new FEMRiskFactorList[map.size()];
				int iCount = 0;
				String key = "";
				while (itr.hasNext()) {
					key = itr.next();
					fEMRiskFactorList[iCount] = new FEMRiskFactorList();
					fEMRiskFactorList[iCount].setAppFactorCode(key.split("_")[0]);
					fEMRiskFactorList[iCount].setFactorType(key.split("_")[1]);
					fEMRiskFactorList[iCount].setFactorValue(map.get(key));
					if ("TextAge".equals(fEMRiskFactorList[iCount].getFactorType())) {
						String age = map.get(key);
						if (StringUtil.isNotEmpty(age)) {
							String birthday = new OrderConfigNewServiceImpl().getBrithdayByFactor(insureDate, age);
							fEMRiskFactorList[iCount].setFactorValue(birthday);
						}
					}
					fEMRiskFactorList[iCount].setIsPremCalFacotor("Y");
					iCount++;
				}
				CalProductPrem[] calProductPrem;
				if ("Y".equalsIgnoreCase(complicatedFlag) && StringUtil.isNotEmpty(dutyinfo)) {
					String[] dutys = dutyinfo.split(",");
					calProductPrem = new CalProductPrem[dutys.length];
					for (int i = 0; i < dutys.length; i++) {
						calProductPrem[i] = new CalProductPrem();
						calProductPrem[i].setDutyCode(dutys[i].split("-")[0]);
						calProductPrem[i].setAmnt(dutys[i].split("-")[1]);
						calProductPrem[i].setRiskCode(riskcode);
						calProductPrem[i].setFEMRiskFactorList(fEMRiskFactorList);
						calProductPrem[i].setInsureDate(insureDate);
					}

				} else {
					calProductPrem = new CalProductPrem[1];
					calProductPrem[0] = new CalProductPrem();
					calProductPrem[0].setRiskCode(riskcode);
					calProductPrem[0].setFEMRiskFactorList(fEMRiskFactorList);
					calProductPrem[0].setInsureDate(insureDate);
				}

				Map<String, Object> mp = new HashMap<String, Object>();
				mp.put("CalProductPrem", changeAotuPeriod(calProductPrem));
				ProductPremResponse ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
				String[] prems = ProductPrem.getPrem();
				double[] rates = ProductPrem.getFeeRate();
				if ("2".equals(ProductPrem.getResultDTO().getResultCode())) {
					jsonMap.put("status", "2");
					String msg = ProductPrem.getResultDTO().getResultInfoDesc();
					if (StringUtil.isEmpty(msg)) {
						msg = "责任保额组合错误！";
					}
					jsonMap.put("msg", msg);
					return ajaxHtml(JSONObject.fromObject(jsonMap).toString());
				} else if ("3".equals(ProductPrem.getResultDTO().getResultCode())) {
					warnMessage = ProductPrem.getResultDTO().getResultInfoDesc();
				}

				String[] DiscountPrice = ProductPrem.getDiscountPrice();

				double tPrems = Double.parseDouble(prems[0]);
				jsonMap.put("status", "1");

				if (!riskcode.startsWith("1015") && !"Y".equalsIgnoreCase(complicatedFlag)) {
					if (prems != null && prems.length > 0 && tPrems > 0) {
						// jsonMap.put("productPrem", "" + DiscountPrice[0]);//
						// 现价
						jsonMap.put("productRatePrem", tPrems + "");// 原价

						/*
						 * 送积分规则修改 if (rates != null && rates.length > 0 &&
						 * rates[0] > 0 && a.size() > 0 && a.get(0) != null &&
						 * DiscountPrice != null && DiscountPrice.length > 0) {
						 * jsonMap.put("productIntegral", "" +
						 * getIntegral(Double.parseDouble(DiscountPrice[0]),
						 * rates[0], a.get(0).get("ProductType"))); } else {
						 * jsonMap.put("productIntegral", ""); }
						 */

						Map<String, String> ProductIDMap = new HashMap<String, String>();
						ProductIDMap.put(riskcode, DiscountPrice[0]);

						/* cps 修改点 */
						Map<String, Map<String, String>> resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap,"", channelSn);
						Map<String, String> productDetail = resultProductDetail.get(riskcode);

						String rAmount = productDetail.get("Amount");
						String rIntegral = productDetail.get("Integral");
						String PointAmount = productDetail.get("PointAmount");
						BigDecimal CurrentValidatePoint=new BigDecimal("0");
						Member loginMember = getLoginMember();
						String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
						if(loginMember!=null){
							 CurrentValidatePoint=new BigDecimal(loginMember.getCurrentValidatePoint());
							BigDecimal pointValue = CurrentValidatePoint.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
							BigDecimal points=new BigDecimal(PointAmount);
							if(points.compareTo(pointValue)>0){
								PointAmount=String.valueOf(pointValue);
							}
						}

						// 网站活动折扣前保费
						BigDecimal prem = new BigDecimal(0);
						if (StringUtil.isNotEmpty(DiscountPrice[0])) {
							prem = new BigDecimal(DiscountPrice[0]);
						}

						// 网站活动折扣后保费
						BigDecimal nowPrem = new BigDecimal(0);
						if (StringUtil.isNotEmpty(rAmount)) {
							nowPrem = new BigDecimal(rAmount);
						}
						// 有折扣的情况 原价显示保险公司折后价格
						if (prem.compareTo(nowPrem) == 1) {
							jsonMap.put("productRatePrem", DiscountPrice[0]);
						}

						// 折后价
						jsonMap.put("productPrem", rAmount);
						// 高倍积分
						DiscountPrice[0] = rIntegral;
						jsonMap.put("loginFlag", "false");
						jsonMap.put("pointDesFlag", "false");
						String baseIntegral = nowPrem.multiply(new BigDecimal(PointScalerUnit)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
						// 获得积分的数量用现价进行换算 一元得一积分，不满一元不送积分
						if (DiscountPrice != null && DiscountPrice.length > 0  ) {
							if (loginMember != null && Integer.parseInt(DiscountPrice[0]) > 0 ) {
								String productGivePointsPercent = productDetail.get("pointrate");//产品赠送积分比例
								//会员等级
								Map<String,String>  map_result_grade=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BUY,loginMember.getId(), baseIntegral,productGivePointsPercent);
								String flag_grade=map_result_grade.get("flag");
								if("true".equals(flag_grade)){
									//String point_result=map_result_grade.get("points");
									String MemberGrade=map_result_grade.get("MemberGrade");
									
									//无取舍操作的积分值
									//String pointsAll=map_result_grade.get("pointsAll");
									DiscountPrice[0]= String.valueOf(Integer.parseInt(DiscountPrice[0]) + Integer.parseInt(map_result_grade.get("addpoints")));
									jsonMap.put("pointDesFlag", "true");
									jsonMap.put("pointDes", "您是"+MemberGrade+"会员,投保成功将获得 "+DiscountPrice[0]+" 个积分");
								}
								
								//会员生日月
								Map<String,String>  map_result=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BIRTH_MONTH,loginMember.getId(), baseIntegral,productGivePointsPercent);
								String flag=map_result.get("flag");
								//String point=map_result.get("points");
								if("true".equals(flag)){
									String MemberGrade=map_result.get("MemberGrade");
									DiscountPrice[0]= String.valueOf(Integer.parseInt(DiscountPrice[0]) + Integer.parseInt(map_result.get("addpoints")));
									
									jsonMap.put("pointDesFlag", "true");
									jsonMap.put("pointDes", MemberGrade+"会员生日月享特权,投保成功特别赠送 "+DiscountPrice[0]+" 个积分");
								}
								jsonMap.put("loginFlag", "true");
							}
							jsonMap.put("productIntegral", String.valueOf((int) Double.parseDouble(DiscountPrice[0])));
							// 最多抵扣积分
							jsonMap.put("IntePointAmountgral", String.valueOf(Double.parseDouble(PointAmount)));
							jsonMap.put("MemberAmountgral", String.valueOf(CurrentValidatePoint));
						}
					}
				} else {
					String DiscountTotalPrice = ProductPrem.getDiscountTotalPrice();
					String TotalPrice = ProductPrem.getTotalPrice();
					if (StringUtil.isNotEmpty(DiscountTotalPrice) && StringUtil.isNotEmpty(TotalPrice)) {

						jsonMap.put("status", "1");
						jsonMap.put("productPrem", DiscountTotalPrice);// 现价
						jsonMap.put("productRatePrem", TotalPrice);// 原价

						/*
						 * 送积分规则修改 if (rates != null && rates.length > 0 &&
						 * rates[0] > 0 && a.get(0) != null && DiscountPrice !=
						 * null && DiscountPrice.length > 0) {
						 * jsonMap.put("productIntegral", "" +
						 * getIntegral(Double.parseDouble(DiscountTotalPrice),
						 * rates[0], a.get(0).get("ProductType"))); } else {
						 * jsonMap.put("productIntegral", ""); }
						 */

						Map<String, String> ProductIDMap = new HashMap<String, String>();
						ProductIDMap.put(riskcode, DiscountTotalPrice);

						Map<String, Map<String, String>> resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap,"", channelSn);
						Map<String, String> productDetail = resultProductDetail.get(riskcode);

						String rAmount = productDetail.get("Amount");
						String rIntegral = productDetail.get("Integral");
						String PointAmount = productDetail.get("PointAmount");
						BigDecimal CurrentValidatePoint=new BigDecimal("0");
						Member loginMember = getLoginMember();
						String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
						if(loginMember!=null){
							CurrentValidatePoint=new BigDecimal(loginMember.getCurrentValidatePoint());
							
							BigDecimal pointValue = CurrentValidatePoint.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
							BigDecimal points=new BigDecimal(PointAmount);
							if(points.compareTo(pointValue)>0){
								PointAmount=String.valueOf(pointValue);
							}
						}
						// 网站活动折扣前保费
						BigDecimal prem = new BigDecimal(0);
						if (StringUtil.isNotEmpty(DiscountTotalPrice)) {
							prem = new BigDecimal(DiscountTotalPrice);
						}

						// 网站活动折扣后保费
						BigDecimal nowPrem = new BigDecimal(0);
						if (StringUtil.isNotEmpty(rAmount)) {
							nowPrem = new BigDecimal(rAmount);
						}
						// 有折扣的情况 原价显示保险公司折后价格
						if (prem.compareTo(nowPrem) == 1) {
							jsonMap.put("productRatePrem", DiscountTotalPrice);
						}

						// 折后价
						jsonMap.put("productPrem", rAmount);
						// 高倍积分
						DiscountTotalPrice = rIntegral;
						jsonMap.put("loginFlag", "false");
						jsonMap.put("pointDesFlag", "false");
						String baseIntegral = nowPrem.multiply(new BigDecimal(PointScalerUnit)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
						
						if (loginMember != null  && Double.parseDouble(DiscountTotalPrice) >0 ) {
							String productGivePointsPercent = productDetail.get("pointrate");//产品赠送积分比例
							//会员等级
							Map<String,String>  map_result_grade=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BUY,loginMember.getId(), baseIntegral,productGivePointsPercent);
							String flag_grade=map_result_grade.get("flag");
							//String point_result=map_result_grade.get("points");
							String MemberGrade=map_result_grade.get("MemberGrade");
							if("true".equals(flag_grade)){
								DiscountTotalPrice=String.valueOf(Integer.parseInt(DiscountTotalPrice) + Integer.parseInt(map_result_grade.get("addpoints")));
								jsonMap.put("pointDesFlag", "true");
								jsonMap.put("pointDes", "您是"+MemberGrade+"会员,投保成功将获得 "+DiscountTotalPrice+" 个积分");
							}
							//会员生日月
							Map<String,String>  map_result=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BIRTH_MONTH,loginMember.getId(), baseIntegral,productGivePointsPercent);
							String flag=map_result.get("flag");
							MemberGrade=map_result.get("MemberGrade");
							if("true".equals(flag)){
								DiscountTotalPrice=String.valueOf(Integer.parseInt(DiscountTotalPrice) + Integer.parseInt(map_result.get("addpoints")));
								jsonMap.put("pointDesFlag", "true");
								jsonMap.put("pointDes", MemberGrade+"会员生日月享特权,投保成功特别赠送 "+DiscountTotalPrice+" 个积分");
							}
							jsonMap.put("loginFlag", "true");
						}

						// 获得积分的数量用现价进行换算 一元得一积分，不满一元不送积分
						if (DiscountPrice != null && DiscountPrice.length > 0) {
							jsonMap.put("productIntegral", String.valueOf((int) Double.parseDouble(DiscountTotalPrice)));
							// 最多抵扣积分
							jsonMap.put("IntePointAmountgral", String.valueOf(Double.parseDouble(PointAmount)));
							jsonMap.put("MemberAmountgral", String.valueOf(CurrentValidatePoint));
						}
					}
				}

			}
		} catch (Exception e) {
			jsonMap.put("status", "0");
			jsonMap.put("productPrem", "0");// 现价
			jsonMap.put("productRatePrem", "0");// 原价
			jsonMap.put("productIntegral", "0");
			jsonMap.put("msg", "保费计算失败");
			logger.error("保费计算失败：" + e.getMessage(), e);
		}

		if ("point".equals(pointproducttype)) {
			jsonMap.put("status", "3");

			Object productRatePrem = jsonMap.get("productRatePrem");
			Object productPrem = jsonMap.get("productPrem");
			
			String productprem = "";
			if (!StringUtil.isEmpty(productRatePrem)) {
				productprem = productRatePrem + "";

			} else {
				productprem = productPrem + "";

			}
			
			jsonMap.put("productPrem", new BigDecimal(Double.parseDouble(productprem) * Double.parseDouble(Config.getValue("PointScalerUnit"))).setScale(0, BigDecimal.ROUND_UP).toString());
			
			QueryBuilder qb = new QueryBuilder("SELECT LastNum FROM `GiftClassify`  WHERE productid=? and status='0' and UNIX_TIMESTAMP(DATE_FORMAT(startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S'))" , riskcode);
			Object lashnum = qb.executeOneValue();
			if (StringUtil.isEmpty(lashnum)) {
				lashnum = "0";
			}
			jsonMap.put("productLashNum",  lashnum + "");
		}

		if ("Y".equalsIgnoreCase(complicatedFlag)) {
			if (StringUtil.isNotEmpty(warnMessage)) {
				jsonMap.put("msg", warnMessage);
			}
			return ajaxHtml(JSONObject.fromObject(jsonMap).toString());
		}
		String jsonpname = request.getParameter("prem_callback");
		return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
	}

	private CalProductPrem[] changeAotuPeriod(CalProductPrem[] calProductPrem) throws Exception {
		for (CalProductPrem cal : calProductPrem) {
			for (FEMRiskFactorList fEMRiskFactorList : cal.getFEMRiskFactorList()) {
				if ("Period".equals(fEMRiskFactorList.getFactorType())) {
					String periodvalue = fEMRiskFactorList.getFactorValue();
					if (periodvalue.endsWith("@")) {
						fEMRiskFactorList.setFactorValue(PremCalculate.getPeriod(periodvalue, cal.getRiskCode()));
					}
					return calProductPrem;
				}
			}
		}
		return calProductPrem;
	}

	/**
	 * 调用积分
	 * 
	 * @param tPrem
	 * @param tRate
	 * @param productType
	 * @return
	 */
	private int getIntegral(double tPrem, double tRate, String productType) {
		try {
			if (StringUtil.isEmpty(productType)) {
				logger.info("调用积分规则险类productType：{}", productType);
				return 0;
			}

			IntegralAction iA = new IntegralAction();
			int result = iA.paySuccess(tRate, tPrem, productType);
			Object[] argArr = {tRate, tPrem, productType, result};
			logger.info("FeeRate(初始手续费率):{} - InitPrem(初始定价):{}险类:{} 积分：{}", argArr);
			return result;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * 查询改版产品列表页的活动
	 * @param product_id
	 * @param channelsn
	 * @return
	 */
	public LinkedMap searchProductListAvtivityRe(String product_id, String channelsn, String discountRate) {
		LinkedMap map = new LinkedMap();
		if (StringUtil.isEmpty(product_id)) {
			logger.error("产品：{} 的列表页的SalesV_....的Article.RiskCode值为空！", this.getRequest().getHeader("REFERER"));
			return null;
		}
		String[] params = product_id.split(",");
		String memberid ="";
		try{
			memberid = (String) ActionContext.getContext().getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		} catch (Exception e) {
			memberid="";
		}
		for (int i = 0; i < params.length; i++) {
			if (!StringUtil.checkID(product_id)) {
				logger.error("{}列表页查询活动信息的传递主键发生错误！", product_id);
				return null;
			}
			// 查询此产品参加的满减活动信息
//			QueryBuilder qb = new QueryBuilder(
//					"SELECT DISTINCT type as type,activitysn,title,description,GroupbuyWhether FROM sdcouponactivityinfo ac  WHERE ac.channelsn LIKE '%"
//							+ channelsn
//							+ "%' AND  ((FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0 and ac.insurancecompany='' ) OR (FIND_IN_SET(?,ac.insurancecompany)!=0  and ac.riskcode='' ) OR ( ac.riskcode='' and ac.insurancecompany='' and  ac.product='') or ( (FIND_IN_SET(?,ac.insurancecompany)!=0)  and (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0) ) OR(   FIND_IN_SET(?, ac.product) != 0  ) )  AND  ac.status='3' AND TYPE IN ('8','6', '3', '2', '1', '7')  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) GROUP BY TYPE ORDER BY FIND_IN_SET(TYPE,'8,6,3,2,1,7') limit 2");
//			qb.add(product_id);
//			qb.add(product_id.substring(0, 4));
//			qb.add(product_id.substring(0, 4));
//			qb.add(product_id);
//			qb.add(product_id);
			StringBuffer sql = new StringBuffer(" select DISTINCT s2.type as type,s2.activitysn,s2.title,s2.prop2,s2.GroupbuyWhether,s2.memberchannel ");
			sql.append(" from sdproductactivitylink s1,sdcouponactivityinfo s2 ");
			sql.append(" where s1.activitychannel =? and  s1.productid = ? ");
			sql.append(" and s1.activitysn=s2.activitysn AND  s2.status='3' AND TYPE IN ('8','6', '3', '2', '1', '7')  ");
			sql.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(s2.starttime,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) ");
			sql.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(s2.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) ORDER BY FIND_IN_SET(TYPE,'8,6,3,2,1,7') limit 2 ");
			QueryBuilder qb = new QueryBuilder(sql.toString());
			qb.add(channelsn);
			qb.add(product_id);
			
			DataTable dt = qb.executeDataTable();
			//自定义活动、团购、折扣、满减、高倍积分活动、满送、买送
			if (dt != null && dt.getRowCount() > 0) {
				for (int j = 0; j < dt.getRowCount(); j++) {
					String tType = dt.getString(j, "type");
					String GroupbuyWhether = dt.getString(j, "GroupbuyWhether");
					String description = dt.getString(j, "prop2"); // 使用prop2活动简述字段
					if ("8".equals(tType)) {// 自定义活动
						String title = dt.getString(j, "title");
						if (title.length() > 2) {
							title = title.substring(0, 2);
						}
						map.put(params[i] + "@" + tType, title + "@" + description);
						break;
					} else if ("1".equals(GroupbuyWhether)) {
						map.put(params[i] + "@x", "团购@" + description);
						break;
					} else if (!"6".equals(tType)) {
						if ("7".equals(tType)) {
							map.put(params[i] + "@" + tType, "多返@" + description);
							break;
						} else if ("1".equals(tType)) {
							map.put(params[i] + "@" + tType, "满送@" + description);
							break;
						} else if ("2".equals(tType)) {
							map.put(params[i] + "@" + tType, "买送@" + description);
							break;
						} else if (StringUtil.isNotEmpty(discountRate)) {
							map.put(params[i] + "@6", Double.parseDouble(discountRate)*10 + "折@");
							break;
						} else if ("3".equals(tType)) {
							map.put(params[i] + "@" + tType, "满减@" + description);
							break;
						}
					} else {
						if(StringUtil.isNotEmpty(dt.getString(j, "MemberChannel")) && "Y".equals(dt.getString(j, "MemberChannel"))){//含有会员渠道拆分
							if(StringUtil.isEmpty(memberid)){//未登录显示折扣 登录显示几折
								map.put(params[i] + "@" + tType, "折扣@" + description);
							}else{
								QueryBuilder disqb = new QueryBuilder("SELECT TRUNCATE(ActivityData,1) FROM SdActivityRule WHERE ActivitySn=? ");
								disqb.append(" AND memberrule =(SELECT (CASE WHEN vipflag='Y' THEN 'VIP' ELSE grade END)AS grade FROM member WHERE id=? )");
								disqb.add(dt.getString(j, "activitysn"));
								disqb.add(memberid);
								map.put(params[i] + "@" + tType, disqb.executeString() + "折@" + description);
							}
					    }else{//没有会员渠道拆分走老逻辑
							QueryBuilder disqb = new QueryBuilder("SELECT TRUNCATE(ActivityData,1) FROM SdActivityRule WHERE ActivitySn=? ");
							disqb.add(dt.getString(j, "activitysn"));
							map.put(params[i] + "@" + tType, disqb.executeString() + "折@" + description);
					    }
						break;
					}
					
				}
//			} else if (StringUtil.isNotEmpty(discountRate)) {
//				map.put(params[i] + "@6", Double.parseDouble(discountRate)*10 + "折@");
			}
			
		}
		
		return map;
	}
	
	/**
	 * 
	 * @Title: searchProductListActivity
	 * @Description: TODO(查询产品列表页的满减活动)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	public LinkedMap searchProductListAvtivity(String product_id, String channelsn) {
		LinkedMap map = new LinkedMap();
		if (StringUtil.isEmpty(product_id)) {
			logger.error("产品：{} 的列表页的SalesV_....的Article.RiskCode值为空！", this.getRequest().getHeader("REFERER"));
			return null;
		}
		String[] params = product_id.split(",");
		String activityinfo = new String("<span  class=\"shop_activity activity1\" >");
		for (int i = 0; i < params.length; i++) {
			if (!StringUtil.checkID(product_id)) {
				logger.error("{}列表页查询活动信息的传递主键发生错误！", product_id);
				return null;
			}
			// 查询此产品参加的满减活动信息
			QueryBuilder qb = new QueryBuilder(
					"SELECT DISTINCT type as type,activitysn,title,description,GroupbuyWhether FROM sdcouponactivityinfo ac  WHERE ac.channelsn LIKE '%"
							+ channelsn
							+ "%' AND  ((FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0 and ac.insurancecompany='' ) OR (FIND_IN_SET(?,ac.insurancecompany)!=0  and ac.riskcode='' ) OR ( ac.riskcode='' and ac.insurancecompany='' and  ac.product='') or ( (FIND_IN_SET(?,ac.insurancecompany)!=0)  and (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0) ) OR(   FIND_IN_SET(?, ac.product) != 0  ) )  AND  ac.status='3' AND TYPE IN ('8','6', '3', '2', '1', '7')  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) GROUP BY TYPE ORDER BY FIND_IN_SET(TYPE,'8,6,3,2,1,7') limit 2");
			qb.add(product_id);
			qb.add(product_id.substring(0, 4));
			qb.add(product_id.substring(0, 4));
			qb.add(product_id);
			qb.add(product_id);
			DataTable dt = qb.executeDataTable();
			//自定义活动、团购、折扣、满减、高倍积分活动、满送、买送
			for (int j = 0; j < dt.getRowCount(); j++) {
				String tType = dt.getString(j, "type");
				String GroupbuyWhether = dt.getString(j, "GroupbuyWhether");
				if ("8".equals(tType)) {
					String title = dt.getString(j, "title").substring(0, 2);
					String description = dt.getString(j, "description");
					map.put(params[i] + "@" + tType, title + "@" + description);
					
				}else if("1".equals(GroupbuyWhether)){
					activityinfo = activityinfo + "团购</span>";
					map.put(params[i] + "@" + tType, activityinfo);
					break;
					
				}else if ("6".equals(tType)) {
					QueryBuilder disqb = new QueryBuilder("SELECT TRUNCATE(ActivityData,1) FROM SdActivityRule WHERE ActivitySn=? ");
					disqb.add(dt.getString(j, "activitysn"));
					activityinfo = activityinfo + disqb.executeString() + "折</span>";
					map.put(params[i] + "@" + tType, activityinfo);
					break;
				}else if ("3".equals(tType)) {
					activityinfo = activityinfo + "满减</span>";
					map.put(params[i] + "@" + tType, activityinfo);
					break;
				}else if ("7".equals(tType)) {
					activityinfo = activityinfo + "积分</span>";
					map.put(params[i] + "@" + tType, activityinfo);
					break;
				}else if ("1".equals(tType)) {
					activityinfo = activityinfo + "满送</span>";
					map.put(params[i] + "@" + tType, activityinfo);
					break;
				}else if ("2".equals(tType)) {
					activityinfo = activityinfo + "买送</span>";
					map.put(params[i] + "@" + tType, activityinfo);
					break;
				}
			}
		}
		return map;
	}
}