package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class MemberHobby extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4933620964157567575L;
	private Member member;
	private String codeValue;
    private String isSelected;
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

	public String getIsSelected() {
		return isSelected;
	}

}
