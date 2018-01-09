package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：财务数据确定总和表<br>
 * 表代码：financialsuredataSUM<br>
 * 表主键：ID<br>
 */
public class financialsuredataSUMSchema extends Schema {
	private String ID;

	private String batchNumber;

	private String branchcode;

	private Date invoicedate;

	private String insurancetype;

	private String insurancesubtype;

	private String state;

	private String premium1stSUM;

	private String premium2ndSUM;

	private String fee1stSUM;

	private String fee2ndSUM;

	private Date createdate;

	private String createuser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("batchNumber", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("branchcode", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("invoicedate", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("insurancetype", DataColumn.STRING, 4, 20 , 0 , true , false),
		new SchemaColumn("insurancesubtype", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("state", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("premium1stSUM", DataColumn.STRING, 7, 32 , 0 , false , false),
		new SchemaColumn("premium2ndSUM", DataColumn.STRING, 8, 32 , 0 , false , false),
		new SchemaColumn("fee1stSUM", DataColumn.STRING, 9, 32 , 0 , false , false),
		new SchemaColumn("fee2ndSUM", DataColumn.STRING, 10, 32 , 0 , false , false),
		new SchemaColumn("createdate", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("createuser", DataColumn.STRING, 12, 32 , 0 , false , false)
	};

	public static final String _TableCode = "financialsuredataSUM";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into financialsuredataSUM values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update financialsuredataSUM set ID=?,batchNumber=?,branchcode=?,invoicedate=?,insurancetype=?,insurancesubtype=?,state=?,premium1stSUM=?,premium2ndSUM=?,fee1stSUM=?,fee2ndSUM=?,createdate=?,createuser=? where ID=?";

	protected static final String _DeleteSQL = "delete from financialsuredataSUM  where ID=?";

	protected static final String _FillAllSQL = "select * from financialsuredataSUM  where ID=?";

	public financialsuredataSUMSchema(){
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
		return new financialsuredataSUMSchema();
	}

	protected SchemaSet newSet(){
		return new financialsuredataSUMSet();
	}

	public financialsuredataSUMSet query() {
		return query(null, -1, -1);
	}

	public financialsuredataSUMSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public financialsuredataSUMSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public financialsuredataSUMSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (financialsuredataSUMSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){batchNumber = (String)v;return;}
		if (i == 2){branchcode = (String)v;return;}
		if (i == 3){invoicedate = (Date)v;return;}
		if (i == 4){insurancetype = (String)v;return;}
		if (i == 5){insurancesubtype = (String)v;return;}
		if (i == 6){state = (String)v;return;}
		if (i == 7){premium1stSUM = (String)v;return;}
		if (i == 8){premium2ndSUM = (String)v;return;}
		if (i == 9){fee1stSUM = (String)v;return;}
		if (i == 10){fee2ndSUM = (String)v;return;}
		if (i == 11){createdate = (Date)v;return;}
		if (i == 12){createuser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return batchNumber;}
		if (i == 2){return branchcode;}
		if (i == 3){return invoicedate;}
		if (i == 4){return insurancetype;}
		if (i == 5){return insurancesubtype;}
		if (i == 6){return state;}
		if (i == 7){return premium1stSUM;}
		if (i == 8){return premium2ndSUM;}
		if (i == 9){return fee1stSUM;}
		if (i == 10){return fee2ndSUM;}
		if (i == 11){return createdate;}
		if (i == 12){return createuser;}
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
	* 获取字段batchNumber的值，该字段的<br>
	* 字段名称 :批次号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbatchNumber() {
		return batchNumber;
	}

	/**
	* 设置字段batchNumber的值，该字段的<br>
	* 字段名称 :批次号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
    }

	/**
	* 获取字段branchcode的值，该字段的<br>
	* 字段名称 :机构<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbranchcode() {
		return branchcode;
	}

	/**
	* 设置字段branchcode的值，该字段的<br>
	* 字段名称 :机构<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbranchcode(String branchcode) {
		this.branchcode = branchcode;
    }

	/**
	* 获取字段invoicedate的值，该字段的<br>
	* 字段名称 :开票时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getinvoicedate() {
		return invoicedate;
	}

	/**
	* 设置字段invoicedate的值，该字段的<br>
	* 字段名称 :开票时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
    }

	/**
	* 获取字段insurancetype的值，该字段的<br>
	* 字段名称 :保监险种类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getinsurancetype() {
		return insurancetype;
	}

	/**
	* 设置字段insurancetype的值，该字段的<br>
	* 字段名称 :保监险种类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setinsurancetype(String insurancetype) {
		this.insurancetype = insurancetype;
    }

	/**
	* 获取字段insurancesubtype的值，该字段的<br>
	* 字段名称 :保健险种细分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsurancesubtype() {
		return insurancesubtype;
	}

	/**
	* 设置字段insurancesubtype的值，该字段的<br>
	* 字段名称 :保健险种细分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsurancesubtype(String insurancesubtype) {
		this.insurancesubtype = insurancesubtype;
    }

	/**
	* 获取字段state的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstate() {
		return state;
	}

	/**
	* 设置字段state的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstate(String state) {
		this.state = state;
    }

	/**
	* 获取字段premium1stSUM的值，该字段的<br>
	* 字段名称 :新单保费总和<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpremium1stSUM() {
		return premium1stSUM;
	}

	/**
	* 设置字段premium1stSUM的值，该字段的<br>
	* 字段名称 :新单保费总和<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpremium1stSUM(String premium1stSUM) {
		this.premium1stSUM = premium1stSUM;
    }

	/**
	* 获取字段premium2ndSUM的值，该字段的<br>
	* 字段名称 :续期保费总和<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpremium2ndSUM() {
		return premium2ndSUM;
	}

	/**
	* 设置字段premium2ndSUM的值，该字段的<br>
	* 字段名称 :续期保费总和<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpremium2ndSUM(String premium2ndSUM) {
		this.premium2ndSUM = premium2ndSUM;
    }

	/**
	* 获取字段fee1stSUM的值，该字段的<br>
	* 字段名称 :新单手续费总和<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfee1stSUM() {
		return fee1stSUM;
	}

	/**
	* 设置字段fee1stSUM的值，该字段的<br>
	* 字段名称 :新单手续费总和<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfee1stSUM(String fee1stSUM) {
		this.fee1stSUM = fee1stSUM;
    }

	/**
	* 获取字段fee2ndSUM的值，该字段的<br>
	* 字段名称 :续期手续费总和<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfee2ndSUM() {
		return fee2ndSUM;
	}

	/**
	* 设置字段fee2ndSUM的值，该字段的<br>
	* 字段名称 :续期手续费总和<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfee2ndSUM(String fee2ndSUM) {
		this.fee2ndSUM = fee2ndSUM;
    }

	/**
	* 获取字段createdate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreatedate() {
		return createdate;
	}

	/**
	* 设置字段createdate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreatedate(Date createdate) {
		this.createdate = createdate;
    }

	/**
	* 获取字段createuser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateuser() {
		return createuser;
	}

	/**
	* 设置字段createuser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateuser(String createuser) {
		this.createuser = createuser;
    }

}