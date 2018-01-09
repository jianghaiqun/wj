package com.sinosoft.cms.dataservice;

import java.util.Date;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.schema.ZDMemberRankSchema;
import com.sinosoft.schema.ZDMemberRankSet;


public class MemberLevel extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select MconfigCode,MconfigName,"
        +"MconfigValue,MconfigCode as MconfigCode_key from ZDMemberRank");
		dga.bindData(qb);
	}

	public void add() {
		ZDMemberRankSchema ZDMemberRank = new ZDMemberRankSchema();
		ZDMemberRank.setMconfigName($V("Name").trim());
		int FieldCount = new QueryBuilder("select count(*) from ZDMemberRank "
		+"where MconfigName = ?",$V("Name").trim()).executeInt();
		
		if(FieldCount > 0){
			Response.setLogInfo(0,"已有相同的配置名称！");
			return;
		}
		FieldCount = new QueryBuilder("select count(*) from ZDMemberRank "
		+"where MconfigCode = ?",$V("TreeLevel").trim()).executeInt();
		
		if(FieldCount > 0){
			Response.setLogInfo(0,"已有相同的配置代码！");
			return;
		}
		FieldCount = new QueryBuilder("select count(*) from ZDMemberRank "
		+"where MconfigValue = ?",$V("Score").trim()).executeInt();

		if(FieldCount > 0){
			Response.setLogInfo(0,"已有相同的配置值！");
			return;
		}
		ZDMemberRank = new ZDMemberRankSchema();
		ZDMemberRank.setValue(Request);
		ZDMemberRank.setMconfigCode($V("TreeLevel").trim());
		ZDMemberRank.setMconfigName($V("Name").trim());
		ZDMemberRank.setMconfigValue($V("Score").trim());
		ZDMemberRank.setModifyTime(new Date());
		ZDMemberRank.setModifyUser(User.getUserName());
		ZDMemberRank.setAddUser(User.getUserName());
		ZDMemberRank.setAddTime(new Date());
		if (ZDMemberRank.insert()) {
			Response.setStatus(1);
			Response.setMessage("新增会员配置项成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误！");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String temp[] = ids.split(",");
		for(int i=0;i<temp.length;i++){
			if(i==0){
				ids="'"+temp[i]+"'";
			}else{
				ids+=",'"+temp[i]+"'";
			}
		}
		Transaction trans = new Transaction();
		ZDMemberRankSchema ZDMemberRank = new ZDMemberRankSchema();
		ZDMemberRankSet set = ZDMemberRank.query(
				new QueryBuilder("where MconfigCode in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder("update ZDMemberRank "
		+"set MconfigCode=?,MconfigName=?,MconfigValue=? where MconfigCode=?");
			qb.add(dt.getString(i, "MconfigCode"));
			qb.add(dt.getString(i, "MconfigName"));
			qb.add(dt.getString(i, "MconfigValue"));
			qb.add(dt.getString(i, "MconfigCode_key"));
			trans.add(qb);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}
}
