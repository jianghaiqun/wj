package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.math.BigDecimal;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 表名称：砍价活动轨迹表<br>
 * 表代码：ActivityRecord<br>
 * 表主键：activityId, initiatorOpenid, participantOpenid<br>
 */
public class ActivityRecordSchema extends Schema {
	private String serialNo;

	private String activityId;

	private String initiatorOpenid;

	private String participantOpenid;

	private String participantWxName;

	private String participantWxHead;

	private BigDecimal Amount;

	private Date executeDate;

	private String ace;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("serialNo", DataColumn.STRING, 0, 32 , 0 , false , false),
		new SchemaColumn("activityId", DataColumn.STRING, 1, 20 , 0 , true , true),
		new SchemaColumn("initiatorOpenid", DataColumn.STRING, 2, 50 , 0 , true , true),
		new SchemaColumn("participantOpenid", DataColumn.STRING, 3, 50 , 0 , true , true),
		new SchemaColumn("participantWxName", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("participantWxHead", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("Amount", DataColumn.BIGDECIMAL, 6, 10 , 0 , false , false),
		new SchemaColumn("executeDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("ace", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 12, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ActivityRecord";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ActivityRecord values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ActivityRecord set serialNo=?,activityId=?,initiatorOpenid=?,participantOpenid=?,participantWxName=?,participantWxHead=?,Amount=?,executeDate=?,ace=?,prop1=?,prop2=?,prop3=?,prop4=? where activityId=? and initiatorOpenid=? and participantOpenid=?";

	protected static final String _DeleteSQL = "delete from ActivityRecord  where activityId=? and initiatorOpenid=? and participantOpenid=?";

	protected static final String _FillAllSQL = "select * from ActivityRecord  where activityId=? and initiatorOpenid=? and participantOpenid=?";

	public ActivityRecordSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new ActivityRecordSchema();
	}

	protected SchemaSet newSet(){
		return new ActivityRecordSet();
	}

	public ActivityRecordSet query() {
		return query(null, -1, -1);
	}

	public ActivityRecordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ActivityRecordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ActivityRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ActivityRecordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){serialNo = (String)v;return;}
		if (i == 1){activityId = (String)v;return;}
		if (i == 2){initiatorOpenid = (String)v;return;}
		if (i == 3){participantOpenid = (String)v;return;}
		if (i == 4){participantWxName = (String)v;return;}
		if (i == 5){participantWxHead = (String)v;return;}
		if (i == 6){if(v==null){Amount = null;}else{Amount =  ((BigDecimal)v) ;}return;}
		if (i == 7){executeDate = (Date)v;return;}
		if (i == 8){ace = (String)v;return;}
		if (i == 9){prop1 = (String)v;return;}
		if (i == 10){prop2 = (String)v;return;}
		if (i == 11){prop3 = (String)v;return;}
		if (i == 12){prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return serialNo;}
		if (i == 1){return activityId;}
		if (i == 2){return initiatorOpenid;}
		if (i == 3){return participantOpenid;}
		if (i == 4){return participantWxName;}
		if (i == 5){return participantWxHead;}
		if (i == 6){return Amount;}
		if (i == 7){return executeDate;}
		if (i == 8){return ace;}
		if (i == 9){return prop1;}
		if (i == 10){return prop2;}
		if (i == 11){return prop3;}
		if (i == 12){return prop4;}
		return null;
	}

	/**
	* 获取字段serialNo的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getserialNo() {
		return serialNo;
	}

	/**
	* 设置字段serialNo的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setserialNo(String serialNo) {
		this.serialNo = serialNo;
    }

	/**
	* 获取字段activityId的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getactivityId() {
		return activityId;
	}

	/**
	* 设置字段activityId的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setactivityId(String activityId) {
		this.activityId = activityId;
    }

	/**
	* 获取字段initiatorOpenid的值，该字段的<br>
	* 字段名称 :发起者openid<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getinitiatorOpenid() {
		return initiatorOpenid;
	}

	/**
	* 设置字段initiatorOpenid的值，该字段的<br>
	* 字段名称 :发起者openid<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setinitiatorOpenid(String initiatorOpenid) {
		this.initiatorOpenid = initiatorOpenid;
    }

	/**
	* 获取字段participantOpenid的值，该字段的<br>
	* 字段名称 :参与者openid<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getparticipantOpenid() {
		return participantOpenid;
	}

	/**
	* 设置字段participantOpenid的值，该字段的<br>
	* 字段名称 :参与者openid<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setparticipantOpenid(String participantOpenid) {
		this.participantOpenid = participantOpenid;
    }

	/**
	* 获取字段participantWxName的值，该字段的<br>
	* 字段名称 :参与者昵称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getparticipantWxName() {
		return participantWxName;
	}

	/**
	* 设置字段participantWxName的值，该字段的<br>
	* 字段名称 :参与者昵称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setparticipantWxName(String participantWxName) {
		this.participantWxName = participantWxName;
    }

	/**
	* 获取字段participantWxHead的值，该字段的<br>
	* 字段名称 :参与者头像<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getparticipantWxHead() {
		return participantWxHead;
	}

	/**
	* 设置字段participantWxHead的值，该字段的<br>
	* 字段名称 :参与者头像<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setparticipantWxHead(String participantWxHead) {
		this.participantWxHead = participantWxHead;
    }

	/**
	* 获取字段Amount的值，该字段的<br>
	* 字段名称 :砍价金额<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getAmount() {
		return Amount;
	}

	/**
	* 设置字段Amount的值，该字段的<br>
	* 字段名称 :砍价金额<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAmount(BigDecimal amount) {
		this.Amount = amount;
    }

	/**
	* 获取字段executeDate的值，该字段的<br>
	* 字段名称 :砍价时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getexecuteDate() {
		return executeDate;
	}

	/**
	* 设置字段executeDate的值，该字段的<br>
	* 字段名称 :砍价时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setexecuteDate(Date executeDate) {
		this.executeDate = executeDate;
    }

	/**
	* 获取字段ace的值，该字段的<br>
	* 字段名称 :活动成就<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getace() {
		return ace;
	}

	/**
	* 设置字段ace的值，该字段的<br>
	* 字段名称 :活动成就<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setace(String ace) {
		this.ace = ace;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

}