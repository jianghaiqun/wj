package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.entity.Admin;
import cn.com.sinosoft.service.MailTemplateService;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Service实现类 - 创建邮件模版
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFTEEB47174264A6259B9A1BA5D141B72C0
 * ============================================================================
 */

@Service
public class MailTemplateServiceImpl extends BaseServiceImpl<Admin, String>
		implements MailTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(MailTemplateServiceImpl.class);

	@Override
	public boolean addMailTemplate(String fileName, String description,
			String subject) {
		try {
			File f = new File(ServletActionContext.getRequest().getRealPath("/WEB-INF/template/shop/")+ "/" + fileName + ".ftl");
			if (f.exists()) {
				logger.info("文件存在");
				return false;
			} else {
				logger.info("文件不存在");
				f.createNewFile();// 不存在则创建
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if(verifyXML(fileName)){
			addXML(fileName, description, subject);
		}else{
			return false;
		}
		return true;
	}

	public void addXML(String fileName, String description, String subject) {
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			String configFilePath = ServletActionContext.getRequest()
					.getRealPath("/WEB-INF/template/");
			document = saxReader.read(new File(configFilePath
					+ File.separator+"template.xml"));
			Element element = (Element) document
					.selectSingleNode("/sinosoft/mailConfig");
			Element element1 = element.addElement(fileName);
			Element element2 = element1.addElement("description");
			element2.setText(description);
			Element element3 = element1.addElement("subject");
			element3.setText(subject);
			Element element4 = element1.addElement("templateFilePath");
			element4.setText("/WEB-INF/template/shop/" + fileName + ".ftl");

			// FileWriter fw = new FileWriter(new
			// File(configFilePath+"\\template.xml"));
			//FileWriter fw = new FileWriter(new File("e:\\template.xml"));
			// XMLWriter output = null;
			//
			// /** 格式化输出,类型IE浏览一样 */
			// OutputFormat format = OutputFormat.createPrettyPrint();
			// /** 指定XML字符集编码 */
			// format.setEncoding("UTF-8");
			// output = new XMLWriter(fw, format);
			// output.write(document);
			// output.close();

			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(
					new FileOutputStream(configFilePath + File.separator+"template.xml"),
					"utf-8"), format);
			writer.write(document);
			writer.close();

		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}

	}

	public static boolean verifyXML(String fileName) {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		String configFilePath = ServletActionContext.getRequest().getRealPath("/WEB-INF/template/");
		try {
			document = saxReader.read(new File(configFilePath
					+ File.separator+"template.xml"));
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		Element element = (Element) document
				.selectSingleNode("/sinosoft/mailConfig/"+fileName);
		if (element != null) {
			return false;
		}
		return true;
	}

}