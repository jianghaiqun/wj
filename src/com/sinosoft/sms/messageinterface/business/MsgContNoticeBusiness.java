package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.sms.messageinterface.parse.MessageCode;


/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhouxaing
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口注册发送验证码处理类
 ******************************************************************************/

public class MsgContNoticeBusiness extends MessageBusinessBL
{
    private String mobilenum;
    private String unitcode;
    private String contNo;

    protected boolean checkData()
    {
    	
        mobilenum = super.getInput(MessageCode.MOBILENUM);
        unitcode=super.getInput(MessageCode.MANAGECOM);
        contNo=super.getInput(MessageCode.SERVICEBUSSNO);
//
//        tSQL.append("select messageid  from LIMessageInteract ");
//        tSQL.append("  where servicecode = '");
//        tSQL.append(MessageCode.Msg_BIRTHDAY);
//        tSQL.append("' and servicebussno = '");
//        tSQL.append(appntno);
//        tSQL.append("' and mobilenum ='");
//        tSQL.append(mobilenum);
//        tSQL.append("' and to_char(makedate, 'yyyy') = to_char(date'");
//        tSQL.append(PubFun.getCurrentDate());
//        tSQL.append("', 'yyyy')  ");
//        tSSRS = tExeSQL.execSQL(tSQL.toString());
//        if (tSSRS.getMaxRow() > 0)
//        {
//        	super.mErrors.clearErrors();
//            super.mErrors.addOneError("此客户相同手机号今年已经发送过生日短信。");
//            return false;
//        }

        return true;
    }
    protected boolean dealData()
    {
    	DataTable dt = new QueryBuilder("select a.contno,a.AppntName,a.InsuredName,a.signdate,b.productName,b.insuranceCompany,b.ordersn from fccont a,orders b where a.orderNo=b.b.ordersn and a.contNo =? ", contNo).executePagedDataTable(1, 0);
    	String contNo=dt.getString(0, 0);
    	String appntName=dt.getString(0, 1);
    	String insuredName=dt.getString(0, 2);
    	String signDate=dt.getString(0, 3);
    	String productName=dt.getString(0, 4);
    	String insuranceCompany=dt.getString(0, 5);
    	String ordersn=dt.getString(0, 6);
    	
    	
    	
    	
    	String senddata =appntName+ ";"+contNo+";"+appntName+";"+insuredName+";"+signDate+";"+productName+";"+insuranceCompany+";"+ordersn;
        super.addMobileNum(mobilenum);
        super.addSendData(senddata);
        super.addManageCom(unitcode);
        super.addResult();

        return true;

    }

}
