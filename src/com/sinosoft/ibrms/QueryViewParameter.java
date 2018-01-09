package com.sinosoft.ibrms;

import com.sinosoft.framework.data.DBConnPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class QueryViewParameter {
	private static final Logger logger = LoggerFactory.getLogger(QueryViewParameter.class);
	public QueryViewParameter() {

	}

	public String queryForViewParameter(String tableName, String id) {
		String viewParameter = "";
		String sql = "select viewparameter from " + tableName + " where id=?";

		Connection conn = DBConnPool.getConnection();
		try {

			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				viewParameter = rs.getString(1);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
		}
		return viewParameter;
	}
}
