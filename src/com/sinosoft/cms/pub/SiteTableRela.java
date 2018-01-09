package com.sinosoft.cms.pub;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringFormat;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDCodeSchema;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * 与站点有关的表之间的关系
 * 
 */
public class SiteTableRela {
	private static final Logger logger = LoggerFactory.getLogger(SiteTableRela.class);

	/**
	 * 返回字段中不包含SiteID，但通过字段与其他含有SiteID字段的表关联的所有表
	 */
	public static TableRela[] getRelas() {
		// 不具有SiteID字段的表,但是关联了具有SiteID字段的表的主键
		ArrayList list = new ArrayList();
		list.add(new TableRela("ZCAdvertisement", "PositionID", "ZCAdPosition", "ID", null, false));
		list.add(new TableRela("ZCAdVisitLog", "AdID", "ZCAdvertisement", "ID", null));
		list.add(new TableRela("ZCApproval", "ArticleID", "ZCArticle", "ID", null, false));
		list.add(new TableRela("ZCArticleLog", "ArticleID", "ZCArticle", "ID", null));
		list.add(new TableRela("ZCArticlePage", "ArticleID", "ZCArticle", "ID", null));
		list.add(new TableRela("ZCArticle", "CatalogID", "ZCCatalog", "ID", null, false));

		list.add(new TableRela("ZCAttachmentRela", "RelaID", "ZCArticle", "ID", null));
		list.add(new TableRela("ZCAudioRela", "RelaID", "ZCArticle", "ID", null));
		list.add(new TableRela("ZCImageRela", "RelaID", "ZCArticle", "ID", "RelaType='ArticleImage'"));
		list.add(new TableRela("ZCImageRela", "RelaID", "ZCImagePlayer", "ID", "RelaType='ImagePlayerImage'"));
		list.add(new TableRela("ZCVideoRela", "RelaID", "ZCArticle", "ID", null));

		list.add(new TableRela("ZCAttachmentRela", "ID", "ZCAttachment", "ID", null, false));
		list.add(new TableRela("ZCAudioRela", "ID", "ZCAudio", "ID", null, false));
		list.add(new TableRela("ZCImageRela", "ID", "ZCImage", "ID", null, false));
		list.add(new TableRela("ZCVideoRela", "ID", "ZCVideo", "ID", null, false));

		list.add(new TableRela("ZCAttachment", "CatalogID", "ZCCatalog", "ID", null, false));
		list.add(new TableRela("ZCAudio", "CatalogID", "ZCCatalog", "ID", null, false));
		list.add(new TableRela("ZCCatalog", "ParentID", "ZCCatalog", "ID", null, false));
		list.add(new TableRela("ZCVideo", "CatalogID", "ZCCatalog", "ID", null, false));
		list.add(new TableRela("ZCImage", "CatalogID", "ZCCatalog", "ID", null, false));

		list.add(new TableRela("ZCComment", "RelaID", "ZCArticle", "ID", null, false));// 对应5个表的ID
		list.add(new TableRela("ZCComment", "CatalogID", "ZCCatalog", "ID", null, false));
		list.add(new TableRela("ZCDeployJob", "ConfigID", "ZCDeployConfig", "ID", null, false));
		list.add(new TableRela("ZCDeployLog", "JobID", "ZCDeployJob", "ID", null, false));

		list.add(new TableRela("ZCLink", "LinkGroupID", "ZCLinkGroup", "ID", null, false));
		list.add(new TableRela("ZCMagazineIssue", "MagazineID", "ZCMagazine", "ID", null));
		list.add(new TableRela("ZCMagazineCatalogRela", "CatalogID", "ZCCatalog", "ID", null));
		list.add(new TableRela("ZCMagazineCatalogRela", "MagazineID", "ZCMagazine", "ID", null, false));
		list.add(new TableRela("ZCMagazineCatalogRela", "IssueID", "ZCMagazineIssue", "ID", null, false));

		// list.add(new TableRela("ZCPaperIssue", "PaperID", "ZCPaper", "ID",
		// null));
		// list.add(new TableRela("ZCPaperPage", "IssueID", "ZCPaperIssue",
		// "ID", null));
		// list.add(new TableRela("ZCPaperPageNewsRela", "PageID",
		// "ZCPaperPage", "ID", null));
		// list.add(new TableRela("ZCPaperPageNewsRela", "ArticleID",
		// "ZCArticle", "ID", null));

		list.add(new TableRela("ZCPageBlock", "CatalogID", "ZCCatalog", "ID", null, false));
		list.add(new TableRela("ZCPageBlockItem", "BlockID", "ZCPageBlock", "ID", null));
		list.add(new TableRela("ZCTemplateBlockRela", "BlockID", "ZCPageBlock", "ID", null, false));

		list.add(new TableRela("ZCVoteSubject", "VoteID", "ZCVote", "ID", null));
		list.add(new TableRela("ZCVoteItem", "VoteID", "ZCVote", "ID", null));
		list.add(new TableRela("ZCVoteItem", "SubjectID", "ZCVoteSubject", "ID", null, false));
		list.add(new TableRela("ZCVoteLog", "VoteID", "ZCVote", "ID", null));

		list.add(new TableRela("ZDColumnRela", "RelaID", "ZCCatalog", "ID", "RelaType='0'"));
		list.add(new TableRela("ZDColumnRela", "RelaID", "ZCCatalog", "ID", "RelaType='1'"));
		list.add(new TableRela("ZDColumnRela", "ColumnID", "ZDColumn", "ID", null, false));
		list.add(new TableRela("ZDColumnValue", "RelaID", "ZCCatalog", "ID", "RelaType='0'"));
		list.add(new TableRela("ZDColumnValue", "RelaID", "ZCArticle", "ID", "RelaType='2'"));
		list.add(new TableRela("ZDColumnValue", "ColumnID", "ZDColumn", "ID", null, false));

		// 导出定时任务中的索引任务
		list.add(new TableRela("ZDSchedule", "SourceID", "ZCFullText", "ID", "TypeCode='IndexMaintenance'"));
		// 导出定时任务中的Web采集任务
		list.add(new TableRela("ZDSchedule", "SourceID", "ZCWebGather", "ID", "TypeCode='WebCrawl'"));
		list.add(new TableRela("ZCCustomTable", "DatabaseID", "ZCDatabase", "ID", null, false));
		list.add(new TableRela("ZCCustomTableColumn", "TableID", "ZCCustomTable", "ID", null));

		list.add(new TableRela("ZCArticle", "CatalogInnerCode", "ZCCatalog", "InnerCode", null, false));
		list.add(new TableRela("ZCImage", "CatalogInnerCode", "ZCCatalog", "InnerCode", null, false));
		list.add(new TableRela("ZCVideo", "CatalogInnerCode", "ZCCatalog", "InnerCode", null, false));
		list.add(new TableRela("ZCAttachment", "CatalogInnerCode", "ZCCatalog", "InnerCode", null, false));
		list.add(new TableRela("ZCAudio", "CatalogInnerCode", "ZCCatalog", "InnerCode", null, false));
		list.add(new TableRela("ZCArticle", "CatalogInnerCode", "ZCCatalog", "InnerCode", null, false));

		list.add(new TableRela("ZCCatalogConfig", "CatalogID", "ZCCatalog", "ID", null, false));
		list.add(new TableRela("ZCCatalogConfig", "CatalogInnerCode", "ZCCatalog", "InnerCode", null, false));
		list.add(new TableRela("ZCCatalog", "Parent", "ZCCatalog", "ID", null, false));

		list.add(new TableRela("ZSGoods", "CatalogID", "ZCCatalog", "ID", null, false));
		list.add(new TableRela("ZSGoods", "CatalogInnerCode", "ZCCatalog", "InnerCode", null, false));
		list.add(new TableRela("ZSBrand", "BranchInnerCode", "ZDBranch", "BranchInnerCode", null, false));
		list.add(new TableRela("ZSPaymentProp", "PaymentID", "ZSPayment", "ID", null));

		list.add(new TableRela("ZCForumScore", "ID", "ZCForumGroup", "ID", null, false));
		list.add(new TableRela("ZCForumAttachment", "PostID", "ZCPost", "ID", null, false));
		list.add(new TableRela("ZCForumMember", "AdminID", "ZCForumGroup", "ID", null, false));
		list.add(new TableRela("ZCForumMember", "UserGroupID", "ZCForumGroup", "ID", null, false));
		list.add(new TableRela("ZCForumMember", "DefinedID", "ZCForumGroup", "ID", null, false));

		TableRela[] trs = new TableRela[list.size()];
		for (int i = 0; i < list.size(); i++) {
			trs[i] = (TableRela) list.get(i);
		}
		return trs;
	}

