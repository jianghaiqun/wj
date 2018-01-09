package com.sinosoft.platform;

import java.util.LinkedList;
import java.util.List;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDPrivilegeSchema;
public class UserTabMenu extends Page {

	/**
	 * 得到有权限的站点下拉框
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx init(Mapx params) {
		String userName = params.getString("UserName");
		DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag ,id")
				.executeDataTable();
		dt = dt.filter(new Filter(userName) {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv((String) this.Param, Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
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

	/**
	 * 显示所有菜单
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in(select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y' order by OrderFlag";
		if (!"admin".equals(User.getUserName())) {
//			sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and parentid not in('122','37')and id not in('122','37') and visiable='Y'order by OrderFlag";
			//mysql
//			sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and parentid in('120','0')and id not in('122','37','123','124','125') and visiable='Y'order by OrderFlag";
			//sybase
			sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and parentid in(120,0)and id not in(122,37,123,124,125) and visiable='Y'order by OrderFlag";
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

	/**
	 * 得到站点下有权限的菜单项
	 * 
	 */
	public void getCheckedMenu() {
		String UserName = $V("UserName");
		if (StringUtil.isEmpty(UserName)) {
			Response.put("checkedMenu", "");
			return;
		}
		String SiteID = $V("SiteID");
		List list = new LinkedList();
		String sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y' order by OrderFlag";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Priv.getPriv(UserName, Priv.MENU, SiteID + "-" + dt.getString(i, "ID"), Priv.MENU_BROWSE)) {
				list.add(dt.getString(i, "ID"));
			}
		}
		Response.put("checkedMenu", StringUtil.join(list.toArray()));
	}

	/**
	 * 保存菜单权限
	 * 
	 */
	public void save() {
		String UserName = $V("UserName");
		long SiteID = Long.parseLong($V("SiteID"));
		DataTable resultDT = (DataTable) Request.get("dt");
		String[] RoleCodes = new String[0];
		List roleCodeList = PubFun.getRoleCodesByUserName(UserName);
		if (roleCodeList != null && roleCodeList.size() != 0) {
			RoleCodes = (String[]) roleCodeList.toArray(new String[roleCodeList.size()]);
		}
		DataColumn[] types = resultDT.getDataColumns();
		DataColumn[] copyTypes = new DataColumn[types.length];
		System.arraycopy(types, 0, copyTypes, 0, types.length);
		Object[][] copyValues = new Object[resultDT.getRowCount()][types.length];
		for (int i = 0; i < copyValues.length; i++) {
			System.arraycopy(resultDT.getDataRow(i).getDataValues(), 0, copyValues[i], 0, types.length);
		}
		DataTable rolePrivDT = new DataTable(copyTypes, copyValues);
		for (int i = 0; i < rolePrivDT.getRowCount(); i++) {
			for (int j = 0; j < rolePrivDT.getColCount(); j++) {
				if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					rolePrivDT.set(i, j, RolePriv.getRolePriv(RoleCodes, Priv.MENU, SiteID + "-"
							+ rolePrivDT.getString(i, "ID"), rolePrivDT.getDataColumn(j).getColumnName().toLowerCase()) ? "1" : "0");
				}
			}
		}

		// 对比结果DT和角色权限DT
		String v1 = null;
		String v2 = null;
		for (int i = 0; i < rolePrivDT.getRowCount(); i++) {
			for (int j = 0; j < rolePrivDT.getColCount(); j++) {
				if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					v1 = rolePrivDT.getString(i, j);
					v2 = resultDT.getString(i, j);
					if (v1.equals(v2)) {
						resultDT.set(i, j, "0");
					} else if ("0".equals(v1)) {
						if ("1".equals(v2)) {
							resultDT.set(i, j, "1");
						}
					} else if ("1".equals(v1)) {
						if ("0".equals(v2)) {
							resultDT.set(i, j, "-1");
						}
					}
				}
			}
		}

		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(" where OwnerType=? and Owner=? and PrivType='" + Priv.MENU
				+ "' and ID like ?", Priv.OWNERTYPE_USER, $V("UserName"));
		qb.add(SiteID + "%");
		trans.add(new ZDPrivilegeSchema().query(qb), Transaction.DELETE_AND_BACKUP);
		for (int i = 0; i < resultDT.getRowCount(); i++) {
			if ("1".equals(resultDT.getString(i, Priv.MENU_BROWSE))
					|| "-1".equals(resultDT.getString(i, Priv.MENU_BROWSE))) {
				ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
				priv.setOwnerType(Priv.OWNERTYPE_USER);
				priv.setOwner(UserName);
				priv.setID(SiteID + "-" + resultDT.getString(i, "ID"));
				priv.setPrivType(Priv.MENU);
				priv.setCode(Priv.MENU_BROWSE);
				priv.setValue(resultDT.getString(i, Priv.MENU_BROWSE));
				trans.add(priv, Transaction.INSERT);
			}
		}

		if (trans.commit()) {
			Priv.updatePriv(UserName, Priv.MENU);
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}
}
