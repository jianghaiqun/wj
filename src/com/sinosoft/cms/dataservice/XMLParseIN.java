package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XMLParseIN {

	private static final Logger logger = LoggerFactory.getLogger(XMLParseIN.class);

	private HashMap mapElementPath = new HashMap();
	private int ElementNo = 0; //节点序号
	private String XMLName="";
	 private String ManageCom="";   
	private String XMLEncoding="";
    private String XMLFilePath="";     
    private String Element="";           
    private String ParentElementNo;  
    private String Attribute="";        
    private int OrderNo;           
    private String Function="";         
    private int LevelId;           
    private String ElementValue="";      
    private String AttributeValue="";    
    private String ElementSQL="";       
    private String ElementSQLVar="";     
    private String AttributeSQL="";      
    private String AttributeSQLVar="";    
    private String ForFlag="";           
    private int AttributeNo; 
    private DataTable dt = null;
    private Transaction tran  = new Transaction();
    private QueryBuilder qb=null;
    
    public boolean xmlParseIN(String FilePathName,String FileName,String MngCom){
    	XMLName = FileName;
    	ManageCom = MngCom;
    	if(FilePathName==null || FilePathName.equals("") || FileName==null ||FileName.equals("") || MngCom==null ||MngCom.equals("")){
    		logger.warn("传入的参数不能为空!");
    		return false;
    	}
        SAXReader reader = new SAXReader();   
        Document doc;
        tran  = new Transaction();
		qb = new QueryBuilder("delete from tabvxml where XMLName=?  and ManageCom=?");
		qb.add(XMLName);
		qb.add(ManageCom);
		tran.add(qb);
		try {
			doc = reader.read(FilePathName);
			ElementNo = 0;
	        XMLEncoding = doc.getXMLEncoding();
	        XMLFilePath ="";
	        
	    	dt = new QueryBuilder("select Element,Attribute,ElementSQL,ElementSQLVar,AttributeSQL,AttributeSQLVar from AllXmlToTab where xmlname=? and managecom=?",XMLName,ManageCom).executeDataTable();
	        // 获取XML根元素   
	        Element root = doc.getRootElement();   
	        getElementList(root); 
	        
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			tran.clear();
			return false;
		} 
		tran.commit();
			
    	return true;
    }

	 public void getElementList(Element element) {
		ElementNo++;
        Element=element.getName(); 
        if(!element.isRootElement()){ //非根节点
    	   ParentElementNo=getParentElementNo(element.getParent().getPath().toString());  
        }else{
    	   ParentElementNo = "0";
        }

         if(element.attributeCount()>0){
        	 Attribute=element.attribute(0).getName();   //只取第一个属性的名称
         }
                  
         OrderNo = ElementNo;            
         Function = "";   
         String swap = "/";     //出现的字母
         LevelId = (element.getPath().length()-element.getPath().replaceAll(swap , "").length())/swap.length()-1;              
         ElementValue ="";          
         AttributeValue ="";   
		if(dt!=null && dt.getRowCount()>0){
			for (int i = 0; i < dt.getRowCount(); i++) {
				String tElement = (String) dt.get(i, 0);
				String tElementSQL = (String) dt.get(i, 2);
				String tElementSQLVar = (String) dt.get(i, 3);
				if(tElement!=null && tElement.equals(Element) && tElementSQL!=null && !tElementSQL.equals("")){
					ElementSQL = tElementSQL.replaceAll("'", "\\\\'");
					ElementSQLVar = tElementSQLVar;
					break;
				}else{
					ElementSQL = "";
					ElementSQLVar = "";
				}
			}
			for (int i = 0; i < dt.getRowCount(); i++) {
				String tAttribute = (String) dt.get(i, 1);
				String tAttributeSQL = (String) dt.get(i, 4);
				String tAttributeSQLVar = (String) dt.get(i, 5);
				if(tAttribute!=null && Attribute!=null && tAttribute.equals(Attribute) && tAttributeSQL!=null && !tAttributeSQL.equals("")){
					AttributeSQL = tAttributeSQL.replaceAll("'", "\\\\'");;
					AttributeSQLVar = tAttributeSQLVar;
					break;
				}else{
					AttributeSQL = "";
					AttributeSQLVar = "";
				}
			}
		}                
        AttributeNo= ElementNo; 
        mapElementPath.put(element.getPath(), String.valueOf(ElementNo));

        Object[] argArr = {ElementNo,Element,ParentElementNo,Attribute,OrderNo,Function,LevelId,ForFlag,AttributeNo};
        logger.info("ElementNo={} Element={}  ParentElementNo={} Attribute={}  OrderNo={}  Function={} LevelId={} ForFlag={} AttributeNo={}", argArr);
		//生成插入语句
		qb = new QueryBuilder("INSERT INTO `tabvxml` (`ElementNo`,`XMLName`,`XMLEncoding`,`XMLFilePath`,`Element`,`ParentElementNo`,`Attribute`,`OrderNo`,`Function`,`LevelId`,`ElementValue`,`AttributeValue`,`ElementSQL`,`ElementSQLVar`,`AttributeSQL`,`AttributeSQLVar`,`ManageCom`,`ForFlag`,`AttributeNo`)" 
				+" VALUES ("+ElementNo+",'"+XMLName+"','"+XMLEncoding+"','"+XMLFilePath+"','"+Element+"',"+ParentElementNo+",'"+Attribute+"',"+OrderNo+",'"+Function+"',"+LevelId+",'"+ElementValue+"','"+AttributeValue+"','"+ElementSQL+"','"+ElementSQLVar+"','"+AttributeSQL+"','"+AttributeSQLVar+"','"+ManageCom+"','"+ForFlag+"',"+AttributeNo+")");
		tran.add(qb);
		List elements = element.elements();
		// 有子元素
		if (!elements.isEmpty()) {
			// 有子元素
			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Element elem = (Element) it.next();
				// 递归遍历
				getElementList(elem);
			}
		} 
	 }
	 //得到父节点号
	 public String getParentElementNo(String ParentElement){
//		 System.out.println("==="+ParentElement);
		 String tParentElementNo = "";
		 Set entrySet = mapElementPath.entrySet();
	     Iterator itr = entrySet.iterator();
	     while (itr.hasNext()) {
	            Map.Entry entry = (Map.Entry) itr.next();
	            Object key = entry.getKey();
	            String value = (String) entry.getValue();
	            if(key.toString().equalsIgnoreCase(ParentElement)){
	            	tParentElementNo = value;
	            }
	      }
		 return tParentElementNo;
	 }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XMLParseIN tXMLParseIN = new XMLParseIN();
		tXMLParseIN.xmlParseIN("D:\\wangjin\\WebContent\\WEB-INF\\data\\backup\\09.xml","09.xml","2011");	

	}

}
