/**
 * @CopyRight:Sinosoft
 * @File:QueryBuilder.java
 * @CreateTime:2012-6-20 上午8:45:32
 * @Package:cn.com.sinosoft.entity
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LiuXin
 * 
 */
@Entity
@Table(name = "CITY")
public class City  implements Serializable{

	private static final long serialVersionUID = 6194173138117235790L;
	@Id
	@Column(name = "PLACECODE", length = 20, nullable = true)
	private String placeCode;
	/**
	 * 地区名称
	 */
	@Column(name = "PLACENAME", length = 100)
	private String placeName;
	/**
	 * 上级地区编码
	 */
	@Column(name = "UPPLACECODE", length = 20)
	private String upplaceCode;
	/**
	 * 地区类型
	 */
	@Column(name = "PLACETYPE", length = 2)
	private String placeType;
	/**
	 * 简称
	 */
	@Column(name = "SHOTNAME", length = 40)
	private String shotName;
	/**
	 * 省份简称
	 */
	@Column(name = "PROVINCE", length = 4)
	private String province;

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getUpplaceCode() {
		return upplaceCode;
	}

	public void setUpplaceCode(String upplaceCode) {
		this.upplaceCode = upplaceCode;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getShotName() {
		return shotName;
	}

	public void setShotName(String shotName) {
		this.shotName = shotName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}
