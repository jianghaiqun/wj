package com.sinosoft.points;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDOrder;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointsCalculate {
	private static final Logger logger = LoggerFactory.getLogger(PointsCalculate.class);

	/**
	 * 
	 * @Title: pointsManage
	 * @Description:
	 * @入参： type:处理类型（
	 *      00-查询所有积分规则信息；01-查询单条积分规则信息；02-送积分行为；03-使用积分行为；04-查询产品积分情况）
	 * @行为： act: 行为，即什么地方调用
	 * @param 04-类型，入参 产品 ProductList
	 * 
	 * @return Map 返回类型 {code:0/1,message}
	 * @author
	 * @throws Exception
	 */
	public Map<String, Object> pointsManage(String type, String act, Map<String, Object> param) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(IntegralConstant.STATUS, IntegralConstant.SUCCESS);
		// 查询全部积分情况
		if (IntegralConstant.POINT_ALL.equals(type)) {
			result.put(IntegralConstant.DATA, getRolePiontsList(null, null));
			return result;

		}
		// 查询产品积分情况
		else if (IntegralConstant.POINT_PRODUCT.equals(type)) {
			List<String> productlist = (List<String>) param.get("ProductList");
			if (productlist == null || productlist.size() == 0) {
				result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
				result.put(IntegralConstant.MESSAGE, "产品编码不能为空.");
				return result;

			}
			result.put(IntegralConstant.DATA, getProductList(productlist));
			return result;

		}

		if (StringUtil.isEmpty(type) || StringUtil.isEmpty(act)) {
			result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
			result.put(IntegralConstant.MESSAGE, "处理类型或行为不能为空." + type + "|" + act);
			return result;

		}
		// 通过行为查询规则记录
		DataTable act_dt = getRolePiontsList(act, param);

		if (act_dt == null || act_dt.getRowCount() == 0) {
			result.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
			result.put(IntegralConstant.MESSAGE, "处理类型" + act + "不存在规则.");
			return result;

		}

		// 查询单条积分规则信息
		if (IntegralConstant.POINT_SEARCH.equals(type)) {
			BigDecimal totalpoints = new BigDecimal("0");

			List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < act_dt.getRowCount(); i++) {
				Map<String, Object> data = new HashMap<String, Object>();

				totalpoints = totalpoints.add(new BigDecimal(act_dt.getString(i, "PointsNum")));

				data.put("PointsNum", act_dt.getString(i, "PointsNum"));
				data.put("PointDes", act_dt.getString(i, "PointDes"));

				data_list.add(data);
			}

			result.put(IntegralConstant.DATA, data_list);
			result.put(IntegralConstant.ACTION_POINTS, String.valueOf(totalpoints));
			return result;

		}

		try {
			// 遍历全部规则，处理不同业务逻辑
			for (int i = 0; i < act_dt.getRowCount(); i++) {
				String PointsGive = act_dt.getString(i, "PointsGive");

				String className = "com.sinosoft.points.PointsRule" + PointsGive;
				Class<?> mailParse = Class.forName(className);
				PointsRule mPoints = (PointsRule) mailParse.newInstance();
				mPoints.pointsDeal(act, param, act_dt.get(i));

			}

			return result;

		} catch (Exception e) {
			logger.error("积分处理异常.PointsCalculate - pointsManage 原因：" + e.getMessage(), e);
			throw e;
		}

	}

	/**
	 * 
	 * @Title: getRolePiontsList
	 * @Description: 查询所有积分规则信息
	 * @return DataTable 返回类型
	 * @author
	 */
	public static DataTable getRolePiontsList(String act, Map<String, Object> param) {

		StringBuffer act_sb = new StringBuffer();

		act_sb.append(" select ID,MemberAct,PointsGive,PointsNum,PointDes ,MemberGrade ");
		act_sb.append(" from SDPointRule ");
		act_sb.append(" where  startdate<='").append(PubFun.getCurrentDate()).append("' ");
		act_sb.append(" and  EndDate>='").append(PubFun.getCurrentDate()).append("' ");
		act_sb.append(" and  Status='Y'  ");
		if (StringUtil.isNotEmpty(act)) {
			act_sb.append("  and MemberAct=? ");

		}
		// PointsGive 参数筛选
		if (param != null && param.size() > 0) {
			String PointsGive = String.valueOf(param.get("PointsGive"));
			if (StringUtil.isNotEmpty(PointsGive)) {
				act_sb.append(" and  PointsGive='" + PointsGive + "' ");
			}
			String MemberGrade = String.valueOf(param.get("MemberGrade"));
			if (StringUtil.isNotEmpty(MemberGrade)) {
				act_sb.append(" and  MemberGrade='" + MemberGrade + "' ");
			}
		}
		QueryBuilder act_qb = new QueryBuilder(act_sb.toString());

		if (StringUtil.isNotEmpty(act)) {
			act_qb.add(act);

		}
		return act_qb.executeDataTable();
	}

	/**
	 * 
	 * @Description: 查询产品积分规则信息
	 * @return DataTable 返回类型
	 * @author
	 */
	public static DataTable getProductList(List<String> productlist) {

		StringBuffer act_sb = new StringBuffer();
		act_sb.append(" select ProductID,BuyPoints,GivePoints ");
		act_sb.append(" from SDProductPointRate ");
		act_sb.append(" where 1=1 and ProductID in (");

		for (int i = 0; i < productlist.size(); i++) {
			act_sb.append(" '").append(productlist.get(i)).append("' ");
			if (i != productlist.size() - 1) {
				act_sb.append(",");
			}
		}
		act_sb.append(" ) ");

		QueryBuilder act_qb = new QueryBuilder(act_sb.toString());
		DataTable dt = act_qb.executeDataTable();
		// 处理新上架产品，没有配置比例
		for (int i = 0; i < dt.getRowCount(); i++) {
			String BuyPoints = dt.getString(i, "BuyPoints");
			if (StringUtil.isEmpty(BuyPoints)) {
				dt.set(i, "BuyPoints", "0");
			}
			String GivePoints = dt.getString(i, "GivePoints");
			if (StringUtil.isEmpty(GivePoints)) {
				dt.set(i, "GivePoints", "0");
			}
		}
		return dt;
	}

	public Map<String, Object> getMemberUpgradeInfo(String memberid,
			List<SDOrder> list) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (list.size() < 0) {
			// 返回错误状态
			result.put("status", "1");
			logger.warn("支付成功后等级提示信息订单集合为空,memberid={}", memberid);
			return result;
		}
		result.put("status", "0");
		try {
			String ordersn = "in (";
			for (int i = 0; i < list.size(); i++) {
				ordersn = ordersn + "'" + list.get(i).getOrderSn() + "',";
				if (i == list.size() - 1) {
					ordersn = ordersn.substring(0, ordersn.length() - 1) + ")";
				}
			}
			// 计算获得总积分
			DataTable dt_points = new QueryBuilder(
					"select CONVERT(round(sum(point)),SIGNED) from  sdinformation where ordersn "
							+ ordersn).executeDataTable();
			if (dt_points.getRowCount() > 0) {
				if ("0".equals(dt_points.getString(0, 0))) {
					result.put("status", "1");
					logger.info("支付成功后等级提示信息订单查询总积分为0,memberid={}", memberid);
					return result;
				}
				result.put("Points", dt_points.getString(0, 0));
				result.put(
						"PointsValue",
						new BigDecimal(dt_points.getString(0, 0)).divide(
								new BigDecimal(Config
										.getValue("PointScalerUnit")), 1,
								BigDecimal.ROUND_HALF_UP).toString());
			} else {
				// 返回错误状态
				result.put("status", "1");
				logger.warn("支付成功后等级提示信息订单查询无结果集,memberid={}", memberid);
				return result;
			}
			result.put("GradeClass", "");
			memberSchema smemberSchema = new memberSchema();
			smemberSchema.setid(memberid);
			memberSet smemberSet = smemberSchema.query();
			if (smemberSet.size() > 0) {
				smemberSchema = smemberSet.get(0);
				if ("Y".equals(smemberSchema.getvipFlag())) {
					result.put("GradeClass", "vip_k vip_kvip");
				} else if ("K1".equals(smemberSchema.getgrade())) {
					result.put("GradeClass", "vip_k vip_k1");
				} else if ("K2".equals(smemberSchema.getgrade())) {
					result.put("GradeClass", "vip_k vip_k2");
				} else {
					result.put("GradeClass", "vip_k vip_no");
				}
			}

			return result;
		} catch (Exception e) {
			// 返回错误状态
			result.put("status", "1");
			logger.error(e.getMessage(), e);
			return result;
		}
	}
	
	/**
	 * 
	 * @Title: getMemberUpgradeInfo
	 * @Description:支付成功后等级提示信息
	 * @入参： memberid:会员id;list：订单集合;param:参数集合（暂时无用）
	 * @return Map 返回类型 { status-状态:0【正常】、1【异常】; Points-此次购买所获赠的积分数
	 *         Upgrade-此次购买之后是否能够升级:true【能够升级】、false【不能够升级】;
	 *         CurrentGrade-当前等级:k0、k1、k2、VIP;
	 *         CurrentGradeName-当前等级名称:k0会员、k1会员、k2会员、VIP会员;
	 *         HavedNextGrade-是否存在下一级：true【存在】、false【不存在】;
	 *         NextGrade-下一等级:k1、k2、vip; NextGradeName-下一等级:k1会员、k2会员、vip会员;
	 *         UpgradeCount-会员升级所需的订单购买数目:-1【购买订单数已达标】、N【升级所需的订单购买数目】;
	 *         UpgradeAmount-会员升级所需的购买金额:-1【购买金额已达标】、N【升级所需的购买金额】; }
	 * @author
	 * @throws Exception
	 */
	public Map<String, Object> getMemberUpgradeInfo(String memberid, List<SDOrder> list, Map<String, Object> param)
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		if (list.size() < 0) {
			// 返回错误状态
			result.put("status", "1");
			logger.warn("支付成功后等级提示信息订单集合为空,memberid={}", memberid);
			return result;
		}
		try {
			memberSchema smemberSchema = new memberSchema();
			smemberSchema.setid(memberid);
			memberSet smemberSet = smemberSchema.query();
			if (smemberSet.size() > 0) {
				// 查询会员等级记录
				DataTable grade_dt = new QueryBuilder(
						"SELECT gradecode,gradename,grade,pregradecode,ordercount,sumprem FROM membergrade  ORDER BY grade DESC ")
						.executeDataTable();
				String vip = "";
				String vipName = "";
				String vipOrdercount = "";
				String vipsumprem = "";
				if (grade_dt.getRowCount() > 0) {
					vip = grade_dt.getString(0, "gradecode");
					vipName = grade_dt.getString(0, "gradename");
					vipOrdercount = grade_dt.getString(0, "ordercount");
					vipsumprem = grade_dt.getString(0, "sumprem");
				} else {
					logger.warn("VIP会员级别没有相关记录！");
					result.put("status", "0");
					return result;
				}
				smemberSchema = smemberSet.get(0);
				// 此次支付总金额
				BigDecimal total = new BigDecimal("0");
				String ordersn = "in (";
				for (int i = 0; i < list.size(); i++) {
					total = total.add(new BigDecimal(list.get(i).getPayPrice()));
					ordersn = ordersn + "'" + list.get(i).getOrderSn() + "',";
					if (i == list.size() - 1) {
						ordersn = ordersn.substring(0, ordersn.length() - 1) + ")";
					}
				}
				// 计算获得总积分
				DataTable dt_points = new QueryBuilder(
						"select CONVERT(round(sum(point)),SIGNED) from  sdinformation where ordersn " + ordersn)
						.executeDataTable();
				if (dt_points.getRowCount() > 0) {
					result.put("Points", dt_points.getString(0, 0));
				} else {
					// 返回错误状态
					result.put("status", "1");
					logger.info("支付成功后等级提示信息订单查询总积分为0,memberid={}", memberid);
					return result;
				}
				String vipFlag = smemberSchema.getvipFlag();
				// VIP会员
				if ("Y".equals(vipFlag)) {
					result.put("status", "0");
					result.put("Upgrade", "false");
					result.put("CurrentGrade", vip);
					result.put("CurrentGradeName", vipName);
					result.put("HavedNextGrade", "false");
					return result;
				} else {
					// 会员等级
					String grade = smemberSchema.getgrade();
					// 会员等级名称
					String gradeName = "";
					// 会员下一等级
					String nextGradeCode = "";
					// 下一等级名称
					String nextgradecodeName = "";
					// 会员当前购买订单数
					BigDecimal buyCount = new BigDecimal(smemberSchema.getbuyCount());
					// 会员当前购买金额
					BigDecimal consumeAmount = new BigDecimal(StringUtil.isNullToZero(smemberSchema.getconsumeAmount()));
					// 等级需要购买订单数
					BigDecimal nextOrdercount = null;
					// 购买金额
					BigDecimal nextSumprem = null;
					// 遍历会员级别,匹配当前会员的下一级别及当前会员的等级名称
					for (int i = 0; i < grade_dt.getRowCount(); i++) {
						String gradecode = grade_dt.getString(i, "gradecode");
						if (gradecode.equals(grade)) {
							gradeName = grade_dt.getString(i, "gradename");
						}
						String precode = grade_dt.getString(i, "pregradecode");
						if (precode.equals(grade)) {
							nextGradeCode = grade_dt.getString(i, "gradecode");
							nextgradecodeName = grade_dt.getString(i, "gradename");
							if (StringUtil.isEmpty(grade_dt.getString(i, "ordercount"))) {
								nextOrdercount = new BigDecimal("0");
							} else {
								nextOrdercount = new BigDecimal(grade_dt.getString(i, "ordercount"));
							}
							if (StringUtil.isEmpty(grade_dt.getString(i, "sumprem"))) {
								nextSumprem = new BigDecimal("0");
							} else {
								nextSumprem = new BigDecimal(grade_dt.getString(i, "sumprem"));
							}
						}
					}
					// 存在下一级别（除vip）
					if (StringUtil.isNotEmpty(nextGradeCode)) {
						result.put("HavedNextGrade", "true");
						// 此次购买之后的累计金额
						consumeAmount = consumeAmount.add(total);
						// 此次购买之后的累计次数
						buyCount = buyCount.add(new BigDecimal("1"));
						// 购买订单数
						if (nextOrdercount.compareTo(buyCount) <= 0) {
							result.put("UpgradeCount", "-1");
						} else {
							result.put("UpgradeCount", nextOrdercount.subtract(buyCount));
							// result.put("UpgradeCount",nextOrdercount.subtract(buyCount).intValue());
						}
						// 购买金额
						if (nextSumprem.compareTo(consumeAmount) <= 0) {
							result.put("UpgradeAmount", "-1");
						} else {
							result.put("UpgradeAmount", nextSumprem.subtract(consumeAmount));
							// result.put("UpgradeAmount",nextSumprem.subtract(consumeAmount));
						}
						// 该订单的保单生效后会员等级达到提升条件
						if ((buyCount.compareTo(nextOrdercount) != -1) && (consumeAmount.compareTo(nextSumprem) != -1)) {
							result.put("Upgrade", "true");
						} else {// 保单生效后会员等级无变化
							result.put("Upgrade", "false");
						}
						result.put("CurrentGrade", grade);
						result.put("CurrentGradeName", gradeName);
						result.put("NextGrade", nextGradeCode);
						result.put("NextGradeName", nextgradecodeName);
						// 返回正确状态
						result.put("status", "0");
					} else {// 已经是顶级（除vip）
						result.put("status", "0");
						result.put("Upgrade", "true");
						result.put("CurrentGrade", grade);
						result.put("CurrentGradeName", gradeName);
						// 已经是顶级（除vip）,则推荐下一级是vip
						result.put("HavedNextGrade", "true");
						result.put("NextGrade", vip);
						result.put("NextGradeName", vipName);
						result.put("UpgradeCount", vipOrdercount);
						result.put("UpgradeAmount", vipsumprem);
					}
				}
			} else {
				// 返回错误状态
				result.put("status", "1");
			}
			return result;
		} catch (Exception e) {
			// 返回错误状态
			result.put("status", "1");
			logger.error(e.getMessage(), e);
			return result;
		}
	}

	/**
	 * 
	 * sendWarnMail:(发送礼品兑换告警邮件). <br/>
	 *
	 * @author 郭斌
	 */
	public void sendWarnMail(Member mem, String GiftName, int point, String giftid ) {
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("membername",
					(StringUtil.isEmpty(mem.getEmail()) ? "-" : mem.getEmail()) + "/" +
					(StringUtil.isEmpty(mem.getMobileNO()) ? "-" : mem.getMobileNO()));
			data.put("giftname", GiftName);
			data.put("giftid", giftid);
			data.put("point", point);
			String toEmail = Config.getValue("BuyIntegralWarnMail");
			if(StringUtil.isNotEmpty(toEmail)){
				ActionUtil.sendMail("wj00207", toEmail, data);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
