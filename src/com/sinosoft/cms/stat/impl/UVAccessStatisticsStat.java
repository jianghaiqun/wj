package com.sinosoft.cms.stat.impl;

import com.sinosoft.cms.stat.AbstractRecordStat;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.UVAccessStatisticsSchema;
import com.sinosoft.schema.UVAccessStatisticsSet;

import java.util.Date;

/**
 * UV统计量各指标处理
 */
public class UVAccessStatisticsStat extends AbstractRecordStat {

	public void deal(Visit v) {
		try {
			UVAccessStatisticsSchema tUVASSchema = new UVAccessStatisticsSchema();
			String ip = v.IP;
			String accessDate = "";
			if(v.StartTime != null){
				accessDate = DateUtil.toString(new Date(v.StartTime),"yyyy-MM-dd");
			}
			String leaveDate = "";
			if(v.LeaveTime != null){
				leaveDate = DateUtil.toString(new Date(v.LeaveTime),"yyyy-MM-dd");
			}
			UVAccessStatisticsSet tUVASSet = tUVASSchema.query(new QueryBuilder("where IP='" + ip + "' and AccessDate='" + accessDate + "'"));
			if (tUVASSet.size() == 0 || !accessDate.equals(leaveDate)) {
				Transaction transaction = new Transaction();
				tUVASSchema.setID(NoUtil.getMaxID("UVAccessStatisticsID") + "");
				tUVASSchema.setIP(ip);
				if(v.StartTime != null){
					tUVASSchema.setAccessDate(accessDate);
					tUVASSchema.setAccessTime(DateUtil.toTimeString(new Date(v.StartTime)));
				}
				if(v.LeaveTime != null){
					tUVASSchema.setLeaveDate(leaveDate);
					tUVASSchema.setLeaveTime(DateUtil.toTimeString(new Date(v.LeaveTime)));
				}
				tUVASSchema.setSessionID(v.SessionID);
				tUVASSchema.setArea(v.District);
				tUVASSchema.setReferer(v.Referer);
				if(v.URL != null || v.Referer != null){
					String[] strE = super.getSearchEngine(v);
					if (strE != null) {
						tUVASSchema.setEntry(strE[1]);
					}
				}
				tUVASSchema.setMemberID(v.MemberID);
				tUVASSchema.setTerminalType(super.checkMobile(v));
				tUVASSchema.setOSType(v.OS);
				tUVASSchema.setExplorerType(v.Browser);
				tUVASSchema.setScreenResolution(v.Screen);
				tUVASSchema.setISSupportCookie(v.CookieEnabled == true ? "1" : "0");
				tUVASSchema.setISSupportFlash(v.FlashEnabled == true ? v.FlashVersion : "0");
				tUVASSchema.setLanguage(v.Language);
				tUVASSchema.setChannel(v.channel);
				tUVASSchema.setIsJumpOrClose(v.IsJumpOrClose);
				transaction.add(tUVASSchema, Transaction.INSERT);
				transaction.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
