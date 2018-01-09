package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：短信2<br>
 * 表代码：LIMessageSent<br>
 * 表主键：MessageId<br>
 */
public class LIMessageSentSchema extends Schema {
	private String MessageId;

	private String ServiceCode;

	private String ComCode;

	private String SendData;

	private String MobileNum;

	private String CustomerName;

	private String Operator;

	private String ServiceBussNo;

	private Date MakeDate;

	private String MakeTime;

	private Date ModifyDate;

	private String ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("MessageId", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("ServiceCode", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("ComCode", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("SendData", DataColumn.STRING, 3, 2500 , 0 , false , false),
		new SchemaColumn("MobileNum", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("CustomerName", DataColumn.STRING, 5, 80 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 6, 80 , 0 , true , false),
		new SchemaColumn("ServiceBussNo", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 8, 0 , 0 , true , false),
		new SchemaColumn("MakeTime", DataColumn.STRING, 9, 8 , 0 , true , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.STRING, 11, 8 , 0 , true , false)
	};

	public static final String _TableCode = "LIMessageSent";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LIMessageSent values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LIMessageSent set MessageId=?,ServiceCode=?,ComCode=?,SendData=?,MobileNum=?,CustomerName=?,Operator=?,ServiceBussNo=?,MakeDate=?,MakeTime=?,ModifyDate=?,ModifyTime=? where MessageId=?";

	protected static final String _DeleteSQL = "delete from LIMessageSent  where MessageId=?";

	protected static final String _FillAllSQL = "select * from LIMessageSent  where MessageId=?";

	public LIMessageSentSchema(){
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
		return new LIMessageSentSchema();
	}

	protected SchemaSet newSet(){
		return new LIMessageSentSet();
	}

	public LIMessageSentSet query() {
		return query(null, -1, -1);
	}

	public LIMessageSentSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LIMessageSentSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LIMessageSentSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LIMessageSentSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){MessageId = (String)v;return;}
		if (i == 1){ServiceCode = (String)v;return;}
		if (i == 2){ComCode = (String)v;return;}
		if (i == 3){SendData = (String)v;return;}
		if (i == 4){MobileNum = (String)v;return;}
		if (i == 5){CustomerName = (String)v;return;}
		if (i == 6){Operator = (String)v;return;}
		if (i == 7){ServiceBussNo = (String)v;return;}
		if (i == 8){MakeDate = (Date)v;return;}
		if (i == 9){MakeTime = (String)v;return;}
		if (i == 10){ModifyDate = (Date)v;return;}
		if (i == 11){ModifyTime = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return MessageId;}
		if (i == 1){return ServiceCode;}
		if (i == 2){return ComCode;}
		if (i == 3){return SendData;}
		if (i == 4){return MobileNum;}
		if (i == 5){return CustomerName;}
		if (i == 6){return Operator;}
		if (i == 7){return ServiceBussNo;}
		if (i == 8){return MakeDate;}
		if (i == 9){return MakeTime;}
		if (i == 10){return ModifyDate;}
		if (i == 11){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段MessageId的值，该字段的<br>
	* 字段名称 :MessageId<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getMessageId() {
		return MessageId;
	}

	/**
	* 设置字段MessageId的值，该字段的<br>
	* 字段名称 :MessageId<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setMessageId(String messageId) {
		this.MessageId = messageId;
    }

	/**
	* 获取字段ServiceCode的值，该字段的<br>
	* 字段名称 :ServiceCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceCode() {
		return ServiceCode;
	}

	/**
	* 设置字段ServiceCode的值，该字段的<br>
	* 字段名称 :ServiceCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceCode(String serviceCode) {
		this.ServiceCode = serviceCode;
    }

	/**
	* 获取字段ComCode的值，该字段的<br>
	* 字段名称 :ComCode<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getComCode() {
		return ComCode;
	}

	/**
	* 设置字段ComCode的值，该字段的<br>
	* 字段名称 :ComCode<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setComCode(String comCode) {
		this.ComCode = comCode;
    }

	/**
	* 获取字段SendData的值，该字段的<br>
	* 字段名称 :SendData<br>
	* 数据类型 :varchar(2500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendData() {
		return SendData;
	}

	/**
	* 设置字段SendData的值，该字段的<br>
	* 字段名称 :SendData<br>
	* 数据类型 :varchar(2500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendData(String sendData) {
		this.SendData = sendData;
    }

	/**
	* 获取字段MobileNum的值，该字段的<br>
	* 字段名称 :MobileNum<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMobileNum() {
		return MobileNum;
	}

	/**
	* 设置字段MobileNum的值，该字段的<br>
	* 字段名称 :MobileNum<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMobileNum(String mobileNum) {
		this.MobileNum = mobileNum;
    }

	/**
	* 获取字段CustomerName的值，该字段的<br>
	* 字段名称 :CustomerName<br>
	* 数据类型 :varchar(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCustomerName() {
		return CustomerName;
	}

	/**
	* 设置字段CustomerName的值，该字段的<br>
	* 字段名称 :CustomerName<br>
	* 数据类型 :varchar(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCustomerName(String customerName) {
		this.CustomerName = customerName;
    }

	/**
	* 获取字段Operator的值，该字段的<br>
	* 字段名称 :Operator<br>
	* 数据类型 :varchar(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getOperator() {
		return Operator;
	}

	/**
	* 设置字段Operator的值，该字段的<br>
	* 字段名称 :Operator<br>
	* 数据类型 :varchar(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOperator(String operator) {
		this.Operator = operator;
    }

	/**
	* 获取字段ServiceBussNo的值，该字段的<br>
	* 字段名称 :ServiceBussNo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceBussNo() {
		return ServiceBussNo;
	}

	/**
	* 设置字段ServiceBussNo的值，该字段的<br>
	* 字段名称 :ServiceBussNo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceBussNo(String serviceBussNo) {
		this.ServiceBussNo = serviceBussNo;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
    }

	/**
	* 获取字段MakeTime的值，该字段的<br>
	* 字段名称 :MakeTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getMakeTime() {
		return MakeTime;
	}

	/**
	* 设置字段MakeTime的值，该字段的<br>
	* 字段名称 :MakeTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMakeTime(String makeTime) {
		this.MakeTime = makeTime;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setModifyTime(String modifyTime) {
		this.ModifyTime = modifyTime;
    }

}