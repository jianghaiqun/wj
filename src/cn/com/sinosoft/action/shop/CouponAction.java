package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.bean.CouponInfo;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("member")
public class CouponAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1280624735273459929L;

	private String memberID;
	private int page=1;
	private int count;
	private int lastpage;
	private int ispage;
	private int pagesize = 5;
	private List<CouponInfo> colist = new ArrayList<CouponInfo>();
	
	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public int getIspage() {
		return ispage;
	}

	public void setIspage(int ispage) {
		this.ispage = ispage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public List<CouponInfo> getColist() {
		return colist;
	}

	public void setColist(List<CouponInfo> colist) {
		this.colist = colist;
	}
	
	public String queryCoupon(){
		return "query";
	}
	
	public String notUse(){
		page_Index = getRequest().getParameter("page_Index");
		if (StringUtil.isEmpty(page_Index)) {
			page = 1;
		} else {
			page = Integer.valueOf(page_Index);
		}
		memberID = getLoginMember().getId();
		
		String sqlPart = " order by startTime asc  limit  "
				+ (page - 1) * pagesize + "," + pagesize;
		String whereSql = " where status='2' and memberId = ? and UNIX_TIMESTAMP(endTime) > UNIX_TIMESTAMP(now())";
		String sql = " select couponSn, parValue,date_format(startTime,'%Y-%m-%d') as startTime, prop3, prop4,"
				   + " date_format(endTime,'%Y-%m-%d') as endTime,direction,shortName from CouponInfo";

		DataTable dt = new QueryBuilder(sql + whereSql + sqlPart, memberID).executeDataTable();
		count = new QueryBuilder("select count(1) from CouponInfo"+whereSql, memberID).executeInt();
		this.lastpage=(count+pagesize-1)/(pagesize);
			
		CouponInfo couponInfo = null;
		int i = (page - 1) * pagesize + 1;
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int k = 0; k < rowCount; k++) {
				couponInfo = new CouponInfo();
				couponInfo.setSerialno(String.valueOf(i++));
				// 优惠券号
				couponInfo.setCouponSn(dt.getString(k, "couponSn"));
				// 非折扣券
				if ("01".equals(dt.getString(k, "prop3")) || StringUtil.isEmpty(dt.getString(k, "prop3"))) {
					// 面值
					couponInfo.setParValue(dt.getString(k, "parValue"));
					couponInfo.setUnit("￥");
				} else {
					// 折扣
					couponInfo.setParValue(dt.getString(k, "prop4"));
					couponInfo.setUnit("折");
				}
				// 起始时间
				couponInfo.setStartTime(dt.getString(k, "startTime"));
				// 终止时间
				couponInfo.setEndTime(dt.getString(k, "endTime"));
				// 使用说明
				couponInfo.setDirection(dt.getString(k, "direction"));
				// 简短描述
				couponInfo.setShortName(dt.getString(k, "shortName"));
				colist.add(couponInfo);
			}
		}
		setActionAlias("coupon!notUse.action");
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		page_count = String.valueOf(lastpage);
		getPageDataList(param_map);
		return "notuse";
	}
	
	public String used(){
		page_Index = getRequest().getParameter("page_Index");
		if (StringUtil.isEmpty(page_Index)) {
			page = 1;
		} else {
			page = Integer.valueOf(page_Index);
		}
		memberID = getLoginMember().getId();
		
		String sqlPart = " order by payTime asc  limit  "
				+ (page - 1) * pagesize + "," + pagesize;
		String whereSql = " from CouponInfo where status='1' and memberId = ? ";
		String sql = " select couponSn, parValue,date_format(payTime,'%Y-%m-%d %H:%i:%S') as payTime, prop3, prop4,shortName,"
				   + " date_format(startTime,'%Y-%m-%d') as startTime,date_format(endTime,'%Y-%m-%d') as endTime,orderSn ";
		
		count = new QueryBuilder("select count(1) "+whereSql, memberID).executeInt();
		DataTable dt = new QueryBuilder(sql + whereSql + sqlPart, memberID).executeDataTable();
		
		this.lastpage=(count+pagesize-1)/(pagesize);
		CouponInfo couponInfo = null;
		int i = (page - 1) * pagesize + 1;
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int k = 0; k < rowCount; k++) {
				couponInfo = new CouponInfo();
				couponInfo.setSerialno(String.valueOf(i++));
				// 优惠券号
				couponInfo.setCouponSn(dt.getString(k, "couponSn"));
				// 非折扣券
				if ("01".equals(dt.getString(k, "prop3")) || StringUtil.isEmpty(dt.getString(k, "prop3"))) {
					// 面值
					couponInfo.setParValue(dt.getString(k, "parValue"));
					couponInfo.setUnit("￥");
				} else {
					// 折扣
					couponInfo.setParValue(dt.getString(k, "prop4"));
					couponInfo.setUnit("折");
				}
				// 起始时间
				couponInfo.setStartTime(dt.getString(k, "startTime"));
				// 终止时间
				couponInfo.setEndTime(dt.getString(k, "endTime"));
				// 使用
				couponInfo.setPayTime(dt.getString(k, "payTime"));
				// 简短描述
				couponInfo.setShortName(dt.getString(k, "shortName"));
				// 订单号(购物车只显示一个订单)
				QueryBuilder qb=new QueryBuilder("select ordersn from sdorders where paysn=? limit 0,1 ",StringUtil.isNotEmpty(dt.getString(k, "orderSn"))?dt.getString(k, "orderSn"):"未知");
				couponInfo.setOrderSn(qb.executeString());
				colist.add(couponInfo);
			}
		}
		setActionAlias("coupon!used.action");
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		page_count = String.valueOf(lastpage);
		getPageDataList(param_map);
		return "used";
	}
	
	public String overTime(){
		page_Index = getRequest().getParameter("page_Index");
		if (StringUtil.isEmpty(page_Index)) {
			page = 1;
		} else {
			page = Integer.valueOf(page_Index);
		}
		memberID = getLoginMember().getId();
		
		String sqlPart = " order by startTime asc  limit  "
				+ (page - 1) * pagesize + "," + pagesize;
		String whereSql = " where memberId = ? and (status = '5' or (status = '2' and UNIX_TIMESTAMP(endTime) < UNIX_TIMESTAMP(now()))) ";
		String sql = " select couponSn, parValue,date_format(startTime,'%Y-%m-%d') as startTime, prop3, prop4,"
				   + " date_format(endTime,'%Y-%m-%d') as endTime,direction,shortName from CouponInfo ";
		
		count = new QueryBuilder("select count(1) from CouponInfo "+whereSql, memberID).executeInt();
		DataTable dt = new QueryBuilder(sql + whereSql + sqlPart, memberID).executeDataTable();
		
		this.lastpage=(count+pagesize-1)/(pagesize);
		CouponInfo couponInfo = null;
		int i = (page - 1) * pagesize + 1;
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int k = 0; k < rowCount; k++) {
				couponInfo = new CouponInfo();
				couponInfo.setSerialno(String.valueOf(i++));
				// 优惠券号
				couponInfo.setCouponSn(dt.getString(k, "couponSn"));
				// 非折扣券
				if ("01".equals(dt.getString(k, "prop3")) || StringUtil.isEmpty(dt.getString(k, "prop3"))) {
					// 面值
					couponInfo.setParValue(dt.getString(k, "parValue"));
					couponInfo.setUnit("￥");
				} else {
					// 折扣
					couponInfo.setParValue(dt.getString(k, "prop4"));
					couponInfo.setUnit("折");
				}
				// 起始时间
				couponInfo.setStartTime(dt.getString(k, "startTime"));
				// 终止时间
				couponInfo.setEndTime(dt.getString(k, "endTime"));
				// 使用说明
				couponInfo.setDirection(dt.getString(k, "direction"));
				// 简短描述
				couponInfo.setShortName(dt.getString(k, "shortName"));
				
				colist.add(couponInfo);
			}
		}
 
		setActionAlias("coupon!overTime.action");
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		page_count = String.valueOf(lastpage);
		getPageDataList(param_map);
		return "overTime";
	}
	/**
	 * 
	* @Title: couponJh 
	* @Description: TODO(优惠券激活) 
	* @return String    返回类型 
	* @author zhangjing
	 */
	public String couponJh() {
		memberID = getLoginMember().getId();
		String couponsn = getParameter("couponsn");
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("success", "");
		// 优惠劵有效性校验
		if (couponsn == null || "".equals(couponsn)) {
			jsonMap.put("false", "激活失败，您的激活码不存在！");
			return ajaxJson(jsonMap);
		}
/*		QueryBuilder qbExist = new QueryBuilder("select id from couponinfo where couponsn=? and status='2' and (memberid!='' and  memberid is not null)");
		qbExist.add(couponsn);
		DataTable dtExist = qbExist.executeDataTable();
		if(dtExist!=null && dtExist.getRowCount()>0){
			jsonMap.put("false", "该优惠劵已被激活绑定，不能重复激活。");
			return ajaxJson(jsonMap);
		}*/
		
		QueryBuilder qb = new QueryBuilder("select id, status, memberid from couponinfo where couponsn=? "); // and status='2' and (memberid='' or  memberid is null) 
		qb.add(couponsn);
		DataTable dt = qb.executeDataTable();
		if(dt.getRowCount()!=1){
			jsonMap.put("false", "优惠券激活数目异常,请联系客服人员!");
			logger.error("优惠券激活数目异常,优惠券号为:{};会员id为:{}", couponsn, memberID);
			return ajaxJson(jsonMap);
		}else if(!"2".equals(dt.getString(0, "status"))){
			jsonMap.put("false", "该优惠劵状态异常。");
			return ajaxJson(jsonMap);
		}else if(StringUtil.isNotEmpty(dt.getString(0, "memberid"))){
			jsonMap.put("false", "该优惠劵已被激活绑定，不能重复激活。");
			return ajaxJson(jsonMap);
		}else if (dt.getRowCount() == 1) {
			QueryBuilder qb_update = new QueryBuilder("update couponinfo set memberid=? where couponsn=?");
			qb_update.add(memberID);
			qb_update.add(couponsn);
			if (qb_update.executeNoQuery() != 1) {
				jsonMap.put("false", "优惠券激活失败,优惠券号为:" + couponsn + ";会员id为:" + memberID);
				logger.error("优惠券激活失败,优惠券号为:{};会员id为:{}", couponsn, memberID);
				return ajaxJson(jsonMap);
			}
			jsonMap.put("success", "恭喜您优惠券激活成功！");
			return ajaxJson(jsonMap);
		} else {
			jsonMap.put("false", "激活失败，请检查您的激活码是否有误！");
			return ajaxJson(jsonMap);
		}
	}
}
