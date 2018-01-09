package com.wangjin.search;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.messages.LongTimeTask;

public class DeleteKeyword extends Ajax {
	public void deleteKeyword() {
		final String keyWord = $V("keyWord");
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				DeleteKeyword.doDeleteKeyword(this, keyWord);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始取消......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	public static void doDeleteKeyword(LongTimeTask lTT, String keyWord) {
		AddKeyword.deletekeyword(keyWord);
		lTT.setPercent(100);
		logger.info("取消审核通过关键词完成.");
	}

}
