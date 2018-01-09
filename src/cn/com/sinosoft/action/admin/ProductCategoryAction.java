package cn.com.sinosoft.action.admin;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;


import org.apache.catalina.connector.Request;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.LogConfig;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductCategory;
import cn.com.sinosoft.service.ProductCategoryService;
import cn.com.sinosoft.util.StrutsUtil;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
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
public class ProductCategoryAction extends BaseAdminAction {

	private static final long serialVersionUID = 3066159260207583127L;
	
	private String parentId;
	private String channelId;
	private ProductCategory productCategory;
	private List<ProductCategory> productCategoryTreeList;
	private List<Channel> productCategoryChannelList;
	private List<Channel> channelList;
	private Channel channel;
	@Resource
	private ProductCategoryService productCategoryService;
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		productCategory = productCategoryService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}

	// 删除
	public String delete() {
		ProductCategory productCategory = productCategoryService.load(id);
		Set<ProductCategory> childrenProductCategorySet = productCategory.getChildren();
		redirectionUrl = "product_category!list.action";
		if (childrenProductCategorySet != null && childrenProductCategorySet.size() > 0) {
			addActionError("此商品分类存在下级分类，删除失败!");
			return ERROR;
		}
		Set<Product> productSet = productCategory.getProductSet();
		if (productSet != null && productSet.size() > 0) {
			addActionError("此商品分类下存在商品，删除失败!");
			return ERROR;
		}
		productCategoryService.delete(id);
		return SUCCESS;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "productCategory.name", message = "分类名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "productCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "productCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(parentId)) {
			ProductCategory parent = productCategoryService.load(parentId);
			productCategory.setParent(parent);
		} else {
			productCategory.setParent(null);
		}
		productCategory.setChannelSet(new HashSet<Channel>(channelList));
		productCategoryService.save(productCategory);
		redirectionUrl = "product_category!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "productCategory.name", message = "分类名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "productCategory.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "productCategory.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		ProductCategory persistent = productCategoryService.load(id);
		productCategory.setChannelSet(new HashSet<Channel>(channelList));
		BeanUtils.copyProperties(productCategory, persistent, new String[]{"id", "createDate", "modifyDate", "path", "parent", "children", "productSet"});
		productCategoryService.update(persistent);
		redirectionUrl = "product_category!list.action";
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

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductCategory> getProductCategoryTreeList() {
		productCategoryTreeList = productCategoryService.getProductCategoryTreeList();
		
		return productCategoryTreeList;
	}

	public void setProductCategoryTreeList(List<ProductCategory> productCategoryTreeList) {
		this.productCategoryTreeList = productCategoryTreeList;
	}

	public List<Channel> getProductCategoryChannelList() {
		productCategoryChannelList = productCategoryService.getProductCategoryChannelList();
		return productCategoryChannelList;
	}

	public void setProductCategoryChannelList(
			List<Channel> productCategoryChannelList) {
		this.productCategoryChannelList = productCategoryChannelList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}