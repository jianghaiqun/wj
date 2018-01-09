package com.sinosoft.workflow;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserList;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZWInstanceSchema;
import com.sinosoft.schema.ZWInstanceSet;
import com.sinosoft.schema.ZWStepSchema;
import com.sinosoft.schema.ZWStepSet;
import com.sinosoft.schema.ZWWorkflowSchema;
import com.sinosoft.workflow.Workflow.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工作流工具类
 */
public class WorkflowUtil {
	private static final Logger logger = LoggerFactory.getLogger(WorkflowUtil.class);

	private static Mapx WorkflowMap = new Mapx();
	private static Object mutex = new Object();

	/**
	 * 查找工作流定义
	 */
	public static Workflow findWorkflow(long workflowID) {
		Long key = new Long(workflowID);
		if (WorkflowMap.containsKey(key)) {
			return (Workflow) WorkflowMap.get(key);
		} else {
			Workflow wf = new Workflow();
			wf.setID(workflowID);
			if (!wf.fill()) {
				return null;
			}
			wf.init();
			synchronized (mutex) {
				WorkflowMap.put(key, wf);
			}
			return wf;
		}
	}

	/**
	 * 创建一个工作流实例
	 */
	public static Context createInstance(Transaction tran, long workflowID, String name, String dataID,
			String dataVersionID) throws Exception {
		WorkflowInstance instance = new WorkflowInstance();
		instance.setWorkflowID(workflowID);
		instance.setAddTime(new Date());
		instance.setAddUser(User.getUserName());
		instance.setDataID(dataID);
		instance.setName(name);
		instance.setState(WorkflowInstance.ACTIVATED);
		instance.setID(NoUtil.getMaxID("WorkflowInstanceID"));
		tran.add(instance, Transaction.INSERT);

		ZWStepSchema newStep = new ZWStepSchema();
		newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
		newStep.setWorkflowID(instance.getWorkflowID());
		newStep.setNodeID(findWorkflow(instance.getWorkflowID()).getStartNode().getID());
		newStep.setActionID(WorkflowAction.START_ACTIONID);// 创建时的动作是暂存
		newStep.setPreviousStepID(-1);// 没有上一步
		newStep.setInstanceID(instance.getID());
		newStep.setDataVersionID(dataVersionID);
		newStep.setOwner(User.getUserName());
		newStep.setStartTime(new Date());
		newStep.setMemo("");
		newStep.setOperators(User.getUserName());
		newStep.setState(WorkflowStep.UNDERWAY);
		newStep.setAddTime(new Date());
		newStep.setAddUser(User.getUserName());
		tran.add(newStep, Transaction.INSERT);

		Context context = new Context(tran, instance, newStep);
		findAdapter().onStepCreate(context);

		WorkflowAction.executeMethod(context, findWorkflow(instance.getWorkflowID()).getStartNode(), "Post");// 执行开始节点的后置方法
		return context;
	}

	/**
	 * 根据工作流ID和数据ID，查找实例
	 */
	public static WorkflowInstance findInstance(long workflowID, String dataID) throws WorkflowException {
		ZWInstanceSchema wi = new ZWInstanceSchema();
		wi.setWorkflowID(workflowID);
		wi.setDataID(dataID);
		ZWInstanceSet set = wi.query();
		if (set.size() == 0) {
			throw new WorkflowException("未能找到指定的工作流实例：WorkflowID=" + workflowID + ",DataID=" + dataID);
		}
		if (set.size() > 1) {
			throw new WorkflowException("找到的工作流实例个数大于一：WorkflowID=" + workflowID + ",DataID=" + dataID);
		}
		wi = set.get(0);
		WorkflowInstance wfi = new WorkflowInstance();
		for (int i = 0; i < wfi.getColumnCount(); i++) {
			wfi.setV(i, wi.getV(i));
		}
		return wfi;
	}

