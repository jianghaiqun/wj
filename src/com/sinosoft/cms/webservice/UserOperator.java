package com.sinosoft.cms.webservice;

public interface UserOperator
{
	//新增操作
	public static final String CREATE = "create";
	//挂起用户
	public static final String SUSPEND = "suspend";
	//恢复挂起用户
	public static final String RESTORE = "restore";
	//变更用户
	public static final String UPDATE = "update";
	
	UserOperationResponse doUserOperation(UserOperationRequest request);
}
