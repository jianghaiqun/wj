package com.sinosoft.datachannel;

import com.sinosoft.cms.payment.PaymentManage;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDCacheWXMessageSchema;
import com.sinosoft.weixin.WeiXinCommon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SMSSendManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.SMSSendManager";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("-1", "生日祝福定时发送");
		map.put("0", "非车险保单生效提醒");
		map.put("1", "非车险保单到期提醒");
		map.put("2", "非车险订单支付提醒");
		map.put("3", "生效保单微信消息提醒");
		map.put("4", "一年期保单到期微信消息提醒");
		map.put("5", "短期保单到期微信消息提醒");
		return map;
	}

	public void execute(long id) {
		if ("-1".equals(id + "")) {
			HappyBirthDayService happyBirthDayService = new HappyBirthDayService();
			happyBirthDayService.execute();
		}else if("0".equals(id + "")){
			ContNoticeService contNoticeService=new ContNoticeService();
			contNoticeService.execute();
		}else if("1".equals(id + "")){
			ContOverNoticeService contOverNoticeService=new ContOverNoticeService();
			contOverNoticeService.execute();
		}else if("2".equals(id + "")){
			ContPayNoticeService contPayNoticeService=new ContPayNoticeService();
			contPayNoticeService.execute();
		}else if("3".equals(id + "")){
			sendEffectWXMessage();
		}else if("4".equals(id + "")){
			sendYearOutEffectWXMessage();
		}else if("5".equals(id + "")){
			sendOutEffectWXMessage();
		}

	}

	/**
	 * 当天生效的保单微信提醒
	 */
	public void sendEffectWXMessage() {
		// 取得token
		String token = WeiXinCommon.ajaxtoken();
		// 取得生效保单微信模版ID
		String templateId = new QueryBuilder("select Memo from zdcode where CodeType='WX.MessageTemplate' and CodeValue='effecting'").executeString();
		// 理财险微信模板ID
		String lcxTemplateId = new QueryBuilder("select Memo from zdcode where CodeType='WX.lcx.MessageTemplate' and CodeValue='lcxEffecting'").executeString();
		if (StringUtil.isNotEmpty(templateId)) {
			DataTable dt = new QueryBuilder("select r.orderSn, r.riskCode, r.policyNo,w.openId,date_format(r.svaliDate, '%Y年%m月%d日') svaliDate,(select applicantName from sdinformationappnt where informationSn=r.informationSn) applicantName, (select ProductGenera from sdproduct where ProductID=r.riskCode) productGenera, r.riskName from sdinformationrisktype r, sdorders o, wxbind w where r.appStatus in ('1','3') and date_format(r.svaliDate, '%Y-%m-%d') = date_format(DATE_ADD(NOW(), INTERVAL +1 DAY), '%Y-%m-%d') and r.policyNo is not null and r.policyNo != '' and r.orderSn = o.orderSn and o.memberId is not null and o.memberId != '' and o.memberId = w.memberId and w.openId is not null and w.openId != ''").executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				Map<String, Object> param = new HashMap<String, Object>();
				PaymentManage PaymentManage = new PaymentManage();
				param.put("topcolor", "#000000");
				param.put("url", "");
				
				// 微信推送 拼装参数
				Map<String, Object> dataParam = new HashMap<String, Object>();
				String product = "";
				String riskcode;
				String financProductsIds = Config.getValue("LicaiBaoxianProducts");//理财产品ids
				for (int i = 0; i < rowCount; i++) {
					try {
						product = "";
						riskcode = dt.getString(i, "riskCode");
						if (StringUtil.isNotEmpty(dt.getString(i, "productGenera"))) {
							product = dt.getString(i, "productGenera") + "/";
						}
						product += dt.getString(i, "riskName");
						param.put("touser", dt.getString(i, "openId"));
						dataParam = new HashMap<String, Object>();
						if (financProductsIds.contains(riskcode)) {
							param.put("template_id", lcxTemplateId);
							dataParam.put("first", PaymentManage.getWXDataMap("value", "尊敬的开心保用户，您的保单即将生效！"));
							dataParam.put("keyword1", PaymentManage.getWXDataMap("value", dt.getString(i, "policyNo")));
							dataParam.put("keyword2", PaymentManage.getWXDataMap("value", product));
							dataParam.put("keyword3", PaymentManage.getWXDataMap("value", dt.getString(i, "applicantName")));
							dataParam.put("keyword4", PaymentManage.getWXDataMap("value", formatDate(dt.getString(i, "svaliDate")) +" 00:00"));
							dataParam.put("keyword5", PaymentManage.getWXDataMap("value", formatDate(dt.getString(i, "svaliDate")) +" 09:00\n"));
							dataParam.put("remark", PaymentManage.getWXDataMap("value", "【您可关注“开心保保险”官方微信(kaixinbbx)，登陆会员中心，随时随地查看理财收益。】\n感谢您的支持！如有疑问可拨打4009-789-789咨询。如需理赔，提交材料到微信，理赔更方便！"));
						} else {
							param.put("template_id", templateId);
							dataParam.put("first", PaymentManage.getWXDataMap("value", "尊敬的开心保用户，您的保单即将生效！"));
							dataParam.put("policyno", PaymentManage.getWXDataMap("value", dt.getString(i, "policyNo")));
							dataParam.put("product", PaymentManage.getWXDataMap("value", product));
							dataParam.put("contno", PaymentManage.getWXDataMap("value", dt.getString(i, "applicantName")));
							dataParam.put("appdate", PaymentManage.getWXDataMap("value", dt.getString(i, "svaliDate")+" 00:00"));
							dataParam.put("remark", PaymentManage.getWXDataMap("value", "感谢您的支持！如有疑问可拨打4009-789-789咨询。如需理赔，提交材料到微信，理赔更方便！"));
						}
						param.put("data", dataParam);
						if (!WeiXinCommon.ajaxSendInfoToUser(token, dt.getString(i, "openId"), param)) {
							logger.error("生效保单（{}）微信消息发送失败！", dt.getString(i, "policyNo"));
							cacheWXMessageAdd(dt.getString(i, "orderSn"), dt.getString(i, "policyNo"), dt.getString(i, "openId"), DateUtil.getCurrentDateTime(), "effecting");
						}
					} catch(Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}
	
	public void cacheWXMessageAdd(String orderSn, String policyNo, String openId, String sendDate, String type) {
		SDCacheWXMessageSchema schema = new SDCacheWXMessageSchema();
		schema.setid(""+NoUtil.getMaxID("CacheWXMessage"));
		schema.setmessageType(type);
		schema.setopenId(openId);
		schema.setorderSn(orderSn);
		schema.setpolicyNo(policyNo);
		schema.setsendDate(sendDate);
		schema.setsendCount("1");
		schema.setcreateDate(new Date());
		schema.setcreateUser("System");
	}
	
	/**
	 * 一年期保单过期前三天微信提醒
	 */
	public void sendYearOutEffectWXMessage() {
		// 取得token
		String token = WeiXinCommon.ajaxtoken();
		// 取得生效保单微信模版ID
		String templateId = new QueryBuilder("select Memo from zdcode where CodeType='WX.MessageTemplate' and CodeValue='outEffect'").executeString();
		if (StringUtil.isNotEmpty(templateId)) {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT r.policyNo,w.openId, r.riskName, DATE_FORMAT(r.evaliDate, '%Y年%m月%d日') evaliDate,p.HtmlPath,p.ispublish,pri.ispublish wapispublish "); 
			sb.append("FROM sdinformationrisktype r, sdinformation i, sdorders o, wxbind w ,sdproduct p LEFT JOIN productrelainfo pri ON (p.productid = pri.productid) ");
			sb.append("WHERE r.appStatus IN ('1','3') AND DATE_FORMAT(r.evaliDate, '%Y-%m-%d')=DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 3 DAY), '%Y-%m-%d') ");
			sb.append("AND r.informationSn=i.informationSn AND ((i.ensureLimitType='D' and i.ensureLimit in ('365', '366')) ");
			sb.append("OR (i.ensureLimitType='M' and i.ensureLimit = '12') OR (i.ensureLimitType='Y' and i.ensureLimit = '1')) AND i.orderSn=o.orderSn ");
			sb.append("AND o.memberId IS NOT NULL AND o.memberId != '' AND o.memberId = w.memberId AND w.openId IS NOT NULL AND w.openId != '' ");
			sb.append(" AND p.ProductID=r.riskCode  AND  p.productname NOT LIKE'%安联臻爱%'  ");
			sb.append("");
			sb.append("");
			DataTable dt = new QueryBuilder(sb.toString()).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				Map<String, Object> param = new HashMap<String, Object>();
				PaymentManage PaymentManage = new PaymentManage();
				String wjServerContext = Config.getFrontServerContextPath();
				String WapServerContext = Config.getValue("WapServerContext");
				param.put("template_id", templateId);
				param.put("topcolor", "#000000");
				
				// 微信推送 拼装参数
				Map<String, Object> dataParam = new HashMap<String, Object>();
				for (int i = 0; i < rowCount; i++) {
					try {
						param.put("touser", dt.getString(i, "openId"));
						//修改详情逻辑 start
						//在线产品默认跳到详细页
						String url=dt.getString(i, "HtmlPath").replace(wjServerContext, WapServerContext);
						//下线产品跳到产品大类中
						if("N".equals(dt.getString(i, "ispublish"))||"N".equals(dt.getString(i, "wapispublish"))||"".equals(dt.getString(i, "wapispublish"))||dt.getString(i, "wapispublish")==null){
						
							url=dt.getString(i, "HtmlPath").replace(wjServerContext, WapServerContext);
							int index = url.lastIndexOf("/");
							url = url.substring(0,index);
						}
						param.put("url", url);
						//修改详情逻辑 end
						dataParam = new HashMap<String, Object>();
						dataParam.put("first", PaymentManage.getWXDataMap("value", "亲，您的保单快要到期了哦，建议您提前续保，预防风险！"));
						dataParam.put("keyword1", PaymentManage.getWXDataMap("value", dt.getString(i, "riskName")));
						dataParam.put("keyword2", PaymentManage.getWXDataMap("value", dt.getString(i, "policyNo")));
						dataParam.put("keyword3", PaymentManage.getWXDataMap("value", dt.getString(i, "evaliDate")));
						dataParam.put("remark", PaymentManage.getWXDataMap("value", "点击详情，尊享更多会员优惠"));
						param.put("data", dataParam);
						if (!WeiXinCommon.ajaxSendInfoToUser(token, dt.getString(i, "openId"), param)) {
							logger.error("过期保单（{}）微信消息发送失败！", dt.getString(i, "policyNo"));
							cacheWXMessageAdd(dt.getString(i, "orderSn"), dt.getString(i, "policyNo"), dt.getString(i, "openId"), DateUtil.getCurrentDateTime(), "outEffect");
						}
					} catch(Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			//安联臻爱产品发送微信通知或email通知
			sendAnLianZhenAiMessage(templateId,token);
			//删除安联臻爱中间表垃圾数据
			delYesterdayZhenAiRenewal();
		}
	}
	
	
	
	/**
	 * 安联臻爱产品发送微信通知或email通知
	 * 规则： 距离到期30天一次，距离到期3天一次。 微信，邮箱仅发送一个。微信优先级高。
	 */
	public void sendAnLianZhenAiMessage(String templateId , String token ) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT r.policyNo,w.openId, r.riskName, DATE_FORMAT(r.evaliDate, '%Y年%m月%d日') evaliDate,p.HtmlPath,p.ispublish,pri.ispublish wapispublish  ,m.email  ");
		sb.append(",LEFT(r.evaliDate,10) AS fEvaliDate,LEFT(r.svaliDate,10) AS svaliDate,LEFT(r.evaliDate,10) AS evaliDate_mail,o.orderSn,COUNT(1) AS MulNum");
		sb.append(" FROM sdinformationrisktype r LEFT JOIN sdproduct p  ON p.ProductID=r.riskCode    LEFT JOIN productrelainfo pri ON (p.productid = pri.productid) , ");  
		sb.append(" sdinformation i, sdorders o LEFT JOIN wxbind w  ON o.memberId = w.memberId LEFT JOIN member m  ON o.memberId = m.id  "); 
		sb.append(" WHERE r.appStatus IN ('1','3') "); 
		sb.append(" AND( DATE_FORMAT(r.evaliDate, '%Y-%m-%d')=DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 3 DAY), '%Y-%m-%d')  "); 
		sb.append(" OR DATE_FORMAT(r.evaliDate, '%Y-%m-%d')=DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 30 DAY), '%Y-%m-%d') ) "); 
		sb.append(" AND r.informationSn=i.informationSn   AND i.orderSn=o.orderSn  "); 
		sb.append(" AND o.memberId IS NOT NULL AND o.memberId != ''   "); 
		sb.append(" AND p.remark6='2049' AND p.productname LIKE'%安联臻爱%'  GROUP BY policyNo "); 
		DataTable dt = new QueryBuilder(sb.toString()).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			if(dt.getRowCount()==1 && dt.get(0).get(3)==null) return;
			int rowCount = dt.getRowCount();
			Map<String, Object> data = new HashMap<String, Object>();//email
			int dateNumber = 0;
			String now_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			Map<String, Object> param = new HashMap<String, Object>();//weixin
			PaymentManage PaymentManage = new PaymentManage();
			String WapServerContext = Config.getValue("WapServerContext");
			param.put("template_id", templateId);
			param.put("topcolor", "#000000");
			// 微信推送 拼装参数
			Map<String, Object> dataParam = new HashMap<String, Object>();
			for (int i = 0; i < rowCount; i++) {
				if(StringUtil.isNotEmpty(dt.getString(i, "openId"))){//绑定微信号，发送微信提醒
					try {
						param.put("touser", dt.getString(i, "openId"));
						// 跳转会员中心
						param.put("url", WapServerContext+"/member/index.shtml");
						//修改详情逻辑 end
						dataParam = new HashMap<String, Object>();
						dataParam.put("first", PaymentManage.getWXDataMap("value", "亲，您的保单快要到期了哦，建议您提前续保，预防风险！"));
						dataParam.put("keyword1", PaymentManage.getWXDataMap("value", dt.getString(i, "riskName")));
						dataParam.put("keyword2", PaymentManage.getWXDataMap("value", dt.getString(i, "policyNo")));
						dataParam.put("keyword3", PaymentManage.getWXDataMap("value", dt.getString(i, "evaliDate")));
						dataParam.put("remark", PaymentManage.getWXDataMap("value", "点击详情，尊享更多会员优惠"));
						param.put("data", dataParam);
						if (!WeiXinCommon.ajaxSendInfoToUser(token, dt.getString(i, "openId"), param)) {
							logger.error("过期保单（{}）微信消息发送失败！", dt.getString(i, "policyNo"));
							cacheWXMessageAdd(dt.getString(i, "orderSn"), dt.getString(i, "policyNo"), dt.getString(i, "openId"), DateUtil.getCurrentDateTime(), "outEffect");
						}
					} catch(Exception e) {
						logger.error(e.getMessage(), e);
					}
				}else{//未绑定微信号，发送邮件提醒
					try {
						dateNumber = Integer.parseInt(DateUtil.subDateByDay(dt.getString(i, "fEvaliDate"), now_date));
						data.put("svaliDate", dt.getString(i, "svaliDate"));
						data.put("evaliDate", dt.getString(i, "evaliDate_mail"));
						data.put("dateNumber", dateNumber);
						data.put("orderSn", dt.getString(i, "OrderSn"));
						data.put("mulNum", dt.getString(i, "MulNum"));
						data.put("renewalFlag", "Y");
						ActionUtil.sendMessage("wj00098", data);
					} catch(Exception e) {
						logger.error(e.getMessage(), e);
					}
					
				}
				
			}
		}
		
	}
	
	
	private void delYesterdayZhenAiRenewal() {
		String sql = " DELETE FROM ZhenAiRenewal WHERE DATEDIFF(createdate,NOW())=-7 ";
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder(sql));
		if (!trans.commit()) {
			logger.error("删除臻爱产品续费中间表过期数据失败！");
		}
	}

	/**
	 * 短期保单过期前微信提醒
	 */
	public void sendOutEffectWXMessage() {
		// 取得token
		String token = WeiXinCommon.ajaxtoken();
		// 取得生效保单微信模版ID
		String templateId = new QueryBuilder("select Memo from zdcode where CodeType='WX.MessageTemplate' and CodeValue='outEffect'").executeString();
		if (StringUtil.isNotEmpty(templateId)) {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT r.policyNo,w.openId, r.riskName, DATE_FORMAT(r.evaliDate, '%Y年%m月%d日') evaliDate,p.HtmlPath,p.ispublish,pri.ispublish wapispublish "); 
			sb.append("FROM sdinformationrisktype r, sdinformation i, sdorders o, wxbind w ,sdproduct p LEFT JOIN productrelainfo pri ON (p.productid = pri.productid) ");
			sb.append("WHERE r.appStatus IN ('1','3') AND DATE_FORMAT(r.evaliDate, '%Y-%m-%d')=DATE_FORMAT(NOW(), '%Y-%m-%d') ");
			sb.append("AND r.informationSn=i.informationSn AND ((i.ensureLimitType='D' and i.ensureLimit < 365) ");
			sb.append("OR (i.ensureLimitType='M' and i.ensureLimit < 12) OR (i.ensureLimitType='Y' and i.ensureLimit < 1)) AND i.orderSn=o.orderSn ");
			sb.append("AND o.memberId IS NOT NULL AND o.memberId != '' AND o.memberId = w.memberId AND w.openId IS NOT NULL AND w.openId != '' and p.ProductID=r.riskCode ");
			
			sb.append("");
			DataTable dt = new QueryBuilder(sb.toString()).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				Map<String, Object> param = new HashMap<String, Object>();
				PaymentManage PaymentManage = new PaymentManage();
				String wjServerContext = Config.getFrontServerContextPath();
				String WapServerContext = Config.getValue("WapServerContext");
				param.put("template_id", templateId);
				param.put("topcolor", "#000000");
				
				// 微信推送 拼装参数
				Map<String, Object> dataParam = new HashMap<String, Object>();
				for (int i = 0; i < rowCount; i++) {
					try {
						param.put("touser", dt.getString(i, "openId"));
						//修改详情逻辑 start
						//在线产品默认跳到详细页
						String url=dt.getString(i, "HtmlPath").replace(wjServerContext, WapServerContext);
						//下线产品跳到产品大类中
						if("N".equals(dt.getString(i, "ispublish"))||"N".equals(dt.getString(i, "wapispublish"))||"".equals(dt.getString(i, "wapispublish"))||dt.getString(i, "wapispublish")==null){
						
							url=dt.getString(i, "HtmlPath").replace(wjServerContext, WapServerContext);
							int index = url.lastIndexOf("/");
							url = url.substring(0,index);
						}
						param.put("url", url);
						//修改详情逻辑 end
						dataParam = new HashMap<String, Object>();
						dataParam.put("first", PaymentManage.getWXDataMap("value", "亲，您的保单快要到期了哦，建议您提前续保，预防风险！"));
						dataParam.put("keyword1", PaymentManage.getWXDataMap("value", dt.getString(i, "riskName")));
						dataParam.put("keyword2", PaymentManage.getWXDataMap("value", dt.getString(i, "policyNo")));
						dataParam.put("keyword3", PaymentManage.getWXDataMap("value", dt.getString(i, "evaliDate")));
						dataParam.put("remark", PaymentManage.getWXDataMap("value", "点击详情，尊享更多会员优惠"));
						param.put("data", dataParam);
						if (!WeiXinCommon.ajaxSendInfoToUser(token, dt.getString(i, "openId"), param)) {
							logger.error("过期保单（{}）微信消息发送失败！", dt.getString(i, "policyNo"));
							cacheWXMessageAdd(dt.getString(i, "orderSn"), dt.getString(i, "policyNo"), dt.getString(i, "openId"), DateUtil.getCurrentDateTime(), "outEffect");
						}
					} catch(Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	private String formatDate (String strdate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date date = format.parse(strdate);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
			return format1.format(date);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "定时发送任务";
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
		return "com.sinosoft.datachannel.SMSSendManager";
	}

}
