package cn.com.sinosoft.action.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.ArticleCategory;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.service.ArticleCategoryService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 文章分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT3BAD225777A5BDFDC6BF0F6474A05010
 * ============================================================================
 */

@ParentPackage("admin")
public class ArticleCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = -7786508966240073537L;

	private String parentId;
	private ArticleCategory articleCategory;
	private List<ArticleCategory> articleCategoryTreeList;
	private List<Channel> articleCategoryChannelList;
	private List<Channel> channelList;

	@Resource
	private ArticleCategoryService articleCategoryService;
	
	// ajax根据渠道名称获取所有渠道下的文章分类名称
				@SuppressWarnings("unchecked")
				public String findAllChannelarticleCategory()  {
					String channelnames=channelList.get(0).getId().substring(0, channelList.get(0).getId().length()-1);
					String[] channelname=channelnames.split(",");
					List<ArticleCategory> allarticleCategoryName=articleCategoryService.getRootArticleCategoryChannelList(channelname);
						StringBuilder stringBuilder = new StringBuilder();
						String[] methodNameArray = new String[allarticleCategoryName.size()];
						stringBuilder.append("<option value=\"" + "" + "\">" + "顶级分类"+ "</option>");
						for (int i = 0; i < allarticleCategoryName.size(); i++) {
							methodNameArray[i] = allarticleCategoryName.get(i).getName();
							if(allarticleCategoryName.get(i).getLevel()==0){
							stringBuilder.append("<option value=\"" + allarticleCategoryName.get(i).getId() + "\">" + allarticleCategoryName.get(i).getName() + "</option>");
							}
							else{
								stringBuilder.append("<option value=\"" + allarticleCategoryName.get(i).getId() + "\">" + "------"+allarticleCategoryName.get(i).getName() + "</option>");
							}
						}
						if (stringBuilder.length() == 0) {
							stringBuilder.append("<option value=\"noValue\">无文章分类</option>");
						}
						return ajaxText(stringBuilder.toString());
				}
				
	

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		articleCategory = articleCategoryService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}

	// 删除
	public String delete() {
		ArticleCategory articleCategory = articleCategoryService.load(id);
		Set<ArticleCategory> childrenArticleCategoryList = articleCategory.getChildren();
		if (childrenArticleCategoryList != null && childrenArticleCategoryList.size() > 0) {
			addActionError("此文章分类存在下级分类，删除失败!");
			return ERROR;
		}
		Set<Article> articleSet = articleCategory.getArticleSet();
		if (articleSet != null && articleSet.size() > 0) {
			addActionError("此文章分类下存在文章，删除失败!");
			return ERROR;
		}
		articleCategoryService.delete(id);
		redirectionUrl = "article_category!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "articleCategory.name", message = "分类名称不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "articleCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "articleCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(parentId)) {
			ArticleCategory parent = articleCategoryService.load(parentId);
			articleCategory.setParent(parent);
		} else {
			articleCategory.setParent(null);
		}
		articleCategory.setChannelSet(new HashSet<Channel>(channelList));
		articleCategoryService.save(articleCategory);
		redirectionUrl = "article_category!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "articleCategory.name", message = "分类名称不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "articleCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "articleCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		ArticleCategory persistent = articleCategoryService.load(id);
		articleCategory.setChannelSet(new HashSet<Channel>(channelList));
		BeanUtils.copyProperties(articleCategory, persistent, new String[]{"id", "createDate", "modifyDate", "path", "parent", "children", "articleSet"});
		articleCategoryService.update(persistent);
		redirectionUrl = "article_category!list.action";
		return SUCCESS;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public List<ArticleCategory> getArticleCategoryTreeList() {
		articleCategoryTreeList = articleCategoryService.getArticleCategoryTreeList();
		return articleCategoryTreeList;
	}

	public void setArticleCategoryTreeList(List<ArticleCategory> articleCategoryTreeList) {
		this.articleCategoryTreeList = articleCategoryTreeList;
	}

	public List<Channel> getArticleCategoryChannelList() {
		articleCategoryChannelList=articleCategoryService.getArticleCategoryChannelList();
		return articleCategoryChannelList;
	}

	public void setArticleCategoryChannelList(
			List<Channel> articleCategoryChannelList) {
		this.articleCategoryChannelList = articleCategoryChannelList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

}