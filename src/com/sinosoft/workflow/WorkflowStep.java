package com.sinosoft.workflow;

import com.sinosoft.schema.ZWStepSchema;

/**
 * 流程步骤
 * 
 */
public class WorkflowStep extends ZWStepSchema {
	private static final long serialVersionUID = 1L;

	/**
	 * 步骤未被申请
	 */
	public static final String UNREAD = "Unread";

	/**
	 * 步骤处理中
	 */
	public static final String UNDERWAY = "Underway";

	/**
	 * 步骤己完成
	 */
	public static final String FINISHED = "Finish";

	/**
	 * 步骤己被撤销
	 */
	public static final String CANCEL = "Cancel";

	/**
	 * 步骤己创建，但还需等待其他分支的结果
	 */
	public static final String WAIT = "Wait";
}
