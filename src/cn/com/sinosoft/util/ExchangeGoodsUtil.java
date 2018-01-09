/**
 * 
 */
package cn.com.sinosoft.util;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.sms.messageinterface.process.Md5security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author wangcaiyun
 *
 */
public class ExchangeGoodsUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExchangeGoodsUtil.class);

	private String errMsg = "";
	
	/**
	 * 福禄接口
	 * @param params
	 * @return
	 */
	public String fuluPost(Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			setErrMsg("福禄接口请求参数未设置！");
			return null;
		}
		Map<String, String> baseInfo = getFuluBaseInfo();
		String url="";
		String secret = "";
		if (baseInfo != null && !baseInfo.isEmpty()) {
			url = baseInfo.get("url");
			secret = baseInfo.get("secret");
		}
		if (StringUtil.isEmpty(url)) {
			setErrMsg("福禄接口地址未设置！");
			return null;
		}
		if (StringUtil.isEmpty(secret)) {
			setErrMsg("福禄私钥未设置！");
			return null;
		}
		// 获取签名
		String sign = sign(params, secret);
		params.put("sign", sign);
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		return httpClientUtil.doPost(url, params, "UTF-8");
	}

	/**
	 * 加密取得签名
	 */
	public String sign(Map<String, String> params, String secret) {
		// 检查参数是否已经排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		// 把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder();
		for (String key : keys) {
			String value = params.get(key);
			if (StringUtil.isNotEmpty(value)) {
				query.append(key).append("=").append(value).append("&");
			} else {
				params.remove(key);
			}
		}
		if (query.length() > 0) {
			// 把秘钥拼接在参数后面、使用MD5加密
			Md5security md5security = new Md5security();
			md5security.md5s(query.substring(0, query.length() - 1) + secret, "UTF-8");
			return md5security.str;
			
		} else {
			return null;
		}
	}
	
	/**
	 * 取得福禄交互存储地址
	 * @return
	 */
	public String getFuluSaveAddress() {
		return new QueryBuilder("select CodeName from zdcode where CodeType = 'FuLuInfo' and ParentCode = 'FuLuInfo' and CodeValue='saveAddress'").executeString();	
	}
	
	public Map<String, String> getFuluBaseInfo() {
		@SuppressWarnings("unchecked")
		Map<String, String> baseInfo = new QueryBuilder(
				"select CodeValue,Memo from zdcode where CodeType='FuLuInfo' and ParentCode='FuLuInfo'")
				.executeDataTable().toMapx(0, 1);
		return baseInfo;
	}
	
	/**
	 * 保存福禄请求数据
	 * @param saveAddress 存储地址
	 * @param type 请求类型
	 * @param fileName 文件名
	 * @param requestContent 请求数据
	 */
	public void saveFuluRequest(String saveAddress, String type, String fileName, String requestContent) {
		try {
			String date = DateUtil.getCurrentDate("yyyyMMdd");
			// 保存请求内容
			String path = saveAddress+type+"/request/"+date+"/";
			FileUtil.mkdir(path);
			FileUtil.writeText(path+fileName+".txt", requestContent);
		} catch(Exception e) {
			logger.error("ExchangeGoodsUtil:保存福禄请求数据异常！"+requestContent + e.getMessage(), e);
		}
	}
	
	/**
	 * 保存福禄响应数据
	 * @param saveAddress 存储地址
	 * @param type 请求类型
	 * @param fileName 文件名
	 * @param responseContent 响应数据
	 */
	public void saveFuluResponse(String saveAddress,String type, String fileName, String responseContent) {
		try {
			String date = DateUtil.getCurrentDate("yyyyMMdd");
			// 保存响应内容
			String path = saveAddress+type+"/response/"+date+"/";
			FileUtil.mkdir(path);
			FileUtil.writeText(path+fileName+".txt", responseContent);
		} catch(Exception e) {
			logger.error("ExchangeGoodsUtil:保存福禄响应数据异常！"+responseContent + e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(ExchangeGoodsUtil.DoDES("Pc1Lf5oOleIwCJF4iKajcQ==", "789902457","951D5E9100FC6B555132444BCA222C50"));
//		System.out.println(ExchangeGoodsUtil.DoDES("Pc1Lf5oOleIwCJF4iKajcQ==", "789902457","951D5E9100FC6B555132444BCA222C50"));
		//System.out.println(ExchangeGoodsUtil.DoDES("ZixItydCx0OAq0MJ42Vhyw==", "653200113","F636297CB44B2F02BA4651282266EC2F"));
		
	}
	public static String DoDES(String data, String OrderId, String secret) {
		try {
			data = data.replace("\\", "");
			Md5security md5security = new Md5security();
			md5security.md5s(OrderId + secret, "UTF-8");
			String password =md5security.str.substring(4, 12);
			byte[] byteMi = new sun.misc.BASE64Decoder().decodeBuffer(data);
			IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
			SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, zeroIv);
			byte decryptedData[] = cipher.doFinal(byteMi);
			return new String(decryptedData, "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
		}catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
