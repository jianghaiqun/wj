package com.sinosoft.jdt;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * 报文生成类，用来生成核保或承保发送报文 
 * @author zhangjinquan 11180
 * @CreateTime 2012-11-06 23:09-2012-11-08 09:45
 * 
 */
public class PacketXmlProducerNew
{
	private static final Logger logger = LoggerFactory.getLogger(PacketXmlProducerNew.class);
	/**
	 * (String, String)的HashMap类型, 存储公共变量。
	 */
	private HashMap<String, String> mapVarValue = new HashMap<String, String>(3);
	/**
	 * String类型, 用于表示xml配置的编码格式
	 */
	private String xmlEncoding = null;
	/**
	 * String类型, 用于表示xml节点配置查询字段的sql语句
	 */
	private String strSqlSelect = "select XMLEncoding,Element,ElementNo,ElementValue,ElementSQL,ElementSQLVar,ForFlag,Attribute,AttributeValue,AttributeNo,AttributeSQL,AttributeSQLVar,Function from tabvxml";
	/**
	 * String类型, 用于表示xml节点的子节点查询sql
	 */
	private String strChildSql = this.strSqlSelect + " where xmlname=? and ManageCom=? and ParentElementNo=? order by OrderNo,ElementNo";
	/**
	 * 查询对象, 用来查询子节点
	 */
	private QueryBuilder qbChild = new QueryBuilder(strChildSql);
	/**
	 * 默认的列表字符串分割标记
	 */
	private String strDefaultSplit = ",";
	/**
	 * 默认的属性名与属性之间的分割标记
	 */
	private String strDefaultKeyValueSplit = "=";
	
