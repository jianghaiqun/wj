package com.sinosoft.cms.dataservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.TopAdInfoSchema;
import com.sinosoft.schema.TopAdInfoSet;
import com.sinosoft.schema.TopAdRelaSchema;
import com.sinosoft.schema.TopAdRelaSet;

public class AdvertiseManage extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String SearchContent = (String) dga.getParam("SearchContent");
		QueryBuilder qb = new QueryBuilder("SELECT * FROM TopAdRela ");
		if (StringUtil.isNotEmpty(SearchContent)) {
			qb.append(" where AdSpaceName like ?", "%" + SearchContent.trim()
					+ "%");
		}
		qb.append("  order by createdate asc");
		DataTable dt = qb.executeDataTable();
		DataTable newdt = new DataTable(dt.getDataColumns(), null);
		for (int i = dga.getPageIndex() * dga.getPageSize(); i < dt
				.getRowCount()
				&& i < (dga.getPageIndex() + 1) * dga.getPageSize(); i++) {
			newdt.insertRow(dt.getDataRow(i));
		}
		// newdt.decodeColumn("AdSpaceType", selectPosTypes());
		newdt.insertColumn("AdSpaceTypeNs");
		for (int i = 0; i < newdt.getRowCount(); i++) {
			newdt.set(i, "AdSpaceTypeNs",
					getAdSpaceTypeNs(newdt.getString(i, "AdSpaceType")));
		}
		dga.setTotal(dt.getRowCount());
		dga.bindData(newdt);
	}

	/**
	 * 获取逗号分隔的类型对应的名称
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getAdSpaceTypeNs(String adSpaceTypes) {
		String result = "";
		String[] arr = adSpaceTypes.split(",");

		Mapx posTypes = selectPosTypes();
		for (String temp : arr) {
			if (StringUtil.isEmpty(result)) {
				result = (String) posTypes.get(temp);
			} else {
				result += "," + (String) posTypes.get(temp);
			}
		}

		return result;
	}

	public static Mapx DialogInit(Mapx params) {
		String id = (String) params.get("ID");
		// 修改
		if (StringUtil.isNotEmpty(id)) {
			TopAdRelaSchema TopAdRelaSchema = new TopAdRelaSchema();
			TopAdRelaSchema.setID(id);
			TopAdRelaSchema.fill();
			Mapx mapx = TopAdRelaSchema.toMapx();
			mapx.remove("AdSpaceType");
			params.putAll(mapx);
		}
		return params;
	}

	public void add() {
		TopAdRelaSchema topAdRela = new TopAdRelaSchema();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String id = $V("ID");
		String adSpaceName = $V("AdSpaceName");
		// 修改
		if (StringUtil.isNotEmpty(id)) {
			int info = new QueryBuilder(
					"SELECT * FROM TopAdinfo WHERE parentid='" + id
							+ "' AND active = 'Y'").executeInt();
			if (info > 0) {
				Response.setLogInfo(0, "此广告位下，已有正在启用中的活动，不可修改！");
				return;
			}
			topAdRela.setID(id);
			topAdRela.fill();
			if (!adSpaceName.equals(topAdRela.getAdSpaceName())) {
				int NameCount = new QueryBuilder(
						"SELECT COUNT(*) FROM TopAdRela WHERE AdSpaceName =? ",
						adSpaceName).executeInt();
				if (NameCount > 0) {
					Response.setLogInfo(0, "已经有同名的广告位，请您重新填写广告位名");
					return;
				}
			}
			topAdRela.setValue(Request);
		} else { // 新增
			int NameCount = new QueryBuilder(
					"SELECT COUNT(*) FROM TopAdRela WHERE AdSpaceName =? ",
					adSpaceName).executeInt();
			if (NameCount > 0) {
				Response.setLogInfo(0, "已经有同名的广告位，请您重新填写广告位名");
				return;
			}
			topAdRela.setValue(Request);
			topAdRela.setID(NoUtil.getMaxID("TopAdRelaID", "SN") + "");
			topAdRela.setAdSpaceName(adSpaceName);
			topAdRela.setAuthor(User.getUserName());
			topAdRela.setCreateDate(sdf.format(new Date()));
		}
		// 修改
		if (StringUtil.isNotEmpty(id)) {
			if (topAdRela.update()) {
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "发生错误");
			}
		} else { // 新增
			if (topAdRela.insert()) {
				Response.setLogInfo(1, "新增成功");
			} else {
				Response.setLogInfo(0, "发生错误");
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		TopAdRelaSchema topAdRela = new TopAdRelaSchema();
		TopAdInfoSchema topAdInfo = new TopAdInfoSchema();
		TopAdRelaSet set = topAdRela.query(new QueryBuilder("where id in ("
				+ ids + ")"));
		TopAdInfoSet adSet = topAdInfo.query(new QueryBuilder("where ID in ("
				+ ids + ")"));
		trans.add(set, Transaction.DELETE);
		trans.add(adSet, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除广告位成功！");
		} else {
			Response.setLogInfo(0, "操作数据库时发生错误!");
		}
	}

	/**
	 * 初始化广告位类型树
	 * 
	 * @param ta
	 */
	public static void initPosTypes(TreeAction ta) {

		DataTable dt = new QueryBuilder(
				"select CodeValue,'' as ParentID,'1' as TreeLevel,CodeName,'' as Checked "
						+ "FROM zdcode WHERE codetype='topadtype' AND parentCode = 'TopAdType' ORDER BY codeorder")
				.executeDataTable();
		ta.setRootText("广告位类型");
		ta.setIdentifierColumnName("CodeValue");
		ta.bindData(dt);
	}

	/**
	 * 编辑场合初始化广告位类型树
	 * 
	 * @param ta
	 */
	public static void editPosTypes(TreeAction ta) {

		DataTable dt = new QueryBuilder(
				"SELECT c.CodeValue,'' AS ParentID,'1' AS TreeLevel,c.CodeName,CASE WHEN t.AdSpaceType IS NOT NULL THEN 'Checked' ELSE '' END AS Checked FROM zdcode c  "
						+ "LEFT JOIN TopAdRela t ON LOCATE(c.CodeValue, t.AdSpaceType) > 0 AND t.id = ? WHERE c.codetype='topadtype' AND c.parentCode = 'TopAdType' ORDER BY c.codeorder",
				ta.getParam("ID")).executeDataTable();
		ta.setRootText("广告位类型");
		ta.setIdentifierColumnName("CodeValue");
		ta.bindData(dt);
	}

	/**
	 * 获取广告类型
	 * 
	 * @return
	 */
	public static Mapx selectPosTypes() {
		Mapx posTypes = new Mapx();
		QueryBuilder qb = new QueryBuilder(
				"SELECT * FROM zdcode WHERE codetype='topadtype' AND parentCode = 'TopAdType' ORDER BY codeorder");
		DataTable dt = qb.executeDataTable();
		posTypes = dt.toMapx("CodeValue", "CodeName");
		return posTypes;
	}
}
