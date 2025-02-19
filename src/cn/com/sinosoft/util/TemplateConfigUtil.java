package cn.com.sinosoft.util;

import cn.com.sinosoft.bean.DynamicConfig;
import cn.com.sinosoft.bean.HtmlConfig;
import cn.com.sinosoft.bean.MailConfig;
import com.sinosoft.schema.SDInterActionSchema;
import com.sinosoft.schema.SDInterActionSet;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 工具类 - 模板配置
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT186E0591084D889939733B88B0218614
 * ============================================================================
 */

public class TemplateConfigUtil {
	private static final Logger logger = LoggerFactory.getLogger(TemplateConfigUtil.class);

	public static final String CONFIG_FILE_NAME = "template.xml";// 模板配置文件名称
	public static final String DYNAMIC_CONFIG_LIST_CACHE_KEY = "dynamicConfigList";// 动态模板配置缓存Key
	public static final String HTML_CONFIG_LIST_CACHE_KEY = "htmlConfigList";// 生成静态模板配置缓存Key
	public static final String MAIL_CONFIG_LIST_CACHE_KEY = "mailConfigList";// 邮件模板配置缓存Key
	
	/**
	 * 获取动态模板配置
	 * 
	 * @return DynamicConfig集合
	 */
	@SuppressWarnings("unchecked")
	public static List<DynamicConfig> getDynamicConfigList() {
		List<DynamicConfig> dynamicConfigList = (List<DynamicConfig>) OsCacheConfigUtil.getFromCache(DYNAMIC_CONFIG_LIST_CACHE_KEY);
		if (dynamicConfigList != null) {
			return dynamicConfigList;
		}
		File configFile = null;
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Element htmlConfigElement = (Element)document.selectSingleNode("/sinosoft/dynamicConfig");
		Iterator<Element> iterator = htmlConfigElement.elementIterator();
		dynamicConfigList = new ArrayList<DynamicConfig>();
	    while(iterator.hasNext()) {
	    	Element element = (Element)iterator.next();
	    	String description = element.element("description").getTextTrim();
	    	String templateFilePath = element.element("templateFilePath").getTextTrim();
	    	DynamicConfig dynamicConfig = new DynamicConfig();
	    	dynamicConfig.setName(element.getName());
	    	dynamicConfig.setDescription(description);
	    	dynamicConfig.setTemplateFilePath(templateFilePath);
	    	dynamicConfigList.add(dynamicConfig);
	    }
	    OsCacheConfigUtil.putInCache(DYNAMIC_CONFIG_LIST_CACHE_KEY, dynamicConfigList);
		return dynamicConfigList;
	}
	
	/**
	 * 根据动态模板配置名称获取DynamicConfig对象
	 * 
	 * @return DynamicConfig对象
	 */
	public static DynamicConfig getDynamicConfig(String name) {
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			File configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Element element = (Element)document.selectSingleNode("/sinosoft/dynamicConfig/" + name);
		String description = element.element("description").getTextTrim();
		String templateFilePath = element.element("templateFilePath").getTextTrim();
    	DynamicConfig dynamicConfig = new DynamicConfig();
    	dynamicConfig.setName(element.getName());
    	dynamicConfig.setDescription(description);
    	dynamicConfig.setTemplateFilePath(templateFilePath);
		return dynamicConfig;
	}
	
