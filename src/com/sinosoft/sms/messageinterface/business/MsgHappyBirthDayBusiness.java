package com.sinosoft.sms.messageinterface.business;

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
 * @direction: 短信接口生日祝福批量处理类
 ******************************************************************************/

public class MsgHappyBirthDayBusiness extends MessageBusinessBL
{
    private String mobilenum;
    private String fixeddate;
    private String fixedtime;
    private String membername;
    private String unitcode;

    protected boolean checkData()
    {
    	
        mobilenum = super.getInput(MessageCode.MOBILENUM);
        fixeddate = super.getInput(MessageCode.FIXEDDATE);
        fixedtime = super.getInput(MessageCode.FIXEDTIME);
        membername= super.getInput(MessageCode.MEMBERNAME);
        unitcode=super.getInput(MessageCode.MANAGECOM);
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
    	String senddata = "";
    	senddata += membername;
        super.addMobileNum(mobilenum);
        super.addMemberName(membername);
        super.addSendData(senddata);
        super.addManageCom(unitcode);
        super.addFixDate(fixeddate);
        super.addFixTime(fixedtime);
        super.addResult();

        return true;

    }

}
