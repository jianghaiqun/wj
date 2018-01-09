package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/**
 * 
* @ClassName: InsuranceTestResult 
* @Description: TODO(实体类--保险测试结果) 
* @author zhangjing 
* @date 2014-3-3 
*
 */
@Entity
public class InsuranceTestResult extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6655225564387403857L;
	private String userid;
	private String type;
	private String level;
	private String product_id1;
	private String product_id2;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getProduct_id1() {
		return product_id1;
	}
	public void setProduct_id1(String productId1) {
		product_id1 = productId1;
	}
	public String getProduct_id2() {
		return product_id2;
	}
	public void setProduct_id2(String productId2) {
		product_id2 = productId2;
	}
	
}
