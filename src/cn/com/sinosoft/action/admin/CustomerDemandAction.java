package cn.com.sinosoft.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.entity.Code;
import cn.com.sinosoft.entity.CustomerDemand;
import cn.com.sinosoft.service.CodeService;
import cn.com.sinosoft.service.CustomerDemandService;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;

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

@ParentPackage("admin")
public class CustomerDemandAction extends BaseAdminAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4720568404871881028L;

	private CustomerDemand customerDemand;
	private Code code;

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
//		System.out.println(logInfo);
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
		customerDemandService.save(customerDemand);
		redirectionUrl = "customer_demand!list.action";
		return SUCCESS;
	}
	public String update() throws Exception {
		CustomerDemand persistent = customerDemandService.load(id);
		BeanUtils.copyProperties(customerDemand, persistent, new String[] {"id", "createDate", "modifyDate"});
		customerDemandService.update(persistent);
		flushCache();
		redirectionUrl = "customer_demand!list.action";
		return SUCCESS;
	}
	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}
	public List<String> getAllInsuranceName() {
		List<Code> list = new ArrayList<Code>();
		list = codeService.getList("paramENDescription", "forWhomInsurance");
		List<String> allInsuranceName = new ArrayList<String>();
		for(Code code :list){
			allInsuranceName.add(code.getParamValue());
		}
		return allInsuranceName;
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