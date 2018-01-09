package com.sinosoft.workflow.methods;

import com.sinosoft.workflow.Context;

/**
 * 在流程实例开始之前初始化表单中的值
 * 
 */
public abstract class NodeMethod {
	public abstract void execute(Context context) throws Exception;
}