	/**
	 * 根据承保核保标记、保险公司编码、订单号生成核保或承保发送报文
	 * @param stFlag 核保承保标记: "01"-核保; "09"-承保;"02"-退保.
	 * @param stManageCom 保险公司编码
	 * @param OrderSn 订单号
	 * @return
	 */
	public String generatePacket(String strFlag, String strManageCom, String strOrderSn, String[] encoding,String strInsuredSn)
	{
		if (StringUtil.isEmpty(strFlag) || StringUtil.isEmpty(strManageCom) || StringUtil.isEmpty(strOrderSn))
		{
			Object[] argArr = {strFlag, strManageCom, strOrderSn};
			logger.error("PacketXmlProducer: 核保承保退保标记、保险公司编码、订单号均不能为空！Flag={}, ManageCom={}, Order={}", argArr);
			return null;
		}
		
		/* 保存输入的公共变量便于后续读取 */
		mapVarValue.put("Flag", strFlag);
		mapVarValue.put("ManageCom", strManageCom);
		mapVarValue.put("OrderNo", strOrderSn);
		mapVarValue.put("InsuredSn", strInsuredSn);
		
		qbChild.getParams().clear();
		qbChild.add(strFlag + ".xml");
		qbChild.add(strManageCom);
		qbChild.add(0);
		
		Document doc = this.getXmlString(encoding);
		if (null == doc)
		{
			return null;
		}
		/** modified by yuzj for 0003112: 史带2071复杂险E-T报文责任信息重复 @20170825 
		if ("2071".equals(strManageCom) && "01".equals(strFlag)) {
			String sql = "select supplierDutyCode, amt from sdinformationduty where ordersn = ? order by id asc ";
			QueryBuilder qb = new QueryBuilder(sql, strOrderSn);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				Element root = doc.getRootElement();
				Element eleDutyinfo = root.element("Request").element("ahsPolicy").element("subjectInfo").element("subjectInfo").element("planInfo").element("planInfo").element("dutyInfo");
				Element eleDutyinfo1 = eleDutyinfo.element("dutyInfo");
				Element dutyFactorInfo = eleDutyinfo1.element("dutyFactorInfo");
				eleDutyinfo1.element("dutyCode").setText(dt.getString(0, 0));
				eleDutyinfo1.element("dutyAount").setText(dt.getString(0, 1));
				for (int i = 1; i < dt.getRowCount(); i++) {
					Element eleDutyinfo2 = eleDutyinfo.addElement("dutyInfo");
					eleDutyinfo2.addElement("dutyName");
					eleDutyinfo2.addElement("dutyCode").setText(dt.getString(i, 0));
					eleDutyinfo2.addElement("totalModalPremium");
					eleDutyinfo2.addElement("dutyAount").setText(dt.getString(i, 1));
					eleDutyinfo2.addElement("DutySpareField1");
					eleDutyinfo2.addElement("DutySpareField2");
					eleDutyinfo2.addElement("DutySpareField3");
					eleDutyinfo2.add((Element)dutyFactorInfo.clone());
				}
			}
		} else */
		if ("2100".equals(strManageCom) && "01".equals(strFlag)) {
			String sql = "select supplierDutyCode, amt from sdinformationduty where ordersn = ? order by id asc ";
			QueryBuilder qb = new QueryBuilder(sql, strOrderSn);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				Element root = doc.getRootElement();
				Element eleDutyinfo = root.element("Request").element("ahsPolicy").element("subjectInfo").element("subjectInfo").element("planInfo").element("planInfo").element("dutyInfo");
				Element eleDutyinfo1 = eleDutyinfo.element("dutyInfo");
				Element dutyFactorInfo = eleDutyinfo1.element("dutyFactorInfo");
				eleDutyinfo1.element("dutyCode").setText(dt.getString(0, 0));
				eleDutyinfo1.element("dutyAount").setText(dt.getString(0, 1));
				for (int i = 1; i < dt.getRowCount(); i++) {
					Element eleDutyinfo2 = eleDutyinfo.addElement("dutyInfo");
					eleDutyinfo2.addElement("dutyName");
					eleDutyinfo2.addElement("dutyCode").setText(dt.getString(i, 0));
					eleDutyinfo2.addElement("totalModalPremium");
					eleDutyinfo2.addElement("dutyAount").setText(dt.getString(i, 1));
					eleDutyinfo2.add((Element)dutyFactorInfo.clone());
				}
			}
		} 
		return this.formatXML(doc, xmlEncoding);
	}
	
	/**
	 * 受益人赋值
	 * 
	 * @param beneficiaryInfo
	 * @param dr
	 */
	private void setBeneficiaryInfo(Element beneficiaryInfo, DataRow dr) {
		// 受益人姓名
		beneficiaryInfo.addElement("professionName").setText(
				dr.getString("bnfName"));
		// 受益人性别
		beneficiaryInfo.addElement("sexCode").setText(dr.getString("bnfSex"));
		// 受益人证件类型
		beneficiaryInfo.addElement("certificateType").setText(
				dr.getString("bnfIDType"));
		// 受益人证件号码
		beneficiaryInfo.addElement("certificateNo").setText(
				dr.getString("bnfIDNo"));
		// 受益人证件有效期起期
		beneficiaryInfo.addElement("certificateEffStartDate").setText(
				dr.getString("bnfStartID"));
		// 受益人证件有效期止期
		beneficiaryInfo.addElement("certificateEffEndDate").setText(
				dr.getString("bnfEndID"));
		// 受益人生日
		beneficiaryInfo.addElement("birthday")
				.setText(dr.getString("bnfBirthday"));
		// 受益人国籍 中国 写死
		beneficiaryInfo.addElement("nationality").setText("37");
		// 受益人与被保人关系
		beneficiaryInfo.addElement("relation").setText(
				dr.getString("relationToInsured"));
		// 受益人比例
		if (StringUtil.isNotEmpty(dr.getString("benePer"))
				&& NumberUtil.isNumber(dr.getString("benePer"))) {
			beneficiaryInfo.addElement("BeneRate").setText(
					(new BigDecimal(dr.getString("benePer")).divide(
							new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP))
							.toString());
		}
		// 受益人顺序
		beneficiaryInfo.addElement("BeneOrder").setText(dr.getString("bnfNo"));
	}
	
	/**
	 * 获取发送报文的字符串格式的xml文档
	 * @param encoding 返回xml的编码格式
	 * @return
	 */
	private Document getXmlString(String[] encoding)
	{
		/* 构造xml根节点数据获取sql，并查询对应配置数据 */
		String sql = this.strSqlSelect + " where xmlname=? and ManageCom=? and levelid=0";
		String strFlag = mapVarValue.get("Flag");
		String strXmlName = strFlag + ".xml";
		String strManageCom = mapVarValue.get("ManageCom");
		DataTable dt = new QueryBuilder(sql, strXmlName, strManageCom).executeDataTable();
		if ((null == dt) || (dt.getRowCount()==0))
		{
			logger.error("PacketXmlProducer: 查找指定xml模板根节点失败！Flag={}, ManageCom={}", strFlag, strManageCom);
			return null;
		}
		else if (dt.getRowCount() > 1)
		{
			logger.error("PacketXmlProducer: 指定xml模板有多个根节点！Flag={}, ManageCom={}", strFlag, strManageCom);
			return null;
		}
		
		DataRow dr = dt.getDataRow(0);
		
		xmlEncoding = dr.getString(0);
		if (StringUtil.isEmpty(xmlEncoding))
		{
			logger.error("PacketXmlProducer: 指定xml模板根节点encoding数据为空！Flag={}, ManageCom={}", strFlag, strManageCom);
			return null;
		}
		if ((null != encoding) && encoding.length > 0)
		{
			encoding[0] = xmlEncoding;
		}
		
		/* 设置xml的根节点 */
		PacketXmlElement peRoot = new PacketXmlElement();
		peRoot.setIsRoot(true);
		this.setCfgValueForElement(peRoot, dr);
		
		/* 根据节点配置信息获取该节点的子节点和数据 */
		if (!this.getChildrenAndDataForPXElement(peRoot))
		{
			return null;
		}
		
		return this.genrateXmlFromPXElement(peRoot);
	}
	
	/**
	 * 根据配置查询结果设置节点数据
	 * @param peEle
	 * @param dr
	 */
	private void setCfgValueForElement(PacketXmlElement peEle, DataRow dr)
	{
		peEle.setName(dr.getString(1));
		peEle.setElementNo(dr.getString(2));
		peEle.setCfgValue(dr.getString(3));
		peEle.setCfgSql(dr.getString(4));
		peEle.setCfgSqlVarStr(dr.getString(5));
		peEle.setCfgForFlag(dr.getString(6));
		peEle.setCfgAttributeStr(dr.getString(7));
		peEle.setCfgAttributeValue(dr.getString(8));
		peEle.setCfgAttributeNoStr(dr.getString(9));
		peEle.setCfgAttributeSql(dr.getString(10));
		peEle.setCfgAttributeSqlVarStr(dr.getString(11));
	}
	
	/** 
     * 格式化XML文档 
     * 
     * @param document xml文档 
     * @param charset 字符串的编码 ，和document的setXMLEncoding必须一致
     * @return 格式化后XML字符串 
     */ 
	private String formatXML(Document document, String charset)
	{
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		try
		{
			xw.write(document);
			xw.flush();
			xw.close();
			return sw.toString();
		}
		catch (IOException e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 从报文xml根节点创建org.dom4j.Document对象
	 * @param pxEle
	 * @return
	 */
	private Document genrateXmlFromPXElement(PacketXmlElement pxEle)
	{
		if (null == pxEle)
		{
			return null;
		}
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(xmlEncoding);
		
		Element root = doc.addElement(pxEle.getName());
		
		if (this.setChildrenForDOCElementFromPXElement(pxEle, root))
		{
			return doc;
		}
		
		return null;
		
	}
	/**
	 * 根据报文xml节点对象和org.dom4j.Element对象配置org.dom4j.Element的子节点数据
	 * @param pxEle
	 * @param ele
	 * @return
	 */
	private boolean setChildrenForDOCElementFromPXElement(PacketXmlElement pxEle, Element ele)
	{
		/* 处理节点属性值 */
		if (pxEle.getAttributesSize()>0)
		{
			HashMap<String, String> attributes = pxEle.getAttributes();
			Set<String> keys = attributes.keySet();
			Iterator<String> iterator = keys.iterator();
			/* 循环设置所有属性值 */
			while (iterator.hasNext())
			{
				String key = iterator.next();
				ele.addAttribute(key, attributes.get(key));
			}
		}
		/* 没有子节点的情况，直接对text赋值 */
		if (!pxEle.getHasChild())
		{
			/* 节点数据不为空才进行设置 */
			if (StringUtil.isNotEmpty(pxEle.getText()))
			{
				ele.setText(pxEle.getText());
			}
			return true;
		}
		
		/* 有子节点的情况循环处理子节点 */
		ArrayList<PacketXmlElement> children = pxEle.getChildren();
		for (int i=0; i<children.size(); i++)
		{
			PacketXmlElement pxChild = children.get(i);
			Element child = ele.addElement(pxChild.getName());
			
			/* 通过递归配置子节点 */
			if (!this.setChildrenForDOCElementFromPXElement(pxChild, child))
			{
				return false;
			}
		}
		
		return true;
	}
	/**
	 * 根据节点配置信息获取该节点的子节点和数据
	 * @param ele
	 * @return
	 */
	private boolean getChildrenAndDataForPXElement(PacketXmlElement ele)
	{
		if (null == ele)
		{
			logger.error("PacketXmlProducer: 获取节点数据时，传入节点为空！Flag={}, ManageCom={}", mapVarValue.get("Flag"), mapVarValue.get("ManageCom"));
			return false;
		}
		
		if (StringUtil.isEmpty(ele.getName()) || StringUtil.isEmpty(ele.getElementNo()))
		{
			Object[] argArr = {mapVarValue.get("Flag"), mapVarValue.get("ManageCom"),
					ele.getName(), ele.getElementNo(), mapVarValue.get("OrderNo")};

			logger.error("PacketXmlProducer: 获取节点数据时，传入节点的名称或编号为空！" +
					"Flag={}, ManageCom={}, name={}, No={}, OrderSn={}", argArr);

			return false;
		}
		
		//从AttributeSQL开始解析本节点的循环数据
		if (StringUtil.isNotEmpty(ele.getCfgAttributeSql()))
		{
			return this.getPrepareDataFromCfgAttributeSql(ele);
		}
		
		return this.getDataForOnePXElement(ele);
	}
	
	/**
	 * 为单个节点配置数据
	 * @param ele
	 * @return
	 */
	private boolean getDataForOnePXElement(PacketXmlElement ele)
	{
		//设置属性数据
		if (!this.setAttributeForPXElement(ele))
		{
			return false;
		}
		
		// 一、有默认值的情况，肯定是叶子节点的情况
		if (StringUtil.isNotEmpty(ele.getCfgValue()))
		{
			ele.setText(ele.getCfgValue());
			ele.setIsLeaf(true);
			return true;
		}
		
		// 二、有配置sql的情况：是包含子节点的节点，查询出来的数据为子节点基础数据
		else if (StringUtil.isNotEmpty(ele.getCfgSql()))
		{
			return this.getDataFromCfgSql(ele);
		}
		
		// 三、没有默认值，也没有配置sql的情况，查看是否有子节点配置，有，则获取，无则继续下一步 
		DataTable childDT = this.getChildDTForPXElement(ele);
		if ((null != childDT) && (childDT.getRowCount()>0))
		{
			if (this.getChildrenByChildDT(childDT, ele))
			{
				return true;
			}
			
			return false;
		}
		
		// 三、其他情况, 从直接父节点开始遍历所有父节点获取本节点数据
		String[] text = new String[1];
		if (ele.getDataFromSelfAndParent(ele.getElementNo(), text))
		{
			ele.setText(text[0]);
			ele.setIsLeaf(true);
			return true;
		}
		
		// zhangjinquan 11180 实在没有配置的情况，按照空数据处理 2012-11-13
		return true;
	}
	
	/**
	 * 为指定的xml节点配置属性数据
	 * @param ele
	 * @return
	 */
	private boolean setAttributeForPXElement(PacketXmlElement ele)
	{
		String strAttributeStr = ele.getCfgAttributeStr();
		
		//一、没有配置属性的情况 ，不做任何处理
		if (StringUtil.isEmpty(strAttributeStr))
		{
			return true;
		}
		
		//属性列表
		String[] strAttributeArr = strAttributeStr.split(this.strDefaultSplit);

		//二、配置了属性默认值的情况
		if (StringUtil.isNotEmpty(ele.getCfgAttributeValue()))
		{
			if (!this.setAttributeValueFromCfgValue(ele, strAttributeArr))
			{
				return false;
			}
			
			if (ele.getAttributesSize() == strAttributeArr.length)
			{
				return true;
			}
		}
		
		int leftNum = strAttributeArr.length - ele.getAttributesSize();
		int getFromParent = 0;
		
		String[] value = new String[1];
		
		//三、没有配置属性默认值或默认值不全的情况
		for (int i=0; i<strAttributeArr.length; i++)
		{
			StringBuffer sbAttrNo = new StringBuffer(ele.getElementNo());
			sbAttrNo.append("a");
			sbAttrNo.append(i);
			if (ele.getDataFromSelfAndParent(sbAttrNo.toString(), value))
			{
				ele.putAttribute(strAttributeArr[i], value[0]);
				getFromParent++;
			}
		}
		
		if ((leftNum < strAttributeArr.length) && (getFromParent < leftNum))
		{
			Object[] argArr = {mapVarValue.get("Flag"), mapVarValue.get("ManageCom"),
					ele.getName(), ele.getElementNo(), strAttributeStr, mapVarValue.get("OrderNo")};

			logger.error("PacketXmlProducer: 设置节点属性时，未获取全！" +
					"Flag={}, ManageCom={}, name={}, No={}, Attribute={}, OrderSn={}", argArr);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 根据数据库中配置的节点属性值设置节点属性值
	 * @param ele
	 * @return
	 */
	private boolean setAttributeValueFromCfgValue(PacketXmlElement ele, String[] strAttributeArr)
	{
		String strArributeValue = ele.getCfgAttributeValue();
		String[] strAttributeValueArr = strArributeValue.split(this.strDefaultSplit);
		/* 每个属性值都配置的情况，按顺序进行配置 */
		if (strAttributeArr.length == strAttributeValueArr.length)
		{
			if (strArributeValue.indexOf(this.strDefaultKeyValueSplit)>-1)
			{
				Object[] argArr = { ele.getName(), ele.getElementNo(),
						mapVarValue.get("Flag"), mapVarValue.get("ManageCom"), strArributeValue};

				logger.error("PacketXmlProducer: 节点属性值配置错误，节点的全部属性都有指定值时，不需要配置属性名！" +
						"name={}, No={}, Flag={}, ManageCom={}, AttributeValue={}", argArr);
				return false;
			}
			
			for (int i=0; i<strAttributeArr.length; i++)
			{
				ele.putAttribute(strAttributeArr[i], strAttributeValueArr[i]);
			}
			return true;
		}
		
		/* 只有部分属性有指定值的情况 */
		for (int i=0; i<strAttributeValueArr.length; i++)
		{
			String[] attrNameValue = strAttributeValueArr[i].split(this.strDefaultKeyValueSplit);
			if (attrNameValue.length != 2)
			{
				Object[] argArr = { ele.getName(), ele.getElementNo(),
						mapVarValue.get("Flag"), mapVarValue.get("ManageCom"), strArributeValue};

				logger.error("PacketXmlProducer: 节点属性值配置错误，只配置节点的部分属性的指定值时，必须配置属性名！" +
						"name={}, No={}, Flag={}, ManageCom={}, AttributeValue={}", argArr);
				return false;
			}
			
			boolean flag = true;
			
			//校验属性名称是否正确
			for (int j=0; j<strAttributeArr.length; j++)
			{
				if (strAttributeArr[j].equals(attrNameValue[0]))
				{
					ele.putAttribute(attrNameValue[0], attrNameValue[1]);
					flag = false;
				}
			}
			
			if (flag)
			{
				Object[] argArr = { ele.getName(), ele.getElementNo(),
						mapVarValue.get("Flag"), mapVarValue.get("ManageCom"), strArributeValue};

				logger.error("PacketXmlProducer: 节点属性值配置错误，只配置节点的部分属性的指定值时，属性名配置有误！" +
						"name={}, No={}, Flag={}, ManageCom={}, AttributeValue={}", argArr);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 从配置的属性值获取sql语句中获取属性值数据
	 * @param ele
	 * @return
	 */
	private boolean getPrepareDataFromCfgAttributeSql(PacketXmlElement ele)
	{
		DataTable dt = this.getDataFromSqlAndVar(ele.getCfgAttributeSql(), ele.getCfgAttributeSqlVarStr(), ele);
		
		if ((null == dt) || (0 == dt.getRowCount()))
		{
			/*
			LogUtil.info("PacketXmlProducer: 通过AttributeSql获取属性数据失败！name=" + ele.getName()
							+ ", No=" + ele.getElementNo() + ", Flag=" + mapVarValue.get("Flag")
							+ ", ManageCom=" + mapVarValue.get("ManageCom")
							+ ", AttributeSql=" + ele.getCfgAttributeSql()
							+ ", AttributeSqlVar=" + ele.getCfgAttributeSqlVarStr());
			*/
			return true;
		}
		
		PacketXmlElement parent = ele.parent;
		if (null == parent)
		{
			Object[] argArr = {mapVarValue.get("Flag"), mapVarValue.get("ManageCom"),
					ele.getName(), ele.getElementNo(), mapVarValue.get("OrderNo")};

			logger.error("PacketXmlProducer: 循环节点的父节点不能为空！" +
					"Flag={}, ManageCom={}, name={}, No={}, OrderSn={}", argArr);
			return false;
		}
		
		PacketXmlElement newEle = ele;
		for (int i=0; i<dt.getRowCount(); i++)
		{
			if (i > 0)
			{
				//创建新的循环节点，并添加到父节点的子节点列表中
				newEle = new PacketXmlElement();
				this.copyPXElementDataToAnther(ele, newEle);
				parent.addChild(newEle);
			}

			for (int j=0; j<dt.getColCount(); j++)
			{
				newEle.putDataKeyValue(dt.getColumnName(j), dt.getString(i, j));
			}
			
			//为新节点配置数据
			if (!this.getDataForOnePXElement(newEle))
			{
				return false;
			}
		}

		return true;
	}
	
	/**
	 * 从配置的sql语句中获取数据
	 * @param ele
	 * @return
	 */
	private boolean getDataFromCfgSql(PacketXmlElement ele)
	{
		DataTable dt = this.getDataFromSqlAndVar(ele.getCfgSql(), ele.getCfgSqlVarStr(), ele);
		
		if ((null == dt) || (0 == dt.getRowCount()))
		{
			DataTable childDT = this.getChildDTForPXElement(ele);
			if ((null != childDT) && (childDT.getRowCount() > 0))
			{
				/*
				LogUtil.error("PacketXmlProducer: 非叶子节点通过ElementSql获取数据失败！name=" + ele.getName()
								+ ", No=" + ele.getElementNo() + ", Flag=" + mapVarValue.get("Flag")
								+ ", ManageCom=" + mapVarValue.get("ManageCom"));
				*/
				
				/* 从子节点配置数据表获取子节点的相应数据 */
				return this.getChildrenByChildDT(childDT, ele);
			}
			
			ele.setIsLeaf(true);

			Object[] argArr = { ele.getName(), ele.getElementNo(),
					mapVarValue.get("Flag"), mapVarValue.get("ManageCom")};
			logger.info("PacketXmlProducer: 叶子节点不需要配置ElementSql，" +
					"顶多只需要配置AttributeSql！name={}, No={}, Flag={}, ManageCom={}", argArr);
			return true;
		}
		
		//配置子节点数据
		return this.getChildrenDataFromDT(dt, ele);
	}
	
	/**
	 * 根据指定节点获取子节点列表
	 * @param ele
	 * @return
	 */
	private DataTable getChildDTForPXElement(PacketXmlElement ele)
	{
		/* 修改父节点编号的绑定条件 */
		qbChild.set(2, Integer.parseInt(ele.getElementNo()));
		return qbChild.executeDataTable();
	}
	
	/**
	 * 根据配置sql获取数据
	 * @param sql
	 * @param sqlVar
	 * @param ele
	 * @return
	 */
	private DataTable getDataFromSqlAndVar(String sql, String sqlVar, PacketXmlElement ele)
	{
		/* 创建查询对象 */
		QueryBuilder qb = new QueryBuilder(sql);
		
		/* 如果变量列表不为空，则进行配置 */
		if (StringUtil.isNotEmpty(sqlVar))
		{
			// 支持团单的保险公司在zdconfig 中配置 
			if(Config.getValue("groupTypes").indexOf(mapVarValue.get("ManageCom")) != -1){
				String sqlGroupType = "SELECT a.ordersn,b.GroupType FROM sdorders a LEFT JOIN sdorderitemoth b ON a.ordersn=b.ordersn WHERE a.ordersn=? limit 1";
				QueryBuilder queryBuilder = new QueryBuilder(sqlGroupType);
				queryBuilder.add(mapVarValue.get("OrderNo"));
				DataTable dt = queryBuilder.executeDataTable();
				if(dt != null && dt.getRowCount() > 0){
					String element = ele.getName();
					if(sqlVar.indexOf(";")>0){
						// 团单执行第2条sql  个单执行第1条sql
						if(StringUtil.isNotEmpty(dt.getString(0, 1))&&"g".equals(dt.getString(0, 1))){
							sqlVar = sqlVar.split(";")[1];
							sql = sql.split(";")[1];
						}else{
							sqlVar = sqlVar.split(";")[0];
							sql = sql.split(";")[0];
						}
					}
				}
				/* 创建查询对象 */
				qb = new QueryBuilder(sql);
			}
			
			ArrayList<String> varValueList = new ArrayList<String>();
			String[] varList = sqlVar.split(this.strDefaultSplit);
			/* 获取变量列表对应的变量值 */
			if (!this.getVarValue(varList, ele, varValueList))
			{
				return null;
			}
			/* 配置绑定变量 */
			for (int i=0; i<varValueList.size(); i++)
			{
				qb.add(varValueList.get(i));
			}
		}
		
		/* 执行查询 */
		return qb.executeDataTable();
	}
	/**
	 * 从子节点配置数据表获取子节点的相应数据
	 * @param childDT 子节点数据列表
	 * @param ele 父节点
	 * @return
	 */
	private boolean getChildrenByChildDT(DataTable childDT, PacketXmlElement ele)
	{		
		for (int k=0; k<childDT.getRowCount(); k++)
		{
			DataRow dr = childDT.get(k);
			PacketXmlElement child = new PacketXmlElement();
			this.setCfgValueForElement(child, dr);
			child.parent = ele;
			ele.addChild(child);
			
			/* 如果获取失败，则返回 */
			if (!this.getChildrenAndDataForPXElement(child))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 对于配置sql的情况，sql查询结果数据肯定是子节点数据，说明本节点肯定有子节点 ，
	 * 本函数实现对这种节点数据的获取
	 * @param dt
	 * @param ele
	 * @return
	 */
	private boolean getChildrenDataFromDT(DataTable dt, PacketXmlElement ele)
	{
		DataTable childDT = this.getChildDTForPXElement(ele);
		if ((null == childDT) || (0 == childDT.getRowCount()))
		{
			if (1 == dt.getColCount())
			{
				/* 本判断只用于旧数据的兼容处理，同一公司同一流程的xml配置中只要任何一条记录包含AttributeSql配置
		        	就不应该进入本判断里面处理 */
				return this.getSelfDataFromDT(dt, ele);
			}

			Object[] argArr = {mapVarValue.get("Flag"), mapVarValue.get("ManageCom"),
					ele.getName(), ele.getElementNo(), mapVarValue.get("OrderNo")};

			logger.error("PacketXmlProducer: 查找子节点失败！" +
					"Flag={}, ManageCom={}, name={}, No={}, OrderSn={}", argArr);
			return false;
		}
		
		/* 获得列名并保存下来 */
		String[] strColumnNameArr = new String[dt.getColCount()];
		for (int j=0; j<dt.getColCount(); j++)
		{
			strColumnNameArr[j] = dt.getColumnName(j);
		}
		
		/* 遍历配置sql查询结果数据集 */
		for (int i=0; i<dt.getRowCount(); i++)
		{
			/* 针对每行数据配置到节点数据map中，新行重新put数据时会覆盖同名数据 */
			for (int j=0; j<dt.getColCount(); j++)
			{
				ele.putDataKeyValue(strColumnNameArr[j], dt.getString(i, j));
			}
			
			/* 从子节点配置数据表获取子节点的相应数据 */
			if (!this.getChildrenByChildDT(childDT, ele))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 根据配置sql查询结果进行本节点数据配置（本节点为单叶子节点或者循环叶子节点）
	 * 本方法只用于旧数据的兼容处理，同一公司同一流程的xml配置中只要任何一条记录包含AttributeSql配置，就不应该出现本方法的调用
	 * @param dt
	 * @param ele
	 * @return
	 */
	private boolean getSelfDataFromDT(DataTable dt, PacketXmlElement ele)
	{
		/* 如果查询结果大于1行，则判断循环标记是否为"Y" */
		if (dt.getRowCount() > 1)
		{
			if (!"Y".equalsIgnoreCase(ele.getCfgForFlag()))
			{
				Object[] argArr = {mapVarValue.get("Flag"), mapVarValue.get("ManageCom"),
						ele.getName(), ele.getElementNo(), mapVarValue.get("OrderNo")};

				logger.error("PacketProducer: 非循环节点数据多余一条" +
						"Flag={}, ManageCom={}, name={}, No={}, OrderSn={}", argArr);
				return false;
			}
			
			/* 循环节点不能是根节点 */
			if (null == ele.parent)
			{
				Object[] argArr = {mapVarValue.get("Flag"), mapVarValue.get("ManageCom"),
						ele.getName(), ele.getElementNo(), mapVarValue.get("OrderNo")};

				logger.error("PacketProducer: 循环节点不能是根节点！" +
						"Flag={}, ManageCom={}, name={}, No={}, OrderSn={}", argArr);

				return false;
			}
			
			/* 设置第一个节点的数据 */
			ele.setText(dt.getString(0, 0));
			
			/* 为父节点增加循环的本节点并配置其数据 */
			for (int i=1; i<dt.getRowCount(); i++)
			{
				PacketXmlElement child = new PacketXmlElement();
				child.setName(ele.getName());
				child.setText(dt.getString(i, 0));
				ele.parent.addChild(child);
			}
			return true;
		}
		
		ele.setText(dt.getString(0, 0));
		return true;
	}
	
	/**
	 * 从一个xml节点对象拷贝指定数据到另一个xml节点对象
	 * @param srcEle
	 * @param desEle
	 */
	private void copyPXElementDataToAnther(PacketXmlElement srcEle, PacketXmlElement desEle)
	{
		desEle.setName(srcEle.getName());
		desEle.setElementNo(srcEle.getElementNo());
		desEle.setCfgValue(srcEle.getCfgValue());
		desEle.setCfgSql(srcEle.getCfgSql());
		desEle.setCfgSqlVarStr(srcEle.getCfgSqlVarStr());
		desEle.setCfgForFlag(srcEle.getCfgForFlag());
		desEle.setCfgAttributeStr(srcEle.getCfgAttributeStr());
		desEle.setCfgAttributeValue(srcEle.getCfgAttributeValue());
		desEle.setCfgAttributeNoStr(srcEle.getCfgAttributeNoStr());
	}
	
	/**
	 * 获取配置sql的变量列表的值
	 * @param varList
	 * @param ele
	 * @param varValueList
	 * @return
	 */
	private boolean getVarValue(String[] varList, PacketXmlElement ele, ArrayList<String> varValueList)
	{
		String[] text = new String[1];
		for (int i=0; i<varList.length; i++)
		{
			/* 一、从公共变量列表中获取对应值 */
			String value = mapVarValue.get(varList[i]);
			if (StringUtil.isNotEmpty(value))
			{
				varValueList.add(value);
				continue;
			}
			/* 二、从父节点数据中取值 */
			if (ele.getDataFromSelfAndParent(varList[i], text))
			{
				varValueList.add(text[0]);
				continue;
			}
			/* 三、取值失败，返回false */
			Object[] argArr = {mapVarValue.get("Flag"), mapVarValue.get("ManageCom"),
					ele.getName(), ele.getElementNo(), mapVarValue.get("OrderNo")};

			logger.error("PacketXmlProducer: 获取变量列表对应值失败！" +
					"Flag={}, ManageCom={}, name={}, No={}, OrderSn={}", argArr);
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		String[] encoding = {"UTF-8"};
		String strFlag = "01";
		String strManageCom = "1065";
		String strOrderSn = "2013000000014036";
		String strInsuredSn = "2013000000014036_1";
		/*
		com.sinosoft.cms.dataservice.XmlDataUWBL a = new com.sinosoft.cms.dataservice.XmlDataUWBL();
		PacketXmlProducer b = new PacketXmlProducer();
		long t0 = System.currentTimeMillis();
		for (int i=0; i<10; i++)
		{
			//a.dealData(strFlag, strManageCom, strOrderSn, encoding);
			new PacketXmlProducer().generatePacket(strFlag, strManageCom, strOrderSn, encoding);
		}
		long t1 = System.currentTimeMillis();
		for (int i=0; i<10; i++)
		{
			//b.generatePacket(strFlag, strManageCom, strOrderSn, encoding);
			new com.sinosoft.cms.dataservice.XmlDataUWBL().dealData(strFlag, strManageCom, strOrderSn, encoding);
		}
		long t2 = System.currentTimeMillis();
		System.out.println((t2-t1)+"\r\n"+(t1-t0));//391 23672
		
		*/
		String xml = null;
		xml = new PacketXmlProducerNew().generatePacket(strFlag, strManageCom, strOrderSn, encoding,strInsuredSn);
		//xml = new com.sinosoft.cms.dataservice.XmlDataUWBL().dealData(strFlag, strManageCom, strOrderSn, encoding);
		System.out.println(xml);
		
		
	}

}
