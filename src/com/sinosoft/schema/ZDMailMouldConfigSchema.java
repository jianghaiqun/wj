package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：邮件模板配置表<br>
 * 表代码：ZDMailMouldConfig<br>
 * 表主键：EmailType<br>
 */
public class ZDMailMouldConfigSchema extends Schema {
	private String EmailType;

	private String ActivityFlag;

	private String ProductFlag;

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
		new SchemaColumn("EmailType", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("ActivityFlag", DataColumn.STRING, 1, 1 , 0 , false , false),
		new SchemaColumn("ProductFlag", DataColumn.STRING, 2, 1 , 0 , false , false),
		new SchemaColumn("ProductCategory", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("Product", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 11, 200 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMailMouldConfig";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDMailMouldConfig values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMailMouldConfig set EmailType=?,ActivityFlag=?,ProductFlag=?,ProductCategory=?,Product=?,Operator=?,CreateDate=?,ModifyDate=?,Prop1=?,Prop2=?,Prop3=?,Prop4=? where EmailType=?";

	protected static final String _DeleteSQL = "delete from ZDMailMouldConfig  where EmailType=?";

	protected static final String _FillAllSQL = "select * from ZDMailMouldConfig  where EmailType=?";

	public ZDMailMouldConfigSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new ZDMailMouldConfigSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMailMouldConfigSet();
	}

	public ZDMailMouldConfigSet query() {
		return query(null, -1, -1);
	}

	public ZDMailMouldConfigSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMailMouldConfigSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMailMouldConfigSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMailMouldConfigSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){EmailType = (String)v;return;}
		if (i == 1){ActivityFlag = (String)v;return;}
		if (i == 2){ProductFlag = (String)v;return;}
		if (i == 3){ProductCategory = (String)v;return;}
		if (i == 4){Product = (String)v;return;}
		if (i == 5){Operator = (String)v;return;}
		if (i == 6){CreateDate = (String)v;return;}
		if (i == 7){ModifyDate = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){Prop3 = (String)v;return;}
		if (i == 11){Prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return EmailType;}
		if (i == 1){return ActivityFlag;}
		if (i == 2){return ProductFlag;}
		if (i == 3){return ProductCategory;}
		if (i == 4){return Product;}
		if (i == 5){return Operator;}
		if (i == 6){return CreateDate;}
		if (i == 7){return ModifyDate;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return Prop3;}
		if (i == 11){return Prop4;}
		return null;
	}

	/**
	* 获取字段EmailType的值，该字段的<br>
	* 字段名称 :邮件模版类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getEmailType() {
		return EmailType;
	}

	/**
	* 设置字段EmailType的值，该字段的<br>
	* 字段名称 :邮件模版类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setEmailType(String emailType) {
		this.EmailType = emailType;
    }

	/**
	* 获取字段ActivityFlag的值，该字段的<br>
	* 字段名称 :是否启用推荐活动<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y 启动 N 禁用<br>
	*/
	public String getActivityFlag() {
		return ActivityFlag;
	}

	/**
	* 设置字段ActivityFlag的值，该字段的<br>
	* 字段名称 :是否启用推荐活动<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y 启动 N 禁用<br>
	*/
	public void setActivityFlag(String activityFlag) {
		this.ActivityFlag = activityFlag;
    }

	/**
	* 获取字段ProductFlag的值，该字段的<br>
	* 字段名称 :是否启用推荐产品<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y 启用 N 禁用<br>
	*/
	public String getProductFlag() {
		return ProductFlag;
	}

	/**
	* 设置字段ProductFlag的值，该字段的<br>
	* 字段名称 :是否启用推荐产品<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y 启用 N 禁用<br>
	*/
	public void setProductFlag(String productFlag) {
		this.ProductFlag = productFlag;
    }

	/**
	* 获取字段ProductCategory的值，该字段的<br>
	* 字段名称 :推荐产品类别<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductCategory() {
		return ProductCategory;
	}

	/**
	* 设置字段ProductCategory的值，该字段的<br>
	* 字段名称 :推荐产品类别<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductCategory(String productCategory) {
		this.ProductCategory = productCategory;
    }

	/**
	* 获取字段Product的值，该字段的<br>
	* 字段名称 :推荐产品<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	推荐产品<br>
	*/
	public String getProduct() {
		return Product;
	}

	/**
	* 设置字段Product的值，该字段的<br>
	* 字段名称 :推荐产品<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	推荐产品<br>
	*/
	public void setProduct(String product) {
		this.Product = product;
    }

	/**
	* 获取字段Operator的值，该字段的<br>
	* 字段名称 :操作员<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperator() {
		return Operator;
	}

	/**
	* 设置字段Operator的值，该字段的<br>
	* 字段名称 :操作员<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperator(String operator) {
		this.Operator = operator;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(String modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

}