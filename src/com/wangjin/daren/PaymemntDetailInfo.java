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
import com.sinosoft.schema.AuthorDetailInfoSchema;
import com.sinosoft.schema.PaymemntDetailInfoSchema;
import com.sinosoft.schema.PaymemntDetailInfoSet;

/**
 * @author wangcaiyun
 *
 */
public class PaymemntDetailInfo extends Page {

	public static Mapx<String, String> init(Mapx<String, String> params) {
		String id = params.getString("authorDetailInfo_id");
		AuthorDetailInfoSchema schema = new AuthorDetailInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			params.put("authorName", schema.getauthorName());
			params.put("articleLink", schema.getarticleLink());
		}
		return params;
	}
	
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		params.put("YesOrNo", HtmlUtil.codeToOptions("YesOrNo", false));
		params.put("isPay", "N");
		String type = params.getString("type");
		String id = params.getString("ID");
		if ("Modify".equals(type) && StringUtil.isNotEmpty(id)) {
			PaymemntDetailInfoSchema schema = new PaymemntDetailInfoSchema();
			schema.setid(id);
			if (schema.fill()) {
				params.put("payTime", schema.getpayTime());
				params.put("payPrice", schema.getpayPrice().toString());
				params.put("isPay", schema.getisPay());
			}
		}
		return params;
	}
	
	public void dg1DataBind(DataGridAction dga) {
		String authorDetailInfo_id = (String) dga.getParams().get("authorDetailInfo_id");
		StringBuilder sb = new StringBuilder();
		sb.append("select id,payTime,payPrice,if(isPay='Y','是','否') as isPay,");
		sb.append(" DATE_FORMAT(createDate,'%Y-%m-%d') as createDate,DATE_FORMAT(modifyDate,'%Y-%m-%d') as modifyDate,");
		sb.append(" (select IFNULL(realName, userName) from zduser where userName = createUser) as createUser,");
		sb.append(" if(modifyUser is null, '', (select IFNULL(realName, userName) from zduser where userName = PaymemntDetailInfo.modifyUser)) as modifyUser");
		sb.append(" from PaymemntDetailInfo where authorDetailInfo_id = ? order by payTime asc");
		QueryBuilder qb = new QueryBuilder(sb.toString(), authorDetailInfo_id);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public void add() {
		// 校验支付时间是否重复
		String payTime = Request.getString("payTime");
		int count = new QueryBuilder("select count(1) from PaymemntDetailInfo where payTime = ? and authorDetailInfo_id=? ", payTime, Request.getString("authorDetailInfo_id")).executeInt();
		if (count > 0) {
			Response.setStatus(0);
			Response.setMessage("该支付时间数据已录入，不能重复录入！");
			return;
		}
		PaymemntDetailInfoSchema schema = new PaymemntDetailInfoSchema();
		schema.setValue(Request);
		schema.setid(NoUtil.getMaxID("PaymemntDetailInfoID")+"");
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
		PaymemntDetailInfoSchema schema = new PaymemntDetailInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			// 校验支付时间是否重复
			String payTime = Request.getString("payTime");
			int count = new QueryBuilder("select count(1) from PaymemntDetailInfo where payTime = ? and authorDetailInfo_id=? ", payTime, Request.getString("authorDetailInfo_id")).executeInt();
			if (count > 1) {
				Response.setStatus(0);
				Response.setMessage("该支付时间数据已存在，不能重复！");
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
			Response.setMessage("未找到该支付数据！");
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
		trans.add(new QueryBuilder("DELETE FROM PaymemntDetailInfo WHERE id IN ('"+ids.replace(",", "','")+"')"));
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
	}
	
}
