package com.sinosoft.cms.stat.impl;

import com.sinosoft.cms.stat.AbstractRecordStat;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.PVStatisticsSchema;

import java.util.Date;

/**
 * PV统计量各指标处理
 */
public class PVStatisticsStat extends AbstractRecordStat {

	public void deal(Visit v) {
		try {
			Transaction transaction = new Transaction();
			PVStatisticsSchema tPVStatisticsSchema = new PVStatisticsSchema();
			tPVStatisticsSchema.setID(NoUtil.getMaxID("PVStatisticsStatID")	+ "");
			tPVStatisticsSchema.setIP(v.IP);
			if(v.StartTime != null){
				tPVStatisticsSchema.setAccessDate(DateUtil.toString(new Date(v.StartTime), "yyyy-MM-dd"));
				tPVStatisticsSchema.setAccessTime(DateUtil.toTimeString(new Date(v.StartTime)));
			}
			if(v.LeaveTime != null){
				tPVStatisticsSchema.setLeaveDate(DateUtil.toString(new Date(v.LeaveTime), "yyyy-MM-dd"));
				tPVStatisticsSchema.setLeaveTime(DateUtil.toTimeString(new Date(v.LeaveTime)));
			}
			tPVStatisticsSchema.setAccessPage(v.URL);// 访问页面
			tPVStatisticsSchema.setSessionID(v.SessionID);
			tPVStatisticsSchema.setArea(v.District);
			tPVStatisticsSchema.setReferer(v.Referer);
			if (v.URL != null || v.Referer != null) {
				String[] strE = super.getSearchEngine(v);
				if (strE != null) {
					tPVStatisticsSchema.setEntry(strE[1]);
				}
			}
			tPVStatisticsSchema.setMemberID(v.MemberID);
			tPVStatisticsSchema.setTerminalType(super.checkMobile(v));
			tPVStatisticsSchema.setChannel(v.channel);
			tPVStatisticsSchema.setStayTime(String.valueOf(v.StayTime));// 明细记录页面停留时间
			tPVStatisticsSchema.setIsJumpOrClose(v.IsJumpOrClose);
			transaction.add(tPVStatisticsSchema, Transaction.INSERT);
			transaction.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
