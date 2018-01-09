package cn.com.sinosoft.service;

import java.util.LinkedHashMap;

public interface DealDataService<T> extends BaseService<T, String> {
	public boolean submitModel(LinkedHashMap<Object, String> lmap) throws Exception;
	public boolean saveAll(LinkedHashMap<Object, String> lmap) throws Exception ;
}