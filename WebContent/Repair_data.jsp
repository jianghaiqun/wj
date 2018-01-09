

<%@page import="com.sun.org.apache.xalan.internal.xsltc.compiler.sym"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.framework.data.DataTable"%>
<%@page import="cn.com.sinosoft.entity.CouponInfo"%>
<%@page import="cn.com.sinosoft.entity.SDInformation"%>
<%@page import="cn.com.sinosoft.entity.SDInformationRiskType"%>
<%@page import="cn.com.sinosoft.entity.SDOrder"%>
<%@page import="cn.com.sinosoft.entity.TradeInfo"%>
<%@page import="cn.com.sinosoft.service.CouponInfoService"%>
<%@page import="cn.com.sinosoft.service.SDInformationService"%>
<%@page import="cn.com.sinosoft.service.SDOrderService"%>
<%@page import="cn.com.sinosoft.service.TradeInfoService"%>

<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%@page import="com.sinosoft.framework.data.DataTable"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.framework.data.Transaction"%>
<%@page import="com.sinosoft.framework.utility.LogUtil"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>

<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%


System.out.println("开始执行");
out.print("开始执行");
class A{
	/**
	 * 
	* @Title: checkActivity 
	* @Description: TODO(购物车每个订单校验活动) 
	* @return boolean    返回类型 
	* @author zhangjing
	 */
	 public boolean checkActivity(String orderid, String CouponSn) {
			QueryBuilder qb_productid = new QueryBuilder("SELECT productid FROM  sdinformation WHERE ordersn=?",orderid);
			DataTable dt_productid=qb_productid.executeDataTable();
			String productId = dt_productid.getString(0,0);
			QueryBuilder qb = new QueryBuilder(
					"SELECT id FROM sdcouponactivityinfo ac WHERE ((FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0 AND ac.insurancecompany = '') OR (FIND_IN_SET(?,ac.insurancecompany)!=0 AND ac.riskcode = '') OR (ac.riskcode='' and   ac.insurancecompany='' and ac.product='' ) OR ( ( FIND_IN_SET(?, ac.insurancecompany) != 0 ) AND ( FIND_IN_SET( (SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode = ?), ac.riskcode ) != 0 ) ) OR(   FIND_IN_SET(?, ac.product) != 0  )  )   AND  ((SELECT COUNT(id) FROM couponinfo cou WHERE cou.activitysn=ac.activitysn AND cou.STATUS='0' AND ac.autocreate='1' AND ac.type != '3' ) !=0  OR ( ac.autocreate='0' AND ac.type != '3' )  OR ac.type = '3' ) and  activitysn=?");
			qb.add(productId);
			qb.add(productId.substring(0, 4));
			qb.add(productId.substring(0, 4));
			qb.add(productId);
			qb.add(productId);
			qb.add(CouponSn);
			DataTable dt = qb.executeDataTable();
			if (dt.getRowCount() > 0) {
				return true;
			} else {
				return false;
			}
		}	
}
class B{
	/**
	 * 
	 * @Title: checkCoupon
	 * @Description: TODO(购物车每个订单校验是否使用优惠券)
	 * @return boolean 返回类型
	 * @author 
	 */
	
	 public boolean checkCoupon(String orderid, String CouponSn) {
			QueryBuilder qb_coupon = new QueryBuilder("select direction,parvalue from  couponinfo where couponSn=? ", CouponSn.trim());
			DataTable dt_coupon = qb_coupon.executeDataTable();
			//优惠券的优惠金额赋值
			if (dt_coupon.getRowCount() > 0) {
				return true;
			} else {
				return false;
			}
		}
	
}
class C {
	
