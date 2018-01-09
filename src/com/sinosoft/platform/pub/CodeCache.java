package com.sinosoft.platform.pub;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.cache.CacheProvider;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

public class CodeCache extends CacheProvider {
	public String getProviderName() {
		return "Code";
	}

	public String getName() {
		return "代码缓存";
	}

	public String getID() {
		return "Code";
	}

	public void onKeyNotFound(String type, Object key) {
		if (!"true".equals(Config.getValue("App.ExistPlatformDB"))) {
			return;
		}
		// 不需要做操作,单项代码不可能超过20000行
		Object value = new QueryBuilder("select CodeName from ZDCode where CodeType=? and CodeValue=? order by CodeOrder,CodeValue", type, key).executeOneValue();
		CacheManager.set(this.getProviderName(), type, key, value);
	}

	public void onTypeNotFound(String type) {
		if (!"true".equals(Config.getValue("App.ExistPlatformDB"))) {
			return;
		}
		DataTable dt = new QueryBuilder("select CodeType,ParentCode,CodeName,CodeValue from ZDCode where CodeType=? order by CodeOrder,CodeValue", type).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			String codetype = dr.getString("CodeType");
			String parentcode = dr.getString("ParentCode");
			if (parentcode.equals("System")) {
				continue;
			}
			CacheManager.set(this.getProviderName(), codetype, dr.get("CodeValue"), dr.get("CodeName"));
		}
	}
}
