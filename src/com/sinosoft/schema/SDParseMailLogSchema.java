package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：邮件解析日志表<br>
 * 表代码：SDParseMailLog<br>
 * 表主键：ID<br>
 */
public class SDParseMailLogSchema extends Schema {
	private Long ID;

	private Date CreateDate;

	private Date SendDate;

	private String MailFrom;

	private String Title;

	private String PolicyNo;

	private String Company;

	private String FilePath;

	private String Remark1;

	private String Remark2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("SendDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("MailFrom", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("Title", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("PolicyNo", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Company", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("FilePath", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("Remark1", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Remark2", DataColumn.STRING, 9, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SDParseMailLog";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDParseMailLog values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDParseMailLog set ID=?,CreateDate=?,SendDate=?,MailFrom=?,Title=?,PolicyNo=?,Company=?,FilePath=?,Remark1=?,Remark2=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDParseMailLog  where ID=?";

	protected static final String _FillAllSQL = "select * from SDParseMailLog  where ID=?";

	public SDParseMailLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new SDParseMailLogSchema();
	}

	protected SchemaSet newSet(){
		return new SDParseMailLogSet();
	}

	public SDParseMailLogSet query() {
		return query(null, -1, -1);
	}

	public SDParseMailLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDParseMailLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDParseMailLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDParseMailLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){CreateDate = (Date)v;return;}
		if (i == 2){SendDate = (Date)v;return;}
		if (i == 3){MailFrom = (String)v;return;}
		if (i == 4){Title = (String)v;return;}
		if (i == 5){PolicyNo = (String)v;return;}
		if (i == 6){Company = (String)v;return;}
		if (i == 7){FilePath = (String)v;return;}
		if (i == 8){Remark1 = (String)v;return;}
		if (i == 9){Remark2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return CreateDate;}
		if (i == 2){return SendDate;}
		if (i == 3){return MailFrom;}
		if (i == 4){return Title;}
		if (i == 5){return PolicyNo;}
		if (i == 6){return Company;}
		if (i == 7){return FilePath;}
		if (i == 8){return Remark1;}
		if (i == 9){return Remark2;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段SendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getSendDate() {
		return SendDate;
	}

	/**
	* 设置字段SendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendDate(Date sendDate) {
		this.SendDate = sendDate;
    }

	/**
	* 获取字段MailFrom的值，该字段的<br>
	* 字段名称 :发件人<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMailFrom() {
		return MailFrom;
	}

	/**
	* 设置字段MailFrom的值，该字段的<br>
	* 字段名称 :发件人<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMailFrom(String mailFrom) {
		this.MailFrom = mailFrom;
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :邮件主题<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :邮件主题<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段PolicyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPolicyNo() {
		return PolicyNo;
	}

	/**
	* 设置字段PolicyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPolicyNo(String policyNo) {
		this.PolicyNo = policyNo;
    }

	/**
	* 获取字段Company的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCompany() {
		return Company;
	}

	/**
	* 设置字段Company的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCompany(String company) {
		this.Company = company;
    }

	/**
	* 获取字段FilePath的值，该字段的<br>
	* 字段名称 :保存路径<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFilePath() {
		return FilePath;
	}

	/**
	* 设置字段FilePath的值，该字段的<br>
	* 字段名称 :保存路径<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFilePath(String filePath) {
		this.FilePath = filePath;
    }

	/**
	* 获取字段Remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark1() {
		return Remark1;
	}

	/**
	* 设置字段Remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark1(String remark1) {
		this.Remark1 = remark1;
    }

	/**
	* 获取字段Remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark2() {
		return Remark2;
	}

	/**
	* 设置字段Remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark2(String remark2) {
		this.Remark2 = remark2;
    }

}