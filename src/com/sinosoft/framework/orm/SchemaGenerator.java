package com.sinosoft.framework.orm;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.FileUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class SchemaGenerator {
	private static final Logger logger = LoggerFactory.getLogger(SchemaGenerator.class);
	private String fileName;

	private String outputDir;

	private String namespace;

	private String aID = "ID";

	private Namespace nso;

	private Namespace nsc;

	private Namespace nsa;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setOutputDir(String dir) {
		this.outputDir = dir;
	}

	public void generate() throws Exception {
		File f = new File(this.fileName);
		if (!f.exists()) {
			throw new RuntimeException(f.getAbsolutePath() + "文件不存在");
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
				generateOneTable((Element) tables.get(i));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void generateOneTable(Element table) {
		String tableName = table.elementText(new QName("Name", nsa));
		String tableCode = table.elementText(new QName("Code", nsa));
		String tableComment = table.elementText(new QName("Comment", nsa));
		// System.out.println(tableName + "," + tableCode);
		Element eColumns = table.element(new QName("Columns", nsc));
		if (eColumns == null) {
			logger.error("Error:没有为表{}定义列!", tableCode);
			return;
		}
		List columns = eColumns.elements();
		SchemaColumn[] scs = new SchemaColumn[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			SchemaColumn sc = new SchemaColumn();
			Element column = (Element) columns.get(i);
			sc.ID = column.attributeValue(aID);
			sc.Name = column.elementText(new QName("Name", nsa));
			sc.Code = column.elementText(new QName("Code", nsa));
			sc.Comment = column.elementText(new QName("Comment", nsa));
			sc.DataType = column.elementText(new QName("DataType", nsa));
			String length = column.elementText(new QName("Length", nsa));
			try {
				if (length != null && !length.equals(""))
					sc.Length = Integer.parseInt(length);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			String precision = column.elementText(new QName("Precision", nsa));
			try {
				if (precision != null && !precision.equals(""))
					sc.Precision = Integer.parseInt(precision);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			String mandatory = column.elementText(new QName("Mandatory", nsa));
			if (mandatory == null || mandatory.equals("") || mandatory.equals("0")) {
				sc.Mandatory = false;
			} else {
				sc.Mandatory = true;
			}
			scs[i] = sc;
			// System.out.println(" "+sc.Name+","+sc.Code);
		}
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
					List keyColumns = key.element(new QName("Key.Columns", nsc)).elements();
					for (int j = 0; j < keyColumns.size(); j++) {
						String columnID = ((Element) keyColumns.get(j)).attributeValue("Ref");
						for (int k = 0; k < scs.length; k++) {
							if (scs[k].ID.equals(columnID)) {
								scs[k].isPrimaryKey = true;
							}
						}
					}
					keyFlag = true;
					break;
				}
			}
			if (!keyFlag) {
				logger.error("表{}未找到主键!", tableCode);
			}
		}
		// 以下生成文件
		if (!checkCode(tableCode, "表代码")) {
			return;
		}

		StringBuffer sb = new StringBuffer();
		StringBuffer dsb = new StringBuffer();// 存放字段声明
		StringBuffer hsb = new StringBuffer();// 存放初始化函数
		StringBuffer svsb = new StringBuffer();// 存放setV函数
		StringBuffer gvsb = new StringBuffer();// 存放getV函数
		StringBuffer csb = new StringBuffer();// 存放setter\getter
		StringBuffer isb = new StringBuffer();// 存放import部分
		StringBuffer insertsb = new StringBuffer();
		StringBuffer updatesb = new StringBuffer();
		StringBuffer pksb = new StringBuffer();
		StringBuffer keysb = new StringBuffer(); // 存放主键的字符串,如UserName,RoleCode

		sb.append("package " + this.namespace + ";\n\n");

		isb.append("import com.sinosoft.framework.data.DataColumn;\n");
		isb.append("import com.sinosoft.framework.orm.Schema;\n");
		isb.append("import com.sinosoft.framework.orm.SchemaSet;\n");
		isb.append("import com.sinosoft.framework.orm.SchemaColumn;\n");
		isb.append("import com.sinosoft.framework.data.QueryBuilder;\n");

		hsb.append("\tpublic static final SchemaColumn[] _Columns = new SchemaColumn[] {\n");

		svsb.append("\tpublic void setV(int i, Object v) {\n");
		gvsb.append("\tpublic Object getV(int i) {\n");

		insertsb.append("\tprotected static final String _InsertAllSQL = \"insert into " + tableCode + " values(");
		updatesb.append("\tprotected static final String _UpdateAllSQL = \"update " + tableCode + " set ");
		pksb.append(" where ");

		boolean dateFlag = false;
		boolean blobFlag = false;
		boolean firstPKFlag = true;
		for (int i = 0; i < scs.length; i++) {
			String code = scs[i].Code;
			if (i == 0) {
				insertsb.append("?");
				updatesb.append(code + "=?");
			} else {
				insertsb.append(",?");
				updatesb.append("," + code + "=?");
			}
			if (scs[i].isPrimaryKey) {
				if (firstPKFlag) {
					pksb.append(code + "=?");
					keysb.append(code);
					firstPKFlag = false;
				} else {
					pksb.append(" and " + code + "=?");
					keysb.append(", " + code);
				}
			}
			if (!checkCode(code, "表" + tableCode + "的字段")) {
				return;
			}
			String dataType = scs[i].DataType;
			if (dataType == null || dataType.equals("")) {
				String[] arrArg = {fileName, tableCode, code};
				logger.error("Error:{}中表{}的字段{}的数据类型未定义!", arrArg);
				return;
			}
			String type = dataType.toLowerCase().trim();
			String ctype = null;
			String vtype = null;
			if (type.startsWith("nvarchar") || type.startsWith("varchar") || type.startsWith("char")
					|| type.startsWith("nchar") || type.startsWith("enum")) {
				type = "String";
				ctype = "STRING";
				vtype = type;
			} else if (type.startsWith("long varchar") || type.startsWith("ntext") || type.startsWith("text")
					|| type.startsWith("mediumtext") || type.startsWith("longtext")) {
				type = "String";
				ctype = "CLOB";
				vtype = type;
			} else if (type.startsWith("blob")) {
				type = "byte[] ";
				ctype = "BLOB";
				vtype = type;
			} else if (type.startsWith("int") || type.startsWith("bit") || type.startsWith("smallint")
					|| type.startsWith("tinyint") || type.startsWith("mediumint")) {
				type = "int";
				ctype = "INTEGER";
				vtype = "Integer";
			} else if (type.startsWith("long") || type.startsWith("bigint")) {
				type = "long";
				ctype = "LONG";
				vtype = "Long";
			} else if (type.startsWith("float")) {
				type = "float";
				ctype = "FLOAT";
				vtype = "Float";
			} else if (type.startsWith("double") || type.startsWith("decimal") || type.startsWith("number")) {
				type = "double";
				ctype = "DOUBLE";
				vtype = "Double";
			} else if (type.startsWith("blob") || type.startsWith("clob")) {
				type = "Blob";
				ctype = "BLOB";
				vtype = type;
				blobFlag = true;
			} else if (type.startsWith("date") || type.startsWith("time")) {
				type = "Date";
				ctype = "DATETIME";
				vtype = type;
				dateFlag = true;
			} else {
				logger.error("{}：不支持的数据类型{}", tableCode, type);
				return;
			}
			dsb.append("\tprivate " + vtype + " " + scs[i].Code + ";\n\n");

			csb.append("\t/**\n");
			csb.append("\t* 获取字段" + code + "的值，该字段的<br>\n");
			csb.append("\t* 字段名称 :" + scs[i].Name + "<br>\n");
			csb.append("\t* 数据类型 :" + scs[i].DataType + "<br>\n");
			csb.append("\t* 是否主键 :" + scs[i].isPrimaryKey + "<br>\n");
			csb.append("\t* 是否必填 :" + scs[i].Mandatory + "<br>\n");
			if (scs[i].Comment != null) {
				csb.append("\t* 备注信息 :<br>\n");
				splitComment(csb, scs[i].Comment, "\t");
			}
			csb.append("\t*/\n");
			csb.append("\tpublic " + type + " get" + code + "() {\n");
			if (vtype.equals("Float") || vtype.equals("Integer") || vtype.equals("Long") || vtype.equals("Double")) {
				csb.append("\t\tif(" + code + "==null){return 0;}\n");
				csb.append("\t\treturn " + code + "." + type + "Value();\n");
			} else {
				csb.append("\t\treturn " + code + ";\n");
			}
			csb.append("\t}\n\n");

			csb.append("\t/**\n");
			csb.append("\t* 设置字段" + code + "的值，该字段的<br>\n");
			csb.append("\t* 字段名称 :" + scs[i].Name + "<br>\n");
			csb.append("\t* 数据类型 :" + scs[i].DataType + "<br>\n");
			csb.append("\t* 是否主键 :" + scs[i].isPrimaryKey + "<br>\n");
			csb.append("\t* 是否必填 :" + scs[i].Mandatory + "<br>\n");
			if (scs[i].Comment != null) {
				csb.append("\t* 备注信息 :<br>\n");
				splitComment(csb, scs[i].Comment, "\t");
			}
			csb.append("\t*/\n");

			String tCode = code.substring(0, 1).toLowerCase() + code.substring(1);
			csb.append("\tpublic void set" + code + "(" + type + " " + tCode + ") {\n");
			if (vtype.equals("Float") || vtype.equals("Integer") || vtype.equals("Long") || vtype.equals("Double")) {
				csb.append("\t\tthis." + code + " = new " + vtype + "(" + tCode + ");\n");
			} else {
				csb.append("\t\tthis." + code + " = " + tCode + ";\n");
			}
			csb.append("    }\n\n");

			// 重载方法
			if (vtype.equals("Float") || vtype.equals("Integer") || vtype.equals("Long") || vtype.equals("Double")) {
				csb.append("\t/**\n");
				csb.append("\t* 设置字段" + code + "的值，该字段的<br>\n");
				csb.append("\t* 字段名称 :" + scs[i].Name + "<br>\n");
				csb.append("\t* 数据类型 :" + scs[i].DataType + "<br>\n");
				csb.append("\t* 是否主键 :" + scs[i].isPrimaryKey + "<br>\n");
				csb.append("\t* 是否必填 :" + scs[i].Mandatory + "<br>\n");
				if (scs[i].Comment != null) {
					csb.append("\t* 备注信息 :<br>\n");
					splitComment(csb, scs[i].Comment, "\t");
				}
				csb.append("\t*/\n");
				csb.append("\tpublic void set" + code + "(String " + tCode + ") {\n");
				csb.append("\t\tif (" + tCode + " == null){\n");
				csb.append("\t\t\tthis." + code + " = null;\n");
				csb.append("\t\t\treturn;\n");
				csb.append("\t\t}\n");
				csb.append("\t\tthis." + code + " = new " + vtype + "(" + tCode + ");\n");
				csb.append("    }\n\n");
			}

			hsb.append("\t\tnew SchemaColumn(\"" + code + "\", DataColumn." + ctype + ", " + i + ", " + scs[i].Length
					+ " , " + scs[i].Precision + " , " + scs[i].Mandatory + " , " + scs[i].isPrimaryKey + ")");
			if (i < scs.length - 1) {
				hsb.append(",\n");
			} else {
				hsb.append("\n");
			}

			if (vtype.equals("Float") || vtype.equals("Integer") || vtype.equals("Long") || vtype.equals("Double")) {
				svsb.append("\t\tif (i == " + i + "){if(v==null){" + code + " = null;}else{" + code + " = new " + vtype
						+ "(v.toString());}return;}\n");
			} else {
				svsb.append("\t\tif (i == " + i + "){" + code + " = (" + vtype + ")v;return;}\n");
			}
			gvsb.append("\t\tif (i == " + i + "){return " + code + ";}\n");
		}

		if (dateFlag) {
			isb.append("import java.util.Date;\n");
		}
		if (blobFlag) {
			isb.append("import com.sinosoft.framework.orm.Blob;\n");
		}
		isb.append("\n");
		sb.append(isb);

		sb.append("/**\n");
		sb.append(" * 表名称：" + tableName);
		sb.append("<br>\n * 表代码：" + tableCode);
		if (tableComment != null) {
			sb.append("<br>\n * 表备注：<br>\n" + tableComment);
			splitComment(sb, tableComment, "");
		}
		sb.append("<br>\n * 表主键：" + keysb);
		sb.append("<br>\n */\n");
		sb.append("public class " + tableCode + "Schema extends Schema {\n");

		sb.append(dsb);

		hsb.append("\t};\n\n");

		// 复制几个变量引用，主要是便于在Schema类中统一处理
		hsb.append("\tpublic static final String _TableCode = \"" + tableCode + "\";\n\n");
		hsb.append("\tpublic static final String _NameSpace = \"" + this.namespace + "\";\n\n");
		insertsb.append(")\";\n\n");
		updatesb.append("");
		updatesb.append(pksb);
		updatesb.append("\";\n\n");
		hsb.append(insertsb);
		hsb.append(updatesb);
		hsb.append("\tprotected static final String _DeleteSQL = \"delete from " + tableCode + " " + pksb.toString()
				+ "\";\n\n");
		hsb.append("\tprotected static final String _FillAllSQL = \"select * from " + tableCode + " " + pksb.toString()
				+ "\";\n\n");

		// 构造函数
		hsb.append("\tpublic " + tableCode + "Schema(){\n");
		hsb.append("\t\tTableCode = _TableCode;\n");
		hsb.append("\t\tNameSpace = _NameSpace;\n");
		hsb.append("\t\tColumns = _Columns;\n");
		hsb.append("\t\tInsertAllSQL = _InsertAllSQL;\n");
		hsb.append("\t\tUpdateAllSQL = _UpdateAllSQL;\n");
		hsb.append("\t\tDeleteSQL = _DeleteSQL;\n");
		hsb.append("\t\tFillAllSQL = _FillAllSQL;\n");
		hsb.append("\t\tHasSetFlag = new boolean[" + scs.length + "];\n");
		hsb.append("\t}\n\n");

		// newInstance函数
		hsb.append("\tprotected Schema newInstance(){\n");
		hsb.append("\t\treturn new " + tableCode + "Schema();\n");
		hsb.append("\t}\n\n");

		// newSet函数
		hsb.append("\tprotected SchemaSet newSet(){\n");
		hsb.append("\t\treturn new " + tableCode + "Set();\n");
		hsb.append("\t}\n\n");

		// query函数:QueryBuilder
		hsb.append("\tpublic " + tableCode + "Set query() {\n");
		hsb.append("\t\treturn query(null, -1, -1);\n");
		hsb.append("\t}\n\n");

		hsb.append("\tpublic " + tableCode + "Set query(QueryBuilder qb) {\n");
		hsb.append("\t\treturn query(qb, -1, -1);\n");
		hsb.append("\t}\n\n");

		hsb.append("\tpublic " + tableCode + "Set query(int pageSize, int pageIndex) {\n");
		hsb.append("\t\treturn query(null, pageSize, pageIndex);\n");
		hsb.append("\t}\n\n");

		hsb.append("\tpublic " + tableCode + "Set query(QueryBuilder qb , int pageSize, int pageIndex){\n");
		hsb.append("\t\treturn (" + tableCode + "Set)querySet(qb , pageSize , pageIndex);\n");
		hsb.append("\t}\n\n");

		svsb.append("\t}\n\n");
		gvsb.append("\t\treturn null;\n");
		gvsb.append("\t}\n\n");
		sb.append(hsb);
		sb.append(svsb);
		sb.append(gvsb);
		sb.append(csb);
		sb.append("}");
		FileUtil.writeText(outputDir + "/" + tableCode + "Schema.java", sb.toString());
		generateSet(tableCode);
	}

	private void generateSet(String tableCode) {
		StringBuffer sb = new StringBuffer(1000);
		sb.append("package " + namespace + ";\n\n");
		sb.append("import " + namespace + "." + tableCode + "Schema;\n");
		sb.append("import com.sinosoft.framework.orm.SchemaSet;\n\n");
		sb.append("public class " + tableCode + "Set extends SchemaSet {\n");
		sb.append("\tpublic " + tableCode + "Set() {\n");
		sb.append("\t\tthis(10,0);\n");
		sb.append("\t}\n\n");

		sb.append("\tpublic " + tableCode + "Set(int initialCapacity) {\n");
		sb.append("\t\tthis(initialCapacity,0);\n");
		sb.append("\t}\n\n");

		sb.append("\tpublic " + tableCode + "Set(int initialCapacity,int capacityIncrement) {\n");
		sb.append("\t\tsuper(initialCapacity,capacityIncrement);\n");
		sb.append("\t\tTableCode = " + tableCode + "Schema._TableCode;\n");
		sb.append("\t\tColumns = " + tableCode + "Schema._Columns;\n");
		sb.append("\t\tNameSpace = " + tableCode + "Schema._NameSpace;\n");
		sb.append("\t\tInsertAllSQL = " + tableCode + "Schema._InsertAllSQL;\n");
		sb.append("\t\tUpdateAllSQL = " + tableCode + "Schema._UpdateAllSQL;\n");
		sb.append("\t\tFillAllSQL = " + tableCode + "Schema._FillAllSQL;\n");
		sb.append("\t\tDeleteSQL = " + tableCode + "Schema._DeleteSQL;\n");
		sb.append("\t}\n\n");

		// newInstance函数
		sb.append("\tprotected SchemaSet newInstance(){\n");
		sb.append("\t\treturn new " + tableCode + "Set();\n");
		sb.append("\t}\n\n");

		sb.append("\tpublic boolean add(" + tableCode + "Schema aSchema) {\n");
		sb.append("\t\treturn super.add(aSchema);\n");
		sb.append("\t}\n\n");
		sb.append("\tpublic boolean add(" + tableCode + "Set aSet) {\n");
		sb.append("\t\treturn super.add(aSet);\n");
		sb.append("\t}\n\n");
		sb.append("\tpublic boolean remove(" + tableCode + "Schema aSchema) {\n");
		sb.append("\t\treturn super.remove(aSchema);\n");
		sb.append("\t}\n\n");
		sb.append("\tpublic " + tableCode + "Schema get(int index) {\n");
		sb.append("\t\t" + tableCode + "Schema tSchema = (" + tableCode + "Schema) super.getObject(index);\n");
		sb.append("\t\treturn tSchema;\n");
		sb.append("\t}\n\n");
		sb.append("\tpublic boolean set(int index, " + tableCode + "Schema aSchema) {\n");
		sb.append("\t\treturn super.set(index, aSchema);\n");
		sb.append("\t}\n\n");
		sb.append("\tpublic boolean set(" + tableCode + "Set aSet) {\n");
		sb.append("\t\treturn super.set(aSet);\n");
		sb.append("\t}\n");
		sb.append("}\n ");
		FileUtil.writeText(outputDir + "/" + tableCode + "Set.java", sb.toString());
	}

	private void splitComment(StringBuffer sb, String comment, String tab) {
		String[] a = comment.split("\n");
		for (int i = 0; i < a.length; i++) {
			if (a[i].trim().equals("")) {
				continue;
			}
			sb.append(tab);
			sb.append(a[i].trim());
			sb.append("<br>\n");
		}
	}

	private boolean checkCode(String code, String msgPrefix) {
		char[] ca = code.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (i == 0) {
				if (!Character.isJavaIdentifierStart(ca[i])) {
					logger.error("{}{}不是合适的Java标志名", msgPrefix, code);
					return false;
				}
			} else {
				if (!Character.isJavaIdentifierPart(ca[i])) {
					logger.error("{}{}不是合适的Java标志名", msgPrefix, code);
					return false;
				}
			}
		}
		return true;
	}

	static void dealFile(String fileName, String namespace, String outputDir) {
		BackupTableGenerator btg = new BackupTableGenerator();
		btg.setFileName(fileName);
		try {
			btg.toBackupTable();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		SchemaGenerator og = new SchemaGenerator();
		og.setFileName(fileName);
		og.setOutputDir(outputDir);
		og.setNamespace(namespace);
		try {
			og.generate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		og.setFileName(fileName.substring(0, fileName.length() - 4) + "_B.pdm");
		og.setOutputDir(outputDir);
		og.setNamespace(namespace);
		try {
			og.generate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void execute(String packageStr) {
		// 生成备份表
		String str = Config.getValue("App.PDM");
		String[] files = str.split("\\,");

		String prefix = Config.getContextRealPath();
		prefix = prefix.substring(0, prefix.length() - 2);
		prefix = prefix.substring(0, prefix.lastIndexOf("/") + 1);
		String javapath = prefix + "Java/" + packageStr.replaceAll("\\.", "/");
		FileUtil.mkdir(javapath);
		FileUtil.deleteEx(javapath + "/.+java");

		for (int i = 0; i < files.length; i++) {
			String fileName = "DB/" + files[i] + ".pdm";
			dealFile(fileName, packageStr, javapath);
		}
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	class SchemaColumn {
		public String ID;

		public String Name;

		public String Code;

		public String Comment;

		public String DataType;

		public int Length;

		public int Precision;

		public boolean Mandatory;

		public boolean isPrimaryKey;
	}
}
