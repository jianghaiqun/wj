package cn.com.sinosoft.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 实体类 - 库存分类表
 * ============================================================================
 * 
 * KEY:SINOSOFT2286020E9BD6A508A0492D312FF4D1C5
 * ============================================================================
 */

@Entity
public class Gift extends BaseEntity {
	private static final long serialVersionUID = -3847083016043643799L;

	private Stock stock;// 库存ID

	@Column(nullable = false, length = 500)
	private String cardNo; // 卡号

	@Column(nullable = false, length = 2)
	private String status; // 使用状态

	@Column(nullable = true, length = 200)
	private String address; // 使用的地址

	private Date sendDate; // 使用的日期

	@Column(nullable = true, length = 200)
	private String memberID; // 会员ID
	
	/* zhangjinquan 11180 需求-库存管理修改-增加密码、有效期、说明 2012-10-30 start 001 */
	@Column(nullable = false, length = 50)
	private String password; // 密码
	
	@Column(nullable = false)
	private Date expireDate; // 有效期
	
	@Column(nullable = true)
	private String description; //说明
	/* zhangjinquan 11180 需求-库存管理修改-增加密码、有效期、说明 2012-10-30 end   001 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	/* zhangjinquan 11180 需求-库存管理修改-增加密码、有效期、说明 2012-10-30 start 002 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/* zhangjinquan 11180 需求-库存管理修改-增加密码、有效期、说明 2012-10-30 end   002 */

}