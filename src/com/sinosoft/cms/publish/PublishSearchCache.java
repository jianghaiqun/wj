package com.sinosoft.cms.publish;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.template.CmsTemplateData;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.cms.template.ParserCache;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.statical.TemplateParser;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.FEMSearchProperties;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.SearchInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublishSearchCache {
	private static final Logger logger = LoggerFactory.getLogger(PublishSearchCache.class);
	private String templateDir;
	private String staticDir;
	private String strRisks;
	private Map<String, String> mapSearchConditoin;
	private String mFirstHTMLPath;
	private static Map<String, List<String>> allSearchConditoins;
	private String mstr_Conditoin = "";
	private String mstr_ConditoinValue = "";

	public static String Publish(String risks,
			Map<String, String> searchConditoinMap) {
		PublishSearchCache psc = new PublishSearchCache();
		psc.strRisks = risks;
		psc.mapSearchConditoin = searchConditoinMap;
		psc.mFirstHTMLPath = psc.getHTMLPath(searchConditoinMap);
		if (!psc.Publish()) {
			return "";
		}
		return psc.mFirstHTMLPath;
	}

	public static boolean syncSearchCondition(LongTimeTask lTT) {
		DataTable dt = new QueryBuilder(
				"select codevalue,codename from zdcode where codetype='ProductType' and parentcode='ProductType' order by codevalue")
				.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int iTotal = dt.getRowCount();
			Map<String, Object> map = new HashMap<String, Object>();
			FEMSearchProperties[] mFEMSearchProperties = null;
			SearchInfoResponse siInfo = null;
			QueryBuilder insertQB = new QueryBuilder(
					"INSERT INTO zdcode VALUES ('SearchCondition', ?, ?, ?, ?, null, null, null, null, '', ?, ?, null, null)");
			QueryBuilder delQB = new QueryBuilder(
					"delete from zdcode where codetype='SearchCondition' and ParentCode=?",
					"");
			for (int i = 0; i < iTotal; i++) {
				map.clear();
				map.put("ERiskSubType", dt.getString(i, 0));
				try {
					if (lTT != null) {
						lTT.setCurrentInfo("正在重置(" + (i + 1) + "/" + iTotal
								+ "):" + dt.getString(i, 1));
					}
					logger.info(
							"筛选页面接口获取数据:{}:{}", dt.getString(i, 0), dt.getString(i, 1));
					siInfo = ProductWebservice.SearchInfoServiceImpl(map, null);
					mFEMSearchProperties = siInfo.getFEMSearchProperties();
					reFillData(mFEMSearchProperties, insertQB, delQB);
					if (lTT != null) {
						lTT.setPercent(Integer.valueOf((i + 1) * 100
								/ (iTotal + 1)));
					}

				} catch (Exception e) {
					logger.error(
							"筛选页面接口获取数据失败：" + e.getMessage() + " continue..." + e.getMessage(), e);
					continue;
				}
			}
			if (allSearchConditoins != null) {
				allSearchConditoins.clear();
			}

			PublishSearchCache psc = new PublishSearchCache();
			psc.getHTMLPath(new HashMap<String, String>());
			if (lTT != null) {
				lTT.setPercent(100);
			}
		}
		return false;
	}

	private static void reFillData(FEMSearchProperties[] tFEMSearchProperties,
			QueryBuilder insertQB, QueryBuilder delQB) {
		if (tFEMSearchProperties != null && tFEMSearchProperties.length > 0
				&& tFEMSearchProperties[0] != null) {
			delQB.set(0, tFEMSearchProperties[0].getERiskSubType());
			delQB.executeNoQuery();
			ArrayList<Object> list = new ArrayList<Object>();
			for (int i = 0; i < tFEMSearchProperties.length; i++) {
				list.clear();
				list.add(tFEMSearchProperties[i].getERiskSubType());
				list.add(tFEMSearchProperties[i].getSearchCode());
				list.add(tFEMSearchProperties[i].getSearchName());
				list.add(i + 1);
				list.add(new Date());
				list.add("admin");
				insertQB.setParams(list);
				insertQB.executeNoQuery();

			}
		}
	}

	public static String getSearchPath(
			Map<String, String> searchConditoinMap) {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(searchConditoinMap.get("CatalogID"));
		catalog.fill();

		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		String siteAlias = site.getAlias();
		String staticDir = Config.getContextRealPath()
				+ Config.getValue("Statical.TargetDir").replace('\\', '/');
		String rootPath = staticDir + "/" + siteAlias + "/";
		String catalogPath = rootPath + CatalogUtil.getPath(catalog.getID())
				+ "/";
		catalogPath = catalogPath.replaceAll("/+", "/");

		PublishSearchCache psc = new PublishSearchCache();
		String fileName = psc.getHTMLPath(searchConditoinMap) + ".shtml";
		File f = new File(catalogPath + fileName);
		if (f.exists() && f != null) {
			return f.toString();
		}
		return null;
	}
	private String getHTMLPath(Map<String, String> searchConditoinMap) {
		String fileName = "";
		if (allSearchConditoins == null || allSearchConditoins.size() == 0) {
			DataTable dt = new QueryBuilder(
					"select parentcode,codevalue from zdcode where codetype='SearchCondition' order by CodeOrder")
					.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				allSearchConditoins = new HashMap<String, List<String>>();
				List<String> list = null;
				for (int i = 0; i < dt.getRowCount(); i++) {
					list = allSearchConditoins.get(dt.getString(i, 0));
					if (list == null) {
						list = new ArrayList<String>();
						list.add(dt.getString(i, 1));
						allSearchConditoins.put(dt.getString(i, 0), list);
					} else {
						list.add(dt.getString(i, 1));
					}
				}
			}
			if (allSearchConditoins == null) {
				allSearchConditoins = new HashMap<String, List<String>>();
			}
			List<String> list = new ArrayList<String>();
			list.add("ERiskType");
			list.add("SupplierCode");
			list.add("Age");
			list.add("Sex");
			allSearchConditoins.put("quicksearch", list);
		}
		List<String> list = allSearchConditoins.get(searchConditoinMap
				.get("ProductType"));
		if (list != null && list.size() > 0) {
			mstr_Conditoin = "";
			for (String name : list) {
				fileName += "_"
						+ (StringUtil.isEmpty(searchConditoinMap.get(name)) ? "0"
								: searchConditoinMap.get(name));
				mstr_Conditoin += "_" + name;
			}
			fileName += "_"
					+ (StringUtil.isEmpty(searchConditoinMap
							.get("ProductsOrder")) ? "0" : searchConditoinMap
							.get("ProductsOrder"));
			if (fileName.startsWith("_")) {
				fileName = fileName.substring(1);
			}
			mstr_ConditoinValue = fileName;
			if (mstr_Conditoin.startsWith("_")) {
				mstr_Conditoin = mstr_Conditoin.substring(1);
			}
		}
		if (!"".equals(fileName)) {
			fileName = StringUtil.md5Hex(fileName);
		} else {
			return "search";
		}
		return fileName;
	}

	private boolean Publish() {
		ArrayList<String> fileList = new ArrayList<String>();

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(mapSearchConditoin.get("CatalogID"));
		catalog.fill();
		// catalog.setMeta_Title(mapSearchConditoin.get("Meta_Title"));

		int level = CatalogUtil.getLevel(catalog.getID());
		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		String siteAlias = site.getAlias();
		String contextRealPath = Config.getContextRealPath();
		templateDir = contextRealPath
				+ Config.getValue("Statical.TemplateDir").replace('\\', '/');
		staticDir = contextRealPath
				+ Config.getValue("Statical.TargetDir").replace('\\', '/');
		staticDir = staticDir.replaceAll("/+", "/");
		String rootPath = staticDir + "/" + siteAlias + "/";
		rootPath = rootPath.replaceAll("/+", "/");
		String listTemplate = catalog.getListTemplate();
		TemplateParser listParser = null;
		if (StringUtil.isEmpty(listTemplate)) {
			return false;
		}
		// 编译生成列表页的模板
		listTemplate = templateDir + "/" + siteAlias + "/" + listTemplate;
		listTemplate = listTemplate.replaceAll("//", "/");
		listParser = ParserCache.get(site.getID(), listTemplate, level, false,
				fileList);
		if (listParser == null) {
			return false;
		}
		// 静态化模板数据类
		CmsTemplateData catalogTemplateData = new CmsTemplateData();
		catalogTemplateData.setLevel(level);
		catalogTemplateData.setSite(site);
		catalogTemplateData.setCatalog(catalog);

		String catalogPath = rootPath + CatalogUtil.getPath(catalog.getID())
				+ "/";
		catalogPath = catalogPath.replaceAll("/+", "/");
		// 生成列表页
		if (!staticListPages(site, catalog, catalogPath + mFirstHTMLPath
				+ ".shtml", listParser, catalogTemplateData, false,
				(int) catalog.getListPage())) {
			return false;
		}
		mFirstHTMLPath = site.getURL()
				+ ("/" + CatalogUtil.getPath(catalog.getID()) + "/"
						+ mFirstHTMLPath + ".shtml").replaceAll("/+", "/");
		return true;
	}

	@SuppressWarnings("rawtypes")
	private boolean staticListPages(ZCSiteSchema site, ZCCatalogSchema catalog,
			String fileName, TemplateParser catalogParser,
			CmsTemplateData templateData, boolean generateDetail,
			int publishPages) {
		Mapx listParams = new Mapx();
		PageGenerator p = new PageGenerator();

		listParams = catalogParser.getPageListPrams();

		String item = (String) listParams.get("item");
		if (StringUtil.isEmpty(item)) {
			return false;
		} else {
			item = item.toLowerCase();
		}

		// 统计代码
		String statScript = "";
		// if ("Y".equals(site.getAutoStatFlag())) {
		// if (catalog != null) {
		// String serviceUrl = Config.getValue("ServicesContext");
		// String statUrl = "SiteID=" + site.getID()
		// + "&Type=Article&CatalogInnerCode="
		// + catalog.getInnerCode() + "&Dest=" + serviceUrl
		// + "/Stat.jsp";
		// statScript = p.getStatScript(statUrl);
		// }
		// }
		 statScript += "\n<script type=\"text/javascript\">\n";
		 statScript += "var searchConditoinNames = \"" + mstr_Conditoin
		 + "\".split(\"_\");\n";
		 statScript += "var searchConditoinValues = \"" + mstr_ConditoinValue
		 + "\".split(\"_\");\n";
		 statScript += "</script>\n";

		DataTable allTables = getAllPagedDocList(templateData);
		// int total = strRisks.split(",").length;
		int total = allTables.getRowCount();
		if (total < 1) {
			// 空白页
			templateData.setTotal(total);
			templateData.setPageCount(0);
			templateData.setPageSize(1);
			templateData.setPageIndex(0);
			templateData.setListTable(new DataTable());
			if (!p.writeListHTML(catalogParser, templateData, fileName, 0,
					statScript)) {
				return false;
			}
		} else {
			int pageSize = 0;
			if (catalog != null && catalog.getListPageSize() > 0) {
				pageSize = (int) catalog.getListPageSize();
			} else {
				pageSize = 20;
			}
			if (publishPages == 0 && catalog.getListPage() > 0) {
				publishPages = (int) catalog.getListPage();// 列表页最大分页数
			}
			// 当列表页最大分页数小于指定页数时，以列表页最大分页数为准
			if (catalog.getListPage() > 0
					&& catalog.getListPage() < publishPages) {
				publishPages = (int) catalog.getListPage();
			}
			// 如果指定了生成页数，并且文档总数大于需要生成的数目，则以生成数为准
			if (catalog.getListPage() > 0) {
				int publishTotal = publishPages * pageSize;
				if (total > publishTotal) {
					total = publishTotal;
				}
			}
			int pageCount = (total % pageSize == 0) ? total / pageSize : total
					/ pageSize + 1;
			templateData.setTotal(total);
			templateData.setPageCount(pageCount);
			templateData.setPageSize(pageSize);

			int pageIndex = 0;
			// 设置发布页数
			if (publishPages > 0) {
				pageCount = publishPages;
			}

			int rowNum = 1;
			for (int k = 0; k < pageCount; k++) {
				templateData.setPageIndex(pageIndex);
				int count = pageSize;
				if ((k + 1) * pageSize > total) {
					count = total - k * pageSize;
					if (count <= 0) {
						break;
					}
				}
				DataTable listTable = getPagedDocList(allTables, pageSize,
						pageIndex);

				// 扩展上一页、下一页
				listTable.insertColumns(new String[] { "PrevLink", "NextLink",
						"PrevTitle", "NextTitle" });
				// 记录编号
				for (int i = 0; i < listTable.getRowCount(); i++) {
					listTable.set(i, "_RowNo", rowNum);
					rowNum++;
				}
				templateData.setListTable(listTable);
				if (!p.writeListHTML(catalogParser, templateData, fileName,
						pageIndex, statScript)) {
					return false;
				}
				pageIndex++;
			}
		}
		return true;
	}

	private DataTable getPagedDocList(DataTable allTables, int pageSize,
			int pageIndex) {
		DataTable table = new DataTable();
		for (int i = 0; i < pageSize; i++) {
			if (allTables.getRowCount() <= pageSize * pageIndex + i) {
				break;
			}
			table.insertRow(allTables.get(pageSize * pageIndex + i));
		}
		return table;
	}

	private DataTable getAllPagedDocList(CmsTemplateData templateData) {
		DataTable dt = new DataTable();
		if (strRisks == null || "".equals(strRisks)) {
			return dt;
		}
		String[] riskArr = strRisks.split(",");
		QueryBuilder qb = null;
		if ("quick".equals(mapSearchConditoin.get("SearchType"))) {
			qb = new QueryBuilder(
					"select b.* from zdcolumnvalue a, zcarticle b where "
							+ "a.RelaID =  b.ID and a.ColumnCode = 'RiskCode' "
							+ "and a.RelaType = '2' and b.CatalogID = ? and TextValue = ? ",
					mapSearchConditoin.get("CatalogID2"), "");
		} else {
			qb = new QueryBuilder(
					"select b.* from zdcolumnvalue a, zcarticle b where "
							+ "a.RelaID =  b.ID and a.ColumnCode = 'RiskCode' "
							+ "and a.RelaType = '2' and b.CatalogID = ? and TextValue = ? ",
					mapSearchConditoin.get("CatalogID"), "");
		}

		DataTable risktable = null;
		for (int i = 0; i < riskArr.length; i++) {
			qb.set(1, riskArr[i]);
			risktable = qb.executeDataTable();
			if (risktable != null && risktable.getRowCount() > 0) {
				dt.insertRow(risktable.get(0));
			}
		}

		dt.insertColumn("ArticleLink");
		dt.insertColumn("_RowNo");
		String levelString = templateData.getLevelStr();
		String[] columnValue = new String[dt.getRowCount()];
		String[] catalogValue = new String[dt.getRowCount()];
		String[] catalogLink = new String[dt.getRowCount()];
		if (dt.getRowCount() > 0) {
			ColumnUtil.extendDocColumnData(dt, dt.getString(0, "catalogID"));
			dt.insertColumn("filePath");
		}
		dt.insertColumns(new String[] { "FirstImagePath", "FirstVideoImage",
				"FirstVideoHtml", "FirstTag", "FirstTagUrl" });
		templateData.dealTag(dt);
		dt.insertColumn("AttributeName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "_RowNo", i + 1);
			// String docID = dt.getString(i, "ID");
			String docCatalogID = dt.getString(i, "catalogID");

			ZCCatalogSchema catalogSchema = CatalogUtil.getSchema(docCatalogID);

			// 取栏目的名称和链接URL
			catalogValue[i] = catalogSchema.getName();
			catalogLink[i] = CatalogUtil.getLink(docCatalogID, levelString);

			if ("4".equals((String) dt.get(i, "Type"))) { // 转向链接
				columnValue[i] = (String) dt.get(i, "RedirectURL");
			} else {
				columnValue[i] = levelString + PubFun.getDocURL(dt.get(i));

				String imagePath = Config.getContextPath()
						+ Config.getValue("UploadDir") + "/"
						+ SiteUtil.getAlias(catalogSchema.getSiteID()) + "/";
				imagePath = imagePath.replaceAll("/+", "/");

				String attachPath = Config.getContextPath()
						+ "/Services/AttachDownLoad.jsp";
				attachPath = imagePath.replaceAll("/+", "/");

				String content = dt.getString(i, "Content");
				content = content.replaceAll(imagePath, PubFun
						.getLevelStr(CatalogUtil.getDetailLevel(docCatalogID)));
				content = content.replaceAll(attachPath,
						Config.getValue("ServicesContext")
								+ "AttachDownLoad.jsp");
			}

			// 根据属性获取关联图片、附件、视频相关信息
			PubFun.dealArticleMedia(dt, levelString, "image,attchment,video");

		}
		dt.insertColumn("Link", columnValue);
		dt.insertColumn("CatalogName", catalogValue);
		dt.insertColumn("CatalogLink", catalogLink);
		dt.insertColumn("BranchName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "BranchName",
					PubFun.getBranchName(dt.getString(i, "BranchInnerCode")));
		}
		return dt;
	}

	public static String getCacheHTMLPath(Map<String, String> searchConditoinMap) {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(searchConditoinMap.get("CatalogID"));
		catalog.fill();

		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		String siteAlias = site.getAlias();
		String staticDir = Config.getContextRealPath()
				+ Config.getValue("Statical.TargetDir").replace('\\', '/');
		String rootPath = staticDir + "/" + siteAlias + "/";
		String catalogPath = rootPath + CatalogUtil.getPath(catalog.getID())
				+ "/";
		catalogPath = catalogPath.replaceAll("/+", "/");

		PublishSearchCache psc = new PublishSearchCache();
		String fileName = psc.getHTMLPath(searchConditoinMap) + ".shtml";
		File f = new File(catalogPath + fileName);
		if (f.exists()) {
			return site.getURL()
					+ ("/" + CatalogUtil.getPath(catalog.getID()) + "/" + fileName)
							.replaceAll("/+", "/");
		}
		return null;
	}
}
