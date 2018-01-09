package com.sinosoft.datachannel;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class MemberRankManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.MemberRankManager";

	public boolean isRunning(long id) {
		return false;
	} 

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("-1", "会员等级");
		return map;
	}

	public void execute(long id) {
		if ("-1".equals(id + "")) {
			new QueryBuilder("INSERT INTO PrecMember  " +
					"  SELECT  a.applicantName,a.applicantBirthday,a.applicantSex," +
					"  a.applicantMobile,a.applicantMail,s.ordersn,s.memberid,s.createDate,s.totalAmount " +
					"  FROM sdorders s,sdinformation i,sdinformationappnt a " +
					"  WHERE  s.ordersn=i.ordersn AND i.informationsn = a.informationsn " +
					"  AND s.memberid IS NOT NULL AND s.memberid !=''"+
                    " AND s.orderstatus in (7,10)  AND s.createdate <=NOW() " +
					"  AND s.createdate>(SELECT IF(MAX(createdate) IS NULL ,'',MAX(createdate)) FROM PrecMember )").executeNoQuery();
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "会员等级";
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.sinosoft.datachannel.MemberRankManager";
	}

}
