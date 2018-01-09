package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.LotteryActDao;
import cn.com.sinosoft.entity.LotteryAct;
import cn.com.sinosoft.service.LotteryActService;

/**
 * Service实现类 - 活动实现
 * ============================================================================
 *
 *  
 *
 * KEY:SINOSOFT5CCDCA53AF8463D621530B1ADA0CE130
 * ============================================================================
 */

@Service
public class LotteryActServiceImpl extends BaseServiceImpl<LotteryAct, String> implements LotteryActService {

	@Resource
	private LotteryActDao lotteryActDao;
	
	@Resource
	public void setBaseDao(LotteryActDao lotteryActDao) {
		super.setBaseDao(lotteryActDao);
	}
	
	@Override
	public List<LotteryAct> getListByCondition(String actCode,String type,String awards,String recordType) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("actCode","=",actCode));
		qbs.add(createQB("type","=",type));
		qbs.add(createQB("awards","=",awards));
		qbs.add(createQB("recordType","=",recordType));
		List<LotteryAct> pcs = lotteryActDao.findByQBs(qbs, "id", "desc");
		
		return pcs;
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

	@Override
	public List<LotteryAct> getListByMemberId(String memberId, String recordType,String useType,String actCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("memberId","=",memberId));
		qbs.add(createQB("recordType","=",recordType));
		qbs.add(createQB("useType","=",useType));
		qbs.add(createQB("actCode","=",actCode));
		List<LotteryAct> pcs = lotteryActDao.findByQBs(qbs, "id", "desc");
		
		return pcs;
	}

	@Override
	public List<LotteryAct> getListByWin(String recordType, String type, String actCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("recordType","=",recordType));
		qbs.add(createQB("type","=",type));
		qbs.add(createQB("actCode","=",actCode));
		List<LotteryAct> pcs = lotteryActDao.findByQBs(qbs, "id", "desc");
		return pcs;
	}

	@Override
	public List<LotteryAct> getListByAllUse(String recordType, String useType, String actCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("recordType","=",recordType));
		qbs.add(createQB("useType","=",useType));
		qbs.add(createQB("actCode","=",actCode));
		List<LotteryAct> pcs = lotteryActDao.findByQBs(qbs, "id", "desc");
		
		return pcs;
	}

	@Override
	public List<LotteryAct> getListByAllActCode(String actCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("actCode","=",actCode));
		List<LotteryAct> pcs = lotteryActDao.findByQBs(qbs, "id", "desc");
		return pcs;
	}

	@Override
	public List<LotteryAct> getListByWin(String recordType, String type) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("recordType","=",recordType));
		qbs.add(createQB("type","=",type));
		List<LotteryAct> pcs = lotteryActDao.findByQBs(qbs, "id", "desc");
		return pcs;
	}

}