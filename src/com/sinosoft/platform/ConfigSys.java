package com.sinosoft.platform;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDConfigSchema;
import com.sinosoft.schema.ZDConfigSet;


public class ConfigSys extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String SearchType = (String) dga.getParams().get("SearchType");
		QueryBuilder qb = new QueryBuilder(
				"select type,name,value,type as type_key from zdconfig where type not like ? ");
		qb.add("System.");// 不显示System.开头的配置项
		if (StringUtil.isNotEmpty(SearchType)) {
			qb.append(" and (type like ? or name like ?)");
			qb.add("%" + SearchType + "%");
			qb.add("%" + SearchType + "%");
		}
		dga.bindData(qb);
	}

	public void add() {
		ZDConfigSchema zdconfig = new ZDConfigSchema();
		zdconfig.setValue(Request);
		zdconfig.setAddTime(new Date());
		zdconfig.setAddUser(User.getUserName());
		if (zdconfig.getType().startsWith("System.")) {
			Response.setStatus(0);
			Response.setMessage("不允许添加以“System.”开头的配置项！");
			return;
		}
		if (zdconfig.insert()) {
			
			Config.update();
			Response.setStatus(1);
			Response.setMessage("新增类别成功！");
			//加redis缓存 先清后加
			delZDConfigMapFromRedis();
			setZDConfigMapIntoRedis();
			
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	
	}

	public void del() {
		String ids = $V("IDs");
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		ZDConfigSchema zdconfig = new ZDConfigSchema();
		ZDConfigSet set = zdconfig.query(new QueryBuilder("where type in ('" + ids + "')"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		// 日志
		StringBuffer configLog = new StringBuffer("删除配置项:");
		if (set.size() > 0) {
			configLog.append(set.get(0).getName() + " 等");
		}
		if (trans.commit()) {
			Config.update();
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCONFIG, configLog + "成功", Request.getClientIP());
			Response.setStatus(1);
			Response.setMessage("删除成功！");
			//加redis缓存 先清后加
			delZDConfigMapFromRedis();
			setZDConfigMapIntoRedis();
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCONFIG, configLog + "失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
		
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder("update ZDConfig set type=?,name=?,value=? where type=?");
			qb.add(dt.getString(i, "type"));
			qb.add(dt.getString(i, "name"));
			qb.add(dt.getString(i, "value"));
			qb.add(dt.getString(i, "type_key"));
			trans.add(qb);
		}
		if (trans.commit()) {
			Config.update();
			Response.setStatus(1);
			Response.setMessage("修改成功!");
			//加redis缓存 先清后加
			delZDConfigMapFromRedis();
			setZDConfigMapIntoRedis();
		} else {
			Response.setStatus(0);
			Response.setMessage("修改失败!");
		}
	}
	/**
	 * setZDConfigMapIntoRedis:(将ZDConfig表数据加载到redis的ZDConfigMap中 type为key
	 * value为value). <br/>
	 *
	 * @author miaozhiqiang
	 */
	public void setZDConfigMapIntoRedis() {
		Map<String, String> zdconfigMap = new HashMap<String, String>();
		String queryAllZDConfigFromDB = "select type,value from zdconfig";
        QueryBuilder zdconfigQB = new QueryBuilder(queryAllZDConfigFromDB); 
        DataTable zdconfigDT = zdconfigQB.executeDataTable();
        
		for (DataRow zdconfigDR : zdconfigDT) {
			zdconfigMap.put(zdconfigDR.getString("type"), zdconfigDR.getString("value"));
		}
		if (zdconfigMap.size()>0) {
			JedisCommonUtil.setMap(0, "ZDConfigMap", zdconfigMap);
		} 
	}
	
	/**
	 * delZDConfigMapFromRedis:(清除ZDConfigMap). <br/>
	 *
	 * @author miaozhiqiang
	 */
	public void delZDConfigMapFromRedis() {

		Set<String> redisKeys = JedisCommonUtil.findKeys(0,"ZDConfigMap");
		if (redisKeys.size()!=0) {
			String[] keyArray =   (String[]) redisKeys.toArray(new String[redisKeys.size()]);
			JedisCommonUtil.remove(0,keyArray);
		}
	}
}
