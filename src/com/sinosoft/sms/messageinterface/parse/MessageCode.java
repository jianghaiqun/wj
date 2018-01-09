package com.sinosoft.sms.messageinterface.parse;
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhouxiang
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口服务公共参数
 ******************************************************************************/
public class MessageCode
{
    /**数据包信息*/
    public static final String ROOTDATA = "Messages";
    /**公共信息*/
    public static final String PUBLICDATA = "PublicInfo";
    /**短信信息*/
    public static final String MESSAGEDATA = "Message";

    /**公共信息节点*/
    /**应用系统代码*/
    public static final String SYSTEMCODE = "SystemCode";
    /**服务代码*/
    public static final String SEVICECODE = "ServiceCode";

    /**短信信息节点*/
    /**信息Id*/
    public static final String MESSAGEID = "MessageId";
    /**手机号码*/
    public static final String MOBILENUMS = "MobileNums";
    public static final String MOBILENUM = "MobileNum";
    /**邮件地址*/
    public static final String EMAIL = "Email";
    /**信息主题*/
    public static final String MESSTOPIC = "MessTopic";
    /**发送数据*/
    public static final String SENDDATA = "SendData";
    /**数据类型,0:源数据；1:组织好的数据*/
    public static final String DATATYPE = "DataType";
    /**发送方式,0：立即；1：定时*/
    public static final String SENDWAY = "SendWay";
    /**定时发送日期*/
    public static final String FIXEDDATE = "FixedDate";
    /**定时发送时间*/
    public static final String FIXEDTIME = "FixedTime";
    /**机构代码*/
    public static final String MANAGECOM = "UnitCode";
    /**发送优先级*/
    public static final String DEALORDER = "DealOrder";
    /**回执是否立即匹配, 0：否；1：是*/
    public static final String ANSWERMATCH = "AnswerMatch";

    /**业务服务中使用信息*/
    /**投保人编号appntno*/
    public static final String APPNTNO = "AppntNo";
    //投保人姓名
    public static final String APPNTNAME = "AppntName";
    //投保人性别 
    public static final String APPNTSEX = "AppntSex";
    /**投保人地址编号addressno*/
    public static final String ADDRESSNO = "AddressNo";
    /**接受短信客户姓名，可能是投保人、申请人等*/
    public static final String CUSTOMERNAME = "CustomerName";
    /**投保单号*/
    public static final String PRTNO = "PrtNo";
    /**批次号*/
    public static final String SERIALNO = "SerialNo";
    /**收付费标示*/
    public static final String DEALTYPE = "DealType";
    /**银行制返盘PayCode*/
    public static final String PAYCODE = "PayCode";
    /**银行制返盘PolNo*/
    public static final String POLNO = "PolNo";
    /**通知书流水号*/
    public static final String PRTSEQ = "PrtSeq";
    /**理赔赔案号*/
    public static final String CLMNO = "CLMNo";
    /**保全受理号*/
    public static final String EDORACCEPTNO = "EdorAcceptNo";
    /**代理人工号*/
    public static final String AGENTCODE = "AgentCode";
    /**保单合同号*/
    public static final String CONTNO = "ContNo";
    /**保全申请日期*/
    public static final String EDORAPPDATE = "EdorAppDate";
    /**保全项目代码*/
    public static final String EDORTYPE = "EdorType";
    /**保全项目名称*/
    public static final String EDORNAME = "EdorName";
    /**业务服务号*/
    public static final String SERVICEBUSSNO = "ServiceBussNo";
    /**涉及金额*/
    public static final String MONEY = "Money";	
    /**账户号*/
    public static final String ACCNO = "AccNo";	
    /**账户名称*/
    public static final String ACCNAME = "AccName";	
    /**还款日期*/
    public static final String PAYOFFDATE = "PayOffDate";
    /**处理日期*/
    public static final String DEALDATE = "DealDate";
    
    /**业务员名字*/
    public static final String AGENTNAME = "AgentName";
    /**申请项目类型*/
    public static final String APPTYPE = "AppType";
    /**项目改动层号*/
    public static final String OTHERNOTYPE = "OtherNoType";
    /**会员名字*/
    public static final String MEMBERNAME = "MemberName";
    /**注册验证码*/
    public static final String CAPTCHA = "Captcha";
    