	/**
	 * 优惠金额进行分单（按订单、保单）
	 * 
	 * @param couOrderList
	 *            参加优惠的订单
	 * @param sumPrice
	 *            参加优惠的订单总金额
	 * @param couponPrice
	 *            优惠金额
	 */
	private Map<String, BigDecimal> separateCoupon(List<SDOrder> couOrderList,
			BigDecimal sumPrice, BigDecimal couponPrice) {
		// 记录订单优惠后的金额
		Map<String, BigDecimal> orderCouMap = new HashMap<String, BigDecimal>();
		// 订单优惠金额
		BigDecimal orderCou = new BigDecimal(0);
		// 订单优惠金额和
		BigDecimal sumOrderCou = new BigDecimal(0);
		Transaction tran = new Transaction();
		String updateSql = "update sdorders set orderCoupon = ? where id = ?";
		QueryBuilder updateQb = new QueryBuilder(updateSql);
		for (int i = 0; i < couOrderList.size(); i++) {
			updateQb = new QueryBuilder(updateSql);
			if (i == couOrderList.size() - 1) {
				// 最后一单用总优惠金额-前所有订单优惠金额和
				orderCou = couponPrice.subtract(sumOrderCou);
			} else {
				// 每单优惠金额 = 总优惠金额*订单金额/参加优惠的订单总金额
				orderCou = couponPrice.multiply(
						couOrderList.get(i).getTotalAmount()).divide(sumPrice,
						2, BigDecimal.ROUND_HALF_DOWN);
				// 累计订单优惠金额
				sumOrderCou = sumOrderCou.add(orderCou);
			}
			// 更新订单优惠金额
			updateQb.add(orderCou.toString());
			updateQb.add(couOrderList.get(i).getId());
			tran.add(updateQb);
			// 记录订单优惠后的金额 = 订单金额-优惠金额
			orderCouMap.put(couOrderList.get(i).getOrderSn(),
					couOrderList.get(i).getTotalAmount().subtract(orderCou));

			// 保单个数
			String sql00 = "SELECT * FROM sdinformationrisktype WHERE  ordersn =?   ";
			QueryBuilder qb00  = new QueryBuilder(sql00);
			qb00.add(couOrderList.get(i).getOrderSn());
			DataTable  dt00 =qb00.executeDataTable();
			int size = dt00.getRowCount();
			int riskTypeCount = size;
			// 保单优惠金额
			BigDecimal couValue = new BigDecimal(0);
			// 保单优惠金额和
			BigDecimal sumCouValue = new BigDecimal(0);
			for (int m =0; m<dt00.getRowCount(); m++) {
				QueryBuilder qb = new QueryBuilder(
						"update sdinformationrisktype "
								+ "set couponValue = ? where id = ?");
				if (riskTypeCount == 1) {
					// 最后一个保单优惠金额 = 订单优惠金额-之前保单的总优惠金额
					qb.add(orderCou.subtract(sumCouValue).toString());
				} else {
					// 保单优惠金额 = 订单优惠金额*保单金额/订单总金额
					couValue = orderCou.multiply(
							new BigDecimal(dt00.getString(m, "timePrem"))).divide(
							couOrderList.get(i).getTotalAmount(), 2,
							BigDecimal.ROUND_HALF_DOWN);
					// 累计保单优惠金额
					sumCouValue = sumCouValue.add(couValue);
					qb.add(couValue.toString());
				}
				qb.add(dt00.getString(m, "id"));
				tran.add(qb);
				riskTypeCount--;
			}
		}
		tran.commit();
		return orderCouMap;
	}
}

class D {
	
	/**
	 * 积分抵值进行分单（按订单、保单）
	 * 
	 * @param orderList
	 *            所有订单集合
	 * @param totleAmount
	 *            订单优惠后总金额
	 * @param offsetPoint
	 *            总积分抵值金额
	 */
	private void separatePoint(List<SDOrder> orderList, BigDecimal totleAmount,
			BigDecimal offsetPoint, Map<String, BigDecimal> orderCouMap) {
		// 订单积分抵值金额
		BigDecimal orderInte = new BigDecimal(0);
		// 订单积分抵值金额和
		BigDecimal sumOrderInte = new BigDecimal(0);
		Transaction tran = new Transaction();
		String updateSql = "update sdorders set orderIntegral = ? where id = ?";
		QueryBuilder updateQb = new QueryBuilder(updateSql);
		for (int i = 0; i < orderList.size(); i++) {
			updateQb = new QueryBuilder(updateSql);
			if (i == orderList.size() - 1) {
				// 最后一单用总积分抵值金额-前所有订单积分抵值金额和
				orderInte = offsetPoint.subtract(sumOrderInte);
			} else {
				BigDecimal ordAmout = orderList.get(i).getTotalAmount();
				if (orderCouMap.containsKey(orderList.get(i).getOrderSn())) {
					ordAmout = orderCouMap.get(orderList.get(i).getOrderSn());
				}

				// 每单积分抵值金额 = 总积分抵值金额*订单优惠后金额/订单优惠后总金额
				orderInte = offsetPoint.multiply(ordAmout).divide(totleAmount,
						2, BigDecimal.ROUND_HALF_DOWN);
				// 累计订单积分抵值金额
				sumOrderInte = sumOrderInte.add(orderInte);
			}
			// 更新订单积分抵值金额
			updateQb.add(orderInte.toString());
			updateQb.add(orderList.get(i).getId());
			tran.add(updateQb);

			// 保单个数
				String sql00 = "SELECT * FROM sdinformationrisktype WHERE  ordersn =?   ";
			QueryBuilder qb00  = new QueryBuilder(sql00);
			qb00.add(orderList.get(i).getOrderSn());
			DataTable  dt00 =qb00.executeDataTable();
			int size = dt00.getRowCount();
			int riskTypeCount = size;
		
			// 保单积分抵值金额
			BigDecimal inteValue = new BigDecimal(0);
			// 保单积分抵值金额和
			BigDecimal sumInteValue = new BigDecimal(0);			
			for (int m =0; m<dt00.getRowCount(); m++) {
				QueryBuilder qb = new QueryBuilder(
						"update sdinformationrisktype "
								+ "set integralValue = ? where id = ?");
				if (riskTypeCount == 1) {
					// 最后一个保单积分抵值金额 = 订单积分抵值金额-之前保单的总积分抵值金额
					qb.add(orderInte.subtract(sumInteValue)
							.toString());
				} else {
					// 保单积分抵值金额 = 订单积分抵值金额*保单金额/订单金额
					inteValue = orderInte.multiply(
							new BigDecimal(dt00.getString(m, "timePrem"))).divide(
							orderList.get(i).getTotalAmount(), 2,
							BigDecimal.ROUND_HALF_DOWN);
					sumInteValue = sumInteValue.add(inteValue);
					qb.add(inteValue.toString());
				}
				qb.add(dt00.getString(m, "Id"));
				tran.add(qb);
				riskTypeCount--;
			}
		}

		tran.commit();
	}
}

