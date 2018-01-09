package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：理赔频道首次点击记录<br>
 * 表代码：SDLipeiFirstClick<br>
 * 表主键：Id<br>
 */
public class SDLipeiFirstClickSchema extends Schema {
	private String Id;

	private String MemberId;

	private Date CreateDate;

	private Date ModifyDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MemberId", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 6, 100 , 0 , false , false)
	};

	public static final String _TableCode = "SDLipeiFirstClick";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDLipeiFirstClick values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDLipeiFirstClick set Id=?,MemberId=?,CreateDate=?,ModifyDate=?,Prop1=?,Prop2=?,Prop3=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDLipeiFirstClick  where Id=?";

	protected static final String _FillAllSQL = "select * from SDLipeiFirstClick  where Id=?";

	public SDLipeiFirstClickSchema(){
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
		return new SDLipeiFirstClickSchema();
	}

	protected SchemaSet newSet(){
		return new SDLipeiFirstClickSet();
	}

	public SDLipeiFirstClickSet query() {
		return query(null, -1, -1);
	}

	public SDLipeiFirstClickSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDLipeiFirstClickSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDLipeiFirstClickSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDLipeiFirstClickSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){MemberId = (String)v;return;}
		if (i == 2){CreateDate = (Date)v;return;}
		if (i == 3){ModifyDate = (Date)v;return;}
		if (i == 4){Prop1 = (String)v;return;}
		if (i == 5){Prop2 = (String)v;return;}
		if (i == 6){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return MemberId;}
		if (i == 2){return CreateDate;}
		if (i == 3){return ModifyDate;}
		if (i == 4){return Prop1;}
		if (i == 5){return Prop2;}
		if (i == 6){return Prop3;}
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
	* 获取字段MemberId的值，该字段的<br>
	* 字段名称 :会员号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberId() {
		return MemberId;
	}

	/**
	* 设置字段MemberId的值，该字段的<br>
	* 字段名称 :会员号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberId(String memberId) {
		this.MemberId = memberId;
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
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}