package com.sinosoft.cms.dataservice;

import cn.com.sinosoft.util.CsvFileParser;
import cn.com.sinosoft.util.ExcelReadUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.CustomServiceFileSchema;
import com.sinosoft.schema.CustomServiceFileSet;
import com.sinosoft.schema.CustomServiceOrderInfoSchema;
import com.sinosoft.schema.CustomServiceOrderInfoSet;
import com.sinosoft.schema.ZCCatalogSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author wangwenying
 * 
 */
public class CustomerServiceManage extends Page {
 
	@SuppressWarnings("rawtypes")
	public static Mapx initEditDialog(Mapx params) {
		long ID = Long.parseLong(params.get("ID").toString());
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		params = catalog.toMapx();
		return params;
	}

	public void del(){
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		Transaction trans = new Transaction();
		CustomServiceFileSchema tCustomServiceFileSchema = new CustomServiceFileSchema();
		
		CustomServiceFileSet tCustomServiceFileSet = tCustomServiceFileSchema.query(new QueryBuilder("where ID in (" + ids + ")"));
		trans.add(tCustomServiceFileSet, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除附件成功");
		} else {
			Response.setLogInfo(0, "删除附件失败");
		}
		for (int i = 0; i < tCustomServiceFileSet.size(); i++) {
			CustomServiceFileSchema schema = tCustomServiceFileSet.get(i);
			File file = new File(Config.getContextRealPath() + "/temp/" + schema.getFilePath());
			FileUtil.delete(file);
		}
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String Name = (String) dga.getParams().get("SearchName");
		QueryBuilder qb = new QueryBuilder("select * from CustomServiceFile where 1=1 ");
		if (StringUtil.isNotEmpty(Name)) {
			qb.append(" and FileName like ?", "%" + Name.trim() + "%");
		}
	
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by ID desc");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx uploadInit(Mapx params) {
		// 允许的类型
		String allowType = "xls,xlsx,csv";
		params.put("allowType", allowType);
		
		//允许上传附件大小设置
		String fileSize = Config.getValue("AllowCustomerServcieFileSize");
		if(StringUtil.isEmpty(fileSize)){
			fileSize = "20971520"; //默认20M
		}
		params.put("fileMaxSize", fileSize);
		return params;
	}
	
