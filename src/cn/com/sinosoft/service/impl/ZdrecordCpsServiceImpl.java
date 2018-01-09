package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.ZdrecordCpsDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ZdrecordCps;
import cn.com.sinosoft.service.ZdrecordCpsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Date;

@Service
public class ZdrecordCpsServiceImpl extends
		BaseServiceImpl<ZdrecordCps, String> implements ZdrecordCpsService {
	@Resource
	private ZdrecordCpsDao zdrecordCpsDao;

	@Resource
	public void setZdrecordCpsDao(ZdrecordCpsDao zdrecordCpsDao) {
		super.setBaseDao(zdrecordCpsDao);
	}

	@Override
	public ZdrecordCps createZDRecordCPS(String productId, String ipAddress,
			Member m, String jumpAddress) {
		DateFormat format1 = new java.text.SimpleDateFormat("hh:mm:ss");
		ZdrecordCps zc = new ZdrecordCps();
		zc.setIpAddress(ipAddress);
		if(m!=null){
			zc.setMemberId(m.getId());
		}
		zc.setProductId(productId);
		zc.setCreateDate(new Date());
		zc.setModifyDate(new Date());
		zc.setJumpAddress(jumpAddress);
		zc.setOperDate(new Date());
		zc.setOperTime(format1.format(new Date()));
		return zc;
	}

}
