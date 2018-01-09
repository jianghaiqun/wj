package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDShoppingCart;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDShoppingCartService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.PrecontractInfoSchema;
import com.sinosoft.schema.SDCouponInfoSchema;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ParentPackage("shop")
public class ShoppingCartAction extends BaseShopAction {

	private static final long serialVersionUID = 392740423374402375L;
	private String orderSn;
	private String checkOrderSn;
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private SDShoppingCartService sdshopCartService;
	
	private String topUrl;
	//区分top广告和弹窗广告的标志位
	private String prop1;
	//过渡页保险公司编码
	private String company;
	//过度页保险公司名称
	private String companyName;
	//过度页保险公司类型（样式控制）
	private String type;
	//过度页维析js
	private String name;
	//过度页跳转路径
	private String pageLink;
	//预约人姓名		
	private String preName;
	//预约人性别		
	private String preSex;
	//预约人邮箱
	private String preEmail;
	//预约人电话
	private String prePhone;
	//预约人电话
	private String remark;
	
	public String addOrders(){
		Member memberLogin = getLoginMember();
		Map<String, Object> tData = new HashMap<String, Object>();
		int shopCartNo = 0;
		try {
			if(memberLogin != null){
				if(StringUtil.isNotEmpty(orderSn)){
					SDOrder sdorder = sdorderService.getOrderByOrderSn(orderSn);
					if(StringUtil.isEmpty(sdorder.getMemberId())){
						sdorder.setMemberId(memberLogin.getId());
					}
					sdorder.setOrderStatus(SDOrderStatus.prepay);
					sdorderService.update(sdorder);
					boolean checkFlag = sdshopCartService.checkInsuredFromShopCart(sdorder);
					boolean channelCheckFlag = sdshopCartService.checkChannelFromShopCart(sdorder);//校验是否同渠道
					// 校验该产品是否可以加入购物车
					if (!sdshopCartService.checkProduct(orderSn)) {
						tData.put("status", "X");
						tData.put("shopCartNo", shopCartNo);
						JSONObject jsonObject = JSONObject.fromObject(tData);
						return ajax(jsonObject.toString(), "text/html");
					}
					if(!channelCheckFlag){
						tData.put("status", "M");//提示订单来源渠道不同，不能加入购物车。
						tData.put("shopCartNo", shopCartNo);
						JSONObject jsonObject = JSONObject.fromObject(tData);
						return ajax(jsonObject.toString(), "text/html");
					}
					if(checkFlag){
						SDShoppingCart shopCart = sdshopCartService.createCartByOrder(sdorder);
						sdshopCartService.save(shopCart);
						tData.put("status", "Y");
					}else{
						tData.put("status", "E");//提示用户购物车中被保人同一产品被保人重复。
					}
				}
				shopCartNo = sdshopCartService.getShopCartNo(memberLogin.getId());
			}else{
				tData.put("status", "NOLOG");//未登录
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			tData.put("status", "N");
		}
		tData.put("shopCartNo", shopCartNo);
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 动态计算购物车中产品数量
	 */
	public String getShopCartNo(){
		String shopCartNo = "0";
		Member memberLogin = getLoginMember();
		if(memberLogin!=null){
			shopCartNo = Integer.toString(sdshopCartService.getShopCartNo(memberLogin.getId()));
		}
		return ajax(shopCartNo, "text/html");
	}
	/**
	 * 
	 * 动态计算选中订单总价
	 */
	public String ajaxTotleAmount(){
		String totlePrice = "0.0";
		
		if(StringUtil.isNotEmpty(checkOrderSn)){
			String[] orderSns = checkOrderSn.split(",");
			BigDecimal totle = new BigDecimal("0.0");
			for(String oss : orderSns){
				if(StringUtil.isNotEmpty(oss)){
					SDOrder sdorder = sdorderService.getOrderByOrderSn(oss.trim());
					totle = totle.add(sdorder.getTotalAmount());
				}
			}
			totlePrice = totle.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}
		return ajaxHtml(totlePrice); 
	}
	/**
	 * 
	* @Title: ajaxDiscountPrice 
	* @Description: TODO(购物车根据勾选情况实时查询优惠金额和总金额) 
	* @return String    返回类型 
	* @author XXX
	 */
	@SuppressWarnings("rawtypes")
	public String ajaxDiscountPrice(){
		BigDecimal totlePrice = new BigDecimal("0");
		BigDecimal realamount = new BigDecimal("0");
		BigDecimal totalamount = new BigDecimal("0");
		Map<String, String> jsonMap=new HashMap<String, String>();
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		String num="0";
		if(StringUtil.isNotEmpty(checkOrderSn)){
			String[] orderSns = checkOrderSn.split(",");
			num=String.valueOf(orderSns.length);
			for(String oss : orderSns){
				if(StringUtil.isNotEmpty(oss)){
					SDOrder sdorder = sdorderService.getOrderByOrderSn(oss.trim());
					paramterList.add(sdorder);
				}
			}
		}else{
			return ajaxJson(jsonMap);
		}
		//筛选优惠活动
		Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(paramterList,paramterList.get(0).getChannelsn(),true);
		//遍历优惠信息Map
		Set keySet = activity_product_info1.keySet();   
	    for(Iterator it = keySet.iterator();it.hasNext();){
	    	//活动号（包含“_no_activity”）
	    	String activitysn=String.valueOf(it.next());
	    		//获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
		        Map<String,Object> map_info = activity_product_info1.get(activitysn);
		        Map activityamont_map=(Map) map_info.get("ActivityAmont");
		        String RealAmount=String.valueOf(activityamont_map.get("RealAmount"));
		        String DiscountAmount=String.valueOf(activityamont_map.get("DiscountAmount"));
		        String TotalAmount=String.valueOf(activityamont_map.get("TotalAmount"));
		        Map activityinfot_map=(Map) map_info.get("ActivityInfo");
		        if("3".equals(activityinfot_map.get("type"))){
		        	totlePrice=totlePrice.add(new BigDecimal(String.valueOf(activityamont_map.get("DiscountAmount"))));
		        }
		        realamount=realamount.add(new BigDecimal(String.valueOf(RealAmount)));
		        if("6".equals(activityinfot_map.get("type"))){
		        	totalamount=totalamount.add(new BigDecimal(String.valueOf(RealAmount)));
		        }else{
		        	totalamount=totalamount.add(new BigDecimal(String.valueOf(TotalAmount)));
		        }
	    		jsonMap.put(activitysn,RealAmount+"&"+DiscountAmount);
	    }
	    jsonMap.put("discountamount", String.valueOf(totlePrice.setScale(2,BigDecimal.ROUND_DOWN)));
	    jsonMap.put("realamount", String.valueOf(realamount.setScale(2,BigDecimal.ROUND_DOWN)));
	    jsonMap.put("totalamount", String.valueOf(totalamount.setScale(2,BigDecimal.ROUND_DOWN)));
	    jsonMap.put("ordernum",num);
		return ajaxJson(jsonMap); 
	}
	
	private String topAdInfo(DataRow row) {
		String target = "";
		String imgSrc = row.getString("AdContent");
		String width = row.getString("AdvertiseWidth");
		String height = row.getString("AdvertiseHeight");
		String alt = row.getString("PicDesc");
		if("New".equals(row.getString("PicOpenType"))){
			target = "_blank";
		}else{
			target = "_self";
		}
		
		String link = row.getString("PicLinkURL");
		
		String result = imgSrc+","+width+","+height+","+alt+","+target+","+link;
		return result;
	}
	
  public String showTopAd(){
	    //topUrl = topUrl.replaceAll("//", "/");
	    HttpServletRequest request = ServletActionContext.getRequest();
	    String josn = request.getParameter("callback");
	    if(topUrl.indexOf("order_config_new")!=-1 || topUrl.indexOf("/alipay!")!=-1 || topUrl.indexOf("/pay!")!=-1 
	    		|| topUrl.indexOf("/member_shopcart!")!=-1 || topUrl.indexOf("/pay_call_back!")!=-1){
	    	String result = ",,,,,";
			return ajaxHtml(josn+"("+result+");");
	    }
	    /*String str[] = topUrl.split("/");
	    if(topUrl.equals("/") || topUrl.equals("/index.shtml")){
	    	topUrl = ""; 
	    }else{
	    	topUrl = str[1];
	    }
	    String value = "";
	    
	    if(topUrl=="" || topUrl==null){
	    	value = "index";
	    }else{
	    	DataTable dtCode =new QueryBuilder("SELECT * FROM zdcode WHERE codetype='topadtype'").executeDataTable();
	    	//由于zdcode表中的codevalue字段过短
	    	String zixun="zixun,zhishi,wenda,baike,zhuanti,gongsi,huati,";
		    if(dtCode!=null && dtCode.getRowCount()>0){
		    	int rowCount = dtCode.getRowCount();
		    	for(int i = 0;i<rowCount;i++){
		    		String sub[] = dtCode.getString(i, "CodeValue").split(",");
		    		for(int j = 0;j<sub.length;j++){
		    			//针对咨询页范围的广告，包括咨询，知识，问答，百科，专题，公司，积分，话题
		    			if(zixun.indexOf(topUrl)!=-1){
		    				String zixun_sub=zixun.substring(zixun.indexOf(topUrl),zixun.length());
		    				value=zixun_sub.substring(0,zixun_sub.indexOf(","));
		    			}
		    			else if(topUrl.indexOf(sub[0])!=-1){
			    			value = sub[0];
			    		}else if(j>0 && topUrl.indexOf(sub[j])!=-1){
			    			value = sub[j];
			    		}
		    		}
		    		
		    	}
		    }
	    }*/
	    
	    /*String sqlPart = "";
	    
	    if("index".equals(value)){
	    	sqlPart = " AND (r.adspacetype = 'index' or r.adspacetype = 'indexAndList' or r.adspacetype = 'topAll' ) ";
	    }else if("zixun".equals(value)||"zhishi".equals(value)||"wenda".equals(value)||"baike".equals(value)||"zhuanti".equals(value)||"gongsi".equals(value)||"huati".equals(value)){
	    	sqlPart = " AND (r.adspacetype = 'zixunList' or r.adspacetype = 'indexAndList' or r.adspacetype = 'topAll') ";
	    }else if("-baoxian".equals(value)){
	    	sqlPart = " AND (r.adspacetype = 'indexAndList' or r.adspacetype = 'topAll') ";
	    }else if("chexian".equals(value)){
	    	sqlPart = " AND (r.adspacetype = 'carList') ";
	    }else{
	    	sqlPart = " AND (LOCATE('topAll', r.adspacetype) != 0) ";
	    }*/
		  
	    //String serverContext = Config.getServerContext();
	    
	    //topUrl = topUrl.replace(serverContext, "");
	    if(topUrl.equals("/") || topUrl.equals("/index.shtml")){
	    	topUrl = "index"; 
	    } else {
	    	topUrl = topUrl.substring(1);
	    }
	    
	    if (topUrl.contains("?")) {
	    	topUrl = topUrl.substring(0, topUrl.indexOf("?"));
	    }
	    Map<String,Object> resultMap = new HashMap<String,Object>();
	    String prop = "";
		
		//String sql = "SELECT i.AdContent,i.PicDesc,i.PicOpenType,i.PicLinkURL,r.AdvertiseWidth,r.AdvertiseHeight,r.prop1 FROM topadinfo i,topadrela r " +
		//			" WHERE i.starttime < NOW() AND i.endtime > NOW() AND i.active = 'Y' AND (r.prop1='' OR  r.prop1 IS NULL OR r.prop1='popup') AND i.ParentID = r.id"+sqlPart;
		
		String sql = "SELECT i.AdContent,i.PicDesc,i.PicOpenType,i.PicLinkURL,r.AdvertiseWidth,r.AdvertiseHeight,r.prop1,c.CodeValue,c.Memo FROM topadinfo i,topadrela r, zdcode c "
				+ " WHERE i.starttime < NOW() AND i.endtime > NOW() AND i.active = 'Y' AND (r.prop1='' OR  r.prop1 IS NULL OR r.prop1='popup') AND i.ParentID = r.id "
				+ " AND c.parentCode = 'TopAdType' AND LOCATE(c.codevalue, r.adspacetype) order by c.codeorder ";
		DataTable dt = new QueryBuilder(sql).executeDataTable();

	    Map<String,Object> topAllMap = new HashMap<String,Object>();
	    
		if(dt.getRowCount()>0){
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataRow row = dt.get(i);
				String memo = row.getString("Memo");
				prop = row.getString("prop1");
				prop = "popup".equals(prop) ? "popup" : "top";
				if (resultMap.isEmpty() || (!resultMap.isEmpty()
						&& !prop.equals(resultMap.get("popup")))) {
					// 列表页
					if ("list".equals(row.getString("CodeValue"))) {
						if (StringUtil.isNotEmpty(memo) && (topUrl.endsWith("/") || topUrl.endsWith("index.shtml"))) {
							String[] urlArr = memo.split(",");
							
							if (existUrl(topUrl, urlArr)) {
								resultMap.put(prop, topAdInfo(row));
							}
						}
						
					} else if (row.getString("CodeValue").endsWith("_productDetail")) {
						// 详细页
						if (StringUtil.isNotEmpty(memo) && topUrl.endsWith("shtml") && !topUrl.endsWith("index.shtml")) {
							String[] urlArr = memo.split(",");
							
							if (existUrl(topUrl, urlArr)) {
								resultMap.put(prop, topAdInfo(row));
							}
						}
						
					} else if (StringUtil.isNotEmpty(memo)) {
						String[] urlArr = memo.split(",");
						
						if (existUrl(topUrl, urlArr)) {
							resultMap.put(prop, topAdInfo(row));
						}
					}
					// 全站场合
					else{
						if ("topAll".equals(row.getString("CodeValue"))) {
							topAllMap.put(prop, topAdInfo(row));
						}
					}
				}
			}
			if (resultMap.isEmpty() && !topAllMap.isEmpty()) {
				resultMap = topAllMap;
			}
		}
		
		/*DataTable dt  = new QueryBuilder(sql).executeDataTable();
		if(dt.getRowCount()>0){
			for(int i=0;i<dt.getRowCount();i++){
				imgSrc = dt.getString(i, "AdContent");
				width = dt.getString(i, "AdvertiseWidth");
				height = dt.getString(i, "AdvertiseHeight");
				prop = dt.getString(i, "prop1");
				alt = dt.getString(i, "PicDesc");
				if("New".equals(dt.getString(i, "PicOpenType"))){
					target = "_blank";
				}else{
					target = "_self";
				}
				link = dt.getString(i, "PicLinkURL");
				
				result = imgSrc+","+width+","+height+","+alt+","+target+","+link;
				resultMap.put("popup".equals(prop)?"popup":"top", result);
			}
			if(StringUtil.isEmpty(resultMap.get("popup"))){
				String sql1 = "SELECT i.AdContent,i.PicDesc,i.PicOpenType,i.PicLinkURL,r.AdvertiseWidth,r.AdvertiseHeight,r.prop1 FROM topadinfo i,topadrela r " +
						" WHERE i.active = 'Y' AND r.prop1='popup' AND i.ParentID = r.id"+sqlPart+" order by i.endtime desc limit 1";
				DataTable dt1  = new QueryBuilder(sql1).executeDataTable();
					if(dt1.getRowCount()>0){
						imgSrc = dt1.getString(0, "AdContent");
						width = dt1.getString(0, "AdvertiseWidth");
						height = dt1.getString(0, "AdvertiseHeight");
						prop = dt1.getString(0, "prop1");
						alt = dt1.getString(0, "PicDesc");
						if("New".equals(dt1.getString(0, "PicOpenType"))){
							target = "_blank";
						}else{
							target = "_self";
						}
						link = dt1.getString(0, "PicLinkURL");
						
						result = imgSrc+","+width+","+height+","+alt+","+target+","+link;
						resultMap.put("popup", result);
					}
				
			}
			if(StringUtil.isEmpty(resultMap.get("top"))){
				String sql1 = "SELECT i.AdContent,i.PicDesc,i.PicOpenType,i.PicLinkURL,r.AdvertiseWidth,r.AdvertiseHeight,r.prop1 FROM topadinfo i,topadrela r " +
						" WHERE i.active = 'Y' AND (r.prop1='' OR  r.prop1 IS NULL) AND i.ParentID = r.id"+sqlPart+" order by i.endtime desc limit 1";
				DataTable dt1  = new QueryBuilder(sql1).executeDataTable();
					if(dt1.getRowCount()>0){
						imgSrc = dt1.getString(0, "AdContent");
						width = dt1.getString(0, "AdvertiseWidth");
						height = dt1.getString(0, "AdvertiseHeight");
						prop = dt1.getString(0, "prop1");
						alt = dt1.getString(0, "PicDesc");
						if("New".equals(dt1.getString(0, "PicOpenType"))){
							target = "_blank";
						}else{
							target = "_self";
						}
						link = dt1.getString(0, "PicLinkURL");
						
						result = imgSrc+","+width+","+height+","+alt+","+target+","+link;
						resultMap.put("top", result);
					}
			}
		}else{
			String sql1 = "SELECT i.AdContent,i.PicDesc,i.PicOpenType,i.PicLinkURL,r.AdvertiseWidth,r.AdvertiseHeight,r.prop1 FROM topadinfo i,topadrela r " +
					" WHERE i.active = 'Y' AND (r.prop1='' OR  r.prop1 IS NULL OR r.prop1='popup') AND i.ParentID = r.id"+sqlPart+" order by i.endtime desc ";
			DataTable dt1  = new QueryBuilder(sql1).executeDataTable();
			boolean mark1 = false;
			boolean mark2 = false;
			for(int i=0;i<dt1.getRowCount();i++){
				if("popup".equals(dt1.getString(i, "prop1")) && !mark1){
					mark1 = true;
					imgSrc = dt1.getString(i, "AdContent");
					width = dt1.getString(i, "AdvertiseWidth");
					height = dt1.getString(i, "AdvertiseHeight");
					prop = dt1.getString(i, "prop1");
					alt = dt1.getString(i, "PicDesc");
					if("New".equals(dt1.getString(i, "PicOpenType"))){
						target = "_blank";
					}else{
						target = "_self";
					}
					link = dt1.getString(i, "PicLinkURL");
					
					result = imgSrc+","+width+","+height+","+alt+","+target+","+link;
					resultMap.put("popup", result);
				}else if(!("popup".equals(dt1.getString(i, "prop1"))) && !mark2){
					mark2 = true;
					imgSrc = dt1.getString(i, "AdContent");
					width = dt1.getString(i, "AdvertiseWidth");
					height = dt1.getString(i, "AdvertiseHeight");
					prop = dt1.getString(i, "prop1");
					alt = dt1.getString(i, "PicDesc");
					if("New".equals(dt1.getString(i, "PicOpenType"))){
						target = "_blank";
					}else{
						target = "_self";
					}
					link = dt1.getString(i, "PicLinkURL");
					
					result = imgSrc+","+width+","+height+","+alt+","+target+","+link;
					resultMap.put("top", result);
				}
			}
		}*/
		
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return ajaxHtml(josn+"("+jsonObject.toString()+");");
//		return ajax(jsonObject.toString(), "text/html");
	}
  
	/**
	 * 判断地址栏是否包含在设定的广告中
	 * 
	 * @return
	 */
	private boolean existUrl(String paramTopUrl, String[] urlArr) {

		for (String temp : urlArr) {
			if (paramTopUrl.startsWith(temp)) {
				return true;
			}
		}
		return false;
	}
	
   public String goToCompany(){
	  QueryBuilder qb = new QueryBuilder("select * from zdconfig where type = ?");
	  
	  if(company!=null){
		  if(company.startsWith("2026")){//安盛天平
			  type =  "as_area";
			  name = "astpCar";
		  }else if(company.startsWith("2007")){//平安
			  type =  "pa_area";
			  name = "paCar";
		  }else if(company.startsWith("2021")){//大地
			  type =  "dd_area";
			  name = "ddCar";
		  }else if(company.startsWith("1087")){//阳光
			  type =  "yg_area";
			  name = "ygCar";
		  }else if(company.startsWith("2011")){//太平洋
			  type =  "tpy_area";
			  name = "tpyCar";
		  }
	  }
	  qb.add(company);
	  
	  DataTable dt = qb.executeDataTable();
	  if(dt!=null && dt.getRowCount()>0){
		  companyName = dt.getString(0, 1);
		  pageLink = dt.getString(0, 2);
	  }
  
	  return "carGoto";
  }
   
   public String saveInfo(){
	    HttpServletRequest request = ServletActionContext.getRequest();
	    Transaction trans = new Transaction();
	    
	    String url = request.getHeader("referer");
	    String partUrl = url.replace(Config.getFrontServerContextPath(), "");
	    partUrl = partUrl.substring(0,partUrl.lastIndexOf("/"));
	    
	    String wxID = "";
	    QueryBuilder qb = new QueryBuilder("SELECT * FROM precontractInfo WHERE PreconPartURL LIKE '%"+partUrl+"%' AND wxid <> '' and wxid is not null LIMIT 1");
	    DataTable dt = qb.executeDataTable();
	    if(dt.getRowCount()>0){
	    	wxID = dt.getString(0, "WXID");
	    }else{
	    	wxID = "py"+NoUtil.getMaxNo("WXID",6);
	    }
		Map<String,Object> resultMap = new HashMap<String,Object>();
		PrecontractInfoSchema precontractInfo = new PrecontractInfoSchema ();
		precontractInfo.setID(NoUtil.getMaxNo("Precon"));
		precontractInfo.setPreconName(preName);
		precontractInfo.setPreconEmail(preEmail);
		precontractInfo.setPreconPartURL(partUrl);
		precontractInfo.setPreconURL(url);
		precontractInfo.setPreconPhone(prePhone);
		precontractInfo.setPreconSex(preSex);
		precontractInfo.setCreateDate(new Date());
		precontractInfo.setWXID(wxID);
		precontractInfo.setRemark(remark);
		
		trans.add(precontractInfo,Transaction.INSERT);
		
		if (trans.commit()) {
			resultMap.put("status", "Y");
			resultMap.put("WXID", wxID);
			
			String stencilUrl = partUrl.startsWith("/") ? partUrl.substring(partUrl.indexOf("/") + 1) : partUrl;
			if (!stencilUrl.endsWith("/")) {
				stencilUrl += "/";
			}
			
			if (StringUtil.isNotEmpty(stencilUrl)) {
				QueryBuilder pConfig_qb = new QueryBuilder("SELECT CouponBatch FROM PrecontractCouponConfig WHERE StencilUrl = ?", stencilUrl);
				DataTable pConfig_dt = pConfig_qb.executeDataTable();
				if (pConfig_dt != null && pConfig_dt.getRowCount() > 0) {
					String couponBatch = pConfig_dt.getString(0, 0);
					
					Map<String, String> couponMap = getCouponInfo(couponBatch);
					// 优惠券信息不存在的情况
					if ("no".equals(couponMap.get("IsExist"))) {
						resultMap.put("status", "N");
						resultMap.put("errMsg", "预约成功，但优惠券赠送失败，您可以联系客服领取优惠券！");
					} else {
						
						String memberId = (String) request.getSession().getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME);
						String couponSn = sendCoupon(preEmail, couponMap.get("id"), couponMap.get("status"), memberId);
						
						// 验证是否登录
						if (StringUtil.isEmpty(memberId)) {
							resultMap.put("login", "N");
							resultMap.put("couponSn", couponSn);
						} else {
							resultMap.put("login", "Y");
						}
					}
				} else {
					resultMap.put("status", "N");
					logger.error("预约赠送优惠券查找失败！预约栏目URL：{}", stencilUrl);
					resultMap.put("errMsg", "预约成功，但优惠券赠送失败，您可以联系客服领取优惠券！");
				}
			} else {
				resultMap.put("status", "N");
				logger.error("预约赠送优惠券查找失败！预约栏目URL为空！");
				resultMap.put("errMsg", "预约成功，但优惠券赠送失败，您可以联系客服领取优惠券！");
			}
			
		} else {
			resultMap.put("status", "N");
			logger.error("预约数据存储失败！");
			resultMap.put("errMsg", "预约失败！");
		}
		
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
		
	}
   
