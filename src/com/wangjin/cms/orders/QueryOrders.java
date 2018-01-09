package com.wangjin.cms.orders;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.util.DownloadNet;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.jdt.ServiceClient;
import com.sinosoft.lis.f1print.HTElectronicPolicy;
import com.sinosoft.lis.f1print.RBElectronicPolicy;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.*;
import com.wangjin.infoseeker.QueryUtil;

public class QueryOrders extends Page {

	// 交互保险公司编码
	private String comId = "";

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

		HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		String url = Config.interfaceMap.getString("ProductCenterAccessServlet");
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

	/**
	 * 电子保单下载接口调用
	 * 
	 * @param pair
	 *
	 * @param param
	 *            GET参数
	 * @throws IOException
	 */
	public String electronicDownloadInterface(String param, NameValuePair[] pair) throws IOException {

		HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
		String url = Config.interfaceMap.getString("PdfDownloadAccessServlet");
		PostMethod post = new PostMethod(url + param);
		if (pair != null && pair.length > 0) {
			post.setRequestBody(pair);
		}
		HttpMethodParams params = new HttpMethodParams();
		// 设置读取返回报文信息的字符集（与接口的发送数据流字符集要相同）
		params.setContentCharset("GBK");
		post.setParams(params);
		try {
			httpClient.executeMethod(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), "GBK"));
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
	 * 查询电子保单
	 */
	public void electronicDown(DataGridAction dga) {

		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.OrderSn, b.tbTradeSeriNo, a.policyNo, a.riskName, a.riskCode, ");
		sql.append("a.electronicPath, ");
		sql.append("sd.recognizeeName,  ");
		sql.append("c.startDate ");
		sql.append("from sdinformationinsured sd,sdinformationrisktype a ");
		sql.append("left join sdorders b on a.orderSn=b.orderSn ");
		sql.append("left join sdinformation c on a.orderSn=c.orderSn ");
		sql.append("where a.appStatus = '1' ");
		sql.append("AND a.recognizeeSn = sd.recognizeeSn ");
		// 订单号
		String orderSn = dga.getParams().getString("orderSn");
		if (StringUtil.isNotEmpty(orderSn)) {
			sql.append(" and sd.orderSn like '" + orderSn.trim() + "%'");
		}
		// 渠道订单号
		String riskName = dga.getParams().getString("riskName");
		if (StringUtil.isNotEmpty(riskName)) {
			sql.append(" and a.riskName like '%" + riskName.trim() + "%'");
		}
		// 保单号
		String policyNo = dga.getParams().getString("policyNo");
		if (StringUtil.isNotEmpty(policyNo)) {
			sql.append(" and a.policyNo like '%" + policyNo.trim() + "%'");
		}
		// 保险公司
		String supplierCode = dga.getParams().getString("supplierCode");
		if (StringUtil.isNotEmpty(supplierCode)) {
			sql.append(" and a.riskCode like '" + supplierCode.trim() + "%'");
		}
		// 创建开始日期
		String createDate = dga.getParams().getString("createDate");
		if (StringUtil.isNotEmpty(createDate)) {
			sql.append(" and b.CreateDate >= date('" + createDate.trim() + "')");
		}
		// 创建终止日期
		String endCreateDate = dga.getParams().getString("endCreateDate");

		if (StringUtil.isNotEmpty(endCreateDate)) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				sql.append(" and b.CreateDate <= date('"
						+ formatter.format(DateUtils.addDays(formatter.parse(endCreateDate.trim()), 1)) + "')");
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		sql.append(" order by b.CreateDate desc ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String filename = (String) dt.get(i).get("recognizeeName");// 被保人姓名
																			// ;形式
																			// 被保人-保单号
				String pno = (String) dt.get(i).get("policyNo");// 保单号
				String path = (String) dt.get(i).get("electronicPath");// 真实地址
				if (!StringUtil.isEmpty(path)) {
					String exname = path.split("\\.")[path.split("\\.").length - 1];// 扩展名
					String fp = filename + "-" + pno + "." + exname;
					dt.get(i).set(
							"electronicPath",
							Config.getServerContext() + "/FileDownLoad.jsp?filepath="
									+ (String) dt.get(i).get("electronicPath") + "&filename=" + fp);
				} else {
					dt.get(i).set("electronicPath", "javascript:void(0);");
				}

			}
		}

		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 下载电子保单
	 */
	public void downloadByOrderSn() {

		String orderSn = $V("orderSn");
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id,a.OrderSn, a.policyNo, a.riskName, a.riskCode, a.electronicPath, b.recognizeeName ");
		sql.append(" from sdinformationrisktype a ");
		sql.append(" LEFT JOIN sdinformationinsured b ON a.recognizeeSn=b.recognizeeSn");
		sql.append(" where a.appStatus = '1' ");
		sql.append(" and a.orderSn like '%" + orderSn.trim() + "%'");
		sql.append(" order by a.id desc ");
		SDInformationRiskTypeSchema sdInformationRiskTypeSchema = new SDInformationRiskTypeSchema();
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executePagedDataTable(0, 0);
		List<File> listFile = new ArrayList<File>();
		Map<String, String> map = new HashMap<String, String>();
		String pdfDownload_delay = "";
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				// 获取被保人姓名
				String recognizeeName = (String) dt.get(i).get("recognizeeName");// 被保人姓名
				if (!StringUtil.isEmpty(dt.get(i).get("electronicPath"))) {
					File tfile = new File(Config.getValue("newPolicyPath")
							+ replacePath((String) dt.get(i).get("electronicPath")));
					String fileame = tfile.getName();
					listFile.add(i, tfile);
					String electpath = (String) dt.get(i).get("electronicPath");// 电子保单保存路径1、为空调用经带通后拼接2、不为空拼接地址
					electpath = electpath.split("\\.")[electpath.split("\\.").length - 1];// 获取路径扩展名
					String cutname = recognizeeName + "-" + (String) dt.get(i).get("policyNo") + "." + electpath;// 压缩后的名称、
																													// 被保人姓名-保单号.扩展名
					map.put(fileame, cutname);// 添加压缩后的名称、 被保人姓名-保单号.扩展名
				} else {// 更新，下载订单下保单
					String riskCode = (String) dt.get(i).get("riskCode");
					String policyNo = (String) dt.get(i).get("policyNo");
					try {
						Map<String, String> param = new HashMap<String, String>();
						param.put("comCode", riskCode.substring(0, 4));
						param.put("policyNo", policyNo);
						Map<String, String> resultMap = pdfDownload(param);
						if ("N".equals(resultMap.get("Success"))) {
							Response.setStatus(0);
							Response.setMessage("电子保单下载失败 ！调用金带通电子保单下载异常！");
							return;
						} else {
							sdInformationRiskTypeSchema.setId((String) dt.get(i).get("id"));
							if (sdInformationRiskTypeSchema.fill()) {
								File file = new File(Config.getValue("newPolicyPath")
										+ replacePath(sdInformationRiskTypeSchema.getelectronicPath()));
								String fileame = file.getName();
								if (sdInformationRiskTypeSchema.getelectronicPath() != null
										&& sdInformationRiskTypeSchema.getelectronicPath() != "") {
									listFile.add(i, file);
									String electpath = sdInformationRiskTypeSchema.getelectronicPath();// 电子保单保存路径1、为空调用经带通后拼接2、不为空拼接地址
									electpath = electpath.split("\\.")[electpath.split("\\.").length - 1];// 获取路径扩展名
									String cutname = recognizeeName + "-" + (String) dt.get(i).get("policyNo") + "."
											+ electpath;// 压缩后的名称、 被保人姓名-保单号.扩展名
									map.put(fileame, cutname);// 添加压缩后的名称、
																// 被保人姓名-保单号.扩展名
								} else {// 下载pdf可能存在延迟
									pdfDownload_delay = "delay";
								}
							} else {
								Response.setStatus(0);
								Response.setMessage("电子保单下载失败！调用金带通电子保单下载异常！");
								return;
							}
						}
					} catch (Exception e) {
						logger.error("=====保单(" + riskCode + ")电子保单下载功能异常===" + e.getMessage(), e);
						Response.setStatus(0);
						Response.setMessage("网络繁忙，请稍候重试！");
						return;
					}
				}
			}
		}
		if ("delay".equals(pdfDownload_delay)) {
			Response.setStatus(0);
			Response.setMessage("保单下载服务繁忙，请稍候重试！");
			return;
		}
		if (listFile.size() > 0) {
			String policyPath = elecPolicydownload(orderSn, listFile, map);
			if (StringUtil.isEmpty(policyPath)) {
				Response.setStatus(0);
				Response.setMessage("电子保单下载失败 ！");
				return;
			} else if ("IsException".equals(policyPath)) {
				Response.setStatus(0);
				Response.setMessage("订单号" + orderSn + "下载异常！");
				return;
			} else {
				Response.put("OrderPath", policyPath);
				Response.put("FileName", orderSn + ".rar");
				Response.setStatus(1);
				return;
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("订单号" + orderSn + "下没有保单喔！");
			return;
		}
	}

	/**
	 * @Title: elecPolicydownload.
	 * @Description: (按订单压缩电子保单).
	 */
	public String elecPolicydownload(String p_Ordersn, List<File> listFile, Map<String, String> map) {

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
				zipFiles(listFile, zipfile, map);
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
	 * @param listFile
	 *            .
	 * @param zipfile
	 *            void.
	 * @author CongZN.
	 * @throws IOException
	 */
	public static void zipFiles(List<File> listFile, File zipfile, Map<String, String> map) throws IOException {

		byte[] buf = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
		for (int i = 0; i < listFile.size(); i++) {
			FileInputStream in = new FileInputStream(listFile.get(i));
			String zipFileName = map.get(listFile.get(i).getName());// 保单压缩文件名称
			out.setEncoding("GBK");
			out.putNextEntry(new ZipEntry(zipFileName));
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
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
	 * 生成电子保单处理
	 */
	public void makeElectronic() {

		String id = $V("IDs");
		// 产品编码
		String riskCode = $V("riskCode");
		if (StringUtil.isNotEmpty(id)) {
			SDInformationRiskTypeSchema insured = new SDInformationRiskTypeSchema();
			insured.setId(id);
			try {
				if (insured.fill()) {
					// 查询被保人编码
					String sql = "select insuredSn from SDInformationInsured where ordersn=? and recognizeeSn = ? ";
					QueryBuilder qb = new QueryBuilder(sql);
					qb.add(insured.getorderSn());
					qb.add(insured.getrecognizeeSn());
					// 被保人编码
					String insuredSn = qb.executeString();
					// 订单号
					String orderSn = insured.getorderSn();
					// 承保时间
					String insureDate = insured.getinsureDate();
					// 电子保单路径
					String path = insured.getelectronicPath();
					// 保单号
					String policyNo = insured.getpolicyNo();
					if (StringUtil.isEmpty(insureDate)) {
						insureDate = DateUtil.toDateTimeString(insured.getcreateDate());
					}
					if (StringUtil.isNotEmpty(insuredSn)) {
						// 平安电子保单生成
						if (riskCode.indexOf("2007") == 0) {
							String validateCode = insured.getvalidateCode();
							if (StringUtil.isNotEmpty(policyNo) && StringUtil.isNotEmpty(validateCode)) {
								InsureTransferNew insureTransferNew = new InsureTransferNew();
								// 生成电子保单、返回保单存放物理路径
								String policyPath = insureTransferNew.checkElectronicPolicyIsSuccess(orderSn, insuredSn,
										policyNo, validateCode, path, insureDate);
								// 更新保单表
								if (StringUtil.isNotEmpty(policyPath)) {
									insured.setelectronicPath(policyPath);
									if (insured.update()) {
										Response.setLogInfo(1, "电子保单路径更新成功！");
									} else {
										Response.setLogInfo(0, "电子保单路径更新失败！");
									}
								} else {
									Response.setLogInfo(0, "电子保单生成的路径为空！");
								}
							}

							// 华泰电子保单生成
						} else if (riskCode.indexOf("2023") == 0) {
							// 险种
							String risktype = "";
							String sql1 = "select riskType from SDInformation where ordersn=?";
							DataTable dt1 = new QueryBuilder(sql1, orderSn).executeDataTable();
							if (dt1.getRowCount() > 0) {
								risktype = dt1.getString(0, 0);
							}
							String policyPath = "";
							// 保单号
							if (StringUtil.isNotEmpty(policyNo)) {
								// 家财险的情况、生成电子保单
								if (StringUtil.isNotEmpty(risktype) && "11".equals(risktype)) {
									HTElectronicPolicy htP = new HTElectronicPolicy();
									policyPath = htP.getPolicyPath(orderSn, insuredSn, policyNo, path, insureDate);
								} else {
									DownloadNet db = new DownloadNet();
									String electronicCout = "";
									String sql_epath = "select electronicCout from sdinformationrisktype where ordersn=?";
									DataTable dt_epath = new QueryBuilder(sql_epath, orderSn).executeDataTable();
									if (dt_epath.getRowCount() > 0) {
										electronicCout = dt_epath.getString(0, 0);
									}
									policyPath = db.EpolicyDeal(orderSn, electronicCout + "", "2023", insuredSn, policyNo,
											path, insureDate);
								}
							}
							// 更新保单表
							if (StringUtil.isNotEmpty(policyPath)) {
								insured.setelectronicPath(policyPath);
								if (insured.update()) {
									Response.setLogInfo(1, "电子保单路径更新成功！");
								} else {
									Response.setLogInfo(0, "电子保单路径更新失败！");
								}
							} else {
								Response.setLogInfo(0, "电子保单生成的路径为空！");
							}

							// 电子保单生成-人保寿险
						} else if (riskCode.startsWith("1004")) {
							try {
								String policyPath = "";
								// 01 承保标志
								if (StringUtil.isNotEmpty(insured.getappStatus()) && "1".equals(insured.getappStatus())) {

									if (StringUtil.isNotEmpty(policyNo)) {

										RBElectronicPolicy rbP = new RBElectronicPolicy();
										policyPath = rbP.getPolicyPath(orderSn, insuredSn, policyNo, path, insureDate);

										insured.setelectronicPath(policyPath);
										if (insured.update()) {
											Response.setLogInfo(1, "电子保单路径更新成功！");
										} else {
											Response.setLogInfo(0, "电子保单路径更新失败！");
										}
									} else {
										Response.setLogInfo(0, "电子保单生成的路径为空！");
									}
								}
							} catch (Exception e) {
								logger.error("电子发送保单异常：" + e.getMessage(), e);
							}

						} else {
							Response.setLogInfo(0, "此保险公司不能生成电子保单，只有平安和华泰财险可生成电子保单 ！");
						}
					}
				}
			} catch (Exception e) {
				Response.setLogInfo(0, e.getMessage());
				logger.error(e.getMessage(), e);
			}
		} else {
			Response.setLogInfo(0, "请选择要生成电子保单的条目 ！");
		}
	}

	// 得到订单信息
	public void orderInquery(DataGridAction dga) {

		String createDate = dga.getParams().getString("createDate");
		String endCreateDate = dga.getParams().getString("endCreateDate");
		String tbTradeSeriNo = dga.getParams().getString("tbTradeSeriNo");
		String orderSn = dga.getParams().getString("orderSn");
		String OrderStatus = dga.getParams().getString("OrderStatus");
		String remarkContent = dga.getParams().getString("remarkContent");
		String remarkSele = dga.getParams().getString("remarkSele");
		String paySn = dga.getParams().getString("paySn");
		String channelsn = dga.getParams().getString("channelsn");
		String channel = QueryUtil.getChannelInfo(channelsn, "");
		String channelinfo = "";
		String description = dga.getParams().getString("description");
		if (StringUtil.isNotEmpty(channel)) {
			channelinfo = " AND EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("
					+ channel + "))";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id,d.id AS rid,a.memberid,d.recognizeesn AS recognizeeSn,a.ordersn,a.orderStatus,e.policyNo,e.appstatus,DATE_FORMAT(e.svalidate,'%Y-%m-%d') AS svalidate,");
		sb.append("IFNULL( (SELECT ChannelName FROM channelinfo WHERE channelcode=a.channelsn),a.channelsn) channelsn,a.channelsn as channelsnEn, c.ApplicantMail,a.renewalId, ");
		sb.append("f.FeeRate,");
		sb.append("a.tbTradeSeriNo,b.ProductName,b.planName,");
		sb.append("ROUND(SUM(d.recognizeePrem),2) AS totalAmount,a.CreateDate,a.ModifyDate,"
				+ " a.ActivitySn,a.orderActivity,a.PayPrice,");
		sb.append("h.codeName AS orderstatusname , d.RecognizeeName AS recognizeeNu,c.ApplicantName,'' remark,");
		sb.append("IF (a.channelSn='b2b_app' || a.channelSn='b2c_pc' || a.channelSn='b2c_wap' || a.channelSn='b2b_ht', a.memberid, (SELECT CONCAT(IFNULL(email,''),'/',IFNULL(mobileno,'')) FROM member WHERE id=a.memberid)) AS MID ,  ");
		sb.append(" a.couponsn as couponSn,a.orderCoupon as orderCoupon,a.orderCoupon as parValue,a.orderIntegral as offsetPoint, a.orderIntegral as orderIntegral, a.paySn as paySn, '' as paymentReport,b.productid,b.insurancecompany ,a.diyActivityDescription as diyActivityDescription ,(SELECT ChannelName FROM channelinfo WHERE channelcode = (SELECT fromWap FROM member WHERE id=a.memberid)) fromWap ");
		sb.append(" FROM sdorders a");
		sb.append(" ,sdinformation b,sdinformationappnt c,sdinformationinsured d,sdinformationrisktype e,femrisk f,zdcode h ");
		sb.append("WHERE a.ordersn = b.ordersn AND b.informationsn = c.informationsn AND a.ordersn = d.ordersn   AND a.ordersn = e.ordersn AND d.recognizeeSn = e.recognizeeSn ");
		sb.append(" AND b.productid = f.eriskid  ");
		if (StringUtil.isEmpty(createDate)) {
			if (StringUtil.isEmpty(endCreateDate)) {
				sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus ");
			} else {
				sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus and DATE(a.createdate) <='" + endCreateDate
						+ "' ");
			}
		} else {
			if (StringUtil.isEmpty(endCreateDate)) {
				sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus and DATE(a.createdate)>='" + createDate
						+ "' ");
			} else {
				sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus and DATE(a.createdate)>='" + createDate
						+ "' and DATE(a.createdate) <='" + endCreateDate + "' ");
			}
		}
		if (StringUtil.isNotEmpty(tbTradeSeriNo)) {
			sb.append(" and a.tbTradeSeriNo like '" + tbTradeSeriNo.trim() + "%' ");
		}
		// 渠道
		sb.append(channelinfo);
		// 订单号
		if (StringUtil.isNotEmpty(orderSn)) {
			sb.append(" and a.orderSn like '" + orderSn.trim() + "'");
		}

		if (StringUtil.isNotEmpty(OrderStatus)) {
			sb.append(" and a.orderstatus = " + OrderStatus.trim() + "");
		}
		// 自定义活动描述
		if (StringUtil.isNotEmpty(description)) {
			sb.append(" and a.diyActivityDescription like '%" + description.trim() + "%' ");
		}
		// 保全记录
		if (StringUtil.isNotEmpty(remarkContent) || "1".equals(remarkSele)) {
			String remarkSql = "  SELECT remark,ordersn FROM sdremark WHERE " + "ordersn IS NOT NULL AND ordersn !=''  ";
			String remarkWhere = "";
			String remarkPart = " GROUP BY ordersn ";

			if (StringUtil.isNotEmpty(remarkContent)) {

				remarkWhere += " AND remark LIKE '%" + remarkContent + "%' ";
			}
			if ("1".equals(remarkSele)) {

				remarkWhere += " AND remark!='' AND remark IS NOT NULL ";
			}
			String reOrdersn = "''";
			QueryBuilder reQb = new QueryBuilder(remarkSql + remarkWhere + remarkPart);
			DataTable reDt = reQb.executeDataTable();

			if (reDt != null && reDt.getRowCount() > 0) {
				for (int k = 0; k < reDt.getRowCount(); k++) {
					if (k == 0) {
						reOrdersn = "'" + reDt.getString(k, "ordersn") + "'";
					} else {
						reOrdersn += ",'" + reDt.getString(k, "ordersn") + "'";
					}
				}

			}
			if (StringUtil.isNotEmpty(remarkContent) || "1".equals(remarkSele)) {
				sb.append(" and a.orderSn in (" + reOrdersn.trim() + ")");
			}
			if ("2".equals(remarkSele)) {
				sb.append(" and a.orderSn not in (" + reOrdersn.trim() + ")");
			}
		}
		// 商家订单号不为空的情况，查询保全记录中存在此商家订单号的订单以及属于此商家订单号的订单
		if (StringUtil.isNotEmpty(paySn)) {
			if ("2".equals(remarkSele)) {
				sb.append(" and a.paySn = '" + paySn.trim() + "'");
			} else {
				sb.append(" and (a.paySn = '" + paySn.trim() + "'");
				String remarkSql = "SELECT ordersn FROM sdremark WHERE ordersn IS NOT NULL AND ordersn !='' AND remark LIKE '%变更：初始商家订单号-"
						+ paySn + "%' GROUP BY ordersn ";
				String reOrdersn = "''";
				QueryBuilder reQb = new QueryBuilder(remarkSql);
				DataTable reDt = reQb.executeDataTable();

				if (reDt != null && reDt.getRowCount() > 0) {
					for (int k = 0; k < reDt.getRowCount(); k++) {
						if (k == 0) {
							reOrdersn = "'" + reDt.getString(k, "ordersn") + "'";
						} else {
							reOrdersn += ",'" + reDt.getString(k, "ordersn") + "'";
						}
					}

				}
				sb.append(" or a.orderSn in (" + reOrdersn.trim() + "))");
			}
		}

		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			sb.append(" and b.productName like '%" + productName.trim() + "%'");
		}
		// 会员ID 如果此时是会员查询页面跳转过来，那么此时要根据memberid来查询
		String memberid = dga.getParams().getString("memberid");

		// 会员ID 可能为 用户名 手机号 邮箱
		String memberEmail = dga.getParams().getString("memberEmail");
		String memberMobile = dga.getParams().getString("memberMobile");
		if (StringUtil.isNotEmpty(memberEmail) || StringUtil.isNotEmpty(memberMobile) || StringUtil.isNotEmpty(memberid)) {
			sb.append(" and exists (select 'X' from member mem where mem.id =a.MemberId  ");
			if (StringUtil.isNotEmpty(memberMobile)) {
				sb.append(" and mem.mobileno  like " + memberMobile.trim());
			}
			if (StringUtil.isNotEmpty(memberEmail)) {
				sb.append(" and mem.email  like '" + memberEmail.trim() + "'");
			}
			if (StringUtil.isNotEmpty(memberid)) {
				sb.append(" and mem.id like '" + memberid.trim() + "'");
			}
			sb.append(")");

		}

		// 投保人姓名 手机号 E-mail 证件号码 关联 informationappnt表
		String applicantName = dga.getParams().getString("applicantName");
		String recognizeeName = dga.getParams().getString("recognizeeName");
		String shipMobile = dga.getParams().getString("shipMobile");
		String email = dga.getParams().getString("email");
		String applicantId = dga.getParams().getString("applicantId");
		String recognizeeId = dga.getParams().getString("recognizeeId");
		if (StringUtil.isNotEmpty(applicantName)) {
			sb.append(" and ( c.ApplicantName = '" + applicantName.trim() + "')");
		}
		if (StringUtil.isNotEmpty(recognizeeName)) {
			sb.append(" and ( d.RecognizeeName = '" + recognizeeName.trim() + "')");
		}
		if (StringUtil.isNotEmpty(email)) {
			sb.append(" and (c.ApplicantMail like '" + email.trim() + "')");
		}
		if (StringUtil.isNotEmpty(shipMobile)) {
			sb.append(" and ( c.ApplicantMobile = '" + shipMobile.trim() + "' )");
		}
		if (StringUtil.isNotEmpty(applicantId)) {
			sb.append(" and ( c.ApplicantIdentityId = '" + applicantId.trim() + "')");
		}
		if (StringUtil.isNotEmpty(recognizeeId)) {
			sb.append(" and ( d.RecognizeeIdentityId = '" + recognizeeId.trim() + "')");
		}

		String policyNo = dga.getParams().getString("policyNo");
		if (StringUtil.isNotEmpty(policyNo)) {
			sb.append(" and e.policyNo = '" + policyNo.trim() + "'");
		}

		sb.append(" GROUP BY a.ordersn order by a.modifydate desc,a.ordersn desc");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		dt.insertColumn("KID");
		// 产品更新弹出提示框标识
		dt.insertColumn("updateWarningFlag");

		if (dt != null) {
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			// String KID = dga.getParams().getString("KID");
			String tempSql = null;
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "KID", StringUtil.md5Hex(com.sinosoft.cms.pub.PubFun.getKeyValue() + dt.getString(i, "orderSn")));
				if (dt.getString(i, "orderstatus").equals("7") || dt.getString(i, "appstatus").equals("1")) {
					dt.set(i, "totalAmount", "+" + dt.getString(i, "totalAmount"));
				}
				// 保全记录查询
				String queryRemark = "SELECT remark,OperateTime,OperateName FROM sdremark WHERE ordersn='"
						+ dt.getString(i, "orderSn") + "' ORDER BY OperateTime DESC";
				QueryBuilder qbr = new QueryBuilder(queryRemark);
				DataTable dtr = qbr.executeDataTable();
				String remark = "";
				if (dtr != null && dtr.getRowCount() > 0) {
					String remarkTem = "";
					for (int j = 0; j < dtr.getRowCount(); j++) {
						int a = j + 1;
						remarkTem = dtr.getString(j, "remark") + "  " + dtr.getString(j, "OperateTime") + "  "
								+ dtr.getString(j, "OperateName") + " && ";
						if (j == dtr.getRowCount() - 1 && remarkTem.indexOf("变更：初始商家订单号") >= 0) {
							remark = remarkTem + " " + remark;
						} else {
							remark += a + ", " + remarkTem;
						}
					}
					dt.set(i, "remark", remark);
				}
				// 被保人个数查询
				String sqlre = "SELECT recognizeeName,recognizeeIdentityId FROM sdinformationinsured WHERE ordersn ='"
						+ dt.getString(i, "orderSn") + "' GROUP BY recognizeekey";
				QueryBuilder qbre = new QueryBuilder(sqlre);
				DataTable dtre = qbre.executeDataTable();
				if (dtre != null && dtre.getRowCount() > 0) {
					dt.set(i, "recognizeeNu", dtre.getRowCount());
				} else {
					dt.set(i, "recognizeeNu", 0);
				}

				String paymentSql = "select count(1) from paymentInfo where ordersn = ?";
				QueryBuilder paymentQb = new QueryBuilder(paymentSql, dt.getString(i, "ordersn"));
				if (paymentQb.executeInt() > 0) {
					dt.set(i, "paymentReport", "是");

				}

				if (Integer.valueOf(dt.getString(i, "orderstatus")) < 7 || "8".equals(dt.getString(i, "orderstatus").trim())) {
					dt.set(i, "PayPrice", "");
				}

				// 获取产品更新标识
				tempSql = "SELECT IsUpdateFlag, IsUpdateDate FROM sdproduct WHERE productid = ?";
				DataTable tempDt = new QueryBuilder(tempSql, dt.getString(i, "productid")).executeDataTable();
				if (tempDt != null && tempDt.getRowCount() > 0) {
					String isUpdateFlag = tempDt.getString(0, "IsUpdateFlag");
					String isUpdateDate = tempDt.getString(0, "IsUpdateDate");
					String orderCreateDate = dt.getString(i, "createDate");
					// 如果产品中心设置了更新标识为“Y”, 并且更新时间大于下单时间，设置更新提示层标识
					if ("Y".equals(isUpdateFlag)
							&& DateUtil.compare(isUpdateDate, orderCreateDate, DateUtil.Format_DateTime) >= 0) {
						// 更新提示层标识
						dt.set(i, "updateWarningFlag", "Y");
					}
				}
			}

		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 结算订单查询
	 *
	 * @param dga
	 */
	public void settleOrderInquery(DataGridAction dga) {

		String createDate = dga.getParams().getString("createDate");
		String endCreateDate = dga.getParams().getString("endCreateDate");
		String orderSn = dga.getParams().getString("orderSn");
		String OrderStatus = dga.getParams().getString("OrderStatus");
		String remarkContent = dga.getParams().getString("remarkContent");
		String remarkSele = dga.getParams().getString("remarkSele");
		String paySn = dga.getParams().getString("paySn");
		String channelsn = dga.getParams().getString("channelsn");
		String channel = QueryUtil.getChannelInfo(channelsn, "");
		String channelinfo = "";
		String description = dga.getParams().getString("description");
		String startDate = dga.getParams().getString("startDate") + " 00:00:00";
		;
		String endDate = dga.getParams().getString("endDate") + " 23:59:59";
		String cPrice = dga.getParams().getString("cPrice");// 会员回购统计部分 ：回购标记
		if (StringUtil.isNotEmpty(channel)) {
			channelinfo = " AND EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN ("
					+ channel + "))";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id,d.id AS rid,a.memberid,d.recognizeesn AS recognizeeSn,a.ordersn,a.orderStatus,e.policyNo,e.appstatus,DATE_FORMAT(e.svalidate,'%Y-%m-%d') AS svalidate,");
		sb.append("(SELECT ChannelName FROM channelinfo WHERE channelcode=a.channelsn) channelsn, c.ApplicantMail, ");
		sb.append("(SELECT g.name FROM AREA g WHERE  c.applicantarea1 = g.id ),");
		if ("0".equals(cPrice) || "1".equals(cPrice)) {
			// 会员回购统计部分 判断会员回购统计部分 此处修复因活动优惠原因造成查找订单不准确问题
		} else {
			sb.append("f.FeeRate,");
		}
		sb.append("b.ProductName,");
		sb.append("ROUND(SUM(d.recognizeePrem),2) AS totalAmount,a.CreateDate,a.ModifyDate,a.orderActivity,a.PayPrice,");
		sb.append("h.codeName AS orderstatusname ,'' isoldmember ,d.RecognizeeName AS recognizeeNu,'' remark,");
		sb.append("a.orderCoupon as orderCoupon,'' parValue,a.offsetPoint as offsetPoint, a.orderIntegral as orderIntegral, a.paySn as paySn, b.productid,b.insurancecompany ,a.diyActivityDescription as diyActivityDescription ,(SELECT ChannelName FROM channelinfo WHERE channelcode = (SELECT fromWap FROM member WHERE id=a.memberid)) fromWap ");
		sb.append(" FROM sdorders a");
		if ("0".equals(cPrice) || "1".equals(cPrice)) {// 会员回购统计部分 判断会员回购统计部分
														// 此处修复因活动优惠原因造成查找订单不准确问题(精确支付时间)
			sb.append(" LEFT JOIN tradeinformation tf  ON a.ordersn = tf.ordid ");
		}
		sb.append(" ,sdinformation b,sdinformationappnt c,sdinformationinsured d,sdinformationrisktype e,femrisk f,zdcode h ");
		sb
				.append("WHERE a.ordersn = b.ordersn AND b.informationsn = c.informationsn AND a.ordersn = d.ordersn   AND a.ordersn = e.ordersn AND d.recognizeeSn = e.recognizeeSn ");
		if ("0".equals(cPrice) || "1".equals(cPrice)) {// 会员回购统计部分
														// 此处修复因活动优惠原因造成查找订单不准确问题

		} else {
			sb.append(" AND b.productid = f.eriskid  ");
		}
		if ("0".equals(cPrice) || "1".equals(cPrice)) {// 会员回购统计部分 判断
			sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus and tf.receiveDate >='" + startDate
					+ " 00:00:00' and tf.receiveDate  <='" + endDate + " 23:59:59' ");
		} else {
			if (StringUtil.isEmpty(createDate)) {
				if (StringUtil.isEmpty(endCreateDate)) {
					sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus ");
				} else {
					sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus and a.createdate <='"
							+ endCreateDate + " 23:59:59' ");
				}
			} else {
				if (StringUtil.isEmpty(endCreateDate)) {
					sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus and a.createdate>='" + createDate
							+ " 00:00:00' ");
				} else {
					sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus and a.createdate>='" + createDate
							+ " 00:00:00' and a.createdate <='" + endCreateDate + " 23:59:59' ");
				}

			}
		}
		// 渠道订单号
		if ("0".equals(cPrice) || "1".equals(cPrice)) {// 会员回购统计部分判断
			sb.append(" and a.orderSn in  ");
			// 此处根据保单号查找 //回购数据
			if ("0".equals(cPrice)) {
				sb.append("( SELECT a.orderSn ");
				sb.append(" FROM SDOrderItem f,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid ");
				sb.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
				sb.append(" AND a.channelsn!='b2b_app' ");
				sb.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
				sb.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
				sb.append(" AND a.memberid IN ( ");
				sb.append(" SELECT a.memberid ");
				sb.append(" FROM SDOrderItem f,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid ");
				sb.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
				sb.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
				sb.append(" AND (EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY)) ");
				sb.append(" OR EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate<=DATE_ADD(a.createdate, INTERVAL -30 DAY))) ");
				sb.append(" AND NOT EXISTS(SELECT 1 FROM sdorders b WHERE b.memberid = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
				sb.append(" AND NOT EXISTS(SELECT 1 FROM member b WHERE b.id = a.memberid AND b.createdate>=DATE_ADD(a.createdate, INTERVAL -30 DAY) AND b.createdate<a.createdate) ");
				sb.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
				sb.append("))");
			} else if ("1".equals(cPrice)) {// 保单数据
				sb.append(" (SELECT  a.orderSn ");
				sb.append(" FROM SDOrderItem f ,sdinformationrisktype sdor,sdorders a left join tradeinformation tf on a.ordersn=tf.ordid  ");
				sb.append(" WHERE a.ordersn = sdor.ordersn and a.paySn NOT LIKE 'BG%' AND a.orderStatus IN ('7', '10', '12', '14') AND f.ordersn=a.ordersn AND a.memberid IS NOT NULL AND a.memberid !='' ");
				sb.append(" AND a.channelsn!='b2b_app' ");
				sb.append(" AND (f.channelCode != '02' OR f.channelCode IS NULL) ");
				sb.append(" AND tf.receiveDate <='" + endDate + "'  AND tf.receiveDate >='" + startDate + "' ");
				sb.append(")");
			}
		}
		// 渠道
		sb.append(channelinfo);
		// 订单号
		if (StringUtil.isNotEmpty(orderSn)) {
			orderSn = orderSn.replace(",", "','");
			sb.append(" and a.orderSn in ('" + orderSn + "') ");
		}

		if (StringUtil.isNotEmpty(OrderStatus)) {
			sb.append(" and a.orderstatus = " + OrderStatus.trim() + "");
		}
		// 自定义活动描述
		if (StringUtil.isNotEmpty(description)) {
			sb.append(" and a.diyActivityDescription like '%" + description.trim() + "%' ");
		}
		// 保全记录
		if (StringUtil.isNotEmpty(remarkContent) || "1".equals(remarkSele)) {
			String remarkSql = "  SELECT remark,ordersn FROM sdremark WHERE " + "ordersn IS NOT NULL AND ordersn !=''  ";
			String remarkWhere = "";
			String remarkPart = " GROUP BY ordersn ";

			if (StringUtil.isNotEmpty(remarkContent)) {

				remarkWhere += " AND remark LIKE '%" + remarkContent + "%' ";
			}
			if ("1".equals(remarkSele)) {

				remarkWhere += " AND remark!='' AND remark IS NOT NULL ";
			}
			String reOrdersn = "''";
			QueryBuilder reQb = new QueryBuilder(remarkSql + remarkWhere + remarkPart);
			DataTable reDt = reQb.executeDataTable();

			if (reDt != null && reDt.getRowCount() > 0) {
				for (int k = 0; k < reDt.getRowCount(); k++) {
					if (k == 0) {
						reOrdersn = "'" + reDt.getString(k, "ordersn") + "'";
					} else {
						reOrdersn += ",'" + reDt.getString(k, "ordersn") + "'";
					}
				}

			}
			if (StringUtil.isNotEmpty(remarkContent) || "1".equals(remarkSele)) {
				sb.append(" and a.orderSn in (" + reOrdersn.trim() + ")");
			}
			if ("2".equals(remarkSele)) {
				sb.append(" and a.orderSn not in (" + reOrdersn.trim() + ")");
			}
		}
		// 商家订单号不为空的情况，查询保全记录中存在此商家订单号的订单以及属于此商家订单号的订单
		if (StringUtil.isNotEmpty(paySn)) {
			if ("2".equals(remarkSele)) {
				sb.append(" and a.paySn = '" + paySn.trim() + "'");
			} else {
				sb.append(" and (a.paySn = '" + paySn.trim() + "'");
				String remarkSql = "SELECT ordersn FROM sdremark WHERE ordersn IS NOT NULL AND ordersn !='' AND remark LIKE '%变更：初始商家订单号-"
						+ paySn + "%' GROUP BY ordersn ";
				String reOrdersn = "''";
				QueryBuilder reQb = new QueryBuilder(remarkSql);
				DataTable reDt = reQb.executeDataTable();

				if (reDt != null && reDt.getRowCount() > 0) {
					for (int k = 0; k < reDt.getRowCount(); k++) {
						if (k == 0) {
							reOrdersn = "'" + reDt.getString(k, "ordersn") + "'";
						} else {
							reOrdersn += ",'" + reDt.getString(k, "ordersn") + "'";
						}
					}

				}
				sb.append(" or a.orderSn in (" + reOrdersn.trim() + "))");
			}
		}

		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			sb.append(" and b.productName like '%" + productName.trim() + "%'");
		}
		// 会员ID 如果此时是会员查询页面跳转过来，那么此时要根据memberid来查询
		String memberid = dga.getParams().getString("memberid");

		// 会员ID 可能为 用户名 手机号 邮箱
		if (StringUtil.isNotEmpty(memberid)) {
			sb.append(" and exists (select 'X' from member mem where mem.id =a.MemberId  ");
			if (StringUtil.isNotEmpty(memberid)) {
				sb.append(" and mem.id like '%" + memberid.trim() + "%'");
			}
			sb.append(")");

		}

		// 投保人姓名 手机号 E-mail 证件号码 关联 informationappnt表
		String policyNo = dga.getParams().getString("policyNo");
		if (StringUtil.isNotEmpty(policyNo)) {
			policyNo = policyNo.replace(",", "','");
			sb.append(" and e.policyNo in ('" + policyNo + "') ");
		}

		sb.append(" GROUP BY a.ordersn order by a.modifydate desc,a.ordersn desc");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("KID");
		if (dt != null) {
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			// String KID = dga.getParams().getString("KID");
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "KID", StringUtil.md5Hex(com.sinosoft.cms.pub.PubFun.getKeyValue() + dt.getString(i, "orderSn")));
				if (dt.getString(i, "orderstatus").equals("7") || dt.getString(i, "appstatus").equals("1")) {
					dt.set(i, "totalAmount", "+" + dt.getString(i, "totalAmount"));
				}
				// 保全记录查询
				String queryRemark = "SELECT remark,OperateTime,OperateName FROM sdremark WHERE ordersn='"
						+ dt.getString(i, "orderSn") + "' ORDER BY OperateTime DESC";
				QueryBuilder qbr = new QueryBuilder(queryRemark);
				DataTable dtr = qbr.executeDataTable();
				String remark = "";
				if (dtr != null && dtr.getRowCount() > 0) {
					String remarkTem = "";
					for (int j = 0; j < dtr.getRowCount(); j++) {
						int a = j + 1;
						remarkTem = dtr.getString(j, "remark") + "  " + dtr.getString(j, "OperateTime") + "  "
								+ dtr.getString(j, "OperateName") + " && ";
						if (j == dtr.getRowCount() - 1 && remarkTem.indexOf("变更：初始商家订单号") >= 0) {
							remark = remarkTem + " " + remark;
						} else {
							remark += a + ", " + remarkTem;
						}
					}
					dt.set(i, "remark", remark);
				}
				// 被保人个数查询
				String sqlre = "SELECT recognizeeName,recognizeeIdentityId FROM sdinformationinsured WHERE ordersn ='"
						+ dt.getString(i, "orderSn") + "' GROUP BY recognizeekey";
				QueryBuilder qbre = new QueryBuilder(sqlre);
				DataTable dtre = qbre.executeDataTable();
				if (dtre != null && dtre.getRowCount() > 0) {
					dt.set(i, "recognizeeNu", dtre.getRowCount());
				} else {
					dt.set(i, "recognizeeNu", 0);
				}
				String parValue = dt.getString(i, "orderCoupon");
				BigDecimal totalamount = new BigDecimal("0.0");
				if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {

					String totalAmount = dt.getString(i, "totalAmount");
					if (StringUtil.isEmpty(totalAmount)) {
						totalAmount = "0.00";
					}
					totalamount = new BigDecimal(totalAmount);

					if (StringUtil.isNotEmpty(parValue) && totalamount.compareTo(new BigDecimal(parValue)) != -1) {
						totalamount = totalamount.subtract(new BigDecimal(parValue));
						dt.set(i, "PayPrice", totalamount);
					}
				}
				if (StringUtil.isNotEmpty(parValue)) {
					dt.set(i, "parValue", parValue);
				} else {
					dt.set(i, "parValue", "");
				}
				// 积分抵值
				if ("0".equals(dt.getString(i, "orderIntegral")) || dt.getString(i, "orderIntegral") == null
						|| "".equals(dt.getString(i, "orderIntegral"))) {
					dt.set(i, "offsetPoint", "0");
				} else {
					// 积分换算规则修改时间点
					BigDecimal offsetPoint = new BigDecimal(dt.getString(i, "orderIntegral"));
					dt.set(i, "offsetPoint", offsetPoint);
					if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {
						if (totalamount.compareTo(offsetPoint) != -1) {
							dt.set(i, "PayPrice", totalamount.subtract(offsetPoint));
						}
					}
				}

				if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {
					if (Integer.valueOf(dt.getString(i, "orderstatus")) < 7
							|| "8".equals(dt.getString(i, "orderstatus").trim())) {
						dt.set(i, "PayPrice", "");
					}
				}
			}

		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 
	 * @Title: checkActivity
	 * @Description: TODO(购物车每个订单校验活动)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	private static boolean checkActivity(String orderid, String CouponSn) {

		QueryBuilder qb_productid = new QueryBuilder("SELECT productid FROM sdinformation WHERE ordersn=?");
		qb_productid.add(orderid);
		DataTable dt_productid = qb_productid.executeDataTable();
		String productId = dt_productid.getString(0, 0);
		QueryBuilder qb = new QueryBuilder(
				"SELECT id FROM sdcouponactivityinfo ac WHERE ((FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0 AND ac.insurancecompany = '') OR (FIND_IN_SET(?,ac.insurancecompany)!=0 AND ac.riskcode = '') OR ( ( FIND_IN_SET(?, ac.insurancecompany) != 0 ) AND ( FIND_IN_SET( (SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode = ?), ac.riskcode ) != 0 ) ) OR(   FIND_IN_SET(?, ac.product) != 0  )  )  and  ac.status='3' AND  ((SELECT COUNT(id) FROM couponinfo cou WHERE cou.activitysn=ac.activitysn AND cou.STATUS='0' AND ac.autocreate='1' AND ac.type != '3' ) !=0  OR ( ac.autocreate='0' AND ac.type != '3' )  OR ac.type = '3' ) and  activitysn=?");
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

	/**
	 * 
	 * @Title: checkCoupon
	 * @Description: TODO(购物车每个订单校验活动)
	 * @return boolean 返回类型
	 * @author
	 */
	private static boolean checkCoupon(String orderid, String CouponSn) {

		QueryBuilder qb_productid = new QueryBuilder("SELECT productid FROM sdinformation WHERE ordersn=?");
		qb_productid.add(orderid);
		DataTable dt_productid = qb_productid.executeDataTable();
		String productId = dt_productid.getString(0, 0);
		QueryBuilder qb = new QueryBuilder(
				"SELECT  couponsn,parvalue,direction "
						+ " FROM couponinfo ac WHERE IF (ac.riskcode IS NOT NULL AND ac.riskcode != '', (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0), 1=1) AND IF (ac.insurancecompany IS NOT NULL AND ac.insurancecompany != '', FIND_IN_SET(?, ac.insurancecompany) != 0, 1=1) AND IF (ac.product IS NOT NULL AND ac.product != '', FIND_IN_SET(?, ac.product) != 0, 1=1) AND couponsn =?");
		qb.add(productId);
		qb.add(productId.substring(0, 4));
		qb.add(productId);
		qb.add(CouponSn);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void orderInquery2(DataGridAction dga) {// 保单基本信息表

		String orderSn = $V("orderSn");
		String company = $V("company");
		QueryBuilder qb = new QueryBuilder(
				"SELECT '' as appFlag, sd.id AS baodanId,sd.orderSn,sd.recognizeeSn,sd.createDate,sd.svaliDate,sd.evaliDate,sd.riskName,sd.informationSn,ROUND(sd.timePrem,2),sd.policyNo,"
						+ " sd.CancelDate,i.recognizeeAppntRelation,i.recognizeeName,i.recognizeeEnName,i.recognizeePrem,i.remark,i.recognizeeIdentityType,i.recognizeeSex,"
						+ " i.recognizeeIdentityTypeName,i.recognizeeIdentityId,i.recognizeeSexName,i.recognizeeAppntRelationName,i.recognizeeBirthday,"
						+ " i.recognizeeMobile,i.recognizeeMail, sd.payPrice,sd.appStatus,'' as checkflag, d.flagType,(SELECT codename FROM zdcode WHERE codetype = 'appStatus' AND codevalue = sd.appStatus) AS status,sd.insureMsg "
						+ " FROM SDInformationRiskType sd,SDInformationInsured i left join dictionary d on d.codeType='certificate'"
						+ " and d.insuranceCode=? and d.codeValue=i.recognizeeIdentityType,sdorders s"
						+ " WHERE s.ordersn=sd.ordersn AND (d.productid is null or d.productid='') AND  i.orderSn=sd.orderSn AND i.recognizeeSn=sd.recognizeeSn AND sd.orderSn =?",
				company, orderSn);
		qb.append(" order by sd.policyNo asc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (StringUtil.isNotEmpty(orderSn) && orderSn.startsWith("wp")) {
			if (dt.getRowCount() >= 1) {
				for (int i = 0; i < dt.getRowCount(); i++) {
					if ("本人".equals(dt.get(i, "recognizeeAppntRelationName").toString().trim())) {
						QueryBuilder qbapp = new QueryBuilder(
								"SELECT a.applicantMail FROM sdinformationappnt a where informationSn=?", dt.get(i,
										"informationSn").toString());
						String appEmail = qbapp.executeOneValue().toString();
						dt.set(i, "recognizeeMail", appEmail);
					}
				}
			}
		}
		if (dt != null && dt.getRowCount() > 0) {
			int rowcount = dt.getRowCount();
			String appFlag = "";
			for (int i = 0; i < rowcount; i++) {
				if (("1".equals(dt.getString(i, "appStatus")) || "3".equals(dt
						.getString(i, "appStatus")))
						&& StringUtil.isNotEmpty(dt.getString(i, "policyNo"))) {
					appFlag = "1";
				} else {
					appFlag = "0";
				}
				dt.set(i, "appFlag", appFlag);
				dt.set(i,
						"recognizeeIdentityType",
						dt.getString(i, "recognizeeIdentityType") + "_"
								+ dt.getString(i, "flagType"));
			}

		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public void orderInquery3(DataGridAction dga) {// 健康告知信息

		String orderSn = $V("orderSn");
		QueryBuilder qb = new QueryBuilder(
				"SELECT id healthid,healthyinfoid,showinfo,selectFlag FROM sdinsuredhealth WHERE orderSn=? AND showinfotype<>'#' ORDER BY showorder ASC",
				orderSn);
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}

	public static Mapx orderInqueryApp2(Mapx params) {// 查看订单信息--投保人信息

		String id = params.getString("id");
		String orderSn = params.getString("orderSn");
		String company = params.getString("company");
		if (StringUtil.isNotEmpty(id)) {
			SDOrdersSchema orders = new SDOrdersSchema();
			orders.setid(id);
			orders.fill();
			QueryBuilder qb = new QueryBuilder(
					"SELECT a.id,a.applicantName,a.applicantEnName,a.applicantIdentityType,a.applicantIdentityId,"
							+ " a.applicantBirthday,a.applicantMobile,a.applicantMail,i.recognizeeAppntRelationName, a.applicantSex,a.applicantIdentityTypeName,a.applicantSexName"
							+ " FROM sdinformationappnt a,SDInformationInsured i,SDInformationRiskType sd"
							+ " WHERE i.informationsn=a.informationsn AND i.recognizeeSn=sd.recognizeeSn AND i.orderSn =?",
					orderSn);
			qb.append(" order by sd.createDate desc");
			DataTable dt = qb.executeDataTable();

			Mapx map = orders.toMapx();
			map.put("applicantId", dt.get(0, "id"));
			map.put("applicantName", dt.get(0, "applicantName"));
			map.put("appIdentityTypeName", dt.get(0, "applicantIdentityTypeName"));
			map.put("appSexName", dt.get(0, "applicantSexName"));
			QueryBuilder qb1 = new QueryBuilder(
					"select flagType from dictionary where codeType='certificate' and insuranceCode=? and codeValue=? ",
					company, dt.getString(0, "applicantIdentityType"));
			String flagType = "";
			if (qb1.executeString() != null) {
				flagType = qb1.executeString();
			}
			String applicantIdentityType = dt.getString(0,
					"applicantIdentityType") + "_" + flagType;
			map.put("applicantIdentityTypeName", HtmlUtil
					.mapxToOptions(getCertificateSelect(company),
							applicantIdentityType, false));
			map.put("applicantIdentityId", dt.get(0, "applicantIdentityId"));
			map.put("applicantBirthday", dt.get(0, "applicantBirthday"));
			map.put("applicantMobile", dt.get(0, "applicantMobile"));
			map.put("applicantMail", dt.get(0, "applicantMail"));
			map.put("applicantEnName", dt.get(0, "applicantEnName"));
			map.put("applicantSexName",
					HtmlUtil.mapxToOptions(getSexSelect(company),
							dt.getString(0, "applicantSex"), false));
			map.put("recognizeeAppntRelationName",
					dt.get(0, "recognizeeAppntRelationName"));

			return map;
		} else {
			return params;
		}

	}

	/**
	 * 取得保险公司性别下拉框
	 * 
	 * @param company
	 *            保险公司编码
	 * @return
	 */
	public static Map<String, String> getSexSelect(String company) {

		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName from dictionary where codeType='Sex' and insuranceCode = ? order by id asc",
				company);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				map.put(dt.getString(i, 0), dt.getString(i, 1));
			}
		}
		return map;
	}

	/**
	 * 取得保险公司证件类型下拉框
	 * 
	 * @param company
	 *            保险公司编码
	 * @return
	 */
	public static Map<String, String> getCertificateSelect(String company) {

		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,flagType from dictionary where codeType='certificate' and insuranceCode = ? order by id asc",
				company);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			String flagType = "";
			for (int i = 0; i < rowCount; i++) {
				flagType = "";
				if (StringUtil.isNotEmpty(dt.getString(i, 2))) {
					flagType = dt.getString(i, 2);
				}
				map.put(dt.getString(i, 0) + "_" + flagType, dt.getString(i, 1));
			}
		}
		return map;
	}

	/**
	 * 取得保险公司证件类型下拉框
	 * 
	 * @param NDestination
	 *            保险公司编码
	 * @return
	 */
	public static String getDestinationSelect(String NDestination) {

		String codeValue = "";
		Map<String, String> map = new HashMap<String, String>();
		if (NDestination.split(",").length > 0) {
			String Destinations[] = NDestination.split(",");
			int j = Destinations.length;
			for (int i = 0; i < j; i++) {
				QueryBuilder qb = new QueryBuilder(
						"select codeValue,codeName,flagType from dictionary where codeType='CountryCode' and insuranceCode = ? order by id asc",
						Destinations[i]);
				DataTable dt = qb.executeDataTable();
				if (dt != null && dt.getRowCount() > 0) {
					if (i == 0) {
						codeValue = dt.getString(0, 0);
					} else {
						codeValue = "," + dt.getString(0, 0);
					}
				}

			}
		}
		return codeValue;
	}

	/**
	 * 取得与投保人关系下拉框
	 * 
	 * @param company
	 *            保险公司编码
	 * @param productId
	 *            产品编码
	 * @return
	 */
	public static Map<String, String> getRelationSelect(String company,
			String productId) {

		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,flagType from dictionary where codeType='Relationship' and insuranceCode = ? and productId = ? order by id asc",
				company, productId);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				map.put(dt.getString(i, 0), dt.getString(i, 1));
			}
		} else {
			qb = new QueryBuilder(
					"select codeValue,codeName,flagType from dictionary where codeType='Relationship' and insuranceCode = ? order by id asc",
					company);
			dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					map.put(dt.getString(i, 0), dt.getString(i, 1));
				}
			}
		}
		return map;
	}

	/**
	 * 取得与投保人关系下拉框
	 * 
	 * @param company
	 *            保险公司编码
	 * @param productId
	 *            产品编码
	 * @return
	 */
	public static Map<String, String> getdestinationCountrySelect(String company,
			String productId) {

		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,flagType from dictionary where codeType='CountryCode' and  productId = ? order by id asc");
		qb.add(productId);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				map.put(dt.getString(i, 0), dt.getString(i, 1));
			}
		} else {
			qb = new QueryBuilder(
					"select codeValue,codeName,flagType from dictionary where codeType='CountryCode' and insuranceCode = ? and  (productId is null or productId!='') order by id asc",
					company);
			dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					map.put(dt.getString(i, 0), dt.getString(i, 1));
				}
			}
		}
		return map;
	}

	/**
	 * 取得被保人旅游目的地
	 * 
	 * @param company
	 *            保险公司编码
	 * @param productId
	 *            产品编码
	 * @return
	 */
	public static String getdestinationCountrySelected(String company,
			String productId) {

		Map<String, String> map = new HashMap<String, String>();
		String detionationCountry = "";
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,flagType from dictionary where codeType='CountryCode' and  codeValue = ? order by id asc",
				company, productId);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			detionationCountry = dt.getString(0, 1);
		}
		return detionationCountry;

	}

	@SuppressWarnings("unchecked")
	public static Mapx initDialog2(Mapx params) { // 查看订单信息-- 购买优惠信息&订单基本信息

		String id = params.getString("id");
		String rid = params.getString("rid");
		String orderSn = params.getString("orderSn");
		String recognizeeSn = params.getString("recognizeeSn");
		String parValue = params.getString("parValue");
		String company = params.getString("company");
		String productId = "";
		String querySql = "select OrderStatus from SDOrders where id =?";
		QueryBuilder qb = new QueryBuilder(querySql);
		qb.add(id);
		int status = qb.executeInt();
		if (StringUtil.isNotEmpty(id)) {
			SDOrdersSchema orders = new SDOrdersSchema();
			orders.setid(id);
			orders.fill();

			SDInformationInsuredSchema sdInformationInsured = new SDInformationInsuredSchema();
			sdInformationInsured.setId(rid);
			sdInformationInsured.fill();

			// 格式化为两位小数 fhz 20121123
			if (status == 7) {
				orders.setpayAmount(String.valueOf(new BigDecimal(orders.gettotalAmount()).setScale(2,
						BigDecimal.ROUND_HALF_UP)));
			} else {
				orders.setpayAmount(getAmount(orders.getpayAmount()));
			}
			orders.setproductTotalPrice(getAmount(orders.getproductTotalPrice()));
			orders.settotalAmount(getAmount(orders.gettotalAmount()));

			Mapx map = orders.toMapx();
			map.put("orderStatus", HtmlUtil.codeToOptions("orderstatus", orders.getorderStatus() + ""));
			// mod by wangej - 20130815 - 已支付的订单除了保单号其他信息不允许修改 - begin
			map.put("orderStatusHidden", orders.getorderStatus());
			// mod by wangej - 20130815 - 已支付的订单除了保单号其他信息不允许修改 - end
			// 获取 InsuredCompanyReturnData 保单号 ---ysc
			try {
				String result = ("select policyNo from SDInformationRiskType where orderSn = '" + orders.getorderSn() + "'");
				QueryBuilder db = new QueryBuilder(result);
				DataTable dtPn = db.executeDataTable();
				if (dtPn != null && dtPn.getRowCount() > 0) {
					map.put("policyNumber", dtPn.getRowCount());
				} else {
					map.put("policyNumber", 0);
				}

				String sql = "SELECT d.ActivitySn,d.orderActivity,date_format(a.svalidate,'%Y-%m-%d %T') as svalidate,date_format(a.evaliDate,'%Y-%m-%d %T') as evaliDate,c.codename insurCompany,b.productName,d.memberid,if(d.couponSn='0','',d.couponSn) couponSn,"
						+ " FORMAT((IFNULL(a.timePrem, 0)-IFNULL(a.couponValue, 0)-IFNULL(a.integralValue, 0)),2) AS payAmount,b.ensureLimit,b.ensureLimitType,"
						+ "(SELECT description FROM sdcouponactivityinfo WHERE activitysn=d.ActivitySn) HDdescription,b.id as informationId,b.productId,"
						+ "(SELECT (SELECT codename FROM zdcode WHERE codetype='Activity.type' AND codevalue = s.type) TYPE"
						+ " FROM sdcouponactivityinfo s WHERE s.activitysn=d.ActivitySn) HDtype,co.direction,(SELECT concat(IFNULL(email,''),'/',IFNULL(mobileno,'')) FROM member WHERE id=d.memberid) AS MID,d.paySn"
						+ " FROM SDInformationRiskType a,SDInformation b,zdcode c,sdorders d LEFT JOIN CouponInfo co ON d.couponsn=co.couponsn "
						+ " WHERE c.codetype='SupplierCode' AND c.codevalue=b.insuranceCompany AND a.informationsn=b.informationSn AND d.ordersn = b.ordersn"
						+ " AND a.orderSn ='"
						+ orders.getorderSn() + "' and a.recognizeeSn='" + sdInformationInsured.getrecognizeeSn() + "'";
				QueryBuilder qbDate = new QueryBuilder(sql);
				DataTable dt = qbDate.executeDataTable();
				if (dt != null && dt.getRowCount() > 0) {
					String svalidate = dt.getString(0, "svalidate");
					if (StringUtil.isNotEmpty(svalidate)) {
						if (svalidate.length() > 10) {
							map.put("svalidate", svalidate.substring(0, 10));
							map.put("svalitime", svalidate.substring(11));
						} else {
							map.put("svalidate", svalidate);
						}
					}

					map.put("evaliDate", dt.get(0, "evaliDate"));
					map.put("insurCompany", dt.get(0, "insurCompany"));
					map.put("productName", dt.get(0, "productName"));
					map.put("memberid", dt.get(0, "MID"));
					map.put("couponSn", dt.get(0, "couponSn"));
					map.put("direction", dt.get(0, "direction"));
					map.put("HDdescription", dt.get(0, "HDdescription"));
					map.put("informationId", dt.get(0, "informationId"));
					map.put("ensureLimit", dt.get(0, "ensureLimit"));
					map.put("ensureLimitType", dt.get(0, "ensureLimitType"));
					productId = dt.getString(0, "productId");
					if (orders.getorderStatus() >= 7) {
						map.put("payAmount", dt.get(0, "payAmount"));
					} else {
						map.put("payAmount", "");
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			map.put("id", id);
			map.put("parValue", parValue);
			map.put("orderSn", orderSn);
			map.put("recognizeeSn", recognizeeSn);
			map.put("productId", productId);
			map.put("company", company);
			Map<String, String> certMap = getCertificateSelect(company);
			Map<String, String> sexMap = getSexSelect(company);
			Map<String, String> relationMap = getRelationSelect(company, productId);
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
			String XCountry = "none";
			String FCountry = "none";
			QueryBuilder qb_des = new QueryBuilder(
					"SELECT destinationCountry,destinationCountryText FROM sdinformationinsured WHERE ordersn=? ");
			qb_des.add(orderSn);
			DataTable dt_des = qb_des.executeDataTable();
			String destinationCountry = "";
			String destinationCountryText = "";
			if (dt_des.getRowCount() > 0) {
				destinationCountry = dt_des.get(0).getString(0);
				destinationCountryText = dt_des.get(0).getString(1);
			}
			if (singleFlag) {
				Map<String, String> destinationCountryMap = getdestinationCountrySelect(company, productId);
				map.put("destinationCountry", HtmlUtil.mapxToOptions(destinationCountryMap,
						destinationCountry, false));
				XCountry = "";
			} else {

				if (StringUtil.isNotEmpty(destinationCountry)) {
					String Destination = destinationCountry;
					String NDestination = "";
					String FDestination = "已选国家：";
					if (destinationCountry.split(",").length > 0) {
						int j = destinationCountry.split(",").length;
						String Destinations[] = destinationCountry.split(",");
						for (int i = 0; i < j; i++) {
							if (i == 0) {
								NDestination = setDestinationCountry(company, Destinations[i]);
								FDestination += "&nbsp;" + setDestinationCountry(company, Destinations[i]);
							} else {
								NDestination += "," + setDestinationCountry(company, Destinations[i]);
								FDestination += "&nbsp;" + setDestinationCountry(company, Destinations[i]);
							}
						}
					} else {
						NDestination = setDestinationCountry(company, Destination);
						FDestination = "&nbsp;" + setDestinationCountry(company, Destination);
					}
					map.put("NDestination", NDestination);
					map.put("FDestination", FDestination);
					FCountry = "";
				}
			}
			map.put("FCountry", FCountry);
			map.put("XCountry", XCountry);

			map.put("recognizeeIdentityType", HtmlUtil.mapxToOptions(certMap,
					null, false));
			map.put("recognizeeSex", HtmlUtil.mapxToOptions(sexMap,
					null, false));
			map.put("recognizeeAppntRelation", HtmlUtil.mapxToOptions(relationMap,
					null, false));

			Map<String, String> selectFlagMap = new HashMap<String, String>();
			selectFlagMap.put("Y", "Y");
			selectFlagMap.put("N", "N");
			map.put("healthyFlag", HtmlUtil.mapxToOptions(selectFlagMap, null, false));

			String specialEffDateProductIds = new QueryBuilder(
					"SELECT CodeValue FROM zdcode WHERE CodeType='specialEffDateFlag' and CodeValue=?", productId)
					.executeString();
			if (StringUtil.isNotEmpty(specialEffDateProductIds)) {
				map.put("specialEffDateFlag", "Y");
			} else {
				map.put("specialEffDateFlag", "N");
			}
			return map;
		} else {
			return params;
		}
	}

	public static String setDestinationCountry(String insuranceCode, String DestinationCountry) {

		if ((null == DestinationCountry)) {
			return "";
		}

		// String[] destinations = insured.getDestinationCountry().split(",");
		String[] destinations = DestinationCountry.split(",");
		Set<String> countrySet = new HashSet<String>();
		StringBuffer destinationText = new StringBuffer();
		int j = 0;
		String sql = "select 1 from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add("DestinationCountryText");
		qb.add("DestinationCountryText");
		qb.add(insuranceCode);
		boolean saveEnNameFlag = qb.executeInt() > 0;
		for (int i = 0; i < destinations.length; i++) {
			String name = null;
			String CountryCode = destinations[i].trim();
			if (saveEnNameFlag) {
				name = getCodeEnNameByCodeValue(insuranceCode, "CountryCode", CountryCode);
			} else {
				name = findCountryNameByCode(insuranceCode, CountryCode);
			}

			if (StringUtil.isEmpty(name)) {
				continue;
			}
			countrySet.add(CountryCode);
			if (j > 0) {
				if (destinationText.indexOf(name) == -1) {
					destinationText.append(",");
				}
			}
			if (destinationText.indexOf(name) == -1) {
				destinationText.append(name);
			}

			j++;
		}
		int m = 0;
		DestinationCountry = "";
		for (String cCode : countrySet) {
			if (m > 0) {
				DestinationCountry = DestinationCountry + ",";
			}
			DestinationCountry = DestinationCountry + cCode;
			m = m + 1;
		}
		return destinationText.toString();
		// insured.setDestinationCountryText();
	}

	public static String getCodeEnNameByCodeValue(String insuranceCode, String CountryCode, String CountryCodevalue) {

		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,codeEnName,flagType from dictionary where codeType='CountryCode' and  codeValue = ? and insuranceCode=? order by id asc");
		qb.add(CountryCodevalue);
		qb.add(insuranceCode);
		String enmame = "";
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			enmame = dt.getString(0, 3).replaceAll("&amp;", "&");
		}
		return enmame;
	}

	public static String getCodeNameByCodeValue(String insuranceCode, String CountryCode, String CountryCodevalue,
			String productid) {

		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,flagType from dictionary where codeType='CountryCode' and  codeValue = ? and productid=?  order by id asc");
		qb.add(CountryCodevalue);
		qb.add(productid);
		String mame = "";
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			mame = dt.getString(0, 1);
		} else {
			QueryBuilder qb_insurance = new QueryBuilder(
					"select codeValue,codeName,flagType from dictionary where codeType='CountryCode' and  codeValue = ? and insuranceCode=? and (productid is null or  productid='' ) order by id asc");
			qb_insurance.add(CountryCodevalue);
			qb_insurance.add(insuranceCode);
			DataTable dt_insurance = qb_insurance.executeDataTable();
			if (dt_insurance != null && dt_insurance.getRowCount() > 0) {
				mame = dt_insurance.getString(0, 1);
			}
		}

		return mame;
	}

	// 平安
	public static String getCountryText2007(String cComCode, String cCountryCode) {

		List<HashMap<String, String>> countryList = new ArrayList<HashMap<String, String>>();
		String shenGenEn = "";
		String shenGenCh = "";
		String unShenGenEn = "";
		String unShenGenCh = "";
		String dest = "";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select codeEnName,codeName,flagType from dictionary where codetype='CountryCode' and insuranceCode = ? and codeValue in (");
			if (StringUtil.isNotEmpty(cCountryCode)) {
				String[] s = cCountryCode.split(",");
				for (int i = 0; i < s.length; i++) {
					if (i != (s.length - 1)) {
						sb.append("'" + s[i] + "',");
					} else {
						sb.append("'" + s[i] + "'");
					}
				}
			}
			sb.append(")");
			String[] sqltemp = { cComCode };
			countryList = new GetDBdata().query(sb.toString(), sqltemp);
			Iterator<HashMap<String, String>> countryIt = countryList.iterator();
			while (countryIt.hasNext()) {
				HashMap<String, String> countryMap = (HashMap<String, String>) countryIt.next();
				if ("Y".equals(countryMap.get("flagType"))) {
					shenGenEn += countryMap.get("codeEnName") + ",";
					shenGenCh += countryMap.get("codeName") + ",";
				} else if ("N".equals(countryMap.get("flagType"))) {
					unShenGenEn += countryMap.get("codeEnName") + ",";
					unShenGenCh += countryMap.get("codeName") + ",";
				}
			}
			if (StringUtil.isNotEmpty(unShenGenCh) && StringUtil.isNotEmpty(unShenGenEn)) {
				dest = shenGenCh + "申根协议国家" + " " + shenGenEn + "SCHENGEN STATES" + " "
						+ unShenGenCh.substring(0, unShenGenCh.lastIndexOf(",")) + " "
						+ unShenGenEn.substring(0, unShenGenEn.lastIndexOf(","));
			} else {
				dest = shenGenCh + "申根协议国家" + " " + shenGenEn + "SCHENGEN STATES" + " ";
			}
		} catch (Exception e) {
			logger.error("查询目的地异常" + e.getMessage(), e);
		}
		return dest;
	}

	// 1015
	public static String getCountryText1015(String comCode, String destinationCountry) {

		String[] country = destinationCountry.split(",");
		String countryEn = "";
		if (country != null && country.length > 0) {
			for (String s : country) {
				countryEn = getNameByCodeValue(comCode, "CountryCode", s.trim()).get("codeEnName");
				if ("Y".equals(getNameByCodeValue(comCode, "CountryCode", s.trim()).get("flagType").trim())) {
					return countryEn;
				}
			}
		}
		return countryEn;
	}

	public static String getCodeValueByCodeName(String productid, String insuranceCode, String CountryCode, String CodeName) {

		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,codeEnName,flagType from dictionary where codeType='CountryCode' and  CodeName = ? and productid=? order by id asc");
		qb.add(CodeName);
		qb.add(productid);
		String codeValue = "";
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			codeValue = dt.getString(0, 0).replaceAll("&amp;", "&");
		} else {
			QueryBuilder qbinsu = new QueryBuilder(
					"select codeValue,codeName,codeEnName,flagType from dictionary where codeType='CountryCode' and  CodeName = ? and insuranceCode=? order by id asc");
			qbinsu.add(CodeName);
			qbinsu.add(insuranceCode);
			DataTable dt_insu = qbinsu.executeDataTable();
			if (dt_insu != null && dt_insu.getRowCount() > 0) {
				codeValue = dt_insu.getString(0, 0).replaceAll("&amp;", "&");
			}
		}
		return codeValue;
	}

	public static Map<String, String> getNameByCodeValue(String insuranceCode, String codetype,
			String codeValue) {

		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,codeEnName,flagType from dictionary where codeType='CountryCode' and  codeValue = ? and insuranceCode=? order by id asc");
		qb.add(codeValue);
		qb.add(insuranceCode);
		String codeEnName = "";
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			codeEnName = dt.getString(0, 1).replaceAll("&amp;", "&");
			map.put("codeEnName", codeEnName);
			map.put("flagType", dt.getString(0, 3));
		}
		return map;
	}

	public static String findCountryNameByCode(String insuranceCode, String CountryCode) {

		Map<String, String> map = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder(
				"select codeValue,codeName,codeEnName,flagType from dictionary where codeType='CountryCode' and  codeValue = ?  order by id asc");
		qb.add(CountryCode);
		String countrymame = "";
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			countrymame = dt.getString(0, 1).replaceAll("&amp;", "&");
		}
		return countrymame;
	}

	public static String getSchengenCountryText(String cComCode, String cCountryCode, String cProductID) {

		List<HashMap<String, String>> countryList = new ArrayList<HashMap<String, String>>();
		String shenGen = "";
		String unShenGen = "";
		String dest = "";
		try {
			int t = new QueryBuilder(" SELECT COUNT(1) FROM dictionary WHERE codetype='CountryCode' AND productId = ? ",
					cProductID).executeInt();
			String wherepart = " and insuranceCode = '" + cComCode + "'";
			if (t >= 1) {
				wherepart = " and productId = '" + cProductID + "'";
			}
			StringBuffer sb = new StringBuffer();
			sb.append("select codeEnName,codeName,flagType from dictionary where codetype='CountryCode' " + wherepart
					+ " and codeValue in (");
			if (StringUtil.isNotEmpty(cCountryCode)) {
				String[] s = cCountryCode.split(",");
				for (int i = 0; i < s.length; i++) {
					if (i != (s.length - 1)) {
						sb.append("'" + s[i] + "',");
					} else {
						sb.append("'" + s[i] + "'");
					}
				}
			}
			sb.append(")");
			// String[] sqltemp = {cComCode};
			// countryList = new GetDBdata().query(sb.toString(),sqltemp);
			countryList = new GetDBdata().query(sb.toString());
			Iterator<HashMap<String, String>> countryIt = countryList.iterator();
			boolean schengenFlag = false;
			while (countryIt.hasNext()) {
				HashMap<String, String> countryMap = (HashMap<String, String>) countryIt.next();
				if ("Y".equals(countryMap.get("flagType"))) {
					if (countryMap.get("codeEnName").toUpperCase().indexOf("SCHENGEN") == -1) {
						shenGen += countryMap.get("codeName") + "/" + countryMap.get("codeEnName") + "，";
					} else {
						schengenFlag = true;
					}
				} else if ("N".equals(countryMap.get("flagType")) || "".equals(countryMap.get("flagType"))) {

					unShenGen += countryMap.get("codeName") + "/" + countryMap.get("codeEnName") + "，";
				}
			}
			String shenGenChar = "申根国家/Schengen ";
			if (StringUtil.isNotEmpty(shenGen)) {
				dest = dest + shenGen + shenGenChar;
			}
			if (StringUtil.isEmpty(shenGen) && schengenFlag) {
				dest = dest + shenGenChar;
			}
			if (StringUtil.isNotEmpty(unShenGen)) {
				dest = dest + unShenGen;
			}
		} catch (Exception e) {
			logger.error("查询目的地异常" + e.getMessage(), e);
		}
		dest = dest.substring(0, dest.length() - 1) + ";";
		return dest;
	}

	private static String getAmount(String amount) {

		if (StringUtils.isNotBlank(amount)) {
			return String.valueOf(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		return "0";
	}

	/**
	 * 修改订单信息
	 * */
	public void editOrders() {

		// String createDate = $V("createDate");
		String orderSn = $V("orderSn");
		String orderStatus = $V("orderStatus");
		String recognizeeSn = $V("recognizee_Sn");
		try {
			GetDBdata db = new GetDBdata();

			String status = db.getOneValue("select orderStatus from sdorders where orderSn='" + orderSn + "'");
			if ("7".equals(status)) {
				Response.setLogInfo(1, "已支付状态不允许修改！");
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Double paidAmount = Double.valueOf($V("payAmount"));
		// Double paymentFee = Double.valueOf($V("paymentFee"));

		Double productTotalPrice = Double.valueOf($V("productTotalPrice"));
		Double totalAmount = Double.valueOf($V("totalAmount"));
		String policyNumber = $V("policyNumber");// 加入保单号 add by fhz 20121210
		SDOrdersSchema order = new SDOrdersSchema();
		SDInformationRiskTypeSchema insured = new SDInformationRiskTypeSchema();
		GetDBdata db = new GetDBdata();
		String id = $V("order_id");
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
				String sql = "select id from SDInformationRiskType where orderSn = '" + order.getorderSn()
						+ "' and recognizeeSn='" + recognizeeSn + "'";
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
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "修改" + orderSn + "失败!");
			}
		} else {

		}
	}

	/**
	 * 修改订单信息 update moddate by wangej 20130904
	 * 
	 * */
	public void editOrdersDate(String orderSn) {

		GetDBdata db = new GetDBdata();
		String updateSql = "update SDOrders set modifyDate = SYSDATE()  where orderSn = '" + orderSn + "'";
		try {
			db.execUpdateSQL(updateSql, null);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 修改订单信息 update updateMemberId by wangej 20130904
	 * 
	 * */
	public void updateMemberId() {

		String memberEmail = $V("memberEmail");
		String orderSn = $V("orderSn");
		String memberMobileNo = $V("memberMobileNo");
		String memberid_new = $V("memberid");
		GetDBdata db = new GetDBdata();
		String updateSql = "";
		QueryBuilder qb_order = new QueryBuilder(
				"select t1.memberid,t2.point from  sdorders t1,sdinformation t2 where t1.ordersn=t2.ordersn and t1.ordersn=?",
				orderSn);
		DataTable dt_order = qb_order.executeDataTable();
		if (dt_order.getRowCount() > 0) {
			String memberid_old = String.valueOf(dt_order.get(0, 0));
			if (StringUtil.isNotEmpty(memberid_old)) {
				if (memberid_old.trim().equals(memberid_new)) {
					Response.setLogInfo(0, "更改关联的用户应为不同的用户!");
				}
				// 没有起保，处理结积分
				if (checkPeriodEffectiveness(orderSn)) {
					QueryBuilder qb_point = new QueryBuilder("SELECT point FROM member WHERE id IN ('" + memberid_old
							+ "','" + memberid_new + "') ORDER BY FIND_IN_SET(id,'" + memberid_old + ","
							+ memberid_new + "')");
					DataTable dt_point = qb_point.executeDataTable();
					int point_old = StringUtil.changeStringToInt(String.valueOf(dt_point.get(0, 0)));
					int point_new = StringUtil.changeStringToInt(String.valueOf(dt_point.get(1, 0)));
					int sdorder_ponit = StringUtil.changeStringToInt(String.valueOf(dt_order.get(0, 1)));
					QueryBuilder qb_point_intcalendar = new QueryBuilder(
							"SELECT SUM(integral) FROM  sdintcalendar  WHERE prop1 IN (SELECT id FROM sdinformationrisktype WHERE ordersn=?)",
							orderSn);
					DataTable dt_intcalendar = qb_point_intcalendar.executeDataTable();
					int point_intcalendar = 0;
					if (dt_intcalendar.getRowCount() > 0) {
						point_intcalendar = StringUtil.changeStringToInt(String.valueOf(dt_intcalendar.get(0, 0)));
					}
					int point_subtract = subtractToZero(sdorder_ponit, point_intcalendar);
					Transaction tran = new Transaction();
					tran.add(new QueryBuilder("update member set point =? where id=?", subtractToZero(point_old,
							point_subtract), memberid_old));
					tran.add(new QueryBuilder("update member set point =? where id=?", point_new + point_subtract,
							memberid_new));
					tran.add(new QueryBuilder("update sdorders set memberid =? where ordersn=?", memberid_new, orderSn));
					tran.add(new QueryBuilder("update SDIntCalendar set memberid =? where businessid=?", memberid_new,
							orderSn));
					tran.add(new QueryBuilder("update sdshoppingcart set memberid =? where ordersn=?", memberid_new, orderSn));
					tran.add(new QueryBuilder(
							"update SDIntCalendar set memberid =? where businessid in (SELECT id FROM sdinformationrisktype WHERE ordersn=?)",
							memberid_new, orderSn));
					if (tran.commit()) {
						Response.setLogInfo(1, "修改成功");
					} else {
						Response.setLogInfo(0, "修改" + memberEmail + "失败!");
					}

				}
				// 已起保，处理可用积分
				else {
					QueryBuilder qb_currentValidatePoint = new QueryBuilder(
							"SELECT currentValidatePoint FROM member WHERE id IN ('" + memberid_old + "','" + memberid_new
									+ "') ORDER BY FIND_IN_SET(id,'" + memberid_old + "," + memberid_new + "')");
					DataTable dt_currentValidatePoint = qb_currentValidatePoint.executeDataTable();
					int currentValidatePoint_old = StringUtil.changeStringToInt(String.valueOf(dt_currentValidatePoint.get(
							0, 0)));
					int currentValidatePoint_new = StringUtil.changeStringToInt(String.valueOf(dt_currentValidatePoint.get(
							1, 0)));
					int sdorder_ponit = StringUtil.changeStringToInt(String.valueOf(dt_order.get(0, 1)));
					QueryBuilder qb_point_intcalendar = new QueryBuilder(
							"SELECT SUM(integral) FROM  sdintcalendar  WHERE prop1 IN (SELECT id FROM sdinformationrisktype WHERE ordersn=?)",
							orderSn);
					DataTable dt_intcalendar = qb_point_intcalendar.executeDataTable();
					int point_intcalendar = 0;
					if (dt_intcalendar.getRowCount() > 0) {
						point_intcalendar = StringUtil.changeStringToInt(String.valueOf(dt_intcalendar.get(0, 0)));
					}
					int point_subtract = subtractToZero(sdorder_ponit, point_intcalendar);
					Transaction tran = new Transaction();
					tran.add(new QueryBuilder("update member set currentValidatePoint =? where id=?", subtractToZero(
							currentValidatePoint_old, point_subtract), memberid_old));
					tran.add(new QueryBuilder("update member set currentValidatePoint =? where id=?",
							currentValidatePoint_new + point_subtract, memberid_new));
					tran.add(new QueryBuilder("update sdorders set memberid =? where ordersn=?", memberid_new, orderSn));
					tran.add(new QueryBuilder("update SDIntCalendar set memberid =? where businessid=?", memberid_new,
							orderSn));
					tran.add(new QueryBuilder("update sdshoppingcart set memberid =? where ordersn=?", memberid_new, orderSn));
					tran.add(new QueryBuilder(
							"update SDIntCalendar set memberid =? where businessid in (SELECT id FROM sdinformationrisktype WHERE ordersn=?)",
							memberid_new, orderSn));
					if (tran.commit()) {
						Response.setLogInfo(1, "修改成功");
					} else {
						Response.setLogInfo(0, "修改" + memberEmail + "失败!");
					}
				}
			} else {
				if (StringUtil.isEmpty(memberEmail)) {
					updateSql = "update SDOrders a,member b  set a.modifyDate = SYSDATE(), a.memberId = b.id where a.orderSn = '"
							+ orderSn + "' and b.mobileNo='" + memberMobileNo + "'";
				} else {
					updateSql = "update SDOrders a,member b  set a.modifyDate = SYSDATE(), a.memberId = b.id where a.orderSn = '"
							+ orderSn + "' and b.email = '" + memberEmail + "'";
				}
				try {
					db.execUpdateSQL(updateSql, null);
					Response.setLogInfo(1, "修改成功");
				} catch (Exception e) {
					Response.setLogInfo(0, "修改" + memberEmail + "失败!");
					logger.error(e.getMessage(), e);
				}
			}
		} else {
			logger.warn("订单管理会员时,查询该订单没有数据,订单号为:{}", orderSn);
		}
	}

	/**
	 * 
	 * @Title: subtractToZero
	 * @Description: TODO(差值小于0时，置0)
	 * @return int 返回类型
	 * @author zhangjing
	 */
	private int subtractToZero(int one, int two) {

		if (one > two) {
			return one - two;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @Title: checkPeriodEffectiveness
	 * @Description: TODO(判断是否起保，true为没有达到起保日期)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	public boolean checkPeriodEffectiveness(String ordersn) {

		if (StringUtil.isNotEmpty(ordersn)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			QueryBuilder qb_information = new QueryBuilder(
					"select insurancecompany,productid,startdate from  sdinformation where ordersn=?", ordersn);
			DataTable dt_information = qb_information.executeDataTable();
			if (dt_information.getRowCount() > 0) {
				int startPeriod = 1;
				String comCode = dt_information.getString(0, 0);
				String productId = dt_information.getString(0, 1);
				Date checkStartDay = DateUtil.parse(dt_information.getString(0, 2), "yyyy-MM-dd HH:mm:ss");
				QueryBuilder qb_period = new QueryBuilder(
						"SELECT startPeriod  FROM ProductPeriod WHERE comCode=?  AND riskCode=?  ORDER BY id  DESC",
						comCode, productId);
				DataTable dt_period = qb_period.executeDataTable();
				String stp = "";
				if (dt_period.getRowCount() > 0) {
					stp = dt_period.getString(0, 0);
				}
				if (StringUtil.isNotEmpty(stp)) {
					startPeriod = Integer.parseInt(stp);
				}
				String newDate = sdf.format(PubFun.calDate(new Date(), startPeriod, "D", null));
				if (StringUtil.isNotEmpty(newDate)) {
					newDate = newDate + " 00:00:00";
				}
				try {
					Date startDate = sdf.parse(newDate);
					if (checkStartDay.getTime() - startDate.getTime() >= 0) {
						return true;
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
			}

		}
		return false;
	}

	public void dg1Edit3() { // 修改订单基本信息

		String orderSn = $V("orderSn");
		SDOrdersSchema ordersSchemamember = new SDOrdersSchema();
		SDOrdersSet ordersSet = ordersSchemamember.query(new QueryBuilder("where orderSn ='" + orderSn + "'"));
		ordersSchemamember = ordersSet.get(0);
		// ordersSchemamember.fill();
		if (ordersSchemamember.fill()) {
			ordersSchemamember.setValue(Request);
			if (ordersSchemamember.update()) {
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "修改" + ordersSchemamember.getorderSn() + "失败!");
			}
		}
	}

	/**
	 * 修改订单条目
	 * */
	public void editOrderItemUpdate() {

		DataTable dt = (DataTable) Request.get("DT");
		// Transaction trans = new Transaction();
		try {
			boolean sendTaobaoFlag = false;
			for (int i = 0; i < dt.getRowCount(); i++) {
				// QueryBuilder qb = new QueryBuilder(
				// " update SDInformationRiskType set policyNo=?,svaliDate=? ,evaliDate=? where id=? ");
				// qb.add(dt.getString(i, "policyNo"));
				// qb.add(dt.getString(i, "svaliDate"));
				// qb.add(dt.getString(i, "evaliDate"));
				// qb.add(dt.getString(i, "id"));
				// trans.add(qb);
				sendTaobaoFlag = false;
				String sql = "";
				String[] sqltemp = new String[1];

				SDInformationRiskTypeSchema insured = new SDInformationRiskTypeSchema();
				insured.setId(dt.getString(i, "baodanID"));
				if (insured.fill()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// 淘宝的订单
					if (!StringUtil.isEmpty(insured.getorderSn()) && insured.getorderSn().toUpperCase().indexOf("TB") == 0) {
						if (insured.getoperationflag() < Integer.valueOf(Config.getValue("operationflag"))) {
							// 保单号插入时掉出单回调
							if (!StringUtil.isEmpty(dt.getString(i, "policyNo"))) {
								sendTaobaoFlag = true;
								insured.setoperationflag(insured.getoperationflag() + 1);
							}
						} else {
							if (!dt.getString(i, "policyNo").equals(insured.getpolicyNo())) {
								Response.setLogInfo(0, "已达到操作上限");
								return;
							}
						}
					}

					insured.setpolicyNo(dt.getString(i, "policyNo"));
					insured.setsvaliDate(sdf.parse(dt.getString(i, "svaliDate")));
					insured.setevaliDate(sdf.parse(dt.getString(i, "evaliDate").substring(0, 10) + " 23:59:59"));
					insured.setmodifyDate(new Date());
					if (insured.update()) {
						// add by wangej 20130904 更新SDOrders修改时间
						editOrdersDate(insured.getorderSn());
						Response.setLogInfo(1, "修改成功");
						// 出单回调
						if (sendTaobaoFlag) {
							String result = transformXMLToString(reponseResultTB(insured));
							taoBaoAsync(result, "?comId=" + comId);
						}
					} else {
						Response.setLogInfo(0, "修改失败!");
					}
				}
				// QueryBuilder qb1 = new QueryBuilder(
				// " update orderitem set productPrice=? where id in ( "
				// + "select orderitem_id from information  where id = ?)");
				// qb1.add(dt.getDouble(i, "productPrice"));
				// qb1.add(dt.getString(i, "id"));
				// trans.add(qb1);

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "修改失败!");
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
	private Document reponseResultTB(SDInformationRiskTypeSchema insured) throws Exception {

		String currentDate = DateUtil.getCurrentDate();
		String currentTime = DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
		Element rootPackageList = new Element("PackageList");
		Element rootPackage = new Element("Package");
		Document doc = new Document(rootPackageList); // 要创建的XML文档 - doc

		// 取得唯一编码
		String sql = "select * from SDOrders where orderSn = ?";
		String sqltemp[] = { insured.getorderSn() };
		List<HashMap<String, String>> list = new GetDBdata().query(sql, sqltemp);
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
			TotalPremiumElement.setText(String.valueOf((int) (Double.parseDouble(insured.gettimePrem()) * 100)));
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

	public static Mapx init(Mapx params) {

		params.put("createDate", PubFun.getPrevMonthDay(getFormat("yyyy-MM-dd", new Date())));
		params.put("endCreateDate", getFormat("yyyy-MM-dd", new Date()));
		// 判断当前用户是否有保单一键变更的权限
		QueryBuilder qb = new QueryBuilder(
				"select count(1) from zdcode where CodeType='PolicyInfoChange' and CodeValue=?",
				User.getUserName());
		if (qb.executeInt() > 0) {
			// 有权限设置标识“1”
			params.put("PolicyInfoChange", "1");
		}
		String mxbChannelSns = new QueryBuilder(
				" SELECT GROUP_CONCAT(ChannelCode) FROM ChannelInfo WHERE ParentInnerChanelCode='00006'").executeString();
		params.put("mxbChannelSns", mxbChannelSns);
		params.put("channelsn", HtmlUtil.codeToOptions("Activity.channel", true));
		params.put("OrderStatus", HtmlUtil.codeToOptions("orderstatus", true));
		return params;
	}

	private static String getFormat(String format, Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	// 保全记录是否有重复
	public void checkRemark() {

		String orderSn = $V("orderSn");
		String remark = $V("rem");
		String sql = "select OperateName,id,remark from sdremark where orderSn='" + orderSn + "'" + " AND remark IN ('"
				+ remark + "') ORDER BY OperateTime DESC";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			Response.setStatus(2);
		}
	}

	// 保全记录添加
	public void save() {

		SDRemarkSchema comment = new SDRemarkSchema();
		SDOrdersSchema orders = new SDOrdersSchema();
		String orderSn = $V("orderSn");
		String prodid = $V("prodid");
		String oid = $V("id");
		String id = PubFun.GetNRemarkId();
		String remark = $V("rem");
		String operateType = $V("hidoperateType");
		String CurrentDateTime = DateUtil.getCurrentDateTime();
		try {
			Date modifydate = PubFun.StringToDate(CurrentDateTime, "yyyy-MM-dd HH:mm:ss");
			comment.setid(id);
			comment.setremark(remark);
			comment.setorderSn(orderSn);
			comment.setOperateName(User.getUserName());
			comment.setOperateTime(modifydate);
			comment.setOperateType(operateType);
			comment.setprop1("");
			comment.setprop2("");
			comment.setupperId("");
			comment.insert();
			// 修改订单信息--订单时间
			String sql = "SELECT OperateTime FROM sdremark WHERE ordersn='" + orderSn + "' ORDER BY OperateTime DESC ";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				Response.setLogInfo(1, "保存成功!");
				Response.put("Group", dt.getString(0, "OperateTime"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败!");
		}
	}

	// 保全记录修改
	public void update() {

		SDOrdersSchema orders = new SDOrdersSchema();
		String orderSn = $V("orderSn");
		String remark = $V("rem");
		String oid = $V("id");
		SDRemarkSchema comment = new SDRemarkSchema();
		String CurrentDateTime = DateUtil.getCurrentDateTime();
		String operateType = $V("hidoperateType");
		Date modifydate = PubFun.StringToDate(CurrentDateTime, "yyyy-MM-dd HH:mm:ss");
		try {
			String sql = "select OperateName,id,remark from sdremark where orderSn='" + orderSn + "'"
					+ " ORDER BY OperateTime DESC";
			QueryBuilder qb = new QueryBuilder(sql);

			DataTable dt = qb.executeDataTable();
			String operateName = "";
			String pid = "";
			if (dt != null && dt.getRowCount() > 0) {
				operateName = dt.getString(0, "operateName");
				pid = dt.getString(0, "id");
				if (remark.equals(dt.getString(0, "remark"))) {
					Response.setLogInfo(0, "您未做任何修改！");
					return;
				}
			}
			if (!operateName.equals(User.getUserName())) {
				Response.setLogInfo(0, "抱歉，您不能修改此记录！");
				return;
			}
			comment.setid(pid);
			if (comment.fill()) {
				comment.setremark(remark);
				comment.setOperateName(User.getUserName());
				comment.setOperateTime(modifydate);
				comment.setOperateType(operateType);
				comment.setorderSn(orderSn);
				comment.setprop1("");
				comment.setprop2("");
				comment.setupperId(pid);
				comment.update();
			}
			// 修改订单信息--订单时间
			String sqlo = "SELECT OperateTime FROM sdremark WHERE ordersn='" + orderSn + "' ORDER BY OperateTime DESC ";
			QueryBuilder qbo = new QueryBuilder(sqlo);
			DataTable dto = qbo.executeDataTable();
			if (dt != null && dto.getRowCount() > 0) {
				Response.setLogInfo(1, "保存成功!");
				Response.put("Group", dto.getString(0, "OperateTime"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	// 保全记录删除
	public void del() {

		SDOrdersSchema orders = new SDOrdersSchema();
		String orderSn = $V("orderSn");
		String oid = $V("id");
		String sql = "select OperateName,id from sdremark where orderSn='" + orderSn + "'" + " ORDER BY OperateTime DESC";
		QueryBuilder qb = new QueryBuilder(sql);

		DataTable dt = qb.executeDataTable();
		String operateName = "";
		String id = "";
		if (dt != null && dt.getRowCount() > 0) {
			operateName = dt.getString(0, "operateName");
			id = dt.getString(0, "id");
		}
		if (!operateName.equals(User.getUserName())) {
			Response.setStatus(0);
			Response.setMessage("抱歉，您不能删除此记录！");
			return;
		}

		String sqlb = "insert into bsdremark select * from sdremark where id='" + id + "'";
		QueryBuilder mQB = new QueryBuilder(sqlb);
		mQB.executeNoQuery();
		SDRemarkSchema remark = new SDRemarkSchema();
		try {
			remark.setValue(Request);
			remark.setid(id);
			remark.fill();
			if (remark.delete()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			}
			String sqlo = "SELECT OperateTime FROM sdremark WHERE ordersn='" + orderSn + "' ORDER BY OperateTime DESC ";
			QueryBuilder qbo = new QueryBuilder(sqlo);
			DataTable dto = qbo.executeDataTable();
			if (dto != null && dto.getRowCount() > 0) {
				Response.put("Group", dto.getString(0, "OperateTime"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("删除失败!");
		}
	}

	public void baoquan(DataGridAction dga) {

		String orderSn = $V("orderSn");

		QueryBuilder qb = new QueryBuilder("SELECT remark,operateType,operateName,operateTime FROM sdremark WHERE ordersn='"
				+ orderSn + "' ORDER BY OperateTime DESC ");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "operateType").equals("add")) {
					dt.set(i, "operateType", "添加");
				} else {
					dt.set(i, "operateType", "修改");
				}
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static Mapx baoquanModi(Mapx<String, Object> params) {

		String orderSn = params.getString("orderSn");
		if (StringUtil.isNotEmpty(orderSn)) {
			String sql = "SELECT OperateTime FROM sdremark WHERE ordersn='" + orderSn + "' ORDER BY OperateTime DESC ";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				params.put("remarkdefault", dt.getString(0, "OperateTime"));
			}
		}
		return params;
	}

	public Map<String, String> download(Map<String, String> map) {

		Map<String, String> result = new HashMap<String, String>();
		result.put("Success", "N");
		// 请求参数
		String param = "";
		NameValuePair[] pair = null;
		String responseXml = "";
		// 保险公司编码
		String comCode = map.get("comCode");
		// 保单号
		String serialNo = map.get("policyNo");
		// 产品分类
		String channel = map.get("channel");
		// 承保日期
		String insureDate = map.get("insureDate");
		// 电子保单路径
		String pdfPath = map.get("pdfPath");
		// 被保人证件类型
		String recognizeeIdentityType = map.get("recognizeeIdentityType");
		// 被保人证件号
		String recognizeeIdentityId = map.get("recognizeeIdentityId");
		// 投保序号
		String applyPolicyNo = map.get("applyPolicyNo");
		// 投保人证件号
		String applicantIdentityId = map.get("applicantIdentityId");
		// 电子保单下载路径
		String electronicCout = map.get("electronicCout");

		NameValuePair serialNoP = new NameValuePair("serialNo", serialNo);
		NameValuePair comCodeP = new NameValuePair("comCode", comCode);
		NameValuePair insureDateP = new NameValuePair("insureDate", insureDate);
		NameValuePair policyPath = new NameValuePair("policyPath", pdfPath);
		NameValuePair channelP = new NameValuePair("channel", channel);

		if ("2011".equals(comCode) || "1061".equals(comCode)) {
			if (StringUtil.isEmpty(recognizeeIdentityType)) {
				result.put("Message", "保单(" + serialNo + ")的电子保单下载需要被保人证件类型!");
				return result;
			}
			if (StringUtil.isEmpty(recognizeeIdentityId)) {
				result.put("Message", "保单(" + serialNo + ")的电子保单下载需要被保人证件号!");
				return result;
			}
			NameValuePair identityType = new NameValuePair("identityType", recognizeeIdentityType);
			NameValuePair identityId = new NameValuePair("identityId", recognizeeIdentityId);
			pair = new NameValuePair[] { serialNoP, comCodeP, insureDateP, policyPath, identityType, identityId };

		} else if ("2043".equals(comCode)) {
			// 投保人证件号
			if (StringUtil.isEmpty(applicantIdentityId)) {
				result.put("Message", "保单(" + serialNo + ")的电子保单下载需要投保人证件号!");
				return result;
			}
			NameValuePair appIdentityId = new NameValuePair("appIdentityId", applicantIdentityId);
			pair = new NameValuePair[] { serialNoP, comCodeP, insureDateP, policyPath, appIdentityId };

		} else if ("2101".equals(comCode)) {

			if (StringUtil.isEmpty(electronicCout)) {
				result.put("Message", "保单(" + serialNo + ")的电子保单下载路径为空!");
				return result;
			}
			NameValuePair channelSn = new NameValuePair("channelSn", "wj");
			NameValuePair downLoadUrl = new NameValuePair("downLoadUrl", electronicCout);
			pair = new NameValuePair[] { serialNoP, comCodeP, insureDateP, policyPath, downLoadUrl, channelSn };

		}
		else if ("1004".equals(comCode) || "1014".equals(comCode) || "1065".equals(comCode) || "1018".equals(comCode)
				|| "1038".equals(comCode) || "0007".equals(comCode) || "2046".equals(comCode) || "2103".equals(comCode)
				|| "2104".equals(comCode)) {
			if (StringUtil.isEmpty(electronicCout)) {
				result.put("Message", "保单(" + serialNo + ")的电子保单下载路径为空!");
				return result;
			}

			NameValuePair downLoadUrl = new NameValuePair("downLoadUrl", electronicCout);
			pair = new NameValuePair[] { serialNoP, comCodeP, insureDateP, policyPath, downLoadUrl };

		} else {
			pair = new NameValuePair[] { serialNoP, comCodeP, insureDateP, policyPath, channelP };
		}
		try {
			// 调用经代通电子保单下载接口
			responseXml = electronicDownloadInterface(param, pair);
			// 交易返回报文
			Document doc = new SAXBuilder().build(new ByteArrayInputStream(responseXml.getBytes("GBK")));
			String isSuccessful = doc.getRootElement().getChild("Head").getChildTextTrim("isSuccessful");
			String message = doc.getRootElement().getChild("Head").getChildTextTrim("message");
			result.put("Message", message);
			// 更新保单表
			if ("Y".equals(isSuccessful)) {
				result.put("Success", "Y");
				result.put("PolicyPath", doc.getRootElement().getChild("Body").getChildTextTrim("policyPath"));
			}

		} catch (Exception e) {
			logger.info("保单({})电子保单下载返回报文：{}", serialNo, responseXml);
			logger.error("=====保单(" + serialNo + ")电子保单下载功能异常===" + e.getMessage(), e);
			result.put("Message", "保单(" + serialNo + ")电子保单下载功能异常!");
			return result;
		}

		return result;
	}

	/**
	 * 发送续保邮件
	 */
	public void sendRenewalEmail() {

		String applicantMail = $V("applicantMail");
		String renewalUrl = $V("renewalUrl");
		if (StringUtil.isEmpty(applicantMail)) {
			Response.setLogInfo(0, "投保人邮箱不能为空!");
			return;
		}
		if (StringUtil.isEmpty(renewalUrl)) {
			Response.setLogInfo(0, "推送续保链接不能为空!");
			return;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("renewalUrl", renewalUrl);
		data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
		if (ActionUtil.sendMail("wj00133", applicantMail, data)) {
			Response.setLogInfo(1, "发送成功");
		} else {
			Response.setLogInfo(0, "发送失败!");
		}
	}

	public void sendClaimsData() {

		String mail = $V("mail");
		String content = $V("content");
		String comCode = $V("comCode");

		if (StringUtil.isEmpty(mail)) {
			Response.setLogInfo(0, "邮箱不能为空！");
			return;
		}
		if (StringUtil.isEmpty(content)) {
			Response.setLogInfo(0, "邮件内容不能为空！");
			return;
		}

		DataTable dt = new QueryBuilder(
				"SELECT c.ID, d.ShortName FROM fdinscom d, ZCCatalog c WHERE d.SupplierCode=? and d.ShortName=c.Name and c.Type ='7' ",
				comCode).executeDataTable();
		if (dt == null || dt.getRowCount() == 0) {
			Response.setLogInfo(0, "保险公司未设置理赔申请书！");
			return;
		}
		String catalogId = dt.getString(0, 0);
		String comName = dt.getString(0, 1);
		if (StringUtil.isEmpty(catalogId)) {
			Response.setLogInfo(0, "保险公司:" + comName + ",未设置理赔申请书！");
			return;
		}

		dt = new QueryBuilder("select Name, Path, FileName from zcattachment where CatalogID=? and SiteID='221'", catalogId)
				.executeDataTable();
		if (dt == null || dt.getRowCount() == 0) {
			Response.setLogInfo(0, "保险公司:" + comName + ",未设置理赔申请书！");
			return;
		}
		// 附件路径
		String FilePath = Config.getValue("StaticResourcePath") + "/" + dt.getString(0, "Path") + comCode + ".zip";
		String fileName = comName + "理赔申请书" + ".rar";
		List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();
		Map<String, Object> attach = new HashMap<String, Object>();
		attach.put("name", fileName);
		attach.put("path", FilePath);
		attachments.add(attach);
		if (ActionUtil.sendGeneralMail(mail, "开心保-理赔申请书", content, attachments)) {
			Response.setLogInfo(1, "发送成功！");
		} else {
			Response.setLogInfo(0, "发送失败！");
		}

	}

	/**
	 * 新接口电子保单下载处理
	 */
	public void electronicDownload() {

		String id = $V("IDs");
		// 产品编码
		String riskCode = $V("riskCode");

		if (StringUtil.isNotEmpty(id)) {

			SDInformationRiskTypeSchema riskType = new SDInformationRiskTypeSchema();
			riskType.setId(id);
			String responseXml = "";
			String serialNo = "";
			try {
				if (riskType.fill()) {
					if (StringUtil.isEmpty(riskType.getpolicyNo())) {
						Response.setLogInfo(0, "您选择保单没有保单号，不能下载电子保单 ！");
						return;
					}
					serialNo = riskType.getpolicyNo();
					Map<String, String> param = new HashMap<String, String>();
					param.put("comCode", riskCode.substring(0, 4));
					param.put("policyNo", riskType.getpolicyNo());
					Map<String, String> resultMap = pdfDownload(param);
					if ("N".equals(resultMap.get("Success"))) {
						Response.setLogInfo(0, resultMap.get("Message"));
					} else {
						Response.setLogInfo(1, resultMap.get("Message"));
					}
				}
			} catch (Exception e) {
				logger.info("保单({})电子保单下载返回报文：{}", serialNo, responseXml);
				logger.error("=====保单(" + serialNo + ")电子保单下载功能异常===" + e.getMessage(), e);
				Response.setLogInfo(0, "网络繁忙，请稍候重试！");
			}
		} else {
			Response.setLogInfo(0, "请选择要生成电子保单的条目 ！");
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
		NameValuePair serialNoP = new NameValuePair("policyNo", map.get("policyNo"));
		NameValuePair isSendEmailP = new NameValuePair("isSendEmail", "N");
		NameValuePair isRewriteP = new NameValuePair("isRewrite", "Y");
		NameValuePair channelCodeP = new NameValuePair("channelCode", "wj");
		NameValuePair[] pair = new NameValuePair[] { orderSnP, serialNoP, isSendEmailP, isRewriteP, channelCodeP };
		String responseXml = "";
		try {
			responseXml = this.electronicDownloadInterfaceNew(pair);
			Document doc = new SAXBuilder().build(new ByteArrayInputStream(responseXml.getBytes("GBK")));
			String isSuccessful = doc.getRootElement().getChild("Head").getChildTextTrim("isSuccessful");
			String message = doc.getRootElement().getChild("Head").getChildTextTrim("message");
			result.put("Message", message);
			if ("Y".equals(isSuccessful)) {
				result.put("Success", "Y");
				result.put("PolicyPath", doc.getRootElement().getChild("Body").getChildTextTrim("policyPath"));
			}
		} catch (Exception e) {
			logger.info("保单({})电子保单下载返回报文：{}", map.get("policyNo"), responseXml);
			logger.error("=====保单(" + map.get("policyNo") + ")电子保单下载功能异常===" + e.getMessage(), e);
			result.put("Message", "保单(" + map.get("policyNo") + ")电子保单下载功能异常!");
			return result;
		}
		return result;
	}

	public String electronicDownloadInterfaceNew(NameValuePair[] pair) throws IOException {

		HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
		String url = Config.interfaceMap.getString("PdfDownloadAccessServletNew");
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), "GBK"));
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
	 * 电子发票界面初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx electronicInvoiceInit(Mapx params) {

		String id = params.getString("IDs");
		String riskCode = params.getString("riskCode");
		if (StringUtil.isNotEmpty(id)) {

			String sql =
					"SELECT app.applicantName,app.applicantEnName,app.applicantMail,app.applicantMobile,risk.policyNo,risk.timePrem,risk.orderSn"
							+ " FROM sdinformationrisktype risk INNER JOIN sdinformationappnt app ON risk.applicantSn=app.applicantSn WHERE risk.id='"
							+ id + "'";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executePagedDataTable(0, 0);
			if (dt != null && dt.getRowCount() > 0) {
				for (int i = 0; i < dt.getRowCount(); i++) {
					String applicantName = (String) dt.get(i).get("applicantName");
					String applicantEnName = (String) dt.get(i).get("applicantEnName");
					String applicantMail = (String) dt.get(i).get("applicantMail");
					String applicantMobile = (String) dt.get(i).get("applicantMobile");
					String policyNo = (String) dt.get(i).get("policyNo");
					String timePrem = (String) dt.get(i).get("timePrem");
					String orderSn = (String) dt.get(i).get("orderSn");

					if (StringUtil.isNotEmpty(riskCode) && "2049".equals(riskCode.subSequence(0, 4))
							&& StringUtil.isNotEmpty(applicantEnName)) {
						params.put("applicantName", applicantName + "/" + applicantEnName);
					} else {
						params.put("applicantName", applicantName);
					}
					params.put("applicantMail", applicantMail);
					params.put("applicantMobile", applicantMobile);

					params.put("policyNo", policyNo);
					params.put("timePrem", timePrem);
					params.put("riskCode", riskCode);
					params.put("orderSn", orderSn);
				}
			}

		}

		return params;
	}

	/**
	 * 新接口电子保单下载处理
	 */
	public void electronicInvoiceDown() {

		String riskCode = $V("riskCode");// 产品编码
		String applicantName = $V("applicantName");

		String applicantMail = $V("applicantMail");
		String applicantMobile = $V("applicantMobile");
		String policyNo = $V("policyNo");
		String timePrem = $V("timePrem");
		String orderSn = $V("orderSn");
		String invoice_HeadType = $V("Invoice_HeadType");
		String invoice_TaxpayerId = $V("Invoice_TaxpayerId");

		if (!"01".equals(invoice_HeadType) && StringUtil.isEmpty(invoice_TaxpayerId)) {
			Response.setError("抬头为非个人时,[纳税识别号/统一社会信用代码]不能为空!");
			return;
		}

		if (StringUtil.isNotEmpty(riskCode)) {
			String jsonData =
					"{\"riskCode\" : \"" + riskCode + "\" , \"applicantName\" : \"" + applicantName
							+ "\" , \"applicantMail\" : \"" + applicantMail + "\" , \"applicantMobile\" : \""
							+ applicantMobile
							+ "\" , \"policyNo\" : \"" + policyNo + "\" , \"timePrem\" : \"" + timePrem
							+ "\" , \"orderSn\" : \"" + orderSn
							+ "\" , \"invoice_HeadType\" : \"" + invoice_HeadType
							+ "\" , \"invoice_TaxpayerId\" : \"" + invoice_TaxpayerId
							+ "\"}";
			String returnResult = (String) ServiceClient.execute(jsonData, "GBK", "json", "downloadInvoice");
			if (StringUtil.isNotEmpty(returnResult)) {
				Map<String, Object> parseResult = parseJSON2Map(returnResult);
				if ("000000".equals(parseResult.get("MessageStatusSubCode"))) {
					Response.setLogInfo(1, "电子发票下载成功，请稍后查收 ！");
				} else {
					Response.setLogInfo(0, "电子发票下载失败:" + parseResult.get("MessageStatusSubDescription"));
				}
				// <MessageStatusSubCode>000000</MessageStatusSubCode>
				// <MessageStatusSubDescription>交易成功</MessageStatusSubDescription>
			}

		} else {
			Response.setLogInfo(0, "请选择要生成电子发票的条目 ！");
		}

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
			map = null;
			logger.error(e.getMessage(), e);
		}

		return map;
	}

	public static void main(String[] args) throws FileNotFoundException {

		byte[] buf = new byte[1024];
		File listFile = new File("D:\\yzyd\\会员导入.xls");

		try {
			FileInputStream in = new FileInputStream(listFile);
			String zipFileName = "中文测试阿帕奇123123.xls";// 保单压缩文件名称
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream("F:\\zippath.zip"));
			out.setEncoding("GBK");
			out.putNextEntry(new ZipEntry(zipFileName));
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			System.out.println(zipFileName);
			out.closeEntry();
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		System.exit(0);
	}

	public void orderQuery(DataGridAction dga) {

		String createDate = dga.getParams().getString("createDate");
		String endCreateDate = dga.getParams().getString("endCreateDate");
		String tbTradeSeriNo = dga.getParams().getString("tbTradeSeriNo");
		String orderSn = dga.getParams().getString("orderSn");
		String OrderStatus = dga.getParams().getString("OrderStatus");
		String remarkContent = dga.getParams().getString("remarkContent");
		String remarkSele = dga.getParams().getString("remarkSele");
		String paySn = dga.getParams().getString("paySn");
		String channelsn = dga.getParams().getString("channelsn");
		String channelinfo = "";
		String description = dga.getParams().getString("description");
		if (StringUtil.isNotEmpty(channelsn)) {
			channelinfo = " AND FIND_IN_SET(a.channelsn, '" + channelsn + "') ";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id,d.id AS rid,a.memberid,c.ApplicantName,a.ordersn,a.orderStatus,e.policyNo,e.appstatus,e.svalidate,");
		sb.append("(SELECT ChannelName FROM channelinfo WHERE channelcode=a.channelsn) channelsn, c.ApplicantMail, ");
		sb.append("a.tbTradeSeriNo,b.ProductName,b.planName,a.channelsn as channelsn2,");
		sb.append("SUM(d.recognizeePrem) AS totalAmount,a.CreateDate,a.ModifyDate,a.orderActivity,a.PayPrice,h.codeName AS orderstatusname, ");
		sb.append("'' isoldmember,");
		sb.append("'' AS MID, m.email, m.mobileNO, ");
		sb.append(" a.orderCoupon as orderCoupon,'' parValue,a.offsetPoint as offsetPoint, a.orderIntegral as orderIntegral, a.paySn as paySn, '' as paymentReport,b.productid,b.insurancecompany ,a.diyActivityDescription as diyActivityDescription ,(SELECT ChannelName FROM channelinfo WHERE channelcode = (SELECT fromWap FROM member WHERE id=a.memberid)) fromWap ");
		sb.append(" FROM sdorders a LEFT JOIN member m ON m.id = a.memberid inner join zdcode h on h.CodeType = 'orderstatus' AND h.codevalue = a.orderstatus ");
		sb.append(" ,sdinformation b,sdinformationappnt c,sdinformationinsured d,sdinformationrisktype e ");
		sb.append("WHERE a.ordersn = b.ordersn AND b.informationsn = c.informationsn AND a.ordersn = d.ordersn AND a.ordersn = e.ordersn AND d.recognizeeSn = e.recognizeeSn ");
		if (StringUtil.isNotEmpty(createDate)) {
			sb.append(" and a.createdate >='" + createDate + " 00:00:00' ");
		}
		if (StringUtil.isNotEmpty(endCreateDate)) {
			sb.append(" and a.createdate <='" + endCreateDate + " 23:59:59' ");
		}
		// 渠道订单号
		if (StringUtil.isNotEmpty(tbTradeSeriNo)) {
			sb.append(" and a.tbTradeSeriNo like '%" + tbTradeSeriNo.trim() + "%'");
		}
		// 渠道
		sb.append(channelinfo);
		// 订单号
		if (StringUtil.isNotEmpty(orderSn)) {
			sb.append(" and a.orderSn like '%" + orderSn.trim() + "%'");
		}

		if (StringUtil.isNotEmpty(OrderStatus)) {
			sb.append(" and a.orderstatus = " + OrderStatus.trim() + "");
		}
		// 自定义活动描述
		if (StringUtil.isNotEmpty(description)) {
			sb.append(" and a.diyActivityDescription like '%" + description.trim() + "%' ");
		}
		// 保全记录
		if (StringUtil.isNotEmpty(remarkContent) || "1".equals(remarkSele)) {
			String remarkSql = "  SELECT remark,ordersn FROM sdremark WHERE " + "ordersn IS NOT NULL AND ordersn !=''  ";
			String remarkWhere = "";
			String remarkPart = " GROUP BY ordersn ";

			if (StringUtil.isNotEmpty(remarkContent)) {

				remarkWhere += " AND remark LIKE '%" + remarkContent + "%' ";
			}
			if ("1".equals(remarkSele)) {

				remarkWhere += " AND remark!='' AND remark IS NOT NULL ";
			}
			String reOrdersn = "''";
			QueryBuilder reQb = new QueryBuilder(remarkSql + remarkWhere + remarkPart);
			DataTable reDt = reQb.executeDataTable();

			if (reDt != null && reDt.getRowCount() > 0) {
				for (int k = 0; k < reDt.getRowCount(); k++) {
					if (k == 0) {
						reOrdersn = "'" + reDt.getString(k, "ordersn") + "'";
					} else {
						reOrdersn += ",'" + reDt.getString(k, "ordersn") + "'";
					}
				}
			}
			if (StringUtil.isNotEmpty(remarkContent) || "1".equals(remarkSele)) {
				sb.append(" and a.orderSn in (" + reOrdersn.trim() + ")");
			}
			if ("2".equals(remarkSele)) {
				sb.append(" and a.orderSn not in (" + reOrdersn.trim() + ")");
			}
		}
		// 商家订单号不为空的情况，查询保全记录中存在此商家订单号的订单以及属于此商家订单号的订单
		if (StringUtil.isNotEmpty(paySn)) {
			if ("2".equals(remarkSele)) {
				sb.append(" and a.paySn = '" + paySn.trim() + "'");
			} else {
				sb.append(" and (a.paySn = '" + paySn.trim() + "'");
				String remarkSql = "SELECT ordersn FROM sdremark WHERE ordersn IS NOT NULL AND ordersn !='' AND remark LIKE '%变更：初始商家订单号-"
						+ paySn + "%' GROUP BY ordersn ";
				String reOrdersn = "''";
				QueryBuilder reQb = new QueryBuilder(remarkSql);
				DataTable reDt = reQb.executeDataTable();

				if (reDt != null && reDt.getRowCount() > 0) {
					for (int k = 0; k < reDt.getRowCount(); k++) {
						if (k == 0) {
							reOrdersn = "'" + reDt.getString(k, "ordersn") + "'";
						} else {
							reOrdersn += ",'" + reDt.getString(k, "ordersn") + "'";
						}
					}
				}
				sb.append(" or a.orderSn in (" + reOrdersn.trim() + "))");
			}
		}

		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			sb.append(" and b.productName like '%" + productName.trim() + "%'");
		}
		// 会员ID 如果此时是会员查询页面跳转过来，那么此时要根据memberid来查询
		String memberid = dga.getParams().getString("memberid");

		// 会员ID 可能为 用户名 手机号 邮箱
		String mid = dga.getParams().getString("mid");
		if (StringUtil.isNotEmpty(mid) || StringUtil.isNotEmpty(memberid)) {
			sb.append(" and exists (select 'X' from member mem where mem.id =a.MemberId  ");
			if (StringUtil.isNotEmpty(mid)) {
				sb.append(" and ( mem.mobileno  like '%" + mid.trim() + "%' or mem.username  like '%" + mid.trim()
						+ "%' or mem.email like '%" + mid.trim() + "%')");
			}
			if (StringUtil.isNotEmpty(memberid)) {
				sb.append(" and mem.id like '%" + memberid.trim() + "%'");
			}
			sb.append(")");
		}

		// 投保人姓名 手机号 E-mail 证件号码 关联 informationappnt表
		String applicantName = dga.getParams().getString("applicantName");
		String shipMobile = dga.getParams().getString("shipMobile");
		String email = dga.getParams().getString("email");
		String idNO = dga.getParams().getString("idNO");
		if (StringUtil.isNotEmpty(applicantName)) {
			sb.append(" and (( c.ApplicantName like '%" + applicantName.trim() + "%') or ( d.RecognizeeName like '%"
					+ applicantName.trim() + "%'))");
		}
		if (StringUtil.isNotEmpty(email)) {
			sb.append(" and ((c.ApplicantMail like '%" + email.trim() + "%') or (d.RecognizeeMail like '%" + email.trim()
					+ "%'))");
		}
		if (StringUtil.isNotEmpty(shipMobile)) {
			sb.append(" and ( c.ApplicantMobile = '" + shipMobile.trim() + "' )");
		}
		if (StringUtil.isNotEmpty(idNO)) {
			sb.append(" and ((c.ApplicantIdentityId like '%" + idNO.trim() + "%') or (d.RecognizeeIdentityId like '%"
					+ idNO.trim() + "%'))");
		}

		String policyNo = dga.getParams().getString("policyNo");
		if (StringUtil.isNotEmpty(policyNo)) {
			sb.append(" and e.policyNo like '%" + policyNo.trim() + "%'");
		}

		sb.append(" GROUP BY a.ordersn order by a.modifydate desc,a.ordersn desc");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		String[] channelArr = { "b2b_app", "b2c_pc", "b2c_wap", "b2b_ht" };
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "orderStatus").equals("7") || dt.getString(i, "appstatus").equals("1")) {
					dt.set(i, "totalAmount", "+" + dt.getString(i, "totalAmount"));
				}
				// 保全记录查询
				/*
				 * String queryRemark =
				 * "SELECT remark,OperateTime,OperateName FROM sdremark WHERE ordersn='"
				 * + dt.getString(i, "orderSn") + "' ORDER BY OperateTime DESC";
				 * QueryBuilder qbr = new QueryBuilder(queryRemark); DataTable
				 * dtr = qbr.executeDataTable(); String remark = ""; if (dtr !=
				 * null && dtr.getRowCount() > 0) { String remarkTem = ""; for
				 * (int j = 0; j < dtr.getRowCount(); j++) { int a = j + 1;
				 * remarkTem = dtr.getString(j, "remark") + "  " +
				 * dtr.getString(j, "OperateTime") + "  " + dtr.getString(j,
				 * "OperateName") + " && "; if (j == dtr.getRowCount() -1 &&
				 * remarkTem.indexOf("变更：初始商家订单号") >= 0) { remark = remarkTem +
				 * " " + remark; } else { remark += a + ", " + remarkTem; } }
				 * dt.set(i, "remark", remark); }
				 */
				// 被保人个数查询
				// String sqlre =
				// "SELECT count(1) FROM sdinformationinsured WHERE ordersn ='"
				// + dt.getString(i, "orderSn") + "' GROUP BY recognizeekey";
				// QueryBuilder qbre = new QueryBuilder(sqlre);
				// dt.set(i, "recognizeeNu", qbre.executeInt());
				String parValue = dt.getString(i, "orderCoupon");
				BigDecimal totalamount = new BigDecimal("0.0");
				if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {

					String totalAmount = dt.getString(i, "totalAmount");
					if (StringUtil.isEmpty(totalAmount)) {
						totalAmount = "0.00";
					}
					totalamount = new BigDecimal(totalAmount);

					if (StringUtil.isNotEmpty(parValue) && totalamount.compareTo(new BigDecimal(parValue)) != -1) {
						totalamount = totalamount.subtract(new BigDecimal(parValue));
						dt.set(i, "PayPrice", totalamount);
					}
				}
				if (StringUtil.isNotEmpty(parValue)) {
					dt.set(i, "parValue", parValue);
				} else {
					dt.set(i, "parValue", "");
				}

				// 积分抵值
				if ("0".equals(dt.getString(i, "orderIntegral")) || dt.getString(i, "orderIntegral") == null
						|| "".equals(dt.getString(i, "orderIntegral"))) {
					dt.set(i, "offsetPoint", "0");
				} else {
					// 积分换算规则修改时间点
					BigDecimal offsetPoint = new BigDecimal(dt.getString(i, "orderIntegral"));
					dt.set(i, "offsetPoint", offsetPoint);
					if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {
						if (totalamount.compareTo(offsetPoint) != -1) {
							dt.set(i, "PayPrice", totalamount.subtract(offsetPoint));
						}
					}
				}

				String paymentSql = "select count(1) from paymentInfo where ordersn = ?";
				QueryBuilder paymentQb = new QueryBuilder(paymentSql, dt.getString(i, "ordersn"));
				if (paymentQb.executeInt() > 0) {
					dt.set(i, "paymentReport", "是");

				}
				if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {
					if (Integer.valueOf(dt.getString(i, "orderStatus")) < 7
							|| "8".equals(dt.getString(i, "orderStatus").trim())) {
						dt.set(i, "PayPrice", "");
					}
				}
				if (ArrayUtils.contains(channelArr, dt.getString(i, "channelsn2"))) {
					dt.set(i, "MID", dt.getString(i, "memberId"));
				}
				else {
					dt.set(i, "MID", dt.getString(i, "email") + "/" + dt.getString(i, "mobileNO"));
				}
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
}
