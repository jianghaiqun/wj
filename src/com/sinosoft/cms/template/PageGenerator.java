package com.sinosoft.cms.template;

import com.sinosoft.Product;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.resource.ConfigImageLib;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.cms.site.Keyword;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.script.EvalException;
import com.sinosoft.framework.utility.Big5Convert;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCKeywordSchema;
import com.sinosoft.schema.ZCKeywordSet;
import com.sinosoft.schema.ZCPageBlockSchema;
import com.sinosoft.schema.ZCPageBlockSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.statical.TemplateException;
import com.sinosoft.statical.TemplateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 静态化页面
 * 
 */
public class PageGenerator {

	private static final Logger logger = LoggerFactory.getLogger(PageGenerator.class);
	private String templateDir;

	private String staticDir;

	private ArrayList fileList = new ArrayList();

	private Product product = new Product();

	private LongTimeTask task;

	public static Long CATALOGID;

	public PageGenerator() {
		this(null);
	}

	public PageGenerator(LongTimeTask ltt) {
		String contextRealPath = Config.getContextRealPath();
		templateDir = contextRealPath + Config.getValue("Statical.TemplateDir").replace('\\', '/');
		staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		staticDir = staticDir.replaceAll("/+", "/");

		task = ltt;
		if (ltt == null) {
			task = LongTimeTask.createEmptyInstance();
		}
	}

	public boolean staticCatalog(ZCCatalogSchema catalog, boolean isGenerateDetail, boolean isGenerateList) {
		return staticCatalog(catalog, isGenerateDetail, isGenerateList, false, 0);
	}
	
	public boolean staticCatalog(ZCCatalogSchema catalog, boolean isGenerateDetail, boolean isGenerateList, int publishPages) {
		return staticCatalog(catalog, isGenerateDetail, isGenerateList, false, publishPages);
	}
	
	public boolean staticCatalog(ZCCatalogSchema catalog, boolean isGenerateDetail, boolean isGenerateList, boolean isGenerateWapDetail) {
		return staticCatalog(catalog, isGenerateDetail, isGenerateList, isGenerateWapDetail, 0);
	}

	/**
	 * 生成栏目首页、列表页和文章静态页面 不包含子目录
	 * 
	 * @param className
	 * @param catalogID
	 * @param articleClass
	 */
	public boolean staticCatalog(ZCCatalogSchema catalog, boolean isGenerateDetail, boolean isPublishList, boolean isGenerateWapDetail, int publishPages) {
		TemplateCache.clear();
		long t = System.currentTimeMillis();

		String message = "";

		int level = CatalogUtil.getLevel(catalog.getID());
		boolean flag = true; // 成功标记

		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());

		task.setCurrentInfo("正在发布栏目\"" + catalog.getName() + "\"");

		// 发布栏目相关区块
		staticPageBlock(site, catalog, 0);

		// 如果栏目链接到外部地址，则本栏目不发布
		String catalogURL = catalog.getURL();
		if (StringUtil.isNotEmpty(catalogURL) && (catalogURL.startsWith("http://") || catalogURL.startsWith("https://"))) {
			return true;
		}

		String siteAlias = site.getAlias();
		String rootPath = staticDir + "/" + siteAlias + "/";
		rootPath = rootPath.replaceAll("/+", "/");

		// 静态化模板数据类
		CmsTemplateData catalogTemplateData = new CmsTemplateData();
		catalogTemplateData.setLevel(level);
		catalogTemplateData.setSite(site);
		catalogTemplateData.setCatalog(catalog);
		ZCArticleSchema article = new ZCArticleSchema();
		catalogTemplateData.setArticle(article);

		// 生成栏目首页
		String indexTemplate = catalog.getIndexTemplate();
		if (StringUtil.isNotEmpty(indexTemplate) && !"Y".equals(catalog.getSingleFlag())) {
			indexTemplate = templateDir + "/" + siteAlias + "/" + indexTemplate;
			indexTemplate = indexTemplate.replaceAll("/+", "/");
			String path = rootPath + CatalogUtil.getPath(catalog.getID());
			TemplateParser tp = getParser(site.getID(), indexTemplate, level);
			if (tp == null) {
				message = "栏目“" + catalog.getName() + "”首页模板" + catalog.getIndexTemplate() + "不存在";
				logger.warn(message);
				task.addError(message);
				return false;
			}
			tp.define("site", catalogTemplateData.getSite());
			tp.define("TemplateData", catalogTemplateData);
			tp.define("catalog", catalogTemplateData.getCatalog());

			String ext = SiteUtil.getExtensionType(catalog.getSiteID());
			String fileName = "index." + ext;
			String statScript = "";
			// if ("Y".equals(site.getAutoStatFlag())) {
			// // 统计代码
			// String serviceUrl = Config.getValue("ServicesContext");
			// String statUrl = "SiteID=" + site.getID() +
			// "&Type=Article&CatalogInnerCode=" + catalog.getInnerCode()
			// + "&Dest=" + serviceUrl + "/Stat.jsp";
			// statScript = getStatScript(statUrl);
			// }
			//
			// statScript += getVersionInfo();
			fileName = path + "/" + fileName;

			if (!writeHTML(tp, fileName, statScript)) {
				return false;
			}
		}

		// 生成栏目Rss
		String rssTemplate = catalog.getRssTemplate();
		if (StringUtil.isNotEmpty(rssTemplate)) {
			rssTemplate = templateDir + "/" + siteAlias + "/" + rssTemplate;
			rssTemplate = rssTemplate.replaceAll("/+", "/");
			String path = rootPath + CatalogUtil.getPath(catalog.getID());
			TemplateParser tp = getParser(site.getID(), rssTemplate, level);
			if (tp != null) {
				tp.define("site", catalogTemplateData.getSite());
				tp.define("TemplateData", catalogTemplateData);
				tp.define("catalog", catalogTemplateData.getCatalog());
				tp.define("article", catalogTemplateData.getArticle());
				task.setCurrentInfo("正在生成栏目\"" + catalog.getName() + "\" Rss文件");
				if (!writeHTML(tp, path + "/rss.xml", "")) {
					return false;
				}
			} else {
				message = "栏目“" + catalog.getName() + "”Rss模板" + catalog.getRssTemplate() + "不存在";
				logger.warn(message);
				task.addError(message);
				return false;
			}
		}
		TemplateParser listParser = null;
		if (!"Y".equals(catalog.getSingleFlag())) {
			String listTemplate = catalog.getListTemplate();
			// 只设置首页 允许列表页模板为空
			if (StringUtil.isNotEmpty(indexTemplate) && StringUtil.isEmpty(listTemplate)) {
				// 如果没有详细页和列表页，仍然需要生成相应的系统包含区块
				staticInnerPageBlock(site, catalog);
				staticPageBlock(site, catalog, 4);
				return true;
			}
			// 发布列表页标记不等于off 即根据原有规则发布 标记可以关闭，但列表页模板不能为空！ 所以这里判断为空的代码没有去掉
			// （详细页同理）
			if (StringUtil.isEmpty(listTemplate)) {
				message = "栏目“" + catalog.getName() + "”列表页模板" + catalog.getListTemplate() + "不存在";
				if (task != null) {
					task.addError(message);
				}
				Errorx.addError(message);
				return true;
			}
			// 编译生成列表页的模板
			listTemplate = templateDir + "/" + siteAlias + "/" + listTemplate;
			listTemplate = listTemplate.replaceAll("//", "/");
			listParser = getParser(site.getID(), listTemplate, level);
			if (listParser == null) {
				message = "栏目“" + catalog.getName() + "”列表页模板" + catalog.getListTemplate() + "不存在";
				if (task != null) {
					task.addError(message);
				}
				Errorx.addError(message);
				return true;
			}
		}
		// 详细页模板，允许详细页模板为空
		if (StringUtil.isEmpty(catalog.getDetailTemplate())) {
			if (catalog.getType() != Catalog.CATALOG) {// 媒体库栏目直接忽略
				return true;
			}
			// 发布详细页标记不等于off 即根据原有规则发布
			if (!"Y".equals(catalog.getSingleFlag()) && StringUtil.isEmpty(catalog.getIndexTemplate()) && !"off".equals(catalog.getPublishDT())) {
				message = "栏目“" + catalog.getName() + "”详细页模板" + catalog.getDetailTemplate() + "不存在";
				if (task != null) {
					task.addError(message);
				}
				Errorx.addError(message);
			}
		}
		// Wap站详细页模板，允许Wap站详细页模板为空
		if (StringUtil.isEmpty(catalog.getWapDetailTemplate())) {
			if (catalog.getType() != Catalog.CATALOG) {// 媒体库栏目直接忽略
				return true;
			}
			// 发布详细页标记不等于off 即根据原有规则发布
			if (!"Y".equals(catalog.getSingleFlag()) && StringUtil.isEmpty(catalog.getIndexTemplate()) && !"off".equals(catalog.getPublishWDT())) {
				message = "栏目“" + catalog.getName() + "”的Wap站详细页模板" + catalog.getWapDetailTemplate() + "不存在";
				if (task != null) {
					task.addError(message);
				}
				Errorx.addError(message);
			}
		}
		// 列表页命名规则
		String listNameRule = null;
		if (StringUtil.isEmpty(indexTemplate)) {
			String ext = ServletUtil.getUrlExtension(catalog.getListTemplate());
			String extSite = SiteUtil.getExtensionType(catalog.getSiteID());
			if (ext.equals(".jsp") || "jsp".equals(extSite)) {
				listNameRule = "index.jsp";
			} else {
				listNameRule = "index.shtml";
			}
		} else {
			String ext = ServletUtil.getUrlExtension(indexTemplate);
			String extSite = SiteUtil.getExtensionType(catalog.getSiteID());
			if (ext.equals(".jsp") || "jsp".equals(extSite)) {
				listNameRule = "list.jsp";
			} else {
				listNameRule = "list.shtml";
			}
		}

		String catalogPath = rootPath + CatalogUtil.getPath(catalog.getID());
		// 生成列表页
		// 发布详细页标记不等于off 即根据原有规则发布
		boolean isDetail = isGenerateDetail;
		if ("off".equals(catalog.getPublishDT())) {
			isDetail = false;
		}
		// 发布Wap站详细页标记不等于off 即根据原有规则发布
		boolean isWapDetail = isGenerateWapDetail;
		if ("off".equals(catalog.getPublishWDT())) {
			isWapDetail = false;
		}

