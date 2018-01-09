package cn.com.sinosoft.dao.impl;

import cn.com.sinosoft.bean.OrderBys;
import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	protected static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	private Class<T> entityClass;
	protected SessionFactory sessionFactory;

	public BaseDaoImpl() {
		this.entityClass = null;
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if ((type instanceof ParameterizedType)) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = ((Class) parameterizedType[0]);
		}
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}

	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + this.entityClass.getName()
				+ " as model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids)
				.list();
	}

	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model."
				+ propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value)
				.uniqueResult();
	}

	public List<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model."
				+ propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	public List<T> getAll() {
		String hql = "from " + this.entityClass.getName();
		return getSession().createQuery(hql).list();
	}

	public Long getTotalCount() {
		String hql = "select count(*) from " + this.entityClass.getName();
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if ((newValue == oldValue) || (newValue.equals(oldValue))) {
			return true;
		}
		if (((newValue instanceof String))
				&& (oldValue != null)
				&& (StringUtils.equalsIgnoreCase((String) oldValue,
						(String) newValue))) {
			return true;
		}

		Object object = get(propertyName, newValue);
		return object == null;
	}

	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		Object object = get(propertyName, value);
		return object != null;
	}

	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}
	
	public PK save1(Object entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}

	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		Object entity = load(id);
		getSession().delete(entity);
	}

	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			getSession().delete(entity);
		}
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}

	public Pager findByPager(Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		return findByPager(pager, detachedCriteria);
	}

	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		Pager.OrderType orderType = pager.getOrderType();

		Criteria criteria = detachedCriteria
				.getExecutableCriteria(getSession());
		if ((StringUtils.isNotEmpty(property))
				&& (StringUtils.isNotEmpty(keyword))) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property,
						".");
				String propertySuffix = StringUtils.substringAfter(property,
						".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}

		Integer totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber.intValue() - 1)
				* pageSize.intValue());
		criteria.setMaxResults(pageSize.intValue());
		if ((StringUtils.isNotEmpty(orderBy)) && (orderType != null)) {
			if (orderType == Pager.OrderType.asc)
				criteria.addOrder(Order.asc(orderBy));
			else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}

	public List<T> findByQBs(List<QueryBuilder> qbs, String orderBy,
			String orderType) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		Criteria criteria = detachedCriteria
				.getExecutableCriteria(getSession());
		if ((qbs != null) && (qbs.size() > 0)) {
			for (QueryBuilder qb : qbs) {
				if ("like".equals(qb.getSign()))
					criteria.add(Restrictions.like(qb.getProperty(),
							"%" + qb.getValue() + "%"));
				else if ("=".equals(qb.getSign()))
					criteria.add(Restrictions.eq(qb.getProperty(),
							qb.getValue()));
				else if (">".equals(qb.getSign()))
					criteria.add(Restrictions.gt(qb.getProperty(),
							qb.getValue()));
				else if ("<".equals(qb.getSign()))
					criteria.add(Restrictions.lt(qb.getProperty(),
							qb.getValue()));
				else if (">=".equals(qb.getSign()))
					criteria.add(Restrictions.ge(qb.getProperty(),
							qb.getValue()));
				else if ("<=".equals(qb.getSign()))
					criteria.add(Restrictions.le(qb.getProperty(),
							qb.getValue()));
				else if ("<>".equals(qb.getSign()))
					criteria.add(Restrictions.ne(qb.getProperty(),
							qb.getValue()));
				else if ("companynotice".equals(qb.getSign()))
					criteria.add(Restrictions
							.sqlRestriction(" 0=0 connect by PRIOR ComCode = UpComCode start with ComCode = '"
									+ qb.getValue() + "'"));
				else if ("birthday".equals(qb.getSign())) {
					criteria.add(Restrictions.sqlRestriction("to_char("
							+ qb.getProperty() + ",'MM-dd') <= '"
							+ qb.getValue() + "' and to_char("
							+ qb.getProperty() + "+15,'MM-dd') >= '"
							+ qb.getValue() + "' "));
				} else if ("notNULL".equals(qb.getSign())) {
					criteria.add(Restrictions.isNotNull(qb.getProperty()));
				} else if ("ISNULL".equals(qb.getSign())) {
					criteria.add(Restrictions.isNull(qb.getProperty()));
				} else if ("isNULLorEMPTY".equals(qb.getSign())) {
					criteria.add(Restrictions .sqlRestriction("("+qb.getProperty()+" is null or "+qb.getProperty()+" = '')"));
				}
			}
		}
		if ((StringUtils.isNotEmpty(orderBy)) && (orderType != null)) {
			if ("asc".equals(orderType))
				criteria.addOrder(Order.asc(orderBy));
			else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		return criteria.list();
	}

	public Pager findByPagerQbs(Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		return findByPagerQbs(pager, detachedCriteria);
	}

	public Pager findByPagerQbs(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String orderBy = pager.getOrderBy();
		Pager.OrderType orderType = pager.getOrderType();
		Criteria criteria = detachedCriteria
				.getExecutableCriteria(getSession());
		if ((pager.getProperties() != null)
				&& (pager.getProperties().size() > 0)) {
			for (QueryBuilder qb : pager.getProperties()) {
				if ("like".equals(qb.getSign()))
					criteria.add(Restrictions.like(qb.getProperty(),
							"%" + qb.getValue() + "%"));
				else if ("or".equals(qb.getSign()))
					criteria.add(Restrictions.or(
							Restrictions.like(qb.getProperty(), qb.getValue()),
							Restrictions.like(qb.getProperty(), qb.getValue())));
				else if ("=".equals(qb.getSign()))
					criteria.add(Restrictions.eq(qb.getProperty(),
							qb.getValue()));
				else if (">".equals(qb.getSign()))
					criteria.add(Restrictions.gt(qb.getProperty(),
							qb.getValue()));
				else if ("<".equals(qb.getSign()))
					criteria.add(Restrictions.lt(qb.getProperty(),
							qb.getValue()));
				else if (">=".equals(qb.getSign()))
					criteria.add(Restrictions.ge(qb.getProperty(),
							qb.getValue()));
				else if ("<=".equals(qb.getSign()))
					criteria.add(Restrictions.le(qb.getProperty(),
							qb.getValue()));
				else if ("<>".equals(qb.getSign()))
					criteria.add(Restrictions.ne(qb.getProperty(),
							qb.getValue()));
				else if ("birthday".equals(qb.getSign()))
					criteria.add(Restrictions.sqlRestriction("to_char("
							+ qb.getProperty() + ",'MM-dd') <= '"
							+ qb.getValue() + "' and to_char("
							+ qb.getProperty() + "+15,'MM-dd') >= '"
							+ qb.getValue() + "' "));
				else if ("contNo".equals(qb.getSign()))
					criteria.add(Restrictions.sqlRestriction("to_char("
							+ qb.getProperty() + ",'MM-dd') <= '"
							+ qb.getValue() + "' and to_char("
							+ qb.getProperty() + "+30,'MM-dd') >= '"
							+ qb.getValue() + "' "));
				else if ("groupinfo".equals(qb.getSign()))
					criteria.add(Restrictions
							.sqlRestriction(" 0=0 connect by PRIOR user_Serial = upper_Agent start with user_Serial = '"
									+ qb.getValue() + "'"));
				else if ("companynotice".equals(qb.getSign())) {
					criteria.add(Restrictions
							.sqlRestriction(" filiale_serial in (select  comcode from fdcom connect by PRIOR ComCode = UpComCode start with ComCode= '"
									+ qb.getValue() + "') "));
				}
			}
		}
		Integer totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber.intValue() - 1)
				* pageSize.intValue());
		criteria.setMaxResults(pageSize.intValue());
		if ((StringUtils.isNotEmpty(orderBy)) && (orderType != null)) {
			if (orderType == Pager.OrderType.asc)
				criteria.addOrder(Order.asc(orderBy));
			else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		if ((pager.getOrderBys() != null) && (pager.getOrderBys().size() > 0)) {
			for (OrderBys ob : pager.getOrderBys()) {
				if (ob.getOrderByType().equals("asc"))
					criteria.addOrder(Order.asc(ob.getOrderBy()));
				else {
					criteria.addOrder(Order.desc(ob.getOrderBy()));
				}
			}
		}

		pager.setPageCount(Integer.valueOf(getPageCounts(totalCount.intValue(),
				pageSize.intValue())));
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		if (totalCount.intValue() == 0) {
			pager.setPageNumber(Integer.valueOf(0));
		}
		return pager;
	}

	private int getPageCounts(int totalCount, int pageSize) {
		int pageCount = totalCount % pageSize;
		if (pageCount == 0)
			return totalCount / pageSize;
		if (pageCount > 0) {
			return totalCount / pageSize + 1;
		}
		return 0;
	}

}