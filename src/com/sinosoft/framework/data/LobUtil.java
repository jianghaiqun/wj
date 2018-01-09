package com.sinosoft.framework.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LobUtil {
	private static final Logger logger = LoggerFactory.getLogger(LobUtil.class);

	public static String clobToString(Clob clob) {
		if (clob == null) {
			return null;
		}
		try {
			Reader r = clob.getCharacterStream();
			StringWriter sw = new StringWriter();
			char[] cs = new char[(int) clob.length()];
			try {
				r.read(cs);
				sw.write(cs);
				return sw.toString();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static byte[] blobToBytes(Blob blob) {
		if (blob == null) {
			return null;
		}
		try {
			return blob.getBytes(1L, (int) blob.length());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static void setClob(DBConn conn, PreparedStatement ps, int i, Object v) throws SQLException {
		if (conn.getDBConfig().isOracle()) {
			Class clobClass = null;
			try {
				clobClass = Class.forName("oracle.sql.CLOB");
			} catch (ClassNotFoundException e2) {
				logger.error(e2.getMessage(), e2);
				return;
			}
			Object clob = null;
			Object oc = conn.getPhysicalConnection();
			try {
				Method m = clobClass.getMethod("createTemporary", new Class[] { java.sql.Connection.class,
						boolean.class, int.class });
				clob = m.invoke(null, new Object[] { oc, new Boolean(true), new Integer(1) });
				// 相当于clob=CLOB.createTemporary(oc,true,1);
				// Oracle9i中是1,10G中变成了10，但还是会将1自动转为10

				m = clobClass.getMethod("open", new Class[] { int.class });
				m.invoke(clob, new Object[] { new Integer(1) });// 相当于clob.open(1);

				m = clobClass.getMethod("setCharacterStream", new Class[] { long.class });
				Writer writer = (Writer) m.invoke(clob, new Object[] { new Long(0) });
				// 相当于Writer writer = clob.setCharacterStream(0L);
				writer.write(String.valueOf(v));
				writer.close();

				clobClass.getMethod("close", (Class[]) null).invoke(clob, null);// 相当于clob.close();
				ps.setClob(i, (Clob) clob);
			} catch (Exception e) {
				try {
					if (clob != null) {
						Method m = clobClass.getMethod("freeTemporary", new Class[] { clobClass });
						m.invoke(null, new Object[] { clob });// 相当于CLOB.freeTemporary(clob);
					}
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				logger.error(e.getMessage(), e);
			}
		} else {
			ps.setObject(i, v);
		}
	}

	public static void setBlob(DBConn conn, PreparedStatement ps, int i, byte[] v) throws SQLException {
		if (conn.getDBConfig().isOracle()) {
			Class blobClass = null;
			try {
				blobClass = Class.forName("oracle.sql.BLOB");
			} catch (ClassNotFoundException e2) {
				logger.error(e2.getMessage(), e2);
				return;
			}
			Object blob = null;
			Object oc = conn.getPhysicalConnection();
			try {
				Method m = blobClass.getMethod("createTemporary", new Class[] { java.sql.Connection.class,
						boolean.class, int.class });
				blob = m.invoke(null, new Object[] { oc, new Boolean(true), new Integer(1) });
				// 相当于blob=BLOB.createTemporary(oc,true,1);
				// Oracle9i中是1,10G中变成了10，但还是会将1自动转为10

				m = blobClass.getMethod("open", new Class[] { int.class });
				m.invoke(blob, new Object[] { new Integer(1) });// 相当于blob.open(1);

				m = blobClass.getMethod("getBinaryOutputStream", new Class[] { long.class });
				OutputStream out = (OutputStream) m.invoke(blob, new Object[] { new Long(0) });
				out.write(v);
				out.close();

				blobClass.getMethod("close", (Class[]) null).invoke(blob, null);// 相当于blob.close();
				ps.setBlob(i, (Blob) blob);
			} catch (Exception e) {
				try {
					if (blob != null) {
						Method m = blobClass.getMethod("freeTemporary", new Class[] { blobClass });
						m.invoke(null, new Object[] { blob });// 相当于BLOB.freeTemporary(clob);
					}
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				logger.error(e.getMessage(), e);
			}
		} else {
			ps.setObject(i, v);
		}
	}
}
