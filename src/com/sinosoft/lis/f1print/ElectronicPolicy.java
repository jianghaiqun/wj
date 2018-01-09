package com.sinosoft.lis.f1print;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.pub.NoUtil;
import com.tenpay.util.MD5Util;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * 电子保单公共类
 *
 */
public class ElectronicPolicy {
	private static final Logger logger = LoggerFactory.getLogger(ElectronicPolicy.class);

	/**
	 * 
	 * 打印电子保单
	 */
	public String printPolicy(String moduleName ,Map<String,String> mp,String insuredSn,String comCode){
		String policyPath = "";
		String folderPath = Config.getContextRealPath();
		
		String modulepath = folderPath+"template"+File.separator+"electronicPolicy"+File.separator;
		String insureYear = mp.get("insureDate").substring(0, 4);
		String insureMonth = mp.get("insureDate").substring(5, 7);
		
		try {
			String path ;
			String policyName = "";
			String npath = Config.getValue("newPolicyPath");
			if(!StringUtil.isEmpty(npath)){
				folderPath = npath;
			}
			if (StringUtil.isNotEmpty(mp.get("policyPath"))) {
				path = mp.get("policyPath").substring(0, mp.get("policyPath").lastIndexOf(File.separator));
				policyName = mp.get("policyPath").substring(mp.get("policyPath").lastIndexOf(File.separator) + 1);
			} else {
				if(!StringUtil.isEmpty(comCode)){
					path = folderPath+File.separator+"EPolicy"+File.separator+comCode+
					File.separator+insureYear + File.separator + insureMonth;
				}else{
					path = folderPath+File.separator+"EPolicy"+File.separator + insureYear + File.separator + insureMonth;
					logger.error("保险公司编码：{}|路径：{}", comCode, path);
				}
			}
			
			if (StringUtil.isEmpty(policyName)) {
				// 输出PDF格式结果，格式“保单号MD5加密.pdf”
				policyName = MD5Util.MD5Encode(mp.get("policyNo"), "UTF-8") + ".pdf";
			}
			
			mp.put("SUBREPORT_DIR", modulepath);
			
			String modulePolicyPath = modulepath + moduleName;
			
			File policyFile = new File(path);
			if (!policyFile.exists()) {
				policyFile.mkdirs();
			}
			policyPath = path +File.separator+policyName;
			logger.info("电子保单路径 policyPath：{}", policyPath);
			DBConn conn=DBConnPool.getConnection();
			JasperPrint print = JasperFillManager.fillReport(modulePolicyPath, mp, conn);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, policyPath);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.exportReport();
			updateOrdersPrintPath(insuredSn,policyName,policyPath,"电子保单打印成功");
			conn.close();
			
		} catch (Exception e) {
			logger.error("电子保单打印异常："+e.getMessage(), e);
			updateOrdersPrint(insuredSn, "打印电子保单失败");
		}
		return policyPath;
	}
	/**
	 * 
	 * 更新电子保单状态
	 */
	public void updateOrdersPrint(String insuredSn,String flag) {
		try {
			String sql = "update OrdersPrint set flag = ?  where InsuredSn = ?";
			QueryBuilder qbProduct = new QueryBuilder(sql);
			qbProduct.add(flag);
			qbProduct.add(insuredSn);
			qbProduct.executeNoQuery();
		} catch (Exception e) {
			logger.error("电子保单记录状态更新异常："+e.getMessage(), e);
		}
		
	}
	
	/**
	 * 
	 * 电子保单路径更新
	 */
	public void updateOrdersPrintPath(String insuredSn,String policyName, String policyPath,
			String flag) {
		try {
			String sql = "update OrdersPrint set policyName = ? ,policyPath = ? ,flag = ?  where InsuredSn = ?";
			QueryBuilder qbProduct = new QueryBuilder(sql);
			qbProduct.add(policyName);
			qbProduct.add(policyPath);
			qbProduct.add(flag);
			qbProduct.add(insuredSn);
			qbProduct.executeNoQuery();
		} catch (Exception e) {
			logger.error("电子保单记录路径更新异常：" + e.getMessage(), e);
		}
		
	}
	/**
	 * 
	 * 电子保单表添加记录
	 */
	public Long saveOrdersPrint(String orderSn, String insuredSn) {
		long id = 0;
		try {
			String printNo = "";
			String sql = "select printNo from OrdersPrint where ordersn=? and insuredSn=?";
			DataTable dt = new QueryBuilder(sql,orderSn,insuredSn).executeDataTable();
			if(dt.getRowCount()>0){
				printNo = dt.getString(0, 0);
				return Long.parseLong(printNo);
			}else{
				String insertSQL = "insert into OrdersPrint values (? , ? , ?, ? , ? , ? , ? , ? , ? , ? )";
				QueryBuilder qbProduct = new QueryBuilder(insertSQL);
				id = NoUtil.getMaxID("OrdersPrintPrintno");
				qbProduct.add(id);
				qbProduct.add(orderSn);
				qbProduct.add("");
				qbProduct.add("");
				qbProduct.add("");
				qbProduct.add("SysAuto");
				qbProduct.add(PubFun.getCurrentDate());
				qbProduct.add(PubFun.getCurrentTime());
				qbProduct.add("");
				qbProduct.add(insuredSn);
				qbProduct.executeNoQuery();
				return id;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return id;
		}
	}
}
