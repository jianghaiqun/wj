package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 实体类 - 管理员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT7603DDA2CB064D51962786F66C07F0DB
 * ============================================================================
 */

@Entity
public class ArticleHome extends BaseEntity{
	
	private static final long serialVersionUID = -4835795489216531099L;
	//========================新添加=============================
	public static final int MAX_ARTICLE_LIST_COUNT = 20;// 首页布局文章列表最大文章数

	public static final int MAX_PAGE_CONTENT_COUNT = 2000;// 内容分页每页最大字数
	public static final int DEFAULT_ARTICLE_LIST_PAGE_SIZE = 20;// 文章列表默认每页显示数
	//========================新添加=============================
	private String title;// 栏目名称
	private String articleCategoryId;
//	private ArticleCategory articleCategory;// 文章分类
	private String homeSn;// 首页布局编号
	@Column(unique = true, updatable = false, nullable = false)
	public String getHomeSn() {
		return homeSn;
	}
	public void setHomeSn(String homeSn) {
		this.homeSn = homeSn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArticleCategoryId() {
		return articleCategoryId;
	}
	public void setArticleCategoryId(String articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}
	
}