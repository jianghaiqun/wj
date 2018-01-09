package cn.com.sinosoft.util;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.tenpay.util.MD5Util;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadNet {
	private static final Logger logger = LoggerFactory.getLogger(DownloadNet.class);
	/**
	 * 华泰产品获取电子保单并发送.
	 * 
	 * @throws Exception
	 * 
	 */
	public String EpolicyDeal(String ordersn, String PolicyPath,
			String insureTypeCode, String insuredSn, String policyNo,
			String path, String insureDate) throws Exception {

		long id = 0;
		try {
			// 电子保单路径
			if (StringUtil.isEmpty(PolicyPath)) {
				throw new Exception("电子保单路径为空");
			}
			String insureYear = insureDate.substring(0, 4);
			String insureMonth = insureDate.substring(5, 7);
			String name = "";
			String npath = Config.getValue("newPolicyPath");
			String newPath = Config.getContextRealPath();
			if (!StringUtil.isEmpty(npath)) {
				newPath = npath;
			}

			String source = "";

			if (StringUtil.isNotEmpty(path)) {
				source = path.substring(0, path.lastIndexOf(File.separator));
				name = path.substring(path.lastIndexOf(File.separator) + 1);
			} else {
				source = newPath + File.separator + "EPolicy"
						+ File.separator + insureTypeCode + File.separator
						+ insureYear + File.separator + insureMonth;
			}
			if (StringUtil.isEmpty(name)) {
				// 输出PDF格式结果，格式“保单号MD5加密.pdf”
				if ("1099".equals(insureTypeCode)) {
					name = MD5Util.MD5Encode(policyNo, "UTF-8") + ".rar";
				} else {
					name = MD5Util.MD5Encode(policyNo, "UTF-8") + ".pdf";
				}
			}

			if (!new File(source).exists()) {
				new File(source).mkdirs();
			}
			source = source + File.separator + name;
			// insert OrdersPrint电子保单记录表
			id = saveOrdersPrint(ordersn, name, source, insuredSn, "下载开始");

			// 下载电子保单
			if (downloadNet(source, PolicyPath)) {
				updateOrdersPrint(insuredSn, "下载完成");
				return source;
			} else {
				return "";
			}

		} catch (Exception e) {
			// sendPrintErrorMail(ordersn, insuredSn, e.getMessage(), null);
			if (id != 0) {
				try {
					updateOrdersPrint(insuredSn, "下载失败");
				} catch (Exception ex) {
					logger.error(ex.getMessage(), e);
				}
			}
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param source
	 *            String 类型 本地下载路径
	 * @param downPath
	 *            String 类型 保险公司下载路径
	 * @throws Exception
	 */
	public boolean downloadNet(String source, String downPath) throws Exception {

		FileOutputStream fs = null;
		try {
			int byteread = 0;
			URL url = new URL(downPath);
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			fs = new FileOutputStream(source);

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}

			File f = new File(source);
			if (!f.exists()) {
				throw new Exception("从保險公司官网下载电子保单异常，下载完不存在");
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("从保險公司官网下载电子保单异常，具体错误：" + e.getMessage());
		} finally {
			fs.close();
		}
	}

	/**
	 * 发送电子保单错误提示
	 * 
	 * @param ordsn
	 *            类型 String 订单号
	 * @param errMsg
	 *            类型 String 错误信息
	 */
	public static void sendPrintErrorMail(String ordsn, String insuredSn,
			String errMsg, Map<String, Object> tMap) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "select a.ProductName , c.timePrem ,a.StartDate , c.PolicyNo ,c.InsureMsg ,c.returnPremiums,a.ProductId "
					+ "from SDInformation a,SDInformationInsured b ,SDInformationRiskType c "
					+ "where a.InformationSn = b.InformationSn and b.RecognizeeSn = c.RecognizeeSn and b.OrderSn = ? and b.InsuredSn = ?";
			map.put("OrderSn", ordsn);
			DataTable dt = new QueryBuilder(sql, ordsn, insuredSn)
					.executeDataTable();
			if (dt.getRowCount() > 0) {
				map.put("ProductName", dt.getString(0, 0));
				map.put("TotalAmount", dt.getString(0, 1));
				map.put("Effective", dt.getString(0, 2).substring(0, 11));
				if (dt.getString(0, 3) != null
						&& !"".equals(dt.getString(0, 3))) {
					map.put("PolicyNo", dt.getString(0, 3));
				} else {
					if (tMap != null && tMap.get("policyNo") != null) {
						map.put("PolicyNo", tMap.get("policyNo").toString());
					} else {
						logger.warn("保单号为空！订单号为：{}", ordsn);
						map.put("PolicyNo", "");
					}
				}
				map.put("ErrMsg", errMsg);
				if (dt.getString(0, 5) != null && !"".equals(dt.getString(0, 5))) {
					map.put("TotalPremium", dt.getString(0, 5));
				} else {
					if (tMap != null && tMap.get("totalPremium") != null) {
						map.put("TotalPremium", tMap.get("totalPremium").toString());
					} else {
						logger.warn("保费为空！订单号为：{}", ordsn);
						map.put("TotalPremium", "");
					}
				}
				map.put("ProductId", dt.getString(0, 6));

			} else {
				logger.warn("未查询到订单及保单相关信息");
			}
			
			String receiver = Config.getValue("PrintDiffMail");
			if (errMsg.contains("网站计算出的保费与保险公司返回的保费不一致")) {
				ActionUtil.sendMail("wj00050", receiver, map);
			} else {
				ActionUtil.sendMail("wj00046", receiver, map);
			}
		} catch (Exception e) {
			logger.error("出现异常:" + "sendPrintErrorMail方法出现异常" + e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param orderID
	 *            类型 String 订单id
	 * @param policyPath
	 *            类型 String 保险公司保单下载路径
	 * @return 类型Boolean
	 * @throws Exception
	 *             类型Exception 电子保单发送异常，电子保单地址： policyPath
	 */
	public boolean sendEpolicy(String orderID, String policyPath, String policyName)
			throws Exception {

		try {
			Map<String, Object> emailMap = findInformationAppntByOrderSn(orderID);
			if(StringUtil.isNotEmpty((String)emailMap.get("mail"))){
				emailMap.put("orderSn", orderID);
				ActionUtil.sendMessage("wj00041", emailMap);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("电子保单发送异常，电子保单地址：" + policyPath);
		}
	}

	/**
	 * 
	 * @param orderID
	 *            类型 String 订单ID
	 * @return 类型 Map或null
	 */
	public Map<String, Object> findInformationAppntByOrderSn(String orderID) {

		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select a.ProductName as productname, b.ApplicantName as applicantname,b.ApplicantMail as mail "
				+ "from SDInformation a , SDInformationAppnt b "
				+ "where a.InformationSn = b.InformationSn and  a.orderSn = ?";
		try {
			DataTable dt = new QueryBuilder(sql, orderID).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				for (int j = 0; j < 1; j++) {
					String productname = dt.getString(j, 0);
					String applicantname = dt.getString(j, 1);
					String mail = dt.getString(j, 2);
					map.put("ProductName", productname);
					map.put("ApplicantName", applicantname);
					map.put("mail", mail);
					// map.put("mail", "shizhijun11509@sinosoft.com.cn");
				}
			}
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * OrdersPrint 电子保单记录表
	 * 
	 * @param ordersn
	 *            类型 String 订单号
	 * @param name
	 *            类型 String 保单名称
	 * @param path
	 *            类型 String 保单下载路径
	 * @throws Exception
	 *             类型 Exception 电子保单记录保存OrdersPrint数据库错误
	 */
	public long saveOrdersPrint(String ordersn, String name, String path,
			String insuredSn, String status) throws Exception {

		long id = 0;
		try {
			String insertSQL = "insert into OrdersPrint values (? , ? , ?, ? , ? , ? , ? , ? , ? , ? )";
			QueryBuilder qbProduct = new QueryBuilder(insertSQL);
			id = NoUtil.getMaxID("OrdersPrintPrintno");
			qbProduct.add(id);
			qbProduct.add(ordersn);
			qbProduct.add("");
			qbProduct.add(name);
			qbProduct.add(path);
			qbProduct.add("SysAuto");
			qbProduct.add(PubFun.getCurrentDate());
			qbProduct.add(PubFun.getCurrentTime());
			qbProduct.add(status);
			qbProduct.add(insuredSn);
			qbProduct.executeNoQuery();

			return id;
		} catch (Exception e) {
			throw new Exception("电子保单记录保存OrdersPrint数据库错误");
		}
	}

	/**
	 * OrdersPrint电子保单记录表
	 * 
	 * @param ordersn
	 *            类型 String 订单号码
	 * @param name
	 *            类型 String 保单名称
	 * @param path
	 *            类型 String 保险公司保单下载路径
	 * @throws Exception
	 *             类型 Exception 电子保单记录更新OrdersPrint数据库错误
	 */
	private void updateOrdersPrint(String insuredSn, String flag) throws Exception {

		try {
			String insertSQL = "update OrdersPrint set flag = ? where InsuredSn = ?";
			QueryBuilder qbProduct = new QueryBuilder(insertSQL);
			qbProduct.add(flag);
			qbProduct.add(insuredSn);
			qbProduct.executeNoQuery();

		} catch (Exception e) {
			throw new Exception("电子保单记录更新OrdersPrint数据库错误." + insuredSn + " - " + flag);
		}
	}

	public static String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	public static String getCurrentMonth() {

		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(new Date());
	}

	public static String getCurrentYear() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}

	public void getGeneratepolicy(List<HashMap<String, String>> insuredSnList,
			List<HashMap<String, String>> pathList, String orderSn,
			String insureTypeCode) {

		/*
		 * 发送电子保单压缩包 String temp = Config.getContextRealPath() + "ElPolicy" +
		 * File.separator + "temp" + File.separator + orderSn; String inputName
		 * = Config.getContextRealPath() + "ElPolicy" + File.separator + "temp"
		 * + File.separator + orderSn + ".zip"; List<File> fileList = new
		 * ArrayList<File>(); boolean flag = false; try { if (!new
		 * File(temp).exists()) { new File(temp).mkdirs(); } ZipOutputStream out
		 * = new ZipOutputStream(new FileOutputStream( inputName));
		 * out.setEncoding("GBK"); if (pathList != null && pathList.size() > 0)
		 * { for (HashMap<String, String> map : pathList) { String path =
		 * map.get("path"); String recognizeeName =
		 * map.get("recognizeeName")+"-"+map.get("insuredSn")+""; if
		 * (StringUtil.isNotEmpty(path) &&
		 * StringUtil.isNotEmpty(recognizeeName)) { File inputFile = new
		 * File(path); String name = inputFile.getName(); int dot =
		 * name.lastIndexOf('.'); String extension = name.substring(dot,
		 * name.length()); String pathNew = temp + File.separator +
		 * recognizeeName + extension; File fileNew = new File(pathNew);
		 * FileUtil.copyFile(inputFile, fileNew); fileList.add(fileNew); }
		 * 
		 * } zip(out, new File(temp), ""); out.flush(); out.close();
		 * 
		 * } try { flag = sendEpolicy(orderSn,inputName); } catch (Exception e)
		 * { e.printStackTrace(); sendPrintErrorMail(orderSn,"","电子保单发送失败！");
		 * flag = false; }finally{ if (insuredSnList != null &&
		 * insuredSnList.size() > 0) { for (HashMap<String, String> map :
		 * insuredSnList) { String insuredSn = map.get("insuredSn"); if (flag) {
		 * updateOrdersPrint(insuredSn,"发送完成"); }else{
		 * updateOrdersPrint(insuredSn,"发送失败"); } } }
		 * 
		 * } } catch (Exception e) { e.printStackTrace(); } finally{
		 * deleteFileAndDirectory(temp); //deleteFile(inputName); }
		 */
		if (pathList != null && pathList.size() > 0) {
			for (HashMap<String, String> map : pathList) {
				boolean flag = false;
				String path = map.get("path");
				String recognizeeName = map.get("recognizeeName") + "-" + map.get("insuredSn") + "";
				if (StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(recognizeeName)) {
					try {
						flag = sendEpolicy(orderSn, path, recognizeeName);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						sendPrintErrorMail(orderSn, "", "电子保单发送失败！", null);
						flag = false;
					} finally {
						String insuredSn = map.get("insuredSn");
						try {
							if (flag) {
								updateOrdersPrint(insuredSn, "发送完成");
							} else {
								updateOrdersPrint(insuredSn, "发送失败");
							}
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
						deleteFileAndDirectory(path);
					}
				}

			}
		}
	}

	public void getGeneratepolicy(HashMap<String, String> pathMap, String orderSn) {

		boolean flag = false;
		String path = pathMap.get("path");
		String recognizeeName = pathMap.get("recognizeeName") + "-" + pathMap.get("insuredSn") + "";
		if (StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(recognizeeName)) {
			try {
				flag = sendEpolicy(orderSn, path, recognizeeName);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				sendPrintErrorMail(orderSn, "", "电子保单发送失败！", null);
				flag = false;
			} finally {
				String insuredSn = pathMap.get("insuredSn");
				try {
					if (flag) {
						updateOrdersPrint(insuredSn, "发送完成");
					} else {
						updateOrdersPrint(insuredSn, "发送失败");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				deleteFileAndDirectory(path);
			}
		}

	}

	private boolean deleteFileAndDirectory(String temp) {

		if (!temp.endsWith(File.separator)) {
			temp = temp + File.separator;
		}
		File dirFile = new File(temp);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {

		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * 将文件进行打包
	 */
	private void zip(ZipOutputStream out, File f, String base) throws Exception {

		if (f.isDirectory()) {
			String curbase = base + File.separator + f.getName();
			File[] fl = f.listFiles();
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], curbase);
			}
		} else {
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base
					+ File.separator + f.getName()));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}
}
