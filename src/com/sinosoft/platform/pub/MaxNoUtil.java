package com.sinosoft.platform.pub;

import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class MaxNoUtil {
	private static final Logger logger = LoggerFactory.getLogger(MaxNoUtil.class);

	private static Object mutex = new Object();
	
	public static String JDMaxNo(String noType, String noLimit) {
		DBConn conn = null;
		CallableStatement c = null;
		synchronized (mutex) {

			try {

				conn = DBConnPool.getConnection();

				c = conn.prepareCall("{ call CreateJDMaxNo_JDT(?,?,?) }");

				// 给存储过程的第一个参数设置值
				c.setString(1, noType);
				c.setString(2, noLimit);
				c.setInt(3, java.sql.Types.VARCHAR);

				// 执行存储过程
				c.execute();

				// 得到存储过程的输出参数值
				String result = c.getString(3);

				conn.setLastSuccessExecuteTime(System.currentTimeMillis());

				return result;

			} catch (Exception e) {
				logger.error("创建最大流水号异常." + e.getMessage(), e);
				return null;
			} finally {
				if (c != null) {
					try {
						c.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(), e);
					}
				}
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
}