    /**系统代码*/
    public static final String SYS_SYSTEMCODE = "WF";
    /**服务代码*/
    /**投保确认*/
    public static final String TB_TRAIL = "LIS000001";
    /**首期扣款通知*/
    public static final String TB_PAYSUCC = "LIS000002";
    /**投保人已承保通知*/
    public static final String TB_APPNTSUCC = "LIS000003";
    /**代理人已承保通知*/
    public static final String TB_AGENTSUCC = "LIS000004";
    /**新契约各类通知书提醒*/
    public static final String TB_NOTICESUCC = "LIS000005";
    /**10日未结案通知*/
    public static final String CLM_OVERDAYS = "LIS000007";
    /**理赔已赔付通知*/
    public static final String CLM_ENDCASE = "LIS000006";
    
    /**保全经办业务员代办保单层项目完成通知*/
    public static final String EDOR_ITEMSUCC = "LIS000009";
    /**代理人离职（个、银）*/
    public static final String QD_DIMISSION = "LIS000011";
    /**生日祝福*/
    public static final String Msg_BIRTHDAY = "LIS000010";
    /**孤儿单预分配（个）*/
    public static final String PR_DIMISSION = "LIS000013";
    /**孤儿单分配确认（个）*/
    public static final String CO_DIMISSION = "LIS000012";
    /**短信平台生日快乐，10日未结案，银保承保对账成功发送短信*/
    public static final String MSG_TRANSFERTASK = "LIS000014";
    /**新年问候*/
    public static final String MSG_NEWYEAR = "LIS000016";
    /**续期收费成功短信*/
    public static final String XQ_PAYSUCC = "LIS000017";
    /**批处理校验短信通知*/
    public static final String MSG_TASKSTATE = "LIS000018";
    /**续期收费提醒*/
    public static final String MSG_XQPAYCALL = "LIS000019";
    /**续期收费多次失败提醒*/
    public static final String MSG_XQPAYWARNCALL = "LIS000020";
    /**续期收费失败，保单失效提醒*/
    public static final String MSG_XQPAYINVALIDATE = "LIS000021";
    /**续期收费失败短信(投保人)*/
    public static final String XQ_PAYFAILTOAPPNT = "LIS000022";
    /**续期收费失败短信(代理人)*/
    public static final String XQ_PAYFAILTOAGENT = "LIS000023";
    /**续期回退/续期暂收费回退成功，短信提醒代码*/
    public static final String MSG_XQANDTEMPRETURNSUC = "LIS000024";
    /**续期回退/续期暂收费回退失败，短信提醒代码*/
    public static final String MSG_XQANDTEMPRETURNFAIL = "LIS000025";
    /**分红通知寄发短信通知-纸质信函方式*///取消分红短信
    //public static final String MSG_BONUSNOTICEPAPERBUSINESS = "LIS000026";
    /**分红通知寄发短信通知-电子邮件方式*/
    //public static final String MSG_BONUSNOTICEMAILBUSINESS = "LIS000027";
    /**保全补费因余额不足失败短信通知*/
    public static final String MSG_BQGETFAILYEBZ = "LIS000030";
    /**银保代理人已承保通知*/
    public static final String TB_YBAGENTSUCC = "LIS000031";
    /**保全补费非余额不足失败短信通知*/
    public static final String MSG_BQGETFAILNOTYEBZ = "LIS000032";
    /**保全退费费失败短信通知*/
    public static final String MSG_BQPAYFAILTOAPPNT = "LIS000033";
    /**银保投保人已承保通知-常规*/
    public static final String TB_YBAPPNTSUCC = "LIS000034";
    
    /**续期收费成功给代理人的短信通知*/
    public static final String XQ_PAYSUCCTOAGENT = "LIS000035";
    
    /**续期应交日后45天给保单代理人的短信通知*/
    public static final String MSG_CONTINVATOAGENT = "LIS000036";
    
    /**理赔多受益人已赔付短信通知**/
    public static final String CLM_INDEMNIFIED = "LIS000037";
    
    /**团险理赔已赔付短信通知**/
    public static final String MSG_GRPGLAIM_PAYED = "LIS000038";
    
    /**电销赠险激活后短信**/
    public static final String MSG_TELSALE_GIFT = "LIS000039";
    
    /**一保通自动注册成功提醒短信**/
    public static final String MSG_AUTOLOGONSUCC = "LIS000040";
    
    /**电销逾期撤件通知短信**/
    public static final String MSG_TMOVERDUE = "LIS000041";
    
    /**电销新契约出单通知短信**/
    public static final String MSG_TMCONTPRINTED = "LIS000042";
    
    /**电销首期转账成功通知短信**/
    public static final String MSG_TMFIRSTPAYSUCC = "LIS000043";
    