String sql = "SELECT * FROM sdorders WHERE  paySn IS NOT NULL  AND  ((couponSn !='' AND couponSn!='0' ) OR (offsetPoint !='' AND offsetPoint!='0')) AND (orderStatus='7' OR orderStatus='9'  OR orderStatus='10') and createdate <'2014-07-04' GROUP  BY paySn   ";
QueryBuilder qb  = new QueryBuilder(sql);
DataTable  dt4 =qb.executeDataTable();

if (dt4 != null && dt4.getRowCount() != 0) {
	
 for(int m=0;m < dt4.getRowCount(); m++){
		
	String  paysnValue=dt4.getString(m, "paySn");
	BigDecimal totleAmount = new BigDecimal("0.0");
	BigDecimal payPrice_mj = new BigDecimal("0.0");
	List<SDOrder> couOrderList = new ArrayList<SDOrder>();
	String sql1 = "SELECT * FROM sdorders WHERE paySn="+"'"+paysnValue+"'";
	System.out.println("优惠历史数据补录paySn:"+paysnValue);
	QueryBuilder qb1  = new QueryBuilder(sql1);
	DataTable  dt1 =qb1.executeDataTable();
	List<SDOrder> sdorderList = new ArrayList<SDOrder>();
			if (dt1 != null && dt1.getRowCount() != 0) {
				String strOrderSns = "";
				for (int j = 0; j < dt1.getRowCount(); j++) {
					String paySn = dt1.getString(j, "paySn");
					String payType=dt1.getString(j, "payType");
				
					SDOrder  sorder1= new SDOrder();
					sorder1.setId(dt1.getString(j, "id"));
					sorder1.setCouponSn(dt1.getString(j, "couponSn"));
					sorder1.setTotalAmount( new  BigDecimal(dt1.getString(j, "totalAmount")));
					sorder1.setOrderSn(dt1.getString(j, "orderSn"));
					sdorderList.add(sorder1);
							strOrderSns += "'"+dt1.getString(j, "orderSn").trim()+"',";
							totleAmount = totleAmount.add( new BigDecimal(dt1.getString(j, "totalAmount")));
							if (StringUtil.isNotEmpty(dt1.getString(j, "couponSn"))) {
								SDOrder sorder = new SDOrder();
								if (dt1.getString(j, "couponSn").startsWith("HD")) {
									//累计符合条件的满减总额
// 									String sql2 = "select productId from SDInformation where ordersn=?";
// 									DataTable dt2 = new QueryBuilder(sql2,dt1.getString(j, "orderSn")).executeDataTable();
// 									String productId = dt2.getString(0, 0);
									A  a = new A(); 
									boolean flag = a.checkActivity(dt1.getString(j, "orderSn"),dt1.getString(j, "couponSn"));
									if(flag){
										payPrice_mj = payPrice_mj.add(  new BigDecimal(dt1.getString(j, "totalAmount")));
										sorder.setId(dt1.getString(j, "id"));
										sorder.setCouponSn(dt1.getString(j, "couponSn"));
										sorder.setTotalAmount( new  BigDecimal(dt1.getString(j, "totalAmount")));
										sorder.setOrderSn(dt1.getString(j, "orderSn"));
										couOrderList.add(sorder);
									}	
								} else {
									B b = new B();
									boolean flag = b.checkCoupon(dt1.getString(j, "orderSn").trim(),
											dt1.getString(j, "couponSn"));
									if (flag) {
										payPrice_mj = payPrice_mj.add( new BigDecimal(dt1.getString(j, "totalAmount")));
										sorder.setId(dt1.getString(j, "id"));
										sorder.setCouponSn(dt1.getString(j, "couponSn"));
										sorder.setTotalAmount( new  BigDecimal(dt1.getString(j, "totalAmount")));
										sorder.setOrderSn(dt1.getString(j, "orderSn"));
										couOrderList.add(sorder);
									}
								}
							}
					}
				String payType=" ";
			try{
				String sql2 = "SELECT * FROM tradeinformation WHERE ordid=?";
				DataTable dt2 = new QueryBuilder(sql2,dt4.getString(m, "orderSn").trim()).executeDataTable();
				 payType = dt2.getString(0, 0);
			}catch(Exception e){
				System.out.println(dt4.getString(m, "Pysn")+":"+ "更新失败");
			}
				
						strOrderSns = strOrderSns.substring(0, strOrderSns.length() - 1);
						BigDecimal offsetPoint=new BigDecimal("0");
						if(dt4.getString(m, "offsetPoint")!=null&&!("0".equals(String.valueOf(dt4.getString(m, "offsetPoint"))))&& (!"".equals(String.valueOf(dt4.getString(m, "offsetPoint"))))){
							offsetPoint = new BigDecimal(dt4.getString(m, "offsetPoint"))
									.divide(new BigDecimal("200"), 1,
											BigDecimal.ROUND_DOWN);
						}
						String sql5 = "";
						QueryBuilder qb5  = null;
						DataTable  dt5 =null;
						String sdorderCouponsnValue="";
						if (dt4.getString(m, "paySn") != null
								&& (dt4.getString(m, "paySn").endsWith("D") || dt4.getString(m, "paySn").endsWith("G")
										|| dt4.getString(m, "paySn").endsWith("00") || dt4.getString(m, "paySn").endsWith("01"))) {
							
							// 银联支付的情况
							if ("ylzf".startsWith(payType)) {
								// 记录优惠的字段不为空的情况
								if (StringUtil.isNotEmpty(dt4.getString(m, "couponSn"))) {
									// 参加活动的情况取得活动优惠金额
									if (dt4.getString(m, "couponSn").startsWith("HD")) {
										QueryBuilder qb_activity=new QueryBuilder("select prop1,accumulation,payamount from sdcouponactivityinfo where activitysn=?",dt4.getString(m, "couponSn"));
										DataTable dt=qb_activity.executeDataTable();
										if(dt.getRowCount()==1){
											//查询梯度价格
											QueryBuilder qb_payamount=new QueryBuilder("select payamount,prop1,id from SDActivityPayamount where activitysn=?  order by  CONVERT(payamount,DECIMAL)  desc ",dt4.getString(m, "couponSn"));
											DataTable dt_payamount=qb_payamount.executeDataTable();
											BigDecimal payamount=new BigDecimal("1");
											BigDecimal prop1=new  BigDecimal("1");
											//无梯度价格
											if(dt_payamount.getRowCount()==1){
												payamount=new BigDecimal(dt_payamount.getString(0, 0));
												prop1=new BigDecimal(dt_payamount.getString(0, 1));
											}else{
												//校验梯度价格
												for(int i=0;i<dt_payamount.getRowCount();i++){
													BigDecimal payamou=new BigDecimal(dt_payamount.getString(i,0));
													if(payPrice_mj.compareTo(payamou)!=-1){
														payamount=payamou;
														QueryBuilder qb_prop1=new QueryBuilder("select prop1 from SDActivityPayamount where id=?",dt_payamount.getString(i,2));
														prop1=new BigDecimal(qb_prop1.executeDataTable().getString(0,0));
														break;
													}
												}
											}
											int num=1;
											//可累计的情况获得可累计次数
											if("0".equals(dt.getString(0, 1))){
												num=payPrice_mj.divide(payamount,0,BigDecimal.ROUND_DOWN).intValue();
											}
											if(num==0){
												num=1;
											}
											sdorderCouponsnValue=String.valueOf(prop1.multiply(new BigDecimal(num)));
											if(sdorderCouponsnValue==null||"null".equals(sdorderCouponsnValue)){
												sdorderCouponsnValue="";
											}
										}
										// 使用优惠券的情况 取得使用的优惠券信息
									} else {
										 sql5 = "SELECT ParValue FROM CouponInfo WHERE couponSn="+"'"+dt4.getString(m, "couponSn").trim()+"'";
										 qb5  = new QueryBuilder(sql5);
										 dt5 =qb5.executeDataTable();
									}
								}

							} else {
								 sql5 = "SELECT * FROM CouponInfo WHERE couponSn="+"'"+dt4.getString(m, "couponSn").trim()+"'";
								 qb5  = new QueryBuilder(sql1);
								  dt5 =qb5.executeDataTable();
								
								if (dt5 != null && dt5.getRowCount() != 0) {

									QueryBuilder qb_activity=new QueryBuilder("select prop1,accumulation,payamount from  sdcouponactivityinfo where activitysn=?",dt4.getString(m, "couponSn"));
									DataTable dt=qb_activity.executeDataTable();
									if(dt.getRowCount()==1){
										//查询梯度价格
										QueryBuilder qb_payamount=new QueryBuilder("select payamount,prop1,id from SDActivityPayamount where activitysn=?  order by  CONVERT(payamount,DECIMAL)  desc ",dt4.getString(m, "couponSn"));
										DataTable dt_payamount=qb_payamount.executeDataTable();
										BigDecimal payamount=new BigDecimal("1");
										BigDecimal prop1=new  BigDecimal("1");
										//无梯度价格
										if(dt_payamount.getRowCount()==1){
											payamount=new BigDecimal(dt_payamount.getString(0, 0));
											prop1=new BigDecimal(dt_payamount.getString(0, 1));
										}else{
											//校验梯度价格
											for(int i=0;i<dt_payamount.getRowCount();i++){
												BigDecimal payamou=new BigDecimal(dt_payamount.getString(i,0));
												if(payPrice_mj.compareTo(payamou)!=-1){
													payamount=payamou;
													QueryBuilder qb_prop1=new QueryBuilder("select prop1 from SDActivityPayamount where id=?",dt_payamount.getString(i,2));
													prop1=new BigDecimal(qb_prop1.executeDataTable().getString(0,0));
													break;
												}
											}
										}
										int num=1;
										//可累计的情况获得可累计次数
										if("0".equals(dt.getString(0, 1))){
											num=payPrice_mj.divide(payamount,0,BigDecimal.ROUND_DOWN).intValue();
										}
										sdorderCouponsnValue=String.valueOf(prop1.multiply(new BigDecimal(num)));
										if(sdorderCouponsnValue==null||"null".equals(sdorderCouponsnValue)){
											sdorderCouponsnValue="";
										}
									}
								}
							}
						} 
						Transaction tran = new Transaction();
						String updateSql = "update sdorders set ";
						// 记录更新项
						String update = "";
						Map<String, BigDecimal> orderCouMap = new HashMap<String, BigDecimal>();
						BigDecimal couponPrice = new BigDecimal(0);
						// 使用优惠的情况 优惠金额进行分单
						if (couOrderList.size() > 0) {
							if (StringUtil.isNotEmpty(sdorderCouponsnValue)) {
								couponPrice = new BigDecimal(sdorderCouponsnValue);
							} else {
								
 								 sql5 = "SELECT ParValue FROM CouponInfo WHERE couponSn="+"'"+dt4.getString(m, "couponSn").trim()+"'";
 								 qb5  = new QueryBuilder(sql5);
								 dt5 =qb5.executeDataTable();
								couponPrice = new BigDecimal(dt5.getString(0,0));
							}
							update = "sumCoupon = '" + couponPrice + "' ";
							// 优惠金额按比例进行分单
							C c =new C();
							orderCouMap = c.separateCoupon(couOrderList, payPrice_mj,
									couponPrice);
						}

						// 使用积分的情况 积分抵值进行分单
						if (offsetPoint.compareTo(new BigDecimal("0")) == 1) {
							if (!"".equals(update)) {
								update += ", ";
							}
							update += "sumIntegral = '" + offsetPoint + "' ";
							D d =new D();
							d.separatePoint(sdorderList, totleAmount.subtract(couponPrice),
									offsetPoint, orderCouMap);
						}
						
						// 存在更新项的时候执行
						if (!"".equals(update)) {
							tran.add(new QueryBuilder(updateSql + update
									+ " where orderSn in (" + strOrderSns + ")"));
							tran.commit();
						}
					out.println("success:"+paysnValue);
				}
			}
		}


%>
