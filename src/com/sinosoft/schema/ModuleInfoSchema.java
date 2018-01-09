package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：模块信息表<br>
 * 表代码：ModuleInfo<br>
 * 表主键：Id<br>
 */
public class ModuleInfoSchema extends Schema {
	private String Id;

	private String ModuleName;

	private String ModuleType;

	private String ModuleURL;

	private String ProduceFlag;

	private String Memo;

	private Date CreateDate;

	private Date ModifyDate;

	private Date ProDate;

	private String Remark1;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("ModuleName", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("ModuleType", DataColumn.STRING, 2, 5 , 0 , false , false),
		new SchemaColumn("ModuleURL", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("ProduceFlag", DataColumn.STRING, 4, 1 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("ProDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("Remark1", DataColumn.STRING, 9, 20 , 0 , false , false)
	};

	public static final String _TableCode = "ModuleInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ModuleInfo values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ModuleInfo set Id=?,ModuleName=?,ModuleType=?,ModuleURL=?,ProduceFlag=?,Memo=?,CreateDate=?,ModifyDate=?,ProDate=?,Remark1=? where Id=?";

	protected static final String _DeleteSQL = "delete from ModuleInfo  where Id=?";

	protected static final String _FillAllSQL = "select * from ModuleInfo  where Id=?";

	public ModuleInfoSchema(){
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
		return new ModuleInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ModuleInfoSet();
	}

	public ModuleInfoSet query() {
		return query(null, -1, -1);
	}

	public ModuleInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ModuleInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ModuleInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ModuleInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ModuleName = (String)v;return;}
		if (i == 2){ModuleType = (String)v;return;}
		if (i == 3){ModuleURL = (String)v;return;}
		if (i == 4){ProduceFlag = (String)v;return;}
		if (i == 5){Memo = (String)v;return;}
		if (i == 6){CreateDate = (Date)v;return;}
		if (i == 7){ModifyDate = (Date)v;return;}
		if (i == 8){ProDate = (Date)v;return;}
		if (i == 9){Remark1 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ModuleName;}
		if (i == 2){return ModuleType;}
		if (i == 3){return ModuleURL;}
		if (i == 4){return ProduceFlag;}
		if (i == 5){return Memo;}
		if (i == 6){return CreateDate;}
		if (i == 7){return ModifyDate;}
		if (i == 8){return ProDate;}
		if (i == 9){return Remark1;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ModuleName的值，该字段的<br>
	* 字段名称 :模块名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleName() {
		return ModuleName;
	}

	/**
	* 设置字段ModuleName的值，该字段的<br>
	* 字段名称 :模块名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleName(String moduleName) {
		this.ModuleName = moduleName;
    }

	/**
	* 获取字段ModuleType的值，该字段的<br>
	* 字段名称 :模块类型<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleType() {
		return ModuleType;
	}

	/**
	* 设置字段ModuleType的值，该字段的<br>
	* 字段名称 :模块类型<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleType(String moduleType) {
		this.ModuleType = moduleType;
    }

	/**
	* 获取字段ModuleURL的值，该字段的<br>
	* 字段名称 :模块路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleURL() {
		return ModuleURL;
	}

	/**
	* 设置字段ModuleURL的值，该字段的<br>
	* 字段名称 :模块路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleURL(String moduleURL) {
		this.ModuleURL = moduleURL;
    }

	/**
	* 获取字段ProduceFlag的值，该字段的<br>
	* 字段名称 :生成标记<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProduceFlag() {
		return ProduceFlag;
	}

	/**
	* 设置字段ProduceFlag的值，该字段的<br>
	* 字段名称 :生成标记<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProduceFlag(String produceFlag) {
		this.ProduceFlag = produceFlag;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备用<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备用<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段ProDate的值，该字段的<br>
	* 字段名称 :生成日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getProDate() {
		return ProDate;
	}

	/**
	* 设置字段ProDate的值，该字段的<br>
	* 字段名称 :生成日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProDate(Date proDate) {
		this.ProDate = proDate;
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

}