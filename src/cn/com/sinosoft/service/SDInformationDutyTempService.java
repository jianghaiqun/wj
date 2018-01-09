/**
 * 
 */
package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.SDInformationDutyTemp;

/**
 * @author wangcaiyun
 *
 */
public interface SDInformationDutyTempService extends BaseService<SDInformationDutyTemp, String> {

	public List<SDInformationDutyTemp> getDutyTemp(String Serials);
}
