package com.wangjin.pointsMall;


import cn.com.sinosoft.service.impl.ExchangeGoodsServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.finance.activemq.producer.QueueSender;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.schema.PointExchangeInfoSchema;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * ClassName: EXGiftManage <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(礼品兑换管理). <br/>
 * date: 2016年8月19日 上午10:01:51 <br/>
 *
 * @author "guanyulong"
 * @version
 */
public class EXGiftManage extends Page {
	
	/**
	 * 
	 * @Title: init 
	 * @Description: TODO(礼品管理查询页面 初始化查询条件数据)
	 * @return Mapx<String,String> 返回类型
	 * @author
	 */
	public static Mapx<String, String> init(Mapx<String, String> params) {

		params.put("Type", HtmlUtil.codeToOptions("PointsMall.Type",true));// 种类
		params.put("fuLuOrderStatus", HtmlUtil.codeToOptions("fuLuOrder.Status", true));// 福禄订单状态
		return params;
	}
     /**
	 * 
	 * dg1DataBind:(礼品兑换查询). <br/>
	 *
	 * @author "guanyulong"
	 * @param dga
	 */
	@SuppressWarnings("rawtypes")
	public static void dg1DataBind(DataGridAction dga) {

		String giftTitle = (String) dga.getParams().get("giftTitle");// 礼品标题
		String type_param = (String) dga.getParams().get("type");// 类别
		String fuLuGoodsID = (String) dga.getParams().get("fuLuGoodsID");// 福禄商品编码
		String memberid = (String) dga.getParams().get("memberid");// 会员id编码
		String createStartDate = (String) dga.getParams().get("createStartDate");// 创建开始时间
		String createEndDate = (String) dga.getParams().get("createEndDate");// 创建结束时间
		String orderSn = (String) dga.getParams().get("orderSn");// 订单号
		String fuLuOrderStatus = (String) dga.getParams().get("fuLuOrderStatus");// 福禄订单状态
																					

		// 查询礼品兑换数据
		QueryBuilder qb = new QueryBuilder("SELECT pei.*,gc.gifttitle FROM PointExchangeInfo pei ,GiftClassify gc WHERE pei.giftClassifyID = gc.id ");
		qb.append(" AND pei.TYPE IN ('1','2','3','4','5','6') ");// 确保取出数据（之前数据库存在数据01发生错误）
		// 一般为 1，2，3，4，5，6 新增5流量直充 6账户直充

		// 礼品标题
		if (StringUtil.isNotEmpty(giftTitle)) {
			qb.append(" and gc.gifttitle like " + "'%" + giftTitle + "%'");
		}
		// 种类/类别
		if (StringUtil.isNotEmpty(type_param)) {
			qb.append(" and pei.type = '" + type_param + "'");
		}

		// 福禄商品编码
		if (StringUtil.isNotEmpty(fuLuGoodsID)) {
			qb.append(" and pei.fuLuGoodsID like " + "'%" + fuLuGoodsID + "%'");
		}

		// 会员Id编码
		if (StringUtil.isNotEmpty(memberid)) {
			qb.append(" and pei.memberid = '" + memberid + "'");
		}
		// 开始时间
		if (StringUtil.isNotEmpty(createStartDate)) {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(pei.CreateDate,'%Y-%m-%d')) >=  UNIX_TIMESTAMP('"
					+ createStartDate.trim() + "')");
		}
		// 结束时间
		if (StringUtil.isNotEmpty(createEndDate)) {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(pei.CreateDate,'%Y-%m-%d')) <=  UNIX_TIMESTAMP('"
					+ createEndDate.trim() + "')");
		}
		// 订单号 ：
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" and pei.orderSn = '" + orderSn + "'");
		}
		// 是否二冲 失败订单？
		// 订单状态
		if (StringUtil.isNotEmpty(fuLuOrderStatus)) {
			if (fuLuOrderStatus.equals("失败")||fuLuOrderStatus.equals("2")) {
				qb.append(" and pei.fuLuOrderStatus = '" + "失败" + "'");
			} else {
				
				qb.append(" and pei.fuLuOrderStatus = '" + fuLuOrderStatus + "'");
			}
		}

		qb.append(" order by pei.createDate desc ");
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		Mapx map_type = CacheManager.getMapx("Code", "PointsMall.Type");// 1:保险产品|2:通讯产品|3:卡密产品|4:卡号产品|5:流量直冲|6:账号直冲|
		Mapx map_status = CacheManager.getMapx("Code", "Jfsc.orderStatus");
		Mapx map_FuLu_status = CacheManager.getMapx("Code", "fuLuOrder.Status");
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			// 礼品种类
			String type = dt.getString(i, "TYPE");
			if (StringUtil.isNotEmpty(type)) {
				dt.set(i, "TYPE", map_type.get(type));
			} else {
				dt.set(i, "TYPE", "");
			}
			// 福禄订单状态
			String fuLuOrder_Status = dt.getString(i, "fuLuOrderStatus");
			if (StringUtil.isNotEmpty(fuLuOrder_Status)) {
				dt.set(i, "fuLuOrderStatus", map_FuLu_status.get(fuLuOrder_Status));
			} else {
				dt.set(i, "fuLuOrderStatus", "");
			}
			//积分兑换状态
			String JiFenStatus = dt.getString(i, "status");
			if (StringUtil.isNotEmpty(JiFenStatus)) {
				dt.set(i, "status", map_status.get(JiFenStatus));
			} else {
				dt.set(i, "status", "");
			}
			//渠道
			String prop2 = dt.getString(i, "prop2");
			if (StringUtil.isEmpty(prop2)) {
				dt.set(i, "prop2", "");//以前老逻辑无渠道
			}else{
				if("wj_jfsc".equals(prop2)){
					dt.set(i, "prop2", "主站");
				}else if("wap_jfsc".equals(prop2)){
					dt.set(i, "prop2", "wap站");
				}
			}
		}
		dga.bindData(dt);
	}

	/**
	 * 
	 * initExchangeDialog:(更新订单状态展示). <br/>
	 * @author "guanyulong"
	 * @param params
	 * @return
	 */
	public static Mapx<String, String> initExchangeDialog(Mapx<String, String> params) {

		String ID = params.getString("ID");
		if (StringUtil.isNotEmpty(ID)) {
			QueryBuilder qb = new QueryBuilder("SELECT pei.*,gc.gifttitle FROM PointExchangeInfo pei ,GiftClassify gc WHERE pei.giftClassifyID = gc.id and pei.id=?");
			qb.add(ID);
			DataTable dt = qb.executeDataTable();
			DataRow dr = dt.getDataRow(0);
			params.put("gifttitle", dr.getString("gifttitle"));
			params.put("exchangeQuantity", dr.getString("exchangeQuantity"));
			params.put("orderSn", dr.getString("orderSn"));
			params.put("fuLuOrderSn", dr.getString("fuLuOrderSn"));
			params.put("fuLuGoodsID", dr.getString("fuLuGoodsID"));
			params.put("memberid", dr.getString("memberid"));
			params.put("cardNo", dr.getString("cardNo"));
			params.put("cardKey", dr.getString("cardKey"));
			params.put("mobileNo", dr.getString("mobileNo"));
			params.put("wrongMassage", dr.getString("wrongMassage"));
		}
		return params;
	}