	public String getOrderNum() {
		
	    HttpServletRequest request = ServletActionContext.getRequest();
	    String url = request.getHeader("referer");
	    String partUrl = url.replace(Config.getFrontServerContextPath(), "");
		partUrl = partUrl.substring(0, partUrl.lastIndexOf("/"));
	    
		QueryBuilder qb = new QueryBuilder("SELECT COUNT(*) FROM precontractInfo WHERE PreconPartURL = ?", partUrl);
	    DataTable dt = qb.executeDataTable();
	    
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (dt.getRowCount() > 0) {
			Integer orderNum = Integer.valueOf(dt.get(0, 0) + "");
			// 获取预约配置表中的初始预约数，与实际预约数相加，得到最终预约人数
			if (partUrl.startsWith("/")) {
				partUrl = partUrl.substring(1);
			}
			if (!partUrl.endsWith("/")) {
				partUrl += "/";
			}
			QueryBuilder qb2 = new QueryBuilder("SELECT Prop1 FROM PrecontractCouponConfig WHERE StencilUrl = ?", partUrl);
			DataTable dt2 = qb2.executeDataTable();
			if (dt2.getRowCount() > 0) {
				try {
					orderNum += Integer.valueOf(dt2.get(0, 0) + "");
				} catch (Exception e) {
					logger.error("预约初始人数未设置！StencilUrl：" + partUrl + e.getMessage(), e);
				}
			}
			
			resultMap.put("status", "Y");
			resultMap.put("orderNum", orderNum);
		} else {
			resultMap.put("status", "N");
		}
		
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	private Map<String, String> getCouponInfo(String batch) {
		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder("select id, status from couponinfo where batch=? order by status asc limit 0,1 ", batch);
		DataTable dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() == 0) {
			map.put("IsExist", "no");
		} else {
			map.put("id", dt.getString(0, 0));
			map.put("status", dt.getString(0, 1));
		}
		return map;
	}
	
