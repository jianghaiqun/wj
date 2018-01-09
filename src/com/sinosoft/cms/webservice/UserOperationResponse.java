package com.sinosoft.cms.webservice;

public class UserOperationResponse 
{
	//用户代码
	private String userCode;
	//成功失败flag，1失败，0成功
	private int errorFlag;
	//错误代码，CMS自定义
	private int errorCode;
	
	//错误消息
	//create 1.用户已存在 2 机构不存在（暂时忽略）
	//update 1.用户不存在 
	//suspend 1.用户不存在 2.已suspend 
	//restore 1.用户不存在 2.未suspend
	private String errorMessage;

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public int getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
