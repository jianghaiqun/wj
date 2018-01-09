package cn.com.sinosoft.entity;

import java.util.ArrayList;
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
 * 实体类 - 特殊字典表
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

@Entity
public class Code extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4299417822520613363L;
	
	private String paramENDescription;//参数英文代码
	private String paramZHDescription;//参数中文名称
	private String paramValue;//参数值
	public Code() {
		super();
	}
	public String getParamENDescription() {
		return paramENDescription;
	}
	public void setParamENDescription(String paramENDescription) {
		this.paramENDescription = paramENDescription;
	}
	public String getParamZHDescription() {
		return paramZHDescription;
	}
	public void setParamZHDescription(String paramZHDescription) {
		this.paramZHDescription = paramZHDescription;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}