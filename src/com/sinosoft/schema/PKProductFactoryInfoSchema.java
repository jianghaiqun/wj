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
 * 表名称：PK产品投保要素信息表<br>
 * 表代码：PKProductFactoryInfo<br>
 * 表主键：ID<br>
 */
public class PKProductFactoryInfoSchema extends Schema {
	private String ID;

	private String RiskCode;

	private String AppFactorCode;

	private String FactoryType;

	private String FactoryName;

	private String FactoryValue;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private Date CreateDate;

	private String CreateUser;

	private Date ModifyDate;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("RiskCode", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("AppFactorCode", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("FactoryType", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("FactoryName", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("FactoryValue", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 12, 50 , 0 , false , false)
	};

	public static final String _TableCode = "PKProductFactoryInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PKProductFactoryInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PKProductFactoryInfo set ID=?,RiskCode=?,AppFactorCode=?,FactoryType=?,FactoryName=?,FactoryValue=?,Prop1=?,Prop2=?,Prop3=?,CreateDate=?,CreateUser=?,ModifyDate=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from PKProductFactoryInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from PKProductFactoryInfo  where ID=?";

	public PKProductFactoryInfoSchema(){
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
		return new PKProductFactoryInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PKProductFactoryInfoSet();
	}

	public PKProductFactoryInfoSet query() {
		return query(null, -1, -1);
	}

	public PKProductFactoryInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PKProductFactoryInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PKProductFactoryInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PKProductFactoryInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){RiskCode = (String)v;return;}
		if (i == 2){AppFactorCode = (String)v;return;}
		if (i == 3){FactoryType = (String)v;return;}
		if (i == 4){FactoryName = (String)v;return;}
		if (i == 5){FactoryValue = (String)v;return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
		if (i == 8){Prop3 = (String)v;return;}
		if (i == 9){CreateDate = (Date)v;return;}
		if (i == 10){CreateUser = (String)v;return;}
		if (i == 11){ModifyDate = (Date)v;return;}
		if (i == 12){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RiskCode;}
		if (i == 2){return AppFactorCode;}
		if (i == 3){return FactoryType;}
		if (i == 4){return FactoryName;}
		if (i == 5){return FactoryValue;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		if (i == 8){return Prop3;}
		if (i == 9){return CreateDate;}
		if (i == 10){return CreateUser;}
		if (i == 11){return ModifyDate;}
		if (i == 12){return ModifyUser;}
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
	* 获取字段RiskCode的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRiskCode() {
		return RiskCode;
	}

	/**
	* 设置字段RiskCode的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRiskCode(String riskCode) {
		this.RiskCode = riskCode;
    }

	/**
	* 获取字段AppFactorCode的值，该字段的<br>
	* 字段名称 :投保要素编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAppFactorCode() {
		return AppFactorCode;
	}

	/**
	* 设置字段AppFactorCode的值，该字段的<br>
	* 字段名称 :投保要素编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAppFactorCode(String appFactorCode) {
		this.AppFactorCode = appFactorCode;
    }

	/**
	* 获取字段FactoryType的值，该字段的<br>
	* 字段名称 :投保要素类型<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFactoryType() {
		return FactoryType;
	}

	/**
	* 设置字段FactoryType的值，该字段的<br>
	* 字段名称 :投保要素类型<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFactoryType(String factoryType) {
		this.FactoryType = factoryType;
    }

	/**
	* 获取字段FactoryName的值，该字段的<br>
	* 字段名称 :投保要素名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFactoryName() {
		return FactoryName;
	}

	/**
	* 设置字段FactoryName的值，该字段的<br>
	* 字段名称 :投保要素名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFactoryName(String factoryName) {
		this.FactoryName = factoryName;
    }

	/**
	* 获取字段FactoryValue的值，该字段的<br>
	* 字段名称 :投保要素默认值<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFactoryValue() {
		return FactoryValue;
	}

	/**
	* 设置字段FactoryValue的值，该字段的<br>
	* 字段名称 :投保要素默认值<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFactoryValue(String factoryValue) {
		this.FactoryValue = factoryValue;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
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
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建者<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建者<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改者<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改者<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}