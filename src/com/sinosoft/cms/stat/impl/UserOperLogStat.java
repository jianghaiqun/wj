package com.sinosoft.cms.stat.impl;

import com.sinosoft.cms.stat.AbstractRecordStat;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.UserOperLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;

import java.util.Date;

/**
 * 查看产品日志记录
 */
public class UserOperLogStat extends AbstractRecordStat {

	public void deal(Visit v) {
		try {
			Transaction transaction = new Transaction();
			if (StringUtil.isNotEmpty(v.Operation)) {
				ZCArticleSchema tZCArticleSchema = new ZCArticleSchema();
				ZCArticleSet tZCArticleSet = tZCArticleSchema.query(new QueryBuilder("where ID='" + v.Operation + "'"));
				if (tZCArticleSet.size() > 0) {
					UserOperLogSchema tUserOperLogSchema = new UserOperLogSchema();
					tUserOperLogSchema.setID(NoUtil.getMaxID("UserOperLogID") + "");
					tUserOperLogSchema.setIP(v.IP);
					if(v.StartTime != null){
						tUserOperLogSchema.setAccessDate(DateUtil.toString(new Date(v.StartTime), "yyyy-MM-dd"));
						tUserOperLogSchema.setAccessTime(DateUtil.toTimeString(new Date(v.StartTime)));
					}
					if(v.LeaveTime != null){
						tUserOperLogSchema.setLeaveDate(DateUtil.toString(new Date(v.LeaveTime), "yyyy-MM-dd"));
						tUserOperLogSchema.setLeaveTime(DateUtil.toTimeString(new Date(v.LeaveTime)));
					}
					tUserOperLogSchema.setAccessPage(v.URL);// 访问页面
					tUserOperLogSchema.setSessionID(v.SessionID);
					tUserOperLogSchema.setArea(v.District);
					tUserOperLogSchema.setMemberID(v.MemberID);
					tUserOperLogSchema.setTerminalType(super.checkMobile(v));
					tUserOperLogSchema.setChannel(v.channel);
					tUserOperLogSchema.setStayTime(String.valueOf(v.StayTime));// 明细记录页面停留时间
					tUserOperLogSchema.setOperation(v.Operation);// 用户操作日志（查看产品代码）
					tUserOperLogSchema.setIsJumpOrClose(v.IsJumpOrClose);
					transaction.add(tUserOperLogSchema, Transaction.INSERT);
					transaction.commit();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
