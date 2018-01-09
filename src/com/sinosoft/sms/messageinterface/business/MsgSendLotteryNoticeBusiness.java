package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.sms.messageinterface.parse.MessageCode;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * @direction: 用户中奖通知
 ******************************************************************************/

public class MsgSendLotteryNoticeBusiness extends MessageBusinessBL
{
	  private String mobilenum;
	  private String membername;
	  private String unitcode;

	  protected boolean checkData()
	  {
	    this.mobilenum = super.getInput("MobileNum");
	    this.membername = super.getInput("MemberName");
	    this.unitcode = super.getInput("UnitCode");
	    return true;
	  }

	  protected boolean dealData() {
	    Date tDate = new Date();
	    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
	    String temp2 = formatter1.format(tDate);
	    String senddata = this.membername + temp2;
	    super.addMobileNum(this.mobilenum);
	    super.addSendData(senddata);
	    super.addManageCom(this.unitcode);
	    super.addResult();

	    return true;
	  }

}
