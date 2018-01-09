package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.GiftDao;
import cn.com.sinosoft.entity.Gift;
import cn.com.sinosoft.service.GiftService;

@Service
public class GiftServiceImpl extends BaseServiceImpl<Gift, String> implements GiftService {
	@Resource
	GiftDao giftDao;

	@Resource
	public void setGiftDao(GiftDao giftDao) {
		super.setBaseDao(giftDao);
	}

	public List<Gift> getGiftList() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		// qbs.add(createQB("serialNumber", "=", sericalNo));
		return giftDao.findByQBs(qbs, "id", "asc");
	}

}
