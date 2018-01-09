package com.sinosoft.cms.pub;

import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.cache.CacheProvider;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCatalogConfigSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZCSiteSet;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;

/**
 * CMS相关的缓存项，包括站点，栏目，内部编码，名称，栏目发布设置
 * 
 */
public class CMSCache extends CacheProvider {

	private static final int CACHESIZE = 10000;// 最多缓存一万个栏目

	public static final String ProviderName = "CMS";

	public String getProviderName() {
		return ProviderName;
	}

	public String getID() {
		return "CMS";
	}

	public String getName() {
		return "CMS";
	}

	/*
	 * 当ZCCatalogSchema被置入缓存时，同步更新InnerCode和Name的缓存
	 */
	public void onKeySet(String type, Object key, Object value) {
		if (type.equals("Catalog")) {
			ZCCatalogSchema catalog = (ZCCatalogSchema) value;
			CacheManager.set(this.getProviderName(), "CatalogInnerCode", catalog.getInnerCode(), catalog.getID());

			// 优先缓存一级栏目名称与栏目ID的信息
			Mapx map = CacheManager.getMapx(this.getProviderName(), "CatalogName");
			if (catalog.getParentID() != 0) {

				// 本栏目为非一级栏目则判断是否已有缓存，缓存存在则不缓存本栏目信息
				if (StringUtil.isEmpty(map.getString(catalog.getSiteID() + "|" + catalog.getName()))) {
					CacheManager.set(this.getProviderName(), "CatalogName", catalog.getSiteID() + "|" + catalog.getName(), catalog.getID());
				}

				if (StringUtil.isEmpty(map.getString(catalog.getSiteID() + "|" + catalog.getName() + "|" + catalog.getParentID()))) {
					CacheManager.set(this.getProviderName(), "CatalogName", catalog.getSiteID() + "|" + catalog.getName() + "|" + catalog.getParentID(), catalog.getID());
				}
			} else {
				// 本栏目为一级栏目，缓存本栏目信息
				CacheManager.set(this.getProviderName(), "CatalogName", catalog.getSiteID() + "|" + catalog.getName(), catalog.getID());
				CacheManager.set(this.getProviderName(), "CatalogName", catalog.getSiteID() + "|" + catalog.getName() + "|" + catalog.getParentID(), catalog.getID());
			}
		}
	}

	public void onKeyNotFound(String type, Object key) {
		if (key == null || StringUtil.isEmpty(String.valueOf(key)) || "null".equals(key)) {
			return;
		}
		// 缓存站点数据
		if (type.equals("Site")) {
			ZCSiteSchema schema = new ZCSiteSchema();
			schema.setID(String.valueOf(key));
			if (schema.fill()) {
				CacheManager.set(this.getProviderName(), type, schema.getID(), schema);
			}
		}
		// 缓存栏目扩展配置
		if (type.equals("CatalogConfig")) {
			ZCCatalogConfigSchema schema = new ZCCatalogConfigSchema();
			String id = String.valueOf(key);
			if (id.indexOf(',') > 0) {
				String[] arr = id.split(",");
				schema.setSiteID(arr[0]);
				schema.setCatalogID("0");
			} else {
				schema.setCatalogID(id);
			}
			ZCCatalogConfigSet set = schema.query();
			if (set.size() > 0) {
				schema = set.get(0);
			} else {
				logger.warn("未找到ID={}的ZCCatalogConfig记录!", id);
				return;
			}
			CacheManager.set(this.getProviderName(), type, id, schema);
		}
		// 缓存栏目Schema
		if (type.equals("Catalog")) {
			ZCCatalogSchema schema = new ZCCatalogSchema();
			schema.setID(String.valueOf(key));
			if (schema.fill()) {
				CacheManager.set(this.getProviderName(), type, schema.getID(), schema);
			}
		}
		// 缓存栏目内部编码与ID的映射
		if (type.equals("CatalogInnerCode")) {
			ZCCatalogSchema schema = new ZCCatalogSchema();
			schema.setInnerCode(String.valueOf(key));
			ZCCatalogSet set = schema.query();
			for (int i = 0; i < set.size(); i++) {
				schema = set.get(i);
				// 只需更新Catalog，会连带更新InnerCode和Name
				CacheManager.set(this.getProviderName(), "Catalog", schema.getID(), schema);
			}
		}
		// 缓存栏目名称与ID的映射
		if (type.equals("CatalogName")) {
			String[] arr = key.toString().split("\\|");
			String SiteID = arr[0];
			String Name = arr[1];
			String ParentID = null;
			if (arr.length > 2) {
				ParentID = arr[2];
			}
			QueryBuilder qb = new QueryBuilder("where SiteID=? and Name=?", Long.parseLong(SiteID), Name);
			if (StringUtil.isNotEmpty(ParentID)) {
				qb.append(" and ParentID=?", Long.parseLong(ParentID));
			}
			ZCCatalogSchema schema = new ZCCatalogSchema();
			ZCCatalogSet set = schema.query(qb);
			for (int i = 0; i < set.size(); i++) {
				schema = set.get(i);
				CacheManager.set(this.getProviderName(), "Catalog", schema.getID(), schema);
			}
		}
		// 标签缓存
		if (type.equals("Tag")) {
			ZCTagSchema tag = new ZCTagSchema();
			String[] arr = key.toString().split("\\|");
			tag.setSiteID(arr[0]);
			tag.setTag(arr[1]);
			ZCTagSet set = tag.query();
			for (int i = 0; i < set.size(); i++) {
				tag = set.get(i);
				CacheManager.set(this.getProviderName(), "Tag", key, tag);
			}
		}
	}

