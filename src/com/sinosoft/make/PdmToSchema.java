package com.sinosoft.make;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PdmToSchema {
	private static final Logger logger = LoggerFactory.getLogger(PdmToSchema.class);
	private String txtPath; // 生成文件路径名称

	/**
	 * 解析Pdm文件,暂只支持格式 Physical Data Model (xml) (*.pdm)
	 * 
	 * @author hanming
	 * @version 1.0
	 */
	public Table[] parsePDM_VO(String filePath) {
		if (txtPath == null) {
			txtPath = filePath.substring(0, filePath.lastIndexOf("\\") + 1) + "sinosoft\\";

		}

		// 将pdm格式 Physical Data Model (xml) (*.pdm) 文件复制为*.xml格式
		String xmlFile = filePath.substring(0, filePath.lastIndexOf("\\") + 1) + "pdm.xml";
		File fileObject = new File(xmlFile);
		FileInputStream inStream;
		FileOutputStream outStream;
		try {
			fileObject.createNewFile();
			inStream = new FileInputStream(filePath);
			outStream = new FileOutputStream(xmlFile);
			copyContent(inStream, outStream);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		// 解析pdm.xml文件
		Table[] tabs = new Table[] {};
		List voS = new ArrayList();
		Table vo = null;
		Column[] cols = null;
		File f = new File(xmlFile);
		SAXReader sr = new SAXReader();
		Document doc = null;
		try {
			doc = sr.read(f);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		Iterator itr = doc.selectNodes("//c:Tables//o:Table").iterator();
		while (itr.hasNext()) {
			vo = new Table();
			cols = new Column[] {};
			List list = new ArrayList();
			Column col = null;
			Element e_table = (Element) itr.next();
			vo.setTableName(e_table.elementTextTrim("Name"));
			vo.setTableCode(e_table.elementTextTrim("Code"));
			Iterator itr1 = e_table.element("Columns").elements("Column").iterator();
			while (itr1.hasNext()) {
				col = new Column();
				Element e_col = (Element) itr1.next();
				String pkID = e_col.attributeValue("Id");
				col.setDefaultValue(e_col.elementTextTrim("DefaultValue"));
				col.setName(e_col.elementTextTrim("Name"));
				try {
					col.setType(e_col.elementTextTrim("DataType").substring(0, e_col.elementTextTrim("DataType").indexOf("(")));
				} catch (Exception ex) {
					col.setType(e_col.elementTextTrim("DataType"));
				}
				col.setCode(e_col.elementTextTrim("Code"));
				if (e_col.elementTextTrim("Length") == null) {
					col.setLength(null);
				} else {
					col.setLength(Integer.valueOf(e_col.elementTextTrim("Length")));
				}
				if (e_col.elementTextTrim("Mandatory") == null) {
					col.setMandatory(null);
					col.setMandatoryFlag(false);
				} else {
					col.setMandatory(e_col.elementTextTrim("Mandatory"));
					col.setMandatoryFlag(true);
				}
				if (e_col.elementTextTrim("Comment") == null) // 备注信息
				{
					col.setComment(null);
				} else {
					col.setComment(e_col.elementTextTrim("Comment"));
				}
				// 判断是否为主键
				String keys_key_id = e_table.element("Keys").element("Key").attributeValue("Id");
				String keys_primarykey_ref_id = e_table.element("PrimaryKey").element("Key").attributeValue("Ref");
				// String keys_key_column_column_ref =
				// e_table.element("Keys").element("Key").element("Key.Columns").element("Column").attributeValue("Ref");
				// //只得到第一个的
				Iterator ColumnItr = e_table.element("Keys").element("Key").element("Key.Columns").elementIterator("Column");
				while (ColumnItr.hasNext()) {
					Element oColumn = (Element) ColumnItr.next();
					String keys_key_column_column_ref = oColumn.attributeValue("Ref");
					// System.out.println(keys_key_id+"=="+keys_primarykey_ref_id+"&&"+keys_key_column_column_ref+"=="+pkID);
					try {
						if (keys_primarykey_ref_id.equals(keys_key_id) && keys_key_column_column_ref.equals(pkID)) {
							col.setPkFlag(true);
							vo.setPkField(col.getCode());
						}
					} catch (Exception ex) {
						System.err.println("%%% 警告:未设置主键字段... %%%");
					}
				}
				list.add(col);
			}
			vo.setCols((Column[]) list.toArray(cols));
			voS.add(vo);
		}
		return (Table[]) voS.toArray(tabs);
	}

	/**
	 * 文件内容的复制
	 * 
	 * @author hanming
	 * @version 1.0
	 */
	public void copyContent(FileInputStream inObj, FileOutputStream outObj) {
		int copyLen;
		byte[] copyBuf = new byte[1024];
		try {
			while ((copyLen = inObj.read(copyBuf, 0, 1024)) != -1) {
				String copyStr = new String(copyBuf);
				// System.out.println(copyStr);
				outObj.write(copyBuf, 0, copyLen);
			}
		} catch (IOException e) {
			logger.error("error: ", e.getMessage());
		}
	}

	/**
	 * 把表字段System.out出来
	 * 
	 * @author hanming
	 * @version 1.0
	 */
	public void initTable(Table[] tabs) {
		List list = new ArrayList();
		for (int i = 0; i < tabs.length; i++) {
			list.add(tabs[i].getTableName());
			Table tab = tabs[i];
			logger.info("========{}:{} Begin========", tabs[i].getTableName(), tabs[i].getTableCode());

			String strPKField = "";
			for (int j = 0; j < tab.getCols().length; j++) {
				logger.info("Name:{}", tab.getCols()[j].getName());
				logger.info("Code:{}", tab.getCols()[j].getCode());
				logger.info("DataType:{}", tab.getCols()[j].getType());
				logger.info("Length:{}", tab.getCols()[j].getLength());
				logger.info("PkFlag:{}", tab.getCols()[j].isPkFlag());
				logger.info("Mandatory:{}", tab.getCols()[j].getMandatory());
				logger.info("MandatoryFlag:{}", tab.getCols()[j].isMandatoryFlag());
				if (tab.getCols()[j].isPkFlag()) {
					strPKField = strPKField + tab.getCols()[j].getCode() + ";";
				}
			}
			logger.info("PKField:{}", strPKField);
			logger.info("========{} End========", tabs[i].getTableName());
		}
		logger.info("========处理的表如下:");
		for (int i = 0; i < list.size(); i++) {
			logger.info(String.valueOf(list.get(i)));
		}
	}

	/**
	 * 生成Schema文件
	 * 
	 * @author hanming
	 * @version 1.0
	 */
	public void makeSchemaAndSet(Table[] tabs) {
		if (!new File(txtPath).exists()) {
			new File(txtPath).mkdirs();

		}

		int pkNum = 0;
		// 每个schema头部相同信息
		String headStr = "package com.sinosoft.schema;" + "\n";
		headStr += "\n";
		headStr += "import com.sinosoft.framework.data.DataColumn;" + "\n";
		headStr += "import com.sinosoft.framework.orm.Schema;" + "\n";
		headStr += "import com.sinosoft.framework.orm.SchemaSet;" + "\n";
		headStr += "import com.sinosoft.framework.orm.SchemaColumn;" + "\n";
		headStr += "import com.sinosoft.framework.data.QueryBuilder;" + "\n";

		String headAddStr = "\n";

		String endStr = "}";

		for (int i = 0; i < tabs.length; i++) {
			boolean headStrAddDate = false;
			boolean headStrBigdecimal = false;
			Table tab = tabs[i];
			logger.info("{}:{}",tabs[i].getTableName(), tabs[i].getTableCode());

			String body1 = "";
			String body2 = "	public static final SchemaColumn[] _Columns = new SchemaColumn[] {" + "\n";
			String body3 = "	public static final String _TableCode = \"" + tabs[i].getTableCode() + "\";" + "\n\n";
			body3 += "	public static final String _NameSpace = \"com.sinosoft.schema\";" + "\n\n";
			body3 += "	protected static final String _InsertAllSQL = \"insert into " + tabs[i].getTableCode() + " values(";
			String body4 = "	protected static final String _UpdateAllSQL = \"update " + tabs[i].getTableCode() + " set ";
			String body5 = "";
			String body6 = "	public void setV(int i, Object v) {\n";
			String body7 = "	public Object getV(int i) {\n";
			String body8 = "";
			pkNum = 0;
			String strPKField = "";
			String PKStr = "";
			for (int j = 0; j < tab.getCols().length; j++) {
//				 System.out.println("Name:"+tab.getCols()[j].getName());
				// System.out.println("Code:"+tab.getCols()[j].getCode());
				// System.out.println("DataType:"+tab.getCols()[j].getType());
				// System.out.println("Length:"+tab.getCols()[j].getLength());
//				 System.out.println("PkFlag:"+tab.getCols()[j].isPkFlag());
				// System.out.println("Mandatory:"+tab.getCols()[j].getMandatory());
				// System.out.println("MandatoryFlag:"+tab.getCols()[j].isMandatoryFlag());
				// System.out.println("Comment:"+tab.getCols()[j].getComment());
				// System.out.println();
				logger.info(tab.getCols()[j].getName() + " - " + tab.getCols()[j].getCode() + " - " + tab.getCols()[j].isPkFlag());
				if (tab.getCols()[j].isPkFlag()) {
					pkNum++;
					strPKField += tab.getCols()[j].getCode() + ", ";
					if (PKStr.equals("")) {
						PKStr += "" + tab.getCols()[j].getCode() + "=?";
					} else {
						PKStr += " and " + tab.getCols()[j].getCode() + "=?";
					}
				}

				String type = tab.getCols()[j].getType();
				String DataColumnType = tab.getCols()[j].getType();
				String getsetType = tab.getCols()[j].getType();
				if (tab.getCols()[j].getType().trim().equalsIgnoreCase("varchar") || tab.getCols()[j].getType().trim().equalsIgnoreCase("varchar2")) {
					type = "String";
					DataColumnType = "STRING";
					getsetType = "String";

				} else if (tab.getCols()[j].getType().trim().equalsIgnoreCase("text") || tab.getCols()[j].getType().trim().equalsIgnoreCase("mediumtext")) {
					type = "String";
					DataColumnType = "CLOB";
					getsetType = "String";

				} else if (tab.getCols()[j].getType().trim().equalsIgnoreCase("datetime") || tab.getCols()[j].getType().trim().equalsIgnoreCase("date")) {
					type = "Date";
					DataColumnType = "DATETIME";
					getsetType = "Date";
					headStrAddDate = true;

				} else if (tab.getCols()[j].getType().trim().equalsIgnoreCase("bigint") || tab.getCols()[j].getType().trim().equalsIgnoreCase("long")) {
					type = "Long";
					DataColumnType = "LONG";
					getsetType = "long";

				} else if (tab.getCols()[j].getType().trim().equalsIgnoreCase("integer") || tab.getCols()[j].getType().trim().equalsIgnoreCase("int")) {
					type = "Integer";
					DataColumnType = "INTEGER";
					getsetType = "int";

				} else if (tab.getCols()[j].getType().trim().equalsIgnoreCase("double")  ) {
					type = "Double";
					DataColumnType = "DOUBLE";
					getsetType = "double";
					
				}  else if ( tab.getCols()[j].getType().trim().equalsIgnoreCase("decimal") ) {
					type = "BigDecimal";
					DataColumnType = "BigDecimal";
					getsetType = "BigDecimal";
					headStrBigdecimal = true;
					
				}  else if (tab.getCols()[j].getType().trim().equalsIgnoreCase("boolean")||tab.getCols()[j].getType().trim().equalsIgnoreCase("bit")) {
					type = "Boolean";
					DataColumnType = "BOOLEAN";
					getsetType = "boolean";
				}

				String length = "";
				if (tab.getCols()[j].getLength() == null || tab.getCols()[j].getLength().equals("null")) {
					length = "0";
				} else {
					length = tab.getCols()[j].getLength().toString();
				}
				body1 += "	private " + type + " " + tab.getCols()[j].getCode() + ";" + "\n\n";
				if (j == (tab.getCols().length - 1)) {
					body2 += "		new SchemaColumn(\"" + tab.getCols()[j].getCode() + "\", DataColumn." + DataColumnType.toUpperCase() + ", " + j + ", " + length + " , 0 , "
							+ tab.getCols()[j].isMandatoryFlag() + " , " + tab.getCols()[j].isPkFlag() + ")" + "\n";
					body3 += "?)\";" + "\n\n";
					body4 += "" + tab.getCols()[j].getCode() + "=?";
				} else {
					body2 += "		new SchemaColumn(\"" + tab.getCols()[j].getCode() + "\", DataColumn." + DataColumnType.toUpperCase() + ", " + j + ", " + length + " , 0 , "
							+ tab.getCols()[j].isMandatoryFlag() + " , " + tab.getCols()[j].isPkFlag() + ")," + "\n";
					body3 += "?,";
					body4 += "" + tab.getCols()[j].getCode() + "=?,";
				}
				if ( type.equals("Integer") || type.equals("Boolean") || type.equals("Long")) {
					body6 += "		if (i == " + j + "){if(v==null){" + tab.getCols()[j].getCode() + " = null;}else{" + tab.getCols()[j].getCode() + " = new " + type + "(v.toString());}return;}\n";
					
				
				} else if ( type.equals("BigDecimal") ) {
					body6 += "		if (i == " + j + "){if(v==null){" + tab.getCols()[j].getCode() + " = null;}else{" + tab.getCols()[j].getCode() + " =  ((BigDecimal)v) ;}return;}\n";
				
				}  else {
					body6 += "		if (i == " + j + "){" + tab.getCols()[j].getCode() + " = (" + type + ")v;return;}\n";
				
				}
				body7 += "		if (i == " + j + "){return " + tab.getCols()[j].getCode() + ";}\n";
				String dataTypestr = tab.getCols()[j].getType();
				if (tab.getCols()[j].getLength() != null) {
					dataTypestr = tab.getCols()[j].getType() + "(" + tab.getCols()[j].getLength() + ")";
				}
				String CommentStr = "";
				if (tab.getCols()[j].getComment() != null && !tab.getCols()[j].getComment().equals("")) {
					CommentStr = "	* 备注信息 :<br>\n";
					CommentStr += "	" + tab.getCols()[j].getComment() + "<br>\n";
					logger.info(CommentStr);
				}

				if (type.equals("Long") || type.equals("Integer")) {
					body8 += "	/**\n" + "	* 获取字段" + tab.getCols()[j].getCode() + "的值，该字段的<br>\n" + "	* 字段名称 :" + tab.getCols()[j].getName() + "<br>\n" + "	* 数据类型 :" + dataTypestr + "<br>\n"
							+ "	* 是否主键 :" + tab.getCols()[j].isPkFlag() + "<br>\n" + "	* 是否必填 :" + tab.getCols()[j].isMandatoryFlag() + "<br>\n" + CommentStr + "	*/\n" + "	public " + getsetType
							+ " get" + tab.getCols()[j].getCode() + "() {\n" + "		if(" + tab.getCols()[j].getCode() + "==null){return 0;}\n" + "		return " + tab.getCols()[j].getCode() + "."
							+ getsetType + "Value();\n" + "	}\n\n" + "	/**\n" + "	* 设置字段" + tab.getCols()[j].getCode() + "的值，该字段的<br>\n" + "	* 字段名称 :" + tab.getCols()[j].getName() + "<br>\n"
							+ "	* 数据类型 :" + dataTypestr + "<br>\n" + "	* 是否主键 :" + tab.getCols()[j].isPkFlag() + "<br>\n" + "	* 是否必填 :" + tab.getCols()[j].isMandatoryFlag() + "<br>\n" + CommentStr
							+ "	*/\n" + "	public void set" + tab.getCols()[j].getCode() + "(" + getsetType + " " + (tab.getCols()[j].getCode().substring(0, 1)).toLowerCase()
							+ tab.getCols()[j].getCode().substring(1) + ") {\n" + "		this." + tab.getCols()[j].getCode() + " = new " + type + "("
							+ (tab.getCols()[j].getCode().substring(0, 1)).toLowerCase() + tab.getCols()[j].getCode().substring(1) + ");\n" + "    }\n\n" + "	/**\n" + "	* 设置字段"
							+ tab.getCols()[j].getCode() + "的值，该字段的<br>\n" + "	* 字段名称 :" + tab.getCols()[j].getName() + "<br>\n" + "	* 数据类型 :" + dataTypestr + "<br>\n" + "	* 是否主键 :"
							+ tab.getCols()[j].isPkFlag() + "<br>\n" + "	* 是否必填 :" + tab.getCols()[j].isMandatoryFlag() + "<br>\n" + CommentStr + "	*/\n" + "	public void set"
							+ tab.getCols()[j].getCode() + "(String " + (tab.getCols()[j].getCode().substring(0, 1)).toLowerCase() + tab.getCols()[j].getCode().substring(1) + ") {\n" + "		if ("
							+ (tab.getCols()[j].getCode().substring(0, 1)).toLowerCase() + tab.getCols()[j].getCode().substring(1) + " == null){\n" + "			this." + tab.getCols()[j].getCode()
							+ " = null;\n" + "			return;\n" + "		}\n" + "		this." + tab.getCols()[j].getCode() + " = new " + type + "(" + (tab.getCols()[j].getCode().substring(0, 1)).toLowerCase()
							+ tab.getCols()[j].getCode().substring(1) + ");\n" + "    }\n\n";
				} else {
					body8 += "	/**\n" + "	* 获取字段" + tab.getCols()[j].getCode() + "的值，该字段的<br>\n" + "	* 字段名称 :" + tab.getCols()[j].getName() + "<br>\n" + "	* 数据类型 :" + dataTypestr + "<br>\n"
							+ "	* 是否主键 :" + tab.getCols()[j].isPkFlag() + "<br>\n" + "	* 是否必填 :" + tab.getCols()[j].isMandatoryFlag() + "<br>\n" + CommentStr + "	*/\n" + "	public " + getsetType
							+ " get" + tab.getCols()[j].getCode() + "() {\n" + "		return " + tab.getCols()[j].getCode() + ";\n" + "	}\n\n" + "	/**\n" + "	* 设置字段" + tab.getCols()[j].getCode()
							+ "的值，该字段的<br>\n" + "	* 字段名称 :" + tab.getCols()[j].getName() + "<br>\n" + "	* 数据类型 :" + dataTypestr + "<br>\n" + "	* 是否主键 :" + tab.getCols()[j].isPkFlag() + "<br>\n"
							+ "	* 是否必填 :" + tab.getCols()[j].isMandatoryFlag() + "<br>\n" + CommentStr + "	*/\n" + "	public void set" + tab.getCols()[j].getCode() + "(" + getsetType + " "
							+ (tab.getCols()[j].getCode().substring(0, 1)).toLowerCase() + tab.getCols()[j].getCode().substring(1) + ") {\n" + "		this." + tab.getCols()[j].getCode() + " = "
							+ (tab.getCols()[j].getCode().substring(0, 1)).toLowerCase() + tab.getCols()[j].getCode().substring(1) + ";\n" + "    }\n\n";
				}

			}
			if (headStrAddDate) {
				headAddStr += "import java.util.Date;" + "\n";
				headAddStr += "\n";
			}
			
			if (headStrBigdecimal) {
				headAddStr += "import java.math.BigDecimal;" + "\n";
				headAddStr += "\n";
			}
			

			strPKField = strPKField.substring(0, strPKField.length() - 2);
			String bodyheadStr = "/**" + "\n" + " * 表名称：" + tabs[i].getTableName() + "<br>" + "\n" + " * 表代码：" + tabs[i].getTableCode() + "<br>" + "\n" + " * 表主键：" + strPKField + "<br>" + "\n"
					+ " */" + "\n";
			bodyheadStr += "public class " + tabs[i].getTableCode() + "Schema extends Schema {" + "\n";

			body2 += "	};" + "\n\n";
			body4 += " where " + PKStr + "\";" + "\n\n";
			body4 += "	protected static final String _DeleteSQL = \"delete from " + tabs[i].getTableCode() + "  where " + PKStr + "\";" + "\n\n";
			body4 += "	protected static final String _FillAllSQL = \"select * from " + tabs[i].getTableCode() + "  where " + PKStr + "\";" + "\n\n";
			body5 += "	public " + tabs[i].getTableCode() + "Schema(){\n" + "		TableCode = _TableCode;\n" + "		NameSpace = _NameSpace;\n" + "		Columns = _Columns;\n"
					+ "		InsertAllSQL = _InsertAllSQL;\n" + "		UpdateAllSQL = _UpdateAllSQL;\n" + "		DeleteSQL = _DeleteSQL;\n" + "		FillAllSQL = _FillAllSQL;\n" + "		HasSetFlag = new boolean["
					+ tab.getCols().length + "];\n" + "	}" + "\n\n";
			body5 += "	protected Schema newInstance(){\n" + "		return new " + tabs[i].getTableCode() + "Schema();\n" + "	}\n\n" + "	protected SchemaSet newSet(){\n" + "		return new "
					+ tabs[i].getTableCode() + "Set();\n" + "	}\n\n" + "	public " + tabs[i].getTableCode() + "Set query() {\n" + "		return query(null, -1, -1);\n" + "	}\n\n" + "	public "
					+ tabs[i].getTableCode() + "Set query(QueryBuilder qb) {\n" + "		return query(qb, -1, -1);\n" + "	}\n\n" + "	public " + tabs[i].getTableCode()
					+ "Set query(int pageSize, int pageIndex) {\n" + "		return query(null, pageSize, pageIndex);\n" + "	}\n\n" + "	public " + tabs[i].getTableCode()
					+ "Set query(QueryBuilder qb , int pageSize, int pageIndex){\n" + "		return (" + tabs[i].getTableCode() + "Set)querySet(qb , pageSize , pageIndex);\n" + "	}\n\n";
			body6 += "	}\n\n";
			body7 += "		return null;\n" + "	}\n\n";

			// System.out.println("PKField:"+strPKField);
			try {
				PrintWriter printwriter2 = new PrintWriter(new FileOutputStream(txtPath + tabs[i].getTableCode() + "Schema.java"));
				printwriter2.print(headStr + headAddStr + bodyheadStr + body1 + body2 + body3 + body4 + body5 + body6 + body7 + body8 + endStr);
				printwriter2.close();
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			} // 生成文件

			String setHeadStr = "package com.sinosoft.schema;\n\n";
			setHeadStr += "import com.sinosoft.schema." + tabs[i].getTableCode() + "Schema;\n";
			setHeadStr += "import com.sinosoft.framework.orm.SchemaSet;\n\n";

			String setBodyStr = "public class " + tabs[i].getTableCode() + "Set extends SchemaSet {\n";
			setBodyStr += "	public " + tabs[i].getTableCode() + "Set() {\n";
			setBodyStr += "		this(10,0);\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	public " + tabs[i].getTableCode() + "Set(int initialCapacity) {\n";
			setBodyStr += "		this(initialCapacity,0);\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	public " + tabs[i].getTableCode() + "Set(int initialCapacity,int capacityIncrement) {\n";
			setBodyStr += "		super(initialCapacity,capacityIncrement);\n";
			setBodyStr += "		TableCode = " + tabs[i].getTableCode() + "Schema._TableCode;\n";
			setBodyStr += "		Columns = " + tabs[i].getTableCode() + "Schema._Columns;\n";
			setBodyStr += "		NameSpace = " + tabs[i].getTableCode() + "Schema._NameSpace;\n";
			setBodyStr += "		InsertAllSQL = " + tabs[i].getTableCode() + "Schema._InsertAllSQL;\n";
			setBodyStr += "		UpdateAllSQL = " + tabs[i].getTableCode() + "Schema._UpdateAllSQL;\n";
			setBodyStr += "		FillAllSQL = " + tabs[i].getTableCode() + "Schema._FillAllSQL;\n";
			setBodyStr += "		DeleteSQL = " + tabs[i].getTableCode() + "Schema._DeleteSQL;\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	protected SchemaSet newInstance(){\n";
			setBodyStr += "		return new " + tabs[i].getTableCode() + "Set();\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	public boolean add(" + tabs[i].getTableCode() + "Schema aSchema) {\n";
			setBodyStr += "		return super.add(aSchema);\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	public boolean add(" + tabs[i].getTableCode() + "Set aSet) {\n";
			setBodyStr += "		return super.add(aSet);\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	public boolean remove(" + tabs[i].getTableCode() + "Schema aSchema) {\n";
			setBodyStr += "		return super.remove(aSchema);\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	public " + tabs[i].getTableCode() + "Schema get(int index) {\n";
			setBodyStr += "		" + tabs[i].getTableCode() + "Schema tSchema = (" + tabs[i].getTableCode() + "Schema) super.getObject(index);\n";
			setBodyStr += "		return tSchema;\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	public boolean set(int index, " + tabs[i].getTableCode() + "Schema aSchema) {\n";
			setBodyStr += "		return super.set(index, aSchema);\n";
			setBodyStr += "	}\n\n";
			setBodyStr += "	public boolean set(" + tabs[i].getTableCode() + "Set aSet) {\n";
			setBodyStr += "		return super.set(aSet);\n";
			setBodyStr += "	}\n";

			String setEndStr = "}\n";
			setEndStr += " ";

			try {
				PrintWriter printwriter2 = new PrintWriter(new FileOutputStream(txtPath + tabs[i].getTableCode() + "Set.java"));
				printwriter2.print(setHeadStr + setBodyStr + setEndStr);
				printwriter2.close();
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			} // 生成文件

		}
	}

	/**
	 * 把表字段System.out出来,并和数据库进行比较,将不同点System.out出来.
	 * 
	 * @author hanming
	 * @version 1.0
	 */
	public void compareTab_DB(Table[] tabs) {
		int pkNum = 0;
		String[] pkArr; // 存放主键的数组
		String[][] tableArr; // 存放表字段信息的数组 Name|Code|DataType|Length
		for (int i = 0; i < tabs.length; i++) {
			Table tab = tabs[i];
			// System.out.println(tabs[i].getTableName() + ":" +
			// tabs[i].getTableCode());
			pkNum = 0;
			tableArr = new String[(tab.getCols().length)][3]; // 初始化
			String strPKField = "";
			for (int j = 0; j < tab.getCols().length; j++) {
				// System.out.println("Name:" + tab.getCols()[j].getName());
				// System.out.println("Code:" + tab.getCols()[j].getCode());
				// System.out.println("DataType:" + tab.getCols()[j].getType());
				// System.out.println("Length:" + tab.getCols()[j].getLength());
				// System.out.println("PkFlag:" + tab.getCols()[j].isPkFlag());
				// System.out.println("Mandatory:" +
				// tab.getCols()[j].getMandatory());
				// System.out.println("MandatoryFlag:" +
				// tab.getCols()[j].isMandatoryFlag());
				// System.out.println();
				tableArr[j][0] = tab.getCols()[j].getCode();
				tableArr[j][1] = tab.getCols()[j].getType();
				tableArr[j][2] = String.valueOf(tab.getCols()[j].getLength());
				if (tab.getCols()[j].isPkFlag()) {
					pkNum++;
					strPKField = strPKField + tab.getCols()[j].getCode() + ";";
				}
			}
			// System.out.println("PKField:" + strPKField);

			pkArr = new String[pkNum];
			pkArr = strPKField.split(";");
			compareDB(tabs[i].getTableCode(), tableArr, pkArr);

			// System.out.println(tabs[i].getTableName() + " End ");
			// System.out.println();
		}
	}

	/**
	 * compareTab_DB 中调用 实现校验功能如下: 数据库中表:LLCase字段数量不匹配 DB中字段BANKCHANGE在PDM中不存在
	 * 字段NOTICEWAY数据类型不一致,PDM:date DB:VARCHAR2 varchar2字段NREAMIL数据长度不一致,PDM:2
	 * DB:1 主键是否匹配
	 * 
	 * @author hanming
	 * @version 1.0
	 */
	public void compareDB(String TableName, String[][] tableArr, String[] pkArr) {
		// ExeSQL mExeSQL = new ExeSQL();
		// String colSql =
		// " select column_name,data_type,data_length from user_tab_columns c where c.table_name = upper('"+TableName+"') ";
		// SSRS colSSRS = mExeSQL.execSQL(colSql);
		// if(colSSRS.getMaxRow()>0)
		// {
		// if(tableArr.length!=colSSRS.getMaxRow())
		// {
		// System.out.println("数据库中表:"+TableName+"字段数量不匹配");
		// }
		// String column_name="";
		// String data_type="";
		// String data_length="";
		// for(int i = 1; i <= colSSRS.getMaxRow(); i++)
		// {
		// column_name = colSSRS.GetText(i, 1);
		// data_type = colSSRS.GetText(i, 2);
		// data_length = colSSRS.GetText(i, 3);
		// boolean findFlag = false;
		// for(int j=0;j<tableArr.length;j++)
		// {
		// if(tableArr[j][0]!=null &&
		// tableArr[j][0].equalsIgnoreCase(column_name))
		// {
		// //非INTEGER判断
		// if(!tableArr[j][1].equalsIgnoreCase(data_type) &&
		// !tableArr[j][1].equals("INTEGER"))
		// {
		// System.out.println("字段"+column_name+"数据类型不一致,PDM:"+tableArr[j][1]+" DB:"+data_type);
		// }
		// //INTEGER判断
		// if(!tableArr[j][1].equalsIgnoreCase(data_type) &&
		// tableArr[j][1].equals("INTEGER") && data_type.equals("NUMBER"))
		// {
		// if(!data_length.equals("22"))
		// {
		// System.out.println("字段"+column_name+"数据类型不一致,PDM:"+tableArr[j][1]+" DB:"+data_type);
		// }
		// }
		// //VARCHAR2 长度判断
		// if(tableArr[j][1].equalsIgnoreCase("VARCHAR2"))
		// {
		// if(!tableArr[j][2].equals(data_length))
		// {
		// System.out.println("字段"+column_name+"数据长度不一致,PDM:"+tableArr[j][2]+" DB:"+data_length);
		// }
		// }
		// //NUMBER 长度判断(待完善)
		//					
		// findFlag = true;
		// break;
		// }
		// }
		// if(!findFlag)
		// {
		// System.out.println("DB中字段"+column_name+"在PDM中不存在");
		// }
		// }
		//		
		// //主键判断
		// String pkSQL =
		// " select cu.Constraint_name,cu.Column_Name from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' and au.table_name = upper('"+TableName+"')";
		// SSRS pkSSRS = mExeSQL.execSQL(pkSQL);
		// if(pkSSRS.getMaxRow()>0)
		// {
		// if(pkArr.length!=pkSSRS.getMaxRow())
		// {
		// System.out.println("数据库中表:"+TableName+"主键数量不匹配");
		// }
		// String pkCode ="";
		// for(int i = 1; i <= pkSSRS.getMaxRow(); i++)
		// {
		// boolean findFlag = false;
		// pkCode = pkSSRS.GetText(i, 2);
		// for(int j=0;j<pkArr.length;j++)
		// {
		// if(pkCode.equalsIgnoreCase(pkArr[j]))
		// {
		// findFlag = true;
		// break;
		// }
		// }
		// if(!findFlag)
		// {
		// System.out.println("DB中主键字段"+pkCode+"在PDM中不是主键");
		// }
		// }
		// }
		// else
		// {
		// System.out.println("数据库中表:"+TableName+"没有主键");
		// }
		// }
		// else
		// {
		// System.out.println("数据库中没有找到表:"+TableName);
		// }

	}

}
