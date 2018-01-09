package cn.com.sinosoft.action.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.entity.PresentCategory;
import cn.com.sinosoft.service.PresentCategoryService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTF8D13DDE8B51433D52B3F73D96C223F8
 * ============================================================================
 */

@ParentPackage("admin")
public class PresentCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = 3066159260207583127L;
	
	private String parentId;
	private String channelId;
	private PresentCategory presentCategory;
	private List<PresentCategory> presentCategoryTreeList;
	@Resource
	private PresentCategoryService presentCategoryService;
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		presentCategory = presentCategoryService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}

	// 删除
	public String delete() {
		PresentCategory presentCategory = presentCategoryService.load(id);
		Set<PresentCategory> childrenPresentCategorySet = presentCategory.getChildren();
		redirectionUrl = "present_category!list.action";
		if (childrenPresentCategorySet != null && childrenPresentCategorySet.size() > 0) {
			addActionError("此商品分类存在下级分类，删除失败!");
			return ERROR;
		}
		Set<Present> presentSet = presentCategory.getPresentSet();
		if (presentSet != null && presentSet.size() > 0) {
			addActionError("此商品分类下存在商品，删除失败!");
			return ERROR;
		}
		presentCategoryService.delete(id);
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "presentCategory.name", message = "分类名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "presentCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "presentCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(parentId)) {
			PresentCategory parent = presentCategoryService.load(parentId);
			presentCategory.setParent(parent);
		} else {
			presentCategory.setParent(null);
		}
		presentCategoryService.save(presentCategory);
		redirectionUrl = "present_category!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "presentCategory.name", message = "分类名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "presentCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "presentCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		PresentCategory persistent = presentCategoryService.load(id);
		BeanUtils.copyProperties(presentCategory, persistent, new String[]{"id", "createDate", "modifyDate", "path", "parent", "children", "presentSet"});
		presentCategoryService.update(persistent);
		redirectionUrl = "present_category!list.action";
		return SUCCESS;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public PresentCategory getPresentCategory() {
		return presentCategory;
	}

	public void setPresentCategory(PresentCategory presentCategory) {
		this.presentCategory = presentCategory;
	}

	public List<PresentCategory> getPresentCategoryTreeList() {
		presentCategoryTreeList = presentCategoryService.getPresentCategoryTreeList();
		return presentCategoryTreeList;
	}

	public void setPresentCategoryTreeList(List<PresentCategory> presentCategoryTreeList) {
		this.presentCategoryTreeList = presentCategoryTreeList;
	}

}