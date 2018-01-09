package cn.com.sinosoft.action.admin;

import javax.annotation.Resource;


import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.entity.ProductInsType;
import cn.com.sinosoft.service.ProductInsTypeService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品类型
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB9E483EA86B3007D9DF1F8E314D400D2
 * ============================================================================
 */

@ParentPackage("admin")
public class ProductInsTypeAction extends BaseAdminAction {

	private static final long serialVersionUID = 8895838200173152426L;

	private ProductInsType productInsType;

	@Resource
	private ProductInsTypeService productInsTypeService;

	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = productInsType.getName();
		if (productInsTypeService.isUnique("name", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		productInsType = productInsTypeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = productInsTypeService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		productInsTypeService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "productInsType.name", message = "商品类型不允许为空!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		productInsType.setProductInsAttributeList(null);
		productInsTypeService.save(productInsType);
		redirectionUrl = "product_ins_type!list.action";
		return SUCCESS;
	}

	// 更新
	@InputConfig(resultName = "error")
	public String update() {
		ProductInsType persistent = productInsTypeService.load(id);
		BeanUtils.copyProperties(productInsType, persistent, new String[] {"id", "createDate", "modifyDate", "productInsAttributeList", "productSet"});
		productInsTypeService.update(persistent);
		redirectionUrl = "product_ins_type!list.action";
		return SUCCESS;
	}

	public ProductInsType getProductInsType() {
		return productInsType;
	}

	public void setProductInsType(ProductInsType productInsType) {
		this.productInsType = productInsType;
	}

}