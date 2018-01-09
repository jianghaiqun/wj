package com.sinosoft.platform.pub;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDMaxNoSchema;
import com.sinosoft.schema.ZDMaxNoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoUtil {
	private static final Logger logger = LoggerFactory.getLogger(NoUtil.class);

	private static ZDMaxNoSet MaxNoSet;
	private static Object mutex = new Object();

	public static String getMaxNo(String noType) {
		return getMaxNo(noType, "SN");
	}

	public static String getMaxNoLoal(String noType) {
		return getMaxNo(noType, "SN");
	}

	public static long getMaxID(String noType, String subType) {
		// 注释掉本段逻辑，统一使用lock方式。在本地模式下，如果有多个应用同时部署在生产环境中，会导致主键冲突
		// if (Config.isDebugMode()) {
		return getMaxIDUseLock(noType, subType);
		// }
		// return getMaxIDLocal(noType, subType);
	}

	public synchronized static long getMaxIDUseLock(String noType, String subType) {
		DBConn conn = DBConnPool.getConnection("Default", false, false);// 不能使用阻塞型事务中的连接
		DataAccess da = new DataAccess(conn);
		try {
			da.setAutoCommit(false);
			QueryBuilder qb = new QueryBuilder("select a.MaxValue from ZDMaxNo a where a.NoType=? and a.NoSubType=?", noType, subType);
			if (Config.isOracle()) {
				qb.append(" for update");
			}
			Object maxValue = da.executeOneValue(qb);
			if (maxValue != null) {
				long t = Long.parseLong(maxValue.toString()) + 1;
				qb = new QueryBuilder("update ZDMaxNo a set a.MaxValue=? where a.NoType=? and a.NoSubType=?", t, noType);
				qb.add(subType);
				da.executeNoQuery(qb);
				da.commit();
				return t;
			} else {
				ZDMaxNoSchema maxno = new ZDMaxNoSchema();
				maxno.setNoType(noType);
				maxno.setNoSubType(subType);
				maxno.setMaxValue(1);
				maxno.setLength(10);
				maxno.setDataAccess(da);
				if (maxno.insert()) {
					da.commit();
					return 1;
				} else {
					throw new RuntimeException("获取最大号时发生错误!");
				}
			}
		} catch (Exception e) {
			try {
				da.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
			throw new RuntimeException("获取最大号时发生错误:" + e.getMessage());
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 得到类型为noType位长为length的编码
	 */
	public static String getMaxNo(String noType, int length) {
		long t = getMaxID(noType, "SN");
		String no = String.valueOf(t);
		if (no.length() > length) {
			return no.substring(0, length);
		}
		return StringUtil.leftPad(no, '0', length);
	}

	/**
	 * 得到类型为noType位长为length且前缀为prefix的编码
	 */
	public static String getMaxNo(String noType, String prefix, int length) {
		long t = getMaxID(noType, prefix);
		String no = String.valueOf(t);
		if (no.length() > length) {
			return no.substring(0, length);
		}
		return prefix + StringUtil.leftPad(no, '0', length);
	}

	public synchronized static String getMaxNoUseLock(String noType, String subType) {
		DataAccess da = new DataAccess();
		try {
			da.setAutoCommit(false);
			QueryBuilder qb = new QueryBuilder("select `MaxValue`,`Length` from ZDMaxNo where NoType = ? and NoSubType = ?", noType, subType);
			if (Config.isOracle()) {
				qb.append(" for update");
			}
			DataTable dt = qb.executeDataTable();
			if (dt.getRowCount() > 0) {
				long t = Long.parseLong(dt.getString(0, "MaxValue")) + 1;
				int length = Integer.parseInt(dt.getString(0, "Length"));
				String no = String.valueOf(t);
				if (length > 0) {
					no = StringUtil.leftPad(no, '0', length);
				}
				qb = new QueryBuilder("update ZDMaxNo set `MaxValue`=? where NoType=? and NoSubType=?", t, noType);
				qb.add(subType);
				da.executeNoQuery(qb);
				da.commit();
				return no;
			} else {
				ZDMaxNoSchema maxno = new ZDMaxNoSchema();
				maxno.setNoType(noType);
				maxno.setNoSubType(subType);
				maxno.setMaxValue(1);
				maxno.setLength(10);
				maxno.setDataAccess(da);
				if (maxno.insert()) {
					da.commit();
					return "0000000001";
				} else {
					throw new RuntimeException("获取最大号时发生错误!");
				}
			}
		} catch (Exception e) {
			try {
				da.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
			throw new RuntimeException("获取最大号时发生错误:" + e.getMessage());
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public static long getMaxID(String noType) {
		return getMaxID(noType, "ID");
	}

	public static long getSDSearchCacheID() {
		return getMaxID("SDSearchCacheID");
	}


	public static long getMaxIDLocal(String noType) {
		return getMaxID(noType, "ID");
	}

	public static String getMaxNo(String noType, String subType) {
			return getMaxNoUseLock(noType, subType);
	}

	private static synchronized void init() {
		if (MaxNoSet != null) {
			return;
		}
		ZDMaxNoSchema maxno = new ZDMaxNoSchema();
		MaxNoSet = maxno.query();
	}
	
	/**
	 * 华泰产品-根据产品ID生成流水号
	 * 
	 * @param productID
	 * @return
	 */
	public static String HtSerialNO(String productID) {
		try{
			// InsuranceCode(4位)：依照具体产品
			if (StringUtil.isEmpty(productID) || productID.length() != 4) {
				logger.error("华泰产品生成流水号异常，产品编码为空或者长度不对，请核实！{}", productID);
				return null;
			}
	
			// 年份末两未：如2011取11，2014取14
			SimpleDateFormat sdf = new SimpleDateFormat("yy");
			String yy = sdf.format(new Date());
			if (StringUtil.isEmpty(yy) || yy.length() != 2) {
				logger.error("华泰产品生成流水号异常，生成年份信息异常，请核实！{}", yy);
				return null;
			}
	
			// 根据产品，年份生成6为流水号
			String HtSerialNO = getMaxNo("HtSerialNO" + productID + yy, 6);
			if (StringUtil.isEmpty(HtSerialNO) || HtSerialNO.length() != 6) {
				logger.error("华泰产品生成流水号异常，生成流水号信息异常，请核实！(HtSerialNO{}){}",productID + yy, HtSerialNO);
				return null;
			}
			
			return "91196" + productID + "27" + yy + HtSerialNO;
		} catch (Exception e) {
			logger.error("华泰产品生成流水好异常，请核实！" + productID + e.getMessage(), e);
			return null;
		}
	}
}
