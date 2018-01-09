package com.sinosoft.ibrms;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.LRTemplateBSchema;
import com.sinosoft.schema.LRTemplateSchema;
import com.sinosoft.schema.LRTemplateSet;
import com.sinosoft.schema.LRTemplateTSchema;
import com.sinosoft.schema.LRTemplateTSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;




public class RuleStateBL {
	private static final Logger logger = LoggerFactory.getLogger(RuleStateBL.class);
	/** 错误处理类 */
	public Errorx mErrors = new Errorx();
	/** 数据操作字符串 */
	private String mOperate;
	 /** 往后面传输的数据库操作 */
    private Map map = new HashMap<String,Object>();
	//状态
	private String mState;
	//操作人
	private String mUserName;
	//规则id
	private String mLRTemplate_ID;
	//规则表
	private String mLRTemplateName;
	
	private Transaction trans = new Transaction();
	
	private LRTemplateTSchema mLRTemplateTSchema = new LRTemplateTSchema();
	
	
	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(Map cInputData, String cOperate) {
        //将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		//检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
		// 进行业务处理		
		if (!dealDate()) {			
			mErrors.addError( "数据处理失败RuleStateBL-->dealData!");
			return false;
		}
		if (trans.commit()) {
			return true;
		} else {
			mErrors.addError("执行sql语句失败");
			return false;
		}
	}

