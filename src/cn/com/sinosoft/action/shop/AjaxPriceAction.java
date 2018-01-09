package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.util.CookieUtil;
import com.sinosoft.cms.document.ShoppingGuidePKManage;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.points.IntegralConstant;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("shop") 
public class AjaxPriceAction extends BaseShopAction {

	private static final long serialVersionUID = -975907428768389791L;
	private String Callback;
	
	/**
	 * @Title: search.
	 * @Description: TODO(Ajax动态加载价格).
	 * @return String.
	 * @author CongZN.
	 */
	public String ajaxPrice() {
		
		Map<String, String> jsonMap = new HashMap<String, String>();
		String productIDS = getParameter("ProductIDS");
		String channelSn = getParameter("channelSn");
		if(StringUtil.isEmpty(channelSn)){
			channelSn = "wj";
		}
		if (StringUtil.isEmpty(productIDS)) {
			return ajaxJson("");
		}
		
		productIDS = productIDS.replaceAll("Ajax_Prict_", "'");
		productIDS = productIDS.replaceAll(",", "',");
		
		String query_PriceSql = "select ProductID,Remark4 from sdproduct where ProductID in("+productIDS+"')";
		QueryBuilder query_Price = new QueryBuilder(query_PriceSql);
		DataTable dt = query_Price.executeDataTable();
		
		Map<String,String> ProductInfoMap = new HashMap<String, String>();
		Map<String, Map<String, String>> result_ProductInfoList;
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			String productPrice = dt.getString(i, "Remark4");
			if(StringUtil.isNotEmpty(productPrice)){
				ProductInfoMap.put(dt.getString(i, "ProductID"), productPrice);
			}
			logger.info("AJAX 刷新价格参数,产品编码：{}|价格：{}", dt.getString(i, "ProductID"), productPrice);
		}
		result_ProductInfoList = ActivityCalculate.ProductCalculate(ProductInfoMap,"", channelSn); 
		for (int i = 0; i < dt.getRowCount(); i++) {
			String productPrice = dt.getString(i, "Remark4");
			String riskcode = dt.getString(i, "ProductID");
			Map<String, String> productDetail = result_ProductInfoList.get(riskcode);
			String productGivePointsPercent = productDetail.get("pointrate");//产品赠送积分比例
			if(productDetail!=null && productDetail.size()>=1){
				String rIntegral = productDetail.get("Integral");
				String PointAmount = productDetail.get("PointAmount");
				BigDecimal CurrentValidatePoint =new BigDecimal("0");
				Member loginMember = getLoginMember();
				if(loginMember!=null){
					 CurrentValidatePoint=new BigDecimal(loginMember.getCurrentValidatePoint());
					String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
					BigDecimal pointValue = CurrentValidatePoint.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
					BigDecimal points=new BigDecimal(PointAmount);
					if(points.compareTo(pointValue)>0){
						PointAmount=String.valueOf(pointValue);
					}
				}
				String rAmount = productDetail.get("Amount");
				DataTable dt3 = new QueryBuilder(" SELECT z2.TextValue FROM zcarticle z1,zdcolumnvalue z2 WHERE z1.id = z2.relaid AND z1.prop4 = ? AND ColumnCode = 'DiscountRate' LIMIT 1 ",riskcode).executeDataTable();
				String discountRate = "dis";
				if(dt3!=null && dt3.getRowCount()>=1){
					if(StringUtil.isNotEmpty(dt3.getString(0, "TextValue"))){
						discountRate = dt3.getString(0, "TextValue");
					}
				}
				String baseIntegral = (new BigDecimal(rAmount).multiply(new BigDecimal(Config.getValue("PointScalerUnit")))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				String  loginFlag="false";
				String pointDesFlag="false";
				String pointDes="";
				int intInte = Integer.valueOf(rIntegral);
				if (loginMember != null && intInte > 0) {
					//会员等级
					Map<String,String>  map_result_grade=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BUY,loginMember.getId(), baseIntegral,productGivePointsPercent);
					String flag_grade=map_result_grade.get("flag");
					if("true".equals(flag_grade)){
						String point_result=map_result_grade.get("points");
						String MemberGrade=map_result_grade.get("MemberGrade");
						//无取舍操作的积分值
						//String pointsAll=map_result_grade.get("pointsAll");
						// 取得会员等级增加的积分数
						rIntegral = String.valueOf(Integer.valueOf(rIntegral) + Integer.valueOf(map_result_grade.get("addpoints")));
						
						pointDesFlag="true";
						pointDes="您是"+MemberGrade+"会员,投保成功将获得 "+rIntegral+" 个积分";
						
					}
					//会员生日月
					Map<String,String>  map_result=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BIRTH_MONTH,loginMember.getId(), baseIntegral,productGivePointsPercent);
					String flag=map_result.get("flag");
					//String point=map_result.get("points");
					if("true".equals(flag)){
						String MemberGrade=map_result.get("MemberGrade");
						rIntegral = String.valueOf(Integer.valueOf(rIntegral) + Integer.valueOf(map_result.get("addpoints")));
						pointDesFlag="true";
						pointDes=MemberGrade+"会员生日月享特权,投保成功特别赠送 "+rIntegral+" 个积分";
					}
					
					loginFlag="true";
				}
				if(StringUtil.isEmpty(rIntegral)){
					rIntegral = "0";
				}
				jsonMap.put(riskcode, rAmount+"_"+productPrice+"_"+rIntegral+"_"+discountRate+"_"+
				                      PointAmount+"_"+loginFlag+"_"+pointDesFlag+"_"+pointDes+"_"+
						              String.valueOf(CurrentValidatePoint)+"_"+Config.getValue("discount"));
			}
		}
		String jsonStr = formatJson(jsonMap);
		return ajaxJson(Callback + "(" + jsonStr + ");");
	}

