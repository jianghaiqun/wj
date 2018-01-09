package com.sinosoft.sms.messageinterface.pubfun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*******************************************************************************
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhouxiang
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口加密服务处理类
 ******************************************************************************/
public class EncodeMD5
{
    private static final Logger logger = LoggerFactory.getLogger(EncodeMD5.class);

    public static String HexCode[] =
    { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

    public static byte[] encodeByMD5(byte[] a)
    {
        byte[] b = null;
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            b = md5.digest(a);
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error(e.getMessage(), e);
            return null;
        }
        return b;
    }

    public static String byteArrayToHexString(byte b[])
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            sb = sb.append(byteToHexString(b[i]));
        return sb.toString();
    }

    public static String byteToHexString(byte b)
    {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return HexCode[d1] + HexCode[d2];
    }

    public static String EnCode(String OriString)
    {
        return byteArrayToHexString(encodeByMD5(OriString.getBytes()));
    }

}
