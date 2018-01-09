package com.sinosoft.cms.datachannel;

import java.util.ArrayList;
import java.util.Date;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.publish.AutomaticPublishCatalog;
import com.sinosoft.cms.resource.ConfigImageLib;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserList;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCAttachmentSchema;
import com.sinosoft.schema.ZCAudioSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;
import com.sinosoft.schema.ZCVideoSchema;
import com.sinosoft.schema.ZSGoodsSchema;
import com.sinosoft.schema.ZSGoodsSet;

public class Publisher {

	/**
	 * 生成全站所有页面，覆盖原有所有文件
	 * 
	 * @param siteID
	 * @return
	 */
	public boolean publishAll(long siteID) {
		PageGenerator p = new PageGenerator();
		if (p.staticSite(siteID)) {
			Deploy d = new Deploy();
			d.addJobs(siteID, p.getFileList());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成站点所有页面，同时提供进度条调用task
	 * 
	 * @param siteID
	 * @param task
	 * @return
	 */
	public boolean publishAll(long siteID, LongTimeTask task) {
		PageGenerator p = new PageGenerator(task);
		if (p.staticSite(siteID)) {
			Deploy d = new Deploy();
			d.addJobs(siteID, p.getFileList());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成站点首页
	 * 
	 * @param siteID
	 * @return
	 */
	public boolean publishIndex(long siteID, LongTimeTask task) {
		PageGenerator p = new PageGenerator(task);
		if (p.staticSiteIndex(siteID)) {
			Deploy d = new Deploy();
			d.addJobs(siteID, p.getFileList());
			return true;
		} else {
			return false;
		}
	}

	public boolean publishIndex(long siteID) {
		PageGenerator p = new PageGenerator();
		if (p.staticSiteIndex(siteID)) {
			Deploy d = new Deploy();
			d.addJobs(siteID, p.getFileList());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 发布文章 同时发布文章对应的列表页
	 * 
	 * @param articleSet
	 * @return
	 */
	public boolean publishArticle(ZCArticleSet articleSet, boolean generateCatalog, LongTimeTask task) {
		if (articleSet == null || articleSet.size() == 0) {
			return true;
		}
		Transaction trans = new Transaction();
		if (publishArticle(trans, articleSet, generateCatalog, task)) {
			if (!trans.commit()) {
				Errorx.addError("更新文章状态失败。");
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 发布文章 同时发布文章对应的列表页
	 * 
	 * @param articleSet
	 * @return
	 */
	public boolean publishArticle(Transaction trans, ZCArticleSet articleSet, boolean generateCatalog, LongTimeTask task) {
		if (articleSet == null || articleSet.size() <= 0) {
			return true;
		}
		PageGenerator p = new PageGenerator(task);
		if (task != null) {
			task.setCurrentInfo("开始发布文章");
			task.setPercent(15);
		}
		Deploy d = new Deploy();
		Date date = new Date();
		for (int i = 0; i < articleSet.size(); i++) {
			ZCArticleSchema article = articleSet.get(i);
			boolean referPublish = true;
			// 引用复制不发布具体详细页面
			if ("4".equals(article.getType())) {
				referPublish = false;
			}
			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();

			if (article.getStatus() == Article.STATUS_OFFLINE) {
				String msg = "文档处于下线状态，不能发布。如需发布，请先上线后再操作。";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			String workflowName = CatalogUtil.getWorkflow(article.getCatalogID());
			// 有工作流时初稿不能发布
			if (StringUtil.isNotEmpty(workflowName) && article.getStatus() == Article.STATUS_WORKFLOW) {
				String msg = "文档需审核，不能发布。";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			} else if (StringUtil.isNotEmpty(workflowName) && article.getStatus() == Article.STATUS_DRAFT && !UserList.ADMINISTRATOR.equals(article.getAddUser())) {
				String msg = "文档需审核，不能发布。";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			// 发布时间大于当前时间则不发布文档
			if (article.getPublishDate() != null && article.getPublishDate().getTime() > new Date().getTime()) {
				String msg = "未到上线时间，文档不能发布！";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}
			// 处理文章Tag 变成html
			String tag = article.getTag();
			tagToHtml(article);
			QueryBuilder qb = new QueryBuilder("update zcarticle set modifyuser=?,modifytime=?,status=? where id=?");
			if (article.getPublishDate() == null) {
				qb = new QueryBuilder("update zcarticle set publishdate=?,FirstPublishDate=?,modifyuser=?,modifytime=?,status=? where id=?");
				qb.add(date);
				qb.add(date);
			} else {
				article.setPublishDate(date);

			}

			if (User.getCurrent() == null) {
				qb.add("System");
				article.setModifyUser("System");
			} else {
				qb.add(User.getUserName());
				article.setModifyUser(User.getUserName());
			}
			qb.add(date);
			qb.add(Article.STATUS_PUBLISHED);
			qb.add(article.getID());
			qb.executeNoQuery();
			article.setModifyTime(date);
			article.setStatus(Article.STATUS_PUBLISHED);

			// 引用复制的文章不需要生成具体的静态页面
			if (referPublish) {
				if (!p.staticDoc("Article", article)) {
					continue;
				}
				if (!p.staticDoc("Article", article, "wap")) {
					continue;
				}

				d.addJobs(articleSet.get(0).getSiteID(), p.getFileList());
			}

			if (task != null) {
				task.setPercent(30 + i / articleSet.size() * 50);
				task.setCurrentInfo("正在发布:" + article.getTitle());
			}
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setArticleID(article.getID());
			articleLog.setAction("PUBLISH");
			articleLog.setActionDetail("发布文章");
			if (User.getCurrent() == null) {
				articleLog.setAddUser("SYSTEM");
			} else {
				// 2011-1-14 处理历史显示真实姓名
				articleLog.setAddUser(User.getRealName());
			}

			articleLog.setAddTime(date);
			article.setTag(tag);
			trans.add(article, Transaction.UPDATE);
			trans.add(articleLog, Transaction.INSERT);

			// 发布文章关联的媒体文件
			trans.add(new QueryBuilder("update zcimage set status=" + Article.STATUS_PUBLISHED + " where status<>? and exists (select 1 from zcimagerela where relaid=? and id=zcimage.id)",
					Article.STATUS_PUBLISHED, article.getID()));
			trans.add(new QueryBuilder("update zcvideo set status=" + Article.STATUS_PUBLISHED + " where status<>? and exists (select id from zcvideorela where relaid=? and id=zcvideo.id)",
					Article.STATUS_PUBLISHED, article.getID()));
			trans.add(new QueryBuilder("update zcattachment set status=" + Article.STATUS_PUBLISHED
					+ " where status<>? and exists (select id from zcattachmentrela where relaid=? and id=zcattachment.id)", Article.STATUS_PUBLISHED, article.getID()));
			trans.add(new QueryBuilder("update zcaudio set status=" + Article.STATUS_PUBLISHED + " where status<>? and exists (select id from zcaudiorela where relaid=? and id=zcaudio.id)",
					Article.STATUS_PUBLISHED, article.getID()));
		}
		/*
		if (generateCatalog) {
			// 存所有涉及的栏目ID，处理文章引用时问题
			Mapx catalogMap = new Mapx();
			for (int k = 0; k < articleSet.size(); k++) {
				catalogMap.put(articleSet.get(k).getCatalogID() + "", articleSet.get(k).getCatalogID() + "");
				String pid = CatalogUtil.getParentID(articleSet.get(k).getCatalogID());
				while (StringUtil.isNotEmpty(pid) && !"null".equals(pid) && !"0".equals(pid)) {
					catalogMap.put(pid, pid);
					pid = CatalogUtil.getParentID(pid);
				}
			}

			// 生成本级栏目
			Object[] vs = catalogMap.valueArray();
			for (int j = 0; j < catalogMap.size(); j++) {
				String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
				// if (StringUtil.isEmpty(listpage) || "0".equals(listpage) ||
				// "-1".equals(listpage)) {
				if (StringUtil.isEmpty(listpage)) {
					listpage = "20"; // 默认只发布前二十页
				}
				publishCatalog(Long.parseLong(vs[j].toString()), false, false, false, Integer.parseInt(listpage));
				if (task != null) {
					task.setPercent(task.getPercent() + 5);
					task.setCurrentInfo("发布栏目页面");
				}
			}
		}*/
		return true;
	}

	private void tagToHtml(ZCArticleSchema article) {
		// String tagStr = article.getTag();
		// if (StringUtil.isNotEmpty(tagStr)) {
		// String[] tag = tagStr.split(" ");
		// GetDBdata db = new GetDBdata();
		// String path = "";
		// String id = "";// 索引id
		// try {
		// path =
		// db.getOneValue("select value from zdconfig where type='FrontServerContextPath'");
		// if (StringUtil.isNotEmpty(article.getCatalogID() + "")) {
		// String catalogId =
		// db.getOneValue("select id from zccatalog where innercode=left('" +
		// article.getCatalogInnerCode() + "', 6)");
		// id =
		// db.getOneValue("select id from ZCFullText where memo like '%"+catalogId+"%'");
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
		// tagHtml += "<a href=\"" + path + "/tag/" +tagId+"000001" + "-" + id +
		// ".html\">" + tag[i] + "</a>&nbsp;&nbsp;";
		// }
		// }
		// article.setTag(tagHtml);
		// }

	}

	public boolean publishDocs(String docType, SchemaSet docSet, boolean generateCatalog, LongTimeTask task) {
		if (docSet == null || docSet.size() <= 0) {
			return true;
		}
		PageGenerator p = new PageGenerator(task);
		if (task != null) {
			task.setCurrentInfo("开始发布");
		}

		Transaction trans = new Transaction();
		Mapx catalogMap = new Mapx();
		long siteID = 0;
		Deploy d = new Deploy();
		for (int i = 0; i < docSet.size(); i++) {
			Schema doc = (Schema) docSet.getObject(i);

			if (!p.staticDoc(docType, (Schema) doc.clone())) {
				continue;
			}

			// 更新发布时间
			DataRow row = doc.toDataRow();

			// 添加发布列表
			siteID = row.getLong("SiteID");
			d.addJobs(siteID, p.getFileList());

			Date publishDate = row.getDate("PublishDate");
			if (publishDate == null) {
				publishDate = new Date();
			}
			String modifyUser = null;
			if (User.getCurrent() == null) {
				modifyUser = "sys";
			} else {
				modifyUser = User.getUserName();
			}

			QueryBuilder qb = new QueryBuilder("update zc" + docType + " set publishdate=?,modifytime=?,modifyuser=?,status=? where id=?");
			qb.add(publishDate);
			qb.add(new Date());
			qb.add(modifyUser);
			qb.add(Article.STATUS_PUBLISHED);
			qb.add(row.get("ID"));
			trans.add(qb);

			catalogMap.put(row.getString("catalogid"), row.getString("catalogid"));

			if (task != null) {
				task.setPercent(30 + i / docSet.size() * 50);
				task.setCurrentInfo("正在发布:" + row.getString("Name"));
			}
		}

		if (!trans.commit()) {
			Errorx.addError("更新文档状态失败。");
			return false;
		}

		if (generateCatalog) {
			// 生成本级栏目
			Object[] vs = catalogMap.valueArray();
			for (int j = 0; j < catalogMap.size(); j++) {
				String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
				// if (StringUtil.isEmpty(listpage) || "0".equals(listpage) ||
				// "-1".equals(listpage)) {
				if (StringUtil.isEmpty(listpage)) {
					listpage = "20"; // 默认只发布前二十页
				}
				publishCatalog(Long.parseLong(vs[j].toString()), false, false, true, Integer.parseInt(listpage));
				if (task != null) {
					task.setPercent(task.getPercent() + 5);
					task.setCurrentInfo("发布栏目页面");
				}
			}
		}

		if (Errorx.hasError()) {
			return false;
		}

		return true;
	}

	public boolean publishArticle(ZCArticleSet articleSet) {
		return publishArticle(articleSet, true, null);
	}

	public boolean publishArticle(ZCArticleSet articleSet, LongTimeTask task) {
		return publishArticle(articleSet, true, task);
	}

	/**
	 * 发布商品 同时发布商品对应的列表页
	 * 
	 * @param articleSet
	 * @return
	 */
	public boolean publishGoods(ZSGoodsSet goodsSet, LongTimeTask task) {
		return publishGoods(goodsSet, true, task);
	}

	public boolean publishGoods(ZSGoodsSet goodsSet, boolean generateCatalog, LongTimeTask task) {
		Transaction trans = new Transaction();
		if (publishGoods(trans, goodsSet, generateCatalog, task)) {
			if (!trans.commit()) {
				Errorx.addError("更新商品状态失败。");
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean publishGoods(Transaction trans, ZSGoodsSet goodsSet, boolean generateCatalog, LongTimeTask task) {
		PageGenerator p = new PageGenerator(task);
		if (task != null) {
			task.setCurrentInfo("开始发布商品");
		}
		Deploy d = new Deploy();
		Date date = new Date();
		for (int i = 0; i < goodsSet.size(); i++) {
			ZSGoodsSchema goods = goodsSet.get(i);

			if (goods.getStatus().equals(Article.STATUS_OFFLINE + "")) {
				String msg = "商品处于下线状态，不能发布。如需发布，请先上线后再操作。";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			String workflowName = CatalogUtil.getWorkflow(goods.getCatalogID());
			if (StringUtil.isNotEmpty(workflowName) && goods.getStatus().equals(Article.STATUS_WORKFLOW + "")) {
				String msg = "商品需审核，不能发布。";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			QueryBuilder qb = new QueryBuilder("update zsgoods set modifyuser=?,modifytime=?,status=? where id=?");
			if (goods.getPublishDate() == null) {
				qb = new QueryBuilder("update zsgoods set publishdate=?,modifyuser=?,modifytime=?,status=? where id=?");
				qb.add(date);
				goods.setPublishDate(date);
			}

			if (User.getCurrent() == null) {
				qb.add("System");
				goods.setModifyUser("System");
			} else {
				qb.add(User.getUserName());
				goods.setModifyUser(User.getUserName());
			}
			qb.add(date);
			qb.add(Article.STATUS_PUBLISHED);
			qb.add(goods.getID());
			qb.executeNoQuery();
			goods.setModifyTime(date);
			goods.setStatus(Article.STATUS_PUBLISHED + "");

			if (!p.staticDoc("Article", goods)) {
				continue;
			}

			d.addJobs(goodsSet.get(0).getSiteID(), p.getFileList());

			if (task != null) {
				task.setPercent(30 + i / goodsSet.size() * 50);
				task.setCurrentInfo("正在发布:" + goods.getName());
			}
		}

		if (generateCatalog) {
			// 存所有涉及的栏目ID，处理文章引用时问题
			Mapx catalogMap = new Mapx();
			for (int k = 0; k < goodsSet.size(); k++) {
				catalogMap.put(goodsSet.get(k).getCatalogID() + "", goodsSet.get(k).getCatalogID() + "");
				String pid = CatalogUtil.getParentID(goodsSet.get(k).getCatalogID());
				while (StringUtil.isNotEmpty(pid) && !"null".equals(pid) && !"0".equals(pid)) {
					catalogMap.put(pid, pid);
					pid = CatalogUtil.getParentID(pid);
				}
			}

			// 生成本级栏目
			Object[] vs = catalogMap.valueArray();
			for (int j = 0; j < catalogMap.size(); j++) {
				String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
				// if (StringUtil.isEmpty(listpage) || "0".equals(listpage) ||
				// "-1".equals(listpage)) {
				if (StringUtil.isEmpty(listpage)) {
					listpage = "20"; // 默认只发布前二十页
				}
				publishCatalog(Long.parseLong(vs[j].toString()), false, false, true, Integer.parseInt(listpage));
				if (task != null) {
					task.setPercent(task.getPercent() + 5);
					task.setCurrentInfo("发布栏目页面");
				}
			}
		}
		return true;
	}

	/**
	 * 发布栏目
	 * 
	 * @param catalogID
	 * @param child
	 * @param detail
	 * @return
	 */
	public boolean publishCatalog(long catalogID, boolean child, boolean detail, boolean list) {
		return publishCatalog(catalogID, child, detail, list, false, 0, null);
	}

	public boolean publishCatalog(long catalogID, boolean child, boolean detail, boolean list, int publishSize) {
		return publishCatalog(catalogID, child, detail, list, false, publishSize, null);
	}

	public boolean publishCatalog(long catalogID, boolean child, boolean detail, boolean list, LongTimeTask task) {
		return publishCatalog(catalogID, child, detail, list, false, 0, task);
	}
	
	public boolean publishCatalog(long catalogID, boolean child, boolean detail, boolean list, boolean wapDetail) {
		return publishCatalog(catalogID, child, detail, list, wapDetail, 0, null);
	}

	public boolean publishCatalog(long catalogID, boolean child, boolean detail, boolean list, boolean wapDetail, int publishSize) {
		return publishCatalog(catalogID, child, detail, list, wapDetail, publishSize, null);
	}

	public boolean publishCatalog(long catalogID, boolean child, boolean detail, boolean list, boolean wapDetail, LongTimeTask task) {
		return publishCatalog(catalogID, child, detail, list, wapDetail, 0, task);
	}

	/**
	 * 生成栏目的所有文件，全部生成，覆盖原有文件
	 * 
	 * @param catalogID
	 * @param child
	 *            是否生成子栏目
	 * @param detail
	 *            是否生成详细页
	 * @param publishPages
	 *            发布的页数
	 * @param task
	 *            长时间任务 供进度条调用
	 * @return
	 */
	public boolean publishCatalog(long catalogID, boolean child, boolean detail, boolean list, boolean wapDetail,
			int publishPages, LongTimeTask task) {

		PageGenerator p = new PageGenerator(task);
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		catalog.fill();

		if (publishPages == 0 && catalog.getListPage() > 0) {
			publishPages = (int) catalog.getListPage();// 列表页最大分页数
		}

		// 当列表页最大分页数小于指定页数时，以列表页最大分页数为准
		if (catalog.getListPage() > 0 && catalog.getListPage() < publishPages) {
			publishPages = (int) catalog.getListPage();
		}

		if (task != null) {
			task.setPercent(30);
		}

		if (!p.staticCatalog(catalog, detail, list, wapDetail, publishPages)) {
			return false;
		}

		if (task != null) {
			task.setPercent(45);
		}

		if (child) {
			if (!p.staticChildCatalog(catalogID, detail, list, wapDetail, publishPages)) {
				return false;
			}
		}

		if (task != null) {
			task.setPercent(75);
		}

		if (detail) {
			Transaction trans = new Transaction();
			String wherePart = null;
			String catalogWherePart = " CatalogID=" + catalog.getID();
			if (child) {
				catalogWherePart = " CatalogInnerCode like '" + catalog.getInnerCode() + "%' ";
			}
			wherePart = " where " + catalogWherePart + " and status in(" + Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED + ") ";
			trans.add(new QueryBuilder("update zcarticle set publishDate = ?,status=" + Article.STATUS_PUBLISHED + wherePart, new Date()));
			if (!trans.commit()) {
				return false;
			}
		}
		Deploy d = new Deploy();
		d.addJobs(catalog.getSiteID(), p.getFileList());
		if (task != null) {
			task.setPercent(100);
		}

		return true;
	}

	public void deletePubishedFile(ZCImageSchema image) {
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		String filePath = staticDir + "/" + SiteUtil.getAlias(image.getSiteID()) + "/upload/" + CatalogUtil.getPath(image.getCatalogID()) + "/" + image.getID() + ".shtml";
		FileUtil.delete(filePath);
	}

	/**
	 * 删除文档发布相关文件，如果图片相关的缩略图、生成的shtml文件
	 * 
	 * @param set
	 */
	public void deletePubishedFile(SchemaSet set) {
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/') + "/";
		ArrayList fileList = new ArrayList();
		long siteID = 0;
		for (int i = 0; i < set.size(); i++) {
			Schema doc = set.getObject(i);
			siteID = doc.toDataRow().getLong("SiteID");
			if (doc instanceof ZCArticleSchema) {
				ZCArticleSchema article = (ZCArticleSchema) doc;
				String filePath = staticDir + SiteUtil.getAlias(article.getSiteID()) + "/" + PubFun.getArticleURL(article);
				// 如果文章是产品中心目录下的产品，则无法删除物理文件
				if (!article.getCatalogInnerCode().startsWith("002313")) {
					fileList.add(filePath);
				}

			} else if (doc instanceof ZCImageSchema) {
				ZCImageSchema image = (ZCImageSchema) doc;
				String basePath = staticDir + SiteUtil.getAlias(image.getSiteID()) + "/" + image.getPath();
				Mapx configFields = new Mapx();
				configFields.putAll(ConfigImageLib.getImageLibConfig(image.getSiteID()));
				int count = Integer.parseInt(configFields.get("Count").toString());
				for (int j = 1; j <= count; j++) {
					fileList.add(basePath + j + "_" + image.getFileName());
				}
				fileList.add(basePath + "s_" + image.getFileName());
				fileList.add(basePath + image.getSrcFileName());
				fileList.add(staticDir + SiteUtil.getAlias(image.getSiteID()) + "/" + CatalogUtil.getPath(image.getCatalogID()) + "/" + image.getID() + ".shtml");
			} else if (doc instanceof ZCAttachmentSchema) {
				ZCAttachmentSchema attach = (ZCAttachmentSchema) doc;
				String basePath = staticDir + SiteUtil.getAlias(attach.getSiteID()) + "/" + attach.getPath();
				fileList.add(basePath + attach.getSrcFileName());
				if (StringUtil.isNotEmpty(attach.getImagePath()) && attach.getImagePath().indexOf("nopicture.jpg") == -1) {
					String coverImage = staticDir + "/" + attach.getImagePath();
					fileList.add(coverImage);
				}
				fileList.add(staticDir + SiteUtil.getAlias(attach.getSiteID()) + "/" + CatalogUtil.getPath(attach.getCatalogID()) + "/" + attach.getID() + ".shtml");
			} else if (doc instanceof ZCVideoSchema) {
				ZCVideoSchema video = (ZCVideoSchema) doc;
				String basePath = staticDir + SiteUtil.getAlias(video.getSiteID()) + "/" + video.getPath();
				fileList.add(basePath + video.getSrcFileName());
				fileList.add(basePath + video.getImageName());
				fileList.add(basePath + video.getFileName());
				fileList.add(staticDir + SiteUtil.getAlias(video.getSiteID()) + "/" + CatalogUtil.getPath(video.getCatalogID()) + "/" + video.getID() + ".shtml");
			} else if (doc instanceof ZCAudioSchema) {
				ZCAudioSchema audio = (ZCAudioSchema) doc;
				String basePath = staticDir + SiteUtil.getAlias(audio.getSiteID()) + "/" + audio.getPath() + audio.getSrcFileName();
				fileList.add(basePath + audio.getSrcFileName());
				fileList.add(basePath + audio.getFileName());
				fileList.add(staticDir + SiteUtil.getAlias(audio.getSiteID()) + "/" + CatalogUtil.getPath(audio.getCatalogID()) + "/" + audio.getID() + ".shtml");
			}
		}

		// 删除本地文件
		for (int i = 0; i < fileList.size(); i++) {
			FileUtil.delete((String) fileList.get(i));
		}

		// 删除部署服务器上的文件
		Deploy deploy = new Deploy();
		if (fileList.size() > 0 && siteID != 0) {
			deploy.addJobs(siteID, fileList, Deploy.OPERATION_DELETE);
		}

	}

	public long publishSetTask(final String type, final SchemaSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishDocs(type, set, true, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public long deleteFileTask(final SchemaSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				if (set != null && set.size() > 0) {
					p.deletePubishedFile(set);
					String listpage = CatalogUtil.getData(set.getObject(0).toDataRow().getString("CatalogID")).getString("ListPage");
					// if (StringUtil.isEmpty(listpage) || "0".equals(listpage)
					// || "-1".equals(listpage)) {
					if (StringUtil.isEmpty(listpage)) {
						listpage = "20"; // 默认只发布前二十页
					}
					p.publishCatalog(set.getObject(0).toDataRow().getLong("CatalogID"), false, false, true, Integer.parseInt(listpage));
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

}
