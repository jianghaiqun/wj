package com.sinosoft.cms.template;

import com.sinosoft.cms.dataservice.Advertise;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.dataservice.CustomTableUtil;
import com.sinosoft.cms.dataservice.Form;
import com.sinosoft.cms.dataservice.Vote;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.cms.site.ImagePlayerBasic;
import com.sinosoft.cms.site.ImagePlayerRela;
import com.sinosoft.cms.stat.report.SourceReport;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.controls.HtmlP;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DBUtil;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCCustomTableSchema;
import com.sinosoft.schema.ZCCustomTableSet;
import com.sinosoft.schema.ZCImagePlayerSchema;
import com.sinosoft.schema.ZCImagePlayerSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCVoteSchema;
import com.sinosoft.schema.ZCVoteSet;
import com.sinosoft.statical.TemplateData;
import com.sinosoft.utility.ArticleCache;
import org.drools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文档数据，为静态化页面提供当前页面的数据信息
 * 
 */
public class CmsTemplateData extends TemplateData {
	private static final Logger logger = LoggerFactory.getLogger(CmsTemplateData.class);

	private static final String SEPARATE = "|";

	private DataRow block;

	private DataRow doc;

	private DataRow catalog;

	private DataRow article;

	private DataRow site;

	private int level; // 目录层级 0站点根目录

	private String levelStr;

	ArticleCache mArticleCache = new ArticleCache();

	public CmsTemplateData() {
	}

	public void setSite(ZCSiteSchema site) {
		this.site = site.toDataRow();
	}

	public void setCatalog(ZCCatalogSchema catalog) {
		this.setCatalog(catalog.toDataRow());
	}

	public void setDoc(Schema schema) {
		this.doc = schema.toDataRow();
	}

	public void setArticle(Schema doc2) {
		this.article = doc2.toDataRow();
	}

	public DataRow getDoc() {
		return doc;
	}

	public void setDoc(DataRow doc) {
		this.doc = doc;
	}

	public DataRow getCatalog() {
		return catalog;
	}

	public DataRow getArticle() {
		return article;
	}

	public void setCatalog(DataRow catalog) {
		this.catalog = catalog;
		ColumnUtil.extendCatalogColumnData(this.catalog, site.getLong("ID"), this.getLevelStr());
	}

	public DataRow getSite() {
		return site;
	}

	public void setSite(DataRow site) {
		this.site = site;
	}

	public DataTable getList(String item, String name, String displayLevel, String count) {
		return getCatalogList(item, "article", name, displayLevel, count, null, "");
	}

	public DataTable getList(String item, String name, String displayLevel, String count, String condition) {
		return getCatalogList(item, "article", name, displayLevel, count, condition, "");
	}

	/**
	 * <cms:list item="" item catalogType name 制定栏目的栏目名 displayLevel 显示层级 level
	 * root child count 显示数量 condition
	 */
	public DataTable getCatalogList(String item, String catalogType, String name, String displayLevel, String count, String condition, String cmsorder) {
		String regex = "([0-9\u4e00-\u9fa5]+)";
		Matcher matcher = Pattern.compile(regex).matcher(name);
		DataTable dt = null;
		if (matcher.find()) {
			String name1 = matcher.group(0);
			dt = getCatalogList1(item, catalogType, name1, displayLevel, count, condition);
		} else {
			dt = getCatalogList1(item, catalogType, name, displayLevel, count, condition);
		}
		return dt;
	}

