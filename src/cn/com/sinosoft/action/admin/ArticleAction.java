package cn.com.sinosoft.action.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;


import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.ArticleCategory;
import cn.com.sinosoft.entity.ArticleHome;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.service.ArticleCategoryService;
import cn.com.sinosoft.service.ArticleHomeService;
import cn.com.sinosoft.service.ArticleService;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 文章
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT8DDD613CBE6FE3988F4CE19EAC8724CB
 * ============================================================================
 */

@ParentPackage("admin")
public class ArticleAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

	private Article article;
	private ArticleHome articleHome;
	private List<ArticleCategory> articleCategoryTreeList;
	private List<Channel> articleCategoryChannelList;//渠道
	private List<Channel> channelList;


	@Resource
	private ArticleService articleService;
	
	@Resource
	private ArticleHomeService articleHomeService;
	@Resource
	private ArticleCategoryService articleCategoryService;

	// 添加
	public String add() {
		return INPUT;
	}
	// 添加首页文章
	public String addhome() {
		return "inputhome";
	}

	// 编辑
	public String edit() {
		article = articleService.load(id);
		return INPUT;
	}
	// 编辑首页文章管理类
	public String edithome() {
	//	article = articleService.load(id);
		return "inputhome";
	}

	// 列表
	public String list() {
		pager = articleService.findByPager(pager);
		return LIST;
	}
	// 首页文章列表
	public String listhome() {
		pager = articleHomeService.findByPager(pager);
		return "listhome";
	}

	// 删除
	public String delete() throws Exception {
		articleService.delete(ids);
		flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!"),
			@RequiredStringValidator(fieldName = "article.content", message = "文章内容不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "article.isPublication", message = "是否发布不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isTop", message = "是否置顶不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isRecommend", message = "是否推荐不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		article.setHits(0);
		article.setChannelSet(new HashSet<Channel>(channelList));
		articleService.save(article);
		flushCache();
		redirectionUrl = "article!list.action";
		return SUCCESS;
	}
//==========================================添加首页文章配置savehome============================================================================
	@InputConfig(resultName = "error")
	public String savehome() throws Exception {
//		articleHome.setMarkId("1");
		//System.out.println("");
//		articleHome.setArticleCategoryId("100000");
//		articleHome.setMarkName("新闻动态");
//		articleHomeService.save(articleHome);
//		flushCache();
//		redirectionUrl = "article!edithome.action";
		return SUCCESS;
	}
//==========================================添加首页文章配置savehome============================================================================

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!"),
			@RequiredStringValidator(fieldName = "article.content", message = "文章内容不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "article.isPublication", message = "是否发布不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isTop", message = "是否置顶不允许为空!"),
			@RequiredFieldValidator(fieldName = "article.isRecommend", message = "是否推荐不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Article persistent = articleService.load(id);
		article.setChannelSet(new HashSet<Channel>(channelList));
		BeanUtils.copyProperties(article, persistent, new String[] {"id", "createDate", "modifyDate", "pageCount", "htmlFilePath", "hits"});
		articleService.update(persistent);
		flushCache();
		redirectionUrl = "article!list.action";
		return SUCCESS;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	

	public List<Channel> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}
	public List<ArticleCategory> getArticleCategoryTreeList() {
		articleCategoryTreeList = articleCategoryService.getArticleCategoryTreeList();
		return articleCategoryTreeList;
	}

	public void setArticleCategoryTreeList(List<ArticleCategory> articleCategoryTreeList) {
		this.articleCategoryTreeList = articleCategoryTreeList;
	}
	
	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}
	public List<Channel> getArticleCategoryChannelList() {
		articleCategoryChannelList=articleCategoryService.getArticleCategoryChannelList();
		return articleCategoryChannelList;
	}

	public void setArticleCategoryChannelList(
			List<Channel> articleCategoryChannelList) {
		this.articleCategoryChannelList = articleCategoryChannelList;
	}
}