	/**
	 * 根据DynamicConfig对象读取模板文件内容
	 * 
	 * @return 模板文件内容
	 */
	public static String readTemplateFileContent(DynamicConfig dynamicConfig) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(dynamicConfig.getTemplateFilePath()));
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return templateFileContent;
	}
	
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String writeTemplateFileContent(DynamicConfig dynamicConfig, String templateFileContent) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(dynamicConfig.getTemplateFilePath()));
		try {
			FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return templateFileContent;
	}

	/**
	 * 获取生成静态模板配置
	 * 
	 * @return HtmlConfig集合
	 */
	@SuppressWarnings("unchecked")
	public static List<HtmlConfig> getHtmlConfigList() {
		List<HtmlConfig> htmlConfigList = (List<HtmlConfig>) OsCacheConfigUtil.getFromCache(HTML_CONFIG_LIST_CACHE_KEY);
		if (htmlConfigList != null) {
			return htmlConfigList;
		}
		File configFile = null;
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Element htmlConfigElement = (Element)document.selectSingleNode("/sinosoft/htmlConfig");
		Iterator<Element> iterator = htmlConfigElement.elementIterator();
		htmlConfigList = new ArrayList<HtmlConfig>();
	    while(iterator.hasNext()) {
	    	Element element = (Element)iterator.next();
	    	String description = element.element("description").getTextTrim();
	    	String templateFilePath = element.element("templateFilePath").getTextTrim();
	    	String htmlFilePath = element.element("htmlFilePath").getTextTrim();
	    	HtmlConfig htmlConfig = new HtmlConfig();
	    	htmlConfig.setName(element.getName());
	    	htmlConfig.setDescription(description);
	    	htmlConfig.setTemplateFilePath(templateFilePath);
	    	htmlConfig.setHtmlFilePath(htmlFilePath);
	    	htmlConfigList.add(htmlConfig);
	    }
	    OsCacheConfigUtil.putInCache(HTML_CONFIG_LIST_CACHE_KEY, htmlConfigList);
		return htmlConfigList;
	}
	
	/**
	 * 根据生成静态模板配置名称获取HtmlConfig对象
	 * 
	 * @return HtmlConfig对象
	 */
	public static HtmlConfig getHtmlConfig(String name) {
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			File configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Element element = (Element)document.selectSingleNode("/sinosoft/htmlConfig/" + name);
		String description = element.element("description").getTextTrim();
		String templateFilePath = element.element("templateFilePath").getTextTrim();
    	String htmlFilePath = element.element("htmlFilePath").getTextTrim();
		HtmlConfig htmlConfig = new HtmlConfig();
    	htmlConfig.setName(element.getName());
    	htmlConfig.setDescription(description);
    	htmlConfig.setTemplateFilePath(templateFilePath);
    	htmlConfig.setHtmlFilePath(htmlFilePath);
		return htmlConfig;
	}
	
	/**
	 * 根据HtmlConfig对象读取模板文件内容
	 * 
	 * @return 模板文件内容
	 */
	public static String readTemplateFileContent(HtmlConfig htmlConfig) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(htmlConfig.getTemplateFilePath()));
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return templateFileContent;
	}
	
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String writeTemplateFileContent(HtmlConfig htmlConfig, String templateFileContent) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(htmlConfig.getTemplateFilePath()));
		try {
			FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return templateFileContent;
	}
	
	/**
	 * 获取邮件模板配置
	 * 
	 * @return MailConfig集合
	 */
	@SuppressWarnings("unchecked")
	public static List<MailConfig> getMailConfigList() {
		List<MailConfig> mailConfigList = (List<MailConfig>) OsCacheConfigUtil.getFromCache(MAIL_CONFIG_LIST_CACHE_KEY);
//		 if (mailConfigList != null) {
//			return mailConfigList;
//		}
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;			
			File configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Element mailConfigElement = (Element)document.selectSingleNode("/sinosoft/mailConfig");
		Iterator<Element> iterator = mailConfigElement.elementIterator();
		mailConfigList = new ArrayList<MailConfig>();
	    while(iterator.hasNext()) {
	    	Element element = (Element)iterator.next();
	    	String description = element.element("description").getTextTrim();
	    	String subject = element.element("subject").getTextTrim();
	    	String templateFilePath = element.element("templateFilePath").getTextTrim();
	    	String name = element.getName();
	    	String isEmail = IsMAIL(name);
	    	String names ="";
	    	if("1".equals(isEmail)){
	    		names = "是";
	    	}else{
	    		names = "否";
	    	}
	    	MailConfig mailConfig = new MailConfig();
	    	mailConfig.setName(element.getName());
	    	mailConfig.setDescription(description);
	    	mailConfig.setSubject(subject);
	    	mailConfig.setTemplateFilePath(templateFilePath);
	    	mailConfig.setIsEmail(names);
	    	mailConfigList.add(mailConfig);
	    }
	    OsCacheConfigUtil.putInCache(MAIL_CONFIG_LIST_CACHE_KEY, mailConfigList);
		return mailConfigList;
	}
	
	/**
	 * 根据邮件模板配置名称获取MailConfig对象
	 * 
	 * @return MailConfig对象
	 */
	public static MailConfig getMailConfig(String name) {
		Document document = null;
		try {
			String configFilePath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()).getParent() + "/template/" + CONFIG_FILE_NAME;
			File configFile = new File(configFilePath);
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(configFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Element element = (Element)document.selectSingleNode("/sinosoft/mailConfig/" + name);
		String description = element.element("description").getTextTrim();
		String subject = element.element("subject").getTextTrim();
		String templateFilePath = element.element("templateFilePath").getTextTrim();
		MailConfig mailConfig = new MailConfig();
    	mailConfig.setName(element.getName());
    	mailConfig.setDescription(description);
    	mailConfig.setSubject(subject);
    	mailConfig.setTemplateFilePath(templateFilePath);
		return mailConfig;
	}
	
	/**
	 * 根据MailConfig对象读取模板文件内容
	 * 
	 * @return 模板文件内容
	 */
	public static String readTemplateFileContent(MailConfig mailConfig) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(mailConfig.getTemplateFilePath()));
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return templateFileContent;
	}
	
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String writeTemplateFileContent(MailConfig mailConfig, String templateFileContent) {
		ServletContext servletContext = ServletActionContext.getServletContext();
		File templateFile = new File(servletContext.getRealPath(mailConfig.getTemplateFilePath()));
		try {
			FileUtils.writeStringToFile(templateFile, templateFileContent, "UTF-8");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return templateFileContent;
	}
	/**
	 * 写入模板文件内容
	 * 
	 */
	public static String IsMAIL(String templateName) {
		String Ismail = "";
		SDInterActionSchema tSDInterActionSchema = new SDInterActionSchema();
		tSDInterActionSchema.setEmailType(templateName);
		SDInterActionSet tSDInterActionSet= tSDInterActionSchema.query();
		SDInterActionSchema sSDInterActionSchema = tSDInterActionSet.get(0);
		if(sSDInterActionSchema==null){
			Ismail="0";
		}else{
		Ismail = sSDInterActionSchema.getisEmail();
		}
		return Ismail;
	}
}