package com.sinosoft.aeonlife;

import com.sinosoft.aeonlife.utils.CreateCSVUtils;
import com.sinosoft.aeonlife.utils.CreateFilePath;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.aeonlife.utils.ZipUtils;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DealRefundFundsService <br/>
 * Function: 百年对接退保资金划拨结果. <br/>
 * date: 2016年9月12日 上午9:23:54 <br/>
 *
 * @author LHY
 * @version
 */
public class AeonLifeDealRefundFundsService {

	private static final Logger logger = LoggerFactory.getLogger(AeonLifeDealRefundFundsService.class);

	public static void main(String[] args) {

		createRefundfunds();
	}

	public static void createRefundfunds() {

		Map<String, List<String>> path_map = CreateFilePath.createFilePath();
		List<String> list = path_map.get(SftpCommon.STR_OWN);
		String path = "";
		// 取得资金划拨路径
		for (String paths : list) {
			if (paths.contains(SftpCommon.STR_REFUNDFUNDS)) {
				path = paths;
				break;
			}
		}
		List<Map<String, String>> exportData = selectRefundfunds();
		
		String filenam = SftpCommon.AEONLIFE + SftpCommon.FILE_SPLIT + SftpCommon.STR_REFUNDFUNDS
				+ SftpCommon.FILE_SPLIT + DateUtil.getCurrentDate("yyyyMMdd") + "_1";
		String fn = path + filenam + ".csv";
		if(!exportData.isEmpty() || exportData != null){
			CreateCSVUtils.createCSVFile(exportData, fn);
			File f = new File(path);
			File zipfile = new File(path + filenam + ".zip");
			ZipUtils.ZipFiles(f.listFiles(), zipfile);
		}

		updateRefundfunds();
	}

	/**
	 * updateRefundfunds:标记所有发送给百年过的文件. <br/>
	 *
	 * @author liuhongyu
	 */
	private static void updateRefundfunds() {
		//标记所有发送给百年过的文件，update
		String sqlUpdate = "UPDATE sdorders o, partnerrefundfundsrecord p, sdinformationrisktype r, partnerrefundapply a, financinginfo f, sdinformation i "
				+"SET p.prop1 = 1 "
				+"where o.paysn = p.pordersn "
				+"and r.orderSn = o.orderSn "
				+"and a.pordersn = o.paysn "
				+"and f.ordersn = o.ordersn "
				+"and i.ordersn = o.ordersn "
				+"and p.Dealresult = '0' ";
		QueryBuilder qbUpdate = new QueryBuilder(sqlUpdate);
		int num = qbUpdate.executeNoQuery();
		logger.info("标记{}行", num);
	}
	
	private static List<Map<String, String>> selectRefundfunds() {

		// 从数据库读取当日的退保信息
//		String sql = "select r.insuranceBankSeriNO,p.SerialNo,f.Prop1, i.productOutCode,f.Total,f.principal,f.Income,f.Fee,f.cancelDate "
//				+ "from partnerrefundapply p, sdinformationrisktype r, sdinformation i, sdorders o, financinginfo f "
//				+ "where o.ordersn = f.ordersn AND p.POrderSn = o.paySn AND r.ordersn = o.ordersn AND o.ordersn = i.ordersn  "
//				+ "AND DATE_FORMAT(p.ApplyDate, '%Y-%m-%d') = DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - 1 DAY), '%Y-%m-%d') "
//				+ "AND r.appstatus in ('2','3','4') ";
		
		//查询未发送给百年过的数据，发送给百年
		String sql = "select r.insuranceBankSeriNO, a.SerialNo, f.Prop1, i.productOutCode,  f.Total, f.Principal, f.Income, f.Fee, f.cancelDate "
				+"from sdorders o, partnerrefundfundsrecord p, sdinformationrisktype r, partnerrefundapply a, financinginfo f, sdinformation i "
				+"where o.paysn = p.pordersn "
				+"and r.orderSn = o.orderSn "
				+"and a.pordersn = o.paysn "
				+"and f.ordersn = o.ordersn "
				+"and i.ordersn = o.ordersn "
				+"and p.prop1 is null "
				+"and p.Dealresult = '0' ";

		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		List<Map<String, String>> exportData = new ArrayList<Map<String, String>>();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String insuranceBankSeriNO = dt.getString(i, "insuranceBankSeriNO");
				String RefundAplaySn = dt.getString(i, "SerialNo");
				String Prop1 = dt.getString(i, "Prop1");
				String productOutCode = dt.getString(i, "productOutCode");
				String Total = dt.getString(i, "Total");
				String Principal = dt.getString(i, "Principal");
				String Income = dt.getString(i, "Income");
				String Fee = dt.getString(i, "Fee");
				String cancelDate = dt.getString(i, "cancelDate");
				String DealResult = "0";
				String DealResultDesc = "成功";
				Map<String, String> row1 = new LinkedHashMap<String, String>();
				row1.put("0", insuranceBankSeriNO);
				row1.put("1", insuranceBankSeriNO);
				row1.put("2", Prop1);
				row1.put("3", RefundAplaySn);
				row1.put("4", productOutCode);
				row1.put("5", Total);
				row1.put("6", Principal);
				row1.put("7", Income);
				row1.put("8", Fee);
				row1.put("9", cancelDate);
				row1.put("10", DealResult);
				row1.put("11", DealResultDesc);
				exportData.add(row1);
			}
			return exportData;
		}
		return exportData;
	}
}
