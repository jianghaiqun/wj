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
 * 表名称：促销产品亮点表<br>
 * 表代码：SDPromotionProductHighlights<br>
 * 表主键：id<br>
 */
public class SDPromotionProductHighlightsSchema extends Schema {
	private String id;

	private String ProductId;

	private String Detail1;

	private String Detail2;

	private Long OrderFlag;

	private String DetailId;

	private String Prop1;

	private String Prop2;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 255 , 0 , true , true),
		new SchemaColumn("ProductId", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("Detail1", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("Detail2", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 4, 20 , 0 , false , false),
		new SchemaColumn("DetailId", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 11, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDPromotionProductHighlights";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDPromotionProductHighlights values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDPromotionProductHighlights set id=?,ProductId=?,Detail1=?,Detail2=?,OrderFlag=?,DetailId=?,Prop1=?,Prop2=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from SDPromotionProductHighlights  where id=?";

	protected static final String _FillAllSQL = "select * from SDPromotionProductHighlights  where id=?";

	public SDPromotionProductHighlightsSchema(){
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
		return new SDPromotionProductHighlightsSchema();
	}

	protected SchemaSet newSet(){
		return new SDPromotionProductHighlightsSet();
	}

	public SDPromotionProductHighlightsSet query() {
		return query(null, -1, -1);
	}

	public SDPromotionProductHighlightsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDPromotionProductHighlightsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDPromotionProductHighlightsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDPromotionProductHighlightsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){ProductId = (String)v;return;}
		if (i == 2){Detail1 = (String)v;return;}
		if (i == 3){Detail2 = (String)v;return;}
		if (i == 4){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 5){DetailId = (String)v;return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
		if (i == 8){CreateUser = (String)v;return;}
		if (i == 9){CreateDate = (Date)v;return;}
		if (i == 10){ModifyUser = (String)v;return;}
		if (i == 11){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return ProductId;}
		if (i == 2){return Detail1;}
		if (i == 3){return Detail2;}
		if (i == 4){return OrderFlag;}
		if (i == 5){return DetailId;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		if (i == 8){return CreateUser;}
		if (i == 9){return CreateDate;}
		if (i == 10){return ModifyUser;}
		if (i == 11){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段ProductId的值，该字段的<br>
	* 字段名称 :产品id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductId() {
		return ProductId;
	}

	/**
	* 设置字段ProductId的值，该字段的<br>
	* 字段名称 :产品id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductId(String productId) {
		this.ProductId = productId;
    }

	/**
	* 获取字段Detail1的值，该字段的<br>
	* 字段名称 :产品亮点描述1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDetail1() {
		return Detail1;
	}

	/**
	* 设置字段Detail1的值，该字段的<br>
	* 字段名称 :产品亮点描述1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDetail1(String detail1) {
		this.Detail1 = detail1;
    }

	/**
	* 获取字段Detail2的值，该字段的<br>
	* 字段名称 :产品亮点描述2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDetail2() {
		return Detail2;
	}

	/**
	* 设置字段Detail2的值，该字段的<br>
	* 字段名称 :产品亮点描述2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDetail2(String detail2) {
		this.Detail2 = detail2;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序<br>
	* 数据类型 :bigint(20)<br>
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
	* 获取字段DetailId的值，该字段的<br>
	* 字段名称 :促销详细产品表Id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDetailId() {
		return DetailId;
	}

	/**
	* 设置字段DetailId的值，该字段的<br>
	* 字段名称 :促销详细产品表Id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDetailId(String detailId) {
		this.DetailId = detailId;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
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
	* 字段名称 :创建日<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :更新人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :更新人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :更新日<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :更新日<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}