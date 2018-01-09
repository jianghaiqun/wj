/**
 * 积分兑换接口
 * @author cuishigang
 * @creaeDate 2015-06-02
 */
package cn.com.sinosoft.util;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.entity.GoodsStock;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.GiftClassifyService;
import cn.com.sinosoft.service.GoodsStockService;
import cn.com.sinosoft.service.MemberService;
import com.alibaba.fastjson.JSONObject;
import com.finance.activemq.producer.QueueSender;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.GiftClassifySchema;
import com.sinosoft.schema.GoodsStockSchema;
import com.sinosoft.schema.PointExchangeInfoSchema;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.SDInterActionSchema;
import com.sinosoft.schema.SDInterActionSet;
import com.sinosoft.schema.memberSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PointExchangeInterface
{
	protected static final Logger logger = LoggerFactory.getLogger(PointExchangeInterface.class);
	public static final String MESSAGE = "message";
	public static final String STATUS = "status";
	public static final String SUCCESS = "success";
	public static final String CHANNELSN = "channelSn";
	public static final String WJ = "wj_jfsc";
	public static final String PRESENT_ID = "presentId";
	public static final String MEMBER_ID = "memberId";
	public static final String MOBILENO = "rechargeNo";
	
	public QueueSender queueSender;
	public GiftClassifyService mGiftClassifyService;
	public GoodsStockService mGoodsStockService;
	public MemberService memberService;
	public String memberid;
	public String presentID;
	
	/**
	 * 初始化参数
	 * @param cGiftClassifyService
	 * @param cGoodsStockService
	 * @param cPointExchangeInfoService
	 * @param cMemberService
	 * @param cMemberId
	 * @param cPresentID
	 */
	public void init( GiftClassifyService cGiftClassifyService,GoodsStockService cGoodsStockService,
			MemberService cMemberService,String cMemberId,String cPresentID) {
		mGiftClassifyService = cGiftClassifyService;
		mGoodsStockService = cGoodsStockService;
		memberService = cMemberService;
		memberid = cMemberId;
		presentID = cPresentID;
	}
	
	public boolean checkStockNum(int stockNum) {
		if (stockNum <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 积分校验
	 * @return
	 */
	public Map<String,String> checkPoint(String cInfo) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		
		Map<String, String> resultMap = checkParams(cInfo);
		if (!SUCCESS.equals(resultMap.get(STATUS))) {
			return resultMap;
		}
		// 会员积分数
		String mem_point = resultMap.get("mem_point");
		GiftClassify mGiftClassifySchema = mGiftClassifyService.getGiftClassify(presentID);
		
		if (!checkStockNum(Integer.parseInt(mGiftClassifySchema.getLastNum()))) {
			jsonMap.put(MESSAGE, "");
			jsonMap.put(STATUS, "6");
			return jsonMap;
		}
		int point = Integer.parseInt(mGiftClassifySchema.getPoints());
		int memPoint = Integer.valueOf(mem_point);
		if (point <= memPoint) {
			jsonMap.put(MESSAGE, "");
			jsonMap.put(STATUS, "4");
			jsonMap.put("point", String.valueOf(point));
			jsonMap.put("mobilemsg", String.valueOf(resultMap.get(MESSAGE)));
			jsonMap.put("mobilenum", String.valueOf(cInfo));

		} else {
			jsonMap.put(MESSAGE, mem_point);
			jsonMap.put(STATUS, "5");
		}
		return jsonMap;
		
	}
	
	public Map<String, String>  checkParams(String mobile) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		Map<String, String> map = validateMobile(mobile);
		if ("-1".equals(map.get("result"))) {
			jsonMap.put(MESSAGE, "您的电话号码输入不正确!");
			jsonMap.put(STATUS, "1");
			return jsonMap;
		}
		if ("0".equals(map.get("result"))) {
			jsonMap.put(MESSAGE, "您的电话号码为未知运营商!");
			jsonMap.put(STATUS, "1");
			return jsonMap;
		}
		String id = memberid;
		if (StringUtil.isEmpty(id)) {
			jsonMap.put(MESSAGE, "您还没有登录，不能进行兑换!");
			jsonMap.put(STATUS, "2");
			return jsonMap;
		}
		Member member = memberService.load(id);
		if (member == null) {
			jsonMap.put(MESSAGE, "您还没有登录，不能进行兑换!");
			jsonMap.put(STATUS, "2");
			return jsonMap;
		}
		jsonMap.put(STATUS, "success");
		jsonMap.put(MESSAGE, map.get(MESSAGE));
		jsonMap.put("type", map.get("result"));
		jsonMap.put("memberId", member.getId());
		jsonMap.put("mem_point", String.valueOf(member.getCurrentValidatePoint()));
		return jsonMap;
	}
	
	/**
	 * 简单校验手机合法性
	 * 
	 * @param mobile
	 * @return
	 */
	public Map<String, String> validateMobile(String mobile) {

		Map<String, String> map = new HashMap<String, String>();
		String returnString = "";
		String returnMessage = "";
		String htmlFilePath = "";
		if (mobile == null || mobile.trim().length() != 11) {
			returnString = "-1";
			returnMessage = "";
			map.put("result", returnString);
			map.put("message", returnMessage);
			return map;
		}
		map.put("result", returnString);
		map.put("message", returnMessage);
		map.put("htmlFilePath", htmlFilePath);
		return map;
	}
	
	/**
	 * 积分兑换
	 * @return
	 */
	public Map<String, String> Exchange(String cInfo) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		String mobile = cInfo;
		Map<String, String> resultMap = this.checkParams(mobile);
		if (!SUCCESS.equals(resultMap.get(STATUS))) {
			return resultMap;
		}
		
		// 会员Id
		String memberId = resultMap.get("memberId");
		// 会员积分数
		String mem_point = resultMap.get("mem_point");
		// 电话卡类型
		String type = resultMap.get("type");
		
		int dealCount = 0;
		do {
			GiftClassifySchema mGiftClassifySchema = new GiftClassifySchema();
			mGiftClassifySchema.setid(presentID);
			if (!mGiftClassifySchema.fill()) {
				jsonMap.put(MESSAGE, "兑换的礼品不存在！");
				jsonMap.put(STATUS, "error");
				return jsonMap;
			}
			
			if (!checkStockNum(Integer.parseInt(mGiftClassifySchema.getlastNum()))) {
				jsonMap.put(MESSAGE, "");
				jsonMap.put(STATUS, "6");
				return jsonMap;
			}
			int point = Integer.parseInt(mGiftClassifySchema.getpoints());
			int memPoint = Integer.parseInt(mem_point);
			logger.info("会员积分:{}兑换商品积分:{}", mem_point, point);
	
			if (memPoint > 0 && point <= memPoint) {
				synchronized (memberId) {
					synchronized (mGiftClassifySchema.getid()) {
						// 模式是现货的情况
						if ("1".equals(mGiftClassifySchema.getPointsExchangeType())) {
							jsonMap = dealStock(mGiftClassifySchema, memberId, type, mobile);
							
						} else {
							// 模式是福禄的情况
							jsonMap = dealFulu(mGiftClassifySchema, memberId, mobile);
						}
						if("commitFail".equals(jsonMap.get(STATUS))) {
							dealCount++;
							continue;
						} else {
							break;
						}
					}
				}
			} else {
				jsonMap.put(MESSAGE, mem_point);
				jsonMap.put(STATUS, "5");
				break;
			}
		} while(dealCount < Constant.UPDATE_MEMBER_DEAL_COUNT);
		
		if (dealCount >= Constant.UPDATE_MEMBER_DEAL_COUNT) {
			jsonMap.put(MESSAGE, "兑换礼品失败！请重新兑换或者联系客服！");
			jsonMap.put(STATUS, "error");
		}
		return jsonMap;
	}
	
	/**
	 * 福禄模式礼品的兑换处理
	 * 
	 * @param mGiftClassifySchema
	 *            礼品信息
	 * @param memberId
	 *            会员Id
	 * @param mobile
	 *            手机号
	 * @return 兑换处理结果
	 */
	private Map<String, String> dealFulu(GiftClassifySchema mGiftClassifySchema, String memberId, String mobile) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		// 生成订单号
		String orderSn = PubFun.GetPointsOrderSn();
		try {
			Transaction trans = new Transaction();// 事务创建
			// 更新礼品表库存、人气
			updateGiftNum(mGiftClassifySchema, trans);

			jsonMap.put("orderSn", orderSn);
			// 积分流水与会员积分更新
			Map<String, String> resultMap = subtractPoint(memberId, mGiftClassifySchema, orderSn, trans);
			if (!SUCCESS.equals(resultMap.get(STATUS))) {
				return resultMap;
			}
			
			// 生成积分兑换表记录
			PointExchangeInfoSchema tPointExchangeInfo = new PointExchangeInfoSchema();
			makePointExchangeInfo(tPointExchangeInfo, mGiftClassifySchema, "", mobile, memberId, orderSn);
			
			trans.add(tPointExchangeInfo, Transaction.INSERT);
			if(!trans.commitRoll()){
				logger.error("兑换礼品更新数据失败！礼品id:{}", mGiftClassifySchema.getid());
				jsonMap.put(MESSAGE, "兑换失败，请联系客服！");
				jsonMap.put(STATUS, "commitFail");
				return jsonMap;
			}
			
			// 少量库存发送告警邮件
			lowStocksWarn(Integer.parseInt(mGiftClassifySchema.getlastNum()) - 1, mGiftClassifySchema.getgiftTitle(), orderSn);
			// 用户消耗大于等于500积分时，给用户发送积分扣除提醒
			warn(memberId, mGiftClassifySchema.getpoints(), mGiftClassifySchema.getgiftTitle(), orderSn);
			
			jsonMap.put(STATUS, "4");
			Map<String, String> param = new HashMap<String, String>();
			param.put(CHANNELSN, WJ);
			param.put(PRESENT_ID, mGiftClassifySchema.getid());
			param.put(MEMBER_ID, memberId);
			param.put(MOBILENO, mobile);
			param.put("orderSn", orderSn);
			queueSender = new QueueSender();
			queueSender.sendToPointBack(JSONObject.toJSONString(param));
		} catch (Exception e) {
			logger.error("积分兑换福禄礼品异常！PointExchangeInterface--dealFulu" + e.getMessage(), e);
		}
		return jsonMap;
	}
	
	/**
	 * 现货模式礼品的兑换处理
	 * @param mGiftClassifySchema 礼品信息
	 * @param memberId 会员Id
	 * @param type 电话卡类型
	 * @param mobile 手机号
	 * @return 兑换处理结果
	 */
	private Map<String, String> dealStock(GiftClassifySchema mGiftClassifySchema, String memberId, String type, String mobile) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		List<GoodsStock> goodlist;
		if (StringUtil.isNotEmpty(type)) {
			// 根据presentID 查看礼品库存
			goodlist = mGoodsStockService.goodslistByType(presentID, "0" + type);
		} else {
			goodlist = mGoodsStockService.goodslist(presentID);
		}
		
		// 库存不足
		if (!checkStockNum(goodlist.size())) {
			jsonMap.put(MESSAGE, "");
			jsonMap.put(STATUS, "6");
			return jsonMap;
		}
		Transaction trans = new Transaction();// 事务创建
		try {
			// 库存递减
			GoodsStock tGoodsStock = goodlist.get(0);
			GoodsStockSchema mGoodsStockSchema = new GoodsStockSchema();
			mGoodsStockSchema.setid(tGoodsStock.getId());
			mGoodsStockSchema.fill();
			// 更新礼品库存表状态、会员
			updateStock(mGoodsStockSchema, memberId, trans);
			
			// 更新礼品表库存、人气
			updateGiftNum(mGiftClassifySchema, trans);
			
			// 积分流水与会员积分更新
			Map<String, String> resultMap = subtractPoint(memberId, mGiftClassifySchema, tGoodsStock.getId(), trans);
			if (!SUCCESS.equals(resultMap.get(STATUS))) {
				return resultMap;
			}
			
			// 生成订单号
			String orderSn = PubFun.GetPointsOrderSn();
			jsonMap.put("orderSn", orderSn);
			// 生成积分兑换表记录
			PointExchangeInfoSchema tPointExchangeInfo = new PointExchangeInfoSchema();
			makePointExchangeInfo(tPointExchangeInfo, mGiftClassifySchema, tGoodsStock.getId(), mobile, memberId, orderSn);
		    
			trans.add(tPointExchangeInfo, Transaction.INSERT);
			if(!trans.commitRoll()){
				logger.error("兑换礼品更新数据失败！礼品id:{}", mGiftClassifySchema.getid());
				jsonMap.put(MESSAGE, "兑换失败，请联系客服！");
				jsonMap.put(STATUS, "commitFail");
				return jsonMap;
			}
			
			// 少量库存发送告警邮件
			lowStocksWarn(Integer.parseInt(mGiftClassifySchema.getlastNum()), mGiftClassifySchema.getgiftTitle(), tGoodsStock.getId());
			// 用户消耗大于等于500积分时，给用户发送积分扣除提醒
			warn(memberId, mGiftClassifySchema.getpoints(), mGiftClassifySchema.getgiftTitle(), tGoodsStock.getId());
						
			// 发送电话卡充值信息
			boolean sendResult = sendMobileCard(mobile, mGiftClassifySchema, tGoodsStock.getCardNo(), tGoodsStock.getPassWord());
			// 发送成功的状态
			if (sendResult) {
				// 更新成已发送的状态
				new QueryBuilder("update PointExchangeInfo set status='20' where id=?", tPointExchangeInfo.getid()).executeNoQuery();
			} else {
				logger.error("积分兑换现货礼品发送短信失败！礼品id:{}会员id:{}", mGiftClassifySchema.getid(), memberId);
			}
			jsonMap.put(STATUS, "4");
		} catch(Exception e) {
			logger.error("积分兑换礼品更新失败！PointExchangeInterface--Exception" + e.getMessage(), e);
		}
		return jsonMap;
	}
	
	/**
	 * 用户消耗大于等于500积分时，给用户发送积分扣除提醒
	 * @param memId
	 * @param usedPoints
	 * @param giftName
	 * @param businessid
	 */
	public void warn(String memId, String usedPoints, String giftName, String businessid) {
		try {
			
			memberSchema member = new memberSchema();
			member.setid(memId);

			if (member.fill()) {
				String warnPoint = Config.getValue("ChangeGiftWarnNum");
				if (StringUtil.isNotEmpty(warnPoint) && Integer.parseInt(usedPoints) > Integer.parseInt(warnPoint)) {
					Member mem = new Member();
					mem.setEmail(member.getemail());
					mem.setMobileNO(member.getmobileNO());

					new PointsCalculate().sendWarnMail(mem, giftName, Integer.parseInt(usedPoints), businessid);
				}
				
				// 用户消耗大于等于500积分时，给用户发送积分扣除提醒
				if (Integer.parseInt(usedPoints) >= 500 && StringUtil.isNotEmpty(member.getemail())) {
					// 发送积分扣除提醒邮件.
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("MemberEmail", member.getemail());
					map.put("StandardDate", PubFun.getCurrentDateStandard());
					map.put("UsedIntegral", usedPoints);
					map.put("memberId",member.getid());
					map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT,"1");
					ActionUtil.sendMail("wj00130", member.getemail(), map);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 发送充值信息
	 * @param mobile 手机号
	 * @param point 花费积分数
	 * @param giftPrice 礼品价值
	 * @param giftTitle 礼品名
	 * @param cardno 卡号
	 * @param password 卡密
	 * @return true:发送成功  false:发送失败
	 */
	public boolean sendMobileCard(String mobile, GiftClassifySchema mGiftClassifySchema, String cardno, String password) {
		try {
			String sendData = mGiftClassifySchema.getpoints() + ";" 
			+ mGiftClassifySchema.getgiftPrice() + ";" + mGiftClassifySchema.getgiftTitle() + ";" + password;
			boolean sendResult = ActionUtil.sendSms("wj00204", mobile, sendData);
			return sendResult;
		} catch(Exception e) {
			logger.error("积分兑换现货发送短信异常！PointExchangeInterface--sendMobileCard" + e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * 生成积分兑换表记录
	 * @param mGiftClassifySchema 礼品表
	 * @param stockId 库存Id
	 * @param mobile 手机号
	 * @param memberId 会员Id
	 * @param status 兑换状态
	 * @param orderSn 订单号
	 * @return 
	 * @throws ParseException 
	 */
	private void makePointExchangeInfo(PointExchangeInfoSchema tPointExchangeInfo,GiftClassifySchema mGiftClassifySchema, String stockId, String mobile, String memberId, String orderSn)  { 
		tPointExchangeInfo.setid(NoUtil.getMaxNo("GiftClassifyID", 11));
		tPointExchangeInfo.setgoodsStockID(stockId);
		tPointExchangeInfo.setgiftClassifyID(mGiftClassifySchema.getid());
		tPointExchangeInfo.setmobileNo(mobile);
		tPointExchangeInfo.settype(mGiftClassifySchema.gettype());// 礼品属性
															// 库存表中的type,暂时有：联通、移动、电信
		tPointExchangeInfo.setpoints(mGiftClassifySchema.getpoints());
		tPointExchangeInfo.setmemberid(memberId);
		tPointExchangeInfo.setstatus("7");// 已兑换
		tPointExchangeInfo.setmodifyDate(new Date());
		tPointExchangeInfo.setorderSn(orderSn);			
		tPointExchangeInfo.setcreateDate(new Date());//创建日期
		tPointExchangeInfo.setfuLuGoodsID(mGiftClassifySchema.getFuLuGoodsID());//福禄商品编号
		tPointExchangeInfo.setexchangeQuantity("1");//购买数量
		tPointExchangeInfo.setprop2(WJ);//渠道
	}
	
	/**
	 * 更新礼品表库存、人气
	 * @param mGiftClassifySchema
	 * @return 
	 */
	private void updateGiftNum(GiftClassifySchema mGiftClassifySchema, Transaction trans) {
		QueryBuilder qb;
		String lastNum = String.valueOf(Integer.parseInt(mGiftClassifySchema.getlastNum()) - 1);
		String popularity = String.valueOf(Integer.parseInt(mGiftClassifySchema.getpopularity()) + 1);
		if (StringUtil.isEmpty(mGiftClassifySchema.getversion())) {
			qb = new QueryBuilder("update GiftClassify set lastNum=?, popularity=?, version='1' where id=? and (version is null or version='') ");
			qb.add(lastNum);
			qb.add(popularity);
			qb.add(mGiftClassifySchema.getid());
		} else {
			qb = new QueryBuilder("update GiftClassify set lastNum=?, popularity=?, version=version+1 where id=? and version=? ");
			qb.add(lastNum);
			qb.add(popularity);
			qb.add(mGiftClassifySchema.getid());
			qb.add(mGiftClassifySchema.getversion());
		}
		trans.add(qb);
	}
	
	/**
	 * 更新礼品库存表状态、会员
	 * @param tGoodsStock 库存表
	 * @param memberId 会员ID
	 */
	private void updateStock(GoodsStockSchema mGoodsStockSchema, String memberId, Transaction trans) {
		QueryBuilder qb;
		if (StringUtil.isEmpty(mGoodsStockSchema.getversion())) {
			qb = new QueryBuilder("update GoodsStock set memberid=?, status='1', version='1' where id=? and (version is null or version='') and status !='1' ");
			qb.add(memberId);
			qb.add(mGoodsStockSchema.getid());
		} else {
			qb = new QueryBuilder("update GoodsStock set memberid=?, status='1', version=version+1 where id=? and version=? and status !='1' ");
			qb.add(memberId);
			qb.add(mGoodsStockSchema.getid());
			qb.add(mGoodsStockSchema.getversion());
		}
		trans.add(qb);
	}
	
	/**
	 * 少量库存发送告警邮件
	 * @param stockNum 库存
	 * @param giftName 礼品名称
	 * @param giftType 礼品类型
	 */
	public void lowStocksWarn(int stockNum, String giftName, String giftType) {
		try {
			if (stockNum <= 3) {
				// 发邮件
				String toMail = Config.getValue("InventoryReminderEmail");
				if (StringUtil.isNotEmpty(toMail)) {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("name", giftName);
					data.put("type", giftType);
					if (!ActionUtil.sendMail("wj00102", toMail, data)) {
						logger.error("积分兑换库存不足发送邮件失败！");
					}
				}
			} 
		} catch(Exception e) {
			logger.error("积分兑换礼品少量库存发送告警邮件！礼品名称："+giftName+"、礼品类型："+giftType+"、库存量："+stockNum+"!PointExchangeInterface--lowStocksWarn" + e.getMessage(), e);

		}
	}
	
	private static SDInterActionSet check(String actionId) {

		SDInterActionSchema tSDInterActionSchema = new SDInterActionSchema();
		SDInterActionSet tSDInterActionSet = tSDInterActionSchema.query(new QueryBuilder("where ActionId ='" + actionId
				+ "'"));
		return tSDInterActionSet;
	}
	
	/**
	 * 扣除会员积分
	 * @param memberId
	 * @param mGiftClassifySchema
	 * @param businessId
	 * @param trans
	 * @return
	 */
	public Map<String, String> subtractPoint(String memberId, GiftClassifySchema mGiftClassifySchema, String businessId, Transaction trans) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		
		try {
			memberSchema member = new memberSchema();
			member.setid(memberId);
			if (!member.fill()) {
				jsonMap.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
				jsonMap.put(IntegralConstant.MESSAGE, "会员信息不存在，请重新登录！");
				return jsonMap;
			}
			// 取得使用积分数
			String usedPoints = mGiftClassifySchema.getpoints();
			if (StringUtil.isEmpty(usedPoints)) {
				jsonMap.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
				jsonMap.put(IntegralConstant.MESSAGE, "兑换礼品所需积分数非法，请联系客服！");
				return jsonMap;
			}
			int intUsedPoints = Integer.parseInt(usedPoints);
			if (member.getcurrentValidatePoint() <= 0 || member.getcurrentValidatePoint() < intUsedPoints) {
				jsonMap.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
				jsonMap.put(IntegralConstant.MESSAGE, "可用积分不足。会员积分为：" + member.getcurrentValidatePoint() + " 兑换商品需要积分:"
						+ usedPoints);
				logger.info("可用积分不足。会员积分为：{} 兑换商品需要积分:{}", member.getcurrentValidatePoint(), usedPoints);
				return jsonMap;
			}
			int aboutToExpirePoints = member.getaboutToExpirePoints();
			
			if (aboutToExpirePoints > 0) {
				// 抵扣掉即将过期积分剩余的使用积分数
				int usedPoint = 0;
				if (aboutToExpirePoints >= intUsedPoints) {
					aboutToExpirePoints = aboutToExpirePoints - intUsedPoints;
				} else {
					usedPoint = intUsedPoints - aboutToExpirePoints;
					aboutToExpirePoints = 0;
				}
				// 处理剩余使用积分记录表
				dealPointOverTimeRecord(member.getid(), usedPoint, trans);
			}
			
			QueryBuilder qb;
			if (StringUtil.isEmpty(member.getversion())) {
				qb = new QueryBuilder("update member set currentValidatePoint=?, usedPoint=?, version='1', modifyDate=?, aboutToExpirePoints=? where id=? and (version is null or version='')");
				qb.add(member.getcurrentValidatePoint() - intUsedPoints);
				qb.add(member.getusedPoint() + intUsedPoints);
				qb.add(DateUtil.getCurrentDateTime());
				qb.add(aboutToExpirePoints);
				qb.add(member.getid());
			} else {
				qb = new QueryBuilder("update member set currentValidatePoint=?, usedPoint=?, version=version+1, modifyDate=?, aboutToExpirePoints=? where id=? and version=?");
				qb.add(member.getcurrentValidatePoint() - intUsedPoints);
				qb.add(member.getusedPoint() + intUsedPoints);
				qb.add(DateUtil.getCurrentDateTime());
				qb.add(aboutToExpirePoints);
				qb.add(member.getid());
				qb.add(member.getversion());
			}
			trans.add(qb);
			trans.add(dealSDIntCalendar(mGiftClassifySchema, memberId, businessId), Transaction.INSERT);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonMap.put(IntegralConstant.STATUS, IntegralConstant.FAIL);
			jsonMap.put(IntegralConstant.MESSAGE, "扣除积分操作失败，请重新兑换或联系客服！");
		}
		return jsonMap;
	}
	
	/**
	 * 处理剩余使用积分记录表
	 * @param memberId
	 * @param usedPoint
	 * @param trans
	 */
	private void dealPointOverTimeRecord(String memberId, int usedPoint, Transaction trans) {
		String now = PubFun.getCurrent();
		String operationDate = new QueryBuilder("select operationDate from pointOverTimeRecord where memberId=?", memberId).executeString();
		if (StringUtil.isEmpty(operationDate)) {
			trans.add(new QueryBuilder(" insert into pointOverTimeRecord values (?,?,now())", memberId, usedPoint));
		} else {
			if (now.compareTo(operationDate) >= 0) {
				trans.add(new QueryBuilder(" update pointOverTimeRecord set usedPoints=usedPoints+?,operationDate=now() where memberId=?", usedPoint, memberId));
			} else if (usedPoint > 0) {
				trans.add(new QueryBuilder(" update pointOverTimeRecord set usedPoints=usedPoints+? where memberId=?", usedPoint, memberId));
			}
		}
	}
	
	/**
	 * 生成积分明细
	 * @param mGiftClassifySchema
	 * @param memberId
	 * @param businessid
	 * @return
	 */
	private SDIntCalendarSchema dealSDIntCalendar(GiftClassifySchema mGiftClassifySchema, String memberId, String businessid) {
		SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
		tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
		tSDIntCalendarSchema.setMemberId(memberId);
		tSDIntCalendarSchema.setIntegral(mGiftClassifySchema.getpoints());
		tSDIntCalendarSchema.setSource(cn.com.sinosoft.util.Constant.POINT_SOURCE_EXCHANGE);// 积分来源
		tSDIntCalendarSchema.setManner("1");// 表示收入
		tSDIntCalendarSchema.setStatus("0");
		tSDIntCalendarSchema.setProp1("point");
		tSDIntCalendarSchema.setProp2(mGiftClassifySchema.getgiftPrice());
		tSDIntCalendarSchema.setDescription(mGiftClassifySchema.getgiftTitle());
		tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDIntCalendarSchema.setsvaliDate(PubFun.getCurrentDate());
		if (StringUtil.isNotEmpty(businessid)) {
			tSDIntCalendarSchema.setBusinessid(businessid);
		}

		try {
			Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,
					IntegralConstant.POINT_SOURCE_EXCHANGE, null);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(IntegralConstant.DATA);
			if (list.size() > 0) {
				Map<String, Object> map_data = list.get(0);
				tSDIntCalendarSchema.setDescription(String.valueOf(map_data.get("PointDes")) + " 使用积分：" + mGiftClassifySchema.getpoints());
			} else {
				tSDIntCalendarSchema.setDescription(IntegralConstant.POINT_SOURCE_EXCHANGE_DES + " 使用积分：" + mGiftClassifySchema.getpoints());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return tSDIntCalendarSchema;
	}
}
