package com.sinosoft.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.cms.datachannel.PublishMonitor;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.SCQuestionSchema;
import com.sinosoft.schema.SCQuestionSet;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;
import com.sinosoft.schema.memberSchema;

/**
 * @Author周翔
 * @Date 2012-6-11
 * @Mail zhouxiang@sinosoft.com.cn
 */

public class AuditQuestionUI extends Page {
	
	
	
	public void save() {
		
		String IDs = $V("IDs");
		String Type = $V("Type");
		
		//获取问题信息
		SCQuestionSchema tSCQuestionSchema=new SCQuestionSchema();
		SCQuestionSet tSCQuestionSet = tSCQuestionSchema.query(new QueryBuilder("where ID in (" + IDs + ")"));
		for (int i = 0; i < tSCQuestionSet.size(); i++) {
			Transaction trans = new Transaction();
			tSCQuestionSchema = tSCQuestionSet.get(i);
			long articleId=NoUtil.getMaxID("DocID");//文章id
			if (Type.equals("Pass")) {
				ZCArticleSchema tZCArticleSchema=new ZCArticleSchema();
				ZCArticleSet tZCArticleSet=tZCArticleSchema.query(new QueryBuilder("where ID ='"+tSCQuestionSchema.getQuestionId()+"'"));
				if(tZCArticleSet.size()>0){
					Response.setError("操作失败!'"+tSCQuestionSchema.getTitle()+"'该问题已审核");
					return;
				}
				tZCArticleSchema=new ZCArticleSchema();
				tZCArticleSchema.setID(articleId);
				String catalogID=$V("CatalogID");
				tZCArticleSchema.setSiteID(CatalogUtil.getSiteID(catalogID));//根据栏目取站点id
//				tZCArticleSchema.setSiteID(111);
				tZCArticleSchema.setCatalogID(catalogID);
				tZCArticleSchema.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
//				tZCArticleSchema.setCatalogInnerCode("111");
				tZCArticleSchema.setBranchInnerCode(User.getBranchInnerCode());
				tZCArticleSchema.setTitle(tSCQuestionSchema.getTitle());
				
				memberSchema tmemberSchema=new memberSchema();
				tmemberSchema.setid(tSCQuestionSchema.getUserId());
				if (!tmemberSchema.fill()) {
					Response.setError("用户不存在，无法审核！");
					return;
				}
				String name="";
				if (StringUtil.isNotEmpty(tmemberSchema.getusername())) {
					name = tmemberSchema.getusername();
				} else if(StringUtil.isNotEmpty(tmemberSchema.getemail())){
					name = tmemberSchema.getemail().substring(0, 4)+"********";
				}else{
					name=tmemberSchema.getmobileNO().substring(0, 3)+"****"+tmemberSchema.getmobileNO().substring(7, 11);
				}
				tZCArticleSchema.setAuthor(name);
				tZCArticleSchema.setType("1");
				tZCArticleSchema.setStatus("0");
//				tZCArticleSchema.setContent("<p>"+tSCQuestionSchema.getContent()+"</p>");
				tZCArticleSchema.setTopFlag("0");
				tZCArticleSchema.setTemplateFlag("0");
				tZCArticleSchema.setCommentFlag("1");
				tZCArticleSchema.setCopyImageFlag("Y");
				tZCArticleSchema.setOrderFlag(OrderUtil.getDefaultOrder());
				tZCArticleSchema.setHitCount(0);
				tZCArticleSchema.setStickTime(0);
				tZCArticleSchema.setPublishFlag("1");
				tZCArticleSchema.setPriority("1");
				tZCArticleSchema.setAddUser(User.getUserName());
				tZCArticleSchema.setAddTime(new Date());
				tZCArticleSchema.setSourceSign("A");
				String contentSign=$V("ContentSign");
				if(StringUtil.isEmpty(contentSign)){
					Response.setError("请先选择内容类型！");
					return;
				}else{
					tZCArticleSchema.setContentSign(contentSign);
				}
				tZCArticleSchema.setURL(PubFun.getArticleURL(tZCArticleSchema));
				tZCArticleSchema.setDownlineDate(DateUtil.parse("2099-12-31 23:59:59", DateUtil.Format_DateTime));
				String temp="";
				//ZCCatalogSchema catalog = CMSCache.getCatalog(tCatalogID);
				ZCCatalogSchema catalog=new ZCCatalogSchema();
				catalog.setID(catalogID);
				catalog.fill();
				ZCSiteSchema tZCSiteSchema=new ZCSiteSchema();
				tZCSiteSchema.setID(catalog.getSiteID());
				tZCSiteSchema.fill();
				String tSiteName=tZCSiteSchema.getName();
				temp=catalog.getName();
				long tParentID=catalog.getParentID();
			    while(tParentID!=0){
			    	catalog=new ZCCatalogSchema();
			    	catalog.setID(tParentID);
			    	catalog.fill();
			    	temp=temp+"-"+catalog.getName();
			    	tParentID=catalog.getParentID();
			    }
				String tMetaTitle=tSCQuestionSchema.getTitle()+"-"+temp+"-"+tSiteName;
				tZCArticleSchema.setMetaTitle(tMetaTitle);
				
				
				trans.add(tZCArticleSchema, Transaction.INSERT);
				tSCQuestionSchema.setCatalogId(catalogID);
				trans.add(tSCQuestionSchema, Transaction.UPDATE); 
				//跟新日志
				ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
				articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
				articleLog.setArticleID(articleId);
				articleLog.setAction("INSERT");
				articleLog.setActionDetail("新建文章");
				articleLog.setAddUser(User.getRealName());
				articleLog.setAddTime(new Date());
				trans.add(articleLog, Transaction.INSERT);

				saveCustomColumn(trans, tZCArticleSchema.getCatalogID(),articleId, true,tSCQuestionSchema.getAddDate(),tSCQuestionSchema.getContent());
			}
			//更新SCQuestionSchema-->articleId
			tSCQuestionSchema.setQuestionId(articleId+"");
			if (Type.equals("Pass")) {
				tSCQuestionSchema.setaState("01");
			} else if (Type.equals("NoPass")) {
				tSCQuestionSchema.setaState("02");
			}
			tSCQuestionSchema.setaDate(new Date());
			tSCQuestionSchema.setOperator(User.getUserName());
			trans.add(tSCQuestionSchema, Transaction.UPDATE);
			
			try {
				if (!trans.commit()) {
					Response.setError("操作失败!");
				} else {
					publish(articleId);
				}
			} catch (Exception e) {
				Response.setError("系统异常!");
			}
		}
//		map.put("CatalogName", CatalogUtil.getName(catalogID));

	}
	
