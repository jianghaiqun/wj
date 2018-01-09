package com.sinosoft.ibrms;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.ibrms.bom.AbstractBOM;
import com.sinosoft.ibrms.bom.MemberBOM;
import com.sinosoft.ibrms.bom.OrderBOM;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RuleIntegral extends Page
{
  private static List<AbstractBOM> getAbstractBOMList(String MemberID, double prem, String riskcode, double chargerate)
  {
    List list = new ArrayList();
    MemberBOM tMemberBOM = new MemberBOM();

    memberSchema tMemberSchema = new memberSchema();
    tMemberSchema.setid(MemberID);
    memberSet tMemberSet = tMemberSchema.query();
    if (tMemberSet.size() != 0)
      tMemberSchema = tMemberSet.get(0);

    if (StringUtil.isNotEmpty(MemberID))
      tMemberBOM.setRegisterdate(tMemberSchema.getcreateDate());
    else {
      tMemberBOM.setRegisterdate(new Date());
    }

    OrderBOM tOrderBOM = new OrderBOM();
    tOrderBOM.setPrem(prem);
    tOrderBOM.setRiskcode(riskcode);
    tOrderBOM.setChargerate(chargerate);
    tOrderBOM.setCurrentdate(new Date());
    list.add(tMemberBOM);
    list.add(tOrderBOM);
    return list;
  }

  public static int getIntegral(String MemberID, double prem, String riskcode, double chargerate) {
    List list = getAbstractBOMList(MemberID, prem, riskcode, chargerate);

    new RuleInlet("select drlpath from lrtemplate where business='02' and valid='1' and  state='7' and startdate<=sysdate() and (enddate>=sysdate() or enddate is null)", list).excuteAppoint();
    new RuleInlet("select drlpath from lrtemplate where business='03' and valid='1' and  state='7' and startdate<=sysdate() and (enddate>=sysdate() or enddate is null)", list).excuteAppoint();
    Double d = ((MemberBOM)list.get(0)).getFinalintegral();
    return new BigDecimal(d.doubleValue()).setScale(0, 4).intValue();
  }
}