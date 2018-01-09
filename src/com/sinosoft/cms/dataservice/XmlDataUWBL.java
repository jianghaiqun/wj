package com.sinosoft.cms.dataservice;


import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


	public class XmlDataUWBL {

		private static final Logger logger = LoggerFactory.getLogger(XmlDataUWBL.class);

		private HashMap mapElement = new HashMap(); //（ElementNo, Element）序号，节点对象
		private HashMap mapElementValue = new HashMap(); //（ElementNo, ElementValue）序号，节点值
		private HashMap mapAttributeValue = new HashMap(); //（AttributeNo, AttributeValue）属性号，属性值
  
        private String filename01 = "01.xml"; //核保的xml文件
        private String filename09 = "09.xml"; //承保的xml文件
        private String docEncoding;
		public XmlDataUWBL() {

		}

		/**
		 * 处理各种请求
		 * 
		 * @param 接口标记（BusinessLogic ： 01 核保   09 承保） 机构编码 （ManageCom ）订单号  ( OrderNo )
		 * //生成待发送报文
			String docstr = tXmlDataUWBL.dealData(BusinessLogic, ManageCom, OrderNo);
		 * @return
		 */
		public String dealData(String BusinessLogic,String ManageCom,String OrderNo, String[] outputEncoding) {
			String result = "";
			Document doc = null;
			if(OrderNo==null || OrderNo.equals("")){
				logger.error("定单号为空");
				return result;
			}
		    if(BusinessLogic!=null && BusinessLogic.equals("01")){ //核保
				HashMap mapVar = new HashMap();
				mapVar.put("ManageCom", ManageCom);
				mapVar.put("OrderNo", OrderNo);
				
				//接口调用：根据表配置生成xml(指定的一个xml)
				doc = createDocument(filename01,ManageCom,mapVar);
		    }else if(BusinessLogic!=null && BusinessLogic.equals("09")){ //承保
				HashMap mapVar = new HashMap();
				mapVar.put("ManageCom", ManageCom);
				mapVar.put("OrderNo", OrderNo);
				//接口调用：根据表配置生成xml(指定的一个xml)
				doc = createDocument(filename09,ManageCom,mapVar);
		    }
		    //将Document 转为字符串
		    if(doc!=null){
//		    	result = doc.asXML();
		    	result = formatXML(doc, docEncoding); //解决中文乱码问题
		    	if ((null != outputEncoding) && (outputEncoding.length > 0))
		    	{
		    		outputEncoding[0] = docEncoding;
		    	}
		    }
			return result;
		}

		//接口调用：根据表配置生成xml(所有的xml,不传入参数)
		public boolean createXml(){
			DataTable dt = null;
			//根节点
			dt = new QueryBuilder("select XMLName,XMLFilePath,ManageCom from TabVXml where levelid=0").executeDataTable();
			if(dt!=null && dt.getRowCount()>0){
				for (int i = 0; i < dt.getRowCount(); i++) {
					String XMLName = (String) dt.get(i, 0);
					String XMLFilePath = (String) dt.get(i, 1);
					String ManageCom = (String) dt.get(i, 2);
					logger.info("{}{}文件开始生成...", ManageCom, XMLName);
					if(!createXml(XMLName,ManageCom,null)){
						logger.error("{}{}文件生成失败!", ManageCom, XMLName);
						return false;
					}
					logger.info("{}{}文件生成完成...", ManageCom, XMLName);
				}
			}else{
				logger.error("未找到根节点");
				return false;
			}
			return true;
		}
		
		//接口调用：根据表配置生成xml(所有的xml)
		public boolean createXml(HashMap mapVar){
			DataTable dt = null;
			//根节点
			dt = new QueryBuilder("select XMLName,XMLFilePath,ManageCom from TabVXml where levelid=0").executeDataTable();
			if(dt!=null && dt.getRowCount()>0){
				for (int i = 0; i < dt.getRowCount(); i++) {
					String XMLName = (String) dt.get(i, 0);
					String XMLFilePath = (String) dt.get(i, 1);
					String ManageCom = (String) dt.get(i, 2);
					logger.info("{}{}文件开始生成...", ManageCom, XMLName);
					if(!createXml(XMLName,ManageCom,mapVar)){
						logger.error("{}{}文件生成失败!",ManageCom, XMLName);
						return false;
					}
					logger.info("{}{}文件生成完成...", ManageCom, XMLName);
				}
			}else{
				logger.info("未找到根节点");
				return false;
			}
			return true;
		}
		
		//接口调用：根据表配置生成xml(指定的一个xml)
		public boolean createXml(String xmlname,String ManageCom,HashMap mapVar) {
			logger.info("{}{}文件开始生成...", ManageCom, xmlname);
			mapElement = new HashMap();//（ElementNo, Element）序号，节点对象
			mapElementValue = new HashMap(); //（ElementNo, ElementValue）序号，节点值
			mapAttributeValue = new HashMap(); //（AttributeNo, AttributeValue）属性号，属性值
			DataTable dt = null;
			//根节点
			dt = new QueryBuilder("select XMLName,XMLFilePath,XMLEncoding,Element,Attribute,Function,ElementValue,AttributeValue,ElementSQL,ElementSQLVar,AttributeSQL,AttributeSQLVar,ElementNo,AttributeNo  from TabVXml where xmlname=? and ManageCom=? and levelid=0",xmlname,ManageCom).executeDataTable();
			if(dt!=null && dt.getRowCount()>0){
				DataRow dr = dt.getDataRow(0);
				String XMLName = (String) dr.get(0);
				String XMLFilePath = (String) dr.get(1);
				String XMLEncoding = (String) dr.get(2);
				
		        Document document = createDocument(dr,mapVar,xmlname,ManageCom);
		        if(document==null){
		        	return false;
		        }
		        OutputFormat format = OutputFormat.createPrettyPrint();
		        format.setEncoding(XMLEncoding); 
				try {
					String fileName = XMLFilePath+XMLName;
					Writer fileWriter = new FileWriter(fileName);
					XMLWriter xmlWriter = new XMLWriter(fileWriter,format);
					xmlWriter.write(document);
					xmlWriter.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					return false;
				}
				
			}else{
				logger.error("未找到根节点");
				return false;
			}
			logger.info("{}{}文件生成完毕...", ManageCom, xmlname);
			return true;
		}
		
		//接口调用：根据表配置生成Document(指定的一个xml)
		public Document createDocument(String xmlname,String ManageCom,HashMap mapVar) {
			logger.info("{}{}Document文件开始生成...", ManageCom, xmlname);
			mapElement = new HashMap(); //（ElementNo, Element）序号，节点对象
			mapElementValue = new HashMap(); //（ElementNo, ElementValue）序号，节点值
			mapAttributeValue = new HashMap(); //（AttributeNo, AttributeValue）属性号，属性值
			DataTable dt = null;
			Document document = null;
			//根节点
			dt = new QueryBuilder("select XMLName,XMLFilePath,XMLEncoding,Element,Attribute,Function,ElementValue,AttributeValue,ElementSQL,ElementSQLVar,AttributeSQL,AttributeSQLVar,ElementNo,AttributeNo from TabVXml where xmlname=? and ManageCom=? and levelid=0",xmlname,ManageCom).executeDataTable();
			if(dt!=null && dt.getRowCount()>0){
				DataRow dr = dt.getDataRow(0);
				String XMLName = (String) dr.get(0);
				String XMLFilePath = (String) dr.get(1);
				String XMLEncoding = (String) dr.get(2);
				
		        document = createDocument(dr,mapVar,xmlname,ManageCom);
		        if(document==null){
		        	return null;
		        }
			}else{
				logger.error("未找到根节点");
				return null;
			}
			logger.info("{}{}文件生成完毕...", ManageCom, xmlname);
			return document;
		}
		//生成Document文件
		public Document createDocument(DataRow dr,HashMap mapVar,String xmlname,String ManageCom){
			String XMLName = (String) dr.get(0);
			String XMLFilePath = (String) dr.get(1);
			String XMLEncoding = (String) dr.get(2);
			
			String rootElement = (String) dr.get(3);
			String rootAttribute = (String) dr.get(4);
			String rootFunction = (String) dr.get(5);
			String rootElementValue = (String) dr.get(6);
			String rootAttributeValue = (String) dr.get(7);
			String rootElementSQL = (String) dr.get(8);
			String rootElementSQLVar = (String) dr.get(9);
			String rootAttributeSQL = (String) dr.get(10);
			String rootAttributeSQLVar = (String) dr.get(11);
			String rootElementNo =  String.valueOf((Integer)dr.get(12));
			
//			OutputFormat format = OutputFormat.createPrettyPrint();
//	        format.setEncoding(XMLEncoding); 
			Document document = DocumentHelper.createDocument();
			docEncoding = XMLEncoding;
			document.setXMLEncoding(XMLEncoding);
			//添加根节点
			Element rElement = document.addElement(rootElement);
			mapElement.put(rootElementNo, rElement);
			//添加节点值
			if(rootElementValue!=null && !rootElementValue.equals("")){ //默认值不为空
				rElement.setText(rootElementValue);
			}else if(rootElementSQL!=null && !rootElementSQL.equals("") && rootElementSQLVar!=null && !rootElementSQLVar.equals("") ){ //条件查询不为空
				String sqlVar = fncStringToArr(rootElementSQLVar,mapVar);
				DataTable dt = QueryBuilderNew(rootElementSQL,sqlVar);
				if(dt!=null && dt.getRowCount()>0){
					rElement.setText(dt.getString(0, 0));
				}
			}
			//添加属性及值
			if(rootAttribute!=null && !rootAttribute.equals("") ){
				if(rootAttributeValue!=null && !rootAttributeValue.equals("")){ //默认值不为空
					rElement.addAttribute(rootAttribute, rootAttributeValue); //添加属性
				}else if(rootAttributeSQL!=null && !rootAttributeSQL.equals("") && rootAttributeSQLVar!=null && !rootAttributeSQLVar.equals("")){ //条件查询不为空
					String sqlVar = fncStringToArr(rootAttributeSQLVar,mapVar);
					DataTable dt = QueryBuilderNew(rootAttributeSQL,sqlVar);
					if(dt!=null && dt.getRowCount()>0){
						rElement.addAttribute(rootAttribute, dt.getString(0, 0)); //添加属性
					}
				}
			}
			//节点的特殊处理方法
			if(rootFunction!=null && rootFunction.equals("Y")){  
				ElementFunction(rElement,rootElement);
			}
			
			//非根节点的处理
			DataTable dt = new QueryBuilder("select Element,ParentElementNo,Attribute,Function,ElementValue,AttributeValue,ElementSQL,ElementSQLVar,AttributeSQL,AttributeSQLVar,ElementNo,AttributeNo from TabVXml where xmlname=? and ManageCom=? and levelid<>0 order by orderno,ElementNo",xmlname,ManageCom).executeDataTable();
			if(dt!=null && dt.getRowCount()>0){
				for (int i = 0; i < dt.getRowCount(); i++) {
					String tElement = (String) dt.get(i, 0);
					String tParentElementNo = String.valueOf((Integer) dt.get(i, 1));
					String tAttribute = (String) dt.get(i, 2);
					String tFunction = (String) dt.get(i, 3);
					String tElementValue = (String) dt.get(i, 4);
					String tAttributeValue = (String) dt.get(i, 5);
					
					String tElementSQL = (String) dt.get(i, 6);
					String tElementSQLVar = (String) dt.get(i, 7);
					String tAttributeSQL = (String) dt.get(i, 8);
					String tAttributeSQLVar = (String) dt.get(i, 9);
					String tElementNo =  String.valueOf((Integer) dt.get(i, 10));
					String tAttributeNo = String.valueOf((Integer) dt.get(i, 11));
					
					
					if(!addElement(tParentElementNo,tElement,tAttribute,tFunction,tElementValue,tAttributeValue,tElementSQL,tElementSQLVar,tAttributeSQL,tAttributeSQLVar,tElementNo,tAttributeNo,mapVar)){
						logger.error("添加节点失败");
						return null;
					}
				}
			}else{
				logger.error("未找到根节点下子节点信息");
				return null;
			}
			return document;
		}
		 /** 
         * 格式化XML文档 
         * 
         * @param document xml文档 
         * @param charset    字符串的编码 ，和document的setXMLEncoding必须一致
         * @return 格式化后XML字符串 
         */ 
	public static String formatXML(Document document, String charset) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		try {
			xw.write(document);
			xw.flush();
			xw.close();
		} catch (IOException e) {
			logger.error("格式化XML文档发生异常，请检查！{}" + e.getMessage(), e);
		}
		return sw.toString();
	}

		
		//查找父节点tLastElementName，在父节点下添加子节点tElementName
		private boolean addElement(String tParentElementNo,String tElementName,String tAttribute,String tFunction,String tElementValue,String tAttributeValue,String tElementSQL,String tElementSQLVar,String tAttributeSQL,String tAttributeSQLVar,String tElementNo,String tAttributeNo,HashMap mapVar){
			boolean returnFlag = false;
			DataTable dt = null;
			Set entrySet = mapElement.entrySet();
	        Iterator itr = entrySet.iterator();
	        
			Set valueSet = null; //对应mapElementValue
	        Iterator valueIter = null; //对应mapElementValue
	        
	        Set attributeValueSet = null; //对应mapAttributeValue
	        Iterator attributeValueIter = null; //对应mapAttributeValue
	        
	        while (itr.hasNext()) {
	            Map.Entry entry = (Map.Entry) itr.next();
	            Object key = entry.getKey();
	            Element value = (Element) entry.getValue();
	            if(key.toString().equals(tParentElementNo)){
	            	Element tElement = value.addElement(tElementName);//添加子节点
	            	mapElement.put(tElementNo, tElement);

	    			//添加节点值
	    			if(tElementValue!=null && !tElementValue.equals("")){ //默认值不为空
	    				tElement.setText(tElementValue);
	    			}else if(tElementSQL!=null && !tElementSQL.equals("") && tElementSQLVar!=null && !tElementSQLVar.equals("") ){ //条件查询不为空
	    				String sqlVar = fncStringToArr(tElementSQLVar,mapVar);
	    				dt = QueryBuilderNew(tElementSQL,sqlVar);
	    				if(dt!=null && dt.getRowCount()>0 && dt.getColCount()<=1){ //sql中只定义一个节点的情况
	    					tElement.setText(dt.getString(0, 0));
	    				}else if(dt!=null && dt.getRowCount()>0 && dt.getColCount()>1){ //sql中只定义多个节点的情况
	    					DataColumn[] dateaColumns = dt.getDataColumns();
	    					for (int i = 0; i < dateaColumns.length; i++) {//循环每一列
	    						if (dateaColumns[i].getColumnName().equalsIgnoreCase(tElementNo)) {
	    							tElement.setText(dt.getString(0, i));
	    						}
	    						mapElementValue.put(dateaColumns[i].getColumnName(), dt.getString(0, i));
	    					}
	    				}
	    			}else if(mapElementValue!=null){ //从mapElementValue中取值
	    				valueSet = mapElementValue.entrySet();
	    				valueIter = valueSet.iterator();
	    		        while (valueIter.hasNext()) {
	    		            Map.Entry entry1 = (Map.Entry) valueIter.next();
	    		            Object key1 = entry1.getKey();
	    		            String value1 = (String) entry1.getValue();
	    		            if(key1.toString().equalsIgnoreCase(tElementNo)){
	    		            	tElement.setText(value1);
	    		            }
	    		        }
	    			}
	    			
	    			//添加属性及值
	    			if(tAttribute!=null && !tAttribute.equals("") ){
	    				if(tAttributeValue!=null && !tAttributeValue.equals("")){ //默认值不为空
	    					tElement.addAttribute(tAttribute, tAttributeValue); //添加属性
	    				}else if(tAttributeSQL!=null && !tAttributeSQL.equals("") && tAttributeSQLVar!=null && !tAttributeSQLVar.equals("")){ //条件查询不为空
	    					String sqlVar = fncStringToArr(tAttributeSQLVar,mapVar);
	    					dt = QueryBuilderNew(tAttributeSQL,sqlVar);
	    					if(dt!=null && dt.getRowCount()>0 && dt.getColCount()<=1){//sql中只定义一个节点属性的情况
	    						tElement.addAttribute(tAttribute, dt.getString(0, 0)); //添加属性
	    					}else if(dt!=null && dt.getRowCount()>0 && dt.getColCount()>1){ //sql中只定义多个节点属性的情况
	        					DataColumn[] dateaColumns = dt.getDataColumns();
	        					for (int i = 0; i < dateaColumns.length; i++) {//循环每一列
	        						if (dateaColumns[i].getColumnName().equalsIgnoreCase(tAttributeNo)) {
	        							tElement.addAttribute(tAttribute,dt.getString(0, i));
	        						}
	        						mapAttributeValue.put(dateaColumns[i].getColumnName(), dt.getString(0, i));
	        					}
	        				}
	    				}else if(mapAttributeValue!=null){ //从mapAttributeValue中取值
	    					attributeValueSet = mapAttributeValue.entrySet();
	    					attributeValueIter = valueSet.iterator();
	        		        while (attributeValueIter.hasNext()) {
	        		            Map.Entry entry1 = (Map.Entry) attributeValueIter.next();
	        		            Object key1 = entry1.getKey();
	        		            String value1 = (String) entry1.getValue();
	        		            if(key1.toString().equalsIgnoreCase(tAttributeNo)){
	        		            	tElement.addAttribute(tAttribute,value1);
	        		            }
	        		        }
	        			}
	    				
	    			}
	    			//节点的特殊处理方法
	    			if(tFunction!=null && tFunction.equals("Y")){
	    				ElementFunction(tElement,tElementName);
	    			}
	            	
	            	returnFlag = true;
	            	break;
	            }
	        }
			return returnFlag;
			
		}

		/**节点的特殊处理（如需特殊处理在此方法中增加）
		 * tElement 节点
		 * tElementName 节点名称
		 * 例如：
		 * 	if(tElementName!=null && tElementName.equals("SQTBRequestXML")){
				tElement.setText("1234567");
			}
		 */
		private void ElementFunction(Element tElement,String tElementName){
//			if(tElementName!=null && tElementName.equals("SQTBRequestXML")){
//				tElement.setText("1234567");
//			}else if(tElementName!=null && tElementName.equals("TRAN_CODE")){
//				tElement.setText("002");
//			}
		}
		//将传入的字符串tElementSQLVar中的多个变量从mapVar中查询得到对应的值，组成字符串返回
		public String fncStringToArr(String tElementSQLVar,HashMap mapVar){
			String returnValue ="";
			if(mapVar!=null){
				
				String swap = ",";     //出现的字母
				int n = (tElementSQLVar.length()-tElementSQLVar.replaceAll(swap , "").length())/swap.length()+1;
				String[] ss= new String[n];
		        ss=tElementSQLVar.split(swap);
		        for(int i=0;i<ss.length;i++){
		        	String v = "";
		        	Set entrySet = mapVar.entrySet();
			        Iterator itr = entrySet.iterator();
			        while (itr.hasNext()) {
			            Map.Entry entry = (Map.Entry) itr.next();
			            Object key = entry.getKey();
			            String value = (String) entry.getValue();
			            if(key.toString().equalsIgnoreCase(ss[i])){
			            	v = value;
			            	break;
			            }
			        }
			        if(i==(ss.length-1)){
			        	returnValue +=v;
			        }else{
			        	returnValue +=v+",";
			        }
		        	
		        }
			}
			logger.info("returnValue:{}", returnValue);
			return returnValue;
		}
		//QueryBuilder最多只能2个参数
		public DataTable QueryBuilderNew(String tSQLStr,String tSQLVar){
			DataTable dt = null;
			if(tSQLVar!=null && !tSQLVar.equals("")){
				String swap = ",";     //出现的字母
				int n = (tSQLVar.length()-tSQLVar.replaceAll(swap , "").length())/swap.length()+1;
				String[] ss= new String[n];
		        ss=tSQLVar.split(swap);
		        if(n==1){
//		        	System.out.println(tSQLStr+"===="+ss[0]);
		        	dt = new QueryBuilder(tSQLStr,ss[0]).executeDataTable();
		        }else if(n==2){
//		        	System.out.println(tSQLStr);
//		        	System.out.println("===="+ss[0]+":"+ss[1]);
		        	dt = new QueryBuilder(tSQLStr,ss[0],ss[1]).executeDataTable();
		        }
			}
			return dt;
		}


		//测试
		public static void main(String[] args) {
			XmlDataUWBL tXmlDataUWBL = new XmlDataUWBL();

			String BusinessLogic ="09";
			String ManageCom ="2011";
			String OrderNo ="2012000000001153";
			String str = tXmlDataUWBL.dealData(BusinessLogic, ManageCom, OrderNo, null);
//			System.out.println("=="+str);
//			HashMap mapVar = new HashMap();
//			mapVar.put("username", "wangjin001");
//			mapVar.put("realname", "james");
//			//接口调用：根据表配置生成xml(指定的一个xml)
//			tXmlDataUWBL.createXml("test1.xml","D:/",mapVar);
//			//接口调用：根据表配置生成xml(所有的xml)
//			tXmlDataUWBL.createXml(mapVar);
//			//接口调用：根据表配置生成xml(所有的xml,不传入参数)
//			tXmlDataUWBL.createXml();

			
		}
	}