	/**
	 * 根据ID查找实例
	 */
	public static WorkflowInstance findInstance(long instanceID) throws WorkflowException {
		ZWInstanceSchema wi = new ZWInstanceSchema();
		wi.setID(instanceID);
		if (!wi.fill()) {
			return null;
			//throw new WorkflowException("未能找到指定的工作流实例：ID=" + instanceID);
		}
		WorkflowInstance wfi = new WorkflowInstance();
		for (int i = 0; i < wfi.getColumnCount(); i++) {
			wfi.setV(i, wi.getV(i));
		}
		return wfi;
	}

	/**
	 * 根据ID查找步骤
	 */
	public static WorkflowStep findStep(long stepID) throws WorkflowException {
		ZWStepSchema step = new ZWStepSchema();
		step.setID(stepID);
		if (!step.fill()) {
			throw new WorkflowException("未能找到指定的工作流步骤：ID=" + stepID);
		}
		WorkflowStep wfs = new WorkflowStep();
		for (int i = 0; i < wfs.getColumnCount(); i++) {
			wfs.setV(i, step.getV(i));
		}
		return wfs;
	}

	/**
	 * 获取当前用户正在处理的步骤
	 */
	public static ZWStepSchema findCurrentStep(long instanceID) throws WorkflowException {
		QueryBuilder qb = new QueryBuilder("where InstanceID=? and (State=? or State=?)", instanceID,
				WorkflowStep.UNDERWAY);
		qb.add(WorkflowStep.UNREAD);
		ZWStepSet set = new WorkflowStep().query(qb);
		for (int i = 0; i < set.size(); i++) {
			ZWStepSchema step = set.get(i);
			if (step.getState().equals(WorkflowStep.UNDERWAY) && step.getOwner().equals(User.getUserName())) {
				return step;
			}
		}
		for (int i = 0; i < set.size(); i++) {
			ZWStepSchema step = set.get(i);
			if (step.getState().equals(WorkflowStep.UNREAD)
					&& hasPriv(step.getAllowOrgan(), step.getAllowRole(), step.getAllowUser())) {
				if ("1".equals(findWorkflow(step.getWorkflowID()).getData().get("NotNeedApply"))) {
					return step;
				}
			}
		}
		throw new WorkflowException("未能找到当前步骤:InstanceID=" + instanceID + ",User=" + User.getUserName());
	}

