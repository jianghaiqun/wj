package com.sinosoft.framework.orm;

import com.sinosoft.framework.data.DBConnConfig;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.utility.CaseIgnoreMapx;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TableUpdater {
	private static final Logger logger = LoggerFactory.getLogger(TableUpdater.class);

	/**
	 * 将更新SQL语句转换成Oracle兼容的语法
	 */
	public static String toOracleSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnConfig.ORACLE);
		StringBuffer sb = new StringBuffer();
		sb.append("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS';\n");
		sb.append("create or replace procedure proc_dropifexists(\n");
		sb.append("	    p_table in varchar2\n");
		sb.append("	) is\n");
		sb.append("	    v_count number(10);\n");
		sb.append("	begin\n");
		sb.append("	   select count(*)\n");
		sb.append("	   into v_count\n");
		sb.append("	   from user_objects\n");
		sb.append("	   where object_name = upper(p_table);\n");
		sb.append("	   if v_count > 0 then\n");
		sb.append("	      execute immediate 'drop table ' || p_table ||' purge';\n");
		sb.append("	   end if;\n");

		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if (StringUtil.isEmpty(line) || line.startsWith("/*")) {
				sb.append(line + "\n");
				continue;
			}
			sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	/**
	 *将更新SQL语句转换成SQLServer 2005兼容的语法
	 */
	public static String toSQLServerSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnConfig.MSSQL);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if (StringUtil.isEmpty(line) || line.startsWith("/*")) {
				sb.append(line + "\n");
				continue;
			}
			sb.append(arr[i] + "\ngo\n");
		}
		return sb.toString();
	}

	/**
	 * 将更新SQL语句转换成Mysql兼容的语法
	 */
	public static String toMysqlrSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnConfig.MYSQL);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if (StringUtil.isEmpty(line) || line.startsWith("/*")) {
				sb.append(line + "\n");
				continue;
			}
			sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	/**
	 * 将更新SQL语句转换成DB2兼容的语法
	 */
	public static String toDB2SQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnConfig.DB2);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if (StringUtil.isEmpty(line) || line.startsWith("/*")) {
				sb.append(line + "\n");
				continue;
			}
			sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	public static class DropTableInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;

		public String[] toSQLArray(String dbType) {
			return new String[] { TableCreator.dropTable(TableName, dbType) };
		}
	}

	public static class CreateTableInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public ArrayList Columns = new ArrayList();

		public String[] toSQLArray(String dbType) {
			try {
				SchemaColumn[] scs = new SchemaColumn[Columns.size()];
				for (int i = 0; i < scs.length; i++) {
					scs[i] = (SchemaColumn) Columns.get(i);
				}
				return new String[] { TableCreator.createTable(scs, TableName, dbType) };
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return null;
		}
	}

	public static class AlterKeyInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public String NewKeys;
		public boolean DropFlag;

		public String[] toSQLArray(String dbType) {

			if (DropFlag) {// Mysql 和 Oracle测试过了，通用
				String sql = "alter table " + TableName + " drop primary key";
				if (DBConnConfig.MSSQL.equals(dbType)) {
					sql = "alter table " + TableName + " drop constraint PK_" + TableName;
				}
				return new String[] { sql };
			} else {
				String sql = "alter table " + TableName + " add primary key (" + NewKeys + ")";
				if (DBConnConfig.MSSQL.equals(dbType)) {
					sql = "alter table " + TableName + " add constraint PK_" + TableName
							+ " primary key  NONCLUSTERED(" + NewKeys + ")";
				}
				return new String[] { sql };
			}
		}
	}

	public static class AlterTableInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public String Action;// add ,drop ,moidfy
		public int ColumnType;
		public String OldColumnName;
		public String NewColumnName;
		public String AfterColumn;
		public int Length;
		public int Precision;
		public boolean Mandatory;

		public String[] toSQLArray(String dbType) {
			return toSQLArray(dbType, new ArrayList(), new CaseIgnoreMapx());
		}

		public String[] toSQLArray(String dbType, ArrayList togetherColumns, Mapx exclusiveMapx) {
			exclusiveMapx.put(OldColumnName, "1");// 避免oracle下出现两次
			if (!togetherColumns.contains(this)) {
				togetherColumns.add(0, this);// 多个字段一起增加
			}
			if (dbType.equals(DBConnConfig.MYSQL)) {
				if (Action.equalsIgnoreCase("add")) {
					String[] arr = new String[togetherColumns.size()];
					for (int i = 0; i < togetherColumns.size(); i++) {
						AlterTableInfo info = (AlterTableInfo) togetherColumns.get(i);
						String fieldExt = TableCreator.getFieldExtDesc(info.Length, info.Precision).trim();
						String sql = "alter table " + info.TableName + " add column " + info.OldColumnName + " "
								+ TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt;
						if (info.Mandatory) {
							sql += " not null";
						}
						if (StringUtil.isNotEmpty(info.AfterColumn)) {
							sql += " after " + info.AfterColumn;
						}
						arr[i] = sql;
					}
					return arr;
				}
				if (Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + TableName + " drop column " + OldColumnName;
					return new String[] { sql };
				}
				if (Action.equalsIgnoreCase("modify") || Action.equalsIgnoreCase("change")) {
					String sql = "alter table " + TableName + " change column " + OldColumnName;
					if (StringUtil.isNotEmpty(NewColumnName)) {
						sql += " " + NewColumnName;
					}
					sql += " " + TableCreator.toSQLType(ColumnType, dbType);
					String fieldExt = TableCreator.getFieldExtDesc(Length, Precision).trim();
					sql += fieldExt;
					if (Mandatory) {
						sql += " not null";
					}
					return new String[] { sql };
				}
			}
			if (dbType.equals(DBConnConfig.ORACLE)) {
				if (Action.equalsIgnoreCase("add")) {
					StringBuffer sb = new StringBuffer();
					// if (TableName.equalsIgnoreCase("zcsite")) {
					// System.out.println(1);
					// }
					if (StringUtil.isNotEmpty(AfterColumn)) {
						Schema schema = SchemaUtil.findSchema(TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns.clone();
						ArrayList list = new ArrayList();
						for (int i = 0; i < scs.length; i++) {
							if (exclusiveMapx == null || !exclusiveMapx.containsKey(scs[i].getColumnName())) {
								list.add(scs[i].getColumnName());
							}
							for (int j = togetherColumns.size() - 1; j >= 0; j--) {// after相同的，后加的要放在前面
								AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
								String columnName = info.AfterColumn;
								if (scs[i].getColumnName().equalsIgnoreCase(columnName)) {
									list.add("'0' as " + info.OldColumnName);
								}
							}
						}
						// sb.append(TableCreator.dropTable(TableName + "_TMP",
						// dbType) + "\n");
						sb.append("create table " + TableName + "_TMP as select " + StringUtil.join(list) + " from "
								+ TableName + "\n");
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
							String fieldExt = TableCreator.getFieldExtDesc(info.Length, info.Precision).trim();
							if (Mandatory) {// 非空就得先置值
								if (info.ColumnType == DataColumn.STRING) {
									sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName + "='0'\n");
								} else if (info.ColumnType == DataColumn.DATETIME) {// 日期需要先置null，修改类型，再置notnull
									sb
											.append("update " + info.TableName + "_TMP set " + info.OldColumnName
													+ "=null\n");
									sb.append("alter table " + info.TableName + "_TMP modify " + info.OldColumnName
											+ " " + TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt + "\n");
									sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName
											+ "='1970-01-01 00:00:00'\n");
								} else {// 数字类型需要先置null，修改类型，再置notnull
									sb
											.append("update " + info.TableName + "_TMP set " + info.OldColumnName
													+ "=null\n");
									sb.append("alter table " + info.TableName + "_TMP modify " + info.OldColumnName
											+ " " + TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt + "\n");
									sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName + "=0\n");
								}
							} else {
								sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName + "=null\n");
							}
							sb.append("alter table " + info.TableName + "_TMP modify " + info.OldColumnName + " "
									+ TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt
									+ (info.Mandatory ? " not null" : "") + "\n");
						}
						sb.append("drop table " + TableName + "\n");
						sb.append("rename " + TableName + "_TMP to " + TableName + "\n");
						addPrimaryKey(sb, TableName, dbType);// 重新加主键
						addIndexes(sb, TableName, dbType);// 重新加索引
					} else {
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
							String fieldExt = TableCreator.getFieldExtDesc(info.Length, info.Precision).trim();
							sb.append("alter table " + info.TableName + " add " + info.OldColumnName + " "
									+ TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt);
							if (info.Mandatory) {
								sb.append(" not null");
							}
							sb.append("\\n");
						}
					}
					return sb.toString().split("\\n");
				}
				if (Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + TableName + " drop column " + OldColumnName;
					return new String[] { sql };
				}
				if (Action.equalsIgnoreCase("modify") || Action.equalsIgnoreCase("change")) {
					if (StringUtil.isNotEmpty(NewColumnName) && !NewColumnName.equalsIgnoreCase(OldColumnName)) {
						Schema schema = SchemaUtil.findSchema(TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns.clone();
						ArrayList list = new ArrayList();
						for (int i = 0; i < scs.length; i++) {
							if (exclusiveMapx == null || !exclusiveMapx.containsKey(scs[i].getColumnName())) {
								if (scs[i].getColumnName().equalsIgnoreCase(NewColumnName)) {
									list.add(OldColumnName + " as " + NewColumnName);
								} else {
									list.add(scs[i].getColumnName());
								}
							}
						}
						StringBuffer sb = new StringBuffer();
						sb.append("create table " + TableName + "_TMP as select " + StringUtil.join(list) + " from "
								+ TableName + "\n");
						sb.append("drop table " + TableName + "\n");
						sb.append("rename " + TableName + "_TMP to " + TableName + "\n");
						addPrimaryKey(sb, TableName, dbType);// 重新加主键
						addIndexes(sb, TableName, dbType);// 重新加索引
						return sb.toString().split("\\n");
					}
					String sql = "alter table " + TableName + " modify " + OldColumnName;
					sql += " " + TableCreator.toSQLType(ColumnType, dbType);
					String fieldExt = TableCreator.getFieldExtDesc(Length, Precision).trim();
					sql += fieldExt;
					if (Mandatory) {
						sql += " not null";
					}
					return new String[] { sql };
				}
			}
			if (dbType.equals(DBConnConfig.MSSQL)) {
				if (Action.equalsIgnoreCase("add")) {
					ArrayList sqlList = new ArrayList();
					if (StringUtil.isNotEmpty(AfterColumn)) {
						Schema schema = SchemaUtil.findSchema(TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns.clone();
						ArrayList listInsert = new ArrayList();
						ArrayList listSelect = new ArrayList();
						ArrayList pkList = new ArrayList();
						for (int i = scs.length - 1; i >= 0; i--) {
							if (exclusiveMapx == null || !exclusiveMapx.containsKey(scs[i].getColumnName())) {
								listInsert.add(scs[i].getColumnName());
								listSelect.add(scs[i].getColumnName());
							}
							if (scs[i].isPrimaryKey()) {
								pkList.add(scs[i].getColumnName());
							}
							for (int j = 0; j < togetherColumns.size(); j++) {
								AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
								String columnName = info.AfterColumn;
								if (scs[i].getColumnName().equalsIgnoreCase(columnName)) {
									SchemaColumn sc = new SchemaColumn(info.OldColumnName, info.ColumnType, i,
											info.Length, info.Precision, info.Mandatory, false);
									scs = (SchemaColumn[]) ArrayUtils.add(scs, i + 1, sc);
									// 如果是必填 的，则需要先置为0
									if (info.Mandatory) {
										listInsert.add(info.OldColumnName);
										listSelect.add("''0'' as " + info.OldColumnName);
									}
								}
							}
						}
						// 去掉重复的列
						for (int i = scs.length - 1; i > 0; i--) {
							boolean duplicateFlag = false;
							for (int j = i - 1; j >= 0; j--) {
								if (scs[i].getColumnName().equalsIgnoreCase(scs[j].getColumnName())) {
									scs = (SchemaColumn[]) ArrayUtils.remove(scs, i);
									duplicateFlag = true;
									break;
								}
							}
							if (duplicateFlag) {
								continue;
							}
							if (exclusiveMapx != null && exclusiveMapx.containsKey(scs[i].getColumnName())) {
								boolean removeFlag = true;
								for (int j = 0; j < togetherColumns.size(); j++) {
									AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
									String columnName = info.OldColumnName;
									if (scs[i].getColumnName().equalsIgnoreCase(columnName)) {
										removeFlag = false;
									}
								}
								if (removeFlag) {
									scs = (SchemaColumn[]) ArrayUtils.remove(scs, i);
								}
							}
						}
						try {
							sqlList.add(TableCreator.dropTable(TableName + "_TMP", dbType));
							String sql = TableCreator.createTable(scs, TableName + "_TMP", dbType);
							// 去掉主键
							int index = sql.indexOf(",\n\tconstraint");
							sql = sql.substring(0, index) + ")";
							sqlList.add(sql);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
						// 转移数据
						sqlList.add("if exists(select * from " + TableName + ") exec ('insert into " + TableName
								+ "_TMP (" + StringUtil.join(listInsert) + ") select " + StringUtil.join(listSelect)
								+ " from " + TableName + "')");
						sqlList.add("drop table " + TableName);// 删除旧表
						sqlList.add("sp_rename '" + TableName + "_TMP', '" + TableName + "', 'OBJECT'");// 重命名临时表为旧表名
						StringBuffer sb = new StringBuffer();
						addPrimaryKey(sb, TableName, dbType);// 重新加主键
						addIndexes(sb, TableName, dbType);// 重新加索引
						String[] arr = sb.toString().split("\\n");
						for (int i = 0; i < arr.length; i++) {
							sqlList.add(arr[i]);
						}
					} else {
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
							String fieldExt = TableCreator.getFieldExtDesc(info.Length, info.Precision).trim();
							String sql = "alter table " + info.TableName + " add " + info.OldColumnName + " "
									+ TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt;
							if (info.Mandatory) {
								sql += " not null";
							}
							sqlList.add(sql);
						}
					}
					String[] arr = new String[sqlList.size()];
					for (int i = 0; i < arr.length; i++) {
						arr[i] = (String) sqlList.get(i);
					}
					return arr;
				}
				if (Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + TableName + " drop column " + OldColumnName;
					return new String[] { sql };
				}
				if (Action.equalsIgnoreCase("modify") || Action.equalsIgnoreCase("change")) {
					ArrayList sqlList = new ArrayList();
					SchemaColumn column = SchemaUtil.findColumn(TableName, OldColumnName);
					if (column == null) {
						column = SchemaUtil.findColumn(TableName, NewColumnName);// 字段名修改时需要这样
					}
					if (column.isPrimaryKey()) {// 先去掉主键
						sqlList.add("alter table " + TableName + " drop constraint PK_" + TableName);
					}
					String sql = "alter table " + TableName + " alter column " + OldColumnName;
					sql += " " + TableCreator.toSQLType(ColumnType, dbType);
					String fieldExt = TableCreator.getFieldExtDesc(Length, Precision).trim();
					sql += fieldExt;
					if (Mandatory) {
						sql += " not null";
					}
					sqlList.add(sql);
					if (StringUtil.isNotEmpty(NewColumnName) && !NewColumnName.equalsIgnoreCase(OldColumnName)) {
						sqlList.add(" sp_rename '" + TableName + "." + OldColumnName + "','" + NewColumnName
								+ "','column'");
					}
					if (column.isPrimaryKey()) {// 再加上主键
						SchemaColumn[] scs = SchemaUtil.findSchema(TableName).Columns;
						ArrayList pkList = SchemaUtil.getPrimaryKeyColumns(scs);
						sqlList.add("alter table " + TableName + " add constraint PK_" + TableName
								+ " primary key NONCLUSTERED(" + StringUtil.join(pkList) + ")");
					}
					String[] arr = new String[sqlList.size()];
					for (int i = 0; i < arr.length; i++) {
						arr[i] = (String) sqlList.get(i);
					}
					return arr;
				}
			}
			return null;
		}
	}

	public static class SQLInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String SQL;

		public String[] toSQLArray(String dbType) {
			if (SQL.trim().endsWith(";")) {
				SQL = SQL.substring(0, SQL.length() - 1).trim();
			}
			return new String[] { SQL };
		}
	}

	public static class CommentInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String Comment;

		public String[] toSQLArray(String dbType) {
			return new String[] { Comment };
		}
	}

	public static void addIndexes(StringBuffer sb, String tableName, String dbType) {
		try {
			Class c = Class.forName("com.sinosoft.platform.pub.Install");
			Method m = c.getMethod("getIndexSQLForTable", new Class[] { String.class, String.class });
			Object obj = m.invoke(null, new Object[] { tableName, dbType });
			ArrayList list = (ArrayList) obj;
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i) + "\n");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void addPrimaryKey(StringBuffer sb, String tableName, String dbType) {
		try {
			SchemaColumn[] scs = SchemaUtil.findSchema(tableName).Columns;
			ArrayList pkList = SchemaUtil.getPrimaryKeyColumns(scs);
			AlterKeyInfo info = new AlterKeyInfo();
			info.TableName = tableName;
			info.NewKeys = StringUtil.join(pkList);
			String[] arr = info.toSQLArray(dbType);
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i] + "\n");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
