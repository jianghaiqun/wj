package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LiuXin
 *
 */
@Entity
@Table(name = "DICT")
public class Dict implements Serializable {
	private static final long serialVersionUID = -4575443573887895841L;
	/**
	 * 字典编码
	 */
	@Id
	@Column(name = "DICTSERIAL", length = 30, nullable = true)
	private String dictSerial;
	/**
	 * 字典名称
	 */
	@Column(name = "DICTNAME", length = 200)
	private String dictName;
	/**
	 * 字典级别
	 * 1=1级；2=2级；3=3级
	 */
	@Column(name = "DICTLEVEL", length = 1)
	private String dictLevel;
	/**
	 * 字典类别编码
	 */
	@Column(name = "DICTTYPESERIAL", length = 30)
	private String dictTypeSerial;
	/**
	 * 只针对车型编码
	 *  U001=营业；U002=非营业；-1=营业或非营业。
	 */
	@Column(name = "DICTFLAG", length = 10)
	private String dictFlag;
	/**
	 * 上级字典编码
	 */
	@Column(name = "DICTSUPERCODE", length = 200)
	private String dictSuperCode;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATEDATE", nullable = true)
	private Date createDate;
	/**
	 * 修改时间
	 */
	@Column(name = "MODIFYDATE",insertable = false, nullable = true)
	private Date modifyDate;
	/**
	 * 创建者
	 */
	@Column(name = "CREATEAUTHOR",updatable = false, length = 60)
	private String createAuthor;

	public String getDictSerial() {
		return dictSerial;
	}

	public void setDictSerial(String dictSerial) {
		this.dictSerial = dictSerial;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictLevel() {
		return dictLevel;
	}

	public void setDictLevel(String dictLevel) {
		this.dictLevel = dictLevel;
	}

	public String getDictTypeSerial() {
		return dictTypeSerial;
	}

	public void setDictTypeSerial(String dictTypeSerial) {
		this.dictTypeSerial = dictTypeSerial;
	}

	public String getDictSuperCode() {
		return dictSuperCode;
	}

	public void setDictSuperCode(String dictSuperCode) {
		this.dictSuperCode = dictSuperCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDictFlag() {
		return dictFlag;
	}

	public void setDictFlag(String dictFlag) {
		this.dictFlag = dictFlag;
	}

}