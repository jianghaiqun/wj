package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*******************************************************************************
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhouxiang
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口业务服务入口类
 ******************************************************************************/
public class MessageBusinessFactory
{
    private static final Logger logger = LoggerFactory.getLogger(MessageBusinessFactory.class);
    private static MessageBusinessFactory b = new MessageBusinessFactory();
    /** 发送方式：0-及时发送，1-定时发送 */
	public String SendWay = "";
    private MessageBusinessFactory()
    {

    }

    public static MessageBusinessFactory newInstance()
    {
        return b;
    }

    /**实例化业务逻辑处理类*/
    public MessageBusiness getBusiness(String tServiceCode)
    {
        MessageBusiness messagebusiness = null;

        String tServiceClass = ""; //服务类名

        StringBuffer tSQL = new StringBuffer("");
        tSQL.append("select m.serviceclass,m.sendway ");
        tSQL.append("from LIMessageService m  ");
        tSQL.append("where 1=1 and servicecode ='");
        tSQL.append(tServiceCode);
        tSQL.append("' ");
        try
        {
        	DataTable dt = new QueryBuilder(tSQL.toString()).executeDataTable();
    		if (dt.getRowCount() == 0) {
    			return null;
    		}
            tServiceClass = dt.getString(0, 0);
            SendWay = dt.getString(0, 1).trim();
            tServiceClass = tServiceClass.trim();//"com.sinosoft.lis.messageinterface.business.TestBusiness";

            Class tClass = Class.forName(tServiceClass);
            messagebusiness = (MessageBusiness) tClass.newInstance();
            logger.info("---{}交易服务类实例化成功　：{}",tServiceCode, tServiceClass);
        }
        catch (Exception e)
        {
        }
        return messagebusiness;
    }

    public String  getSendWay()
    {
       return SendWay;
    }

}
