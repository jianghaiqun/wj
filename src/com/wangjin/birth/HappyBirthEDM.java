/**简短介绍 
  * <p>Date        :2014-08-04</p> 
  * <p>Module      :生日EDM </p> 
  * <p>Description: 描述信息</p> 
  * <p>Remark      : </p> 
  * @author XXX
  * @version  
  * <p>------------------------------------------------------------</p> 
  * <p>  修改历史</p> 
  * <p>   序号               日期                              修改人                                 修改原因</p> 
  * <p>    1     2014-08-04   jiaomengying   req20140522001-生日EDM  </p> 
  */ 
package com.wangjin.birth;

import cn.com.sinosoft.action.shop.MemberCenterAction;
import cn.com.sinosoft.common.email.MessageConstant;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HappyBirthEDM extends ConfigEanbleTaskManager { 
	public static final String CODE = "com.wangjin.birth.HappyBirthEDM";

	public boolean isRunning(long id) {
		return false;
	}
	
	/**
	 * 
	 * @Title: checkBirth
	 * @Description: TODO(生日当天发送生日祝福邮件)
	 * @return boolean 返回类型
	 * @author jiaomengying
	 */
	public boolean checkBirth() {
		String sql = "SELECT applicantmail,applicantname, applicantsexname,DATE_FORMAT(applicantBirthday,'%m-%d') as applicantBirthday," +
				" DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 30 DAY),'%Y年%m月%d日') endTime FROM SDInformationAppnt WHERE DATE_FORMAT(applicantBirthday,'%m-%d')=DATE_FORMAT(NOW(),'%m-%d') " +
				" GROUP BY applicantmail";
		QueryBuilder querybuilder = new QueryBuilder(sql);
		DataTable dt = querybuilder.executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String mail = String.valueOf(dt.get(i, 0));
				if ("".equals(mail) || mail == null) {
					logger.warn("投保人为{}的邮箱为空", String.valueOf(dt.get(i, 1)));
				} else {
					//获取星座
					String birthday=String.valueOf(dt.get(i, 4));
					Map<String,String> map=getAstro(birthday);
					if("".equals(map.get("content"))||map.get("content")==null){
						return false;
					}
					// 发送邮件
					if (this.postEmail(mail,map.get("title"),map.get("content"))) {
						logger.info("邮件发送成功");
					}
				}
			}
			return true;
		} else {
			logger.info("没有需要发送生日祝福的邮件");
			return false;
		}
	}

	/**
	 * 
	 * @Title: postEmail
	 * @Description: TODO(生日祝福发送邮件)
	 * @return boolean 返回类型
	 * @author jiaomengying
	 */
	public boolean postEmail(String mail,String title,String content) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			title = java.net.URLDecoder.decode(title, "utf-8");
			data.put("title", title);
			data.put("content", java.net.URLDecoder.decode(content, "utf-8"));
			data.put(MessageConstant.PARAM_SUBJECT_NAME, title);
			data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		// 发邮件
		if (ActionUtil.sendMail("wj00096", mail, data)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: getAstro
	 * @Description: TODO(获取内容)
	 * @return String 返回类型
	 * @author jiaomengying
	 */
	private Map<String, String> getAstro(String birthday) {
		String title = "生日祝福-开心保";
		// 返回索引指向的星座string
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("content", birthday);
		return map;
	}

	/**
	 * 会员生日祝福提醒
	 * 
	 * @return
	 */
	public boolean memBirthdayBlessing() {
		memberSet membertset = new memberSet();
		memberSchema memberschema = new memberSchema();
		membertset = memberschema
				.query(new QueryBuilder(
						"where birthday is not null and birthday != '' and SUBSTRING(birthday,6,2) = DATE_FORMAT(NOW(),'%m')"));
		if (membertset != null && membertset.size() > 0) {
			MemberCenterAction action = new MemberCenterAction();
			Map<String, Object> map = new HashMap<String, Object>();
			String memName = "";
			// 取得各会员等级的生日月积分倍数
			PointsCalculate PointsCalculate = new PointsCalculate();
			Map<String, Object> param = new HashMap<String, Object>();
			// 记录各等级生日月积分倍数，不用重复查询
			Map<String, String> gradeBithMap = new HashMap<String, String>();
			// 等级
			String grade = "";
			// 积分倍数
			String PointMultiple = "";
			for (int i = 0; i < membertset.size(); i++) {
				memberschema = membertset.get(i);
				memName = "会员";
				// 判断当前时间是否是生日月
				if (action.isMemBirthMonth(memberschema.getbirthday(),
						memberschema.getbirthYear())) {

					// 判断是否是VIP会员
					if ("Y".equals(memberschema.getvipFlag())) {
						grade = "VIP";
					} else {
						grade = memberschema.getgrade();
					}
					// 已查出该会员等级生日月积分倍数的直接取得
					if (gradeBithMap.containsKey(grade)) {
						PointMultiple = gradeBithMap.get(grade);
					} else {
						try {
							// 查询该会员等级生日月积分倍数
							param = new HashMap<String, Object>();
							param.put("MemberGrade", grade);
							Map<String, Object> result = PointsCalculate
									.pointsManage(
											IntegralConstant.POINT_SEARCH,
											IntegralConstant.POINT_SOURCE_BIRTH_MONTH,
											param);
							if (result != null
									&& !result.isEmpty()
									&& !StringUtil.isEmpty(result
											.get(IntegralConstant.ACTION_POINTS))) {
								// 取出生日月加成积分 加1换算成倍数
								PointMultiple = NumberUtil.round(Double
										.valueOf((String) result
												.get(IntegralConstant.ACTION_POINTS)) + 1, 2)
										+ "";
								gradeBithMap.put(grade, StringUtil.subZeroAndDot(PointMultiple));
							} else {
								// 生日月积分无加成 不发送生日祝福提醒信息
								continue;
							}
						} catch (Exception e) {
							logger.error("HappyBirthEDM.java--memBirthdayBlessing 会员查询生日月加成错误！会员ID:"
									+ memberschema.getid() + e.getMessage(), e);
							continue;
						}
					}

					if (StringUtil.isNotEmpty(memberschema.getrealName())) {
						memName = memberschema.getrealName();
					}
					map = new HashMap<String, Object>();
					map.put("PointMultiple", PointMultiple);
					map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
					// 邮箱注册用户 发邮件祝福
					if (!"1".equals(memberschema.getregisterType())
							&& StringUtil.isNotEmpty(memberschema.getemail())) {
						ActionUtil.sendMail("wj00125", memberschema.getemail(), map);
						// 手机注册用户发短信祝福
					} else if (StringUtil.isNotEmpty(memberschema.getmobileNO())) {
						ActionUtil.sendSms("wj00122", memberschema.getmobileNO(), memName + ";" + PointMultiple);
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 定时任务调用主方法
	 * 
	 * @param id
	 */
	public void execute(long id) {
		if ("-1".equals(id + "")) {
			if (checkBirth()) {
				logger.info("生日祝福邮件发送成功！");
			}
		} else if ("0".equals(id + "")) {
			if (memBirthdayBlessing()) {
				logger.info("会员生日祝福邮件/短信发送成功！");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("-1", "生日祝福EDM");
		map.put("0", "会员生日祝福提醒");
		return map;
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "生日祝福EDM";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendItem#getID()
	 */
	@Override
	public String getID() {
		return "com.wangjin.birth.HappyBirthEDM";
	}

}
