/**
 * 
 */
package com.wangjin.daren;

import java.math.BigDecimal;
import java.util.Date;

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
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.AuthorDetailInfoSchema;
import com.sinosoft.schema.PaymemntDetailInfoSchema;
import com.sinosoft.schema.PaymemntDetailInfoSet;

/**
 * @author wangcaiyun
 *
 */
public class AuthorDetailInfo extends Page {

	public void getContactedPeople() {
		String authorName = Request.getString("authorName");
		String articleLink = Request.getString("articleLink");
		String contactType = Request.getString("contactType");
		
		if (StringUtil.isEmpty(authorName) && StringUtil.isEmpty(articleLink) && StringUtil.isEmpty(contactType)) {
			Response.setStatus(0);
			Response.setMessage("查询条件必须输入一个！");
			return;
		}
		QueryBuilder qb = new QueryBuilder("select IFNULL(d.RealName, d.UserName), group_concat(DISTINCT a.authorName) from AuthorDetailInfo a, zduser d where a.createUser=d.UserName ");
		if (StringUtil.isNotEmpty(authorName)) {
			qb.append(" and a.authorName like '%"+authorName+"%' ");
		}
		if (StringUtil.isNotEmpty(articleLink)) {
			qb.append(" and a.articleLink = ? ", articleLink);
		}
		if (StringUtil.isNotEmpty(contactType)) {
			qb.append(" and a.contactType like '%"+contactType+"%' ");
		}
		qb.append(" group by a.createUser");
		DataTable dt = qb.executeDataTable();
		Response.setStatus(1);
		if (dt == null || dt.getRowCount() == 0) {
			Response.setMessage("无");
		} else {
			String contactPeo = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				contactPeo += ("联系人："+dt.getString(i, 0)+" 作者："+dt.getString(i, 1)+"<br>");
			}
			Response.setMessage(contactPeo);
		}
	}
	
	public static Mapx<String, String> init(Mapx<String, String> params) {
		// 来源
		params.put("source", HtmlUtil.codeToOptions("Author.source", true));
		// 推荐险种
		params.put("productName", HtmlUtil.codeToOptions("Author.productName", true));
		params.put("YesOrNo", HtmlUtil.codeToOptions("YesOrNo", true));
		
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
		// 来源
		params.put("source", HtmlUtil.codeToOptions("Author.source", false));
		// 推荐险种
		params.put("productName", HtmlUtil.codeToOptions("Author.productName", false));
		// 联系工具
		params.put("contactTool", HtmlUtil.codeToOptions("Author.contactTool", false));
		// 支付规则
		params.put("payRules", HtmlUtil.codeToOptions("Author.payRules", false));
		// 性别
		params.put("sex", HtmlUtil.codeToOptions("Gender", false));
		
		String type = params.getString("type");
		String id = params.getString("ID");
		if ("Modify".equals(type) && StringUtil.isNotEmpty(id)) {
			AuthorDetailInfoSchema schema = new AuthorDetailInfoSchema();
			schema.setid(id);
			if (schema.fill()) {
				params.put("destination", schema.getdestination());
				params.put("authorName", schema.getauthorName());
				params.put("authorSex", schema.getauthorSex());
				params.put("city", schema.getcity());
				params.put("code", schema.getcode());
				params.put("articleLink", schema.getarticleLink());
				params.put("sourceValue", schema.getsource());
				params.put("contactToolValue", schema.getcontactTool());
				params.put("contactType", schema.getcontactType());
				params.put("cooperationTime", schema.getcooperationTime());
				params.put("payType", schema.getpayType());
				params.put("payRulesValue", schema.getpayRules());
				params.put("productNameValue", schema.getproductName());
				params.put("remark1", schema.getremark1());
			}
		}
		
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		// 目的地
		String destination = dga.getParams().getString("destination");
		// 作者名
		String authorName = dga.getParams().getString("authorName");
		// 来源
		String source = dga.getParams().getString("source");
		// 推荐险种
		String productName = dga.getParams().getString("productName");
		// 合作时间
		String cooperationTime = dga.getParams().getString("cooperationTime");
		String endCooperationTime = dga.getParams().getString("endCooperationTime");
		// 支付时间
		String payTime = dga.getParams().getString("payTime");
		String endPayTime = dga.getParams().getString("endPayTime");
		// 是否已支付
		String isPay = dga.getParams().getString("isPay");
		// 联系人
		String contactPeople = dga.getParams().getString("contactPeople");
		// 备注
		String remark1 = dga.getParams().getString("remark1");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id,destination,authorName,if (authorSex='M', '男', '女') as authorSex,");
		sb.append("city,code,articleLink,source,contactTool,contactType,payType,payRules,remark1,");
		sb.append("productName,cooperationTime,DATE_FORMAT(createDate,'%Y-%m-%d') as createDate,");
		sb.append("(select IFNULL(realName, userName) from zduser where userName = createUser) as createUser,");
		sb.append("DATE_FORMAT(modifyDate,'%Y-%m-%d') as modifyDate,IF(modifyUser is null, '',(select IFNULL(realName, userName) from zduser where userName = AuthorDetailInfo.modifyUser)) as modifyUser,");
		sb.append("(SELECT sum(payPrice) FROM PaymemntDetailInfo WHERE authorDetailInfo_id = AuthorDetailInfo.id and isPay = 'Y' ");
		if (StringUtil.isNotEmpty(payTime)) {
			sb.append(" and payTime >= '"+payTime+"' ");
		}
		if (StringUtil.isNotEmpty(endPayTime)) {
			sb.append(" and payTime <= '"+endPayTime+"' ");
		}
		sb.append(") as payPrice ");
		sb.append("FROM AuthorDetailInfo where ((branchInnerCode like '"+User.getBranchInnerCode()+"%' and branchInnerCode != ?) or createUser = ? ) ");
		QueryBuilder qb = new QueryBuilder(sb.toString(), User.getBranchInnerCode(),User.getUserName());
		if (StringUtil.isNotEmpty(destination)) {
			qb.append(" and destination like '%"+destination+"%' ");
		}
		if (StringUtil.isNotEmpty(authorName)) {
			qb.append(" and authorName like '%"+authorName+"%' ");
		}
		if (StringUtil.isNotEmpty(source)) {
			qb.append(" and source = ? ", source);
		}
		if (StringUtil.isNotEmpty(productName)) {
			qb.append(" and productName = ? ", productName);
		}
		if (StringUtil.isNotEmpty(cooperationTime)) {
			qb.append(" and cooperationTime >= ? ", cooperationTime);
		}
		if (StringUtil.isNotEmpty(endCooperationTime)) {
			qb.append(" and cooperationTime <= ? ", endCooperationTime);
		}
		if (StringUtil.isNotEmpty(contactPeople)) {
			qb.append(" and createUser in ('"+contactPeople.replace(",", "','")+"') ");
		}
		if (StringUtil.isNotEmpty(remark1)) {
			qb.append(" and remark1 like '%"+remark1+"%' ");
		}
		
		if (StringUtil.isNotEmpty(payTime) || StringUtil.isNotEmpty(endPayTime) || StringUtil.isNotEmpty(isPay)) {
			qb.append(" and EXISTS (SELECT id FROM PaymemntDetailInfo WHERE authorDetailInfo_id = AuthorDetailInfo.id ");
			if (StringUtil.isNotEmpty(payTime)) {
				qb.append(" and payTime >= ? ", payTime);
			}
			if (StringUtil.isNotEmpty(endPayTime)) {
				qb.append(" and payTime <= ? ", endPayTime);
			}
			if (StringUtil.isNotEmpty(isPay)) {
				qb.append(" and isPay = ? ", isPay);
			}
			qb.append(") ");
		}
		qb.append(" order by cooperationTime desc, id desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public void add() {
		// 校验该帖子是否重复
		String articleLink = Request.getString("articleLink");
		int count = new QueryBuilder("select count(1) from AuthorDetailInfo where articleLink = ? ", articleLink).executeInt();
		if (count > 0) {
			Response.setStatus(0);
			Response.setMessage("该帖子已存在，不能重复录入！");
			return;
		}
		AuthorDetailInfoSchema schema = new AuthorDetailInfoSchema();
		schema.setValue(Request);
		schema.setid(NoUtil.getMaxID("AuthorDetailInfoID")+"");
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
			return;
		}
		/* 生成稿费支付明细 */
		// 合作时间
		String cooperationTime = schema.getcooperationTime();
		// 支付规则
		String payRules = new QueryBuilder("select Memo from zdcode where CodeType='Author.payRules' and ParentCode='Author.payRules' and CodeValue=?", schema.getpayRules()).executeString();
		if (StringUtil.isEmpty(payRules)) {
			Response.setMessage(Response.getMessage()+"未取到支付规则，生成稿费明细失败！");
			return;
		}
		String[] rules = payRules.split(",");
		String rule;
		String payTime;
		PaymemntDetailInfoSchema paySchema;
		PaymemntDetailInfoSet set = new PaymemntDetailInfoSet();
		trans = new Transaction();
		for (int i = 0; i < rules.length; i++) {
			rule = rules[i];
			payTime = calPayTime(cooperationTime,rule.split("-")[0]);
			if (StringUtil.isEmpty(payTime)) {
				Response.setMessage(Response.getMessage()+"支付规则（"+rule+"）设置错误，生成稿费明细失败！");
				return;
			}
			paySchema = new PaymemntDetailInfoSchema();
			paySchema.setid(NoUtil.getMaxID("PaymemntDetailInfoID")+"");
			paySchema.setpayTime(payTime);
			paySchema.setpayPrice(new BigDecimal(rule.split("-")[1]));
			paySchema.setisPay("N");
			paySchema.setauthorDetailInfo_id(schema.getid());
			paySchema.setcreateDate(new Date());
			paySchema.setcreateUser(User.getUserName());
			set.add(paySchema);
		}
		trans.add(set, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage(Response.getMessage()+" 生成稿费明细成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage(Response.getMessage()+" 生成稿费明细失败！");
		}
		
	}
	
	/**
	 * 
	 * @param cooperationTime 合作时间
	 * @param time 支付时间间隔
	 * @return
	 */
	private String calPayTime(String cooperationTime, String time) {
		String result = "";
		Date date = DateUtil.parse(cooperationTime);
		if (date == null) {
			return result;
		}
		int count = Integer.valueOf(time.substring(0, time.length() - 1));
		if (time.endsWith("D")) {
			result = DateUtil.toString(DateUtil.addDay(date, count));
		} else if (time.endsWith("M")) {
			result = DateUtil.toString(DateUtil.addMonth(date, count));
		} else if (time.endsWith("Y")) {
			result = DateUtil.toString(DateUtil.addYear(date, count));
		}
		
		return result;
	}
	
	public void edit() {
		String id = Request.getString("ID");
		if (StringUtil.isEmpty(id)) {
			Response.setStatus(0);
			Response.setMessage("数据ID不能为空！");
			return;
		}
		AuthorDetailInfoSchema schema = new AuthorDetailInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			// 校验该帖子是否重复
			String articleLink = Request.getString("articleLink");
			int count = new QueryBuilder("select count(1) from AuthorDetailInfo where articleLink = ? ", articleLink).executeInt();
			if (count > 1) {
				Response.setStatus(0);
				Response.setMessage("该帖子链接已存在，不能重复！");
				return;
			}
			
			String cooperationTime = schema.getcooperationTime();
			
			schema.setValue(Request);
			schema.setmodifyDate(new Date());
			schema.setmodifyUser(User.getUserName());
			Transaction trans = new Transaction();
			trans.add(schema, Transaction.UPDATE);
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功！");
				
				/* 生成稿费支付明细 */
				// 合作时间变更
				if (!Request.getString("cooperationTime").equals(cooperationTime)) {
					trans = new Transaction();
					trans.add(new QueryBuilder("delete from PaymemntDetailInfo where authorDetailInfo_id = ? and isPay = 'N'", schema.getid()));
					// 支付规则
					String payRules = new QueryBuilder("select Memo from zdcode where CodeType='Author.payRules' and ParentCode='Author.payRules' and CodeValue=?", schema.getpayRules()).executeString();
					if (StringUtil.isEmpty(payRules)) {
						Response.setMessage(Response.getMessage()+"未取到支付规则，生成稿费明细失败！");
						return;
					}
					String[] rules = payRules.split(",");
					String rule;
					String payTime;
					PaymemntDetailInfoSchema paySchema;
					PaymemntDetailInfoSet set = new PaymemntDetailInfoSet();
					for (int i = 0; i < rules.length; i++) {
						rule = rules[i];
						payTime = calPayTime(schema.getcooperationTime(),rule.split("-")[0]);
						if (StringUtil.isEmpty(payTime)) {
							Response.setMessage(Response.getMessage()+"支付规则（"+rule+"）设置错误，生成稿费明细失败！");
							return;
						}
						paySchema = new PaymemntDetailInfoSchema();
						paySchema.setid(NoUtil.getMaxID("PaymemntDetailInfoID")+"");
						paySchema.setpayTime(payTime);
						paySchema.setpayPrice(new BigDecimal(rule.split("-")[1]));
						paySchema.setisPay("N");
						paySchema.setauthorDetailInfo_id(schema.getid());
						paySchema.setcreateDate(new Date());
						paySchema.setcreateUser(User.getUserName());
						set.add(paySchema);
					}
					trans.add(set, Transaction.INSERT);
					
					if (trans.commit()) {
						Response.setStatus(1);
						Response.setMessage(Response.getMessage()+" 生成稿费明细成功！");
					} else {
						Response.setStatus(0);
						Response.setMessage(Response.getMessage()+" 生成稿费明细失败！");
					}
				}
				
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败！");
			}
			
			
		} else {
			Response.setStatus(0);
			Response.setMessage("未找到该文章数据！");
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
		trans.add(new QueryBuilder("DELETE FROM PaymemntDetailInfo WHERE authorDetailInfo_id IN ('"+ids.replace(",", "','")+"')"));
		trans.add(new QueryBuilder("DELETE FROM AuthorDetailInfo WHERE id IN ('"+ids.replace(",", "','")+"')"));
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
	}
}
