package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：邮件模板产品种类和品类配置<br>
 * 表代码：ZDMailMouldCategory<br>
 * 表主键：ID<br>
 */
public class ZDMailMouldCategorySchema extends Schema {
	private String ID;

	private String EmailType;

	private String ProductType;

	private String ProductCategory;

	private String Operator;

	private String CreateDate;

	private String ModifyDate;
	
	private String Prop1;
	
	private String Prop2;
	
	private String Prop3;
	
	private String Prop4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("EmailType", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("ProductType", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("ProductCategory", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 10, 50 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMailMouldCategory";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDMailMouldCategory values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMailMouldCategory set ID=?,EmailType=?,ProductType=?,ProductCategory=?,Operator=?,CreateDate=?,ModifyDate=?,Prop1=?,Prop2=?,Prop3=?,Prop4=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDMailMouldCategory  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDMailMouldCategory  where ID=?";

	public ZDMailMouldCategorySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new ZDMailMouldCategorySchema();
	}

	protected SchemaSet newSet(){
		return new ZDMailMouldCategorySet();
	}

	public ZDMailMouldCategorySet query() {
		return query(null, -1, -1);
	}

	public ZDMailMouldCategorySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMailMouldCategorySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMailMouldCategorySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMailMouldCategorySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){EmailType = (String)v;return;}
		if (i == 2){ProductType = (String)v;return;}
		if (i == 3){ProductCategory = (String)v;return;}
		if (i == 4){Operator = (String)v;return;}
		if (i == 5){CreateDate = (String)v;return;}
		if (i == 6){ModifyDate = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){Prop3 = (String)v;return;}
		if (i == 10){Prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return EmailType;}
		if (i == 2){return ProductType;}
		if (i == 3){return ProductCategory;}
		if (i == 4){return Operator;}
		if (i == 5){return CreateDate;}
		if (i == 6){return ModifyDate;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		if (i == 9){return Prop3;}
		if (i == 10){return Prop4;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :主键<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	主键<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :主键<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	主键<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段EmailType的值，该字段的<br>
	* 字段名称 :模板类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	模板类型<br>
	*/
	public String getEmailType() {
		return EmailType;
	}

	/**
	* 设置字段EmailType的值，该字段的<br>
	* 字段名称 :模板类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	模板类型<br>
	*/
	public void setEmailType(String emailType) {
		this.EmailType = emailType;
    }

	/**
	* 获取字段ProductType的值，该字段的<br>
	* 字段名称 :产品类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	产品类型<br>
	*/
	public String getProductType() {
		return ProductType;
	}

	/**
	* 设置字段ProductType的值，该字段的<br>
	* 字段名称 :产品类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	产品类型<br>
	*/
	public void setProductType(String productType) {
		this.ProductType = productType;
    }

	/**
	* 获取字段ProductCategory的值，该字段的<br>
	* 字段名称 :产品品类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	产品品类<br>
	*/
	public String getProductCategory() {
		return ProductCategory;
	}

	/**
	* 设置字段ProductCategory的值，该字段的<br>
	* 字段名称 :产品品类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	产品品类<br>
	*/
	public void setProductCategory(String productCategory) {
		this.ProductCategory = productCategory;
    }

	/**
	* 获取字段Operator的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	操作人<br>
	*/
	public String getOperator() {
		return Operator;
	}

	/**
	* 设置字段Operator的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	操作人<br>
	*/
	public void setOperator(String operator) {
		this.Operator = operator;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	创建日期<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	创建日期<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	修改日期<br>
	*/
	public String getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	修改日期<br>
	*/
	public void setModifyDate(String modifyDate) {
		this.ModifyDate = modifyDate;
    }

	public String getProp1() {
		return Prop1;
	}

	public void setProp1(String prop1) {
		Prop1 = prop1;
	}

	public String getProp2() {
		return Prop2;
	}

	public void setProp2(String prop2) {
		Prop2 = prop2;
	}

	public String getProp3() {
		return Prop3;
	}

	public void setProp3(String prop3) {
		Prop3 = prop3;
	}

	public String getProp4() {
		return Prop4;
	}

	public void setProp4(String prop4) {
		Prop4 = prop4;
	}

	
}