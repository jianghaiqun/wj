package com.sinosoft.bbs.admin;

/**
 * 分数设置
 * @author wst
 * 
 */
import com.sinosoft.bbs.ForumCache;
import com.sinosoft.bbs.ForumUtil;
import com.sinosoft.schema.ZCForumScoreSchema;

public class ForumScore {

	public long InitScore;

	public long PublishTheme;

	public long DeleteTheme;

	public long PublishPost;

	public long DeletePost;

	public long Best;

	public long CancelBest;

	public long Bright;

	public long CancelBright;

	public long TopTheme;

	public long CancelTop;

	public long UpTheme;

	public long DownTheme;

	public long Upload;

	public long Download;

	public long Search;

	public long Vote;

	public ForumScore() {
		init();
	}

	public ForumScore(String SiteID) {
		this(Long.parseLong(SiteID));
	}

	public ForumScore(long SiteID) {
		init(SiteID);
	}

	public void init(long SiteID) {
		if (!ForumUtil.isInitDB(SiteID + "")) {
			return;
		}
		ZCForumScoreSchema score = ForumCache.getScoreBySiteID(String.valueOf(SiteID));
		if (score != null) {
			InitScore = score.getInitScore();
			PublishTheme = score.getPublishTheme();
			DeleteTheme = score.getDeleteTheme();
			PublishPost = score.getPublishPost();
			DeletePost = score.getDeletePost();
			Best = score.getBest();
			CancelBest = score.getCancelBest();
			Bright = score.getBright();
			CancelBright = score.getCancelBright();
			TopTheme = score.getTopTheme();
			CancelTop = score.getCancelTop();
			UpTheme = score.getUpTheme();
			DownTheme = score.getDownTheme();
			Upload = score.getUpload();
			Download = score.getDownload();
			Search = score.getSearch();
			Vote = score.getVote();
		} else {
			clean();
		}
	}

	public void init() {
		init(ForumUtil.getCurrentBBSSiteID());
	}

	public void clean() {
		InitScore = 0;
		PublishTheme = 0;
		DeleteTheme = 0;
		PublishPost = 0;
		DeletePost = 0;
		Best = 0;
		CancelBest = 0;
		Bright = 0;
		CancelBright = 0;
		TopTheme = 0;
		CancelTop = 0;
		UpTheme = 0;
		DownTheme = 0;
		Upload = 0;
		Download = 0;
		Search = 0;
		Vote = 0;
	}
}
