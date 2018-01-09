package com.wangjin.search;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.messages.LongTimeTask;

public class StickyKeyword extends Ajax {
	public void stickyKeyword() {
		final String keyWord = $V("keyWord");
		final String level = $V("level");
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				StickyKeyword.doStickyKeyTop(this, keyWord, level);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始置顶......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	/*
	 * 置顶关键词
	 */
	public static void doStickyKeyTop(LongTimeTask lTT, String keyWord,
			String level) {
		AddKeyword.stickykeyword(keyWord, level);
		UpdateStaticPage.doUpdate();
		lTT.setPercent(100);
		logger.info("置顶关键词完成.");
	}
}
