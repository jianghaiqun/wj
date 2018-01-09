package com.sinosoft.framework.data;

import com.sinosoft.framework.utility.Mapx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);

	public static DataTable getTableInfo() {
		return getTableInfo(DBConnPool.getDBConnConfig());
	}

	public static DataTable getTableInfo(DBConnConfig dcc) {
		Connection conn = null;
		try {
			conn = DBConnPoolImpl.createConnection(dcc, false);
			DatabaseMetaData dbm = conn.getMetaData();
			String currentCatalog = conn.getCatalog();
			ResultSet rs = dbm.getTables(currentCatalog, null, null, null);
			return new DataTable(rs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}

	public static DataTable getColumnInfo(String tableName) {
		return getColumnInfo(DBConnPool.getDBConnConfig(), tableName);
	}

	public static DataTable getColumnInfo(DBConnConfig dcc, String tableName) {
		DBConn conn = null;
		try {
			conn = DBConnPoolImpl.createConnection(dcc, false);
			DatabaseMetaData dbm = conn.getMetaData();
			String currentCatalog = conn.getCatalog();
			String schema = null;
			String oldName = tableName;
			int index = tableName.indexOf(".");
			if (index > 0) {
				schema = tableName.substring(0, index);
				tableName = tableName.substring(index + 1);
			}
			ResultSet rs = dbm.getColumns(currentCatalog, schema, tableName, null);
			DataTable dt = new DataTable(rs);

			rs = dbm.getPrimaryKeys(currentCatalog, null, tableName);
			DataTable keyDt = new DataTable(rs);
			Mapx map = keyDt.toMapx("Column_Name", "PK_Name");
			dt.insertColumn("isKey");
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataRow dr = dt.getDataRow(i);
				if (map.containsKey(dr.getString("Column_Name"))) {
					dr.set("isKey", "Y");
				} else {
					dr.set("isKey", "N");
				}
			}
			DataAccess da = new DataAccess(conn);
			DataTable data = da.executeDataTable(new QueryBuilder("select * from " + oldName + " where 1=2"));
			for (int i = 0; i < data.getColCount(); i++) {
				DataRow dr = dt.getDataRow(i);
				dr.set("Type_Name", data.getDataColumn(i).getColumnType());
			}
			return dt;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}

	public static DataTable getSQLTypes() {
		return getSQLTypes(DBConnPool.getDBConnConfig());
	}

	public static DataTable getSQLTypes(DBConnConfig dcc) {
		Connection conn = null;
		try {
			conn = DBConnPoolImpl.createConnection(dcc, false);
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet rs = dbm.getTypeInfo();
			DataTable dt = new DataTable(rs);
			return dt;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}

	public static int getCount(QueryBuilder qb) {
		return DataAccess.getCount(DBConnPool.getDBConnConfig().DBType, qb);
	}
}
