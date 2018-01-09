package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：分公司批次删除权限申请记录表<br>
 * 表代码：authorityinfo<br>
 * 表主键：ID<br>
 */
public class authorityinfoSchema extends Schema {
	private String ID;

	private String branchCode;

	private String batchNumber;

	private String reason;

	private String state;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private Date createdate;

	private String createuser;

	private Date modifydate;

	private String modifyuser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("branchCode", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("batchNumber", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("reason", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("state", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 6, 32 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 7, 32 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 8, 32 , 0 , false , false),
		new SchemaColumn("createdate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("createuser", DataColumn.STRING, 10, 32 , 0 , false , false),
		new SchemaColumn("modifydate", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("modifyuser", DataColumn.STRING, 12, 32 , 0 , false , false)
	};

	public static final String _TableCode = "authorityinfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into authorityinfo values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update authorityinfo set ID=?,branchCode=?,batchNumber=?,reason=?,state=?,prop1=?,prop2=?,prop3=?,prop4=?,createdate=?,createuser=?,modifydate=?,modifyuser=? where ID=?";

	protected static final String _DeleteSQL = "delete from authorityinfo  where ID=?";

	protected static final String _FillAllSQL = "select * from authorityinfo  where ID=?";

	public authorityinfoSchema(){
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
		return new authorityinfoSchema();
	}

	protected SchemaSet newSet(){
		return new authorityinfoSet();
	}

	public authorityinfoSet query() {
		return query(null, -1, -1);
	}

	public authorityinfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public authorityinfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public authorityinfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (authorityinfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){branchCode = (String)v;return;}
		if (i == 2){batchNumber = (String)v;return;}
		if (i == 3){reason = (String)v;return;}
		if (i == 4){state = (String)v;return;}
		if (i == 5){prop1 = (String)v;return;}
		if (i == 6){prop2 = (String)v;return;}
		if (i == 7){prop3 = (String)v;return;}
		if (i == 8){prop4 = (String)v;return;}
		if (i == 9){createdate = (Date)v;return;}
		if (i == 10){createuser = (String)v;return;}
		if (i == 11){modifydate = (Date)v;return;}
		if (i == 12){modifyuser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return branchCode;}
		if (i == 2){return batchNumber;}
		if (i == 3){return reason;}
		if (i == 4){return state;}
		if (i == 5){return prop1;}
		if (i == 6){return prop2;}
		if (i == 7){return prop3;}
		if (i == 8){return prop4;}
		if (i == 9){return createdate;}
		if (i == 10){return createuser;}
		if (i == 11){return modifydate;}
		if (i == 12){return modifyuser;}
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
	* 获取字段branchCode的值，该字段的<br>
	* 字段名称 :批次号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbranchCode() {
		return branchCode;
	}

	/**
	* 设置字段branchCode的值，该字段的<br>
	* 字段名称 :批次号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbranchCode(String branchCode) {
		this.branchCode = branchCode;
    }

	/**
	* 获取字段batchNumber的值，该字段的<br>
	* 字段名称 :机构号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbatchNumber() {
		return batchNumber;
	}

	/**
	* 设置字段batchNumber的值，该字段的<br>
	* 字段名称 :机构号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
    }

	/**
	* 获取字段reason的值，该字段的<br>
	* 字段名称 :申请理由<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreason() {
		return reason;
	}

	/**
	* 设置字段reason的值，该字段的<br>
	* 字段名称 :申请理由<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreason(String reason) {
		this.reason = reason;
    }

	/**
	* 获取字段state的值，该字段的<br>
	* 字段名称 :申请状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstate() {
		return state;
	}

	/**
	* 设置字段state的值，该字段的<br>
	* 字段名称 :申请状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstate(String state) {
		this.state = state;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段createdate的值，该字段的<br>
	* 字段名称 :申请时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreatedate() {
		return createdate;
	}

	/**
	* 设置字段createdate的值，该字段的<br>
	* 字段名称 :申请时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreatedate(Date createdate) {
		this.createdate = createdate;
    }

	/**
	* 获取字段createuser的值，该字段的<br>
	* 字段名称 :申请人<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateuser() {
		return createuser;
	}

	/**
	* 设置字段createuser的值，该字段的<br>
	* 字段名称 :申请人<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateuser(String createuser) {
		this.createuser = createuser;
    }

	/**
	* 获取字段modifydate的值，该字段的<br>
	* 字段名称 :处理时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifydate() {
		return modifydate;
	}

	/**
	* 设置字段modifydate的值，该字段的<br>
	* 字段名称 :处理时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifydate(Date modifydate) {
		this.modifydate = modifydate;
    }

	/**
	* 获取字段modifyuser的值，该字段的<br>
	* 字段名称 :处理人<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyuser() {
		return modifyuser;
	}

	/**
	* 设置字段modifyuser的值，该字段的<br>
	* 字段名称 :处理人<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
    }

}