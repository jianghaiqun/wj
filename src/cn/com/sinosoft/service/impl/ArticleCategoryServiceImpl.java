package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cn.com.sinosoft.dao.ArticleCategoryDao;
import cn.com.sinosoft.dao.ChannelDao;
import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.ArticleCategory;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.ProductCategory;
import cn.com.sinosoft.service.ArticleCategoryService;

/**
 * Service实现类 - 文章分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTCD623CA67EE561A340B34E79BEDEE5CE
 * ============================================================================
 */

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory, String> implements ArticleCategoryService {

	@Resource
	private ArticleCategoryDao articleCategoryDao;
	@Resource
	private ChannelDao channelDao;

	@Resource
	public void setBaseDao(ArticleCategoryDao articleCategoryDao) {
		super.setBaseDao(articleCategoryDao);
	}
	
	@Cacheable(modelId = "caching")
	public List<ArticleCategory> getRootArticleCategoryList() {
		List<ArticleCategory> rootArticleCategoryList = articleCategoryDao.getRootArticleCategoryList();
		if (rootArticleCategoryList != null) {
			for (ArticleCategory rootArticleCategory : rootArticleCategoryList) {
				Hibernate.initialize(rootArticleCategory);
			}
		}
		return rootArticleCategoryList;
	}
	
	@Cacheable(modelId = "caching")
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory) {
		List<ArticleCategory> parentArticleCategoryList = articleCategoryDao.getParentArticleCategoryList(articleCategory);
		if (parentArticleCategoryList != null) {
			for (ArticleCategory parentArticleCategory : parentArticleCategoryList) {
				Hibernate.initialize(parentArticleCategory);
			}
		}
		return parentArticleCategoryList;
	}
	//根据渠道选择顶级文章集合
			@Cacheable(modelId = "caching")
			public List<ArticleCategory> getRootArticleCategoryChannelList(String[]  channel) {
				List<ArticleCategory> rootArticleCategoryChannelLists = articleCategoryDao.getRootArticleCategoryChannelList(channel);
				if (rootArticleCategoryChannelLists != null) {
					for (ArticleCategory rootArticleCategoryChannel : rootArticleCategoryChannelLists) {
						Hibernate.initialize(rootArticleCategoryChannel);
					}
				}
				return rootArticleCategoryChannelLists;
			}
		
	
	public List<ArticleCategory> getParentArticleCategoryList(Article article) {
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> articleCategoryList = new ArrayList<ArticleCategory>();
		articleCategoryList.addAll(this.getParentArticleCategoryList(articleCategory));
		articleCategoryList.add(articleCategory);
		return articleCategoryList;
	}
	
	public List<ArticleCategory> getArticleCategoryPathList(ArticleCategory articleCategory) {
		List<ArticleCategory> articleCategoryPathList = new ArrayList<ArticleCategory>();
		articleCategoryPathList.addAll(this.getParentArticleCategoryList(articleCategory));
		articleCategoryPathList.add(articleCategory);
		return articleCategoryPathList;
	}
	
	public List<ArticleCategory> getArticleCategoryPathList(Article article) {
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> articleCategoryList = new ArrayList<ArticleCategory>();
		articleCategoryList.addAll(this.getParentArticleCategoryList(articleCategory));
		articleCategoryList.add(articleCategory);
		return articleCategoryList;
	}
	
	@Cacheable(modelId = "caching")
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory) {
		List<ArticleCategory> childrenArticleCategoryList = articleCategoryDao.getChildrenArticleCategoryList(articleCategory);
		if (childrenArticleCategoryList != null) {
			for (ArticleCategory childrenArticleCategory : childrenArticleCategoryList) {
				Hibernate.initialize(childrenArticleCategory);
			}
		}
		return childrenArticleCategoryList;
	}
	
	public List<ArticleCategory> getChildrenArticleCategoryList(Article article) {
		ArticleCategory articleCategory = article.getArticleCategory();
		List<ArticleCategory> articleCategoryList = getChildrenArticleCategoryList(articleCategory);
		if (articleCategoryList == null) {
			articleCategoryList = new ArrayList<ArticleCategory>();
		}
		articleCategoryList.add(articleCategory);
		return articleCategoryList;
	}
	//获取渠道列表
	public List<Channel> getArticleCategoryChannelList(){
		List<Channel> allProductCategoryChannelList=channelDao.getAll();
		return  allProductCategoryChannelList;	
	}
	
	@Cacheable(modelId = "caching")
	public List<ArticleCategory> getArticleCategoryTreeList() {
		List<ArticleCategory> allArticleCategoryList = this.getAll();
		return recursivArticleCategoryTreeList(allArticleCategoryList, null, null);
	}
	
	// 递归父类排序分类树
	private List<ArticleCategory> recursivArticleCategoryTreeList(List<ArticleCategory> allArticleCategoryList, ArticleCategory p, List<ArticleCategory> temp) {
		if (temp == null) {
			temp = new ArrayList<ArticleCategory>();
		}
		for (ArticleCategory articleCategory : allArticleCategoryList) {
			ArticleCategory parent = articleCategory.getParent();
			if ((p == null && parent == null) || (articleCategory != null && parent == p)) {
				temp.add(articleCategory);
				if (articleCategory.getChildren() != null && articleCategory.getChildren().size() > 0) {
					recursivArticleCategoryTreeList(allArticleCategoryList, articleCategory, temp);
				}
			}
		}
		return temp;
	}

	@Override
	@Cacheable(modelId = "caching")
	public List<ArticleCategory> getAll() {
		List<ArticleCategory> allArticleCategoryList = articleCategoryDao.getAll();
		if (allArticleCategoryList != null) {
			for (ArticleCategory articleCategory : allArticleCategoryList) {
				Hibernate.initialize(articleCategory);
			}
		}
		return allArticleCategoryList;
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(ArticleCategory articleCategory) {
		articleCategoryDao.delete(articleCategory);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		articleCategoryDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		articleCategoryDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(ArticleCategory articleCategory) {
		return articleCategoryDao.save(articleCategory);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(ArticleCategory articleCategory) {
		articleCategoryDao.update(articleCategory);
	}

}