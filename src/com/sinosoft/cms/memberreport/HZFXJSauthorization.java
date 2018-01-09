package com.sinosoft.cms.memberreport;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ZipUtil;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.sinosoft.framework.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 统计计划管理..
 * Created by dongsheng on 2017/3/21.
 */
public class HZFXJSauthorization extends Page {

    public static Mapx init(Mapx params) {
        Date now = new Date();
        params.put("startDate", DateFormatUtils.ISO_DATE_FORMAT.format(now));
        params.put("endDate", DateFormatUtils.ISO_DATE_FORMAT.format(now));
        return params;
    }


    public void dg1DataBind(DataGridAction dga) {
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate");
        String IDNember = dga.getParam("IDNember");
        String bankCardNo = dga.getParam("bankCardNo");
        QueryBuilder qb = new QueryBuilder();
		String sql = " select o.ordersn,LEFT(o.createdate,10) as createdate,a.applicantidentityTypeName,a.applicantIdentityId,b.bankusername,b.bankname,b.bankno "
				+ " from sdorders o,sdinformation i,sdinformationappnt a ,directpaybankinfo b   "
				+ " where o.ordersn = i.ordersn "
				+ " and a.informationsn = i.informationsn "
				+ " and i.productid = '225801001' "
				+ " and o.ordersn = b.ordersn  and o.orderstatus>=7 and   o.orderstatus!=8 "
				+ " and DATE(o.createdate) <= '" + endDate + "' "
				+ " and DATE(o.createdate) >= '" + startDate + "' ";
        if (StringUtils.isNotEmpty(IDNember)) {
        	sql = sql + "and a.applicantIdentityId = '" + IDNember + "'";
		}
        if (StringUtils.isNotEmpty(bankCardNo)) {
        	sql = sql + "and b.bankno = '" + bankCardNo + "'";
		}
        qb.setSQL(sql);
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        dga.bindData(dt);

    } 
    
    public void download (){
    	try {
			String filePath = Config.getContextRealPath() + "model/authorization.jasper";
			
    		DataTable dataTable = (DataTable) Request.get("tableData");
    		List<String> fileNames = new ArrayList<String>();
    		String path = null;
    		String date = DateUtil.getCurrentDate().replace("-", "");
    		for (int i = 0; i < dataTable.getRowCount(); i++) {
				String ordersn = dataTable.getString(i, "ordersn");
				String bankusername = dataTable.getString(i, "bankusername");
				String bankno = dataTable.getString(i, "bankno");
				String bankname = dataTable.getString(i, "bankname");
				String applicantidentityTypeName = dataTable.getString(i, "applicantidentityTypeName");
				String applicantIdentityId = dataTable.getString(i, "applicantIdentityId");
				String createdate = dataTable.getString(i, "createdate");
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("bankusername", bankusername);
				map.put("bankno", bankno);
				map.put("bankname", bankname);
				map.put("applicantidentityTypeName", applicantidentityTypeName);
				map.put("createdate", createdate);
				map.put("applicantIdentityId", applicantIdentityId);
				
				path = Config.getValue("newPolicyPath") +"/EFile/authorization/" + date + "/" + ordersn + ".pdf";
				File file = new File(path);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				fileNames.add(path);
				JasperPrint print = JasperFillManager.fillReport(filePath, map);
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.exportReport();
				
			}
    		String zipFilePath = null;
    		String downloadpath = null;
    		if (fileNames.size() > 0) {
				String[] srcFiles = new String[fileNames.size()];
				fileNames.toArray(srcFiles);

				String datetime = DateUtil.getCurrentDateTime().replace(" ", "").replace(":", "");
				String name = datetime + ".zip";
				zipFilePath = Config.getValue("newPolicyPath") + "/EFile/authorization/" + date + "/" + name;
				downloadpath = Config.getValue("ContextPath") + "/EFile/authorization/" + date + "/" + name;
				
				
				try {
					ZipUtil.zipBatch(srcFiles, zipFilePath);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			Response.setLogInfo(1, "下载成功");
			Response.setMessage(downloadpath);
		} catch (Exception e) {
			e.printStackTrace();
			Response.setLogInfo(0, "批量下载失败！");
		}
    }   
}
