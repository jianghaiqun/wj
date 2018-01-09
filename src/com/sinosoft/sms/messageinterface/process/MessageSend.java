package com.sinosoft.sms.messageinterface.process;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.LIMessageSentFailSchema;
import com.sinosoft.schema.LIMessageSentSchema;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

/*******************************************************************************
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhouxiang
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口服务数据发送处理类
 ******************************************************************************/

public class MessageSend
{
    private static final Logger logger = LoggerFactory.getLogger(MessageSend.class);
    private Document mDoc = null;
    private LIMessageSentSchema mLIMessageSentSchema = new LIMessageSentSchema();
  //  public CErrors mErrors = new CErrors();
    private  int connectTimeOut = 2000; //连接超时
    private  int readTimeOut = 2000; //读取数据超时


    public boolean submitData(Mapx<String, Object> mMap, String cOperate) throws ParseException
    {
        if (!getInputData(mMap))
        {
            return false;
        }
        if (sendMsg())
        {
            MsgSentSucc();
        }
        else
        {
        	//如果是短信发送失败批处理，信息已存在于fail表，不再重新提交返回错误信息
        	if(cOperate.equals("FAIL"))
        	{
        		return false;
        	}
        	else
        	{
        		MsgSentfail();	
        	}	
        }

        return true;
    }

    private boolean getInputData(Mapx<String, Object> mMap)
    {
        mDoc = (Document) mMap.get("Document");
        mLIMessageSentSchema =(LIMessageSentSchema) mMap.get("LIMessageSentSchema");

        return true;
    }

    private boolean sendMsg()
    {
        String tSendData = PubFun.docToString(mDoc);

//        OutputFile(mDoc);

        String strSql ="select Value from zdconfig where type = 'SendMsgUser'";
        
        DataTable dt = new QueryBuilder(strSql).executeDataTable();
		if (dt.getRowCount() != 1) {
			return false;
		}
        String UserCode = dt.getString(0, 0);

        strSql ="select Value from zdconfig where type = 'SendMsgPassword'";
        dt = new QueryBuilder(strSql).executeDataTable();
		if (dt.getRowCount() != 1) {
			return false;
		}
        String Password = dt.getString(0, 0);
        //数据库中直接配置了加密后的用户名密码
        //Password = EncodeMD5.EnCode("001");

        strSql ="select Value from zdconfig where type = 'MsgServerPort'";
        dt = new QueryBuilder(strSql).executeDataTable();
        String tPort =  dt.getString(0, 0);
        if (tPort == null || "".equals(tPort))
        {
            logger.info("请设置短信平台http路径与端口!");
            return false;
        }
        String url = tPort + "/interface/SysSendMsg.jsp?userName=" + UserCode +
                     "&password=" + Password;


        logger.info("url=={}", url);

        ByteArrayInputStream fi = null;
        try
        {
            fi = new ByteArrayInputStream(tSendData.getBytes());
            URL jspUrl = new URL(url);
            URLConnection uc = jspUrl.openConnection();
            // （单位：毫秒）连接超时
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connectTimeOut));
            // （单位：毫秒）读操作超时
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(readTimeOut));
            uc.setDoOutput(true);
            uc.setRequestProperty("content-type", "text/html");
            OutputStream os = uc.getOutputStream();
            int length = 0;
            byte[] buffer = new byte[4096];
            while ((length = fi.read(buffer)) != -1)
            {
                os.write(buffer, 0, length);
            }
            os.flush(); // 将输出流提交到服务器端
            os.close(); // 关闭输出流对象
            fi.close();
            InputStream is = uc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "gbk")); 
            String str = null; 
            String message = "";
            while((str = br.readLine()) != null) 
            { 
            	message += new String(str.getBytes("UTF-8"),"UTF-8"); 
            } 
            br.close(); 


//            while ((length = is.read(buffer)) != -1)
//            {
//                message += new String(buffer).trim();
//            }
            is.close();
            logger.info(message);
            String index = message.substring(0, 1);
            if ("0".equals(index))
            {
            	logger.error("短信平台接收短信失败！");
                return false;
            }
        }
        catch (IOException e)
        {
           logger.error(e.getMessage(), e);
            try
            {
                if (fi != null)
                {
                    fi.close();
                }
            }
            catch (IOException e1)
            {
               logger.error(e.getMessage(), e);
            }
            return false;
        }
        return true;
    }

    private boolean MsgSentSucc() throws ParseException
    {
        mLIMessageSentSchema.setMakeDate(PubFun.StringToDate(PubFun.getCurrentDate()));
        mLIMessageSentSchema.setMakeTime(PubFun.getCurrentTime());
        mLIMessageSentSchema.setModifyDate(PubFun.StringToDate(PubFun.getCurrentDate()));
        mLIMessageSentSchema.setModifyTime(PubFun.getCurrentTime());
        
        
        Transaction tSuccData = new Transaction();
        tSuccData.add(mLIMessageSentSchema, Transaction.INSERT);
        DataSubmit(tSuccData);
        return true;
    }

    private boolean MsgSentfail() throws ParseException
    {
        LIMessageSentFailSchema tLIMessageSentFailSchema = new
                LIMessageSentFailSchema();

        tLIMessageSentFailSchema.setMessageId(mLIMessageSentSchema.getMessageId());
        tLIMessageSentFailSchema.setServiceCode(mLIMessageSentSchema.
                                                getServiceCode());
        tLIMessageSentFailSchema.setComCode(mLIMessageSentSchema.getComCode());
        tLIMessageSentFailSchema.setSendData(mLIMessageSentSchema.getSendData());
        tLIMessageSentFailSchema.setMobileNum(mLIMessageSentSchema.getMobileNum());
        tLIMessageSentFailSchema.setOperator(mLIMessageSentSchema.getOperator());
        tLIMessageSentFailSchema.setServiceBussNo(mLIMessageSentSchema.getServiceBussNo());
        tLIMessageSentFailSchema.setMakeDate(PubFun.StringToDate(PubFun.getCurrentDate()));
        tLIMessageSentFailSchema.setMakeTime(PubFun.getCurrentTime());
        tLIMessageSentFailSchema.setModifyDate(PubFun.StringToDate(PubFun.getCurrentDate()));
        tLIMessageSentFailSchema.setModifyTime(PubFun.getCurrentTime());
        tLIMessageSentFailSchema.setSucessFlag("N");
        
        /*** 添加发送客户名称字段 朱涛 2010-07-08***/
        tLIMessageSentFailSchema.setCustomerName(mLIMessageSentSchema.getCustomerName());
        
        Transaction tFailData = new Transaction();
        tFailData.add(tLIMessageSentFailSchema, Transaction.INSERT);
        DataSubmit(tFailData);

        return true;
    }

    private boolean DataSubmit(Transaction trans)
    {
        if (!trans.commit())
        {
            //this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            return false;
        }

        return true;
    }

    /**开发中为了
     * 检验数据*/
    private boolean OutputFile(Document mDoc)
    {
        XMLOutputter outputter = null;
        FileWriter writer = null;

        try
        {
           outputter = new XMLOutputter();
            writer = new FileWriter("D:/Msg" + PubFun.getCurrentDate() +
                                    "-" +
                                    PubFun.getCurrentTime().replace(':', '-') +
                                    ".xml");
            outputter.output(mDoc, writer);
            writer.close();
        }
        catch (IOException e)
        {
           logger.error(e.getMessage(), e);
        }
        return true;
    }
}
