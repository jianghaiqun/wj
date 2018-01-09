package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnConfig;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DBConnPoolImpl;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCDatabaseSchema;
import com.sinosoft.schema.ZCDatabaseSet;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class OuterDatabase extends Page {
	private static boolean initFlag = true;
	private static Object mutex = new Object();

	public static void init() {
		ZCDatabaseSet set = new ZCDatabaseSchema().query();
		for (int i = 0; i < set.size(); i++) {
			ZCDatabaseSchema db = set.get(i);
			addConnPool(db);
		}
	}

	public static void addConnPool(ZCDatabaseSchema db) {
		DBConnConfig dcc = new DBConnConfig();
		dcc.DBName = db.getDBName();
		dcc.DBPassword = db.getPassword();
		dcc.DBPort = (int) db.getPort();
		dcc.DBServerAddress = db.getAddress();
		dcc.DBType = db.getServerType();
		dcc.DBUserName = db.getUserName();
		dcc.PoolName = "_OuterDatabase_" + db.getID();
		dcc.TestTable = db.getTestTable();
		dcc.isLatin1Charset = "Y".equals(db.getLatin1Flag());
		if (!DBConnPool.getPoolMap().containsKey(dcc.PoolName + ".")) {
			new DBConnPoolImpl(dcc);
		}
	}

	public static DBConn getConnection(long id) {
		if (initFlag) {
			synchronized (mutex) {
				if (initFlag) {
					init();
					initFlag = false;
				}
			}
		}
		return DBConnPool.getConnection("_OuterDatabase_" + id);
	}

	public static void removeConnPool(long id) {
		Object o = DBConnPool.getPoolMap().get("_OuterDatabase_" + id + ".");
		if (o == null) {
			return;
		}
		DBConnPoolImpl pool = (DBConnPoolImpl) o;
		pool.clear();
		DBConnPool.getPoolMap().remove("_OuterDatabase_" + id + ".");
	}

	public static DBConnConfig getDBConnConfig(long id) {
		if (initFlag) {
			synchronized (mutex) {
				if (initFlag) {
					init();
					initFlag = false;
				}
			}
		}
		return DBConnPool.getDBConnConfig("_OuterDatabase_" + id);
	}

	public static void dg1DataBind(DataGridAction dga) {
		DataTable dt = new QueryBuilder("select * from ZCDatabase where SiteID=?", Application.getCurrentSiteID())
				.executeDataTable();
		Mapx map = new Mapx();
		map.put("ORACLE", "Oracle");
		map.put("DB2", "DB2");
		map.put("MSSQL2000", "SQL Server 2000");
		map.put("MSSQL", "SQL Server 2005");
		map.put("MYSQL", "Mysql");
		dt.decodeColumn("ServerType", map);
		dga.bindData(dt);
	}

	public void save() {
		ZCDatabaseSchema db = new ZCDatabaseSchema();
		if (StringUtil.isEmpty($V("ID"))) {
			db.setValue(Request);
			db.setID(NoUtil.getMaxID("DatabaseID"));
			db.setAddTime(new Date());
			db.setAddUser(User.getUserName());
			db.setSiteID(Application.getCurrentSiteID());
			if (db.insert()) {
				Response.setMessage("添加数据库连接成功");
			} else {
				Response.setError("发生错误,添加数据库连接失败");
			}
		} else {
			db.setID(Long.parseLong($V("ID")));
			db.fill();
			db.setValue(Request);
			db.setModifyTime(new Date());
			db.setModifyUser(User.getUserName());
			if (db.update()) {
				Response.setMessage("修改数据库连接成功");
			} else {
				Response.setError("发生错误,修改数据库连接失败");
			}
		}
		removeConnPool(db.getID());
		addConnPool(db);
	}

	public void del() {
		String ids = $V("IDs");
		String[] arr = ids.split("\\,");
		Transaction tran = new Transaction();
		for (int i = 0; i < arr.length; i++) {
			tran.add(new QueryBuilder("delete from ZCDatabase where SiteID=? and ID=?", Application.getCurrentSiteID(),
					Long.parseLong(arr[i])));
		}
		if (tran.commit()) {
			Response.setMessage("删除数据库连接成功");
		} else {
			Response.setError("发生错误,删除数据库连接失败");
		}
	}

	public void connTest() {
		DBConnConfig dcc = new DBConnConfig();
		dcc.DBName = $V("DBName");
		dcc.DBPassword = $V("Password");
		dcc.DBPort = Integer.parseInt($V("Port"));
		dcc.DBServerAddress = $V("Address");
		dcc.DBType = $V("ServerType");
		dcc.DBUserName = $V("UserName");
		dcc.isLatin1Charset = "Y".equals($V("Latin1Flag"));
		DBConn conn = null;
		try {
			conn = DBConnPoolImpl.createConnection(dcc, false);
			String msg = "测试连接成功";
			DataAccess da = new DataAccess(conn);
			try {
				da.executeOneValue(new QueryBuilder("select 1 from " + $V("TestTable") + " where 1=2"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				msg += "，但表" + $V("TestTable") + "不存在!";
			} finally {
				da.close();
			}
			Response.setMessage(msg);
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setError("连接到数据库时发生错误:" + e.getMessage());
			return;
		} finally {
			if (conn != null) {
				try {
					conn.closeReally();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public static DataTable getDatabases(Mapx map) {
		return new QueryBuilder("select id,name from ZCDatabase where SiteID=?", Application.getCurrentSiteID())
				.executeDataTable();
	}

	public void getTables() {
		if (StringUtil.isEmpty($V("DatabaseID"))) {
			Response.setError("未传入DatabaseID");
			return;
		}
		long id = Long.parseLong($V("DatabaseID"));
		Connection conn = null;
		try {
			conn = OuterDatabase.getConnection(id);
			DatabaseMetaData dbm = conn.getMetaData();
			String currentCatalog = conn.getCatalog();
			ResultSet rs = dbm.getTables(currentCatalog, null, null, null);
			ArrayList al = new ArrayList();
			while (rs.next()) {
				if (rs.getObject(2) != null) {
					al.add(rs.getObject(2) + "." + rs.getObject(3));
				} else {
					al.add(rs.getObject(3));
				}
			}
			String[] arr = new String[al.size()];
			for (int i = 0; i < arr.length; i++) {// 有时候会超过1000个表，例如具有DBA权限的Oracle用户
				arr[i] = al.get(i).toString();
			}
			Response.put("Tables", arr);
			Response.setMessage("获取表信息成功");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setError("连接到数据库时发生错误:" + e.getMessage());
			return;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
}
