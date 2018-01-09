package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：SDTbsdInsured<br>
 * 表代码：SDTbsdInsured<br>
 * 表主键：ID<br>
 */
public class SDTbsdInsuredSchema extends Schema {
	private Long ID;

	private String Name;

	private String PassportId;

	private String Mobile;

	private String Email;

	private Long InsertBatch;

	private Date CreateDate;

	private String CreateUser;

	private Date ModifyDate;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 50 , 0 , true , false),
		new SchemaColumn("PassportId", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 3, 13 , 0 , false , false),
		new SchemaColumn("Email", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("InsertBatch", DataColumn.LONG, 5, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 9, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDTbsdInsured";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDTbsdInsured values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDTbsdInsured set ID=?,Name=?,PassportId=?,Mobile=?,Email=?,InsertBatch=?,CreateDate=?,CreateUser=?,ModifyDate=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDTbsdInsured  where ID=?";

	protected static final String _FillAllSQL = "select * from SDTbsdInsured  where ID=?";

	public SDTbsdInsuredSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new SDTbsdInsuredSchema();
	}

	protected SchemaSet newSet(){
		return new SDTbsdInsuredSet();
	}

	public SDTbsdInsuredSet query() {
		return query(null, -1, -1);
	}

	public SDTbsdInsuredSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDTbsdInsuredSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDTbsdInsuredSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDTbsdInsuredSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){PassportId = (String)v;return;}
		if (i == 3){Mobile = (String)v;return;}
		if (i == 4){Email = (String)v;return;}
		if (i == 5){if(v==null){InsertBatch = null;}else{InsertBatch = new Long(v.toString());}return;}
		if (i == 6){CreateDate = (Date)v;return;}
		if (i == 7){CreateUser = (String)v;return;}
		if (i == 8){ModifyDate = (Date)v;return;}
		if (i == 9){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return PassportId;}
		if (i == 3){return Mobile;}
		if (i == 4){return Email;}
		if (i == 5){return InsertBatch;}
		if (i == 6){return CreateDate;}
		if (i == 7){return CreateUser;}
		if (i == 8){return ModifyDate;}
		if (i == 9){return ModifyUser;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段PassportId的值，该字段的<br>
	* 字段名称 :PassportId<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPassportId() {
		return PassportId;
	}

	/**
	* 设置字段PassportId的值，该字段的<br>
	* 字段名称 :PassportId<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPassportId(String passportId) {
		this.PassportId = passportId;
    }

	/**
	* 获取字段Mobile的值，该字段的<br>
	* 字段名称 :Mobile<br>
	* 数据类型 :varchar(13)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* 设置字段Mobile的值，该字段的<br>
	* 字段名称 :Mobile<br>
	* 数据类型 :varchar(13)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* 获取字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* 设置字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
    }

	/**
	* 获取字段InsertBatch的值，该字段的<br>
	* 字段名称 :InsertBatch<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getInsertBatch() {
		if(InsertBatch==null){return 0;}
		return InsertBatch.longValue();
	}

	/**
	* 设置字段InsertBatch的值，该字段的<br>
	* 字段名称 :InsertBatch<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsertBatch(long insertBatch) {
		this.InsertBatch = new Long(insertBatch);
    }

	/**
	* 设置字段InsertBatch的值，该字段的<br>
	* 字段名称 :InsertBatch<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsertBatch(String insertBatch) {
		if (insertBatch == null){
			this.InsertBatch = null;
			return;
		}
		this.InsertBatch = new Long(insertBatch);
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :CreateUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :CreateUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}