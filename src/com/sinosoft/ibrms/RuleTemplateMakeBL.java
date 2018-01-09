package com.sinosoft.ibrms;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.schema.LRBOMSchema;
import com.sinosoft.schema.LRBOMSet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RuleTemplateMakeBL {
    private static final Logger logger = LoggerFactory.getLogger(RuleTemplateMakeBL.class);
	public Errorx mErrors = new Errorx();

	// 日期时间
	private String sCurrentDate = IBRMSPubFun.getCurrentDate();
	private String sCurrentTime = IBRMSPubFun.getCurrentTime();

	private Map<String,String> tTransferData = new HashMap<String,String>();
	private String drlPath = "";
	
	public HashMap<String, LRBOMSchema> mBomMap = new HashMap<String, LRBOMSchema>();
	public ArrayList<String> mBomList = new  ArrayList<String>();
	public HashMap<String,ArrayList<EachRow>> bomMap;
	
	private  Map mDTColumnTypeMap;
	
	private HashMap<String, Integer> CtbaleMap = new HashMap<String, Integer>();
	
	private String TableName;
	
	private String mOperate;
	
	private String crtStr[];
	
	private Transaction trans = new Transaction();
	
	public RuleTemplateMakeBL() {
  	  mBomMap.clear();
  	  mBomList.clear();
	    LRBOMSchema dbLRBOMSchema = new LRBOMSchema();
	    dbLRBOMSchema.setValid("1");
	    LRBOMSet tLRBOMSet = dbLRBOMSchema.query();
	    if(tLRBOMSet!=null)
	    for (int i = 0; i < tLRBOMSet.size(); i++) {
	      LRBOMSchema tLRBOMSchema =tLRBOMSet.get(i);
	      mBomMap.put(tLRBOMSchema.getName(), tLRBOMSchema);
	    }
	}

	public boolean submitData(Map tVData,String cOperate) {
		tTransferData = tVData;
		mOperate = cOperate;
		if (tVData == null||mOperate==null) {
            logger.error("RuleTemplateMakeBL.submitData() :传入的数据为空！");
			mErrors.addError("RuleTemplateMakeBL.submitData() :传入的数据为空！");
			return false;
		}
		
		if(mOperate.equals("ADD")||mOperate.equals("UPDATE")){
			
			if (!checkData())
				return false;

			if (!prepareData())
				return false;

			if (!dealData())
				return false;
			else
				return true;
		}
		
		if(mOperate.equals("DELETE")){
			if(!checkDataDel()){
				return false;
			}
			if (!dealDataDel())
				return false;
			else
				return true;
			
		}
		return false;
	}
	
	private boolean checkDataDel(){
		String tLRTemplate_ID = (String) tTransferData.get("LRTemplate_ID");
		if(tLRTemplate_ID==null||tLRTemplate_ID.equalsIgnoreCase("")){
            logger.error("\t@> RuleTemplateMakeBL.checkData() :修改规则菜单中保存后台时，RuleTemplateID传递错误！");
			mErrors.addError("RuleTemplateMakeBL.checkData() :修改规则菜单中保存后台时，RuleTemplateID传递错误！");
			return false;
		}
		return true;
	}
	
	private boolean dealDataDel(){
		
		String LRTemplate_ID = (String) tTransferData.get("LRTemplate_ID");
		
		String sql = "delete from lrruletemplate where id='"+LRTemplate_ID+"'";
		
		trans.add(new QueryBuilder(sql));
		
		if (trans.commit()) {
			return true;
		} else {
			mErrors.addError("执行sql语句失败");
			return false;
		}
	}
	
	private boolean checkData() {
		String BOMS = (String) tTransferData.get("BOMS");
		String SQLPara = (String) tTransferData.get("SQLPara");
		String ViewPara = (String) tTransferData.get("ViewPara");
		
		//String SQLStatement = (String) tTransferData.get("SQLStatement");
		String RuleCh = (String) tTransferData.get("RuleCh");

		String RuleTemplateName = (String) tTransferData.get("RuleTemplateName");
		String RuleTemplateDes = (String) tTransferData.get("RuleTemplateDes");
		String Creator = (String) tTransferData.get("Creator");
		
		String Operation = (String) tTransferData.get("Operation");
		String flag = (String) tTransferData.get("flag");
		String LRTemplate_ID = (String) tTransferData.get("LRTemplate_ID");
		boolean dataTransmissionOK = true;

		if (BOMS == null || BOMS.equalsIgnoreCase("")) {
			logger.error("\t@> RuleTemplateMakeBL.checkData() :修改规则菜单中保存后台时，BOMS传递错误！");
			mErrors.addError("RuleTemplateMakeBL.checkData() :修改规则菜单中保存后台时，BOMS传递错误！");
			dataTransmissionOK = false;
		}
		if (RuleCh == null || RuleCh.equalsIgnoreCase("")) {
            logger.error("\t@> RuleTemplateMakeBL.checkData() :修改规则菜单中保存后台时，RuleCh传递错误！");
			mErrors.addError("RuleTemplateMakeBL.checkData() :修改规则菜单中保存后台时，RuleCh传递错误！");
			dataTransmissionOK = false;
		}
		if (Creator == null || Creator.equalsIgnoreCase("")) {
            logger.error("\t@> RuleTemplateMakeBL.checkData() :修改规则菜单中保存后台时，Creator传递错误！");
			mErrors.addError("RuleTemplateMakeBL.checkData() :修改规则菜单中保存后台时，Creator传递错误！");
			dataTransmissionOK = false;
		}
		if(!mOperate.equals("UPDATE")){
			DataTable dt = null;
			String sql = "select 1 from lrruletemplate where ruletemplatename = '"+RuleTemplateName+"'";
			dt = new QueryBuilder(sql).executeDataTable();
			if (dt.getRowCount()!=0) {
				mErrors.addError("系统中已存在该模板名称，请确认或更换模板名后重新录入！");
				dataTransmissionOK = false;
			}
		}
		if (!dataTransmissionOK)
			return false;

		return true;
	}

	boolean prepareData() {

		String Operation = (String) tTransferData.get("Operation");
		String flag = (String) tTransferData.get("flag");
		String Creator = (String) tTransferData.get("Creator");

		String SQLStatement = (String) tTransferData
				.get("SQLStatement");
		String Modifier = null;

		
		tTransferData.remove("Creator");
		tTransferData.put("Creator", Creator);

		tTransferData.put("Modifier", Modifier);

		tTransferData.put("MakeDate", sCurrentDate);
		tTransferData.put("MakeTime", sCurrentTime);
		tTransferData.put("ModifyDate", sCurrentDate);
		tTransferData.put("ModifyTime", sCurrentTime);
		
		return true;
	}


	private boolean dealData() {
		// 获取要处理的数据
		String BOMS = (String) tTransferData.get("BOMS");
		String ViewPara = (String) tTransferData.get("ViewPara");
		String RuleCh = (String) tTransferData.get("RuleCh");
		String RuleTemplateName = (String) tTransferData.get("RuleTemplateName");
		String RuleTemplateDes = (String) tTransferData.get("RuleTemplateDes");
//		String TableName = (String) tTransferData.get("TableName");
//		System.out.println("获取到的TableName是：" + TableName);
		String Creator = (String) tTransferData.get("Creator");
		String LRTemplate_ID = "";
		if(mOperate.equals("UPDATE")){
			LRTemplate_ID = (String) tTransferData.get("LRTemplate_ID");
		}else{
		// 准备要处理的数据
			LRTemplate_ID = IBRMSPubFun.getLRRuleTemplateID();
		}
		//生成规则文件
		CreatDrl(null,LRTemplate_ID,ViewPara);
		if(mOperate.equals("UPDATE")){
			
			String sql = "update LRRuleTemplate set ruletemplatename='"+RuleTemplateName+"',boms='"+BOMS+"',SQLPARAMETER ='"+drlPath+"',DESCRIPTION='"+RuleTemplateDes+"',CREATOR='"+Creator+"',MODIFYDATE='"+sCurrentDate+"',MODIFYTIME='"+sCurrentTime+"',RULECH='"+RuleCh+"',VIEWPARAMETER='"+ViewPara+"' where id='"+LRTemplate_ID+"'";
			
			trans.add(new QueryBuilder(sql));
			
			if (trans.commit()) {
				return true;
			} else {
				mErrors.addError("执行sql语句失败");
				return false;
			}
			
		}else{
			String sql = "insert into LRRuleTemplate values('"+LRTemplate_ID+"','"+RuleTemplateName+"','"+BOMS+"','"+drlPath+"','"+RuleTemplateDes+"','"+Creator+"','"+sCurrentTime+"','"+sCurrentDate+"','"+sCurrentTime+"','"+sCurrentDate+"','"+RuleCh+"','"+ViewPara+"')";
			
			trans.add(new QueryBuilder(sql));
			
			if (trans.commit()) {
				return true;
			} else {
				mErrors.addError("执行sql语句失败");
				return false;
			}
		}
	}
	
	private String dealOperator(String str){
		return str.replace("?", " ").
		replace("and", "&&").
		replace("{", " ( ").
		replace("}", " ) ").
		replace("or", "||").
		replace("+_o", "+").
		replace("-_o", "-").
		replace("*_o", "*").
		replace("=_o", "=");
	}
	
	
	/**
	 * 
	 * @param tJSONObject
	 * @param IfSql
	 * @param ThenSql
	 * @return
	 */
  public boolean CreatDrl(String data, String rulID, String viewPara) {
    PrintWriter out = null;
    Document doc = null;
    try {
      String sql = "select codevalue from zdcode where codetype='DRLTemplatePath'";
      String Path = "";
      DataTable dt = null;

		dt = new QueryBuilder(sql).executeDataTable();
		if (dt.getString(0, 0) != null) {
			Path = dt.getString(0, 0) ;
		} else {
            logger.error("Creator获取错误！");
		}
      
      doc = CreateDocumentByString(viewPara);
      
      bomMap = AnalyseDoc(doc);
      
      String conditionContent="";
      String resultContent="";
      
      for (int i = 0; i < mBomList.size(); i++) {
        String bomName = mBomList.get(i);
        String init="";
        String conditions="";
        ArrayList<EachRow> rowsArrayList=bomMap.get(bomName);
        for (int j = 0; j < rowsArrayList.size(); j++) {
          if("INIT".equals(rowsArrayList.get(j).getPosition()))
          {
            init =rowsArrayList.get(j).getContent();
          }
          else if("CONDITION".equals(rowsArrayList.get(j).getPosition()))
          {
            if(!"".equals(conditions))
              conditions +=",";
            conditions += rowsArrayList.get(j).getContent();
          }
          else if("RESULT".equals(rowsArrayList.get(j).getPosition()))
          {
            resultContent += "        "+rowsArrayList.get(j).getContent()+";\n";
          }
        }
        conditionContent += "        "+init+conditions+")\n";
      }
      StringBuffer ruleTemp= new StringBuffer();
      ruleTemp.append("    when\n"); 
      ruleTemp.append(conditionContent);
      ruleTemp.append("\n");
      ruleTemp.append("    then\n"); 
      ruleTemp.append(resultContent);
      ruleTemp.append("\n");

      
      
      File tempFile = new File(Path);
      if (!tempFile.exists()) {
        tempFile.mkdirs();
      }
      // 取得规则文件的存储路径

      String FileName = rulID + ".drl"; // drl 文件名

      out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);
      drlPath = Path + FileName;

      out.println("package com.sinosoft.drools");
      out.println("import com.sinosoft.ibrms.bom.*;");
      // @Import
      out.println("");
      
      
      if(data!=null&&!"null".equals(data))
      {
      JSONObject jsonObj = new JSONObject(data);
      JSONArray tJSONArray = jsonObj.getJSONArray("rows");
      JSONObject temp;

      // 文件头信息
      // @Package

      
      Iterator tIterator = null;
      String key = null;
      String value=null;
      String tempIf;
      String tempThen;

      for(int i = 0;i<tJSONArray.length();i++){
        String rule=ruleTemp.toString();
        temp = tJSONArray.getJSONObject(i);
        tIterator = temp.keys();        
        
         while (tIterator.hasNext())
         {
           key = (String)tIterator.next();
           value= temp.getString(key).replaceAll("\"", "");
           if(value==null||"".equals(value))
             value="null";
           rule = rule.replaceFirst(key,value);
           
           if(CtbaleMap.containsKey(key))
    	   {
    		   if((value.length()-CtbaleMap.get(key))>0)
    		   {
    			   CtbaleMap.put(key, value.getBytes().length+2);
    		   }
    	   }
    	   else
    	   {
    		   CtbaleMap.put(key, value.getBytes().length+2);
    	   }
                     
         }
        
        out.println("rule \""+rulID+"_"+i+"\"");
        out.println("dialect \"mvel\"");
        out.println(rule);
        out.println("end");
        out.println("");
      }
      
      for(int i = 0;i<tJSONArray.length();i++)
      {
    	  
          temp = tJSONArray.getJSONObject(i);
          tIterator = temp.keys();              
          
    	  String insertStr = "insert into "+this.TableName+" values ( ";
          
          for(int j = 0; j<crtStr.length; j++)
          {
        	value= temp.getString(crtStr[j]).replaceAll("\"", "");
        	String ItemType = (String)mDTColumnTypeMap.get(crtStr[j]);
        	
        	if("Date".equals(ItemType))
        	{
        		value = value.substring(0, 10);
        	}
        	
          	insertStr += "'"+value+"'";
          	
          	if(j!=crtStr.length-1)
          	{
          		insertStr += " , ";
          	}
          	
          }
          
          insertStr += " )";
          trans.add(new QueryBuilder(insertStr));
      }
      
      
      }
      else {
        out.println("rule \""+rulID+"\"");
        out.println("dialect \"mvel\"");
        out.println(ruleTemp.toString());
        out.println("end");
        out.println("");
      }
    } catch (Exception e) {
        logger.error("生成源文件失败!!" + e.getMessage(), e);
        mErrors.addError("创建java文件出错，原因为");
    } finally {
        if (out != null)
        out.close();
    }

    return true;
  }
	
  private Document CreateDocumentByString(String xmlDocument)
	    throws SAXException, IOException, ParserConfigurationException {
	   DocumentBuilder builder = DocumentBuilderFactory.newInstance()
	     .newDocumentBuilder();
	   Document document=builder.parse(new InputSource(new StringReader(xmlDocument)));
	   return document;
	}
	
	private HashMap<String,ArrayList<EachRow>> AnalyseDoc(Document doc)
	{
      HashMap<String,ArrayList<EachRow>> bomMap=new HashMap<String,ArrayList<EachRow>>();
	  
	  Element root=doc.getDocumentElement();
	  NodeList conditionList = root.getElementsByTagName("Condition");
      NodeList resultList = root.getElementsByTagName("Result");      
      
      for (int i = 0; i < conditionList.getLength(); i++) {
        Node condition= conditionList.item(i);
        NodeList metaNodeList = condition.getChildNodes();
        
        String mainBomName = "";
        String conditionContent="";
        int paraNum = 0;
        boolean Left_Paren = false;
        boolean Right_Paren = false;
        for (int j = 0; j < metaNodeList.getLength(); j++) {
          Node metaNode = metaNodeList.item(j);
          NamedNodeMap attrMap = metaNode.getAttributes();
          
          String nodeID = attrMap.getNamedItem("id").getNodeValue();
          String enName = attrMap.getNamedItem("EnName").getNodeValue();

          
          if("Link".equals(nodeID))
          {
              continue;
          }
          else if("BOM".equals(nodeID))
          {
            if("".equals(mainBomName))
            {
                mainBomName = enName; 
            }
            InitBom(bomMap,enName);
          }
          else if("BOMItem".equals(nodeID))
          {
            String bomName = attrMap.getNamedItem("BomName").getNodeValue();
            String itemContent="";
            if(mainBomName.equals(bomName))
            {
              itemContent =enName;
            }
            else 
            {
              itemContent ="$"+bomName+"."+enName;
            }
            if(Left_Paren)
            {
              itemContent = "("+itemContent;
              Left_Paren = false;
            }
            if(Right_Paren)
            {
              itemContent = itemContent+")";
              Right_Paren = false;
            }
            if (paraNum>0) {
              conditionContent= conditionContent.replace("##",itemContent);
              paraNum --;
            }
            else {
              conditionContent += itemContent;
            }
          }
          
          else if("Operator".equals(nodeID))
          {
            String paraNumString=attrMap.getNamedItem("IsNextOperator").getNodeValue();
            if(paraNumString!=null)
            {
              paraNum = Integer.parseInt(paraNumString);
            }
            conditionContent += " "+enName;
          }
          else if("Left_Paren".equals(nodeID))
          {
            Left_Paren = true;
          }
          else if("Right_Paren".equals(nodeID))
          {
            Right_Paren = true;
          }
          else if("Input".equals(nodeID))
          {
            String tableValue = attrMap.getNamedItem("TableValue").getNodeValue();
            if(tableValue!=null&&!"".equals(tableValue))
              enName = tableValue;
            if(enName!=null&&!"".equals(enName))
            {
              if(Left_Paren)
              {
                enName = "("+enName;
                Left_Paren = false;
              }
              if(Right_Paren)
              {
                enName = enName+")";
                Right_Paren = false;
              }
              if (paraNum>0) {
                conditionContent = conditionContent.replace("##",enName);
                paraNum --;
              }
              else {
                conditionContent += enName;
              }
            }
          }
        }
        ArrayList<EachRow> rows = bomMap.get(mainBomName);
        EachRow row = new EachRow();
        row.setPosition("CONDITION");
        row.setContent(conditionContent);
        rows.add(row);
        bomMap.put(mainBomName, rows);
      }
      
      for (int i = 0; i < resultList.getLength(); i++) {
        Node result= resultList.item(i);
        NodeList metaNodeList = result.getChildNodes();
        
        String mainBomName = "";
        String resultContent="";
        int paraNum = 0;
        boolean Left_Paren = false;
        boolean Right_Paren = false;
        for (int j = 0; j < metaNodeList.getLength(); j++) {
          Node metaNode = metaNodeList.item(j);
          NamedNodeMap attrMap = metaNode.getAttributes();
          
          String nodeID = attrMap.getNamedItem("id").getNodeValue();
          String enName = attrMap.getNamedItem("EnName").getNodeValue();
          
          if("BOM".equals(nodeID))
          {
            if("".equals(mainBomName))
            {
                mainBomName = enName; 
            }
            InitBom(bomMap,enName);
          }
          else if("BOMItem".equals(nodeID))
          {
            String bomName = attrMap.getNamedItem("BomName").getNodeValue();
            String itemContent = "$"+bomName+"."+enName;
            
            if(Left_Paren)
            {
              itemContent = "("+itemContent;
              Left_Paren = false;
            }
            if(Right_Paren)
            {
              itemContent = ")"+itemContent;
              Right_Paren = false;
            }
            if (paraNum>0) {
              resultContent= resultContent.replace("##",itemContent);
              paraNum--;
            }
            else {
              resultContent += itemContent;
            }
          }
          else if("Operator".equals(nodeID))
          {
            String paraNumString=attrMap.getNamedItem("IsNextOperator").getNodeValue();
            if(paraNumString!=null)
            {
              paraNum = Integer.parseInt(paraNumString);
            }
            if(Right_Paren)
            {
              enName = ")"+enName;
              Right_Paren = false;
            }
            resultContent += enName;
          }
          else if("Left_Paren".equals(nodeID))
          {
            Left_Paren = true;
          }
          else if("Right_Paren".equals(nodeID))
          {
            Right_Paren = true;
            if(j == metaNodeList.getLength()-1)
              resultContent += enName;  
          }
          else if("Input".equals(nodeID))
          {
            String tableValue = attrMap.getNamedItem("TableValue").getNodeValue();
            if(tableValue!=null&&!"".equals(tableValue))
              enName = tableValue;
            if(enName!=null&&!"".equals(enName))
            {
              if(Left_Paren)
              {
                enName = "("+enName;
                Left_Paren = false;
              }
              if(Right_Paren)
              {
                enName = enName+")";
                Right_Paren = false;
              }
              if (paraNum>0) {
                resultContent = resultContent.replace("##",enName);
                paraNum --;
              }
              else {
                resultContent += enName;
              }
            }
          }
        }
        
        ArrayList<EachRow> rows = bomMap.get(mainBomName);
        EachRow row = new EachRow();
        row.setPosition("RESULT");
        row.setContent(resultContent);
        rows.add(row);
        bomMap.put(mainBomName, rows);
      }
      
      return bomMap;
	}
	
	private static class EachRow {

	  public String position="";
	  
	  public String content="";

	  public String getPosition() {
		  return position;
	  }

	  public void setPosition(String position) {
		  this.position = position;
	  }

	  public String getContent() {
		  return content;
	  }

	  public void setContent(String content) {
		  this.content = content;
	  }

  }
	
	private void InitBom(HashMap<String,ArrayList<EachRow>> bomMap,String bomName)
 {
    LRBOMSchema tLRBOMSchema = mBomMap.get(bomName);
    if (bomMap.get(bomName) == null) {
      String tFBOM = tLRBOMSchema.getFBOM();
      String tFatherItem = tLRBOMSchema.getFatherItem();
      if (tFBOM!=null&&!"".equals(tFBOM)) {
        InitBom(bomMap, tFBOM);
        ArrayList<EachRow> rows = new ArrayList<EachRow>();
        EachRow initRow = new EachRow();
        initRow.setPosition("INIT");
        initRow.setContent("$" + bomName + ":" + bomName + "(");
        rows.add(initRow);
        EachRow linkRow = new EachRow();
        linkRow.setPosition("CONDITION");
        linkRow.setContent(tFatherItem + "==" + "$" + tFBOM + "." + tFatherItem);
        rows.add(linkRow);
        bomMap.put(bomName, rows);
        mBomList.add(bomName);
      } else {
        ArrayList<EachRow> rows = new ArrayList<EachRow>();
        EachRow row = new EachRow();
        row.setPosition("INIT");
        row.setContent("$" + bomName + ":" + bomName + "(");
        rows.add(row);
        bomMap.put(bomName, rows);
        mBomList.add(bomName);
      }
    }
  }
	
	public static final void main(String[] args) {
	  RuleTemplateMakeBL t = new RuleTemplateMakeBL();

	  


	  //String xmlDocString="<Rule><Condition><MetaNode id=\"BOM\" EnName=\"BOMAgent\" ChName=\"代理人\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"AgentGrade\" ChName=\"的代理人职级\" MatchType=\"String\" Source=\"select gradecode,gradename from laagentgrade\" isHierarchical=\"0\" BomName=\"BOMAgent\"/><MetaNode id=\"Operator\" EnName=\"== null\" ChName=\"是空\" MatchType=\"String\" ResultType=\"Boolean\" ParaType=\"String\" IsNextOperator=\"0\"/></Condition><Result><MetaNode id=\"BOM\" EnName=\"BOMAgent\" ChName=\"代理人\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"AgentGrade\" ChName=\"的代理人职级\" MatchType=\"String\" Source=\"select gradecode,gradename from laagentgrade\" isHierarchical=\"0\" BomName=\"BOMAgent\"/><MetaNode id=\"Operator\" EnName=\"PubFun.str_equal(#,@)==true\" ChName=\"是\" MatchType=\"String\" ResultType=\"Boolean\" ParaType=\"String\" IsNextOperator=\"2\"/><MetaNode id=\"Input\" EnName=\"\" ChName=\"请输入值\"/></Result></Rule>";
	  //String xmlDocString = "<Rule><Condition><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"c1\" ChName=\"的C1\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"&gt;=##\" ChName=\"大于等于\" MatchType=\"Number\" ResultType=\"Boolean\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"0\" ChName=\"0\" TableValue=\"\"/></Condition><Condition><MetaNode id=\"Link\" EnName=\"and\" ChName=\"并且\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"c2\" ChName=\"的C2\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"&gt;=##\" ChName=\"大于等于\" MatchType=\"Number\" ResultType=\"Boolean\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"0\" ChName=\"0\" TableValue=\"\"/></Condition><Result><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"raYBXQM\" ChName=\"银保续期提奖\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"=##\" ChName=\"是\" MatchType=\"Number\" ResultType=\"Over\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Left_Paren\" EnName=\"(\" ChName=\"(\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"raSSBF\" ChName=\"二次银保单实收保费\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"0.008\" ChName=\"0.008\" TableValue=\"\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"c1\" ChName=\"的C1\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Right_Paren\" EnName=\")\" ChName=\")\"/><MetaNode id=\"Operator\" EnName=\"+##\" ChName=\"加\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Left_Paren\" EnName=\"(\" ChName=\"(\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"ra3SSBF\" ChName=\"的三次银保单实收保费\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"0.002\" ChName=\"0.002\" TableValue=\"\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"c2\" ChName=\"的C2\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Right_Paren\" EnName=\")\" ChName=\")\"/></Result></Rule>";
	  
	  String xmlDocString = "<Rule><Condition><MetaNode id=\"BOM\" EnName=\"DuplicateBO\" ChName=\"除重规则\"/><MetaNode id=\"BOMItem\" EnName=\"isCardId\" ChName=\"的是否包含该车牌号\" MatchType=\"String\" Source=\"select code,codename from Fdcode where codetype = ''yesno1''\" isHierarchical=\"0\" BomName=\"DuplicateBO\"/><MetaNode id=\"Operator\" EnName=\"==&quot;##&quot;\" ChName=\"是\" MatchType=\"String\" ResultType=\"Boolean\" ParaType=\"String\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"\" ChName=\"请输入值\" TableValue=\"DUPLICATEBO_ISCARDID1\"/></Condition><Result><MetaNode id=\"BOM\" EnName=\"RiskBO\" ChName=\"保险产品\"/><MetaNode id=\"BOMItem\" EnName=\"amount\" ChName=\"的份数\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"RiskBO\"/><MetaNode id=\"Operator\" EnName=\"=##\" ChName=\"是\" MatchType=\"Number\" ResultType=\"Over\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"\" ChName=\"请输入值\" TableValue=\"RISKBO_AMOUNT2\"/></Result></Rule>";
	  

	      try {
	    t.CreatDrl(null,"0001",xmlDocString);
	    String conditionString="";
	    conditionString.length();
	    
	    

    } catch (Exception e) {
      e.printStackTrace();
    }
	}
	
}