		if ("Y".equals(catalog.getSingleFlag())) {
			Boolean flagExist = false;
			ZCArticleSchema articleSingle = new ZCArticleSchema();
			ZCArticleSet articleSingleSet = articleSingle.query(new QueryBuilder("where catalogid=?", catalog.getID()));
			String url = "";
			for (int i = 0; i < articleSingleSet.size(); i++) {
				articleSingle = articleSingleSet.get(i);
				url = articleSingle.getURL();
				if (url.endsWith(site.getProp1())) {
					flagExist = true;
					break;
				}
			}
			if (flagExist && articleSingleSet.size() < 2) {
				String headurl = url.substring(url.lastIndexOf("/"));
				flag = staticListPages(site, catalog, catalogPath + headurl, listParser, catalogTemplateData, isDetail, isWapDetail, isPublishList, publishPages);
			} else {

				flag = staticListPages(site, catalog, catalogPath + listNameRule, listParser, catalogTemplateData, isDetail, isWapDetail, isPublishList, publishPages);
			}

		} else {
			if (listParser.getPageListPrams().size() > 0) { // 存在分页标签 page=true
				flag = staticListPages(site, catalog, catalogPath + listNameRule, listParser, catalogTemplateData, isDetail, isWapDetail, isPublishList, publishPages);
			} else {
				listParser.define("site", catalogTemplateData.getSite());
				listParser.define("TemplateData", catalogTemplateData);
				listParser.define("catalog", catalogTemplateData.getCatalog());
				listParser.define("article", catalogTemplateData.getArticle());
				String statScript = "";
				// if ("Y".equals(site.getAutoStatFlag())) {
				// // 统计代码
				// String serviceUrl = Config.getValue("ServicesContext");
				// String statUrl = "SiteID=" + site.getID() +
				// "&Type=Article&CatalogInnerCode=" + catalog.getInnerCode()
				// + "&Dest=" + serviceUrl + "/Stat.jsp";
				// statScript = getStatScript(statUrl);
				// }

				// statScript += getVersionInfo();
				if (catalog != null && !"off".equals(catalog.getPublishLT()) && isPublishList) {
					String fileName = catalogPath + listNameRule;
					if (!writeHTML(listParser, fileName, statScript)) {
						return false;
					}
				}
			}
		}
		// 生成栏目对应的系统区块文件，根据栏目模板找到在其他栏目中的对应区块
		staticInnerPageBlock(site, catalog);
		// 生成系统生成的区块
		staticPageBlock(site, catalog, 4);

