package com.sinosoft.inter;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.ibrms.RuleInlet;
import com.sinosoft.ibrms.bom.InteractionBOM;
import com.sinosoft.schema.SDInterActionSchema;
import com.sinosoft.schema.SDInterActionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据准备，经验积分规则 ============================================================================
 * 
 * KEY:SINOSOFT9A02B310F538BFE58B8F04583E341B6E ============================================================================
 */

public class InterUtil {
	private static final Logger logger = LoggerFactory.getLogger(InterUtil.class);
	/**
	 * 
	 * @param actionId
	 *            动作id.
	 * 
	 * @return String
	 */
	public int deal(String actionId) {
		SDInterActionSet tSDInterActionSet = check(actionId);// 获取动作信息

		if (tSDInterActionSet.size() == 0 || tSDInterActionSet.size() > 1) {
			logger.info("横向接口表{}数据重复", actionId);
		}
		SDInterActionSchema tSDInterActionSchema = tSDInterActionSet.get(0);
		if ("RS".equals(tSDInterActionSchema.getActionType())) {
			return getActivityReward();
		} else if ("RS1".equals(tSDInterActionSchema.getActionType())) {

		}

		return 0;

	}

	// 获取积分
	private int getActivityReward() {
		InteractionBOM tInteractionBOM = new InteractionBOM();
		tInteractionBOM.setIsRegister(1);

		String sql = "select drlpath from lrtemplate where business='02' and valid='1' and  state='7' and startdate<=sysdate() and (enddate>=sysdate() or enddate is null)";
		RuleInlet tRuleInlet = new RuleInlet(sql, tInteractionBOM);
		tRuleInlet.excuteAppoint();

		return tInteractionBOM.getIntegralNum();
	}

	private SDInterActionSet check(String actionId) {
		SDInterActionSchema tSDInterActionSchema = new SDInterActionSchema();

		SDInterActionSet tSDInterActionSet = tSDInterActionSchema.query(new QueryBuilder("where ActionId ='" + actionId + "'"));

		return tSDInterActionSet;

	}
}