package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.sms.messageinterface.parse.MessageCode;

/*******************************************************************************
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhouxiang
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口服务业务处理实例化类
 ******************************************************************************/
public abstract class MessageBusinessBL implements MessageBusiness
{
    public String servicecode = "";
    public Mapx<String, Object> outputData = new Mapx<String, Object>();
    private Mapx<String, Object> inputData = new Mapx<String, Object>();
    private Mapx<String, Object> tResultData = new Mapx<String, Object>();
   // private VData tResultData = new VData();


    public Mapx<String, Object> getResult()
    {
        return tResultData;
    }

    public boolean submitData(Mapx<String, Object> mMap, String tServiceCode)
    {
        if (getInputData(mMap, tServiceCode))
        {
            if (checkData())
            {
                if (dealData())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            //this.mErrors.addOneError("MessageBusinessBL.getInputData（）有误。");
            return false;
        }
    }

    private boolean getInputData(Mapx<String, Object> mMap, String tServiceCode)
    {
        servicecode = tServiceCode;
        
        inputData = (Mapx<String, Object>) mMap.get("TransferData");
//        inputData = (TransferData) vData.getObjectByObjectName("TransferData",0);
//        globalInput = (GlobalInput) vData.getObjectByObjectName("GlobalInput",0);
        return true;
    }


    public String getInput(String key)
    {
        if (inputData == null)
        {
            return null;
        }
        return (String) inputData.get(key);
    }

    public void addMobileNum(String Value)
    {
        outputData.put(MessageCode.MOBILENUM, Value);
    }

    public void addSendData(String Value)
    {
        outputData.put(MessageCode.SENDDATA, Value);
    }

    public void addCustomerName(String Value)
    {
        outputData.put(MessageCode.CUSTOMERNAME, Value);
    }

    public void addFixDate(String Value)
    {
        outputData.put(MessageCode.FIXEDDATE, Value);
    }

    public void addFixTime(String Value)
    {
        outputData.put(MessageCode.FIXEDTIME, Value);
    }

    public void addManageCom(String Value)
    {
        outputData.put(MessageCode.MANAGECOM, Value);
    }

    public void addServiceBussNo(String Value)
    {
        outputData.put(MessageCode.SERVICEBUSSNO, Value);
    }
    
    public void addMemberName(String Value)
    {
        outputData.put(MessageCode.MEMBERNAME, Value);
    }
    public void addCaptcha(String Value)
    {
        outputData.put(MessageCode.CAPTCHA, Value);
    }

    public void addResult()
    {
//        tResultData.add(outputData);
//        tResultData.add(globalInput);
        tResultData.put("TransferData",outputData);
       // tResultData.putAll(globalInput);
    }

//    public void buildError(String Func, String ErrMsg)
//    {
//        CError cError = new CError();
//        cError.moduleName = "MessageBusinessBL";
//        cError.functionName = Func;
//        cError.errorMessage = ErrMsg;
//        this.mErrors.addOneError(cError);
//    }

    protected abstract boolean checkData();

    protected abstract boolean dealData();


}
