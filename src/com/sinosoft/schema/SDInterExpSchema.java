package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：横向接口经验表<br>
 * 表代码：SDInterExp<br>
 * 表主键：Id<br>
 */
public class SDInterExpSchema extends Schema {
	private String Id;

	private String ActionId;

	private String Experience;

	private String ExpExplain;

	private String EveryDayMax;

	private String EveryMonthMax;

	private String EveryYearMax;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Operator;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ActionId", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("Experience", DataColumn.STRING, 2, 10 , 0 , true , false),
		new SchemaColumn("ExpExplain", DataColumn.STRING, 3, 50 , 0 , true , false),
		new SchemaColumn("EveryDayMax", DataColumn.STRING, 4, 10 , 0 , true , false),
		new SchemaColumn("EveryMonthMax", DataColumn.STRING, 5, 10 , 0 , true , false),
		new SchemaColumn("EveryYearMax", DataColumn.STRING, 6, 10 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 9, 10 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 10, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SDInterExp";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInterExp values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInterExp set Id=?,ActionId=?,Experience=?,ExpExplain=?,EveryDayMax=?,EveryMonthMax=?,EveryYearMax=?,Prop1=?,Prop2=?,Prop3=?,Operator=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDInterExp  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInterExp  where Id=?";

	public SDInterExpSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new SDInterExpSchema();
	}

	protected SchemaSet newSet(){
		return new SDInterExpSet();
	}

	public SDInterExpSet query() {
		return query(null, -1, -1);
	}

	public SDInterExpSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInterExpSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInterExpSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInterExpSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ActionId = (String)v;return;}
		if (i == 2){Experience = (String)v;return;}
		if (i == 3){ExpExplain = (String)v;return;}
		if (i == 4){EveryDayMax = (String)v;return;}
		if (i == 5){EveryMonthMax = (String)v;return;}
		if (i == 6){EveryYearMax = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){Prop3 = (String)v;return;}
		if (i == 10){Operator = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ActionId;}
		if (i == 2){return Experience;}
		if (i == 3){return ExpExplain;}
		if (i == 4){return EveryDayMax;}
		if (i == 5){return EveryMonthMax;}
		if (i == 6){return EveryYearMax;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		if (i == 9){return Prop3;}
		if (i == 10){return Operator;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ActionId的值，该字段的<br>
	* 字段名称 :动作id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getActionId() {
		return ActionId;
	}

	/**
	* 设置字段ActionId的值，该字段的<br>
	* 字段名称 :动作id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setActionId(String actionId) {
		this.ActionId = actionId;
    }

	/**
	* 获取字段Experience的值，该字段的<br>
	* 字段名称 :经验值<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExperience() {
		return Experience;
	}

	/**
	* 设置字段Experience的值，该字段的<br>
	* 字段名称 :经验值<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExperience(String experience) {
		this.Experience = experience;
    }

	/**
	* 获取字段ExpExplain的值，该字段的<br>
	* 字段名称 :经验说明<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExpExplain() {
		return ExpExplain;
	}

	/**
	* 设置字段ExpExplain的值，该字段的<br>
	* 字段名称 :经验说明<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExpExplain(String expExplain) {
		this.ExpExplain = expExplain;
    }

	/**
	* 获取字段EveryDayMax的值，该字段的<br>
	* 字段名称 :每日最多次数<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getEveryDayMax() {
		return EveryDayMax;
	}

	/**
	* 设置字段EveryDayMax的值，该字段的<br>
	* 字段名称 :每日最多次数<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setEveryDayMax(String everyDayMax) {
		this.EveryDayMax = everyDayMax;
    }

	/**
	* 获取字段EveryMonthMax的值，该字段的<br>
	* 字段名称 :每月最多次数<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getEveryMonthMax() {
		return EveryMonthMax;
	}

	/**
	* 设置字段EveryMonthMax的值，该字段的<br>
	* 字段名称 :每月最多次数<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setEveryMonthMax(String everyMonthMax) {
		this.EveryMonthMax = everyMonthMax;
    }

	/**
	* 获取字段EveryYearMax的值，该字段的<br>
	* 字段名称 :每年最多次数<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getEveryYearMax() {
		return EveryYearMax;
	}

	/**
	* 设置字段EveryYearMax的值，该字段的<br>
	* 字段名称 :每年最多次数<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setEveryYearMax(String everyYearMax) {
		this.EveryYearMax = everyYearMax;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Operator的值，该字段的<br>
	* 字段名称 :操作员<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperator() {
		return Operator;
	}

	/**
	* 设置字段Operator的值，该字段的<br>
	* 字段名称 :操作员<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperator(String operator) {
		this.Operator = operator;
    }

}