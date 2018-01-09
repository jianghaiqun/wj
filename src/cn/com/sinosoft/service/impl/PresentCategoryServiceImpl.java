package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cn.com.sinosoft.dao.ChannelDao;
import cn.com.sinosoft.dao.PresentCategoryDao;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.entity.PresentCategory;
import cn.com.sinosoft.service.PresentCategoryService;

/**
 * Service实现类 - 礼品分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9C63255F0E5AAEE7DD3D83FB323FC00D
 * ============================================================================
 */

@Service
public class PresentCategoryServiceImpl extends BaseServiceImpl<PresentCategory, String> implements
		PresentCategoryService {

	@Resource
	private PresentCategoryDao presentCategoryDao;
	@Resource
	private ChannelDao channelDao;
	
	@Resource
	public void setBaseDao(PresentCategoryDao presentCategoryDao) {
		super.setBaseDao(presentCategoryDao);
	}


	@Cacheable(modelId = "caching")
	public List<PresentCategory> getRootPresentCategoryList() {
		List<PresentCategory> rootPresentCategoryList = presentCategoryDao.getRootPresentCategoryList();
		if (rootPresentCategoryList != null) {
			for (PresentCategory rootPresentCategory : rootPresentCategoryList) {
				Hibernate.initialize(rootPresentCategory);
			}
		}
		return rootPresentCategoryList;
	}
	
	@Cacheable(modelId = "caching")
	public List<PresentCategory> getParentPresentCategoryList(PresentCategory presentCategory) {
		List<PresentCategory> parentPresentCategoryList = presentCategoryDao.getParentPresentCategoryList(presentCategory);
		if (parentPresentCategoryList != null) {
			for (PresentCategory parentPresentCategory : parentPresentCategoryList) {
				Hibernate.initialize(parentPresentCategory);
			}
		}
		return parentPresentCategoryList;
	}
	
	public List<PresentCategory> getParentPresentCategoryList(Present present) {
		PresentCategory presentCategory = present.getPresentCategory();
		List<PresentCategory> presentCategoryList = new ArrayList<PresentCategory>();
		presentCategoryList.addAll(this.getParentPresentCategoryList(presentCategory));
		presentCategoryList.add(presentCategory);
		return presentCategoryList;
	}
	
	public List<PresentCategory> getPresentCategoryPathList(PresentCategory presentCategory) {
		List<PresentCategory> presentCategoryPathList = new ArrayList<PresentCategory>();
		presentCategoryPathList.addAll(this.getParentPresentCategoryList(presentCategory));
		presentCategoryPathList.add(presentCategory);
		return presentCategoryPathList;
	}
	
	public List<PresentCategory> getPresentCategoryPathList(Present present) {
		PresentCategory presentCategory = present.getPresentCategory();
		List<PresentCategory> presentCategoryList = new ArrayList<PresentCategory>();
		presentCategoryList.addAll(this.getParentPresentCategoryList(presentCategory));
		presentCategoryList.add(presentCategory);
		return presentCategoryList;
	}
	
	@Cacheable(modelId = "caching")
	public List<PresentCategory> getChildrenPresentCategoryList(PresentCategory presentCategory) {
		List<PresentCategory> childrenPresentCategoryList = presentCategoryDao.getChildrenPresentCategoryList(presentCategory);
		if (childrenPresentCategoryList != null) {
			for (PresentCategory childrenPresentCategory : childrenPresentCategoryList) {
				Hibernate.initialize(childrenPresentCategory);
			}
		}
		return childrenPresentCategoryList;
	}
	
	public List<PresentCategory> getChildrenPresentCategoryList(Present present) {
		PresentCategory presentCategory = present.getPresentCategory();
		List<PresentCategory> presentCategoryList = getChildrenPresentCategoryList(presentCategory);
		if (presentCategoryList == null) {
			presentCategoryList = new ArrayList<PresentCategory>();
		}
		presentCategoryList.add(presentCategory);
		return presentCategoryList;
	}
	//获取渠道列表
	public List<Channel> getPresentCategoryChannelList(){
		List<Channel> allPresentCategoryChannelList=channelDao.getAll();
		return  allPresentCategoryChannelList;	
	}
	
	@Cacheable(modelId = "caching")
	public List<PresentCategory> getPresentCategoryTreeList() {
		List<PresentCategory> allPresentCategoryList = this.getAll();
		return recursivPresentCategoryTreeList(allPresentCategoryList, null, null);
	}
	
	// 递归父类排序分类树
	private List<PresentCategory> recursivPresentCategoryTreeList(List<PresentCategory> allPresentCategoryList, PresentCategory p, List<PresentCategory> temp) {
		if (temp == null) {
			temp = new ArrayList<PresentCategory>();
		}
		for (PresentCategory presentCategory : allPresentCategoryList) {
			PresentCategory parent = presentCategory.getParent();
			if ((p == null && parent == null) || (presentCategory != null && parent == p)) {
				temp.add(presentCategory);
				if (presentCategory.getChildren() != null && presentCategory.getChildren().size() > 0) {
					recursivPresentCategoryTreeList(allPresentCategoryList, presentCategory, temp);
				}
			}
		}
		return temp;
	}

	@Override
	@Cacheable(modelId = "caching")
	public List<PresentCategory> getAll() {
		List<PresentCategory> allPresentCategory = presentCategoryDao.getAll();
		if (allPresentCategory != null) {
			for (PresentCategory presentCategory : allPresentCategory) {
				Hibernate.initialize(presentCategory);
			}
		}
		return allPresentCategory;
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(PresentCategory presentCategory) {
		presentCategoryDao.delete(presentCategory);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		presentCategoryDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		presentCategoryDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(PresentCategory presentCategory) {
		return presentCategoryDao.save(presentCategory);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(PresentCategory presentCategory) {
		presentCategoryDao.update(presentCategory);
	}
}