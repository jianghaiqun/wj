/**
 * Project Name:wj
 * File Name:AesUtilQN.java
 * Package Name:com.sinosoft
 * Date:2016年8月10日下午2:18:13
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AesUtilQN {
	private static final Logger logger = LoggerFactory.getLogger(AesUtilQN.class);

	private static final String defaultCharset = "UTF-8";
	private static final String KEY_AES = "AES";
	private static final String KEY_MD5 = "MD5";
    public static final String key = "test";
	private static MessageDigest md5Digest;

	static {
		try {
			md5Digest = MessageDigest.getInstance(KEY_MD5);
		} catch (NoSuchAlgorithmException e) {
			
		}
	}

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		String content = "{\"activitySn\":\"\",\"appnt\":{\"accName\":\"\",\"applicantAddress\":\"\",\"applicantAge\":\"\",\"applicantArea1\":\"\",\"applicantArea1Name\":\"\",\"applicantArea2\":\"\",\"applicantArea2Name\":\"\",\"applicantArea3\":\"\",\"applicantArea3Name\":\"\",\"applicantBirthday\":\"1990-08-01\",\"applicantEnName\":\"dghg\",\"applicantEndID\":\"\",\"applicantFirstName\":\"\",\"applicantIdentityId\":\"1558456\",\"applicantIdentityType\":\"2\",\"applicantIdentityTypeName\":\"护照\",\"applicantLastName\":\"\",\"applicantMail\":\"123@k.cn\",\"applicantMobile\":\"13356565656\",\"applicantName\":\"符合\",\"applicantOccupation1\":\"\",\"applicantOccupation1Name\":\"\",\"applicantOccupation2\":\"\",\"applicantOccupation2Name\":\"\",\"applicantOccupation3\":\"\",\"applicantOccupation3Name\":\"\",\"applicantSex\":\"M\",\"applicantSexName\":\"男\",\"applicantSn\":\"\",\"applicantStartID\":\"\",\"applicantTel\":\"\",\"applicantZipCode\":\"\",\"bankAccNo\":\"\",\"bankCode\":\"\",\"createDate\":\"\",\"id\":\"\",\"informationSn\":\"\",\"invoiceHeading\":\"\",\"modifyDate\":\"\",\"remark\":\"\",\"sdinformaiton_id\":\"\",\"socialSecurity\":\"\"},\"bankInfo\":null,\"channelsn\":\"\",\"commentId\":0,\"couponsn\":\"\",\"createdate\":\"\",\"dellFlag\":\"\",\"discountAmount\":\"\",\"discountRates\":\"\",\"diyActivityDescription\":\"\",\"diyActivitySn\":\"\",\"healthysn\":\"\",\"id\":\"\",\"insured\":[{\"createDate\":\"\",\"destinationCountry\":\"东南亚 Southeast Asia\",\"destinationCountryText\":\"东南亚\",\"discountPrice\":\"\",\"driverNo\":\"\",\"driverSchoolName\":\"\",\"flightLocation\":\"\",\"flightNo\":\"\",\"flightTime\":\"\",\"haveBuy\":\"\",\"height\":\"\",\"id\":\"\",\"informationSn\":\"\",\"insuredSn\":\"\",\"isSelf\":\"\",\"modifyDate\":\"\",\"mulInsuredFlag\":\"\",\"nationality\":\"\",\"orderSn\":\"\",\"outGoingParpose\":\"\",\"overseasOccupation\":\"\",\"recognizeeAddress\":\"\",\"recognizeeAge\":\"\",\"recognizeeAppntRelation\":\"00\",\"recognizeeAppntRelationName\":\"本人\",\"recognizeeArea1\":\"\",\"recognizeeArea1Name\":\"\",\"recognizeeArea2\":\"\",\"recognizeeArea2Name\":\"\",\"recognizeeArea3\":\"\",\"recognizeeArea3Name\":\"\",\"recognizeeBirthday\":\"1990-08-01\",\"recognizeeEnName\":\"dghg\",\"recognizeeEndID\":\"\",\"recognizeeFirstName\":\"\",\"recognizeeIdentityId\":\"1558456\",\"recognizeeIdentityType\":\"2\",\"recognizeeIdentityTypeName\":\"护照\",\"recognizeeIsMarry\":\"\",\"recognizeeKey\":\"\",\"recognizeeLashName\":\"\",\"recognizeeMail\":\"\",\"recognizeeMobile\":\"13356565656\",\"recognizeeMul\":\"\",\"recognizeeName\":\"符合\",\"recognizeeOccupation1\":\"\",\"recognizeeOccupation1Name\":\"\",\"recognizeeOccupation2\":\"\",\"recognizeeOccupation2Name\":\"\",\"recognizeeOccupation3\":\"\",\"recognizeeOccupation3Name\":\"\",\"recognizeeOperate\":\"\",\"recognizeePrem\":\"\",\"recognizeeSex\":\"M\",\"recognizeeSexName\":\"男\",\"recognizeeSn\":\"\",\"recognizeeStartID\":\"\",\"recognizeeTel\":\"\",\"recognizeeTotalPrem\":\"\",\"recognizeeZipCode\":\"\",\"remark\":\"\",\"schoolOrCompany\":\"\",\"sdinformation_id\":\"\",\"socialSecurity\":\"\",\"travelMode\":\"\",\"travelType\":\"\",\"uwCheckFlag\":\"\",\"weight\":\"\"}],\"memberId\":\"\",\"modifyDate\":\"\",\"offsetPoint\":\"\",\"orderActivity\":\"\",\"orderCoupon\":\"\",\"orderIntegral\":\"\",\"orderSn\":\"\",\"orderStatus\":0,\"orderinfo\":{\"appLevel\":\"\",\"appLevelName\":\"\",\"appMult\":\"\",\"appType\":\"\",\"appTypeName\":\"\",\"birthday\":\"\",\"chargeDisplay\":\"\",\"chargeType\":\"\",\"chargeYear\":\"\",\"chargeYearName\":\"\",\"cpsSource\":\"\",\"cpsUserCode\":\"\",\"createDate\":\"\",\"discountRates\":\"\",\"endDate\":\"2016-08-16\",\"ensure\":\"1D-2D\",\"ensureDisplay\":\"\",\"ensureLimit\":\"2\",\"ensureLimitType\":\"D\",\"healthysn\":\"\",\"id\":\"\",\"informationSn\":\"\",\"insuranceCompany\":\"\",\"kid\":\"\",\"modifyDate\":\"\",\"orderSn\":\"\",\"planCode\":\"ATSEATSTPP\",\"planName\":\"\",\"point\":\"\",\"pointStatus\":\"\",\"primitiveProductTitle\":\"\",\"productDiscountPrice\":\"\",\"productHtmlFilePath\":\"\",\"productId\":\"204901052\",\"productName\":\"安联安途东南亚旅行险计划\",\"productOutCode\":\"\",\"productPrice\":\"\",\"productQuantity\":\"\",\"remark\":\"\",\"riskType\":\"\",\"sdorder_id\":\"\",\"sex\":\"\",\"startDate\":\"2016-08-15\",\"subRiskType\":\"\",\"supKindCode\":\"\",\"supRiskCode\":\"\",\"textAge\":\"60D-85Y\"},\"payAmount\":\"\",\"payPrice\":\"\",\"paySn\":\"\",\"payStatus\":0,\"payType\":\"\",\"pointexchangeflag\":\"\",\"productNum\":\"\",\"productTotalPrice\":\"\",\"property\":[],\"remark\":\"\",\"sumActivity\":\"\",\"sumCoupon\":\"\",\"sumIntegral\":\"\",\"tbComCode\":\"\",\"tbTradeSeriNo\":\"\",\"totalAmount\":\"\"}";
//		System.out.println("加密前：" + content);
//		System.out.println("加密密钥和解密密钥：" + key);
//		String encrypt = encrypt(content, key);
//		System.out.println("加密后：" + encrypt);
//		String decrypt = decrypt(encrypt, key);
//		System.out.println(decrypt);
//		System.out.println("解密后：" + decrypt);
//		System.out.println(System.currentTimeMillis() - start);
//
	}

	/**
	 * 加密
	 *
	 * @param data
	 *            需要加密的内容
	 * @param key
	 *            加密密码
	 * @return
	 */
	public static String encrypt(String data, String key) {

		return doAES(data, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 解密
	 *
	 * @param data
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @return
	 */
	public static String decrypt(String data, String key) {

		return doAES(data, key, Cipher.DECRYPT_MODE);
	}

	/**
	 * 加解密
	 *
	 * @param data
	 * @param key
	 * @param mode
	 * @return
	 */
	private static String doAES(String data, String key, int mode) {

		try {
			if (StringUtils.isBlank(data) || StringUtils.isBlank(key)) {
				return null;
			}

			boolean encrypt = mode == Cipher.ENCRYPT_MODE;
			byte[] content;
			if (encrypt) {
				content = data.getBytes(defaultCharset);

			} else {
				content = Base64.decodeBase64(data.getBytes(defaultCharset));

			}
			// 创建密钥
			SecretKeySpec keySpec = new SecretKeySpec(md5Digest.digest(key.getBytes(defaultCharset)), KEY_AES);
			// 创建密码器
			Cipher cipher = Cipher.getInstance(KEY_AES);
			// 初始化
			cipher.init(mode, keySpec);

			byte[] result = cipher.doFinal(content);

			if (encrypt) {
				return new String(Base64.encodeBase64(result));

			} else {
				return new String(result, defaultCharset);

			}
		} catch (Exception e) {
			logger.error("AES密文处理异常" + e.getMessage(), e);

		}
		return null;
	}
}
