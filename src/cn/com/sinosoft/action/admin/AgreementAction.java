package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.entity.Agreement;
import cn.com.sinosoft.service.AgreementService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * 后台Action类 - 会员注册协议
 * ============================================================================
 * KEY:SINOSOFT692803CD521A3455EC230DFDE29BBE74
 * ============================================================================
 */

@ParentPackage("admin")
public class AgreementAction extends BaseAdminAction {

	private static final long serialVersionUID = 7884213806344111048L;

	private Agreement agreement;
  
	@Resource
	private AgreementService agreementService;

	// 编辑
	public String edit() {
		agreement = agreementService.getAgreement();
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "agreement.content", message = "会员注册协议内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Agreement persistent = agreementService.getAgreement();
		BeanUtils.copyProperties(agreement, persistent, new String[] {"id", "createDate", "modifyDate"});
		agreementService.update(persistent);
		redirectionUrl = "agreement!edit.action";
		return SUCCESS;
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	
}