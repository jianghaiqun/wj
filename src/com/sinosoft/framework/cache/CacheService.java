package com.sinosoft.framework.cache;

import java.util.List;

import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.extend.IExtendItem;
import com.sinosoft.framework.extend.IExtendService;

public class CacheService implements IExtendService<CacheProvider> {
	public void register(IExtendItem item) {
		CacheManager.registerProvider((CacheProvider) item);
	}

	public static CacheService getInstance() {
		return (CacheService) ExtendManager.findExtendServiceByClass(CacheService.class.getName()).getInstance();
	}

	public CacheProvider get(String id) {
		return CacheManager.getCache(id);
	}

	public CacheProvider remove(String id) {
		return CacheManager.removeProvider(id);
	}

	public List<CacheProvider> getAll() {
		return CacheManager.getAllProvider();
	}
}