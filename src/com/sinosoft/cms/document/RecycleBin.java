package com.sinosoft.cms.document;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.BlockingTransaction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.SchemaUtil;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.BZCArticleLogSchema;
import com.sinosoft.schema.BZCArticleLogSet;
import com.sinosoft.schema.BZCArticleSchema;
import com.sinosoft.schema.BZCArticleSet;
import com.sinosoft.schema.BZCAttachmentRelaSchema;
import com.sinosoft.schema.BZCAttachmentRelaSet;
import com.sinosoft.schema.BZCAudioRelaSchema;
import com.sinosoft.schema.BZCAudioRelaSet;
import com.sinosoft.schema.BZCCatalogConfigSchema;
import com.sinosoft.schema.BZCCatalogConfigSet;
import com.sinosoft.schema.BZCCatalogSchema;
import com.sinosoft.schema.BZCCatalogSet;
import com.sinosoft.schema.BZCCommentSchema;
import com.sinosoft.schema.BZCCommentSet;
import com.sinosoft.schema.BZCImageRelaSchema;
import com.sinosoft.schema.BZCImageRelaSet;
import com.sinosoft.schema.BZCPageBlockSchema;
import com.sinosoft.schema.BZCPageBlockSet;
import com.sinosoft.schema.BZCVideoRelaSchema;
import com.sinosoft.schema.BZCVideoRelaSet;
import com.sinosoft.schema.BZDColumnRelaSchema;
import com.sinosoft.schema.BZDColumnRelaSet;
import com.sinosoft.schema.BZDColumnSchema;
import com.sinosoft.schema.BZDColumnSet;
import com.sinosoft.schema.BZDColumnValueSchema;
import com.sinosoft.schema.BZDColumnValueSet;
import com.sinosoft.schema.BZWInstanceSchema;
import com.sinosoft.schema.BZWInstanceSet;
import com.sinosoft.schema.BZWStepSchema;
import com.sinosoft.schema.BZWStepSet;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;

/**
 * 特别注意：需要考虑多次删除的情况，除文章/栏目本身之外，相关的数据也有可能会有多条删除备份记录<br>
 * 目前的做法是一次取出全部，然后在SchemaUtil.getZSetFromBSet()方法里取主键相同的记录中BackupNo最大的记录
 * 
 */
