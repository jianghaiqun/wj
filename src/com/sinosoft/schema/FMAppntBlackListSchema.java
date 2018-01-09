package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：投保人黑名单表<br>
 * 表代码：FMAppntBlackList<br>
 * 表主键：ID<br>
 */
public class FMAppntBlackListSchema extends Schema {
	private String ID;

	private String appntCompanySn;

	private String appntCompanyName;

	private String ProductId;

	private String ProductName;

	private String applicantName;

	private String applicantAge;

	private String applicantSex;

	private String appntIDType;

	private String appntIDNo;

	private String Remark;

	private String createDate;

	private String prop1;

	private String prop2;

	private String prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 11 , 0 , true , true),
		new SchemaColumn("appntCompanySn", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("appntCompanyName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("ProductId", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("ProductName", DataColumn.STRING, 4, 150 , 0 , false , false),
		new SchemaColumn("applicantName", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("applicantAge", DataColumn.STRING, 6, 5 , 0 , false , false),
		new SchemaColumn("applicantSex", DataColumn.STRING, 7, 5 , 0 , false , false),
		new SchemaColumn("appntIDType", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("appntIDNo", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 10, 600 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.STRING, 11, 600 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 12, 600 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 13, 600 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 14, 600 , 0 , false , false)
	};

	public static final String _TableCode = "FMAppntBlackList";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into FMAppntBlackList values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update FMAppntBlackList set ID=?,appntCompanySn=?,appntCompanyName=?,ProductId=?,ProductName=?,applicantName=?,applicantAge=?,applicantSex=?,appntIDType=?,appntIDNo=?,Remark=?,createDate=?,prop1=?,prop2=?,prop3=? where ID=?";

	protected static final String _DeleteSQL = "delete from FMAppntBlackList  where ID=?";

	protected static final String _FillAllSQL = "select * from FMAppntBlackList  where ID=?";

	public FMAppntBlackListSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new FMAppntBlackListSchema();
	}

	protected SchemaSet newSet(){
		return new FMAppntBlackListSet();
	}

	public FMAppntBlackListSet query() {
		return query(null, -1, -1);
	}

	public FMAppntBlackListSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public FMAppntBlackListSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public FMAppntBlackListSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (FMAppntBlackListSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){appntCompanySn = (String)v;return;}
		if (i == 2){appntCompanyName = (String)v;return;}
		if (i == 3){ProductId = (String)v;return;}
		if (i == 4){ProductName = (String)v;return;}
		if (i == 5){applicantName = (String)v;return;}
		if (i == 6){applicantAge = (String)v;return;}
		if (i == 7){applicantSex = (String)v;return;}
		if (i == 8){appntIDType = (String)v;return;}
		if (i == 9){appntIDNo = (String)v;return;}
		if (i == 10){Remark = (String)v;return;}
		if (i == 11){createDate = (String)v;return;}
		if (i == 12){prop1 = (String)v;return;}
		if (i == 13){prop2 = (String)v;return;}
		if (i == 14){prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return appntCompanySn;}
		if (i == 2){return appntCompanyName;}
		if (i == 3){return ProductId;}
		if (i == 4){return ProductName;}
		if (i == 5){return applicantName;}
		if (i == 6){return applicantAge;}
		if (i == 7){return applicantSex;}
		if (i == 8){return appntIDType;}
		if (i == 9){return appntIDNo;}
		if (i == 10){return Remark;}
		if (i == 11){return createDate;}
		if (i == 12){return prop1;}
		if (i == 13){return prop2;}
		if (i == 14){return prop3;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段appntCompanySn的值，该字段的<br>
	* 字段名称 :投保公司编号<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappntCompanySn() {
		return appntCompanySn;
	}

	/**
	* 设置字段appntCompanySn的值，该字段的<br>
	* 字段名称 :投保公司编号<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappntCompanySn(String appntCompanySn) {
		this.appntCompanySn = appntCompanySn;
    }

	/**
	* 获取字段appntCompanyName的值，该字段的<br>
	* 字段名称 :投保公司名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappntCompanyName() {
		return appntCompanyName;
	}

	/**
	* 设置字段appntCompanyName的值，该字段的<br>
	* 字段名称 :投保公司名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappntCompanyName(String appntCompanyName) {
		this.appntCompanyName = appntCompanyName;
    }

	/**
	* 获取字段ProductId的值，该字段的<br>
	* 字段名称 :产品编号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductId() {
		return ProductId;
	}

	/**
	* 设置字段ProductId的值，该字段的<br>
	* 字段名称 :产品编号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductId(String productId) {
		this.ProductId = productId;
    }

	/**
	* 获取字段ProductName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductName() {
		return ProductName;
	}

	/**
	* 设置字段ProductName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductName(String productName) {
		this.ProductName = productName;
    }

	/**
	* 获取字段applicantName的值，该字段的<br>
	* 字段名称 :投保人姓名<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantName() {
		return applicantName;
	}

	/**
	* 设置字段applicantName的值，该字段的<br>
	* 字段名称 :投保人姓名<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantName(String applicantName) {
		this.applicantName = applicantName;
    }

	/**
	* 获取字段applicantAge的值，该字段的<br>
	* 字段名称 :投保人年龄<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantAge() {
		return applicantAge;
	}

	/**
	* 设置字段applicantAge的值，该字段的<br>
	* 字段名称 :投保人年龄<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantAge(String applicantAge) {
		this.applicantAge = applicantAge;
    }

	/**
	* 获取字段applicantSex的值，该字段的<br>
	* 字段名称 :投保人性别<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantSex() {
		return applicantSex;
	}

	/**
	* 设置字段applicantSex的值，该字段的<br>
	* 字段名称 :投保人性别<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantSex(String applicantSex) {
		this.applicantSex = applicantSex;
    }

	/**
	* 获取字段appntIDType的值，该字段的<br>
	* 字段名称 :投保人证件类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappntIDType() {
		return appntIDType;
	}

	/**
	* 设置字段appntIDType的值，该字段的<br>
	* 字段名称 :投保人证件类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappntIDType(String appntIDType) {
		this.appntIDType = appntIDType;
    }

	/**
	* 获取字段appntIDNo的值，该字段的<br>
	* 字段名称 :投保人证件号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappntIDNo() {
		return appntIDNo;
	}

	/**
	* 设置字段appntIDNo的值，该字段的<br>
	* 字段名称 :投保人证件号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappntIDNo(String appntIDNo) {
		this.appntIDNo = appntIDNo;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(String createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

}