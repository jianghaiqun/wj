package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：网站群分发配置表<br>
 * 表代码：ZCInnerDeploy<br>
 * 表主键：ID<br>
 */
public class ZCInnerDeploySchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String DeployType;

	private String CatalogInnerCode;

	private String TargetCatalog;

	private String SyncCatalogInsert;

	private String SyncCatalogModify;

	private String SyncArticleModify;

	private Long AfterSyncStatus;

	private Long AfterModifyStatus;

	private String Status;

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
		new SchemaColumn("DeployType", DataColumn.STRING, 3, 2 , 0 , true , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 4, 200 , 0 , true , false),
		new SchemaColumn("TargetCatalog", DataColumn.STRING, 5, 4000 , 0 , true , false),
		new SchemaColumn("SyncCatalogInsert", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("SyncCatalogModify", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("SyncArticleModify", DataColumn.STRING, 8, 2 , 0 , false , false),
		new SchemaColumn("AfterSyncStatus", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("AfterModifyStatus", DataColumn.LONG, 10, 0 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 16, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 18, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 19, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCInnerDeploy";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCInnerDeploy values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCInnerDeploy set ID=?,SiteID=?,Name=?,DeployType=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCInnerDeploy  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCInnerDeploy  where ID=?";

	public ZCInnerDeploySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[20];
	}

	protected Schema newInstance(){
		return new ZCInnerDeploySchema();
	}

	protected SchemaSet newSet(){
		return new ZCInnerDeploySet();
	}

	public ZCInnerDeploySet query() {
		return query(null, -1, -1);
	}

	public ZCInnerDeploySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCInnerDeploySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCInnerDeploySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCInnerDeploySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){DeployType = (String)v;return;}
		if (i == 4){CatalogInnerCode = (String)v;return;}
		if (i == 5){TargetCatalog = (String)v;return;}
		if (i == 6){SyncCatalogInsert = (String)v;return;}
		if (i == 7){SyncCatalogModify = (String)v;return;}
		if (i == 8){SyncArticleModify = (String)v;return;}
		if (i == 9){if(v==null){AfterSyncStatus = null;}else{AfterSyncStatus = new Long(v.toString());}return;}
		if (i == 10){if(v==null){AfterModifyStatus = null;}else{AfterModifyStatus = new Long(v.toString());}return;}
		if (i == 11){Status = (String)v;return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
		if (i == 15){Prop4 = (String)v;return;}
		if (i == 16){AddUser = (String)v;return;}
		if (i == 17){AddTime = (Date)v;return;}
		if (i == 18){ModifyUser = (String)v;return;}
		if (i == 19){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return DeployType;}
		if (i == 4){return CatalogInnerCode;}
		if (i == 5){return TargetCatalog;}
		if (i == 6){return SyncCatalogInsert;}
		if (i == 7){return SyncCatalogModify;}
		if (i == 8){return SyncArticleModify;}
		if (i == 9){return AfterSyncStatus;}
		if (i == 10){return AfterModifyStatus;}
		if (i == 11){return Status;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return Prop3;}
		if (i == 15){return Prop4;}
		if (i == 16){return AddUser;}
		if (i == 17){return AddTime;}
		if (i == 18){return ModifyUser;}
		if (i == 19){return ModifyTime;}
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
	* 获取字段DeployType的值，该字段的<br>
	* 字段名称 :分发类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1表示自动分发<br>
	2表示手工选择<br>
	3表示脚本分发（暂不支持）<br>
	*/
	public String getDeployType() {
		return DeployType;
	}

	/**
	* 设置字段DeployType的值，该字段的<br>
	* 字段名称 :分发类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1表示自动分发<br>
	2表示手工选择<br>
	3表示脚本分发（暂不支持）<br>
	*/
	public void setDeployType(String deployType) {
		this.DeployType = deployType;
    }

	/**
	* 获取字段CatalogInnerCode的值，该字段的<br>
	* 字段名称 :源栏目代码<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCatalogInnerCode() {
		return CatalogInnerCode;
	}

	/**
	* 设置字段CatalogInnerCode的值，该字段的<br>
	* 字段名称 :源栏目代码<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogInnerCode(String catalogInnerCode) {
		this.CatalogInnerCode = catalogInnerCode;
    }

	/**
	* 获取字段TargetCatalog的值，该字段的<br>
	* 字段名称 :目标栏目描述<br>
	* 数据类型 :varchar(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	本字段中的值每行代表一个源栏目，每行格式如下http://www.zving.com/cms/ 272 13422 password 新闻动态，即以空格分离CMS地址、站点ID、栏目ID、密钥和栏目名称。<br>
	*/
	public String getTargetCatalog() {
		return TargetCatalog;
	}

	/**
	* 设置字段TargetCatalog的值，该字段的<br>
	* 字段名称 :目标栏目描述<br>
	* 数据类型 :varchar(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	本字段中的值每行代表一个源栏目，每行格式如下http://www.zving.com/cms/ 272 13422 password 新闻动态，即以空格分离CMS地址、站点ID、栏目ID、密钥和栏目名称。<br>
	*/
	public void setTargetCatalog(String targetCatalog) {
		this.TargetCatalog = targetCatalog;
    }

	/**
	* 获取字段SyncCatalogInsert的值，该字段的<br>
	* 字段名称 :分发栏目新增标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSyncCatalogInsert() {
		return SyncCatalogInsert;
	}

	/**
	* 设置字段SyncCatalogInsert的值，该字段的<br>
	* 字段名称 :分发栏目新增标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSyncCatalogInsert(String syncCatalogInsert) {
		this.SyncCatalogInsert = syncCatalogInsert;
    }

	/**
	* 获取字段SyncCatalogModify的值，该字段的<br>
	* 字段名称 :分发栏目修改/删除标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSyncCatalogModify() {
		return SyncCatalogModify;
	}

	/**
	* 设置字段SyncCatalogModify的值，该字段的<br>
	* 字段名称 :分发栏目修改/删除标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSyncCatalogModify(String syncCatalogModify) {
		this.SyncCatalogModify = syncCatalogModify;
    }

	/**
	* 获取字段SyncArticleModify的值，该字段的<br>
	* 字段名称 :文章再次修改分发标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSyncArticleModify() {
		return SyncArticleModify;
	}

	/**
	* 设置字段SyncArticleModify的值，该字段的<br>
	* 字段名称 :文章再次修改分发标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSyncArticleModify(String syncArticleModify) {
		this.SyncArticleModify = syncArticleModify;
    }

	/**
	* 获取字段AfterSyncStatus的值，该字段的<br>
	* 字段名称 :初次分发后文章状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getAfterSyncStatus() {
		if(AfterSyncStatus==null){return 0;}
		return AfterSyncStatus.longValue();
	}

	/**
	* 设置字段AfterSyncStatus的值，该字段的<br>
	* 字段名称 :初次分发后文章状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterSyncStatus(long afterSyncStatus) {
		this.AfterSyncStatus = new Long(afterSyncStatus);
    }

	/**
	* 设置字段AfterSyncStatus的值，该字段的<br>
	* 字段名称 :初次分发后文章状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterSyncStatus(String afterSyncStatus) {
		if (afterSyncStatus == null){
			this.AfterSyncStatus = null;
			return;
		}
		this.AfterSyncStatus = new Long(afterSyncStatus);
    }

	/**
	* 获取字段AfterModifyStatus的值，该字段的<br>
	* 字段名称 :改动后分发文章状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getAfterModifyStatus() {
		if(AfterModifyStatus==null){return 0;}
		return AfterModifyStatus.longValue();
	}

	/**
	* 设置字段AfterModifyStatus的值，该字段的<br>
	* 字段名称 :改动后分发文章状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterModifyStatus(long afterModifyStatus) {
		this.AfterModifyStatus = new Long(afterModifyStatus);
    }

	/**
	* 设置字段AfterModifyStatus的值，该字段的<br>
	* 字段名称 :改动后分发文章状态<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterModifyStatus(String afterModifyStatus) {
		if (afterModifyStatus == null){
			this.AfterModifyStatus = null;
			return;
		}
		this.AfterModifyStatus = new Long(afterModifyStatus);
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