package cn.com.sinosoft.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OrderBy;

/**
 * 实体类 - 库存分类表
 * ============================================================================
 * 
 * KEY:SINOSOFT2286020E9BD6A508A0492D312FF4D1C5
 * ============================================================================
 */

@Entity
public class Stock extends BaseEntity {
	private static final long serialVersionUID = -3847083016043643799L;
	@Column(nullable = false, length = 500)
	private String name;// 分类名称

	@Column(nullable = true)
	private Integer residue; // 库存剩余量

	@Column(nullable = true)
	private Integer betake; // 已经使用的库存

	private Set<Present> presentSet;// 礼品

	private List<Gift> giftList;

	@OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
	@OrderBy(clause = "id asc")
	public List<Gift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<Gift> giftList) {
		this.giftList = giftList;
	}

	@OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
	public Set<Present> getPresentSet() {
		return presentSet;
	}

	public void setPresentSet(Set<Present> presentSet) {
		this.presentSet = presentSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getResidue() {
		return residue;
	}

	public void setResidue(Integer residue) {
		this.residue = residue;
	}

	public Integer getBetake() {
		return betake;
	}

	public void setBetake(Integer betake) {
		this.betake = betake;
	}

}