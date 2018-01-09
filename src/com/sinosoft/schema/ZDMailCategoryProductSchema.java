package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：邮件模板产品品类和产品对应关系表<br>
 * 表代码：ZDMailCategoryProduct<br>
 * 表主键：ID<br>
 */
public class ZDMailCategoryProductSchema extends Schema {
	private Long ID;

	private String ProductCategory;

	private String Product;

	private String Operator;

	private String CreateDate;

	private String ModifyDate;
	
	private String Prop1;
	
	private String Prop2;
	
	private String Prop3;
	
	private String Prop4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , false , true),
		new SchemaColumn("ProductCategory", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("Product", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 9, 50 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMailCategoryProduct";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDMailCategoryProduct values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMailCategoryProduct set ID=?,ProductCategory=?,Product=?,Operator=?,CreateDate=?,ModifyDate=?,Prop1=?,Prop2=?,Prop3=?,Prop4=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDMailCategoryProduct  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDMailCategoryProduct  where ID=?";

	public ZDMailCategoryProductSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[6];
	}

	protected Schema newInstance(){
		return new ZDMailCategoryProductSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMailCategoryProductSet();
	}

	public ZDMailCategoryProductSet query() {
		return query(null, -1, -1);
	}

	public ZDMailCategoryProductSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMailCategoryProductSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMailCategoryProductSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMailCategoryProductSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){ProductCategory = (String)v;return;}
		if (i == 2){Product = (String)v;return;}
		if (i == 3){Operator = (String)v;return;}
		if (i == 4){CreateDate = (String)v;return;}
		if (i == 5){ModifyDate = (String)v;return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
		if (i == 8){Prop3 = (String)v;return;}
		if (i == 9){Prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ProductCategory;}
		if (i == 2){return Product;}
		if (i == 3){return Operator;}
		if (i == 4){return CreateDate;}
		if (i == 5){return ModifyDate;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		if (i == 8){return Prop3;}
		if (i == 9){return Prop4;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :主键<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	主键<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :主键<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	主键<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :主键<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	主键<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
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
	* 获取字段Product的值，该字段的<br>
	* 字段名称 :产品<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	产品<br>
	*/
	public String getProduct() {
		return Product;
	}

	/**
	* 设置字段Product的值，该字段的<br>
	* 字段名称 :产品<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	产品<br>
	*/
	public void setProduct(String product) {
		this.Product = product;
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