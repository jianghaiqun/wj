package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：流程步骤表<br>
 * 表代码：ZWStep<br>
 * 表主键：ID<br>
 */
public class ZWStepSchema extends Schema {
	private Long ID;

	private Long WorkflowID;

	private Long InstanceID;

	private String DataVersionID;

	private Integer NodeID;

	private Integer ActionID;

	private Long PreviousStepID;

	private String Owner;

	private Date StartTime;

	private Date FinishTime;

	private String State;

	private String Operators;

	private String AllowUser;

	private String AllowOrgan;

	private String AllowRole;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("WorkflowID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("InstanceID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("DataVersionID", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("NodeID", DataColumn.INTEGER, 4, 0 , 0 , false , false),
		new SchemaColumn("ActionID", DataColumn.INTEGER, 5, 0 , 0 , false , false),
		new SchemaColumn("PreviousStepID", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("Owner", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("FinishTime", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("State", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("Operators", DataColumn.STRING, 11, 400 , 0 , false , false),
		new SchemaColumn("AllowUser", DataColumn.STRING, 12, 4000 , 0 , false , false),
		new SchemaColumn("AllowOrgan", DataColumn.STRING, 13, 4000 , 0 , false , false),
		new SchemaColumn("AllowRole", DataColumn.STRING, 14, 4000 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 15, 400 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 17, 50 , 0 , true , false)
	};

	public static final String _TableCode = "ZWStep";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZWStep values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZWStep set ID=?,WorkflowID=?,InstanceID=?,DataVersionID=?,NodeID=?,ActionID=?,PreviousStepID=?,Owner=?,StartTime=?,FinishTime=?,State=?,Operators=?,AllowUser=?,AllowOrgan=?,AllowRole=?,Memo=?,AddTime=?,AddUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZWStep  where ID=?";

	protected static final String _FillAllSQL = "select * from ZWStep  where ID=?";

	public ZWStepSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new ZWStepSchema();
	}

	protected SchemaSet newSet(){
		return new ZWStepSet();
	}

	public ZWStepSet query() {
		return query(null, -1, -1);
	}

	public ZWStepSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZWStepSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZWStepSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZWStepSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){WorkflowID = null;}else{WorkflowID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){InstanceID = null;}else{InstanceID = new Long(v.toString());}return;}
		if (i == 3){DataVersionID = (String)v;return;}
		if (i == 4){if(v==null){NodeID = null;}else{NodeID = new Integer(v.toString());}return;}
		if (i == 5){if(v==null){ActionID = null;}else{ActionID = new Integer(v.toString());}return;}
		if (i == 6){if(v==null){PreviousStepID = null;}else{PreviousStepID = new Long(v.toString());}return;}
		if (i == 7){Owner = (String)v;return;}
		if (i == 8){StartTime = (Date)v;return;}
		if (i == 9){FinishTime = (Date)v;return;}
		if (i == 10){State = (String)v;return;}
		if (i == 11){Operators = (String)v;return;}
		if (i == 12){AllowUser = (String)v;return;}
		if (i == 13){AllowOrgan = (String)v;return;}
		if (i == 14){AllowRole = (String)v;return;}
		if (i == 15){Memo = (String)v;return;}
		if (i == 16){AddTime = (Date)v;return;}
		if (i == 17){AddUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return WorkflowID;}
		if (i == 2){return InstanceID;}
		if (i == 3){return DataVersionID;}
		if (i == 4){return NodeID;}
		if (i == 5){return ActionID;}
		if (i == 6){return PreviousStepID;}
		if (i == 7){return Owner;}
		if (i == 8){return StartTime;}
		if (i == 9){return FinishTime;}
		if (i == 10){return State;}
		if (i == 11){return Operators;}
		if (i == 12){return AllowUser;}
		if (i == 13){return AllowOrgan;}
		if (i == 14){return AllowRole;}
		if (i == 15){return Memo;}
		if (i == 16){return AddTime;}
		if (i == 17){return AddUser;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段WorkflowID的值，该字段的<br>
	* 字段名称 :流程ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getWorkflowID() {
		if(WorkflowID==null){return 0;}
		return WorkflowID.longValue();
	}

	/**
	* 设置字段WorkflowID的值，该字段的<br>
	* 字段名称 :流程ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWorkflowID(long workflowID) {
		this.WorkflowID = new Long(workflowID);
    }

	/**
	* 设置字段WorkflowID的值，该字段的<br>
	* 字段名称 :流程ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWorkflowID(String workflowID) {
		if (workflowID == null){
			this.WorkflowID = null;
			return;
		}
		this.WorkflowID = new Long(workflowID);
    }

	/**
	* 获取字段InstanceID的值，该字段的<br>
	* 字段名称 :实例ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getInstanceID() {
		if(InstanceID==null){return 0;}
		return InstanceID.longValue();
	}

	/**
	* 设置字段InstanceID的值，该字段的<br>
	* 字段名称 :实例ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setInstanceID(long instanceID) {
		this.InstanceID = new Long(instanceID);
    }

	/**
	* 设置字段InstanceID的值，该字段的<br>
	* 字段名称 :实例ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setInstanceID(String instanceID) {
		if (instanceID == null){
			this.InstanceID = null;
			return;
		}
		this.InstanceID = new Long(instanceID);
    }

	/**
	* 获取字段DataVersionID的值，该字段的<br>
	* 字段名称 :数据版本ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDataVersionID() {
		return DataVersionID;
	}

	/**
	* 设置字段DataVersionID的值，该字段的<br>
	* 字段名称 :数据版本ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDataVersionID(String dataVersionID) {
		this.DataVersionID = dataVersionID;
    }

	/**
	* 获取字段NodeID的值，该字段的<br>
	* 字段名称 :节点ID<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getNodeID() {
		if(NodeID==null){return 0;}
		return NodeID.intValue();
	}

	/**
	* 设置字段NodeID的值，该字段的<br>
	* 字段名称 :节点ID<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setNodeID(int nodeID) {
		this.NodeID = new Integer(nodeID);
    }

	/**
	* 设置字段NodeID的值，该字段的<br>
	* 字段名称 :节点ID<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setNodeID(String nodeID) {
		if (nodeID == null){
			this.NodeID = null;
			return;
		}
		this.NodeID = new Integer(nodeID);
    }

	/**
	* 获取字段ActionID的值，该字段的<br>
	* 字段名称 :动作节点ID<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getActionID() {
		if(ActionID==null){return 0;}
		return ActionID.intValue();
	}

	/**
	* 设置字段ActionID的值，该字段的<br>
	* 字段名称 :动作节点ID<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActionID(int actionID) {
		this.ActionID = new Integer(actionID);
    }

	/**
	* 设置字段ActionID的值，该字段的<br>
	* 字段名称 :动作节点ID<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActionID(String actionID) {
		if (actionID == null){
			this.ActionID = null;
			return;
		}
		this.ActionID = new Integer(actionID);
    }

	/**
	* 获取字段PreviousStepID的值，该字段的<br>
	* 字段名称 :前一步骤<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getPreviousStepID() {
		if(PreviousStepID==null){return 0;}
		return PreviousStepID.longValue();
	}

	/**
	* 设置字段PreviousStepID的值，该字段的<br>
	* 字段名称 :前一步骤<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPreviousStepID(long previousStepID) {
		this.PreviousStepID = new Long(previousStepID);
    }

	/**
	* 设置字段PreviousStepID的值，该字段的<br>
	* 字段名称 :前一步骤<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPreviousStepID(String previousStepID) {
		if (previousStepID == null){
			this.PreviousStepID = null;
			return;
		}
		this.PreviousStepID = new Long(previousStepID);
    }

	/**
	* 获取字段Owner的值，该字段的<br>
	* 字段名称 :所有者<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOwner() {
		return Owner;
	}

	/**
	* 设置字段Owner的值，该字段的<br>
	* 字段名称 :所有者<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOwner(String owner) {
		this.Owner = owner;
    }

	/**
	* 获取字段StartTime的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getStartTime() {
		return StartTime;
	}

	/**
	* 设置字段StartTime的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStartTime(Date startTime) {
		this.StartTime = startTime;
    }

	/**
	* 获取字段FinishTime的值，该字段的<br>
	* 字段名称 :完成时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getFinishTime() {
		return FinishTime;
	}

	/**
	* 设置字段FinishTime的值，该字段的<br>
	* 字段名称 :完成时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFinishTime(Date finishTime) {
		this.FinishTime = finishTime;
    }

	/**
	* 获取字段State的值，该字段的<br>
	* 字段名称 :步骤状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getState() {
		return State;
	}

	/**
	* 设置字段State的值，该字段的<br>
	* 字段名称 :步骤状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setState(String state) {
		this.State = state;
    }

	/**
	* 获取字段Operators的值，该字段的<br>
	* 字段名称 :经办人<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperators() {
		return Operators;
	}

	/**
	* 设置字段Operators的值，该字段的<br>
	* 字段名称 :经办人<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperators(String operators) {
		this.Operators = operators;
    }

	/**
	* 获取字段AllowUser的值，该字段的<br>
	* 字段名称 :允许的用户<br>
	* 数据类型 :varchar(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowUser() {
		return AllowUser;
	}

	/**
	* 设置字段AllowUser的值，该字段的<br>
	* 字段名称 :允许的用户<br>
	* 数据类型 :varchar(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowUser(String allowUser) {
		this.AllowUser = allowUser;
    }

	/**
	* 获取字段AllowOrgan的值，该字段的<br>
	* 字段名称 :允许的机构<br>
	* 数据类型 :varchar(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowOrgan() {
		return AllowOrgan;
	}

	/**
	* 设置字段AllowOrgan的值，该字段的<br>
	* 字段名称 :允许的机构<br>
	* 数据类型 :varchar(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowOrgan(String allowOrgan) {
		this.AllowOrgan = allowOrgan;
    }

	/**
	* 获取字段AllowRole的值，该字段的<br>
	* 字段名称 :允许的角色<br>
	* 数据类型 :varchar(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowRole() {
		return AllowRole;
	}

	/**
	* 设置字段AllowRole的值，该字段的<br>
	* 字段名称 :允许的角色<br>
	* 数据类型 :varchar(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowRole(String allowRole) {
		this.AllowRole = allowRole;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

}