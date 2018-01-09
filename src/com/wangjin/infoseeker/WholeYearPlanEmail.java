package com.wangjin.infoseeker;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class WholeYearPlanEmail extends ConfigEanbleTaskManager {
	public static final String CODE = "com.wangjin.infoseeker.WholeYearPlanEmail";

	public boolean isRunning(long id) {
		return false;
	}

	/**
	 * 
	* @Title: deal 
	* @Description: TODO(待过期活动邮件提醒定时任务,处理高倍积分、网站折扣活动；活动结束前12小时邮件提醒) 
	* @return boolean    返回类型 
	* @author cshg
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean deal() {
		
		try{
			String path = Config.getContextRealPath();
			String modulepath = path + "ReportTemplate/";
			File reportFile = new File(modulepath+"/WholeYearPlanReport.jasper");
			String filepath = path + File.separator + "WholePlan" + File.separator +PubFun.getCurrentDate()+".xls";
			String startDate = PubFun.getCurrentDate();
			Map<String,Object> parameters=new HashMap<String,Object>();  
		    parameters.put("startDate", startDate);
			parameters = WholeYearPlan.dealReport(parameters);
			JasperPrint report = null; 
        	FileOutputStream output = null; 
        	report = JasperFillManager.fillReport(reportFile.getPath(),parameters); 
        	JRAbstractExporter exporter = new JExcelApiExporter(); 
        	output = new FileOutputStream(filepath); 
        	exporter.setParameter(JRExporterParameter.JASPER_PRINT, report); 
        	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output); 
        	exporter.exportReport(); 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("StartDate", startDate);
			map.put("FilePath", filepath);
			map.put("FileName", "全年计划报表-"+startDate+".xls");
			String toEmail = Config.getValue("WholePlanToEmail");
			if(StringUtil.isNotEmpty(toEmail)){
				if(!ActionUtil.sendMail("wj00116", toEmail, map)){
					logger.error("保费/手续费计划定时任务,邮件发送失败！");
					return false;
				}
			}
		}catch(Exception e){
			logger.error("保费/手续费计划定时任务,邮件发送异常！" + e.getMessage(), e);
			return false;
		}
		return true;
		
	}

	/**
	 * 
	* @Title: execute 
	* @Description: TODO(定时任务调用主方法) 
	* @return void    返回类型 
	* @author cuishg
	 */
	public void execute(long id) {
		// 邮件发送
		if ("0".equals(id + "")) {
			if (deal()) {
				logger.info("保费/手续费计划定时任务！");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "保费/手续费计划定时任务");
		return map;
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "保费/手续费计划定时任务";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.wangjin.infoseeker.WholeYearPlanEmail";
	}
}
