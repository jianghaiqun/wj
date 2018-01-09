package com.sinosoft.platform;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDUserLogSchema;
import com.sinosoft.schema.ZDUserLogSet;

import java.util.Date;

public class UserLog extends Page {

	// 登录状态
	public static final String LOGIN = "Login";
	public static final String LOGOUT = "Logout";
	public static final String LOG = "Log";
	public static final Mapx USERLOG_LOGTYPE_MAP = new Mapx();

	static {
		USERLOG_LOGTYPE_MAP.put("0", "");
		USERLOG_LOGTYPE_MAP.put(LOGIN, "登陆");
		USERLOG_LOGTYPE_MAP.put(LOGOUT, "退出");
	}
	// 论坛操作
	public static final Mapx USERLOG_FORUM_MAP = new Mapx();
	public static final String FORUM = "Forum";
	public static final String FORUM_TOPTHEME = "TopTheme";
	public static final String FORUM_TOPCANCEL = "TopCancel";
	public static final String FORUM_DELTHEME = "DelTheme";
	public static final String FORUM_BESTTHEME = "BestTheme";
	public static final String FORUM_BESTCANCEL = "BestCancel";
	public static final String FORUM_BRIGHTTHEME = "BrightTheme";
	public static final String FORUM_UPTHEME = "UpTheme";
	public static final String FORUM_DOWNTHEME = "DownTheme";
	public static final String FORUM_MOVETHEME = "MoveTheme";
	public static final String FORUM_EDITPOST = "EditPost";
	public static final String FORUM_DELPOST = "DelPost";
	public static final String FORUM_HIDEPOST = "HidePost";

	static {
		USERLOG_FORUM_MAP.put("0", "");
		USERLOG_FORUM_MAP.put(FORUM_TOPTHEME, "置顶主题");
		USERLOG_FORUM_MAP.put(FORUM_TOPCANCEL, "取消置顶");
		USERLOG_FORUM_MAP.put(FORUM_DELTHEME, "删除主题");
		USERLOG_FORUM_MAP.put(FORUM_BESTTHEME, "设置精华");
		USERLOG_FORUM_MAP.put(FORUM_BESTCANCEL, "取消精华");
		USERLOG_FORUM_MAP.put(FORUM_UPTHEME, "提升主题");
		USERLOG_FORUM_MAP.put(FORUM_DOWNTHEME, "下沉主题");
		USERLOG_FORUM_MAP.put(FORUM_MOVETHEME, "移动主题");
		USERLOG_FORUM_MAP.put(FORUM_EDITPOST, "编辑帖子");
		USERLOG_FORUM_MAP.put(FORUM_DELPOST, "删除帖子");
		USERLOG_FORUM_MAP.put(FORUM_HIDEPOST, "屏蔽帖子");
	}

