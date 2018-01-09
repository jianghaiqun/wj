package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：横向接口动作表<br>
 * 表代码：SDInterAction<br>
 * 表主键：Id<br>
 */
public class SDInterActionSchema extends Schema {
	private String Id;

	private String ActionId;

	private String ActionName;

	private String isExperience;

	private String isIntegral;

	private String isEmail;

	private String isSMS;

	private String isLog;

	private String EmailType;

	private String SMSType;

	private String ActionType;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Operator;

	private String isAct;

	private String ActType;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ActionId", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("ActionName", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("isExperience", DataColumn.STRING, 3, 2 , 0 , true , false),
		new SchemaColumn("isIntegral", DataColumn.STRING, 4, 2 , 0 , true , false),
		new SchemaColumn("isEmail", DataColumn.STRING, 5, 2 , 0 , true , false),
		new SchemaColumn("isSMS", DataColumn.STRING, 6, 2 , 0 , true , false),
		new SchemaColumn("isLog", DataColumn.STRING, 7, 2 , 0 , true , false),
		new SchemaColumn("EmailType", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("SMSType", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("ActionType", DataColumn.STRING, 10, 20 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("isAct", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("ActType", DataColumn.STRING, 16, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SDInterAction";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInterAction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInterAction set Id=?,ActionId=?,ActionName=?,isExperience=?,isIntegral=?,isEmail=?,isSMS=?,isLog=?,EmailType=?,SMSType=?,ActionType=?,Prop1=?,Prop2=?,Prop3=?,Operator=?,isAct=?,ActType=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDInterAction  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInterAction  where Id=?";

	public SDInterActionSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new SDInterActionSchema();
	}

	protected SchemaSet newSet(){
		return new SDInterActionSet();
	}

	public SDInterActionSet query() {
		return query(null, -1, -1);
	}

	public SDInterActionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInterActionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInterActionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInterActionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ActionId = (String)v;return;}
		if (i == 2){ActionName = (String)v;return;}
		if (i == 3){isExperience = (String)v;return;}
		if (i == 4){isIntegral = (String)v;return;}
		if (i == 5){isEmail = (String)v;return;}
		if (i == 6){isSMS = (String)v;return;}
		if (i == 7){isLog = (String)v;return;}
		if (i == 8){EmailType = (String)v;return;}
		if (i == 9){SMSType = (String)v;return;}
		if (i == 10){ActionType = (String)v;return;}
		if (i == 11){Prop1 = (String)v;return;}
		if (i == 12){Prop2 = (String)v;return;}
		if (i == 13){Prop3 = (String)v;return;}
		if (i == 14){Operator = (String)v;return;}
		if (i == 15){isAct = (String)v;return;}
		if (i == 16){ActType = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ActionId;}
		if (i == 2){return ActionName;}
		if (i == 3){return isExperience;}
		if (i == 4){return isIntegral;}
		if (i == 5){return isEmail;}
		if (i == 6){return isSMS;}
		if (i == 7){return isLog;}
		if (i == 8){return EmailType;}
		if (i == 9){return SMSType;}
		if (i == 10){return ActionType;}
		if (i == 11){return Prop1;}
		if (i == 12){return Prop2;}
		if (i == 13){return Prop3;}
		if (i == 14){return Operator;}
		if (i == 15){return isAct;}
		if (i == 16){return ActType;}
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
	* 字段名称 :ActionId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getActionId() {
		return ActionId;
	}

	/**
	* 设置字段ActionId的值，该字段的<br>
	* 字段名称 :ActionId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setActionId(String actionId) {
		this.ActionId = actionId;
    }

	/**
	* 获取字段ActionName的值，该字段的<br>
	* 字段名称 :动作描述<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getActionName() {
		return ActionName;
	}

	/**
	* 设置字段ActionName的值，该字段的<br>
	* 字段名称 :动作描述<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setActionName(String actionName) {
		this.ActionName = actionName;
    }

	/**
	* 获取字段isExperience的值，该字段的<br>
	* 字段名称 :是否经验<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0否 1是<br>
	*/
	public String getisExperience() {
		return isExperience;
	}

	/**
	* 设置字段isExperience的值，该字段的<br>
	* 字段名称 :是否经验<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0否 1是<br>
	*/
	public void setisExperience(String isExperience) {
		this.isExperience = isExperience;
    }

	/**
	* 获取字段isIntegral的值，该字段的<br>
	* 字段名称 :是否积分<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getisIntegral() {
		return isIntegral;
	}

	/**
	* 设置字段isIntegral的值，该字段的<br>
	* 字段名称 :是否积分<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setisIntegral(String isIntegral) {
		this.isIntegral = isIntegral;
    }

	/**
	* 获取字段isEmail的值，该字段的<br>
	* 字段名称 :是否邮件<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getisEmail() {
		return isEmail;
	}

	/**
	* 设置字段isEmail的值，该字段的<br>
	* 字段名称 :是否邮件<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setisEmail(String isEmail) {
		this.isEmail = isEmail;
    }

	/**
	* 获取字段isSMS的值，该字段的<br>
	* 字段名称 :是否短信<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getisSMS() {
		return isSMS;
	}

	/**
	* 设置字段isSMS的值，该字段的<br>
	* 字段名称 :是否短信<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setisSMS(String isSMS) {
		this.isSMS = isSMS;
    }

	/**
	* 获取字段isLog的值，该字段的<br>
	* 字段名称 :是否日志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getisLog() {
		return isLog;
	}

	/**
	* 设置字段isLog的值，该字段的<br>
	* 字段名称 :是否日志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setisLog(String isLog) {
		this.isLog = isLog;
    }

	/**
	* 获取字段EmailType的值，该字段的<br>
	* 字段名称 :邮件模版类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmailType() {
		return EmailType;
	}

	/**
	* 设置字段EmailType的值，该字段的<br>
	* 字段名称 :邮件模版类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmailType(String emailType) {
		this.EmailType = emailType;
    }

	/**
	* 获取字段SMSType的值，该字段的<br>
	* 字段名称 :短信模版类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSMSType() {
		return SMSType;
	}

	/**
	* 设置字段SMSType的值，该字段的<br>
	* 字段名称 :短信模版类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSMSType(String sMSType) {
		this.SMSType = sMSType;
    }

	/**
	* 获取字段ActionType的值，该字段的<br>
	* 字段名称 :动作类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getActionType() {
		return ActionType;
	}

	/**
	* 设置字段ActionType的值，该字段的<br>
	* 字段名称 :动作类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setActionType(String actionType) {
		this.ActionType = actionType;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(2)<br>
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
	* 获取字段isAct的值，该字段的<br>
	* 字段名称 :是否抽奖活动<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisAct() {
		return isAct;
	}

	/**
	* 设置字段isAct的值，该字段的<br>
	* 字段名称 :是否抽奖活动<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisAct(String isAct) {
		this.isAct = isAct;
    }

	/**
	* 获取字段ActType的值，该字段的<br>
	* 字段名称 :抽奖活动类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getActType() {
		return ActType;
	}

	/**
	* 设置字段ActType的值，该字段的<br>
	* 字段名称 :抽奖活动类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActType(String actType) {
		this.ActType = actType;
    }

}