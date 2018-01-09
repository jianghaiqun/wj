package com.sinosoft.inter;

import cn.com.sinosoft.action.shop.AjaxPriceAction;
import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.common.email.MessageMqService;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.util.SpringContextUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.stat.impl.OperationLogAction;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.SDInterActionSchema;
import com.sinosoft.schema.SDInterActionSet;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 横向接口
 * ============================================================================
 * 
 * KEY:SINOSOFT9A02B310F538BFE58B8F04583E341B6E
 * ============================================================================
 */

public class ActionUtil {
	private static final Logger logger = LoggerFactory.getLogger(ActionUtil.class);
	/**
	 * a0010.ftl-开心保在线投保 - 待支付提醒. a0051.ftl-开心保在线投保 - 订单支付成功（自动注册）.
	 * a0052.ftl-开心保在线投保 - 订单支付成功（未登录） . a0020.ftl-开心保在线投保 - 保单查看通知.
	 * a0029.ftl-开心保在线投保 - 电子保单（未登录）--禁用 . a0030.ftl-开心保在线投保 - 电子保单（登录） --禁用.
	 * a0049.ftl-开心保在线投保 - 密码找回邮件. a0041.ftl-开心保在线投保 - 密码重置邮件. a0004.ftl-开心保在线投保
	 * - 邮箱验证邮件. a0005.ftl-开心保在线投保 - 邮箱验证邮件. a0002.ftl-开心保在线投保 - 账户注册成功.
	 * a0048.ftl-开心保在线投保 - 邮箱验证邮件. a0011b.ftl-开心保在线投保 - 订单支付成功 -是 (注释：a0011).
	 * a0097.ftl-开心保在线投保 - 会员评论邮件. a0098.ftl-开心保在线投保 - 保单过期提醒. a0133.ftl-开心保在线投保
	 * - 续保提醒. a0208.ftl-理赔结案通知邮件 a0209.ftl-理赔退回通知邮件 a0308.ftl-理赔邮寄材料通知邮件
	 * a0311.ftl-理赔补充材料通知邮件 a0314.ftl-开心保-验证码（礼品兑换） a0318.ftl-开心保-礼品兑换成功通知
	 * SQL:SELECT * FROM SDINTERACTION
	 */
	private static final String EMAILTYPE = "a0002,a0004,a0005,a0006,a0010,a0011,a0020,a0041,a0048,a0049,a0051,a0052,a0096,a0097,a0098,a0011b,a0113,a0088,a0089,a0125,a0126,a0127,a0128,a0129,a0130,a0131,a0132,a0133,a0208,a0209,a0308,a0311,a0314,a0318";

	private static MessageMqService messageMqService;

	private static MessageMqService getMqService() {
		if (messageMqService == null) {
			messageMqService = (MessageMqService) SpringContextUtil.getBean("messageMqService");
		}
		return messageMqService;
	}

