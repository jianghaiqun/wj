package cn.com.sinosoft.action.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.entity.ProductInsAttribute;
import cn.com.sinosoft.entity.ProductInsType;
import cn.com.sinosoft.entity.ProductInsAttribute.AttributeInsType;
import cn.com.sinosoft.service.ProductInsAttributeService;
import cn.com.sinosoft.service.ProductInsTypeService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品属性
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTBF29C1240A7C6969E02F8F49566B5C42
 * ============================================================================
 */

@ParentPackage("admin")
public class ProductInsAttributeAction extends BaseAdminAction {

	private static final long serialVersionUID = -7712786079159509171L;

	private ProductInsAttribute productInsAttribute;
	private List<String> attributeOptionList;
	private String productInsTypeId;
	private ProductInsType productInsType;

	@Resource
	private ProductInsAttributeService productInsAttributeService;
	@Resource
	private ProductInsTypeService productInsTypeService;
	
	// 根据productInsTypeId获取已启用的商品属性JSON数据
	public String ajaxProductInsAttribute() {
		ProductInsType productInsType = productInsTypeService.load(productInsTypeId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "createDate", "modifyDate", "productInsType" });
		JSONArray jsonArray = JSONArray.fromObject(productInsType.getEnabledProductInsAttributeList(), jsonConfig);
		return ajaxJson(jsonArray.toString());
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		productInsAttribute = productInsAttributeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		if (StringUtils.isNotEmpty(productInsTypeId)) {
			productInsType = productInsTypeService.load(productInsTypeId);
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductInsAttribute.class);
			detachedCriteria.add(Restrictions.eq("productInsType", productInsType));
			pager = productInsAttributeService.findByPager(pager, detachedCriteria);
		} else {
			pager = productInsAttributeService.findByPager(pager);
		}
		return LIST;
	}

	// 删除
	public String delete() {
		productInsAttributeService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "productInsAttribute.name", message = "商品属性名称不允许为空!"), 
			@RequiredStringValidator(fieldName = "productInsAttribute.productInsType.id", message = "商品类型不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "productInsAttribute.attributeInsType", message = "商品属性类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "productInsAttribute.isRequired", message = "是否必填不允许为空!"),
			@RequiredFieldValidator(fieldName = "productInsAttribute.isEnabled", message = "是否启用不允许为空!"),
			@RequiredFieldValidator(fieldName = "productInsAttribute.orderList", message = "排序不允许为空!") 
		}, 
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "productInsAttribute.orderList", min = "0", message = "排序必须为零或正整数!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		ProductInsAttribute pa = productInsAttributeService.getProductInsAttribute(productInsAttribute.getProductInsType(), productInsAttribute.getName());
		if (pa != null) {
			addActionError("商品属性名称在此商品分类中已存在!");
			return ERROR;
		}
		if (productInsAttribute.getAttributeInsType() == AttributeInsType.select || productInsAttribute.getAttributeInsType() == AttributeInsType.checkbox) {
			if(attributeOptionList == null || attributeOptionList.size() < 1) {
				addActionError("请至少填写一个选项内容!");
				return ERROR;
			}
			Iterator<String> iterator = attributeOptionList.iterator(); 
			while (iterator.hasNext()) {
				String attributeOption = (String) iterator.next();
				if (StringUtils.isEmpty(attributeOption)) {
					iterator.remove();
				}
			}
			productInsAttribute.setAttributeOptionList(attributeOptionList);
		} else {
			productInsAttribute.setAttributeOptionList(null);
		}
		productInsAttributeService.save(productInsAttribute);
		if (StringUtils.isNotEmpty(productInsTypeId)) {
			redirectionUrl = "product_ins_attribute!list.action?productInsTypeId=" + productInsAttribute.getProductInsType().getId();
		} else {
			redirectionUrl = "product_ins_attribute!list.action";
		}
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "productInsAttribute.name", message = "商品属性名称不允许为空!"), 
			@RequiredStringValidator(fieldName = "productInsAttribute.productInsType.id", message = "商品类型不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "productInsAttribute.attributeInsType", message = "商品属性类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "productInsAttribute.isEnabled", message = "是否启用不允许为空!"),
			@RequiredFieldValidator(fieldName = "productInsAttribute.isRequired", message = "是否必填不允许为空!"),
			@RequiredFieldValidator(fieldName = "productInsAttribute.orderList", message = "排序不允许为空!") }, 
			intRangeFields = { 
			@IntRangeFieldValidator(fieldName = "productInsAttribute.orderList", min = "0", message = "排序必须为零或正整数!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		ProductInsAttribute persistent = productInsAttributeService.load(id);
		ProductInsAttribute pa = productInsAttributeService.getProductInsAttribute(productInsAttribute.getProductInsType(), productInsAttribute.getName());
		if (pa != null && pa != persistent) {
			addActionError("商品属性名称在此商品分类中已存在!");
			return ERROR;
		}
		if (productInsAttribute.getAttributeInsType() == AttributeInsType.select || productInsAttribute.getAttributeInsType() == AttributeInsType.checkbox) {
			if(attributeOptionList == null || attributeOptionList.size() < 1) {
				addActionError("请至少填写一个选项内容!");
				return ERROR;
			}
			Iterator<String> iterator = attributeOptionList.iterator(); 
			while (iterator.hasNext()) {
				String attributeOption = (String) iterator.next();
				if (StringUtils.isEmpty(attributeOption)) {
					iterator.remove();
				}
			}
			productInsAttribute.setAttributeOptionList(attributeOptionList);
		} else {
			productInsAttribute.setAttributeOptionList(null);
		}
		BeanUtils.copyProperties(productInsAttribute, persistent, new String[] {"id", "createDate", "modifyDate"});
		productInsAttributeService.update(persistent);
		if (StringUtils.isNotEmpty(productInsTypeId)) {
			redirectionUrl = "product_ins_attribute!list.action?productInsTypeId=" + productInsAttribute.getProductInsType().getId();
		} else {
			redirectionUrl = "product_ins_attribute!list.action";
		}
		return SUCCESS;
	}

	public ProductInsAttribute getProductInsAttribute() {
		return productInsAttribute;
	}

	public void setProductInsAttribute(ProductInsAttribute productInsAttribute) {
		this.productInsAttribute = productInsAttribute;
	}
	
	public List<String> getAttributeOptionList() {
		return attributeOptionList;
	}

	public void setAttributeOptionList(List<String> attributeOptionList) {
		this.attributeOptionList = attributeOptionList;
	}

	// 获取所有商品类型
	public List<ProductInsType> getAllProductInsType() {
		return productInsTypeService.getAll();
	}

	public String getProductInsTypeId() {
		return productInsTypeId;
	}

	public void setProductInsTypeId(String productInsTypeId) {
		this.productInsTypeId = productInsTypeId;
	}

	public ProductInsType getProductInsType() {
		return productInsType;
	}

	public void setProductInsType(ProductInsType productInsType) {
		this.productInsType = productInsType;
	}

	// 获取所有商品属性类型
	public List<AttributeInsType> getAllAttributeInsType() {
		List<AttributeInsType> allAttributeInsType = new ArrayList<AttributeInsType>();
		for (AttributeInsType attributeInsType : AttributeInsType.values()) {
			allAttributeInsType.add(attributeInsType);
		}
		return allAttributeInsType;
	}

}