package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：会员等级表<br>
 * 表代码：MemberGrade<br>
 * 表主键：id<br>
 */
public class MemberGradeSchema extends Schema {
	private String id;

	private String gradeCode;

	private String gradeName;

	private Integer grade;

	private String preGradeCode;

	private String orderCount;

	private String sumPrem;

	private String link;

	private String prop1;

	private String prop2;

	private String prop3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("gradeCode", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("gradeName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("grade", DataColumn.INTEGER, 3, 11 , 0 , false , false),
		new SchemaColumn("preGradeCode", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("orderCount", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("sumPrem", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("link", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 14, 0 , 0 , false , false)
	};

	public static final String _TableCode = "MemberGrade";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into MemberGrade values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update MemberGrade set id=?,gradeCode=?,gradeName=?,grade=?,preGradeCode=?,orderCount=?,sumPrem=?,link=?,prop1=?,prop2=?,prop3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from MemberGrade  where id=?";

	protected static final String _FillAllSQL = "select * from MemberGrade  where id=?";

	public MemberGradeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new MemberGradeSchema();
	}

	protected SchemaSet newSet(){
		return new MemberGradeSet();
	}

	public MemberGradeSet query() {
		return query(null, -1, -1);
	}

	public MemberGradeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public MemberGradeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public MemberGradeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (MemberGradeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){gradeCode = (String)v;return;}
		if (i == 2){gradeName = (String)v;return;}
		if (i == 3){if(v==null){grade = null;}else{grade = new Integer(v.toString());}return;}
		if (i == 4){preGradeCode = (String)v;return;}
		if (i == 5){orderCount = (String)v;return;}
		if (i == 6){sumPrem = (String)v;return;}
		if (i == 7){link = (String)v;return;}
		if (i == 8){prop1 = (String)v;return;}
		if (i == 9){prop2 = (String)v;return;}
		if (i == 10){prop3 = (String)v;return;}
		if (i == 11){createUser = (String)v;return;}
		if (i == 12){createDate = (Date)v;return;}
		if (i == 13){modifyUser = (String)v;return;}
		if (i == 14){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return gradeCode;}
		if (i == 2){return gradeName;}
		if (i == 3){return grade;}
		if (i == 4){return preGradeCode;}
		if (i == 5){return orderCount;}
		if (i == 6){return sumPrem;}
		if (i == 7){return link;}
		if (i == 8){return prop1;}
		if (i == 9){return prop2;}
		if (i == 10){return prop3;}
		if (i == 11){return createUser;}
		if (i == 12){return createDate;}
		if (i == 13){return modifyUser;}
		if (i == 14){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段gradeCode的值，该字段的<br>
	* 字段名称 :会员等级编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgradeCode() {
		return gradeCode;
	}

	/**
	* 设置字段gradeCode的值，该字段的<br>
	* 字段名称 :会员等级编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
    }

	/**
	* 获取字段gradeName的值，该字段的<br>
	* 字段名称 :会员等级名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	会员等级规则类型<br>
	*/
	public String getgradeName() {
		return gradeName;
	}

	/**
	* 设置字段gradeName的值，该字段的<br>
	* 字段名称 :会员等级名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	会员等级规则类型<br>
	*/
	public void setgradeName(String gradeName) {
		this.gradeName = gradeName;
    }

	/**
	* 获取字段grade的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	会员等级规则名称<br>
	*/
	public int getgrade() {
		if(grade==null){return 0;}
		return grade.intValue();
	}

	/**
	* 设置字段grade的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	会员等级规则名称<br>
	*/
	public void setgrade(int grade) {
		this.grade = new Integer(grade);
    }

	/**
	* 设置字段grade的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	会员等级规则名称<br>
	*/
	public void setgrade(String grade) {
		if (grade == null){
			this.grade = null;
			return;
		}
		this.grade = new Integer(grade);
    }

	/**
	* 获取字段preGradeCode的值，该字段的<br>
	* 字段名称 :前一会员等级编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpreGradeCode() {
		return preGradeCode;
	}

	/**
	* 设置字段preGradeCode的值，该字段的<br>
	* 字段名称 :前一会员等级编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpreGradeCode(String preGradeCode) {
		this.preGradeCode = preGradeCode;
    }

	/**
	* 获取字段orderCount的值，该字段的<br>
	* 字段名称 :升级所需有效订单数<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderCount() {
		return orderCount;
	}

	/**
	* 设置字段orderCount的值，该字段的<br>
	* 字段名称 :升级所需有效订单数<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderCount(String orderCount) {
		this.orderCount = orderCount;
    }

	/**
	* 获取字段sumPrem的值，该字段的<br>
	* 字段名称 :升级所需累计保费<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsumPrem() {
		return sumPrem;
	}

	/**
	* 设置字段sumPrem的值，该字段的<br>
	* 字段名称 :升级所需累计保费<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsumPrem(String sumPrem) {
		this.sumPrem = sumPrem;
    }

	/**
	* 获取字段link的值，该字段的<br>
	* 字段名称 :等级对应的链接<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getlink() {
		return link;
	}

	/**
	* 设置字段link的值，该字段的<br>
	* 字段名称 :等级对应的链接<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlink(String link) {
		this.link = link;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

}