	/**
	 * 各表中各字段的最大号类型
	 */
	public static NoType[] getNoRelaArray() {
		ArrayList list = new ArrayList();
		list.add(new NoType("ZDColumnValue", "ID", "ColumnValueID"));
		list.add(new NoType("ZCArticleLog", "ID", "ArticleLogID"));
		list.add(new NoType("ZCArticle", "ID", "DocID"));

		list.add(new NoType("ZCMessage", "ID", "MessageID"));
		list.add(new NoType("ZCDeployJob", "ID", "DeployJobID"));
		list.add(new NoType("ZCDeployLog", "ID", "DeployLogID"));
		list.add(new NoType("ZCDeployConfig", "ID", "DeployConfigID"));

		list.add(new NoType("ZCAdvertisement", "ID", "AdvertiseID"));
		list.add(new NoType("ZCAdPosition", "ID", "AdPositionID"));
		list.add(new NoType("ZDColumn", "ID", "ColumnID"));

		list.add(new NoType("ZCCustomTable", "ID", "CustomTableID"));
		list.add(new NoType("ZCFullText", "ID", "FullTextID"));

		list.add(new NoType("ZDMemberLevel", "ID", "MembeLevelID"));
		list.add(new NoType("ZCDatabase", "ID", "DatabaseID"));

		list.add(new NoType("ZCVote", "ID", "VoteID"));
		list.add(new NoType("ZCVoteSubject", "ID", "VoteSubjectID"));
		list.add(new NoType("ZCNotes", "ID", "NotesID"));

		list.add(new NoType("ZCBadWord", "ID", "BadWordID"));
		list.add(new NoType("ZCCustomTableColumn", "ID", "CustomTableColumnID"));
		list.add(new NoType("ZDSchedule", "ID", "ScheduleID"));

		list.add(new NoType("ZCVoteItem", "ID", "VoteItemID"));
		list.add(new NoType("ZDColumnRela", "ID", "ColumnRelaID"));
		list.add(new NoType("ZCImagePlayer", "ID", "ImagePlayerID"));

		list.add(new NoType("ZCLink", "ID", "LinkID"));
		list.add(new NoType("ZCLinkGroup", "ID", "LinkGroupID"));
		list.add(new NoType("ZCPageBlock", "ID", "PageBlockID"));

		list.add(new NoType("ZCPageBlockItem", "ID", "PageBlockID"));
		list.add(new NoType("ZCSite", "ID", "SiteID"));
		list.add(new NoType("ZCImage", "ID", "DocID"));

		list.add(new NoType("ZCAttachment", "ID", "DocID"));
		list.add(new NoType("ZCAudio", "ID", "DocID"));
		list.add(new NoType("ZCVideo", "ID", "DocID"));

		list.add(new NoType("ZCWebGather", "ID", "GatherID"));
		list.add(new NoType("ZCDBGather", "ID", "DBGatherID"));
		list.add(new NoType("ZCInnerGather", "ID", "InnerGatherID"));
		list.add(new NoType("ZCInnerDeploy", "ID", "InnerDeployID"));
		list.add(new NoType("ZDUserLog", "ID", "LogID"));
		list.add(new NoType("ZDMenu", "ID", "MenuID"));

		list.add(new NoType("ZWWorkflowDef", "ID", "WorkflowDefID"));
		list.add(new NoType("ZCQuestion", "ID", "QuestionID"));
		list.add(new NoType("ZWWorkflowEntry", "ID", "WorkflowEntryID"));

		list.add(new NoType("ZCComment", "ID", "CommentID"));
		list.add(new NoType("ZCCatalog", "ID", "CatalogID"));
		list.add(new NoType("ZCCatalog", "InnerCode", "CatalogInnerCode"));

		list.add(new NoType("ZCForum", "ID", "ForumID"));
		list.add(new NoType("ZCPost", "ID", "PostID"));
		list.add(new NoType("ZCTheme", "ID", "ThemeID"));

		list.add(new NoType("ZCForumGroup", "ID", "ForumGroupID"));
		list.add(new NoType("ZCForumConfig", "ID", "ForumID"));
		list.add(new NoType("ZCForumAttachment", "ID", "ForumAttachmentID"));
		list.add(new NoType("ZCForum", "ID", "ForumID"));

		list.add(new NoType("ZCPaper", "ID", "CatalogID"));
		list.add(new NoType("ZCPaperIssue", "ID", "CatalogID"));
		list.add(new NoType("ZCPaperPage", "ID", "CatalogID"));
		list.add(new NoType("ZCMagazine", "ID", "CatalogID"));
		list.add(new NoType("ZCMagazineIssue", "ID", "CatalogID"));
		list.add(new NoType("ZCMessageBoard", "ID", "MessageBoardID"));

		list.add(new NoType("ZCVoteLog", "ID", "VoteLogID"));
		list.add(new NoType("ZCCatalogConfig", "ID", "CatalogConfigID"));

		list.add(new NoType("ZSOrder", "ID", "OrderID"));
		list.add(new NoType("ZDMemberAddr", "ID", "MemberAddr"));
		list.add(new NoType("ZSBrand", "ID", "ZSBrandID"));
		list.add(new NoType("ZSGoods", "ID", "GoodsID"));
		list.add(new NoType("ZSPayment", "ID", "paymentID"));
		list.add(new NoType("ZSSend", "ID", "SendID"));
		list.add(new NoType("ZSPaymentPropID", "ID", "PaymentPropID"));

		NoType[] arr = new NoType[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = (NoType) list.get(i);
		}
		return arr;
	}