	/**
	 * 
	 * sendCoupon:(给用户发送优惠券). <br/>
	 *
	 * @author gaohaijun
	 * @param mail
	 * @param id
	 * @param status
	 * @param memberId
	 * @return 优惠券编号
	 */
	private String sendCoupon(final String mail, String id, String status, String memberId) {
		
		final SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
		sdcouponschema.setId(id);
		Transaction trans = new Transaction();
		// 查询
		if (sdcouponschema.fill()) {
			// 数据库操作方式 1：插入 2:更新
			int operationType = 2;

			// 没有未使用的优惠券的情况 生成一张优惠券
			if (!("0".equals(status))) {
				operationType = 1;
				// 生成优惠劵ID
				String year = DateUtil.getCurrentDateTime("yyyy");
				String couponId = year + NoUtil.getMaxNo("CouponSn", 12);
				// 优惠券ID值
				sdcouponschema.setId(couponId);
				// 优惠券号
				sdcouponschema.setCouponSn(DigestUtils.md5Hex(couponId));
				// 创建时间
				sdcouponschema.setCreateDate(new Date());
				// 创建者
				sdcouponschema.setCreateUser("admin");
			}
			// 状态2已发放
			sdcouponschema.setStatus("2");
			// 发放时间
			sdcouponschema.setProp2(DateUtil.getCurrentDateTime());
			// 将会员id关联到优惠券表中
			sdcouponschema.setMemberId(memberId);
			// 邮箱
			sdcouponschema.setMail(mail);
			// 修改时间
			sdcouponschema.setModifyDate(new Date());
			// 修改者
			sdcouponschema.setModifyUser("admin");

			trans.add(sdcouponschema, operationType);

			if (trans.commit()) {
				// 邮箱地址合法的情况 发送邮件
				if (StringUtil.isMail(mail) && "Y".equals(sdcouponschema.getRemindFlag())) {
					sendMail(sdcouponschema, mail);
				} else {
					logger.warn("会员：{} 的邮箱不合法！", memberId);
				}

			} else {
				logger.error("预约增优惠券，数据库操作失败！会员id:{}", memberId);
			}
		}
		return sdcouponschema.getCouponSn();
	}

