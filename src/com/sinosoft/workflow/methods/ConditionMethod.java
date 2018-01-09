package com.sinosoft.workflow.methods;

import com.sinosoft.workflow.Context;

/**
 * 条件动作必须继承本类
 * 
 */
public abstract class ConditionMethod {
	/**
	 * 检查条件是否满足
	 */
	public abstract boolean validate(Context context) throws Exception;
}
