package com.sinosoft.platform;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDPrivilegeSchema;

public class RoleTabMenu extends Page {

	public static Mapx init(Mapx params) {
		String roleCode = params.getString("RoleCode");
		DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag ,id").executeDataTable();
		dt = dt.filter(new Filter(roleCode) {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return RolePriv.getRolePriv(new String[] { (String) this.Param }, Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
			}
		});
		String SiteID = params.getString("SiteID");
		if (StringUtil.isEmpty(SiteID)) {
			SiteID = params.getString("OldSiteID");
			if (StringUtil.isEmpty(SiteID)) {
				SiteID = Application.getCurrentSiteID() + "";
			}
		}
		params.put("SiteID", HtmlUtil.dataTableToOptions(dt, SiteID));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		// String sql =
		// "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y'order by OrderFlag";
		// DataTable dt = new QueryBuilder(sql).executeDataTable();
		// if (!"admin".equals(User.getUserName())) {
		// dt = dt.filter(new Filter() {
		// public boolean filter(Object obj) {
		// DataRow dr = (DataRow) obj;
		// return Priv.getPriv(User.getUserName(), Priv.MENU,
		// dr.getString("id"), Priv.MENU_BROWSE);
		// }
		// });
		// }
		// for (int i = 0; i < dt.getRowCount(); i++) {
		// if ("2".equals(dt.get(i, "Type"))) {
		// dt.set(i, "TreeLevel", "1");
		// } else {
		// dt.set(i, "TreeLevel", "0");
		// }
		// }
		// dga.bindData(dt);
		String sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y'order by OrderFlag";

		// if (!"admin".equals(User.getUserName())) {
		// dt = dt.filter(new Filter() {
		// public boolean filter(Object obj) {
		// DataRow dr = (DataRow) obj;
		// return Priv.getPriv(User.getUserName(), Priv.MENU,
		// dr.getString("id"), Priv.MENU_BROWSE);
		// }
		// });
		// }
		if (!"admin".equals(User.getUserName())) {
			sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and parentid not in('122','37')and id not in('122','37') and visiable='Y'order by OrderFlag";
		}
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("2".equals(dt.get(i, "Type"))) {
				dt.set(i, "TreeLevel", "1");
			} else {
				dt.set(i, "TreeLevel", "0");
			}
		}
		dga.bindData(dt);
	}

	public void getCheckedMenu() {
		String RoleCode = $V("RoleCode");
		if (StringUtil.isEmpty(RoleCode)) {
			RoleCode = $V("Role.LastRoleCode");
			if (StringUtil.isEmpty(RoleCode)) {
				Response.put("checkedMenu", "");
				return;
			}
		}
		String SiteID = $V("SiteID");
		QueryBuilder qb = new QueryBuilder("select ID from ZDPrivilege where OwnerType=? and Owner=? and PrivType='" + Priv.MENU + "' and ID like ? and Value='1'", RolePriv.OWNERTYPE_ROLE, RoleCode);
		qb.add(SiteID + "-%");

		DataTable dt = qb.executeDataTable();
		Response.put("checkedMenu", StringUtil.join(dt.getColumnValues(0)));
	}

	public void save() {
		String RoleCode = $V("RoleCode");
		String SiteID = $V("SiteID");
		DataTable dt = (DataTable) Request.get("dt");
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder("where OwnerType=? and Owner=? and PrivType='" + Priv.MENU + "' and ID like ?", RolePriv.OWNERTYPE_ROLE, RoleCode);
		qb.add(SiteID + "-%");
		trans.add(new ZDPrivilegeSchema().query(qb), Transaction.DELETE_AND_BACKUP);
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("1".equals(dt.getString(i, Priv.MENU_BROWSE))) {
				ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
				priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
				priv.setOwner(RoleCode);
				priv.setID(SiteID + "-" + dt.getString(i, "ID"));
				priv.setPrivType(Priv.MENU);
				priv.setCode(Priv.MENU_BROWSE);
				priv.setValue(dt.getString(i, Priv.MENU_BROWSE));
				trans.add(priv, Transaction.INSERT);
			}
		}

		if (trans.commit()) {
			RolePriv.updatePriv(RoleCode, Priv.MENU);
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}
}
