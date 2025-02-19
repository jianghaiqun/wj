package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：数据库采集表<br>
 * 表代码：ZCDBGather<br>
 * 表主键：ID<br>
 */
public class ZCDBGatherSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private Long DatabaseID;

	private String TableName;

	private Long CatalogID;

	private Long ArticleStatus;

	private String PathReplacePartOld;

	private String PathReplacePartNew;

	private String NewRecordRule;

	private String SQLCondition;

	private String Status;

	private String MappingConfig;

	private String Memo;

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
		new SchemaColumn("Name", DataColumn.STRING, 2, 200 , 0 , true , false),
		new SchemaColumn("DatabaseID", DataColumn.LONG, 3, 0 , 0 , true , false),
		new SchemaColumn("TableName", DataColumn.STRING, 4, 50 , 0 , true , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 5, 0 , 0 , true , false),
		new SchemaColumn("ArticleStatus", DataColumn.LONG, 6, 0 , 0 , true , false),
		new SchemaColumn("PathReplacePartOld", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("PathReplacePartNew", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("NewRecordRule", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("SQLCondition", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("MappingConfig", DataColumn.CLOB, 12, 0 , 0 , true , false),
		new SchemaColumn("Memo", DataColumn.STRING, 13, 400 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 21, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCDBGather";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCDBGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCDBGather set ID=?,SiteID=?,Name=?,DatabaseID=?,TableName=?,CatalogID=?,ArticleStatus=?,PathReplacePartOld=?,PathReplacePartNew=?,NewRecordRule=?,SQLCondition=?,Status=?,MappingConfig=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCDBGather  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCDBGather  where ID=?";

	public ZCDBGatherSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[22];
	}

	protected Schema newInstance(){
		return new ZCDBGatherSchema();
	}

	protected SchemaSet newSet(){
		return new ZCDBGatherSet();
	}

	public ZCDBGatherSet query() {
		return query(null, -1, -1);
	}

	public ZCDBGatherSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCDBGatherSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCDBGatherSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCDBGatherSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){if(v==null){DatabaseID = null;}else{DatabaseID = new Long(v.toString());}return;}
		if (i == 4){TableName = (String)v;return;}
		if (i == 5){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 6){if(v==null){ArticleStatus = null;}else{ArticleStatus = new Long(v.toString());}return;}
		if (i == 7){PathReplacePartOld = (String)v;return;}
		if (i == 8){PathReplacePartNew = (String)v;return;}
		if (i == 9){NewRecordRule = (String)v;return;}
		if (i == 10){SQLCondition = (String)v;return;}
		if (i == 11){Status = (String)v;return;}
		if (i == 12){MappingConfig = (String)v;return;}
		if (i == 13){Memo = (String)v;return;}
		if (i == 14){Prop1 = (String)v;return;}
		if (i == 15){Prop2 = (String)v;return;}
		if (i == 16){Prop3 = (String)v;return;}
		if (i == 17){Prop4 = (String)v;return;}
		if (i == 18){AddUser = (String)v;return;}
		if (i == 19){AddTime = (Date)v;return;}
		if (i == 20){ModifyUser = (String)v;return;}
		if (i == 21){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return DatabaseID;}
		if (i == 4){return TableName;}
		if (i == 5){return CatalogID;}
		if (i == 6){return ArticleStatus;}
		if (i == 7){return PathReplacePartOld;}
		if (i == 8){return PathReplacePartNew;}
		if (i == 9){return NewRecordRule;}
		if (i == 10){return SQLCondition;}
		if (i == 11){return Status;}
		if (i == 12){return MappingConfig;}
		if (i == 13){return Memo;}
		if (i == 14){return Prop1;}
		if (i == 15){return Prop2;}
		if (i == 16){return Prop3;}
		if (i == 17){return Prop4;}
		if (i == 18){return AddUser;}
		if (i == 19){return AddTime;}
		if (i == 20){return ModifyUser;}
		if (i == 21){return ModifyTime;}
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
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :任务名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :任务名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段DatabaseID的值，该字段的<br>
	* 字段名称 :外部数据库ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getDatabaseID() {
		if(DatabaseID==null){return 0;}
		return DatabaseID.longValue();
	}

	/**
	* 设置字段DatabaseID的值，该字段的<br>
	* 字段名称 :外部数据库ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDatabaseID(long databaseID) {
		this.DatabaseID = new Long(databaseID);
    }

	/**
	* 设置字段DatabaseID的值，该字段的<br>
	* 字段名称 :外部数据库ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDatabaseID(String databaseID) {
		if (databaseID == null){
			this.DatabaseID = null;
			return;
		}
		this.DatabaseID = new Long(databaseID);
    }

	/**
	* 获取字段TableName的值，该字段的<br>
	* 字段名称 :外部数据表名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTableName() {
		return TableName;
	}

	/**
	* 设置字段TableName的值，该字段的<br>
	* 字段名称 :外部数据表名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTableName(String tableName) {
		this.TableName = tableName;
    }

	/**
	* 获取字段CatalogID的值，该字段的<br>
	* 字段名称 :采集到此栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getCatalogID() {
		if(CatalogID==null){return 0;}
		return CatalogID.longValue();
	}

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :采集到此栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :采集到此栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(String catalogID) {
		if (catalogID == null){
			this.CatalogID = null;
			return;
		}
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 获取字段ArticleStatus的值，该字段的<br>
	* 字段名称 :采集后的文档状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getArticleStatus() {
		if(ArticleStatus==null){return 0;}
		return ArticleStatus.longValue();
	}

	/**
	* 设置字段ArticleStatus的值，该字段的<br>
	* 字段名称 :采集后的文档状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleStatus(long articleStatus) {
		this.ArticleStatus = new Long(articleStatus);
    }

	/**
	* 设置字段ArticleStatus的值，该字段的<br>
	* 字段名称 :采集后的文档状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleStatus(String articleStatus) {
		if (articleStatus == null){
			this.ArticleStatus = null;
			return;
		}
		this.ArticleStatus = new Long(articleStatus);
    }

	/**
	* 获取字段PathReplacePartOld的值，该字段的<br>
	* 字段名称 :文件路径部分替换旧值<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPathReplacePartOld() {
		return PathReplacePartOld;
	}

	/**
	* 设置字段PathReplacePartOld的值，该字段的<br>
	* 字段名称 :文件路径部分替换旧值<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPathReplacePartOld(String pathReplacePartOld) {
		this.PathReplacePartOld = pathReplacePartOld;
    }

	/**
	* 获取字段PathReplacePartNew的值，该字段的<br>
	* 字段名称 :文件路径部分替换新值<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPathReplacePartNew() {
		return PathReplacePartNew;
	}

	/**
	* 设置字段PathReplacePartNew的值，该字段的<br>
	* 字段名称 :文件路径部分替换新值<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPathReplacePartNew(String pathReplacePartNew) {
		this.PathReplacePartNew = pathReplacePartNew;
    }

	/**
	* 获取字段NewRecordRule的值，该字段的<br>
	* 字段名称 :记录查新规则<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getNewRecordRule() {
		return NewRecordRule;
	}

	/**
	* 设置字段NewRecordRule的值，该字段的<br>
	* 字段名称 :记录查新规则<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setNewRecordRule(String newRecordRule) {
		this.NewRecordRule = newRecordRule;
    }

	/**
	* 获取字段SQLCondition的值，该字段的<br>
	* 字段名称 :查询条件<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSQLCondition() {
		return SQLCondition;
	}

	/**
	* 设置字段SQLCondition的值，该字段的<br>
	* 字段名称 :查询条件<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSQLCondition(String sQLCondition) {
		this.SQLCondition = sQLCondition;
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
	* 获取字段MappingConfig的值，该字段的<br>
	* 字段名称 :字段映射配置<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getMappingConfig() {
		return MappingConfig;
	}

	/**
	* 设置字段MappingConfig的值，该字段的<br>
	* 字段名称 :字段映射配置<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMappingConfig(String mappingConfig) {
		this.MappingConfig = mappingConfig;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
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