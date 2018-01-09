package com.sinosoft.ibrms;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Errorx;

import java.util.HashMap;
import java.util.Map;



public class RuleStateUI extends Page {
	
	/** 错误处理类 */
	public Errorx mErrors = new Errorx();
	/** 数据操作字符串 */
	private String mOperate;
	
	private Map<String, Object> mInputData;
	

    /**
     传输数据的公共方法
     */
    public boolean submitData()
    {
    	String State = $V("State");
		String UserName = $V("UserName");
		String LRTemplate_ID = $V("LRTemplate_ID");
		String LRTemplateName = $V("LRTemplateName");
		
		Map<String, String> mInputData = new HashMap<String, String>();
		mInputData.put("State", State);
		mInputData.put("UserName", UserName);
		mInputData.put("LRTemplate_ID", LRTemplate_ID);
		mInputData.put("LRTemplateName", LRTemplateName);

        //进行业务处理
        if (!dealData())
        {
            return false;
        }
    
        RuleStateBL tRuleStateBL = new RuleStateBL();
        logger.info("Start RuleStateUI Submit...");
        if(!tRuleStateBL.submitData(mInputData, mOperate)){
        	this.mErrors = tRuleStateBL.mErrors;
        	Response.setMessage(mErrors.getMessages()[0]);
        	return false;
        }
        logger.info("End RuleStateUI Submit...");
        Response.setMessage("操作成功");
        return true;
    }

    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        return true;
    }
}
