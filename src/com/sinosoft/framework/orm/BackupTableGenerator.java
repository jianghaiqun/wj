/**
 * @Author 王育春
 * @Date 2006-7-2
 * @Mail wyuch@m165.com
 */
package com.sinosoft.framework.orm;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BackupTableGenerator {
	private static final Logger logger = LoggerFactory.getLogger(BackupTableGenerator.class);

	private String fileName;

	private String aID = "ID";

	private boolean isOracle = false;

	private Namespace nso;

	private Namespace nsc;

	private Namespace nsa;

	private int CurrentObjectID = 90000;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void toBackupTable() throws Exception {
		File f = new File(this.fileName);
		if (!f.exists()) {
			throw new RuntimeException("文件不存在");
		}
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(f);
		Element root = doc.getRootElement();
		nso = root.getNamespaceForPrefix("o");
		nsc = root.getNamespaceForPrefix("c");
		nsa = root.getNamespaceForPrefix("a");
		Element rootObject = root.element(new QName("RootObject", nso));
		Element children = rootObject.element(new QName("Children", nsc));
		Element model = children.element(new QName("Model", nso));
		if (model.attributeValue("ID") == null) {
			if (model.attributeValue("Id") != null) {
				aID = "Id";
			} else {
				throw new RuntimeException("ID属性名称未定，PDM版本不正确");
			}
		}
		List tables = model.element(new QName("Tables", nsc)).elements();
		for (int i = 0; i < tables.size(); i++) {
			try {
				dealOneTable((Element) tables.get(i));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			String backupFileName = this.fileName.substring(0,fileName.lastIndexOf('.'))+"_B"+fileName.substring(fileName.lastIndexOf('.'));
			XMLWriter output = new XMLWriter(new FileOutputStream(backupFileName), format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			logger.error(e.getMessage() ,e);
		}
	}

	private int getObjectID() {
		return CurrentObjectID++;
	}

	private void dealOneTable(Element table) {
		String tableCode = table.elementText(new QName("Code", nsa));

		checkCode(tableCode, "表代码");
		table.element(new QName("Name", nsa)).setText(table.elementText(new QName("Name", nsa)) + "备份");
		table.element(new QName("Code", nsa)).setText("B" + table.elementText(new QName("Code", nsa)));

		String CreationDate = table.elementText(new QName("CreationDate", nsa));
		String Creator = table.elementText(new QName("Creator", nsa));
		String ModificationDate = table.elementText(new QName("ModificationDate", nsa));
		String Modifier = table.elementText(new QName("Modifier", nsa));

		Element eColumns = table.element(new QName("Columns", nsc));
		if (eColumns == null) {
			return;
		}
		List columns = eColumns.elements();
		Element template = (Element) columns.get(0);
		String templateObjectID = template.elementText(new QName("ObjectID", nsa));
		templateObjectID = templateObjectID.substring(0, templateObjectID.length() - 5);
		// 备份号
		Element ele = eColumns.addElement(new QName("Column", nso));
		String objectID = "o" + getObjectID();
		ele.addAttribute(aID, objectID);
		ele.addElement(new QName("Name", nsa)).setText("备份编号");
		ele.addElement(new QName("Code", nsa)).setText("BackupNo");
		ele.addElement(new QName("CreationDate", nsa)).setText(CreationDate);
		ele.addElement(new QName("Creator", nsa)).setText(Creator);
		ele.addElement(new QName("ModificationDate", nsa)).setText(ModificationDate);
		ele.addElement(new QName("Modifier", nsa)).setText(Modifier);
		if (isOracle) {
			ele.addElement(new QName("DataType", nsa)).setText("varchar2(15)");
		} else {
			ele.addElement(new QName("DataType", nsa)).setText("varchar(15)");
		}
		ele.addElement(new QName("Length", nsa)).setText("15");
		ele.addElement(new QName("Mandatory", nsa)).setText("1");
		ele.addElement(new QName("ObjectID", nsa)).setText(templateObjectID + getObjectID());

		// 备份人
		ele = eColumns.addElement(new QName("Column", nso));
		ele.addAttribute(aID, "o" + getObjectID());
		ele.addElement(new QName("Name", nsa)).setText("备份人");
		ele.addElement(new QName("Code", nsa)).setText("BackupOperator");
		ele.addElement(new QName("CreationDate", nsa)).setText(CreationDate);
		ele.addElement(new QName("Creator", nsa)).setText(Creator);
		ele.addElement(new QName("ModificationDate", nsa)).setText(ModificationDate);
		ele.addElement(new QName("Modifier", nsa)).setText(Modifier);
		if (isOracle) {
			ele.addElement(new QName("DataType", nsa)).setText("varchar2(200)");
		} else {
			ele.addElement(new QName("DataType", nsa)).setText("varchar(200)");
		}
		ele.addElement(new QName("Length", nsa)).setText("200");
		ele.addElement(new QName("Mandatory", nsa)).setText("1");
		ele.addElement(new QName("ObjectID", nsa)).setText(templateObjectID + getObjectID());

		// 备份时间
		ele = eColumns.addElement(new QName("Column", nso));
		ele.addAttribute(aID, "o" + getObjectID());
		ele.addElement(new QName("Name", nsa)).setText("备份时间");
		ele.addElement(new QName("Code", nsa)).setText("BackupTime");
		ele.addElement(new QName("CreationDate", nsa)).setText(CreationDate);
		ele.addElement(new QName("Creator", nsa)).setText(Creator);
		ele.addElement(new QName("ModificationDate", nsa)).setText(ModificationDate);
		ele.addElement(new QName("Modifier", nsa)).setText(Modifier);
		ele.addElement(new QName("DataType", nsa)).setText("datetime");
		ele.addElement(new QName("Mandatory", nsa)).setText("1");
		ele.addElement(new QName("ObjectID", nsa)).setText(templateObjectID + getObjectID());

		// 备份备注
		ele = eColumns.addElement(new QName("Column", nso));
		ele.addAttribute(aID, "o" + getObjectID());
		ele.attribute(aID).setValue("o" + getObjectID());
		ele.addElement(new QName("Name", nsa)).setText("备份备注");
		ele.addElement(new QName("Code", nsa)).setText("BackupMemo");
		ele.addElement(new QName("CreationDate", nsa)).setText(CreationDate);
		ele.addElement(new QName("Creator", nsa)).setText(Creator);
		ele.addElement(new QName("ModificationDate", nsa)).setText(ModificationDate);
		ele.addElement(new QName("Modifier", nsa)).setText(Modifier);
		if (isOracle) {
			ele.addElement(new QName("DataType", nsa)).setText("varchar2(50)");
		} else {
			ele.addElement(new QName("DataType", nsa)).setText("varchar(50)");
		}
		ele.addElement(new QName("Length", nsa)).setText("50");
		ele.addElement(new QName("Mandatory", nsa)).setText("0");
		ele.addElement(new QName("ObjectID", nsa)).setText(templateObjectID + getObjectID());

		Element primaryKey = table.element(new QName("PrimaryKey", nsc));
		String keyRef = null;
		if (primaryKey != null) {
			primaryKey = primaryKey.element(new QName("Key", nso));
			if (primaryKey != null) {
				keyRef = primaryKey.attributeValue("Ref");
			}
		}
		if (keyRef != null) {
			List keys = table.element(new QName("Keys", nsc)).elements();
			boolean keyFlag = false;
			for (int i = 0; i < keys.size(); i++) {
				Element key = (Element) keys.get(i);
				if (keyRef.equals(key.attributeValue(aID))) {
					Element eKeyColumns = key.element(new QName("Key.Columns", nsc));
					List keyColumns = eKeyColumns.elements();
					Element keyTemplate = (Element) keyColumns.get(0);
					Element backupIDKey = keyTemplate.createCopy();
					backupIDKey.attribute("Ref").setValue(objectID);
					eKeyColumns.add(backupIDKey);
					keyFlag = true;
					break;
				}
			}
			if (!keyFlag) {
			}
		}
	}

	private boolean checkCode(String code, String msgPrefix) {
		char[] ca = code.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (i == 0) {
				if (!Character.isJavaIdentifierStart(ca[i])) {
					return false;
				}
			} else {
				if (!Character.isJavaIdentifierPart(ca[i])) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		BackupTableGenerator btg = new BackupTableGenerator();
		btg.setFileName("H:/Projects/SportYou/Doc/DataBase/MiddingCMS.pdm");
		try {
			btg.toBackupTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
