package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCQuestionSchema;
import com.sinosoft.schema.ZCQuestionSet;

import java.util.Date;

/**
 * 
 * @author 谭喜才
 * @mail 0871huhu@sina.com
 * @date 2009-6-23
 */

public class Question extends Page {

	// static Mapx AutoTransferFlag=new Mapx();
	// static {
	// AutoTransferFlag.put("N","否");
	// AutoTransferFlag.put("Y","是");
	// }
	/**
	 * 问题数据绑定
	 * 
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String InnerCode = (String) dga.getParam("QuestionInnerCode");
		QueryBuilder qb = new QueryBuilder("select *  from zcquestion where 1=1 ");
		if (StringUtil.isEmpty(InnerCode)) {
			logger.info("没有得到InnerCode的值,历史InnerCode：{}", dga.getParams().getString("Cookie.Ask.InnerCode"));
			qb.append(" and QuestionInnerCode = ?");
			InnerCode = dga.getParam("Cookie.Ask.InnerCode");
		} else {
			qb.append(" and QuestionInnerCode = ?");
		}
		qb.append(" order by AddTime desc");
		qb.add(InnerCode.trim());
		dga.bindData(qb);
	}

	/**
	 * 初始化对话页面隐藏字段,查询下拉列表等
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initDialog(Mapx params) {
		String ID = params.getString(("ID"));
		String questionInnerCode = params.getString("QuestionInnerCode");
		if (StringUtil.isNotEmpty(ID)) {
			ZCQuestionSchema question = new ZCQuestionSchema();
			question.setID(ID);
			if (!question.fill()) {
				return params;
			}
			Mapx map = question.toMapx();
			String questionName = new QueryBuilder("select name from zcquestiongroup where InnerCode=?",
					questionInnerCode).executeString();
			map.put("QuestionInnerCode", questionInnerCode);
			map.put("QuestionName", questionName);
			return map;
		} else {
			String questionName = new QueryBuilder("select name from zcquestiongroup where InnerCode=?",
					questionInnerCode).executeString();
			params.put("QuestionInnerCode", questionInnerCode);
			params.put("QuestionName", questionName);
			return params;
		}
	}

	/**
	 * 
	 *
	 */
	// public boolean verifyName(String Name){
	// int count=new
	// QueryBuilder("select count(*) from ZSTeam where Name=?",Name.trim()).executeInt();
	// if(count>0&&StringUtil.isNotEmpty(Name)){
	// return true;
	// }else{
	// return false;
	// }
	// }

	/**
	 * 增加问题信息
	 * 
	 */
	public void add() {
		String questionInnerCode = $V("QuestionInnerCode");
		if (StringUtil.isEmpty(questionInnerCode)) {
			Response.setLogInfo(0, "操作失败，请选择分类!");
			return;
		}
		ZCQuestionSchema question = new ZCQuestionSchema();
		question.setID(NoUtil.getMaxID("QuestionID"));
		question.setValue(Request);
		question.setQuestionInnerCode(questionInnerCode);
		question.setAddUser(User.getUserName());
		Date currentDate = new Date();
		question.setAddTime(currentDate);
		if (question.insert()) {
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
		ZCQuestionSchema question = new ZCQuestionSchema();
		String id = $V("ID");
		question.setID(id);
		question.fill();
		question.setValue(Request);
		question.setModifyTime(new Date());
		question.setModifyUser(User.getUserName());
		if (question.update()) {
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
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCQuestionSchema question = new ZCQuestionSchema();
		ZCQuestionSet questionset = question.query(new QueryBuilder("where ID in (" + ids + ")"));
		trans.add(questionset, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "操作成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}
}
