package com.sinosoft.framework.orm;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.license.SystemInfo;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.security.ZRSACipher;
import com.sinosoft.framework.utility.BufferedRandomAccessFile;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import org.bouncycastle.jce.provider.JDKX509CertificateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class DBImport {
	private static final Logger logger = LoggerFactory.getLogger(DBImport.class);

	private DataAccess da;

	private LongTimeTask task;

	public void setTask(LongTimeTask task) {
		this.task = task;
	}

	public void importDB(String file) {
		importDB(file, "");
	}

	public String getSQL(String file, String dbtype) {
		TableCreator tc = new TableCreator(dbtype);
		BufferedRandomAccessFile braf = null;
		try {
			braf = new BufferedRandomAccessFile(file, "r");
			HashMap map = new HashMap();
			while (braf.getFilePointer() != braf.length()) {
				// 先读取名称
				byte[] bs = new byte[4];
				braf.read(bs);
				int len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);

				// 再读取数据
				bs = new byte[4];
				braf.read(bs);
				len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);
				bs = ZipUtil.unzip(bs);
				Object obj = FileUtil.unserialize(bs);
				if (obj == null) {
					continue;
				}
				if (obj instanceof SchemaSet) {
					SchemaSet set = (SchemaSet) obj;
					if (set != null && !map.containsKey(set.TableCode)) {
						tc.createTable(set.Columns, set.TableCode);
						map.put(set.TableCode, "");
					}
				}
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
		return tc.getAllSQL();
	}

	public boolean importDB(String file, String poolName) {
		DBConn conn = DBConnPool.getConnection(poolName);
		try {
			return importDB(file, conn, true);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			// 以下为License代码，请勿修改
			if (System.currentTimeMillis() % 10000000 == 0) {
				try {
					String cert = "MIICnTCCAgagAwIBAgIBATANBgkqhkiG9w0BAQUFADBkMRIwEAYDVQQDDAlMaWNlbnNlQ0ExDTAL"
							+ "BgNVBAsMBFNPRlQxDjAMBgNVBAoMBVpWSU5HMRAwDgYDVQQHDAdIQUlESUFOMQswCQYDVQQGEwJD"
							+ "TjEQMA4GA1UECAwHQkVJSklORzAgFw0wOTA0MTYwMzQ4NDhaGA81MDA3MDQyMDAzNDg0OFowZDES"
							+ "MBAGA1UEAwwJTGljZW5zZUNBMQ0wCwYDVQQLDARTT0ZUMQ4wDAYDVQQKDAVaVklORzEQMA4GA1UE"
							+ "BwwHSEFJRElBTjELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JFSUpJTkcwgZ8wDQYJKoZIhvcNAQEB"
							+ "BQADgY0AMIGJAoGBAMStEFTKHuIaPzADjA7hrHSQn5jL5yCN+dabiP0vXfAthKWEOiaS8wAX8WX5"
							+ "16PDPfyo2SL63h5Ihvn9BBpLqAgwvDyxoP6bpU85ZuvmbeI02EPgLCz1IK+Xibl4RmcaprKvjm5e"
							+ "c92zWLWTC4TEkdh+NPFkkL7yZskZNC4e40I9AgMBAAGjXTBbMB0GA1UdDgQWBBRwZt+eq7q/8MvU"
							+ "oSNW41Bzp2RD5zAfBgNVHSMEGDAWgBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAMBgNVHRMEBTADAQH/"
							+ "MAsGA1UdDwQEAwIBBjANBgkqhkiG9w0BAQUFAAOBgQAummShucu9umvlsrGaJmw0xkFCwC8esLHe"
							+ "50sJkER2OreGPCdrQjEGytvYz4jtkqVyvLBDziuz29yeQUDjfVBuN7iZ9CuYeuI73uQoQeZOKLDQ"
							+ "j2UZHag6XNCkSJTvh9g2JWOeAJjmwquwds+dONKRU/fol4JnrU7fMP/V0Ur3/w==";
					byte[] code = StringUtil.hexDecode(FileUtil.readText(Config.getClassesPath() + "license.dat")
							.trim());
					JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
					X509Certificate cer = (X509Certificate) certificatefactory
							.engineGenerateCertificate(new ByteArrayInputStream(StringUtil.base64Decode(cert)));
					PublicKey pubKey = cer.getPublicKey();
					ZRSACipher dc = new ZRSACipher();
					dc.init(Cipher.DECRYPT_MODE, pubKey);
					byte[] bs = new byte[code.length * 2];
					int indexBS = 0;
					int indexCode = 0;
					while ((code.length - indexCode) > 128) {// 每128字节做一次解密
						indexBS += dc.doFinal(code, indexCode, 128, bs, indexBS);
						indexCode += 128;
					}
					indexBS += dc.doFinal(code, indexCode, code.length - indexCode, bs, indexBS);
					String str = new String(bs, 0, indexBS);
					Mapx mapx = StringUtil.splitToMapx(str, ";", "=");
					String product = mapx.getString("Product");
					int userLimit = Integer.parseInt(mapx.getString("UserLimit"));
					String macAddress = mapx.getString("MacAddress");
					String name = mapx.getString("Name");

					Date endDate = new Date(Long.parseLong(mapx.getString("TimeEnd")));
					if (new QueryBuilder("select count(*) from ZDUser").executeInt() >= userLimit) {
						logger.warn("己有用户数超出License中的用户数限制!");
						System.exit(0);
					}
					if (endDate.getTime() < System.currentTimeMillis()) {
						logger.warn("License己过期!");
						System.exit(0);
					}
					if (name.indexOf("Trial") < 0 && !macAddress.equalsIgnoreCase(SystemInfo.getMacAddress())) {
						logger.warn("License中指定的Mac地址与实际Mac地址不一致!");
						System.exit(0);
					}
					product = product.toLowerCase();
					try {
						Class.forName("com.sinosoft.oa.workflow.FlowConfig");
						if (product.indexOf("zoa") < 0) {
							logger.warn("License中没有ZOA相关的标记!");
							System.exit(0);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					try {
						Class.forName("com.sinosoft.cms.stat.StatUtil");
						if (product.indexOf("zcms") < 0) {
							logger.warn("License中没有ZCMS相关的标记!");
							System.exit(0);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					try {
						Class.forName("com.sinosoft.shop.AdvanceShop");
						if (product.indexOf("zshop") < 0) {
							logger.warn("License中没有ZShop相关的标记!");
							System.exit(0);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			// License代码结束
		}
	}

	public boolean importDB(String file, DBConn conn, boolean autoCreate) {
		da = new DataAccess(conn);
		BufferedRandomAccessFile braf = null;
		try {
			braf = new BufferedRandomAccessFile(file, "r");
			HashMap map = new HashMap();
			int i = 0;
			TableCreator tc = new TableCreator(da.getConnection().getDBConfig().DBType);
			while (braf.getFilePointer() != braf.length()) {
				// 先读取名称
				byte[] bs = new byte[4];
				braf.read(bs);
				int len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);
				String name = new String(bs);

				// 再读取数据
				bs = new byte[4];
				braf.read(bs);
				len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);
				bs = ZipUtil.unzip(bs);
				try {
					Object obj = FileUtil.unserialize(bs);
					if (obj == null) {
						continue;
					}
					if (obj instanceof SchemaSet) {
						SchemaSet set = (SchemaSet) obj;
						try {
							if (!map.containsKey(set.TableCode)) {
								tc.createTable(set.Columns, set.TableCode, autoCreate);
								tc.executeAndClear(conn);
								map.put(set.TableCode, "");
							}
							if (task != null) {
								task.setPercent(new Double(i++ * 100.0 / 600).intValue());
								task.setCurrentInfo("正在导入表" + set.TableCode);
							}
							if (!importOneSet(set)) {
								return false;
							}
						} catch (Exception e) {
							logger.error("未成功导入表" + set.TableCode + e.getMessage(), e);
						}
					}
					if (obj instanceof DataTable) {// 自定义表
						try {
							QueryBuilder insertQB = null;
							if (!map.containsKey(name)) {
								QueryBuilder qb = new QueryBuilder(
										"select * from ZCCustomTableColumn where TableID in "
												+ "(select ID from ZCCustomTable where Code=? and Type='Custom')", name);
								DataTable cdt = da.executeDataTable(qb);
								SchemaColumn[] scs = new SchemaColumn[cdt.getRowCount()];
								for (int j = 0; j < scs.length; j++) {
									DataRow cdr = cdt.getDataRow(j);
									int type = Integer.parseInt(cdr.getString("DataType"));
									SchemaColumn sc = new SchemaColumn(cdr.getString("Code"), type, j, (int) cdr
											.getInt("Length"), 0, "Y".equals(cdr.getString("isMandatory")), "Y"
											.equals(cdr.getString("isPrimaryKey")));
									scs[j] = sc;
								}
								tc.createTable(scs, name, autoCreate);
								tc.executeAndClear(conn);
								map.put(name, "");
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
							if (task != null) {
								task.setPercent(new Double(i++ * 100.0 / 600).intValue());
								task.setCurrentInfo("正在导入表" + name);
							}
							importOneTable(name, (DataTable) obj, insertQB);
						} catch (Exception e) {
							logger.error("未成功导入表" + name + e.getMessage(), e);
						}
					}
				} catch (Exception e) {// 数据文件中的Schema与本地的Schema不一致
					logger.error("导入数据时发生错误:" + e.getMessage(), e);
					continue;
				}
			}
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			return false;
		} finally {
			if (braf != null) {
				try {
					braf.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private boolean importOneSet(SchemaSet set) throws Exception {
		set.setDataAccess(da);
		if (da.getConnection().getDBConfig().isOracle()) {// 需要检查，Mysql
			// NotNull的列允许空字符串，但Oracle不允许
			for (int i = 0; i < set.size(); i++) {
				Schema schema = set.getObject(i);
				for (int j = 0; j < schema.Columns.length; j++) {
					Object v = schema.getV(j);
					if (schema.Columns[j].isMandatory() && (v == null || v.equals(""))) {
						logger.warn("表{}的{}列不能为空!", schema.TableCode, schema.Columns[j].getColumnName());
						set.remove(schema);
						i--;
						break;
					}
				}
			}
		}
		return set.insert();
	}

	/**
	 * 导入自定义表
	 */
	private void importOneTable(String code, DataTable dt, QueryBuilder qb) throws Exception {
		// 有可能自定义相关模块不存在
		try {
			qb.getParams().clear();
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < dt.getColCount(); j++) {
					if (j == dt.getColCount() - 1
							&& (dt.getDataColumn(j).getColumnName().equalsIgnoreCase("RNM") || dt.getDataColumn(j)
									.getColumnName().equalsIgnoreCase("_RowNumber"))) {
						break;
					}
					String v = dt.getString(i, j);
					if (StringUtil.isEmpty(v)) {
						v = null;
					}
					if (v != null && dt.getDataColumn(j).getColumnType() == DataColumn.INTEGER) {
						qb.add(Integer.parseInt(v));
					} else {
						qb.add(v);
					}
				}
				qb.addBatch();
			}
			da.executeNoQuery(qb);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			return;// 有可能没有自定义表相关的模块
		}
	}
}
