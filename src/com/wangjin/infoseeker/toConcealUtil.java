/**
 * Project Name:wj
 * File Name:toConceal.java
 * Package Name:com.wangjin.infoseeker
 * Date:2017年7月6日下午6:59:42
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.wangjin.infoseeker;

/**
 * ClassName:toConceal <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2017年7月6日 下午6:59:42 <br/>
 *
 * @author:liuhongyu 
 */
public class toConcealUtil {

	public static String toConceal(String str,int SIZE) {
		String SYMBOL = "*";
		
		if(null  == str || "".equals(str)) 
			return str;
		int l = str.length();
		int a = l/2;
		int b = a-1;
		int c = l%2;
		StringBuffer sb = new StringBuffer(l);
		if(l <= 2) {
			if(c==1)
				return SYMBOL;
			sb.append(SYMBOL);
			sb.append(str.charAt(l-1));
		}else {
			if(b<=0) {
				sb.append(str.substring(0, 1));
				sb.append(SYMBOL);
				sb.append(str.substring(l-1, l));
			}else if(b>=SIZE/2 && SIZE+1!=l){
				int e  = (l-SIZE)/2;
				sb.append(str.substring(0, e));
				for(int i  = 0;i<SIZE;i++)
					sb.append(SYMBOL);
				if((c==0&&SIZE%2==0)||(c!=0&&SIZE%2!=0)) 
					sb.append(str.substring(l-e, l));
				else 
					sb.append(str.substring(l-(e+1), l)); 
			}else {
				int d  = l -2 ;
				sb.append(str.substring(0, 1));
				for(int i  = 0;i<d;i++)
					sb.append(SYMBOL);
				sb.append(str.substring(l-1, l)); 
			}
		}
		return sb.toString();
	}
}

