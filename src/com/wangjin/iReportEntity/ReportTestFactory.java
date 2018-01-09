package com.wangjin.iReportEntity;

import java.util.Vector;

public class ReportTestFactory {
	
	public static java.util.Collection generateCollection() {
		
		java.util.Vector collection = new java.util.Vector();
		
		long serialNo = 1;
		
		for(int i=0;i<100;i++){
			
			ReportEntity pe = new ReportEntity();
			
			pe.setSerialNo(serialNo);
			pe.setOne("--");
			pe.setTwo("0.95");
			pe.setThree("0.33");
			pe.setFour("-0.89");
			pe.setFive("-0.55");
			pe.setSix("0.25");
			pe.setSeven("-0.60");
			pe.setEight("-1.00");
			pe.setNight("0%");
			pe.setTen("0%");
			pe.setEleven("0%");
			pe.setTwele("0%");
			pe.setProject("测试项目"+serialNo);
			pe.setTotal("641574.85");
			collection.add(pe);
			
			serialNo = serialNo + 1;
		}
		
		return collection;
		
	}
	
}
