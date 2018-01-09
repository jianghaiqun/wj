package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.service.SDOrderService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskBrightSpotList;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class WapBidAction extends BaseShopAction {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SDOrderService sdorderService;
	private String Callback;
	
	public String getCallback() {
		return Callback;
	}
	public void setCallback(String callback) {
		Callback = callback;
	}

	//渠道编码
	private String channelSn="";
	public String getChannelSn() {
		return channelSn;
	}
	public void setChannelSn(String channelSn) {
		this.channelSn = channelSn;
	}
	
	public String getProductContentHtml(String articleId) {
		
		Map<String, String> map = new HashMap<String, String>();
		ZCArticleSchema tartilce = new ZCArticleSchema();
		tartilce.setID(articleId);
		if (tartilce.fill()) {
			long siteId = tartilce.getSiteID();
			ZCSiteSchema zcSite = new ZCSiteSchema();
			zcSite.setID(siteId);
			if (zcSite.fill()) {
				map.put("Url", zcSite.getURL());
				map.put("WapURL", zcSite.getWapURL());
			}
		}
		
		StringBuffer contentHtml = new StringBuffer();
		// 取得
		String sql = "SELECT ID, MenuName FROM SDWapBidMenu WHERE DocumentId = ? ORDER BY OrderFlag ";
		DataTable dt = new QueryBuilder(sql, articleId).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			contentHtml.append("<ul class=\"member_tab\">\r\n");
			int count = dt.getRowCount();
			String width = "";
			String width1 = "";
			if (count == 2) {
				 width = "style=\"width:50%\"";
				 width1 = "width:200%;";
			} else if (count == 1) {
				width = "style=\"width:100%\"";
				width1 = "width:100%;";
			}
			for (int i = 0; i < count; i++) {
				if (i == 0) {
					contentHtml.append("<li "+width+"><label class=\"check\">"+dt.getString(i, 1)+"</label><div class=\"tab-content\" id=\"tab-content"+(i+1)+"\" style=\"display: block;"+width1+"\">");
				} else {
					contentHtml.append("<li "+width+"><label class=\"\">"+dt.getString(i, 1)+"</label><div class=\"tab-content\" id=\"tab-content"+(i+1)+"\" style=\"display: none;"+width1+"\">");
				}
				// 取得产品信息
				contentHtml.append(getProductInfo(dt.getString(i, 0), map, (i+1)));
					
				contentHtml.append("</div></li>\r\n");
			}
			
			contentHtml.append("</ul>");
		}
		return contentHtml.toString();
	}
	
	private String getProductInfo(String id, Map<String, String> map, int index) {
		StringBuffer html = new StringBuffer();
		String sql = " SELECT wp.ID, wp.WapBidMenuId, wp.ProductId, wp.WapProductName, wp.OrderFlag, "
				+ " p.ProductID, p.ProductName, p.HtmlPath, p.Remark6, l.Detail, p.SectionAge, p.Remark2, p.Remark4 "
				+ " FROM sdWapBidProduct wp "
				+ " INNER JOIN sdProduct p ON wp.ProductId = p.ProductID "
				+ " LEFT JOIN sdProductHighlights l ON  l.CreateDate = "
				+ " (SELECT MIN(CreateDate) FROM sdProductHighlights l WHERE l.ProductID = wp.ProductId) "
				+ " WHERE WapBidMenuId = ? ORDER BY OrderFlag ";
		DataTable dt = new QueryBuilder(sql, id).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			String wapStaticUrl=Config.getValue("WapStaticResourceUrl");
			String productId = "";
			html.append("<ul class=\"list_show\">");
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				productId = dt.getString(i, "ProductId");
				// 获取产品中心端产品数据
				String[] riskCode = {productId};
				Map<String,Object> paramter = new HashMap<String,Object>();
				paramter.put("RiskCode", riskCode);
				//paramter.put("BU1", "N");//判断是否是赠险
				ProductInfoWebServiceStub.FMRisk[] fm = null;
				try{
					ProductInfoWebServiceStub.ProductInfoResponse prifr = ProductWebservice.ProductInfoSereviceImpl(paramter, null);
					fm = prifr.getFMRisk();
				}catch(Exception e){
					logger.error(productId+"获取产品中心端产品数据失败！" + WapBidAction.class.getName() + e.getMessage(), e);
				}
				
				html.append("<li>");
				html.append("<div class=\"title_area\">");
				html.append("<p class=\"ttl_text\"><a href=\""+dt.getString(i, "HtmlPath").replace(map.get("Url"), map.get("WapURL"))+"\">");
				html.append("<b id=\"producttitle_"+index+"_"+productId+"\">"+dt.getString(i, "ProductName")+"</b></a></p>");
				html.append("<p class=\"pro_logo\"><span ><img alt=\"\" src=\""+wapStaticUrl+"/images/companylogo/"+dt.getString(i, "Remark6")+".gif\">");
				html.append("</span></p></div>");
				html.append("<div class=\"item_list\"><dl class=\"item_cont\">");
				html.append("<dt><span class=\"icon_01\"></span>产品特色：</dt>");
				// 取得产品特色
				if (!StringUtil.isEmpty(dt.getString(i, "Detail"))){
					html.append("<dd>"+dt.getString(i, "Detail")+"</dd>");
				}else{
					// 产品中心获取
					if (null != fm && fm.length > 0) {
						FEMRiskBrightSpotList[] femRiskBrightSpotList = fm[0]
								.getFEMRiskBrightSpotList();
						if (null != femRiskBrightSpotList
								&& femRiskBrightSpotList.length > 0
								&& null != femRiskBrightSpotList[0]) {
							html.append("<dd>"+femRiskBrightSpotList[0].getBrightSpotName()+"</dd>");
						}
					}
				}
				// 承保年龄
				html.append("<dt><span class=\"icon_02\"></span>承保年龄：</dt>");
				html.append("<dd>"+this.getSectionAge(dt.getString(i, "SectionAge"))+"</dd>");
				// 保障期限
				html.append("<dt><span class=\"icon_03\"></span>保障期限：</dt>");
				html.append("<dd>"+this.getTerm(dt.getString(i, "Remark2"))+"</dd>");
				html.append("<dt><span class=\"icon_04\"></span>保障项目：</dt>");
				html.append("</dl></div>");
				// 保障项目
				html.append("<div class=\"prix_list\"><ul class=\"prix_cont\">");
				String dutyHtml = getDutyHtml(productId);
				if(!StringUtil.isEmpty(dutyHtml)){
					html.append(dutyHtml);
				}else{
					// 产品中心获取
					if (null != fm && fm.length > 0) {
						ProductInfoWebServiceStub.FMDuty[] duty = fm[0]
								.getFMDuty();
						if (duty.length > 0) {
							html.append(getProductCoreDuty(duty));
						}
					}
				}
				html.append("</ul></div>");
				
				// 价格
				html.append("<div class=\"item_list\">");
				html.append("<dl class=\"item_cont item_color\">");
				html.append("<dt><span class=\"icon_05\"></span>价格：</dt>");
				html.append("<dd ><i class=\"pay_yj\" style=\"display:none\">原价:￥"+dt.getString(i, "Remark4")+"</i><span id=\"new_price_"+index+"_" + productId + "\"></span></dd>");
				html.append("</dl></div>");
				html.append("</li>");
			}
			html.append("</ul>");
		}
		
		return html.toString();
	}
	
	public String getProdcutContentHtml(String articleId) {

		// WAP站URL
		String WapURL = "";
		// WAP静态资源URL
		String WapResourceURL = "";
		// 主站静态资源URL
		String StaticResourceURL = "";

		ZCArticleSchema tartilce = new ZCArticleSchema();
		tartilce.setID(articleId);
		if (tartilce.fill()) {
			long siteId = tartilce.getSiteID();
			ZCSiteSchema zcSite = new ZCSiteSchema();
			zcSite.setID(siteId);
			if (zcSite.fill()) {
				WapURL = zcSite.getWapURL();
				WapResourceURL = zcSite.getWapResourceURL();
				StaticResourceURL = zcSite.getStaticResourcePath();
			}
		}

		String contentHtml = "";
		String sql = "SELECT ID, MenuName, Description, OrderFlag FROM SDWapBidMenu WHERE DocumentId = '"
				+ articleId + "' ORDER BY OrderFlag ";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		if (dt.getRowCount() > 0) {
			// 标题菜单
			if (dt.getRowCount() > 1) {
				contentHtml = "<div class=\"info-nav\">" + "\r\n";
				for (int i = 0; i < dt.getRowCount(); i++) {
					contentHtml += "<div id=\"productList" + i + "\"";
					if (0 == i) {
						contentHtml += " onclick=\"showProdInfo(this.id)\" class=\"producted hover\">";
					} else {
						contentHtml += " onclick=\"showProdInfo(this.id)\" class=\"producted\">";
					}

					contentHtml += dt.getString(i, 1) + "</div>" + "\r\n";
				}
				contentHtml += "</div>" + "\r\n";
			}

			// 产品内容
			for (int i = 0; i < dt.getRowCount(); i++) {
				contentHtml += "<div id=\"productList" + i + "c\"";
				if (0 == i) {
					contentHtml += " class=\"content-show\">" + "\r\n";
				}else{
					contentHtml += " class=\"content-show\" style=\"display: none;\">" + "\r\n";
				}
				
				contentHtml += "<div class=\"product-panel\">" + "\r\n";
				contentHtml += "<div>" + "\r\n";

				String sqlP = " SELECT wp.ID, wp.WapBidMenuId, wp.ProductId, wp.WapProductName, wp.OrderFlag, "
						+ " p.ProductID, p.ProductName, p.Remark6, l.Detail, p.SectionAge, p.Remark2, p.Remark4 "
						+ " FROM sdWapBidProduct wp "
						+ " INNER JOIN sdProduct p ON wp.ProductId = p.ProductID "
						+ " LEFT JOIN sdProductHighlights l ON  l.CreateDate = "
						+ " (SELECT MIN(CreateDate) FROM sdProductHighlights l WHERE l.ProductID = wp.ProductId) "
						+ " WHERE WapBidMenuId = '"
						+ dt.getString(i, 0)
						+ "' ORDER BY OrderFlag ";
				DataTable dtP = new QueryBuilder(sqlP).executeDataTable();
				if (dtP.getRowCount() > 0) {
					for (int j = 0; j < dtP.getRowCount(); j++) {
						String productId = dtP.getString(j, 5);
						
						// 获取产品中心端产品数据
						String[] riskCode = {productId};
						Map<String,Object> paramter = new HashMap<String,Object>();
						paramter.put("RiskCode", riskCode);
						//paramter.put("BU1", "N");//判断是否是赠险
						ProductInfoWebServiceStub.FMRisk[] fm = null;
						try{
							ProductInfoWebServiceStub.ProductInfoResponse prifr = ProductWebservice.ProductInfoSereviceImpl(paramter, null);
							fm = prifr.getFMRisk();
						}catch(Exception e){
							logger.error(productId+"获取产品中心端产品数据失败！" + WapBidAction.class.getName() + e.getMessage(), e);
						}
						
						contentHtml += "<div class=\"filter-list-show\">"
								+ "\r\n";
						contentHtml += "<div class=\"ltd clearfix\">" + "\r\n";
						// 产品公司logo
						contentHtml += "<div class=\"title_left\"><img width=\"190\" height=\"190\" src=\""
								+ StaticResourceURL
								+ "/images/productimages/"
								+ dtP.getString(j, 7) + ".jpg\"></div>";
						// logo名称
						if (!StringUtil.isEmpty(dtP.getString(j, 3))) {
							contentHtml += "<div class=\"text_left\"><p class=\"introTxt\">" 
									+ dtP.getString(j, 3) + "</p></div>";
						}
						contentHtml += "<div class=\"right\">" + "\r\n";
						// 产品名称+链接
						String productDetailLink = WapURL
								+ "/mobile/list-show.html?ProdCode="
								+ productId;
						contentHtml += "<a href=\"" + productDetailLink
								+ "\"><span class=\"ttl\">"
								+ dtP.getString(j, 6) + "</span></a>" + "\r\n";
						
						// 产品活动
						contentHtml += "<p id=\"activity_" + productId +"\" class=\"activity_" + productId +"\"></p>"
								+ "\r\n";
						contentHtml += "</div></div>" + "\r\n";
						// 产品特色
						String productLight = "";
						
						contentHtml += "<div class=\"list clearfix\">" + "\r\n";
						contentHtml += "<div class=\"left\">产品特色：</div>"
								+ "\r\n";
						if (!StringUtil.isEmpty(dtP.getString(j, 8))){
							productLight = dtP.getString(j, 8);
						}else{
							// 产品中心获取
							if (null != fm && fm.length > 0) {
								FEMRiskBrightSpotList[] femRiskBrightSpotList = fm[0]
										.getFEMRiskBrightSpotList();
								if (null != femRiskBrightSpotList
										&& femRiskBrightSpotList.length > 0
										&& null != femRiskBrightSpotList[0]) {
									productLight = femRiskBrightSpotList[0].getBrightSpotName();
								}
							}
						}
						contentHtml += "<span>" + productLight
								+ "</span>" + "\r\n";
						contentHtml += "</div>" + "\r\n";
						// 承保年龄
						contentHtml += "<div class=\"list clearfix\">" + "\r\n";
						contentHtml += "<div class=\"left\">承保年龄：</div>"
								+ "\r\n";
						contentHtml += "<span>"
								+ this.getSectionAge(dtP.getString(j, 9))
								+ "</span>" + "\r\n";
						contentHtml += "</div>" + "\r\n";
						// 承保期限
						contentHtml += "<div class=\"list clearfix\">" + "\r\n";
						contentHtml += "<div class=\"left\">承保期限：</div>"
								+ "\r\n";
						contentHtml += "<span>" + this.getTerm(dtP.getString(j, 10))
								+ "</span>" + "\r\n";
						contentHtml += "</div>" + "\r\n";
						// 保障项目
						contentHtml += "<div class=\"list clearfix\">" + "\r\n";
						contentHtml += "<div class=\"left\">保障项目：</div>"
								+ "\r\n";
						contentHtml += "<div class=\"right\">" + "\r\n";
						contentHtml += "<ul>" + "\r\n";
						String productDuty = "";
						String dutyHtml = getDutyHtml(productId);
						if(!StringUtil.isEmpty(dutyHtml)){
							productDuty = dutyHtml;
						}else{
							// 产品中心获取
							if (null != fm && fm.length > 0) {
								ProductInfoWebServiceStub.FMDuty[] duty = fm[0]
										.getFMDuty();
								if (duty.length > 0) {
									productDuty = getProductCoreDuty(duty);
								}
							}
						}
						contentHtml += productDuty;
						contentHtml += "</ul></div></div>" + "\r\n";

						// 价格
						contentHtml += "<div class=\"list clearfix\">" + "\r\n";
						contentHtml += "<div class=\"left\">价格：</div>" + "\r\n";
						contentHtml += "<div class=\"right\">" + "\r\n";
						contentHtml += "<div class=\"count-price03\"><span id=\"new_price_" + productId + "\" class=\"new_price_" + productId + "\">"
								+ "</span>起</div>" + "\r\n";
						contentHtml += "<div class=\"count-price02 new_price_" + productId + "\" style=\"display:none;\"><span>"
								+ "￥" + dtP.getString(j, 11) + "</span></div>"
								+ "\r\n";
						contentHtml += "</div></div>" + "\r\n";
						// 查看详情
						contentHtml += "<div class=\"btnArea\">" + "\r\n";
						contentHtml += "<div class=\"ui-btn\">" + "\r\n";
						contentHtml += "<a href=\"" + productDetailLink + "\">"
								+ "\r\n";
						contentHtml += "<div class=\"ui-btn-content\">查看详情"
								+ "\r\n";
						contentHtml += "</div></a></div></div>" + "\r\n";
						contentHtml += "</div>" + "\r\n";
					}
				}

				contentHtml += "</div></div></div>" + "\r\n";
			}
		}

		return contentHtml;
	}

	/**
	 * 获取产品的最小年龄与最大年龄
	 * 
	 * @param SectionAge
	 *            数据库查询的值
	 * @return
	 */
	private String getSectionAge(String sectionAge) {
		String sectionAgeStr = "";
		if (!StringUtil.isEmpty(sectionAge)) {
			String beginAge = "";
			String endAge = "";
			String[] sectionAgeArr = sectionAge.split("\\|");
			// 18Y-50Y|51Y-60Y 或者 0Y|1Y情况
			if (sectionAgeArr.length > 1) {
				String[] beginAgeArr = sectionAgeArr[0].split("-");
				if (beginAgeArr.length > 1) {
					beginAge = beginAgeArr[0];
				} else {
					beginAge = sectionAgeArr[0];
				}
				String[] endAgeArr = sectionAgeArr[sectionAgeArr.length - 1]
						.split("-");
				if (beginAgeArr.length > 1) {
					endAge = endAgeArr[endAgeArr.length - 1];
				} else {
					endAge = sectionAgeArr[sectionAgeArr.length - 1];
				}
			}
			// 18Y-50Y 或者 1Y
			else {
				String[] ageArr = sectionAge.split("-");
				if (ageArr.length > 1) {
					beginAge = ageArr[0];
					endAge = ageArr[1];
				} else {
					beginAge = sectionAge;
				}
			}
			sectionAgeStr = replace(beginAge + (endAge != "" ? "-" + endAge : ""));
		}
		return sectionAgeStr;
	}

	/**
	 * 年月日替换
	 * 
	 * @param sectionAgeStr
	 * @return
	 */
	private String replace(String sectionAgeStr) {
		Map<String, String> en_zh = new HashMap() {
			{
				put("Y", "周岁");
				put("M", "月");
				put("D", "天");
			}
		};
		Iterator it = en_zh.keySet().iterator();
		while (it.hasNext()) {
			String en = (String) it.next();
			sectionAgeStr = sectionAgeStr.replace(en, en_zh.get(en));
		}
		return sectionAgeStr;
	}

	/**
	 * 获取产品的产品的最小天数与最大天数
	 * 
	 * @param term
	 *            数据库查询的值
	 * @return
	 */
	private String getTerm(String term) {
		String termStr = "";

		if (!StringUtil.isEmpty(term)) {
			String[] termArr = term.split("-");
			if (termArr.length > 0) {
				termStr = termArr[0] + "天" + "-" + termArr[1] + "天";
			}
		}
		return termStr;
	}

	/**
	 * 特色保障
	 * 
	 * @param term
	 *            数据库查询的值
	 * @return
	 */
	private String getDutyHtml(String productId) {

		String sql = "SELECT DutyHtmlWap FROM sdProductDuty WHERE ProductId = '"
				+ productId + "' order by OrderBy";
		
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		String dutyHtml = "";
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				dutyHtml += dt.getString(i, 0);
			}
		}

		return dutyHtml;
	}
	
	/**
	 * 产品中心获取责任html
	 * 
	 * @param duty
	 * @return
	 */
	private String getProductCoreDuty(ProductInfoWebServiceStub.FMDuty[] duty){
		String dutyHtml = "";
		if (duty.length > 0) {
			for (int i = 0; i < duty.length; i++) {
				dutyHtml += "<li><span class=\"intro_l\">" + duty[i].getDutyName() + "</span>";
				FEMDutyFactor dutyFactor = duty[i].getFEMDutyFactor();
				FEMDutyAmntPremList[] AmntPremValues = dutyFactor
						.getFEMDutyAmntPremList();
				String defaultValue = "";
				if (AmntPremValues != null && AmntPremValues.length > 0
						&& AmntPremValues[0] != null) {
					defaultValue = AmntPremValues[0].getAmnt();
				} else {
					defaultValue = "-";
				}
				dutyHtml += "<span class=\"prix_r\">" + defaultValue + "</span></li>";
			}
		}

		return dutyHtml;
	}
	


	/**
	 * 
	* @Title: searchProductListActivity 
	* @Description: TODO(查询产品列表页的满减活动) 
	* @return String    返回类型 
	* @author zhangjing
	 */
	public String searchProductListAvtivity() {
		String productId = getParameter("productId");
		String memberId = getParameter("memberId");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(productId)) {
			logger.error("产品：{} 的Article.RiskCode值为空！", this.getRequest().getHeader("REFERER"));
			jsonMap.put("false","");
			return ajaxMap2Json(jsonMap);
		}
		String[] params = productId.split(",");
		try {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (int i = 0; i < params.length; i++) {
				Map<String, String> objMap = new HashMap<String, String>();
				String product_id = params[i].substring(params[i].lastIndexOf("_")+1);
				objMap.put("productId", params[i].substring(10));
				if (!StringUtil.checkID(product_id)) {
					logger.error("{}Wap页查询活动信息的传递主键发生错误！", productId.substring(10));
					jsonMap.put("false","");
					return ajaxMap2Json(jsonMap);
				}
				if(StringUtil.isEmpty(channelSn)){
					channelSn = "wap_ht";
				}
				
				// 刷新价格Action
				AjaxPriceAction ap = new AjaxPriceAction();
				String newPrice = ap.queryAjaxPrice(product_id, channelSn, memberId);

				objMap.put("newPrice", newPrice);
				
				//查询此产品参加的满减活动信息
				//QueryBuilder qb=new QueryBuilder("SELECT DISTINCT type as type,activitysn,title,description  FROM sdcouponactivityinfo ac  WHERE ac.channelsn LIKE '%"+channelSn+"%' AND  ((FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0 and ac.insurancecompany='' ) OR (FIND_IN_SET(?,ac.insurancecompany)!=0  and ac.riskcode='' ) OR ( ac.riskcode='' and ac.insurancecompany='' and  ac.product='') or ( (FIND_IN_SET(?,ac.insurancecompany)!=0)  and (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0) ) OR(   FIND_IN_SET(?, ac.product) != 0  ) )  AND  ac.status='3' AND TYPE IN ('8','6', '3', '2', '1', '7')  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) GROUP BY TYPE  ORDER BY FIND_IN_SET(TYPE,'8,6,3,2,1,7') limit 2");
				// 隐藏折扣、满减图标
				QueryBuilder qb=new QueryBuilder("SELECT DISTINCT type as type,activitysn,title,groupbuyWhether FROM sdcouponactivityinfo ac  WHERE ac.channelsn LIKE '%"+channelSn+"%' AND  ((FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0 and ac.insurancecompany='' ) OR (FIND_IN_SET(?,ac.insurancecompany)!=0  and ac.riskcode='' ) OR ( ac.riskcode='' and ac.insurancecompany='' and  ac.product='') or ( (FIND_IN_SET(?,ac.insurancecompany)!=0)  and (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0) ) OR(   FIND_IN_SET(?, ac.product) != 0  ) )  AND  ac.status='3' AND TYPE IN ('8', '6', '3', '2', '1', '7')  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) GROUP BY TYPE  ORDER BY FIND_IN_SET(TYPE,'8,6,3,2,1,7') ");
				
				qb.add(product_id);
				qb.add(product_id.substring(0,4));
				qb.add(product_id.substring(0,4));
				qb.add(product_id);
				qb.add(product_id);
				DataTable dt=qb.executeDataTable();
				//自定义活动
				//满减、满送、买送、高倍积分活动
				appendMapValue(objMap, "activity", "");
				
				for (int j = 0; j < dt.getRowCount(); j++) {
					
					String tType = dt.getString(j, "type");
					if ("1".equals(dt.getString(j, "groupbuyWhether"))) {
						appendMapValue(objMap, "activity","<span class=\"activity_100\"><em class=\"icon\"><b class=\"tick\"></b></em>团购</span>");
					} else if("8".equals(tType)){
						String title=dt.getString(j, "title");
						if(title.length()>2){
							title=title.substring(0,2);
						}
						appendMapValue(objMap, "activity","<span class=\"activity_8\"><em class=\"icon\"><b class=\"tick\"></b></em>"+title+"</span>");

					} else if("2".equals(tType)){
						// 买送
						appendMapValue(objMap, "activity","<span class=\"activity_2\"><em class=\"icon\"><b class=\"tick\"></b></em>买送</span>");
					} else if("1".equals(tType)){
						// 满送
						appendMapValue(objMap, "activity","<span class=\"activity_1\"><em class=\"icon\"><b class=\"tick\"></b></em>满送</span>");
					} else if("7".equals(tType)){
						// 高倍积分
						appendMapValue(objMap, "activity","<span class=\"activity_7\"><em class=\"icon\"><b class=\"tick\"></b></em>多返</span>");
					}else if("6".equals(tType)){
						// 折扣
						if("discount".equals(Config.getValue("discount"))){
						    appendMapValue(objMap, "activity","<span class=\"activity_6\"><em class=\"icon\"><b class=\"tick\"></b></em>折扣</span>");
						}
					} else if ("3".equals(tType)) {
						// 满减
						appendMapValue(objMap, "activity","<span class=\"activity_3\"><em class=\"icon\"><b class=\"tick\"></b></em>满减</span>");
					}
				}
				list.add(objMap);
			}
			jsonMap.put("objList", list);
			jsonMap.put("discount", Config.getValue("discount"));

		} catch (Exception e) {
			logger.error("产品："+this.getRequest().getHeader("REFERER")+" 的Article.RiskCode值为！"+productId + e.getMessage(), e);
		}
		return ajaxHtml(Callback + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
	}

	/**
	 * 
	 * @Title: appendMap
	 * @Description: (追加Map中的信息)
	 * @return void 返回类型
	 * @author
	 */
	private Map<String, String> appendMapValue(Map<String, String> map,
			String key, String appendValue) {
		String value = map.get(key);
		if (StringUtil.isEmpty(value)) {
			map.put(key, appendValue);
		} else {
			map.put(key, value + appendValue);
		}
		return map;
	}
}
