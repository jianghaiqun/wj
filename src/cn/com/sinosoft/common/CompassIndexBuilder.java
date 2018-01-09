package cn.com.sinosoft.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 通过quartz调度定时重建索引或自动随Spring ApplicationContext启动而重建索引
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT13D156FE217D4020D96236A46E1DB023
 * ============================================================================
 */

public class CompassIndexBuilder implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(CompassIndexBuilder.class);

	private int lazyTime = 30;// 索引操作线程延时，单位:秒

	private Thread indexThread = new Thread() {
		@Override
		public void run() {
			try {
				Thread.sleep(lazyTime * 1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	};

	public void afterPropertiesSet() throws Exception {
		indexThread.setDaemon(true);
		indexThread.setName("Compass Indexer");
		indexThread.start();
	}

	public void index() {
	}

	public void setLazyTime(int lazyTime) {
		this.lazyTime = lazyTime;
	}

	 
}