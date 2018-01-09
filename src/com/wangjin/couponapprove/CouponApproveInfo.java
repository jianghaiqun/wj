package com.wangjin.couponapprove;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;

/**
 * 优惠券审批信息表管理
 * @author guozc
 * @date 2017-02-15
 */
public class CouponApproveInfo extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String couponBatch = dga.getParams().getString("couponBatch");
		String approveStatus = dga.getParams().getString("approveStatus");
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.id,a.couponBatch,a.couponApplicant,a.approveStatus,b.codename as approveStatusName,a.remark1,a.remark2,a.remark3,date_format(a.createDate,'%Y-%c-%d %H:%i:%s') as createDate,a.createUser");
		listSql.append(" from CouponApproveInfo a left join zdcode b on a.approveStatus = b.codevalue where b.codetype = 'CouponApproveInfo.status' and a.remark2 != '0' ");
		if (StringUtil.isNotEmpty(couponBatch)) {
			listSql.append(" and a.couponBatch like '%" + couponBatch.trim() + "%'");
		}
		if(StringUtil.isNotEmpty(approveStatus)){
			listSql.append(" and a.approveStatus = '"+approveStatus+"' ");
		}
		QueryBuilder qb = new QueryBuilder(listSql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {

		return params;
	}
	
	public static Mapx<String, Object> init(Mapx<String, Object> params) {
		Map<String,Object> approveStatus = new HashMap<String,Object>();
		approveStatus.put("2", "不通过");
		approveStatus.put("1", "通过");
		params.put("approveStatus", HtmlUtil.mapxToOptions(approveStatus));
		params.put("approveStatusOpts", HtmlUtil.codeToOptions("CouponApproveInfo.status",true));
		return params;
	}
	
	public void approve(){
		String ids = $V("ids");
		String approveStatus = $V("approveStatus");
		String remark1 = $V("remark1");
		String ccMail = $V("ccMail");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		for(String id : ids.split(",")){
			QueryBuilder qb = new QueryBuilder("update CouponApproveInfo set approveStatus = ?,"
					+ "remark1 = ?,createDate = ?,createUser = ? where id = ?");
			qb.add(approveStatus);
			qb.add(remark1);
			qb.add(new Date());
			qb.add(User.getUserName());
			qb.add(id);
			trans.add(qb);
		}
		if(trans.commit()){
			Response.setLogInfo(1, "审批成功!");
			sendMailToApply(ids,approveStatus,remark1,ccMail);
		}else{
			Response.setLogInfo(0, "审批失败!");
		}
	}
	
	/**
	 * 审批后给申请人发送审批结果邮件
	 */
	private void sendMailToApply(String ids,String approveStatus,String remark1,String ccMail){
		StringBuffer emailContent = null;
		for(String id : ids.split(",")){
			emailContent = new StringBuffer();
			QueryBuilder qb = new QueryBuilder("select a.couponBatch,b.email from CouponApproveInfo a,zduser b "
					+ "where a.couponApplicant = b.username and a.id = ?");
			qb.add(id);
			DataTable dt = qb.executeDataTable();
			emailContent.append("您好！<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;批次号为");
			emailContent.append(dt.getString(0, 0));
			emailContent.append("的新生成优惠券已经审核完毕,审核结果:");
			if("1".equals(approveStatus)){
				emailContent.append("<font color='green'>通过</font>");
			}else{
				emailContent.append("<font color='red'>不通过</font>");
			}
			if(StringUtil.isNotEmpty(remark1)){
				emailContent.append(" 审核意见:"+remark1);
			}
			try {
				ActionUtil.sendGeneralMail(dt.getString(0, "email"), ccMail, "开心保-优惠券生成申请结果通知", emailContent.toString());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
}