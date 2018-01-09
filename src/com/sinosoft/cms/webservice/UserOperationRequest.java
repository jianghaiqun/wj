package com.sinosoft.cms.webservice;

public class UserOperationRequest 
{
	//操作类型，参看UserOperator下的常数
	private String operationType;
	//用户名代码
	private String userCode;
	//用户名
	private String userName;
	//机构代码
	private String orgCode;
	//机构名
	private String orgName;
	
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgName() {
		return orgName;
	}
}
