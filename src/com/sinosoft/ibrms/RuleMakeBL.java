package com.sinosoft.ibrms;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.schema.LRBOMSchema;
import com.sinosoft.schema.LRBOMSet;
import com.sinosoft.schema.LRTemplateTSchema;
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

public class RuleMakeBL {
	private static final Logger logger = LoggerFactory.getLogger(RuleMakeBL.class);
	public Errorx mErrors = new Errorx();

	// 日期时间
	private String sCurrentDate = IBRMSPubFun.getCurrentDate();
	private String sCurrentTime = IBRMSPubFun.getCurrentTime();
	private String drlPath = "";

	public HashMap<String, LRBOMSchema> mBomMap = new HashMap<String, LRBOMSchema>();
	public ArrayList<String> mBomList = new ArrayList<String>();
	public HashMap<String, ArrayList<EachRow>> bomMap;

	private Map mDTColumnTypeMap;

	private HashMap<String, Integer> CtbaleMap = new HashMap<String, Integer>();

	private String TableName;

	private String crtStr[];

	private Map tTransferData;

	private Transaction trans = new Transaction();

	public RuleMakeBL() {
		trans.clear();
		mBomMap.clear();
		mBomList.clear();
		LRBOMSchema dbLRBOMSchema = new LRBOMSchema();
		dbLRBOMSchema.setValid("1");
		LRBOMSet tLRBOMSet = dbLRBOMSchema.query();
		if (tLRBOMSet != null)
			for (int i = 0; i < tLRBOMSet.size(); i++) {
				LRBOMSchema tLRBOMSchema = tLRBOMSet.get(i);
				mBomMap.put(tLRBOMSchema.getName(), tLRBOMSchema);
			}

	}

	public boolean submitData(Map tVData) {
		if (tVData == null) {
			logger.error("RuleMakeBL.submitData() :传入的数据为空！");
			this.mErrors.addError("RuleMakeBL.submitData() :传入的数据为空！");
			return false;
		}
		tTransferData = tVData;
		if (!checkData())
			return false;

		if (!prepareData())
			return false;

		if (!dealData())

			return false;

		else
			return true;
	}

	private boolean checkData() {
		String BOMS = (String) tTransferData.get("BOMS");
		String SQLPara = (String) tTransferData.get("SQLPara");
		String ViewPara = (String) tTransferData.get("ViewPara");

		// String SQLStatement = (String) tTransferData.get("SQLStatement");
		String RuleCh = (String) tTransferData.get("RuleCh");

		String RuleName = (String) tTransferData.get("RuleName");
		String RuleDes = (String) tTransferData.get("RuleDes");
		String Creator = (String) tTransferData.get("Creator");
		String RuleStartDate = (String) tTransferData.get("RuleStartDate");
		String RuleEndDate = (String) tTransferData.get("RuleEndDate");
		// String TempalteLevel = (String) tTransferData
		// .get("TempalteLevel");
		String Business = (String) tTransferData.get("Business");
		String State = (String) tTransferData.get("State");
		String Valid = (String) tTransferData.get("Valid");
		// String RuleTYpe =(String)tTransferData.get("RuleTYpe");
		String Operation = (String) tTransferData.get("Operation");
		String flag = (String) tTransferData.get("flag");
		String TableName = (String) tTransferData.get("TableName");
		String LRTemplate_ID = (String) tTransferData.get("LRTemplate_ID");

		boolean dataTransmissionOK = true;

		if (flag.equalsIgnoreCase("4")) {
			if (TableName == null || TableName.equalsIgnoreCase("")) {
				logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，DT表名传递错误！");
				this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，DT表名传递错误！");
				dataTransmissionOK = false;
			}
			if (LRTemplate_ID == null || LRTemplate_ID.equalsIgnoreCase("")) {
				logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，LRTemplate_ID表名传递错误！");
				this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，LRTemplate_ID表名传递错误！");
				dataTransmissionOK = false;
			}
		}

		if (BOMS == null || BOMS.equalsIgnoreCase("")) {
			logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，BOMS传递错误！");
			this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，BOMS传递错误！");
			dataTransmissionOK = false;
		}
		// if (SQLPara == null || SQLPara.equalsIgnoreCase("")) {
		// System.out
		// .println("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，SQLPara传递错误！");
		// dataTransmissionOK = false;
		// }
		if (ViewPara == null || ViewPara.equalsIgnoreCase("")) {
			logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，ViewPara传递错误！");
			this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，ViewPara传递错误！");
			dataTransmissionOK = false;
		}
		// if (SQLStatement == null || SQLStatement.equalsIgnoreCase("")) {
		// System.out
		// .println("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，SQLStatement传递错误！");
		// dataTransmissionOK = false;
		// }
		if (RuleCh == null || RuleCh.equalsIgnoreCase("")) {
			logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，RuleCh传递错误！");
			this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，RuleCh传递错误！");
			dataTransmissionOK = false;
		}
		if (Creator == null || Creator.equalsIgnoreCase("")) {
			logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，Creator传递错误！");
			this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，Creator传递错误！");
			dataTransmissionOK = false;
		}
		if (RuleStartDate == null || RuleStartDate.equalsIgnoreCase("")) {
			logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，RuleStartDate传递错误！");
			this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，RuleStartDate传递错误！");
			dataTransmissionOK = false;
		}
		// if (TempalteLevel == null || TempalteLevel.equalsIgnoreCase("")) {
		// System.out
		// .println("\t@> RuleMakeBL.checkData() :修改规则菜单中保存后台时，TempalteLevel传递错误！");
		// dataTransmissionOK = false;
		// }
		//
		if (Business == null || Business.equalsIgnoreCase("")) {
			logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，Business传递错误！");
			this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，Business传递错误！");
			dataTransmissionOK = false;
		}
		if (State == null || State.equalsIgnoreCase("")) {
			logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，State传递错误！");
			this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，State传递错误！");
			dataTransmissionOK = false;
		}
		if (Valid == null || Valid.equalsIgnoreCase("")) {
			logger.error("RuleMakeBL.checkData() :修改规则菜单中保存后台时，Valid传递错误！");
			this.mErrors.addError("RuleMakeBL.checkData() :修改规则菜单中保存后台时，Valid传递错误！");
			dataTransmissionOK = false;
		}
		if (!dataTransmissionOK)

			return false;

		return true;
	}

