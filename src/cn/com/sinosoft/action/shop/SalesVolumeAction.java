package cn.com.sinosoft.action.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.document.RecommendToDetail;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.inter.ActivityCalculateDetail;
import com.sinosoft.jdt.cc.CCDealInterface;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.SalesVolumeService;
import cn.com.sinosoft.util.Constant;
import net.sf.json.JSONObject;

@ParentPackage("shop")
public class SalesVolumeAction extends BaseShopAction {

	@Autowired
	private SalesVolumeService salesVolumeService;

	/**
	 *
	 */
	private static final long serialVersionUID = -975907428768389791L;

	private String channelSn = "";// 渠道编码

	public String getChannelSn() {

		return channelSn;
	}

	public void setChannelSn(String channelSn) {

		this.channelSn = channelSn;
	}



	/**
	 * ajax方式检索销售量
	 *
	 * @return 销售量
	 */
	public String search() {
		long start = System.currentTimeMillis();

		String param = getParameter("productIds");
		if (StringUtil.isEmpty(param)) {
			return ajaxJson("");
		}
		String[] params = param.split(",");
		Map<String, String> jsonMap = new HashMap<String, String>();

		for (int i = 0; i < params.length; i++) {
			String productID = params[i].substring(7);
			try {
				String tSalesVolume = "0";
				tSalesVolume = String.valueOf(salesVolumeService.getSalesVolume(productID));

				// 如果大于1万，以“万”为单位显示
				if (StringUtil.isNotEmpty(tSalesVolume) && !"0".equals(tSalesVolume)) {
					double dSalesVolume = Double.valueOf(tSalesVolume);
					if (dSalesVolume >= 10000) {
						BigDecimal bigDecimal = new BigDecimal(dSalesVolume/10000);
						tSalesVolume = String.valueOf(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)) + "万";
					}
				}
				jsonMap.put(params[i], tSalesVolume);
			} catch (Exception e) {
				logger.error("查询销量失败！" + e.getMessage(), e);
				jsonMap.put(params[i], "0");
			}
		}
		long end = System.currentTimeMillis();
		logger.info("search耗时:{}", end - start);
		return ajaxJson(jsonMap);
	}

	public static String dealSalvesVolumn(String cProductID) {

		QueryBuilder qbsales = new QueryBuilder(
				"select a.SalesVolume,b.SplitRiskCode from sdsearchrelaproduct a,sdproduct b where a.productid=b.productid and a.ProductID = ?",
				cProductID);
		DataTable dtsales = qbsales.executeDataTable();
		String tSalesVolume = "0";
		if (dtsales != null && dtsales.getRowCount() >= 1) {
			String tSplitRiskCode = dtsales.getString(0, "SplitRiskCode");
			if (StringUtil.isNotEmpty(tSplitRiskCode)) {
				if (tSplitRiskCode.indexOf(",") != -1) {
					String[] src_plan = tSplitRiskCode.split(",");
					for (int j = 0; j < src_plan.length; j++) {
						if (src_plan[j].indexOf("-") != -1) {
							String[] src = src_plan[j].split("-");
							String cProductId = src[0];
							QueryBuilder qbchisales = new QueryBuilder(
									"select a.SalesVolume from sdsearchrelaproduct a where a.ProductID = ?", cProductId);
							DataTable dtchisales = qbchisales.executeDataTable();
							if (dtchisales != null && dtchisales.getRowCount() >= 1) {
								String cSalesVolume = dtchisales.getString(0, "SalesVolume");
								tSalesVolume = String.valueOf(Integer.parseInt(tSalesVolume)
										+ Integer.parseInt(cSalesVolume));
							}
						}
					}
				}
			} else {
				tSalesVolume = dtsales.getString(0, "SalesVolume");
			}
		}
		return tSalesVolume;
	}

	/**
	 * 产品评论
	 *
	 * @Title: comment
	 * @return String 返回类型
	 */
	public String comment() {

		String param = getParameter("productIds");
		if (StringUtil.isEmpty(param)) {
			return ajaxJson("");
		}
		String[] params = param.split(",");
		Map<String, String> jsonMap = new HashMap<String, String>();
		String productID = "";
		for (int i = 0; i < params.length; i++) {
			productID = params[i].substring(9);
			String commentCount = "0";
			try {
				commentCount = SearchAPI.getCommentCount(productID);
				if (StringUtil.isNotEmpty(commentCount)) {
					jsonMap.put(params[i], commentCount);
				} else {
					jsonMap.put(params[i], "0");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				jsonMap.put(params[i], "0");
			}
		}
		return ajaxJson(jsonMap);
	}

	/**
	 * 检索销售量
	 *
	 * @return 销售量
	 */
	public String search1(String params) {

		String result = "";

		if (StringUtil.isEmpty(params)) {
			return result;
		}
		String[] riskcodes = params.split(",");
		for (int i = 0; i < riskcodes.length; i++) {
			String productID = riskcodes[i];
			List<Map<String, String>> salesVolume;
			if (i != 0) {
				result += ",";
			}
			result += "SalesV_" + riskcodes[i] + ":";
			try {
				QueryBuilder qbsales = new QueryBuilder(
						"select a.SalesVolume,b.SplitRiskCode from sdsearchrelaproduct a,sdproduct b where a.productid=b.productid and a.ProductID = ?",
						productID);
				DataTable dtsales = qbsales.executeDataTable();
				if (dtsales != null && dtsales.getRowCount() >= 1) {
					String tSalesVolume = "0";
					String tSplitRiskCode = dtsales.getString(0, "SplitRiskCode");
					if (StringUtil.isNotEmpty(tSplitRiskCode)) {
						if (tSplitRiskCode.indexOf(",") != -1) {
							String[] src_plan = tSplitRiskCode.split(",");
							for (int j = 0; i < src_plan.length; i++) {
								if (src_plan[i].indexOf("-") != -1) {
									String[] src = src_plan[i].split("-");
									String cProductId = src[0];
									QueryBuilder qbchisales = new QueryBuilder(
											"select a.SalesVolume from sdsearchrelaproduct a where a.ProductID = ?",
											cProductId);
									DataTable dtchisales = qbchisales.executeDataTable();
									if (dtchisales != null && dtchisales.getRowCount() >= 1) {
										String cSalesVolume = dtchisales.getString(0, "SalesVolume");
										tSalesVolume = String.valueOf(Integer.parseInt(tSalesVolume)
												+ Integer.parseInt(cSalesVolume));
									}
								}
							}
						}
					}
					result += tSalesVolume;
				} else {
					result += "0";
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result += "0";
			}
		}
		return result;
	}

	public String queryProState() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String result = "N";
		Member member = getLoginMember();
		if(null==member){
			jsonMap.put("HtmlPath", "NoLogin");
			jsonMap.put("state", result);
			return ajaxJson(jsonMap);
		}
		String param = getParameter("orderSn");
		QueryBuilder qb = new QueryBuilder(
				"select p.isPublish,p.HtmlPath from sdproduct p, sdinformation i where i.orderSn = ? and p.ProductID = i.productId ");
		qb.add(param);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			result = dt.getString(0, 0);
			jsonMap.put("HtmlPath", dt.getString(0, 1));
		}
		jsonMap.put("state", result);

		return ajaxJson(jsonMap);
	}

	/**
	 * ajax方式检索销售量
	 *
	 * @return 销售量
	 */
	public String queryProductState() {

		Map<String, String> jsonMap = new HashMap<String, String>();

		String param = getParameter("productId");

		if (StringUtil.isEmpty(param)) {
			return ajaxJson("");
		}
		String memberId = "";
		Member member = getLoginMember();
		if (member != null) {
			memberId = member.getId();
		}
		try {
			QueryBuilder qb = new QueryBuilder(
					"select isPublish from sdproduct where ProductID = ?");

			qb.add(param);

			String result = (String) qb.executeOneValue();

			if ("N".equals(result)) {
				StringBuffer resultBuffer = new StringBuffer();
				RecommendToDetail recommendToDetail = new RecommendToDetail();
				DataTable dt = recommendToDetail.getRecomProductInfo(param);
				if (dt != null && dt.getRowCount() > 0) {
					int rowcount = dt.getRowCount();
					AjaxPriceAction ap = new AjaxPriceAction();
					String dutyHtml = "";

					for (int i = 0; i < rowcount; i++) {
						if (StringUtil.isNotEmpty(dt.getString(i, "DutyHTML2"))) {
							dutyHtml = dt.getString(i, "DutyHTML2").replace("rec_list_li", "").replace("rec_list_s", "")
									.replace("<span class=\"rec_list_money\">", "<em>")
									.replace("</span></li>", "</em></li>");
						}
						String newPrice = ap.queryAjaxPrice(dt.getString(i, "ProductID"), memberId);
						resultBuffer.append("<li><div class=\"icon_C" + dt.getString(i, "SupplierCode2") + " cp_logos\"></div>");
						resultBuffer.append("<p class=\"tit\"><a href=\"" + dt.getString(i, "URL") + "\" target=\"_blank\">"
								+ dt.getString(i, "ProductName") + "</a></p>");
						resultBuffer.append("<p class=\"txt\">适用人群：" + dt.getString(i, "AdaptPeopleInfo") + "</p>");
						resultBuffer.append("<ul class=\"info_list\">" + dutyHtml + "</ul>");
						resultBuffer.append("<p class=\"pri\"><em>￥" + newPrice + "</em><a href=\"" + dt.getString(i, "URL")
								+ "\" target=\"_blank\">去看看</a></p>");
						resultBuffer.append("</li>");
					}
				}

				jsonMap.put("product", resultBuffer.toString());

			}

			jsonMap.put("state", result);

			return ajaxJson(jsonMap);
		} catch (Exception e) {
			return ajaxJson("");
		}
	}

	/**
	 * 通过ID查询产品信息.
	 *
	 * @return
	 */
	public List<HashMap<String, String>> queryProduct(String productType,
			String price) {

		GetDBdata dBdata = new GetDBdata();

		List<HashMap<String, String>> resultList = null;

		StringBuffer sb = new StringBuffer();
		sb.append("select SQL_CACHE b.textvalue ");
		sb.append("from ");
		sb.append("zdcolumnvalue b, ");
		sb.append("zccatalog z1, ");
		sb.append("zcarticle z2 ");
		sb.append("where ");
		sb.append("b.relaid=z2.ID and ");
		sb.append("z1.id = z2.CatalogID and ");
		sb.append("z1.producttype is not null and z2.Status='30' and ");
		sb.append("b.columncode='RiskCode' and z1.producttype='" + productType
				+ "' ");

		try {
			List<HashMap<String, String>> queryResult = dBdata.query(sb
					.toString());

			if (queryResult != null && queryResult.size() > 0) {

				String tempparame = AjaxProduct.CombinationConditions(
						queryResult, queryResult.size());

				StringBuffer sql = new StringBuffer(
						"select s2.* from SDProduct s1,SDSearchRelaProduct s2 where s1.productid = s2.productid and s1.isPublish = 'Y' and s2.productID in("
								+ tempparame + ")");

				sql.append(" order by abs(s2.InitPrem - " + price + ") LIMIT 3");

				resultList = dBdata.query(sql.toString());

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return resultList;
	}

	/**
	 * 产品详细页，通过ID查询推荐产品信息.
	 *
	 * @return
	 * @author jiaomengying
	 */
	public String queryProductRec() {

		String productid = getParameter("productid");
		String exSql = "";
		try {
			// 查询产品所属栏目
			/*
			 * String sql="SELECT d.id,d.ProductType" +
			 * " FROM zcarticle b,ZDColumnValue c , zccatalog d" +
			 * " WHERE b.CatalogInnerCode LIKE '002313%' AND b.id=c.relaid AND c.columncode='RiskCode' AND c.textvalue =? AND b.catalogid=d.id"
			 * + " ORDER BY d.ProductType LIMIT 0,1";
			 */
			String sql = " SELECT a.id,a.producttype FROM zccatalog a,sdproduct b WHERE a.id = b.remark1 AND b.productid = ? LIMIT 1 ";

			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(productid);
			DataTable dt = qb.executeDataTable();
			// 查询产品所属分类下销量排名前三的其他产品
			String sql1 = " SELECT z2.URL,z2.logo,z2.InitPrem,z2.productname,z3.parentid,z1.id" +
					" FROM zcarticle z1,sdsearchrelaproduct z2,zccatalog z3" +
					" WHERE  z1.title = z2.productname AND z1.catalogid=z3.id ";

			StringBuffer result = new StringBuffer();
			String productId = "''";
			String sqlpart1 = "AND z2.productid NOT IN (" + productid + ") AND z1.catalogid =?" +
					" ORDER BY salesvolume+0 DESC LIMIT 0,3";
			QueryBuilder qb1 = new QueryBuilder(sql1 + sqlpart1);
			qb1.add(dt.get(0, "id"));
			DataTable dt1 = qb1.executeDataTable();

			String sql2 = "SELECT * FROM zdcolumnvalue WHERE relaid=? AND columncode ='baseprem'";

			// 如果同分类下有产品
			int rowCount = 0;
			String parentid = "''";
			if (dt1 != null && dt1.getRowCount() > 0) {
				rowCount = dt1.getRowCount();
				parentid = "'" + dt1.get(0, "parentid") + "'";
				for (int i = 0; i < rowCount; i++) {
					QueryBuilder qb3 = new QueryBuilder(sql2);
					qb3.add(dt1.get(i, "id"));
					DataTable dt3 = qb3.executeDataTable();
					productId += ",'" + dt1.get(i).getString("productid") + "'";
					result.append("<li class=\"usershop_list\">");
					result.append("<h4><a href=\"" + dt1.get(i, "url")
							+ "\" target=\"_blank\"><img width=\"70\" height=\"70\" alt=\"\" src=\""
							+ Config.getProductURL() + dt1.get(i, "logo") + "\"></a></h4>");
					result.append("<div class=\"user_shop\">");
					result.append("<a href=\"" + dt1.get(i, "url") + "\" target=\"_blank\">" + dt1.get(i, "productName")
							+ "</a>");
					if (dt3 != null && dt3.getRowCount() > 0) {
						if (!dt1.get(i, "initPrem").equals(dt3.get(0, "textValue"))) {
							result.append("<span class=\"shop_m_z moneys\">￥" + dt3.get(0, "textValue") + "</span>");
						} else {
							result.append("<span class=\"shop_m_z moneys\"></span>");
						}
					} else {
						result.append("<span class=\"shop_m_z moneys\"></span>");
					}
					result.append("<span class=\"red shop_m moneys\">￥" + dt1.get(i, "initPrem") + "</span>");
					result.append("</div></li>");
				}
			}
			// 如果同分类下没有其他产品或者不足3个
			for (int i = 0; i < 3 - rowCount; i++) {
				String sqlpart2 = "AND z2.productid NOT IN (" + productId + ") and z3.parentid=" + parentid +
						" ORDER BY salesvolume+0 DESC LIMIT 0,3";
				exSql = sql1 + sqlpart2;
				QueryBuilder qb2 = new QueryBuilder(exSql);
				DataTable dt2 = qb2.executeDataTable();
				if (dt2 != null && dt2.getRowCount() > 0) {
					QueryBuilder qb3 = new QueryBuilder(sql2);
					qb3.add(dt2.get(i, "id"));
					DataTable dt3 = qb3.executeDataTable();
					result.append("<li class=\"usershop_list\">");
					result.append("<h4><a href=\"" + dt2.get(i, "url")
							+ "\" target=\"_blank\"><img width=\"70\" height=\"70\" alt=\"\" src=\""
							+ Config.getProductURL() + dt2.get(i, "logo") + "\"></a></h4>");
					result.append("<div class=\"user_shop\">");
					result.append("<a href=\"" + dt2.get(i, "url") + "\" target=\"_blank\">" + dt2.get(i, "productName")
							+ "</a>");
					if (dt3 != null && dt3.getRowCount() > 0) {
						if (!dt2.get(i, "initPrem").equals(dt3.get(0, "textValue"))) {
							result.append("<span class=\"shop_m_z moneys\">￥" + dt3.get(0, "textValue") + "</span>");
						} else {
							result.append("<span class=\"shop_m_z moneys\"></span>");
						}
					} else {
						result.append("<span class=\"shop_m_z moneys\"></span>");
					}
					result.append("<span class=\"red shop_m moneys\">￥" + dt2.get(i, "initPrem") + "</span>");
					result.append("</div></li>");
				}
			}

			Map<String, Object> price = new HashMap<String, Object>();
			price.put("products", result.toString());// 折扣保费
			JSONObject jsonObject = JSONObject.fromObject(price);

			return ajax(jsonObject.toString(), "text/html");
		} catch (Exception e) {
			logger.info("productid:{},exSql:{}", productid, exSql);
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	/**
	 * 购买流程显示活动信息 比详细页少领券活动
	 * @param productId
	 * @param channelSn
	 * @return
	 */
	public Map<String, String> shoppingShowActivity(String productId, String channelSn) {

		List<Map<String, String>> resultActivityList = ActivityCalculate.ProductActivityInfo(productId, channelSn);

		// 满减 3、打折 6-红色
		// 满送 1、买送 2-蓝色
		// 高倍积分-绿色
		StringBuffer activityinfo_sub = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		map.put("activityFlag", "0");
		map.put("activityInfo", "");
		map.put("DiscountMemberChannel", "N");
		if (resultActivityList == null || resultActivityList.size() == 0) {
			return map;
		}
		for (int i = 0; i < resultActivityList.size(); i++) {
			Map<String, String> activitMap = resultActivityList.get(i);
			String type = activitMap.get("type");
			String typeName = activitMap.get("typeName");
			String title = activitMap.get("title");
			String description = activitMap.get("description");
			String GroupbuyWhether = activitMap.get("GroupbuyWhether");
			if ("1".equals(GroupbuyWhether)) {
				activityinfo_sub.append("<li>");
				activityinfo_sub.append("<span class=\" tb_icon active_07\">团购</span>");
				activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
				activityinfo_sub.append("</li>");
			} else {
				if ("3".equals(type)) {
					activityinfo_sub.append("<li>");
					activityinfo_sub.append("<span class=\" tb_icon active_04\">" + typeName + "</span>");
					activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
					activityinfo_sub.append("</li>");
				} else if ("6".equals(type)) {
						activityinfo_sub.append("<li>");
						activityinfo_sub.append("<span class=\" tb_icon active_02\">" + typeName + "</span>");
						activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
						activityinfo_sub.append("</li>");
						map.put("activityFlag", "1");
						if ("Y".equalsIgnoreCase(activitMap.get("memberChannel"))) {
							map.put("DiscountMemberChannel", "Y");
						}

				} else if ("1".equals(type)) {
					activityinfo_sub.append("<li>");
					activityinfo_sub.append("<span class=\" tb_icon active_06\">" + typeName + "</span>");
					activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
					activityinfo_sub.append("</li>");
				} else if ("2".equals(type)) {
					activityinfo_sub.append("<li>");
					activityinfo_sub.append("<span class=\" tb_icon active_05\">" + typeName + "</span>");
					activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
					activityinfo_sub.append("</li>");
				} else if ("7".equals(type)) {
					activityinfo_sub.append("<li>");
					activityinfo_sub.append("<span class=\" tb_icon active_08\">多返</span>");
					activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
					activityinfo_sub.append("</li>");;

				} else if ("8".equals(type)) {
					if (title.length() > 2) {
						// activityinfo_sub.append("<span class=\" hr_up h10\"></span><em class=\"sp_red_bg sp_sgreen_bg\">"+title.substring(0,2)+"</em><em class=\"sp_sgreen\">");
						activityinfo_sub.append("<li>");
						activityinfo_sub.append("<span class=\" tb_icon active_03\">" + title.substring(0, 2) + "</span>");
						activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
						activityinfo_sub.append("</li>");
					} else {
						// activityinfo_sub.append("<span class=\" hr_up h10\"></span><em class=\"sp_red_bg sp_sgreen_bg\">"+title+"</em><em class=\"sp_sgreen\">");
						activityinfo_sub.append("<li>");
						activityinfo_sub.append("<span class=\" tb_icon active_03\">" + title + "</span>");
						activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
						activityinfo_sub.append("</li>");
					}
				}
			}
		}
		map.put("activityInfo", activityinfo_sub.toString());
		return map;
	}

	/**
	 * @Title: showActivity
	 * @Description: TODO(产品详细页展示的最新活动信息)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public String showActivity() {

		String productId = getParameter("productId");
		String flag = getParameter("flag");
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(productId)) {
			logger.error("产品：{} 的详细页的SalesV_....的Article.RiskCode值为空！", this.getRequest().getHeader("REFERER"));
			jsonMap.put("false", "");
			return ajaxJson(jsonMap);
		}
		if (!"compare".equals(flag)) {
			productId = productId.substring(7);
		}
		if (!StringUtil.checkID(productId)) {
			logger.error("{}详细页查询活动信息的传递主键发生错误！", productId);
			jsonMap.put("false", "");
			return ajaxJson(jsonMap);
		}
		if (StringUtil.isEmpty(channelSn)) {
			channelSn = "wj";
		}
		// ProductID, title, description, type, typeName
		List<Map<String, String>> resultActivityList = ActivityCalculate.ProductActivityInfo(productId, channelSn);

		// 满减 3、打折 6-红色
		// 满送 1、买送 2-蓝色
		// 高倍积分-绿色
		StringBuffer activityinfo_sub = new StringBuffer();
		for (int i = 0; i < resultActivityList.size(); i++) {
			Map<String, String> activitMap = resultActivityList.get(i);
			String type = activitMap.get("type");
			String typeName = activitMap.get("typeName");
			String title = activitMap.get("title");
			String description = activitMap.get("description");
			String GroupbuyWhether = activitMap.get("GroupbuyWhether");

			if ("1".equals(GroupbuyWhether)) {
				/*
				 * activityinfo_sub.append("<span  id=\"gropu_time_show\">");
				 * activityinfo_sub
				 * .append("<span class=\" hr_up h10\"></span>");
				 * activityinfo_sub
				 * .append("<em class=\"sp_red_bg sp_purple_bg \">团购</em>");
				 * activityinfo_sub
				 * .append("<em class=\"sp_purple\">"+description
				 * +"</em></span>");
				 */
				activityinfo_sub.append("<li>");
				activityinfo_sub.append("<span class=\" tb_icon active_07\">团购</span>");
				activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
				activityinfo_sub.append("</li>");
			} else {
				if ("3".equals(type)) {
					activityinfo_sub.append("<li>");
					activityinfo_sub.append("<span class=\" tb_icon active_04\">" + typeName + "</span>");
					activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
					activityinfo_sub.append("</li>");
				} else if ("6".equals(type)) {
					if("discount".equals(Config.getValue("discount"))){
						activityinfo_sub.append("<li>");
						activityinfo_sub.append("<span class=\" tb_icon active_02\">" + typeName + "</span>");
						activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
						activityinfo_sub.append("</li>");
					}
				} else if ("1".equals(type)) {
					activityinfo_sub.append("<li>");
					activityinfo_sub.append("<span class=\" tb_icon active_06\">" + typeName + "</span>");
					activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
					activityinfo_sub.append("</li>");
				} else if ("2".equals(type)) {
					activityinfo_sub.append("<li>");
					activityinfo_sub.append("<span class=\" tb_icon active_05\">" + typeName + "</span>");
					activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
					activityinfo_sub.append("</li>");
				} else if ("7".equals(type)) {
					activityinfo_sub.append("<li>");
					activityinfo_sub.append("<span class=\" tb_icon active_08\">多返</span>");
					activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
					activityinfo_sub.append("</li>");

					jsonMap.put("pointtitle", title);
				} else if ("8".equals(type)) {
					if (title.length() > 2) {
						// activityinfo_sub.append("<span class=\" hr_up h10\"></span><em class=\"sp_red_bg sp_sgreen_bg\">"+title.substring(0,2)+"</em><em class=\"sp_sgreen\">");
						activityinfo_sub.append("<li>");
						activityinfo_sub.append("<span class=\" tb_icon active_03\">" + title.substring(0, 2) + "</span>");
						activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
						activityinfo_sub.append("</li>");
					} else {
						// activityinfo_sub.append("<span class=\" hr_up h10\"></span><em class=\"sp_red_bg sp_sgreen_bg\">"+title+"</em><em class=\"sp_sgreen\">");
						activityinfo_sub.append("<li>");
						activityinfo_sub.append("<span class=\" tb_icon active_03\">" + title + "</span>");
						activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
						activityinfo_sub.append("</li>");
					}
				} else if ("9".equals(type)) {
					String activitySn = activitMap.get("activitySn");

					if (StringUtil.isNotEmpty(activitySn)) {
						activityinfo_sub.append("<li>");
						activityinfo_sub.append("<span class=\" tb_icon active_09\">" + typeName + "</span>");

						activityinfo_sub
								.append("<span class=\"tb_text\"><a onclick=\"activityCoupon('" + activitySn
										+ "')\" href=\"javascript:void(0);\" class=\"lq_description\" >" + description
										+ "</a></span>");
						activityinfo_sub.append("</li>");
					}
				} else if ("12".equals(type)) {
                    String activitySn = activitMap.get("activitySn");

                    if (StringUtil.isNotEmpty(activitySn)) {
                        // 获取二维码图片
                        String sql = "SELECT imgPath FROM ActivityImgUpload WHERE location = '3' AND activitysn = ?";
                        QueryBuilder qb = new QueryBuilder(sql, activitySn);
                        String qrCodeImg = qb.executeString();

                        if (StringUtil.isNotEmpty(qrCodeImg)) {
                            activityinfo_sub.append("<li>");
                            activityinfo_sub.append("<span class=\" tb_icon active_10\">推荐</span>");
                            activityinfo_sub.append("<span class=\"tb_text\">" + description + "</span>");
                            activityinfo_sub.append("<div class=\"wechart_btn\">详情<div class=\"wechare_box\">");
                            activityinfo_sub.append("<img src=\""+ qrCodeImg +"\" alt=\"\"/>");
                            activityinfo_sub.append("</div>");
                            activityinfo_sub.append("</div>");
                            activityinfo_sub.append("</li>");
                        }
                    }
                }

			}
		}
		if (activityinfo_sub.length() == 0) {
			jsonMap.put("false", "");
		} else {
			jsonMap.put("success", activityinfo_sub.toString());
			jsonMap.put("productId", productId.toString());
			// jsonMap.put("doubleIntegral",activityinfo.toString());
		}
		DataTable dt = new QueryBuilder("SELECT endtime FROM SDPromotionHomePageSpecialProduct  WHERE productid=?",
				productId).executeDataTable();
		if (dt.getRowCount() > 0) {
			jsonMap.put("DateCountdown",
					"<div class=\"group_time\">距离促销活动结束：<span class=\"yomibox\" data=\"" + dt.getString(0, 0)
							+ "\"><span class=\"hg\"></span></span></div>");
		}
		return ajaxJson(jsonMap);
	}



	/**
	 * 在字符串中的数字前后加空格
	 *
	 * @param str
	 */
	private String addspace(String str) {

		if (StringUtil.isEmpty(str)) {
			return "";
		}
		char[] c = str.toCharArray();
		String result = "";
		boolean flag = false;
		for (int i = 0; i < c.length; i++) {
			if (NumberUtil.isInt(String.valueOf(c[i]))) {
				if (!flag) {
					result += " ";
					flag = true;
				}
			} else {
				if (flag) {
					result += " ";
					flag = false;
				}
			}
			result += String.valueOf(c[i]);
		}
		return result;
	}

	/**
	 *
	 * @Title: searchProductListActivity
	 * @Description: TODO(查询产品列表页的满减活动)
	 * @return String 返回类型
	 * @author
	 */
	public String searchProductListAvtivity() {

		String productId = getParameter("productId");
		// 详细页标志
		String detailFlag = getParameter("detailFlag");
		HashMap<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(productId)) {
			logger.warn("产品：{} 的详细页的SalesV_....的Article.RiskCode值为空！", this.getRequest().getHeader("REFERER"));
			jsonMap.put("false", "");
			return ajaxJson(jsonMap);
		}
		String[] params = productId.split(",");
		StringBuffer activityinfo = new StringBuffer("<span  class=\"shop_activity activity1\" >");
		try {
			for (int i = 0; i < params.length; i++) {
				String product_id = params[i].substring(9);
				if (!StringUtil.checkID(product_id)) {
					logger.error("{}详细页查询活动信息的传递主键发生错误！", productId.substring(9));
					jsonMap.put("false", "");
					return ajaxJson(jsonMap);
				}
				if (StringUtil.isEmpty(channelSn)) {
					channelSn = "wj";
				}
				// 查询此产品参加的满减活动信息
				QueryBuilder qb = new QueryBuilder(
						"SELECT DISTINCT type as type,activitysn,title,description,GroupbuyWhether  FROM sdcouponactivityinfo ac  WHERE ac.channelsn LIKE '%"
								+ channelSn
								+ "%' AND  ((FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0 and ac.insurancecompany='' ) OR (FIND_IN_SET(?,ac.insurancecompany)!=0  and ac.riskcode='' ) OR ( ac.riskcode='' and ac.insurancecompany='' and  ac.product='') or ( (FIND_IN_SET(?,ac.insurancecompany)!=0)  and (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0) ) OR(   FIND_IN_SET(?, ac.product) != 0  ) )  AND  ac.status='3' AND TYPE IN ('8','6', '3', '2', '1', '7')  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) GROUP BY TYPE  ORDER BY FIND_IN_SET(TYPE,'8,6,3,2,1,7') limit 1");
				qb.add(product_id);
				qb.add(product_id.substring(0, 4));
				qb.add(product_id.substring(0, 4));
				qb.add(product_id);
				qb.add(product_id);
				DataTable dt = qb.executeDataTable();
				// 自定义活动、团购、折扣、满减、高倍积分活动、满送、买送
				for (int j = 0; j < dt.getRowCount(); j++) {
					String tType = dt.getString(j, "type");
					String GroupbuyWhether = dt.getString(j, "GroupbuyWhether");
					if ("8".equals(tType) && (!"true".equals(detailFlag))) {
						String title = dt.getString(j, "title");
						if (title.length() > 2) {
							title = title.substring(0, 2);
						}
						String description = dt.getString(j, "description");
						if (description.length() > 20) {
							description = description.substring(0, 20);
						}
						appendMapValue(jsonMap, params[i] + "@" + tType, title + "@" + description);
						break;

					} else if ("1".equals(GroupbuyWhether)) {
						appendMapValue(jsonMap, params[i] + "@x", activityinfo.toString() + "团购</span>");
						break;
					} else if ("6".equals(tType)) {
						QueryBuilder disqb = new QueryBuilder(
								"SELECT TRUNCATE(ActivityData,1) FROM SdActivityRule WHERE ActivitySn=? ");
						disqb.add(dt.getString(j, "activitysn"));
						appendMapValue(jsonMap, params[i] + "@" + tType, activityinfo.toString() + disqb.executeString()
								+ "折</span>");
						break;
					} else if ("3".equals(tType)) {
						appendMapValue(jsonMap, params[i] + "@" + tType, activityinfo.toString() + "满减</span>");
						break;
					} else if ("7".equals(tType)) {
						appendMapValue(jsonMap, params[i] + "@" + tType, activityinfo.toString() + "积分</span>");
						break;
					} else if ("1".equals(tType)) {
						appendMapValue(jsonMap, params[i] + "@" + tType, activityinfo.toString() + "满送</span>");
						break;
					} else if ("2".equals(tType)) {
						appendMapValue(jsonMap, params[i] + "@" + tType, activityinfo.toString() + "买送</span>");
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("产品：" + this.getRequest().getHeader("REFERER") + " 的详细页的SalesV_....的Article.RiskCode值为！"
					+ productId + e.getMessage(), e);

		}
		return ajaxJson(jsonMap);
	}

	/**
	 *
	 * @Title: searchProductListAvtivityRe
	 * @Description: TODO(查询改版产品列表页的活动信息)
	 * @return String 返回类型
	 * @author
	 */
	public String searchProductListAvtivityRe() {

		String productId = getParameter("productId");
		// 详细页标志
		String detailFlag = getParameter("detailFlag");
		HashMap<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(productId)) {
			logger.error("产品：{} 的详细页的SalesV_....的Article.RiskCode值为空！", this.getRequest().getHeader("REFERER"));
			jsonMap.put("false", "");
			return ajaxJson(jsonMap);
		}
		String[] params = productId.split(",");

		DataTable dt3 = new QueryBuilder(
				" select BackUp2, RiskCode from femriskb where RiskCode in ('"
						+ productId.replaceAll("Activity_", "").replaceAll(",", "','") + "') and IsPublish='Y'  ")
				.executeDataTable();
		HashMap<String, String> disRateMap = new HashMap<String, String>();

		if (dt3 != null && dt3.getRowCount() >= 1) {
			int rowcount = dt3.getRowCount();
			for (int i = 0; i < rowcount; i++) {
				if (StringUtil.isNotEmpty(dt3.getString(i, "BackUp2"))) {
					disRateMap.put(dt3.getString(i, "RiskCode"), Double.parseDouble(dt3.getString(i, "BackUp2")) * 10 + "折");
				}
			}
		}

		try {
			Member member = getLoginMember();
			String memberrulesql = "";
			String tType = "";
			String GroupbuyWhether = "";
			String description = "";
			for (int i = 0; i < params.length; i++) {
				String product_id = params[i].substring(9);
				if (!StringUtil.checkID(product_id)) {
					logger.error("{}详细页查询活动信息的传递主键发生错误！", productId.substring(9));
					jsonMap.put("false", "");
					return ajaxJson(jsonMap);
				}
				if (StringUtil.isEmpty(channelSn)) {
					channelSn = "wj";
				}
				// 查询此产品参加的满减活动信息
				QueryBuilder qb = new QueryBuilder(
						"SELECT DISTINCT type as type,activitysn,title,prop2,GroupbuyWhether,MemberChannel  FROM sdcouponactivityinfo ac  WHERE ac.channelsn LIKE '%"
								+ channelSn
								+ "%' AND  ((FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0 and ac.insurancecompany='' ) OR (FIND_IN_SET(?,ac.insurancecompany)!=0  and ac.riskcode='' ) OR ( ac.riskcode='' and ac.insurancecompany='' and  ac.product='') or ( (FIND_IN_SET(?,ac.insurancecompany)!=0)  and (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0) ) OR(   FIND_IN_SET(?, ac.product) != 0  ) )  AND  ac.status='3' AND TYPE IN ('8','6', '3', '2', '1', '7')  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) GROUP BY TYPE  ORDER BY FIND_IN_SET(TYPE,'8,6,3,2,1,7') limit 2 ");
				qb.add(product_id);
				qb.add(product_id.substring(0, 4));
				qb.add(product_id.substring(0, 4));
				qb.add(product_id);
				qb.add(product_id);
				DataTable dt = qb.executeDataTable();

				// 自定义活动、团购、折扣、满减、高倍积分活动、满送、买送
				for (int j = 0; j < dt.getRowCount(); j++) {
					tType = dt.getString(j, "type");
					GroupbuyWhether = dt.getString(j, "GroupbuyWhether");
					description = dt.getString(j, "prop2"); // 使用prop2活动简述字段
					if ("8".equals(tType) && (!"true".equals(detailFlag))) {
						String title = dt.getString(j, "title");
						if (title.length() > 2) {
							title = title.substring(0, 2);
						}

						appendMapValue(jsonMap, params[i] + "@" + tType, title + "@" + description);
						disRateMap.remove(params[i]);
						break;
					} else if ("1".equals(GroupbuyWhether)) {
						appendMapValue(jsonMap, params[i] + "@x", "团购@" + description);
						disRateMap.remove(params[i]);
						break;
					} else if ("6".equals(tType)) {
						if("discount".equals(Config.getValue("discount"))){
							if(StringUtil.isNotEmpty(dt.getString(j, "MemberChannel")) && "Y".equals(dt.getString(j, "MemberChannel"))){//含有会员渠道拆分
								if(null==member){//未登录显示折扣 登录显示几折
									appendMapValue(jsonMap, params[i] + "@" + tType,  "折扣@" + description);
								}else{
									if(StringUtil.isNotEmpty(member.getVipFlag()) && "Y".equals(member.getVipFlag())){
										memberrulesql = "AND  memberrule LIKE '%VIP%' ";
									}else{
										memberrulesql = "AND  memberrule LIKE '%"+member.getGrade()+"%' ";
									}
									QueryBuilder disqb = new QueryBuilder( "SELECT TRUNCATE(ActivityData,1) FROM SdActivityRule WHERE ActivitySn=? "+memberrulesql);
									disqb.add(dt.getString(j, "activitysn"));
									appendMapValue(jsonMap, params[i] + "@" + tType, disqb.executeString() + "折@" + description);
								}
						    }else{//没有会员渠道拆分走老逻辑
								QueryBuilder disqb = new QueryBuilder( "SELECT TRUNCATE(ActivityData,1) FROM SdActivityRule WHERE ActivitySn=? ");
								disqb.add(dt.getString(j, "activitysn"));
								appendMapValue(jsonMap, params[i] + "@" + tType, disqb.executeString() + "折@" + description);
						    }
							disRateMap.remove(params[i]);
							break;
						}
					} else {
//						if (StringUtil.isNotEmpty(disRateMap.get(params[i]))) {
//							appendMapValue(jsonMap, params[i] + "@6", disRateMap.get(params[i]) + "@");
//							disRateMap.remove(params[i]);
//							break;
//						} else
						if ("3".equals(tType)) {
							appendMapValue(jsonMap, params[i] + "@" + tType, "满减@" + description);
							break;
						}else if ("7".equals(tType)) {
							appendMapValue(jsonMap, params[i] + "@" + tType, "多返@" + description);
							break;
						} else if ("1".equals(tType)) {
							appendMapValue(jsonMap, params[i] + "@" + tType, "满送@" + description);
							break;
						} else if ("2".equals(tType)) {
							appendMapValue(jsonMap, params[i] + "@" + tType, "买送@" + description);
							break;
						}
					}

				}
			}
//			if (!disRateMap.isEmpty()) {
//				for (Map.Entry<String, String> entry : disRateMap.entrySet()) {
//					appendMapValue(jsonMap, "Activity_" + entry.getKey() + "@6", entry.getValue() + "@");
//				}
//			}
		} catch (Exception e) {
			logger.error("改版产品列表页查询活动信息异常！" + productId + e.getMessage(), e);

		}
		return ajaxJson(jsonMap);
	}

	/**
	 *
	 * @Title: appendMap
	 * @Description: TODO(追加Map中的信息,以@符号分割)
	 * @return void 返回类型
	 * @author
	 */
	private HashMap<String, String> appendMapValue(HashMap<String, String> map, String key, String appendValue) {

		String value = map.get(key);
		if (StringUtil.isEmpty(value)) {
			map.put(key, appendValue);
		} else {
			map.put(key, value + "@" + appendValue);
		}
		return map;
	}

	/**
	 *
	 * @Title: sendWeiXiData
	 * @Description: TODO(维析需要传递的数据)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	public String sendWeiXiData() {

		String productId = getParameter("productId");
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(productId)) {
			logger.error("产品：{} 的详细页的SalesV_....的Article.RiskCode值为空！", this.getRequest().getHeader("REFERER"));
			jsonMap.put("false", "");
			return ajaxJson(jsonMap);
		}
		if (!StringUtil.checkID(productId.substring(7))) {
			logger.error("{}详细页查询活动信息的传递主键发生错误！", productId.substring(7));
			return "";
		}
		String riskname = "";
		String companyname = "";
		// 查询此产品参加的最新活动信息
		String proid = productId.substring(7);
		String member = String.valueOf(getLoginMember());
		if ("null".equals(member)) {
			jsonMap.put("memberid", "");
		} else if ("tencent".equals(getLoginMember().getId())) {
			jsonMap.put("memberid", "");
		}
		else {
			jsonMap.put("memberid", getLoginMember().getId());
		}
		QueryBuilder qb_riskname = new QueryBuilder(
				"SELECT co.codename AS codename FROM fmrisk fm,zdcode co WHERE  co.codetype = 'risktype'  AND co.codevalue = fm.belongflag AND riskcode = ?",
				proid.substring(0, proid.length()));
		if (qb_riskname.executeDataTable().getRowCount() > 0) {
			String codename = qb_riskname.executeDataTable().getString(0, 0);
			riskname = riskname + codename.substring(codename.lastIndexOf("-") + 1, codename.length());
		} else {
			logger.error("产品：{}的详细页的SalesV_....的Article.RiskCode值对应的险种名称没有查询到！", this.getRequest().getHeader("REFERER"));
		}
		QueryBuilder qb_companyname = new QueryBuilder(
				"SELECT codename FROM zdcode WHERE codetype = 'SupplierCode'  AND codevalue=?", proid.substring(0, 4));
		if (qb_companyname.executeDataTable().getRowCount() > 0) {
			companyname = companyname + qb_companyname.executeDataTable().getString(0, 0);
		} else {
			logger.error("产品：{}的详细页的SalesV_....的Article.RiskCode值对应的公司名称没有查询到！", this.getRequest().getHeader("REFERER"));
		}
		jsonMap.put("totalamount", "");
		jsonMap.put("riskname", riskname);
		jsonMap.put("companyname", companyname);
		return ajaxJson(jsonMap);
	}

	/**
	 * 取得产品详细页的公告
	 *
	 * @return 公告
	 */
	public String showInform() {

		// 产品编码
		String param = getParameter("productId");
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(param)) {
			jsonMap.put("info", "");
			return ajaxJson(jsonMap);
		}

		String sql = "select Info from ProductAnnouncement "
				+ "where ProductID = ? and ViewFlag = '1' "
				+ "and UNIX_TIMESTAMP(DATE_FORMAT(StartDate,'%Y-%m-%d %H:%i:%s')) "
				+ "<= UNIX_TIMESTAMP(now()) and "
				+ "UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) "
				+ "> UNIX_TIMESTAMP(now()) order by InfoOrder asc limit 0,3 ";
		// 查询此产品的公告信息
		QueryBuilder qb = new QueryBuilder(sql, param);
		DataTable dt = qb.executeDataTable();
		// 公告信息
		String info = "";
		// 若该产品没有公告信息 则取所在的保险公司的公告信息
		if (dt == null || dt.getRowCount() == 0) {
			sql = "select Info from ProductAnnouncement " +
					"where ComCode = ? and ViewFlag = '1' "
					+ "and UNIX_TIMESTAMP(DATE_FORMAT(StartDate,'%Y-%m-%d %H:%i:%s')) "
					+ "<= UNIX_TIMESTAMP(now()) and "
					+ "UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) "
					+ "> UNIX_TIMESTAMP(now()) order by InfoOrder asc limit 0,3 ";
			qb = new QueryBuilder(sql, param.substring(0, 4));
			dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				for (int j = 0; j < dt.getRowCount(); j++) {
					info += dt.getString(j, 0);
				}
			}
		} else {
			for (int i = 0; i < dt.getRowCount(); i++) {
				info += dt.getString(i, 0);
			}
		}

		jsonMap.put("info", info);

		String companycount_sql = "SELECT count(1) FROM sdproduct p,zcarticle a  WHERE a.CatalogInnerCode LIKE '002313%' AND a.type='1' AND a.prop4=p.productid AND a.status='30' AND p.remark6 = ?";
		jsonMap.put("companycount", new QueryBuilder(companycount_sql, param.substring(0, 4)).executeString());

		return ajaxJson(jsonMap);
	}

	/**
	 * 取得会员中心首页的公告
	 *
	 * @return 公告
	 */
	public String showInforMem() {

		Map<String, String> jsonMap = new HashMap<String, String>();
		String sql = "select Info from ProductAnnouncement "
				+ " where (ProductID='' OR ProductID IS NULL) "
				+ " AND (ComCode='' OR ComCode IS NULL) and ViewFlag = '1' "
				+ "and UNIX_TIMESTAMP(DATE_FORMAT(StartDate,'%Y-%m-%d %H:%i:%s')) "
				+ "<= UNIX_TIMESTAMP(now()) and "
				+ "UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) "
				+ "> UNIX_TIMESTAMP(now()) order by InfoOrder asc limit 0,1 ";
		// 查询公告信息
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		// 公告信息
		if (dt != null && dt.getRowCount() > 0) {
			jsonMap.put("info", dt.getString(0, 0));
		} else {
			jsonMap.put("info", "");
		}
		return ajaxJson(jsonMap);
	}

	public String isWapProduct(String riskcode) {

		// 产品编码
		if (StringUtil.isEmpty(riskcode)) {
			return "N";
		}
		QueryBuilder qb = new QueryBuilder("select IsPublish from productrelainfo where ProductID = ?", riskcode);
		return qb.executeString();
	}


	/**
	 * 1.判断当前产品是不是理财险
	 * 2.理财险产品必须先登录
	 * @author chouweigao
	 *
	 */
	public String  lcxCheck() {
		String param = getParameter("productIds");
		Map<String, String> jsonMap = new HashMap<String, String>();

		String riskcode = param.substring(7);

		String isTrue = "1";
		String[] lcx_arr = Config.getValue("LicaiBaoxianProducts").split(",");
		List<String> listLcx = Arrays.asList(lcx_arr);
		if (listLcx.contains(riskcode)) {
			//记录产品是理财险
			String member = String.valueOf(getLoginMember());
			if ("null".equals(member)) {
				isTrue = "0";
			}
		}
		jsonMap.put("isLcx", isTrue);
		return ajaxJson(jsonMap);
	}

	public String pointInfo() {
		Map<String, String> jsonMap = new HashMap<String, String>();

		String orderSn = getParameter("orderSn");
		String productId = getParameter("productId");
		String price = getParameter("price");
		String risktypeNum = getParameter("risktypeNum");
		String channelsn = getParameter("channelsn");
		// 订单关联会员
		String orderSnTem = getParameter("orderSnTem");
		jsonMap.put("result_sendPointsDesc", "保单生效后可获得");
		jsonMap.put("result_sendPointsValue", "0");
		jsonMap.put("result_sendPoints", "0");
		jsonMap.put("inspageone_pointsInfo","");
		jsonMap.put("inspageone_points", "");
		jsonMap.put("inspageone_pointsprice", "");
		String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
		String memberId = "";
		// 当前登录会员
		Member member = getLoginMember();
		if (member != null) {
			memberId = member.getId();
		} else if (StringUtil.isNotEmpty(orderSnTem)) {
			memberId = new QueryBuilder("select memberId from sdorders where ordersn=?", orderSnTem).executeString();
			if (StringUtil.isNotEmpty(memberId)) {
				member = memberService.get(memberId);
			}
		}

		int integral = 0;
		String baseIntegral = "0";
		Map<String, Map<String, String>> resultProductDetail = null;
		if (StringUtil.isNotEmpty(orderSn)) {
			DataTable dt = new QueryBuilder("select totalAmount, channelsn from sdorders where ordersn=?", orderSn).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				Map<String, String> ProductIDMap = new HashMap<String, String>();
				ProductIDMap.put(productId, dt.getString(0, 0));
				resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap, orderSn, dt.getString(0, 1));
			}
		} else if (StringUtil.isNotEmpty(price)) {
			ActivityCalculateDetail ActivityCalculateDetail = new ActivityCalculateDetail();
			Map<String, String> ProductIDMap = new HashMap<String, String>();
			ProductIDMap.put(productId, price);
			if (StringUtil.isNotEmpty(risktypeNum) && risktypeNum.contains("份")) {
				risktypeNum = risktypeNum.replace("份", "").trim();
			}
			resultProductDetail = ActivityCalculateDetail.ProductIntegralCalculate(ProductIDMap, risktypeNum, channelsn, memberId);
		}

		String productGivePointsPercent = null;//产品赠送积分比例
		BigDecimal nowPrem = new BigDecimal(0);
		if (resultProductDetail != null && resultProductDetail.size() > 0) {
			Map<String, String> productDetail = resultProductDetail.get(productId);

			String rAmount = productDetail.get("Amount");
			String rIntegral = productDetail.get("Integral");

			if (StringUtil.isNotEmpty(rAmount)) {
				nowPrem = new BigDecimal(rAmount);
			}

			if (StringUtil.isNotEmpty(rIntegral)) {
				integral = Integer.parseInt(rIntegral);
			}
			baseIntegral = nowPrem.multiply(new BigDecimal(PointScalerUnit)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			productGivePointsPercent = productDetail.get("pointrate");
		}

		if (StringUtil.isNotEmpty(memberId)) {
			if (integral > 0) {
				//会员等级
				Map<String,String>  map_result_grade=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BUY, memberId, baseIntegral,productGivePointsPercent);
				String flag_grade=map_result_grade.get("flag");
				if("true".equals(flag_grade)){
					String MemberGrade=map_result_grade.get("MemberGrade");
					integral = (integral + Integer.parseInt(map_result_grade.get("addpoints")));
					jsonMap.put("result_sendPointsDesc", "您是"+MemberGrade+"会员,保单生效后可获得");
				}
				//会员生日月
				Map<String,String>  map_result=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BIRTH_MONTH,memberId, baseIntegral,productGivePointsPercent);
				String flag=map_result.get("flag");
				if("true".equals(flag)){
					String MemberGrade=map_result.get("MemberGrade");
					integral = (integral + Integer.parseInt(map_result.get("addpoints")));
					jsonMap.put("result_sendPointsDesc", MemberGrade+"会员生日月享特权,保单生效后特别赠送");
				}
			}

			// 取得可使用积分数
			Map map=new HashMap();
			List list=new ArrayList();
			list.add(productId);
			map.put("ProductList",list);

			try {
				Map result = new PointsCalculate().pointsManage(IntegralConstant.POINT_PRODUCT, "", map);

				DataTable dt_result=(DataTable) result.get(IntegralConstant.DATA);
				if(dt_result.getRowCount()>0){
					String BuyPoints=dt_result.getString(0,"BuyPoints");
					//String GivePoints=dt_result.getString(0,"GivePoints");
					java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.000");
					String str = myformat.format(new BigDecimal(Double
							.parseDouble(String.valueOf(nowPrem))
							* Double.parseDouble(BuyPoints)
							* Double.parseDouble(PointScalerUnit)));
					BigDecimal points=new BigDecimal(str).setScale(0, BigDecimal.ROUND_UP);
					BigDecimal CurrentValidatePoint=new BigDecimal(member.getCurrentValidatePoint());
					if(points.compareTo(CurrentValidatePoint)>0){
						jsonMap.put("inspageone_points",String.valueOf(CurrentValidatePoint));
						BigDecimal pointValue = CurrentValidatePoint.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
						jsonMap.put("inspageone_pointsprice",String.valueOf(pointValue));
						jsonMap.put("inspageone_pointsInfo","您用"+CurrentValidatePoint+"积分可以进行抵扣费用¥"+pointValue.toString()+"（继续支付可直抵）");
					}else{
						jsonMap.put("inspageone_points",String.valueOf(points));
						BigDecimal pointValue = points.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
						jsonMap.put("inspageone_pointsprice", String.valueOf(pointValue));
						if(points.compareTo(new BigDecimal("0"))==0){
							jsonMap.put("inspageone_pointsInfo","");//不可使用积分抵扣时，不显示
						}else{
							jsonMap.put("inspageone_pointsInfo","您用"+points+"积分可以进行抵扣费用¥"+pointValue.toString()+"（继续支付可直抵）");
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		if (integral > 0) {
			jsonMap.put("result_sendPoints", String.valueOf(integral));
			jsonMap.put("result_sendPointsValue", new BigDecimal(integral).divide(new BigDecimal(PointScalerUnit), 1, BigDecimal.ROUND_HALF_UP).toString());
		}

		return ajaxJson(jsonMap);
	}

	/**
	 * getProductDetailBright:获取产品亮点. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	public String loadProductDetailBright() {
		String productIds = getParameter("productIds");
		Map<String, String> jsonMap = new HashMap<String, String>();

		if (StringUtil.isNotEmpty(productIds)) {
			String[] productidArr = productIds.split(",");

			for (int i = 0; i < productidArr.length; i++) {

				QueryBuilder qb = new QueryBuilder("SELECT BrightSpotName FROM FEMRiskBrightSpotList WHERE riskcode= ?");
				qb.add(productidArr[i]);
				DataTable dt = qb.executeDataTable();

				String tHTML = "";
				for (int j = 0; j < dt.getRowCount(); j++) {
					tHTML += "<li><em class=\"fte_icon\"></em>"
							+ dt.getString(j, "BrightSpotName")
							+ "</li>";
				}

				jsonMap.put(productidArr[i], tHTML);
			}
		}

		return ajaxJson(jsonMap);
	}

	/**
	 * visitBackOnLine:发送同意在线回访报文. <br/>
	 *
	 * @author liuhongyu
	 */
	@SuppressWarnings("unchecked")
	public void visitBackOnLine() {

		String orderSns = getParameter("orderSn");
		if (StringUtil.isEmpty(orderSns)) {
			logger.error("在线回访错误，订单号为空");
			return;
		}

		Mapx<String, String> onlineRevisitProducts =  CacheManager.getMapx("Code", "OnLineCallBackProductID");// 支持线上回访的产品

		for(String orderSn : orderSns.split(",")){
			QueryBuilder qb = new QueryBuilder(
					"select * from sdinformationrisktype where orderSn = ?", orderSn);
			DataTable dt = qb.executeDataTable();

			if (dt.getRowCount() == 0) {
				logger.error("订单不存在");
				return;
			}

			DataRow datarow = dt.get(0);
			String RiskCode = datarow.getString("RiskCode");
			String comCode = RiskCode.substring(0, 4);

			// 判断产品是否支持在线回访(购物车多产品同时支付时有的产品可能不支持在线回访)
			if(!onlineRevisitProducts.containsKey(RiskCode)){
				continue;
			}

			Map<String, Object> sendMap = new HashMap<String, Object>();
			String transDate = DateUtil.getCurrentDate("yyyy-MM-dd");
			sendMap.put("TransDate", transDate);
			String transTime = DateUtil.getCurrentDate("HH:mm:ss");
			sendMap.put("TransTime", transTime);
			sendMap.put("TransCode", Constant.ONLINE_VISIT);
			sendMap.put("ComCode", comCode);
			sendMap.put("ContNo", datarow.get("PolicyNo"));
			sendMap.put("ReVisitDate", DateUtil.getCurrentDateTime());

			Map<String, Object> result = null;
			try {
				Class<?> ccDeal = Class.forName("com.sinosoft.jdt.cc.CCDeal" + comCode);
				CCDealInterface ccdi = (CCDealInterface) ccDeal.newInstance();
				result = ccdi.cardCheck(sendMap);
			} catch (Exception e) {
				logger.error("调用经代通接口出现异常" + e.getMessage(), e);
			}
			if (null == result) {
				logger.error("返回结果为空");
				return;
			}
			if ("nopass".equals(result.get("passFlag"))) {
				logger.info("已处理{}", result.get("Msg"));
			} else {
				// 更新在线回访记录状态为回访成功
				QueryBuilder upQb = new QueryBuilder("update onlinerevisitrecord set status = '1',modifyDate = NOW() where orderSn = ?",orderSn);
				upQb.executeNoQuery();

				logger.info("{}:::回访成功!", datarow.get("PolicyNo") + (String) result.get("Msg"));
			}
		}
	}

	/**
	 * ajax方式检索销售量
	 *
	 * @return 销售量
	 */
	public String searchSalesVolume() {

		// 20123256,552166316,21022232,2012233231
		String param = getParameter("productIds");
		if (StringUtil.isEmpty(param)) {
			return ajaxJson("");
		}
		String[] params = param.split(",");
		Map<String, String> jsonMap = new HashMap<String, String>();

		for (int i = 0; i < params.length; i++) {
			try {
				String tSalesVolume = "0";
				tSalesVolume = dealSalvesVolumn(params[i]);
				jsonMap.put(params[i], tSalesVolume);
			} catch (Exception e) {
				logger.error("查询销量失败！" + e.getMessage(), e);
				jsonMap.put(params[i], "0");
			}
		}
		return ajaxJson(jsonMap);
	}

}
