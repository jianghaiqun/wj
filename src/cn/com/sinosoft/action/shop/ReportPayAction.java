package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.common.JCaptchaEngine;
import cn.com.sinosoft.service.MailService;
import com.octo.captcha.service.CaptchaService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.schema.PaymentInfoSchema;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class ReportPayAction extends BaseShopAction {

	private static final long serialVersionUID = -7227426726084770262L;

	@Resource
	private MailService mailService;
	
	@Resource
	private CaptchaService captchaService;

	/** 被保险人姓名 */
	private String name;

	/** 被保险人证件号 */
	private String identityId;

	/** 订单号 */
	private String orderSn;

	/** 联系人姓名 */
	private String contactName;

	/** 联系人邮箱 */
	private String ConEmail;

	/** 联系人电话 */
	private String ConTel;

	/** 申请保障类别 */
	private String ensureType;

	/** 事故发生时间 */
	private String ConTime;

	/** 事故发生地点 */
	private String ConDi;

	/** 事件描述 */
	private String descript;

	private String flag;

	/**
	 * 保险公司报案电话信息
	 */
	private List<Map<String, String>> companyInfo;

	public String reportInit() {

		DataTable dt = new QueryBuilder(
				"select CodeValue, CodeName, Memo from zdcode where CodeType='ReportPhone' and ParentCode='ReportPhone' order by CodeOrder asc ")
				.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			companyInfo = new ArrayList<Map<String, String>>();
			Map<String, String> map;
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				map = new HashMap<String, String>();
				map.put("comCode", dt.getString(i, 0));
				map.put("comPhone", dt.getString(i, 1));
				map.put("comName", dt.getString(i, 2));
				companyInfo.add(map);
			}
		}

		return "input";
	}

	public String submit() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(STATUS, SUCCESS);
		try {

			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest().getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
				map.put(STATUS, "error");
				map.put("message", "验证码错误，请重新提交！");
				return ajaxJson(map);
			}

			// 记录理赔报案信息
			PaymentInfoSchema schema = new PaymentInfoSchema();
			schema.setid(PubFun.GetPaymentNO());
			schema.setcreateDate(new Date());
			schema.setmodifyDate(new Date());
			// 被保险人姓名
			schema.setinsureName(name);
			// 被保险人证件号
			schema.setinsureIdentityId(identityId);
			// 订单号
			schema.setorderSn(orderSn);
			// 联系人姓名
			schema.setcontactName(contactName);
			// 联系人邮箱
			schema.setcontactMail(ConEmail);
			// 联系人电话
			schema.setcontactMobile(ConTel);
			// 申请保障类别
			schema.setensureType(ensureType);
			// 事故发生时间
			schema.sethappenTime(ConTime);
			// 事故发生地点
			schema.sethappenArea(ConDi);
			// 事件描述
			schema.sethappenDesc(descript);
			// 处理状态
			schema.setstate("0");
			schema.insert();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			map.put(STATUS, "error");
			map.put("message", "系统出现错误，请重新提交！");
			return ajaxJson(map);
		}

		// 取得邮件地址
		String sql = "select CodeName from zdcode where CodeType=? and ParentCode=?";
		QueryBuilder qb = new QueryBuilder(sql, "Payment.MailAddress",
				"Payment.MailAddress");
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			String[] address = new String[dt.getRowCount()];
			for (int i = 0; i < dt.getRowCount(); i++) {
				address[i] = dt.getString(i, 0);
			}

			String text = "<br>被保险人姓名：" + name + "<br>被保险人证件号：" + identityId
					+ "<br>订单号：" + orderSn + "<br>联系人姓名：" + contactName
					+ "<br>联系人邮箱：" + ConEmail + "<br>联系人电话：" + ConTel
					+ "<br>申请保障类别：" + ensureType + "<br>事故发生时间：" + ConTime
					+ "<br>事故发生地点：" + ConDi + "<br>事件描述：" + descript
					+ "<br><br>报案时间：" + DateUtil.getCurrentDateTime();
			// 发送报案提醒邮件
			mailService.sendBatchMail(address, text, "理赔报案提醒");
		}
		return ajaxJson(map);
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getIdentityId() {

		return identityId;
	}

	public void setIdentityId(String identityId) {

		this.identityId = identityId;
	}

	public String getOrderSn() {

		return orderSn;
	}

	public void setOrderSn(String orderSn) {

		this.orderSn = orderSn;
	}

	public String getContactName() {

		return contactName;
	}

	public void setContactName(String contactName) {

		this.contactName = contactName;
	}

	public String getConEmail() {

		return ConEmail;
	}

	public void setConEmail(String conEmail) {

		ConEmail = conEmail;
	}

	public String getConTel() {

		return ConTel;
	}

	public void setConTel(String conTel) {

		ConTel = conTel;
	}

	public String getEnsureType() {

		return ensureType;
	}

	public void setEnsureType(String ensureType) {

		this.ensureType = ensureType;
	}

	public String getConTime() {

		return ConTime;
	}

	public void setConTime(String conTime) {

		ConTime = conTime;
	}

	public String getConDi() {

		return ConDi;
	}

	public void setConDi(String conDi) {

		ConDi = conDi;
	}

	public String getDescript() {

		return descript;
	}

	public void setDescript(String descript) {

		this.descript = descript;
	}

	public List<Map<String, String>> getCompanyInfo() {

		return companyInfo;
	}

	public void setCompanyInfo(List<Map<String, String>> companyInfo) {

		this.companyInfo = companyInfo;
	}

	public String getFlag() {

		return flag;
	}

	public void setFlag(String flag) {

		this.flag = flag;
	}

}
