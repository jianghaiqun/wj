package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：短信4<br>
 * 表代码：LIMessageService<br>
 * 表主键：ServiceCode<br>
 */
public class LIMessageServiceSchema extends Schema {
	private String ServiceCode;

	private String ServiceType;

	private String ServiceClass;

	private String ServiceKind;

	private String ServiceName;

	private String ServiceObj;

	private String ServiceObjName;

	private String MessTopic;

	private String ComKind;

	private String SendWay;

	private String DealOrder;

	private Date SendDate;

	private String SendTime;

	private String ReplyFlag;

	private String CheckFlag;

	private String BatchFlag;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ServiceCode", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("ServiceType", DataColumn.STRING, 1, 2 , 0 , true , false),
		new SchemaColumn("ServiceClass", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("ServiceKind", DataColumn.STRING, 3, 2 , 0 , false , false),
		new SchemaColumn("ServiceName", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("ServiceObj", DataColumn.STRING, 5, 60 , 0 , false , false),
		new SchemaColumn("ServiceObjName", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("MessTopic", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("ComKind", DataColumn.STRING, 8, 2 , 0 , false , false),
		new SchemaColumn("SendWay", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("DealOrder", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("SendDate", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("SendTime", DataColumn.STRING, 12, 8 , 0 , false , false),
		new SchemaColumn("ReplyFlag", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("CheckFlag", DataColumn.STRING, 14, 2 , 0 , true , false),
		new SchemaColumn("BatchFlag", DataColumn.STRING, 15, 2 , 0 , true , false)
	};

	public static final String _TableCode = "LIMessageService";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LIMessageService values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LIMessageService set ServiceCode=?,ServiceType=?,ServiceClass=?,ServiceKind=?,ServiceName=?,ServiceObj=?,ServiceObjName=?,MessTopic=?,ComKind=?,SendWay=?,DealOrder=?,SendDate=?,SendTime=?,ReplyFlag=?,CheckFlag=?,BatchFlag=? where ServiceCode=?";

	protected static final String _DeleteSQL = "delete from LIMessageService  where ServiceCode=?";

	protected static final String _FillAllSQL = "select * from LIMessageService  where ServiceCode=?";

	public LIMessageServiceSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[16];
	}

	protected Schema newInstance(){
		return new LIMessageServiceSchema();
	}

	protected SchemaSet newSet(){
		return new LIMessageServiceSet();
	}

	public LIMessageServiceSet query() {
		return query(null, -1, -1);
	}

	public LIMessageServiceSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LIMessageServiceSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LIMessageServiceSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LIMessageServiceSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ServiceCode = (String)v;return;}
		if (i == 1){ServiceType = (String)v;return;}
		if (i == 2){ServiceClass = (String)v;return;}
		if (i == 3){ServiceKind = (String)v;return;}
		if (i == 4){ServiceName = (String)v;return;}
		if (i == 5){ServiceObj = (String)v;return;}
		if (i == 6){ServiceObjName = (String)v;return;}
		if (i == 7){MessTopic = (String)v;return;}
		if (i == 8){ComKind = (String)v;return;}
		if (i == 9){SendWay = (String)v;return;}
		if (i == 10){DealOrder = (String)v;return;}
		if (i == 11){SendDate = (Date)v;return;}
		if (i == 12){SendTime = (String)v;return;}
		if (i == 13){ReplyFlag = (String)v;return;}
		if (i == 14){CheckFlag = (String)v;return;}
		if (i == 15){BatchFlag = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ServiceCode;}
		if (i == 1){return ServiceType;}
		if (i == 2){return ServiceClass;}
		if (i == 3){return ServiceKind;}
		if (i == 4){return ServiceName;}
		if (i == 5){return ServiceObj;}
		if (i == 6){return ServiceObjName;}
		if (i == 7){return MessTopic;}
		if (i == 8){return ComKind;}
		if (i == 9){return SendWay;}
		if (i == 10){return DealOrder;}
		if (i == 11){return SendDate;}
		if (i == 12){return SendTime;}
		if (i == 13){return ReplyFlag;}
		if (i == 14){return CheckFlag;}
		if (i == 15){return BatchFlag;}
		return null;
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
	* 获取字段ServiceType的值，该字段的<br>
	* 字段名称 :ServiceType<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getServiceType() {
		return ServiceType;
	}

	/**
	* 设置字段ServiceType的值，该字段的<br>
	* 字段名称 :ServiceType<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setServiceType(String serviceType) {
		this.ServiceType = serviceType;
    }

	/**
	* 获取字段ServiceClass的值，该字段的<br>
	* 字段名称 :ServiceClass<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceClass() {
		return ServiceClass;
	}

	/**
	* 设置字段ServiceClass的值，该字段的<br>
	* 字段名称 :ServiceClass<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceClass(String serviceClass) {
		this.ServiceClass = serviceClass;
    }

	/**
	* 获取字段ServiceKind的值，该字段的<br>
	* 字段名称 :ServiceKind<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceKind() {
		return ServiceKind;
	}

	/**
	* 设置字段ServiceKind的值，该字段的<br>
	* 字段名称 :ServiceKind<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceKind(String serviceKind) {
		this.ServiceKind = serviceKind;
    }

	/**
	* 获取字段ServiceName的值，该字段的<br>
	* 字段名称 :ServiceName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceName() {
		return ServiceName;
	}

	/**
	* 设置字段ServiceName的值，该字段的<br>
	* 字段名称 :ServiceName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceName(String serviceName) {
		this.ServiceName = serviceName;
    }

	/**
	* 获取字段ServiceObj的值，该字段的<br>
	* 字段名称 :ServiceObj<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceObj() {
		return ServiceObj;
	}

	/**
	* 设置字段ServiceObj的值，该字段的<br>
	* 字段名称 :ServiceObj<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceObj(String serviceObj) {
		this.ServiceObj = serviceObj;
    }

	/**
	* 获取字段ServiceObjName的值，该字段的<br>
	* 字段名称 :ServiceObjName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceObjName() {
		return ServiceObjName;
	}

	/**
	* 设置字段ServiceObjName的值，该字段的<br>
	* 字段名称 :ServiceObjName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceObjName(String serviceObjName) {
		this.ServiceObjName = serviceObjName;
    }

	/**
	* 获取字段MessTopic的值，该字段的<br>
	* 字段名称 :MessTopic<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMessTopic() {
		return MessTopic;
	}

	/**
	* 设置字段MessTopic的值，该字段的<br>
	* 字段名称 :MessTopic<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMessTopic(String messTopic) {
		this.MessTopic = messTopic;
    }

	/**
	* 获取字段ComKind的值，该字段的<br>
	* 字段名称 :ComKind<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getComKind() {
		return ComKind;
	}

	/**
	* 设置字段ComKind的值，该字段的<br>
	* 字段名称 :ComKind<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setComKind(String comKind) {
		this.ComKind = comKind;
    }

	/**
	* 获取字段SendWay的值，该字段的<br>
	* 字段名称 :SendWay<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendWay() {
		return SendWay;
	}

	/**
	* 设置字段SendWay的值，该字段的<br>
	* 字段名称 :SendWay<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendWay(String sendWay) {
		this.SendWay = sendWay;
    }

	/**
	* 获取字段DealOrder的值，该字段的<br>
	* 字段名称 :DealOrder<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDealOrder() {
		return DealOrder;
	}

	/**
	* 设置字段DealOrder的值，该字段的<br>
	* 字段名称 :DealOrder<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDealOrder(String dealOrder) {
		this.DealOrder = dealOrder;
    }

	/**
	* 获取字段SendDate的值，该字段的<br>
	* 字段名称 :SendDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getSendDate() {
		return SendDate;
	}

	/**
	* 设置字段SendDate的值，该字段的<br>
	* 字段名称 :SendDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendDate(Date sendDate) {
		this.SendDate = sendDate;
    }

	/**
	* 获取字段SendTime的值，该字段的<br>
	* 字段名称 :SendTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendTime() {
		return SendTime;
	}

	/**
	* 设置字段SendTime的值，该字段的<br>
	* 字段名称 :SendTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendTime(String sendTime) {
		this.SendTime = sendTime;
    }

	/**
	* 获取字段ReplyFlag的值，该字段的<br>
	* 字段名称 :ReplyFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReplyFlag() {
		return ReplyFlag;
	}

	/**
	* 设置字段ReplyFlag的值，该字段的<br>
	* 字段名称 :ReplyFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyFlag(String replyFlag) {
		this.ReplyFlag = replyFlag;
    }

	/**
	* 获取字段CheckFlag的值，该字段的<br>
	* 字段名称 :CheckFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCheckFlag() {
		return CheckFlag;
	}

	/**
	* 设置字段CheckFlag的值，该字段的<br>
	* 字段名称 :CheckFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCheckFlag(String checkFlag) {
		this.CheckFlag = checkFlag;
    }

	/**
	* 获取字段BatchFlag的值，该字段的<br>
	* 字段名称 :BatchFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBatchFlag() {
		return BatchFlag;
	}

	/**
	* 设置字段BatchFlag的值，该字段的<br>
	* 字段名称 :BatchFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBatchFlag(String batchFlag) {
		this.BatchFlag = batchFlag;
    }

}