package com.sinosoft.workflow;

import java.util.Date;

import com.sinosoft.framework.User;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.PlatformCache;
import com.sinosoft.schema.ZWStepSchema;
import com.sinosoft.workflow.Workflow.Node;

/**
 * 工作流执行时的上下文
 * 
 */
public class Context {
	private WorkflowInstance instance;

	private ZWStepSchema step;

	private Mapx variables;

	private Workflow flow;

	private Transaction tran;

	private boolean variableFlag = false;// 变量信息是否被改动

	public Context(Transaction tran, WorkflowStep step) throws WorkflowException {
		this(tran, (ZWStepSchema) step);
	}

	public Context(Transaction tran, ZWStepSchema step) throws WorkflowException {
		this.tran = tran;
		this.flow = WorkflowUtil.findWorkflow(step.getWorkflowID());
		this.instance = WorkflowUtil.findInstance(step.getInstanceID());
		this.step = step;
		this.variables = WorkflowUtil.getDataVariables(instance.getDataID(), step.getDataVersionID());
	}

	public WorkflowInstance getInstance() {
		return instance;
	}

	public ZWStepSchema getStep() {
		return step;
	}

	public Mapx getVariables() {
		return variables;
	}

	public Workflow getWorkflow() {
		return flow;
	}

	public Transaction getTransaction() {
		return tran;
	}

	public Context(Transaction tran, WorkflowInstance instance, WorkflowStep step) {
		this(tran, instance, (ZWStepSchema) step);
	}

	public Context(Transaction tran, WorkflowInstance instance, ZWStepSchema step) {
		this.tran = tran;
		this.flow = WorkflowUtil.findWorkflow(step.getWorkflowID());
		this.instance = instance;
		this.step = step;
		if (step.getID() != 1) {
			this.variables = WorkflowUtil.getDataVariables(instance.getDataID(), step.getDataVersionID());
		}
	}

	/**
	 * 本方法仅在WorkflowUtil.findInitAction()中使用
	 */
	protected Context(long workflowID) {
		this.flow = WorkflowUtil.findWorkflow(workflowID);
	}

	/**
	 * 返回执行当前节点、条件的用户名
	 */
	public String getOwner() {
		return User.getUserName();
	}

	/**
	 * 返回执行当前节点、条件的用户所在的机构
	 */
	public String getOwnerOrgan() {
		return User.getBranchInnerCode();
	}

	/**
	 * 设置正在创建的步骤的所属机构（仅在节点的PreAction/PreScript中有效）
	 */
	public void setStepOrgan(String organNames) {
		step.setAllowOrgan(organNames);
	}

	/**
	 * 设置正在创建的步骤的所属角色（仅在节点的PreAction/PreScript中有效）
	 */
	public void setStepRole(String roleNames) {
		step.setAllowRole(roleNames);
	}

	/**
	 * 设置正在创建的步骤的所属用户（仅在节点的PreAction/PreScript中有效）
	 */
	public void setStepUser(String userNames) {
		step.setAllowUser(userNames);
	}

	/**
	 * 获取变量值
	 */
	public Object getValue(String fieldName) {
		return variables.get(fieldName);
	}

	/**
	 * 设置变量的值
	 */
	public void setValue(String fieldName, String value) {
		variableFlag = true;
		variables.put(fieldName, value);
	}

	/**
	 * 设置变量的值
	 */
	public void setValue(String fieldName, long value) {
		variableFlag = true;
		variables.put(fieldName, value);
	}

	/**
	 * 设置变量的值
	 */
	public void setValue(String fieldName, int value) {
		variableFlag = true;
		variables.put(fieldName, value);
	}

	/**
	 * 保存对数据和步骤所作的修改
	 */
	public void save() {
		tran.add(step, Transaction.UPDATE);
		if (variableFlag) {
			WorkflowUtil.saveDataVariables(this);
		}
	}

	/**
	 * 判断当前用户是否属于某个角色
	 */
	public boolean isOwnRole(String roleName) {
		String roles = "," + PlatformCache.getUserRole(User.getUserName()) + ",";
		roleName = "," + roleName + ",";
		return roles.indexOf(roleName) >= 0;
	}

	/**
	 * 返回当前流程实例的名字
	 */
	public String getFlowName() {
		return instance.getName();
	}

	/**
	 * 返回当前流程的名字
	 */
	public String getWorkflowName() {
		return WorkflowUtil.findWorkflow(instance.getWorkflowID()).getName();
	}

	/**
	 * 返回当前步骤的名字
	 */
	public String getStepName() {
		return WorkflowUtil.getStepName(flow.getID(), step.getNodeID());
	}

	public void gotoStep(String stepName) throws Exception {
		Node[] nodes = flow.getNodes();
		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];
			if (!node.getType().equals(Workflow.ACTIONNODE) && node.getName().equalsIgnoreCase(stepName)) {
				WorkflowStep newStep = new WorkflowStep();
				newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
				newStep.setWorkflowID(flow.getID());

				// 从配置中复制权限信息，以便于脚本中修改
				newStep.setAllowOrgan(node.getData().getString("Organ"));
				newStep.setAllowRole(node.getData().getString("Role"));
				newStep.setAllowUser(node.getData().getString("User"));

				newStep.setNodeID(node.getID());
				newStep.setActionID(WorkflowAction.SCRIPT_ACTIONID);
				newStep.setInstanceID(instance.getID());

				newStep.setPreviousStepID(step.getID());
				newStep.setStartTime(new Date());
				newStep.setOperators(null);
				newStep.setState(WorkflowStep.UNREAD);
				newStep.setAddTime(new Date());
				newStep.setAddUser(User.getUserName());

				if (node.getType().equals(Workflow.STARTNODE)) {// 起始节点必须
					newStep.setAllowUser(instance.getAddUser());
					newStep.setState(WorkflowStep.UNDERWAY);
					newStep.setOwner(instance.getAddUser());
				}

				Context context = new Context(tran, instance, newStep);// 生成新的步骤后需要重置上下文
				// onStepCreate()先于前置事件执行
				WorkflowUtil.findAdapter().onStepCreate(context);
				WorkflowAction.executeMethod(this, flow.findNode(newStep.getNodeID()), "Pre");// 下一节点的前置方法
				context.save();

				// 检查工作流是否结束
				if (Workflow.ENDNODE.equalsIgnoreCase(node.getType())) {
					// 结束当前步骤
					newStep.setState(WorkflowStep.FINISHED);
					newStep.setFinishTime(new Date());
					newStep.setOwner("SYSTEM");

					// 结束整个工作流
					instance.setState(WorkflowInstance.COMPLETED);
					tran.add(context.getInstance(), Transaction.UPDATE);
				}
				tran.add(newStep, Transaction.INSERT);
			}
		}
	}
}
