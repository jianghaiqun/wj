/**
 * 
 */
package cn.com.sinosoft.bean;

import java.util.Map;

/**
 * 支付成功页评论展示用
 * @author wangcaiyun
 *
 */
public class CommentInfo {

	// 订单号
	private String orderSn;
	
	// 产品名
	private String productName;
	
	// 投保目的
	private Map<String, String> purpose;
	
	// 评论成功显示样式
	private String disCommented;
	
	// 未评论显示样式
	private String disComment;
	
	// 评论成功赠送积分数
	private String points;
	
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Map<String, String> getPurpose() {
		return purpose;
	}

	public void setPurpose(Map<String, String> purpose) {
		this.purpose = purpose;
	}

	public String getDisCommented() {
		return disCommented;
	}

	public void setDisCommented(String disCommented) {
		this.disCommented = disCommented;
	}

	public String getDisComment() {
		return disComment;
	}

	public void setDisComment(String disComment) {
		this.disComment = disComment;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

}
