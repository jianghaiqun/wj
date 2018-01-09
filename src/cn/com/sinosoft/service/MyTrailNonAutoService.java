package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.MyTrailNonAuto;
import cn.com.sinosoft.entity.TrailProduct;
/**
 * @author LiuXin
 *
 */
public interface MyTrailNonAutoService extends BaseService<MyTrailNonAuto,String>{
	public List<MyTrailNonAuto> getMyNonAutoTailByMemberId(String memberId);
	public List<TrailProduct> getTrailProductBySerialNo(String serialNumber);

}
