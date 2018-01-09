package com.sinosoft.bbs.admin;

import java.util.Date;

import com.sinosoft.bbs.ForumUtil;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.ZCForumConfigSchema;
import com.sinosoft.schema.ZCForumConfigSet;

public class ForumSetting extends Ajax {
	
	public static Mapx init(Mapx params){
		long siteID = ForumUtil.getCurrentBBSSiteID();
		ZCForumConfigSet set = new ZCForumConfigSchema().query(new QueryBuilder("where SiteID = ?", siteID));
		if(set.size()==1){
			params = set.get(0).toMapx();
		}
		DataTable dt = new QueryBuilder("select TempCloseFlag from ZCForumConfig where SiteID = ?", siteID).executeDataTable();
		Mapx map = new Mapx();
		map.put("Y", "是");
		map.put("N", "否");
		params.put("TempCloseFlag", HtmlUtil.mapxToRadios("TempCloseFlag",map, dt.getRowCount()>0?dt.getString(0,"TempCloseFlag"):""));
		return params;
	}
	
	public void add() {
		Transaction trans=new Transaction();
		
		ZCForumConfigSchema config = new ZCForumConfigSchema();
		config.setID($V("ID"));
		config.fill();
		config.setSiteID(ForumUtil.getCurrentBBSSiteID());
		config.setValue(Request);
		config.setAddTime(new Date());
		config.setAddUser(User.getUserName());
		trans.add(config, Transaction.DELETE_AND_INSERT);

		if(trans.commit()){
			CacheManager.set("Forum", "Config", config.getID(), config);
			Response.setLogInfo(1, "操作成功!");
		}else{
			Response.setLogInfo(0, "操作失败!");
		}
		
	}
}
