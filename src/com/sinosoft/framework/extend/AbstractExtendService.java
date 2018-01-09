package com.sinosoft.framework.extend;

import com.sinosoft.framework.utility.Mapx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AbstractExtendService<T extends IExtendItem> implements IExtendService<T> {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractExtendService.class);

	protected Mapx ItemMap = new Mapx();

	protected static IExtendService<?> findInstance(Class<?> clazz) {
		return ExtendManager.findExtendServiceByClass(clazz.getName()).getInstance();
	}

	public void register(IExtendItem item) {
		this.ItemMap.put(item.getID(), (T) item);
	}

	public T get(String id) {
		return (T) this.ItemMap.get(id);
	}

	public T remove(String id) {
		return (T) this.ItemMap.remove(id);
	}

	public List<T> getAll() {
		Object[] obj = ItemMap.valueArray();
		List<T> list = new ArrayList<T>();
		for (Object o : obj) {
			T atm = (T) o;
			list.add(atm);
		}
		return list;
	}
}