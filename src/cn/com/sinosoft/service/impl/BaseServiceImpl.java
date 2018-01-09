package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.BaseDao;
import cn.com.sinosoft.service.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<T, PK extends Serializable>
  implements BaseService<T, PK>
{
  public static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
  private BaseDao<T, PK> baseDao;

  public BaseDao<T, PK> getBaseDao()
  {
    return this.baseDao;
  }

  public void setBaseDao(BaseDao<T, PK> baseDao) {
    this.baseDao = baseDao;
  }

  public T get(PK id) {
    return this.baseDao.get(id);
  }

  public T load(PK id) {
    return this.baseDao.load(id);
  }

  public List<T> get(PK[] ids) {
    return this.baseDao.get(ids);
  }

  public T get(String propertyName, Object value) {
    return this.baseDao.get(propertyName, value);
  }

  public List<T> getList(String propertyName, Object value) {
    return this.baseDao.getList(propertyName, value);
  }

  public List<T> getAll() {
    return this.baseDao.getAll();
  }

  public Long getTotalCount() {
    return this.baseDao.getTotalCount();
  }

  public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
    return this.baseDao.isUnique(propertyName, oldValue, newValue);
  }

  public boolean isExist(String propertyName, Object value) {
    return this.baseDao.isExist(propertyName, value);
  }

  public PK save(T entity) {
    return this.baseDao.save(entity);
  }

  public void update(T entity) {
    this.baseDao.update(entity);
  }

  public void delete(T entity) {
    this.baseDao.delete(entity);
  }

  public void delete(PK id) {
    this.baseDao.delete(id);
  }

  public void delete(PK[] ids) {
    this.baseDao.delete(ids);
  }

  public void flush() {
    this.baseDao.flush();
  }

  public void clear() {
    this.baseDao.clear();
  }

  public void evict(Object object) {
    this.baseDao.evict(object);
  }

  public Pager findByPager(Pager pager) {
    return this.baseDao.findByPager(pager);
  }

  public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
    return this.baseDao.findByPager(pager, detachedCriteria);
  }

  public Pager findByPagerQbs(Pager pager)
  {
    return this.baseDao.findByPagerQbs(pager);
  }

  public Pager findByPagerQbs(Pager pager, DetachedCriteria detachedCriteria)
  {
    return this.baseDao.findByPager(pager, detachedCriteria);
  }

  public List<T> findByQBs(List<QueryBuilder> qbs, String orderBy, String orderType)
  {
    return this.baseDao.findByQBs(qbs, orderBy, orderType);
  }
}