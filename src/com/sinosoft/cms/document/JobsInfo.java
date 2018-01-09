/**
 * 
 */
package com.sinosoft.cms.document;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.JobsInfoSchema;
import com.sinosoft.schema.JobsInfoSet;

/**
 * @author wangcaiyun
 *
 */
public class JobsInfo extends Page {

	public static Mapx<String, String> init(Mapx<String, String> params) {
		// 是否发布
		params.put("YesOrNo",HtmlUtil.codeToOptions("YesOrNo", true));
		return params;
	}
	
	@SuppressWarnings("unchecked")
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		params.put("YesOrNo",HtmlUtil.codeToOptions("YesOrNo", false));
		String id = params.getString("id");
		if(StringUtil.isNotEmpty(id)){
			JobsInfoSchema jobsInfo=new JobsInfoSchema();
			jobsInfo.setid(id);
			if(jobsInfo.fill()){
				params.putAll(jobsInfo.toMapx());
			}
		} else {
			// 默认
			params.put("hrEmail", "hr@kaixinbao.com");
		}
		
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		// 岗位名称
		String jobsName = dga.getParam("jobsName");
		// 类别
		String jobsType= dga.getParam("jobsType");
		// 工作性质
		String jobsProperty = dga.getParam("jobsProperty");
		// 工作地点
		String jobsAddress= dga.getParam("jobsAddress");
		// 是否发布
		String isPublish= dga.getParam("isPublish");
		//查询招聘岗位
		QueryBuilder qb = new QueryBuilder("select id,jobsName,jobsType,jobsProperty,jobsAddress,isPublish,publishDate,jobsDuty,jobsRequirement,hrEmail,createUser,createDate,modifyUser,modifyDate,orderFlag,(select CodeName from zdcode where CodeType='YesOrNo' and CodeValue=isPublish) as isPublishName  from JobsInfo where 1=1 ");
		
		if (StringUtil.isNotEmpty(jobsName)) {
			qb.append(" and jobsName like " + "'%" + jobsName + "%'");
		}
		if (StringUtil.isNotEmpty(jobsType)) {
			qb.append(" and jobsType like " + "'%" + jobsType + "%'");
		}
		if (StringUtil.isNotEmpty(jobsProperty)) {
			qb.append(" and jobsProperty like " + "'%" + jobsProperty + "%'");
		}
		if (StringUtil.isNotEmpty(jobsAddress)) {
			qb.append(" and jobsAddress like " + "'%" + jobsAddress + "%'");
		}
		if (StringUtil.isNotEmpty(isPublish)) {
			qb.append(" and isPublish = '" + isPublish + "'");
		}
		qb.append(" order by OrderFlag desc, CreateDate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public void saveJobs() {
		//事务创建
		Transaction trans = new Transaction();
		JobsInfoSchema jobsInfo=new JobsInfoSchema();
		String flag = Request.getString("flag");
		String oldIsPublish = "";
		Date date = new Date();
		String user = User.getUserName();
		int operation = Transaction.INSERT;
		if ("edit".equals(flag)) {
			jobsInfo.setid(Request.getString("id"));
			if (!jobsInfo.fill()) {
				Response.setStatus(0);
				Response.setMessage("没有查询到岗位信息！");
				return;
			}
			oldIsPublish = jobsInfo.getisPublish();
			operation = Transaction.UPDATE;
		} 
		
		jobsInfo.setValue(Request);
		jobsInfo.setmodifyDate(date);
		jobsInfo.setmodifyUser(user);
		if ("add".equals(flag)){
			jobsInfo.setid(String.valueOf(NoUtil.getMaxID("JobsInfoID", "SN")));
			jobsInfo.setorderFlag(System.currentTimeMillis());
			jobsInfo.setcreateDate(date);
			jobsInfo.setcreateUser(user);
		}
		
		if (!"Y".equalsIgnoreCase(oldIsPublish) && "Y".equalsIgnoreCase(jobsInfo.getisPublish())) {
			jobsInfo.setpublishDate(date);
		}
		
		trans.add(jobsInfo, operation);
		
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("岗位信息保存成功！");
			return;
		}else{
			Response.setStatus(0);
			Response.setMessage("岗位信息保存失败！");
			return;
		}
	}
	
	/**
	 * 撤销发布
	 */
	public void pauseJobs() {
		//事务创建
		Transaction trans = new Transaction();
		JobsInfoSchema jobsInfo=new JobsInfoSchema();
		jobsInfo.setid(Request.getString("id"));
		if(!jobsInfo.fill()){
			Response.setStatus(0);
			Response.setMessage("没有查询到岗位信息！");
			return;
		}
		Date date = new Date();
		jobsInfo.setisPublish("N");
		jobsInfo.setpublishDate(date);
		jobsInfo.setmodifyUser(User.getUserName());
		jobsInfo.setmodifyDate(date);
		trans.add(jobsInfo, Transaction.UPDATE);
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("岗位撤销发布成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("岗位撤销发布失败！");
		}
	}
	
	public void publishJobs() {
		//事务创建
		Transaction trans = new Transaction();
		JobsInfoSchema jobsInfo=new JobsInfoSchema();
		jobsInfo.setid(Request.getString("id"));
		if(!jobsInfo.fill()){
			Response.setStatus(0);
			Response.setMessage("没有查询到岗位信息！");
			return;
		}
		Date date = new Date();
		jobsInfo.setisPublish("Y");
		jobsInfo.setpublishDate(date);
		jobsInfo.setmodifyUser(User.getUserName());
		jobsInfo.setmodifyDate(date);
		trans.add(jobsInfo, Transaction.UPDATE);
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("岗位发布成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("岗位发布失败！");
		}
	
	}
	
	public void delJobs() {
		//事务创建
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from JobsInfo where id = ?", Request.getString("id")));
		
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("删除成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("删除失败！");
		}
	}
	public void sortJobs() {
		DataTable dt = (DataTable) Request.get("DT");
		JobsInfoSet set = new JobsInfoSet();
		JobsInfoSchema schema;
		for (int i = 0; i < dt.getRowCount(); i++) {
			schema = new JobsInfoSchema();
			schema.setid(dt.getString(i, "id"));
			schema.fill();
			schema.setorderFlag(dt.getString(i, "OrderFlag"));
			schema.setmodifyDate(new Date());
			schema.setmodifyUser(User.getUserName());

			set.add(schema);
		}
		if (set.update()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}
}
