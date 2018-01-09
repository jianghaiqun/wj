package cn.com.sinosoft.dao;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.QueryBuilder;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface BaseDao<T, PK extends Serializable> 
{
  public abstract T get(PK paramPK);

  public abstract T load(PK paramPK);

  public abstract List<T> get(PK[] paramArrayOfPK);

  public abstract T get(String paramString, Object paramObject);

  public abstract List<T> getList(String paramString, Object paramObject);

  public abstract List<T> getAll();

  public abstract Long getTotalCount();

  public abstract boolean isUnique(String paramString, Object paramObject1, Object paramObject2);

  public abstract boolean isExist(String paramString, Object paramObject);

  public abstract PK save(T paramT);

  public abstract void update(T paramT);

  public abstract void delete(T paramT);

  public abstract void delete(PK paramPK);

  public abstract void delete(PK[] paramArrayOfPK);

  public abstract void flush();

  public abstract void clear();

  public abstract void evict(Object paramObject);

  public abstract Pager findByPager(Pager paramPager);

  public abstract Pager findByPager(Pager paramPager, DetachedCriteria paramDetachedCriteria);

  public abstract List<T> findByQBs(List<QueryBuilder> paramList, String paramString1, String paramString2);

  public abstract Pager findByPagerQbs(Pager paramPager);

  public abstract Pager findByPagerQbs(Pager paramPager, DetachedCriteria paramDetachedCriteria);
}