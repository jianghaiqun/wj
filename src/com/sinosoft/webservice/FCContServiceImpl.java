package com.sinosoft.webservice;

import cn.com.sinosoft.service.CpsBMQueryService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.webservice.fCContService.FCContCancelServiceStub;
import com.sinosoft.webservice.fCContService.FCContServiceStub;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCAppnt;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCBnf;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCContNew;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCContResponse;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCInsured;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCItemCargo;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCItemHouse;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCItemProp;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCOrderNew;
import com.sinosoft.webservice.fCContService.FCContServiceStub.FCPol;
import com.sinosoft.webservice.fCContService.FCContServiceStub.GetFCCont;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FCContServiceImpl {
	private static final Logger logger = LoggerFactory.getLogger(FCContServiceImpl.class);

	/**
	 * 结算中心接口
	 * 
	 * @param paramter
	 * @param orderField
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> FCContCancelService(Map<String, Object> paramter) throws Exception {

		if (paramter == null) {
			logger.error("结算中心退保接口(FCContServiceImpl)异常:" + "无此订单信息!");
			return null;// 无此订单信息，返回失败
		}
		String url = Config.interfaceMap.getString("FCContCancelService");
		FCContCancelServiceStub stub = new FCContCancelServiceStub(url);

		stub._getServiceClient().getOptions()
				.setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);

		FCContCancelServiceStub.GetFCContCancel getFCContCancel = new FCContCancelServiceStub.GetFCContCancel();
		FCContCancelServiceStub.FCContCancelRequest param = new FCContCancelServiceStub.FCContCancelRequest();
		param.setRequestType("FC003");

		param.setOrderNo((String) paramter.get("OrderNo"));
		param.setOpType((String) paramter.get("OpType"));
		param.setSettleDate((String) paramter.get("SettleDate"));
		param.setSXFSum((String) paramter.get("SXFSum"));
		param.setContNo((String) paramter.get("ContNo"));
		param.setIntegralValue((String) paramter.get("IntegralValue"));
		param.setActivityValue((String) paramter.get("ActivityValue"));
		getFCContCancel.setFCContCancelRequest(param);

		try {
			FCContCancelServiceStub.FCContCancelResponse re = stub.getFCContCancel(getFCContCancel).get_return();
			Map<String, String> settleMap = new HashMap<String, String>();
			settleMap.put("ResultCode", re.getResultDTO().getResultCode());
			settleMap.put("ResultInfoDesc", re.getResultDTO().getResultInfoDesc());
			logger.info("结算中心退保接口(FCContServiceImpl)   code:{} desc:{}", re.getResultDTO().getResultCode(),
					 re.getResultDTO().getResultInfoDesc());
			// 记录日志
			return settleMap;
		} catch (Exception e) {
			logger.error("接口FCContService访问结算中心异常" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 结算中心接口
	 * 
	 * @param paramter
	 * @param orderField
	 * @return
	 * @throws Exception
//	 */
	public static Map<String, String> FCContService(Map<String, Object> paramter) throws Exception {

		if (paramter == null) {
			logger.warn("结算中心接口(FCContServiceImpl)异常:无此订单信息!");
			return null;// 无此订单信息，返回失败
		}
		String url = Config.interfaceMap.getString("FCContService");
		FCContServiceStub stub = new FCContServiceStub(url);
		stub._getServiceClient().getOptions()
				.setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);

		GetFCCont gf = new GetFCCont();
		FCContServiceStub.FCContRequest request = new FCContServiceStub.FCContRequest();
		request.setRiskCode((String) paramter.get("RiskCode"));// 险种代码
		request.setRequestType((String) paramter.get("RequestType"));// 请求类型
		request.setFccontNew((FCContNew) paramter.get("FCContNew"));// 保单基本信息
		request.setFcorderNew((FCOrderNew) paramter.get("FCOrderNew"));// 订单基本信息
		/* 新结算流程暂时不用这些数据  2017-01-19 wangcaiyun 
		 * 
		request.setFCAppnt((FCAppnt) paramter.get("FCAppnt"));// 投保人信息
		List<FCInsured> tFCInsuredList = (List<FCInsured>) paramter.get("FCInsuredList");
		if (!tFCInsuredList.isEmpty() && tFCInsuredList.size() > 0) {
			request.setFCInsured((FCInsured[]) (tFCInsuredList.toArray(new FCInsured[tFCInsuredList.size()])));// 被保人信息列表
		}
		List<FCPol> tFCPolList = (List<FCPol>) paramter.get("FCPolList");
		if (tFCPolList != null && tFCPolList.size() > 0) {
			request.setFCPol((FCPol[]) tFCPolList.toArray(new FCPol[tFCPolList.size()]));// 险种/险别列表信息
		}
		List<FCBnf> tFCBnfList = (List<FCBnf>) paramter.get("FCBnfList");
		if (tFCBnfList != null && tFCBnfList.size() > 0) {
			request.setFCBnf((FCBnf[]) tFCBnfList.toArray(new FCBnf[tFCBnfList.size()]));// 受益人信息列表
		}
		request.setFCRiskSpecialInfo((FCRiskSpecialInfo) paramter.get("FCRiskSpecialInfo"));// 寿险险种个性化页面信息
		request.setFCItemAccident((FCItemAccident) paramter.get("FCItemAccident"));// 团体意外险信息
		request.setFCItemHouse((FCItemHouse) paramter.get("FCItemHouse"));// 房屋信息(家财险)\
		List<FCItemProp> tFCItemPropList = (List<FCItemProp>) paramter.get("FCItemPropList");
		if (tFCItemPropList != null && tFCItemPropList.size() > 0) {
			request.setFCItemProp((FCItemProp[]) tFCItemPropList.toArray(new FCItemProp[tFCItemPropList.size()]));// 企业财产清单
		}
		request.setFCItemCredit((FCItemCredit) paramter.get("FCItemCredit"));// 信用信息
		request.setFCItemLiab((FCItemLiab) paramter.get("FCItemLiab"));// 责任信息
		request.setFCItemCargo((FCItemCargo) paramter.get("FCItemCargo"));// 货运险信息
		request.setFCItemCar((FCItemCar) paramter.get("FCItemCar"));// 车辆信息
		*/
		gf.setFCContRequest(request);

		try {
			FCContResponse reponse = stub.getFCCont(gf).get_return();
			FCContServiceStub.ResultDTO rd = reponse.getResultDTO();
			Map<String, String> settleMap = new HashMap<String, String>();
			settleMap.put("settleStatus", rd.getResultCode());
			settleMap.put("settleMgr", rd.getResultInfoDesc());
			logger.info("结算中心接口(FCContServiceImpl)   code:{} desc:{}", rd.getResultCode(), rd.getResultInfoDesc());
			// 记录日志
			return settleMap;
		} catch (Exception e) {
			logger.error("接口FCContService访问结算中心异常" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 结算中心接口调用入口
	 * 
	 * @param insureTypeCode
	 * @param orderID
	 */
	public void callFCContService(String insureTypeCode, String orderSn, String recognizeeSn, String informationSn,
			String paySn) {

		Map<String, String> settleMap = new HashMap<String, String>();
		try {
			settleMap = FCContServiceImpl.FCContService(FCContServiceImpl.setParamterDataByOrderID(insureTypeCode, orderSn,
					recognizeeSn, informationSn, paySn));
			String sql = "where orderSn = ? and recognizeeSn = ? ";
			// 是否是美行保团单
			boolean isGroup = com.sinosoft.lis.pubfun.PubFun.getGroupTypeByOrdersn(orderSn);
			//是否是团险订单
			DataTable dt_group=new QueryBuilder("SELECT id FROM teamuserorder  WHERE  ordersn=?",orderSn).executeDataTable();
			if(dt_group.getRowCount()>0){
				sql = "where orderSn = ? ";
			}else if(isGroup){
				sql = "where orderSn = ? ";
			}
			
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(orderSn);
			if(dt_group.getRowCount()<=0 && !isGroup){
				qb.add(recognizeeSn);
			}
			
			SDInformationRiskTypeSchema sdIRiskTypeSchema = new SDInformationRiskTypeSchema();
			SDInformationRiskTypeSet sdIRiskTypeSet = new SDInformationRiskTypeSet();
			sdIRiskTypeSet = sdIRiskTypeSchema.query(qb);
			if (!sdIRiskTypeSet.isEmpty() && sdIRiskTypeSet.size() > 0) {
				for (int i = 0; i < sdIRiskTypeSet.size(); i++) {
					SDInformationRiskTypeSchema sdIRiskTypeSchema1 = new SDInformationRiskTypeSchema();
					sdIRiskTypeSchema1 = sdIRiskTypeSet.get(i);
					sdIRiskTypeSchema1.setbalanceStatus(settleMap.get("settleStatus"));// 成功(0),失败(1),发送中(2)
					sdIRiskTypeSchema1.setbalanceFlag("1");// 表示已经与网金结算中心交互
					sdIRiskTypeSchema1.setbalanceMsg(settleMap.get("settleMgr"));// 保存结果信息
					sdIRiskTypeSchema1.setmodifyDate(new Date());
					Transaction transaction = new Transaction();
					transaction.add(sdIRiskTypeSchema1, Transaction.UPDATE);
					transaction.commit();
				}
			} else {
				logger.info("经代通保存保险公司返回信息失败！");
			}
		} catch (Exception e) {
			logger.error("更新是否与网金结算中心交互异常" + e.getMessage(), e);
			// 出现异常需要把结算状态从发送中更新成失败
			String sql = "where orderSn = ? and recognizeeSn = ?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(orderSn);
			qb.add(recognizeeSn);
			SDInformationRiskTypeSchema sdIRiskTypeSchema = new SDInformationRiskTypeSchema();
			SDInformationRiskTypeSet sdIRiskTypeSet = new SDInformationRiskTypeSet();
			sdIRiskTypeSet = sdIRiskTypeSchema.query(qb);
			if (!sdIRiskTypeSet.isEmpty() && sdIRiskTypeSet.size() > 0) {
				sdIRiskTypeSchema = sdIRiskTypeSet.get(0);
				sdIRiskTypeSchema.setbalanceStatus("1");// 成功(0),失败(1),发送中(2)
				sdIRiskTypeSchema.setbalanceFlag("1");// 表示已经与网金结算中心交互
				sdIRiskTypeSchema.setbalanceMsg("网金结算中心交互异常");// 保存结果信息
				sdIRiskTypeSchema.setmodifyDate(new Date());
				Transaction transaction = new Transaction();
				transaction.add(sdIRiskTypeSchema, Transaction.UPDATE);
				transaction.commit();
			}
		}
	}

	/**
	 * 通过orderID组装Paramter数据
	 * 
	 * @param orderID
	 * @return
	 */
	private static Map<String, Object> setParamterDataByOrderID(String insureTypeCode, String orderID, String recognizeeSn,
			String informationSn, String paySn) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			List<HashMap<String, String>> SDInformationRiskTypeList = getInsureComReturnByOrderId(orderID, recognizeeSn);
			if (SDInformationRiskTypeList.isEmpty() || SDInformationRiskTypeList.size() <= 0) {
				return null;
			}
			if (!getSDOrderListByID(orderID)) {
				return null;
			}
			List<HashMap<String, String>> SDInformationList = getSDInformation(informationSn);
			Iterator<HashMap<String, String>> sdInformationIt = SDInformationList.iterator();
			while (sdInformationIt.hasNext()) {
				HashMap<String, String> sdInformationMap = (HashMap<String, String>) sdInformationIt.next();
				paramMap.put("FCContNew",
						getTFCContNew(SDInformationRiskTypeList, sdInformationMap, orderID, paramMap, paySn, recognizeeSn));
				paramMap.put("FCOrderNew", getTFCOrderNew(orderID));
				/* 新结算流程暂时不用这些数据  2017-01-19 wangcaiyun 
				 * 
				paramMap.put("FCAppnt", getTFCAppnt(sdInformationMap.get("informationSn")));
				paramMap.put("FCPolList", getTInsuredRiskList(SDInformationRiskTypeList, sdInformationMap));
				List<FCInsured> tFCInsuredList = new ArrayList<FCInsured>();
				List<HashMap<String, String>> sdInfoInsuredList = getInfoInsuredListByInfo(sdInformationMap.get("orderSn"),
						recognizeeSn);
				Iterator<HashMap<String, String>> sdInfoInsuredIt = sdInfoInsuredList.iterator();
				while (sdInfoInsuredIt.hasNext()) {
					HashMap<String, String> sdInfoInsuredMap = (HashMap<String, String>) sdInfoInsuredIt.next();
					tFCInsuredList.add(getTFCInsuredList(sdInfoInsuredMap));
					paramMap.put("FCBnfList", getTFCBnfList(sdInfoInsuredMap.get("recognizeeSn")));
					List<HashMap<String, String>> itemMainList = getItemMainListByInsuredInfo((sdInfoInsuredMap
							.get("recognizeeSn")));
					Iterator<HashMap<String, String>> itemMainIt = itemMainList.iterator();
					while (itemMainIt.hasNext()) {
						HashMap<String, String> itemMainMap = (HashMap<String, String>) itemMainIt.next();
						List<FCItemHouse> tFCItemHouseList = getTFCItemHouseList(sdInfoInsuredMap.get(itemMainMap.get("id")));
						if (!tFCItemHouseList.isEmpty()) {
							paramMap.put("FCItemHouse", tFCItemHouseList.get(0));
						}
						paramMap.put("FCItemPropList", getTFCItemPropList(itemMainMap.get("id")));
						List<FCItemCargo> tFCItemCargoList = getTFCItemCargoList(itemMainMap.get("id"));
						if (!tFCItemCargoList.isEmpty()) {
							paramMap.put("FCItemCargo", tFCItemCargoList.get(0));
						}
					}
				}
				paramMap.put("FCInsuredList", tFCInsuredList);
				*/
			}

		} catch (Exception e) {
			logger.error("网金结算中心接口参数异常：" + e.getMessage(), e);
		}
		return paramMap;
	}

	/**
	 * 货运信息列表
	 * 
	 **/
	private static List<FCItemCargo> getTFCItemCargoList(String itemMainId) {

		List<FCItemCargo> tFCItemCargoList = new ArrayList<FCItemCargo>();
		List<HashMap<String, String>> itemCargoList = getItemCargoListByItemMain(itemMainId);
		Iterator<HashMap<String, String>> itemCargoIt = itemCargoList.iterator();
		while (itemCargoIt.hasNext()) {
			HashMap<String, String> itemCargoMap = (HashMap<String, String>) itemCargoIt.next();
			FCItemCargo tFCItemCargo = new FCItemCargo();
			tFCItemCargo.setGoodsName(itemCargoMap.get("name"));// 货物名称
			tFCItemCargo.setMarks(itemCargoMap.get(""));// 标记??
			if (StringUtil.isNotEmpty(itemCargoMap.get("quantity"))) {
				tFCItemCargo.setQuantity(Integer.parseInt(itemCargoMap.get("quantity")));// 包装及数量
			}
			tFCItemCargo.setLadingNo(itemCargoMap.get("ladingNo"));// 提单号
			tFCItemCargo.setInvoiceNo(itemCargoMap.get("invoiceNo"));// 发票号
			tFCItemCargo.setInvoiceCurrency(itemCargoMap.get("invoiceCurrency"));// 发票金额币别
			if (StringUtil.isNotEmpty(itemCargoMap.get("invoiceAmount"))) {
				tFCItemCargo.setInvoiceAmount(Double.parseDouble(itemCargoMap.get("invoiceAmount")));// 发票金额
			}
			tFCItemCargo.setConveyance(itemCargoMap.get("conveyance"));// 装载运输工具
			tFCItemCargo.setShipNoteNo(itemCargoMap.get("shipNoteNo"));// 起运通知书编号
			tFCItemCargo.setCarryBillNo(itemCargoMap.get("carryBillNo"));// 货票运单号
			tFCItemCargo.setBLName(itemCargoMap.get("bLName"));// 运具名称
			tFCItemCargo.setBLNo(itemCargoMap.get("bLNo"));// 运具牌号
			tFCItemCargo.setTransferConveyance(itemCargoMap.get("transferConveyance"));// 转运工具
			if (StringUtil.isNotEmpty(itemCargoMap.get("startDate"))) {
				tFCItemCargo.setStartDate(stringFormat(itemCargoMap.get("startDate"), "yyyy-MM-dd"));// 起运日期
			}
			tFCItemCargo.setStartSiteName(itemCargoMap.get("startSiteName"));// 起运地
			tFCItemCargo.setViaSiteName(itemCargoMap.get("viaSiteName"));// 中转地
			tFCItemCargo.setEndSiteName(itemCargoMap.get("endSiteName"));// 目的地
			tFCItemCargo.setVoyageNo(itemCargoMap.get("voyageNo"));// 航次
			if (StringUtil.isNotEmpty(itemCargoMap.get("goodsPrice"))) {
				tFCItemCargo.setGoodsPrice(Double.parseDouble(itemCargoMap.get("goodsPrice")));// 货物价值
			}
			tFCItemCargoList.add(tFCItemCargo);
		}
		return tFCItemCargoList;
	}

	/**
	 * 财产信息列表
	 **/
	private static List<FCItemProp> getTFCItemPropList(String itemMainId) {

		List<FCItemProp> tFCItemPropList = new ArrayList<FCItemProp>();
		List<HashMap<String, String>> itemPropList = getItemPropListByItemMain(itemMainId);
		Iterator<HashMap<String, String>> itemPropIt = itemPropList.iterator();
		while (itemPropIt.hasNext()) {
			HashMap<String, String> itemPropMap = (HashMap<String, String>) itemPropIt.next();
			FCItemProp tFCItemProp = new FCItemProp();
			tFCItemProp.setItemNo(itemMainId);// 标的编码
			tFCItemProp.setItemName(itemPropMap.get("name"));// 标的名称
			if (StringUtil.isNotEmpty(itemPropMap.get("amt"))) {
				tFCItemProp.setItemAmt(Integer.parseInt(itemPropMap.get("amt")));// 数量
			}
			tFCItemProp.setInsurePrice(itemPropMap.get("insurePrice"));// 投保定价方式
			// tFCItemProp.setInsAmount(Double.parseDouble(itemPropMap.get("")));//保额??
			// tFCItemProp.setPrem(Double.parseDouble(itemPropMap.get("")));//保费??
			// tFCItemProp.setRate(Double.parseDouble(itemPropMap.get("")));//费率??
			tFCItemProp.setAddress(itemPropMap.get("address"));// 地址
			tFCItemProp.setRemark(itemPropMap.get("remark"));// 备注
			tFCItemPropList.add(tFCItemProp);
		}
		return tFCItemPropList;
	}

	/**
	 * 组装房屋信息列表
	 */
	private static List<FCItemHouse> getTFCItemHouseList(String itemMainId) {

		List<FCItemHouse> tFCItemHouseList = new ArrayList<FCItemHouse>();
		List<HashMap<String, String>> itemHouseList = getItemHouseListByItemMain(itemMainId);
		Iterator<HashMap<String, String>> itemHouseIt = itemHouseList.iterator();
		/**
		 * -------------------------------------房屋信息列表--------------------------
		 * --
		 **/
		while (itemHouseIt.hasNext()) {
			HashMap<String, String> itemHouseMap = (HashMap<String, String>) itemHouseIt.next();
			FCItemHouse tFCItemHouse = new FCItemHouse();
			tFCItemHouse.setHouseOwner(itemHouseMap.get(""));// 房屋所有者??
			tFCItemHouse.setHouseType(itemHouseMap.get(""));// 房屋类型??
			tFCItemHouse.setStructure(itemHouseMap.get("structure"));// 房屋结构
			if (StringUtil.isNotEmpty(itemHouseMap.get("buildArea"))) {
				tFCItemHouse.setBuildArea(Double.parseDouble(itemHouseMap.get("buildArea")));// 建筑面积
			}
			if (StringUtil.isNotEmpty(itemHouseMap.get("unitValue"))) {
				tFCItemHouse.setUnitValue(Double.parseDouble(itemHouseMap.get("unitValue")));// 每平米价格
			}
			if (StringUtil.isNotEmpty(itemHouseMap.get("sumValue"))) {
				tFCItemHouse.setSumValue(Double.parseDouble(itemHouseMap.get("sumValue")));// 总价款
			}
			if (StringUtil.isNotEmpty(itemHouseMap.get("buildTime"))) {
				tFCItemHouse.setBuildTime(itemHouseMap.get("buildTime"));// 建造时间
			}
			tFCItemHouse.setUseage(itemHouseMap.get("useage"));// 用途

			// 被保人财产所在省 HouseProvice ??
			// 被保人财产所在市 HouseCity ??
			// 被保人财产地址 HouseAddress ??
			// 财产所在地邮编 HouseZipcode ??

			tFCItemHouseList.add(tFCItemHouse);
		}
		return tFCItemHouseList;
	}

	/**
	 * 根据被保人编号组装受益人信息
	 */
	private static List<FCBnf> getTFCBnfList(String recognizeeSn) {

		List<FCBnf> tFCBnfList = new ArrayList<FCBnf>();
		List<HashMap<String, String>> sdInfoInsuredBnfList = getInfoInsuredBnfListByInfo(recognizeeSn);
		Iterator<HashMap<String, String>> infoInsuredBnfIt = sdInfoInsuredBnfList.iterator();
		/**
		 * ---------------------------------受益人信息列表-----------------------------
		 **/
		while (infoInsuredBnfIt.hasNext()) {
			HashMap<String, String> infoInsuredBnfMap = (HashMap<String, String>) infoInsuredBnfIt.next();
			FCBnf tFCBnf = new FCBnf();
			// 险种编码 RiskCode ?? 已经取消
			tFCBnf.setName(infoInsuredBnfMap.get("bnfName"));// 姓名
			tFCBnf.setSex(infoInsuredBnfMap.get("bnfSex"));// 性别
			if (StringUtil.isNotEmpty(infoInsuredBnfMap.get("bnfBirthday"))) {
				tFCBnf.setBirthday(infoInsuredBnfMap.get("bnfBirthday"));// 出生日期
			}
			tFCBnf.setIDType(infoInsuredBnfMap.get("bnfIDType"));// 证件类型
			tFCBnf.setIDNo(infoInsuredBnfMap.get("bnfIDNo"));// 证件号码
			tFCBnf.setRelationToInsured(infoInsuredBnfMap.get("relationToInsured"));// 与被保人关系
			tFCBnf.setBnfGrade(infoInsuredBnfMap.get("bnfType"));// 受益级别 ??
			if (StringUtil.isNotEmpty(infoInsuredBnfMap.get("bnfLot"))) {
				tFCBnf.setBnfLot(Double.parseDouble(infoInsuredBnfMap.get("bnfLot")));// 受益份额(%)
			}
			tFCBnfList.add(tFCBnf);
		}
		return tFCBnfList;
	}

	/**
	 * 
	 * 保单信息
	 */
	private static List<FCPol> getTInsuredRiskList(
			List<HashMap<String, String>> sDInformationRiskTypeList, HashMap<String, String> sdInformationMap) {

		List<FCPol> tInsuredRiskList = new ArrayList<FCPol>();
		FCPol tFCPol = new FCPol();
		tFCPol.setRiskCode(sdInformationMap.get("productId"));// 险种编码
		if (StringUtil.isNotEmpty(sdInformationMap.get("startDate"))) {
			tFCPol.setCValiDate(stringFormat(sdInformationMap.get("startDate"), "yyyy-MM-dd"));// 生效日期
		}
		tFCPol.setPeriodFlag(sdInformationMap.get("chargeType"));// 交费年期
		tFCPol.setKindCode(sdInformationMap.get("riskType"));// 险别
		Iterator<HashMap<String, String>> SDInformationRiskTypeIt = sDInformationRiskTypeList.iterator();
		while (SDInformationRiskTypeIt.hasNext()) {
			HashMap<String, String> SDInformationRiskTypeMap = (HashMap<String, String>) SDInformationRiskTypeIt.next();
			if (SDInformationRiskTypeMap.get("amnt") != null && !SDInformationRiskTypeMap.get("amnt").equals("null")
					&& !"".equals(SDInformationRiskTypeMap.get("amnt"))) {
				tFCPol.setAmnt(Double.parseDouble(SDInformationRiskTypeMap.get("amnt")));// 保险金额
			}
			if (SDInformationRiskTypeMap.get("timePrem") != null && !SDInformationRiskTypeMap.get("timePrem").equals("null")
					&& !"".equals(SDInformationRiskTypeMap.get("timePrem"))) {
				tFCPol.setTimePrem(Double.parseDouble(SDInformationRiskTypeMap.get("timePrem")));// 保费
			}
			tFCPol.setMult(Double.parseDouble("1"));// 份数
		}
		tInsuredRiskList.add(tFCPol);
		return tInsuredRiskList;
	}

	/**
	 * 组装被保人信息
	 */
	private static FCInsured getTFCInsuredList(HashMap<String, String> infoInsuredMap) {

		FCInsured tFCInsured = new FCInsured();
		tFCInsured.setAccName(infoInsuredMap.get("recognizeeName"));// 被保险人姓名
		tFCInsured.setIDType(infoInsuredMap.get("recognizeeIdentityType"));// 被保险人证件类型
		tFCInsured.setIDNo(infoInsuredMap.get("recognizeeIdentityId"));// 被保险人证件号码
		tFCInsured.setSex(infoInsuredMap.get("recognizeeSex"));// 被保险人性别
		if (StringUtil.isNotEmpty(infoInsuredMap.get("recognizeeBirthday"))) {
			tFCInsured.setBirthday(infoInsuredMap.get("recognizeeBirthday"));// 被保险人出生日期
		}
		// tFCInsured.setOccupationType(infoInsuredMap.get(""));//被保险人职业类别??
		tFCInsured.setOccupationCode(infoInsuredMap.get("recognizeeOccupation3"));// 被保险人职业
		tFCInsured.setRgtAddress(infoInsuredMap.get("recognizeeAddress"));// 被保险人通信地址
		tFCInsured.setInsuredZipCode(infoInsuredMap.get("recognizeeZipCode"));// 邮政编码
		tFCInsured.setInsuredMobile(infoInsuredMap.get("recognizeeMobile"));// 移动电话
		tFCInsured.setInsuredPhone(infoInsuredMap.get("recognizeeTel"));// 办公/家庭电话
		tFCInsured.setRelationToAppnt(infoInsuredMap.get("recognizeeAppntRelation"));// 与投保人关系
		tFCInsured.setIsMarried(infoInsuredMap.get("recognizeeIsMarry"));// 婚否
		return tFCInsured;
	}

	/**
	 * 
	 * 组装投保人信息
	 */
	private static FCAppnt getTFCAppnt(String informationSn) {

		List<FCAppnt> tFCAppntList = new ArrayList<FCAppnt>();
		List<HashMap<String, String>> infoAppntList = getInfoSDAppntListByInfo(informationSn);
		Iterator<HashMap<String, String>> infoAppntIt = infoAppntList.iterator();
		while (infoAppntIt.hasNext()) {
			HashMap<String, String> infoAppntMap = (HashMap<String, String>) infoAppntIt.next();
			FCAppnt tFCAppnt = new FCAppnt();
			tFCAppnt.setAppntName(infoAppntMap.get("applicantName"));// 投保人姓名
			tFCAppnt.setIDType(infoAppntMap.get("applicantIdentityType"));// 投保人证件类型
			tFCAppnt.setIDNo(infoAppntMap.get("applicantIdentityId"));// 投保人证件号码
			tFCAppnt.setAppntSex(infoAppntMap.get("applicantSex"));// 投保人性别
			if (StringUtil.isNotEmpty(infoAppntMap.get("applicantBirthday"))) {
				tFCAppnt.setAppntBirthday(infoAppntMap.get("applicantBirthday"));// 投保人出生日期
			}
			// tFCAppnt.setOccupationType(infoAppntMap.get(""));//投保人职业类别??
			tFCAppnt.setOccupationCode(infoAppntMap.get("applicantOccupation3"));// 投保人职业
			// tFCAppnt.setProvince(infoAppntMap.get(""));//所在省??
			tFCAppnt.setCity(infoAppntMap.get("applicantArea2"));// 所在市
			tFCAppnt.setAppntEmail(infoAppntMap.get("applicantMobile"));// 邮箱
			tFCAppnt.setAddressNo(infoAppntMap.get("applicantAddress"));// 投保人通信地址

			// 邮政编码 AppntZipCode ??
			// 投保人移动电话 AppntMobile ??
			// 投保人家庭/办公电话(固定电话) AppntPhone ??
			tFCAppntList.add(tFCAppnt);
		}
		return tFCAppntList.get(0);
	}

	/**
	 * 
	 * 组装基本信息
	 */
	private static FCContNew getTFCContNew(
			List<HashMap<String, String>> sDInformationRiskTypeList,
			HashMap<String, String> sdInformationMap, String ordersn,
			Map<String, Object> paramMap, String paySn, String recognizeeSn) {

		FCContNew tFCCont = new FCContNew();
		// 险种代码
		paramMap.put("RiskCode", sdInformationMap.get("productId"));
		// 请求类型
		paramMap.put("RequestType", "FC001");
		Iterator<HashMap<String, String>> SDInformationRiskTypeIt = sDInformationRiskTypeList
				.iterator();
		while (SDInformationRiskTypeIt.hasNext()) {
			HashMap<String, String> SDInformationRiskTypeMap = (HashMap<String, String>) SDInformationRiskTypeIt
					.next();
			// 保单号
			tFCCont.setContNo(SDInformationRiskTypeMap.get("policyNo"));
			// 保单优惠券优惠金额
			tFCCont.setBackUp2(SDInformationRiskTypeMap.get("couponValue"));
			// 保单积分抵值金额
			tFCCont.setBackUp3(SDInformationRiskTypeMap.get("integralValue"));
			// 保单活动优惠金额
			tFCCont.setBackUp4(SDInformationRiskTypeMap.get("activityValue"));
			// 保单活动优惠金额
			tFCCont.setBackUp5(SDInformationRiskTypeMap.get("payPrice"));
			if (StringUtil.isNotEmpty(SDInformationRiskTypeMap
					.get("applyPolicyNo"))) {
				// 投保单号，无核保过程
				tFCCont.setProposalContNo(SDInformationRiskTypeMap
						.get("applyPolicyNo"));
			} else {
				// 投保单号，无核保过程
				tFCCont.setProposalContNo(SDInformationRiskTypeMap
						.get("policyNo"));
			}
			// 保单承保日期（出单日期）,寿险显示保单承保日期，财险显示出单日期
			if (StringUtil.isNotEmpty(SDInformationRiskTypeMap
					.get("insureDate"))) {
				tFCCont.setSignDate(stringFormat(
						SDInformationRiskTypeMap.get("insureDate"),
						"yyyy-MM-dd"));
				tFCCont.setSignTime(stringFormat(
						SDInformationRiskTypeMap.get("insureDate"), "HH:mm:ss"));
			}
			String payamount=obtainTeamInfo(sdInformationMap.get("orderSn"));
			if(!StringUtil.isEmpty(payamount)){
				// 总保费
				tFCCont.setSumPrem(Double.parseDouble(payamount));
				tFCCont.setBackUp5(payamount);
			}else{
				// 美行保团单传订单总保费
				// 是否是美行保团单
				boolean isGroup = com.sinosoft.lis.pubfun.PubFun.getGroupTypeByOrdersn(sdInformationMap.get("orderSn"));
				
				if(isGroup){
					DataTable dt =  obtainGroupInfo(sdInformationMap.get("orderSn"));
					if(dt!=null && dt.getRowCount()>=1){
						// 保单优惠券优惠金额
						tFCCont.setBackUp2(dt.getString(0, "sumCoupon"));
						// 保单积分抵值金额
						tFCCont.setBackUp3(dt.getString(0, "sumIntegral"));
						// 保单活动优惠金额
						tFCCont.setBackUp4(dt.getString(0, "sumActivity"));
						// 保单支付金额
						tFCCont.setBackUp5(dt.getString(0, "payPrice"));
						tFCCont.setSumPrem(Double.parseDouble(dt.getString(0, "totalAmount")));
					}
				}else{
					// 总保费
					tFCCont.setSumPrem(Double.parseDouble((SDInformationRiskTypeMap
							.get("timePrem"))));
				}
			}
			// 保险公司
			tFCCont.setSupplierCode(sdInformationMap.get("insuranceCompany"));
			// 保单起保日期(保单生效日期)
			if (StringUtil.isNotEmpty(sdInformationMap.get("startDate"))) {
				tFCCont.setCValiDate(stringFormat(
						sdInformationMap.get("startDate"), "yyyy-MM-dd")
						.toString());
				// 保单起保时间 ,有些险种会细化到时间点,如旅游险
				tFCCont.setCValiDateTime(stringFormat(
						sdInformationMap.get("startDate"), "HH:mm:ss")
						.toString());
			}
			// 保单终止日期
			if (StringUtil.isNotEmpty(sdInformationMap.get("endDate"))) {
				tFCCont.setCInValiDate(stringFormat(
						sdInformationMap.get("endDate"), "yyyy-MM-dd"));
				// 保单终止时间,有些险种会细化到时间点
				tFCCont.setCInValiTime(stringFormat(
						sdInformationMap.get("endDate"), "HH:mm:ss"));
			}
			// 被保人编码、被保人年龄
			String sql = "select insuredSn,recognizeeAge from SDInformationInsured where orderSn = ? and recognizeeSn = ?";
			QueryBuilder qb = new QueryBuilder();
			qb.append(sql);
			qb.add(ordersn);
			qb.add(recognizeeSn);
			DataTable dt = qb.executeDataTable();
			String recognizeeAge = dt.getString(0, 1);
			tFCCont.setBackUp1(dt.getString(0, 0));
			// 商家内部订单号
			tFCCont.setInnerOrderNo(paySn);
			// 订单号
			tFCCont.setOrderNo(sdInformationMap.get("orderSn"));
			/*// 保单优惠券优惠金额
			tFCCont.setBackUp2(SDInformationRiskTypeMap.get("couponValue"));
			// 保单积分抵值金额
			tFCCont.setBackUp3(SDInformationRiskTypeMap.get("integralValue"));
			// 保单活动优惠金额
			tFCCont.setBackUp4(SDInformationRiskTypeMap.get("activityValue"));
			// 保单支付金额
			tFCCont.setBackUp5(SDInformationRiskTypeMap.get("payPrice"));*/
			// 缴费方式
			tFCCont.setBackUp6(sdInformationMap.get("appType"));

			// 长期险appType必须有值,趸缴为0,年缴为12
			if (StringUtil.isEmpty(sdInformationMap.get("appType"))
					&& StringUtil.isNotEmpty(sdInformationMap.get("chargeYear"))) {
				String chargeYear = sdInformationMap.get("chargeYear");
				if ("0C".equals(chargeYear)) {
					tFCCont.setBackUp6("0");
				} else {
					tFCCont.setBackUp6("12");
				}
			}

			// 缴费年期
			if (StringUtil.isNotEmpty(sdInformationMap.get("chargeYear"))
					&& !"0C".equals(sdInformationMap.get("chargeYear"))) {
				if (sdInformationMap.get("chargeYear").endsWith("A")) {
					if (StringUtil.isNotEmpty(recognizeeAge)) {
						String chargeYear = sdInformationMap.get("chargeYear").substring(0, sdInformationMap.get("chargeYear").length() - 1);
						tFCCont.setBackUp7(String.valueOf(Integer.valueOf(chargeYear)-Integer.valueOf(recognizeeAge)));
					}
				} else {
					tFCCont.setBackUp7(sdInformationMap
							.get("chargeYear")
							.substring(0,
									sdInformationMap.get("chargeYear").length() - 1));
				}
			}

			List<HashMap<String, String>> tradeList = getInfoTradeListByPaySn(paySn);
			if (tradeList != null && tradeList.size() > 0) {
				Iterator<HashMap<String, String>> tradeIt = tradeList.iterator();
				while (tradeIt.hasNext()) {
					HashMap<String, String> tradeMap = (HashMap<String, String>) tradeIt.next();
					// 第三方支付平台代码，产品中心提供接口，电商传值，经代通不需要传
					if ("wap_kqzf".equals(tradeMap.get("PayType"))) {
						tFCCont.setThirdPlatformCode("kqzf");
					} else if ("b2b_ylzf".equals(tradeMap.get("PayType"))) {
						tFCCont.setThirdPlatformCode("ylzf");
					} else if ("bill_ff".equals(tradeMap.get("PayType"))) {
						tFCCont.setThirdPlatformCode("bill_kq");
					} else {
						tFCCont.setThirdPlatformCode(tradeMap.get("PayType"));
					}
					
					tFCCont.setThirdPlatformCodeName(tradeMap.get("PayTypeName"));// 第三方支付平台名称,产品中心提供接口，电商传值，经代通不需要传
					tFCCont.setThirdPlatformTrandeNo(tradeMap.get("TradeSn"));// 第三方支付平台流水号
				}
			} else {
				tradeList = getInfoTradeListByOrderId(ordersn);
				Iterator<HashMap<String, String>> tradeIt = tradeList.iterator();
				while (tradeIt.hasNext()) {
					HashMap<String, String> tradeMap = (HashMap<String, String>) tradeIt.next();
					// 第三方支付平台代码，产品中心提供接口，电商传值，经代通不需要传
					if ("wap_kqzf".equals(tradeMap.get("payType"))) {
						tFCCont.setThirdPlatformCode("kqzf");
					} else if ("b2b_ylzf".equals(tradeMap.get("payType"))) {
						tFCCont.setThirdPlatformCode("ylzf");
					} else if ("bill_ff".equals(tradeMap.get("PayType"))) {
						tFCCont.setThirdPlatformCode("bill_kq");
					} else {
						tFCCont.setThirdPlatformCode(tradeMap.get("payType"));
					}
					
					tFCCont.setThirdPlatformCodeName(tradeMap.get("description"));// 第三方支付平台名称,产品中心提供接口，电商传值，经代通不需要传
					tFCCont.setThirdPlatformTrandeNo(tradeMap.get("tradeSeriNo"));// 第三方支付平台流水号
				}
			}
			// 针对wap站的银联支付，核心生成的支付流水号替换成银联返回的支付流水号
			String ThirdPlatformCode = tFCCont.getThirdPlatformCode();
			if ("wap_yl".equals(ThirdPlatformCode)) {
				List<HashMap<String, String>> tradereceivesn = getInfoTradeReceivesn(ordersn);
				if (tradereceivesn.size() > 0) {
					Map<String, String> tradeMap = tradereceivesn.get(0);
					String paysn = tradeMap.get("receiveRefundId");
					if (StringUtil.isNotEmpty(paysn)) {
						// 商家内部订单号
						tFCCont.setInnerOrderNo(paysn);
					}
				}
			} else if ("bill_kq".equals(ThirdPlatformCode)) {// 针对快钱支付，核心生成的支付订单号，替换成银联返回的支付流水号，用于一键对账
				// 商家内部订单号
				tFCCont.setInnerOrderNo(ordersn);
			}

			// 设置业务经理编号
			// 商务平台
			QueryBuilder bmqb = new QueryBuilder("select channelSn from sdorders where ordersn=?", ordersn);
			String channelSn = bmqb.executeString();
			// 商务平台 代理人
			if ("cps_swpt".equals(channelSn) || "cps_dlr".equals(channelSn)) {
				String busniessManager = getBMByOrderSn(ordersn, channelSn);
				tFCCont.setBackUp10(busniessManager);
			}
			// 保险期限
			tFCCont.setPeriod(sdInformationMap.get("ensure"));
		}
		return tFCCont;
	}

	/**
	 * getBMByOrderSn:获取业务经理. <br/>
	 *
	 * @author wwy
	 * @param orderSn
	 * @return
	 */
	private static String getBMByOrderSn(String orderSn, String channelSn) {

		String busniessManager = null;
		try {
			String sql = "SELECT cid FROM sdorders o, cps c WHERE o.ordersn = c.on AND o.ordersn = '" + orderSn + "'";
			QueryBuilder qb = new QueryBuilder(sql);
			String cpsUserId = qb.executeString();
			if (StringUtil.isNotEmpty(cpsUserId)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("cid", cpsUserId);
				// 调用接口获取 业务经理编号
				String webServiceUrl = null;
				if ("cps_swpt".equals(channelSn)) {
					webServiceUrl = Config.getValue("CPSSERVERICEURL_BM");
				}
				if ("cps_dlr".equals(channelSn)) {
					webServiceUrl = Config.getValue("AGENTSERVERICEURL_BM");
				}

				Service servicemodel = new ObjectServiceFactory().create(CpsBMQueryService.class);
				CpsBMQueryService service = (CpsBMQueryService) new XFireProxyFactory()
						.create(servicemodel, webServiceUrl);
				busniessManager = service.queryBM(cpsUserId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return busniessManager;
	}

	/**
	 * 组装订单基本信息
	 */
	private static FCOrderNew getTFCOrderNew(String orderID) {

		FCOrderNew fcorder = new FCOrderNew();
		List<HashMap<String, String>> sdorderList = getSDOrderInfo(orderID);
		if (sdorderList != null) {
			Iterator<HashMap<String, String>> sdorderIt = sdorderList.iterator();
			while (sdorderIt.hasNext()) {
				HashMap<String, String> sdorderMap = (HashMap<String, String>) sdorderIt.next();
				// 订单总金额
				fcorder.setBackUp1(sdorderMap.get("totalAmount"));
				// 订单优惠券优惠金额
				fcorder.setBackUp3(sdorderMap.get("orderCoupon"));
				// 订单活动优惠金额
				fcorder.setOrderActivity(sdorderMap.get("orderActivity"));
				// 订单积分抵值金额
				fcorder.setBackUp4(sdorderMap.get("orderIntegral"));
				// 订单来源（渠道）
				fcorder.setBackUp5(sdorderMap.get("channelsn"));
				// 订单支付金额
				fcorder.setOrderPayMoney(sdorderMap.get("payPrice"));
				// 订单支付日
				fcorder.setOrderMakeDate(sdorderMap.get("modifyDate"));
				// 优惠券码
				fcorder.setCouponSn(sdorderMap.get("couponSn"));
				// 优惠券ID
				fcorder.setCouponID(sdorderMap.get("Id"));
				// 非折扣券
				if (StringUtil.isEmpty(sdorderMap.get("prop3")) || "01".equals(sdorderMap.get("prop3"))) {
					// 优惠券面值
					fcorder.setCouponParValue(sdorderMap.get("parValue"));
				} else {
					// 优惠券面值
					fcorder.setCouponParValue(sdorderMap.get("sumCoupon"));
				}

				// 优惠券发放人
				fcorder.setProvideUser(sdorderMap.get("realProvideUser"));
				// 优惠券描述
				fcorder.setCouponDesc(sdorderMap.get("direction"));
				// 活动ID
				fcorder.setActvityID(sdorderMap.get("id"));
				// 活动码
				fcorder.setActivitySn(sdorderMap.get("activitysn"));
				// 活动优惠金额
				fcorder.setActivityValue(sdorderMap.get("sumActivity"));
				// 活动创建人
				fcorder.setActivityUser(sdorderMap.get("createuser"));
				// 活动描述
				fcorder.setActivityDesc(sdorderMap.get("description"));
				// 商家订单优惠券优惠金额
				fcorder.setSumCouponValue(sdorderMap.get("sumCoupon"));
				// 商家订单积分抵值金额
				fcorder.setBackUp2(sdorderMap.get("sumIntegral"));
				// 商家订单活动优惠金额(周年庆前的单子只能用参加一个活动，即活动的总优惠金额等于交易的总活动优惠金额)
				fcorder.setSumActivityValue(sdorderMap.get("sumActivity"));

				String paysn = sdorderMap.get("paySn");
				if (StringUtil.isEmpty(paysn)) {
					logger.warn("订单号为{}的单子支付流水号为空,即sdorders表中的paysn为空", orderID);
				} else {
					// 从新交易表查询交易信息
					QueryBuilder qb = new QueryBuilder(
							"select CouponSumAmount,ActivitySumAmount,PointSumAmount,TotalAmount,TradeAmount,CreateDate from tradesummaryinfo where PaySn=?",
							paysn);
					DataTable dt = qb.executeDataTable();
					// 有数据的情况
					if (dt != null && dt.getRowCount() > 0) {
						// 商家订单优惠券优惠金额
						fcorder.setSumCouponValue(dt.getString(0, 0));
						// 商家订单活动优惠金额
						fcorder.setSumActivityValue(dt.getString(0, 1));
						// 商家订单积分抵值金额
						fcorder.setBackUp2(dt.getString(0, 2));
						// 商家订单总金额
						fcorder.setSumMoney(dt.getString(0, 3));
						// 商家订单支付金额
						fcorder.setPayMoney(dt.getString(0, 4));
						// 折扣券
						if ("02".equals(sdorderMap.get("prop3"))) {
							// 优惠券面值
							fcorder.setCouponParValue(dt.getString(0, 0));
						}
						// 订单支付日
						fcorder.setOrderMakeDate(dt.getString(0, 5));
						
						// 无数据，即该订单为周年庆前的单子，商家订单总金额、商家订单支付金额需计算
					} else {
						// 商家订单总金额
						BigDecimal totleAmount = new BigDecimal("0.0");
						// 商家订单支付金额
						BigDecimal payPrice = new BigDecimal("0.0");
						QueryBuilder qb_totalamout = new QueryBuilder(
								"select totalamount,payPrice from sdorders where paysn=?", paysn);
						dt = qb_totalamout.executeDataTable();
						for (int i = 0; i < dt.getRowCount(); i++) {
							totleAmount = totleAmount.add(new BigDecimal(dt.get(i, 0).toString()));
							payPrice = payPrice.add(new BigDecimal(dt.get(i, 1).toString()));
						}
						totleAmount = totleAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						payPrice = payPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
						// 此次支付的订单总金额（无抵消）
						fcorder.setSumMoney(totleAmount.toString());
						fcorder.setPayMoney(payPrice.toString());
					}
				}
			}
		}
		Object[] argArr = {fcorder.getOrderMakeDate(), fcorder.getSumMoney(), fcorder.getBackUp1(),
				fcorder.getCouponSn(),fcorder.getCouponID(), fcorder.getCouponParValue(),
				fcorder.getProvideUser(), fcorder.getPayMoney()};
		logger.info("getTFCOrderNew中fcorder的数据分别为：OrderMakeDate：{};SumMoney:{};" +
				"BackUp1:{};CouponSn:{};CouponID:{};CouponParValue:{};ProvideUser:{};PayMoney{}", argArr);
		return fcorder;
	}

	/**
	 * 按指定的format输出日期字符串
	 */
	public static String stringFormat(String dateStr, String format) {

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat t = new SimpleDateFormat(format);
		String formatString = "";
		try {
			Date d1 = sdf.parse(dateStr);
			formatString = t.format(d1);
		} catch (ParseException e) {
			formatString = null;
			logger.error(e.getMessage(), e);
		}
		return formatString;
	}

	/**
	 * 通过订单号查询订单对象
	 * 
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	private static List<HashMap<String, String>> getSDOrderInfo(String orderID) {

		List<HashMap<String, String>> sdorderList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select a.modifyDate,a.totalAmount,b.Id,b.direction,b.prop3,"
					+ "b.couponSn,b.parValue,b.realProvideUser,a.paySn,acti.description,"
					+ "acti.activitysn,acti.prop1,acti.id,acti.payamount,acti.createuser,"
					+ "acti.accumulation,a.offsetPoint,a.orderCoupon,a.orderIntegral,"
					+ "a.orderActivity,a.payPrice,a.sumCoupon,a.sumIntegral,a.sumActivity,a.channelsn as channelsn ,a.couponsn as ordercouponsn   "
					+ "from SDOrders a left join CouponInfo b on a.couponSn = b.couponSn "
					+ "and b.status='1' LEFT JOIN sdcouponactivityinfo acti ON "
					+ "a.activitySn = acti.activitysn where a.orderSn = ? ";
			String[] sqltemp = { orderID };
			sdorderList = new GetDBdata().query(sql, sqltemp);
			//b2b_app渠道，红包查询b2b数据库
			for (int i = 0; i < sqltemp.length; i++) {
				HashMap<String, String> result=sdorderList.get(i);
				String channelsn=result.get("channelsn");
				if("b2b_app".equals(channelsn)){
					String ordercouponsn=result.get("ordercouponsn");
					if(StringUtil.isNotEmpty(ordercouponsn)){
						DataTable b2b_dt = new QueryBuilder("SELECT  Id,direction,prop3,couponSn,parValue,realProvideUser FROM couponinfo   WHERE  couponsn = '"+ordercouponsn+"'	").executeDataTable("B2B");
						result.put("Id", b2b_dt.getString(0,"Id"));
						result.put("direction", b2b_dt.getString(0,"direction"));
						result.put("prop3", b2b_dt.getString(0,"prop3"));
						result.put("couponSn", b2b_dt.getString(0,"couponSn"));
						result.put("parValue", b2b_dt.getString(0,"parValue"));
						result.put("realProvideUser", b2b_dt.getString(0,"realProvideUser"));
					}
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sdorderList = null;
		}
		return sdorderList;
	}

	/**
	 * 
	 * @param orderID
	 * @return
	 */
	private static List<HashMap<String, String>> getSDInformation(String informationSn) {

		List<HashMap<String, String>> sdInformationList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from SDInformation where informationSn = ?";
			String[] sqltemp = { informationSn };
			sdInformationList = new GetDBdata().query(sql, sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sdInformationList = null;
		}
		return sdInformationList;
	}

	/**
	 * 通过orderID获取交易信息
	 * 
	 * @param orderID
	 * @return
	 */
	private static List<HashMap<String, String>> getInsureComReturnByOrderId(String orderID, String recognizeeSn) {

		List<HashMap<String, String>> insureComReturnList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from SDInformationRiskType where orderSn = ? and recognizeeSn = ? ";
			String[] sqltemp = { orderID, recognizeeSn };
			insureComReturnList = new GetDBdata().query(sql, sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			insureComReturnList = null;
		}
		return insureComReturnList;
	}

	/**
	 * 通过orderID获取交易信息(银联对账使用)
	 * 
	 * @param orderID
	 * @return
	 */
	private static List<HashMap<String, String>> getInfoTradeReceivesn(String orderID) {

		List<HashMap<String, String>> infoTradeList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select distinct tradeSeriNo,receiveRefundId from TradeInformation t  where ordID = '" + orderID
					+ "'";
			infoTradeList = new GetDBdata().query(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoTradeList = null;
		}
		return infoTradeList;
	}

	/**
	 * 获取支付平台返回的支付流水号
	 * 
	 * @param orderID
	 * @return
	 */
	private static List<HashMap<String, String>> getInfoTradeListByOrderId(String orderID) {

		List<HashMap<String, String>> infoTradeList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select distinct t.tradeSeriNo,t.payType,p.description from TradeInformation t left join paybase p on t.payType = p.payType where t.ordID = '"
					+ orderID + "'";
			infoTradeList = new GetDBdata().query(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoTradeList = null;
		}
		return infoTradeList;
	}

	/**
	 * 通过orderID获取交易信息
	 * 
	 * @param orderID
	 * @return
	 */
	private static List<HashMap<String, String>> getInfoTradeListByPaySn(String paySn) {

		List<HashMap<String, String>> infoTradeList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select distinct TradeSn,PayType,PayTypeName from TradeSummaryInfo where PaySn = '" + paySn + "'";
			infoTradeList = new GetDBdata().query(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoTradeList = null;
		}
		return infoTradeList;
	}

	/**
	 * 通过被保人编号获得受益人信息
	 */
	private static List<HashMap<String, String>> getInfoInsuredBnfListByInfo(String recognizeeSn) {

		List<HashMap<String, String>> infoInsuredBnfList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from SDInformationBnf where RecognizeeSn = ?";
			String[] sqltemp = { recognizeeSn };
			infoInsuredBnfList = new GetDBdata().query(sql, sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoInsuredBnfList = null;
		}
		return infoInsuredBnfList;
	}

	/**
	 * 通过标的ID获得财产信息列表
	 * 
	 * @param itemMainID
	 * @return
	 */
	private static List<HashMap<String, String>> getItemPropListByItemMain(String itemMainID) {

		List<HashMap<String, String>> itemPropList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationItemProp where informationItemMain_id = '" + itemMainID + "'";
			itemPropList = new GetDBdata().query(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			itemPropList = null;
		}
		return itemPropList;
	}

	/**
	 * 通过标的ID获得货运信息列表
	 * 
	 * @param itemMainID
	 * @return
	 */
	private static List<HashMap<String, String>> getItemCargoListByItemMain(String itemMainID) {

		List<HashMap<String, String>> itemCargoList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationItemCargo where informationItemMain_id = '" + itemMainID + "'";
			itemCargoList = new GetDBdata().query(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			itemCargoList = null;
		}
		return itemCargoList;
	}

	/**
	 * 通过标的ID获得房屋信息列表
	 * 
	 * @param itemMainID
	 * @return
	 */
	private static List<HashMap<String, String>> getItemHouseListByItemMain(String itemMainID) {

		List<HashMap<String, String>> itemHouseList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationItemHouse where informationItemMain_id = '" + itemMainID + "'";
			itemHouseList = new GetDBdata().query(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			itemHouseList = null;
		}
		return itemHouseList;
	}

	/**
	 * 通过被保人ID获得标的信息列表
	 * 
	 * @param infoInsuredID
	 * @return
	 */
	private static List<HashMap<String, String>> getItemMainListByInsuredInfo(String infoInsuredID) {

		List<HashMap<String, String>> itemMainList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationItemMain where informationInsured_id = '" + infoInsuredID + "'";
			itemMainList = new GetDBdata().query(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			itemMainList = null;
		}
		return itemMainList;
	}

	/**
	 * 通过information_id获得被保人信息列表
	 * 
	 * @param info
	 * @return
	 */
	private static List<HashMap<String, String>> getInfoInsuredListByInfo(String orderSn, String recognizeeSn) {

		List<HashMap<String, String>> infoInsuredList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from SDInformationInsured where orderSn = ? and recognizeeSn = ?";
			String[] sqltemp = { orderSn, recognizeeSn };
			infoInsuredList = new GetDBdata().query(sql, sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoInsuredList = null;
		}
		return infoInsuredList;
	}

	/**
	 * 通过订单明细表编码获得投保人信息
	 * 
	 * 
	 */
	private static List<HashMap<String, String>> getInfoSDAppntListByInfo(String InformationSn) {

		List<HashMap<String, String>> infoAppntList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from SDInformationAppnt where informationSn = ?";
			String[] sqltemp = { InformationSn };
			infoAppntList = new GetDBdata().query(sql, sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoAppntList = null;
		}
		return infoAppntList;
	}

	/**
	 * 通过订单号查询订单对象
	 * 
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	private static boolean getSDOrderListByID(String orderID) {

		try {
			String sql = "select * from SDOrders where orderSn = '" + orderID + "'";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			if (dt.getRowCount() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	/**
	 * 
	 * obtainTeamInfo:(获取团险信息). <br/>
	 *
	 * @author chouweigao
	 * @see bug0002635:获得sdorders表中的PayPrice做为结算总额
	 * @param ordersn
	 * @return 
	 */
	private static String obtainTeamInfo (String ordersn){
		DataTable dt=new QueryBuilder("SELECT s1.PayPrice FROM  sdorders s1 ,teamuserorder s2 WHERE s1.ordersn  =s2.orderSn AND  s1.orderSn =?",ordersn).executeDataTable();
		if(dt.getRowCount()>0){
			return dt.getString(0, 0);
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * obtainGroupInfo:(获取团单信息). <br/>
	 *
	 * @author jiaomengying
	 * @see 
	 * @param ordersn
	 * @return 
	 */
	private static DataTable obtainGroupInfo (String ordersn){
		DataTable dt=new QueryBuilder("SELECT a.`totalAmount`,a.`payPrice`,a.`sumCoupon`,a.`sumIntegral`,a.`sumActivity` FROM sdorders a,sdorderitemoth b WHERE a.ordersn = b.ordersn AND b.grouptype = 'g' AND b.ordersn = ? LIMIT 1",ordersn).executeDataTable();
		return dt;
	}
}
