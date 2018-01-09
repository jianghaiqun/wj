package com.sinosoft.message;

import com.sinosoft.cms.datachannel.PublishMonitor;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCAnswerSchema;
import com.sinosoft.schema.SCQuestionSchema;
import com.sinosoft.schema.SCQuestionSet;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;
import com.sinosoft.schema.memberSchema;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author周翔
 * @Date 2012-6-11
 * @Mail zhouxiang@sinosoft.com.cn
 */

public class AuditAnswerUI extends Page {
	
	
	
	public void save() {
		String tID = $V("ID");
		String tQuestionId = $V("QuestionId");
		Transaction trans = new Transaction();
		// 获取问题信息
		SCQuestionSchema tSCQuestionSchema = new SCQuestionSchema();
		SCQuestionSet tSCQuestionSet = tSCQuestionSchema
				.query(new QueryBuilder("where QuestionId ='" + tQuestionId
						+ "'"));
		if (tSCQuestionSet.size() > 1) {
			Response.setError("操作失败!内部数据出错!");
			return;
		}
		tSCQuestionSchema = tSCQuestionSet.get(0);
		tSCQuestionSchema.setState("01");
		trans.add(tSCQuestionSchema, Transaction.UPDATE);
		// 修改答案采纳时间
		SCAnswerSchema tSCAnswerSchema = new SCAnswerSchema();
		tSCAnswerSchema.setId(tID);
		tSCAnswerSchema.fill();
		tSCAnswerSchema.setaState("01");
		tSCAnswerSchema.setaDate(new Date());
		tSCAnswerSchema.setOperator(User.getRealName());
		trans.add(tSCAnswerSchema, Transaction.UPDATE);
		long tArticleId = Long.parseLong(tQuestionId);
		ZCArticleSchema tZCArticleSchema = new ZCArticleSchema();
		tZCArticleSchema.setID(tArticleId);
		tZCArticleSchema.fill();
		tZCArticleSchema.setStatus("60");
		trans.add(tZCArticleSchema, Transaction.UPDATE);
		
		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(tSCAnswerSchema.getUserId());
		tmemberSchema.fill();
		String name = "";
		if (StringUtil.isNotEmpty(tmemberSchema.getusername())) {
			name = tmemberSchema.getusername();
		} else if (StringUtil.isNotEmpty(tmemberSchema.getemail())) {
			name = tmemberSchema.getemail().substring(0, 4) + "********";
		} else if (StringUtil.isNotEmpty(tmemberSchema.getmobileNO())
				&& tmemberSchema.getmobileNO().length() >= 11) {
			name = tmemberSchema.getmobileNO().substring(0, 3) + "****"
					+ tmemberSchema.getmobileNO().substring(7, 11);
		}

		QueryBuilder qb = new QueryBuilder();
		qb.setSQL("select textvalue from ZDColumnValue where relaid=? and columncode='isOk' ");
		qb.add(tArticleId + "");
		String isOk = qb.executeString();
		boolean isUpdate = true;
		if ("solve".equals(isOk)
				&& StringUtil.isNotEmpty(tZCArticleSchema.getContent())) {
			isUpdate = false;
		}
		if (isUpdate) {
			updateCustomColumn(trans, tArticleId, tSCAnswerSchema.getContent(),
					name, tSCAnswerSchema.getAddDate());
		}

		try {
			// 从回答表里取得回答的信息，进行多条显示
			qb = new QueryBuilder();
			String sql = "select UserId, MAX(adddate) as addDate, Content from scanswer where QuestionId = ? and (aState = '01' ";
			if (!isUpdate) {
				sql += " or id =" + tID;
			}
			sql += ") group by Content,UserId order by adddate desc ";
			qb.setSQL(sql);
			qb.add(tArticleId + "");
			DataTable result = qb.executeDataTable();
			// 其他答案
			String qitadaan = "";
			int count = 0;
			if (result != null && result.getRowCount() > 0) {
				for (int i = 0; i < result.getRowCount(); i++) {
					// 回答内容有数据的情况
					if (StringUtil.isNotEmpty(result.get(i)
							.getString("Content"))) {
						// 根据回答者ID取得回答者名
						tmemberSchema = new memberSchema();
						tmemberSchema.setid(result.get(i).getString("UserId"));
						tmemberSchema.fill();
						String userName = "";
						if (StringUtil.isNotEmpty(tmemberSchema.getusername())) {
							userName = tmemberSchema.getusername();
						} else if (StringUtil.isNotEmpty(tmemberSchema
								.getemail())) {
							userName = tmemberSchema.getemail().substring(0, 4)
									+ "********";
						} else if (StringUtil.isNotEmpty(tmemberSchema
								.getmobileNO())
								&& tmemberSchema.getmobileNO().length() >= 11) {
							userName = tmemberSchema.getmobileNO().substring(0,
									3)
									+ "****"
									+ tmemberSchema.getmobileNO().substring(7,
											11);
						}
						// 时间格式化
						Date addDate = result.get(i).getDate("addDate");
						SimpleDateFormat formatter1 = new SimpleDateFormat(
								"yyyy-MM-dd");
						String replyTime = formatter1.format(addDate);
						qitadaan += "<div class='answerdetail' ><div class='icon'></div><div class='answercont'><p>"
								+ result.get(i).getString("Content")
								+ "</p></div><h4>"
								+ userName
								+ "&nbsp;&nbsp;|&nbsp;&nbsp;回复于 "
								+ replyTime + "</h4></div>";
						count++;
						if (count >= 9) {
							break;
						}
					}
				}
				if (StringUtil.isNotEmpty(qitadaan)) {
					qb = new QueryBuilder(
							"select count(*) from ZDColumnValue where relaid=? and columncode='qitadaan'",
							tArticleId + "");
					if (qb.executeInt() == 0) {
						qb = new QueryBuilder(
								"select ColumnId, ColumnCode from zdcolumnrela where relatype=1 and relaid=? and columncode='qitadaan' ",
								tZCArticleSchema.getCatalogID() + "");
						DataTable dt = qb.executeDataTable();
						if (dt != null && dt.getRowCount() > 0) {
							ZDColumnValueSchema value = new ZDColumnValueSchema();
							value.setID(NoUtil.getMaxID("ColumnValueID"));
							value.setColumnID(dt.get(0).getString("ColumnId"));
							value.setColumnCode(dt.get(0).getString(
									"ColumnCode"));
							value.setRelaType(ColumnUtil.RELA_TYPE_DOCID);
							value.setRelaID(tArticleId + "");
							value.setTextValue(qitadaan);
							trans.add(value, Transaction.INSERT);
						}

					} else {
						trans.add(new QueryBuilder(
								"update ZDColumnValue set textvalue=? where relaid=? and columncode='qitadaan'",
								qitadaan, tArticleId + ""));
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// saveCustomColumn(trans, tZCArticleSchema.getCatalogID(),tArticleId,
		// true,tSCAnswerSchema.getContent(),name,tSCAnswerSchema.getAddDate());
		// map.put("CatalogName", CatalogUtil.getName(catalogID));
		try {
			if (!trans.commit()) {
				Response.setError("操作失败!");
			} else {
				publish(tArticleId);
			}
		} catch (Exception e) {
			Response.setError("系统异常!");
		}

	}
	
	private void updateCustomColumn(Transaction trans, long tArticleId, String content, String name, Date addDate) {

		trans.add(new QueryBuilder("update  ZCArticle set Content=? where ID=? ",content, tArticleId + ""));
		trans.add(new QueryBuilder("update ZDColumnValue set textvalue=? where relaid=? and columncode='isOk'","solve", tArticleId + ""));
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");//定时使用
		String temp2 = formatter1.format(addDate);
		trans.add(new QueryBuilder("update ZDColumnValue set textvalue=? where relaid=? and columncode='ReplyTime'",temp2, tArticleId + ""));
		trans.add(new QueryBuilder("update ZDColumnValue set textvalue=? where relaid=? and columncode='ReplyUser'",name, tArticleId + ""));	
	}

	/**
	 * 发布
	 * 
	 */
	public boolean publish(long articleID) {
		ZCArticleSchema article = new ZCArticleSchema();
		//int articleID = Integer.parseInt((String) Request.get("ArticleID"));

		ZCArticleSet set = new ZCArticleSet();
		article.setID(articleID);
		if (article.fill()) {
			set.add(article);
		}
		ZCArticleSet referset = article.query(new QueryBuilder(" where refersourceid=? ", articleID));
		if (referset.size() > 0) {
			for (int i = 0; i < referset.size(); i++) {
				String catalogInnerCode = referset.get(i).getCatalogInnerCode();
				boolean hasPriv = Priv.getPriv(User.getUserName(), Priv.ARTICLE, catalogInnerCode, Priv.ARTICLE_MANAGE);
				String workflow = CatalogUtil.getWorkflow(referset.get(i).getCatalogID());
				// 如果目标栏目没有工作流且用户有目标栏目的文章管理权限，则发布时一起更新为发布状态
				if (hasPriv && StringUtil.isEmpty(workflow)) {
					set.add(referset.get(i));
				}
			}
		}

		// 更新发布状态
		Transaction trans = new Transaction();
		for (int i = 0; i < set.size(); i++) {
			Date date = new Date();// 必须更新modifyTime，全文检索以此为标记查找更新
			ZCArticleSchema article2 = set.get(i);
			QueryBuilder qb = new QueryBuilder("update zcarticle set modifyuser=?,modifytime=?,status=? where id=?");
			if (article2.getPublishDate() == null) {
				qb = new QueryBuilder(
						"update zcarticle set publishdate=?,modifyuser=?,modifytime=?,status=? where id=?");
				qb.add(date);
				article2.setPublishDate(date);
			}
			qb.add(User.getUserName());
			qb.add(date);
			if (article2.getPublishDate().getTime() > System.currentTimeMillis()) {
				qb.add(Article.STATUS_TOPUBLISH);
			} else {
				qb.add(Article.STATUS_PUBLISHED);
			}
			qb.add(article2.getID());
			qb.executeNoQuery();
			article2.setModifyUser(User.getUserName());
			article.setModifyTime(date);
			article.setStatus(Article.STATUS_PUBLISHED);
		}

		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, "发布文章:" + article.getTitle() + "成功！", Request
				.getClientIP(), trans);

		if (trans.commit()) {
			// 将生成文件的动作交给后台进程执行
			PublishMonitor m = new PublishMonitor();
			m.addJob(set);

			Response.setMessage("操作成功!");
			return true;
		} else {
			Response.setError("操作失败!");
			return false;
		}
	}
	
	public static void saveCustomColumn(Transaction trans, long catalogID, long articleID, boolean newFlag,String answerContent, String replyUser, Date replyTime) {
		if (!newFlag) {
			DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, articleID);
			if (columnDT.getRowCount() > 0) {
				trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
						ColumnUtil.RELA_TYPE_DOCID, articleID + ""));
			}
			trans.add(getValueFromRequest(catalogID, articleID,answerContent,replyUser,replyTime), Transaction.INSERT);
		} else {
			trans.add(getValueFromRequest(catalogID, articleID,answerContent,replyUser,replyTime), Transaction.INSERT);
		}
	}
	
