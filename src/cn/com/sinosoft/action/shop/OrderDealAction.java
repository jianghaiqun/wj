package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.action.shop.uw.UsersUWCheck;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.service.CpsProductBuyService;
import cn.com.sinosoft.service.impl.OrderConfigNewServiceImpl;
import cn.com.sinosoft.service.impl.SDOrderServiceImpl;
import cn.com.sinosoft.util.ArithUtil;
import com.sinosoft.cms.dataservice.Retransmit;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.SchemaUtil;
import com.sinosoft.framework.servlets.TBContInsureServlet;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.jdt.CancelContService;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.schema.BuyForCustomerOldSnSchema;
import com.sinosoft.schema.PolicyChangeInfoSchema;
import com.sinosoft.schema.PolicyChangeInfoSet;
import com.sinosoft.schema.SDInformationAppntSchema;
import com.sinosoft.schema.SDInformationDutySchema;
import com.sinosoft.schema.SDInformationDutySet;
import com.sinosoft.schema.SDInformationInsuredSchema;
import com.sinosoft.schema.SDInformationInsuredSet;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.schema.SDInsuredHealthSchema;
import com.sinosoft.schema.SDInsuredHealthSet;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.SDIntCalendarSet;
import com.sinosoft.schema.SDOrderItemOthSchema;
import com.sinosoft.schema.SDOrderItemOthSet;
import com.sinosoft.schema.SDOrderItemSchema;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.SDOrdersSet;
import com.sinosoft.schema.SDRemarkSchema;
import com.sinosoft.schema.TradeInformationSchema;
import com.sinosoft.schema.TradeInformationSet;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.sinosoft.schema.TradeSummaryInfoSet;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.wangjin.cms.orders.QueryOrders;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ParentPackage("shop")
public class OrderDealAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8065835021320338L;

	// 交互保险公司编码
	private String comId = "";

	/**
	 * 订单变更
	 * 
	 * @return
	 */
	public void changeOrder() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		InputStream inputStream = null;
		BufferedReader reader = null;
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			String strResponse = "";

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));

			while (StringUtil.isNotEmpty(strMessage = reader.readLine())) {
				strResponse += strMessage;
			}
			JSONObject basicOrders = JSONObject.fromObject(strResponse);
			// 投保人信息
			/* String applicantInfo = request.getParameter("applicantInfo"); */
			JSONObject appInfo = JSONObject.fromObject(basicOrders
					.get("applicantInfo"));
			// 保单信息
			/* String insuredInfo = request.getParameter("insuredInfo"); */
			JSONObject insuInfo = JSONObject.fromObject(basicOrders
					.get("insuredInfo"));
			JSONArray insuredArray = insuInfo.getJSONArray("insureds");
			if (insuredArray == null || insuredArray.isEmpty()) {
				results.put("Status", 0);
				results.put("Msg", "保单信息为空，不能变更！");
			} else {
				// 健康信息
				/* String healData = request.getParameter("healInfo"); */
				/*JSONObject healInfo = JSONObject.fromObject(basicOrders
						.get("healData"));
				// TODO 为空怎么办？？？
				JSONArray healArray = healInfo.getJSONArray("healDatas");*/

				int size = insuredArray.size();
				DataTable insuredDT = new DataTable();
				/*
				 * insuredDT.insertColumn("checkflag");
				 * insuredDT.insertColumn("appFlag");
				 * insuredDT.insertColumn("baodanId");
				 * insuredDT.insertColumn("recognizeeAppntRelation");
				 * insuredDT.insertColumn("appStatus");
				 * insuredDT.insertColumn("policyNo");
				 */
				DataColumn[] types = new DataColumn[14];
				types[0] = new DataColumn("checkflag", DataColumn.STRING);
				types[1] = new DataColumn("appFlag", DataColumn.STRING);
				types[2] = new DataColumn("baodanId", DataColumn.STRING);
				types[3] = new DataColumn("recognizeeAppntRelation",
						DataColumn.STRING);
				types[4] = new DataColumn("appStatus", DataColumn.STRING);
				types[5] = new DataColumn("policyNo", DataColumn.STRING);
				types[6] = new DataColumn("recognizeeName", DataColumn.STRING);
				types[7] = new DataColumn("recognizeeIdentityId",
						DataColumn.STRING);
				types[8] = new DataColumn("recognizeeMobile", DataColumn.STRING);
				types[9] = new DataColumn("recognizeeMail", DataColumn.STRING);
				types[10] = new DataColumn("recognizeeIdentityType",
						DataColumn.STRING);
				types[11] = new DataColumn("recognizeeSex", DataColumn.STRING);
				types[12] = new DataColumn("recognizeeBirthday",
						DataColumn.STRING);
				types[13] = new DataColumn("recognizeeEnName",
						DataColumn.STRING);

				for (int i = 0; i < size; i++) {

					JSONObject insured = JSONObject.fromObject(insuredArray
							.getJSONObject(i));
					String checkflag = insured.getString("checkflag");
					String[] values = { checkflag,
							insured.getString("appFlag"),
							insured.getString("baodanId"),
							insured.getString("recognizeeAppntRelation"),
							insured.getString("appStatus"),
							insured.getString("policyNo"),
							insured.getString("recognizeeName"),
							insured.getString("recognizeeIdentityId"),
							insured.getString("recognizeeMobile"),
							insured.getString("recognizeeMail"),
							insured.getString("recognizeeIdentityType"),
							insured.getString("recognizeeSex"),
							DateUtil.toString(DateUtil.parse(insured.getString("recognizeeBirthday"))),
							insured.getString("recognizeeEnName") };
					DataRow dr = new DataRow(types, values);
					insuredDT.insertRow(dr);
					/*
					 * insuredDT.set(i, "checkflag", checkflag);
					 * insuredDT.set(i, "appFlag",
					 * insured.getString("appFlag")); insuredDT.set(i,
					 * "baodanId", insured.getString("baodanId"));
					 * insuredDT.set(i,
					 * "recognizeeAppntRelation",insured.getString
					 * ("recognizeeAppntRelation")); insuredDT.set(i,
					 * "appStatus", insured.getString("appStatus"));
					 * insuredDT.set(i, "policyNo",
					 * insured.getString("policyNo"));
					 */
				}
				/*int hsize = healArray.size();
				DataTable healDT = new DataTable();
				DataColumn[] heal_types = new DataColumn[3];
				heal_types[0] = new DataColumn("selectFlag", DataColumn.STRING);
				heal_types[1] = new DataColumn("healthid", DataColumn.STRING);
				heal_types[2] = new DataColumn("healthyinfoid",
						DataColumn.STRING);
				if (hsize > 0) {
					for (int i = 0; i < hsize; i++) {
						JSONObject heal = JSONObject.fromObject(healArray
								.getJSONObject(i));

						String[] values = { heal.getString("selectFlag"),
								heal.getString("healthid"),
								heal.getString("healthyinfoid") };
						DataRow dr = new DataRow(heal_types, values);
						healDT.insertRow(dr);
						
						 * if (!heal.getString("selectFlag").isEmpty()) {
						 * healDT.set(i, "selectFlag",
						 * heal.getString("selectFlag")); } if
						 * (!heal.getString("healthyinfoid").isEmpty()) {
						 * healDT.set(i, "healthyinfoid",
						 * heal.getString("healthyinfoid")); }
						 
					}

				}*/
				// 订单信息
				/* String orderInfo = request.getParameter("orderInfo"); */
				JSONObject ordInfo = JSONObject.fromObject(basicOrders
						.get("orderInfo"));
				// 订单表ID
				String orderId = ordInfo.getString("id");
				// 订单号
				String orderSn = ordInfo.getString("orderSn");
				// 订单信息表ID
				String informationId = ordInfo.getString("informationId");
				// 投保人ID
				String applicantId = appInfo.getString("applicantId");
				// 投保人姓名
				String applicantName = appInfo.getString("applicantName");
				// 投保人姓名
				String applicantEnName = appInfo.getString("applicantEnName");
				// 投保人证件类型
				String applicantIdentityType = appInfo
						.getString("applicantIdentityType");
				// 投保人证件类型名称
				String applicantIdentityTypeName = appInfo
						.getString("applicantIdentityTypeName");
				// 投保人证件号
				String applicantIdentityId = appInfo
						.getString("applicantIdentityId");
				String applicantSexName = appInfo.getString("applicantSexName");
				// 投保人性别
				String applicantSex = appInfo.getString("applicantSex");
				// 投保人出生日期
				String applicantBirthday = appInfo
						.getString("applicantBirthday");
				// 投保人手机号
				String applicantMobile = appInfo.getString("applicantMobile");
				// 投保人邮箱
				String applicantMail = appInfo.getString("applicantMail");
				// 保险起期
				String svalidate = ordInfo.getString("svalidate");
				String svalitime = ordInfo.getString("svalitime");
				// 保险止期
				String evaliDate = ordInfo.getString("evaliDate");

				String NDestination = ordInfo.getString("NDestination");

				String FDestination = ordInfo.getString("FDestination");

				String destination = ordInfo.getString("destination");
				
				String appntType = ordInfo.getString("appnType");
				
				String loginName = ordInfo.getString("loginName");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", orderId);
				map.put("orderSn", orderSn);
				map.put("informationId", informationId);
				map.put("applicantId", applicantId);
				map.put("svalidate", svalidate);
				map.put("svalitime", svalitime);
				map.put("evaliDate", evaliDate);
				map.put("NDestination", NDestination);
				map.put("FDestination", FDestination);
				map.put("destination", destination);
				map.put("applicantName", applicantName);
				map.put("applicantEnName", applicantEnName);
				map.put("applicantIdentityType", applicantIdentityType);
				map.put("applicantIdentityTypeName", applicantIdentityTypeName);
				map.put("applicantIdentityId", applicantIdentityId);
				map.put("applicantSex", applicantSex);
				map.put("applicantSexName", applicantSexName);
				map.put("applicantBirthday", applicantBirthday);
				map.put("applicantMobile", applicantMobile);
				map.put("applicantMail", applicantMail);
				map.put("appntType", appntType);
				map.put("loginName", loginName);
				// 保单信息
				map.put("Data", insuredDT);
				// 健康信息
				/*map.put("healData", healDT);*/
				logger.info("==== changeOrder 入参信息：{} ===", map.toString());
				try {
					results = policyChange(map);

				} catch (Exception e) {
					logger.error("订单变更接口异常." + e.getMessage(), e);
				}
			}
			response.getWriter().print(
					JSONObject.fromObject(results).toString());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}

	/**
	 * 撤单处理
	 * 
	 * @return
	 */
	public void dealCancel() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		InputStream inputStream = null;
		BufferedReader reader = null;
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			String strResponse = "";

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));

			while (StringUtil.isNotEmpty(strMessage = reader.readLine())) {
				strResponse += strMessage;
			}
			JSONObject basicOrders = JSONObject.fromObject(strResponse);
			/*
			 * JSONObject basicOrderInfo = JSONObject.fromObject(basicOrders
			 * .get("IDs"));
			 */
			String ids = basicOrders.getString("IDs");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("IDs", ids);

			try {
				results = dealCancelInfo(map);
			} catch (Exception e) {
				logger.error("撤单接口异常." + e.getMessage(), e);
			}
			response.getWriter().print(
					JSONObject.fromObject(results).toString());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public Map<String, Object> dealCancelInfo(Map<String, Object> map) {

		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		String ids = String.valueOf(map.get("IDs")); // $V("IDs")
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			results.put("Status", 0);
			results.put("Msg", "传入ID时发生错误");
			return results;
		}
		String[] id = ids.split(",");
		int n = id.length;
		if (n == 1) {
			for (int i = 0; i < n; i++) {
				String channelsn = "";
				String memberId = "";
				String insuredSn = id[i];
				String comCode = "";
				String appStatus = "";
				String ordersn = "";
				String riskTypeId = "";
				String balanceStatus = "";
				String flag = "";
				// b2c财务流水用（虚拟支付）
				String paysn = "";
				String paytype = "";
				String sql = "select a.insuranceCompany , c.appStatus ,a.ordersn ,c.id,c.balanceStatus ,d.channelsn ,d.memberId ,a.productId "
						+ " , d.paySn ,d.payType from SDInformation a, sdorders d, sdinformationinsured b ,sdinformationrisktype c "
						+ "where a.informationSn=b.informationSn and b.recognizeeSn = c.recognizeeSn and a.orderSn = d.orderSn and b.insuredSn=?";
				DataTable dt = new QueryBuilder(sql, insuredSn)
						.executeDataTable();
				if (dt.getRowCount() == 1) {
					comCode = dt.getString(0, 0);
					appStatus = dt.getString(0, 1);
					ordersn = dt.getString(0, 2);
					riskTypeId = dt.getString(0, 3);
					balanceStatus = dt.getString(0, 4);
					channelsn = dt.getString(0, 5);
					memberId = dt.getString(0, 6);
					// b2c财务流水用（虚拟支付）
					paysn = dt.getString(0, 8);
					paytype = dt.getString(0, 9);

				}
				if (!"0".equals(balanceStatus) && "1".equals(appStatus)) {
					results.put("Status", 0);
					results.put("Msg", "此订单未结算，不能撤单！");
					return results;
				}
				if (("1".equals(appStatus) || "3".equals(appStatus))
						&& StringUtil.isNotEmpty(insuredSn)
						&& StringUtil.isNotEmpty(comCode)) {
					CancelContService tCancelContService = new CancelContService();
					flag = tCancelContService.callInsTransInterface(comCode,
							ordersn, insuredSn, riskTypeId);
					// 保险公司撤单成功后 更新系统数据状态
					if ("SUCCESS".equals(flag)) {
						// 更新保单表的撤单信息
						SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
						sdRiskTypeSchema.setId(riskTypeId);
						if (sdRiskTypeSchema.fill()) {
							boolean isGroup = com.sinosoft.lis.pubfun.PubFun.getGroupTypeByOrdersn(sdRiskTypeSchema.getorderSn());
							if(isGroup){
								QueryBuilder qb_update = new QueryBuilder("update SDInformationRiskType set appStatus=?,cancelDate=? where policyno = ?");
								qb_update.add("2");
								qb_update.add(new Date());
								qb_update.add(sdRiskTypeSchema.getpolicyNo());
								qb_update.executeNoQuery();
							}else{
								sdRiskTypeSchema.setappStatus("2");// 撤单成功后状态更新为2
								sdRiskTypeSchema.setcancelDate(new Date());
								sdRiskTypeSchema.update();
							}
							
						}
						// 更新订单表的订单状态
						tCancelContService.changeSDOrders(ordersn);
					}

				} else if ("2".equals(appStatus)) {
					results.put("Status", 0);
					results.put("Msg", "此订单已经撤单！");
					return results;
					// 网金结算中心撤单失败时，可以重新撤单
				} else if ("4".equals(appStatus)) {
					flag = "SUCCESS";
				} else if ("5".equals(appStatus)) {
					results.put("Status", 0);
					results.put("Msg", "此订单已退款！");
					return results;
					// 网金结算中心撤单失败时，可以重新撤单
				}else {
					results.put("Status", 0);
					results.put("Msg", "订单有误！");
					return results;
				}
				if ("SUCCESS".equals(flag)) {
					if (StringUtil.isNotEmpty(riskTypeId)) {
						CancelContService tCancelContService = new CancelContService();
						SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
						SDInformationRiskTypeSet sdRiskTypeSet = new SDInformationRiskTypeSet();
						sdRiskTypeSet = sdRiskTypeSchema
								.query(new QueryBuilder("where ID = '"
										+ riskTypeId + "'"));
						if (!sdRiskTypeSet.isEmpty()
								&& sdRiskTypeSet.size() == 1) {
							sdRiskTypeSchema = sdRiskTypeSet.get(0);
							if ("4".equals(sdRiskTypeSchema.getappStatus())||
									"2".equals(sdRiskTypeSchema.getappStatus())) {// 表示与保险公司对接退保成功
								if (tCancelContService.callProductInterface(
										ordersn, "", PubFun.getCurrentDate(),
										"02", sdRiskTypeSchema.getpolicyNo(),
										riskTypeId, "",
										"")) {
									try {
										// b2c财务流水用
										if (Constant.B2B_LQ.equals(paytype)) {
											results.put("paytype", paytype);
											results.put("paysn", paysn);
											results.put("memberId", memberId);
											results.put("ordersn", ordersn);
											results.put("channelsn", channelsn);
											String paymentfee = "";
											boolean isGroup = com.sinosoft.lis.pubfun.PubFun.getGroupTypeByOrdersn(sdRiskTypeSchema.getorderSn());
											if(isGroup){
												paymentfee = new QueryBuilder("select sum(payPrice) as payPrice from SDInformationRiskType where policyNo=?", sdRiskTypeSchema.getpolicyNo()).executeString();
											}else{
												paymentfee = sdRiskTypeSchema
														.getpayPrice();
											}
											results.put("actualPayPrice",paymentfee);
										} else {
											results.put("paytype", "");
										}
									} catch (Exception e) {
										logger.error(e.getMessage(), e);
									} finally {
										results.put("Status", 1);
										results.put("Msg", "撤单成功！");
										return results;
									}
								} else {
									// bug0003099-主站、美行保（新平台）撤单，信息提示
									String Msg = "撤单结算中心调用失败！";
									if ("4".equals(sdRiskTypeSchema.getappStatus())) {
										Msg = "与产品中心对接撤单失败";
									}
									results.put("Status", 0);
									results.put("Msg", Msg);
									return results;
								}
							}
						}
					} else {
						results.put("Status", 0);
						results.put("Msg", "订单有误！");
						return results;
					}
				} else if ("NOCONFIG".equals(flag)) {
					results.put("Status", 0);
					results.put("Msg", "此公司的订单未开放撤单功能！请联系技术人员。");
					return results;
				} else {
					String errorMsg ="撤单经代通调用失败！";
					if(flag.indexOf("|")!=-1){
						errorMsg = flag.substring(flag.indexOf("|")+1);
					}
					String insureMsg = "";
					if (StringUtil.isNotEmpty(riskTypeId)) {
						SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
						SDInformationRiskTypeSet sdRiskTypeSet = new SDInformationRiskTypeSet();
						sdRiskTypeSet = sdRiskTypeSchema
								.query(new QueryBuilder("where ID = '"
										+ riskTypeId + "'"));
						if (!sdRiskTypeSet.isEmpty()
								&& sdRiskTypeSet.size() == 1) {
							sdRiskTypeSchema = sdRiskTypeSet.get(0);
							insureMsg = sdRiskTypeSchema.getinsureMsg();
							// bug0003099-主站、美行保（新平台）撤单，信息提示
							if (StringUtil.isNotEmpty(sdRiskTypeSchema.getbalanceMsg())) {
								if (StringUtil.isNotEmpty(insureMsg)) {
									insureMsg += "/" + sdRiskTypeSchema.getbalanceMsg();
								}else {
									insureMsg = sdRiskTypeSchema.getbalanceMsg();
								}
							}
						}
					}
					results.put("Status", 0);
					results.put("Msg", errorMsg);
					if(StringUtil.isNotEmpty(insureMsg)){
						results.put("Msg", insureMsg);
					}else{
						results.put("Msg", "撤单经代通调用失败！");
					}
					return results;
				}
			}

		} else {
			results.put("Status", 0);
			results.put("Msg", "传入ID时发生错误");
			return results;
		}
		return results;
	}

	/**
	 * 新接口电子保单下载处理
	 * 
	 * @return
	 */
	public void electronicDownload() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		InputStream inputStream = null;
		BufferedReader reader = null;
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			String strResponse = "";

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));

			while (StringUtil.isNotEmpty(strMessage = reader.readLine())) {
				strResponse += strMessage;
			}
			JSONObject basicOrders = JSONObject.fromObject(strResponse);
			/*
			 * JSONObject basicOrderInfo = JSONObject.fromObject(basicOrders
			 * .get("IDs"));
			 */
			String ids = basicOrders.getString("IDs");
			String riskCode = basicOrders.getString("riskCode");
			String orderSn = basicOrders.getString("orderSn");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("IDs", ids);
			map.put("riskCode", riskCode);

			try {
				if (StringUtil.isNotEmpty(orderSn)) {
					map.put("orderSn", orderSn);
					results = downloadByOrderSn1(map);
				} else {
					results = electronicDownload1(map);
				}
			} catch (Exception e) {
				logger.error("电子保单下载接口异常." + e.getMessage(), e);
			}
			response.getWriter().print(
					JSONObject.fromObject(results).toString());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 新接口电子保单下载处理
	 */
	public Map<String, Object> electronicDownload1(Map<String, Object> map) {

		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		String id = String.valueOf(map.get("IDs"));// $V("IDs");
		// 产品编码
		String riskCode = String.valueOf(map.get("riskCode"));// $V("riskCode");

		if (StringUtil.isNotEmpty(id)) {

			SDInformationRiskTypeSchema riskType = new SDInformationRiskTypeSchema();
			riskType.setId(id);
			String responseXml = "";
			String serialNo = "";
			try {
				if (riskType.fill()) {
					if (StringUtil.isEmpty(riskType.getpolicyNo())) {
						results.put("Status", 0);
						results.put("Msg", "您选择保单没有保单号，不能下载电子保单 ！");
						return results;
					}
					serialNo = riskType.getpolicyNo();
					if(StringUtil.isNotEmpty(riskType.getelectronicPath())){
						results.put("Status", 1);
						results.put("Msg", "下载成功");
					}else{
						Map<String, String> param = new HashMap<String, String>();
						param.put("comCode", riskCode.substring(0, 4));
						param.put("policyNo", riskType.getpolicyNo());
						Map<String, String> resultMap = pdfDownload(param);
						if ("Y".equals(resultMap.get("Success"))) {
							results.put("Status", 1);
							results.put("Msg", resultMap.get("Message"));
						} else {
							results.put("Status", 0);
							results.put("Msg", resultMap.get("Message"));
						}
					}
					
				}
			} catch (Exception e) {
				logger.info("保单({})电子保单下载返回报文：{}", serialNo, responseXml);
				logger.error("=====保单(" + serialNo + ")电子保单下载功能异常===" + e.getMessage(), e);
				results.put("Status", 0);
				results.put("Msg", "网络繁忙，请稍候重试！");
			}
		} else {
			results.put("Status", 0);
			results.put("Msg", "请选择要生成电子保单的条目 ！");
		}
		return results;

	}

	/**
	 * 下载电子保单
	 */
	public Map<String, Object> downloadByOrderSn1(Map<String, Object> map) {
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		String orderSn = String.valueOf(map.get("orderSn"));
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.OrderSn, a.policyNo, a.riskName, a.riskCode, a.electronicPath,b.recognizeeName ");
		sql.append("from sdinformationrisktype a ,sdinformationinsured b ");
		sql.append("where a.appStatus = '1' and a.recognizeeSn=b.recognizeeSn ");
		sql.append(" and a.orderSn like '%" + orderSn.trim() + "%'");
		sql.append(" order by a.id desc ");
		SDInformationRiskTypeSchema sdInformationRiskTypeSchema = new SDInformationRiskTypeSchema();
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executePagedDataTable(0, 0);
		List<File> listFile = new ArrayList<File>();
		List<String> listFileName = new ArrayList<String>();
		String pdfDownload_delay = "";
		
		int num = dt.getRowCount();
		SDOrderItemOthSchema sdOrderItemOth = new SDOrderItemOthSchema();
		SDOrderItemOthSet sdOrderItemOthSet = new SDOrderItemOthSet();
		sdOrderItemOthSet = sdOrderItemOth.query(new QueryBuilder("where OrderSn=?", orderSn));
		if(sdOrderItemOthSet != null && sdOrderItemOthSet.size() > 0){
			sdOrderItemOth = sdOrderItemOthSet.get(0);
			if(StringUtil.isNotEmpty(sdOrderItemOth.getGroupType()) && "g".equals(sdOrderItemOth.getGroupType())){
				num = 1;
			}
		}
		// req20170621801-美行保用户退保修改-6 装载错误信息用  errorMessage
		StringBuffer errorMessage = new StringBuffer();
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < num; i++) {
				String recognizeeName = (String) dt.get(i).get("recognizeeName");// 被保人姓名
				String pno = (String) dt.get(i).get("policyNo");// 保单号
				String filename = recognizeeName + "-" + pno;
				if(StringUtil.isNotEmpty(sdOrderItemOth.getGroupType()) && "g".equals(sdOrderItemOth.getGroupType())){
					filename = orderSn;//团险文件名为订单号
				}
				if (!StringUtil.isEmpty(dt.get(i).get("electronicPath"))) {
					listFile.add(
							
							new File(Config.getValue("newPolicyPath")
									+ replacePath((String) dt.get(i).get(
											"electronicPath"))));
					
					String path = (String) dt.get(i).get("electronicPath");// 真实地址
					String exname = path.split("\\.")[path.split("\\.").length - 1];// 扩展名
					String fp = filename + "." + exname;
					listFileName.add(fp);
				} else {// 更新，下载订单下保单
					String riskCode = (String) dt.get(i).get("riskCode");
					String policyNo = (String) dt.get(i).get("policyNo");
					try {
						Map<String, String> param = new HashMap<String, String>();
						param.put("comCode", riskCode.substring(0, 4));
						param.put("policyNo", policyNo);
						Map<String, String> resultMap = pdfDownload(param);
						// req20170621801-美行保用户退保修改-6  "N".equals(resultMap.get("Success")
						if (!"Y".equals(resultMap.get("Success"))) {
							results.put("Status", 0);
							results.put("Msg", "电子保单下载失败 ！调用金带通电子保单下载异常！");
							/*return results;*/
							errorMessage.append(" 被保人 ："+ recognizeeName + "电子保单下载失败");
							continue;
						} else {
							sdInformationRiskTypeSchema.setId((String) dt
									.get(i).get("id"));
							if (sdInformationRiskTypeSchema.fill()) {
								if (sdInformationRiskTypeSchema
										.getelectronicPath() != null
										&& sdInformationRiskTypeSchema
												.getelectronicPath() != "") {
									listFile.add(
											new File(Config.getValue("newPolicyPath")
													+ replacePath(sdInformationRiskTypeSchema
															.getelectronicPath())));
									String exname = sdInformationRiskTypeSchema
											.getelectronicPath().split("\\.")[sdInformationRiskTypeSchema
																				.getelectronicPath().split("\\.").length - 1];// 扩展名
									String fp = filename +"."+exname;
									listFileName.add(fp);
								} else {// 下载pdf可能存在延迟
									pdfDownload_delay = "delay";
									errorMessage.append(" 被保人 ："+ recognizeeName + "保单下载服务繁忙，请稍候重试！");
									continue;
								}
							} else {
								results.put("Status", 0);
								results.put("Msg", "电子保单下载失败！调用径代通电子保单下载异常！");
								/*return results;*/
								errorMessage.append(" 被保人 ："+ recognizeeName + "电子保单下载失败");
								continue;
							}
						}
					} catch (Exception e) {
						logger.error("=====保单(" + riskCode + ")电子保单下载功能异常===" + e.getMessage(), e);
						results.put("Status", 0);
						results.put("Msg", "网络繁忙，请稍候重试！");
						return results;
					}
				}
			}
		}
		/*if ("delay".equals(pdfDownload_delay)) {
			results.put("Status", 0);
			results.put("Msg", "保单下载服务繁忙，请稍候重试！");
			return results;
		}*/
		if (listFile.size() > 0) {
			String policyPath = elecPolicydownload(orderSn, listFile,
					listFileName);
			if (StringUtil.isEmpty(policyPath)) {
				results.put("Status", 0);
				results.put("Msg", "电子保单下载失败 ！");
				return results;
			} else if ("IsException".equals(policyPath)) {
				results.put("Status", 0);
				results.put("Msg", "订单号" + orderSn + "下载异常！");
				return results;
			} else {
				results.put("Status", 1);
				results.put("OrderPath", policyPath);
				results.put("FileName", orderSn + ".rar");
				if (StringUtil.isNotEmpty(errorMessage.toString())) {
					results.put("Msg", errorMessage.toString());
				}else {
					results.put("Msg", "成功");
				}
				return results;
			}
		} else {
			results.put("Status", 0);
			results.put("Msg", "订单号" + orderSn + "下载失败！");
			return results;
		}
	}

	/**
	 * @Title: elecPolicydownload.
	 * @Description: (按订单压缩电子保单).
	 */
	public String elecPolicydownload(String p_Ordersn, List<File> listFile,
			List<String> listFileName) {
		String resultPath = "";
		String tFolderOutPath = "";
		try {
			String fileName = p_Ordersn + ".zip";
			String filePath = Config.getValue("PolicyFilePath");
			String date = DateUtil.getCurrentDate("yyyyMM");

			// 电子保单压缩保存路径
			tFolderOutPath = filePath + File.separator + "EPolicy"
					+ File.separator + "WJ" + File.separator
					+ date.substring(0, 4) + File.separator + date.substring(4)
					+ File.separator;

			// 路径是否存在，不存在则创建路径
			File newfilepatch = new File(tFolderOutPath);
			if (!newfilepatch.exists()) {
				newfilepatch.mkdirs();
			}

			String zipFilePath = tFolderOutPath + fileName;
			File zipfile = new File(zipFilePath);

			resultPath = zipFilePath;

			try {
				zipFiles(listFile, zipfile, listFileName);
			} catch (IOException e) {
				logger.error("电子保单下载异常,生成压缩文件异常" + e.getMessage(), e);
				return "IsException";
			}
		} catch (Exception e) {
			logger.error("电子保单下载异常 ordersn:" + p_Ordersn + e.getMessage(), e);
			return "IsException";
		}
		return resultPath;
	}

	/**
	 * @Title: zipFiles.
	 * @Description: TODO(压缩文件).
	 * @param srcfile
	 *            .
	 * @param zipfile
	 *            void.
	 * @author CongZN.
	 * @throws IOException
	 */
	public static void zipFiles(List<File> listFile, File zipfile,
			List<String> listFileName) throws IOException {
		byte[] buf = new byte[1024];

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
		for (int i = 0; i < listFile.size(); i++) {
			FileInputStream in = new FileInputStream(listFile.get(i));
			if (!StringUtil.isEmpty(listFileName.get(i))) {
				out.putNextEntry(new ZipEntry(listFileName.get(i).toString()));
			} else {
				out.putNextEntry(new ZipEntry(listFile.get(i).getName()));
			}
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
				out.setEncoding("gbk");
			}
			out.closeEntry();
			in.close();
		}
		out.close();
	}

	/**
	 * 保单路径替换.
	 * 
	 * @param oldPath
	 * @return
	 */
	public static String replacePath(String oldPath) {
		String newPath = "";
		if (!StringUtil.isEmpty(oldPath)) {
			String serverPath = Config.getValue("newPolicyPath");
			newPath = oldPath.replace(serverPath, "");
		}

		return newPath;
	}

	/**
	 * 修改订单信息
	 * 
	 * @return
	 */
	public void editOrders() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		InputStream inputStream = null;
		BufferedReader reader = null;
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			String strResponse = "";

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));

			while (StringUtil.isNotEmpty(strMessage = reader.readLine())) {
				strResponse += strMessage;
			}
			JSONObject basicOrders = JSONObject.fromObject(strResponse);
			JSONObject basicOrderInfo = JSONObject.fromObject(basicOrders
					.get("basicOrderInfo"));
			String orderSn = basicOrderInfo.getString("orderSn");
			String orderStatus = basicOrderInfo.getString("orderStatus");
			String recognizeeSn = basicOrderInfo.getString("recognizee_Sn");
			Double paidAmount = Double.valueOf(basicOrderInfo
					.getString("payAmount"));
			Double productTotalPrice = Double.valueOf(basicOrderInfo
					.getString("productTotalPrice"));
			Double totalAmount = Double.valueOf(basicOrderInfo
					.getString("totalAmount"));
			String policyNumber = basicOrderInfo.getString("policyNumber");
			String id = basicOrderInfo.getString("order_id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderSn", orderSn);
			map.put("orderStatus", orderStatus);
			map.put("recognizee_Sn", recognizeeSn);
			map.put("payAmount", paidAmount);
			map.put("productTotalPrice", productTotalPrice);
			map.put("totalAmount", totalAmount);
			map.put("policyNumber", policyNumber);
			map.put("order_id", id);

			try {
				results = editOrders1(map);
			} catch (Exception e) {
				logger.error("订单修改接口异常." + e.getMessage(), e);
			}
			response.getWriter().print(
					JSONObject.fromObject(results).toString());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 修改订单信息
	 * */
	public Map<String, Object> editOrders1(Map<String, Object> map) {
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		// String createDate = $V("createDate");
		String orderSn = String.valueOf(map.get("orderSn"));
		String recognizeeSn = String.valueOf(map.get("recognizee_Sn"));
		try {
			GetDBdata db = new GetDBdata();

			String status = db
					.getOneValue("select orderStatus from sdorders where orderSn='"
							+ orderSn + "'");
			if ("7".equals(status)) {
				results.put("Status", 0);
				results.put("Msg", "已支付状态不允许修改！");
				return results;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Double paidAmount = Double
				.valueOf(String.valueOf(map.get("payAmount")));
		// Double paymentFee = Double.valueOf($V("paymentFee"));

		Double productTotalPrice = Double.valueOf(String.valueOf(map
				.get("productTotalPrice")));
		Double totalAmount = Double.valueOf(String.valueOf(map
				.get("totalAmount")));
		String policyNumber = String.valueOf(map.get("policyNumber"));// 加入保单号
																		// add
																		// by
																		// fhz
																		// 20121210
		SDOrdersSchema order = new SDOrdersSchema();
		SDInformationRiskTypeSchema insured = new SDInformationRiskTypeSchema();
		GetDBdata db = new GetDBdata();
		String id = String.valueOf(map.get("order_id"));
		order.setid(id);
		if (order.fill()) {
			// order.setorderSn(orderSn);
			order.setorderStatus("4");
			order.setpayAmount(String.valueOf(paidAmount));
			// order.setpaymentFee(BigDecimal.valueOf(paymentFee));
			order.setproductTotalPrice(String.valueOf(productTotalPrice));
			order.settotalAmount(String.valueOf(totalAmount));
			order.setmodifyDate(new Date());
			try {
				String sql = "select id from SDInformationRiskType where orderSn = '"
						+ order.getorderSn()
						+ "' and recognizeeSn='"
						+ recognizeeSn + "'";
				String result = db.getOneValue(sql);
				if (StringUtils.isNotEmpty(result)) {
					insured.setId(result);
					if (insured.fill()) {
						insured.setpolicyNo(policyNumber);
						insured.update();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (order.update()) {
				results.put("Status", 1);
				results.put("Msg", "修改成功");

			} else {
				results.put("Status", 0);
				results.put("Msg", "修改" + orderSn + "失败!");
			}
		}
		return results;
	}

	/**
	 * 修改订单信息条目
	 * 
	 * @return
	 */
	public void editOrderItemUpdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		InputStream inputStream = null;
		BufferedReader reader = null;
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			String strResponse = "";

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));

			while (StringUtil.isNotEmpty(strMessage = reader.readLine())) {
				strResponse += strMessage;
			}
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			JSONArray basicOrders = JSONArray.fromObject(strResponse);
			list = (List) JSONArray.toCollection(basicOrders, Map.class);

			try {
				results = editOrderItemUpdate1(list);
			} catch (Exception e) {
				logger.error("订单修改接口异常." + e.getMessage(), e);
			}
			response.getWriter().print(
					JSONObject.fromObject(results).toString());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 修改订单条目
	 * */
	public Map<String, Object> editOrderItemUpdate1(List<Map<String, String>> dt) {

		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();

		try {
			boolean sendTaobaoFlag = false;
			for (int i = 0; i < dt.size(); i++) {

				sendTaobaoFlag = false;

				SDInformationRiskTypeSchema insured = new SDInformationRiskTypeSchema();
				insured.setId(dt.get(i).get("baodanID"));
				if (insured.fill()) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					// 淘宝的订单
					if (!StringUtil.isEmpty(insured.getorderSn())
							&& insured.getorderSn().toUpperCase().indexOf("TB") == 0) {
						if (insured.getoperationflag() < Integer.valueOf(Config
								.getValue("operationflag"))) {
							// 保单号插入时掉出单回调
							if (!StringUtil.isEmpty(dt.get(i).get("policyNo"))) {
								sendTaobaoFlag = true;
								insured.setoperationflag(insured
										.getoperationflag() + 1);
							}
						} else {
							if (!dt.get(i).get("policyNo")
									.equals(insured.getpolicyNo())) {
								results.put("Status", 0);
								results.put("Msg", "已达到操作上限");
								return results;
							}
						}
					}

					insured.setpolicyNo(dt.get(i).get("policyNo"));
					insured.setsvaliDate(sdf.parse(dt.get(i).get("svaliDate")));
					insured.setevaliDate(sdf.parse(dt.get(i).get("evaliDate")
							.substring(0, 10)
							+ " 23:59:59"));
					insured.setmodifyDate(new Date());
					if (insured.update()) {
						// add by wangej 20130904 更新SDOrders修改时间
						editOrdersDate(insured.getorderSn());
						results.put("Status", 1);
						results.put("Msg", "修改成功");
						// 出单回调
						if (sendTaobaoFlag) {
							String result = transformXMLToString(reponseResultTB(insured));
							taoBaoAsync(result, "?comId=" + comId);
						}
					} else {
						results.put("Status", 0);
						results.put("Msg", "修改失败!");
					}
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			results.put("Status", 0);
			results.put("Msg", "修改失败!");
		}
		return results;
	}

	/**
	 * 投被保人修改
	 * 
	 * @return
	 */
	public void modifyAppRec() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		InputStream inputStream = null;
		BufferedReader reader = null;
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			String strResponse = "";

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));

			while (StringUtil.isNotEmpty(strMessage = reader.readLine())) {
				strResponse += strMessage;
			}
			Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
			JSONObject basicOrders = JSONObject.fromObject(strResponse);
			JSONObject basicApp = JSONObject.fromObject(basicOrders.get("app"));
			JSONObject basicRec = JSONObject.fromObject(basicOrders.get("rec"));
			Map<String, Object> map1 = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map1 = getMap(basicApp);
			map2 = getMap(basicRec);
			map.put("app", map1);
			map.put("rec", map2);

			try {
				results = dg1Edit4(map);
			} catch (Exception e) {
				logger.error("订单修改接口异常." + e.getMessage(), e);
			}
			response.getWriter().print(
					JSONObject.fromObject(results).toString());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public Map<String, Object> getMap(JSONObject basicApp) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		Iterator<String> it = basicApp.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = (String) basicApp.get(key);
			map1.put(key, value);
		}
		return map1;
	}

	/**
	 * 投被保人修改
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> dg1Edit4(Map<String, Map<String, Object>> map) {

		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		String id_appnt = String.valueOf(map.get("app").get("id_appnt"));
		// 投保人
		SDInformationAppntSchema informationAppntSchema = new SDInformationAppntSchema();
		// informationSchema.setValue(Request);
		informationAppntSchema.setId(id_appnt);
		informationAppntSchema.fill();
		/* informationAppntSchema.setValue(Request); */
		// 投保人姓名
		informationAppntSchema.setapplicantName(String.valueOf(map.get("app")
				.get("applicantName")));
		// 投保人性别
		informationAppntSchema.setapplicantSex(String.valueOf(map.get("app")
				.get("applicantSex")));
		// 投保电话
		informationAppntSchema.setapplicantMobile(String.valueOf(map.get("app")
				.get("applicantMobile")));
		// 投保生日
		informationAppntSchema.setapplicantBirthday(String.valueOf(map.get(
				"app").get("applicantBirthday")));
		// 投保证件类型
		informationAppntSchema.setapplicantIdentityType(String.valueOf(map.get(
				"app").get("applicantIdentityType")));
		// 投保证件号
		informationAppntSchema.setapplicantIdentityId(String.valueOf(map.get(
				"app").get("applicantIdentityId")));
		// 投保邮箱
		informationAppntSchema.setapplicantMail(String.valueOf(map.get("app")
				.get("applicantMail")));

		String insuranceCompany = "";
		GetDBdata db0 = new GetDBdata();
		try {
			insuranceCompany = db0
					.getOneValue("select insuranceCompany from SDInformation where informationSn='"
							+ informationAppntSchema.getinformationSn() + "'");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// 将性别名称,证件名称同步修改 ---ysc
		GetDBdata db = new GetDBdata();
		try {
			String aResult = db
					.getOneValue("select codeName from dictionary where codeType = 'Sex' and codeValue = '"
							+ informationAppntSchema.getapplicantSex()
							+ "' and insuranceCode ='" + insuranceCompany + "'");
			String aResult1 = db
					.getOneValue("select codeName from dictionary where codeType = 'certificate' and codeValue = '"
							+ informationAppntSchema.getapplicantIdentityType()
							+ "' and insuranceCode ='" + insuranceCompany + "'");
			informationAppntSchema.setapplicantSexName(aResult);
			informationAppntSchema.setapplicantIdentityTypeName(aResult1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		informationAppntSchema.setId(id_appnt);

		// 被保人
		String id_insured = String.valueOf(map.get("rec").get("id_insured"));
		SDInformationInsuredSchema informationinsuredSchema = new SDInformationInsuredSchema();
		informationinsuredSchema.setId(id_insured);
		informationinsuredSchema.fill();
		/* informationinsuredSchema.setValue(Request); */
		// 被保人名
		informationinsuredSchema.setrecognizeeName(String.valueOf(map
				.get("rec").get("recognizeeName")));
		// 被保人性别
		informationinsuredSchema.setrecognizeeSex(String.valueOf(map.get("rec")
				.get("recognizeeSex")));
		// 被保人电话
		informationinsuredSchema.setrecognizeeMobile(String.valueOf(map.get(
				"rec").get("recognizeeMobile")));
		// 被保人生日
		informationinsuredSchema.setrecognizeeBirthday(String.valueOf(map.get(
				"rec").get("recognizeeBirthday")));
		// 被保人证件类型
		informationinsuredSchema.setrecognizeeIdentityType(String.valueOf(map
				.get("rec").get("recognizeeIdentityType")));
		// 被保人证件号
		informationinsuredSchema.setrecognizeeIdentityId(String.valueOf(map
				.get("rec").get("recognizeeIdentityId")));
		// 被保人邮箱
		informationinsuredSchema.setrecognizeeMail(String.valueOf(map
				.get("rec").get("recognizeeMail")));

		// 将性别名称,证件名称同步修改 ---ysc
		GetDBdata db1 = new GetDBdata();
		try {
			String rResult = db1
					.getOneValue("select codeName from dictionary where codeType = 'Sex' and codeValue = '"
							+ informationinsuredSchema.getrecognizeeSex()
							+ "' and insuranceCode ='" + insuranceCompany + "'");
			String rResult1 = db1
					.getOneValue("select codeName from dictionary where codeType = 'certificate' and codeValue = '"
							+ informationinsuredSchema
									.getrecognizeeIdentityType()
							+ "' and insuranceCode ='" + insuranceCompany + "'");
			informationinsuredSchema.setrecognizeeSexName(rResult);
			informationinsuredSchema.setrecognizeeIdentityTypeName(rResult1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		informationinsuredSchema.setId(id_insured);

		if (informationAppntSchema.update()
				&& informationinsuredSchema.update()) {
			// add by wangej 20130904 更新SDOrders修改时间
			editOrdersDate(informationinsuredSchema.getorderSn());
			results.put("Status", 1);
			results.put("Msg", "修改成功");
		} else {
			results.put("Status", 0);
			results.put("Msg", "修改" + informationAppntSchema.getId() + "失败!");
		}
		return results;
	}

	/**
	 * 美亚出单回调
	 * 
	 * @param returnXml
	 *            请求报文
	 * @param param
	 *            GET参数
	 * @throws IOException
	 */
	public void taoBaoAsync(String returnXml, String param) throws IOException {
		HttpClient httpClient = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		String url = Config.interfaceMap
				.getString("ProductCenterAccessServlet");
		PostMethod post = new PostMethod(url + param);
		InputStream in_tb = null;
		try {
			in_tb = new ByteArrayInputStream(returnXml.getBytes("GBK"));
			post.setRequestBody(in_tb);
			httpClient.executeMethod(post);
		} catch (IOException e) {
			throw e;
		} finally {
			post.releaseConnection();
			if (in_tb != null) {
				in_tb.close();
			}
		}
	}

	// 将DOC转换成str方法
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

	/**
	 * 获得出单回调的请求数据
	 * 
	 * @return 请求数据
	 * @throws Exception
	 */
	private Document reponseResultTB(SDInformationRiskTypeSchema insured)
			throws Exception {
		String currentDate = DateUtil.getCurrentDate();
		String currentTime = DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
		Element rootPackageList = new Element("PackageList");
		Element rootPackage = new Element("Package");
		Document doc = new Document(rootPackageList); // 要创建的XML文档 - doc

		// 取得唯一编码
		String sql = "select * from SDOrders where orderSn = ?";
		String sqltemp[] = { insured.getorderSn() };
		List<HashMap<String, String>> list = new GetDBdata()
				.query(sql, sqltemp);
		// 唯一编码
		String uuId = "";
		// 淘宝交易流水号
		String taoBaoSerial = "";
		if (list.size() > 0) {
			uuId = list.get(0).get("remark");
			comId = list.get(0).get("tbComCode");
			taoBaoSerial = list.get(0).get("tbTradeSeriNo");
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
		ComSerialElement.setText(insured.getorderSn());
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
		PolicyNoElement.setText(insured.getpolicyNo());
		PolicyElement.addContent(PolicyNoElement);

		// 投保单号(必填)
		Element ProposalNoElement = new Element("ProposalNo");
		ProposalNoElement.setText(insured.getorderSn());
		PolicyElement.addContent(ProposalNoElement);

		// 总保费(必填)
		Element TotalPremiumElement = new Element("TotalPremium");
		if (!StringUtil.isEmpty(insured.gettimePrem())) {
			// 以分为单位
			TotalPremiumElement.setText(String.valueOf((int) (Double
					.parseDouble(insured.gettimePrem()) * 100)));
		}

		PolicyElement.addContent(TotalPremiumElement);

		// 出单是否成功(必填)
		Element IsSuccessElement = new Element("IsSuccess");
		IsSuccessElement.setText("1");
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

		return doc;
	}

	/**
	 * 修改订单信息 update moddate by wangej 20130904
	 * 
	 * */
	public void editOrdersDate(String orderSn) {

		GetDBdata db = new GetDBdata();
		String updateSql = "update SDOrders set modifyDate = SYSDATE()  where orderSn = '"
				+ orderSn + "'";
		try {
			db.execUpdateSQL(updateSql, null);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 修改页的详细
	 * 
	 * @param params
	 * @return
	 */
	public Mapx initDialog2(Mapx params) {
		// 查看订单信息-- 购买优惠信息&订单基本信息
		QueryOrders qo = new QueryOrders();
		// 返回结果
		Mapx map = qo.initDialog2(params);
		return map;
	}

	/**
	 * 保单一键变更操作
	 */
	public Map<String, Object> policyChange(Map<String, Object> map) {

		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		SDInformationRiskTypeSet risktypeSet = new SDInformationRiskTypeSet();
		List<SDInformationRiskTypeSchema> risktypeList = new ArrayList<SDInformationRiskTypeSchema>();
		SDInformationRiskTypeSchema risktype = new SDInformationRiskTypeSchema();
		SDInformationInsuredSet insuredSet = new SDInformationInsuredSet();
		List<SDInformationInsuredSchema> insuredList = new ArrayList<SDInformationInsuredSchema>();
		SDInformationInsuredSchema insured = new SDInformationInsuredSchema();
		SDInformationDutySchema duty = new SDInformationDutySchema();
		SDInformationDutySet dutySet = new SDInformationDutySet();
		SDInformationDutySchema duty_change = new SDInformationDutySchema();
		SDInformationDutySet dutyset_change = new SDInformationDutySet();
		// 变更记录
		List<PolicyChangeInfoSchema> changeInfoList = new ArrayList<PolicyChangeInfoSchema>();
		// 被保人变更记录
		Map<String, List<PolicyChangeInfoSchema>> insuredChangeInfoMap = new HashMap<String, List<PolicyChangeInfoSchema>>();
		Date now = new Date();
		// 订单表ID
		String orderId = String.valueOf(map.get("id"));
		// 订单号
		String orderSn = String.valueOf(map.get("orderSn"));
		// 订单信息表ID
		String informationId = String.valueOf(map.get("informationId"));
		// 投保人ID
		String applicantId = String.valueOf(map.get("applicantId"));
		// 保险起期
		String svalidate = String.valueOf(map.get("svalidate"));
		String svalitime = String.valueOf(map.get("svalitime"));
		// 保险止期
		String evaliDate = String.valueOf(map.get("evaliDate"));

		String NDestination = String.valueOf(map.get("NDestination"));
		// 优惠购买费率
		String rate = String.valueOf(map.get("rate"));

		String destination = String.valueOf(map.get("destination"));
		// 自动、指定投保人
		String appntType = String.valueOf(map.get("appntType"));
		// 操作人
		String loginName = String.valueOf(map.get("loginName"));
		// 保单信息
		DataTable dataDT = (DataTable) map.get("Data");
		// 健康告知信息
		DataTable healDT = (DataTable) map.get("healData");

		/* 校验参数是否有值 */
		if (StringUtil.isEmpty(orderId)) {
			results.put("Status", 0);
			results.put("Msg", "订单表ID为空，不能变更！");
			return results;
		}
		if (StringUtil.isEmpty(orderSn)) {
			results.put("Status", 0);
			results.put("Msg", "订单号为空，不能变更！");
			return results;
		}
		if (StringUtil.isEmpty(informationId)) {
			results.put("Status", 0);
			results.put("Msg", "订单信息表ID为空，不能变更！");
			return results;
		}
		if (StringUtil.isEmpty(applicantId)) {
			results.put("Status", 0);
			results.put("Msg", "投保信息ID为空，不能变更！");
			return results;
		}
		if (dataDT == null || dataDT.getRowCount() < 1) {
			results.put("Status", 0);
			results.put("Msg", "无保单信息，不能变更！");
			return results;
		}
		// 取得订单表信息
		SDOrdersSchema sdorder = new SDOrdersSchema();
		sdorder.setid(orderId);
		if (!sdorder.fill()) {
			results.put("Status", 0);
			results.put("Msg", "数据库中订单表中不存在订单信息！");
			return results;
		}
		String oldPaySn = sdorder.getpaySn();
		if (StringUtil.isEmpty(oldPaySn)) {
			results.put("Status", 0);
			results.put("Msg", "数据库中订单表中交易流水号为空！");
			return results;
		}

		// 取得订单基础信息
		SDInformationSchema information = new SDInformationSchema();

		information.setId(informationId);
		if (!information.fill()) {
			results.put("Status", 0);
			results.put("Msg", "数据库中不存在订单基础信息！");
			return results;
		}
		/*int tLen = healDT.getRowCount();
		if (healDT != null && tLen >= 1) {
			if (!"1015".equals(information.getinsuranceCompany())) {
				for (int l = 0; l < tLen; l++) {
					if ("Y".equals(healDT.getString(l, "selectFlag"))) {
						results.put("Status", 0);
						results.put("Msg", "健康告知第" + (l + 1)
								+ "条选项（Y）不符合投保要求，请重新选择！");
						return results;
					}
				}
			}
		}*/
		String company = information.getinsuranceCompany();
		String productid = information.getproductId();
		boolean isChange = false;
		// 是否修改起保日期标识
		boolean changePreiod = false;
		Date startDate = DateUtil.parse((svalidate + " " + svalitime),
				"yyyy-MM-dd HH:mm:ss");
		if (startDate.compareTo(information.getstartDate()) != 0) {
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("01");
			schema.setbeforeValue(information.getstartDate().toString());
			schema.setafterValue(svalidate + " " + svalitime);
			changeInfoList.add(schema);
			information.setstartDate(startDate);
			information.setendDate(DateUtil.parse(evaliDate,
					"yyyy-MM-dd HH:mm:ss"));
			changePreiod = true;
			isChange = true;
		}

		// 起保日期的校验
		String message = checkPeriod(information.getproductId(), (svalidate
				+ " " + svalitime), evaliDate);
		if (StringUtil.isNotEmpty(message)) {
			results.put("Status", 0);
			results.put("Msg", message);
			return results;
		}
		// 旅游目的校验
		QueryBuilder qb_des = new QueryBuilder(
				"SELECT destinationCountry,destinationCountryText FROM sdinformationinsured WHERE ordersn=? ");
		qb_des.add(orderSn);
		DataTable dt_des = qb_des.executeDataTable();
		String destinationCountrySelect = "";
		String destinationCountryTextSelect = "";
		String destinationCountry = "";
		String destinationCountryText = "";
		boolean changeDetionationSelect = false;
		if (dt_des.getRowCount() > 0) {
			destinationCountrySelect = dt_des.get(0).getString(0);
			destinationCountryTextSelect = dt_des.get(0).getString(1);
			destinationCountry = destinationCountrySelect;
			destinationCountryText = destinationCountryTextSelect;
			changeDetionationSelect = true;
			//modify by cuishigang 如果原单有旅游目的地，则变更后的订单与原单旅游目的地一直
			destination = destinationCountrySelect;
			NDestination = destinationCountryTextSelect;
					
		}

		if (StringUtil.isNotEmpty(destinationCountrySelect)) {
			if (StringUtil.isEmpty(destination)
					&& StringUtil.isEmpty(NDestination)) {
				results.put("Status", 0);
				results.put("Msg", "旅游目的地不能为空");
				return results;
			}

		}
		Map<String, String> destionationinfo = new HashMap<String, String>();
		boolean changeDetionation = false;
		QueryBuilder qbScope = new QueryBuilder(
				"SELECT codevalue FROM zdcode WHERE codetype='singleDestination' AND parentcode='singleDestination'");
		DataTable dtScope = qbScope.executeDataTable();
		boolean singleFlag = false;
		if (dtScope != null && dtScope.getRowCount() > 0) {
			for (int j = 0; j < dtScope.getRowCount(); j++) {
				if (dtScope.getString(j, "codevalue").equals(company)) {
					singleFlag = true;
					break;
				}
			}
		}
		if (changeDetionationSelect) {
			if (singleFlag && StringUtil.isNotEmpty(destinationCountrySelect)) {
				if (!destinationCountrySelect.equals(destination)) {
					destinationCountry = destination;
					destinationCountryText = QueryOrders
							.getCodeNameByCodeValue(company, "CountryCode",
									destination, productid);
					changeDetionation = true;
					isChange = true;
				}
			} else {
				if (StringUtil.isNotEmpty(NDestination)) {
					String[] destinationnameStr = NDestination.split(",");
					String destinationvalue = "";
					for (int i = 0; i < destinationnameStr.length; i++) {
						if (i == 0) {
							destinationvalue += QueryOrders
									.getCodeValueByCodeName(productid, company,
											"CountryCode",
											destinationnameStr[i]);
						} else {
							destinationvalue += ","
									+ QueryOrders.getCodeValueByCodeName(
											productid, company, "CountryCode",
											destinationnameStr[i]);
						}
					}
					if (StringUtil.isNotEmpty(destinationvalue)) {
						String[] destinationvalue_temp = destinationvalue
								.split(",");
						String[] destinationCountrySelect_temp = destinationCountrySelect
								.split(",");
						boolean diffflagN = false;
						if (destinationvalue_temp.length != destinationCountrySelect_temp.length) {
							diffflagN = false;
						} else {
							for (int i = 0; i < destinationvalue_temp.length; i++) {
								diffflagN = false;
								for (int j = 0; j < destinationCountrySelect_temp.length; j++) {
									if (destinationvalue_temp[i]
											.equals(destinationCountrySelect_temp[j])) {
										diffflagN = true;
										break;
									}
								}
							}
						}
						if (!diffflagN) {
							changeDetionation = true;
							isChange = true;
						}
						if (changeDetionation) {
							destinationCountry = destinationvalue;
							destinationCountryText = QueryOrders
									.setDestinationCountry(company,
											destinationCountry);
							if ("2007".equals(company)
									&& destinationCountry != null
									&& !"".equals(destinationCountry)) {
								if (destinationCountry != null
										&& !"".equals(destinationCountry)) {
									String CountryText = QueryOrders
											.getCountryText2007("2007",
													destinationCountry);
									destinationCountryText = CountryText;
								}
							} else if ("1015".equals(company)
									&& destinationCountry != null
									&& !"".equals(destinationCountry)) {

								destinationCountryText = QueryOrders
										.getCountryText1015("1015",
												destinationCountry);
							} else if (("2011".equals(company)
									|| "2023".equals(company) || "2071"
										.equals(company))
									&& destinationCountry != null
									&& !"".equals(destinationCountry)) {
								destinationCountryText = QueryOrders
										.getSchengenCountryText(company,
												destinationCountry, productid);
							}
						}

					}
				}
			}
			destionationinfo.put("destinationCountry", destinationCountry);
			destionationinfo.put("destinationCountryText",
					destinationCountryText);
		}

		// 取得该产品的证件类型
		Map<String, String> certificateInfo = getCertificateSelect(information
				.getproductId());
		// 取得该产品的性别信息
		Map<String, String> sexInfo = getSexSelect(information.getproductId());
		// 取得该产品的投被保人关系
		Map<String, String> relationInfo = QueryOrders.getRelationSelect(
				information.getinsuranceCompany(), information.getproductId());
		// 取得投保人信息
		SDInformationAppntSchema appnt = new SDInformationAppntSchema();
		appnt.setId(applicantId);
		if (!appnt.fill()) {
			results.put("Status", 0);
			results.put("Msg", "数据库中不存在投保信息！");
			return results;

		}
		// 投被保人信息变更标识
		boolean appChange = false ;
		if(!"1".equals(appntType)){
			/* 对比投保人信息是否修改 */
			appChange = checkAppntChange(changeInfoList, appnt,
					certificateInfo, sexInfo, map);
		}
		// 校验保单是否承保成功
		message = checkAppstatus(appChange, changePreiod, dataDT,
				changeDetionation);
		// 有错误信息提示
		if (StringUtil.isNotEmpty(message)) {
			results.put("Status", 0);
			results.put("Msg", message);
			return results;
		}
		if (!isChange && appChange) {
			isChange = true;
		}
		// 取得渠道编码
		String channelCode = new QueryBuilder(
				"select channelCode from sdorderitem where orderSn=?", orderSn)
				.executeString();

		SDOrderServiceImpl service = new SDOrderServiceImpl();
		Map<String, Object> paramter1 = new HashMap<String, Object>();
		Map<String, Object> insuredPremMap = new HashMap<String, Object>();
		// 责任信息
		Map<String, String> dutyJson = new HashMap<String, String>();
		// 取得责任信息
		duty.setorderSn(orderSn);
		dutySet = duty.query();
		if (dutySet != null && dutySet.size() > 0) {
			int dutySize = dutySet.size();
			for (int i = 0; i < dutySize; i++) {
				dutyJson.put(dutySet.get(i).getdutySn(), dutySet.get(i)
						.getamt());
			}
		}

		// 取得限购份数和产品类型
		String sql = "SELECT LimitCount,ProductType FROM sdproduct WHERE productid=? AND limitcount IS NOT NULL AND limitcount <> ''";
		DataTable dt = new QueryBuilder(sql, information.getproductId())
				.executeDataTable();
		int limitCount = 0;
		String productType = "";
		if (dt != null && dt.getRowCount() > 0) {
			limitCount = dt.getInt(0, 0);
			productType = dt.getString(0, 1);
		}

		// 变更后保险公司折后订单总金额
		BigDecimal totalAmount = new BigDecimal(0);
		// 变更后原价订单总金额
		BigDecimal sumProductPrem = new BigDecimal(0);
		// 变更后实际支付总金额
		BigDecimal sumPayPrem = new BigDecimal(0);
		Map<String, Boolean> baodanMap = new HashMap<String, Boolean>();
		SDInformationInsuredSchema insuredTemp = new SDInformationInsuredSchema();
		int rowcount = dataDT.getRowCount();
		for (int i = 0; i < rowcount; i++) {
			// 对选中的保单取得相关的保单表信息和被保人表信息
			if ("1".equals(dataDT.getString(i, "checkflag"))
					&& "1".equals(dataDT.getString(i, "appFlag"))) {

				// 取得保单表信息
				risktype = new SDInformationRiskTypeSchema();
				risktype.setId(dataDT.getString(i, "baodanId"));
				if (!risktype.fill()) {
					results.put("Status", 0);
					results.put("Msg", "第" + (i + 1) + "行的保单信息在数据库中不存在，不能变更！");
					return results;
				}
				risktypeList.add(risktype);

				if (!"0".equals(risktype.getbalanceStatus())
						&& "1".equals(risktype.getappStatus())) {
					results.put("Status", 0);
					results.put("Msg", "保单：" + risktype.getpolicyNo()
							+ "未结算，不能变更！");
					return results;
				}
				if ("2".equals(risktype.getappStatus())
						|| "4".equals(risktype.getappStatus())) {
					results.put("Status", 0);
					results.put("Msg", "保单：" + risktype.getpolicyNo()
							+ "已撤单，不能变更！");
					return results;
				}
				// 取得被保人表信息
				insured = new SDInformationInsuredSchema();
				insured.setId(risktype.getsdinformationinsured_id());

				if (!insured.fill()) {
					results.put("Status", 0);
					results.put("Msg", "第" + (i + 1) + "行的被保人信息在数据库中不存在，不能变更！");
					return results;
				}
				
				// 被保人出生日期是否修改
				boolean birthdayChange = false;
				boolean baodanChange = false;
				// 不是本人的情况
				if ("1".equals(appntType) || !"本人".equals(relationInfo.get(dataDT.getString(i, "recognizeeAppntRelation")))) {
					// 校验被保人信息是否修改
					baodanMap = checkInsuredChange(insuredChangeInfoMap,
							insured, certificateInfo, sexInfo, relationInfo,
							dataDT.get(i), changeDetionation, destionationinfo);
					baodanChange = baodanMap.get("baodanChange");
					birthdayChange = baodanMap.get("birthdayChange");
					if (!baodanChange && !changePreiod && !appChange && !changeDetionation) {
						continue;
					}
					if (!isChange && baodanChange) {
						isChange = true;
					}
					if ("2".equals(appntType)&& compare(appnt, insured, relationInfo)) {
						results.put("Status", 0);
						results.put("Msg", "第" + (i + 1)
								+ "行的保单投被保人信息不一致，无法变更！");
						return results;
					}
				} else {
					// 是本人的情况 保险起期、投保人信息未变更时 处理下一条
					if (!changePreiod && !appChange && !changeDetionation) {
						// 指定时，被保人关系是本人时
						// 校验被保人信息是否修改
						baodanMap = checkInsuredChange(insuredChangeInfoMap,
								insured, certificateInfo, sexInfo, relationInfo,
								dataDT.get(i), changeDetionation, destionationinfo);
						baodanChange = baodanMap.get("baodanChange");
						birthdayChange = baodanMap.get("birthdayChange");
						if(!baodanChange){
							continue;
						}else {
							if ("本人".equals(relationInfo.get(dataDT.getString(i, "recognizeeAppntRelation")))) {
								copyInsured(appnt, insured);
								isChange = true;
							}
						}
					}
				}

				insuredTemp = new SDInformationInsuredSchema();
				insuredTemp.setId(risktype.getsdinformationinsured_id());
				insuredTemp.fill();

				// 投保人信息修改并关系是本人的情况 被保人复制投保人信息
				if ((appChange && "本人".equals(relationInfo.get(dataDT
						.getString(i, "recognizeeAppntRelation"))))
						|| (!"本人".equals(insuredTemp
								.getrecognizeeAppntRelationName()) && "本人"
								.equals(relationInfo.get(dataDT.getString(i,
										"recognizeeAppntRelation"))))) {
					copyAppnt(appnt, insured);
				}
				// 限购份数校验
				if (limitCount > 0) {
					// 保险期限或证件类型或证件号变更 进行份数校验
					if (changePreiod
							|| !insuredTemp.getrecognizeeIdentityType().equals(
									insured.getrecognizeeIdentityType())
							|| !insuredTemp.getrecognizeeIdentityId().equals(
									insured.getrecognizeeIdentityId())) {
						if (!checkProductLimit(information, productType,
								insured.getrecognizeeIdentityType(),
								insured.getrecognizeeIdentityId(),
								insuredTemp.getId(), limitCount, changePreiod)) {
							results.put("Status", 0);
							results.put("Msg", "第" + (i + 1)
									+ "行的被保人已超过限购份数，不能变更！");
							return results;
						}
					}

				}

				insuredList.add(insured);

				// 起保日期修改或投保人出生日期修改 进行保费试算
				if (changePreiod || birthdayChange) {
					if (paramter1 == null || paramter1.isEmpty()) {
						paramter1 = service.getProductInformation(
								information.getproductId(), "N", channelCode);
						if (paramter1 == null || paramter1.isEmpty()
								|| paramter1.containsKey("error")) {
							results.put("Status", 0);
							results.put("Msg", "与产品中心交互未取得产品信息，请稍后再变更！");
							return results;
						}
					}
					// 取得保费试算后的新保费
					double insuredPrem = getInsuredPrem(paramter1,
							insuredPremMap, dutyJson, information,
							dataDT.get(i), svalidate, service);
					// 保费与原来不一致的情况，不能变更
					if (insuredPrem != Double.valueOf(risktype.gettimePrem())) {
						results.put("Status", 0);
						results.put("Msg", "第" + (i + 1)
								+ "行的保单变更前后保费不一致，无法变更！");
						return results;
					}
				}

				totalAmount = totalAmount.add(new BigDecimal(risktype
						.gettimePrem()));
				sumProductPrem = sumProductPrem.add(new BigDecimal(risktype
						.getproductPrice()));
				sumPayPrem = sumPayPrem.add(new BigDecimal(risktype
						.getpayPrice()));
			}
		}
		if (!isChange) {
			results.put("Status", 0);
			results.put("Msg", "无信息修改，无需变更！");
			return results;
		}
		// 变更的保单进行先撤单再承保的操作
		int risktypeListSize = risktypeList.size();
		if (risktypeListSize > 0) {
			Transaction trans = new Transaction();
			// 新生成订单表数据
			makeSDOrder(sdorder, sumProductPrem, totalAmount, now, trans,
					sumPayPrem,loginName);
			// 新生成订单信息表数据
			makeInformation(sdorder, information, now, trans);
			// 新生成订单明细表
			makeOrderItemSave(orderSn, sdorder, information, trans);
			// 新生成投保人表数据
			makeSDInformationAppnt(appnt, information, now, trans);
			// 新生成责任信息表数据
			makeSDInformationDuty(duty_change, dutyset_change, dutySet,
					information, now, trans);
			// 新生成交易信息数据
			makeTradeInfomation(sdorder, now, trans, orderSn, oldPaySn);
			// 新生成
			makeInsured(insuredList, risktypeList, information,
					appnt.getapplicantSn(), now, trans, insuredSet,
					risktypeSet, healDT,orderSn);
			if (!trans.commit()) {
				results.put("Status", 0);
				results.put("Msg", "生成数据失败！变更失败！");
				return results;
			}

			String errorMessage = "";
			String failRiskTypeIds = "";
			String failInsuredIds = "";
			List<String> changedRiskType = new ArrayList<String>();
			boolean cancleFlag = false;
			for (int i = 0; i < risktypeListSize; i++) {
				// 撤单
				CancelContService tCancelContService = new CancelContService();
				String riskTypeId = risktypeList.get(i).getId();
				String flag = tCancelContService.callInsTransInterface(
						information.getinsuranceCompany(), orderSn, insuredList
								.get(i).getinsuredSn(), riskTypeId);
				// 保险公司撤单成功后 更新系统数据状态
				if ("SUCCESS".equals(flag)) {
					// 更新保单表的撤单信息
					SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
					sdRiskTypeSchema.setId(riskTypeId);
					if (sdRiskTypeSchema.fill()) {
						sdRiskTypeSchema.setappStatus("2");// 撤单成功后状态更新为2
						sdRiskTypeSchema.setcancelDate(new Date());
						sdRiskTypeSchema.update();
					}
					// 更新订单表的订单状态
					tCancelContService.changeSDOrders(orderSn);

					if (!tCancelContService.callProductInterface(orderSn, "",
							PubFun.getCurrentDate(), "02",
							sdRiskTypeSchema.getpolicyNo(), riskTypeId, "", "")) {
						logger.info("保单：{}撤单结算中心调用失败！", sdRiskTypeSchema.getpolicyNo());
					}
					// 记录变更前后的保单表ID：变更前保单表ID,变更后保单表ID
					changedRiskType.add(riskTypeId + ","
							+ risktypeSet.get(i).getId());
					PolicyChangeInfoSchema policyChangeInfo = new PolicyChangeInfoSchema();
					String sql_Init = "select beforeValue from PolicyChangeInfo where newPaySn = ? and newOrderSn = ?  and changeType='19' order by createdate asc limit 0, 1";
					QueryBuilder qb_Init = new QueryBuilder(sql_Init, oldPaySn,
							orderSn);
					String beforvalue = qb_Init.executeString();
					if (StringUtil.isNotEmpty(beforvalue)) {
						policyChangeInfo.setbeforeValue(beforvalue);
					} else {
						policyChangeInfo.setbeforeValue(riskTypeId);
					}
					policyChangeInfo.setchangeType("19");
					policyChangeInfo.setafterValue(risktypeSet.get(i).getId());
					changeInfoList.add(policyChangeInfo);

				} else {
					errorMessage += "保单：" + risktype.getpolicyNo()
							+ "保险公司撤单失败，未变更！";
					// 撤单失败删除变更记录
					if (insuredChangeInfoMap.containsKey(insuredList.get(i)
							.getId())) {
						insuredChangeInfoMap.remove(insuredList.get(i).getId());
					}
					failRiskTypeIds += risktypeSet.get(i).getId() + ",";
					failInsuredIds += insuredSet.get(i).getId() + ",";
					continue;
				}
				cancleFlag = true;
				totalAmount = totalAmount.add(new BigDecimal(risktype
						.gettimePrem()));
				sumProductPrem = sumProductPrem.add(new BigDecimal(risktype
						.getproductPrice()));
			}

			// 全部撤单操作失败的情况， 删除所有数据
			if (!cancleFlag) {
				trans = new Transaction();
				trans.add(new QueryBuilder(
						"delete from sdorders where ordersn = ?", sdorder
								.getorderSn()));
				trans.add(new QueryBuilder(
						"delete from sdinformation where ordersn = ?", sdorder
								.getorderSn()));
				trans.add(new QueryBuilder(
						"delete from sdinformationappnt where informationsn = ?",
						information.getinformationSn()));
				trans.add(dutyset_change, Transaction.DELETE);
				trans.add(insuredSet, Transaction.DELETE);
				trans.add(risktypeSet, Transaction.DELETE);
				trans.add(new QueryBuilder(
						"delete from TradeInformation where ordID = ?", sdorder
								.getorderSn()));
				trans.add(new QueryBuilder(
						"delete from TradeSummaryInfo where OrderSns = ?",
						sdorder.getorderSn()));
				trans.add(new QueryBuilder(
						"delete from SDOrderItemOth where orderSn = ?", sdorder
								.getorderSn()));
				trans.add(new QueryBuilder(
						"delete from SDRemark where orderSn = ?", sdorder
								.getorderSn()));
				trans.commit();
				results.put("Status", 0);
				results.put("Msg", errorMessage);
				return results;

			} else if (StringUtil.isNotEmpty(failRiskTypeIds)) {// 有撤单失败的情况，删除数据
				trans = new Transaction();
				failRiskTypeIds = failRiskTypeIds.substring(0,
						failRiskTypeIds.length() - 1);
				failInsuredIds = failInsuredIds.substring(0,
						failInsuredIds.length() - 1);
				failRiskTypeIds = failRiskTypeIds.replace(",", "','");
				failInsuredIds = failInsuredIds.replace(",", "','");
				trans.add(new QueryBuilder(
						"delete from SDInformationRiskType where id in ('"
								+ failRiskTypeIds + "')"));
				trans.add(new QueryBuilder(
						"delete from SDInformationInsured where id in ('"
								+ failInsuredIds + "')"));
				trans.add(new QueryBuilder(
						"delete from SDOrderItemOth where sdinformationinsured_id in ('"
								+ failInsuredIds + "')"));
				// 更新订单价格
				sdorder.setproductTotalPrice(sumProductPrem.setScale(2,
						BigDecimal.ROUND_HALF_UP).toString());
				sdorder.settotalAmount(totalAmount.setScale(2,
						BigDecimal.ROUND_HALF_UP).toString());
				sdorder.setpayAmount(sdorder.gettotalAmount());
				sdorder.setpayPrice(sdorder.gettotalAmount());
				trans.add(sdorder, Transaction.UPDATE);
				information.setproductPrice(sdorder.getproductTotalPrice());
				information.setproductDiscountPrice(sdorder.gettotalAmount());
				trans.add(information, Transaction.UPDATE);
				trans.add(new QueryBuilder(
						"update TradeInformation set ordAmt = ? where ordID = ?",
						sdorder.gettotalAmount(), sdorder.getorderSn()));
				QueryBuilder qb = new QueryBuilder(
						"update TradeSummaryInfo set TotalAmount = ?, TradeAmount = ? where OrderSns = ?");
				qb.add(sdorder.gettotalAmount());
				qb.add(sdorder.gettotalAmount());
				qb.add(sdorder.getorderSn());
				trans.add(qb);
				trans.commit();
			}

			// 保存变更记录 （变更履历由最下面挪动到这里，怀疑承保时候没有返回值走不到下面的变更记录更新）
			saveChangeInfo(orderSn, oldPaySn, sdorder.getorderSn(),
					sdorder.getpaySn(), changeInfoList, insuredChangeInfoMap,loginName);

			// 退款用（同 产品购买功能中增加的代客支付数据）
			saveBuyForCustomerOldSn(orderSn, sdorder.getorderSn());

			// 承保
			String errMessage = send(information.getinsuranceCompany(),
					sdorder.getorderSn());
			if (StringUtil.isNotEmpty(errMessage)) {
				results.put("Status", 0);
				results.put("Msg", "变更失败！" + errMessage);
				return results;

			} else {
				// 有错误信息的情况
				if (StringUtil.isNotEmpty(errorMessage)) {
					results.put("Status", 0);
					results.put("Msg", "部分变更成功，部分失败：" + errorMessage);
					return results;
				} else {
					results.put("Status", 1);
					results.put("Msg", "变更成功！");
				}
			}

			// 保存变更记录
			// saveChangeInfo(orderSn, oldPaySn, sdorder.getorderSn(),
			// sdorder.getpaySn(), changeInfoList, insuredChangeInfoMap);

			// 更新原订单状态、保单变更状态
			updateStatus(changedRiskType, orderSn);

			// 合作商CPS变更后。插入CPS 表数据
			partnersCps(orderSn, sdorder.getorderSn(), sdorder.getchannelsn());

		} else {
			if (appChange || changePreiod || changeDetionation) {
				results.put("Status", 0);
				results.put("Msg", "没有可变更的保单！");
			} else {
				results.put("Status", 0);
				results.put("Msg", "无信息修改，无需变更！");
			}

		}
		return results;
	}

	/**
	 * 判断起保日期是否符合产品限制
	 * 
	 * @param productId
	 *            产品编码
	 * @param startDate
	 *            起保日期
	 * @param endDate
	 *            起保终止日期
	 * @return 错误信息
	 */
	private String checkPeriod(String productId, String startDate,
			String endDate) {

		QueryBuilder qb = new QueryBuilder(
				"SELECT startPeriod,endPeriod FROM productPeriod WHERE riskCode=?",
				productId);
		DataTable dt = qb.executeDataTable();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date periodfail;
		Date periodeffective;
		try {
			periodfail = sfTime.parse(endDate);
			periodeffective = sfTime.parse(startDate);
		} catch (ParseException e1) {
			return "起保日期格式有误，不能变更！";
		}
		// 起保时间间隔
		int startPeriod = 1;
		int endPeriod = 700;
		if (dt != null && dt.getRowCount() > 0) {
			if (StringUtil.isNotEmpty(dt.getString(0, 0))) {
				startPeriod = Integer.parseInt(dt.getString(0, 0));
			}
			if (StringUtil.isNotEmpty(dt.getString(0, 1))) {
				endPeriod = Integer.parseInt(dt.getString(0, 1));
			}
		}
		// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
		qb = new QueryBuilder(
				"SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
		qb.add(productId);
		String memo = qb.executeString();
		if (StringUtil.isNotEmpty(memo)) {
			startPeriod = Integer.parseInt(memo);
		}
		// 当前时间
		Date now = new Date();
		// 当前时间超出起保时间
		if (startPeriod != 0 && now.after(periodeffective)) {
			return "起保时间小于当前时间，不能变更！";
		} else if (startPeriod == 0 && endPeriod==0) {
			if (!new SimpleDateFormat("yyyyMMdd").format(now).equals(
					new SimpleDateFormat("yyyyMMdd").format(periodeffective))) {
				return "该产品只能当天购买，不能变更！";
			}
		}

		String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
				.calSDate(sf.format(now), startPeriod, "D");
		Date nowDate;
		try {
			nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
					.StringToDate(tDate);
			if ((periodeffective.getTime() < nowDate.getTime())
					&& (periodfail.getTime() >= periodeffective.getTime())) {
				return "起保时间不满足该产品起保时间间隔 " + startPeriod + " 天，不能变更！";
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return "计算最小起保期限错误！";
		}
		String maxDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
				.calSDate(sf.format(now), endPeriod, "D");
		try {
			Date end = sf.parse(maxDate + " 00:00:00");
			if (end.getTime() < periodeffective.getTime()) {
				return "起保时间超过该产品最大的起保时间：" + maxDate + "，不能变更！";
			}
		} catch (ParseException e1) {
			logger.error(e1.getMessage(), e1);
			return "计算最大起保期限错误！";
		}

		return "";
	}

	/**
	 * 校验投保人信息是否修改，修改则记录修改项
	 * 
	 * @param changeInfoList
	 *            修改记录
	 * @param appnt
	 *            投保人信息
	 * @param certificateInfo
	 *            证件信息
	 * @param sexInfo
	 *            性别星系
	 * @return true:修改 false:未修改
	 */
	private boolean checkAppntChange(
			List<PolicyChangeInfoSchema> changeInfoList,
			SDInformationAppntSchema appnt,
			Map<String, String> certificateInfo, Map<String, String> sexInfo,
			Map<String, Object> map) {
		boolean appChange = false;
		// 投保人姓名
		String applicantName = String.valueOf(map.get("applicantName"));
		// 投保人姓名
		String applicantEnName = String.valueOf(map.get("applicantEnName"));
		// 投保人证件类型
		String applicantIdentityType = String.valueOf(map
				.get("applicantIdentityType"));
		// 投保人证件类型名称
		String applicantIdentityTypeName = String.valueOf(map
				.get("applicantIdentityTypeName"));
		// 投保人证件号
		String applicantIdentityId = String.valueOf(map
				.get("applicantIdentityId"));
		// 投保人性别
		String applicantSex = String.valueOf(map.get("applicantSex"));
		// 投保人性别名称
		String applicantSexName = String.valueOf(map.get("applicantSexName"));// applicantSexName
		// 投保人出生日期
		String applicantBirthday = String.valueOf(map.get("applicantBirthday"));
		// 投保人手机号
		String applicantMobile = String.valueOf(map.get("applicantMobile"));
		// 投保人邮箱
		String applicantMail = String.valueOf(map.get("applicantMail"));
		if (applicantMail == null) {
			applicantMail = "";
		}

		// 投保人姓名
		if (!appnt.getapplicantName().equals(applicantName)) {
			// 投保人姓名变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("02");
			schema.setbeforeValue(appnt.getapplicantName());
			schema.setafterValue(applicantName);
			changeInfoList.add(schema);

			appnt.setapplicantName(applicantName);
			appChange = true;
		}
		// 投保人英文名
		if (StringUtil.isNotEmpty(appnt.getapplicantEnName())) {
			if (!appnt.getapplicantEnName().equals(applicantEnName)) {
				// 投保人英文名变更记录
				PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
				schema.setchangeType("16");
				schema.setbeforeValue(appnt.getapplicantEnName());
				schema.setafterValue(applicantEnName);
				changeInfoList.add(schema);

				appnt.setapplicantEnName(applicantEnName);
				appChange = true;
			}
		}
		// 投保人证件类型
		if (!appnt.getapplicantIdentityType().equals(applicantIdentityType)) {
			String newTypeName = "";
			for (String key : certificateInfo.keySet()) {
				if (key.startsWith(applicantIdentityType)) {
					newTypeName = certificateInfo.get(key);
					break;
				}
			}
			// 投保人证件类型变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("03");
			schema.setbeforeValue(appnt.getapplicantIdentityTypeName());
			schema.setafterValue(newTypeName);
			changeInfoList.add(schema);

			appnt.setapplicantIdentityType(applicantIdentityType);
			// 投保人证件类型名称
			appnt.setapplicantIdentityTypeName(newTypeName);
			appChange = true;

		}
		// 投保人证件号
		if (!appnt.getapplicantIdentityId().equals(applicantIdentityId)) {
			// 投保人证件号变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("04");
			schema.setbeforeValue(appnt.getapplicantIdentityId());
			schema.setafterValue(applicantIdentityId);
			changeInfoList.add(schema);

			appnt.setapplicantIdentityId(applicantIdentityId);
			appChange = true;
		}
		// 投保人性别
		if (!appnt.getapplicantSex().equals(applicantSex)) {
			// 投保人性别变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("05");
			schema.setbeforeValue(appnt.getapplicantSexName());
			schema.setafterValue(sexInfo.get(applicantSex));
			changeInfoList.add(schema);

			appnt.setapplicantSex(applicantSex);
			appnt.setapplicantSexName(sexInfo.get(applicantSex));
			appChange = true;
		}
		// 投保人出生日期
		if (!appnt.getapplicantBirthday().equals(applicantBirthday)) {
			// 投保人出生日期变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("06");
			schema.setbeforeValue(appnt.getapplicantBirthday());
			schema.setafterValue(applicantBirthday);
			changeInfoList.add(schema);

			appnt.setapplicantBirthday(applicantBirthday);
			appChange = true;
		}
		// 投保人手机号
		if (StringUtil.isNotEmpty(appnt.getapplicantMobile()) && !appnt.getapplicantMobile().equals(applicantMobile)) {
			// 投保人手机号变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("07");
			schema.setbeforeValue(appnt.getapplicantMobile());
			schema.setafterValue(applicantMobile);
			changeInfoList.add(schema);

			appnt.setapplicantMobile(applicantMobile);
			appChange = true;
		}
		// 投保人邮箱
		String mail = appnt.getapplicantMail(); 
		if (mail == null) {
			mail = "";
		}
		if (!mail.equals(applicantMail)) {
			// 投保人邮箱变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("08");
			schema.setbeforeValue(appnt.getapplicantMail());
			schema.setafterValue(applicantMail);
			changeInfoList.add(schema);

			appnt.setapplicantMail(applicantMail);
			appChange = true;
		}

		return appChange;
	}

	/**
	 * 校验保单是否承保成功
	 * 
	 * @param appChange
	 *            投保人是否变更
	 * @param changePreiod
	 *            起保期限是否变更
	 * @param dataDT
	 *            前台被保人信息
	 * @return 错误信息
	 */
	private String checkAppstatus(boolean appChange, boolean changePreiod,
			DataTable dataDT, boolean changedestionation) {
		int rowcount = dataDT.getRowCount();
		// 投保人或起保期限变更的情况 被保人必须全选
		// appChange 投保人变更   changePreiod 保险期间变更   changedestionation 目的地变更
		if (appChange || changePreiod || changedestionation) {
			// 是否全选标识
			boolean checkflag = false;
			for (int i = 0; i < rowcount; i++) {
				if ("1".equals(dataDT.getString(i, "appFlag"))
						&& !"1".equals(dataDT.getString(i, "checkflag"))) {
					checkflag = true;
				}
				// 承保状态为未承保的情况
				if ("0".equals(dataDT.getString(i, "appStatus"))
						|| StringUtil.isEmpty(dataDT.getString(i, "appStatus"))) {
					return "第" + (i + 1) + "行保单未承保成功、承保状态是“"
							+ dataDT.getString(i, "appStatus") + "”，请承保后再变更！";
				}
				// 保单号为空的情况
				if ("1".equals(dataDT.getString(i, "appStatus"))
						&& StringUtil.isEmpty(dataDT.getString(i, "policyNo"))) {
					return "第" + (i + 1) + "行保单未承保成功、保单号为空，请承保后再变更！";
				}
			}
			// 承保状态下没全选的情况（不包括撤单、已变更）？ 投保人信息变更需要全选被保人信息
			if (checkflag) {
				return "投保人信息或起保期限或旅游目的地变更，请选择全部保单！若保单信息分页，则请分别每页全选变更一次！";

			}
		} else {
			// 只被保人信息变更的情况 只校验选中的
			for (int i = 0; i < rowcount; i++) {
				// 对选中的保单进行校验
				if ("1".equals(dataDT.getString(i, "checkflag"))) {
					// 承保状态为未承保的情况
					if ("0".equals(dataDT.getString(i, "appStatus"))
							|| StringUtil.isEmpty(dataDT.getString(i,
									"appStatus"))) {
						return "第" + (i + 1) + "行保单未承保成功、承保状态是“"
								+ dataDT.getString(i, "appStatus")
								+ "”，请承保后再变更！";
					}
					// 保单号为空的情况
					if ("1".equals(dataDT.getString(i, "appStatus"))
							&& StringUtil.isEmpty(dataDT.getString(i,
									"policyNo"))) {
						return "第" + (i + 1) + "行保单未承保成功、保单号为空，请承保后再变更！";
					}
				}
			}
		}
		return "";
	}

	/**
	 * 校验被保人信息是否修改，修改则记录修改项
	 * 
	 * @param insuredChangeInfoMap
	 *            修改记录
	 * @param appnt
	 *            被保人信息
	 * @param certificateInfo
	 *            证件信息
	 * @param sexInfo
	 *            性别星系
	 * @return true:修改 false:未修改
	 */
	private Map<String, Boolean> checkInsuredChange(
			Map<String, List<PolicyChangeInfoSchema>> insuredChangeInfoMap,
			SDInformationInsuredSchema insured,
			Map<String, String> certificateInfo, Map<String, String> sexInfo,
			Map<String, String> relationInfo, DataRow dr,
			boolean destionationchange, Map<String, String> destionationinfo) {
		List<PolicyChangeInfoSchema> changeInfoList = new ArrayList<PolicyChangeInfoSchema>();
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		boolean baodanChange = false;
		boolean birthdayChange = false;
		// 被保人姓名
		String recognizeeName = dr.getString("recognizeeName");
		// 被保人证件类型
		String recognizeeIdentityType = dr.getString("recognizeeIdentityType")
				.split("_")[0];
		// 被保人证件号码
		String recognizeeIdentityId = dr.getString("recognizeeIdentityId");
		// 被保人性别
		String recognizeeSex = dr.getString("recognizeeSex");
		// 被保人出生日期
		String recognizeeBirthday = dr.getString("recognizeeBirthday");
		// 被保人手机号码
		String recognizeeMobile = dr.getString("recognizeeMobile");
		// 被保人邮箱
		String recognizeeMail = dr.getString("recognizeeMail");
		// 与投保人关系
		String recognizeeAppntRelation = dr
				.getString("recognizeeAppntRelation");
		String recognizeeAppntRelationName = relationInfo
				.get(recognizeeAppntRelation);
		// 被保人英文名
		String recognizeeEnName = dr.getString("recognizeeEnName");
		// 旅游目的地
		if (destionationchange) {
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("20");
			schema.setbeforeValue(insured.getdestinationCountry());
			schema.setafterValue(destionationinfo.get("destinationCountry"));
			changeInfoList.add(schema);

			insured.setdestinationCountry(destionationinfo
					.get("destinationCountry"));
			insured.setdestinationCountryText(destionationinfo
					.get("destinationCountryText"));
			baodanChange = true;
		}
		// 被保人姓名
		if (!insured.getrecognizeeName().equals(recognizeeName)) {
			// 被保人姓名变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("09");
			schema.setbeforeValue(insured.getrecognizeeName());
			schema.setafterValue(recognizeeName);
			changeInfoList.add(schema);

			insured.setrecognizeeName(recognizeeName);
			baodanChange = true;
		}
		// 被保人证件类型
		if (!insured.getrecognizeeIdentityType().equals(recognizeeIdentityType)) {
			String newTypeName = certificateInfo.get(dr
					.getString("recognizeeIdentityType"));
			// 被保人证件类型变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("10");
			schema.setbeforeValue(insured.getrecognizeeIdentityTypeName());
			schema.setafterValue(newTypeName);
			changeInfoList.add(schema);

			insured.setrecognizeeIdentityType(recognizeeIdentityType);
			insured.setrecognizeeIdentityTypeName(newTypeName);
			baodanChange = true;
		}
		// 被保人证件号码
		if (!insured.getrecognizeeIdentityId().equals(recognizeeIdentityId)) {
			// 被保人证件号码变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("11");
			schema.setbeforeValue(insured.getrecognizeeIdentityId());
			schema.setafterValue(recognizeeIdentityId);
			changeInfoList.add(schema);

			insured.setrecognizeeIdentityId(recognizeeIdentityId);
			baodanChange = true;
		}
		// 被保人性别
		if (!insured.getrecognizeeSex().equals(recognizeeSex)) {
			// 被保人性别变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("12");
			schema.setbeforeValue(insured.getrecognizeeSexName());
			schema.setafterValue(sexInfo.get(recognizeeSex));
			changeInfoList.add(schema);

			insured.setrecognizeeSex(recognizeeSex);
			insured.setrecognizeeSexName(sexInfo.get(recognizeeSex));
			baodanChange = true;
		}
		// 被保人出生日期
		if (!insured.getrecognizeeBirthday().equals(recognizeeBirthday)) {
			// 被保人出生日期变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("13");
			schema.setbeforeValue(insured.getrecognizeeBirthday());
			schema.setafterValue(recognizeeBirthday);
			changeInfoList.add(schema);

			insured.setrecognizeeBirthday(recognizeeBirthday);
			baodanChange = true;
			birthdayChange = true;
		}
		// 被保人手机号码
		if (!recognizeeMobile.equals(insured.getrecognizeeMobile())) {
			// 被保人手机号码变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("14");
			schema.setbeforeValue(insured.getrecognizeeMobile());
			schema.setafterValue(recognizeeMobile);
			changeInfoList.add(schema);

			insured.setrecognizeeMobile(recognizeeMobile);
			baodanChange = true;
		}
		// 被保人邮箱
		if (StringUtil.isNotEmpty(insured.getrecognizeeMail())
				&& !insured.getrecognizeeMail().equals(recognizeeMail)) {
			// 被保人邮箱变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("15");
			schema.setbeforeValue(insured.getrecognizeeMail());
			schema.setafterValue(recognizeeMail);
			changeInfoList.add(schema);

			insured.setrecognizeeMail(recognizeeMail);
			baodanChange = true;
		}
		// 被保人英文名
		if (StringUtil.isNotEmpty(insured.getrecognizeeEnName())
				&& !insured.getrecognizeeEnName().equals(recognizeeEnName)) {
			// 被保人英文名变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("17");
			schema.setbeforeValue(insured.getrecognizeeEnName());
			schema.setafterValue(recognizeeEnName);
			changeInfoList.add(schema);

			insured.setrecognizeeEnName(recognizeeEnName);
			baodanChange = true;
		}
		// 与投保人关系
		if (!insured.getrecognizeeAppntRelation().equals(
				recognizeeAppntRelation)) {
			// 与投保人关系变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("18");
			schema.setbeforeValue(insured.getrecognizeeAppntRelationName());
			schema.setafterValue(recognizeeAppntRelationName);
			changeInfoList.add(schema);

			insured.setrecognizeeAppntRelation(recognizeeAppntRelation);
			insured.setrecognizeeAppntRelationName(recognizeeAppntRelationName);
			baodanChange = true;
		}
		if (baodanChange) {
			insuredChangeInfoMap.put(insured.getId(), changeInfoList);
		}
		result.put("baodanChange", baodanChange);
		result.put("birthdayChange", birthdayChange);
		return result;
	}

	/**
	 * 投保人基础信息复制到被保人信息中
	 * 
	 * @param appnt
	 *            投保人信息
	 * @param insured
	 *            被保人信息
	 */
	private void copyAppnt(SDInformationAppntSchema appnt,
			SDInformationInsuredSchema insured) {
		// 姓名
		insured.setrecognizeeName(appnt.getapplicantName());
		if (StringUtil.isNotEmpty(insured.getrecognizeeEnName())) {
			insured.setrecognizeeEnName(appnt.getapplicantEnName());
		}
		insured.setrecognizeeFirstName(appnt.getapplicantFirstName());
		insured.setrecognizeeLashName(appnt.getapplicantLastName());
		// 证件类型
		insured.setrecognizeeIdentityType(appnt.getapplicantIdentityType());
		insured.setrecognizeeIdentityTypeName(appnt
				.getapplicantIdentityTypeName());
		// 证件号码
		insured.setrecognizeeIdentityId(appnt.getapplicantIdentityId());
		// 性别
		insured.setrecognizeeSex(appnt.getapplicantSex());
		insured.setrecognizeeSexName(appnt.getapplicantSexName());
		// 出生日期
		insured.setrecognizeeBirthday(appnt.getapplicantBirthday());
		// 手机号码
		if (StringUtil.isNotEmpty(insured.getrecognizeeMobile())) {
			insured.setrecognizeeMobile(appnt.getapplicantMobile());
		}
		// 电子邮箱
		if (StringUtil.isNotEmpty(insured.getrecognizeeMail())) {
			insured.setrecognizeeMail(appnt.getapplicantMail());
		}
	}

	/**
	 * 限购份数的校验
	 * 
	 * @param info
	 *            订单信息
	 * @param type
	 *            险种类型
	 * @param identityType
	 *            证件类型
	 * @param identityId
	 *            证件号
	 * @param insuredId
	 *            被保险人Id
	 * @param limitCount
	 *            限购份数
	 * @param changePreiod
	 *            保险期限是否变更
	 * @return
	 */
	private boolean checkProductLimit(SDInformationSchema info, String type,
			String identityType, String identityId, String insuredId,
			int limitCount, boolean changePreiod) {

		StringBuffer sb = new StringBuffer();
		QueryBuilder qb;
		// 家财险的校验
		if ("C".equals(type)) {
			// 保险期限没变时 不用校验
			if (!changePreiod) {
				return true;
			}
			// 取得家庭地址
			String sql = "select propertyAdress, propertyArea1, propertyArea2,propertyArea3 from sdinformationproperty where sdinformationinsured_id = ?";
			qb = new QueryBuilder(sql, insuredId);
			DataTable dt = qb.executeDataTable();
			String propertyAdress = "";
			String propertyArea1 = "";
			String propertyArea2 = "";
			String propertyArea3 = "";
			if (dt != null && dt.getRowCount() > 0) {
				propertyAdress = dt.getString(0, 0);
				propertyArea1 = dt.getString(0, 1);
				propertyArea2 = dt.getString(0, 2);
				propertyArea3 = dt.getString(0, 3);
			} else {
				return true;
			}
			sb.append("SELECT COUNT(1) FROM SDInformation a, SDInformationInsured b, ");
			sb.append("SDInformationRiskType c,sdinformationproperty d ");
			sb.append("WHERE a.InformationSn = b.InformationSn AND b.RecognizeeSn =c.RecognizeeSn ");
			sb.append("AND b.RecognizeeSn =d.RecognizeeSn AND AppStatus ='1' ");
			sb.append("AND a.InformationSn = c.InformationSn AND a.ProductId = ? ");
			sb.append("AND d.propertyArea1 = ? AND d.propertyArea2 = ? AND d.propertyArea3 = ? AND d.propertyAdress = ? ");
			sb.append("AND ((a.StartDate <= ? AND a.StartDate >= ?) OR (a.EndDate >= ? AND a.EndDate <= ?))");
			qb = new QueryBuilder(sb.toString());
			qb.add(info.getproductId());
			qb.add(propertyArea1);
			qb.add(propertyArea2);
			qb.add(propertyArea3);
			qb.add(propertyAdress);
			qb.add(info.getendDate());
			qb.add(info.getstartDate());
			qb.add(info.getstartDate());
			qb.add(info.getendDate());

		} else {
			// 除家财险其他险种的校验
			sb.append("SELECT COUNT(1) FROM SDInformation a, SDInformationInsured b, ");
			sb.append("SDInformationRiskType c WHERE a.InformationSn = b.InformationSn ");
			sb.append("AND b.RecognizeeSn = c.RecognizeeSn AND c.AppStatus ='1' ");
			sb.append("AND a.InformationSn = c.InformationSn AND b.RecognizeeIdentityType = ? ");
			sb.append("AND b.RecognizeeIdentityId = ? AND a.productoutcode = ? ");
			sb.append("AND ((EndDate >=? AND StartDate <= ?) OR (startDate<=? AND endDate>= ?) ");
			sb.append("OR (StartDate >= ? AND EndDate <= ? )) ");
			qb = new QueryBuilder(sb.toString());
			qb.add(identityType);
			qb.add(identityId);
			qb.add(info.getproductOutCode());
			qb.add(info.getstartDate());
			qb.add(info.getstartDate());
			qb.add(info.getendDate());
			qb.add(info.getendDate());
			qb.add(info.getstartDate());
			qb.add(info.getendDate());
		}
		if (limitCount < qb.executeInt()) {
			return false;
		}

		return true;
	}

	/**
	 * 保费试算取得保费
	 * 
	 * @param paramter1
	 *            产品信息
	 * @param insuredPremMap
	 * @param dutyJson
	 *            责任信息
	 * @param information
	 *            订单信息
	 * @param dr
	 *            保单信息
	 * @param svalidate
	 *            起保日期
	 * @param service
	 * @return 保费
	 */
	private double getInsuredPrem(Map<String, Object> paramter1,
			Map<String, Object> insuredPremMap, Map<String, String> dutyJson,
			SDInformationSchema information, DataRow dr, String svalidate,
			SDOrderServiceImpl service) {
		OrderConfigNewServiceImpl orderConfigNewService = new OrderConfigNewServiceImpl();

		// 产品基础数据
		String[] BaseInformation = new String[16];
		// 责任投保要素列表
		List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();
		// 产品投保要素列表
		List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();
		// 投保要素信息
		Map<String, String> insureJson = new HashMap<String, String>();
		// 参数集合
		Map<String, Object> cMap = new HashMap<String, Object>();
		double insuredPrem = 0.0;
		String insuredSex = dr.getString("recognizeeSex");
		String recognizeeBirthday = dr.getString("recognizeeBirthday");
		// 取得产品基础数据
		BaseInformation = (String[]) paramter1.get("baseInformation");
		// 取得责任投保要素列表
		riskAppFactior = (List<OrderRiskAppFactor>) paramter1
				.get("riskAppFactor");
		// 取得责任投保要素列表
		dutyFactor = (List<OrderDutyFactor>) paramter1.get("dutyFactor");
		cMap = new HashMap<String, Object>();
		cMap.put("baseInformations", BaseInformation);
		cMap.put("riskAppFactior", riskAppFactior);
		cMap.put("dutyFactor", dutyFactor);
		cMap.put("productId", information.getproductId());
		cMap.put("sdinformation", information);
		cMap.put("dutyJson", dutyJson);
		cMap.put("effdate", svalidate);
		insureJson.put("Plan", information.getplanCode());
		insureJson.put("AppType", information.getappType());
		insureJson.put("FeeYear", information.getchargeYear());
		insureJson.put("TextAge", recognizeeBirthday);
		insureJson.put("Period", information.getensure());
		insureJson.put("Sex", insuredSex);
		cMap.put("insureJson", insureJson);
		cMap.put("insuredBirthDay", recognizeeBirthday);
		cMap.put("insuredSex", insuredSex);

		// 产品年龄限制
		String limitAge = orderConfigNewService.getSectionAge(information
				.getproductId());

		String realSecAge = orderConfigNewService.getRealSectionAge(
				recognizeeBirthday, limitAge, svalidate);
		if (!"-1".equals(realSecAge)) {
			if (String.valueOf(insuredPremMap
					.get(insuredSex + "-" + realSecAge)) != null
					&& !"null".equals(String.valueOf(insuredPremMap
							.get(insuredSex + "-" + realSecAge)))
					&& !"".equals(String.valueOf(insuredPremMap.get(insuredSex
							+ "-" + realSecAge)))) {
				Map<String, Object> tempMap = (Map<String, Object>) insuredPremMap
						.get(insuredSex + "-" + realSecAge);
				insuredPrem = insuredPrem
						+ Double.parseDouble(String.valueOf(tempMap
								.get("retCountPrem")));
			} else {
				Map<String, Object> rMap = calPrem(cMap, service,
						orderConfigNewService);
				insuredPrem = insuredPrem
						+ Double.parseDouble(String.valueOf(rMap
								.get("retCountPrem")));
				insuredPremMap.put(insuredSex + "-" + realSecAge, rMap);
			}
		} else {
			insuredPrem = insuredPrem + Double.parseDouble("0");
		}
		return insuredPrem;
	}

	/**
	 * 新生成订单表数据
	 * 
	 * @param sdorder
	 * @param sumProductPrem
	 *            订单原价
	 * @param totalAmount
	 *            订单折后价
	 * @param now
	 *            当前时间
	 * @param trans
	 */
	private void makeSDOrder(SDOrdersSchema sdorder, BigDecimal sumProductPrem,
			BigDecimal totalAmount, Date now, Transaction trans,
			BigDecimal sumPayPrem,String loginName) {
		sdorder.setid(PubFun.GetOrderID(sdorder.getorderSn()));
		sdorder.setorderSn(PubFun.GetOrderSn());
		sdorder.setcreateDate(now);
		sdorder.setmodifyDate(now);
		sdorder.setorderStatus("7");
		sdorder.setpaySn(PubFun.GetBGPaySn());
		/* sdorder.setcouponSn(""); */
		sdorder.setcouponSn(sdorder.getcouponSn());
		sdorder.setactivitySn("");
		sdorder.setoffsetPoint("0");
		sdorder.setcommentId(null);
		/* sdorder.setorderCoupon("0"); */
		sdorder.setorderCoupon(sdorder.getorderCoupon());
		sdorder.setorderIntegral("0");
		sdorder.setorderActivity(ArithUtil.sub(totalAmount
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString(), sumPayPrem.setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
		sdorder.setsumActivity("0");
		sdorder.setproductTotalPrice(sumProductPrem.setScale(2,
				BigDecimal.ROUND_HALF_UP).toString());
		sdorder.settotalAmount(totalAmount
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		sdorder.setpayAmount(sumPayPrem.setScale(2, BigDecimal.ROUND_HALF_UP)
				.toString());
		sdorder.setmemberId(sdorder.getmemberId());
		/* sdorder.setpayPrice(sdorder.gettotalAmount()); */
		sdorder.setpayPrice(sumPayPrem.setScale(2, BigDecimal.ROUND_HALF_UP)
				.toString());
		trans.add(sdorder, Transaction.INSERT);
	}

	/**
	 * 新生成订单信息表数据
	 * 
	 * @param sdorder
	 *            订单表数据
	 * @param information
	 *            订单信息表
	 * @param now
	 *            当前时间
	 * @param trans
	 */
	private void makeInformation(SDOrdersSchema sdorder,
			SDInformationSchema information, Date now, Transaction trans) {
		information.setId(PubFun.GetInformationID(sdorder.getorderSn()));
		information.setinformationSn(PubFun.GetSDInformationSn());
		information.setcreateDate(now);
		information.setmodifyDate(now);
		information.setorderSn(sdorder.getorderSn());
		information.setpoint("");
		information.setpointStatus("");
		information.setsdorder_id(sdorder.getid());
		information.setproductPrice(sdorder.getproductTotalPrice());
		information.setproductDiscountPrice(sdorder.gettotalAmount());
		trans.add(information, Transaction.INSERT);
	}

	/**
	 * 处理orderitem
	 */
	public void makeOrderItemSave(String orderSn, SDOrdersSchema sdorder,
			SDInformationSchema sdInformation, Transaction trans) {
		SDOrderItemSchema sdorderitem = new SDOrderItemSchema();
		QueryBuilder qb = new QueryBuilder(
				"SELECT channelCode,channelAgentCode,typeFlag FROM sdorderitem WHERE ordersn=?",
				orderSn);
		DataTable dt = qb.executeDataTable();
		String channelCode = "";
		String typeFlag = "";
		String ChannelAgentCode = "";
		if (dt != null && dt.getRowCount() > 0) {
			if (StringUtil.isNotEmpty(dt.getString(0, 0))) {
				channelCode = dt.getString(0, 0);
			}
			if (StringUtil.isNotEmpty(dt.getString(0, 1))) {
				typeFlag = dt.getString(0, 1);
			}
			if (StringUtil.isNotEmpty(dt.getString(0, 2))) {
				ChannelAgentCode = dt.getString(0, 2);
			}
		}
		Date createDate = new Date();
		Date modifyDate = new Date();
		sdorderitem.setid(PubFun.GetOrderItemID(sdInformation.getorderSn()));
		sdorderitem.setcreateDate(createDate);
		sdorderitem.setmodifyDate(modifyDate);
		sdorderitem.setsdorder_id(sdorder.getid());
		sdorderitem.setorderSn(sdorder.getorderSn());
		sdorderitem.setorderPoint("0");
		sdorderitem.setpointStatus("1");
		sdorderitem.setorderitemSn(PubFun.GetItemNo());
		sdorderitem.getchannelCode();
		sdorderitem.gettypeFlag();
		sdorderitem.getchannelAgentCode();
		sdorderitem.setchannelCode(channelCode);
		sdorderitem.settypeFlag(typeFlag);
		sdorderitem.setchannelAgentCode(ChannelAgentCode);
		trans.add(sdorderitem, Transaction.INSERT);
	}

	/**
	 * 新生成投保人表数据
	 * 
	 * @param appnt
	 *            投保人表
	 * @param information
	 *            订单信息
	 * @param now
	 *            当前时间
	 * @param trans
	 */
	private void makeSDInformationAppnt(SDInformationAppntSchema appnt,
			SDInformationSchema information, Date now, Transaction trans) {
		appnt.setId(PubFun.GetInformationAppntID(information.getorderSn()));
		appnt.setcreateDate(now);
		appnt.setmodifyDate(now);
		appnt.setinformationSn(information.getinformationSn());
		appnt.setapplicantSn(PubFun.GetSDAppntSn());
		int appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun
				.getAge(appnt.getapplicantBirthday());
		appnt.setapplicantAge(String.valueOf(appAge));
		appnt.setsdinformaiton_id(information.getId());
		trans.add(appnt, Transaction.INSERT);
	}

	/**
	 * 新生成责任信息表数据
	 * 
	 * @param dutySet
	 *            责任信息表
	 * @param info
	 *            订单信息表
	 * @param now
	 *            当前时间
	 * @param trans
	 */
	private void makeSDInformationDuty(SDInformationDutySchema duty_change,
			SDInformationDutySet dutySet_change, SDInformationDutySet dutySet,
			SDInformationSchema info, Date now, Transaction trans) {
		if (dutySet != null && dutySet.size() > 0) {
			int dutySize = dutySet.size();

			for (int i = 0; i < dutySize; i++) {
				duty_change = (SDInformationDutySchema) dutySet.get(i).clone();
				duty_change
						.setId(PubFun.GetInformationDutyID(info.getorderSn()));
				duty_change.setcreateDate(now);
				duty_change.setmodifyDate(now);
				duty_change.setinformationSn(info.getinformationSn());
				duty_change.setorderSn(info.getorderSn());
				duty_change.setsdinformation_id(info.getId());

				dutySet_change.add(duty_change);
			}
			trans.add(dutySet_change, Transaction.INSERT);
		}

	}

	/**
	 * 新生成交易信息数据
	 * 
	 * @param sdorder
	 *            订单信息
	 * @param now
	 *            当前时间
	 * @param trans
	 * @param oldOrderSn
	 *            旧订单号
	 * @param oldPaySn
	 *            旧交易号
	 */
	private void makeTradeInfomation(SDOrdersSchema sdorder, Date now,
			Transaction trans, String oldOrderSn, String oldPaySn) {
		// 交易表
		TradeInformationSchema trade = new TradeInformationSchema();
		// 交易汇总表
		TradeSummaryInfoSchema tradeSummary = new TradeSummaryInfoSchema();

		trade.setordID(oldOrderSn);
		TradeInformationSet tradesSet = trade.query();
		if (tradesSet != null && tradesSet.size() > 0) {
			trade = tradesSet.get(0);
			trade.setid(PubFun.GetTradeInformationID(sdorder.getorderSn()));
			trade.setcreateDate(now);
			trade.setmodifyDate(now);
			trade.setordAmt(sdorder.gettotalAmount());
			trade.setordID(sdorder.getorderSn());
			trade.settradeSeriNO(sdorder.getpaySn());
			trade.settradeCheckSeriNo(sdorder.getpaySn());
			trade.setweixicookie("");
			trans.add(trade, Transaction.INSERT);
		}

		tradeSummary.setPaySn(oldPaySn);
		TradeSummaryInfoSet summarySet = tradeSummary.query();
		if (summarySet != null && summarySet.size() > 0) {
			tradeSummary = summarySet.get(0);
			tradeSummary.setid(NoUtil.getMaxNo("TradeSummaryID", 11));
			tradeSummary.setPaySn(sdorder.getpaySn());
			tradeSummary.setTradeSn(sdorder.getpaySn());
			tradeSummary.setOrderSns(sdorder.getorderSn());
			tradeSummary.setCouponSn("");
			tradeSummary.setCouponSumAmount("");
			tradeSummary.setActivitySumAmount("");
			tradeSummary.setPointSumAmount("");
			tradeSummary.setTotalAmount(sdorder.gettotalAmount());
			tradeSummary.setTradeAmount(sdorder.gettotalAmount());
			tradeSummary.setCreateDate(now);
			tradeSummary.setModifyDate(now);
			trans.add(tradeSummary, Transaction.INSERT);
		}

		String initPaySn = oldPaySn;
		if (oldPaySn.indexOf("BG") >= 0) {
			String sql = "select initPaySn from PolicyChangeInfo where newPaySn = ?";
			QueryBuilder qb = new QueryBuilder(sql, oldPaySn);
			initPaySn = qb.executeString();
		}
		// 保全记录添加
		SDRemarkSchema comment = new SDRemarkSchema();
		comment.setid(com.sinosoft.lis.pubfun.PubFun.GetNRemarkId());
		comment.setremark("变更：初始商家订单号-" + initPaySn);
		comment.setorderSn(sdorder.getorderSn());
		comment.setOperateName("system");
		comment.setOperateTime(new Date());
		comment.setOperateType("add");
		comment.setprop1("");
		comment.setprop2("");
		comment.setupperId("");
		trans.add(comment, Transaction.INSERT);
	}

	/**
	 * 
	 * @param insuredList
	 * @param risktypeList
	 * @param info
	 * @param applicantSn
	 * @param now
	 * @param trans
	 * @param insuredSet
	 * @param risktypeSet
	 */
	private void makeInsured(List<SDInformationInsuredSchema> insuredList,
			List<SDInformationRiskTypeSchema> risktypeList,
			SDInformationSchema info, String applicantSn, Date now,
			Transaction trans, SDInformationInsuredSet insuredSet,
			SDInformationRiskTypeSet risktypeSet, DataTable healDT,String orderSn) {
		int count = risktypeList.size();
		SDInformationInsuredSchema insured = new SDInformationInsuredSchema();
		SDInformationRiskTypeSchema risktype = new SDInformationRiskTypeSchema();
		List<QueryBuilder> list = new ArrayList<QueryBuilder>();
		QueryBuilder qb = null;
		for (int i = 0; i < count; i++) {
			insured = new SDInformationInsuredSchema();
			SchemaUtil.copyFieldValue(insuredList.get(i), insured);
			insured.setId(PubFun.GetInformationInsuredID(info.getorderSn()));
			insured.setcreateDate(now);
			insured.setmodifyDate(now);
			insured.setorderSn(info.getorderSn());
			insured.setinformationSn(info.getinformationSn());
			insured.setrecognizeeSn(PubFun.GetSDInsuredSn());
			int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun
					.getAge(insured.getrecognizeeBirthday());
			insured.setrecognizeeAge(String.valueOf(tInsuredAge));
			insured.setinsuredSn(info.getorderSn()
					+ insured.getinsuredSn().substring(16));
			insured.setsdinformation_id(info.getId());
			if (StringUtil.isNotEmpty(insured.getrecognizeeKey())) {
				insured.setrecognizeeKey(info.getorderSn()
						+ insured.getrecognizeeKey().substring(16));
			}
			insured.setdiscountPrice(insured.getrecognizeePrem());
			insuredSet.add(insured);

			risktype = new SDInformationRiskTypeSchema();
			SchemaUtil.copyFieldValue(risktypeList.get(i), risktype);
			risktype.setId(PubFun.GetInformationRiskTypeID(info.getorderSn()));
			risktype.setcreateDate(now);
			risktype.setmodifyDate(now);
			risktype.setorderSn(info.getorderSn());
			risktype.setinformationSn(info.getinformationSn());
			risktype.setrecognizeeSn(insured.getrecognizeeSn());
			risktype.setapplicantSn(applicantSn);
			risktype.setpolicyNo("");
			risktype.setapplyPolicyNo("");
			risktype.setsvaliDate(info.getstartDate());
			risktype.setevaliDate(info.getendDate());
			risktype.setelectronicPath("");
			risktype.setinsurerFlag("");
			risktype.setinsureMsg("");
			risktype.setinsureDate("");
			risktype.setbalanceStatus("");
			risktype.setbalanceFlag("");
			risktype.setbalanceMsg("");
			risktype.setappStatus("0");
			risktype.setvalidateCode("");
			risktype.setsdinformationinsured_id(insured.getId());
			risktype.setsdorder_id(info.getsdorder_id());
			risktype.setcouponValue("");
			risktype.setintegralValue("");
			risktype.setactivityValue("");
			/* risktype.setpayPrice(risktype.gettimePrem()); */
			risktype.setpayPrice(risktype.getpayPrice());
			risktypeSet.add(risktype);

			orderItemOthSave(insured, info, trans,orderSn);
			/*makeHealth(insuredList.get(i).getorderSn(), insuredList.get(0),
					healDT, insured, trans);*/

			if ("11".equals(info.getriskType())) {
				qb = makeSDInformationProperty(insuredList.get(i).getorderSn(),
						insuredList.get(i).getrecognizeeSn(),
						insured.getrecognizeeSn(), insured.getId());
				list.add(qb);
			}
		}
		trans.add(risktypeSet, Transaction.INSERT);
		trans.add(insuredSet, Transaction.INSERT);

		if (list != null && list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				trans.add(list.get(i));
			}
		}
	}

	/**
	 * 保存变更记录
	 * 
	 * @param oldOrderSn
	 *            旧订单号
	 * @param oldPaySn
	 *            旧交易号
	 * @param newOrderSn
	 *            新订单号
	 * @param newPaySn
	 *            新交易号
	 * @param changeInfoList
	 *            保险起期、投保人变更记录
	 * @param insuredChangeInfoMap
	 *            被保人变更记录
	 */
	private void saveChangeInfo(String oldOrderSn, String oldPaySn,
			String newOrderSn, String newPaySn,
			List<PolicyChangeInfoSchema> changeInfoList,
			Map<String, List<PolicyChangeInfoSchema>> insuredChangeInfoMap,String loginName) {
		Date now = new Date();
		PolicyChangeInfoSet set = new PolicyChangeInfoSet();
		String initPaySn = oldPaySn;
		String initOrderSn = oldOrderSn;
		// 查询初始订单号和初始交易号
		String sql = "select initPaySn, initOrderSn from PolicyChangeInfo where newPaySn = ? and newOrderSn = ? order by createdate asc limit 0, 1";
		QueryBuilder qb = new QueryBuilder(sql, oldPaySn, oldOrderSn);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			initPaySn = dt.getString(0, 0);
			initOrderSn = dt.getString(0, 1);
		}
		// 投保人或保险起期变更记录
		for (int i = 0; i < changeInfoList.size(); i++) {
			changeInfoList.get(i).setid(PubFun.GetChangeInfoID());
			changeInfoList.get(i).setcreateDate(now);
			changeInfoList.get(i).setcreateUser(loginName);
			changeInfoList.get(i).setnewPaySn(newPaySn);
			changeInfoList.get(i).setnewOrderSn(newOrderSn);
			changeInfoList.get(i).setoldPaySn(oldPaySn);
			changeInfoList.get(i).setoldOrderSn(oldOrderSn);
			changeInfoList.get(i).setinitPaySn(initPaySn);
			changeInfoList.get(i).setinitOrderSn(initOrderSn);
			set.add(changeInfoList.get(i));
		}

		// 被保人信息变更记录
		if (!insuredChangeInfoMap.isEmpty()) {
			List<PolicyChangeInfoSchema> list = null;
			Set<String> key = insuredChangeInfoMap.keySet();
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				String s = it.next();
				list = insuredChangeInfoMap.get(s);
				if (list != null && list.size() > 0) {
					int size = list.size();
					for (int i = 0; i < size; i++) {
						list.get(i).setid(PubFun.GetChangeInfoID());
						list.get(i).setcreateDate(now);
						list.get(i).setcreateUser(loginName);
						list.get(i).setnewPaySn(newPaySn);
						list.get(i).setnewOrderSn(newOrderSn);
						list.get(i).setoldPaySn(oldPaySn);
						list.get(i).setoldOrderSn(oldOrderSn);
						list.get(i).setinitPaySn(initPaySn);
						list.get(i).setinitOrderSn(initOrderSn);
						set.add(list.get(i));
					}
				}
			}
		}

		if (set.size() > 0) {
			Transaction trans = new Transaction();
			trans.add(set, Transaction.INSERT);
			trans.commit();
		}
	}

	/**
	 * saveBuyForCustomerOldSn:保存代客支付撤单重出数据. <br/>
	 *
	 * @author wwy
	 * @param p_OldOrderSn
	 * @param p_NewOrderSn
	 */
	private void saveBuyForCustomerOldSn(String p_OldOrderSn,
			String p_NewOrderSn) {

		QueryBuilder qb = new QueryBuilder(
				"select Ordersn, BuyForCustomerFlag, OldOrderSn from BuyForCustomerOldSn where ordersn = ?",
				p_OldOrderSn);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			BuyForCustomerOldSnSchema schema = new BuyForCustomerOldSnSchema();
			schema.setid(NoUtil.getMaxNo("buyForCustomerOldSn"));
			schema.setOrdersn(p_NewOrderSn);
			schema.setOldOrderSn(dt.getString(0, 2));
			schema.setBuyForCustomerFlag(dt.getString(0, 1));
			schema.setAddTime(new Date());
			schema.setAddUser(User.getUserName());
			schema.setProp1("订单变更");
			schema.insert();
		}
	}

	/**
	 * 发送报文承保
	 */
	private String send(String CompanyID, String OrderSn) {
		String errMessage = "";
		try {
			// 判断是否核保
			String uwSql = "select count(1) from zdcode where CodeType='UWCheckClassName' "
					+ "and ParentCode='UWCheckClassName' and CodeValue=?";
			int uwFlag = new QueryBuilder(uwSql, CompanyID).executeInt();
			// 核保时
			if (uwFlag > 0) {
				UsersUWCheck uwCheck = new UsersUWCheck();
				Map<String, Object> tMap = uwCheck.moreUWCheck(OrderSn);
				// 核保结果标记
				String tPassFlag = tMap.get("passFlag").toString();
				// 核保不通过的情况
				if ("0".equals(tPassFlag)) {
					// 取得错误信息
					List<Map<String, String>> tList = (List<Map<String, String>>) tMap
							.get("result");
					for (Map<String, String> m : tList) {
						errMessage = errMessage + m.get("RecognizeeName") + ","
								+ m.get("rtnMessage") + ";<br/>";
					}
					return errMessage;
				}
			}

			// 调用经代通
			InsureTransferNew itn = new InsureTransferNew();
			itn.callInsTransInterface(CompanyID, OrderSn, null,"Y");

			String sql1 = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ? ";
			DataTable dt1 = new QueryBuilder(sql1, OrderSn).executeDataTable();
			String error = "";
			for (int i = 0; i < dt1.getRowCount(); i++) {
				String appStatus = dt1.getString(i, 0);
				if (!"1".equals(appStatus)) {
					error += dt1.getString(i, 1) + ",";
				}
			}
			if (StringUtil.isNotEmpty(error)) {
				errMessage = "承保失败，原因：" + error;

			}else{
				// 结算到结算中心
				TBContInsureServlet tb = new TBContInsureServlet();
				tb.callFCContService(OrderSn);
			}
			return errMessage;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMessage = "承保发送失败! 异常原因：" + e.getMessage();
			return errMessage;
		}
	}
	
	public void callFCCont() {
		HttpServletRequest request = ServletActionContext.getRequest();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			String strResponse = "";

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));

			while (StringUtil.isNotEmpty(strMessage = reader.readLine())) {
				strResponse += strMessage;
			}
			JSONObject basicOrders = JSONObject.fromObject(strResponse);
			String OrderSn = basicOrders.getString("orderSn");
			
			if(StringUtil.isNotEmpty(OrderSn)){
				TBContInsureServlet tb = new TBContInsureServlet();
				tb.callFCContService(OrderSn);
			}
		}catch(Exception e){
			
		}
	}
	
	

	/**
	 * 更新原订单状态、保单变更状态
	 * 
	 * @param changedRiskType
	 *            变更的保单ID
	 * @param oldOrderSn
	 *            原订单号
	 */
	private void updateStatus(List<String> changedRiskType, String oldOrderSn) {
		SDInformationRiskTypeSet oldRiskTypeSet = new SDInformationRiskTypeSet();
		SDInformationRiskTypeSchema oldRiskType = new SDInformationRiskTypeSchema();
		SDInformationRiskTypeSchema newRiskType = new SDInformationRiskTypeSchema();
		Transaction trans = new Transaction();
		if (changedRiskType != null && changedRiskType.size() > 0) {
			int size = changedRiskType.size();
			String[] ids;
			for (int i = 0; i < size; i++) {
				oldRiskType = new SDInformationRiskTypeSchema();
				newRiskType = new SDInformationRiskTypeSchema();
				ids = changedRiskType.get(i).split(",");
				oldRiskType.setId(ids[0]);
				if (!oldRiskType.fill()) {
					continue;
				}
				newRiskType.setId(ids[1]);
				if (!newRiskType.fill()) {
					continue;
				}
				// 撤单成功时
				if ("2".equals(oldRiskType.getappStatus())) {
					// 新保单承保成功的情况 原保单的变更状态更新为“1”
					if ("1".equals(newRiskType.getappStatus())) {
						oldRiskType.setchangeStatus("1");
					} else {
						// 新保单未承保成功的情况 原保单的变更状态更新成“3”
						oldRiskType.setchangeStatus("3");
					}

					// 结算中心撤单失败时
				} else if ("4".equals(oldRiskType.getappStatus())) {
					// 新保单承保成功的情况 原保单的变更状态更新为“2”
					if ("1".equals(newRiskType.getappStatus())) {
						oldRiskType.setchangeStatus("2");
					} else {
						// 新保单未承保成功的情况 原保单的变更状态更新成“4”
						oldRiskType.setchangeStatus("4");
					}

					// 其他状态不处理
				} else {
					continue;
				}
				oldRiskTypeSet.add(oldRiskType);
			}

			if (oldRiskTypeSet.size() > 0) {
				trans.add(oldRiskTypeSet, Transaction.UPDATE);
				trans.commit();
				trans.clear();
			}
			// 取得原订单的总保单数
			String sql = "select count(1) from SDInformationrisktype where orderSn = ? ";
			QueryBuilder qb = new QueryBuilder(sql, oldOrderSn);
			int sumCount = qb.executeInt();
			// 取得变更的保单数
			sql += "and changeStatus is not null and changeStatus != ''";
			qb = new QueryBuilder(sql, oldOrderSn);
			int changeCount = qb.executeInt();
			// 部分变更 更新订单状态为有作废
			if (sumCount > changeCount) {
				trans.add(new QueryBuilder(
						"update sdorders set orderStatus = '14' where orderSn = ?",
						oldOrderSn));
			} else {
				// 全部变更 更新订单状态为已作废
				trans.add(new QueryBuilder(
						"update sdorders set orderStatus = '13' where orderSn = ?",
						oldOrderSn));
			}
			trans.commit();
		}
	}

	/**
	 * 订单变更 合作商CPS数据添加.
	 * 
	 * @param p_OldOrderSn
	 * @param p_NewOrderSn
	 */
	public static void partnersCps(String p_OldOrderSn, String p_NewOrderSn,
			String p_Channelsn) {

		try {
			QueryBuilder query_cps = new QueryBuilder(
					"select * from cps c where c.on = ?", p_OldOrderSn);
			DataTable dt = query_cps.executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				String cpsUserId = dt.getString(i, "cid");
				String uid = dt.getString(i, "wi");
				String channel = dt.getString(i, "pw");
				String cpsUserource = dt.getString(i, "os");
				String productName = dt.getString(i, "pna");
				String totleprice = dt.getString(i, "pp");
				OrderConfigNewAction.recordCPS(cpsUserId, uid, channel,
						cpsUserource, p_NewOrderSn, productName, totleprice);
			}

			if (StringUtil.isEmpty(p_Channelsn)) {
				return;
			}

			String webServiceUrl = "";
			if (p_Channelsn.endsWith("dlr")) {
				webServiceUrl = Config.getValue("AGENTSERVERICEURL");
				dealCpsOrder(p_OldOrderSn, p_NewOrderSn, webServiceUrl);
			} else if (p_Channelsn.endsWith("swpt")) {
				webServiceUrl = Config.getValue("CPSSERVERICEURL");
				dealCpsOrder(p_OldOrderSn, p_NewOrderSn, webServiceUrl);
			}

		} catch (Exception e) {
			logger.error("订单变更：合作商INSERT异常! 订单号：" + p_OldOrderSn + e.getMessage());
		}
	}

	public static String dealTBorder(String productId, String planCode,
			String ensure) {

		QueryBuilder db_period = new QueryBuilder(
				"SELECT `Period`  FROM jdproductc WHERE ERiskID = ? AND PlanCode =? AND CoverageYear=? ");
		db_period.add(productId);
		db_period.add(planCode);
		db_period.add(ensure);
		DataTable dt = db_period.executeDataTable();
		String Period = "";
		if (dt.getRowCount() > 0) {
			if (dt.getString(0, 0) != null && dt.getString(0, 0) != "") {
				Period = dt.getString(0, 0).toString();
			}
		}
		return Period;
	}

	@SuppressWarnings("finally")
	public Map<String, Object> calPrem(Map<String, Object> cMap,
			SDOrderServiceImpl service,
			OrderConfigNewServiceImpl orderConfigNewService) {
		// String [] factorValue =new String [1];
		SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> mMap = new HashMap<String, Object>();
		String[] baseInformations = (String[]) cMap.get("baseInformations");// 产品基本信息
		List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) cMap
				.get("riskAppFactior");// 产品投保要素
		List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>) cMap
				.get("dutyFactor");// 产品责任信息
		SDInformationSchema sdi = (SDInformationSchema) cMap
				.get("sdinformation");// 订单项信息
		Map<String, String> insureJson = (Map<String, String>) cMap
				.get("insureJson");// 投保要素信息
		Map<String, String> dutyJson = (Map<String, String>) cMap
				.get("dutyJson");// 责任信息
		String productId = (String) cMap.get("productId");// 产品ID
		String insuredBirthDay = (String) cMap.get("insuredBirthDay");// 被保人生日
		String insuredSex = (String) cMap.get("insuredSex");// 被保人生日
		String channelsn = (String) cMap.get("channelsn");// 渠道：wap、wj
		String effective = "";
		String retCountPrem = "";
		String productPrem = "";

		if (StringUtil.isEmpty(channelsn)) {
			channelsn = "wj";
		}

		List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();

		Double totlePrem = 0.0;// 总保费
		Double countPrice = 0.0;// 折扣后保费

		for (int i = 0; i < riskAppFactior.size(); i++) {
			// OrderRiskAppFactor orderRiskAppFactor =
			// riskAppFactior.get(i);
			if ("TextAge".equals(riskAppFactior.get(i).getFactorType())) {
				String textage = insuredBirthDay;
				String factorValueTemp = insuredBirthDay;
				if ("".equals(factorValueTemp) || factorValueTemp == null) {
					FEMRiskFactorList femr = riskAppFactior.get(i)
							.getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if ("".equals(textage)) {
						if (femr != null && femr.getFactorValue() != null) {
							riskFactor.setFactorValue(orderConfigNewService
									.getBrithdayByFactor(effective,
											femr.getFactorValue()));
						} else {
							riskFactor.setFactorValue("1991-01-01");
						}
					} else {
						riskFactor.setFactorValue(factorValueTemp.toString());
					}
					riskFactor.setAppFactorCode(riskAppFactior.get(i)
							.getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i)
							.getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i)
							.getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				} else {
					riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if (factorValueTemp != null)
						riskFactor.setFactorValue(factorValueTemp.toString());
					riskFactor.setAppFactorCode(riskAppFactior.get(i)
							.getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i)
							.getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i)
							.getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				}
			} else if ("Sex".equals(riskAppFactior.get(i).getFactorType())) {
				String factorValueTemp = insuredSex;
				riskAppFactior.get(i).getFactorValue().get(0);
				List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
				FEMRiskFactorList riskFactor = new FEMRiskFactorList();
				if (factorValueTemp != null)
					riskFactor.setFactorValue(factorValueTemp.toString());
				riskFactor.setAppFactorCode(riskAppFactior.get(i)
						.getAppFactorCode());
				riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
				riskFactor.setIsPremCalFacotor(riskAppFactior.get(i)
						.getIsPremCalFacotor());
				factorValue.add(riskFactor);
				riskAppFactior.get(i).setFactorValue(factorValue);
			} else {
				Object factorValueTemp = insureJson.get(riskAppFactior.get(i)
						.getFactorType());
				riskAppFactior.get(i).getFactorValue().get(0);
				List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
				FEMRiskFactorList riskFactor = new FEMRiskFactorList();
				if (factorValueTemp != null)
					riskFactor.setFactorValue(factorValueTemp.toString());
				riskFactor.setAppFactorCode(riskAppFactior.get(i)
						.getAppFactorCode());
				riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
				riskFactor.setIsPremCalFacotor(riskAppFactior.get(i)
						.getIsPremCalFacotor());
				factorValue.add(riskFactor);
				riskAppFactior.get(i).setFactorValue(factorValue);
			}
		}

		for (OrderDutyFactor orderDutyFactor : dutyFactor) {
			String dutyValueTemp = dutyJson.get(orderDutyFactor.getDutyCode());
			if (orderDutyFactor.getFdAmntPremList() != null) {
				// orderDutyFactor.getFdAmntPremList().clear();
			}
			List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
			if ("nvalue".equals(dutyValueTemp)
					|| StringUtil.isEmpty(dutyValueTemp)) {
				// System.out.println("dutyValueTemp=" + dutyValueTemp);
			} else {
				for (FEMDutyAmntPremList femd : orderDutyFactor
						.getFdAmntPremList()) {
					if (dutyValueTemp.equals(femd.getBackUp1())) {
						fdAmntPremList.add(femd);
					}
				}
				orderDutyFactor.setFdAmntPremList(fdAmntPremList);
				dutyFactorLast.add(orderDutyFactor);
			}
		}
		if (dutyFactorLast.size() == 0) {
			dutyFactorLast = null;
		}
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("baseInformation", baseInformations);
		paramter.put("riskAppFactor", riskAppFactior);
		paramter.put("dutyFactor", dutyFactorLast);
		if (sdi.getstartDate() != null) {
			/**
			 * 保费试算根据保险公司判断是起保日期还是当前日期 heyang 2013-7-19
			 */
			String startdate = sdf_1.format(sdi.getstartDate());
			if (productId.startsWith("1087") || productId.startsWith("1001")
					|| productId.startsWith("1004")
					|| productId.startsWith("2043")) {
				startdate = PubFun.getCurrentDate();
			}
			paramter.put("effective", startdate);
		}
		try {
			Map<String, Object> mapPrem = service
					.getProductPremDutyAmounts(paramter);
			retCountPrem = String.valueOf(mapPrem.get("countPrice").toString());// 折后保费
			productPrem = String.valueOf(mapPrem.get("totlePrem").toString());// 原保费

			totlePrem = totlePrem
					+ Double.parseDouble(mapPrem.get("totlePrem").toString());// 总保费
			countPrice = countPrice
					+ Double.parseDouble(mapPrem.get("countPrice").toString());// 折扣后保费

			mMap.put("productPrem", productPrem);
			mMap.put("retCountPrem", retCountPrem);// 折扣后保费

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			mMap.put("retCountPrem", "0");
			mMap.put("productPrem", "0");
		} finally {
			return mMap;
		}
	}

	private QueryBuilder makeSDInformationProperty(String orderSn,
			String oldRecognizeeSn, String newRecognizeeSn, String newInsuredID) {
		DataTable dt = new QueryBuilder(
				"select * from SDInformationProperty where recognizeeSn = ?",
				oldRecognizeeSn).executeDataTable();
		QueryBuilder qb = null;
		if (dt != null && dt.getRowCount() > 0) {
			StringBuffer insertSQL = new StringBuffer();
			insertSQL
					.append("INSERT INTO SDInformationProperty ( id,createDate,modifyDate,carPlateNo,chassisNumber,hourseAge,hourseNo,hourseType,licenseNumber,propertyAdress,propertyArea1,propertyArea2,propertyArea3,propertyToRecognizee,propertyZip,recognizeeSn,remark1,sdinformationinsured_id) VALUES  (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ? ,? ,?,?,?)");
			qb = new QueryBuilder(insertSQL.toString());
			qb.add(PubFun.GetInformationPropID(orderSn));
			qb.add(dt.getString(0, "createDate"));
			qb.add(dt.getString(0, "modifyDate"));
			qb.add(dt.getString(0, "carPlateNo"));
			qb.add(dt.getString(0, "chassisNumber"));
			qb.add(dt.getString(0, "hourseAge"));
			qb.add(dt.getString(0, "hourseNo"));
			qb.add(dt.getString(0, "hourseType"));
			qb.add(dt.getString(0, "licenseNumber"));
			qb.add(dt.getString(0, "propertyAdress"));
			qb.add(dt.getString(0, "propertyArea1"));
			qb.add(dt.getString(0, "propertyArea2"));
			qb.add(dt.getString(0, "propertyArea3"));
			qb.add(dt.getString(0, "propertyToRecognizee"));
			qb.add(dt.getString(0, "propertyZip"));
			qb.add(newRecognizeeSn);
			qb.add(dt.getString(0, "remark1"));
			qb.add(newInsuredID);
		}
		return qb;
	}

	/**
	 * 处理orderitemoht
	 * 
	 * @param t
	 */
	public void orderItemOthSave(SDInformationInsuredSchema t,
			SDInformationSchema sdinf, Transaction trans,String orderSn) {
		SDOrderItemOthSchema sdorderitemoth = new SDOrderItemOthSchema();
		sdorderitemoth.setid(PubFun.GetOrderItemOthID(sdinf.getorderSn()));
		sdorderitemoth.setcreateDate(t.getcreateDate());
		sdorderitemoth.setmodifyDate(t.getcreateDate());
		sdorderitemoth.setorderSn(sdinf.getorderSn());
		sdorderitemoth.setrecognizeeSn(t.getrecognizeeSn());
		sdorderitemoth.setorderitemSn(PubFun.GetItemOthNo());
		sdorderitemoth.setsdinformationinsured_id(t.getId());
		
		DataTable dt = new QueryBuilder("SELECT branchinnercode,AgencyNo,GroupType,AppntType,Remark,outComCode FROM sdorderitemoth WHERE ordersn = ?",orderSn).executeDataTable();
		if(dt.getRowCount()<=0){
			return;
		}
		sdorderitemoth.setBranchInnerCode(dt.getString(0, "branchinnercode"));
		// 旅行路线
		if (StringUtil.isNotEmpty(t.getdestinationCountry())) {
			String tDestinationValue="";
			String Destinations[] = t.getdestinationCountry().split(",");
			if (Destinations.length > 0) {
				for (int i = 0; i < Destinations.length; i++) {
					String destinationValue = new QueryBuilder("SELECT CASE WHEN destination IS  NULL OR '' THEN"
							 + " (SELECT CONCAT(codeName,'/',codeEnName) AS destination FROM dictionary WHERE codeType='CountryCode' AND codeValue ='"+Destinations[i]+"'"
											+ " AND  insuranceCode='"+sdinf.getproductId().substring(0,4)+"'"
											+ " AND  (productid IS NULL OR productid='')) ELSE destination END AS destination "
											+ " FROM ("
											+ " SELECT  (SELECT CONCAT(codeName,'/',codeEnName) AS destination FROM dictionary WHERE codeType='CountryCode' AND codeValue ='"+Destinations[i]+"'"
											+ " AND productId='"+sdinf.getproductId()+"') destination FROM DUAL ) al").executeString();
					if (i == 0){
						tDestinationValue = destinationValue;
					}else{
						tDestinationValue += "," + destinationValue;
					}
				}
			}
			sdorderitemoth.setItinerary(tDestinationValue);
		}
		//安联业余员信息
		if(StringUtil.isNotEmpty(dt.getString(0, "outComCode"))){
			sdorderitemoth.setOutComCode(dt.getString(0, "outComCode"));
		}
		sdorderitemoth.setAgencyNo(dt.getString(0, "AgencyNo"));
		sdorderitemoth.setGroupType(dt.getString(0, "GroupType"));
		sdorderitemoth.setAppntType(dt.getString(0, "AppntType"));
		sdorderitemoth.setRemark(dt.getString(0, "Remarkb"));
		if ("2023".equals(sdinf.getinsuranceCompany())) {// 存入华泰特殊编号
			String outcode = PubFun.getHTSN(sdinf.getproductOutCode());
			sdorderitemoth.settpySn(outcode);// 存入华泰特殊编号
		}else if("2011".equals(sdinf.getinsuranceCompany())){//2011-太平洋财险核保处理接口
			sdorderitemoth.settpySn(PubFun.GetTpySn());
		}else{
			sdorderitemoth.settpySn(String.valueOf(System.currentTimeMillis()));
		}
		trans.add(sdorderitemoth, Transaction.INSERT);
	}

	/**
	 * 
	 */
	private void makeHealth(String orderSn, SDInformationInsuredSchema insured,
			DataTable healDT, SDInformationInsuredSchema ninsured,
			Transaction trans) {

		if (healDT != null && healDT.getRowCount() >= 1) {

			SDInsuredHealthSchema hschema = new SDInsuredHealthSchema();

			SDInsuredHealthSchema newhschema = null;
			hschema.setorderSn(orderSn);
			SDInsuredHealthSet hset = hschema.query();
			SDInsuredHealthSet newhset = new SDInsuredHealthSet();
			for (int i = 0; i < hset.size(); i++) {
				newhschema = new SDInsuredHealthSchema();
				SchemaUtil.copyFieldValue(hset.get(i), newhschema);
				for (int j = 0; j < healDT.getRowCount(); j++) {
					if (newhschema.gethealthyInfoId().equals(
							healDT.getString(j, "healthyinfoid"))) {
						newhschema.setselectFlag(healDT.getString(j,
								"selectFlag"));
					}
				}
				newhschema.setorderSn(ninsured.getorderSn());
				newhschema.setid("BG"
						+ NoUtil.getMaxNoUseLock("HealthyNo", "SN"));
				newhschema.setinformationSn(ninsured.getinformationSn());
				newhschema.setrecognizeeSn(ninsured.getrecognizeeSn());
				newhschema.setsdinformationinsured_id(ninsured.getId());
				newhset.add(newhschema);
			}
			if (newhset != null && newhset.size() >= 1) {
				trans.add(newhset, Transaction.INSERT);
			}
		}
	}

	/**
	 * @Title: dealCpsOrder
	 * @Description: 处理CPS订单
	 * @return void 返回类型
	 * @author heyang
	 */
	private static void dealCpsOrder(String orderSn, String newOrderSn,
			String webServiceUrl) {
		try {
			Service servicemodel = new ObjectServiceFactory()
					.create(CpsProductBuyService.class);
			CpsProductBuyService service = (CpsProductBuyService) new XFireProxyFactory()
					.create(servicemodel, webServiceUrl);
			service.saveOrder(orderSn);// 处理原有订单
			service.saveOrder(newOrderSn);// 处理新的订单
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
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
			if (StringUtil.isEmpty(offsetPoint)
					|| "0".equals(String.valueOf(offsetPoint))) {
				logger.info("撤单积分返还，订单积分为0，订单号为：{}", ordersn);
				return null;
			} else {
				String paysn = sdOrderSchema.getpaySn();
				if (StringUtil.isNotEmpty(paysn)) {
					Transaction tran = new Transaction();
					int offset = 0;
					BigDecimal PointScalerUnit = new BigDecimal(
							Config.getConfigValue("PointScalerUnit"));
					QueryBuilder qb_order = new QueryBuilder(
							"select id from sdorders where paysn=? and orderstatus !='9' ",
							paysn);
					if (qb_order.executeDataTable().getRowCount() == 0) {
						DataTable dt_point = new QueryBuilder(
								"select Integral from SDIntCalendar where Businessid in (select ordersn from sdorders where paysn=?) and Source='"
										+ IntegralConstant.POINT_SOURCE_OFFSET_POINT
										+ "' and Manner='0' and Status='0' ",
								paysn).executeDataTable();

						if (dt_point.getRowCount() > 0) {
							for (int i = 0; i < dt_point.getRowCount(); i++) {
								offset = offset
										+ Integer.parseInt(dt_point.getString(
												i, 0));
							}
							offset = new BigDecimal(offsetPoint).intValue()
									- offset;
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
						QueryBuilder qb_member = new QueryBuilder(
								"update member set currentValidatePoint =?,usedPoint=?  where id=?");
						qb_member.add(new BigDecimal(currentValidatePoint).add(
								new BigDecimal(offset)).intValue());
						qb_member.add(new BigDecimal(usedPoint).subtract(
								new BigDecimal(offset)).intValue());
						qb_member.add(sdOrderSchema.getmemberId());
						tran.add(qb_member);
						// 在流水表中创建新的撤单流水记录
						SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
						tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID")
								+ "");
						tSDIntCalendarSchema.setMemberId(sdOrderSchema
								.getmemberId());// 会员id
						tSDIntCalendarSchema
								.setIntegral(String.valueOf(offset));// 积分值
						tSDIntCalendarSchema
								.setSource(IntegralConstant.POINT_SOURCE_OFFSET_POINT);// 表示积分抵值返还
						tSDIntCalendarSchema.setBusinessid(ordersn);
						tSDIntCalendarSchema.setManner("0");// 表示收入
						tSDIntCalendarSchema.setStatus("0"); // 表示正常
						tSDIntCalendarSchema.setProp1(sdrisk.getId());// 保单号表id
						tSDIntCalendarSchema.setsvaliDate(PubFun
								.getCurrentDate());// 生效时间
						tSDIntCalendarSchema.setCreateDate(PubFun
								.getCurrentDate());
						tSDIntCalendarSchema.setCreateTime(PubFun
								.getCurrentTime());
						tSDIntCalendarSchema
								.setDescription(IntegralConstant.POINT_SOURCE_OFFSET_POINT_DES);
						tran.add(tSDIntCalendarSchema, Transaction.INSERT);
						if (tran.commit()) {
							logger.info("撤单返还抵值积分会员积分退还账户成功");
						} else {
							logger.error("撤单返还抵值积分会员积分退还账户发生异常，memberid为：{}订单号为：{}"
									,sdOrderSchema.getmemberId(), ordersn);
						}
					} else {
						// DataTable dt_tradeamount = new
						// QueryBuilder("SELECT tradeamount FROM tradesummaryinfo WHERE paysn=?",
						// paysn).executeDataTable();
						// BigDecimal tradeamount=new
						// BigDecimal(dt_tradeamount.getString(0,0));
						BigDecimal integralValue = new BigDecimal(
								sdrisk.getintegralValue());
						offset = PointScalerUnit.multiply(integralValue)
								.intValue();
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

						QueryBuilder qb_member = new QueryBuilder(
								"update member set currentValidatePoint =?,usedPoint=?  where id=?");
						qb_member.add(new BigDecimal(currentValidatePoint).add(
								new BigDecimal(offset)).intValue());
						qb_member.add(new BigDecimal(usedPoint).subtract(
								new BigDecimal(offset)).intValue());
						qb_member.add(sdOrderSchema.getmemberId());
						tran.add(qb_member);
						// 在流水表中创建新的撤单流水记录
						SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
						tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID")
								+ "");
						tSDIntCalendarSchema.setMemberId(sdOrderSchema
								.getmemberId());// 会员id
						tSDIntCalendarSchema
								.setIntegral(String.valueOf(offset));// 积分值
						tSDIntCalendarSchema
								.setSource(IntegralConstant.POINT_SOURCE_OFFSET_POINT);// 表示积分抵值返还
						tSDIntCalendarSchema.setBusinessid(ordersn);
						tSDIntCalendarSchema.setManner("0");// 表示收入
						tSDIntCalendarSchema.setStatus("0"); // 表示正常
						tSDIntCalendarSchema.setProp1(sdrisk.getId());// 保单号表id
						tSDIntCalendarSchema.setsvaliDate(PubFun
								.getCurrentDate());// 生效时间
						tSDIntCalendarSchema.setCreateDate(PubFun
								.getCurrentDate());
						tSDIntCalendarSchema.setCreateTime(PubFun
								.getCurrentTime());
						tSDIntCalendarSchema
								.setDescription(IntegralConstant.POINT_SOURCE_OFFSET_POINT_DES);
						tran.add(tSDIntCalendarSchema, Transaction.INSERT);
						if (tran.commit()) {
							logger.info("撤单返还抵值积分会员积分退还账户成功");
						} else {
							logger.error("撤单返还抵值积分会员积分退还账户发生异常，memberid为：{}订单号为：{}",
									sdOrderSchema.getmemberId(), ordersn);
						}
					}
					if (offset > 0) {
						return new BigDecimal(offset).divide(PointScalerUnit,
								2, BigDecimal.ROUND_HALF_UP).toString();
					}
				} else {
					logger.error("撤单返还抵值积分支付流水号有异常，支付流水号为：{}，订单号为：",
							sdOrderSchema.getpaySn(), ordersn);
				}
			}
		} else {
			logger.info("撤单返还抵值积分传入的订单号为空");
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
	private void calculatePointToZero(SDInformationRiskTypeSchema sdrisk) {

		try {
			// 订单号
			String ordersn = sdrisk.getorderSn();
			// 订单
			SDOrdersSchema sdOrderSchema = new SDOrdersSchema();
			SDOrdersSet sdOrderSet = new SDOrdersSet();
			sdOrderSet = sdOrderSchema.query(new QueryBuilder(
					" where ordersn=?", ordersn));
			sdOrderSchema = sdOrderSet.get(0);
			memberSchema tmemberSchema = new memberSchema();
			tmemberSchema.setid(sdOrderSchema.getmemberId());
			// 订单详情
			SDInformationSchema sdinformation = new SDInformationSchema();
			SDInformationSet sdinformationSet = new SDInformationSet();
			sdinformationSet = sdinformation.query(new QueryBuilder(
					" where ordersn=?", ordersn));
			sdinformation = sdinformationSet.get(0);
			// 积分流水
			SDIntCalendarSchema sdintcalendar = new SDIntCalendarSchema();
			SDIntCalendarSet sdintcalendarset = new SDIntCalendarSet();
			sdintcalendarset = sdintcalendar
					.query(new QueryBuilder(
							" where businessid=? and (source='0' or source='26')  and Manner='0' and  status='1' ",
							ordersn));
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
					BigDecimal totalamount = new BigDecimal(
							sdOrderSchema.gettotalAmount());
					// 保单占订单总金额的百分比
					BigDecimal divid = timePrem.divide(totalamount, 2,
							BigDecimal.ROUND_HALF_UP);
					// 退保保单所占比例计算后积分
					int risktype_point = new BigDecimal(
							sdinformation.getpoint()).multiply(divid)
							.intValue();
					// 判断是全部撤单还是部分撤单,全部撤单积分流水表清零
					if ("9".equals(String.valueOf(sdOrderSchema
							.getorderStatus()))) {
						sdintcalendar.setStatus("2");
						sdintcalendar.setManner("2");
						sdintcalendar.setIntegral("0");
						risktype_point = intcal;
						transaction.add(sdintcalendar, Transaction.UPDATE);
						tmemberSchema.setisBuy("N");
					} else {
						if (StringUtil.isNotEmpty(sdintcalendar.getProp3())) {
							continue;
						} else {
							sdintcalendar.setIntegral(String.valueOf(intcal
									- risktype_point));
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
					Object[] argArr = {ordersn, tmemberSchema.getpoint(), total};
					logger.info("撤单冻结积分归零的用户账户的冻结积分小于订单积分：订单号为：{};用户账户积分为：{};扣除积分总数为：{}",argArr);
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
			logger.error("类PointServiceManager执行方法calculatePointToZero()发生异常！"
					+ e.getMessage(), e);
		}
	}


	private void cancelCpsOrder(String orderSn) throws MalformedURLException {

		logger.info("查询CPS订单！！！");
		String sql = " select channelsn from sdorders where ordersn=? ";
		QueryBuilder qb = new QueryBuilder(sql, orderSn);
		String channelsn = qb.executeOneValue() + "";
		if (StringUtil.isNotEmpty(channelsn)) {

			String webServiceUrl = null;
			if (channelsn.endsWith("dlr")) {
				webServiceUrl = Config.getValue("AGENTSERVERICEURL");

			} else if (channelsn.endsWith("swpt")) {
				webServiceUrl = Config.getValue("CPSSERVERICEURL");

			} else {
				return;
			}
			logger.info("调用CPS接口地址==>{}", webServiceUrl);

			Service servicemodel = new ObjectServiceFactory()
					.create(CpsProductBuyService.class);
			CpsProductBuyService service = (CpsProductBuyService) new XFireProxyFactory()
					.create(servicemodel, webServiceUrl);
			service.saveOrder(orderSn);
			logger.info("调用CPS接口结束");
		}
	}

	/**
	 * 新的servlet接口调用电子保单下载方法
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> pdfDownload(Map<String, String> map) {
		Map<String, String> result = new HashMap<String, String>();
		NameValuePair orderSnP = new NameValuePair("orderSn", map.get(""));
		NameValuePair serialNoP = new NameValuePair("policyNo",
				map.get("policyNo"));
		NameValuePair isSendEmailP = new NameValuePair("isSendEmail", "N");
		NameValuePair isRewriteP = new NameValuePair("isRewrite", "Y");
		NameValuePair channelCodeP = new NameValuePair("channelCode", "wj");
		NameValuePair[] pair = new NameValuePair[] { orderSnP, serialNoP,
				isSendEmailP, isRewriteP, channelCodeP };
		String responseXml = "";
		try {
			responseXml = this.electronicDownloadInterfaceNew(pair);
			Document doc = new SAXBuilder().build(new ByteArrayInputStream(
					responseXml.getBytes("GBK")));
			String isSuccessful = doc.getRootElement().getChild("Head")
					.getChildTextTrim("isSuccessful");
			String message = doc.getRootElement().getChild("Head")
					.getChildTextTrim("message");
			result.put("Message", message);
			if ("Y".equals(isSuccessful)) {
				result.put("Success", "Y");
				result.put("PolicyPath", doc.getRootElement().getChild("Body")
						.getChildTextTrim("policyPath"));
			}
		} catch (Exception e) {
			logger.info("保单({})电子保单下载返回报文：{}", map.get("policyNo"), responseXml);
			logger.error("=====保单(" + map.get("policyNo") + ")电子保单下载功能异常==="+ e.getMessage(), e);
			result.put("Message", "保单(" + map.get("policyNo") + ")电子保单下载功能异常!");
			return result;
		}
		return result;
	}

	public String electronicDownloadInterfaceNew(NameValuePair[] pair)
			throws IOException {

		HttpClient httpClient = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
		String url = Config.interfaceMap
				.getString("PdfDownloadAccessServletNew");
		PostMethod post = new PostMethod(url);
		if (pair != null && pair.length > 0) {
			post.setRequestBody(pair);
		}
		HttpMethodParams params = new HttpMethodParams();
		// 设置读取返回报文信息的字符集（与接口的发送数据流字符集要相同）
		params.setContentCharset("GBK");
		post.setParams(params);
		try {
			httpClient.executeMethod(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					post.getResponseBodyAsStream(), "GBK"));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String ts = stringBuffer.toString();

			return ts;

		} catch (IOException e) {
			throw e;
		} finally {
			post.releaseConnection();
		}
	}

	/**
	 * 重新发送报文
	 */
	public void send() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		InputStream inputStream = null;
		BufferedReader reader = null;
		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			String strResponse = "";

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));

			while (StringUtil.isNotEmpty(strMessage = reader.readLine())) {
				strResponse += strMessage;
			}
			JSONObject basicOrders = JSONObject.fromObject(strResponse);
			/*
			 * JSONObject basicOrderInfo = JSONObject.fromObject(basicOrders
			 * .get("IDs"));
			 */
			String companyID = basicOrders.getString("CompanyID");
			String orderSn = basicOrders.getString("OrderSn");
			// 变化前原订单起保时间
			String svalidateA = basicOrders.getString("svalidateA");
			// 变化后订单起保时间
			String svalidateB = basicOrders.getString("svalidateB");
			// 变化后订单终止时间
			String evaliDate = basicOrders.getString("evaliDate");
			Map<String, String> map = new HashMap<String, String>();
			map.put("CompanyID", companyID);
			map.put("OrderSn", orderSn);
			// 变化前原订单起保时间
			map.put("svalidateA", svalidateA);
			// 变化后订单起保时间
			map.put("svalidateB", svalidateB);
			// 变化后订单终止时间
			map.put("evaliDate", evaliDate);
			try {
				results = send1(map);
			} catch (Exception e) {
				logger.error("报文重发接口异常." + e.getMessage(), e);
			}
			response.getWriter().print(
					JSONObject.fromObject(results).toString());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}

	/**
	 * 重新发送报文
	 */
	public Map<String, Object> send1(Map<String, String> map) {

		// 返回结果
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			// 保险公司ID
			String CompanyID = map.get("CompanyID");
			// 订单号
			String OrderSn = map.get("OrderSn");
			// 变化前原订单起保时间
			String svalidateA = map.get("svalidateA");
			// 变化后订单起保时间
			String svalidateB = map.get("svalidateB");
			// 变化后订单终止时间
			String evaliDate = map.get("evaliDate");

			if (StringUtil.isEmpty(CompanyID) || StringUtil.isEmpty(OrderSn)) {
				results.put("Status", 0);
				results.put("Msg", "发送失败，保险公司或者订单号为空!");
				return results;
			}
			String sql0 = "SELECT orderstatus FROM sdorders WHERE orderSn = ? AND channelsn IN ('b2b_ht','b2b_01','b2b_02','b2c_wap','b2c_pc') ";
			DataTable dt0 = new QueryBuilder(sql0, OrderSn).executeDataTable();
			if (dt0 == null || dt0.getRowCount() != 1
					|| !"7".equals(dt0.getString(0, 0))) {
				results.put("Status", 0);
				results.put("Msg", "发送失败，此订单不存在或者未支付!");
				return results;
			}

			String sql = "select appStatus,riskCode from SDInformationRiskType where orderSn = ? ";
			DataTable dt = new QueryBuilder(sql, OrderSn).executeDataTable();
			if (dt == null || dt.getRowCount() == 0) {
				results.put("Status", 0);
				results.put("Msg", "发送失败，保单表数据不存在记录!");
				return results;
			}

			int j = 0;
			for (int i = 0; i < dt.getRowCount(); i++) {
				String appStatus = dt.getString(i, 0);
				if ("1".equals(appStatus)) {
					j++;
				}
				if (!dt.getString(i, 1).startsWith(CompanyID)) {
					results.put("Status", 0);
					results.put("Msg", "发送失败，此订单对应保险公司与页面选择不一致，此订单不能直接发送!");
					return results;
				}
			}
			if (j == dt.getRowCount()) {
				results.put("Status", 0);
				results.put("Msg", "发送失败，保单表数据都已承保成功!");
				return results;
			}
			
			// 判断是否核保
			String uwSql = "select count(1) from zdcode where CodeType='UWCheckClassName' "
					+ "and ParentCode='UWCheckClassName' and CodeValue=?";
			int uwFlag = new QueryBuilder(uwSql, CompanyID).executeInt();
			// 核保时
			if (uwFlag > 0) {
				UsersUWCheck uwCheck = new UsersUWCheck();
				Map<String, Object> tMap = uwCheck
						.moreUWCheckRetransmit(OrderSn);
				// 核保结果标记
				String tPassFlag = tMap.get("passFlag").toString();
				String tMessage = "";
				// 核保不通过的情况
				if ("0".equals(tPassFlag)) {
					// 取得错误信息
					List<Map<String, String>> tList = (List<Map<String, String>>) tMap
							.get("result");
					for (Map<String, String> m : tList) {
						tMessage = tMessage + m.get("RecognizeeName") + ","
								+ m.get("rtnMessage") + ";<br/>";
					}
					results.put("Status", 0);
					results.put("Msg", tMessage);
					return results;
				}
			}
			if(!svalidateA.equalsIgnoreCase(svalidateB)){
				// 更新电子保单路径
				Transaction ts = new Transaction();
				QueryBuilder qb =new QueryBuilder();
				qb.setSQL("UPDATE sdinformation SET startDate=? ,endDate=?  WHERE ordersn=?  ");
				qb.add(svalidateB);
				qb.add(evaliDate);
				qb.add(OrderSn);
				ts.add(qb);
				QueryBuilder qb_baodan =new QueryBuilder();
				qb_baodan.setSQL("UPDATE sdinformationrisktype SET svaliDate=? ,evaliDate=?  WHERE ordersn=?  and (appstatus='0' or appstatus='' or appstatus is null) ");
				qb_baodan.add(svalidateB);
				qb_baodan.add(evaliDate);
				qb_baodan.add(OrderSn);
				ts.add(qb_baodan);
				ts.commit();
				}
			// 调用经代通
			InsureTransferNew itn = new InsureTransferNew();
			itn.callInsTransInterface(CompanyID, OrderSn, null);
			String sql1 = "select appStatus,insureMsg,policyno,recognizeeSn from SDInformationRiskType where orderSn = ? ";
			DataTable dt1 = new QueryBuilder(sql1, OrderSn).executeDataTable();
			String error = "";
			for (int i = 0; i < dt1.getRowCount(); i++) {
				String appStatus = dt1.getString(i, 0);
				if (!"1".equals(appStatus)) {
					error += dt1.getString(i, 1) + ",";
				}
			}
			if (StringUtil.isNotEmpty(error)) {
				results.put("Status", 0);
				results.put("Msg", "承保失败，原因：" + error);
			} else {
				results.put("Status", 1);
				results.put("Msg", "承保成功!");
				if (StringUtil.isNotEmpty(OrderSn) && OrderSn.startsWith("TB")) {
					Transaction trans = new Transaction();
					String PolicyNo = dt1.getString(0, 2);
					String recognizeeSn = dt1.getString(0, 3);
					Retransmit rr = new Retransmit();
					rr.dealJdriskagencyData(OrderSn, recognizeeSn, PolicyNo,
							trans);
					trans.commit();
				}
				// 结算到结算中心
				TBContInsureServlet tb = new TBContInsureServlet();
				tb.callFCContService(OrderSn);
			}
			return results;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			results.put("Status", 0);
			results.put("Msg", "发送失败! 异常原因：" + e.getMessage());
			return results;
		}
	}

	/**
	 * 取得保险公司证件类型下拉框
	 * 
	 * @param company
	 *            保险公司编码
	 * @return
	 */
	public static Map<String, String> getCertificateSelect(String productid) {
		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,flagType from dictionary where codeType='certificate' and productId = ? order by id asc",
				productid);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			map = dt.toMapx("codeValue", "codeName");
		} else {
			qb = new QueryBuilder(
					"select codeValue,codeName,flagType from dictionary where codeType='certificate' and insuranceCode = ? order by id asc",
					productid.substring(0, 4));
			dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				map = dt.toMapx("codeValue", "codeName");
			}
		}
		return map;
	}

	/**
	 * 取得保险公司性别下拉框
	 * 
	 * @param company
	 *            保险公司编码
	 * @return
	 */
	public static Map<String, String> getSexSelect(String productId) {
		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName from dictionary where codeType='sex' and productId = ? order by id asc",
				productId);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			map = dt.toMapx("codeValue", "codeName");
		} else {
			qb = new QueryBuilder(
					"select codeValue,codeName from dictionary where codeType='sex' and insuranceCode = ? order by id asc",
					productId.substring(0, 4));
			dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				map = dt.toMapx("codeValue", "codeName");
			}
		}
		return map;
	}
	/**
	 * 投保人基础信息复制到被保人信息中
	 * 
	 * @param appnt
	 *            投保人信息
	 * @param insured
	 *            被保人信息
	 */
	private void copyInsured(SDInformationAppntSchema appnt,
			SDInformationInsuredSchema insured) {
		// 姓名
		appnt.setapplicantName(insured.getrecognizeeName());
		if (StringUtil.isNotEmpty(appnt.getapplicantEnName())) {
			appnt.setapplicantEnName(insured.getrecognizeeEnName());
		}
		appnt.setapplicantFirstName(insured.getrecognizeeFirstName());
		appnt.setapplicantLastName(insured.getrecognizeeLashName());
		// 证件类型
		appnt.setapplicantIdentityType(insured.getrecognizeeIdentityType());
		appnt.setapplicantIdentityTypeName(insured.getrecognizeeIdentityTypeName());
		// 证件号码
		appnt.setapplicantIdentityId(insured.getrecognizeeIdentityId());
		// 性别
		appnt.setapplicantSex(insured.getrecognizeeSex());
		appnt.setapplicantSexName(insured.getrecognizeeSexName());
		// 出生日期
		appnt.setapplicantBirthday(insured.getrecognizeeBirthday());
		
	}

	private boolean compare(SDInformationAppntSchema appnt,
			SDInformationInsuredSchema insured, Map<String, String> relationInfo) {

		if (appnt.getapplicantIdentityType().equals(
				insured.getrecognizeeIdentityType())
				&& appnt.getapplicantIdentityTypeName().equals(
						insured.getrecognizeeIdentityTypeName())
				&& appnt.getapplicantIdentityId().equals(
						insured.getrecognizeeIdentityId())) {
			if (!appnt.getapplicantName().equals(insured.getrecognizeeName())
					|| !appnt.getapplicantSex().equals(
							insured.getrecognizeeSex())
					|| !appnt.getapplicantSexName().equals(
							insured.getrecognizeeSexName())
					|| !appnt.getapplicantBirthday().equals(
							insured.getrecognizeeBirthday())// 英文名一致性校验添加：
					|| (StringUtil.isNotEmpty(appnt.getapplicantEnName()) && !appnt.getapplicantEnName().equals(insured.getrecognizeeEnName()))) {
				return true;
			} else {
				String recognizeeAppntRelation = "";
				for (String key : relationInfo.keySet()) {
					if ("本人".equals(relationInfo.get(key))) {
						recognizeeAppntRelation = key;
						break;
					}
				}
				insured.setrecognizeeAppntRelation(recognizeeAppntRelation);
				insured.setrecognizeeAppntRelationName("本人");
			}

		}
		return false;

	}
}
