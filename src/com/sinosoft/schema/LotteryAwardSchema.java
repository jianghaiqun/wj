package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：奖品明细表<br>
 * 表代码：LotteryAward<br>
 * 表主键：ID, AwardQuantum<br>
 */
public class LotteryAwardSchema extends Schema {
	private String ID;

	private String AwardDetail;

	private String AwardNumber;

	private String AwardQuantum;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 5 , 0 , true , true),
		new SchemaColumn("AwardDetail", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("AwardNumber", DataColumn.STRING, 2, 3 , 0 , false , false),
		new SchemaColumn("AwardQuantum", DataColumn.STRING, 3, 5 , 0 , true , true)
	};

	public static final String _TableCode = "LotteryAward";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LotteryAward values(?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LotteryAward set ID=?,AwardDetail=?,AwardNumber=?,AwardQuantum=? where ID=? and AwardQuantum=?";

	protected static final String _DeleteSQL = "delete from LotteryAward  where ID=? and AwardQuantum=?";

	protected static final String _FillAllSQL = "select * from LotteryAward  where ID=? and AwardQuantum=?";

	public LotteryAwardSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[4];
	}

	protected Schema newInstance(){
		return new LotteryAwardSchema();
	}

	protected SchemaSet newSet(){
		return new LotteryAwardSet();
	}

	public LotteryAwardSet query() {
		return query(null, -1, -1);
	}

	public LotteryAwardSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LotteryAwardSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LotteryAwardSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LotteryAwardSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){AwardDetail = (String)v;return;}
		if (i == 2){AwardNumber = (String)v;return;}
		if (i == 3){AwardQuantum = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AwardDetail;}
		if (i == 2){return AwardNumber;}
		if (i == 3){return AwardQuantum;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :VARCHAR(5)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :VARCHAR(5)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段AwardDetail的值，该字段的<br>
	* 字段名称 :奖项描述<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAwardDetail() {
		return AwardDetail;
	}

	/**
	* 设置字段AwardDetail的值，该字段的<br>
	* 字段名称 :奖项描述<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAwardDetail(String awardDetail) {
		this.AwardDetail = awardDetail;
    }

	/**
	* 获取字段AwardNumber的值，该字段的<br>
	* 字段名称 :奖项个数<br>
	* 数据类型 :VARCHAR(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAwardNumber() {
		return AwardNumber;
	}

	/**
	* 设置字段AwardNumber的值，该字段的<br>
	* 字段名称 :奖项个数<br>
	* 数据类型 :VARCHAR(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAwardNumber(String awardNumber) {
		this.AwardNumber = awardNumber;
    }

	/**
	* 获取字段AwardQuantum的值，该字段的<br>
	* 字段名称 :奖项阶段<br>
	* 数据类型 :VARCHAR(5)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getAwardQuantum() {
		return AwardQuantum;
	}

	/**
	* 设置字段AwardQuantum的值，该字段的<br>
	* 字段名称 :奖项阶段<br>
	* 数据类型 :VARCHAR(5)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setAwardQuantum(String awardQuantum) {
		this.AwardQuantum = awardQuantum;
    }

}