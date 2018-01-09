package com.wxpay.wxap.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.sinosoft.framework.Config;
import com.wxpay.wxap.RequestHandler;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.SortedMap;
import java.util.TreeMap;

/* 二维码生成器
 * @author lenovo
 */
public class QRUtil {
	private static final Logger logger = LoggerFactory.getLogger(QRUtil.class);

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private QRUtil() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getQrUrl(String cPaySn) {

		RequestHandler queryReq = new RequestHandler(null, null);
		queryReq.init();
		queryReq.init(WxPayConfig.APP_ID, WxPayConfig.APP_SECRET,
				WxPayConfig.PARTNER_KEY, WxPayConfig.APP_KEY);

		String noncestr = Sha1Util.getNonceStr();
		String timestamp = Sha1Util.getTimeStamp();

		SortedMap<String, String> signParams = new TreeMap<String, String>();
		signParams.put("appid", WxPayConfig.APP_ID);
		signParams.put("mch_id", WxPayConfig.PARTNER);
		signParams.put("nonce_str", noncestr);
		signParams.put("product_id", cPaySn);
		signParams.put("time_stamp", timestamp);

		String sign = queryReq.createSign(signParams);

		signParams.put("sign", sign);

		String toURL = "appid=" + WxPayConfig.APP_ID + "&mch_id="
				+ WxPayConfig.PARTNER + "&nonce_str=" + noncestr
				+ "&product_id=" + cPaySn + "&time_stamp=" + timestamp
				+ "&sign=" + sign;
		String parm = "weixin://wxpay/bizpayurl?" + toURL;

		// "weixin://wxpay/bizpayurl?appid=wx2421b1c4370ec43b&mch_id=10000100&nonce_str=f6808210402125e30663234f94c87a8c&product_id=1&time_stamp=1415949957&sign=512F68131DD251DA4A45DA79CC7EFE9D";

		//System.out.println("parm=====================" + parm);

		String imgURL = File.separator + "wxpay_qr" + File.separator + cPaySn
				+ ".gif";
		String tURL = Config.getContextRealPath() + imgURL;
		try {
			int width = 300;
			int height = 300;
			// 二维码的图片格式
			String format = "gif";
			Hashtable hints = new Hashtable();
			// 内容所使用编码
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

			BitMatrix bitMatrix = new MultiFormatWriter().encode(parm,
					BarcodeFormat.QR_CODE, width, height, hints);
			// 生成二维码
			File outputFile = new File(tURL);
			QRUtil.writeToFile(bitMatrix, format, outputFile);
		} catch (Exception e) {
			logger.error("微信支付生成二维码异常，PaySn：" + cPaySn + e.getMessage(), e);
			return "";
		}
		return Config.getServerContext() + imgURL.replaceAll("\\\\", "/");
	}

	public static void main(String[] args) throws Exception {
		/*
		 * String text = "http://www.ablanxue.com"; int width = 300; int height
		 * = 300; // 二维码的图片格式 String format = "gif"; Hashtable hints = new
		 * Hashtable(); // 内容所使用编码 hints.put(EncodeHintType.CHARACTER_SET,
		 * "utf-8"); BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
		 * BarcodeFormat.QR_CODE, width, height, hints); // 生成二维码 File
		 * outputFile = new File("d:" + File.separator + "new.gif");
		 * QRUtil.writeToFile(bitMatrix, format, outputFile);
		 */
		Element root = new Element("xml");
		Document Doc = new Document(root);
		Element elements = new Element("appid").setText("adfasdf");
		root.addContent(elements);
		XMLOutputter XMLOut = new XMLOutputter();
		try {
			XMLOut.output(Doc, new FileOutputStream("D:\\fruit.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
