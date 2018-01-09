package com.sinosoft.make;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProSchema {
	private static final Logger logger = LoggerFactory.getLogger(ProSchema.class);
	/**
	 * 解析传入文件的pdm,并生成schema.java和set.java文件
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String PdmPath = "D:\\work\\svn\\Documents\\PDM\\schema生成模板.pdm";
		
		
		PdmToSchema pp = new PdmToSchema();
		Table[] tab = pp.parsePDM_VO(PdmPath); // pam 路径
		// 传入待解析pdm的名称,PDM12版本
		// pp.initTable(tab); //把表字段System.out出来
		pp.compareTab_DB(tab); //
		// 把表字段System.out出来,并和数据库进行比较,将不同点System.out出来.
		pp.makeSchemaAndSet(tab);

		logger.info("生成schema完成!");
	}
}
