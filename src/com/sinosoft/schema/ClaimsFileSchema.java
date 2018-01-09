package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：理赔文件<br>
 * 表代码：ClaimsFile<br>
 * 表主键：id<br>
 */
public class ClaimsFileSchema extends Schema {
	private String id;

	private String claimsDataId;

	private String fileSuffix;

	private String fileSize;

	private String fileName;

	private String filePath;

	private String remark1;

	private String remark2;

	private String remark3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("claimsDataId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("fileSuffix", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("fileSize", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("fileName", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("filePath", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 12, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ClaimsFile";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ClaimsFile values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ClaimsFile set id=?,claimsDataId=?,fileSuffix=?,fileSize=?,fileName=?,filePath=?,remark1=?,remark2=?,remark3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from ClaimsFile  where id=?";

	protected static final String _FillAllSQL = "select * from ClaimsFile  where id=?";

	public ClaimsFileSchema(){
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
		return new ClaimsFileSchema();
	}

	protected SchemaSet newSet(){
		return new ClaimsFileSet();
	}

	public ClaimsFileSet query() {
		return query(null, -1, -1);
	}

	public ClaimsFileSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ClaimsFileSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ClaimsFileSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ClaimsFileSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){claimsDataId = (String)v;return;}
		if (i == 2){fileSuffix = (String)v;return;}
		if (i == 3){fileSize = (String)v;return;}
		if (i == 4){fileName = (String)v;return;}
		if (i == 5){filePath = (String)v;return;}
		if (i == 6){remark1 = (String)v;return;}
		if (i == 7){remark2 = (String)v;return;}
		if (i == 8){remark3 = (String)v;return;}
		if (i == 9){createUser = (String)v;return;}
		if (i == 10){createDate = (Date)v;return;}
		if (i == 11){modifyUser = (String)v;return;}
		if (i == 12){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return claimsDataId;}
		if (i == 2){return fileSuffix;}
		if (i == 3){return fileSize;}
		if (i == 4){return fileName;}
		if (i == 5){return filePath;}
		if (i == 6){return remark1;}
		if (i == 7){return remark2;}
		if (i == 8){return remark3;}
		if (i == 9){return createUser;}
		if (i == 10){return createDate;}
		if (i == 11){return modifyUser;}
		if (i == 12){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段claimsDataId的值，该字段的<br>
	* 字段名称 :理赔资料Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsDataId() {
		return claimsDataId;
	}

	/**
	* 设置字段claimsDataId的值，该字段的<br>
	* 字段名称 :理赔资料Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsDataId(String claimsDataId) {
		this.claimsDataId = claimsDataId;
    }

	/**
	* 获取字段fileSuffix的值，该字段的<br>
	* 字段名称 :文件格式<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfileSuffix() {
		return fileSuffix;
	}

	/**
	* 设置字段fileSuffix的值，该字段的<br>
	* 字段名称 :文件格式<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
    }

	/**
	* 获取字段fileSize的值，该字段的<br>
	* 字段名称 :文件大小<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfileSize() {
		return fileSize;
	}

	/**
	* 设置字段fileSize的值，该字段的<br>
	* 字段名称 :文件大小<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfileSize(String fileSize) {
		this.fileSize = fileSize;
    }

	/**
	* 获取字段fileName的值，该字段的<br>
	* 字段名称 :文件名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfileName() {
		return fileName;
	}

	/**
	* 设置字段fileName的值，该字段的<br>
	* 字段名称 :文件名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfileName(String fileName) {
		this.fileName = fileName;
    }

	/**
	* 获取字段filePath的值，该字段的<br>
	* 字段名称 :文件路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfilePath() {
		return filePath;
	}

	/**
	* 设置字段filePath的值，该字段的<br>
	* 字段名称 :文件路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfilePath(String filePath) {
		this.filePath = filePath;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark3() {
		return remark3;
	}

	/**
	* 设置字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark3(String remark3) {
		this.remark3 = remark3;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

}