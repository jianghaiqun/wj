package com.sinosoft.cms.pub;

import com.sinosoft.cms.pub.SiteTableRela.TableRela;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DBUtil;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 导入导出整个站点,包括模板.<br>
 * 默认将从schema目录下含有SiteID字段的表导出
 */
public class SiteExporter {
	private static final Logger logger = LoggerFactory.getLogger(SiteExporter.class);
	private int PageSize = 1000;

	private OutputStream os;

	private Mapx relaMap = new Mapx();

	private long siteID;

	private boolean isExportMediaFile = true;

	public SiteExporter(long siteID) {
		this.siteID = siteID;
	}

	public SiteExporter(long siteID, boolean isExportMediaFile) {
		this.siteID = siteID;
		this.isExportMediaFile = isExportMediaFile;
	}

	/**
	 * 增加关联表的导出SQL.
	 */
	public void addRelaTable(String sql) {
		relaMap.put(sql, "");
	}

	public boolean exportSite(String file) {
		try {
			FileUtil.delete(file);
			os = new FileOutputStream(file);
			return exportSite();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public boolean exportSite(HttpServletResponse response) {
		try {
			os = response.getOutputStream();
			return exportSite();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public boolean exportSite() {
		try {
			os.write(Constant.GlobalCharset.equals("GBK") ? 1 : 2);
			byte[] bs = NumberUtil.toBytes(siteID);
			os.write(bs);
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
			return false;
		}
		boolean flag = true;

		TableRela[] trs = SiteTableRela.getRelas();
		String[] tables = SiteTableRela.getSiteIDTables();
		// 导出顺序，ZCSite->带SiteID的表->关联表，导入时将依赖于这个顺序
		QueryBuilder qb = new QueryBuilder("select * from ZCSite where ID=?", siteID);
		try {
			transferSQL(qb, "ZCSite");
		} catch (Exception e) {
			flag = false;
			logger.error(e.getMessage(), e);
		}
		if (!flag) {
			try {
				os.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		for (int i = 0; i < tables.length; i++) {
			if (tables[i].toLowerCase().startsWith("b")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcdeploy")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcstatitem")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcvisitlog")) {
				continue;
			}
			qb = SiteTableRela.getSelectQuery(siteID, tables[i]);
			if (tables[i].toLowerCase().equals("zccatalog")) {
				qb.append(" order by innercode");
			}
			if (tables[i].equalsIgnoreCase("ZCArticle")) {
				PageSize = 100;
			} else {
				PageSize = 1000;
			}
			try {
				transferSQL(qb, tables[i]);
			} catch (Exception e) {
				flag = false;
				logger.error(e.getMessage(), e);
			}
		}
		for (int i = 0; i < trs.length; i++) {
			if (trs[i].TableCode.toLowerCase().startsWith("b")) {
				continue;
			}
			if (!trs[i].isExportData) {
				continue;
			}
			if (!SiteTableRela.hasSiteIDField(trs[i].RelaTable)) {// 有些关联表没有SiteID但有关联关系，如ZDColumn
				continue;
			}
			qb = SiteTableRela.getSelectQuery(siteID, trs[i]);
			try {
				transferSQL(qb, trs[i]);
			} catch (Exception e) {
				flag = false;
				logger.error(e.getMessage(), e);
			}
		}
		// 导出自定义表
		DataTable dt = new QueryBuilder("select Code from ZCCustomTable where Type='Custom' and SiteID=?", siteID)
				.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String code = dt.getString(i, 0);
			transferCustomTable(code);
		}
		if (!flag) {
			return false;
		}
		if (this.isExportMediaFile) {
			flag = exportFile();
		}
		return flag;
		// 导出模板文件,暂无
	}

	private void transferCustomTable(String table) {
		int count = 0;
		try {
			QueryBuilder qb = new QueryBuilder("select * from " + table);
			count = DBUtil.getCount(qb);
			for (int i = 0; i * PageSize < count || (i == 0 && count == 0); i++) {
				DataTable dt = qb.executePagedDataTable(PageSize, i);
				// 先写入名称
				String name = "CustomTable:" + table;
				byte[] bs = FileUtil.serialize(name);
				os.write(NumberUtil.toBytes(bs.length));
				os.write(bs);

				bs = FileUtil.serialize(dt);
				bs = ZipUtil.zip(bs);
				os.write(NumberUtil.toBytes(bs.length));
				os.write(bs);
			}
		} catch (Exception e) {// 没有Schema对应的表
			logger.error("对应的自定义表不存在：" + table + e.getMessage(), e);
			return;
		}
	}

	private boolean exportFile() {
		String path = SiteUtil.getAbsolutePath(siteID);
		try {
			exportDir(new File(path));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private void exportDir(File parent) throws IOException {
		String root = SiteUtil.getAbsolutePath(siteID);
		root = root.replaceAll("[\\\\/]+", "/");
		File[] fs = parent.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (f.getName().equals(".svn")) {
				continue;
			}
			if (f.getName().equals(".shtml")) {
				continue;
			}
			if (f.isDirectory()) {
				exportDir(f);
			} else {
				byte[] bs = FileUtil.readByte(f);
				if (bs != null) {
					bs = ZipUtil.zip(bs);
					String path = f.getAbsolutePath();
					path = path.replaceAll("[\\\\/]+", "/");
					path = path.substring(root.length());
					path = "File:" + path;// 加前缀以区别于表名
					byte[] nbs = FileUtil.serialize(path);
					os.write(NumberUtil.toBytes(nbs.length));
					os.write(nbs);
					os.write(NumberUtil.toBytes(bs.length));
					os.write(bs);
					os.flush();
				}
			}
		}
	}

	private void transferSQL(QueryBuilder qb, Serializable obj) throws Exception {
		int count = DBUtil.getCount(qb);
		for (int i = 0; i * PageSize < count || (i == 0 && count == 0); i++) {
			DataTable dt = qb.executePagedDataTable(PageSize, i);
			// 写入关系对象
			byte[] bs = FileUtil.serialize(obj);
			os.write(NumberUtil.toBytes(bs.length));
			os.write(bs);

			// 写入DataTable
			bs = FileUtil.serialize(dt);
			bs = ZipUtil.zip(bs);
			os.write(NumberUtil.toBytes(bs.length));
			os.write(bs);
			os.flush();
		}
	}

	public static void main(String[] args) {
		SiteExporter se = new SiteExporter(206);
		se.exportSite("G:/zving.dat");
	}
}
