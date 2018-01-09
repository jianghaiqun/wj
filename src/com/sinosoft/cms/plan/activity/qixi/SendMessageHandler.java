package com.sinosoft.cms.plan.activity.qixi;

/**
 * Created by dongsheng on 2017/7/12.
 */
public interface SendMessageHandler {

	boolean sendMessage();

	void addMsg(String mobile, String msgData);

}
