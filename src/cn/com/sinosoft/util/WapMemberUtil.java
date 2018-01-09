package cn.com.sinosoft.util;

import cn.com.sinosoft.action.shop.MemberAction;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDRelationAppnt;
import cn.com.sinosoft.entity.SDRelationRecognizee;
import cn.com.sinosoft.entity.Wxbind;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.MemberRankService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.SDRelationAppntService;
import cn.com.sinosoft.service.SDRelationRecognizeeService;
import cn.com.sinosoft.service.WxbindService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.SDCouponInfoSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * wap会员接口工具类
 */
public class WapMemberUtil {
	private static final Logger logger = LoggerFactory.getLogger(WapMemberUtil.class);
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * wap站会员登录接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapLogin(Map<String, Object> parameters,
			MemberService memberService, HttpServletRequest req) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String loginName = (String) parameters.get("UserName");
		String passWord = (String) parameters.get("Password");

		if (StringUtil.isEmpty(loginName) || StringUtil.isEmpty(passWord)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0001");
			return resultMap;

		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0001");
		resultMap.put("STATYS", "true");

		try {
			GetDBdata db = new GetDBdata();
			// 去掉用户名登录的控制
			String[] temp = { loginName, loginName };
			String firstLogin = null;
			try {
				firstLogin = db
						.getOneValue(
								"select 'X' from member where (loginDate is null or loginDate = '') and (email=? or mobileNo=?)",
								temp);
			} catch (Exception e) {
				logger.error("wap站会员登录接口异常" + e.getMessage(), e);
			}

			// 不需要绑定
			Member loginMember = memberService
					.getMemberByLoginNameNoBinding(loginName);
			if (loginMember != null) {
				// 解除会员账户锁定
				if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
					Date lockedDate = loginMember.getLockedDate();
					Date now = new Date();
					Calendar c1 = Calendar.getInstance();
					c1.setTime(lockedDate);
					Calendar c2 = Calendar.getInstance();
					c2.setTime(now);
					if (c2.get(Calendar.DAY_OF_YEAR)
							- c1.get(Calendar.DAY_OF_YEAR) >= 1) {
						loginMember.setLoginFailureCount(0);
						loginMember.setIsAccountLocked("N");
						loginMember.setLockedDate(null);
						memberService.update(loginMember);
					}
				}
				if ("N".equalsIgnoreCase(loginMember.getIsAccountEnabled())) {
					String[] errorArr = { "MemberAction000009" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0001");
					return resultMap;
				}

				// 不需要绑定
				if (!memberService.verifyMemberNoNeedBinding(loginMember,
						passWord)) {
					String[] errorArr = { "MemberAction000010" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0001");
					return resultMap;
				}
			} else {
				String[] errorArr = { "MemberAction000011" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0001");
				return resultMap;
			}
			loginMember.setLoginFailureCount(0);
			loginMember.setLoginIp(req.getRemoteAddr());
			loginMember.setLoginDate(new Date());
			memberService.update(loginMember);

			/*
			 * 不加此逻辑判断，用户名返回为登录账户 if (loginMember.getUsername() != null &&
			 * !"".equals(loginMember.getUsername())) { loginName =
			 * loginMember.getUsername(); }
			 */

			// 调用横向接口
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Member", loginMember);
			try {
				ActionUtil.dealAction("wj00011", map, req);
			} catch (Exception e) {
				logger.error("wap站会员登录接口异常" + e.getMessage(), e);
			}

			Map<String, Object> results = new HashMap<String, Object>();
			// TODO 会员首次登陆处理逻辑
			if (firstLogin != null && "X".equals(firstLogin)) {
				results.put("UserName", loginName);
				results.put("Nickname", getNickname(loginMember));
			} else {
				results.put("UserName", loginName);
				results.put("Nickname", getNickname(loginMember));
			}
			results.put("MemberID", loginMember.getId());
			// add by wangej 20150709 会员等级
			results.put("MemberGrade", loginMember.getGrade());
			results.put("VipFlag", loginMember.getVipFlag());

			resultMap.put("RESULTS", results);
		} catch (Exception e) {
			logger.error("wap站会员登录接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0001");
			return resultMap;
		}

		return resultMap;

	}

	/**
	 * wap站会员注册接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapRegister(
			Map<String, Object> parameters, MemberService memberService,
			MemberRankService memberRankService, HttpServletRequest req,
			SystemConfig sc) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String registerUserName = (String) parameters.get("UserName");
		String passWord = (String) parameters.get("Password");
		// wap站推荐会员id add by wangej-20150515
		String rMemberID = StringUtil.noNull((String) parameters
				.get("RMemberID"));

		if (StringUtil.isEmpty(registerUserName)
				|| StringUtil.isEmpty(passWord)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0002");
			return resultMap;

		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0002");
		resultMap.put("STATYS", "true");

		Member member = new Member();

		try {
			// 邮箱注册
			if (StringUtil.isMail(registerUserName)) {
				try {
					if (memberService.isExistByMailbox(registerUserName)) {
						String[] errorArr = { "MemberAction000001" };
						resultMap = WapErrorUtil.dealErrorInfo(errorArr,
								"KXBJRT0002");
						return resultMap;
					}
				} catch (Exception e) {
					logger.error("wap站会员注册接口异常" + e.getMessage(), e);
					String[] errorArr = { "G000001" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0002");
					return resultMap;
				}
				member.setEmail(registerUserName);
				member.setRegisterType("0");// 表示邮箱注册
			}
			// 手机注册
			if (StringUtil.isMobileNO(registerUserName)) {
				try {
					if (memberService.isExistByMobileNO(registerUserName)) {
						String[] errorArr = { "MemberAction000002" };
						resultMap = WapErrorUtil.dealErrorInfo(errorArr,
								"KXBJRT0002");
						return resultMap;
					}
				} catch (Exception e) {
					logger.error("wap站会员注册接口异常" + e.getMessage(), e);
					String[] errorArr = { "G000001" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0002");
					return resultMap;
				}
				member.setMobileNO(registerUserName);
				member.setRegisterType("1");// 表示手机注册
			}

			if (!sc.getIsRegister()) {
				String[] errorArr = { "MemberAction000003" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0002");
				return resultMap;
			}

			int count = 0;
			/*
			 * if (!(member.getAddress() == null ||
			 * "".equals(member.getAddress()))) count++; if
			 * (!(member.getBirthday() == null || "".equals(member
			 * .getBirthday()))) count++;
			 */
			if (!(member.getEmail() == null || "".equals(member.getEmail())))
				count++;
			/*
			 * if (!(member.getFaxNO() == null || "".equals(member.getFaxNO())))
			 * count++; if (love != null) count++; if (!(member.getIDNO() ==
			 * null || "".equals(member.getIDNO()))) count++; if
			 * (!(member.getIDType() == null || "".equals(member.getIDType()) ||
			 * "-1" .equals(member.getIDType()))) count++; if
			 * (!(member.getIndustryType() == null || "".equals(member
			 * .getIndustryType()))) count++; if (!(member.getLocation() == null
			 * || "".equals(member .getLocation()))) count++; if
			 * (!(member.getMarriageStatus() == null ||
			 * "".equals(member.getMarriageStatus()) || "-1"
			 * .equals(member.getIDType()))) count++;
			 */
			if (!(member.getMobileNO() == null || "".equals(member
					.getMobileNO())))
				count++;
			/*
			 * if (!(member.getPersonalURL() == null || "".equals(member
			 * .getPersonalURL()))) count++; if (!(member.getPosition() == null
			 * || "".equals(member .getPosition()))) count++; if
			 * (!(member.getQQNO() == null || "".equals(member.getQQNO())))
			 * count++; if (!(member.getRealName() == null || "".equals(member
			 * .getRealName()))) count++; if (!(member.getSex() == null ||
			 * "".equals(member.getSex()))) count++; if
			 * (!(member.getTelephoneNO() == null || "".equals(member
			 * .getTelephoneNO()))) count++; if (!(member.getUsername() == null
			 * || "".equals(member .getUsername()))) count++; if
			 * (!(member.getVIPType() == null ||
			 * "".equalsIgnoreCase(member.getVIPType()) || "-1"
			 * .equals(member.getIDType()))) count++; if (!(member.getZipcode()
			 * == null || "".equals(member.getZipcode()))) count++;
			 */
			double a = (count / 20.0) * 100;
			DecimalFormat myformat = new DecimalFormat("#####0");
			String fullDegree = myformat.format(a) + "%";
			member.setFullDegree(fullDegree);

			member.setPassword(DigestUtils.md5Hex(passWord));
			member.setSafeQuestion(null);
			member.setSafeAnswer(null);
			member.setMemberRank(memberRankService.getDefaultMemberRank());
			member.setPoint(0);
			member.setDeposit(new BigDecimal("0"));
			member.setIsAccountEnabled("Y");
			member.setIsAccountLocked("N");
			member.setLoginFailureCount(0);
			member.setPasswordRecoverKey(null);
			member.setLockedDate(null);
			// TODO 注册会员时，不对登录时间进行设置
			// member.setLoginDate(new Date());
			member.setRegisterIp(req.getRemoteAddr());
			member.setLoginIp(req.getRemoteAddr());
			member.setMemberAttributeMap(null);
			member.setReceiverSet(null);
			member.setFavoriteProductSet(null);
			member.setUsedPoint(0);
			member.setCurrentValidatePoint(0);
			member.setExpiricalValue(0);
			member.setLoginDateAfterUngrade(new Date());
			// fhz 这里都没有验证
			member.setIsMobileNOBinding("N");
			member.setIsEmailBinding("N");
			// WAP注册会员等同于网站注册会员
			member.setVIPFrom("0");
			// 标志会员来自与WAP
			member.setFromWap("wap");
			// add by wangej-20150515 wap站推荐会员id&上次登录显示的积分
			// 给推荐人送积分的触发点在手机绑定成功后，现阶段只有pc支持手机绑定 ，所以 wap站 只需要存入推荐人id即可
			// 推荐会员id
			member.setRecommendMemId(rMemberID);
			// 上次登录显示的积分
			member.setPreLoginPoints(0);
			// add by wangej-2015617 会员等级注册后默认为普通会员
			member.setGrade("K0");
			member.setVipFlag("N");
			if(StringUtil.isEmpty(member.getEmail())){
				member.setEmail("");
			}
			if(StringUtil.isEmpty(member.getMobileNO())){
				member.setMobileNO("");
			}
			if(StringUtil.isEmpty(member.getmBindInfoForLogin())){
				BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
				bindInfoForLogin.setId("");
				member.setmBindInfoForLogin(bindInfoForLogin);
			}
			memberService.save(member);

			if ("0".equals(member.getRegisterType())
					&& member.getVIPFrom().equals("0")) {
				// 发送邮件日期
				member.setVerifyEmailSendDate(new Date());
				memberService.save(member);
				if (!sendVerifyEMail(member, req)) {
					String[] errorArr = { "MemberAction000004" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0002");
					return resultMap;
				}

				Map<String, Object> map = new HashMap<String, Object>();
				if (StringUtil.isNotEmpty(member.getId())) {
					try {
						Map<String,Object> mailParam = new HashMap<String,Object>();
						mailParam.put("memberId", member.getId());
						ActionUtil.sendMessage("wj0003", mailParam);
						ActionUtil.dealAction("wj0003", map, req);
					} catch (Exception e) {
						logger.error("wap站会员注册接口异常" + e.getMessage(), e);
					}
				}

			} else if ("1".equals(member.getRegisterType())
					&& member.getVIPFrom().equals("0")) {
				memberService.save(member);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Member", member);
				try {
					ActionUtil.sendSms("wj0004", member.getMobileNO(), "");
					ActionUtil.dealAction("wj0004", map, req);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			// add by wangej 20150606 注册送积分处理 begin
			if (StringUtil.isNotEmpty(member.getId())) {
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 注册送积分
					Map<String, Object> map1 = new HashMap<String, Object>();
					// 注册会员ID
					map1.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_REGISTER, map1);
				} catch (Exception e) {
					logger.error("wap站注册送积分异常！" + e.getMessage(), e);
				}
				// 触发活动
				QueryBuilder qb_activity = new QueryBuilder(
						"SELECT id from sdcouponactivityinfo  WHERE TYPE='0' AND STATUS='3' ");
				DataTable qt_activity = qb_activity.executeDataTable();
				if (qt_activity.getRowCount() == 1) {
					String mail = member.getEmail();
					String mobile = member.getMobileNO();
					provideCoupon(mail, mobile, member.getId());
				}
			}
			// add by wangej 20150606 注册送积分处理 end
			// 表示操作后注册
			/*
			 * if (member.getVIPFrom().equals("1")) { // if
			 * (codeType.equals("0")) { // Order order =
			 * orderService.getOrderByOrderSn(serialNO); //
			 * order.setMemberId(member.getId()); // orderService.update(order);
			 * // member.setCurrentValidatePoint(order.getPoint()); //
			 * memberService.update(member); // // } if (codeType.equals("1"))
			 * {// 车险一键报价 SDtargetInformation sdTargetInformation =
			 * sdTargetInformationService .get(serialNO);
			 * sdTargetInformation.setMemberId(member.getId());
			 * sdTargetInformationService.update(sdTargetInformation);
			 * ShowInsurance showInsurance = showInsuranceService
			 * .get(serialNO); showInsurance.setMemberId(member.getId());
			 * showInsuranceService.update(showInsurance);
			 * 
			 * }
			 * 
			 * }
			 */

			/*
			 * JdbcTemplateData jtd = new JdbcTemplateData(); String sql3 =
			 * "select codevalue from zdcode where codetype=? and parentcode=? order by codevalue asc"
			 * ; String[] sql3temp = { "member.Hobby", "hobby" }; List<Map>
			 * listhy; try { listhy = jtd.obtainData(sql3, sql3temp);
			 * Iterator<Map> it = listhy.iterator(); while (it.hasNext()) {
			 * MemberHobby mh = new MemberHobby(); mh.setCodeValue((String)
			 * it.next().get("CodeValue")); mh.setIsSelected("N");
			 * mh.setMember(member); memberHobbyService.save(mh); } } catch
			 * (Exception e) { e.printStackTrace(); }
			 */

			/*
			 * if (love != null && love.length > 0) { for (String s : love) {
			 * String sql5 =
			 * "update memberhobby set isselected='Y' where codevalue=? and  member_id=?"
			 * ; String[] sql5temp = { s, member.getId() }; JdbcTemplateData
			 * jtdss = new JdbcTemplateData(); jtdss.updateOrSaveOrDelete(sql5,
			 * sql5temp); } }
			 */

		} catch (Exception e) {
			logger.error("wap站会员注册接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0002");
			return resultMap;
		}

		return resultMap;
	}

	/**
	 * wap站修改密码接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapChangePassword(
			Map<String, Object> PARAMETERS, MemberService memberService) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String userName = (String) PARAMETERS.get("UserName");
		String oldPassword = (String) PARAMETERS.get("OldPassword");
		String newPassword = (String) PARAMETERS.get("NewPassword");

		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(oldPassword)
				|| StringUtil.isEmpty(newPassword)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0020");
			return resultMap;
		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0020");
		resultMap.put("STATYS", "true");

		try {
			Member persistent = memberService
					.getMemberByLoginNameNoBinding(userName);
			if (StringUtils.isNotEmpty(oldPassword)
					&& StringUtils.isNotEmpty(newPassword)) {
				String oldPasswordMd5 = DigestUtils.md5Hex(oldPassword);
				if (!StringUtils.equals(persistent.getPassword(),
						oldPasswordMd5)) {
					String[] errorArr = { "MemberAction000005" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0020");
					return resultMap;
				}
				String newPasswordMd5 = DigestUtils.md5Hex(newPassword);
				persistent.setPassword(newPasswordMd5);
			}
			// 密码安全问题相关设置
			/*
			 * if (StringUtils.isNotEmpty(member.getSafeQuestion()) &&
			 * StringUtils.isNotEmpty(member.getSafeAnswer())) {
			 * persistent.setSafeQuestion(member.getSafeQuestion());
			 * persistent.setSafeAnswer(member.getSafeAnswer()); }
			 */
			memberService.update(persistent);
		} catch (Exception e) {
			logger.error("wap站修改密码接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0020");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * wap站会员信息修改接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapUpdateUserBaseInfo(
			Map<String, Object> PARAMETERS, MemberService memberService,
			HttpServletRequest req) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String userName = (String) PARAMETERS.get("UserName");
		String unitID = (String) PARAMETERS.get("UnitID");

		if ((StringUtil.isEmpty(userName) && StringUtil.isEmpty(unitID))
				|| (StringUtil.isNotEmpty(userName) && StringUtil
						.isNotEmpty(unitID))) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0019");
			return resultMap;
		}

