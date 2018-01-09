package com.sinosoft.ibrms;

import com.sinosoft.framework.data.DBConnPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QueryForDTData {
	private static final Logger logger = LoggerFactory.getLogger(QueryForDTData.class);
	private SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static ArrayList columnType=new ArrayList();
	
	public QueryForDTData()
	{
		
	}
	
	 public ArrayList queryDataBase(String sql)
	{
		 ArrayList rArrayList=new ArrayList();
		 Connection conn=DBConnPool.getConnection();
		 
		 Statement stmt=null;
		 ResultSet rs=null;
		 
			try {
				stmt=conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				stmt=null;
			}
		
		
		try {
			rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			rs=null;
		}
		
		rArrayList=translateReusltSet(rs);
		
		try {
			rs.close();
			logger.info("ResultSet has been closed!");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			logger.error(e2.getMessage(), e2);
		}
		
		try {
			stmt.close();
			logger.info("Statement has been closed!");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage(), e1);
		}
		
		try {
			conn.close();
			logger.info("Connection has been closed!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		return rArrayList;
	}
	 
	private ArrayList translateReusltSet(ResultSet rs)
	{
		ArrayList rsArrayList=new ArrayList();
		
		ResultSetMetaData rsmd=null;
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		int columnCount=0;
		try {
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		int i=0;
		
		try {
			while(rs.next())
			{
				Map rowData=new HashMap();
				
				String columnValue="";
				
				for(i=0;i<columnCount;i++)
				{
					columnType.add(i,rsmd.getColumnName(i+1));
					columnValue=getDataValue(rsmd,rs,i+1);
					rowData.put(rsmd.getColumnName(i+1), columnValue);
				}
				rsArrayList.add(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		return rsArrayList;
	}
	
	public String getDataValue(ResultSetMetaData rsmd, ResultSet rs, int i) {
		String strValue = "";

		try {
			int dataType = rsmd.getColumnType(i);
			int dataScale = rsmd.getScale(i);
			int dataPrecision = rsmd.getPrecision(i);
			// ��������Ϊ�ַ�
			if ((dataType == Types.CHAR) || (dataType == Types.VARCHAR)) {
				// ���ڴ������ݿ��������GBKģʽ�����û�б�Ҫ��һ��unicodeToGBK
				// strValue = StrTool.unicodeToGBK(rs.getString(i));
				strValue = rs.getString(i);
			}
			// ��������Ϊ���ڡ�ʱ��
			else if ((dataType == Types.TIMESTAMP) || (dataType == Types.DATE)) {
				//rs.getDate(columnName)
				Date date=rs.getTimestamp(i);
				if(date==null)
					strValue="";
				else 
				    strValue = dateFormat.format(date);
			}
			// ��������Ϊ����
			else if ((dataType == Types.DECIMAL) || (dataType == Types.FLOAT)) {
				// strValue = String.valueOf(rs.getFloat(i));
				// ��������ķ���ʹ�����������ʱ�򲻻������ѧ��������ʽ
				strValue = String.valueOf(rs.getBigDecimal(i));
				// ȥ�㴦��
				strValue = IBRMSPubFun.getInt(strValue);
			}
			// ��������Ϊ����
			else if ((dataType == Types.INTEGER)
					|| (dataType == Types.SMALLINT)) {
				strValue = String.valueOf(rs.getInt(i));
				strValue = IBRMSPubFun.getInt(strValue);
			}
			// ��������Ϊ����
			else if (dataType == Types.NUMERIC) {
				if (dataScale == 0) {
					if (dataPrecision == 0) {
						// strValue = String.valueOf(rs.getDouble(i));
						// ��������ķ���ʹ�����������ʱ�򲻻������ѧ��������ʽ
						strValue = String.valueOf(rs.getBigDecimal(i));
					} else {
						strValue = String.valueOf(rs.getLong(i));
					}
				} else {
					// strValue = String.valueOf(rs.getDouble(i));
					// ��������ķ���ʹ�����������ʱ�򲻻������ѧ��������ʽ
					strValue = String.valueOf(rs.getBigDecimal(i));
				}
				strValue = IBRMSPubFun.getInt(strValue);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return strValue.trim();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
        QueryForDTData queryDTData=new QueryForDTData();
        
        String sql="select * from DTT0113";
        
        ArrayList al=queryDTData.queryDataBase(sql);
        
        Map map=null;
        String rowData="";
        for(int i=0;i<al.size();i++)
        {
        	map=(Map)al.get(i);
        	rowData="";
        	for(int j=0;j<map.size();j++)
        	{
        		String key=(String)columnType.get(j);
        		rowData+=" "+map.get(key);
        	
        	}
        	System.out.println("ROW"+i+"::"+rowData);
        }*/
	}

}
