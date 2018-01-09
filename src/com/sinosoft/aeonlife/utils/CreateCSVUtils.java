/**
 * Project Name:FrontTrade
 * File Name:CSVUtils.java
 * Package Name:com.finance.util
 * Date:2016年5月31日下午6:32:57
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife.utils;

/**
 * ClassName:CSVUtils <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2016年5月31日 下午6:32:57 <br/>
 *
 * @author:chouweigao 
 */

import com.sinosoft.framework.utility.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CreateCSVUtils {
	private static final Logger logger = LoggerFactory.getLogger(CreateCSVUtils.class);

	public static File createCSVFile(List exportData,String outPutPath) {

		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			csvFile = new File(outPutPath);
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// GB2312使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Map<String, String> map  =(Map<String, String>) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
					Entry propertyEntry = (Entry) propertyIterator.next();
					//System.out.println( propertyEntry.getKey().toString());
					csvFileOutputStream.write("\""
							+ propertyEntry.getValue().toString() + "\"");
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				csvFileOutputStream.close();
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
			}
		}
		return csvFile;
	}

	public static void main(String[] args) throws Exception {
		//投保申请
//		String fn = SftpCommon.STR_POLICYREQ;
//		String[] str   = {"BNRS-251106151238143000","130702","1000010035352769900000015829","韦静","370628198201176539","13585824936","vieking0@sina.com","辽宁省大连市沙河口区诺德大厦2楼10001","1000","1","20160614 09:14:31","20160614 08:14:31","I10365"};
//		String[] str1 = {"BNRS-251106151238143001","130702","1000010035352769900000015830","韦静","370628198201176539","13585824937","vieking1@sina.com","辽宁省大连市沙河口区诺德大厦2楼10002","2000","1","20160614 09:14:32","20160614 09:14:32","I10366"};
//		String[] str2 = {"BNRS-251106151238143002","130702","1000010035352769900000015831","韦静","370628198201176539","13585824938","vieking2@sina.com","辽宁省大连市沙河口区诺德大厦2楼10003","3000","1","20160614 09:14:33","20160614 09:14:33","I10367"};
		
//		//投保结果
//		String fn = SftpCommon.STR_POLICYRESULT;
//		String[] str   = {"BNRS-251106151238143000","7880101900300319","1","1","1000.00","1000.00","0.00","20160614","20160624","","http://218.24.167.27:9000/personal/ecdownload/urlcode.jsp?ContNo=7880101900300319&ECCode=6E18C66A72CA6514"};
//		String[] str1 = {"BNRS-251106151238143001","7880101900300320","1","1","2000.00","2000.00","0.00","20160614","20160624","","http://218.24.167.27:9000/personal/ecdownload/urlcode.jsp?ContNo=7880101900300320&ECCode=396CEDC2ED98509A"};
//		String[] str2 = {"BNRS-251106151238143002","7880101900300321","1","1","3000.00","3000.00","0.00","20160614","20160624","","http://218.24.167.27:9000/personal/ecdownload/urlcode.jsp?ContNo=7880101900300321&ECCode=D2D8C73B76F440D3"};
		
		//退保结果
//		String fn = SftpCommon.STR_REFUNDRESULT;
//		String[] str = {"BNRS-251106151238143000","BNRS-2-160522094924280166","BNRS-2-160522094924280166","130702","1","2","1000.00","1000.00","0.00","0.00","20160614 14:57:44","成功"};
		
		//资产同步
//		String fn = SftpCommon.STR_POLICYVALUE;
//		String[] str = {"TEST-251106151238143002","7880101900300312","1","2","2000.99","2000.0","0.99","20160616",""};
		
		//退保
		String fn = SftpCommon.STR_REFUNDAPPLY;
		String[] str ={"2013000011120949_1","2013000011120949_1","130702","2","201606017 02:02:01"};
		
//		String fn = SftpCommon.STR_REFUNDFUNDS;
//		String[] str = {"BNRS-151106151238143076","BNRS-2-160523103611285075","BNRS-2-160523103611285075","BNRS-R-160524131107670859","1307","110043.09","110000.00","228.52","185.43","20160611 12:00:10","0","交易成功"};
		List exportData = new ArrayList<Map>(); 
		Map row1 =  new LinkedHashMap<String, String>();
		for (int i = 0; i < str.length; i++) {
			row1.put(i, str[i]);
		}
//		exportData.add(row1);
//		row1 = new LinkedHashMap<String, String>();
//		for (int i = 0; i < str1.length; i++) {
//			row1.put(i, str1[i]);
//		}
//		exportData.add(row1);
//		row1 = new LinkedHashMap<String, String>();
//		for (int i = 0; i < str2.length; i++) {
//			row1.put(i, str2[i]);
//		}
		exportData.add(row1);
		
		String path = "D:/test/temp/aeonlife_"+fn+"_"+DateUtil.getCurrentDate("yyyyMMdd")+"_1";
		CreateCSVUtils.createCSVFile(exportData, path+".csv");
		
		String path1 = "D:/test/temp/";
		String filename = "aeonlife_"+fn+"_"+DateUtil.getCurrentDate("yyyyMMdd")+"_1";
		File f = new File(path1);
		File zipfile = new File(path1+filename+".zip");
		ZipUtils.ZipFiles(f.listFiles(), zipfile);
	}
}
