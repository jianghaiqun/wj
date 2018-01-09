package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.action.admin.BaseAdminAction;
import cn.com.sinosoft.entity.Code;
import cn.com.sinosoft.service.CodeService;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParentPackage("admin")
public class CodeAction extends BaseAdminAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4720568404871881028L;

	private Code code;

	@Resource
	private CodeService codeService;

	// 添加
	public String add() {
		return INPUT;
	}
	// 列表
	public String list() {
//		System.out.println(logInfo);
		pager = codeService.findByPager(pager);
		return LIST;
	}

	// 编辑
	public String edit() {
		code = codeService.load(id);
		return INPUT;
	}
	// 删除
	public String delete() throws Exception {
		codeService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}
	public String save() {
		if(code.getParamENDescription().equals("forWhomInsurance")){
			code.setParamZHDescription("为谁投保");
		}
		if(code.getParamENDescription().equals("insuranceType")){
			code.setParamZHDescription("险种类型");
		}
		if(code.getParamENDescription().equals("protectionPeriod")){
			code.setParamZHDescription("保障期限");
		}
		codeService.save(code);
		redirectionUrl = "code!list.action";
		return SUCCESS;
	}
	public String update() throws Exception {
		Code persistent = codeService.load(id);
		BeanUtils.copyProperties(code, persistent, new String[] {"id", "createDate", "modifyDate"});
		if(code.getParamENDescription().equals("forWhomInsurance")){
			persistent.setParamZHDescription("为谁投保");
		}
		if(code.getParamENDescription().equals("insuranceType")){
			persistent.setParamZHDescription("险种类型");
		}
		if(code.getParamENDescription().equals("protectionPeriod")){
			persistent.setParamZHDescription("保障期限");
		}
		codeService.update(persistent);
		flushCache();
		redirectionUrl = "code!list.action";
		return SUCCESS;
	}
	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}
	// 获取所有自定义注册项属性类型
	public List<String> getAllDictionaryItemTypes() {
		List<String> allDictionaryItemTypes = new ArrayList<String>();
		allDictionaryItemTypes.add("为谁投保");
		allDictionaryItemTypes.add("险种类型");
		allDictionaryItemTypes.add("保障期限");
		
		return allDictionaryItemTypes;
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