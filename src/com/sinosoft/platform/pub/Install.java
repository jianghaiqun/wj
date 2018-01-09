package com.sinosoft.platform.pub;

import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.RequestImpl;
import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnConfig;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DBConnPoolImpl;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.orm.DBImport;
import com.sinosoft.framework.schedule.CronManager;
import com.sinosoft.framework.security.EncryptUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.UserList;
import com.sinosoft.schema.ZCFullTextSchema;
import com.sinosoft.schema.ZCSiteSchema;
import org.apache.tools.ant.filters.StringInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * 初始化数据库
 */
public class Install extends Ajax {
	public void execute() {
		if (Config.isInstalled) {
			Response.setError("已经为" + Config.getAppCode() + "初始数据库完毕，不能再次初始化!");
			return;
		}

		final DBConnConfig dcc = new DBConnConfig();
		dcc.isJNDIPool = "1".equals($V("isJNDIPool"));
		dcc.isLatin1Charset = "1".equals($V("isLatin1Charset"));
		dcc.JNDIName = $V("JNDIName");
		dcc.DBName = $V("DBName");
		dcc.DBPassword = $V("Password");
		try {
			dcc.DBPort = Integer.parseInt($V("Port"));
		} catch (NumberFormatException e1) {
			// e1.printStackTrace();
		}
		dcc.DBServerAddress = $V("Address");
		dcc.DBType = $V("ServerType");
		dcc.DBUserName = $V("UserName");

		if (Config.isJboss()) {// JBoss下要去掉前边的jdbc
			if (dcc.JNDIName.toLowerCase().startsWith("jdbc/")) {
				dcc.JNDIName = dcc.JNDIName.substring(5);
			}
		}

		DBConn conn = null;
		try {
			if (dcc.isMysql()) {
				try {
					conn = DBConnPoolImpl.createConnection(dcc, false);
				} catch (Exception e) {// 有可能是因为数据库不存在
					if (conn != null) {
						conn.close();
					}
					dcc.DBName = "mysql";
					try {
						conn = DBConnPoolImpl.createConnection(dcc, false);
						// 如果是Mysql,则检查表名大小写
						DataAccess da = new DataAccess(conn);
						DataTable dt = da.executeDataTable(new QueryBuilder(
								"show variables like 'lower_case_table_names'"));
						if (dt.getRowCount() == 0 || dt.getInt(0, 1) == 0) {
							Response.setError("检查到mysql数据库区分表名大小写，请修改my.cnf或my.ini:<br><font color=red>"
									+ "在[mysqld]段加上一行配置lower_case_table_names=1!</font>");
							conn.closeReally();
							return;
						}
						// 检查字符集
						dt = da.executeDataTable(new QueryBuilder("show variables like 'character_set_database'"));
						String charset = Constant.GlobalCharset.replaceAll("\\-", "");
						if (!charset.equalsIgnoreCase(dt.getString(0, 1))) {
							Response.setError("检查到mysql的字符集为" + dt.getString(0, 1) + "，但程序要求的字符集为"
									+ charset.toLowerCase() + "，请修改my.cnf或my.ini:<br><font color=red>"
									+ "凡以default-character-set开头的行，都修改为default-character-set=" + charset.toLowerCase()
									+ "</font>");
							conn.closeReally();
							return;
						}
						if (!dcc.isJNDIPool) {
							// 检查数据库是否存在，如果不存在，则先创建一个
							dt = da.executeDataTable(new QueryBuilder("show databases like ?", $V("DBName")));
							if (dt.getRowCount() == 0) {
								logger.info("安装目标数据库不存在，将自动创建目标数据库!");
								da.executeNoQuery(new QueryBuilder("create schema " + $V("DBName")));
								dcc.DBName = $V("DBName");// 必须改回去,dcc中的值会写入framework.xml
								conn.close();// 必须关闭到mysql的连接
								conn = DBConnPoolImpl.createConnection(dcc, false);
							}
						}
					} catch (Exception e2) {// 如果mysql也不能连接，则抛出原异常
						conn.closeReally();
						throw e;
					}
				}
			} else if (dcc.isSQLServer() || dcc.isSybase()) {
				try {
					conn = DBConnPoolImpl.createConnection(dcc, false);
				} catch (Exception e) {// 有可能是因为数据库不存在
					logger.error(e.getMessage(), e);
					if (conn != null) {
						conn.closeReally();
					}
					if (dcc.isSQLServer() && !dcc.isJNDIPool) {
						dcc.DBName = "master";
						try {
							conn = DBConnPoolImpl.createConnection(dcc, false);
							DataAccess da = new DataAccess(conn);
							// 检查数据库是否存在，如果不存在，则先创建一个
							DataTable dt = da.executeDataTable(new QueryBuilder(
									"select * from sysDatabases where name=?", $V("DBName")));
							if (dt.getRowCount() == 0) {
								if (Config.isSQLServer()) {
									logger.info("安装目标数据库不存在，将自动创建目标数据库!");
									da.executeNoQuery(new QueryBuilder("create database " + $V("DBName")));
									dcc.DBName = $V("DBName");
									conn.closeReally();// 必须关闭到master的连接
									conn = DBConnPoolImpl.createConnection(dcc, false);
								}
							} else {// 存在数据库，又不能连接，则说明没有权限
								conn.closeReally();
								Response.setError("用户" + dcc.DBUserName + "没有访问数据库" + $V("DBName") + "的权限！");
								return;
							}
						} catch (Exception e2) {// 如果master也不能连接，则抛出原异常
							throw e;
						}
					}else{
						return;
					}
				}
				if (dcc.isSybase() && !dcc.isJNDIPool) {// 要防止数据库不存在直接写入master库的问题
					DataAccess da = new DataAccess(conn);
					try {
//						da.executeNoQuery(new QueryBuilder("use master"));
//						// 检查数据库是否存在，如果不存在，则先创建一个
//						DataTable dt = da.executeDataTable(new QueryBuilder("select * from sysdatabases where name=?",
//								$V("DBName")));
//						if (dt.getRowCount() == 0) {
//							Response.setError("安装目标数据库不存在，请手工创建!<br>" + "注意：<br>1、注意分配给该数据库的存储空间不小于150M！"
//									+ "<br>2、服务器页面大小必须为16K" + "<br>3、字符集必须为UTF8且排序规则为nocase！");
//							conn.closeReally();
//							return;
//						}
						da.executeNoQuery(new QueryBuilder("use " + $V("DBName")));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						// 说明没有master库的权限
					}
				}
			} else {
				conn = DBConnPoolImpl.createConnection(dcc, false);
			}
			boolean importData = "1".equals($V("ImportData"));
			final DBConn conn2 = conn;
			final boolean autoCreate = "1".equals($V("AutoCreate"));
			final RequestImpl r = Request;

			if (importData) {
				LongTimeTask ltt = LongTimeTask.getInstanceByType("Install");
				if (ltt != null) {
					Response.setError("相关任务正在运行中，请先中止！");
					return;
				}
				ltt = new LongTimeTask() {
					public void execute() {
						try {
							DBImport di = new DBImport();
							di.setTask(this);
							Config.setValue("App.DebugMode", "true");
							if (di.importDB(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat", conn2,
									autoCreate)) {
								if (autoCreate) {
									setCurrentInfo("正在建立索引");
									Install.createIndexes(conn2, null, conn2.getDBConfig().DBType);
								}
								setCurrentInfo("正在初始化系统配置");
								Install.init(conn2, r);
								setPercent(33);
								generateFrameworkConfig(dcc);
								Config.loadConfig();// 重新载入framework.xml
								Config.isInstalled = false;
								Config.loadDBConfig();// 必须加载数据库配置

								UserData user = new UserData();
								user.setLogin(true);
								user.setManager(true);
								user.setBranchInnerCode("0001");
								user.setUserName(UserList.ADMINISTRATOR);
								User.setCurrent(user);
								Application.setCurrentSiteID(new QueryBuilder("select ID from ZCSite").executeString());

								// 开始建立数据库索引
								setCurrentInfo("正在建立全文检索索引文件，需耗时约1至3分钟，请稍等");
								ZCFullTextSchema ft = new ZCFullTextSchema();
								ft = ft.query().get(0);
								SearchAPI.update(ft.getID());

								// 开始发布全站
								setCurrentInfo("正在生成全站静态文件，需耗时约2-5分钟，请稍等");
								PageGenerator pg = new PageGenerator(this);
								ZCSiteSchema site = new ZCSiteSchema();
								site = site.query().get(0);
								pg.staticSite(site.getID());

								setCurrentInfo("安装完成，将重定向到登录页面!");
								Config.isInstalled = true;// 安装结束
								// 开始定时任务系统
								CronManager.getInstance().init();
							} else {
								addError("<font color=red>导入失败，请查看服务器日志! 确认问题后请按F5刷新页面重新导入。</font>");
							}
						} finally {
							if (conn2 != null) {
								try {
									conn2.closeReally();
								} catch (SQLException e) {
									logger.error(e.getMessage(), e);
								}
							}
						}
					}
				};
				ltt.setType("Install");
				ltt.setUser(User.getCurrent());
				ltt.start();
				$S("TaskID", "" + ltt.getTaskID());
				Response.setStatus(1);
			} else {// 只配置连接
				Install.init(conn2, r);
				generateFrameworkConfig(dcc);
				Config.loadConfig();// 重新载入framework.xml
				CronManager.getInstance().init();
				Response.setError(Config.getAppCode() + "初始化完毕!");// 本不是错误，用以区别导入数据的情况
			}
		} catch (Exception e) {
			Response.setError("连接到数据库时发生错误:" + e.getMessage());
		} 
	}

