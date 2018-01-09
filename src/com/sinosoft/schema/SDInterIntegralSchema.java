package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：横向接口积分表<br>
 * 表代码：SDInterIntegral<br>
 * 表主键：Id<br>
 */
public class SDInterIntegralSchema extends Schema {
	private String Id;

	private String ActionId;

	private String Integral;

	private String IntExplain;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Operator;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ActionId", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("Integral", DataColumn.STRING, 2, 2 , 0 , true , false),
		new SchemaColumn("IntExplain", DataColumn.STRING, 3, 2 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 7, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SDInterIntegral";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInterIntegral values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInterIntegral set Id=?,ActionId=?,Integral=?,IntExplain=?,Prop1=?,Prop2=?,Prop3=?,Operator=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDInterIntegral  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInterIntegral  where Id=?";

	public SDInterIntegralSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new SDInterIntegralSchema();
	}

	protected SchemaSet newSet(){
		return new SDInterIntegralSet();
	}

	public SDInterIntegralSet query() {
		return query(null, -1, -1);
	}

	public SDInterIntegralSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInterIntegralSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInterIntegralSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInterIntegralSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ActionId = (String)v;return;}
		if (i == 2){Integral = (String)v;return;}
		if (i == 3){IntExplain = (String)v;return;}
		if (i == 4){Prop1 = (String)v;return;}
		if (i == 5){Prop2 = (String)v;return;}
		if (i == 6){Prop3 = (String)v;return;}
		if (i == 7){Operator = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ActionId;}
		if (i == 2){return Integral;}
		if (i == 3){return IntExplain;}
		if (i == 4){return Prop1;}
		if (i == 5){return Prop2;}
		if (i == 6){return Prop3;}
		if (i == 7){return Operator;}
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
	* 获取字段Integral的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIntegral() {
		return Integral;
	}

	/**
	* 设置字段Integral的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIntegral(String integral) {
		this.Integral = integral;
    }

	/**
	* 获取字段IntExplain的值，该字段的<br>
	* 字段名称 :积分说明<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIntExplain() {
		return IntExplain;
	}

	/**
	* 设置字段IntExplain的值，该字段的<br>
	* 字段名称 :积分说明<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIntExplain(String intExplain) {
		this.IntExplain = intExplain;
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