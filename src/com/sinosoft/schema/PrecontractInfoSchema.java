package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：PrecontractInfo<br>
 * 表代码：PrecontractInfo<br>
 * 表主键：ID<br>
 */
public class PrecontractInfoSchema extends Schema {
	private String ID;

	private String PreconName;

	private String PreconSex;

	private String PreconEmail;

	private String PreconPhone;

	private String PreconURL;

	private String PreconPartURL;

	private String WXID;

	private Date CreateDate;
	
	private String Remark;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("PreconName", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("PreconSex", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("PreconEmail", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("PreconPhone", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("PreconURL", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("PreconPartURL", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("WXID", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 9, 0 , 0 , false , false)
	};

	public static final String _TableCode = "PrecontractInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PrecontractInfo values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PrecontractInfo set ID=?,PreconName=?,PreconSex=?,PreconEmail=?,PreconPhone=?,PreconURL=?,PreconPartURL=?,WXID=?,CreateDate=?,Remark=? where ID=?";

	protected static final String _DeleteSQL = "delete from PrecontractInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from PrecontractInfo  where ID=?";

	public PrecontractInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new PrecontractInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PrecontractInfoSet();
	}

	public PrecontractInfoSet query() {
		return query(null, -1, -1);
	}

	public PrecontractInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PrecontractInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PrecontractInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PrecontractInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){PreconName = (String)v;return;}
		if (i == 2){PreconSex = (String)v;return;}
		if (i == 3){PreconEmail = (String)v;return;}
		if (i == 4){PreconPhone = (String)v;return;}
		if (i == 5){PreconURL = (String)v;return;}
		if (i == 6){PreconPartURL = (String)v;return;}
		if (i == 7){WXID = (String)v;return;}
		if (i == 8){CreateDate = (Date)v;return;}
		if (i == 9){Remark = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return PreconName;}
		if (i == 2){return PreconSex;}
		if (i == 3){return PreconEmail;}
		if (i == 4){return PreconPhone;}
		if (i == 5){return PreconURL;}
		if (i == 6){return PreconPartURL;}
		if (i == 7){return WXID;}
		if (i == 8){return CreateDate;}
		if (i == 9){return Remark;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段PreconName的值，该字段的<br>
	* 字段名称 :PreconName<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPreconName() {
		return PreconName;
	}

	/**
	* 设置字段PreconName的值，该字段的<br>
	* 字段名称 :PreconName<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPreconName(String preconName) {
		this.PreconName = preconName;
    }

	/**
	* 获取字段PreconSex的值，该字段的<br>
	* 字段名称 :PreconSex<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPreconSex() {
		return PreconSex;
	}

	/**
	* 设置字段PreconSex的值，该字段的<br>
	* 字段名称 :PreconSex<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPreconSex(String preconSex) {
		this.PreconSex = preconSex;
    }

	/**
	* 获取字段PreconEmail的值，该字段的<br>
	* 字段名称 :PreconEmail<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPreconEmail() {
		return PreconEmail;
	}

	/**
	* 设置字段PreconEmail的值，该字段的<br>
	* 字段名称 :PreconEmail<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPreconEmail(String preconEmail) {
		this.PreconEmail = preconEmail;
    }

	/**
	* 获取字段PreconPhone的值，该字段的<br>
	* 字段名称 :PreconPhone<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPreconPhone() {
		return PreconPhone;
	}

	/**
	* 设置字段PreconPhone的值，该字段的<br>
	* 字段名称 :PreconPhone<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPreconPhone(String preconPhone) {
		this.PreconPhone = preconPhone;
    }

	/**
	* 获取字段PreconURL的值，该字段的<br>
	* 字段名称 :PreconURL<br>
	* 数据类型 :VARCHAR(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPreconURL() {
		return PreconURL;
	}

	/**
	* 设置字段PreconURL的值，该字段的<br>
	* 字段名称 :PreconURL<br>
	* 数据类型 :VARCHAR(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPreconURL(String preconURL) {
		this.PreconURL = preconURL;
    }

	/**
	* 获取字段PreconPartURL的值，该字段的<br>
	* 字段名称 :PreconPartURL<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPreconPartURL() {
		return PreconPartURL;
	}

	/**
	* 设置字段PreconPartURL的值，该字段的<br>
	* 字段名称 :PreconPartURL<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPreconPartURL(String preconPartURL) {
		this.PreconPartURL = preconPartURL;
    }

	/**
	* 获取字段WXID的值，该字段的<br>
	* 字段名称 :WXID<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWXID() {
		return WXID;
	}

	/**
	* 设置字段WXID的值，该字段的<br>
	* 字段名称 :WXID<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWXID(String wXID) {
		this.WXID = wXID;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }
	
	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :Remark<br>
	* 数据类型 :TEXT<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getRemark() {
		return CreateDate;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :Remark<br>
	* 数据类型 :TEXT<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

}