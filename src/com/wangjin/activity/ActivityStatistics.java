/**
 * 
 */
package com.wangjin.activity;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.infoseeker.QueryUtil;

/**
 * @author wangcaiyun
 *
 */
public class ActivityStatistics extends Page {

	/**
	 * 推荐活动统计初始化
	 * @param params
	 * @return
	 */
	public static Mapx<String, String> initReco(Mapx<String, String> params) {
		params.put("referrerType",HtmlUtil.mapxToOptions(CacheManager.getMapx("Code", "Actvity.referrerType"), true));
		return params;
	}
	
	/**
	 * 推荐活动统计查询
	 * @param dga
	 */
	public static void recoDataBind(DataGridAction dga) {
		// 推荐人手机号
		String referrerMobileNo = dga.getParams().getString("referrerMobileNo");
		// 推荐人邮箱
		String referrerEmail = dga.getParams().getString("referrerEmail");
		// 被推荐人手机号
		String recoMobileNo = dga.getParams().getString("recoMobileNo");
		// 被推荐人邮箱
		String recoEmail = dga.getParams().getString("recoEmail");
		// 统计起始时间
		String createDate = dga.getParams().getString("createDate");
		// 统计结束时间
		String endCreateDate = dga.getParams().getString("endCreateDate");
		// 优惠券归属
		String referrerType = dga.getParams().getString("referrerType");
		// 优惠券批次号
		String batch = dga.getParams().getString("batch");
		// 优惠券码
		String couponSn = dga.getParams().getString("couponSn");
		// 优惠券状态
		String couponStatus = dga.getParams().getString("couponStatus");
		// 商家订单号
		String paySn = dga.getParams().getString("paySn");
		// 订单渠道
		String channelsn = dga.getParams().getString("channelsn");
		String channel = QueryUtil.getChannelInfo(channelsn, "");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select concat(m1.mobileNo, '/', m1.email) as referrerInfo, ");
		sb.append("concat(m2.mobileNo, '/', m2.email) as recommendedInfo, c.batch, r.couponSn, o.paySn, ");
		sb.append("(select codename from zdcode where codetype='Actvity.referrerType' and codevalue=r.referrerType) as referrerTypeName, ");
		sb.append("(select codename from zdcode where codetype='Coupon.status' and codevalue=c.status) as couponStatusName, ");
		sb.append("(select ChannelName from ChannelInfo where ChannelCode=o.channelSn) as channelName ");
		sb.append("from RecommendActivityStatistics r, member m1, member m2, couponinfo c ");
		sb.append("left join sdorders o on c.orderSn=o.paySn and c.status='1' ");
		sb.append("where r.couponSn=c.couponSn and r.referrerMemberId=m1.id and r.recommendedMemberId=m2.id ");
		if (StringUtil.isNotEmpty(referrerMobileNo)) {
			sb.append("and m1.mobileNO = '"+referrerMobileNo.trim()+"' ");
		}
		if (StringUtil.isNotEmpty(referrerEmail)) {
			sb.append("and m1.email = '"+referrerEmail.trim()+"' ");
		}
		if (StringUtil.isNotEmpty(recoMobileNo)) {
			sb.append("and m2.mobileNO = '"+recoMobileNo.trim()+"' ");
		}
		if (StringUtil.isNotEmpty(recoEmail)) {
			sb.append("and m2.email = '"+recoEmail.trim()+"' ");
		}
		if (StringUtil.isNotEmpty(createDate)) {
			sb.append("and r.createDate >= '"+createDate.trim()+" 00:00:00' ");
		}
		if (StringUtil.isNotEmpty(endCreateDate)) {
			sb.append("and r.createDate <= '"+endCreateDate+" 23:59:59' ");
		}
		if (StringUtil.isNotEmpty(referrerType)) {
			sb.append("and r.referrerType = '"+referrerType.trim()+"' ");
		}
		if (StringUtil.isNotEmpty(batch)) {
			sb.append("and c.batch = '"+batch.trim()+"' ");
		}
		if (StringUtil.isNotEmpty(couponSn)) {
			sb.append("and r.couponSn = '"+couponSn.trim()+"' ");
		}
		if (StringUtil.isNotEmpty(couponStatus)) {
			sb.append("and c.status = '"+couponStatus.trim()+"' ");
		}
		if (StringUtil.isNotEmpty(paySn)) {
			sb.append("and (c.status='1' and o.paySn = '"+paySn.trim()+"') ");
		}
		if (StringUtil.isNotEmpty(channel)) {
			sb.append("and (c.status='1' and o.channelsn in ("+channel+")) ");
		}
		sb.append("group by r.id order by r.createDate desc ");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dga.bindData(dt);
	}
}
