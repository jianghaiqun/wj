package com.sinosoft.cms.mail;

import cn.com.sinosoft.bean.MailConfig;
import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.sinosoft.cms.payment.PaymentManage;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.MailAction;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDCacheWXMessageSchema;
import com.sinosoft.weixin.WeiXinCommon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TimingSendMail .
 * @Description: TODO(扫描未发送成功邮件并发送).
 * @author CongZN.
 * @date 2014-8-27 上午10:01:52.
 * 
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
public class TimingSendMail extends ConfigEanbleTaskManager {

	public static final String CODE = "com.sinosoft.cms.mail.TimingSendMail";

	public boolean isRunning(long id) {

		return false;
	}

	public Mapx getConfigEnableTasks() {

		Mapx<String, String> map = new Mapx<String, String>();
		map.put("1", "邮件定时发送");
		map.put("2", "电子保单过期提醒");
		map.put("3", "境外险保单生效提醒+事项提醒");
		map.put("4", "达人稿费支付提醒邮件");
		return map;
	}

	public void cacheWXMessageAdd(String orderSn, String openId, String sendDate, Transaction tran) {

		SDCacheWXMessageSchema schema = new SDCacheWXMessageSchema();
		schema.setid("" + NoUtil.getMaxID("CacheWXMessage"));
		schema.setmessageType("noPay");
		schema.setopenId(openId);
		schema.setorderSn(orderSn);
		schema.setsendDate(sendDate);
		schema.setsendCount("1");
		schema.setcreateDate(new Date());
		schema.setcreateUser("System");
		tran.add(schema, Transaction.INSERT);
	}

