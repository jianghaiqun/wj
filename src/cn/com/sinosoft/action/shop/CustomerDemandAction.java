package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Code;
import cn.com.sinosoft.entity.CustomerDemand;
import cn.com.sinosoft.service.CodeService;
import cn.com.sinosoft.service.CustomerDemandService;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;
import java.util.Date;
import java.util.List;

/**
 * 后台Action类 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD06041D9E5D00829FE51D5C8A9D10C0B
 * ============================================================================
 */
@ParentPackage("shop")
public class CustomerDemandAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5753601053434912943L;

	private CustomerDemand customerDemand;
	private Code code;
	private List<String> allInsuranceName;
	private List<String> allInsuranceType;
	private List<String> allGuaranteePeriod;

	public List<String> getAllInsuranceName() {
		return allInsuranceName;
	}

	public void setAllInsuranceName(List<String> allInsuranceName) {
		this.allInsuranceName = allInsuranceName;
	}

	public List<String> getAllInsuranceType() {
		return allInsuranceType;
	}

	public void setAllInsuranceType(List<String> allInsuranceType) {
		this.allInsuranceType = allInsuranceType;
	}

	public List<String> getAllGuaranteePeriod() {
		return allGuaranteePeriod;
	}

	public void setAllGuaranteePeriod(List<String> allGuaranteePeriod) {
		this.allGuaranteePeriod = allGuaranteePeriod;
	}

	@Resource
	private CustomerDemandService customerDemandService;
	
	@Resource
	private CodeService codeService;

	public CustomerDemand getCustomerDemand() {
		return customerDemand;
	}

	public void setCustomerDemand(CustomerDemand customerDemand) {
		this.customerDemand = customerDemand;
	}

	public CustomerDemandService getCustomerDemandService() {
		return customerDemandService;
	}

	public void setCustomerDemandService(CustomerDemandService customerDemandService) {
		this.customerDemandService = customerDemandService;
	}
	// 添加
	public String add() {
		return INPUT;
	}
	// 列表
	public String list() {
		pager = customerDemandService.findByPager(pager);
		return LIST;
	}

	// 编辑
	public String edit() {
		customerDemand = customerDemandService.load(id);
		return INPUT;
	}
	// 删除
	public String delete() throws Exception {
		customerDemandService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}
	public String save() {
//		System.out.println("=== enter ==");
		customerDemandService.save(customerDemand);
//		redirectionUrl = "../shop/customer_demand!list.action";
		return SUCCESS;
	}
	public String update() throws Exception {
		CustomerDemand persistent = customerDemandService.load(id);
		BeanUtils.copyProperties(customerDemand, persistent, new String[] {"id", "createDate", "modifyDate"});
		customerDemandService.update(persistent);
		flushCache();
		redirectionUrl = "../shop/customer_demand!list.action";
		return SUCCESS;
	}
	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}
	/*
	@InputConfig(resultName = "error")
	public List<String> getAllInsuranceNames() {
		List<Code> list = new ArrayList<Code>();
		list = codeService.getList("paramENDescription", "forWhomInsurance");
		System.out.println(list.size()+"@@@@@@@@@@@@@@");
		for(Code code :list){
			this.allInsuranceNames.add(code.getParamValue());
		}
		return this.allInsuranceNames;
	}
	
	public void setAllInsuranceNames(List<String> allInsuranceNames) {
		this.allInsuranceNames = allInsuranceNames;
	}

	public List<String> getAllInsuranceType() {
		List<Code> list = new ArrayList<Code>();
		list = codeService.getList("paramENDescription", "insuranceType");
		List<String> allInsuranceType = new ArrayList<String>();
		for(Code code :list){
			allInsuranceType.add(code.getParamValue());
		}
		return allInsuranceType;
	}
	public List<String> getAllGuaranteePeriod() {
		List<Code> list = new ArrayList<Code>();
		list = codeService.getList("paramENDescription", "protectionPeriod");
		List<String> allGuaranteePeriod = new ArrayList<String>();
		for(Code code :list){
			allGuaranteePeriod.add(code.getParamValue());
		}
		return allGuaranteePeriod;
	}
	*/
	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
}