	public static void generateFrameworkConfig(DBConnConfig dcc) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<framework>\n");
		sb.append("	<application>\n");
		sb.append("		<config name=\"DebugMode\">false</config>\n");
		sb.append("		<config name=\"LogLevel\">Debug</config>\n");
		sb.append("		<config name=\"LoginClass\">com.sinosoft.platform.Login</config>\n");
		sb.append("		<config name=\"LoginPage\">Login.jsp</config>\n");
		sb.append("		<config name=\"MinimalMemory\">true</config>\n");
		sb.append("		<config name=\"CodeSource\">com.sinosoft.platform.pub.PlatformCodeSource</config>\n");
		sb.append("		<config name=\"WorkflowAdapter\">com.sinosoft.cms.workflow.CMSWorkflowAdapter</config>\n");
		sb.append("		<config name=\"ExistPlatformDB\">true</config>\n");
		sb.append("		<config name=\"PDM\">Platform,ZCMS,WorkFlow</config>\n");
		sb.append("	</application>\n");

		sb.append("	<cache>\n");
		sb.append("		<provider class=\"com.sinosoft.bbs.ForumCache\" />\n");
		sb.append("		<provider class=\"com.sinosoft.platform.pub.PlatformCache\" />\n");
		sb.append("		<provider class=\"com.sinosoft.cms.pub.CMSCache\" />\n");
		sb.append("		<provider class=\"com.sinosoft.cms.document.MessageCache\" />\n");
		sb.append("	</cache>\n");

