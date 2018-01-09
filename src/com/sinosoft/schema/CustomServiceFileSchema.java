package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：客服询单信息文件表<br>
 * 表代码：CustomServiceFile<br>
 * 表主键：id<br>
 */
public class CustomServiceFileSchema extends Schema {
	private String id;

	private String FileName;

	private String FilePath;

	private String FileDesc;

	private String FileType;

	private String useFlag;

	private Date AddTime;

	private String AddUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("FileName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("FilePath", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("FileDesc", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("FileType", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("useFlag", DataColumn.STRING, 5, 1 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 10, 100 , 0 , false , false)
	};

	public static final String _TableCode = "CustomServiceFile";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CustomServiceFile values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CustomServiceFile set id=?,FileName=?,FilePath=?,FileDesc=?,FileType=?,useFlag=?,AddTime=?,AddUser=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from CustomServiceFile  where id=?";

	protected static final String _FillAllSQL = "select * from CustomServiceFile  where id=?";

	public CustomServiceFileSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new CustomServiceFileSchema();
	}

	protected SchemaSet newSet(){
		return new CustomServiceFileSet();
	}

	public CustomServiceFileSet query() {
		return query(null, -1, -1);
	}

	public CustomServiceFileSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CustomServiceFileSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CustomServiceFileSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CustomServiceFileSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){FileName = (String)v;return;}
		if (i == 2){FilePath = (String)v;return;}
		if (i == 3){FileDesc = (String)v;return;}
		if (i == 4){FileType = (String)v;return;}
		if (i == 5){useFlag = (String)v;return;}
		if (i == 6){AddTime = (Date)v;return;}
		if (i == 7){AddUser = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return FileName;}
		if (i == 2){return FilePath;}
		if (i == 3){return FileDesc;}
		if (i == 4){return FileType;}
		if (i == 5){return useFlag;}
		if (i == 6){return AddTime;}
		if (i == 7){return AddUser;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return Prop3;}
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
	* 字段名称 :文件地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFilePath() {
		return FilePath;
	}

	/**
	* 设置字段FilePath的值，该字段的<br>
	* 字段名称 :文件地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFilePath(String filePath) {
		this.FilePath = filePath;
    }

	/**
	* 获取字段FileDesc的值，该字段的<br>
	* 字段名称 :文件描述<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFileDesc() {
		return FileDesc;
	}

	/**
	* 设置字段FileDesc的值，该字段的<br>
	* 字段名称 :文件描述<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFileDesc(String fileDesc) {
		this.FileDesc = fileDesc;
    }

	/**
	* 获取字段FileType的值，该字段的<br>
	* 字段名称 :文件类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFileType() {
		return FileType;
	}

	/**
	* 设置字段FileType的值，该字段的<br>
	* 字段名称 :文件类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFileType(String fileType) {
		this.FileType = fileType;
    }

	/**
	* 获取字段useFlag的值，该字段的<br>
	* 字段名称 :是否解析<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getuseFlag() {
		return useFlag;
	}

	/**
	* 设置字段useFlag的值，该字段的<br>
	* 字段名称 :是否解析<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setuseFlag(String useFlag) {
		this.useFlag = useFlag;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
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