	// 站点操作
	public static final Mapx USERLOG_SITE_MAP = new Mapx();
	public static final String SITE = "Site";
	public static final String SITE_ADDSITE = "AddSite";
	public static final String SITE_DELSITE = "DelSite";
	public static final String SITE_UPDATESITE = "UpdateSite";
	static {
		USERLOG_SITE_MAP.put("0", "");
		USERLOG_SITE_MAP.put(SITE_ADDSITE, "增加站点");
		USERLOG_SITE_MAP.put(SITE_DELSITE, "删除站点");
		USERLOG_SITE_MAP.put(SITE_UPDATESITE, "修改站点");
	}
	// 栏目操作
	public static final Mapx USERLOG_CATALOG_MAP = new Mapx();
	public static final String CATALOG = "Catalog";
	public static final String CATALOG_ADDCATALOG = "AddCataLog";
	public static final String CATALOG_DELCATALOG = "DelCataLog";
	public static final String CATALOG_UPDATECATALOG = "UpdateCataLog";
	static {
		USERLOG_CATALOG_MAP.put("0", "");
		USERLOG_CATALOG_MAP.put(CATALOG_ADDCATALOG, "增加栏目");
		USERLOG_CATALOG_MAP.put(CATALOG_DELCATALOG, "删除栏目");
		USERLOG_CATALOG_MAP.put(CATALOG_UPDATECATALOG, "修改栏目");
	}
	// 文章操作
	public static final Mapx USERLOG_ARTICLE_MAP = new Mapx();
	public static final String ARTICLE = "Article";
	public static final String ARTICLE_SAVEARTICLE = "AddArticle";
	public static final String ARTICLE_DELARTICLE = "DelArticle";
	public static final String ARTICLE_PUBLISHARTICLE = "PublishArticle";
	public static final String ARTICLE_TOPUBLISHARTICLE = "ToPublishArticle";
	public static final String ARTICLE_MOVEARTICLE = "MoveArticle";
	public static final String ARTICLE_TOPARTICLE = "TopArticle";
	public static final String ARTICLE_NOTTOPARTICLE = "NotTopArticle";
	public static final String ARTICLE_UPARTICLE = "UpArticle";
	public static final String ARTICLE_DOWNARTICLE = "DownArticle";
	public static final String ARTICLE_COPYARTICLE = "CopyArticle";
	static {
		USERLOG_ARTICLE_MAP.put("0", "");
		USERLOG_ARTICLE_MAP.put(ARTICLE_SAVEARTICLE, "增加文章");
		USERLOG_ARTICLE_MAP.put(ARTICLE_DELARTICLE, "删除文章");
		USERLOG_ARTICLE_MAP.put(ARTICLE_PUBLISHARTICLE, "发布文章");
		USERLOG_ARTICLE_MAP.put(ARTICLE_TOPUBLISHARTICLE, "转为待发布文章");
		USERLOG_ARTICLE_MAP.put(ARTICLE_MOVEARTICLE, "转移文章");
		USERLOG_ARTICLE_MAP.put(ARTICLE_TOPARTICLE, "置顶文章");
		USERLOG_ARTICLE_MAP.put(ARTICLE_NOTTOPARTICLE, "取消置顶文章");
		USERLOG_ARTICLE_MAP.put(ARTICLE_UPARTICLE, "文章上线");
		USERLOG_ARTICLE_MAP.put(ARTICLE_DOWNARTICLE, "文章下线");
		USERLOG_ARTICLE_MAP.put(ARTICLE_COPYARTICLE, "复制文章");

	}
	// 媒体库操作
	public static final Mapx USERLOG_RESOURCE_MAP = new Mapx();
	public static final String RESOURCE = "Resource";
	public static final String RESOURCE_ADDTYPEIMAGE = "AddTypeImage";
	public static final String RESOURCE_ADDIMAGE = "AddImage";
	public static final String RESOURCE_EDITIMAGE = "EditImage";
	public static final String RESOURCE_PUBLISTHIMAGE = "PublishImage";
	public static final String RESOURCE_COPYIMAGE = "CopyImage";
	public static final String RESOURCE_MOVEIMAGE = "MoveImage";
	public static final String RESOURCE_DELIMAGE = "DelImage";

	public static final String RESOURCE_ADDTYPEVIDEO = "AddTypeVideo";
	public static final String RESOURCE_ADDVIDEO = "AddVideo";
	public static final String RESOURCE_EDITVIDEO = "EditVideo";
	public static final String RESOURCE_PUBLISTHVIDEO = "PublishVideo";
	public static final String RESOURCE_COPYVIDEO = "CopyVideo";
	public static final String RESOURCE_MOVEVIDEO = "MoveVideo";
	public static final String RESOURCE_DELVIDEO = "DelVideo";

	public static final String RESOURCE_ADDTYPEAUDIO = "AddTypeAudio";
	public static final String RESOURCE_ADDAUDIO = "AddAudio";
	public static final String RESOURCE_EDITAUDIO = "EditAudio";
	public static final String RESOURCE_PUBLISTHAUDIO = "PublishAudio";
	public static final String RESOURCE_COPYAUDIO = "CopyAudio";
	public static final String RESOURCE_MOVEAUDIO = "MoveAudio";
	public static final String RESOURCE_DELAUDIO = "DelAudio";