	boolean prepareData() {

		String Operation = (String) tTransferData.get("Operation");
		String flag = (String) tTransferData.get("flag");
		// String TableName = (String) tTransferData.get("TableName");
		String State = (String) tTransferData.get("State");
		String Creator = (String) tTransferData.get("Creator");

		String LRTemplate_ID = (String) tTransferData.get("LRTemplate_ID");

		String SQLStatement = (String) tTransferData.get("SQLStatement");
		// String CreateTable = (String) tTransferData
		// .get("CreateTable");
		String DTData = (String) tTransferData.get("DTData");

		logger.info("准备数据总的DTData是::{}", DTData);

		mDTColumnTypeMap = (Map) tTransferData.get("DTColumnTypeMap");

		String Modifier = null;

		if (flag.equalsIgnoreCase("4")) {
			Modifier = Creator;

			String sql = "select Creator from lrtemplatet where id='" + LRTemplate_ID + "'";
			DataTable dt = null;

			dt = new QueryBuilder(sql).executeDataTable();
			if (dt.getString(0, 0) != null) {
				Creator = dt.getString(0, 0);
			} else {
				logger.error("Creator获取错误！");
			}

		} else {

			// String DTTableSerialNumber = PubFun1.CreateMaxNo("ibrmsDTTNo",
			// 4);
			// System.out.println("获取的流水号(DTTableSerialNumber)是："
			// + DTTableSerialNumber);
			LRTemplate_ID = IBRMSPubFun.getLRTemplateID();
			logger.info("获取的流水号(LRTemplateTSerialNumber)是：{}", LRTemplate_ID);

			// TableName = "DTT" + DTTableSerialNumber;

		}
		// System.out.println("TableName是：" + TableName);
		// SQLStatement = SQLStatement.replaceAll("#DTTable#", TableName);
		// CreateTable = CreateTable.replaceAll("#DTTable#", TableName);

		// ArrayList SQLArray = prepareInsert(DTData, TableName,
		// DTColumnTypeMap);
		// System.out.println("插入数据准备结束：" + SQLArray.toString());
		// tTransferData.removeByName("TableName");
		// tTransferData.setNameAndValue("TableName", TableName);
		// System.out.println("保存的TableName是：" + TableName);
		// tTransferData.removeByName("SQLStatement");
		// tTransferData.setNameAndValue("SQLStatement", SQLStatement);
		//
		// tTransferData.removeByName("SQLArray");
		// tTransferData.setNameAndValue("SQLArray", SQLArray);
		// tTransferData.removeByName("CreateTable");
		// tTransferData.setNameAndValue("CreateTable", CreateTable);

		State = "1";
		tTransferData.remove("State");
		tTransferData.put("State", State);

		tTransferData.remove("LRTemplate_ID");
		tTransferData.put("LRTemplate_ID", LRTemplate_ID);

		tTransferData.remove("Creator");
		tTransferData.put("Creator", Creator);

		tTransferData.put("Modifier", Modifier);

		tTransferData.put("MakeDate", sCurrentDate);
		tTransferData.put("MakeTime", sCurrentTime);
		tTransferData.put("ModifyDate", sCurrentDate);
		tTransferData.put("ModifyTime", sCurrentTime);

		int Version = 1;
		String sql = "select max(version) from LRTemplateB where Id='" + LRTemplate_ID + "'";
		DataTable dt = null;

		dt = new QueryBuilder(sql).executeDataTable();
		int rowCount = dt.getRowCount();
		if ("null".equalsIgnoreCase(dt.getString(0, 0)) || "".equalsIgnoreCase(dt.getString(0, 0))) {
			Version = 1;
		} else {
			Version = Integer.parseInt(dt.getString(0, 0)) + 1;
		}

		tTransferData.put("Version", Integer.toString(Version));

		return true;
	}

