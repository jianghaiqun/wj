package com.sinosoft.framework;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DBConnPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDBdata {
	private static final Logger logger = LoggerFactory.getLogger(GetDBdata.class);
	private FDate fDate = new FDate();

	public List<HashMap<String, String>> query(String sql) throws Exception {
		Connection con = DBConnPool.getConnection();
		if (con == null) {
			throw new Exception("Connect failed!");
		}
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();
		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			while (rs.next()) {
				HashMap<String, String> map1 = new HashMap<String, String>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = rsmd.getColumnName(i); // 数据库中字段
					map1.put(columnName, getDataValue(rsmd, rs, i));
				}
				list1.add(map1);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
		return list1;
	}

	/**
	 * 
	 * 防止sql注入
	 */
	public List<HashMap<String, String>> query(String sql, String[] temp) throws Exception {
		Connection con = DBConnPool.getConnection();
		if (con == null) {
			throw new Exception("Connect failed!");
		}
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();
		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (temp != null && temp.length > 0) {
				for (int i = 1; i <= temp.length; i++) {
					pstmt.setString(i, temp[i - 1]);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			while (rs.next()) {
				HashMap<String, String> map1 = new HashMap<String, String>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = rsmd.getColumnName(i); // 数据库中字段
					map1.put(columnName, getDataValue(rsmd, rs, i));
				}
				list1.add(map1);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			rs.close();
			pstmt.close();
			con.close();
		}
		return list1;
	}

	/**
	 * 查询sql add by guobin
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryObj(String sql) throws Exception {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBConnPool.getConnection();
			if (con == null) {
				throw new Exception("Connect failed!");
			}
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			return resultSetToList(rs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (con != null && !con.isClosed()) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {
				throw new Exception("数据关闭异常", e);
			}
		}

	}

	/**
	 * 添加防止sql注入
	 */
	public List<Map<String, Object>> queryObj(String sql, String[] temp) throws Exception {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBConnPool.getConnection();
			if (con == null) {
				throw new Exception("Connect failed!");
			}
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (temp != null && temp.length > 0) {
				for (int i = 1; i <= temp.length; i++) {
					pstmt.setString(i, temp[i - 1]);
				}
			}
			rs = pstmt.executeQuery();
			return resultSetToList(rs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (con != null && !con.isClosed()) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {
				throw new Exception("数据关闭异常", e);
			}
		}

	}

	/**
	 * 将rs转换为list方法 list中为hashmap add by guobin
	 * 
	 * @param rs
	 * @return
	 * @throws java.sql.SQLException
	 */
	private ArrayList<Map<String, Object>> resultSetToList(ResultSet rs) throws java.sql.SQLException {
		if (rs == null)
			return new ArrayList<Map<String, Object>>();

		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();

		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> rowData;
		while (rs.next()) {
			rowData = new HashMap<String, Object>();
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), getDataValue(md, rs, i));
			}
			list.add(rowData);
		}
		return list;
	}

	public List<HashMap<String, String>> query(String sql, String JndiName) throws Exception {
		Connection con = DBConnPool.getConnection(JndiName);
		if (con == null) {
			throw new Exception("Connect failed!");
		}

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		List<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();

		while (rs.next()) {
			HashMap<String, String> map1 = new HashMap<String, String>();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i); // 数据库中字段
				map1.put(columnName, getDataValue(rsmd, rs, i));
			}
			list1.add(map1);
		}
		rs.close();
		pstmt.close();
		con.close();
		return list1;
	}

	/**
	 * 把ResultSet中取出的数据转换为相应的数据值字符串
	 * 输出：如果成功执行，返回True，否则返回False，并且在Error中设置错误的详细信息
	 * 
	 * @param rsmd
	 *            ResultSetMetaData
	 * @param rs
	 *            ResultSet
	 * @param i
	 *            int
	 * @return String
	 */
	private String getDataValue(ResultSetMetaData rsmd, ResultSet rs, int i) {
		String strValue = "";

		try {
			int dataType = rsmd.getColumnType(i);
			// System.out.println(rsmd.getColumnName(i) + "====" + dataType);
			int dataScale = rsmd.getScale(i);
			int dataPrecision = rsmd.getPrecision(i);
			if ((dataType == Types.CHAR) || (dataType == Types.VARCHAR) || (dataType == Types.LONGVARCHAR)) {
				strValue = rs.getString(i);

			} else if (dataType == Types.TIMESTAMP || dataType == Types.DATE) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					if (rs.getObject(i) != null) {
						strValue = fDate.getString(sdf.parse(String.valueOf(rs.getObject(i)))).toString();
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}

			} else if ((dataType == Types.DECIMAL) || (dataType == Types.FLOAT)) {
				strValue = String.valueOf(rs.getBigDecimal(i));
				strValue = PubFun.getInt(strValue);

			} else if ((dataType == Types.INTEGER) || (dataType == Types.SMALLINT)) {
				strValue = String.valueOf(rs.getInt(i));
				strValue = PubFun.getInt(strValue);

			} else if (dataType == Types.NUMERIC) {
				if (dataScale == 0) {
					if (dataPrecision == 0) {
						strValue = String.valueOf(rs.getBigDecimal(i));
						if ("null".equals(strValue)) {
							strValue = "";
						}
					} else {
						strValue = String.valueOf(rs.getLong(i));
						if ("null".equals(strValue)) {
							strValue = "";
						}
					}
				} else {
					strValue = String.valueOf(rs.getBigDecimal(i));
					if ("null".equals(strValue)) {
						strValue = "";
					}
				}
				strValue = PubFun.getInt(strValue);

			} else if (dataType == Types.BIT) {
				strValue = rs.getString(i);

			} else {
				strValue = rs.getString(i);

			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return PubFun.cTrim(strValue);
	}

	public static String getInt(String Value) {
		if (Value == null) {
			return null;
		}
		if (Value.equals("null")) {
			return "";
		}
		String result = "";
		boolean mflag = true;
		int m = 0;
		m = Value.lastIndexOf(".");
		if (m == -1) {
			result = Value;
		} else {
			for (int i = m + 1; i <= Value.length() - 1; i++) {
				if (Value.charAt(i) != '0') {
					result = Value;
					mflag = false;
					break;
				}
			}
			if (mflag) {
				result = Value.substring(0, m);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param sql
	 * @param JndiName
	 *            or null 可以为空
	 * @return boolean
	 */
	public boolean execUpdateSQL(String sql, String JndiName) throws Exception {
		Connection con = DBConnPool.getConnection(JndiName);
		if (con == null) {
			throw new Exception("Connect failed!");
		}
		PreparedStatement pstmt = null;

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			pstmt.executeUpdate();
			pstmt.close();
			con.commit();
			con.close();
		} catch (SQLException e) {
			// @@错误处理
			logger.error("##### Error Sql in ExeSQL at execUpdateSQL: " + sql + e.getMessage(), e);

			try {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
						logger.error(ex.getMessage(), ex);
					} finally {
						pstmt.close();
					}
				}
				con.rollback();
				con.close();
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
				return false;
			}
			return false;
		} finally {
			try {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
						logger.error(ex.getMessage(), ex);
					} finally {
						pstmt.close();
					}
				}
				if (con.isClosed() == false) {
					con.close();
				}
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return true;
	}

	public String getOneValue(String sql) throws Exception {
		Connection con = DBConnPool.getConnection();
		if (con == null) {
			throw new Exception("Connect failed!");
		}
		String value = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				value = getDataValue(rsmd, rs, 1);
				break;
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			logger.error("### Error ExeSQL at getOneValue: " + sql + e.getMessage(), e);
			value = "";
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
						logger.error(ex.getMessage(), ex);
					} finally {
						pstmt.close();
					}
				}
				con.close();
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
						logger.error(ex.getMessage(), ex);
					} finally {
						pstmt.close();
					}
				}
				if (con.isClosed() == false) {
					con.close();
				}
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return value.trim();
	}

	/**
	 * 
	 * 防止sql注入
	 */
	public String getOneValue(String sql, String[] temp) throws Exception {
		Connection con = DBConnPool.getConnection();
		if (con == null) {
			throw new Exception("Connect failed!");
		}
		String value = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (temp != null && temp.length > 0) {
				for (int i = 1; i <= temp.length; i++) {
					pstmt.setString(i, temp[i - 1]);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				value = getDataValue(rsmd, rs, 1);
				break;
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			logger.error("### Error ExeSQL at getOneValue: " + sql + e.getMessage(), e);
			value = "";
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
						logger.error(ex.getMessage(), ex);
					} finally {
						pstmt.close();
					}
				}
				con.close();
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
						logger.error(ex.getMessage(), ex);
					} finally {
						pstmt.close();
					}
				}
				if (con.isClosed() == false) {
					con.close();
				}
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return value.trim();
	}

	public SSRS execSQL(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		SSRS tSSRS = null;
		Connection con = DBConnPool.getConnection();
		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			tSSRS = new SSRS(n);
			while (rs.next()) {
				for (int j = 1; j <= n; j++) {
					tSSRS.SetText(getDataValue(rsmd, rs, j));
				}
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			logger.error("##### Error Sql in ExeSQL at execSQL(String sql): " + sql + e.getMessage(), e);
			tSSRS = null;
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
						logger.error(ex.getMessage(), ex);
					} finally {
						pstmt.close();
					}
				}
				con.close();
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
						logger.error(ex.getMessage(), ex);
					} finally {
						pstmt.close();
					}
				}
				if (con.isClosed() == false) {
					con.close();
				}
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		return tSSRS;
	}
}
