package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：活动产品关联表<br>
 * 表代码：SdProductActivityLink<br>
 * 表主键：Id<br>
 */
public class SdProductActivityLinkSchema extends Schema {
	private Integer Id;

	private String ProductId;

	private String ActivitySn;

	private String ActivityChannel;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.INTEGER, 0, 0 , 0 , true , true),
		new SchemaColumn("ProductId", DataColumn.STRING, 1, 20 , 0 , true , false),
		new SchemaColumn("ActivitySn", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("ActivityChannel", DataColumn.STRING, 3, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SdProductActivityLink";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SdProductActivityLink values(?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SdProductActivityLink set Id=?,ProductId=?,ActivitySn=?,ActivityChannel=? where Id=?";

	protected static final String _DeleteSQL = "delete from SdProductActivityLink  where Id=?";

	protected static final String _FillAllSQL = "select * from SdProductActivityLink  where Id=?";

	public SdProductActivityLinkSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[4];
	}

	protected Schema newInstance(){
		return new SdProductActivityLinkSchema();
	}

	protected SchemaSet newSet(){
		return new SdProductActivityLinkSet();
	}

	public SdProductActivityLinkSet query() {
		return query(null, -1, -1);
	}

	public SdProductActivityLinkSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SdProductActivityLinkSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SdProductActivityLinkSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SdProductActivityLinkSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){Id = null;}else{Id = new Integer(v.toString());}return;}
		if (i == 1){ProductId = (String)v;return;}
		if (i == 2){ActivitySn = (String)v;return;}
		if (i == 3){ActivityChannel = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ProductId;}
		if (i == 2){return ActivitySn;}
		if (i == 3){return ActivityChannel;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public int getId() {
		if(Id==null){return 0;}
		return Id.intValue();
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(int id) {
		this.Id = new Integer(id);
    }

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		if (id == null){
			this.Id = null;
			return;
		}
		this.Id = new Integer(id);
    }

	/**
	* 获取字段ProductId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProductId() {
		return ProductId;
	}

	/**
	* 设置字段ProductId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProductId(String productId) {
		this.ProductId = productId;
    }

	/**
	* 获取字段ActivitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getActivitySn() {
		return ActivitySn;
	}

	/**
	* 设置字段ActivitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setActivitySn(String activitySn) {
		this.ActivitySn = activitySn;
    }

	/**
	* 获取字段ActivityChannel的值，该字段的<br>
	* 字段名称 :活动渠道<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getActivityChannel() {
		return ActivityChannel;
	}

	/**
	* 设置字段ActivityChannel的值，该字段的<br>
	* 字段名称 :活动渠道<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActivityChannel(String activityChannel) {
		this.ActivityChannel = activityChannel;
    }

}