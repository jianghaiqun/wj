/**
 * 
 */
package com.wangjin.daren;

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
import com.sinosoft.schema.TravelNotesStatisticsSchema;

/**
 * @author wangcaiyun
 *
 */
public class TravelNotesStatistics extends Page {

	public static Mapx<String, String> init(Mapx<String, String> params) {
		DataTable dt = new QueryBuilder("select userName, IFNULL(realName, userName) from zduser where ((branchInnerCode like '"+User.getBranchInnerCode()+"%' and branchInnerCode != ?) or userName = ? ) ", User.getBranchInnerCode(), User.getUserName()).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			Mapx<String, String> mapx = new Mapx<String, String>();
			String[] checkarray = null;
			int i = 0;
			int rowCount = dt.getRowCount();
			for (; i < rowCount; i++) {
				mapx.put(dt.getString(i, 0), dt.getString(i, 1));
			}
			params.put("contactPeople",HtmlUtil.mapxToCheckboxes("contactPeople",mapx,checkarray,checkarray));
		}
		
		return params;
	}
	
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		String type = params.getString("type");
		String id = params.getString("ID");
		if ("Modify".equals(type) && StringUtil.isNotEmpty(id)) {
			TravelNotesStatisticsSchema schema = new TravelNotesStatisticsSchema();
			schema.setid(id);
			if (schema.fill()) {
				params.put("statisticalTime", schema.getstatisticalTime());
				params.put("flow", schema.getflow());
				params.put("orderNum", schema.getorderNum()+"");
				params.put("sumPrem", schema.getsumPrem().toString());
				params.put("buyAuthor", schema.getbuyAuthor());
				params.put("authorOrderNum", schema.getauthorOrderNum()+"");
				if (schema.getauthorSumPrem() != null ) {
					params.put("authorSumPrem", schema.getauthorSumPrem().toString());
				}
				
				params.put("convertNum", schema.getconvertNum()+"");
			}
		}
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		// 统计时间
		String statisticalTime = dga.getParams().getString("statisticalTime");
		String endStatisticalTime = dga.getParams().getString("endStatisticalTime");
		// 购买作者名
		String buyAuthor = dga.getParams().getString("buyAuthor");
		// 联系人
		String contactPeople = dga.getParams().getString("contactPeople");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT t.id,t.statisticalTime,t.flow,t.orderNum,t.sumPrem,");
		sb.append(" t.buyAuthor,t.authorOrderNum,t.authorSumPrem,t.convertNum,");
		sb.append(" DATE_FORMAT(t.createDate,'%Y-%m-%d') as createDate,DATE_FORMAT(t.modifyDate,'%Y-%m-%d') as modifyDate,");
		sb.append(" (select IFNULL(realName, userName) from zduser where userName = t.createUser) as createUser,");
		sb.append(" (select IFNULL(realName, userName) from zduser where userName = t.modifyUser) as modifyUser,");
		sb.append(" count(DISTINCT a.articleLink) as articleNum,count(DISTINCT a.contactType) as authorNum,");
		sb.append(" (SELECT SUM(payPrice) FROM PaymemntDetailInfo where payTime=t.statisticalTime and createUser=t.createUser and isPay='Y') as cost");
		sb.append(" FROM TravelNotesStatistics t left join AuthorDetailInfo a ");
		sb.append(" on a.createUser=t.createUser and a.cooperationTime=t.statisticalTime");
		sb.append(" where ((t.branchInnerCode like '"+User.getBranchInnerCode()+"%' and t.branchInnerCode != ?) or t.createUser = ? ) ");
		QueryBuilder qb = new QueryBuilder(sb.toString(), User.getBranchInnerCode(),User.getUserName());
		
		if (StringUtil.isNotEmpty(statisticalTime)) {
			qb.append(" and t.statisticalTime >= ?", statisticalTime);
		}
		if (StringUtil.isNotEmpty(endStatisticalTime)) {
			qb.append(" and t.statisticalTime <= ?", endStatisticalTime);
		}
		
		if (StringUtil.isNotEmpty(buyAuthor)) {
			qb.append(" and t.buyAuthor like '%"+buyAuthor+"%' ");
		}
		if (StringUtil.isNotEmpty(contactPeople)) {
			qb.append(" and t.createUser in ('"+contactPeople.replace(",", "','")+"') ");
		}
		qb.append(" group by t.id order by t.statisticalTime desc");
		
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public void add() {
		// 校验统计时间是否重复
		String statisticalTime = Request.getString("statisticalTime");
		int count = new QueryBuilder("select count(1) from TravelNotesStatistics where statisticalTime = ? and createUser=? ", statisticalTime, User.getUserName()).executeInt();
		if (count > 0) {
			Response.setStatus(0);
			Response.setMessage("该统计时间数据已录入，不能重复录入！");
			return;
		}
		TravelNotesStatisticsSchema schema = new TravelNotesStatisticsSchema();
		schema.setValue(Request);
		schema.setid(NoUtil.getMaxID("TravelNotesStatisID")+"");
		schema.setbranchInnerCode(User.getBranchInnerCode());
		schema.setcreateDate(new Date());
		schema.setcreateUser(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(schema, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败！");
		}
	}
	
	public void edit() {
		String id = Request.getString("ID");
		if (StringUtil.isEmpty(id)) {
			Response.setStatus(0);
			Response.setMessage("数据ID不能为空！");
			return;
		}
		TravelNotesStatisticsSchema schema = new TravelNotesStatisticsSchema();
		schema.setid(id);
		if (schema.fill()) {
			// 校验统计时间是否重复
			String statisticalTime = Request.getString("statisticalTime");
			int count = new QueryBuilder("select count(1) from TravelNotesStatistics where statisticalTime = ? and createUser=? ", statisticalTime, schema.getcreateUser()).executeInt();
			if (count > 1) {
				Response.setStatus(0);
				Response.setMessage("该统计时间数据已存在，统计时间不能重复！");
				return;
			}
			schema.setValue(Request);
			schema.setmodifyDate(new Date());
			schema.setmodifyUser(User.getUserName());
			Transaction trans = new Transaction();
			trans.add(schema, Transaction.UPDATE);
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败！");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("未找到该游记统计数据！");
		}
	}
	
	public void delete() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("DELETE FROM TravelNotesStatistics WHERE id IN ('"+ids.replace(",", "','")+"')"));
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
	}
	
}
