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
public class BPersonnelSchema extends Schema {

	private String user_id;
	private String user_name;
	private String user_organ;
	private String user_team;
	private String user_post;
	private String user_sex;
	private String user_tel;
	private String user_addr;
	private String user_interest;
	private String user_speciality;
	private String user_motto;
	
	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("user_id", DataColumn.STRING, 0, 18 , 0 , true , true),
		new SchemaColumn("user_name", DataColumn.STRING, 1, 18 , 0 , true , false),
		new SchemaColumn("user_organ", DataColumn.STRING, 2, 4 , 0 , true , false),
		new SchemaColumn("user_team", DataColumn.STRING, 3, 4 , 0 , true , false),
		new SchemaColumn("user_post", DataColumn.STRING, 4, 6 , 0 , false , false),
		new SchemaColumn("user_sex", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("user_tel", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("user_addr", DataColumn.STRING, 7, 60 , 0 , false , false),
		new SchemaColumn("user_interest", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("user_speciality", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("user_motto", DataColumn.STRING, 10, 400 , 0 , false , false)
	};

	public static final String _TableCode = "userinfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into userinfo values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update userinfo set user_id=?,user_name=?,user_organ=?,user_team=?,user_post=?,user_sex=?,user_tel=?,user_addr=?,user_interest=?,user_speciality=?,user_motto=? where user_id=?";

	protected static final String _DeleteSQL = "delete from userinfo where user_id=?";

	protected static final String _FillAllSQL = "select * from userinfo where user_id=?";

	public BPersonnelSchema(){
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
		return new BPersonnelSchema();
	}

	protected SchemaSet newSet(){
		return new BPersonnelSet();
	}

	public BPersonnelSet query() {
		return query(null, -1, -1);
	}

	public BPersonnelSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BPersonnelSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BPersonnelSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BPersonnelSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){user_id = (String)v;return;}
		if (i == 1){user_name = (String)v;return;}
		if (i == 2){user_organ = (String)v;return;}
		if (i == 3){user_team = (String)v;return;}
		if (i == 4){user_post = (String)v;return;}
		if (i == 5){user_sex = (String)v;return;}
		if (i == 6){user_tel = (String)v;return;}
		if (i == 7){user_addr = (String)v;return;}
		if (i == 8){user_interest = (String)v;return;}
		if (i == 9){user_speciality = (String)v;return;}
		if (i == 10){user_motto = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return user_id;}
		if (i == 1){return user_name;}
		if (i == 2){return user_organ;}
		if (i == 3){return user_team;}
		if (i == 4){return user_post;}
		if (i == 5){return user_sex;}
		if (i == 6){return user_tel;}
		if (i == 7){return user_addr;}
		if (i == 8){return user_interest;}
		if (i == 9){return user_speciality;}
		if (i == 10){return user_motto;}
		return null;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String userName) {
		user_name = userName;
	}

	public String getUser_organ() {
		return user_organ;
	}

	public void setUser_organ(String userOrgan) {
		user_organ = userOrgan;
	}

	public String getUser_team() {
		return user_team;
	}

	public void setUser_team(String userTeam) {
		user_team = userTeam;
	}

	public String getUser_post() {
		return user_post;
	}

	public void setUser_post(String userPost) {
		user_post = userPost;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String userSex) {
		user_sex = userSex;
	}

	public String getUser_tel() {
		return user_tel;
	}

	public void setUser_tel(String userTel) {
		user_tel = userTel;
	}

	public String getUser_addr() {
		return user_addr;
	}

	public void setUser_addr(String userAddr) {
		user_addr = userAddr;
	}

	public String getUser_interest() {
		return user_interest;
	}

	public void setUser_interest(String userInterest) {
		user_interest = userInterest;
	}

	public String getUser_speciality() {
		return user_speciality;
	}

	public void setUser_speciality(String userSpeciality) {
		user_speciality = userSpeciality;
	}

	public String getUser_motto() {
		return user_motto;
	}

	public void setUser_motto(String userMotto) {
		user_motto = userMotto;
	}

	

	

}