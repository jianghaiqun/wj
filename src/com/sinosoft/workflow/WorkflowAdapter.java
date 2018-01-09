package com.sinosoft.workflow;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工作流适配器
 * 
 */
public abstract class WorkflowAdapter {
	protected static final Logger logger = LoggerFactory.getLogger(WorkflowAdapter.class);

	/**
	 * 临时保存时触发此方法
	 */
	public void onTemporarySave(Context context) {
		// 一般情况下不需要特别处理
	}

	/**
	 * 步骤回退时触发此方法
	 */
	public void onStepCancel(Context context) {
		// 一般情况下不需要特别处理
	}

	/**
	 * 步骤被创建时触发此方法
	 */
	public void onStepCreate(Context context) {
		// 一般情况下不需要特别处理
	}

	/**
	 * 动作执行时触发此方法
	 */
	public void onActionExecute(Context context,WorkflowAction action) {
		// 一般情况下不需要特别处理
	}

	/**
	 * 通知下一步处理人,仅当工作设置为“通知下一步处理人”时才会调用本方法
	 */
	public void notifyNextStep(Context context, String[] users) {
		// 一般情况下不需要特别处理，仅当工作设置为“通知下一步处理人”时才会调用本方法
		String className = Config.getValue("App.WorkflowAdapter");
		logger.warn("ID为{}的流程配置了'通知下一步处理人'选项，" +
				"但{}未实现notifyNextStep()方法", context.getWorkflow().getID(), className);
	}

	/**
	 * 工作流定义被删除时调用此方法
	 */
	public abstract void onWorkflowDelete(Transaction tran, long workflowID);

	/**
	 * 获取与工作流相关联的数据容器
	 */
	public abstract Mapx getVariables(String dataID, String dataVersionID);

	/**
	 * 保存与工作流相关联的数据
	 */
	public abstract boolean saveVariables(Context context);
}
