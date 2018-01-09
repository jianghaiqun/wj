package com.sinosoft.datachannel;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.jdt.ServiceClient;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

public class MaYiPolicyUploadTask extends ConfigEanbleTaskManager{
	 public static final String CODE = "com.sinosoft.datachannel.MaYiPolicyUploadTask";
	
	public boolean isRunning(long id) {
		return false;
	}

	@Override
	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
        map.put("1", "蚂蚁电子保单上传");
		return map;
	}
	
	public static void execute(long id) {
		if ("1".equals(id + "")) {
			ServiceClient.execute(new String[2], null, null, "upLoadFileToMaYi");
		}
	}
	
	
	@Override
	public String getCode() {
		return CODE;
	}
	
	@Override
	public String getName() {
		return "蚂蚁平台定时任务";
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
        return "com.sinosoft.datachannel.MaYiPolicyUploadTask";
    }

}
