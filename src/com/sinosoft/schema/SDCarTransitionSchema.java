package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：车险过渡表<br>
 * 表代码：SDCarTransition<br>
 * 表主键：ID<br>
 */
public class SDCarTransitionSchema extends Schema {
	private Integer ID;

	private String RecomUserCode;

	private String Address;

	private String PlateNo;

	private String InsuranceDate;

	private String CarOwner;

	private String ContactPhone;

	private String ContactEmail;

	private String CreateDate;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.INTEGER, 0, 20 , 0 , true , true),
		new SchemaColumn("RecomUserCode", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("PlateNo", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("InsuranceDate", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("CarOwner", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("ContactPhone", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("ContactEmail", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 150 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 150 , 0 , false , false)
	};

	public static final String _TableCode = "SDCarTransition";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDCarTransition values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDCarTransition set ID=?,RecomUserCode=?,Address=?,PlateNo=?,InsuranceDate=?,CarOwner=?,ContactPhone=?,ContactEmail=?,CreateDate=?,Prop1=?,Prop2=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDCarTransition  where ID=?";

	protected static final String _FillAllSQL = "select * from SDCarTransition  where ID=?";

	public SDCarTransitionSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new SDCarTransitionSchema();
	}

	protected SchemaSet newSet(){
		return new SDCarTransitionSet();
	}

	public SDCarTransitionSet query() {
		return query(null, -1, -1);
	}

	public SDCarTransitionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDCarTransitionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDCarTransitionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDCarTransitionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Integer(v.toString());}return;}
		if (i == 1){RecomUserCode = (String)v;return;}
		if (i == 2){Address = (String)v;return;}
		if (i == 3){PlateNo = (String)v;return;}
		if (i == 4){InsuranceDate = (String)v;return;}
		if (i == 5){CarOwner = (String)v;return;}
		if (i == 6){ContactPhone = (String)v;return;}
		if (i == 7){ContactEmail = (String)v;return;}
		if (i == 8){CreateDate = (String)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RecomUserCode;}
		if (i == 2){return Address;}
		if (i == 3){return PlateNo;}
		if (i == 4){return InsuranceDate;}
		if (i == 5){return CarOwner;}
		if (i == 6){return ContactPhone;}
		if (i == 7){return ContactEmail;}
		if (i == 8){return CreateDate;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :INT (20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public int getID() {
		if(ID==null){return 0;}
		return ID.intValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :INT (20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(int iD) {
		this.ID = new Integer(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :INT (20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Integer(iD);
    }

	/**
	* 获取字段RecomUserCode的值，该字段的<br>
	* 字段名称 :推荐人<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecomUserCode() {
		return RecomUserCode;
	}

	/**
	* 设置字段RecomUserCode的值，该字段的<br>
	* 字段名称 :推荐人<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecomUserCode(String recomUserCode) {
		this.RecomUserCode = recomUserCode;
    }

	/**
	* 获取字段Address的值，该字段的<br>
	* 字段名称 :地址<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* 设置字段Address的值，该字段的<br>
	* 字段名称 :地址<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* 获取字段PlateNo的值，该字段的<br>
	* 字段名称 :车牌号<br>
	* 数据类型 :VARCHAR (30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPlateNo() {
		return PlateNo;
	}

	/**
	* 设置字段PlateNo的值，该字段的<br>
	* 字段名称 :车牌号<br>
	* 数据类型 :VARCHAR (30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPlateNo(String plateNo) {
		this.PlateNo = plateNo;
    }

	/**
	* 获取字段InsuranceDate的值，该字段的<br>
	* 字段名称 :保险日期<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceDate() {
		return InsuranceDate;
	}

	/**
	* 设置字段InsuranceDate的值，该字段的<br>
	* 字段名称 :保险日期<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceDate(String insuranceDate) {
		this.InsuranceDate = insuranceDate;
    }

	/**
	* 获取字段CarOwner的值，该字段的<br>
	* 字段名称 :车主姓名<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCarOwner() {
		return CarOwner;
	}

	/**
	* 设置字段CarOwner的值，该字段的<br>
	* 字段名称 :车主姓名<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCarOwner(String carOwner) {
		this.CarOwner = carOwner;
    }

	/**
	* 获取字段ContactPhone的值，该字段的<br>
	* 字段名称 :联系电话<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContactPhone() {
		return ContactPhone;
	}

	/**
	* 设置字段ContactPhone的值，该字段的<br>
	* 字段名称 :联系电话<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContactPhone(String contactPhone) {
		this.ContactPhone = contactPhone;
    }

	/**
	* 获取字段ContactEmail的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :VARCHAR (50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContactEmail() {
		return ContactEmail;
	}

	/**
	* 设置字段ContactEmail的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :VARCHAR (50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContactEmail(String contactEmail) {
		this.ContactEmail = contactEmail;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :VARCHAR (150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :VARCHAR (150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :VARCHAR (150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :VARCHAR (150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}