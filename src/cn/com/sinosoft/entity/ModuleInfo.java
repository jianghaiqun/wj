package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/**
 * 
 * 实体类：投保录入页---模块信息
 *
 */



@Entity
public class ModuleInfo extends BaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1444860688195298097L;
	private String ModuleCode; //模块编码
	private String ModuleType; //模块类型
	private String ModuleURL; //模块路径
	private String TemplateCode; //模板编码
	private int ModuleOrder;//排序
	public int getModuleOrder() {
		return ModuleOrder;
	}
	public void setModuleOrder(int moduleOrder) {
		ModuleOrder = moduleOrder;
	}
	private String Remark1; //预留字段
	private String Remark2; //预留字段
	public String getModuleCode() {
		return ModuleCode;
	}
	public void setModuleCode(String moduleCode) {
		ModuleCode = moduleCode;
	}
	public String getModuleType() {
		return ModuleType;
	}
	public void setModuleType(String moduleType) {
		ModuleType = moduleType;
	}
	public String getModuleURL() {
		return ModuleURL;
	}
	public void setModuleURL(String moduleURL) {
		ModuleURL = moduleURL;
	}
	public String getTemplateCode() {
		return TemplateCode;
	}
	public void setTemplateCode(String templateCode) {
		TemplateCode = templateCode;
	}
	public String getRemark1() {
		return Remark1;
	}
	public void setRemark1(String remark1) {
		Remark1 = remark1;
	}
	public String getRemark2() {
		return Remark2;
	}
	public void setRemark2(String remark2) {
		Remark2 = remark2;
	}

	
	
	
}