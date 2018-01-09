package cn.com.sinosoft.dao.impl;

import java.util.Date;
import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
 
import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.ArticleDao;
import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.ArticleCategory;
import cn.com.sinosoft.entity.Channel;

/**
 * Dao实现类 - 文章
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT818712BD2CE092B961D2E741A622D073
 * ============================================================================
 */

@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article, String> implements ArticleDao {

	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(ArticleCategory articleCategory) {
		String hql = "from Article as article where article.isPublication = ? and article.articleCategory.path like ? order by article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory.getPath() + "%").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(int firstResult, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? order by article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(ArticleCategory articleCategory, int firstResult, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and article.articleCategory.path like ? and article.id='402881e83639944c013639c9ccda000e' order by article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory.getPath() + "%").setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(Channel channel,ArticleCategory articleCategory, int firstResult, int maxResults) {
		String hql = "from Article as article left join  fetch article.channelSet  as  channel where article.isPublication = ? and article.articleCategory.path like ? and channel=? order by article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory.getPath() + "%").setParameter(2, channel).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(Date beginDate, Date endDate, int firstResult, int maxResults) {
		if (beginDate != null && endDate == null) {
			String hql = "from Article as article where article.isPublication = ? and article.createDate >= ? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else if (endDate != null && beginDate == null) {
			String hql = "from Article as article where article.isPublication = ? and article.createDate <= ? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else if (endDate != null && beginDate != null) {
			String hql = "from Article as article where article.isPublication = ? and article.createDate >= ? and article.createDate <= ? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setParameter(2, endDate).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else {
			String hql = "from Article as article where article.isPublication = ? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
	}
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(Channel channel,Date beginDate, Date endDate, int firstResult, int maxResults) {
		if (beginDate != null && endDate == null) {
			String hql = "from Article as article left join  fetch article.channelSet  as  channel where article.isPublication = ? and article.createDate >= ? and channel=? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setParameter(2, channel).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else if (endDate != null && beginDate == null) {
			String hql = "from Article as article left join  fetch article.channelSet  as  channel where article.isPublication = ? and article.createDate <= ? and channel=? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, endDate).setParameter(2, channel).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else if (endDate != null && beginDate != null) {
			String hql = "from Article as article left join  fetch article.channelSet  as  channel where article.isPublication = ? and article.createDate >= ? and article.createDate <= ? and channel=? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, beginDate).setParameter(2, endDate).setParameter(3, channel).setFirstResult(firstResult).setMaxResults(maxResults).list();
		} else {
			String hql = "from Article as article left join  fetch article.channelSet  as  channel where article.isPublication = ? and channel=? order by article.isTop desc, article.createDate desc";
			return getSession().createQuery(hql).setParameter(0, true).setParameter(1, channel).setFirstResult(firstResult).setMaxResults(maxResults).list();
		}
	}
	
	public Pager getArticlePager(ArticleCategory articleCategory, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
		detachedCriteria.createAlias("articleCategory", "articleCategory");
		detachedCriteria.add(Restrictions.or(Restrictions.eq("articleCategory", articleCategory), Restrictions.like("articleCategory.path", articleCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isPublication", true));
		return super.findByPager(pager, detachedCriteria);
	}
	public Pager getArticlePager(ArticleCategory articleCategory,Channel channel, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
		detachedCriteria.createAlias("articleCategory", "articleCategory").createAlias("channelSet", "channelSet");
		detachedCriteria.add(Restrictions.or(Restrictions.eq("articleCategory", articleCategory), Restrictions.like("articleCategory.path", articleCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isPublication", true));
		detachedCriteria.add(Restrictions.eq("channelSet.id", "2"));
		//detachedCriteria.add(Restrictions.sqlRestriction("from Article as article left join  fetch article.channelSet  as  channel where channel='1'"));
		return super.findByPager(pager, detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<Article> getRecommendArticleList(int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and article.isRecommend = ? order by article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Article> getRecommendArticleList(ArticleCategory articleCategory, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and article.isRecommend = ? and (articleCategory = ? or article.articleCategory.path like ?) order by article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, articleCategory).setParameter(3, articleCategory.getPath() + "%").list();
	}
	@SuppressWarnings("unchecked")
	public List<Article> getRecommendArticleList(ArticleCategory articleCategory,Channel channel, int maxResults) {
		String hql = "from Article as article left join  fetch article.channelSet  as  channel where article.isPublication = ? and article.isRecommend = ? and (article.articleCategory = ? or article.articleCategory.path like ?) and channel=? order by article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, true).setParameter(2, articleCategory).setParameter(3, articleCategory.getPath() + "%").setParameter(4, channel).list();
	}

	@SuppressWarnings("unchecked")
	public List<Article> getHotArticleList(int maxResults) {
		String hql = "from Article as article where article.isPublication = ? order by article.hits desc, article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Article> getHotArticleList(ArticleCategory articleCategory, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and (articleCategory = ? or article.articleCategory.path like ?) order by article.hits desc, article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory).setParameter(2, articleCategory.getPath() + "%").list();
	}
	@SuppressWarnings("unchecked")
	public List<Article> getHotArticleList(ArticleCategory articleCategory,Channel channel, int maxResults) {
		String hql = "from Article as article left join  fetch article.channelSet  as  channel where article.isPublication = ? and (article.articleCategory = ? or article.articleCategory.path like ?) and channel = ? order by article.hits desc, article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory).setParameter(2, articleCategory.getPath() + "%").setParameter(3, channel).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getNewArticleList(int maxResults) {
		String hql = "from Article as article where article.isPublication = ? order by article.createDate asc";
		return getSession().createQuery(hql).setParameter(0, true).list();
	}

	@SuppressWarnings("unchecked")
	public List<Article> getNewArticleList(ArticleCategory articleCategory, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and (articleCategory = ? or article.articleCategory.path like ?) order by article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory).setParameter(2, articleCategory.getPath() + "%").list();
	}
	@SuppressWarnings("unchecked")
	public List<Article> getNewArticleList(ArticleCategory articleCategory, Channel channel,int maxResults) {
		String hql = "from Article as article left join  fetch article.channelSet  as  channel where article.isPublication = ? and (article.articleCategory = ? or article.articleCategory.path like ?) and channel = ? order by article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory).setParameter(2, articleCategory.getPath() + "%").setParameter(3, channel).list();
	}
	
	// 根据isTop、createDate进行排序
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getAll() {
		String hql = "from Article as article order by article.isTop desc, article.createDate desc";
		return getSession().createQuery(hql).list();
	}
	/**
	 * 首页文章布局1,2,3,4,5
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getHomeArticleList(ArticleCategory articleCategory) {
		String hql = "from Article as article where article.isPublication = ? and (articleCategory = ? or article.articleCategory.path like ?) order by article.createDate desc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, articleCategory).setParameter(2, articleCategory.getPath() + "%").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticleTreeList() {
		String hql = "from Article as article where article.isPublication = ? order by article.createDate asc";
		return getSession().createQuery(hql).setParameter(0, true).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getAllZj() {
		String hql = "from Article as article where article.isPublication = ? and article.articleCategory.name = ? order by article.createDate asc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, "专家点评").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getAllSp() {
		String hql = "from Article as article where article.isPublication = ? and article.articleCategory.name = ? order by article.createDate asc";
		return getSession().createQuery(hql).setParameter(0, true).setParameter(1, "视频介绍").list();
	}

}