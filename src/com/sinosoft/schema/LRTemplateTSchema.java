package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：模板草稿<br>
 * 表代码：LRTemplateT<br>
 * 表主键：Id<br>
 */
public class LRTemplateTSchema extends Schema {
	private String Id;

	private Integer Version;

	private String RuleName;

	private String RuleCh;

	private String SQLStatement;

	private String BOMs;

	private String SQLParameter;

	private String ViewParameter;

	private String Valid;

	private String State;

	private String TemplateLevel;

	private String Business;

	private String Type;

	private String TableName;

	private Date StartDate;

	private Date EndDate;

	private String Description;

	private String Creator;

	private String Modifier;

	private String Approver;

	private String Deployer;

	private String MakeTime;

	private Date MakeDate;

	private String ModifyTime;

	private Date ModifyDate;

	private String AuthorTime;

	private Date AuthorDate;

	private String DeclTime;

	private Date DeclDate;

	private String MarketingNum;

	private String DRLPath;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("Version", DataColumn.INTEGER, 1, 0 , 0 , true , false),
		new SchemaColumn("RuleName", DataColumn.STRING, 2, 200 , 0 , true , false),
		new SchemaColumn("RuleCh", DataColumn.STRING, 3, 4000 , 0 , true , false),
		new SchemaColumn("SQLStatement", DataColumn.STRING, 4, 4000 , 0 , true , false),
		new SchemaColumn("BOMs", DataColumn.STRING, 5, 1000 , 0 , true , false),
		new SchemaColumn("SQLParameter", DataColumn.STRING, 6, 4000 , 0 , true , false),
		new SchemaColumn("ViewParameter", DataColumn.LONG, 7, 0 , 0 , true , false),
		new SchemaColumn("Valid", DataColumn.STRING, 8, 1 , 0 , true , false),
		new SchemaColumn("State", DataColumn.STRING, 9, 2 , 0 , true , false),
		new SchemaColumn("TemplateLevel", DataColumn.STRING, 10, 12 , 0 , true , false),
		new SchemaColumn("Business", DataColumn.STRING, 11, 2 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 12, 8 , 0 , false , false),
		new SchemaColumn("TableName", DataColumn.STRING, 13, 30 , 0 , true , false),
		new SchemaColumn("StartDate", DataColumn.DATETIME, 14, 0 , 0 , true , false),
		new SchemaColumn("EndDate", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("Description", DataColumn.STRING, 16, 1000 , 0 , false , false),
		new SchemaColumn("Creator", DataColumn.STRING, 17, 80 , 0 , true , false),
		new SchemaColumn("Modifier", DataColumn.STRING, 18, 80 , 0 , false , false),
		new SchemaColumn("Approver", DataColumn.STRING, 19, 80 , 0 , false , false),
		new SchemaColumn("Deployer", DataColumn.STRING, 20, 80 , 0 , false , false),
		new SchemaColumn("MakeTime", DataColumn.STRING, 21, 8 , 0 , true , false),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 22, 0 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.STRING, 23, 8 , 0 , true , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 24, 0 , 0 , true , false),
		new SchemaColumn("AuthorTime", DataColumn.STRING, 25, 8 , 0 , false , false),
		new SchemaColumn("AuthorDate", DataColumn.DATETIME, 26, 0 , 0 , false , false),
		new SchemaColumn("DeclTime", DataColumn.STRING, 27, 8 , 0 , false , false),
		new SchemaColumn("DeclDate", DataColumn.DATETIME, 28, 0 , 0 , false , false),
		new SchemaColumn("MarketingNum", DataColumn.STRING, 29, 20 , 0 , false , false),
		new SchemaColumn("DRLPath", DataColumn.STRING, 30, 200 , 0 , false , false)
	};

	public static final String _TableCode = "LRTemplateT";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LRTemplateT values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LRTemplateT set Id=?,Version=?,RuleName=?,RuleCh=?,SQLStatement=?,BOMs=?,SQLParameter=?,ViewParameter=?,Valid=?,State=?,TemplateLevel=?,Business=?,Type=?,TableName=?,StartDate=?,EndDate=?,Description=?,Creator=?,Modifier=?,Approver=?,Deployer=?,MakeTime=?,MakeDate=?,ModifyTime=?,ModifyDate=?,AuthorTime=?,AuthorDate=?,DeclTime=?,DeclDate=?,MarketingNum=?,DRLPath=? where Id=?";

	protected static final String _DeleteSQL = "delete from LRTemplateT  where Id=?";

	protected static final String _FillAllSQL = "select * from LRTemplateT  where Id=?";

	public LRTemplateTSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[31];
	}

	protected Schema newInstance(){
		return new LRTemplateTSchema();
	}

	protected SchemaSet newSet(){
		return new LRTemplateTSet();
	}

	public LRTemplateTSet query() {
		return query(null, -1, -1);
	}

	public LRTemplateTSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LRTemplateTSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LRTemplateTSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LRTemplateTSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){if(v==null){Version = null;}else{Version = new Integer(v.toString());}return;}
		if (i == 2){RuleName = (String)v;return;}
		if (i == 3){RuleCh = (String)v;return;}
		if (i == 4){SQLStatement = (String)v;return;}
		if (i == 5){BOMs = (String)v;return;}
		if (i == 6){SQLParameter = (String)v;return;}
		if (i == 7){ViewParameter = (String)v;return;}
		if (i == 8){Valid = (String)v;return;}
		if (i == 9){State = (String)v;return;}
		if (i == 10){TemplateLevel = (String)v;return;}
		if (i == 11){Business = (String)v;return;}
		if (i == 12){Type = (String)v;return;}
		if (i == 13){TableName = (String)v;return;}
		if (i == 14){StartDate = (Date)v;return;}
		if (i == 15){EndDate = (Date)v;return;}
		if (i == 16){Description = (String)v;return;}
		if (i == 17){Creator = (String)v;return;}
		if (i == 18){Modifier = (String)v;return;}
		if (i == 19){Approver = (String)v;return;}
		if (i == 20){Deployer = (String)v;return;}
		if (i == 21){MakeTime = (String)v;return;}
		if (i == 22){MakeDate = (Date)v;return;}
		if (i == 23){ModifyTime = (String)v;return;}
		if (i == 24){ModifyDate = (Date)v;return;}
		if (i == 25){AuthorTime = (String)v;return;}
		if (i == 26){AuthorDate = (Date)v;return;}
		if (i == 27){DeclTime = (String)v;return;}
		if (i == 28){DeclDate = (Date)v;return;}
		if (i == 29){MarketingNum = (String)v;return;}
		if (i == 30){DRLPath = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return Version;}
		if (i == 2){return RuleName;}
		if (i == 3){return RuleCh;}
		if (i == 4){return SQLStatement;}
		if (i == 5){return BOMs;}
		if (i == 6){return SQLParameter;}
		if (i == 7){return ViewParameter;}
		if (i == 8){return Valid;}
		if (i == 9){return State;}
		if (i == 10){return TemplateLevel;}
		if (i == 11){return Business;}
		if (i == 12){return Type;}
		if (i == 13){return TableName;}
		if (i == 14){return StartDate;}
		if (i == 15){return EndDate;}
		if (i == 16){return Description;}
		if (i == 17){return Creator;}
		if (i == 18){return Modifier;}
		if (i == 19){return Approver;}
		if (i == 20){return Deployer;}
		if (i == 21){return MakeTime;}
		if (i == 22){return MakeDate;}
		if (i == 23){return ModifyTime;}
		if (i == 24){return ModifyDate;}
		if (i == 25){return AuthorTime;}
		if (i == 26){return AuthorDate;}
		if (i == 27){return DeclTime;}
		if (i == 28){return DeclDate;}
		if (i == 29){return MarketingNum;}
		if (i == 30){return DRLPath;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :模板号<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :模板号<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段Version的值，该字段的<br>
	* 字段名称 :版本号<br>
	* 数据类型 :INT<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getVersion() {
		if(Version==null){return 0;}
		return Version.intValue();
	}

	/**
	* 设置字段Version的值，该字段的<br>
	* 字段名称 :版本号<br>
	* 数据类型 :INT<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVersion(int version) {
		this.Version = new Integer(version);
    }

	/**
	* 设置字段Version的值，该字段的<br>
	* 字段名称 :版本号<br>
	* 数据类型 :INT<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVersion(String version) {
		if (version == null){
			this.Version = null;
			return;
		}
		this.Version = new Integer(version);
    }

	/**
	* 获取字段RuleName的值，该字段的<br>
	* 字段名称 :规则名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRuleName() {
		return RuleName;
	}

	/**
	* 设置字段RuleName的值，该字段的<br>
	* 字段名称 :规则名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRuleName(String ruleName) {
		this.RuleName = ruleName;
    }

	/**
	* 获取字段RuleCh的值，该字段的<br>
	* 字段名称 :规则中文<br>
	* 数据类型 :VARCHAR(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRuleCh() {
		return RuleCh;
	}

	/**
	* 设置字段RuleCh的值，该字段的<br>
	* 字段名称 :规则中文<br>
	* 数据类型 :VARCHAR(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRuleCh(String ruleCh) {
		this.RuleCh = ruleCh;
    }

	/**
	* 获取字段SQLStatement的值，该字段的<br>
	* 字段名称 :规则逻辑<br>
	* 数据类型 :VARCHAR(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	规则的SQL语言描述<br>
	*/
	public String getSQLStatement() {
		return SQLStatement;
	}

	/**
	* 设置字段SQLStatement的值，该字段的<br>
	* 字段名称 :规则逻辑<br>
	* 数据类型 :VARCHAR(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	规则的SQL语言描述<br>
	*/
	public void setSQLStatement(String sQLStatement) {
		this.SQLStatement = sQLStatement;
    }

	/**
	* 获取字段BOMs的值，该字段的<br>
	* 字段名称 :所用BOM<br>
	* 数据类型 :VARCHAR(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBOMs() {
		return BOMs;
	}

	/**
	* 设置字段BOMs的值，该字段的<br>
	* 字段名称 :所用BOM<br>
	* 数据类型 :VARCHAR(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBOMs(String bOMs) {
		this.BOMs = bOMs;
    }

	/**
	* 获取字段SQLParameter的值，该字段的<br>
	* 字段名称 :SQL参数<br>
	* 数据类型 :VARCHAR(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	列出所有的参数 和 运算符号 用来进行界面展现 A.a>;B.b=<br>
	*/
	public String getSQLParameter() {
		return SQLParameter;
	}

	/**
	* 设置字段SQLParameter的值，该字段的<br>
	* 字段名称 :SQL参数<br>
	* 数据类型 :VARCHAR(4000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	列出所有的参数 和 运算符号 用来进行界面展现 A.a>;B.b=<br>
	*/
	public void setSQLParameter(String sQLParameter) {
		this.SQLParameter = sQLParameter;
    }

	/**
	* 获取字段ViewParameter的值，该字段的<br>
	* 字段名称 :View参数<br>
	* 数据类型 :long<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	列出所有的参数 和 运算符号 ���来进行界面展现 A.a>;B.b=<br>
	*/
	public String getViewParameter() {
		return ViewParameter;
	}

	/**
	* 设置字段ViewParameter的值，该字段的<br>
	* 字段名称 :View参数<br>
	* 数据类型 :long<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	列出所有的参数 和 运算符号 ���来进行界面展现 A.a>;B.b=<br>
	*/
	public void setViewParameter(String viewParameter) {
		this.ViewParameter =viewParameter;
    }


	/**
	* 获取字段Valid的值，该字段的<br>
	* 字段名称 :有效标志<br>
	* 数据类型 :VARCHAR(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0表示无效,1表示有效<br>
	*/
	public String getValid() {
		return Valid;
	}

	/**
	* 设置字段Valid的值，该字段的<br>
	* 字段名称 :有效标志<br>
	* 数据类型 :VARCHAR(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0表示无效,1表示有效<br>
	*/
	public void setValid(String valid) {
		this.Valid = valid;
    }

	/**
	* 获取字段State的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0——初始 1——待测试 2——待修改 3——待审批 4-------待发布 7------可使用 9------已作废<br>
	*/
	public String getState() {
		return State;
	}

	/**
	* 设置字段State的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0——初始 1——待测试 2——待修改 3——待审批 4-------待发布 7------可使用 9------已作废<br>
	*/
	public void setState(String state) {
		this.State = state;
    }

	/**
	* 获取字段TemplateLevel的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :VARCHAR(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-系统级规则,2-组合级规则,3-产品级规则,4-业务级规则,5-客户级规则,6-保单级规则<br>
	*/
	public String getTemplateLevel() {
		return TemplateLevel;
	}

	/**
	* 设置字段TemplateLevel的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :VARCHAR(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-系统级规则,2-组合级规则,3-产品级规则,4-业务级规则,5-客户级规则,6-保单级规则<br>
	*/
	public void setTemplateLevel(String templateLevel) {
		this.TemplateLevel = templateLevel;
    }

	/**
	* 获取字段Business的值，该字段的<br>
	* 字段名称 :业务模块<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-契约自核规则 2-保全自核规则, 3-理赔自核规则, 4- 续期续保核保规则, 5-销售管理模块<br>
	*/
	public String getBusiness() {
		return Business;
	}

	/**
	* 设置字段Business的值，该字段的<br>
	* 字段名称 :业务模块<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-契约自核规则 2-保全自核规则, 3-理赔自核规则, 4- 续期续保核保规则, 5-销售管理模块<br>
	*/
	public void setBusiness(String business) {
		this.Business = business;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :分类<br>
	* 数据类型 :VARCHAR(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :分类<br>
	* 数据类型 :VARCHAR(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段TableName的值，该字段的<br>
	* 字段名称 :表名<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	规则所需数据的存储表名<br>
	*/
	public String getTableName() {
		return TableName;
	}

	/**
	* 设置字段TableName的值，该字段的<br>
	* 字段名称 :表名<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	规则所需数据的存储表名<br>
	*/
	public void setTableName(String tableName) {
		this.TableName = tableName;
    }

	/**
	* 获取字段StartDate的值，该字段的<br>
	* 字段名称 :生效日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getStartDate() {
		return StartDate;
	}

	/**
	* 设置字段StartDate的值，该字段的<br>
	* 字段名称 :生效日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStartDate(Date startDate) {
		this.StartDate = startDate;
    }

	/**
	* 获取字段EndDate的值，该字段的<br>
	* 字段名称 :失效日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getEndDate() {
		return EndDate;
	}

	/**
	* 设置字段EndDate的值，该字段的<br>
	* 字段名称 :失效日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEndDate(Date endDate) {
		this.EndDate = endDate;
    }

	/**
	* 获取字段Description的值，该字段的<br>
	* 字段名称 :功能描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescription() {
		return Description;
	}

	/**
	* 设置字段Description的值，该字段的<br>
	* 字段名称 :功能描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescription(String description) {
		this.Description = description;
    }

	/**
	* 获取字段Creator的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :VARCHAR(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCreator() {
		return Creator;
	}

	/**
	* 设置字段Creator的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :VARCHAR(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreator(String creator) {
		this.Creator = creator;
    }

	/**
	* 获取字段Modifier的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :VARCHAR(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifier() {
		return Modifier;
	}

	/**
	* 设置字段Modifier的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :VARCHAR(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifier(String modifier) {
		this.Modifier = modifier;
    }

	/**
	* 获取字段Approver的值，该字段的<br>
	* 字段名称 :审批人<br>
	* 数据类型 :VARCHAR(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getApprover() {
		return Approver;
	}

	/**
	* 设置字段Approver的值，该字段的<br>
	* 字段名称 :审批人<br>
	* 数据类型 :VARCHAR(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setApprover(String approver) {
		this.Approver = approver;
    }

	/**
	* 获取字段Deployer的值，该字段的<br>
	* 字段名称 :发布人<br>
	* 数据类型 :VARCHAR(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDeployer() {
		return Deployer;
	}

	/**
	* 设置字段Deployer的值，该字段的<br>
	* 字段名称 :发布人<br>
	* 数据类型 :VARCHAR(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDeployer(String deployer) {
		this.Deployer = deployer;
    }

	/**
	* 获取字段MakeTime的值，该字段的<br>
	* 字段名称 :入机时间<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getMakeTime() {
		return MakeTime;
	}

	/**
	* 设置字段MakeTime的值，该字段的<br>
	* 字段名称 :入机时间<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMakeTime(String makeTime) {
		this.MakeTime = makeTime;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :入机日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	纪录产生该纪录的操作员。<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :入机日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	纪录产生该纪录的操作员。<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后一次修改时间<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后一次修改时间<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setModifyTime(String modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :最后一次修改日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	纪录最后一次修改的日期。<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :最后一次修改日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	纪录最后一次修改的日期。<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段AuthorTime的值，该字段的<br>
	* 字段名称 :审批时间<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAuthorTime() {
		return AuthorTime;
	}

	/**
	* 设置字段AuthorTime的值，该字段的<br>
	* 字段名称 :审批时间<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAuthorTime(String authorTime) {
		this.AuthorTime = authorTime;
    }

	/**
	* 获取字段AuthorDate的值，该字段的<br>
	* 字段名称 :审批日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	纪录产生该纪录的操作员。<br>
	*/
	public Date getAuthorDate() {
		return AuthorDate;
	}

	/**
	* 设置字段AuthorDate的值，该字段的<br>
	* 字段名称 :审批日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	纪录产生该纪录的操作员。<br>
	*/
	public void setAuthorDate(Date authorDate) {
		this.AuthorDate = authorDate;
    }

	/**
	* 获取字段DeclTime的值，该字段的<br>
	* 字段名称 :发布时间<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDeclTime() {
		return DeclTime;
	}

	/**
	* 设置字段DeclTime的值，该字段的<br>
	* 字段名称 :发布时间<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDeclTime(String declTime) {
		this.DeclTime = declTime;
    }

	/**
	* 获取字段DeclDate的值，该字段的<br>
	* 字段名称 :发布日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	纪录产生该纪录的操作员。<br>
	*/
	public Date getDeclDate() {
		return DeclDate;
	}

	/**
	* 设置字段DeclDate的值，该字段的<br>
	* 字段名称 :发布日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	纪录产生该纪录的操作员。<br>
	*/
	public void setDeclDate(Date declDate) {
		this.DeclDate = declDate;
    }

	/**
	* 获取字段MarketingNum的值，该字段的<br>
	* 字段名称 :营销编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMarketingNum() {
		return MarketingNum;
	}

	/**
	* 设置字段MarketingNum的值，该字段的<br>
	* 字段名称 :营销编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMarketingNum(String marketingNum) {
		this.MarketingNum = marketingNum;
    }

	/**
	* 获取字段DRLPath的值，该字段的<br>
	* 字段名称 :文件路径<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDRLPath() {
		return DRLPath;
	}

	/**
	* 设置字段DRLPath的值，该字段的<br>
	* 字段名称 :文件路径<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDRLPath(String dRLPath) {
		this.DRLPath = dRLPath;
    }

}