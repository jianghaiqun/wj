package cn.com.sinosoft.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 * 实体类 - 渠道
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * ============================================================================
 */

@Entity
public class Channel extends BaseEntity {
	private static final long serialVersionUID = -4134998295214560010L;
	private String name;// 渠道名称
	private String extended;// 扩展属性

	
	private Set<ProductCategory> productcategorySet;// 产品分类
	private Set<Product> productSet;// 产品
	private Set<ArticleCategory> articlecategorySet;//文章分类
	private Set<Article> articleSet;//文章

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getExtended() {
		return extended;
	}

	public void setExtended(String extended) {
		this.extended = extended;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "channelSet")
	public Set<ProductCategory> getProductcategorySet() {
		return productcategorySet;
	}

	public void setProductcategorySet(Set<ProductCategory> productcategorySet) {
		this.productcategorySet = productcategorySet;
	}
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "channelSet")
	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "channelSet")
	public Set<ArticleCategory> getArticlecategorySet() {
		return articlecategorySet;
	}

	public void setArticlecategorySet(Set<ArticleCategory> articlecategorySet) {
		this.articlecategorySet = articlecategorySet;
	}
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "channelSet")
	public Set<Article> getArticleSet() {
		return articleSet;
	}

	public void setArticleSet(Set<Article> articleSet) {
		this.articleSet = articleSet;
	}
	
}