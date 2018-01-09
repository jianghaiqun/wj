/**
 * Project Name:wj
 * File Name:InsureApplyDocumentService.java
 * Package Name:com.sinosoft.asyninter
 * Date:2016年9月8日下午3:14:07
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.asyninter;

import cn.com.sinosoft.service.impl.BaseServiceImpl;
import com.sinosoft.aeonlife.utils.CreateCSVUtils;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.aeonlife.utils.ZipUtils;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.PartnerInfoSchema;
import com.sinosoft.schema.PartnerInfoSet;
import com.sinosoft.schema.SDOrdersSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:RefundApplyResultService <br/>
 * Function:处理合作方退保结果文件. <br/>
 * Date:2016年9月8日 下午3:14:07 <br/>
 *
 * @author:liuhognyu
 */
public class RefundApplyResultService extends BaseServiceImpl<SDOrdersSet, String> {
	private static final Logger logger = LoggerFactory.getLogger(RefundApplyResultService.class);
	private final static String CLASS_NAME = RefundApplyResultService.class.getName();

	public void execute() {

		RefundApplyResultService service = new RefundApplyResultService();
		try {
			service.dealPartnerRefundResult();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
	public static void main(String[] args) {

		RefundApplyResultService service = new RefundApplyResultService();
		try {
			service.dealPartnerRefundResult();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * dealPartnerRefundResult:从合作方获取投保申请文件. <br/>
	 *
	 * @author liuhongyu
	 * @throws IOException
	 */
	private void dealPartnerRefundResult() throws IOException {

		PartnerInfoSet set = getPartnerInfoSet();
		if (set == null) {
			logger.warn("数据错误,没找到合作方!");
			return;
		}
		for (int i = 0; i < set.size(); i++) {
			PartnerInfoSchema info = set.get(i);
			if (StringUtil.isNotEmpty(info.getFtpPath())) {
				String path = info.getFtpPath() + cfp(SftpCommon.UPLOAD, SftpCommon.PATH_REFUNDRESULT);

				List<Map<String, String>> exportData = selectRefundApplyResult(info);
//				if (exportData != null && exportData.size() > 0) {
					String filenam = info.getpartnerId() + SftpCommon.FILE_SPLIT + SftpCommon.STR_REFUNDRESULT
							+ SftpCommon.FILE_SPLIT + DateUtil.getCurrentDate("yyyyMMdd") + "_1";
					String fn = path + filenam + ".csv";
					CreateCSVUtils.createCSVFile(exportData, fn);

					File f = new File(path);
					File zipfile = new File(path + filenam + ".zip");
					ZipUtils.ZipFiles(f.listFiles(), zipfile); 
//				}
			}
			else{
				logger.warn("ftp路径空, partnerId:{}", info.getpartnerId());
			}
		}
	}

	/**
	 * getPartnerInfoSet:获取合作方. <br/>
	 * 
	 * @author liuhongyu
	 * @param loadType
	 * @param dateStr
	 * @param strType
	 * @return
	 */
	protected PartnerInfoSet getPartnerInfoSet() {

		String sql = "where type='asyn'";
		QueryBuilder queryBuilder = new QueryBuilder(sql);
		PartnerInfoSchema parterInfo = new PartnerInfoSchema();
		PartnerInfoSet set = parterInfo.query(queryBuilder);
		return set;
	}

	/**
	 * createPartnerPolicyReq:创建合作方退保申请. <br/>
	 *
	 * @author liuhongyu
	 * @param row
	 * @param partnerInfo
	 * @return
	 */
	/**
	 * selectRefundApplyResult:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author liuhongyu
	 * @param info
	 * @return
	 */
	private List<Map<String, String>> selectRefundApplyResult(PartnerInfoSchema info) {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
//		String sql = "SELECT p.POrderSn,p.SerialNo, f.Prop1, r.riskCode, IF(p.CancelResult = '1', IFNULL(f.CancelResult,'0'), '0') AS CancelResult, "
//				+"f.returnType, f.Total, f.Principal, f.Income, f.Fee, r.cancelDate, IF(p.CancelResult = '0', p.CancelFailReason, f.CancelMsg) AS CancelMsg "
//				+"FROM partnerrefundapply p "
//				+"LEFT JOIN sdorders o on p.POrderSn = o.paySn "
//				+"LEFT JOIN sdinformationrisktype r on r.ordersn = o.orderSn "
//				+"LEFT JOIN financinginfo f on f.OrderSn = o.ordersn "
//				+"where DATE_FORMAT(p.createdate, '%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d') "
//				+"AND p.Prop1 = ? ";

		String sql = "select p.POrderSn,p.SerialNo, f.Prop1, r.riskCode, "
				+"if(p.CancelResult = '1', IFNULL(f.CancelResult,'0'), '0') AS CancelResult, f.returnType, "
				+"if(f.InsStatus = '3' or f.InsStatus = '4',f.Total,'')as Total, if(f.InsStatus = '3' or f.InsStatus = '4',f.Principal,'')as Principal, "
				+"if(f.InsStatus = '3' or f.InsStatus = '4',f.Income,'')as Income, if(f.InsStatus = '3' or f.InsStatus = '4',f.Fee,'')as Fee, "
				+"r.cancelDate, if(p.CancelResult = '0', p.CancelFailReason, if(f.InsStatus = '3' or f.InsStatus = '4',f.CancelMsg,'请联系管理员')) AS CancelMsg "
				+"FROM partnerrefundapply p "
				+"LEFT JOIN sdorders o on p.POrderSn = o.paySn "
				+"LEFT JOIN sdinformationrisktype r on r.ordersn = o.orderSn "
				+"LEFT JOIN financinginfo f on f.OrderSn = o.ordersn "
				+"where DATE_FORMAT(p.createdate, '%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d') "
				+"AND p.Prop1 = ? ";
		
		
		QueryBuilder qb = new QueryBuilder(sql, info.getchannelSn());

		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) { 
			Map<String, String> map = null;
			for (int i = 0; i < dt.getRowCount(); i++) {
				map = new LinkedHashMap<String, String>();

				map.put("0", dt.getString(i, "POrderSn"));
				map.put("1", dt.getString(i, "SerialNo"));
				map.put("2", dt.getString(i, "Prop1"));
				map.put("3", dt.getString(i, "riskCode"));
				map.put("4", dt.getString(i, "CancelResult"));
				map.put("5", dt.getString(i, "returnType")); 
				map.put("6", dt.getString(i, "Total"));
				map.put("7", dt.getString(i, "Principal"));
				map.put("8", dt.getString(i, "Income"));
				map.put("9", dt.getString(i, "Fee"));
				map.put("10", dt.getString(i, "cancelDate"));
				map.put("11", dt.getString(i, "CancelMsg"));

				result.add(map);
			}
		}

		return result;
	}

	private static String cfp(String root, String buis) {

		StringBuffer sb = new StringBuffer();
		sb.append(root);
		sb.append(DateUtil.getCurrentDate(SftpCommon.FORMAT_YMD));
		sb.append(buis);
		return sb.toString();
	}
}