	private void saveCustomColumn(Transaction trans, long catalogID, long articleID, boolean newFlag, Date addDate,String content) {
		if (!newFlag) {
			DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, articleID);
			if (columnDT.getRowCount() > 0) {
				trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
						ColumnUtil.RELA_TYPE_DOCID, articleID + ""));
			}
			trans.add(getValueFromRequest(catalogID, articleID,addDate,content), Transaction.INSERT);
		} else {
			trans.add(getValueFromRequest(catalogID, articleID,addDate,content), Transaction.INSERT);
		}
		
	}

	private static SchemaSet getValueFromRequest(long catalogID, long docID, Date addDate,String content) {
		DataTable dt = ColumnUtil.getColumn(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID);
		ZDColumnValueSet set = new ZDColumnValueSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDColumnValueSchema value = new ZDColumnValueSchema();
			value.setID(NoUtil.getMaxID("ColumnValueID"));
			value.setColumnID(dt.getString(i, "ID"));
			value.setColumnCode(dt.getString(i, "Code"));
			value.setRelaType(ColumnUtil.RELA_TYPE_DOCID);
			value.setRelaID(docID + "");
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");//定时使用
			String temp2 = formatter1.format(addDate);
			if("AskTime".equals(dt.getString(i, "Code"))){
				value.setTextValue(temp2);
			}else if("cainadaan".equals(dt.getString(i, "Code"))){
				value.setTextValue(content);
			}
			set.add(value);
		}
		return set;
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
				"update zcarticle set publishdate=?,FirstPublishDate=?,modifyuser=?,modifytime=?,status=? where id=?");
				qb.add(date);
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
	
	
	
	
	public void del() {
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		Transaction trans = new Transaction();
		SCQuestionSchema tSCQuestionSchema=new SCQuestionSchema();
		SCQuestionSet tSCQuestionSet = tSCQuestionSchema.query(new QueryBuilder("where ID in (" + ids + ")"));
		for(int i=0;i<tSCQuestionSet.size();i++){
			tSCQuestionSet.get(i).setaState("03");
		}
		trans.add(tSCQuestionSet, Transaction.UPDATE);
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
		String str="";
		if("".equals(VerifyStatus)||VerifyStatus==null){
	    	VerifyStatus="All";
	    }
		if(!"All".equals(VerifyStatus)&&!"".equals(VerifyStatus)){
			str=" and astate='"+VerifyStatus+"'";
		}else{
			str=" and astate in('00','01','02')";
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
		String strSQL = "select *,if (astate='00','未审核',if(astate='01','审核通过','审核未通过')) as sstate,(select email from member b where b.id=a.userid) as UserEmail ,(select name from zccatalog c where c.id=a.catalogid) as catalogname from SCQuestion a where 1=1 "+str+" order by adddate desc";
		
		dla.setTotal(new QueryBuilder(strSQL));
	
		DataTable dt = new QueryBuilder(strSQL).executePagedDataTable(
				dla.getPageSize(), dla.getPageIndex());
		dla.bindData(dt);
	}
	
}