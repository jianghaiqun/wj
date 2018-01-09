package cn.com.sinosoft.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.com.sinosoft.entity.SDDeliverAddress;

/**
 * Service接口 - 邮寄地址
 *
 */
@Transactional(rollbackFor=Exception.class)
public interface SDDeliverAddressService extends BaseService<SDDeliverAddress, String> {
	@Transactional(readOnly=true)
	public List<SDDeliverAddress> getSDDeliverAddressInfo(String memberId);
}