	/**
	 * sendMessage:发送消息到email系统. <br/>
	 * 
	 * @author guozc
	 * @param actionId
	 * @param data
	 *            自定义参数
	 */
	public static boolean sendMessage(String actionId, Map<String, Object> data) {
		if (StringUtil.isEmpty(actionId)) {
			logger.warn("actionId不能为空");
			return false;
		}
		try {
			getMqService().sendMessage(actionId, "wj", data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * sendGeneralSms:发送短信(需要模板). <br/>
	 * 
	 * @author guozc
	 * @param actionId
	 * @param mobile
	 *            接收手机号码
	 * @param sendData
	 *            发送参数，参数用“;”分隔
	 */
	public static boolean sendSms(String actionId, String mobile, String sendData) {
		if (StringUtil.isEmpty(actionId)) {
			logger.warn("actionId不能为空");
			return false;
		}
		if (StringUtil.isEmpty(mobile)) {
			logger.warn("mobile不能为空");
			return false;
		}
		if (sendData == null) {
			logger.warn("sendData不能为null");
			return false;
		}
		try {
			getMqService().sendSms(actionId, "wj", mobile, sendData);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * sendGeneralSms:发送通用短信(不需要模板). <br/>
	 * 
	 * @author guozc
	 * @param mobile
	 *            接收手机号码
	 * @param sendData
	 *            发送数据
	 */
	public static boolean sendGeneralSms(String mobile, String sendData) {
		if (StringUtil.isEmpty(mobile)) {
			logger.warn("mobile不能为空");
			return false;
		}
		if (StringUtil.isEmpty(sendData)) {
			logger.warn("sendData不能为空");
			return false;
		}
		try {
			getMqService().sendGeneralSms("wj", mobile, sendData);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * sendMail:(发送邮件，需要模板). <br/>
	 * 
	 * @author guozc
	 * @param actionId
	 * @param receiver
	 *            接收邮件
	 * @param freemarkerData
	 *            邮件模板数据
	 */
	public static boolean sendMail(String actionId, String receiver, Map<String, Object> freemarkerData) {
		if (StringUtil.isEmpty(actionId)) {
			logger.warn("actionId不能为空");
			return false;
		}
		if (StringUtil.isEmpty(receiver)) {
			logger.warn("receiver不能为空");
			return false;
		}
		freemarkerData.put(MessageConstant.PARAM_RECEIVER_NAME, receiver);
		try {
			getMqService().sendMail(actionId, "wj", freemarkerData);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * sendMail:(发送邮件，需要模板,带附件). <br/>
	 * 
	 * @author guozc
	 * @param actionId
	 * @param receiver
	 *            接收邮件
	 * @param attachments
	 *            附件 [{"name":"附件名称","path":"附件网络地址"}]
	 * @param freemarkerData
	 *            邮件模板数据
	 */
	public static boolean sendMail(String actionId, String receiver, List<Map<String, Object>> attachments,
			Map<String, Object> freemarkerData) {
		if (attachments == null) {
			logger.warn("attachments不能为空");
			return false;
		}
		freemarkerData.put(MessageConstant.PARAM_ATTACHMENTS, attachments);
		return sendMail(actionId, receiver, freemarkerData);
	}

	/**
	 * validGeneralMailParam:(发送不需要模板邮件公共参数校验). <br/>
	 * 
	 * @author guozc
	 * @param receiver
	 *            邮件接收地址(多个地址用英文逗号分隔)
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @return
	 */
	private static boolean validGeneralMailParam(String receiver, String subject, String content) {
		if (StringUtil.isEmpty(receiver)) {
			logger.warn("receiver不能为空");
			return false;
		}
		if (StringUtil.isEmpty(subject)) {
			logger.warn("subject不能为空");
			return false;
		}
		if (StringUtil.isEmpty(content)) {
			logger.warn("content不能为空");
			return false;
		}
		return true;
	}

	/**
	 * sendGeneralMail:(发送邮件，不需要模板). <br/>
	 * 
	 * @author guozc
	 * @param receiver
	 *            邮件接收地址(多个地址用英文逗号分隔)
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @return
	 */
	public static boolean sendGeneralMail(String receiver, String subject, String content) {
		if (!validGeneralMailParam(receiver, subject, content)) {
			return false;
		}
		try {
			getMqService().sendGeneralMail("wj", receiver, null, subject, content, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * sendGeneralMail:(发送邮件，不需要模板). <br/>
	 * 
	 * @author guozc
	 * @param receiver
	 *            邮件接收地址(多个地址用英文逗号分隔)
	 * @param ccAddress
	 *            抄送地址(多个地址用英文逗号分隔)
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @return
	 */
	public static boolean sendGeneralMail(String receiver, String ccAddress, String subject, String content) {
		if (!validGeneralMailParam(receiver, subject, content)) {
			return false;
		}
		try {
			getMqService().sendGeneralMail("wj", receiver, ccAddress, subject, content, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * sendGeneralMail:(发送邮件，不需要模板). <br/>
	 * 
	 * @param receiver
	 *            邮件接收地址(多个地址用英文逗号分隔)
	 * @param ccAddress
	 *            抄送地址(多个地址用英文逗号分隔)
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachments
	 *            附件 [{"name":"附件名称","path":"附件地址"}]
	 * @return
	 */
	public static boolean sendGeneralMail(String receiver, String ccAddress, String subject, String content,
			List<Map<String, Object>> attachments) {
		if (!validGeneralMailParam(receiver, subject, content)) {
			return false;
		}
		try {
			getMqService().sendGeneralMail("wj", receiver, ccAddress, subject, content, attachments);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * sendGeneralMail:(发送邮件，不需要模板). <br/>
	 * 
	 * @author guozc
	 * @param receiver
	 *            邮件接收地址(多个地址用英文逗号分隔)
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachments
	 *            附件 [{"name":"附件名称","path":"附件地址"}]
	 * @return
	 */
	public static boolean sendGeneralMail(String receiver, String subject, String content,
			List<Map<String, Object>> attachments) {
		if (!validGeneralMailParam(receiver, subject, content)) {
			return false;
		}
		try {
			getMqService().sendGeneralMail("wj", receiver, null, subject, content, attachments);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * sendWarnMail:(发送告警邮件). <br/>
	 * 
	 * @author guozc
	 * @param errMsg
	 *            告警信息
	 * @return
	 */
	public static boolean sendAlarmMail(String errMsg) {
		if (StringUtil.isEmpty(errMsg)) {
			logger.warn("告警信息不能为空");
			return false;
		}
		String toMail = Config.getValue("InsureErrorMail");
		if (StringUtil.isNotEmpty(toMail)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("errMsg", errMsg);
			return sendMail("wj01014", toMail, data);
		}
		return true;
	}

	/**
	 * sendInsureAlarmMailByOrderSn:(发送承保失败告警邮件). <br/>
	 * 
	 * @author guozc
	 * @param orderSn
	 *            订单号
	 * @param errMsg
	 *            告警信息
	 * @return
	 */
	public static boolean sendInsureAlarmMailByOrderSn(String orderSn, String errMsg) {
		if (StringUtil.isEmpty(errMsg)) {
			logger.warn("告警信息不能为空");
			return false;
		}
		String toMail = Config.getValue("InsureErrorMail");
		if (StringUtil.isNotEmpty(toMail)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("errmsg", errMsg);
			data.put("orderSn", orderSn);
			data.put("receiver", toMail);
			ActionUtil.sendMessage("wj00031", data);
		}
		return true;
	}

	/**
	 * sendInsureAlarmMailByOrderSn:(发送承保失败告警邮件). <br/>
	 * 
	 * @author guozc
	 * @param orderSn
	 *            被保人号
	 * @param errMsg
	 *            告警信息
	 * @return
	 */
	public static boolean sendInsureAlarmMailByInsuredSn(String insuredSn, String errMsg) {
		if (StringUtil.isEmpty(errMsg)) {
			logger.warn("告警信息不能为空");
			return false;
		}
		String toMail = Config.getValue("InsureErrorMail");
		if (StringUtil.isNotEmpty(toMail)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("errmsg", errMsg);
			data.put("insuredSn", insuredSn);
			data.put("receiver", toMail);
			ActionUtil.sendMessage("wj00031", data);
		}
		return true;
	}

	/**
	 * sendInsureAlarmMailByOrderSn:(发送承保失败告警邮件). <br/>
	 * 
	 * @author guozc
	 * @param orderSn
	 *            支付流水号
	 * @param errMsg
	 *            告警信息
	 * @return
	 */
	public static boolean sendInsureAlarmMailByPaySn(String paySn, String errMsg) {
		if (StringUtil.isEmpty(errMsg)) {
			logger.warn("告警信息不能为空");
			return false;
		}
		String toMail = Config.getValue("InsureErrorMail");
		if (StringUtil.isNotEmpty(toMail)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("errmsg", errMsg);
			data.put("paySn", paySn);
			data.put("receiver", toMail);
			ActionUtil.sendMessage("wj00031", data);
		}
		return true;
	}

	/**
	 * 
	 * @param actionId
	 *            动作id.
	 * @param map
	 *            参数集合
	 * 
	 * @param request
	 * 
	 * @return boolean
	 */
	@SuppressWarnings({ "static-access" })
	public static boolean dealAction(String actionId, Map<String, Object> data, HttpServletRequest request) {
		try {
			SDInterActionSet tSDInterActionSet = check(actionId);// 获取动作信息
			logger.info("调用横向接口开始```");
			if (tSDInterActionSet.size() == 0 || tSDInterActionSet.size() > 1) {
				logger.info("横向接口表{}数据重复", actionId);
			}
			SDInterActionSchema tSDInterActionSchema = tSDInterActionSet.get(0);
			boolean result = true;
			// 处理积分接口
			if ("1".equals(tSDInterActionSchema.getisIntegral())) {
				IntegralAction tIntegralAction = new IntegralAction();
				result = tIntegralAction.deal(actionId, data, tSDInterActionSchema.getActionType());
			}

			// 处理经验接口
			if ("1".equals(tSDInterActionSchema.getisExperience())) {
				ExperienceAction tExperienceAction = new ExperienceAction();
				result = tExperienceAction.deal(actionId, data, tSDInterActionSchema.getActionType());
			}
			// 处理抽奖活动接口
			if ("1".equals(tSDInterActionSchema.getisAct())) {
				LotteryAction lotteryAction = new LotteryAction();
				result = lotteryAction.deal(actionId, data, tSDInterActionSchema.getActType());
			}
			// 记录用户操作日志接口
			if (request != null) {
				if ("1".equals(tSDInterActionSchema.getisLog())) {
					if (StringUtil.isNotEmpty(actionId)) {
						OperationLogAction operLogAction = new OperationLogAction();
						operLogAction.saveOperLog(request, actionId);
					}
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("横向接口出错" + e.getMessage(), e);
			return false;
		}
	}

	private static SDInterActionSet check(String actionId) {
		SDInterActionSchema tSDInterActionSchema = new SDInterActionSchema();
		SDInterActionSet tSDInterActionSet = tSDInterActionSchema
				.query(new QueryBuilder("where ActionId ='" + actionId + "'"));
		return tSDInterActionSet;
	}

	/**
	 * @Title: getMailConfig.
	 * @Description: TODO(邮件发送-获取邮件所设置活动、产品等配置). 注意：p_emailType 需 配置 EMAILTYPE
	 *               常量使用.
	 * @return Map<String,Object> 返回类型.
	 * @author CongZN.
	 */
	public static Map<String, Object> getMailConfig(Map<String, Object> p_data, String p_emailType, String p_Email) {

		String front = Config.getFrontServerContextPath();
		p_data.put("front", front);

		getMailConfigMember(p_data, p_Email);
		getMailConfigProduct(p_data, p_emailType);

		Map<String, String> activityMap;
		List<Map<String, String>> activityList = new ArrayList<Map<String, String>>();

		Map<String, String> productMap;
		List<Map<String, String>> productList = new ArrayList<Map<String, String>>();

		String[] emailArr = EMAILTYPE.split(",");

		boolean state = false;
		for (int k = 0; k < emailArr.length; k++) {
			if (emailArr[k].equals(p_emailType)) {
				state = true;
				break;
			}
		}

		if (!state) {
			p_data.put("MailActivityList", activityList);
			p_data.put("MailProductList", productList);
			return p_data;
		}

		try {

			QueryBuilder query_mailConfig = new QueryBuilder(
					"select z1.ActivityFlag,z1.ProductFlag,z1.Product,z2.ImagePath,z2.ImageLink,z2.ImageDesc,(select memo from zdcode where codetype='MailConfigRiskType' and codeValue = z1.ProductCategory) as ProductCategory from zdmailmouldconfig z1 left join zdmailconfigimage z2 on z1.emailType = z2.emailType where z1.emailType = ?");
			query_mailConfig.add(p_emailType);
			String activityFlag = "";
			String productFlag = "";
			String productID = "";
			String productCategory = "";

			String ServerContext = Config.getServerContext();

			DataTable productMailConfig = query_mailConfig.executeDataTable();
			for (int i = 0; i < productMailConfig.getRowCount(); i++) {

				// 活动 开启标记 Y 启用 N 禁用
				activityFlag = productMailConfig.getString(i, "ActivityFlag");
				// 产品 开启标记 Y 启用 N 禁用
				productFlag = productMailConfig.getString(i, "ProductFlag");

				productID = productMailConfig.getString(i, "Product");

				// 活动、查看明细
				if ("Y".equals(activityFlag)) {

					activityMap = new HashMap<String, String>();

					String imagePath = productMailConfig.getString(i, "ImagePath");
					String imageDesc = productMailConfig.getString(i, "ImageDesc");
					String imageLink = productMailConfig.getString(i, "ImageLink");

					activityMap.put("ImagePath", ServerContext + File.separator + imagePath);
					activityMap.put("ImageDesc", imageDesc);
					activityMap.put("ImageLink", imageLink);

					activityList.add(activityMap);
				}

				productCategory = productMailConfig.getString(i, "ProductCategory");
			}

			p_data.put("ProductCategory", productCategory);

			// 如果模板配置了具体某一种类的推荐产品，那么则取特定产品类型的推荐产品
			if ("Y".equals(productFlag) && p_data.get("OrderSn") != null) {
				// 获取订单产品种类
				QueryBuilder query_producttype = new QueryBuilder(
						"SELECT b.ProductType FROM sdinformation a,sdproduct b"
								+ " WHERE a.productId = b.ProductID AND a.orderSn = '" + (String) p_data.get("OrderSn")
								+ "'");
				DataTable producttypResult = query_producttype.executeDataTable();
				String productType = null;
				if (producttypResult.getRowCount() != 0) {
					productType = producttypResult.getString(0, "ProductType");
					// 根据订单产品种类获取品类推荐产品
					QueryBuilder query_category_product = new QueryBuilder(
							"select b.product From zdmailmouldcategory a,ZDMailCategoryProduct b "
									+ "where a.ProductCategory = b.ProductCategory and a.ProductType = '" + productType
									+ "' and a.EmailType = '" + p_emailType + "'");
					DataTable categoryProductResult = query_category_product.executeDataTable();
					if (categoryProductResult.getRowCount() != 0) {
						String relpro = categoryProductResult.getString(0, "product");
						if (StringUtils.isNotBlank(relpro)) {
							productID = relpro;
						}
					}
				}
			}

			// 产品
			if ("Y".equals(productFlag) && StringUtil.isNotEmpty(productID)) {
				QueryBuilder query_product = new QueryBuilder(
						"select ProductID,ProductName,Url,LogoLink,InitPrem,SupplierCode2 from SDSearchRelaProduct where ProductID in ("
								+ productID + ")");
				DataTable productResult = query_product.executeDataTable();
				AjaxPriceAction ap = new AjaxPriceAction();
				for (int i = 0; i < productResult.getRowCount(); i++) {

					String newPrice = ap.queryAjaxPrice(productResult.getString(i, "ProductID"), "wj", "");

					productMap = new HashMap<String, String>();

					String productName = productResult.getString(i, "ProductName");
					String url = productResult.getString(i, "Url");
					String LogoLink = productResult.getString(i, "LogoLink");

					productMap.put("ProductName", productName);
					productMap.put("Url", url);
					productMap.put("LogoLink", LogoLink);
					productMap.put("ComanyPic", "http://resource.kaixinbao.com/active_file/images/email/logo/"
							+ productResult.getString(i, "SupplierCode2") + ".gif");
					productMap.put("InitPrem", newPrice);

					productList.add(productMap);
				}
			}

			// 活动信息
			p_data.put("MailActivityList", activityList);
			// 产品信息
			p_data.put("MailProductList", productList);

		} catch (Exception e) {
			logger.error("邮件发送模板-获取活动、产品信息方法异常!");
		}

		return p_data;
	}

	/**
	 * @Title: getMailConfigMember.
	 * @Description: TODO(邮件发送-封装会员信息).
	 * @param p_data
	 * @param p_Email
	 * @return Map<String,Object>.
	 * @author CongZN.
	 */
	public static Map<String, Object> getMailConfigMember(Map<String, Object> p_data, String p_Email) {

		String serverContext = Config.getValue("ServerContext");
		String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
		QueryBuilder query_memberName = new QueryBuilder(
				"select realName,currentValidatePoint from Member where email = ?");
		query_memberName.add(p_Email);

		// 会员真实姓名.
		String realName = "";
		String integral = "";

		DataTable dt_member = query_memberName.executeDataTable();
		for (int i = 0; i < dt_member.getRowCount(); i++) {
			realName = dt_member.getString(i, "realName");
			integral = dt_member.getString(i, "currentValidatePoint");
		}

		if (StringUtil.isEmpty(integral)) {
			integral = "0.00";
		}

		BigDecimal pointValue = new BigDecimal(integral).divide(new BigDecimal(PointScalerUnit), 2,
				BigDecimal.ROUND_DOWN);

		String orderSn = (String) p_data.get("OrderSn");

		String productId = (String) p_data.get("ProductId");

		String KID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);

		if (StringUtil.isEmpty(realName)) {
			realName = "尊敬的会员您好:";
		} else {
			realName = "尊敬的&nbsp;" + realName + "&nbsp;您好:";
		}

		String OrderDetailsUrl = serverContext + "/shop/order_config_new!linkOrderDetails.action?orderSn=" + orderSn
				+ "&KID=" + KID;

		p_data.put("MemberRealName", realName);
		// 查询订单路径.
		p_data.put("QueryOrdersUrl", serverContext + "/shop/order_query!list.action");
		// 查看积分路径.
		p_data.put("QueryPointUrl", serverContext + "/shop/point!newList.action");
		// 查看评论.
		p_data.put("QueryCommentsUrl", serverContext + "/shop/member_comment!queryComment.action");
		// 查看订单明细
		p_data.put("OrderDetailsUrl", OrderDetailsUrl);
		// 续保路径.
		p_data.put("renewalSkipUrl",
				serverContext + "/shop/member!renewalSkip.action?banner_id=xubao&productId=" + productId);
		// 积分使用说明
		p_data.put("QueryPointsDesc", serverContext + "/shop/point!queryPointsDesc.action");
		// 积分商城
		p_data.put("JFUrl", Config.getFrontServerContextPath() + "/jifen/");
		// 会员中心首页
		p_data.put("MemCenterUrl", serverContext + "/shop/member_center!index.action");
		// 发件人
		p_data.put("toMail", p_Email);
		// 积分
		p_data.put("Integral", integral);
		// 低值
		p_data.put("PointValue", pointValue);

		return p_data;
	}

	/**
	 * @Title: getMailConfigProduct.
	 * @Description: TODO(邮件发送-封装产品信息).
	 * @param p_data
	 * @param p_EmailType
	 * @return Map<String,Object>.
	 * @author CongZN.
	 */
	public static Map<String, Object> getMailConfigProduct(Map<String, Object> p_data, String p_EmailType) {

		String orderSn = (String) p_data.get("OrderSn");
		String productDetailUrl = "";

		if (StringUtil.isNotEmpty(orderSn)) {
			QueryBuilder query_productDetailUrl = new QueryBuilder(
					"select Url from SDSearchRelaProduct where ProductID = (select productid from sdinformation where ordersn = ?)");
			query_productDetailUrl.add(orderSn);
			productDetailUrl = query_productDetailUrl.executeString();
		}

		if (StringUtil.isEmpty(productDetailUrl)) {
			productDetailUrl = "###";
		}

		p_data.put("productDetailUrl", productDetailUrl);

		return p_data;
	}

	/**
	 * @Title: CombinationConditions.
	 * @Description: TODO(重组订单号).
	 * @param list
	 * @return String.
	 * @author CongZN.
	 */
	private static String CombinationConditions(List<SDOrder> list) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i < list.size()) {
				sb.append("'" + list.get(i).getOrderSn() + "',");
			}
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

}