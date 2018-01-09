package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.sms.messageinterface.parse.MessageCode;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;


/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : gaoph
 * @version  : 1.00
 * @date     : 2010-12-21 
 * @direction: 短信接口_代理人生日祝福
 ******************************************************************************/

public class MsgAgentBirthdayBusiness extends MessageBusinessBL
{
    private String agentcode;
    private String agentname;
    private String mobilenum;
    private String managecom;
    private String fixeddate;
//    private ExeSQL tExeSQL = new ExeSQL();
//    private SSRS tSSRS = null;
    private StringBuffer tSQL = new StringBuffer("");


    @SuppressWarnings("static-access")
	protected boolean checkData()
    {
        agentcode = super.getInput(MessageCode.AGENTCODE);
        agentname = super.getInput(MessageCode.AGENTNAME);
        mobilenum = super.getInput(MessageCode.MOBILENUM);
        managecom = super.getInput(MessageCode.MANAGECOM);
        fixeddate = super.getInput(MessageCode.FIXEDDATE);

        tSQL.append("select messageid  from LIMessageInteract ");
        tSQL.append("  where servicecode = '");
        tSQL.append(MessageCode.MSG_AGENTBIRTHDAY);
        tSQL.append("' and servicebussno = '");
        tSQL.append(agentcode);
        tSQL.append("' and mobilenum ='");
        tSQL.append(mobilenum);
        tSQL.append("' and date_format(makedate,'%Y') = date_format(date'");
        tSQL.append(PubFun.getCurrentDate());
        tSQL.append("', '%Y')  ");
        DataTable dt = new QueryBuilder(tSQL.toString()).executeDataTable();
//        tSSRS = tExeSQL.execSQL(tSQL.toString());
        if (dt.getRowCount() > 0)
        {
//        	super.mErrors.clearErrors();
//            super.mErrors.addOneError("此代理人相同手机号今年已经发送过生日短信。");
            return false;
        }
        tSQL.delete(0, tSQL.length());
        return true;
    }

    protected boolean dealData()
    {
        String fixedtime = "08:00:00";

        super.addMobileNum(mobilenum);
        super.addSendData(agentname);
        super.addCustomerName(agentname);
        super.addManageCom(managecom);
        super.addServiceBussNo(agentcode);
        super.addFixDate(fixeddate);
        super.addFixTime(fixedtime);
        super.addResult();

        return true;

    }

}
