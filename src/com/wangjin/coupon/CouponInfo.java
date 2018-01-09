package com.wangjin.coupon;

import cn.com.sinosoft.util.Constant;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityGiveDetail;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.CouponApproveInfoSchema;
import com.sinosoft.schema.SDCouponInfoSchema;
import com.sinosoft.schema.SDInterActionSchema;
import com.sinosoft.schema.SDInterActionSet;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CouponInfo extends Page {

	// private Logger log = Logger.getLogger(CouponInfo.class);
	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
	
	/**
	 * 优惠券页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Mapx<String, String> init(Mapx<String, String> params) {

		params.put("statusInit", HtmlUtil.codeToOptions("Coupon.status", true));// 状态
		params.put("purposeInit", HtmlUtil.codeToOptions("Coupon.purpose", true));// 用途
		QueryBuilder qb_createUser = new QueryBuilder("Select realName,userName from ZDUser ");
		// String activityChannel = params.getString("ActivityChannel");
		DataTable createUser = qb_createUser.executeDataTable();
		params.put("createUserInit", HtmlUtil.dataTableToOptions(createUser));// 申请人
		// 险种
		QueryBuilder qb_riskCode = new QueryBuilder(
				"SELECT codename,code FROM fdcode WHERE codetype='erisksubtype' AND CODE NOT LIKE '%00' ");
		DataTable dt_riskcode = qb_riskCode.executeDataTable();
		params.put("riskcodeInit", HtmlUtil.dataTableToOptions(dt_riskcode, true));
		// 公司
		QueryBuilder qb_insuranceCompany = new QueryBuilder(
				"SELECT codename,codevalue FROM zdcode WHERE codetype='SupplierCode' AND codevalue NOT LIKE '%sys%' ");
		DataTable dt_insuranceCompany = qb_insuranceCompany.executeDataTable();
		params.put("insuranceCompanyInit", HtmlUtil.dataTableToOptions(dt_insuranceCompany, true));
		// 批次
		QueryBuilder qb_batch = new QueryBuilder(
				"SELECT DISTINCT(a.batch) as batch, batch as bat FROM CouponInfo a LEFT JOIN member d  ON a.memberId = d.id  LEFT JOIN zdcode z   ON z.codevalue = a.status WHERE z.codetype = 'coupon.status'  AND 1 = 1  AND a.status != 6  GROUP BY a.batch");
		DataTable dt_batch = qb_batch.executeDataTable();
		params.put("batch", HtmlUtil.dataTableToOptions(dt_batch, true));
		params.put("yesterday", yesterday);
		params.put("today", today);
		params.put("username", User.getUserName());
		params.put("realname", User.getRealName());
		// Mapx mapx=CacheManager.getMapx("Code", "Coupon.channel");
		// Mapx mapxchannel = new Mapx();
		// String checkstr="";
		// for(Iterator it = mapx.keySet().iterator();it.hasNext();){
		// Object key = it.next();
		// checkstr=checkstr+String.valueOf(key)+",";
		// if ("cps".equals(activityChannel)) {
		// if (String.valueOf(key).contains("cps")) {
		// checkstr=checkstr+String.valueOf(key)+",";
		// mapxchannel.put(key, mapx.get(key));
		// }
		// }
		// else {
		// // 添加淘宝渠道 by 20151208 wangwenying
		// //if (!String.valueOf(key).contains("tb")) {
		// checkstr=checkstr+String.valueOf(key)+",";
		// mapxchannel.put(key, mapx.get(key));
		// //}
		// }
		// }
		// String
		// []checkarray=checkstr.substring(0,checkstr.length()-1).split(",");
		// params.put("channel",HtmlUtil.mapxToCheckboxes("channel",mapxchannel,checkarray,null));

		// 查询应用渠道
		Mapx mapxchannel = new Mapx();
		try {
			List<Map<String, Object>> channels = new GetDBdata().queryObj("select ChannelCode,ChannelName "
					+ "From channelinfo where ChannelLevel = '1'");
			for (Map<String, Object> channel : channels) {
				mapxchannel.put(channel.get("ChannelCode"), channel.get("ChannelName"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		params.put("channelSelect", HtmlUtil.mapxToOptions(mapxchannel, true));
		// 优惠券类型
		params.put("couponTypeInit", HtmlUtil.codeToOptions("Coupon.type", false));
		params.put("couponTypeInit1", HtmlUtil.codeToOptions("Coupon.type", true));
		// 优惠券折扣
		params.put("discountInit", HtmlUtil.codeToOptions("Coupon.discount", true));
		// 发放渠道
		params.put("issuechannelCheckbox", HtmlUtil.codeToCheckboxes("issuechannel", "Coupon.issuechannel"));
		params.put("issuechannelSelect", HtmlUtil.codeToOptions("Coupon.issuechannel", true));
		return params;
	}

	public static void initProvideUser(DataGridAction dga) {

		QueryBuilder qb_provideUser = new QueryBuilder("Select realName,userName from ZDUser ");
		DataTable provideUser = qb_provideUser.executeDataTable();
		dga.bindData(provideUser);
	}

	/**
	 * 编辑优惠券页面初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {

		String ID = params.getString("ID");
		QueryBuilder qb = new QueryBuilder(
				"select Id, CouponSn, RiskCode, InsuranceCompany,provideUser, ParValue, PayAmount, UseTimes, Direction, StartTime, EndTime, STATUS, Purpose, OrderSn, PayTime, MemberId, Mail, Mobile, Prop1, Prop2, Prop3, Prop4, CreateUser, CreateDate, ModifyUser, ModifyDate,realProvideUser,channelsn,product,shortName,issuechannel,remindFlag,maxDeduction from CouponInfo where id=?");
		qb.add(ID);
		DataTable dt = qb.executeDataTable();
		DataRow dr = dt.getDataRow(0);
		params.put("direction", dr.getString("direction"));
		params.put("payAmount", dr.getString("payAmount"));
		params.put("parValue", dr.getString("parValue"));
		params.put("createNum", dr.getString("createNum"));
		params.put("useTimes", dr.getString("useTimes"));
		params.put("purpose", dr.getString("purpose"));
		params.put("createUser", dr.getString("createUser"));
		params.put("startT", dr.getString("startTime"));
		params.put("endT", dr.getString("endTime"));
		params.put("createDate", dr.getString("createDate"));
		params.put("couponsn", dr.getString("couponsn"));
		params.put("prop4", dr.getString("prop4"));
		params.put("prop3", dr.getString("prop3"));
		params.put("shortName", dr.getString("shortName"));
		String startTime = dr.getString("startTime");
		if (startTime.length() != 0) {
			params.put("startDate", dr.getString("startTime").substring(0, dr.getString("startTime").indexOf(" ")));
			params.put(
					"startTime",
					dr.getString("startTime").substring(dr.getString("startTime").indexOf(" ") + 1,
							dr.getString("startTime").length()));
			params.put("endDate", dr.getString("endTime").substring(0, dr.getString("endTime").indexOf(" ")));
			params.put(
					"endTime",
					dr.getString("endTime").substring(dr.getString("endTime").indexOf(" ") + 1,
							dr.getString("endTime").length()));
		} else {
			params.put("startDate", "");
			params.put("startTime", "");
			params.put("endDate", "");
			params.put("endTime", "");
		}
		// 优惠券类型
		String couponType = dr.getString("prop3");
		// 非折扣券
		if ("01".equals(couponType)) {
			// 显示面值
			params.put("parValueDis", "");
			// 隐藏折扣
			params.put("discountDis", "none");
		} else { // 折扣券
			// 隐藏面值
			params.put("parValueDis", "none");
			// 显示折扣
			params.put("discountDis", "");
		}
		params.put("statusInit", HtmlUtil.codeToOptions("Coupon.status", true));// 状态
		QueryBuilder qb_Purpose = new QueryBuilder("select Purpose from CouponInfo where id=?");
		qb_Purpose.add(ID);
		DataTable Purpose = qb_Purpose.executeDataTable();
		params.put("purposeInit", HtmlUtil.codeToOptions("Coupon.purpose", Purpose, true));// 用途
		params.put("discountInit", HtmlUtil.codeToOptions("Coupon.discount", Purpose, true));// 折扣
		QueryBuilder qb_createUser = new QueryBuilder("Select realName,userName from ZDUser ");
		DataTable createUser = qb_createUser.executeDataTable();
		QueryBuilder qb_createU = new QueryBuilder("select CreateUser from CouponInfo where id=?");
		qb_createU.add(ID);
		DataTable createU = qb_createU.executeDataTable();
		params.put("createUserInit", HtmlUtil.dataTableToOptions(createUser, createU, true));// 申请人
		// 优惠公司
		String insuranceCompany[] = dr.getString("insuranceCompany").split(",");
		if (insuranceCompany[0].length() > 0) {
			String sql_company = "";
			for (int i = 0; i < insuranceCompany.length; i++) {
				sql_company += "'" + insuranceCompany[i] + "',";
			}
			QueryBuilder querybuilder = new QueryBuilder(
					"SELECT codename FROM zdcode WHERE codetype='SupplierCode' AND codevalue NOT LIKE '%sys%'  AND codevalue IN ("
							+ sql_company.substring(0, sql_company.length() - 1) + ")");
			DataTable datatable = querybuilder.executeDataTable();
			String insuranceCompanyother = "";
			for (int i = 0; i < datatable.getRowCount(); i++) {
				insuranceCompanyother += datatable.getDataRow(i).getString("codename") + ",";
			}
			params.put("insuranceCompanyother", insuranceCompanyother.substring(0, insuranceCompanyother.length() - 1));
		} else {
			params.put("insuranceCompanyother", "");
		}
		params.put("insuranceCompany", dr.getString("insuranceCompany"));// 优惠公司
		// 险种
		String riskcode[] = dr.getString("riskCode").split(",");
		if (riskcode[0].length() > 0) {
			String sql_riskcode = "";
			for (int i = 0; i < riskcode.length; i++) {
				sql_riskcode += "'" + riskcode[i] + "',";
			}
			QueryBuilder qb_riskcode = new QueryBuilder(
					"SELECT codename FROM fdcode WHERE codetype='erisksubtype' AND CODE NOT LIKE '%00' AND code IN ("
							+ sql_riskcode.substring(0, sql_riskcode.length() - 1) + ")");
			DataTable dt_riskcode = qb_riskcode.executeDataTable();
			String riskcodeother = "";
			for (int i = 0; i < dt_riskcode.getRowCount(); i++) {
				riskcodeother += dt_riskcode.getDataRow(i).getString("codename") + ",";
			}
			params.put("riskCodeother", riskcodeother.substring(0, riskcodeother.length() - 1));// 优惠险种
		} else {
			params.put("riskCodeother", "");// 优惠险种
		}
		params.put("riskCode", dr.getString("riskCode"));// 优惠险种

		// 优惠产品
		String product[] = dr.getString("product").split(",");
		if (product.length > 0 && product[0].length() > 0) {
			String sql_product = "";
			for (int i = 0; i < product.length; i++) {
				sql_product += ",'" + product[i] + "'";
			}
			QueryBuilder qb_product = new QueryBuilder("SELECT ProductName FROM sdproduct WHERE ProductID IN ("
					+ sql_product.substring(1) + ")");
			DataTable dt_product = qb_product.executeDataTable();
			String productother = "";
			for (int i = 0; i < dt_product.getRowCount(); i++) {
				productother += ("," + dt_product.getDataRow(i).getString("ProductName"));
			}
			params.put("productother", productother.substring(1));
		} else {
			params.put("productother", "");// 优惠险种
		}
		params.put("product", dr.getString("product"));

		// 发放人
		String provideuser[] = dr.getString("provideUser").split(",");
		if (provideuser[0].length() > 0) {
			String sql_provideuser = "";
			for (int i = 0; i < provideuser.length; i++) {
				sql_provideuser += "'" + provideuser[i] + "',";
			}
			QueryBuilder qb_provideuser = new QueryBuilder("Select realName from ZDUser  WHERE userName IN ("
					+ sql_provideuser.substring(0, sql_provideuser.length() - 1) + ")");
			DataTable dt_provideuser = qb_provideuser.executeDataTable();
			String provideuserother = "";
			for (int i = 0; i < dt_provideuser.getRowCount(); i++) {
				provideuserother += dt_provideuser.getDataRow(i).getString("realName") + ",";
			}
			params.put("provideUserother", provideuserother.substring(0, provideuserother.length() - 1));// 发放人
		} else {
			params.put("provideUserother", "");// 发放人
		}
		// 优惠券是否与活动共用
		String Prop1 = dr.getString("Prop1");
		if (StringUtil.isEmpty(Prop1) || !("Y".equals(Prop1))) {
			params.put("Prop1", "");
		} else {
			params.put("Prop1", "checked");
		}
        // 优惠券是否与活动共用
        String remindFlag = dr.getString("remindFlag");
        if (StringUtil.isEmpty(remindFlag) || !("Y".equals(remindFlag))) {
            params.put("remindFlag", "");
        } else {
            params.put("remindFlag", "checked");
        }
		params.put("provideUser", dr.getString("provideUser"));// 发放人
		params.put("yesterday", yesterday);
		params.put("today", today);
		// 初始化应用渠道
		// add by wengej 20160112 代理人优惠劵修改报错 bug0001952
		// Mapx mapx=CacheManager.getMapx("Code", "Coupon.channel");
		// String checkstr="";
		// for(Iterator it = mapx.keySet().iterator();it.hasNext();){
		// Object key = it.next();
		// if ("cps".equals(String.valueOf(key))) {
		// checkstr =mapx.get(key).toString();
		// }
		// }

		// params.put("channel",HtmlUtil.mapxToCheckboxes("channel",CacheManager.getMapx("Code",
		// "Coupon.channel"),dr.getString("channelsn").split(","),null));
		params.put(
				"issuechannelCheckbox",
				HtmlUtil.mapxToCheckboxes("issuechannel", CacheManager.getMapx("Code", "Coupon.issuechannel"),
						dr.getString("issuechannel").split(","), null));
		String maxDeduction = dr.getString("maxDeduction");
		if(maxDeduction == null){
			maxDeduction = "";
		}
		params.put("maxDeduction", maxDeduction);

		return params;
	}

	/**
	 * 异步优惠券异步添加
	 */
	public void asyncAdd() {

		LongTimeTask ltt = new LongTimeTask() {

			String currUser = User.getUserName();// 当前用户

			public void execute() {

				add(this, currUser);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		$S("TaskID", "" + ltt.getTaskID());
	}

	/**
	 * 优惠券增加
	 */
	public void add(LongTimeTask task, String currUserName) {

		int createNum = Integer.parseInt(Request.getString("createNum"));
		if (createNum < 1) {
			task.addError("生成数量输入有误！");
			return;
		}

		String couponType = Request.getString("prop3");
		if (StringUtil.isEmpty(couponType)) {
			task.addError("请选择优惠券类型！");
			return;
		}
		// 查询当前用户的优惠券值折扣限制
		String sql = "select CodeName from zdcode where CodeType='CouponVar.Permission' "
				+ "and ParentCode='CouponVar.Permission' and CodeValue = ?";
		QueryBuilder qb = new QueryBuilder(sql, currUserName);
		String varPermission = qb.executeString();
		// 非折扣券
		if ("01".equals(couponType)) {
			if (StringUtil.isNotEmpty(varPermission)
					&& !"null".equals(varPermission)) {
				if (StringUtil.isEmpty(Request.getString("payAmount"))) {
					task.addError("消费金额是必填项，请输入!");
					return;
				}
				if (StringUtil.isEmpty(Request.getString("parValue"))) {
					task.addError("面值是必填项，请输入!");
					return;
				}
				// 消费金额
				double payAmount = Double.valueOf(Request.getString("payAmount"));
				// 面值
				double parValue = Double.valueOf(Request.getString("parValue"));
				// 折扣限制值
				double douPermission = Double.valueOf(varPermission
						.replace("%", "")) / 100.0;

				// 折扣值
				double value = 0.0;
				if (payAmount != 0) {
					// 折扣值 = 面值/消费金额
					value = parValue / payAmount;
				}
				// 超过折扣限制时报错
				if (value > douPermission) {
					task.addError("超过了您的券值折扣限制" + varPermission
							+ "（面值/消费金额）!");
					return;
				}
			}
		} else { // 折扣券
			if (StringUtil.isEmpty(Request.getString("prop4"))) {
				task.addError("折扣是必选项，请选择!");
				return;
			} else {
				if (StringUtil.isNotEmpty(varPermission)
						&& !"null".equals(varPermission)) {
					// 折扣限制值
					double douPermission = Double.valueOf(varPermission
							.replace("%", "")) / 10.0;
					// 折扣不能低于折扣限制
					if (douPermission > Double.valueOf(Request.getString("prop4"))) {
						task.addError("超过了您的券值折扣限制" + varPermission);
						return;
					}
				}
			}
		}

		int flag = 0;
		// 定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String RiskCode_str = Request.getString("riskCode");
		String InsuranceCompany_str = Request.getString("insuranceCompany");
		String startDate = Request.getString("startDate");
		String startTime = Request.getString("startTime");
		String endDate = Request.getString("endDate");
		String endTime = Request.getString("endTime");
		String isApply = Request.getString("isApply");
		String starttime = "";
		String endtime = "";
		try {
			starttime = DateUtil.toDateTimeString(sdf.parse(startDate + " " + startTime));
			endtime = DateUtil.toDateTimeString(sdf.parse(endDate + " " + endTime));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		String year = DateUtil.getCurrentDateTime("yyyy");
		// 生成优惠券批次
		String batch = year + NoUtil.getMaxNo("CouponBatch", 8);
		String message = "添加成功！";
		logger.info("优惠券创建（数目：{}）开始：{}", createNum, System.currentTimeMillis());
		// 事务创建
		Transaction trans = null;
		String parValue = Request.getString("parValue");
		// 折扣券面值设置为0
		if ("02".equals(couponType)) {
			parValue = "0";
		}
		task.setCurrentInfo("正在生成优惠券(0/" + createNum + ")......");
		for (int i = 0; i < createNum; i++) {
			// 终止任务
			if (task.checkStop()) {
				return;
			}
			if (i % 200 == 0) {
				trans = new Transaction();
			}
			// 优惠券schema
			SDCouponInfoSchema couponSchema = new SDCouponInfoSchema();
			// 将DataCollection中的键值对按名称自动匹配到Schema的字段中去
			couponSchema.setValue(Request);
			// 渠道编号
			String channelInfo = Request.getString("channel");
			// 判断是否可与活动同时使用,更改优惠券描述
			String prop1 = Request.getString("prop1");
			if (!"Y".equals(prop1)) {
				couponSchema.setDirection(couponSchema.getDirection() + "（不可与活动同时使用）");
			}
			// 处理，渠道信息；针对该渠道下的所有子渠道增加活动信息
			couponSchema.setChannelSn(channelInfo);

			try {
				if (startDate.length() != 0) {
					// 开始时间
					couponSchema.setStartTime(sdf.parse(startDate + " " + startTime));
					// 结束时间
					couponSchema.setEndTime(sdf.parse(endDate + " " + endTime));
				}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			// 生成优惠劵ID
			String id = year + NoUtil.getMaxNo("CouponSn", 12);
			// 优惠券ID值
			couponSchema.setId(id);
			// 优惠券批次
			couponSchema.setBatch(batch);
			// 优惠券号
			couponSchema.setCouponSn(DigestUtils.md5Hex(id));
			// 初始状态为未使用
			couponSchema.setStatus("0");
			// 使用次数默认为1
			couponSchema.setUseTimes("1");
			// 优惠券面值
			couponSchema.setParValue(parValue);
			// 优惠券立即使用URL
			if (StringUtil.isNotEmpty(Request.getString("usedUrl"))) {
				couponSchema.setUsedUrl(Request.getString("usedUrl"));
			} else {
				couponSchema.setUsedUrl("javascript:void(0);");
			}
			try {
				Date date = sdf.parse(PubFun.getCurrent());
				// 系统时间
				couponSchema.setCreateDate(date);
				// couponSchema.setCreateUser(User.getUserName());
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			if (StringUtil.isEmpty(couponSchema.getRemindFlag())) {
                couponSchema.setRemindFlag("N");
            }
			// 增加数据
			trans.add(couponSchema, Transaction.INSERT);
			if (((i - 199) % 200) == 0 && i != 0) {
				task.setCurrentInfo("正在生成优惠券(" + (i + 1) + "/" + createNum + ")......");
				task.setPercent(Integer
						.valueOf((i + 1) * 100 / (createNum)));
				if (!trans.commit()) {
					flag++;
					break;
				}
			}
		}
		// 添加批次审核信息
		CouponApproveInfoSchema couponApproveInfoSchema = new CouponApproveInfoSchema();
		couponApproveInfoSchema.setid(NoUtil.getMaxID("CouponApproveInfo") + "");
		couponApproveInfoSchema.setcouponBatch(batch);
		couponApproveInfoSchema.setcouponApplicant(Request.getString("createUser"));
		if ("1".equals(isApply)) {
			couponApproveInfoSchema.setapproveStatus("0");
			couponApproveInfoSchema.setremark2("1");
		} else {
			couponApproveInfoSchema.setapproveStatus("1");
			couponApproveInfoSchema.setcreateDate(new Date());
			couponApproveInfoSchema.setremark1("自动审核");
			couponApproveInfoSchema.setremark2("0");// 系统自动审核标志
		}
		trans.add(couponApproveInfoSchema, Transaction.INSERT);
		// 将多出200整数倍的事务提交
		if ((createNum % 200) != 0) {
			task.setCurrentInfo("正在生成优惠券(" + createNum + "/" + createNum + ")......");
			task.setPercent(100);
		}
		if (!trans.commit()) {
			flag++;
		}
		task.setPercent(100);
		if (flag == 0) {// 所有事务都已正确提交
			// 生成的优惠劵从优惠险种、优惠公司、有效期三个维度进行重叠性校验
			logger.info("优惠券创建（数目：{}）结束：{}", createNum, System.currentTimeMillis());
			if (RiskCode_str.length() != 0) {
				String RiskCode[] = RiskCode_str.split(",");
				for (int j = 0; j < RiskCode.length; j++) {
					QueryBuilder querybuilder = new QueryBuilder("SELECT SUM(FIND_IN_SET('" + RiskCode[j]
							+ "',riskcode)) FROM CouponInfo where status !='6'  and  batch !='" + batch + "' ");
					int num = querybuilder.executeInt();
					if (num != 0) {
						message += "优惠险种有重叠；";
						break;
					}
				}
			}
			if (InsuranceCompany_str.length() != 0) {
				String InsuranceCompany[] = InsuranceCompany_str.split(",");
				for (int j = 0; j < InsuranceCompany.length; j++) {
					QueryBuilder querybuilder = new QueryBuilder("SELECT SUM(FIND_IN_SET('" + InsuranceCompany[j]
							+ "',insurancecompany)) FROM CouponInfo where status !='6'   and  batch !='" + batch
							+ "' ");
					int num = querybuilder.executeInt();
					if (num != 0) {
						message += "优惠公司有重叠；";
						break;
					}
				}
			}
			if (starttime != "" && starttime != null) {
				QueryBuilder querybuilder = new QueryBuilder(
						"SELECT id FROM CouponInfo where  status !='6' and UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('"
								+ starttime.trim()
								+ "','%Y-%m-%d %H:%i:%s')) and  UNIX_TIMESTAMP(DATE_FORMAT('"
								+ endtime.trim()
								+ "','%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s'))   and  batch !='"
								+ batch + "' ");
				int num = querybuilder.executeDataTable().getRowCount();
				if (num > 0) {
					message += "优惠券有效时间有重叠；";
				}
			}
			task.setCurrentInfo(message.substring(0, message.length() - 1));
		} else {
			task.addError("发生错误！");
		}
	}

	/**
	 * 优惠券修改
	 */
	public void edit() {

		// 事务创建
		Transaction trans = new Transaction();
		// 优惠券schema
		SDCouponInfoSchema couponSchema = new SDCouponInfoSchema();
		// 将DataCollection中的键值对按名称自动匹配到Schema的字段中去
		couponSchema.setId(Request.getString("ID"));
		if (couponSchema.fill()) {
			couponSchema.setValue(Request);
			// 判断是否可与活动同时使用,更改优惠券描述
			String direction = couponSchema.getDirection();
			String prop1 = Request.getString("prop1");
			if (!"Y".equals(prop1)) {
				couponSchema.setDirection(direction + "（不可与活动同时使用）");
			} else {
				if (direction.indexOf("（不可与活动同时使用）") != -1) {
					couponSchema.setDirection(direction.substring(0, direction.indexOf("（不可与活动同时使用）")));
				}
			}
			// 渠道编号
			String channelInfo = Request.getString("channel");
			// 处理，渠道信息；针对该渠道下的所有子渠道增加活动信息
			// add by wengej 20160112 代理人优惠劵修改报错 bug0001952
			// 如果channelInfo只有一个渠道，说明当前edit是代理人优惠劵
			couponSchema.setChannelSn(channelInfo);

			// 只有未使用状态的优惠券才可以更改
			String status = couponSchema.getStatus();
			if (!(status.equals("0"))) {
				Response.setStatus(0);
				Response.setMessage("只有未使用状态的优惠券才可以修改!");
				return;
			}
			String strParValue = Request.getString("parValue");
			// 查询当前用户的优惠券值折扣限制
			String sql = "select CodeName from zdcode where CodeType='CouponVar.Permission' "
					+ "and ParentCode='CouponVar.Permission' and CodeValue = ?";
			QueryBuilder qb = new QueryBuilder(sql, User.getUserName());
			String varPermission = qb.executeString();
			// 非折扣券
			if ("01".equals(couponSchema.getProp3())) {
				if (StringUtil.isNotEmpty(varPermission)
						&& !"null".equals(varPermission)) {
					if (StringUtil.isEmpty(Request.getString("payAmount"))) {
						Response.setStatus(0);
						Response.setMessage("消费金额是必填项，请输入!");
						return;
					}
					if (StringUtil.isEmpty(Request.getString("parValue"))) {
						Response.setStatus(0);
						Response.setMessage("面值是必填项，请输入!");
						return;
					}
					// 消费金额
					double payAmount = Double.valueOf(Request
							.getString("payAmount"));
					// 面值
					double parValue = Double.valueOf(Request.getString("parValue"));
					// 折扣限制值
					double douPermission = Double.valueOf(varPermission.replace(
							"%", "")) / 100.0;

					// 折扣值
					double value = 0.0;
					if (payAmount != 0) {
						// 折扣值 = 面值/消费金额
						value = parValue / payAmount;
					}
					// 超过折扣限制时报错
					if (value > douPermission) {
						Response.setStatus(0);
						Response.setMessage("超过了您的券值折扣限制" + varPermission
								+ "（面值/消费金额）!");
						return;
					}
				}
			} else {// 折扣券
				strParValue = "0";
				// 折扣
				String prop4 = Request.getString("prop4");
				if (StringUtil.isEmpty(prop4)) {
					Response.setStatus(0);
					Response.setMessage("折扣是必选项，请选择!");
					return;
				} else {
					if (StringUtil.isNotEmpty(varPermission)
							&& !"null".equals(varPermission)) {
						// 折扣限制值
						double douPermission = Double.valueOf(varPermission
								.replace("%", "")) / 10.0;
						// 折扣不能低于折扣限制
						if (douPermission > Double.valueOf(prop4)) {
							Response.setStatus(0);
							Response.setMessage("超过了您的券值折扣限制" + varPermission);
							return;
						}
					}
				}
			}
			couponSchema.setParValue(strParValue);
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				String startDate = Request.getString("startDate");
				String startTime = Request.getString("startTime");
				if (startDate.length() != 0) {
					// 开始时间
					couponSchema.setStartTime(sdf.parse(startDate + " " + startTime));
					// 结束时间
					couponSchema.setEndTime(sdf.parse(Request.getString("endDate") + " " + Request.getString("endTime")));
					// 修改时间和修改人
					Date date = sdf.parse(PubFun.getCurrent());
					couponSchema.setModifyDate(date);
					couponSchema.setModifyUser(User.getUserName());
				}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
            if (StringUtil.isEmpty(couponSchema.getRemindFlag())) {
                couponSchema.setRemindFlag("N");
            }
			// 修改数据
			trans.add(couponSchema, Transaction.UPDATE);
			if (trans.commit()) {// 提交事务
				Response.setStatus(1);
				Response.setMessage("编辑成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		}
	}

	/**
	 * 优惠券删除
	 */
	public void delete() {

		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
			sdcouponschema.setId(IDs[n]);
			if (sdcouponschema.fill()) {
				// 删除状态为6
				sdcouponschema.setStatus("6");
				// 更改人和更改时间
				String now = DateUtil.getCurrentDateTime();
				sdcouponschema.setModifyDate(DateUtil.parseDateTime(now));
				sdcouponschema.setModifyUser(User.getUserName());
				trans.add(sdcouponschema, Transaction.UPDATE);
				// trans.add(sdcouponschema, Transaction.DELETE_AND_BACKUP);
			} else {
				Response.setLogInfo(1, "无效主键数据!");
				return;
			}
		}
		if (trans.commit()) {
			// for (int n = 0; n < IDs.length; n++) {
			// CacheManager.remove("Forum", "Forum", IDs[n]);
			// }
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}

	/**
	 * 优惠券发放
	 */
	public void provide() {

		// 优惠券主键ID
		String id = $V("IDs");
		if (id.indexOf(",") != -1) {
			Response.setLogInfo(0, "请选择一个要发放的优惠券,如果要选择发放多个优惠券,请使用批量上传发放功能！");
			return;
		}
		// 邮箱或电话
		String mailMobile = $V("mailMobile");
		// 根据邮箱或电话获取会员的注册方式
		QueryBuilder queryBuilder = new QueryBuilder(
				"select id,RegisterType,email,mobileNO,username from member where email = ? or mobileNO = ? ");
		queryBuilder.add(mailMobile);
		queryBuilder.add(mailMobile);

		DataTable dt = queryBuilder.executeDataTable();

		if (dt == null || dt.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("此用户没有注册");
			return;
		}

		//String registerType = dt.getString(0, "RegisterType");
		String mobileNO = dt.getString(0, "mobileNO");
		String username = dt.getString(0, "username");

		//发送顺序修改为 先微信 在手机号 最后邮箱
		// 邮箱注册
		/*if ("0".equals(registerType) && StringUtil.isNotEmpty(email)) {
			mailMobile = email;
			// 手机注册
		} else if ("1".equals(registerType) && StringUtil.isNotEmpty(mobileNO)) {
			mailMobile = mobileNO;
			// 绑定手机
		} else if ("2".equals(registerType) && StringUtil.isNotEmpty(mobileNO)) {
			mailMobile = mobileNO;
			// 被动注册的邮箱
		} else if ("2".equals(registerType) && StringUtil.isNotEmpty(email)) {
			mailMobile = email;
		}*/

		SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
		sdcouponschema.setId(id);
		// 查询
		if (!sdcouponschema.fill()) {
			Response.setStatus(0);
			Response.setMessage("无效优惠券!");
			return;
		}
		
		// memberid
		String memberid = dt.getString(0, "id");

		// 修改优惠券状态
		Map<String, Object> resultMap = updData(sdcouponschema, memberid, mobileNO);
		if (Constant.FAIL.equals(resultMap.get(Constant.STATUS))) {
			Response.setStatus(0);
			Response.setMessage((String) resultMap.get(Constant.MSG));
			return;
		}

		ActivityGiveDetail agd = new ActivityGiveDetail();

		// 优先顺序 用户名，输入的联系方式
		String name = username;
		if (StringUtil.isEmpty(name)) {
			name = mailMobile;
		}
		// 消息提醒
		Map<String, Object> remindMap = agd.couponRemind(sdcouponschema.getCouponSn(), memberid, name);

		if (Constant.FAIL.equals(remindMap.get(Constant.STATUS))) {
			Response.setStatus(0);
			Response.setMessage((String) remindMap.get(Constant.MSG));
		} else {
			Response.setStatus(1);
			Response.setMessage((String) remindMap.get(Constant.DATA));
		}
	}

	/**
	 *
	 * @Title: provideExcel
	 * @Description: (线下发放并导出)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public void provideExcel() {

		// 优惠券主键ID
		String id = $V("IDs");
		String batchsn = $V("batchsn");
		if (!StringUtil.checkID(id)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDss = id.split(",");
		List<String> list_ids = new ArrayList<String>();
		if (!StringUtil.isEmpty(batchsn)) {
			DataTable dt = new QueryBuilder("select id  from  couponinfo where batch=? and status='0' ", batchsn)
					.executeDataTable();
			if (dt.getRowCount() < 1) {
				Response.setStatus(0);
				Response.setMessage("该批次下没有未使用状态的优惠券!");
				return;
			}
			for (int i = 0; i < dt.getRowCount(); i++) {
				list_ids.add(dt.getString(i, 0));
			}
		} else {
			Collections.addAll(list_ids, IDss);

		}
		Transaction trans = new Transaction();
		for (int n = 0; n < list_ids.size(); n++) {
			SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
			sdcouponschema.setId(list_ids.get(n));
			// 查询
			if (sdcouponschema.fill()) {
				if (!("0".equals(sdcouponschema.getStatus()))) {
					Response.setStatus(0);
					Response.setMessage("只有状态为\"未使用\"的优惠券才可以发放!");
					return;
				}
				// 只有优惠券发放人包括自己的才可以发放
				if (sdcouponschema.getProvideUser().indexOf(User.getUserName()) == -1) {
					Response.setStatus(0);
					Response.setMessage("只有优惠券发放人中包含自己的才可以发放!");
					return;
				}
				// 关联活动的优惠券不可以发放
				if (!("".equals(sdcouponschema.getActivitySn()) || sdcouponschema.getActivitySn() == null)) {
					Response.setStatus(0);
					Response.setMessage("关联活动的优惠券不可以发放!");
					return;
				}
				// 发放状态为2
				sdcouponschema.setStatus("2");
				// 实际发放人
				sdcouponschema.setRealProvideUser(User.getUserName());
				// 更改人和更改时间
				String now = DateUtil.getCurrentDateTime();
				sdcouponschema.setModifyDate(DateUtil.parseDateTime(now));
				sdcouponschema.setModifyUser(User.getUserName());
				// 发放时间
				sdcouponschema.setProp2(now);
				trans.add(sdcouponschema, Transaction.UPDATE);
			} else {
				Response.setLogInfo(0, "无效主键数据!");
				return;
			}
		}
		// 提交事务
		if (trans.commit()) {
			Response.setLogInfo(1, "线下发放成功!");
			return;
		} else {
			Response.setLogInfo(0, "操作失败!");
			return;
		}
	}

	public void provideExcelVali() {

		String batchsn = $V("batchsn");
		if (!StringUtil.isEmpty(batchsn)) {
			DataTable dt = new QueryBuilder("select id  from  couponinfo where batch=? and status='0' ", batchsn)
					.executeDataTable();
			if (dt.getRowCount() < 1) {
				Response.setStatus(0);
				Response.setMessage("该批次下没有未使用状态的优惠券!");
				return;
			}
		}
	}

	/**
	 * 优惠券冻结
	 */
	public void frozen() {

		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
			sdcouponschema.setId(IDs[n]);
			if (sdcouponschema.fill()) {
				String status = sdcouponschema.getStatus();
				if (!(status.equals("0"))) {
					Response.setLogInfo(0, "只有未使用状态的优惠券才可以冻结!");
					return;
				}
				// 冻结状态为4
				sdcouponschema.setStatus("4");
				// 更改人和更改时间
				String now = DateUtil.getCurrentDateTime();
				sdcouponschema.setModifyDate(DateUtil.parseDateTime(now));
				sdcouponschema.setModifyUser(User.getUserName());
				trans.add(sdcouponschema, Transaction.UPDATE);
				// trans.add(sdcouponschema, Transaction.DELETE_AND_BACKUP);

			} else {
				Response.setLogInfo(1, "无效主键数据!");
				return;
			}
		}
		if (trans.commit()) {
			// for (int n = 0; n < IDs.length; n++) {
			// CacheManager.remove("Forum", "Forum", IDs[n]);
			// }
			Response.setLogInfo(1, "冻结成功!");
			return;
		} else {
			Response.setLogInfo(0, "冻结失败!");
			return;
		}
	}

	/**
	 * 优惠券解冻
	 */
	public void unFrozen() {

		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
			sdcouponschema.setId(IDs[n]);
			if (sdcouponschema.fill()) {
				String status = sdcouponschema.getStatus();
				if (!(status.equals("4"))) {
					Response.setLogInfo(0, "只有冻结状态的优惠券才可以解冻!");
					return;
				}
				String endtime = String.valueOf(sdcouponschema.getEndTime());
				endtime.substring(0, endtime.length() - 2);
				// 更改人和更改时间
				String now = DateUtil.getCurrentDateTime();
				sdcouponschema.setModifyDate(DateUtil.parseDateTime(now));
				sdcouponschema.setModifyUser(User.getUserName());
				if (DateUtil.compare(endtime, now, "yyyy-MM-dd HH:mm:ss") < 0) {
					// 过期状态为5
					sdcouponschema.setStatus("5");
				} else {
					// 解冻状态为0未使用
					sdcouponschema.setStatus("0");
				}
				trans.add(sdcouponschema, Transaction.UPDATE);
				// trans.add(sdcouponschema, Transaction.DELETE_AND_BACKUP);
			} else {
				Response.setLogInfo(1, "无效主键数据!");
				return;
			}
		}
		if (trans.commit()) {
			// for (int n = 0; n < IDs.length; n++) {
			// CacheManager.remove("Forum", "Forum", IDs[n]);
			// }
			Response.setLogInfo(1, "解冻成功!");
			return;
		} else {
			Response.setLogInfo(0, "解冻失败!");
			return;
		}
	}

	@SuppressWarnings("unchecked")
	public static void dg1DataBindForApprove(DataGridAction dga) {

		dga.getParams().put("isApply", "1");
		dg1DataBind(dga);
	}

	/**
	 * 优惠券初始化查询
	 *
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String couponSn = (String) dga.getParams().get("couponSn");
		String orderSn = (String) dga.getParams().get("orderSn");
		String status = (String) dga.getParams().get("status");
		String payStartDate = (String) dga.getParams().get("payStartDate");
		String payStartTime = (String) dga.getParams().get("payStartTime");
		String payEndDate = (String) dga.getParams().get("payEndDate");
		String payEndTime = (String) dga.getParams().get("payEndTime");
		String createStartDate = (String) dga.getParams().get("createStartDate");
		String createStartTime = (String) dga.getParams().get("createStartTime");
		String createEndDate = (String) dga.getParams().get("createEndDate");
		String createEndTime = (String) dga.getParams().get("createEndTime");
		String batch = (String) dga.getParams().get("batch");

		String createuserSearch = (String) dga.getParams().get("createuserSearch");
		String provideUserSearch = (String) dga.getParams().get("provideUserSearch");
		String realProvideUserSearch = (String) dga.getParams().get("realProvideUserSearch");
		String riskcodeSearch = (String) dga.getParams().get("riskcodeSearch");
		String insuranceCompanySearch = (String) dga.getParams().get("insuranceCompanySearch");
		String directionSearch = (String) dga.getParams().get("directionSearch");
		String mobile = (String) dga.getParams().get("mobile");
		String email = (String) dga.getParams().get("email");
		String batchflag = (String) dga.getParams().get("batchflag");
		// 优惠券类型
		String prop3 = (String) dga.getParams().get("prop3");
		String issuechannel = (String) dga.getParams().get("issuechannel");// 发放渠道
		String channelInfo = (String) dga.getParams().get("channel");// 应用渠道

		String isApply = (String) dga.getParams().get("isApply");// 是否是加载申请优惠券列表

		QueryBuilder qb = new QueryBuilder(
				"select a.*,d.username,'' as couponType, '无限制' as productName,b.approveStatus from CouponInfo a left join member d on a.memberId = d.id LEFT JOIN  zdcode z ON cast(z.codevalue as SIGNED INTEGER)=a.status ");
		qb.append(" LEFT JOIN CouponApproveInfo b on a.batch = b.couponBatch ");
		qb.append(" where z.codetype='coupon.status' and z.ParentCode = 'coupon.status' and 1=1 and a.status !=6 ");
		if ("1".equals(isApply)) {
			qb.append(" and b.approveStatus = '0' ");
		} else {
			qb.append(" and (b.approveStatus = '1' or b.approveStatus is null) ");
		}
		// 申请人查询
		if (StringUtil.isNotEmpty(createuserSearch)) {
			QueryBuilder querybuilder = new QueryBuilder("SELECT username FROM zduser WHERE realname LIKE '%"
					+ createuserSearch + "%'");
			if (querybuilder.executeDataTable().getRowCount() != 0) {
				qb.append(" and a.createUser like " + "'%" + querybuilder.executeString().trim() + "%'");
			}
		}
		// 发放人查询
		if (StringUtil.isNotEmpty(provideUserSearch)) {
			QueryBuilder querybuilder = new QueryBuilder("SELECT username FROM zduser WHERE realname LIKE '%"
					+ provideUserSearch + "%'");
			if (querybuilder.executeDataTable().getRowCount() != 0) {
				qb.append(" and provideUser like " + "'%" + querybuilder.executeString().trim() + "%'");
			}
		}
		// 真正发放人查询
		if (StringUtil.isNotEmpty(realProvideUserSearch)) {
			QueryBuilder querybuilder = new QueryBuilder("SELECT username FROM zduser WHERE realname LIKE '%"
					+ realProvideUserSearch + "%'");
			if (querybuilder.executeDataTable().getRowCount() != 0) {
				qb.append(" and realProvideUser like " + "'%" + querybuilder.executeString().trim() + "%'");
			}
		}
		// 险种查询
		if (StringUtil.isNotEmpty(riskcodeSearch)) {
			qb.append(" and riskCode like " + "'%" + riskcodeSearch + "%'");
		}
		// 优惠公司查询
		if (StringUtil.isNotEmpty(insuranceCompanySearch)) {
			qb.append(" and insuranceCompany like " + "'%" + insuranceCompanySearch + "%'");
		}
		// 使用说明查询
		if (StringUtil.isNotEmpty(directionSearch)) {
			qb.append(" and direction like " + "'%" + directionSearch.trim() + "%'");
		}
		// 优惠券号查询
		if (StringUtil.isNotEmpty(couponSn)) {
			qb.append(" and couponSn like " + "'%" + couponSn.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" and orderSn like " + "'%" + orderSn.trim() + "%'");
		}
		// 所属手机查询
		if (StringUtil.isNotEmpty(mobile)) {
			qb.append(" and mobile like " + "'%" + mobile.trim() + "%'");
		}
		// 所属邮箱查询
		if (StringUtil.isNotEmpty(email)) {
			qb.append(" and mail like " + "'%" + email.trim() + "%'");
		}
		// 优惠券批次查询
		if (StringUtil.isNotEmpty(batch)) {
			qb.append(" and batch ='" + batch.trim() + "'");
		}
		// 状态查询
		if (StringUtil.isNotEmpty(status)) {
			qb.append(" and a.status = " + status.trim());
		}
		// 优惠券类型查询
		if (StringUtil.isNotEmpty(prop3)) {
			qb.append(" and a.prop3 ='" + prop3.trim() + "'");
		}
		// 发放渠道查询
		if (StringUtil.isNotEmpty(issuechannel)) {
			qb.append(" and FIND_IN_SET('" + issuechannel + "',a.issuechannel)");
		}
		// 应用渠道查询
		if (StringUtil.isNotEmpty(channelInfo)) {
			qb.append(" and channelsn like '%" + channelInfo + "%'");
		}
		// 使用开始时间查询
		if (StringUtil.isNotEmpty(payStartDate)) {
			if (StringUtil.isEmpty(payStartTime)) {
				payStartTime = "00:00:00";
			} else {
				if (payStartTime.length() == 7) {
					payStartTime = "0" + payStartTime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.payTime,'%Y-%m-%d %H:%i:%s')) >=  UNIX_TIMESTAMP( '"
					+ payStartDate.trim() + " " + payStartTime.trim() + "')");
		}
		// 使用结束时间
		if (StringUtil.isNotEmpty(payEndDate)) {
			if (StringUtil.isEmpty(payEndTime)) {
				payEndTime = "23:59:59";
			} else {
				if (payStartTime.length() == 7) {
					payEndTime = "0" + payEndTime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.payTime,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP('"
					+ payEndDate.trim() + " " + payEndTime.trim() + "')");
		}
		// 申请开始时间(默认为昨天)
		if (StringUtil.isNotEmpty(createStartDate)) {
			if (StringUtil.isEmpty(createStartTime)) {
				createStartTime = "00:00:00";
			} else {
				if (createStartTime.length() == 7) {
					createStartTime = "0" + createStartTime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(" + "'"
					+ createStartDate.trim() + " " + createStartTime.trim() + "')");
		} else {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(" + "'"
					+ yesterday + " " + "00:00:00" + "')");
		}
		// 申请结束时间(默认为当天)
		if (StringUtil.isNotEmpty(createEndDate)) {
			if (StringUtil.isEmpty(createEndTime)) {
				createEndTime = "23:59:59";
			} else {
				if (payStartTime.length() == 7) {
					createEndTime = "0" + createEndTime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(" + "'"
					+ createEndDate.trim() + " " + createEndTime.trim() + "')");
		} else {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(" + "'" + today
					+ " " + "23:59:59" + "')");
		}
		// 批次查询
		if (StringUtil.isNotEmpty(batchflag)) {
			qb.append("  GROUP BY a.batch  ");
		}
		qb.append(" order by batch desc ");
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "couponSn", dt.getString(i, "couponSn"));
			String[] riskCodeArr = dt.getString(i, "riskCode").split(",");
			StringBuffer riskCodeSb = new StringBuffer();
			for (int j = 0; j < riskCodeArr.length; j++) {
				QueryBuilder qb_riskCode = new QueryBuilder(
						"select CodeName from fdcode where CodeType = 'erisksubtype' and Code = ? ");
				qb_riskCode.add(riskCodeArr[j]);
				riskCodeSb.append(qb_riskCode.executeString());
				if (j != riskCodeArr.length - 1) {
					riskCodeSb.append(",");
				}
			}
			if (riskCodeSb.length() == 0 || "null".equals(String.valueOf(riskCodeSb))) {
				dt.set(i, "riskCode", "无限制");
			} else {
				dt.set(i, "riskCode", riskCodeSb);
			}
			String[] insuranceCompanyArr = dt.getString(i, "insuranceCompany").split(",");
			StringBuffer insuranceCompanySb = new StringBuffer();
			for (int j = 0; j < insuranceCompanyArr.length; j++) {
				QueryBuilder qb_insuranceCompany = new QueryBuilder(
						"select codename from zdCode where CodeType = 'SupplierCode' and CodeValue = ? ");
				qb_insuranceCompany.add(insuranceCompanyArr[j]);
				insuranceCompanySb.append(qb_insuranceCompany.executeString());
				if (j != insuranceCompanyArr.length - 1) {
					insuranceCompanySb.append(",");
				}
			}
			if (insuranceCompanySb.length() == 0 || "null".equals(String.valueOf(insuranceCompanySb))) {
				dt.set(i, "insuranceCompany", "无限制");
			} else {
				dt.set(i, "insuranceCompany", insuranceCompanySb);
			}

			String product = dt.getString(i, "product");
			if (StringUtil.isNotEmpty(product)) {
				String productName = new QueryBuilder(
						"SELECT GROUP_CONCAT(ProductName) FROM sdproduct WHERE productid IN ('"
								+ product.replace(",", "','") + "')").executeString();
				if (StringUtil.isNotEmpty(productName)) {
					dt.set(i, "productName", productName);
				}
			}

			dt.set(i, "parValue", dt.getString(i, "parValue"));

			if (dt.getString(i, "payAmount").length() == 0 || "null".equals(String.valueOf(dt.getString(i, "payAmount")))) {
				dt.set(i, "payAmount", "无限制");
			} else {
				dt.set(i, "payAmount", dt.getString(i, "payAmount"));
			}
			if (dt.getString(i, "useTimes").length() == 0 || "null".equals(String.valueOf(dt.getString(i, "useTimes")))) {
				dt.set(i, "useTimes", "无限制");
			} else {
				dt.set(i, "useTimes", dt.getString(i, "useTimes"));
			}
			dt.set(i, "direction", dt.getString(i, "direction"));
			if (dt.getString(i, "startTime").length() == 0 || "null".equals(String.valueOf(dt.getString(i, "startTime")))) {
				dt.set(i, "startTime", "无限制");
			} else {
				dt.set(i, "startTime", dt.getString(i, "startTime").substring(0, 11));
			}
			if (dt.getString(i, "endTime").length() == 0 || "null".equals(String.valueOf(dt.getString(i, "endTime")))) {
				dt.set(i, "endTime", "无限制");
			} else {
				dt.set(i, "endTime", dt.getString(i, "endTime").substring(0, 11));
			}
			QueryBuilder qb_status = new QueryBuilder(
					"select CodeName from zdCode where CodeType = 'Coupon.status' and CodeValue = ? ");
			qb_status.add(dt.getString(i, "status"));
			dt.set(i, "status", qb_status.executeString());
			dt.set(i, "orderSn", dt.getString(i, "orderSn"));
			dt.set(i, "payTime", dt.getString(i, "payTime"));
			dt.set(i, "memberId", dt.getString(i, "memberId"));
			dt.set(i, "mail", dt.getString(i, "mail"));
			dt.set(i, "mobile", dt.getString(i, "mobile"));
			QueryBuilder qb_purpose = new QueryBuilder(
					"select CodeName from zdCode where CodeType = 'Coupon.purpose' and CodeValue = ? ");
			qb_purpose.add(dt.getString(i, "purpose"));
			dt.set(i, "purpose", qb_purpose.executeString());
			dt.set(i, "activitysn", dt.getString(i, "activitysn"));
			// 申请人
			QueryBuilder qb_createUser = new QueryBuilder("select realname from zduser where username =?");
			qb_createUser.add(dt.getString(i, "createUser"));
			dt.set(i, "createUser", qb_createUser.executeDataTable().getString(0, 0));

			dt.set(i, "createDate", dt.getString(i, "createDate"));
			// 优惠券类型
			dt.set(i,
					"couponType",
					new QueryBuilder("select CodeName from zdCode where CodeType = 'Coupon.type' and CodeValue = ? ", dt
							.getString(i, "prop3")).executeString());
			// 用户名处理
			String username[] = dt.getString(i, "provideUser").split(",");
			String provideUser = "";
			for (int j = 0; j < username.length; j++) {
				QueryBuilder qb_username = new QueryBuilder("select realname from zduser where username =?");
				qb_username.add(username[j]);
				if (qb_username.executeDataTable().getRowCount() > 0) {
					provideUser += qb_username.executeDataTable().getString(0, 0) + ",";
				}
			}
			if (provideUser.equals("") || provideUser == null) {
				dt.set(i, "provideUser", "");
			} else {
				dt.set(i, "provideUser", provideUser.substring(0, provideUser.length() - 1));
			}
			// 实际发放人
			QueryBuilder qb_realProvideUser = new QueryBuilder("select realname from zduser where username =?");
			qb_realProvideUser.add(dt.getString(i, "realProvideUser"));
			if (dt.getString(i, "realProvideUser") != "") {
				dt.set(i, "realProvideUser", qb_realProvideUser.executeDataTable().getString(0, 0));
			} else {
				dt.set(i, "realProvideUser", "");
			}

		}
		for (DataColumn dc : dt.getDataColumns()) {
			if ("STATUS".equalsIgnoreCase(dc.getColumnName())) {
				dc.setColumnType(DataColumn.STRING);
				break;
			}
		}
		dga.bindData(dt);
	}

	/**
	 * 初始化发放人
	 * 
	 * @param dga
	 */
	public static void dg1DataBindProvideUser(DataGridAction dga) {

		String realName = (String) dga.getParams().get("realName");
		QueryBuilder qb = new QueryBuilder("Select userName as id,realName from ZDUser where 1=1");
		if (StringUtil.isNotEmpty(realName)) {
			qb.append(" and realName like " + "'%" + realName.trim() + "%'");
		}
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "realName", dt.getString(i, "realName"));
		}
		dga.bindData(dt);
	}

	/**
	 * 初始化险种
	 * 
	 * @param dga
	 */
	public static void dg1DataBindRiskCode(DataGridAction dga) {

		String codename = (String) dga.getParams().get("codename");
		QueryBuilder qb_riskCode = new QueryBuilder(
				"SELECT code as id ,codename FROM fdcode WHERE codetype='erisksubtype' AND CODE NOT LIKE '%00' ");
		if (StringUtil.isNotEmpty(codename)) {
			qb_riskCode.append(" and codename like " + "'%" + codename.trim() + "%'");
		}
		qb_riskCode.append(dga.getSortString());
		dga.setTotal(qb_riskCode);
		DataTable dt = qb_riskCode.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "codename", dt.getString(i, "codename"));
		}
		dga.bindData(dt);
	}

	/**
	 * 初始化保险公司
	 * 
	 * @param dga
	 */
	public static void dg1DataBindInsuranceCompany(DataGridAction dga) {

		String codename = (String) dga.getParams().get("codename");
		QueryBuilder qb_insuranceCompany = new QueryBuilder(
				"SELECT codevalue as id,codename FROM zdcode WHERE codetype='SupplierCode' AND codevalue NOT LIKE '%sys%' ");
		if (StringUtil.isNotEmpty(codename)) {
			qb_insuranceCompany.append(" and codename like " + "'%" + codename.trim() + "%'");
		}
		qb_insuranceCompany.append(dga.getSortString());
		dga.setTotal(qb_insuranceCompany);
		DataTable dt = qb_insuranceCompany.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "codename", dt.getString(i, "codename"));
		}
		dga.bindData(dt);
	}

	/**
	 * 判断电话是否为会员
	 * 
	 * @param mobileNo
	 * @return
	 */
	public String isExistByMobileNO(String mobileNo) {

		String sql = "select id from member where  mobileNO='" + mobileNo + "' or username ='" + mobileNo + "'";
		QueryBuilder querybuilder = new QueryBuilder(sql);
		if (querybuilder.executeDataTable().getRowCount() == 0) {
			return "false";
		} else {
			return querybuilder.executeDataTable().get(0, 0).toString();
		}
	}

	/**
	 * 判断邮箱是否为会员
	 * 
	 * @param mailbox
	 * @return
	 */
	public String isExistByMailbox(String mailbox) {

		String sql = "select id from member where  email = '" + mailbox + "' or username ='" + mailbox + "'";
		QueryBuilder querybuilder = new QueryBuilder(sql);
		if (querybuilder.executeDataTable().getRowCount() == 0) {
			return "false";
		} else {
			return querybuilder.executeDataTable().get(0, 0).toString();
		}
	}

	// 获取动作信息
	private SDInterActionSet check(String actionId) {

		SDInterActionSchema tSDInterActionSchema = new SDInterActionSchema();
		SDInterActionSet tSDInterActionSet = tSDInterActionSchema.query(new QueryBuilder("where ActionId ='" + actionId
				+ "'"));
		return tSDInterActionSet;
	}

	/**
	 * 异步优惠券发放批量导入
	 */
	public void asyncUploadExcel() {

		LongTimeTask ltt = new LongTimeTask() {

			String currUser = User.getUserName();// 当前用户

			public void execute() {

				uploadExcel(this, currUser);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始导入数据文件......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	// 优惠券发放批量导入
	public void uploadExcel(LongTimeTask task, String currUser) {

		try {

			String fileaddress = $V("fileaddress");
			FileInputStream finput = null;
			try {
				finput = new FileInputStream(fileaddress);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
				task.addError("导入失败！请选择正确模板");
				return;
			}

			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(finput);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				task.addError("导入失败！请选择正确模板");
				return;
			}
			HSSFWorkbook wb = null;
			try {
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				task.addError("导入失败！请选择正确模板");
				return;
			}
			HSSFSheet sheet = wb.getSheetAt(0);
			try {
				finput.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				task.addError("导入失败！请选择正确模板");
				return;
			}
			// 最大行数
			int maxRow = sheet.getLastRowNum();
			if (maxRow - 9 <= 0) {
				task.addError("导入失败！导入的文件中不包含有效数据！");
				return;
			}
			int num = maxRow;
			// 剔除无数据的空格
			for (int i = 10; i < maxRow + 1; i++) {
				HSSFRow row = sheet.getRow(i);
				if ((row.getCell(1) == null) & (row.getCell(2) == null)) {
					num--;
				}
			}
			// 查询未使用的优惠券总数
			int total = new QueryBuilder("select count(id) from CouponInfo where status ='0' and FIND_IN_SET('" + currUser
					+ "',provideuser)!=0").executeInt();
			if (num - 9 > total) {
				task.addError("导入的人数大于权限范围内可发放的优惠券总数！");
				return;
			}
			// 格式化号码
			DecimalFormat df = new DecimalFormat("0");
			// 检查是否有空
			for (int j = 10; j < maxRow + 1; j++) {
				// 终止任务
				if (task.checkStop()) {
					return;
				}
				task.setCurrentInfo("正在校验excel第(" + (j + 1) + "/" + (maxRow + 1) + ")行数据......");
				task.setPercent(Integer
						.valueOf((j - 9) * 100 / (maxRow - 9)));
				// 获取行数据
				HSSFRow row = sheet.getRow(j);
				// 优惠券号
				HSSFCell cel_2 = row.getCell(1);
				String celval_2 = "";
				// 电话或邮箱
				HSSFCell cel_3 = row.getCell(2);
				String celval_3 = "";
				if ((cel_2 == null) || (cel_3 == null)) {
					task.addError("第" + (j + 1) + "行有空数据，请修改后重新上传！");
					return;
				}
				if (row.getCell(1).getCellType() == 1) {
					celval_2 = row.getCell(1).getStringCellValue().trim();
				} else {
					task.addError("第" + (j + 1) + "行优惠券号为非文本类型！");
					return;
				}

				// 判断是邮箱或者是电话，格式化防止科学计数法
				if (cel_3.toString().indexOf("@") != -1) {
					if (row.getCell(2).getCellType() == 1) {
						celval_3 = row.getCell(2).getStringCellValue().trim();
					} else {
						task.addError("第" + (j + 1) + "行邮箱为非文本类型！");
						return;
					}
				} else {
					if (row.getCell(2).getCellType() == 0) {
						celval_3 = df.format(row.getCell(2).getNumericCellValue()).trim();
					} else if (row.getCell(2).getCellType() == 1) {
						celval_3 = row.getCell(2).getStringCellValue().trim();
					} else {
						task.addError("第" + (j + 1) + "行邮箱或电话为非文本类型和数字类型！");
						return;
					}
				}
				if (((cel_2 == null) || (celval_2.equals(""))) && ((cel_3 == null) || (celval_3.equals("")))) {
					task.addError("第" + (j + 1) + "行有空数据，请修改后重新上传！");
					return;
				}
				// 非法字符串检查
				String regex = "script|iframe";
				Pattern p = Pattern.compile(regex);
				Matcher m2 = p.matcher(celval_2.toLowerCase());
				Matcher m3 = p.matcher(celval_3.toLowerCase());
				if (m2.find() || m3.find()) {
					task.addError("第" + (j + 1) + "行有非法字符,请重新导入");
					return;
				}
				// 判断邮箱或电话合法性
				if (StringUtil.isMail(celval_3)) {
					logger.info("第{}行为邮箱", (j + 1));
				} else if (StringUtil.isMobileNO(celval_3)) {
					logger.info("第{}行为电话", (j + 1));
				} else {
					task.addError("第" + (j + 1) + "行邮箱或电话不合法，请修改后重新上传！");
					return;
				}
				// 判断是否有重复数据
				for (int k = j + 1; k < maxRow; k++) {
					HSSFRow row_next = sheet.getRow(k);
					if ((row_next.getCell(1) == null)) {
						continue;
					}
					// 优惠券号
					String celval_2_next = "";
					if (row_next.getCell(1).getCellType() == 1) {
						celval_2_next = row_next.getCell(1).getStringCellValue().trim();
					} else {
						task.addError("第" + (j + 1) + "行优惠券号为非文本类型！");
						return;
					}
					if (celval_2_next.equals(celval_2)) {
						task.addError("第" + (j + 1) + "行与第" + (k + 1) + "行优惠券号重复,请重新导入");
						return;
					}
				}
				QueryBuilder querybuilder = new QueryBuilder(
						"select a.id,a.purpose,a.status,b.approveStatus from CouponInfo a "
								+ "left join CouponApproveInfo b on a.batch = b.couponBatch where a.couponSn= '"
								+ String.valueOf(celval_2) + "'");
				DataTable dt = querybuilder.executeDataTable();
				if (dt.getRowCount() == 0) {
					task.addError("第" + (j + 1) + "行优惠券号不存在！");
					return;
				} else {
					String purpose = dt.getString(0, 1);
					if ("1".equals(purpose)) {
						task.addError("第" + (j + 1) + "行优惠券用途为活动发放，而非手动发放！");
						return;
					}
					String status = dt.getString(0, 2);
					if (!("0".equals(status))) {
						task.addError("第" + (j + 1) + "行优惠券状态非“未使用”！");
						return;
					}
					String approveStatus = dt.getString(0, 3);
					if ("0".equals(approveStatus) || "2".equals(approveStatus)) {
						task.addError("第" + (j + 1) + "行优惠券未通过审批，不能发放!");
						return;
					}
				}
			}
			task.setPercent(100);
			// 更新数据
			Transaction trasaction = new Transaction();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (int j = 10; j < maxRow + 1; j++) {
				// 终止任务
				if (task.checkStop()) {
					return;
				}
				// 获取行数据
				HSSFRow row = sheet.getRow(j);
				// 优惠券号
				String celval_2 = row.getCell(1).getStringCellValue().trim();
				// begin 加入导入进度
				task.setCurrentInfo("正在发放(" + (j - 9) + "/" + (maxRow - 9) + ")：券"
						+ celval_2);
				task.setPercent(Integer
						.valueOf((j - 9) * 100 / (maxRow - 9)));
				// end 加入导入进度
				HSSFCell cel_3 = row.getCell(2);
				// 电话或邮箱
				String celval_3 = "";
				// 判断是邮箱或者是电话，格式化防止科学计数法
				if (cel_3.toString().indexOf("@") != -1) {
					celval_3 = row.getCell(2).getStringCellValue().trim();
				} else {
					if (row.getCell(2).getCellType() == 0) {
						celval_3 = df.format(row.getCell(2).getNumericCellValue()).trim();
					} else if (row.getCell(2).getCellType() == 1) {
						celval_3 = row.getCell(2).getStringCellValue().trim();
					} else {
						task.addError("第" + (j + 1) + "行电话为非文本类型和数字类型！");
						return;
					}
				}
				StringBuffer sql_update = new StringBuffer("update couponinfo set status='2',realprovideuser=?");
				if (StringUtil.isMail(celval_3)) {
					sql_update.append(",mail=?");
					String memberid = isExistByMailbox(celval_3);
					if (!(memberid.equals("false"))) {
						sql_update.append(",memberid='" + memberid + "'");
					}
				} else if (StringUtil.isMobileNO(celval_3)) {
					sql_update.append(",mobile=?");
					String memberid = isExistByMobileNO(celval_3);
					if (!(memberid.equals("false"))) {
						sql_update.append(",memberid='" + memberid + "'");
					}
				}
				// 更改人和更改时间
				String now = DateUtil.getCurrentDateTime();
				sql_update.append(" ,modifydate = '" + now + "',prop2='" + now + "' ,modifyuser = '" + currUser + "' ");
				QueryBuilder querybuilder = new QueryBuilder(sql_update.toString(), currUser, celval_3);
				querybuilder.append(" where couponSn=?", celval_2);
				trasaction.add(querybuilder);
				Map<String, String> map = new HashMap<String, String>();
				map.put("couponsn", celval_2);
				map.put("mailMobile", celval_3);
				list.add(map);
			}
			task.setPercent(100);
			if (trasaction.commit()) {
				for (int i = 0; i < list.size(); i++) {
					// 终止任务
					if (task.checkStop()) {
						return;
					}
					task.setCurrentInfo("正在发送优惠券发放通知信息(" + (i + 1) + "/" + (list.size()) + ")");
					task.setPercent(Integer
							.valueOf((i + 1) * 100 / (list.size())));
					
					// 提醒顺序为 微信模板消息提醒  - 手机短信提醒 - 邮箱提醒。只提醒一次+++++++++++++++++

					String mailMobile = String.valueOf(list.get(i).get("mailMobile"));
					QueryBuilder queryBuilder = new QueryBuilder(
							"select id,RegisterType,email,mobileNO,username from member where email = ? or mobileNO = ? ");
					queryBuilder.add(mailMobile);
					queryBuilder.add(mailMobile);

					DataTable dt = queryBuilder.executeDataTable();
					if (dt == null || dt.getRowCount() == 0) {
						continue;
					}
					String username = dt.getString(0, "username");
					
					// memberid
					String memberid = dt.getString(0, "id");

					ActivityGiveDetail agd = new ActivityGiveDetail();

					// 优先顺序 用户名，输入的联系方式
					String name = username;
					if (StringUtil.isEmpty(name)) {
						name = mailMobile;
					}
					// 消息提醒
					Map<String, Object> remindMap = agd.couponRemind(list.get(i).get("couponsn"), memberid, name);

					if (Constant.FAIL.equals(remindMap.get(Constant.STATUS))) {
						task.setCurrentInfo((String) remindMap.get(Constant.MSG) + list.get(i).get("mailMobile"));
					}
				}
				task.setPercent(100);
				logger.info("共发放了{}张优惠券！", String.valueOf(maxRow - 9));
			}
		} catch (Exception e) {
			logger.error("导入异常！请选择正确模板！" + e.getMessage(), e);
			task.addError("导入异常！请选择正确模板！");
		}
	}

	/**
	 * 查询设置用户优惠券折扣权限
	 * 
	 * @param dga
	 */
	public static void dgPermDataBind(DataGridAction dga) {

		String name = (String) dga.getParams().get("name");
		String sql = "select CodeValue, CodeName, Memo, AddTime, AddUser, ModifyTime,"
				+ " ModifyUser from zdcode where CodeType = 'CouponVar.Permission'"
				+ " and ParentCode = 'CouponVar.Permission' ";
		if (StringUtil.isNotEmpty(sql)) {
			sql += "and CodeValue like '%" + name + "%'";
		}
		sql += " order by CodeOrder ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 增加用户优惠券折扣权限
	 */
	public void addCouponPerm() {

		// 用户名
		String username = Request.getString("username");
		// 折扣
		String couponVarPerm = Request.getString("couponVarPerm");
		// 备注
		String memo = Request.getString("memo");
		// 校验用户是否存在
		String sql = "select count(1) from zduser where username = ?";
		QueryBuilder querybuilder = new QueryBuilder(sql, username);
		if (querybuilder.executeInt() == 0) {
			Response.setStatus(0);
			Response.setMessage("用户名不存在！");
			return;
		}

		// 校验用户是否已经设置折扣权限
		sql = "select count(1) from zdcode where CodeType = 'CouponVar.Permission' "
				+ "and ParentCode = 'CouponVar.Permission' and CodeValue = ?";
		querybuilder = new QueryBuilder(sql, username);
		if (querybuilder.executeInt() > 0) {
			Response.setStatus(0);
			Response.setMessage("用户" + username + "的折扣已经设置了！");
			return;
		}

		// 增加数据
		ZDCodeSchema zdCodeSchema = new ZDCodeSchema();
		zdCodeSchema.setCodeType("CouponVar.Permission");
		zdCodeSchema.setParentCode("CouponVar.Permission");
		zdCodeSchema.setCodeValue(username);
		zdCodeSchema.setCodeName(couponVarPerm);
		zdCodeSchema.setCodeOrder(System.currentTimeMillis());
		zdCodeSchema.setMemo(memo);
		zdCodeSchema.setAddTime(new Date());
		zdCodeSchema.setAddUser(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(zdCodeSchema, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("添加成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("提交发生错误！");
		}
	}

	/**
	 * 修改用户优惠券折扣权限
	 */
	public void dg1Edit() {

		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		String sql = "";
		// 优惠券折扣
		String codeName = "";
		for (int i = 0; i < dt.getRowCount(); i++) {
			codeName = dt.getString(i, "CodeName");
			if (StringUtil.isEmpty(codeName)) {
				Response.setStatus(0);
				Response.setMessage("优惠券折扣不能为空！");
				return;
			}
			codeName = codeName.trim();
			String num = codeName.substring(0, codeName.length() - 1);
			if (!codeName.endsWith("%") || !NumberUtil.isNumber(num)) {
				Response.setStatus(0);
				Response.setMessage("优惠券折扣请填百分数（例：60%）！");
				return;
			}

			if (Double.valueOf(num) > 100) {
				Response.setStatus(0);
				Response.setMessage("优惠券折扣上限100%！");
				return;
			}

			sql = "update ZDCode set CodeName=?, Memo=?, ModifyTime=?, "
					+ "ModifyUser=? where CodeType='CouponVar.Permission' "
					+ "and ParentCode='CouponVar.Permission' and CodeValue=? ";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(dt.getString(i, "CodeName"));
			qb.add(dt.getString(i, "Memo"));
			qb.add(DateUtil.getCurrentDateTime());
			qb.add(User.getUserName());
			qb.add(dt.getString(i, "CodeValue"));
			trans.add(qb);
		}
		if (trans.commit()) {
			// 更新缓存信息
			for (int i = 0; i < dt.getRowCount(); i++) {
				CacheManager.set("Code", dt.getString(i, "CodeType"),
						dt.getString(i, "CodeValue"),
						dt.getString(i, "CodeName"));
			}
			Response.setStatus(1);
			Response.setMessage("修改成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("修改失败！");
		}
	}

	/**
	 * 删除用户优惠券折扣权限
	 */
	public void del() {

		DataTable dt = (DataTable) Request.get("DT");
		ZDCodeSet set = new ZDCodeSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setValue(dt.getDataRow(i));
			code.setCodeType("CouponVar.Permission");
			code.setParentCode("CouponVar.Permission");
			code.fill();
			set.add(code);
		}
		// 日志
		StringBuffer codeLog = new StringBuffer("删除代码:");
		if (set.size() > 0) {
			codeLog.append(set.get(0).getCodeName() + " 等");
		}
		// 备份
		if (set.deleteAndBackup()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, codeLog + "成功",
					Request.getClientIP());
			// 删除缓存里相关信息
			for (int i = 0; i < set.size(); i++) {
				CacheManager.remove("Code", set.get(i).getCodeType(), set
						.get(i).getCodeValue());
			}
			Response.setLogInfo(1, "删除成功!");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, codeLog + "失败",
					Request.getClientIP());
			Response.setLogInfo(0, "删除失败!");
		}
	}

	public void checkApplyEmail() {

		String applyUser = $V("applyUser");
		QueryBuilder qb = new QueryBuilder("select email from zduser where username = ?", applyUser);
		if (StringUtil.isEmpty(qb.executeString())) {
			Response.setLogInfo(0, "所选申请人邮箱不能为空");
		} else {
			Response.setLogInfo(1, "所选申请人邮箱不为空");
		}
	}

	public void sendApproveEmail() {

		String batchNo = $V("batchNo");
		String approveEmail = $V("approveEmail");
		String approveCCEmail = $V("approveCCEmail");
		QueryBuilder qb = new QueryBuilder("select approveStatus from CouponApproveInfo where couponBatch = ?", batchNo);
		String approveStatus = qb.executeString();
		if (StringUtil.isEmpty(approveStatus)) {
			Response.setLogInfo(0, "批次号不存在或该批次优惠券不需要审核");
			return;
		}
		if (!"0".equals(approveStatus)) {
			Response.setLogInfo(0, "该批次优惠券已经审核");
			return;
		}
		String emailContent = "您好！<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;批次为" + batchNo
				+ "的新生成优惠券等待您审批，请在cms后台->营销管理->优惠券审批菜单查看详情。";
		try {
			ActionUtil.sendGeneralMail(approveEmail, approveCCEmail, "开心保-优惠券审批通知", emailContent);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "发送失败");
		}
		Response.setLogInfo(1, "发送成功");
	}

	/**
	 * updData:修改优惠券状态. <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	private Map<String, Object> updData(SDCouponInfoSchema sdcouponschema, String memberid, String sendMode) {

		Transaction trans = new Transaction();
		if (!("0".equals(sdcouponschema.getStatus()))) {
			return PubFun.errMsg("只有状态为\"未使用\"的优惠券才可以发放!");
		}
		if ("1".equals(sdcouponschema.getPurpose())) {
			return PubFun.errMsg("只有用途为\"手动发放\"的优惠券才可以手动发放!");
		}
		// 只有优惠券发放人包括自己的才可以发放
		if (sdcouponschema.getProvideUser().indexOf(User.getUserName()) == -1) {
			return PubFun.errMsg("只有优惠券发放人中包含自己的才可以发放!");
		}
		// 发放状态为2
		sdcouponschema.setStatus("2");
		// String channelSn = sdcouponschema.getChannelSn();
		// modify by cuishigang 微信代理人优惠劵，发放时不与主站会员关联
		// 判断邮箱是否为会员
		// 将会员id关联到优惠券表中
		sdcouponschema.setMemberId(memberid);
		sdcouponschema.setMail(sendMode);
		// 实际发放人
		sdcouponschema.setRealProvideUser(User.getUserName());
		// 更改人和更改时间
		String now = DateUtil.getCurrentDateTime();
		sdcouponschema.setModifyDate(DateUtil.parseDateTime(now));
		sdcouponschema.setModifyUser(User.getUserName());
		// 发放时间
		sdcouponschema.setProp2(now);
		trans.add(sdcouponschema, Transaction.UPDATE);
		if (trans.commit()) {
			return PubFun.sucMsg();
		} else {
			return PubFun.errMsg("手动发放失败, 数据库操作失败!");
		}
	}
}