	private static SchemaSet getValueFromRequest(long catalogID, long docID,String answerContent, String replyUser, Date replyTime) {
		DataTable dt = ColumnUtil.getColumn(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID);
		ZDColumnValueSet set = new ZDColumnValueSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDColumnValueSchema value = new ZDColumnValueSchema();
			value.setID(NoUtil.getMaxID("ColumnValueID"));
			value.setColumnID(dt.getString(i, "ID"));
			value.setColumnCode(dt.getString(i, "Code"));
			value.setRelaType(ColumnUtil.RELA_TYPE_DOCID);
			value.setRelaID(docID + "");
			if("cainadaan".equals(dt.getString(i, "Code"))){
				value.setTextValue(answerContent);
			}
			if("isOk".equals(dt.getString(i, "Code"))){
				value.setTextValue("solve");
			}
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");//定时使用
			String temp2 = formatter1.format(replyTime);
			if("ReplyTime".equals(dt.getString(i, "Code"))){
				value.setTextValue(temp2);
			}
			if("ReplyUser".equals(dt.getString(i, "Code"))){
				value.setTextValue(replyUser);
			}
			set.add(value);
		}
		return set;
	}
	
	public void del() {
		String tID= $V("ID");
		Transaction trans = new Transaction();
		SCAnswerSchema tSCAnswerSchema=new SCAnswerSchema();
		tSCAnswerSchema.setId(tID);
		tSCAnswerSchema.fill();
		tSCAnswerSchema.setaState("03");
		trans.add(tSCAnswerSchema, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除提问成功");
		} else {
			Response.setLogInfo(0, "删除提问失败");
		}
	}
	public static void dg1DataBind(DataListAction dla) {
//		String CatalogID = dla.getParam("CatalogID");
//		String CatalogType = dla.getParam("CatalogType");
	    String VerifyStatus = dla.getParam("VerifyStatus");
	    String tQTitle = dla.getParam("QTitle");
		String tQUser = dla.getParam("QUser");
		String tQTime = dla.getParam("QTime");
	    if("".equals(VerifyStatus)||VerifyStatus==null){
	    	VerifyStatus="All";
	    }
		String str="";
		if(!"All".equals(VerifyStatus)&&!"".equals(VerifyStatus)){
			str=str+" and State='"+VerifyStatus+"'";
		}
		if(!"".equals(tQTitle)&&tQTitle!=null){
			str=str+" and title like '%"+tQTitle+"%'";
	    }
		if(!"".equals(tQUser)&&tQUser!=null){
			str=str+" and userid like '%"+tQUser+"%'";
	    }
		if(!"".equals(tQTime)&&tQTime!=null){
			str=str+" and date_format(adddate,'%Y-%m-%d') = '"+tQTime+"'";
	    }
		String strSQL = "select *,if(state='00','未解决','已解决') as jstate,if (astate='00','未审核',if(astate='01','审核通过','审核未通过')) as sstate,(select email from member b where b.id=a.userid) as UserEmail from SCQuestion a where 1=1  and aState='01' "+str+" order by adate desc";
		
		dla.setTotal(new QueryBuilder(strSQL));
	
		DataTable dt = new QueryBuilder(strSQL).executePagedDataTable(
				dla.getPageSize(), dla.getPageIndex());
		dla.bindData(dt);
	}
	
	public static void dg1DataBind1(DataListAction dla) {
		String tQuestionId = dla.getParam("QuestionId");
		String tStatus = dla.getParam("Status");
		String str=" and QuestionId='"+tQuestionId+"'";
		if("".equals(tStatus)||tStatus==null){
			tStatus="All";
	    }
		if(!"All".equals(tStatus)&&!"".equals(tStatus)){
			str+=" and aState='"+tStatus+"'";
		}else{
			str+=" and astate in('00','01','02')";
		}
		
		String strSQL = "select *,if(astate='00','未采纳','已采纳') as cstate,(select email from member b where b.id=a.userid) as UserEmail from SCAnswer a where 1=1 "+str;
		
		dla.setTotal(new QueryBuilder(strSQL));
	
		DataTable dt = new QueryBuilder(strSQL).executePagedDataTable(
				dla.getPageSize(), dla.getPageIndex());
		dla.bindData(dt);
	}
	
}