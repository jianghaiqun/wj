package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class TradeInformation extends BaseEntity{
 
	/**交易信息表
	 *  wugq
	 */
	private static final long serialVersionUID = -8869366809169175600L;
	
	
	private String ordID;//订单号
	private String ordAmt;//订单金额
	private String tradeResult;//交易结果
	private String tradeSeriNO;//支付平台返回订单号针对支付
	private String tradeBank;//支付付款银行
	private String merId;//商户号
	private String  payStatus;//订单状态  0  支付发送   1  支付返回   2退款发送  3 退款返回 
	private String  sendSign;//支付发送签名
	private String returnSign;//支付返回签名
	private String sendSign2;//退款发送签名
	private String returnSign2;//退款返回签名
	private  String errorMsg;//支付错误信息
	private String errorMsg2;// 退款错误信息
	private String receiveRefundId;//支付平台返回退款单号
	private String  sendDate;// 支付发送时间 
	private String  sendDate2;// 退款发送时间 
	private String receiveDate;//支付接受时间
	private String receiveDate2;//退款接受时间
	private String refundId;//退款单号
	private String payType ;//支付方式
	private String tradeCheckSeriNo ;//淘宝支付宝对账用单号(TB)
	private String weixiCookie ;//维析所需客户浏览器cookie信息
	private String billOrderNo;//快钱订单号
	private String kqPaySn;//快钱订单号
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getSendSign() {
		return sendSign;
	}
	public void setSendSign(String sendSign) {
		this.sendSign = sendSign;
	}
	public String getReturnSign() {
		return returnSign;
	}
	public void setReturnSign(String returnSign) {
		this.returnSign = returnSign;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getSendSign2() {
		return sendSign2;
	}
	public void setSendSign2(String sendSign2) {
		this.sendSign2 = sendSign2;
	}
	public String getReturnSign2() {
		return returnSign2;
	}
	public void setReturnSign2(String returnSign2) {
		this.returnSign2 = returnSign2;
	}
	public String getErrorMsg2() {
		return errorMsg2;
	}
	public void setErrorMsg2(String errorMsg2) {
		this.errorMsg2 = errorMsg2;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendDate2() {
		return sendDate2;
	}
	public void setSendDate2(String sendDate2) {
		this.sendDate2 = sendDate2;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getReceiveDate2() {
		return receiveDate2;
	}
	public void setReceiveDate2(String receiveDate2) {
		this.receiveDate2 = receiveDate2;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getOrdID() {
		return ordID;
	}
	public void setOrdID(String ordID) {
		this.ordID = ordID;
	}
	public String getOrdAmt() {
		return ordAmt;
	}
	public void setOrdAmt(String ordAmt) {
		this.ordAmt = ordAmt;
	}
	public String getTradeResult() {
		return tradeResult;
	}
	public void setTradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
	}
	public String getTradeSeriNO() {
		return tradeSeriNO;
	}
	public void setTradeSeriNO(String tradeSeriNO) {
		this.tradeSeriNO = tradeSeriNO;
	}
	public String getTradeBank() {
		return tradeBank;
	}
	public void setTradeBank(String tradeBank) {
		this.tradeBank = tradeBank;
	}

	public void setReceiveRefundId(String receiveRefundId) {
		this.receiveRefundId = receiveRefundId;
	}
	public String getReceiveRefundId() {
		return receiveRefundId;
	}
	public String getTradeCheckSeriNo() {
		return tradeCheckSeriNo;
	}
	public void setTradeCheckSeriNo(String tradeCheckSeriNo) {
		this.tradeCheckSeriNo = tradeCheckSeriNo;
	}
	public String getWeixiCookie() {
		return weixiCookie;
	}
	public void setWeixiCookie(String weixiCookie) {
		this.weixiCookie = weixiCookie;
	}

    
	public String getBillOrderNo() {
		return billOrderNo;
	}
	public void setBillOrderNo(String billOrderNo) {
		this.billOrderNo = billOrderNo;
	}
	
	public String getKqPaySn() {
	
		return kqPaySn;
	}
	
	public void setKqPaySn(String kqPaySn) {
	
		this.kqPaySn = kqPaySn;
	}
	
	
}