    /**理赔受理短息通知申请人**/
    public static final String CLM_ACCEPTRPTOR = "LIS000044";
    
    /**理赔受理短息通知委托人**/
    public static final String CLM_ACCEPTASSIGNEE = "LIS000045";
    
    /**理赔已赔付短信通知委托人**/
    public static final String CLM_ENDASSIGNEE = "LIS000046";
    
    /**保单贷款到期短信提醒**/
    public static final String MSG_REMINDPAYOFFLOAN = "LIS000047";
    
    /**保单贷款滚存短信提醒**/
    public static final String MSG_LOANACCUMULATE = "LIS000048";
    
    /**保单贷款超停预警短信提醒**/
    public static final String MSG_OVERLOANWARNING = "LIS000049";
    
    /**保单贷款超停短信通知**/
    public static final String MSG_OVERLOAN = "LIS000050";
    
    /**保单失效短信通知**/
    public static final String MSG_CONTINVALID = "LIS000051";
    
    /**保全经办业务员代办客户层项目完成通知*/
    public static final String EDOR_ITEMDEALCUSTOMERSUCC = "LIS000052";
    
    /**保全经办非业务员代办保单层项目完成通知*/
    public static final String EDOR_ITEMNOTDEALPOLICYSUCC = "LIS000053";
    
    /**保全经办非业务员代办客户层项目完成通知*/
    public static final String EDOR_ITEMNOTDEALCUSTOMERSUCC = "LIS000054";
    /**代理人生日问候短信*/
    public static final String MSG_AGENTBIRTHDAY = "LIS000055";
    
    /**航意险大连地区承保通知短信*/
    public static final String MSG_PALNEACCIDENTCALLACCEPTDL = "LIS000056";  
   
    /**航意险大连地区退保通知短信*/
    public static final String MSG_PALNEACCIDENTCALLSURRENDERDL = "LIS000057";  
    
    /**航意险北京地区承保通知短信*/
    public static final String MSG_PALNEACCIDENTCALLACCEPTBJ = "LIS000058";  
   
    /**航意险北京地区退保通知短信*/
    public static final String MSG_PALNEACCIDENTCALLSURRENDERBJ = "LIS000059"; 
    
    /**大连航意险7.5元方案短信承保通知短信*/
    public static final String MSG_PALNEACCIDENTCALLACCEPTDLSEVEN = "LIS000061";  
   
    /**大连航意险7.5元方案短信退保通知短信*/
    public static final String MSG_PALNEACCIDENTCALLSURRENDERDLSEVEN = "LIS000060";
    
    /**电销新契约首期POS转账出单通知短信*/
    public static final String MSG_TMCONTPOSPRINTED = "LIS000062";
    
    /**每天对导入回访系统中的应访客户发送短信提醒近期会接到公司回访电话*/
    public static final String MSG_CALLBACKPHONE = "LIS000063";
    
    /**针对银保通的保单给客户经理发送已承保短信通知*/
    public static final String TB_YBTAGENTSUCC = "LIS000064";
    
    /**针对安贷无忧意外卡(200009)发送已承保短信通知*/
    public static final String MSG_GRPCARD = "LIS000065";
    /**报案确认发送短信*/
    public static final String LLREPORT_MSG = "LIS000066";
    
    public static final String MSG_TELSALE_ACTIVATED = "LIS000067";
    /**网站手工注册*/
    public static final String MSG_MANUAL_WEBSIGNIN = "LIS000068";
    
    /**团险承保短信发送*/
    public static final String MSG_GRPCONT = "LIS000069";
    
    /** 物流公司 */
    public static final String LOGISTICS_COMPANY = "LogisticsCompany";
    /** 运单号 */
    public static final String WAY_BILL_NO = "WayBillNo";
    
    /** 理赔项目 */
    public static final String CLAIMS_ITEMS = "ClaimsItems";
    /** 理赔单号 */
    public static final String CLAIMS_NO = "ClaimsNO";
    /** 理赔金额 */
    public static final String CLAIMS_MONEY = "ClaimsMoney";
    /** 理赔拒赔原因 */
    public static final String CLAIMS_RETURN_DESC = "ClaimsReturnDesc";
    /** 理赔材料邮寄地址 */
    public static final String CLAIMS_SEND_ADDRESS = "ClaimsSendAddress";
    /** 理赔材料邮寄信息 */
    public static final String CLAIMS_POST_INFO = "ClaimsPostInfo";
    
}