	/**
	 * 返回所有的含有SiteID的表名
	 */
	public static String[] getSiteIDTables() {
		ArrayList list = new ArrayList();
		list.add("ZCCatalog");
		list.add("ZCAdPosition");
		list.add("ZCAdvertisement");
		list.add("ZCArticle");
		list.add("ZCAttachment");
		list.add("ZCAudio");
		list.add("ZCComment");
		list.add("ZCDatabase");
		list.add("ZCCustomTable");
		list.add("ZCDeployConfig");
		list.add("ZCDeployJob");
		list.add("ZCDeployLog");
		list.add("ZCForumAttachment");
		list.add("ZCForum");
		list.add("ZCForumConfig");
		list.add("ZCForumGroup");
		list.add("ZCForumMember");
		list.add("ZCForumScore");
		list.add("ZCFullText");
		list.add("ZCImagePlayer");
		list.add("ZCImage");
		list.add("ZCJsFile");
		list.add("ZCKeywordType");
		list.add("ZCLinkGroup");
		list.add("ZCLink");
		list.add("ZCMagazine");
		list.add("ZCMessageBoard");
		list.add("ZCPageBlock");
		list.add("ZCPaper");
		list.add("ZCPost");
		list.add("ZCCatalogConfig");
		list.add("ZCStatItem");
		list.add("ZCTag");
		list.add("ZCTemplateBlockRela");
		list.add("ZCTemplate");
		list.add("ZCTemplateTagRela");
		list.add("ZCTheme");
		list.add("ZCVideo");
		list.add("ZCVisitLog");
		list.add("ZCVote");
		list.add("ZCWebGather");
		list.add("ZCDBGather");
		list.add("ZCInnerGather");
		list.add("ZCInnerDeploy");
		list.add("ZCTag");
		list.add("ZDColumn");
		list.add("ZDMember");
		
		list.add("ZSShopConfig");
		list.add("ZSGoods");
		list.add("ZSBrand");
		list.add("ZSStore");		
		list.add("ZSOrder");
		list.add("ZSOrderItem");
		list.add("ZSFavorite");
		list.add("ZSSend");
		list.add("ZSPayment");
		
		String[] arr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = (String) list.get(i);
		}
		return arr;
	}

	/**
	 * 返回所有的含有SiteID的表名
	 */
	public static String[] getSiteIDTablesBySchema() {
		ArrayList list = new ArrayList();
		String path = ZDCodeSchema.class.getResource("ZDCodeSchema.class").getPath();
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
			if (path.startsWith("/")) {
				path = path.substring(1);
			} else if (path.startsWith("file:/")) {
				path = path.substring(6);
			}
		} else {
			if (path.startsWith("file:/")) {
				path = path.substring(5);
			}
		}
		path = path.replaceAll("%20", " ");
		if (path.toLowerCase().indexOf(".jar!") > 0) {
			try {
				path = path.substring(0, path.indexOf(".jar!") + 4);
				ZipFile z = new ZipFile(path);
				Enumeration all = z.getEntries();
				while (all.hasMoreElements()) {
					String name = all.nextElement().toString();
					if (name.startsWith("com.sinosoft.schema.")) {
						name = name.substring("com.sinosoft.schema.".length(), name.lastIndexOf(".") + 1);
						name = name.substring(0, name.indexOf("Schema"));
						if (hasSiteIDField(name)) {
							list.add(name);
						}
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		} else {
			File p = new File(path.substring(0, path.toLowerCase().indexOf("zdcodeschema.class")));
			File[] fs = p.listFiles();
			for (int i = 0; i < fs.length; i++) {
				String name = fs[i].getName();
				if (name.endsWith("Schema.class") && !name.startsWith("B")) {
					name = name.substring(0, name.length() - "Schema.class".length());
					if (hasSiteIDField(name)) {
						list.add(name);
					}
				}
			}
		}
		String[] names = new String[list.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = list.get(i).toString();
		}
		return names;
	}

	/**
	 * 判断表中是否含有SiteID字段
	 */
	public static boolean hasSiteIDField(String tableName) {
		if (tableName.equalsIgnoreCase("ZCAdminGroup")) {// 特别处理，不通过SiteID导出，通过TableRela导出
			return false;
		}
		try {
			Schema schema = (Schema) Class.forName("com.sinosoft.schema." + tableName + "Schema").newInstance();
			SchemaColumn[] scs = SchemaUtil.getColumns(schema);
			boolean flag = false;
			for (int i = 0; i < scs.length; i++) {
				String name = scs[i].getColumnName();
				if (name.equalsIgnoreCase("SiteID")) {
					flag = true;
				} else if (name.endsWith("ID") && !name.equals("ID")) {
					// System.out.println(tableName + "." + name);//仅手工建立关联关系时有用
				}
			}
			return flag;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 删除关联表中的关联记录
	 */
	public static void deleteRelated(Schema schema, Transaction tran) {
		String tableName = SchemaUtil.getTableCode(schema);
		TableRela[] trs = SiteTableRela.getRelas();
		Mapx map = schema.toCaseIgnoreMapx();
		for (int i = 0; i < trs.length; i++) {
			TableRela tr = trs[i];
			if (tr.RelaTable.equalsIgnoreCase(tableName)) {
				Object o = map.get(tr.RelaTable.toLowerCase());
				StringFormat sf = new StringFormat("delete from ? where ?=?");
				sf.add(tr.TableCode);
				sf.add(tr.KeyField);
				sf.add(o);
				QueryBuilder qb = new QueryBuilder(sf.toString());
				tran.add(qb);
			}
		}
	}

	/**
	 * 得到查找关联表某站点所有数据的查询构造器
	 */
	public static QueryBuilder getSelectQuery(long SiteID, TableRela tr) {
		StringFormat sf = null;
		if(tr.TableCode.equalsIgnoreCase("ZDColumnValue") || tr.TableCode.equalsIgnoreCase("ZDColumnRela")){
			if(Config.isSybase()){
				sf = new StringFormat("select * from ? where ? and exists (select 1 from ? where SiteID=? and convert(varchar,?)=?.?)");
			}else{
				sf = new StringFormat("select * from ? where ? and exists (select 1 from ? where SiteID=? and ?=?.?)");
			}
		}else{
			sf = new StringFormat("select * from ? where ? and exists (select 1 from ? where SiteID=? and ?=?.?)");
		}
		sf.add(tr.TableCode);
		if (StringUtil.isNotEmpty(tr.Condition)) {
			sf.add(tr.Condition);
		} else {
			sf.add("1=1");
		}
		sf.add(tr.RelaTable);
		sf.add(SiteID);
		sf.add(tr.RelaField);
		sf.add(tr.TableCode);
		sf.add(tr.KeyField);
		QueryBuilder qb = new QueryBuilder(sf.toString());
		return qb;
	}

	/**
	 * 得到关联表中某站点所有数据的删除查询构造器
	 */
	public static QueryBuilder getDeleteQuery(long SiteID, TableRela tr) {
		StringFormat sf = new StringFormat(
				"delete from ? where ? and exists (select 1 from ? where SiteID=? and ?=?.?)");
		sf.add(tr.TableCode);
		sf.add(tr.Condition);
		sf.add(tr.RelaTable);
		sf.add(SiteID);
		sf.add(tr.RelaField);
		sf.add(tr.TableCode);
		sf.add(tr.KeyField);
		QueryBuilder qb = new QueryBuilder(sf.toString());
		return qb;
	}

	/**
	 * 得到含有SiteID字段的表的取数查询构造器
	 */
	public static QueryBuilder getSelectQuery(long SiteID, String TableName) {
		StringFormat sf = new StringFormat("select * from ? where SiteID=?");
		sf.add(TableName);
		sf.add(SiteID);
		QueryBuilder qb = new QueryBuilder(sf.toString());
		return qb;
	}

	/**
	 * 得到含有SiteID字段的表的删除查询构造器
	 */
	public static QueryBuilder getDeleteQuery(long SiteID, String TableName) {
		StringFormat sf = new StringFormat("delete from ? where SiteID=?");
		sf.add(TableName);
		sf.add(SiteID);
		QueryBuilder qb = new QueryBuilder(sf.toString());
		return qb;
	}

	/**
	 * 表示两张表之间的关联关系
	 */
	public static class TableRela implements Serializable {
		private static final long serialVersionUID = 1L;
		/**
		 * 关联表名
		 */
		public String TableCode;
		/**
		 * 关联字段名
		 */
		public String KeyField;
		/**
		 * 被关联表名
		 */
		public String RelaTable;
		/**
		 * 被关联表中的被关联字段
		 */
		public String RelaField;
		/**
		 * 关联的其他条件
		 */
		public String Condition;

		/**
		 * 是否要根据本关联关系导出数据
		 */
		public boolean isExportData;

		/**
		 * 构造器
		 */
		public TableRela(String TableCode, String KeyField, String RelaTable, String RelaField, String Condition,
				boolean isExportData) {
			this.TableCode = TableCode;
			this.KeyField = KeyField;
			this.RelaTable = RelaTable;
			this.RelaField = RelaField;
			this.Condition = Condition;
			this.isExportData = isExportData;
		}

		/**
		 * 构造器,默认导出数据
		 */
		public TableRela(String TableCode, String KeyField, String RelaTable, String RelaField, String Condition) {
			this(TableCode, KeyField, RelaTable, RelaField, Condition, true);
		}
	}

	/**
	 * 表字段的MaxNo关联关系
	 */
	public static class NoType {
		public String TableCode;
		public String FieldName;
		public String NoType;

		public NoType(String TableCode, String FieldName, String NoType) {
			this.TableCode = TableCode;
			this.FieldName = FieldName;
			this.NoType = NoType;
		}
	}

	/**
	 * 清除掉所有SiteID错误的数据
	 */
	public static void clearData() {
		Transaction tran = new Transaction();
		String[] tables = SiteTableRela.getSiteIDTables();
		for (int i = 0; i < tables.length; i++) {
			QueryBuilder qb = new QueryBuilder("delete from " + tables[i]
					+ " where not exists (select 1 from ZCSite where ID="+tables[i]+".SiteID)");
			tran.add(qb);
		}
		TableRela[] trs = SiteTableRela.getRelas();
		for (int i = 0; i < trs.length; i++) {
			TableRela tr = trs[i];
			if (tr.isExportData) {
				StringFormat sf = new StringFormat("delete from ? where ? and not exists (select 1 from ? where ?=?.?)");
				sf.add(tr.TableCode);
				if (StringUtil.isNotEmpty(tr.Condition)) {
					sf.add(tr.Condition);
				} else {
					sf.add("1=1");
				}
				sf.add(tr.RelaTable);
				sf.add(tr.RelaField);
				sf.add(tr.TableCode);
				sf.add(tr.KeyField);
				QueryBuilder qb = new QueryBuilder(sf.toString());
				tran.add(qb);
			}
		}
		tran.commit();
	}

	/**
	 * 清除所有B表中的数据
	 */
	public static void clearAllBackupData() {
		String path = ZDCodeSchema.class.getResource("ZDCodeSchema.class").getPath();
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
			if (path.startsWith("/")) {
				path = path.substring(1);
			} else if (path.startsWith("file:/")) {
				path = path.substring(6);
			}
		} else {
			if (path.startsWith("file:/")) {
				path = path.substring(5);
			}
		}
		path = path.replaceAll("%20", " ");
		File p = new File(path.substring(0, path.toLowerCase().indexOf("zdcodeschema.class")));
		File[] fs = p.listFiles();
		for (int i = 0; i < fs.length; i++) {
			String name = fs[i].getName();
			if (name.endsWith("Schema.class") && name.startsWith("B")) {
				name = name.substring(0, name.length() - "Schema.class".length());
				QueryBuilder qb = new QueryBuilder("truncate table " + name);
				try {
					qb.executeNoQuery();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public static void main(String[] args) {
		clearAllBackupData();
	}
}
