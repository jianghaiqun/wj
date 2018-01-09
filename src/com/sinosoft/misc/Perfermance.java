package com.sinosoft.misc;

import com.sinosoft.framework.utility.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Perfermance {
	private static final Logger logger = LoggerFactory.getLogger(Perfermance.class);
	public static void main(String[] args) {
		final int threadCount = 4;
		for (int k = 0; k < threadCount; k++) {
			new Thread() {
				public void run() {
					long start = System.currentTimeMillis();
					long last = System.currentTimeMillis();
					for (int i = 0; i < 10000 / threadCount; i++) {
						if (i % 100 == 0) {
							logger.info(i + ",百次耗时:" + (System.currentTimeMillis() - last));
							last = System.currentTimeMillis();
						}
						try {
							// ServletUtil
							// .getURLContent("http://localhost:8080/ZCMS/Site/CatalogBasic.jsp?CatalogID=9937&InnerCode=002800");
							ServletUtil.getURLContent("http://localhost:8080/ZCMS/Platform/Code.jsp");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					logger.info(String.valueOf(System.currentTimeMillis() - start));
				}
			}.start();
		}
	}
}
