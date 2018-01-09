package com.sinosoft.jdt;

import com.sinosoft.framework.utility.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * 报文节点类，用来存储核保或承保发送的xml报文中的节点数据
 * @author zhangjinquan 11180
 * @CreateTime 2012-11-06 22:09-2012-11-07 23:09
 * 
 */
public class PacketXmlElement
{
	private static final Logger logger = LoggerFactory.getLogger(PacketXmlElement.class);
	/**
	 * 布尔型, 是否根节点标记，true-是, false-否
	 */
	private boolean isRoot = false;
	/**
	 * 布尔型, 是否叶子节点标记，true-是, false-否
	 */
	private boolean isLeaf = false;
	/**
	 * 字符串型, 表示xml节点的名称
	 */
	private String name = null;
	/**
	 * 字符串格式, 表示xml节点的节点编号
	 */
	private String elementNo = null;
	/**
	 * 字符串型, 表示xml节点的数据
	 */
	private String text = null;
	/**
	 * 字符串类型, 表示数据库中配置的默认值
	 */
	private String cfgValue = null;
	/**
	 * 字符串类型, 表示数据库中配置的取数sql语句
	 */
	private String cfgSql = null;
	/**
	 * 字符串类型, 表示数据库中配置的sql变量名列表字符串, 半角逗号隔开
	 */
	private String cfgSqlVarStr = null;
	/**
	 * 字符串类型, 表示数据库中配置的本节点循环标记: 'Y'-表示循环节点, 其他表示非循环节点
	 */
	private String cfgForFlag = null;
	/**
	 * 字符串类型, 表示数据库中配置的本节点属性获取sql
	 */
	private String cfgAttributeSql = null;
	/**
	 * 字符串类型, 表示数据库中配置的本节点属性获取sql变量列表, 半角逗号隔开
	 */
	private String cfgAttributeSqlVarStr = null;
	/**
	 * 字符串类型, 表示数据库中配置的本节点属性值列表, 半角逗号隔开
	 */
	private String cfgAttributeValue = null;
	/**
	 * 字符串类型, 表示数据库中配置的本节点属性名列表, 半角逗号隔开
	 */
	private String cfgAttributeStr = null;
	/**
	 * 字符串类型, 表示数据库中配置的本节点属性值编号列表, 半角逗号隔开
	 */
	private String cfgAttributeNoStr = null;
	/**
	 * PacketElement, 父节点
	 */
	public PacketXmlElement parent = null;
	/**
	 * (String, String)的HashMap, 存放节点数据, 用于子节点文本、sql变量值、子节点属性数据或子节点属性sql变量数据获取
	 */
	private HashMap<String, String> dataMap = new HashMap<String, String>();
	/**
	 * ArrayList类型, 子节点列表
	 */
	private ArrayList<PacketXmlElement> children = new ArrayList<PacketXmlElement>();
	/**
	 * (String, String)的HashMap, 用于存放该节点的属性列表, key为属性名，value为属性值，
	 */
	private HashMap<String, String> attributes = new HashMap<String, String>();
	
	/**
	 * 设置是否根节点标记
	 * @param isRoot
	 */
	public void setIsRoot(boolean isRoot)
	{
		this.isRoot = isRoot;
	}
	
	/**
	 * 获取是否根节点标记
	 * @return
	 */
	public boolean getIsRoot()
	{
		return this.isRoot;
	}
	
	/**
	 * 设置是否叶子节点标记
	 * @param isRoot
	 */
	public void setIsLeaf(boolean isLeaf)
	{
		this.isLeaf = isLeaf;
	}
	
	/**
	 * 获取是否叶子节点标记
	 * @return
	 */
	public boolean getIsLeaf()
	{
		return this.isLeaf;
	}
	
	/**
	 * 获取是否包含数据标记
	 * @return
	 */
	public boolean getHasData()
	{
		return !this.dataMap.isEmpty();
	}
	
	/**
	 * 设置节点名称
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * 获取节点名称
	 * @return
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * 设置节点编号
	 * @param name
	 */
	public void setElementNo(String elementNo)
	{
		this.elementNo = elementNo;
	}
	
