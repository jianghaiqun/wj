package com.sinosoft.bbs.admin;

/**
 * 分数设置
 * @author wst
 * 
 */
import java.util.Date;

import com.sinosoft.bbs.ForumUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCForumScoreSchema;
import com.sinosoft.schema.ZCForumScoreSet;

public class ForumScoreSetting extends Page {

	public static Mapx init(Mapx params) {
		ZCForumScoreSet set = new ZCForumScoreSchema().query(new QueryBuilder("where SiteID=?", ForumUtil.getCurrentBBSSiteID()));
		if (set.size() > 0) {
			params = set.get(0).toMapx();
		}
		return params;
	}

	public void save() {
		Transaction trans = new Transaction();
		ZCForumScoreSchema forumScore = new ZCForumScoreSchema();
		if(StringUtil.isNotEmpty($V("ID"))){
			forumScore.setID($V("ID"));
			forumScore.fill();
			forumScore.setValue(Request);
			trans.add(forumScore, Transaction.UPDATE);
		}else{
			forumScore.setID(NoUtil.getMaxID("ForumScoreID"));
			forumScore.setValue(Request);
			forumScore.setAddUser(User.getUserName());
			forumScore.setAddTime(new Date());
			trans.add(forumScore, Transaction.INSERT);
		}
		if (trans.commit()) {
			CacheManager.set("Forum", "Score", forumScore.getID(), forumScore);
			Response.setLogInfo(1, "操作成功！");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}
}