	/**
	 * 解析文件
	 */
	public void deal() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEIMAGE, "处理失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
			return;
		}
		CustomServiceFileSchema tCustomServiceFileSchema = new CustomServiceFileSchema();
		CustomServiceFileSet tCustomServiceFileSet = tCustomServiceFileSchema.query(new QueryBuilder("where ID in (" + ids + ")"));
		
		if (tCustomServiceFileSet.size() == 0) {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
		Map<String, Object> result = dealFile(tCustomServiceFileSet);
		Response.setStatus((Integer)result.get("status"));
		Response.setMessage((String)result.get("message"));
	}
	
	/**
	 * 解析文件
	 * 
	 * @param tCustomServiceFileSet
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, Object> dealFile(CustomServiceFileSet tCustomServiceFileSet){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		int status = 1;
		Map<String, Object> filesInfo = new HashMap<String, Object>();
		for (int i = 0; i < tCustomServiceFileSet.size(); i++) {
			CustomServiceFileSchema schema = tCustomServiceFileSet.get(i);
			
			String filePath = schema.getFilePath();
			if (StringUtil.isNotEmpty(filePath)) {
				getFilesInfo(schema, filesInfo);
			}else{
				status = 0;
			}
		}
		
		// 时间format
		String format = "yyyy-MM-dd hh:mm:ss";
		
		if (!filesInfo.isEmpty()) {
			if (null != filesInfo.get("error")
					&& StringUtil.isNotEmpty((String) filesInfo.get("error"))) {
				status = 0;
			} else {
				// 呼入
				if (null != filesInfo.get("ComeCall")) {
					List<Map<String, String>> comeCallData = (List<Map<String, String>>) filesInfo
							.get("ComeCall");
					for (Map<String, String> map : comeCallData) {
						// 来电电话号
						String telephone = map.get("1");
						String callDateStr = map.get("10");
						if (StringUtil.isNotEmpty(telephone) && StringUtil.isNotEmpty(callDateStr) && !"-".equals(callDateStr)) {
							// 订单是否存在
							List<Map<String, String>> orderInfos = queryOrderByPhoneForComeCall(telephone, callDateStr);
							for (int i = 0; i < orderInfos.size(); i++) {
								Map<String, String> orderInfo = orderInfos.get(i);
								if (StringUtil.isNotEmpty(orderInfo.get("orderSn"))) {
									Map<String, String> info = new HashMap<String, String>();
									info.put("OrderSn", orderInfo.get("orderSn"));
									info.put("ConversionType", "电话");
									info.put("CustomerPhone", telephone);
									info.put("EmployeeNum", map.get("6"));
									info.put("EmployeeName", map.get("7"));
									// 插入数据
									insertInfo(info);
								}
							}
						}
					}
				}
				// 呼出
				else if (null != filesInfo.get("CallOut")) {
					List<Map<String, String>> callOutData = (List<Map<String, String>>) filesInfo
							.get("CallOut");
					for (Map<String, String> map : callOutData) {
						// 来电电话号
						String telephone = map.get("1");
						String callDateStr = map.get("7");
						if (StringUtil.isNotEmpty(telephone) && StringUtil.isNotEmpty(callDateStr) && !"-".equals(callDateStr)) {
							// 订单是否存在
							List<Map<String, String>> orderInfos = queryOrderByPhoneForCallOut(telephone, callDateStr);
							for (int i = 0; i < orderInfos.size(); i++) {
								Map<String, String> orderInfo = orderInfos.get(i);
								if (StringUtil.isNotEmpty(orderInfo.get("orderSn"))) {
									Map<String, String> info = new HashMap<String, String>();
									info.put("OrderSn", orderInfo.get("orderSn"));
									info.put("ConversionType", "电话");
									info.put("CustomerPhone", telephone);
									info.put("EmployeeNum", map.get("4"));
									info.put("EmployeeName", map.get("5"));
									// 插入数据
									insertInfo(info);
								}
							}
						}
					}
				}
				// xiaoneng
				else if (null != filesInfo.get("xiaoneng")) {
					List<Map<String, String>> xiaonengData = (List<Map<String, String>>) filesInfo
							.get("xiaoneng");
					for (Map<String, String> map : xiaonengData) {
						// 轨迹详情
						String browsingRecord = map.get("23");
						String orderSn = fetchStringByRegexForBrowsingRecord(browsingRecord);
						if (StringUtil.isNotEmpty(orderSn)) {
							// 订单是否存在
							Map<String, String> orderInfo = queryOrderByOrderSn(orderSn);
							if (StringUtil.isNotEmpty(orderInfo.get("orderSn"))) {
								// 来电是否在订单支付之前
								String insureDate = orderInfo.get("insureDate");
								if (StringUtil.isNotEmpty(insureDate)) {
									Date payDate = DateUtil.parse(insureDate, format);
									String startDateStr = map.get("1");
									if (!"-".equals(startDateStr) && StringUtil.isNotEmpty(startDateStr)) {
										startDateStr = startDateStr.replace("  ", " ");
										Date startDate = DateUtil.parse(startDateStr, "yyyy/MM/dd hh:mm");
										if (null != startDate && startDate.before(payDate) && DateUtil.addDay(startDate, 7).after(payDate)) {
											String employeeName = map.get("6");
											if (StringUtil.isNotEmpty(employeeName)) {
												String[] employeeNames = map.get("6").split(",");

												for (String str : employeeNames) {
													Map<String, String> info = new HashMap<String, String>();
													info.put("OrderSn", orderInfo.get("orderSn"));
													info.put("ConversionType", "xiaoneng");
													info.put("EmployeeName", str);
													// 插入数据
													insertInfo(info);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				// 旺旺WWCService 淘宝订单TBOrder
				else if (null != filesInfo.get("WWCService") && null != filesInfo.get("TBOrder")) {
					List<Map<String, String>> WWCServiceData = (List<Map<String, String>>) filesInfo
							.get("WWCService");
					List<Map<String, String>> TBOrderData = (List<Map<String, String>>) filesInfo
							.get("TBOrder");
					for (Map<String, String> wwMap : WWCServiceData) {
						// 客户旺旺名称
						String customerWWName = wwMap.get("3");
						
						
						if (StringUtil.isNotEmpty(customerWWName)) {
							for (Map<String, String> tbMap : TBOrderData) {
								if (tbMap.containsValue(customerWWName)) {
									String channelOrderSn = tbMap.get("0");
									channelOrderSn = fetchStringByRegexForDoubleQuote(channelOrderSn);
									if (StringUtil.isNotEmpty(channelOrderSn)) {
										// 订单是否存在
										String startDateStr = wwMap.get("1");
										Map<String, String> orderInfo = queryOrderByChannelOrderSn(channelOrderSn, startDateStr);
										//LogUtil.info("channelOrderSn: " + channelOrderSn + "startDateStr:" + startDateStr + " orderSn:" + orderInfo.get("orderSn"));
										if (StringUtil.isNotEmpty(orderInfo.get("orderSn"))) {
														
											String employeeWWName = wwMap.get("4");
											if (StringUtil.isNotEmpty(employeeWWName)) {
												Mapx mapx = CacheManager.getMapx("Code", "Employee.WW");
												if (null != mapx.get(employeeWWName)) {
													Map<String, String> info = new HashMap<String, String>();
													info.put("OrderSn", orderInfo.get("orderSn"));
													info.put("ConversionType", "旺旺");
													info.put("EmployeeName", (String) mapx.get(employeeWWName));
													info.put("BuyerWWName", wwMap.get("3"));
													info.put("ServiceWWName", wwMap.get("4"));
													info.put("ChannelOrderSn", channelOrderSn);
													// 插入数据
													insertInfo(info);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				// 人工导入转化
				else if (null != filesInfo.get("ManualInput")) {
					List<Map<String, String>> manualInputData = (List<Map<String, String>>) filesInfo
							.get("ManualInput");
					for (Map<String, String> map : manualInputData) {
						// 订单是否存在
						Map<String, String> orderInfo = queryOrderByOrderSn(map.get("0"));
						if (StringUtil.isNotEmpty(orderInfo.get("orderSn"))) {
							Map<String, String> info = new HashMap<String, String>();
							info.put("OrderSn", orderInfo.get("orderSn"));
							info.put("ConversionType", "xiaoneng");
							info.put("EmployeeName", map.get("1"));
							// 插入数据
							insertInfo(info);
						}
					}
				}
				// 未知转化
				else {
					
				}
			}
		}
		
		result.put("status", status);
		if (0 == status) {
			result.put("message", "操作失败！");
		}
		else {
			result.put("message", "操作成功！");
		}
		return result;
	}
	
	/**
	 * 获取文件内容
	 * 
	 * @param schema
	 * @param filesInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getFilesInfo(CustomServiceFileSchema schema,
			Map<String, Object> filesInfo) {

		File file = new File(Config.getContextRealPath() + "/temp/" + schema.getFilePath());
		
		if ("xls".equals(schema.getFileType())
				|| "xlsx".equals(schema.getFileType())) {
			
			if (file.exists() && file.isFile()) {
				try {
					List<Map<String, String>> data = ExcelReadUtil.getData(file, 0);
					
					if (data.size() > 1) {
						int column = data.get(0).size();
						// 删除第一个元素，删除列标题
						data.remove(0);
						// 呼入信息
						if (column == 22) {
							if (null != filesInfo.get("ComeCall")) {
								List<Map<String, String>> tempData = (List<Map<String, String>>) filesInfo.get("ComeCall");
								
								filesInfo.put("ComeCall", tempData.addAll(data));
							} else {
								filesInfo.put("ComeCall", data);
							}
						} 
						// 呼出信息
						else if (column == 20) {
							if (null != filesInfo.get("CallOut")) {
								List<Map<String, String>> tempData = (List<Map<String, String>>) filesInfo.get("CallOut");
								filesInfo.put("CallOut", tempData.addAll(data));
							} else {
								filesInfo.put("CallOut", data);
							}
						} 
						// 旺旺客服对应信息 附件3 
						else if (column == 7) {
							if (null != filesInfo.get("WWCService")) {
								List<Map<String, String>> tempData = (List<Map<String, String>>) filesInfo.get("WWCService");
								filesInfo.put("WWCService", tempData.addAll(data));
							} else {
								filesInfo.put("WWCService", data);
							}
						} 
						// 手工录入
						else if (column == 2) {
							if (null != filesInfo.get("ManualInput")) {
								List<Map<String, String>> tempData = (List<Map<String, String>>) filesInfo.get("ManualInput");
								filesInfo.put("ManualInput", tempData.addAll(data));
							} else {
								filesInfo.put("ManualInput", data);
							}
						}
						else {
							logger.warn("文件格式不正确！");
							filesInfo.put("error", "文件格式不正确！");
						}
					}
				} catch (Exception e) {
					logger.error("读取文件数据异常！" + e.getMessage(), e);
				}
			} else {
				logger.error("文件丢失或上传失败！");
				filesInfo.put("error", "文件丢失或上传失败！");
			}
			
		} else if ("csv".equals(schema.getFileType())) {
			try {
				CsvFileParser csvUtil = new CsvFileParser();
				List<Map<String, String>> data = csvUtil.ReadFileToList(file);
				
				if (data.size() > 0) {
					int colNum = data.get(0).size();
							
					// xiaoneng
					if (colNum == 24) {
						if (null != filesInfo.get("xiaoneng")) {
							List<Map<String, String>> tempData = (List<Map<String, String>>) filesInfo.get("xiaoneng");
							filesInfo.put("xiaoneng", tempData.addAll(data));
						} else {
							filesInfo.put("xiaoneng", data);
						}
					} 
					// 淘宝订单信息
					else if (colNum == 41) {
						if (null != filesInfo.get("TBOrder")) {
							List<Map<String, String>> tempData = (List<Map<String, String>>) filesInfo.get("TBOrder");
							filesInfo.put("TBOrder", tempData.addAll(data));
						} else {
							filesInfo.put("TBOrder", data);
						}
					} else {
						logger.warn("文件格式不正确！");
						filesInfo.put("error", "文件格式不正确！");
					}
				}
			} catch (Exception e) {
				logger.error("读取文件数据异常！" + e.getMessage(), e);
			}
		} else {
			logger.warn("未知文件类型！");
			filesInfo.put("error", "未知文件类型！");
		}
		
		// 删除文件记录和文件。
		schema.delete();
		FileUtil.delete(file);
		
		return filesInfo;
	}
	
	/**
	 * 获取订单号
	 * 
	 * @param telephone
	 * @return
	 */
	private List<Map<String, String>> queryOrderByPhoneForComeCall (String telephone, String callDateStr) {

		// 时间format
		String format = "yyyy-MM-dd hh:mm:ss";
		callDateStr = callDateStr.replace("  ", " ");
		Date callDate = DateUtil.parse(callDateStr, format);
		// 七天内的时间
		Date callDateAddDay = DateUtil.addDay(callDate, 7);
		
		QueryBuilder qb = new QueryBuilder(" SELECT o.orderSn, risk.insureDate "
				+ " FROM sdorders o, sdinformationappnt appnt ,sdinformationrisktype risk "
				+ " WHERE appnt.applicantMobile = ? "
				+ " AND o.ordersn=risk.orderSn AND risk.informationSn=appnt.informationSn AND o.orderStatus >=7 AND o.orderStatus <> 8  "
				// 打电话七天内的所有订单
				+ " AND UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + callDateStr + "','%Y-%m-%d %H:%i:%s')) "
				+ " AND UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('" + DateUtil.toString(callDateAddDay, format) + "','%Y-%m-%d %H:%i:%s')) "
				+ " AND NOT EXISTS (SELECT id FROM PolicyChangeInfo p WHERE p.newOrderSn = o.orderSn)", telephone);
		
		DataTable dt = qb.executeDataTable();
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				Map<String, String> tempMap = new HashMap<String, String>();
				tempMap.put("orderSn", dt.getString(i, "orderSn"));
				tempMap.put("insureDate", dt.getString(i, "insureDate"));
				result.add(tempMap);
			}
		}
		
		return result;
	}
	
	/**
	 * 获取订单号
	 * 
	 * @param telephone
	 * @return
	 */
	private List<Map<String, String>> queryOrderByPhoneForCallOut (String telephone, String callDateStr) {

		// 时间format
		String format = "yyyy-MM-dd hh:mm:ss";
		callDateStr = callDateStr.replace("  ", " ");
		Date callDate = DateUtil.parse(callDateStr, format);
		// 18个月前
		Date callDateAddMonth = DateUtil.addMonth(callDate, -18);
		
		// 查询出18个月前所有已支付订单，是当前电话号码的订单
		QueryBuilder qb1 = new QueryBuilder("SELECT distinct appnt.applicantIdentityId FROM sdinformationappnt appnt, "
				+ " sdinformation info, sdinformationrisktype risk WHERE "
				+ " info.orderSn = risk.orderSn "
				// 打电话18个月前至今所有订单
				+ " AND UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + DateUtil.toString(callDateAddMonth, format) + "','%Y-%m-%d %H:%i:%s')) "
				+ " AND UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('" + callDateStr + "','%Y-%m-%d %H:%i:%s')) "
				+ " AND appnt.informationSn = info.informationSn AND appnt.applicantMobile = ?", telephone);

		DataTable dt1 = qb1.executeDataTable();
		String identitySql = "";
		if (dt1.getRowCount() > 0) {
			identitySql = "";
			for (int i = 0; i < dt1.getRowCount(); i++) {
				if (StringUtil.isEmpty(identitySql)) {
					identitySql = "'" + dt1.getString(i, "applicantIdentityId") + "'";
				} else {
					identitySql += ",'" + dt1.getString(i, "applicantIdentityId") + "'";
				}
			}
			identitySql = " appnt.applicantIdentityId in (" + identitySql + ")";
		}

		// 七天内的时间
		Date callDateAddDay = DateUtil.addDay(callDate, 7);
		QueryBuilder qb2 = new QueryBuilder("SELECT o.orderSn, risk.insureDate FROM sdorders o, sdinformationappnt appnt, "
				+ " sdinformationrisktype risk WHERE "
				+ " appnt.informationSn = risk.informationSn "
				+ " AND o.orderSn = risk.orderSn "
				+ " AND o.orderStatus >=7 AND o.orderStatus <> 8 "
				// 打电话七天内的所有订单
				+ " AND UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + callDateStr.trim() + "','%Y-%m-%d %H:%i:%s')) "
				+ " AND UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('" + DateUtil.toString(callDateAddDay, format) + "','%Y-%m-%d %H:%i:%s')) ");
		
				if (StringUtil.isNotEmpty(identitySql)) {
					qb2.append(" AND (");
					qb2.append(identitySql);
					qb2.append(" OR appnt.applicantMobile = ?)", telephone);
				} else {
					qb2.append(" AND appnt.applicantMobile = ?", telephone);
				}
				// 删除变更单
				qb2.append(" AND NOT EXISTS (SELECT id FROM PolicyChangeInfo p WHERE p.newOrderSn = o.orderSn) ");
				
		DataTable dt = qb2.executeDataTable();
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				Map<String, String> tempMap = new HashMap<String, String>();
				tempMap.put("orderSn", dt.getString(i, "orderSn"));
				tempMap.put("insureDate", dt.getString(i, "insureDate"));
				result.add(tempMap);
			}
		}
		
		return result;
	}
	
	/**
	 * 通过 订单号获取订单信息
	 * 
	 * @param orderSn
	 * @return
	 */
	private Map<String, String> queryOrderByOrderSn (String orderSn) {
		QueryBuilder qb = new QueryBuilder(" SELECT o.orderSn, risk.insureDate FROM sdorders o, sdinformationrisktype risk  "
				+ " WHERE o.orderSn = risk.orderSn "
				+ " AND o.orderStatus >=7 AND o.orderStatus <> 8 "
				+ " AND o.orderSn = ?", orderSn);
		
		DataTable dt = qb.executeDataTable();
		
		Map<String, String> result = new HashMap<String, String>();
		if (dt.getRowCount() > 0) {
			result.put("orderSn", dt.getString(0, "orderSn"));
			result.put("insureDate", dt.getString(0, "insureDate"));
		}
		
		return result;
	}
	
	/**
	 * 通过 渠道订单号获取订单信息
	 * 
	 * @param channelOrderSn
	 * @param dateStr
	 * @return
	 */
	private Map<String, String> queryOrderByChannelOrderSn (String channelOrderSn, String dateStr) {

		// 时间format
		String format = "yyyy-MM-dd HH:mm:ss";
		dateStr = dateStr.replace("  ", " ");
		Date date = DateUtil.parse(dateStr, format);
		// 七天内的时间
		Date dateAddDay = DateUtil.addDay(date, 7);
		
		QueryBuilder qb = new QueryBuilder("SELECT o.orderSn, risk.insureDate FROM sdorders o, "
				+ " sdinformationrisktype risk WHERE "
				+ " o.tbTradeSeriNo = ?"
				+ " AND o.orderSn = risk.orderSn "
				// 打电话七天内的所有订单
				+ " AND UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + dateStr + "','%Y-%m-%d %H:%i:%s')) "
				+ " AND UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('" + DateUtil.toString(dateAddDay, format) + "','%Y-%m-%d %H:%i:%s')) "
				+ " AND o.orderStatus >=7 AND o.orderStatus <> 8 "
				// 删除变更单
				+ " AND NOT EXISTS (SELECT 1 FROM PolicyChangeInfo p WHERE p.newOrderSn = o.orderSn) ", "10" + channelOrderSn);
		
		DataTable dt = qb.executeDataTable();
		
		Map<String, String> result = new HashMap<String, String>();
		if (dt.getRowCount() > 0) {
			result.put("orderSn", dt.getString(0, "orderSn"));
			result.put("insureDate", dt.getString(0, "insureDate"));
		}
		
		return result;
	}
	
	/**
	 * 浏览轨迹中获取订单号
	 * 
	 * @param browsingRecord
	 * @return
	 */
	private String fetchStringByRegexForBrowsingRecord(String browsingRecord){
		
		if (StringUtil.isNotEmpty(browsingRecord)) {
			String regex = "wj/shop/order_config_new!sendDirectUrl.action\\?orderSn=([^&orderId=]*)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(browsingRecord);
			if (m.find()) {
				return m.group(1);
			}
		}
		return "";
	}
	
	/**
	 * 淘宝订单数据中获取订单号
	 * 
	 * @param str
	 * @return
	 */
	private String fetchStringByRegexForDoubleQuote(String str){
		
		if (StringUtil.isNotEmpty(str) && str.indexOf("\"") != -1) {
			String regex = "\"([^\"]*)\"";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.find()) {
				return m.group(1);
			}
		}
		return str;
	}
	
	/**
	 * 插入信息
	 * 
	 * @return
	 */
	private boolean insertInfo(Map<String, String> info){
		CustomServiceOrderInfoSchema schema = new CustomServiceOrderInfoSchema();
		schema.setOrderSn(info.get("OrderSn"));
		//schema.setConversionType(info.get("ConversionType"));
		schema.setEmployeeName(info.get("EmployeeName"));
		CustomServiceOrderInfoSet set = schema.query();
		if (set.size() > 0) {
			CustomServiceOrderInfoSchema tempSchema = set.get(0);
			String conversionType = tempSchema.getConversionType();
			if (conversionType.indexOf(info.get("ConversionType")) != -1) {
				return true;
			} else {
				tempSchema.setConversionType(tempSchema.getConversionType() + "," + info.get("ConversionType"));
				
				return tempSchema.update();
			}
		}
		schema.setid(String.valueOf(NoUtil.getMaxID("CustomServiceInfo")));
		//schema.setOrderSn(info.get("OrderSn"));
		schema.setConversionType(info.get("ConversionType"));
		schema.setCustomerPhone(info.get("CustomerPhone"));
		schema.setEmployeeNum(info.get("EmployeeNum"));
		//schema.setEmployeeName(info.get("EmployeeName"));
		schema.setTrackDetails(info.get("TrackDetails"));
		schema.setBuyerWWName(info.get("BuyerWWName"));
		schema.setServiceWWName(info.get("ServiceWWName"));
		schema.setChannelOrderSn(info.get("ChannelOrderSn"));
		schema.setAddTime(new Date());
		schema.setAddUser(User.getUserName());
		schema.setProp1(info.get("Prop1"));
		schema.setProp2(info.get("Prop2"));
		schema.setProp3(info.get("Prop3"));
		
		return schema.insert();
	}
	
	public static void dg2DataBind(DataGridAction dga) {
		String startDate = (String) dga.getParams().get("startDate");
		String endDate = (String) dga.getParams().get("endDate");
		String orderChannel = (String) dga.getParams().get("orderChannel");
		String conversionType = (String) dga.getParams().get("conversionType");
		String orderStatus = (String) dga.getParams().get("orderStatus");
		String appStatus = (String) dga.getParams().get("appStatus");
		QueryBuilder qb = new QueryBuilder("SELECT c.EmployeeName, risk.payPrice, risk.productPrice, o.orderStatus, risk.insureDate, o.channelsn, "
				+ "c.ConversionType, o.ordersn, risk.policyNo, "
				+ " (SELECT CASE WHEN username!='' THEN username WHEN mobileno!='' THEN mobileno ELSE email END memberID FROM member WHERE id=o.memberid) AS MID "
				+ " , (SELECT remark FROM sdremark r WHERE r.orderSn = o.orderSn limit 1) remark "
				+ " , (SELECT h.codeName FROM zdcode h WHERE h.CodeType='orderstatus' AND h.codevalue = o.orderStatus) AS orderstatusname "
				+ " , (SELECT h.codeName FROM zdcode h WHERE h.CodeType='AppStatus' AND h.codevalue = risk.AppStatus) AS appstatusname "
				+ " FROM CustomServiceOrderInfo c, sdorders o, sdinformation info, sdinformationrisktype risk "
				+ " WHERE c.OrderSn = o.OrderSn "
				+ " AND info.orderSn = o.orderSn AND info.informationSn = risk.informationSn ");
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d')) >=  UNIX_TIMESTAMP('"+startDate.trim()+"')");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d')) <=  UNIX_TIMESTAMP('"+endDate.trim()+"')");
		}
		if (StringUtil.isNotEmpty(orderChannel)) {
			qb.append(" and o.channelsn like ?", "%" + orderChannel.trim() + "%");
		}
		if (StringUtil.isNotEmpty(conversionType)) {
			qb.append(" and c.ConversionType like ?", "%" + conversionType.trim() + "%");
		}
		if (StringUtil.isNotEmpty(orderStatus)) {
			qb.append(" and o.orderStatus = ?", orderStatus.trim());
		}
		if (StringUtil.isNotEmpty(appStatus)) {
			qb.append(" and risk.appStatus = ?", appStatus.trim());
		}
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by risk.insureDate desc");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public static void dg3DataBind(DataGridAction dga) {
		String startDate = (String) dga.getParams().get("startDate");
		String endDate = (String) dga.getParams().get("endDate");
		String orderChannel = (String) dga.getParams().get("orderChannel");
		String conversionType = (String) dga.getParams().get("conversionType");
		
		QueryBuilder qb = new QueryBuilder(" SELECT c.EmployeeName, SUM(CONVERT(risk.payPrice, DECIMAL(10,2))) AS payPriceAll, COUNT(1) AS COUNT "
				+ " FROM CustomServiceOrderInfo c, sdorders o, sdinformation info, sdinformationrisktype risk, zdcode z "
				+ " WHERE c.OrderSn = o.OrderSn AND info.orderSn = o.orderSn AND info.informationSn = risk.informationSn AND o.orderStatus <> '9' "
				+ " AND risk.appStatus = '1' AND risk.payPrice IS NOT NULL "
				+ " AND z.CodeType='Employee.WW' AND z.ParentCode = 'Employee.WW' AND z.codeName = c.EmployeeName ");
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d')) >=  UNIX_TIMESTAMP('"+startDate.trim()+"')");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d')) <=  UNIX_TIMESTAMP('"+endDate.trim()+"')");
		}
		if (StringUtil.isNotEmpty(orderChannel)) {
			qb.append(" and o.channelsn like ?", "%" + orderChannel.trim() + "%");
		}
		if (StringUtil.isNotEmpty(conversionType)) {
			qb.append(" and c.ConversionType like ?", "%" + conversionType.trim() + "%");
		}
		qb.append(" GROUP BY c.EmployeeName "
				+ " UNION "
				+ " SELECT h.codeName AS EmployeeName, '0' AS payPriceAll, '0' AS COUNT FROM zdcode h "
				+ " WHERE h.CodeType='Employee.WW' AND h.ParentCode = 'Employee.WW' AND h.codeName NOT IN  "
				+ " (SELECT DISTINCT c.EmployeeName FROM CustomServiceOrderInfo c, sdorders o, sdinformation info, sdinformationrisktype risk "
				+ " WHERE c.OrderSn = o.OrderSn AND info.orderSn = o.orderSn AND info.informationSn = risk.informationSn AND o.orderStatus <> '9' "
				+ " AND risk.appStatus = '1' AND risk.payPrice IS NOT NULL ");
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d')) >=  UNIX_TIMESTAMP('"+startDate.trim()+"')");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(risk.insureDate,'%Y-%m-%d')) <=  UNIX_TIMESTAMP('"+endDate.trim()+"')");
		}
		if (StringUtil.isNotEmpty(orderChannel)) {
			qb.append(" and o.channelsn like ?", "%" + orderChannel.trim() + "%");
		}
		if (StringUtil.isNotEmpty(conversionType)) {
			qb.append(" and c.ConversionType like ?", "%" + conversionType.trim() + "%");
		}
		qb.append(" ) ");
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		params.put("createDate", PubFun.getPrevMonthDay(DateUtil.getCurrentDate()));
		params.put("endCreateDate", DateUtil.getCurrentDate());
		params.put("OrderStatus", HtmlUtil.codeToOptions("orderstatus", true));
		params.put("AppStatus", HtmlUtil.codeToOptions("AppStatus", true));
		return params;
	}
}
