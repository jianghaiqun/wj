/**
 * 
 */
package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.service.ExchangeGoodsService;
import cn.com.sinosoft.service.GiftClassifyService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.PointExchangeInfoService;
import cn.com.sinosoft.util.AesUtilQN;
import cn.com.sinosoft.util.ExchangeGoodsUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.schema.GiftClassifySchema;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcaiyun
 *
 */
@Service
public class ExchangeGoodsServiceImpl implements ExchangeGoodsService{
	private static final Logger logger = LoggerFactory.getLogger(ExchangeGoodsServiceImpl.class);
	public static final String STATUS = "status";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";	
	private String notifyurl = Config.getServerContext()+"/exchange/exchange_goods!notifyFulu.action";
	
	@Resource
	protected MemberService memberService;
	@Resource
	protected GiftClassifyService giftClassifyService;
	@Resource
	protected PointExchangeInfoService pointExchangeInfoService;
	
	/**
	 * 福禄订单查询
	 * @param orderSn 订单号
	 * @return
	 */
	public Map<String, Object> orderSearch(String orderSn) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(STATUS, ERROR);
		try {
			if (StringUtil.isEmpty(orderSn)) {
				result.put(MESSAGE, "订单号不能为空！");
				return result;
			}
			
			DataTable dt = new QueryBuilder("select giftClassifyID, mobileNo from PointExchangeInfo where orderSn=?", orderSn).executeDataTable();
			if (dt == null || dt.getRowCount() != 1) {
				result.put(MESSAGE, "订单号非法！");
				return result;
			}
			String giftClassifyID = dt.getString(0, 0);
			String mobileNo = dt.getString(0, 1);
			GiftClassifySchema giftClassifySchema = new GiftClassifySchema();
			giftClassifySchema.setid(giftClassifyID);
			if (!giftClassifySchema.fill()) {
				result.put(MESSAGE, "数据错误，礼品信息未查询到！");
				return result;
			}
			GiftClassify giftClassify = copyGiftClassify(giftClassifySchema);
			// 取得福禄基础信息
			ExchangeGoodsUtil exchangeGoodsUtil = new ExchangeGoodsUtil();
			Map<String, String> baseInfo = exchangeGoodsUtil.getFuluBaseInfo();
			if (baseInfo == null || baseInfo.isEmpty()) {
				result.put(MESSAGE, "福禄请求类型基础数据未设置！");
				logger.warn("福禄请求类型基础数据未设置！ExchangeGoodsServiceImpl--orderSearch");
				return result;
			}
			
			// 拼装请求参数
			Map<String, String> requestParam = getRequestParam(giftClassify, orderSn, "", "orderSearch", baseInfo);
			// 请求错误的情况
			if (ERROR.equals(requestParam.get(STATUS))) {
				result.put(MESSAGE, requestParam.get(MESSAGE));
				return result;
			}
			
			// 取得福禄交互保存地址
			String saveAddress = baseInfo.get("saveAddress");
			// 福禄请求类型
			String method = requestParam.get("method");
			
			int index = 1;
			String responseContent = "";
			while (index <= 3) {
				// 时间戳
				String timestamp = DateUtil.getCurrentDate("HHmmss");
				// 文件名
				String fileName = (orderSn + "-"+timestamp);
				
				if (StringUtil.isEmpty(responseContent)) {
					responseContent = dealFulu(saveAddress, method, fileName, requestParam);
					
				} else {
					break;
				}
				
				Thread.sleep(2000);
				index++;
			}
			
			if (StringUtil.isEmpty(responseContent)) {
				String errMsg = "福禄请求失败，响应报文为空！";
				if (StringUtil.isNotEmpty(exchangeGoodsUtil.getErrMsg())) {
					errMsg += exchangeGoodsUtil.getErrMsg();
				}
				result.put(MESSAGE, errMsg);
				return result;
			}
			// 处理响应数据
			return dealResponseContent(responseContent, "orderSearch", giftClassify, orderSn, mobileNo, baseInfo);
			
		} catch (Exception e) {
			logger.error("ExchangeGoodsServiceImpl-orderSearch:福禄订单查询异常！" + e.getMessage(), e);
			result.put(MESSAGE, "福禄订单查询系统处理异常！");
		}
		return result;
	}
	
	private GiftClassify copyGiftClassify(GiftClassifySchema giftClassifySchema) {
		GiftClassify giftClassify = new GiftClassify();
		giftClassify.setId(giftClassifySchema.getid());
		giftClassify.setFlowSize(giftClassifySchema.getFlowSize());
		giftClassify.setFlowtype(giftClassifySchema.getflowtype());
		giftClassify.setFuLuGoodsID(giftClassifySchema.getFuLuGoodsID());
		giftClassify.setGiftTitle(giftClassifySchema.getgiftTitle());
		giftClassify.setGiftPrice(giftClassifySchema.getgiftPrice());
		giftClassify.setPoints(giftClassifySchema.getpoints());
		giftClassify.setType(giftClassifySchema.gettype());
		return giftClassify;
	}
	
	/**
	 * 福禄兑换
	 * @param paramMap 参数
	 * @return
	 */
	public Map<String, Object> doExchange(Map<String, String> paramMap) {
		// 主站兑换的处理
		if ("wj_jfsc".equals(paramMap.get("channelSn"))) {// 主站兑换的处理、同主站
			return dealExchangeWJ(paramMap);
		}else if ("wap_jfsc".equals(paramMap.get("channelSn"))) {// wap站兑换的处理、同主站
			return dealExchangeWJ(paramMap);
		}
		return null;
	}
	
	/**
	 * 主站福禄兑换处理
	 * @param paramMap 参数
	 * @return
	 */
	private Map<String, Object> dealExchangeWJ(Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		ExchangeGoodsUtil exchangeGoodsUtil = new ExchangeGoodsUtil();
		result.put(STATUS, ERROR);
		
		try {
			GiftClassify giftClassify = giftClassifyService.getGiftClassify(paramMap.get("presentId"));
			if (giftClassify == null) {
				result.put(MESSAGE, "兑换礼品ID非法！");
				return result;
			}
			// 订单号
			String orderSn = paramMap.get("orderSn");
			// 充值账号
			String rechargeNo = paramMap.get("rechargeNo");
			
			// 取得福禄基础信息
			Map<String, String> baseInfo = exchangeGoodsUtil.getFuluBaseInfo();
			if (baseInfo == null || baseInfo.isEmpty()) {
				result.put(MESSAGE, "福禄请求类型基础数据未设置！");
				logger.warn("福禄请求类型基础数据未设置！ExchangeGoodsServiceImpl--dealWJ");
				return result;
			}
			
			// 拼装请求参数
			Map<String, String> requestParam = getRequestParam(giftClassify, orderSn, rechargeNo, "add", baseInfo);
			// 请求错误的情况
			if (ERROR.equals(requestParam.get(STATUS))) {
				result.put(MESSAGE, requestParam.get(MESSAGE));
				return result;
			}
			
			// 取得福禄交互保存地址
			String saveAddress = baseInfo.get("saveAddress");
			// 福禄请求类型
			String method = requestParam.get("method");
			
			int index = 1;
			String responseContent = "";
			while (index <= 3) {
				// 时间戳
				String timestamp = DateUtil.getCurrentDate("HHmmss");
				// 文件名
				String fileName = (orderSn + "-"+timestamp);
				
				if (StringUtil.isEmpty(responseContent)) {
					responseContent = dealFulu(saveAddress, method, fileName, requestParam);
					
				} else {
					break;
				}
				
				Thread.sleep(2000);
				index++;
			}
			
			if (StringUtil.isEmpty(responseContent)) {
				String errMsg = "福禄请求失败，响应报文为空！";
				if (StringUtil.isNotEmpty(exchangeGoodsUtil.getErrMsg())) {
					errMsg += exchangeGoodsUtil.getErrMsg();
				}
				result.put(MESSAGE, errMsg);
				return result;
			}
			// 处理响应数据
			return dealResponseContent(responseContent, "add", giftClassify, orderSn, rechargeNo, baseInfo);
			
		} catch (Exception e) {
			logger.error("ExchangeGoodsServiceImpl-dealWJ:福禄请求异常！" + e.getMessage(), e);
			result.put(MESSAGE, "系统处理异常！");
		}

		return result;
	}
	
	/**
	 * 福禄响应数据处理
	 * @param responseContent 福禄响应数据
	 * @param requestType 请求类型
	 * @param giftClassify 礼品信息
	 * @param orderSn 订单号
	 * @param rechargeNo 充值号码
	 * @param baseInfo 福禄基础数据
	 * @return
	 */
	private Map<String, Object> dealResponseContent(String responseContent, String requestType, GiftClassify giftClassify, String orderSn, String rechargeNo, Map<String, String> baseInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(STATUS, ERROR);
		try {
			JSONObject json = JSONObject.fromObject(responseContent);
			if (json.has("MessageCode")) {
				String errmsg = json.get("MessageInfo")+"-"+json.get("MessageCode");
				result.put(MESSAGE, errmsg);
				int count = new QueryBuilder("update pointexchangeinfo set wrongMassage = ?, modifyDate = now() where orderSn = ?", errmsg, orderSn).executeNoQuery();
				if (count < 1) {
					logger.error("ExchangeGoodsServiceImpl-dealResponseContent:未更新积分记录表（pointexchangeinfo）错误信息！{}", errmsg);
				}
				
				try {
					String toMail = Config.getValue("InventoryReminderEmail");
					if (StringUtil.isNotEmpty(toMail)) {
						String mailText = "积分兑换订单号："+orderSn+",福禄返回错误："+errmsg;
						String subject = "";
						if ("add".equals(requestType)) {
							subject = "积分兑换礼品，福禄兑换错误";
						} else if ("orderSearch".equals(requestType)) {
							subject = "积分兑换礼品订单，福禄查询错误";
						}
						ActionUtil.sendGeneralMail(toMail, subject, mailText);
					}
				} catch(Exception e) {
					logger.error("ExchangeGoodsServiceImpl-dealResponseContent:发送告警邮件异常！" + e.getMessage(), e);
				}
			} else {
				// 充值响应数据处理
				if ("add".equals(requestType)) {
					return dealAddResponse(giftClassify, orderSn, rechargeNo, json, baseInfo);
					
				} else if ("orderSearch".equals(requestType)) { // 订单查询
					// 订单查询响应数据处理
					return dealAddResponse(giftClassify, orderSn, rechargeNo, json, baseInfo);
					
				} else {
					result.put(MESSAGE, "福禄请求类型错误！");
					logger.error("兑换订单：{},请求类型：{},福禄请求类型错误！ExchangeGoodsServiceImpl--dealResponseContent", orderSn, requestType);
				}
			}
		} catch(Exception e) {
			logger.error("ExchangeGoodsServiceImpl-dealFulu:保存福禄请求数据异常！" + e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 充值响应数据处理
	 * @param giftClassify 礼品信息
	 * @param orderSn 订单号
	 * @param rechargeNo 充值号码
	 * @param json 福禄响应数据
	 * @param fuluBaseInfo 福禄基础数据
	 * @return
	 */
	private Map<String, Object> dealAddResponse(GiftClassify giftClassify, String orderSn, String rechargeNo, JSONObject json, Map<String, String> fuluBaseInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(STATUS, ERROR);
		try {
			// 福禄订单号
			String OrderId = json.getString("OrderId");
			// 福禄商品编号
			String ProductId = json.getString("ProductId");
			// 订单状态
			String Status = json.getString("Status");
			// 进价
			String PurchasePrice = json.getString("PurchasePrice");
			// 卡号
			String CardNumber = "";
			// 密码
			String CardPwd = "";
			// 截止期限
			String CardDeadline = "";
			// 卡信息
			if (json.containsKey("Cards")) {
				JSONArray cards = json.getJSONArray("Cards");
				if (cards != null && !cards.isEmpty()) {
					JSONObject cardsJson = cards.getJSONObject(0);
					CardNumber = cardsJson.getString("CardNumber");
					CardPwd = cardsJson.getString("CardPwd");
					CardDeadline = cardsJson.getString("CardDeadline");
					if (StringUtil.isNotEmpty(CardNumber)) {
						// 解密
						CardNumber = ExchangeGoodsUtil.DoDES(CardNumber, OrderId, fuluBaseInfo.get("secret"));
					}
					if (StringUtil.isNotEmpty(CardPwd)) {
						// 解密
						CardPwd = ExchangeGoodsUtil.DoDES(CardPwd, OrderId, fuluBaseInfo.get("secret"));
					}
				}
			}
			
			String updateSql = "";
			if (StringUtil.isNotEmpty(CardNumber) && StringUtil.isNotEmpty(rechargeNo)) {
				// 发送短信
				String password = "无";
				if (StringUtil.isNotEmpty(CardNumber)) {
					password = CardPwd;
				} 
				String sendData = giftClassify.getPoints() + ";" + giftClassify.getGiftPrice() + ";" 
						+ giftClassify.getGiftTitle() + ";" + CardNumber + ";" + password + ";";
				if (ActionUtil.sendSms("wj00205", rechargeNo, sendData)) {
					updateSql = ", status='20' ";
				} else {
					logger.error("ExchangeGoodsServiceImpl-dealAddResponse:发送卡号、卡密信息失败！订单号：{}", orderSn);
				}
			} else {
				updateSql = ", status='20' ";
			}
			
			// 更新积分兑换记录表
			QueryBuilder qb = new QueryBuilder("update pointexchangeinfo set fuLuOrderSn=?, fuLuGoodsID=?, purchasePrice=?, fuLuOrderStatus=?, cardNo=?, cardKey=?, cardDeadline=?, modifyDate=now()"+updateSql+" where orderSn = ?");
			qb.add(OrderId);
			qb.add(ProductId);
			qb.add(PurchasePrice);
			qb.add(Status);
			qb.add(CardNumber);
			qb.add(CardPwd);
			qb.add(CardDeadline);
			qb.add(orderSn);
			int count = qb.executeNoQuery();
			if (count < 1) {
				result.put(MESSAGE, "更新积分记录表失败！");
			} else {
				result.put(STATUS, SUCCESS);
			}
			
		} catch(Exception e) {
			logger.error("ExchangeGoodsServiceImpl-dealAddResponse:处理福禄响应数据异常！" + e.getMessage(), e);
			result.put(MESSAGE, "系统错误！");
		}
		return result;
	}
	
	/**
	 * 福禄交互处理
	 * @param saveAddress 交互数据保存地址
	 * @param method 请求类型
	 * @param fileName 文件名
	 * @param requestParam 请求数据
	 * @return 响应数据
	 */
	private String dealFulu(String saveAddress, String method, String fileName, Map<String, String> requestParam) {
		ExchangeGoodsUtil exchangeGoodsUtil = new ExchangeGoodsUtil();
		// 保存请求数据
		try {
			exchangeGoodsUtil.saveFuluRequest(saveAddress, method, fileName, JSONObject.fromObject(requestParam).toString());
		} catch(Exception e) {
			logger.error("ExchangeGoodsServiceImpl-dealFulu:保存福禄请求数据异常！" + e.getMessage(), e);
		}
		// 福禄请求交互
		String responseContent = exchangeGoodsUtil.fuluPost(requestParam);
		
		// 保存响应数据
		try {
			exchangeGoodsUtil.saveFuluResponse(saveAddress, method, fileName, responseContent);
		} catch(Exception e) {
			logger.error("ExchangeGoodsServiceImpl:保存福禄响应数据异常！" + e.getMessage(), e);
		}
		
		return responseContent;
	}

	/**
	 * 拼装请求参数
	 * @param giftClassify 礼品信息表
	 * @param orderSn 订单号
	 * @param requestType 请求类型-充值：add、库存查询：stockSearch、订单查询：requestType
	 * @return 请求参数
	 */
	public Map<String, String> getRequestParam(GiftClassify giftClassify, String orderSn, String rechargeNo, String requestType, Map<String, String> fuluBaseInfo) {
		Map<String, String> requestMap = new HashMap<String, String>();
		
		// 礼品类型+"_"+请求类型 取得对应福禄的method
		String key = giftClassify.getType()+"_"+requestType;
		if (!fuluBaseInfo.containsKey(key)) {
			requestMap.put(STATUS, ERROR);
			requestMap.put(MESSAGE, "福禄请求类型基础数据未设置！");
			logger.error("兑换订单：{},请求类型：{},福禄请求类型基础数据未设置！ExchangeGoodsServiceImpl--getRequestParam", orderSn, requestType);
			return requestMap;
		}
		String method = fuluBaseInfo.get(key);
		requestMap.put("method", method);
		// 福禄云接口编号
		requestMap.put("customerid", fuluBaseInfo.get("customerid"));
		
		// 公共参数
		commonParam(requestMap);
		
		// 充值
		if ("add".equals(requestType)) {
			// 拼装充值请求参数
			addRequestParam(giftClassify, orderSn, rechargeNo, requestMap);
			
		} else if ("stockSearch".equals(requestType)) { // 库存查询
			// 拼装库存查询请求参数
			stockSearchRequestParam(giftClassify, rechargeNo, requestMap);
			
		} else if ("orderSearch".equals(requestType)) { // 订单查询
			// 拼装订单查询请求参数
			// 合作商家订单号
			requestMap.put("customerorderno", orderSn);
			
		} else {
			requestMap.put(STATUS, ERROR);
			requestMap.put(MESSAGE, "福禄请求类型错误！");
			logger.error("兑换订单：{},请求类型：{},福禄请求类型错误！ExchangeGoodsServiceImpl--getRequestParam", orderSn, requestType);
		}
		
		return requestMap;
	}
	
	/**
	 * 公共请求参数
	 * @param requestMap
	 */
	private void commonParam( Map<String, String> requestMap) {
		requestMap.put("timestamp", DateUtil.getCurrentDateTime());
		requestMap.put("format", "json");
		requestMap.put("v", "1.0");
	}
	
	/**
	 * 拼装充值请求参数
	 * @param giftClassify 礼品表
	 * @param orderSn 订单号
	 * @param rechargeNo 充值号码
	 * @param requestMap 请求参数
	 */
	private void addRequestParam(GiftClassify giftClassify, String orderSn, String rechargeNo, Map<String, String> requestMap) {
		// 合作商家订单号
		requestMap.put("customerorderno", orderSn);
		
		// 手机话费充值
		if ("2".equals(giftClassify.getType())) {
			// 充值手机号码
			requestMap.put("chargephone", rechargeNo);
			// 充值面值
			requestMap.put("chargeparvalue", giftClassify.getGiftPrice());
			// 回调地址
			requestMap.put("notifyurl", notifyurl);
			
		} else if ("5".equals(giftClassify.getType())) {// 手机流量充值
			// 充值手机号码
			requestMap.put("chargephone", rechargeNo);
			// 流量大小
			requestMap.put("chargeparvalue", giftClassify.getFlowSize());
			// 流量类型
			requestMap.put("areatype", giftClassify.getFlowtype());
			// 回调地址
			requestMap.put("notifyurl", notifyurl);
			
		} else if ("6".equals(giftClassify.getType())) {// 账号直冲
			// 充值账号
			requestMap.put("chargeaccount", rechargeNo);
			// 福禄商品编号
			requestMap.put("productid", giftClassify.getFuLuGoodsID());
			// 购买数量
			requestMap.put("buynum", "1");
			// 回调地址
			requestMap.put("notifyurl", notifyurl);
			
		} else if ("3".equals(giftClassify.getType())) {// 卡密取卡
			// 福禄商品编号
			requestMap.put("productid", giftClassify.getFuLuGoodsID());
			// 购买数量
			requestMap.put("buynum", "1");
		}
	}
	
	/**
	 * 拼装库存查询请求参数
	 * @param giftClassify 礼品表
	 * @param rechargeNo 充值号码
	 * @param requestMap 请求参数
	 */
	private void stockSearchRequestParam(GiftClassify giftClassify, String rechargeNo, Map<String, String> requestMap) {
		// 手机话费充值
		if ("2".equals(giftClassify.getType())) {
			// 充值手机号码
			requestMap.put("chargephone", rechargeNo);
			// 充值面值
			requestMap.put("chargeparvalue", giftClassify.getGiftPrice());
			
		} else if ("5".equals(giftClassify.getType())) {// 手机流量充值
			// 充值手机号码
			requestMap.put("chargephone", rechargeNo);
			// 流量大小
			requestMap.put("chargeparvalue", giftClassify.getFlowSize());
			// 流量类型
			requestMap.put("areatype", giftClassify.getFlowtype());
		}
	}
	
	public Map<String, Object> checkParam(String param) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(STATUS, ERROR);
		if (StringUtil.isNotEmpty(param)) {
			result.put(MESSAGE, "参数不能为空！");
			return result;
		} else {
			// 解密
			String exchangeInfo = AesUtilQN.decrypt(param, Config.getValue("ExchangeKey"));
			if (StringUtil.isNotEmpty(exchangeInfo)) {
				result.put(MESSAGE, "参数不合法！");
				return result;
			}
			try {
				Map<String, String> paramMap = new HashMap<String, String>();
				JSONObject json = JSONObject.fromObject(exchangeInfo);
				// 渠道
				String channelSn = String.valueOf(json.get("channelSn"));
				if ("TB".equalsIgnoreCase(channelSn)) {
					// 淘宝TODO
					
				} else if ("wj_jfsc".equalsIgnoreCase(channelSn)) {
					String presentId = String.valueOf(json.get("presentId"));
					String memberId = String.valueOf(json.get("memberId"));
					String rechargeNo = String.valueOf(json.get("rechargeNo"));
					String orderSn = String.valueOf(json.get("orderSn"));
					if (StringUtil.isEmpty(memberId) || !memberService.isExistByMemberId(memberId)) {
						result.put(MESSAGE, "请登录后再兑换！");
						return result;
					}
					if (StringUtil.isEmpty(rechargeNo)) {
						result.put(MESSAGE, "充值号码不能为空！");
						return result;
					}
					if (StringUtil.isEmpty(presentId)) {
						result.put(MESSAGE, "兑换礼品ID不能为空！");
						return result;
					}
					if (StringUtil.isEmpty(orderSn)) {
						result.put(MESSAGE, "兑换礼品订单号不能为空！");
						return result;
					}
					paramMap.put("channelSn", channelSn);
					paramMap.put("memberId", memberId);
					paramMap.put("rechargeNo", rechargeNo);
					paramMap.put("presentId", presentId);
					paramMap.put("orderSn", orderSn);
				} else {
					result.put(MESSAGE, "渠道非法！");
					return result;
				}
				
				result.put(MESSAGE, paramMap);
			} catch(Exception e) {
				logger.error("兑换礼品类ExchangeGoodsAction 转换参数错误！" + e.getMessage(), e);
				result.put(MESSAGE, "参数不合法！");
				return result;
			}
			
		}
		result.put(STATUS, SUCCESS);
		return result;
	}

	public static void main(String[] args) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("chargetime", "2016-08-29 19:52:35");
		param.put("customerorderno", "2014000000000191");
		param.put("orderno", "789076365");
		param.put("remark", "交易成功");
		param.put("status", "True");
		
		
		
		//param.put("", "");
		ExchangeGoodsUtil exchangeGoodsUtil = new ExchangeGoodsUtil();
		String sign = "e8c028494240e6c1f22523547886a4ba";
		String secret = "951D5E9100FC6B555132444BCA222C50";
		if (!sign.equals(exchangeGoodsUtil.sign(param, secret))) {
//			System.out.println("error");

		} else {
//			System.out.println("right");
		}
		
	}
	/**
	 * 福禄充值回调
	 * @param param
	 * @param sign
	 * @return
	 */
	public boolean notifyFulu(Map<String, String> param, String sign) {
		param.put("orderno", realyOrdersn(param.get("orderno")));
		logger.info("ExchangeGoodsServiceImpl--notifyFulu 参数：{} 签名：{}", param, sign);
		ExchangeGoodsUtil exchangeGoodsUtil = new ExchangeGoodsUtil();
		if (StringUtil.isEmpty(sign)) {
			logger.warn("ExchangeGoodsServiceImpl--notifyFulu 福禄回调签名为空！回调数据：{}", param.toString());
			return false;
		}
		Map<String, String> baseInfo = exchangeGoodsUtil.getFuluBaseInfo();
		String secret = "";
		if (baseInfo != null && !baseInfo.isEmpty()) {
			secret = baseInfo.get("secret");
		} else {
			logger.warn("ExchangeGoodsServiceImpl--notifyFulu 福禄基础数据未设置！回调数据：{}", param.toString());
			return false;
		}
		// 验签
		if (!sign.equals(exchangeGoodsUtil.sign(param, secret))) {
			logger.warn("ExchangeGoodsServiceImpl--notifyFulu 福禄回调签名错误！回调数据：{}", param.toString());
			return false;
		}
		
		String updateSql = "";
		String statusMsg = "失败";
		if ("true".equalsIgnoreCase(param.get("status"))) {
			updateSql = ", status='21' ";
			statusMsg = "成功";
		}
		QueryBuilder qb = new QueryBuilder("update pointexchangeinfo set fuLuOrderSn=?, fuLuOrderStatus=?, modifyDate=now()"+updateSql+" where orderSn=?");
		qb.add(param.get("orderno"));
		qb.add(statusMsg);
		qb.add(param.get("customerorderno"));
		logger.info("ExchangeGoodsServiceImpl--notifyFulu 更新sql：{}", qb.getSQL());
		int rowCount = qb.executeNoQuery();
		logger.info("ExchangeGoodsServiceImpl--notifyFulu 更新条数：{}", rowCount);
		
		if ("false".equalsIgnoreCase(param.get("status"))) {
			try {
				String toMail = Config.getValue("InventoryReminderEmail");
				if (StringUtil.isNotEmpty(toMail)) {
					String mailText = "积分兑换礼品福禄充值失败："+param.get("remark")+",兑换订单号："+param.get("customerorderno")+"。福禄返回数据："+param.toString();
					String subject = "积分兑换礼品，福禄充值失败";
					ActionUtil.sendGeneralMail(toMail, subject, mailText);
				}
			} catch(Exception e) {
				logger.error("ExchangeGoodsServiceImpl-notifyFulu:福禄回调，福禄充值失败发送告警邮件异常！" + e.getMessage(), e);
			}
		} else {
			// 福禄充值成功发送短信、邮件
			sendSMS(param.get("customerorderno"));
		}
		
		return true;
	}
	
	/**
	 * 福禄充值成功发送短信、邮件
	 * @param orderSn
	 * @return
	 */
	private boolean sendSMS(String orderSn) {
		try {
			DataTable dt = new QueryBuilder("select g.giftTitle,g.points,g.giftPrice,g.type,p.mobileNo,p.memberId from pointexchangeinfo p, giftClassify g where p.orderSn=? and p.giftClassifyID=g.id", orderSn).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				String giftTitle = dt.getString(0, 0);
				String points = dt.getString(0, 1);
				String giftPrice = dt.getString(0, 2);
				String type = dt.getString(0, 3);
				String mobileNo = dt.getString(0, 4);
				String memberId = dt.getString(0, 5);
				// 账号直冲
				if ("6".equals(type)) {
					dt = new QueryBuilder("select isMobileNOBinding, isEmailBinding, email, mobileNO from member where id=?", memberId).executeDataTable();
					if (dt != null && dt.getRowCount() > 0) {
						String toMobile = dt.getString(0, 3);
						String toEmail = dt.getString(0, 2);
						if ("Y".equalsIgnoreCase(dt.getString(0, 0)) && StringUtil.isNotEmpty(toMobile)) {
							ActionUtil.sendSms("wj00317", toMobile, points + ";" + giftPrice + ";" + giftTitle);
						} else if ("Y".equalsIgnoreCase(dt.getString(0, 1)) && StringUtil.isNotEmpty(toEmail)) {
							Map<String, Object> data = new HashMap<String, Object>();
							// 积分数
							data.put("pointnum", points);
							// 礼品价格
							data.put("giftprice", giftPrice);
							// 标题
							data.put("title", giftTitle); 
							// 会员昵称
							data.put("MemberRealName", "尊敬的会员您好:");
							// 显示活动信息
							data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
							ActionUtil.sendMail("wj00318", toEmail, data);
						}
					}
					
				} else {// 话费、流量
					// 发送短信
					ActionUtil.sendSms("wj00317", mobileNo, points + ";" + giftPrice + ";" + giftTitle);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}
	
	public String realyOrdersn(String orderno){
		if(StringUtil.isEmpty(orderno)){
			logger.error("福禄返回订单号异常,福禄返回订单号为空");
		}
		String arr[]=orderno.split("_");
		if(arr.length>2 || arr.length<=0){
			logger.error("福禄返回订单号异常");
		}
		return arr[0];
	}
}
