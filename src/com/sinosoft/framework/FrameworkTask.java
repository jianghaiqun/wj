package com.sinosoft.framework;

import java.io.File;

import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.framework.utility.FileUtil;

/**
 * 清空Debug模式下的Session缓存
 * 
 */
public class FrameworkTask extends SystemTask {

	public void execute() {
		// 清除缓存
		File dir = new File(Config.getContextRealPath() + "WEB-INF/cache/");
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (f.isFile()) {
				FileUtil.delete(f);
			}
		}
	}

	public String getID() {
		return "com.sinosoft.framework.FrameworkTask";
	}

	public String getName() {
		return "Framework定时任务";
	}

	public String getCronExpression() {
		return "30 10,16 * * *";
	}

}
