package com.sinosoft.schema;

import java.util.Date;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

/**
 * 表名称：积分记录表<br>
 * 表代码：SDIntCalendar<br>
 * 表主键：ID<br>
 */
public class SDIntCalendarSchema extends Schema {
	private String ID;

	private String MemberId;

	private String Integral;

	private String Source;

	private String Manner;

	private String CreateDate;

	private String CreateTime;

	private String Status;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Operator;

	private String Businessid;
	
	private String svaliDate;
	
	private String Description;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MemberId", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("Integral", DataColumn.STRING, 2, 20 , 0 , true , false),
		new SchemaColumn("Source", DataColumn.STRING, 3, 32 , 0 , true , false),
		new SchemaColumn("Manner", DataColumn.STRING, 4, 2 , 0 , true , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 5, 10 , 0 , true , false),
		new SchemaColumn("CreateTime", DataColumn.STRING, 6, 10 , 0 , true , false),
		new SchemaColumn("Status", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 10 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Businessid", DataColumn.STRING, 12, 32 , 0 , false , false),
		new SchemaColumn("svaliDate", DataColumn.STRING, 13, 0 , 0 , false , false),
		new SchemaColumn("Description", DataColumn.STRING, 14, 100 , 0 , false , false)
	};

	public static final String _TableCode = "SDIntCalendar";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDIntCalendar values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDIntCalendar set ID=?,MemberId=?,Integral=?,Source=?,Manner=?,CreateDate=?,CreateTime=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Operator=?,Businessid=?,svaliDate=?,Description=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDIntCalendar  where ID=?";

	protected static final String _FillAllSQL = "select * from SDIntCalendar  where ID=?";

	public SDIntCalendarSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new SDIntCalendarSchema();
	}

	protected SchemaSet newSet(){
		return new SDIntCalendarSet();
	}

	public SDIntCalendarSet query() {
		return query(null, -1, -1);
	}

	public SDIntCalendarSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDIntCalendarSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDIntCalendarSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDIntCalendarSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){MemberId = (String)v;return;}
		if (i == 2){Integral = (String)v;return;}
		if (i == 3){Source = (String)v;return;}
		if (i == 4){Manner = (String)v;return;}
		if (i == 5){CreateDate = (String)v;return;}
		if (i == 6){CreateTime = (String)v;return;}
		if (i == 7){Status = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){Prop3 = (String)v;return;}
		if (i == 11){Operator = (String)v;return;}
		if (i == 12){Businessid = (String)v;return;}
		if (i == 13){svaliDate = (String)v;return;}
		if (i == 14){Description = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return MemberId;}
		if (i == 2){return Integral;}
		if (i == 3){return Source;}
		if (i == 4){return Manner;}
		if (i == 5){return CreateDate;}
		if (i == 6){return CreateTime;}
		if (i == 7){return Status;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return Prop3;}
		if (i == 11){return Operator;}
		if (i == 12){return Businessid;}
		if (i == 13){return svaliDate;}
		if (i == 14){return Description;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段MemberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getMemberId() {
		return MemberId;
	}

	/**
	* 设置字段MemberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMemberId(String memberId) {
		this.MemberId = memberId;
    }

	/**
	* 获取字段Integral的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIntegral() {
		return Integral;
	}

	/**
	* 设置字段Integral的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIntegral(String integral) {
		this.Integral = integral;
    }

	/**
	* 获取字段Source的值，该字段的<br>
	* 字段名称 :积分来源/使用<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSource() {
		return Source;
	}

	/**
	* 设置字段Source的值，该字段的<br>
	* 字段名称 :积分来源/使用<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSource(String source) {
		this.Source = source;
    }

	/**
	* 获取字段Manner的值，该字段的<br>
	* 字段名称 :积分收入支出<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getManner() {
		return Manner;
	}

	/**
	* 设置字段Manner的值，该字段的<br>
	* 字段名称 :积分收入支出<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setManner(String manner) {
		this.Manner = manner;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :日期<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :日期<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段CreateTime的值，该字段的<br>
	* 字段名称 :时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCreateTime() {
		return CreateTime;
	}

	/**
	* 设置字段CreateTime的值，该字段的<br>
	* 字段名称 :时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreateTime(String createTime) {
		this.CreateTime = createTime;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :积分状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :积分状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
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

	/**
	* 获取字段Businessid的值，该字段的<br>
	* 字段名称 :业务号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBusinessid() {
		return Businessid;
	}

	/**
	* 设置字段Businessid的值，该字段的<br>
	* 字段名称 :业务号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBusinessid(String businessid) {
		this.Businessid = businessid;
    }
	
	/**
	* 获取字段svaliDate的值，该字段的<br>
	* 字段名称 :生效起期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsvaliDate() {
		return svaliDate;
	}

	/**
	* 设置字段svaliDate的值，该字段的<br>
	* 字段名称 :轨迹描述<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsvaliDate(String svaliDate) {
		this.svaliDate = svaliDate;
    }
	/**
	* 获取字段Description的值，该字段的<br>
	* 字段名称 :轨迹描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescription() {
		return Description;
	}
	/**
	* 设置字段Description的值，该字段的<br>
	* 字段名称 :轨迹描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescription(String description) {
		Description = description;
	}

	
}