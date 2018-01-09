package com.sinosoft.points;

import java.util.Map;

import com.sinosoft.framework.data.DataRow;

/**
 * 直接赠送
 *
 */
public class PointsRule01 extends PointsRule {

	@Override
	public Map<String, Object> pointsDeal(String act, Map<String, Object> param, DataRow act_dr) {
		return super.pointsDeal(act, param, act_dr);
	}

}
