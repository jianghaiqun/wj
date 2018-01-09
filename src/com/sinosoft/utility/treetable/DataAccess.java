/*
 * 创建日期 2005-7-15
 * 作者：王育春
 * 邮箱:wangyc@sinosoft.com.cn
 */

package com.sinosoft.utility.treetable;

import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.utility.SysConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccess {
    private static final Logger logger = LoggerFactory.getLogger(DataAccess.class);
    private Connection conn;

    public DataAccess() {
        this.conn = DBConnPool.getConnection();
    }

    public DataAccess(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return conn;
    }

    public void setAutoCommit(boolean bCommit) throws SQLException {
        this.conn.setAutoCommit(bCommit);
    }

    public void commit() throws SQLException {
        this.conn.commit();
    }

    public void rollback() throws SQLException {
        this.conn.rollback();
    }

    public void close() throws SQLException {
        this.conn.close();
    }

    public DataTable executeDataTable(String sql) {
   
        Statement stmt = null;
        ResultSet rs = null;
        DataTable dt = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //if (rs.next() ) {
                dt = new DataTable(rs);
           // }
        } catch (SQLException e) {
            logger.error("------------DataAccess.executeDataTable:Err-------------" + sql + e.getMessage(), e);
        } finally {
            try {
                rs.close();
                stmt.close();
                rs = null;
                stmt = null;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return dt;
    }

    /**
     * @param sql
     * @param pageSize 每页记录数
     * @param pageIndex 页数，从0开始
     * @return
     */
    public DataTable executePagedDataTable(String sql, int pageSize,
            int pageIndex) {
        if (pageSize < 1) {
            throw new RuntimeException(
                "DataAccess.executePagedDataTable：每页记录数不能小于1！");
        }
        if (pageIndex < 0) {
            throw new RuntimeException(
                "DataAccess.executePagedDataTable：页数不能小于1！");
        }
        String dbName = null;
        try {
            dbName = SysConst.DBTYPE.toLowerCase();//conn.getMetaData().getDatabaseProductName().toLowerCase();
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            throw new RuntimeException("发生错误，未得到数据库的产品名!");
        }
        String tSQL = "";
        if (dbName.indexOf("db2") > -1) {
            tSQL = "select * from (select temp.*,ROW_NUMBER() over() as row___Num "
                    + "from ("
                    + sql
                    + ") as temp) as t where row___Num>"
                    + (pageIndex * pageSize)
                    + " and row___Num<="
                    + ((pageIndex + 1) * pageSize);
        } else if (dbName.indexOf("mysql") > -1) {
            tSQL = tSQL + " limit " + (pageIndex * pageSize) + ","
                    + ((pageIndex + 1) * pageSize);
        } else if (dbName.indexOf("oracle") > -1) {
            tSQL = "select * from (select temp.*,rownum row___Num from  ("
                    + sql + ") temp) where row___Num >"
                    + (pageIndex * pageSize) + " and row___Num<="
                    + ((pageIndex + 1) * pageSize);
        }
        // 其它数据库暂没有实现
        logger.info("------------DataAccess.executePagedDataTable-------------{}", tSQL);
        Statement stmt = null;
        ResultSet rs = null;
        DataTable dt = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tSQL);
            dt = new DataTable(rs);
        } catch (SQLException e) {
            logger.error("------------DataAccess.executeDataTable:Err-------------" + sql + e.getMessage(), e);
        } finally {
            try {
                rs.close();
                stmt.close();
                rs = null;
                stmt = null;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (dbName.indexOf("db2") > -1 || dbName.indexOf("oracle") > -1) {
            dt.deleteColumn("row___Num");
        }
        return dt;
    }

    public Object excuteOneValue(String sql) throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        Object t = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                t = rs.getObject(1);
            }
        } catch (SQLException e) {
            logger.error("------------DataAccess.excuteOneValue:Err-------------" + sql + e.getMessage(), e);
        } finally {
            try {
                rs.close();
                stmt.close();
                rs = null;
                stmt = null;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return t;
    }

    public int executeNoQuery(String sql) throws SQLException {
        logger.info(sql);
        Statement stmt = null;
        int t = -1;
        try {
            stmt = conn.createStatement();
            t = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("------------DataAccess.executeNoQuery:Err-------------" + sql + e.getMessage(), e);
        } finally {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return t;
    }
}
