package cn.com.sinosoft.dao.impl;

import cn.com.sinosoft.dao.DealDataDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DealDataDaoImpl extends BaseDaoImpl<Object, String> implements
		DealDataDao<Object> {
	@Override
	public boolean submitModel(LinkedHashMap<Object, String> lmap) {
		Session session = null;
		try {
			session = this.sessionFactory.getCurrentSession();
			session.beginTransaction();
			Iterator it = lmap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, String> entry = (Map.Entry<Object, String>) it
						.next();
				Object key = entry.getKey();
				String value = entry.getValue();

				if (value != null && !"".equals(value)) {
					if (key.getClass().getName().indexOf("List") != -1) {

						@SuppressWarnings("unchecked")
						List key2 = (List) key;

						if ("insert".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.save(obj);
							}
						} else if ("delete".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.delete(obj);
							}
						} else if ("update".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.update(obj);
							}
						} else if ("insert&update".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.saveOrUpdate(obj);
							}
						}

					} else {
						if ("insert".equalsIgnoreCase(value)) {
							session.save(key);
						} else if ("delete".equalsIgnoreCase(value)) {
							session.delete(key);
						} else if ("update".equalsIgnoreCase(value)) {
							session.update(key);
						}
					}
				}
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.getTransaction().rollback();
			return false;

		} finally {

		}
	}

	@Override
	public boolean saveAll(LinkedHashMap<Object, String> lmap) {
		Session session = null;
		try {
			session = this.sessionFactory.getCurrentSession();
			Iterator it = lmap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, String> entry = (Map.Entry<Object, String>) it
						.next();
				Object key = entry.getKey();
				String value = entry.getValue();

				if (value != null && !"".equals(value)) {
					if (key.getClass().getName().indexOf("List") != -1) {

						@SuppressWarnings("unchecked")
						List key2 = (List) key;
						//System.out.println("Class1:===="+key.getClass().getName());
						if ("insert".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.save(obj);
							}
						} else if ("delete".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.delete(obj);
							}
						} else if ("update".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.update(obj);
							}
						} else if ("insert&update".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.saveOrUpdate(obj);
							}
						}

					} else {
						//System.out.println("Class2:===="+key.getClass().getName());
						
						if ("insert".equalsIgnoreCase(value)) {
							session.save(key);
						} else if ("delete".equalsIgnoreCase(value)) {
							session.delete(key);
						} else if ("update".equalsIgnoreCase(value)) {
							session.update(key);
						}
					}
				}
			}
			session.flush();
			session.clear();
			return true;
		} catch (Exception e) {
			logger.error("订单保存失败，失败原因：" + e.getMessage(), e);
			return false;
		} finally {

		}
	}
	
}