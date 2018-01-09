package com.sinosoft.bbs;

import java.util.Date;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCPostSchema;

public class MyPost extends Ajax {

	public static void getMyPost(DataListAction dla) {
		String addTime = dla.getParams().getString("addtime");
		String ascdesc = dla.getParams().getString("ascdesc");

		QueryBuilder qb = new QueryBuilder("select p.*, t.Subject TSubject, f.Name "
				+ "from ZCPost p, ZCTheme t, ZCForum f where p.SiteID=?"
				+ " and p.ThemeID=t.ID and p.Invisible='Y' and t.ForumID=f.ID and p.first='N'"
				+ " and p.AddUser=? and t.status='Y' and t.VerifyFlag='Y' ", User.getUserName(), dla.getParam("SiteID"));

		ForumPriv priv = new ForumPriv(dla.getParam("SiteID"));
		if (!priv.hasPriv("AllowPanel")) {
			return;
		}
		if (!StringUtil.isEmpty(addTime) && !"0".equals(addTime)) {
			Date date = new Date((new Date().getTime() - Long.parseLong(addTime)));
			qb.append(" and p.addTime >?", date);
		}

		qb.append(" order by p.AddTime");

		if (!StringUtil.isEmpty(ascdesc)) {
			if ("DESC".equals(ascdesc))
				qb.append(" desc ");
		} else {
			qb.append(" desc");
		}
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("AuditStatus");
		dt.insertColumn("Operation");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "VerifyFlag").equals("Y")) {
				dt.set(i, "AuditStatus", "正常");
				if (dt.get(i, "ApplyDel") == null) {
					dt.set(i, "Operation", "<a href='javascript:applyDel(" + dt.get(i, "ID") + ")'>申请删除</a>");
				} else {
					dt.set(i, "Operation", "已申请删除");
				}
			} else {
				dt.set(i, "AuditStatus", "待审核");
				dt.set(i, "Operation", "<cite><a href='javascript:editPost(" + dt.get(i, "ID") + ","
						+ dt.get(i, "ForumID") + "," + dt.get(i, "ThemeID")
						+ ")'>修改</a></cite> <em><a href='javascript:del(" + dt.get(i, "ID") + ")' >删除</a></em>");
			}
		}
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		params.put("AddUser", User.getUserName());
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public void del() {
		String ID = $V("ID");
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		post.setID(ID);
		post.fill();
		post.setInvisible("N");
		trans.add(post, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}

	}
}
