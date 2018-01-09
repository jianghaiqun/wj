package cn.com.sinosoft.util;


import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;

import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.InformationAppntSchema;
import com.sinosoft.schema.InformationInsuredSchema;
import com.sinosoft.schema.ZDMemberSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import com.sinosoft.schema.ordersSchema;
import com.sinosoft.schema.ordersSet;
import com.sinosoft.utility.ExeSQL;

public class StringUtilC extends Page {
	/**
	 * UTF-8的三个字节的BOM
	 */
	public static final byte[] BOM = new byte[] { (byte) 239, (byte) 187, (byte) 191 };

	/**
	 * 十六进制字符
	 */
	public static final char HexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取指定字符串的MD5摘要
	 */
	
	/**
	 * 将字符串进行md5摘要，然后输出成十六进制形式
	 */
	public void  md5Hex() {
		try {
			String src = $V("KID");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src.getBytes());
			Response.put("KID", hexEncode(md)); 
		} catch (Exception e) {
			
		}
	}
	/**
	 * 将二进制数组转换成十六进制表示
	 */
	public static String hexEncode(byte[] bs) {
		return new String(new Hex().encode(bs));
	}
}