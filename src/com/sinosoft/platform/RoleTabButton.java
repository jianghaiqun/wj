package com.sinosoft.platform;

import com.sinosoft.framework.Page;
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

public class RoleTabButton extends Page {

	/**
	 * 角色管理-菜单权限页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
		params.put("ButtonTypeList", HtmlUtil.codeToCheckboxes("ButtonType", "ButtonType"));
		return params;
	}

	/**
	 * 角色管理-菜单权限页面初始化
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String SiteID = dga.getParam("SiteID");
		if (StringUtil.isEmpty(SiteID)) {
			SiteID = dga.getParam("OldSiteID");
			if (StringUtil.isEmpty(SiteID)) {
				SiteID = Application.getCurrentSiteID() + "";
			}
		}
		final String Site = SiteID;
		final String roleCode = dga.getParam("RoleCode");
		String sql = "select ID,ParentID,Name,Icon,'' as Code,Addtime,Memo, '' as TreeLevel,Type,'0' as 'TT','' URL,'' OnClick from ZDMenu where Visiable='Y' order by OrderFlag,id";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return RolePriv.getRolePriv(new String[] { roleCode }, Priv.MENU, Site + "-" + dr.getString("ID"), Priv.MENU_BROWSE);
			}
		});

		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("2".equals(dt.get(i, "Type"))) {
				dt.set(i, "TreeLevel", "1");
			} else {
				dt.set(i, "TreeLevel", "0");
			}
		}

		String sqlButton = "select CONCAT(ParentID ,'-',id) as ID,ParentID,Name, Icon ,Code ,Addtime,Memo, '3' as TreeLevel,'','1' as 'TT', URL ,OnClick from ZDButton order by OrderFlag,id";
		DataTable dtButton = new QueryBuilder(sqlButton).executeDataTable();

		DataTable dtResult = new DataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			Object ID = dt.get(i, "ID");
			dt.set(i, "ID", ID);
			dtResult.insertRow(dt.get(i));
			if (!"0".equals(dt.get(i, "ParentID"))) {
				for (int j = 0; j < dtButton.getRowCount(); j++) {
					if (ID.equals(dtButton.get(j, "ParentID"))) {
						DataRow dr = dtButton.get(j);
						dtResult.insertRow(dr);
					}
				}
			}
		}
		dga.bindData(dtResult);
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
		// 菜单
		QueryBuilder qb = new QueryBuilder("select ID from ZDPrivilege where OwnerType=? and Owner=? and PrivType='" + Priv.MENU + "' and ID like ? and Value='1'", RolePriv.OWNERTYPE_ROLE, RoleCode);
		qb.add(SiteID + "-%");
		DataTable dt = qb.executeDataTable();
		// 按钮
		QueryBuilder qb_button = new QueryBuilder("select ID from ZDPrivilege where OwnerType=? and Owner=? and PrivType='" + Priv.BUTTON + "' and ID like ? and Value='1'", RolePriv.OWNERTYPE_ROLE,
				RoleCode);
		qb_button.add(SiteID + "-%");
		DataTable dt_button = qb_button.executeDataTable();
		if (dt_button != null && dt_button.getRowCount() > 0) {
			for (int i = 0; i < dt_button.getRowCount(); i++) {
				dt.insertRow(dt_button.get(i));
			}
		}
		Response.put("checkedMenu", StringUtil.join(dt.getColumnValues(0)));
	}

	/**
	 * 数据保存
	 */
	public void save() {
		String RoleCode = $V("RoleCode");
		String SiteID = $V("SiteID");
		DataTable dt = (DataTable) Request.get("dt");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setLogInfo(0, "保存失败!");
			return;
		}
		Transaction trans = new Transaction();

		// 先删除 后插入
		QueryBuilder qb = new QueryBuilder("where OwnerType=? and Owner=? and PrivType='" + Priv.BUTTON + "' and ID like ?", RolePriv.OWNERTYPE_ROLE, RoleCode);
		qb.add(SiteID + "-%");
		trans.add(new ZDPrivilegeSchema().query(qb), Transaction.DELETE_AND_BACKUP);

		// 数据格式为SiteID-菜单id-按钮id
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "ID").indexOf("-") != -1 && "1".equals(dt.getString(i, Priv.BUTTON_BROWSE))) {
				ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
				priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
				priv.setOwner(RoleCode);
				priv.setID(SiteID + "-" + dt.getString(i, "ID"));
				priv.setPrivType(Priv.BUTTON);
				priv.setCode(Priv.BUTTON_BROWSE);
				priv.setValue(dt.getString(i, Priv.BUTTON_BROWSE));
				trans.add(priv, Transaction.INSERT);
			}
		}
		if (trans.commit()) {
			RolePriv.updatePriv(RoleCode, Priv.BUTTON);
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}
}
