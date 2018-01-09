package com.sinosoft.cms.document;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.selfdriveactivitySchema;
import com.sinosoft.schema.selfdriveactivitySet;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SelfDriveActivity extends Page {

	/*
	 * 活动数据查询
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String standardDateStart = dga.getParams().getString("standardDateStart");
		String standardDateEnd = dga.getParams().getString("standardDateEnd");
		String realName = dga.getParams().getString("realName");
		String mobileNo = dga.getParams().getString("mobileNo");
		String idCode = dga.getParams().getString("idCode");
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		fmt.setLenient(false);
		if (StringUtil.isEmpty(standardDateStart)) {
			standardDateStart = fmt.format(new Date());
		}
		if (StringUtil.isEmpty(standardDateEnd)) {
			standardDateEnd = fmt.format(new Date());
		}
		
		QueryBuilder qb = new QueryBuilder(
				"select id,realname,mobileno,email,idcode,createdate,modifydate from selfdriveactivity "+
				"where "+
				"DATE(createdate) >='" + standardDateStart + "' AND DATE(createdate) <='"+ standardDateEnd + "'"
				);

		if (StringUtil.isNotEmpty(realName)) {
			qb.append("and realname like '%" + realName +"%'");
		}
		if (StringUtil.isNotEmpty(mobileNo)) {
			qb.append("and mobileno like '%" + mobileNo +"%'");
		}
		if (StringUtil.isNotEmpty(idCode)) {
			qb.append("and idcode like '%" + idCode + "%'");
		}
		qb.append("ORDER BY createdate desc ");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);

	}

	/*
	 * 加载活动详细数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Mapx dg2DataBind(Mapx params) {

		Mapx map = new Mapx();
		String id = (String) params.get("id");
		if (id != null) {
			StringBuffer sql1 = new StringBuffer();
			sql1.append("select * from selfdriveactivity where id = '")
					.append(id)
					.append("'");
			QueryBuilder qb1 = new QueryBuilder(sql1.toString());
			DataTable dt1 = qb1.executeDataTable();

			if (dt1 != null && dt1.getRowCount() >= 1) {
				map.put("id", dt1.get(0, "id"));
				map.put("realname", dt1.get(0, "realname"));
				map.put("mobileno", dt1.get(0, "mobileno"));
				map.put("email", dt1.get(0, "email"));
				map.put("idcode", dt1.get(0, "idcode"));
				map.put("createdate", dt1.get(0, "createdate"));
			} else {
				map.put("codeValue", "text");
			}
		}
		map.put("codeName", HtmlUtil.codeToOptions("WXActivityGameType", true));

		return map;
	}

	public boolean edit() {

		selfdriveactivitySchema selfdriveactivitySchema = new selfdriveactivitySchema();
		
		Transaction trans = new Transaction();

		//id
		String id = $V("id");
		//id
		String createdate = $V("createdate");
		// 姓名
		String realname = $V("realname");
		// 手机号
		String mobileno = $V("mobileno");
		// 电子邮箱
		String email = $V("email");
		// 身份证号
		String idcode = $V("idcode");
		
		selfdriveactivitySchema.setid(id);
		selfdriveactivitySchema.setrealname(realname);
		selfdriveactivitySchema.setmobileno(mobileno);
		selfdriveactivitySchema.setemail(email);
		selfdriveactivitySchema.setidcode(idcode);
		selfdriveactivitySchema.setcreatedate(DateUtil.parseDateTime(createdate));
		selfdriveactivitySchema.setmodifydate(new Date());

		trans.add(selfdriveactivitySchema, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("修改数据成功!"); 
		} else {
			Response.setStatus(0);
			Response.setMessage("修改数据发生错误!");
			logger.error("数据修改异常");
			return false;
		}
		return true;

	}

	public void del() {

		String ids = $V("IDs");
		Transaction trans = new Transaction();
		selfdriveactivitySchema schema = new selfdriveactivitySchema();
		selfdriveactivitySet set = schema.query(new QueryBuilder("where id in ('" + ids + "')"));

		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}