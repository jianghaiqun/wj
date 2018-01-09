package com.sinosoft.lis.f1print;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.tenpay.util.MD5Util;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ElectronicPolicyPrint {
	private static final Logger logger = LoggerFactory.getLogger(ElectronicPolicyPrint.class);

	private GlobalInput mGlobalInput = new GlobalInput();// 登录用户信息

	private CErrors mErrors = new CErrors();// 返回打印错误信息

	private HashMap mResult = new HashMap();// 返回保单结果

	private String tPolicyName = "";// 保单名称

	private String tFolderInPath = "";// 模版存放路径

	private String tFolderOutPath = "";// 结果输出路径

	private String tOrderSN = "";// 订单号

	private String tOrderId = "";// 订单号

	private String tInsurancePeriod = "";// 汉语保险期间

	private String tEInsurancePeriod = "";// 保险期间
	private String insuredSn = "";//被保人流水号
	private String policyNo = "";//保单号
	private String validateCode = "";//保单验真码
	private String insureDate = "";// 承包时间
	private String policyPath = "";
	public ElectronicPolicyPrint() {
	}

	private boolean getInputData(VData cInputData) throws Exception {
		/** 保存从外部传来的管理机构的信息，作为查询条件 */
		try {
			mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0));
			tOrderSN = (String) cInputData.get(1);
			insuredSn = (String) cInputData.get(2);
			policyNo = (String) cInputData.get(3);
			validateCode = (String) cInputData.get(4);
			policyPath = (String) cInputData.get(5);
			insureDate = (String) cInputData.get(6);
			if (mGlobalInput == null) {
				buildError("getInputData", "没有得到登录用户的信息！");
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new Exception("getInputData类查询用户信息异常" + e.getMessage());
		}
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			/** 得到从外部传来的数据，并备份到本类中 */
			if (!getInputData(cInputData)) {
				return false;
			}

			// 准备所有要打印的数据
			if (!queryData()) {
				return false;
			}
		} catch (Exception e) {
			buildError("queryData", "Error:订单号为：" + tOrderSN
					+ "的电子保单提取出现异常，不能下载！");
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public boolean queryData() throws Exception {
		SimpleDateFormat dfe = new SimpleDateFormat("yyyy-MM-dd");
		String tCurrentDate = "";
		String tCurrentTime = "";
		String outFileName = "";
		String outFilePath = "";
		String insureYear = insureDate.substring(0, 4);
		String insureMonth = insureDate.substring(5, 7);
		try {
			tCurrentDate = PubFun.getCurrentDate();
			tCurrentTime = PubFun.getCurrentTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String tody = df.format(new Date());
			// 设置路径
			File path = new File(ElectronicPolicyPrint.class.getResource("/")
					.getFile());
			String folderPath = path.getParentFile().getParentFile().toString();
			// 模版读取路径
			tFolderInPath = folderPath + "/template/electronicPolicy/";
			
			String npath = Config.getValue("newPolicyPath");
			if(!StringUtil.isEmpty(npath)){
				folderPath = npath;
			}
			// 保单路径不为空的情况
			if (StringUtil.isNotEmpty(policyPath)) {
				tFolderOutPath = policyPath.substring(0, policyPath.lastIndexOf(File.separator) + 1);
				outFileName = policyPath.substring(policyPath.lastIndexOf(File.separator) + 1);
			} else {
				// 结果输出路径
				tFolderOutPath = folderPath +File.separator+"EPolicy"+
				File.separator+"2007"+File.separator+insureYear + File.separator 
				+ insureMonth + File.separator;
			}
			
			if (StringUtil.isEmpty(outFileName)) {
				// 输出PDF格式结果，格式“保单号MD5加密.pdf”
				outFileName = MD5Util.MD5Encode(policyNo, "UTF-8") + ".pdf";
			}
			
			// 路径是否存在，不存在则创建路径
			File folderOut = new File(tFolderOutPath);
			if (!folderOut.exists()) {
				folderOut.mkdirs();
			}
			File folderIn = new File(tFolderInPath);
			if (!folderIn.exists()) {
				folderIn.mkdirs();
			}

			// 设置模版名字
			String sql = "select b.value,b.prop1 from SDInformation a,zdconfig b where a.orderSn='"
					+ tOrderSN + "' and b.type=a.productId";
			DataTable dtModule = new QueryBuilder(sql).executeDataTable();
			//境外险标记 境外1境内0
			String jwFlag="";
			if(dtModule.getRowCount()==1){
				tPolicyName = dtModule.getString(0, 0);
				jwFlag = dtModule.getString(0, 1);
			}
			// 读取电子保单模版
			String fileName = tFolderInPath + tPolicyName + ".jasper";
			
			outFilePath = tFolderOutPath + outFileName;
			logger.info("电子保单路径 outFilePath：{}", outFilePath);
			// 存储输出结果
			HashMap hm = new HashMap();
			hm.put("SUBREPORT_DIR", tFolderInPath);// 子报表路径

			// 业务逻辑，获取结果
			sql = "select  b.ApplicantName,'中国平安财产保险股份有限公司上海分公司',c.RecognizeeName," +
					"(select e.codeName from dictionary e where e.insuranceCode = a.InsuranceCompany and e.codetype='Relationship' and c.RecognizeeAppntRelation = e.codeValue and (e.productId in (d.riskCode,'') or e.productId is null) limit 1)," +
					"c.RecognizeeIdentityId,'法定',c.DestinationCountry,case when a.planName is null then a.primitiveProductTitle else CONCAT(a.primitiveProductTitle,'/',a.planName) end ,d.timePrem,date(a.StartDate),date(a.EndDate)," +
					"DATE_FORMAT(c.RecognizeeBirthday,'%Y/%m/%d'),d.PolicyNo,'业务员代码','',c.FlightNo,date(d.ModifyDate)," +
					"d.ValidateCode,case when b.applicantEnName is null then CONCAT(b.applicantFirstName,b.applicantLastName) else b.applicantEnName end," +
					"case when c.recognizeeEnName is null then  CONCAT(c.recognizeeFirstName,c.recognizeeLashName) else c.recognizeeEnName end ," +
					"(select e.codeEnName from dictionary e where e.insuranceCode = a.InsuranceCompany and e.codetype='Relationship' and c.RecognizeeAppntRelation = e.codeValue and (e.productId in (d.riskCode,'') or e.productId is null) limit 1) "+
					"from SDInformation a " +
					"left join SDInformationAppnt b on a.InformationSn = b.InformationSn " +
					"left join SDInformationInsured c on  c.InformationSn = a.InformationSn " +
					"left join SDInformationRiskType d on c.RecognizeeSn = d.RecognizeeSn " +
					"where  c.InsuredSn='"+insuredSn+"'";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			
			if (dt != null && dt.getRowCount() == 1) {
				hm.put("OrderId", tOrderSN);// 订单号，用于取得计划详细信息
				hm.put("Address", dt.getString(0, 1));// 签单分公司
				if(StringUtil.isNotEmpty(jwFlag)&&"1".equalsIgnoreCase(jwFlag)){
					hm.put("Name", dt.getString(0, 0)+ "/"+ getPinYin(dt.getString(0, 18)));// 投保人
					hm.put("Insuron", dt.getString(0, 2) + "/"+ getPinYin(dt.getString(0, 19)));// 被保险人
					hm.put("Relationship", dt.getString(0, 3)+"/"+dt.getString(0, 20));// 与投保人的关系加英文
					
				}else{
					hm.put("Name", dt.getString(0, 0));
					hm.put("Insuron", dt.getString(0, 2));
					hm.put("Relationship", dt.getString(0, 3));// 与投保人的关系
				}
				hm.put("Num", dt.getString(0, 4));// 身份证、护照号码
				hm.put("Beneficiary", dt.getString(0, 5));// 保险受益人
				String sql_ = "select destinationCountryText from SDInformationInsured where orderSn=? and insuredSn = ?";
				DataTable dt1 = new QueryBuilder(sql_,tOrderSN,insuredSn).executeDataTable();
				String dest = "";
				if(dt1.getRowCount()>0){
					dest = dt1.getString(0, 0);
				}else {
					dest =  "申根协议国家" + " SCHENGEN STATES" + " ";
				}

				hm.put("Destination", dest);// 目的地
				hm.put("Plan", dt.getString(0, 7));// 保险计划

				String total = getData(dt.getString(0, 8));
				if ("".equals(total)) {
					buildError("checkData", "Error:保险费合计" + dt.getString(0, 8)
							+ "格式不正确，应为数值！");
					return false;
				}
				hm.put("Total", total);// 保费合计

				if (!getInsurancePeriod(dt.getString(0, 9), dt.getString(0, 10))) {
					buildError("queryData", "Error:保障期间获取失败");
					return false;
				}
				hm.put("InsurancePeriod", tInsurancePeriod);// 保险期间
				hm.put("EInsurancePeriod", tEInsurancePeriod);// 保险期间（英文）
				hm.put("Birthday", dt.getString(0, 11));// 出生日期,格式YYYY/MM/DD
				hm.put("Plicy", policyNo);// 合同号
				hm.put("Code", dt.getString(0, 13));// 业务员代码
				hm.put("Range", dt.getString(0, 14));// 航程
				hm.put("FlightNum", dt.getString(0, 15));// 航班号
				if(StringUtil.isNotEmpty(dt.getString(0, 16))){
					hm.put("IssueDate", dfe.format(dfe.parse(dt.getString(0, 16))));// 签单日期,格式YYYY-MM-DD
				}
				hm.put("CAPTCHA", validateCode);// 验真码

				// 调用打印程序
				try {
					DBConn conn=DBConnPool.getConnection();
					JasperPrint print = JasperFillManager.fillReport(fileName, hm,
							conn);
					
					JRPdfExporter exporter = new JRPdfExporter();
					
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
							outFilePath);
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
					
					exporter.exportReport();
					
					conn.close();
					
					// 正常返回结果路径
					mResult.put("ResultPath", outFilePath);
				} catch (Exception e) {
					throw new Exception("电子保单模板异常" + e.getMessage());
				}
				logger.info("订单号为：{}的电子保单提取成功，等待记录。", tOrderSN);
				saveOrdersPrint(outFileName,outFilePath, tCurrentDate, tCurrentTime, "电子保单打印成功");
				return true;
			}else{
				mResult.put("ResultPath", "");
				buildError("queryData", "Error:订单号为：" + tOrderSN
						+ "的查询保单信息失败！");
				saveOrdersPrint("","", tCurrentDate, tCurrentTime, "电子保单打印失败");
				return false;
			}
		} catch (Exception e) {
			mResult.put("ResultPath", "");
			buildError("queryData", "Error:订单号为：" + tOrderSN
					+ "的电子保单提取出现异常，不能下载！");
			saveOrdersPrint("","", tCurrentDate, tCurrentTime, "电子保单打印失败");
			throw new Exception("getInputData类查询用户信息异常" + e.getMessage());
		}

	}
	
	private static String spiltAdd(String temp) {
		String[] str = temp.split(",");
		String newStr = "";
		for (int i = 0; i < str.length; i++) {
			newStr = newStr + "'" + str[i] + "'" + ",";
		}

		return newStr.substring(0, newStr.length() - 1);
	}

	private boolean saveOrdersPrint(String outFileName,String outFilePath,String tCurrentDate,String tCurrentTime,String describe){
		// 写入打印结果
		/*现在不采用此种发放执行sql，有可能连接池泄露
		 * MMap map = new MMap();
		map.put("insert into OrdersPrint values ('"
				+ NoUtil.getMaxID("OrdersPrintPrintno") 
				+ "','" + tOrderSN + "','" + tOrderId + "','" + outFileName
				+ "','" + outFilePath + "','" + mGlobalInput.Operator
				+ "','" + tCurrentDate + "','" + tCurrentTime + "','"+describe+"','"+insuredSn+"')",
				"INSERT");
		VData tResult = new VData();
		tResult.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tResult, "")) {
			// @@错误处理
			CError.buildErr(this, "PubSubmit提交数据失败");
			return false;
		}*/
		Transaction trans = new Transaction();
		StringBuffer insertSQL = new StringBuffer("insert into OrdersPrint values ('"
				+ NoUtil.getMaxID("OrdersPrintPrintno") 
				+ "','" + tOrderSN + "','" + tOrderId + "','" + outFileName
				+ "','" + outFilePath + "','" + mGlobalInput.Operator
				+ "','" + tCurrentDate + "','" + tCurrentTime + "','"+describe+"','"+insuredSn+"')");
		QueryBuilder qb = new QueryBuilder(insertSQL.toString());
		trans.add(qb);
		if(!trans.commit()){
			logger.error("生成电子保单记录失败（{}）", tOrderSN);
			return false;
		}
		return true;
	}
	private boolean getInsurancePeriod(String tInsuranceStartDate,
			String tInsuranceEndDate) {
		SimpleDateFormat dfe = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat dfd = new SimpleDateFormat("yyyy/MM/dd");
		FDate fDate = new FDate();
		Date date = fDate.getDate(tInsuranceStartDate);
		if (date == null) {
			buildError("checkData", "Error:保单起期" + tInsuranceStartDate
					+ "格式有误，应为YYYY-MM-DD");
			return false;
		} else {
			tInsurancePeriod = "自" + dfe.format(date) + "零时（0：00）起至";
			tEInsurancePeriod = "Form " + dfd.format(date) + "（0：00） To ";
		}
		date = fDate.getDate(tInsuranceEndDate);
		if (date == null) {
			buildError("checkData", "Error:保单止期" + tInsuranceEndDate
					+ "格式有误，应为YYYY-MM-DD");
			return false;
		} else {
			tInsurancePeriod += dfe.format(date) + "二十四时（24：00）止（北京时间）";
			tEInsurancePeriod += dfd.format(date) + "（24：00）(BeiJing Time)";
		}
		return true;
	}

	private String getPinYin(String tNamePinYin) {
		if (tNamePinYin == null || "".equals(tNamePinYin)) {
			return tNamePinYin = "---";
		} else {
			return tNamePinYin;
		}
	}

	private String getData(String tTotal) {
		DecimalFormat df = new DecimalFormat("0.00");
		try {
			return df.format(Double.parseDouble(tTotal));// 格式化小数点后两位
		} catch (Exception e) {
			return "";
		}
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ElectronicPolicyPrint";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public HashMap getResult() {
		return mResult;
	}

	public CErrors getError() {
		return mErrors;
	}

	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		// 传入用户信息
		GlobalInput tG = new GlobalInput();
		tG.Operator = "SysAuto";

		// 将用户信息与保单信息放入容器
		VData cInputData = new VData();
		cInputData.addElement(tG);
		cInputData.addElement("2013000000013058");// OrderSN
		cInputData.addElement("2013000000013058-1");
		cInputData.addElement("dfgdfgdfgdfg");
		cInputData.addElement("dfgdfgdfgdfg");
		ElectronicPolicyPrint tElectronicPolicyPrint = new ElectronicPolicyPrint();
		// 调用保单打印程序进行打印
		if (tElectronicPolicyPrint.submitData(cInputData, "")) {
			logger.info("保单打印成功！保单存储路径为："
					+ tElectronicPolicyPrint.getResult().get("ResultPath"));
		} else {
			LogUtil.info("保单打印失败！原因是："
					+ tElectronicPolicyPrint.getError().getFirstError());
		}
	}*/
}
