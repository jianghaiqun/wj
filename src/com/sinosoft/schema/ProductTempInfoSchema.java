package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：产品模板关系表<br>
 * 表代码：ProductTempInfo<br>
 * 表主键：Id<br>
 */
public class ProductTempInfoSchema extends Schema {
	private String Id;

	private String ProductId;

	private String TemplateId;

	private Date MakeDate;

	private String Remark;

	private String ExcelTemplate;

	private String ExcelRemark;

	private String ExcelFlag;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ProductId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("TemplateId", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("ExcelTemplate", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("ExcelRemark", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("ExcelFlag", DataColumn.STRING, 7, 2 , 0 , false , false)
	};

	public static final String _TableCode = "ProductTempInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ProductTempInfo values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ProductTempInfo set Id=?,ProductId=?,TemplateId=?,MakeDate=?,Remark=?,ExcelTemplate=?,ExcelRemark=?,ExcelFlag=? where Id=?";

	protected static final String _DeleteSQL = "delete from ProductTempInfo  where Id=?";

	protected static final String _FillAllSQL = "select * from ProductTempInfo  where Id=?";

	public ProductTempInfoSchema(){
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
		return new ProductTempInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ProductTempInfoSet();
	}

	public ProductTempInfoSet query() {
		return query(null, -1, -1);
	}

	public ProductTempInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ProductTempInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ProductTempInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ProductTempInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ProductId = (String)v;return;}
		if (i == 2){TemplateId = (String)v;return;}
		if (i == 3){MakeDate = (Date)v;return;}
		if (i == 4){Remark = (String)v;return;}
		if (i == 5){ExcelTemplate = (String)v;return;}
		if (i == 6){ExcelRemark = (String)v;return;}
		if (i == 7){ExcelFlag = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ProductId;}
		if (i == 2){return TemplateId;}
		if (i == 3){return MakeDate;}
		if (i == 4){return Remark;}
		if (i == 5){return ExcelTemplate;}
		if (i == 6){return ExcelRemark;}
		if (i == 7){return ExcelFlag;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ProductId的值，该字段的<br>
	* 字段名称 :产品Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductId() {
		return ProductId;
	}

	/**
	* 设置字段ProductId的值，该字段的<br>
	* 字段名称 :产品Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductId(String productId) {
		this.ProductId = productId;
    }

	/**
	* 获取字段TemplateId的值，该字段的<br>
	* 字段名称 :大模板Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTemplateId() {
		return TemplateId;
	}

	/**
	* 设置字段TemplateId的值，该字段的<br>
	* 字段名称 :大模板Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTemplateId(String templateId) {
		this.TemplateId = templateId;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段ExcelTemplate的值，该字段的<br>
	* 字段名称 :Excel模板<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExcelTemplate() {
		return ExcelTemplate;
	}

	/**
	* 设置字段ExcelTemplate的值，该字段的<br>
	* 字段名称 :Excel模板<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExcelTemplate(String excelTemplate) {
		this.ExcelTemplate = excelTemplate;
    }

	/**
	* 获取字段ExcelRemark的值，该字段的<br>
	* 字段名称 :Excel说明<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExcelRemark() {
		return ExcelRemark;
	}

	/**
	* 设置字段ExcelRemark的值，该字段的<br>
	* 字段名称 :Excel说明<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExcelRemark(String excelRemark) {
		this.ExcelRemark = excelRemark;
    }

	/**
	* 获取字段ExcelFlag的值，该字段的<br>
	* 字段名称 :excel标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y:表示与配置产品excel模板<br>
	*/
	public String getExcelFlag() {
		return ExcelFlag;
	}

	/**
	* 设置字段ExcelFlag的值，该字段的<br>
	* 字段名称 :excel标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y:表示与配置产品excel模板<br>
	*/
	public void setExcelFlag(String excelFlag) {
		this.ExcelFlag = excelFlag;
    }

}