package com.sinosoft.bbs;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class ThemeSearch extends Ajax {
	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		if (StringUtil.isEmpty(User.getUserName())) {
			params.put("redirect", "<script>window.location='Index.jsp?SiteID=" + SiteID + "'</script>");
			return params;
		}
		ForumPriv priv = new ForumPriv(SiteID);
		if (!priv.hasPriv("AllowSearch")) {
			params.put("redirect", "<script>window.location='Index.jsp?SiteID=" + SiteID + "'</script>");
			return params;
		}

		params.put("AddUser", User.getUserName());
		params.put("Priv", ForumUtil.initPriv(params.getString("SiteID")));
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public static void dg1DataBind(DataListAction dla) {
		String SiteID = dla.getParam("SiteID");
		ForumPriv priv = new ForumPriv(SiteID);
		if (!priv.hasPriv("AllowSearch")) {
			return;
		}
		String searchAddUser = dla.getParams().getString("SearchAddUser").replace('*', '%');
		String searchSubject = dla.getParams().getString("SearchSubject");

		QueryBuilder qb = new QueryBuilder("select * from ZCTheme where SiteID=? and Status='Y' ", SiteID);
		if (StringUtil.isNotEmpty(searchAddUser)) {
			qb.append(" and AddUser like ?", searchAddUser);
		}
		if (StringUtil.isNotEmpty(searchSubject)) {
			qb.append(" and Subject like ?", "%" + searchSubject + "%");
		}
		qb.append(" order by ID desc");
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public static Mapx initAddDialog(Mapx params) {
		return params;
	}
}
