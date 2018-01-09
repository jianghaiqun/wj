package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.ArticleCategory;
import cn.com.sinosoft.entity.ArticleHome;
import cn.com.sinosoft.service.ArticleCategoryService;
import cn.com.sinosoft.service.ArticleHomeService;
import cn.com.sinosoft.service.ArticleService;
import cn.com.sinosoft.util.SerialNumberUtil;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;
import java.util.Date;
import java.util.List;

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
public class ArticleHomeAction extends BaseAdminAction {

	private static final long serialVersionUID = 2936525791810867774L;
	private Article article;
	private ArticleHome articleHome;
	private List<ArticleCategory> articleCategoryTreeList;
	private String title;// 栏目名称
	private String articleCategoryId;// 栏目名称
//	private int myId = 0;
	@Resource
	private ArticleService articleService;
	
	@Resource
	private ArticleHomeService articleHomeService;
	@Resource
	private ArticleCategoryService articleCategoryService;

	// 添加首页文章
	public String add() {
		return INPUT;
	}
	// 编辑
	public String edit() {
		articleHome = articleHomeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = articleHomeService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
		articleHomeService.delete(ids);
		flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
		//	@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
		//	@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!"),
		//	@RequiredStringValidator(fieldName = "article.content", message = "文章内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
//		articleHome = new ArticleHome();
/*		articleHome.setArticleCategoryId1("1");
		articleHome.setArticleCategoryId2("2");
		articleHome.setArticleCategoryId3("3");
		articleHome.setArticleCategoryId4("4");
		articleHome.setArticleCategoryId5("5");
	*/
		articleHome =new ArticleHome();
		articleHome.setTitle(title);
		articleHome.setArticleCategoryId(articleCategoryId);
		articleHome.setHomeSn(SerialNumberUtil.buildHomeSn());
		articleHomeService.save(articleHome);
		flushCache();
		redirectionUrl = "article_home!list.action";
		return SUCCESS;
	}
/*/==========================================添加首页文章配置savehome============================================================================
	@InputConfig(resultName = "error")
	public String savehome() throws Exception {
//		articleHome.setMarkId("1");
		//System.out.println("");
//		articleHome.setArticleCategoryId("100000");
		articleHome.setMarkName("新闻动态");
		articleHomeService.save(articleHome);
//		flushCache();
//		redirectionUrl = "article!edithome.action";
		return SUCCESS;
	}
//==========================================添加首页文章配置savehome============================================================================*/

	// 更新
	@Validations(
		requiredStrings = { 
//			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
//			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!"),
//			@RequiredStringValidator(fieldName = "article.content", message = "文章内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		ArticleHome persistent = articleHomeService.load(id);
	//	BeanUtils.copyProperties(article, persistent, new String[] {"id", "createDate", "modifyDate", "pageCount", "htmlFilePath", "hits"});
		articleHomeService.update(persistent);
		flushCache();
		redirectionUrl = "article_home!list.action";
		return SUCCESS;
	}
	
	public String getArticleCategoryId() {
		return articleCategoryId;
	}
	public void setArticleCategoryId(String articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

}