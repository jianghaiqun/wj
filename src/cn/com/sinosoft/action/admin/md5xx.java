package cn.com.sinosoft.action.admin;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class md5xx {
	
	/*
	*MD5加密函数,函数名:getMD5Mac
	*输入:字符数组(byte[])
	*输出:字符数组(byte[])
	*功能:仅对获得的字符数组进行MD5操作,无填充,无MAC密钥
	*函数头:public byte[] getMD5Mac(byte[] bySourceByte)
	*/ 
	public static byte[] getMD5Mac(byte[] bySourceByte){
	byte[] byDisByte;
	MessageDigest md;

	try{
	md = MessageDigest.getInstance("MD5"); 
	md.reset();
	md.update(bySourceByte);
	byDisByte = md.digest(); 
	}catch (NoSuchAlgorithmException n){
	return(null);
	}
	return(byDisByte);
	}
	/*
	*MD5加密函数,函数名:getMD5Mac
	*输入:字符串(String)
	*输出:字符串(String)
	*功能:仅对获得的字符串进行MD5操作,无填充,无MAC密钥
	*函数头:public String getMD5Mac(String stSourceString)
	*/ 

	public static String getMD5Mac(String stSourceString)
	{
	String mystring;
	byte getbyte[];
	getbyte = getMD5Mac(stSourceString.getBytes());
	mystring = bintoascii(getbyte);
	return(mystring);
	}

	public static String bintoascii(byte []bySourceByte)
	{
	int len,i;
	byte tb;
	char high,tmp,low;
	String result=new String();
	len=bySourceByte.length;
	for(i=0;i<len;i++)
	{
	tb=bySourceByte[i];

	tmp=(char)((tb>>>4)&0x000f);
	if(tmp>=10)
	high=(char)('a'+tmp-10);
	else
	high=(char)('0'+tmp);
	result+=high;
	tmp=(char)(tb&0x000f);
	if(tmp>=10)
	low=(char)('a'+tmp-10);
	else
	low=(char)('0'+tmp);

	result+=low;

	}
	return result;
	}

	public static boolean MACCompare(String message, String mac)
	{   
	String mystring;
	mystring = getMD5Mac(message);
	return(mystring.equals(mac)); 

	}
}

