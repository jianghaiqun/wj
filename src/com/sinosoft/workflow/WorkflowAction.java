package com.sinosoft.workflow;

import java.util.Date;

import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZWStepSchema;
import com.sinosoft.schema.ZWStepSet;
import com.sinosoft.workflow.Workflow.Node;
import com.sinosoft.workflow.methods.ConditionMethod;
import com.sinosoft.workflow.methods.ConditionScript;
import com.sinosoft.workflow.methods.MethodScript;
import com.sinosoft.workflow.methods.NodeMethod;

/**
 * 对应于一个动作节点
 * 
 */
public class WorkflowAction {
	/**
	 * 重新开始动作，工作流结束后重新从开始节点执行时会执行此动作
	 */
	public static final int RESTART_ACTIONID = -5;

	/**
	 * 开始动作，工作流创建时使用此动作.
	 */
	public static final int START_ACTIONID = -2;

	/**
	 * 临时处理动作，执行此动作只会保存数据，不影响工作流。<br>
	 * 此动作会调用WorkflowAdapter.onTemporarySave()方法
	 */
	public static final int TEMPORARYSAVE_ACTIONID = -1;

	/**
	 * 申请处理动作，只能针对未读步骤。
	 */
	public static final int APPLY_ACTIONID = -3;

	/**
	 * 脚本动作，通过Context.gotoStep()时跳转的步骤会将ActionID置为SCRIPT_ACTIONID
	 */
	public static final int SCRIPT_ACTIONID = -4;

	/**
	 * 强制结束动作，通过Workflow.forceEnd时强制结束流程时会使用此ID
	 */
	public static final int FORCEEND_ACTIONID = -6;

	private int id;

	private String name;

	private Mapx data;

	private Node node;

	private Context context;

	protected WorkflowAction(int id, String name, Mapx data) {
		this.id = id;
		this.name = name;
		this.data = data;
	}

