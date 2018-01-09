package cn.com.sinosoft.util;

import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuXin
 * 
 */
public class JdbcTemplateData {
	private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateData.class);
	protected DBConn conn;

	public JdbcTemplateData() {
	 
	}

	/**
	 * 单个sql查询
	 * 
	 * @param sql
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public List obtainData(String sql) throws InstantiationException, IllegalAccessException, ClassNotFoundException { 
		return obtainData(sql, null);   
	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @param temp
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public List obtainData(String sql, String[] temp) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (conn == null) { 
			conn = DBConnPool.getConnection();
		}

		Map rsTree = null; 
		List rsall = new ArrayList();
		PreparedStatement ps = null;
		ResultSet r = null; 
		try { 
			ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (temp != null && temp.length > 0) {
				for (int i = 1; i <= temp.length; i++) {
					ps.setString(i, temp[i - 1]);
				}
			}
			r = ps.executeQuery();
			ResultSetMetaData rsmd = r.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			while (r.next()) {
				rsTree = new HashMap(numberOfColumns);// 注意要new
				for (int i = 1; i < numberOfColumns + 1; i++) {
					rsTree.put(rsmd.getColumnName(i), r.getObject(i));
				}
				rsall.add(rsTree);
			}
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
		} catch (SQLException e) {
			logger.error("JdbcTemplateData.obtainData 数据库查询错误 . " + e.getMessage(), e);

		} finally {
			try {
				if (r != null) { // 关闭记录集
					r.close();
					r = null;

				}
				if (ps != null) { // 关闭声明
					ps.close();
					ps = null;
				}
				if (conn != null) { // 关闭连接对象
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				logger.error("JdbcTemplateData.obtainData 数据库关闭错误 . " + ex.getMessage(), ex);
			}
		}
		return rsall;
	}

	/**
	 * 保存删除
	 * 
	 * @param sql
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public boolean updateOrSaveOrDelete(String sql) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return updateOrSaveOrDelete(sql, null);
	}

	/**
	 * 保存删除
	 * 
	 * @param sql
	 * @param temp
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public boolean updateOrSaveOrDelete(String sql, String[] temp) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		boolean flag = false;
		if (conn == null) {
			conn = DBConnPool.getConnection();
		}

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (temp != null && temp.length > 0) {
				for (int i = 1; i <= temp.length; i++) {
					ps.setString(i, temp[i - 1]);
				}
			}
			flag = ps.executeUpdate() == -1 ? false : true;
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
		} catch (SQLException e) {
			logger.error("JdbcTemplateData.updateOrSaveOrDelete 数据库查询错误 . " + e.getMessage(), e);

		} finally {
			try {
				if (ps != null) { // 关闭声明
					ps.close();
					ps = null;
				}
				if (conn != null) { // 关闭连接对象
					conn.close();
					conn = null;
				}
			} catch (SQLException ex) {
				logger.error("JdbcTemplateData.updateOrSaveOrDelete 数据库关闭错误 . " + ex.getMessage(), ex);
			}
		}

		return flag;
	}

	public static void main(String[] args) {
		try {
			JdbcTemplateData db = new JdbcTemplateData();
			// String[] value = { "certificate", "1061" };
			// String sql =
			// "select codevalue,codename from dictionary where insuranceCode = '1016' ";
			// List<HashMap<String, String>> list = db.obtainData(sql);
			// System.out.println("----" + list.size());  
		} catch (Exception e) {
		}
	}

}
