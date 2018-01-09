package com.sinosoft.framework.orm;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mysql2Oracle {
	private static final Logger logger = LoggerFactory.getLogger(Mysql2Oracle.class);

	public static String generateOneTableSQL(String tableName) {
		StringBuffer sb = new StringBuffer();
		try {
			Class clz = Class.forName("com.sinosoft.schema." + tableName + "Schema");
			Schema sc = (Schema) clz.newInstance();

			SchemaColumn[] cols = SchemaUtil.getColumns(sc);

			ArrayList pkList = new ArrayList();

			sb.append("drop table " + tableName + " cascade constraints;\n");
			sb.append("create table " + tableName + " (\n");
			for (int i = 0; i < cols.length; i++) {
				SchemaColumn col = cols[i];
				String dataType = "";
				if (col.getColumnType() == DataColumn.INTEGER || col.getColumnType() == DataColumn.LONG) {
					dataType = "integer";
				} else if (col.getColumnType() == DataColumn.STRING) {
					if (col.getLength() < 8 && col.getLength()>0) {
						dataType = "varchar(" + col.getLength() + ")";
					} else if(col.getLength()>=8) {
						dataType = "varchar2(" + col.getLength() + ")";
					} else{
						dataType = "long";
					}

				}else if (col.getColumnType() == DataColumn.FLOAT){
					dataType ="float";
				}else if (col.getColumnType() == DataColumn.DATETIME) {
					dataType = "date";
				} else {
					dataType = "varchar2(" + col.getLength() + ")";
				}

				if (col.isPrimaryKey()) {
					pkList.add(col.getColumnName());
				}

				sb.append(col.getColumnName());
				sb.append(" " + dataType);
				sb.append(col.isMandatory() ? " not null" : " ");
				sb.append(",\n");
			}
			// String pkArray = pkList.toString().replaceAll("\\[", "").replaceAll("\\]", "");
			sb.append("constraint PK_" + tableName + " primary key (" + StringUtil.join(pkList.toArray(), ",") + ")\n");
			sb.append(");\n");

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {

			logger.error(e.getMessage(), e);
		}
		return sb.toString();
	}

	public static void dealFile(String fileName) {
		BackupTableGenerator btg = new BackupTableGenerator();
		btg.setFileName(fileName);
		try {
			btg.toBackupTable();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		try {
			generateSQL(fileName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		fileName = fileName.substring(0, fileName.length() - 4) + "_B.pdm";

		try {
			generateSQL(fileName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void generateSQL(String fileName) throws Exception {
		File f = new File(fileName);
		if (!f.exists()) {
			throw new RuntimeException("文件不存在");
		}
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(f);
		Element root = doc.getRootElement();
		Namespace nso = root.getNamespaceForPrefix("o");
		Namespace nsc = root.getNamespaceForPrefix("c");
		Namespace nsa = root.getNamespaceForPrefix("a");
		Element rootObject = root.element(new QName("RootObject", nso));
		Element children = rootObject.element(new QName("Children", nsc));
		Element model = children.element(new QName("Model", nso));

		List tables = model.element(new QName("Tables", nsc)).elements();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tables.size(); i++) {
			Element table = (Element) tables.get(i);
			String tableName = table.elementText(new QName("Code", nsa));
			sb.append(generateOneTableSQL(tableName));
		}
		fileName = fileName.substring(fileName.lastIndexOf('/')+1);
		FileUtil.writeText("DB/Oracle/"+fileName.substring(0, fileName.length() - 4)+".sql",sb.toString());
	}
	
	public static void importData(){
		
		//valueSQL.append("alter session set nls_date_format ='YYYY-MM-DD hh24:Mi:ss' \n");
		DataTable dt = new QueryBuilder("show tables").executeDataTable();
		//DBConn conn = DBConnPool.getConnection("ZCMSOracle");
		//DataAccess da = new DataAccess(conn);
		for (int i = 0; i < dt.getRowCount(); i++) {
			StringBuffer valueSQL = new StringBuffer();
			//Transaction trans = new Transaction();
			//trans.add(new QueryBuilder("alter session set nls_date_format ='YYYY-MM-DD hh24:Mi:ss' "));
			//trans.setDataAccess(da);
			String tableName = dt.getString(i, 0);
			
			valueSQL.append("truncate table "+tableName+";\n");
			
			if(!tableName.startsWith("b") && !"zcarticle".equals(tableName)){
				DataTable dtValue = new QueryBuilder("select * from "+dt.getString(i, 0)).executeDataTable();
				
				//trans.add(new QueryBuilder("truncate table "+tableName));
				for (int j = 0; j < dtValue.getRowCount(); j++) {
					StringBuffer sb = new StringBuffer();
					sb.append("insert into ");
					sb.append(tableName);
					sb.append(" values(");
					for (int k = 0; k < dtValue.getColCount(); k++) {
						if(k!=0){
							sb.append(",'"+dtValue.getString(j, k)+"'");
						}else{
							sb.append("'"+dtValue.getString(j, k)+"'");
						}
					}
					sb.append(")");
					//trans.add(new QueryBuilder(sb.toString()));
					valueSQL.append(sb+";\n");
					//System.out.println(sb.toString()+";");
				}
			}
			
			//trans.commit();
		}
		
		
		//FileUtil.writeText("DB/Oracle/Data.sql",valueSQL.toString() );
	}

	public static void main(String[] args) {
		
		String str = "zvingcms";
		String[] files = str.split("\\,");

		for (int i = 0; i < files.length; i++) {
			String fileName = "DB/" + files[i] + ".pdm";
			dealFile(fileName);
		}
		
		
		//importData();
		
	}
}