		sb.append("	<extend>\n");
		sb.append("		<action class=\"com.sinosoft.shop.extend.ShopMenuExtend\" />\n");
		sb.append("		<action class=\"com.sinosoft.shop.extend.ShopLoginExtend\" />\n");
		sb.append("		<action class=\"com.sinosoft.bbs.extend.BBSMenuExtend\" />\n");
		sb.append("		<action class=\"com.sinosoft.bbs.extend.BBSLoginExtend\" />\n");
		sb.append("	</extend>\n");

		sb.append("	<cron>\n");
		sb.append("		<config name=\"RefreshInterval\">30000</config>\n");
		sb.append("		<taskManager class=\"com.sinosoft.datachannel.WebCrawlTaskManager\" />\n");
		sb.append("		<taskManager class=\"com.sinosoft.datachannel.WJServiceManager\" />\n");
		sb.append("		<taskManager class=\"com.sinosoft.datachannel.PointServiceManager\" />\n");
		sb.append("		<taskManager class=\"com.sinosoft.datachannel.SMSSendManager\" />\n");
		sb.append("		<taskManager class=\"com.sinosoft.cms.dataservice.FullTextTaskManager\" />\n");
		sb.append("		<taskManager class=\"com.sinosoft.cms.datachannel.PublishTaskManager\" />\n");
		sb.append("		<task class=\"com.sinosoft.framework.FrameworkTask\" time=\"30 10,16 * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.cms.dataservice.ADUpdating\" time=\"*/30 * * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.cms.datachannel.DeployTask\" time=\"* * * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.cms.datachannel.PublishTask\" time=\"* * * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.cms.stat.StatUpdateTask\" time=\"*/5 * * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.cms.datachannel.ArticleArchiveTask\" time=\"0 0 1 * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.cms.datachannel.ArticleCancelTopTask\" time=\"*/5 * * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.cms.document.ArticleRelaTask\" time=\"*/2 * * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.cms.site.TagUpdateTask\" time=\"0 * * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.datachannel.InnerSyncTask\" time=\"* * * * *\" />\n");
		sb.append("		<task class=\"com.sinosoft.datachannel.DBSyncTask\" time=\"* * * * *\" />\n");
		sb.append("	</cron>\n");

