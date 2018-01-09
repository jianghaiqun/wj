package com.sinosoft.platform;

import java.util.Map;

import com.sinosoft.cms.site.Catalog;
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

public class RoleTabCatalog extends Page {

	public final static Mapx PrivTypeMap = new Mapx();

	static {
		PrivTypeMap.put(Priv.ARTICLE, "文章栏目");
		PrivTypeMap.put(Priv.IMAGE, "图片栏目");
		PrivTypeMap.put(Priv.VIDEO, "视频栏目");
		PrivTypeMap.put(Priv.AUDIO, "音频栏目");
		PrivTypeMap.put(Priv.ATTACH, "附件栏目");
	}

	public final static Mapx CatalogTypeMap = new Mapx();

	static {
		CatalogTypeMap.put(Priv.ARTICLE, Catalog.CATALOG);
		CatalogTypeMap.put(Priv.IMAGE, Catalog.IMAGELIB);
		CatalogTypeMap.put(Priv.VIDEO, Catalog.VIDEOLIB);
		CatalogTypeMap.put(Priv.AUDIO, Catalog.AUDIOLIB);
		CatalogTypeMap.put(Priv.ATTACH, Catalog.ATTACHMENTLIB);
	}

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

		String PrivType = params.getString("PrivType");
		if (StringUtil.isEmpty(PrivType)) {
			PrivType = params.getString("OldPrivType");
			if (StringUtil.isEmpty(PrivType)) {
				PrivType = Priv.ARTICLE;
			}
		}
		params.put("PrivType", HtmlUtil.mapxToOptions(PrivTypeMap, PrivType));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String roleCode = dga.getParam("RoleCode");
		String siteID = dga.getParam("SiteID");
		if (StringUtil.isEmpty(siteID)) {
			siteID = dga.getParam("OldSiteID");
			if (StringUtil.isEmpty(siteID)) {
				siteID = Application.getCurrentSiteID() + "";
			}
			if (StringUtil.isNotEmpty(siteID)) {
				if (!RolePriv.getRolePriv(new String[] { roleCode }, Priv.SITE, siteID, Priv.SITE_BROWSE)) {
					siteID = "";
				}
			}
		}
		if (StringUtil.isEmpty(siteID)) {
			DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag ,id").executeDataTable();
			dt = dt.filter(new Filter(roleCode) {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return RolePriv.getRolePriv(new String[] { (String) this.Param }, Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
				}
			});
			if (dt.getRowCount() > 0) {
				siteID = dt.getString(0, "ID");
			} else {
				dga.bindData(new DataTable());
				return;
			}
		}

		String PrivType = dga.getParam("PrivType");
		if (StringUtil.isEmpty(PrivType)) {
			PrivType = dga.getParam("OldPrivType");
			if (StringUtil.isEmpty(PrivType)) {
				PrivType = Priv.ARTICLE;
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + roleCode + "' as RoleCode");
		Object[] ks = ((Mapx) Priv.PRIV_MAP.get(PrivType)).keyArray();
		for (int i = 0; i < ((Mapx) Priv.PRIV_MAP.get(PrivType)).size(); i++) {
			sb.append(",'' as " + ks[i]);
		}

		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType" + sb.toString() + ",'' as ParentInnerCode from ZCSite a where a.ID = ?";
		DataTable siteDT = new QueryBuilder(sql, Long.parseLong(siteID)).executeDataTable();
		// 置被选中的值
		Map PrivTypeMap = RolePriv.getPrivTypeMap(roleCode, Priv.SITE);
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			DataRow dr = siteDT.getDataRow(i);
			Map mapRow = (Map) PrivTypeMap.get(siteDT.getString(i, "ID"));
			if (mapRow != null) {
				for (int j = 0; j < dr.getColumnCount(); j++) {
					if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
						Object o = mapRow.get(dr.getDataColumn(j).getColumnName().toLowerCase());
						dr.set(j, "1".equals(o) ? "√" : "");
					}
				}
			}
		}
		String catalogType = CatalogTypeMap.getString(PrivType);
		QueryBuilder qb = new QueryBuilder("select InnerCode as ID,Name,TreeLevel ,'" + PrivType + "' as PrivType" + sb.toString()
				+ ", (select b.InnerCode from ZCCatalog b where a.parentid=b.id) as ParentInnerCode from ZCCatalog a where Type = " + catalogType + " and a.SiteID = " + siteID
				+ " order by orderflag,innercode ");
		DataTable catalogDT = qb.executeDataTable();
		catalogDT = DataGridAction.sortTreeDataTable(catalogDT, "ID", "ParentInnerCode");
		PrivTypeMap = RolePriv.getPrivTypeMap(roleCode, PrivType);
		for (int i = 0; i < catalogDT.getRowCount(); i++) {
			DataRow dr = catalogDT.getDataRow(i);
			Map mapRow = (Map) PrivTypeMap.get(dr.getString("ID"));
			if (mapRow != null) {
				for (int j = 0; j < dr.getColumnCount(); j++) {
					if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
						Object o = mapRow.get(dr.getDataColumn(j).getColumnName().toLowerCase());
						dr.set(j, "1".equals(o) ? "√" : "");
					}
				}
			}
		}
		catalogDT.insertRow(siteDT.getDataRow(0), 0);
		catalogDT = DataGridAction.sortTreeDataTable(catalogDT, "ID", "ParentInnerCode");
		dga.bindData(catalogDT);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		String RoleCode = $V("RoleCode");
		String PrivType = $V("PrivType");
		long SiteID = Long.parseLong($V("SiteID"));
		Transaction trans = new Transaction();

		ZDPrivilegeSchema p = new ZDPrivilegeSchema();
		QueryBuilder qb1 = new QueryBuilder("where OwnerType='" + RolePriv.OWNERTYPE_ROLE + "' and Owner=? and PrivType=?"
				+ " and exists (select '1' from zccatalog where innercode=ZDPrivilege.id and siteid=?)", RoleCode, PrivType);
		qb1.add(SiteID);
		trans.add(p.query(qb1), Transaction.DELETE_AND_BACKUP);
		QueryBuilder qb2 = new QueryBuilder("where OwnerType='" + RolePriv.OWNERTYPE_ROLE + "' and Owner=? and PrivType='site' and ID=?", RoleCode, SiteID + "");
		qb2.append(" and code like ?", PrivType + "_%");
		trans.add(p.query(qb2), Transaction.DELETE_AND_BACKUP);

		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
					priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
					priv.setOwner(dr.getString("RoleCode"));
					priv.setID(dr.getString("ID"));
					priv.setPrivType(dr.getString("PrivType"));
					priv.setCode(dr.getDataColumn(j).getColumnName().toLowerCase());
					priv.setValue("".equals(dr.getString(j)) ? "0" : "1");
					trans.add(priv, Transaction.INSERT);
				}
			}
		}
		if (trans.commit()) {
			RolePriv.updateAllPriv(RoleCode);
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

}
