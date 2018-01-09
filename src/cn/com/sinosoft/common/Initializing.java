package cn.com.sinosoft.common;

import com.sinosoft.framework.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * 初始化
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFTF493F4F9639AB767EED179DBF919AD85
 * ============================================================================
 */

public class Initializing implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(Initializing.class);
	private String keyFile = "key";

	@Resource
	private ServletContext servletContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("http://www.sinosoft.com.cn");
		logger.info("Copyright (c) 2006 sinosoft.com.cn All Rights Reserved.");
		Config.complateInitTime = System.currentTimeMillis();
		logger.info("启动已完成，5分钟后开始执行定时任务。");
	}

	public String getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

}