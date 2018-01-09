package com.sinosoft.datachannel;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class BalanceStatusManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.BalanceStatusManager";
	
	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "结算数据处理定时调用");
		return map;
	}
	
	public void execute(long id) {
		if ("0".equals(id + "")) {
			Transaction transaction = new Transaction();
			String sql = "update sdinformationrisktype set balanceStatus = '', modifyDate = now() where modifyDate < date_add(now(), interval -24 hour) and appStatus='1' and balanceStatus = '2' ";// 当前时间之前未发生结算交互,BalanceFlag表示未与结算中心发生交互，appStatus表示经代通与保险公司交互成功
			QueryBuilder qb = new QueryBuilder(sql);
			transaction.add(qb);
			transaction.commit();
		}
	}
	
	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "结算数据处理任务调用";
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
		return "com.sinosoft.datachannel.BalanceStatusManager";
	}
}
