package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：退款文件<br>
 * 表代码：RefundFileInfo<br>
 * 表主键：id<br>
 */
public class RefundFileInfoSchema extends Schema {
	private String id;

	private String FileName;

	private String FilePath;

	private Date CreateDate;

	private String CreateUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("FileName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("FilePath", DataColumn.STRING, 2, 256 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 7, 100 , 0 , false , false)
	};

	public static final String _TableCode = "RefundFileInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into RefundFileInfo values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update RefundFileInfo set id=?,FileName=?,FilePath=?,CreateDate=?,CreateUser=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from RefundFileInfo  where id=?";

	protected static final String _FillAllSQL = "select * from RefundFileInfo  where id=?";

	public RefundFileInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new RefundFileInfoSchema();
	}

	protected SchemaSet newSet(){
		return new RefundFileInfoSet();
	}

	public RefundFileInfoSet query() {
		return query(null, -1, -1);
	}

	public RefundFileInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public RefundFileInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public RefundFileInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (RefundFileInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){FileName = (String)v;return;}
		if (i == 2){FilePath = (String)v;return;}
		if (i == 3){CreateDate = (Date)v;return;}
		if (i == 4){CreateUser = (String)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return FileName;}
		if (i == 2){return FilePath;}
		if (i == 3){return CreateDate;}
		if (i == 4){return CreateUser;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return Prop3;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段FileName的值，该字段的<br>
	* 字段名称 :文件名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFileName() {
		return FileName;
	}

	/**
	* 设置字段FileName的值，该字段的<br>
	* 字段名称 :文件名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFileName(String fileName) {
		this.FileName = fileName;
    }

	/**
	* 获取字段FilePath的值，该字段的<br>
	* 字段名称 :文件路径<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFilePath() {
		return FilePath;
	}

	/**
	* 设置字段FilePath的值，该字段的<br>
	* 字段名称 :文件路径<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFilePath(String filePath) {
		this.FilePath = filePath;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}