	public void execute(long id) {

		String subject = null;
		String toMail = null;
		String mailText = null;
		String filePath = null;
		String paySn = null;
		String mailType = null;
		String mailID = null;
		String sendCount = null;

		try {
			if ("1".equals(id + "")) {
				QueryBuilder query_cacheErroerMail = new QueryBuilder(
						"select * from SDCacheErroerMail where SendDate < ? and MailType not in('a0020','wxActivity0001') and prop2 < 5 order by Priority+0 ",
						DateUtil.getCurrentDateTime());

				DataTable dt = query_cacheErroerMail.executeDataTable();

				// if(dt.getRowCount() > 2000){
				// LogUtil.error("严重警告：邮件定时扫描任务,检测到错误邮件数量 ："+dt.getRowCount()+"封 发送异常!");
				// LogUtil.error("程序出现异常,定时任务扫描时间 单位(分) 系统将停止该定时任务!待处理后启用!");
				// String update_TimingSql =
				// "update ZDSchedule set isUsing = 'N' where TypeCode = ?";
				// QueryBuilder update_Timing = new
				// QueryBuilder(update_TimingSql);
				// update_Timing.add(CODE);
				// update_Timing.executeNoQuery();
				// LogUtil.error("定时任务已停止!");
				// }

				String PaySns = "";
				Map<String,Object> mailParam = null;
				for (int i = 0; i < dt.getRowCount(); i++) {
					// MailSenderInfo mailInfo = new MailSenderInfo();

					mailID = dt.getString(i, "ID");
					sendCount = dt.getString(i, "prop2");
					subject = dt.getString(i, "Subject");
					toMail = dt.getString(i, "Addressee");
					mailText = dt.getString(i, "Content");
					// filePath = dt.getString(i, "FilePath");
					paySn = dt.getString(i, "PaySn");
					mailType = dt.getString(i, "MailType");
					// 第一次发送待支付邮件时发送待支付微信提醒
					if ("a0010".equals(mailType)
							&& StringUtil.isNotEmpty(paySn)
							&& (StringUtil.isEmpty(sendCount) || Integer
									.valueOf(sendCount) <= 1)) {
						PaySns += ("," + paySn);
					}
					try {
						if (StringUtil.isEmpty(mailText)) {
							// 解决生产环境paySn第一次反查数据为空的问题
							if ("a0010".equals(mailType)) {
								mailText = a0010Retry(paySn, toMail, mailType);
							} else {
								updateSendMailCount(mailID, sendCount);
								continue;
							}
						}

						/*mailInfo.setSubject(subject);
						mailInfo.setToAddress(toMail);
						mailInfo.setContent(mailText);*/

						mailParam = new HashMap<String,Object>();
						mailParam.put("mailId", mailID);
						ActionUtil.sendMessage("wj01022", mailParam);

					} catch (Exception e) {
						updateSendMailCount(mailID, sendCount);
						logger.error("type:" + id + "邮件发送-定时计划执行异常 message:"
								+ e.getMessage(), e);
					}
				}

				// 待支付发送微信消息
				// 取得token
				String token = WeiXinCommon.ajaxtoken();
				// 取得待支付微信模板ID
				String templateId = new QueryBuilder(
						"select Memo from zdcode where CodeType='WX.MessageTemplate' and CodeValue='noPay'").executeString();
				if (StringUtil.isNotEmpty(templateId)) {
					Transaction tran = new Transaction();
					boolean tranFlag = false;
					Map<String, Object> param = new HashMap<String, Object>();
					PaymentManage PaymentManage = new PaymentManage();
					param.put("template_id", templateId);
					param.put("topcolor", "#000000");
					param.put("url", "");
					String orderSns = "";
					String idsSucc = "";
					String idsFail = "";
					// 暂时注释掉 错误信息重发
					/*dt = new QueryBuilder(
							"select w.id, w.orderSn, w.openId, date_format(o.createDate,'%Y-%m-%d %H:%m:%s') createDate, o.totalAmount,(select productName from sdinformation where orderSn=o.orderSn ) productName from SDCacheWXMessage w, sdorders o where w.sendDate < ? and w.sendCount < 5 and w.messageType = 'noPay' and w.orderSn=o.orderSn and o.orderStatus='5' ",
							DateUtil.getCurrentDateTime()).executeDataTable();
					if (dt != null && dt.getRowCount() > 0) {
						int rowCount = dt.getRowCount();
						// 微信推送 拼装参数
						Map<String, Object> dataParam = new HashMap<String, Object>();
						for (int i = 0; i < rowCount; i++) {
							try {
								param.put("touser", dt.getString(i, "openId"));
								dataParam = new HashMap<String, Object>();
								dataParam.put("first", PaymentManage.getWXDataMap("value", "尊敬的开心保用户，您有一笔保单待支付："));
								dataParam.put("keyword1",
										PaymentManage.getWXDataMap("value", dt.getString(i, "totalAmount") + "元"));
								dataParam.put("keyword2",
										PaymentManage.getWXDataMap("value", dt.getString(i, "productName")));
								dataParam
										.put("keyword3", PaymentManage.getWXDataMap("value", dt.getString(i, "createDate")));
								dataParam.put("keyword4", PaymentManage.getWXDataMap("value", dt.getString(i, "orderSn")));
								dataParam.put("remark", PaymentManage.getWXDataMap("value", "请及时支付订单，过期失效"));
								param.put("data", dataParam);
								orderSns += ("," + dt.getString(i, "orderSn"));
								if (!WeiXinCommon.ajaxSendInfoToUser(token, dt.getString(i, "openId"), param)) {
									idsFail += ("," + dt.getString(i, "id"));
									LogUtil.error("type:" + id + " 待支付订单（" + dt.getString(i, "orderSn") + "）微信消息发送失败！");
								} else {
									idsSucc += ("," + dt.getString(i, "id"));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (StringUtil.isNotEmpty(orderSns)) {
							orderSns = orderSns.substring(1).replace(",", "','");
						}

						if (StringUtil.isNotEmpty(idsFail)) {
							idsFail = idsFail.substring(1).replace(",", "','");
							tran.add(new QueryBuilder("update SDCacheWXMessage set sendCount = sendCount + 1 where id in ('"
									+ idsFail + "') "));
							tranFlag = true;
						}
						if (StringUtil.isNotEmpty(idsSucc)) {
							idsFail = idsFail.substring(1).replace(",", "','");
							tran.add(new QueryBuilder("delete from SDCacheWXMessage where id in ('" + idsFail + "') "));
							tranFlag = true;
						}
					}*/

					if (StringUtil.isNotEmpty(PaySns)) {
						PaySns = PaySns.substring(1).replace(",", "','");
						String sql = "select w.openId, o.orderSn, date_format(o.createDate,'%Y-%m-%d %H:%m:%s') createDate, o.totalAmount,(select productName from sdinformation where orderSn=o.orderSn ) productName from sdorders o, wxbind w where o.paySn in ('"
								+ PaySns
								+ "') and o.memberId is not null and o.memberId != '' and o.memberId=w.memberId and w.openId is not null and w.openId != '' and o.orderStatus='5' ";
						if (StringUtil.isNotEmpty(orderSns)) {
							sql += " and o.orderSn not in ('" + orderSns + "') ";
						}
						dt = new QueryBuilder(sql).executeDataTable();

						if (dt != null && dt.getRowCount() > 0) {
							int rowCount = dt.getRowCount();

							// 微信推送 拼装参数
							Map<String, Object> dataParam = new HashMap<String, Object>();
							for (int i = 0; i < rowCount; i++) {
								try {
									param.put("touser", dt.getString(i, "openId"));
									dataParam = new HashMap<String, Object>();
									dataParam.put("first", PaymentManage.getWXDataMap("value", "尊敬的开心保用户，您有一笔保单待支付："));
									dataParam.put("keyword1",
											PaymentManage.getWXDataMap("value", dt.getString(i, "totalAmount") + "元"));
									dataParam.put("keyword2",
											PaymentManage.getWXDataMap("value", dt.getString(i, "productName")));
									dataParam.put("keyword3",
											PaymentManage.getWXDataMap("value", dt.getString(i, "createDate")));
									dataParam.put("keyword4",
											PaymentManage.getWXDataMap("value", dt.getString(i, "orderSn")));
									dataParam.put("remark", PaymentManage.getWXDataMap("value", "请及时支付订单，过期失效"));
									param.put("data", dataParam);
									if (!WeiXinCommon.ajaxSendInfoToUser(token, dt.getString(i, "openId"), param)) {
										logger.error("type:{} 待支付订单（{}）微信消息发送失败！", id, dt.getString(i, "orderSn"));
										cacheWXMessageAdd(dt.getString(i, "orderSn"), dt.getString(i, "openId"),
												DateUtil.getCurrentDateTime(), tran);
										tranFlag = true;
									}
								} catch (Exception e) {
									logger.error(e.getMessage(), e);
								}
							}
						}

					}
					if (tranFlag) {
						tran.commit();
					}
				}

				// 发送微信活动倒计时推送消息.
				try {
					schedualSendWeiXinActivityTemplateMsg(token);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			} else if ("2".equals(id + "")) {
				Map<String, Object> data = new HashMap<String, Object>();
				StringBuffer sb = new StringBuffer();
				QueryBuilder query_ordersn = new QueryBuilder();
				// 短期意外险及旅游险 才会发过期提醒邮件
				sb.append(" select r.OrderSn,left(r.evaliDate,10) as fEvaliDate,left(r.svaliDate,10) as svaliDate,left(r.evaliDate,10) as evaliDate,count(1) as MulNum from sdinformationrisktype r, sdinformation i,sdorders o ");
				sb.append(" where r.svaliDate <= now() and r.ordersn=i.ordersn and i.risktype in ('01','02')");
				sb.append(" and o.ordersn=i.ordersn  and r.appStatus = '1' and i.ensureLimitType != 'A' and (i.ensureLimitType != 'Y' or (i.ensureLimitType = 'Y' and i.ensureLimit = '1'))");
				sb.append(" and (left(r.evaliDate,10) = DATE_ADD(left( now(),10),INTERVAL 30 DAY)  ");
				sb.append(" or left(r.evaliDate,10) = DATE_ADD(left(now(),10),INTERVAL 7 DAY) or left(r.evaliDate,10) = DATE_ADD(left(now(),10),INTERVAL 1 DAY))  and o.channelsn in ('cps_01','cps_02','cps_04','cps_08','cps_6666','cps_8888','cps_cps_bbbao','cps_dlr','cps_dlr_wap','cps_dm001','cps_dm002','cps_drlm_wap','cps_linkstars','cps_linkstars_wap','cps_ps_pc','cps_ps_wap','cps_swpt','cps_swpt_wap','cps_wx','kxb_app','qunar','sp_wap_jk','wap_cps','wap_cps_agent','wap_ht','wap_wx','wj')  group by r.OrderSn ");
				query_ordersn.setSQL(sb.toString());
				DataTable dt = query_ordersn.executeDataTable(); 
				for (int i = 0; i < dt.getRowCount(); i++) {
					String fEvaliDate = dt.getString(i, "fEvaliDate");
					int days = Integer.parseInt(DateUtil.subDateByDay(dt.getString(i, "evaliDate"),
							dt.getString(i, "svaliDate"))) + 1;
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String now_date = format.format(new Date());
					int dateNumber = Integer.parseInt(DateUtil.subDateByDay(fEvaliDate, now_date));
					// 保险期限大于等于183天的保单，过期前30天、7天发送提醒邮件;保险期限在3-7天的保单，过期前1天发送提醒邮件
					if ((days >= 183 && dateNumber > 1) || (days >= 3 && days <= 7 && dateNumber == 1)) {
						data.put("svaliDate", dt.getString(i, "svaliDate"));
						data.put("evaliDate", dt.getString(i, "evaliDate"));
						data.put("dateNumber", dateNumber);
						data.put("orderSn", dt.getString(i, "OrderSn"));
						data.put("mulNum", dt.getString(i, "MulNum"));
						ActionUtil.sendMessage("wj00098", data);
					}
				}

			} else if ("3".equals(id + "")) {
				// 境外险生效前一天发送给会员提醒邮件或短信

				StringBuffer sb = new StringBuffer();
				sb.append("select o.orderSn, max(o.memberId), left(max(r.svaliDate),10) as svaliDate, count(1) as MulNum, ");
				sb.append("left(max(r.evaliDate),10) as evaliDate, z.CodeName as reportPhone, i.productName, a.applicantName, ");
				sb.append("z.Memo as companyName, max(i.productName), m.registerType, m.email, m.mobileNO, sum(r.payPrice) as payPrice ");
				sb.append("from sdorders o, member m, sdinformationrisktype r, sdinformationappnt a, sdinformation i ");
				sb.append("left join zdcode z on z.codeType='ReportPhone' and z.codevalue= i.insuranceCompany ");
				sb.append("where i.subrisktype in ('06', '07') and o.memberId is not null and o.memberId != '' ");
				sb.append("and o.ordersn = r.ordersn and r.appStatus = '1' and i.informationSn=a.informationSn ");
				sb.append("and left(r.svaliDate,10) = DATE_ADD(left(now(),10),INTERVAL 1 DAY) ");
				sb.append("and o.ordersn=i.ordersn and o.memberId = m.id and o.orderSn not like 'TB%' group by o.orderSn ");
				DataTable dt = new QueryBuilder(sb.toString())
						.executeDataTable();
				if (dt != null && dt.getRowCount() > 0) {
					int count = dt.getRowCount();

					Map<String, Object> map = new HashMap<String, Object>();
					for (int i = 0; i < count; i++) {

						// 发邮件
						if (!"1".equals(dt.getString(i, "registerType"))
								&& StringUtil.isNotEmpty(dt.getString(i,
										"email"))) {
							map = new HashMap<String, Object>();
							map.put("ApplicantName", dt.getString(i, "applicantName"));
							map.put("ProductName", dt.getString(i, "productName"));
							map.put("SvaliDate", dt.getString(i, "svaliDate"));
							map.put("EvaliDate", dt.getString(i, "evaliDate"));
							map.put("Company", dt.getString(i, "companyName"));
							map.put("ReportPhone", dt.getString(i, "reportPhone"));
							map.put("MulNum", dt.getString(i, "MulNum"));
							map.put("OrderSn", dt.getString(i, "orderSn"));
							map.put("payPrice", dt.getString(i, "payPrice"));
							map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
							ActionUtil.sendMail("wj00129", dt.getString(i, "email"), map);
						} else if (StringUtil.isNotEmpty(dt.getString(i,
								"mobileNO"))) {
							// 发短信
							ActionUtil.sendSms("wj00124", dt.getString(i, "mobileNO")
									, dt.getString(i, "productName") + ";" + dt.getString(i, "svaliDate") 
									+ ";" + dt.getString(i, "evaliDate"));
						}
					}
				}
			} else if ("4".equals(id + "")) {
				// 达人支付稿费前一天发送支付提醒邮件
				try {
					drPaymemnt();
				} catch (Exception e) {
					logger.error("type:" + id + "邮件发送-定时计划执行达人稿费支付提醒邮件任务异常 message:"
							+ e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			updateSendMailCount(mailID, sendCount);
			logger.error("type:" + id + "邮件发送-定时计划执行异常 message:"
					+ e.getMessage(), e);
		}
	}

	private void drPaymemnt() throws Exception {

		String sql = "select u.Email,a.authorName,CONCAT(a.contactTool,':',a.contactType) contactType,"
				+ " a.articleLink,a.payType,p.payPrice from AuthorDetailInfo a, PaymemntDetailInfo p, zduser u "
				+ " where p.isPay != 'Y' and p.payTime = ? and a.id=p.authorDetailInfo_id "
				+ " and a.createUser=u.userName and u.Email is not null and u.Email != '' order by a.createUser asc";
		String time = DateUtil.toString(DateUtil.addDay(new Date(), 1));
		DataTable dt = new QueryBuilder(sql, time).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			String email = dt.getString(0, 0);
			int i = 0;
			int rowCount = dt.getRowCount();
			String subject = time + " 稿费支付提醒";
			String data = "您好，明日需支付稿费信息：<br><table border='1'><tr><td>作者</td><td>联系方式</td><td>帖子链接</td><td>支付方式</td><td>支付金额</td></tr>";
			for (; i < rowCount; i++) {
				if (!dt.getString(i, 0).equals(email)) {
					data += "</table>";
					ActionUtil.sendGeneralMail(email, subject, data);
					email = dt.getString(i, 0);
					data = "您好，明日需支付稿费信息：<br><table><tr><td>作者</td><td>联系方式</td><td>帖子链接</td><td>支付方式</td><td>支付金额</td></tr>";
				}
				data += ("<tr><td>" + dt.getString(i, "authorName") + "</td><td>" + dt.getString(i, "contactType")
						+ "</td><td>" + dt.getString(i, "articleLink") + "</td><td>" + dt.getString(i, "payType")
						+ "</td><td>" + dt.getString(i, "payPrice") + "元</td></tr>");
			}
			data += "</table>";
			ActionUtil.sendGeneralMail(email, subject, data);
		}
	}

	/**
	 * @Title: updateSendMailCount.
	 * @Description: TODO(通过ID更新该邮件发送数量).
	 * @param p_MailID
	 * @param p_Count
	 *            void.
	 * @author CongZN.
	 */
	public static void updateSendMailCount(String p_MailID, String p_Count) {

		if (StringUtil.isEmpty(p_Count)) {
			p_Count = "1";
		}

		Integer sendCount = Integer.parseInt(p_Count) + 1;
		String update_TimingSql = "update SDCacheErroerMail set prop2 = ? where ID = ?";
		QueryBuilder update_SendMailCount = new QueryBuilder(update_TimingSql);
		update_SendMailCount.add(sendCount);
		update_SendMailCount.add(p_MailID);
		update_SendMailCount.executeNoQuery();
	}

	/**
	 * @Title: compareDate.
	 * @Description: TODO(通过保险止期计算距离天数).
	 * @param p_EvaliDate
	 * @return String.
	 * @author CongZN.
	 */
	public static String compareDate(String p_EvaliDate) {

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 30);
		date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fDate = format.format(date);
		if (fDate.equals(p_EvaliDate)) {
			return "30";
		} else {
			Calendar calendar1 = new GregorianCalendar();
			Date date1 = new Date();
			calendar1.setTime(date1);
			calendar1.add(calendar1.DATE, 7);
			date1 = calendar1.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String fDate1 = format1.format(date1);
			if (fDate1.equals(p_EvaliDate)) {
				return "7";
			} else {
				return "1";
			}

		}
	}

	public String getCode() {

		return CODE;
	}

	public String getName() {

		return "邮件发送";
	}

	@Override
	public void execute(String paramString) {

		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {

		return false;
	}

	@Override
	public String getID() {

		return "com.sinosoft.cms.mail.TimingSendMail";
	}

	@SuppressWarnings("static-access")
	public static void main(String[] arg) {

		// new TimingSendMail().execute(1);
	}

	/**
	 * 针对待支付邮件特殊处理 解决生产环境paySn第一次反查数据为空的问题
	 * 
	 * @param paySn
	 *            支付流水号
	 * @param toMail
	 *            邮箱地址
	 * 
	 * @return 邮件正文
	 * @throws Exception
	 */
	public String a0010Retry(String paySn, String toMail, String mailType)
			throws Exception {

		Map<String, Object> p_Data = new HashMap<String, Object>();
		QueryBuilder qb = new QueryBuilder(
				"select channelsn from sdorders where paySn = ?");
		qb.add(paySn);
		String channelsn = qb.executeString();
		p_Data.put("ChannelSn", channelsn);
		p_Data = ActionUtil.getMailConfig(p_Data, mailType, toMail);
		p_Data = MailAction.shoppingCartData(paySn, p_Data);
		MailConfig mailConfig = TemplateConfigUtil.getMailConfig(mailType);
		String templateFilePath = mailConfig.getTemplateFilePath();
		String mailText = MailAction.parseTemplate(templateFilePath, p_Data);
		return mailText;
	}

	public void schedualSendWeiXinActivityTemplateMsg(String token) {

		List<String> failIdList = new ArrayList<String>();
		List<String> successIdList = new ArrayList<String>();
		Transaction tran = new Transaction();
		String id = null;
		String messageType = null;
		String templateId = null;
		String openid = null;
		String url = null;
		String first = null;
		String remark = null;
		String keyword1 = null;
		String keyword2 = null;
		String keyword3 = null;
		PaymentManage PaymentManage = new PaymentManage();
		// SDCacheWXMessageSchema schema = new SDCacheWXMessageSchema();
		QueryBuilder qb = new QueryBuilder(
				"select * from SDCacheWXMessage where sendDate < ? and messageType like 'activityEnd' and sendCount < 5 order by createDate",
				DateUtil.getCurrentDateTime());
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			try {
				id = dt.getString(i, "id");
				messageType = dt.getString(i, "messageType");
				templateId = dt.getString(i, "templateId");
				openid = dt.getString(i, "openId");
				first = dt.getString(i, "first");
				keyword1 = dt.getString(i, "keyword1");
				keyword2 = dt.getString(i, "keyword2");
				keyword3 = dt.getString(i, "keyword3");
				remark = dt.getString(i, "remark");
				url = dt.getString(i, "url");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("touser", openid);
				param.put("template_id", templateId);
				param.put("url", url);
				param.put("topcolor", "#000000");
				Map<String, Object> dataParam = new HashMap<String, Object>();
				dataParam = new HashMap<String, Object>();
				dataParam.put("first",
						PaymentManage.getWXDataMap("value", first));
				dataParam.put("keyword1",
						PaymentManage.getWXDataMap("value", keyword1));
				dataParam.put("keyword2",
						PaymentManage.getWXDataMap("value", keyword2));
				dataParam.put("keyword3",
						PaymentManage.getWXDataMap("value", keyword3));
				dataParam.put("remark",
						PaymentManage.getWXDataMap("value", remark));
				param.put("data", dataParam);
				if (!WeiXinCommon.ajaxSendInfoToUser(token, openid, param)) {
					logger.error("id:{}type:{}微信活动通知发送失败!", id, messageType);
					failIdList.add(id);
				} else {
					successIdList.add(id);
				}
			} catch (Exception e) {
				logger.error("id:" + id + "type:" + messageType + "微信活动通知发送失败!" + e.getMessage(), e);
				failIdList.add(id);
			}
		}
		int sizeSuc = successIdList.size();
		if (sizeSuc > 0) {
			StringBuilder sb = new StringBuilder("delete from SDCacheWXMessage where id in(");
			for (int i = 0; i < sizeSuc; i++) {
				sb.append("'" + successIdList.get(i) + "'");
				if (i != sizeSuc - 1) {
					sb.append(",");
				}
			}
			sb.append(");");
			logger.info("删除发送成功的记录.");
			tran.add(new QueryBuilder(sb.toString()));
		}
		int sizeFail = failIdList.size();
		if (sizeFail > 0) {
			StringBuilder sb = new StringBuilder("update SDCacheWXMessage set sendCount = sendCount + 1 where id in(");
			for (int i = 0; i < sizeFail; i++) {
				sb.append("'" + failIdList.get(i) + "'");
				if (i != sizeFail - 1) {
					sb.append(",");
				}
			}
			sb.append(");");
			logger.error("发送失败,增加发送失败记录的发送次数.{}", sb.toString());
			tran.add(new QueryBuilder(sb.toString()));
		}
		tran.commit();
	}
}
