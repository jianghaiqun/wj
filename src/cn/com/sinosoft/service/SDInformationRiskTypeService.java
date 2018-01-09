package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationRiskType;
import cn.com.sinosoft.entity.SDOrder;

/**
 * Service接口 - 险种
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB99DB2E7E2EF324F36B462FD6C183267
 * ============================================================================
 */

public interface SDInformationRiskTypeService extends BaseService<SDInformationRiskType, String> {

	public SDInformationRiskType getByOrderSnAndRecognizeeSn(String ordersn,
			String recognizeeSn);

	public SDInformationRiskType getInformationRiskType(SDOrder sdor,
			SDInformation sdinf, SDInformationAppnt sdapp, String recognizeeSn);
	
	public List<SDInformationRiskType> getInformationRiskTypeByOrderSn(String ordersn);
}
