package cn.com.sinosoft.dao.impl;
import java.util.List;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.ArticleHomeDao;
import cn.com.sinosoft.entity.ArticleHome;

@Repository
public class ArticleHomeDaoImpl extends BaseDaoImpl<ArticleHome, String> implements ArticleHomeDao {

	@SuppressWarnings("unchecked")
	public String getLastHomeSn() {
		String hql = "from ArticleHome as articleHome order by articleHome.createDate desc";
		List<ArticleHome> articleHomeList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (articleHomeList != null && articleHomeList.size() > 0) {
			return articleHomeList.get(0).getHomeSn();
		} else {
			return null;
		}
	}

	@Override
	public String findByHomeSn(String i) {
		String hql = "select articleHome.articleCategoryId from ArticleHome as articleHome where articleHome.homeSn = ? order by articleHome.createDate desc";
		return (String)getSession().createQuery(hql).setParameter(0, i).uniqueResult();
	}
}