	public static final String RESOURCE_ADDTYPEATTACHMENT = "AddTypeAttachment";
	public static final String RESOURCE_ADDATTACHMENT = "AddAttachment";
	public static final String RESOURCE_EDITATTACHMENT = "EditAttachment";
	public static final String RESOURCE_PUBLISTHATTACHMENT = "PublishAttachment";
	public static final String RESOURCE_COPYATTACHMENT = "CopyAttachment";
	public static final String RESOURCE_MOVEATTACHMENT = "MoveAttachment";
	public static final String RESOURCE_DELATTACHMENT = "DelAttachment";
	static {
		USERLOG_RESOURCE_MAP.put("0", "");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDTYPEIMAGE, "新增图片分类");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDIMAGE, "增加图片");
		USERLOG_RESOURCE_MAP.put(RESOURCE_EDITIMAGE, "编辑图片");
		USERLOG_RESOURCE_MAP.put(RESOURCE_PUBLISTHIMAGE, "发布图片");
		USERLOG_RESOURCE_MAP.put(RESOURCE_COPYIMAGE, "复制图片");
		USERLOG_RESOURCE_MAP.put(RESOURCE_MOVEIMAGE, "移动图片");
		USERLOG_RESOURCE_MAP.put(RESOURCE_DELIMAGE, "删除图片");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDTYPEVIDEO, "新增视频分类");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDVIDEO, "增加视频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_EDITVIDEO, "编辑视频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_PUBLISTHVIDEO, "发布视频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_COPYVIDEO, "复制视频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_MOVEVIDEO, "移动视频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_DELVIDEO, "删除视频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDTYPEAUDIO, "新增音频分类");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDAUDIO, "增加音频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_EDITAUDIO, "编辑音频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_PUBLISTHAUDIO, "发布音频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_COPYAUDIO, "复制音频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_MOVEAUDIO, "移动音频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_DELAUDIO, "删除音频");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDTYPEATTACHMENT, "新增附件分类");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDATTACHMENT, "增加附件");
		USERLOG_RESOURCE_MAP.put(RESOURCE_EDITATTACHMENT, "编辑附件");
		USERLOG_RESOURCE_MAP.put(RESOURCE_PUBLISTHATTACHMENT, "发布附件");
		USERLOG_RESOURCE_MAP.put(RESOURCE_COPYATTACHMENT, "复制附件");
		USERLOG_RESOURCE_MAP.put(RESOURCE_MOVEATTACHMENT, "移动附件");
		USERLOG_RESOURCE_MAP.put(RESOURCE_DELATTACHMENT, "删除附件");
	}

	// 用户角色操作
	public static final Mapx USERLOG_USER_MAP = new Mapx();
	public static final String USER = "User";
	public static final String USER_DELUSER = "DelUser";
	public static final String USER_DELROLE = "DelROLE";
	public static final String USER_EDITPASSWORD = "EditPassword";
	static {
		USERLOG_USER_MAP.put("0", "");
		USERLOG_USER_MAP.put(USER_DELUSER, "删除用户");
		USERLOG_USER_MAP.put(USER_DELROLE, "删除角色");
		USERLOG_USER_MAP.put(USER_EDITPASSWORD, "修改密码");

	}
	// 系统管理
	public static final Mapx USERLOG_SYSTEM_MAP = new Mapx();
	public static final String SYSTEM = "System";
	public static final String SYSTEM_DELBRANCH = "DelBranch";
	public static final String SYSTEM_DELCODE = "DelCode";
	public static final String SYSTEM_DELCONFIG = "DelConfig";
	public static final String SYSTEM_DELSCHEDULE = "DelSchedule";
	public static final String SYSTEM_DELMENU = "DelMenu";
	static {
		USERLOG_SYSTEM_MAP.put("0", "");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELBRANCH, "删除机构");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELCODE, "删除代码");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELCONFIG, "删除配置项");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELSCHEDULE, "删除定时任务");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELMENU, "删除菜单");

	}

	public static final Mapx USERLOG_MAP = new Mapx();
	static {
		USERLOG_MAP.put(LOG, USERLOG_LOGTYPE_MAP);
		USERLOG_MAP.put(FORUM, USERLOG_FORUM_MAP);
		USERLOG_MAP.put(SITE, USERLOG_SITE_MAP);
		USERLOG_MAP.put(CATALOG, USERLOG_CATALOG_MAP);
		USERLOG_MAP.put(ARTICLE, USERLOG_ARTICLE_MAP);
		USERLOG_MAP.put(USER, USERLOG_USER_MAP);
		USERLOG_MAP.put(SYSTEM, USERLOG_SYSTEM_MAP);
		USERLOG_MAP.put(RESOURCE, USERLOG_RESOURCE_MAP);
	}
	/**
	 * 页面显示的下拉列表MAP
	 */
	public static final Mapx USERLOG_SELECT_MAP = new Mapx();
	static {
		USERLOG_SELECT_MAP.put(LOG, "登录状态");
		USERLOG_SELECT_MAP.put(FORUM, "论坛操作");
		USERLOG_SELECT_MAP.put(SITE, "站点操作");
		USERLOG_SELECT_MAP.put(CATALOG, "栏目操作");
		USERLOG_SELECT_MAP.put(ARTICLE, "文章操作");
		USERLOG_SELECT_MAP.put(USER, "用户角色操作");
		USERLOG_SELECT_MAP.put(SYSTEM, "系统管理");
		USERLOG_SELECT_MAP.put(RESOURCE, "媒体库操作");
	}

	public void menuVisit() {
		String id = Request.valueArray()[0].toString();
		if (!StringUtil.verify(id, "Int")) {
			return;
		}
		DataTable dt = new QueryBuilder(
				"select Name,(select Name from ZDMenu where id=a.ParentID) from ZDMenu a where id=?", id)
				.executeDataTable();
		String menu = dt.getString(0, 1) + "->" + dt.getString(0, 0);
		ZDUserLogSchema userlog = new ZDUserLogSchema();
		userlog.setUserName(User.getUserName());
		userlog.setIP(Request.getClientIP());
		userlog.setAddTime(new Date());
		userlog.setLogID(NoUtil.getMaxID("LogID"));
		userlog.setLogType("Menu");
		userlog.setLogMessage("访问菜单：" + menu);
		userlog.insert();
		Response.setStatus(1);
	}

	public void logout() {
		if (log("Menu", "", "退出系统", Request.getClientIP())) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
		}

	}

	public static void dg1DataBind(DataGridAction dga) {
		String searchUser = dga.getParams().getString("SearchUser");
		String ip = dga.getParams().getString("IP");
		String logMessage = dga.getParams().getString("LogMessage");
		String startDate = dga.getParams().getString("StartDate");
		String endDate = dga.getParams().getString("EndDate");
		String logType = dga.getParams().getString("LogType");
		String subType = dga.getParams().getString("SubType");

		QueryBuilder qb = new QueryBuilder("select a.*,(select b.RealName from zduser b where b.UserName = a.UserName) as RealName from ZDUserLog a where 1=1");
		if (StringUtil.isNotEmpty(searchUser)) {
			qb.append(" and UserName like ? ", "%" + searchUser + "%");
		}
		if (StringUtil.isNotEmpty(ip)) {
			qb.append(" and IP like ? ", "%" + ip + "%");
		}
		if (StringUtil.isNotEmpty(logType)) {
			qb.append(" and LogType like ? ", "%" + logType + "%");
		}
		if (StringUtil.isNotEmpty(subType) && !"0".equals(subType)) {
			qb.append(" and SubType like ? ", "%" + subType + "%");
		}
		if (StringUtil.isNotEmpty(logMessage)) {
			qb.append(" and LogMessage like ? ", "%" + logMessage + "%");
		}
		if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
			qb.append(" and AddTime >=?", startDate);
			qb.append(" and AddTime<=?", endDate);
		}
		qb.append(" order by addtime desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("LogType", USERLOG_SELECT_MAP);
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		Date date = new Date();
		String str = DateUtil.toString(date);
		params.put("Time", str);
		params.put("LogType", HtmlUtil.mapxToOptions(USERLOG_SELECT_MAP, true));
		return params;
	}
	
	public static Mapx initDialog(Mapx params) {
		ZDUserLogSchema userLog = new ZDUserLogSchema();
		userLog.setUserName(params.getString("UserName"));
		userLog.setLogID(params.getString("LogID"));
		userLog.fill();
		params = userLog.toMapx();
		params.put("LogType", USERLOG_SELECT_MAP.getString(userLog.getLogType()));
		params.put("RealName", PubFun.getUserRealName(userLog.getUserName()));
		return params;
	}

	public void del() {
		String IDs = $V("ids");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		ZDUserLogSet set = new ZDUserLogSchema().query(new QueryBuilder("where LogID in (" + IDs + ")"));
		if (set.deleteAndBackup()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	public void delAll() {
		QueryBuilder qb = new QueryBuilder("delete from ZDUserLog");
		if (qb.executeNoQuery() > 0) {
			Response.setLogInfo(1, "日志清空成功");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}
	
	public void delByTime(){
		int month = Integer.parseInt($V("month"));
		long timeNow = new Date().getTime();
		long time = timeNow-((long)1000*60*60*24*30*month);
		Date date = new Date(time); 
		String sql = "delete from zdUserlog where addtime < ?";
		QueryBuilder qb = new QueryBuilder(sql,date);
		if(qb.executeNoQuery()>=0){
			Response.setLogInfo(1, "删除成功");
		}else{
			Response.setLogInfo(0, "操作失败");
		}

	}

	public static boolean log(String logType, String subType, String logMessage, String ip) {
		return log(logType, subType, logMessage, ip, User.getUserName(), null, null);
	}

	public static boolean log(String logType, String subType, String logMessage, String ip, Transaction trans) {
		return log(logType, subType, logMessage, ip, User.getUserName(), trans, null);
	}

	public static boolean log(String logType, String subType, String logMessage, String ip, String userName) {
		return log(logType, subType, logMessage, ip, userName, null, null);
	}

	public static boolean log(String logType, String subType, String logMessage, String ip, String userName, String memo) {
		return log(logType, subType, logMessage, ip, userName, null, memo);
	}
	
	/**
	 * @param trans
	 *            当前加入用户日志的事务对象
	 * @param logType
	 *            消息类型
	 * @param logMessage
	 *            消息内容
	 * @param ip
	 *            用户IP
	 */
	public static boolean log(String logType, String subType, String logMessage, String ip, String userName,
			Transaction trans, String memo) {
		ZDUserLogSchema userlog = new ZDUserLogSchema();
		userlog.setUserName(userName);
		userlog.setIP(ip);
		userlog.setAddTime(new Date());
		userlog.setLogID(NoUtil.getMaxID("LogID"));
		userlog.setLogType(logType);
		userlog.setSubType(subType);
		userlog.setLogMessage(logMessage);
		userlog.setMemo(memo);
		if (trans == null) {
			return userlog.insert();
		} else {
			trans.add(userlog, Transaction.INSERT);
		}
		return true;
	}

	public static DataTable getSubType(Mapx params) {
		String logType = params.getString("LogType");
		if (StringUtil.isEmpty(logType) || "0".equals(logType)) {
			return null;
		}
		Mapx map = (Mapx) USERLOG_MAP.get(logType);
		return map.toDataTable();
	}
}
