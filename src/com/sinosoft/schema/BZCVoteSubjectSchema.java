package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：投票题目表备份<br>
 * 表代码：BZCVoteSubject<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCVoteSubjectSchema extends Schema {
	private Long ID;

	private Long VoteID;

	private String Type;

	private String Subject;

	private Long VoteCatalogID;

	private Long OrderFlag;

	private String Prop1;

	private String Prop2;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("VoteID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 2, 1 , 0 , true , false),
		new SchemaColumn("Subject", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("VoteCatalogID", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 5, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 8, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 9, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 11, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCVoteSubject";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZCVoteSubject values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCVoteSubject set ID=?,VoteID=?,Type=?,Subject=?,VoteCatalogID=?,OrderFlag=?,Prop1=?,Prop2=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCVoteSubject  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCVoteSubject  where ID=? and BackupNo=?";

	public BZCVoteSubjectSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new BZCVoteSubjectSchema();
	}

	protected SchemaSet newSet(){
		return new BZCVoteSubjectSet();
	}

	public BZCVoteSubjectSet query() {
		return query(null, -1, -1);
	}

	public BZCVoteSubjectSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCVoteSubjectSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCVoteSubjectSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCVoteSubjectSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){VoteID = null;}else{VoteID = new Long(v.toString());}return;}
		if (i == 2){Type = (String)v;return;}
		if (i == 3){Subject = (String)v;return;}
		if (i == 4){if(v==null){VoteCatalogID = null;}else{VoteCatalogID = new Long(v.toString());}return;}
		if (i == 5){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
		if (i == 8){BackupNo = (String)v;return;}
		if (i == 9){BackupOperator = (String)v;return;}
		if (i == 10){BackupTime = (Date)v;return;}
		if (i == 11){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return VoteID;}
		if (i == 2){return Type;}
		if (i == 3){return Subject;}
		if (i == 4){return VoteCatalogID;}
		if (i == 5){return OrderFlag;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		if (i == 8){return BackupNo;}
		if (i == 9){return BackupOperator;}
		if (i == 10){return BackupTime;}
		if (i == 11){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :投票标题ID<br>
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
	* 字段名称 :投票标题ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :投票标题ID<br>
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
	* 获取字段VoteID的值，该字段的<br>
	* 字段名称 :投票ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getVoteID() {
		if(VoteID==null){return 0;}
		return VoteID.longValue();
	}

	/**
	* 设置字段VoteID的值，该字段的<br>
	* 字段名称 :投票ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVoteID(long voteID) {
		this.VoteID = new Long(voteID);
    }

	/**
	* 设置字段VoteID的值，该字段的<br>
	* 字段名称 :投票ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVoteID(String voteID) {
		if (voteID == null){
			this.VoteID = null;
			return;
		}
		this.VoteID = new Long(voteID);
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :单选多选<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :单选多选<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Subject的值，该字段的<br>
	* 字段名称 :投票题目<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* 设置字段Subject的值，该字段的<br>
	* 字段名称 :投票题目<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* 获取字段VoteCatalogID的值，该字段的<br>
	* 字段名称 :投票栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getVoteCatalogID() {
		if(VoteCatalogID==null){return 0;}
		return VoteCatalogID.longValue();
	}

	/**
	* 设置字段VoteCatalogID的值，该字段的<br>
	* 字段名称 :投票栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteCatalogID(long voteCatalogID) {
		this.VoteCatalogID = new Long(voteCatalogID);
    }

	/**
	* 设置字段VoteCatalogID的值，该字段的<br>
	* 字段名称 :投票栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteCatalogID(String voteCatalogID) {
		if (voteCatalogID == null){
			this.VoteCatalogID = null;
			return;
		}
		this.VoteCatalogID = new Long(voteCatalogID);
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序权值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序权值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序权值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
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
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}