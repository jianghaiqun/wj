package com.sinosoft.cms.document;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.utility.ArticleCachePlan;

import java.util.Map;

/**
 * 文章管理
 * 
 */
public class ArticleCacheList extends Page {
	/**
	 * 页面查询
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String catalogId = dga.getParam("catalogID");

		if (StringUtil.isEmpty(catalogId)) {
			logger.warn("页面缓存，模块发布，catalogId不能为空.");
			return;
		}

		// dga.setTotal(1);
		// 编码，文件数据，数据库文件数
		String column[] = new String[] { "ModuleCode", "DbFiles" };
		DataColumn[] dcs = new DataColumn[column.length];
		for (int i = 0; i < column.length; i++) {
			DataColumn dc = new DataColumn();
			dc.setColumnName(column[i]);
			dc.setColumnType(DataColumn.STRING);
			dcs[i] = dc;
		}

		String content = ArticleCachePlan.getTemplateContent(catalogId);
		if (StringUtil.isEmpty(content)) {
			dga.setTotal(0);
			dga.bindData(new DataTable(dcs, null));
			return;
		}

		// 解析模版标签 cms:list
		Map<String, Object> cms_list_key = ArticleCachePlan.getListKey(content, null);
		if (cms_list_key == null || cms_list_key.isEmpty()) {
			dga.setTotal(0);
			dga.bindData(new DataTable(dcs, null));
			return;
		}

		// 获取栏目对应的文章数
		QueryBuilder qb = new QueryBuilder("select count(1) from zcarticle where catalogid=?", catalogId);
		int article_total = qb.executeInt();

		// 
		int i = 0;
		Object[][] values = new Object[cms_list_key.size()][column.length];

		for (String key : cms_list_key.keySet()) {
			values[i][0] = key;
			values[i][1] = article_total;
			i += 1;
		}

		dga.setTotal(cms_list_key.size());

		DataTable dt = new DataTable(dcs, values);
		dga.bindData(dt);
	}

	/**
	 * 局部生成
	 */
	public void pro() {
		String catalogID = $V("catalogID");
		String ModuleCode = $V("ModuleCode");

		if (StringUtil.isEmpty(catalogID) || StringUtil.isEmpty(ModuleCode)) {
			Response.setStatus(0);
			Response.setMessage("栏目或者模块编码为空!");
			return;
		}

		if (ArticleCachePlan.cacheBlock(catalogID, ModuleCode)) {
			Response.setStatus(1);
			Response.setMessage("缓存文件成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("缓存文件发生错误!");
		}
	}
}
