package com.sinosoft.framework.cache;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.clustering.Clustering;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.CodeCache;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CacheManager {
	private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);

	private static Object mutex = new Object();

	private static Mapx ProviderMap;

	private static void loadConfig() {
		if (ProviderMap == null) {
			String path = Config.getContextRealPath() + "WEB-INF/classes/framework.xml";
			SAXReader reader = new SAXReader(false);
			Document doc;
			try {
				doc = reader.read(new File(path));
				Element root = doc.getRootElement();
				Element cache = root.element("cache");
				if (cache != null) {
					List types = cache.elements();
					ProviderMap = new Mapx();
					for (int i = 0; i < types.size(); i++) {
						Element type = (Element) types.get(i);
						String className = type.attributeValue("class");
						try {
							CacheProvider cp = (CacheProvider) Class.forName(className).newInstance();
							ProviderMap.put(cp.getProviderName(), cp);
						} catch (InstantiationException e) {
							logger.error(e.getMessage(), e);
						} catch (IllegalAccessException e) {
							logger.error(e.getMessage(), e);
						} catch (ClassNotFoundException e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			} catch (DocumentException e) {
				logger.error(e.getMessage(), e);
			}
			CacheProvider cp = new CodeCache();
			ProviderMap.put(cp.getProviderName(), cp);
		}
	}

	/**
	 * 获取指定类型的的CacheProvider
	 */
	public static CacheProvider getCache(String type) {
		synchronized (mutex) {
			if (ProviderMap == null) {
				loadConfig();
			}
		}
		return (CacheProvider) ProviderMap.get(type);
	}

	/**
	 * 获取缓存数据
	 */
	public static Object get(String providerName, String type, int key) {
		return get(providerName, type, String.valueOf(key));
	}

	public static Object get(String providerName, String type, long key) {
		return get(providerName, type, String.valueOf(key));
	}

	public static Object get(String providerName, String type, Object key) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			throw new RuntimeException("未找到CacheProvider:" + providerName);
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			synchronized (cp) {
				if (Clustering.isClustering()) {// 集群下先从缓存中取
					map = Clustering.getMapx(cp.getProviderName() + "_" + type);
					if (map == null) {
						cp.onTypeNotFound(type);
						map = (Mapx) cp.TypeMap.get(type);
						if (map != null) {
							Clustering.putMapx(cp.getProviderName() + "_" + type, map);
						}
					} else {
						cp.TypeMap.put(type, map);
					}
				} else {
					if (map == null) {
						cp.onTypeNotFound(type);
					}
				}
				map = (Mapx) cp.TypeMap.get(type);
				if (map == null) {
					logger.warn("指定的CacheProvider:{}下不存在类型为{}的缓存。", providerName, type);
					return null;
				}
			}
		}
		if (!map.containsKey(key)) {
			synchronized (mutex) {
				if (Clustering.isClustering()) {// 集群下先从缓存中取
					if (Clustering.containsKey(cp.getProviderName() + "_" + type + "." + key)) {
						String str = Clustering.get(cp.getProviderName() + "_" + type + "." + key);
						map.put(key, str);
					} else {
						cp.onKeyNotFound(type, key);
						if (map.containsKey(key)) {
							Clustering.put(cp.getProviderName() + "_" + type + "." + key, map.getString(key));
						}
					}
				} else if (!map.containsKey(key)) {
					cp.onKeyNotFound(type, key);
				}
				if (!map.containsKey(key)) {
					logger.error("获取缓存数据失败:{}", providerName + "," + type + "," + key);
				}
			}
		}
		return map.get(key);
	}

	/**
	 * 更新缓存数据
	 */
	public static void set(String providerName, String type, int key, Object value) {
		set(providerName, type, String.valueOf(key), value);
	}

	public static void set(String providerName, String type, long key, Object value) {
		set(providerName, type, String.valueOf(key), value);
	}

	public static void set(String providerName, String type, long key, long value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, long key, int value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, int key, long value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, int key, int value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key, long value) {
		set(providerName, type, key, String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key, int value) {
		set(providerName, type, key, String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key, Object value) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			logger.warn("未找到CacheProvider:{}", providerName);
			return;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			map = new Mapx();
			synchronized (cp) {
				cp.TypeMap.put(type, map);
			}
		}
		synchronized (mutex) {
			map.put(key, value);
			if (Clustering.isClustering()) {// 集群下需要更新集群缓存
				Clustering.putObject(cp.getProviderName() + "_" + type + "." + key, value);
			}
			cp.onKeySet(type, key, value);
		}
	}

	protected static void registerProvider(CacheProvider cp) {
		synchronized (mutex) {
			if (getCache(cp.getID()) == null)
				ProviderMap.put(cp.getID(), cp);
		}
	}

	protected static CacheProvider removeProvider(String id) {
		synchronized (mutex) {
			return (CacheProvider) ProviderMap.remove(id);
		}
	}

	protected static List<CacheProvider> getAllProvider() {
		synchronized (mutex) {
			List<CacheProvider> list = new ArrayList<CacheProvider>();
			Object[] o = ProviderMap.valueArray();
			for (Object o1 : o) {
				list.add((CacheProvider) o1);
			}
			return list;
		}
	}

	/**
	 * 删除缓存数据
	 */
	public static void remove(String providerName, String type, int key) {
		remove(providerName, type, String.valueOf(key));
	}

	public static void remove(String providerName, String type, long key) {
		remove(providerName, type, String.valueOf(key));
	}

	public static void remove(String providerName, String type, Object key) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			logger.warn("未找到CacheProvider:{}", providerName);
			return;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			logger.warn("指定的CacheProvider:{}下没有类型为{}的缓存", providerName, type);
			return;
		}
		synchronized (map) {
			map.remove(key);
			if (Clustering.isClustering()) {// 集群下需要删除集群缓存
				Clustering.remove(cp.getProviderName() + "_" + type + "." + key);
			}
		}
	}

	/**
	 * 删除缓存类型
	 */
	public static void removeType(String providerName, String type) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			logger.warn("未找到CacheProvider:{}", providerName);
			return;
		}
		synchronized (cp) {
			cp.TypeMap.remove(type);
			if (Clustering.isClustering()) {// 集群下需要删除集群缓存
				Clustering.remove(cp.getProviderName() + "_" + type);
			}
		}
	}

	/**
	 * 获取缓存类型对应的Mapx。<br>
	 * 注意：可能缓存中只有同一类型的一部分数据，其它数据要等待延迟加载。
	 */
	public static Mapx getMapx(String providerName, String type) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			logger.warn("未找到CacheProvider:{}", providerName);
			return null;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			synchronized (cp) {
				if (Clustering.isClustering()) {// 集群下先从缓存中取
					map = Clustering.getMapx(cp.getProviderName() + "_" + type);
					if (map == null) {
						cp.onTypeNotFound(type);
						map = (Mapx) cp.TypeMap.get(type);
						if (map != null) {
							Clustering.putMapx(cp.getProviderName() + "_" + type, map);
						}
					} else {
						cp.TypeMap.put(type, map);
					}
				} else {
					if (map == null) {
						cp.onTypeNotFound(type);
					}
				}
				map = (Mapx) cp.TypeMap.get(type);
				if (map == null) {
					logger.warn("指定的CacheProvider:{}下不存在类型为{}的缓存。", providerName, type);
					return null;
				}
			}
		}
		return map;
	}

	/**
	 * 设置缓存类型对应的Mapx
	 */
	public static void setMapx(String providerName, String type, Mapx map) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			logger.warn("未找到CacheProvider:{}", providerName);
			return;
		}
		cp.TypeMap.put(type, map);
		if (Clustering.isClustering()) {
			Clustering.putMapx(cp.getProviderName() + "_" + type, map);
		}
	}

	/**
	 * 获取缓存类型对应的Mapx，请使用getMapx(String providerName, String type)代替
	 * 
	 * @deprecated
	 */
	public static Mapx get(String providerName, String type) {
		return getMapx(providerName, type);
	}
}