		sb.append("	<databases>\n");
		sb.append("		<database name=\"Default\">\n");
		sb.append("			<config name=\"Type\">" + dcc.DBType + "</config>\n");
		if (dcc.isJNDIPool) {
			sb.append("			<config name=\"JNDIName\">" + dcc.JNDIName + "</config>\n");
		} else {
			sb.append("			<config name=\"ServerAddress\">" + dcc.DBServerAddress + "</config>\n");
			sb.append("			<config name=\"Port\">" + dcc.DBPort + "</config>\n");
			sb.append("			<config name=\"Name\">" + dcc.DBName + "</config>\n");
			sb.append("			<config name=\"UserName\">" + dcc.DBUserName + "</config>\n");
			sb.append("			<config name=\"Password\">$KEY"
					+ EncryptUtil.encrypt3DES(dcc.DBPassword, EncryptUtil.DEFAULT_KEY) + "</config>\n");
			sb.append("			<config name=\"MaxConnCount\">1000</config>\n");
			sb.append("			<config name=\"InitConnCount\">0</config>\n");
			sb.append("			<config name=\"TestTable\">ZDMaxNo</config>\n");
			if (dcc.isLatin1Charset) {
				sb.append("			<config name=\"isLatin1Charset\">true</config>\n");
			}
		}
		sb.append("		</database>\n");
		sb.append("	</databases>\n");
		sb.append("	<allowUploadExt>\n");
		sb
				.append("		<config name=\"AllowAttachExt\">doc,docx,xls,xlsx,ppt,pptx,pdf,swf,rar,zip,txt,xml,html,htm,css,js,db,dat</config>\n");
		sb.append("		<config name=\"AllowAudioExt\">mp3</config>\n");
		sb.append("		<config name=\"AllowImageExt\">jpg,gif,jpeg,png,bmp,tif,zip</config>\n");
		sb.append("		<config name=\"AllowVideoExt\">asx,flv,avi,wmv,mp4,mov,asf,mpg,rm,rmvb</config>\n");
		sb.append("		</allowUploadExt>\n");
		sb.append("	</framework>\n");
		FileUtil.writeText(Config.getContextRealPath() + "WEB-INF/classes/framework.xml", sb.toString(), "UTF-8");
	}

	public static void generateSQL(HttpServletRequest request, HttpServletResponse response) {
		String dbtype = request.getParameter("Type");
		String sql = new DBImport().getSQL(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat", dbtype);

		StringBuffer sb = new StringBuffer(sql);
		createIndexes(null, sb, dbtype);

		try {
			request.setCharacterEncoding(Constant.GlobalCharset);
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" + dbtype + ".zip");

			OutputStream os = response.getOutputStream();
			ZipUtil.zipStream(new StringInputStream(sb.toString()), response.getOutputStream(), dbtype + ".sql");
			os.flush();
			os.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		createIndexes(DBConnPool.getConnection(), new StringBuffer(), "MYSQL");
	}

	public static String[] IndexArray = new String[] { "zcarticle(CatalogID,Status)", "zcarticle(Orderflag)",
			"zcarticle(publishDate)", "zcarticle(addtime)", "zcarticle(modifytime)", "zcarticle(DownlineDate)",
			"zcarticle(CatalogInnercode)", "zcarticle(SiteID)", "zcarticle(refersourceid)", "zcarticle(keyword)",
			"zcarticle(ArchiveDate)", "bzcarticle(BackupMemo)", "bzcarticle(CatalogID)", "zcimage(CatalogID)",
			"zcimage(CatalogInnercode)", "zcimage(SiteID)", "zcimage(OrderFlag)", "zcimage(publishDate)",
			"zcimage(addtime)", "zcimage(modifytime)", "zcvideo(CatalogID)", "zcvideo(CatalogInnercode)",
			"zcvideo(SiteID)", "zcvideo(OrderFlag)", "zcvideo(publishDate)", "zcvideo(addtime)", "zcvideo(modifytime)",
			"zcaudio(CatalogID)", "zcaudio(CatalogInnercode)", "zcaudio(SiteID)", "zcaudio(OrderFlag)",
			"zcaudio(publishDate)", "zcaudio(addtime)", "zcaudio(modifytime)", "zcattachment(CatalogID)",
			"zcattachment(CatalogInnercode)", "zcattachment(SiteID)", "zcattachment(OrderFlag)",
			"zcattachment(publishDate)", "zcattachment(addtime)", "zcattachment(modifytime)",
			"zcarticlelog(articleID)", "zcarticlevisitlog(articleID)", "zccomment(relaid)", "zccomment(catalogid)",
			"zccatalog(SiteID,Type)", "zccatalog(InnerCode)", "zcpageblock(SiteID)", "zcpageblock(CatalogID)",
			"zdprivilege(owner)", "zdcolumnrela(relaid)", "zdcolumnvalue(relaid)", "zwinstance(workflowid)",
			"zwstep(InstanceID)", "zwstep(NodeID)", "zwstep(owner)", "zctag(tag,siteid)", "zcvisitlog(siteid)" };

	/**
	 * 获得索引创建语句，参数i表示该索引是所在表的第几个索引
	 */
	public static String createIndex(String indexInfo, int i, String dbType) {
		int p1 = indexInfo.indexOf("(");
		if (p1 < 0) {
			return null;
		}
		String table = indexInfo.substring(0, p1);
		if (!indexInfo.endsWith(")")) {
			return null;
		}
		indexInfo = indexInfo.substring(p1 + 1, indexInfo.length() - 1);
		String[] cs = indexInfo.split(",");
		StringBuffer sb = new StringBuffer();
		String name = "idx" + i + "_" + table.substring(2);
		if (dbType.equals(DBConnConfig.ORACLE)) {
			if (name.length() > 15) {
				name = name.substring(0, 15);
			}
		}
		sb.append("create index " + name + " on " + table + " (");
		boolean first = true;
		for (int j = 0; j < cs.length; j++) {
			if (StringUtil.isEmpty(cs[j])) {
				continue;
			}
			if (!first) {
				sb.append(",");
			}
			sb.append(cs[j]);
			first = false;
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 返回某张表上的所有索引语句
	 */
	public static ArrayList getIndexSQLForTable(String tableName, String dbType) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < IndexArray.length; i++) {
			String indexInfo = IndexArray[i];
			if (indexInfo.toLowerCase().startsWith(tableName.toLowerCase() + "(")) {
				String sql = createIndex(indexInfo, i, dbType);
				if (StringUtil.isNotEmpty(sql)) {
					list.add(sql);
				}
			}
		}
		return list;
	}

	/**
	 * 创建所有索引
	 */
	public static void createIndexes(DBConn conn, StringBuffer sbAll, String dbtype) {
		DataAccess da = null;
		if (conn == null) {
			da = new DataAccess();
		} else {
			da = new DataAccess(conn);
		}
		for (int i = 0; i < IndexArray.length; i++) {
			String indexInfo = IndexArray[i];
			int p1 = indexInfo.indexOf("(");
			if (p1 < 0) {
				continue;
			}
			String table = indexInfo.substring(0, p1);
			if (!indexInfo.endsWith(")")) {
				continue;
			}
			String name = "idx" + i + "_" + table.substring(2);
			String sql = createIndex(indexInfo, i, dbtype);
			if (conn != null) {
				try {
					if (dbtype.equals(DBConnConfig.MYSQL)) {
						da.executeNoQuery(new QueryBuilder("alter table " + table + " drop index " + name));
					} else if (dbtype.equals(DBConnConfig.MSSQL)) {
						da.executeNoQuery(new QueryBuilder("drop index " + name + " on " + table));
					} else {
						da.executeNoQuery(new QueryBuilder("drop index " + name));
					}
				} catch (SQLException e) {
					// 不需要输出
				}
				try {
					da.executeNoQuery(new QueryBuilder(sql));
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				sbAll.append(sql);
				if (dbtype.equals(DBConnConfig.MSSQL)) {
					sbAll.append("\n");
					sbAll.append("go\n");
				} else {
					sbAll.append(";\n");
				}
			}
		}
	}

	public static void init(DBConn conn, RequestImpl r) {
		DataAccess da = new DataAccess(conn);
		try {
			if (StringUtil.isNotEmpty(Config.getContextPath())) {
				String path = Config.getContextPath() + "Services";
				String prefix = r.getScheme() + "://" + r.getServerName();
				if (r.getScheme().equalsIgnoreCase("http") && r.getPort() != 80) {
					prefix = prefix + ":" + r.getPort();
				}
				if (r.getScheme().equalsIgnoreCase("https") && r.getPort() != 443) {
					prefix = ":" + r.getPort();
				}
				path = prefix + path;
				da.executeNoQuery(new QueryBuilder("update ZDConfig set Value=? where Type='ServicesContext'", path));
				Config.update();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