		logger.info("生成栏目{}耗时{}", catalog.getName(), (System.currentTimeMillis() - t));
		return flag;
	}

	/**
	 * 静态化子栏目及所属文章 采用递归方法静态化所有的子栏目
	 * 
	 * @param parentID
	 * @param detail
	 * @param publishSize
	 * @return
	 */
	public boolean staticChildCatalog(long parentID, boolean detail, boolean list, boolean wapDetail, int publishSize) {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder("where parentid=?", parentID));
		for (int i = 0; i < catalogSet.size(); i++) {
			catalog = catalogSet.get(i);
			if (!staticCatalog(catalog, detail, list, wapDetail, publishSize)) {
				return false;
			}
			if (!staticChildCatalog(catalogSet.get(i).getID(), detail, list, wapDetail, publishSize)) {
				return false;
			}
		}
		return true;
	}

	public boolean staticChildCatalog(long parentID, boolean detail, boolean list) {
		return staticChildCatalog(parentID, detail, list, false, 0);
	}

	/**
	 * 生成列表页
	 * 
	 * @param site
	 * @param catalog
	 * @param fileName
	 * @param catalogParser
	 * @param templateData
	 * @param generateDetail
	 * @param publishPages
	 * @return
	 */
	private boolean staticListPages(ZCSiteSchema site, ZCCatalogSchema catalog, String fileName, TemplateParser catalogParser, CmsTemplateData templateData, 
			boolean generateDetail, boolean generateWapDetail, boolean generateList, int publishPages) {
		Mapx listParams = new Mapx();

		// 是否为单页栏目
		boolean singlePageCatalog = catalog != null && "Y".equals(catalog.getSingleFlag());
		if (singlePageCatalog) {
			listParams.put("item", "article");
			listParams.put("page", "true");
			listParams.put("pagesize", "1");
		} else {
			listParams = catalogParser.getPageListPrams();
		}

		String item = (String) listParams.get("item");
		if (StringUtil.isEmpty(item)) {
			String message = "列表页模板" + catalogParser.getTemplate() + "的cms:list标签未设置item参数。";
			task.addError(message);
			return false;
		} else {
			item = item.toLowerCase();
		}
		String type = (String) listParams.get("type");
		String pagesizeStr = (String) listParams.get("pagesize");
		String condition = (String) listParams.get("condition");
		String displayLevel = (String) listParams.get("level");
		String hasAttribute = (String) listParams.get("hasattribute");
		String catalogName = (String) listParams.get("name");
		String catalogID = (String) listParams.get("id");
		String tag = (String) listParams.get("tag");
		if (StringUtil.isNotEmpty(catalogID)) {
			catalogName = catalogID;
		}
		if (StringUtil.isEmpty(catalogName)) {
			catalogName = catalog.getID() + "";
		}
		// 判断是否是当前栏目，如果是则生成详细页，否则不生成详细页
		boolean isCurrentCatalog = catalog != null && (catalogName.equals(catalog.getID() + "") || catalogName.equals(catalog.getName()));

		TemplateParser detailParser = null;
		TemplateParser wapDetailParser = null;
		int detailLevel = templateData.getLevel();
		if ((generateDetail && isCurrentCatalog) || singlePageCatalog) {
			String detailTemplate = templateDir + "/" + site.getAlias() + "/" + catalog.getDetailTemplate();
			detailTemplate = detailTemplate.replaceAll("//", "/");
			if (StringUtil.isNotEmpty(catalog.getDetailNameRule())) {
				detailLevel = CatalogUtil.getDetailLevel(catalog.getID());
			}
			detailParser = getParser(site.getID(), detailTemplate, detailLevel);
		}
		if ((generateWapDetail && isCurrentCatalog) || singlePageCatalog) {
			String wapDetailTemplate = templateDir + "/" + site.getAlias() + "/" + catalog.getWapDetailTemplate();
			wapDetailTemplate = wapDetailTemplate.replaceAll("//", "/");
			if (StringUtil.isNotEmpty(catalog.getDetailNameRule())) {
				detailLevel = CatalogUtil.getDetailLevel(catalog.getID());
			}
			wapDetailParser = getParser(site.getID(), wapDetailTemplate, detailLevel);
		}
		CmsTemplateData detailTemplateData = new CmsTemplateData();
		detailTemplateData.setLevel(detailLevel);
		detailTemplateData.setSite(site);
		if (catalog != null) {
			detailTemplateData.setCatalog(catalog);
		}

		// 统计代码
		String statScript = "";
		// if ("Y".equals(site.getAutoStatFlag())) {
		// if (catalog != null) {
		// String serviceUrl = Config.getValue("ServicesContext");
		// String statUrl = "SiteID=" + site.getID() +
		// "&Type=Article&CatalogInnerCode=" + catalog.getInnerCode()
		// + "&Dest=" + serviceUrl + "/Stat.jsp";
		// statScript = getStatScript(statUrl);
		// }
		// }

		int total = templateData.getPagedDocListTotal(item, catalogName, displayLevel, hasAttribute, type, condition, tag);
		if (total < 1) {
			// 空白页
			if (singlePageCatalog && detailParser != null) {
				ZCArticleSchema row = new ZCArticleSchema();
				row.setPublishDate(new Date());
				row.setContent("该栏目没有添加文章，请添加文章。");
				row.setSiteID(catalog.getSiteID());
				row.setCatalogID(catalog.getID());
				row.setCatalogInnerCode(catalog.getInnerCode());

				DataRow emptyRow = row.toDataRow();
				if (Config.isOracle()) {
					emptyRow.insertColumn("RNM", "1");
				}
				staticDoc(item, detailParser, detailTemplateData, emptyRow, fileName);
			} else {
				templateData.setTotal(total);
				templateData.setPageCount(0);
				templateData.setPageSize(Integer.parseInt(pagesizeStr));
				templateData.setPageIndex(0);
				templateData.setListTable(new DataTable());
				// 发布列表页标记不等于off 即根据原有规则发布
				if (catalog != null && !"off".equals(catalog.getPublishLT()) && generateList) {
					if (!writeListHTML(catalogParser, templateData, fileName, 0, statScript)) {
						return false;
					}
				}
			}
		} else {
			if (singlePageCatalog && detailParser != null) {
				DataTable listTable = templateData.getPagedDocList(item, catalogName, displayLevel, hasAttribute, type, condition, tag, 1, 0);
				DataRow docRow = listTable.get(0);
				staticDoc(item, detailParser, detailTemplateData, docRow, fileName);
			} else {
				int pageSize = Integer.parseInt(pagesizeStr);
				if (pageSize == 0) {// 标签中未声明每页多少
					if (catalog != null && catalog.getListPageSize() > 0) {// 使用栏目参数
						pageSize = (int) catalog.getListPageSize();
					} else {
						// 默认20条
						pageSize = 20;
					}
				}

				if (publishPages == 0 && catalog.getListPage() > 0) {
					publishPages = (int) catalog.getListPage();// 列表页最大分页数
				}

				// 当列表页最大分页数小于指定页数时，以列表页最大分页数为准
				if (catalog.getListPage() > 0 && catalog.getListPage() < publishPages) {
					publishPages = (int) catalog.getListPage();
				}

				// 如果指定了生成页数，并且文档总数大于需要生成的数目，则以生成数为准
				if (catalog.getListPage() > 0) {
					int publishTotal = publishPages * pageSize;
					if (total > publishTotal) {
						total = publishTotal;
					}
				}

				int pageCount = (total % pageSize == 0) ? total / pageSize : total / pageSize + 1;

				templateData.setTotal(total);
				templateData.setPageCount(pageCount);
				templateData.setPageSize(pageSize);

				int pageIndex = 0;
				// 设置发布页数
				if (publishPages > 0) {
					pageCount = publishPages;
				}

				int rowNum = 1;
				DataRow prevDoc = null;
				for (int k = 0; k < pageCount; k++) {
					templateData.setPageIndex(pageIndex);

					int count = pageSize;
					if ((k + 1) * pageSize > total) {
						count = total - k * pageSize;
						if (count <= 0) {
							break;
						}
					}
					DataTable listTable = templateData.getPagedDocList(item, catalogName, displayLevel, hasAttribute, type, condition, tag, pageSize, pageIndex);

					// 扩展上一页、下一页
					listTable.insertColumns(new String[] { "PrevLink", "NextLink", "PrevTitle", "NextTitle" });
					// 记录编号
					for (int i = 0; i < listTable.getRowCount(); i++) {
						listTable.set(i, "_RowNo", rowNum);
						rowNum++;
					}

					templateData.setListTable(listTable);
					// 发布列表页标记不等于off 即根据原有规则发布
					if (catalog != null && !"off".equals(catalog.getPublishLT()) && generateList) {
						if (!writeListHTML(catalogParser, templateData, fileName, pageIndex, statScript)) {
							return false;
						}
					}

					// 生成详细页
					if (detailParser != null) {
						// 根据当前栏目id，获取对应的详细页面
						ZCArticleSet tZCarticleSet = new ZCArticleSchema().query(new QueryBuilder("where catalogid = ? and status in (" + Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
								+ ") ", catalog.getID()), pageSize, pageIndex);
						for (int i = 0; i < tZCarticleSet.size(); i++) {
							ZCArticleSchema tZCarticle = tZCarticleSet.get(i);
							try {
								if (task != null) {
									task.setCurrentInfo("正在发布栏目\"" + catalog.getName() + "\" 的文章 " + tZCarticle.getTitle());
								}

								PageGenerator p = new PageGenerator(task);
								// 文章类型的栏目取title
								int catalogType = (int) catalog.getType();
								boolean isArticle = catalogType == Catalog.CATALOG || catalogType == Catalog.SUBJECT || catalogType == Catalog.MAGAZINE || catalogType == Catalog.NEWSPAPER;
								if (isArticle && "4".equals((String) tZCarticle.getType())) { // 转向链接不生成详细页
									continue;

								} else {
									logger.info("生成文章详细页面: {} -- 文章类型:{}",tZCarticle.getURL(), tZCarticle.getType());
									p.staticDoc("Article", tZCarticle);
								}
							} catch (Exception e) {
								logger.error("生成文章详细页面异常: " + tZCarticle.getID() + e.getMessage(), e);
							}
						}
					}
					
					// 生成Wap站详细页
					if (wapDetailParser != null) {
						// 根据当前栏目id，获取对应的详细页面
						ZCArticleSet tZCarticleSet = new ZCArticleSchema().query(new QueryBuilder("where catalogid = ? and status in (" + Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
								+ ") ", catalog.getID()), pageSize, pageIndex);
						for (int i = 0; i < tZCarticleSet.size(); i++) {
							ZCArticleSchema tZCarticle = tZCarticleSet.get(i);
							try {
								if (task != null) {
									task.setCurrentInfo("正在发布栏目\"" + catalog.getName() + "\" 的Wap站文章 " + tZCarticle.getTitle());
								}

								PageGenerator p = new PageGenerator(task);
								// 文章类型的栏目取title
								int catalogType = (int) catalog.getType();
								boolean isArticle = catalogType == Catalog.CATALOG || catalogType == Catalog.SUBJECT || catalogType == Catalog.MAGAZINE || catalogType == Catalog.NEWSPAPER;
								if (isArticle && "4".equals((String) tZCarticle.getType())) { // 转向链接不生成详细页
									continue;

								} else {
									logger.info("生成文章Wap站详细页面: {} -- 文章类型:{}",tZCarticle.getURL(), tZCarticle.getType());
									p.staticDoc("Article", tZCarticle, "wap");
								}
							} catch (Exception e) {
								logger.error("生成文章详细页面异常: " + tZCarticle.getID() + e.getMessage(), e);
							}
						}
					}

					pageIndex++;
				}
			}
		}
		return true;
	}

	/**
	 * 根据站点id生成静态页面，递归调用
	 * 
	 * @param siteID
	 * @param parentID
	 */
	public boolean staticSite(long siteID) {
		TemplateCache.clear();
		// 区块
		task.setCurrentInfo("正在处理区块");
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(siteID);
		site.fill();

		if (!staticPageBlock(site, null, 0)) {
			return false;
		}
		task.setPercent(4);

		// 首页
		task.setCurrentInfo("正在处理站点首页");
		if (!staticSiteIndex(site)) {
			return false;
		}

		// 栏目
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder("where siteid=? and parentid=0 and type in (1,9)", siteID));
		for (int i = 0; i < catalogSet.size(); i++) {
			if (task.checkStop()) {
				return true;
			}
			catalog = catalogSet.get(i);
			task.setCurrentInfo("正在生成栏目" + catalog.getName());
			if (!staticCatalog(catalog, true, true)) {
				return false;
			}
			if (!staticChildCatalog(catalog.getID(), true, true)) {
				return false;
			}
			logger.info("percent:{}", (4 + i * 90 / catalogSet.size()));
			task.setPercent(4 + i * 90 / catalogSet.size());
		}
		task.setPercent(98);
		return true;
	}

	/**
	 * 静态化首页
	 * 
	 * @param siteID
	 * @return
	 */
	public boolean staticSiteIndex(long siteID) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(siteID);
		if (!site.fill()) {
			return false;
		}
		task.setCurrentInfo("发布站点首页");

		if (!staticPageBlock(site, null, 0)) {
			return false;
		}

		if (task.getPercent() < 40) {
			task.setPercent(40);
		}

		return staticSiteIndex(site);
	}

	public boolean staticSiteIndex(ZCSiteSchema site) {
		long t = System.currentTimeMillis();
		TemplateCache.clear();

		String template = templateDir + "/" + site.getAlias() + "/" + site.getIndexTemplate();
		if (template == null) {
			return false;
		}
		template = template.replaceAll("//", "/");
		TemplateParser tp = getParser(site.getID(), template, 0);
		logger.info("编译中间脚本:{}", (System.currentTimeMillis() - t));

		if (tp == null) {
			return false;
		}

		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite(site);
		tp.define("TemplateData", templateData);
		tp.define("site", site.toDataRow());
		String path = SiteUtil.getAbsolutePath(site.getID()) + "/";
		String fileName = "index." + SiteUtil.getExtensionType(site.getID());

		String statScript = "";
		// if ("Y".equals(site.getAutoStatFlag())) {
		// // 统计代码
		// String serviceUrl = Config.getValue("ServicesContext");
		// String statUrl = "SiteID=" + site.getID() + "&Type=Article&Dest=" +
		// serviceUrl + "/Stat.jsp";
		// statScript = getStatScript(statUrl);
		// }
		//
		// statScript += getVersionInfo();

		task.setCurrentInfo("生成首页文件：" + fileName);

		boolean flag = writeHTML(tp, path + "/" + fileName, statScript);

		template = template.replaceAll(staticDir, "");
		int level = 0;
		ZCPageBlockSet blockSet = new ZCPageBlockSchema().query(new QueryBuilder("where exists (select blockid from ZCTemplateBlockRela where filename=? and blockid=ZCPageBlock.ID)", template + "_"
				+ level));
		if (blockSet != null && blockSet.size() > 0) {
			staticPageBlock(blockSet);
		}

		logger.info("生成首页耗时:{}", (System.currentTimeMillis() - t));
		return flag;
	}
	
	public boolean staticDoc(String docType, Schema doc) {
		return staticDoc(docType, doc, null);
	}

	/**
	 * 静态化文档页面
	 * 
	 * @param doc
	 * @param publishType:发布页面类型，null为主站页面，"wap"为wap站页面
	 */
	public boolean staticDoc(String docType, Schema doc, String publishType) {
		TemplateCache.clear();

		DataRow docDataRow = doc.toDataRow();
		String templateName = "";
		ZCCatalogSchema catalog = CatalogUtil.getSchema(docDataRow.getString("CatalogID"));
		if (catalog == null) {
			return false;
		}

		if (((!isCustomArticleTable) && (!isCustomTemplate)) && Config.getValue("InsureCompanyTopicCatalog") != null
				&& Config.getValue("InsureCompanyTopicCatalog").equals(String.valueOf(catalog.getID()))) {
			this.task.addError("要发布保险公司专题栏目的文章，请通过【站点管理->栏目与专题->文档库->基本属性】页面上“保险公司专题静态页面生成”后的【生成】按钮进行发布，谢谢！");
			this.task.setPercent(100);
			return false;
		}

		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		if (site == null) {
			return false;
		}
		
		if ("wap".equals(publishType)) {
			//单独发布文章时，若对应栏目未指定对应的wap站详细页模板，直接跳过发布wap站详细页的逻辑。
			if ("on".equals(catalog.getPublishWDT())) {
				templateName = catalog.getWapDetailTemplate();
			} else {
				return true;
			}
		} else {
			// zhangjinquan 11180 根据是否指定模板获取模板
			if (this.isCustomTemplate) {
				templateName = this.templateName;
			} else {
				templateName = catalog.getDetailTemplate();
			}
		}

		DataTable dt = new DataTable();
		dt.insertRow(docDataRow);

		// 标签页需求 congzn add
		CATALOGID = catalog.getID();

		ColumnUtil.extendDocColumnData(dt, catalog.getID());

		docDataRow = dt.get(0);

		String siteCode = site.getAlias();
		if ("1".equals(docDataRow.getString("TemplateFlag"))) {
			templateName = docDataRow.getString("Template");
		}

		templateName = templateDir + "/" + siteCode + templateName;
		templateName = templateName.replaceAll("/+", "/");

		String rootPath = "";
		if ("wap".equals(publishType)) {
			rootPath = staticDir + "/" + site.getWapAlias() + "/";
		} else {
			rootPath = staticDir + "/" + site.getAlias() + "/";
		}

		HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(), catalog.toDataRow(), docDataRow, catalog.getDetailNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		int level = h.getLevel();

		// 如果数据库url为空
		if (StringUtil.isEmpty(docDataRow.getString("URL"))) {
			QueryBuilder qb = new QueryBuilder("update zcarticle set URL=? where id=?");
			qb.add(h.getFullPath());
			qb.add(docDataRow.getString("ID"));
			qb.executeNoQuery();
			// 根据最新规则重新生成url
			docDataRow.set("URL", h.getFullPath());
		}

		// 上一页和下一页
		docDataRow.insertColumn("PrevLink", "#");
		docDataRow.insertColumn("PrevTitle", "没有文章");
		docDataRow.insertColumn("NextLink", "#");
		docDataRow.insertColumn("NextTitle", "没有文章");
		docDataRow.insertColumn("FirstImagePath", null);
		docDataRow.insertColumn("FirstVideoImage", null);
		docDataRow.insertColumn("FirstVideoHtml", null);

		PubFun.dealArticleMedia(docDataRow);

		docDataRow.insertColumn("BranchName", PubFun.getBranchName(docDataRow.getString("BranchInnerCode")));

		DataTable prevDT = new QueryBuilder("select * from zcarticle where catalogid=? and orderflag >? and status in (" + Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
				+ ") order by orderflag asc", docDataRow.getLong("CatalogID"), docDataRow.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (prevDT.getRowCount() == 1) {
			String prevUrl = "";
			if ("4".equals(prevDT.getString(0, "type"))) { // 转向链接
				prevUrl = prevDT.getString(0, "RedirectURL");
			} else {
				String url = prevDT.getString(0, "URL");
				if ("".equals(url.lastIndexOf("/"))) {
					prevUrl = prevDT.getString(0, "ID") + ".shtml";
				} else {
					prevUrl = url.substring(url.lastIndexOf("/") + 1, url.length());
				}
			}
			docDataRow.set("PrevLink", prevUrl);
			docDataRow.set("PrevTitle", prevDT.getString(0, "Title"));
		}
		DataTable nextDT = new QueryBuilder("select * from zcarticle where catalogid=? and orderflag <? and status in (" + Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
				+ ") order by orderflag desc", docDataRow.getLong("CatalogID"), docDataRow.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (nextDT.getRowCount() == 1) {
			String nextUrl = "";
			if ("4".equals(nextDT.getString(0, "type"))) { // 转向链接
				nextUrl = nextDT.getString(0, "RedirectURL");
			} else {
				String url = nextDT.getString(0, "URL");
				if ("".equals(url.lastIndexOf("/"))) {
					nextUrl = nextDT.getString(0, "ID") + ".shtml";
				} else {
					nextUrl = url.substring(url.lastIndexOf("/") + 1, url.length());
				}
			}
			docDataRow.set("NextLink", nextUrl);
			docDataRow.set("NextTitle", nextDT.getString(0, "Title"));
		}

		TemplateParser parser = getParser(site.getID(), templateName, level);
		if (parser == null) {
			task.addError("没有找到对应的模板文件" + catalog.getDetailTemplate());
			return false;
		}

		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setLevel(level);
		templateData.setSite(site);
		templateData.setCatalog(catalog);
		if ("article".equalsIgnoreCase(docType)) {

			templateData.setArticle(doc);
		}

		// 拷贝全局变量数据
		if (this.globalData.size() > 0) {
			templateData.addGlobalData(this.globalData);
		}

		if (this.isCustomArticleTable) {
			templateData.setCustomListTable(this.articleTable);
		}
		String genFileName = rootPath + docDataRow.getString("URL");
		File ff = new File(genFileName);
		// 判断其是目录，且为单页栏目
		if (ff.isDirectory() && "Y".equals(catalog.getSingleFlag())) {
			String ext = SiteUtil.getExtensionType(catalog.getSiteID());
			if (genFileName.endsWith("/"))
				genFileName += "index." + ext;
			else
				genFileName += "/index." + ext;

		}

		if (this.isCustomFileName) {
			genFileName = genFileName.substring(0, genFileName.lastIndexOf("/") + 1) + this.fileName + ((this.fileName.indexOf(".") != -1) ? "" : ".shtml");
		}

		return staticDoc(docType, parser, templateData, docDataRow, genFileName);
	}
	
	public String previewDoc(String docType, DataRow docDataRow) {
		return previewDoc(docType, docDataRow, 1);
	}

	public String previewDoc(String docType, DataRow docDataRow, int currentPage) {
		String templateName = "";
		ZCCatalogSchema catalog = CatalogUtil.getSchema(docDataRow.getString("CatalogID"));
		if (catalog == null) {
			return "";
		}

		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		if (site == null) {
			return "";
		}

		DataTable dt = new DataTable();
		dt.insertRow(docDataRow);
		ColumnUtil.extendDocColumnData(dt, catalog.getID());
		docDataRow = dt.get(0);

		templateName = catalog.getDetailTemplate();

		String siteCode = site.getAlias();
		if ("1".equals(docDataRow.getString("TemplateFlag"))) {
			templateName = docDataRow.getString("Template");
		}

		templateName = templateDir + "/" + siteCode + templateName;
		templateName = templateName.replaceAll("/+", "/");

		// 上一页和下一页
		docDataRow.insertColumn("PrevLink", "#");
		docDataRow.insertColumn("PrevTitle", "没有文章");
		docDataRow.insertColumn("NextLink", "#");
		docDataRow.insertColumn("NextTitle", "没有文章");
		docDataRow.insertColumn("FirstImagePath", null);
		docDataRow.insertColumn("FirstVideoImage", null);
		docDataRow.insertColumn("FirstVideoHtml", null);

		PubFun.dealArticleMedia(docDataRow);
		docDataRow.insertColumn("BranchName", PubFun.getBranchName(docDataRow.getString("BranchInnerCode")));

		DataTable prevDT = new QueryBuilder("select * from zcarticle where catalogid=? and orderflag >? and status in (" + Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
				+ ") order by orderflag asc", docDataRow.getLong("CatalogID"), docDataRow.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (prevDT.getRowCount() == 1) {
			docDataRow.set("PrevLink", prevDT.getString(0, "ID") + ".shtml");
			docDataRow.set("PrevTitle", prevDT.getString(0, "Title"));
		}
		DataTable nextDT = new QueryBuilder("select * from zcarticle where catalogid=? and orderflag <? and status in (" + Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
				+ ") order by orderflag desc", docDataRow.getLong("CatalogID"), docDataRow.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (nextDT.getRowCount() == 1) {
			docDataRow.set("NextLink", nextDT.getString(0, "ID") + ".shtml");
			docDataRow.set("NextTitle", nextDT.getString(0, "Title"));
		}

		String content = docDataRow.getString("Content");

		String siteurl = SiteUtil.getURL(docDataRow.getLong("SiteID"));
		if (StringUtil.isNotEmpty(siteurl) && !"http://".equalsIgnoreCase(siteurl)) {
			String cmsurl = (Config.getContextPath() + Config.getValue("Statical.TargetDir") + "/" + SiteUtil.getAlias(docDataRow.getLong("SiteID")) + "/").replaceAll("/+", "/");
			content = content.replaceAll(siteurl, cmsurl);
		}

		// 处理分页的内容
		String[] pages = content.split(Constant.PAGE_BREAK, -1);
		if (pages.length > 0 && currentPage <= pages.length) {
			content = pages[currentPage - 1];
		}

		docDataRow.set("Content", content);

		if (!new File(templateName).exists()) {
			return "详细页模板文件不存在：" + templateName;
		} else if (!new File(templateName).isFile()) {
			return "详细页模板不能为文件夹:" + templateName;
		}

		String templatecontent = FileUtil.readText(templateName);

		TagParser tagParser = new TagParser();
		tagParser.setSiteID(site.getID());
		tagParser.setTemplateFileName(templateName);
		tagParser.setPageBlock(false);
		tagParser.setPreview(true);
		tagParser.setContent(templatecontent);
		tagParser.setDirLevel(0);
		tagParser.parse();

		TemplateParser templateParser = new TemplateParser();
		templateParser.setFileName(templateName);
		templateParser.addClass("com.sinosoft.cms.pub.CatalogUtil");
		templateParser.addClass("com.sinosoft.cms.pub.SiteUtil");
		templateParser.addClass("com.sinosoft.cms.pub.PubFun");
		templateParser.setPageListPrams(tagParser.getPageListPrams());
		templateParser.setTemplate(tagParser.getContent());

		try {
			templateParser.parse();
		} catch (EvalException e) {
			return "模板解析错误：" + e.getMessage();
		}

		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setLevel(0);
		templateData.setSite(site);
		templateData.setCatalog(catalog);
		templateData.setDoc(docDataRow);
		templateData.setPageIndex(currentPage - 1);
		templateData.setPageSize(1);
		templateData.setPageCount(pages.length);
		templateData.setTotal(pages.length);
		templateData.setFirstFileName("PreviewDoc.jsp?ArticleID=" + docDataRow.getString("ID") + "&currentPage=1");
		templateData.setOtherFileName("PreviewDoc.jsp?ArticleID=" + docDataRow.getString("ID") + "&currentPage=@INDEX");

		templateParser.define("site", templateData.getSite());
		templateParser.define("catalog", templateData.getCatalog());
		templateParser.define("TemplateData", templateData);
		templateParser.define(docType.toLowerCase(), docDataRow);
		templateParser.define("system", ParserCache.getConfig(site.getID()));
		templateParser.define("page", templateData.getPageRow());

		String html = "";
		try {
			templateParser.generate();
			html = templateParser.getResult();
		} catch (TemplateException e) {
			logger.error(e.getMessage(), e);
			html = "解析失败，错误信息：" + e.getMessage();
		}

		return html;
	}

	private boolean staticDoc(String docType, TemplateParser parser, CmsTemplateData templateData, DataRow docDataRow, String fileName) {
		fileName = fileName.replace('\\', '/').replaceAll("/+", "/");
		String rootPath = fileName.substring(0, fileName.lastIndexOf("/") + 1);
		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

		// 更新文章url
		ZCCatalogSchema catalog = CatalogUtil.getSchema(docDataRow.getString("CatalogID"));
		if (catalog == null) {
			return false;
		}
		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		if (site == null) {
			return false;
		}

		/*
		 * HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(),
		 * catalog.toDataRow(), docDataRow, catalog.getDetailNameRule());
		 * HtmlNameRule h = nameParser.getNameRule(); QueryBuilder qb = new
		 * QueryBuilder("update zcarticle set URL=? where id=?");
		 * qb.add(h.getFullPath()); qb.add(docDataRow.getString("ID"));
		 * qb.executeNoQuery();
		 */

		// 单独设置的模板
		String detailTemplateName = "";
		TemplateParser currentDetailParser;
		if ("1".equals(docDataRow.getString("TemplateFlag"))) {
			detailTemplateName = docDataRow.getString("Template");
			detailTemplateName = templateDir + "/" + templateData.getSite().getString("Alias") + detailTemplateName;
			detailTemplateName = detailTemplateName.replaceAll("/+", "/");
			currentDetailParser = getParser(templateData.getSite().getLong("Alias"), detailTemplateName, templateData.getLevel());
			if (currentDetailParser == null) {
				currentDetailParser = parser;
			}
		} else {
			currentDetailParser = parser;
		}

		currentDetailParser.define("site", templateData.getSite());
		currentDetailParser.define("catalog", templateData.getCatalog());
		currentDetailParser.define(docType.toLowerCase(), docDataRow);
		File f = new File(rootPath);
		if (!f.exists()) {
			f.mkdirs();
		}

		// 文章分页
		if ("article".equalsIgnoreCase(docType)) {

			// zhangjinquan 11180 将替换图片、附件、关键词等单独抽取函数 2012-12-22 start
			String content = this.preDealDoc(docDataRow, templateData.getLevel(), templateData.getSearchURL());
			// zhangjinquan 11180 将替换图片、附件、关键词等当度抽取函数 2012-12-22 end

			String[] pages = content.split(Constant.PAGE_BREAK, -1);
			if (pages.length > 0) {
				// zhangjinquan 11180 增加详细页condition情况的分页处理 2012-12-24 start
				int index = fileName.lastIndexOf(".");
				String otherPageName = null;
				if (index != -1) {
					otherPageName = fileName.substring(0, index) + "_@INDEX" + fileName.substring(index);
				} else {
					otherPageName += "_@INDEX";
				}
				templateData.setFirstFileName(fileName);
				templateData.setOtherFileName(otherPageName);

				boolean[] dealResult = new boolean[] { false };
				boolean dealAsList = this.dealDetailPages(currentDetailParser, pages, dealResult, templateData, docDataRow, rootPath);
				if (dealAsList) {
					return dealResult[0];
				}
				// zhangjinquan 11180 增加详细页condition情况的分页处理 2012-12-24 end

				templateData.setPageCount(pages.length);
				templateData.setPageSize(1);
				templateData.setTotal(pages.length);

				for (int k = 0; k < pages.length; k++) {
					docDataRow.set("Content", pages[k]);
					templateData.setPageIndex(k);
					templateData.setDoc(docDataRow);

					currentDetailParser.define("TemplateData", templateData);
					currentDetailParser.define(docType.toLowerCase(), docDataRow);
					currentDetailParser.define("page", templateData.getPageRow());
					String pageName = fileName;
					if (k > 0) {
						pageName = otherPageName.replaceAll("@INDEX", String.valueOf(k + 1));
					}

					String statScript = "";
					// if
					// ("Y".equals(templateData.getSite().getString("AutoStatFlag")))
					// {
					// // 统计代码
					// String serviceUrl = Config.getValue("ServicesContext");
					// String statUrl = "SiteID=" +
					// docDataRow.getString("SiteID") + "&Type=" + docType
					// + "&CatalogInnerCode=" +
					// docDataRow.getString("CatalogInnerCode") + "&LeafID="
					// + docDataRow.getString("ID") + "&Dest=" + serviceUrl +
					// "/Stat.jsp";
					// statScript = getStatScript(statUrl);
					// }
					//
					// statScript += getVersionInfo();
					String extSite = SiteUtil.getExtensionType(site.getID());
					if ("jsp".equals(extSite)) {
						pageName = pageName.substring(0, pageName.lastIndexOf(".")) + ".jsp";
					}

					if (!writeHTML(currentDetailParser, rootPath + "/" + pageName, statScript)) {
						return false;
					}
				}
			}
		} else {
			templateData.setDoc(docDataRow);
			currentDetailParser.define("TemplateData", templateData);
			currentDetailParser.define(docType.toLowerCase(), docDataRow);
			String pageName = fileName;

			String statScript = "";
			// if ("Y".equals(templateData.getSite().getString("AutoStatFlag")))
			// {
			// // 统计代码
			// String serviceUrl = Config.getValue("ServicesContext");
			// String statUrl = "SiteID=" + docDataRow.getString("SiteID") +
			// "&Type=" + docType + "&CatalogInnerCode="
			// + docDataRow.getString("CatalogInnerCode") + "&LeafID=" +
			// docDataRow.getString("ID") + "&Dest="
			// + serviceUrl + "/Stat.jsp";
			// statScript = getStatScript(statUrl);
			// }
			//
			// statScript += getVersionInfo();
			long siteID = docDataRow.getLong("SiteID");
			String extSite = SiteUtil.getExtensionType(siteID);
			if ("jsp".equals(extSite)) {
				pageName = pageName.substring(0, pageName.lastIndexOf(".")) + ".jsp";
			}

			if (!writeHTML(currentDetailParser, rootPath + "/" + pageName, statScript)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @description 将替换图片、附件路径、关键词等单独抽取成函数
	 * @author zhangjinquan 11180
	 * @createdate 2012-12-22
	 * 
	 * @param docDataRow
	 * @param level
	 * @param searchUrl
	 * @return
	 */
	private String preDealDoc(DataRow docDataRow, int level, String searchUrl) {
		// 替换图片、附件路径
		String content = docDataRow.getString("Content");
		long siteID = docDataRow.getLong("SiteID");

		String imagePath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(siteID) + "/";
		imagePath = imagePath.replaceAll("/+", "/");

		String attachPath = Config.getContextPath() + "/Services/AttachDownLoad.jsp";
		attachPath = attachPath.replaceAll("/+", "/");

		String serviceContext = Config.getValue("ServicesContext");
		if (!serviceContext.endsWith("/")) {
			serviceContext = serviceContext + "/";
		}

		if (StringUtil.isNotEmpty(content)) {
			String siteurl = SiteUtil.getURL(siteID);
			if (StringUtil.isNotEmpty(siteurl) && !"http://".equalsIgnoreCase(siteurl)) {
				content = content.replaceAll(siteurl, StringUtil.md5Hex(siteurl));
			}
			String prefix = PubFun.getLevelStr(level);
			// 如果有分离部署
			if ("Y".equals(ConfigImageLib.getImageLibConfig(siteID).getString("ImageSeparateFlag"))) {
				prefix = ConfigImageLib.getImageLibConfig(siteID).getString("ImageSeparateURLPrefix");
			}
			content = content.replaceAll(imagePath, prefix);
			content = content.replaceAll(attachPath, serviceContext + "AttachDownLoad.jsp");

			if (StringUtil.isNotEmpty(siteurl) && !"http://".equalsIgnoreCase(siteurl)) {
				content = content.replaceAll(StringUtil.md5Hex(siteurl), siteurl);
			}
		}

		// 点击量
		if (StringUtil.isNotEmpty(docDataRow.getString("HitCount"))) {
			docDataRow.set("HitCount", getClickScript(docDataRow.getString("ID")));
		}

		// zhangjinquan 11180 关键词替换功能提取成单独的函数 2012-12-22 start
		content = this.keyWordReplace(content, docDataRow.getString("CatalogID"), siteID, searchUrl);
		// zhangjinquan 11180 关键词替换功能提取成单独的函数 2012-12-22 end

		docDataRow.insertColumn("FullContent", content);

		return content;
	}

	/**
	 * @author zhangjinquan 11180
	 * @description 关键词替换功能提取成单独的函数
	 * @createdate 2012-12-22
	 */
	@SuppressWarnings("unchecked")
	private String keyWordReplace(String content, String catalogId, long siteId, String searchUrl) {
		// 如果设置了关键词替换功能
		String keyWordType = CatalogUtil.getHotWordType(catalogId);

		if (StringUtil.isNotEmpty(keyWordType) && !"0".equalsIgnoreCase(keyWordType)) {
			Keyword.updateCache(keyWordType);
			ZCKeywordSet keywordLoopSet = (ZCKeywordSet) Keyword.getKeyWordSet(keyWordType);
			ZCKeywordSet keywordSet = new ZCKeywordSet();

			if (keywordLoopSet != null && keywordLoopSet.size() > 0) {
				Mapx keyWordCache = new Mapx();
				Mapx keyWordCacheAlt = new Mapx();

				// 优先级低于3的关键词不考虑调用
				for (int i = 0; i < keywordLoopSet.size(); i++) {
					if (keywordLoopSet.get(i).getPriorityLevel() < 3 || StringUtil.isEmpty(keywordLoopSet.get(i).getLinkUrl()) || "http://".equals(keywordLoopSet.get(i).getLinkUrl())
							|| !(keywordLoopSet.get(i).getKeywordType().indexOf("," + keyWordType + ",") > -1) || content.indexOf(keywordLoopSet.get(i).getKeyword()) == -1) {
						continue;
					} else {
						keywordSet.add(keywordLoopSet.get(i));
					}
				}

				// 每个URL在文章中只出现一次，优先长关键词
				HashMap mapUrl = new HashMap();
				// 将所有的 url存入hashmap
				for (int i = 0; i < keywordSet.size(); i++) {
					ZCKeywordSchema keyword = keywordSet.get(i);
					String word_value = keyword.getKeyword();
					String url_key = keyword.getLinkUrl();
					if (url_key != null && !url_key.equals("") && !"http://".equals(url_key)) {
						mapUrl.put(url_key, word_value);
					}

				}
				// 将hashmap中的value更新为最长的
				if (mapUrl != null) {
					Set entrySet = mapUrl.entrySet();
					Iterator itr = entrySet.iterator();
					while (itr.hasNext()) {
						Map.Entry entry = (Map.Entry) itr.next();
						Object key = entry.getKey();
						Object value = entry.getValue();
						for (int i = 0; i < keywordSet.size(); i++) {
							ZCKeywordSchema keyword = keywordSet.get(i);
							String word_value = keyword.getKeyword();
							String url_key = keyword.getLinkUrl();
							if (key.equals(url_key) && word_value.length() > value.toString().length()) {
								mapUrl.put(url_key, word_value);
							}
						}
					}
				}

				/*
				 * //找到mapUrl中关键词相同的项 Iterator<String> ite =
				 * mapUrl.values().iterator(); Map<String, Integer> countMap
				 * =new HashMap<String, Integer>(); while(ite.hasNext()){ String
				 * item = ite.next();
				 * 
				 * if(countMap.containsKey(item)){ countMap.put(item,
				 * Integer.valueOf(countMap.get(item).intValue() + 1)); }else{
				 * countMap.put(item, new Integer(1)); } } //重复的关键词 ArrayList
				 * repeatWord = new ArrayList();
				 * 
				 * for (Iterator iter = countMap.keySet().iterator();
				 * iter.hasNext();) { String key = (String) iter.next();
				 * if(countMap.get(key) > 1){ repeatWord.add(key); } }
				 */

				// 关键词去重
				HashMap mapWord = new HashMap();
				Set urlSet = mapUrl.entrySet();
				Iterator urlitr = urlSet.iterator();
				while (urlitr.hasNext()) {
					Map.Entry entry = (Map.Entry) urlitr.next();
					Object key = entry.getKey();
					Object value = entry.getValue();
					mapWord.put(value, key);
				}

				// 对关键词进行筛选，如果长词当中包含短词，则将短词过滤掉
				ArrayList al = new ArrayList();
				Set wordSet = mapWord.entrySet();
				Iterator worditr = wordSet.iterator();
				while (worditr.hasNext()) {
					Map.Entry entry = (Map.Entry) worditr.next();
					Object key = entry.getKey();
					al.add(key);
				}

				Collections.sort(al, new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						if (o1.length() > o2.length()) {
							return 1;
						} else if (o1.length() == o2.length()) {
							return 0;
						} else {
							return -1;
						}
					}
				});

				ArrayList keywordList = new ArrayList();
				for (int i = 0; i < al.size(); i++) {
					String word1 = (String) al.get(i);

					if (i == al.size() - 1) {
						if (!keywordList.contains(word1)) {
							keywordList.add(word1);
						}
						break;
					}

					for (int j = i + 1; j < al.size(); j++) {
						String word2 = (String) al.get(j);
						if (word2.indexOf(word1) > -1 && !word1.equals(word2)) {
							if (keywordList.contains(word1)) {
								keywordList.remove(word1);
							}
							if (!keywordList.contains(word2)) {
								keywordList.add(word2);
							}
						} else {
							if (!keywordList.contains(word2)) {
								keywordList.add(word2);
							}
						}
					}
				}

				// 对排序后的第一个词进行比对操作
				for (int i = 0; i < keywordList.size(); i++) {
					String firstWord = (String) al.get(0);
					String compareWord = (String) keywordList.get(i);
					if (compareWord.indexOf(firstWord) > -1) {
						break;
					}
					if (i == keywordList.size() - 1) {
						keywordList.add(firstWord);
					}
				}

				ArrayList keywordResultList = new ArrayList();
				if (keywordList.size() > 5) {
					// 随机抽取5个关键词,进行显示
					Random ran = new Random();
					Set<Integer> set = new HashSet<Integer>();
					while (set.size() == 5 ? false : true) {
						int num = ran.nextInt(keywordList.size());
						set.add(num);
					}
					Iterator<Integer> it = set.iterator();
					while (it.hasNext()) {
						keywordResultList.add(keywordList.get(it.next()));
					}
				} else {
					keywordResultList.addAll(keywordList);
				}

				HashMap resultMap = new HashMap();

				// 关键词优先调用优先级高的
				for (int i = 0; i < keywordResultList.size(); i++) {
					String keyword = (String) keywordResultList.get(i);
					String url = new QueryBuilder("select LinkURL from zckeyword where keywordType = ? and keyword = ? and priorityLevel >= 3 order by priorityLevel desc limit 0,1", "," + keyWordType
							+ ",", keyword).executeString();
					if (StringUtil.isNotEmpty(url)) {
						/*
						 * if(!mapWord.get(keyword).equals(url)){
						 * if(mapUrl.containsKey(url)){ String repeatKeyWord =
						 * (String)mapUrl.get(url);
						 * if(!repeatKeyWord.equals(keyword)){
						 * if(keyword.length()>repeatKeyWord.length()){
						 * mapWord.put(keyword, url);
						 * mapWord.remove(repeatKeyWord); }else{
						 * mapWord.remove(keyword); } }else{
						 * mapWord.put(keyword, url); } }else{
						 * mapWord.put(keyword, url); } }else{
						 * mapWord.put(keyword, url); }
						 */
						resultMap.put(keyword, url);
					}
				}

				// 针对寄存逻辑进行数据结构转换 mapUrl(url,word);
				HashMap convertMap = new HashMap();
				Set resultSet = resultMap.entrySet();
				Iterator resultitr = resultSet.iterator();
				while (resultitr.hasNext()) {
					Map.Entry entry = (Map.Entry) resultitr.next();
					Object key = entry.getKey();
					Object value = entry.getValue();
					convertMap.put(value, key);
				}

				// 每个URL在文章中只出现一次，优先长关键词
				if (convertMap != null) {
					String regex = "<\\s*p([\\s+([^>]*)]*)\\s*>[^(</p>)]*(<\\s*(a|A)\\s[^>]*>)?<\\s*(I|i)(M|m)(G|g)\\s[^>]*(a|A)(l|L)(t|T)\\s*=\\s*\"?([^\"]+)\"?[^>]*>[^(</a>)^(</p>)]*(</a>)?</p>";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(content);

					String findImgs = "";
					int j = keywordSet.size() + 1;
					String md5dataImgAlt = "";
					while (m.find()) {
						String findImg = m.group();
						md5dataImgAlt = StringUtil.md5Hex(keywordSet.size() + (j++) + "");
						keyWordCacheAlt.put(md5dataImgAlt, findImg);
						content = content.replace(findImg, md5dataImgAlt);
					}
					for (int i = 0; i < keywordSet.size(); i++) {
						ZCKeywordSchema keyword = keywordSet.get(i);
						String word = keyword.getKeyword();
						String url = keyword.getLinkUrl();

						boolean continueFlag = false;
						Set entrySet = convertMap.entrySet();
						Iterator itr = entrySet.iterator();
						while (itr.hasNext()) {
							Map.Entry entry = (Map.Entry) itr.next();
							Object key = entry.getKey();
							Object value = entry.getValue();
							if (key.equals(url) && value.equals(word)) {
								continueFlag = true;
								break;
							}
						}
						if (continueFlag) {
							String md5data = StringUtil.md5Hex(i + "");
							if (StringUtil.isEmpty(url)) {

								url = searchUrl + "?site=" + siteId + "&query=" + word;
							}
							String target = keyword.getLinkTarget();
							String alt = keyword.getLinkAlt();
							if (StringUtil.isEmpty(alt)) {
								alt = word;
							}
							String text = "<a href='" + url + "' target='" + target + "' title='" + alt + "' >" + word + "</a>";
							keyWordCache.put(md5data, text);
							// content = content.replaceAll(word, md5data);
							content = content.replaceFirst(word, md5data);
						}
					}
				}

				// DataTable dtCache = keyWordCache.toDataTable();
				// for (int i = 0; i < keywordSet.size(); i++) {
				// content = content.replaceAll(dtCache.getString(i, 0),
				// dtCache.getString(i, 1));
				// }
				DataTable dtCache = keyWordCache.toDataTable();
				for (int i = 0; i < dtCache.getRowCount(); i++) {
					content = content.replaceAll(dtCache.getString(i, 0), dtCache.getString(i, 1));
				}
				DataTable dtCacheImg = keyWordCacheAlt.toDataTable();
				for (int i = 0; i < dtCacheImg.getRowCount(); i++) {
					content = content.replaceAll(dtCacheImg.getString(i, 0), dtCacheImg.getString(i, 1));
				}

			}
		}
		// 解决超链接靠近显示的问题
		content = content.replaceAll("</a><a href=", "</a>&nbsp;<a href=");
		return content;
	}

	private static final Pattern pField = Pattern.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");

	@SuppressWarnings("unchecked")
	private boolean dealDetailPages(TemplateParser parser, String[] pages, boolean[] dealResult, CmsTemplateData templateData, DataRow docDataRow, String rootPath) {
		try {
			Mapx map = parser.getPageListPrams();
			int pageCount = pages.length;
			String condition = map.getString("condition");
			String tag = map.getString("tag");
			int pageSize = 0;
			try {
				pageSize = Integer.parseInt(map.getString("pagesize"));
			} catch (Exception e) {
			}
			;
			boolean dataAsList = (pageSize > 0) && (1 == pageCount) && (null != map) && "true".equalsIgnoreCase(map.getString("detailaslist")) && StringUtil.isNotEmpty(condition);
			if (!dataAsList) {
				return false;
			}
			String content = pages[0];
			docDataRow.set("Content", content);
			DataRow article = docDataRow;
			DataRow site = templateData.getSite();
			DataRow catalog = templateData.getCatalog();
			templateData.setDoc(article);
			templateData.setPageSize(pageSize);
			templateData.setDetailAsList(map.getString("listID"), true);

			// 检查是否包含变量表达式，如果是则进行值替换
			boolean tag_flag = false;
			if (StringUtil.isNotEmpty(tag)) {
				tag_flag = true;
			}
			if (tag_flag) {
				Matcher m = pField.matcher(tag);
				StringBuffer sb = new StringBuffer();
				int lastEndIndex = 0;
				while (m.find(lastEndIndex)) {
					sb.append(tag.substring(lastEndIndex, m.start()));
					String type = m.group(1).toLowerCase();
					String columnName = m.group(2).toLowerCase();

					if ("article".equals(type)) {
						sb.append(article.getString(columnName));
					} else if ("site".equals(type)) {
						sb.append(site.getString(columnName));
					} else if ("catalog".equals(type)) {
						sb.append(catalog.getString(columnName));
					}

					lastEndIndex = m.end();
				}
				sb.append(tag.substring(lastEndIndex));

				condition = sb.toString();
			}

			if ("true".equals(map.getString("customtableflag"))) {
				String listId = map.getString("listID");
				templateData.addGlobalData("nowListId", "nowListId", listId);
				templateData.addGlobalData(listId, listId, "true");
			}

			// 获取分页列表的所有数据
			DataTable dataList = templateData.getPagedDocList(map.getString("item"), map.getString("name"), map.getString("level"), map.getString("hasattribute"), map.getString("type"), condition,
					tag, 0, 0);
			int total = 0;

			// 没有数据的情况按以前的处理
			if ((null == dataList) || (0 == (total = dataList.getRowCount()))) {
				return false;
			}
			pageCount = total / pageSize + ((total % pageSize == 0) ? 0 : 1);
			templateData.setTotal(total);
			templateData.setPageCount(pageCount);

			parser.define("TemplateData", templateData);
			parser.define("article", docDataRow);
			String fileName = templateData.getFirstFileName();
			String otherPageName = templateData.getOtherFileName();

			for (int k = 0; k < pageCount; k++) {
				templateData.setPageIndex(k);
				templateData.setDoc(docDataRow);
				DataTable pageDt = new DataTable();
				int count = (k + 1) * pageSize;
				count = count > total ? total : count;

				// 获取分页数据
				for (int i = k * pageSize; i < count; i++) {
					pageDt.insertRow(dataList.getDataRow(i));
				}

				templateData.setListTable(pageDt);

				String pageName = fileName;
				if (k > 0) {
					pageName = otherPageName.replaceAll("@INDEX", String.valueOf(k + 1));
				}

				String extSite = SiteUtil.getExtensionType(site.getString("ID"));
				if ("jsp".equals(extSite)) {
					pageName = pageName.substring(0, pageName.lastIndexOf(".")) + ".jsp";
				}

				if (!writeHTML(parser, rootPath + "/" + pageName, "")) {
					dealResult[0] = false;
					return true;
				}
			}

			dealResult[0] = true;
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public boolean staticPageBlock(ZCPageBlockSet set) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(set.get(0).getSiteID());
		site.fill();

		Mapx catalogMap = new Mapx();

		for (int i = 0; i < set.size(); i++) {
			ZCCatalogSchema catalog = (ZCCatalogSchema) catalogMap.get(set.get(i).getCatalogID() + "");
			if (catalog == null) {
				catalog = new ZCCatalogSchema();
				catalog.setID(set.get(i).getCatalogID());
				if (!catalog.fill()) {
					catalog = null;
				}

				catalogMap.put(set.get(i).getCatalogID() + "", catalog);
			}

			if (!staticOnePageBlock(site, catalog, set.get(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean staticPageBlock(ZCSiteSchema site, ZCCatalogSchema catalog, int type) {
		if (site == null) {
			return false;
		}
		QueryBuilder qb = null;
		String wherePart = (type == 4) ? "where type=4" : "where type<>4";
		if (catalog != null) {
			wherePart += " and catalogid=" + catalog.getID();
		} else {
			wherePart += " and catalogid is null and siteid=" + site.getID();
		}
		qb = new QueryBuilder(wherePart);
		ZCPageBlockSet set = new ZCPageBlockSchema().query(qb);
		if (set.size() > 0) {
			for (int i = 0; i < set.size(); i++) {
				ZCPageBlockSchema block = set.get(i);
				if (!staticOnePageBlock(site, catalog, block) && type != 4) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 生成栏目对应的系统区块文件。根据栏目模板找到在其他栏目中的对应区块。
	 * 
	 * @param site
	 * @param catalog
	 * @return
	 */
	private boolean staticInnerPageBlock(ZCSiteSchema site, ZCCatalogSchema catalog) {
		if (site == null) {
			return false;
		}
		String alias = site.getAlias();
		// 栏目首页模板
		String indexTemplate = catalog.getIndexTemplate();
		if (StringUtil.isNotEmpty(indexTemplate)) {
			indexTemplate = "/" + alias + indexTemplate + "_" + catalog.getTreeLevel();
			staticInnerPageBlock(site, catalog, indexTemplate);
		}
		// 栏目列表模板
		String listTemplate = catalog.getListTemplate();
		if (StringUtil.isNotEmpty(listTemplate)) {
			listTemplate = "/" + alias + listTemplate + "_" + catalog.getTreeLevel();
			staticInnerPageBlock(site, catalog, listTemplate);
		}

		// 栏目详细页模板
		String detailTemplate = catalog.getDetailTemplate();
		if (StringUtil.isNotEmpty(detailTemplate)) {
			detailTemplate = "/" + alias + detailTemplate + "_" + CatalogUtil.getDetailLevel(catalog.getID());
			staticInnerPageBlock(site, catalog, detailTemplate);
		}

		return true;
	}

	private boolean staticInnerPageBlock(ZCSiteSchema site, ZCCatalogSchema catalog, String templateFile) {
		QueryBuilder qb = new QueryBuilder("where exists (select blockid from  ZCTemplateBlockRela where filename=?" + " and blockid=zcpageblock.id)", templateFile);
		ZCPageBlockSet set = new ZCPageBlockSchema().query(qb);
		if (set.size() > 0) {
			for (int i = 0; i < set.size(); i++) {
				ZCPageBlockSchema block = set.get(i);
				String targetFile = templateDir + "/" + site.getAlias() + "/" + block.getFileName();
				File f = new File(targetFile);
				if (!f.exists()) {
					if (!staticOnePageBlock(site, catalog, block)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 静态化指定的区块
	 * 
	 * @param block
	 * @param site
	 * @return
	 */
	public boolean staticOnePageBlock(ZCSiteSchema site, ZCCatalogSchema catalog, ZCPageBlockSchema block) {
		TemplateCache.clear();
		String templateName = block.getTemplate();

		String filename = block.getFileName().replace('\\', '/').replaceAll("/+", "/");
		if (filename.startsWith("/")) {
			filename = filename.substring(1);
		}
		int level = StringUtil.count(filename, "/");
		if (block.getType() == 4) {
			String code = block.getCode();
			if (code.lastIndexOf("_") != -1) {
				level = Integer.parseInt(code.substring(code.lastIndexOf("_") + 1));
			}
		}

		if (block.getType() != 3) {
			templateName = templateDir + "/" + site.getAlias() + "/" + block.getTemplate();
			templateName = templateName.replace('\\', '/').replaceAll("/+", "/");
		}

		String sitePath = SiteUtil.getAbsolutePath(block.getSiteID());
		if (block.getType() == 3) {
			File f = new File(sitePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			long t = System.currentTimeMillis();
			String fullPath = sitePath + filename;
			boolean successFlag = FileUtil.writeText(fullPath, block.getContent());
			Object[] argArr = {fullPath, successFlag, (System.currentTimeMillis() - t)};
			logger.info("生成{}({})页面耗时：{}", argArr);

			// 生成其他级别页面的调用页面
			String includeFile = staticDir + "/" + site.getAlias() + "/" + block.getFileName();
			includeFile = includeFile.replace('\\', '/').replaceAll("/+", "/");
			generateIncludeFiles(includeFile);
		} else {
			// 如果区块中存在引用多个栏目数据，将本区块复制到其他栏目中
			if (block.getType() != 4) {
				PreParser p = new PreParser();
				p.setSiteID(block.getSiteID());
				p.setTemplateFileName(templateName);
				ArrayList idList = p.parseList();
				ZCPageBlockSet blockSet = new ZCPageBlockSet();
				for (int i = 0; i < idList.size(); i++) {
					String id = (String) idList.get(i);
					if (catalog != null && (catalog.getID() + "").equals(id)) {
						continue;
					}

					// 判断是否已经添加
					QueryBuilder qb = new QueryBuilder("select count(*) from zcpageblock where catalogid=? and template=? and filename=?");
					qb.add(Long.parseLong(id));
					qb.add(block.getTemplate());
					qb.add(block.getFileName());
					int count = qb.executeInt();
					if (count > 0) {
						continue;
					}

					ZCPageBlockSchema blockCopy = (ZCPageBlockSchema) block.clone();
					long blockID = NoUtil.getMaxID("PageBlockID");
					blockCopy.setID(blockID);
					blockCopy.setCatalogID(id);
					blockCopy.setAddTime(new Date());
					blockCopy.setAddUser("admin");

					blockSet.add(blockCopy);
				}

				if (blockSet.size() > 0) {
					if (!blockSet.insert()) {
						return false;
					}
				}
			}

			TemplateParser parser = getParser(site.getID(), templateName, level, true);

			if (parser == null) {
				if (block.getType() == 4) {
					return true;
				}
				task.addError("没有找到附带发布" + block.getName() + "对应的模板文件" + templateName);
				Errorx.addError("没有找到附带发布" + block.getName() + "对应的模板文件" + templateName);
				return false;
			}

			CmsTemplateData templateData = new CmsTemplateData();
			templateData.setLevel(level);
			templateData.setSite(site);
			templateData.setBlock(block.toDataRow());

			if (catalog != null) {
				templateData.setCatalog(catalog);
				parser.define("catalog", templateData.getCatalog());
			}
			parser.define("site", templateData.getSite());
			parser.define("TemplateData", templateData);
			parser.define("article", new ZCArticleSchema());

			// 分页生成
			if (parser.getPageListPrams().size() > 0) {
				staticListPages(site, catalog, sitePath + filename, parser, templateData, false, false, false, -1);

			} else {
				task.setCurrentInfo("区块文件：" + filename);

				if (!writeHTML(parser, sitePath + "/" + filename, "")) {
					if (block.getType() == 4) {
						return true;
					}
					task.addError("生成区块发生错误。生成文件：" + filename);
					return false;
				}

				// 生成其他级别页面的调用页面
				String includeFile = staticDir + "/" + site.getAlias() + "/" + block.getFileName();
				includeFile = includeFile.replace('\\', '/').replaceAll("/+", "/");
				generateIncludeFiles(includeFile);
			}
		}

		return true;
	}

	private TemplateParser getParser(long siteID, String template, int level) {
		return getParser(siteID, template, level, false);
	}

	private TemplateParser getParser(long siteID, String template, int level, boolean isPageBlock) {
		if (template == null || "".equals(template)) {
			return null;
		}
		TemplateParser parser = ParserCache.get(siteID, template, level, isPageBlock, fileList);
		return parser;
	}

	/**
	 * 更新包含文件，根据文件被包含的级别生成相应的包含文件
	 * 
	 * @param includeFile
	 */
	private void generateIncludeFiles(String includeFile) {
		// 更新包含文件
		String fileName = includeFile.substring(includeFile.lastIndexOf("/") + 1, includeFile.lastIndexOf("."));
		String includeDir = includeFile.substring(0, includeFile.lastIndexOf("/"));
		File files = new File(includeDir);
		Collection c = new ArrayList();
		if (files.exists()) {
			File[] fileList = files.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				File tmpFile = fileList[i];
				String tmpFileName = tmpFile.getName();
				if (tmpFileName.startsWith(fileName + "_")) {
					if (tmpFileName.lastIndexOf("_") != -1) {
						String levelStr = "";
						if (tmpFileName.lastIndexOf(".") != -1) {
							levelStr = tmpFileName.substring(tmpFileName.lastIndexOf("_") + 1, tmpFileName.lastIndexOf("."));
						} else {
							levelStr = tmpFileName.substring(tmpFileName.lastIndexOf("_") + 1);
						}

						if (!StringUtil.isDigit(levelStr)) {
							continue;
						}

						int level = Integer.parseInt(levelStr);
						String levelString = "";
						for (int j = 0; j < level; j++) {
							levelString += "../";
						}
						long t = System.currentTimeMillis();
						TagParser parser = new TagParser();
						parser.setPageBlock(false);
						String includeContent = parser.dealResource(FileUtil.readText(includeFile), levelString);
						boolean successFlag = FileUtil.writeText(tmpFile.getAbsolutePath(), includeContent);
						Object[] argArr = {tmpFile.getAbsolutePath(), successFlag, (System.currentTimeMillis() - t)};
						logger.info("生成{}({})页面耗时：{}", argArr);
						c.add(tmpFile.getAbsolutePath());
					}
				}
			}
		}

		fileList.addAll(c);
	}

	public boolean writeHTML(TemplateParser tp, String fileName, String statScript) {
		long t = System.currentTimeMillis();
		try {
			tp.generate();
		} catch (Exception e) {
			logger.error("模板解析错误，请检查模板{}是否正确。错误信息：{}", tp.getFileName(), e.getMessage());
			String errorMessage = "模板解析错误，请检查模板" + tp.getFileName() + "是否正确。错误信息：" + e.getMessage();

			Errorx.addError(errorMessage);
			task.addError(errorMessage);
			return false;
		}
		logger.info("解析页面耗时：{}", (System.currentTimeMillis() - t));

		String html = tp.getResult();

		/* 张进铨 11180 2012-09-05 start */
		if (!"".equals(statScript)) {
			/* 将统计代码添加到body内部，防止杀毒软件报错 */
			String[] htmlBody = html.split("</body>");
			if (2 == htmlBody.length) {
				html = htmlBody[0] + statScript + "</body>" + htmlBody[1];// 添加统计代码
			} else {
				html += statScript;
			}
		}
		/* 张进铨 11180 2012-09-05 end */

		fileName = fileName.replaceAll("/+", "/");

		String filePath = fileName.substring(0, fileName.lastIndexOf('/'));
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		// long siteid=Application.getCurrentSiteID();
		// if(siteid!=0){
		// String extSite = SiteUtil.getExtensionType(siteid);
		// if(!fileName.endsWith(extSite)){
		// fileName=fileName.substring(0,fileName.lastIndexOf(".")+1)+extSite;
		// }
		// }
		// System.out.println(fileName+"=========");
		try {
			boolean successFlag = FileUtil.writeText(fileName, html);
			Object[] argArr = {fileName, successFlag, (System.currentTimeMillis() - t)};
			logger.info("生成{}({})页面耗时：{}", argArr);
			fileList.add(fileName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// convertBig5(html, fileName);

		return true;
	}

	/**
	 * 将html转换为繁体中文
	 * 
	 * @param html
	 * @param fileName
	 */
	private void convertBig5(String html, String fileName) {
		if ("Y".equals(Config.getValue("Big5ConvertFlag"))) {
			String big5FileName = fileName;
			if (fileName.indexOf("cn/") != -1) {
				big5FileName = fileName.replaceAll("cn", "big5");
			} else if (fileName.indexOf("cache") != -1 || fileName.indexOf("include") != -1) {
				big5FileName = big5FileName.replaceAll(staticDir, staticDir + "/big5/");

				big5FileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_big5" + fileName.substring(fileName.lastIndexOf('.'));
			} else if (fileName.indexOf("index.shtml") != -1) {
				big5FileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_big5" + fileName.substring(fileName.lastIndexOf('.'));
			} else {
				return;
			}
			String big5Dir = big5FileName.substring(0, big5FileName.lastIndexOf("/"));
			File big5File = new File(big5Dir);
			if (!big5File.exists()) {
				big5File.mkdirs();
			}

			String big5Html = Big5Convert.toTradition(html);
			big5Html = big5Html.replaceAll("cn/", "big5/");

			Pattern includePattern = Pattern.compile("\\s(file|virtual)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = includePattern.matcher(big5Html);
			StringBuffer sb = new StringBuffer();
			int lastEndIndex = 0;
			while (m.find(lastEndIndex)) {
				sb.append(big5Html.substring(lastEndIndex, m.start()));
				lastEndIndex = m.end();
				String includeFile = m.group(3);
				includeFile = includeFile.substring(0, includeFile.lastIndexOf('.')) + "_big5" + includeFile.substring(includeFile.lastIndexOf('.'));
				sb.append(" virtual=\"" + includeFile + "\"");
			}
			sb.append(big5Html.substring(lastEndIndex));
			big5Html = sb.toString();

			FileUtil.writeText(big5FileName, big5Html);
			fileList.add(big5FileName);
		}
	}

	public boolean writeListHTML(TemplateParser tp, CmsTemplateData templateData, String firstFileName, int pageIndex, String statScript) {
		firstFileName = firstFileName.replace('\\', '/').replaceAll("/+", "/");
		String fileDir = firstFileName.substring(0, firstFileName.lastIndexOf("/") + 1);
		firstFileName = firstFileName.substring(firstFileName.lastIndexOf("/") + 1);
		String otherFileName;
		int index = firstFileName.lastIndexOf(".");
		if (index != -1) {
			otherFileName = firstFileName.substring(0, index) + "_@INDEX" + firstFileName.substring(index);
		} else {
			otherFileName = firstFileName + "_@INDEX";
		}
		templateData.setFirstFileName(firstFileName);
		templateData.setOtherFileName(otherFileName);

		// 定义模板数据
		tp.define("site", templateData.getSite());
		tp.define("catalog", templateData.getCatalog());
		tp.define("TemplateData", templateData);
		tp.define("page", templateData.getPageRow());

		long t = System.currentTimeMillis();
		try {
			tp.generate();
		} catch (Exception e) {
			logger.error("模板{}解析错误，请检查模板变量是否正确", tp.getFileName());
			task.addError("模板" + tp.getFileName() + "解析错误，请检查模板变量是否正确");
			return false;
		}
		logger.info("解析页面：{}", (System.currentTimeMillis() - t));

		String html = tp.getResult();
		/* 张进铨 11180 2012-09-05 start */
		if (!"".equals(statScript)) {
			/* 将统计代码添加到body内部，防止杀毒软件报错 */
			String[] htmlBody = html.split("</body>");
			if (2 == htmlBody.length) {
				html = htmlBody[0] + statScript + "</body>" + htmlBody[1];// 添加统计代码
			} else {
				html += statScript;
			}
		}
		/* 张进铨 11180 2012-09-05 end */
		String fileName = null;
		if (pageIndex == 0) {
			fileName = fileDir + firstFileName;
		} else {
			fileName = fileDir + otherFileName.replaceAll("@INDEX", String.valueOf(pageIndex + 1));
		}

		String FilePath = fileName.substring(0, fileName.lastIndexOf('/'));
		File f = new File(FilePath);
		if (!f.exists()) {
			f.mkdirs();
		}

		html += getVersionInfo();

		boolean successFlag = FileUtil.writeText(fileName, html);
		Object[] argArr = {fileName, successFlag, (System.currentTimeMillis() - t)};
		logger.info("{}({})耗时：{}", argArr);
		fileList.add(fileName);

		convertBig5(html, fileName);

		return true;
	}

	public String getStatScript(String statUrl) {
		String serviceUrl = Config.getValue("ServicesContext");
		String statScript = "\n<script src=\"" + serviceUrl + "/Stat.js\" type=\"text/javascript\" ></script>\n";
		statScript += "<script defer=\"defer\" type=\"text/javascript\" >\n";
		statScript += "if(window._zcms_stat)_zcms_stat(\"" + statUrl + "\");\n";
		statScript += "</script>\n";
		return statScript;
	}

	public String getClickScript(String articleID) {
		String serviceUrl = Config.getValue("ServicesContext");
		String clickScript = "\n<script src=\"" + serviceUrl + "/Counter.jsp?Type=Article&ID=" + articleID + "\" type=\"text/javascript\"></script>\n";
		return clickScript;
	}

	public String getVersionInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n<!-- Powered by ");
		sb.append(product.getAppCode());
		sb.append("(" + product.getAppName() + ") ");
		sb.append(product.getMainVersion());
		sb.append(".");
		sb.append(product.getMinorVersion());
		sb.append(" PublishDate ");
		sb.append(DateUtil.getCurrentDateTime());
		sb.append("-->");
		return sb.toString();
	}

	public ArrayList getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList fileList) {
		this.fileList = fileList;
	}

	/**
	 * zhangjinquan 11180 是否指定模板
	 */
	private boolean isCustomTemplate = false;
	/**
	 * @author zhangjinquan 11180
	 * @description 指定模板的名称
	 */
	private String templateName = null;
	/**
	 * zhangjinquan 11180 是否指定生成文件名称
	 */
	private boolean isCustomFileName = false;
	/**
	 * @author zhangjinquan 11180
	 * @description 指定生成文件的名称
	 */
	private String fileName = null;
	/**
	 * 指定的文章数据表
	 */
	private DataTable articleTable = null;
	/**
	 * zhangjinquan 11180 是否指定文章数据表
	 */
	private boolean isCustomArticleTable = false;
	/**
	 * zhangjinquan 11180 指定全局数据
	 * 
	 * @createdate 2012-12-24
	 */
	private HashMap<String, HashMap<String, String>> globalData = new HashMap<String, HashMap<String, String>>(2);

	/**
	 * @author zhangjinquan 11180
	 * @description 设置指定模板
	 * @param templateName
	 */
	public void setDetailTemplate(String templateName) {
		if (StringUtil.isEmpty(templateName)) {
			return;
		}
		this.templateName = templateName;
		this.isCustomTemplate = true;
	}

	/**
	 * @author zhangjinquan 11180
	 * @description 设置指定生成文件名
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		if (StringUtil.isEmpty(fileName)) {
			return;
		}
		this.fileName = fileName;
		this.isCustomFileName = true;
	}

	/**
	 * @author zhangjinquan 11180
	 * @description 设置指定文章数据表
	 * @param articleTable
	 */
	public void setCustomTable(DataTable articleTable) {
		if (null == articleTable) {
			return;
		}
		this.articleTable = articleTable;
		this.isCustomArticleTable = true;
	}

	/**
	 * @author zhangjinquan 11180
	 * @description 设置配置数据
	 */
	public void setCustomData(String templateName, String fileName, DataTable articleTable) {
		if (StringUtil.isEmpty(templateName) || StringUtil.isEmpty(fileName) || (null == articleTable)) {
			return;
		}
		this.templateName = templateName;
		this.isCustomTemplate = true;
		this.fileName = fileName;
		this.isCustomFileName = true;
		this.articleTable = articleTable;
		this.isCustomArticleTable = true;

	}

	/**
	 * @author zhangjinquan 11180
	 * @description 清理配置项
	 */
	public void clearCustom() {
		this.templateName = null;
		this.isCustomTemplate = false;
		this.fileName = null;
		this.isCustomFileName = false;
		this.articleTable = null;
		this.isCustomArticleTable = false;
		this.globalData.clear();
	}

	/**
	 * @author zhangjinquan 11180
	 * @description 增加模板全局map数据元素增加方法
	 * @createdate 2012-12-19
	 * @param id
	 * @param key
	 * @param value
	 */
	public void addCustomGlobalData(String id, String key, String value) {
		if ((null == id) || (null == key) || (null == value)) {
			return;
		}

		id = id.toLowerCase();
		key = key.toLowerCase();

		if (this.globalData.containsKey(id)) {
			this.globalData.get(id).put(key, value);
		} else {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(key, value);
			this.globalData.put(id, map);
		}
	}

	public void addCustomGlobalData(String id, String key, int value) {
		this.addCustomGlobalData(id, key, String.valueOf(value));
	}

	/**
	 * @author zhangjinquan 11180
	 * @description 增加模板全局map数据元素数据获取方法
	 * @createdate 2012-12-19
	 * @param id
	 * @param key
	 */
	public String getCustomGlobalData(String id, String key) {
		if ((null == id) || (null == key)) {
			return "";
		}

		id = id.toLowerCase();
		key = key.toLowerCase();

		if (this.globalData.containsKey(id)) {
			String returnVal = this.globalData.get(id).get(key);
			return ((null == returnVal) ? "" : returnVal);
		}
		return "";
	}
}
