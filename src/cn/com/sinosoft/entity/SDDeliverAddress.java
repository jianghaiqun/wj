/**
 * 
 */
package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * 实体类：邮寄地址信息 
 *
 */
@Entity
@Table(name = "sddeliveraddress")
public class SDDeliverAddress extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7393588814469287894L;

	private String memberId;//所属用户id

	private String name;//姓名

	private String tel;//电话

	private String provinceCode;//地区省
	
	private String provinceName;//地区省名
	
	private String cityCode;//地区市

	private String cityName;//地区市名
	
	private String Section;//地区

	private String detailAddr;//详细地址

	private String zipCode;//邮编

	private String isDefault;//是否默认
	private String modifyUser;//更新者Id
	private String createUser;//创建者Id

	
	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
    }

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
    }

	public String getSection() {
		return Section;
	}

	public void setSection(String section) {
		Section = section;
	}

	public String getDetailAddr() {
		return detailAddr;
	}
	
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
    }

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
    }

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
    }
}
