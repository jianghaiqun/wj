package com.sinosoft.message;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDIntCalendarSchema;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sinosoft.framework.User.getUserName;

public class MemberIntegral extends Page {

	/*
	 * 查询
	 */

	public static void dg1DataBind(DataGridAction dga) {

		String username = dga.getParams().getString("username");
		String mobileno = dga.getParams().getString("mobileno");
		String email = dga.getParams().getString("email");
		String Status = dga.getParams().getString("Status");
		String Manner = dga.getParams().getString("Manner");
		String startDate = dga.getParams().getString("StartDate");
		String endDate = dga.getParams().getString("EndDate");
		
		QueryBuilder qb = new QueryBuilder("select a.id,a.Integral,a.CreateDate,a.CreateTime,a.Businessid,a.MemberId," +
				"(select codename from zdcode z where a.Manner=z.codevalue and z.codetype='Manner' ) Manner," +
				"(select codename from zdcode z where a.Status=z.codevalue and z.codetype='Status' ) Status," +
				"(select codename from zdcode z where a.Source=z.codevalue and z.codetype='Source' ) Source," +
				"b.username,b.email,b.mobileNO from SDIntCalendar a ,member b where a.MemberId=b.ID");

		if (StringUtil.isNotEmpty(username)) {
			qb.append(" and b.username like ? ", "%" + username + "%");
		}
		if (StringUtil.isNotEmpty(mobileno)) {
			qb.append(" and b.mobileNO =?",mobileno);
		}
		if (StringUtil.isNotEmpty(email)) {
			qb.append(" and b.email =?",email);
		}
		if (StringUtil.isNotEmpty(Manner)) {
			qb.append(" and  Manner =? ", Manner);
		} else {
			qb.append(" and ( Manner ='0' or Manner ='1' )");
		}
		if (StringUtil.isNotEmpty(Status)) {
			qb.append(" and  Status =? ", Status);
		} else {
			qb.append(" and ( Status ='0' or Status ='1' )");
		}
		if (StringUtil.isNotEmpty(startDate) ) {
			qb.append(" and a.CreateDate >=?", startDate);
		}
		if(StringUtil.isNotEmpty(endDate)){
			qb.append(" and a.CreateDate <=?", endDate);
		}
		qb.append(" order by a.ID desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);

	}

	public void dg2DataBind(DataGridAction dga) {
		String memberId = $V("memberId");
		String source = dga.getParams().getString("Source");
		String Status = dga.getParams().getString("Status");
		String Manner = dga.getParams().getString("Manner");
		String startDate = dga.getParams().getString("StartDate");
		String endDate = dga.getParams().getString("EndDate");

		QueryBuilder qb = new QueryBuilder("select a.id,a.Integral,a.CreateDate,a.CreateTime,a.Businessid,a.MemberId," +
				"(select codename from zdcode z where a.Manner=z.codevalue and z.codetype='Manner' ) Manner," +
				"(select codename from zdcode z where a.Status=z.codevalue and z.codetype='Status' ) Status," +
				"zd.codename Source,a.description from SDIntCalendar a, zdcode zd where a.MemberId=? and a.Source=zd.codevalue and zd.codetype='Source'", memberId);

		if (StringUtil.isNotEmpty(source)) {
			qb.append(" and zd.codename like ? ", "%" + source + "%");
		} 
		if (StringUtil.isNotEmpty(Manner)) {
			qb.append(" and  Manner =? ", Manner);
		} else {
			qb.append(" and ( Manner ='0' or Manner ='1' )");
		}
		if (StringUtil.isNotEmpty(Status)) {
			qb.append(" and  Status =? ", Status);
		} else {
			qb.append(" and ( Status ='0' or Status ='1' )");
		}
		if (StringUtil.isNotEmpty(startDate) ) {
			qb.append(" and a.CreateDate >=?", startDate);
		}
		if(StringUtil.isNotEmpty(endDate)){
			qb.append(" and a.CreateDate <=?", endDate);
		}
		qb.append(" order by a.ID desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);

	}
	
	/*
	 * 修改
	 */

	public static Mapx initDialog(Mapx params) {
		SDIntCalendarSchema userLog = new SDIntCalendarSchema();
		
		userLog.setID(params.getString("ID"));
		userLog.setCreateDate(params.getString("CreateDate"));
		userLog.fill();
		String businessid = userLog.getBusinessid();
		params = userLog.toMapx();
		DataTable dt = new QueryBuilder("select b.username from member b where b.id ='"
						+ userLog.getMemberId() + "'").executeDataTable();
		params.put("username", dt.getString(0, "username"));

		String integralSource = params.getString("Source");

		String manner = params.getString("Manner");
		String status = params.getString("Status");

		QueryBuilder qb = new QueryBuilder("SELECT codename,codevalue FROM zdcode WHERE codetype='source'");
		dt = qb.executeDataTable();
		params.put("Source", HtmlUtil.dataTableToOptions(dt, integralSource));


		Map<String, String> map = new HashMap<String, String>();

		map.put("0", "收入");
		map.put("1", "支出");
		params.put("mannerSelectTag", HtmlUtil.mapxToOptions(map, manner));

		map = new HashMap<String, String>();

		map.put("0", "正常");
		if (StringUtil.isNotEmpty(businessid)) {
			map.put("1", "冻结");
		}
		params.put("statusSelectTag", HtmlUtil.mapxToOptions(map, status));
		return params;
	}

	public void save() {
		String id = $V("ID");
		String integral = $V("Integral");
		String status = $V("Status");
		String manner = $V("Manner");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(integral) || StringUtil.isEmpty(status)
				|| StringUtil.isEmpty(manner)) {
			Response.setLogInfo(0, "传入数据错误！");
			return;
		}
		SDIntCalendarSchema branch = new SDIntCalendarSchema();
		branch.setID(id);
		if (!branch.fill()) {
			Response.setLogInfo(0, id + "会员不存在！");
			return;
		}
		String businessId = branch.getBusinessid();
		String oldIntegral = branch.getIntegral();
		String oldManner = branch.getManner();
		String oldStatus = branch.getStatus();
		String memberId = branch.getMemberId();

		branch.setValue(Request);
		branch.setOperator(getUserName());

		QueryBuilder qb = new QueryBuilder("select currentValidatePoint,point,version from member where id = ?");
		qb.add(memberId);
		DataTable dt = qb.executeDataTable();
		// 获取当前有效积分
		int currentValidatePoint = dt.getInt(0, "currentValidatePoint");
		// version
		String version = dt.getString(0, "version");
		// 冻结积分
		int point = dt.getInt(0, "point");

		// 如果积分不够支出，返回
		if ("1".equals(manner) && currentValidatePoint < Integer.valueOf(integral)) {
			Response.setLogInfo(0, "当前有效积分不够支出，新增失败");
			return;
		}

		// 如果是状态是正常
		if ("0".equals(status)) {
			// 如果之前的状态是冻结,恢复冻结积分
			if ("1".equals(oldStatus)) {
				point = point - Integer.valueOf(oldIntegral);
			}
			// 如果之前是收入
			if ("0".equals(oldManner)) {
				currentValidatePoint = currentValidatePoint - Integer.valueOf(oldIntegral) + Integer.valueOf(integral);
			}
			// 如果之前是支出
			else if ("1".equals(oldManner)) {
				currentValidatePoint = currentValidatePoint + Integer.valueOf(oldIntegral) - Integer.valueOf(integral);
			}
		}
		// 如果状态是冻结
		else if ("1".equals(status) && StringUtil.isNotEmpty(businessId)) {
			// 如果之前的状态是正常
			if ("0".equals(oldStatus)) {
				// 如果之前是收入
				if ("0".equals(oldManner)) {
					currentValidatePoint = currentValidatePoint - Integer.valueOf(oldIntegral);
				}
				// 如果之前是支出
				else if ("1".equals(oldManner)) {
					currentValidatePoint = currentValidatePoint + Integer.valueOf(oldIntegral);
				}
				point = point + Integer.valueOf(integral);
			}
			// 如果之前的状态是冻结
			else if ("1".equals(oldStatus)) {
				point = point - Integer.valueOf(oldIntegral) + Integer.valueOf(integral);
			}
		}
		// 更新当前有效积分
		if (StringUtil.isEmpty(version)) {
			qb = new QueryBuilder("update member set currentValidatePoint=?,point=?, version='1', modifyDate=? where id=? and (version is null or version='')");
			qb.add(currentValidatePoint);
			qb.add(point);
			qb.add(DateUtil.getCurrentDateTime());
			qb.add(memberId);
		} else {
			qb = new QueryBuilder("update member set currentValidatePoint=?,point=?, version=version+1, modifyDate=? where id=? and version=?");
			qb.add(currentValidatePoint);
			qb.add(point);
			qb.add(DateUtil.getCurrentDateTime());
			qb.add(memberId);
			qb.add(version);
		}
		trans.add(branch, Transaction.UPDATE);
		trans.add(qb);
		if (trans.commit()) {
			String userName = User.getUserName();
			String ip = Request.getClientIP();

			StringBuilder sb = new StringBuilder();
			sb.append("更新积分：");
			sb.append(integral);
			sb.append(",状态：");
			sb.append(status);
			sb.append(",收入/支出：");
			sb.append(manner);

			UserLog.log("intCalendar", "add", sb.toString(), ip, userName);
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
		DataTable dt = new QueryBuilder(
				"select * from Member where UserName=?", UserName)
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}


	public void add(){
		SDIntCalendarSchema sdintCalendar = new SDIntCalendarSchema();
		Transaction trans = new Transaction();

		// 支出/收入
		String manner = $V("Manner");
		// 状态
		String status = $V("Status");
		// 积分
		String integral = $V("Integral");
		// 来源
		String source = $V("Source");
		sdintCalendar.setID(NoUtil.getMaxID("IntegralID") + "");
		sdintCalendar.setMemberId($V("memberId"));
		sdintCalendar.setIntegral(integral);
		sdintCalendar.setSource(source);
		sdintCalendar.setManner(manner);
		sdintCalendar.setCreateDate(PubFun.getCurrentDate());
		sdintCalendar.setCreateTime(PubFun.getCurrentTime());
		sdintCalendar.setStatus(status);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdintCalendar.setsvaliDate(sdFormat.format(new Date()));
		sdintCalendar.setDescription($V("description"));

		QueryBuilder qb = new QueryBuilder("select currentValidatePoint,version from member where id = ?");
		qb.add($V("memberId"));
		DataTable dt = qb.executeDataTable();
		// 获取当前有效积分
		int currentValidatePoint = dt.getInt(0, "currentValidatePoint");
		// version
		String version = dt.getString(0, "version");

		// 如果积分不够支出，返回
		if ("1".equals(manner) && currentValidatePoint < Integer.valueOf(integral)) {
			Response.setLogInfo(0, "当前有效积分不够支出，新增失败");
			return;
		}
		trans.add(sdintCalendar, Transaction.INSERT);

		// 判断收入/支出
		if ("0".equals(manner)) {
			// 收入
			currentValidatePoint = currentValidatePoint + Integer.valueOf(integral);
		} else {
			// 支出
			currentValidatePoint = currentValidatePoint - Integer.valueOf(integral);
		}

		// 更新当前有效积分
		if (StringUtil.isEmpty(version)) {
			qb = new QueryBuilder("update member set currentValidatePoint=?, version='1', modifyDate=? where id=? and (version is null or version='')");
			qb.add(currentValidatePoint);
			qb.add(DateUtil.getCurrentDateTime());
			qb.add($V("memberId"));
		} else {
			qb = new QueryBuilder("update member set currentValidatePoint=?, version=version+1, modifyDate=? where id=? and version=?");
			qb.add(currentValidatePoint);
			qb.add(DateUtil.getCurrentDateTime());
			qb.add($V("memberId"));
			qb.add(version);
		}
		trans.add(qb);
		if (trans.commit()) {
			String userName = User.getUserName();
			String ip = Request.getClientIP();

			StringBuilder sb = new StringBuilder();
			sb.append("新增积分：");
			sb.append(integral);
			sb.append(",状态：");
			sb.append(status);
			sb.append(",收入/支出：");
			sb.append(manner);

			UserLog.log("intCalendar", "add", sb.toString(), ip, userName);
			Response.setLogInfo(1, "新增成功");
		} else {
			Response.setLogInfo(0, "新增失败!");
		}
	}

	/**
	 * 获取积分来源
	 *
	 * @param params
	 */
	public static Mapx initIntegralSource(Mapx params){
		String integralSource = params.getString("integralSource");

		QueryBuilder qb = new QueryBuilder("SELECT codename,codevalue FROM zdcode WHERE codetype='source'");
		DataTable dt = qb.executeDataTable();
		params.put("integralSource", HtmlUtil.dataTableToOptions(dt, integralSource));
		return params;
	}
}