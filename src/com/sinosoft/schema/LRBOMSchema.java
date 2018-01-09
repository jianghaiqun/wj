package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：业务BOM<br>
 * 表代码：LRBOM<br>
 * 表主键：Name<br>
 */
public class LRBOMSchema extends Schema {
	private String Name;

	private String CNName;

	private String FBOM;

	private String LocalItem;

	private String FatherItem;

	private String BOMLevel;

	private String Business;

	private String Discription;

	private String Source;

	private String Valid;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Name", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("CNName", DataColumn.STRING, 1, 30 , 0 , true , false),
		new SchemaColumn("FBOM", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("LocalItem", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("FatherItem", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("BOMLevel", DataColumn.STRING, 5, 1 , 0 , true , false),
		new SchemaColumn("Business", DataColumn.STRING, 6, 2 , 0 , true , false),
		new SchemaColumn("Discription", DataColumn.STRING, 7, 300 , 0 , false , false),
		new SchemaColumn("Source", DataColumn.STRING, 8, 40 , 0 , true , false),
		new SchemaColumn("Valid", DataColumn.STRING, 9, 1 , 0 , true , false)
	};

	public static final String _TableCode = "LRBOM";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LRBOM values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LRBOM set Name=?,CNName=?,FBOM=?,LocalItem=?,FatherItem=?,BOMLevel=?,Business=?,Discription=?,Source=?,Valid=? where Name=?";

	protected static final String _DeleteSQL = "delete from LRBOM  where Name=?";

	protected static final String _FillAllSQL = "select * from LRBOM  where Name=?";

	public LRBOMSchema(){
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
		return new LRBOMSchema();
	}

	protected SchemaSet newSet(){
		return new LRBOMSet();
	}

	public LRBOMSet query() {
		return query(null, -1, -1);
	}

	public LRBOMSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LRBOMSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LRBOMSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LRBOMSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Name = (String)v;return;}
		if (i == 1){CNName = (String)v;return;}
		if (i == 2){FBOM = (String)v;return;}
		if (i == 3){LocalItem = (String)v;return;}
		if (i == 4){FatherItem = (String)v;return;}
		if (i == 5){BOMLevel = (String)v;return;}
		if (i == 6){Business = (String)v;return;}
		if (i == 7){Discription = (String)v;return;}
		if (i == 8){Source = (String)v;return;}
		if (i == 9){Valid = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Name;}
		if (i == 1){return CNName;}
		if (i == 2){return FBOM;}
		if (i == 3){return LocalItem;}
		if (i == 4){return FatherItem;}
		if (i == 5){return BOMLevel;}
		if (i == 6){return Business;}
		if (i == 7){return Discription;}
		if (i == 8){return Source;}
		if (i == 9){return Valid;}
		return null;
	}

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :BOM英文名<br>
	* 数据类型 :varchar2(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :BOM英文名<br>
	* 数据类型 :varchar2(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段CNName的值，该字段的<br>
	* 字段名称 :BOM的中文名<br>
	* 数据类型 :varchar2(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCNName() {
		return CNName;
	}

	/**
	* 设置字段CNName的值，该字段的<br>
	* 字段名称 :BOM的中文名<br>
	* 数据类型 :varchar2(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCNName(String cNName) {
		this.CNName = cNName;
    }

	/**
	* 获取字段FBOM的值，该字段的<br>
	* 字段名称 :父BOM<br>
	* 数据类型 :varchar2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	0——此词条是一个BOM 1——此词条是叶子词条<br>
	*/
	public String getFBOM() {
		return FBOM;
	}

	/**
	* 设置字段FBOM的值，该字段的<br>
	* 字段名称 :父BOM<br>
	* 数据类型 :varchar2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	0——此词条是一个BOM 1——此词条是叶子词条<br>
	*/
	public void setFBOM(String fBOM) {
		this.FBOM = fBOM;
    }

	/**
	* 获取字段LocalItem的值，该字段的<br>
	* 字段名称 :本BOM字段<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLocalItem() {
		return LocalItem;
	}

	/**
	* 设置字段LocalItem的值，该字段的<br>
	* 字段名称 :本BOM字段<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLocalItem(String localItem) {
		this.LocalItem = localItem;
    }

	/**
	* 获取字段FatherItem的值，该字段的<br>
	* 字段名称 :父BOM字段<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFatherItem() {
		return FatherItem;
	}

	/**
	* 设置字段FatherItem的值，该字段的<br>
	* 字段名称 :父BOM字段<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFatherItem(String fatherItem) {
		this.FatherItem = fatherItem;
    }

	/**
	* 获取字段BOMLevel的值，该字段的<br>
	* 字段名称 :BOM层次<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBOMLevel() {
		return BOMLevel;
	}

	/**
	* 设置字段BOMLevel的值，该字段的<br>
	* 字段名称 :BOM层次<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBOMLevel(String bOMLevel) {
		this.BOMLevel = bOMLevel;
    }

	/**
	* 获取字段Business的值，该字段的<br>
	* 字段名称 :业务模块<br>
	* 数据类型 :VARCHAR2(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0——公共 1-契约自核规则 2-保全自核规则, 3-理赔自核规则, 4- 续期续保核保规则, 5-销售管理模块<br>
	*/
	public String getBusiness() {
		return Business;
	}

	/**
	* 设置字段Business的值，该字段的<br>
	* 字段名称 :业务模块<br>
	* 数据类型 :VARCHAR2(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0——公共 1-契约自核规则 2-保全自核规则, 3-理赔自核规则, 4- 续期续保核保规则, 5-销售管理模块<br>
	*/
	public void setBusiness(String business) {
		this.Business = business;
    }

	/**
	* 获取字段Discription的值，该字段的<br>
	* 字段名称 :BOM描述信息<br>
	* 数据类型 :varchar2(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDiscription() {
		return Discription;
	}

	/**
	* 设置字段Discription的值，该字段的<br>
	* 字段名称 :BOM描述信息<br>
	* 数据类型 :varchar2(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDiscription(String discription) {
		this.Discription = discription;
    }

	/**
	* 获取字段Source的值，该字段的<br>
	* 字段名称 :BOM对应的Class名称<br>
	* 数据类型 :varchar2(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSource() {
		return Source;
	}

	/**
	* 设置字段Source的值，该字段的<br>
	* 字段名称 :BOM对应的Class名称<br>
	* 数据类型 :varchar2(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSource(String source) {
		this.Source = source;
    }

	/**
	* 获取字段Valid的值，该字段的<br>
	* 字段名称 :有效性<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getValid() {
		return Valid;
	}

	/**
	* 设置字段Valid的值，该字段的<br>
	* 字段名称 :有效性<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setValid(String valid) {
		this.Valid = valid;
    }

}