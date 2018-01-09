package com.sinosoft.cms.datachannel;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCAttachmentSchema;
import com.sinosoft.schema.ZCAttachmentSet;
import com.sinosoft.schema.ZCAudioSchema;
import com.sinosoft.schema.ZCAudioSet;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCImageSet;
import com.sinosoft.schema.ZCVideoSchema;
import com.sinosoft.schema.ZCVideoSet;

import java.util.Date;

public class PublishTaskManager extends ConfigEanbleTaskManager {

	public void execute(long id) {
		logger.info("PublishTaskManager开始发布内容");

		ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
		config.setID(id);
		if (config.fill()) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(config.getCatalogID());
			if (!catalog.fill()) {
				return;
			}
			Publisher p = new Publisher();
			if (catalog.getType() == Catalog.CATALOG) {
				ZCArticleSet set = new ZCArticleSet();
				if (StringUtil.isNotEmpty(config.getCatalogID() + "")) {
					set = new ZCArticleSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
							new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCArticleSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and SiteID=?", new Date(), config
							.getSiteID()));
				}
				p.publishArticle(set);
			} else if (catalog.getType() == Catalog.VIDEOLIB) {
				ZCVideoSet set = new ZCVideoSet();
				if (StringUtil.isNotEmpty(config.getCatalogID() + "")) {
					set = new ZCVideoSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
							new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
					// System.out.println(set.size());
				} else {
					set = new ZCVideoSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and SiteID=?", new Date(), config
							.getSiteID()));
				}
				p.publishDocs("video", set, true, null);
			} else if (catalog.getType() == Catalog.IMAGELIB) {
				ZCImageSet set = new ZCImageSet();
				if (StringUtil.isNotEmpty(config.getCatalogID() + "")) {
					set = new ZCImageSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
							new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCImageSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and SiteID=?", new Date(), config
							.getSiteID()));
				}
				p.publishDocs("image", set, true, null);
			} else if (catalog.getType() == Catalog.AUDIOLIB) {
				ZCAudioSet set = new ZCAudioSet();
				if (StringUtil.isNotEmpty(config.getCatalogID() + "")) {
					set = new ZCAudioSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
							new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCAudioSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and SiteID=?", new Date(), config
							.getSiteID()));
				}
				p.publishDocs("audio", set, true, null);
			} else if (catalog.getType() == Catalog.ATTACHMENTLIB) {
				ZCAttachmentSet set = new ZCAttachmentSet();
				if (StringUtil.isNotEmpty(config.getCatalogID() + "")) {
					set = new ZCAttachmentSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
							new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCAttachmentSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH + " and (publishdate<=? or publishdate is null) and SiteID=?", new Date(),
							config.getSiteID()));
				}
				p.publishDocs("attachment", set, true, null);
			}

		}
		logger.info("PublishTaskManager结束发布内容");
	}

	public String getCode() {
		return "Publisher";
	}

	public String getName() {
		return "内容发布任务";
	}

	public Mapx getConfigEnableTasks() {
		Mapx map = new Mapx();
		map.put("-1", "发布全部文档");
		map.put("0", "全局区块发布");
		return map;
	}

	public boolean isRunning(long arg0) {
		return false;
	}

	@Override
	public void execute(String paramString) {
		execute(Integer.parseInt(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.sinosoft.cms.datachannel.PublishTaskManager";
	}

}
