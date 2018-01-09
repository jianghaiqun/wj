/**
 * 
 */
package cn.com.sinosoft.action.shop;

import com.sinosoft.framework.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @author wangcaiyun
 *
 */
public class KqPkipair {
	private static final Logger logger = LoggerFactory.getLogger(KqPkipair.class);
	public String signMsg(String signMsg) {

		String base64 = "";
		try {
			// 密钥仓库
			KeyStore ks = KeyStore.getInstance("PKCS12");
			// 读取密钥仓库（相对路径）
			String file = Config.getClassesPath() +"99bill-rsa.pfx";
			
			FileInputStream ksfis = new FileInputStream(file);
			
			BufferedInputStream ksbufin = new BufferedInputStream(ksfis);

			char[] keyPwd = "KXB_KQ0725$$".toCharArray();
			
			ks.load(ksbufin, keyPwd);
			// 从密钥仓库得到私钥
			PrivateKey priK = (PrivateKey) ks.getKey("test-alias", keyPwd);
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priK);
			signature.update(signMsg.getBytes("utf-8"));
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			base64 = encoder.encode(signature.sign());
			
		} catch(FileNotFoundException e){
			logger.error("快钱加密文件99bill-rsa.pfx找不到" + e.getMessage(), e);
		}catch (Exception ex) {
			logger.error("快钱加密异常！" + ex.getMessage(), ex);
		}
		logger.info("快钱加密串：{}", base64);
		return base64;
	}
	public boolean enCodeByCer( String val, String msg, String cerName) {
		boolean flag = false;
		try {
			//获得文件(相对路径)
			String file = Config.getClassesPath() +cerName;
			FileInputStream inStream = new FileInputStream(file);
			
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			//获得公钥
			PublicKey pk = cert.getPublicKey();
			//签名
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(pk);
			signature.update(val.getBytes("UTF-8"));
			//解码
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			flag = signature.verify(decoder.decodeBuffer(msg));
			logger.info("快钱验签结果：{}", flag);
		} catch (Exception e) {
			logger.error("快钱验签异常！" + e.getMessage(), e);
		}
		return flag;
	}
}
