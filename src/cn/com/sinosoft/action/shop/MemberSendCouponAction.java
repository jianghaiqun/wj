package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDCouponInfoSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("shop")
public class MemberSendCouponAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4566799038620236384L;

	/**
	 * 赠券活动处理
	 * 
	 * @return
	 */
	public String sendCouponDeal() {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		String josn = request.getParameter("callback");
		// 验证是否登录
		Member member = getLoginMember();
		if (member == null) {
			map.put(STATUS, "notLogin");
			return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
		}
		String id = member.getId();
		memberSchema memberSchemas = new memberSchema();
		memberSchemas.setid(id);
		memberSet memberSets = memberSchemas.query();
		if ((null == memberSets) || (0 == memberSets.size())) {
			map.put(STATUS, "notLogin");
			return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
		}

		// 取得活动号
		String activitysns = request.getParameter("activitysn");
		
		if (StringUtil.isEmpty(activitysns)) {
			addErrMessage(map, "请选择活动！");
			return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
		}
		
		//多个活动号
		String acitvitysnArr[] = new String[]{activitysns};
		if(activitysns.indexOf(",")!=-1){
			acitvitysnArr = activitysns.split(",");
		}
		for(int i=0;i<acitvitysnArr.length;i++){
			
			String activitysn = acitvitysnArr[i];
			
			// 每个用户仅能领取一次
			String checkGetCouponSql = "select count(1) from couponinfo "
					+ "where status in ('1','2','3','5') and activitysn = ? and memberid = ? ";
			QueryBuilder checkGetCouponQb = new QueryBuilder(checkGetCouponSql,
					activitysn, id);
			if (checkGetCouponQb.executeInt() == 1) {
				addErrMessage(map, "您已经领取过优惠券！");
				return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
			}

			// 校验活动号数据库中是否存在
			String checkActiSnSql = "select status, batch, "
					+ "if (UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s')) "
					+ "<= UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')), "
					+ "'ok', 'err') as startStatus, "
					+ "if (UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) "
					+ ">= UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')), "
					+ "'ok', 'err')  as endStatus "
					+ "from sdcouponactivityinfo where activitysn=? ";
			QueryBuilder checkActiSnQb = new QueryBuilder(checkActiSnSql,
					activitysn);

			DataTable dt = checkActiSnQb.executeDataTable();
			int count = dt.getRowCount();
			if (count == 0) {
				addErrMessage(map, "当前活动不存在，不能进行领取！");
				return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
			} else if (count > 1) {
				addErrMessage(map, "当前活动的活动号不唯一，不能进行领取！");
				return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
			}
			// 活动状态
			String status = dt.getString(0, 0);
			// 优惠券批次号
			String batch = dt.getString(0, 1);
			// 活动起始时间状态
			String startStatus = dt.getString(0, 2);
			// 活动结束时间状态
			String endStatus = dt.getString(0, 3);

			// 活动的校验
			if (!checkActivity(status, startStatus, endStatus, map)) {
				return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
			}

			// 关联的优惠券为空的情况
			if (StringUtil.isEmpty(batch)) {
				addErrMessage(map, "当前活动未关联优惠券，不能进行领取！");
				return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
			}

			// 用优惠券批次号和活动号取得优惠券信息
			HashMap<String, String> couponMap = getCouponInfo(batch, activitysn);
			// 优惠券信息不存在的情况
			if ("no".equals(couponMap.get("IsExist"))) {
				addErrMessage(map, "当前活动关联的优惠券批次不存在，不能进行领取！");
				return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
			}

			// 发放优惠券并发邮件或短信
			HashMap<String, String> sendResultMap = sendCoupon(memberSets.get(0)
					.getemail(), memberSets.get(0).getmobileNO(),
					couponMap.get("id"), couponMap.get("status"), id);
			// 发放成功
			if (sendResultMap.isEmpty()) {
				map.put(STATUS, "Y");
				map.put(MESSAGE, "领券成功，可进入会员中心查看!");
			} else {
				// 发放失败 记录错误信息
				map = sendResultMap;
				ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
			}
			
		
			
		}
		
		return ajaxHtml(josn+"("+JSONObject.fromObject(map).toString()+");");
	}
	
	/**
	 * 赠券活动处理
	 * 
	 * @return
	 */
	public HashMap<String, String> sendCouponDeal(String activitysn) {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		// 验证是否登录
		String id = (String) request.getSession().getAttribute(
				Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(id)) {
			map.put(STATUS, "notLogin");
			return map;
		}

		memberSchema memberSchemas = new memberSchema();
		memberSchemas.setid(id);
		memberSet memberSets = memberSchemas.query();
		if ((null == memberSets) || (0 == memberSets.size())) {
			map.put(STATUS, "notLogin");
			return map;
		}

		// 每个用户仅能领取2次
		String checkGetCouponSql = "select count(1) from couponinfo "
				+ "where status in ('1','2','3','5') and activitysn = ? and memberid = ? " +
				" AND createdate > DATE_FORMAT(NOW(),'%y-%m-%d')";
		QueryBuilder checkGetCouponQb = new QueryBuilder(checkGetCouponSql,
				activitysn, id);
		if (checkGetCouponQb.executeInt() == 2) {
			map.put("text", "您已经领取过优惠券！");
			logger.info("您已经领取过优惠券！");
			map.put(STATUS, "N");
			return map;
		}

		// 校验活动号数据库中是否存在
		String checkActiSnSql = "select status, batch, "
				+ "if (UNIX_TIMESTAMP(DATE_FORMAT(starttime,'%Y-%m-%d %H:%i:%s')) "
				+ "<= UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')), "
				+ "'ok', 'err') as startStatus, "
				+ "if (UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s')) "
				+ ">= UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')), "
				+ "'ok', 'err')  as endStatus "
				+ "from sdcouponactivityinfo where activitysn=? ";
		QueryBuilder checkActiSnQb = new QueryBuilder(checkActiSnSql,
				activitysn);

		DataTable dt = checkActiSnQb.executeDataTable();
		int count = dt.getRowCount();
		if (count == 0) {
			map.put("text", "当前活动不存在，不能进行领取！");
			logger.info("当前活动不存在，不能进行领取！");
			map.put(STATUS, "N");
			return map;
		} else if (count > 1) {
			map.put("text", "当前活动的活动号不唯一，不能进行领取！");
			logger.info("当前活动的活动号不唯一，不能进行领取！");
			map.put(STATUS, "N");
			return map;
		}
		// 活动状态
		String status = dt.getString(0, 0);
		// 优惠券批次号
		String batch = dt.getString(0, 1);
		// 活动起始时间状态
		String startStatus = dt.getString(0, 2);
		// 活动结束时间状态
		String endStatus = dt.getString(0, 3);

		// 活动的校验
		if (!checkActivity(status, startStatus, endStatus, map)) {
			map.put(STATUS, "N");
			return map;
		}

		// 关联的优惠券为空的情况
		if (StringUtil.isEmpty(batch)) {
			map.put("text", "当前活动未关联优惠券，不能进行领取！");
			logger.info("当前活动未关联优惠券，不能进行领取！");
			map.put(STATUS, "N");
			return map;
		}

		// 用优惠券批次号和活动号取得优惠券信息
		HashMap<String, String> couponMap = getCouponInfo(batch, activitysn);
		// 优惠券信息不存在的情况
		if ("no".equals(couponMap.get("IsExist"))) {
			map.put("text", "当前活动关联的优惠券批次不存在，不能进行领取！");
			logger.info("当前活动关联的优惠券批次不存在，不能进行领取！");
			map.put(STATUS, "N");
			return map;
		}

		// 发放优惠券并发邮件或短信
		HashMap<String, String> sendResultMap = sendCoupon(memberSets.get(0)
				.getemail(), memberSets.get(0).getmobileNO(),
				couponMap.get("id"), couponMap.get("status"), id);
		// 发放成功
		if (sendResultMap.isEmpty()) {
			map.put(STATUS, "Y");
			map.put(MESSAGE, "领券成功，可进入会员中心查看!");
		} else {
			// 发放失败 记录错误信息
			map = sendResultMap;
		}

		return map;
	}

	/**
	 * 活动的校验
	 * 
	 * @param status
	 *            活动状态
	 * @param startStatus
	 *            起始时间状态
	 * @param endStatus
	 *            结束时间状态
	 * @param map
	 *            记录错误
	 * @return
	 */
	private boolean checkActivity(String status, String startStatus,
			String endStatus, HashMap<String, String> map) {
		boolean result = false;
		// 待发布的状态
		if ("0".equals(status)) {
			addErrMessage(map, "当前活动未上线，不能进行领取！");

			// 已删除的状态
		} else if ("1".equals(status)) {
			addErrMessage(map, "当前活动不存在，不能进行领取！");

			// 暂停的状态
		} else if ("2".equals(status)) {
			addErrMessage(map, "当前活动已暂停，不能进行领取！");

			// 已发布的状态
		} else if ("3".equals(status)) {
			if ("err".equals(startStatus)) {
				addErrMessage(map, "当前活动未开始，不能进行领取！");

			} else if ("err".equals(endStatus)) {
				addErrMessage(map, "当前活动已经结束，不能进行领取！");

			} else {
				result = true;
			}

			// 已过期的状态
		} else if ("4".equals(status)) {
			addErrMessage(map, "当前活动已经结束，不能进行领取！！");

			// 其它 错误状态
		} else {
			logger.warn("选择的活动状态(status:{})错误！", status);
			addErrMessage(map, "当前活动不存在，不能进行领取！");
		}

		return result;
	}

	/**
	 * 用优惠券批次号和活动号取得优惠券信息
	 * 
	 * @param batch
	 *            优惠券批次号
	 * @param activitysn
	 *            活动号
	 * @return
	 */
	private HashMap<String, String> getCouponInfo(String batch,
			String activitysn) {
		HashMap<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select id, status from couponinfo where batch=? "
						+ "and activitysn = ? order by status asc limit 0,1 ",
				batch, activitysn);
		DataTable dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() == 0) {
			map.put("IsExist", "no");
		} else {
			map.put("id", dt.getString(0, 0));
			map.put("status", dt.getString(0, 1));
		}
		return map;
	}

	/**
	 * 发放优惠券并发邮件或短信
	 * 
	 * @param mail
	 *            邮箱地址
	 * @param mobile
	 *            手机号
	 * @param id
	 *            优惠券ID
	 * @param status
	 *            优惠券状态
	 * @param memberId
	 *            会员ID
	 * @return 发放结果
	 */
	private HashMap<String, String> sendCoupon(String mail, String mobile,
			String id, String status, String memberId) {
		HashMap<String, String> map = new HashMap<String, String>();

		SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
		sdcouponschema.setId(id);
		Transaction trans = new Transaction();
		// 查询
		if (sdcouponschema.fill()) {
			// 数据库操作方式 1：插入 2:更新
			int operationType = 2;

			// 没有未使用的优惠券的情况 生成一张优惠券
			if (!("0".equals(status))) {
				operationType = 1;
				// 生成优惠劵ID
				String year = DateUtil.getCurrentDateTime("yyyy");
				String couponId = year + NoUtil.getMaxNo("CouponSn", 12);
				// 优惠券ID值
				sdcouponschema.setId(couponId);
				// 优惠券号
				sdcouponschema.setCouponSn(DigestUtils.md5Hex(couponId));
				// 创建时间
				sdcouponschema.setCreateDate(new Date());
				// 创建者
				sdcouponschema.setCreateUser("admin");
			}
			// 状态2已发放
			sdcouponschema.setStatus("2");
			// 发放时间
			sdcouponschema.setProp2(DateUtil.getCurrentDateTime());
			// 将会员id关联到优惠券表中
			sdcouponschema.setMemberId(memberId);
			// 邮箱
			sdcouponschema.setMail(mail);
			// 手机号
			sdcouponschema.setMobile(mobile);
			// 修改时间
			sdcouponschema.setModifyDate(new Date());
			// 修改者
			sdcouponschema.setModifyUser("admin");

			trans.add(sdcouponschema, operationType);

			if (trans.commit()) {
				// 邮箱地址合法的情况 发送邮件
				if (StringUtil.isMail(mail)) {
					sendMail(sdcouponschema, mail);

					// 手机号合法的情况 发送短信
				} else if (StringUtil.isMobileNO(mobile)) {
					sendMobileMess(sdcouponschema, mobile);

				} else {
					logger.warn("会员：{} 的邮箱和手机号不合法！", memberId);
				}

			} else {
				logger.error("赠券活动，数据库操作失败！会员id:{} 活动号：{}", memberId, sdcouponschema.getActivitySn());
				addErrMessage(map, "系统错误, 请重新点击活动领券！");
				return map;
			}
		}
		return map;
	}

	/**
	 * 发送邮件
	 * 
	 * @param sdcouponschema
	 *            优惠券信息
	 * @param mail
	 *            邮箱地址
	 */
	private void sendMail(SDCouponInfoSchema sdcouponschema, String mail) {
		// 如果需要信息提醒
		if (!"Y".equals(sdcouponschema.getRemindFlag())) {
			return;
		}
		// 公共参数
		Map<String, Object> data = new HashMap<String, Object>();

		Member member = new Member();
		
		// 优惠券说明
		try {
			data.put("direction", java.net.URLDecoder.decode(
					sdcouponschema.getDirection(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		// 优惠金额
		data.put("parValue", sdcouponschema.getParValue());
		// 优惠券开始时间
		String starttime = sdcouponschema.getStartTime().toString();
		// 优惠券结束时间
		String endtime = sdcouponschema.getEndTime().toString();
		
		// 开始时间
		data.put("starttime", starttime.substring(0, 10));
		// 结束时间
		data.put("endtime", endtime.substring(0, 10));
		// 优惠券编号
		data.put("couponsn", sdcouponschema.getCouponSn());
		data.put("url", Config.getValue("FrontServerContextPath")
				+ "/wj/shop/coupon!queryCoupon.action");
		// 邮箱地址
		member.setEmail(mail);
		data.put("Member", member);
		// 非折扣券
		if (StringUtil.isEmpty(sdcouponschema.getProp3()) || "01".equals(sdcouponschema.getProp3())) {
			// 优惠金额
			data.put("parValueShow", sdcouponschema.getParValue() + "元");

			if (ActionUtil.sendMail("wj00088", mail,data)) {
				logger.info("赠券活动，发送邮件成功");
			} else {
				logger.warn("会员{} 赠券活动，邮箱：{}发送邮件失败", sdcouponschema.getMemberId(), mail);
			}
		} else { // 折扣券
			// 优惠金额
			data.put("parValueShow", sdcouponschema.getProp4() + "折");

			if (ActionUtil.sendMail("wj00113",mail, data)) {
				logger.info("赠券活动，发送邮件成功");
			} else {
				logger.warn("会员{} 赠券活动，邮箱：{} 发送邮件失败", sdcouponschema.getMemberId(), mail);
			}
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param sdcouponschema
	 *            优惠券信息
	 * @param mobileNo
	 *            手机号
	 */
	private void sendMobileMess(SDCouponInfoSchema sdcouponschema,
			String mobileNo) {
        // 如果需要信息提醒
        if (!"Y".equals(sdcouponschema.getRemindFlag())) {
            return;
        }
		// 优惠券结束时间
		String endtime = String.valueOf(sdcouponschema.getEndTime());
		String month = endtime.substring(5, 7);
		if (month.startsWith("0")) {
			month = month.substring(1, 2);
		}
		String day = endtime.substring(8, 10);
		if (day.startsWith("0")) {
			day = day.substring(1, 2);
		}
		// 优惠券发放发送短信动作代码为wj00090
		String sendData = sdcouponschema.getParValue() + ";" + endtime.substring(0, 4) + "-" + month + "-" + day;
		if (ActionUtil.sendSms("wj00090", mobileNo, sendData)) {
			logger.info("赠券活动，发送短信成功");
		} else {
			logger.warn("会员{} 赠券活动，手机号：{}发送短信失败",sdcouponschema.getMemberId(), mobileNo);
		}
	}

	/**
	 * 记录错误信息
	 * 
	 * @param map
	 *            记录错误信息map
	 * @param message
	 *            错误信息
	 */
	private void addErrMessage(HashMap<String, String> map, String message) {
		map.put(STATUS, ERROR);
		map.put(MESSAGE, message);
	}
}
