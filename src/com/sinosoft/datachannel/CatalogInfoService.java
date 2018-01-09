package com.sinosoft.datachannel;

import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Framework;
import com.sinosoft.framework.RequestImpl;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;

/**
 * 向其他ZCMS应用提供本应用下允许采集/分发的站点和栏目列表
 * 
 */
public class CatalogInfoService extends Ajax {
	public void getRemoteSiteInfo() {
		String url = $V("ServerAddr");
		if (url.equalsIgnoreCase("localhost")) {
			getSiteInfo();
		} else {
			Mapx map = Framework.callRemoteMethod(url, "com.sinosoft.datachannel.CatalogInfoService.getSiteInfo", Request);
			if (map == null) {
				return;
			}
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				$S(ks[i].toString(), map.get(ks[i]));
			}
		}
	}

	public void getRemoteCatalogInfo() {
		String url = $V("ServerAddr");
		if (url.equalsIgnoreCase("localhost")) {
			getCatalogInfo();
		} else {
			Mapx map = Framework.callRemoteMethod(url, "com.sinosoft.datachannel.CatalogInfoService.getCatalogInfo",
					Request);
			if (map == null) {
				return;
			}
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				$S(ks[i].toString(), map.get(ks[i]));
			}
		}
	}

	public void getSiteInfo() {
		String type = $V("Type");
		if ("Gather".equalsIgnoreCase(type)) {
			type = "Gather";
		} else {
			type = "Deploy";
		}
		if ("admin".equalsIgnoreCase(User.getUserName()) && User.isLogin()) {
			//推送中站点排序
			DataTable dt = new QueryBuilder("select ID,Name from ZCSite order by id").executeDataTable();
			$S("SiteTable", dt);
		} else {
			DataTable dt = new QueryBuilder("select ID,Name from ZCSite where "
					+ "exists (select 1 from ZCCatalogConfig where SiteID=ZCSite.ID and AllowInner" + type
					+ "='Y')  order by id").executeDataTable();
			$S("SiteTable", dt);
		}
	}

	public void getCatalogInfo() {
		String type = $V("Type");
		if ("Gather".equalsIgnoreCase(type)) {
			type = "Gather";
		} else {
			type = "Deploy";
		}
		long siteID = Long.parseLong($V("SiteID"));
		DataTable dt = null;
		if ("admin".equalsIgnoreCase(User.getUserName()) && User.isLogin()) {
			dt = new QueryBuilder(
					"select ID,Name,ParentID,TreeLevel from ZCCatalog where SiteID=? and Type=?  order by orderFlag,innercode",
					siteID, Catalog.CATALOG).executeDataTable();
		} else {
			dt = new QueryBuilder("select ID,Name,ParentID,TreeLevel from ZCCatalog where SiteID=? and Type=? and "
					+ "exists (select 1 from ZCCatalogConfig where CatalogID=ZCCatalog.ID and AllowInner" + type
					+ "='Y')  order by orderFlag,innercode", siteID, Catalog.CATALOG).executeDataTable();
		}
		dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String prefix = "";
			for (int j = 1; j < dt.getInt(i, "TreeLevel"); j++) {
				prefix += "　";
			}
			dt.set(i, "Name", prefix + dt.getString(i, "Name"));
		}
		$S("CatalogTable", dt);

	}

	public static DataTable getLocalSites(Mapx params) {
		String type = params.getString("Type");
		CatalogInfoService info = new CatalogInfoService();
		RequestImpl request = new RequestImpl();
		request.put("Type", type);
		info.setRequest(request);
		info.getSiteInfo();
		DataTable dt = info.getResponse().getDataTable("SiteTable");
		return dt;
	}
}
