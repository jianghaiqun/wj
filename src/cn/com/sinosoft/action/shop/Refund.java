package cn.com.sinosoft.action.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Refund {
	private static final Logger logger = LoggerFactory.getLogger(Refund.class);
	/**
	 * 汇付天下退款
	 * 
	 * @return
	 */
	public static String refund_hftx(String Version, String MerId, String DivDetails, String RefAmt, String OldOrdId, String BgRetUrl, String ChkValue,String ordId,String GateUrl) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(GateUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());// 发送请求
			StringBuffer sb = new StringBuffer();
			addPair(sb, "Version", Version);
			addPair(sb, "CmdId", "Refund");
			addPair(sb, "MerId", MerId);
			addPair(sb, "DivDetails", DivDetails);
			addPair(sb, "RefAmt", RefAmt);
			addPair(sb, "OrdId", ordId);
			addPair(sb, "OldOrdId", OldOrdId);
			addPair(sb, "BgRetUrl", BgRetUrl);
			addPair(sb, "ChkValue", ChkValue);

			osw.write(sb.substring(0, sb.length() - 1));
			osw.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));//获得响应
			String line = null;
			sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			line = sb.toString();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return null;
	}

	public static void addPair(StringBuffer sb, String name, String value) {
		sb.append(name);
		sb.append("=");
		sb.append(value);
		sb.append("&");
	}

	public static void main(String[] args) {

	}
}
