package cn.com.sinosoft.action.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.entity.DeliveryCorp;
import cn.com.sinosoft.entity.DeliveryType;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.DeliveryType.DeliveryMethod;
import cn.com.sinosoft.entity.Product.WeightUnit;
import cn.com.sinosoft.service.DeliveryCorpService;
import cn.com.sinosoft.service.DeliveryTypeService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 配送方式
 * ============================================================================
 * KEY:SINOSOFT5AC22C1AA1942CA43D5DCBD481F91367
 * ============================================================================
 */

@ParentPackage("admin")
public class DeliveryTypeAction extends BaseAdminAction {

	private static final long serialVersionUID = -2431663334945495069L;

	private DeliveryType deliveryType;

	@Resource
	private DeliveryTypeService deliveryTypeService;
	@Resource
	private DeliveryCorpService deliveryCorpService;
	
	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = deliveryType.getName();
		if (deliveryTypeService.isUnique("name", oldValue, newValue)) {
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
		deliveryType = deliveryTypeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		pager = deliveryTypeService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		long totalCount = deliveryTypeService.getTotalCount();
		if (ids.length >= totalCount) {
			return ajaxJsonErrorMessage("删除失败!必须至少保留一个配送方式");
		}
		deliveryTypeService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "deliveryType.name", message = "配送方式名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "deliveryType.deliveryMethod", message = "配送类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "deliveryType.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
//		if (deliveryType.getFirstWeightPrice().compareTo(new BigDecimal("0")) < 0) {
//			addActionError("首重价格不允许小于0");
//			return ERROR;
//		}
//		if (deliveryType.getContinueWeightPrice().compareTo(new BigDecimal("0")) < 0) {
//			addActionError("续重价格不允许小于0");
//			return ERROR;
//		}
		deliveryType.setFirstWeightPrice(new BigDecimal("0"));
		deliveryType.setContinueWeightPrice(new BigDecimal("0"));
		deliveryType.setFirstWeight(1.00);
		deliveryType.setContinueWeight(0.00);
		Product product = new Product();
		deliveryType.setFirstWeightUnit((WeightUnit.g));
		deliveryType.setContinueWeightUnit((WeightUnit.g));
		
		if (StringUtils.isEmpty(deliveryType.getDefaultDeliveryCorp().getId())) {
			deliveryType.setDefaultDeliveryCorp(null);
		}
		deliveryTypeService.save(deliveryType);
		redirectionUrl = "delivery_type!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "deliveryType.name", message = "配送方式名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "deliveryType.deliveryMethod", message = "配送类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "deliveryType.orderList", message = "排序不允许为空!")},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "deliveryType.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
//		if (deliveryType.getFirstWeightPrice().compareTo(new BigDecimal("0")) < 0) {
//			addActionError("首重价格不允许小于0");
//			return ERROR;
//		}
//		if (deliveryType.getContinueWeightPrice().compareTo(new BigDecimal("0")) < 0) {
//			addActionError("续重价格不允许小于0");
//			return ERROR;
//		}
		deliveryType.setFirstWeightPrice(new BigDecimal("0"));
		deliveryType.setContinueWeightPrice(new BigDecimal("0"));
		deliveryType.setFirstWeight(1.00);
		deliveryType.setContinueWeight(0.00);
		Product product = new Product();
		deliveryType.setFirstWeightUnit((WeightUnit.g));
		deliveryType.setContinueWeightUnit((WeightUnit.g));
		
		if (StringUtils.isEmpty(deliveryType.getDefaultDeliveryCorp().getId())) {
			deliveryType.setDefaultDeliveryCorp(null);
		}
		DeliveryType persistent = deliveryTypeService.load(id);
		BeanUtils.copyProperties(deliveryType, persistent, new String[]{"id", "createDate", "modifyDate", "orderSet", "shippingSet", "reshipSet"});
		deliveryTypeService.update(persistent);
		redirectionUrl = "delivery_type!list.action";
		return SUCCESS;
	}

	// 获取所有物流公司
	public List<DeliveryCorp> getAllDeliveryCorp() {
		return deliveryCorpService.getAll();
	}

	// 获取所有配送类型
	public List<DeliveryMethod> getAllDeliveryMethod() {
		List<DeliveryMethod> allDeliveryMethod = new ArrayList<DeliveryMethod>();
		for (DeliveryMethod deliveryMethod : DeliveryMethod.values()) {
			allDeliveryMethod.add(deliveryMethod);
		}
		return allDeliveryMethod;
	}

	// 获取所有重量单位
	public List<WeightUnit> getAllWeightUnit() {
		List<WeightUnit> allWeightUnit = new ArrayList<WeightUnit>();
		for (WeightUnit weightUnit : WeightUnit.values()) {
			allWeightUnit.add(weightUnit);
		}
		return allWeightUnit;
	}
	
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

}