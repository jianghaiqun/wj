package com.sinosoft.orders;

import cn.com.sinosoft.service.CpsProductBuyService;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.HttpClientUtil;

import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.cms.dataservice.ZjfaePayManage;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.CancelContService;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.schema.CancelContRefundSchema;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.SDIntCalendarSet;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.SDOrdersSet;
import com.sinosoft.schema.memberSchema;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author周翔
 * @Date 2013-3-27
 * @Mail zhouxiang@sinosoft.com.cn
 */
public class CancelCont extends Page {

	// 淘宝商家流水号 
	private String comId = "";

	public static Mapx init(Mapx params) {
		String mxbChannelSns = new QueryBuilder( " SELECT GROUP_CONCAT(ChannelCode) FROM ChannelInfo WHERE ParentInnerChanelCode='00006'" ).executeString();
		params.put("createDate", com.sinosoft.lis.pubfun.PubFun.getPrevMonthDay(getFormat("yyyy-MM-dd", new Date())));
		params.put("endCreateDate", getFormat("yyyy-MM-dd", new Date()));
		params.put("mxbChannelSns", mxbChannelSns);
		return params;
	}

	private static String getFormat(String format, Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	@SuppressWarnings("finally")
	public void dealCancel() {
		String surrenderData = this.Request.getString("surrenderData");
		boolean surrenderImport = false;
		if(null != surrenderData){
			surrenderImport = true;
		}
		String ids = $V("IDs");
		if(surrenderImport){
			ids = surrenderData;
		}
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		String[] id = ids.split(",");
		int n = id.length;
		String returnIntegral = "";
		if (n == 1) {
			for (int i = 0; i < n; i++) {
				String channelsn = "";
				String memberId = "";
				String productId = "";
				String insuredSn = id[i];
				String comCode = "";
				String appStatus = "";
				String ordersn = "";
				String riskTypeId = "";
				String balanceStatus = "";
				String flag = "";
				String  sdate = "";
				String  edate = "";
				String  orderstatus = "";
				
				String sql = "select a.insuranceCompany , c.appStatus ,a.ordersn ,c.id,c.balanceStatus ,d.channelsn ,d.memberId ,a.productId,a.startdate,a.enddate,d.orderstatus "
						+ "from SDInformation a, sdorders d, sdinformationinsured b ,sdinformationrisktype c "
						+ "where a.informationSn=b.informationSn and b.recognizeeSn = c.recognizeeSn and a.orderSn = d.orderSn and b.insuredSn=?";
				DataTable dt = new QueryBuilder(sql, insuredSn).executeDataTable();
				if (dt.getRowCount() == 1) {
					comCode = dt.getString(0, 0);
					appStatus = dt.getString(0, 1);
					ordersn = dt.getString(0, 2);
					riskTypeId = dt.getString(0, 3);
					balanceStatus = dt.getString(0, 4);
					channelsn = dt.getString(0, 5);
					memberId = dt.getString(0, 6);
					productId = dt.getString(0, 7);
					sdate = dt.getString(0, 8);
					edate = dt.getString(0, 9);
					orderstatus = dt.getString(0, 10);
				}
				String special_pro=Config.getValue("KL_SURRENDER_PRO");//昆仑支持未承保撤单的产品zdconfig中配置
				if(StringUtil.isEmpty(special_pro)){
					special_pro="";
				}
				if ((!"0".equals(balanceStatus) && "1".equals(appStatus))&&!special_pro.contains(productId)) {
					Response.setLogInfo(0, "此订单未结算，不能撤单！");
					return;
				}
				if("0007".equals(comCode)){
					Response.setLogInfo(0, "中国太平不支持在线撤单！");
					return;
				}
				if("2252".equals(comCode)){
					Response.setLogInfo(0, "国寿财险不支持在线撤单！");
					return;
				}
				/**首先判断是昆仑健康保产品且
				 * 订单
				 * 未支付 orderstatus=4 or orderstatus=5
				 * 未计算  balanceStatus!=0
				 * 未承保  appStatus!=1*/
				if(special_pro.contains(productId)&&!"0".equals(balanceStatus) && !"1".equals(appStatus)&&("4".equals(orderstatus)||"5".equals(orderstatus))){
					CancelContService tCancelContService = new CancelContService();
					flag = tCancelContService.callInsTransInterface(comCode, ordersn, insuredSn, riskTypeId);
					if("SUCCESS".equals(flag)){
						Response.setLogInfo(0, "昆仑未承保订单撤单成功！");
					}else{
						String errorMsg="";
						if(flag.indexOf("|")!=-1){
							errorMsg = flag.substring(flag.indexOf("|")+1);
						}
						Response.setLogInfo(0, "昆仑未承保订单撤单失败，失败原因："+errorMsg);
					}
					return;
				}else if ((surrenderImport || "1".equals(appStatus) || "3".equals(appStatus)) && StringUtil.isNotEmpty(insuredSn)
						&& StringUtil.isNotEmpty(comCode)) {
					CancelContService tCancelContService = new CancelContService();
					if(!surrenderImport){
						flag = tCancelContService.callInsTransInterface(comCode,ordersn, insuredSn, riskTypeId);
					}
					// 保险公司撤单成功后 更新系统数据状态
					if (surrenderImport || "SUCCESS".equals(flag)) {
						// 更新保单表的撤单信息
						SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
						sdRiskTypeSchema.setId(riskTypeId);
						if (sdRiskTypeSchema.fill()) {
							sdRiskTypeSchema.setappStatus("4");// 撤单成功后状态更新为2
							sdRiskTypeSchema.setcancelDate(new Date());
							sdRiskTypeSchema.update();
						}
						// 更新订单表的订单状态
						tCancelContService.changeSDOrders(ordersn);
						/*
						 * 与保险公司撤单成功后查找是否使用了积分抵值，分为购物车和非购物车方式
						 * 1、非购物车，该订单的所有保单全部撤消后才可以返还抵值积分
						 * 2、购物车，该购物车的所有订单的所有保单全部撤消后才可以返还抵值积分
						 */
						returnIntegral = this.returnPoint(sdRiskTypeSchema);
						// 冻结积分清零
						this.calculatePointToZero(sdRiskTypeSchema);
						if (Constant.B2B_APP.equals(channelsn) || Constant.B2B_HTML5.equals(channelsn)) {
							this.dealB2bBalanceInfo(channelsn, memberId, ordersn, sdRiskTypeSchema.getcancelDate(),
									productId, "-"+sdRiskTypeSchema.getpayPrice(), sdRiskTypeSchema.getrecognizeeSn(),sdate,edate);
						}

						try {
							//浙金支付退款
							if (Constant.CHANNELSN_ZJFAE.equals(channelsn)) {
								ZjfaePayManage z = new ZjfaePayManage();
								z.zjfaeRefund(sdRiskTypeSchema.getrecognizeeSn(), channelsn);
							}
						} catch (Exception e1) {
							logger.error("浙金支付退款异常，" + e1.getMessage(), e1);
						}
					}

				} else if ("2".equals(appStatus)) {
					Response.setLogInfo(0, "此订单已经撤单！");
					return;
					// 网金结算中心撤单失败时，可以重新撤单
				} else if ("4".equals(appStatus)) {
					flag = "SUCCESS";
					// 取得撤单回退积分数
					String Integral = new QueryBuilder(
							"select Integral from SDIntCalendar where prop1 = ? and Source = ?",
							riskTypeId,
							IntegralConstant.POINT_SOURCE_OFFSET_POINT)
							.executeString();
					if (StringUtil.isNotEmpty(Integral)) {
						BigDecimal PointScalerUnit = new BigDecimal(
								Config.getConfigValue("PointScalerUnit"));
						returnIntegral = new BigDecimal(Integral).divide(
								PointScalerUnit, 2, BigDecimal.ROUND_HALF_UP)
								.toString();
					}

				} else {
					Response.setLogInfo(0, "订单有误！");
					return;
				}
				if (surrenderImport || "SUCCESS".equals(flag)) {
					if (StringUtil.isNotEmpty(riskTypeId)) {
						CancelContService tCancelContService = new CancelContService();
						SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
						SDInformationRiskTypeSet sdRiskTypeSet = new SDInformationRiskTypeSet();
						sdRiskTypeSet = sdRiskTypeSchema.query(new QueryBuilder("where ID = '" + riskTypeId + "'"));
						if (!sdRiskTypeSet.isEmpty() && sdRiskTypeSet.size() == 1) {
							sdRiskTypeSchema = sdRiskTypeSet.get(0);
							if ("4".equals(sdRiskTypeSchema.getappStatus()) && !"2".equals(sdRiskTypeSchema.getbalanceStatus())) {
								sdRiskTypeSchema.setbalanceStatus("2");
								sdRiskTypeSchema.update();
								// 表示与保险公司对接退保成功
								String returnActivity = "";
								if (tCancelContService.callProductInterface(ordersn, "", PubFun.getCurrentDate(), "02",
										sdRiskTypeSchema.getpolicyNo(), riskTypeId, returnIntegral, returnActivity)) {
									try {
										cancelCpsOrder(ordersn);
									} catch (MalformedURLException e) {
										logger.error(e.getMessage(), e);
									} finally {
										Response.setLogInfo(1, "撤单成功！");
										//TODO
										//快钱渠道撤单调用接口
										if (channelsn.equals(Constant.CHANNELSN_BILL_KQ)) {
											try {
												// 发送消息队列到消费者
												Map<String, String> dataSendMap = new HashMap<String, String>();
												dataSendMap.put("type", "2");
												dataSendMap.put("orderSn", ordersn);
												dataSendMap.put("operator", "dealBillCallback");
												ProducerMQ mq = new ProducerMQ();
												mq.sendToCallBackPolicy(JSON.toJSONString(dataSendMap));

											} catch (Exception e) {
												logger.error(e.getMessage(), e);
											}
										}

										// 插入撤单退费表数据
										CancelContRefundSchema cancelContRefundSchema = new CancelContRefundSchema();
										cancelContRefundSchema.setId(CommonUtil.getUUID());
										cancelContRefundSchema.setOrderSn(ordersn);
										cancelContRefundSchema.setPolicyNo(sdRiskTypeSchema.getpolicyNo());

										// 设置状态，0：待审核；1：已审核；2：已退款；
										cancelContRefundSchema.setStatus("0");
										// 设置撤单来源：0：会员前端撤单；1：CMS后台撤单；
										cancelContRefundSchema.setCancelFrom("1");
										// 设置保费与实际支付保费
										cancelContRefundSchema.setPrem(sdRiskTypeSchema.gettimePrem());
										cancelContRefundSchema.setPayPrem(sdRiskTypeSchema.getpayPrice());
										Date currentDate = new Date();
										cancelContRefundSchema.setRepealDate(currentDate);
										cancelContRefundSchema.setCreateDate(currentDate);
										cancelContRefundSchema.setModifyDate(currentDate);
										cancelContRefundSchema.insert();

										return;
									}
								} else {
									Response.setLogInfo(0, "撤单结算中心调用失败！");
									return;
								}
							}
						}
					} else {
						Response.setLogInfo(0, "订单有误！");
						return;
					}
				} else if ("NOCONFIG".equals(flag)) {
					Response.setLogInfo(0, "此公司的订单未开放撤单功能！请联系技术人员。");
					return;
				} else {
					String errorMsg = "撤单经代通调用失败！";
					if(flag.indexOf("|")!=-1){
						errorMsg = flag.substring(flag.indexOf("|")+1);
					}
					Response.setLogInfo(0, errorMsg);
					return;
				}
			}

		} else {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
	}

	/**
	 * 
	 * dealB2bBalanceInfo:(推送b2b结算数据). <br/>
	 *
	 * @author zhangjing
	 * @param ordersn
	 * @param productid
	 * @param memberid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void dealB2bBalanceInfo(String channelsn, String userName, String orderSn, Date createDate, String productId,
			String payPriceStr, String policyNoStr,String startDate,String endDate) {
		Map<String, Object> param = null;
		boolean isSave = false;
		try {
			// 用户信息
			param = new HashMap<String, Object>();
			param.put("channelsn", channelsn);
			param.put("userName", userName);
			param.put("orderSn", orderSn);
			param.put("createDate", DateUtil.toDateTimeString(createDate));
			param.put("productId", productId);
			param.put("payPriceStr", payPriceStr);
			param.put("policyNoStr", policyNoStr);
			param.put("startDate", "");
			param.put("endDate", "");
			String url = Config.interfaceMap.getString("balance_detail_interface");
			// String url
			// ="http://localhost:8082/b2b/rest/balanceDetail/doBalanceDetail";
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			String httpOrgCreateTestRtn = httpClientUtil.doPost(url, param);
			Map<String, Object> checkResult = httpClientUtil.parserToMap(httpOrgCreateTestRtn);
			//发生异常信息保存BalanceDetail
			if ("1".equals(checkResult.get("status"))) {
				String msg = String.valueOf( checkResult.get("msg"));
				logger.error("dealB2bBalanceInfo接口出现异常========={}", orderSn+"_"+msg);
				 isSave = true;
			}
		} catch (Exception e) {
			 isSave = true;
			logger.error("类CancelCont执行方法dealB2bBalanceInfo()发生异常！" + e.getMessage(), e);
		}finally{
			if(isSave){
				try {
					saveBalanceDetail(param);
				} catch (Exception e1) {
					logger.error("通知FrontTrade的发送推广费详细信息失败！"+ orderSn+"_" + e1.getMessage(), e1);
				}
			}
		}
	}

	/**
	 * 
	 * saveBalanceDetail:(保存没有传到b2b的推广费详细). <br/>
	 *
	 * @author chouweigao
	 * @throws Exception 
	 */
	private void saveBalanceDetail(Map<String, Object> param ) throws Exception{
		
		if (param == null) {
			return;
		}
		String channelsn=(String) param.get("channelsn");
		String userName=(String) param.get("userName");
		String orderSn=(String) param.get("orderSn");
		String createDate=(String) param.get("createDate");
		String productId=(String) param.get("productId");
		String payPriceStr=(String) param.get("payPriceStr");
		String policyNoStr=(String) param.get("policyNoStr");
		String startDate=(String) param.get("startDate");
		String endDate=(String) param.get("endDate");
		StringBuffer sb = new StringBuffer();
		sb.append("	insert into SdBalanceDetail	");
		sb.append("	(	id,	");
		sb.append("	channelsn,	");
		sb.append("	userName,	");
		sb.append("	orderSn,	");
		sb.append("	createDate,	");
		sb.append("	productId,	");
		sb.append("	payPriceStr,	");
		sb.append("	policyNoStr	,");
		sb.append("	startDate	,");
		sb.append("	endDate	)");
		sb.append("	values  (	");
		sb.append("'" + CommonUtil.getUUID() + "',");
		sb.append("'" + channelsn + "',");
		sb.append("'" + userName + "',");
		sb.append("'" + orderSn + "',");
		sb.append("'" + createDate + "',");
		sb.append("'" + productId + "',");
		sb.append("'" + payPriceStr + "',");
		sb.append("'" + policyNoStr + "',");
		sb.append("'" + startDate + "',");
		sb.append("'" + endDate + "')");

		String sql = sb.toString();
		QueryBuilder qb = new QueryBuilder(sql);
		qb.executeNoQuery();
		
		String url = Config.interfaceMap.getString("balance_start_interface");
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		Map<String, Object> params = new HashMap<String, Object>();
		String res = httpClientUtil.doPost(url, params);
		Map<String, Object> checkResult = HttpClientUtil.parserToMap(res);
		//发生异常信息保存BalanceDetail
		if ("1".equals(checkResult.get("status"))) {
			String msg = String.valueOf( checkResult.get("msg"));
			logger.error("向FrontTrade发送启动接口出现异常========={}", msg);
		}
	}
	
	/**
	 * 淘宝SD专用撤单,先修改订单状态渠道，再撤单。
	 */
	public void tbsdDealCancel() {

		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		String[] id = ids.split(",");
		int n = id.length;
		if (n == 1) {
			String insuredSn = id[0];
			String sql = "SELECT ordersn FROM sdinformationinsured WHERE insuredSn=?";
			QueryBuilder qb = new QueryBuilder(sql, insuredSn);
			DataTable dt = qb.executeDataTable();
			if (dt.getRowCount() != 1) {
				Response.setLogInfo(0, "无法找到被保人对应的订单记录");
				return;
			} else {
				String ordersn = dt.getString(0, 0);
				// 将订单渠道置为淘宝SD
				if (!"tbsd".equals(dt.getString(0, 0))) {
					String sql1 = "UPDATE sdorders SET channelsn = 'tbsd' WHERE ordersn = ?";
					QueryBuilder qb1 = new QueryBuilder(sql1, ordersn);
					qb1.executeNoQuery();
				}
				dealCancel();
			}
		} else {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
	}

	/**
	 * 淘宝异常订单关闭
	 */
	public void tbOrderCancel() {

		Transaction tran = new Transaction();

		try {
			String ids = $V("IDs");
			if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
				Response.setLogInfo(0, "传入ID时发生错误");
				return;
			}
			String ordersn = "";
			String productId = "";
			String productType = "";
			String sql = "select a.ordersn, a.productId "
					+ "from SDInformation a, sdinformationinsured b ,sdinformationrisktype c "
					+ "where a.informationSn=b.informationSn and b.recognizeeSn = c.recognizeeSn and b.insuredSn=?";
			QueryBuilder qb = new QueryBuilder(sql, ids);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				ordersn = dt.getString(0, 0);
				productId = dt.getString(0, 1);
			}

			String xml = reponseResultTB(ordersn);
			// 取得淘宝产品类型
			sql = "select ProductType from jdproductc where ERiskID = ?";
			productType = new QueryBuilder(sql, productId).executeString();
			boolean result = taoBaoAsync(xml, "?comId=" + comId + "&productType=" + productType);

			if (result) {

				// 更新订单表状态
				tran.add(new QueryBuilder("update sdorders set orderStatus = '9',modifyDate = ? where orderSn = ?", DateUtil
						.getCurrentDateTime(), ordersn));
				// 更新保单表状态
				tran.add(new QueryBuilder(
						"update sdInformationRiskType set appStatus = '2',cancelDate = ? where orderSn = ?", DateUtil
								.getCurrentDateTime(), ordersn));
				tran.add(new QueryBuilder("update sdInformationRiskType set modifyDate = ? where orderSn = ?", DateUtil
						.getCurrentDateTime(), ordersn));

				if (!tran.commit()) {
					Response.setLogInfo(0, "更新订单状态失败");
					return;
				} else {
					Response.setLogInfo(0, "淘宝撤单成功");
					return;
				}
			} else {
				Response.setLogInfo(0, "连接淘宝错误，请尝试重新撤单");
				return;
			}
		} catch (Exception e) {
			logger.error("淘宝关单错误：" + e.getMessage(), e);
			Response.setLogInfo(0, "淘宝撤单失败");
			return;
		}
	}

	/**
	 * 淘宝关单回调
	 * 
	 * @param returnXml
	 *            请求报文
	 * @param param
	 *            GET参数
	 * @throws IOException
	 */
	public boolean taoBaoAsync(String returnXml, String param) throws IOException {

		HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		String url = Config.interfaceMap.getString("OrderCancelAccessServlet");
		PostMethod post = new PostMethod(url + param);
		boolean bl = false;
		InputStream in_tb = null;
		try {
			in_tb = new ByteArrayInputStream(returnXml.getBytes("GBK"));
			post.setRequestBody(in_tb);
			httpClient.executeMethod(post);

			String result = post.getResponseBodyAsString().trim();
			if ("success".equals(result)) {
				bl = true;
			}
		} catch (Exception e) {
			logger.error("淘宝关单错误：" + e.getMessage(), e);
		} finally {
			post.releaseConnection();
			if (in_tb != null) {
				in_tb.close();
			}
		}
		return bl;
	}

	/**
	 * 组装关单回调报文
	 * 
	 * @return 关单报文
	 * @throws Exception
	 */
	private String reponseResultTB(String orderSn) throws Exception {

		String currentDate = DateUtil.getCurrentDate();
		String currentTime = DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
		Element rootPackageList = new Element("PackageList");
		Element rootPackage = new Element("Package");
		Document doc = new Document(rootPackageList); // 要创建的XML文档 - doc

		// 取得唯一编码
		String sql = "select * from SDOrders where orderSn = ?";
		String sqltemp[] = { orderSn };
		List<HashMap<String, String>> list = new GetDBdata().query(sql, sqltemp);
		// 唯一编码
		String uuId = "";
		// 淘宝交易流水号
		String taoBaoSerial = "";
		// 总保费
		String totalPremium = "";
		if (list.size() > 0) {
			uuId = list.get(0).get("remark");
			comId = list.get(0).get("tbComCode");
			taoBaoSerial = list.get(0).get("tbTradeSeriNo");
			totalPremium = list.get(0).get("totalAmount");
		}

		// header 信息
		Element headerElement = new Element("Header");

		// 交易类型(必填)
		Element RequestTypeElement = new Element("RequestType");
		RequestTypeElement.setText("12");
		headerElement.addContent(RequestTypeElement);

		// 唯一编码(必填)
		Element UUIDElement = new Element("UUID");
		UUIDElement.setText(uuId);
		headerElement.addContent(UUIDElement);

		// 交互保险公司编码(必填)
		Element ComIdElement = new Element("ComId");
		ComIdElement.setText(comId);
		headerElement.addContent(ComIdElement);

		// 发送方编号(必填)
		Element FromElement = new Element("From");
		// FromElement.setText("taobao");
		FromElement.setText(comId);
		headerElement.addContent(FromElement);

		// 发送时间(必填)
		Element SendTimeElement = new Element("SendTime");
		SendTimeElement.setText(currentTime);
		headerElement.addContent(SendTimeElement);

		// 淘宝交易流水号(必填)
		Element TaoBaoSerialElement = new Element("TaoBaoSerial");
		TaoBaoSerialElement.setText(taoBaoSerial);
		headerElement.addContent(TaoBaoSerialElement);

		// 保险公司流水号(非必填)
		Element ComSerialElement = new Element("ComSerial");
		// ComSerialElement.setText(insured.getorderSn());
		headerElement.addContent(ComSerialElement);

		// end header

		// Callback 信息
		Element CallbackElement = new Element("Callback");

		// Policy
		Element PolicyElement = new Element("Policy");
		CallbackElement.addContent(PolicyElement);

		// 淘宝订单号(必填)
		Element TBOrderIdElement = new Element("TBOrderId");
		TBOrderIdElement.setText(taoBaoSerial);
		PolicyElement.addContent(TBOrderIdElement);

		// 保单号(非必填)
		Element PolicyNoElement = new Element("PolicyNo");
		// PolicyNoElement.setText(insured.getpolicyNo());
		PolicyElement.addContent(PolicyNoElement);

		// 投保单号(必填)
		Element ProposalNoElement = new Element("ProposalNo");
		ProposalNoElement.setText(orderSn);
		PolicyElement.addContent(ProposalNoElement);

		// 总保费(必填)
		Element TotalPremiumElement = new Element("TotalPremium");
		if (!StringUtil.isEmpty(totalPremium)) {
			// 以分为单位
			TotalPremiumElement.setText(String.valueOf((int) (Double.parseDouble(totalPremium) * 100)));
		}

		PolicyElement.addContent(TotalPremiumElement);

		// 出单是否成功(必填)
		Element IsSuccessElement = new Element("IsSuccess");
		IsSuccessElement.setText("0");
		PolicyElement.addContent(IsSuccessElement);

		// 出单失败信息(非必填)
		Element FailReasonElement = new Element("FailReason");
		PolicyElement.addContent(FailReasonElement);

		// 电子保单地址(非必填)
		Element PolicyUrlElement = new Element("PolicyUrl");
		PolicyElement.addContent(PolicyUrlElement);

		// 账务日期(非必填)
		Element AccountDateElement = new Element("AccountDate");
		AccountDateElement.setText(currentDate);
		PolicyElement.addContent(AccountDateElement);

		// 出单时间(非必填)
		Element IssuedTimeElement = new Element("IssuedTime");
		IssuedTimeElement.setText(currentTime);
		PolicyElement.addContent(IssuedTimeElement);

		rootPackage.addContent(headerElement);
		rootPackage.addContent(CallbackElement);
		rootPackageList.addContent(rootPackage);

		return transformXMLToString(doc);
	}

	/**
	 * 将Document对象转换成字符串
	 * 
	 * @return 关单报文
	 * @throws Exception
	 */
	public String transformXMLToString(Document document) {

		try {
			XMLOutputter xmlout = new XMLOutputter();
			Format tFormat = Format.getPrettyFormat();
			tFormat.setEncoding("GBK");
			xmlout.setFormat(tFormat);
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			xmlout.output(document, bo);
			String xmlStr = bo.toString();
			return xmlStr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/***
	 * 
	 * @Title: returnPoint
	 * @Description: TODO(撤单返还抵值积分)
	 * @return String 返回撤单抵值金额
	 * @author
	 */
	public String returnPoint(SDInformationRiskTypeSchema sdrisk) {

		String ordersn = sdrisk.getorderSn();
		if (StringUtil.isNotEmpty(ordersn)) {
			String sql = "where ordersn = '" + ordersn + "'";
			QueryBuilder qb = new QueryBuilder(sql);
			SDOrdersSchema sdOrderSchema = new SDOrdersSchema();
			SDOrdersSet sdOrderSet = new SDOrdersSet();
			sdOrderSet = sdOrderSchema.query(qb);
			sdOrderSchema = sdOrderSet.get(0);
			String offsetPoint = sdOrderSchema.getoffsetPoint();
			if (StringUtil.isEmpty(offsetPoint) || "0".equals(String.valueOf(offsetPoint))) {
				logger.info("撤单积分返还，订单积分为0，订单号为：{}", ordersn);
				return null;
			} else {
				String paysn = sdOrderSchema.getpaySn();
				if (StringUtil.isNotEmpty(paysn)) {
					Transaction tran = new Transaction();
					int offset = 0;
					BigDecimal PointScalerUnit = new BigDecimal(Config.getConfigValue("PointScalerUnit"));
					QueryBuilder qb_order = new QueryBuilder("select id from sdorders where paysn=? and orderstatus !='9' ",
							paysn);
					memberSchema tmemberSchema = new memberSchema();
					tmemberSchema.setid(sdOrderSchema.getmemberId());
					if (!tmemberSchema.fill()) {
						logger.info("用户不存在，订单号为：{}", ordersn);
						return null;
					}
					if (qb_order.executeDataTable().getRowCount() == 0) {
						DataTable dt_point = new QueryBuilder(
								"select Integral from SDIntCalendar where Businessid in (select ordersn from sdorders where paysn=?) and Source='"
										+ IntegralConstant.POINT_SOURCE_OFFSET_POINT + "' and Manner='0' and Status='0' ",
								paysn).executeDataTable();

						if (dt_point.getRowCount() > 0) {
							for (int i = 0; i < dt_point.getRowCount(); i++) {
								offset = offset + Integer.parseInt(dt_point.getString(i, 0));
							}
							offset = new BigDecimal(offsetPoint).intValue() - offset;
						} else {
							offset = Integer.parseInt(offsetPoint);
						}
						if (offset <= 0) {
							logger.info("撤单积分返还最后一单，订单积分减法异常，订单号为：{}", ordersn);
							return null;
						}
						// 更改可用积分
						DataTable dt_member = new QueryBuilder(
								" select currentValidatePoint,usedPoint from member   where id=? ",
								sdOrderSchema.getmemberId()).executeDataTable();
						if (dt_member.getRowCount() < 1) {
							logger.info("撤单查询会员信息无数据，订单积分减法异常，订单号为：{}", ordersn);
							return null;
						}
						String currentValidatePoint = dt_member.getString(0, 0);
						if (StringUtil.isEmpty(currentValidatePoint)) {
							currentValidatePoint = "0";
						}
						String usedPoint = dt_member.getString(0, 1);
						if (StringUtil.isEmpty(usedPoint)) {
							usedPoint = "0";
						}
						QueryBuilder qb_member;
						if (StringUtil.isEmpty(tmemberSchema.getversion())) {
							qb_member = new QueryBuilder(
									"update member set currentValidatePoint =?,usedPoint=?,version='1'  where id=? and (version is null or version='')");
							qb_member.add(new BigDecimal(currentValidatePoint).add(new BigDecimal(offset)).intValue());
							qb_member.add(new BigDecimal(usedPoint).subtract(new BigDecimal(offset)).intValue());
							qb_member.add(sdOrderSchema.getmemberId());
							tran.add(qb_member);
						} else {
							qb_member = new QueryBuilder(
									"update member set currentValidatePoint =?,usedPoint=?,version=version+1  where id=? and version=?");
							qb_member.add(new BigDecimal(currentValidatePoint).add(new BigDecimal(offset)).intValue());
							qb_member.add(new BigDecimal(usedPoint).subtract(new BigDecimal(offset)).intValue());
							qb_member.add(sdOrderSchema.getmemberId());
							qb_member.add(tmemberSchema.getversion());
							tran.add(qb_member);
						}
						// 在流水表中创建新的撤单流水记录
						SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
						tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
						tSDIntCalendarSchema.setMemberId(sdOrderSchema.getmemberId());// 会员id
						tSDIntCalendarSchema.setIntegral(String.valueOf(offset));// 积分值
						tSDIntCalendarSchema.setSource(IntegralConstant.POINT_SOURCE_OFFSET_POINT);// 表示积分抵值返还
						tSDIntCalendarSchema.setBusinessid(ordersn);
						tSDIntCalendarSchema.setManner("0");// 表示收入
						tSDIntCalendarSchema.setStatus("0"); // 表示正常
						tSDIntCalendarSchema.setProp1(sdrisk.getId());// 保单号表id
						tSDIntCalendarSchema.setsvaliDate(PubFun.getCurrentDate());// 生效时间
						tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
						tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
						tSDIntCalendarSchema.setDescription(IntegralConstant.POINT_SOURCE_OFFSET_POINT_DES);
						tran.add(tSDIntCalendarSchema, Transaction.INSERT);
						if (tran.commit()) {
							logger.info("撤单返还抵值积分会员积分退还账户成功");
						} else {
							logger.error("撤单返还抵值积分会员积分退还账户发生异常，" +
									"memberid为：{}订单号为：{}",sdOrderSchema.getmemberId(), ordersn);
						}
					} else {
						// DataTable dt_tradeamount = new
						// QueryBuilder("SELECT tradeamount FROM tradesummaryinfo WHERE paysn=?",
						// paysn).executeDataTable();
						// BigDecimal tradeamount=new
						// BigDecimal(dt_tradeamount.getString(0,0));
						BigDecimal integralValue = new BigDecimal(sdrisk.getintegralValue());
						offset = PointScalerUnit.multiply(integralValue).intValue();
						// 更改可用积分
						DataTable dt_member = new QueryBuilder(
								" select currentValidatePoint,usedPoint from member   where id=? ",
								sdOrderSchema.getmemberId()).executeDataTable();
						if (dt_member.getRowCount() < 1) {
							logger.error("撤单查询会员信息无数据，订单积分减法异常，订单号为：{}", ordersn);
							return null;
						}
						String currentValidatePoint = dt_member.getString(0, 0);
						if (StringUtil.isEmpty(currentValidatePoint)) {
							currentValidatePoint = "0";
						}
						String usedPoint = dt_member.getString(0, 1);
						if (StringUtil.isEmpty(usedPoint)) {
							usedPoint = "0";
						}
						QueryBuilder qb_member;
						if (StringUtil.isEmpty(tmemberSchema.getversion())) {
							qb_member = new QueryBuilder(
									"update member set currentValidatePoint =?,usedPoint=?,version='1'  where id=? and (version is null or version='')");
							qb_member.add(new BigDecimal(currentValidatePoint).add(new BigDecimal(offset)).intValue());
							qb_member.add(new BigDecimal(usedPoint).subtract(new BigDecimal(offset)).intValue());
							qb_member.add(sdOrderSchema.getmemberId());
							tran.add(qb_member);
						} else {
							qb_member = new QueryBuilder(
									"update member set currentValidatePoint =?,usedPoint=?,version=version+1  where id=? and version=?");
							qb_member.add(new BigDecimal(currentValidatePoint).add(new BigDecimal(offset)).intValue());
							qb_member.add(new BigDecimal(usedPoint).subtract(new BigDecimal(offset)).intValue());
							qb_member.add(sdOrderSchema.getmemberId());
							qb_member.add(tmemberSchema.getversion());
							tran.add(qb_member);
						}
						// 在流水表中创建新的撤单流水记录
						SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
						tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
						tSDIntCalendarSchema.setMemberId(sdOrderSchema.getmemberId());// 会员id
						tSDIntCalendarSchema.setIntegral(String.valueOf(offset));// 积分值
						tSDIntCalendarSchema.setSource(IntegralConstant.POINT_SOURCE_OFFSET_POINT);// 表示积分抵值返还
						tSDIntCalendarSchema.setBusinessid(ordersn);
						tSDIntCalendarSchema.setManner("0");// 表示收入
						tSDIntCalendarSchema.setStatus("0"); // 表示正常
						tSDIntCalendarSchema.setProp1(sdrisk.getId());// 保单号表id
						tSDIntCalendarSchema.setsvaliDate(PubFun.getCurrentDate());// 生效时间
						tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
						tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
						tSDIntCalendarSchema.setDescription(IntegralConstant.POINT_SOURCE_OFFSET_POINT_DES);
						tran.add(tSDIntCalendarSchema, Transaction.INSERT);
						if (tran.commit()) {
							logger.info("撤单返还抵值积分会员积分退还账户成功");
						} else {
							logger.error("撤单返还抵值积分会员积分退还账户发生异常，" +
									"memberid为：{}订单号为：",sdOrderSchema.getmemberId(), ordersn);
						}
					}
					if (offset > 0) {
						return new BigDecimal(offset).divide(PointScalerUnit,
								2, BigDecimal.ROUND_HALF_UP).toString();
					}
				} else {
					logger.error("撤单返还抵值积分支付流水号有异常，" +
							"支付流水号为：{}，订单号为：{}",sdOrderSchema.getpaySn(), ordersn);
				}
			}
		} else {
			logger.warn("撤单返还抵值积分传入的订单号为空");
		}
		return null;
	}

	/**
	 * 
	 * @Title: calculatePointToZero
	 * @Description: TODO(撤单冻结积分按比例扣除)
	 * @return boolean 返回类型
	 * @author
	 */
	public void calculatePointToZero(SDInformationRiskTypeSchema sdrisk) {

		try {
			// 订单号
			String ordersn = sdrisk.getorderSn();
			// 订单
			SDOrdersSchema sdOrderSchema = new SDOrdersSchema();
			SDOrdersSet sdOrderSet = new SDOrdersSet();
			sdOrderSet = sdOrderSchema.query(new QueryBuilder(" where ordersn=?", ordersn));
			sdOrderSchema = sdOrderSet.get(0);
			memberSchema tmemberSchema = new memberSchema();
			tmemberSchema.setid(sdOrderSchema.getmemberId());
			// 订单详情
			SDInformationSchema sdinformation = new SDInformationSchema();
			SDInformationSet sdinformationSet = new SDInformationSet();
			sdinformationSet = sdinformation.query(new QueryBuilder(" where ordersn=?", ordersn));
			sdinformation = sdinformationSet.get(0);
			// 积分流水
			SDIntCalendarSchema sdintcalendar = new SDIntCalendarSchema();
			SDIntCalendarSet sdintcalendarset = new SDIntCalendarSet();
			sdintcalendarset = sdintcalendar.query(new QueryBuilder(
					" where businessid=? and (source='0' or source='26')  and Manner='0' and  status='1' ", ordersn));
			Transaction transaction = new Transaction();
			int total = 0;
			if (tmemberSchema.fill() && tmemberSchema != null) {
				// 冻结积分
				int point = tmemberSchema.getpoint();
				for (int i = 0; i < sdintcalendarset.size(); i++) {
					sdintcalendar = sdintcalendarset.get(i);
					int intcal = Integer.parseInt(sdintcalendar.getIntegral());
					// 保单金额
					BigDecimal timePrem = new BigDecimal(sdrisk.gettimePrem());
					// 订单总金额
					BigDecimal totalamount = new BigDecimal(sdOrderSchema.gettotalAmount());
					// 保单占订单总金额的百分比
					BigDecimal divid = timePrem.divide(totalamount, 2, BigDecimal.ROUND_HALF_UP);
					// 退保保单所占比例计算后积分
					int risktype_point = new BigDecimal(sdinformation.getpoint()).multiply(divid)
							.intValue();
					// 判断是全部撤单还是部分撤单,全部撤单积分流水表清零
					if ("9".equals(String.valueOf(sdOrderSchema.getorderStatus()))) {
						sdintcalendar.setStatus("2");
						sdintcalendar.setManner("2");
						sdintcalendar.setIntegral("0");
						risktype_point = intcal;
						transaction.add(sdintcalendar, Transaction.UPDATE);
						tmemberSchema.setisBuy("N");

						// 加成积分清零
						QueryBuilder qb_update = new QueryBuilder(
								"update SDIntCalendar set Integral='0' where  (source='0' or source='26') and Manner='0' and  status='1' and prop3 is not null and prop3!='' and businessid=? ",
								ordersn);
						transaction.add(qb_update);
					} else {
						if (StringUtil.isNotEmpty(sdintcalendar.getProp3())) {
							continue;
						} else {
							sdintcalendar.setIntegral(String.valueOf(intcal - risktype_point));
							transaction.add(sdintcalendar, Transaction.UPDATE);
						}
					}
					total = total + risktype_point;
				}
				if (point >= total) {
					// 更新对应会员的冻结积分
					tmemberSchema.setpoint(tmemberSchema.getpoint() - total);
					transaction.add(tmemberSchema, Transaction.UPDATE);
				} else {
					Object[] argArr = {ordersn, tmemberSchema.getpoint(), };
					logger.info("撤单冻结积分归零的用户账户的冻结积分小于订单积分：" +
							"订单号为：{};用户账户积分为：{};扣除积分总数为：{}", argArr);
				}
				// 在流水表中创建新的撤单流水记录
				SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
				tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
				tSDIntCalendarSchema.setMemberId(tmemberSchema.getid());// 会员id
				tSDIntCalendarSchema.setIntegral(String.valueOf(total));// 积分值
				tSDIntCalendarSchema.setSource("0");// 表示购买产品
				tSDIntCalendarSchema.setBusinessid(ordersn);
				tSDIntCalendarSchema.setManner("2");// 表示撤单
				tSDIntCalendarSchema.setStatus("2"); // 表示撤单
				tSDIntCalendarSchema.setProp1(sdrisk.getId());// 保单号表id
				tSDIntCalendarSchema.setsvaliDate(PubFun.getCurrentDate());
				tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
				tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
				transaction.add(tSDIntCalendarSchema, Transaction.INSERT);
				// 提交
				if (!transaction.commit()) {
					logger.error("类PointServiceManager执行方法calculatePointToZero()提交事务发生异常！");
				}
			}
		} catch (Exception e) {
			logger.error("类PointServiceManager执行方法calculatePointToZero()发生异常！" + e.getMessage(), e);
		}
	}

	public void cancelCpsOrder(String orderSn) throws MalformedURLException {

		logger.info("查询CPS订单！！！");
		String sql = " select channelsn from sdorders where ordersn=? ";
		QueryBuilder qb = new QueryBuilder(sql, orderSn);
		String channelsn = qb.executeOneValue() + "";
		if (StringUtil.isNotEmpty(channelsn)) {

			String webServiceUrl = null;
			if (channelsn.endsWith("dlr")) {
				webServiceUrl = Config.getValue("AGENTSERVERICEURL");
				
			} else if (channelsn.indexOf("swpt") != -1 || channelsn.endsWith("wap_cps") || "cps_drlm_wap".equals(channelsn)) {
				webServiceUrl = Config.getValue("CPSSERVERICEURL");

			} else {
				return;
			}
			logger.info("调用CPS接口地址==>{}", webServiceUrl);

			Service servicemodel = new ObjectServiceFactory().create(CpsProductBuyService.class);
			CpsProductBuyService service = (CpsProductBuyService) new XFireProxyFactory()
					.create(servicemodel, webServiceUrl);
			service.saveOrder(orderSn);
			logger.info("调用CPS接口结束");
		}
	}
	
	
	
	// 取消订单并退款
		public void cancelOrderAndRefund() throws ParseException {
			String channelsn = ""; 
			String memberId = "";
			String productId = "";
			String appStatus = "";
			String ordersn = "";
			String riskTypeId = "";
			String orderstatus = "";
			int orderStatusflag = 0;
			String paysn = "";
			String integralValue = "";
			String timePrem= "" ; 
			String cancelDate= "" ;
			String payPrice= "" ;
			String policyNo= ""  ;
			String description = "";
			String channelname = "";
			String orderSn = $V("orderSn");
			String svaliDate = "";
			String evaliDate = "";
			String recognizeeSn = "";
			String sql = "select a.insuranceCompany , c.appStatus ,a.ordersn ,c.id, d.orderstatus ,d.channelsn ,d.memberId"
					+ "  ,a.productId ,d.paysn ,c.integralValue , c.timePrem , c.cancelDate , c.payPrice ,c.policyNo ,p.description"
					+",(SELECT channelname FROM channelinfo cl  WHERE cl.channelcode = d.channelsn  ) as channelname ,a.startDate,a.endDate,c.recognizeeSn"
					+ " from SDInformation a,sdinformationrisktype c , sdorders d left join paybase p on d.paytype=p.paytype"
					+ " where a.orderSn=c.orderSn and a.orderSn = d.orderSn and a.orderSn=?";
			DataTable dt = new QueryBuilder(sql, orderSn).executeDataTable();
			StringBuffer policyNos = new StringBuffer();
			int j = 0;
			for (int i = 0; i < dt.getRowCount(); i++) {
				appStatus = dt.getString(i, 1);// 承保标记
				ordersn = dt.getString(i, 2);
				riskTypeId = dt.getString(i, 3);
				orderstatus = dt.getString(i, 4);
				channelsn = dt.getString(i, 5);
				memberId = dt.getString(i, 6);
				productId = dt.getString(i, 7);
				paysn =  dt.getString(i, 8);
				integralValue = dt.getString(i, 9);
				timePrem =dt.getString(i, 10);
			    cancelDate= dt.getString(i, 11);
			    payPrice= dt.getString(i, 12);
			    policyNo= dt.getString(i, 13);
                description = dt.getString(i, 14);
                channelname = dt.getString(i, 15);
                svaliDate = dt.getString(i, 16);
                evaliDate = dt.getString(i, 17);
                recognizeeSn = dt.getString(i, 18);
				if (!"7".equals(orderstatus.toString())) {
					Response.setLogInfo(0, "订单未支付不可以撤单！");
					return;
				}
				if ("0".equals(appStatus) || StringUtil.isEmpty(appStatus)) {// 未承保
					if ( StringUtil.isNotEmpty(riskTypeId)) {
						// 更新保单表的撤单信息
						SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
						sdRiskTypeSchema.setId(riskTypeId);
						sdRiskTypeSchema.setorderSn(ordersn);
						sdRiskTypeSchema.setintegralValue(integralValue);
						sdRiskTypeSchema.setcancelDate(new Date());
						sdRiskTypeSchema.setpayPrice(payPrice);
						sdRiskTypeSchema.setpolicyNo(policyNo);
						sdRiskTypeSchema.settimePrem(timePrem);
						if (j == 0) {
							policyNos.append("'");
							policyNos.append(riskTypeId);
							policyNos.append("'");
						}else {
							policyNos.append(",'");
							policyNos.append(riskTypeId);
							policyNos.append("'");
						}
						j++;
						this.returnPoint(sdRiskTypeSchema);
						// 冻结积分清零
						this.calculatePointToZero(sdRiskTypeSchema);
						if (Constant.B2B_APP.equals(channelsn)  || Constant.B2B_HTML5.equals(channelsn)) {
							this.dealB2bBalanceInfo(channelsn, memberId, ordersn,
									sdRiskTypeSchema.getcancelDate(), productId,
									"-"+payPrice,recognizeeSn,svaliDate,evaliDate);
						}
					} else {
						Response.setLogInfo(0, "订单有误！");
						return;
					}
				} else {// 承保
					++orderStatusflag;
				}

			}

			if (orderStatusflag == dt.getRowCount()) {
				Response.setLogInfo(0, "您的订单已全部承保！");
				return;
			} else {
				String sqlsdorders = "update sdorders set orderStatus = ?,modifydate=now() where ordersn = ?";
				String sqlsdinformationrisktype = "UPDATE sdinformationrisktype SET appstatus = '2',modifydate=NOW(),cancelDate =NOW(), balanceMsg='取消订单并退款' WHERE id IN("+policyNos+")";
				QueryBuilder qbrisktype = new QueryBuilder(sqlsdinformationrisktype);
				QueryBuilder qbProduct = new QueryBuilder(sqlsdorders);
				if (orderStatusflag == 0) {
					qbProduct.add("9");
				} else {
					qbProduct.add("10");
				}
				qbProduct.add(ordersn);
				int rowResult = qbProduct.executeNoQuery();
				int risktypeResult =qbrisktype.executeNoQuery();
				//TODO
				//快钱渠道撤单调用接口
				if (channelsn.equals(Constant.CHANNELSN_BILL_KQ)) {
					try {
						// 发送消息队列到消费者
						Map<String, String> dataSendMap = new HashMap<String, String>();
						dataSendMap.put("type", "2");
						dataSendMap.put("orderSn", orderSn);
						dataSendMap.put("operator", "dealBillCallback");
						ProducerMQ mq = new ProducerMQ();
						mq.sendToCallBackPolicy(JSON.toJSONString(dataSendMap));

					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (rowResult == 0 || risktypeResult == 0) {
					Response.setLogInfo(0, "订单状态更新失败！请联系运维人员数据库修改订单状态！");
					return;
				}

			}

			try {
				UserLog.log("MemberOrderInquiry", "CancelOrderAndRefund",
						"取消订单并退款 ", Request.getClientIP(), User.getUserName());
			} catch (Exception e) {
				logger.error("========CancelCont类的方法（cancelOrderAndRefund）生成操作日志失败，" +
						"订单号："+ordersn+"======" + e.getMessage(), e);
			}
			String toMail = Config.getValue("cancelOrderAndRefund");// 发给财务邮件
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("ordersn", orderSn);
			data.put("paysn", paysn);
			if(StringUtil.isEmpty(channelname)){
				data.put("channelsn", channelsn + "" +description);
				logger.warn("========表channelinfo不存在{}渠道======", channelsn);
			}else{
				data.put("channelsn", channelname + "," +description);
			}
			if(StringUtil.isNotEmpty(toMail)){
				if (ActionUtil.sendMail("wj00199", StringUtils.join(toMail.split(";"), ","), data)) {
					Response.setLogInfo(1, "撤单成功！");
				} else {
					Response.setLogInfo(1, "撤单成功但邮件发送失败！");
				}
			}else{
				Response.setLogInfo(1, "撤单成功但邮件发送失败(邮件接收邮箱为空)！");
			}
		}
}