	public WorkflowAction(Node node) {
		if (!node.getType().equals(Workflow.ACTIONNODE)) {
			throw new RuntimeException("WorkflowAction必须是动作节点!");
		}
		this.id = node.getID();
		this.name = node.getName();
		this.data = node.getData();
		this.node = node;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Mapx getData() {
		return data;
	}

	/**
	 * 执行动作节点，转移到下一步骤
	 */
	public void execute(Transaction tran, long instanceID, String memo) throws Exception {
		execute(tran, WorkflowUtil.findInstance(instanceID), memo);
	}

	public void execute(Transaction tran, long instanceID, String selectedUser, String memo) throws Exception {
		execute(tran, WorkflowUtil.findInstance(instanceID), selectedUser, memo);
	}

	public void execute(Context context, String selectedUser, String memo) throws Exception {
		execute(context.getTransaction(), context.getInstance(), context.getStep(), selectedUser, memo);
	}

	/**
	 *执行动作节点，转移到下一步骤
	 */
	public void execute(Transaction tran, WorkflowInstance instance, String memo) throws Exception {
		execute(tran, instance, null, memo);
	}

	public void execute(Transaction tran, WorkflowInstance instance, String selectedUser, String memo) throws Exception {
		if (this.id == WorkflowAction.RESTART_ACTIONID) {// 重新开始
			restartInstance(tran, instance, memo);
			return;
		}
		ZWStepSchema step = WorkflowUtil.findCurrentStep(instance.getID());
		execute(tran, instance, step, selectedUser, memo);
	}

	public void execute(Transaction tran, WorkflowInstance instance, ZWStepSchema step, String selectedUser, String memo)
			throws Exception {
		if (this.id == WorkflowAction.RESTART_ACTIONID) {// 重新开始
			restartInstance(tran, instance, memo);
			return;
		}
		context = new Context(tran, instance, step);
		step.setMemo(memo);
		if (this.id == WorkflowAction.TEMPORARYSAVE_ACTIONID) {// 临时保存
			WorkflowUtil.findAdapter().onTemporarySave(context);
			context.save();
		} else {// 一般动作
			step.setState(WorkflowStep.FINISHED);
			step.setFinishTime(new Date());
			step.setOwner(User.getUserName());// 需要置一下，如果没有申请直接处理，则需要置Owner
			Workflow wf = WorkflowUtil.findWorkflow(context.getStep().getWorkflowID());
			executeMethod(context, wf.findNode(step.getNodeID()), "Post");// 上一步骤的后置方法
			if (!context.getInstance().getState().equals(WorkflowInstance.COMPLETED)) {// 有可能上一步骤后置方法中已经将流程终结
				executeMethod(context, wf.findNode(this.id), "Post");// 动作方法
			}
			WorkflowUtil.findAdapter().onActionExecute(context, this);
			context.save();
			if (!context.getInstance().getState().equals(WorkflowInstance.COMPLETED)) {// 有可能上一步骤后置方法中已经将流程终结
				tryCreateNextStep(selectedUser);
			}
		}
	}

	/**
	 * 重新开始流程
	 */
	public void restartInstance(Transaction tran, WorkflowInstance instance, String memo) {
		instance.setState(WorkflowInstance.ACTIVATED);
		tran.add(instance, Transaction.UPDATE);
		Workflow flow = WorkflowUtil.findWorkflow(instance.getWorkflowID());

		WorkflowStep newStep = new WorkflowStep();
		newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
		newStep.setWorkflowID(instance.getWorkflowID());
		newStep.setNodeID(flow.getStartNode().getID());
		newStep.setActionID(WorkflowAction.RESTART_ACTIONID);
		newStep.setPreviousStepID(flow.getEndNode().getID());// 重新开始的上一步是结束
		newStep.setInstanceID(instance.getID());
		newStep.setDataVersionID("0");
		newStep.setOwner(User.getUserName());
		newStep.setStartTime(new Date());
		newStep.setMemo("重新开始流程");
		newStep.setOperators(User.getUserName());
		newStep.setState(WorkflowStep.UNDERWAY);
		newStep.setAddTime(new Date());
		newStep.setAddUser(User.getUserName());
		tran.add(newStep, Transaction.INSERT);

		Context context = new Context(tran, instance, newStep);
		WorkflowUtil.findAdapter().onStepCreate(context);
		context.save();
	}

	/**
	 * 尝试转移到下一步骤
	 */
	private void tryCreateNextStep(String selectedUser) throws Exception {// 不能返回值，可能会有多个分支
		if (node == null) {
			throw new WorkflowException("不正确的WorkflowAction对象，node未置值");
		}
		WorkflowTransition[] wts = node.getTransitions();
		for (int i = 0; i < wts.length; i++) {
			WorkflowTransition wt = wts[i];
			// 生成下一步骤
			if (checkConditions(wt)) {// 此处必须先检查，因为如果有其他分支的话，其他分支不会来检查此处的条件逻辑
				if (!checkOtherLine(wt)) {// 检查其他分支是否有未处理完的节点,如果有，则将当前步骤状态为等待
					context.getStep().setState(WorkflowStep.WAIT);// 等待其他分支
					context.save();
					return;// 先不创建
				}

				// 如果是会签的最后一个处理人，则需要需要将NodeID相同的步骤记录状态修改为Finish
				if ("1".equals(node.getWorkflow().findNode(context.getStep().getNodeID()).getData().get("SignJointly"))) {
					QueryBuilder qb = new QueryBuilder("update ZWStep set State=? where InstanceID=? and State=?");
					qb.add(WorkflowStep.FINISHED);
					qb.add(context.getStep().getInstanceID());
					qb.add(WorkflowStep.WAIT);
				}

				ZWStepSchema newStep = new WorkflowStep();
				newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
				newStep.setWorkflowID(context.getInstance().getWorkflowID());

				// 从配置中复制权限信息，以便于脚本中修改
				newStep.setAllowOrgan(wt.getTargetNode().getData().getString("Organ"));
				newStep.setAllowRole(wt.getTargetNode().getData().getString("Role"));
				newStep.setAllowUser(wt.getTargetNode().getData().getString("User"));

				newStep.setNodeID(wt.getTargetNode().getID());
				newStep.setActionID(this.getID());// 置为当前的动作ID
				newStep.setInstanceID(context.getInstance().getID());

				newStep.setPreviousStepID(context.getStep().getID());
				newStep.setStartTime(new Date());
				newStep.setOperators(null);
				newStep.setState(WorkflowStep.UNREAD);
				newStep.setAddTime(new Date());
				newStep.setAddUser(User.getUserName());

				if ("1".equals(wt.getTargetNode().getData().get("SignJointly"))) {
					// 为每个人都创建一个步骤
					String[] users = WorkflowUtil.getPrivUsers(newStep);
					if (users == null || users.length == 0) {
						throw new WorkflowException("没有可以执行下一步骤的用户，请与系统管理员联系！InstanceID=" + newStep.getInstanceID());
					}
					for (int j = 0; j < users.length; j++) {
						if (j == 0) {// 重置前一个的值
							newStep.setAllowUser(users[j]);
							newStep.setAllowOrgan(null);
							newStep.setAllowRole(null);
							continue;
						}
						ZWStepSchema step = (ZWStepSchema) newStep.clone();
						step.setAllowUser(users[j]);
						step.setAllowOrgan(null);
						step.setAllowRole(null);
						step.setID(NoUtil.getMaxID("WorkflowStepID"));
						context.getTransaction().add(step, Transaction.INSERT);
					}
				} else {
					if (StringUtil.isNotEmpty(selectedUser)) {
						if (!StringUtil.checkID(selectedUser)) {
							throw new RuntimeException("可能的SQL注入：" + selectedUser);
						}
						newStep.setAllowUser(selectedUser);
						newStep.setAllowOrgan(null);
						newStep.setAllowRole(null);
					}
				}

				if (wt.getTargetNode().getType().equals(Workflow.STARTNODE)) {// 起始节点必须
					newStep.setAllowUser(context.getInstance().getAddUser());
					newStep.setState(WorkflowStep.UNDERWAY);
					newStep.setOwner(context.getInstance().getAddUser());
				}

				context = new Context(context.getTransaction(), context.getInstance(), newStep);// 生成新的步骤后需要重置上下文

				// 检查动作执行后AllowOrgan,AllowRole,AllowUser的结果，如果没有人可以执行下一步骤，则提示失败
				if (!Workflow.ENDNODE.equalsIgnoreCase(wt.getTargetNode().getType())) {
					if ("1".equals(context.getWorkflow().getData().get("NotifyNextStep"))) {
						String[] users = WorkflowUtil.getPrivUsers(newStep);
						if (users == null || users.length == 0) {
							throw new WorkflowException("没有可以执行下一步骤的用户，请与系统管理员联系！InstanceID=" + newStep.getInstanceID());
						}
						WorkflowUtil.findAdapter().notifyNextStep(context, users);// 通知下一步骤处理人
					} else {
						if (!WorkflowUtil.hasPrivUser(newStep)) {// 此方法较getPrivUser()性能较优
							throw new WorkflowException("没有可以执行下一步骤的用户，请与系统管理员联系！InstanceID=" + newStep.getInstanceID());
						}
					}
				}

				// onStepCreate()先于前置事件执行
				WorkflowUtil.findAdapter().onStepCreate(context);
				executeMethod(context,
						WorkflowUtil.findWorkflow(newStep.getWorkflowID()).findNode(newStep.getNodeID()), "Pre");// 下一节点的前置方法

				if (!context.getInstance().getState().equals(WorkflowInstance.COMPLETED)) {
					// 检查工作流是否结束
					if (Workflow.ENDNODE.equalsIgnoreCase(wt.getTargetNode().getType())) {
						// 结束当前步骤
						newStep.setState(WorkflowStep.FINISHED);
						newStep.setFinishTime(new Date());
						newStep.setOwner("SYSTEM");

						// 结束整个工作流
						context.getInstance().setState(WorkflowInstance.COMPLETED);
						context.getTransaction().add(context.getInstance(), Transaction.UPDATE);
					}
					context.getTransaction().add(newStep, Transaction.INSERT);
				}
				context.save();
			}
		}
	}

	/**
	 * 检查转移条件是否满足
	 */
	public boolean checkConditions(WorkflowTransition wt) throws Exception {
		Object[] keyArray = wt.getData().keyArray();
		boolean flag = true;
		for (int i = 0; i < keyArray.length; i++) {
			String key = (String) keyArray[i];
			String value = wt.getData().getString(key);
			if ("Script".equalsIgnoreCase(key) && StringUtil.isNotEmpty(value)) {
				ConditionScript cs = new ConditionScript();
				cs.setScript(value);
				flag = cs.validate(context);
			} else if ("Method".equalsIgnoreCase(key) && StringUtil.isNotEmpty(value)) {
				Object o = Class.forName(value).newInstance();
				ConditionMethod cm = null;
				if (o instanceof ConditionMethod) {
					cm = (ConditionMethod) o;
				} else {
					throw new WorkflowException(value + "没有实现ConditionMethod抽象类");
				}
				flag = cm.validate(context);
			}
		}
		return flag;
	}

	/**
	 * 检查其他分支是否已经结束<br>
	 * 如果当前实例未完成的步骤中有可以走到当前步骤的路径，则说明当前步骤为能终结
	 */
	private boolean checkOtherLine(WorkflowTransition wt) {
		QueryBuilder qb = new QueryBuilder("where InstanceID=? and ID<>? and (State=? or State=?)", context
				.getInstance().getID(), context.getStep().getID());// 查询本步骤以外的所有未完成的步骤，包括会签的步骤
		qb.add(WorkflowStep.UNDERWAY);
		qb.add(WorkflowStep.UNREAD);
		ZWStepSet set = new ZWStepSchema().query(qb);
		for (int i = 0; i < set.size(); i++) {
			int nodeID = set.get(i).getNodeID();
			if (isLinked(nodeID, wt.getTargetNode().getID(), 1)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查两个节点之间是否可以连通
	 */
	private boolean isLinked(int src, int target, int level) {// 判断是否可以从src走到target
		if (level > 50) {// 避免无限循环
			return false;
		}
		Workflow wf = WorkflowUtil.findWorkflow(context.getInstance().getWorkflowID());
		WorkflowTransition[] wts = wf.findNode(src).getTransitions();
		for (int j = 0; j < wts.length; j++) {
			if (wts[j].getFromNode().getID() == wts[j].getTargetNode().getID()) {
				continue;
			}
			if (wts[j].getTargetNode().getID() == target) {
				return true;
			} else {
				if (isLinked(wts[j].getTargetNode().getID(), target, level + 1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 执行前置或后置方法，type=Pre或Post
	 */
	public static void executeMethod(Context context, Node node, String type) throws Exception {
		String method = node.getData().getString(type + "Action");
		String script = node.getData().getString(type + "Script");
		if (StringUtil.isNotEmpty(method)) {
			Object o = Class.forName(method).newInstance();
			NodeMethod nm = null;
			if (o instanceof NodeMethod) {
				nm = (NodeMethod) o;
			} else {
				throw new WorkflowException(method + "没有实现NodeMethod抽象类");
			}
			nm.execute(context);
		}
		if (StringUtil.isNotEmpty(script)) {
			MethodScript sa = new MethodScript(script);
			sa.execute(context);
		}
	}

	/**
	 * 获得临时保存对应的Action
	 */
	public static WorkflowAction getTemporarySaveAction() {
		return new WorkflowAction(TEMPORARYSAVE_ACTIONID, "暂存", new Mapx());
	}

	/**
	 * 获得重新开始对应的Action
	 */
	public static WorkflowAction getRestartAction() {
		return new WorkflowAction(RESTART_ACTIONID, "重新处理", new Mapx());
	}

	/**
	 * 获得申请处理对应的Action
	 */
	public static WorkflowAction getApplyAction(int nodeID) {
		Mapx map = new Mapx();
		map.put("NodeID", "" + nodeID);
		return new WorkflowAction(APPLY_ACTIONID, "申请处理", map);
	}
}