		String nickname = (String) PARAMETERS.get("Nickname");
		String personName = (String) PARAMETERS.get("PersonName");
		String gender = (String) PARAMETERS.get("Gender");
		String birthday = (String) PARAMETERS.get("Birthday");
		/*
		 * String mobile = (String)PARAMETERS.get("Mobile"); String email =
		 * (String)PARAMETERS.get("Email");
		 */

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0019");
		resultMap.put("STATYS", "true");

		int getSumPoints = 0; // add by wangej 20150519 修改资料获取的积分总和
		try {
			if (StringUtil.isNotEmpty(userName)) {

				Member oldmember = memberService
						.getMemberByLoginNameNoBinding(userName);

				// add by wangej 20150518 会员修改资料送积分 积分表流水处理 begin
				PointsCalculate PointsCalculate = new PointsCalculate();
				MemberAction memberAction = new MemberAction();
				memberAction.setMember(oldmember);
				String columns = "";
				int oldCurrentValidatePoint = 0;
				int newCurrentValidatePoint = 0;
				Map<String, Object> mapInner = new HashMap<String, Object>();

				if (StringUtil.isNotEmpty(personName)
						&& StringUtil.isEmpty(oldmember.getRealName())
						&& memberAction.hasUpdated("realName")) {
					columns += "realName,";
					mapInner = new HashMap<String, Object>();
					mapInner.put(IntegralConstant.MEM_ID, oldmember.getId());
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_NAME, mapInner);
				}
				if (StringUtil.isNotEmpty(gender)
						&& StringUtil.isEmpty(oldmember.getSex())
						&& memberAction.hasUpdated("sex")) {
					columns += "sex,";
					mapInner = new HashMap<String, Object>();
					mapInner.put(IntegralConstant.MEM_ID, oldmember.getId());
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_SEX, mapInner);
				}
				if (StringUtil.isNotEmpty(birthday)
						&& StringUtil.isEmpty(oldmember.getBirthday())
						&& memberAction.hasUpdated("birthday")) {
					columns += "birthday,";
					mapInner = new HashMap<String, Object>();
					mapInner.put(IntegralConstant.MEM_ID, oldmember.getId());
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_BIRTHDAY,
							mapInner);
				}
				columns += oldmember.getHasUpdate();

				// 取old积分
				oldCurrentValidatePoint = oldmember.getCurrentValidatePoint();
				// 取最新的member数据
				memberService.clear();
				oldmember = memberService.load(oldmember.getId());
				oldmember.setHasUpdate(columns);
				// add by wangej 20150518 会员修改资料送积分 积分表流水处理 end
				// mod by wangej 20150526 资料百分比计算错误 按照pc重新计算 begin
				int count = 0;
				if (StringUtil.isNotEmpty(birthday))
					count += 15;
				if (!(oldmember.getEmail() == null || "".equals(oldmember
						.getEmail())))
					count += 15;
				if (!(oldmember.getIDType() == null
						|| "".equals(oldmember.getIDType()) || "-1"
							.equals(oldmember.getIDType())))
					count += 4;
				if (!(oldmember.getIndustryType() == null || ""
						.equals(oldmember.getIndustryType())))
					count += 3;
				if (!(oldmember.getLocation() == null || "".equals(oldmember
						.getLocation())))
					count += 4;
				if (!(oldmember.getMarriageStatus() == null
						|| "".equals(oldmember.getMarriageStatus()) || "-1"
							.equals(oldmember.getMarriageStatus())))
					count += 4;
				if (!(oldmember.getMobileNO() == null || "".equals(oldmember
						.getMobileNO())))
					count += 15;
				if (!(oldmember.getPosition() == null || "".equals(oldmember
						.getPosition())))
					count += 2;
				if (StringUtil.isNotEmpty(personName))
					count += 15;
				if (StringUtil.isNotEmpty(gender))
					count += 15;
				if (!(oldmember.getVIPType() == null
						|| "".equalsIgnoreCase(oldmember.getVIPType()) || "-1"
							.equals(oldmember.getVIPType())))
					count += 4;
				if (!(oldmember.getZipcode() == null || "".equals(oldmember
						.getZipcode())))
					count += 4;
				String fullDegree = count + "%";
				// mod by wangej 20150526 资料百分比计算错误 按照pc重新计算 end
				oldmember.setFullDegree(fullDegree);
				oldmember.setBirthday(birthday);
				/*
				 * if(!StringUtil.noNull(email).equals(StringUtil.noNull(oldmember
				 * .getEmail()))){ if(memberService.isExistByMailbox(email)){
				 * String[] errorArr = {"MemberAction000007"}; resultMap =
				 * WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0019"); return
				 * resultMap; }else{ oldmember.setEmail(email); } }
				 * if(!StringUtil
				 * .noNull(mobile).equals(StringUtil.noNull(oldmember
				 * .getMobileNO()))){
				 * if(memberService.isExistByMobileNO(mobile)){ String[]
				 * errorArr = {"MemberAction000008"}; resultMap =
				 * WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0019"); return
				 * resultMap; }else{ oldmember.setMobileNO(mobile); } }
				 */
				oldmember.setRealName(personName);
				String[] whereString = { "Sex", gender };
				oldmember.setSex(getColumnValue("CodeValue", "CodeName",
						whereString));
				oldmember.setUsername(nickname);
				memberService.update(oldmember);
				// add by wangej 20150518 会员修改资料送积分 接口返回修改获取积分总和 benin
				newCurrentValidatePoint = oldmember.getCurrentValidatePoint();
				getSumPoints = newCurrentValidatePoint
						- oldCurrentValidatePoint;
				if (getSumPoints < 0) {
					getSumPoints = 0;
				}
				// add by wangej 20150518 会员修改资料送积分 接口返回修改获取积分总和 end
				Map<String, Object> map = new HashMap<String, Object>();
				Member newMember = memberService.load(oldmember.getId());
				map.put("Member", newMember);
				try {
					ActionUtil.dealAction("wj00012", map, req);
				} catch (Exception e) {
					logger.error("wap站会员信息修改接口异常" + e.getMessage(), e);
				}

			}
		} catch (Exception e) {
			logger.error("wap站会员信息修改接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0019");
			return resultMap;
		}
		resultMap.put("GetSumPoints", getSumPoints);
		return resultMap;
	}

	/**
	 * wap站找回密码接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapFetchPassword(
			Map<String, Object> PARAMETERS, MemberService memberService,
			HttpServletRequest req) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String rId = (String) PARAMETERS.get("UserName");
		String contacts = (String) PARAMETERS.get("Contacts");

		if (StringUtil.isEmpty(rId) || StringUtil.isEmpty(contacts)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0005");
			return resultMap;
		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0005");
		resultMap.put("STATYS", "true");

		try {
			Member rMember = memberService.getMemberByLoginNameNoBinding(rId);

			// add by wangej 20150807 处理邮箱或手机数据库无对应数据报空指针异常 20150807
			if (rMember == null) {
				String[] errorArray = { "MemberAction000006" };
				resultMap = WapErrorUtil
						.dealErrorInfo(errorArray, "KXBJRT0005");
				return resultMap;
			}

			String memberId = rMember.getId();

			/*
			 * String mobileNoOrEmail = rId; String sendType = null; Pattern
			 * pattern1 = Pattern.compile("^\\d{11}$"); Matcher matcher1 =
			 * pattern1.matcher(mobileNoOrEmail.trim()); if (matcher1.matches())
			 * { sendType = "1"; } else { sendType = "0"; }
			 */
			rMember.setRetrieveEmailvalid("0");
			// 邮件找回密码，发送连接
			if ("email".equals(contacts)) {
				if (!sendRetrieveMail(rMember, req)) {
					String[] errorArr = { "MemberAction000012" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0005");
					return resultMap;
				}
				rMember.setRetrieveEmailSendDate(new Date());
				memberService.update(rMember);

				Map<String, Object> results = new HashMap<String, Object>();
				// 返回会员ID
				results.put("MemberId", memberId);
				resultMap.put("RESULTS", results);
			}

			if ("mobile".equals(contacts)) {
				String svcode = VCFactory();
				if (!SVCToPhone(svcode, rId, "Y", req)) {
					String[] errorArr = { "MemberAction000013" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0005");
					return resultMap;
				} else {
					Map<String, Object> results = new HashMap<String, Object>();
					// 返回验证码
					results.put("Vcode", svcode);
					resultMap.put("RESULTS", results);
				}
			}
		} catch (Exception e) {
			logger.error("wap站找回密码接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0005");
			return resultMap;
		}

		return resultMap;
	}

	/**
	 * wap站会员信息接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapUserInfo(
			Map<String, Object> parameters, MemberService memberService) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String userName = (String) parameters.get("UserName");
		String unitID = (String) parameters.get("UnitID");
		String channelCode = "wap";
		if (StringUtil.isNotEmpty(String.valueOf(parameters.get("From")))) {
			channelCode = String.valueOf(parameters.get("From"));
		}
		if ((StringUtil.isEmpty(userName) && StringUtil.isEmpty(unitID))
				|| (StringUtil.isNotEmpty(userName) && StringUtil
						.isNotEmpty(unitID))) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0018");
			return resultMap;
		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0018");
		resultMap.put("STATYS", "true");

		try {
			if (StringUtil.isNotEmpty(userName)) {

				Map<String, Object> userInfoMap = new HashMap<String, Object>();

				Member member = memberService
						.getMemberByLoginNameNoBinding(userName);

				if (member == null) {
					String[] errorArr = { "MemberAction000006" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0018");
					return resultMap;
				}

				// 拥有的积分
				int IntegralSum = member.getCurrentValidatePoint();
				// 优惠券数量
				int DiscCouponCount = getCouponCountByMemberId(member.getId(),
						channelCode);

				userInfoMap.put("IntegralSum", IntegralSum);
				userInfoMap.put("DiscCouponCount", DiscCouponCount);
				userInfoMap.put("Nickname", member.getUsername());
				userInfoMap.put("PersonName", member.getRealName());
				String[] whereString = { "Sex", member.getSex() };
				userInfoMap.put("Gender",
						getColumnValue("CodeName", "CodeValue", whereString));
				userInfoMap.put("Birthday", member.getBirthday());
				userInfoMap.put("MemberID", member.getId());
				if (StringUtil.isNotEmpty(member.getHeadPicPath())) {
					userInfoMap.put("ImagePath", Config.getServerContext()
							+ "/" + member.getHeadPicPath());
				} else {
					userInfoMap.put("ImagePath", "");
				}
				// add by wangej 20150616 获取会员等级
				userInfoMap.put("MemberGrade", member.getGrade());
				userInfoMap.put("VipFlag", member.getVipFlag());

				JSONArray arrOrderInfo = new JSONArray();
				List<Map<String, Object>> orderInfoList = getOrderInfoMapForMember(member
						.getId());
				for (Map<String, Object> map : orderInfoList) {
					arrOrderInfo.put(map);
				}
				userInfoMap.put("Orders", arrOrderInfo);

				resultMap.put("RESULTS", userInfoMap);

			}

			// TODO 联合登录逻辑
			if (StringUtil.isNotEmpty(unitID)) {

			}
		} catch (Exception e) {
			logger.error("wap站会员信息接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0018");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * wap站会员信息接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapUserInfoById(
			Map<String, Object> parameters, MemberService memberService) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String memberID = (String) parameters.get("MemberID");

		if (StringUtil.isEmpty(memberID)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0037");
			return resultMap;
		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0037");
		resultMap.put("STATYS", "true");
		String channelCode = "wap";
		if (StringUtil.isNotEmpty(String.valueOf(parameters.get("From")))) {
			channelCode = String.valueOf(parameters.get("From"));
		}
		try {
			if (StringUtil.isNotEmpty(memberID)) {

				Map<String, Object> userInfoMap = new HashMap<String, Object>();

				Member member = memberService.get(memberID);

				if (member == null) {
					String[] errorArr = { "MemberAction000006" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0037");
					return resultMap;
				}

				// 拥有的积分
				int IntegralSum = member.getPoint();
				// 优惠券数量
				int DiscCouponCount = getCouponCountByMemberId(member.getId(),
						channelCode);

				userInfoMap.put("IntegralSum", IntegralSum);
				userInfoMap.put("DiscCouponCount", DiscCouponCount);
				userInfoMap.put("Nickname", getNickname(member));
				String account = String.valueOf(member.getEmail());
				if ("Y".equals(member.getIsMobileNOBinding())) {
					account = member.getMobileNO();
				}
				userInfoMap.put("Account", account);
				userInfoMap.put("PersonName", member.getRealName());
				String[] whereString = { "Sex", member.getSex() };
				userInfoMap.put("Gender",
						getColumnValue("CodeName", "CodeValue", whereString));
				userInfoMap.put("Birthday", member.getBirthday());
				userInfoMap.put("ImagePath", Config.getServerContext() + "/"
						+ member.getHeadPicPath());
				// add by wangej 20150616 获取会员等级
				userInfoMap.put("MemberGrade", member.getGrade());
				userInfoMap.put("VipFlag", member.getVipFlag());

				JSONArray arrOrderInfo = new JSONArray();
				List<Map<String, Object>> orderInfoList = getOrderInfoMapForMember(member
						.getId());
				for (Map<String, Object> map : orderInfoList) {
					arrOrderInfo.put(map);
				}
				userInfoMap.put("Orders", arrOrderInfo);

				resultMap.put("RESULTS", userInfoMap);

			}

		} catch (Exception e) {
			logger.error("wap站会员信息接口异常,memberid：" + memberID + ",message:"
					+ e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0037");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * wap站会员信息接口-微信openid
	 * 
	 * @return
	 */
	public static Map<String, Object> wapUserInfoByOpenID(
			Map<String, Object> parameters, MemberService memberService,
			WxbindService mWxbindService) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String openID = (String) parameters.get("OpenID");

		// 如果OpenID为空，则不返回错误信息，返回空结果
		if (StringUtil.isEmpty(openID)) {
			// String[] errorArr = {"G000002"};
			// resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0039");
			resultMap.put("RESULTS", null);
			resultMap.put("USER", "kxb");
			resultMap.put("REQUESTTYPE", "KXBJRT0039");
			resultMap.put("STATYS", "true");
			return resultMap;
		}
		String channelCode = "wap";
		if (StringUtil.isNotEmpty(String.valueOf(parameters.get("From")))) {
			channelCode = String.valueOf(parameters.get("From"));
		}
		String account = "";
		Wxbind tWxbind = mWxbindService.getWxbindByOpenID(openID);
		if (tWxbind != null) {
			account = tWxbind.getMemAccount();
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0039");
		resultMap.put("STATYS", "true");

		try {
			if (StringUtil.isNotEmpty(account)) {

				Map<String, Object> userInfoMap = new HashMap<String, Object>();

				Member member = memberService
						.getMemberByLoginNameNoBinding(account);

				if (member == null) {
					String[] errorArr = { "MemberAction000006" };
					resultMap = WapErrorUtil.dealErrorInfo(errorArr,
							"KXBJRT0039");
					return resultMap;
				}

				// 拥有的积分
				int IntegralSum = member.getPoint();
				// 优惠券数量
				int DiscCouponCount = getCouponCountByMemberId(member.getId(),
						channelCode);

				userInfoMap.put("IntegralSum", IntegralSum);
				userInfoMap.put("DiscCouponCount", DiscCouponCount);
				userInfoMap.put("Nickname", getNickname(member));
				userInfoMap.put("PersonName", member.getRealName());
				String[] whereString = { "Sex", member.getSex() };
				userInfoMap.put("Gender",
						getColumnValue("CodeName", "CodeValue", whereString));
				userInfoMap.put("Birthday", member.getBirthday());
				userInfoMap.put("Account", account);
				userInfoMap.put("ImagePath", Config.getServerContext() + "/"
						+ member.getHeadPicPath());
				resultMap.put("RESULTS", userInfoMap);

			} else {
				String[] errorArr = { "MemberAction000016" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0039");
				return resultMap;
			}

		} catch (Exception e) {
			logger.error("wap站会员信息接口-微信openid异常,openID：" + openID
					+ ",message:" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0039");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * wap站会员信息接口-微信openid
	 * 
	 * @return
	 */
	public static Map<String, Object> wapCheckLogin(
			Map<String, Object> parameters, MemberService memberService,
			WxbindService mWxbindService) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String openID = (String) parameters.get("OpenID");

		if (StringUtil.isEmpty(openID)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0040");
			return resultMap;
		}

		try {
			Map<String, Object> userInfoMap = new HashMap<String, Object>();
			Wxbind tWxbind = mWxbindService.getWxbindByOpenID(openID);
			if (tWxbind == null) {
				userInfoMap.put("ISBIND", "false");
			} else {
				userInfoMap.put("ISBIND", "true");
			}
			resultMap.put("RESULTS", userInfoMap);
			resultMap.put("USER", "kxb");
			resultMap.put("REQUESTTYPE", "KXBJRT0040");
			resultMap.put("STATYS", "true");
		} catch (Exception e) {
			logger.error("wap站会员信息接口-微信openid异常,openID：" + openID
					+ ",message:" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0040");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * wap站微信绑定会员信息接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapWeiXinBind(
			Map<String, Object> parameters, MemberService mMemberService,
			WxbindService mWxbindService) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String openID = (String) parameters.get("OpenID");
		String account = (String) parameters.get("Account");

		if (StringUtil.isEmpty(openID) || StringUtil.isEmpty(account)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0038");
			return resultMap;
		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0038");
		resultMap.put("STATYS", "true");
		Member member = mMemberService.getMemberByLoginNameNoBinding(account);

		if (member == null) {
			String[] errorArr = { "MemberAction000006" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0038");
			return resultMap;
		}
		String channelCode = "wap";
		if (StringUtil.isNotEmpty(String.valueOf(parameters.get("From")))) {
			channelCode = String.valueOf(parameters.get("From"));
		}
		String isEmailBinding = member.getIsEmailBinding();
		String isMobileNOBinding = member.getIsMobileNOBinding();
		String registerType = member.getRegisterType();
		StringBuffer sb = new StringBuffer(
				" SELECT COUNT(1) FROM wxbind WHERE 1=1 AND (openid=?  ");
		if ("Y".equals(isEmailBinding) || "0".equals(registerType)) {
			sb.append(" OR memAccount='" + member.getEmail() + "'");
		}
		if ("Y".equals(isMobileNOBinding) || "1".equals(registerType)) {
			sb.append(" OR memAccount='" + member.getMobileNO() + "'");
		}
		sb.append(" )");
		try {
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(openID);
			int tCount = qb.executeInt();
			if (tCount >= 1) {
				String[] errorArr = { "MemberAction000017" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0038");
				return resultMap;
			}
			Wxbind tWxbind = new Wxbind();
			tWxbind.setOpenId(openID);
			tWxbind.setMemAccount(account);
			mWxbindService.save(tWxbind);

			Map<String, Object> userInfoMap = new HashMap<String, Object>();

			// 拥有的积分
			int IntegralSum = member.getPoint();
			// 优惠券数量
			int DiscCouponCount = getCouponCountByMemberId(member.getId(),
					channelCode);

			userInfoMap.put("IntegralSum", IntegralSum);
			userInfoMap.put("DiscCouponCount", DiscCouponCount);
			userInfoMap.put("Nickname", member.getUsername());
			userInfoMap.put("PersonName", member.getRealName());
			String[] whereString = { "Sex", member.getSex() };
			userInfoMap.put("Gender",
					getColumnValue("CodeName", "CodeValue", whereString));
			userInfoMap.put("Birthday", member.getBirthday());
			userInfoMap.put("Account", account);

			resultMap.put("RESULTS", userInfoMap);

		} catch (Exception e) {
			logger.error("wap站微信绑定会员信息接口异常,openID：" + openID + ",message:"
					+ e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0038");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * wap站邮件找回密码接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapDoRetrieveByEmail(
			Map<String, Object> PARAMETERS, MemberService memberService,
			HttpServletRequest req) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			String memberId = (String) PARAMETERS.get("MemberId");
			String password = (String) PARAMETERS.get("PassWord");

			if (StringUtil.isEmpty(memberId) || StringUtil.isEmpty(password)) {
				String[] errorArr = { "G000002" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0025");
				return resultMap;
			}

			resultMap.put("USER", "kxb");
			resultMap.put("REQUESTTYPE", "KXBJRT0025");
			resultMap.put("STATYS", "true");

			Member reMember = memberService.load(memberId);

			if (reMember == null) {
				String[] errorArr = { "MemberAction000006" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0025");
				return resultMap;
			}

			reMember.setPassword(DigestUtils.md5Hex(password));
			reMember.setIsAccountEnabled("Y");
			reMember.setIsAccountLocked("N");
			reMember.setLoginFailureCount(0);
			reMember.setLockedDate(null);
			reMember.setLoginDate(new Date());
			reMember.setRegisterIp(req.getRemoteAddr());
			reMember.setLoginIp(req.getRemoteAddr());
			reMember.setMemberAttributeMap(null);
			reMember.setReceiverSet(null);
			reMember.setFavoriteProductSet(null);
			reMember.setRetrieveEmailvalid("1");
			memberService.update(reMember);

		} catch (Exception e) {
			logger.error("wap站邮件找回密码接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0025");
			return resultMap;

		}
		return resultMap;
	}

	/**
	 * wap站重新发送邮件链接接口
	 * 
	 * @return
	 * */
	public static Map<String, Object> wapReSendRetrieveMail(
			Map<String, Object> PARAMETERS, MemberService memberService,
			HttpServletRequest req) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String memberId = (String) PARAMETERS.get("MemberId");

		if (StringUtil.isEmpty(memberId)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0026");
			return resultMap;
		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0026");
		resultMap.put("STATYS", "true");

		try {
			Member resMember = memberService.load(memberId);

			if (resMember == null) {
				String[] errorArr = { "MemberAction000007" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0026");
				return resultMap;
			}

			memberSchema smemberSchema = new memberSchema();
			smemberSchema.setid(memberId);
			memberSet smemberSet = smemberSchema.query();
			memberSchema tmemberSchema = smemberSet.get(0);
			tmemberSchema.setretrieveEmailvalid("0");
			if (!tmemberSchema.update()) {
				String[] errorArr = { "MemberAction000012" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0026");
				return resultMap;
			}

			if (!"0".equals(resMember.getRegisterType())) {
				String[] errorArr = { "MemberAction000015" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0026");
				return resultMap;
			}
			if (!sendRetrieveMail(resMember, req)) {
				String[] errorArr = { "MemberAction000008" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0026");
				return resultMap;
			}
			resMember.setRetrieveEmailSendDate(new Date());
			memberService.update(resMember);
		} catch (Exception e) {
			logger.error("wap站重新发送邮件链接接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0026");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * wap站手机找回密码接口
	 * 
	 * @return
	 * */
	public static Map<String, Object> wapMobileRetrievePassword(
			Map<String, Object> PARAMETERS, MemberService memberService,
			HttpServletRequest req) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String mobileNo = (String) PARAMETERS.get("MobileNo");
		String passWord = (String) PARAMETERS.get("PassWord");

		if (StringUtil.isEmpty(mobileNo) || StringUtil.isEmpty(passWord)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0027");
			return resultMap;
		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0027");
		resultMap.put("STATYS", "true");

		try {
			Member persistent = memberService
					.getMemberByLoginNameNoBinding(mobileNo);
			if (persistent == null) {
				String[] errorArr = { "MemberAction000014" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0027");
				return resultMap;

			}

			persistent.setPassword(DigestUtils.md5Hex(passWord));
			persistent.setIsAccountEnabled("Y");
			persistent.setIsAccountLocked("N");
			persistent.setLoginFailureCount(0);
			persistent.setLockedDate(null);
			persistent.setLoginDate(new Date());
			persistent.setRegisterIp(req.getRemoteAddr());
			persistent.setLoginIp(req.getRemoteAddr());
			persistent.setMemberAttributeMap(null);
			persistent.setReceiverSet(null);
			persistent.setFavoriteProductSet(null);
			memberService.update(persistent);
		} catch (Exception e) {
			logger.error("wap站手机找回密码接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0027");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * wap站重新发送验证码接口
	 * 
	 * @return
	 */
	public static Map<String, Object> wapReSendSVCToPhone(
			Map<String, Object> PARAMETERS, MemberService memberService,
			HttpServletRequest req) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String mobileNo = (String) PARAMETERS.get("MobileNo");

		if (StringUtil.isEmpty(mobileNo)) {
			String[] errorArr = { "G000002" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0028");
			return resultMap;
		}

		String svcode;
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0028");
		resultMap.put("STATYS", "true");

		try {
			// 找回密码的member
			Member retriveMember = memberService
					.getMemberByLoginNameNoBinding(mobileNo);
			if (retriveMember == null) {
				String[] errorArr = { "MemberAction000007" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0028");
				return resultMap;
			}

			// 开始重新发送短信
			svcode = VCFactory();
			if (!ActionUtil.sendSms("wj00010", mobileNo, svcode)) {// 修改密码发送到手机
				String[] errorArr = { "MemberAction000008" };
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0028");
				return resultMap;
			}

			Map<String, Object> results = new HashMap<String, Object>();
			// 返回验证码
			results.put("Vcode", svcode);
			resultMap.put("RESULTS", results);
		} catch (Exception e) {
			logger.error("wap站重新发送验证码接口异常" + e.getMessage(), e);
			String[] errorArr = { "G000001" };
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0028");
			return resultMap;
		}
		return resultMap;

	}

	/**
	 * 取得指定会员的订单信息
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> getOrderInfoMapForMember(
			String memberId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		QueryBuilder qb = new QueryBuilder();
		qb.setSQL("select a.orderSn,a.orderStatus,d.appStatus,d.evaliDate,d.svaliDate,b.LogoLink,b.ProductID,b.ProductName,a.couponSn,a.totalAmount,a.modifyDate,d.electronicPath,a.PayPrice, "
				+ " a.offsetPoint,a.orderIntegral,(SELECT type FROM sdcouponactivityinfo ac WHERE ac.activitySn = a.activitySn) activityType,(SELECT title FROM sdcouponactivityinfo ac WHERE ac.activitySn = a.activitySn) activityTitle,a.activitySn,a.orderActivity,(SELECT ad.direction FROM CouponInfo ad WHERE ad.couponSn = a.couponsn) couponTitle, a.orderCoupon "
				+ " from sdorders a ,SDSearchRelaProduct b,sdinformation c,sdinformationrisktype d where a.orderSn=d.orderSn and c.informationSn=d.informationSn and (a.orderSn like 'wp%' or channelSn like 'brand_%') and a.memberId = ? and a.orderSn = c.orderSn and b.ProductID = c.productId order by a.modifyDate desc");
		qb.add(memberId);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() == 0) {
			return list;
		} else {
			for (int i = 0; i < dt.getRowCount(); i++) {
				Map<String, Object> orderInfoMap = new HashMap<String, Object>();
				orderInfoMap.put("OrderNumber", dt.get(i, "orderSn"));
				orderInfoMap
						.put("OrderStatus", getOrderStatus(String.valueOf(dt
								.get(i, "orderStatus"))));
				orderInfoMap.put("ProdLogoUrl", dt.get(i, "LogoLink"));
				orderInfoMap.put("ProdCode", dt.get(i, "ProductID"));
				orderInfoMap.put("ProdName", dt.get(i, "ProductName"));
				orderInfoMap.put("IsUseDiscCoupon",
						getIsUseDiscCoupon((String) dt.get(i, "couponSn")));
				orderInfoMap.put("Premium", String.valueOf(new BigDecimal(dt
						.get(i, "totalAmount").toString()).setScale(2,
						BigDecimal.ROUND_HALF_UP)));
				orderInfoMap.put("SubmitTime", DateUtil
						.toDateTimeString((Date) dt.get(i, "modifyDate")));
				orderInfoMap.put("CouponDesc", dt.get(i, "couponTitle"));
				orderInfoMap.put("CouponAmount", dt.get(i, "orderCoupon"));
				orderInfoMap.put("PointAmount", dt.get(i, "orderIntegral"));
				orderInfoMap.put("OffsetPoint", dt.get(i, "offsetPoint"));
				orderInfoMap.put("ActiveType", dt.get(i, "activityType"));
				orderInfoMap.put("ActiveDesc", dt.get(i, "activityTitle"));
				orderInfoMap.put("ActiveAmount", dt.get(i, "orderActivity"));
				orderInfoMap.put(
						"ActualAmountPaid",
						new BigDecimal(StringUtil.isNotEmpty(String.valueOf(dt
								.get(i, "PayPrice"))) ? String.valueOf(dt.get(
								i, "PayPrice")) : "0"));

				String appstatus = String.valueOf(dt.get(i, "appStatus"));
				String evaliDate = String.valueOf(dt.get(i, "evaliDate"));
				String svaliDate = String.valueOf(dt.get(i, "svaliDate"));
				// String mAppStatus = "";
				// 待生效订单：已承保，但没有到起保日期
				// 已过期订：已承保，但已过止保日期
				try {
					Date dSvaliDate = sdf.parse(svaliDate);// 生效日期
					Date dEvaliDate = sdf.parse(evaliDate);// 失效日期
					Date dNow = sdf.parse(PubFun.getCurrent());// 当前日期
					long lEvaliDate = dEvaliDate.getTime();
					long lSvaliDate = dSvaliDate.getTime();
					long lNow = dNow.getTime();
					if ("1".equals(appstatus)) {
						orderInfoMap.put("AppStatus", "100");// 已承保订单
					}
					if ("1".equals(appstatus) && lSvaliDate > lNow) {
						orderInfoMap.put("AppStatus", "101");// 待生效保单
					}
					if ("1".equals(appstatus) && lEvaliDate < lNow) {
						orderInfoMap.put("AppStatus", "106");// 已过期保单
					}
				} catch (ParseException e) {
					logger.error("wap站，处理保单状态时，日期转型错误！");
				}
				String electronicPath = String.valueOf(dt.get(i,
						"electronicPath"));
				if (StringUtil.isNotEmpty(electronicPath)) {
					String tElectronicPath = Config.getServerContext()
							+ "/WapFileDownLoad.jsp?orderSn="
							+ dt.get(i, "orderSn");
					orderInfoMap.put("ElectronicPath", tElectronicPath);
				} else {
					orderInfoMap.put("ElectronicPath", "");
				}
				list.add(orderInfoMap);
			}
		}

		return list;
	}

	/**
	 * 判断活动类型
	 * 
	 * @Title: dealActivityType
	 * @return boolean 返回类型
	 */
	public static boolean dealActivityType(String cActivitySn) {
		QueryBuilder qb = new QueryBuilder(
				" SELECT COUNT(1) FROM sdcouponactivityinfo WHERE ActivitySn=? AND `status`='3' AND `type`='6' ");
		qb.add(cActivitySn);
		int tCount = qb.executeInt();
		if (tCount >= 1) {
			return true;
		}

		return false;
	}

	/**
	 * 取得订单状态的中文信息
	 * 
	 * @return
	 */
	public static String getOrderStatus(String statusSn) {
		int i = Integer.parseInt(statusSn);
		switch (i) {
		case 0:
			return "未处理";
		case 1:
			return "已处理";
		case 2:
			return "已完成";
		case 3:
			return "已取消";
		case 4:
			return "暂存";
		case 5:
			return "待支付";
		case 6:
			return "处理中";
		case 7:
			return "已支付";
		case 8:
			return "自动取消";
		case 9:
			return "已撤销";
		case 10:
			return "有撤销";
		}
		return "";
	}

	/**
	 * 是否使用优惠券
	 * 
	 * @return
	 */
	public static String getIsUseDiscCoupon(String couponSn) {

		QueryBuilder qb = new QueryBuilder();
		qb.setSQL("select status from couponInfo where couponSn = ?");
		qb.add(couponSn);
		String status = qb.executeString();
		if (!"1".equals(status)) {
			return "false";
		}
		return "true";
	}

	/**
	 * 取得指定会员的优惠券数量
	 * 
	 * @return
	 */
	public static int getCouponCountByMemberId(String memberId,
			String channelCode) {

		QueryBuilder qb = new QueryBuilder();
		qb.setSQL("select count(1) from couponInfo where memberId = ? and status = '2' AND channelsn like '%"
				+ channelCode
				+ "%' AND UNIX_TIMESTAMP(endTime) > UNIX_TIMESTAMP(NOW()) ");
		qb.add(memberId);
		int count = qb.executeInt();

		return count;
	}

	/**
	 * 取得用户昵称
	 * 
	 * @return
	 */
	public static String getNickname(Member member) {

		if (StringUtil.isNotEmpty(member.getUsername())) {
			return member.getUsername();
		} else if (StringUtil.isNotEmpty(member.getEmail())) {
			return member.getEmail();
		} else if (StringUtil.isNotEmpty(member.getMobileNO())) {
			return member.getMobileNO();
		} else {
			return null;
		}

	}

	/**
	 * 发送邮箱注册验证邮件
	 * 
	 * @return
	 */
	public static boolean sendVerifyEMail(Member tMember, HttpServletRequest req) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 得到邮箱验证的地址
			String path = Config.getValue("ServerContext");
			// TODO 注册成功之后，邮箱验证地址
			String verifyURL = path
					+ "/shop/member!verifyEmail.action?member.id="
					+ tMember.getId();
			map.put("verifyURL", verifyURL);// 得到验证地址

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int rigisterDate = Integer.parseInt(sdf.format(new Date()));
			map.put("rigisterDate", rigisterDate);
			map.put("Points","");
			if (StringUtil.isNotEmpty(tMember.getId())) {
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					//邮箱校验赠送积分
					Map<String, Object> map_param = new HashMap<String, Object>();
					map_param.put("PointsGive",IntegralConstant.POINT_GIVE_01);
					// 积分处理
					Map map_result=PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH,IntegralConstant.POINT_SOURCE_EMAIL, map_param);
					map.put("Points",map_result.get(IntegralConstant.ACTION_POINTS));
				} catch(Exception e) {
					logger.error("wap注册邮箱验证送积分异常！" + e.getMessage(), e);
				}
			}
			map.put("memberId", tMember.getId());
			map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
			ActionUtil.sendMail("wj00048", tMember.getEmail(), map);
			map.put("Member", tMember);
			ActionUtil.dealAction("wj00048", map, req);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;

	}

	/**
	 * 获取表ZDCODE列制定列的值
	 * 
	 * @param column1
	 *            检索列
	 * @param column2
	 *            条件列
	 * @param whereString
	 *            条件参数
	 * @return
	 */
	public static String getColumnValue(String column1, String column2,
			String[] whereString) {

		GetDBdata db = new GetDBdata();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ");
			sb.append(column1);
			sb.append(" FROM zdCode WHERE codeType = ? and ");
			sb.append(column2);
			sb.append(" = ?");
			String result = db.getOneValue(sb.toString(), whereString);
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 发送找回密码邮件
	 * 
	 * @return
	 */
	public static boolean sendRetrieveMail(Member tMember,
			HttpServletRequest req) {
		try {
			// 发送邮件日期
			Map<String, Object> map = new HashMap<String, Object>();
			// 得到邮箱验证的地址
			String path = Config.getValue("WapServerContext");
			// TODO 修改为WAP站邮件密码找回页面地址
			String retrieveURL = path + "/mobile/setpass.html?member.id="
					+ tMember.getId();
			map.put("retrieveURL", retrieveURL);// 得到验证地址
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int rigisterDate = Integer.parseInt(sdf.format(new Date()));
			map.put("rigisterDate", rigisterDate);
			map.put("memberId", tMember.getId());
			map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
			ActionUtil.sendMail("wj00049", tMember.getEmail(), map);
			map.put("Member", tMember);
			ActionUtil.dealAction("wj00049", map, req);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 生成四位验证码
	 * 
	 * @return
	 */
	public static String VCFactory() {
		boolean flag = true;
		String sendValidateCode = null;
		Random rm = new Random();
		while (flag) {
			int code = rm.nextInt(9999);
			if (code >= 1000) {
				sendValidateCode = String.valueOf(code);
				flag = false;
			}
		}
		return sendValidateCode;
	}

	/**
	 * 发送验证码到手机
	 * 
	 * @param svcode
	 *            验证码
	 * @param way
	 *            手机号
	 * @return 是否发送成功
	 */

	public static boolean SVCToPhone(String svcode, String way,
			String isModify, HttpServletRequest req) {
		boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ToMobileNO", way);
		map.put("ToName", "");
		map.put("Captcha", svcode);
		if ("N".equals(isModify)) {
			if (ActionUtil.dealAction("wj0002", map, req)) {// 注册发送到手机
				flag = true;
			}
		} else {
			if (ActionUtil.sendSms("wj00010", way, svcode)) {// 修改密码发送到手机
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 优惠劵激活接口
	 * 
	 * @Title: couponVerify
	 * @return Map<String,Object> 返回类型
	 */
	public static Map<String, Object> couponVerify(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> couponMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		MemberService tMemberService = (MemberService) mServiceMap
				.get("MemberService");
		// 解析入参
		try {
			String couponSn = (String) tPARAMETERS.get("CouponNumber");
			String UserName = (String) tPARAMETERS.get("UserName");
			String channelCode = (String) tPARAMETERS.get("From");// "wap"
			String orderSn = (String) tPARAMETERS.get("orderSn");
			String standardPrem = (String) tPARAMETERS.get("StandardPrem");
			if (StringUtil.isEmpty(couponSn) || StringUtil.isEmpty(UserName)
					|| StringUtil.isEmpty(channelCode)) {

				errorinfos.add("G000002");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0041",
						couponMap);
			}
			Member member = tMemberService.get(UserName);
			// 优惠劵有效性校验,状态、渠道编码、使用期限
			QueryBuilder qb = new QueryBuilder(
					" SELECT a.couponSn,a.memberId FROM CouponInfo a "
							+ " WHERE a.couponSn=? AND a.status='2' AND channelsn like '%"
							+ channelCode + "%' "
							+ " AND a.startTime <=NOW() AND a.endTime>=NOW()");
			qb.add(couponSn);
			DataTable dt = qb.executeDataTable();
			int couponLen = dt.getRowCount();
			// 是否查询到优惠劵纪录
			if (couponLen <= 0) {
				errorinfos.add("MemberAction000018");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0041",
						couponMap);
			}
			// 是否已绑定会员--激活成功
			String tmemId = dt.getString(0, "memberId");
			if (StringUtil.isNotEmpty(tmemId)) {
				errorinfos.add("MemberAction000020");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0041",
						couponMap);
			}
			// 支付时，激活优惠劵
			if (StringUtil.isNotEmpty(orderSn)) {
				// 判断订单号是否合法
				if (!WapShoppingUtil.checkOrderNumber(orderSn)) {
					errorinfos.add("SaveOrder003");
					return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0041",
							null);
				}
				if (!checkCouponSn(couponSn, orderSn, channelCode, standardPrem)) {
					errorinfos.add("MemberAction000019");
					return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0041",
							couponMap);
				}
			}
			// 优惠劵有效性校验,状态、渠道编码、使用期限
			qb = new QueryBuilder(
					" SELECT a.couponSn,a.status,a.direction,a.parValue,a.startTime,a.endTime FROM CouponInfo a WHERE a.couponSn=? AND channelsn like '%"
							+ channelCode
							+ "%' AND a.startTime <=NOW() AND a.endTime>=NOW() ");

			qb.add(couponSn);
			dt = qb.executeDataTable();
			couponMap.put("DiscCouponNumber", dt.getString(0, "couponSn"));
			couponMap.put("DiscCouponStatus", "2");
			couponMap.put("DiscCouponName", dt.getString(0, "direction"));
			couponMap.put("DiscCouponType", "");
			couponMap.put("Amount", dt.getString(0, "parValue"));
			couponMap.put("ValidDateFrom", dt.getString(0, "startTime"));
			couponMap.put("ValidDateTo", dt.getString(0, "endTime"));
			// 激活绑定会员
			qb = new QueryBuilder(
					" update CouponInfo set memberId=? where couponSn=? ");
			qb.add(member.getId());
			qb.add(couponSn);
			qb.executeNoQuery();
			resultMap.put("RESULTS", couponMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0041",
					couponMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0041");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 
	 * @Title: checkCouponSn
	 * @Description: 优惠劵有效性校验：保险公司、险类
	 */
	public static boolean checkCouponSn(String couponSn, String orderSn,
			String channelCode, String standardPrem) {

		QueryBuilder qb = new QueryBuilder(
				"SELECT a.totalAmount,b.productId FROM sdorders a,sdinformation b WHERE a.orderSn = b.orderSn AND a.orderSn=? ");
		qb.add(orderSn);
		DataTable dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() <= 0) {
			return false;
		}
		String productId = dt.getString(0, "productId");
		DataTable productRiskCode = getRiskCode(productId);
		String totalAmount = dt.getString(0, "totalAmount");
		if (StringUtil.isNotEmpty(standardPrem)) {
			totalAmount = standardPrem;
		}

		qb = new QueryBuilder(
				" SELECT a.couponSn,a.riskCode,a.insuranceCompany,a.payAmount FROM CouponInfo a "
						+ "WHERE a.couponSn=? AND a.status='2' AND channelsn like '%"
						+ channelCode
						+ "%' "
						+ "AND a.startTime <=NOW() AND a.endTime>=NOW()");
		qb.add(couponSn);
		dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() <= 0) {
			return false;
		}
		String payAmount = dt.getString(0, "payAmount");
		String productCompanyCode = StringUtil
				.noNull(getCompanyCode(productId));

		String[] couponRiskCodeArr = StringUtil.noNull(
				dt.getString(0, "riskCode")).split(",");

		String[] couponCompanyCodeArr = StringUtil.noNull(
				dt.getString(0, "insuranceCompany")).split(",");

		// 进行公司校验
		boolean companyCodeCheck = false;
		if (StringUtil.isEmpty(couponCompanyCodeArr[0])) {
			companyCodeCheck = true;
		} else {
			for (int i = 0; i < couponCompanyCodeArr.length; i++) {
				if (productCompanyCode.equals(couponCompanyCodeArr[i])) {
					companyCodeCheck = true;
					break;
				}
			}
		}

		if (!companyCodeCheck) {
			return false;
		}
		// 进行险种检验
		boolean riskCodeCheck = false;
		if (StringUtil.isEmpty(couponRiskCodeArr[0])) {
			riskCodeCheck = true;
		} else {
			if (companyCodeCheck) {
				a: for (int i = 0; i < couponRiskCodeArr.length; i++) {
					for (int j = 0; j < productRiskCode.getRowCount(); j++) {
						if (couponRiskCodeArr[i].equals(productRiskCode.get(j,
								"subRisktype"))) {
							riskCodeCheck = true;
							break a;
						}
					}
				}
			}
		}
		if (!riskCodeCheck) {
			return false;
		}
		// 价格校验
		BigDecimal tTotalAmount = new BigDecimal(totalAmount);
		BigDecimal tPayAmount = new BigDecimal(payAmount);
		int amountFlag = tTotalAmount.compareTo(tPayAmount);
		if (amountFlag == -1) {
			return false;
		}
		return true;
	}

	public static DataTable getRiskCode(String productId) {

		DataTable dt = new QueryBuilder(
				"select DISTINCT c.subRisktype from ("
						+ "select SubRiskTypeCode as subRisktype from femrisktypeb a,femriskb b where a.RiskCode=b.RiskCode and b.IsPublish='Y' and a.RiskCode = ?"
						+ " union all "
						+ "select a.BelongFlag as subRisktype from fmriskb a,femriskb b where a.RiskCode=b.RiskCode and b.IsPublish='Y' and a.RiskCode = ?) c",
				productId, productId).executeDataTable();

		return dt;
	}

	public static String getCompanyCode(String productId) {

		String companyCode = new QueryBuilder(
				"select a.SupplierCode from fmriskb a,femriskb b where a.RiskCode=b.RiskCode and b.IsPublish='Y' and a.RiskCode=? ",
				productId).executeString();

		return companyCode;
	}

	/**
	 * 会员优惠劵接口
	 * 
	 * @Title: memberCouponInfo
	 * @return Map<String,Object> 返回类型
	 */
	public static Map<String, Object> memberCouponInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> memberCouponMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		MemberService tMemberService = (MemberService) mServiceMap
				.get("MemberService");
		// 解析入参
		try {
			String UserName = (String) tPARAMETERS.get("UserName");
			// String UnitID = (String)tPARAMETERS.get("UnitID");
			Member member = tMemberService.get(UserName);
			String channelCode = (String) tPARAMETERS.get("From");// "wap"
			if (StringUtil.isEmpty(UserName) || StringUtil.isEmpty(channelCode)) {

				errorinfos.add("G000002");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0041",
						null);
			}
			// 会员已使用优惠劵
			QueryBuilder qb = new QueryBuilder(
					" SELECT couponSn, parValue,DATE_FORMAT(startTime,'%Y/%m/%d') AS startTime,"
							+ " DATE_FORMAT(endTime,'%Y/%m/%d') AS endTime,direction,date_format(payTime,'%Y/%m/%d %H:%i:%S') as payTime,prop3,prop4 FROM CouponInfo "
							+ " WHERE STATUS='1' AND memberId = ? AND channelsn like '%"
							+ channelCode + "%'");
			qb.add(member.getId());
			DataTable dt = qb.executeDataTable();
			int couponLen = dt.getRowCount();
			JSONArray usedInfo = new JSONArray();
			for (int i = 0; i < couponLen; i++) {
				Map<String, Object> usedMap = new HashMap<String, Object>();
				usedMap.put("DiscCouponNumber", dt.getString(i, "couponSn"));
				usedMap.put("DiscCouponStatus", "1");
				usedMap.put("DiscCouponName", dt.getString(i, "direction"));
				// 折扣优惠劵 02 折扣优惠劵
				String disFlag = String.valueOf(dt.getString(i, "prop3"));
				usedMap.put("DiscCouponType", disFlag);
				usedMap.put("Amount", dt.getString(i, "parValue"));
				if ("02".equals(disFlag)) {
					String dis = String.valueOf(dt.getString(i, "prop4"));
					usedMap.put("Amount", dis);
				}
				usedMap.put("ValidDateFrom", dt.getString(i, "startTime"));
				usedMap.put("ValidDateTo", dt.getString(i, "endTime"));
				usedMap.put("UseTime", dt.getString(i, "payTime"));
				usedInfo.put(usedMap);
			}
			// 会员未使用优惠劵
			qb = new QueryBuilder(
					" SELECT couponSn, parValue,DATE_FORMAT(startTime,'%Y/%m/%d') AS startTime,"
							+ " DATE_FORMAT(endTime,'%Y/%m/%d') AS endTime,direction,prop3,prop4 FROM CouponInfo "
							+ " WHERE STATUS='2' AND memberId = ? AND UNIX_TIMESTAMP(endTime) > UNIX_TIMESTAMP(NOW()) AND channelsn like '%"
							+ channelCode + "%'");
			qb.add(member.getId());
			dt = qb.executeDataTable();
			couponLen = dt.getRowCount();
			JSONArray unusedInfo = new JSONArray();
			for (int i = 0; i < couponLen; i++) {
				Map<String, Object> unusedMap = new HashMap<String, Object>();
				unusedMap.put("DiscCouponNumber", dt.getString(i, "couponSn"));
				unusedMap.put("DiscCouponStatus", "2");
				unusedMap.put("DiscCouponName", dt.getString(i, "direction"));
				// 折扣优惠劵 02 折扣优惠劵
				String disFlag = String.valueOf(dt.getString(i, "prop3"));
				unusedMap.put("DiscCouponType", disFlag);
				unusedMap.put("Amount", dt.getString(i, "parValue"));
				if ("02".equals(disFlag)) {
					String dis = String.valueOf(dt.getString(i, "prop4"));
					unusedMap.put("Amount", dis);
				}
				unusedMap.put("ValidDateFrom", dt.getString(i, "startTime"));
				unusedMap.put("ValidDateTo", dt.getString(i, "endTime"));
				unusedInfo.put(unusedMap);
			}
			// 会员已过期优惠劵
			qb = new QueryBuilder(
					" SELECT couponSn, parValue,DATE_FORMAT(startTime,'%Y/%m/%d') AS startTime,"
							+ " DATE_FORMAT(endTime,'%Y/%m/%d') AS endTime,direction,prop3,prop4 FROM CouponInfo "
							+ " WHERE memberId = ? and (status = '5' or (status = '2' and UNIX_TIMESTAMP(endTime) < UNIX_TIMESTAMP(now()))) AND channelsn like '%"
							+ channelCode + "%'");
			qb.add(member.getId());
			dt = qb.executeDataTable();
			couponLen = dt.getRowCount();
			JSONArray expiredInfo = new JSONArray();
			for (int i = 0; i < couponLen; i++) {
				Map<String, Object> expiredMap = new HashMap<String, Object>();
				expiredMap.put("DiscCouponNumber", dt.getString(i, "couponSn"));
				expiredMap.put("DiscCouponStatus", "2");
				expiredMap.put("DiscCouponName", dt.getString(i, "direction"));
				// 折扣优惠劵 02 折扣优惠劵
				String disFlag = String.valueOf(dt.getString(i, "prop3"));
				expiredMap.put("DiscCouponType", disFlag);
				expiredMap.put("Amount", dt.getString(i, "parValue"));
				if ("02".equals(disFlag)) {
					String dis = String.valueOf(dt.getString(i, "prop4"));
					expiredMap.put("Amount", dis);
				}
				expiredMap.put("ValidDateFrom", dt.getString(i, "startTime"));
				expiredMap.put("ValidDateTo", dt.getString(i, "endTime"));
				expiredInfo.put(expiredMap);
			}
			memberCouponMap.put("Used", usedInfo);
			memberCouponMap.put("Unused", unusedInfo);
			memberCouponMap.put("Expired", expiredInfo);
			resultMap.put("RESULTS", memberCouponMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0042",
					resultMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0042");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 会员积分接口
	 * 
	 * @Title: memberPointInfo
	 */
	public static Map<String, Object> memberPointInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> memberPointMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		MemberService tMemberService = (MemberService) mServiceMap
				.get("MemberService");
		if (errorinfos.size() > 0) {
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0043",
					resultMap);
		}
		// 解析入参
		try {
			String UserName = (String) tPARAMETERS.get("UserName");
			// String UnitID = (String)tPARAMETERS.get("UnitID");
			Member member = tMemberService.get(UserName);
			if (StringUtil.isEmpty(UserName)) {
				errorinfos.add("G000002");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0041",
						null);
			}
			QueryBuilder qb = new QueryBuilder(
					" SELECT VALUE FROM zdconfig WHERE type = 'PointScalerUnit' ");
			String pointScalerUnit = qb.executeString();
			qb = new QueryBuilder(
					" SELECT currentValidatePoint, POINT, aboutToExpirePoints,aboutToExpireDate FROM member WHERE id =? ");
			qb.add(member.getId());
			DataTable dt = qb.executeDataTable();

			String currentPoint = dt.getString(0, "currentValidatePoint");// 可用积分
			String point = dt.getString(0, "point");// 冻结积分
			// modif by wangej 20150518 积分改版
			String aboutToExpirePoints = dt.getString(0, "aboutToExpirePoints");// 即将过期的积分数
			String aboutToExpireDate = dt.getString(0, "aboutToExpireDate");// 即将过期的积分数对应的月份

			BigDecimal pointToMoney = new BigDecimal(currentPoint)
					.divide(new BigDecimal(pointScalerUnit));// 积分可抵值

			qb = new QueryBuilder(
					" select distinct sdIC.createdate CreateDate, sdIC.createtime CreateTime, sdIC.Description codeName, sdIC.integral Integral ,sdIC.Source as Source ,sdIC.Manner as Manner, sdIC.Businessid as Businessid "
							+ " from  SDIntCalendar sdIC "
							+ " where  sdic.status ='0' and (manner ='0' or manner ='1') and sdIC.integral > 0 and sdIC.memberId=?  ORDER BY sdIC.createdate DESC,sdIC.createtime DESC  ");
			qb.add(member.getId());
			dt = qb.executeDataTable();
			int couponLen = dt.getRowCount();
			JSONArray PointDetailInfo = new JSONArray();
			for (int i = 0; i < couponLen; i++) {
				Map<String, Object> PointDetailMap = new HashMap<String, Object>();
				String manner = "";
				if ("0".equals(String.valueOf(dt.getString(i, "Manner")))) {
					manner = "积分收入";
				} else {
					manner = "积分支出";
				}
				String tIntegral = dt.getString(i, "Integral");
				PointDetailMap.put("TypeName", manner);
				PointDetailMap.put("UseTime", dt.getString(i, "CreateDate"));
				PointDetailMap.put("UseCount", tIntegral);
				//mod by wangej 20150810 积分轨迹描述直接去表SDIntCalendar-Description字段信息
				/*String desc = "";
				if ("0".equals(String.valueOf(dt.getString(i, "Source")))) {
					desc = String.valueOf(dt.getString(i, "CodeName"))
							+ "+订单号："
							+ String.valueOf(dt.getString(i, "Businessid"));
				} else if ("2"
						.equals(String.valueOf(dt.getString(i, "Source")))) {
					QueryBuilder qb1 = new QueryBuilder(
							"select ordersn from sdorders where paysn=? limit 0,1");
					qb1.add(String.valueOf(dt.getString(i, "Businessid")));
					DataTable dt1 = qb1.executeDataTable();
					if (dt1.getRowCount() > 0) {
						desc = new BigDecimal(String.valueOf(dt.getString(i,
								"Integral"))).divide(new BigDecimal(
								pointScalerUnit), 2, BigDecimal.ROUND_DOWN)
								+ "元积分抵值 订单号：" + dt1.getString(0, 0);
					} else {
						desc = new BigDecimal(String.valueOf(dt.getString(i,
								"Integral"))).divide(new BigDecimal(
								pointScalerUnit), 2, BigDecimal.ROUND_DOWN)
								+ "元积分抵值 ";
					}
				} else {
					desc = String.valueOf(dt.getString(i, "CodeName"));
				}*/
				PointDetailMap.put("UseDesc", String.valueOf(dt.getString(i, "CodeName")));
				PointDetailInfo.put(PointDetailMap);
			}
			memberPointMap.put("DiscActivePoint", currentPoint);
			memberPointMap.put("PointAmount", pointToMoney);
			memberPointMap.put("UnActivePoint", point);
			memberPointMap.put("AboutToExpirePoints", aboutToExpirePoints);
			memberPointMap.put("AboutToExpirePointsDate", aboutToExpireDate);
			memberPointMap.put("PointDetail", PointDetailInfo);
			resultMap.put("RESULTS", memberPointMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0043",
					resultMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0043");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 联合登录（QQ、新浪微博、支付宝）判断是否已绑定
	 * 
	 * @Title: unitBind
	 * @return Map<String,Object> 返回类型
	 */
	public static Map<String, Object> unitBind(Map<String, Object> tPARAMETERS,
			Map<String, Object> mServiceMap, String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> unionMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		MemberService tMemberService = (MemberService) mServiceMap
				.get("MemberService");
		BindInfoForLoginService bindInfoForLoginService = (BindInfoForLoginService) mServiceMap
				.get("BindInfoForLoginService");
		// 解析入参
		try {
			String bindType = String.valueOf(tPARAMETERS.get("BindType"));// 联合登录类型
			// QQ
			// Sina
			// Alipay
			String openID = String.valueOf(tPARAMETERS.get("OpenID"));// 联合登录唯一标识符
			if (StringUtil.isEmpty(openID)) {
				errorinfos.add("WXError001");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0046",
						unionMap);
			}
			if (!"Tencent".equals(bindType) && !"Sina".equals(bindType)
					&& !"Alipay".equals(bindType)) {
				errorinfos.add("WXError003");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0047",
						unionMap);
			}
			BindInfoForLogin tBindInfoForLogin = bindInfoForLoginService
					.getBindInfoForLoginByOpenID(openID);

			unionMap.put("BindFlag", "N");
			unionMap.put("OpenID", "");
			unionMap.put("UserName", "");
			unionMap.put("MemberID", "");
			// 绑定
			if (tBindInfoForLogin != null) {
				if (StringUtil.isNotEmpty(tBindInfoForLogin.getKxbUserEmail())
						|| StringUtil.isNotEmpty(tBindInfoForLogin
								.getKxbUserPhone())) {
					unionMap.put("BindFlag", "Y");
				}
				unionMap.put("OpenID", openID);
				String registertype = String.valueOf(tBindInfoForLogin
						.getRegisterType());
				String userAccount = String.valueOf(tBindInfoForLogin
						.getKxbUserEmail());
				// 手机注册
				if ("1".equals(registertype)) {
					userAccount = String.valueOf(tBindInfoForLogin
							.getKxbUserPhone());
				} else {
					// 邮箱注册
				}
				unionMap.put("UserName", "");
				unionMap.put("MemberID", "");
				if (StringUtil.isNotEmpty(userAccount)) {
					Member member = tMemberService
							.getMemberByLoginName(userAccount);
					String memberid = "";
					if (member != null) {
						memberid = member.getId();
					}
					unionMap.put("UserName", userAccount);
					unionMap.put("MemberID", memberid);
				}
			}

			resultMap.put("RESULTS", unionMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0046",
					unionMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0046");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 联合登录（QQ、新浪微博、支付宝）信息绑定
	 * 
	 * @Title: unitBind
	 * @return Map<String,Object> 返回类型
	 */
	public static Map<String, Object> unitBindingInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> unionMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		MemberService tMemberService = (MemberService) mServiceMap
				.get("MemberService");
		BindInfoForLoginService bindInfoForLoginService = (BindInfoForLoginService) mServiceMap
				.get("BindInfoForLoginService");
		// 解析入参
		try {
			String bindType = String.valueOf(tPARAMETERS.get("BindType"));// 联合登录类型
			// QQ
			// Sina
			// Alipay
			String nickName = String.valueOf(tPARAMETERS.get("NickName"));// 昵称
			String avatar = String.valueOf(tPARAMETERS.get("Avatar"));// 头像
			String userEmail = String.valueOf(tPARAMETERS.get("UserEmail"));// email
			String userPhone = String.valueOf(tPARAMETERS.get("UserPhone"));// 电话号
			String openID = String.valueOf(tPARAMETERS.get("OpenID"));// 唯一标识符
			BindInfoForLogin tBindInfoForLogin = new BindInfoForLogin();

			if (StringUtil.isEmpty(openID)) {
				errorinfos.add("WXError001");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0047",
						unionMap);
			}
			if (!"Tencent".equals(bindType) && !"Sina".equals(bindType)
					&& !"Alipay".equals(bindType)) {
				errorinfos.add("WXError003");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0047",
						unionMap);
			}
			if (StringUtil.isEmpty(userEmail) && StringUtil.isEmpty(userPhone)) {
				errorinfos.add("WXError002");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0047",
						unionMap);
			}
			tBindInfoForLogin = bindInfoForLoginService
					.getBindInfoForLoginByOpenID(openID);
			unionMap.put("BindFlag", "N");
			unionMap.put("OpenID", "");
			unionMap.put("UserName", "");
			unionMap.put("MemberID", "");

			boolean opflag = true;
			if (tBindInfoForLogin == null) {
				tBindInfoForLogin = new BindInfoForLogin();
				opflag = false;
			}

			tBindInfoForLogin.setComCode(String.valueOf(bindType));
			tBindInfoForLogin.setUserNickName(replaceHTML(nickName).replaceAll(
					"\\+", "%20"));
			tBindInfoForLogin.setOpenID(openID);
			tBindInfoForLogin.setAvatar(String.valueOf(avatar));
			tBindInfoForLogin.setKxbUserEmail(String.valueOf(userEmail));
			tBindInfoForLogin.setKxbUserPhone(String.valueOf(userPhone));
			String userAccount = userPhone;
			if (StringUtil.isEmpty(userAccount)) {
				userAccount = userEmail;
			}
			Member member = tMemberService.getMemberByLoginName(userAccount);
			if (member == null) {
				errorinfos.add("WXError004");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0047",
						unionMap);
			}
			/*
			 * if(member.getmBindInfoForLogin()!=null){
			 * errorinfos.add("WXError005"); return
			 * WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0047", unionMap); }
			 */
			if (member != null) {
				tBindInfoForLogin.setmMember(member);
			}
			if (!opflag) {
				bindInfoForLoginService.save(tBindInfoForLogin);
			} else {
				bindInfoForLoginService.update(tBindInfoForLogin);
			}
			member.setmBindInfoForLogin(tBindInfoForLogin);
			tMemberService.update(member);
			unionMap.put("BindFlag", "Y");
			unionMap.put("OpenID", openID);
			String memberid = "";
			if (member != null) {
				memberid = member.getId();
			}
			unionMap.put("UserName", userAccount);
			unionMap.put("MemberID", memberid);
			resultMap.put("RESULTS", unionMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0047",
					unionMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0047");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 投、被保人信息接口：增、删、改
	 * 
	 * @Title: dealRelaInfo
	 * @return Map<String,Object> 返回类型
	 */
	public static Map<String, Object> dealRelaInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> unionMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		MemberService tMemberService = (MemberService) mServiceMap
				.get("MemberService");

		DealDataService mDealDataService = (DealDataService) mServiceMap
				.get("DealDataService");

		Transaction trans = new Transaction();
		// 解析入参
		try {
			String oppResult = "True";
			String operateType = String.valueOf(tPARAMETERS.get("OperateType"));// 操作类型
			// Appnt：投保人
			// Insured：被保人
			String memberID = String.valueOf(tPARAMETERS.get("MemberID"));// 会员ID
			// String productID =
			// String.valueOf(tPARAMETERS.get("ProductID"));//产品ID
			// String comCode =
			// String.valueOf(tPARAMETERS.get("ComCode"));//保险公司编码
			String method = String.valueOf(tPARAMETERS.get("Method"));// 操作方法
			// Add：新增
			// Update：修改
			// Delete：删除
			String RelaName = String.valueOf(tPARAMETERS.get("RelaName"));// 姓名
			if (StringUtil.isEmpty(memberID)) {
				errorinfos.add("G000002");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0048",
						unionMap);
			}
			Member member = tMemberService.get(memberID);
			if (member == null) {
				errorinfos.add("MemberAction000006");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0048",
						unionMap);
			}
			if ("Delete".equals(method)) {
				// 默认为投保人
				QueryBuilder relaQB = new QueryBuilder(
						" DELETE FROM sdrelationappnt WHERE memberId=? AND applicantName=? ",
						memberID, RelaName);
				// 被保人
				if ("Insured".equals(operateType)) {
					relaQB = new QueryBuilder(
							" DELETE FROM sdrelationrecognizee WHERE memberId=? AND recognizeeName=? ",
							memberID, RelaName);
				}
				trans.add(relaQB);
				if (!trans.commit()) {
					errorinfos.add("MemberAction000022");
					return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0048",
							unionMap);
				} else {
					unionMap.put("OppResult", oppResult);

					resultMap.put("RESULTS", unionMap);
					resultMap.put("USER", "kxb");
					resultMap.put("REQUESTTYPE", "KXBJRT0047");
					resultMap.put("STATYS", "true");
					return resultMap;
				}
			} else if ("Add".equals(method) || "Update".equals(method)) {
				LinkedHashMap<Object, String> mLMap = new LinkedHashMap<Object, String>();
				// 新增 || 修改
				Object obj = getRelaEntity(operateType, tPARAMETERS,
						mServiceMap);
				if ("Appnt".equals(operateType)) {
					if ("Add".equals(method)) {
						// 常用投保人个数校验
						int tCount = member.getSdrelationappntSet().size();
						// 判断投、被保人唯一性：memberid、姓名
						int appntCount = new QueryBuilder(
								" SELECT COUNT(1) FROM sdrelationappnt WHERE mMember_id = ? AND applicantName=? ",
								member.getId(), RelaName).executeInt();
						if (appntCount >= 1) {
							errorinfos.add("MemberAction000026");
							return WapErrorUtil.dealErrorInfo(errorinfos,
									"KXBJRT0048", unionMap);
						}
						if (tCount >= 10) {
							errorinfos.add("MemberAction000025");
							return WapErrorUtil.dealErrorInfo(errorinfos,
									"KXBJRT0048", unionMap);
						}
					}
					if (obj != null) {
						SDRelationAppnt appnt = (SDRelationAppnt) obj;
						List<SDRelationAppnt> appntList = new ArrayList<SDRelationAppnt>();
						appntList.add(appnt);
						mLMap.put(appntList, "insert&update");
					}
				} else {
					// 常用被保人个数校验
					if ("Add".equals(method)) {
						// 判断投、被保人唯一性：memberid、姓名
						int appntCount = new QueryBuilder(
								" SELECT COUNT(1) FROM SDRelationRecognizee WHERE mMember_id = ? AND recognizeeName=? ",
								member.getId(), RelaName).executeInt();
						if (appntCount >= 1) {
							errorinfos.add("MemberAction000027");
							return WapErrorUtil.dealErrorInfo(errorinfos,
									"KXBJRT0048", unionMap);
						}
						int tCount = member.getSdrelationrecognizeeSet().size();
						if (tCount >= 10) {
							errorinfos.add("MemberAction000025");
							return WapErrorUtil.dealErrorInfo(errorinfos,
									"KXBJRT0048", unionMap);
						}
					}
					if (obj != null) {
						SDRelationRecognizee insured = (SDRelationRecognizee) obj;
						List<SDRelationRecognizee> insuredList = new ArrayList<SDRelationRecognizee>();
						insuredList.add(insured);
						mLMap.put(insuredList, "insert&update");
					}
				}
				if (mLMap != null && mLMap.size() >= 1) {
					if (!mDealDataService.saveAll(mLMap)) {
						logger.error("新增或修改常用投、被保人信息失败!");
						errorinfos.add("MemberAction000023");
						return WapErrorUtil.dealErrorInfo(errorinfos,
								"KXBJRT0048", unionMap);
					}
				} else {
					logger.error("新增或修改时，获取常用投、被保人信息异常!");
					errorinfos.add("MemberAction000024");
					return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0048",
							unionMap);
				}
			}
			unionMap.put("OppResult", oppResult);

			resultMap.put("RESULTS", unionMap);
			resultMap.put("USER", "kxb");
			resultMap.put("REQUESTTYPE", "KXBJRT0047");
			resultMap.put("STATYS", "true");
			resultMap.put("RESULTS", unionMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0048",
					unionMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0048");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 查询投保人关系
	 * 
	 * @param tPARAMETERS
	 * @param mServiceMap
	 * @param USER
	 * @return
	 */
	public static Map<String, Object> queryRelaInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> unionMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		DictionaryService tDictionaryService = (DictionaryService) mServiceMap
				.get("DictionaryService");

		OccupationService tOccupationService = (OccupationService) mServiceMap
				.get("OccupationService");

		AreaService tAreaService = (AreaService) mServiceMap.get("AreaService");

		// 解析入参
		try {
			String operateType = String.valueOf(tPARAMETERS.get("OperateType"));// 操作类型
			// Appnt：投保人
			// Insured：被保人
			String memberID = String.valueOf(tPARAMETERS.get("MemberID"));// 会员ID
			String productID = String.valueOf(tPARAMETERS.get("ProductID"));// 产品ID
			String comCode = String.valueOf(tPARAMETERS.get("ComCode"));// 保险公司编码
			String cIsAll = String.valueOf(tPARAMETERS.get("IsAll"));// 是否查询全部出参
			String cRelaName = String.valueOf(tPARAMETERS.get("RelaName"));// 姓名
			if (StringUtil.isEmpty(memberID)) {
				errorinfos.add("G000002");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0049",
						unionMap);
			}
			int appntCount = 0;
			int insuredCount = 0;
			SDRelationAppntService sdRelationAppntService = (SDRelationAppntService) mServiceMap
					.get("SDRelationAppntService");
			SDRelationRecognizeeService sdRelationRecognizeeService = (SDRelationRecognizeeService) mServiceMap
					.get("SDRelationRecognizeeService");
			String relaName = "";// 姓名
			String relaEnName = "";// 英文名
			String relaSex = "";// 性别编码
			String relaSexName = "";// 性别名称
			String relaIdentifyType = "";// 证件类型编码
			String relaIdentifyTypeName = "";// 证件类型名称
			String relaIdentify = "";// 证件号
			String relaBirthday = "";// 出生日期
			String relaAge = "";// 年龄
			String relaEmail = "";// 邮箱
			String relaMobile = "";// 手机号
			String relaZipCode = "";// 邮编
			String relaOcc1 = "";// 职业
			String relaOcc1Name = "";// 职业
			String relaOcc2 = "";// 二级职业
			String relaOcc2Name = "";// 二级职业
			String relaOcc3 = "";// 三级职业
			String relaOcc3Name = "";// 三级职业
			String relaArea1 = "";// 省
			String relaArea1Name = "";// 省
			String relaArea2 = "";// 市
			String relaArea2Name = "";// 市
			String relaArea3 = "";// 区
			String relaArea3Name = "";// 区
			String relaAddress = "";// 地址
			String relaStartID = "";// 证件有效起期
			String relaEndID = "";// 证件有效止期
			String socialSecurity = "";// 是否有医保
			// String matchType = "03";//暂时不用
			JSONArray arrInfo = new JSONArray();
			List<SDRelationAppnt> appntList = sdRelationAppntService
					.getSDRelationAppntInfoList(comCode, productID, memberID,
							cRelaName);
			// 投保人
			appntCount = appntList.size();
			if ("Appnt".equals(operateType)) {
				for (SDRelationAppnt appnt : appntList) {
					relaName = String.valueOf(appnt.getApplicantName());// 姓名
					relaEnName = String.valueOf(appnt.getApplicantEnName());// 英文名
					relaSex = String.valueOf(appnt.getApplicantSex());// 性别编码
					relaSexName = String.valueOf(appnt.getApplicantSexName());// 性别名称
					relaIdentifyType = String.valueOf(appnt
							.getApplicantIdentityType());// 证件类型编码
					relaIdentifyTypeName = String.valueOf(appnt
							.getApplicantIdentityTypeName());// 证件类型名称
					relaIdentify = String.valueOf(appnt
							.getApplicantIdentityId());// 证件号
					relaBirthday = String.valueOf(appnt.getApplicantBirthday());// 出生日期
					relaAge = String.valueOf(appnt.getApplicantAge());// 年龄
					relaEmail = String.valueOf(appnt.getApplicantMail());// 邮箱
					relaMobile = String.valueOf(appnt.getApplicantMobile());// 手机号
					relaZipCode = String.valueOf(appnt.getApplicantZipCode());// 邮编
					relaOcc1 = String.valueOf(appnt.getApplicantOccupation1());// 职业
					relaOcc1Name = String.valueOf(tOccupationService
							.getOccupationName(relaOcc1));// 职业
					relaOcc2 = String.valueOf(appnt.getApplicantOccupation2());// 二级职业
					relaOcc2Name = String.valueOf(tOccupationService
							.getOccupationName(relaOcc2));// 二级职业
					relaOcc3 = String.valueOf(appnt.getApplicantOccupation3());// 三级职业
					relaOcc3Name = String.valueOf(tOccupationService
							.getOccupationName(relaOcc3));// 三级职业
					relaArea1 = String.valueOf(appnt.getApplicantArea1());// 省
					relaArea1Name = String.valueOf(tAreaService
							.getAreaName(relaArea1));// 省
					relaArea2 = String.valueOf(appnt.getApplicantArea2());// 市
					relaArea2Name = String.valueOf(tAreaService
							.getAreaName(relaArea2));// 市
					relaArea3 = String.valueOf(appnt.getApplicantArea3());// 区
					relaArea3Name = String.valueOf(tAreaService
							.getAreaName(relaArea3));// 区
					relaAddress = String.valueOf(appnt.getApplicantAddress());// 地址
					relaStartID = String.valueOf(appnt.getApplicantStartID());// 证件有效起期
					relaEndID = String.valueOf(appnt.getApplicantEndID());// 证件有效止期
					socialSecurity = String.valueOf(appnt.getSocialSecurity());// 是否有医保
					if (StringUtil.isNotEmpty(productID)
							&& StringUtil.isNotEmpty(comCode)
							&& !productID.equals(appnt.getProductId())
							&& !comCode.equals(appnt.getRemark())) {
						relaSex = tDictionaryService.getCodeValueByCodeName(
								comCode, "Sex", appnt.getApplicantSexName());
						relaIdentifyType = tDictionaryService
								.getCodeValueByCodeName(comCode, "certificate",
										appnt.getApplicantIdentityTypeName());
						if (StringUtil.isEmpty(relaIdentifyType)) {
							relaIdentifyType = tDictionaryService
									.getCodeValueByCodeName(comCode,
											"certificate", "身份证");
							relaIdentifyTypeName = "身份证";
						}
						// 如果是全站匹配，则地区、职业为空
						relaOcc1 = "";// 职业
						relaOcc1Name = "";// 职业
						relaOcc2 = "";// 二级职业
						relaOcc2Name = "";// 二级职业
						relaOcc3 = "";// 三级职业
						relaOcc3Name = "";// 三级职业
						relaArea1 = "";// 省
						relaArea1Name = "";// 省
						relaArea2 = "";// 市
						relaArea2Name = "";// 市
						relaArea3 = "";// 区
						relaArea3Name = "";// 区
					}
					Map<String, String> appMap = new HashMap<String, String>();
					appMap.put("RelaName", relaName);
					if ("Y".equals(cIsAll)) {
						appMap.put("RelaEnName", relaEnName);
						appMap.put("RelaSex", relaSex);
						appMap.put("RelaSexName", relaSexName);
						appMap.put("RelaIdentifyType", relaIdentifyType);
						appMap.put("RelaIdentifyTypeName", relaIdentifyTypeName);
						appMap.put("RelaIdentify", relaIdentify);
						appMap.put("RelaBirthday", relaBirthday);
						appMap.put("RelaAge", relaAge);
						appMap.put("RelaEmail", relaEmail);
						appMap.put("RelaMobile", relaMobile);
						appMap.put("RelaZipCode", relaZipCode);
						appMap.put("RelaOcc1", relaOcc1);
						appMap.put("RelaOcc1Name", relaOcc1Name);
						appMap.put("RelaOcc2", relaOcc2);
						appMap.put("RelaOcc2Name", relaOcc2Name);
						appMap.put("RelaOcc3", relaOcc3);
						appMap.put("RelaOcc3Name", relaOcc3Name);
						appMap.put("RelaArea1", relaArea1);
						appMap.put("RelaArea1Name", relaArea1Name);
						appMap.put("RelaArea2", relaArea2);
						appMap.put("RelaArea2Name", relaArea2Name);
						appMap.put("RelaArea3", relaArea3);
						appMap.put("RelaArea3Name", relaArea3Name);
						appMap.put("RelaAddress", relaAddress);
						appMap.put("RelaStartID", relaStartID);
						appMap.put("RelaEndID", relaEndID);
						appMap.put("SocialSecurity", socialSecurity);
						appMap.put("MemberID", memberID);
						appMap.put("matchType", "");// TODO 暂不处理
					}
					arrInfo.put(appMap);
				}
			} else if ("Insured".equals(operateType)) {
				List<SDRelationRecognizee> insuredList = sdRelationRecognizeeService
						.getSDRelationRecognizeeInfoList(comCode, productID,
								memberID, cRelaName);
				insuredCount = insuredList.size();
				for (SDRelationRecognizee insured : insuredList) {
					relaName = String.valueOf(insured.getRecognizeeName());// 姓名
					relaEnName = String.valueOf(insured.getRecognizeeEnName());// 英文名
					relaSexName = String
							.valueOf(insured.getRecognizeeSexName());// 性别名称
					// 性别编码 需要根据性别名称反查
					relaSex = tDictionaryService.getCodeValueByCodeName(
							comCode, "Sex", insured.getRecognizeeSexName());// 性别编码
					relaIdentifyTypeName = String.valueOf(insured
							.getRecognizeeIdentityTypeName());// 证件类型名称
					// 证件类型编码 需要根据性别名称反查
					relaIdentifyType = tDictionaryService
							.getCodeValueByCodeName(comCode, "certificate",
									insured.getRecognizeeIdentityTypeName());// 证件类型编码
					relaIdentify = String.valueOf(insured
							.getRecognizeeIdentityId());// 证件号
					relaBirthday = String.valueOf(insured
							.getRecognizeeBirthday());// 出生日期
					relaAge = String.valueOf(insured.getRecognizeeAge());// 年龄
					relaEmail = String.valueOf(insured.getRecognizeeMail());// 邮箱
					relaMobile = String.valueOf(insured.getRecognizeeMobile());// 手机号
					relaZipCode = String
							.valueOf(insured.getRecognizeeZipCode());// 邮编
					relaOcc1 = String.valueOf(insured
							.getRecognizeeOccupation1());// 职业
					relaOcc1Name = String.valueOf(tOccupationService
							.getOccupationName(relaOcc1));// 职业
					relaOcc2 = String.valueOf(insured
							.getRecognizeeOccupation2());// 二级职业
					relaOcc2Name = String.valueOf(tOccupationService
							.getOccupationName(relaOcc2));// 二级职业
					relaOcc3 = String.valueOf(insured
							.getRecognizeeOccupation3());// 三级职业
					relaOcc3Name = String.valueOf(tOccupationService
							.getOccupationName(relaOcc3));
					;// 三级职业
					relaArea1 = String.valueOf(insured.getRecognizeeArea1());// 省
					relaArea1Name = String.valueOf(tAreaService
							.getAreaName(relaArea1));// 省
					relaArea2 = String.valueOf(insured.getRecognizeeArea2());// 市
					relaArea2Name = String.valueOf(tAreaService
							.getAreaName(relaArea2));// 市
					relaArea3 = String.valueOf(insured.getRecognizeeArea3());// 区
					relaArea3Name = String.valueOf(tAreaService
							.getAreaName(relaArea3));// 区
					relaAddress = String
							.valueOf(insured.getRecognizeeAddress());// 地址
					relaStartID = String
							.valueOf(insured.getRecognizeeStartID());// 证件有效起期
					relaEndID = String.valueOf(insured.getRecognizeeEndID());// 证件有效止期
					socialSecurity = "";// 是否有医保
					if (StringUtil.isNotEmpty(productID)
							&& StringUtil.isNotEmpty(comCode)
							&& !productID.equals(insured.getProductId())
							&& !comCode.equals(insured.getRemark())) {
						relaSex = tDictionaryService.getCodeValueByCodeName(
								comCode, "Sex", insured.getRecognizeeSexName());
						relaIdentifyType = tDictionaryService
								.getCodeValueByCodeName(comCode, "certificate",
										insured.getRecognizeeIdentityTypeName());
						// 如果为匹配到则默认为省份证
						if (StringUtil.isEmpty(relaIdentifyType)) {
							relaIdentifyType = tDictionaryService
									.getCodeValueByCodeName(comCode,
											"certificate", "身份证");
							relaIdentifyTypeName = "身份证";
						}
						// 如果是全站匹配，则地区、职业为空
						relaOcc1 = "";// 职业
						relaOcc1Name = "";// 职业
						relaOcc2 = "";// 二级职业
						relaOcc2Name = "";// 二级职业
						relaOcc3 = "";// 三级职业
						relaOcc3Name = "";// 三级职业
						relaArea1 = "";// 省
						relaArea1Name = "";// 省
						relaArea2 = "";// 市
						relaArea2Name = "";// 市
						relaArea3 = "";// 区
						relaArea3Name = "";// 区
					}
					Map<String, String> appMap = new HashMap<String, String>();
					appMap.put("RelaName", relaName);
					if ("Y".equals(cIsAll)) {
						appMap.put("RelaEnName", relaEnName);
						appMap.put("RelaSex", relaSex);
						appMap.put("RelaSexName", relaSexName);
						appMap.put("RelaIdentifyType", relaIdentifyType);
						appMap.put("RelaIdentifyTypeName", relaIdentifyTypeName);
						appMap.put("RelaIdentify", relaIdentify);
						appMap.put("RelaBirthday", relaBirthday);
						appMap.put("RelaAge", relaAge);
						appMap.put("RelaEmail", relaEmail);
						appMap.put("RelaMobile", relaMobile);
						appMap.put("RelaZipCode", relaZipCode);
						appMap.put("RelaOcc1", relaOcc1);
						appMap.put("RelaOcc1Name", relaOcc1Name);
						appMap.put("RelaOcc2", relaOcc2);
						appMap.put("RelaOcc2Name", relaOcc2Name);
						appMap.put("RelaOcc3", relaOcc3);
						appMap.put("RelaOcc3Name", relaOcc3Name);
						appMap.put("RelaArea1", relaArea1);
						appMap.put("RelaArea1Name", relaArea1Name);
						appMap.put("RelaArea2", relaArea2);
						appMap.put("RelaArea2Name", relaArea2Name);
						appMap.put("RelaArea3", relaArea3);
						appMap.put("RelaArea3Name", relaArea3Name);
						appMap.put("RelaAddress", relaAddress);
						appMap.put("RelaStartID", relaStartID);
						appMap.put("RelaEndID", relaEndID);
						appMap.put("SocialSecurity", socialSecurity);
						appMap.put("MemberID", memberID);
						appMap.put("matchType", "");// TODO 需要处理
					}
					arrInfo.put(appMap);
				}

			}

			unionMap.put("RelaInfoArray", arrInfo);
			unionMap.put("AppntCount", appntCount);
			unionMap.put("InsuredCount", insuredCount);
			unionMap.put("MemberID", memberID);
			resultMap.put("RESULTS", unionMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0049",
					unionMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0049");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 積分規則接口
	 * 
	 * @Title: getRolePionts add by wangej 20150515
	 */
	public static Map<String, Object> getRolePionts(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> unionMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();
		Map<String, Object> map_return = new HashMap<String, Object>();
		PointsCalculate PointsCalculate = new PointsCalculate();
		try {
			// 调用common方法获取积分规则列表
			map_return = PointsCalculate.pointsManage(
					IntegralConstant.POINT_ALL, "", null);
			DataTable dt = (DataTable) map_return.get(IntegralConstant.DATA);
			if (dt == null || dt.getRowCount() <= 0) {
				unionMap.put("PointRoles", "");
			} else {
				int roleLen = dt.getRowCount();
				JSONArray usedInfo = new JSONArray();
				for (int i = 0; i < roleLen; i++) {
					Map<String, Object> usedMap = new HashMap<String, Object>();
					usedMap.put("RoleType", dt.getString(i, "MemberAct"));
					usedMap.put("PointsGive", dt.getString(i, "PointsGive"));
					usedMap.put("RoleName", dt.getString(i, "PointDes"));
					usedMap.put("RoleValue", dt.getString(i, "PointsNum"));
					usedInfo.put(usedMap);
				}
				unionMap.put("PointRoles", usedInfo);
			}
		} catch (Exception e) {
			errorinfos.add("MemberAction000028");
			logger.error("WapMemberUtil-KXBJRT0054-getRolePionts; 错误信息："
					+ e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0054",
					resultMap);
		}
		resultMap.put("RESULTS", unionMap);
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0054");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * wap站 会员推荐积分信息
	 * 
	 * @Title: getMemRecomInfo add by wangej 20150522
	 */
	public static Map<String, Object> getMemRecomInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> unionMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();
		String memberID = StringUtil.noNull((String) tPARAMETERS
				.get("MemberID"));
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0055");
		resultMap.put("STATYS", "true");
		try {

			String sqlString = "SELECT recommendRegPoints,recommendBuyPoints FROM member WHERE id = ? ";
			DataTable dt = new QueryBuilder(sqlString, memberID)
					.executeDataTable();
			if (dt == null || dt.getRowCount() != 1) {
				logger.warn("获取member中推荐数据发生错误，WapMemberUtil - getMemRecomInfo memberid={}", memberID);
			} else {
				unionMap.put("AllRecPoint", dt.getInt(0, "recommendRegPoints")
						+ dt.getInt(0, "recommendBuyPoints"));
				unionMap.put("RecRegPoint", dt.getInt(0, "recommendRegPoints"));
				unionMap.put("RecOrdPoint", dt.getInt(0, "recommendBuyPoints"));
				unionMap.put("RecOrdRate", Config.getValue("RecommBuyPoints"));
				unionMap.put("RecRegCount",
						Config.getValue("RecommRegisterCount"));
			}
		} catch (Exception e) {
			errorinfos.add("MemberAction000029");
			logger.error("WapMemberUtil-KXBJRT0055-getMemRecomInfo; 错误信息："
					+ e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0055",
					resultMap);
		}
		resultMap.put("RESULTS", unionMap);
		return resultMap;
	}

	/**
	 * 会员等级信息
	 * 
	 * @Title: getMemberGradeInfo add by wangej 20150617
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMemberGradeInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> unionMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();
		Map<String, Object> map_return = new HashMap<String, Object>();
		Map<String, Object> map_inner = new HashMap<String, Object>();
		PointsCalculate PointsCalculate = new PointsCalculate();
		try {

			StringBuffer sqlS = new StringBuffer();
			sqlS.append(" SELECT gradeName GradeName, gradeCode GradeCode,preGradeCode GradePreCode, ");
			sqlS.append(" orderCount GradePayCount,sumPrem GradePayAmont,link GradeLink ");
			sqlS.append(" FROM membergrade ORDER BY grade ");
			QueryBuilder qb = new QueryBuilder(sqlS.toString());
			DataTable dt = qb.executeDataTable();

			if (dt == null || dt.getRowCount() <= 0) {
				unionMap.put("MemberGrades", "");
			} else {
				int roleLen = dt.getRowCount();
				JSONArray usedInfo = new JSONArray();
				for (int i = 0; i < roleLen; i++) {
					Map<String, Object> usedMap = new HashMap<String, Object>();
					usedMap.put("GradeName", dt.getString(i, "GradeName"));
					usedMap.put("GradeCode", dt.getString(i, "GradeCode"));
					usedMap.put("GradePreCode", dt.getString(i, "GradePreCode"));
					usedMap.put("GradePayCount",
							dt.getString(i, "GradePayCount"));
					usedMap.put("GradePayAmont",
							dt.getString(i, "GradePayAmont"));
					usedMap.put("GradeLink", dt.getString(i, "GradeLink"));

					// 调用common方法获取积分规则列表
					map_inner.put("MemberGrade", dt.getString(i, "GradeCode"));
					// 会员生日月积分加成
					map_return = PointsCalculate.pointsManage(
							IntegralConstant.POINT_SEARCH,
							IntegralConstant.POINT_SOURCE_BIRTH_MONTH,
							map_inner);
					List<Map<String, Object>> reList = (List<Map<String, Object>>) map_return
							.get(IntegralConstant.DATA);
					if (reList == null || reList.size() <= 0) {
						usedMap.put("GradeBirthdayPonitRate", "0");
					} else {
						usedMap.put("GradeBirthdayPonitRate", reList.get(0)
								.get("PointsNum"));
					}
					// 会员等级积分加成
					map_return = PointsCalculate.pointsManage(
							IntegralConstant.POINT_SEARCH,
							IntegralConstant.POINT_SOURCE_BUY, map_inner);
					reList = (List<Map<String, Object>>) map_return
							.get(IntegralConstant.DATA);
					if (reList == null || reList.size() <= 0) {
						usedMap.put("GradePonitRate", "0");
					} else {
						usedMap.put("GradePonitRate",
								reList.get(0).get("PointsNum"));
					}

					// 现阶段只有VIP为人工 其他的为自动 20150629
					if ("VIP".equals(dt.getString(i, "GradeCode"))) {
						usedMap.put("GradeClass", "1");
					} else {
						usedMap.put("GradeClass", "0");
					}
					usedInfo.put(usedMap);
				}
				unionMap.put("MemberGrades", usedInfo);
			}
		} catch (Exception e) {
			errorinfos.add("MemberAction000030");
			logger.error("WapMemberUtil-KXBJRT0056-getMemberGradeInfo; 错误信息："
					+ e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0056",
					resultMap);
		}
		resultMap.put("RESULTS", unionMap);
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0056");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 根据操作类型得到实体类
	 * 
	 * @param operateType
	 * @param tPARAMETERS
	 * @return
	 */
	public static Object getRelaEntity(String operateType,
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap) {

		MemberService tMemberService = (MemberService) mServiceMap
				.get("MemberService");
		SDRelationAppntService sdRelationAppntService = (SDRelationAppntService) mServiceMap
				.get("SDRelationAppntService");
		SDRelationRecognizeeService sdRelationRecognizeeService = (SDRelationRecognizeeService) mServiceMap
				.get("SDRelationRecognizeeService");
		String memberID = String.valueOf(tPARAMETERS.get("MemberID"));// 会员ID
		Member member = tMemberService.load(memberID);
		String productID = String.valueOf(tPARAMETERS.get("ProductID"));// 产品ID
		String comCode = String.valueOf(tPARAMETERS.get("ComCode"));// 保险公司编码
		String method = String.valueOf(tPARAMETERS.get("Method"));// 操作方法 Add：新增
		// Update：修改
		// Delete：删除
		String relaName = String.valueOf(tPARAMETERS.get("RelaName"));// 姓名
		// String relaEnName =
		// String.valueOf(tPARAMETERS.get("RelaEnName"));//英文名
		String relaSex = String.valueOf(tPARAMETERS.get("RelaSex"));// 性别编码
		String relaSexName = String.valueOf(tPARAMETERS.get("RelaSexName"));// 性别名称
		String relaIdentifyType = String.valueOf(tPARAMETERS
				.get("RelaIdentifyType"));// 证件类型编码
		String relaIdentifyTypeName = String.valueOf(tPARAMETERS
				.get("RelaIdentifyTypeName"));// 证件类型名称
		String relaIdentify = String.valueOf(tPARAMETERS.get("RelaIdentify"));// 证件号
		String relaBirthday = String.valueOf(tPARAMETERS.get("RelaBirthday"));// 出生日期
		String relaAge = String.valueOf(tPARAMETERS.get("RelaAge"));// 年龄
		String relaEmail = String.valueOf(tPARAMETERS.get("RelaEmail"));// 邮箱
		String relaMobile = String.valueOf(tPARAMETERS.get("RelaMobile"));// 手机号
		// String relaZipCode =
		// String.valueOf(tPARAMETERS.get("RelaZipCode"));//邮编
		// String relaOcc1 = String.valueOf(tPARAMETERS.get("RelaOcc1"));//职业
		// String relaOcc2 = String.valueOf(tPARAMETERS.get("RelaOcc2"));//二级职业
		// String relaOcc3 = String.valueOf(tPARAMETERS.get("RelaOcc3"));//三级职业
		// String relaArea1 = String.valueOf(tPARAMETERS.get("RelaArea1"));//省
		// String relaArea2 = String.valueOf(tPARAMETERS.get("RelaArea2"));//市
		// String relaArea3 = String.valueOf(tPARAMETERS.get("RelaArea3"));//区
		// String relaAddress =
		// String.valueOf(tPARAMETERS.get("RelaAddress"));//地址
		// String relaStartID =
		// String.valueOf(tPARAMETERS.get("RelaStartID"));//证件有效起期
		// String relaEndID =
		// String.valueOf(tPARAMETERS.get("RelaEndID"));//证件有效止期
		// String SocialSecurity =
		// String.valueOf(tPARAMETERS.get("SocialSecurity"));//是否有医保
		try {
			if ("Appnt".equals(operateType)) {
				SDRelationAppnt appnt = new SDRelationAppnt();
				if ("Update".equals(method)) {
					// 修改操作
					appnt = sdRelationAppntService.getSDRelationAppntInfo(
							comCode, productID, memberID, relaName);
				}
				appnt.setProductId(productID);
				appnt.setRemark(comCode);
				appnt.setApplicantName(relaName);
				// appnt.setApplicantEnName(relaEnName);
				appnt.setApplicantSex(relaSex);
				appnt.setApplicantSexName(relaSexName);
				appnt.setApplicantIdentityType(relaIdentifyType);
				appnt.setApplicantIdentityTypeName(relaIdentifyTypeName);
				appnt.setApplicantIdentityId(relaIdentify);
				appnt.setApplicantBirthday(relaBirthday);
				appnt.setApplicantAge(relaAge);
				appnt.setApplicantMail(relaEmail);
				// appnt.setApplicantArea1(relaArea1);
				// appnt.setApplicantArea2(relaArea2);
				// appnt.setApplicantArea3(relaArea3);
				// appnt.setApplicantAddress(relaAddress);
				// appnt.setApplicantZipCode(relaZipCode);
				appnt.setApplicantMobile(relaMobile);
				// appnt.setApplicantOccupation1(relaOcc1);
				// appnt.setApplicantOccupation2(relaOcc2);
				// appnt.setApplicantOccupation3(relaOcc3);
				// appnt.setApplicantStartID(relaStartID);
				// appnt.setApplicantEndID(relaEndID);
				appnt.setmMember(member);
				appnt.setMemberId(member.getId());
				// appnt.setSocialSecurity(SocialSecurity);
				appnt.setModifyDate(sdf.parse(PubFun.getCurrentDate() + " "
						+ PubFun.getCurrentTime()));
				return appnt;
			} else {
				SDRelationRecognizee insured = new SDRelationRecognizee();
				if ("Update".equals(method)) {
					// 修改操作
					insured = sdRelationRecognizeeService
							.getSDRelationRecognizeeInfo(comCode, productID,
									memberID, relaName);
				}
				insured.setProductId(productID);
				insured.setRemark(comCode);
				insured.setRecognizeeName(relaName);
				// insured.setRecognizeeEnName(relaEnName);
				insured.setRecognizeeSex(relaSex);
				insured.setRecognizeeSexName(relaSexName);
				insured.setRecognizeeIdentityType(relaIdentifyType);
				insured.setRecognizeeIdentityTypeName(relaIdentifyTypeName);
				insured.setRecognizeeIdentityId(relaIdentify);
				insured.setRecognizeeBirthday(relaBirthday);
				insured.setRecognizeeAge(relaAge);
				insured.setRecognizeeMail(relaEmail);
				// insured.setRecognizeeArea1(relaArea1);
				// insured.setRecognizeeArea2(relaArea2);
				// insured.setRecognizeeArea3(relaArea3);
				// insured.setRecognizeeAddress(relaAddress);
				// insured.setRecognizeeZipCode(relaZipCode);
				insured.setRecognizeeMobile(relaMobile);
				// insured.setRecognizeeOccupation1(relaOcc1);
				// insured.setRecognizeeOccupation2(relaOcc2);
				// insured.setRecognizeeOccupation3(relaOcc3);
				// insured.setRecognizeeStartID(relaStartID);
				// insured.setRecognizeeEndID(relaEndID);
				insured.setmMember(member);
				insured.setMemberId(member.getId());
				insured.setModifyDate(sdf.parse(PubFun.getCurrentDate() + " "
						+ PubFun.getCurrentTime()));
				return insured;
			}
		} catch (Exception e) {
			logger.error("新增或修改常用投、被保人信息异常，信息如下：" + e.getMessage(), e);
			return null;
		}
	}

	public static String replaceHTML(String str) {
		String s1 = str.replaceAll("</?[^>]+>", "");
		s1 = s1.replaceAll("\\&nbsp;", " ");
		s1 = s1.replaceAll("\\&lt;", "<");
		s1 = s1.replaceAll("\\&gt;", ">");
		s1 = s1.replaceAll("\\&mdash;", "");
		s1 = s1.replaceAll("\\&deg;", "");
		s1 = s1.replaceAll("\\&ldquo;", "");
		s1 = s1.replaceAll("\\&rdquo;", "");
		s1 = s1.replaceAll("\\&middot;", "");
		s1 = s1.replaceAll("\\&lsquo;", "‘");
		s1 = s1.replaceAll("\\&rsquo;", "’");
		s1 = s1.replaceAll("\\&hellip;", "…");
		s1 = s1.replaceAll("&#039;", "'");
		s1 = s1.replaceAll("&amp;", "&");
		return s1;
	}

	/**
	 * 
	 * @Title: provideCoupon
	 * @Description: TODO(注册自动发送优惠券)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	static public String provideCoupon(String mail, String mobile,
			String memberid) {
		// 公共参数
		Map<String, Object> data = new HashMap<String, Object>();
		SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
		QueryBuilder qb_coupon = new QueryBuilder(
				" SELECT cou.id AS id ,cou.status as status FROM couponinfo cou,sdcouponactivityinfo sdac WHERE sdac.TYPE='0' AND sdac.STATUS='3'  AND cou.batch=sdac.batch order by cou.status ");
		DataTable qt = qb_coupon.executeDataTable();
		// id值
		String coupon_id = qt.getString(0, 0);
		// 状态
		String status = qt.getString(0, 1);
		sdcouponschema.setId(coupon_id);
		Transaction trans = new Transaction();
		// 查询
		if (sdcouponschema.fill()) {
			if (StringUtil.isMail(mail)) {
				if (!("0".equals(status))) {
					// 生成优惠劵ID
					String year = DateUtil.getCurrentDateTime("yyyy");
					String id = year + NoUtil.getMaxNo("CouponSn", 12);
					// 优惠券ID值
					sdcouponschema.setId(id);
					// 优惠券号
					sdcouponschema.setCouponSn(DigestUtils.md5Hex(id));
					// 创建时间
					sdcouponschema.setCreateDate(new Date());
					// 创建者
					sdcouponschema.setCreateUser("admin");
				}
				// 状态2已发放
				sdcouponschema.setStatus("2");
				// 将会员id关联到优惠券表中
				sdcouponschema.setMemberId(memberid);
				sdcouponschema.setMail(mail);
				// 更改人和更改时间
				String now = DateUtil.getCurrentDateTime();

				// 发放时间
				sdcouponschema.setProp2(now);
				if (!("0".equals(status))) {
					trans.add(sdcouponschema, Transaction.INSERT);
				} else {
					trans.add(sdcouponschema, Transaction.UPDATE);
				}
				if (trans.commit()) {
					// 如果需要信息提醒
					if ("Y".equals(sdcouponschema.getRemindFlag())) {
						// 发送邮件
						Member member = new Member();
						data.put("Member", member);
						// 优惠券说明
						try {
							data.put(
									"direction",
									java.net.URLDecoder.decode(
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
						// 非折扣券
						if (StringUtil.isEmpty(sdcouponschema.getProp3())
								|| "01".equals(sdcouponschema.getProp3())) {
							// 优惠金额
							data.put("parValueShow", sdcouponschema.getParValue()
									+ "元");

							if (ActionUtil.sendMail("wj00088", mail,data)) {
								logger.info("注册送优惠券活动，发送邮件成功");
							} else {
								logger.warn("注册送优惠券活动，发送邮件失败");
							}
						} else { // 折扣券
							// 优惠金额
							data.put("parValueShow", sdcouponschema.getProp4()
									+ "折");

							if (ActionUtil.sendMail("wj00113",mail, data)) {
								logger.info("注册送优惠券活动，发送邮件成功");
							} else {
								logger.warn("注册送优惠券活动，发送邮件失败");
							}
						}
					}
				} else {
					logger.warn("注册送优惠券活动，更改优惠券状态错误");
				}
			} else if (StringUtil.isMobileNO(mobile)) {
				if (!("0".equals(status))) {
					// 生成优惠劵ID
					String year = DateUtil.getCurrentDateTime("yyyy");
					String id = year + NoUtil.getMaxNo("CouponSn", 12);
					// 优惠券ID值
					sdcouponschema.setId(id);
					// 优惠券号
					sdcouponschema.setCouponSn(DigestUtils.md5Hex(id));
					// 创建时间
					sdcouponschema.setCreateDate(new Date());
					// 创建者
					sdcouponschema.setCreateUser("admin");
				}
				// 状态2已发放
				sdcouponschema.setStatus("2");
				// 将会员id关联到优惠券表中
				sdcouponschema.setMemberId(memberid);
				sdcouponschema.setMobile(mobile);
				// 发放时间
				sdcouponschema.setProp2(DateUtil.getCurrentDateTime());
				if (!("0".equals(status))) {
					trans.add(sdcouponschema, Transaction.INSERT);
				} else {
					trans.add(sdcouponschema, Transaction.UPDATE);
				}
				if (trans.commit()) {
                    // 如果需要信息提醒
                    if ("Y".equals(sdcouponschema.getRemindFlag())) {
                        // 优惠券结束时间
                        String endtime = String
                                .valueOf(sdcouponschema.getEndTime());
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
                        if (ActionUtil.sendSms("wj00090", mobile, sendData)) {
                            logger.info("注册送优惠券活动，发送短信成功");
                        } else {
                            logger.warn("注册送优惠券活动，发送短信失败");
                        }
                    }
				}
			} else {
				logger.warn("注册送优惠券活动邮箱和手机号错误！");
			}
		}
		return "";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
