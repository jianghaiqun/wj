package com.sinosoft.workflow;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZWWorkflowSchema;
import com.sinosoft.schema.ZWWorkflowSet;

import java.util.Date;

/**
 * 后台逻辑，响应UI请求
 * 
 */
public class WorkflowPage extends Page {
	public static Mapx init(Mapx params) {
		String id = params.getString("ID");
		if (StringUtil.isNotEmpty(id)) {
			ZWWorkflowSchema fc = new ZWWorkflowSchema();
			fc.setID(id);
			fc.fill();
			params.put("Name", fc.getName());
			params.put("ID", fc.getID());
			params.put("Memo", fc.getMemo());
			params.put("XML", StringUtil.javaEncode(fc.getConfigXML()));
		}

		return params;
	}

	public static void roleDataBind(DataGridAction dga) {
		String  sql="select RoleCode,RoleName from ZDRole";
		logger.info("{}==User.getBranchInnerCode()",User.getBranchInnerCode());
		if(!"admin".equals(User.getUserName())){
		   sql=	sql+" where BranchInnerCode='"+User.getBranchInnerCode()+"'";
		}
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		dga.bindData(dt);
	}

	public void save() {
		ZWWorkflowSchema wd = new ZWWorkflowSchema();
		if (StringUtil.isNotEmpty($V("ID"))) {
			wd.setID($V("ID"));
			wd.fill();
		} else {
			wd.setID(NoUtil.getMaxID("WorkflowID"));
			wd.setAddTime(new Date());
			wd.setAddUser(User.getUserName());
		}
		wd.setModifyTime(new Date());
		wd.setModifyUser(User.getUserName());
		wd.setName($V("Name"));
		wd.setConfigXML(StringUtil.htmlDecode($V("XML")));
		wd.setMemo($V("Memo"));
		Response.put("ID", wd.getID());
		boolean flag = true;
		if (StringUtil.isNotEmpty($V("ID"))) {
			flag = wd.update();
		} else {
			flag = wd.insert();
		}
		if (flag) {
			WorkflowUtil.updateCache(wd);
			Response.setMessage("保存成功!");
		} else {
			Response.setError("保存数据到数据库时发生错误!");
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = " select * from ZWWorkflow";
		dga.bindData(new QueryBuilder(sql));
	}

	public void del() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setLogInfo(0, "传入工作流发生错误!");
			return;
		}
		ZWWorkflowSchema wf = new ZWWorkflowSchema();
		ZWWorkflowSet set = wf.query(new QueryBuilder("where ID in (" + IDs + ")"));
		Transaction tran = new Transaction();
		for (int i = 0; i < set.size(); i++) {
			WorkflowUtil.findAdapter().onWorkflowDelete(tran, set.get(i).getID());
		}
		tran.add(new QueryBuilder("delete from ZWInstance where WorkflowID in (" + IDs + ")"));
		tran.add(new QueryBuilder("delete from ZWStep where WorkflowID in (" + IDs + ")"));
		tran.add(set, Transaction.DELETE_AND_BACKUP);
		if (tran.commit()) {
			for (int i = 0; i < set.size(); i++) {
				WorkflowUtil.deleteCache(set.get(i));
			}
			Response.setLogInfo(1, "删除成功！");
		} else {
			Response.setLogInfo(0, "删除失败！");
		}
	}
}
