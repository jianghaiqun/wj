package cn.com.sinosoft.service;

import java.util.Date;
import java.util.Map;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.SDOrder;

/**
 * Service接口 - 绑定信息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTC25D50D18A27A8E1B4A11F7643DAA055
 * ============================================================================
 */

public interface BindInfoForLoginService extends BaseService<BindInfoForLogin, String> {
	
	public BindInfoForLogin getBindInfoForLoginByOpenID(String openid);
}