package com.sinosoft.cms.resend;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
/**
 * @ClassName: ResendTiming 
 * @Description: TODO(报文重发-定时任务) 
 * @author CongZN 
 * @date 2014-10-9 下午04:03:36 
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class ResendTiming extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.cms.resend.ResendTiming";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("1", "CMS-报文重发");
		return map;
	}

	public void execute(long id) {
		if ("1".equals(id + "")) {
			ResendMain.executResend();
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "CMS-报文重发";
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.sinosoft.cms.resend.ResendTiming";
	}

}
