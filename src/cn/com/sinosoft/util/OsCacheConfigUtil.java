package cn.com.sinosoft.util;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 工具类 - 缓存
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT36E03F145A16F1CAF1C6DBCE956C081E
 * ============================================================================
 */

public class OsCacheConfigUtil {
	
	public static final String GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME = "cacheManager";// GeneralCacheAdministrator注入Bean名称
	
	/**
	 * 根据Key读取缓存
	 * 
	 * @return 缓存对象
	 */
	public static Object getFromCache(String key) {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		Object object = null;
		try {
			object = generalCacheAdministrator.getFromCache(key);
		} catch (NeedsRefreshException e) {
			generalCacheAdministrator.cancelUpdate(key);
		}
		return object;
	}
	
	/**
	 * 加入对象到缓存
	 * 
	 */
	public static void putInCache(String key, Object object) {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		generalCacheAdministrator.putInCache(key, object);
	}
	
	/**
	 * 根据Key刷新缓存对象
	 * 
	 */
	public static void flushEntry(String key) {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		generalCacheAdministrator.flushEntry(key);
	}

}