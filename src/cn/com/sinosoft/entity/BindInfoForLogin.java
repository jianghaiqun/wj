package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;



/**
 * 实体类 - 会员绑定信息表
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT112C95F35897FDDCA9E38A76E6DE9B8C
 * ============================================================================
 */

@Entity
@Table(name = "bindInfoForLogin")
public class BindInfoForLogin extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1035095724471649113L;
	private String	comCode	;//	第三方机构编码
	private String	comName	;//	第三方机构名称
	private String	openID	;//	用户唯一标识
	private String	userAccount	;//	用户账号
	private String	userEmail	;//	用户邮箱
	private String	userPhone	;//	用户手机
	private String	userNickName	;//	昵称
	private String  avatar;//头像
	private String	kxbUserEmail	;//	开心宝用户邮箱
	private String	kxbUserPhone	;//	开心宝用户手机
	private String	kxbUserAccount	;//	开心宝用户账号
	private String  registerType;//开心宝注册方式
	
	private Member  mMember;
	
	@OneToOne(mappedBy = "mBindInfoForLogin", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Member getmMember() {
		return mMember;
	}
	public void setmMember(Member mMember) {
		this.mMember = mMember;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getKxbUserEmail() {
		return kxbUserEmail;
	}
	public void setKxbUserEmail(String kxbUserEmail) {
		this.kxbUserEmail = kxbUserEmail;
	}
	public String getKxbUserPhone() {
		return kxbUserPhone;
	}
	public void setKxbUserPhone(String kxbUserPhone) {
		this.kxbUserPhone = kxbUserPhone;
	}
	public String getKxbUserAccount() {
		return kxbUserAccount;
	}
	public void setKxbUserAccount(String kxbUserAccount) {
		this.kxbUserAccount = kxbUserAccount;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
    
	
	
}