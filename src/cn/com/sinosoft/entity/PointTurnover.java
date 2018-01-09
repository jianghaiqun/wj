package cn.com.sinosoft.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


/**
 * 实体类 - 积分流水
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTA422025AB3BAEE5940EB4488D12B6691
 * ============================================================================
 */

@Entity
public class PointTurnover extends BaseEntity {

	private static final long serialVersionUID = 4858058186018438872L;
	
	// 积分状态（有效,到期,冻结）
		public enum PointStatus {
			effective,maturity, freeze
		};
		
	private String pointSource;// 积分来源/使用
	private Integer pointTurnover;// 积分收入支出
	private String remarks;// 备注
	private PointStatus pointStatus;// 积分状态
	private Date MaturityDate;// 积分到期时间
	private Member member;// 会员
	/**
	 * @return the pointSource
	 */
	public String getPointSource() {
		return pointSource;
	}

	/**
	 * @param pointSource the pointSource to set
	 */
	public void setPointSource(String pointSource) {
		this.pointSource = pointSource;
	}

	/**
	 * @return the pointTurnover
	 */
	public Integer getPointTurnover() {
		return pointTurnover;
	}

	/**
	 * @param pointTurnover the pointTurnover to set
	 */
	public void setPointTurnover(Integer pointTurnover) {
		this.pointTurnover = pointTurnover;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the pointStatus
	 */
	@Enumerated
	@Column(nullable = false)
	public PointStatus getPointStatus() {
		return pointStatus;
	}

	/**
	 * @param pointStatus the pointStatus to set
	 */
	public void setPointStatus(PointStatus pointStatus) {
		this.pointStatus = pointStatus;
	}

	/**
	 * @return the maturityDate
	 */
	public Date getMaturityDate() {
		return MaturityDate;
	}

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(Date maturityDate) {
		MaturityDate = maturityDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}