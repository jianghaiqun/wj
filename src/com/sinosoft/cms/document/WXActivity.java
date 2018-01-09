package com.sinosoft.cms.document;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ActivityInfoWXSchema;
import com.sinosoft.schema.ActivityInfoWXSet;
import com.sinosoft.schema.ActivityproductSchema;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class WXActivity extends Page {

	private static Properties props = new Properties();
	static {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties");
			InputStreamReader rd = new InputStreamReader(is, "UTF-8");
			props.load(rd);
			rd.close();
			is.close();
			// props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("waperrorinfos.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
	}

	/*
	 * 活动数据查询
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String name = dga.getParams().getString("name");
		QueryBuilder qb = new QueryBuilder(
				"select f.name,if(f.status = '1','正常','停用')as status,f.startTime,f.stopTime,z.codeName as type,f.createDate,f.createStaff,f.id, "
						+ "f.prop1 "
						+ "from activityinfowx f,zdcode z where f.id is not null and z.codetype = 'wxactivitygametype' and f.type = z.codevalue ");

		if (StringUtil.isNotEmpty(name)) {
			qb.append(" and name like ? ", "%" + name + "%");
		}
		qb.append("ORDER BY id desc ");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);

	}

	public static void dg2DataBind(DataGridAction dga) {

		String activityId = dga.getParams().getString("activityId");
		QueryBuilder qb = new QueryBuilder("select * from activityproduct where activityid = ?", activityId);

		qb.append(" ORDER BY createdate ");
		DataTable dt = qb.executeDataTable();
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static void dg3DataBind(DataGridAction dga) {

		String mobile = dga.getParams().getString("mobile");
		QueryBuilder qb = new QueryBuilder("select i.name,m.username,m.email,r.prop2 "
				+ "from member m,activityrecord r,activityinfowx i "
				+ "where m.id = r.prop4 and r.activityId = i.id and m.mobileNo like '%" + mobile + "%'");

		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/*
	 * 加载活动详细数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Mapx initDialog2(Mapx params) {

		Mapx map = new Mapx();
		String id = (String) params.get("id");
		if (id != null) {
			StringBuffer sql1 = new StringBuffer();
			sql1.append("select * from activityinfowx where id = '")
					.append(id)
					.append("'");
			QueryBuilder qb1 = new QueryBuilder(sql1.toString());
			DataTable dt1 = qb1.executeDataTable();

			if (dt1 != null && dt1.getRowCount() >= 1) {
				map.put("id", dt1.get(0, "id"));
				map.put("name", dt1.get(0, "name"));
				map.put("codeValue", dt1.get(0, "type"));
				map.put("activityLogo1", dt1.get(0, "activitylogo1"));
				map.put("activityLogo2", dt1.get(0, "activitylogo2"));
				map.put("shareTitle", dt1.get(0, "sharetitle"));
				map.put("shareDescribe", dt1.get(0, "sharedescribe"));
				map.put("productId", dt1.get(0, "productId"));
				map.put("productImage", dt1.get(0, "productImage"));
				map.put("productName", dt1.get(0, "productName"));
				map.put("productDescribe", dt1.get(0, "productDescribe"));
				map.put("description", dt1.get(0, "description"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				map.put("standardDateStart", sdf.format(dt1.get(0, "startTime")).split(" ")[0]);
				map.put("standardDateEnd", sdf.format(dt1.get(0, "stopTime")).split(" ")[0]);
				map.put("standardTimeStart", sdf.format(dt1.get(0, "startTime")).split(" ")[1]);
				map.put("standardTimeEnd", sdf.format(dt1.get(0, "stopTime")).split(" ")[1]);
				map.put("gamePeriod", dt1.get(0, "gamePeriod"));
				map.put("createDate", dt1.get(0, "createDate"));
				map.put("createStaff", dt1.get(0, "createStaff"));
				map.put("ruleText", dt1.get(0, "ruleText"));
			} else {
				map.put("codeValue", "text");
			}
		}
		map.put("codeName", HtmlUtil.codeToOptions("WXActivityGameType", true));

		return map;
	}

	public boolean save() {

		ActivityInfoWXSchema activityInfoWXSchema = new ActivityInfoWXSchema();
		Transaction trans = new Transaction();

		String userName = User.getUserName();
		// 活动名称
		String name = $V("name");
		// 活动类型
		String type = $V("type");
		// 活动logo1
		String activityLogo1 = $V("activityLogo1");
		// 活动logo2
		String activityLogo2 = $V("activityLogo2");
		// 活动logo1
		String shareTitle = $V("shareTitle");
		// 活动logo2
		String shareDescribe = $V("shareDescribe");
		// 参与产品
		String productId = $V("productId");
		// 产品图片
		String productImage = $V("productImage");
		// 产品名称
		String productName = $V("productName");
		// 产品描述
		String productDescribe = $V("productDescribe");
		// 开始日期
		String standardDateStart = $V("standardDateStart");
		// 开始时间
		String standardTimeStart = $V("standardTimeStart");
		// 结束日期
		String standardDateEnd = $V("standardDateEnd");
		// 结束时间
		String standardTimeEnd = $V("standardTimeEnd");
		// 游戏时长
		String gamePeriod = $V("gamePeriod");
		// 活动数据
		String ruleText = $V("ruleText");
		// 活动描述
		String description = $V("description");

		String id = NoUtil.getMaxNo("wxActivityInfo", 6);

		activityInfoWXSchema.setid(id);
		activityInfoWXSchema.setname(name);
		activityInfoWXSchema.setdescription(description);
		activityInfoWXSchema.settype(type);
		activityInfoWXSchema.setactivitylogo1(activityLogo1);
		activityInfoWXSchema.setactivitylogo2(activityLogo2);
		activityInfoWXSchema.setsharetitle(shareTitle);
		activityInfoWXSchema.setsharedescribe(shareDescribe);
		activityInfoWXSchema.setstatus("1");
		activityInfoWXSchema.setstartTime(DateUtil.parseDateTime(standardDateStart + " " + standardTimeStart));
		activityInfoWXSchema.setstopTime(DateUtil.parseDateTime(standardDateEnd + " " + standardTimeEnd));
		activityInfoWXSchema.setgamePeriod(gamePeriod);
		activityInfoWXSchema.setproductId(productId);
		activityInfoWXSchema.setproductImage(productImage);
		activityInfoWXSchema.setproductName(productName);
		activityInfoWXSchema.setproductDescribe(productDescribe);
		activityInfoWXSchema.setruleText(ruleText);
		activityInfoWXSchema.setcreateDate(new Date());
		activityInfoWXSchema.setcreateStaff(userName);
		activityInfoWXSchema.setmodifyDate(new Date());
		activityInfoWXSchema.setmodifyStaff(userName);

		if ("001".equals(type)) {
			// 拼接活动地址
			activityInfoWXSchema.setprop1(Config.getValue("WapServerContext") + "/act/" + id + "/index.shtml");
		} else if ("002".equals(type)) {
			activityInfoWXSchema.setprop1(Config.getValue("WapServerContext") + "/act/" + id + "/zpindex.shtml");
		}

		DataTable dt = Request.getDataTable("Data");
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow row = dt.get(i);
			ActivityproductSchema activityproductSchema = new ActivityproductSchema();
			int opType = Transaction.INSERT;
			activityproductSchema.setid(NoUtil.getMaxNo("Activityproduct", 10));
			activityproductSchema.setactivityId(id);
			activityproductSchema.setadduser(userName);
			activityproductSchema.setcreatedate(new Date());
			activityproductSchema.setproductImage(row.getString(2));
			activityproductSchema.setproductUrl(row.getString(3));
			trans.add(activityproductSchema, opType);
		}

		trans.add(activityInfoWXSchema, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("新建活动成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("新建活动发生错误!");
			logger.error("微信活动创建异常");
			return false;
		}
		return true;
	}

	public boolean edit() {

		ActivityInfoWXSchema activityInfoWXSchema = new ActivityInfoWXSchema();
		Transaction trans = new Transaction();

		String userName = User.getUserName();
		// id
		String id = $V("activityId");
		// 活动名称
		String name = $V("name");
		// 活动类型
		String type = $V("type");
		// 活动类型
		String activityLogo1 = $V("activityLogo1");
		// 活动类型
		String activityLogo2 = $V("activityLogo2");
		// 分享标题
		String shareTitle = $V("shareTitle");
		// 分享描述
		String shareDescribe = $V("shareDescribe");
		// 参与产品
		String productId = $V("productId");
		// 产品图片
		String productImage = $V("productImage");
		// 产品名称
		String productName = $V("productName");
		// 产品描述
		String productDescribe = $V("productDescribe");
		// 开始日期
		String standardDateStart = $V("standardDateStart");
		// 开始时间
		String standardTimeStart = $V("standardTimeStart");
		// 结束日期
		String standardDateEnd = $V("standardDateEnd");
		// 结束时间
		String standardTimeEnd = $V("standardTimeEnd");
		// 游戏时长
		String gamePeriod = $V("gamePeriod");
		// 活动数据
		String ruleText = $V("ruleText");
		// 活动描述
		String description = $V("description");
		// 创建人
		String createDate = $V("createDate");
		// 创建时间
		String createStaff = $V("createStaff");

		activityInfoWXSchema.setid(id);
		activityInfoWXSchema.setname(name);
		activityInfoWXSchema.setdescription(description);
		activityInfoWXSchema.settype(type);
		activityInfoWXSchema.setactivitylogo1(activityLogo1);
		activityInfoWXSchema.setactivitylogo2(activityLogo2);
		activityInfoWXSchema.setsharetitle(shareTitle);
		activityInfoWXSchema.setsharedescribe(shareDescribe);
		activityInfoWXSchema.setstatus("1");
		activityInfoWXSchema.setstartTime(DateUtil.parseDateTime(standardDateStart + " " + standardTimeStart));
		activityInfoWXSchema.setstopTime(DateUtil.parseDateTime(standardDateEnd + " " + standardTimeEnd));
		activityInfoWXSchema.setgamePeriod(gamePeriod);
		activityInfoWXSchema.setproductId(productId);
		activityInfoWXSchema.setproductImage(productImage);
		activityInfoWXSchema.setproductName(productName);
		activityInfoWXSchema.setproductDescribe(productDescribe);
		activityInfoWXSchema.setruleText(ruleText);
		activityInfoWXSchema.setcreateDate(DateUtil.parseDateTime(createDate));
		activityInfoWXSchema.setcreateStaff(createStaff);
		activityInfoWXSchema.setmodifyDate(new Date());
		activityInfoWXSchema.setmodifyStaff(userName);
		if ("001".equals(type)) {
			// 拼接活动地址
			activityInfoWXSchema.setprop1(Config.getValue("WapServerContext") + "/act/" + id + "/index.shtml");
		} else if ("002".equals(type)) {
			activityInfoWXSchema.setprop1(Config.getValue("WapServerContext") + "/act/" + id + "/zpindex.shtml");
		}

		trans.add(activityInfoWXSchema, Transaction.UPDATE);
		DataTable dt = Request.getDataTable("Data");

		trans.add(new QueryBuilder("delete from activityproduct where activityId = ?", id));
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow row = dt.get(i);
			ActivityproductSchema activityproductSchema = new ActivityproductSchema();
			activityproductSchema.setid(NoUtil.getMaxNo("Activityproduct", 10));
			activityproductSchema.setadduser(userName);
			activityproductSchema.setcreatedate(new Date());
			activityproductSchema.setmodifyuser(userName);
			activityproductSchema.setmodifydate(new Date());
			activityproductSchema.setproductImage(row.getString(2));
			activityproductSchema.setproductUrl(row.getString(3));
			activityproductSchema.setactivityId(id);
			trans.add(activityproductSchema, Transaction.INSERT);
		}

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("修改活动成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("修改活动发生错误!");
			logger.error("微信活动修改异常");
			return false;
		}
		return true;

	}

	public void del() {

		String ids = $V("IDs");
		Transaction trans = new Transaction();
		ActivityInfoWXSchema info = new ActivityInfoWXSchema();
		ActivityInfoWXSet set = info.query(new QueryBuilder("where id in (" + ids + ")"));

		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void stop() {

		String ids = $V("IDs");
		Transaction trans = new Transaction();
		String sql = "update activityinfowx set status = '0' where id in (" + ids + ")";
		QueryBuilder qb = new QueryBuilder(sql);
		trans.add(qb);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("停用成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}