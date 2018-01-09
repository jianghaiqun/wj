package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.Gift;

public interface GiftService extends BaseService<Gift, String> {
	public List<Gift> getGiftList();
}
