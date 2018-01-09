package cn.com.sinosoft.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.sinosoft.service.RenewalService;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.DownloadNet;

import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.cms.resend.ResendMain;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.jdt.ServiceClient;
import com.sinosoft.jdt.tb.TBDealInterfaceNew;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
@Service
public class RenewalServiceImpl implements RenewalService{
	private static final Logger logger = LoggerFactory.getLogger(InsureTransferNew.class);
	private String ErrMsg ;
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryRenewal(Map<String, String> map) {
		
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper(); 
		String json = "";
		/*{"policyNo":"301-1-593-17-0000012679-00"};*/
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] result = (Object []) ServiceClient.execute(json, null, null, "2049-RenewalSearch");
		if (result != null) {
			resultsMap = (Map<String, Object>) result[0];
		}
		return resultsMap;
	}

	@Override
	public Map<String, Object> renewal(Map<String, String> map) {
		// TODO Auto-generated method stub
		Map<String, Object> toMap = null;
		String insureTypeCode = map.get("insureTypeCode");
		String p_orderSn = map.get("orderSn");
		String p_insuredSn = map.get("insuredSn");
		String sendEmailFlag = map.get("sendEmailFlag");
		List<String> unSuccess = new ArrayList<String>();
		InsureTransferNew insureTransferNew = new InsureTransferNew();
		toMap =insureTransferNew.callInsTransInterface(insureTypeCode,p_orderSn,unSuccess,sendEmailFlag);
		/*toMap = renewalDealInterface(insureTypeCode,p_orderSn,p_insuredSn,sendEmailFlag);*/
		return toMap;
	}
	/**
	 * 调用径代通承保部分
	 * @param insureTypeCode
	 * @param orderSn
	 * @param insuredSn
	 * @param sendEmailFlag
	 * @return
	 */
	private Map<String, Object> renewalDealInterface(String insureTypeCode,String orderSn,String insuredSn,String sendEmailFlag){
		
		HashMap<String, Object> toMap = null;
		List<HashMap<String,String>> insuredSnList = null;
		List<HashMap<String,String>> pathList = new ArrayList<HashMap<String,String>>();
		DownloadNet db=new DownloadNet();
		String channelsn = "wj";
		
		try {
			// 调用经代通之前判断，如果该订单返回信息已存在，不允许再访问，防止重复提交
			String sql = "select id,recognizeeSn, insuredSn,recognizeeName,informationSn from SDInformationInsured where ordersn=? and insuredSn=?";
			String[] sqltemp = {orderSn,insuredSn};
			insuredSnList = new GetDBdata().query(sql,sqltemp);
			int count=0;
			if(insuredSnList != null && insuredSnList.size()>0){
				for(HashMap<String,String> insuredSnMap : insuredSnList){
					
					/*String insuredSn = insuredSnMap.get("insuredSn");
					if(unSuccess != null && unSuccess.contains(insuredSn)){
						continue;
					}*/
					String recognizeeSn = insuredSnMap.get("recognizeeSn");
					String informationSn = insuredSnMap.get("informationSn");
					if(insuredSn != null && !"".equals(insuredSn)){
						SDInformationRiskTypeSchema sdInformationSchema = new SDInformationRiskTypeSchema();
						SDInformationRiskTypeSet sdInformationSet = new SDInformationRiskTypeSet();
						sdInformationSet = sdInformationSchema.query(new QueryBuilder("where OrderSn = ? and RecognizeeSn = ?",orderSn ,recognizeeSn));
						if(sdInformationSet!=null && sdInformationSet.size()>0){
							sdInformationSchema = sdInformationSet.get(0);
//							if(sdInformationSchema==null || (sdInformationSchema!=null && "1".equals(sdInformationSchema.getappStatus()))){
//								continue;
//							}
							if (sdInformationSchema == null || "1".equals(sdInformationSchema.getappStatus()) || "2".equals(sdInformationSchema.getappStatus())) {
								continue;
							}
							// 承包时间
							String insureDate = sdInformationSchema.getinsureDate();
							// 承包时间为空 则用创建时间
							if (StringUtil.isEmpty(insureDate)) {
								insureDate = DateUtil.toDateTimeString(sdInformationSchema.getcreateDate());
							}
							String id = sdInformationSchema.getId();
							String sql2 = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
							QueryBuilder qb = new QueryBuilder(sql2);
							qb.add("TBDealClassName");
							qb.add("TBDealClassName");
							qb.add(insureTypeCode);
							String className = qb.executeString();
							if (StringUtil.isNotEmpty(className)) {
								this.writeTXT("承保：调用金代通开始"+ " - 订单号" +orderSn);
								String sqlGroupType = "SELECT a.ordersn,b.GroupType,a.ChannelSn FROM sdorders a LEFT JOIN sdorderitemoth b ON a.ordersn=b.ordersn WHERE a.ordersn=? limit 1";
								QueryBuilder queryBuilder = new QueryBuilder(sqlGroupType);
								queryBuilder.add(orderSn);
								DataTable dt = queryBuilder.executeDataTable();
								if(dt != null && dt.getRowCount() > 0){
									channelsn = dt.getString(0, "ChannelSn");
									if(StringUtil.isNotEmpty(dt.getString(0, 1))&&"g".equals(dt.getString(0, 1))){
										if(count == 0){
											toMap = new HashMap<String, Object>();
											count++;
											Class<?> tbDeal = Class.forName(className);
											TBDealInterfaceNew tbDI = (TBDealInterfaceNew)tbDeal.newInstance();
											tbDI.dealData(toMap, insureTypeCode, orderSn,insuredSn);	
										}
									}else{
										toMap = new HashMap<String, Object>();
										Class<?> tbDeal = Class.forName(className);
										TBDealInterfaceNew tbDI = (TBDealInterfaceNew)tbDeal.newInstance();
										tbDI.dealData(toMap, insureTypeCode, orderSn,insuredSn);
									}
								}
	
								this.writeTXT("承保：调用金代通结束"+ " - 订单号" +orderSn);	
							} 
							// 保险公司返回报文中有保费的校验：保险公司返回的保费与网站计算的保费不一致时增加邮件通知
							if(insureTypeCode.startsWith("2105")||insureTypeCode.startsWith("1015")||insureTypeCode.startsWith("2011")||insureTypeCode.startsWith("2096")||insureTypeCode.startsWith("2049")){
								sdInformationSet = sdInformationSchema.query(new QueryBuilder("where OrderSn = ? and RecognizeeSn = ?",orderSn ,recognizeeSn));
								if(sdInformationSet != null && sdInformationSet.size()>0){
									sdInformationSchema = sdInformationSet.get(0);
									Double timePrem = 0.0;
									Double returnPremiums = 0.0;
									if(StringUtil.isNotEmpty(String.valueOf(toMap.get("totalPremium")))){
										 returnPremiums = Double.parseDouble(toMap.get("totalPremium").toString());
									}
									if(StringUtil.isNotEmpty(sdInformationSchema.gettimePrem())){
										timePrem = Double.parseDouble(sdInformationSchema.gettimePrem());
									}
									//只有已承保的订单才校验返回保费是否一致
									if("1".equals(String.valueOf(toMap.get("appStatus")))){
										if(timePrem-returnPremiums != 0.0){
											DownloadNet down=new DownloadNet();
											down.sendPrintErrorMail(orderSn, insuredSn ,"网站计算出的保费与保险公司返回的保费不一致",toMap);
										}
									}
								}
							}
							// 后更新，将保险公司返回结果保存至数据库
							this.updateResultMap(toMap, id);
							
						}
					}
				}
				
				try {

					// 发送消息队列到经代通下载电子保单
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderSn", orderSn);
					map.put("policyNo", "");
					if(Constant.B2B_HT_CHANNELSN.equals(channelsn)||
							Constant.B2B_HT_CHANNELSN01.equals(channelsn)||Constant.B2B_HT_CHANNELSN02.equals(channelsn)||
							Constant.B2C_PC_CHANNELSN.equals(channelsn)||Constant.B2C_WAP_CHANNELSN.equals(channelsn)){
						map.put("channelCode", "mxbnew");
					}else{
						map.put("channelCode", "wj");
					}
					map.put("isSendEmail", sendEmailFlag);
					map.put("isRewrite", "Y");
					map.put("paramMap", null);
					ProducerMQ mq = new ProducerMQ();
					mq.sendToJDT(JSON.toJSONString(map));
				
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				
				// 理财险
				if (insureTypeCode.startsWith("2103")) {
					if (!"1".equals(toMap.get("appStatus"))) {
						return com.sinosoft.cms.pub.PubFun.errMsg(null, toMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("调用经代通接口出现异常" + e.getMessage(), e);
			ErrMsg = "调用经代通接口出现异常";
			ActionUtil.sendInsureAlarmMailByOrderSn(orderSn,ErrMsg);
			ResendMain.resendCacheAdd(orderSn, insureTypeCode, ErrMsg);
		}finally{
			if(insuredSnList!=null && insuredSnList.size()>0 && pathList.size()>0){
				db.getGeneratepolicy(insuredSnList,pathList,orderSn ,insureTypeCode);
			}
		}
		return com.sinosoft.cms.pub.PubFun.sucMsg();	
		
	}
	private Map<String, Object> parseJSON2Map(String jsonStr) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			try {
				// 最外层解析
				JSONObject json = JSONObject.fromObject(jsonStr);
				for (Object k : json.keySet()) {
					Object v = json.get(k);
					// 如果内层还是数组的话，继续解析
					if (v instanceof JSONArray) {
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						Iterator<JSONObject> it = ((JSONArray) v).iterator();
						while (it.hasNext()) {
							JSONObject json2 = it.next();
							list.add(parseJSON2Map(json2.toString()));
						}
						map.put(k.toString(), list);
					} else {
						map.put(k.toString(), v);
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				map	= null;
			}
			
			return map;
		}
	/**
	 * 记录金代通发送时间、返回金代通时间
	 * 
	 * @param content
	 *            记录内容
	 */
	public void writeTXT(String content) {
		FileWriter fw = null;
		try {
			String filepath = Config.getContextRealPath() + File.separator + "wjtojdt" + File.separator + PubFun.getCurrentDate() + ".txt";
			File tFile = new File(filepath);
			File parent = tFile.getParentFile(); 
			if(!parent.exists()){ 
				parent.mkdirs(); 
			}
			 
			if (!tFile.exists()) {
				tFile.createNewFile();
			}
			fw = new FileWriter(tFile, true);
			fw.write(PubFun.getCurrentTime() + " : " + content + "\r\n");
			fw.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);

		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 按指定的format输出日期字符串
	 */
	public String stringToDate(String dateStr, String format) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat t = new SimpleDateFormat(format);
		String formatString = "";
		try {
			formatString = t.format(sdf.parse(dateStr));
		} catch (ParseException e) {
			formatString = null;
			logger.error(e.getMessage(), e);
		}
		return formatString;
	}
	/**
	 * 修改数据方法
	 * 
	 * @param reMap
	 * @param insureTypeCode
	 * @param orderID
	 * @param ID
	 */
	private void updateResultMap(HashMap<String, Object> reMap, String ID) {
		try {
			Transaction transaction = new Transaction();
			SDInformationRiskTypeSchema sdInformationSchema = new SDInformationRiskTypeSchema();
			SDInformationRiskTypeSet sdInformationSet = new SDInformationRiskTypeSet();
			sdInformationSet = sdInformationSchema.query(new QueryBuilder("where id = ? " , ID));
			SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insureDateString=sdf_2.format(new Date());
			if (!sdInformationSet.isEmpty() && sdInformationSet.size() > 0) {
				sdInformationSchema = sdInformationSet.get(0);
				sdInformationSchema.setmodifyDate(new Date());
				sdInformationSchema.setinsureDate(insureDateString);
				if(!StringUtil.isEmpty(reMap.get("applyPolicyNo"))){
					sdInformationSchema.setapplyPolicyNo(reMap.get("applyPolicyNo").toString());// 投保单号
				}
				
				sdInformationSchema.setpolicyNo(reMap.get("policyNo") == null ? "" : reMap.get("policyNo").toString());// 保单号
				sdInformationSchema.setnoticeNo(reMap.get("noticeNo") == null ? "" : reMap.get("noticeNo").toString());// 财务通知单号
				sdInformationSchema.setvalidateCode(reMap.get("validateCode") == null ? "" : reMap.get("validateCode").toString());// 保单验证码
				if (reMap.get("totalPremium") != null) {
					sdInformationSchema.setreturnPremiums(reMap.get("totalPremium").toString());// 保单保费
				}
				sdInformationSchema.setappStatus(reMap.get("appStatus") == null ? "" : reMap.get("appStatus").toString());// 投保状态
				sdInformationSchema.setinsuranceTransCode(reMap.get("TRAN_CODE") == null ? "" : reMap.get("TRAN_CODE").toString());// 保险公司返回交易流水号
				sdInformationSchema.setinsuranceBankCode(reMap.get("BANK_CODE") == null ? "" : reMap.get("BANK_CODE").toString());// 保险公司返回银行编码
				sdInformationSchema.setinsuranceBankSeriNO(reMap.get("BK_SERIAL") == null ? "" : reMap.get("BK_SERIAL").toString());// 保险公司返回银行交易流水号
				sdInformationSchema.setinsuranceBRNO(reMap.get("BRNO") == null ? "" : reMap.get("BRNO").toString());// 保险公司返回系列号
				sdInformationSchema.setinsuranceTELLERNO(reMap.get("TELLERNO") == null ? "" : reMap.get("TELLERNO").toString());// 保险公司反回商户号
				sdInformationSchema.setinsurerFlag(reMap.get("PA_RSLT_CODE") == null ? "" : reMap.get("PA_RSLT_CODE").toString());// 保险公司返回投保结果编码
				sdInformationSchema.setinsureMsg(reMap.get("PA_RSLT_MESG") == null ? "" : reMap.get("PA_RSLT_MESG").toString());// 保险公司返回投保结果信息
				sdInformationSchema.setelectronicCout(reMap.get("policyPath") == null ? "" : reMap.get("policyPath").toString());// 保险公司返回的保单下载路径
				sdInformationSchema.setelectronicPath(reMap.get("remark1") == null ? "" : reMap.get("remark1").toString());//保险公司返回保单下载物理路径
				sdInformationSchema.setbalanceFlag("0");
				//added by yuzj @20160830 for 泰康在线住院保===begin===
				if (reMap.get("startDate") != null) {
					sdInformationSchema.setsvaliDate(DateUtil.parseDateTime(reMap.get("startDate").toString()));// 保单保费
				}
				if (reMap.get("endDate") != null) {
					sdInformationSchema.setevaliDate(DateUtil.parseDateTime(reMap.get("endDate").toString()));// 保单保费
				}
				//added by yuzj @20160830 for 泰康在线住院保===end===
				transaction.add(sdInformationSchema, Transaction.UPDATE);
				transaction.commit();
			}
		} catch (Exception e) {
			logger.error("类InsureTransfer方法updateResultMap()出现异常" + e.getMessage(), e);
		}
	}
}