	public void onTypeNotFound(String type) {
		if (type.equals("Site")) {
			ZCSiteSet set = new ZCSiteSchema().query();
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set(this.getProviderName(), type, set.get(i).getID(), set.get(i));
			}
		}
		if (type.equals("CatalogConfig")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// 默认不加载数据
		}
		if (type.equals("Catalog")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// 默认不加载数据
		}
		if (type.equals("CatalogName")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// 默认不加载数据
		}
		if (type.equals("CatalogInnerCode")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// 默认不加载数据
		}
		// 标签缓存
		if (type.equals("Tag")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// 需要设置缓存最大大小
			ZCTagSchema tag = new ZCTagSchema();
			ZCTagSet set = tag.query();
			for (int i = 0; i < set.size(); i++) {
				tag = set.get(i);
				CacheManager.set(this.getProviderName(), "Tag", tag.getSiteID() + "|" + tag.getTag(), tag);
			}
		}
	}

	public static ZCSiteSchema getSite(String id) {
		return (ZCSiteSchema) CacheManager.get(ProviderName, "Site", id);
	}

	public static ZCSiteSchema getSite(long id) {
		return (ZCSiteSchema) CacheManager.get(ProviderName, "Site", id);
	}

	public static ZCCatalogSchema getCatalogByInnerCode(String innerCode) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName, "CatalogInnerCode", innerCode));
	}

	public static ZCCatalogSchema getCatalog(String id) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", id);
	}

	public static ZCCatalogSchema getCatalog(long id) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", id);
	}

	public static ZCCatalogSchema getCatalog(long siteID, String name) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName, "CatalogName", siteID + "|" + name));
	}

	public static ZCCatalogSchema getCatalog(String siteID, String name) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName, "CatalogName", siteID + "|" + name));
	}

	public static ZCCatalogSchema getCatalog(long siteID, long parentID, String name) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName, "CatalogName", siteID + "|" + name + "|" + parentID));
	}

	public static ZCCatalogSchema getCatalog(String siteID, long parentID, String name) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName, "CatalogName", siteID + "|" + name + "|" + parentID));
	}

	public static ZCCatalogConfigSchema getCatalogConfig(String id) {
		return (ZCCatalogConfigSchema) CacheManager.get(ProviderName, "CatalogConfig", id);
	}

	public static ZCCatalogConfigSchema getCatalogConfig(long id) {
		return (ZCCatalogConfigSchema) CacheManager.get(ProviderName, "CatalogConfig", id);
	}

	public static ZCTagSchema getTag(long siteID, String tagName) {
		return (ZCTagSchema) CacheManager.get(ProviderName, "Tag", siteID + "|" + tagName);
	}

	public static void removeCatalog(ZCCatalogSchema catalog) {
		CacheManager.remove(ProviderName, "Catalog", catalog.getID());
		CacheManager.remove(ProviderName, "CatalogConfig", catalog.getID());
		CacheManager.remove(ProviderName, "CatalogInnerCode", catalog.getSiteID() + "|" + catalog.getName());
		CacheManager.remove(ProviderName, "CatalogInnerCode", catalog.getSiteID() + "|" + catalog.getName() + "|" + catalog.getParentID());
	}

	public static void removeCatalogSet(ZCCatalogSet catalogSet) {
		for (int i = 0; i < catalogSet.size(); i++) {
			removeCatalog(catalogSet.get(i));
		}
	}
}