/**
 * 
 * saveExchangeGift:(保存信息,调用二次充值). <br/>
 *
 * @author "guanyulong 
 */
	public void saveExchangeGift() {

		PointExchangeInfoSchema pointExchangeIF = new PointExchangeInfoSchema();
		pointExchangeIF.setid(Request.getString("ID"));
		pointExchangeIF.fill();
		String _n="1";
		if(StringUtil.isNotEmpty(pointExchangeIF.getprop3())){
			_n=String.valueOf((Integer.parseInt(pointExchangeIF.getprop3().split("_")[1])+1));
		}
		String ordersn=pointExchangeIF.getorderSn().toString()+"_"+_n;
		Transaction trans = new Transaction();// 事务创建
		int check = checkEXGiftManager(pointExchangeIF);
		if (check != 1) {//校验
			return;
		}
		try {
			//pointExchangeIF.setValue(Request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			pointExchangeIF.setmobileNo(Request.getString("mobileNo"));
			pointExchangeIF.setmodifyDate(date);
			pointExchangeIF.setfuLuOrderStatus("处理中");
			pointExchangeIF.setorderSn(pointExchangeIF.getorderSn());
			pointExchangeIF.setwrongMassage("已进行二次充值！");
			pointExchangeIF.setprop3(ordersn);//福禄第几次二次充值
			trans.add(pointExchangeIF, Transaction.UPDATE);
			PossToFuLu(pointExchangeIF);//调用福禄接口
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("礼品保存成功！");
				return;
			} else {
				Response.setStatus(0);
				Response.setMessage("保存礼品失败！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	
	/**
	 * 
	 * PossToFuLu:(saveExchangeGift的验证). <br/>
	 *
	 * @author "guanyulong"
	 */
	public int checkEXGiftManager(PointExchangeInfoSchema pointExchangeIF) {

		int isTrue = 0;
		// 库存状态
		if (!pointExchangeIF.fill()) {
			Response.setStatus(0);
			Response.setMessage("查询库存信息失败，请重新选择！");
			return isTrue;
		}
		
		if (!"失败".equals(pointExchangeIF.getfuLuOrderStatus())) {
			Response.setStatus(0);
			Response.setMessage("福禄订单只有'失败'状态才可编辑！");
			return isTrue;
		}

		return ++isTrue;
	}
	
	/**
	 * 
	 * PossToFuLu:(saveExchangeGift 调用福禄接口进行二次充值). <br/>
	 *
	 * @author "guanyulong"
	 */
	public void PossToFuLu(PointExchangeInfoSchema pointExchangeIF ){
		String ordersn=pointExchangeIF.getprop3().toString();
		String channelSn="wj_jfsc";
		if(StringUtil.isNotEmpty(pointExchangeIF.getprop2())){
			channelSn=pointExchangeIF.getprop2();
		}
		// 调用福禄接口 TODO 
		Map<String, String> param = new HashMap<String, String>();
		param.put("channelSn", channelSn);
		param.put("presentId",  pointExchangeIF.getgiftClassifyID());
		param.put("orderSn",  ordersn); 
		param.put("memberId", pointExchangeIF.getmemberid());
		param.put("rechargeNo", pointExchangeIF.getmobileNo());
	    QueueSender queueSender =new QueueSender();
	    queueSender.sendToPointBack(JSONObject.toJSONString(param));
		UserLog.log("EXGiftManage", "PossToFuLu", "福禄二充:ordersn= "+ordersn, Request.getClientIP(),User.getUserName(),null,null);
	}

	public void updateFuluOrderStatus() {
		String orderSn = Request.getString("orderSn");
		if (StringUtil.isEmpty(orderSn)) {
			Response.setLogInfo(0, "订单号不能为空！");
			return;
		}
		ExchangeGoodsServiceImpl mExchangeGoodsService = new ExchangeGoodsServiceImpl();
		Map<String, Object> result = mExchangeGoodsService.orderSearch(orderSn);
		if ("success".equals(result.get("status"))) {
			Response.setLogInfo(1, "更新福禄订单状态成功");
			
		} else {
			Response.setLogInfo(0, (String)result.get("message"));
		}
		
		
	}
	
}