	/**
	 * 获取节点编号
	 * @return
	 */
	public String getElementNo()
	{
		return this.elementNo;
	}
	
	/**
	 * 根据key值获取本节点中存储的对应数据
	 * @param key
	 * @return
	 */
	public String getDataByKey(String key)
	{
		if (this.dataMap.isEmpty())
		{
			return null;
		}
		return this.dataMap.get(key);
	}
	
	/**
	 * 判断本节点是否包含指定key值的数据
	 * @param key
	 * @return
	 */
	public boolean containsDataKey(String key)
	{
		if (this.dataMap.isEmpty())
		{
			return false;
		}
		return this.dataMap.containsKey(key);
	}
	
	/**
	 * 将指定键值对存放到本节点数据中
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putDataKeyValue(String key, String value)
	{
		if ((null == key) || (null == value))
		{
			logger.warn("PacketElement：报文节点数据的key和value均不能为空！");
			return false;
		}
		/*
		if (this.dataMap.containsKey(key))
		{
			LogUtil.error("PacketElement：报文节点数据的key值不能重复！");
			return false;
		}
		*/
		this.dataMap.put(key, value);
		return true;
	}
	
	/**
	 * 根据key值获取本节点属性列表中指定属性值
	 * @param key
	 * @return
	 */
	public String getAttributeValue(String key)
	{
		if (this.attributes.isEmpty())
		{
			return null;
		}
		return this.attributes.get(key);
	}
	
	/**
	 * 判断本节点属性列表是否包含指定名称的属性
	 * @param key
	 * @return
	 */
	public boolean containsAttribute(String key)
	{
		if (this.attributes.isEmpty())
		{
			return false;
		}
		return this.attributes.containsKey(key);
	}
	
	/**
	 * 将指定属性键值对存放到本节点属性列表中
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putAttribute(String key, String value)
	{
		if ((null == key) || (null == value))
		{
			logger.warn("PacketElement：报文节点属性列表成员的key和value均不能为空！");
			return false;
		}
		if (this.attributes.containsKey(key))
		{
			logger.warn("PacketElement：报文节点属性列表成员不能重复！");
			return false;
		}
		this.attributes.put(key, value);
		return true;
	}
	
	/**
	 * 获取属性列表中已存在属性的个数
	 * @return
	 */
	public int getAttributesSize()
	{
		return this.attributes.size();
	}
	
	/**
	 * 获取本节点的属性列表
	 * @return
	 */
	public HashMap<String, String> getAttributes()
	{
		return this.attributes;
	}
	
	/**
	 * 获取是否拥有子节点
	 * @return
	 */
	public boolean getHasChild()
	{
		return !this.children.isEmpty();
	}
	/**
	 * 增加指定的节点作为本节点的一个子节点
	 * @param child
	 * @return
	 */
	public boolean addChild(PacketXmlElement child)
	{
		if (null == child)
		{
			logger.warn("PacketElement: 子节点不能为空！");
			return false;
		}
		if (child == this)
		{
			logger.warn("PacketElement: 子节点不能为自身！");
			return false;
		}
		if (this.children.contains(child))
		{
			logger.warn("PacketElement: 不能重复添加同一个子节点！");
			return false;
		}
		this.children.add(child);
		return true;
	}
	/**
	 * 返回子节点列表
	 * @return
	 */
	public ArrayList<PacketXmlElement> getChildren()
	{
		return this.children;
	}
	
	/**
	 * 设置本节点的文本数据
	 * @param text
	 * @return
	 */
	public boolean setText(String text)
	{
		if (!this.children.isEmpty())
		{
			logger.warn("PacketElement: 拥有子节点的节点不能设置文本内容！");
			return false;
		}
		this.text = text;
		return true;
	}
	/**
	 * 返回本节点的文本数据
	 * @return
	 */
	public String getText()
	{
		return this.text;
	}
	
	/**
	 * 设置数据库配置的默认值
	 * @param name
	 */
	public void setCfgValue(String cfgValue)
	{
		this.cfgValue = cfgValue;
	}
	
	/**
	 * 获取数据库配置的默认值
	 * @return
	 */
	public String getCfgValue()
	{
		return this.cfgValue;
	}
	
	/**
	 * 设置数据库配置的数据获取sql
	 * @param name
	 */
	public void setCfgSql(String cfgSql)
	{
		this.cfgSql = cfgSql;
	}
	
	/**
	 * 获取数据库配置的数据获取sql
	 * @return
	 */
	public String getCfgSql()
	{
		return this.cfgSql;
	}
	
	/**
	 * 设置数据库配置的sql变量名列表字符串
	 * @param name
	 */
	public void setCfgSqlVarStr(String cfgSqlVarStr)
	{
		this.cfgSqlVarStr = cfgSqlVarStr;
	}
	
	/**
	 * 获取数据库配置的sql变量名列表字符串
	 * @return
	 */
	public String getCfgSqlVarStr()
	{
		return this.cfgSqlVarStr;
	}
	
	/**
	 * 设置数据库配置的循环标记
	 * @param name
	 */
	public void setCfgForFlag(String cfgForFlag)
	{
		this.cfgForFlag = cfgForFlag;
	}
	
	/**
	 * 获取数据库配置的循环标记
	 * @return
	 */
	public String getCfgForFlag()
	{
		return this.cfgForFlag;
	}
	
	/**
	 * 设置数据库配置的属性列表值获取sql
	 * @param name
	 */
	public void setCfgAttributeSql(String cfgAttributeSql)
	{
		this.cfgAttributeSql = cfgAttributeSql;
	}
	
	/**
	 * 获取数据库配置的属性列表值获取sql
	 * @return
	 */
	public String getCfgAttributeSql()
	{
		return this.cfgAttributeSql;
	}
	
	/**
	 * 设置数据库配置的属性列表值获取sql变量列表
	 * @param name
	 */
	public void setCfgAttributeSqlVarStr(String cfgAttributeSqlVarStr)
	{
		this.cfgAttributeSqlVarStr = cfgAttributeSqlVarStr;
	}
	
	/**
	 * 获取数据库配置的属性列表值获取sql的变量列表
	 * @return
	 */
	public String getCfgAttributeSqlVarStr()
	{
		return this.cfgAttributeSqlVarStr;
	}

	/**
	 * 设置数据库配置的属性列表默认值列表
	 * @param name
	 */
	public void setCfgAttributeValue(String cfgAttributeValue)
	{
		this.cfgAttributeValue = cfgAttributeValue;
	}
	
	/**
	 * 获取数据库配置的属性列表默认值列表
	 * @return
	 */
	public String getCfgAttributeValue()
	{
		return this.cfgAttributeValue;
	}
	
	/**
	 * 设置数据库配置的属性列表名称列表字符串
	 * @param name
	 */
	public void setCfgAttributeStr(String cfgAttributeStr)
	{
		this.cfgAttributeStr = cfgAttributeStr;
	}
	
	/**
	 * 获取数据库配置的属性列表名称列表字符串
	 * @return
	 */
	public String getCfgAttributeStr()
	{
		return this.cfgAttributeStr;
	}
	
	/**
	 * 设置数据库配置的属性列表默认值列表
	 * @param name
	 */
	public void setCfgAttributeNoStr(String cfgAttributeNoStr)
	{
		this.cfgAttributeNoStr = cfgAttributeNoStr;
	}
	
	/**
	 * 获取数据库配置的属性列表默认值列表
	 * @return
	 */
	public String getCfgAttributeNoStr()
	{
		return this.cfgAttributeNoStr;
	}
	
	/**
	 * 从直接本节点自身开始开始遍历所有父节点获取本节点数据
	 * @param ele
	 * @return
	 */
	public boolean getDataFromSelfAndParent(String key, String[] returnArr)
	{
		PacketXmlElement parent = this;
		while (null != parent)
		{
			// 一旦获取到数据，则停止遍历父节点
			if (parent.containsDataKey(key))
			{
				returnArr[0] = parent.getDataByKey(key);
				return true;
			}
			
			/* 查找上一级父节点 */
			parent = parent.parent;
		}
		
		return false;
	}
}