	private boolean dealData() {
		// 获取要处理的数据
		String LRTemplate_ID = (String) tTransferData.get("LRTemplate_ID");

		String BOMS = (String) tTransferData.get("BOMS");

		String SQLPara = (String) tTransferData.get("SQLPara");
		String ViewPara = (String) tTransferData.get("ViewPara");
		String SQLStatement = (String) tTransferData.get("SQLStatement");
		String RuleCh = (String) tTransferData.get("RuleCh");
		String RuleName = (String) tTransferData.get("RuleName");
		String RuleDes = (String) tTransferData.get("RuleDes");
		String TableName = (String) tTransferData.get("TableName");
		// System.out.println("获取到的TableName是：" + TableName);
		String Creator = (String) tTransferData.get("Creator");
		String RuleStartDate = (String) tTransferData.get("RuleStartDate");
		String RuleEndDate = (String) tTransferData.get("RuleEndDate");
		// String TempalteLevel = (String) tTransferData
		// .get("TempalteLevel");
		String Business = (String) tTransferData.get("Business");
		String State = (String) tTransferData.get("State");
		String Valid = (String) tTransferData.get("Valid");
		String MakeDate = (String) tTransferData.get("MakeDate");
		String MakeTime = (String) tTransferData.get("MakeTime");
		String ModifyDate = (String) tTransferData.get("ModifyDate");
		String ModifyTime = (String) tTransferData.get("ModifyTime");
		String Version = (String) tTransferData.get("Version");
		String RuleTYpe = (String) tTransferData.get("RuleTYpe");
		String Modifier = (String) tTransferData.get("Modifier");
		String MarketingNum = (String) tTransferData.get("MarketingNum");

		// 保存结果的sql
		String SQLReuslt = (String) tTransferData.get("SQLReuslt");

		/*
		 * SQLStatement = SQLStatement.replaceAll("#DTTable#", "DTT" +
		 * maxIndex);
		 */

		// 准备要处理的数据
		LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
		tLRTemplateTSchema.setId(LRTemplate_ID);
		tLRTemplateTSchema.setVersion(Integer.parseInt(Version));
		tLRTemplateTSchema.setSQLStatement(SQLStatement);
		tLRTemplateTSchema.setRuleCh(RuleCh);
		tLRTemplateTSchema.setBOMs(BOMS);
		tLRTemplateTSchema.setSQLParameter(SQLPara);
		tLRTemplateTSchema.setViewParameter(ViewPara);
		tLRTemplateTSchema.setValid(Valid);
		tLRTemplateTSchema.setState(State);
		tLRTemplateTSchema.setTemplateLevel("00");
		tLRTemplateTSchema.setBusiness(Business);

		tLRTemplateTSchema.setStartDate(IBRMSPubFun.getDate(RuleStartDate));
		if (RuleEndDate != null && !"".equals(RuleEndDate)) {
			tLRTemplateTSchema.setEndDate(IBRMSPubFun.getDate(RuleEndDate));
		}
		tLRTemplateTSchema.setDescription(RuleDes);
		tLRTemplateTSchema.setCreator(Creator);
		tLRTemplateTSchema.setMakeDate(IBRMSPubFun.getDate(MakeDate));
		tLRTemplateTSchema.setMakeTime(MakeTime);
		tLRTemplateTSchema.setModifyDate(IBRMSPubFun.getDate(ModifyDate));
		tLRTemplateTSchema.setModifyTime(ModifyTime);
		tLRTemplateTSchema.setRuleName(RuleName);
		// tLRTemplateTSchema.setTableName(TableName);
		// System.out.println("setTableName:" + TableName);
		//		
		// tLRTemplateTSchema.setResultSql(SQLReuslt);//暂时保存结果sql
		tLRTemplateTSchema.setType(RuleTYpe);

		tLRTemplateTSchema.setModifier(Modifier);
		tLRTemplateTSchema.setMarketingNum(MarketingNum);
		String CreateTable = (String) tTransferData.get("CreateTable");
		ArrayList SQLArray = (ArrayList) tTransferData.get("SQLArray");

		// TransferData transferData = new TransferData();

		String flag = (String) tTransferData.get("flag");
		String Operation = (String) tTransferData.get("Operation");
		// String TableName=(String)tTransferData.get("TableName");
		String DTData = (String) tTransferData.get("DTData");
		// 生成规则文件
		CreatDrl(DTData, LRTemplate_ID, ViewPara);
		// 保存规则文件路径
		tLRTemplateTSchema.setDRLPath(drlPath);
		tLRTemplateTSchema.setTableName(this.TableName);
		trans.add(tLRTemplateTSchema, Transaction.DELETE_AND_INSERT);

		if (trans.commit()) {
			return true;
		} else {
			mErrors.addError("执行sql语句失败");
			return false;
		}
	}

