package com.wangjin.search;

import cn.com.sinosoft.util.DownloadNet;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.wangjin.cms.orders.QueryOrders;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class PdfDownloadTask extends ConfigEanbleTaskManager {
	
	public static final String CODE = "com.wangjin.search.PdfDownloadTask";
	
	public Mapx<String,String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("-1", "保单下载发送");
		return map;
	}
	
	public void execute(long id) {
		if ("-1".equals(id + "")) {
			logger.info("保单下载发送任务开始执行...");
			/*QueryBuilder qb = new QueryBuilder("SELECT a.id,a.electronicCout,a.electronicPath,c.insuranceCode as 'comCode',a.policyNo,a.orderSn,b.insuredSn,b.recognizeeName,(CASE WHEN d.riskType='11' THEN 'jc' ELSE 'ly' END) channel FROM sdinformationrisktype a JOIN sdinformationinsured b ON a.recognizeeSn = b.recognizeeSn JOIN jdpdftrade c ON a.policyno = c.serialNo JOIN sdinformation d ON a.informationSn = d.informationSn WHERE c.insuranceCode IN (?) AND c.MakeDate >= date_sub(curdate(),interval ? day) AND (c.tradeSign <> '000000' or c.tradeSign is null)");
			String pdfDownLoadTaskCompany = Config.interfaceMap.getString("PdfDownLoadTaskCompany");
			qb.add(pdfDownLoadTaskCompany);
			String pdfDownLoadTaskStartDate = Config.interfaceMap.getString("PdfDownLoadTaskStartDate");
			qb.add(pdfDownLoadTaskStartDate);*/
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT a.id,a.electronicCout,a.electronicPath,c.insuranceCode as 'comCode',a.policyNo,a.orderSn,b.insuredSn,b.recognizeeName,(CASE WHEN d.riskType='11' THEN 'jc' ELSE 'ly' END) channel FROM sdinformationrisktype a JOIN sdinformationinsured b ON a.recognizeeSn = b.recognizeeSn JOIN jdpdftrade c ON a.policyno = c.serialNo JOIN sdinformation d ON a.informationSn = d.informationSn WHERE c.insuranceCode IN (");
			String pdfDownLoadTaskCompany = Config.interfaceMap.getString("PdfDownLoadTaskCompany");
			sb.append(pdfDownLoadTaskCompany);
			sb.append(") AND c.MakeDate >= date_sub(curdate(),interval ");
			String pdfDownLoadTaskStartDate = Config.interfaceMap.getString("PdfDownLoadTaskStartDate");
			sb.append(pdfDownLoadTaskStartDate);
			sb.append(" day) AND (c.tradeSign <> '000000' or c.tradeSign is null)");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			DataTable dt = qb.executeDataTable();
			for (DataRow dataRow : dt) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("comCode", dataRow.getString("comCode"));
				params.put("policyNo", dataRow.getString("policyNo"));
				params.put("electronicCout", dataRow.getString("electronicCout"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String dateStr = sdf.format(new java.util.Date());
				params.put("insureDate",dateStr);
				params.put("channel", dataRow.getString("channel"));
				params.put("pdfPath", dataRow.getString("electronicPath"));
				
				QueryOrders qo = new QueryOrders();
				Map<String, String> result = qo.download(params);
				String orderSn = dataRow.getString("orderSn");
				String insuredSn = dataRow.getString("insuredSn");
				String recognizeeName = dataRow.getString("recognizeeName");
				String riskId = dataRow.getString("id");
				if ("N".equals(result.get("Success"))) {
					Map<String,Object> toMap = new HashMap<String, Object>();
					toMap.put("policyNo", params.get("policyNo"));
					DownloadNet.sendPrintErrorMail(orderSn, insuredSn, result.get("Message")+" 未发送给客户电子保单！", toMap);
					logger.error("订单({})的保单下载电子保单失败！{}", orderSn, result.get("Message"));
				} else {
					byte[] info = PubFun.getPdfBytes(result.get("PolicyPath"));
					if (info == null || info.length == 0) {
						DownloadNet.sendPrintErrorMail(orderSn, insuredSn, "订单("+orderSn+")的保单下载电子保单失败！下载电子保单信息为空！ 未发送给客户电子保单！", null);
						logger.error("订单({})的保单下载电子保单失败！下载电子保单信息为空！", orderSn);
					} else {
						HashMap<String,String> map = new HashMap<String,String>();
						map.put("path", result.get("PolicyPath"));
						map.put("insuredSn",insuredSn);
						map.put("recognizeeName", recognizeeName);
						DownloadNet DownloadNet = new DownloadNet();
						DownloadNet.getGeneratepolicy(map, orderSn);
						Transaction ts = new Transaction();
						ts.add(new QueryBuilder("update sdinformationrisktype set electronicPath = ?,modifyDate = now() where id = ? ", result.get("PolicyPath"), riskId));
						ts.commit();
					}
				}
			}
			logger.info("保单下载发送任务执行结束");
		}
	}
	
	public String getCode() {
		return CODE;
	}
	
	public String getName() {
		return "电子保单扫描任务";
	}
	
	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}
	@Override
	public String getID() {
		return "com.wangjin.search.BaiNianLiCaiPdfDownloadTask";
	}
	
}
