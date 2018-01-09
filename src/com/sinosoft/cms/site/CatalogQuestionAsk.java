/**问答导入菜单
 * <p>Date        :2012-06-05</p> 
 * <p>Module      :CMS </p> 
 * <p>Description :问答导入页面左侧菜单</p> 
 * <p>Remark      :CatalogQuestionAsk.java</p> 
 * @author  wangcaiyun
 * @version 1.0 
 * <p>------------------------------------------------------------</p> 
 * <p>  修改历史</p> 
 * <p>  序号   日期                修改人     修改原因</p> 
 */
package com.sinosoft.cms.site;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;

import java.util.List;

public class CatalogQuestionAsk extends Page { 

	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		long siteID = (obj != null) ? Long.parseLong(obj.toString()) : Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : CatalogCJWT.CATALOG;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.DocList.LastCatalog");
		}
		String[] codes = getSelectedCatalogList(IDs, CatalogShowConfig.getArticleCatalogShowLevel());

		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ?");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(Long.parseLong(parentTreeLevel));
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			if (!CatalogShowConfig.isArticleCatalogLoadAllChild()) {
				qb.append(" and TreeLevel<?", Integer.parseInt(parentTreeLevel) + 3);
				ta.setExpand(false);
			} else {
				ta.setExpand(true);
			}
			qb.append(" order by orderflag,innercode");
			dt = qb.executeDataTable();
		} else {
			ta.setLevel(CatalogShowConfig.getArticleCatalogShowLevel());
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel-1 <=? and innercode like '002308%' order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			prepareSelectedCatalogData(dt, codes, catalogType, siteID, ta.getLevel());
		}

		String siteName = "文档库";
		if (catalogType == CatalogCJWT.SHOP_GOODS) {
			siteName = "商品库";
		} else if (catalogType == CatalogCJWT.SHOP_GOODS_BRAND) {
			siteName = "商品品牌库";
		}
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});

		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// 单选
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// 多选、全选
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}
		ta.bindData(dt);
		addSelectedBranches(ta, codes);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}
	
	/**
	 * 编辑时要根据ID预先选中树形的某引起节点
	 */
	public static void prepareSelectedCatalogData(DataTable dt, String[] codes, int catalogType, long siteID, int level) {
		for (int i = 0; i < codes.length; i++) {
			String innerCode = codes[i];
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type=? and SiteID=? and TreeLevel>? and InnerCode like ? "
							+ "order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(level + 1);
			qb.add(innerCode + "%");
			DataTable dt2 = qb.executeDataTable();
			dt.union(dt2);
		}
	}
	
	/**
	 * 编辑时要根据ID预先选中树形的某引起节点
	 */
	public static String[] getSelectedCatalogList(String IDs, int level) {
		Mapx map = new Mapx();
		if (StringUtil.isNotEmpty(IDs)) {
			String[] arr = IDs.split("\\,");
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i])) {
					String innerCode = CatalogUtil.getInnerCode(arr[i]);
					if (StringUtil.isNotEmpty(innerCode)) {
						if (innerCode.length() > level * 6) {
							innerCode = innerCode.substring(0, level * 6);
							map.put(innerCode, "1");
						}
					}
				}
			}
		}
		String[] codes = new String[map.size()];
		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			codes[i] = ks[i].toString();
		}
		return codes;
	}
	
	/**
	 * 编辑时要根据ID预先选中树形的某引起节点
	 */
	public static void addSelectedBranches(TreeAction ta, String[] codes) {
		List list = ta.getItemList();
		for (int i = 0; i < list.size(); i++) {
			TreeItem item = (TreeItem) list.get(i);
			if (item.isRoot()) {
				continue;
			}
			for (int j = 0; j < codes.length; j++) {
				if (item.getData().getString("InnerCode").equals(codes[j].toString())) {
					item.setLazy(false);
					item.setExpanded(true);
					try {
						int level = ta.getLevel();
						ta.setLevel(1000);
						ta.addChild(item);
						ta.setLevel(level);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);

					}
				}
			}
		}
	}
}
