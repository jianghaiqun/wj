package cn.com.sinosoft.util;

import com.sinosoft.framework.Config;
import freemarker.template.TemplateException;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FTLManager extends FreemarkerManager {
	private static final Logger logger = LoggerFactory.getLogger(FTLManager.class);

	@Override
	protected void loadSettings(ServletContext servletContext) {
		 InputStream in = null;
		 boolean isDefault = true;
	        try {
	        	File pFile = new File(Config.getContextRealPath() + "WEB-INF/classes/freemarker.properties");
	        	if (pFile.exists()) {
	        		in = new FileInputStream(pFile);
	        	}
	            if (in != null) {
	                Properties p = new Properties();
	                p.load(in);

	                for (Object o : p.keySet()) {
	                    String name = (String) o;
	                    String value = (String) p.get(name);

	                    if (name == null) {
	                        throw new IOException(
	                                "init-param without param-name.  Maybe the freemarker.properties is not well-formed?");
	                    }
	                    if (value == null) {
	                        throw new IOException(
	                                "init-param without param-value.  Maybe the freemarker.properties is not well-formed?");
	                    }
	                    addSetting(name, value);
	                }
	                if (!p.isEmpty()) {
	                	isDefault = false;
	                }
	            }
	        } catch (IOException e) {
				logger.error("Error while loading freemarker settings from /freemarker.properties" + e.getMessage(), e);
	        } catch (TemplateException e) {
				logger.error("Error while loading freemarker settings from /freemarker.properties" + e.getMessage(), e);
	        } finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch(IOException io) {
						logger.warn("Unable to close input stream" + io.getMessage(), io);
	                }
	            }
	            if (isDefault) {
	            	addDefaultSetting();
	            }
	        }
	}
	/**
	 * 添加默认配置项
	 */
	private void addDefaultSetting(){
		
	}

}