	public boolean CreatTable() {

		String flag = (String) tTransferData.get("flag");

		if (flag.equals("4")) {// 如果是修改规则，则删除原来的数据表，以免产生过多无用表
			TableName = (String) tTransferData.get("TableName");
			trans.add(new QueryBuilder("drop table " + TableName));
		} else {
			String DTTableSerialNumber = IBRMSPubFun.getDTTableSerialNumber();
			logger.info("获取的流水号(DTTableSerialNumber)是：{}", DTTableSerialNumber);
			TableName = "DTT" + DTTableSerialNumber;
		}

		String CreatStr = "create table " + TableName + " (";

		Iterator Creatitem = mDTColumnTypeMap.keySet().iterator();

		crtStr = new String[mDTColumnTypeMap.size()];

		int i = 0;

		while (Creatitem.hasNext()) {
			String TableItem = (String) Creatitem.next();
			String ItemType = (String) mDTColumnTypeMap.get(TableItem);

			crtStr[i++] = TableItem;

			if ("Date".equals(ItemType)) {
				CreatStr += TableItem + " DATE ";

				if (Creatitem.hasNext()) {
					CreatStr += ", ";
				} else {
					CreatStr += ")";
				}

			}

			if ("Number".equals(ItemType)) {
				CreatStr += TableItem + " Integer ";

				if (Creatitem.hasNext()) {
					CreatStr += ", ";
				} else {
					CreatStr += ")";
				}

			}

			if ("String".equals(ItemType)) {
				CreatStr += TableItem + " VARCHAR(" + CtbaleMap.get(TableItem).toString() + ") ";

				if (Creatitem.hasNext()) {
					CreatStr += ", ";
				} else {
					CreatStr += ")";
				}
			}

		}

		// 注释by李磊 先注释掉，等修改的时候再看
		trans.add(new QueryBuilder(CreatStr));
		// creatTMMap.put(CreatStr, "CREATE");

		return true;
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
			String sql = "select codevalue from zdcode where codetype='DRLPath' and parentcode='" + tTransferData.get("Business") + "'";
			DataTable dt = null;

			dt = new QueryBuilder(sql).executeDataTable();
			String Path = dt.getString(0, 0);// 生成java类的路径
			if (Path == null || "".equals(Path)) {
				logger.error("规则文件存储路径错误！");
				this.mErrors.addError("规则文件存储路径错误！");
				return false;
			}
			doc = CreateDocumentByString(viewPara);

			bomMap = AnalyseDoc(doc);
			String conditionContentinit = "";
			String conditionContent = "";
			String resultContent = "";

			for (int i = 0; i < mBomList.size(); i++) {
				String bomName = mBomList.get(i);
				String init = "";
				String conditions = "";
				ArrayList<EachRow> rowsArrayList = bomMap.get(bomName);
				for (int j = 0; j < rowsArrayList.size(); j++) {
					if ("INIT".equals(rowsArrayList.get(j).getPosition())) {
						init = rowsArrayList.get(j).getContent();
					} else if ("CONDITION".equals(rowsArrayList.get(j).getPosition())) {
						conditions += "        eval(" + rowsArrayList.get(j).getContent() + ")\n";
					} else if ("RESULT".equals(rowsArrayList.get(j).getPosition())) {
						resultContent += "        " + rowsArrayList.get(j).getContent() + ";\n";
					}
				}
				conditionContentinit += "        " + init + "\n";
				conditionContent += conditions;
			}
			StringBuffer ruleTemp = new StringBuffer();
			ruleTemp.append("    when\n");
			ruleTemp.append(conditionContentinit);
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

			if (data != null && !"null".equals(data)) {
				JSONObject jsonObj = new JSONObject(data);
				JSONArray tJSONArray = jsonObj.getJSONArray("rows");
				JSONObject temp;

				// 文件头信息
				// @Package

				Iterator tIterator = null;
				String key = null;
				String value = null;
				String tempIf;
				String tempThen;

				for (int i = 0; i < tJSONArray.length(); i++) {
					String rule = ruleTemp.toString();
					temp = tJSONArray.getJSONObject(i);
					tIterator = temp.keys();

					while (tIterator.hasNext()) {
						key = (String) tIterator.next();
						value = temp.getString(key).replaceAll("\"", "");
						if (value == null || "".equals(value))
							value = "null";
						rule = rule.replaceFirst(key, value);

						if (CtbaleMap.containsKey(key)) {
							if ((value.length() - CtbaleMap.get(key)) > 0) {
								CtbaleMap.put(key, value.getBytes().length + 2);
							}
						} else {
							CtbaleMap.put(key, value.getBytes().length + 2);
						}

					}

					out.println("rule \"" + rulID + "_" + i + "\"");
					out.println("dialect \"mvel\"");
					out.println(rule);
					out.println("end");
					out.println("");
				}

				CreatTable();

				for (int i = 0; i < tJSONArray.length(); i++) {

					temp = tJSONArray.getJSONObject(i);
					tIterator = temp.keys();

					String insertStr = "insert into " + this.TableName + " values ( ";

					for (int j = 0; j < crtStr.length; j++) {
						value = temp.getString(crtStr[j]).replaceAll("\"", "");
						String ItemType = (String) mDTColumnTypeMap.get(crtStr[j]);

						if ("Date".equals(ItemType)) {
							value = value.substring(0, 10);
						}

						insertStr += "'" + value + "'";

						if (j != crtStr.length - 1) {
							insertStr += " , ";
						}

					}

					insertStr += " )";
					trans.add(new QueryBuilder(insertStr));
					// creatTMMap.put(insertStr, "INSERT");

				}

			} else {
				out.println("rule \"" + rulID + "\"");
				out.println("dialect \"mvel\"");
				out.println(ruleTemp.toString());
				out.println("end");
				out.println("");
			}

		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("生成源文件失败!!" + e.getMessage(), e);
			mErrors.addError("创建java文件出错，原因为");
		} finally {
			if (out != null)
				out.close();
		}

