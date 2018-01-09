package com.sinosoft.cms.dataservice;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCQuestionGroupSchema;
import com.sinosoft.schema.ZCQuestionGroupSet;

public class QuestionTeam extends Page {
	
	/**
	 * 树形结构
	 * 
	 */
	public static void treeDataBind(TreeAction ta) {
		DataTable dt = new QueryBuilder(" select InnerCode,ParentInnerCode,TreeLevel,Name from ZCQuestionGroup").executeDataTable();
		ta.setRootText("问题分类列表");
		ta.setParentIdentifierColumnName("ParentInnerCode");
		ta.setIdentifierColumnName("InnerCode");
		ta.setBranchIcon("Icons/treeicon12.gif");
		ta.setLeafIcon("Icons/treeicon12.gif");
		ta.bindData(dt);
	}
	
	/**
	 * 初始化对话页面隐藏字段,查询下拉列表等
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initDialog(Mapx params) {
		String InnerCode=params.getString("InnerCode");
		String ParentInnerCode=params.getString("ParentInnerCode");
		if (StringUtil.isNotEmpty(InnerCode)) {
			ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
			group.setInnerCode(InnerCode);
			if (!group.fill()) {
				return params;
			}
			Mapx map = group.toMapx();
			ParentInnerCode=map.getString("ParentInnerCode");
			if(StringUtil.isNotEmpty(ParentInnerCode)&&!"0".equals(ParentInnerCode)){
				String ParentName=new QueryBuilder("select name from zcquestionGroup where InnerCode=?",ParentInnerCode).executeString();
				map.put("ParentName", ParentName);
				map.put("ParentInnerCode", ParentInnerCode);
			}
			return map;
		} else {
			String ParentName=new QueryBuilder("select name as ParentName from zcquestiongroup where InnerCode=?",ParentInnerCode).executeString();
			params.put("ParentInnerCode", ParentInnerCode);
			params.put("ParentName", ParentName);
			return params;
		}
	}
	/**
	 * 验证分类名重名
	 *
	 */
	public boolean verifyName(String Name){
		int count=new QueryBuilder("select count(*) from ZCQuestionGroup where Name=?",Name.trim()).executeInt();
		if(count>0&&StringUtil.isNotEmpty(Name)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 增加问题信息
	 * 
	 */
	public void add() {
		String ParentInnerCode=$V("ParentInnerCode");
		ZCQuestionGroupSchema group=new ZCQuestionGroupSchema();
		Transaction trans=new Transaction();
		group.setValue(Request);
		if(verifyName(group.getName())){
			Response.setLogInfo(0, "操作失败,分组名已经存在!");
			return;
		}
		if(StringUtil.isNotEmpty(ParentInnerCode)){
			group.setInnerCode(NoUtil.getMaxNo("GroupInnerCode", ParentInnerCode, 4));
			group.setParentInnerCode(ParentInnerCode);
			int parentTreelevel=new QueryBuilder("select TreeLevel from ZCQuestionGroup where InnerCode=?",ParentInnerCode).executeInt();
			group.setTreeLevel(parentTreelevel+1);
			trans.add(new QueryBuilder("update ZCQuestionGroup set IsLeaf='Y' where InnerCode=?",ParentInnerCode));
		}else{
			group.setInnerCode(NoUtil.getMaxNo("GroupInnerCode", 4));
			group.setParentInnerCode("0");
			group.setTreeLevel(1);
		}
		group.setOrderFlag(0);
		group.setAddUser(User.getUserName());
		Date currentDate=new Date();
		group.setAddTime(currentDate);
		trans.add(group, Transaction.INSERT);
		if (trans.commit()) {
			Response.setLogInfo(1, "操作成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}

	}

	/**
	 * 修改问题信息
	 * 
	 */
	public void edit() {
		ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
		String innerCode = $V("InnerCode");
		group.setInnerCode(innerCode);
		group.fill();
		group.setName($V("Name"));
		group.setModifyTime(new Date());
		group.setModifyUser(User.getUserName());
		if (group.update()) {
			Response.setLogInfo(1, "操作成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}

	}

	/**
	 * 删除问题信息
	 * 
	 */
	public void del() {
		String InnerCode = $V("InnerCode");
		if (StringUtil.isEmpty(InnerCode)) {
			Response.setStatus(0);
			Response.setMessage("传入InnerCode时发生错误!");
			return;
		}
		Transaction trans=new Transaction();
		ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
		ZCQuestionGroupSet questionset = group.query(new QueryBuilder("where InnerCode = ?",InnerCode));
		trans.add(questionset,Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "操作成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}
}
