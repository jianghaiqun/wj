package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：微信警告信息<br>
 * 表代码：WxWornInfo<br>
 * 表主键：ID<br>
 */
public class WxWornInfoSchema extends Schema {
	private String ID;

	private String AppSignature;

	private String AppId;

	private String ErrorType;

	private String Description;

	private String AlarmContent;

	private String TimeStamp;

	private String SignMethod;

	private String Prop1;

	private String Prop2;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("AppSignature", DataColumn.STRING, 1, 200 , 0 , false , false),
		new SchemaColumn("AppId", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("ErrorType", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("Description", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("AlarmContent", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("TimeStamp", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("SignMethod", DataColumn.STRING, 7, 500 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 11, 0 , 0 , false , false)
	};

	public static final String _TableCode = "WxWornInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WxWornInfo values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WxWornInfo set ID=?,AppSignature=?,AppId=?,ErrorType=?,Description=?,AlarmContent=?,TimeStamp=?,SignMethod=?,Prop1=?,Prop2=?,CreateDate=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from WxWornInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from WxWornInfo  where ID=?";

	public WxWornInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new WxWornInfoSchema();
	}

	protected SchemaSet newSet(){
		return new WxWornInfoSet();
	}

	public WxWornInfoSet query() {
		return query(null, -1, -1);
	}

	public WxWornInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WxWornInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WxWornInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WxWornInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){AppSignature = (String)v;return;}
		if (i == 2){AppId = (String)v;return;}
		if (i == 3){ErrorType = (String)v;return;}
		if (i == 4){Description = (String)v;return;}
		if (i == 5){AlarmContent = (String)v;return;}
		if (i == 6){TimeStamp = (String)v;return;}
		if (i == 7){SignMethod = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){CreateDate = (Date)v;return;}
		if (i == 11){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AppSignature;}
		if (i == 2){return AppId;}
		if (i == 3){return ErrorType;}
		if (i == 4){return Description;}
		if (i == 5){return AlarmContent;}
		if (i == 6){return TimeStamp;}
		if (i == 7){return SignMethod;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return CreateDate;}
		if (i == 11){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段AppSignature的值，该字段的<br>
	* 字段名称 :唯一标识<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAppSignature() {
		return AppSignature;
	}

	/**
	* 设置字段AppSignature的值，该字段的<br>
	* 字段名称 :唯一标识<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAppSignature(String appSignature) {
		this.AppSignature = appSignature;
    }

	/**
	* 获取字段AppId的值，该字段的<br>
	* 字段名称 :商户标示<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAppId() {
		return AppId;
	}

	/**
	* 设置字段AppId的值，该字段的<br>
	* 字段名称 :商户标示<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAppId(String appId) {
		this.AppId = appId;
    }

	/**
	* 获取字段ErrorType的值，该字段的<br>
	* 字段名称 :警告类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getErrorType() {
		return ErrorType;
	}

	/**
	* 设置字段ErrorType的值，该字段的<br>
	* 字段名称 :警告类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setErrorType(String errorType) {
		this.ErrorType = errorType;
    }

	/**
	* 获取字段Description的值，该字段的<br>
	* 字段名称 :错误描述<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescription() {
		return Description;
	}

	/**
	* 设置字段Description的值，该字段的<br>
	* 字段名称 :错误描述<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescription(String description) {
		this.Description = description;
    }

	/**
	* 获取字段AlarmContent的值，该字段的<br>
	* 字段名称 :错误详情<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAlarmContent() {
		return AlarmContent;
	}

	/**
	* 设置字段AlarmContent的值，该字段的<br>
	* 字段名称 :错误详情<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAlarmContent(String alarmContent) {
		this.AlarmContent = alarmContent;
    }

	/**
	* 获取字段TimeStamp的值，该字段的<br>
	* 字段名称 :时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTimeStamp() {
		return TimeStamp;
	}

	/**
	* 设置字段TimeStamp的值，该字段的<br>
	* 字段名称 :时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTimeStamp(String timeStamp) {
		this.TimeStamp = timeStamp;
    }

	/**
	* 获取字段SignMethod的值，该字段的<br>
	* 字段名称 :签名方法<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSignMethod() {
		return SignMethod;
	}

	/**
	* 设置字段SignMethod的值，该字段的<br>
	* 字段名称 :签名方法<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSignMethod(String signMethod) {
		this.SignMethod = signMethod;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}