package com.wangjin.activity;

import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
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
import com.sinosoft.schema.ActivityImgUploadSchema;
import com.sinosoft.schema.SDCouponActivityInfoSchema;
import com.sinosoft.schema.SdActivityRewardSchema;
import com.sinosoft.schema.SdActivityRuleSchema;
import com.sinosoft.schema.SdActivityRuleSet;
import com.sinosoft.schema.SdProductActivityLinkSchema;
import com.wangjin.infoseeker.QueryUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: ActivityInfo
 * @Description: TODO(CMS活动管理的逻辑处理类)
 * @author zhangjing
 * @date 2013-12-4 下午01:46:59
 *
 */
public class ActivityInfo extends Page {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInfo.class);
	private static final String QUNAR_CHANNEL="qunar";

	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String yesterday = DateUtil.toString(
			DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");

	/**
	 *
	 * @Title: init
	 * @Description: TODO(活动管理初始化查询)
	 * @return Mapx<String,String> 返回类型
	 * @author zhangjing
	 */
	public static Mapx<String, String> init(Mapx<String, String> params) {
		String activityChannel = params.getString("ActivityChannel");
		params.put("GroupbuyWhether",HtmlUtil.codeToOptions("GroupbuyWhether", true));//团购
		params.put("statusInit",HtmlUtil.codeToOptions("Activity.status", true));// 状态
		// params.put("purposeInit", HtmlUtil.codeToOptions("Coupon.purpose",
		// true));// 用途
		QueryBuilder qb_createUser = new QueryBuilder(
				"Select realName,userName from ZDUser ");
		DataTable createUser = qb_createUser.executeDataTable();
		params.put("createUserInit", HtmlUtil.dataTableToOptions(createUser));// 申请人
		// 险种
		QueryBuilder qb_riskCode = new QueryBuilder(
				"SELECT codename,code FROM fdcode WHERE codetype='erisksubtype' AND CODE NOT LIKE '%00' ");
		DataTable dt_riskcode = qb_riskCode.executeDataTable();
		params.put("riskcodeInit",
				HtmlUtil.dataTableToOptions(dt_riskcode, true));
		// 公司
		QueryBuilder qb_insuranceCompany = new QueryBuilder(
				"SELECT codename,codevalue FROM zdcode WHERE codetype='SupplierCode' AND codevalue NOT LIKE '%sys%' ");
		DataTable dt_insuranceCompany = qb_insuranceCompany.executeDataTable();
		params.put("insuranceCompanyInit",
				HtmlUtil.dataTableToOptions(dt_insuranceCompany, true));
		// 活动类型
		if ("tb".equals(activityChannel)) {
			params.put("type",HtmlUtil.mapxToOptions(CacheManager.getMapx("Code", "Activity.TBType"), true));
		} else {
			params.put("type",HtmlUtil.mapxToOptions(CacheManager.getMapx("Code", "Activity.type"), true));
		}

		// 推荐有礼活动 奖励类型
		params.put("rewardType",HtmlUtil.mapxToOptions(CacheManager.getMapx("Code", "Activity.rewardType"), true));

		params.put("yesterday", yesterday);
		params.put("today", today);
		params.put("username", User.getUserName());
		params.put("realname", User.getRealName());
		//积分倍数
		params.put("multiplenum",HtmlUtil.mapxToOptions(CacheManager.getMapx("Code", "Activity.multiplenum"), false));

		String selectOption = CacheManager.getMapx("Code", "Activity.multiplenum").toString();
		selectOption = selectOption.replace("|",":").replace(":",",").substring(0,selectOption.length() - 1);
		params.put("selectOption",selectOption);
		return params;
	}

	public static void initProvideUser(DataGridAction dga) {
		QueryBuilder qb_provideUser = new QueryBuilder(
				"Select realName,userName from ZDUser ");
		DataTable provideUser = qb_provideUser.executeDataTable();
		dga.bindData(provideUser);
	}

	/**
	 *
	 * @Title: initDialog
	 * @Description: TODO(编辑活动初始化查询)
	 * @return Mapx<String,String> 返回类型
	 * @author zhangjing
	 */
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		String ID = params.getString("ID");
		QueryBuilder qb = new QueryBuilder(
				"select id, activitysn, title, description, couponnum, batch, TYPE, riskcode, insurancecompany, payamount, starttime, endtime, STATUS, accumulation, autocreate, channelsn, prop1, prop2, prop3, prop4, createuser, createtime, modifyuser, modifytime,product,GroupbuyWhether,GroupbuyNum,memberChannel,memberRule,ruleDescription FROM sdcouponactivityinfo  where id=?");
		qb.add(ID);
		DataTable dt = qb.executeDataTable();
		DataRow dr = dt.getDataRow(0);
		String type=dr.getString("type");
		params.put("title", dr.getString("title"));
		params.put("prop2", dr.getString("prop2"));
		params.put("description", dr.getString("description"));
		params.put("payamount", dr.getString("payamount"));
		params.put("autocreate", dr.getString("autocreate"));
		String prop3 = dr.getString("prop3");
		if (prop3.length() != 0) {
			params.put( "remindDate", dr.getString("prop3").substring(0, dr.getString("prop3").indexOf(" ")));
			params.put( "remindTime", dr.getString("prop3").substring( dr.getString("prop3").indexOf(" ") + 1, dr.getString("prop3").length()));
			params.put("remindChose", "0");
			params.put("remindChose_other", "是");
		} else {
			params.put("remindDate", "");
			params.put("remindTime", "");
			params.put("remindChose", "1");
			params.put("remindChose_other", "否");
		}
		if ("0".equals(dr.getString("autocreate"))) {
			params.put("autoCreate_other", "是");
		} else {
			params.put("autoCreate_other", "否");
		}
		params.put("couponnum", dr.getString("couponnum"));
		params.put("batch", dr.getString("batch"));
		// 活动类型
		QueryBuilder qb_type = new QueryBuilder(
				"SELECT codename FROM zdcode WHERE codetype ='activity.type' AND codevalue=?",type);
		params.put("type_other", qb_type.executeString());
		params.put("type", type);
		params.put("createuser", dr.getString("createuser"));
		String startTime = dr.getString("startTime");
		if (startTime.length() != 0) {
			params.put(
					"startDate",
					dr.getString("startTime").substring(0,
							dr.getString("startTime").indexOf(" ")));
			params.put(
					"startTime",
					dr.getString("startTime").substring(
							dr.getString("startTime").indexOf(" ") + 1,
							dr.getString("startTime").length()));
			params.put(
					"endDate",
					dr.getString("endTime").substring(0,
							dr.getString("endTime").indexOf(" ")));
			params.put(
					"endTime",
					dr.getString("endTime").substring(
							dr.getString("endTime").indexOf(" ") + 1,
							dr.getString("endTime").length()));
		} else {
			params.put("startDate", "");
			params.put("startTime", "");
			params.put("endDate", "");
			params.put("endTime", "");
		}

		params.put("accumulation", dr.getString("accumulation"));
		params.put("memberChannelHidden", dr.getString("memberChannel"));
		// 会员等级
		String memberRule = dr.getString("memberRule");
		if (StringUtil.isNotEmpty(memberRule)) {
			String[] memberRuleArray = memberRule.split(",");
			for (int i = 0; i < memberRuleArray.length; i++) {
				String memberRuleValue = memberRuleArray[i];
				if ("K0".equals(memberRuleValue)) {
					params.put("checked_1", "checked");
				} else if ("K1".equals(memberRuleValue)) {
					params.put("checked_2", "checked");
				} else if ("K2".equals(memberRuleValue)) {
					params.put("checked_3", "checked");
				} else if ("VIP".equals(memberRuleValue)) {
					params.put("checked_4", "checked");
				}
			}
		}
		// 优惠公司
		String insuranceCompany[] = dr.getString("insuranceCompany").split(",");
		if (insuranceCompany[0].length() > 0) {
			String sql_company = "";
			for (int i = 0; i < insuranceCompany.length; i++) {
				sql_company += "'" + insuranceCompany[i] + "',";
			}
			QueryBuilder querybuilder = new QueryBuilder(
					"SELECT codename FROM zdcode WHERE codetype='SupplierCode' AND codevalue NOT LIKE '%sys%'  AND codevalue IN ("
							+ sql_company.substring(0, sql_company.length() - 1)
							+ ")");
			DataTable datatable = querybuilder.executeDataTable();
			String insuranceCompanyother = "";
			for (int i = 0; i < datatable.getRowCount(); i++) {
				insuranceCompanyother += datatable.getDataRow(i).getString(
						"codename")
						+ ",";
			}
			params.put(
					"insuranceCompanyother",
					insuranceCompanyother.substring(0,
							insuranceCompanyother.length() - 1));
		} else {
			params.put("insuranceCompanyother", "");
		}
		params.put("insurancecompany", dr.getString("insuranceCompany"));// 优惠公司
		// 产品
		if (dr.getString("channelsn").contains("tb")) {
			params.put("product", dr.getString("product"));
			params.put("productother", dr.getString("product"));
		} else {
			String[] productArr = dr.getString("product").split(",");
			StringBuffer productSb = new StringBuffer();
			for (int j = 0; j < productArr.length; j++) {
				productSb.append("'" + productArr[j] + "',");
			}
			QueryBuilder qb_product = new QueryBuilder(
					"select productname from sdproduct where productid in ("
							+ productSb.substring(0, productSb.length() - 1) + ")");
			DataTable dt_product = qb_product.executeDataTable();
			StringBuffer productName = new StringBuffer();
			for (int j = 0; j < dt_product.getRowCount(); j++) {
				productName.append(dt_product.getString(j, 0) + ",");
			}
			if (StringUtil.isEmpty(productName)) {
				params.put("product", "");
			} else {
				params.put("productother",
						productName.substring(0, productName.length() - 1));
			}
			params.put("product", dr.getString("product"));
			params.put("prop1", dr.getString("prop1"));//优惠金额
		}

		// 险种
		String riskcode[] = dr.getString("riskCode").split(",");
		if (riskcode[0].length() > 0) {
			String sql_riskcode = "";
			for (int i = 0; i < riskcode.length; i++) {
				sql_riskcode += "'" + riskcode[i] + "',";
			}
			QueryBuilder qb_riskcode = new QueryBuilder(
					"SELECT codename FROM fdcode WHERE codetype='erisksubtype' AND CODE NOT LIKE '%00' AND code IN ("
							+ sql_riskcode.substring(0,
									sql_riskcode.length() - 1) + ")");
			DataTable dt_riskcode = qb_riskcode.executeDataTable();
			String riskcodeother = "";
			for (int i = 0; i < dt_riskcode.getRowCount(); i++) {
				riskcodeother += dt_riskcode.getDataRow(i)
						.getString("codename") + ",";
			}
			params.put("riskCodeother",
					riskcodeother.substring(0, riskcodeother.length() - 1));// 优惠险种
		} else {
			params.put("riskCodeother", "");// 优惠险种
		}
		params.put("riskcode", dr.getString("riskCode"));// 优惠险种
		QueryBuilder qb_createUser = new QueryBuilder(
				"Select realName,userName from ZDUser ");
		DataTable createUser = qb_createUser.executeDataTable();
		QueryBuilder qb_createU = new QueryBuilder(
				"select CreateUser from couponinfo where id=?");
		qb_createU.add(ID);
		DataTable createU = qb_createU.executeDataTable();
		params.put("createUserInit",HtmlUtil.dataTableToOptions(createUser, createU, true));// 申请人
		//活动规则表
		if("1".equals(type)||"2".equals(type)||"3".equals(type)||"6".equals(type)||"7".equals(type)||"8".equals(type)||"10".equals(type)){
			QueryBuilder qb_payamount = new QueryBuilder("SELECT startamount,endamount,activitydata,MemberRule FROM  SdActivityRule   where activitysn=? order by id ",
					dr.getString("activitysn"));
			DataTable dt_activityrule = qb_payamount.executeDataTable();
			StringBuffer sb_memberRule = new StringBuffer();
			if("1".equals(type)||"2".equals(type)||"3".equals(type)||"8".equals(type)||"10".equals(type)){
				StringBuffer sb_payamount = new StringBuffer();
				StringBuffer sb_payamount_B = new StringBuffer();
				StringBuffer sb_prop1 = new StringBuffer();
				for (int i = 0; i < dt_activityrule.getRowCount(); i++) {
					sb_payamount.append(dt_activityrule.getString(i, 0) + ",");
					sb_payamount_B.append(dt_activityrule.getString(i, 1) + ",");
					sb_prop1.append(dt_activityrule.getString(i, 2) + ",");
					sb_memberRule.append(dt_activityrule.getString(i, 3) + ";");
				}
				if (StringUtil.isEmpty(sb_payamount)) {
					params.put("sb_payamount", "");
				} else {
					params.put("sb_payamount",
							sb_payamount.substring(0, sb_payamount.length() - 1));
				}
				if (StringUtil.isEmpty(sb_payamount_B)) {
					params.put("sb_payamount_B", "");
				} else {
					params.put("sb_payamount_B",
							sb_payamount_B.substring(0, sb_payamount_B.length() - 1));
				}
				if (StringUtil.isEmpty(sb_prop1)) {
					params.put("sb_prop1", "");
				} else {
					params.put("sb_prop1", sb_prop1.substring(0, sb_prop1.length() - 1));
				}
				if (StringUtil.isEmpty(sb_memberRule)) {
					params.put("sb_memberRule", "");
				} else {
					params.put("sb_memberRule", sb_memberRule.substring(0, sb_memberRule.length() - 1));
				}
			}else if("6".equals(type)){
				StringBuffer discountrate = new StringBuffer();
				for (int i = 0; i < dt_activityrule.getRowCount(); i++) {
					discountrate.append(dt_activityrule.getString(i, "activitydata") + ",");
					sb_memberRule.append(dt_activityrule.getString(i, 3) + ";");
					params.put("sb_discountrate",discountrate.substring(0, discountrate.length() - 1));
					if (StringUtil.isEmpty(sb_memberRule)) {
						params.put("sb_memberRule", "");
					} else {
						params.put("sb_memberRule", sb_memberRule.substring(0, sb_memberRule.length() - 1));
					}
				}
			}else if("7".equals(type)){
				StringBuffer multiplenum = new StringBuffer();
				String multiplenuminit="";
				for (int i = 0; i < dt_activityrule.getRowCount(); i++) {
					//积分倍数
					sb_memberRule.append(dt_activityrule.getString(i, 3) + ";");
					if(i==0){
						multiplenuminit=HtmlUtil.dataTableToOptions(new QueryBuilder("SELECT codename,codevalue FROM  zdcode WHERE codetype='Activity.multiplenum' AND codevalue!='System'").executeDataTable(),dt_activityrule.getString(i,"activitydata"),false);
						params.put("multiplenum",dt_activityrule.getString(i,"activitydata"));
						params.put("multiplenuminit",multiplenuminit);
					}else{
						multiplenum.append(dt_activityrule.getString(i,"activitydata") + ",");
						params.put("sb_multiplenum",multiplenum.substring(0, multiplenum.length() - 1));
					}
					if (StringUtil.isEmpty(sb_memberRule)) {
						params.put("sb_memberRule", "".substring(i, sb_memberRule.length() - 1));
					} else {
						params.put("sb_memberRule", sb_memberRule.substring(0, sb_memberRule.length() - 1));
					}
				}
				String selectOption = CacheManager.getMapx("Code", "Activity.multiplenum").toString();
				selectOption = selectOption.replace("|",":").replace(":",",").substring(0,selectOption.length() - 1);
				params.put("selectOption",selectOption);

			}

		}

		if (dr.getString("channelsn").contains("tb")) {
			params.put("ActivityChannel", "tb");
		}

		Mapx mapx=CacheManager.getMapx("Code", "Activity.channel");
		Mapx channelMapx = new Mapx();
		String checkstr="";
		for(Iterator it =  mapx.keySet().iterator();it.hasNext();){
		    Object key = it.next();
		    if (dr.getString("channelsn").contains("tb")) {
				if (String.valueOf(key).contains("tb")) {
					checkstr=checkstr+String.valueOf(key)+",";
					channelMapx.put(key, mapx.get(key));
				}
			} else {
				if (!String.valueOf(key).contains("tb")) {
					checkstr=checkstr+String.valueOf(key)+",";
					channelMapx.put(key, mapx.get(key));
				}
			}

		}
		String []checkarray=checkstr.substring(0,checkstr.length()-1).split(",");
		//初始化应用渠道
		params.put("channel",HtmlUtil.mapxToCheckboxes("channel",channelMapx,dr.getString("channelsn").split(","),checkarray));
		params.put("GroupbuyWhether",HtmlUtil.codeToOptions("GroupbuyWhether", true));//团购
		params.put("GroupbuyWhetherValue", dr.getString("GroupbuyWhether"));//是否团购
		params.put("GroupbuyNum", dr.getString("GroupbuyNum"));//团购数
		QueryBuilder qb_Groupbuy = new QueryBuilder(
				"SELECT codename FROM zdcode WHERE codetype ='GroupbuyWhether' AND codevalue=?",dr.getString("GroupbuyWhether"));
		params.put("GroupbuyWhether_other", qb_Groupbuy.executeString());

		// 推荐有礼活动
        if ("12".equals(type)) {
            params.put("ruleDescription", dr.getString("ruleDescription"));// 活动规则描述
            QueryBuilder queryBuilder = new QueryBuilder("SELECT a.value1,a.value2, (SELECT codename FROM zdcode WHERE codetype ='Activity.rewardType' " +
                    "AND codevalue= a.rewardType) AS rewardType FROM sdactivityreward a WHERE a.activitySn = ?", dr.getString("activitysn"));
            DataTable dataTable = queryBuilder.executeDataTable();
            if (dataTable != null && dataTable.getRowCount() > 0) {
                DataRow dataRow = dataTable.getDataRow(0);
                params.put("rewardType", dataRow.getString("rewardType"));// 活动奖励类型
                params.put("recommendBatch", dataRow.getString("value1"));// 推荐人优惠券
                params.put("recommendedBatch", dataRow.getString("value2"));// 被推荐人优惠券
            }
        }
		return params;
	}

	/**
	 *
	 * @Title: add
	 * @Description: TODO(新建活动)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public void add() {
		// 事务创建
		Transaction trans = new Transaction();
		// 创建活动schema
		SDCouponActivityInfoSchema activitySchema = new SDCouponActivityInfoSchema();
        SdActivityRewardSchema sdActivityRewardSchema = new SdActivityRewardSchema();
		// 将DataCollection中的键值对按名称自动匹配到Schema的字段中去
		activitySchema.setValue(Request);

		// 渠道编号
		String channelInfo = Request.getString("channel");
		if(StringUtil.isEmpty(channelInfo)){
			Response.setStatus(0);
			Response.setMessage("你还没有选择渠道");
			return;
		}
		if(StringUtil.isNotEmpty(Request.getString("remindChose")) &&  Request.getString("remindChose").equals("0") &&
				(StringUtil.isEmpty(Request.getString("remindDate")) ||StringUtil.isEmpty(Request.getString("remindTime")) )){
			Response.setStatus(0);
			Response.setMessage("还没有选提醒时间喔！");
			return;
		}
		if (channelInfo.subSequence(0, 1).equals(",")) {//添加渠道判断是否包含逗号
			channelInfo = channelInfo.replaceFirst(",","");
		}
		//团购
		String GroupbuyWhether = Request.getString("GroupbuyWhether");
		if("1".equals(GroupbuyWhether)){
			String GroupbuyNum = Request.getString("GroupbuyNum");
			if(!StringUtil.isPositiveInteger(GroupbuyNum)){
				Response.setStatus(0);
				Response.setMessage("团购单数必须为正整数！");
				return;
			}
		}
		String channel = "";
		if("tb".equals(Request.getString("ActivityChannel"))) {
			String[] arr = channelInfo.split(",");
			for (String ch : arr) {
				channel += (ch + ",");
				if ("tb".equals(ch)) {
					String chiChannelSn = new QueryBuilder("select group_concat(ChannelCode) from ChannelInfo where ChannelCode not in ('tbsd','tb_my','"+QUNAR_CHANNEL+"') and ParentInnerChanelCode in (select InnerChannelCode from ChannelInfo where ChannelCode='tb')").executeString();
					if (StringUtil.isNotEmpty(chiChannelSn)) {
						channel += (chiChannelSn + ",");
					}
				}
			}
		} else {
			//处理，渠道信息；针对该渠道下的所有子渠道增加活动信息
			channel = QueryUtil.getChildChannelList(channelInfo);
		}
		activitySchema.setchannelsn(channel);

		// 定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = Request.getString("startDate");
		String startTime = Request.getString("startTime");
		String starttime = startDate + " "+ startTime;
		String endtime = Request.getString("endDate")+" "+ Request.getString("endTime");
		// 优惠券批次号
		String batch = Request.getString("batch");
        // 推荐人优惠券
        String recommendBatch = null;
        // 被推荐人优惠券
        String recommendedBatch = null;
		// 优惠券数量
		String couponnum = Request.getString("couponnum");
		//险种、公司、产品限制条件
		String  insuranceCompany= Request.getString("insuranceCompany");
		String  riskCode= Request.getString("riskCode");
		String  product= Request.getString("product");
        if (startDate.length() != 0) {
            try {
                // 开始时间
                activitySchema.setstarttime(sdf.parse(starttime));
                // 结束时间
                activitySchema.setendtime(sdf.parse(endtime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage(), e);
            }
        }
        //
		// 生成活动主键ID
		String year = DateUtil.getCurrentDateTime("yyyy");
		String id = year + NoUtil.getMaxNo("ActivitySn", 12);
		// 活动ID值
		activitySchema.setId(id);
		// 活动编号
		String activitysn = "HD" + DigestUtils.md5Hex(id);
		activitySchema.setactivitysn(activitysn);
		// 初始状态为“待发布”
		activitySchema.setstatus("0");
        Date date = null;
		try {
			date = sdf.parse(PubFun.getCurrent());
			// 系统时间
			activitySchema.setcreatetime(date);
			// couponSchema.setCreateUser(User.getUserName());
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		// 活动类型
		String type = Request.getString("type");
		if("6".equals(type)|| "8".equals(type) ){//折扣和自定义活动增加收藏夹和带支付的邮件提醒
			activitySchema.setprop3(Request.getString("remindDate")+" "+Request.getString("remindTime"));
		}
		// 满送活动
		if ("1".equals(type)) {
			String payAmount = Request.getString("payAmount_0");
			String payAmountB = Request.getString("payAmountB_0");
			if (!(StringUtil.isInteger(payAmount))) {
				Response.setStatus(0);
				Response.setMessage("消费金额开始区间非正整数！");
				return;
			}
			if (!(StringUtil.isInteger(payAmountB))) {
				Response.setStatus(0);
				Response.setMessage("消费金额结束区间非正整数！");
				return;
			}
			// 活动规则表，记录满送活动金额信息
			SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
			sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
			sdactivityruleschema.setActivitySn(activitysn);
			sdactivityruleschema.setStartAmount(new BigDecimal(payAmount));
			sdactivityruleschema.setEndAmount(new BigDecimal(payAmountB));
			trans.add(sdactivityruleschema, Transaction.INSERT);
		}else if ("2".equals(type)) {//买送活动
				// 活动规则表，记录买送活动金额信息
				SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
				sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
				sdactivityruleschema.setActivitySn(activitysn);
				sdactivityruleschema.setStartAmount(new BigDecimal("0"));
				sdactivityruleschema.setEndAmount(new BigDecimal("999999999"));
				trans.add(sdactivityruleschema, Transaction.INSERT);
		}else if ("3".equals(type)){
			String num_payamout = Request.getString("num");
			//如果是多个梯度价格，活动为“不可累计”
			if(Integer.parseInt(num_payamout)>1){
				activitySchema.setaccumulation("1");
			}
			for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
				// 区间开始
				String payAmount = Request.getString("payAmount_" + i);
				// 区间结束
				String payAmountB = Request.getString("payAmountB_" + i);
				// 适合会员级别
				String memberRule = Request.getString("checkvip_" + i);
				//优惠金额
				String prop1 = Request.getString("prop1_" + i);
				if (StringUtil.isEmpty(prop1)|| StringUtil.isEmpty(payAmount)|| StringUtil.isEmpty(payAmountB)) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的优惠金额或消费金额为空！");
					return;
				}else if ((!(StringUtil.isDouble(payAmount)))) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的消费金额开始区间为非正数！");
					return;
				}else if ((!(StringUtil.isDouble(payAmountB)))) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的消费金额结束区间为非正数！");
					return;
				}else if (!(StringUtil.isDouble(prop1))) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的优惠金额为非正数！");
					return;
				}else if (new BigDecimal(payAmount).compareTo(new BigDecimal(prop1)) <= 0) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的优惠金额应小于消费金额开始区间！");
					return;
				}
				// 活动规则表，记录满送活动金额信息
				SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
				sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
				sdactivityruleschema.setActivitySn(activitysn);
				sdactivityruleschema.setStartAmount(new BigDecimal(payAmount));
				sdactivityruleschema.setEndAmount(new BigDecimal(payAmountB));
				sdactivityruleschema.setActivityData(new BigDecimal(prop1));
				sdactivityruleschema.setMemberRule(memberRule);
				trans.add(sdactivityruleschema, Transaction.INSERT);
			}
		}else if ("8".equals(type)){//自定义活动
			String num_payamout = Request.getString("num");
			String checkvip_List ="";
			String payAmount="";
			String payAmountB="";
			String memberRule="";
			//自定义活动为“不可累计”
			activitySchema.setaccumulation("1");
			for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
				// 区间开始
				payAmount = Request.getString("payAmount_" + i);
				// 区间结束
				payAmountB = Request.getString("payAmountB_" + i);
				//自定义活动无优惠金额
				//String prop1 = Request.getString("prop1_" + i);
				// 适合会员级别
				memberRule = Request.getString("checkvip_" + i);
				if (StringUtil.isEmpty(payAmount)|| StringUtil.isEmpty(payAmountB)) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的优惠金额或消费金额为空！");
					return;
				}
				if ((!(StringUtil.isDouble(payAmount)))) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的消费金额开始区间为非正数！");
					return;
				}else if ((!(StringUtil.isDouble(payAmountB)))) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的消费金额结束区间为非正数！");
					return;
				}
				if(StringUtil.isNotEmpty(Request.getString("checkvip_" + i))){
					checkvip_List = checkvip_List+","+Request.getString("checkvip_" + i);
				}
//				else if (!(StringUtil.isDouble(prop1))) {
//					Response.setStatus(0);
//					Response.setMessage("第" + (i + 1) + "行的优惠金额为非正数！");
//					return;
//				}else if (new BigDecimal(payAmount).compareTo(new BigDecimal(prop1)) <= 0) {
//					Response.setStatus(0);
//					Response.setMessage("第" + (i + 1) + "行的优惠金额应小于消费金额开始区间！");
//					return;
//				}
				// 活动规则表，记录满送活动金额信息
				SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
				sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
				sdactivityruleschema.setActivitySn(activitysn);
				sdactivityruleschema.setStartAmount(new BigDecimal(payAmount));
				sdactivityruleschema.setEndAmount(new BigDecimal(payAmountB));
				sdactivityruleschema.setActivityData(new BigDecimal(0));
				sdactivityruleschema.setMemberRule(memberRule);
				trans.add(sdactivityruleschema, Transaction.INSERT);
			}
			if(!isExist(checkvip_List)){
				Response.setStatus(0);
				Response.setMessage("每个会员等级仅可选一次!");
				return;
			}
		}else if ("6".equals(type)) {// 折扣活动
			String num_payamout = Request.getString("num");
			String discountrate=Request.getString("discountrate");
			String checkvip_List ="";
			//自定义活动为“不可累计”
			if(Integer.parseInt(num_payamout)>1){
				activitySchema.setaccumulation("1");
			}
			for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
				// 活动规则表，记录折扣活动折扣率
				SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
				sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
				sdactivityruleschema.setActivitySn(activitysn);
				sdactivityruleschema.setMemberRule(Request.getString("checkvip_" + i));
				if(i>0){
					discountrate=Request.getString("discountrate_"+i);
				}
				if(StringUtil.isNotEmpty(Request.getString("checkvip_" + i))){
					checkvip_List = checkvip_List+","+Request.getString("checkvip_" + i);
				}
				if(StringUtil.isDouble(discountrate)){
					if(!(0<=Double.parseDouble(discountrate)&&Double.parseDouble(discountrate)<10)){
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的折扣活动的折扣率应该大于0，小于10");
						return;
					}
					sdactivityruleschema.setActivityData(new BigDecimal(discountrate));
				}else{
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的折扣活动的折扣率应为非负数");
					return;
				}
				trans.add(sdactivityruleschema, Transaction.INSERT);
			}
			if(!isExist(checkvip_List)){
				Response.setStatus(0);
				Response.setMessage("每个会员等级仅可选一次!");
				return;
			}
		}else if ("10".equals(type)) {// 满送话费
			String num_payamout = Request.getString("num");
			//如果是多个梯度价格，活动为“不可累计”
			if(Integer.parseInt(num_payamout)>1){
				activitySchema.setaccumulation("1");
			}
			for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
				// 区间开始
				String payAmount = Request.getString("payAmount_" + i);
				// 区间结束
				String payAmountB = Request.getString("payAmountB_" + i);
				// 适合会员级别
				String memberRule = Request.getString("checkvip_" + i);
				//优惠金额
				String prop1 = Request.getString("prop1_" + i);
				if (StringUtil.isEmpty(prop1)|| StringUtil.isEmpty(payAmount)|| StringUtil.isEmpty(payAmountB)) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的优惠金额或消费金额为空！");
					return;
				}else if ((!(StringUtil.isDouble(payAmount)))) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的消费金额开始区间为非正数！");
					return;
				}else if ((!(StringUtil.isDouble(payAmountB)))) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的消费金额结束区间为非正数！");
					return;
				}else if (!(StringUtil.isDouble(prop1))) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的优惠金额为非正数！");
					return;
				}else if (new BigDecimal(payAmount).compareTo(new BigDecimal(prop1)) <= 0) {
					Response.setStatus(0);
					Response.setMessage("第" + (i + 1) + "行的优惠金额应小于消费金额开始区间！");
					return;
				}
				// 活动规则表，记录满送活动金额信息
				SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
				sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
				sdactivityruleschema.setActivitySn(activitysn);
				sdactivityruleschema.setStartAmount(new BigDecimal(payAmount));
				sdactivityruleschema.setEndAmount(new BigDecimal(payAmountB));
				sdactivityruleschema.setActivityData(new BigDecimal(prop1));
				sdactivityruleschema.setMemberRule(memberRule);
				trans.add(sdactivityruleschema, Transaction.INSERT);
			}
		} else if ("7".equals(type)) {//高倍积分
			String checkvip_List ="";
			String num_payamout =Request.getString("num");
			String multiplenum=Request.getString("multiplenum");
			if(Integer.parseInt(num_payamout)>1){
				activitySchema.setaccumulation("1");
			}
			for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
			// 活动规则表，记录高倍积分活动积分倍数
				if(i>0){
					multiplenum=Request.getString("multiplenum_"+i);
				}
				SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
				sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
				sdactivityruleschema.setActivitySn(activitysn);
				sdactivityruleschema.setMemberRule(Request.getString("checkvip_"+i));
				if(StringUtil.isNotEmpty(Request.getString("checkvip_" + i))){
					checkvip_List = checkvip_List+","+Request.getString("checkvip_" + i);
				}
				sdactivityruleschema.setActivityData(new BigDecimal(multiplenum));
				trans.add(sdactivityruleschema, Transaction.INSERT);
			}
			if(!isExist(checkvip_List)){
				Response.setStatus(0);
				Response.setMessage("每个会员等级仅可选一次!");
				return;
			}
		} else if ("11".equals(type)) {
			String payAmount = Request.getString("payAmount");
			if (StringUtil.isNotEmpty(payAmount) && !(StringUtil.isInteger(payAmount))) {
				Response.setStatus(0);
				Response.setMessage("消费金额非正整数！");
				return;
			}
			// 消费金额
			activitySchema.setpayamount(payAmount);

			// 会员等级
			String memberRule = Request.getString("checkvip");
			activitySchema.setMemberRule(memberRule);

			// 自动获取优惠劵
			String autoCreate = Request.getString("autoCreate1");
			activitySchema.setautocreate(autoCreate);
		} else if ("12".equals(type)) {
		    // 活动规则描述
            String ruleDescription = Request.getString("ruleDescription");
            // 奖励类型
            String rewardType = Request.getString("rewardType");
            // 推荐人优惠券
            recommendBatch = Request.getString("recommendBatch");
            // 被推荐人优惠券
            recommendedBatch = Request.getString("recommendedBatch");

            activitySchema.setRuleDescription(ruleDescription);

            // 活动奖励
            sdActivityRewardSchema.setid(CommonUtil.getUUID());
            sdActivityRewardSchema.setactivitySn(activitysn);
            sdActivityRewardSchema.setrewardType(rewardType);
            sdActivityRewardSchema.setvalue1(recommendBatch);
            sdActivityRewardSchema.setvalue2(recommendedBatch);
            sdActivityRewardSchema.setcreateTime(date);
        }
		if("1".equals(type)||"2".equals(type)||"3".equals(type)||"6".equals(type)||"7".equals(type)||"8".equals(type)||"10".equals(type)||"11".equals(type)){
			//根据险种、公司等条件筛选出全部productid
			DataTable dt_products=getProductActivityLinkData(activitysn,insuranceCompany,riskCode,product);
			String channel_str="'"+channel.replace(",", "','")+"'";
			if("3".equals(type)||"6".equals(type)||"10".equals(type)){
				//校验满减或折扣活动同一时间段同一产品是否只有一个活动与之对应  //3满减，6折扣，10满送话费
				boolean flag=checkActivitySingleton(String.valueOf(starttime),String.valueOf(endtime), insuranceCompany, riskCode, product, dt_products,channel_str,activitysn);
				//校验没有通过,同一时间段同一产品有多个活动
				if(!flag){
					return;
				}
			} else if("7".equals(type)){
				//高倍积分活动同一时间段同一产品是否只有一个活动与之对应
				boolean flag=checkGBActivitySingleton(type,"高倍积分",String.valueOf(starttime),String.valueOf(endtime), insuranceCompany, riskCode, product, dt_products,channel_str,activitysn);
				//校验没有通过,同一时间段同一产品有多个活动
				if(!flag){
					return;
				}
			} else if ("11".equals(type)) {
				//支付有礼活动同一时间段同一产品是否只有一个活动与之对应
				boolean flag=checkGBActivitySingleton(type,"支付有礼",String.valueOf(starttime),String.valueOf(endtime), insuranceCompany, riskCode, product, dt_products,channel_str,activitysn);
				if(!flag){
					return;
				}
			}
//			//多个渠道，多套数据
//			String[] channel_array=channel.split(",");
//			for (int i = 0; i < channel_array.length; i++) {
//				//产品、活动关联表增加数据
//				if (channel_array[i].contains("tb")) {
//					if (StringUtil.isEmpty(product)) {
//						Response.setStatus(0);
//						Response.setMessage("请关联产品!");
//						return;
//					} else {
//						String[] productids = product.split(",");
//						for (int j = 0; j < productids.length; j++) {
//							//产品活动关联表
//							SdProductActivityLinkSchema linkschema=new SdProductActivityLinkSchema();
//							linkschema.setId(NoUtil.getMaxNo("ActivityRulesID", 11));
//							linkschema.setProductId(productids[j]);
//							linkschema.setActivitySn(activitysn);
//							linkschema.setActivityChannel(channel_array[i]);
//							trans.add(linkschema,Transaction.INSERT);
//						}
//					}
//				} else {
//					if(dt_products.getRowCount()>0){
//						for (int j = 0; j < dt_products.getRowCount(); j++) {
//							//产品活动关联表
//							SdProductActivityLinkSchema linkschema=new SdProductActivityLinkSchema();
//							linkschema.setId(NoUtil.getMaxNo("ActivityRulesID", 11));
//							linkschema.setProductId(dt_products.getString(j,0));
//							linkschema.setActivitySn(activitysn);
//							linkschema.setActivityChannel(channel_array[i]);
//							trans.add(linkschema,Transaction.INSERT);
//						}
//					}else{
//						Response.setStatus(0);
//						Response.setMessage("设定险种和公司条件下没有任何产品，公司为："+insuranceCompany+"险种为："+riskCode);
//						return;
//					}
//				}
//			}
		}
		// 检查优惠券数量是否满足输入的数量条件
		String autoCreate = Request.getString("autoCreate");
		//与优惠券相关的活动
		if ("0".equals(type)||"1".equals(type)||"2".equals(type)||"4".equals(type)||"5".equals(type)) {
			if (!checkCouponNum(batch, autoCreate, couponnum)) {
				return;
			}
		} else if ("11".equals(type)) {
			autoCreate = Request.getString("autoCreate1");
			int iNumPayamout = Integer.parseInt(Request.getString("batch_num"));
			couponnum = "1";
			StringBuilder sbBatch = new StringBuilder();
			for (int i = 0; i < iNumPayamout; i++) {
				batch = Request.getString("batch_" + (i+1));
				if (!checkCouponNum(batch, autoCreate, couponnum)) {
					return;
				}
				sbBatch.append(",");
				sbBatch.append(batch);
			}
			activitySchema.setbatch(sbBatch.toString().substring(1));
		}
		// 增加活动信息数据
		trans.add(activitySchema, Transaction.INSERT);
		// 增加活动奖励数据
		trans.add(sdActivityRewardSchema, Transaction.INSERT);

		// 提交事务
		if (trans.commit()) {
			//优惠券相关的活动
			if ("0".equals(type)||"1".equals(type)||"2".equals(type)||"4".equals(type)||"5".equals(type)||"9".equals(type)) {
				if (!updateCoupon(batch, couponnum, autoCreate, activitySchema.getactivitysn())) {
					return;
				}
			} else if ("11".equals(type)) {
				int iNumPayamout = Integer.parseInt(Request.getString("batch_num"));
				for (int i = 0; i < iNumPayamout; i++) {
					batch = Request.getString("batch_" + (i+1));
					if (!updateCoupon(batch, couponnum, autoCreate, activitySchema.getactivitysn())) {
						return;
					}
				}
			} else if ("12".equals(type)) {
                if (!updateCoupon(recommendBatch, couponnum, autoCreate, activitySchema.getactivitysn())) {
                    return;
                }
                if (!updateCoupon(recommendedBatch, couponnum, autoCreate, activitySchema.getactivitysn())) {
                    return;
                }
            }
			String message = "添加成功！";
			Response.setStatus(1);
			Response.setMessage(message.substring(0, message.length() - 1));
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误！");
		}
	}

	/**
	 *
	 * @Title: edit
	 * @Description: TODO(修改活动)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public void edit() {
		// 事务创建
		Transaction trans = new Transaction();
		// 活动schema
		SDCouponActivityInfoSchema activitySchema = new SDCouponActivityInfoSchema();
		// 将DataCollection中的键值对按名称自动匹配到Schema的字段中去
		activitySchema.setId(Request.getString("ID"));
		if (activitySchema.fill()) {
			// 只有"待发布"状态的活动才可以更改
			String status = activitySchema.getstatus();
			if (!(status.equals("0"))) {
				Response.setStatus(0);
				Response.setMessage("只有\"待发布\"状态的活动才可以修改!");
				return;
			}
			if(StringUtil.isNotEmpty(Request.getString("remindChose")) &&  Request.getString("remindChose").equals("0") &&
					(StringUtil.isEmpty(Request.getString("remindDate")) ||StringUtil.isEmpty(Request.getString("remindTime")) )){
				Response.setStatus(0);
				Response.setMessage("还没有选提醒时间喔！");
				return;
			}
			//赋值
			activitySchema.setValue(Request);
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startDate = Request.getString("startDate");
			String startTime = Request.getString("startTime");
			String starttime = startDate + " "+ startTime;
			String endtime = Request.getString("endDate")+" "+ Request.getString("endTime");
			try {
				if (startDate.length() != 0) {
					// 开始时间
					activitySchema.setstarttime(sdf.parse(startDate + " "
							+ startTime));
					// 结束时间
					activitySchema.setendtime(sdf.parse(endtime));
					// 修改时间和修改人
					Date date = sdf.parse(PubFun.getCurrent());
					activitySchema.setmodifytime(date);
					activitySchema.setmodifyuser(User.getUserName());
				}
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			// 渠道编号
			String channelInfo = Request.getString("channel");
			if(StringUtil.isEmpty(channelInfo)){
				Response.setStatus(0);
				Response.setMessage("你还没有选择渠道");
				return;
			}
			//团购
			String GroupbuyWhether = Request.getString("GroupbuyWhether");
			if("1".equals(GroupbuyWhether)){
				String GroupbuyNum = Request.getString("GroupbuyNum");
				if(!StringUtil.isPositiveInteger(GroupbuyNum)){
					Response.setStatus(0);
					Response.setMessage("团购单数必须为正整数！");
					return;
				}
			}
			//处理，渠道信息；针对该渠道下的所有子渠道增加活动信息
			String channel = QueryUtil.getChildChannelList(channelInfo);
			activitySchema.setchannelsn(channel);
			//活动类型
			String type = activitySchema.gettype();

			//活动编码
			String activitysn = activitySchema.getactivitysn();
			//险种、公司、产品限制条件
			String  insuranceCompany= Request.getString("insuranceCompany");
			String  riskCode= Request.getString("riskCode");
			String  product= Request.getString("product");
			if("6".equals(type)|| "8".equals(type) ){//折扣和自定义活动增加收藏夹和带支付的邮件提醒
				activitySchema.setprop3(Request.getString("remindDate")+" "+Request.getString("remindTime"));
			}
			//满送活动
			if ("1".equals(type)) {
				String payAmount = Request.getString("payAmount_0");
				String payAmountB = Request.getString("payAmountB_0");
				if (!(StringUtil.isInteger(payAmount))) {
					Response.setStatus(0);
					Response.setMessage("消费金额开始区间非正整数！");
					return;
				}
				if (!(StringUtil.isInteger(payAmountB))) {
					Response.setStatus(0);
					Response.setMessage("消费金额结束区间非正整数！");
					return;
				}
				//删除先前活动规则
				SdActivityRuleSchema sdactivityrules=new SdActivityRuleSchema();
				SdActivityRuleSet ruleset=sdactivityrules.query(new QueryBuilder("where activitysn=?",activitysn));
				trans.add(ruleset,Transaction.DELETE);
				// 活动规则表，记录满送活动金额信息
				SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
				sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
				sdactivityruleschema.setActivitySn(activitysn);
				sdactivityruleschema.setStartAmount(new BigDecimal(payAmount));
				sdactivityruleschema.setEndAmount(new BigDecimal(payAmountB));
				trans.add(sdactivityruleschema, Transaction.INSERT);
			}else if ("3".equals(type)||"10".equals(type)){
				String num_payamout = Request.getString("num");
				for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
					// 区间开始
					String payAmount = Request.getString("payAmount_" + i);
					// 区间结束
					String payAmountB = Request.getString("payAmountB_" + i);
					// 适合会员级别
					String memberRule = Request.getString("checkvip_" + i);
					//优惠金额
					String prop1 = Request.getString("prop1_" + i);
					if (StringUtil.isEmpty(prop1)|| StringUtil.isEmpty(payAmount)|| StringUtil.isEmpty(payAmountB)) {
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的优惠金额或消费金额为空！");
						return;
					}else if ((!(StringUtil.isDouble(payAmount)))) {
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的消费金额开始区间为非正数！");
						return;
					}else if ((!(StringUtil.isDouble(payAmountB)))) {
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的消费金额结束区间为非正数！");
						return;
					}else if (!(StringUtil.isDouble(prop1))) {
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的优惠金额为非正数！");
						return;
					}else if (new BigDecimal(payAmount).compareTo(new BigDecimal(prop1)) <= 0) {
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的优惠金额应小于消费金额开始区间！");
						return;
					}
					//删除先前活动规则
					SdActivityRuleSchema sdactivityrules=new SdActivityRuleSchema();
					SdActivityRuleSet ruleset=sdactivityrules.query(new QueryBuilder("where activitysn=?",activitysn));
					trans.add(ruleset,Transaction.DELETE);
					// 活动规则表，记录满送活动金额信息
					SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
					sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
					sdactivityruleschema.setActivitySn(activitysn);
					sdactivityruleschema.setStartAmount(new BigDecimal(payAmount));
					sdactivityruleschema.setEndAmount(new BigDecimal(payAmountB));
					sdactivityruleschema.setActivityData(new BigDecimal(prop1));
					sdactivityruleschema.setMemberRule(memberRule);
					trans.add(sdactivityruleschema, Transaction.INSERT);
				}
			}else if ("8".equals(type)){
				String checkvip_List="";
				String num_payamout = Request.getString("num");
				for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
					// 区间开始
					String payAmount = Request.getString("payAmount_" + i);
					// 区间结束
					String payAmountB = Request.getString("payAmountB_" + i);
					//自定义活动无优惠金额
					//String prop1 = Request.getString("prop1_" + i);
					// 适合会员级别
					String memberRule = Request.getString("checkvip_" + i);
					if (StringUtil.isEmpty(payAmount)|| StringUtil.isEmpty(payAmountB)) {
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的消费金额为空！");
						return;
					}else if ((!(StringUtil.isDouble(payAmount)))) {
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的消费金额开始区间为非正数！");
						return;
					}else if ((!(StringUtil.isDouble(payAmountB)))) {
						Response.setStatus(0);
						Response.setMessage("第" + (i + 1) + "行的消费金额结束区间为非正数！");
						return;
					}
					if(StringUtil.isNotEmpty(Request.getString("checkvip_" + i))){
						checkvip_List = checkvip_List+","+Request.getString("checkvip_" + i);
					}
//					else if (!(StringUtil.isDouble(prop1))) {
//						Response.setStatus(0);
//						Response.setMessage("第" + (i + 1) + "行的优惠金额为非正数！");
//						return;
//					}else if (new BigDecimal(payAmount).compareTo(new BigDecimal(prop1)) <= 0) {
//						Response.setStatus(0);
//						Response.setMessage("第" + (i + 1) + "行的优惠金额应小于消费金额开始区间！");
//						return;
//					}
					//删除先前活动规则
					SdActivityRuleSchema sdactivityrules=new SdActivityRuleSchema();
					SdActivityRuleSet ruleset=sdactivityrules.query(new QueryBuilder("where activitysn=?",activitysn));
					trans.add(ruleset,Transaction.DELETE);
					// 活动规则表，记录满送活动金额信息
					SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
					sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
					sdactivityruleschema.setActivitySn(activitysn);
					sdactivityruleschema.setStartAmount(new BigDecimal(payAmount));
					sdactivityruleschema.setEndAmount(new BigDecimal(payAmountB));
					sdactivityruleschema.setMemberRule(memberRule);
					sdactivityruleschema.setActivityData(new BigDecimal("0"));
					trans.add(sdactivityruleschema, Transaction.INSERT);
				}
				if(!isExist(checkvip_List)){
					Response.setStatus(0);
					Response.setMessage("每个会员等级仅可选一次!");
					return;
				}
			}else if ("6".equals(type)) {// 折扣活动
				String checkvip_List="";
				String num_payamout = Request.getString("num");
				String discountrate=Request.getString("discountrate");
				for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
					//删除先前活动规则
					SdActivityRuleSchema sdactivityrules=new SdActivityRuleSchema();
					SdActivityRuleSet ruleset=sdactivityrules.query(new QueryBuilder("where activitysn=?",activitysn));
					trans.add(ruleset,Transaction.DELETE);
					// 活动规则表，记录折扣活动折扣率
					SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
					sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
					sdactivityruleschema.setActivitySn(activitysn);
					sdactivityruleschema.setMemberRule(Request.getString("checkvip_"+i));
					if(StringUtil.isNotEmpty(Request.getString("checkvip_" + i))){
						checkvip_List = checkvip_List+","+Request.getString("checkvip_" + i);
					}
					if(i>0){
						discountrate=Request.getString("discountrate_"+i);
					}
					if(StringUtil.isDouble(discountrate)){
						if(!(0<Double.parseDouble(discountrate)&&Double.parseDouble(discountrate)<10)){
							Response.setStatus(0);
							Response.setMessage("折扣活动的折扣率应该大于0，小于10");
							return;
						}
						sdactivityruleschema.setActivityData(new BigDecimal(discountrate));
					}else{
						Response.setStatus(0);
						Response.setMessage("折扣活动的折扣率应为非负数");
						return;
					}
					trans.add(sdactivityruleschema, Transaction.INSERT);
				}
				if(!isExist(checkvip_List)){
					Response.setStatus(0);
					Response.setMessage("每个会员等级仅可选一次!");
					return;
				}
			} else if ("7".equals(type)) {//高倍积分
				String checkvip_List="";
				String num_payamout = Request.getString("num");
				String multiplenum=Request.getString("multiplenum");
				for (int i = 0; i < Integer.parseInt(num_payamout); i++) {
					//删除先前活动规则
					SdActivityRuleSchema sdactivityrules=new SdActivityRuleSchema();
					SdActivityRuleSet ruleset=sdactivityrules.query(new QueryBuilder("where activitysn=?",activitysn));
					trans.add(ruleset,Transaction.DELETE);
					// 活动规则表，记录高倍积分活动积分倍数
					SdActivityRuleSchema sdactivityruleschema = new SdActivityRuleSchema();
					sdactivityruleschema.setID(NoUtil.getMaxNo("ActivityRulesID", 11));
					sdactivityruleschema.setActivitySn(activitysn);
					if(i>0){
						multiplenum=Request.getString("multiplenum_"+i);
					}
					sdactivityruleschema.setMemberRule(Request.getString("checkvip_"+i));
					if(StringUtil.isNotEmpty(Request.getString("checkvip_" + i))){
						checkvip_List = checkvip_List+","+Request.getString("checkvip_" + i);
					}
					sdactivityruleschema.setActivityData(new BigDecimal(multiplenum));
					trans.add(sdactivityruleschema, Transaction.INSERT);
				}
				if(!isExist(checkvip_List)){
					Response.setStatus(0);
					Response.setMessage("每个会员等级仅可选一次!");
					return;
				}
			} else if ("11".equals(type)) {
				String payAmount = Request.getString("payAmount");
				if (StringUtil.isNotEmpty(payAmount) && !(StringUtil.isInteger(payAmount))) {
					Response.setStatus(0);
					Response.setMessage("消费金额非正整数！");
					return;
				}
				// 消费金额
				activitySchema.setpayamount(payAmount);

				// 会员等级
				String memberRule = Request.getString("checkvip");
				activitySchema.setMemberRule(memberRule);
			}

			if("1".equals(type)||"2".equals(type)||"3".equals(type)||"6".equals(type)||"7".equals(type)||"8".equals(type)||"11".equals(type)){
				String channel_str="'"+channel.replace(",", "','")+"'";
				//根据险种、公司等条件筛选出全部productid
				DataTable dt_products=getProductActivityLinkData(activitysn,insuranceCompany,riskCode,product);
				if("3".equals(type)||"6".equals(type)){
					//校验满减或折扣或活动同一时间段同一产品是否只有一个活动与之对应
					boolean flag=checkActivitySingleton(String.valueOf(starttime),String.valueOf(endtime), insuranceCompany, riskCode, product, dt_products,channel_str,activitysn);
					//校验没有通过,同一时间段同一产品有多个活动
					if(!flag){
						return;
					}
				}
				 else if("7".equals(type)){
					//高倍积分活动同一时间段同一产品是否只有一个活动与之对应
					boolean flag=checkGBActivitySingleton(type,"高倍积分",String.valueOf(starttime),String.valueOf(endtime), insuranceCompany, riskCode, product, dt_products,channel_str,activitysn);
					//校验没有通过,同一时间段同一产品有多个活动
					if(!flag){
						return;
					}
				} else if ("11".equals(type)) {
					//支付有礼活动同一时间段同一产品是否只有一个活动与之对应
					boolean flag=checkGBActivitySingleton(type,"支付有礼",String.valueOf(starttime),String.valueOf(endtime), insuranceCompany, riskCode, product, dt_products,channel_str,activitysn);
					if(!flag){
						return;
					}
				}
			}
			// 修改数据
			trans.add(activitySchema, Transaction.UPDATE);
			// 提交事务
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("编辑成功！");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}
	/**
	 *
	* @Title: getProductActivityLinkData
	* @Description: TODO(根据险种、公司等条件筛选出全部productid)
	* @return void    返回类型
	* @author zhangjing
	 */
	private DataTable getProductActivityLinkData(String ActivitySn,String  InsuranceCompany,String RiskCode,String Products){
		String sql="";
		if(StringUtil.isNotEmpty(InsuranceCompany)&&StringUtil.isNotEmpty(RiskCode)){
			sql=" SELECT riskcode,riskshortname FROM fmrisk WHERE FIND_IN_SET(BelongFlag,'"+RiskCode+"')!=0  and  FIND_IN_SET(suppliercode,'"+InsuranceCompany+"')!=0 ";
		}else if(StringUtil.isNotEmpty(InsuranceCompany)&&StringUtil.isEmpty(RiskCode)){
			sql=" SELECT riskcode,riskshortname FROM fmrisk WHERE   FIND_IN_SET(suppliercode,'"+InsuranceCompany+"')!=0 ";
		}else if(StringUtil.isEmpty(InsuranceCompany)&&StringUtil.isNotEmpty(RiskCode)){
			sql=" SELECT riskcode,riskshortname FROM fmrisk WHERE FIND_IN_SET(BelongFlag,'"+RiskCode+"')!=0 ";
		}else if(StringUtil.isNotEmpty(Products)&&StringUtil.isEmpty(InsuranceCompany)&&StringUtil.isEmpty(RiskCode)){
			sql=" SELECT riskcode,riskshortname FROM fmrisk WHERE FIND_IN_SET(riskcode,'"+Products+"')!=0  ";
		}else {
			sql=" SELECT riskcode,riskshortname FROM fmrisk";
		}
		DataTable dt_products=new QueryBuilder(sql).executeDataTable();
		return dt_products;
	}
	/**
	 *
	* @Title: checkSingletonActivity
	* @Description: TODO(校验满减或折扣活动同一时间段同一产品是否只有一个活动与之对应)
	* @return boolean    返回类型
	* @author XXX
	 */
	private boolean checkActivitySingleton(String starttime,String endtime,String  InsuranceCompany,String RiskCode,String Products,DataTable dt_products,String channel_str,String activitysn){
		String sql="SELECT productid,title  FROM  sdproductactivitylink t1 LEFT JOIN sdcouponactivityinfo t2 ON t1.activitysn=t2.activitysn WHERE 1=1 and type in ('3','6') and activitychannel in ("+channel_str+")  and t2.activitysn!='"+activitysn+"'  "
						+" and  ((  UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s'))   "
						+ "	and  UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s')))"
						+ " or (UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s')) "
						+ " and UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')))"
						+ " or (UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s'))"
						+ " and  UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')))"
						+ " or (UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s'))"
						+ " and  UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')) ))"
						+ " and t2.status='3'";
		DataTable dt_activity=new QueryBuilder(sql).executeDataTable();
		if(dt_activity.getRowCount()>0){
			for (int i = 0; i < dt_activity.getRowCount(); i++) {
				if (channel_str.contains("tb")) {
					if (StringUtil.isNotEmpty(Products) && Products.contains(dt_activity.getString(i,"productid"))) {
						Response.setStatus(0);
						Response.setMessage("同一时间段内,\""+dt_activity.getString(i,"productid")+"\"已参加了\""+dt_activity.getString(i,"title")+"\"活动！");
						return false;
					}
				} else {
					for (int j = 0; j < dt_products.getRowCount(); j++) {
						if(StringUtil.noNull(dt_products.getString(j,"riskcode")).equals(StringUtil.noNull(dt_activity.getString(i,"productid")))){
							Response.setStatus(0);
							Response.setMessage("同一时间段内,\""+dt_products.getString(j,"riskshortname")+"\"已参加了\""+dt_activity.getString(i,"title")+"\"活动！");
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	/**
	 *
	* @Title: checkGBActivitySingleton
	* @Description: (校验同一时间段同一产品是否只有一个活动与之对应)
	* @return boolean    返回类型
	* @author XXX
	 */
	private boolean checkGBActivitySingleton(String type, String typeName,String starttime,String endtime,String  InsuranceCompany,String RiskCode,String Products,DataTable dt_products,String channel_str,String activitysn){
		String sql="SELECT productid,title  FROM  sdproductactivitylink t1 LEFT JOIN sdcouponactivityinfo t2 ON t1.activitysn=t2.activitysn WHERE 1=1 and type ='"+type+"' and activitychannel in ("+channel_str+")  and t2.activitysn!='"+activitysn+"'  "
						+" and  ((  UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s'))   "
						+ "	and  UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s')))"
						+ " or (UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s')) "
						+ " and UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')))"
						+ " or (UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s'))"
						+ " and  UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')))"
						+ " or (UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"+starttime+"','%Y-%m-%d %H:%i:%s'))"
						+ " and  UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(DATE_FORMAT('"+endtime+"','%Y-%m-%d %H:%i:%s')) ))"
						+ " and t2.status='3'";
		DataTable dt_activity=new QueryBuilder(sql).executeDataTable();
		if(dt_activity.getRowCount()>0){
			for (int i = 0; i < dt_activity.getRowCount(); i++) {
				for (int j = 0; j < dt_products.getRowCount(); j++) {
					if(StringUtil.noNull(dt_products.getString(j,"riskcode")).equals(StringUtil.noNull(dt_activity.getString(i,"productid")))){
						Response.setStatus(0);
						Response.setMessage("同一时间段内"+typeName+",\""+dt_products.getString(j,"riskshortname")+"\"已参加了\""+dt_activity.getString(i,"title")+"\"活动！");
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 *
	 * @Title: delete
	 * @Description: TODO(删除活动)
	 * @return void 返回类型
	 * @author zhangjing
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
			SDCouponActivityInfoSchema activityschema = new SDCouponActivityInfoSchema();
			activityschema.setId(IDs[n]);
			if (activityschema.fill()) {
				// 删除状态为1
				activityschema.setstatus("1");
				// 更改人和更改时间
				String now = DateUtil.getCurrentDateTime();
				activityschema.setmodifytime(DateUtil.parseDateTime(now));
				activityschema.setmodifyuser(User.getUserName());
				trans.add(activityschema, Transaction.UPDATE);
				trans.add(new QueryBuilder(" delete FROM sdproductactivitylink WHERE activitysn=? ",activityschema.getactivitysn()));
			} else {
				Response.setLogInfo(1, "无效主键数据!");
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}

	/**
	 *
	 * @Title: provide
	 * @Description: TODO(发布活动)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public void provide() {
		// 活动主键ID
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		if(IDs.length>1){
			Response.setStatus(0);
			Response.setMessage("一次只能发布一个活动!");
			return;
		}
		Transaction trans = new Transaction();
		// 记录注册送优惠券类型的活动个数
		int num = 0;
		int num_insuranceTest = 0;
		Map<String, String> activitySns = new HashMap<String,String>();
		List<Map<String, String>> activitySnlist = new ArrayList<Map<String,String>>();
		SDCouponActivityInfoSchema activityschema = new SDCouponActivityInfoSchema();
		for (int n = 0; n < IDs.length; n++) {
			activityschema.setId(IDs[n]);
			if (activityschema.fill()) {
				String status = activityschema.getstatus();
				if (!(status.equals("0"))) {
					Response.setLogInfo(0, "只有待发布状态的活动才可以发布!");
					return;
				}
				String type = activityschema.gettype();
				// 注册送优惠券的活动只允许发布一个
				if (type.equals("0")) {
					if (IDs.length > 1) {
						num++;
					}
					QueryBuilder qb_type = new QueryBuilder(
							"SELECT count(id) FROM sdcouponactivityinfo where status='3' and type='0' ");
					int row = qb_type.executeInt();
					if (row > 0) {
						Response.setLogInfo(0, "\"注册送优惠券\"类型的活动只允许发布一个!");
						return;
					}
				}
				// 保险测试送优惠券的活动只允许发布一个
				if (type.equals("4")) {
					if (IDs.length > 1) {
						num_insuranceTest++;
					}
					QueryBuilder qb_type = new QueryBuilder(
							"SELECT count(id) FROM sdcouponactivityinfo where status='3' and type='4' ");
					int row = qb_type.executeInt();
					if (row > 0) {
						Response.setLogInfo(0, "\"保险测试送优惠券\"类型的活动只允许发布一个!");
						return;
					}
				}
				String endtime = String.valueOf(activityschema.getendtime());
				endtime.substring(0, endtime.length() - 2);
				String now = DateUtil.getCurrentDateTime();
				if (DateUtil.compare(endtime, now, "yyyy-MM-dd HH:mm:ss") < 0) {
					// 过期状态为4已过期
					activityschema.setstatus("4");
				} else {
					// 发布状态为3
					activityschema.setstatus("3");
				}
				// 更改人和更改时间
				activityschema.setmodifytime(DateUtil.parseDateTime(now));
				activityschema.setmodifyuser(User.getUserName());
				trans.add(activityschema, Transaction.UPDATE);
			} else {
				Response.setLogInfo(1, "无效主键数据!");
			}
			String type=activityschema.gettype();
			if("1".equals(type)||"2".equals(type)||"3".equals(type)||"6".equals(type)||"7".equals(type)||"8".equals(type)||"9".equals(type)||"10".equals(type)||"11".equals(type)||"12".equals(type)){
				//根据险种、公司等条件筛选出全部productid
				DataTable dt_products=getProductActivityLinkData(activityschema.getactivitysn(),activityschema.getinsurancecompany(),activityschema.getriskcode(),activityschema.getproduct());
				String channel_str="'"+activityschema.getchannelsn().replace(",", "','")+"'";
				if("3".equals(type)||"6".equals(type)||"12".equals(type)){
					//校验满减或折扣活动同一时间段同一产品是否只有一个活动与之对应
					boolean flag=checkActivitySingleton(String.valueOf(activityschema.getstarttime()),String.valueOf(activityschema.getendtime()), activityschema.getinsurancecompany(), activityschema.getriskcode(), activityschema.getproduct(), dt_products,channel_str,activityschema.getactivitysn());
					if(!flag){
						return;
					}
				}
				else if("7".equals(type)){
					//高倍积分活动同一时间段同一产品是否只有一个活动与之对应
					boolean flag=checkGBActivitySingleton(type,"高倍积分",String.valueOf(activityschema.getstarttime()),String.valueOf(activityschema.getendtime()), activityschema.getinsurancecompany(), activityschema.getriskcode(), activityschema.getproduct(), dt_products,channel_str,activityschema.getactivitysn());
					if(!flag){
						return;
					}
				}
				else if ("11".equals(type)) {
					//支付有礼活动同一时间段同一产品是否只有一个活动与之对应
					boolean flag=checkGBActivitySingleton(type,"支付有礼",String.valueOf(activityschema.getstarttime()),String.valueOf(activityschema.getendtime()), activityschema.getinsurancecompany(), activityschema.getriskcode(), activityschema.getproduct(), dt_products,channel_str,activityschema.getactivitysn());
					if(!flag){
						return;
					}
				}
				String channelSn = activityschema.getchannelsn();
				if(channelSn.indexOf("tb")!=-1){
					String[] channelsn=activityschema.getchannelsn().split(",");
					String[] products=activityschema.getproduct().split(",");
					for (int j = 0; j < products.length; j++) {
						for (int i = 0; i < channelsn.length; i++) {
							if(channelsn[i]==null||"".equals(channelsn[i])){
								continue;
							}
							//产品活动关联表
							SdProductActivityLinkSchema linkschema=new SdProductActivityLinkSchema();
							linkschema.setId(NoUtil.getMaxNo("ActivityRulesID", 11));
							linkschema.setProductId(products[j]);
							linkschema.setActivitySn(activityschema.getactivitysn());
							linkschema.setActivityChannel(channelsn[i]);
							trans.add(linkschema,Transaction.INSERT);
						}
					}
				}else{
					//产品、活动关联表增加数据
					if(dt_products.getRowCount()>0){
						for (int j = 0; j < dt_products.getRowCount(); j++) {
							String[] channelsn=activityschema.getchannelsn().split(",");
							for (int i = 0; i < channelsn.length; i++) {
								//产品活动关联表
								SdProductActivityLinkSchema linkschema=new SdProductActivityLinkSchema();
								linkschema.setId(NoUtil.getMaxNo("ActivityRulesID", 11));
								linkschema.setProductId(dt_products.getString(j,0));
								linkschema.setActivitySn(activityschema.getactivitysn());
								linkschema.setActivityChannel(channelsn[i]);
								trans.add(linkschema,Transaction.INSERT);
							}
						}
					}else{
						Response.setStatus(0);
						Response.setMessage("设定险种和公司条件下没有任何产品，公司为："+activityschema.getinsurancecompany()+"险种为："+activityschema.getriskcode());
					}
				}
				if(compareDate(activityschema.getendtime(),new Date())<=0 ){
					Response.setLogInfo(0, "活动已过终止日期! activitysn=" +activityschema.getactivitysn());
					return;
				}
				if(("6".equals(type)||"8".equals(type)) && StringUtil.isNotEmpty(activityschema.getprop3())){//自定义和折扣的 是否提醒
					String channelsn =activityschema.getchannelsn();
					if(channelsn.contains("wj") ||channelsn.contains("wap_wx")||channelsn.contains("wap_ht")||channelsn.contains("kxb_app")){
						try{
							if(compareDate(DateUtil.parse(activityschema.getprop3(), "yyyy-MM-dd HH:mm:ss"),new Date())<=0
									|| StringUtil.isEmpty(type) || StringUtil.isEmpty(activityschema.getactivitysn()) || StringUtil.isEmpty(activityschema.getendtime().toString()) ){
								Response.setLogInfo(0, "请检查提醒时间是否小于当前时间以及数据是否规范! activitysn="+activityschema.getactivitysn());//提醒时间需要小于当前时间!
								return;
							}
						}catch(Exception e){
							Response.setLogInfo(0, "您所录入的提醒时间有误! activitysn=" +activityschema.getactivitysn());
							return;
						}
						activitySns = new HashMap<String,String>();
						activitySns.put("activitySn", activityschema.getactivitysn());//activitySns.size()
						activitySns.put("type", type);


						try {
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							activitySns.put("endtime", formatter.format(formatter.parse(activityschema.getendtime().toString())));//确保日期格式准确
						} catch (ParseException e) {
							//logger.error(e.getMessage(), e);
							Response.setLogInfo(0, "终止日期有误! activitysn=" +activityschema.getactivitysn());
							return;
						}
						activitySns.put("runtime", activityschema.getprop3());
						activitySnlist.add(activitySns);
					}else{
						Response.setLogInfo(0, "带提醒时间的活动必须是wj,wap_wx,wap_ht,kxb_app渠道之一! activitysn=" +activityschema.getactivitysn());
						return;
					}

				}else{
					if(StringUtil.isNotEmpty(activityschema.getprop3())){
						Response.setLogInfo(0, "只有自定义活动和折扣活动才能发布带提醒时间的活动任务! activitysn=" +activityschema.getactivitysn());
						return;
					}
				}
			}



		}
		if (num < 2 && num_insuranceTest < 2) {
			String ResponseLogInfo="发布活动成功";
			String id = (String) User.getValue("_CurrentSiteID");
			if (trans.commit()) {
				try {
					//折扣活动发放  更新价格

					new ActivityOutDateCheck().updateProductInitPrem(activityschema.getactivitysn(), activityschema.gettype(),activityschema.gettitle(),false);
				} catch (MessagingException e) {
					ResponseLogInfo = ResponseLogInfo + ",但折扣活动发放更新价格失败";
					logger.error(e.getMessage(), e);
				}
				try {
					if(activitySnlist.size()>0){
						//定时任务
						SendUserActivation sendUserActivation = new SendUserActivation();
						sendUserActivation.activityActivationtimer(activitySnlist);
					}
				} catch (Exception e) {
					ResponseLogInfo = ResponseLogInfo + ",但提醒时间发布失败";
					logger.error(e.getMessage(), e);
				}
				ResponseLogInfo = ResponseLogInfo+"!";
				Response.setLogInfo(1, ResponseLogInfo);
				return;
			} else {
				Response.setLogInfo(0, "操作失败!");
				return;
			}
		} else if (num >= 2) {
			Response.setLogInfo(0, "此次发布的活动中包括多个\"注册送优惠券\"类型的活动，请重新选择!");
			return;
		} else if (num_insuranceTest >= 2) {
			Response.setLogInfo(0, "此次发布的活动中包括多个\"保险测试送优惠券\"类型的活动，请重新选择!");
			return;
		}
	}
	/**
	 *
	* @Title: provide
	* @Description: TODO(更新最新产品到活动)
	* @return void    返回类型
	* @author XXX
	 */
	public void updateproduct() {
		// 活动主键ID
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			SDCouponActivityInfoSchema activityschema = new SDCouponActivityInfoSchema();
			activityschema.setId(IDs[n]);
			if (activityschema.fill()) {
				String activitysn=activityschema.getactivitysn();
				String status=activityschema.getstatus();
				if(!"3".equals(status)){
					Response.setLogInfo(0, "\""+activityschema.gettitle()+"\"的活动状态不是\"发布\"状态!");
					return;
				}
				String type=activityschema.gettype();
				if("1".equals(type)||"2".equals(type)||"3".equals(type)||"6".equals(type)||"7".equals(type)||"8".equals(type)||"10".equals(type)){
					//删除先前的活动关联记录
					QueryBuilder qb_delete=new QueryBuilder("delete from SdProductActivityLink where activitysn=?",activitysn);
					trans.add(qb_delete);
					//根据险种、公司等条件筛选出全部productid
					DataTable dt_products=getProductActivityLinkData(activitysn,activityschema.getinsurancecompany(),activityschema.getriskcode(),activityschema.getproduct());
					//产品、活动关联表增加数据
					if(dt_products.getRowCount()>0){
						for (int j = 0; j < dt_products.getRowCount(); j++) {
							String[] channelsn=activityschema.getchannelsn().split(",");
							for (int i = 0; i < channelsn.length; i++) {
								//产品活动关联表
								SdProductActivityLinkSchema linkschema=new SdProductActivityLinkSchema();
								linkschema.setId(NoUtil.getMaxNo("ActivityRulesID", 11));
								linkschema.setProductId(dt_products.getString(j,0));
								linkschema.setActivitySn(activitysn);
								linkschema.setActivityChannel(channelsn[i]);
								trans.add(linkschema,Transaction.INSERT);
							}
						}
					}else{
						Response.setStatus(0);
						Response.setMessage("设定险种和公司条件下没有任何产品，公司为："+activityschema.getinsurancecompany()+"险种为："+activityschema.getriskcode());
					}
				}
			} else {
				Response.setLogInfo(0, "无效主键数据!");
			}
		}
		if(trans.commit()){
			Response.setLogInfo(1, "最新产品已更新到该活动!");
			return;
		}else{
			Response.setLogInfo(0, "产品更新失败!");
			return;
		}
	}

	/**
	 *
	 * @Title: frozen
	 * @Description: TODO(暂停活动)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public void frozen() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		if(IDs.length>1){
			Response.setStatus(0);
			Response.setMessage("一次只能暂停一个活动!");
			return;
		}
		Transaction trans = new Transaction();
		SDCouponActivityInfoSchema activityschema = new SDCouponActivityInfoSchema();
		for (int n = 0; n < IDs.length; n++) {
			activityschema.setId(IDs[n]);
			if (activityschema.fill()) {
				String status = activityschema.getstatus();
				if (!(status.equals("3"))) {
					Response.setLogInfo(0, "只有已发布状态的活动才可以暂停!");
					return;
				}
				String endtime = String.valueOf(activityschema.getendtime());
				endtime.substring(0, endtime.length() - 2);
				String now = DateUtil.getCurrentDateTime();
				if (DateUtil.compare(endtime, now, "yyyy-MM-dd HH:mm:ss") < 0) {
					// 过期状态为4已过期
					activityschema.setstatus("4");
				} else {
					// 暂停状态为2
					activityschema.setstatus("2");
				}
				// 更改人和更改时间
				activityschema.setmodifytime(DateUtil.parseDateTime(now));
				activityschema.setmodifyuser(User.getUserName());
				trans.add(activityschema, Transaction.UPDATE);
				QueryBuilder qb_delete_link=new QueryBuilder("delete from sdproductactivitylink where activitysn=?",activityschema.getactivitysn());
				trans.add(qb_delete_link);
			} else {
				Response.setLogInfo(0, "无效主键数据!");
			}
		}
		try {
			//折扣活动暂停  更新价格
			new ActivityOutDateCheck().updateProductInitPrem(activityschema.getactivitysn(), activityschema.gettype(),activityschema.gettitle(),true);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "暂停成功!");
		} else {
			Response.setLogInfo(0, "暂停失败!");
		}
	}

	/**
	 *
	 * @Title: unFrozen
	 * @Description: TODO(活动恢复)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public void unFrozen() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		if(IDs.length>1){
			Response.setStatus(0);
			Response.setMessage("一次只能恢复一个活动!");
			return;
		}
		Transaction trans = new Transaction();
		// 记录注册送优惠券类型的活动个数
		int num = 0;
		int num_insuranceTest = 0;
		SDCouponActivityInfoSchema activityschema = new SDCouponActivityInfoSchema();
		for (int n = 0; n < IDs.length; n++) {
			activityschema.setId(IDs[n]);
			if (activityschema.fill()) {
				String status = activityschema.getstatus();
				if (!(status.equals("2"))) {
					Response.setLogInfo(0, "只有已暂停状态的活动才可以恢复!");
					return;
				}
				String type = activityschema.gettype();
				String activitysn=activityschema.getactivitysn();
				if("1".equals(type)||"2".equals(type)||"3".equals(type)||"6".equals(type)||"7".equals(type)||"8".equals(type)||"9".equals(type)||"10".equals(type)||"11".equals(type)){
					//根据险种、公司等条件筛选出全部productid
					DataTable dt_products=getProductActivityLinkData(activityschema.getactivitysn(),activityschema.getinsurancecompany(),activityschema.getriskcode(),activityschema.getproduct());
					String channel_str="'"+activityschema.getchannelsn().replace(",", "','")+"'";
					if("3".equals(type)||"6".equals(type)){
						//校验满减或折扣活动同一时间段同一产品是否只有一个活动与之对应
						boolean flag=checkActivitySingleton(String.valueOf(activityschema.getstarttime()),String.valueOf(activityschema.getendtime()), activityschema.getinsurancecompany(), activityschema.getriskcode(), activityschema.getproduct(), dt_products,channel_str,activityschema.getactivitysn());
						if(!flag){
							return;
						}
					}
					 else if("7".equals(type)){
						//高倍积分活动同一时间段同一产品是否只有一个活动与之对应
						boolean flag=checkGBActivitySingleton(type,"高倍积分",String.valueOf(activityschema.getstarttime()),String.valueOf(activityschema.getendtime()), activityschema.getinsurancecompany(), activityschema.getriskcode(), activityschema.getproduct(), dt_products,channel_str,activityschema.getactivitysn());
						if(!flag){
							return;
						}
					}
					else if ("11".equals(type)) {
						//支付有礼活动同一时间段同一产品是否只有一个活动与之对应
						boolean flag=checkGBActivitySingleton(type,"支付有礼",String.valueOf(activityschema.getstarttime()),String.valueOf(activityschema.getendtime()), activityschema.getinsurancecompany(), activityschema.getriskcode(), activityschema.getproduct(), dt_products,channel_str,activityschema.getactivitysn());
						if(!flag){
							return;
						}
					}
					String channelSn= activityschema.getchannelsn();
					if(channelSn.indexOf("tb")!=-1){
						String[] channelsn=activityschema.getchannelsn().split(",");
						String[] products=activityschema.getproduct().split(",");
						for (int j = 0; j < products.length; j++) {
							for (int i = 0; i < channelsn.length; i++) {
								if(channelsn[i]==null||"".equals(channelsn[i])){
									continue;
								}
								//产品活动关联表
								SdProductActivityLinkSchema linkschema=new SdProductActivityLinkSchema();
								linkschema.setId(NoUtil.getMaxNo("ActivityRulesID", 11));
								linkschema.setProductId(products[j]);
								linkschema.setActivitySn(activitysn);
								linkschema.setActivityChannel(channelsn[i]);
								trans.add(linkschema,Transaction.INSERT);
							}
						}
					}else{
						//产品、活动关联表增加数据
						if(dt_products.getRowCount()>0){
							for (int j = 0; j < dt_products.getRowCount(); j++) {
								String[] channelsn=activityschema.getchannelsn().split(",");
								for (int i = 0; i < channelsn.length; i++) {
									//产品活动关联表
									SdProductActivityLinkSchema linkschema=new SdProductActivityLinkSchema();
									linkschema.setId(NoUtil.getMaxNo("ActivityRulesID", 11));
									linkschema.setProductId(dt_products.getString(j,0));
									linkschema.setActivitySn(activitysn);
									linkschema.setActivityChannel(channelsn[i]);
									trans.add(linkschema,Transaction.INSERT);
								}
							}
						}else{
							Response.setStatus(0);
							Response.setMessage("设定险种和公司条件下没有任何产品，公司为："+activityschema.getinsurancecompany()+"险种为："+activityschema.getriskcode());
						}
					}
				}
				// 注册送优惠券的活动只允许发布一个
				if (type.equals("0")) {
					if (IDs.length > 1) {
						num++;
					}
					QueryBuilder qb_type = new QueryBuilder(
							"SELECT count(id) FROM sdcouponactivityinfo where status='3' and type='0' ");
					int row = qb_type.executeInt();
					if (row > 0) {
						Response.setLogInfo(0, "\"注册送优惠券\"类型的活动只允许发布一个!");
						return;
					}
				}
				// 保险测试送优惠券的活动只允许发布一个
				if (type.equals("4")) {
					if (IDs.length > 1) {
						num_insuranceTest++;
					}
					QueryBuilder qb_type = new QueryBuilder(
							"SELECT count(id) FROM sdcouponactivityinfo where status='3' and type='4' ");
					int row = qb_type.executeInt();
					if (row > 0) {
						Response.setLogInfo(0, "\"保险测试送优惠券\"类型的活动只允许发布一个!");
						return;
					}
				}
				String endtime = String.valueOf(activityschema.getendtime());
				endtime.substring(0, endtime.length() - 2);
				String now = DateUtil.getCurrentDateTime();
				if (DateUtil.compare(endtime, now, "yyyy-MM-dd HH:mm:ss") < 0) {
					// 过期状态为4已过期
					activityschema.setstatus("4");
				} else {
					// 恢复后状态为3已发布
					activityschema.setstatus("3");
				}
				// 更改人和更改时间
				activityschema.setmodifytime(DateUtil.parseDateTime(now));
				activityschema.setmodifyuser(User.getUserName());
				trans.add(activityschema, Transaction.UPDATE);
				// trans.add(sdcouponschema, Transaction.DELETE_AND_BACKUP);
			} else {
				Response.setLogInfo(1, "无效主键数据!");
			}
		}
		if (num < 2 && num_insuranceTest < 2) {
			if (trans.commit()) {
				Response.setLogInfo(1, "恢复成功!");
				try {
					//折扣活动暂停  更新价格
					new ActivityOutDateCheck().updateProductInitPrem(activityschema.getactivitysn(), activityschema.gettype(),activityschema.gettitle(),false);
				} catch (MessagingException e) {
					logger.error(e.getMessage(), e);
				}
				return;
			} else {
				Response.setLogInfo(0, "恢复失败!");
				return;
			}
		} else if (num >= 2) {
			Response.setLogInfo(0, "此次恢复的活动中包括多个\"注册送优惠券\"类型的活动，请重新选择!");
			return;
		} else if (num_insuranceTest >= 2) {
			Response.setLogInfo(0, "此次恢复的活动中包括多个\"保险测试送优惠券\"类型的活动，请重新选择!");
			return;
		}
	}

	/**
	 *
	 * @Title: dg1DataBind
	 * @Description: TODO(活动初始化查询)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String activitysn = (String) dga.getParams().get("activitysn");
		String title = (String) dga.getParams().get("title");
		String status = (String) dga.getParams().get("status");
		String createuser = (String) dga.getParams().get("createuser");
		String description = (String) dga.getParams().get("description");
		String riskcodeSearch = (String) dga.getParams().get("riskcodeSearch");
		String insuranceCompanySearch = (String) dga.getParams().get(
				"insuranceCompanySearch");
		String startDate = (String) dga.getParams().get("startDate");
		String startTime = (String) dga.getParams().get("startTime");
		String endDate = (String) dga.getParams().get("endDate");
		String endTime = (String) dga.getParams().get("endTime");
		String createStartDate = (String) dga.getParams()
				.get("createStartDate");
		String createStartTime = (String) dga.getParams()
				.get("createStartTime");
		String createEndDate = (String) dga.getParams().get("createEndDate");
		String createEndTime = (String) dga.getParams().get("createEndTime");
		QueryBuilder qb = new QueryBuilder(
				"select a.* from sdcouponactivityinfo a where  status !=1 ");// 非删除状态的活动
		// 申请人查询
		if (StringUtil.isNotEmpty(createuser)) {
			QueryBuilder querybuilder = new QueryBuilder(
					"SELECT username FROM zduser WHERE realname LIKE '%"
							+ createuser + "%'");
			if (querybuilder.executeDataTable().getRowCount() != 0) {
				qb.append(" and createUser like  '%"
						+ querybuilder.executeString().trim() + "%'");
			}
		}
		// 险种查询
		if (StringUtil.isNotEmpty(riskcodeSearch)) {
			qb.append(" and riskCode like " + "'%" + riskcodeSearch + "%'");
			// }
		}
		// 活动名称查询
		if (StringUtil.isNotEmpty(title)) {
			qb.append(" and title like " + "'%" + title + "%'");
			// }
		}
		// 优惠公司查询
		if (StringUtil.isNotEmpty(insuranceCompanySearch)) {
			qb.append(" and insuranceCompany like " + "'%"
					+ insuranceCompanySearch + "%'");
			// }
		}
		// 活动描述
		if (StringUtil.isNotEmpty(description)) {
			qb.append(" and description like  '%" + description.trim() + "%'");
		}
		// 活动编号查询
		if (StringUtil.isNotEmpty(activitysn)) {
			qb.append(" and activitysn like  '%" + activitysn.trim() + "%'");
		}
		// 状态查询
		if (StringUtil.isNotEmpty(status)) {
			qb.append(" and a.status = " + status.trim());
		}
		// 活动开始时间查询
		if (StringUtil.isNotEmpty(startDate)) {
			if (StringUtil.isEmpty(startTime)) {
				startTime = "00:00:00";
			} else {
				if (startTime.length() == 7) {
					startTime = "0" + startTime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.starttime,'%Y-%m-%d %H:%i:%s')) >=  UNIX_TIMESTAMP( '"
					+ startDate.trim() + " " + startTime.trim() + "')");
		}
		// 活动结束时间
		if (StringUtil.isNotEmpty(endDate)) {
			if (StringUtil.isEmpty(endTime)) {
				endTime = "23:59:59";
			} else {
				if (startTime.length() == 7) {
					endTime = "0" + endTime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.endtime,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP('"
					+ endDate.trim() + " " + endTime.trim() + "')");
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
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createtime,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP("
					+ "'"
					+ createStartDate.trim()
					+ " "
					+ createStartTime.trim() + "')");
		}
//		else {
//			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createtime,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP("
//					+ "'" + yesterday + " " + "00:00:00" + "')");
//		}
		// 申请结束时间(默认为当天)
		if (StringUtil.isNotEmpty(createEndDate)) {
			if (StringUtil.isEmpty(createEndTime)) {
				createEndTime = "23:59:59";
			} else {
				if (createEndTime.length() == 7) {
					createEndTime = "0" + createEndTime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createtime,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP("
					+ "'"
					+ createEndDate.trim()
					+ " "
					+ createEndTime.trim()
					+ "')");
		}
//		else {
//			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createtime,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP("
//					+ "'" + today + " " + "23:59:59" + "')");
//		}
		qb.append(" order by  a.createtime desc");
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "activitysn", dt.getString(i, "activitysn"));
			dt.set(i, "title", dt.getString(i, "title"));
			dt.set(i, "description", dt.getString(i, "description"));
			if (StringUtil.isNotEmpty(dt.getString(i, "couponnum"))) {
				dt.set(i, "couponnum", dt.getString(i, "couponnum"));
			} else {
				dt.set(i, "couponnum", "无限制");
			}
			//满减、折扣、高倍积分
			if("1".equals(dt.getString(i, "type"))||"3".equals(dt.getString(i, "type"))||"6".equals(dt.getString(i, "type"))||"7".equals(dt.getString(i, "type"))||"8".equals(dt.getString(i, "type"))){
				QueryBuilder qb_activityrule = new QueryBuilder(
						"SELECT startamount,activitydata FROM  sdactivityrule WHERE activitysn=?  ORDER BY startamount  ",dt.getString(i, "activitysn"));
				DataTable dt_activityrule = qb_activityrule.executeDataTable();
				if("3".equals(dt.getString(i, "type"))){
					//消费金额
					dt.set(i, "payamount", StringUtil.noNull(dt_activityrule.getString(0, 0)));
					// 优惠金额
					dt.set(i, "prop1",  StringUtil.noNull(dt_activityrule.getString(0, 1)));
					//批次
					dt.set(i, "batch", "满减活动无批次");
				}else if("6".equals(dt.getString(i, "type"))){
					//消费金额
					dt.set(i, "payamount", "");
					// 优惠金额
					dt.set(i, "prop1", trimZeros(StringUtil.noNull(dt_activityrule.getString(0, 1)))+"折");
					dt.set(i, "batch", "折扣活动无批次");
				}else if("7".equals(dt.getString(i, "type"))){
					//消费金额
					dt.set(i, "payamount", "");
					// 优惠金额
					dt.set(i, "prop1", trimZeros(StringUtil.noNull(dt_activityrule.getString(0, 1)))+"倍");
					dt.set(i, "batch", "高倍积分活动无批次");
				}else if("8".equals(dt.getString(i, "type"))){
					//消费金额
					dt.set(i, "payamount", StringUtil.noNull(dt_activityrule.getString(0, 0)));
					// 优惠金额
					dt.set(i, "prop1","");
					//批次
					dt.set(i, "batch", "自定义活动无批次");
				}
			}else {
				dt.set(i, "batch", dt.getString(i, "batch"));
			}
			QueryBuilder qb_type = new QueryBuilder(
					"SELECT codename FROM zdcode WHERE codetype ='activity.type' AND codevalue=?",
					dt.getString(i, "type"));
			dt.set(i, "type", qb_type.executeString());
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
			if (riskCodeSb.length() == 0
					|| "null".equals(String.valueOf(riskCodeSb))) {
				dt.set(i, "riskCode", "无限制");
			} else {
				dt.set(i, "riskCode", riskCodeSb);
			}
			String[] insuranceCompanyArr = dt.getString(i, "insuranceCompany")
					.split(",");
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
			String[] productArr = dt.getString(i, "product").split(",");
			StringBuffer productSb = new StringBuffer();
			for (int j = 0; j < productArr.length; j++) {
				productSb.append("'" + productArr[j] + "',");
			}
			QueryBuilder qb_product = new QueryBuilder(
					"select productname from sdproduct where productid in ("
							+ productSb.substring(0, productSb.length() - 1)
							+ ")");
			DataTable dt_product = qb_product.executeDataTable();
			StringBuffer productName = new StringBuffer("");
			for (int j = 0; j < dt_product.getRowCount(); j++) {
				productName.append(dt_product.getString(j, 0) + ",");
			}
			if (StringUtil.isEmpty(productName)) {
				dt.set(i, "product", "无限制");
			} else {
				dt.set(i, "product",
						productName.substring(0, productName.length() - 1));
			}
			if (insuranceCompanySb.length() == 0
					|| "null".equals(String.valueOf(insuranceCompanySb))) {
				dt.set(i, "insuranceCompany", "无限制");
			} else {
				dt.set(i, "insuranceCompany", insuranceCompanySb);
			}
			if (dt.getString(i, "starttime").length() == 0
					|| "null".equals(String.valueOf(dt
							.getString(i, "starttime")))) {
				dt.set(i, "starttime", "无限制");
			} else {
				dt.set(i, "starttime",
						dt.getString(i, "starttime").substring(0, 11));
			}
			if (dt.getString(i, "endtime").length() == 0
					|| "null"
							.equals(String.valueOf(dt.getString(i, "endtime")))) {
				dt.set(i, "endtime", "无限制");
			} else {
				dt.set(i, "endtime", dt.getString(i, "endtime")
						.substring(0, 11));
			}
			QueryBuilder qb_status = new QueryBuilder(
					"select CodeName from zdCode where CodeType = 'activity.status' and CodeValue = ? ");
			qb_status.add(dt.getString(i, "status"));
			dt.set(i, "status", qb_status.executeString());
			if ("0".equals(dt.getString(i, "autocreate"))) {
				dt.set(i, "autocreate", "是");
			} else {
				dt.set(i, "autocreate", "否");
			}
			// 累加状态
			if ("0".equals(dt.getString(i, "accumulation"))) {
				dt.set(i, "accumulation", "是");
			} else {
				dt.set(i, "accumulation", "否");
			}
			// 申请人
			QueryBuilder qb_createUser = new QueryBuilder(
					"select realname from zduser where username =?");
			qb_createUser.add(dt.getString(i, "createuser"));
			dt.set(i, "createuser", qb_createUser.executeString());

			dt.set(i, "createtime", dt.getString(i, "createtime"));
			// 修改人
			QueryBuilder qb_modifyUser = new QueryBuilder(
					"select realname from zduser where username =?");
			qb_modifyUser.add(dt.getString(i, "modifyuser"));
			dt.set(i, "modifyuser", qb_modifyUser.executeString());
			dt.set(i, "modifytime", dt.getString(i, "modifytime"));
		}
		dga.bindData(dt);
	}
	/**
	 *
	* @Title: trimZeros
	* @Description: TODO(去小数点后除末尾无用0)
	* @return String    返回类型
	* @author XXX
	 */
	private static String trimZeros(String str){
		String data=str;
		while (true) {
			if((data.endsWith("0")&&data.length()>0)&&(data.indexOf(".")!=-1)){
				data=data.substring(0,data.length()-1);
			}if(data.endsWith(".")&&data.length()>0){
				data=data.substring(0,data.length()-1);
			}else{
				break;
			}
		}
		return data;
	}
	/**
	 *
	 * @Title: dg1DataBindRiskCode
	 * @Description: TODO(初始化险种)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public static void dg1DataBindRiskCode(DataGridAction dga) {

		String codename = (String) dga.getParams().get("codename");
		QueryBuilder qb_riskCode = new QueryBuilder(
				"SELECT code as id ,codename FROM fdcode WHERE codetype='erisksubtype' AND CODE NOT LIKE '%00' ");
		if (StringUtil.isNotEmpty(codename)) {
			qb_riskCode.append(" and codename like " + "'%" + codename.trim()
					+ "%'");
		}
		qb_riskCode.append(dga.getSortString());
		dga.setTotal(qb_riskCode);
		DataTable dt = qb_riskCode.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "codename", dt.getString(i, "codename"));
		}
		dga.bindData(dt);
	}

	/**
	 *
	 * @Title: dg1DataBindInsuranceCompany
	 * @Description: TODO(初始化保险公司)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public static void dg1DataBindInsuranceCompany(DataGridAction dga) {

		String codename = (String) dga.getParams().get("codename");
		QueryBuilder qb_insuranceCompany = new QueryBuilder(
				"SELECT DISTINCT codevalue AS id,codename FROM zdcode,fmrisk WHERE codetype='SupplierCode' AND codevalue NOT LIKE '%sys%' AND zdcode.CodeValue=fmrisk.SupplierCode ");
		if (StringUtil.isNotEmpty(codename)) {
			qb_insuranceCompany.append(" and codename like " + "'%"
					+ codename.trim() + "%'");
		}
		qb_insuranceCompany.append(dga.getSortString());
		dga.setTotal(qb_insuranceCompany);
		DataTable dt = qb_insuranceCompany.executePagedDataTable(
				dga.getPageSize(), dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "codename", dt.getString(i, "codename"));
		}
		dga.bindData(dt);
	}

	/**
	 *
	 * @Title: dg1DataBindProduct
	 * @Description: TODO(初始化产品)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public static void dg1DataBindProduct(DataGridAction dga) {

		String productname = (String) dga.getParams().get("productname");
		QueryBuilder qb_product = new QueryBuilder(
				"SELECT productid as  id,productname FROM  sdproduct WHERE  IsPublish='Y' ");
		if (StringUtil.isNotEmpty(productname)) {
			qb_product.append(" and productname like " + "'%"
					+ productname.trim() + "%'");
		}
		qb_product.append(" ORDER BY productid ");
		qb_product.append(dga.getSortString());
		dga.setTotal(qb_product);
		DataTable dt = qb_product.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "productname", dt.getString(i, "productname"));
		}
		dga.bindData(dt);
	}

	public static void dg2DataBindProduct(DataGridAction dga) {
		String productcode = (String) dga.getParams().get("productcode");
		QueryBuilder qb_product = new QueryBuilder("select CustomCode as id,ProductName,PlanCode,CoverageYear,AgeIssue,Occupation,Gender,PaymentMethods from jdproductc ");
		if (StringUtil.isNotEmpty(productcode)) {
			qb_product.append("where CustomCode like '"
					+ productcode.trim() + "%'");
		}
		qb_product.append(" ORDER BY CustomCode ");
		qb_product.append(dga.getSortString());
		dga.setTotal(qb_product);
		DataTable dt = qb_product.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 *
	 * @Title: dg1DataBindCouponBatch
	 * @Description: TODO(初始化各个批次优惠券)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public static void dg1DataBindCouponBatch(DataGridAction dga) {
		String createuser = (String) dga.getParams().get("createuser");
		QueryBuilder qb_couponBatch = new QueryBuilder(
				"SELECT DISTINCT(a.batch)  AS  batch,a.couponsn,a.parvalue,a.payamount,a.direction,a.createuser,a.endtime,a.createdate FROM couponinfo a left join CouponApproveInfo b on a.batch = b.couponBatch WHERE a.STATUS ='0' and a.purpose ='1' and (a.activitysn =''  OR  a.activitysn IS  NULL)  AND FIND_IN_SET('"
						+ User.getUserName() + "',a.provideuser)!=0 and (b.approveStatus = '1' or b.approveStatus is null) ");
		if (StringUtil.isNotEmpty(createuser)) {
			QueryBuilder qb_user = new QueryBuilder(
					"select username from ZDUser  WHERE realname like '%"
							+ createuser + "%'");
			DataTable dt_user = qb_user.executeDataTable();
			String user = "";
			for (int i = 0; i < dt_user.getRowCount(); i++) {
				user += "'" + dt_user.get(i, 0) + "',";
			}
			if(StringUtil.isEmpty(user)){
				qb_couponBatch.append(" and a.createuser in ('')");
			}else{
				qb_couponBatch.append(" and a.createuser in ("+ user.substring(0, user.length() - 1) + ")");
			}
		}
		// 创建优惠券由新到旧的顺序
		qb_couponBatch.append(" GROUP BY a.batch ");
		qb_couponBatch.append(dga.getSortString());
		dga.setTotal(qb_couponBatch);
		DataTable dt = qb_couponBatch.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "batch", dt.getString(i, "batch"));
			dt.set(i, "couponsn", dt.getString(i, "couponsn"));
			dt.set(i, "parvalue", dt.getString(i, "parvalue"));
			dt.set(i, "payamount", dt.getString(i, "payamount"));
			dt.set(i, "direction", dt.getString(i, "direction"));
			QueryBuilder qb_user = new QueryBuilder(
					"Select realName from ZDUser  WHERE userName ='"
							+ dt.getString(i, "createuser") + "'");
			DataTable dt_user = qb_user.executeDataTable();
			dt.set(i, "createuser", dt_user.getDataRow(0).getString("realName"));
			dt.set(i, "endtime", dt.getString(i, "endtime"));
			dt.set(i, "createdate", dt.getString(i, "createdate"));
		}
		dga.bindData(dt);

	}
	/**
	 *
	 * @Title: isExist
	 * @Description: TODO(判断字符串是否有重复)
	 */
	public static boolean isExist(String tmp){
		tmp = tmp.replace(",,",",");
		String [] tmpchar = tmp.split(",");
		String str ="";
	    for (String strs : tmpchar) {//去除空的
		     if(!strs.equals("")){
		    	 str+=strs+",";
		     }
		 }
	    tmpchar = str.split(",");
		for (int i = 0; i < tmpchar.length; i++) {
			for (int j = i+1; j < tmpchar.length; j++) {
				if(tmpchar[i].equals(tmpchar[j])){
					return false;
					}
				}
			}
		return true;
		}


	/**
	 * @Title: compareDate
	 * @Description: TODO(判断日期大小)
	 */
	public static int compareDate(Date d1,Date d2){
        if (d1.getTime() > d2.getTime()) {
            //d1大
            return 1;
        } else if (d1.getTime() < d2.getTime()) {
           //d2大
            return -1;
        } else {//相等
            return 0;
        }
	}

	/**
	 * 检查未使用的优惠券是否够活动发放
	 *
	 * @param batch
	 * @param autoCreate
	 * @param couponnum
	 * @return
	 */
	private boolean checkCouponNum(String batch, String autoCreate, String couponnum) {
		QueryBuilder qb_cou = new QueryBuilder(
				"select count(id) as num,endtime  from couponinfo where batch=? and status ='0' ");
		qb_cou.add(batch);
		DataTable qb_dt=qb_cou.executeDataTable();
		String endtime_coupon = qb_dt.getString(0, 1);
		//非自动生成优惠券的活动
		if ("1".equals(autoCreate)) {
			String num = qb_dt.getString(0, 0);
			if (Integer.parseInt(couponnum) > Integer.parseInt(num)) {
				Response.setStatus(0);
				Response.setMessage("该批次："+batch+"的“未使用”状态的优惠券数量不足" + couponnum+ "个！");
				return false;
			}
		} else {
			couponnum = "0";
		}
		//优惠券的截止时间不能小于活动的截止时间，保证活动期间优惠券没有失效
		if (DateUtil .compare(endtime_coupon, Request.getString("endDate") + " "
								+ Request.getString("endTime"),
						"yyyy-MM-dd HH:mm:ss") < 0) {
			Response.setStatus(0);
			Response.setMessage("该批次："+batch+"的优惠券截止时间小于活动的截止时间，请选择其他批次优惠券！");
			return false;
		}

		return true;
	}

	/**
	 * 更新优惠券的活动编号与之关联
	 *
	 * @param batch
	 * @param couponnum
	 * @param autoCreate
	 * @param activitysn
	 * @return
	 */
	private boolean updateCoupon(String batch, String couponnum, String autoCreate, String activitysn) {
		// 更新优惠券的活动编号与之关联
		String str_str = "";
		//非自动生成优惠券的活动
		if ("1".equals(autoCreate)) {
			str_str = "limit 0," + couponnum;
		}
		QueryBuilder qb = new QueryBuilder(
				"select couponsn from couponinfo where batch='" + batch
						+ "'  and status ='0' " + str_str);
		DataTable dt = qb.executeDataTable();
		String str_couponsn = "(";
		for (int i = 0; i < dt.getRowCount(); i++) {
			str_couponsn += "'" + dt.getString(i, 0) + "',";
		}
		str_couponsn = str_couponsn.substring(0,
				str_couponsn.length() - 1) + ")";
		// 选定数量的优惠券的活动编号更新,真正发放人更新，状态依然为”未使用“
		QueryBuilder qb_coupon = new QueryBuilder(
				"update couponinfo set activitysn = '"
						+ activitysn
						+ "' ,realProvideUser='" + User.getUserName()
						+ "'   where batch= '" + batch
						+ "'  and couponsn in " + str_couponsn);
		int row = qb_coupon.executeNoQuery();
		// 剩余数量的优惠券全部删除
		QueryBuilder qb_delete = new QueryBuilder(
				"update couponinfo set activitysn = '"
						+ activitysn
						+ "'  ,status='6' where batch= '" + batch
						+ "'  and couponsn not in " + str_couponsn);
		qb_delete.executeNoQuery();
		// 判断更新的优惠券条数与输入的数量是否一致
		if ("1".equals(autoCreate)) {
			if (row != Integer.parseInt(couponnum)) {
				Response.setStatus(0);
				Response.setMessage("更新的优惠券数目与输入的数量不一致,优惠券批次："+batch);
				return false;
			}
		}

		return true;
	}

    /**
     * 推荐有礼活动上传图片，图片位置下拉框初始化
     *
     * @param params
     * @return
     */
    public static Mapx initUploadPictureDialog(Mapx params) {
        params.put("ActivityImgLocation", HtmlUtil.codeToOptions("Activity.ImgLocation", false));
        return params;
    }

    /**
     * 上传推荐有礼活动页面的图片
     *
     */
    public void uploadPicture() {
        String ServerPath = Config.getContextRealPath()+"/wwwroot/kxb";
        String imageName =  $V("image");
        String activitySn =  $V("activitySn");
        String base64 = $V("imgBase64");
        base64 = base64.substring(base64.indexOf(",")+1,base64.length());
        String path = "/upload/rewardActivityImg/";
        String location =  $V("location");
        int COUNT = new  QueryBuilder(" SELECT COUNT(imgPath) FROM ActivityImgUpload WHERE activitySn = '"+activitySn+"' AND location='"+ location +"'").executeInt();
        if(COUNT>0){
            Response.setStatus(0);
            Response.setMessage("同一个位置不可以重复上传图片！");
            return;
        }
        try {
            base64ToFile(base64, ServerPath, path, imageName, activitySn, location);
            Response.setStatus(1);
            Response.setMessage("上传成功");
        } catch (Exception e) {
            logger.error("上传推荐有礼活动页面的图片发生错误！", e);
            Response.setStatus(0);
            Response.setMessage("上传推荐有礼活动页面的图片发生错误！");
        }
    }

    /**
     * 上传图片
     *
     * @param base64
     * @param ServerPath
     * @param path
     * @param imageName
     * @param activitySn
     * @param location
     * @throws Exception
     */
    public void base64ToFile(String base64, String ServerPath ,String path,String imageName, String activitySn, String location)throws Exception{
        File dirname = new File(ServerPath+path);
        if (!dirname.isDirectory()){ //目录不存在
            dirname.mkdirs();
        }

        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] bytes1 = decoder.decodeBuffer(base64);
        OutputStream out=new FileOutputStream(ServerPath + path + imageName);
        out.write(bytes1);
        out.flush();
        out.close();
        OSSUploadFile.uploadFile(ServerPath + path + imageName);

        ActivityImgUploadSchema activityImgUploadSchema = new ActivityImgUploadSchema();
        activityImgUploadSchema.setID(CommonUtil.getUUID());
        activityImgUploadSchema.setactivitySn(activitySn);
        activityImgUploadSchema.setlocation(location);
        activityImgUploadSchema.setimageName(imageName);
        activityImgUploadSchema.setimgPath(Config.getValue("StaticResourcePath") + path + imageName);
        activityImgUploadSchema.setcreateDate(new Date());
        activityImgUploadSchema.insert();
    }

    /**
     * 推荐有礼活动图片列表
     *
     * @param dga
     */
    public void queryPicture(DataGridAction dga) {
        QueryBuilder qb = new QueryBuilder("SELECT a.id,(SELECT codeName FROM zdcode " +
                "WHERE codetype = 'Activity.ImgLocation' AND codevalue = a.location) AS location,a.imageName,a.imgPath " +
                "FROM ActivityImgUpload a WHERE a.activitySn = ? ORDER BY location ASC",  dga.getParams().getString("activitySn"));
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        dga.setTotal(qb);
        dga.bindData(dt);
    }

    /**
     * 删除推荐有礼活动的图片
     *
     */
    public void delActivityPicture() {
        String id = Request.getString("id");
        String ServerPath = Config.getContextRealPath()+"/wwwroot/kxb";
        String StaticResourcePath = Config.getValue("StaticResourcePath");
        String PictureUrl = "";
        ActivityImgUploadSchema fileSchema = new ActivityImgUploadSchema();
        fileSchema.setID(id);
        if (fileSchema.fill()) {
            PictureUrl=fileSchema.getimgPath();
            PictureUrl = PictureUrl.replace(StaticResourcePath,ServerPath);
            File file = new File(PictureUrl);
            if (file.exists()) {
                file.delete();
            }
            fileSchema.delete();
        } else {
            Response.setStatus(0);
            Response.setMessage("图片数据未查询到!");
        }
    }
}
