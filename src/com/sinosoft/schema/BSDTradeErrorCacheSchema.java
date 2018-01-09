package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：交易失败缓存表����<br>
 * 表代码：BSDTradeErrorCache<br>
 * 表备注：<br>
交易失败缓存表交易失败缓存表<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BSDTradeErrorCacheSchema extends Schema {
	private String ID;

	private String OrderSn;

	private String CompanyName;

	private String insuredSn;

	private String SendDate;

	private String ErrorMessage;

	private String CreateDate;

	private String Prop1;

	private String Prop2;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("OrderSn", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("CompanyName", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("insuredSn", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("SendDate", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("ErrorMessage", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 9, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 10, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 12, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BSDTradeErrorCache";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BSDTradeErrorCache values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BSDTradeErrorCache set ID=?,OrderSn=?,CompanyName=?,insuredSn=?,SendDate=?,ErrorMessage=?,CreateDate=?,Prop1=?,Prop2=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BSDTradeErrorCache  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BSDTradeErrorCache  where ID=? and BackupNo=?";

	public BSDTradeErrorCacheSchema(){
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
		return new BSDTradeErrorCacheSchema();
	}

	protected SchemaSet newSet(){
		return new BSDTradeErrorCacheSet();
	}

	public BSDTradeErrorCacheSet query() {
		return query(null, -1, -1);
	}

	public BSDTradeErrorCacheSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BSDTradeErrorCacheSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BSDTradeErrorCacheSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BSDTradeErrorCacheSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){OrderSn = (String)v;return;}
		if (i == 2){CompanyName = (String)v;return;}
		if (i == 3){insuredSn = (String)v;return;}
		if (i == 4){SendDate = (String)v;return;}
		if (i == 5){ErrorMessage = (String)v;return;}
		if (i == 6){CreateDate = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){BackupNo = (String)v;return;}
		if (i == 10){BackupOperator = (String)v;return;}
		if (i == 11){BackupTime = (Date)v;return;}
		if (i == 12){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return OrderSn;}
		if (i == 2){return CompanyName;}
		if (i == 3){return insuredSn;}
		if (i == 4){return SendDate;}
		if (i == 5){return ErrorMessage;}
		if (i == 6){return CreateDate;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		if (i == 9){return BackupNo;}
		if (i == 10){return BackupOperator;}
		if (i == 11){return BackupTime;}
		if (i == 12){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段OrderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderSn() {
		return OrderSn;
	}

	/**
	* 设置字段OrderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderSn(String orderSn) {
		this.OrderSn = orderSn;
    }

	/**
	* 获取字段CompanyName的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCompanyName() {
		return CompanyName;
	}

	/**
	* 设置字段CompanyName的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCompanyName(String companyName) {
		this.CompanyName = companyName;
    }

	/**
	* 获取字段insuredSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuredSn() {
		return insuredSn;
	}

	/**
	* 设置字段insuredSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuredSn(String insuredSn) {
		this.insuredSn = insuredSn;
    }

	/**
	* 获取字段SendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendDate() {
		return SendDate;
	}

	/**
	* 设置字段SendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendDate(String sendDate) {
		this.SendDate = sendDate;
    }

	/**
	* 获取字段ErrorMessage的值，该字段的<br>
	* 字段名称 :异常信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getErrorMessage() {
		return ErrorMessage;
	}

	/**
	* 设置字段ErrorMessage的值，该字段的<br>
	* 字段名称 :异常信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setErrorMessage(String errorMessage) {
		this.ErrorMessage = errorMessage;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :���ݱ��<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :���ݱ��<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :������<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :������<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :����ʱ��<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :����ʱ��<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :���ݱ�ע<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :���ݱ�ע<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}