	/**
	 * 获取流程的开始节点可以执行的动作
	 */
	public static WorkflowAction[] findInitActions(long workflowID) throws Exception {
		Workflow wf = WorkflowUtil.findWorkflow(workflowID);
		Node[] nodes = wf.getNodes();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType().equals(Workflow.STARTNODE)) {
				ArrayList list = new ArrayList(4);
				// 加入临时保存Action
				list.add(WorkflowAction.getTemporarySaveAction());
				Context ctx = new Context(workflowID);
				WorkflowTransition[] tss = nodes[i].getTransitions();
				for (int j = 0; j < tss.length; j++) {
					if (tss[j].validate(ctx)) {
						list.add(new WorkflowAction(tss[j].getTargetNode()));
					}
				}
				WorkflowAction[] actions = new WorkflowAction[list.size()];
				for (int j = 0; j < list.size(); j++) {
					actions[j] = (WorkflowAction) list.get(j);
				}
				return actions;
			}
		}
		return null;
	}

	/**
	 * 获取当前用户可以执行的动作
	 */
	public static WorkflowAction[] findAvaiableActions(long instanceID) throws Exception {
		WorkflowInstance instance = findInstance(instanceID);
		return findAvaiableActions(instance);
	}

	/**
	 * 获取当前用户可以执行的动作
	 */
	public static WorkflowAction[] findAvaiableActions(WorkflowInstance instance) throws Exception {
		if(null==instance){
			return null;
		}
		if (instance.getState().equals(WorkflowInstance.COMPLETED)) {
			return new WorkflowAction[0];// 己完成的流程
		}
		ZWStepSchema step = new ZWStepSchema();
		QueryBuilder qb = new QueryBuilder("where InstanceID=? and (State=? or State=?)");
		qb.add(instance.getID());
		qb.add(WorkflowStep.UNDERWAY);
		qb.add(WorkflowStep.UNREAD);
		ZWStepSet set = step.query(qb);
		ArrayList list = new ArrayList(4);
		Workflow flow = WorkflowUtil.findWorkflow(instance.getWorkflowID());

		// 先检查有没有正在处理的
		boolean underwayFlag = false;
		for (int i = 0; i < set.size(); i++) {
			step = set.get(i);
			Transaction tran = new Transaction();
			Context ctx = new Context(tran, step);
			if (step.getOwner() != null && step.getOwner().equals(User.getUserName())) {// 当前正在处理的任务
				Node node = flow.findNode(step.getNodeID());

				// 加入临时保存Action
				list.add(WorkflowAction.getTemporarySaveAction());

				WorkflowTransition[] tss = node.getTransitions();
				for (int j = 0; j < tss.length; j++) {
					if (tss[j].validate(ctx)) {
						list.add(new WorkflowAction(tss[j].getTargetNode()));
					}
				}
				underwayFlag = true;
				break;
			}
		}
		if (!underwayFlag) {// 有正在处理的步骤则不允许申请新步骤
			for (int i = 0; i < set.size(); i++) {
				step = set.get(i);
				if (step.getState().equals(WorkflowStep.UNREAD)) {
					// 检查是否具有权限
					if (hasPriv(step.getAllowOrgan(), step.getAllowRole(), step.getAllowUser())) {
						if (!"1".equals(flow.getData().get("NotNeedApply"))) {
							list.add(WorkflowAction.getApplyAction(step.getNodeID()));// 加入申请Action
						} else {
							// 如果只有一个人可以申请，则直接显示操作按钮
							Transaction tran = new Transaction();
							Context ctx = new Context(tran, step);
							Node node = flow.findNode(step.getNodeID());

							// 加入临时保存Action
							list.add(WorkflowAction.getTemporarySaveAction());

							WorkflowTransition[] tss = node.getTransitions();
							for (int j = 0; j < tss.length; j++) {
								if (tss[j].validate(ctx)) {
									list.add(new WorkflowAction(tss[j].getTargetNode()));
								}
							}
							underwayFlag = true;
						}
					}
				}
			}
		}
		// 去掉重复的Action
		for (int i = list.size() - 1; i >= 0; i--) {
			WorkflowAction a1 = (WorkflowAction) list.get(i);
			for (int j = i - 1; j >= 0; j--) {
				WorkflowAction a2 = (WorkflowAction) list.get(j);
				if (a1.getID() == a2.getID()) {
					list.remove(i);
				}
			}
		}
		WorkflowAction[] actions = new WorkflowAction[list.size()];
		for (int i = 0; i < list.size(); i++) {
			actions[i] = (WorkflowAction) list.get(i);
		}
		return actions;
	}

	/**
	 * 根据实例ID和节点ID查找未读步骤
	 */
	public static ZWStepSchema findUnreadStep(long instanceID, int nodeID) throws WorkflowException {
		ZWStepSchema step = new ZWStepSchema();
		step.setInstanceID(instanceID);
		step.setNodeID(nodeID);
		step.setState(WorkflowStep.UNREAD);
		ZWStepSet set = step.query();
		if (set.size() > 0) {
			step = set.get(0);
			return step;
		}
		throw new WorkflowException("找不到可以申请的步骤!InstanceID=" + instanceID + ",NodeID=" + nodeID);
	}

	/**
	 * 申请工作流步骤
	 */
	public static void applyStep(long instanceID, int nodeID) throws Exception {
		ZWStepSchema step = findUnreadStep(instanceID, nodeID);
		if (!hasPriv(step.getAllowOrgan(), step.getAllowRole(), step.getAllowUser())) {
			throw new WorkflowException("用户没有申请流程步骤" + step.getNodeID() + "的权限，WorkflowID=" + step.getWorkflowID());
		}
		QueryBuilder qb = new QueryBuilder(
				"update ZWStep set State=?,Owner=? where State=? and InstanceID=? and NodeID=? and ID=?");
		qb.add(WorkflowStep.UNDERWAY);
		qb.add(User.getUserName());
		qb.add(WorkflowStep.UNREAD);
		qb.add(instanceID);
		qb.add(nodeID);
		qb.add(step.getID());// 必须要加StepID，会签模式下可能会有多个同一NodeID的步骤
		if (qb.executeNoQuery() == 0) {
			throw new WorkflowException("申请失败，可能己被别的用户申请!");
		}
	}

	/**
	 * 强制结束流程
	 */
	public static void forceEnd(long instanceID, int nodeID) throws Exception {
		Transaction tran = new Transaction();
		forceEnd(tran, instanceID, nodeID);
		if (!tran.commit()) {
			throw new WorkflowException(tran.getExceptionMessage());
		}
	}

	public static void forceEnd(Transaction tran, long instanceID, int nodeID) throws Exception {
		if (!UserList.ADMINISTRATOR.equals(User.getUserName())) {
			throw new WorkflowException("当前用户没有强制结束流程的权限!");
		}

		WorkflowInstance instance = findInstance(instanceID);
		Workflow flow = findWorkflow(instance.getWorkflowID());
		if (flow == null) {// 工作流定义己被删除
			logger.warn("强制结束流程时发现ID为{}的流程不存在!", instance.getWorkflowID());
		} else {

			// 创建结束节点
			ZWStepSchema newStep = new ZWStepSchema();
			newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
			newStep.setWorkflowID(instance.getWorkflowID());
			newStep.setNodeID(flow.getEndNode().getID());
			newStep.setActionID(WorkflowAction.FORCEEND_ACTIONID);
			newStep.setPreviousStepID(0);
			newStep.setInstanceID(instance.getID());
			newStep.setDataVersionID("0");
			newStep.setOwner(User.getUserName());
			newStep.setStartTime(new Date());
			newStep.setMemo("强制结束流程");
			newStep.setOperators(User.getUserName());
			newStep.setState(WorkflowStep.FINISHED);
			newStep.setAddTime(new Date());
			newStep.setAddUser(User.getUserName());

			tran.add(newStep, Transaction.INSERT);
			Context context = new Context(tran, instance, newStep);
			findAdapter().onStepCreate(context);
			WorkflowAction.executeMethod(context, findWorkflow(instance.getWorkflowID()).getEndNode(), "Pre");// 执行开始节点的后置方法
		}

		instance.setState(WorkflowInstance.COMPLETED);
		tran.add(instance, Transaction.UPDATE);
		tran.add(new QueryBuilder("update ZWStep set State=? where InstanceID=?", WorkflowStep.FINISHED, instanceID));
	}

	/**
	 * 判断一个用户是否属于指定的角色或部门或一个用户列表中
	 */
	private static boolean hasPriv(String[] branchInnerCodes, String[] roleCodes, String[] userNames) {
		if (userNames != null) {
			for (int i = 0; i < userNames.length; i++) {
				if (userNames[i].equals(User.getUserName())) {
					return true;
				}
			}
		}
		boolean flag = false;
		if (branchInnerCodes != null) {// 机构与角色之间是与的关系
			String innerCode = User.getBranchInnerCode();
			if (StringUtil.isNotEmpty(innerCode)) {
				for (int i = 0; i < branchInnerCodes.length; i++) {
					if (innerCode.equals((branchInnerCodes[i]))) {
						flag = true;
						break;
					}
				}
			}
		} else {
			flag = true;
		}
		if (flag) {
			if (roleCodes != null) {
				List list = PubFun.getRoleCodesByUserName(User.getUserName());
				for (int i = 0; i < roleCodes.length; i++) {
					if (list.contains(roleCodes[i])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean hasPriv(String branchInnerCodes, String roleCodes, String userNames) {
		String[] p1 = null;
		String[] p2 = null;
		String[] p3 = null;
		if (StringUtil.isNotEmpty(branchInnerCodes)) {
			p1 = StringUtil.splitEx(branchInnerCodes, ",");
		}
		if (StringUtil.isNotEmpty(roleCodes)) {
			p2 = StringUtil.splitEx(roleCodes, ",");
		}
		if (StringUtil.isNotEmpty(userNames)) {
			p3 = StringUtil.splitEx(userNames, ",");
		}
		return hasPriv(p1, p2, p3);
	}

	/**
	 * 根据工作流ID和动作ID查找动作
	 */
	public static WorkflowAction findAction(long workflowID, int actionID) {
		Workflow wf = findWorkflow(workflowID);
		if (actionID == WorkflowAction.TEMPORARYSAVE_ACTIONID) {
			return WorkflowAction.getTemporarySaveAction();
		}
		if (actionID == WorkflowAction.RESTART_ACTIONID) {
			return WorkflowAction.getRestartAction();
		}
		if (actionID == WorkflowAction.START_ACTIONID) {
			return new WorkflowAction(actionID, "开始流转", new Mapx());
		}
		if (actionID == WorkflowAction.SCRIPT_ACTIONID) {
			return new WorkflowAction(actionID, "脚本跳转", new Mapx());
		}
		if (actionID == WorkflowAction.FORCEEND_ACTIONID) {
			return new WorkflowAction(actionID, "强制结束", new Mapx());
		}
		Node node = wf.findNode(actionID);
		if (node.getType().equals(Workflow.ACTIONNODE)) {
			return new WorkflowAction(node);
		}
		return null;
	}

	private static WorkflowAdapter adapter;

	/**
	 * 获取工作流适配器
	 */
	public static WorkflowAdapter findAdapter() {
		if (adapter == null) {
			synchronized (mutex) {
				if (adapter == null) {
					String className = Config.getValue("App.WorkflowAdapter");
					if (StringUtil.isEmpty(className)) {
						throw new RuntimeException("未定义工作流适配器!");
					}
					try {
						Class c = Class.forName(className);
						if (!WorkflowAdapter.class.isAssignableFrom(c)) {
							throw new RuntimeException("类" + className + "未继承WorkflowAdapter!");
						}
						adapter = (WorkflowAdapter) c.newInstance();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return adapter;
	}

	/**
	 * 获取流程数据
	 */
	public static Mapx getDataVariables(String dataID, String dataVersionID) {
		return findAdapter().getVariables(dataID, dataVersionID);
	}

	/**
	 * 保存流程数据
	 */
	public static boolean saveDataVariables(Context context) {
		return findAdapter().saveVariables(context);
	}

	/**
	 * 获取步骤名称
	 */
	public static String getStepName(long workflowID, int nodeID) {
		Workflow flow = WorkflowUtil.findWorkflow(workflowID);
		if (flow == null) {
			return "未找到工作流";
		}
		Node node = flow.findNode(nodeID);
		if (node == null) {
			return "未找到步骤";
		}
		return node.getName();
	}

	/**
	 * 获取动作名称
	 */
	public static String getActionNodeName(long workflowID, int actionID) {
		return findAction(workflowID, actionID).getName();
	}

	/**
	 * 删除实例
	 */
	public static void deleteInstance(Transaction tran, long instanceID) {
		ZWInstanceSet iset = new ZWInstanceSchema().query(new QueryBuilder("where ID=?", instanceID));
		ZWStepSet sset = new ZWStepSchema().query(new QueryBuilder("where InstanceID=?", instanceID));
		tran.add(iset, Transaction.DELETE_AND_BACKUP);
		tran.add(sset, Transaction.DELETE_AND_BACKUP);
	}

	/**
	 * 更新缓存数据
	 */
	public static void updateCache(ZWWorkflowSchema schema) {
		synchronized (mutex) {
			WorkflowMap.put(new Long(schema.getID()), Workflow.convert(schema));
		}
	}

	/**
	 * 删除缓存数据
	 */
	public static void deleteCache(ZWWorkflowSchema schema) {
		synchronized (mutex) {
			WorkflowMap.remove(new Long(schema.getID()));
		}
	}

	private static boolean checkPrivString(String str) {
		if (str.indexOf('\'') > 0 || str.indexOf('\"') > 0) {
			return false;
		}
		if (str.indexOf('(') > 0 || str.indexOf(')') > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断本节点是否有用户有权限审核
	 */
	public static boolean hasPrivUser(ZWStepSchema step) throws WorkflowException {
		if (StringUtil.isNotEmpty(step.getAllowUser())) {
			return true;
		}
		QueryBuilder qb = new QueryBuilder("select count(1) from ZDUser where 1=1");
		if (StringUtil.isNotEmpty(step.getAllowOrgan())) {
			if (!checkPrivString(step.getAllowOrgan())) {
				throw new WorkflowException("AllowOrgan中含有非法字符,StepID=" + step.getID());
			}
			qb.append(" and BranchInnerCode in ('" + step.getAllowOrgan().replaceAll(",", "','") + "')");
		}
		if (StringUtil.isNotEmpty(step.getAllowRole())) {
			if (!checkPrivString(step.getAllowRole())) {
				throw new WorkflowException("AllowRole中含有非法字符,StepID=" + step.getID());
			}
			qb.append(" and exists (select 1 from ZDUserRole where UserName=ZDUser.UserName and RoleCode in ('"
					+ step.getAllowRole().replaceAll(",", "','") + "'))");
		}
		if (StringUtil.isNotEmpty(step.getAllowOrgan()) || StringUtil.isNotEmpty(step.getAllowRole())) {
			return qb.executeInt() > 0;
		}
		return false;
	}

	public static String[] getPrivUsers(ZWStepSchema step) throws WorkflowException {
		Mapx map = new Mapx();
		if (StringUtil.isNotEmpty(step.getAllowUser())) {
			if (checkPrivString(step.getAllowUser())) {
				String[] p1 = step.getAllowUser().split(",");
				for (int i = 0; i < p1.length; i += 2) {// 角色代码和名称都有
					map.put(p1[i], "1");
				}
			}
		}
		QueryBuilder qb = new QueryBuilder("select username,1 from ZDUser where 1=1");
		if (StringUtil.isNotEmpty(step.getAllowOrgan())) {
			if (!checkPrivString(step.getAllowOrgan())) {
				throw new WorkflowException("AllowOrgan中含有非法字符,StepID=" + step.getID());
			}
			qb.append(" and BranchInnerCode in ('" + step.getAllowOrgan().replaceAll(",", "','") + "')");
		}
		if (StringUtil.isNotEmpty(step.getAllowRole())) {
			if (!checkPrivString(step.getAllowRole())) {
				throw new WorkflowException("AllowRole中含有非法字符,StepID=" + step.getID());
			}
			qb.append(" and exists (select 1 from ZDUserRole where UserName=ZDUser.UserName and RoleCode in ('"
					+ step.getAllowRole().replaceAll(",", "','") + "'))");
		}
		if (StringUtil.isNotEmpty(step.getAllowOrgan()) || StringUtil.isNotEmpty(step.getAllowRole())) {
			map.putAll(qb.executeDataTable().toMapx(0, 1));
		}
		String[] users = new String[map.size()];
		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			users[i] = ks[i].toString();
		}
		return users;
	}

	/**
	 * 判断当前步骤是否是起始步骤
	 */
	public static boolean isStartStep(long instanceID) {
		QueryBuilder qb = new QueryBuilder("where InstanceID=? and (State=? or State=?)", instanceID);
		qb.add(WorkflowStep.UNDERWAY);
		qb.add(WorkflowStep.UNREAD);
		ZWStepSet set = new ZWStepSchema().query(qb);
		for (int i = 0; i < set.size(); i++) {
			if (WorkflowUtil.findWorkflow(set.get(i).getWorkflowID()).getStartNode().getID() == set.get(i).getNodeID()) {
				return true;
			}
		}
		return false;
	}
}
