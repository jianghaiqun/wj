/**
 * Project Name:wj
 * File Name:CreateRefundApply.java
 * Package Name:com.sinosoft.aeonlife.utils
 * Date:2016年6月17日下午5:18:18
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.aeonlife.utils.CreateCSVUtils;
import com.sinosoft.aeonlife.utils.CreateFilePath;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.aeonlife.utils.ZipUtils;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

/**
 * ClassName:CreateRefundApply <br/>
 * Function:TODO 创建退保csv文件. <br/>
 * Date:2016年6月17日 下午5:18:18 <br/>
 *
 * @author:chouweigao 
 */
public class CreateRefundApply {
	/**
	 * 
	 * createRefundApply:(构建退保申请路径). <br/>
	 *
	 * @author chouweigao
	 */
	public static void createRefundApply(){
		
		ZDCodeSchema code = new ZDCodeSchema();
		ZDCodeSet codeSet = code.query(new QueryBuilder("where CodeType = 'SFTP_PATHS' and ParentCode = 'SFTP_PATHS'"));
		
		for (int i = 0; i < codeSet.size(); i++) {
			CreateFilePath filepath = new CreateFilePath();
			String path = "";
			code = codeSet.get(i);
			// 路径：如/alidata/sftpFile/sftp_own
			path = code.getCodeValue() + filepath.cfp(SftpCommon.DOWNLOAD, SftpCommon.PATH_REFUNDAPPLY);
			// 文件名前缀
			String prefix = code.getMemo();

			List<Map<String, String>> exportData = selectRefundApply(code.getCodeName());
			String filenam = prefix + SftpCommon.FILE_SPLIT + SftpCommon.STR_REFUNDAPPLY
					+ SftpCommon.FILE_SPLIT + DateUtil.getCurrentDate("yyyyMMdd") + "_1";
			String fn = path + filenam + ".csv";
			CreateCSVUtils.createCSVFile(exportData, fn);

			File f = new File(path);
			File zipfile = new File(path + filenam + ".zip");
			ZipUtils.ZipFiles(f.listFiles(), zipfile);
		}
	}
	
	/**
	 * selectRefundApply:根据不同保险公司提交退保请求数据. <br/>
	 *
	 * @author wwy
	 * @param company
	 * @return
	 */
	private static List<Map<String, String>> selectRefundApply(String company){
		//String[] str ={"2013000011120949_1","2013000011120949_1","130702","2","201606017 02:02:01"};
		String sql = "SELECT fsk.insuranceBankSeriNO as ordersn ,fsk.riskcode,ff.cancelDate, IF(DATE_SUB(DATE_FORMAT(ff.cancelDate, '%Y-%m-%d'), INTERVAL 10 DAY) > DATE_FORMAT(fsk.insureDate, '%Y-%m-%d'), '2', '1') as refundFlag "
				+ " FROM financinginfo ff,sdinformationRiskType  fsk WHERE ff.ordersn = fsk.ordersn AND ff.InsStatus = '2' AND DATE_SUB(CURDATE(),INTERVAL 1 DAY) = DATE_FORMAT(ff.cancelDate,'%Y-%m-%d') AND LEFT(fsk.riskcode, 4) = ? ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(company);
		DataTable dt = qb.executeDataTable();
		List<Map<String, String>> exportData = new ArrayList<Map<String, String>>();
		if (dt.getRowCount()>0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String osn  = dt.getString(i, "ordersn");
				String riskcode = dt.getString(i, "riskcode");
				String date = dt.getString(i, "cancelDate");
				String[] day = date.split(" ");
				day[0]= day[0].replace("-", "");
				String applyDate = day[0]+" "+day[1];
				String refundFlag = dt.getString(i, "refundFlag");
				Map<String, String> row1 =  new LinkedHashMap<String, String>();
				row1.put("0", osn);
				row1.put("1", osn);
				row1.put("2", riskcode);
				row1.put("3", refundFlag);
				row1.put("4", applyDate);
				exportData.add(row1);
			}
			return exportData;
		}
		return exportData;
	}
}

