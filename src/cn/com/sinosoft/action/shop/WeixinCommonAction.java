package cn.com.sinosoft.action.shop;

import com.sinosoft.inter.ActionUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 
 * @author
 * 
 */
@ParentPackage("shop")
public class WeixinCommonAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mMobileNo = "";// 手机号

	private String mActionCode = "";// 短信模版类型

	/**
	 * 代理人微信平台,发送手机验证码
	 * 
	 * @param tPARAMETERS
	 * @return
	 */
	public void validataCode() {
		
		// 生成验证码
		String tValidateCode = "";
		try {
			String tMobileNo = mMobileNo;// 手机号
			tMobileNo = this.getRequest().getParameter("mMobileNo");
			Random rm = new Random();
			int code = rm.nextInt(999999);
			if (code >= 100000) {
				tValidateCode = String.valueOf(code);
			}
			// 发送至手机
			String tActionCode = mActionCode;// 产品编码
			tActionCode = this.getRequest().getParameter("mActionCode");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("MobileNum", tMobileNo);
			data.put("validatecode", tValidateCode);
			ActionUtil.sendSms(tActionCode, tMobileNo, tValidateCode);
			
			OutputStream out;
			out = this.getResponse().getOutputStream();
			out.write(tValidateCode.getBytes("utf-8"));
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("WeixinCommonAction.getValidataCode 方法出现异常：" + e.getMessage(), e);
		}
	}

	public String getmMobileNo() {
		return mMobileNo;
	}

	public void setmMobileNo(String mMobileNo) {
		this.mMobileNo = mMobileNo;
	}

	public String getmActionCode() {
		return mActionCode;
	}

	public void setmActionCode(String mActionCode) {
		this.mActionCode = mActionCode;
	}

}
