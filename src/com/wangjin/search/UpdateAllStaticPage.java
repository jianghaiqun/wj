package com.wangjin.search;

import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.platform.Application;

import java.util.HashMap;
import java.util.List;

public class UpdateAllStaticPage extends Ajax {

	public void doUpdateAllStaticPage() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				UpdateAllStaticPage.doUpdate(this);
			}
		};
		PageGenerator p = new PageGenerator(ltt);
		if (p.staticSiteIndex(Application.getCurrentSiteID())) {
			Deploy d = new Deploy();
			d.addJobs(Application.getCurrentSiteID(), p.getFileList());
		}
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始更新......");
		$S("TaskID", "" + ltt.getTaskID());
	}
	/*
	 * 更新每一期所有的关键词
	 */
	public static void doUpdate(LongTimeTask lTT) {
		List<HashMap<String, String>> maplist = UpdateStaticPage.setKeyWord();//前100个关键词
//		List<HashMap<String, String>> maplist_detail = UpdateStaticPage.setKeyWord_detail();
		UpdateStaticPage.updateIndexPage(maplist);
//		int i = 0;
//		int size = maplist_detail.size();
//		ZCSiteSchema site = SiteUtil.getSchema("221");
//		String locahosturl = site.getProp4();
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		staticDir = staticDir.replaceAll("/+", "/");
//		CreateHTML chtml = new CreateHTML();
//		String header = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\kxb_header_index_new.shtml"));
//		String footer = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\kxb_footer_new.shtml"));
//		String community = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\community.shtml"));
//		for (HashMap<String, String> map : maplist_detail) {
//			UpdateStaticPage.updateKeywordPage(map.get("keyWord"), UpdateStaticPage.getPageID(Integer.valueOf(map.get("WordId"))), locahosturl, header, footer, community);
//			lTT.setPercent(100 * (++i / size));
//		}
		lTT.setPercent(100);
		logger.info("全部关键词静态页面更新完成.");
	}
}
