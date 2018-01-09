package com.sinosoft.message;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDExpCalendarSchema;


public class MemberExperience extends Page {

	
/*
 * 查询
 */

	public static void dg1DataBind(DataGridAction dga) {
		
		String username = dga.getParams().getString("username");
		
		String Manner = dga.getParams().getString("Manner");
		String startDate = dga.getParams().getString("StartDate");
		String endDate = dga.getParams().getString("EndDate");

		QueryBuilder qb = new QueryBuilder("select a.id,a.MemberId,a.Experience,a.CreateDate,a.CreateTime," +
				"b.username,b.email,b.mobileNO,(select codename from zdcode z where a.Source=z.codevalue and z.codetype='Source1'  ) Source," +
				"c.codename from SDExpCalendar a ,member b ,zdcode c where a.MemberId = b.ID " +
				"and a.Manner=c.codevalue and c.codetype='Manner'");

		if (StringUtil.isNotEmpty(username)) {
			qb.append(" and b.username like ? ","%" + username + "%");
		}
		
		if (StringUtil.isNotEmpty(Manner)) {
			qb.append(" and  a.Manner =? ", Manner);
		}else{
			qb.append(" and ( a.Manner ='0' or a.Manner ='1' )");		
		}
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and a.CreateDate >=?", startDate);
		}
		if(StringUtil.isNotEmpty(endDate)){
			qb.append(" and a.CreateDate <=?", endDate);
		}
		qb.append(" order by a.ID desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());		
		dga.setTotal(qb);		
		dga.bindData(dt);		
	}	
		
	/*
	 * 修改
	 */

	public static Mapx initDialog(Mapx params) {
		SDExpCalendarSchema userLog = new SDExpCalendarSchema();
		userLog.setID(params.getString("ID"));
		
		userLog.setCreateDate(params.getString("Experience"));
		userLog.setCreateDate(params.getString("CreateDate"));
		
		userLog.fill();
		params = userLog.toMapx();
		DataTable dt = new QueryBuilder("select b.username from member b where b.id ='"+userLog.getMemberId()+"'").executeDataTable();
		params.put("username", dt.getString(0, "username"));		
		return params;
	}
	
	public void save() {
		String ID = $V("ID");
		String Experience = $V("Experience");
		String Manner = $V("Manner");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(Experience) || StringUtil.isEmpty(Manner)) {
			Response.setLogInfo(0, "传入数据错误！");
			return;
		}
		SDExpCalendarSchema branch = new SDExpCalendarSchema();
		branch.setID(ID);
		if (!branch.fill()) {
			Response.setLogInfo(0, ID + "会员不存在！");
			return;
		}
		branch.setValue(Request);
		branch.setOperator(User.getUserName());
		trans.add(branch, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

/*
 * 新建
 */

	public void checkName() {
		String UserName = Request.getString("UserName");
		DataTable dt = new QueryBuilder("select * from Member where UserName=?", UserName).executeDataTable();
		if (dt.getRowCount() > 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}
	public long createID(){
		return  new QueryBuilder("select max( CAST(ID as SIGNED)) from SDExpCalendar").executeLong()+1;
	}
	
	public void add() {

		SDExpCalendarSchema userLog = new SDExpCalendarSchema();
		Transaction trans = new Transaction();
		
//		String MemberId = $V("memberId");
//		String Experience = $V("Experience");
//		String Status = $V("Status");
//		String Manner = $V("Manner");
		String Source = $V("Source");
		
		
		userLog.setID(NoUtil.getMaxID("ExperienceID") + "");
		userLog.setMemberId($V("MemberId"));
		userLog.setCreateDate(PubFun.getCurrentDate());
		userLog.setCreateTime(PubFun.getCurrentTime());
		userLog.setSource(Source);
		userLog.setValue(Request);
		trans.add(userLog, Transaction.INSERT);
		if (trans.commit()) {
			Response.setLogInfo(1, "新增成功");
		} else {
			Response.setLogInfo(0, "新增失败!");
		}
	}
	
}	