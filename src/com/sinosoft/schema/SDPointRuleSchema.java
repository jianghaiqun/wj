package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：积分规则表<br>
 * 表代码：SDPointRule<br>
 * 表主键：ID<br>
 */
public class SDPointRuleSchema extends Schema {
	private Integer ID;

	private String MemberAct;

	private String MemberGrade;

	private String PointsGive;

	private String StartDate;

	private String EndDate;

	private String Status;

	private String PointsNum;

	private String PointDes;

	private String prop1;

	private String prop2;

	private String prop3;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.INTEGER, 0, 0 , 0 , true , true),
		new SchemaColumn("MemberAct", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("MemberGrade", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("PointsGive", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("StartDate", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("EndDate", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("PointsNum", DataColumn.STRING, 7, 40 , 0 , false , false),
		new SchemaColumn("PointDes", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 15, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDPointRule";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDPointRule values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDPointRule set ID=?,MemberAct=?,MemberGrade=?,PointsGive=?,StartDate=?,EndDate=?,Status=?,PointsNum=?,PointDes=?,prop1=?,prop2=?,prop3=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDPointRule  where ID=?";

	protected static final String _FillAllSQL = "select * from SDPointRule  where ID=?";

	public SDPointRuleSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[16];
	}

	protected Schema newInstance(){
		return new SDPointRuleSchema();
	}

	protected SchemaSet newSet(){
		return new SDPointRuleSet();
	}

	public SDPointRuleSet query() {
		return query(null, -1, -1);
	}

	public SDPointRuleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDPointRuleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDPointRuleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDPointRuleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Integer(v.toString());}return;}
		if (i == 1){MemberAct = (String)v;return;}
		if (i == 2){MemberGrade = (String)v;return;}
		if (i == 3){PointsGive = (String)v;return;}
		if (i == 4){StartDate = (String)v;return;}
		if (i == 5){EndDate = (String)v;return;}
		if (i == 6){Status = (String)v;return;}
		if (i == 7){PointsNum = (String)v;return;}
		if (i == 8){PointDes = (String)v;return;}
		if (i == 9){prop1 = (String)v;return;}
		if (i == 10){prop2 = (String)v;return;}
		if (i == 11){prop3 = (String)v;return;}
		if (i == 12){CreateUser = (String)v;return;}
		if (i == 13){CreateDate = (Date)v;return;}
		if (i == 14){ModifyUser = (String)v;return;}
		if (i == 15){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return MemberAct;}
		if (i == 2){return MemberGrade;}
		if (i == 3){return PointsGive;}
		if (i == 4){return StartDate;}
		if (i == 5){return EndDate;}
		if (i == 6){return Status;}
		if (i == 7){return PointsNum;}
		if (i == 8){return PointDes;}
		if (i == 9){return prop1;}
		if (i == 10){return prop2;}
		if (i == 11){return prop3;}
		if (i == 12){return CreateUser;}
		if (i == 13){return CreateDate;}
		if (i == 14){return ModifyUser;}
		if (i == 15){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
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
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(int iD) {
		this.ID = new Integer(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
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
	* 获取字段MemberAct的值，该字段的<br>
	* 字段名称 :会员行为<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberAct() {
		return MemberAct;
	}

	/**
	* 设置字段MemberAct的值，该字段的<br>
	* 字段名称 :会员行为<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberAct(String memberAct) {
		this.MemberAct = memberAct;
    }

	/**
	* 获取字段MemberGrade的值，该字段的<br>
	* 字段名称 :会员等级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberGrade() {
		return MemberGrade;
	}

	/**
	* 设置字段MemberGrade的值，该字段的<br>
	* 字段名称 :会员等级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberGrade(String memberGrade) {
		this.MemberGrade = memberGrade;
    }

	/**
	* 获取字段PointsGive的值，该字段的<br>
	* 字段名称 :积分赠送规则<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPointsGive() {
		return PointsGive;
	}

	/**
	* 设置字段PointsGive的值，该字段的<br>
	* 字段名称 :积分赠送规则<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPointsGive(String pointsGive) {
		this.PointsGive = pointsGive;
    }

	/**
	* 获取字段StartDate的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStartDate() {
		return StartDate;
	}

	/**
	* 设置字段StartDate的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStartDate(String startDate) {
		this.StartDate = startDate;
    }

	/**
	* 获取字段EndDate的值，该字段的<br>
	* 字段名称 :结束时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEndDate() {
		return EndDate;
	}

	/**
	* 设置字段EndDate的值，该字段的<br>
	* 字段名称 :结束时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEndDate(String endDate) {
		this.EndDate = endDate;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段PointsNum的值，该字段的<br>
	* 字段名称 :积分数<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPointsNum() {
		return PointsNum;
	}

	/**
	* 设置字段PointsNum的值，该字段的<br>
	* 字段名称 :积分数<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPointsNum(String pointsNum) {
		this.PointsNum = pointsNum;
    }

	/**
	* 获取字段PointDes的值，该字段的<br>
	* 字段名称 :文案描述<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPointDes() {
		return PointDes;
	}

	/**
	* 设置字段PointDes的值，该字段的<br>
	* 字段名称 :文案描述<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPointDes(String pointDes) {
		this.PointDes = pointDes;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
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
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人员<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人员<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
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

}