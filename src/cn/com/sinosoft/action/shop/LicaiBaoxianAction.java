package cn.com.sinosoft.action.shop;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import cn.com.sinosoft.service.LicaiBaoxianService;
import com.sinosoft.framework.data.QueryBuilder;
import org.apache.struts2.convention.annotation.ParentPackage;
  
/**
 * ClassName: LicaiBaoxianAction <br/>
 * Function: 理财险小额验签，查询. <br/>
 * date: 2016年6月1日 上午10:20:10 <br/>
 *
 * @author wwy
 * @version 
 */
@ParentPackage("shop")
public class LicaiBaoxianAction extends BaseShopAction { 
	
	/**
	 * serialVersionUID:.
	 */
	private static final long serialVersionUID = 704055305689159586L;
	
	private String orderSn;
	private String bankCode;
	private String bankAccNo;
	private String bussNo;
	
	@Resource
	private LicaiBaoxianService licaiBaoxianService;

	/**
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public String cardCheckSearch () {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderSn", orderSn);
		param.put("bussNo", bussNo);
		Map<String, Object> jsonMap = licaiBaoxianService.cardCheckSearch(param);
		
		return ajaxMap2Json(jsonMap);
	}
	/**
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public String cardCheckSend () {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderSn", orderSn);
		param.put("bankCode", bankCode);
		param.put("bankAccNo", bankAccNo);
		
		Map<String, Object> jsonMap = licaiBaoxianService.cardCheckSend(param);
		
		return ajaxMap2Json(jsonMap);
	}
	/**
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public String clearcardcheck(){
		
		String bankaccno = getParameter("bankaccno");
		
		String sql = "UPDATE sdcardbind set prop2 = '1' ,sendcheckcount = '0' where bankaccno = ?";
		
		QueryBuilder qb = new QueryBuilder(sql,bankaccno);
		
		int dt = qb.executeNoQuery();
		
		return ajaxJson(String.valueOf(dt));
	}
	
	public String authenticationApply(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderSn", orderSn);
		param.put("bankCode", bankCode);
		param.put("bankAccNo", bankAccNo);
		param.put("bussNo", bussNo);
		Map<String, Object> jsonMap = licaiBaoxianService.obtainVCode(param);
		return ajaxMap2Json(jsonMap);
	}
	
	
	public String getOrderSn() {
		return orderSn;
	}
	
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getBussNo() {
		return bussNo;
	}
	
	public void setBussNo(String bussNo) {
		this.bussNo = bussNo;
	}
}