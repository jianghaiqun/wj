package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：招聘岗位信息<br>
 * 表代码：JobsInfo<br>
 * 表主键：id<br>
 */
public class JobsInfoSchema extends Schema {
	private String id;

	private String jobsName;

	private String jobsType;

	private String jobsProperty;

	private String jobsAddress;

	private String jobsDuty;

	private String jobsRequirement;

	private String isPublish;

	private Date publishDate;

	private String hrEmail;

	private Long orderFlag;

	private String prop1;

	private String prop2;

	private String prop3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("jobsName", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("jobsType", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("jobsProperty", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("jobsAddress", DataColumn.STRING, 4, 1000 , 0 , false , false),
		new SchemaColumn("jobsDuty", DataColumn.CLOB, 5, 0 , 0 , false , false),
		new SchemaColumn("jobsRequirement", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("isPublish", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("publishDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("hrEmail", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("orderFlag", DataColumn.LONG, 10, 20 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 17, 0 , 0 , false , false)
	};

	public static final String _TableCode = "JobsInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into JobsInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update JobsInfo set id=?,jobsName=?,jobsType=?,jobsProperty=?,jobsAddress=?,jobsDuty=?,jobsRequirement=?,isPublish=?,publishDate=?,hrEmail=?,orderFlag=?,prop1=?,prop2=?,prop3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from JobsInfo  where id=?";

	protected static final String _FillAllSQL = "select * from JobsInfo  where id=?";

	public JobsInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new JobsInfoSchema();
	}

	protected SchemaSet newSet(){
		return new JobsInfoSet();
	}

	public JobsInfoSet query() {
		return query(null, -1, -1);
	}

	public JobsInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public JobsInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public JobsInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (JobsInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){jobsName = (String)v;return;}
		if (i == 2){jobsType = (String)v;return;}
		if (i == 3){jobsProperty = (String)v;return;}
		if (i == 4){jobsAddress = (String)v;return;}
		if (i == 5){jobsDuty = (String)v;return;}
		if (i == 6){jobsRequirement = (String)v;return;}
		if (i == 7){isPublish = (String)v;return;}
		if (i == 8){publishDate = (Date)v;return;}
		if (i == 9){hrEmail = (String)v;return;}
		if (i == 10){if(v==null){orderFlag = null;}else{orderFlag = new Long(v.toString());}return;}
		if (i == 11){prop1 = (String)v;return;}
		if (i == 12){prop2 = (String)v;return;}
		if (i == 13){prop3 = (String)v;return;}
		if (i == 14){createUser = (String)v;return;}
		if (i == 15){createDate = (Date)v;return;}
		if (i == 16){modifyUser = (String)v;return;}
		if (i == 17){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return jobsName;}
		if (i == 2){return jobsType;}
		if (i == 3){return jobsProperty;}
		if (i == 4){return jobsAddress;}
		if (i == 5){return jobsDuty;}
		if (i == 6){return jobsRequirement;}
		if (i == 7){return isPublish;}
		if (i == 8){return publishDate;}
		if (i == 9){return hrEmail;}
		if (i == 10){return orderFlag;}
		if (i == 11){return prop1;}
		if (i == 12){return prop2;}
		if (i == 13){return prop3;}
		if (i == 14){return createUser;}
		if (i == 15){return createDate;}
		if (i == 16){return modifyUser;}
		if (i == 17){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段jobsName的值，该字段的<br>
	* 字段名称 :岗位名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getjobsName() {
		return jobsName;
	}

	/**
	* 设置字段jobsName的值，该字段的<br>
	* 字段名称 :岗位名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setjobsName(String jobsName) {
		this.jobsName = jobsName;
    }

	/**
	* 获取字段jobsType的值，该字段的<br>
	* 字段名称 :类别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getjobsType() {
		return jobsType;
	}

	/**
	* 设置字段jobsType的值，该字段的<br>
	* 字段名称 :类别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setjobsType(String jobsType) {
		this.jobsType = jobsType;
    }

	/**
	* 获取字段jobsProperty的值，该字段的<br>
	* 字段名称 :工作性质<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getjobsProperty() {
		return jobsProperty;
	}

	/**
	* 设置字段jobsProperty的值，该字段的<br>
	* 字段名称 :工作性质<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setjobsProperty(String jobsProperty) {
		this.jobsProperty = jobsProperty;
    }

	/**
	* 获取字段jobsAddress的值，该字段的<br>
	* 字段名称 :工作地点<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getjobsAddress() {
		return jobsAddress;
	}

	/**
	* 设置字段jobsAddress的值，该字段的<br>
	* 字段名称 :工作地点<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setjobsAddress(String jobsAddress) {
		this.jobsAddress = jobsAddress;
    }

	/**
	* 获取字段jobsDuty的值，该字段的<br>
	* 字段名称 :岗位职责<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getjobsDuty() {
		return jobsDuty;
	}

	/**
	* 设置字段jobsDuty的值，该字段的<br>
	* 字段名称 :岗位职责<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setjobsDuty(String jobsDuty) {
		this.jobsDuty = jobsDuty;
    }

	/**
	* 获取字段jobsRequirement的值，该字段的<br>
	* 字段名称 :任职要求<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getjobsRequirement() {
		return jobsRequirement;
	}

	/**
	* 设置字段jobsRequirement的值，该字段的<br>
	* 字段名称 :任职要求<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setjobsRequirement(String jobsRequirement) {
		this.jobsRequirement = jobsRequirement;
    }

	/**
	* 获取字段isPublish的值，该字段的<br>
	* 字段名称 :是否发布<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisPublish() {
		return isPublish;
	}

	/**
	* 设置字段isPublish的值，该字段的<br>
	* 字段名称 :是否发布<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisPublish(String isPublish) {
		this.isPublish = isPublish;
    }

	/**
	* 获取字段publishDate的值，该字段的<br>
	* 字段名称 :发布时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getpublishDate() {
		return publishDate;
	}

	/**
	* 设置字段publishDate的值，该字段的<br>
	* 字段名称 :发布时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpublishDate(Date publishDate) {
		this.publishDate = publishDate;
    }

	/**
	* 获取字段hrEmail的值，该字段的<br>
	* 字段名称 :hr邮箱<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethrEmail() {
		return hrEmail;
	}

	/**
	* 设置字段hrEmail的值，该字段的<br>
	* 字段名称 :hr邮箱<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethrEmail(String hrEmail) {
		this.hrEmail = hrEmail;
    }

	/**
	* 获取字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getorderFlag() {
		if(orderFlag==null){return 0;}
		return orderFlag.longValue();
	}

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(long orderFlag) {
		this.orderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(String orderFlag) {
		if (orderFlag == null){
			this.orderFlag = null;
			return;
		}
		this.orderFlag = new Long(orderFlag);
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