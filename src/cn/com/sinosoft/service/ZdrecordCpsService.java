package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ZdrecordCps;

public interface ZdrecordCpsService extends BaseService<ZdrecordCps,String>{
	public ZdrecordCps createZDRecordCPS(String productId ,String ipAddress, Member m,String jumpAddress);
	
}