/**
 * @Title: queryAjaxPrice.
 * @Description: TODO(后台使用,计算产品最新价格).
 * @param p_ProductID
 * @return String.
 * @author CongZN. 
 */
public String queryAjaxPrice(String p_ProductID, String memberId) {
	HttpServletRequest request = ServletActionContext.getRequest();
		String Channel = "";
		
		String r_price = "";
		Cookie ck;
		ck = CookieUtil.getCookieByName(request, "cpsUserId");
		if(StringUtil.isEmpty(ck)){
			Channel = "wj";
		}else{
			Channel = "cps";
		}
	
		r_price = queryAjaxPrice(p_ProductID, Channel, memberId);
		
		return r_price;
	}

/**
 * @Title: queryAjaxPrice.
 * @Description: TODO(后台使用,计算产品最新价格).
 * @param p_ProductID.
 * @param p_Channel.
 * @return String.
 * @author CongZN. 
 */
public String queryAjaxPrice(String p_ProductID,String p_Channel, String memberId) {
	
	String query_PriceSql = "select ProductID,Remark4 from sdproduct where ProductID = ?";
	QueryBuilder query_Price = new QueryBuilder(query_PriceSql);
	query_Price.add(p_ProductID);
	DataTable dt = query_Price.executeDataTable();
	
	Map<String,String> ProductInfoMap;
	
	String r_price = "";
	
	for (int i = 0; i < dt.getRowCount(); i++) {
		String productPrice = dt.getString(i, "Remark4");
		logger.info("AJAX 刷新价格参数(后台),产品编码：{}|价格：{}", dt.getString(i, "ProductID"), productPrice);
		if(StringUtil.isNotEmpty(productPrice)){
			ProductInfoMap = new HashMap<String, String>();
			ProductInfoMap.put("ProductID", dt.getString(i, "ProductID"));
			ProductInfoMap.put("Amount", productPrice);
			r_price = ActivityCalculate.ProductCalculate(dt.getString(i, "ProductID"),"",productPrice,p_Channel, memberId);
			logger.info("AJAX 刷新价格返回(后台),产品编码：{}|价格：{}",dt.getString(i, "ProductID"), r_price);
		}
	}
	
	if(StringUtil.isEmpty(r_price)){
		QueryBuilder query_price = new QueryBuilder("SELECT InitPrem FROM  sdsearchrelaproduct WHERE  productid = ?",p_ProductID);
		r_price = query_price.executeString();
		logger.info("AJAX 刷新价格二次查询(后台),产品编码：{}|价格：{}", p_ProductID, r_price);
	}
	
	return r_price;
}

	public String ajaxPKPrice() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String articleId = getParameter("articleId");
		String channelSn = getParameter("channelSn");
		String diffDays = getParameter("diffDays");
		if(StringUtil.isEmpty(channelSn)){
			channelSn = "wj";
		}
		if (StringUtil.isEmpty(articleId) || StringUtil.isEmpty(diffDays)) {
			return ajaxJson("");
		}
		ShoppingGuidePKManage man= new ShoppingGuidePKManage();
		// 取得PK产品信息
		Map<String, String> productMap = man.getPKProductInfo(articleId);
		if (productMap.isEmpty()) {
			return ajaxJson("");
		}
		
		String productId1 = productMap.get("ProductId1");
		String productId2 = productMap.get("ProductId2");
		
		String sql = "select AppFactorCode,FactoryType,FactoryValue from PKProductFactoryInfo where RiskCode = ? order by FactoryType asc, ID asc";
		// 取得产品1投保要素信息
		DataTable dt = new QueryBuilder(sql, productId1).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			Map<String, String> appFactorMap1 = getAppFactorInfo(dt, diffDays);
			if (appFactorMap1 == null) {
				jsonMap.put(productId1, "超过最长保险期限");
			} else {
				try {
					// 保费试算
					String prices = man.premDoCal(appFactorMap1, productId1);
					String discountPrice = prices.split(",")[0];
					String prem  = prices.split(",")[1];
					jsonMap.put(productId1, getActivityPrice(productId1, discountPrice, channelSn)+"_"+prem);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		// 取得产品2投保要素信息
		dt = new QueryBuilder(sql, productId2).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			Map<String, String> appFactorMap2 = getAppFactorInfo(dt, diffDays);
			if (appFactorMap2 == null) {
				jsonMap.put(productId2, "超过最长保险期限");
			} else {
				try {
					// 保费试算
					String prices = man.premDoCal(appFactorMap2, productId2);
					String discountPrice = prices.split(",")[0];
					String prem  = prices.split(",")[1];
					jsonMap.put(productId2, getActivityPrice(productId2, discountPrice, channelSn)+"_"+prem);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		String jsonStr = formatJson(jsonMap);
		return ajaxJson(Callback + "(" + jsonStr + ");");
	}

	public String ajaxPKActivityPrice() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String channelSn = getParameter("channelSn");
		String productIDS = getParameter("ProductIDS");
		String discountPrices = getParameter("discountPrices");
		if(StringUtil.isEmpty(channelSn)){
			channelSn = "wj";
		}
		if (StringUtil.isEmpty(productIDS)) {
			return ajaxJson("");
		}
		if (StringUtil.isEmpty(discountPrices)) {
			return ajaxJson("");
		}
		String[] riskcodes = productIDS.split(",");
		String[] discountPrice = discountPrices.split(",");
		for (int i = 0; i < riskcodes.length; i++) {
			jsonMap.put(riskcodes[i], getActivityPrice(riskcodes[i], discountPrice[i], channelSn));
		}
		
		String jsonStr = formatJson(jsonMap);
		return ajaxJson(Callback + "(" + jsonStr + ");");
	}
	
	private String getActivityPrice(String riskcode, String discountPrice, String channelSn) {
		// 取得网站折后价
		Map<String, String> ProductIDMap = new HashMap<String, String>();
		ProductIDMap.put(riskcode, discountPrice);
		Map<String, Map<String, String>> resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap,"", channelSn);
		Map<String, String> productDetail = resultProductDetail.get(riskcode);
		// 折后价
		return productDetail.get("Amount");
	}
	
	private Map<String, String> getAppFactorInfo(DataTable dt, String diffDays) {
		Map<String, String> map = new HashMap<String, String>();
		int rowcount = dt.getRowCount();
		int days = Integer.valueOf(diffDays);
		String factoryValue = "";
		String lower = "";
		String upper = "";
		boolean flag = false;
		ShoppingGuidePKManage man= new ShoppingGuidePKManage();
		for (int i = 0; i < rowcount; i++) {
			factoryValue = dt.getString(i, "FactoryValue");
			if (StringUtil.isNotEmpty(factoryValue)) {
				if ("Period".equals(dt.getString(i, "FactoryType"))) {
					if (!flag) {
						if (factoryValue.contains("-")) {
							lower = factoryValue.split("-")[0];
							lower = man.convertDate(lower);
							if (days <= Integer.valueOf(lower)) {
								map.put(dt.getString(i, "AppFactorCode") + "_" + dt.getString(i, "FactoryType"), factoryValue);
								flag = true;
								continue;
							}
							
							upper = factoryValue.split("-")[1];
							upper = man.convertDate(upper);
							if (days <= Integer.valueOf(upper)) {
								map.put(dt.getString(i, "AppFactorCode") + "_" + dt.getString(i, "FactoryType"), factoryValue);
								flag = true;
								continue;
							}
							
						} else {
							lower = man.convertDate(factoryValue);
							if (days <= Integer.valueOf(lower)) {
								map.put(dt.getString(i, "AppFactorCode") + "_" + dt.getString(i, "FactoryType"), factoryValue);
								flag = true;
								continue;
							}
						}
					}
					
				} else {
					map.put(dt.getString(i, "AppFactorCode") + "_" + dt.getString(i, "FactoryType"), factoryValue);
				}
			}
		}
		
		if (!flag) {
			return null;
		}
		return map;
	}
	
public void setCallback(String callback) {
	Callback = callback;
}
}
