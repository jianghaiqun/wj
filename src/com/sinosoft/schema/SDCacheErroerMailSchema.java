package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：邮件异常缓存表<br>
 * 表代码：SDCacheErroerMail<br>
 * 表主键：ID<br>
 */
public class SDCacheErroerMailSchema extends Schema {
	private String ID;

	private String MailType;

	private String Addressee;

	private String Subject;

	private String Content;

	private String FilePath;

	private String Priority;

	private String PaySn;

	private String SendDate;

	private String CreateDate;

	private String ModifyDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MailType", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("Addressee", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("Subject", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 4, 0 , 0 , false , false),
		new SchemaColumn("FilePath", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("Priority", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("PaySn", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("SendDate", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 14, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDCacheErroerMail";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDCacheErroerMail values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDCacheErroerMail set ID=?,MailType=?,Addressee=?,Subject=?,Content=?,FilePath=?,Priority=?,PaySn=?,SendDate=?,CreateDate=?,ModifyDate=?,Prop1=?,Prop2=?,Prop3=?,Prop4=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDCacheErroerMail  where ID=?";

	protected static final String _FillAllSQL = "select * from SDCacheErroerMail  where ID=?";

	public SDCacheErroerMailSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new SDCacheErroerMailSchema();
	}

	protected SchemaSet newSet(){
		return new SDCacheErroerMailSet();
	}

	public SDCacheErroerMailSet query() {
		return query(null, -1, -1);
	}

	public SDCacheErroerMailSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDCacheErroerMailSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDCacheErroerMailSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDCacheErroerMailSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){MailType = (String)v;return;}
		if (i == 2){Addressee = (String)v;return;}
		if (i == 3){Subject = (String)v;return;}
		if (i == 4){Content = (String)v;return;}
		if (i == 5){FilePath = (String)v;return;}
		if (i == 6){Priority = (String)v;return;}
		if (i == 7){PaySn = (String)v;return;}
		if (i == 8){SendDate = (String)v;return;}
		if (i == 9){CreateDate = (String)v;return;}
		if (i == 10){ModifyDate = (String)v;return;}
		if (i == 11){Prop1 = (String)v;return;}
		if (i == 12){Prop2 = (String)v;return;}
		if (i == 13){Prop3 = (String)v;return;}
		if (i == 14){Prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return MailType;}
		if (i == 2){return Addressee;}
		if (i == 3){return Subject;}
		if (i == 4){return Content;}
		if (i == 5){return FilePath;}
		if (i == 6){return Priority;}
		if (i == 7){return PaySn;}
		if (i == 8){return SendDate;}
		if (i == 9){return CreateDate;}
		if (i == 10){return ModifyDate;}
		if (i == 11){return Prop1;}
		if (i == 12){return Prop2;}
		if (i == 13){return Prop3;}
		if (i == 14){return Prop4;}
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
	* 获取字段MailType的值，该字段的<br>
	* 字段名称 :邮件类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMailType() {
		return MailType;
	}

	/**
	* 设置字段MailType的值，该字段的<br>
	* 字段名称 :邮件类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMailType(String mailType) {
		this.MailType = mailType;
    }

	/**
	* 获取字段Addressee的值，该字段的<br>
	* 字段名称 :收件人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddressee() {
		return Addressee;
	}

	/**
	* 设置字段Addressee的值，该字段的<br>
	* 字段名称 :收件人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddressee(String addressee) {
		this.Addressee = addressee;
    }

	/**
	* 获取字段Subject的值，该字段的<br>
	* 字段名称 :标题<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* 设置字段Subject的值，该字段的<br>
	* 字段名称 :标题<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :正文<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :正文<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段FilePath的值，该字段的<br>
	* 字段名称 :附件<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFilePath() {
		return FilePath;
	}

	/**
	* 设置字段FilePath的值，该字段的<br>
	* 字段名称 :附件<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFilePath(String filePath) {
		this.FilePath = filePath;
    }

	/**
	* 获取字段Priority的值，该字段的<br>
	* 字段名称 :优先级<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPriority() {
		return Priority;
	}

	/**
	* 设置字段Priority的值，该字段的<br>
	* 字段名称 :优先级<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPriority(String priority) {
		this.Priority = priority;
    }

	/**
	* 获取字段PaySn的值，该字段的<br>
	* 字段名称 :交易号(支付流水号)<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPaySn() {
		return PaySn;
	}

	/**
	* 设置字段PaySn的值，该字段的<br>
	* 字段名称 :交易号(支付流水号)<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPaySn(String paySn) {
		this.PaySn = paySn;
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
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(String modifyDate) {
		this.ModifyDate = modifyDate;
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
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

}