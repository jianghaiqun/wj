package com.sinosoft.framework.orm;

import com.mysql.jdbc.Statement;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.LobUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.CaseIgnoreMapx;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Schema类，对应于数据库中的一条完整的记录<br>
 * 
 */
public abstract class Schema implements Serializable, Cloneable {
	private static final Logger logger = LoggerFactory.getLogger(Schema.class);

	private static final long serialVersionUID = 1L;

	protected String TableCode;

	protected SchemaColumn[] Columns;

	protected String InsertAllSQL;

	protected String UpdateAllSQL;

	protected String FillAllSQL;

	protected String DeleteSQL;

	protected String NameSpace;

	protected int bConnFlag = 0;// 1为连接从外部传入，0为未传入连接

	protected boolean bOperateFlag = false;

	protected int[] operateColumnOrders;

	protected boolean[] HasSetFlag;

	protected transient DataAccess mDataAccess;

	public boolean insert() {
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		PreparedStatement pstmt = null;
		try {
			DBConn conn = mDataAccess.getConnection();
			//LogUtil.info("InsertAllSQL:" + InsertAllSQL);
			pstmt = conn.prepareStatement(InsertAllSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			for (int i = 0; i < Columns.length; i++) {
				SchemaColumn sc = Columns[i];
				if (sc.isMandatory()) {
					if (this.getV(i) == null) {
						throw new SQLException("表" + this.TableCode + "的列" + sc.getColumnName() + "不能为空");
					}
				}
				Object v = getV(i);
				SchemaUtil.setParam(sc, pstmt, conn, i, v);
				//LogUtil.info("位数：" + i + " 列名：" + sc.getColumnName() + "   值：" + v + "  类型：" + sc.getColumnType());
			}
			int count = pstmt.executeUpdate();
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			if (count == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("操作表" + this.TableCode + "时发生错误:" + e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * insertReturnPk:(保存数据并把自增主键值设置到Schema的id属性中). <br/>
	 * TODO(要用此方法插入数据的Schema必须有id属性，且此id对应表字段是自增的).<br/>
	 * @param schema 实际插入的Schema
	 * @return 是否成功
	 * @author guozc
	 */
	public boolean insertReturnPk() {
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			DBConn conn = mDataAccess.getConnection();
			pstmt = conn.prepareStatement(InsertAllSQL, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < Columns.length; i++) {
				SchemaColumn sc = Columns[i];
				if (sc.isMandatory()) {
					if (this.getV(i) == null) {
						throw new SQLException("表" + this.TableCode + "的列" + sc.getColumnName() + "不能为空");
					}
				}
				Object v = getV(i);
				SchemaUtil.setParam(sc, pstmt, conn, i, v);
			}
			int count = pstmt.executeUpdate();
			
			// 设置自增主键属性id
			rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {  
			   int id = rs.getInt(1);
			   Field field = this.getClass().getDeclaredField("id");
			   field.setAccessible(true);
			   field.set(this, id);
			}  
			
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			if (count == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("操作表" + this.TableCode + "时发生错误:" + e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public boolean update() {
		long current = System.currentTimeMillis();
		// 检查是否满足update的条件，即主键是否已经置值
		String sql = UpdateAllSQL;
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		if (bOperateFlag) {
			StringBuffer sb = new StringBuffer("update " + TableCode + " set ");
			for (int i = 0; i < operateColumnOrders.length; i++) {
				if (i != 0) {
					sb.append(",");
				}
				String columnName = Columns[operateColumnOrders[i]].getColumnName();
				sb.append(columnName);
				sb.append("=?");
			}
			sb.append(sql.substring(sql.indexOf(" where")));
			sql = sb.toString();
		}
		if (mDataAccess.getConnection().getDBConfig().isSybase()) {// Sybase下的临时措施，将来要改掉
			sql = StringUtil.replaceAllIgnoreCase(sql, " Count=", "\"Count\"=");
			sql = StringUtil.replaceAllIgnoreCase(sql, " Scroll=", "\"Scroll\"=");
			sql = StringUtil.replaceAllIgnoreCase(sql, ",Count=", ",\"Count\"=");
			sql = StringUtil.replaceAllIgnoreCase(sql, ",Scroll=", ",\"Scroll\"=");
		}
		PreparedStatement pstmt = null;
		try {
			DBConn conn = mDataAccess.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (bOperateFlag) {
				for (int i = 0; i < operateColumnOrders.length; i++) {
					Object v = getV(operateColumnOrders[i]);
					SchemaUtil.setParam(Columns[operateColumnOrders[i]], pstmt, conn, operateColumnOrders[i], v);
				}
			} else {
				for (int i = 0; i < Columns.length; i++) {
					Object v = getV(i);
					SchemaUtil.setParam(Columns[i], pstmt, conn, i, v);
				}
			}
			int pkIndex = 0;// 第几个主键
			for (int i = 0, j = 0; i < Columns.length; i++) {
				SchemaColumn sc = Columns[i];
				if (sc.isPrimaryKey()) {
					Object v = getV(sc.getColumnOrder());
					if (OldKeys != null) {
						v = OldKeys[pkIndex++];
					}
					if (v == null) {
						logger.warn("不满足Update的条件，{}Schema的{}为空", TableCode, sc.getColumnName());
						return false;
					} else {
						if (bOperateFlag) {
							SchemaUtil.setParam(Columns[i], pstmt, conn, j + operateColumnOrders.length, v);
						} else {
							SchemaUtil.setParam(Columns[i], pstmt, conn, j + Columns.length, v);
						}
					}
					j++;
				}
			}
			pstmt.executeUpdate();
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			if (Config.isDebugLoglevel()) {
				long time = System.currentTimeMillis() - current;
				logger.info("{}ms\t{}", time, sql);
			}
			return true;
		} catch (Exception e) {
			logger.error("操作表" + this.TableCode + "时发生错误:" + e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public boolean delete() {
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		PreparedStatement pstmt = null;
		try {
			DBConn conn = mDataAccess.getConnection();
			pstmt = conn.prepareStatement(DeleteSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int pkIndex = 0;
			for (int i = 0, j = 0; i < Columns.length; i++) {
				SchemaColumn sc = Columns[i];
				if (sc.isPrimaryKey()) {
					Object v = getV(sc.getColumnOrder());
					if (OldKeys != null) {
						v = OldKeys[pkIndex++];
					}
					if (v == null) {
						logger.warn("不满足delete的条件，{}Schema的{}为空", TableCode, sc.getColumnName());
						return false;
					} else {
						SchemaUtil.setParam(Columns[i], pstmt, conn, j, v);
					}
					j++;
				}
			}
			pstmt.executeUpdate();
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			return true;
		} catch (Exception e) {
			logger.error("操作表" + this.TableCode + "时发生错误:" + e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public boolean backup() {
		return backup(null, null);
	}

	public boolean backup(String backupOperator, String backupMemo) {
		try {
			backupOperator = StringUtil.isEmpty(backupOperator) ? User.getUserName() : backupOperator;
			backupOperator = StringUtil.isEmpty(backupOperator) ? "SYSTEM" : backupOperator;

			Class c = Class.forName("com.sinosoft.schema.B" + this.TableCode + "Schema");
			Schema bSchema = (Schema) c.newInstance();
			int i = 0;
			for (; i < this.Columns.length; i++) {
				bSchema.setV(i, this.getV(i));
			}
			bSchema.setV(i, SchemaUtil.getBackupNo());
			bSchema.setV(i + 1, backupOperator);
			bSchema.setV(i + 2, new Date());
			bSchema.setV(i + 3, backupMemo);
			if (bConnFlag == 0) {
				return bSchema.insert();
			} else {
				bSchema.setDataAccess(mDataAccess);
				return bSchema.insert();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean deleteAndInsert() {
		if (bConnFlag == 1) {
			if (!delete()) {
				return false;
			}
			return insert();
		} else {
			mDataAccess = new DataAccess();
			bConnFlag = 1;
			try {
				mDataAccess.setAutoCommit(false);
				delete();
				insert();
				mDataAccess.commit();
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				try {
					mDataAccess.rollback();
				} catch (SQLException e1) {
					logger.error(e1.getMessage(), e1);
				}
				return false;
			} finally {
				try {
					mDataAccess.setAutoCommit(true);
					mDataAccess.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				this.mDataAccess = null;
				this.bConnFlag = 0;
			}
		}
	}

	public boolean deleteAndBackup() {
		return deleteAndBackup(null, null);
	}

	public boolean deleteAndBackup(String backupOperator, String backupMemo) {
		try {
			backupOperator = StringUtil.isEmpty(backupOperator) ? User.getRealName() : backupOperator;
			backupOperator = StringUtil.isEmpty(backupOperator) ? "SYSTEM" : backupOperator;

			Class c = Class.forName("com.sinosoft.schema.B" + this.TableCode + "Schema");
			Schema bSchema = (Schema) c.newInstance();
			int i = 0;
			for (; i < this.Columns.length; i++) {
				bSchema.setV(i, this.getV(i));
			}
			bSchema.setV(i, SchemaUtil.getBackupNo());
			bSchema.setV(i + 1, backupOperator);
			bSchema.setV(i + 2, new Date());
			bSchema.setV(i + 3, backupMemo);
			if (bConnFlag == 0) {
				mDataAccess = new DataAccess();
				bConnFlag = 1;
				try {
					mDataAccess.setAutoCommit(false);
					delete();
					bSchema.setDataAccess(mDataAccess);
					bSchema.insert();
					mDataAccess.commit();
					return true;
				} catch (SQLException e) {
					logger.error("操作表" + this.TableCode + "时发生错误:" + e.getMessage(), e);
					try {
						mDataAccess.rollback();
					} catch (SQLException e1) {
						logger.error(e1.getMessage(), e1);
					}
					return false;
				} finally {
					bConnFlag = 0;
					try {
						mDataAccess.setAutoCommit(true);
						mDataAccess.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(), e);
					}
				}
			} else {
				if (!delete()) {
					return false;
				}
				bSchema.setDataAccess(mDataAccess);
				return bSchema.insert();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean fill() {
		// 检查是否满足fill的条件，即主键是否已经置值
		String sql = FillAllSQL;
		if (bOperateFlag) {
			StringBuffer sb = new StringBuffer("select ");
			for (int i = 0; i < operateColumnOrders.length; i++) {
				if (i == 0) {
					sb.append(Columns[operateColumnOrders[i]].getColumnName());
				} else {
					sb.append(",");
					sb.append(Columns[operateColumnOrders[i]].getColumnName());
				}
			}
			sb.append(sql.substring(sql.indexOf(" from")));
			sql = sb.toString();
		}
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			DBConn conn = mDataAccess.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			for (int i = 0, j = 0; i < Columns.length; i++) {
				SchemaColumn sc = Columns[i];
				if (sc.isPrimaryKey()) {
					Object v = getV(sc.getColumnOrder());
					if (v == null) {
						throw new RuntimeException("不满足fill的条件，" + TableCode + "Schema的" + sc.getColumnName() + "为空");
					} else {
						SchemaUtil.setParam(Columns[i], pstmt, conn, j, v);
					}
					j++;
				}
			}
			rs = pstmt.executeQuery();
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			if (rs.next()) {
				setVAll(conn, this, rs);
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("操作表" + this.TableCode + "时发生错误:" + e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null;
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				rs = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public SchemaSet querySet(QueryBuilder qb, int pageSize, int pageIndex) {
		// 检查哪些字段有值
		if (qb != null) {
			if (!qb.getSQL().trim().toLowerCase().startsWith("where")) {
				throw new RuntimeException("QueryBuilder中的SQL语句不是以where开头的字符串");
			}
		}
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		DBConn conn = mDataAccess.getConnection();
		StringBuffer sb = new StringBuffer("select ");
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			if (bOperateFlag) {
				for (int i = 0; i < operateColumnOrders.length; i++) {
					if (i != 0) {
						sb.append(",");
					}
					sb.append(Columns[operateColumnOrders[i]].getColumnName());
				}
			} else {
				if (conn.getDBConfig().isSQLServer()) {// SQL
					// Server2005的分页必须有order by
					for (int i = 0; i < this.Columns.length; i++) {
						if (i != 0) {
							sb.append(",");
						}
						sb.append(Columns[i].getColumnName());
					}
				} else {
					sb.append("*");
				}
			}
			sb.append(" from " + TableCode);
			if (qb == null) {
				boolean firstFlag = true;
				qb = new QueryBuilder();
				for (int i = 0; i < Columns.length; i++) {
					SchemaColumn sc = Columns[i];
					if (!isNull(sc)) {
						if (firstFlag) {
							sb.append(" where ");
							sb.append(sc.getColumnName());
							sb.append("=?");
							firstFlag = false;
						} else {
							sb.append(" and ");
							sb.append(sc.getColumnName());
							sb.append("=?");
						}
						Object v = getV(sc.getColumnOrder());
						qb.add(v);
					}
				}
			} else {
				sb.append(" ");
				sb.append(qb.getSQL());

			}
			qb.setSQL(sb.toString());
			String pageSQL = qb.getSQL();
			logger.info("pageSQL:{}", pageSQL);
			if (pageSize > 0) {
				pageSQL = DataAccess.getPagedSQL(conn, qb, pageSize, pageIndex);
			}
			// LogUtil.getLogger().info(qb.toString());
			pageIndex = pageIndex < 0 ? 0 : pageIndex;

			pstmt = conn.prepareStatement(pageSQL);
			DataAccess.setParams(pstmt, qb, conn);
			rs = pstmt.executeQuery();

			// 必须清除后两项参数，以便Schema能够继续执行query(pageSize,pageIndex)
			if (pageSize > 0 && !conn.getDBConfig().isSQLServer2000() && !conn.getDBConfig().isSybase()) {
				qb.getParams().remove(qb.getParams().size() - 1);
				qb.getParams().remove(qb.getParams().size() - 1);
			}

			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			SchemaSet set = this.newSet();
			while (rs.next()) {
				Schema schema = this.newInstance();
				setVAll(conn, schema, rs);
				set.add(schema);
			}
			set.setOperateColumns(operateColumnOrders);
			return set;
		} catch (Exception e) {
			logger.error("操作表" + this.TableCode + "时发生错误:" + e.getMessage(), e);
			return null;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				pstmt = null;
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				rs = null;
			}
			if (bConnFlag == 0) {
				conn = null;
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	private void setVAll(DBConn conn, Schema schema, ResultSet rs) throws Exception {
		ArrayList pklist = new ArrayList(4);
		boolean latin1Flag = conn.getDBConfig().isLatin1Charset && conn.getDBConfig().isOracle();
		if (bOperateFlag) {
			for (int i = 0; i < operateColumnOrders.length; i++) {
				int order = operateColumnOrders[i];
				if (Columns[order].getColumnType() == DataColumn.CLOB) {
					if (conn.getDBConfig().isOracle() || conn.getDBConfig().isDB2()) {
						schema.setV(order, LobUtil.clobToString(rs.getClob(i + 1)));
					} else if (conn.getDBConfig().isSybase()) {
						String str = rs.getString(i + 1);
						if (str.equals(" ")) {// sybase会将空字符串转换成空格
							str = "";
						}
						schema.setV(order, str);
					} else {
						schema.setV(order, rs.getObject(i + 1));
					}
				} else if (Columns[order].getColumnType() == DataColumn.BLOB) {
					schema.setV(order, LobUtil.blobToBytes(rs.getBlob(i + 1)));
				} else {
					schema.setV(order, rs.getObject(i + 1));
				}
				if (Columns[order].getColumnType() == DataColumn.CLOB || Columns[order].getColumnType() == DataColumn.STRING) {
					String str = (String) schema.getV(order);
					if (latin1Flag && StringUtil.isNotEmpty(str)) {
						try {
							str = new String(str.getBytes("ISO-8859-1"), Constant.GlobalCharset);
						} catch (UnsupportedEncodingException e) {
							logger.error(e.getMessage(), e);
						}
					}
					schema.setV(order, str);
				}
				if (Columns[order].isPrimaryKey()) {
					pklist.add(schema.getV(order));
				}
			}
		} else {
			for (int i = 0; i < Columns.length; i++) {
				if (Columns[i].getColumnType() == DataColumn.CLOB) {
					if (conn.getDBConfig().isOracle() || conn.getDBConfig().isDB2()) {
						schema.setV(i, LobUtil.clobToString(rs.getClob(i + 1)));
					} else if (conn.getDBConfig().isSybase()) {
						String str = rs.getString(i + 1);
						if (str.equals(" ")) {// sybase会将空字符串转换成空格
							str = "";
						}
						schema.setV(i, str);
					} else {
						schema.setV(i, rs.getObject(i + 1));
					}
				} else if (Columns[i].getColumnType() == DataColumn.BLOB) {
					schema.setV(i, LobUtil.blobToBytes(rs.getBlob(i + 1)));
				} else {
					schema.setV(i, rs.getObject(i + 1));
				}
				if (Columns[i].getColumnType() == DataColumn.CLOB || Columns[i].getColumnType() == DataColumn.STRING) {
					String str = (String) schema.getV(i);
					if (latin1Flag && StringUtil.isNotEmpty(str)) {
						try {
							str = new String(str.getBytes("ISO-8859-1"), Constant.GlobalCharset);
						} catch (UnsupportedEncodingException e) {
							logger.error(e.getMessage(), e);
						}
					}
					schema.setV(i, str);
				}
				if (Columns[i].isPrimaryKey()) {
					pklist.add(schema.getV(i));
				}
			}
		}
		schema.OldKeys = pklist.toArray();
	}

	public void setOperateColumns(String[] colNames) {
		if (colNames == null || colNames.length == 0) {
			bOperateFlag = false;
			return;
		}
		operateColumnOrders = new int[colNames.length];
		for (int i = 0, k = 0; i < colNames.length; i++) {
			boolean flag = false;
			for (int j = 0; j < Columns.length; j++) {
				if (colNames[i].toString().toLowerCase().equals(Columns[j].getColumnName().toLowerCase())) {
					operateColumnOrders[k] = j;
					k++;
					flag = true;
					break;
				}
			}
			if (!flag) {
				throw new RuntimeException("指定的列名" + colNames[i] + "不正确");
			}
		}
		bOperateFlag = true;
	}

	public void setOperateColumns(int[] colOrder) {
		if (colOrder == null || colOrder.length == 0) {
			bOperateFlag = false;
			return;
		}
		operateColumnOrders = colOrder;
		bOperateFlag = true;
	}

	public void setDataAccess(DataAccess dAccess) {
		mDataAccess = dAccess;
		bConnFlag = 1;
	}

	protected boolean isNull(SchemaColumn sc) {
		return StringUtil.isEmpty(getV(sc.getColumnOrder()));
	}

	/**
	 * 将DataCollection中的键值对按名称自动匹配到Schema的字段中去
	 */
	public void setValue(Mapx map) {// 主要用于从前台传值到Schema时自动区配
		Object value = null;
		Object key = null;
		Object[] ks = map.keyArray();
		Object[] vs = map.valueArray();
		for (int i = 0; i < map.size(); i++) {
			value = vs[i];
			key = ks[i];
			for (int j = 0; j < Columns.length; j++) {
				SchemaColumn sc = Columns[j];
				if (key != null && key.toString().equalsIgnoreCase(sc.getColumnName())) {
					try {
						int type = sc.getColumnType();
						if (type == DataColumn.DATETIME) {
							if (value != null && !"".equals(value)) {
								setV(j, DateUtil.parseDateTime(value.toString()));
							}
						} else if (type == DataColumn.DOUBLE) {
							setV(j, new Double(value.toString()));
						} else if (type == DataColumn.FLOAT) {
							setV(j, new Float(value.toString()));
						} else if (type == DataColumn.LONG) {
							setV(j, new Long(value.toString()));
						} else if (type == DataColumn.INTEGER) {
							setV(j, new Integer(value.toString()));
						} else {
							setV(j, value);
						}
						break;
					} catch (Exception e) {
						// logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * 将DataCollection中的键值对按名称自动匹配到Schema的字段中去
	 */
	public void setValue(DataCollection dc) {// 主要用于从前台传值到Schema时自动区配
		String value = null;
		String key = null;
		Object[] ks = dc.keyArray();
		Object[] vs = dc.valueArray();
		for (int i = 0; i < dc.size(); i++) {
			if (!(vs[i] instanceof String) && vs[i] != null) {
				continue;
			}
			value = (String) vs[i];
			key = (String) ks[i];
			for (int j = 0; j < Columns.length; j++) {
				SchemaColumn sc = Columns[j];
				if (key.equalsIgnoreCase(sc.getColumnName())) {
					try {
						int type = sc.getColumnType();
						if (type == DataColumn.DATETIME) {
							if (value != null && !"".equals(value)) {
								if (DateUtil.isTime(value.toString())) {
									setV(j, DateUtil.parseDateTime(value.toString(), "HH:mm:ss"));
								} else {
									setV(j, DateUtil.parseDateTime(value.toString()));
								}
							}
						} else if (type == DataColumn.DOUBLE) {
							setV(j, new Double(value.toString()));
						} else if (type == DataColumn.BIGDECIMAL) {
							setV(j, new BigDecimal(value.toString()));
						} else if (type == DataColumn.FLOAT) {
							setV(j, new Float(value.toString()));
						} else if (type == DataColumn.LONG) {
							setV(j, new Long(value.toString()));
						} else if (type == DataColumn.INTEGER) {
							setV(j, new Integer(value.toString()));
						} else {
							setV(j, value);
						}
						break;
					} catch (Exception e) {
						// logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * 将DataRow中的字段值对按名称自动匹配到Schema的字段中去
	 */
	public void setValue(DataRow dr) {
		String value = null;
		String key = null;
		boolean webMode = dr.isWebMode();
		dr.setWebMode(false);
		for (int i = 0; i < dr.getColumnCount(); i++) {
			value = dr.getString(i);
			key = dr.getDataColumns()[i].getColumnName();
			for (int j = 0; j < Columns.length; j++) {
				SchemaColumn sc = Columns[j];
				if (key.equalsIgnoreCase(sc.getColumnName())) {
					try {
						int type = sc.getColumnType();
						if (type == DataColumn.DATETIME) {
							if (value != null && !"".equals(value)) {
								setV(j, DateUtil.parseDateTime(value.toString()));
							}
						} else if (type == DataColumn.DOUBLE) {
							setV(j, new Double(value.toString()));
						} else if (type == DataColumn.FLOAT) {
							setV(j, new Float(value.toString()));
						} else if (type == DataColumn.LONG) {
							setV(j, new Long(value.toString()));
						} else if (type == DataColumn.INTEGER) {
							setV(j, new Integer(value.toString()));
						} else {
							setV(j, value);
						}
						break;
					} catch (Exception e) {
						// logger.error(e.getMessage(), e);
					}
				}
			}
		}
		dr.setWebMode(webMode);
	}

	/**
	 * 主要是调试时用，便于在Eclipse的调试界面直接看Schema里的内容
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < Columns.length; i++) {
			sb.append(Columns[i].getColumnName());
			sb.append(":");
			sb.append(getV(i));
			sb.append(" ");
		}
		sb.append("}");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		Schema s = this.newInstance();
		SchemaUtil.copyFieldValue(this, s);
		return s;
	}

	/**
	 * 将Schema转化为Mapx
	 */
	public Mapx toMapx() {
		Mapx map = new Mapx();
		for (int i = 0; i < Columns.length; i++) {
			map.put(Columns[i].getColumnName(), getV(i));
		}
		return map;
	}

	/**
	 * 将Schmea转化小键名不区分大小写的Mapx
	 */
	public Mapx toCaseIgnoreMapx() {
		return new CaseIgnoreMapx(toMapx());
	}

	/**
	 * 将Schema转化为DataRow
	 */
	public DataRow toDataRow() {
		int len = Columns.length;
		DataColumn[] dcs = new DataColumn[len];
		Object[] values = new Object[len];
		for (int i = 0; i < len; i++) {
			DataColumn dc = new DataColumn();
			dc.setColumnName(Columns[i].getColumnName());
			dc.setColumnType(Columns[i].getColumnType());
			dcs[i] = dc;
			values[i] = getV(i);
		}
		return new DataRow(dcs, values);
	}

	/**
	 * 获得Schema对应的数据表的字段数
	 */
	public int getColumnCount() {
		/* ${_ZVING_LICENSE_CODE_} */
		return Columns.length;
	}

	protected Object[] OldKeys;

	/**
	 * 获取旧的主键值，仅在fill()/query()方法调用后可以取到值，否则为null.
	 */
	protected Object[] getOldKeys() {
		return OldKeys;
	}

	/**
	 * 将第i个字段的值设为v
	 */
	public abstract void setV(int i, Object v);

	/**
	 * 获取第i个字段的值
	 */
	public abstract Object getV(int i);

	/**
	 * 初始化一个新的Schema实例，本方法在子类中实现
	 */
	protected abstract Schema newInstance();

	/**
	 * 初始化一个新的Set实例，本方法在子类中实现
	 */
	protected abstract SchemaSet newSet();
}