	public DataTable getCatalogList1(String item, String catalogType, String name, String displayLevel, String count, String condition) {
		DataTable dt = null;

		// 获取缓存数据
		String key = item + "_" + catalogType + "_" + displayLevel + "_" + name + "_" + count + "_" + condition + "_" + level;
		dt = TemplateCache.getDataTable(key);
		if (dt != null) {
			return dt;
		}

		if (count == null || "".equals(count) || "null".equals(count)) {
			count = "20";
		}
		int pageSize = Integer.parseInt(count);
		if ("catalog".equalsIgnoreCase(item)) {
			String typeSql = null;
			if ("article".equals(catalogType)) {
				typeSql = " and type =" + Catalog.CATALOG;
			} else if ("image".equals(catalogType)) {
				typeSql = " and type =" + Catalog.IMAGELIB;
			} else if ("video".equals(catalogType)) {
				typeSql = " and type =" + Catalog.VIDEOLIB;
			} else if ("audio".equals(catalogType)) {
				typeSql = " and type =" + Catalog.AUDIOLIB;
			} else if ("attachment".equals(catalogType)) {
				typeSql = " and type =" + Catalog.ATTACHMENTLIB;
			} else if ("magazine".equalsIgnoreCase(catalogType)) {
				typeSql = " and type =" + Catalog.MAGAZINE;
			} else if ("goods".equals(catalogType)) {
				typeSql = " and type =" + Catalog.SHOP_GOODS;
			} else if ("brand".equals(catalogType)) {
				typeSql = " and type =" + Catalog.SHOP_GOODS_BRAND;
			} else {
				typeSql = " and type =" + Catalog.CATALOG;
			}

			String parentID = null;

			// 指定的栏目的子栏目
			if (StringUtil.isNotEmpty(name) && !"null".equalsIgnoreCase(name)) {
				if (site != null) {
					if (name.indexOf("/") != -1) {
						parentID = CatalogUtil.getIDByNames(site.getString("ID"), name);
					} else {
						parentID = CatalogUtil.getIDByName(site.getString("ID"), name);
					}

					if (StringUtil.isEmpty(parentID) && StringUtil.isDigit(name)) {
						parentID = name;
					}
				}
			}

			// 确定栏目的显示层级
			String parentSql = "";
			if ("root".equalsIgnoreCase(displayLevel)) { // 站点的子栏目
				if (StringUtil.isEmpty(parentID)) {// 只有栏目ID不为空的时候有效
					parentID = "0";
				}
				parentSql = " and parentid=" + parentID;
			} else if ("current".equalsIgnoreCase(displayLevel)) { // 当前栏目的同级栏目
				if (StringUtil.isEmpty(parentID)) {
					if (catalog != null) {
						parentID = catalog.getString("ParentID");
					}
				}
				parentSql = " and parentid=" + parentID;
			} else if ("child".equalsIgnoreCase(displayLevel)) { // 默认为当前栏目的子栏目
				if (StringUtil.isEmpty(parentID)) {
					if (catalog != null) {
						// 如果是子栏目
						if ("1".equals(catalog.getString("isLeaf"))) {
							// 此处逻辑有问题，待新的标签机制来解决,wyuch 2010-03-11
							if ("magazine".equalsIgnoreCase(catalogType)) {
								parentID = catalog.getString("ID");
							} else {
								parentID = catalog.getString("ParentID");
							}
						} else {
							parentID = catalog.getString("ID");
						}
					}
				}
				parentSql = " and parentid=" + parentID;
			} else if ("all".equalsIgnoreCase(displayLevel)) { // 默认为当前栏目的子栏目
				if (StringUtil.isEmpty(parentID)) {
					if (catalog != null) {
						// 如果是子栏目
						if ("1".equals(catalog.getString("isLeaf"))) {
							parentID = catalog.getString("ParentID");
						} else {
							parentID = catalog.getString("ID");
						}
					}
				}
				parentSql = " and innercode like '" + CatalogUtil.getInnerCode(parentID) + "%'";
			} else {
				if (StringUtil.isEmpty(parentID)) {
					if (catalog != null) {
						// 如果是子栏目
						if ("1".equals(catalog.getString("isLeaf"))) {
							parentID = catalog.getString("ParentID");
						} else {
							parentID = catalog.getString("ID");
						}
					}
				}
				parentSql = " and parentid=" + parentID;
			}

			// 没有指定的上级栏目id，且没有当前栏目id 则认为没有找到栏目列表
			String sql = "";
			if (StringUtil.isEmpty(parentID)) {
				return null;
			} else {
				if (StringUtil.isEmpty(condition) || "null".equalsIgnoreCase(condition)) {
					condition = "";
				} else {
					condition = " and " + condition;
				}
				sql = "select * from zccatalog where siteID=? and PublishFlag='Y' " + parentSql + typeSql + condition + " Order by orderflag,id ";
				if ("magazine".equalsIgnoreCase(catalogType)) {
					sql = "select * from zccatalog where siteID=? " + parentSql + typeSql + condition + " Order by orderflag desc,id desc";
				}
				QueryBuilder qb = new QueryBuilder(sql);
				qb.add(site.getLong("ID"));
				dt = qb.executePagedDataTable(pageSize, 0);
			}

			String levelString = getLevelStr();
			dt.insertColumn("Link");
			dt.insertColumn("_RowNo");
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "_RowNo", i + 1);
				dt.set(i, "ImagePath", levelString + dt.getString(i, "ImagePath"));
				String url = CatalogUtil.getLink(dt.getString(i, "ID"), levelString);
				dt.set(i, "Link", url);
			}
			ColumnUtil.extendCatalogColumnData(dt, site.getLong("ID"), levelString);
		}
		TemplateCache.setDataTable(key, dt);

		return dt;
	}

	public DataTable getDocList(String item, String catalogName, String displayLevel, String type, String count) {
		return getDocList(item, catalogName, displayLevel, null, type, count, "", "", "");
	}

	public DataTable getDocList(String item, String catalogName, String type, String count) {
		return getDocList(item, catalogName, "all", null, type, count, "", "", "");
	}

	public DataTable getDocList(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String countStr, String condition) {
		return getDocList(item, catalogName, displayLevel, "", orderType, countStr, condition, "", "");
	}

	public DataTable getDocList(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String countStr, String condition, String cmsorder) {
		return getDocList(item, catalogName, displayLevel, "", orderType, countStr, condition, "", cmsorder);
	}

	/**
	 * 文章列表 为<cms:list item="article" type="relate/hot/recent" catalog="栏目名称"
	 * parent="" count="20"></cms:list>提供数据
	 * 
	 * @param catalogName
	 * @param item
	 * @param orderType
	 * @param countStr
	 * @param condition
	 * @return
	 */
	public DataTable getDocList(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String countStr, String condition, String tag, String cmsorder) {
		DataTable dt = null;
		String key = cmsorder + "_" + item + "_" + catalogName + "_" + displayLevel + "_" + hasAttribute + "_" + orderType + "_" + countStr + "_" + condition + "_" + tag + "_" + level;
		if (StringUtil.isNotEmpty(cmsorder)) {
			key = "cmslist_" + cmsorder;
		}

		// 获取缓存数据,其中相关文章不需要缓存
		if (!"relate".equalsIgnoreCase(orderType)) {
			dt = TemplateCache.getDataTable(key);
			if (dt != null) {
				return dt;
			}
		}

		if (countStr == null || "".equals(countStr) || "null".equals(countStr)) {
			countStr = "50";
		}
		int pageSize = Integer.parseInt(countStr);

		if (article != null) {
			if (!StringUtil.isEmpty(article.get(0)) && StringUtil.isNotEmpty(cmsorder)) {
				dt = mArticleCache.readCacheByDataTable(String.valueOf(article.get(0)), key);
				if (dt == null || dt.getRowCount() <= 0) {
					dt = getPagedDocList(item, catalogName, displayLevel, hasAttribute, orderType, condition, tag, pageSize, 0);
					mArticleCache.writeCache(key, String.valueOf(article.get(0)), dt);
				}
			} else {
				// 列表页缓存
				dt = getPagedDocList(item, catalogName, displayLevel, hasAttribute, orderType, condition, tag, pageSize, 0);
			}
		} else {
			dt = getPagedDocList(item, catalogName, displayLevel, hasAttribute, orderType, condition, tag, pageSize, 0);
		}
		// TemplateCache.setDataTable(key, dt);
		return dt;
	}

	/**
	 * 关键词html化
	 * 
	 * @param catalogId
	 * 
	 * @param item
	 * @return
	 */
	private String tagToHtml(DataRow dataRow) {
		String tagStr = dataRow.getString("tag");
		return tagStr;
		// String catalogId=dataRow.getString("catalogID");
		// if (StringUtil.isNotEmpty(tagStr)&&StringUtil.isNotEmpty(catalogId))
		// {
		// String[] tag = tagStr.split(" ");
		// GetDBdata db = new GetDBdata();
		// String path = "";
		// String id = "";// 索引id
		// try {
		// path =
		// db.getOneValue("select value from zdconfig where type='FrontServerContextPath'");
		// if (StringUtil.isNotEmpty(catalogId)) {
		// String catalogId1 =
		// db.getOneValue("select id from zccatalog where innercode=left('" +
		// dataRow.get("CatalogInnerCode") + "', 6)");
		// id =
		// db.getOneValue("select id from ZCFullText where memo like '%"+catalogId1+"%'");
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// String tagHtml = "";
		// for (int i = 0; i < tag.length; i++) {
		// ZCTagSchema tZCTagSchema=new ZCTagSchema();
		// ZCTagSet tZCTagSet=tZCTagSchema.query(new QueryBuilder("where tag= '"
		// + tag[i] + "' "));
		// if(tZCTagSet.get(0)!=null){
		// String tagId=tZCTagSet.get(0).getID()+"";
		// if(tagId.length()<6){
		// tagId=StringUtil.leftPad(tagId, '0', 6);
		// }
		// tagHtml += "<a href=\"" + path + "/tag/"+tagId+"000001" + "-" + id +
		// ".html\">" + tag[i] + "</a>&nbsp;&nbsp;";
		//					
		// }
		// }
		// return tagHtml;
		// }
		// return "";

	}

	/**
	 * 提供分页数据
	 * 
	 * @param item
	 * @param catalogName
	 * @param displayLevel
	 * @param hasAttribute
	 * @param orderType
	 * @param condition
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public DataTable getPagedDocList(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String condition, String tag, int pageSize, int pageIndex) {
		DataTable dt = null;

		String listId = this.getGlobalData("nowListId", "nowListId");

		if ("true".equals(this.getGlobalData(listId, listId)) && (null != this.customDataTable)) {
			if (0 == pageSize) {
				dt = this.customDataTable;
			} else {
				dt = new DataTable();
				int count = (pageIndex + 1) * pageSize;
				count = this.customDataTable.getRowCount() > count ? count : this.customDataTable.getRowCount();
				for (int i = pageIndex * pageSize; i < count; i++) {
					dt.insertRow(this.customDataTable.getDataRow(i));
				}
			}
		} else {
			QueryBuilder qb = getDocListQueryBuilder(item, catalogName, displayLevel, hasAttribute, orderType, condition, tag, "*");
			if (StringUtil.isNotEmpty(condition) && condition.startsWith("select")) {
				dt = qb.executeDataTable();

			} else {
				dt = qb.executePagedDataTable(pageSize, pageIndex);
			}
		}

		if (dt == null) {
			return null;
		}

		dt.insertColumn("ArticleLink");
		dt.insertColumn("_RowNo");

		String levelString = getLevelStr();
		String[] columnValue = new String[dt.getRowCount()];
		String[] catalogValue = new String[dt.getRowCount()];
		String[] catalogLink = new String[dt.getRowCount()];
		if (dt.getRowCount() > 0) {
			ColumnUtil.extendDocColumnData(dt, dt.getString(0, "catalogID"));
			dt.insertColumn("filePath");

		}

		if ("article".equalsIgnoreCase(item) || "goods".equalsIgnoreCase(item)) {
			dt.insertColumns(new String[] { "FirstImagePath", "FirstVideoImage", "FirstVideoHtml", "FirstTag", "FirstTagUrl" });
			dealTag(dt);
		} else if ("video".equalsIgnoreCase(item)) {
			dt.insertColumn("RelaVideoHtml");
		}

		// 新加代码2010-07-26
		// if("article".equalsIgnoreCase(item)){
		// //将属性代码转换成属性值
		// Mapx mapattri=HtmlUtil.codeToMapx("ArticleAttribute");
		// dt.decodeColumn("attribute", mapattri);
		// }

		if ("article".equalsIgnoreCase(item)) {
			// 将属性代码转换成属性值
			dt.insertColumn("AttributeName");
			Mapx mapattri = HtmlUtil.codeToMapx("ArticleAttribute");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < dt.getRowCount(); i++) {
				String att = dt.getString(i, "Attribute");
				if (StringUtil.isNotEmpty(att)) {
					String[] atts = att.split(",");
					for (int j = 0; j < atts.length; j++) {
						sb.append(mapattri.get(atts[j]) + ",");
					}
					dt.set(i, "AttributeName", sb.toString().substring(0, sb.length() - 1));
					sb = new StringBuffer();
				}
			}
			for (int i = 0; i < dt.getRowCount(); i++) {
				String tagHtml = tagToHtml(dt.get(i));
				if (StringUtil.isNotEmpty(tagHtml)) {
					dt.set(i, "tag", tagHtml);
				}
			}

		}

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "_RowNo", i + 1);
			String docID = dt.getString(i, "ID");
			String docCatalogID = dt.getString(i, "catalogID");

			ZCCatalogSchema catalogSchema = CatalogUtil.getSchema(docCatalogID);

			// 取栏目的名称和链接URL
			catalogValue[i] = catalogSchema.getName();
			catalogLink[i] = CatalogUtil.getLink(docCatalogID, levelString);

			if ("article".equalsIgnoreCase(item) || "goods".equalsIgnoreCase(item)) {
				if ("4".equals((String) dt.get(i, "Type"))) { // 转向链接
					columnValue[i] = (String) dt.get(i, "RedirectURL");
				} else {
					if (StringUtils.isEmpty(dt.get(i, "Url") + ""))
						columnValue[i] = levelString + PubFun.getDocURL(dt.get(i));
					else
						columnValue[i] = levelString + dt.get(i, "Url");

					String imagePath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(site.getLong("ID")) + "/";
					imagePath = imagePath.replaceAll("/+", "/");

					String attachPath = Config.getContextPath() + "/Services/AttachDownLoad.jsp";
					attachPath = imagePath.replaceAll("/+", "/");

					String content = dt.getString(i, "Content");
					content = content.replaceAll(imagePath, PubFun.getLevelStr(CatalogUtil.getDetailLevel(docCatalogID)));
					content = content.replaceAll(attachPath, Config.getValue("ServicesContext") + "AttachDownLoad.jsp");
				}

				// 根据属性获取关联图片、附件、视频相关信息
				PubFun.dealArticleMedia(dt, levelString, "image,attchment,video");

			} else if ("image".equalsIgnoreCase(item)) {
				// 根据图片查找对应的文章链接
				DataTable imageRelaTable = new QueryBuilder("select a.id as imageID,b.id,b.catalogid,b.publishdate " + "from zcimagerela a ,zcarticle b where a.relatype='ArticleImage' "
						+ "and a.relaid=b.id and a.id=?", Long.parseLong(docID)).executeDataTable();
				if (imageRelaTable != null && imageRelaTable.getRowCount() > 0) {
					String articleCatalogID = imageRelaTable.getString(0, "catalogid");
					String articleID = imageRelaTable.getString(0, "id");
					String nameRule = CatalogUtil.getSchema(articleCatalogID).getDetailNameRule();
					if (StringUtil.isNotEmpty(nameRule)) {
						HtmlNameParser nameParser = new HtmlNameParser(site, null, imageRelaTable.get(0), nameRule);
						HtmlNameRule h = nameParser.getNameRule();
						dt.set(i, "articlelink", levelString + h.getFullPath());
					} else {
						ZCArticleSchema article = new ZCArticleSchema();
						article.setID(articleID);
						String articleLink = "";
						if (article.fill()) {
							articleLink = levelString + PubFun.getArticleURL(article);
						} else {
							articleLink = "#";
						}

						dt.set(i, "articlelink", articleLink);
					}
				} else {
					dt.set(i, "articlelink", "#");
				}

				// 设置图片的链接地址到图片详细页
				columnValue[i] = levelString + CatalogUtil.getPath(docCatalogID) + docID + ".shtml";
			} else if ("video".equalsIgnoreCase(item)) {
				int vCount = Integer.parseInt(dt.getString(i, "Count"));
				String path = "";
				for (int n = 0; n < vCount; n++) {
					if (n != 0) {
						path += "|";
					}
					path = path + dt.getString(i, "path") + n + "_" + dt.getString(i, "FileName");
				}
				dt.set(i, "filePath", path);
				String nameRule = CatalogUtil.getSchema(docCatalogID).getDetailNameRule();
				if (StringUtil.isNotEmpty(nameRule)) {
					HtmlNameParser nameParser = new HtmlNameParser(site, null, dt.get(i), nameRule);
					HtmlNameRule h = nameParser.getNameRule();
					columnValue[i] = levelString + h.getFullPath();
				} else {
					columnValue[i] = levelString + CatalogUtil.getPath(docCatalogID) + docID + ".shtml";
				}

				dt.set(i, "RelaVideoHtml", PubFun.getVideoHtml(dt.getDataRow(i)));
			} else if ("attachment".equalsIgnoreCase(item)) {
				if (StringUtil.isNotEmpty(docCatalogID) && "N".equals(CatalogUtil.getAttachDownFlag(docCatalogID))) {
					columnValue[i] = levelString + dt.getString(i, "path") + dt.getString(i, "filename");
				} else if ("N".equals(SiteUtil.getAttachDownFlag(dt.getString(i, "SiteID")))) {
					columnValue[i] = levelString + dt.getString(i, "path") + dt.getString(i, "filename");
				} else {
					columnValue[i] = Config.getValue("ServicesContext") + "/AttachDownLoad.jsp?id=" + docID;
				}
			} else {
				String nameRule = CatalogUtil.getSchema(docCatalogID).getDetailNameRule();
				if (StringUtil.isNotEmpty(nameRule)) {
					HtmlNameParser nameParser = new HtmlNameParser(site, null, dt.get(i), nameRule);
					HtmlNameRule h = nameParser.getNameRule();
					columnValue[i] = levelString + h.getFullPath();
				} else {
					columnValue[i] = levelString + CatalogUtil.getPath(docCatalogID) + docID + ".shtml";
				}
			}

		}
		dt.insertColumn("Link", columnValue);
		dt.insertColumn("CatalogName", catalogValue);
		dt.insertColumn("CatalogLink", catalogLink);
		dt.insertColumn("BranchName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "BranchName", PubFun.getBranchName(dt.getString(i, "BranchInnerCode")));
		}
		String cs[] = { "htorange", "htpurple", "htgreen", "htpink", "htred" };
		dt.insertColumn("css");
		for (int i = 0; i < dt.getRowCount(); i++) {
			Random random = new Random();
			dt.set(i, "css", cs[random.nextInt(4)]);
		}

		// 相关文章、推荐文章需要重新排序
		if (doc != null) {
			if ("relate".equalsIgnoreCase(orderType) || "newRecommend".equalsIgnoreCase(orderType)) {
				String relaIDs = "";
				if ("relate".equalsIgnoreCase(orderType)) {
					relaIDs = doc.getString("RelativeArticle");
				} else if ("newRecommend".equalsIgnoreCase(orderType)) {
					relaIDs = doc.getString("RecommendArticle");
				}

				// 按ID顺序来显示
				String[] ids = relaIDs.split("\\,");
				DataTable result = new DataTable(dt.getDataColumns(), null);
				for (int i = 0; i < ids.length; i++) {
					for (int j = 0; j < dt.getRowCount(); j++) {
						if (dt.getString(j, "ID").equals(ids[i])) {
							result.insertRow(dt.getDataRow(j));
							break;
						}
					}
				}

				dt = result;
			}
		}

		return dt;
	}

	/**
	 * 获取分页列表的总条数
	 * 
	 * @param item
	 * @param catalogName
	 * @param displayLevel
	 * @param hasAttribute
	 * @param orderType
	 * @param condition
	 * @return
	 */
	public int getPagedDocListTotal(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String condition, String tag) {
		int total = 0;
		String listId = this.getGlobalData("nowListId", "nowListId");
		if ("true".equals(this.getGlobalData(listId, listId)) && (null != this.customDataTable)) {
			total = this.customDataTable.getRowCount();
		} else {
			QueryBuilder qb = getDocListQueryBuilder(item, catalogName, displayLevel, hasAttribute, orderType, condition, tag, "*");
			total = DBUtil.getCount(qb);
		}
		return total;
	}

	public QueryBuilder getDocListQueryBuilder(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String condition, String tag, String cols) {
		String typeStr = "";
		String key = item + "_" + catalogName + "_" + displayLevel + "_" + hasAttribute + "_" + orderType + "_" + condition + "_" + tag + "_" + cols + "_querybuilder";
		QueryBuilder qb = TemplateCache.getQueryBuilder(key);
		if (qb != null) {
			return qb;
		} else {
			qb = new QueryBuilder();
		}

		if ("article".equalsIgnoreCase(item) || "goods".equalsIgnoreCase(item)) {
			if ("relate".equalsIgnoreCase(orderType) && doc != null) {
				String relateID = doc.getString("RelativeArticle");
				String keyword = doc.getString("keyword");
				if (StringUtil.isNotEmpty(relateID)) {
					typeStr += " and id in (" + relateID + ") ";
				} else if (keyword != null && !"".equalsIgnoreCase(keyword)) {
					String[] keywordArr = keyword.split(",");
					for (int i = 0; i < keywordArr.length; i++) {
						typeStr += " and keyword like '%" + keywordArr[i] + "%'";
					}
				} else {
					typeStr += " and 1=2";// 如果相关文件，则都不显示
				}
				typeStr += " order by topflag desc,orderflag desc,publishdate desc, id desc";
			} else if ("newRecommend".equalsIgnoreCase(orderType) && doc != null) {
				String recommendArticle = doc.getString("RecommendArticle");
				if (StringUtil.isNotEmpty(recommendArticle)) {
					typeStr += " and id in (" + recommendArticle + ") ";
				} else {
					typeStr += " and 1=2";// 如果没有文章文件，则都不显示
				}
				typeStr += " order by topflag desc,orderflag desc,publishdate desc, id desc";
			} else if ("hitcount".equalsIgnoreCase(orderType)) {
				typeStr += " order by hitcount desc,topflag desc,orderflag desc";
			} else if ("top".equalsIgnoreCase(orderType)) {
				typeStr += " and topflag='1' order by orderflag desc";
			} else if ("recent".equalsIgnoreCase(orderType)) {
				typeStr += " order by topflag desc, publishdate desc,orderflag desc, id desc";
			} else if ("modify".equalsIgnoreCase(orderType)) {// 按修改时间排序
				typeStr += " order by topflag desc, modifytime desc,orderflag desc, id desc";
			} else if ("modifyzx".equalsIgnoreCase(orderType)) {// 按修改时间排序
				typeStr += " order by modifytime desc";
			} else if ("rand".equalsIgnoreCase(orderType)) {// 按随机排序
				typeStr += " order by rand()";
			} else if ("firstpublish".equalsIgnoreCase(orderType)) {// 按第一次发布时间排序
				typeStr += " order by FirstPublishDate desc";
			} else {
				typeStr += " order by topflag desc,orderflag desc,publishdate desc, id desc";
			}

			// 文章属性，如图片、视频、推荐、热点新闻等
			if (StringUtil.isNotEmpty(hasAttribute) && !"null".equals(hasAttribute)) {
				String[] attribute = hasAttribute.split(",");
				if (attribute.length > 0) {
					String attributeSql = "";
					for (int i = 0; i < attribute.length; i++) {
						String attr = attribute[i].trim();
						if (StringUtil.isNotEmpty(attr)) {
							attributeSql += " and attribute like '%" + attribute[i] + "%'";
						}
					}
					typeStr = attributeSql + typeStr;
				}
			}
			if (StringUtil.isNotEmpty(tag) && !"null".equals(tag)) {
				String[] tags = tag.split(" ");
				if (tags.length > 0) {
					String tagSql = "";
					for (int i = 0; i < tags.length; i++) {
						String attr = tags[i].trim();
						if (StringUtil.isNotEmpty(attr)) {
							if (i == 0) {
								tagSql += " and (tag like '%" + tags[i] + "%'";
							} else if (i == (tags.length - 1)) {
								tagSql += " or tag like '%" + tags[i] + "%')";
							} else {
								tagSql += " or tag like '%" + tags[i] + "%'";
							}
						}
					}
					if (tags.length == 1) {
						tagSql += ")";
					}
					typeStr = tagSql + typeStr;
				}
			}
		} else if ("image".equalsIgnoreCase(item)) {

			if ("recent".equalsIgnoreCase(orderType)) {
				typeStr += " order by id desc";

			} else if ("firstpublish".equalsIgnoreCase(orderType)) {
				typeStr += " order by  addtime  ";

			} else {
				typeStr += " order by id desc";
			}

		} else {
			if ("recent".equalsIgnoreCase(orderType)) {
				typeStr += " order by id desc";
			} else {
				typeStr += " order by id desc";
			}
		}

		// 确定栏目的ID
		String catalogID = null;
		if (StringUtil.isNotEmpty(catalogName) && !"null".equalsIgnoreCase(catalogName)) {
			if (catalogName.indexOf(",") != -1) {
				catalogID = CatalogUtil.getIDsByName(site.getString("ID"), catalogName);
			} else if (catalogName.indexOf("/") != -1) {
				catalogID = CatalogUtil.getIDByNames(site.getString("ID"), catalogName);
			} else {
				catalogID = CatalogUtil.getIDByName(site.getString("ID"), catalogName);
			}
			// 直接数字
			if (StringUtil.isEmpty(catalogID) && StringUtil.isDigit(catalogName)) {
				catalogID = catalogName;
			}
		} else if ("relate".equalsIgnoreCase(orderType)) {// 如果是获取相关文章，则根据全站查找
			displayLevel = "root";
		} else {
			if (catalog != null) {
				catalogID = catalog.getString("ID");
			}
			if (doc != null) {
				catalogID = doc.getString("CatalogID");
			}
		}

		// 没有找到栏目名称id为空
		if (!"root".equalsIgnoreCase(displayLevel) && StringUtil.isEmpty(catalogID)) {
			return null;
		}

		String parentInnerCode = "";
		if (StringUtil.isNotEmpty(catalogID) && catalogID.indexOf(",") != -1) {
			String[] catalogIDs = catalogID.split(",");
			for (int m = 0; m < catalogIDs.length; m++) {
				parentInnerCode += CatalogUtil.getInnerCode(catalogIDs[m]) + ",";
			}

			if (StringUtil.isNotEmpty(parentInnerCode)) {
				parentInnerCode = parentInnerCode.substring(0, parentInnerCode.length() - 1);
			}
		} else {
			parentInnerCode = CatalogUtil.getInnerCode(catalogID);
		}
		String catalogStr = " 1=1 ";
		// 确定文章的显示层级
		if ("root".equalsIgnoreCase(displayLevel)) { // 站点根栏目
			catalogStr = " siteid=?";
			qb.add(site.getLong("id"));
		} else if ("current".equalsIgnoreCase(displayLevel)) { // 当前栏目的同级栏目
			if (StringUtil.isNotEmpty(catalogID) && catalogID.indexOf(",") != -1) {
				catalogStr = " catalogid in (";
				catalogStr += catalogID;
				catalogStr += ")";
			} else {
				catalogStr = " catalogid=?";
				qb.add(Long.parseLong(catalogID));
			}
		} else if ("child".equalsIgnoreCase(displayLevel)) { // 当前栏目的子栏目的文章，不包含本栏目
			if (StringUtil.isNotEmpty(catalogID) && catalogID.indexOf(",") != -1) {
				catalogStr = " catalogid not in (";
				catalogStr += catalogID;
				catalogStr += ")";
				String[] innercodes = parentInnerCode.split(",");
				for (int m = 0; m < innercodes.length; m++) {
					if (m == 0) {
						catalogStr = catalogStr + " and (cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%'";
					} else if (m == innercodes.length - 1) {
						catalogStr = catalogStr + " or cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%')";
					} else {
						catalogStr = catalogStr + " or cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%'";
					}
				}
			} else {
				catalogStr = " catalogid <> ?";
				qb.add(Long.parseLong(catalogID));
				catalogStr = catalogStr + " and cataloginnercode like ?";
				qb.add(parentInnerCode + "%");
			}
		} else if ("all".equalsIgnoreCase(displayLevel)) {// 默认为当前栏目所有子栏目的文章
			if (StringUtil.isNotEmpty(catalogID) && catalogID.indexOf(",") != -1) {
				String[] innercodes = parentInnerCode.split(",");
				for (int m = 0; m < innercodes.length; m++) {
					if (m == 0) {
						catalogStr = catalogStr + " and (cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%'";
					} else if (m == innercodes.length - 1) {
						catalogStr = catalogStr + " or cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%')";
					} else {
						catalogStr = catalogStr + " or cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%'";
					}
				}
			} else {
				catalogStr = catalogStr + " and cataloginnercode like ?";
				qb.add(parentInnerCode + "%");
			}
		} else if ("none".equalsIgnoreCase(displayLevel)) {

		} else {
			if (StringUtil.isNotEmpty(catalogID) && catalogID.indexOf(",") != -1) {
				String[] innercodes = parentInnerCode.split(",");
				for (int m = 0; m < innercodes.length; m++) {
					if (m == 0) {
						catalogStr = catalogStr + " and (cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%'";
					} else if (m == innercodes.length - 1) {
						catalogStr = catalogStr + " or cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%')";
					} else {
						catalogStr = catalogStr + " or cataloginnercode like '";
						catalogStr = catalogStr + innercodes[m] + "%'";
					}
				}
			} else {
				catalogStr = catalogStr + " and cataloginnercode like ?";
				qb.add(parentInnerCode + "%");
			}
		}

		String statusStr = "";
		if ("article".equals(item) || "goods".equals(item)) {
			statusStr = " and status in(" + Article.STATUS_PUBLISHED + ",'25') and (publishdate<=? or publishdate is null)";
			qb.add(new Date());
		}
		if (StringUtil.isEmpty(condition) || "null".equals(condition)) {
			condition = "1=1";
		}
		String sql = "";
		if (item.equals("goods")) {
			sql += "select " + cols + " from zs";
		} else {
			sql += "select " + cols + " from zc";
		}
		sql += item + " where " + condition + " and " + catalogStr + statusStr + typeStr;

		if (StringUtil.isNotEmpty(condition) && condition.startsWith("select")) {
			qb = new QueryBuilder(condition);

		} else {
			qb.setSQL(sql);
			TemplateCache.setQueryBuilder(key, qb);
		}
		return qb;
	}

	public DataTable getMagazineList(String item, String name, String count) {
		return getMagazineList(item, null, name, count, null, null);
	}

	public DataTable getMagazineList(String item, String name, String count, String condition, String cmsorder) {
		return getMagazineList(item, null, name, count, condition, cmsorder);
	}

	/**
	 * 期刊数据 <cms:list item="magazine" name="Magazine1" count=15></cms:list>
	 * 
	 * @param item
	 * @param type
	 * @param name
	 * @param count
	 * @param condition
	 * @return
	 */
	public DataTable getMagazineList(String item, String type, String name, String count, String condition, String cmsorder) {
		DataTable dt = null;

		// 获取缓存数据
		String key = item + "_" + type + "_" + name + "_" + count + "_" + condition;
		dt = TemplateCache.getDataTable(key);
		if (dt != null) {
			return dt;
		}

		if (count == null || "".equals(count) || "null".equals(count)) {
			count = "20";
		}
		int pageSize = Integer.parseInt(count);
		if ("magazine".equalsIgnoreCase(item)) {
			String sql = "";
			if (StringUtil.isEmpty(name)) {
				sql = "select * from ZCMagazine where siteID=? ";
				QueryBuilder qb = new QueryBuilder(sql);
				qb.add(site.getString("ID"));
				dt = qb.executePagedDataTable(pageSize, 0);
			} else {
				sql = "select * from ZCMagazine where siteID=? and name=?";
				QueryBuilder qb = new QueryBuilder(sql);
				qb.add(site.getString("ID"));
				qb.add(name);
				dt = qb.executePagedDataTable(pageSize, 0);
			}
		}
		TemplateCache.setDataTable(key, dt);

		return dt;
	}

	/**
	 * 期刊期数数据 <cms:list item="magazineIssue" name="Magazine1"
	 * count=15></cms:list>
	 * 
	 * @param item
	 * @param name
	 * @param type
	 * @param count
	 * @param condition
	 * @return dt 期刊期数数据
	 */
	public DataTable getMagazineIssueList(String item, String name, String type, String count, String condition, String cmsorder) {
		DataTable dt = null;

		// 获取缓存数据
		String key = item + "_" + name + "_" + type + "_" + count + "_" + condition;
		dt = TemplateCache.getDataTable(key);
		if (dt != null) {
			return dt;
		}

		if (count == null || "".equals(count) || "null".equals(count)) {
			count = "20";
		}
		String levelStr = getLevelStr();
		int pageSize = Integer.parseInt(count);
		if ("magazineissue".equalsIgnoreCase(item)) {
			String sql = "";
			QueryBuilder qb = new QueryBuilder();
			if (StringUtil.isEmpty(name)) {
				sql = "select * from ZCMagazine where siteID=?";
				qb.setSQL(sql);
				qb.add(site.getString("ID"));
			} else {
				sql = "select * from ZCMagazine where siteID=? and name=?";
				qb.setSQL(sql);
				qb.add(site.getString("ID"));
				qb.add(name);
			}
			DataTable magazineDT = qb.executeDataTable();

			if (magazineDT.getRowCount() > 0) {
				sql = "select * from ZCMagazineIssue where MagazineID=?";
				String catalogID = "";
				if (catalog != null && !type.equalsIgnoreCase("all")) {
					catalogID = catalog.getString("ID");
					String catalogName = CatalogUtil.getName(catalogID);
					int catalogType = (int) CatalogUtil.getCatalogType(catalogID);
					if (catalogType == Catalog.MAGAZINE) {
						if (catalogName.indexOf("年第") != -1) {
							String currentYear = catalogName.substring(0, catalogName.indexOf("年第"));
							String currentPeriodNum = catalogName.substring(catalogName.indexOf("年第") + 2, catalogName.indexOf("期"));
							sql = "select * from ZCMagazineIssue where MagazineID=?" + " and Year='" + currentYear + "' and PeriodNum='" + currentPeriodNum + "'";
						}
					}
				}
				sql += " order by year desc,periodnum desc,ID desc";

				qb = new QueryBuilder(sql);
				qb.add(magazineDT.getString(0, "ID"));
				dt = qb.executePagedDataTable(pageSize, 0);
				dt.insertColumn("PrevLink");
				dt.insertColumn("NextLink");
				dt.insertColumn("Link");

				if (dt.getRowCount() > 0) {
					for (int i = 0; i < dt.getRowCount() - 1; i++) {
						String prevCatalogID = CatalogUtil.getIDByNames(site.getString("ID"), name + "/" + dt.getString(i, "Year") + "年第" + dt.getString(i, "PeriodNum") + "期");
						dt.set(i, "PrevLink", CatalogUtil.getLink(prevCatalogID, levelStr));
					}

					for (int i = 1; i < dt.getRowCount(); i++) {
						String nextCatalogID = CatalogUtil.getIDByNames(site.getString("ID"), name + "/" + dt.getString(i - 1, "Year") + "年第" + dt.getString(i - 1, "PeriodNum") + "期");
						dt.set(0, "NextLink", CatalogUtil.getLink(nextCatalogID, levelStr));
					}

					DataTable magazineIssueDT = new QueryBuilder("select * from ZCMagazineIssue where MagazineID=? and ID>? order by year desc,periodnum desc,ID desc", magazineDT.getString(0, "ID"),
							dt.getString(0, "ID")).executeDataTable();
					if (magazineIssueDT.getRowCount() > 0) {
						String nextCatalogID = CatalogUtil.getIDByNames(site.getString("ID"), name + "/" + magazineIssueDT.getString(0, "Year") + "年第" + magazineIssueDT.getString(0, "PeriodNum")
								+ "期");
						dt.set(0, "NextLink", CatalogUtil.getLink(nextCatalogID, levelStr));
					} else {
						dt.set(0, "NextLink", "#");
					}

					magazineIssueDT = new QueryBuilder("select * from ZCMagazineIssue where MagazineID=? and ID<? order by year desc,periodnum desc,ID desc", magazineDT.getString(0, "ID"), dt
							.getString(0, "ID")).executeDataTable();
					if (magazineIssueDT.getRowCount() > 0) {
						String nextCatalogID = CatalogUtil.getIDByNames(site.getString("ID"), name + "/" + magazineIssueDT.getString(0, "Year") + "年第" + magazineIssueDT.getString(0, "PeriodNum")
								+ "期");
						dt.set(dt.getRowCount() - 1, "PrevLink", CatalogUtil.getLink(nextCatalogID, levelStr));
					} else {
						dt.set(dt.getRowCount() - 1, "PrevLink", "#");
					}

					for (int m = 0; m < dt.getRowCount(); m++) {
						String ID = CatalogUtil.getIDByNames(site.getString("ID"), name + "/" + dt.getString(m, "Year") + "年第" + dt.getString(m, "PeriodNum") + "期");
						ColumnUtil.extendCatalogColumnData(dt.getDataRow(m), SiteUtil.getURL(site.getString("ID")));
						if (StringUtil.isDigit(ID)) {
							dt.set(m, "Link", CatalogUtil.getLink(ID, levelStr));
						} else {
							dt.set(m, "Link", "#");
						}
					}
				} else {
					return null;
				}

			} else {
				return null;
			}
		}
		TemplateCache.setDataTable(key, dt);

		return dt;
	}

	/**
	 * 友情链接数据 <cms:list item="friendlink" group="link1" count=15></cms:list>
	 * 
	 * @param item
	 * @param group
	 *            链接分组代码code
	 * @param count
	 * @param condition
	 * @return
	 */
	public DataTable getFriendLinkList(String item, String group, String count, String condition, String cmsorder) {
		DataTable dt = new DataTable();
		if (count == null || "".equalsIgnoreCase(count)) {
			count = "20";
		}
		String sql = "select * from zclink where siteID = " + site.getString("ID") + " and LinkGroupID = " + "(select id from zclinkgroup where siteID = " + site.getString("ID")
				+ " and name=?) order by OrderFlag desc,id desc";

		int pageSize = Integer.parseInt(count);
		String key = "cmslist_" + cmsorder;
		if (article != null) {
			if (!StringUtil.isEmpty(article.get(0)) && StringUtil.isNotEmpty(cmsorder)) {
				dt = mArticleCache.readCacheByDataTable(String.valueOf(article.get(0)), key);
				if (dt == null || dt.getRowCount() <= 0) {
					dt = new QueryBuilder(sql, group).executePagedDataTable(pageSize, 0);
					mArticleCache.writeCache(key, String.valueOf(article.get(0)), dt);
				}
			} else {
				// 列表页缓存
				dt = new QueryBuilder(sql, group).executePagedDataTable(pageSize, 0);
			}
		} else {
			dt = new QueryBuilder(sql, group).executePagedDataTable(pageSize, 0);
		}
		return dt;
	}

	/**
	 * 热点专题 <cms:list item="hotopic" ></cms:list>
	 * 
	 * @param item
	 * @param group
	 *            链接分组代码code
	 * @param count
	 * @param condition
	 * @return
	 */

	public DataTable getHoTopicList(String item, String group, String count, String condition, String cmsorder) {
		DataTable dt = new DataTable();
		String sql = null;
		if (count == null || "".equalsIgnoreCase(count)) {
			count = "20";
		}
		sql = "select * from zclinkgroup where siteID = " + site.getString("ID") + " and Prop1 = " + "(select id from ZCLinkTeam where siteID = " + site.getString("ID")
				+ " and name = ?) order by id ";
		int pageSize = Integer.parseInt(count);
		String key = "cmslist_" + cmsorder;
		if (article != null) {
			if (!StringUtil.isEmpty(article.get(0)) && StringUtil.isNotEmpty(cmsorder)) {
				dt = mArticleCache.readCacheByDataTable(String.valueOf(article.get(0)), key);
				if (dt == null || dt.getRowCount() <= 0) {
					dt = new QueryBuilder(sql, group).executePagedDataTable(pageSize, 0);
					mArticleCache.writeCache(key, String.valueOf(article.get(0)), dt);
				}
			} else {
				// 列表页缓存
				dt = new QueryBuilder(sql, group).executePagedDataTable(pageSize, 0);
			}
		} else {
			dt = new QueryBuilder(sql, group).executePagedDataTable(pageSize, 0);
		}
		return dt;
	}

	public DataTable getReportCompany() {
		DataTable dt = new QueryBuilder("select CodeValue as companyCode, CodeName as companyPhone, Memo as companyName from zdcode where CodeType='ReportPhone' and ParentCode='ReportPhone' order by CodeOrder asc ").executeDataTable();
		return dt;
	}
	
	public DataTable getZdcodeInfo(String CodeType) {
		DataTable dt = new QueryBuilder("select CodeValue, CodeName, Memo from zdcode where CodeType=? and ParentCode=? order by CodeOrder asc ", CodeType, CodeType).executeDataTable();
		return dt;
	}
	
	/**
	 * 页面文件数据 <cms:list item="link" group="link1" count=15></cms:list>
	 * 
	 * @param item
	 * @param group
	 * @param count
	 * @param condition
	 * @return
	 */
	public DataTable getBlockList(String item, String count, String condition, String cmsorder) {
		DataTable dt = new DataTable();
		if (count == null || "".equalsIgnoreCase(count)) {
			count = "20";
		}
		String blockid = block.getString("ID");
		String blockType = block.getString("Type");
		String sortField = block.getString("SortField");
		String catalogID = block.getString("CatalogID");
		String siteID = block.getString("SiteID");

		String sql = "";
		QueryBuilder qb = new QueryBuilder();
		if ("1".equalsIgnoreCase(blockType)) {
			if (catalogID != null && !"".equalsIgnoreCase(catalogID)) {
				sql = "select * from zcarticle where catalogid = ? order by " + sortField + " desc";
				qb.add(catalogID);
			} else {
				sql = "select * from zcarticle where exists(select id from zccatalog where siteid = ? and id=zcarticle.catalogid) order by " + sortField + " desc";
				qb.add(siteID);
			}
		} else if ("2".equalsIgnoreCase(blockType)) {
			sql = "select * from zcpageblockitem where blockid = ? order by id";
			qb.add(blockid);
		}
		qb.setSQL(sql);

		int pageSize = Integer.parseInt(count);
		// 文章发布缓存
		String key = "cmslist_" + cmsorder;
		if (article != null || StringUtil.isEmpty(cmsorder)) {
			if (!StringUtil.isEmpty(article.get(0)) && StringUtil.isNotEmpty(cmsorder)) {
				dt = mArticleCache.readCacheByDataTable(String.valueOf(article.get(0)), key);
				if (dt == null || dt.getRowCount() <= 0) {
					dt = qb.executePagedDataTable(pageSize, 0);
					mArticleCache.writeCache(key, String.valueOf(article.get(0)), dt);
				}
			} else {
				// 列表页缓存
				dt = qb.executePagedDataTable(pageSize, 0);
			}
		} else {
			dt = qb.executePagedDataTable(pageSize, 0);
		}

		String levelString = "";
		for (int i = 0; i < level; i++) {
			levelString += "../";
		}

		int size = dt.getRowCount();
		String[] columnValue = new String[dt.getRowCount()];
		for (int i = 0; i < size; i++) {
			if ("1".equalsIgnoreCase(blockType)) {
				columnValue[i] = levelString + PubFun.getDocURL(dt.get(i));
			} else if ("2".equalsIgnoreCase(blockType)) {
				columnValue[i] = (String) dt.get(i, "URL");
			}
		}

		dt.insertColumn("Link", columnValue);
		return dt;
	}

	public String getTree(String id, String method, String tagBody, String style, String levelStr) {
		TreeAction ta = new TreeAction();

		ta.setTagBody(tagBody);
		ta.setMethod(method);
		ta.setID(id);

		int level = Integer.parseInt(levelStr);
		if (level <= 0) {
			level = 999;
		}
		ta.setLevel(level);
		ta.setStyle(style);

		HtmlP p = new HtmlP();
		try {
			p.parseHtml(ta.getTagBody());

			ta.setTemplate(p);

			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);
			Class c = Class.forName(className);
			Method m = c.getMethod(method, new Class[] { TreeAction.class });
			m.invoke(null, new Object[] { ta });

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return ta.getHtml();
	}

	public String getAD(String name, String type) {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isEmpty(name) || name == null) {
			sb.append("广告的代码为空，请修改模板");
		} else {
			DataTable positionDT = new QueryBuilder("select * from zcadposition where PositionName = ? and siteid=?", name, site.getLong("ID")).executeDataTable();
			if (positionDT.getRowCount() <= 0) {
				sb.append("广告版位代码：" + name + "没有找到");
			} else {
				if (positionDT.get(0, "PositionType").equals("code")) {
					DataTable adDt = new QueryBuilder("select * from ZCAdvertisement where PositionID = ? and IsOpen = 'Y'", positionDT.getLong(0, "ID")).executeDataTable();
					sb.append(adDt.getString(0, "AdContent"));
				} else {
					if (type != null && StringUtil.isNotEmpty(type) && type.equalsIgnoreCase("json")) {
						sb.append(Advertise.getJson(positionDT.getString(0, "ID")));
					} else {
						String jsName = positionDT.getString(0, "jsname");
						sb.append("<script language='javascript' src='" + Config.getValue("StaticResourcePath") + "/" + jsName + "'></script>");
						//Advertise.CreateJSCode(positionDT.getString(0, "ID"));
					}
				}
			}
		}
		return sb.toString();
	}

	public String getForm(String code) {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isEmpty(code)) {
			sb.append("表单ID为空，请修改模板");
		} else {
			ZCCustomTableSet set = new ZCCustomTableSchema().query(new QueryBuilder("where Code=?", code));
			if (code == null || set.size() <= 0) {
				sb.append("表单：" + code + "没有找到");
			} else {
				String parseContent = Form.getRuntimeFormContent(set.get(0));
				sb.append(parseContent);
				sb.append("</form>");
			}
		}
		return sb.toString();
	}

	/**
	 * 调查
	 * 
	 * @param code
	 * @return
	 */
	public String getVote(String name, String id) {
		StringBuffer sb = new StringBuffer();
		ZCVoteSchema vote = new ZCVoteSchema();
		QueryBuilder qb = null;
		if (StringUtil.isNotEmpty(id) && StringUtil.isDigit(id)) {
			qb = new QueryBuilder(" where id=? and siteid=?", Long.parseLong(id), site.getLong("ID"));
		} else if (StringUtil.isNotEmpty(name) && !"null".equals(name)) {
			qb = new QueryBuilder(" where title=? and siteid=?", name, site.getLong("ID"));
		} else {
			return "<font color=red>没有找到对应的调查。</font>";
		}

		ZCVoteSet set = vote.query(qb);
		if (set.size() < 1) {
			return "<font color=red>没有找到对应的调查。</font>";
		}
		vote = set.get(0);
		sb.append("<!--" + vote.getTitle() + "-->\n");
		sb.append("<script language='javascript' src='" + (getLevelStr() + "js/vote_" + vote.getID()).replaceAll("/+", "/") + ".js'></script>");
		if (!new File((Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/" + SiteUtil.getAlias(vote.getSiteID()) + "/js/").replaceAll("/+", "/") + "vote_" + vote.getID() + ".js")
				.exists()) {
			Vote.generateJS(vote.getID());
		}
		return sb.toString();
	}

	public DataRow getVoteData(String name, String id) {
		ZCVoteSchema vote = new ZCVoteSchema();
		QueryBuilder qb = null;
		if (StringUtil.isNotEmpty(id) && StringUtil.isDigit(id)) {
			qb = new QueryBuilder(" where id=? and siteid=?", id, site.getLong("ID"));
		} else if (StringUtil.isNotEmpty(name) && !"null".equals(name)) {
			qb = new QueryBuilder(" where title=? and siteid=?", name, site.getLong("ID"));
		} else {
			return null;
		}

		ZCVoteSet set = vote.query(qb);
		if (set.size() < 1) {
			return null;
		}
		vote = set.get(0);
		return vote.toDataRow();
	}

	public DataTable getVoteSubjects(String voteID, int count) {
		QueryBuilder qb = new QueryBuilder("select * from zcvoteSubject where VoteID = ? order by ID", Long.parseLong(voteID));
		return qb.executePagedDataTable(count, 0);
	}

	public DataTable getVoteItems(String subjectID, String inputType, int count) {
		QueryBuilder qb = new QueryBuilder("select * from zcvoteitem where subjectID = ? order by ID", Long.parseLong(subjectID));
		DataTable dt = qb.executePagedDataTable(count, 0);
		dt.insertColumn("html");

		if ("Y".equals(inputType)) {
			inputType = "checkbox";
		} else {
			inputType = "radio";
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			String html = "";
			if ("0".equals(dt.getString(i, "ItemType"))) {
				html = "<label><input name='Subject_" + subjectID + "' type='" + inputType + "' value='" + dt.getString(i, "id") + "' />" + dt.getString(i, "item") + "</label>\n";
			} else {
				html = "<label><input name='Subject_" + subjectID + "' type='" + inputType + "' value='" + dt.getString(i, "id") + "' id='Subject_" + subjectID + "_Item_" + dt.getString(i, "id")
						+ "_Button'/>" + dt.getString(i, "item") + "</label><input id='Subject_" + subjectID + "_Item_" + dt.getString(i, "id") + "' name='Subject_" + subjectID + "_Item_"
						+ dt.getString(i, "id") + "' type='text' value='' onClick=\\\"clickInput('Subject_" + subjectID + "_Item_" + dt.getString(i, "id") + "');\\\"/>\n";
			}
			dt.set(i, "html", html);
		}
		return dt;
	}

	public String getComment(String count) {
		return "<script src=\"" + Config.getValue("ServicesContext") + Config.getValue("CommentListJS") + "?RelaID=" + doc.getString("ID") + "&CatalogID=" + doc.getString("CatalogID")
				+ "&CatalogType=" + catalog.getString("Type") + "&SiteID=" + site.getString("id") + "&Count=" + count + "\"></script>";
	}

	/**
	 * 评测讨论区js
	 *
	 * @param count
	 * @return
	 */
	public String getEvalComment(String count) {
		return "<script src=\"" + Config.getValue("ServicesContext") + Config.getValue("EvalCommentListJS") + "?RelaID=" + doc.getString("ID") + "&CatalogID=" + doc.getString("CatalogID")
				+ "&CatalogType=" + catalog.getString("Type") + "&SiteID=" + site.getString("id") + "&Count=" + count + "\"></script>";
	}
	/**
	 * 图片播放器
	 * 
	 * @param name
	 * @return
	 */
	public String getImagePlayer(String name, String width, String height, String count, String charwidth) {
		if (StringUtil.isEmpty(charwidth) || "null".equalsIgnoreCase(charwidth)) {
			charwidth = "100";
		}
		String cunDate = DateUtil.getCurrentDateTime();
		StringBuffer sb = new StringBuffer();
		ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
		imagePlayer.setName(name);
		imagePlayer.setSiteID(site.getString("id"));
		ZCImagePlayerSet set = imagePlayer.query();
		if (set.size() > 0) {
			imagePlayer = set.get(0);
		} else {
			imagePlayer = new ZCImagePlayerSchema();
			imagePlayer.setCode(name);
			imagePlayer.setSiteID(site.getString("id"));
			set = imagePlayer.query();
			if (set.size() > 0) {
				imagePlayer = set.get(0);
			} else {
				sb.append("没有" + name + "对应的图片播放器，请检查" + name + "是否正确。");
				return sb.toString();
			}
		}

		int imageCount = 6;
		if (StringUtil.isDigit(count)) {
			imageCount = Integer.parseInt(count);
		}

		StringBuffer pics = new StringBuffer();// 图片
		StringBuffer links = new StringBuffer();// 链接
		StringBuffer texts = new StringBuffer();// 显示文本
		String path = Config.getValue("StaticResourcePath");
		if(!path.endsWith("/")){
			path = path+"/";
		}
		DataTable dt = null;

		// 和栏目关联，取文章里面的图片
		if (ImagePlayerBasic.IMAGESOURCE_CATALOG_FIRST.equals(imagePlayer.getImageSource())) {
			String catalogStr = " and cataloginnercode like '%'";
			if (StringUtil.isNotEmpty(imagePlayer.getRelaCatalogInnerCode()) && !"null".equalsIgnoreCase(imagePlayer.getRelaCatalogInnerCode())) {
				catalogStr = " and cataloginnercode like '" + imagePlayer.getRelaCatalogInnerCode() + "%'";
			}

			String statusStr = " and status in(" + Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED + ") and (publishdate<='" + DateUtil.getCurrentDateTime() + "' or publishdate is null)";

			String attributeSql = " and attribute like '%image%'";
			String typeStr = " order by publishdate desc,orderflag desc, id desc";
			dt = new QueryBuilder("select * from zcarticle where siteID=?" + catalogStr + statusStr + attributeSql + typeStr, imagePlayer.getSiteID()).executePagedDataTable(imageCount, 0);
			dt.insertColumns(new String[] { "FirstImagePath" });
			PubFun.dealArticleMedia(dt, getLevelStr(), "image");

			for (int i = 0; i < dt.getRowCount(); i++) {
				String imagePath = dt.getString(i, "FirstImagePath");
				if (StringUtil.isEmpty(imagePath) || imagePath.toLowerCase().indexOf("nopicture.jpg") >= 0) {
					continue;// 没有图片不显示
				}
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				imagePath = dt.getString(i, "FirstImagePath").substring(dt.getString(i, "FirstImagePath").indexOf("upload/"));
				pics.append(path + imagePath);
				String siteUrl = SiteUtil.getURL(dt.getString(i, "SiteID"));
				if (siteUrl.endsWith("shtml")) {
					siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
				}

				if (!siteUrl.endsWith("/")) {
					siteUrl = siteUrl + "/";
				}

				links.append(siteUrl + PubFun.getDocURL(dt.getDataRow(i)));
				if (StringUtil.isNotEmpty(dt.getString(i, "ShortTitle"))) {
					texts.append(StringUtil.subStringEx(dt.getString(i, "ShortTitle"), Integer.parseInt(charwidth)));
				} else {
					texts.append(StringUtil.subStringEx(dt.getString(i, "Title"), Integer.parseInt(charwidth)));
				}
			}
		} else { // 自选图片
			String sql = "select b.* from ZCImageRela a,zcimage b where a.id = b.id  and a.RelaID=" + imagePlayer.getID() + " and a.RelaType='" + ImagePlayerRela.RELATYPE_IMAGEPLAYER
					+ "' order by a.orderflag desc, a.addtime desc";
			dt = new QueryBuilder(sql).executePagedDataTable(imageCount, 0);
			boolean flag = false;
			boolean mark = false;
			String endDateRecord = "";
			int countNo = 0;
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (i != 0) {
					if(flag){
						pics.append(SEPARATE);
						links.append(SEPARATE);
						texts.append(SEPARATE);
					}
				}
				flag = false;
				String startDate = dt.getString(i, "Prop3");
				String endDate = dt.getString(i, "Prop4");
				if(StringUtil.isNotEmpty(endDateRecord) && StringUtil.isNotEmpty(endDate)){
					countNo = DateUtil.compare(endDate, endDateRecord, "yyyy-MM-dd HH:mm:ss")==-1?countNo:i;
				}
				endDateRecord = dt.getString(i, "Prop4");
					if(!StringUtil.isEmpty(startDate)){
						if(DateUtil.compare(cunDate, startDate, "yyyy-MM-dd HH:mm:ss")==1){
							if(!StringUtil.isEmpty(endDate)){
								if(DateUtil.compare(cunDate, endDate, "yyyy-MM-dd HH:mm:ss")==-1){
									flag = true;
									mark = true;
								}
							}else{
								flag = true;
								mark = true;
							}
						}
					}else{
						flag = true;
						mark = true;
					}
				if(i==dt.getRowCount()-1 && !mark){
					if(StringUtil.isEmpty(imagePlayer.getOriginalPicture())
							|| "N".equalsIgnoreCase(imagePlayer
									.getOriginalPicture())){
						pics.append(path + dt.getString(countNo, "path") + "1_" + dt.getString(countNo, "FileName"));
						
					}else{
						pics.append(path + dt.getString(countNo, "path") + "" + dt.getString(countNo, "SrcFileName"));
					}
					
					if (StringUtil.isNotEmpty(getLevelStr() + dt.getString(countNo, "LinkURL"))) {
						links.append(dt.getString(countNo, "LinkURL"));
					}
					if (StringUtil.isNotEmpty(dt.getString(countNo, "LinkText"))) {
						texts.append(StringUtil.subStringEx(dt.getString(countNo, "LinkText"), Integer.parseInt(charwidth)));
					}
				}
				if(flag){
					if(StringUtil.isEmpty(imagePlayer.getOriginalPicture())
							|| "N".equalsIgnoreCase(imagePlayer
									.getOriginalPicture())){
						pics.append(path + dt.getString(i, "path") + "1_" + dt.getString(i, "FileName"));
						
					}else{
						pics.append(path + dt.getString(i, "path") + "" + dt.getString(i, "SrcFileName"));
					}
					
					if (StringUtil.isNotEmpty(getLevelStr() + dt.getString(i, "LinkURL"))) {
						links.append(dt.getString(i, "LinkURL"));
					}
					if (StringUtil.isNotEmpty(dt.getString(i, "LinkText"))) {
						texts.append(StringUtil.subStringEx(dt.getString(i, "LinkText"), Integer.parseInt(charwidth)));
					}
				}
				
			}
		}

		int pWidth = 320, pHeight = 240;
		if (!StringUtil.isDigit(width)) {
			pWidth = (int) imagePlayer.getWidth();
		} else {
			pWidth = Integer.parseInt(width);
		}

		if (!StringUtil.isDigit(height)) {
			pHeight = (int) imagePlayer.getHeight();
		} else {
			pHeight = Integer.parseInt(height);
		}

		int imgHeight = pHeight;
		if ("Y".equals(imagePlayer.getIsShowText())) {
			imgHeight = pHeight - 22;
		}
		String showText = "";
		if ("Y".equals(imagePlayer.getIsShowText())) {
			showText = "&show_text=1&textheight=22";
		} else {
			showText = "&show_text=0";
		}
		//首页图片播放器
		if("index_ad".equals(imagePlayer.getCode())){

           
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				String Alias = "/";
				String imgpath = "";
				String imglink = "";
				
				if (StringUtil.isEmpty(imagePlayer.getOriginalPicture()) || "N".equalsIgnoreCase(imagePlayer.getOriginalPicture())) {
					imgpath = path + Alias + dt.getString(i, "path") + "1_" + dt.getString(i, "FileName");
				} else {
					imgpath = path + Alias + dt.getString(i, "path") + "" + dt.getString(i, "SrcFileName");
				}
				if (StringUtil.isNotEmpty(Alias + dt.getString(i, "LinkURL"))) {
					imglink = dt.getString(i, "LinkURL");
				}

				sb.append("<div class=\"swiper-slide\" >");
	            if(i==0){
	            	sb.append(" <a href=\"" + imglink + "\" target=\"_blank\"><img class=\"lazy\" src=\"" + imgpath + "\"   /></a>");
	            }else{
	            	sb.append(" <a href=\"" + imglink + "\" target=\"_blank\"><img class=\"lazy\" data-original=\"" + imgpath + "\"   /></a>");
	            }
	            
	            sb.append("</div>");
			}

			
            
            return sb.toString();
		
		}
		if ("01".equals(imagePlayer.getRemark1())) {
			String picsStr = pics.toString();
			String linksStr = links.toString();
			String textsStr = texts.toString();
			
			if(picsStr.endsWith(SEPARATE)){
				picsStr = picsStr.substring(0, picsStr.length()-1);
			}
			if(linksStr.endsWith(SEPARATE)){
				linksStr = linksStr.substring(0, linksStr.length()-1);
			}
			if(textsStr.endsWith(SEPARATE)){
				textsStr = textsStr.substring(0, textsStr.length()-1);
			}
			sb.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"" + pWidth + "\" height=\"" + pHeight + "\">");
			sb.append("<param name=\"movie\" value=\"" + getLevelStr() + "images/ImagePlayer.swf\">");
			sb.append("<param name=\"quality\" value=\"high\">");
			sb.append("<param name=\"wmode\" value=\"transparent\">");
			sb.append("<param name=\"FlashVars\" value=\"pics=" + picsStr + "&links=" + linksStr + "&texts=" + textsStr + "&borderwidth=" + pWidth + "&borderheight="
					+ imgHeight + showText + "&overtxtcolor=FFFF00&txtcolor=FFFF00\"/>");
			sb.append("<embed src=\"" + path + "images/ImagePlayer.swf\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" " + "FlashVars=\"wmode=transparent&pics="
					+ picsStr + "&links=" + linksStr + "&texts=" + textsStr + "&borderwidth=" + pWidth + "&borderheight=" + imgHeight + showText);

			sb.append("&button_pos=4&overtxtcolor=FFFF00&txtcolor=FFFF00\" width=\"" + pWidth + "\" height=\"" + pHeight + "\">");
			sb.append("</embed>");
			sb.append("</object>");

		} else if ("02".equals(imagePlayer.getRemark1())) {
			sb.append("<div class=\"ad_jiaodian\">\n");
			sb.append("<div class=\"changeBox_a1\" id=\"change_33\">\n");
			String sql2 = "select * from ZCImagePlayer where ID=" + imagePlayer.getID() + "";
			QueryBuilder qb2 = new QueryBuilder(sql2);
			DataTable dt2 = qb2.executeDataTable();
			boolean flag1 ;
			boolean mark1 = false;
			String endDateRecord = "";
			int countNo = 0;
			for (int i = 0; i < dt.getRowCount(); i++) {
				flag1 = false;
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				
					String startDate = dt.getString(i, "Prop3");
					String endDate = dt.getString(i, "Prop4");
					if(StringUtil.isNotEmpty(endDateRecord) && StringUtil.isNotEmpty(endDate)){
						countNo = DateUtil.compare(endDate, endDateRecord, "yyyy-MM-dd HH:mm:ss")==-1?countNo:i;
					}
					endDateRecord = dt.getString(i, "Prop4");
				if(!StringUtil.isEmpty(startDate)){
						if(DateUtil.compare(cunDate, startDate, "yyyy-MM-dd HH:mm:ss")==1){
							if(!StringUtil.isEmpty(endDate)){
								if(DateUtil.compare(cunDate, endDate, "yyyy-MM-dd HH:mm:ss")==-1){
									flag1 = true;
									mark1 = true;
								}
							}else{
								flag1 = true;
								mark1 = true;
							}
						}
				}else{
						flag1 = true;
						mark1 = true;
				}
				String Alias = "/";
				String imgpath = "";
				String imglink = "";
				if(i==dt.getRowCount()-1 && !mark1){
					if (StringUtil.isEmpty(imagePlayer.getOriginalPicture()) || "N".equalsIgnoreCase(imagePlayer.getOriginalPicture())) {
						imgpath = path + Alias + dt.getString(countNo, "path") + "1_" + dt.getString(countNo, "FileName");
					} else {
						imgpath = path + Alias + dt.getString(countNo, "path") + "" + dt.getString(countNo, "SrcFileName");
					}
					if (StringUtil.isNotEmpty(Alias + dt.getString(countNo, "LinkURL"))) {
						imglink = dt.getString(countNo, "LinkURL");
					}
					sb.append("<div class=\"changeDiv\">\n");
					String text = "";
					if ("Y".equals(imagePlayer.getIsShowText())) {
						text = dt.getString(countNo, "Info");
					}
					if ("Y".equals(imagePlayer.getIsShowNofollow())) {
						sb.append("<a href=\"" + imglink + "\"  rel=\"nofollow\" target=\"_blank\">\n");
					} else {
						sb.append("<a href=\"" + imglink + "\" target=\"_blank\">\n");
					}
					sb.append("<img src=\"" + imgpath + "\" width=\"" + dt2.getString(0, "Width") + "\" height=\"" + dt2.getString(0, "Height") + "\" alt=\"" + text + "\" /></a>\n");
					sb.append("</div>\n");
				}
				
					if(flag1){
						if (StringUtil.isEmpty(imagePlayer.getOriginalPicture()) || "N".equalsIgnoreCase(imagePlayer.getOriginalPicture())) {
							imgpath = path + Alias + dt.getString(i, "path") + "1_" + dt.getString(i, "FileName");
						} else {
							imgpath = path + Alias + dt.getString(i, "path") + "" + dt.getString(i, "SrcFileName");
						}
						if (StringUtil.isNotEmpty(Alias + dt.getString(i, "LinkURL"))) {
							imglink = dt.getString(i, "LinkURL");
						}
						sb.append("<div class=\"changeDiv\">\n");
						String text = "";
						if ("Y".equals(imagePlayer.getIsShowText())) {
							text = dt.getString(i, "Info");
						}
						if ("Y".equals(imagePlayer.getIsShowNofollow())) {
							sb.append("<a href=\"" + imglink + "\"  rel=\"nofollow\" target=\"_blank\">\n");
						} else {
							sb.append("<a href=\"" + imglink + "\" target=\"_blank\">\n");
						}
						sb.append("<img src=\"" + imgpath + "\" width=\"" + dt2.getString(0, "Width") + "\" height=\"" + dt2.getString(0, "Height") + "\" alt=\"" + text + "\" /></a>\n");
						sb.append("</div>\n");
					}
				}
				
			if (dt.getRowCount() > 0) {
				sb.append("<ul class=\"ul_change_a2\">\n");
				boolean flag4;
				int countName = 0;
				for (int i = 0; i < dt.getRowCount(); i++) {
					flag4=false;
					if(i==dt.getRowCount()-1 && countName==0){
						flag4 = true;
					}else{
						String startDate = dt.getString(i, "Prop3");
						String endDate = dt.getString(i, "Prop4");
						if(!StringUtil.isEmpty(startDate)){
							if(DateUtil.compare(cunDate, startDate, "yyyy-MM-dd HH:mm:ss")==1){
								if(!StringUtil.isEmpty(endDate)){
									if(DateUtil.compare(cunDate, endDate, "yyyy-MM-dd HH:mm:ss")==-1){
										flag4 = true;
									}
								}else{
									flag4 = true;
								}
							}
						}else{
							flag4 = true;
						}
					}
					if(flag4){
						countName++;
						sb.append("<li><span>" + countName + "</span></li>\n");
					}
				}
				sb.append("</ul>\n");
			}
			sb.append("</div>\n");
			sb.append("</div>\n");

			// 03.动态广告位-Flash 播放
		} else if ("03".equals(imagePlayer.getRemark1())) {

			String reslutStr = "<script type=\"text/javascript\" src=\"" + Config.getValue("ServicesContext") + Config.getValue("dynImages") + "?dynName=" + name + "&dynWidth=" + width
					+ "&dynHeight=" + height + "&dynCount=" + count + "&dCharwidth=" + charwidth + "\"></script>";

			return reslutStr;

			// 04.动态广告位-DIV 播放
		} else if ("04".equals(imagePlayer.getRemark1())) {

			String reslutStr = "<script type=\"text/javascript\" src=\"" + Config.getValue("ServicesContext") + Config.getValue("dynImages") + "?dynName=" + name + "&dynWidth=" + width
					+ "&dynHeight=" + height + "&dynCount=" + count + "&dCharwidth=" + charwidth + "\"></script>";
			return reslutStr;

		} else if ("05".equals(imagePlayer.getRemark1())) {
			sb.append("<div class=\"ad_jiaodian\">\n");
			sb.append("<div class=\"changeBox_a1\" id=\"change_33\">\n");
			String sql2 = "select * from ZCImagePlayer where ID=" + imagePlayer.getID() + "";
			QueryBuilder qb2 = new QueryBuilder(sql2);
			DataTable dt2 = qb2.executeDataTable();
			boolean flag2;
			boolean mark2 = false;
			String endDateRecord = "";
			int countNo = 0; 
			for (int i = 0; i < dt.getRowCount(); i++) {
				flag2 = false;
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				String Alias = "/";
				String imgpath = "";
				String imglink = "";
				
					String startDate = dt.getString(i, "Prop3");
					String endDate = dt.getString(i, "Prop4");
					if(StringUtil.isNotEmpty(endDateRecord) && StringUtil.isNotEmpty(endDate)){
						countNo = DateUtil.compare(endDate, endDateRecord, "yyyy-MM-dd HH:mm:ss")==-1?countNo:i;
					}
							
					endDateRecord = dt.getString(i, "Prop4");
					if(!StringUtil.isEmpty(startDate)){
						if(DateUtil.compare(cunDate, startDate, "yyyy-MM-dd HH:mm:ss")==1){
							if(!StringUtil.isEmpty(endDate)){
								if(DateUtil.compare(cunDate, endDate, "yyyy-MM-dd HH:mm:ss")==-1){
									flag2 = true;
									mark2 = true;
								}
							}else{
								flag2 = true;
								mark2 = true;
							}
						}
					}else{
						flag2 = true;
						mark2 = true;
					}
					if(i==dt.getRowCount()-1 && !mark2){
						if (StringUtil.isEmpty(imagePlayer.getOriginalPicture()) || "N".equalsIgnoreCase(imagePlayer.getOriginalPicture())) {
							imgpath = path + Alias + dt.getString(countNo, "path") + "1_" + dt.getString(countNo, "FileName");
						} else {
							imgpath = path + Alias + dt.getString(countNo, "path") + "" + dt.getString(countNo, "SrcFileName");
						}
						if (StringUtil.isNotEmpty(Alias + dt.getString(countNo, "LinkURL"))) {
							imglink = dt.getString(countNo, "LinkURL");
						}
						sb.append("<div class=\"changeDiv\">\n");
						String text = "";
						if ("Y".equals(imagePlayer.getIsShowText())) {
							text = dt.getString(countNo, "Info");
						}
						if ("Y".equals(imagePlayer.getIsShowNofollow())) {
							sb.append("<a href=\"" + imglink + "\"  rel=\"nofollow\" target=\"_blank\">\n");
						} else {
							sb.append("<a href=\"" + imglink + "\" target=\"_blank\">\n");
						}
						sb.append("<img src=\"" + imgpath + "\" width=\"" + dt2.getString(0, "Width") + "\" height=\"" + dt2.getString(0, "Height") + "\" alt=\"" + text + "\" /></a>\n");
						sb.append("</div>\n");
					}
				if(flag2){
					if (StringUtil.isEmpty(imagePlayer.getOriginalPicture()) || "N".equalsIgnoreCase(imagePlayer.getOriginalPicture())) {
						imgpath = path + Alias + dt.getString(i, "path") + "1_" + dt.getString(i, "FileName");
					} else {
						imgpath = path + Alias + dt.getString(i, "path") + "" + dt.getString(i, "SrcFileName");
					}
					if (StringUtil.isNotEmpty(Alias + dt.getString(i, "LinkURL"))) {
						imglink = dt.getString(i, "LinkURL");
					}
					sb.append("<div class=\"changeDiv\">\n");
					String text = "";
					if ("Y".equals(imagePlayer.getIsShowText())) {
						text = dt.getString(i, "Info");
					}
					if ("Y".equals(imagePlayer.getIsShowNofollow())) {
						sb.append("<a href=\"" + imglink + "\"  rel=\"nofollow\" target=\"_blank\">\n");
					} else {
						sb.append("<a href=\"" + imglink + "\" target=\"_blank\">\n");
					}
					sb.append("<img src=\"" + imgpath + "\" width=\"" + dt2.getString(0, "Width") + "\" height=\"" + dt2.getString(0, "Height") + "\" alt=\"" + text + "\" /></a>\n");
					sb.append("</div>\n");
				}
			}
			if (dt.getRowCount() > 0) {
				sb.append("<ul class=\"ul_change_a2\">\n");
				int tCount = dt.getRowCount();
				double tWidth = 0;
				switch (tCount) {
				case 1:
					tWidth = 928;
					break;
				case 2:
					tWidth = 454;
					break;
				case 3:
					tWidth = 294.6;
					break;
				case 4:
					tWidth = 216;
					break;
				case 5:
					tWidth = 169;
					break;
				case 6:
					tWidth = 137.1;
					break;
				default:
					tWidth = 100;
				}
				boolean flag3;
				boolean mark3 = false;
				for (int i = 0; i < dt.getRowCount(); i++) {
					flag3=false;
					if(i==dt.getRowCount()-1 && !mark3){
						flag3 = true;
					}else{
						String startDate = dt.getString(i, "Prop3");
						String endDate = dt.getString(i, "Prop4");
						if(!StringUtil.isEmpty(startDate)){
							if(DateUtil.compare(cunDate, startDate, "yyyy-MM-dd HH:mm:ss")==1){
								if(!StringUtil.isEmpty(endDate)){
									if(DateUtil.compare(cunDate, endDate, "yyyy-MM-dd HH:mm:ss")==-1){
										flag3 = true;
									}
								}else{
									flag3 = true;
								}
							}
						}else{
							flag3 = true;
						}
					}
					if(flag3){
						mark3 = true;
						sb.append("<li><span style=\"width:" + tWidth + "px\"><em class=\"change_j\"></em>" + dt.getString(i, "Name") + "</span></li>\n");
					}

				}
				sb.append("</ul>\n");
			}
			sb.append(" <a title=\"上一个\" class=\"a_last\" href=\"#\">上一个</a> ");
			sb.append(" <a title=\"下一个\" class=\"a_next\" href=\"#\">下一个</a> ");
			sb.append("</div>\n");
			sb.append("</div>\n");

			// 03.动态广告位-Flash 播放
		}
		return sb.toString(); 
	}
	
	public String getLinkURL(String type, String code, String name, String spliter) {
		StringBuffer sb = new StringBuffer();
		if ("catalog".equalsIgnoreCase(type)) {
			String levelString = "";
			for (int i = 0; i < level; i++) {
				levelString += "../";
			}
			if (code != null && !"".equals(code) && !"null".equals(code)) {
				ZCCatalogSchema catalog = new ZCCatalogSchema();
				catalog.setAlias(code);
				catalog.setSiteID(Long.parseLong(site.getString("ID")));
				ZCCatalogSet set = catalog.query();
				if (set.size() < 1) {
					return "#";
				} else {
					catalog = set.get(0);
					return CatalogUtil.getLink(catalog.getID(), levelString);
				}
			} else if (name != null && !"".equals(name) && !"null".equals(name)) {
				ZCCatalogSchema catalog = null;
				if (StringUtil.isDigit(name)) {
					catalog = CatalogUtil.getSchema(Long.parseLong(name));
				} else {
					// 通过栏目名称找到栏目ID
					String id = CatalogUtil.getIDByNames(site.getString("ID"), name);
					catalog = CatalogUtil.getSchema(Long.parseLong(id));
				}
				if (catalog == null) {
					return "#";
				} else {
					return CatalogUtil.getLink(catalog.getID(), levelString);
				}
			}
		} else if ("article".equalsIgnoreCase(type)) {
			String levelString = "";
			for (int i = 0; i < level; i++) {
				levelString += "../";
			}
			if (name != null && !"".equals(name) && !"null".equals(name)) {
				ZCArticleSchema article = new ZCArticleSchema();
				if (StringUtil.isDigit(name)) {
					article.setID(Long.parseLong(name));
				} else {
					// name的形式形如：文章标题@新闻/国际新闻
					if (name.indexOf("@") != -1) {
						String title = name.substring(0, name.indexOf("@"));
						name = name.substring(name.indexOf("@") + 1);
						String catlaogID = null;
						if (name.indexOf("/") != -1) {
							catlaogID = CatalogUtil.getIDByNames(site.getString("ID"), name);
						} else {
							catlaogID = CatalogUtil.getIDByName(site.getString("ID"), name);
						}
						if (StringUtil.isNotEmpty(catlaogID)) {
							article.setCatalogID(catlaogID);
						}
						article.setTitle(title);
					} else {
						article.setTitle(name);
					}
				}

				ZCArticleSet set = article.query();
				if (set != null && set.size() > 0) {
					article = set.get(0);
					sb.append(levelString + PubFun.getArticleURL(article));
				}
			}
		} else if ("CurrentPosition".equalsIgnoreCase(type)) {
			if (spliter == null || "".equals(spliter) || "null".equals(spliter)) {
				spliter = ">";
			}

			Object obj = catalog.get("ID");
			long catalogID = (obj == null) ? 0 : ((Long) obj).longValue();
			sb.append(PubFun.getCurrentPage(catalogID, this.level, name, spliter, "_self"));
		} else if ("CurrentPositionList".equalsIgnoreCase(type)) {
			if (spliter == null || "".equals(spliter) || "null".equals(spliter)) {
				spliter = ">";
			}

			Object obj = catalog.get("ID");
			long catalogID = (obj == null) ? 0 : ((Long) obj).longValue();
			sb.append(PubFun.getCurrentPage(catalogID, this.level, name, spliter, "_self", "F"));
		} else if ("HomeURL".equalsIgnoreCase(type)) {
			if (StringUtil.isEmpty(name) || "null".equalsIgnoreCase(name)) {
				name = "您现在的位置：首页";
			}
			sb.append("<a href='" + this.getLevelStr() + "'>" + name + "</a>");
		} else if ("MessageBoard".equalsIgnoreCase(type)) {
			String BoardID = "";
			if (StringUtil.isNotEmpty(name)) {
				BoardID = new QueryBuilder("select ID from ZCMessageBoard where Name = ? and SiteID = ?", name, site.getLong("ID")).executeString();
			}
			sb.append(Config.getValue("ServicesContext") + "/" + "MessageBoardPage.jsp?BoardID=" + BoardID);
		}
		return sb.toString();
	}

	public DataRow getCatalog(String type, String name) {
		DataRow docRow = null;
		if ("catalog".equalsIgnoreCase(type)) {
			String levelString = "";
			for (int i = 0; i < level; i++) {
				levelString += "../";
			}
			if (name != null && !"".equals(name) && !"null".equals(name)) {
				ZCCatalogSchema catalog = new ZCCatalogSchema();
				String id;
				// 修改处理优先顺序
				if (StringUtil.isDigit(name)) {
					id = name;
				} else if (name.indexOf("/") != -1) {
					id = CatalogUtil.getIDByNames(site.getString("ID"), name);
				} else {
					id = CatalogUtil.getIDByName(site.getString("ID"), name);
				}

				catalog.setID(Long.parseLong(id));

				if (!catalog.fill()) {
					return null;
				} else {
					docRow = catalog.toDataRow();
					docRow.insertColumn("link", CatalogUtil.getLink(catalog.getID(), levelString));
				}
			}

			ColumnUtil.extendCatalogColumnData(docRow, site.getLong("ID"), this.getLevelStr());
		}
		return docRow;
	}

	public DataRow getDocument(String type, String name) {
		DataRow docRow = null;
		if ("article".equalsIgnoreCase(type)) {
			String levelString = "";
			for (int i = 0; i < level; i++) {
				levelString += "../";
			}
			if (name != null && !"".equals(name) && !"null".equals(name)) {
				ZCArticleSchema article = new ZCArticleSchema();
				if (StringUtil.isDigit(name)) {
					article.setID(Long.parseLong(name));
				} else {
					// name的形式形如：文章标题@新闻/国际新闻
					if (name.indexOf("@") != -1) {
						String title = name.substring(0, name.indexOf("@"));
						name = name.substring(name.indexOf("@") + 1);

						String catlaogID = null;
						if (name.indexOf("/") != -1) {
							catlaogID = CatalogUtil.getIDByNames(site.getString("ID"), name);
						} else {
							catlaogID = CatalogUtil.getIDByName(site.getString("ID"), name);
						}
						if (StringUtil.isNotEmpty(catlaogID)) {
							article.setCatalogID(catlaogID);
						}

						article.setTitle(title);
					} else {
						article.setTitle(name);
					}
				}

				ZCArticleSet set = article.query();
				if (set != null && set.size() > 0) {
					article = set.get(0);
					docRow = article.toDataRow();
					docRow.insertColumn("link", levelString + PubFun.getArticleURL(article));
				}
			}
		}
		return docRow;
	}

	public DataRow getCurrentIssue(String type, String name) {
		DataRow docRow = null;
		if ("magazine".equalsIgnoreCase(type)) {
			String levelString = "";
			for (int i = 0; i < level; i++) {
				levelString += "../";
			}
			String sql = "";
			QueryBuilder qb = new QueryBuilder();
			if (StringUtil.isEmpty(name)) {
				sql = "select * from ZCMagazine where siteID=?";
				qb.setSQL(sql);
				qb.add(site.getLong("ID"));
			} else {
				sql = "select * from ZCMagazine where siteID=? and name=?";
				qb.setSQL(sql);
				qb.add(site.getLong("ID"));
				qb.add(name);
			}
			DataTable magazineDT = qb.executeDataTable();

			if (magazineDT.getRowCount() > 0) {
				DataTable dt = new QueryBuilder("select * from ZCMagazineIssue where MagazineID=? and Year='" + magazineDT.getString(0, "CurrentYear") + "' and PeriodNum='"
						+ magazineDT.getString(0, "CurrentPeriodNum") + "'", magazineDT.getString(0, "ID")).executePagedDataTable(1, 0);
				if (dt != null && dt.getRowCount() > 0) {
					docRow = dt.get(0);
					String catalogName = docRow.getString("Year") + "年第" + docRow.getString("PeriodNum") + "期";
					String parentID = new QueryBuilder("select ID from zccatalog where type='" + Catalog.MAGAZINE + "' and name=? and siteID=? and parentID=0", name, site.getString("ID"))
							.executeString();
					DataTable catalogDT = new QueryBuilder("select * from zccatalog where type='" + Catalog.MAGAZINE + "' and name='" + catalogName + "' and siteID=? and parentID=?", site
							.getString("ID"), parentID).executePagedDataTable(1, 0);
					if (catalogDT != null && catalogDT.getRowCount() > 0) {
						docRow.insertColumn("CatalogInnerCode", catalogDT.getString(0, "InnerCode"));
						docRow.insertColumn("CatalogID", catalogDT.getString(0, "ID"));
						docRow.insertColumn("CatalogName", catalogDT.getString(0, "Name"));
					}
				}
			}
		}
		return docRow;
	}

	public String getPage(String type, String value, String name, String target) {
		StringBuffer sb = new StringBuffer();
		if ("currentpage".equalsIgnoreCase(type)) {
			if (value == null || "".equals(value) || "null".equals(value)) {
				value = ">";
			}

			if (target == null || "".equals(target) || "null".equals(target)) {
				value = "_self";
			}

			Object obj = catalog.get("ID");
			long catalogID = (obj == null) ? 0 : ((Long) obj).longValue();
			sb.append(PubFun.getCurrentPage(catalogID, this.level, name, value, target));
		} else if ("index".equalsIgnoreCase(type)) {
			sb.append(this.getLevelStr() + "index.shtml");
		}
		return sb.toString();
	}

	public DataTable getPagedDataTable(DataTable dt, int type) {
		int count = PageSize;
		if ((PageIndex + 1) * PageSize > Total) {
			count = Total - PageIndex * PageSize;
		}
		Object[][] values = new Object[count][dt.getColCount()];
		for (int i = 0; i < count; i++) {
			values[i] = dt.getDataRow(PageIndex * PageSize + i).getDataValues();
		}
		return new DataTable(dt.getDataColumns(), values);
	}

	/**
	 * <cms:list item="hotlabels"> 标签. 通过tag属性匹配TAG词.
	 * 
	 * @param type
	 *            . 1.lexicon 优先词库.
	 * @param count
	 * @param tag
	 * @return
	 */
	public DataTable getHotLabels(String type, String tag, int count, String cmsorder) {
		// type 1.lexicon TAG

		DataTable dt = null;
		// 文章发布缓存
		String key = "cmslist_" + cmsorder;
		if (article != null || StringUtil.isEmpty(cmsorder)) {
			if (!StringUtil.isEmpty(article.get(0)) && StringUtil.isNotEmpty(cmsorder)) {
				dt = mArticleCache.readCacheByDataTable(String.valueOf(article.get(0)), key);
				if (dt != null && dt.getRowCount() >= 1) {
					return dt;
				}
			}
		}
		try {
			String[] str = null;

			StringBuffer sb = new StringBuffer("select * from (select distinct * from zctag where 1 = 1 ");

			if (!StringUtil.isEmpty(tag)) {

				str = tag.split(" ");

				for (int i = 0; i < str.length; i++) {

					if (i == 0) {

						sb.append(" and tag like '%" + str[i] + "%'");

					} else {

						sb.append(" or tag like '%" + str[i] + "%'");

					}
				}

				tag = tag.replaceAll(" ", "','");

				sb.append(" order by  rand() limit " + (count + str.length) + ") a where a.tag not in ('" + tag + "')");

			} else {

				sb.append(" order by  rand() limit " + count + ") a ");

			}

			QueryBuilder qb = new QueryBuilder();

			qb.setSQL(sb.toString());

			dt = qb.executeDataTable();

			// 不足补齐（保留原有结果）
			if (dt.getRowCount() < count) {

				String parame = CombinationConditions(dt);

				StringBuffer sql = new StringBuffer("select distinct * from zctag where 1 = 1 ");

				if (!StringUtil.isEmpty(parame)) {
					sql.append(" and id not in (" + parame + ")");
				}

				sql.append(" order by  rand() limit " + (count - dt.getRowCount()));

				qb.setSQL(sql.toString());

				DataTable dtTemp = qb.executeDataTable();

				for (int i = 0; i < dtTemp.getRowCount(); i++) {

					dt.insertRow(dtTemp.get(i));

				}
			}

		} catch (Exception e) {
			logger.error(" <cms:list item=\"hotlabels\"> 解析标签错误：" + e.getMessage(), e);

		}

		// 标签词 链接(优先词库)
		if ("lexicon".equals(type)) {
			try {
				dt = TypeExtend(type, dt);
			} catch (Exception e) {
				logger.error(" <cms:list item=\"hotlabels\"> 解析标签错误 type:lexicon ：" + e.getMessage(), e);
			}
		}

		if (StringUtil.isNotEmpty(cmsorder)) {
			mArticleCache.writeCache(key, String.valueOf(article.get(0)), dt);
		}
		return dt;
	}

	/**
	 * Type属性扩展方法.
	 * 
	 * @param dt
	 * @return
	 * @throws Exception
	 */
	public DataTable TypeExtend(String type, DataTable dt) throws Exception {

		if ("lexicon".equals(type)) {

			for (int i = 0; i < dt.getRowCount(); i++) {

				String value = StringUtil.getLexiconLink(dt.getString(i, "Tag"));
				if (!StringUtil.isEmpty(value)) {
					dt.set(i, "LinkURL", value);
				}

			}

		}
		return dt;
	}

	/**
	 * 组合条件.
	 * 
	 * @param list
	 * @param count
	 * @return
	 */
	public String CombinationConditions(DataTable dt) {

		StringBuffer sb = new StringBuffer();

		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				sb.append(dt.getString(i, "ID") + ",");
			}

			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	/**
	 * <cms:list item="relazx"> 标签. 通过问答的tag检索相关资讯
	 * 
	 * @param type
	 * @param tag
	 * @param count
	 * @return
	 */
	public DataTable getZxArticle(String type, String tag, int count, String cmsorder) {
		// type 保留.
		DataTable dt = null;
		DataTable dtTemp = null;
		int intCount = count;
		String[] tagArr;
		String parame = "";
		QueryBuilder qb = new QueryBuilder();
		String sb = "select * from ZCArticle where " + " type = 1 and cataloginnercode like '002307%' and status =30 ";
		String strOrder = " order by FirstPublishDate desc, rand() limit ";

		// 文章发布缓存
		String key = "cmslist_" + cmsorder;
		if (article != null || StringUtil.isEmpty(cmsorder)) {
			if (!StringUtil.isEmpty(article.get(0)) && StringUtil.isNotEmpty(cmsorder)) {
				dt = mArticleCache.readCacheByDataTable(String.valueOf(article.get(0)), key);
				if (dt != null && dt.getRowCount() >= 1) {
					return dt;
				}
			}
		}
		// 通过tag词检索出符合相应资讯的条目
		if (!StringUtil.isEmpty(tag)) {
			String sq = "";
			tagArr = tag.split(" ");
			boolean result = false;
			// 按tag词顺序检索
			for (int i = 0; i < tagArr.length; i++) {
				sq = sb + "and tag like '%" + tagArr[i] + "%'";
				if (StringUtil.isNotEmpty(parame)) {
					sq += " and id not in (" + parame + ") ";
				}
				sq += strOrder + intCount;
				qb.setSQL(sq);
				dtTemp = qb.executeDataTable();

				// 不足补齐（保留原有结果）
				if (dtTemp.getRowCount() < intCount) {
					intCount = intCount - dtTemp.getRowCount();

				} else {
					result = true;
				}
				// 存储查询结果
				if (dt == null) {
					dt = dtTemp;
				} else {
					for (int j = 0; j < dtTemp.getRowCount(); j++) {
						dt.insertRow(dtTemp.get(j));
					}
				}
				// 满足条数 则跳出
				if (result) {
					intCount = 0;
					break;
				} else {
					// 记录已检出的ID
					parame = CombinationConditions(dt);
				}
			}
		}

		// 不足条数 检索分类属性是推荐的资讯
		if (intCount != 0) {
			String sq = sb + "and (Attribute like 'newRecommend%' " + "or Attribute like '%,newRecommend%') ";
			if (StringUtil.isNotEmpty(parame)) {
				sq += " and id not in (" + parame + ") ";
			}
			qb.setSQL(sq + strOrder + intCount);
			dtTemp = qb.executeDataTable();
			if (dt == null) {
				dt = dtTemp;
			} else {
				for (int j = 0; j < dtTemp.getRowCount(); j++) {
					dt.insertRow(dtTemp.get(j));
				}
			}
		}

		// 取得资讯文章的地址
		String levelString = getLevelStr();
		dt.insertColumn("Link");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String url = levelString + dt.get(i).getString("URL");
			dt.set(i, "Link", url);
		}
		if (StringUtil.isNotEmpty(cmsorder)) {
			mArticleCache.writeCache(key, String.valueOf(article.get(0)), dt);
		}
		return dt;
	}

	/**
	 * <cms:list item="attention"> 标签. 通过tag属性匹配专题.
	 * 
	 * @param type
	 * @param count
	 * @param tag
	 * @return
	 */
	public DataTable getAttentionArticle(String type, String tag, int count, String cmsorder) {
		// type 保留.
		DataTable dt = null;
		// 文章发布缓存
		String key = "cmslist_" + cmsorder;
		if (article != null || StringUtil.isEmpty(cmsorder)) {
			if (!StringUtil.isEmpty(article.get(0)) && StringUtil.isNotEmpty(cmsorder)) {
				dt = mArticleCache.readCacheByDataTable(String.valueOf(article.get(0)), key);
				if (dt != null && dt.getRowCount() >= 1) {
					return dt;
				}
			}
		}

		try {
			String[] str;

			QueryBuilder special_qb = new QueryBuilder("select codevalue from zdCode where parentcode = 'special'");

			DataTable special_dt = special_qb.executeDataTable();

			if (special_dt == null || special_dt.getRowCount() == 0) {
				logger.warn("attention标签异常.zdcode.special 未配置具体的栏目数据");
				return null;

			}

			String special = "";

			for (int i = 0; i < special_dt.getRowCount(); i++) {
				special += special_dt.get(i).getString("codevalue");

				if (i != special_dt.getRowCount() - 1) {
					special += ",";

				}
			}

			StringBuffer sb = new StringBuffer();

			sb.append("select z.* ");
			sb.append("from ZCArticle z ,zccatalog z1 ");
			sb.append("where z1.parentid in ( ? )  and z.type = '1' and  z.status = 30 ");

			if (!StringUtil.isEmpty(tag)) {

				str = tag.split(" ");

				sb.append("and ( ");

				for (int i = 0; i < str.length; i++) {

					if (i == 0) {
						sb.append("  z.title like '%" + str[i] + "%'");

					} else {
						sb.append(" or z.title like '%" + str[i] + "%'");

					}
				}

				sb.append("  ) ");
			}
			
			sb.append(" and z.catalogid = z1.id     " );
			
			sb.append(" order by  rand() limit " + count);

			QueryBuilder qb = new QueryBuilder();
			qb.add(special);
			qb.setSQL(sb.toString());

			dt = qb.executeDataTable();

			// 不足补齐（保留原有结果）
			if (dt.getRowCount() < count) {

				String parame = CombinationConditions(dt);

				StringBuffer sql = new StringBuffer();

				sql.append(" select z.*  ");
				sql.append(" from ZCArticle z ,zccatalog z1  ");
				sql.append(" where z1.parentid in ( ? )  and z.type = '1' and  z.status = 30 and z.catalogid = z1.id   ");

				if (!StringUtil.isEmpty(parame)) {
					sql.append(" and z1.id not in (" + parame + ") ");

				}
				
				sql.append(" and z.catalogid = z1.id   ");

				sql.append(" order by  rand() limit " + (count - dt.getRowCount()));

				QueryBuilder other_qb = new QueryBuilder();
				other_qb.add(special);
				other_qb.setSQL(sql.toString());

				DataTable dtTemp = other_qb.executeDataTable();

				for (int i = 0; i < dtTemp.getRowCount(); i++) {

					dt.insertRow(dtTemp.get(i));

				}
			}

		} catch (Exception e) {
			logger.error(" <cms:list item=\"attention\"> 解析标签错误：" + e.getMessage(), e);

		}
		if (StringUtil.isNotEmpty(cmsorder)) {
			mArticleCache.writeCache(key, String.valueOf(article.get(0)), dt);
			
		}
		return dt;
	}

	public String getLevelStr() {
		if (StringUtil.isEmpty(levelStr)) {
			levelStr = PubFun.getLevelStr(level);
		}
		return levelStr;
	}

	public static DataTable getSiteMap(long siteID) {
		return getSiteMap(siteID + "");
	}

	public static DataTable getSiteMap(String siteID) {
		if (!StringUtil.checkID(siteID)) {
			return null;
		}

		DataTable dt = new QueryBuilder("select treelevel,innercode,name,id,orderflag from ZCCatalog where siteID=?" + " and type=1  order by orderflag,innercode", Long.parseLong(siteID))
				.executeDataTable();

		dt.insertColumn("Link");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String url = CatalogUtil.getLink(dt.getLong(i, "ID"), PubFun.getLevelStr(CatalogUtil.getLevel(dt.getLong(i, "ID"))));
			if (dt.getString(i, "url").startsWith("http") || dt.getString(i, "url").startsWith("/") || dt.getString(i, "url").startsWith("https")) {
				url = dt.getString(i, "url");
			}
			dt.set(i, "Link", url);
		}
		return dt;
	}

	public DataTable getCustomData(String tableName, String CountStr, String condition, String cmsorder) {
		int count = 20;
		if (StringUtil.isDigit(CountStr)) {
			count = Integer.parseInt(CountStr);
		}
		String wherePart = "where 1=1 ";
		if (StringUtil.isNotEmpty(condition) && !condition.equals("null")) {
			wherePart += condition;
		}
		return CustomTableUtil.getData(tableName, new QueryBuilder(wherePart), count, 0);

	}

	public DataTable getKeywords(String type, String CountStr, String cmsorder) {
		int count = 20;
		if (StringUtil.isDigit(CountStr)) {
			count = Integer.parseInt(CountStr);
		}
		if (StringUtil.isEmpty(type)) {
			type += "week";
		}

		Date today = new Date();

		int days = -1;
		if ("day".equals(type)) {
			days = -1;
		} else if ("week".equals(type)) {
			days = -7;
		} else if ("month".equals(type)) {
			days = -31;
		} else if ("season".equals(type)) {
			days = -90;
		} else if ("year".equals(type)) {
			days = -365;
		}
		Date startDate = DateUtil.addDay(today, days);
		return SourceReport.getKeywordData(site.getLong("id"), startDate, today, count);

	}

	/**
	 * 获取文章分页标题及分页内容
	 * 
	 * @return
	 */
	public DataTable getArticlePages() {
		if (this.doc == null) {
			return null;
		} else {
			String[] titles = doc.getString("PageTitle").split("\\" + SEPARATE, -1);
			String[] contents = doc.getString("FullContent").split(Constant.PAGE_BREAK, -1);

			if (titles.length != contents.length) {
				return null;
			}

			DataTable dt = new DataTable();
			DataColumn columns[] = { new DataColumn("rownum", DataColumn.STRING), new DataColumn("title", DataColumn.STRING), new DataColumn("content", DataColumn.STRING) };
			String[] rowNum = new String[titles.length];
			for (int i = 0; i < rowNum.length; i++) {
				String[] rowValues = { (i + 1) + "", titles[i], contents[i] };
				DataRow row = new DataRow(columns, rowValues);
				rowNum[i] = (i + 1) + "";
				dt.insertRow(row);
			}
			return dt;
		}
	}

	public void dealTag(DataTable dtArticle) {
		String searchUrl = getSearchURL();
		searchUrl = StringUtil.replaceEx(searchUrl, "Result.jsp", "Tag.jsp");
		for (int j = 0; j < dtArticle.getRowCount(); j++) {
			String tag = dtArticle.getString(j, "Tag");
			if (StringUtil.isNotEmpty(tag)) {
				String[] tags = tag.split("\\s");
				tag = tags[0];
				ZCTagSchema tagSchema = (ZCTagSchema) SiteUtil.getTag(dtArticle.getLong(j, "SiteID"), tag);
				if (tagSchema == null) {
					continue;
				}
				String tagUrl = tagSchema.getLinkURL();
				if (StringUtil.isEmpty(tagUrl)) {
					tagUrl = searchUrl + "?site=" + dtArticle.getLong(j, "SiteID") + "&query=%00" + StringUtil.urlEncode(tag, "UTF-8");
				}
				dtArticle.set(j, "FirstTag", tag);
				dtArticle.set(j, "FirstTagUrl", tagUrl);
			}
		}
	}

	public String getSearchURL() {
		String serviceUrl = Config.getValue("ServicesContext");
		String context = serviceUrl;
		if (serviceUrl.endsWith("/")) {
			context = serviceUrl.substring(0, serviceUrl.length() - 1);
		}
		int index = context.lastIndexOf('/');
		if (index != -1) {
			context = context.substring(0, index);
		}
		String searchUrl = context + "/Search/Result.jsp";// 搜索路径
		return searchUrl;
	}

	public int getTotal(String type, long catlaogID) {
		int count = new QueryBuilder("select count(*) from zc" + type + " where catalogid=" + catlaogID + " and status=30").executeInt();
		return count;
	}

	public DataRow getBlock() {
		return block;
	}

	public void setBlock(DataRow block) {
		this.block = block;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 定制的ListTable 
	 */
	private DataTable customDataTable = null;

	/**
	 * 定制ListTable
	 */
	public void setCustomListTable(DataTable customDataTable) {
		this.customDataTable = customDataTable;
	}

	/**
	 * 获取定制的ListTable
	 */
	public DataTable getCustomListTable() {
		return this.customDataTable;
	}
}
