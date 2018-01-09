package com.wangjin.search;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.messages.LongTimeTask;

public class CreatKeyword extends Ajax {
	public void creatKeyword() {
		final String keyWord = $V("Keyword_Add");
		final String level = $V("level_Add");
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				CreatKeyword.doStickyKeyTop(this, keyWord, level);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始添加关键词......");
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
		logger.info("添加关键词完成.");
	}
}
