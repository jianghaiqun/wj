package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：部署配置表<br>
 * 表代码：ZCDeployConfig<br>
 * 表主键：ID<br>
 */
public class ZCDeployConfigSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String SourceDir;

	private String TargetDir;

	private String Method;

	private String Host;

	private Long Port;

	private String UserName;

	private String Password;

	private Long UseFlag;

	private Date BeginTime;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("SourceDir", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("TargetDir", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("Method", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Host", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("Port", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("UserName", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Password", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("UseFlag", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("BeginTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 15, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 17, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 18, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCDeployConfig";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCDeployConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCDeployConfig set ID=?,SiteID=?,SourceDir=?,TargetDir=?,Method=?,Host=?,Port=?,UserName=?,Password=?,UseFlag=?,BeginTime=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCDeployConfig  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCDeployConfig  where ID=?";

	public ZCDeployConfigSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[19];
	}

	protected Schema newInstance(){
		return new ZCDeployConfigSchema();
	}

	protected SchemaSet newSet(){
		return new ZCDeployConfigSet();
	}

	public ZCDeployConfigSet query() {
		return query(null, -1, -1);
	}

	public ZCDeployConfigSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCDeployConfigSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCDeployConfigSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCDeployConfigSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){SourceDir = (String)v;return;}
		if (i == 3){TargetDir = (String)v;return;}
		if (i == 4){Method = (String)v;return;}
		if (i == 5){Host = (String)v;return;}
		if (i == 6){if(v==null){Port = null;}else{Port = new Long(v.toString());}return;}
		if (i == 7){UserName = (String)v;return;}
		if (i == 8){Password = (String)v;return;}
		if (i == 9){if(v==null){UseFlag = null;}else{UseFlag = new Long(v.toString());}return;}
		if (i == 10){BeginTime = (Date)v;return;}
		if (i == 11){Prop1 = (String)v;return;}
		if (i == 12){Prop2 = (String)v;return;}
		if (i == 13){Prop3 = (String)v;return;}
		if (i == 14){Prop4 = (String)v;return;}
		if (i == 15){AddUser = (String)v;return;}
		if (i == 16){AddTime = (Date)v;return;}
		if (i == 17){ModifyUser = (String)v;return;}
		if (i == 18){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return SourceDir;}
		if (i == 3){return TargetDir;}
		if (i == 4){return Method;}
		if (i == 5){return Host;}
		if (i == 6){return Port;}
		if (i == 7){return UserName;}
		if (i == 8){return Password;}
		if (i == 9){return UseFlag;}
		if (i == 10){return BeginTime;}
		if (i == 11){return Prop1;}
		if (i == 12){return Prop2;}
		if (i == 13){return Prop3;}
		if (i == 14){return Prop4;}
		if (i == 15){return AddUser;}
		if (i == 16){return AddTime;}
		if (i == 17){return ModifyUser;}
		if (i == 18){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :配置ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :配置ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :配置ID<br>
	* 数据类型 :bigint<br>
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
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段SourceDir的值，该字段的<br>
	* 字段名称 :源目录<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSourceDir() {
		return SourceDir;
	}

	/**
	* 设置字段SourceDir的值，该字段的<br>
	* 字段名称 :源目录<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSourceDir(String sourceDir) {
		this.SourceDir = sourceDir;
    }

	/**
	* 获取字段TargetDir的值，该字段的<br>
	* 字段名称 :目标目录<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTargetDir() {
		return TargetDir;
	}

	/**
	* 设置字段TargetDir的值，该字段的<br>
	* 字段名称 :目标目录<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTargetDir(String targetDir) {
		this.TargetDir = targetDir;
    }

	/**
	* 获取字段Method的值，该字段的<br>
	* 字段名称 :发布方式<br>
	* 数据类型 :VARCHAR (50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMethod() {
		return Method;
	}

	/**
	* 设置字段Method的值，该字段的<br>
	* 字段名称 :发布方式<br>
	* 数据类型 :VARCHAR (50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMethod(String method) {
		this.Method = method;
    }

	/**
	* 获取字段Host的值，该字段的<br>
	* 字段名称 :服务器地址<br>
	* 数据类型 :VARCHAR (255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHost() {
		return Host;
	}

	/**
	* 设置字段Host的值，该字段的<br>
	* 字段名称 :服务器地址<br>
	* 数据类型 :VARCHAR (255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHost(String host) {
		this.Host = host;
    }

	/**
	* 获取字段Port的值，该字段的<br>
	* 字段名称 :端口<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getPort() {
		if(Port==null){return 0;}
		return Port.longValue();
	}

	/**
	* 设置字段Port的值，该字段的<br>
	* 字段名称 :端口<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPort(long port) {
		this.Port = new Long(port);
    }

	/**
	* 设置字段Port的值，该字段的<br>
	* 字段名称 :端口<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPort(String port) {
		if (port == null){
			this.Port = null;
			return;
		}
		this.Port = new Long(port);
    }

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :用户名<br>
	* 数据类型 :VARCHAR (100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :用户名<br>
	* 数据类型 :VARCHAR (100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段Password的值，该字段的<br>
	* 字段名称 :密码<br>
	* 数据类型 :VARCHAR (100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* 设置字段Password的值，该字段的<br>
	* 字段名称 :密码<br>
	* 数据类型 :VARCHAR (100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
    }

	/**
	* 获取字段UseFlag的值，该字段的<br>
	* 字段名称 :使用状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getUseFlag() {
		if(UseFlag==null){return 0;}
		return UseFlag.longValue();
	}

	/**
	* 设置字段UseFlag的值，该字段的<br>
	* 字段名称 :使用状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUseFlag(long useFlag) {
		this.UseFlag = new Long(useFlag);
    }

	/**
	* 设置字段UseFlag的值，该字段的<br>
	* 字段名称 :使用状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUseFlag(String useFlag) {
		if (useFlag == null){
			this.UseFlag = null;
			return;
		}
		this.UseFlag = new Long(useFlag);
    }

	/**
	* 获取字段BeginTime的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getBeginTime() {
		return BeginTime;
	}

	/**
	* 设置字段BeginTime的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBeginTime(Date beginTime) {
		this.BeginTime = beginTime;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

}