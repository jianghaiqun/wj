package com.sinosoft.schema;

import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaUtil;
import com.sinosoft.framework.utility.LogUtil;

public class financialsettlementdetailsSet extends SchemaSet {
	public financialsettlementdetailsSet() {
		this(10,0);
	}

	public financialsettlementdetailsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public financialsettlementdetailsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = financialsettlementdetailsSchema._TableCode;
		Columns = financialsettlementdetailsSchema._Columns;
		NameSpace = financialsettlementdetailsSchema._NameSpace;
		InsertAllSQL = financialsettlementdetailsSchema._InsertAllSQL;
		UpdateAllSQL = financialsettlementdetailsSchema._UpdateAllSQL;
		FillAllSQL = financialsettlementdetailsSchema._FillAllSQL;
		DeleteSQL = financialsettlementdetailsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new financialsettlementdetailsSet();
	}

	public boolean add(financialsettlementdetailsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(financialsettlementdetailsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(financialsettlementdetailsSchema aSchema) {
		return super.remove(aSchema);
	}

	public financialsettlementdetailsSchema get(int index) {
		financialsettlementdetailsSchema tSchema = (financialsettlementdetailsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, financialsettlementdetailsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(financialsettlementdetailsSet aSet) {
		return super.set(aSet);
	}

	@Override
	public boolean insert() {

		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		PreparedStatement pstmt = null;
		try {	
			DBConn conn = mDataAccess.getConnection();
			boolean autoComit = conn.getAutoCommit();
			if (bConnFlag == 0) {
				conn.setAutoCommit(false);
			}
			pstmt = conn.prepareStatement(InsertAllSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			for (int k = 0; k < size(); k++) {
				for (int i = 0; i < Columns.length; i++) {
					Schema schema = getObject(k);
					SchemaColumn sc = Columns[i];
					if (sc.isMandatory()) {
						if (schema.getV(i) == null) {
							LogUtil.warn("表" + this.TableCode + "的列" + sc.getColumnName() + "不能为空");
							return false;
						}
					}
					Object v = schema.getV(i);
					SchemaUtil.setParam(sc, pstmt, conn, i, v);
				}
				pstmt.addBatch();
				if (bConnFlag == 1 && (k + 1) % 200 == 0) {
					pstmt.executeBatch();
					conn.commit();
				}
			}
			pstmt.executeBatch();
			if (bConnFlag == 0) {
				conn.commit();
				conn.setAutoCommit(autoComit);
			}
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			return true;
		} catch (Throwable e) {
			// 批量增加时发生异常很难调试，例如字段超长，主键冲突等，需要输出整个SchemaSet到日志中
			if (e instanceof BatchUpdateException) {
				LogUtil.warn(this.toDataTable());
			}
			LogUtil.warn("操作表" + this.TableCode + "时发生错误:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				pstmt = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
 