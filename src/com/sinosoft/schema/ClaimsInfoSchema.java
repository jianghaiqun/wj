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
 * 表名称：理赔资料信息<br>
 * 表代码：ClaimsInfo<br>
 * 表主键：id<br>
 */
public class ClaimsInfoSchema extends Schema {
	private String id;

	private String name;

	private String fileName;

	private String suffix;

	private String fileSize;

	private String path;

	private String comCode;

	private String classifyId;

	private String riskType;

	private String isHot;

	private Long orderFlag;

	private String prop1;

	private String prop2;

	private String prop3;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("name", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("fileName", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("suffix", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("fileSize", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("path", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("comCode", DataColumn.STRING, 6, 30 , 0 , false , false),
		new SchemaColumn("classifyId", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("riskType", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("isHot", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("orderFlag", DataColumn.LONG, 10, 20 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 17, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ClaimsInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ClaimsInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ClaimsInfo set id=?,name=?,fileName=?,suffix=?,fileSize=?,path=?,comCode=?,classifyId=?,riskType=?,isHot=?,orderFlag=?,prop1=?,prop2=?,prop3=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from ClaimsInfo  where id=?";

	protected static final String _FillAllSQL = "select * from ClaimsInfo  where id=?";

	public ClaimsInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new ClaimsInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ClaimsInfoSet();
	}

	public ClaimsInfoSet query() {
		return query(null, -1, -1);
	}

	public ClaimsInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ClaimsInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ClaimsInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ClaimsInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){name = (String)v;return;}
		if (i == 2){fileName = (String)v;return;}
		if (i == 3){suffix = (String)v;return;}
		if (i == 4){fileSize = (String)v;return;}
		if (i == 5){path = (String)v;return;}
		if (i == 6){comCode = (String)v;return;}
		if (i == 7){classifyId = (String)v;return;}
		if (i == 8){riskType = (String)v;return;}
		if (i == 9){isHot = (String)v;return;}
		if (i == 10){if(v==null){orderFlag = null;}else{orderFlag = new Long(v.toString());}return;}
		if (i == 11){prop1 = (String)v;return;}
		if (i == 12){prop2 = (String)v;return;}
		if (i == 13){prop3 = (String)v;return;}
		if (i == 14){CreateUser = (String)v;return;}
		if (i == 15){CreateDate = (Date)v;return;}
		if (i == 16){ModifyUser = (String)v;return;}
		if (i == 17){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return name;}
		if (i == 2){return fileName;}
		if (i == 3){return suffix;}
		if (i == 4){return fileSize;}
		if (i == 5){return path;}
		if (i == 6){return comCode;}
		if (i == 7){return classifyId;}
		if (i == 8){return riskType;}
		if (i == 9){return isHot;}
		if (i == 10){return orderFlag;}
		if (i == 11){return prop1;}
		if (i == 12){return prop2;}
		if (i == 13){return prop3;}
		if (i == 14){return CreateUser;}
		if (i == 15){return CreateDate;}
		if (i == 16){return ModifyUser;}
		if (i == 17){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段name的值，该字段的<br>
	* 字段名称 :附件名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getname() {
		return name;
	}

	/**
	* 设置字段name的值，该字段的<br>
	* 字段名称 :附件名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setname(String name) {
		this.name = name;
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
	* 获取字段suffix的值，该字段的<br>
	* 字段名称 :文件格式<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsuffix() {
		return suffix;
	}

	/**
	* 设置字段suffix的值，该字段的<br>
	* 字段名称 :文件格式<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsuffix(String suffix) {
		this.suffix = suffix;
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
	* 获取字段path的值，该字段的<br>
	* 字段名称 :文件路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpath() {
		return path;
	}

	/**
	* 设置字段path的值，该字段的<br>
	* 字段名称 :文件路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpath(String path) {
		this.path = path;
    }

	/**
	* 获取字段comCode的值，该字段的<br>
	* 字段名称 :所属保险公司<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcomCode() {
		return comCode;
	}

	/**
	* 设置字段comCode的值，该字段的<br>
	* 字段名称 :所属保险公司<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcomCode(String comCode) {
		this.comCode = comCode;
    }

	/**
	* 获取字段classifyId的值，该字段的<br>
	* 字段名称 :所属二级分类<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclassifyId() {
		return classifyId;
	}

	/**
	* 设置字段classifyId的值，该字段的<br>
	* 字段名称 :所属二级分类<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclassifyId(String classifyId) {
		this.classifyId = classifyId;
    }

	/**
	* 获取字段riskType的值，该字段的<br>
	* 字段名称 :险种属性<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getriskType() {
		return riskType;
	}

	/**
	* 设置字段riskType的值，该字段的<br>
	* 字段名称 :险种属性<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setriskType(String riskType) {
		this.riskType = riskType;
    }

	/**
	* 获取字段isHot的值，该字段的<br>
	* 字段名称 :是否热门<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisHot() {
		return isHot;
	}

	/**
	* 设置字段isHot的值，该字段的<br>
	* 字段名称 :是否热门<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisHot(String isHot) {
		this.isHot = isHot;
    }

	/**
	* 获取字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getorderFlag() {
		if(orderFlag==null){return 0;}
		return orderFlag.longValue();
	}

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(long orderFlag) {
		this.orderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(String orderFlag) {
		if (orderFlag == null){
			this.orderFlag = null;
			return;
		}
		this.orderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
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
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}