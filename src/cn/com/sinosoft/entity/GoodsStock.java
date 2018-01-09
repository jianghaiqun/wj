/**
 * 
 */
package cn.com.sinosoft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "GoodsStock")
public class GoodsStock extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8388671114940717632L;

	private String giftID;

	private String goodsName;

	private String cardNo;

	private String passWord;

	private String payPoints;

	private String memberid;

	private Date outDate;

	private String status;

	private String type;

	private String insuredId;

	private String memo;

	private String version;
	
	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String prop5;

	private String prop6;

	private String prop7;

	public String getGiftID() {
		return giftID;
	}

	public void setGiftID(String giftID) {
		this.giftID = giftID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPayPoints() {
		return payPoints;
	}

	public void setPayPoints(String payPoints) {
		this.payPoints = payPoints;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInsuredId() {
		return insuredId;
	}

	public void setInsuredId(String insuredId) {
		this.insuredId = insuredId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	public String getProp3() {
		return prop3;
	}

	public void setProp3(String prop3) {
		this.prop3 = prop3;
	}

	public String getProp4() {
		return prop4;
	}

	public void setProp4(String prop4) {
		this.prop4 = prop4;
	}

	public String getProp5() {
		return prop5;
	}

	public void setProp5(String prop5) {
		this.prop5 = prop5;
	}

	public String getProp6() {
		return prop6;
	}

	public void setProp6(String prop6) {
		this.prop6 = prop6;
	}

	public String getProp7() {
		return prop7;
	}

	public void setProp7(String prop7) {
		this.prop7 = prop7;
	}
}