public class RecycleBin extends Page {
	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		String parentTreeLevel = (String) ta.getParams().get("ParentLevel");
		String parentID = (String) ta.getParams().get("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,InnerCode from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? order by orderflag,innercode ");
			qb.add(Catalog.CATALOG);
			qb.add(siteID);
			qb.add(Integer.parseInt(parentTreeLevel));
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,InnerCode,SingleFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode ");
			qb.add(Catalog.CATALOG);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}
		ta.setRootText("文档库");
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	public static void treeCatalogDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		if (!Priv.getPriv("site", siteID + "", Priv.SITE_MANAGE)) {// 必须要有站点管理权限
			ta.setRootText("当前用户没有站点管理权限");
			return;
		}
		String catalogID = ta.getParam("CatalogID");
		if (CatalogUtil.getSchema(catalogID) == null) {// 此CatalogID可能是从Cookie中来的，写Cookie时有此栏目，但现在可能栏目已经不存在了
			catalogID = "";// 如果不存在，就默认父节点为站点
		}
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder("select * from BZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=?");
		qb.add(Catalog.CATALOG);
		qb.add(siteID);
		qb.add(ta.getLevel());
		if (StringUtil.isNotEmpty(catalogID)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			qb.append(" and InnerCode like ?", innerCode + "%");
		}
		qb.append(" and not exists (select 1 from ZCCatalog where ID=BZCCatalog.ID)");
		qb.append(" order by orderflag,innercode,backupNo desc");
		dt = qb.executeDataTable();
		for (int i = dt.getRowCount() - 1; i > 0; i--) {
			if (dt.getInt(i, "ID") == dt.getInt(i - 1, "ID")) {
				dt.deleteRow(i);// 清除掉重复的记录
			}
		}
		Mapx map = dt.toMapx("ID", "InnerCode");
		ZCCatalogSet set = new ZCCatalogSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String pid = dt.getString(i, "ParentID");
			dt.set(i, "Prop1", "B");// 表明是B表
			while (!"0".equals(pid) && !map.containsKey(pid)) {
				ZCCatalogSchema catalog = CatalogUtil.getSchema(pid);
				catalog.setProp1("Z");// 表明是Z表，未被删除
				map.put(pid, catalog.getInnerCode());
				set.add(catalog);
				pid = "" + catalog.getParentID();
			}
		}
		DataTable dt2 = set.toDataTable();
		dt2.insertColumn("BackupNo");
		dt2.insertColumn("BackupOperator");
		dt2.insertColumn("BackupTime");
		dt2.insertColumn("BackupMemo");
		dt.union(dt2);

		if (dt.getRowCount() == 0) {
			if (StringUtil.isEmpty(catalogID)) {
				ta.setRootText("当前站点下没有被删除的栏目");
			} else {
				ta.setRootText("栏目 <font class='red'>" + CatalogUtil.getName(catalogID) + "</font> 下没有被删除的栏目");
			}
		} else {
			ta.setRootText("文档库");
		}

		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
			String text = item.getText();
			if ("Z".equals(item.getData().getString("Prop1"))) {
				text = "<font class='red'>" + text + "</font>";
			} else {
				text = text + "&nbsp;<font color='#ccc'>(" + item.getData().getString("Total") + "篇文章)</font>";
			}
			item.setText(text);
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		long catalogID = dga.getParams().getLong("CatalogID");
		if (catalogID == 0) {
			catalogID = dga.getParams().getLong("Cookie.DocList.LastCatalog");
			dga.getParams().put("CatalogID", catalogID);
		}
		String keyword = (String) dga.getParams().get("Keyword");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		QueryBuilder qb = new QueryBuilder("select distinct id from BZCArticle where CatalogID=?");
		qb.add(catalogID);
		if (StringUtil.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and publishdate <= ? ", endDate);
		}
		qb.append(" and not exists (select 1 from ZCArticle where ID=BZCArticle.ID)");
		dga.setTotal(qb);// 先设置总数
		qb.append(dga.getSortString());

		// 先取当页的文章ID
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		StringBuffer sb = new StringBuffer(
				"select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,"
						+ "TopFlag,OrderFlag,TitleStyle,TopDate,BackupTime,BackupOperator,BackupNo from BZCArticle where id in (");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(dt.getString(i, "ID"));
		}
		if (dt.getRowCount() == 0) {
			sb.append("0");// 没有则置为0,防止SQL出错
		}
		qb = new QueryBuilder(sb.toString() + ") ");
		qb.append(dga.getSortString());
		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(" ,id desc,BackupNo desc");
		} else {
			qb.append(" order by id desc,BackupNo desc");
		}
		dt = qb.executeDataTable();
		for (int i = dt.getRowCount() - 1; i > 0; i--) {
			if (dt.getInt(i, "ID") == dt.getInt(i - 1, "ID")) {
				dt.deleteRow(i);// 清除掉重复的记录
			}
		}
		dt.sort("BackupNo", "desc");
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("BackupTime").setDateFormat("yy-MM-dd HH:mm");
		}
		Mapx attributemap = HtmlUtil.codeToMapx("ArticleAttribute");
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (StringUtil.isNotEmpty(dt.getString(i, "Attribute"))) {
					String[] array = dt.getString(i, "Attribute").split(",");
					String attributeName = "";
					for (int j = 0; j < array.length; j++) {
						if (j != array.length - 1) {
							attributeName = attributeName + attributemap.getString(array[j]) + ",";
						} else {
							attributeName = attributeName + attributemap.getString(array[j]);
						}

					}
					dt.set(i, "Title", dt.getString(i, "Title") + "<font class='red'>[" + attributeName + "]</font>");
				}
			}
		}
		dga.bindData(dt);
	}

	public void restoreDocument() {
		restoreDocument($V("IDs"), false);
	}

	/**
	 * 恢复栏目时连带恢复文章的情况下batchMode为true
	 */
	public void restoreDocument(String ids, boolean batchMode) {
		if (!StringUtil.checkID(ids) && !batchMode) {
			Response.setStatus(0);
			Response.setMessage("错误的参数,ID不能为空!");
			return;
		}
		Transaction trans = new Transaction();
		BZCArticleSet set = new BZCArticleSchema().query(new QueryBuilder("where id in(" + ids
				+ ") or id in (select id from bzcarticle where refersourceid in (" + ids + ") )"));
		ZCArticleSet zset = (ZCArticleSet) SchemaUtil.getZSetFromBSet(set);
		for (int i = 0; i < zset.size(); i++) {
			long status = zset.get(i).getStatus();
			if (status == Article.STATUS_PUBLISHED || status == Article.STATUS_TOPUBLISH) {
				zset.get(i).setStatus(Article.STATUS_TOPUBLISH);// 置为待发布
			} else if (status == Article.STATUS_OFFLINE || status == Article.STATUS_ARCHIVE) {
				zset.get(i).setStatus(status);// 下线/归档的保持原状态
			} else {
				zset.get(i).setStatus(Article.STATUS_DRAFT);// 其他的一律变成初稿
			}
		}
		trans.add(zset, Transaction.INSERT);
		StringBuffer logs = new StringBuffer("恢复文章:");

		// 恢复文章的相关评论等，但未恢复工作流相关数据
		if (set.size() > 0) {
			// 恢复文章相关的自定义字段
			BZDColumnValueSchema colValue = new BZDColumnValueSchema();
			BZDColumnValueSet colValueSet = colValue.query(new QueryBuilder("where RelaID in ('" + ids.replaceAll(",", "','")
					+ "') and RelaType=?", ColumnUtil.RELA_TYPE_DOCID));
			trans.add(SchemaUtil.getZSetFromBSet(colValueSet), Transaction.INSERT);

			// 恢复文章图片关联
			BZCImageRelaSchema imageRela = new BZCImageRelaSchema();
			BZCImageRelaSet imageRelaSet = imageRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_IMAGE));
			trans.add(SchemaUtil.getZSetFromBSet(imageRelaSet), Transaction.INSERT);

			// 恢复文章视频关联
			BZCVideoRelaSchema videoRela = new BZCVideoRelaSchema();
			BZCVideoRelaSet videoRelaSet = videoRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_VIDEO));
			trans.add(SchemaUtil.getZSetFromBSet(videoRelaSet), Transaction.INSERT);

			// 恢复文章附件关联
			BZCAttachmentRelaSchema attachmentRela = new BZCAttachmentRelaSchema();
			BZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_ATTACH));
			trans.add(SchemaUtil.getZSetFromBSet(attachmentRelaSet), Transaction.INSERT);

			// 恢复文章音频关联
			BZCAudioRelaSchema audioRela = new BZCAudioRelaSchema();
			BZCAudioRelaSet audioRelaSet = audioRela.query(new QueryBuilder("where RelaID in (" + ids + ")"));
			trans.add(SchemaUtil.getZSetFromBSet(audioRelaSet), Transaction.INSERT);

			// 恢复文章的相关评论
			BZCCommentSchema comment = new BZCCommentSchema();
			BZCCommentSet commentSet = comment.query(new QueryBuilder("where RelaID in (" + ids + ")"));
			trans.add(SchemaUtil.getZSetFromBSet(commentSet), Transaction.INSERT);
		}

		// 操作日志
		if (!batchMode) {
			for (int i = 0; i < set.size(); i++) {
				ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
				articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
				articleLog.setArticleID(set.get(i).getID());
				articleLog.setAction("RESTORE");
				articleLog.setActionDetail("恢复成功");
				//2011-1-14 处理历史显示真实姓名
				articleLog.setAddUser(User.getRealName());
				articleLog.setAddTime(new Date());
				trans.add(articleLog, Transaction.INSERT);
				logs.append(set.get(i).getTitle() + ",");
			}
		}

		if (!batchMode) {// 栏目恢复时不需要提交，使用了阻塞型事务
			String innerCode = "";
			if (set.size() > 0) {
				innerCode = set.get(0).getCatalogInnerCode();
				String sqlArticleCount = "update zccatalog set " + "total=total+" + set.size()
						+ ",isdirty=1 where innercode in(" + CatalogUtil.getParentCatalogCode(innerCode) + ")";
				trans.add(new QueryBuilder(sqlArticleCount));
			}
			if (trans.commit()) {
				for (int i = 0; i < innerCode.length() / 6; i++) {
					String str = innerCode.substring(0, 6 + i * 6);
					String id = CatalogUtil.getIDByInnerCode(str);
					CatalogUtil.update(id);// 需要更新缓存
				}
				UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE, logs + "成功", Request.getClientIP());
				Response.setStatus(1);
				Response.setMessage("恢复成功!");
			} else {
				UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "失败", Request.getClientIP());
				Response.setStatus(0);
				Response.setMessage("操作数据库时发生错误:" + trans.getExceptionMessage());
			}
		}
	}

	public void deleteReally() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}
		Transaction trans = new Transaction();
		BZCArticleSchema article = new BZCArticleSchema();
		BZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids
				+ ") or id in(select id from bzcarticle where refersourceid in (" + ids + ") )"));

		trans.add(set, Transaction.DELETE);
		StringBuffer logs = new StringBuffer("彻底删除文章:");
		if (set.size() > 0) {
			// 删除文章相关的自定义字段
			BZDColumnValueSchema colValue = new BZDColumnValueSchema();
			BZDColumnValueSet colValueSet = colValue.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", ColumnUtil.RELA_TYPE_DOCID));
			trans.add(colValueSet, Transaction.DELETE);

			// 删除文章图片关联
			BZCImageRelaSchema imageRela = new BZCImageRelaSchema();
			BZCImageRelaSet imageRelaSet = imageRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_IMAGE));
			trans.add(imageRelaSet, Transaction.DELETE);

			// 删除文章视频关联
			BZCVideoRelaSchema videoRela = new BZCVideoRelaSchema();
			BZCVideoRelaSet videoRelaSet = videoRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_VIDEO));
			trans.add(videoRelaSet, Transaction.DELETE);

			// 删除文章附件关联
			BZCAttachmentRelaSchema attachmentRela = new BZCAttachmentRelaSchema();
			BZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_ATTACH));
			trans.add(attachmentRelaSet, Transaction.DELETE);

			// 删除文章音频关联
			BZCAudioRelaSchema audioRela = new BZCAudioRelaSchema();
			BZCAudioRelaSet audioRelaSet = audioRela.query(new QueryBuilder("where RelaID in (" + ids + ")"));
			trans.add(audioRelaSet, Transaction.DELETE);

			// 删除文章的相关评论
			BZCCommentSchema comment = new BZCCommentSchema();
			BZCCommentSet commentSet = comment.query(new QueryBuilder("where RelaID in (" + ids + ")"));
			trans.add(commentSet, Transaction.DELETE);

			// 删除操作日志
			BZCArticleLogSchema articleLog = new BZCArticleLogSchema();
			BZCArticleLogSet logset = articleLog.query(new QueryBuilder("where ArticleID in (" + ids + ")"));
			trans.add(logset, Transaction.DELETE);

			// 删除工作流记录
			BZWStepSchema step = new BZWStepSchema();
			BZWStepSet stepset = step.query(new QueryBuilder(
					"where InstanceID in (select id from ZWInstance where DataID in (" + ids + "))"));
			trans.add(stepset, Transaction.DELETE);

			BZWInstanceSchema instance = new BZWInstanceSchema();
			BZWInstanceSet instanceset = instance.query(new QueryBuilder("where DataID in (" + ids + ")"));
			trans.add(instanceset, Transaction.DELETE);
		}

		if (trans.commit()) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "成功", Request.getClientIP());
			Response.setStatus(1);
			Response.setMessage("彻底删除成功!");
		} else {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void restoreCatalog() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}
		String[] arr = ids.split(",");
		ArrayList list = new ArrayList(20);
		for (int i = 0; i < arr.length; i++) {
			if (StringUtil.isNotEmpty(arr[i])) {
				if (CatalogUtil.getSchema(arr[i]) == null) {// 说明栏目存在，不需要恢复
					list.add(arr[i]);
				}
			}
		}
		if (list.size() == 0) {
			Response.setError("栏目已经存在，不能再次恢复！");
			return;
		}
		ids = StringUtil.join(list);
		BZCCatalogSet set = new BZCCatalogSchema().query(new QueryBuilder("where id in (" + ids
				+ ") order by backupNo desc"));
		for (int i = set.size() - 1; i > 0; i--) {
			if (set.get(i).getID() == set.get(i - 1).getID()) {
				set.remove(set.get(i));// 清除掉重复的记录
			}
		}

		// 如果父级节点未删除，则需要检查同一节点是否有同名栏目
		for (int i = 0; i < set.size(); i++) {
			BZCCatalogSchema catalog = set.get(i);
			if (CatalogUtil.getSchema(catalog.getParentID()) != null) {// 存在父栏目
				if (Catalog.checkNameExists(catalog.getName(), catalog.getParentID())) {
					Response.setError("不能恢复栏目“" + catalog.getName() + "”,上级栏目下已有同名子栏目！");
					return;
				}
				String existsName = Catalog.checkAliasExists(catalog.getAlias(), catalog.getParentID());
				if (StringUtil.isNotEmpty(existsName)) {
					Response.setError("栏目“" + existsName + "”已使用了别名" + catalog.getAlias());
					return;
				}
			}
		}
		list.clear();
		// 可能会有这种情况：先删除了名称为A的栏目，又建了一个名称为A的栏目，再删除这个栏目，数据就有两条不同的删除记录，但名称都是A
		for (int i = 0; i < set.size(); i++) {
			BZCCatalogSchema catalog = set.get(i);
			for (int j = i + 1; j < set.size(); j++) {
				if (set.get(j).getParentID() == catalog.getParentID()) {
					if (set.get(j).getName().equals(catalog.getName())) {
						set.get(j).setName(set.get(j).getName() + "_" + j);
					}
					if (set.get(j).getAlias().equals(catalog.getAlias())) {
						set.get(j).setAlias(set.get(j).getAlias() + "_" + j);
					}
				}
			}
			list.add("" + catalog.getID());
		}
		ids = StringUtil.join(list);

		BlockingTransaction tran = new BlockingTransaction();// 必须要用阻塞型事务，有可能要恢复的文章数据量非常大
		tran.add(SchemaUtil.getZSetFromBSet(set), Transaction.INSERT);// 首先恢复栏目本身

		// 恢复栏目下的区块
		BZCPageBlockSet blockSet = new BZCPageBlockSchema().query(new QueryBuilder("where CatalogID in (" + ids + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(blockSet), Transaction.INSERT);

		// 恢复栏目扩展设置
		BZCCatalogConfigSet configSet = new BZCCatalogConfigSchema().query(new QueryBuilder("where CatalogID in ("
				+ ids + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(configSet), Transaction.INSERT);

		// 恢复栏目扩展属性和自定义字段
		String types = ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "," + ColumnUtil.RELA_TYPE_CATALOG_EXTEND;
		BZDColumnRelaSet columnRelaSet = new BZDColumnRelaSchema().query(new QueryBuilder(" where RelaType in ("
				+ types + ") and RelaID in(" + ids + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(columnRelaSet), Transaction.INSERT);

		BZDColumnSet columnSet = new BZDColumnSchema().query(new QueryBuilder(" where ID in("
				+ StringUtil.join(columnRelaSet.toDataTable().getColumnValues("ColumnID")) + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(columnSet), Transaction.INSERT);

		// 恢复栏目关联的zdcolumnvalue数据,包括扩展属性和自定义字段
		BZDColumnValueSet columnValueSet = new BZDColumnValueSchema().query(new QueryBuilder(" where RelaType in ("
				+ types + ") and RelaID in(" + ids + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(columnValueSet), Transaction.INSERT);

		// 开始逐个栏目恢复文章
		for (int i = 0; i < set.size(); i++) {
			QueryBuilder qb = new QueryBuilder("select distinct id from BZCArticle where CatalogID=?");
			qb.add(set.get(i).getID());
			DataTable dt = qb.executeDataTable();
			for (int j = 0; j * 100.0 < dt.getRowCount(); j++) {
				list = new ArrayList();
				for (int k = j * 100; k < (j + 1) * 100 && k < dt.getRowCount(); k++) {
					list.add(dt.getString(k, "ID"));
				}
				ids = StringUtil.join(list);
				restoreDocument(ids, true);
			}
		}
		if (tran.commit()) {
			// 开始创建目录
			for (int i = 0; i < set.size(); i++) {
				String path = Config.getContextRealPath() + CatalogUtil.getAbsolutePath(set.get(i).getID());
				File f = new File(path);
				if (!f.exists()) {
					f.mkdir();
				}
			}
			Response.setMessage("恢复成功!");
		} else {
			Response.setError("恢复失败:" + tran.getExceptionMessage());
		}
	}
}
