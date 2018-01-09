package com.sinosoft.cms.api;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;
import org.apache.commons.lang.ArrayUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ArticleAPI implements APIInterface {
	private ZCArticleSchema article;

	private Mapx customData;

	private Mapx params;

	public boolean setSchema(Schema schema) {
		this.article = (ZCArticleSchema) schema;
		return false;
	}

	/*
	 * 新建文章 新建成功 返回 1 失败返回 -1
	 */
	public long insert() {
		Transaction trans = new Transaction();
		if (insert(trans) > 0) {
			if (trans.commit()) {
				return 1;
			} else
				return -1;
		} else {
			return -1;
		}
	}

	public long insert(Transaction trans) {

		long articleID = NoUtil.getMaxID("DocID");
		article.setID(articleID);
		if (article.getCatalogID() == 0L) {
			return -1;
		}
		article.setSiteID(CatalogUtil.getSiteID(article.getCatalogID()));

		String innerCode = CatalogUtil.getInnerCode(article.getCatalogID());
		article.setCatalogInnerCode(innerCode);
		if (article.getType() == null) {
			article.setType("1");
		}

		if (article.getTopFlag() == null) {
			article.setTopFlag("0");
		}

		if (article.getCommentFlag() == null) {
			article.setCommentFlag("1");
		}

		if (article.getContent() == null) {
			article.setContent("");
		}

		article.setStickTime(0);

		article.setPriority("1");
		article.setTemplateFlag("0");

		article.setPublishFlag("1");

		if (article.getOrderFlag() == 0) {
			if (article.getPublishDate() != null) {
				article.setOrderFlag(article.getPublishDate().getTime());
			} else {
				article.setOrderFlag(OrderUtil.getDefaultOrder());
			}
		}
		article.setHitCount(0);
		if (article.getStatus() == 0) {
			article.setStatus(Article.STATUS_DRAFT);
		}
		article.setAddTime(new Date(article.getOrderFlag()));

		if (article.getAddUser() == null) {
			article.setAddUser("admin");
		}

		if (article.getFirstPublishDate() == null) {
			article.setFirstPublishDate(new Date());
		}
		long catalogID = article.getCatalogID();
		if (null == article.getMetaTitle() || "".equals(article.getMetaTitle())) {
			String temp = "";
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(catalogID);
			catalog.fill();
			ZCSiteSchema tZCSiteSchema = new ZCSiteSchema();
			tZCSiteSchema.setID(catalog.getSiteID());
			tZCSiteSchema.fill();
			String tSiteName = tZCSiteSchema.getName();
			temp = catalog.getName();
			long tParentID = catalog.getParentID();
			while (tParentID != 0) {
				catalog = new ZCCatalogSchema();
				catalog.setID(tParentID);
				catalog.fill();
				temp = temp + "-" + catalog.getName();
				tParentID = catalog.getParentID();
			}
			String tMetaTitle = article.getTitle() + "-" + temp + "-" + tSiteName;
			article.setMetaTitle(tMetaTitle);

		} else {
			String temp = "";
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(catalogID);
			catalog.fill();
			ZCSiteSchema tZCSiteSchema = new ZCSiteSchema();
			tZCSiteSchema.setID(catalog.getSiteID());
			tZCSiteSchema.fill();
			String tSiteName = tZCSiteSchema.getName();
			temp = catalog.getName();
			long tParentID = catalog.getParentID();
			while (tParentID != 0) {
				catalog = new ZCCatalogSchema();
				catalog.setID(tParentID);
				catalog.fill();
				temp = temp + "-" + catalog.getName();
				tParentID = catalog.getParentID();
			}
			String tMetaTitle = article.getMetaTitle() + "-" + temp + "-" + tSiteName;
			article.setMetaTitle(tMetaTitle);

		}

		Date tDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String temp1 = formatter.format(tDate);
		temp1 = temp1.substring(0, 10).replace("-", "");
		if (article.getEveryDayNo() == 0) {
			DataTable nextDT = new QueryBuilder("select max(EveryDayNo)as aNo from zcarticle where  date_format(addtime,'%Y%m%d') =?", temp1).executePagedDataTable(1, 0);

			if ("".equals(nextDT.getString(0, "aNo"))) {
				article.setEveryDayNo(1);

			} else {
				article.setEveryDayNo(Long.parseLong(nextDT.getString(0, "aNo")) + 1);
			}

		}
		article.setURL(PubFun.getArticleURL(article));
		trans.add(article, Transaction.INSERT);

		String sqlArticleCount = "update zccatalog set total = total+1,isdirty=1 where id=?";
		trans.add(new QueryBuilder(sqlArticleCount, article.getCatalogID()));

		if (customData != null) {
			addCustomData(trans);
		}

		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setAction("INSERT");
		articleLog.setActionDetail("添加新文章");
		articleLog.setArticleID(articleID);
		// 2011-1-14 处理历史显示真实姓名
		articleLog.setAddUser("admin");
		articleLog.setAddTime(new Date());
		trans.add(articleLog, Transaction.INSERT);

		return 1;
	}

	private void addCustomData(Transaction trans) {
		// 自定义字段
		ZDColumnValueSchema ColumnValue = null;
		ZDColumnValueSet ColumnValueSet = new ZDColumnValueSet();
		DataTable dt = new QueryBuilder(
				"select b.code code,b.listopt listopt,b.showmod showmod from zdcustomcolumnrela a, zdcustomcolumnitem b where a.type=2 and b.classcode='Sys_CMS' and a.customid=b.id and a.typeid=?",
				article.getCatalogID()).executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String code = "";
				String showMode = "";
				String textValue = "";
				String index = ""; // 序号
				String[] list = null;

				code = dt.getString(i, "code");
				showMode = dt.getString(i, "ShowMod");
				if ("1".equals(showMode) || "2".equals(showMode)) { // 单行文本、多行文本
					textValue = customData.getString(code);
				}
				if ("3".equals(showMode) || "4".equals(showMode)) { // 下拉列表框
					list = dt.getString(i, "listopt").split("\n");
					textValue = customData.getString(code);
					index = ArrayUtils.indexOf(list, textValue) + "";
				}
				if ("5".equals(showMode)) { // 多选
					list = dt.getString(i, "listopt").split("\n");
					textValue = customData.getString(code);
					String[] values = textValue.split("\\|");
					for (int j = 0; j < values.length; j++) {
						if (j != values.length - 1) {
							index += ArrayUtils.indexOf(list, values[j]) + "|";
						} else {
							index += ArrayUtils.indexOf(list, values[j]);
						}

					}
				}
				ColumnValue = new ZDColumnValueSchema();
				ColumnValue.setID(NoUtil.getMaxID("ColumnValueID"));
				ColumnValue.setColumnCode(code);
				textValue = (textValue == null ? "" : textValue);
				ColumnValue.setTextValue(textValue);
				ColumnValue.setRelaID(article.getID() + "");
				ColumnValueSet.add(ColumnValue);
			}
		}
		trans.add(new QueryBuilder("delete from zdcolumnvalue where classcode='Sys_CMS' and articleid=?", article.getID()));
		trans.add(ColumnValueSet, Transaction.INSERT);
	}

	public boolean update() {
		String articleID = params.getString("DocID");

		ZCArticleSchema article1 = new ZCArticleSchema();
		article1.setID(articleID);
		if (!article1.fill()) {
			return false;
		}
		if (article1.getCatalogID() == 0L) {
			return false;
		}

		article1.setTitle(params.getString("Title"));
		article1.setAuthor(params.getString("Author"));
		String content = params.getString("Content");
		String publishDate = params.getString("PublishDate");

		if (StringUtil.isNotEmpty(params.getString("TopFlag"))) {
			article1.setTopFlag(params.getString("TopFlag"));
		}

		if (StringUtil.isNotEmpty(params.getString("CommentFlag"))) {
			article1.setTopFlag(params.getString("CommentFlag"));
		}

		if (StringUtil.isNotEmpty(params.getString("CommentFlag"))) {
			article1.setTopFlag(params.getString("CommentFlag"));
		}

		if (StringUtil.isNotEmpty(content)) {
			article1.setContent(content);
		}

		if (StringUtil.isNotEmpty(publishDate)) {
			try {
				article1.setPublishDate(DateUtil.parse(publishDate, DateUtil.Format_Date));
			} catch (Exception e) {

			}
		}

		article1.setModifyTime(new Date(article1.getOrderFlag()));

		article1.setModifyUser("wsdl");

		Transaction trans = new Transaction();

		trans.add(article1, Transaction.UPDATE);

		// //处理自定义字段
		// DataTable columnDT =
		// ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, articleID);
		// if (columnDT.getRowCount() > 0) {
		// trans.add(new
		// QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
		// ColumnUtil.RELA_TYPE_DOCID, articleID));
		// }
		// DataCollection dc=new DataCollection();
		// dc.putAll(params);
		// trans.add(ColumnUtil.getValueFromRequest(article.getCatalogID(),
		// Long.parseLong(articleID),dc),
		// Transaction.INSERT);

		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setAction("UPDATE");
		articleLog.setActionDetail("编辑文章");
		articleLog.setArticleID(articleID);
		// 2011-1-14 处理历史显示真实姓名
		articleLog.setAddUser("wsdl");
		articleLog.setAddTime(new Date());
		trans.add(articleLog, Transaction.INSERT);

		return trans.commit();
	}

	public boolean delete() {

		if (article == null) {
			return false;
		}

		long articleID = article.getID();
		Transaction trans = new Transaction();

		trans.add(article, Transaction.DELETE);

		ZDColumnValueSchema colValue = new ZDColumnValueSchema();
		colValue.setRelaID(articleID + "");
		ZDColumnValueSet colValueSet = colValue.query();
		if (colValueSet != null && !colValueSet.isEmpty()) {
			trans.add(colValueSet, Transaction.DELETE);
		}

		String sqlArticleCount = "update zccatalog set " + "total = total-1,isdirty=1 where innercode in(" + CatalogUtil.getParentCatalogCode(article.getCatalogInnerCode()) + ")";
		trans.add(new QueryBuilder(sqlArticleCount));

		// 操作日志
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setArticleID(articleID);
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setAction("DELETE");
		articleLog.setActionDetail("删除。删除原因：" + "wsdl删除");
		// 2011-1-14 处理历史显示真实姓名
		articleLog.setAddUser("wsdl");
		articleLog.setAddTime(new Date());
		trans.add(articleLog, Transaction.INSERT);

		if (trans.commit()) {
			// Publisher p = new Publisher();
			// p.deletePubishedFile(article);
			return true;
		} else {
			return false;
		}
	}

	public Mapx getCustomData() {
		return customData;
	}

	public void setCustomData(Mapx customData) {
		this.customData = customData;
	}

	public static DataTable getPagedDataTable(long catalogID, int pageSize, int pageIndex) {
		DataTable dt = new QueryBuilder("select * from zcarticle where catalogid=?", catalogID).executePagedDataTable(pageSize, pageIndex);
		ColumnUtil.extendDocColumnData(dt, catalogID);
		return dt;
	}

	public static DataTable getDataTable(long catalogID) {
		return getPagedDataTable(catalogID, -1, -1);
	}

	public static String getPreviewURL(long artilceID) {
		String url;
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(artilceID);
		if (!article.fill()) {
			return null;
		}

		url = Config.getValue("Statical.TargetDir") + "/" + SiteUtil.getAlias(article.getSiteID()) + PubFun.getArticleURL(article);
		url.replaceAll("//", "/");
		return url;
	}

	public static String getPublishedURL(long artilceID) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(artilceID);
		String url = null;
		if (article.fill()) {
			url = SiteUtil.getURL(article.getSiteID()) + "/" + PubFun.getArticleURL(article);
		} else {
			url = "#";
		}
		url.replaceAll("/+", "/");
		return url;
	}

	public static void main(String[] args) {
		DataTable dt = ArticleAPI.getPagedDataTable(5954, 2, 0);
//		System.out.println(dt.toString());
	}

	public Mapx getParams() {
		return params;
	}

	public void setParams(Mapx params) {
		this.params = params;
		convertParams(params);
	}

	public void convertParams(Mapx params) {
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			String value = params.getString(key);
			if (StringUtil.isEmpty(value) || "null".equalsIgnoreCase(value)) {
				params.put(key, "");
			}
		}
	}
}
