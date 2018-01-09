package com.sinosoft.cms.pub;

import com.sinosoft.cms.pub.SiteTableRela.NoType;
import com.sinosoft.cms.pub.SiteTableRela.TableRela;
import com.sinosoft.cms.resource.ConfigImageLib;
import com.sinosoft.cms.site.Site;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.data.BlockingTransaction;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.license.LicenseInfo;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaUtil;
import com.sinosoft.framework.orm.TableCreator;
import com.sinosoft.framework.utility.CharsetConvert;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCSiteSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;

public class SiteImporter {

	private static final Logger logger = LoggerFactory.getLogger(SiteImporter.class);
	private long siteID;

	private long newSiteID;

	private DataAccess da;

	private String ExportCharset;

	private String file;

	private LongTimeTask task;

	private Mapx map = new Mapx();// 站点相关的信息,从前台传入

	private Mapx customTableMap = new Mapx();// 存储已经导入过的自定义表名

	private boolean isNewSite;

	private NoType[] NoRelas;

	private TableRela[] TableRelas;

	public SiteImporter(String file) {
		this.file = file;
		this.task = LongTimeTask.createEmptyInstance();
	}

	public SiteImporter(String file, LongTimeTask task) {
		this.file = file;
		this.task = task;
	}

	public boolean importSite() {
		return importSite(null);
	}

