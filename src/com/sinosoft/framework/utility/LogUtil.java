package com.sinosoft.framework.utility;

import com.sinosoft.framework.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.tools.ant.filters.StringInputStream;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class LogUtil {
	private static boolean initFlag = false;

	private static Log zvinglog = null;

	public static void init() {
		PrintStream syserr = System.err;
		try {
			Log4jErrorPrintStream errStream = new Log4jErrorPrintStream(System.err);
			System.setErr(errStream);
			String fileName = Config.getClassesPath() + "log4j.config";
			String txt = FileUtil.readText(fileName);
			txt = StringUtil.replaceEx(txt, "%{ContextRealPath}", Config.getContextRealPath());
			Properties ps = new Properties();
			StringInputStream si = new StringInputStream(txt);
			ps.load(si);
			si.close();
			PropertyConfigurator.configure(ps);
			zvinglog = LogFactory.getLog("ZVING");
		} catch (Exception e) {
			System.setErr(syserr);// 加载出错时恢复默认情况
			e.printStackTrace();
		}
	}

	public static Log getLogger() {
		if (!initFlag) {
			init();
			initFlag = true;
		}
		return zvinglog;
	}

	public static void info(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.info(obj);
	}

	public static void debug(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.debug(obj);
	}

	public static void warn(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.warn(obj);
	}

	public static void error(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.error(obj);
	}

	public static void fatal(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.fatal(obj);
	}

	static class Log4jErrorPrintStream extends PrintStream {
		Log4jErrorPrintStream(OutputStream out) {
			super(out, true); // 使用自动刷新
		}

		public void println(String str) {
			try {
				if (zvinglog != null) {
					zvinglog.error(str);
				}
			} catch (Throwable e) {
				System.out.println("LogUtil.Log4jErrorPrintStream.println()发生错误：" + e.getMessage());
			}
		}

		public void println(Object obj) {
			try {
				if (zvinglog != null) {
					zvinglog.error(obj);
				}
			} catch (Throwable e) {
				System.out.println("LogUtil.Log4jErrorPrintStream.println()发生错误：" + e.getMessage());
			}
		}
	}
}
