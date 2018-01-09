package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：预约栏目优惠券配置表<br>
 * 表代码：PrecontractCouponConfig<br>
 * 表主键：StencilUrl<br>
 */
public class PrecontractCouponConfigSchema extends Schema {
	private String StencilUrl;

	private String CouponBatch;

	private String Remark;

	private String Prop1;

	private String Prop2;

	private Date CreateDate;

	private String ModifyUser;

	private String CreateUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("StencilUrl", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("CouponBatch", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("Remark", DataColumn.STRING, 2, 300 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 8, 0 , 0 , false , false)
	};

	public static final String _TableCode = "PrecontractCouponConfig";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PrecontractCouponConfig values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PrecontractCouponConfig set StencilUrl=?,CouponBatch=?,Remark=?,Prop1=?,Prop2=?,CreateDate=?,ModifyUser=?,CreateUser=?,ModifyDate=? where StencilUrl=?";

	protected static final String _DeleteSQL = "delete from PrecontractCouponConfig  where StencilUrl=?";

	protected static final String _FillAllSQL = "select * from PrecontractCouponConfig  where StencilUrl=?";

	public PrecontractCouponConfigSchema(){
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
		return new PrecontractCouponConfigSchema();
	}

	protected SchemaSet newSet(){
		return new PrecontractCouponConfigSet();
	}

	public PrecontractCouponConfigSet query() {
		return query(null, -1, -1);
	}

	public PrecontractCouponConfigSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PrecontractCouponConfigSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PrecontractCouponConfigSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PrecontractCouponConfigSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){StencilUrl = (String)v;return;}
		if (i == 1){CouponBatch = (String)v;return;}
		if (i == 2){Remark = (String)v;return;}
		if (i == 3){Prop1 = (String)v;return;}
		if (i == 4){Prop2 = (String)v;return;}
		if (i == 5){CreateDate = (Date)v;return;}
		if (i == 6){ModifyUser = (String)v;return;}
		if (i == 7){CreateUser = (String)v;return;}
		if (i == 8){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return StencilUrl;}
		if (i == 1){return CouponBatch;}
		if (i == 2){return Remark;}
		if (i == 3){return Prop1;}
		if (i == 4){return Prop2;}
		if (i == 5){return CreateDate;}
		if (i == 6){return ModifyUser;}
		if (i == 7){return CreateUser;}
		if (i == 8){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段StencilUrl的值，该字段的<br>
	* 字段名称 :预约栏目Url<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getStencilUrl() {
		return StencilUrl;
	}

	/**
	* 设置字段StencilUrl的值，该字段的<br>
	* 字段名称 :预约栏目Url<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setStencilUrl(String stencilUrl) {
		this.StencilUrl = stencilUrl;
    }

	/**
	* 获取字段CouponBatch的值，该字段的<br>
	* 字段名称 :优惠券编号<br>
	* 数据类型 :VARCHAR(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCouponBatch() {
		return CouponBatch;
	}

	/**
	* 设置字段CouponBatch的值，该字段的<br>
	* 字段名称 :优惠券编号<br>
	* 数据类型 :VARCHAR(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCouponBatch(String couponBatch) {
		this.CouponBatch = couponBatch;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :VARCHAR(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :VARCHAR(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}