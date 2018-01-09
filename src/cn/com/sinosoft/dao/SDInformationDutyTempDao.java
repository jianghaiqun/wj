/**
 * 
 */
package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDInformationDutyTemp;

/**
 * @author wangcaiyun
 *
 */
public interface SDInformationDutyTempDao extends BaseDao<SDInformationDutyTemp, String> {

	public List<SDInformationDutyTemp> getDutyTemp(String Serials);
}