	private boolean dealDate() {
		/** 0——初始
			1——待测试
			2——待修改
			3——待审批
			4--待发布
			7--可使用
			9--已作废**/
		String CurrentTime = IBRMSPubFun.getCurrentTime();
		LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
		LRTemplateTSet tLRTemplateTSet = tLRTemplateTSchema.query(new QueryBuilder("where id = trim('"+mLRTemplate_ID+"')"));
		if(tLRTemplateTSet.size()==0){
			mErrors.addError("该规则为找到，请刷新页面重新操作！");
			return false;
		}
		mLRTemplateTSchema = tLRTemplateTSet.get(0);
		if(mState.equals("00")){
			String sql = "drop table "+mLRTemplateTSchema.getTableName();
			File file = new File(mLRTemplateTSchema.getDRLPath());
			if(file.isFile()){
				file.delete();
			}
			trans.add(mLRTemplateTSchema,Transaction.DELETE);
			trans.add(new QueryBuilder(sql));
			return true;
		}
		if(mState.equals("2")&&mLRTemplateName.equals("lrtemplate")){//当发布完成后需要修改已使用规则
			
			LRTemplateSchema tLRTemplateSchema = new LRTemplateSchema();
			LRTemplateSet tLRTemplateSet = tLRTemplateSchema.query(new QueryBuilder("where id = trim('"+mLRTemplate_ID+"')"));
			if(tLRTemplateTSet.size()==0){
				mErrors.addError("该规则未找到，请刷新页面重新操作！");
				return false;
			}
			tLRTemplateSchema = tLRTemplateSet.get(0);
			trans.add(tLRTemplateSchema,Transaction.DELETE);

			LRTemplateBSchema tLRTemplateBSchema = new LRTemplateBSchema();
			tLRTemplateBSchema.setId(tLRTemplateSchema.getId());
			tLRTemplateBSchema.setVersion(tLRTemplateSchema.getVersion());
			tLRTemplateBSchema.setRuleName(tLRTemplateSchema.getRuleName());
			tLRTemplateBSchema.setSQLStatement(tLRTemplateSchema.getSQLStatement());
			tLRTemplateBSchema.setBOMs(tLRTemplateSchema.getBOMs());
			tLRTemplateBSchema.setSQLParameter(tLRTemplateSchema.getSQLParameter());
			tLRTemplateBSchema.setValid(tLRTemplateSchema.getValid());
			tLRTemplateBSchema.setState("9");
			tLRTemplateBSchema.setTemplateLevel(tLRTemplateSchema.getTemplateLevel());
			tLRTemplateBSchema.setBusiness(tLRTemplateSchema.getBusiness());
			tLRTemplateBSchema.setType(tLRTemplateSchema.getType());
			tLRTemplateBSchema.setTableName(tLRTemplateSchema.getTableName());
			tLRTemplateBSchema.setStartDate(tLRTemplateSchema.getStartDate());
			tLRTemplateBSchema.setEndDate(tLRTemplateSchema.getEndDate());
			tLRTemplateBSchema.setDescription(tLRTemplateSchema.getDescription());
			tLRTemplateBSchema.setCreator(tLRTemplateSchema.getCreator());
			tLRTemplateBSchema.setModifier(tLRTemplateSchema.getModifier());
			tLRTemplateBSchema.setApprover(tLRTemplateSchema.getApprover());
			tLRTemplateBSchema.setDeployer(tLRTemplateSchema.getDeployer());
			tLRTemplateBSchema.setMakeDate(tLRTemplateSchema.getMakeDate());
			tLRTemplateBSchema.setMakeTime(tLRTemplateSchema.getMakeTime());
			tLRTemplateBSchema.setModifyDate(tLRTemplateSchema.getModifyDate());
			tLRTemplateBSchema.setModifyTime(tLRTemplateSchema.getModifyTime());
			tLRTemplateBSchema.setAuthorDate(tLRTemplateSchema.getAuthorDate());
			tLRTemplateBSchema.setAuthorTime(tLRTemplateSchema.getAuthorTime());
			tLRTemplateBSchema.setDeclDate(tLRTemplateSchema.getDeclDate());
			tLRTemplateBSchema.setDeclTime(tLRTemplateSchema.getDeclTime());
			tLRTemplateBSchema.setRuleCh(tLRTemplateSchema.getRuleCh());
			tLRTemplateBSchema.setViewParameter(tLRTemplateSchema.getViewParameter());
			tLRTemplateBSchema.setMarketingNum(tLRTemplateSchema.getMarketingNum());
			tLRTemplateBSchema.setDRLPath(tLRTemplateSchema.getDRLPath());
			trans.add(tLRTemplateBSchema,Transaction.INSERT);
		}
		
		
		if(mState.equals("4")){//修改为待发布状态，即审批通过
			mLRTemplateTSchema.setApprover(mUserName);
			mLRTemplateTSchema.setAuthorDate(new Date());
			mLRTemplateTSchema.setAuthorTime(CurrentTime);
		}
		
		if(mState.equals("7")){//修改为发布状态(可使用状态)，即在规则正式表lrtemplate中存放数据。
			mLRTemplateTSchema.setDeployer(mUserName);
			mLRTemplateTSchema.setDeclDate(new Date());
			mLRTemplateTSchema.setDeclTime(CurrentTime);
			LRTemplateSchema tLRTemplateSchema = new LRTemplateSchema();
			tLRTemplateSchema.setId(mLRTemplateTSchema.getId());
			tLRTemplateSchema.setVersion(mLRTemplateTSchema.getVersion());
			tLRTemplateSchema.setRuleName(mLRTemplateTSchema.getRuleName());
			tLRTemplateSchema.setSQLStatement(mLRTemplateTSchema.getSQLStatement());
			tLRTemplateSchema.setBOMs(mLRTemplateTSchema.getBOMs());
			tLRTemplateSchema.setSQLParameter(mLRTemplateTSchema.getSQLParameter());
			tLRTemplateSchema.setValid(mLRTemplateTSchema.getValid());
			tLRTemplateSchema.setState(mState);
			tLRTemplateSchema.setTemplateLevel(mLRTemplateTSchema.getTemplateLevel());
			tLRTemplateSchema.setBusiness(mLRTemplateTSchema.getBusiness());
			tLRTemplateSchema.setType(mLRTemplateTSchema.getType());
			tLRTemplateSchema.setTableName(mLRTemplateTSchema.getTableName());
			tLRTemplateSchema.setStartDate(mLRTemplateTSchema.getStartDate());
			tLRTemplateSchema.setEndDate(mLRTemplateTSchema.getEndDate());
			tLRTemplateSchema.setDescription(mLRTemplateTSchema.getDescription());
			tLRTemplateSchema.setCreator(mLRTemplateTSchema.getCreator());
			tLRTemplateSchema.setModifier(mLRTemplateTSchema.getModifier());
			tLRTemplateSchema.setApprover(mLRTemplateTSchema.getApprover());
			tLRTemplateSchema.setDeployer(mLRTemplateTSchema.getDeployer());
			tLRTemplateSchema.setMakeDate(mLRTemplateTSchema.getMakeDate());
			tLRTemplateSchema.setMakeTime(mLRTemplateTSchema.getMakeTime());
			tLRTemplateSchema.setModifyDate(mLRTemplateTSchema.getModifyDate());
			tLRTemplateSchema.setModifyTime(mLRTemplateTSchema.getModifyTime());
			tLRTemplateSchema.setAuthorDate(mLRTemplateTSchema.getAuthorDate());
			tLRTemplateSchema.setAuthorTime(mLRTemplateTSchema.getAuthorTime());
			tLRTemplateSchema.setDeclDate(mLRTemplateTSchema.getDeclDate());
			tLRTemplateSchema.setDeclTime(mLRTemplateTSchema.getDeclTime());
			tLRTemplateSchema.setRuleCh(mLRTemplateTSchema.getRuleCh());
			tLRTemplateSchema.setViewParameter(mLRTemplateTSchema.getViewParameter());
			tLRTemplateSchema.setMarketingNum(mLRTemplateTSchema.getMarketingNum());
			tLRTemplateSchema.setDRLPath(mLRTemplateTSchema.getDRLPath());
			trans.add(tLRTemplateSchema,Transaction.INSERT);
			
		}
		if(mState.equals("9")){//修改为作废状态时，即将规则正式表中数据删除到lrtemplateb表中。
			LRTemplateSchema sLRTemplateSchema = new LRTemplateSchema();
			LRTemplateSet tLRTemplateSet = sLRTemplateSchema.query(new QueryBuilder("where id = trim('"+mLRTemplate_ID+"')"));
			if(tLRTemplateSet.size()==0){
				mErrors.addError("该规则为找到，请刷新页面重新操作！");
				return false;
			}
			LRTemplateSchema tLRTemplateSchema = tLRTemplateSet.get(0);
			tLRTemplateSchema.setState(mState);
			tLRTemplateSchema.setModifier(mUserName);
			tLRTemplateSchema.setModifyDate(new Date());
			tLRTemplateSchema.setModifyTime(CurrentTime);
			trans.add(tLRTemplateSchema,Transaction.UPDATE);
			
			LRTemplateBSchema tLRTemplateBSchema = new LRTemplateBSchema();
			tLRTemplateBSchema.setId(tLRTemplateSchema.getId());
			tLRTemplateBSchema.setVersion(tLRTemplateSchema.getVersion());
			tLRTemplateBSchema.setRuleName(tLRTemplateSchema.getRuleName());
			tLRTemplateBSchema.setSQLStatement(tLRTemplateSchema.getSQLStatement());
			tLRTemplateBSchema.setBOMs(tLRTemplateSchema.getBOMs());
			tLRTemplateBSchema.setSQLParameter(tLRTemplateSchema.getSQLParameter());
			tLRTemplateBSchema.setValid(tLRTemplateSchema.getValid());
			tLRTemplateBSchema.setState(tLRTemplateSchema.getState());
			tLRTemplateBSchema.setTemplateLevel(tLRTemplateSchema.getTemplateLevel());
			tLRTemplateBSchema.setBusiness(tLRTemplateSchema.getBusiness());
			tLRTemplateBSchema.setType(tLRTemplateSchema.getType());
			tLRTemplateBSchema.setTableName(tLRTemplateSchema.getTableName());
			tLRTemplateBSchema.setStartDate(tLRTemplateSchema.getStartDate());
			tLRTemplateBSchema.setEndDate(tLRTemplateSchema.getEndDate());
			tLRTemplateBSchema.setDescription(tLRTemplateSchema.getDescription());
			tLRTemplateBSchema.setCreator(tLRTemplateSchema.getCreator());
			tLRTemplateBSchema.setModifier(tLRTemplateSchema.getModifier());
			tLRTemplateBSchema.setApprover(tLRTemplateSchema.getApprover());
			tLRTemplateBSchema.setDeployer(tLRTemplateSchema.getDeployer());
			tLRTemplateBSchema.setMakeDate(tLRTemplateSchema.getMakeDate());
			tLRTemplateBSchema.setMakeTime(tLRTemplateSchema.getMakeTime());
			tLRTemplateBSchema.setModifyDate(tLRTemplateSchema.getModifyDate());
			tLRTemplateBSchema.setModifyTime(tLRTemplateSchema.getModifyTime());
			tLRTemplateBSchema.setAuthorDate(tLRTemplateSchema.getAuthorDate());
			tLRTemplateBSchema.setAuthorTime(tLRTemplateSchema.getAuthorTime());
			tLRTemplateBSchema.setDeclDate(tLRTemplateSchema.getDeclDate());
			tLRTemplateBSchema.setDeclTime(tLRTemplateSchema.getDeclTime());
			tLRTemplateBSchema.setRuleCh(tLRTemplateSchema.getRuleCh());
			tLRTemplateBSchema.setViewParameter(tLRTemplateSchema.getViewParameter());
			tLRTemplateBSchema.setMarketingNum(tLRTemplateSchema.getMarketingNum());
			tLRTemplateBSchema.setDRLPath(tLRTemplateSchema.getDRLPath());
			trans.add(tLRTemplateBSchema,Transaction.INSERT);
			trans.add(tLRTemplateSchema,Transaction.DELETE);
			
			
		}
		mLRTemplateTSchema.setState(mState);
		mLRTemplateTSchema.setModifier(mUserName);
		mLRTemplateTSchema.setModifyDate(new Date());
		mLRTemplateTSchema.setModifyTime(CurrentTime);
		trans.add(mLRTemplateTSchema,Transaction.UPDATE);
		return true;
	}

	private boolean checkInputData() {
		return true;
	}

	private boolean getInputData(Map cInputData) {
		mState = (String)cInputData.get("State");
		mUserName =(String)cInputData.get("UserName");
		mLRTemplate_ID = (String)cInputData.get("LRTemplate_ID");
		mLRTemplateName = (String)cInputData.get("LRTemplateName");
		
		if(StringUtil.isEmpty(mState)||StringUtil.isEmpty(mLRTemplate_ID)||StringUtil.isEmpty(mLRTemplateName)){
			String[] argArr = {mState, mLRTemplate_ID, mLRTemplateName};
			logger.error("数据传输错误！mState:{},mLRTemplate_ID:{},mLRTemplateName:{}", argArr);
			mErrors.addError("数据传输错误！");
			return false;
		}
		
		return true;
	}
}