	/**
	 * 发送邮件
	 * 
	 * @param sdcouponschema
	 *            优惠券信息
	 * @param mail
	 *            邮箱地址
	 */
	private void sendMail(SDCouponInfoSchema sdcouponschema, String mail) {
		// 公共参数
		Map<String, Object> data = new HashMap<String, Object>();
		// 开始时间
		data.put("starttime", DateUtil.toString(sdcouponschema.getStartTime(), DateUtil.Format_Date));
		// 结束时间
		data.put("endtime",  DateUtil.toString(sdcouponschema.getEndTime(), DateUtil.Format_Date));
		// 优惠券编号
		data.put("couponsn", sdcouponschema.getCouponSn());
		// 优惠金额
		data.put("parValueShow", sdcouponschema.getParValue() + "元");
		// 显示活动信息
		data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
		if (ActionUtil.sendMail("wj00132", mail, data)) {
			logger.info("预约成功发优惠券，发送邮件成功");
		} else {
			logger.error("会员{} 预约成功发优惠券，邮箱：{}发送邮件失败", sdcouponschema.getMemberId(), mail);
		}
	}

	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getCheckOrderSn() {
		return checkOrderSn;
	}
	public void setCheckOrderSn(String checkOrderSn) {
		this.checkOrderSn = checkOrderSn;
	}
	public String getTopUrl() {
		return topUrl;
	}
	public void setTopUrl(String topUrl) {
		this.topUrl = topUrl;
	}
	public String getProp1() {
		return prop1;
	}
	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPageLink() {
		return pageLink;
	}
	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}
	public String getPreName() {
		return preName;
	}
	public void setPreName(String preName) {
		this.preName = preName;
	}
	public String getPreSex() {
		return preSex;
	}
	public void setPreSex(String preSex) {
		this.preSex = preSex;
	}
	public String getPreEmail() {
		return preEmail;
	}
	public void setPreEmail(String preEmail) {
		this.preEmail = preEmail;
	}
	public String getPrePhone() {
		return prePhone;
	}
	public void setPrePhone(String prePhone) {
		this.prePhone = prePhone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
