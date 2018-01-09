package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：产品投保模板表<br>
 * 表代码：ProductToTemplate<br>
 * 表主键：Id<br>
 */
public class ProductToTemplateSchema extends Schema {
	private String Id;

	private String FactorId;

	private String FactorName;

	private String TemplateCode;

	private Long OrderFlag;

	private String Memo;

	private Date CreateDate;

	private Date ModifyDate;

	private String Remark1;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("FactorId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("FactorName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("TemplateCode", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("Remark1", DataColumn.STRING, 8, 30 , 0 , false , false)
	};

	public static final String _TableCode = "ProductToTemplate";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ProductToTemplate values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ProductToTemplate set Id=?,FactorId=?,FactorName=?,TemplateCode=?,OrderFlag=?,Memo=?,CreateDate=?,ModifyDate=?,Remark1=? where Id=?";

	protected static final String _DeleteSQL = "delete from ProductToTemplate  where Id=?";

	protected static final String _FillAllSQL = "select * from ProductToTemplate  where Id=?";

	public ProductToTemplateSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new ProductToTemplateSchema();
	}

	protected SchemaSet newSet(){
		return new ProductToTemplateSet();
	}

	public ProductToTemplateSet query() {
		return query(null, -1, -1);
	}

	public ProductToTemplateSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ProductToTemplateSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ProductToTemplateSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ProductToTemplateSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){FactorId = (String)v;return;}
		if (i == 2){FactorName = (String)v;return;}
		if (i == 3){TemplateCode = (String)v;return;}
		if (i == 4){OrderFlag = (Long)v;return;}
		if (i == 5){Memo = (String)v;return;}
		if (i == 6){CreateDate = (Date)v;return;}
		if (i == 7){ModifyDate = (Date)v;return;}
		if (i == 8){Remark1 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return FactorId;}
		if (i == 2){return FactorName;}
		if (i == 3){return TemplateCode;}
		if (i == 4){return OrderFlag;}
		if (i == 5){return Memo;}
		if (i == 6){return CreateDate;}
		if (i == 7){return ModifyDate;}
		if (i == 8){return Remark1;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段FactorId的值，该字段的<br>
	* 字段名称 :要素Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFactorId() {
		return FactorId;
	}

	/**
	* 设置字段FactorId的值，该字段的<br>
	* 字段名称 :要素Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFactorId(String factorId) {
		this.FactorId = factorId;
    }

	/**
	* 获取字段FactorName的值，该字段的<br>
	* 字段名称 :要素名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFactorName() {
		return FactorName;
	}

	/**
	* 设置字段FactorName的值，该字段的<br>
	* 字段名称 :要素名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFactorName(String factorName) {
		this.FactorName = factorName;
    }

	/**
	* 获取字段TemplateCode的值，该字段的<br>
	* 字段名称 :模板编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	根据模板编码定位唯一模板<br>
	*/
	public String getTemplateCode() {
		return TemplateCode;
	}

	/**
	* 设置字段TemplateCode的值，该字段的<br>
	* 字段名称 :模板编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	根据模板编码定位唯一模板<br>
	*/
	public void setTemplateCode(String templateCode) {
		this.TemplateCode = templateCode;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :模版排序<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :模版排序<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :模版排序<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
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

	/**
	* 获取字段Remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark1() {
		return Remark1;
	}

	/**
	* 设置字段Remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark1(String remark1) {
		this.Remark1 = remark1;
    }

}