	public boolean importSite(String poolName) {
		UserData user = new UserData();
		user.setUserName("admin");
		user.setBranchInnerCode("86");
		user.setLogin(true);
		user.setManager(true);
		User.setCurrent(user);

		isNewSite = "0".equals(map.getString("ID")) || StringUtil.isEmpty(map.getString("ID"));
		NoRelas = SiteTableRela.getNoRelaArray();
		TableRelas = SiteTableRela.getRelas();
		da = new DataAccess();
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			da.setAutoCommit(false);
			ExportCharset = fin.read() == 1 ? "GBK" : "UTF-8";
			byte[] bs = new byte[8];
			fin.read(bs);
			siteID = NumberUtil.toLong(bs);
			boolean flag = true;
			int i = 0;
			while (1 == 1) {
				// 读取对象
				bs = new byte[4];
				if (!bufferRead(bs, fin)) {
					break;
				}
				int len = NumberUtil.toInt(bs);
				bs = new byte[len];
				if (!bufferRead(bs, fin)) {
					flag = false;
					break;
				}
				Object obj = FileUtil.unserialize(bs);

				// 读取DataTable
				bs = new byte[4];
				if (!bufferRead(bs, fin)) {
					flag = false;
					break;
				}
				len = NumberUtil.toInt(bs);
				bs = new byte[len];
				if (!bufferRead(bs, fin)) {
					flag = false;
					break;
				}
				bs = ZipUtil.unzip(bs);
				task.setPercent(i * 100 / 200);
				dealOneEntry(bs, obj);
				i++;
			}
			if (flag) {
				da.commit();
			} else {
				logger.error("读取站点导出文件时发生错误!");
				da.rollback();
			}
			da.setAutoCommit(true);
			Site.updatePrivAndFile(map.getString("Alias"));
			return flag;
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			try {
				da.rollback();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			return false;
		} finally {
			try {
				da.setAutoCommit(true);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			try {
				da.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			try {
				fin.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 从流中读取数据到字符数组中
	 */
	private static boolean bufferRead(byte[] bs, FileInputStream fin) {
		try {
			if (fin.read(bs) == -1) {
				return false;
			}
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	private QueryBuilder insertQB = null;

	/**
	 * 处理一个导出数据项
	 */
	public void dealOneEntry(byte[] bs, Object obj) throws Exception {
		if (bs == null || obj == null) {
			return;
		}
		if (obj instanceof String) {
			String name = obj.toString();
			if (name.startsWith("File:")) {// 模板文件
				dealFile(name, bs);
			} else if (name.startsWith("CustomTable:")) {// 自定义表
				name = name.substring("CustomTable:".length());
				task.setCurrentInfo("正在导入自定义表:" + name);
				DataTable dt = (DataTable) FileUtil.unserialize(bs);
				try {
					if (!customTableMap.containsKey(name)) {
						QueryBuilder qb = new QueryBuilder(
								"select * from ZCCustomTableColumn where exists "
										+ "(select ID from ZCCustomTable where SiteID=? and Code=? and Type='Custom' and ZCCustomTableColumn.TableID=ID)",
								newSiteID, name);
						DataTable cdt = da.executeDataTable(qb);
						SchemaColumn[] scs = new SchemaColumn[cdt.getRowCount()];
						for (int j = 0; j < scs.length; j++) {
							DataRow cdr = cdt.getDataRow(j);
							int type = Integer.parseInt(cdr.getString("DataType"));
							SchemaColumn sc = new SchemaColumn(cdr.getString("Code"), type, j, (int) cdr
									.getInt("Length"), 0, "Y".equals(cdr.getString("isMandatory")), "Y".equals(cdr
									.getString("isPrimaryKey")));
							scs[j] = sc;
						}
						TableCreator tc = new TableCreator(da.getConnection().getDBConfig().DBType);
						tc.createTable(scs, name, true);
						tc.executeAndClear(da.getConnection());
						customTableMap.put(name, "");
						StringBuffer sb = new StringBuffer("insert into " + name + "(");
						for (int j = 0; j < cdt.getRowCount(); j++) {
							if (j != 0) {
								sb.append(",");
							}
							sb.append(cdt.get(j, "Code"));
						}
						sb.append(") values (");
						for (int j = 0; j < cdt.getRowCount(); j++) {
							if (j != 0) {
								sb.append(",");
							}
							sb.append("?");
						}
						sb.append(")");
						insertQB = new QueryBuilder(sb.toString());
						insertQB.setBatchMode(true);
					}
					dealCustomTable(dt, insertQB);
				} catch (Exception e) {
					logger.error("未成功导入表" + name + e.getMessage(), e);
				}
			} else {
				dealSiteIDTable(name, (DataTable) FileUtil.unserialize(bs));
			}
		} else {
			dealRelaTable((TableRela) obj, (DataTable) FileUtil.unserialize(bs));
		}
	}

	/**
	 * 导入自定义表
	 */
	private void dealCustomTable(DataTable dt, QueryBuilder qb) throws Exception {
		try {
			qb.clearBatches();
			qb.getParams().clear();
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < dt.getColCount() - 1; j++) {// 最后一列是行号
					String v = dt.getString(i, j);
					if (StringUtil.isEmpty(v)) {
						v = null;
					}
					qb.add(v);
				}
				qb.addBatch();
			}
			da.executeNoQuery(qb);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
	}

	/**
	 * 处理模板文件
	 */
	public void dealFile(String fileName, byte[] bs) {
		try {
			bs = convertCharset(bs);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		fileName = fileName.substring("File:".length());
		task.setCurrentInfo("正在导入站点文件:" + fileName);
		String root = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/"
				+ map.getString("Alias");
		root = root + "/";
		fileName = root + fileName;
		fileName = fileName.replaceAll("[\\\\/]+", "/");
		String dirName = fileName.substring(0, fileName.lastIndexOf('/'));
		File dir = new File(dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileUtil.writeByte(fileName, bs);
	}

	/**
	 * 处理ZCSite记录
	 */
	public void dealZCSite(DataRow dr) {
		if (isNewSite) {
			if (LicenseInfo.getName().equals("TrailUser")
					&& new QueryBuilder("select count(*) from ZCSite").executeInt() >= 1) {
				throw new RuntimeException("站点数超出限制，请联系全维智码软件更换License！");
			}
			newSiteID = NoUtil.getMaxID("SiteID");
			ZCSiteSchema site = new ZCSiteSchema();
			site.setValue(dr);
			site.setID(newSiteID);
			site.setName(map.getString("Name"));
			site.setAlias(map.getString("Alias"));
			site.setURL(map.getString("URL"));
			site.setHitCount(0);
			site.setChannelCount(0);
			site.setSpecialCount(0);
			site.setMagzineCount(0);
			site.setArticleCount(0);
			site.setImageLibCount(1);
			site.setVideoLibCount(1);
			site.setAudioLibCount(1);
			site.setAttachmentLibCount(1);
			site.setBranchInnerCode(User.getBranchInnerCode());
			site.setAddTime(new Date());
			site.setAddUser(User.getUserName());
			site.setConfigXML(ConfigImageLib.imageLibConfigDefault);
			da.insert(site);
			Transaction trans = new Transaction();
			Site.addDefaultPriv(newSiteID, trans);
			trans.setDataAccess(da);
			trans.commit(false);
			addIDMapping("ZCSite", String.valueOf(siteID), String.valueOf(newSiteID));
		} else {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(map.getString("ID"));
			newSiteID = site.getID();
			site.fill();

			task.setCurrentInfo("正在备份数据，可能需要较长时间，请耐心等待");
			// 先备份数据
			SiteExporter se = new SiteExporter(site.getID());
			se.exportSite(Config.getContextRealPath() + "WEB-INF/data/backup/" + site.getAlias() + "_"
					+ System.currentTimeMillis() + ".dat");

			// 正在删除旧站点数据
			addIDMapping("ZCSite", dr.getString("ID"), String.valueOf(site.getID()));// 加入ID映射
			da.deleteAndBackup(site);// 删除旧站点
			BlockingTransaction tran = new BlockingTransaction(da);
			Site.delSiteRela(site.getID() + "", tran);

			// 删除文件
			String sitePath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/"
					+ site.getAlias();
			FileUtil.delete(sitePath);

			site = new ZCSiteSchema();
			site.setValue(dr);

			da.insert(site);// 插入新站点
		}
	}

	/**
	 * 处理含有SiteID字段的表
	 */
	public void dealSiteIDTable(String tableName, DataTable dt) throws Exception {
		task.setCurrentInfo("正在向表" + tableName + "插入站点数据");
		if (tableName.equalsIgnoreCase("ZCSite")) {
			dealZCSite(dt.getDataRow(0));// ZCSite表特别处理
		} else {
			SchemaSet set = (SchemaSet) Class.forName("com.sinosoft.schema." + tableName + "Set").newInstance();
			if (!tableName.equalsIgnoreCase("ZDMember")) {// ZDMember以UserName为唯一主键，需特别处理
				for (int i = 0; i < dt.getRowCount(); i++) {
					Schema schema = (Schema) Class.forName("com.sinosoft.schema." + tableName + "Schema").newInstance();
					DataRow dr = dt.getDataRow(i);
					setSiteIDTableMaxNo(tableName, dr, true);
					schema.setValue(dt.getDataRow(i));
					set.add(schema);
				}
				// if (dt.getRowCount() == 0) {// 没有记录也要创建映射
				// setSiteIDTableMaxNo(tableName, new
				// DataRow(dt.getDataColumns(), null), false);
				// }
				da.insert(set);
			} else {
				Schema schema = (Schema) Class.forName("com.sinosoft.schema." + tableName + "Schema").newInstance();
				SchemaColumn[] scs = SchemaUtil.getColumns(schema);
				QueryBuilder qb = new QueryBuilder("select count(1) from " + tableName + " where 1=1 ");
				for (int i = 0; i < scs.length; i++) {
					if (scs[i].isPrimaryKey()) {
						qb.append(" and " + scs[i].getColumnName() + "=?");
					}
				}
				for (int i = 0; i < dt.getRowCount(); i++) {
					schema = (Schema) Class.forName("com.sinosoft.schema." + tableName + "Schema").newInstance();
					DataRow dr = dt.getDataRow(i);
					// 要先查询有没有记录
					qb.getParams().clear();
					for (int j = 0; j < scs.length; j++) {
						if (scs[j].isPrimaryKey()) {
							qb.add(dr.get(scs[j].getColumnName()));
						}
					}
					int count = qb.executeInt();
					if (count > 0) {
						setSiteIDTableMaxNo(tableName, dr, false);
					} else {
						setSiteIDTableMaxNo(tableName, dr, true);
					}
					schema.setValue(dt.getDataRow(i));
					set.add(schema);
				}
				// if (dt.getRowCount() == 0) {// 没有记录也要创建映射
				// setSiteIDTableMaxNo(tableName, new
				// DataRow(dt.getDataColumns(), null), false);
				// }
				da.deleteAndInsert(set);
			}
		}
	}

	/**
	 * 置换含有SiteID的表中的最大号字段,newIDFlag=false时不生成最大号
	 */
	public void setSiteIDTableMaxNo(String tableName, DataRow dr, boolean newIDFlag) {
		try {
			if (dr.getDataColumn("SiteID") != null) {
				dr.set("SiteID", this.getIDMapping("ZCSite", String.valueOf(siteID)));
			}
		} catch (Throwable t) {
			logger.error(tableName + t.getMessage(), t);
		}
		for (int i = 0; i < NoRelas.length; i++) {
			NoType nr = NoRelas[i];
			if (nr.TableCode.equalsIgnoreCase(tableName)) {
				String id = dr.getString(nr.FieldName);
				if (newIDFlag) {
					if (nr.TableCode.equals("ZCCatalog") && nr.FieldName.equals("InnerCode")) {
						if (id.length() == 6) {// 一级栏目
							id = CatalogUtil.createCatalogInnerCode(null);
						} else {
							String parent = id.substring(0, id.length() - 6);
							parent = this.getIDMapping(nr.TableCode + "." + nr.FieldName, parent);// 上级编码已经生成过了
							id = CatalogUtil.createCatalogInnerCode(parent);
						}
					} else {
						id = String.valueOf(NoUtil.getMaxID(nr.NoType));
					}
				}
				String type = nr.TableCode;
				if (type.equals("ZCArticle") || type.equals("ZCAttachment") || type.equals("ZCImage")
						|| type.equals("ZCAudio") || type.equals("ZCVideo")) {
					if (nr.NoType.equals("DocID")) {
						type = "Document";
					}
				}
				this.addIDMapping(type + "." + nr.FieldName, dr.getString(nr.FieldName), id);
				dr.set(nr.FieldName, id);
			}
		}
		for (int i = 0; i < TableRelas.length; i++) {
			if (TableRelas[i].TableCode.equals(tableName)) {
				String type = TableRelas[i].RelaTable;
				if (type.equals("ZCArticle") || type.equals("ZCAttachment") || type.equals("ZCImage")
						|| type.equals("ZCAudio") || type.equals("ZCVideo")) {
					if (TableRelas[i].RelaField.equals("ID")) {
						type = "Document";
					}
				}
				String id = this.getIDMapping(type + "." + TableRelas[i].RelaField, dr
						.getString(TableRelas[i].KeyField));
				if (StringUtil.isNotEmpty(id)) {
					dr.set(TableRelas[i].KeyField, id);
				}
			}
		}
	}

	/**
	 * 置换关联表中的最大号字段
	 */
	public void setRelaTableMaxNo(TableRela tr, DataRow dr, boolean newIDFlag) {
		for (int i = 0; i < NoRelas.length; i++) {
			NoType nr = NoRelas[i];
			if (nr.TableCode.equalsIgnoreCase(tr.TableCode)) {
				String id = dr.getString(nr.FieldName);
				if (newIDFlag) {
					id = String.valueOf(NoUtil.getMaxID(nr.NoType));
				}
				this.addIDMapping(nr.TableCode + "." + nr.FieldName, dr.getString(nr.FieldName), id);
				dr.set(nr.FieldName, id);
			}
		}
		for (int i = 0; i < TableRelas.length; i++) {
			if (TableRelas[i].TableCode.equals(tr.TableCode)) {
				String type = TableRelas[i].RelaTable;
				if (type.equals("ZCArticle") || type.equals("ZCAttachment") || type.equals("ZCImage")
						|| type.equals("ZCAudio") || type.equals("ZCVideo")) {
					if (TableRelas[i].RelaField.equals("ID")) {
						type = "Document";
					}
				}
				String id = this.getIDMapping(type + "." + TableRelas[i].RelaField, dr
						.getString(TableRelas[i].KeyField));
				if (StringUtil.isNotEmpty(id)) {
					dr.set(TableRelas[i].KeyField, id);
				}
			}
		}
	}

	/**
	 * 处理关联表
	 */
	public void dealRelaTable(TableRela tr, DataTable dt) throws Exception {
		logger.info("正在向表{}插入站点数据", tr.TableCode);
		if (!tr.isExportData) {// 未导出数据
			return;
		}
		SchemaSet set = (SchemaSet) Class.forName("com.sinosoft.schema." + tr.TableCode + "Set").newInstance();
		for (int i = 0; i < dt.getRowCount(); i++) {
			Schema schema = (Schema) Class.forName("com.sinosoft.schema." + tr.TableCode + "Schema").newInstance();
			DataRow dr = dt.getDataRow(i);
			setRelaTableMaxNo(tr, dr, true);
			schema.setValue(dt.getDataRow(i));
			set.add(schema);
		}
		da.insert(set);
	}

	/**
	 * 获取导出文件中的站点记录信息，并转换为Mapx
	 */
	public static Mapx getSiteInfo(String file) throws Exception {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			fin.read();
			byte[] bs = new byte[8];
			fin.read(bs);

			// 读取对象
			bs = new byte[4];
			if (!bufferRead(bs, fin)) {
				return null;
			}
			int len = NumberUtil.toInt(bs);
			bs = new byte[len];
			if (!bufferRead(bs, fin)) {
				return null;
			}

			// 读取DataTable
			bs = new byte[4];
			if (!bufferRead(bs, fin)) {
				return null;
			}
			len = NumberUtil.toInt(bs);
			bs = new byte[len];
			if (!bufferRead(bs, fin)) {
				return null;
			}
			bs = ZipUtil.unzip(bs);
			DataTable dt = (DataTable) FileUtil.unserialize(bs);
			return dt.getDataRow(0).toCaseIgnoreMapx();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public void setSiteInfo(Mapx map) {
		this.map = map;
	}

	/**
	 * 转换字符集，使同一套模板文件能同时适应不同字符集编码的情况
	 */
	public byte[] convertCharset(byte[] bs) throws UnsupportedEncodingException {
		if (Constant.GlobalCharset.equals("GBK") && ExportCharset.equals("UTF-8")) {
			return CharsetConvert.webFileUTF8ToGBK(bs);
		}
		if (Constant.GlobalCharset.equals("UTF-8") && ExportCharset.equals("GBK")) {
			return CharsetConvert.webFileGBKToUTF8(bs);
		}
		return bs;
	}

	private Mapx idMap = new Mapx();

	/**
	 * 新旧ID映射
	 */
	public String getIDMapping(String type, String oldID) {
		Object obj = idMap.get(type);
		if (StringUtil.isEmpty(oldID)) {
			return null;
		}
		if (obj == null) {
			logger.warn("站点导入时未找到ID映射关系：Type={},OldID={}", type, oldID);
			return null;
		}
		Mapx map = (Mapx) obj;
		return map.getString(oldID);
	}

	/**
	 * 新加一个新旧ID映射
	 */
	public void addIDMapping(String type, String oldID, String newID) {
		Object obj = idMap.get(type);
		if (obj == null) {
			obj = new Mapx();
			idMap.put(type, obj);
		}
		Mapx map = (Mapx) obj;
		map.put(oldID, newID);
	}

	public static void main(String[] args) {
		Mapx map = new Mapx();
		map.put("ID", "0");
		map.put("Name", "导入测试");
		map.put("Alias", "ImportTest");
		map.put("URL", "http://import.test.com");
		SiteImporter si = new SiteImporter("G:/zving.dat");
		si.setSiteInfo(map);
		si.importSite();
	}

}
