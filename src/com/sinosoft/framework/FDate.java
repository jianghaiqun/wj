package com.sinosoft.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FDate implements Cloneable
{

    private final String pattern = "yyyy-MM-dd HH:mm:ss";

    private SimpleDateFormat df;

    public FDate()
    {
        df = new SimpleDateFormat(pattern);
    }


    /**
     * 输入日期类型变量，返回日期字符串
     * <p>
     * <b>Example: </b>
     * <p>
     * <p>
     * getString("Tue Oct 08 00:00:00 CST 2002") returns "2002-10-8"
     * <p>
     * @param mDate 日期类型变量
     * @return 日期字符串
     */
    public String getString(Date mDate)
    {
        String tString = null;
        if (mDate != null)
        {
            tString = df.format(mDate);
        }
        return tString;
    }

}
