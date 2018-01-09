package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：短信1<br>
 * 表代码：LIMessageInteract<br>
 * 表主键：SystemCode, ServiceCode, MessageId<br>
 */
public class LIMessageInteractSchema extends Schema {
	private String SystemCode;

	private String ServiceCode;

	private String MessageId;

	private String ServiceBussNo;

	private Date MakeDate;

	private String MakeTime;

	private String MobileNum;

	private String CustomerName;

	private String MessTopic;

	private String SendData;

	private String DataType;

	private String SendWay;

	private Date FixedDate;

	private String FixedTime;

	private String UnitCode;

	private String SendCode;

	private String DealOrder;

	private String CheckFlag;

	private String AnswerMatch;

	private String ReplyFlag;

	private String Temp1;

	private String Temp2;

	private String Temp3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("SystemCode", DataColumn.STRING, 0, 3 , 0 , true , true),
		new SchemaColumn("ServiceCode", DataColumn.STRING, 1, 10 , 0 , true , true),
		new SchemaColumn("MessageId", DataColumn.STRING, 2, 20 , 0 , true , true),
		new SchemaColumn("ServiceBussNo", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 4, 0 , 0 , true , false),
		new SchemaColumn("MakeTime", DataColumn.STRING, 5, 8 , 0 , true , false),
		new SchemaColumn("MobileNum", DataColumn.STRING, 6, 20 , 0 , true , false),
		new SchemaColumn("CustomerName", DataColumn.STRING, 7, 30 , 0 , false , false),
		new SchemaColumn("MessTopic", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("SendData", DataColumn.STRING, 9, 500 , 0 , true , false),
		new SchemaColumn("DataType", DataColumn.STRING, 10, 1 , 0 , true , false),
		new SchemaColumn("SendWay", DataColumn.STRING, 11, 1 , 0 , false , false),
		new SchemaColumn("FixedDate", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("FixedTime", DataColumn.STRING, 13, 8 , 0 , false , false),
		new SchemaColumn("UnitCode", DataColumn.STRING, 14, 20 , 0 , true , false),
		new SchemaColumn("SendCode", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("DealOrder", DataColumn.STRING, 16, 1 , 0 , false , false),
		new SchemaColumn("CheckFlag", DataColumn.STRING, 17, 1 , 0 , true , false),
		new SchemaColumn("AnswerMatch", DataColumn.STRING, 18, 1 , 0 , false , false),
		new SchemaColumn("ReplyFlag", DataColumn.STRING, 19, 1 , 0 , true , false),
		new SchemaColumn("Temp1", DataColumn.STRING, 20, 20 , 0 , false , false),
		new SchemaColumn("Temp2", DataColumn.STRING, 21, 20 , 0 , false , false),
		new SchemaColumn("Temp3", DataColumn.STRING, 22, 20 , 0 , false , false)
	};

	public static final String _TableCode = "LIMessageInteract";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LIMessageInteract values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LIMessageInteract set SystemCode=?,ServiceCode=?,MessageId=?,ServiceBussNo=?,MakeDate=?,MakeTime=?,MobileNum=?,CustomerName=?,MessTopic=?,SendData=?,DataType=?,SendWay=?,FixedDate=?,FixedTime=?,UnitCode=?,SendCode=?,DealOrder=?,CheckFlag=?,AnswerMatch=?,ReplyFlag=?,Temp1=?,Temp2=?,Temp3=? where SystemCode=? and ServiceCode=? and MessageId=?";

	protected static final String _DeleteSQL = "delete from LIMessageInteract  where SystemCode=? and ServiceCode=? and MessageId=?";

	protected static final String _FillAllSQL = "select * from LIMessageInteract  where SystemCode=? and ServiceCode=? and MessageId=?";

	public LIMessageInteractSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[23];
	}

	protected Schema newInstance(){
		return new LIMessageInteractSchema();
	}

	protected SchemaSet newSet(){
		return new LIMessageInteractSet();
	}

	public LIMessageInteractSet query() {
		return query(null, -1, -1);
	}

	public LIMessageInteractSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LIMessageInteractSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LIMessageInteractSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LIMessageInteractSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){SystemCode = (String)v;return;}
		if (i == 1){ServiceCode = (String)v;return;}
		if (i == 2){MessageId = (String)v;return;}
		if (i == 3){ServiceBussNo = (String)v;return;}
		if (i == 4){MakeDate = (Date)v;return;}
		if (i == 5){MakeTime = (String)v;return;}
		if (i == 6){MobileNum = (String)v;return;}
		if (i == 7){CustomerName = (String)v;return;}
		if (i == 8){MessTopic = (String)v;return;}
		if (i == 9){SendData = (String)v;return;}
		if (i == 10){DataType = (String)v;return;}
		if (i == 11){SendWay = (String)v;return;}
		if (i == 12){FixedDate = (Date)v;return;}
		if (i == 13){FixedTime = (String)v;return;}
		if (i == 14){UnitCode = (String)v;return;}
		if (i == 15){SendCode = (String)v;return;}
		if (i == 16){DealOrder = (String)v;return;}
		if (i == 17){CheckFlag = (String)v;return;}
		if (i == 18){AnswerMatch = (String)v;return;}
		if (i == 19){ReplyFlag = (String)v;return;}
		if (i == 20){Temp1 = (String)v;return;}
		if (i == 21){Temp2 = (String)v;return;}
		if (i == 22){Temp3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return SystemCode;}
		if (i == 1){return ServiceCode;}
		if (i == 2){return MessageId;}
		if (i == 3){return ServiceBussNo;}
		if (i == 4){return MakeDate;}
		if (i == 5){return MakeTime;}
		if (i == 6){return MobileNum;}
		if (i == 7){return CustomerName;}
		if (i == 8){return MessTopic;}
		if (i == 9){return SendData;}
		if (i == 10){return DataType;}
		if (i == 11){return SendWay;}
		if (i == 12){return FixedDate;}
		if (i == 13){return FixedTime;}
		if (i == 14){return UnitCode;}
		if (i == 15){return SendCode;}
		if (i == 16){return DealOrder;}
		if (i == 17){return CheckFlag;}
		if (i == 18){return AnswerMatch;}
		if (i == 19){return ReplyFlag;}
		if (i == 20){return Temp1;}
		if (i == 21){return Temp2;}
		if (i == 22){return Temp3;}
		return null;
	}

	/**
	* 获取字段SystemCode的值，该字段的<br>
	* 字段名称 :SYSTEMCODE<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getSystemCode() {
		return SystemCode;
	}

	/**
	* 设置字段SystemCode的值，该字段的<br>
	* 字段名称 :SYSTEMCODE<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setSystemCode(String systemCode) {
		this.SystemCode = systemCode;
    }

	/**
	* 获取字段ServiceCode的值，该字段的<br>
	* 字段名称 :ServiceCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getServiceCode() {
		return ServiceCode;
	}

	/**
	* 设置字段ServiceCode的值，该字段的<br>
	* 字段名称 :ServiceCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setServiceCode(String serviceCode) {
		this.ServiceCode = serviceCode;
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
	* 获取字段MobileNum的值，该字段的<br>
	* 字段名称 :MobileNum<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getMobileNum() {
		return MobileNum;
	}

	/**
	* 设置字段MobileNum的值，该字段的<br>
	* 字段名称 :MobileNum<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMobileNum(String mobileNum) {
		this.MobileNum = mobileNum;
    }

	/**
	* 获取字段CustomerName的值，该字段的<br>
	* 字段名称 :CustomerName<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCustomerName() {
		return CustomerName;
	}

	/**
	* 设置字段CustomerName的值，该字段的<br>
	* 字段名称 :CustomerName<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCustomerName(String customerName) {
		this.CustomerName = customerName;
    }

	/**
	* 获取字段MessTopic的值，该字段的<br>
	* 字段名称 :MessTopic<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMessTopic() {
		return MessTopic;
	}

	/**
	* 设置字段MessTopic的值，该字段的<br>
	* 字段名称 :MessTopic<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMessTopic(String messTopic) {
		this.MessTopic = messTopic;
    }

	/**
	* 获取字段SendData的值，该字段的<br>
	* 字段名称 :SendData<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSendData() {
		return SendData;
	}

	/**
	* 设置字段SendData的值，该字段的<br>
	* 字段名称 :SendData<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSendData(String sendData) {
		this.SendData = sendData;
    }

	/**
	* 获取字段DataType的值，该字段的<br>
	* 字段名称 :DataType<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getDataType() {
		return DataType;
	}

	/**
	* 设置字段DataType的值，该字段的<br>
	* 字段名称 :DataType<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDataType(String dataType) {
		this.DataType = dataType;
    }

	/**
	* 获取字段SendWay的值，该字段的<br>
	* 字段名称 :SendWay<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendWay() {
		return SendWay;
	}

	/**
	* 设置字段SendWay的值，该字段的<br>
	* 字段名称 :SendWay<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendWay(String sendWay) {
		this.SendWay = sendWay;
    }

	/**
	* 获取字段FixedDate的值，该字段的<br>
	* 字段名称 :FixedDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getFixedDate() {
		return FixedDate;
	}

	/**
	* 设置字段FixedDate的值，该字段的<br>
	* 字段名称 :FixedDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFixedDate(Date fixedDate) {
		this.FixedDate = fixedDate;
    }

	/**
	* 获取字段FixedTime的值，该字段的<br>
	* 字段名称 :FixedTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFixedTime() {
		return FixedTime;
	}

	/**
	* 设置字段FixedTime的值，该字段的<br>
	* 字段名称 :FixedTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFixedTime(String fixedTime) {
		this.FixedTime = fixedTime;
    }

	/**
	* 获取字段UnitCode的值，该字段的<br>
	* 字段名称 :UnitCode<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getUnitCode() {
		return UnitCode;
	}

	/**
	* 设置字段UnitCode的值，该字段的<br>
	* 字段名称 :UnitCode<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setUnitCode(String unitCode) {
		this.UnitCode = unitCode;
    }

	/**
	* 获取字段SendCode的值，该字段的<br>
	* 字段名称 :SendCode<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendCode() {
		return SendCode;
	}

	/**
	* 设置字段SendCode的值，该字段的<br>
	* 字段名称 :SendCode<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendCode(String sendCode) {
		this.SendCode = sendCode;
    }

	/**
	* 获取字段DealOrder的值，该字段的<br>
	* 字段名称 :DealOrder<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDealOrder() {
		return DealOrder;
	}

	/**
	* 设置字段DealOrder的值，该字段的<br>
	* 字段名称 :DealOrder<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDealOrder(String dealOrder) {
		this.DealOrder = dealOrder;
    }

	/**
	* 获取字段CheckFlag的值，该字段的<br>
	* 字段名称 :CheckFlag<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCheckFlag() {
		return CheckFlag;
	}

	/**
	* 设置字段CheckFlag的值，该字段的<br>
	* 字段名称 :CheckFlag<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCheckFlag(String checkFlag) {
		this.CheckFlag = checkFlag;
    }

	/**
	* 获取字段AnswerMatch的值，该字段的<br>
	* 字段名称 :AnswerMatch<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAnswerMatch() {
		return AnswerMatch;
	}

	/**
	* 设置字段AnswerMatch的值，该字段的<br>
	* 字段名称 :AnswerMatch<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAnswerMatch(String answerMatch) {
		this.AnswerMatch = answerMatch;
    }

	/**
	* 获取字段ReplyFlag的值，该字段的<br>
	* 字段名称 :ReplyFlag<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getReplyFlag() {
		return ReplyFlag;
	}

	/**
	* 设置字段ReplyFlag的值，该字段的<br>
	* 字段名称 :ReplyFlag<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setReplyFlag(String replyFlag) {
		this.ReplyFlag = replyFlag;
    }

	/**
	* 获取字段Temp1的值，该字段的<br>
	* 字段名称 :Temp1<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTemp1() {
		return Temp1;
	}

	/**
	* 设置字段Temp1的值，该字段的<br>
	* 字段名称 :Temp1<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTemp1(String temp1) {
		this.Temp1 = temp1;
    }

	/**
	* 获取字段Temp2的值，该字段的<br>
	* 字段名称 :Temp2<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTemp2() {
		return Temp2;
	}

	/**
	* 设置字段Temp2的值，该字段的<br>
	* 字段名称 :Temp2<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTemp2(String temp2) {
		this.Temp2 = temp2;
    }

	/**
	* 获取字段Temp3的值，该字段的<br>
	* 字段名称 :Temp3<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTemp3() {
		return Temp3;
	}

	/**
	* 设置字段Temp3的值，该字段的<br>
	* 字段名称 :Temp3<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTemp3(String temp3) {
		this.Temp3 = temp3;
    }

}