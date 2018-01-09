package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

/**
 * 表名称：机构表<br>
 * 表代码：organ_code<br>
 * 表主键：organ_code<br>
 */
public class BOrganSchema extends Schema {

	private String organ_code;
	private String organ_name;
	private String oragan_respons;
	private String parent_organ;
	private String organ_level;
	
	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("organ_code", DataColumn.STRING, 0, 6 , 0 , true , true),
		new SchemaColumn("organ_name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("oragan_respons", DataColumn.STRING, 2, 400 , 0 , true , false),
		new SchemaColumn("parent_organ", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("organ_level", DataColumn.STRING, 4, 1 , 0 , false , false),
	};

	public static final String _TableCode = "Organ";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into Organ values(?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update Organ set organ_code=?,organ_name=?,oragan_respons=?,parent_organ=?,organ_level=? where organ_code=?";

	protected static final String _DeleteSQL = "delete from Organ  where organ_code=?";

	protected static final String _FillAllSQL = "select * from Organ  where organ_code=?";

	public BOrganSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new BOrganSchema();
	}

	protected SchemaSet newSet(){
		return new BOrganSet();
	}

	public BOrganSet query() {
		return query(null, -1, -1);
	}

	public BOrganSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BOrganSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BOrganSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BOrganSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){organ_code = (String)v;return;}
		if (i == 1){organ_name = (String)v;return;}
		if (i == 2){oragan_respons = (String)v;return;}
		if (i == 3){parent_organ = (String)v;return;}
		if (i == 4){organ_level = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return organ_code;}
		if (i == 1){return organ_name;}
		if (i == 2){return oragan_respons;}
		if (i == 3){return parent_organ;}
		if (i == 4){return organ_level;}
		return null;
	}

	public String getOrgan_code() {
		return organ_code;
	}

	public void setOrgan_code(String organCode) {
		organ_code = organCode;
	}

	public String getOrgan_name() {
		return organ_name;
	}

	public void setOrgan_name(String organName) {
		organ_name = organName;
	}

	public String getOragan_respons() {
		return oragan_respons;
	}

	public void setOragan_respons(String oraganRespons) {
		oragan_respons = oraganRespons;
	}

	public String getParent_organ() {
		return parent_organ;
	}

	public void setParent_organ(String parentOrgan) {
		parent_organ = parentOrgan;
	}

	public String getOrgan_level() {
		return organ_level;
	}

	public void setOrgan_level(String organLevel) {
		organ_level = organLevel;
	}

	

}