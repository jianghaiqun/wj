package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 表名称：定时任务表<br>
 * 表代码：ZDSchedule<br>
 * 表主键：ID<br>
 */
public class ZDScheduleSchema extends Schema {
	private static final Logger logger = LoggerFactory.getLogger(ZDScheduleSchema.class);
	private Long ID;

	private Long SourceID;

	private String TypeCode;

	private String CronExpression;

	private String PlanType;

	private Date StartTime;

	private String Description;

	private String IsUsing;

	private String OrderFlag;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] { new SchemaColumn("ID", DataColumn.LONG, 0, 0, 0, true, true),
			new SchemaColumn("SourceID", DataColumn.LONG, 1, 0, 0, true, false), new SchemaColumn("TypeCode", DataColumn.STRING, 2, 30, 0, true, false),
			new SchemaColumn("CronExpression", DataColumn.STRING, 3, 100, 0, true, false), new SchemaColumn("PlanType", DataColumn.STRING, 4, 10, 0, true, false),
			new SchemaColumn("StartTime", DataColumn.DATETIME, 5, 0, 0, false, false), new SchemaColumn("Description", DataColumn.STRING, 6, 255, 0, false, false),
			new SchemaColumn("IsUsing", DataColumn.STRING, 7, 2, 0, true, false), new SchemaColumn("OrderFlag", DataColumn.STRING, 8, 50, 0, false, false),
			new SchemaColumn("Prop1", DataColumn.STRING, 9, 50, 0, false, false), new SchemaColumn("Prop2", DataColumn.STRING, 10, 50, 0, false, false),
			new SchemaColumn("Prop3", DataColumn.STRING, 11, 50, 0, false, false), new SchemaColumn("Prop4", DataColumn.STRING, 12, 50, 0, false, false),
			new SchemaColumn("AddUser", DataColumn.STRING, 13, 200, 0, true, false), new SchemaColumn("AddTime", DataColumn.DATETIME, 14, 0, 0, true, false),
			new SchemaColumn("ModifyUser", DataColumn.STRING, 15, 200, 0, false, false), new SchemaColumn("ModifyTime", DataColumn.DATETIME, 16, 0, 0, false, false) };

	public static final String _TableCode = "ZDSchedule";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDSchedule values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDSchedule set ID=?,SourceID=?,TypeCode=?,CronExpression=?,PlanType=?,StartTime=?,Description=?,IsUsing=?,OrderFlag=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDSchedule  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDSchedule  where ID=?";

	public ZDScheduleSchema() {
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance() {
		return new ZDScheduleSchema();
	}

	protected SchemaSet newSet() {
		return new ZDScheduleSet();
	}

	public ZDScheduleSet query() {
		return query(null, -1, -1);
	}

	public ZDScheduleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDScheduleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDScheduleSet query(QueryBuilder qb, int pageSize, int pageIndex) {
		return (ZDScheduleSet) querySet(qb, pageSize, pageIndex);
	}

	public void setV(int i, Object v) {
		try {

			if (i == 0) {
				if (v == null) {
					ID = null;
				} else {
					ID = new Long(v.toString());
				}
				return;
			}
			if (i == 1) {
				if (v == null) {
					SourceID = null;
				} else {
					SourceID = new Long(v.toString());
				}
				return;
			}
			if (i == 2) {
				TypeCode = (String) v;
				return;
			}
			if (i == 3) {
				CronExpression = (String) v;
				return;
			}
			if (i == 4) {
				PlanType = (String) v;
				return;
			}
			if (i == 5) {
				StartTime = (Date) v;
				return;
			}
			if (i == 6) {
				Description = (String) v;
				return;
			}
			if (i == 7) {
				IsUsing = (String) v;
				return;
			}
			if (i == 8) {
				OrderFlag = (String) v;
				return;
			}
			if (i == 9) {
				Prop1 = (String) v;
				return;
			}
			if (i == 10) {
				Prop2 = (String) v;
				return;
			}
			if (i == 11) {
				Prop3 = (String) v;
				return;
			}
			if (i == 12) {
				Prop4 = (String) v;
				return;
			}
			if (i == 13) {
				AddUser = (String) v;
				return;
			}
			if (i == 14) {
				AddTime = (Date) v;
				return;
			}
			if (i == 15) {
				ModifyUser = (String) v;
				return;
			}
			if (i == 16) {
				ModifyTime = (Date) v;
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Object getV(int i) {
		if (i == 0) {
			return ID;
		}
		if (i == 1) {
			return SourceID;
		}
		if (i == 2) {
			return TypeCode;
		}
		if (i == 3) {
			return CronExpression;
		}
		if (i == 4) {
			return PlanType;
		}
		if (i == 5) {
			return StartTime;
		}
		if (i == 6) {
			return Description;
		}
		if (i == 7) {
			return IsUsing;
		}
		if (i == 8) {
			return OrderFlag;
		}
		if (i == 9) {
			return Prop1;
		}
		if (i == 10) {
			return Prop2;
		}
		if (i == 11) {
			return Prop3;
		}
		if (i == 12) {
			return Prop4;
		}
		if (i == 13) {
			return AddUser;
		}
		if (i == 14) {
			return AddTime;
		}
		if (i == 15) {
			return ModifyUser;
		}
		if (i == 16) {
			return ModifyTime;
		}
		return null;
	}

	/**
	 * 获取字段ID的值，该字段的<br>
	 * 字段名称 :定时ID<br>
	 * 数据类型 :bigint<br>
	 * 是否主键 :true<br>
	 * 是否必填 :true<br>
	 */
	public long getID() {
		if (ID == null) {
			return 0;
		}
		return ID.longValue();
	}

	/**
	 * 设置字段ID的值，该字段的<br>
	 * 字段名称 :定时ID<br>
	 * 数据类型 :bigint<br>
	 * 是否主键 :true<br>
	 * 是否必填 :true<br>
	 */
	public void setID(long iD) {
		this.ID = new Long(iD);
	}

	/**
	 * 设置字段ID的值，该字段的<br>
	 * 字段名称 :定时ID<br>
	 * 数据类型 :bigint<br>
	 * 是否主键 :true<br>
	 * 是否必填 :true<br>
	 */
	public void setID(String iD) {
		if (iD == null) {
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
	}

	/**
	 * 获取字段SourceID的值，该字段的<br>
	 * 字段名称 :源ID(供任务管理类调用)<br>
	 * 数据类型 :bigint<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 * 备注信息 :<br>
	 * SYSTEM 为系统内置任务不能删除<br>
	 * USER 为用户自定义任务<br>
	 */
	public long getSourceID() {
		if (SourceID == null) {
			return 0;
		}
		return SourceID.longValue();
	}

	/**
	 * 设置字段SourceID的值，该字段的<br>
	 * 字段名称 :源ID(供任务管理类调用)<br>
	 * 数据类型 :bigint<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 * 备注信息 :<br>
	 * SYSTEM 为系统内置任务不能删除<br>
	 * USER 为用户自定义任务<br>
	 */
	public void setSourceID(long sourceID) {
		this.SourceID = new Long(sourceID);
	}

	/**
	 * 设置字段SourceID的值，该字段的<br>
	 * 字段名称 :源ID(供任务管理类调用)<br>
	 * 数据类型 :bigint<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 * 备注信息 :<br>
	 * SYSTEM 为系统内置任务不能删除<br>
	 * USER 为用户自定义任务<br>
	 */
	public void setSourceID(String sourceID) {
		if (sourceID == null) {
			this.SourceID = null;
			return;
		}
		this.SourceID = new Long(sourceID);
	}

	/**
	 * 获取字段TypeCode的值，该字段的<br>
	 * 字段名称 :任务类型代码<br>
	 * 数据类型 :varchar(30)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public String getTypeCode() {
		return TypeCode;
	}

	/**
	 * 设置字段TypeCode的值，该字段的<br>
	 * 字段名称 :任务类型代码<br>
	 * 数据类型 :varchar(30)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public void setTypeCode(String typeCode) {
		this.TypeCode = typeCode;
	}

	/**
	 * 获取字段CronExpression的值，该字段的<br>
	 * 字段名称 :执行表达式<br>
	 * 数据类型 :varchar(100)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public String getCronExpression() {
		return CronExpression;
	}

	/**
	 * 设置字段CronExpression的值，该字段的<br>
	 * 字段名称 :执行表达式<br>
	 * 数据类型 :varchar(100)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public void setCronExpression(String cronExpression) {
		this.CronExpression = cronExpression;
	}

	/**
	 * 获取字段PlanType的值，该字段的<br>
	 * 字段名称 :计划类型<br>
	 * 数据类型 :varchar(10)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public String getPlanType() {
		return PlanType;
	}

	/**
	 * 设置字段PlanType的值，该字段的<br>
	 * 字段名称 :计划类型<br>
	 * 数据类型 :varchar(10)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public void setPlanType(String planType) {
		this.PlanType = planType;
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
	 * 获取字段Description的值，该字段的<br>
	 * 字段名称 :任务描述<br>
	 * 数据类型 :varchar(255)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * 设置字段Description的值，该字段的<br>
	 * 字段名称 :任务描述<br>
	 * 数据类型 :varchar(255)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setDescription(String description) {
		this.Description = description;
	}

	/**
	 * 获取字段IsUsing的值，该字段的<br>
	 * 字段名称 :是否启用<br>
	 * 数据类型 :char(2)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public String getIsUsing() {
		return IsUsing;
	}

	/**
	 * 设置字段IsUsing的值，该字段的<br>
	 * 字段名称 :是否启用<br>
	 * 数据类型 :char(2)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public void setIsUsing(String isUsing) {
		this.IsUsing = isUsing;
	}

	/**
	 * 获取字段OrderFlag的值，该字段的<br>
	 * 字段名称 :排序标记<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getOrderFlag() {
		return OrderFlag;
	}

	/**
	 * 设置字段OrderFlag的值，该字段的<br>
	 * 字段名称 :排序标记<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setOrderFlag(String orderFlag) {
		this.OrderFlag = orderFlag;
	}

	/**
	 * 获取字段Prop1的值，该字段的<br>
	 * 字段名称 :备用字段1<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getProp1() {
		return Prop1;
	}

	/**
	 * 设置字段Prop1的值，该字段的<br>
	 * 字段名称 :备用字段1<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
	}

	/**
	 * 获取字段Prop2的值，该字段的<br>
	 * 字段名称 :备用字段2<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getProp2() {
		return Prop2;
	}

	/**
	 * 设置字段Prop2的值，该字段的<br>
	 * 字段名称 :备用字段2<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
	}

	/**
	 * 获取字段Prop3的值，该字段的<br>
	 * 字段名称 :备用字段3<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getProp3() {
		return Prop3;
	}

	/**
	 * 设置字段Prop3的值，该字段的<br>
	 * 字段名称 :备用字段3<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
	}

	/**
	 * 获取字段Prop4的值，该字段的<br>
	 * 字段名称 :备用字段4<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getProp4() {
		return Prop4;
	}

	/**
	 * 设置字段Prop4的值，该字段的<br>
	 * 字段名称 :备用字段4<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
	}

	/**
	 * 获取字段AddUser的值，该字段的<br>
	 * 字段名称 :添加者<br>
	 * 数据类型 :varchar(200)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public String getAddUser() {
		return AddUser;
	}

	/**
	 * 设置字段AddUser的值，该字段的<br>
	 * 字段名称 :添加者<br>
	 * 数据类型 :varchar(200)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
	}

	/**
	 * 获取字段AddTime的值，该字段的<br>
	 * 字段名称 :添加时间<br>
	 * 数据类型 :datetime<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public Date getAddTime() {
		return AddTime;
	}

	/**
	 * 设置字段AddTime的值，该字段的<br>
	 * 字段名称 :添加时间<br>
	 * 数据类型 :datetime<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
	}

	/**
	 * 获取字段ModifyUser的值，该字段的<br>
	 * 字段名称 :最后修改人<br>
	 * 数据类型 :varchar(200)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	 * 设置字段ModifyUser的值，该字段的<br>
	 * 字段名称 :最后修改人<br>
	 * 数据类型 :varchar(200)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
	}

	/**
	 * 获取字段ModifyTime的值，该字段的<br>
	 * 字段名称 :最后修改时间<br>
	 * 数据类型 :datetime<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	 * 设置字段ModifyTime的值，该字段的<br>
	 * 字段名称 :最后修改时间<br>
	 * 数据类型 :datetime<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
	}

}