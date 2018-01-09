package cn.com.sinosoft.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.DefinitionList;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

import cn.com.sinosoft.entity.MemberAttribute.AttributeType;
import cn.com.sinosoft.util.CommonUtil;

/**
 * zdcode
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT5CAA6FDAF2A5662FADB5F15AD00B2070
 * ============================================================================
 */

public class ZDCode extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8294246252064758033L;

	private String CodeType;

	private String ParentCode;

	private String CodeValue;

	private String CodeName;

	private Long CodeOrder;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public String getCodeType() {
		return CodeType;
	}

	public void setCodeType(String codeType) {
		CodeType = codeType;
	}

	public String getParentCode() {
		return ParentCode;
	}

	public void setParentCode(String parentCode) {
		ParentCode = parentCode;
	}

	public String getCodeValue() {
		return CodeValue;
	}

	public void setCodeValue(String codeValue) {
		CodeValue = codeValue;
	}

	public String getCodeName() {
		return CodeName;
	}

	public void setCodeName(String codeName) {
		CodeName = codeName;
	}

	public Long getCodeOrder() {
		return CodeOrder;
	}

	public void setCodeOrder(Long codeOrder) {
		CodeOrder = codeOrder;
	}

	public String getProp1() {
		return Prop1;
	}

	public void setProp1(String prop1) {
		Prop1 = prop1;
	}

	public String getProp2() {
		return Prop2;
	}

	public void setProp2(String prop2) {
		Prop2 = prop2;
	}

	public String getProp3() {
		return Prop3;
	}

	public void setProp3(String prop3) {
		Prop3 = prop3;
	}

	public String getProp4() {
		return Prop4;
	}

	public void setProp4(String prop4) {
		Prop4 = prop4;
	}

	public String getMemo() {
		return Memo;
	}

	public void setMemo(String memo) {
		Memo = memo;
	}

	public Date getAddTime() {
		return AddTime;
	}

	public void setAddTime(Date addTime) {
		AddTime = addTime;
	}

	public String getAddUser() {
		return AddUser;
	}

	public void setAddUser(String addUser) {
		AddUser = addUser;
	}

	public Date getModifyTime() {
		return ModifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		ModifyTime = modifyTime;
	}

	public String getModifyUser() {
		return ModifyUser;
	}

	public void setModifyUser(String modifyUser) {
		ModifyUser = modifyUser;
	}
	
	
}