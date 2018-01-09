package cn.com.sinosoft.common;

 public class StrTool
 {
	  
	public static String replaceEx(String strMain, String strFind,
			String strReplaceWith) {
		if ((strMain == null) || (strMain.equals(""))) {
			return "";
		}

		StringBuffer tSBql = new StringBuffer();

		String tStrMain = strMain.toLowerCase();

		String tStrFind = strFind.toLowerCase();

		int intStartIndex = 0;

		int intEndIndex = 0;

		while ((intEndIndex = tStrMain.indexOf(tStrFind, intStartIndex)) > -1) {
			tSBql.append(strMain.substring(intStartIndex, intEndIndex));

			tSBql.append(strReplaceWith);

			intStartIndex = intEndIndex + strFind.length();
		}

		tSBql.append(strMain.substring(intStartIndex, strMain.length()));

		return tSBql.toString();
	}
 }
