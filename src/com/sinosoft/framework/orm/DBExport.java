package com.sinosoft.framework.orm;

import com.sinosoft.framework.data.DBUtil;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.BufferedRandomAccessFile;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DBExport {
	private static final Logger logger = LoggerFactory.getLogger(DBExport.class);

	private final static int PageSize = 500;

	private BufferedRandomAccessFile braf;

	private LongTimeTask task;

	public void setTask(LongTimeTask task) {
		this.task = task;
	}

	public void exportDB(String file) {
		FileUtil.delete(file);
		try {
			braf = new BufferedRandomAccessFile(file, "rw");
			String[] arr = SchemaUtil.getAllSchemaClassName();
			for (int i = 0; i < arr.length; i++) {
				try {
					if (task != null) {
						task.setPercent(new Double(i * 100.0 / arr.length).intValue());
						task.setCurrentInfo("正在导出表" + arr[i]);
					}
					transferOneTable(arr[i]);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			// 导出自定义表
			try {
				DataTable dt = new QueryBuilder("select Code,ID from ZCCustomTable where Type='Custom'")
						.executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					transferCustomTable(dt.getString(i, "Code"), dt.getString(i, "ID"));
				}
			} catch (Throwable t) {
				logger.error("系统中没有自定义表" + t.getMessage(), t);
			}
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		} finally {
			if (braf != null) {
				try {
					braf.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	private void transferOneTable(String schemaName) throws Exception {
		Schema schema = (Schema) Class.forName(schemaName).newInstance();
		int count = 0;
		try {
			count = new QueryBuilder("select count(*) from " + schema.TableCode).executeInt();
			for (int i = 0; i * PageSize < count || (i == 0 && count == 0); i++) {
				SchemaSet set = schema.querySet(null, PageSize, i);
				// 先写入名称
				byte[] bs = schemaName.getBytes();
				braf.write(NumberUtil.toBytes(bs.length));
				braf.write(bs);

				bs = FileUtil.serialize(set);
				bs = ZipUtil.zip(bs);
				braf.write(NumberUtil.toBytes(bs.length));
				braf.write(bs);
			}
		} catch (Exception e) {// 没有Schema对应的表
			logger.error("Schema对应的表不存在：" + schemaName + e.getMessage(), e);
			return;
		}
	}

	private void transferCustomTable(String table, String ID) throws Exception {
		int count = 0;
		try {
			String columnName = new QueryBuilder("select Code from ZCCustomTableColumn where TableID=?", ID)
					.executeString();
			QueryBuilder qb = new QueryBuilder("select * from " + table + " order by " + columnName);
			count = DBUtil.getCount(qb);
			for (int i = 0; i * PageSize < count || (i == 0 && count == 0); i++) {
				DataTable dt = qb.executePagedDataTable(PageSize, i);
				// 先写入名称
				byte[] bs = table.getBytes();
				braf.write(NumberUtil.toBytes(bs.length));
				braf.write(bs);

				bs = FileUtil.serialize(dt);
				bs = ZipUtil.zip(bs);
				braf.write(NumberUtil.toBytes(bs.length));
				braf.write(bs);
			}
		} catch (Exception e) {// 没有对应的表
			logger.error("对应的自定义表不存在" + table + ":" + e.getMessage(), e);
			return;
		}
	}
}