		return true;
	}

	private Document CreateDocumentByString(String xmlDocument) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(xmlDocument)));
		return document;
	}

	private HashMap<String, ArrayList<EachRow>> AnalyseDoc(Document doc) {
		HashMap<String, ArrayList<EachRow>> bomMap = new HashMap<String, ArrayList<EachRow>>();

		Element root = doc.getDocumentElement();
		NodeList conditionList = root.getElementsByTagName("Condition");
		NodeList resultList = root.getElementsByTagName("Result");

		for (int i = 0; i < conditionList.getLength(); i++) {
			Node condition = conditionList.item(i);
			NodeList metaNodeList = condition.getChildNodes();

			String mainBomName = "";
			String conditionContent = "";
			int paraNum = 0;
			boolean Left_Paren = false;
			boolean Right_Paren = false;
			for (int j = 0; j < metaNodeList.getLength(); j++) {
				Node metaNode = metaNodeList.item(j);
				NamedNodeMap attrMap = metaNode.getAttributes();
				if (attrMap.getNamedItem("id") == null) {
					continue;
				}
				String nodeID = attrMap.getNamedItem("id").getNodeValue();
				String enName = attrMap.getNamedItem("EnName").getNodeValue();

				if ("Link".equals(nodeID)) {
					continue;
				} else if ("BOM".equals(nodeID)) {
					if ("".equals(mainBomName)) {
						mainBomName = enName;
					}
					InitBom(bomMap, enName);
				} else if ("BOMItem".equals(nodeID)) {
					String bomName = attrMap.getNamedItem("BomName").getNodeValue();
					String itemContent = "";
					if (mainBomName.equals(bomName)) {
						itemContent = "$" + bomName + "." + enName;
					} else {
						itemContent = "$" + bomName + "." + enName;
					}
					if (Left_Paren) {
						itemContent = "(" + itemContent;
						Left_Paren = false;
					}
					if (Right_Paren) {
						itemContent = itemContent + ")";
						Right_Paren = false;
					}
					if (paraNum > 0) {
						conditionContent = conditionContent.replace("##", itemContent);
						paraNum--;
					} else {
						conditionContent += itemContent;
					}
				}

				else if ("Operator".equals(nodeID)) {
					String paraNumString = attrMap.getNamedItem("IsNextOperator").getNodeValue();
					if (paraNumString != null) {
						paraNum = Integer.parseInt(paraNumString);
					}
					conditionContent += " " + enName;

				} else if ("Left_Paren".equals(nodeID)) {
					if (paraNum > 0) {
						conditionContent = conditionContent.replace("##", "(##");
					} else {
						conditionContent += "(";
					}
					// Left_Paren = true;
				} else if ("Right_Paren".equals(nodeID)) {
					conditionContent += ")";
					// Right_Paren = true;
				} else if ("Input".equals(nodeID)) {
					String tableValue = attrMap.getNamedItem("TableValue").getNodeValue();
					if (tableValue != null && !"".equals(tableValue))
						enName = tableValue;
					if (enName != null && !"".equals(enName)) {
						if (Left_Paren) {
							enName = "(" + enName;
							Left_Paren = false;
						}
						if (Right_Paren) {
							enName = enName + ")";
							Right_Paren = false;
						}
						if (paraNum > 0) {
							conditionContent = conditionContent.replace("##", enName);
							paraNum--;
						} else {
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
			Node result = resultList.item(i);
			NodeList metaNodeList = result.getChildNodes();

			String mainBomName = "";
			String resultContent = "";
			int paraNum = 0;
			boolean Left_Paren = false;
			boolean Right_Paren = false;
			for (int j = 0; j < metaNodeList.getLength(); j++) {
				Node metaNode = metaNodeList.item(j);
				NamedNodeMap attrMap = metaNode.getAttributes();
				if (attrMap.getNamedItem("id") == null) {
					continue;
				}
				String nodeID = attrMap.getNamedItem("id").getNodeValue();
				String enName = attrMap.getNamedItem("EnName").getNodeValue();

				if ("BOM".equals(nodeID)) {
					if ("".equals(mainBomName)) {
						mainBomName = enName;
					}
					InitBom(bomMap, enName);
				} else if ("BOMItem".equals(nodeID)) {
					String bomName = attrMap.getNamedItem("BomName").getNodeValue();
					String itemContent = "$" + bomName + "." + enName;

					if (Left_Paren) {
						itemContent = "(" + itemContent;
						Left_Paren = false;
					}
					if (Right_Paren) {
						itemContent = ")" + itemContent;
						Right_Paren = false;
					}
					if (paraNum > 0) {
						resultContent = resultContent.replace("##", itemContent);
						paraNum--;
					} else {
						resultContent += itemContent;
					}
				} else if ("Operator".equals(nodeID)) {
					String paraNumString = attrMap.getNamedItem("IsNextOperator").getNodeValue();
					if (paraNumString != null) {
						paraNum = Integer.parseInt(paraNumString);
					}
					if (Right_Paren) {
						enName = ")" + enName;
						Right_Paren = false;
					}
					resultContent += enName;
				} else if ("Left_Paren".equals(nodeID)) {
					Left_Paren = true;
				} else if ("Right_Paren".equals(nodeID)) {
					Right_Paren = true;
					if (j == metaNodeList.getLength() - 1)
						resultContent += enName;
				} else if ("Input".equals(nodeID)) {
					String tableValue = attrMap.getNamedItem("TableValue").getNodeValue();
					if (tableValue != null && !"".equals(tableValue))
						enName = tableValue;
					if (enName != null && !"".equals(enName)) {
						if (Left_Paren) {
							enName = "(" + enName;
							Left_Paren = false;
						}
						if (Right_Paren) {
							enName = enName + ")";
							Right_Paren = false;
						}
						if (paraNum > 0) {
							resultContent = resultContent.replace("##", enName);
							paraNum--;
						} else {
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

		public String position = "";

		public String content = "";

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

	private void InitBom(HashMap<String, ArrayList<EachRow>> bomMap, String bomName) {
		LRBOMSchema tLRBOMSchema = mBomMap.get(bomName);
		if (bomMap.get(bomName) == null) {
			String tFBOM = tLRBOMSchema.getFBOM();
			String tFatherItem = tLRBOMSchema.getFatherItem();
			if (tFBOM != null && !"".equals(tFBOM)) {
				InitBom(bomMap, tFBOM);
				ArrayList<EachRow> rows = new ArrayList<EachRow>();
				EachRow initRow = new EachRow();
				initRow.setPosition("INIT");
				initRow.setContent("$" + bomName + ":" + bomName + "()");
				rows.add(initRow);
				EachRow linkRow = new EachRow();
				linkRow.setPosition("CONDITION");
				linkRow.setContent("$" + tFatherItem + "==" + "$" + tFBOM + "." + tFatherItem);
				rows.add(linkRow);
				bomMap.put(bomName, rows);
				mBomList.add(bomName);
			} else {
				ArrayList<EachRow> rows = new ArrayList<EachRow>();
				EachRow row = new EachRow();
				row.setPosition("INIT");
				row.setContent("$" + bomName + ":" + bomName + "()");
				rows.add(row);
				bomMap.put(bomName, rows);
				mBomList.add(bomName);
			}
		}
	}

	public static final void main(String[] args) {
		RuleMakeBL t = new RuleMakeBL();

		// String
		// xmlDocString="<Rule><Condition><MetaNode id=\"BOM\" EnName=\"BOMAgent\" ChName=\"代理人\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"AgentGrade\" ChName=\"的代理人职级\" MatchType=\"String\" Source=\"select gradecode,gradename from laagentgrade\" isHierarchical=\"0\" BomName=\"BOMAgent\"/><MetaNode id=\"Operator\" EnName=\"== null\" ChName=\"是空\" MatchType=\"String\" ResultType=\"Boolean\" ParaType=\"String\" IsNextOperator=\"0\"/></Condition><Result><MetaNode id=\"BOM\" EnName=\"BOMAgent\" ChName=\"代理人\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"AgentGrade\" ChName=\"的代理人职级\" MatchType=\"String\" Source=\"select gradecode,gradename from laagentgrade\" isHierarchical=\"0\" BomName=\"BOMAgent\"/><MetaNode id=\"Operator\" EnName=\"PubFun.str_equal(#,@)==true\" ChName=\"是\" MatchType=\"String\" ResultType=\"Boolean\" ParaType=\"String\" IsNextOperator=\"2\"/><MetaNode id=\"Input\" EnName=\"\" ChName=\"请输入值\"/></Result></Rule>";
		// String xmlDocString =
		// "<Rule><Condition><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"c1\" ChName=\"的C1\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"&gt;=##\" ChName=\"大于等于\" MatchType=\"Number\" ResultType=\"Boolean\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"0\" ChName=\"0\" TableValue=\"\"/></Condition><Condition><MetaNode id=\"Link\" EnName=\"and\" ChName=\"并且\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"c2\" ChName=\"的C2\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"&gt;=##\" ChName=\"大于等于\" MatchType=\"Number\" ResultType=\"Boolean\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"0\" ChName=\"0\" TableValue=\"\"/></Condition><Result><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"raYBXQM\" ChName=\"银保续期提奖\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"=##\" ChName=\"是\" MatchType=\"Number\" ResultType=\"Over\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Left_Paren\" EnName=\"(\" ChName=\"(\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"raSSBF\" ChName=\"二次银保单实收保费\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"0.008\" ChName=\"0.008\" TableValue=\"\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"c1\" ChName=\"的C1\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Right_Paren\" EnName=\")\" ChName=\")\"/><MetaNode id=\"Operator\" EnName=\"+##\" ChName=\"加\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Left_Paren\" EnName=\"(\" ChName=\"(\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"ra3SSBF\" ChName=\"的三次银保单实收保费\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"0.002\" ChName=\"0.002\" TableValue=\"\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"BOM\" EnName=\"raXQJX\" ChName=\"续期绩效提奖\" FaBom=\"\" FaItem=\"\"/><MetaNode id=\"BOMItem\" EnName=\"c2\" ChName=\"的C2\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"raXQJX\"/><MetaNode id=\"Right_Paren\" EnName=\")\" ChName=\")\"/></Result></Rule>";

		String xmlDocString = "<Rule><Condition><MetaNode id=\"Left_Paren\" EnName=\"(\" ChName=\"(\"/><MetaNode id=\"BOM\" EnName=\"NumberBO\" ChName=\"会员\"/><MetaNode id=\"BOMItem\" EnName=\"deposit\" ChName=\"的预存款\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"NumberBO\"/><MetaNode id=\"Operator\" EnName=\"+##\" ChName=\"加\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"BOM\" EnName=\"NumberBO\" ChName=\"会员\"/><MetaNode id=\"BOMItem\" EnName=\"exp\" ChName=\"的经验\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"NumberBO\"/><MetaNode id=\"Right_Paren\" EnName=\")\" ChName=\")\"/><MetaNode id=\"Operator\" EnName=\"==##\" ChName=\"等于\" MatchType=\"Number\" ResultType=\"Boolean\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"BOM\" EnName=\"NumberBO\" ChName=\"会员\"/><MetaNode id=\"BOMItem\" EnName=\"score\" ChName=\"的开心果\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"NumberBO\"/></Condition><Condition><MetaNode id=\"Link\" EnName=\"and\" ChName=\"并且\"/><MetaNode id=\"Left_Paren\" EnName=\"(\" ChName=\"(\"/><MetaNode id=\"BOM\" EnName=\"NumberBO\" ChName=\"会员\"/><MetaNode id=\"BOMItem\" EnName=\"sum_amount\" ChName=\"的累计消费金额\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"NumberBO\"/><MetaNode id=\"Operator\" EnName=\"*##\" ChName=\"乘以\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"BOM\" EnName=\"NumberBO\" ChName=\"会员\"/><MetaNode id=\"BOMItem\" EnName=\"sum_score\" ChName=\"的累计开心果\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"NumberBO\"/><MetaNode id=\"Right_Paren\" EnName=\")\" ChName=\")\"/><MetaNode id=\"Operator\" EnName=\"==##\" ChName=\"等于\" MatchType=\"Number\" ResultType=\"Boolean\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"Left_Paren\" EnName=\"(\" ChName=\"(\"/><MetaNode id=\"BOM\" EnName=\"NumberBO\" ChName=\"会员\"/><MetaNode id=\"BOMItem\" EnName=\"sum_exp\" ChName=\"的累积经验\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"NumberBO\"/><MetaNode id=\"Operator\" EnName=\"+##\" ChName=\"加\" MatchType=\"Number\" ResultType=\"Number\" ParaType=\"Number\" IsNextOperator=\"1\"/><MetaNode id=\"BOM\" EnName=\"NumberBO\" ChName=\"会员\"/><MetaNode id=\"BOMItem\" EnName=\"score\" ChName=\"的开心果\" MatchType=\"Number\" Source=\"\" isHierarchical=\"0\" BomName=\"NumberBO\"/><MetaNode id=\"Right_Paren\" EnName=\")\" ChName=\")\"/></Condition><Result><MetaNode id=\"BOM\" EnName=\"NumberBO\" ChName=\"会员\"/><MetaNode id=\"BOMItem\" EnName=\"member_rank_id\" ChName=\"的会员等级\" MatchType=\"String\" Source=\"\" isHierarchical=\"0\" BomName=\"NumberBO\"/><MetaNode id=\"Operator\" EnName=\"=&quot;##&quot;\" ChName=\"是\" MatchType=\"String\" ResultType=\"Over\" ParaType=\"String\" IsNextOperator=\"1\"/><MetaNode id=\"Input\" EnName=\"\" ChName=\"请输入值\" TableValue=\"NUMBERBO_MEMBER_RANK_ID1\"/></Result></Rule>";

		try {
			t.CreatDrl(null, "0001", xmlDocString);
			String conditionString = "";
			conditionString.length();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}